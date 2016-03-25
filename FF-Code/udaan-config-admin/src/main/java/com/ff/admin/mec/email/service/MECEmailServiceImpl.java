package com.ff.admin.mec.email.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.FrameworkResourceLoaderUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.organization.OfficeTO;

/**
 * @author hkansagr
 */
public class MECEmailServiceImpl implements MECEmailService {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MECEmailServiceImpl.class);

	/** The MEC Common Service */
	private MECCommonService mecCommonService;

	/** The Email Sender Utility Service. */
	private EmailSenderUtil emailSenderUtil;

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * @param mecCommonService
	 *            the mecCommonService to set
	 */
	public void setMecCommonService(MECCommonService mecCommonService) {
		this.mecCommonService = mecCommonService;
	}

	@Override
	public void triggerEmailToRHO() throws Exception {
		LOGGER.trace("MECEmailServiceImpl :: triggerEmailToRHO() :: START");
		// To get all RHOs.
		List<OfficeTO> allRHOs = mecCommonService
				.getAllOfficesByType(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);

		if (!CGCollectionUtils.isEmpty(allRHOs)) {
			// Initialized common values
			String from = mecCommonService
					.getConfigParamValueByName(CommonConstants.FFCL_UDAAN_SUPPORT_EMAIL_ID);
			String[] bcc = {}; // It should be blank
			String mailSubject = FrameworkResourceLoaderUtil
					.getAsString(MECCommonConstants.MEC_EMAIL_SUBJECT);
			String bodyText = getBodyTextMsg();
			String sheetName = FrameworkResourceLoaderUtil
					.getAsString(MECCommonConstants.MEC_EMAIL_SHEET_NAME);
			// To get MEC details of particular RHO and send email to respective
			// RHO/user(s).
			for (OfficeTO rho : allRHOs) {
				MailSenderTO mailSenderTO = null;
				FileOutputStream ops = null;
				File excelFile = null;
				try {
					// 1. To get MEC details of particular RHO
					List<CollectionDO> collectionDOs = mecCommonService
							.getChequeDepositSlipDtls(rho.getOfficeId());
					if (!CGCollectionUtils.isEmpty(collectionDOs)) {
						// 2. Prepare mail sender transfer object
						mailSenderTO = new MailSenderTO();
						// from
						mailSenderTO.setFrom(from);
						// to
						String[] to = { rho.getEmail() };
						mailSenderTO.setTo(to);
						// cc
						String[] cc = { rho.getEmail() };
						mailSenderTO.setCc(cc);
						// bcc
						mailSenderTO.setBcc(bcc);
						// mail subject
						mailSenderTO.setMailSubject(mailSubject);
						// plain mail body
						mailSenderTO.setPlainMailBody(bodyText);

						// 3. Prepare excel workbook
						List<String> commaSepratedCellStr = prepareExcelDataList(collectionDOs);
						HSSFWorkbook excelWorkbook = CGExcelUtil
								.writeToWorkbook(sheetName,
										commaSepratedCellStr);
						// To format excel workbook as per required excel format
						formatExcelWorkbook(
								excelWorkbook,
								sheetName,
								collectionDOs.size(),
								commaSepratedCellStr.get(4).split(
										CommonConstants.COMMA).length);

						// 4. Attaching excel file to email
						// attached file name - CDS_OFFICECODE_DDMMYYYY.xls
						String fileName = MECCommonConstants.CHEQUE_DEPOSIT_SLIP
								+ CommonConstants.UNDERSCORE
								+ rho.getOfficeCode().toUpperCase()
								+ CommonConstants.UNDERSCORE
								+ mecCommonService.decreaseDateByDays(
										DateUtil.getCurrentDateInDDMMYYYY(), 1,
										FrameworkConstants.DDMMYYYY_FORMAT)
								+ ".xls";
						excelFile = new File(fileName);
						ops = new FileOutputStream(excelFile);
						excelWorkbook.write(ops);
						// attached file
						mailSenderTO.setAttachedFileName(fileName);
						mailSenderTO.setAttachedFile(excelFile);

						// 5. Call email sender utility service and send email
						// to respective RHO/User(s) [Attachment: prepared excel
						// work book contain MEC details for specific RHO]
						emailSenderUtil.sendEmail(mailSenderTO);
					}// End - IF Block
				} catch (Exception e) {
					LOGGER.error(
							"Exception occurs in MECEmailServiceImpl :: triggerEmailToRHO() :: ",
							e);
					// Don't throw any specific exception to continue for next
					// RHO
				} finally {
					// Close output stream if open
					try {
						if (ops != null) {
							ops.close();
						}
						// If file is created and exists in server machine then
						// delete it after required
						// if (excelFile != null && excelFile.exists()) {
						// excelFile.delete();
						// }
					} catch (IOException e) {
						LOGGER.error(
								"Exception occurs in MECEmailServiceImpl :: triggerEmailToRHO() :: IOException, during file closing or deleting :: ",
								e);
						// Don't throw any specific exception to continue for
						// next RHO
					}// End - Inner Catch Block
				}// End Finally Block
			}// End - FOR Loop
		}// End - IF Block
		LOGGER.trace("MECEmailServiceImpl :: triggerEmailToRHO() :: END");
	}

	/**
	 * To prepare excel sheet data list according to excel format
	 * 
	 * @return result list
	 * @param collectionDOs
	 * @throws Exception
	 */
	private List<String> prepareExcelDataList(List<CollectionDO> collectionDOs)
			throws Exception {
		LOGGER.trace("MECEmailServiceImpl :: prepareExcelDataList() :: START");
		// End string for row or cells.
		String endString = CommonConstants.COMMA;
		// Create blank row/cell
		String blankRow = endString;
		// To create new excel sheet row-cell data/result
		List<String> result = new ArrayList<String>();
		// Add blank row at row no. 1
		result.add(blankRow);
		// Add date at row no. 2
		result.add(MECCommonConstants.PARAM_DATE
				+ CommonConstants.SPACE
				+ CommonConstants.CHARACTER_COLON
				+ CommonConstants.SPACE
				+ mecCommonService.decreaseDateByDays(
						DateUtil.getCurrentDateInDDMMYYYY(), 1) + endString);
		// Add title at row no. 3
		result.add(FrameworkResourceLoaderUtil
				.getAsString(MECCommonConstants.MEC_EMAIL_XLS_TITLE)
				+ endString);
		// Add blank row at row no. 4
		result.add(blankRow);
		// Add cheque(s) deposit slip detail(s) header at row no. 5
		String cdsDtlsHeader = "Bank Name,Cheque No,Customer Name,Cheque Amount,Branch,Bank GL"
				+ endString;
		result.add(cdsDtlsHeader);
		// Add cheque(s) deposit slip detail(s) at row no. 6 to row no. Nth
		prepareCDSDtlsList(collectionDOs, result);
		// Add total no. of cheque(s) and amount at row no. (Nth + 1)
		String totalNosChq = "Total (" + collectionDOs.size() + " Cheque(s))";
		// It will calculate by formula
		String totalChqAmt = CommonConstants.HASH;
		result.add(CommonConstants.COMMA + CommonConstants.COMMA + totalNosChq
				+ CommonConstants.COMMA + totalChqAmt);
		// Add blank row at row no. (Nth + 2)
		result.add(blankRow);
		LOGGER.trace("MECEmailServiceImpl :: prepareExcelDataList() :: END");
		return result;
	}

	/**
	 * To prepare CDS details list to comma separated string
	 * 
	 * @param collectionDOs
	 * @param result
	 */
	private void prepareCDSDtlsList(List<CollectionDO> collectionDOs,
			List<String> result) {
		LOGGER.trace("MECEmailServiceImpl :: prepareCDSDtlsList() :: START");
		for (CollectionDO collectionDO : collectionDOs) {
			String stringResult = CommonConstants.EMPTY_STRING;
			// To append bank name
			stringResult += collectionDO.getBankName() + CommonConstants.COMMA;
			// To append cheque no
			stringResult += collectionDO.getChqNo() + CommonConstants.COMMA;
			// To append customer name
			if (!StringUtil.isNull(collectionDO.getCustomerDO())) {
				stringResult += collectionDO.getCustomerDO().getBusinessName()
						+ CommonConstants.COMMA;
			} else {
				stringResult += CommonConstants.SPACE + CommonConstants.COMMA;
			}
			// To append cheque amount
			stringResult += collectionDO.getTotalAmount()
					+ CommonConstants.COMMA;
			// To append branch
			if (!StringUtil.isNull(collectionDO.getCollectionOfficeDO())) {
				stringResult += collectionDO.getCollectionOfficeDO()
						.getOfficeName()
						+ CommonConstants.SPACE
						+ CommonConstants.HYPHEN
						+ CommonConstants.SPACE
						+ collectionDO.getCollectionOfficeDO().getOfficeCode()
						+ CommonConstants.COMMA;
			} else {
				stringResult += CommonConstants.SPACE + CommonConstants.COMMA;
			}
			// To append bank GL
			if (!StringUtil.isNull(collectionDO.getBankGLDO())) {
				stringResult += collectionDO.getBankGLDO().getGlDesc()
						+ CommonConstants.COMMA;
			} else {
				stringResult += CommonConstants.SPACE + CommonConstants.COMMA;
			}
			result.add(stringResult);
		}
		LOGGER.trace("MECEmailServiceImpl :: prepareCDSDtlsList() :: END");
	}

	/**
	 * To get body text message
	 * 
	 * @return body text message
	 * @exception Exception
	 */
	private String getBodyTextMsg() throws Exception {
		return FrameworkResourceLoaderUtil
				.getAsString(MECCommonConstants.MEC_EMAIL_BODY_TEXT_MSG)
				+ mecCommonService.decreaseDateByDays(
						DateUtil.getCurrentDateInDDMMYYYY(), 1)
				+ FrameworkResourceLoaderUtil
						.getAsString(MECCommonConstants.MEC_EMAIL_SIGNATURE)
				+ FrameworkResourceLoaderUtil
						.getAsString(MECCommonConstants.MEC_EMAIL_DO_NOT_REPLAY);
	}

	/**
	 * To format excel sheet as per format
	 * 
	 * @param excelWorkbook
	 * @param sheetName
	 * @param totalCDSRowCount
	 * @param noOfCols
	 * @exception CGBusinessException
	 */
	private void formatExcelWorkbook(HSSFWorkbook excelWorkbook,
			String sheetName, int totalCDSRowCount, int noOfCols)
			throws CGBusinessException {
		LOGGER.trace("MECEmailServiceImpl :: formatExcelWorkbook() :: START");
		// NOTE: Here, excel row no index starts from 0
		// so, it should <row no> - 1

		// To format date cell at row no. 2 - Date
		formatDateCell(excelWorkbook, sheetName, 1);

		// To format title cell at row no. 3 - Title
		formatTitleCell(excelWorkbook, sheetName, 2);
		// To add merged 6 column cells at row no. 3 - Title
		mergedCells(excelWorkbook, sheetName, 2, 2, 0, noOfCols - 1);

		// To set border at row no. 5 - Header
		formatCDSHeaderCell(excelWorkbook, sheetName, 4);

		// To set border to all CDS details row no 6 to <<no. of details list
		// size>> or <<nth row>> - CDS Details
		for (int n = 5; n < totalCDSRowCount + 5; n++) {
			setRowBorder(excelWorkbook, sheetName, n);
		}

		// To set border to row no <<nth>> + 1 - Total
		int nthRow = totalCDSRowCount + 5;
		String amountInWords = setTotalCell(excelWorkbook, sheetName, nthRow);

		// To resizes column(s) before formating rupees in word cell
		for (int k = 0; k < noOfCols; k++) {
			excelWorkbook.getSheet(sheetName).autoSizeColumn((short) k);
		}

		// Add amount in word cell at row no. (Nth + 3) at last moment
		String rupeesInWord = FrameworkResourceLoaderUtil
				.getAsString(MECCommonConstants.MEC_EMAIL_AMOUNT_IN_WORD)
				+ CommonConstants.SPACE
				+ CommonConstants.CHARACTER_COLON
				+ CommonConstants.SPACE + amountInWords;
		excelWorkbook.getSheet(sheetName).createRow(nthRow + 2).createCell(0)
				.setCellValue(rupeesInWord);

		// To format rupees in word cell at row no. (Nth + 3)
		formatRupeesInWordCells(excelWorkbook, sheetName, nthRow + 2);

		// To set all rows height (suggested 18.0 points or 24 pixels)
		// excelWorkbook.getSheet(sheetName).setDefaultRowHeightInPoints(18.0f);

		LOGGER.trace("MECEmailServiceImpl :: formatExcelWorkbook() :: END");
	}

	/**
	 * To merge cells for particular workbook
	 * 
	 * @param wb
	 * @param sheetName
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	private void mergedCells(HSSFWorkbook wb, String sheetName, int firstRow,
			int lastRow, int firstCol, int lastCol) {
		LOGGER.trace("MECEmailServiceImpl :: mergedCells() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
		LOGGER.trace("MECEmailServiceImpl :: mergedCells() :: END");
	}

	/**
	 * To format date cell as per excel sheet format
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 */
	private void formatDateCell(HSSFWorkbook wb, String sheetName, int rowIndex) {
		LOGGER.trace("MECEmailServiceImpl :: formatDateCell() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To create font and make it bold
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// To set cell style and font
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		// To set vertical align
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (!StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				cell.setCellStyle(cellStyle);
			}
		}
		LOGGER.trace("MECEmailServiceImpl :: formatDateCell() :: END");
	}

	/**
	 * To format title cell as per excel sheet format
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 */
	private void formatTitleCell(HSSFWorkbook wb, String sheetName, int rowIndex) {
		LOGGER.trace("MECEmailServiceImpl :: formatTitleCell() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To set vertical alignment
		CellStyle cellStyle = wb.createCellStyle();
		// To set horizontal align
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// To set vertical align
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// To create font and make it bold
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (!StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				cell.setCellStyle(cellStyle);
			}
		}
		LOGGER.trace("MECEmailServiceImpl :: formatTitleCell() :: END");
	}

	/**
	 * To set CDS header cells as per excel sheet format
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 */
	private void formatCDSHeaderCell(HSSFWorkbook wb, String sheetName,
			int rowIndex) {
		LOGGER.trace("MECEmailServiceImpl :: formatCDSHeaderCell() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To set horizontal alignment
		CellStyle cellStyle = wb.createCellStyle();
		// To create font and make it bold
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		// To set horizontal align
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// To set vertical align
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// To set border to cells
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (!StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				cell.setCellStyle(cellStyle);
			}
		}
		LOGGER.trace("MECEmailServiceImpl :: formatCDSHeaderCell() :: END");
	}

	/**
	 * To set border to CDS details row cells as per excel sheet format
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 */
	private void setRowBorder(HSSFWorkbook wb, String sheetName, int rowIndex) {
		LOGGER.trace("MECEmailServiceImpl :: setRowBorder() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To set border to cells
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		int cnt = 0;
		while (cells.hasNext()) {
			Cell cell = cells.next();
			// If blank then make it null to apply border
			if (StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				cell.setCellValue(cell.getStringCellValue().trim());
			}
			cell.setCellStyle(cellStyle);
			if (cnt == 0) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
			} else if (cnt == 1) {
				cell.setCellValue(Integer.parseInt(cell.getStringCellValue()));
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else if (cnt == 2) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
			} else if (cnt == 3) {
				cell.setCellValue(Double.parseDouble(cell.getStringCellValue()));
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cellStyle.setDataFormat((short) BuiltinFormats
						.getBuiltinFormat("0.00"));
				cell.setCellStyle(cellStyle);
			} else if (cnt == 4) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
			} else if (cnt == 5) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
			cnt++;
		}
		LOGGER.trace("MECEmailServiceImpl :: setRowBorder() :: END");
	}

	/**
	 * To format CDS details total (lower row(s)) cells as per excel sheet
	 * format
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 * @return amountInWords
	 */
	private String setTotalCell(HSSFWorkbook wb, String sheetName, int rowIndex)
			throws CGBusinessException {
		LOGGER.trace("MECEmailServiceImpl :: setTotalCell() :: START");
		String amountInWords = CommonConstants.EMPTY_STRING;
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To set border to cells
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// To create font and make it bold
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (!StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				if (cell.getStringCellValue().equalsIgnoreCase(
						CommonConstants.HASH)) {
					String strFormula = "SUM(D6:D" + rowIndex + ")";
					cell.setCellValue(0.0);
					cell.setCellType(Cell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);
					// Setting Date format like 0.00
					cellStyle.setDataFormat((short) BuiltinFormats
							.getBuiltinFormat("0.00"));
					cell.setCellStyle(cellStyle);

					// To Evaluate formula and getting cell value and convert in
					// to words
					FormulaEvaluator evaluator = wb.getCreationHelper()
							.createFormulaEvaluator();
					CellValue cellValue = evaluator.evaluate(cell);
					String amtStr = CommonConstants.EMPTY_STRING
							+ cellValue.getNumberValue();
					// Split amount string with dot literal its double backward
					// slash and dot character in regex
					String[] amount = amtStr.split("\\.");
					// If rupees is not null then convert it into words
					if (!StringUtil.isEmptyLong(Long.parseLong(amount[0]))) {
						amountInWords = mecCommonService.getNumberInWords(Long
								.parseLong(amount[0])) + " rupees";
					}
					// If paise is not null then convert it into words
					if (!StringUtil.isEmptyLong(Long.parseLong(amount[1]))) {
						amountInWords += " and "
								+ mecCommonService.getNumberInWords(Long
										.parseLong(amount[1])) + " paise";
					}
					// To convert amount in words to upper case
					amountInWords = amountInWords.toUpperCase();
				}
				cell.setCellStyle(cellStyle);
			}
		}
		LOGGER.trace("MECEmailServiceImpl :: setTotalCell() :: END");
		return amountInWords;
	}

	/**
	 * To format Rupees In Word cells as per excel sheet
	 * 
	 * @param wb
	 * @param sheetName
	 * @param rowIndex
	 */
	private void formatRupeesInWordCells(HSSFWorkbook wb, String sheetName,
			int rowIndex) {
		LOGGER.trace("MECEmailServiceImpl :: formatRupeesInWordCells() :: START");
		HSSFSheet sheet = wb.getSheet(sheetName);
		HSSFRow row = sheet.getRow(rowIndex);
		// To set border to cells
		CellStyle cellStyle = wb.createCellStyle();
		// To create font and make it bold
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		// To set vertical align
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// To iterates each cell of rows
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (!StringUtil.isStringEmpty(cell.getStringCellValue().trim())) {
				cell.setCellStyle(cellStyle);
			}
		}
		LOGGER.trace("MECEmailServiceImpl :: formatRupeesInWordCells() :: END");
	}

}
