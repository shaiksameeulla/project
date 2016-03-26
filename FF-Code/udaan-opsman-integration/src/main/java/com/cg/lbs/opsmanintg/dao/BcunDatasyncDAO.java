package com.cg.lbs.opsmanintg.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.opsmanintg.to.OutboundBranchDataTO;
import com.cg.lbs.opsmanintg.to.TwoWayWriteTO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public interface BcunDatasyncDAO {
	/**
	 * This function returns data based on configured query and row count
	 * @param namedQuery is the name of query available in hbm file and used to fetch the data
	 * @param rowCount is the total number of row database has to return.
	 * @return list of selected objects.
	 */
	public List<CGBaseDO> getDataByNamedQueryAndRowCount(String namedQuery, Integer rowCount);
	
	/**
	 * This function returns data based on configured query and row count
	 * @param namedQuery is the name of query available in hbm file and used to fetch the data
	 * @param rowCount is the total number of row database has to return.
	 * @param officeId is the office for which data has to be fetched
	 * @return list of selected objects.
	 */
	public List<CGBaseDO> getOfficeDataByNamedQueryAndRowCount(String namedQuery, Integer rowCount, Integer officeId);
	
	/**
	 * This function returns data based on configured query
	 * @param namedQuery namedQuery is the name of query available in hbm file and used to fetch the data
	 * @return list of selected objects.
	 */
	public List<? extends CGBaseDO> getDataByNamedQuery(String namedQuery);
	
	/**
	 * This function is used for save/update the record in database
	 * @param baseEntity is entity which has to be saved in database
	 */
	public void saveOrUpdateTransferedEntity(CGBaseDO baseEntity);
	
	/**
	 * This function is used for save/update the record in database
	 * @param baseEntity is list of entities which have to be saved in database
	 */
	public void saveOrUpdateTransferedEntities(List<CGBaseDO> baseEntity);
	
	/**
	 * @param namedQuery
	 * @param param
	 * @param value
	 * @return
	 */
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery, String param, Object value) ;
	
	/**
	 * This function is for providing service to the formatter classes. these services are intended to 
	 * provide availability of spring services to the formatter classes
	 * @param namedQuery is the name of query which has to be execute
	 * @param params name of parameter on which configured query will work
	 * @param values is the value of passes parameter
	 * @return selected rows as a base dao containing selected object
	 */
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery, String[] params, Object[] values) ;
	
	/**
	 * This function is for providing service to the formatter classes. these services are intended to 
	 * provide availability of spring services to the formatter classes
	 * @param namedQuery is the name of query which has to be execute
	 * @param params name of parameters on which configured query will work
	 * @param values is the values of passes parameter
	 * @return selected rows as a base dao containing selected object
	 */
	public List<OutboundOfficePacketDO> getOutboundBranchData(OutboundBranchDataTO inputTO) ;
	
	/**
	 * This function is for providing service to the formatter classes. these services are intended to 
	 * provide availability of spring services to the formatter classes
	 * @param namedQuery is the name of query which has to be execute
	 * @param params name of parameters on which configured query will work
	 * @param values is the values of passes parameter
	 * @param rowCount number of rows has to be fetched
	 * @return selected rows as a base dao containing selected object
	 */
	public List<CGBaseDO> getDataByNamedQueryNamedParamAndRowCount(String namedQuery, String paramName, Object paramValue, Integer rowCount) ;
	
	/**
	 * This function is used to get row id<primary key> of the data
	 * @param queryName is the query which has to be execute
	 * @param params are the parameters need to execute the query
	 * @param values are parameter values need to execute the query
	 * @return id of the row.
	 */
	public Integer getUniqueId(String queryName, String[] params,Object[] values);
	
	public List<CGBaseDO> getOutboundMasterDataFromDB(String hql, Integer maxRowFetch);

	/**
	 * Delete from database.
	 *
	 * @param qryName the qry name
	 * @param paramWithValues the param with values
	 * @return true, if successful
	 * @throws CGSystemException 
	 */
	boolean loadAndDeleteFromDatabase(String qryName,
			Map<Object, Object> paramWithValues) throws CGSystemException;

	/**
	 * Update entity status.
	 *
	 * @param baseEntity the base entity
	 */
	void updateEntityStatus(CGBaseDO baseEntity);

	/**
	 * Gets the numbers by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the numbers by named query and named param
	 */
	List<Integer> getNumbersByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values);
	
	public void updatePacketStatus(OutboundBranchDataTO packetInfo, String qryName) throws CGSystemException ;

	List<? extends Number> getNumberByNamedQuery(String namedQuery);

	/**
	 * Gets the anonymus type data by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the anonymus type data by named query and named param
	 */
	List<?> getAnonymusTypeDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values);

	/**
	 * Gets the destination office for stock customer.
	 *
	 * @param namedQuery the named query
	 * @param officeType the office type
	 * @param loggedInoffice the logged inoffice
	 * @param officeList the office list
	 * @return the destination office for stock customer
	 */
	List<Integer> getDestinationOfficeForStockCustomer(String namedQuery,
			String officeType, Integer loggedInoffice, List<Integer> officeList);
	
	/**
	 * Gets the cG base do by id.
	 *
	 * @param twoWayWriteTO the two way write to
	 * @return the cG base do by id
	 */
	public CGBaseDO getCGBaseDOById(TwoWayWriteTO twoWayWriteTO) throws CGSystemException;
	
	/**
	 * Gets customer details from customer Id
	 * @param custId - customer Id
	 * @return CustomerDO - customer details
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CustomerDO getCustomerDtlsFromCustId(Integer custId) throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets paperwork when pincode is supplied as the input parameter
	 * @param pincode
	 * @return List<CNPaperWorksDO> - list of paperworkDos
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CNPaperWorksDO> getPaperWorkByPincode(String pincode) throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets office details from either office Id or office code
	 * @param officeId
	 * @param officeCode
	 * @return OfficeDO - office details
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeDO getOfficeByIdOrCode(Integer officeId, String officeCode) throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets city details from city code
	 * @param cityCode
	 * @return CityDO - city details
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CityDO getCityDtlsByCityCode(String cityCode) throws CGSystemException, CGBusinessException;
	
	/**
	 * Gets city details from either city Id or city code
	 * @param cityId
	 * @param cityCode
	 * @return CityDO - city details
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CityDO getCityDtlsByCityIdOrCode(Integer cityId,String cityCode) throws CGSystemException, CGBusinessException;
	
	public ConfigurableParamsDO getStockSeriesFromConfigParams(String searchParam) throws CGSystemException, CGBusinessException;
	
	public List<CGBaseDO> getDataByNamedQueryAndFetchingParams(String namedQuery,Integer startLimit,Integer endLimit);
	
	public List<CGBaseDO> getDataByNamedQueryNamedParamAndFetchingParams(String namedQuery,String paramName,Collection<Integer> paramValue,
			Integer startLimit,Integer rowCount);
	
	public List<CGBaseDO> updatePublishedEntityStatusForManifest(List<CGBaseDO> unSyncedData,String statusFlag);
	
	public void updatePublishedEntityStatus(CGBaseDO baseDo);
	
	public void saveDrsData(CGBaseDO baseEntity);
	
	public void updateBookingStatusAsRejected(List<CGBaseDO> rejectedList, String recordStatus);
	
}
