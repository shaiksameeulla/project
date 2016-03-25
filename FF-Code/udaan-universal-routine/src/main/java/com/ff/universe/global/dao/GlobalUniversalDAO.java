package com.ff.universe.global.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

public interface GlobalUniversalDAO {
	
	/**
	 * Get the Config Params of the application 
	 * @inputparam 
	 * @return List<ConfigurableParamsDO>
	 * @throws CGSystemException
	 */
	public List<ConfigurableParamsDO> getConfigurabParams()
			throws CGSystemException;

	/**
	 * Gets the standard types by type name.
	 *
	 * @param typeName the type name
	 * @return the standard types by type name
	 * @throws CGSystemException the cG system exception
	 */
	public List<StockStandardTypeDO> getStandardTypesByTypeName(String typeName)
			throws CGSystemException;

	/**
	 * Gets the configurab params.
	 *
	 * @param paramName the param name
	 * @return the configurab params
	 * @throws CGSystemException the cG system exception
	 */
	List<ConfigurableParamsDO> getConfigParamValueByName(String paramName)
			throws CGSystemException;
	
	/**
	 * Updates the configurab param
	 *
	 * @param paramName the param name
	 * @return the configurable params
	 * @throws CGSystemException the cG system exception
	 */
	public void updateConfigurableParamValueByParamName(String paramName, String paramValue) throws CGSystemException;
}
