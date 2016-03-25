/**
 * 
 */
package com.ff.admin.mec.collection.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.mec.BankTO;

/**
 * @author prmeher
 * 
 */
public interface CollectionCommonService {

	/**
	 * To get Collection status
	 * 
	 * @param transactionNo
	 * @return collectionStatus
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public String getCollectionStatus(String transactionNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get payment mode details
	 * 
	 * @param MECProcessCode
	 * @return list of payment modes
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<PaymentModeTO> getPaymentModeDtls(String MECProcessCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all bank details
	 * 
	 * @return List of BankTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<BankTO> getAllBankDtls() throws CGBusinessException,
			CGSystemException;

	/**
	 * To update txn. status by collection id
	 * 
	 * @param status
	 * @param collectionId
	 * @return boolean
	 * @throws CGSystemException
	 */
	public boolean updateCollectionStatus(String status, Integer collectionId)
			throws CGBusinessException, CGSystemException;

}
