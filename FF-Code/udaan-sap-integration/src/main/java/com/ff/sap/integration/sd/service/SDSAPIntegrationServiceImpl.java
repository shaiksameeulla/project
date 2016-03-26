/**
 * 
 */
package com.ff.sap.integration.sd.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ConsignmentBookingBillingMappingDO;
import com.ff.domain.billing.SAPBillingConsignmentSummaryDO;
import com.ff.domain.billing.SalesOrderInterfaceDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.geography.ZoneDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.SAPContractDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;
import com.ff.sap.integration.to.SAPContractTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.universe.business.dao.BusinessCommonDAO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.mec.dao.MECUniversalDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;
import com.ff.universe.ratemanagement.dao.RateUniversalDAO;
import com.firstflight.sd.csdtosap.contract.DTCSDContract;
import com.google.common.collect.Lists;

/**
 * @author cbhure
 *
 */
public class SDSAPIntegrationServiceImpl implements SDSAPIntegrationService{
	
	public SAPIntegrationDAO integrationDAO;
	public BusinessCommonDAO businessCommonDAO;
	public OrganizationCommonDAO organizationCommonDAO; 
	public GeographyServiceDAO geographyServiceDAO;
	public RateUniversalDAO rateUniversalDAO;
	private transient BillingParallelRateCalc billingParallelRateCalc;
	private transient RateCalculationUniversalService rateCalculationUniversalService;
	public MECUniversalDAO mecUniversalDAO;
    
	
	
