/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.business.CustomerTO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;

/**
 * @author kgajare
 * 
 */
public interface PickupGatewayService {
	public List<EmployeeTO> getBranchEmployees(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public List<CustomerTO> getMasterCustomerList(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public List<CustomerTO> getReverseCustomerList(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public List<LabelValueBean> getBranchesUnderHUB(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException;

	public List<String> generateRunsheetNumber(Integer noOfRunsheets)
			throws CGBusinessException, CGSystemException;

	public BookingValidationTO validateConsignment(
			BookingValidationTO cnValidationTO) throws CGBusinessException,
			CGSystemException;

	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException;

	public List<OfficeTO> getOfficeListByOfficeTO(OfficeTO officeTo)
			throws CGBusinessException, CGSystemException;

	public PickupOrderDetailsTO getReversePickupOrderDetail(
			PickupOrderDetailsTO detailTO) throws CGBusinessException,
			CGSystemException;

	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException;

	public ContractPaymentBillingLocationDO getContractPayBillingLocationDtlsBypickupLocation(
			Integer pickupDlvLocId) throws CGBusinessException,
			CGSystemException;

	public CityTO getCity(Integer cityId) throws CGSystemException,
			CGBusinessException;

	ProductTO getProductByConsgSeries(String consgSeries)
			throws CGBusinessException, CGSystemException;
	public void twoWayWrite(PickupTwoWayWriteTO pickupTwoWayWriteTO);

	public String getShippedToCodeByLocationId(Integer pickupDlvLocId)
			throws CGBusinessException, CGSystemException;
	
	public String getLatestShipToCodeByCustomer(Integer OfficeId, Integer customerId)
			throws CGBusinessException, CGSystemException;
}
