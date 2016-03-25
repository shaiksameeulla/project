/**
 * 
 */
package com.capgemini.lbs.framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;

/**
 * @author uchauhan
 * 
 */
public class CGExcelUploadUtil {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CGExcelUploadUtil.class);

	/**
	 * @param l1
	 * @return
	 * @throws CGBusinessException
	 */
	public static XSSFWorkbook CreateExcelFile(List<List> l1)
			throws CGBusinessException {
		LOGGER.debug("CGExcelUploadUtil::CreateExcelFile::START------------>:::::::");
		int rownum = 0;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xssfWorkbook.createSheet();

		// Font setting for sheet.
		XSSFFont font = xssfWorkbook.createFont();
		font.setBoldweight((short) 700);
		sheet.setDefaultColumnWidth(30);

		// Create Styles for sheet.
		XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
		// headerStyle.setFillForegroundColor(LAVENDER.index);
		headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(font);
		try {
			for (int j = 0; j < l1.size(); j++) {
				Row row = sheet.createRow(rownum);
				List<String> l2 = l1.get(j);
				// Getting the index where error information has to be written
				/*
				 * if(j==0) { errorIndex = l2.size()-1; } else { errorMessage =
				 * l2.get(l2.size()-1); }
				 */
				for (int k = 0; k < l2.size(); k++) {
					Cell cell = row.createCell(k);
					try {
						cell.setCellValue(l2.get(k));
						/*
						 * if(k== l2.size()-1) {
						 * cell.setCellValue(errorMessage); }
						 */
					} catch (RuntimeException e) {
						// LOGGER.error("CGExcelUploadUtil::CreateExcelFile::ERROR :: ------------>:::::::"+
						// l2.get(k));
						LOGGER.error(
								"CGExcelUploadUtil::CreateExcelFile::ERROR :: ------------>:::::::",
								e);
					}

				}
				rownum++;
			}
		} catch (Exception e) {
			LOGGER.debug("CGExcelUploadUtil::CreateExcelFile::ERROR :: ------------>:::::::",e);
		}
		LOGGER.debug("CGExcelUploadUtil::CreateExcelFile::END------------>:::::::");
		return xssfWorkbook;
	}

	/**
	 * @param url
	 * @param xlsFile
	 * @return
	 * @throws CGBusinessException
	 */
	public static List<List> getAllRowsValues(String url, FormFile xlsFile)
			throws CGBusinessException {
		return getAllRowsValues(url, null, xlsFile);
	}

	/**
	 * This method will be used to get data of an excel sheet. This will return
	 * a list of list. Each inner list represent a row of excel sheet
	 * 
	 * @param url
	 *            is the fully qualified name of excel sheet.
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */

	/**
	 * @param url
	 * @param sheetName
	 * @param xlsFile
	 * @return
	 * @throws CGBusinessException
	 */
	/*
	 * public static List<List> getAllRowsValues(String url, String sheetName,
	 * FormFile xlsFile) throws CGBusinessException {
	 * LOGGER.debug("CGExcelUploadUtil::getAllRowsValues::START------------>:::::::"
	 * ); List<List> excellData = new ArrayList<List>(); FileInputStream fis =
	 * null; try { InputStream is = xlsFile.getInputStream(); XSSFWorkbook
	 * workbook = new XSSFWorkbook(is); is.close(); XSSFSheet sheet =
	 * workbook.getSheetAt(0); int curr_idx = 0; int max_idx = 0; int
	 * weightRowRange = sheet.getLastRowNum(); for (int cntr = 0; cntr <=
	 * weightRowRange; cntr++) { Row row = sheet.getRow(cntr); curr_idx =
	 * row.getLastCellNum(); if (curr_idx > max_idx) { max_idx = curr_idx; }
	 * List<String> excellRow = getRowContent(row, max_idx);
	 * excellData.add(excellRow); }
	 * 
	 * } catch (FileNotFoundException e) { LOGGER.debug(
	 * "CGExcelUploadUtil::getAllRowsValues::ERROR :: ------------>:::::::");
	 * CGBusinessException(FrameworkConstants.FILE_NOT_FOUND); } catch
	 * (IOException e) { LOGGER.debug(
	 * "CGExcelUploadUtil::getAllRowsValues::ERROR :: ------------>:::::::");
	 * 
	 * LOGGER.debug("CGExcelUploadUtil::getAllRowsValues::END------------>:::::::"
	 * ); return excellData; }
	 */
	public static List<List> getAllRowsValues(String url, String sheetName,
			FormFile xlsFile) throws CGBusinessException {
		LOGGER.debug("CGExcelUploadUtil::getAllRowsValues::START------------>:::::::");
		List<List> excellData = null;
		InputStream is = null;
		InputStream wrappedStream=null;
		try {

			// XSSF
			is = xlsFile.getInputStream();
			 wrappedStream = POIFSFileSystem
					.createNonClosingInputStream(is);
			String fileName = xlsFile.getFileName(); 
			boolean isXlsFile = fileName.endsWith(".xls");
			Workbook workbook = null;
			if(isXlsFile) {
				workbook = new HSSFWorkbook(wrappedStream);
			} else {
				workbook = new XSSFWorkbook(wrappedStream);
			}
			
			int numberOfColumn = 0;
			Sheet sheet = workbook.getSheetAt(0);
			int totalRows = sheet.getPhysicalNumberOfRows();
			excellData = new ArrayList<List>(sheet.getPhysicalNumberOfRows());
			if (!StringUtil.isEmptyInteger(totalRows)) {
				for (int i = 0; i < totalRows; i++) {
					Row row = sheet.getRow(i);
					if (numberOfColumn == 0)
						numberOfColumn = row.getPhysicalNumberOfCells();
					List<String> excellRow = getRowContent(row, numberOfColumn);
					if (!CGCollectionUtils.isEmpty(excellRow))
						excellData.add(excellRow);
				}
			}

		} catch (FileNotFoundException e) {
			LOGGER.error("CGExcelUploadUtil::getAllRowsValues::ERROR(FileNotFoundException) :: ------------>:::::::",e);
			throw new CGBusinessException(FrameworkConstants.FILE_NOT_FOUND);
		} catch (IOException e) {
			LOGGER.error("CGExcelUploadUtil::getAllRowsValues::ERROR(IOException) :: ------------>:::::::",e);
		} catch (Exception e) {
			LOGGER.error("CGExcelUploadUtil::getAllRowsValues::ERROR (Exception):: ------------>:::::::",e);
		} finally {
			if(wrappedStream!=null){
				try {
					wrappedStream.close();
				} catch (IOException e) {
					LOGGER.error("CGExcelUploadUtil::getAllRowsValues::IOException(wrappedStream)::ERROR :: ------------>:::::::",e);
				}
			}
			if (is != null) {
				try {
					
					is.close();
				} catch (IOException e) {
					LOGGER.error("CGExcelUploadUtil::getAllRowsValues::IOException(is)::ERROR :: ------------>:::::::",e);
				}
			}
		}
		LOGGER.debug("CGExcelUploadUtil::getAllRowsValues::END------------>:::::::");
		return excellData;
	}

	/*
	 * private void getAllRow(String url, String sheetName, FormFile xlsFile) {
	 * 
	 * // read the xlsx file XSSFWorkbook xssfWb = new XSSFWorkbook(opcPackage);
	 * SXSSFWorkbook wb = new SXSSFWorkbook(xssfWb, 100); SimpleXLSXWorkbook =
	 * new SimpleXLSXWorkbook(new File("C:/test.xlsx"));
	 * 
	 * HSSFWorkbook hsfWorkbook = new HSSFWorkbook();
	 * 
	 * org.apache.poi.ss.usermodel.Sheet hsfSheet = hsfWorkbook.createSheet();
	 * 
	 * Sheet sheetToRead = workbook.getSheet(0, false);
	 * 
	 * SheetRowReader reader = sheetToRead.newReader(); Cell[] row; int rowPos =
	 * 0; while ((row = reader.readRow()) != null) {
	 * org.apache.poi.ss.usermodel.Row hfsRow = hsfSheet.createRow(rowPos); int
	 * cellPos = 0; for (Cell cell : row) { if(cell != null){
	 * org.apache.poi.ss.usermodel.Cell hfsCell = hfsRow.createCell(cellPos);
	 * hfsCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
	 * hfsCell.setCellValue(cell.getValue()); } cellPos++; } rowPos++; } return
	 * hsfSheet; }
	 */

	/**
	 * @param row
	 * @param max_idx
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<String> getRowContent(Row row, int max_idx) {
		LOGGER.debug("CGExcelUploadUtil::getRowContent::START------------>:::::::");
		List<String> rowContents = new ArrayList<String>();
		int previousCell = -1;
		int currentCell = 0;
		String value = "";
		boolean isEmpty = true;
		if(!StringUtil.isNull(row) && !StringUtil.isEmptyInteger(max_idx)){
		for (int cn = 0; cn < max_idx; cn++) {
			Cell cell = row.getCell(cn, row.CREATE_NULL_AS_BLANK);
			value = getCellValue(cell);
			if (!StringUtil.isStringEmpty(value)) {
				isEmpty = false;
			}
			rowContents.add(value);
			previousCell = currentCell;
		}
		}
		LOGGER.debug("CGExcelUploadUtil::getRowContent::END------------>:::::::");
		return isEmpty ? null : rowContents;
	}

	/**
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		LOGGER.debug("CGExcelUploadUtil::getCellValue::START------------>:::::::");
		String value = "";

		if (cell == null)
			return value;
		if (!StringUtil.isNull(cell.getCellType())) {
			switch (cell.getCellType()) {

			case XSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				double num = cell.getNumericCellValue();
				DecimalFormat pattern = new DecimalFormat("");
				NumberFormat testNumberFormat = NumberFormat
						.getNumberInstance();
				value = testNumberFormat.format(num);
				try {
					value = pattern.parse(value) + "";
				} catch (ParseException e) {
					LOGGER.error("CGExcelUploadUtil::getCellValue::ParseException:::Error::",e);
				}
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;

			case XSSFCell.CELL_TYPE_BLANK:
				value = "";
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
				break;
			default:
				break;
			}
		}
		LOGGER.debug("CGExcelUploadUtil::getCellValue::END------------>:::::::");
		return value;
	}

	@SuppressWarnings({ "static-access" })
	public static boolean uploadFile(String dirPath, String fileName,
			FormFile formFile) throws IOException {
		LOGGER.trace("CGExcelUploadUtil :: uploadFile() :: START");
		boolean upload = Boolean.FALSE;
		FileOutputStream outputStream = null;
		try {
			File file = new File(dirPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String path = dirPath + file.separator + fileName;
			file = new File(path);
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(formFile.getFileData());
			upload = Boolean.TRUE;
		} finally {
			outputStream.close();
		}
		LOGGER.trace("CGExcelUploadUtil :: uploadFile() :: END");
		return upload;
	}

}
