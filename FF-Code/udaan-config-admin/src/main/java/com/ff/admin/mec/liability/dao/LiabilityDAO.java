package com.ff.admin.mec.liability.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.LiabilityCollectionWrapperDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;

/**
 * @author amimehta
 */

public interface LiabilityDAO {

	/**
	 * To save or update liability details
	 * 
	 * @param domain
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOrUpdateLiabilityDtls(LiabilityDO domain)
			throws CGBusinessException, CGSystemException;

	List<CollectionDtlsDO> getLiabilityDetails(Integer regionId, Integer custId)
			throws CGBusinessException, CGSystemException;

	LiabilityDO searchLiabilityDetails(String txnNo)
			throws CGBusinessException, CGSystemException;
	
	public LiabilityDO saveOrUpdateLiabilityDtlsForNext(LiabilityDO domain)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the liability details from collection.
	 *
	 * @param regionId the region id
	 * @param custId the cust id
	 * @return the liability details from collection
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	List<LiabilityCollectionWrapperDO> getLiabilityDetailsFromCollection(
			Integer regionId, Integer custId) throws CGBusinessException,
			CGSystemException;

	boolean saveLiability(LiabilityDO domain) throws CGBusinessException,
			CGSystemException;

	Double getTotalPaidLiabilityByConsg(Integer consgId)
			throws CGBusinessException, CGSystemException;

	void processPaidLiabilityConsingments(List<Integer> consgList)
			throws CGBusinessException, CGSystemException;
}
