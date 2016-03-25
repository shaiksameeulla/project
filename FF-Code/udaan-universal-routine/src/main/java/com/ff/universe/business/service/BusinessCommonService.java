package com.ff.universe.business.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;

public interface BusinessCommonService {
	CustomerTO getCustomer(Integer customerId) throws CGBusinessException,
			CGSystemException;

	List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException;

	List<CustomerTO> getAllBusinessAssociatesUnderRegion(
			CustomerTO baTO) throws CGSystemException;

	List<CustomerTO> getAllCustomersUnderRegion(CustomerTO customerTo)
			throws CGBusinessException,CGSystemException;

	public Integer isValiedBACode(CustomerTO businessAssociateTO)
			throws CGBusinessException, CGSystemException;

	public List<LoadMovementVendorTO> getPartyNames(String partyType,Integer officeId)
			throws CGSystemException;
	
	/**
	 * 
	 * To Populate the details if party name is CC
	 * 
	 * @return ccTOList
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<LoadMovementVendorTO> getPartyNamesForCC()
			throws CGSystemException;

	public CustomerTO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException, CGBusinessException;

	public CustomerTO getBusinessAssociateByIdOrCode(Integer baId,
			String baCode) throws CGSystemException, CGBusinessException;

	List<LoadMovementVendorTO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO)
			throws CGBusinessException, CGSystemException;

	public List<CustomerTO> getDtlsForTPBA(int baID)
			throws CGBusinessException, CGSystemException;

	public List<CustomerTO> getDtlsForTPFR(int frID)
			throws CGBusinessException, CGSystemException;

	public List<LoadMovementVendorTO> getDtlsForTPCC(int ccID)
			throws CGBusinessException, CGSystemException;

	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber)
			throws CGSystemException;
	
	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber,String partyType)
			throws CGSystemException;

	List<CustomerTO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException;

	List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId) throws CGBusinessException, CGSystemException;
	
	public List<LoadMovementVendorTO> getVendorDtlsForDrsByLoggdCity(String partyType,Integer loggdCityId) throws CGSystemException;
	
	public List<CustomerTO> getCustomerForContractByShippedToCode(
			String shippedToCode) throws CGBusinessException, CGSystemException;
}
