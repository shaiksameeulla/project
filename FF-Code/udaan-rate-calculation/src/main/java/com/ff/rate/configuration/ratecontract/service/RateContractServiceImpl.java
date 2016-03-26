package com.ff.rate.configuration.ratecontract.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.business.CustomerWrapperDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.AddressDO;
import com.ff.domain.pickup.PickupDeliveryContractDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateContractSpocDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.pickup.PickupDeliveryContractTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratecontract.dao.RateContractDAO;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.rate.configuration.ratequotation.service.RateQuotationService;
import com.ff.to.ratemanagement.masters.ContractPaymentBillingLocationTO;
import com.ff.to.ratemanagement.masters.RateContractSpocTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.operations.ratecontract.RateCustomerSearchTO;
import com.ff.to.ratemanagement.operations.ratequotation.ContactTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
//import com.ff.universe.util.EmailSenderService;

/**
 * @author hkansagr
 */

public class RateContractServiceImpl implements RateContractService {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RateContractServiceImpl.class);


	/** The Rate Contract DAO. */
	private RateContractDAO rateContractDAO;

	/** The Rate Common Service. */
	private RateCommonService rateCommonService;
	//private EmailSenderService emailSenderService;
	private EmailSenderUtil emailSenderUtil;
	private RateQuotationService rateQuotationService;


	/**
	 * @return the rateQuotationService
	 */
	public RateQuotationService getRateQuotationService() {
		return rateQuotationService;
	}
	/**
	 * @param rateQuotationService the rateQuotationService to set
	 */
	public void setRateQuotationService(RateQuotationService rateQuotationService) {
		this.rateQuotationService = rateQuotationService;
	}
	/**
	 * @return the emailSenderUtil
	 */
	public EmailSenderUtil getEmailSenderUtil() {
		return emailSenderUtil;
	}
	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}
	/**
	 * @return the emailSenderService
	 */
	/*public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}
	 *//**
	 * @param emailSenderService the emailSenderService to set
	 *//*
	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}*/
	/**
	 * @param rateCommonService
	 *            the rateCommonService to set
	 */
	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}
	/**
	 * @param rateContractDAO
	 *            the rateContractDAO to set
	 */
	public void setRateContractDAO(RateContractDAO rateContractDAO) {
		this.rateContractDAO = rateContractDAO;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer loggedInRHO)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getOfficeDetails(loggedInRHO);
	}

	@Override
	public RateContractTO saveRateContractBillingDtls(RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RateContractServiceImpl::saveRateContractBillingDtls()::START");
		RateContractDO domain = null;
		domain = rateContractDAO.searchRateContractBillingDtls(rateContractTO);
		createRateContractDOFromTO(rateContractTO, domain);
		if (!StringUtil.isNull(domain)) {
			//domain = rateContractDAO.saveRateContractBillingDtls(domain);
			/*if (!StringUtil.isEmptyInteger(domain.getRateContractId())) {
				domain = rateContractDAO.searchRateContractBillingDtls(rateContractTO);
				rateContractTO.setRateContractId(domain.getRateContractId());

				 To update PAN & TAN No. 
				//updatePanAndTanNo(rateContractTO);
				Integer customerTypeId = getCustomerType(domain);

				Integer custId = domain.getRateQuotationDO()
						.getCustomer().getCustomerId();
				//rateContractDAO.updateCustomerCode(rateContractTO.getSoldToCode(), custId);

				 To update billing cycle & payment term for customer 
				rateContractDAO.updateCustomerBillDtls(rateContractTO,custId,customerTypeId);
			}*/
			domain = rateContractDAO.saveOrUpdateContract(domain);
			rateContractTO.setSaved(Boolean.TRUE);

			/*If the type of billing has been changed and the value of 'isExistPkUpOrDlvDetails' flag = 'Y', 
			then clear the previously entered pickup/delivery locations*/
			if(!StringUtil.isStringEmpty(rateContractTO.getIsDeletePickupOrDlvLocations()) &&
					CommonConstants.YES.equalsIgnoreCase(rateContractTO.getIsDeletePickupOrDlvLocations())){
				rateContractDAO.clearPickupOrDeliveryLocations(domain);
			}
		} else {			
			throw new CGBusinessException();
		}
		LOGGER.debug("RateContractServiceImpl::saveRateContractBillingDtls()::END");
		return rateContractTO;
	}

	/**
	 * To create RateContractDO from RateContractTO
	 * 
	 * @param to
	 * @return domain
	 */
	private void createRateContractDOFromTO(RateContractTO to, RateContractDO domain) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateContractServiceImpl::createRateContractDOFromTO()::START");
		//RateContractDO domain = new RateContractDO();
		/* Rate Contract Id */
		if (!StringUtil.isEmptyInteger(to.getRateContractId())) {
			domain.setRateContractId(to.getRateContractId());
		}
		/* Customer Id */
		if (!StringUtil.isNull(to.getRateQuotationTO().getCustomer())
				&& !StringUtil.isEmptyInteger(to.getRateQuotationTO().getCustomer().getCustomerId())) {
			domain.setCustomerId(to.getRateQuotationTO().getCustomer().getCustomerId());
		}
		/* Rate Quotation Id */
		if (!StringUtil.isNull(to.getRateQuotationTO())
				&& StringUtil.isEmptyInteger(to.getRateQuotationTO().getRateQuotationId())) {
			RateQuotationDO quotationDO = new RateQuotationDO();
			quotationDO.setRateQuotationId(to.getRateQuotationTO().getRateQuotationId());
			domain.setRateQuotationDO(quotationDO);
		}
		/* Effective From Date */
		if (!StringUtil.isNull(to.getValidFromDate())) {
			domain.setValidFromDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(to.getValidFromDate()));
		}
		/* Valid To Date */
		if (!StringUtil.isNull(to.getValidToDate())) {
			domain.setValidToDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(to.getValidToDate()));
		}
		/* Billing Contract Type */
		if (!StringUtil.isStringEmpty(to.getBillingContractType())) {
			domain.setBillingContractType(to.getBillingContractType());
		}
		/* Type Of Billing */
		if (!StringUtil.isStringEmpty(to.getTypeOfBilling())) {
			domain.setTypeOfBilling(to.getTypeOfBilling());
			/*if(!to.getTypeOfBilling().equals(RateContractConstants.BILL_TYPE_DBCP)){
				Set<ContractPaymentBillingLocationDO> cpblDOSet = domain.getConPayBillLocDO();
				if(!CGCollectionUtils.isEmpty(cpblDOSet)){
					Set<ContractPaymentBillingLocationDO> cpbillDOSet = new HashSet<ContractPaymentBillingLocationDO>(cpblDOSet.size());
					for(ContractPaymentBillingLocationDO cpblDO : cpblDOSet){
						cpblDO.setShippedToCode(null);
						cpbillDOSet.add(cpblDO);
					}
					domain.setConPayBillLocDO(cpbillDOSet);
				}
			}*/
		}
		/* Mode Of Billing */
		if (!StringUtil.isStringEmpty(to.getModeOfBilling())) {
			domain.setModeOfBilling(to.getModeOfBilling());
		}
		/* Billing Cycle */
		if (!StringUtil.isStringEmpty(to.getBillingCycle())) {
			domain.setBillingCycle(to.getBillingCycle());
		}
		/* Payment Term */
		if (!StringUtil.isStringEmpty(to.getPaymentTerm())) {
			domain.setPaymentTerm(to.getPaymentTerm());
		}
		/* Created Date */
		if (!StringUtil.isStringEmpty(to.getCreatedDate())) {
			domain.setCreatedDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(to.getCreatedDate()));
		}

		domain.setUpdatedDate(Calendar.getInstance().getTime());
		/* Created By User */
		if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
			domain.setCreatedBy(to.getCreatedBy());
		}
		/* Updated By User */
		if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
			domain.setUpdatedBy(to.getUpdatedBy());
		}
		/* Rate Contract Number */
		/*if (!StringUtil.isStringEmpty(to.getRateContractNo())) {
			domain.setRateContractNo(to.getRateContractNo());
		}*/
		/* Rate Contract Type i.e. N- Normal, E- ECommerce */
		if (!StringUtil.isStringEmpty(to.getRateContractType())) {
			domain.setRateContractType(to.getRateContractType());
		}
		/* Contract Status i.e. C- Created, S- Submitted, A- Active, I- Inactive, B- Blocked */
		if (!StringUtil.isStringEmpty(to.getContractStatus())) {
			domain.setContractStatus(to.getContractStatus());
		}
		
		/* The below method  is used to validate fields before saving in ff_d_rate_contract table.
		 * The fields payment_term and billing_cycle are getting stamped as null in customer table.
		 * This method has been written in addition to the one written below */
		// validateRateContractDetailsBeforeSaving(domain);
		
		//return domain;
		updateCustomerDetails(to, domain);
		
		/* The below method is used to validate fields before saving in ff_d_customer table.
		 * The fields payment_term and billing_cycle are getting stamped as null in customer table.
		 * This method has been written to prevent that. */
		// validateCustomerDetailsBeforeSaving(domain);
		LOGGER.trace("RateContractServiceImpl::createRateContractDOFromTO()::END");
	}	

	/*private void validateRateContractDetailsBeforeSaving(RateContractDO rateContractDo) throws CGBusinessException {
		boolean isThrowException = false;
		if (StringUtil.isStringEmpty(rateContractDo.getPaymentTerm())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", paymentTerm = " + rateContractDo.getPaymentTerm() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getBillingCycle())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", billingCycle = " + rateContractDo.getBillingCycle() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getModeOfBilling())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", modeOfBilling = " + rateContractDo.getModeOfBilling() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getTypeOfBilling())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", typeOfBilling = " + rateContractDo.getTypeOfBilling() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getRateContractType())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", rateContractType = " + rateContractDo.getRateContractType() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getContractStatus())) {
			LOGGER.error("RateContractServiceImpl::validateRateContractDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", contractStatus = " + rateContractDo.getContractStatus() + "]");
			isThrowException = true;
		}
		
		if (isThrowException) {
			throw new CGBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
		}
	}*/
	
	private void updateCustomerDetails(RateContractTO rateContractTO, RateContractDO domain) throws CGBusinessException, CGSystemException{

		Integer customerTypeId = getCustomerType(domain);

		/*		Integer custId = domain.getRateQuotationDO()
						.getCustomer().getCustomerId();*/

		domain.getRateQuotationDO().getCustomer().setPanNo(rateContractTO.getPanNo());
		domain.getRateQuotationDO().getCustomer().setTanNo(rateContractTO.getTanNo());
		domain.getRateQuotationDO().getCustomer().setBillingCycle(rateContractTO.getBillingCycle());
		domain.getRateQuotationDO().getCustomer().setPaymentTerm(rateContractTO.getPaymentTerm());
		if(!StringUtil.isStringEmpty(rateContractTO.getSoldToCode())){
			domain.getRateQuotationDO().getCustomer().setCustomerCode(rateContractTO.getSoldToCode());
		}else{
			domain.getRateQuotationDO().getCustomer().setCustomerCode(null);
		}

		if(!StringUtil.isEmptyInteger(customerTypeId)){
			CustomerTypeDO custType = new CustomerTypeDO();
			custType.setCustomerTypeId(customerTypeId);			
			domain.getRateQuotationDO().getCustomer().setCustomerType(custType);
		}

		if(rateContractTO.getTypeOfBilling().equals(RateContractConstants.BILL_TYPE_DBCP)){
			domain.getRateQuotationDO().getCustomer().setSapStatus(CommonConstants.YES);
		}else{
			domain.getRateQuotationDO().getCustomer().setSapStatus(CommonConstants.NO);
		}
	}
	
	/*private void validateCustomerDetailsBeforeSaving(RateContractDO rateContractDo) throws CGBusinessException {
		boolean isThrowException = false;
		
		if (StringUtil.isStringEmpty(rateContractDo.getRateQuotationDO().getCustomer().getBillingCycle())) {
			LOGGER.error("RateContractServiceImpl::validateCustomerDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", billingCycle = " + rateContractDo.getRateQuotationDO().getCustomer().getBillingCycle() + "]");
			isThrowException = true;
		}
		
		if (StringUtil.isStringEmpty(rateContractDo.getRateQuotationDO().getCustomer().getPaymentTerm())) {
			LOGGER.error("RateContractServiceImpl::validateCustomerDetailsBeforeSaving::ERROR:: [" + rateContractDo.getRateContractNo()
					+ ", paymentTerm = " + rateContractDo.getRateQuotationDO().getCustomer().getPaymentTerm() + "]");
			isThrowException = true;
		}

		if (isThrowException) {
			throw new CGBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
		}
	}*/

	/**
	 * To update pan and tan number
	 * 
	 * @param to
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*	private boolean updatePanAndTanNo(RateContractTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::updatePanAndTanNo()::START");
		boolean result=Boolean.FALSE;
		String panNo=CommonConstants.EMPTY_STRING;
		String tanNo=CommonConstants.EMPTY_STRING;
		if (!StringUtil.isStringEmpty(to.getPanNo().trim())) {
			panNo=to.getPanNo().trim();
		}
		if (!StringUtil.isStringEmpty(to.getTanNo().trim())) {
			tanNo=to.getTanNo().trim();
		}
		if(!StringUtil.isNull(to.getRateQuotationTO().getCustomer())
				&& !StringUtil.isEmptyInteger(to.getRateQuotationTO().getCustomer().getCustomerId())){
			Integer custId=to.getRateQuotationTO().getCustomer().getCustomerId();
			if (!StringUtil.isStringEmpty(panNo)){//if panNo is Null then no need to update
				if(!rateCommonService.updateCustPanNo(panNo, custId)){
					throw new CGBusinessException();
				}
			}
			if (!StringUtil.isStringEmpty(tanNo)){//if tanNo is Null then no need to update
				if(!rateCommonService.updateCustTanNo(tanNo, custId)){
					throw new CGBusinessException();
				}
			}
		} else {
			throw new CGBusinessException();
		}
		LOGGER.trace("RateContractServiceImpl::updatePanAndTanNo()::END");
		return result;
	}
	 */
	@Override
	public RateContractTO searchRateContractBillingDtls(RateContractTO to)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::searchRateContractBillingDtls()::START");
		RateContractDO domain = null;
		RateContractTO rateContractTO = null;
		if (!StringUtil.isNull(to)) {
			domain = rateContractDAO.searchRateContractBillingDtls(to);
			if (!StringUtil.isNull(domain)) {

				rateContractTO = createRateContractTOFromDO(domain);
				//To get customer new details i.e. PAN No. & TAN No. 
				/*CustomerDO custNewDO = rateContractDAO.getCustById(domain
						.getCustomerId());*/ 
				if (!StringUtil.isNull(domain)) {
					rateContractTO.setPanNo(domain.getRateQuotationDO().getCustomer().getPanNo());
					rateContractTO.setTanNo(domain.getRateQuotationDO().getCustomer().getTanNo());
					rateContractTO.setSoldToCode(domain.getRateQuotationDO().getCustomer().getCustomerCode());
				}

				if(!CGCollectionUtils.isEmpty(domain.getConPayBillLocDO())){
					for(ContractPaymentBillingLocationDO conPayBillLocDo : domain.getConPayBillLocDO()){
						if(RateCommonConstants.STATUS_ACTIVE.equalsIgnoreCase(conPayBillLocDo.getStatus())){
							rateContractTO.setIsExistPkUpOrDlvDetails(Boolean.TRUE);
							break;
						}
					}
				}else{
					rateContractTO.setIsExistPkUpOrDlvDetails(Boolean.FALSE);
				}

			}
		}
		LOGGER.trace("RateContractServiceImpl::searchRateContractBillingDtls()::END");
		return rateContractTO;
	}

	@Override
	public RateContractDO searchRateContractPickDlvDtls(RateContractTO to)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::searchRateContractBillingDtls()::START");
		RateContractDO domain = null;
		if (!StringUtil.isNull(to)) {
			domain = rateContractDAO.searchRateContractBillingDtls(to);
			if (!StringUtil.isNull(domain)) {

				/*rateContractTO = createRateContractTOFromDO(domain);
				 To get customer new details i.e. PAN No. & TAN No. 
				 CustomerDO custNewDO = rateContractDAO.getCustById(domain
						.getCustomerId()); 
				if (!StringUtil.isNull(domain)) {
					rateContractTO.setPanNo(domain.getRateQuotationDO().getCustomer().getPanNo());
					rateContractTO.setTanNo(domain.getRateQuotationDO().getCustomer().getTanNo());
					rateContractTO.setSoldToCode(domain.getRateQuotationDO().getCustomer().getCustomerCode());
				}

				if(!CGCollectionUtils.isEmpty(domain.getConPayBillLocDO())){
					rateContractTO.setIsExistPkUpOrDlvDetails(Boolean.TRUE);
				}else{
					rateContractTO.setIsExistPkUpOrDlvDetails(Boolean.FALSE);
				}*/
				Set<ContractPaymentBillingLocationDO> locationSet = new HashSet<ContractPaymentBillingLocationDO>();
				if(!CGCollectionUtils.isEmpty(domain.getConPayBillLocDO())){
					for(ContractPaymentBillingLocationDO cpblDO : domain.getConPayBillLocDO()){
						if(cpblDO.getStatus().equals(RateContractConstants.ACTIVE)){
							locationSet.add(cpblDO);
						}
						cpblDO.setRateContractDO(null);
					}
				}
				domain.setConPayBillLocDO(locationSet);
				domain.setRateContractSpocDO(null);
				domain.getRateQuotationDO().setRateFixedChargeDO(null);
				domain.getRateQuotationDO().setRateQuotationProductCategoryHeaderDO(null);
				domain.getRateQuotationDO().setRateQuotationRtoChargesDO(null);
				domain.getRateQuotationDO().setCodChargeDO(null);
				domain.getRateQuotationDO().setFixedChargesConfigDO(null);
				domain.getRateQuotationDO().setRatequotationOriginatedFrom(null);
				domain.setOriginatedRateContractDO(null);

			}
		}
		LOGGER.trace("RateContractServiceImpl::searchRateContractBillingDtls()::END");
		return domain;
	}

	/**
	 * To create RateContractDO from RateContractTO
	 * 
	 * @param domain
	 * @return to
	 */
	private RateContractTO createRateContractTOFromDO(RateContractDO domain) 
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::createRateContractTOFromDO()::START");
		RateContractTO to = new RateContractTO();
		/* Rate Contract Id */
		if (!StringUtil.isEmptyInteger(domain.getRateContractId())) {
			to.setRateContractId(domain.getRateContractId());
		}
		/* Customer Id */
		if (!StringUtil.isEmptyInteger(domain.getRateQuotationDO().getCustomer().getCustomerId())) {
			to.setCustomerId(domain.getRateQuotationDO().getCustomer().getCustomerId());
		}
		/* Rate Quotation Id. */
		if (!StringUtil.isNull(domain.getRateQuotationDO())) {
			to.setRateQuotationId(domain.getRateQuotationDO().getRateQuotationId());
		}
		/* Effective From Date */
		if (!StringUtil.isNull(domain.getValidFromDate())) {
			to.setValidFromDate(DateUtil.getDDMMYYYYDateToString(domain
					.getValidFromDate()));
		}
		/* Valid To Date */
		if (!StringUtil.isNull(domain.getValidToDate())) {
			to.setValidToDate(DateUtil.getDDMMYYYYDateToString(domain
					.getValidToDate()));
		}
		/* Billing Contract Type */
		if (!StringUtil.isStringEmpty(domain.getBillingContractType())) {
			to.setBillingContractType(domain.getBillingContractType());
		}
		/* Type Of Billing */
		if (!StringUtil.isStringEmpty(domain.getTypeOfBilling())) {
			to.setTypeOfBilling(domain.getTypeOfBilling());
		}
		/* Mode Of Billing */
		if (!StringUtil.isStringEmpty(domain.getModeOfBilling())) {
			to.setModeOfBilling(domain.getModeOfBilling());
		}
		/* Billing Cycle */
		if (!StringUtil.isStringEmpty(domain.getBillingCycle())) {
			to.setBillingCycle(domain.getBillingCycle());
		}
		/* Payment Term */
		if (!StringUtil.isStringEmpty(domain.getPaymentTerm())) {
			to.setPaymentTerm(domain.getPaymentTerm());
		}
		/* Created By User */
		if (!StringUtil.isEmptyInteger(domain.getCreatedBy())) {
			to.setCreatedBy(domain.getCreatedBy());
		}
		/* Updated By User */
		if (!StringUtil.isEmptyInteger(domain.getUpdatedBy())) {
			to.setUpdatedBy(domain.getUpdatedBy());
		}
		/* Rate Contract Number */
		if (!StringUtil.isStringEmpty(domain.getRateContractNo())) {
			to.setRateContractNo(domain.getRateContractNo());
		}
		/* Rate Contract Type i.e. N- Normal, E- ECommerce */
		if (!StringUtil.isStringEmpty(domain.getRateContractType())) {
			to.setRateContractType(domain.getRateContractType());
		}
		/* Contract Status i.e. C- Created, S- Submitted, A- Active, I- Inactive, B- Blocked */
		if (!StringUtil.isStringEmpty(domain.getContractStatus())) {
			to.setContractStatus(domain.getContractStatus());
		}

		/* Setting this field as an identifier of a renewed contract */
		if (!StringUtil.isNull(domain.getOriginatedRateContractDO())) {
			to.setOriginatedRateContractId(domain.getOriginatedRateContractDO().getRateContractId());
		}
		
		/* To get Pickup/Delivery Details (SET --> List) */
		/*List<ContractPaymentBillingLocationTO> conPayBillLocTOList = null;
		if(!StringUtil.isEmptyColletion(domain.getConPayBillLocDO())){
			List<ContractPaymentBillingLocationDO> conPayBillLocDOList = 
					new ArrayList<ContractPaymentBillingLocationDO>(domain.getConPayBillLocDO().size());
			for(ContractPaymentBillingLocationDO conPayBillLocDO : domain.getConPayBillLocDO()){
					conPayBillLocDOList.add(conPayBillLocDO);				
			}
			conPayBillLocTOList = conPayBillLocDomainConverter(conPayBillLocDOList);
			if(!StringUtil.isEmptyColletion(conPayBillLocTOList)){
				to.setContractLocationList(conPayBillLocTOList);
			}
		}*/
		LOGGER.trace("RateContractServiceImpl::createRateContractTOFromDO()::END");
		return to;
	}

	@Override
	public RateContractTO searchContractDetails(RateContractTO contractTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::searchContractDetails()::START");
		List<RateContractDO> contractDOs = null;
		Boolean contractIsReNew = false;
		RateContractDO rcDO = null;

		RateContractTO rateContractTO = null;
		if (!StringUtil.isNull(contractTO.getUserType())
				&& contractTO.getUserType().equalsIgnoreCase(RateQuotationConstants.SUPER_USER)) {
			rcDO = rateContractDAO.searchContractDetailsForSuperUser(contractTO);
		} 
		else {
			contractDOs = rateContractDAO.searchContractDetails(contractTO);
		}

		if (!CGCollectionUtils.isEmpty(contractDOs)) {
			rcDO = contractDOs.get(0);
		}

		if (!StringUtil.isNull(rcDO)) {
			rateContractTO = contractDetailsDomainConverter(rcDO);
			setRatesFlags(rateContractTO, rcDO);
			if (!StringUtil.isStringEmpty(rcDO.getBillingContractType())) {
				rateContractTO.setBillingSaved("Y");
				rateContractTO.setBillingContractType(rcDO.getBillingContractType());
			}
			if (!StringUtil.isNull(rcDO.getConPayBillLocDO())
					&& !CGCollectionUtils.isEmpty(rcDO.getConPayBillLocDO())) {
				if (!StringUtil.isNull(rcDO.getBillingContractType())) {
					if (rcDO.getBillingContractType().trim().equals("N")){
						rateContractTO.setPkpupSaved("Y");
					}else{
						rateContractTO.setDlvSaved("Y");
					}
				}
			}
			if (!StringUtil.isNull(rcDO.getRateContractSpocDO())
					&& !CGCollectionUtils.isEmpty(rcDO.getRateContractSpocDO())) {
				Set<RateContractSpocDO> spocDOSet = rcDO.getRateContractSpocDO(); 
				for(RateContractSpocDO spocDO : spocDOSet){
					if(spocDO.getContactType().equals("F")){
						rateContractTO.setFfContactsSaved("Y");
						break;
					}
				}
			}else{
				if(!rcDO.getContractStatus().equals(RateContractConstants.CREATED)){
					rateContractTO.setFfContactsSaved("Y");
				}
			}

			// Logic used to check whether a given contract has been renewed or not
			if (!StringUtil.isEmptyInteger(rateContractTO.getRateContractId())) {
				contractIsReNew = rateContractDAO.checkContractIsReNew(rateContractTO.getRateContractId());
				if (contractIsReNew) {
					rateContractTO.setRenewContract(Boolean.TRUE);
				} else {
					rateContractTO.setRenewContract(Boolean.FALSE);
				}
				if (!StringUtil.isEmptyInteger(rateContractTO.getOriginatedRateContractId())
						&& rateContractTO.getContractStatus().equals(RateContractConstants.CREATED)) {
					rateContractTO.setIsNew(Boolean.TRUE);
				} else {
					rateContractTO.setIsNew(Boolean.FALSE);
				}
			}
		} else {
			ExceptionUtil
			.prepareBusinessException(RateErrorConstants.SEARCH_RESULT_NOT_FOUND);
		}

		LOGGER.trace("RateContractServiceImpl::searchContractDetails()::END");
		return rateContractTO;
	}

	private RateContractTO contractDetailsDomainConverter(
			RateContractDO rateContractDO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::contractDetailsDomainConverter()::START");
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		CustomerTO customerNew = new CustomerTO();
		RateQuotationDO rateQuotationDO = new RateQuotationDO();
		RateContractTO rateContractTO = new RateContractTO();
		rateQuotationDO = rateContractDO.getRateQuotationDO();

		rateContractTO.setRateContractId(rateContractDO.getRateContractId());
		rateContractTO.setRateContractNo(rateContractDO.getRateContractNo());
		rateContractTO.setCreatedBy(rateContractDO.getCreatedBy());
		rateContractTO.setCreatedDate(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(rateContractDO
						.getCreatedDate()));

		/* Effective From Date */
		if (!StringUtil.isNull(rateContractDO.getValidFromDate())) {
			rateContractTO.setValidFromDate(DateUtil.getDDMMYYYYDateToString(rateContractDO
					.getValidFromDate()));
		}
		/* Valid To Date */
		if (!StringUtil.isNull(rateContractDO.getValidToDate())) {
			rateContractTO.setValidToDate(DateUtil.getDDMMYYYYDateToString(rateContractDO
					.getValidToDate()));
		}

		if(!StringUtil.isStringEmpty(rateContractDO.getTypeOfBilling())){
			rateContractTO.setTypeOfBilling(rateContractDO.getTypeOfBilling());
		}

		rateContractTO.setContractStatus(rateContractDO.getContractStatus());

		rateQuotationTO
		.setRateQuotationId(rateQuotationDO.getRateQuotationId());

		rateQuotationTO
		.setRateQuotationNo(rateQuotationDO.getRateQuotationNo());

		if (!StringUtil.isNull(rateQuotationDO.getStatus())) {
			rateQuotationTO.setStatus(rateQuotationDO.getStatus());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getSalesOfficeDO())) {
			OfficeTO office = new OfficeTO();
			office.setOfficeId(rateQuotationDO.getCustomer().getSalesOfficeDO()
					.getOfficeId());
			office.setCityId(rateQuotationDO.getCustomer().getSalesOfficeDO()
					.getCityId());
			CityTO cityTO = new CityTO();
			cityTO.setCityId(rateQuotationDO.getCustomer().getSalesOfficeDO()
					.getCityId());
			cityTO = getCity(cityTO);
			office.setCityName(cityTO.getCityName());			
			office.setOfficeName(rateQuotationDO.getCustomer().getSalesOfficeDO()
					.getOfficeName());
			customerNew.setSalesOffice(office);
			OfficeTypeTO offcTypeTO = new OfficeTypeTO();
			offcTypeTO = (OfficeTypeTO) CGObjectConverter.createToFromDomain(rateQuotationDO.getCustomer().getSalesOfficeDO().getOfficeTypeDO(), offcTypeTO);
			office.setOfficeTypeTO(offcTypeTO);
			if(offcTypeTO.getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				rateQuotationTO.setRhoOfcId(rateQuotationDO.getCustomer().getSalesOfficeDO().getOfficeId());
			}else{
				rateQuotationTO.setRhoOfcId(rateQuotationDO.getCustomer().getSalesOfficeDO().getReportingRHO());
			}
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getSalesPersonDO())) {
			EmployeeTO employeeTO = new EmployeeTO();
			employeeTO.setEmployeeId(rateQuotationDO.getCustomer()
					.getSalesPersonDO().getEmployeeId());
			employeeTO.setFirstName(rateQuotationDO.getCustomer()
					.getSalesPersonDO().getFirstName());
			employeeTO.setLastName(rateQuotationDO.getCustomer()
					.getSalesPersonDO().getLastName());

			customerNew.setSalesPerson(employeeTO);
		}

		if (rateQuotationDO.getRateQuotationType().equals(
				RateQuotationConstants.NORMAL)) {

			if (!StringUtil.isNull(rateQuotationDO.getCustomer()
					.getIndustryCategoryDO())) {

				customerNew.setIndustryCategory(rateQuotationDO.getCustomer()
						.getIndustryCategoryDO().getRateIndustryCategoryId()
						+ CommonConstants.TILD
						+ rateQuotationDO.getCustomer().getCustomerCategoryDO()
						.getRateCustomerCategoryId());
			}
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getIndustryTypeDO())) {

			customerNew.setIndustryType(rateQuotationDO.getCustomer()
					.getIndustryTypeDO().getRateIndustryTypeId()
					+ CommonConstants.TILD
					+ rateQuotationDO.getCustomer().getIndustryTypeDO()
					.getRateIndustryTypeCode());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getAddressDO())) {
			PickupDeliveryAddressTO addressTO = new PickupDeliveryAddressTO();
			addressTO.setAddress1(rateQuotationDO.getCustomer().getAddressDO()
					.getAddress1());
			addressTO.setAddress2(rateQuotationDO.getCustomer().getAddressDO()
					.getAddress2());
			addressTO.setAddress3(rateQuotationDO.getCustomer().getAddressDO()
					.getAddress3());
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincodeId(rateQuotationDO.getCustomer().getAddressDO()
					.getPincodeDO().getPincodeId());
			pincodeTO.setPincode(rateQuotationDO.getCustomer().getAddressDO()
					.getPincodeDO().getPincode());
			CityTO cityTO = new CityTO();
			cityTO.setCityId(rateQuotationDO.getCustomer().getAddressDO()
					.getCityDO().getCityId());
			cityTO.setCityName(rateQuotationDO.getCustomer().getAddressDO()
					.getCityDO().getCityName());
			addressTO.setPincode(pincodeTO);
			addressTO.setCity(cityTO);
			customerNew.setAddress(addressTO);
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer()
				.getPrimaryContactDO())) {
			ContactTO contact = new ContactTO();
			contact.setTitle(rateQuotationDO.getCustomer().getPrimaryContactDO()
					.getTitle());
			contact.setDesignation(rateQuotationDO.getCustomer()
					.getPrimaryContactDO().getDesignation());

			contact.setDepartment(rateQuotationDO.getCustomer()
					.getPrimaryContactDO().getDepartment());
			contact.setEmail(rateQuotationDO.getCustomer().getPrimaryContactDO()
					.getEmail());
			contact.setMobile(rateQuotationDO.getCustomer().getPrimaryContactDO()
					.getMobile());
			contact.setExtension(rateQuotationDO.getCustomer()
					.getPrimaryContactDO().getExtension());
			contact.setContactId(rateQuotationDO.getCustomer()
					.getPrimaryContactDO().getContactId());
			contact.setFax(rateQuotationDO.getCustomer().getPrimaryContactDO()
					.getFax());
			contact.setName(rateQuotationDO.getCustomer().getPrimaryContactDO()
					.getName());
			contact.setContactNo(rateQuotationDO.getCustomer()
					.getPrimaryContactDO().getContactNo());
			customerNew.setPrimaryContact(contact);

		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer()
				.getSecondaryContactDO())) {
			ContactTO contact = new ContactTO();
			contact.setTitle(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getTitle());
			contact.setDesignation(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getDesignation());
			contact.setDepartment(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getDepartment());
			contact.setEmail(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getEmail());
			contact.setMobile(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getMobile());
			contact.setExtension(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getExtension());
			contact.setContactId(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getContactId());
			contact.setFax(rateQuotationDO.getCustomer().getSecondaryContactDO()
					.getFax());
			contact.setName(rateQuotationDO.getCustomer().getSecondaryContactDO()
					.getName());
			contact.setContactNo(rateQuotationDO.getCustomer()
					.getSecondaryContactDO().getContactNo());
			customerNew.setSecondaryContact(contact);

		}
		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getGroupKeyDO())) {
			CustomerGroupTO customerGroup = new CustomerGroupTO();
			customerGroup.setCustomerGroupId(rateQuotationDO.getCustomer()
					.getGroupKeyDO().getCustomerGroupId());
			customerNew.setGroupKey(customerGroup);
		}
		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getBusinessName())) {
			customerNew.setBusinessName(rateQuotationDO.getCustomer()
					.getBusinessName());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getCustomerId())) {
			customerNew.setCustomerId(rateQuotationDO.getCustomer()
					.getCustomerId());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getBusinessType())) {
			customerNew.setBusinessType(rateQuotationDO.getCustomer()
					.getBusinessType());
		}
		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getCustomerCode())) {
			customerNew.setCustomerCode(rateQuotationDO.getCustomer()
					.getCustomerCode());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getLegacyCustomerCode())) {
			customerNew.setLegacyCustomerCode(rateQuotationDO.getCustomer()
					.getLegacyCustomerCode());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getTanNo())) {
			customerNew.setTanNo(rateQuotationDO.getCustomer()
					.getTanNo());
		}

		if (!StringUtil.isNull(rateQuotationDO.getCustomer().getPanNo())) {
			customerNew.setPanNo(rateQuotationDO.getCustomer()
					.getPanNo());
		}

		if (!StringUtil.isNull(rateQuotationDO.getApproversRemarks())) {
			rateQuotationTO.setApproversRemarks(rateQuotationDO
					.getApproversRemarks());
		}

		if (!StringUtil.isNull(rateQuotationDO.getExcecutiveRemarks())) {
			rateQuotationTO.setExcecutiveRemarks(rateQuotationDO
					.getExcecutiveRemarks());
		}

		if (!StringUtil.isNull(rateQuotationDO.getRateQuotationType())) {
			rateQuotationTO.setRateQuotationType(rateQuotationDO
					.getRateQuotationType());
		}

		rateQuotationTO.setCustomer(customerNew);
		rateContractTO.setRateQuotationTO(rateQuotationTO);

		if(!StringUtil.isNull(rateContractDO.getOriginatedRateContractDO())){
			rateContractTO.setOriginatedRateContractId(rateContractDO.getOriginatedRateContractDO().getRateContractId());
			rateContractTO.setOldContractExpDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(rateContractDO.getOriginatedRateContractDO().getValidToDate()));
		}

		rateContractTO.setIsRenewed(rateContractDO.getIsRenewed());

		LOGGER.trace("RateContractServiceImpl::contractDetailsDomainConverter()::END");
		return rateContractTO;

	}

	public CityTO getCity(CityTO cityTO) throws CGSystemException,
	CGBusinessException {
		return rateCommonService.getCity(cityTO);
	}

	@Override
	public RateContractDO saveRateContractPickupDlvDtls(RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RateContractServiceImpl::saveRateContractPickupDtls()::START");
		//boolean result = Boolean.FALSE;
		RateContractDO rcDO = null;
		List<ContractPaymentBillingLocationDO> domainList = null;
		Set<ContractPaymentBillingLocationDO> domainSet = new HashSet<ContractPaymentBillingLocationDO>();
		Set<ContractPaymentBillingLocationDO> locationSet = new HashSet<ContractPaymentBillingLocationDO>();
		Set<ContractPaymentBillingLocationDO> locSet = new HashSet<ContractPaymentBillingLocationDO>();
		//List<ContractPaymentBillingLocationTO> toList = null;
		String shipToCode = null;
		domainList = prepareContractPaymentBillingLocationList(rateContractTO);
		//domainList = convertTOListToDOList(toList);
		if (!StringUtil.isEmptyColletion(domainList)) {
			rcDO = rateContractDAO.searchRateContractBillingDtls(rateContractTO);
			if(!CGCollectionUtils.isEmpty(rcDO.getConPayBillLocDO())){
				locSet = rcDO.getConPayBillLocDO();
				if(!StringUtil.isStringEmpty(rateContractTO.getPickDlvIdsArr())){
					String[] pickDlvIds = rateContractTO.getPickDlvIdsArr().split(CommonConstants.COMMA);
					int pLen = pickDlvIds.length;
					int cnt = 0;
					for(ContractPaymentBillingLocationDO pcDO : locSet){
						for(int i = 0;i<pLen;i++){
							if(pcDO.getContractPaymentBillingLocationId().equals(Integer.parseInt(pickDlvIds[i]))){
								pcDO.setStatus(RateContractConstants.INACTIVE);
								shipToCode = pcDO.getShippedToCode();
								domainSet.add(pcDO);
								cnt++;
								break;
							}
							if(cnt == pLen){
								break;
							}
						}
					}
				}
			}

			if(StringUtil.isStringEmpty(shipToCode) && (rateContractTO.getContractStatus().equals(RateContractConstants.ACTIVE) ||
					rateContractTO.getContractStatus().equals(RateCommonConstants.CONTRACT_STATUS_BLOCKED) ||
					rateContractTO.getContractStatus().equals(RateContractConstants.INACTIVE))){
				for(ContractPaymentBillingLocationDO cpblDO : domainList){
					if(!StringUtil.isStringEmpty(cpblDO.getShippedToCode())){
						shipToCode = cpblDO.getShippedToCode();
						break;
					}
				}
			}
			
			for(ContractPaymentBillingLocationDO cpblDO : domainList){
				if((rateContractTO.getContractStatus().equals(RateContractConstants.ACTIVE) ||
						rateContractTO.getContractStatus().equals(RateCommonConstants.CONTRACT_STATUS_BLOCKED) ||
						rateContractTO.getContractStatus().equals(RateContractConstants.INACTIVE))
						&& StringUtil.isStringEmpty(cpblDO.getShippedToCode())){
					cpblDO.setShippedToCode(shipToCode);
				}
				domainSet.add(cpblDO);
			}

			// Logic to prevent multiple locations from being stamped against a DBDP contract
			if (RateContractConstants.BILL_TYPE_DBDP.equals(rcDO.getTypeOfBilling())) {
				for (ContractPaymentBillingLocationDO cpblDoFromDatabase : locSet) {
					if (RateContractConstants.ACTIVE.equals(cpblDoFromDatabase.getStatus())) {
						for (ContractPaymentBillingLocationDO cpblDoFromScreen : domainSet) {
							if (RateContractConstants.ACTIVE.equals(cpblDoFromScreen.getStatus()) && 
									(StringUtil.isEmptyInteger(cpblDoFromScreen.getContractPaymentBillingLocationId()) || 
									cpblDoFromScreen.getContractPaymentBillingLocationId().intValue() !=
									cpblDoFromDatabase.getContractPaymentBillingLocationId().intValue())) {
								throw new CGBusinessException(RateErrorConstants.MULTIPLE_LOCATIONS_FOR_DBDP_CONTRACT);
							}
						}
					}
				}
			}
			
			// Setting the Set<ContractPaymentBillingLocationDO> to parent rate contract DO
			rcDO.setConPayBillLocDO(domainSet);
			
			
			if (rcDO.getContractStatus().equals(RateContractConstants.ACTIVE) 
					|| rcDO.getContractStatus().equals(RateContractConstants.BLOCKED)
					|| rcDO.getContractStatus().equals(RateContractConstants.INACTIVE)) {
				
				// Reset the flag DT_TO_BRANCH = 'N' for the concerned rate contract
				rcDO.setDtToBranch(CommonConstants.NO);
				
				// Extra check added to prevent shipped to code from getting stamped as null for CBCP contract
				if (RateContractConstants.BILL_TYPE_CBCP.equals(rcDO.getTypeOfBilling())) {
					for (ContractPaymentBillingLocationDO contractPaymentBillingLocationDo : rcDO.getConPayBillLocDO()) {
						if (StringUtil.isStringEmpty(contractPaymentBillingLocationDo.getShippedToCode())) {
							// contractPaymentBillingLocationDo.setShippedToCode(shipToCode);
							LOGGER.error("RateContractServiceImpl::saveRateContractPickupDtls()::ERROR:: Empty Shipped To Code found for contract No [" +
								rcDO.getRateContractNo() + "]");
							throw new CGBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
						}
					}
				}
			}
			
			/*result = rateContractDAO.saveRateContractPickupDlvDtls(domainList);*/
			
			rcDO = rateContractDAO.saveOrUpdateContract(rcDO);
			//rcDO = rateContractDAO.saveOrUpdateContractPickupDetails(rcDO);
			//rcDO = rateContractDAO.searchRateContractBillingDtls(rateContractTO);
			if(!CGCollectionUtils.isEmpty(rcDO.getConPayBillLocDO())){
				for(ContractPaymentBillingLocationDO cpblDO : rcDO.getConPayBillLocDO()){
					if(cpblDO.getStatus().equals(RateContractConstants.ACTIVE)){
						locationSet.add(cpblDO);
					}
					cpblDO.setRateContractDO(null);
				}
			}
			rcDO.setConPayBillLocDO(locationSet);
			rcDO.setRateContractSpocDO(null);
			rcDO.getRateQuotationDO().setRateFixedChargeDO(null);
			rcDO.getRateQuotationDO().setRateQuotationProductCategoryHeaderDO(null);
			rcDO.getRateQuotationDO().setRateQuotationRtoChargesDO(null);
			rcDO.getRateQuotationDO().setCodChargeDO(null);
			rcDO.getRateQuotationDO().setFixedChargesConfigDO(null);
			rcDO.getRateQuotationDO().setRatequotationOriginatedFrom(null);
			rcDO.setOriginatedRateContractDO(null);
			/*domainSet = rcDO.getConPayBillLocDO();
				domainList.clear();
				for(ContractPaymentBillingLocationDO cpblDO : domainSet){
					domainList.add(cpblDO);
				}
			 */
			//if (!StringUtil.isEmptyColletion(domainList)) {
			/*int size = domainList.size();
					List<Integer> conPayBillLocIdList = new ArrayList<Integer>(
							size);
					List<Integer> pickupDlvLocIdList = new ArrayList<Integer>(
							size);
					List<Integer> contractIdList = new ArrayList<Integer>(size);
					List<Integer> addressIdList = new ArrayList<Integer>(size);
					for (ContractPaymentBillingLocationDO locDO : domainList) {
						 The contractPaymentBillingLocationId */
			/*if(locDO.getStatus().equals(RateContractConstants.ACTIVE)){
						if (!StringUtil.isEmptyInteger(locDO
								.getContractPaymentBillingLocationId())) {
							conPayBillLocIdList.add(locDO
									.getContractPaymentBillingLocationId());
						}
						/* The pickupDlvLocId. */
			/*if (!StringUtil.isNull(locDO
								.getPickupDeliveryLocation())
								&& !StringUtil.isEmptyInteger(locDO
										.getPickupDeliveryLocation()
										.getPickupDlvLocId())) {
							pickupDlvLocIdList.add(locDO
									.getPickupDeliveryLocation()
									.getPickupDlvLocId());
							/* The contractId. */
			/*if (!StringUtil.isNull(locDO
									.getPickupDeliveryLocation()
									.getPickupDlvContract())
									&& !StringUtil.isEmptyInteger(locDO
											.getPickupDeliveryLocation()
											.getPickupDlvContract()
											.getContractId())) {
								contractIdList
										.add(locDO.getPickupDeliveryLocation()
												.getPickupDlvContract()
												.getContractId());
							}
							/* The addressId. */
			/*if (!StringUtil.isNull(locDO
									.getPickupDeliveryLocation().getAddress())
									&& !StringUtil.isEmptyInteger(locDO
											.getPickupDeliveryLocation()
											.getAddress().getAddressId())) {
								addressIdList.add(locDO
										.getPickupDeliveryLocation()
										.getAddress().getAddressId());
							}
						}
						}
					}
					rateContractTO.setConPayBillLocIdList(conPayBillLocIdList);
					rateContractTO.setPickupDlvLocIdList(pickupDlvLocIdList);
					rateContractTO.setContractIdList(contractIdList);
					rateContractTO.setAddressIdList(addressIdList);
			 */
			//toList = conPayBillLocDomainConverter(domainList);
			//rateContractTO.setContractLocationList(toList);
			//result = Boolean.TRUE;
			//}
		} else {
			throw new CGBusinessException();
		}		
		LOGGER.debug("RateContractServiceImpl::saveRateContractPickupDtls()::END");
		return rcDO;
	}

	/**
	 * To convert RateContractTO to ContractPaymentBillingLocationTO list
	 * 
	 * @param to
	 * @return list
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*private List<ContractPaymentBillingLocationTO> prepareContractPaymentBillingLocationList(
			RateContractTO to) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::prepareContractPaymentBillingLocationList()::START");
		List<ContractPaymentBillingLocationTO> list = new ArrayList<ContractPaymentBillingLocationTO>(
				to.getPincode().length);
		int pinLen = to.getPincode().length;
		for (int i = 0; i < pinLen; i++) {
			// Setting Contract Payment Billing Location //
			ContractPaymentBillingLocationTO contractLocationTO = new ContractPaymentBillingLocationTO();
			if (!StringUtil.isEmptyInteger(to
					.getContractPaymentBillingLocationId()[i])) {
				contractLocationTO.setContractPaymentBillingLocationId(to
						.getContractPaymentBillingLocationId()[i]);
			}

			// Setting Pickup Delivery Location //
			PickupDeliveryLocationTO locationTO = new PickupDeliveryLocationTO();
			if (!StringUtil.isEmptyInteger(to.getPickupDlvLocId()[i])) {
				locationTO.setPickupDlvLocId(to.getPickupDlvLocId()[i]);
			}

			// Setting Address //
			PickupDeliveryAddressTO address = new PickupDeliveryAddressTO();

			if (!StringUtil.isEmptyInteger(to.getAddressId()[i])) {
				address.setAddressId(to.getAddressId()[i]);
			}
			// Location Name //
			if (!StringUtil.isStringEmpty(to.getLocationName()[i])) {
				address.setName(to.getLocationName()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress1()[i])) {
				address.setAddress1(to.getAddress1()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress2()[i])) {
				address.setAddress2(to.getAddress2()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress3()[i])) {
				address.setAddress3(to.getAddress3()[i]);
			}
			// Contact Person //
			if (!StringUtil.isStringEmpty(to.getContactPerson()[i])) {
				address.setContactPerson(to.getContactPerson()[i]);
			}
			// Designation //
			if (!StringUtil.isStringEmpty(to.getDesignation()[i])) {
				address.setDesignation(to.getDesignation()[i]);
			}
			// Mobile //
			if (!StringUtil.isStringEmpty(to.getAddress3()[i])) {
				address.setMobile(to.getMobile()[i]);
			}
			// Email //
			if (!StringUtil.isStringEmpty(to.getAddress3()[i])) {
				address.setEmail(to.getEmail()[i]);
			}
			address.setStatus(RateContractConstants.ACTIVE);

			// Pincode //
			if(!StringUtil.isStringEmpty(to.getPincode()[i])){
				PincodeTO pincode = new PincodeTO();
				pincode.setPincode(to.getPincode()[i]);
				//pincode = rateCommonService.validatePincode(pincode);
				pincode.setPincodeId(to.getLocPincodeId()[i]);
				pincode.setCityId(to.getLocCity()[i]);
				address.setPincode(pincode);
			}
			locationTO.setPickupDlvAddress(address);

			// Setting Pickup Delivery Contract //
			PickupDeliveryContractTO contractTO = new PickupDeliveryContractTO();
			if (!StringUtil.isEmptyInteger(to.getContractId()[i])) {
				contractTO.setContractId(to.getContractId()[i]);
			}
			// Customer //
			if(!StringUtil.isNull(to.getRateQuotationTO()) 
					&& !StringUtil.isNull(to.getRateQuotationTO().getCustomer())
						&& !StringUtil.isEmptyInteger(to.getRateQuotationTO()
								.getCustomer().getCustomerId())){
				CustomerTO customer=new CustomerTO();
				customer.setCustomerId(to.getRateQuotationTO()
						.getCustomer().getCustomerId());
				contractTO.setCustomer(customer);
			}
			// Office //
			OfficeTO office = new OfficeTO();
			if(!StringUtil.isEmptyInteger(to.getPickupBranch()[i])){
				office.setOfficeId(to.getPickupBranch()[i]);
				office.setOfficeCode(to.getPickupBranchCode()[i]);
				contractTO.setOffice(office);
			}

			if(!StringUtil.isStringEmpty(to.getPickupDlvContractType())){ 
				contractTO.setContractType(to.getPickupDlvContractType());
			}
			contractTO.setStatus(RateContractConstants.ACTIVE);
			if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
				contractTO.setCreatedBy(to.getCreatedBy());
			}
			if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
				contractTO.setUpdatedBy(to.getUpdatedBy());
			}
			locationTO.setPickupDlvContract(contractTO);

			// To get location code //
			if (StringUtil.isStringEmpty(to.getLocCode()[i]) 
					&& !StringUtil.isStringEmpty(office.getOfficeCode())) {
				String locCode = getLocationCode(office);
				locationTO.setPickupDlvLocCode(locCode);
			} else {
				locationTO.setPickupDlvLocCode(to.getLocCode()[i]);
			}

			contractLocationTO.setPickupDeliveryLocationTO(locationTO);
			if(!StringUtil.isStringEmpty(to.getCustomerCode()[i])){
				contractLocationTO.setShippedToCode(to.getCustomerCode()[i]);
			}

			// Location type i.e. B-Billing, P-Payment, BP-Billing & Payment. //
			String locationType = CommonConstants.EMPTY_STRING;
			if (StringUtil.equals(to.getIsBillLoc()[i], CommonConstants.YES)) {
				locationType += RateContractConstants.LOCATION_TYPE_BILLING;
			}
			if (StringUtil.equals(to.getIsPayLoc()[i], CommonConstants.YES)) {
				locationType += RateContractConstants.LOCATION_TYPE_PAYMENT;
			}
			// B-Billing, P-Payment, BP-Billing & Payment //
			if (!StringUtil.isStringEmpty(locationType)) {
				contractLocationTO.setLocationOperationType(locationType);
			}
			if (!StringUtil.isEmptyInteger(to.getRateContractId())) {
				contractLocationTO.setRateContractId(to.getRateContractId());
			}
			if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
				contractLocationTO.setCreatedBy(to.getCreatedBy());
			}
			if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
				contractLocationTO.setUpdatedBy(to.getUpdatedBy());
			}
			list.add(contractLocationTO);
		}// END FOR LOOP 
		LOGGER.trace("RateContractServiceImpl::prepareContractPaymentBillingLocationList()::END");
		return list;
	}
	 */
	private List<ContractPaymentBillingLocationDO> prepareContractPaymentBillingLocationList(
			RateContractTO to) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::prepareContractPaymentBillingLocationList()::START");
		int pinLen = to.getPincode().length;

		List<ContractPaymentBillingLocationDO> domainlist = new ArrayList<ContractPaymentBillingLocationDO>();

		for (int i = 0; i < pinLen; i++) {
			/** Setting Contract Payment Billing Location */
			ContractPaymentBillingLocationDO contractLocationDO = new ContractPaymentBillingLocationDO();

			if (!StringUtil.isEmptyInteger(to.getContractPaymentBillingLocationId()[i])) {
				contractLocationDO.setContractPaymentBillingLocationId(to.getContractPaymentBillingLocationId()[i]);
			}
			contractLocationDO.setStatus(RateContractConstants.ACTIVE);
			/** Setting Pickup Delivery Location */
			PickupDeliveryLocationDO locationDO = new PickupDeliveryLocationDO();

			PickupDeliveryLocationTO locationTO = new PickupDeliveryLocationTO();
			if (!StringUtil.isEmptyInteger(to.getPickupDlvLocId()[i])) {
				locationTO.setPickupDlvLocId(to.getPickupDlvLocId()[i]);
				locationDO.setPickupDlvLocId(to.getPickupDlvLocId()[i]);
			}

			/** Setting Address */
			AddressDO locAddress = new AddressDO();


			if (!StringUtil.isEmptyInteger(to.getAddressId()[i])) {
				locAddress.setAddressId(to.getAddressId()[i]);
			}

			if (!StringUtil.isStringEmpty(to.getLocationName()[i])) {
				locAddress.setName(to.getLocationName()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress1()[i])) {
				locAddress.setAddress1(to.getAddress1()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress2()[i])) {
				locAddress.setAddress2(to.getAddress2()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getAddress3()[i])) {
				locAddress.setAddress3(to.getAddress3()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getContactPerson()[i])) {
				locAddress.setContactPerson(to.getContactPerson()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getDesignation()[i])) {
				locAddress.setDesignation(to.getDesignation()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getMobile()[i])) {
				locAddress.setMobile(to.getMobile()[i]);
			}
			if (!StringUtil.isStringEmpty(to.getEmail()[i])) {
				locAddress.setEmail(to.getEmail()[i]);
			}

			locAddress.setStatus(RateContractConstants.ACTIVE);

			/* Pincode */
			if(!StringUtil.isStringEmpty(to.getPincode()[i])){
				PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(to.getLocPincodeId()[i]);
				locAddress.setPincodeDO(pincodeDO);
				// City
				if (!StringUtil.isNull(to.getLocCity()[i])) {
					pincodeDO.setCityId(to.getLocCity()[i]);
					CityDO city = new CityDO();
					city.setCityId(to.getLocCity()[i]);
					locAddress.setCityDO(city);
				}
			}

			locationDO.setAddress(locAddress);

			/** Setting Pickup Delivery Contract */
			PickupDeliveryContractDO contractDO = new PickupDeliveryContractDO();
			if (!StringUtil.isEmptyInteger(to.getContractId()[i])) {
				contractDO.setContractId(to.getContractId()[i]);
			}

			/* Customer */
			if(!StringUtil.isNull(to.getRateQuotationTO()) 
					&& !StringUtil.isNull(to.getRateQuotationTO().getCustomer())
					&& !StringUtil.isEmptyInteger(to.getRateQuotationTO().getCustomer().getCustomerId())){
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId(to.getRateQuotationTO().getCustomer().getCustomerId());
				contractDO.setCustomer(customer);
			}
			// Office
			OfficeDO officeDO = new OfficeDO();
			if (!StringUtil.isNull(to.getPickupBranch()[i])) {
				officeDO.setOfficeId(to.getPickupBranch()[i]);
				officeDO.setOfficeCode(to.getPickupBranchCode()[i]);
				contractDO.setOffice(officeDO);
			}
			if(!StringUtil.isStringEmpty(to.getPickupDlvContractType())){
				contractDO.setContractType(to.getPickupDlvContractType());
			}

			contractDO.setStatus(RateContractConstants.ACTIVE);


			if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
				contractDO.setCreatedBy(to.getCreatedBy());
			}
			if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
				contractDO.setUpdatedBy(to.getUpdatedBy());
			}
			locationDO.setPickupDlvContract(contractDO);

			/* To get location code */
			if (StringUtil.isStringEmpty(to.getLocCode()[i]) 
					&& !StringUtil.isStringEmpty(officeDO.getOfficeCode())) {
				String locCode = getLocationCode(officeDO.getOfficeCode());
				locationTO.setPickupDlvLocCode(locCode);
			} else {
				locationTO.setPickupDlvLocCode(to.getLocCode()[i]);
			}

			locationDO.setPickupDlvLocCode(locationTO.getPickupDlvLocCode());
			contractLocationDO.setPickupDeliveryLocation(locationDO);



			if(!StringUtil.isStringEmpty(to.getCustomerCode()[i])){
				contractLocationDO.setShippedToCode(to.getCustomerCode()[i]);
			}
			/* Location type i.e. B-Billing, P-Payment, BP-Billing & Payment. */
			String locationType = CommonConstants.EMPTY_STRING;
			if (StringUtil.equals(to.getIsBillLoc()[i], CommonConstants.YES)) {
				locationType += RateContractConstants.LOCATION_TYPE_BILLING;
			}
			if (StringUtil.equals(to.getIsPayLoc()[i], CommonConstants.YES)) {
				locationType += RateContractConstants.LOCATION_TYPE_PAYMENT;
			}
			/* B-Billing, P-Payment, BP-Billing & Payment */
			if (!StringUtil.isStringEmpty(locationType)) {
				contractLocationDO.setLocationOperationType(locationType);
			}
			if(!StringUtil.isEmptyInteger(to.getRateContractId())){
				RateContractDO rateContractDO=new RateContractDO();
				rateContractDO.setRateContractId(to.getRateContractId());
				contractLocationDO.setRateContractDO(rateContractDO);
			}
			if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
				contractLocationDO.setCreatedBy(to.getCreatedBy());
			}
			if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
				contractLocationDO.setUpdatedBy(to.getUpdatedBy());
			}

			domainlist.add(contractLocationDO);
		}/* END FOR LOOP */
		LOGGER.trace("RateContractServiceImpl::prepareContractPaymentBillingLocationList()::END");
		return domainlist;
	}
	/**
	 * To convert ContractPaymentBillingLocationTO list to
	 * ContractPaymentBillingLocationDO list
	 * 
	 * @param toList
	 * @return list
	 */
	private List<ContractPaymentBillingLocationDO> convertTOListToDOList(
			List<ContractPaymentBillingLocationTO> toList) {
		LOGGER.trace("RateContractServiceImpl::convertTOListToDOList()::START");
		List<ContractPaymentBillingLocationDO> list = new ArrayList<ContractPaymentBillingLocationDO>(
				toList.size());
		for (ContractPaymentBillingLocationTO to : toList) {
			/** Setting Contract Payment Billing Location */
			ContractPaymentBillingLocationDO contractLocationDO = new ContractPaymentBillingLocationDO();
			if (!StringUtil.isEmptyInteger(to
					.getContractPaymentBillingLocationId())) {
				contractLocationDO.setContractPaymentBillingLocationId(to
						.getContractPaymentBillingLocationId());
			}
			contractLocationDO.setStatus(RateContractConstants.ACTIVE);
			/** Setting Pickup Delivery Location */
			PickupDeliveryLocationDO locationDO = new PickupDeliveryLocationDO();
			PickupDeliveryLocationTO locationTO = to
					.getPickupDeliveryLocationTO();
			if (!StringUtil.isNull(locationTO)) {
				if (!StringUtil.isEmptyInteger(locationTO.getPickupDlvLocId())) {
					locationDO
					.setPickupDlvLocId(locationTO.getPickupDlvLocId());
				}

				/** Setting Address */
				AddressDO address = new AddressDO();
				PickupDeliveryAddressTO addressTO = 
						locationTO.getPickupDlvAddress();
				if (!StringUtil.isNull(addressTO)) {
					if (!StringUtil.isEmptyInteger(addressTO.getAddressId())) {
						address.setAddressId(addressTO.getAddressId());
					}
					if (!StringUtil.isStringEmpty(addressTO.getName())) {
						address.setName(addressTO.getName());
					}
					if (!StringUtil.isStringEmpty(addressTO.getAddress1())) {
						address.setAddress1(addressTO.getAddress1());
					}
					if (!StringUtil.isStringEmpty(addressTO.getAddress2())) {
						address.setAddress2(addressTO.getAddress2());
					}
					if (!StringUtil.isStringEmpty(addressTO.getAddress3())) {
						address.setAddress3(addressTO.getAddress3());
					}
					if (!StringUtil.isStringEmpty(addressTO.getContactPerson())) {
						address.setContactPerson(addressTO.getContactPerson());
					}
					if (!StringUtil.isStringEmpty(addressTO.getDesignation())) {
						address.setDesignation(addressTO.getDesignation());
					}
					if (!StringUtil.isStringEmpty(addressTO.getMobile())) {
						address.setMobile(addressTO.getMobile());
					}
					if (!StringUtil.isStringEmpty(addressTO.getEmail())) {
						address.setEmail(addressTO.getEmail());
					}
					if (!StringUtil.isStringEmpty(addressTO.getStatus())) {
						address.setStatus(addressTO.getStatus());
					}
					/* Pincode */
					if (!StringUtil.isNull(addressTO.getPincode())) {
						PincodeDO pincode = new PincodeDO();
						pincode.setPincodeId(addressTO.getPincode()
								.getPincodeId());
						pincode.setCityId(addressTO.getPincode()
								.getCityId());
						address.setPincodeDO(pincode);
						// City
						if (!StringUtil.isNull(addressTO.getPincode()
								.getCityId())) {
							CityDO city = new CityDO();
							city.setCityId(addressTO.getPincode().getCityId());
							address.setCityDO(city);
						}
					}
					locationDO.setAddress(address);
				}

				/** Setting Pickup Delivery Contract */
				PickupDeliveryContractDO contractDO = new PickupDeliveryContractDO();
				PickupDeliveryContractTO contractTO = locationTO
						.getPickupDlvContract();
				if (!StringUtil.isNull(contractTO)) {
					if (!StringUtil.isEmptyInteger(contractTO.getContractId())) {
						contractDO.setContractId(contractTO.getContractId());
					}
					/* Customer */
					if (!StringUtil.isNull(contractTO.getCustomer())) {
						CustomerDO customer = new CustomerDO();
						customer.setCustomerId(contractTO.getCustomer()
								.getCustomerId());
						contractDO.setCustomer(customer);
					}
					// Office
					if (!StringUtil.isNull(contractTO.getOffice())) {
						OfficeDO office = new OfficeDO();
						office.setOfficeId(contractTO.getOffice().getOfficeId());
						contractDO.setOffice(office);
					}
					if(!StringUtil.isStringEmpty(contractTO.getContractType())){
						contractDO.setContractType(contractTO.getContractType());
					}
					if(!StringUtil.isStringEmpty(contractTO.getStatus())){
						contractDO.setStatus(contractTO.getStatus());
					}
					if (!StringUtil.isEmptyInteger(contractTO.getCreatedBy())) {
						contractDO.setCreatedBy(contractTO.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger(contractTO.getUpdatedBy())) {
						contractDO.setUpdatedBy(contractTO.getUpdatedBy());
					}
					locationDO.setPickupDlvContract(contractDO);
				}
				locationDO
				.setPickupDlvLocCode(locationTO.getPickupDlvLocCode());
				contractLocationDO.setPickupDeliveryLocation(locationDO);
			}
			if(!StringUtil.isStringEmpty(to.getShippedToCode())){
				contractLocationDO.setShippedToCode(to.getShippedToCode());
			}
			if (!StringUtil.isStringEmpty(to.getLocationOperationType())) {
				contractLocationDO.setLocationOperationType(to
						.getLocationOperationType());
			}
			if(!StringUtil.isEmptyInteger(to.getRateContractId())){
				RateContractDO rateContractDO=new RateContractDO();
				rateContractDO.setRateContractId(to.getRateContractId());
				contractLocationDO.setRateContractDO(rateContractDO);
			}
			if (!StringUtil.isEmptyInteger(to.getCreatedBy())) {
				contractLocationDO.setCreatedBy(to.getCreatedBy());
			}
			if (!StringUtil.isEmptyInteger(to.getUpdatedBy())) {
				contractLocationDO.setUpdatedBy(to.getUpdatedBy());
			}
			list.add(contractLocationDO);
		}// END FOR LOOP
		LOGGER.trace("RateContractServiceImpl::convertTOListToDOList()::END");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateCustomerSearchTO> searchCustomerInfo(String custName)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::searchCustomerInfo()::START");
		List<CustomerWrapperDO> custDos = rateContractDAO
				.searchCustomerInfo(custName);
		List<RateCustomerSearchTO> custTos = (List<RateCustomerSearchTO>) CGObjectConverter
				.createTOListFromDomainList(custDos, RateCustomerSearchTO.class);
		LOGGER.trace("RateContractServiceImpl::searchCustomerInfo()::END");
		return custTos;
	}

	@Override
	public void contractExpiryEmailTrigger() throws CGSystemException,
	CGBusinessException ,HttpException, ClassNotFoundException, IOException{
		LOGGER.trace("RateContractServiceImpl::contractExpiryEmailTrigger()::START");


		Date day90 = DateUtil.getFutureDate(RateContractConstants.Days90);
		Date day60 = DateUtil.getFutureDate(RateContractConstants.Days60);
		Date day30 = DateUtil.getFutureDate(RateContractConstants.Days30);
		Date day15 = DateUtil.getFutureDate(RateContractConstants.Days15);

		Integer date90 = RateContractConstants.Days90;
		Integer date60 = RateContractConstants.Days60;
		Integer date30 = RateContractConstants.Days30;
		Integer date15 = RateContractConstants.Days15;


		String expDays90 = RateContractConstants.ExpDays90;
		String expDays60 = RateContractConstants.ExpDays60;
		String expDays30 = RateContractConstants.ExpDays30;
		String expDays15 = RateContractConstants.ExpDays15;
		Date todayDate = DateUtil.getCurrentDate();


		String screen[] =  new String[] {expDays90,expDays60,expDays30,expDays15};
		Date days[] = new Date[] {day90,day60,day30,day15};
		Integer dates[]=new Integer[]{date90,date60,date30,date15};
		/*for(int i=0;i<screen.length;i++){

			List<RateQuotationWrapperDO> quotationWrapperDOlist1 = rateContractDAO
					.getContractListForEmailForRHO(days[i],screen[i],todayDate);

			isMailSent=sendEmailForContractExpiry(quotationWrapperDOlist1,dates[i]);

		}*/
		int scrLen = screen.length;
		for(int j=0;j<scrLen;j++){

			List<RateQuotationWrapperDO> quotationWrapperDOlist1 = rateContractDAO
					.getContractListForEmailForArea(days[j],screen[j],todayDate);

			sendEmailForContractExpiry(quotationWrapperDOlist1,dates[j]);

		}
		LOGGER.trace("RateContractServiceImpl::contractExpiryEmailTrigger()::END");
	}





	private boolean sendEmailForContractExpiry(
			List<RateQuotationWrapperDO> quotationWrapperDOlist,Integer noOfDays) {
		LOGGER.trace("RateContractServiceImpl::sendEmailForContractExpiry()::START");
		boolean isMailSent=Boolean.FALSE;
		if (!StringUtil.isEmptyColletion(quotationWrapperDOlist)) {
			for (RateQuotationWrapperDO wrapperDO : quotationWrapperDOlist) {

				String emailSubj = RateContractConstants.EXPIRY_CONTRACT_EMAIL_SUBJECT;
				String templateName = "";
				String firstName = wrapperDO.getFirstName();
				String lastName = wrapperDO.getLastName();
				String name = firstName + " " + lastName;
				String customerName=wrapperDO.getBusinessName();
				String customerCode=wrapperDO.getCustomerCode();
				String endDate=wrapperDO.getValidToDate();
				String to[] = new String[]{wrapperDO.getEmailIdTO()};
				String cc[] =  new String[]{wrapperDO.getEmailIdCC()};
				//String bcc[] =  new String[]{};
				Map<Object, Object> mailTemplate = new HashMap<Object, Object>();
				mailTemplate.put(RateContractConstants.DAYS, noOfDays);
				mailTemplate.put(RateContractConstants.RATE_CONTRACT_NO, wrapperDO.getContractNo());
				mailTemplate.put(RateContractConstants.NAME, name);
				mailTemplate.put(RateContractConstants.CUSTOMER_NAME, customerName);
				mailTemplate.put(RateContractConstants.CUSTOMER_CODE, customerCode);
				mailTemplate.put(RateContractConstants.END_DATE, endDate);
				templateName = RateContractConstants.CONTRACT_EXP_ALERT_EMAIL_VM;
				String from = FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;

				MailSenderTO mailsenderTO =new MailSenderTO();
				mailsenderTO.setTo(to);
				mailsenderTO.setFrom(from);
				mailsenderTO.setCc(cc);
				mailsenderTO.setTemplateVariables(mailTemplate);
				mailsenderTO.setMailSubject(emailSubj);
				mailsenderTO.setTemplateName(templateName);

				emailSenderUtil.sendEmail(mailsenderTO);

				/*emailSenderService.sendEmailByTemplate(from, to,cc,bcc,
						emailSubj, mailTemplate, templateName);
				 */
				isMailSent = Boolean.TRUE;
			}

		}
		LOGGER.trace("RateContractServiceImpl::sendEmailForContractExpiry()::END");
		return isMailSent;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationListViewTO> rateContractListViewDetails(
			Integer userId, Integer[] region, Integer[] city, String fromDate,
			String toDate, String contractNo, String status, String type, String officeType)
					throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::rateContractListViewDetails()::END");
		List<RateQuotationWrapperDO> rqwDOList = null;
		List<RateQuotationListViewTO> rqwlvTOList = null;
		rqwDOList = rateContractDAO.rateContractListViewDetails(userId,
				region, city, fromDate, toDate, contractNo, status, type, officeType);

		if (!CGCollectionUtils.isEmpty(rqwDOList)) {
			rqwlvTOList = new ArrayList<RateQuotationListViewTO>();

			rqwlvTOList = (List<RateQuotationListViewTO>) CGObjectConverter
					.createTOListFromDomainList(rqwDOList,
							RateQuotationListViewTO.class);

		}

		LOGGER.trace("RateContractServiceImpl::rateContractListViewDetails()::END");
		return rqwlvTOList;
	}

	@Override
	public List<StockStandardTypeTO> getStandardType(String string)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getStandardType(string);
	}

	@Override
	public Boolean submitContract(RateContractTO contractTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RateContractServiceImpl::submitContract()::START");
		Boolean isUpdated = Boolean.FALSE;
		Calendar c = null;
		String conOriginalStatus = null;
		String conActualStatus = null;
		String dateStr = null;
		if(!StringUtil.isNull(contractTO)){
			// Search the actual contract from the DB
			RateContractDO rcDO = rateContractDAO.searchRateContractBillingDtls(contractTO);
			if(StringUtil.isNull(rcDO.getValidFromDate())){
				ExceptionUtil.prepareBusinessException(RateErrorConstants.EMPTY_VALID_FROM_DATE);
			}else{
				if(StringUtil.isStringEmpty(contractTO.getUserType())
						&& DateUtil.stringToDDMMYYYYFormat(DateUtil.getDDMMYYYYDateToString(rcDO.getValidFromDate()))
						.compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY())) <= 0){
					ExceptionUtil.prepareBusinessException(RateErrorConstants.INVALID_VALID_FROM_DATE);
				}
			}

			if(!StringUtil.isNull(rcDO) 
					&& (!StringUtil.isNull(rcDO.getConPayBillLocDO()) && rcDO.getConPayBillLocDO().size()>0)){

				// This IF condition is basically a validation conducted in case of DBCP contracts
				if(rcDO.getTypeOfBilling().equals(RateContractConstants.BILL_TYPE_DBCP)){
					if(StringUtil.isStringEmpty(rcDO.getRateQuotationDO().getCustomer().getCustomerCode())){
						ExceptionUtil.prepareBusinessException(RateErrorConstants.EMPTY_SLOD_TO_CODE);
					}else{
						Set<ContractPaymentBillingLocationDO> cbplSet = rcDO.getConPayBillLocDO();
						for(ContractPaymentBillingLocationDO cpbl : cbplSet){
							/* The below if condition has been modified in order to consider only those pickup/delivery locations that are active.
							 * The ones that are inactive(deleted) are skipped -- Code changes done by Tejas*/
							if(RateContractConstants.ACTIVE.equals(cpbl.getStatus()) && StringUtil.isStringEmpty(cpbl.getShippedToCode())){ 
								if(!StringUtil.isNull(cpbl.getPickupDeliveryLocation()) && 
										!StringUtil.isNull(cpbl.getPickupDeliveryLocation().getPickupDlvContract())){
									if(cpbl.getPickupDeliveryLocation().getPickupDlvContract().getContractType().equals(RateContractConstants.CONTRACT_TYPE_PICKUP)){
										ExceptionUtil.prepareBusinessException(RateErrorConstants.EMPTY_PICKUP_SHIP_TO_CODE);
									}else if(cpbl.getPickupDeliveryLocation().getPickupDlvContract().getContractType().equals(RateContractConstants.CONTRACT_TYPE_DELIVERY)){
										ExceptionUtil.prepareBusinessException(RateErrorConstants.EMPTY_DELIVERY_SHIP_TO_CODE);
									}
								}
							}
						}
					}
				}

				conOriginalStatus = rcDO.getContractStatus();
				if(!StringUtil.isNull(rcDO.getOriginatedRateContractDO())){

					if(DateUtil.stringToDDMMYYYYFormat(DateUtil.getDDMMYYYYDateToString(rcDO.getValidFromDate()))
							.compareTo(rcDO.getOriginatedRateContractDO().getValidToDate()) <= 0){
						ExceptionUtil.prepareBusinessException(RateErrorConstants.INVALID_RENEW_VALID_FROM_DATE, 
								new String[]{DateUtil.getDDMMYYYYDateString(rcDO.getOriginatedRateContractDO().getValidToDate())});
					}

					contractTO.setOriginatedRateContractId(rcDO.getOriginatedRateContractDO().getRateContractId());
					
					// If the contract is getting renewed, then OLD_CONTRACT(valid_to_date) = RENEWED_CONTRACT(valid_from_date) minus one
					try {	
						dateStr = DateUtil.getDDMMYYYYDateToString(rcDO.getValidFromDate());
						SimpleDateFormat sdf = new SimpleDateFormat(FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
						c = Calendar.getInstance();
						c.setTime(sdf.parse(dateStr));

						c.add(Calendar.DATE, -1); // number of days to minus
						dateStr = sdf.format(c.getTime());

						contractTO.setOldExpDate(DateUtil.stringToDDMMYYYYFormat(dateStr));
					} 
					catch(Exception e) {
						ExceptionUtil.prepareBusinessException(RateErrorConstants.DATE_PARSE_ERROR);
					}
				}

				if(rcDO.getTypeOfBilling().equals(RateContractConstants.BILL_TYPE_DBCP)){
					contractTO.setCustomerSapStatus(CommonConstants.YES);
					contractTO.setTypeOfBilling(rcDO.getTypeOfBilling());
				}else{
					contractTO.setCustomerSapStatus(CommonConstants.NO);
				}

				// The below loop calculates the value of distribution channels
				if(!CGCollectionUtils.isEmpty(rcDO.getRateQuotationDO().getRateQuotationProductCategoryHeaderDO())){
					Set<RateQuotationProductCategoryHeaderDO> rqpchDOset = rcDO.getRateQuotationDO().getRateQuotationProductCategoryHeaderDO();
					String dcVal = CommonConstants.EMPTY_STRING;
					for(RateQuotationProductCategoryHeaderDO rqpchDO : rqpchDOset){
						if(dcVal.length() > 0){
							dcVal = dcVal + CommonConstants.COMMA;
						}
						dcVal = dcVal + rqpchDO.getRateProductCategory().getDistributionChannel();
					}

					if(dcVal.length() > 0){
						contractTO.setDistributionChannel(dcVal);
					}else{
						/* Distribution channel should never be null. The below line is added to throw an exception if
					distribution channel is found to be null before contract submit. Code added by -- Tejas*/
						//contractTO.setDistributionChannel(null);
						ExceptionUtil.prepareBusinessException(RateErrorConstants.CONTRACT_NOT_SUBMITTED_SUCCESSFULLY);
					}
				}

				/* The below if condition has been added as an additional check to ensure that contract number always gets stamped
				 * in the ff_d_customer table. If the contract number gets missed from screen, then the below piece of code will
				 * stamp the contract number again. Code added by -- Tejas */
				if(StringUtil.isStringEmpty(contractTO.getRateContractNo())){
					contractTO.setRateContractNo(rcDO.getRateContractNo());
				}
				LOGGER.debug("RateContractServiceImpl::submitContract()::[" + contractTO.getRateContractNo() + ", " + contractTO.getDistributionChannel() + "]");
				// The below line actually saves the contract in the respective tables
				isUpdated = rateContractDAO.submitContract(contractTO);

				if(isUpdated){
					conActualStatus = contractTO.getContractStatus();
					if(conOriginalStatus.equals(RateContractConstants.CREATED) 
							&& ((StringUtil.isStringEmpty(conActualStatus)) 
									|| (!StringUtil.isStringEmpty(conActualStatus) 
											&& (conActualStatus.equals(RateContractConstants.SUBMITTED) 
													|| conActualStatus.equals(RateContractConstants.ACTIVE))))) {
						sendContractCreatedEmail(rcDO, contractTO.getFromMailId());
					}
				}
			}
		}
		LOGGER.debug("RateContractServiceImpl::submitContract()::END");
		return isUpdated;
	}

	/**
	 * To get location code
	 * 
	 * @param office
	 * @return locationCode
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*private String getLocationCode(OfficeTO office) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RateContractServiceImpl::getLocationCode()::START");
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = prepateSeqGenConfigTO(
				RateContractConstants.PROCESS_CONTRACT_CUST_CODE,
				RateContractConstants.ONE_INT);
		sequenceGeneratorConfigTO = rateCommonService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		String genNo = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		String locationCode = office.getOfficeCode() + genNo;
		LOGGER.trace("RateContractServiceImpl::getLocationCode()::END");
		return locationCode;
	}*/

	private String getLocationCode(String officeCode) throws CGBusinessException,
	CGSystemException {
		LOGGER.trace("RateContractServiceImpl::getLocationCode()::START");
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = prepateSeqGenConfigTO(
				RateContractConstants.PROCESS_CONTRACT_CUST_CODE,
				RateContractConstants.ONE_INT);
		sequenceGeneratorConfigTO = rateCommonService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		String genNo = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		String locationCode = officeCode + genNo;
		LOGGER.trace("RateContractServiceImpl::getLocationCode()::END");
		return locationCode;
	}
	/**
	 * To prepare SequenceGeneratorConfigTO
	 * 
	 * @param process
	 * @param digits
	 * @return seqGenConfig
	 */
	private SequenceGeneratorConfigTO prepateSeqGenConfigTO(String process,
			Integer digits) {
		LOGGER.trace("RateContractServiceImpl::prepateSeqGenConfigTO()::START");
		SequenceGeneratorConfigTO seqGenConfig = new SequenceGeneratorConfigTO();
		seqGenConfig.setProcessRequesting(process);
		seqGenConfig.setNoOfSequencesToBegenerated(digits);
		seqGenConfig.setRequestDate(new Date());
		LOGGER.trace("RateContractServiceImpl::prepateSeqGenConfigTO()::END");
		return seqGenConfig;
	}

	@Override
	public List<ContractPaymentBillingLocationTO> searchRateContractPickupDlvDtls(
			RateContractTO to) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::searchRateContractPickupDlvDtls()::START");
		List<ContractPaymentBillingLocationDO> conPayBillLocDO = null;
		List<ContractPaymentBillingLocationTO> conPayBillLocTO = null;
		if (!StringUtil.isNull(to)) {
			conPayBillLocDO = rateContractDAO.searchRateContractPickupDlvDtls(to);
			if (!StringUtil.isEmptyColletion(conPayBillLocDO)) {
				conPayBillLocTO = conPayBillLocDomainConverter(conPayBillLocDO);
			}
		}
		LOGGER.trace("RateContractServiceImpl::searchRateContractPickupDlvDtls()::END");
		return conPayBillLocTO;
	}

	/**
	 * To convert ContractPaymentBillingLocationDO to
	 * ContractPaymentBillingLocationTO
	 * 
	 * @param domainList
	 * @return toList
	 */
	private List<ContractPaymentBillingLocationTO> conPayBillLocDomainConverter(
			List<ContractPaymentBillingLocationDO> domainList)
					throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::conPayBillLocDomainConverter()::START");
		List<ContractPaymentBillingLocationTO> toList = new ArrayList<ContractPaymentBillingLocationTO>(
				domainList.size());
		for (ContractPaymentBillingLocationDO domain : domainList) {
			if(domain.getStatus().equals(RateContractConstants.ACTIVE)){
				ContractPaymentBillingLocationTO to = new ContractPaymentBillingLocationTO();
				/* The contractPaymentBillingLocationId. */
				if (!StringUtil.isEmptyInteger(domain
						.getContractPaymentBillingLocationId())) {
					to.setContractPaymentBillingLocationId(domain
							.getContractPaymentBillingLocationId());
				}
				/* The shippedToCode. */
				if (!StringUtil.isStringEmpty(domain.getShippedToCode())) {
					to.setShippedToCode(domain.getShippedToCode());
				}
				/* The locationOperationType. */
				if (!StringUtil.isStringEmpty(domain.getLocationOperationType())) {
					to.setLocationOperationType(domain.getLocationOperationType());
				}
				/* Pickup Delivery Location. */
				if (!StringUtil.isNull(domain.getPickupDeliveryLocation())) {
					PickupDeliveryLocationTO pickupDlvLoc = new PickupDeliveryLocationTO();
					pickupDlvLoc.setPickupDlvLocId(domain
							.getPickupDeliveryLocation().getPickupDlvLocId());
					pickupDlvLoc.setPickupDlvLocCode(domain
							.getPickupDeliveryLocation().getPickupDlvLocCode());
					/* Pickup Delivery Contract. */
					if (!StringUtil.isNull(domain.getPickupDeliveryLocation()
							.getPickupDlvContract())) {
						PickupDeliveryContractTO pickupDlvContract = new PickupDeliveryContractTO();
						PickupDeliveryContractDO pickupDlvCon = domain
								.getPickupDeliveryLocation().getPickupDlvContract();
						pickupDlvContract.setContractId(pickupDlvCon
								.getContractId());
						pickupDlvContract.setContractType(pickupDlvCon
								.getContractType());
						/* Office */
						if (!StringUtil.isNull(pickupDlvCon.getOffice())) {
							OfficeTO office = new OfficeTO();
							office.setOfficeId(pickupDlvCon.getOffice()
									.getOfficeId());
							office.setOfficeCode(pickupDlvCon.getOffice()
									.getOfficeCode());
							pickupDlvContract.setOffice(office);
						}
						pickupDlvLoc.setPickupDlvContract(pickupDlvContract);
					}
					/* Address. */
					if (!StringUtil.isNull(domain.getPickupDeliveryLocation()
							.getAddress())) {
						PickupDeliveryAddressTO address = new PickupDeliveryAddressTO();
						AddressDO addressDO = domain.getPickupDeliveryLocation()
								.getAddress();
						if (!StringUtil.isEmptyInteger(addressDO.getAddressId())) {
							address.setAddressId(addressDO.getAddressId());
						}
						if (!StringUtil.isStringEmpty(addressDO.getName())) {
							address.setName(addressDO.getName());
						}
						if (!StringUtil.isStringEmpty(addressDO.getAddress1())) {
							address.setAddress1(addressDO.getAddress1());
						}
						if (!StringUtil.isStringEmpty(addressDO.getAddress2())) {
							address.setAddress2(addressDO.getAddress2());
						}
						if (!StringUtil.isStringEmpty(addressDO.getAddress3())) {
							address.setAddress3(addressDO.getAddress3());
						}
						if (!StringUtil.isStringEmpty(addressDO.getContactPerson())) {
							address.setContactPerson(addressDO.getContactPerson());
						}
						if (!StringUtil.isStringEmpty(addressDO.getMobile())) {
							address.setMobile(addressDO.getMobile());
						}
						if (!StringUtil.isStringEmpty(addressDO.getEmail())) {
							address.setEmail(addressDO.getEmail());
						}
						if (!StringUtil.isStringEmpty(addressDO.getDesignation())) {
							address.setDesignation(addressDO.getDesignation());
						}
						/* Pincode */
						if (!StringUtil.isNull(addressDO.getPincodeDO())) {
							PincodeTO pincode = new PincodeTO();
							pincode.setPincodeId(addressDO.getPincodeDO()
									.getPincodeId());
							pincode.setPincode(addressDO.getPincodeDO().getPincode());
							address.setPincode(pincode);
							/*if(!StringUtil.isStringEmpty(addressDO.getPincodeDO()
								.getPincode())) {
							List<OfficeTO> pickupDlvBranchList = rateCommonService
									.getPickupBranchsByPincode(addressDO
											.getPincodeDO().getPincode());
							if(!StringUtil.isEmptyColletion(pickupDlvBranchList)) {
								to.setPickupDlvBranchList(pickupDlvBranchList);
							}
						}*/

						}
						pickupDlvLoc.setPickupDlvAddress(address);
					}
					to.setPickupDeliveryLocationTO(pickupDlvLoc);
				}

				toList.add(to);
			}
		}// END OF FOR
		LOGGER.trace("RateContractServiceImpl::conPayBillLocDomainConverter()::END");
		return toList;
	}

	@Override
	public String renewContract(Integer contractId, Integer quotationId, Integer userId, String officeCode)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::renewContract()::START");
		List<String> seqNOs = generateSequenceNo(
				Integer.parseInt(RateContractConstants.ONE),
				RateContractConstants.CONTRACT_NO);
		String contractNo = getContractNo(officeCode, seqNOs);
		RateContractDO rateContractDO = null;
		RateContractTO rateContractTO =  new RateContractTO();
		rateContractTO.setRateContractId(contractId);
		//	rateContractDO = rateContractDAO.renewContract(contractId, quotationId, contractNo, userId);
		rateContractDO = rateContractDAO.searchRateContractBillingDtls(rateContractTO);

		rateContractDO = createRenewContract(rateContractDO,contractNo, userId);

		if(!StringUtil.isNull(rateContractDO)){
			rateContractDO = rateContractDAO.renewContract(contractId, quotationId, contractNo, userId, rateContractDO);
		}

		LOGGER.trace("RateContractServiceImpl::renewContract()::END");
		return rateContractDO.getRateContractNo();
	}

	public List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateContractServiceImpl::generateSequenceNo()::START");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(process);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSeq);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = rateCommonService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("RateContractServiceImpl::generateSequenceNo()::END");
		return sequenceNumber;
	}

	private String getContractNo(String officeCode,
			List<String> seqNOs) {
		LOGGER.trace("RateContractServiceImpl::getContractNo()::START");
		String contractNo = RateContractConstants.CHAR_C
				+ officeCode + seqNOs.get(0);
		LOGGER.trace("RateContractServiceImpl::getContractNo()::END");
		return contractNo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationListViewTO> rateContractListViewSEDetails(
			Integer userId, String fromDate, String toDate, String contractNo,
			String status) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::rateContractListViewSEDetails()::START");
		List<RateQuotationWrapperDO> rqwDOList = null;
		List<RateQuotationListViewTO> rqwlvTOList = null;
		rqwDOList = rateContractDAO.rateContractListViewSEDetails(userId, fromDate, toDate, contractNo, status);

		if(!CGCollectionUtils.isEmpty(rqwDOList)){
			rqwlvTOList = new ArrayList<RateQuotationListViewTO>();

			rqwlvTOList = (List<RateQuotationListViewTO>)CGObjectConverter.createTOListFromDomainList(rqwDOList, RateQuotationListViewTO.class);

		}

		LOGGER.trace("RateContractServiceImpl::rateContractListViewSEDetails()::END");


		return rqwlvTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateContractSpocTO> getRateContractSpocDetails(String contactType,
			Integer contractId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::getRateContractSpocDetails()::START");
		List<RateContractSpocDO> rcsDOList = null;
		List<RateContractSpocTO> rcsTOList = null;
		rcsDOList = rateContractDAO.getRateContractSpocDetails(contactType, contractId);

		if(!CGCollectionUtils.isEmpty(rcsDOList)){
			rcsTOList = new ArrayList<RateContractSpocTO>();

			rcsTOList = (List<RateContractSpocTO>)CGObjectConverter.createTOListFromDomainList(rcsDOList, RateContractSpocTO.class);

		}

		LOGGER.trace("RateContractServiceImpl::getRateContractSpocDetails()::END");
		return rcsTOList;
	}
	@Override
	public Boolean saveRateContractSpocDetails(RateContractSpocTO rcsTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateContractServiceImpl::saveRateContractSpocDetails()::START");
		Boolean operation = Boolean.FALSE;
		if (!StringUtil.isNull(rcsTO)) {
			List<RateContractSpocDO> rcsDOList = new ArrayList<RateContractSpocDO>();
			domainConverterSpocDO2SpocTO(rcsTO, rcsDOList);
			if (!CGCollectionUtils.isEmpty(rcsDOList)) {
				operation = rateContractDAO
						.saveOrUpdateRateContractSpocDetails(rcsDOList);
			} else {
				operation = Boolean.TRUE;
			}
		}
		LOGGER.trace("RateContractServiceImpl::saveRateContractSpocDetails()::END");
		return operation;
	}

	private void domainConverterSpocDO2SpocTO(RateContractSpocTO rcsTO,
			List<RateContractSpocDO> rcsDOList) {
		LOGGER.trace("RateContractServiceImpl::domainConverterSpocDO2SpocTO()::START");
		RateContractDO rcDO = new RateContractDO();
		int complaintLen = rcsTO.getComplaintTypeAry().length;
		int i=0;
		if(rcsTO.getContactType().trim().equals("C")){
			i = rcsTO.getRowCountF();
		}else{
			complaintLen = rcsTO.getRowCountF();
		}
		for(; i<complaintLen;i++){
			if(!StringUtil.isStringEmpty(rcsTO.getContactNameAry()[i])
					|| !StringUtil.isStringEmpty(rcsTO.getContactNoAry()[i])
					|| !StringUtil.isStringEmpty(rcsTO.getMobileAry()[i])
					|| !StringUtil.isStringEmpty(rcsTO.getFaxAry()[i])
					|| !StringUtil.isStringEmpty(rcsTO.getEmailAry()[i])){
				RateContractSpocDO rcsDO = new RateContractSpocDO();

				rcDO.setRateContractId(rcsTO.getRateContractId());
				//rcsDO.setRateContractId(rcsTO.getRateContractId());
				rcsDO.setRateContractDO(rcDO);
				if(!StringUtil.isEmptyInteger(rcsTO.getContractSpocIdAry()[i])){
					rcsDO.setContractSpocId(rcsTO.getContractSpocIdAry()[i]);
				}
				rcsDO.setContactType(rcsTO.getContactType());
				rcsDO.setComplaintType(rcsTO.getComplaintTypeAry()[i]);
				if(!StringUtil.isStringEmpty(rcsTO.getContactNameAry()[i])){
					rcsDO.setContactName(rcsTO.getContactNameAry()[i]);
				}
				if(!StringUtil.isStringEmpty(rcsTO.getContactNoAry()[i])){
					rcsDO.setContactNo(rcsTO.getContactNoAry()[i]);
				}
				if(!StringUtil.isStringEmpty(rcsTO.getMobileAry()[i])){
					rcsDO.setMobile(rcsTO.getMobileAry()[i]);
				}
				if(!StringUtil.isStringEmpty(rcsTO.getFaxAry()[i])){
					rcsDO.setFax(rcsTO.getFaxAry()[i]);
				}
				if(!StringUtil.isStringEmpty(rcsTO.getEmailAry()[i])){
					rcsDO.setEmail(rcsTO.getEmailAry()[i]);
				}
				rcsDOList.add(rcsDO);
			}
		}
		LOGGER.trace("RateContractServiceImpl::domainConverterSpocDO2SpocTO()::END");
	}
	private Integer getCustomerType(RateContractDO rcDO) throws CGSystemException, CGBusinessException{
		LOGGER.trace("RateContractServiceImpl::getCustomerType()::START");
		Integer customerTypeId = null;
		if(rcDO.getBillingContractType().equals("R")){
			customerTypeId = rateCommonService.getCustomerTypeId("","","R","");
		}else{
			if(rcDO.getRateContractType().equals("N")){
				customerTypeId = rateCommonService.getCustomerTypeId(
						rcDO.getRateContractType(), rcDO.getRateQuotationDO()
						.getCustomer().getIndustryCategoryDO()
						.getRateIndustryCategoryCode(), "N", rcDO
						.getRateQuotationDO().getCustomer()
						.getBusinessType());
			}else{
				customerTypeId = rateCommonService.getCustomerTypeId(
						rcDO.getRateContractType(), "", "N", "");
			}
		}
		LOGGER.trace("RateContractServiceImpl::getCustomerType()::END");

		return customerTypeId;
	}

	private RateContractDO createRenewContract(RateContractDO rateContractDO, String contractNo, Integer userId) 
			throws CGSystemException, CGBusinessException{
		LOGGER.trace("RateContractServiceImpl::createRenewContract()::START");
		RateQuotationDO rateQuotationDO = null;
		Set<ContractPaymentBillingLocationDO> blDOSet = null;
		Set<ContractPaymentBillingLocationDO> blocDOSet = null;
		Set<RateContractSpocDO> spocDOSet = null;
		Set<RateContractSpocDO> spDOSet = null;
		Integer contractId = null;
		RateQuotationTO qTO = new RateQuotationTO();



		qTO.setRateQuotationNo(rateContractDO.getRateQuotationDO().getRateQuotationNo());
		qTO.setRateQuotationId(rateContractDO.getRateQuotationDO().getRateQuotationId());

		rateQuotationDO = rateContractDO.getRateQuotationDO();

		rateQuotationDO = rateQuotationService.getContractQuotation(qTO);

		rateQuotationDO.getCustomer().setPanNo(rateContractDO.getRateQuotationDO().getCustomer().getPanNo());
		rateQuotationDO.getCustomer().setTanNo(rateContractDO.getRateQuotationDO().getCustomer().getTanNo());
		blDOSet = rateContractDO.getConPayBillLocDO();
		spDOSet = rateContractDO.getRateContractSpocDO();

		contractId = rateContractDO.getRateContractId();
		rateContractDO.setRateContractId(null);
		rateContractDO.setRateQuotationDO(rateQuotationDO);

		RateContractDO rcDO = new RateContractDO();
		rcDO.setRateContractId(contractId);

		rateContractDO.setOriginatedRateContractDO(rcDO);

		//rateQuotationDO.setRateContractDO(rateContractDO);
		//rateContractDO.setRateContractId(null);
		rateContractDO.setValidFromDate(null);
		rateContractDO.setValidToDate(null);
		rateContractDO.setCreatedDate(DateUtil.getCurrentDate());
		rateContractDO.setUpdatedDate(null);
		rateContractDO.setRateContractNo(contractNo);
		rateContractDO.setUserId(userId);
		rateContractDO.setContractStatus(RateContractConstants.CREATED);
		rateContractDO.setCustomerId(rateQuotationDO.getCustomer().getCustomerId());


		if(!CGCollectionUtils.isEmpty(blDOSet)){
			blocDOSet = new HashSet<ContractPaymentBillingLocationDO>(blDOSet.size());

			for(ContractPaymentBillingLocationDO BLDo : blDOSet){

				BLDo.setContractPaymentBillingLocationId(null);

				BLDo.setRateContractDO(rateContractDO);

				PickupDeliveryLocationDO pdlDO = new PickupDeliveryLocationDO();

				pdlDO = BLDo.getPickupDeliveryLocation();

				pdlDO.setPickupDlvLocId(null);

				PickupDeliveryContractDO pdcDO =  new PickupDeliveryContractDO();

				pdcDO = pdlDO.getPickupDlvContract();

				OfficeTO office = new OfficeTO();
				office.setOfficeCode(pdcDO.getOffice().getOfficeCode());

				pdcDO.setContractId(null);

				CustomerDO cDO = new CustomerDO();

				cDO = pdcDO.getCustomer();

				//cDO.setCustomerId(null);

				pdcDO.setCustomer(cDO);

				pdlDO.setPickupDlvContract(pdcDO);

				AddressDO aDO = new AddressDO();

				aDO = pdlDO.getAddress();

				//aDO.setAddressId(null);

				pdlDO.setAddress(aDO);

				pdlDO.setPickupDlvLocCode( getLocationCode(office.getOfficeCode()));
				BLDo.setPickupDeliveryLocation(pdlDO);


				blocDOSet.add(BLDo);
			}	 
		}else{
			blocDOSet = new HashSet<ContractPaymentBillingLocationDO>();
		}
		rateContractDO.setConPayBillLocDO(blocDOSet);
		if(!CGCollectionUtils.isEmpty(spDOSet)){
			spocDOSet = new HashSet<RateContractSpocDO>(spDOSet.size());

			for(RateContractSpocDO spDO: spDOSet){
				spDO.setContractSpocId(null);
				spDO.setRateContractDO(rateContractDO);
				spocDOSet.add(spDO);
			}

		}else{
			spocDOSet = new HashSet<RateContractSpocDO>();
		}
		rateContractDO.setRateContractSpocDO(spocDOSet);
		LOGGER.trace("RateContractServiceImpl::createRenewContract()::END");

		return rateContractDO;
	}	

	private void sendContractCreatedEmail(RateContractDO rcDO, String fromMailId) throws CGBusinessException, CGSystemException{

		LOGGER.trace("RateContractServiceImpl::sendContractCreatedEmail()::START");
		String[] toMail = null;
		String[] ccMail = null;
		EmployeeDO salesEmpDO = null;
		List<EmployeeDO> empDOList = null;
		String mailId = null;
		String templateName = null;
		OfficeDO ofcDO = null;

		MailSenderTO emailTO = new MailSenderTO();

		ofcDO = rcDO.getRateQuotationDO().getCustomer().getSalesOfficeDO();
		empDOList = rateContractDAO.getEmployeesforContractAlert(ofcDO);

		if(!CGCollectionUtils.isEmpty(empDOList)){
			ccMail = new String[empDOList.size()];
			int i=0;
			for(EmployeeDO empDO : empDOList){
				ccMail[i] = empDO.getEmailId();
				i++;
			}
		}

		if(!StringUtil.isNull(fromMailId)){
			emailTO.setFrom(fromMailId);
		}else{
			emailTO.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
		}
		salesEmpDO = rcDO.getRateQuotationDO().getCustomer().getSalesPersonDO();
		mailId = salesEmpDO.getEmailId();
		toMail = new String[]{mailId};
		emailTO.setTo(toMail);
		emailTO.setCc(ccMail);
		emailTO.setMailSubject(RateCommonConstants.CONTRACT_CREATED_INTIMATION);

		Map<Object, Object> mailTemplate = new HashMap<Object, Object>();
		mailTemplate.put(RateCommonConstants.RATE_CONTRACT_NUMBER, rcDO.getRateContractNo());
		mailTemplate.put(RateCommonConstants.SALES_PERSON_NAME, salesEmpDO.getFirstName()+CommonConstants.SPACE+salesEmpDO.getLastName());
		mailTemplate.put(RateCommonConstants.CUSTOMER_NAME, rcDO.getRateQuotationDO().getCustomer().getBusinessName());
		mailTemplate.put(RateCommonConstants.EFFECTIVE_FROM, rcDO.getValidFromDate());
		mailTemplate.put(RateCommonConstants.EFFECTIVE_TO, rcDO.getValidToDate());
		mailTemplate.put(RateCommonConstants.CREATED_DATE, DateUtil.getCurrentDate());

		templateName = RateCommonConstants.CONTRACT_CREATED_ALERT_EMAIL_VM;
		emailTO.setTemplateName(templateName);
		emailTO.setTemplateVariables(mailTemplate);

		emailSenderUtil.sendEmail(emailTO);
		LOGGER.trace("RateContractServiceImpl::sendContractCreatedEmail()::END");
	}

	private void setRatesFlags(RateContractTO contractTO, RateContractDO contractDO){
		if(!CGCollectionUtils.isEmpty(contractDO.getRateQuotationDO().getRateQuotationProductCategoryHeaderDO())){
			Set<RateQuotationProductCategoryHeaderDO> rqpchDOSet = contractDO.getRateQuotationDO().getRateQuotationProductCategoryHeaderDO();
			for(RateQuotationProductCategoryHeaderDO rqpchDO : rqpchDOSet){
				if(rqpchDO.getRateProductCategory().getRateProductCategoryCode().equals("CO")){
					contractTO.getRateQuotationTO().setProposedRatesCO("Y");
					Set<RateQuotationWeightSlabDO> rqwsDOSet = rqpchDO.getRateQuotationWeightSlabDO();
					for(RateQuotationWeightSlabDO rqwsDO : rqwsDOSet){
						if(!StringUtil.isStringEmpty(rqwsDO.getRateConfiguredType())){
							if(rqwsDO.getRateConfiguredType().equals("D")){
								contractTO.getRateQuotationTO().setProposedRatesD("Y");
							}else if(rqwsDO.getRateConfiguredType().equals("P")){
								contractTO.getRateQuotationTO().setProposedRatesP("Y");
							}else if(rqwsDO.getRateConfiguredType().equals("B")){
								contractTO.getRateQuotationTO().setProposedRatesB("Y");
							}
						}
					}
				}
			}
		}
	}
}
