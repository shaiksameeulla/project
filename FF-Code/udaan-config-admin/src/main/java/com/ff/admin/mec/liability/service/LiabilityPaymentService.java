package com.ff.admin.mec.liability.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.mec.LiabilityTO;

/**
 * @author amimehta
 */

public interface LiabilityPaymentService {

	/**
	 * To save or update expense details
	 * 
	 * @param to
	 * @return liabilityTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	LiabilityTO saveOrUpdateLiability(LiabilityTO to)
			throws CGBusinessException, CGSystemException;

	LiabilityTO getLiabilityDetails(Integer regionId, Integer custId,
			String custCode) throws CGBusinessException, CGSystemException;

	LiabilityTO searchLiabilityDetails(String txnNo)
			throws CGBusinessException, CGSystemException;

	public LiabilityTO saveOrUpdateLiabilityDtlsForNext(LiabilityTO liabilityTO)
			throws CGBusinessException, CGSystemException;

	LiabilityTO getLiabilityDetailsFromCollection(Integer regionId,
			Integer custId, String custCode) throws CGBusinessException,
			CGSystemException;

	LiabilityTO saveLiability(LiabilityTO to) throws CGBusinessException,
			CGSystemException;

	/**
	 * Process paid liability consingments.
	 *
	 * @param consgList the consg list
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	void processPaidLiabilityConsingments(List<Integer> consgList)
			throws CGBusinessException, CGSystemException;
}
