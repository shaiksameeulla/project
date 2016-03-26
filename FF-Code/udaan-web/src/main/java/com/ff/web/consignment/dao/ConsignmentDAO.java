package com.ff.web.consignment.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;

public interface ConsignmentDAO {
	/*
	 * public boolean saveOrUpdateConsignment(ConsignmentDO consg) throws
	 * CGSystemException;
	 */

	public List<Integer> saveOrUpdateConsignment(List<ConsignmentDO> consignments)
			throws CGSystemException;

	public List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignments) throws CGSystemException;

	/*
	 * public boolean saveOrUpdateCNPricingDtls( List<CNPricingDetailsDO>
	 * cnPrincingDtls) throws CGSystemException;
	 */

	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException;

	public List<Object> getConsigneeConsignorIds(String consgNumber)
			throws CGSystemException;

	/*
	 * public RateComponentDO getRateComponentByCode(String rateCompCode) throws
	 * CGSystemException;
	 */
	public boolean updateConsignmentForOutDoxMF(
			List<ConsignmentDOXDO> consignments) throws CGSystemException;
	public boolean createConsignment(ConsignmentDO consignment)
			throws CGSystemException;
}
