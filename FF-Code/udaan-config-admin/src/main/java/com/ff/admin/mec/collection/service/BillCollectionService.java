package com.ff.admin.mec.collection.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.mec.collection.BillCollectionTO;
import com.ff.to.billing.BillTO;

public interface BillCollectionService {

	/**
	 * To get all customers
	 * 
	 * @return customerTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException;

	/**
	 * To save or update bill collection details
	 * 
	 * @param billCollectionTO
	 * @return billCollectionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BillCollectionTO saveOrUpdateBillCollection(
			BillCollectionTO billCollectionTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To search bill collection details
	 * 
	 * @param transactionNo
	 * @param collectionType
	 * @return billCollectionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BillCollectionTO searchBillCollectionDtls(String transactionNo,
			String collectionType) throws CGBusinessException,
			CGSystemException;

	/**
	 * To searh collection details by txn.
	 * 
	 * @param transactionNo
	 * @return billCollectionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BillCollectionTO searchCollectionDtlsByTxnNo(String transactionNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate collection txn.
	 * 
	 * @param billCollectionTO
	 * @return billCollectionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public BillCollectionTO validateCollection(BillCollectionTO billCollectionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get customers by pickup delivery location
	 * 
	 * @param officeId
	 * @return customerTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException;

	/**
	 * To get collection details by bill number
	 * 
	 * @param billTOs
	 * @return billTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<BillTO> getCollectionDetailsByBillNumber(List<BillTO> billTOs)
			throws CGSystemException, CGBusinessException;

}
