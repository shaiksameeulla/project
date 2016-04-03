package com.ff.ud.utils;

import java.util.Collection;
import java.util.List;

public class StringUtils {

	
	public static String getStringForInQuery(@SuppressWarnings("rawtypes") Collection arrayList){
		String commanSeprated=arrayList.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
		
		if(isNullOrEmpty(commanSeprated)){
			commanSeprated=" '' ";
		}
		
		return commanSeprated;
		
	}
	public static boolean isNullOrEmpty(String data){
		boolean status=true;
		if(null != data && !"".equals(data) && !"null".equals(data)){
			status=false;
		}
		return status;
	}
	
	public static boolean isNullOrEmptyOrZero(String data){
		boolean status=true;
		if(null != data && !"".equals(data) && !"null".equals(data) &&  !"0".equals(data) && !"0.0".equals(data)){
			status=false;
		}
		return status;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public static Boolean isEmptyColletion(Collection arrayList) {
		Boolean isListEmpty = true;
		if (arrayList != null && !arrayList.isEmpty()) {
			isListEmpty = false;

		}
		return isListEmpty;
	}

	/**
	 * 
	 * @param strValue String value which we want to convert Integer
	 * @return integer value of the given string if exception occurs return 0 
	 * 
	 */
	public static Integer getIntegerValueFromString(String strValue){
		Integer intValue=null;
		try{
			intValue=Integer.valueOf(strValue);
		}catch(Exception e){
			//e.printStackTrace();
			intValue=2;
		}
		return intValue;
	}
	
	public static Double getDoubleValueFromString(String strValue){
		Double doubleValue=null;
		try{
			doubleValue=Double.valueOf(strValue);
		}catch(Exception e){
			//e.printStackTrace();
			doubleValue=Double.valueOf(0);
		}
		return doubleValue;
	}
	

	public static Object getOneObject(@SuppressWarnings("rawtypes") List list){
		if(isEmptyColletion(list)){
			return null;
		}else if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
			
	}

	
	
}