	/**
	 * @param mecUniversalDAO the mecUniversalDAO to set
	 */
	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}

	/**
	 * @param rateCalculationUniversalService the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}


	Logger logger = Logger.getLogger(SDSAPIntegrationServiceImpl.class);
	
	@Override
	public List<SAPContractDO> findContractDtlsForSAPIntegration(SAPContractTO sapContractTO) throws CGSystemException, CGBusinessException {
		logger.debug("CONTRACT :: SDSAPIntegrationServiceImpl :: findContractDtlsForSAPIntegration :: Start");
		List<SAPContractDO> sapContractDOsList = null;
		List<CSDSAPCustomerDO> customerNewDOs = null;
		List<SAPContractDO> sapContractDOList = null;
		List<CSDSAPCustomerDO> custDOList = null;
		CSDSAPCustomerDO custNewDO = null;
		ConfigurableParamsDO configParamDO = null;
		Long maxDataCountLimit = null;
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try
		{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapContractTO.getMaxCheck());
			if (!StringUtil.isNull(configParamDO)) {
				String maxDataCount = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			// Find out toat record count from Transaction table = 150
			totalRecords = integrationDAO.getContractDtlsForSAP(sapContractTO.getSapStatus());
						
			if (!StringUtil.isEmptyLong(totalRecords)) {
				// Add for loop -
				for (initialCount = 1l; initialCount <= totalRecords; initialCount = initialCount + maxDataCountLimit) {
		
				//1 Step - Fetching data from CSD Table
				customerNewDOs = integrationDAO.findContractDtlsForSAPIntegration(sapContractTO, maxDataCountLimit);
					
				
				if(!StringUtil.isNull(customerNewDOs) && !StringUtil.isEmptyColletion(customerNewDOs)){ 
					//2 Step - Save CSD Table Data to Interface Staging Table
					//sapContractDOList = new ArrayList<SAPContractDO>();
					sapContractDOList = convertContractCSDDOToStagingDO(customerNewDOs);
					isSaved = integrationDAO.saveContractStagingData(sapContractDOList);
					
					
					//isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfContract(customerNewDOs);
					//3 Step - Fetching data from Staging Table
					
					//if(isUpdated){
						//sapContractDOsList = new ArrayList<>();
						sapContractDOsList = integrationDAO.findContractDtlsFromStaging(sapContractTO, maxDataCountLimit);
					//}
				}
			}
		}
		else{
			//sapContractDOsList = new ArrayList<>();
			sapContractDOsList = integrationDAO.findContractDtlsFromStaging(sapContractTO, maxDataCountLimit);
			}
		}catch (Exception e) {
			logger.error("CONTRACT :: Exception IN :: SDSAPIntegrationServiceImpl :: findContractDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("CONTRACT :: SDSAPIntegrationServiceImpl :: findContractDtlsForSAPIntegration :: end");
		return sapContractDOsList;
	}

	private List<SAPContractDO> convertContractCSDDOToStagingDO (List<CSDSAPCustomerDO> custNewDOs) throws CGSystemException, CGBusinessException{
		logger.debug("CONTRACT :: SDSAPIntegrationServiceImpl :: convertContractCSDDOToStagingDO :: Start");
		List<SAPContractDO> sapContractDOList = new ArrayList<>();
		SAPContractDO sapContractDO = null;
		StateDO stateDo = null;
		CityDO cityDO = null;
		CityTO cityTO = null;
		List<PickupDeliveryContractWrapperDO> pickupContractDOs = null;
		ContractPaymentBillingLocationDO contractPayBillLocDO = null;
		OfficeDO ofcDO = null;
		
		for(CSDSAPCustomerDO custNewDO : custNewDOs){
			
			sapContractDO = new SAPContractDO();
			
			if(!StringUtil.isNull(custNewDO.getAddress())){
				
				if(!StringUtil.isStringEmpty(custNewDO.getAddress().getAddress1())){
					sapContractDO.setAddress1(custNewDO.getAddress().getAddress1());
				}
			
				if(!StringUtil.isStringEmpty(custNewDO.getAddress().getAddress2())){
					sapContractDO.setAddress2(custNewDO.getAddress().getAddress2());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getAddress().getAddress3())){
					sapContractDO.setAddress3(custNewDO.getAddress().getAddress3());
				}
				
				if(!StringUtil.isNull(custNewDO.getAddress().getCityDO())
						&& !StringUtil.isStringEmpty(custNewDO.getAddress().getCityDO().getCityName())){
					sapContractDO.setCity(custNewDO.getAddress().getCityDO().getCityName());
					
					cityTO = new CityTO();
					cityTO.setCityId(custNewDO.getAddress().getCityDO().getCityId());
					cityDO = geographyServiceDAO.getCity(cityTO);
					
					
					if(!StringUtil.isNull(cityDO)){
						Integer  cityId = cityDO.getState();
						stateDo = geographyServiceDAO.getState(cityId);
						if(!StringUtil.isNull(stateDo)
								&& !StringUtil.isStringEmpty(stateDo.getStateCode())){
							sapContractDO.setState(stateDo.getStateCode());
						}
					}
				}
				
				if(!StringUtil.isNull(custNewDO.getAddress().getPincodeDO())
						&& !StringUtil.isStringEmpty(custNewDO.getAddress().getPincodeDO().getPincode())){
					sapContractDO.setPincode(custNewDO.getAddress().getPincodeDO().getPincode());
				}
				
				/*if(!StringUtil.isNull(custNewDO.getAddress().getStateDO())
						&& !StringUtil.isStringEmpty(custNewDO.getAddress().getStateDO().getStateName())){
					sapContractDO.setState(custNewDO.getAddress().getStateDO().getStateName());
				}
				logger.debug("State --------------> "+sapContractDO.getState());*/
			}
			
			if(!StringUtil.isNull(custNewDO.getPrimaryContact())){
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getContactNo())){
					sapContractDO.setPriContactNo(custNewDO.getPrimaryContact().getContactNo());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getEmail())){
					sapContractDO.setPriEmail(custNewDO.getPrimaryContact().getEmail());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getExtension())){
					sapContractDO.setPriExt(custNewDO.getPrimaryContact().getExtension());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getFax())){
					sapContractDO.setPriFax(custNewDO.getPrimaryContact().getFax());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getMobile())){
					sapContractDO.setPriMobile(custNewDO.getPrimaryContact().getMobile());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getName())){
					sapContractDO.setPriPersonName(custNewDO.getPrimaryContact().getName());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getPrimaryContact().getTitle())){
					sapContractDO.setPriTitle(custNewDO.getPrimaryContact().getTitle());
				}
			}
			
			if(!StringUtil.isNull(custNewDO.getSecondaryContact())){
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getContactNo())){
					sapContractDO.setSecContactNo(custNewDO.getSecondaryContact().getContactNo());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getEmail())){
					sapContractDO.setSecEmail(custNewDO.getSecondaryContact().getEmail());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getExtension())){
					sapContractDO.setSecExt(custNewDO.getSecondaryContact().getExtension());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getFax())){
					sapContractDO.setSecFax(custNewDO.getSecondaryContact().getFax());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getMobile())){
					sapContractDO.setSecMobile(custNewDO.getSecondaryContact().getMobile());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getName())){
					sapContractDO.setSecPersonName(custNewDO.getSecondaryContact().getName());
				}
				
				if(!StringUtil.isStringEmpty(custNewDO.getSecondaryContact().getTitle())){
					sapContractDO.setSecTitle(custNewDO.getSecondaryContact().getTitle());
				}
			}
				
			if(!StringUtil.isStringEmpty(custNewDO.getBillingCycle())){
				sapContractDO.setBillingCycle(custNewDO.getBillingCycle());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getPaymentTerm())){
				sapContractDO.setPaymentTermsCode(custNewDO.getPaymentTerm());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getContractNo())){
				sapContractDO.setContractNo(custNewDO.getContractNo());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getCustomerCode())){
				sapContractDO.setCustomerNo(custNewDO.getCustomerCode());
			}
			
			/*if(!StringUtil.isNull(custNewDO.getCustomerCategory())
					&& !StringUtil.isStringEmpty(custNewDO.getCustomerCategory().getRateCustomerCategoryCode())){
				sapContractDO.setCustomerGroup(custNewDO.getCustomerCategory().getRateCustomerCategoryCode());
			}
			logger.debug("Customer Group AS (Rate Custo Category)--------------> "+sapContractDO.getCustomerGroup());*/
			
			if(!StringUtil.isStringEmpty(custNewDO.getBusinessName())){
				sapContractDO.setCustomerName(custNewDO.getBusinessName());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getDistributionChannels())){
				sapContractDO.setDisChannel(custNewDO.getDistributionChannels());
			}
			
			if(!StringUtil.isNull(custNewDO.getGroupKey())
					&& !StringUtil.isStringEmpty(custNewDO.getGroupKey().getCustomerGroupCode())){
				sapContractDO.setGroupKey(custNewDO.getGroupKey().getCustomerGroupCode());
			}
			
			if(!StringUtil.isNull(custNewDO.getIndustryType())
					&& !StringUtil.isStringEmpty(custNewDO.getIndustryType().getRateIndustryTypeCode())){
				sapContractDO.setIndustryTypeCode(custNewDO.getIndustryType().getRateIndustryTypeCode());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getPanNo())){
				sapContractDO.setPanNo(custNewDO.getPanNo());
			}
			
			//New Office Mapped to Logic - 
			/*
			 * select	o.OFFICE_ID,o.OFFICE_CODE
				FROM	ff_d_office o,
						ff_d_contract_payment_billing_location cpbl,
						ff_d_pickup_delivery_contract pdc
				WHERE	pdc.CUSTOMER_ID=2594
				AND		o.OFFICE_ID = pdc.OFFICE_ID
				AND		pdc.CONTRACT_ID IN (	SELECT	cpbl.PICKUP_DELIVERY_LOCATION 
												FROM	ff_d_contract_payment_billing_location cpbl
												WHERE	cpbl.PICKUP_DELIVERY_LOCATION = 2529
												AND	cpbl.LOCATION_OPERATION_TYPE = 'BP'
									
									)
			 */
			Integer customerId = custNewDO.getCustomerId();
			pickupContractDOs = rateUniversalDAO.getPickUpDeliveryContrat(customerId);
			if(!StringUtil.isEmptyColletion(pickupContractDOs)){
				for( PickupDeliveryContractWrapperDO pickUpcontractWrapperDO: pickupContractDOs){
					if(!StringUtil.isNull(pickUpcontractWrapperDO) 
							&& !StringUtil.isEmptyInteger(pickUpcontractWrapperDO.getContractId())){
						
						// To get pickup location details by its contract id.
						Integer contractId = pickUpcontractWrapperDO.getContractId();
						PickupDeliveryLocationDO pickupLocDO = rateUniversalDAO.getPickupDlvLocationByContractId(contractId);
						
						if(!StringUtil.isNull(pickupLocDO)) {
							
							// To get contract payment billing location details by pickup location id
							Integer pickupDlvLocId = pickupLocDO.getPickupDlvLocId();
							contractPayBillLocDO = rateUniversalDAO.getContractPayBillingLocationDtlsBypickupLocation(pickupDlvLocId);
							if(!StringUtil.isNull(contractPayBillLocDO)
									&& !StringUtil.isStringEmpty(contractPayBillLocDO.getLocationOperationType())
									&& contractPayBillLocDO.getLocationOperationType().equals("BP")){
									
								Integer officeId = pickUpcontractWrapperDO.getOfficeId();
								String offCode = null;
								ofcDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
								if(!StringUtil.isNull(ofcDO)){
									sapContractDO.setPlantCode(ofcDO.getOfficeCode());
								}
							}
						}
					}
				}
			}
			
			if(!StringUtil.isNull(custNewDO.getSalesOffice())
					&& !StringUtil.isStringEmpty(custNewDO.getSalesOffice().getOfficeCode())){
				sapContractDO.setSalesOfcCode(custNewDO.getSalesOffice().getOfficeCode());
				
				if(!StringUtil.isEmptyInteger(custNewDO.getSalesOffice().getCityId())){
					Integer cityId = custNewDO.getSalesOffice().getCityId(); 
					ZoneDO zoneDO = geographyServiceDAO.getZoneCodeByCityId(cityId);
					if(!StringUtil.isNull(zoneDO)
							&& !StringUtil.isStringEmpty(zoneDO.getZoneCode())){
						sapContractDO.setSalesDistrict(zoneDO.getZoneCode());
					}
				}
			}
			
			
			if(!StringUtil.isNull(custNewDO.getSalesPerson())
					&& (!StringUtil.isStringEmpty(custNewDO.getSalesPerson().getEmpCode()))){
				sapContractDO.setSalesPersonCode(custNewDO.getSalesPerson().getEmpCode());
			}
			
			if(!StringUtil.isNull(custNewDO.getCustomerType())
					&& (!StringUtil.isStringEmpty(custNewDO.getCustomerType().getCustomerTypeCode()))){
				sapContractDO.setCustomerGroup(custNewDO.getCustomerType().getCustomerTypeCode());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getTanNo())){
				sapContractDO.setTanNo(custNewDO.getTanNo());
			}
			
			if(!StringUtil.isStringEmpty(custNewDO.getLegacyCustCode())){
				sapContractDO.setLegacyCustCode(custNewDO.getLegacyCustCode());
			}
			sapContractDOList.add(sapContractDO);
		}
		logger.debug("CONTRACT :: SDSAPIntegrationServiceImpl :: convertContractCSDDOToStagingDO :: End");
		return sapContractDOList;
	}
	
	private List<DTCSDContract.Contract> prepareContractTOFromDO(List<SAPContractDO> sapContractDOList) { 
		
		DTCSDContract contract = new DTCSDContract();
		List<DTCSDContract.Contract> contractList = null;
		DTCSDContract.Contract cntrct  = null;
		
		contractList =  contract.getContract();
		contractList = new ArrayList<>(); 
		
		for(SAPContractDO sapContractDO : sapContractDOList){
			cntrct = new DTCSDContract.Contract();
			
			if(!StringUtil.isNull(sapContractDO)){ 
				
				if(!StringUtil.isStringEmpty(sapContractDO.getAddress1())){
					cntrct.setAddress1(sapContractDO.getAddress1());
				}
				logger.debug("Add 1 --------------> "+cntrct.getAddress1());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getAddress2())){
					cntrct.setAddress2(sapContractDO.getAddress2());
				}
				logger.debug("Add 2 --------------> "+cntrct.getAddress2());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getAddress3())){
					cntrct.setAddress3(sapContractDO.getAddress3());
				}
				logger.debug("Add 3 --------------> "+cntrct.getAddress3());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getBillingCycle())){
					cntrct.setBillingCycle(sapContractDO.getBillingCycle());
				}
				logger.debug("Billing Cycle --------------> "+cntrct.getBillingCycle());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getCity())){
					cntrct.setCity(sapContractDO.getCity());
				}
				logger.debug("City --------------> "+cntrct.getCity());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getContractNo())){
					cntrct.setContractNo(sapContractDO.getContractNo());
				}
				logger.debug("Contract No --------------> "+cntrct.getContractNo());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getContractNo())){
					cntrct.setContractNo(sapContractDO.getContractNo());
				}
				logger.debug("Contract No --------------> "+cntrct.getContractNo());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getCustomerGroup())){
					cntrct.setCustomerGroup(sapContractDO.getCustomerGroup());
				}
				logger.debug("Customer Group --------------> "+cntrct.getCustomerGroup());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getCustomerName())){
					cntrct.setCustName(sapContractDO.getCustomerName());
				}
				logger.debug("Customer Name --------------> "+cntrct.getCustName());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getCustomerNo())){
					cntrct.setCustomerNo(sapContractDO.getCustomerNo());
				}
				logger.debug("Customer No --------------> "+cntrct.getCustomerNo());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getDisChannel())){
					cntrct.setDistChannel(sapContractDO.getDisChannel());
				}
				logger.debug("Dis Channel --------------> "+cntrct.getDistChannel());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getGroupKey())){
					cntrct.setGroupKey(sapContractDO.getGroupKey());
				}
				logger.debug("Group Key --------------> "+cntrct.getGroupKey());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getIndustryTypeCode())){
					cntrct.setIndustryTypeCode(sapContractDO.getIndustryTypeCode());
				}
				logger.debug("Industry Type Code --------------> "+cntrct.getIndustryTypeCode());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPanNo())){
					cntrct.setPANNo(sapContractDO.getPanNo());
				}
				logger.debug("PAN NO --------------> "+cntrct.getPANNo());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPaymentTermsCode())){
					cntrct.setPaymentTerms(sapContractDO.getPaymentTermsCode());
				}
				logger.debug("Payment Terms --------------> "+cntrct.getPaymentTerms());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPincode())){
					cntrct.setPincode(sapContractDO.getPincode());
				}
				logger.debug("Pincode --------------> "+cntrct.getPincode());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPlantCode())){
					cntrct.setPlantCode(sapContractDO.getPlantCode());
				}
				logger.debug("Plant Code --------------> "+cntrct.getPlantCode());
				
				DTCSDContract.Contract.PrimaryContact primaryContact = new DTCSDContract.Contract.PrimaryContact();
				if(!StringUtil.isStringEmpty(sapContractDO.getPriContactNo())){
					primaryContact.setContactNo(sapContractDO.getPriContactNo());
				}
				logger.debug("Pri Contact No --------------> "+primaryContact.getContactNo());
				
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriEmail())){
					primaryContact.setEmail(sapContractDO.getPriEmail());
				}
				logger.debug("Email --------------> "+primaryContact.getEmail());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriExt())){
					primaryContact.setExtention(sapContractDO.getPriExt());
				}
				logger.debug("Pri Extension --------------> "+primaryContact.getExtention());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriFax())){
					primaryContact.setFax(sapContractDO.getPriFax());
				}
				logger.debug("Pri Fax --------------> "+primaryContact.getFax());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriMobile())){
					primaryContact.setMobile(sapContractDO.getPriMobile());
				}
				logger.debug("Pri Mobile --------------> "+primaryContact.getMobile());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriPersonName())){
					primaryContact.setPersonName(sapContractDO.getPriPersonName());
				}
				logger.debug("Pri Person Name --------------> "+primaryContact.getPersonName());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getPriTitle())){
					primaryContact.setTitle(sapContractDO.getPriTitle());
				}
				logger.debug("Pri Title --------------> "+primaryContact.getTitle());
				cntrct.setPrimaryContact(primaryContact);
				
				DTCSDContract.Contract.SecondaryContact secondaryContact = new DTCSDContract.Contract.SecondaryContact();
				if(!StringUtil.isStringEmpty(sapContractDO.getSecContactNo())){
					secondaryContact.setContactNo(sapContractDO.getSecContactNo());
				}
				logger.debug("Sec Contact No --------------> "+secondaryContact.getContactNo());
				
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecEmail())){
					secondaryContact.setEmail(sapContractDO.getSecEmail());
				}
				logger.debug("Sec Email --------------> "+secondaryContact.getEmail());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecExt())){
					secondaryContact.setExtention(sapContractDO.getSecExt());
				}
				logger.debug("Sec Extension --------------> "+secondaryContact.getExtention());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecFax())){
					secondaryContact.setFax(sapContractDO.getSecFax());
				}
				logger.debug("Sec Fax --------------> "+secondaryContact.getFax());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecMobile())){
					secondaryContact.setMobile(sapContractDO.getSecMobile());
				}
				logger.debug("Sec Mobile --------------> "+secondaryContact.getMobile());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecPersonName())){
					secondaryContact.setPersonName(sapContractDO.getSecPersonName());
				}
				logger.debug("Sec Person Name --------------> "+secondaryContact.getPersonName());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSecTitle())){
					secondaryContact.setTitle(sapContractDO.getSecTitle());
				}
				logger.debug("Sec Title --------------> "+secondaryContact.getTitle());
				cntrct.setSecondaryContact(secondaryContact);
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSalesOfcCode())){
					cntrct.setSalesOfficeCode(sapContractDO.getSalesOfcCode());
				}
				logger.debug("Sales Ofc Code --------------> "+cntrct.getSalesOfficeCode());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getSalesPersonCode())){
					cntrct.setSalesPersonCode(sapContractDO.getSalesPersonCode());
				}
				logger.debug("Sales pesrcon Code --------------> "+cntrct.getSalesPersonCode());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getState())){
					cntrct.setState(sapContractDO.getState());
				}
				logger.debug("State --------------> "+cntrct.getState());
				
				if(!StringUtil.isStringEmpty(sapContractDO.getTanNo())){
					cntrct.setTANNo(sapContractDO.getTanNo());
				}
				logger.debug("TAN NO --------------> "+cntrct.getTANNo());
					
				contractList.add(cntrct);
			}
		}
		return contractList;
	}
	
	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	@Override
	public void updateContractStagingStatusFlag(String sapStatus,List<SAPContractDO> sapContractDOs,String exception) throws CGSystemException {
		List<SAPContractDO> sapContractDOList = new ArrayList<>();
		SAPContractDO sapCntrDO = null;
		for(SAPContractDO sapContractDO : sapContractDOs){
			sapCntrDO = new SAPContractDO();
			sapCntrDO.setId(sapContractDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapCntrDO.setSapTimestamp(dateTime);
			sapCntrDO.setSapStatus(sapStatus);
			sapCntrDO.setException(exception);
			sapContractDOList.add(sapCntrDO);
		}
		integrationDAO.updateContractStagingStatusFlag(sapContractDOList);
		
	}

	@Override
	/*public List<SAPBillingConsignmentSummaryDO> findBillingConsgSummaryForSAPIntegration(SAPBillingConsgSummaryTO sapBillConsgSummaryTO)throws CGSystemException {

		
		List<BillingConsignmentSummaryDO> billConsignmentSummaryDOs = new ArrayList<BillingConsignmentSummaryDO>();
		
		//1 Step - Fecthing data from CSD Table
		
		String queryName = SAPIntegrationConstants.QRY_PARAM_BILL_SUMMARY_CONSG_DETAILS_FOR_SAP;
		String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
		Object paramValues[]= {sapBillConsgSummaryTO.getSapStatus()};
		billConsignmentSummaryDOs = integrationDAO.getBillingConsgSummaryDtls(queryName, paramNames, paramValues);
		
		//2 A Step - Save CSD Table Data to Interface Staging Table
		List<SAPBillingConsignmentSummaryDO> sapBillConsignmentSummaryDOs = new ArrayList<SAPBillingConsignmentSummaryDO>();
		boolean isSaved = false;
		if(!StringUtil.isNull(billConsignmentSummaryDOs)){ 
			sapBillConsignmentSummaryDOs = convertBillingConsgSummaryCSDDOToStagingDO(billConsignmentSummaryDOs); 
			isSaved = integrationDAO.saveBillingConsgSummaryStagingData(sapBillConsignmentSummaryDOs);
		}
		//2 B
		//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
		//if flag = true status = C and Time stamp = current time
		//if flag = false status = N and time Stamp = current Time
		boolean isUpdated = false;
		BillingConsignmentSummaryDO bcsNewDO = null;
		if(isSaved){
			List<BillingConsignmentSummaryDO> bcsList = new ArrayList<>();
			for(BillingConsignmentSummaryDO bcsDO : billConsignmentSummaryDOs){
				bcsNewDO = new BillingConsignmentSummaryDO();
				bcsNewDO.setBillingConsignmentSummaryId(bcsDO.getBillingConsignmentSummaryId());
				logger.debug("bcs ID ------>"+bcsNewDO.getBillingConsignmentSummaryId());
				Date dateTime = Calendar.getInstance().getTime();
				bcsNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+bcsDO.getSapTimestamp());
				bcsNewDO.setSapStatus("C");
				logger.debug("SAP Status ------>"+bcsDO.getSapStatus());
				bcsList.add(bcsNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagForBCS(bcsList);
		}else{
			List<BillingConsignmentSummaryDO> bcsList = new ArrayList<>();
			for(BillingConsignmentSummaryDO bcsDO : billConsignmentSummaryDOs){
				bcsNewDO = new BillingConsignmentSummaryDO();
				bcsNewDO.setBillingConsignmentSummaryId(bcsDO.getBillingConsignmentSummaryId());
				logger.debug("bcs ID ------>"+bcsNewDO.getBillingConsignmentSummaryId());
				Date dateTime = Calendar.getInstance().getTime();
				bcsNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+bcsDO.getSapTimestamp());
				bcsNewDO.setSapStatus("C");
				logger.debug("SAP Status ------>"+bcsDO.getSapStatus());
				bcsList.add(bcsNewDO);
			}
			isUpdated = integrationDAO.updateDateTimeAndStatusFlagForBCS(bcsList);
		}
		
		//3 Step - Fecthing data from Staging Table
		List<SAPBillingConsignmentSummaryDO> sapStkReqDOList = new ArrayList<SAPBillingConsignmentSummaryDO>();
		if(isUpdated){
				String stagingQueryName = SAPIntegrationConstants.QRY_PARAM_GET_BCS_FROM_STAGING;
				String stagingParamNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
				Object stagingParamValues[]= {sapBillConsgSummaryTO.getSapStatus()};
				sapStkReqDOList = integrationDAO.findBCSDtlsFromStaging(stagingQueryName, stagingParamNames, stagingParamValues);
		}
		return sapStkReqDOList;
	}*/
	
	public boolean insertBillingSummaryIntoSAPStagingTable (SAPBillingConsgSummaryTO sapBillConsgSummaryTO) throws CGSystemException, ParseException, CGBusinessException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingSummaryCreationScheduler - for Summary scheduler :: SDSAPIntegrationServiceImpl :: insertBillingSummaryIntoSAPStagingTable :: Start");
		List<SAPBillingConsignmentSummaryDO> sapBillConsignmentSummaryDOs = null;
		boolean isSaved = false;
		try{
			 /**Insert Data into Staging table */
			integrationDAO.summary_staging_insertion_Proc();
			
			/** Insert Stock consolidation Data*/
			List<SalesOrderInterfaceDO> salesOrderInterfaceDosList = integrationDAO.getStockConsolidationDtls();
			if (!CGCollectionUtils.isEmpty(salesOrderInterfaceDosList)) {
				sapBillConsignmentSummaryDOs = convertBillingConsgSummaryCSDDOToStagingDO(salesOrderInterfaceDosList);
				isSaved = integrationDAO
						.saveBillingConsgSummaryStagingData(sapBillConsignmentSummaryDOs);
			}
			
			
		} catch (Exception e) {
			logger.error(
					
					e);
			throw new CGBusinessException(e);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SDSAPIntegrationServiceImpl :: insertBillingSummaryIntoSAPStagingTable :: End");
		return isSaved;
	}

	/**
	 * @param maxCheck
	 * @return
	 * @throws CGSystemException
	 */
	public Long getMaxLimitToSendDataToSAP(String maxCheck)
			throws CGSystemException {
		Long maxDataCountLimit = 0L;
		ConfigurableParamsDO configParamDO = null;
		configParamDO = mecUniversalDAO.getMaxDataCount(maxCheck);
		if (!StringUtil.isNull(configParamDO)) {
			String maxDataCount = configParamDO.getParamValue();
			maxDataCountLimit = Long.valueOf(maxDataCount);
		}
		return maxDataCountLimit;
	}

	private List<SAPBillingConsignmentSummaryDO> convertBillingConsgSummaryCSDDOToStagingDO(List<SalesOrderInterfaceDO> salesOrderInterfaceDoList) throws CGSystemException, CGBusinessException {
		
		List<SAPBillingConsignmentSummaryDO> sapBillingConsgSummaryList = new ArrayList<SAPBillingConsignmentSummaryDO>();
		SAPBillingConsignmentSummaryDO sapBillingConsgmntSummary = null;
			
		List<SalesOrderInterfaceDO> saDOList = null;
		saDOList = (List<SalesOrderInterfaceDO>)salesOrderInterfaceDoList;
		
		for(SalesOrderInterfaceDO soDO : saDOList){
			sapBillingConsgmntSummary = new SAPBillingConsignmentSummaryDO();
			
			if(!StringUtil.isNull(soDO.getBookingDate())){
				sapBillingConsgmntSummary.setBookingDate(soDO.getBookingDate());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Sales order Interface -> Booking Date --------->"+sapBillingConsgmntSummary.getBookingDate());
			
			if(!StringUtil.isStringEmpty(soDO.getBookingOffice())){
				sapBillingConsgmntSummary.setBookingOffice(soDO.getBookingOffice());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Sales order Interface -> Booking Ofc --------->"+sapBillingConsgmntSummary.getBookingOffice());
			
			if(!StringUtil.isStringEmpty(soDO.getCustSoldTo())){
				sapBillingConsgmntSummary.setCustSoldTo(soDO.getCustSoldTo());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Sales order Interface -> Cust Sold To --------->"+sapBillingConsgmntSummary.getCustSoldTo());
			
			if(!StringUtil.isStringEmpty(soDO.getCustShipTo())){
				sapBillingConsgmntSummary.setCustShipTo(soDO.getCustShipTo()); 
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Sales order Interface -> Cust Ship To ------->"+sapBillingConsgmntSummary.getCustShipTo());
			
			sapBillingConsgmntSummary.setDistributionChannel("");
			
			if(!StringUtil.isEmptyInteger(soDO.getSummaryId())){
				sapBillingConsgmntSummary.setTransactionNumber(soDO.getSummaryId());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: TX Number --------->"+sapBillingConsgmntSummary.getCustShipTo());
			
			/*if(!StringUtil.isStringEmpty(soDO.getTransactionNumber())){
				sapBillingConsgmntSummary.setTransactionNumber(soDO.getTransactionNumber());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: TX Number --------->"+sapBillingConsgmntSummary.getCustShipTo());*/
			
			if(!StringUtil.isStringEmpty(soDO.getProductCode())){
				sapBillingConsgmntSummary.setProductCode(soDO.getProductCode());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Prod Code --------->"+sapBillingConsgmntSummary.getProductCode());
			
			if(!StringUtil.isEmptyInteger(soDO.getQuantity())){
				sapBillingConsgmntSummary.setQuantity(soDO.getQuantity());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Prod Code --------->"+sapBillingConsgmntSummary.getQuantity());
			
			if(!StringUtil.isNull(soDO.getFreight())){
				sapBillingConsgmntSummary.setFreight(soDO.getFreight());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Freight ------->"+sapBillingConsgmntSummary.getFreight());
			
			if(!StringUtil.isNull(soDO.getFuelSurcharge())){
				sapBillingConsgmntSummary.setFuelSurcharge(soDO.getFuelSurcharge());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Fuel Charge ------->"+sapBillingConsgmntSummary.getFuelSurcharge());
			
			if(!StringUtil.isNull(soDO.getRiskSurcharge())){
				sapBillingConsgmntSummary.setRiskSurcharge(soDO.getRiskSurcharge());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Risk Charge  ------->"+sapBillingConsgmntSummary.getRiskSurcharge());
			
			if(!StringUtil.isNull(soDO.getParcelHandlingCharges())){
				sapBillingConsgmntSummary.setParcelHandlingCharges(soDO.getParcelHandlingCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Parcel ------->"+sapBillingConsgmntSummary.getParcelHandlingCharges());
			
			if(!StringUtil.isNull(soDO.getAirportHandlingCharges())){
				sapBillingConsgmntSummary.setAirportHandlingCharges(soDO.getAirportHandlingCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Air Port ------->"+sapBillingConsgmntSummary.getAirportHandlingCharges());
			
			if(!StringUtil.isNull(soDO.getDocumentHandlingCharges())){
				sapBillingConsgmntSummary.setDocumentHandlingCharges(soDO.getDocumentHandlingCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: DOC Charges ------->"+sapBillingConsgmntSummary.getDocumentHandlingCharges());
			
			if(!StringUtil.isNull(soDO.getCodCharges())){
				sapBillingConsgmntSummary.setCodCharges(soDO.getCodCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: COD ------->"+sapBillingConsgmntSummary.getCodCharges());
			
			if(!StringUtil.isNull(soDO.getToPayCharges())){
				sapBillingConsgmntSummary.setToPayCharges(soDO.getToPayCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: TO Pay ------->"+sapBillingConsgmntSummary.getToPayCharges());
			
			if(!StringUtil.isNull(soDO.getLcCharges())){
				sapBillingConsgmntSummary.setLcCharges(soDO.getLcCharges());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: LC Charges ------->"+sapBillingConsgmntSummary.getLcCharges());
			
			if(!StringUtil.isNull(soDO.getOthers())){
				sapBillingConsgmntSummary.setOthers(soDO.getOthers());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Others ------->"+sapBillingConsgmntSummary.getOthers());
			
			if(!StringUtil.isNull(soDO.getServiceTax())){
				sapBillingConsgmntSummary.setServiceTax(soDO.getServiceTax());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Service Tax ------->"+sapBillingConsgmntSummary.getServiceTax());
			
			if(!StringUtil.isNull(soDO.getEducationCess())){
				sapBillingConsgmntSummary.setEducationCess(soDO.getEducationCess());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: E Cess ------->"+sapBillingConsgmntSummary.getEducationCess());
			
			if(!StringUtil.isNull(soDO.getSecHighEduCess())){
				sapBillingConsgmntSummary.setSecHighEduCess(soDO.getSecHighEduCess());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Sec H E C ------->"+sapBillingConsgmntSummary.getSecHighEduCess());
			
			if(!StringUtil.isNull(soDO.getStateTax())){
				sapBillingConsgmntSummary.setStateTax(soDO.getStateTax());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: State Tax ------->"+sapBillingConsgmntSummary.getStateTax());
			
			if(!StringUtil.isNull(soDO.getSurchargeOnStateTax())){
				sapBillingConsgmntSummary.setSurchargeOnStateTax(soDO.getSurchargeOnStateTax());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Surcharge on State Tax ------->"+sapBillingConsgmntSummary.getSurchargeOnStateTax());
			
			if(!StringUtil.isNull(soDO.getGrandTotal())){
				sapBillingConsgmntSummary.setGrandTotal(soDO.getGrandTotal());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Grand total ------->"+sapBillingConsgmntSummary.getGrandTotal());
			
			if(!StringUtil.isStringEmpty(soDO.getSummaryCategory())){
				sapBillingConsgmntSummary.setSummaryCategory(soDO.getSummaryCategory());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Summary Category ------->"+sapBillingConsgmntSummary.getSummaryCategory());
			
			if(!StringUtil.isStringEmpty(soDO.getDestinationOfc())){
				sapBillingConsgmntSummary.setDestinationOfc(soDO.getDestinationOfc());
			}
			logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: Dest Ofc ------->"+sapBillingConsgmntSummary.getDestinationOfc());
			
			String userName = "SAP_USER";
			UserDO userDO = integrationDAO.getSAPUserDtls(userName);
			if(!StringUtil.isNull(userDO)
					&& !StringUtil.isEmptyInteger(userDO.getUserId())){
				sapBillingConsgmntSummary.setCreatedBy(userDO.getUserId());
				//sapBillingConsgmntSummary.setUpdatedBy(userDO.getUserId());
				Date today = Calendar.getInstance().getTime();
				sapBillingConsgmntSummary.setCreatedDate(today);
				//sapBillingConsgmntSummary.setUpdatedDate(today);
			}
			
			sapBillingConsgSummaryList.add(sapBillingConsgmntSummary);
		}
		
		return sapBillingConsgSummaryList;
	}

	/**
	 * @param businessCommonDAO the businessCommonDAO to set
	 */
	public void setBusinessCommonDAO(BusinessCommonDAO businessCommonDAO) {
		this.businessCommonDAO = businessCommonDAO;
	}

	@Override
	public void updateBCSStagingStatusFlag(String sapStatus,List<SAPBillingConsignmentSummaryDO> sapBillingConsgSummaryDOList,String exception)throws CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SDSAPIntegrationServiceImpl :: updateBCSStagingStatusFlag :: Start");
		List<SAPBillingConsignmentSummaryDO> sapBCSDOList = new ArrayList<>();
		SAPBillingConsignmentSummaryDO sapbcsDO = null;
		for(SAPBillingConsignmentSummaryDO sapBCSDO : sapBillingConsgSummaryDOList){
			sapbcsDO = new SAPBillingConsignmentSummaryDO();
			sapbcsDO.setId(sapBCSDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapbcsDO.setSapTimestamp(dateTime);
			sapbcsDO.setSapStatus(sapStatus);
			sapbcsDO.setException(exception);
			sapBCSDOList.add(sapbcsDO);
		}
		integrationDAO.updateBCSStagingStatusFlag(sapBCSDOList);
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: SDSAPIntegrationServiceImpl :: updateBCSStagingStatusFlag :: End");
	}


	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}
	
	/**
	 * @param rateUniversalDAO the rateUniversalDAO to set
	 */
	public void setRateUniversalDAO(RateUniversalDAO rateUniversalDAO) {
		this.rateUniversalDAO = rateUniversalDAO;
	}

	@Override
	public void updateBillSalesOrderNumber()throws CGSystemException, HttpException, ClassNotFoundException, IOException  {
		logger.debug("BillCreationScheduler - for bill generation scheduler :: SDSAPIntegrationServiceImpl :: updateBillSalesOrderNumber :: Start");
		try{
			String queryName = SAPIntegrationConstants.QRY_PARAM_SP_BILL_SALES_ORDER_DTLS;
			integrationDAO.updateBillSalesOrderNumber(queryName);
		}catch(Exception e){
			logger.error("BillCreationScheduler - for bill generation scheduler :: Exception in :: SDSAPIntegrationServiceImpl :: updateBillSalesOrderNumber",e);
			throw new CGSystemException(e);
		}
		logger.debug("BillCreationScheduler - for bill generation scheduler :: SDSAPIntegrationServiceImpl :: updateBillSalesOrderNumber :: End");
	}

@Override
public void getConsignmentsForRate() throws CGBusinessException,
			CGSystemException, InterruptedException, HttpException,
			ClassNotFoundException, IOException {

		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler :: BillingCommonServiceImpl::getConsignmentsForRate::START----->");
		Integer maxThreadLimit = 5;
		Long limit = integrationDAO
				.getLimitOfRecordProcessedForBilling(SAPIntegrationConstants.BILLING_JOB_MAX_CN);
		Long threadLimit = integrationDAO
				.getLimitOfRecordProcessedForBilling(SAPIntegrationConstants.BILLING_JOB_MAX_THREAD_LIMIT);
		if (!StringUtil.isEmptyLong(threadLimit)) {
			maxThreadLimit = threadLimit.intValue();
		}
		calcRateForCNInConsgRate(limit, maxThreadLimit);
		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::getConsignmentsForRate::END----->");
	}
	
public void calcRateForCNInConsgRate(Long limit, Integer maxThreadLimit)throws CGBusinessException,CGSystemException, InterruptedException{
		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler   :: BillingCommonServiceImpl::calcRateForCNInConsgRate::START----->");
		long startTime1=0L,stopTime1=0L;
		List<BillingParallelRateCalc> cnThreads=new ArrayList<BillingParallelRateCalc>();
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO = null;
		ConsignmentBillingRateDO consignmentBillingRate=null;
		Map<String, ConsignmentBillingRateDO> calcCNRateDOsMap = new HashMap<String, ConsignmentBillingRateDO>();
		String  bookStatus="B";
		/*Fetching CN*/
		List<ConsignmentBilling> dbconsignmentDOs=integrationDAO.getConsignmentForRate(limit);
		List<ConsignmentBilling> consignmentDOs = new ArrayList<ConsignmentBilling>();
		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::SettingBookingDetails::START-----> for :: " + dbconsignmentDOs.size());
		/** Set Booking Details in Result */ 
		
		List<String> consgNos = new ArrayList<String>();
		String consgNo = null;
		ConsignmentBookingBillingMappingDO consgnBookingMappDO = null;
		List<ConsignmentBookingBillingMappingDO>  consgnBookingMappDOs = new ArrayList<ConsignmentBookingBillingMappingDO>();
		
		//Prepare consignment number list
		List<String> consignNos = new ArrayList<String>();
		Map<String, BookingDO> dbBookingDOs = new HashMap<String, BookingDO>();
		for (ConsignmentBilling consignmentBilling : dbconsignmentDOs) {
			consignNos.add(consignmentBilling.getConsgNo());
		}
		//Get All Bookings
		List<BookingDO> bookingDOs = null;
		if (!CGCollectionUtils.isEmpty(consignNos)){
			bookingDOs = integrationDAO.getBookingDtlsByConsgNos(consignNos); 
		} else {
			throw new CGBusinessException("Booking Details Not Availble");
		}
		
		for (BookingDO bookingDO2 : bookingDOs) {
			dbBookingDOs.put(bookingDO2.getConsgNumber(), bookingDO2);
		}
		
		//get all mapping table details for all consignments
		Map<String, ConsignmentBookingBillingMappingDO> consignmentBookingBillingMappingDOsMap = new HashMap<String, ConsignmentBookingBillingMappingDO>();
		consgnBookingMappDOs = integrationDAO.getConsignmentBookingMappingByConsgNos(consignNos);
		for (ConsignmentBookingBillingMappingDO consignmentBookingBillingMappingDO : consgnBookingMappDOs) {
			consignmentBookingBillingMappingDOsMap.put(consignmentBookingBillingMappingDO.getConsgNumber(), consignmentBookingBillingMappingDO);
		}
		
		for (ConsignmentBilling consignmentBilling : dbconsignmentDOs) {
			/** Get Booking Details */
			BookingDO bookingDO = null;
			consgNo = consignmentBilling.getConsgNo();
			if (!StringUtils.isEmpty(consgNo)) {
				bookingDO = dbBookingDOs.get(consgNo);
				if (bookingDO != null
						&& !StringUtil.isNull(bookingDO.getBookingDate())
						&& !StringUtil.isNull(bookingDO.getBookingType())
						&& !StringUtil.isNull(bookingDO.getBookingOfficeId())) {
					/** Set Booking Date */
					consignmentBilling.setBOOKING_DATE(bookingDO.getBookingDate());
					/** Set Booking Office Id */
					consignmentBilling.setOFFICE_ID(bookingDO.getBookingOfficeId());
					/** Set Booking Type  details*/
					consignmentBilling.setBOOKING_TYPE(bookingDO.getBookingType().getBookingType());
					consignmentBilling.setBOOKING_TYPE_DESC(bookingDO.getBookingType().getBookingTypeDesc());
					/** Set Shift To CODE */
					consignmentBilling.setSHIP_TO_CODE(bookingDO.getShippedToCode());
					
					
					/* T Series credit booking consignments which are either delivered or RTO delivered */
					Boolean tSeriesDelivered = isTSeriesDeliveredOrRtoDeivered(consignmentBilling);
					/* Other consignment series fresh/modified/rto bookings */
					Boolean tSeriesCODValidation = isCODApplicableForTSeriesOrNonTseries(consignmentBilling);
					/* Fresh or Modified or RTO Delivered consignment */ 
					Boolean isFreshOrModifiedOrRTODeliveredCN = isFreshOrModifiedOrRTODeliveredCN(consignmentBilling);
					/* Check Shifttocode */
					Boolean isShiftToCodeApplicable = isShiftToCodeApplicable(consignmentBilling);
					
					if ((tSeriesDelivered
							|| (tSeriesCODValidation && isFreshOrModifiedOrRTODeliveredCN)) && isShiftToCodeApplicable) {
						consignmentDOs.add(consignmentBilling);
						/** Add entry into consignment booking billing mapping table */
						// Get entry from CBBM table if exist.
						consgnBookingMappDO = consignmentBookingBillingMappingDOsMap.get(consgNo);
						if (StringUtil.isNull(consgnBookingMappDO)){
							consgnBookingMappDO = new ConsignmentBookingBillingMappingDO();
						}
						consgnBookingMappDO.setBookingDate(bookingDO.getBookingDate());
						consgnBookingMappDO.setBookingId(bookingDO.getBookingId());
						consgnBookingMappDO.setConsgNumber(consgNo);
						consgnBookingMappDO.setShippedToCode(bookingDO.getShippedToCode());
						consgnBookingMappDO.setBookingOfficeId(bookingDO.getBookingOfficeId());
						consgnBookingMappDO.setConsgId(consignmentBilling.getConsgId());
						if (!StringUtil.isNull(bookingDO.getCustomerId())){
							consgnBookingMappDO.setCustomerId(bookingDO.getCustomerId().getCustomerId());
						}
						if (!StringUtil.isNull(bookingDO.getBookingType())){
							consgnBookingMappDO.setBookingType(bookingDO.getBookingType().getBookingTypeId());
						}
						if (!StringUtil.isNull(bookingDO.getPincodeId())) {
							consgnBookingMappDO.setPincodeId(bookingDO.getPincodeId().getPincodeId());
						}
						if (!StringUtil.isNull(bookingDO.getConsgTypeId())) {
							consgnBookingMappDO.setConsgTypeId(bookingDO.getConsgTypeId().getConsignmentId());
						}
						// Find Out Applicable contract
						Integer applicableRateContract = integrationDAO.findApplicableContractForConsignment(bookingDO.getConsgNumber());
						if(!StringUtil.isEmptyInteger(applicableRateContract)){
							consgnBookingMappDO.setApplicableRateContract(applicableRateContract);
						}
						consignmentBookingBillingMappingDOsMap.put(consgNo, consgnBookingMappDO);
						//integrationDAO.saveOrUpdateConsignmentBookingMapping(consgnBookingMappDO);
					} else {
						consgNos.add(consgNo);
					}
				}  else {
					consgNos.add(consgNo);
				}
			}
		}
		// if not eligible for billing then marked as a PFB
		if (!CGCollectionUtils.isEmpty(consgNos)){
			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl:: Updated Status of nonEligible CN-----> Total NonEligible CN  :: " + consgNos.size());
			integrationDAO.UpdateConsgnBillStatusByConsgnNo(consgNos, "PFB");
		}
		
		//direct RTB Update
		List<String> cnEligibleforRTB = new ArrayList<String>();
		
		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::SettingBookingDetails::END-----> Total Eligible CN  :: " + consignmentDOs.size());
		if (!StringUtil.isEmptyColletion(consignmentDOs)) {
			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate:: Total Consignments For Rate calc :: ----->"
					+ consignmentDOs.size());
			long startTm1=System.currentTimeMillis();
			/*List<ConsignmentTO> consignmentTOs = SAPBillingConverter.convertConsignmentDOsToTOs(consignmentDOs);*/
			List<ConsignmentTO> consignmentTOs = SAPBillingConverter.convertBillingConsignmentDOsToTOs(consignmentDOs);
			long endTm1=System.currentTimeMillis();
			long elapseTm1=endTm1-startTm1;
			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::Elaspse time for converting consgDO to TO ----->"+elapseTm1 +"  for total CN "+consignmentTOs.size());
			
			if(consignmentTOs.size()>10){
				List<List<ConsignmentTO>> partitions = null;
				if(consignmentTOs.size() > maxThreadLimit){
					partitions = Lists.partition(consignmentTOs, consignmentTOs.size()/maxThreadLimit);
				} else {
					partitions = Lists.partition(consignmentTOs, 1);
				}
			   for(List<ConsignmentTO> consignmentSubList : partitions) {
				   billingParallelRateCalc=new BillingParallelRateCalc(consignmentSubList, this.rateCalculationUniversalService, this.integrationDAO);
				   cnThreads.add(billingParallelRateCalc);
				}
				 ExecutorService executor = Executors.newFixedThreadPool(partitions.size());
				 @SuppressWarnings({ "unchecked", "rawtypes" })
				 long startTmMulti=System.currentTimeMillis();  
				 //Calculating rate using Multithreading
				 List<Future< Map<String,ConsignmentRateCalculationOutputTO>>> results1 = executor.invokeAll(cnThreads);
				 executor.shutdown();
				 long endTmMulti=System.currentTimeMillis();
				 logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for MultiThreading----->"+(endTmMulti-startTmMulti));
				 
				 for(Future< Map<String,ConsignmentRateCalculationOutputTO>> resultOutpt:results1){
					 try {					
						 Map<String,ConsignmentRateCalculationOutputTO> resultMap=resultOutpt.get();
						 Iterator<Map.Entry<String,ConsignmentRateCalculationOutputTO>> entries = resultMap.entrySet().iterator();
						 while (entries.hasNext()) {
						     Map.Entry<String,ConsignmentRateCalculationOutputTO> entry = entries.next();
						     if(entry.getValue()!=null && entry.getKey()!=null){
						    	 long start=System.currentTimeMillis();
						    	 ConsignmentRateCalculationOutputTO rateOutputTO=entry.getValue();
						    	 ConsignmentDO consingnment = new ConsignmentDO();
					   			 consingnment.setConsgId(rateOutputTO.getConsgId());
					   			 if(!StringUtil.isNull(rateOutputTO.getRateCalculatedFor()) && rateOutputTO.getRateCalculatedFor().equalsIgnoreCase("R")){
					   				consignmentBillingRate=integrationDAO.getAlreadyExistConsgRate(consingnment,"R");
					   			 } else {
					   				consignmentBillingRate=integrationDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
					   			 }
					   		 if(!StringUtil.isNull(consignmentBillingRate)){
								prepareConsgRateCalcDomainConverter(rateOutputTO,consignmentBillingRate);
								calcCNRateDOsMap.put(entry.getKey(), consignmentBillingRate);
							   	// consignmentBillingRate=integrationDAO.saveOrUpdateConsgRate(consignmentBillingRate,entry.getKey());
						     }
						     else{
						    	 consignmentBillingRate=new ConsignmentBillingRateDO();
						    	 consignmentBillingRate.setCreatedDate(DateUtil.getCurrentDate());
						    	 consignmentBillingRate.setCreatedBy(4);
					   			 prepareConsgRateCalcDomainConverter(rateOutputTO,consignmentBillingRate);
					   			 ConsignmentDO consgDO = new ConsignmentDO();
					   			 consgDO.setConsgId(rateOutputTO.getConsgId());
					   			 consignmentBillingRate.setConsignmentDO(consgDO);
					   			 consignmentBillingRate.setRateCalculatedFor(bookStatus);
					   			 prepareConsgRateCalcDomainConverter(rateOutputTO,consignmentBillingRate);
					   			 calcCNRateDOsMap.put(entry.getKey(), consignmentBillingRate);
					   			 //consignmentBillingRate=integrationDAO.saveOrUpdateConsgRate(consignmentBillingRate,entry.getKey());
						     }
						     long end=System.currentTimeMillis();
						     logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Insert/Update and changing flag ----->"+(end-start)+"  For CN  "+entry.getKey());
						 }
						 else if(entry.getValue()== null && entry.getKey()!=null) {
							//updating status of consg to RTB
				   			cnEligibleforRTB.add(entry.getKey());
				   			//UpdateConsignmentBillingStatus(entry.getKey());
						 }
					  }
					} catch (ExecutionException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
						logger.error("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::Error"+e);
					}
				  }	 
			  } else{
			      for(ConsignmentTO consignmentTO:consignmentTOs){
			   	  try{
			   		  String bookingType=consignmentTO.getBookingType();
			   		  //if(!bookingType.equals("CS") && !bookingType.equals("EB") && !bookingType.equals("FC") && consignmentTO.getChangedAfterNewRateCmpnt().equals("Y") ){
			   		if(!bookingType.equals("CS") && !bookingType.equals("EB") && !bookingType.equals("FC") && consignmentTO.getChangedAfterBillingWtDest().equals("Y") && consignmentTO.getTypeTO().getConsignmentCode().equals("PPX") && consignmentTO.getBOOKING_RATE_BILLED().equals("N")){	
			   			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::Rate for Consg----->"+consignmentTO.getConsgNo());
			   		  long startTime = System.currentTimeMillis();
			   	      //calculating rate for CN
			   		  consignmentRateCalculationOutputTO=rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
			   		  long stopTime = System.currentTimeMillis();
			   		  long elapsedTime = stopTime - startTime;
			   		logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for calc rate----->"+elapsedTime+" for CN  "+consignmentTO.getConsgNo());
			   			   
			   		  //checking prev consg rates
			   		 startTime1 = System.currentTimeMillis();
			   		 if(StringUtil.isStringEmpty(consignmentTO.getConsgStatus())){
			   			   ConsignmentDO consingnment = new ConsignmentDO();
			   			   consingnment.setConsgId(consignmentTO.getConsgId());
			   		       consignmentBillingRate=integrationDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
			   		 }
			   		 else if(consignmentTO.getConsgStatus().equals("R")){
			   		     ConsignmentDO consingnment = new ConsignmentDO();
			   			 consingnment.setConsgId(consignmentTO.getConsgId());
			   			 consignmentBillingRate=integrationDAO.getAlreadyExistConsgRate(consingnment,consignmentTO.getConsgStatus());
			   		 }
			   		 else{
			   			  ConsignmentDO consingnment = new ConsignmentDO();
			   			  consingnment.setConsgId(consignmentTO.getConsgId());
			   			  consignmentBillingRate=integrationDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
			   		}
			   			
			   		if(!StringUtil.isNull(consignmentBillingRate)){
			   		   prepareConsgRateCalcDomainConverter(consignmentRateCalculationOutputTO,consignmentBillingRate);
			   				
			   				consignmentBillingRate=integrationDAO.saveOrUpdateConsgRate(consignmentBillingRate,consignmentTO.getConsgNo());
			   		}
			   		else{
			   			 consignmentBillingRate=new ConsignmentBillingRateDO();
			   			 consignmentBillingRate.setCreatedDate(DateUtil.getCurrentDate());
			   			 consignmentBillingRate.setCreatedBy(4);
			   			 prepareConsgRateCalcDomainConverter(consignmentRateCalculationOutputTO,consignmentBillingRate);
			   			 ConsignmentDO consingnment = new ConsignmentDO();
			   			 consingnment.setConsgId(consignmentTO.getConsgId());
			   			 consignmentBillingRate.setConsignmentDO(consingnment);
			   				
			   			 if(StringUtil.isStringEmpty(consignmentTO.getConsgStatus())){
			   				consignmentBillingRate.setRateCalculatedFor(bookStatus);
			   			 }
			   			 else if(consignmentTO.getConsgStatus().equals("R")){
			   				consignmentBillingRate.setRateCalculatedFor(consignmentTO.getConsgStatus());
			   			}
			   			else{
			   				  consignmentBillingRate.setRateCalculatedFor(bookStatus);
			   			}
			   			  consignmentBillingRate=integrationDAO.saveOrUpdateConsgRate(consignmentBillingRate,consignmentTO.getConsgNo());
			   		  }
			   			stopTime1 = System.currentTimeMillis();
			   			elapsedTime = stopTime1 - startTime1;
			   			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Insert/UPDATING and flag changing----->"+elapsedTime+" for CN  "+consignmentTO.getConsgNo());
			   			   
			   		}
			   		else{
			   			//updating status of consg to RTB
			   			cnEligibleforRTB.add(consignmentTO.getConsgNo());
			   			//UpdateConsignmentBillingStatus(consignmentTO.getConsgNo());
			   		}
			   			  
			   		 }catch(Exception e){
			   			logger.error("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler ::Exception"+e +"Exception occur while calc rate for CN "+consignmentTO.getConsgNo());
			   		}
			   	  }
			   }
			
			// Saving CBBM entries
			if (!CGCollectionUtils.isEmpty(consignmentBookingBillingMappingDOsMap.values())){
				integrationDAO.saveOrUpdateConsignmentBookingMapping(consignmentBookingBillingMappingDOsMap.values());
			}
			
			// UPDATE Consignment Status
			if(!CGCollectionUtils.isEmpty(calcCNRateDOsMap)){
				// consignmentBillingRate=integrationDAO.saveOrUpdateConsgRate(consignmentBillingRate,entry.getKey());
				integrationDAO.bulkSaveOrUpdateConsgRate(calcCNRateDOsMap);
			}
			
			if (!CGCollectionUtils.isEmpty(cnEligibleforRTB)){
				long startTm=System.currentTimeMillis();
				UpdateConsignmentBillingStatus(cnEligibleforRTB);
				long endTm=System.currentTimeMillis();
	   			long elapseTm=endTm-startTm;
	   			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Changing only Billing status flag----->"+elapseTm);
			}
			
			
		} else {
			logger.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler :: BillingCommonServiceImpl::calcRateForCNInConsgRate:: Total Consignments For Rate calc :: -----> 0");
		}
		
		}


	private Boolean isShiftToCodeApplicable(
			ConsignmentBilling consignmentBilling) {
		Boolean flag = true;
		String shiftToCode = consignmentBilling.getSHIP_TO_CODE();
		if (StringUtil.isStringEmpty(shiftToCode)) {
			Integer custId = consignmentBilling.getCustomer();
			if (StringUtil.isEmptyInteger(custId)) {
				String bookingType = consignmentBilling.getBOOKING_TYPE();
				if ("FC".equalsIgnoreCase(bookingType) || "CS".equalsIgnoreCase(bookingType) || "EB".equalsIgnoreCase(bookingType)){
					flag = true;
				}else {
					flag = false;
				}
			}
		}
		return flag;
	}
/*(( ffc.CONSG_STATUS != 'R' 
AND ffc.CONSG_STATUS != 'S' 
AND (  // fresh consignments 
		(ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'N' AND ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'N' AND
		(ffb.BOOKING_DATE <= DATE_SUB(NOW() ,INTERVAL fdp.CONSOLIDATION_WINDOW DAY))) 
		// weight and/or destination modified consignments 
      OR ( fdbt.BOOKING_TYPE NOT IN ('EB' ,'CS' ,'FC') AND fdcnt.CONSIGNMENT_CODE != 'DOX' 
		     AND (ffc.CHANGED_AFTER_BILLING_WEIGHT_DEST = 'Y' OR ffc.CHANGED_AFTER_NEW_RATE_COMPONENT = 'Y')
	       )
     )
)  //RTO or RTO Delivered consignments 
OR (fdbt.BOOKING_TYPE NOT IN ('EB' ,'CS' ,'FC') AND (ffc.CONSG_STATUS = 'R' OR ffc.CONSG_STATUS = 'S'))
)*/
	private Boolean isFreshOrModifiedOrRTODeliveredCN(
			ConsignmentBilling consignmentBilling) {
		Boolean flag = false;
		String bookingType = consignmentBilling.getBOOKING_TYPE();
		String consigStatus = consignmentBilling.getConsgStatus();
		String changedAfterBillingWtDest = consignmentBilling
				.getChangedAfterBillingWtDest();
		String changedAfterNewRateComp = consignmentBilling
				.getChangedAfterNewRateCmpnt();

		if ((/* NonRTO & non RTO Delivered */consigStatus != null
				&& !consigStatus.equalsIgnoreCase("R")
				&& !consigStatus.equalsIgnoreCase("S") && ((/* Fresh CN */
					"N".equalsIgnoreCase(changedAfterNewRateComp) && isValidConsolidationWindow(
					consignmentBilling.getBOOKING_DATE(),
					consignmentBilling.getCONSOLIDATION_WINDOW())) 
					|| (/* Modified CN */(bookingType != null
				&& !"EB".equalsIgnoreCase(bookingType)
				&& !"CS".equalsIgnoreCase(bookingType) && !"FC"
					.equalsIgnoreCase(bookingType)))))
				|| (/* RTO delivered */(bookingType != null
						&& !"EB".equalsIgnoreCase(bookingType)
						&& !"CS".equalsIgnoreCase(bookingType) && !"FC"
							.equalsIgnoreCase(bookingType)) && (consigStatus != null && (consigStatus
						.equalsIgnoreCase("R") || consigStatus
						.equalsIgnoreCase("S"))))) {
			flag = true;
		}

		return flag;
	}

	/*ffb.BOOKING_DATE <= DATE_SUB(NOW() ,INTERVAL fdp.CONSOLIDATION_WINDOW DAY))*/
	private boolean isValidConsolidationWindow(Date booking_DATE,
			Integer consolidation_WINDOW) {
		boolean flag = false;
		if (consolidation_WINDOW != null && booking_DATE!=null){
			Date consDate = DateUtil.getPreviousDate(consolidation_WINDOW);
			if (booking_DATE.compareTo(consDate) <= 0){
				flag = true;
			}
		}
		return flag;
	}

	/*
	 * (fdp.CONSG_SERIES != 'T' OR (fdp.CONSG_SERIES = 'T' AND ffc.COD_AMT IS
	 * NOT NULL AND ffc.COD_AMT != 0) OR ( fdp.CONSG_SERIES = 'T' AND
	 * fdct.CUSTOMER_TYPE_CODE IN ('BA' ,'BV') AND (ffc.COD_AMT IS NULL OR
	 * ffc.COD_AMT = 0) ) )
	 */
	private Boolean isCODApplicableForTSeriesOrNonTseries(
			ConsignmentBilling consignmentBilling) {
		Boolean flag = false;
		if (!consignmentBilling.getCONSG_SERIES().equalsIgnoreCase("T")
				|| (consignmentBilling.getCONSG_SERIES().equalsIgnoreCase("T") && !StringUtil
						.isEmptyDouble(consignmentBilling.getCodAmt()))
				|| (consignmentBilling.getCONSG_SERIES().equalsIgnoreCase("T")
						&& StringUtil.isEmptyDouble(consignmentBilling
								.getCodAmt()) && (consignmentBilling
						.getCUSTOMER_TYPE_CODE() != null && (consignmentBilling
						.getCUSTOMER_TYPE_CODE().equalsIgnoreCase("BA") || consignmentBilling
						.getCUSTOMER_TYPE_CODE().equalsIgnoreCase("BV"))))) {
			flag = true;
		}

		return flag;
	}

	/*
	 * (fdp.CONSG_SERIES = 'T' AND (ffc.COD_AMT IS NULL OR ffc.COD_AMT = 0) AND
	 * (fdct.CUSTOMER_TYPE_CODE NOT IN ('BA' ,'BV') OR fdct.CUSTOMER_TYPE_CODE
	 * IS NULL) AND (ffb.BOOKING_DATE <= DATE_SUB(NOW() ,INTERVAL
	 * fdp.CONSOLIDATION_WINDOW DAY)) AND (ffc.CONSG_STATUS = 'D' OR
	 * ffc.CONSG_STATUS = 'S'))
	 */
	private Boolean isTSeriesDeliveredOrRtoDeivered(
			ConsignmentBilling consignmentBilling) {
		Boolean flag = false;
		if (consignmentBilling.getCONSG_SERIES().equalsIgnoreCase("T")
				&& StringUtil.isEmptyDouble(consignmentBilling.getCodAmt())
				&& (StringUtils.isEmpty(consignmentBilling
						.getCUSTOMER_TYPE_CODE()) || (!StringUtils
						.isEmpty(consignmentBilling.getCUSTOMER_TYPE_CODE()) && (!consignmentBilling
						.getCUSTOMER_TYPE_CODE().equalsIgnoreCase("BA") && !consignmentBilling
						.getCUSTOMER_TYPE_CODE().equalsIgnoreCase("BV"))))
				&& isValidConsolidationWindow(
						consignmentBilling.getBOOKING_DATE(),
						consignmentBilling.getCONSOLIDATION_WINDOW())
				&& (consignmentBilling.getConsgStatus() != null && (consignmentBilling
						.getConsgStatus().equalsIgnoreCase("D") || consignmentBilling
						.getConsgStatus().equalsIgnoreCase("S")))) {
			flag = true;
		}
		return flag;
	}

private void  prepareConsgRateCalcDomainConverter(ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO,ConsignmentBillingRateDO consignmentBillingRateDO) throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::START------------>:::::::");
	 if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){

		 PropertyUtils.copyProperties(consignmentBillingRateDO, consignmentRateCalculationOutputTO);
		 consignmentBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
		 consignmentBillingRateDO.setUpdatedBy(4);
		 consignmentBillingRateDO.setBilled("N");
	 }
	
	
	 logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::END------------>:::::::");
}

public void UpdateConsignmentBillingStatus(List<String> consgNo)throws CGBusinessException,
CGSystemException{
	logger.debug("BillingCommonServiceImpl::UpdateConsignmentBillingStatus::START----->");
	try{
	   integrationDAO.UpdateConsignmentBillingStatus(consgNo);
	} catch(Exception e){
		logger.error("Exception occurs in BillingCommonServiceImpl::UpdateConsignmentBillingStatus()::" 
				+ e);
		throw new CGBusinessException(e);
	}
	logger.debug("BillingCommonServiceImpl::UpdateConsignmentBillingStatus::END----->");
}


public BookingDO getConsgBookingDetails(String consgNo)throws CGBusinessException,
CGSystemException{
	logger.debug("BillingCommonServiceImpl::getConsgBookingDate::START----->");
	BookingDO bookingDO = null;
	Date bookingDate=null;
	try{
		bookingDO=integrationDAO.getCustomerFromTypeBooking(consgNo);
		if(!StringUtil.isNull(bookingDO)){
			/*if(!StringUtil.isNull(bookingDO.getBookingDate())){
				bookingDate=bookingDO.getBookingDate();
				
			}*/
			return bookingDO;
		}
	} catch(Exception e){
		logger.error("Exception occurs in BillingCommonServiceImpl::getConsgBookingDate()::" 
				+ e);
		throw new CGBusinessException(e);
	}
	logger.debug("BillingCommonServiceImpl::getConsgBookingDate::END----->");
	return null;
}


public ProductTO getProduct(Integer productId) throws CGBusinessException,
CGSystemException {

	logger.debug("BillingCommonServiceImpl::getProduct::START----->");
	ProductTO productTO = null;
	ProductDO productDO = null;
	try{
	productDO=integrationDAO.getProduct(productId);
	if(!StringUtil.isNull(productDO)){
		productTO = new ProductTO();
		CGObjectConverter.createToFromDomain(productDO, productTO);
	}
	} catch(Exception e){
		logger.error("Exception occurs in BillingCommonServiceImpl::getProduct()::" 
			+ e);
	throw new CGBusinessException(e);
	}
	logger.debug("BillingCommonServiceImpl::getProduct::END----->");
	return productTO;
 }


public boolean billing_Stock_consolidation_Proc()throws CGBusinessException,
CGSystemException{
	
	logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::billing_Stock_consolidation_Proc::START----->");		
	boolean flag=false;
	try{
		
		flag=integrationDAO.billing_Stock_consolidation_Proc();
		
	}catch(Exception e){
		logger.error(" BillingSummaryCreationScheduler - for Summary scheduler ::  Exception occurs in BillingCommonServiceImpl::billing_Stock_consolidation_Proc()::" 
				+ e);
		throw new CGBusinessException(e);
	}
	logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::billing_Stock_consolidation_Proc::END----->");
	return flag;
}

public boolean billing_consolidation_Proc()throws CGBusinessException,
CGSystemException{
	
	logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::billing_consolidation_Proc::START----->");		
	boolean flag=false;
	try{
		
		flag=integrationDAO.billing_consolidation_Proc();
		
	}catch(Exception e){
		logger.error(" BillingSummaryCreationScheduler - for Summary scheduler :: Exception occurs in BillingCommonServiceImpl::billing_consolidation_Proc()::" 
				+ e);
		throw new CGBusinessException(e);
	}
	logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::billing_consolidation_Proc::END----->");
	return flag;
}

	public List<SAPBillingConsignmentSummaryDO> findBCSDtlsFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO,
			Long maxDataCountLimit) throws CGSystemException {
		return integrationDAO.findBCSDtlsFromStaging(sapBillConsgSummaryTO,
				maxDataCountLimit);
	}

	@Override
	public Long getBCSDtlsCountFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)
			throws CGSystemException {
		return integrationDAO.getBCSDtlsCountFromStaging(sapBillConsgSummaryTO);
	}

	@Override
	public boolean ba_billing_consolidation_Proc() throws CGBusinessException,
			CGSystemException {
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::ba_billing_consolidation_Proc::START----->");		
		boolean flag=false;
		try{
			flag=integrationDAO.ba_billing_consolidation_Proc();
		}catch(Exception e){
			logger.error(" BillingSummaryCreationScheduler - for Summary scheduler :: Exception occurs in BillingCommonServiceImpl::ba_billing_consolidation_Proc()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		logger.debug("BillingSummaryCreationScheduler - for Summary scheduler :: BillingCommonServiceImpl::ba_billing_consolidation_Proc::END----->");
		return flag;
	}
}
