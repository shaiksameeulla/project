package com.ff.admin.mec.collection.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.mec.collection.CNCollectionTO;

public interface CollectionCommonDAO {

	public boolean saveOrUpdateCollection(CollectionDO collectionDO)
			throws CGSystemException;

	public String getCollectionStatus(String transactionNo)
			throws CGSystemException;

	/**
	 * @param cnCollectionTO
	 * @return collectionDtlsDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CollectionDtlsDO> getAllDeliverdConsgDtlsByDate(
			CNCollectionTO cnCollectionTO) throws CGSystemException;

	public CollectionDO searchCollectionDtlsByTxnNo(String transactionNo)
			throws CGSystemException;

	public CollectionDO saveOrUpdateCNCollection(CollectionDO collectionDO)
			throws CGSystemException;

	/**
	 * To get All Delivered Consignment Details and save for Collection details
	 * 
	 * @param currentDate
	 * @param originOffId
	 * @return boolean
	 * @throws CGSystemException
	 */
	/*
	 * public boolean getAllDeliveredConsgForCollectionDtls(Date currentDate,
	 * Integer originOffId) throws CGSystemException;
	 */

	/**
	 * To get All Delivered Consignment Details for Collection details
	 * 
	 * @param currentDate
	 * @param originOffId
	 * @return deliveryDtlDOs
	 * @throws CGSystemException
	 */
	public List<DeliveryDetailsDO> getAllDeliveredConsgForCollectionDtls(
			CNCollectionTO cnCollectionTO) throws CGSystemException;

	/**
	 * To save all delivered CN details to Collection details
	 * 
	 * @param collectionDtlsDOs
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean saveAllDeliveredCNToCollectionDtls(
			List<CollectionDtlsDO> collectionDtlsDOs) throws CGSystemException;

	/**
	 * To get All Delivered CN Details for Expense details
	 * 
	 * @param cnCollectionTO
	 * @return deliveryDetailsDOs
	 * @throws CGSystemException
	 */
	public List<DeliveryDetailsDO> getAllDeliveredDtlsForExpenseDtls(
			CNCollectionTO cnCollectionTO) throws CGSystemException;

	/**
	 * To update CN Delivery Date for Expense CN(s) details.
	 * 
	 * @param collectionDtlsDO
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean updateCnDeliveryDate(CollectionDtlsDO collectionDtlsDO)
			throws CGSystemException;

	/**
	 * To validate txn. by collection Id
	 * 
	 * @param status
	 * @param collectionId
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean updateCollectionStatus(String status, Integer collectionId)
			throws CGSystemException;

}
