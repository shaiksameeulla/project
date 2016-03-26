package com.ff.web.consignment.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.organization.OfficeTO;

public interface ConsignmentService {

	/*public String saveOrUpdateConsignment(ConsignmentTO consg)
			throws CGBusinessException, CGSystemException;*/

	/*public String saveOrUpdateConsgPrincingDtls(
			List<CNPricingDetailsTO> cnPrincingDtls)
			throws CGBusinessException, CGSystemException;*/

	public List<Integer> saveOrUpdateConsignment(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException;

	public Integer getConsgOperatingLevel(ConsignmentTO ConsingmentTO,
			OfficeTO loggedOffice) throws CGBusinessException,
			CGSystemException;

	/*public Integer getRateComponentIdByCode(String rateCompCode)
			throws CGBusinessException, CGSystemException;*/
	
	public List<Integer> createConsingment(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException;

	String updateConsignmentForOutDoxMF(List<ConsignmentTO> consingments)
			throws CGBusinessException, CGSystemException;
	
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException;

	public List<ConsignmentDO> updateConsignments(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param consingments
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<Integer> updateConsignmentForViewEdit(List<ConsignmentTO> consingments)
			throws CGBusinessException, CGSystemException;

}
