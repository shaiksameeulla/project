/**
 * 
 */
package com.ff.admin.mec.collection.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.collection.CollectionDO;

/**
 * @author prmeher
 * 
 */
public interface ValidateCollectionDAO {

	/**
	 * To search collection details for validation of transaction no.
	 * 
	 * @param fromDate
	 * @param to_Date
	 * @param officeId
	 * @param headerStatus
	 * @param headerTransNo
	 * @return
	 * @throws CGSystemException
	 */
	public List<CollectionDO> searchCollectionDetlsForValidation(Date fromDate,
			Date to_Date, Integer officeId, String headerStatus,
			String headerTransNo) throws CGSystemException;

	/**
	 * To validate selected txn(s)
	 * 
	 * @param txnsList
	 * @param updatedBy
	 * @throws CGSystemException
	 */
	void validateTxns(List<String> txnsList, Integer updatedBy)
			throws CGSystemException;

}
