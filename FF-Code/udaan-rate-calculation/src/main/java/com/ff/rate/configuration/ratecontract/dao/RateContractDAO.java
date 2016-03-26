package com.ff.rate.configuration.ratecontract.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerWrapperDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateContractSpocDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.to.ratemanagement.masters.RateContractTO;

public interface RateContractDAO {

	/**
	 * To Save Rate Contract Billing Details
	 * 
	 * @param rateContractDO
	 * @return rateContractDO
	 * @throws CGSystemException
	 */
	RateContractDO saveRateContractBillingDtls(RateContractDO rateContractDO)
			throws CGSystemException;

	/**
	 * To Search Rate Contract Billing Details
	 * 
	 * @param rateContractTO
	 * @return rateContractDO
	 * @throws CGSystemException
	 */
	RateContractDO searchRateContractBillingDtls(RateContractTO rateContractTO)
			throws CGSystemException;

	/**
	 * @param contractTO
	 * @return
	 * @throws CGSystemException
	 */
	List<RateContractDO> searchContractDetails(RateContractTO contractTO)
			throws CGSystemException;

	/**
	 * To Save Rate Contract Pickup/Delivery Details 
	 * 
	 * @param domainList
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveRateContractPickupDlvDtls(List<ContractPaymentBillingLocationDO> domainList)
			throws CGSystemException;
	
	
	
	/**
	 * @param custName
	 * @return
	 * @throws CGSystemException
	 */
	List<CustomerWrapperDO> searchCustomerInfo(String custName) throws CGSystemException;
	
	/**
	 * @param userId
	 * @param region
	 * @param city
	 * @param fromDate
	 * @param toDate
	 * @param contractNo
	 * @param status
	 * @param type
	 * @param officeType 
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> rateContractListViewDetails(Integer userId,
			Integer[] region, Integer[] city, String fromDate, String toDate,
			String contractNo, String status, String type, String officeType)
			throws CGSystemException;
	/**
	 * To get Customer New By Id
	 * 
	 * @param customerId
	 * @return customerNewDO
	 * @throws CGSystemException
	 */
	CustomerDO getCustById(Integer customerId)
			throws CGSystemException;
	
	/**
	 * @param contractTO
	 * @return
	 * @throws CGSystemException
	 */
	Boolean submitContract(RateContractTO contractTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * To Search Rate Contract Pickup/Delivery Details
	 * 
	 * @param rateContractTO
	 * @return domainList
	 * @throws CGSystemException
	 */
	List<ContractPaymentBillingLocationDO> searchRateContractPickupDlvDtls(RateContractTO rateContractTO)
				throws CGSystemException;

	/**
	 * @param contractId
	 * @param quotationId
	 * @param contractNo
	 * @param userId
	 * @return
	 * @throws CGSystemException
	 */
	RateContractDO renewContract(Integer contractId, Integer quotationId, String contractNo, Integer userId, RateContractDO rateContractDO) throws CGSystemException;

	/**
	 * @param rateContractId
	 * @return
	 * @throws CGSystemException
	 */
	boolean checkContractIsReNew(Integer rateContractId) throws CGSystemException;
	
	/**
	 * @param noOfDay
	 * @param expDays
	 * @param todayDate
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> getContractListForEmailForArea(Date noOfDay,
			String expDays, Date todayDate) throws CGSystemException;


	/**
	 * @param noOfDay
	 * @param expDays
	 * @param todayDate
	 * @return
	 * @throws CGSystemException
	 */
	List<RateQuotationWrapperDO> getContractListForEmailForRHO(Date noOfDay,
			String expDays, Date todayDate) throws CGSystemException;

	List<RateQuotationWrapperDO> rateContractListViewSEDetails(Integer userId,
			String fromDate, String toDate, String contractNo, String status)
					 throws CGSystemException;

	List<RateContractSpocDO> getRateContractSpocDetails(String contactType,
			Integer contractId) throws CGSystemException;

	Boolean saveOrUpdateRateContractSpocDetails(List<RateContractSpocDO> rcsDOList)
			 throws CGSystemException;

	void updateCustomerCode(String soldToCode, Integer integer) 
			throws CGSystemException;
	
	/**
	 * To update billing details for customer
	 * @param rateContractTO
	 * @param customerId
	 * @return {boolean}
	 * @throws CGSystemException
	 */
	boolean updateCustomerBillDtls(RateContractTO rateContractTO, Integer custId, Integer custTypeId) 
			throws CGSystemException;

	RateContractDO saveOrUpdateContract(RateContractDO rateContractDO) throws CGSystemException;

	RateContractDO searchContractDetailsForSuperUser(
			RateContractTO contractTO) throws CGSystemException;

	List<EmployeeDO> getEmployeesforContractAlert(OfficeDO ofcDO) throws CGSystemException;
	
	public void clearPickupOrDeliveryLocations(RateContractDO rateContractDo) throws CGSystemException;
}
