package com.capgemini.lbs.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.MessageDetail;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;

/**
 * @author mohammal
 * 
 */
public class CGExcelUtil {

	private final static Logger logger = Logger.getLogger(CGExcelUtil.class);
	/**
	 * This method will be used to get data of an excel sheet. This will return
	 * a list of list. Each inner list represent a row of excel sheet
	 * 
	 * @param url
	 *            is the fully qualified name of excel sheet.
	 * @return list of rows<list>
	 * @throws Exception
	 *             if file not available on the specified path.
	 */
	public static List<List> getAllRowsValues(String url) throws CGBusinessException {
		return getAllRowsValues(url, null);
	}
	
	
	
	
	/**
	 * @param is
	 * @return
	 * @throws CGBusinessException
	 */
	public static List<List> getAllRowsValues(InputStream is) throws CGBusinessException {
		List<List> excellData = new ArrayList<List>();
		try {
			Workbook  workbook = WorkbookFactory.create(is);
			Sheet  sheet = workbook.getSheetAt(0);
			for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
				Row row = rit.next();
				List<String> excellRow = getRowContent(row);
				excellData.add(excellRow);
			}

		} catch (IOException e) {
			logger.error("CGExcelUtil::getAllRowsValues::error =IOException ",e);
		} catch (InvalidFormatException e) {
			logger.error("CGExcelUtil::getAllRowsValues::error =InvalidFormatException " ,e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("CGExcelUtil::getAllRowsValues::error = IOException" ,e);
				}
			}
		}
		return excellData;
	}
	
	
	/**
	 * This method will be used to get data of an excel sheet. This will return
	 * a list of list. Each inner list represent a row of excel sheet
	 * @param url is the fully qualified name of excel sheet.
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	
	public static List<List> getAllRowsValues(String url, String sheetName) throws CGBusinessException {
		// String filename = "datecelltype.xls";
		List<List> excellData = new ArrayList<List>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(url);

			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = sheetName == null || sheetName.equals("") ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
			for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
				Row row = rit.next();
				List<String> excellRow = getRowContent(row);
				excellData.add(excellRow);
			}

		} catch (FileNotFoundException e) {
			logger.error("CGExcelUtil::getAllRowsValues::error = FileNotFoundException" ,e);
			throw new CGBusinessException(FrameworkConstants.FILE_NOT_FOUND);
		} catch (IOException e) {
			logger.error("CGExcelUtil::getAllRowsValues::error = IOException" ,e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("CGExcelUtil::getAllRowsValues::error = IOException" ,e);
				}
			}
		}
		return excellData;
	}
	
	/**
	 * @param url
	 * @param column
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllRowsValues(String url, int column) throws Exception {
		// String filename = "datecelltype.xls";
		List<String> excellData = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(url);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
				Row row = rit.next();
				String excellRow = getColumnContent(row, column);
				excellData.add(excellRow);
			}
		} catch (FileNotFoundException e) {
			logger.error("CGExcelUtil::getAllRowsValues::error = FileNotFoundException" ,e);
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return excellData;
	}

	/**
	 * @param url
	 * @param sheetName
	 * @param column
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllRowsValues(String url, String sheetName, int column) throws  CGBusinessException {
		// String filename = "datecelltype.xls";
		List<String> excellData = new ArrayList<String>();
		FileInputStream fis = null;
		
			

			HSSFWorkbook workbook;
			try {
				fis = new FileInputStream(url);
				workbook = new HSSFWorkbook(fis);
				HSSFSheet sheet = sheetName == null || sheetName.equals("") ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
				logger.debug("CGExcelUtil::getAllRowsValues::sheet = " + sheet);
				if(sheet == null)
					throw new CGBusinessException("Unknown excell sheet name");
				logger.debug("CGExcelUtil::getAllRowsValues:: after exception ");
				for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
					Row row = rit.next();
					String columnContent = getColumnContent(row, column);
					excellData.add(columnContent);
				}
				fis.close();
				
			} catch (IOException e) {
				logger.error("CGExcelUtil::getAllRowsValues::error = " + e.getMessage());
				//FIXME exception handling
				MessageDetail messDetails = new MessageDetail();
				messDetails.setMessageType(MessageType.Error);
				messDetails.setMessageKey("");
				messDetails.setMessageDescription("CGExcelUtil::getAllRowsValues::IOException");
				
				MessageWrapper messWrapper = new MessageWrapper();
				messWrapper.addMessageDetail(messDetails);
				
				
				throw new CGBusinessException(messWrapper);
			}
			
		
		return excellData;
	}
	
	
	/**
	 * This method will be used to get the first row data of an excel sheet.
	 * This will return a list string.
	 * 
	 * @param url
	 *            is the fully qualified name of excel sheet.
	 * @return row values as an list of string
	 * @throws Exception
	 *             if file not available on the specified path.
	 */
	public static List<String> getFirstRowValues(String url) throws Exception {
		List<String> firstRow = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(url);

			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			firstRow = getRowContent(row);
		} catch (FileNotFoundException e) {
			logger.error("CGExcelUtil::getFirstRowValues::error = FileNotFoundException" ,e);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return firstRow;
	}
	
	/**
	 * @param comaSepratedRowString
	 * @return
	 */
	public static HSSFWorkbook writeToWorkbook(String sheetName,
			List<String> comaSepratedCellString) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = sheetName != null ? wb.createSheet(sheetName) : wb
				.createSheet();
		int rowNumber = 0;
		for (String row : comaSepratedCellString) {
			HSSFRow rowA = sheet1.createRow(rowNumber);
			String[] cellContents = row.split(",");
			int cellNumber = 0;
			for (String cellValue : cellContents) {
				HSSFCell cellA = rowA.createCell(cellNumber);
				cellA.setCellValue(new HSSFRichTextString(cellValue));
				cellNumber++;
			}
			rowNumber++;
		}
		return wb;
	}
	
	/**
	 * @param comaSepratedCellString
	 * @return
	 */
	public static HSSFWorkbook writeToWorkbook(List<String> comaSepratedCellString) {
		return writeToWorkbook(null, comaSepratedCellString);
	}
	
	
	/**
	 * @param row
	 * @return
	 */
	private static List<String> getRowContent(Row row) {
		List<String> rowContents = new ArrayList<String>();
		Iterator<Cell> itr = row.cellIterator();
		while (itr.hasNext()) {
			Cell cell = itr.next();
			String value = getCellValue(cell);
			rowContents.add(value);
		}
		return rowContents;
	}
	
	/**
	 * @param row
	 * @param column
	 * @return
	 */
	private static String getColumnContent(Row row, int column) {
		Cell cell = row.getCell(column);
		return getCellValue(cell);
	}
	
	/**
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {

		String value = "";

		if (cell == null)
			return value;

		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			double num = cell.getNumericCellValue();
			DecimalFormat pattern = new DecimalFormat("");
			NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
			value = testNumberFormat.format(num);
			try {
				value = pattern.parse(value) + "";
			} catch (ParseException e) {
				logger.error("CGExcelUtil::getCellValue::error = ParseException" ,e);
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue() + "";
			break;

		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		default:
		}
		return value;
	}
	
	// added by Hari for generating Excelsheet. 
	
	public static HSSFWorkbook convertDataIntoExcel(List headerData, List resultDataList,String workSheetName,String functionality) throws CGBusinessException {
			if(resultDataList != null && resultDataList.size() > 0){
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFCellStyle cs = wb.createCellStyle();
				HSSFSheet sheet = wb.createSheet(workSheetName);
				HSSFCell cell;
				HSSFRow row;
				HSSFFont f = wb.createFont();
							
	
				f.setFontHeightInPoints((short) 10);
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("ARIAL");
				cs.setFont(f);
				
				row = sheet.createRow(0);
				cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				if(headerData != null && headerData.size() > 0){
					for (int i = 0; i < headerData.size(); i++) {
						sheet.setColumnWidth((short) i, (short) (90 * 50));
						cell = row.createCell( (short) i);
						cs.setWrapText(true);
						cell.setCellStyle(cs);
						cell.setCellValue(headerData.get(i).toString());
					}
				}
				
				if(!functionality.equalsIgnoreCase("Transfer")){
					for(int j = 0; resultDataList != null && j < resultDataList.size(); j++) {
						row = sheet.createRow(j+1);
						List resultDataSubList = (List)resultDataList.get(j);		
						for(int k = 0; resultDataSubList != null && k < resultDataSubList.size(); k++) {
							cell = row.createCell( (short) k);
							cell.setCellValue((String)resultDataSubList.get(k));
						}
						
					}
				}else{
					for(int j = 1; resultDataList != null && j < resultDataList.size(); j++) {
						row = sheet.createRow(j);
						List resultDataSubList = (List)resultDataList.get(j);		
						for(int k = 0; resultDataSubList != null && k < resultDataSubList.size(); k++) {
							cell = row.createCell( (short) k);
							cell.setCellValue((String)resultDataSubList.get(k));
						}
						
					}
				}
				
			return wb;
		}else{
			throw new CGBusinessException("Data not found");
		}
	}
	
	
	/**
	 * @param cell
	 * @return
	 */
	public static String getCellValueString(Cell cell) {
		String value = CGExcelUtil.getCellValue(cell);
		return value;
	}
	
	/**
	 * @param comaSepratedRowString
	 * @return
	 */
	public static HSSFWorkbook writeToWrkbook(String cellString) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("temp");
		int rowNumber = 0;
//		for(String row : comaSepratedCellString) {
			HSSFRow rowA = sheet1.createRow(rowNumber);
			String[] cellContents = cellString.split(",");
			int cellNumber = 0;
			for (String cellValue : cellContents) {
				HSSFCell cellA = rowA.createCell(cellNumber);
				cellA.setCellValue(new HSSFRichTextString(cellValue));
				cellNumber++;
			}
//			rowNumber++;
//		}
		return wb;
	}
	
}
