/**
 * 
 */
package com.capgemini.lbs.framework.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mohammes
 *
 */
public class SerialNumberGenerator {
	private static final Logger logger = LoggerFactory
	.getLogger(SerialNumberGenerator.class);

/*
* This method will calculate the list of serial numbers depending on the number which you have inputted 
*/
	
	public static List<String> calculateEndSerialNumber(String startSerialNUmber, Integer seriesLength)
	throws NumberFormatException {
		List<String> siNums=null;
		
		if(isAlphaNumericSerialNumber(startSerialNUmber)){
			siNums=new ArrayList<String>(0);
		char alphabet=startSerialNUmber.charAt(0);
		alphabet = Character.toUpperCase(alphabet);
		int len=startSerialNUmber.substring(1).length();
		Integer number=Integer.parseInt(startSerialNUmber.substring(1));
		int len2=number.toString().length();
		int diff=len-len2;
		String format="";
		int zero=0;
		if(diff == zero){
			format="%"+len2+"d";
		}else{
			format="%0"+len+"d";
		}
		for(int counter=0;counter<seriesLength;counter++){
			String formatted = String.format(format, number+counter);  
			siNums.add(alphabet+formatted);
		}
		}else if(isNumericSerialNumber(startSerialNUmber)){
			Long number= Long.parseLong(startSerialNUmber);
			siNums=new ArrayList<String>(0);
			for(int counter=0;counter<seriesLength;counter++){
				Long tempValue =  number+counter;
				siNums.add( tempValue.toString());
			}
		}
		return siNums;
	}
	/*
	* This method will check whether the given series is numeric or not 
	*/
	public static Boolean isNumericSerialNumber(String serialNumber){
		Boolean isValid =  Boolean.FALSE;
		if(!StringUtil.isStringEmpty(serialNumber)){
				try {
					 Long.parseLong(serialNumber);
					isValid =  Boolean.TRUE;
				}catch (Exception e) {
					logger.error("SerialNumberGenerator::isNumericSerialNumber---->Exception",e);
				}
			
		}
		
		return isValid;
	}
	/*
	* This method will check whether the given series is AlphaNumeric or not 
	*/
	public static Boolean isAlphaNumericSerialNumber(String serialNumber){
		Boolean isValid =  Boolean.FALSE;
		if(!StringUtil.isStringEmpty(serialNumber)){
			//if(serialNumber.trim().length() <= BusinessConstants.NUMERIC_SERIAL_NUMBER_LENGTH){//checking maximum length for consignment 
				try {
					
					if(Character.isLetter(serialNumber.charAt(0))){
						 Long.parseLong(serialNumber.substring(1));
						 isValid =  Boolean.TRUE;
					}else{
						 isValid =  Boolean.FALSE;
					}
					
					
				} catch (Exception e) {
					logger.error("SerialNumberGenerator::isAlphaNumericSerialNumber---->Exception",e);
				}
			//}
		}
		
		return isValid;
	}
	/*public static void main(String[] args){
		String a1="A00000000";
		String a2="100000000000";
		System.out.println("isAlphaNumericSerialNumber :"+a1+" : "+isAlphaNumericSerialNumber(a1));
		System.out.println("isAlphaNumericSerialNumber :"+a2+" : "+isAlphaNumericSerialNumber(a2));
		System.out.println("isNumericSerialNumber :"+a1+" : "+isNumericSerialNumber(a1));
		System.out.println("isNumericSerialNumber :"+a2+" : "+isNumericSerialNumber(a2));
		
		System.out.println("calculateEndSerialNumber :"+a2+" : "+calculateEndSerialNumber(a2,5));
		System.out.println("calculateEndSerialNumber :"+a1+" : "+calculateEndSerialNumber(a1,5));
	}*/
}
