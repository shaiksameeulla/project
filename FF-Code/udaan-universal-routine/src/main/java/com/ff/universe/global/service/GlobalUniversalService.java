package com.ff.universe.global.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

public interface GlobalUniversalService {

	/**
	 * Get the Config Params of the application
	 * 
	 * @inputparam
	 * @return Map<String, String> object of ConfigParams
	 * @throws CGSystemException
	 */
	Map<String, String> getConfigParams() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the standard types by type name.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the standard types by type name
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<StockStandardTypeTO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the config params.
	 * 
	 * @param paramName
	 *            the param name
	 * @return the config params
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	String getConfigParamValueByName(String paramName)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get number in words
	 * 
	 * @param enteredNo
	 * @return enteredNoInWord
	 * @throws CGBusinessException
	 */
	String getNumberInWords(Long enteredNo) throws CGBusinessException;

}
