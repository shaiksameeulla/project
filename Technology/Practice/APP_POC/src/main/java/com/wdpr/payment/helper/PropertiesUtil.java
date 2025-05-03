package com.wdpr.payment.helper;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Properties loadProperties(String filePath) {
		String propertyFileName = null == filePath ? "app-sdk.properties" : filePath;
		Properties appProperties = new Properties();

		try {
			appProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName));
		} catch (IOException arg3) {
			arg3.printStackTrace();
		}

		return appProperties;
	}

}
