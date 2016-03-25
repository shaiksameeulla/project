/*
 * @author 
 */
package com.capgemini.lbs.framework.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * The Class DateUtil.
 * 
 * @author mohammes
 */

public abstract class DateUtil {

	 public final static long SECOND_MILLIS = 1000;
	    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
	    public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
	    public final static long DAY_MILLIS = HOUR_MILLIS*24;
	    public final static long YEAR_MILLIS = DAY_MILLIS*365;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateUtil.class);

	/*
	 * This method will parse the String into date object into given format
	 */

	/**
	 * String to date formatter.
	 * 
	 * @param sourceDate
	 *            the source date
	 * @param formatter
	 *            the formatter
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date stringToDateFormatter(String sourceDate, String formatter) {

		Date convertedDate = null;
		SimpleDateFormat sdfOutput = new SimpleDateFormat(formatter);
		try {
			convertedDate = sdfOutput.parse(sourceDate);
		} catch (ParseException e) {
			LOGGER.error("DateUtil::stringToDateFormatter::error " ,e);
		}
		return convertedDate;
	}
	public static String dateToStringFormatter(Date sourceDate, String formatter) {
		String convertedDateStr = null;
		SimpleDateFormat sdfOutput = new SimpleDateFormat(formatter);
		try {
			convertedDateStr = sdfOutput.format(sourceDate);
		} catch (Exception e) {
			LOGGER.error("DateUtil::dateToStringFormatter::error " ,e);
		}
		return convertedDateStr;
	}
	/*
	 * This will format the date into default format ie. DD-MM-YYY
	 */

	/**
	 * String to ddmmyyyy format.
	 * 
	 * @param sourceDate
	 *            the source date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date stringToDDMMYYYYFormat(String sourceDate) {

		Date convertedDate;

		//convertedDate = stringToDateFormatter(sourceDate, "dd-MM-yyyy");
		convertedDate = slashDelimitedstringToDDMMYYYYFormat(sourceDate);
		return convertedDate;
	}

	/**
	 * String to ddmmyyyy format.
	 * 
	 * @param sourceDate
	 *            the source date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date slashDelimitedstringToDDMMYYYYFormat(String sourceDate) {

		Date convertedDate = null;
		if(!StringUtil.isStringEmpty(sourceDate) && sourceDate.contains(FrameworkConstants.CHARACTER_HYPHEN)){
			convertedDate = stringToDateFormatter(sourceDate, "dd-MM-yyyy");
		}else if(!StringUtil.isStringEmpty(sourceDate)&& sourceDate.contains(FrameworkConstants.FORWARD_SLASH)){
			convertedDate = stringToDateFormatter(sourceDate, "dd/MM/yyyy");
		}
		return convertedDate;
	}
	
	/**
	 * String to ddmmyyyy format.
	 * 
	 * @param sourceDate
	 *            the source date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseStringDateToDDMMYYYYHHMMFormat(String sourceDate) {

		Date convertedDate = null;
		if(!StringUtil.isStringEmpty(sourceDate) && sourceDate.contains(FrameworkConstants.CHARACTER_HYPHEN)){
			convertedDate = stringToDateFormatter(sourceDate, FrameworkConstants.DDMMYYYYHHMM_HYPHEN_FORMAT);
		}else if(!StringUtil.isStringEmpty(sourceDate)&& sourceDate.contains(FrameworkConstants.FORWARD_SLASH)){
			convertedDate = stringToDateFormatter(sourceDate, FrameworkConstants.DDMMYYYYHHMM_SLASH_FORMAT);
		}
		return convertedDate;
	}
	
	public static Date parseStringDateToDDMMYYYYHHMMSSFormat(String sourceDate) {

		Date convertedDate = null;
		if(!StringUtil.isStringEmpty(sourceDate) && sourceDate.contains(FrameworkConstants.CHARACTER_HYPHEN)){
			convertedDate = stringToDateFormatter(sourceDate, FrameworkConstants.DDMMYYYYHHMMSS_HYPHEN_FORMAT);
		}else if(!StringUtil.isStringEmpty(sourceDate)&& sourceDate.contains(FrameworkConstants.FORWARD_SLASH)){
			convertedDate = stringToDateFormatter(sourceDate, FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT);
		}
		return convertedDate;
	}

	/**
	 * This method will be used to convert java.util.Date to String in
	 * dd-mm-yyyy formate
	 * 
	 * @param date
	 *            object to which you want to convert
	 * @return String representation of date object in dd-mm-yyyy format
	 */
	public static String getDDMMYYYYTimestampString(Timestamp date) {
		String strDate = null;
		String dateFormat = FrameworkConstants.DD_MM_YYYY_HYPHEN_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		strDate = sdf.format(date).toString();
		return strDate;
	}
	public static String getDDMMStringFromDate(Date date) {
		String strDate = null;
		String dateFormat = FrameworkConstants.DD_MM_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		strDate = sdf.format(date).toString();
		return strDate;
	}

	/**
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getTimestampFromString(String date, String format)
			throws ParseException {
		Date convertedDate = getDateFromString(date, format);
		return new Timestamp(convertedDate.getTime());
	}

	/**
	 * @param date
	 * @param format
	 * @return java.util.Date
	 * @throws Exception
	 */
	public static Date getDateFromString(String date, String format) {
		Date convertedDate = null;
		try {
			convertedDate = stringToDateFormatter(date,format);
		} catch (Exception e) {
			LOGGER.error("DateUtil::getDateFromString::error " ,e);
		}
		return convertedDate;
	}

	/**
	 * Added By Narasimha Rao Kattunga Method to get today's date.
	 * 
	 * @return strDate
	 */
	public static String todayDate() {
		String todayDate = "";
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			todayDate = formatter.format(currentDate.getTime());

		} catch (Exception e) {
			LOGGER.error("DateUtil::todayDate::error " ,e);
		}
		return todayDate;
	}
	
	/**
	 * @return
	 */
	public static String getDDMMYYYYTodayDate() {
		String todayDate = "";
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_FORMAT);
			todayDate = formatter.format(currentDate.getTime());

		} catch (Exception e) {
			LOGGER.error("DateUtil::getDDMMYYYYTodayDate::error " ,e);
		}
		return todayDate;
	}

	/**
	 * 
	 * Method to get Current Time date.
	 * 
	 * @return strDate
	 */
	public static String todaySystemDate() {
		String todayDate = "";
		try {
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			todayDate = formatter.format(currentDate.getTime());

		} catch (Exception e) {
			LOGGER.error("DateUtil::todaySystemDate::error " ,e);
		}
		return todayDate;
	}

	/**
	 * Added By Narasimha Rao Kattunga Method to get Current Time date.
	 * 
	 * @return strDate
	 */
	public static String currentTime() {
		Calendar calendar = new GregorianCalendar();
		String amOrpm=null;
		Integer currHour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer currMinute = calendar.get(Calendar.MINUTE);
		if (calendar.get(Calendar.AM_PM) == 0)
			amOrpm = "AM";
		else
			amOrpm = "PM";

		return formateTime(currHour + ":" + currMinute);
	}

	/**
	 * This method will convert provide date object to a dd-mm-yyyyy
	 * 
	 * @param date
	 *            have to converted
	 * @return dd-mm-yyyy string of date
	 */
	public static String getDDMMYYYYDateString(Date date) {
		String strDate = "";
		if (date != null) {
			String dateFormat = "dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			strDate = sdf.format(date).toString();
		}
		return strDate;
	}

	/**
	 * This method will convert provide date object to a dd-mm-yyyyy
	 * 
	 * @param date
	 *            have to converted
	 * @return dd/mm/yyyy string of date
	 */
	public static String getDDMMYYYYDateToString(Date date) {
		String strDate = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
			strDate = sdf.format(date).toString();
		}
		return strDate;
	}

	/**
	 * This method will convert provide date object to a yyMMdd
	 * 
	 * @param date
	 *            have to converted
	 * @return yyMMdd string of date
	 */
	public static String getYYMMDDDateToString(Date date) {
		String strDate = "";
		if (date != null) {
			String dateFormat = "yyMMdd";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			strDate = sdf.format(date).toString();
		}
		return strDate;
	}

	/**
	 * This method will convert provide date object to a dd-mm-yyyyy
	 * 
	 * @param date
	 *            have to converted
	 * @return dd-mm-yyyy string of date
	 */
	public static String getDDMMYYYYDateString(Date date, String dateFormat) {
		String strDate = null;
		if (date != null) {
			// String dateFormat = "dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			strDate = sdf.format(date).toString();
		}
		return strDate;
	}

	/**
	 * @return
	 */
	public static String getCurrentTimeInHHmmss() {
		GregorianCalendar gc = new GregorianCalendar();
		StringBuffer sb = new StringBuffer();
		sb.append(gc.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		sb.append(gc.get(Calendar.MINUTE));
		return sb.toString();
	}

	/**
	 * @return
	 */
	public static Date getCurrentDate() {
		GregorianCalendar gc = new GregorianCalendar();
		return gc.getTime();
	}

	/**
	 * This method will convert provide date object to dd-MM-yyyy HH:mm
	 * 
	 * @param date
	 *            have to converted
	 * @return dd-MM-yyyy HH:mm string of date
	 */

	public static String getCurrentDateInYYYYMMDDHHMM() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	/**
	 * This method will  provide String  object in dd/MM/yyyy HH:mm format
	 * 
	 * @param date
	 *            have to converted
	 * @return dd/MM/yyyy HH:mm string of date
	 */

	public static String getDateInDDMMYYYYHHMMSlashFormat() {
		SimpleDateFormat sdfDate = new SimpleDateFormat(FrameworkConstants.DDMMYYYYHHMM_SLASH_FORMAT);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	/**
	 * This method will  provide String  object in dd/MM/yyyy HH:mm format
	 * 
	 * @param date
	 *            have to converted
	 * @return dd/MM/yyyy HH:mm string of date
	 */

	public static String getDateInDDMMYYYYHHMMSlashFormat(Date date) {
		String strDate =null;
		if(!StringUtil.isNull(date)){
			SimpleDateFormat sdfDate = new SimpleDateFormat(FrameworkConstants.DDMMYYYYHHMM_SLASH_FORMAT);
			strDate = sdfDate.format(date);
		}
		return strDate;
	}
	
	/**
	 * This method will  provide String  object in dd/MM/yyyy HH:mm:ss format
	 * 
	 * @param date
	 *            have to converted
	 * @return dd/MM/yyyy HH:mm:ss string of date
	 */

	public static String getDateInDDMMYYYYHHMMSSSlashFormat() {
		final SimpleDateFormat sdfDate = new SimpleDateFormat(FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT);
		final Date now = new Date();
		return sdfDate.format(now);
	}
	

	/**
	 * @param fromdate
	 * @param todate
	 * @return
	 */
	public static Long DayDifferenceBetweenTwoDates(Date fromdate, Date todate) {

		Long diffDays = 0l;
		if (fromdate != null && todate != null) {

			// extracting year,month,days--for--fromdate
			Calendar fromcal = Calendar.getInstance();
			fromcal.setTime(fromdate);

			int fromDay = fromcal.get(Calendar.DATE);
			int fromMonth = fromcal.get(Calendar.MONTH);
			int fromYear = fromcal.get(Calendar.YEAR);

			// extracting year,month,days--for--todate
			Calendar tocal = Calendar.getInstance();
			tocal.setTime(todate);

			int toDay = tocal.get(Calendar.DATE);
			int toMonth = tocal.get(Calendar.MONTH);
			int toYear = tocal.get(Calendar.YEAR);

			// Creates two calendars instances
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			// Set the date for both of the calendar instance

			cal1.set(toYear, toMonth, toDay);
			cal2.set(fromYear, fromMonth, fromDay);

			// Get the represented date in milliseconds
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();

			// Calculate difference in milliseconds
			long diff = Math.abs(milis1 - milis2);


			// Calculate difference in days
			diffDays = Math.abs(diff / (DAY_MILLIS));

			return diffDays;

		} else
			return diffDays;

	}

	/**
	 * @param fromDate
	 * @return
	 */
	public static Date getPreviousDateFromGivenDate(Date fromDate) {
		Calendar tocal = Calendar.getInstance();
		tocal.setTime(fromDate);
		tocal.set(Calendar.DAY_OF_MONTH, tocal.get(Calendar.DAY_OF_MONTH) - 1);
		return tocal.getTime();
	}


	public static Date combineDateWithTimeHHMM(String dateStr, String hhStr,String minStr) {
		StringBuffer ab = new StringBuffer();
		ab.append(StringUtil.isStringEmpty(hhStr)?"00":hhStr);
		ab.append(":");
		ab.append(StringUtil.isStringEmpty(minStr)?"00":minStr);
		return combineDateWithTimeHHMM(dateStr,ab.toString());

	}

	/**
	 * @param dateStr
	 * @param timeStr
	 * @return
	 */
	public static Date combineDateWithTimeHHMMSS(String dateStr, String timeStr) {
		Date convertedDate = null;
		try {


			StringBuffer ab = new StringBuffer(dateStr);
			ab.append(" ");
			if(!StringUtil.isStringEmpty(timeStr)){
				ab.append(timeStr);
			}else{
				ab.append("00:00:00");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			convertedDate = sdf.parse(ab.toString());
		} catch (Exception e) {
			LOGGER.error("DateUtil::combineDateWithTimeHHMMSS::error " ,e);
		}
		return convertedDate;

	}

	/**
	 * @param dateStr
	 * @param timeStr
	 * @return
	 */
	public static Date combineDateWithTimeHHMM(String dateStr, String timeStr) {
		Date convertedDate = null;
		try {
			StringBuffer ab = new StringBuffer(dateStr);
			ab.append(" ");
			if(!StringUtil.isStringEmpty(timeStr)){
				ab.append(timeStr);
			}else{
				ab.append("00:00");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			convertedDate = sdf.parse(ab.toString());
		} catch (Exception e) {
			LOGGER.error("DateUtil::combineDateWithTimeHHMM::error " ,e);
		}
		return convertedDate;

	}
	
	
	
	/**
	 * Combine given time with current date.
	 *
	 * @param time the time
	 * @return the date
	 */
	public static Date combineGivenTimeWithCurrentDate(String time){
		Date convertedDate = null;
		String date=todaySystemDate();
		convertedDate= combineDateWithTimeHHMM(date,time);
		return convertedDate;
	}

	/**
	 * @param time
	 * @return
	 */
	public static String formateTime(String time) {
		String result = "";
		if (!StringUtil.isStringEmpty(time)) {
			String[] timeContent = time.split(":");
			String hour = timeContent[0].length() == 1 ? "0" + timeContent[0]
					: timeContent[0];
			String minute = timeContent[1].length() == 1 ? "0" + timeContent[1]
					: timeContent[1];
			result = hour + ":" + minute;
		}
		return result;
	}

	/**
	 * @param date
	 * @param time
	 * @return
	 */
	public static Timestamp getTimestampFromDateTime(String date,String time) {
		if (date != null && !date.trim().equals("")) {
			String[] dt = date.split("/");
			date=dt[2]+"-"+dt[1]+"-"+dt[0];
		}
		if (time != null && !time.trim().equals("")) {
			time=time+":00";
		}else{
			time="00:00:00";
		}
		return java.sql.Timestamp.valueOf(date+" "+time);
	}

	/**
	 * @param fromdate
	 * @return
	 */
	public static String extractTimeFromDate(Date fromdate) {
		//extracting hours minutes seconds--for--fromdate
		String time="";
		if(fromdate!=null){
			Calendar fromcal=Calendar.getInstance();
			fromcal.setTime(fromdate);
			String hour = fromcal.get(Calendar.HOUR_OF_DAY) < 10 ? "0"+fromcal.get(Calendar.HOUR_OF_DAY) : ""+fromcal.get(Calendar.HOUR_OF_DAY);
			String minute = fromcal.get(Calendar.MINUTE) < 10 ? "0"+fromcal.get(Calendar.MINUTE) : ""+fromcal.get(Calendar.MINUTE); // MINUTE
			//String second = fromcal.get(Calendar.SECOND);
			time=hour+":"+minute;
		}
		return time;
	}
	
	
	public static String extractHourInHHFormatFromDate(Date fromdate) {
	String time=extractTimeFromDate(fromdate);
	
	if(!StringUtil.isStringEmpty(time) && time.contains(FrameworkConstants.CHARACTER_COLON)){
		return time.split(FrameworkConstants.CHARACTER_COLON)[0];
	}
	
	return null;
	}
	
	public static String extractMinutesInMMFormatFromDate(Date fromdate) {
		String time=extractTimeFromDate(fromdate);
		
		if(!StringUtil.isStringEmpty(time) && time.contains(FrameworkConstants.CHARACTER_COLON)){
			return time.split(FrameworkConstants.CHARACTER_COLON)[1];
		}
		
		return null;
		}

	/**
	 * @return
	 */
	public static String getDDMMYYYYDateToStringAndTime() {

		Date date = new Date();  
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");  
		return format.format(date);
	}

	/**
	 * Converts Time format (HHMM to HH:MM)
	 * @param manifestTime
	 * @return
	 */
	public static String convertTimeHHMMToHHMMWithColon(String time) {
		String formattedTime = null;
		if(time.length() == 4){
			StringBuffer sb = new StringBuffer();
			sb.append(time.substring(0, 2));
			sb.append(":");
			sb.append(time.substring(2,time.length()));
			formattedTime = sb.toString();
		}
		return formattedTime;
	}	

	/**
	 * @param input
	 * @return
	 */
	public static XMLGregorianCalendar  convertDateToXMLGregorianCalendar(Date input) {
		DatatypeFactory df = null;
		XMLGregorianCalendar result=null;
		try {
			df = DatatypeFactory.newInstance();
		} catch (Exception dce) {
			LOGGER.error("DateUtil::convertDateToXMLGregorianCalendar::error " ,dce);
		}

		if (input != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(input.getTime());
			result =  df.newXMLGregorianCalendar(gc);
		}
		return result;
	}	

	/**
	 * @param cal
	 * @return
	 */
	public static GregorianCalendar  convertXMLGregorianCalendarToGregorianCalendar(XMLGregorianCalendar cal) {
		GregorianCalendar gc=null;
		if(cal != null){
			gc = cal.toGregorianCalendar();
		}
		return gc;
	}	
	/**
	 * @param cal
	 * @return
	 */
	public static Date  convertXMLGregorianCalendarToDate(XMLGregorianCalendar cal) {
		GregorianCalendar gc=null;
		Date date = null;
		if(cal != null){
			gc = cal.toGregorianCalendar();
			date = gc.getTime();
		}
		return date;
	}	
	/**
	 * @param cal
	 * @return
	 */
	public static Timestamp  convertXMLGregorianCalendarToTimeStamp(XMLGregorianCalendar cal) {
		GregorianCalendar gc=null;
		Timestamp timeStamp=null;
		if(cal != null){
			gc = cal.toGregorianCalendar();
			timeStamp = new Timestamp(gc.getTimeInMillis());
		}
		return timeStamp;
	}	
	/**
	 * @param date
	 * @return
	 */
	public static Timestamp getTimeStampFromDateSlashFormatString(String date){
		Calendar tocal = Calendar.getInstance();
		tocal.setTime(slashDelimitedstringToDDMMYYYYFormat(date));
		return new Timestamp(tocal.getTimeInMillis());
	}
	/**
	 * 
	 * please dont modify the following method
	 */
	public static String getCurrentTimeInString(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		return  sdfDate.format(now);
	}

	/**
	 * @return
	 */
	public static String getCurrentTimeInMilliSeconds(){
		return System.currentTimeMillis()+"";
	}
	public static Date getPreviousDate(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-days);
		return calendar.getTime();
	}
	
	public static Date getFutureDate(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,+days);
		
		return calendar.getTime();
	}


	/**
	 * This method will convert provide date object to dd-MM-yyyy HH:mm
	 * 
	 * @param date
	 *            have to converted
	 * @return dd-MM-yyyy HH:mm string of date
	 */

	public static String getCurrentDateInDDMMYYYY() {
		SimpleDateFormat sdfDate = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public static String getCurrentTime() {
		Calendar calendar = GregorianCalendar.getInstance();
		return getTimeFromDate(calendar);
	}
	
	/**
	 * Gets the time from date.
	 *
	 * @param inputDate the input date
	 * @return the time from date
	 */
	public static String getTimeFromDate(Date inputDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);
		return getTimeFromDate(calendar);
	}
	
	/**
	 * Gets the time from date.
	 *
	 * @param calendar the calendar
	 * @return the time from date
	 */
	private static String getTimeFromDate(Calendar calendar){
		String currHour = calendar.get(Calendar.HOUR_OF_DAY)+"";
		String currMinute = calendar.get(Calendar.MINUTE)+"";
		if(!StringUtil.isStringEmpty(currHour)&& currHour.length()<=1){
			currHour = "0"+currHour;
		}
		if(!StringUtil.isStringEmpty(currMinute)&& currMinute.length()<=1){
			currMinute = "0"+currMinute;
		}
		return currHour+FrameworkConstants.CHARACTER_COLON+currMinute;
	}


	/**
	 * @param paramDate
	 * @return
	 * @throws ParseException
	 */
	public static Date trimTimeFromDate(Date paramDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		return sdf.parse(sdf.format(paramDate));
	}
	/**
	 * @param paramDate
	 * @return
	 * @throws ParseException
	 */
	public static Date appendLastHourToDate(Date paramDate)
			 {
		Date resultDate=null;
		SimpleDateFormat date = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		SimpleDateFormat lastHourDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String currentTime=" 23:59:59";
		StringBuffer ab=new StringBuffer(date.format(paramDate));
		ab.append(currentTime);
		try {
			resultDate= lastHourDate.parse(ab.toString());
		} catch (ParseException e) {
			LOGGER.error("DateUtil ::appendLastHourToDate ::Parsing Exception",e);
		}
		return resultDate;
	}
	/**
	 * @param ldate
	 * @param rdate
	 * @return
	 */
	public static Boolean equalsDate(Date ldate,Date rdate)
	{
		Date lldate=null;
		try {
			lldate = trimTimeFromDate(ldate);
		} catch (ParseException e) {
			LOGGER.error("DateUtil::equalsDate::error " ,e);
		}
		Date rrdate=null;
		try {
			rrdate = trimTimeFromDate(rdate);
		} catch (ParseException e) {
			LOGGER.error("DateUtil::equalsDate::error " ,e);
		}
		if(lldate!=null && rrdate !=null){
			return (lldate.compareTo(rrdate)==0)?Boolean.TRUE:Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
	/**
	 * @param ldate
	 * @param rdate
	 * @return
	 */
	public static Boolean equalsMonths(Date ldate,Date rdate)
	{
		return equalCalendar(ldate,rdate,Calendar.MONTH);
	}
	/**
	 * @param ldate
	 * @param rdate
	 * @return
	 */
	public static Boolean equalsYears(Date ldate,Date rdate)
	{
		return equalCalendar(ldate,rdate,Calendar.YEAR);
	}

	/**
	 * @param date
	 * @param field
	 * @return
	 */
	private static int getCalenderField(Date date,int field){
		Calendar lcalendar =  Calendar.getInstance();
		lcalendar.setTime(date);
		return lcalendar.get(field);
	}
	/**
	 * @param ldate
	 * @param rdate
	 * @param field
	 * @return
	 */
	private static Boolean equalCalendar(Date ldate,Date rdate,int field){
		if(ldate!=null && rdate !=null){
			return (getCalenderField(ldate,field)==getCalenderField(rdate,field))?Boolean.TRUE:Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * Gets the date without time.
	 *
	 * @return the date without time
	 * @throws ParseException the parse exception
	 */
	public static Date getCurrentDateWithoutTime(){
		
		Date rrdate=null;
		try {
			Date paramDate=getCurrentDate();
			rrdate = trimTimeFromDate(paramDate);
		} catch (Exception e) {
			LOGGER.error("DateUtil::getCurrentDateWithoutTime::error " ,e);
		}
		return rrdate;
	}
	public static Long DayDifferenceBetweenTwoDatesIncludingBackDate(Date fromdate, Date todate) {

		Long diffDays = 0l;
		if (fromdate != null && todate != null) {

			// extracting year,month,days--for--fromdate
			Calendar fromcal = Calendar.getInstance();
			fromcal.setTime(fromdate);

			int fromDay = fromcal.get(Calendar.DATE);
			int fromMonth = fromcal.get(Calendar.MONTH);
			int fromYear = fromcal.get(Calendar.YEAR);

			// extracting year,month,days--for--todate
			Calendar tocal = Calendar.getInstance();
			tocal.setTime(todate);

			int toDay = tocal.get(Calendar.DATE);
			int toMonth = tocal.get(Calendar.MONTH);
			int toYear = tocal.get(Calendar.YEAR);

			// Creates two calendars instances
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			// Set the date for both of the calendar instance

			cal1.set(toYear, toMonth, toDay);
			cal2.set(fromYear, fromMonth, fromDay);

			// Get the represented date in milliseconds
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();

			// Calculate difference in milliseconds
			long diff = (milis1 - milis2);

			// Calculate difference in days
			diffDays = (diff / (DAY_MILLIS));

			return diffDays;

		} else
			return diffDays;

	}
	
	/*Converts date string from one format to another format*/
	public static String convertDateFormats(String strDate, String strDateFormat, String desiredFormat){
		String convertedDate = null;
		try {
			//string containing date in below format
			SimpleDateFormat sdfSource = new SimpleDateFormat(strDateFormat);
			Date date = sdfSource.parse(strDate );
			//create SimpleDateFormat object with desired date format
		    SimpleDateFormat sdfDestination = new SimpleDateFormat(desiredFormat);		     
		    //parse the date into another format
		    convertedDate = sdfDestination.format(date);
		} catch (ParseException e) {
			LOGGER.error("DateUtil::convertDateFormats::error " ,e);
		}
		return convertedDate;
	}
	
	/**
	 * Convert milli seconds tohhmmss string format.
	 *
	 * @param milliseconds the milliseconds
	 * @return the string
	 */
	public static String convertMilliSecondsTOHHMMSSStringFormat(long milliseconds){
		String convertedDate = null;
		try {
			convertedDate = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
		            TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
		            TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
		} catch (Exception e) {
			LOGGER.error("DateUtil::convertMilliSecondsTOHHMMSSStringFormat::Exception",e);
		}
		return convertedDate;
	}
	
	
	/**
	 * Convert milliseconds to date.
	 *
	 * @param milliseconds the milliseconds
	 * @return the date
	 */
	public static Date convertMillisecondsToDate(long milliseconds){
		return StringUtil.isEmptyLong(milliseconds)?null:new Date(milliseconds);
	}
	
	public static String getDateInYYYYMMDDHHMM() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	public static String getMeridiemCurrentTime() {
		Calendar calendar = GregorianCalendar.getInstance();
		return getMeridiemTimeFromDate(calendar);
	}
	
	
	private static String getMeridiemTimeFromDate(Calendar calendar){
		
		
		String dayPart;
		String currHour = calendar.get(Calendar.HOUR_OF_DAY)+"";
		String currMinute = calendar.get(Calendar.MINUTE)+"";
		if(!StringUtil.isStringEmpty(currHour)&& currHour.length()<=1){
			currHour = "0"+currHour;
		}
		if(!StringUtil.isStringEmpty(currMinute)&& currMinute.length()<=1){
			currMinute = "0"+currMinute;
		}
		if(Integer.parseInt(currHour)<12){
			dayPart = " AM";
		}else{
			dayPart = "PM";
		}
		
		return currHour+FrameworkConstants.CHARACTER_COLON+currMinute +" " + dayPart;
	}
	
	public static Date getIncrementDate(Date date, Integer noOfDaysToAdd){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.add(Calendar.DATE, noOfDaysToAdd);// number of days to add
		return c1.getTime();
	}
	 public static int getDateDifferenceByHours( Date earlierDate, Date laterDate )
	    {
	        if( earlierDate == null || laterDate == null ) return 0;
	        
	        return (int)((laterDate.getTime()/HOUR_MILLIS) - (earlierDate.getTime()/HOUR_MILLIS));
	    }
	 public static int getDateDifferenceByHours(long earlierDate, long laterDate )
	    {
	        
	        return (int)((laterDate/HOUR_MILLIS) - (earlierDate/HOUR_MILLIS));
	    }
	 
	 public static int getYearsDifferenceByTwoDates(Date fromDate, Date toDate) {
		 Calendar fromCalendar =  Calendar.getInstance();
		 fromCalendar.setTime(fromDate);
		 Calendar toCalendar =  Calendar.getInstance();
		 toCalendar.setTime(toDate);
		 int diff = getYearsDifference(fromCalendar, toCalendar);
		 return diff;
	 }
	 public static int getYearsDifferenceByTwoDates(long fromDate, long toDate) {
		 Calendar fromCalendar =  Calendar.getInstance();
		 fromCalendar.setTimeInMillis(fromDate);
		 Calendar toCalendar =  Calendar.getInstance();
		 toCalendar.setTimeInMillis(toDate);
		 int diff = getYearsDifference(fromCalendar, toCalendar);
		 return diff;
	 }
	private static int getYearsDifference(Calendar fromCalendar,
			Calendar toCalendar) {
		int diff = toCalendar.get(Calendar.YEAR) - fromCalendar.get(Calendar.YEAR);
		 if (fromCalendar.get(Calendar.MONTH) > toCalendar.get(Calendar.MONTH) || 
				 (fromCalendar.get(Calendar.MONTH) == toCalendar.get(Calendar.MONTH) && fromCalendar.get(Calendar.DATE) > toCalendar.get(Calendar.DATE))) {
			 diff--;
		 }
		return diff;
	}
	 
	 public static void main(String s[]){
		Calendar cl= Calendar.getInstance();
		cl.setTimeInMillis(1412846460000L);
		LOGGER.debug(cl.getTime().toString());
	 }
}
