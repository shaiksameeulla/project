package com.ff.web.global.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.ApplScreensTO;

public interface GlobalService {

	/**
	 * 
	 * To Populate third Party type List
	 * 
	 * @param typeName
	 *            <ul>
	 *            <li>third party type
	 *            </ul>
	 * @return stockStandardTypeTO - list of all stockstdtypeTO
	 * @throws CGBusinessException
	 *             - If any violation of Business Rule while converting do to to
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<StockStandardTypeTO> getAllStockStandardType(String typeName)
			throws CGBusinessException, CGSystemException;

	public ApplScreensTO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGBusinessException, CGSystemException;

}
