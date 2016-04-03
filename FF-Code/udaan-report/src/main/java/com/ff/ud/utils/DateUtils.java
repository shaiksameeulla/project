package com.ff.ud.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class to get formatted date and time
 * @author Kiran
 * @since March 26, 2012
 * @version 1.0
 */
public class DateUtils {

	public static final String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
	public static final String DATE_FORMAT_MM_yyyy = "MM/YYYY";
	public static final String DATE_FORMAT_dd_MM_yyyy = "dd/MM/yyyy";
	public static final String JAVA_SQL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HHmmss";
	public static final String FORMAT_yyyy_MM_dd_hh_mm_ss="yyyy-MM-dd hh:mm:ss";
	public static final String DATE_FORMAT_ddMMyyyy = "ddMMyyyy";
	public static final String DATE_FORMAT_ddMMyyyyHHMMSS = "ddMMyyyyHHMMSS";
	 
	public static Timestamp getCurrentTimeStamp(){
			return (new Timestamp(System.currentTimeMillis()));
		}
	/**
	 * Get the current date in the format provided in the argument
	 * 
	 * @param dateFormat
	 * @return formatted Date in String
	 */
	public static String now(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * Fetch the Current Time in the format "HHmmss"
	 */
	public static String getCurrentTime() {
	    return now(TIME_FORMAT);
	}

	/**
	 * Fetch the Current Date in format "yyyyMMdd" 
	 */
	public static String getCurrentDate() {
		return now(DATE_FORMAT_dd_MM_yyyy);
	}
	
	/**
	 * Fetch the Current Date in format "yyyy-MM-dd" 
	 */
	public static String getCurrentDateInSQLFormat() {
		return now(JAVA_SQL_DATE_FORMAT);
	}
	
	/** 
	 * Format String to represent Time in the given format
	 *  
	 * @param Time in String
	 * @return Formatted String
	 */
	public static String formatTime(String timeString, String srcFormat, String trgFormat) {
		
		Date timeObject = null;
		String newTimeString = null;
		
		try {
			timeObject = new SimpleDateFormat(srcFormat).parse(timeString);
			newTimeString = new SimpleDateFormat(trgFormat).format(timeObject); 
		} 
		catch(Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return newTimeString;
	}
	
	/**
	 * Convert date string from source format to given target format 
	 * 
	 * @param dateString
	 * @param srcFormat
	 * @param targetFormat
	 * @return
	 */
	public static String formatDate(String dateString, String srcFormat, String targetFormat) {
		
		Date dateObject = null;
		String newDateString = null;
		
		try {
			dateObject = new SimpleDateFormat(srcFormat).parse(dateString);
			newDateString = new SimpleDateFormat(targetFormat).format(dateObject); 
		} 
		catch(Exception e) {
			return null;
		}
		return newDateString;
	}

	/**
	 * Convert date string to date object  
	 * 
	 * @param dateString
	 * @param date pattern of the string
	 * @return Date
	 */
public static Date getUtilDate(String day,String month,String year) {
	String date=day+"/"+month+"/"+year;
	return getDateFromString(date,DATE_FORMAT_dd_MM_yyyy);
	}
	
	public static Date getDateFromString(String dateString, String pattern) {
		
		if(null==dateString){
			return null;
		}
		
		/*Date dateObject = null;
		try {
			dateObject = new Date(new SimpleDateFormat(pattern).parse(dateString).getTime());
		} 
		catch(Exception e) {
			return null;
		}
		return dateObject;*/
		
		String strDate = dateString;
		SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(pattern);
		java.util.Date utilDate = null;
		try {
			 utilDate = new java.util.Date(dateFormat.parse(strDate).getTime());
			 //System.out.println(utilDate);
		} catch (Exception e) {
			return utilDate;
		}
		return utilDate;
		
	}
	
	public static String getUtilDateFormatMMYY(String month,String year) {
		String date=month+"-"+year;
		return date;
		}

	//Method to convert  java.util.Date to java.sql.Date
	
	public static Date UtilDateToSqlDate(java.util.Date utilDate) {
		  Date sqlDate = null;
		  if (utilDate != null)  sqlDate = new Date(utilDate.getTime());
		  return sqlDate;
	 }

	// Method to convert java.sql.Date to java.util.Date

	 public static java.util.Date SqlDateToUtilDate(Date sqlDate) {
		  java.util.Date utilDate = null;
		  if (sqlDate != null)  utilDate = new java.util.Date(sqlDate.getTime());
		  return utilDate;
	 }
	 
	 public static boolean isLessThanOrEqualNow(Date sqlDate) {
			
		 java.util.Date currentDate = DateUtils.getDateFromString(DateUtils.getCurrentDateInSQLFormat(), 
					DateUtils.JAVA_SQL_DATE_FORMAT);
			
		 if(sqlDate.compareTo(currentDate) >= 0){
			 return true;
		 }else{
			return false; 
		 }
			
	 }
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return difference in days
	 * Date currentDate=Calendar.getInstance().getTime();- to get the current date in util
	 */
	/*public static int getDiffrenceBetweenTwoDatesInDays(Date startDate,Date endDate){
	    
        DateTime endDateJoda=new DateTime(endDate);
        DateTime startJodaDate=new DateTime(startDate);
        
		return Days.daysBetween(new DateMidnight(startJodaDate), new DateMidnight(endDateJoda)).getDays();
		
	}*/
	 
	
	/**
	 * Return String date from Date Object;	
	 * @param utilDate
	 * @param format
	 * @return string
	 */
	public static String getDateToString(java.util.Date utilDate, String format) {
			  String date = "";
			  SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format);
			  if (utilDate != null) {
				  date = dateFormat.format(utilDate);
				  System.out.println("Date :"+date);
			  }
			  return date;
	 }
	 
	 public static void  main(String[] args) {
		 
	
		  
	}
	
	 
}
