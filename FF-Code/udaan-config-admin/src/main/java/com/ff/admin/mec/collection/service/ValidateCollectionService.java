/**
 * 
 */
package com.ff.admin.mec.collection.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.mec.collection.ValidateCollectionEntryTO;

/**
 * @author prmeher
 * 
 */
public interface ValidateCollectionService {

	/**
	 * To seach collection details for validation of txn
	 * 
	 * @param frmDate
	 * @param toDate
	 * @param stationId
	 * @param officeId
	 * @param headerStatus
	 * @param headerTransNo
	 * @return ValidateCollectionEntryTO object
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ValidateCollectionEntryTO searchCollectionDetlsForValidation(
			String frmDate, String toDate, String stationId, String officeId,
			String headerStatus, String headerTransNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate all selected txn(s).
	 * 
	 * @param txnsList
	 * @param updatedBy
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void validateTxns(List<String> txnsList, Integer updatedBy)
			throws CGBusinessException, CGSystemException;

}
