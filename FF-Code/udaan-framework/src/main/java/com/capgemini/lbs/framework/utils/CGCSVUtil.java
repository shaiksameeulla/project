package com.capgemini.lbs.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;

/**
 * @author mohammal
 *
 */
public class CGCSVUtil {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CGCSVUtil.class);
	
	/**
	 * This method will be used to get data of a csv file. This will return
	 * a list of list. Each inner list represent a row of csv file
	 * @param url is the fully qualified name of file
	 * @return list of rows<list>
	 * @throws Exception if file not available on the specified path.
	 */
	@SuppressWarnings("unused")
	public static List<List> getAllRowsFromCSV(String url) throws Exception {
		//This list contains values of the rows
		List<List> cvsRowsValues = new ArrayList<List>();
		
		File csvFile = new File(url);
		BufferedReader bufRdr  = new BufferedReader(new FileReader(csvFile));
		String line = null;
		int row = 0;
		int col = 0;
		//read each line of text file
		while((line = bufRdr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line,",");
			//this list contains single row values
			List<String> rowValues = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				//get next token and store it in the array
				rowValues.add(st.nextToken());
				col++;
			}
			cvsRowsValues.add(rowValues);
			row++;
		}
		bufRdr.close();
		return cvsRowsValues;
	}
	
	/**
	 * This method will be used to get the first row data of the csv file.
	 * This will return a list string.
	 * @param url is the fully qualified name of the file
	 * @return row values as an list of string
	 * @throws Exception if file not available on the specified path.
	 */
	@SuppressWarnings("unused")
	public static List<String> getFirstRowFromCSV(String url) throws CGBusinessException {
		List<String> rowValues = new ArrayList<String>();
		File csvFile = new File(url);
		BufferedReader bufRdr=null;
		try {
			bufRdr = new BufferedReader(new FileReader(csvFile));
			String line = null;
			int col = 0;
			//read each line of text file
			line = bufRdr.readLine();
			if (line != null) {
				StringTokenizer st = new StringTokenizer(line,",");
				while (st.hasMoreTokens()) {
					rowValues.add(st.nextToken());
					col++;
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("CGCSVUtil ::::getFirstRowFromCSV :: FileNotFoundException",e);
			throw new CGBusinessException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CGBusinessException(e.getMessage());
		}finally{
			if(bufRdr!=null){
				try {
					bufRdr.close();
				} catch (IOException e) {
					LOGGER.error("CGCSVUtil ::::getFirstRowFromCSV :: IOException",e);
				}
			}
		}
		
		
		return rowValues;
	}
	/**
	 * @param in
	 * @return
	 * @throws CGBusinessException
	 */
	public static List<String> getFirstRowFromCSV(InputStream in) throws CGBusinessException {
		List<String> rowValues = new ArrayList<String>();
		try {
//			StringBuilder out = new StringBuilder();
//			final char[] buffer = new char[0x10000];

//			Reader reader  = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;
			  //Read File Line By Line
			 while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 rowValues.add(strLine);
			  }
//			int read;
//			do {
//			  read = reader.read(buffer, 0, buffer.length);
//			  if (read>0) {
//			    out.append(buffer, 0, read);
//			  }
//			} while (read>=0);
		} catch (UnsupportedEncodingException e) { 
			LOGGER.error("CGCSVUtil ::::getFirstRowFromCSV :: UnsupportedEncodingException",e);
		} catch (IOException e) {
			LOGGER.error("CGCSVUtil ::::getFirstRowFromCSV :: IOException",e);
		}
 
		return rowValues;
	}
	
}

