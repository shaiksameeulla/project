package com.wdpr.eai.payment.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONConverter {
	
	private JSONConverter() {}
	
	public static String convertJavaToJSON(Object javaObject) {
		
		String jsonString = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			jsonString = objectMapper.writeValueAsString(javaObject);
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		return jsonString;
	}
	
	public static Object convertJSONToJava(String json, Class<?> objType) {
		Object jsonObject = null;
		try {
			jsonObject = new ObjectMapper().readValue(json, objType);
		} catch (Exception e) {
			e.getStackTrace();
		}
				
		return jsonObject;
	}

}
