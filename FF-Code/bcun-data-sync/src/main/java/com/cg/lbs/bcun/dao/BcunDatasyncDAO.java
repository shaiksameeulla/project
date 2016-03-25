package com.cg.lbs.bcun.dao;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGBcunInbundDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.bcun.to.ManualDownloadInputTO;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.to.TwoWayWriteTO;
import com.ff.domain.bcun.OutboundDataPacketWrapperDO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.delivery.DeliveryDetailsDO;

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
	 * Gets the packet details for manual download.
	 *
	 * @param manualDownLoadTO the manual down load to
	 * @return the packet details for manual download
	 * @throws CGSystemException the cG system exception
	 */
	List<OutboundDataPacketWrapperDO> getPacketDetailsForManualDownload(
			ManualDownloadInputTO manualDownLoadTO) throws CGSystemException;

	/**
	 * Update downloaded packet status.
	 *
	 * @param baseEntities the base entities
	 */
	void updateDownloadedPacketStatus(List<OutboundOfficePacketDO> baseEntities);

	/**
	 * Gets the cG base do by id.
	 *
	 * @param twoWayWriteTO the two way write to
	 * @return the cG base do by id
	 */
	public CGBaseDO getCGBaseDOById(TwoWayWriteTO twoWayWriteTO) throws CGSystemException;

	/**
	 * Save or update transfered entities for rate contract.
	 *
	 * @param primaryKeyList the primary key list
	 * @param qryName the qry name
	 * @throws CGSystemException the CG system exception
	 */
	void saveOrUpdateTransferedEntitiesForRateContract(
			List<Integer> primaryKeyList, String qryName);

	/**
	 * Update consg billing status.
	 *
	 * @param consgNumber the consg number
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	public boolean updateConsgBillingStatus(String consgNumber) throws CGSystemException;

	void persistOrUpdateTransferedEntity(CGBaseDO baseEntity);

	List<OutboundDataPacketWrapperDO> getDataPacketByOffice(
			OutboundBranchDataTO inputTO);


	void updateBcunDataTransferFlag(List<CGBcunInbundDO> cgBcunInboundList,
			String qryName);

	boolean updateConsgStatusForDelivery(DeliveryDetailsDO consgDO)
			throws CGSystemException;
	
	/**
	 * Getting the reconciliation data by transaction date
	 * @param configPropertyTO
	 * @return
	 * @throws CGSystemException
	 */
	List<? extends CGBaseDO> getReconcillationData(ReconcillationConfigPropertyTO configPropertyTO) throws CGSystemException;
	/**
	 * Getting the Reconciliation data by transaction offId and transaction Date for blob creation
	 * @param configPropertyTO
	 * @return
	 * @throws CGSystemException
	 */
	List<? extends CGBaseDO> getReconcillationDataForBlobCreation(ReconcillationConfigPropertyTO configPropertyTO) throws CGSystemException;

	boolean executeReleasedSQLScripts(String sqlQuery)
			throws CGSystemException;
	
}
