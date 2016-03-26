/**
 * 
 */
package com.ff.rate.configuration.ratebenchmark.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixDO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;



/**
 * @author rmaladi
 *
 */
public interface RateBenchMarkDAO {

	
	/**
	 * Save the RateBenchMark details
	 * 
	 * @Input param RateBenchMarkHeaderDO
	 * @return True or False
	 * @throws CGSystemException
	 */
	
	boolean saveOrUpdateRateBenchMark(RateBenchMarkHeaderDO rbmhDO)throws CGSystemException;
	
	/**
	 * Update the effective date of the RateBenchMark after Renew BenchMark approved
	 * @param headerId
	 * @param date
	 * @throws CGSystemException
	 */
	
	void updateRateBenchMarkHeaderEffectiveToDate(Integer headerId,Date date) throws CGSystemException ;
	
	/**
	 * Get the RatebenchMark Details
	 * @param RateBenchMarkHeaderTO
	 * @return
	 * @throws CGSystemException
	 */
	
	List<RateBenchMarkMatrixDO> getRateBenchMarkMatrix(RateBenchMarkHeaderTO rbmhTO) throws CGSystemException;
	
	/**
	 * Update the Approver details for the RateBenchMark
	 * @param empId
	 * @param headerId
	 * @return True or false
	 * @throws CGSystemException
	 */
	
	boolean updateApproverDetails(Integer empId, Integer headerId) throws CGSystemException;

	RateBenchMarkHeaderDO getRateBenchMarkHeader(
			RateBenchMarkHeaderTO rbmhTO) throws CGSystemException;

	List<RateBenchMarkHeaderDO> checkEmpIdCorpApprover(Integer empId) throws CGSystemException;
	
	RateBenchMarkHeaderDO getRateBenchMarkByHeaderId(Integer headerId)  throws CGSystemException;
}
