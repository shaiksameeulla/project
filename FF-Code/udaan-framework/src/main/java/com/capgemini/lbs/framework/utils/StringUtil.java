/*
 * @author soagarwa
 */
package com.capgemini.lbs.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class StringUtil.
 * 
 * @author soagarwa
 */

public abstract class StringUtil {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(StringUtil.class);

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';

	// ---------------------------------------------------------------------
	// General convenience methods for working with Strings
	// ---------------------------------------------------------------------

	/*
	 * This method will check if both source and destination is not null then
	 * they are equal or not
	 */

	/**
	 * Equals.
	 * 
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return true, if successful
	 */
	public static boolean equals(String source, String destination) {
		boolean check = false;

		if (source != null && destination != null) {
			source = source.trim();
			destination = destination.trim();
			if (source.equals(destination)) {
				check = true;
			} else {
				check = false;
			}
		} else {
			check = false;
		}
		return check;

	}

	/*
	 * This method will check whether string is empty or it contains empty space
	 * or not
	 */

	/**
	 * Generate ramdom number DDMMYYHH.
	 * 
	 * @return the string
	 */
	public static String generateDDMMYYHHRamdomNumber() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyhh");
		return format.format(date);
	}

	/**
	 * Generate ramdom number DDMMYYHHmmSS.
	 * 
	 * @return the string
	 */
	public static String generateDDMMYYHHMMSSRamdomNumber() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyhhmmss");
		return format.format(date);
	}

	/**
	 * Parses the long list.
	 * 
	 * @param joinedLongs
	 *            the joined longs
	 * @param separator
	 *            the separator
	 * @return the array list
	 * @throws Exception
	 *             the exception
	 */
	public static ArrayList<Long> parseLongList(String joinedLongs,
			String separator) {

		ArrayList<Long> retValue = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(joinedLongs,
					separator);
			int count = tokenizer.countTokens();
			retValue = new ArrayList<Long>(count);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();

				if (token.length() != 0) {
					retValue.add(new Long(token));
				}
			}
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseLongList Exception :", ex);
		}
		return retValue;
	}

	public static ArrayList<Double> parseDoubleList(String joinedLongs,
			String separator) {

		ArrayList<Double> retValue = null;
		try {
			StringTokenizer tokenizer = new StringTokenizer(joinedLongs,
					separator);
			int count = tokenizer.countTokens();
			retValue = new ArrayList<Double>(count);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();

				if (token.length() != 0) {
					retValue.add(new Double(token));
				}
			}
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseDoubleList Exception :", ex);
		}
		return retValue;
	}

	/**
	 * Method to tokenize and return array list.
	 * 
	 * @param joinedIntegers
	 *            String
	 * @param separator
	 *            String
	 * @return retValue
	 * @throws Exception
	 *             the exception
	 */
	public static ArrayList<Integer> parseIntegerList(String joinedIntegers,
			String separator) {
		ArrayList<Integer> retValue = null;

		try {
			StringTokenizer tokenizer = new StringTokenizer(joinedIntegers,
					separator);

			int count = tokenizer.countTokens();

			retValue = new ArrayList<Integer>(count);

			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();

				if (token.length() != 0) {
					retValue.add(new Integer(token));
				}
			}
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseIntegerList Exception :", ex);
		}
		return retValue;
	}

	/**
	 * Method to tokenize string and return array list.
	 * 
	 * @param inputData
	 *            String
	 * @param delimiter
	 *            String
	 * @return result
	 */
	public static ArrayList<String> parseStringList(String inputData,
			String delimiter) {
		ArrayList<String> result = null;

		if (inputData != null && inputData.trim().length() > 0) {
			result = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(inputData, delimiter, true);
			String lastToken = delimiter;
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (!token.equals(delimiter))
					result.add(token);
				else if (token.equals(lastToken))
					result.add("");
				lastToken = token;
			}
		}
		return result;
	}

	/**
	 * Sets the empty for null.
	 * 
	 * @param source
	 *            the source
	 * @return the string
	 */
	public static String setEmptyForNull(String source) {

		if (source == null
				|| source.trim().equals(FrameworkConstants.NULL_CONSTANT)) {
			source = "";

		}
		return source;

	}

	/**
	 * Check integer.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Integer isNullInteger(Integer source) {
		Integer returnValue = null;

		if (source == null || source == 0) {
			return returnValue;
		}
		return source;
	}

	/**
	 * Convert string to integer.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Integer convertStringToInteger(String source) {
		Integer returnValue = null;

		if (isStringEmpty(source)) {
			return returnValue;
		}
		try {
			returnValue = Integer.valueOf(source);
		} catch (NumberFormatException e) {
			LOGGER.error("StringUtil::convertStringToInteger Exception :", e);
		}

		return returnValue;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Long convertStringToLong(String source) {
		Long returnValue = null;

		if (isStringEmpty(source)) {
			return returnValue;
		}
		try {
			returnValue = Long.valueOf(source);
		} catch (NumberFormatException e) {
			LOGGER.error("StringUtil::convertStringToLong:: Exception :", e);
		}

		return returnValue;
	}

	/**
	 * Gets the random number.
	 * 
	 * @return the random number
	 */
	public static Integer getRandomNumber() {

		Integer randomNumber = (int) (Math.random() * 1000000000);

		return randomNumber;
	}

	/**
	 * Gets the random characters.
	 * 
	 * @return the random characters
	 */
	public static String getRandomCharacters() {
		String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String s = "";
		Random generator = new Random();
		for (int i = 0; i < 12; i++) {
			s += validChars.charAt(generator.nextInt(validChars.length()));
		}
		return s;
	}

	/*
	 * Validate the string to be email
	 */
	/**
	 * Validate email.
	 * 
	 * @param email
	 *            the email
	 * @return the boolean
	 */
	public static Boolean validateEmail(String email) {
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

		// Match the given string with the pattern
		Matcher m = p.matcher(email);

		// check whether match is found
		boolean matchFound = m.matches();

		if (matchFound)
			return true;
		else
			return false;
	}

	/**
	 * Convert string to float.
	 * 
	 * @param source
	 *            the source
	 * @return the float
	 */
	public static Float convertStringToFloat(String source) {
		Float convertValue = null;
		if (isStringEmpty(source)) {
			return convertValue;
		}
		convertValue = Float.valueOf(source.trim()).floatValue();
		return convertValue;
	}

	/**
	 * Convert string to double.
	 * 
	 * @param source
	 *            the source
	 * @return the double
	 */
	public static Double convertStringToDouble(String source) {
		Double convertValue = null;
		if (isStringEmpty(source)) {
			return convertValue;
		}
		convertValue = Double.valueOf(source.trim()).doubleValue();
		return convertValue;
	}

	/**
	 * Convert string to integer.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static String convertIntegerToString(Integer source) {
		String returnValue;
		if (source == null) {
			returnValue = null;
			return returnValue;
		}
		returnValue = String.valueOf(source);
		return returnValue;
	}

	/**
	 * Check integer.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Boolean isEmptyInteger(Integer source) {
		Boolean flag = false;
		if (source == null || source == 0 || source == -1) {
			source = null;
			flag = true;
		}
		return flag;
	}

	/**
	 * Check Double.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Boolean isEmptyDouble(Double source) {
		Boolean flag = false;
		if (source == null || source == 0) {
			source = null;
			flag = true;
		}
		return flag;
	}

	/**
	 * Check Long.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Boolean isEmptyLong(Long source) {
		Boolean flag = false;
		if (source == null || source == 0l) {
			source = null;
			flag = true;
		}
		return flag;
	}

	/**
	 * Check Long.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Long parseLong(String source) {

		Long result = 0l;
		try {
			if (!StringUtil.isStringEmpty(source)) {
				result = Long.parseLong(source);
			}
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseLong:: Exception :", ex);
		}
		return result;

	}

	/**
	 * Check whether given string contains integer .
	 * 
	 * @param strValue
	 * @return the Boolean
	 */
	public static Boolean isStringContainsInteger(String strValue) {
		Boolean flag = false;
		if (!isStringEmpty(strValue)) {
			if (isInteger(strValue)
					&& !isEmptyInteger(Integer.valueOf(strValue))) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Method to check if the object passed is Null.
	 * 
	 * @param aObject
	 *            Object
	 * @return Boolean
	 */
	public static Boolean isNull(Object aObject) {
		if (aObject == null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the cN series.
	 * 
	 * @author jaydutta
	 * @param consigNumber
	 *            the consig number
	 * @return the cN series
	 */
	public static String getCNSeries(String consigNumber) {

		String cNSeries = "";

		char cnChar_0 = consigNumber.charAt(0);
		char cnChar_1;
		boolean isDigit = (cnChar_0 >= '0' && cnChar_0 <= '9');
		if (isDigit) {

			cnChar_1 = consigNumber.charAt(1);
			cNSeries = Character.toString(cnChar_0)
					+ Character.toString(cnChar_1);
		} else {
			cNSeries = Character.toString(cnChar_0);
		}

		return cNSeries;
	}

	public static String getRandomCharactersForSummary() {
		String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String s = "";
		Random generator = new Random();
		for (int i = 0; i < 15; i++) {
			s += validChars.charAt(generator.nextInt(validChars.length()));
		}
		return s;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			LOGGER.error("StringUtil::isInteger:: Exception :", e);
			return false;
		}
	}

	public static Integer parseInteger(String input) {
		Integer result = null;
		try {
			result = Integer.parseInt(input);
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseInteger:: Exception :", ex);
		}
		return result;
	}

	public static Double parseDouble(String input) {
		Double result = null;
		try {
			result = Double.parseDouble(input);
		} catch (Exception ex) {
			LOGGER.error("StringUtil::parseDouble:: Exception :", ex);
		}
		return result;
	}

	public static JSONArray getJsonArrayFromStringArry(String[] str) {
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jObj;
			for (int i = 0; i < str.length; i++) {
				jObj = new JSONObject();
				jObj.put("CODE", str[i]);
				jsonArray.put(jObj);
			}
			return jsonArray;
		} catch (Exception e) {
			LOGGER.error(
					"StringUtil::getJsonArrayFromStringArry:: Exception :", e);
		}
		return null;
	}

	/**
	 * Gets the random number.
	 * 
	 * @return the random number
	 */
	public static String getRandomNoByOfficeCode(String officeCode) {
		Integer randomNumber = (int) (Math.random() * 10000000);
		String randomNo = officeCode.concat(String.valueOf(randomNumber));
		// String
		// randomNo=officeCode.concat(generateDDMMYYHHMMSSRamdomNumber());
		return randomNo;
	}

	/**
	 * Check string.
	 * 
	 * @param source
	 *            the source
	 * @return the string
	 */
	public static Boolean isStringEmpty(String source) {
		Boolean isStringEmpty = false;
		if (source == null
				|| source.trim().equals(FrameworkConstants.NULL_CONSTANT)
				|| (source.trim().equals(FrameworkConstants.EMPTY_STRING))) {
			source = null;
			isStringEmpty = true;

		}
		return isStringEmpty;
	}

	@SuppressWarnings("rawtypes")
	public static Boolean isEmptyList(List arrayList) {
		Boolean isListEmpty = true;
		if (arrayList != null && !arrayList.isEmpty()) {
			isListEmpty = false;

		}
		return isListEmpty;
	}

	@SuppressWarnings("rawtypes")
	public static Boolean isEmptyMap(Map hashMap) {
		Boolean isMapEmpty = true;
		if (hashMap != null && !hashMap.isEmpty()) {
			isMapEmpty = false;
		}
		return isMapEmpty;
	}

	@SuppressWarnings("rawtypes")
	public static Boolean isEmptyColletion(Collection arrayList) {
		Boolean isListEmpty = true;
		if (arrayList != null && !arrayList.isEmpty()) {
			isListEmpty = false;

		}
		return isListEmpty;
	}

	public static String getFormattedWeight(Double weight) {
		if (weight != null && weight > 0) {
			String wt = weight.toString();
			int index = wt.indexOf(".");
			String str1 = wt.substring(0, index);
			String str2 = wt.substring(index, wt.length());
			String formattedWeight = null;
			int wtDecimalPlaces = str2.length();
			switch (wtDecimalPlaces) {
			case 2:
				formattedWeight = str1 + str2 + "00";
				break;
			case 3:
				formattedWeight = str1 + str2 + "0";
				break;
			case 4:
				formattedWeight = str1 + str2;
				break;
			default:
				formattedWeight = String.valueOf(weight);
				break;
			}

			return formattedWeight;

		} else
			return "0.000";
	}

	public static Double getFormattedWeight(double Rval, int Rpl) {
		double p = (double) Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return (double) tmp / p;
	}

	public static String covertStringTitleCase(String str) {
		StringBuffer menuName = new StringBuffer();
		if (str != null) {
			String[] strArr = str.split(" ");
			for (int i = 0; i < strArr.length; i++) {
				menuName.append(convertInitialCap(strArr[i]));
			}

		}
		return menuName.toString();
	}

	public static String convertInitialCap(String aString) {

		return (aString.charAt(0) + "".toUpperCase()).concat((aString
				.substring(1)).toLowerCase());

	}

	public static String convertInitialLetterCap(String aString) {
		if (aString.indexOf("/") == -1 && aString.indexOf(" ") == -1) {
			return (aString.charAt(0) + "".toUpperCase()).concat((aString
					.substring(1)).toLowerCase());
		} else {
			return (aString.charAt(0) + "".toUpperCase()).concat((aString
					.substring(1)));
		}
	}

	/**
	 * Generate ramdom number.
	 * 
	 * @return the string
	 */
	public static String generateRamdomNumber() {
		StringBuilder ramdomNumberBuilder = new StringBuilder();
		try {
			long randomNum = new Random(System.currentTimeMillis()).nextLong();
			ramdomNumberBuilder = (randomNum < 0) ? ramdomNumberBuilder
					.append(randomNum * -1) : ramdomNumberBuilder
					.append(randomNum);
		} catch (Exception ex) {
			LOGGER.error("StringUtil::generateRamdomNumber:: Exception :", ex);
		}
		return ramdomNumberBuilder.toString();
	}

	public static String generateNumber() {
		StringBuilder ramdomNumberBuilder = new StringBuilder();
		try {
			Random random = new Random();
			char[] digits = new char[10];
			digits[0] = (char) (random.nextInt(9) + '1');
			for (int i = 1; i < 10; i++) {
				digits[i] = (char) (random.nextInt(10) + '0');
			}
			ramdomNumberBuilder.append(Long.parseLong(new String(digits)));
		} catch (Exception ex) {
			LOGGER.error("StringUtil::generateNumber Exception :", ex);

		}
		return ramdomNumberBuilder.toString();
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of
	 * length 0. Note: Will return <code>true</code> for a CharSequence that
	 * purely consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of
	 * whitespace.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 *         its length is greater than 0, and it does not contain whitespace
	 *         only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its
	 *         length is greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence contains any whitespace characters.
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not empty and contains
	 *         at least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String contains any whitespace characters.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not empty and contains at
	 *         least 1 whitespace character
	 * @see #containsWhitespace(CharSequence)
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0
				&& Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Trim <i>all</i> whitespace from the given String: leading, trailing, and
	 * inbetween characters.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0
				&& Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Trim all occurences of the supplied leading character from the given
	 * String.
	 * 
	 * @param str
	 *            the String to check
	 * @param leadingCharacter
	 *            the leading character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * Trim all occurences of the supplied trailing character from the given
	 * String.
	 * 
	 * @param str
	 *            the String to check
	 * @param trailingCharacter
	 *            the trailing character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimTrailingCharacter(String str,
			char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0
				&& sb.charAt(sb.length() - 1) == trailingCharacter) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * Test if the given String ends with the specified suffix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param suffix
	 *            the suffix to look for
	 * @see java.lang.String#endsWith
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length())
				.toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * Test whether the given string matches the given substring at the given
	 * index.
	 * 
	 * @param str
	 *            the original string (or StringBuilder)
	 * @param index
	 *            the index in the original string to start matching against
	 * @param substring
	 *            the substring to match at the given index
	 */
	public static boolean substringMatch(CharSequence str, int index,
			CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0
				|| sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Replace all occurences of a substring within a string with another
	 * string.
	 * 
	 * @param inString
	 *            String to examine
	 * @param oldPattern
	 *            String to replace
	 * @param newPattern
	 *            String to insert
	 * @return a String with the replacements
	 */
	public static String replace(String inString, String oldPattern,
			String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern)
				|| newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}

	/**
	 * Delete all occurrences of the given substring.
	 * 
	 * @param inString
	 *            the original String
	 * @param pattern
	 *            the pattern to delete all occurrences of
	 * @return the resulting String
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * Delete any character in a given String.
	 * 
	 * @param inString
	 *            the original String
	 * @param charsToDelete
	 *            a set of characters to delete. E.g. "az\n" will delete 'a's,
	 *            'z's and new lines.
	 * @return the resulting String
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	// ---------------------------------------------------------------------

	/**
	 * Quote the given String with single quotes.
	 * 
	 * @param str
	 *            the input String (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or
	 *         <code>null<code> if the input was <code>null</code>
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * Turn the given Object into a String with single quotes if it is a String;
	 * keeping the Object as-is else.
	 * 
	 * @param obj
	 *            the input Object (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or the input object as-is
	 *         if not a String
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * Unqualify a string qualified by a '.' dot character. For example,
	 * "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example,
	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param separator
	 *            the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to upper case
	 * as per {@link Character#toUpperCase(char)}. No other letters are changed.
	 * 
	 * @param str
	 *            the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to lower
	 * case as per {@link Character#toLowerCase(char)}. No other letters are
	 * changed.
	 * 
	 * @param str
	 *            the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str,
			boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * Extract the filename from the given path, e.g. "mypath/myfile.txt" ->
	 * "myfile.txt".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the extracted filename, or <code>null</code> if none
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
				: path);
	}

	/**
	 * Extract the filename extension from the given path, e.g.
	 * "mypath/myfile.txt" -> "txt".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the extracted filename extension, or <code>null</code> if none
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * Strip the filename extension from the given path, e.g.
	 * "mypath/myfile.txt" -> "mypath/myfile".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the path with stripped filename extension, or <code>null</code>
	 *         if none
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	/**
	 * Apply the given relative path to the given path, assuming standard Java
	 * folder separation (i.e. "/" separators).
	 * 
	 * @param path
	 *            the path to start from (usually a full file path)
	 * @param relativePath
	 *            the relative path to apply (relative to the full file path
	 *            above)
	 * @return the full file path that results from applying the relative path
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * Normalize the path by suppressing sequences like "path/.." and inner
	 * simple dots.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that
	 * Windows separators ("\") are replaced by simple slashes.
	 * 
	 * @param path
	 *            the original path
	 * @return the normalized path
	 */
	public static String cleanPath(String path) {
		if (path == null) {
			return null;
		}
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR,
				FOLDER_SEPARATOR);

		// Strip prefix from path to analyze, to not treat it as part of the
		// first path element. This is necessary to correctly parse paths like
		// "file:core/../core/io/Resource.class", where the ".." should just
		// strip the first "core" directory while keeping the "file:" prefix.
		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}
		if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
			prefix = prefix + FOLDER_SEPARATOR;
			pathToUse = pathToUse.substring(1);
		}

		String[] pathArray = delimitedListToStringArray(pathToUse,
				FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (CURRENT_PATH.equals(element)) {
				// Points to current directory - drop it.
			} else if (TOP_PATH.equals(element)) {
				// Registering top path found.
				tops++;
			} else {
				if (tops > 0) {
					// Merging path element with element corresponding to top
					// path.
					tops--;
				} else {
					// Normal path element found.
					pathElements.add(0, element);
				}
			}
		}

		// Remaining top paths need to be retained.
		for (int i = 0; i < tops; i++) {
			pathElements.add(0, TOP_PATH);
		}

		return prefix
				+ collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * Compare two paths after normalization of them.
	 * 
	 * @param path1
	 *            first path for comparison
	 * @param path2
	 *            second path for comparison
	 * @return whether the two paths are equivalent after normalization
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * Parse the given <code>localeString</code> into a {@link Locale}.
	 * <p>
	 * This is the inverse operation of {@link Locale#toString Locale's
	 * toString}.
	 * 
	 * @param localeString
	 *            the locale string, following <code>Locale's</code>
	 *            <code>toString()</code> format ("en", "en_UK", etc); also
	 *            accepts spaces as separators, as an alternative to underscores
	 * @return a corresponding <code>Locale</code> instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if (parts.length >= 2) {
			// There is definitely a variant, and it is everything after the
			// country
			// code sans the separator between the country code and the variant.
			int endIndexOfCountryCode = localeString.indexOf(country)
					+ country.length();
			// Strip off any leading '_' and whitespace, what's left is the
			// variant.
			variant = trimLeadingWhitespace(localeString
					.substring(endIndexOfCountryCode));
			if (variant.startsWith("_")) {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return (language.length() > 0 ? new Locale(language, country, variant)
				: null);
	}

	/**
	 * Determine the RFC 3066 compliant language tag, as used for the HTTP
	 * "Accept-Language" header.
	 * 
	 * @param locale
	 *            the Locale to transform to a language tag
	 * @return the RFC 3066 compliant language tag as String
	 */
	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage()
				+ (hasText(locale.getCountry()) ? "-" + locale.getCountry()
						: "");
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	// ---------------------------------------------------------------------

	/**
	 * Append the given String to the given String array, returning a new array
	 * consisting of the input array contents plus the given String.
	 * 
	 * @param array
	 *            the array to append to (can be <code>null</code>)
	 * @param str
	 *            the String to append
	 * @return the new array (never <code>null</code>)
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * Concatenate the given String arrays into one, with overlapping array
	 * elements included twice.
	 * <p>
	 * The order of elements in the original arrays is preserved.
	 * 
	 * @param array1
	 *            the first array (can be <code>null</code>)
	 * @param array2
	 *            the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were
	 *         <code>null</code>)
	 */
	public static String[] concatenateStringArrays(String[] array1,
			String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * Merge the given String arrays into one, with overlapping array elements
	 * only included once.
	 * <p>
	 * The order of elements in the original arrays is preserved (with the
	 * exception of overlapping elements, which are only included on their first
	 * occurence).
	 * 
	 * @param array1
	 *            the first array (can be <code>null</code>)
	 * @param array2
	 *            the second array (can be <code>null</code>)
	 * @return the new array (<code>null</code> if both given arrays were
	 *         <code>null</code>)
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * Turn given source String array into sorted array.
	 * 
	 * @param array
	 *            the source array
	 * @return the sorted array (never <code>null</code>)
	 */
	public static String[] sortStringArray(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * Copy the given Collection into a String array. The Collection must
	 * contain String elements only.
	 * 
	 * @param collection
	 *            the Collection to copy
	 * @return the String array (<code>null</code> if the passed-in Collection
	 *         was <code>null</code>)
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * Copy the given Enumeration into a String array. The Enumeration must
	 * contain String elements only.
	 * 
	 * @param enumeration
	 *            the Enumeration to copy
	 * @return the String array (<code>null</code> if the passed-in Enumeration
	 *         was <code>null</code>)
	 */
	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<String> list = Collections.list(enumeration);
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Trim the elements of the given String array, calling
	 * <code>String.trim()</code> on each of them.
	 * 
	 * @param array
	 *            the original String array
	 * @return the resulting array (of the same size) with trimmed elements
	 */
	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? element.trim() : null);
		}
		return result;
	}

	/**
	 * Remove duplicate Strings from the given array. Also sorts the array, as
	 * it uses a TreeSet.
	 * 
	 * @param array
	 *            the String array
	 * @return an array without duplicates, in natural sort order
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return toStringArray(set);
	}

	/**
	 * Split a String at the first occurrence of the delimiter. Does not include
	 * the delimiter in the result.
	 * 
	 * @param toSplit
	 *            the string to split
	 * @param delimiter
	 *            to split the string up with
	 * @return a two element array with index 0 being before the delimiter, and
	 *         index 1 being after the delimiter (neither element includes the
	 *         delimiter); or <code>null</code> if the delimiter wasn't found in
	 *         the given input String
	 */
	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @param charsToDelete
	 *            one or more characters to remove from each element prior to
	 *            attempting the split operation (typically the quotation mark
	 *            symbol), or <code>null</code> if no removal should occur
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was
	 *         <code>null</code> or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter, String charsToDelete) {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}
		Properties result = new Properties();
		for (String element : array) {
			if (charsToDelete != null) {
				element = deleteAny(element, charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(),
					splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter)
	 * @param trimTokens
	 *            trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array (only applies to
	 *            tokens that are empty after trimming; StringTokenizer will not
	 *            consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens (<code>null</code> if the input String was
	 *         <code>null</code>)
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters,
			boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of
	 * potential delimiter characters - in contrast to
	 * <code>tokenizeToStringArray</code>.
	 * 
	 * @param str
	 *            the input String
	 * @param delimiter
	 *            the delimiter between elements (this is a single delimiter,
	 *            rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str,
			String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of
	 * potential delimiter characters - in contrast to
	 * <code>tokenizeToStringArray</code>.
	 * 
	 * @param str
	 *            the input String
	 * @param delimiter
	 *            the delimiter between elements (this is a single delimiter,
	 *            rather than a bunch individual delimiter characters)
	 * @param charsToDelete
	 *            a set of characters to delete. Useful for deleting unwanted
	 *            line breaks: e.g. "\r\n\f" will delete all new lines and line
	 *            feeds in a String.
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str,
			String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * 
	 * @param str
	 *            the input String
	 * @return an array of Strings, or the empty array in case of empty input
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set. Note that this
	 * will suppress duplicates.
	 * 
	 * @param str
	 *            the input String
	 * @return a Set of String entries in the list
	 */
	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (String token : tokens) {
			set.add(token);
		}
		return set;
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param coll
	 *            the Collection to display
	 * @param delim
	 *            the delimiter to use (probably a ",")
	 * @param prefix
	 *            the String to start each element with
	 * @param suffix
	 *            the String to end each element with
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll,
			String delim, String prefix, String suffix) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(trimWhitespace(it.next().toString())).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param coll
	 *            the Collection to display
	 * @param delim
	 *            the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll,
			String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful
	 * for <code>toString()</code> implementations.
	 * 
	 * @param coll
	 *            the Collection to display
	 * @return the delimited String
	 */
	public static String collectionToCommaDelimitedString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param arr
	 *            the array to display
	 * @param delim
	 *            the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (ObjectUtils.isEmpty(arr)) {
			return "";
		}
		if (arr.length == 1) {
			return ObjectUtils.nullSafeToString(arr[0]);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for <code>toString()</code> implementations.
	 * 
	 * @param arr
	 *            the array to display
	 * @return the delimited String
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/*
	 * This method will check whether string is empty or it contains empty space
	 * or not
	 *//**
	 * Checks if is empty.
	 * 
	 * @param source
	 *            the source
	 * @return true, if is empty
	 */
	/*
	 * public static boolean isEmpty(String source) { boolean check = false;
	 * 
	 * if (source == null) { check = true; } else if
	 * (source.trim().equals(CommonConstants.EMPTY_STRING)) { check = true; }
	 * else if (source.trim().equalsIgnoreCase(CommonConstants.NULL_CONSTANT)) {
	 * check = true; } else { check = false; } return check;
	 * 
	 * }
	 */
	/**
	 * Determine whether the given array is empty: i.e. <code>null</code> or of
	 * zero length.
	 * 
	 * @param array
	 *            the array to check
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	public static boolean isDigit(char value) {
		return (Character.isDigit(value) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * Generate ddmmyyhhmmss in 24 hr format random number.
	 * 
	 * @return the string
	 */
	public static String generateDDMMYYHHMMSSIn24HrRandomNumber() {
		final SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyHHmmss");
		final Date now = new Date();
		return sdfDate.format(now);
	}

	public static int linearSearch(String array[], String key) {		
		int index = -1;
		if (!isEmpty(array)) {
			List<String> list = Arrays.asList(array);
			index = list.indexOf(key);
//			for (String name : array) {
//				index++;
//				if (name.equalsIgnoreCase(key)) {
//					break;
//				}
//			}
		}
		return index;
	}

	/**
	 * Check BigDecimal.
	 * 
	 * @param source
	 *            the source
	 * @return the integer
	 */
	public static Boolean isEmptyBigDecimal(BigDecimal source) {
		Boolean flag = false;
		if (source == null || source == BigDecimal.ZERO) {
			source = null;
			flag = true;
		}
		return flag;
	}
	 public static String customFormat(String pattern, double value ) {
	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      String output = myFormatter.format(value);
	      LOGGER.trace("customFormat :"+value + "  " + pattern + "  " + output);
	     // System.out.println("customFormat :"+value + "  " + pattern + "  " + output);
	      return output;
	   }
	 public static String customFormat(String pattern, int value ) {
	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      String output = myFormatter.format(value);
	      LOGGER.trace("customFormat :"+value + "  " + pattern + "  " + output);
	     // System.out.println("customFormat :"+value + "  " + pattern + "  " + output);
	      return output;
	   }

	  /* static public void main(String[] args) {

	      customFormat("###.###", 12456.789);
	      customFormat("###.##", 123456.789);
	      customFormat("000000.000", 123.78);
	      customFormat("$###,###.###", 12345.67);  
	   }*/
}
