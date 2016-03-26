package com.ff.sap.integration.common.service;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.utils.StringUtil;

public class CommonSapIntegrationServiceImpl implements CommonSapIntegrationService{
	
	private static final Logger logger = Logger.getLogger(CommonSapIntegrationServiceImpl.class);

	@Override
	public String removeJunkCharactersFromString(String inputString) {
		String resultString = inputString;
		if (!StringUtil.isStringEmpty(inputString)) {
			for (int i=0; i < inputString.length(); i++) {
				int charInt = (int)inputString.charAt(i);
				if (!((charInt >=32 && charInt < 127) || charInt == 8 || charInt ==9 || charInt == 13 || charInt == 10)) {
					resultString = inputString.replace(inputString.charAt(i), (char)32);
					logger.warn("CommonSapIntegrationServiceImpl :: removeJunkCharactersFromString :: Junk character detected in string : [" + resultString + "]");
				}
			}
		}
		else {
			logger.warn("CommonSapIntegrationServiceImpl :: removeJunkCharactersFromString :: The input string is empty");
			resultString = null;
		}
		return resultString;
	}
}
