/**
 * 
 */
package com.ff.rate.configuration.ratebenchmark.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;

/**
 * @author rmaladi
 *
 */
public interface RateBenchMarkService {


	/**
	 * Save the RateBenchMark details
	 * 
	 * @Input param RateBenchMarkHeaderDO
	 * @return SUCCESS OR FAILURE
	 * @throws CGSystemException
	 */
	
	String saveOrUpdateRateBenchMark(RateBenchMarkHeaderTO rbmhTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Get the RatebenchMark Details
	 * @param RateBenchMarkHeaderTO
	 * @return
	 * @throws CGSystemException
	 */
	
	void getRateBenchMarkDetails(RateBenchMarkHeaderTO rbmhTO) throws CGSystemException, CGBusinessException;
	
	/**
	 * Update the Approver details for the RateBenchMark
	 * @param empId
	 * @param headerId
	 * @return True or false
	 * @throws CGSystemException
	 */
	
	String updateApproverDetails(Integer empId, Integer headerId) throws CGSystemException, CGBusinessException;
	
	RateBenchMarkHeaderDO getRateBenchMarkHeaderDetails(RateBenchMarkHeaderTO rbmhTO)
			throws CGSystemException, CGBusinessException;
	
	List<RateBenchMarkHeaderDO> checkEmpIdCorpApprover(Integer empId) throws CGBusinessException, CGSystemException; 
}
