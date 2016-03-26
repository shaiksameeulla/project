package com.ff.web.manifest.rthrto.dao;

import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.manifest.rthrto.RthRtoValidationTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoValidationDAO.
 */
public interface RthRtoValidationDAO {

	/**
	 * Save or update consignment return.
	 *
	 * @param consignmentReturnDO the consignment return do
	 * @throws CGSystemException the cG system exception
	 */
	void saveOrUpdateConsignmentReturn(ConsignmentReturnDO consignmentReturnDO)
			throws CGSystemException;
	
	/**
	 * Gets the data list by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param param the param
	 * @param value the value
	 * @return the data list by named query and named param
	 * @throws CGSystemException the cG system exception
	 */
	public List<? extends CGBaseDO> getDataListByNamedQueryAndNamedParam(
			String namedQuery, String param, Object value)
			throws CGSystemException;

	/**
	 * This function is for providing service to the formatter classes. these services are intended to
	 * provide availability of spring services to the formatter classes
	 *
	 * @param namedQuery is the name of query which has to be execute
	 * @param params name of parameter on which configured query will work
	 * @param values is the value of passes parameter
	 * @return selected rows as a base dao containing selected object
	 * @throws CGSystemException the cG system exception
	 */
	public List<? extends CGBaseDO> getDataListByNamedQueryAndNamedParam(
			String namedQuery, String[] params, Object[] values)
			throws CGSystemException;


	/**
	 * Gets the data by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param param the param
	 * @param value the value
	 * @return the data by named query and named param
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseDO getDataByNamedQueryAndNamedParam(String namedQuery,
			String param, Object value) throws CGSystemException;
	
	/**
	 * Gets the data by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the data by named query and named param
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseDO getDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values) throws CGSystemException;

	/**
	 * Update on hold consg email status.
	 *
	 * @param consignmentReturnIds the consignment return ids
	 * @throws CGSystemException the cG system exception
	 */
	void updateOnHoldConsgEmailStatus(List<Integer> consignmentReturnIds)
			throws CGSystemException;

	/**
	 * Gets the consignment return ids.
	 *
	 * @param reasonCode the reason code
	 * @param returnType the return type
	 * @return the consignment return ids
	 * @throws CGSystemException the cG system exception
	 */
	List<Integer> getConsignmentReturnIds(String reasonCode, String returnType)
			throws CGSystemException;

	/**
	 * Checks if is consg manifested.
	 *
	 * @param rthRtoValidationTO the rth rto validation to
	 * @return true, if is consg manifested
	 * @throws CGSystemException the cG system exception
	 */
	boolean isConsgManifested(RthRtoValidationTO rthRtoValidationTO)
			throws CGSystemException;
}
