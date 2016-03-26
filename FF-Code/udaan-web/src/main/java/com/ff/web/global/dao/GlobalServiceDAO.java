package com.ff.web.global.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.umc.ApplScreenDO;

public interface GlobalServiceDAO {

	/**
	 * 
	 * To Populate third Party type List
	 * 
	 * @param typeName
	 *            <ul>
	 *            <li>third party name
	 *            </ul>
	 * @return StockStandardTypeDO -> List of StockStandardTypeDO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<StockStandardTypeDO> getAllStockStandardType(String typeName)
			throws CGSystemException;

	public ApplScreenDO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGSystemException;

}
