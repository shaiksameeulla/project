package com.ff.web.pickup.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.pickup.PickupOrderDetailsTO;

public interface PickupGatewayDAO  {
	public ReversePickupOrderDetailDO getReversePickupOrderDetail(PickupOrderDetailsTO detailTO)
			throws CGSystemException;

	ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(
			Integer pickupDlvLocId) throws CGSystemException;

	public String getShippedToCodeByLocationId(
			Integer pickupDlvLocId) throws CGSystemException;

	public String getLatestShipToCodeByCustomer(Integer officeId, Integer customerId) throws CGSystemException;
}
