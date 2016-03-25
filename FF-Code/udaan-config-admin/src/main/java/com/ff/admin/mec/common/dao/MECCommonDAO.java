package com.ff.admin.mec.common.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.collection.BulkCollectionValidationWrapperDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;

/**
 * @author hkansagr
 */

public interface MECCommonDAO {

	/**
	 * To get stock std type from DB
	 * 
	 * @param typeName
	 * @return List of StockStandardTypeDO
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGSystemException;

	/**
	 * @desc get CustDO By CustName
	 * @param custName
	 * @return CustomerDO
	 * @throws CGSystemException
	 */
	public CustomerDO getCustDOByCustName(String custName)
			throws CGSystemException;

	/**
	 * To get Liability Payment Mode
	 * 
	 * @param MECProcessCode
	 * @return StockStandardTypeDO List
	 * @throws CGSystemException
	 */
	List<StockStandardTypeDO> getLiabilityPaymentMode(String MECProcessCode)
			throws CGSystemException;

	/**
	 * To get GL Details By GL Id
	 * 
	 * @param glId
	 * @return glMasterDOs
	 * @throws CGSystemException
	 */
	GLMasterDO getGLDtlsById(Integer glId) throws CGSystemException;

	/**
	 * To check whether consg is booked or not.
	 * 
	 * @param consgNo
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean isConsignmentBooked(String consgNo) throws CGSystemException;

	/**
	 * To get collection details for recalculation petty cash
	 * 
	 * @return collectionDOs
	 * @throws CGSystemException
	 */
	List<CollectionDO> getCollectionDtlsForRecalculation()
			throws CGSystemException;

	/**
	 * To update collection recalculation flag
	 * 
	 * @param collectionIds
	 * @throws CGSystemException
	 */
	void updateCollectionRecalcFlag(List<Integer> collectionIds)
			throws CGSystemException;

	/**
	 * To get cheque(s) deposit slip details
	 * 
	 * @param reportingRHOId
	 * @return collectionDOs
	 * @throws CGSystemException
	 */
	List<CollectionDO> getChequeDepositSlipDtls(Integer reportingRHOId)
			throws CGSystemException;
	
	/**
	 * To get collection Id and collection date
	 * 
	 * @return collectionDOs
	 * @throws CGSystemException
	 */
	List<Object[]> getCollectionIdAndCollectionDateForPettyCash(Date currentDateObject)
			throws CGSystemException;
	
	/**
	 * Returns customized list of required collection details
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return List<BulkCollectionValidationWrapperDO>
	 * @throws CGSystemException
	 */
	List<BulkCollectionValidationWrapperDO> getCollectionDetailsForBulkValidation(Integer customerId, Date fromDate, Date toDate, Integer firstResult) 
			throws CGSystemException;
	
	
	/**
	 * Gets the total number of records to be processed
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return Integer
	 * @throws CGSystemException
	 */
	Integer getTotalNumberOfRecordsForBulkValidation(Integer customerId, Date fromDate, Date toDate) throws CGSystemException;

}
