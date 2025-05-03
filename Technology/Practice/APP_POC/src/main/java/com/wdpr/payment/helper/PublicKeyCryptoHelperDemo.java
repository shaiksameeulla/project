package com.wdpr.payment.helper;

import java.security.Key;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdpr.eai.payment.transformer.CardDescriptorTypeMapper;

import dpr.disney.com.adaptivepayment.common.CardDescriptorType;

public class PublicKeyCryptoHelperDemo {

	private static ObjectMapper staticMaper = new ObjectMapper();
	private static Properties appProperties = null;
	
	public static void main(String[] args) {
		
		try {
			 final Key symmKey = CryptoHelper.getSymmetricKey();
			 CardDescriptorType type= CardDescriptorTypeMapper.getInstance().mapCardDescriptorType();
			
			Map<String,String> map = encryptJavaObject(type,true,symmKey);
			System.out.println(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String getPropertyValue(String key) {
		if(appProperties == null) {
			appProperties  = PropertiesUtil.loadProperties(null);
		}
		return appProperties.getProperty(key, (String) null);
	}
	
	public static Map<String, String> encryptJavaObject(Object pojo, boolean useSymmetricKey, Key symmetricKey)
			throws Exception {
		String jsonMsg = null;
		if (pojo instanceof String) {
			jsonMsg = (String) pojo;
		} else {
			jsonMsg = generateJson(pojo);
		}

		String ppKeystore = getPropertyValue("pp.keystore.file");
		String pwd = getPropertyValue("pp.keystore.password");
		String encryptAlias = getPropertyValue("pp.keystore.alias");
		String keystoretype = getPropertyValue("pp.keystore.type");
		Map result = CryptoHelper.encryptMessage(useSymmetricKey, jsonMsg, symmetricKey, keystoretype, ppKeystore, pwd,
				encryptAlias);
		return result;
	}
	private static String generateJson(Object obj) throws JsonProcessingException {
		return staticMaper.writeValueAsString(obj);
	}

}
