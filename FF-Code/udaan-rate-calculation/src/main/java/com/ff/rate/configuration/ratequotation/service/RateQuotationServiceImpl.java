package com.ff.rate.configuration.ratequotation.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.BaseCustomerDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.AddressDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.ContactDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.OctroiChargeDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateContractDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkMatrixHeaderDO;
import com.ff.domain.ratemanagement.operations.ratebenchmark.RateBenchMarkProductDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCustomerDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationFixedChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSpecialDestinationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationWrapperDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.ZoneTO;
import com.ff.jobservices.JobServicesTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ratebenchmark.service.RateBenchMarkService;
import com.ff.rate.configuration.ratebenchmarkdiscount.service.RegionRateBenchMarkDiscountService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationExcelConstants;
import com.ff.rate.configuration.ratequotation.dao.RateQuotationDAO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.rateBenchmarkDiscount.RegionRateBenchMarkDiscountTO;
import com.ff.to.ratemanagement.operations.ratebenchmark.RateBenchMarkHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.ContactTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;
import com.ff.to.ratemanagement.operations.ratequotation.OctroiChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionFixedChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotaionRTOChargesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationCODChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationListViewTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationProposedRatesTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationSlabRateTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationSpecialDestinationTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateQuotationWeightSlabTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserTO;
import com.ff.universe.jobservice.service.JobServicesUniversalService;

public class RateQuotationServiceImpl implements RateQuotationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(RateQuotationServiceImpl.class);
	RateQuotationDAO rateQuotationDAO;
	
	RateCommonService rateCommonService;
	
	RateBenchMarkService rateBenchMarkService;
	
	RegionRateBenchMarkDiscountService regionRateBenchMarkDiscountService;
	
	JobServicesUniversalService jobService;
	
	
	public JobServicesUniversalService getJobService() {
		return jobService;
	}

	public void setJobService(JobServicesUniversalService jobService) {
		this.jobService = jobService;
	}

	/**
	 * @return the rateBenchMarkService
	 */
	public RateBenchMarkService getRateBenchMarkService() {
		return rateBenchMarkService;
	}
 
	/**
	 * @return the regionRateBenchMarkDiscountService
	 */
	public RegionRateBenchMarkDiscountService getRegionRateBenchMarkDiscountService() {
		return regionRateBenchMarkDiscountService;
	}

	/**
	 * @param regionRateBenchMarkDiscountService the regionRateBenchMarkDiscountService to set
	 */
	public void setRegionRateBenchMarkDiscountService(
			RegionRateBenchMarkDiscountService regionRateBenchMarkDiscountService) {
		this.regionRateBenchMarkDiscountService = regionRateBenchMarkDiscountService;
	}

	public void setRateBenchMarkService(RateBenchMarkService rateBenchMarkService) {
		this.rateBenchMarkService = rateBenchMarkService;
	}

	/**
	 * @param rateBenchMarkService the rateBenchMarkService to set
	 */
	
	public RateQuotationDAO getRateQuotationDAO() {
		return rateQuotationDAO;
	}

	public void setRateQuotationDAO(RateQuotationDAO rateQuotationDAO) {
		this.rateQuotationDAO = rateQuotationDAO;
	}

	
	public RateCommonService getRateCommonService() {
		return rateCommonService;
	}

	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryTypeTO> getRateIndustryType()
			throws CGBusinessException, CGSystemException {
		List<RateIndustryTypeTO> rateIndustryTypeTOList = null;
		LOGGER.trace("RateQuotationServiceImpl::getRateIndustryType::START------------>:::::::");
			List<RateIndustryTypeDO> rateIndustryTypeDOList = rateQuotationDAO
					.getRateIndustryType();

			if (!CGCollectionUtils.isEmpty(rateIndustryTypeDOList)) {
				rateIndustryTypeTOList = (List<RateIndustryTypeTO>) CGObjectConverter
						.createTOListFromDomainList(rateIndustryTypeDOList,
								RateIndustryTypeTO.class);
			}
		LOGGER.trace("RateQuotationServiceImpl::getRateIndustryType::END------------>:::::::");
		return rateIndustryTypeTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerGroupTO> getcustomerGroup() throws CGBusinessException,
			CGSystemException {
		List<CustomerGroupTO> customerGroupTOList = null;
			LOGGER.trace("RateQuotationServiceImpl::getcustomerGroup::START------------>:::::::");
			List<CustomerGroupDO> customerGroupDOList = rateQuotationDAO
					.getcustomerGroup();

			if (!CGCollectionUtils.isEmpty(customerGroupDOList)) {
				customerGroupTOList = (List<CustomerGroupTO>) CGObjectConverter
						.createTOListFromDomainList(customerGroupDOList,
								CustomerGroupTO.class);
			}
		LOGGER.trace("RateQuotationServiceImpl::getcustomerGroup::END------------>:::::::");
		return customerGroupTOList;
	}

	public EmployeeUserTO getEmployeeUser(Integer userId)
			throws CGBusinessException, CGSystemException {
		EmployeeUserTO employeeUserTO = new EmployeeUserTO();
			LOGGER.trace("RateQuotationServiceImpl::getEmployeeUser::START------------>:::::::");
			EmployeeUserDO employeeUserDO = rateQuotationDAO
					.getEmployeeUser(userId);

			if (!StringUtil.isNull(employeeUserDO)) {

				EmployeeTO empTO = new EmployeeTO();
				empTO.setEmployeeId(employeeUserDO.getEmpDO().getEmployeeId());
				empTO.setFirstName(employeeUserDO.getEmpDO().getFirstName());
				empTO.setLastName(employeeUserDO.getEmpDO().getLastName());
				employeeUserTO.setEmpTO(empTO);
			}
		LOGGER.trace("RateQuotationServiceImpl::getEmployeeUser::END------------>:::::::");
		return employeeUserTO;
	}

	@Override
	public RateQuotationTO saveOrUpdateBasicInfo(RateQuotationTO rateQuotationTO)
			throws CGBusinessException, CGSystemException {
			LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateBasicInfo::START------------>:::::::");
			RateQuotationDO quotationDO = null;
			
			if (StringUtil.isNull(rateQuotationTO.getRateQuotationId())
					|| (rateQuotationTO.getRateQuotationId()
							.equals(CommonConstants.ZERO))) {
				List<String> seqNOs = generateSequenceNo(
						Integer.parseInt(RateQuotationConstants.ONE),
						RateQuotationConstants.QUOTATION_NO);
				String quotationNo = getQuotationNo(rateQuotationTO, seqNOs);
				if(!StringUtil.isStringEmpty(quotationNo)){
				rateQuotationTO.setRateQuotationNo(quotationNo);
				}else{
					ExceptionUtil.prepareBusinessException(RateErrorConstants.VALIDATE_SEQ_GEN_ERROR, new String[]{RateQuotationConstants.RATE_QUOT_NO});
				}
			}
			
			quotationDO = prepareQuotationDOList(rateQuotationTO);
			if (!StringUtil.isNull(quotationDO)) {
				setCustomerType(rateQuotationTO, quotationDO);
				quotationDO = rateQuotationDAO
						.saveOrUpdateBasicInfo(quotationDO);
				rateQuotationTO.setSaved(Boolean.TRUE);
			}

			if (!StringUtil.isNull(quotationDO)) {
				rateQuotationTO.setRateQuotationId(quotationDO
						.getRateQuotationId());
				ContactTO primaryContactTO = new ContactTO();
				ContactTO secondaryContactTO = new ContactTO();
				CustomerTO customerNewTO = new CustomerTO();
				PickupDeliveryAddressTO address = new PickupDeliveryAddressTO();
				if(quotationDO.getQuotationUsedFor().equals("Q")){
					customerNewTO.setCustomerId(quotationDO.getQuotedCustomer()
							.getCustomerId());
					primaryContactTO.setContactId(quotationDO.getQuotedCustomer()
							.getPrimaryContactDO().getContactId());
					customerNewTO.setPrimaryContact(primaryContactTO);
					if (!StringUtil.isNull(quotationDO.getQuotedCustomer()
							.getSecondaryContactDO())) {
						secondaryContactTO.setContactId(quotationDO.getQuotedCustomer()
								.getSecondaryContactDO().getContactId());
						customerNewTO.setSecondaryContact(secondaryContactTO);
					}
					if (!StringUtil.isNull(quotationDO.getQuotedCustomer().getAddressDO())) {
						address.setAddressId(quotationDO.getQuotedCustomer().getAddressDO()
								.getAddressId());
					}
				}else{
					if(quotationDO.getQuotationUsedFor().equals("C")){
						customerNewTO.setCustomerId(quotationDO.getCustomer()
								.getCustomerId());
						primaryContactTO.setContactId(quotationDO.getCustomer()
								.getPrimaryContactDO().getContactId());
						customerNewTO.setPrimaryContact(primaryContactTO);
						if (!StringUtil.isNull(quotationDO.getCustomer()
								.getSecondaryContactDO())) {
							secondaryContactTO.setContactId(quotationDO.getCustomer()
									.getSecondaryContactDO().getContactId());
							customerNewTO.setSecondaryContact(secondaryContactTO);
						}
						if (!StringUtil.isNull(quotationDO.getCustomer().getAddressDO())) {
							address.setAddressId(quotationDO.getCustomer().getAddressDO()
									.getAddressId());
						}
					}
				}
				customerNewTO.setAddress(address);
				rateQuotationTO.setCustomer(customerNewTO);
			}
		LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateBasicInfo::END------------>:::::::");
		return rateQuotationTO;

	}

	private String getQuotationNo(RateQuotationTO rateQuotationTO,
			List<String> seqNOs) {
		LOGGER.trace("RateQuotationServiceImpl::getQuotationNo::START------------>:::::::");
		String quoNo = RateQuotationConstants.CHAR_Q
				+ rateQuotationTO.getLoginOfficeCode() + seqNOs.get(0);
		LOGGER.trace("RateQuotationServiceImpl::getQuotationNo::END------------>:::::::");
		return quoNo;
	}

	private RateQuotationDO prepareQuotationDOList(
			RateQuotationTO rateQuotationTO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationDOList::START------------>:::::::");
		RateQuotationDO rateQuotationDO = null;
		rateQuotationDO = rateQuotationDAO.getRateQuotation(rateQuotationTO.getRateQuotationNo(),rateQuotationTO.getQuotationUsedFor(),rateQuotationTO.getRateQuotationId());
		
		BaseCustomerDO customerNewDO = null;
		String quotUsedFor = rateQuotationTO.getQuotationUsedFor();
		
		
		if(StringUtil.isNull(rateQuotationDO)){
			rateQuotationDO = new RateQuotationDO();			
		}else if(quotUsedFor.equals("C") && !StringUtil.isNull(rateQuotationDO.getCustomer())){
			customerNewDO = rateQuotationDO.getCustomer();				
		}else if(quotUsedFor.equals("Q") && !StringUtil.isNull(rateQuotationDO.getQuotedCustomer())){
			customerNewDO = rateQuotationDO.getQuotedCustomer();				
		}
		
		if(quotUsedFor.equals("Q")){
			if(StringUtil.isNull(customerNewDO)){
				customerNewDO = new RateQuotationCustomerDO();
			}
		}else{
			if(StringUtil.isNull(customerNewDO)){
				customerNewDO = new CustomerDO();
			}
		}
		
		if (!StringUtil.isEmptyInteger(rateQuotationTO.getRateQuotationId())) {
			rateQuotationDO.setRateQuotationId(rateQuotationTO
					.getRateQuotationId());
		}
		
		rateQuotationDO
				.setRateQuotationNo(rateQuotationTO.getRateQuotationNo());
		
		//if(!StringUtil.isNull(rateQuotationTO.getCustomer())){}
		if (!StringUtil.isNull(rateQuotationTO.getCreatedDate())) {
			rateQuotationDO.setQuotationCreatedDate(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(rateQuotationTO
							.getCreatedDate()));

		}

		if (!StringUtil.isNull(rateQuotationTO.getCreatedBy())) {
			rateQuotationDO.setQuotationCreatedBy(rateQuotationTO
					.getCreatedBy());
			rateQuotationDO.setCreatedDate(DateUtil.getCurrentDate());
		}
		rateQuotationDO.setUpdatedBy(rateQuotationTO
					.getUpdatedBy());
		rateQuotationDO.setUpdatedDate(Calendar.getInstance().getTime());
		
		prepareCustomerDO(rateQuotationTO, rateQuotationDO, customerNewDO);
		
		if(quotUsedFor.equals("Q")){
			rateQuotationDO.setQuotedCustomer((RateQuotationCustomerDO)customerNewDO);
		}else{
			rateQuotationDO.setCustomer((CustomerDO)customerNewDO);
		}
		
		if(!rateQuotationTO.getQuotationUsedFor().equals(RateQuotationConstants.CHAR_C)){
			rateQuotationDO.setStatus(RateQuotationConstants.NEW);
		}
		rateQuotationDO.setRateQuotationType(rateQuotationTO
				.getRateQuotationType());
		if(!StringUtil.isStringEmpty(rateQuotationTO.getQuotationUsedFor())){
			rateQuotationDO.setQuotationUsedFor(rateQuotationTO.getQuotationUsedFor());
		}else{
			rateQuotationDO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
		}
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationDOList::END------------>:::::::");
		return rateQuotationDO;
	}

	@Override
	public RateQuotationTO searchQuotationDetails(
			RateQuotationTO rateQuotationTO) throws CGBusinessException,
			CGSystemException {
		List<RateQuotationDO> rateQuotationDOs = null;
		RateQuotationDO rateQuotationDO = null;
		boolean isQuotExist = Boolean.FALSE;
		RateQuotationTO quotationTO = null;
				LOGGER.trace("RateQuotationServiceImpl::searchQuotationDetails::START------------>:::::::");

			
			rateQuotationDOs = rateQuotationDAO.searchQuotationDetails(rateQuotationTO);
			

			if (!CGCollectionUtils.isEmpty(rateQuotationDOs)) {
				//quotationTO = new RateQuotationTO();
				int quotDOSize = rateQuotationDOs.size();
				for(int i=0;i<quotDOSize;i++){
					if(rateQuotationDOs.get(i).getQuotationUsedFor().equals(RateQuotationConstants.QUOTATION)){
						rateQuotationDO=rateQuotationDOs.get(i);
						break;
					}
				}
				if (!StringUtil.isNull(rateQuotationDO)){
				quotationTO = quotationDomainConverter(rateQuotationDO);
				}else{
					ExceptionUtil
						.prepareBusinessException(RateErrorConstants.DETAILS_DOES_NOT_EXIST);
					
				}
				if(!CGCollectionUtils.isEmpty(rateQuotationDO.getRateQuotationProductCategoryHeaderDO()))
					quotationTO.setProposedRates(Boolean.TRUE);
					setFlags(quotationTO,rateQuotationDO);
				if(!CGCollectionUtils.isEmpty(rateQuotationDO.getRateFixedChargeDO()))
					quotationTO.setFixedChrgs(Boolean.TRUE);
				
				isQuotExist = rateQuotationDAO.isRateQuotationExist(rateQuotationTO.getRateQuotationNo(), RateQuotationConstants.CHAR_C);
				quotationTO.setContractCreated(isQuotExist);
				
				
			}else{
				ExceptionUtil
				.prepareBusinessException(RateErrorConstants.DETAILS_DOES_NOT_EXIST);
			
		}

		LOGGER.trace("RateQuotationServiceImpl::searchQuotationDetails::END------------>:::::::");
		return quotationTO;
	}

	private RateQuotationTO quotationDomainConverter(
			RateQuotationDO rateQuotationDO) {
		LOGGER.trace("RateQuotationServiceImpl::quotationDomainConverter::START------------>:::::::");
		RateQuotationTO rateQuotationTO = new RateQuotationTO();
		CustomerTO customerNew = new CustomerTO();
		if (!StringUtil.isEmptyInteger(rateQuotationDO.getRateQuotationId())) {
		rateQuotationTO
				.setRateQuotationId(rateQuotationDO.getRateQuotationId());
		}
		if (!StringUtil.isStringEmpty(rateQuotationDO.getRateQuotationNo())) {
		rateQuotationTO
				.setRateQuotationNo(rateQuotationDO.getRateQuotationNo());
		}
		if (!StringUtil.isStringEmpty(rateQuotationDO.getStatus())) {
			rateQuotationTO.setStatus(rateQuotationDO.getStatus());
		}
		if (!StringUtil.isEmptyInteger(rateQuotationDO.getQuotationCreatedBy())) {
			rateQuotationTO.setCreatedBy(rateQuotationDO.getQuotationCreatedBy());
		}
		if (!StringUtil.isStringEmpty(rateQuotationDO.getRateQuotationType())) {
			rateQuotationTO.setRateQuotationType(rateQuotationDO
					.getRateQuotationType());
		}
		
		if (!StringUtil.isNull(rateQuotationDO.getQuotationCreatedDate())) {
			rateQuotationTO.setCreatedDate(DateUtil.
					getDateInDDMMYYYYHHMMSlashFormat(rateQuotationDO
							.getQuotationCreatedDate()));
		}
		
		BaseCustomerDO custDO = null;
		if(!StringUtil.isNull(rateQuotationDO.getCustomer())){
			custDO = rateQuotationDO.getCustomer();
		}
		else if(!StringUtil.isNull(rateQuotationDO.getQuotedCustomer())){
			custDO = rateQuotationDO.getQuotedCustomer();
		}
		if (!StringUtil.isNull(custDO.getSalesOfficeDO())) {
			OfficeTO office = new OfficeTO();
			OfficeDO ofcDO = custDO.getSalesOfficeDO();
			office.setOfficeId(ofcDO.getOfficeId());
			office.setCityId(ofcDO.getCityId());
			office.setOfficeName(ofcDO.getOfficeName());
			customerNew.setSalesOffice(office);
			if(ofcDO.getOfficeTypeDO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				rateQuotationTO.setRhoOfcId(ofcDO.getOfficeId());
			}else if(ofcDO.getOfficeTypeDO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
				rateQuotationTO.setRhoOfcId(ofcDO.getOfficeId());
			}else{
				rateQuotationTO.setRhoOfcId(ofcDO.getReportingRHO());
			}
			
		}

		if (!StringUtil.isNull(custDO.getSalesPersonDO())) {
			EmployeeTO employeeTO = new EmployeeTO();
			EmployeeDO empDO = custDO.getSalesPersonDO();
			employeeTO.setEmployeeId(empDO.getEmployeeId());
			employeeTO.setFirstName(empDO.getFirstName());
			employeeTO.setLastName(empDO.getLastName());

			customerNew.setSalesPerson(employeeTO);
		}
		
		if(rateQuotationDO.getRateQuotationType().equals(RateQuotationConstants.NORMAL)){

		if (!StringUtil.isNull(custDO
				.getIndustryCategoryDO().getRateIndustryCategoryId())
			) {

			customerNew.setIndustryCategory(custDO
					.getIndustryCategoryDO().getRateIndustryCategoryId()
					+ CommonConstants.TILD
					+ custDO.getCustomerCategoryDO()
							.getRateCustomerCategoryId());
		}
		}
		if (!StringUtil.isNull(custDO.getIndustryTypeDO())) {

			customerNew.setIndustryType(custDO
					.getIndustryTypeDO().getRateIndustryTypeId()
					+ CommonConstants.TILD
					+ custDO.getIndustryTypeDO()
							.getRateIndustryTypeCode());
		}

		if (!StringUtil.isNull(custDO.getAddressDO())) {
			PickupDeliveryAddressTO addressTO = new PickupDeliveryAddressTO();
			AddressDO addressDO = custDO.getAddressDO();
			addressTO.setAddress1(addressDO.getAddress1());
			addressTO.setAddress2(addressDO.getAddress2());
			addressTO.setAddress3(addressDO.getAddress3());
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincodeId(addressDO.getPincodeDO().getPincodeId());
			pincodeTO.setPincode(addressDO.getPincodeDO().getPincode());
			CityTO cityTO = new CityTO();
			cityTO.setCityId(addressDO.getCityDO().getCityId());
			cityTO.setCityName(addressDO.getCityDO().getCityName());
			addressTO.setPincode(pincodeTO);
			addressTO.setCity(cityTO);
			customerNew.setAddress(addressTO);
		}

		if (!StringUtil.isNull(custDO
				.getPrimaryContactDO())) {
			ContactTO contact = new ContactTO();
			ContactDO primaryContactDO = custDO.getPrimaryContactDO();
			contact.setTitle(primaryContactDO.getTitle());
			contact.setDesignation(primaryContactDO.getDesignation());
			contact.setDepartment(primaryContactDO.getDepartment());
			contact.setEmail(primaryContactDO.getEmail());
			contact.setMobile(primaryContactDO.getMobile());
			contact.setExtension(primaryContactDO.getExtension());
			contact.setContactId(primaryContactDO.getContactId());
			contact.setFax(primaryContactDO.getFax());
			contact.setName(primaryContactDO.getName());
			contact.setContactNo(primaryContactDO.getContactNo());
			customerNew.setPrimaryContact(contact);

		}

		if (!StringUtil.isNull(custDO
				.getSecondaryContactDO())) {
			ContactTO contact = new ContactTO();
			ContactDO secContactDO = custDO.getSecondaryContactDO();
			contact.setTitle(secContactDO.getTitle());
			contact.setDesignation(secContactDO.getDesignation());
			contact.setDepartment(secContactDO.getDepartment());
			contact.setEmail(secContactDO.getEmail());
			contact.setMobile(secContactDO.getMobile());
			contact.setExtension(secContactDO.getExtension());
			contact.setContactId(secContactDO.getContactId());
			contact.setFax(secContactDO.getFax());
			contact.setName(secContactDO.getName());
			contact.setContactNo(secContactDO.getContactNo());
			customerNew.setSecondaryContact(contact);

		}
		if (!StringUtil.isNull(custDO.getGroupKeyDO())) {
			CustomerGroupTO customerGroup = new CustomerGroupTO();
			customerGroup.setCustomerGroupId(custDO
					.getGroupKeyDO().getCustomerGroupId());
			customerNew.setGroupKey(customerGroup);
		}
		if (!StringUtil.isStringEmpty(custDO.getBusinessName())) {
			customerNew.setBusinessName(custDO.getBusinessName());
		}

		if (!StringUtil.isEmptyInteger(custDO.getCustomerId())) {
			customerNew.setCustomerId(custDO.getCustomerId());
		}

		if (!StringUtil.isStringEmpty(custDO.getContractNo())) {
			customerNew.setContractNo(custDO.getContractNo());
		}

		if (!StringUtil.isStringEmpty(custDO.getBusinessType())) {
			customerNew.setBusinessType(custDO.getBusinessType());
		}

		if (!StringUtil.isStringEmpty(custDO.getCustomerCode())) {
			customerNew.setCustomerCode(custDO.getCustomerCode());
		}
		
		if (!StringUtil.isStringEmpty(custDO.getLegacyCustomerCode())) {
			customerNew.setLegacyCustomerCode(custDO.getLegacyCustomerCode());
		}

		//}
		
		rateQuotationTO.setCustomer(customerNew);
		
		if (!StringUtil.isStringEmpty(rateQuotationDO.getApproversRemarks())) {
			rateQuotationTO.setApproversRemarks(rateQuotationDO
					.getApproversRemarks());
		}

		if (!StringUtil.isStringEmpty(rateQuotationDO.getExcecutiveRemarks())) {
			rateQuotationTO.setExcecutiveRemarks(rateQuotationDO
					.getExcecutiveRemarks());
		}
		
		if (!StringUtil.isStringEmpty(rateQuotationDO.getApprovalRequired())) {
			rateQuotationTO.setApprovalRequired(rateQuotationDO
					.getApprovalRequired());
		}
		if (!StringUtil.isStringEmpty(rateQuotationDO.getApprovedAt())) {
			rateQuotationTO.setApprovedAt(rateQuotationDO
					.getApprovedAt());
		}
		
		rateQuotationTO.setQuotationCreatedFrom(rateQuotationDO.getQuotationCreatedFrom());
		LOGGER.trace("RateQuotationServiceImpl::quotationDomainConverter::END------------>:::::::");
		return rateQuotationTO;

	}

	public List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationServiceImpl::generateSequenceNo::START------------>:::::::");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(process);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfSeq);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = rateCommonService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("RateQuotationServiceImpl::generateSequenceNo::END------------>:::::::");
		return sequenceNumber;
	}

	@Override
	public OfficeTO getOfficeDetails(Integer loggedInRHO)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getOfficeDetails(loggedInRHO);
	}

	@Override
	public List<CityTO> getCitiesByCity(CityTO rhoCityTO)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getCitiesByCity(rhoCityTO);
	}

	@Override
	public CityTO getCity(CityTO cityTO) throws CGBusinessException,
			CGSystemException {
		return rateCommonService.getCity(cityTO);
	}

	@Override
	public List<StockStandardTypeTO> getStandardType(String string)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getStandardType(string);
	}

	@Override
	public List<InsuredByTO> getRiskSurchargeInsuredBy()
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getRiskSurchargeInsuredBy();
	}

	@Override
	public List<RateQuotaionFixedChargesTO> loadDefaultFixedChargesValue(
			String quotationId) throws CGBusinessException, CGSystemException {
		List<RateQuotaionFixedChargesTO> fixedChargesTOs = null;
				LOGGER.trace("RateQuotationServiceImpl::loadDefaultFixedChargesValue::START------------>:::::::");
			Integer rateQuotationId = Integer.parseInt(quotationId);

			List<RateQuotationFixedChargesDO> fixedChargesDOs = rateQuotationDAO
					.loadDefaultFixedChargesValue(rateQuotationId);
			RateQuotationFixedChargesConfigDO fixedChargesConfigDO = rateQuotationDAO
					.loadDefaultFixedChargesConfigValue(rateQuotationId);
			fixedChargesTOs = new ArrayList<RateQuotaionFixedChargesTO>(
					fixedChargesDOs.size());
			if (!StringUtil.isNull(fixedChargesConfigDO)) {

				RateQuotaionFixedChargesTO fixedChargesTO = new RateQuotaionFixedChargesTO();
				RateQuotationTO quotationTO = new RateQuotationTO();
				quotationTO.setRateQuotationId(fixedChargesConfigDO
						.getRateQuotationDO().getRateQuotationId());
				fixedChargesTO.setRateQuotation(quotationTO);
				fixedChargesTO.setOctroiBourneBy(fixedChargesConfigDO
						.getOctroiBourneBy());
				fixedChargesTO.setRiskSurchrge(fixedChargesConfigDO
						.getInsuredBy());
				fixedChargesTOs.add(fixedChargesTO);

			}
			if (!CGCollectionUtils.isEmpty(fixedChargesDOs)) {
				for (RateQuotationFixedChargesDO chargesDO : fixedChargesDOs) {
					RateQuotaionFixedChargesTO fixedChargesTO = new RateQuotaionFixedChargesTO();
					RateQuotationTO quotationTO = new RateQuotationTO();
					quotationTO.setRateQuotationId(chargesDO
							.getRateQuotationDO().getRateQuotationId());
					fixedChargesTO.setRateQuotation(quotationTO);
					fixedChargesTO.setRateComponentCode(chargesDO
							.getRateComponentCode());
					fixedChargesTO.setValue(chargesDO.getValue());
					fixedChargesTOs.add(fixedChargesTO);

				}
			}
		LOGGER.trace("RateQuotationServiceImpl::loadDefaultFixedChargesValue::END------------>:::::::");
		return fixedChargesTOs;
	}

	@Override
	public OctroiChargeTO getOctroiChargeValue(OctroiChargeTO octroiChargeTO)
			throws CGBusinessException, CGSystemException {
		OctroiChargeTO octroiCharge = new OctroiChargeTO();
			LOGGER.trace("RateQuotationServiceImpl::getOctroiChargeValue::START------------>:::::::");
			OctroiChargeDO octroiChargeDO = rateQuotationDAO
					.getOctroiChargeValue(octroiChargeTO);

			if (!StringUtil.isNull(octroiChargeDO)) {
				octroiCharge.setOctroiChargeId(octroiChargeDO
						.getOctroiChargeId());
				octroiCharge
						.setServiceCharge(octroiChargeDO.getServiceCharge());
			}else{
				ExceptionUtil
				.prepareBusinessException(RateErrorConstants.OCTROI_CHARGE_VALUE_NOT_FOUND);
			}
		LOGGER.trace("RateQuotationServiceImpl::getOctroiChargeValue::END------------>:::::::");
		return octroiCharge;
	}

	@Override
	public RateQuotaionFixedChargesTO saveOrUpdateFixedCharges(
			RateQuotaionFixedChargesTO fixedChargesTO, List<RateQuotationCODChargeTO> codList)
			throws CGBusinessException, CGSystemException {
		RateQuotationTO quotationTO = new RateQuotationTO();
		RateQuotationDO quotationDO = null;
		
			LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateFixedCharges::START------------>:::::::");
			quotationDO = 
					rateQuotationDAO.getRateQuotation(fixedChargesTO.getRateQuotation().getRateQuotationNo(),
							fixedChargesTO.getRateQuotation().getQuotationUsedFor(),fixedChargesTO.getRateQuotation().getRateQuotationId());
			prepareQuotationFixedChargeDOList(fixedChargesTO, quotationDO, codList);
			if (!StringUtil.isNull(fixedChargesTO)) {
				/*quotationDO = rateQuotationDAO.saveOrUpdateFixedCharges(
						quotationDO, configDO);*/
				quotationDO = rateQuotationDAO.saveOrUpdateBasicInfo(quotationDO);
				fixedChargesTO.setSaved(Boolean.TRUE);
			}

			if (!StringUtil.isNull(quotationDO)) {
				quotationTO.setRateQuotationId(quotationDO.getRateQuotationId());
				quotationTO.setRateQuotationNo(quotationDO.getRateQuotationNo());
				fixedChargesTO.setRateQuotation(quotationTO);
			}

		LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateFixedCharges::END------------>:::::::");
		return fixedChargesTO;

	}

	private void prepareQuotationFixedChargeDOList(
			RateQuotaionFixedChargesTO fixedChargesTO,
			RateQuotationDO rateQuotationDO, List<RateQuotationCODChargeTO> codList) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationFixedChargeDOList::START------------>:::::::");
		Set<RateQuotationFixedChargesDO> rqFixedDO = new HashSet<RateQuotationFixedChargesDO>();
		RateQuotationFixedChargesConfigDO conDO = new RateQuotationFixedChargesConfigDO();
		Set<RateQuotationCODChargeDO> quotationCOD = new HashSet<RateQuotationCODChargeDO>();
		Set<RateQuotationFixedChargesConfigDO> configDO = new HashSet<RateQuotationFixedChargesConfigDO>();
		Integer updatedBy = fixedChargesTO.getRateQuotation().getUpdatedBy();
		boolean con = false;
		rateQuotationDO.setRateFixedChargeDO(rqFixedDO);
		rateQuotationDO.setFixedChargesConfigDO(configDO);
		/*if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getRateQuotationId()))) {
			rateQuotationDO.setRateQuotationId(fixedChargesTO
					.getRateQuotation().getRateQuotationId());
		}*/

		if (!(StringUtil.isNull(fixedChargesTO.getRiskSurchrgeChk()))) {
			if (fixedChargesTO.getRiskSurchrgeChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(Double
						.valueOf(fixedChargesTO.getRiskSurchrge().split(
								CommonConstants.TILD)[1]));
			
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_RISK_SURCHARGE);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getAirportChargesChk()))) {
			if (fixedChargesTO.getAirportChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getAirportCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getSurchargeOnSTChk()))) {
			if (fixedChargesTO.getSurchargeOnSTChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getSurchargeOnST());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getOtherChargesChk()))) {
			if (fixedChargesTO.getOtherChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getOtherCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getOctroiServiceChargesChk()))) {
			if (fixedChargesTO.getOctroiServiceChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO
						.getOctroiServiceCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getParcelChargesChk()))) {
			if (fixedChargesTO.getParcelChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getParcelCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getEduChargesChk()))) {
			if (fixedChargesTO.getEduChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getEduCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getHigherEduChargesChk()))) {
			if (fixedChargesTO.getHigherEduChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getHigherEduCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getDocumentChargesChk()))) {
			if (fixedChargesTO.getDocumentChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getDocumentCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_DOCUMENT_HANDLING_CAHRGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getToPayChargesChk()))) {
			if (fixedChargesTO.getToPayChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getToPayCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getFuelSurchargeChk()))) {
			if (fixedChargesTO.getFuelSurchargeChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getFuelSurcharge());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getServiceTaxChk()))) {
			if (fixedChargesTO.getServiceTaxChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getServiceTax());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getStateTaxChk()))) {
			if (fixedChargesTO.getStateTaxChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				fixedChargesDO.setValue(fixedChargesTO.getStateTax());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);				
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getLcChargesChk()))) {
			if (fixedChargesTO.getLcChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getLcCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		/*if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getRateQuotationId()))) {
			rateQuotationDO.setRateQuotationId(fixedChargesTO
					.getRateQuotation().getRateQuotationId());
		}*/

		/*if (!(StringUtil.isStringEmpty(fixedChargesTO.getRateQuotation()
				.getExcecutiveRemarks()))) {
			rateQuotationDO.setExcecutiveRemarks(fixedChargesTO
					.getRateQuotation().getExcecutiveRemarks());
		}else{
			rateQuotationDO.setExcecutiveRemarks(null);
		}

		if (!(StringUtil.isStringEmpty(fixedChargesTO.getRateQuotation()
				.getApproversRemarks()))) {
			rateQuotationDO.setApproversRemarks(fixedChargesTO
					.getRateQuotation().getApproversRemarks());
		}else{
			rateQuotationDO.setApproversRemarks(null);
		}*/
		rateQuotationDO.setUpdatedBy(updatedBy);
		rateQuotationDO.setUpdatedDate(Calendar.getInstance().getTime());	
		/*if (!(StringUtil.isStringEmpty(fixedChargesTO.getRateQuotation()
				.getRateQuotationNo()))) {
			rateQuotationDO.setRateQuotationNo(fixedChargesTO
					.getRateQuotation().getRateQuotationNo());
		}*/
		rateQuotationDO.setRateFixedChargeDO(rqFixedDO);

		/*if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getRateQuotationId()))) {
			configDO.setQuotationId(fixedChargesTO.getRateQuotation()
					.getRateQuotationId());
		}*/

		if (!(StringUtil.isNull(fixedChargesTO.getOctroiBourneByChk()))) {
			if (fixedChargesTO.getOctroiBourneByChk().equals(RateQuotationConstants.ON)) {
				conDO.setOctroiBourneBy(fixedChargesTO.getOctroiBourneBy());
				//rateQuotationDO.setFixedChargesConfigDO(configDO);
				conDO.setRateQuotationDO(rateQuotationDO);
				con = true;
			}
		}

		//rateQuotationDO.setQuotationCreatedDate(DateUtil.getCurrentDate());
		rateQuotationDO.setCreatedBy(fixedChargesTO.getRateQuotation()
				.getCreatedBy());
		//rateQuotationDO.setStatus(RateQuotationConstants.NEW);
		rateQuotationDO.setRateQuotationType(RateQuotationConstants.NORMAL);

		if (!(StringUtil.isNull(fixedChargesTO.getRiskSurchrgeChk()))) {
			if (fixedChargesTO.getRiskSurchrgeChk().equals(RateQuotationConstants.ON)) {
				fixedChargesTO.setRiskSurchrge(fixedChargesTO.getRiskSurchrge()
						.split(CommonConstants.TILD)[0]);
				conDO.setInsuredBy(fixedChargesTO.getRiskSurchrge());
				//rateQuotationDO.setFixedChargesConfigDO(configDO);
				conDO.setRateQuotationDO(rateQuotationDO);
				con = true;
			}
		}
		if(con){
			conDO.setCreatedBy(updatedBy);
			conDO.setUpdatedBy(updatedBy);	
			configDO.add(conDO);
		}
		
		if(!CGCollectionUtils.isEmpty(codList)){
			prepareCODChargesList(fixedChargesTO, codList, rateQuotationDO, quotationCOD);
		}
		
		/*RateQuotationCODChargeDO quotationCodDO = null;
		int codLen = codList.size();
		for (int i = 0; i < codLen; i++) {
			quotationCodDO = new RateQuotationCODChargeDO();

			CodChargeDO codChargeDO = new CodChargeDO();
			codChargeDO.setCodChargeId(codList.get(i).getCodChargeId());
			quotationCodDO.setCodChargeDO(codChargeDO);

			quotationCodDO.setPercentileValue(codList.get(i)
					.getPercentileValue());
			quotationCodDO.setFixedChargeValue(codList.get(i)
					.getFixedChargeValue());
			quotationCodDO.setConsiderFixed(codList.get(i).getConsiderFixed());
			quotationCodDO.setConsiderHigherFixedOrPercent(codList.get(i)
					.getConsideeHigherFixedPercent());
			quotationCodDO.setCreatedBy(updatedBy);
			quotationCodDO.setUpdatedBy(updatedBy);
			quotationCodDO.setRateQuotationDO(rateQuotationDO);
			quotationCOD.add(quotationCodDO);
		}*/
		rateQuotationDO.setCodChargeDO(quotationCOD);
		//rateQuotationDO.setFixedChargesConfigDO(configDO);
		
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationFixedChargeDOList::END------------>:::::::");
	}

	@Override
	public List<RateComponentTO> loadDefaultRateComponentValue()
			throws CGBusinessException, CGSystemException {
		List<RateComponentTO> rateComponentTO = null;
			LOGGER.trace("RateQuotationServiceImpl::loadDefaultRateComponentValue::START------------>:::::::");
			List<RateComponentDO> rateComponentDOs = rateQuotationDAO
					.loadDefaultRateComponentValue();
			rateComponentTO = new ArrayList<RateComponentTO>(
					rateComponentDOs.size());
			if (!CGCollectionUtils.isEmpty(rateComponentDOs)) {
				for (RateComponentDO rateDO : rateComponentDOs) {

					RateComponentTO rateTO = new RateComponentTO();
					rateTO.setRateComponentId(rateDO.getRateComponentId());
					rateTO.setRateGlobalConfigValue(rateDO
							.getRateGlobalConfigValue());
					rateTO.setRateComponentCode(rateDO.getRateComponentCode());
					rateComponentTO.add(rateTO);
				}
			}
		LOGGER.trace("RateQuotationServiceImpl::loadDefaultRateComponentValue::END------------>:::::::");
		return rateComponentTO;
	}

	@Override
	public RateQuotaionRTOChargesTO saveOrUpdateRTOCharges(RateQuotaionRTOChargesTO rtoChargesTO)
			throws CGBusinessException, CGSystemException {
		RateQuotationTO quotationTO = new RateQuotationTO();
		RateQuotationDO quotationDO = new RateQuotationDO();
		RateQuotationRTOChargesDO rtoChargesDO = new RateQuotationRTOChargesDO();
		
				LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateRTOCharges::START------------>:::::::");
			quotationDO = rateQuotationDAO.getRateQuotation(rtoChargesTO.getRateQuotation().getRateQuotationNo(),rtoChargesTO.getRateQuotation().getQuotationUsedFor(),rtoChargesTO.getRateQuotation().getRateQuotationId());
			if(StringUtil.isNull(quotationDO)){
				quotationDO = new RateQuotationDO();
			}
			if(!StringUtil.isNull(quotationDO.getRateQuotationRtoChargesDO())){
				rtoChargesDO = quotationDO.getRateQuotationRtoChargesDO();
			}
			prepareQuotationRTOChargeDOList(rtoChargesTO, quotationDO,
					rtoChargesDO);
			
			if (!StringUtil.isNull(rtoChargesTO)) {
				quotationDO = rateQuotationDAO.saveOrUpdateRTOCharges(
						quotationDO, rtoChargesDO);
				rtoChargesTO.setSaved(Boolean.TRUE);
			}

			if (!StringUtil.isNull(quotationDO)) {
				quotationTO
						.setRateQuotationId(quotationDO.getRateQuotationId());
				quotationTO
						.setRateQuotationNo(quotationDO.getRateQuotationNo());
				rtoChargesTO.setRateQuotation(quotationTO);
			}

			LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateRTOCharges::END------------>:::::::");
		return rtoChargesTO;

	}

	private void prepareQuotationRTOChargeDOList(
			RateQuotaionRTOChargesTO rtoChargesTO, RateQuotationDO quotationDO,
			RateQuotationRTOChargesDO rtoChargesDO) {
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationRTOChargeDOList::START------------>:::::::");
		Integer updatedBy = rtoChargesTO.getRateQuotation().getUpdatedBy();
		if (!(StringUtil.isNull(rtoChargesTO.getRtoChargesApplicable()))) {
			if (rtoChargesTO.getRtoChargesApplicable().equals(RateQuotationConstants.ON)) {
				rtoChargesDO.setRtoChargeApplicable(CommonConstants.YES);
			}
		}else{
			rtoChargesDO.setRtoChargeApplicable(CommonConstants.NO);
		}
		
		rtoChargesDO.setRateComponentDO(RateCommonConstants.RTO_CODE);
		rtoChargesDO.setCreatedBy(updatedBy);
		rtoChargesDO.setUpdatedBy(updatedBy);	
		rtoChargesDO.setUpdatedDate(Calendar.getInstance().getTime());
		/*quotationDO.setUpdatedDate(DateUtil.getCurrentDate());
		quotationDO
				.setUpdatedBy(updatedBy);*/
		//quotationDO.setStatus(RateQuotationConstants.NEW);
		//quotationDO.setRateQuotationType(RateQuotationConstants.NORMAL);

		if (!(StringUtil.isNull(rtoChargesTO.getSameAsSlabRate()))) {
			if (rtoChargesTO.getSameAsSlabRate().equals(RateQuotationConstants.ON)) {
				rtoChargesDO.setSameAsSlabRate(CommonConstants.YES);
			}
		}else{
			rtoChargesDO.setSameAsSlabRate(CommonConstants.NO);
		}

		//if (!StringUtil.isNull(rtoChargesTO.getDiscountOnSlab())) {
			rtoChargesDO.setDiscountOnSlab(rtoChargesTO.getDiscountOnSlab());

		//}
		/*if (!StringUtil.isNull(rtoChargesTO.getRateQuotation()
				.getRateQuotationId())) {
			if (rtoChargesTO.getRateQuotation().getRateQuotationId() != 0) {
				RateQuotationDO rateQuotationDO = new RateQuotationDO();
				rateQuotationDO.setRateQuotationId(rtoChargesTO
						.getRateQuotation().getRateQuotationId());
				rtoChargesDO.setRateQuotationDO(rateQuotationDO);
				quotationDO.setRateQuotationId(rtoChargesTO.getRateQuotation()
						.getRateQuotationId());
			}
		}*/

		/*if (!(StringUtil.isNull(rtoChargesTO.getRateQuotation()
				.getRateQuotationNo()))) {
			quotationDO.setRateQuotationNo(rtoChargesTO.getRateQuotation()
					.getRateQuotationNo());
		}*/
			if (!(StringUtil.isStringEmpty(rtoChargesTO.getRateQuotation()
					.getExcecutiveRemarks()))) {
				quotationDO.setExcecutiveRemarks(rtoChargesTO
						.getRateQuotation().getExcecutiveRemarks());
			}else{
				quotationDO.setExcecutiveRemarks(null);
			}

			if (!(StringUtil.isStringEmpty(rtoChargesTO.getRateQuotation()
					.getApproversRemarks()))) {
				quotationDO.setApproversRemarks(rtoChargesTO
						.getRateQuotation().getApproversRemarks());
			}else{
				quotationDO.setApproversRemarks(null);
			}
		if(!StringUtil.isNull(quotationDO.getRateQuotationRtoChargesDO()) && !StringUtil.isNull(quotationDO.getRateQuotationRtoChargesDO().getRateQuotationRTOChargesId())){
			rtoChargesDO.setRateQuotationRTOChargesId(quotationDO.getRateQuotationRtoChargesDO().getRateQuotationRTOChargesId());
		}
		rtoChargesDO.setRateQuotationDO(quotationDO);
		quotationDO.setRateQuotationRtoChargesDO(rtoChargesDO);
		LOGGER.trace("RateQuotationServiceImpl::prepareQuotationRTOChargeDOList::END------------>:::::::");
	}

	@Override
	public List<RateTaxComponentTO> loadDefaultRateTaxComponentValue(
			Integer stateId) throws CGBusinessException, CGSystemException {
		List<RateTaxComponentTO> taxComponentTOs = null;
			LOGGER.trace("RateQuotationServiceImpl::loadDefaultRateTaxComponentValue::START------------>:::::::");
			taxComponentTOs = rateCommonService
					.loadDefaultRateTaxComponentValue(stateId);
		LOGGER.trace("RateQuotationServiceImpl::loadDefaultRateTaxComponentValue::END------------>:::::::");
		return taxComponentTOs;
	}

	public RateQuotaionRTOChargesTO loadRTOChargesDefault(String quotationId)
			throws CGBusinessException, CGSystemException {
		RateQuotaionRTOChargesTO quotaionRTOChargesTO = new RateQuotaionRTOChargesTO();
			LOGGER.trace("RateQuotationServiceImpl::loadRTOChargesDefault::START------------>:::::::");
			RateQuotationRTOChargesDO quotationRTOChargesDO = rateQuotationDAO
					.loadRTOChargesDefault(quotationId);
			if (!(StringUtil.isNull(quotationRTOChargesDO))) {
				quotaionRTOChargesTO.setDiscountOnSlab(quotationRTOChargesDO
						.getDiscountOnSlab());
				quotaionRTOChargesTO
						.setRtoChargesApplicable(quotationRTOChargesDO
								.getRtoChargeApplicable());
				quotaionRTOChargesTO.setSameAsSlabRate(quotationRTOChargesDO
						.getSameAsSlabRate());
				quotaionRTOChargesTO.setQuotaionRTOChargesId(quotationRTOChargesDO.getRateQuotationRTOChargesId());
			}
			LOGGER.trace("RateQuotationServiceImpl::loadRTOChargesDefault::END------------>:::::::");
		return quotaionRTOChargesTO;
	}

	@Override
	public RateQuotationTO copyQuotation(RateQuotationTO rateQuotationTO)
			throws CGBusinessException, CGSystemException {

		RateQuotationDO copyQuotationDO = null;
		//RateQuotationTO quotationTO = new RateQuotationTO();
		LOGGER.trace("RateQuotationServiceImpl::copyQuotation::START------------>:::::::");
			List<String> seqNOs = generateSequenceNo(
					Integer.parseInt(RateQuotationConstants.ONE),
					RateQuotationConstants.QUOTATION_NO);
			String quotationNo = getQuotationNo(rateQuotationTO, seqNOs);
			
			
			rateQuotationTO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
			copyQuotationDO = rateQuotationDAO.getRateQuotation(rateQuotationTO.getRateQuotationNo(),RateQuotationConstants.QUOTATION, rateQuotationTO.getRateQuotationId());
			
			
			copyQuotationDO = createCopyQuotation(copyQuotationDO, quotationNo, RateQuotationConstants.CHAR_Q);
			copyQuotationDO.setQuotationCreatedBy(rateQuotationTO.getUpdatedBy());
			copyQuotationDO.setQuotationCreatedDate(Calendar.getInstance().getTime());
			copyQuotationDO.setUpdatedBy(null);
			copyQuotationDO.setUpdatedDate(null);
			
			copyQuotationDO = rateQuotationDAO.copyQuotation(rateQuotationTO,
					quotationNo,copyQuotationDO);
			
			//rateQuotationTO.setSaved(Boolean.TRUE);

			if (!StringUtil.isNull(copyQuotationDO)) {
				rateQuotationTO.setSaved(Boolean.TRUE);
				rateQuotationTO.setRateQuotationId(copyQuotationDO
						.getRateQuotationId());
				rateQuotationTO.setRateQuotationNo(copyQuotationDO
						.getRateQuotationNo());
			}
		LOGGER.trace("RateQuotationServiceImpl::copyQuotation::END------------>:::::::");
		return rateQuotationTO;
	}
	@Override
	public List<CodChargeTO> getDeclaredValueCodCharge()
			throws CGBusinessException, CGSystemException {
		List<CodChargeTO> chargeTOs = null;
			LOGGER.trace("RateQuotationServiceImpl::getDeclaredValueCodCharge::START------------>:::::::");
			List<CodChargeDO> codChargeDOs = rateQuotationDAO
					.getDeclaredValueCodCharge();
			chargeTOs = new ArrayList<CodChargeTO>(codChargeDOs.size());

			if (!CGCollectionUtils.isEmpty(codChargeDOs)) {
				for (CodChargeDO chargeDO : codChargeDOs) {
					CodChargeTO chargeTO = new CodChargeTO();
					chargeTO.setCodChargeId(chargeDO.getCodChargeId());
					chargeTO.setMinimumDeclaredValue(chargeDO
							.getMinimumDeclaredValue());
					chargeTO.setMaximumDeclaredValue(chargeDO
							.getMaximumDeclaredValue());
					chargeTOs.add(chargeTO);
				}
			}
		LOGGER.trace("RateQuotationServiceImpl::getDeclaredValueCodCharge::END------------>:::::::");
		return chargeTOs;
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getAllOfficesByCity(loginCityId);
	}

	@Override
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return rateCommonService.getEmployeesOfOffice(officeTO);
	}

	@Override
	public RateQuotaionFixedChargesTO saveOrUpdateEcomerceFixedCharges(
			RateQuotaionFixedChargesTO fixedChargesTO,
			List<RateQuotationCODChargeTO> codList) throws CGBusinessException,
			CGSystemException {
		
		RateQuotationTO quotationTO = new RateQuotationTO();
		RateQuotationDO quotationDO = null;
		//List<RateQuotationCODChargeDO> quotationCOD = new ArrayList<RateQuotationCODChargeDO>();
		//Set<RateQuotationCODChargeDO> quotationCOD = new HashSet<RateQuotationCODChargeDO>();
		LOGGER.trace("RateQuotationServiceImpl::getDefaultApproveQuotationUIValues::START------------>:::::::");
		quotationDO=rateQuotationDAO.getRateQuotation(fixedChargesTO.getRateQuotation().getRateQuotationNo(),fixedChargesTO.getRateQuotation().getQuotationUsedFor(),fixedChargesTO.getRateQuotation().getRateQuotationId());
			prepareEcomerceQuotationFixedChargeDOList(fixedChargesTO,
					 quotationDO, codList);
			
			if (!StringUtil.isNull(fixedChargesTO)) {
			
				/*quotationDO = rateQuotationDAO
						.saveOrUpdateEcomerceFixedCharges(quotationDO,
								quotationCOD);*/
				quotationDO = rateQuotationDAO
						.saveOrUpdateBasicInfo(quotationDO);
				fixedChargesTO.setSaved(Boolean.TRUE);
			}

			if (!StringUtil.isNull(quotationDO)) {
				quotationTO
						.setRateQuotationId(quotationDO.getRateQuotationId());
				quotationTO
						.setRateQuotationNo(quotationDO.getRateQuotationNo());
				fixedChargesTO.setRateQuotation(quotationTO);
			}

		LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateEcomerceFixedCharges::END------------>:::::::");
		return fixedChargesTO;

	}

	/*private void prepareEcomerceQuotationFixedChargeDOList(
			RateQuotaionFixedChargesTO fixedChargesTO,
			List<RateQuotationCODChargeDO> quotationCOD,
			RateQuotationDO rateQuotationDO,
			List<RateQuotationCODChargeTO> codList) {*/
	private void prepareEcomerceQuotationFixedChargeDOList(
			RateQuotaionFixedChargesTO fixedChargesTO,
			RateQuotationDO rateQuotationDO,
			List<RateQuotationCODChargeTO> codList) {
		LOGGER.trace("RateQuotationServiceImpl::prepareEcomerceQuotationFixedChargeDOList::START------------>:::::::");
		Set<RateQuotationFixedChargesDO> rqFixedDO = new HashSet<RateQuotationFixedChargesDO>();
		Set<RateQuotationCODChargeDO> quotationCOD = new HashSet<RateQuotationCODChargeDO>();
		RateQuotationFixedChargesConfigDO conDO = new RateQuotationFixedChargesConfigDO();
		Set<RateQuotationFixedChargesConfigDO> configDO = new HashSet<RateQuotationFixedChargesConfigDO>();
		Integer updatedBy = fixedChargesTO.getRateQuotation().getUpdatedBy();
		boolean con = false;
		
		if (!(StringUtil.isNull(fixedChargesTO.getFuelSurchargeChk()))) {
			if (fixedChargesTO.getFuelSurchargeChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getFuelSurcharge());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE);
				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getOtherChargesChk()))) {
			if (fixedChargesTO.getOtherChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getOtherCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getAirportChargesChk()))) {
			if (fixedChargesTO.getAirportChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getAirportCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getServiceTaxChk()))) {
			if (fixedChargesTO.getServiceTaxChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getServiceTax());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getEduChargesChk()))) {
			if (fixedChargesTO.getEduChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getEduCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getHigherEduChargesChk()))) {
			if (fixedChargesTO.getHigherEduChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getHigherEduCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(fixedChargesTO.getStateTaxChk()))) {
			if (fixedChargesTO.getStateTaxChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				fixedChargesDO.setValue(fixedChargesTO.getStateTax());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(fixedChargesTO.getSurchargeOnSTChk()))) {
			if (fixedChargesTO.getSurchargeOnSTChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO.getSurchargeOnST());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);

				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getRiskSurchrgeChk()))) {
			if (fixedChargesTO.getRiskSurchrgeChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(Double
						.valueOf(fixedChargesTO.getRiskSurchrge().split(
								CommonConstants.TILD)[1]));
			
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_RISK_SURCHARGE);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getOctroiServiceChargesChk()))) {
			if (fixedChargesTO.getOctroiServiceChargesChk().equals(RateQuotationConstants.ON)) {
				RateQuotationFixedChargesDO fixedChargesDO = new RateQuotationFixedChargesDO();
				fixedChargesDO.setValue(fixedChargesTO
						.getOctroiServiceCharges());
				fixedChargesDO
						.setRateComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE);

				fixedChargesDO.setRateQuotationDO(rateQuotationDO);
				fixedChargesDO.setCreatedBy(updatedBy);
				fixedChargesDO.setUpdatedBy(updatedBy);
				rqFixedDO.add(fixedChargesDO);
			}
		}
		
		if (!(StringUtil.isNull(fixedChargesTO.getOctroiBourneByChk()))) {
			if (fixedChargesTO.getOctroiBourneByChk().equals(RateQuotationConstants.ON)) {
				conDO.setOctroiBourneBy(fixedChargesTO.getOctroiBourneBy());
				//rateQuotationDO.setFixedChargesConfigDO(configDO);
				conDO.setRateQuotationDO(rateQuotationDO);
				con = true;
			}
		}

		
		if (!(StringUtil.isNull(fixedChargesTO.getRiskSurchrgeChk()))) {
			if (fixedChargesTO.getRiskSurchrgeChk().equals(RateQuotationConstants.ON)) {
				fixedChargesTO.setRiskSurchrge(fixedChargesTO.getRiskSurchrge()
						.split(CommonConstants.TILD)[0]);
				conDO.setInsuredBy(fixedChargesTO.getRiskSurchrge());
				//rateQuotationDO.setFixedChargesConfigDO(configDO);
				conDO.setRateQuotationDO(rateQuotationDO);
				con = true;
			}
		}
		if(con){
			conDO.setCreatedBy(updatedBy);
			conDO.setUpdatedBy(updatedBy);	
			configDO.add(conDO);
		}
		if(!CGCollectionUtils.isEmpty(codList)){
			prepareCODChargesList(fixedChargesTO, codList, rateQuotationDO, quotationCOD);
		}
		/*RateQuotationCODChargeDO quotationCodDO = null;
		int codLen = codList.size();
		for (int i = 0; i < codLen; i++) {
			quotationCodDO = new RateQuotationCODChargeDO();

			CodChargeDO codChargeDO = new CodChargeDO();
			codChargeDO.setCodChargeId(codList.get(i).getCodChargeId());
			quotationCodDO.setCodChargeDO(codChargeDO);

			quotationCodDO.setPercentileValue(codList.get(i)
					.getPercentileValue());
			quotationCodDO.setFixedChargeValue(codList.get(i)
					.getFixedChargeValue());
			quotationCodDO.setConsiderFixed(codList.get(i).getConsiderFixed());
			quotationCodDO.setConsiderHigherFixedOrPercent(codList.get(i)
					.getConsideeHigherFixedPercent());
			quotationCodDO.setRateQuotationDO(rateQuotationDO);
			quotationCOD.add(quotationCodDO);
		}*/
		rateQuotationDO.setCodChargeDO(quotationCOD);
		if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getExcecutiveRemarks()))) {
			rateQuotationDO.setExcecutiveRemarks(fixedChargesTO
					.getRateQuotation().getExcecutiveRemarks());
		}else{
		rateQuotationDO.setExcecutiveRemarks(null);
		}
		if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getApproversRemarks()))) {
			rateQuotationDO.setApproversRemarks(fixedChargesTO
					.getRateQuotation().getApproversRemarks());
		}else{
		rateQuotationDO.setApproversRemarks(null);
		}
		/*if (!(StringUtil.isNull(fixedChargesTO.getRateQuotation()
				.getRateQuotationNo()))) {
			rateQuotationDO.setRateQuotationNo(fixedChargesTO
					.getRateQuotation().getRateQuotationNo());
		}*/
		rateQuotationDO.setFixedChargesConfigDO(configDO);
		rateQuotationDO.setRateFixedChargeDO(rqFixedDO);
		rateQuotationDO.setQuotationCreatedDate(DateUtil.getCurrentDate());
		rateQuotationDO.setCreatedBy(fixedChargesTO.getRateQuotation()
				.getCreatedBy());
		//rateQuotationDO.setStatus(RateQuotationConstants.NEW);
		//rateQuotationDO.setRateQuotationType(RateQuotationConstants.ECOMMERCE);
		LOGGER.trace("RateQuotationServiceImpl::prepareEcomerceQuotationFixedChargeDOList::END------------>:::::::");
	}

	@Override
	public List<RateQuotationCODChargeTO> loadQuotationCodCharge(
			String quotationId) throws CGBusinessException, CGSystemException {
		List<RateQuotationCODChargeTO> codChargeTOs = null;
			LOGGER.trace("RateQuotationServiceImpl::loadQuotationCodCharge::START------------>:::::::");
			Integer rateQuotationId = Integer.parseInt(quotationId);

			List<RateQuotationCODChargeDO> codChargeDOs = rateQuotationDAO
					.loadQuotationCodCharge(rateQuotationId);

			codChargeTOs = new ArrayList<RateQuotationCODChargeTO>(
					codChargeDOs.size());
			if (!StringUtil.isEmptyList(codChargeDOs)) {
				for (RateQuotationCODChargeDO do1 : codChargeDOs) {
					RateQuotationCODChargeTO codChargeTO = new RateQuotationCODChargeTO();

					codChargeTO.setConsideeHigherFixedPercent(do1
							.getConsiderHigherFixedOrPercent());
					codChargeTO.setCodChargeId(do1.getCodChargeDO()
							.getCodChargeId());
					codChargeTO.setFixedChargeValue(do1.getFixedChargeValue());

					codChargeTO.setConsiderFixed(do1.getConsiderFixed());
					codChargeTO.setPercentileValue(do1.getPercentileValue());

					codChargeTOs.add(codChargeTO);
				}
			}

		LOGGER.trace("RateQuotationServiceImpl::loadQuotationCodCharge::END------------>:::::::");
		return codChargeTOs;
	}

	@Override
	public RateContractTO createContract(RateQuotationTO rateQuotationTO,
			RateContractTO rateContractTO)
			throws CGSystemException, CGBusinessException {
		RateContractDO contractDO = null;
		RateContractTO contractTO = new RateContractTO();
		LOGGER.trace("RateQuotationServiceImpl::createContract::START------------>:::::::");
			List<String> seqNOs = generateSequenceNo(
					Integer.parseInt(RateContractConstants.ONE),
					RateContractConstants.CONTRACT_NO);
			String contractNo = getContractNo(rateQuotationTO, seqNOs);
			if(StringUtil.isStringEmpty(contractNo)){
				ExceptionUtil.prepareBusinessException(RateErrorConstants.VALIDATE_SEQ_GEN_ERROR, new String[]{RateQuotationConstants.RATE_CONTRACT_NO});
			}
		
			RateQuotationDO copyQuotationDO = null;
			contractDO = new RateContractDO();
			copyQuotationDO = rateQuotationDAO.getRateQuotation(rateQuotationTO.getRateQuotationNo(),RateQuotationConstants.QUOTATION,rateQuotationTO.getRateQuotationId());
			
			copyQuotationDO = createCopyQuotation(copyQuotationDO, rateQuotationTO.getRateQuotationNo(), RateQuotationConstants.CHAR_Q);
			CustomerDO custDO = new CustomerDO();
			try{
				PropertyUtils.copyProperties(custDO, copyQuotationDO.getQuotedCustomer());
			}catch(Exception e){
				throw new CGBusinessException(e);
			}
			
			copyQuotationDO.setCustomer(custDO);
			copyQuotationDO.setQuotedCustomer(null);
			//copyQuotationDO.setRateContractDO(contractDO);
			contractDO.setRateQuotationDO(copyQuotationDO);
			contractDO.setRateContractNo(contractNo);
			contractDO.setCustomerId(copyQuotationDO.getCustomer().getCustomerId());
			contractDO.setCreatedBy(rateQuotationTO.getUpdatedBy());
			contractDO.setCreatedDate(DateUtil.getCurrentDate());
			if(copyQuotationDO.getRateQuotationType().equals(RateQuotationConstants.NORMAL)){
				contractDO.setRateContractType(RateContractConstants.NORMAL_CONTRACT);
			}else{
				contractDO.setRateContractType(RateContractConstants.ECCOMERCE_CONTRACT);	
			}
			contractDO.setContractStatus(RateContractConstants.CREATED);
			
			contractDO = rateQuotationDAO.createContract(rateQuotationTO,
					contractNo, rateQuotationTO.getRateQuotationNo(),contractDO);
			
			if (!StringUtil.isNull(contractDO)) {
				contractTO.setRateContractId(contractDO.getRateContractId());
				contractTO.setRateContractNo(contractDO.getRateContractNo());
				contractTO.setCustomerId(contractDO.getCustomerId());

				RateQuotationTO rateQuotation=new RateQuotationTO();
				rateQuotation.setRateQuotationId(contractDO.getRateQuotationDO().getRateQuotationId());
				rateQuotation.setRateQuotationNo(contractDO.getRateQuotationDO().getRateQuotationNo());
				rateQuotation.setSaved(Boolean.TRUE);
				contractTO.setRateQuotationTO(rateQuotation);
			}
		LOGGER.trace("RateQuotationServiceImpl::createContract::END------------>:::::::");
		return contractTO;
	}

	private String getContractNo(RateQuotationTO rateQuotationTO,
			List<String> seqNOs) {
		LOGGER.trace("RateQuotationServiceImpl::getDefaultApproveQuotationUIValues::START------------>:::::::");
		String quoNo = RateContractConstants.CHAR_C
				+ rateQuotationTO.getLoginOfficeCode() + seqNOs.get(0);
		LOGGER.trace("RateQuotationServiceImpl::getDefaultApproveQuotationUIValues::START------------>:::::::");
		return quoNo;
		
	}
	
	@Override
	public RateQuotationProposedRatesTO saveOrUpdateSlabRateDeatails(
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO, RateQuotationTO rqTO) throws CGBusinessException, CGSystemException {
		//boolean rbmStatus = Boolean.FALSE;
		//String status = CommonConstants.FAILURE;
		Boolean weightSlab = Boolean.FALSE;
		String approvalLevel = "";
	
			LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateSlabRateDeatails::START------------>:::::::");
		if (!StringUtil.isNull(rateQuotationProposedRatesTO)) {
			RateQuotationDO rqDO = null;
			rqDO = rateQuotationDAO.getRateQuotation(rqTO.getRateQuotationNo(),rqTO.getQuotationUsedFor(),rqTO.getRateQuotationId());
			if(StringUtil.isNull(rqDO)){
				rqDO = new RateQuotationDO();
			}
			RateQuotationProductCategoryHeaderDO rqpchDO = new RateQuotationProductCategoryHeaderDO();
			Integer orgSector;
			
			orgSector = rateQuotationProposedRatesTO.getRateOriginSectorId();
			
			Integer prodId = rateQuotationProposedRatesTO.getRateProdCatId();
			
			int wLen = rateQuotationProposedRatesTO.getRateWtId().length;
			int w = 0;
			for (int l = 0; l < wLen; l++) {
				if (rateQuotationProposedRatesTO.getRateWtId()[l].equals(prodId))
					break;
				else
					w++;
			}
			
			if(StringUtil.isEmptyInteger(rateQuotationProposedRatesTO.getRateWtSlabId()[w]))
				weightSlab = Boolean.TRUE;
			
			domainConverterRateQuotProposedRatesTO2RateQuotProdHeaderDO(rateQuotationProposedRatesTO,
					rqpchDO,weightSlab,rqDO, orgSector, rqTO.getUpdatedBy());
			if (!StringUtil.isNull(rqDO)) {
				
				/*if(!StringUtil.isNull(rateQuotationProposedRatesTO.getModuleType()) && rateQuotationProposedRatesTO.getModuleType().equals("C"))
				{
					if(rqTO.getRateQuotationType().equals(RateQuotationConstants.NORMAL)
						&& !(rateQuotationProposedRatesTO.getIndCatCode().split(CommonConstants.TILD)[2].equals("FR"))
						&& !rqTO.getQuotationCreatedFrom().equals("L")){ 
					Set<RateQuotationProductCategoryHeaderDO> rqpchDOSet = new HashSet<RateQuotationProductCategoryHeaderDO>();
					Set<RateQuotationProductCategoryHeaderDO> pchDOSet = rqDO.getRateQuotationProductCategoryHeaderDO();
					for(RateQuotationProductCategoryHeaderDO pchDO : pchDOSet){
						if(pchDO.getRateQuotationProductCategoryHeaderId().equals(rateQuotationProposedRatesTO.getRateQuotationProdCatHeaderId())){
							rqpchDO = pchDO;
							break;
						}
					}
					rqpchDOSet.add(rqpchDO);
					RateQuotationDO rDO = new RateQuotationDO();
					CustomerDO custDO = new CustomerDO();
					RateIndustryCategoryDO ricDO = new RateIndustryCategoryDO();
					RateCustomerCategoryDO rccDO = new RateCustomerCategoryDO();
					ricDO.setRateIndustryCategoryId(Integer.parseInt(rateQuotationProposedRatesTO.getIndCatCode().split(CommonConstants.TILD)[0]));
					rccDO.setRateCustomerCategoryId(Integer.parseInt(rateQuotationProposedRatesTO.getIndCatCode().split(CommonConstants.TILD)[1]));
					custDO.setCustomerCategoryDO(rccDO);
					custDO.setIndustryCategoryDO(ricDO);
					rDO.setCustomer(custDO);
					rDO.setRateQuotationProductCategoryHeaderDO(rqpchDOSet);
					approvalLevel = getApprovalLevel(rDO, rateQuotationProposedRatesTO.getRegionId());
					}
				}*/	
				if(StringUtil.isStringEmpty(approvalLevel)){
				
				//else if(rateQuotationProposedRatesTO.getRateAddWtSlabId() == 0 || StringUtil.isNull(rateQuotationProposedRatesTO.getRateAddWtSlabId()))
					//weightSlab = Boolean.TRUE;
				//Set<RateQuotationProductCategoryHeaderDO> rqpchDOset = new HashSet<RateQuotationProductCategoryHeaderDO>();
				//rqpchDOset.add(rqpchDO);
				//rqDO.setRateQuotationProductCategoryHeaderDO(rqpchDOset);

				
				if(!StringUtil.isNull(rqTO.getRateQuotationId()) && rqTO.getRateQuotationId()!= 0 )
				{
					rqDO.setRateQuotationId(rqTO.getRateQuotationId());
				}else{
					List<String> seqNOs = generateSequenceNo(
							Integer.parseInt(RateQuotationConstants.ONE),
							RateQuotationConstants.QUOTATION_NO);
					String quotationNo = getQuotationNo(rqTO, seqNOs);
					rqDO.setRateQuotationNo(quotationNo);
					rqDO.setCreatedDate(DateUtil.getCurrentDate());
					rqDO.setUserId(rqTO.getUserId());
					rqDO.setStatus(RateQuotationConstants.NEW);
					rqDO.setRateQuotationType(rqTO.getRateQuotationType());
				}
				//rqpchDO.setRateQuotationDO(rqDO);
				
				/* The below method is added to prevent duplicate rate product headers from getting inserted in the database */
				filterDuplicateProductCategoryHeaderEntries(rqDO);
				
				rqDO = rateQuotationDAO.saveOrUpdateBasicInfo(rqDO);
				
				if(!StringUtil.isNull(rqDO) && !StringUtil.isEmptyInteger(rqDO.getRateQuotationId())){
					
					if(!CGCollectionUtils.isEmpty(rqDO.getRateQuotationProductCategoryHeaderDO())){
						Set<RateQuotationProductCategoryHeaderDO> rpqDOSet = rqDO.getRateQuotationProductCategoryHeaderDO();
						for(RateQuotationProductCategoryHeaderDO rpDO : rpqDOSet){
							if(rpDO.getRateProductCategory().getRateProductCategoryId().equals(rateQuotationProposedRatesTO.getRateProdCatId())){
									rateQuotationProposedRatesTO =  new RateQuotationProposedRatesTO();
									rateQuotationProposedRatesTO.setSaved(Boolean.TRUE);
							getRateQuotProposedRateDetailsByProdHeader(
									rateQuotationProposedRatesTO, rpDO.getRateProductCategory().getRateProductCategoryId(),rpDO);
							break;
							}
						}
					}
				  }	
				}else{
					rateQuotationProposedRatesTO.setSlabRateStatus(RateQuotationConstants.FAILURE);
					ExceptionUtil.prepareBusinessException(RateErrorConstants.DATA_NOT_SAVED_SLAB_RATES_ARE_LOWER);
				}
			}
			
		}
		LOGGER.trace("RateQuotationServiceImpl::saveOrUpdateSlabRateDeatails::END------------>:::::::");
		return rateQuotationProposedRatesTO;
	}
	
	private void filterDuplicateProductCategoryHeaderEntries(RateQuotationDO rateQuotationDo) throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationServiceImpl::filterDuplicateProductCategoryHeaderEntries::START");
		Integer courier = 0;
		Integer airCargo = 0;
		Integer train = 0;
		Integer ecommerce = 0;
		Integer priority = 0;
		Integer emotionalBond = 0;
		Set<RateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDoSet = rateQuotationDo.getRateQuotationProductCategoryHeaderDO();
		for (RateQuotationProductCategoryHeaderDO productCategoryHeaderDo : rateQuotationProductCategoryHeaderDoSet) {
			Integer rateProductCategoryId = productCategoryHeaderDo.getRateProductCategory().getRateProductCategoryId();
			RateProductCategoryDO rateProductCategoryDo = rateQuotationDAO.getRateProductDetailsFromRateProductId(rateProductCategoryId);
			if (StringUtil.isNull(rateProductCategoryDo)) {
				throw new CGBusinessException(RateErrorConstants.QUOTATION_NOT_SUBMITTED_SUCCESSFULLY);
			}
			
			String rateProductCategoryCode = rateProductCategoryDo.getRateProductCategoryCode();
			
			if (RateCommonConstants.PRO_CODE_COURIER.equals(rateProductCategoryCode)) {
				courier++;
				if (courier > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else if (RateCommonConstants.PRO_CODE_AIR_CARGO.equals(rateProductCategoryCode)) {
				airCargo++;
				if (airCargo > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else if (RateCommonConstants.PRO_CODE_TRAIN.equals(rateProductCategoryCode)) {
				train++;
				if (train > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else if (RateCommonConstants.PRO_CODE_ECOMMERCE.equals(rateProductCategoryCode)) {
				ecommerce++;
				if (ecommerce > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else if (RateCommonConstants.PRO_CODE_PRIORITY.equals(rateProductCategoryCode)) {
				priority++;
				if (priority > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else if (RateCommonConstants.PRO_CODE_EMOTIONAL_BOND.equals(rateProductCategoryCode)) {
				emotionalBond++;
				if (emotionalBond > 1) {
					rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
				}
			}
			else {
				rateQuotationProductCategoryHeaderDoSet.remove(productCategoryHeaderDo);
			}
		} // End of for loop
		LOGGER.trace("RateQuotationServiceImpl::filterDuplicateProductCategoryHeaderEntries::END");
	}
	
	private void domainConverterRateQuotProposedRatesTO2RateQuotProdHeaderDO(
			
			//RateQuotationProposedRatesTO rqprTO, RateQuotationDO rqDO)  throws CGBusinessException, CGSystemException{
			RateQuotationProposedRatesTO rqprTO, RateQuotationProductCategoryHeaderDO rqpchDO, Boolean weightSlab, RateQuotationDO rqDO, Integer orgSector, Integer updatedBy)  
					throws CGBusinessException, CGSystemException {
		LOGGER.trace("RateQuotationServiceImpl::DomainConverterRateQuotProposedRatesTO2RateQuotProdHeaderDO::START------------>:::::::");
		/*if (!StringUtil.isNull(rqprTO.getRateQuotationId())
				&& rqprTO.getRateQuotationId() != 0) {
			
			RateQuotationDO rqDO = new RateQuotationDO();
			rqDO.setRateQuotationId(rqprTO.getRateQuotationId());
			rqpchDO.setRateQuotationDO(rqDO);
		}*/
		Set<RateQuotationProductCategoryHeaderDO> rqpchDOSet = new HashSet<RateQuotationProductCategoryHeaderDO>();
		Set<RateQuotationWeightSlabDO> rqwsDOSet = new HashSet<RateQuotationWeightSlabDO>();
		Set<RateQuotationSlabRateDO> rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
		Set<RateQuotationSpecialDestinationDO> rqsdDOSet = new HashSet<RateQuotationSpecialDestinationDO>();
		
		if(!CGCollectionUtils.isEmpty(rqDO.getRateQuotationProductCategoryHeaderDO())) {
			rqpchDOSet = new CopyOnWriteArraySet<RateQuotationProductCategoryHeaderDO>(rqDO.getRateQuotationProductCategoryHeaderDO());
			for(RateQuotationProductCategoryHeaderDO pchDO : rqpchDOSet) {
				if(!StringUtil.isEmptyInteger(rqprTO.getRateQuotationProdCatHeaderId())){
					if(pchDO.getRateQuotationProductCategoryHeaderId().equals(rqprTO.getRateQuotationProdCatHeaderId())){
						rqpchDO = pchDO;
						if(StringUtil.isStringEmpty(rqprTO.getRateConfiguredType())){
							rqpchDOSet.remove(pchDO);
						}
						if(weightSlab){
							if(StringUtil.isStringEmpty(rqprTO.getRateConfiguredType())){
								rqpchDO.setRateQuotationWeightSlabDO(rqwsDOSet);
							}else{
								rqwsDOSet = new CopyOnWriteArraySet<RateQuotationWeightSlabDO>(rqpchDO.getRateQuotationWeightSlabDO());
								for(RateQuotationWeightSlabDO rqwsDO : rqwsDOSet){
									if(rqprTO.getRateConfiguredType().equals(rqwsDO.getRateConfiguredType())){
										rqwsDOSet.remove(rqwsDO);
									}
								}
								rqpchDO.setRateQuotationWeightSlabDO(rqwsDOSet);
							}
						}
						if(!CGCollectionUtils.isEmpty(rqpchDO.getRateQuotationWeightSlabDO())){
							rqwsDOSet = rqpchDO.getRateQuotationWeightSlabDO();
							for(RateQuotationWeightSlabDO rqwsDO : rqwsDOSet){
								if(!CGCollectionUtils.isEmpty(rqwsDO.getRateQuotationSlabRateDO())){
									rqsrDOSet = new CopyOnWriteArraySet<RateQuotationSlabRateDO>(rqwsDO.getRateQuotationSlabRateDO());
									for(RateQuotationSlabRateDO rqsrDO : rqsrDOSet){
										if(!StringUtil.isEmptyInteger(orgSector)){
											if(rqsrDO.getOriginSector().equals(orgSector)){
												rqsrDOSet.remove(rqsrDO);
											}
										}else{
											if(StringUtil.isStringEmpty(rqprTO.getRateConfiguredType()) || (!StringUtil.isStringEmpty(rqprTO.getRateConfiguredType()) && rqprTO.getRateConfiguredType().equals(rqsrDO.getRateConfiguredType()))){
												rqsrDOSet.remove(rqsrDO);
											}
										}
									}
									if(!CGCollectionUtils.isEmpty(rqsrDOSet)){
										rqwsDO.setRateQuotationSlabRateDO(rqsrDOSet);
									}else{
										rqwsDO.setRateQuotationSlabRateDO(new HashSet<RateQuotationSlabRateDO>());
									}
								}
								if(!CGCollectionUtils.isEmpty(rqwsDO.getRateQuotationSpecialDestinationDO())){
									rqsdDOSet = new CopyOnWriteArraySet<RateQuotationSpecialDestinationDO>(rqwsDO.getRateQuotationSpecialDestinationDO());
									for(RateQuotationSpecialDestinationDO rqsdDO : rqsdDOSet){
										if(!StringUtil.isEmptyInteger(orgSector)){
											if(rqsdDO.getOriginSector().equals(orgSector)){
												rqsdDOSet.remove(rqsdDO);
											}
										}else{
											if((StringUtil.isStringEmpty(rqprTO.getRateConfiguredType())) || (!StringUtil.isStringEmpty(rqprTO.getRateConfiguredType()) && rqprTO.getRateConfiguredType().equals(rqsdDO.getRateConfiguredType()))){
												rqsdDOSet.remove(rqsdDO);
											}
										}
									}
									if(!CGCollectionUtils.isEmpty(rqsdDOSet)){
										rqwsDO.setRateQuotationSpecialDestinationDO(rqsdDOSet);
									}else{
										rqwsDO.setRateQuotationSpecialDestinationDO(new HashSet<RateQuotationSpecialDestinationDO>());
									}
								}
							}
						}
					}
				}
			}
		}
		
		//flat rate
		rqpchDO.setFlatRate(rqprTO.getFlatRate());
		
		RateProductCategoryDO rpcDO = new RateProductCategoryDO();
		rpcDO.setRateProductCategoryId(rqprTO.getRateProdCatId());
		
		//RateQuotationProductCategoryHeaderDO rqpchDO = new RateQuotationProductCategoryHeaderDO();
		if (!StringUtil.isNull(rqprTO.getRateQuotationProdCatHeaderId())) {
			rqpchDO.setRateQuotationProductCategoryHeaderId(rqprTO.getRateQuotationProdCatHeaderId());
		}
		
		if (StringUtil.isNull(rqpchDO.getCreatedBy())) {
			rqpchDO.setCreatedBy(updatedBy);
		}
		rqpchDO.setUpdatedBy(updatedBy);
		rqpchDO.setUpdatedDate(Calendar.getInstance().getTime());
		
		if (!StringUtil.isNull(rqprTO.getRateMinChargWtId())
				&& rqprTO.getRateMinChargWtId() != 0) {
			RateMinChargeableWeightDO rmcwDO = new RateMinChargeableWeightDO();
			rmcwDO.setRateMinChargeableWeightId(rqprTO.getRateMinChargWtId());
			rqpchDO.setMinimumChargeableWeightDO(rmcwDO);
		} else {
			rqpchDO.setMinimumChargeableWeightDO(null);
		}
		
		rqpchDO.setVobSlab(rqprTO.getRateVobSlabsId());
		rqpchDO.setRateProductCategory(rpcDO);

		//Set<RateQuotationProductCategoryHeaderDO> rqpchDOSet = new HashSet<RateQuotationProductCategoryHeaderDO>();
		
		//rqpchDOSet.add(rqpchDO);
		
		//rqDO.setRateQuotationProductCategoryHeaderDO(rqpchDOSet);
		
		
		int wtLen = 5; 
		
		//Set<RateQuotationSlabRateDO> rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
		//Set<RateQuotationWeightSlabDO> rqwsDOSet = new HashSet<RateQuotationWeightSlabDO>();
	//	Set<RateQuotationSpecialDestinationDO> rqsdDOSet = new HashSet<RateQuotationSpecialDestinationDO>();
		
		
		String[] sector = rqprTO.getSecArrStr().split(RateQuotationConstants.RATE_COMMA);
		
		int k = 0;
		int m = 0;
		int n = 0;
		int w = 0;
		int order = 1;
		int destOrder = 1;
		int pLen = rqprTO.getRateProdId().length;
		Integer prodId = rqprTO.getRateProdCatId();
		for (int l = 0; l < pLen; l++) {
			if (rqprTO.getRateProdId()[l].equals(prodId))
				break;
			else
				k++;
		}
		
		int wLen = rqprTO.getRateWtId().length;
		for (int l = 0; l < wLen; l++) {
			if (rqprTO.getRateWtId()[l].equals(prodId))
				break;
			else
				w++;
		}
		
		m = k;
		
		Integer originSector = null;
		if (!StringUtil.isNull(rqprTO.getRateOriginSectorId())
				&& rqprTO.getRateOriginSectorId() != 0) {
			originSector = rqprTO.getRateOriginSectorId();
		}
		
		int d = 0;
		int destLen = rqprTO.getDestProdCat().length;
		for (int l = 0; l < destLen; l++) {
			if (rqprTO.getDestProdCat()[l].equals(prodId))
				break;
			else
				n++;
		}
		
		d = n;
		
		
		int pin = 0;
		int pinLen = rqprTO.getPinProdCat().length;
		for (int l = 0; l < pinLen; l++) {
			if (rqprTO.getPinProdCat()[l].equals(prodId))
				break;
			else
				pin++;
		}
		
		int splLen = rqprTO.getRowNo();
		RateQuotationWeightSlabDO rqwsDO = null;
		
		if(!rqprTO.getRateProdCatCode().equals("CO")){
		for(int i=0;i<wtLen;i++){
			k = m+i;
			n = d+i;
			rqwsDO = null;
			if(i<=(wtLen-2)){
				if(!StringUtil.isEmptyInteger(rqprTO.getRateQuotEndWeight()[w])){
					
					if(!CGCollectionUtils.isEmpty(rqwsDOSet)){
						for(RateQuotationWeightSlabDO qwsDO : rqwsDOSet){
							if(!(StringUtil.isEmptyInteger(rqprTO.getRateWtSlabId()[w]))
									&& qwsDO.getRateQuotationWeightSlabId().equals(rqprTO.getRateWtSlabId()[w])){
								rqwsDO = qwsDO;
								rqsrDOSet = rqwsDO.getRateQuotationSlabRateDO();
								rqsdDOSet = rqwsDO.getRateQuotationSpecialDestinationDO();
								break;
							}
						}
					}
					if(StringUtil.isNull(rqwsDO)){
						rqwsDO = new RateQuotationWeightSlabDO();
					}
					if (StringUtil.isNull(rqwsDO.getCreatedBy())) {
						rqwsDO.setCreatedBy(updatedBy);
					}
					rqwsDO.setUpdatedBy(updatedBy);
					rqwsDO.setUpdatedDate(Calendar.getInstance().getTime());
					/*if(!(StringUtil.isEmptyInteger(rqprTO.getRateWtSlabId()[w])))
						rqwsDO.setRateQuotationWeightSlabId(rqprTO.getRateWtSlabId()[w]);*/
					
					
					WeightSlabDO stwsDO = new WeightSlabDO();
					stwsDO.setWeightSlabId(rqprTO.getRateQuotStartWeight()[i]);

					WeightSlabDO endwsDO = new WeightSlabDO();
					endwsDO.setWeightSlabId(rqprTO.getRateQuotEndWeight()[w]);
					w++;
					
					
					rqwsDO.setStartWeight(stwsDO);
					rqwsDO.setEndWeight(endwsDO);
					rqwsDO.setOrder(order);
					order++;
					rqwsDO.setRateQuotProductCategoryHeaderDO(rqpchDO);
					
					//rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
					int sectorLength = sector.length;
					for (int j = 0; j < sectorLength; j++) {
						if(!StringUtil.isEmptyDouble(rqprTO.getRate()[k])){
					
						RateQuotationSlabRateDO rqsrDO = new RateQuotationSlabRateDO();
						rqsrDO.setCreatedBy(updatedBy);
						rqsrDO.setUpdatedBy(updatedBy);
						if (!StringUtil.isNull(originSector)){
							rqsrDO.setOriginSector(originSector);
						}
						rqsrDO.setDestinationSector(Integer.parseInt(sector[j]));
						rqsrDO.setRateQuotationWeightSlabDO(rqwsDO);
						rqsrDO.setRate(rqprTO.getRate()[k]);
						rqsrDO.setValueFromROI(rqprTO.getIsROI()[k]);
						rqsrDO.setRateQuotationProductCategoryHeader(rqpchDO);
						rqsrDOSet.add(rqsrDO);
						}
						k = k+5;
					}
					
					/*if((rqprTO.getRateProdCatCode().equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_CODE) 
							|| rqprTO.getRateProdCatCode().equals(RateCommonConstants.RATE_BENCH_MARK_ECOM_PROD_CAT_CODE))
							&& */
					if(splLen>0){
						destOrder = 1;
					//rqsdDOSet = new HashSet<RateQuotationSpecialDestinationDO>();
						for (int j = 0; j < splLen; j++) {
							if(!StringUtil.isEmptyInteger(rqprTO.getStateId()[j+pin]) &&  !StringUtil.isEmptyDouble(rqprTO.getSpecialDestRate()[n])){
								
								RateQuotationSpecialDestinationDO rqsdDO = new RateQuotationSpecialDestinationDO();
								
								rqsdDO.setCreatedBy(updatedBy);
								rqsdDO.setUpdatedBy(updatedBy);
								rqsdDO.setStateId(rqprTO.getStateId()[j+pin]);
								if(!StringUtil.isEmptyInteger(rqprTO.getCityId()[j+pin])){
									CityDO cityDO = new CityDO();
									cityDO.setCityId(rqprTO.getCityId()[j+pin]);
									rqsdDO.setSpecialDestinationCityDO(cityDO);
								}
								if (!StringUtil.isEmptyInteger(originSector)){
									rqsdDO.setOriginSector(originSector);
								}
								rqsdDO.setRate(rqprTO.getSpecialDestRate()[n]);
								rqsdDO.setRateQuotationWeightSlabDO(rqwsDO);
								rqsdDO.setRateQuotationProductCategoryHeaderDO(rqpchDO);
								rqsdDO.setOrder(destOrder);
								rqsdDOSet.add(rqsdDO);
								n = n+5;
								destOrder++;
							}else if(splLen>1){
								n = n+5;
							}
						}
					}
					rqwsDO.setRateQuotationSpecialDestinationDO(rqsdDOSet);
					rqwsDO.setRateQuotationSlabRateDO(rqsrDOSet);
					rqwsDOSet.add(rqwsDO);
				}
			}
			if(i==(wtLen-1)){
				if(!StringUtil.isEmptyInteger(rqprTO.getRateQuotAddWeight())){
					if(!CGCollectionUtils.isEmpty(rqwsDOSet)){
						for(RateQuotationWeightSlabDO qwsDO : rqwsDOSet){
							if(!(StringUtil.isEmptyInteger(rqprTO.getRateAddWtSlabId()))
									&& qwsDO.getRateQuotationWeightSlabId().equals(rqprTO.getRateAddWtSlabId())){
								rqwsDO = qwsDO;
								rqsrDOSet = rqwsDO.getRateQuotationSlabRateDO();
								rqsdDOSet = rqwsDO.getRateQuotationSpecialDestinationDO();
								break;
							}
								
						}
					}
					if(StringUtil.isNull(rqwsDO)){
						rqwsDO = new RateQuotationWeightSlabDO();
					}
				
				if(!(StringUtil.isEmptyInteger(rqprTO.getRateAddWtSlabId())))
					rqwsDO.setRateQuotationWeightSlabId(rqprTO.getRateAddWtSlabId());
				if(StringUtil.isEmptyInteger(rqwsDO.getCreatedBy())){
					rqwsDO.setCreatedBy(updatedBy);
				}
				rqwsDO.setUpdatedBy(updatedBy);
				rqwsDO.setUpdatedDate(Calendar.getInstance().getTime());
				
				WeightSlabDO stwsDO = new WeightSlabDO();
				stwsDO.setWeightSlabId(rqprTO.getRateQuotAddWeight());

				rqwsDO.setOrder(order);
				order++;
				rqwsDO.setStartWeight(stwsDO);
				
				rqwsDO.setRateQuotProductCategoryHeaderDO(rqpchDO);
				
				//rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
				int sectorLen = sector.length;
				for (int j = 0; j < sectorLen; j++) {
					if(!StringUtil.isEmptyDouble(rqprTO.getRate()[k])){
				
				RateQuotationSlabRateDO rqsrDO = new RateQuotationSlabRateDO();
				rqsrDO.setCreatedBy(updatedBy);
				rqsrDO.setUpdatedBy(updatedBy);
				if (!StringUtil.isNull(originSector))
					rqsrDO.setOriginSector(originSector);
				rqsrDO.setDestinationSector(Integer.parseInt(sector[j]));
				rqsrDO.setRateQuotationWeightSlabDO(rqwsDO);
				rqsrDO.setRate(rqprTO.getRate()[k]);
				rqsrDO.setValueFromROI(rqprTO.getIsROI()[k]);
				rqsrDO.setRateQuotationProductCategoryHeader(rqpchDO);
				rqsrDOSet.add(rqsrDO);
			}
					k = k+5;
		}
				
				/*if((rqprTO.getRateProdCatCode().equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_CODE) 
						|| rqprTO.getRateProdCatCode().equals(RateCommonConstants.RATE_BENCH_MARK_ECOM_PROD_CAT_CODE))
						&&*/if( splLen>0){
					destOrder = 1;
					//rqsdDOSet = new HashSet<RateQuotationSpecialDestinationDO>();
					for (int j = 0; j < splLen; j++) {
					if(!StringUtil.isEmptyInteger(rqprTO.getStateId()[j+pin]) && !StringUtil.isEmptyDouble(rqprTO.getSpecialDestRate()[n])){
					
					RateQuotationSpecialDestinationDO rqsdDO = new RateQuotationSpecialDestinationDO();
					rqsdDO.setCreatedBy(updatedBy);
					rqsdDO.setUpdatedBy(updatedBy);
					rqsdDO.setStateId(rqprTO.getStateId()[j+pin]);
					if(!StringUtil.isEmptyInteger(rqprTO.getCityId()[j+pin])){
						CityDO cityDO = new CityDO();
						cityDO.setCityId(rqprTO.getCityId()[j+pin]);
						rqsdDO.setSpecialDestinationCityDO(cityDO);
					}
					if (!StringUtil.isNull(originSector))
						rqsdDO.setOriginSector(originSector);
					rqsdDO.setRate(rqprTO.getSpecialDestRate()[n]);
					rqsdDO.setRateQuotationProductCategoryHeaderDO(rqpchDO);
					rqsdDO.setRateQuotationWeightSlabDO(rqwsDO);
					rqsdDO.setOrder(destOrder);
					destOrder++;
					rqsdDOSet.add(rqsdDO);
					n = n+5;
				}else if(splLen>1){
					n = n+5;
				}
					
			}
					
					}
				rqwsDO.setRateQuotationSpecialDestinationDO(rqsdDOSet);
				rqwsDO.setRateQuotationSlabRateDO(rqsrDOSet);
				rqwsDOSet.add(rqwsDO);
				
				}
			}
			
		}
	}else{
		String configuredType = rqprTO.getRateConfiguredType();
		int awtlen = 0;
		int uLen = 0;
		int tLen = 0;
		int awt = -1;
		if(configuredType.equals("D")){
			wtLen = 5;
			awtlen = 2;
		}else if(configuredType.equals("P")){
			wtLen = 6;
			awtlen = 5;
			uLen = 3;
			tLen = 5;
		}else if(configuredType.equals("B")){
			wtLen = 8;
			awtlen = 5;
			uLen = 5;
			tLen = 7;
		}
		for(int i=0;i<wtLen;i++){
			k = m+i;
			n = d+i;
			rqwsDO = null;
			if(i<=(wtLen-awtlen) || (i >= uLen && i < tLen)){
				if((i == uLen && (!StringUtil.isEmptyInteger(rqprTO.getRateQuotCOStartWeight()[i]))) || !StringUtil.isEmptyInteger(rqprTO.getRateQuotEndWeight()[w])){
					
					if(!CGCollectionUtils.isEmpty(rqwsDOSet)){
						for(RateQuotationWeightSlabDO qwsDO : rqwsDOSet){
							if(!(StringUtil.isEmptyInteger(rqprTO.getRateWtSlabId()[w]))
									&& qwsDO.getRateQuotationWeightSlabId().equals(rqprTO.getRateWtSlabId()[w])){
								rqwsDO = qwsDO;
								rqsrDOSet = rqwsDO.getRateQuotationSlabRateDO();
								rqsdDOSet = rqwsDO.getRateQuotationSpecialDestinationDO();
								break;
							}
						}
					}
					if(StringUtil.isNull(rqwsDO)){
						rqwsDO = new RateQuotationWeightSlabDO();
					}
					if (StringUtil.isNull(rqwsDO.getCreatedBy())) {
						rqwsDO.setCreatedBy(updatedBy);
					}
					rqwsDO.setUpdatedBy(updatedBy);
					rqwsDO.setUpdatedDate(Calendar.getInstance().getTime());
					/*if(!(StringUtil.isEmptyInteger(rqprTO.getRateWtSlabId()[w])))
						rqwsDO.setRateQuotationWeightSlabId(rqprTO.getRateWtSlabId()[w]);*/
					
					
					WeightSlabDO stwsDO = new WeightSlabDO();
					stwsDO.setWeightSlabId(rqprTO.getRateQuotCOStartWeight()[i]);
					rqwsDO.setStartWeight(stwsDO);
					
					WeightSlabDO endwsDO = new WeightSlabDO();
					if(!StringUtil.isEmptyInteger(rqprTO.getRateQuotEndWeight()[w])){
						endwsDO.setWeightSlabId(rqprTO.getRateQuotEndWeight()[w]);
						rqwsDO.setEndWeight(endwsDO);
					}
					
					rqwsDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
					rqwsDO.setOrder(order);
					order++;
					rqwsDO.setRateQuotProductCategoryHeaderDO(rqpchDO);
					
					//rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
					int sectorLength = sector.length;
					for (int j = 0; j < sectorLength; j++) {
						if((!StringUtil.isEmptyInteger(rqprTO.getRateQuotCOStartWeight()[i]) && StringUtil.isEmptyInteger(rqprTO.getRateQuotEndWeight()[w])) || (!StringUtil.isEmptyInteger(rqprTO.getRateQuotEndWeight()[w]) && !StringUtil.isEmptyDouble(rqprTO.getRate()[k]) )){
					
							RateQuotationSlabRateDO rqsrDO = new RateQuotationSlabRateDO();
							rqsrDO.setCreatedBy(updatedBy);
							rqsrDO.setUpdatedBy(updatedBy);
							if (!StringUtil.isEmptyDouble(rqprTO.getRate()[k])){
								rqsrDO.setRate(rqprTO.getRate()[k]);
							}else{
								rqsrDO.setRate(null);
							}
							rqsrDO.setDestinationSector(Integer.parseInt(sector[j]));
							rqsrDO.setRateQuotationWeightSlabDO(rqwsDO);
							
							rqsrDO.setValueFromROI(rqprTO.getIsROI()[k]);
							rqsrDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
							rqsrDO.setRateQuotationProductCategoryHeader(rqpchDO);
							rqsrDOSet.add(rqsrDO);
						}
						if(configuredType.equals("D")){
							k = k+5;
						}else if(configuredType.equals("P")){
							k = k+6;
						}else if(configuredType.equals("B")){
							k = k+8;
						}
					}
					
					if(splLen>0){
						destOrder = 1;					
						for (int j = 0; j < splLen; j++) {
							if(!StringUtil.isEmptyInteger(rqprTO.getStateId()[j+pin]) &&  !StringUtil.isEmptyDouble(rqprTO.getSpecialDestRate()[n])){
								
								RateQuotationSpecialDestinationDO rqsdDO = new RateQuotationSpecialDestinationDO();
								
								rqsdDO.setCreatedBy(updatedBy);
								rqsdDO.setUpdatedBy(updatedBy);
								rqsdDO.setStateId(rqprTO.getStateId()[j+pin]);
								if(!StringUtil.isEmptyInteger(rqprTO.getCityId()[j+pin])){
									CityDO cityDO = new CityDO();
									cityDO.setCityId(rqprTO.getCityId()[j+pin]);
									rqsdDO.setSpecialDestinationCityDO(cityDO);
								}
								if (!StringUtil.isEmptyDouble(rqprTO.getSpecialDestRate()[n])){
									rqsdDO.setRate(rqprTO.getSpecialDestRate()[n]);
								}else{
									rqsdDO.setRate(null);
								}
								rqsdDO.setRateQuotationWeightSlabDO(rqwsDO);
								rqsdDO.setRateQuotationProductCategoryHeaderDO(rqpchDO);
								rqsdDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
								rqsdDO.setOrder(destOrder);
								rqsdDOSet.add(rqsdDO);
								if(configuredType.equals("D")){
									n = n+5;
								}else if(configuredType.equals("P")){
									n = n+6;
								}else if(configuredType.equals("B")){
									n = n+8;
								}
								destOrder++;
							}else if(splLen>1){
								if(configuredType.equals("D")){
									n = n+5;
								}else if(configuredType.equals("P")){
									n = n+6;
								}else if(configuredType.equals("B")){
									n = n+8;
								}
							}
						}
					}
					rqwsDO.setRateQuotationSpecialDestinationDO(rqsdDOSet);
					rqwsDO.setRateQuotationSlabRateDO(rqsrDOSet);
					rqwsDOSet.add(rqwsDO);
				}
				w++;
		}else if((i==(wtLen-1)) || (i == (uLen-1))){
					
				if(!StringUtil.isEmptyInteger(rqprTO.getRateQuotCOAddWeight()[++awt])){
					if(!CGCollectionUtils.isEmpty(rqwsDOSet)){
						if(!StringUtil.isNull(rqprTO.getRateCOAddWtSlab())){
							for(RateQuotationWeightSlabDO qwsDO : rqwsDOSet){
								if(!(StringUtil.isEmptyInteger(rqprTO.getRateCOAddWtSlab()[awt]))
										&& qwsDO.getRateQuotationWeightSlabId().equals(rqprTO.getRateCOAddWtSlab()[awt])){
									rqwsDO = qwsDO;
									rqsrDOSet = rqwsDO.getRateQuotationSlabRateDO();
									rqsdDOSet = rqwsDO.getRateQuotationSpecialDestinationDO();
									break;
								}
							}
						}
					}
					if(StringUtil.isNull(rqwsDO)){
						rqwsDO = new RateQuotationWeightSlabDO();
					}
					
					if(!(StringUtil.isEmptyInteger(rqprTO.getRateAddWtSlabId())))
						rqwsDO.setRateQuotationWeightSlabId(rqprTO.getRateAddWtSlabId());
					if(StringUtil.isEmptyInteger(rqwsDO.getCreatedBy())){
						rqwsDO.setCreatedBy(updatedBy);
					}
					rqwsDO.setUpdatedBy(updatedBy);
					rqwsDO.setUpdatedDate(Calendar.getInstance().getTime());
					
					WeightSlabDO stwsDO = new WeightSlabDO();
					stwsDO.setWeightSlabId(rqprTO.getRateQuotCOAddWeight()[awt]);
	
					//awt++;
					rqwsDO.setOrder(order);
					order++;
					rqwsDO.setStartWeight(stwsDO);
					rqwsDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
					
					rqwsDO.setRateQuotProductCategoryHeaderDO(rqpchDO);
					
					//rqsrDOSet = new HashSet<RateQuotationSlabRateDO>();
					int sectorLen = sector.length;
					for (int j = 0; j < sectorLen; j++) {
							if(!StringUtil.isEmptyDouble(rqprTO.getRate()[k])){
								RateQuotationSlabRateDO rqsrDO = new RateQuotationSlabRateDO();
								rqsrDO.setCreatedBy(updatedBy);
								rqsrDO.setUpdatedBy(updatedBy);
								if (!StringUtil.isNull(originSector)){
									rqsrDO.setOriginSector(originSector);
								}
								rqsrDO.setDestinationSector(Integer.parseInt(sector[j]));
								rqsrDO.setRateQuotationWeightSlabDO(rqwsDO);
								rqsrDO.setRate(rqprTO.getRate()[k]);
								rqsrDO.setValueFromROI(rqprTO.getIsROI()[k]);
								rqsrDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
								rqsrDO.setRateQuotationProductCategoryHeader(rqpchDO);
								rqsrDOSet.add(rqsrDO);
							}
						if(configuredType.equals("D")){
							k = k+5;
						}else if(configuredType.equals("P")){
							k = k+6;
						}else if(configuredType.equals("B")){
							k = k+8;
						}
					}
					
					if( splLen>0){
						destOrder = 1;
						//rqsdDOSet = new HashSet<RateQuotationSpecialDestinationDO>();
						for (int j = 0; j < splLen; j++) {
						if(!StringUtil.isEmptyInteger(rqprTO.getStateId()[j+pin]) && !StringUtil.isEmptyDouble(rqprTO.getSpecialDestRate()[n])){
						
						RateQuotationSpecialDestinationDO rqsdDO = new RateQuotationSpecialDestinationDO();
						rqsdDO.setCreatedBy(updatedBy);
						rqsdDO.setUpdatedBy(updatedBy);
						rqsdDO.setStateId(rqprTO.getStateId()[j+pin]);
						if(!StringUtil.isEmptyInteger(rqprTO.getCityId()[j+pin])){
							CityDO cityDO = new CityDO();
							cityDO.setCityId(rqprTO.getCityId()[j+pin]);
							rqsdDO.setSpecialDestinationCityDO(cityDO);
						}
						if (!StringUtil.isNull(originSector))
							rqsdDO.setOriginSector(originSector);
						rqsdDO.setRate(rqprTO.getSpecialDestRate()[n]);
						rqsdDO.setRateQuotationProductCategoryHeaderDO(rqpchDO);
						rqsdDO.setRateQuotationWeightSlabDO(rqwsDO);
						rqsdDO.setRateConfiguredType(rqprTO.getRateConfiguredType());
						rqsdDO.setOrder(destOrder);
						destOrder++;
						rqsdDOSet.add(rqsdDO);
						if(configuredType.equals("D")){
							n = n+5;
						}else if(configuredType.equals("P")){
							n = n+6;
						}else if(configuredType.equals("B")){
							n = n+8;
						}
					}else if(splLen>1){
						if(configuredType.equals("D")){
							n = n+5;
						}else if(configuredType.equals("P")){
							n = n+6;
						}else if(configuredType.equals("B")){
							n = n+8;
						}
					}
						
				}
						
						}
					
					rqwsDO.setRateQuotationSpecialDestinationDO(rqsdDOSet);
					rqwsDO.setRateQuotationSlabRateDO(rqsrDOSet);
					rqwsDOSet.add(rqwsDO);
						
				}
			}
		}
			
		
	}
		rqpchDO.setRateQuotationWeightSlabDO(rqwsDOSet);
		rqpchDO.setRateQuotationDO(rqDO);
		rqpchDOSet.add(rqpchDO);
		rqDO.setRateQuotationProductCategoryHeaderDO(rqpchDOSet);
		
		LOGGER.trace("RateQuotationServiceImpl::DomainConverterRateQuotProposedRatesTO2RateQuotProdHeaderDO::END------------>:::::::");
	}

	
	@Override
	public void getRateQuotationProposedRateDetails(
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationServiceImpl::getRateQuotationProposedRateDetails::START------------>:::::::");
		Integer quotationId = rateQuotationProposedRatesTO.getRateQuotationId();
		Integer productCatId = rateQuotationProposedRatesTO.getRateProdCatId();
		
		/*List<RateQuotationProductCategoryHeaderDO> rqphList =  rateQuotationDAO.getRateQuotationProductCatHeader(quotationId, productCatId);
		if(!CGCollectionUtils.isEmpty(rqphList)){
			RateQuotationProductCategoryHeaderDO rqpchDO = rqphList.get(0);
			
			toConverterRateQuotationProposedRatesDO2RateQuotationProposedRatesTO(rateQuotationProposedRatesTO,  rqpchDO);
		
		}*/
		
		RateQuotationDO rqDO = null;
		rqDO =  rateQuotationDAO.rateQuotationByQuotationId(quotationId);
		
		
		if(!StringUtil.isNull(rqDO)){
			
			Set<RateQuotationProductCategoryHeaderDO> rqphList = rqDO.getRateQuotationProductCategoryHeaderDO();
			if(!CGCollectionUtils.isEmpty(rqphList)){
				for(RateQuotationProductCategoryHeaderDO rqpchDO : rqphList){
					if(rqpchDO.getRateProductCategory().getRateProductCategoryId().equals(productCatId)){
						toConverterRateQuotationProposedRatesDO2RateQuotationProposedRatesTO(rateQuotationProposedRatesTO,  rqpchDO);
					}
				}
			}
			
			if(!StringUtil.isNull(rqDO.getQuotedCustomer())){
				OfficeDO ofcDO = rqDO.getQuotedCustomer().getSalesOfficeDO();
				if(ofcDO.getOfficeTypeDO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_CORP_OFFICE)){
					rateQuotationProposedRatesTO.setRegionCode("");			
				}
				else{
					if(!StringUtil.isNull(ofcDO.getMappedRegionDO()) && !StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getZone())) {
						ZoneTO zoneTO = rateCommonService.getZoneByZoneId(ofcDO.getMappedRegionDO().getZone());
						if(!StringUtil.isNull(zoneTO)){
							rateQuotationProposedRatesTO.setRegionCode(zoneTO.getZoneCode());
						}
						else{
							rateQuotationProposedRatesTO.setRegionCode("");			
						}
					}
					else{
						rateQuotationProposedRatesTO.setRegionCode("");			
					}
				}
			}
		}

		LOGGER.trace("RateQuotationServiceImpl::getRateQuotationProposedRateDetails::END------------>:::::::");
	}
	
	

	private void getRateQuotProposedRateDetailsByProdHeader(
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO,
			Integer prodCatHeaderId, RateQuotationProductCategoryHeaderDO rqpchDO) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationServiceImpl::getRateQuotProposedRateDetailsByProdHeader::START------------>:::::::");
		List<RateQuotationProductCategoryHeaderDO> rqphList =  rateQuotationDAO.getRateQuotProposedRateDetailsByProdHeader(rqpchDO.getRateQuotationProductCategoryHeaderId());
		
		if(!CGCollectionUtils.isEmpty(rqphList)){
			
			RateQuotationProductCategoryHeaderDO rqpDO = rqphList.get(0);
			
			toConverterRateQuotationProposedRatesDO2RateQuotationProposedRatesTO(rateQuotationProposedRatesTO,  rqpDO);
			
		}
		//TOConverterRateQuotationDO2RateQuotationTO(rateQuotationProposedRatesTO,  rqpchDO);
		LOGGER.trace("RateQuotationServiceImpl::getRateQuotProposedRateDetailsByProdHeader::END------------>:::::::");
	}
	
	
	private void toConverterRateQuotationProposedRatesDO2RateQuotationProposedRatesTO(
			RateQuotationProposedRatesTO rateQuotationProposedRatesTO, RateQuotationProductCategoryHeaderDO rqpchDO)  throws CGBusinessException, CGSystemException{

		LOGGER.trace("RateQuotationServiceImpl::TOConverterRateQuotationDO2RateQuotationTO::START------------>:::::::");
		RateQuotationProductCategoryHeaderTO rqpchTO = new RateQuotationProductCategoryHeaderTO();
		List<CityTO> cityTOList= null;
		rateQuotationProposedRatesTO.setProductCatHeaderTO(rqpchTO);
		
		if (!StringUtil.isNull(rqpchDO.getRateQuotationDO().getRateQuotationId()))
		{
			RateQuotationTO rqTO = new RateQuotationTO();
			rqTO.setRateQuotationId(rqpchDO.getRateQuotationDO().getRateQuotationId());
			rqTO.setRateQuotationNo(rqpchDO.getRateQuotationDO().getRateQuotationNo());
			rqTO.setStatus(rqpchDO.getRateQuotationDO().getStatus());
			//rqTO = (RateQuotationTO)CGObjectConverter.createToFromDomain(rqpchDO.getRateQuotationDO(), rqTO);
			rqpchTO.setQuotationId(rqTO);
		}
		//flat rate
		rqpchTO.setFlatRate(rqpchDO.getFlatRate());
		rqpchTO = (RateQuotationProductCategoryHeaderTO) CGObjectConverter.createToFromDomain(rqpchDO, rqpchTO);
		
		
		RateProductCategoryTO rpcTO = new RateProductCategoryTO();
		
		if (!StringUtil.isNull(rqpchDO.getRateProductCategory()))
		{
		rpcTO = (RateProductCategoryTO) CGObjectConverter.createToFromDomain(rqpchDO.getRateProductCategory(), rpcTO);
		}
		
		rqpchTO.setRateProductCategoryTO(rpcTO);
		
		rqpchTO.setVobSlab(rqpchDO.getVobSlab());
		
		if (!StringUtil.isNull(rqpchDO.getMinimumChargeableWeightDO()))
		{
			rqpchTO.setRateMinChargeableWeight(rqpchDO.getMinimumChargeableWeightDO().getRateMinChargeableWeightId());
		}
		
		
		List<RateQuotationWeightSlabTO> wtSlabTOList = new ArrayList<RateQuotationWeightSlabTO>(); 
		
		List<RateQuotationSlabRateTO> slabTOList = null;
		
		List<RateQuotationSpecialDestinationTO> splDestTOList = null;
		
		
		if(!CGCollectionUtils.isEmpty(rqpchDO.getRateQuotationWeightSlabDO())){
			
			Set<RateQuotationWeightSlabDO> wtSlabDOList = rqpchDO.getRateQuotationWeightSlabDO();
			for(RateQuotationWeightSlabDO rqwsDO: wtSlabDOList){
				if(!StringUtil.isNull(rqwsDO)){
				RateQuotationWeightSlabTO rqwsTO = new RateQuotationWeightSlabTO();
				
				rqwsTO = (RateQuotationWeightSlabTO)CGObjectConverter.createToFromDomain(rqwsDO,rqwsTO);
				
				
				WeightSlabTO stWtSlabTO = new WeightSlabTO();
				stWtSlabTO = (WeightSlabTO)CGObjectConverter.createToFromDomain(rqwsDO.getStartWeight(),stWtSlabTO);
				if(!StringUtil.isNull(rqwsDO.getEndWeight())){
				WeightSlabTO endWtSlabTO = new WeightSlabTO();
				endWtSlabTO = (WeightSlabTO)CGObjectConverter.createToFromDomain(rqwsDO.getEndWeight(),endWtSlabTO);
				rqwsTO.setEndWeightTO(endWtSlabTO);
				}
				rqwsTO.setStartWeightTO(stWtSlabTO);
				
				Set<RateQuotationSlabRateDO> slabDOList = rqwsDO.getRateQuotationSlabRateDO();
				
				slabTOList = new ArrayList<RateQuotationSlabRateTO>();
				for(RateQuotationSlabRateDO rqsrDO: slabDOList){
					
					if(!StringUtil.isNull(rqsrDO)){
						RateQuotationSlabRateTO rqsrTO = new RateQuotationSlabRateTO();
						
						rqsrTO = (RateQuotationSlabRateTO)CGObjectConverter.createToFromDomain(rqsrDO,rqsrTO);
						slabTOList.add(rqsrTO);
					}
				}
				
				rqwsTO.setSlabRateTO(slabTOList);
				
				if(!CGCollectionUtils.isEmpty(rqwsDO.getRateQuotationSpecialDestinationDO())){
					Set<RateQuotationSpecialDestinationDO> splDestDOList = rqwsDO.getRateQuotationSpecialDestinationDO();
					
					splDestTOList = new ArrayList<RateQuotationSpecialDestinationTO>();
					
					Integer[] stateIds = new Integer[splDestDOList.size()];
					Integer state = null;
					int j=0;
					boolean stateExist = Boolean.FALSE;
					
					Map<Integer, List<CityTO>> cityMap = new HashMap<Integer,List<CityTO>>();
					for(RateQuotationSpecialDestinationDO rqsdDO: splDestDOList){
						stateExist = Boolean.FALSE;
						if(!StringUtil.isNull(rqsdDO)){
							RateQuotationSpecialDestinationTO rqsdTO = new RateQuotationSpecialDestinationTO();
							
							rqsdTO = (RateQuotationSpecialDestinationTO)CGObjectConverter.createToFromDomain(rqsdDO,rqsdTO);
							state = rqsdTO.getStateId();
							
							for(int i=0;i<j;i++){
								if(!StringUtil.isEmptyInteger(stateIds[i]) && stateIds[i].equals(state)){
									rqsdTO.setCityList(cityMap.get(state));
									stateExist = Boolean.TRUE;
									break;
								}
							}
							
							if(!stateExist){
								stateIds[j] = state;
								cityTOList = rateCommonService.getCityListByStateId(state);
								cityMap.put(rqsdTO.getStateId(), cityTOList);
								if(!CGCollectionUtils.isEmpty(cityTOList)){
									rqsdTO.setCityList(cityTOList);
								}
								j++;
							}
							
							if(!StringUtil.isNull(rqsdDO.getSpecialDestinationCityDO()) && !StringUtil.isEmptyInteger(rqsdDO.getSpecialDestinationCityDO().getCityId())){
								CityTO splCityTO = new CityTO();
								splCityTO = (CityTO)CGObjectConverter.createToFromDomain(rqsdDO.getSpecialDestinationCityDO(),splCityTO);
								rqsdTO.setSpecialDestinationCityTO(splCityTO);
							}
							splDestTOList.add(rqsdTO);
							
							
						}
					}
					Collections.sort(splDestTOList);
					rqwsTO.setSplDestRateTO(splDestTOList);
				}
				wtSlabTOList.add(rqwsTO);
				}
				
			}
			Collections.sort(wtSlabTOList);
			rqpchTO.setWeightSlabTO(wtSlabTOList);
		}
		LOGGER.trace("RateQuotationServiceImpl::TOConverterRateQuotationDO2RateQuotationTO::END------------>:::::::");
	}
	
	public String getApprovalLevel(RateQuotationDO quotationDO, Integer regionId)throws CGBusinessException, CGSystemException{
		
		Double discount = null;
		
		Double rbDiscountRate;
		
		String approver=null;
		
		boolean appRequire = Boolean.FALSE;
		boolean regLevel = Boolean.FALSE;
		boolean corpLevel = Boolean.FALSE;
		LOGGER.trace("RateQuotationServiceImpl::getApprovalLevel::START------------>:::::::");
		RateBenchMarkHeaderTO rbmhTO = new RateBenchMarkHeaderTO();
		
		rbmhTO.setRateIndustryCategoryId(quotationDO.getQuotedCustomer().getIndustryCategoryDO().getRateIndustryCategoryId());
		rbmhTO.setRateCustCatId(quotationDO.getQuotedCustomer().getCustomerCategoryDO().getRateCustomerCategoryId());
		
		RateBenchMarkHeaderDO rbmhDO = null;//new RateBenchMarkHeaderDO();
		
		rbmhDO = rateBenchMarkService.getRateBenchMarkHeaderDetails(rbmhTO);
		if(StringUtil.isNull(rbmhDO) || !rbmhDO.getIsApproved().equals("Y")){
			ExceptionUtil.prepareBusinessException(RateErrorConstants.VALIDATE_RATE, new String[]{RateQuotationConstants.RATE_BENCH_MARK});
		}
		
		if(!StringUtil.isEmptyInteger(regionId)){
		RegionRateBenchMarkDiscountDO rrbmdDO = regionRateBenchMarkDiscountService.getRateBenchMarkDiscountServiceByRegion(regionId);
		if(StringUtil.isNull(rrbmdDO)){
			ExceptionUtil.prepareBusinessException(RateErrorConstants.VALIDATE_RATE, new String[]{RateQuotationConstants.RATE_REGION_BENCH_MARK});
		}
		else{
		discount = rrbmdDO.getDiscountPercentage();
		}
		
		if(StringUtil.isEmptyDouble(discount)){
			ExceptionUtil.prepareBusinessException(RateErrorConstants.VALIDATE_RATE, new String[]{RateQuotationConstants.RATE_REGION_BENCH_MARK_DISCOUNT});
		}
		}
		for(RateQuotationProductCategoryHeaderDO rqpchDO: quotationDO.getRateQuotationProductCategoryHeaderDO()){
			for(RateBenchMarkProductDO rbmpDO: rbmhDO.getRateBenchMarkProductDO()){
				if(rqpchDO.getRateProductCategory().getRateProductCategoryId().equals(rbmpDO.getRateProductCategoryDO().getRateProductCategoryId())){
					for(RateBenchMarkMatrixHeaderDO rbmmhDO: rbmpDO.getRateBenchMarkMatrixHeaderDO()){		
						if(rqpchDO.getVobSlab().equals(rbmmhDO.getVobSlab())){
			
							Set<RateQuotationWeightSlabDO> weightSet = rqpchDO.getRateQuotationWeightSlabDO();
				
							for(RateQuotationWeightSlabDO weightSlab: weightSet){
					
								for(RateBenchMarkMatrixDO rbmmDO: rbmmhDO.getRateBenchMarkMatrixDO()){
						
									if((!StringUtil.isNull(weightSlab.getEndWeight()) && (weightSlab.getEndWeight().getWeightSlabId().equals(rbmmDO.getWeightSlab()))) 
											|| (StringUtil.isNull(weightSlab.getEndWeight()) && (weightSlab.getStartWeight().getWeightSlabId().equals(rbmmDO.getWeightSlab())))){
							
										for(RateQuotationSlabRateDO rqsrDO: weightSlab.getRateQuotationSlabRateDO()){
											if(rqsrDO.getDestinationSector().equals(rbmmDO.getRateDestinationSector()) && 
													(StringUtil.isNull(rqsrDO.getOriginSector()) || 
															(!StringUtil.isNull(rqsrDO.getOriginSector()) 
																	&& rqsrDO.getOriginSector().equals(rbmmDO.getRateOriginSector())))){
								
													if(rqsrDO.getRate() < rbmmDO.getRate()){
											
														if(!StringUtil.isNull(regionId)){
															rbDiscountRate = rbmmDO.getRate()-((rbmmDO.getRate()*discount)/100);
												
															if(rqsrDO.getRate() < rbDiscountRate){
																appRequire = Boolean.TRUE;
																corpLevel = Boolean.TRUE;
																break;
															}else{
																appRequire = Boolean.TRUE;
																regLevel = Boolean.TRUE;															
															}
														}else{
															appRequire = Boolean.TRUE;
															corpLevel = Boolean.TRUE;
															break;
														}
													}
													break;
											}
								
										}
										if(appRequire)
											break;
							
									}
									if(appRequire)
										break;
						
								}
								if(appRequire)
									break;
							}
							if(appRequire)
								break;
						}
						if(appRequire)
							break;
					}
					if(appRequire)
						break;
				}
				if(appRequire)
					break;
			}
			if(appRequire)
				break;
		}
		if(corpLevel)
			approver = RateQuotationConstants.APPROVAL_RC;
		else if(regLevel)
			approver = RateQuotationConstants.APPROVAL_RO;
		
		LOGGER.trace("RateQuotationServiceImpl::getApprovalLevel::END------------>:::::::");
		return approver;
	}
	

	
	@Override
	public RateQuotaionRTOChargesTO submitRateQuotation(RateQuotaionRTOChargesTO rtoChargesTO, String indCatCode, Integer regionId)
			throws CGBusinessException, CGSystemException {

		
		RateQuotationDO quotationDO = null;//new RateQuotationDO();
		RateQuotationRTOChargesDO rtoChargesDO = new RateQuotationRTOChargesDO();
		Integer quotationId = null;
		String approvelevel= null;
		String status = null;
		
			LOGGER.trace("RateQuotationServiceImpl::submitRateQuotation::START------------>:::::::");
		Integer updatedBy = rtoChargesTO.getRateQuotation().getUpdatedBy();
			if (StringUtil.isNull(rtoChargesTO.getRateQuotation()
					.getRateQuotationId())
					|| (rtoChargesTO.getRateQuotation().getRateQuotationId()
							.equals(CommonConstants.ZERO))) {
				
				List<String> seqNOs = generateSequenceNo(
						Integer.parseInt(RateQuotationConstants.ONE),
						RateQuotationConstants.QUOTATION_NO);
				RateQuotationTO rateQuotationTO = new RateQuotationTO();
				rateQuotationTO.setLoginOfficeCode(rtoChargesTO
					.getRateQuotation().getLoginOfficeCode());
				String quotationNo = getQuotationNo(rateQuotationTO, seqNOs);
				rateQuotationTO.setRateQuotationNo(quotationNo);
				rtoChargesTO.setRateQuotation(rateQuotationTO);
			}else{
				quotationId = rtoChargesTO.getRateQuotation().getRateQuotationId();
			}
			quotationDO = rateQuotationDAO.getRateQuotation(rtoChargesTO.getRateQuotation().getRateQuotationNo(),rtoChargesTO.getRateQuotation().getQuotationUsedFor(),rtoChargesTO.getRateQuotation().getRateQuotationId());
			if(!StringUtil.isNull(quotationDO) && !StringUtil.isNull(quotationDO.getRateQuotationRtoChargesDO())){
				rtoChargesDO = quotationDO.getRateQuotationRtoChargesDO();
			}
			
			//if(!StringUtil.isNull(rtoChargesTO.getRtoChargesApplicable())){
				prepareQuotationRTOChargeDOList(rtoChargesTO, quotationDO, rtoChargesDO);
			//}
			
			if (StringUtil.isNull(indCatCode) && !StringUtil.isNull(quotationDO) && quotationDO.getRateQuotationType().equals(
					RateCommonConstants.RATE_QUOTATION_TYPE_N)){
				if( !StringUtil.isNull(quotationDO.getQuotedCustomer()) && !StringUtil.isNull(quotationDO.getQuotedCustomer()
						.getIndustryCategoryDO())){
					indCatCode = quotationDO.getQuotedCustomer()
						.getIndustryCategoryDO().getRateIndustryCategoryCode();
				}
			}
			
			/*if (!StringUtil.isNull(rtoChargesTO)) {
				rtoChargesDO = rateQuotationDAO
						.saveOrUpdateQuotationRTOCharges(quotationDO,rtoChargesDO);
				if(!StringUtil.isNull(rtoChargesDO)){
				rtoChargesTO = (RateQuotaionRTOChargesTO) CGObjectConverter.createToFromDomain(rtoChargesDO, rtoChargesTO);
				RateQuotationTO rqTO = new RateQuotationTO();
				quotationDO = rtoChargesDO.getRateQuotationDO();
				rqTO = quotationDomainConverter(quotationDO);
				rtoChargesTO.setRateQuotation(rqTO);
				}else{
					rtoChargesTO = null;
				}
			}
			}else{
				List<RateQuotationDO> rqDOList = rateQuotationDAO
						.searchQuotationDetails(rtoChargesTO.getRateQuotation());
				if(!CGCollectionUtils.isEmpty(rqDOList))
					quotationDO = rqDOList.get(0);
			}*/
			if(!StringUtil.isNull(quotationId) && !StringUtil.isNull(quotationDO)){
				if(!StringUtil.isNull(quotationDO)){
					if(StringUtil.isNull(quotationDO.getQuotedCustomer())){
						rtoChargesTO.setBasicInfo(Boolean.FALSE);
					}else if(CGCollectionUtils.isEmpty((quotationDO.getRateQuotationProductCategoryHeaderDO())) ||
								quotationDO.getRateQuotationProductCategoryHeaderDO().isEmpty()){
							rtoChargesTO.setProposedRates(Boolean.FALSE);
						}else if(CGCollectionUtils.isEmpty((quotationDO.getRateFixedChargeDO())) ||
									quotationDO.getRateFixedChargeDO().isEmpty()){
								rtoChargesTO.setFixedCharges(Boolean.FALSE);
							}else{
								if(quotationDO.getRateQuotationType().equals(RateQuotationConstants.NORMAL)){
									if(indCatCode.equals(RateCommonConstants.RATE_CUST_FR)) {
										quotationDO.setStatus(RateQuotationConstants.APPROVED);
										quotationDO.setApprovalRequired(null);
										/*rateQuotationDAO
										.submitQuotation(quotationDO);*/
										rtoChargesTO.getRateQuotation().setStatus(RateQuotationConstants.APPROVED);
										rtoChargesTO.setSaved(Boolean.TRUE);
									}
									else{
										if(!StringUtil.isStringEmpty(quotationDO.getQuotationCreatedFrom()) && quotationDO.getQuotationCreatedFrom().equals("L")){
											approvelevel = null;
										}else{
											//approvelevel =  getQuotationApprovalRequired(quotationDO, regionId);
											approvelevel =  RateQuotationConstants.APPROVAL_RO;
										}
										if(!StringUtil.isStringEmpty(approvelevel)){
											quotationDO.setStatus(RateQuotationConstants.SUBMITTED);
											status = RateQuotationConstants.SUBMITTED;
											if(StringUtil.isEmptyInteger(regionId)){
												approvelevel =  RateQuotationConstants.APPROVAL_RC;
												//quotationDO.setApprovedAt(RateQuotationConstants.APPROVAL_RO);
												quotationDO.setApprovedAt(RateQuotationConstants.APPROVAL_RC);
												status = RateQuotationConstants.APPROVED;
											}
										}else{
											status = RateQuotationConstants.APPROVED;
										}
										quotationDO.setApprovalRequired(approvelevel);
										quotationDO.setStatus(status);
										/*rateQuotationDAO
										.submitQuotation(quotationDO);*/
										rtoChargesTO.getRateQuotation().setStatus(status);
										rtoChargesTO.setSaved(Boolean.TRUE);
									}
								}else if(quotationDO.getRateQuotationType().equals(RateQuotationConstants.ECOMMERCE)){
									approvelevel = RateQuotationConstants.APPROVAL_RC;
									//approvelevel = RateQuotationConstants.APPROVAL_RO;
									status = RateQuotationConstants.SUBMITTED;
									if(StringUtil.isEmptyInteger(regionId)){
										approvelevel = RateQuotationConstants.APPROVAL_RC;
										quotationDO.setApprovedAt(RateQuotationConstants.APPROVAL_RO);
										//quotationDO.setApprovedAt(RateQuotationConstants.APPROVAL_RC);
										status = RateQuotationConstants.SUBMITTED;
									}
									quotationDO.setApprovalRequired(approvelevel);
									quotationDO.setStatus(status);
									/*rateQuotationDAO
									.submitQuotation(quotationDO);*/
									rtoChargesTO.getRateQuotation().setStatus(status);
									rtoChargesTO.setSaved(Boolean.TRUE);
								}
							}
					}
			}
			if(!StringUtil.isNull(quotationDO)){
				quotationDO.setUpdatedBy(updatedBy);
				quotationDO.setUpdatedDate(Calendar.getInstance().getTime());
				quotationDO = rateQuotationDAO.saveOrUpdateBasicInfo(quotationDO);
			}
			
		LOGGER.trace("RateQuotationServiceImpl::submitRateQuotation::END------------>:::::::");
		return rtoChargesTO;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationListViewTO> rateQuotationListViewDetails(
			Integer userId, Integer[] region, Integer[] city, String fromDate,
			String toDate, String quotationNo, String status, String type, String officeType) throws CGBusinessException,
			CGSystemException {

		List<RateQuotationWrapperDO> rqwDOList = null;
		List<RateQuotationListViewTO> rqwlvTOList = null;
			LOGGER.trace("RateQuotationServiceImpl::rateQuotationListViewDetails::START------------>:::::::");
			rqwDOList = rateQuotationDAO.rateQuotationListViewDetails(userId, region, city, fromDate, toDate, quotationNo, status, type, officeType);
			
			if(!CGCollectionUtils.isEmpty(rqwDOList)){
				//rqwlvTOList = new ArrayList<RateQuotationListViewTO>();
				
				rqwlvTOList = (List<RateQuotationListViewTO>)CGObjectConverter.createTOListFromDomainList(rqwDOList, RateQuotationListViewTO.class);
				
			}
			
		
		LOGGER.trace("RateQuotationServiceImpl::rateQuotationListViewDetails::END------------>:::::::");
		return rqwlvTOList;
	}
	
	@Override
	public List<RateQuotationListViewTO> searchQuotationByUserIdDateAndStatus(List<UserDO> userDOList,Date fromDate, Date toDate, String status) throws CGBusinessException,
			CGSystemException{
		
	
	List<RateQuotationListViewTO> rateQuotationListViewTOList = new ArrayList<RateQuotationListViewTO>();
		
		List<RateQuotationDO> listOfRateQuotationList = new ArrayList<RateQuotationDO>();
		
			LOGGER.trace("RateQuotationServiceImpl::searchQuotationByUserIdDateAndStatus::START------------>:::::::");
			/*int doSize = userDOList.size();
			for(int i=0;i<doSize;i++){
				//List<RateQuotationDO> rateQuotationDOs=rateQuotationDAO.getQuotationDtlsByUserIdDateAndStatus(userDOList.get(i).getUserId(),fromDate,toDate,status);
				List<RateQuotationDO> rateQuotationDOs=null;
				if(!StringUtil.isNull(rateQuotationDOs)){
				listOfRateQuotationList.addAll(rateQuotationDOs);
				}
			}*/
			//rateQuotationDO = rateQuotationDAO.searchQuotationDetails(rateQuotationTO);


			if (!StringUtil.isNull(listOfRateQuotationList)) {
				int quotListSize = listOfRateQuotationList.size();				
				for(int i=0;i<quotListSize;i++){
					RateQuotationListViewTO rateQuotatnViewTO = new RateQuotationListViewTO();
					rateQuotatnViewTO.setRateQuotationId(listOfRateQuotationList.get(i).getRateQuotationId());
					rateQuotatnViewTO.setRateQuotationNo(listOfRateQuotationList.get(i).getRateQuotationNo());
					String date = DateUtil.getDDMMYYYYDateString(listOfRateQuotationList.get(i).getQuotationCreatedDate());
					rateQuotatnViewTO.setQuotationDate(date);
					rateQuotatnViewTO.setStatus(listOfRateQuotationList.get(i).getStatus());
					if(!StringUtil.isNull(listOfRateQuotationList.get(i).getRateQuotationType()))
						rateQuotatnViewTO.setRateQuotationType(listOfRateQuotationList.get(i).getRateQuotationType());
					
					String rhoName= rateQuotationDAO.getRHONameByCreatedBy(listOfRateQuotationList.get(i).getQuotationCreatedBy());
					String stationName= rateQuotationDAO.getStationNameByCreatedBy(listOfRateQuotationList.get(i).getQuotationCreatedBy());
					
					rateQuotatnViewTO.setRegionalName(rhoName);
					rateQuotatnViewTO.setCityName(stationName);
					
					String salesOffcName = rateQuotationDAO.getSalesOffcNameByCustomr(listOfRateQuotationList.get(i).getCustomer().getCustomerId());
					String salesPersonName = rateQuotationDAO.getSalesPersonNameByCustomr(listOfRateQuotationList.get(i).getCustomer().getCustomerId());
					
					rateQuotatnViewTO.setSalesOfficeName(salesOffcName);
					rateQuotatnViewTO.setSalesPersonName(salesPersonName);
					
					rateQuotationListViewTOList.add(rateQuotatnViewTO);
				} 
			}

		LOGGER.trace("RateQuotationServiceImpl::searchQuotationByUserIdDateAndStatus::END------------>:::::::");
		return rateQuotationListViewTOList;
	}
	
	
	@Override
	public List<RateQuotationListViewTO> searchQuotationByUserIdStatusAndQuotatnNo(List<UserDO> userDOList,String quotatnNo, String status) throws CGBusinessException,
			CGSystemException{
		
	
	List<RateQuotationListViewTO> rateQuotationListViewTOList = new ArrayList<RateQuotationListViewTO>();
	
		List<RateQuotationDO> listOfRateQuotationList = new ArrayList<RateQuotationDO>();
		
			LOGGER.trace("RateQuotationServiceImpl::searchQuotationByUserIdStatusAndQuotatnNo::START------------>:::::::");
			/*int doSize = userDOList.size();
			for(int i=0;i<doSize;i++){
				//List<RateQuotationDO> rateQuotationDOs=rateQuotationDAO.getQuotationDtlsByUserIdStatusAndQuotatnNo(userDOList.get(i).getUserId(),quotatnNo,status);
				List<RateQuotationDO> rateQuotationDOs= null;
				if(!StringUtil.isNull(rateQuotationDOs)){
				listOfRateQuotationList.addAll(rateQuotationDOs);
				}
			}*/
			//rateQuotationDO = rateQuotationDAO.searchQuotationDetails(rateQuotationTO);


			if (!StringUtil.isNull(listOfRateQuotationList)) { 
				int listSize = listOfRateQuotationList.size();
				for(int i=0;i<listSize;i++){
					RateQuotationListViewTO rateQuotatnViewTO = new RateQuotationListViewTO();
					rateQuotatnViewTO.setRateQuotationId(listOfRateQuotationList.get(i).getRateQuotationId());
					rateQuotatnViewTO.setRateQuotationNo(listOfRateQuotationList.get(i).getRateQuotationNo());
					String date = DateUtil.getDDMMYYYYDateString(listOfRateQuotationList.get(i).getQuotationCreatedDate());
					rateQuotatnViewTO.setQuotationDate(date);
					rateQuotatnViewTO.setStatus(listOfRateQuotationList.get(i).getStatus());
					if(!StringUtil.isNull(listOfRateQuotationList.get(i).getRateQuotationType()))
						rateQuotatnViewTO.setRateQuotationType(listOfRateQuotationList.get(i).getRateQuotationType());
					
					String rhoName= rateQuotationDAO.getRHONameByCreatedBy(listOfRateQuotationList.get(i).getQuotationCreatedBy());
					String stationName= rateQuotationDAO.getStationNameByCreatedBy(listOfRateQuotationList.get(i).getQuotationCreatedBy());
					
					rateQuotatnViewTO.setRegionalName(rhoName);
					rateQuotatnViewTO.setCityName(stationName);
					
					String salesOffcName = rateQuotationDAO.getSalesOffcNameByCustomr(listOfRateQuotationList.get(i).getCustomer().getCustomerId());
					String salesPersonName = rateQuotationDAO.getSalesPersonNameByCustomr(listOfRateQuotationList.get(i).getCustomer().getCustomerId());
					
					rateQuotatnViewTO.setSalesOfficeName(salesOffcName);
					rateQuotatnViewTO.setSalesPersonName(salesPersonName);
					
					rateQuotationListViewTOList.add(rateQuotatnViewTO);
				} 
			}

		LOGGER.trace("RateQuotationServiceImpl::searchQuotationByUserIdStatusAndQuotatnNo::END------------>:::::::");
		return rateQuotationListViewTOList;
	}
	
@SuppressWarnings("unchecked")
public List<OfficeTO> getAllOfficesUnderLoggedInRHO(Integer loginOfficId)throws CGBusinessException,CGSystemException{
		
		List<OfficeTO> officeTOList = null;
			LOGGER.trace("RateQuotationServiceImpl::getAllOfficesUnderLoggedInRHO::START------------>:::::::");
			List<OfficeDO> officeDOList = rateQuotationDAO
			.getAllOfficesUnderLoggedInRHO(loginOfficId);


			if (!CGCollectionUtils.isEmpty(officeDOList)) {
				officeTOList = (List<OfficeTO>) CGObjectConverter
				.createTOListFromDomainList(officeDOList,
						OfficeTO.class);
			}
		LOGGER.trace("RateQuotationServiceImpl::getAllOfficesUnderLoggedInRHO::END------------>:::::::");
		return officeTOList;
	}
	
	public List<UserDO> getAllUsersOfOffice(Integer officeId)throws CGBusinessException,
	CGSystemException{
		
		List<UserDO> userDOList = null;
			LOGGER.trace("RateQuotationServiceImpl::getAllUsersOfOffice::START------------>:::::::");
			userDOList = rateQuotationDAO
			.getAllUsersOfOffice(officeId);


			
		LOGGER.trace("RateQuotationServiceImpl::getAllUsersOfOffice::END------------>:::::::");
		return userDOList;
		
	}
	
	public boolean approveRejectDomesticQuotation(String[] selectdNosArrayOfTypeRO, String[] selectdNosArrayOfTypeRC,String opName,String approver)throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationServiceImpl::approveRejectDomesticQuotation::START------------>:::::::");
		boolean isApproved=Boolean.FALSE;
		
		isApproved = rateQuotationDAO.approveRejectDomesticQuotation(selectdNosArrayOfTypeRO,selectdNosArrayOfTypeRC,opName, approver);
		LOGGER.trace("RateQuotationServiceImpl::approveRejectDomesticQuotation::START------------>:::::::");
		return isApproved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationListViewTO> searchQuotationForRegional(List<RegionRateBenchMarkDiscountTO> rbmhdTOList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover, Integer userRegionId) throws CGBusinessException,
			CGSystemException {
		
		

		List<RateQuotationListViewTO> rateQuotationListViewTOList = new ArrayList<RateQuotationListViewTO>();
		List<Integer>[] region = new ArrayList[2];
		Integer[] indId = new Integer[2];
		Integer[] indCatId = new Integer[2];
			LOGGER.trace("RateQuotationServiceImpl::searchQuotationForRegional::START------------>:::::::");
			if(!CGCollectionUtils.isEmpty(rbmhdTOList)){	
					int j = 0;
					boolean check = true;
					for(RegionRateBenchMarkDiscountTO rbmhdTO : rbmhdTOList){
					check = true;
						for(int i=0;i<j;i++){
						if(!StringUtil.isNull(indId[i]) && indId[i].equals(rbmhdTO.getIndustryCategory())){
							indCatId[i] = rbmhdTO.getIndustryCategory();
							region[i].add(rbmhdTO.getRegion());
							check = false;
						}
						}
						if(check){
							indId[j] = rbmhdTO.getIndustryCategory();
							indCatId[j] = rbmhdTO.getIndustryCategory();
							region[j] = new ArrayList<Integer>();
							region[j].add(rbmhdTO.getRegion());
							j++;
						}
					
						}
					
					int indLen = indCatId.length; 
			for(int i=0;i<indLen;i++){
				if(!StringUtil.isEmptyInteger(indCatId[i])){
					List<RateQuotationWrapperDO> rqwDOList=rateQuotationDAO.searchQuotationForRegional(region[i],indCatId[i], effectiveFrom, effectiveTo, status, quotationNo, null, userRegionId);
			
					if (!CGCollectionUtils.isEmpty(rqwDOList)) { 
				
						for(RateQuotationWrapperDO rqwDO : rqwDOList){
							RateQuotationListViewTO rateQuotatnViewTO = new RateQuotationListViewTO();
						
							rateQuotatnViewTO = (RateQuotationListViewTO)CGObjectConverter.createToFromDomain(rqwDO, rateQuotatnViewTO);
							rateQuotationListViewTOList.add(rateQuotatnViewTO);
						} 
					}
				}
			}
		}
			
		if(!StringUtil.isStringEmpty(isEQApprover) && isEQApprover.equals(CommonConstants.YES)){
				List<RateQuotationWrapperDO> rqwDOList=rateQuotationDAO.searchQuotationForRegional(null,null, effectiveFrom, effectiveTo, status, quotationNo, isEQApprover, userRegionId);
				
				if (!CGCollectionUtils.isEmpty(rqwDOList)) { 
			
					for(RateQuotationWrapperDO rqwDO : rqwDOList){
						RateQuotationListViewTO rateQuotatnViewTO = new RateQuotationListViewTO();
					
						rateQuotatnViewTO = (RateQuotationListViewTO)CGObjectConverter.createToFromDomain(rqwDO, rateQuotatnViewTO);
						rateQuotationListViewTOList.add(rateQuotatnViewTO);
					} 
				}
			}
		
		if (CGCollectionUtils.isEmpty(rateQuotationListViewTOList)) {
			return null;
		} else {
			// Code Snippet Added By Himal
			// Comments: Need to show list in descending order in approver
			// quotation screen, Query fetch details in descending order only
			// but because of industry category logic return (in same method
			// above) for regional quotation list search
			Collections.sort(rateQuotationListViewTOList,
					new Comparator<RateQuotationListViewTO>() {
						@Override
						public int compare(RateQuotationListViewTO a,
								RateQuotationListViewTO b) {
							Date aDate = DateUtil
									.parseStringDateToDDMMYYYYHHMMFormat(a
											.getQuotationDate());
							Date bDate = DateUtil
									.parseStringDateToDDMMYYYYHHMMFormat(b
											.getQuotationDate());
							// To sort in descending order consider with respect
							// to bDate. if ascendig order then consider with
							// respect to aDate.
							if (bDate.before(aDate)) {
								return -1;
							} else if (bDate.after(aDate)) {
								return 1;
							} else {
								return 0;
							}
						}
					});
		}
		LOGGER.trace("RateQuotationServiceImpl::searchQuotationForRegional::END------------>:::::::");
		return rateQuotationListViewTOList;
	}
	
	public List<RegionRateBenchMarkDiscountTO> checkEmpIdRegionalApprovr(Integer empId) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationServiceImpl::checkEmpIdRegionalApprovr::START------------>:::::::");
		List<RegionRateBenchMarkDiscountDO> regionRateBenchMarkDiscntList =  regionRateBenchMarkDiscountService.checkEmpIdRegionalApprover(empId);
		
		List<RegionRateBenchMarkDiscountTO> rbmhdTOList = null;
		RegionRateBenchMarkDiscountTO rbmhdTO = null;
		
		if(!CGCollectionUtils.isEmpty(regionRateBenchMarkDiscntList)){
			rbmhdTOList = new ArrayList<RegionRateBenchMarkDiscountTO>();
			int rrbmdSize = regionRateBenchMarkDiscntList.size();
			for(int i=0;i<rrbmdSize;i++){
				rbmhdTO = new RegionRateBenchMarkDiscountTO();
		
				rbmhdTO.setIndustryCategory(regionRateBenchMarkDiscntList.get(i).getIndustryCategory());
				rbmhdTO.setRegion(regionRateBenchMarkDiscntList.get(i).getRegion());
				rbmhdTOList.add(rbmhdTO);
			}
		
		}
		LOGGER.trace("RateQuotationServiceImpl::checkEmpIdRegionalApprovr::END------------>:::::::");
		return rbmhdTOList;
	}
	
	public List<Integer> checkEmpIdCorpApprovr(Integer empId) throws CGBusinessException, CGSystemException{
		LOGGER.trace("RateQuotationServiceImpl::checkEmpIdCorpApprovr::START------------>:::::::");
		List<Integer> rateIndustryCategryIdList = null;
		
		List<RateBenchMarkHeaderDO> rateBenchMarkHeaderDOList =  rateBenchMarkService.checkEmpIdCorpApprover(empId);
		
		if(!CGCollectionUtils.isEmpty(rateBenchMarkHeaderDOList)){
			int rbmhSize = rateBenchMarkHeaderDOList.size();
			rateIndustryCategryIdList = new ArrayList<Integer>(rbmhSize);
			
			for(int i=0;i<rbmhSize;i++){
				Integer  rateIndustryCategryId= rateBenchMarkHeaderDOList.get(i).getRateIndustryCategoryDO().getRateIndustryCategoryId();
				rateIndustryCategryIdList.add(rateIndustryCategryId); 
			}
		
		}
		
		LOGGER.trace("RateQuotationServiceImpl::checkEmpIdCorpApprovr::END------------>:::::::");
		return rateIndustryCategryIdList;
	}

	@SuppressWarnings("unchecked")
	public List<RateQuotationListViewTO> searchQuotationForCorp(List<Integer> rateIndustryCategryIdList, String effectiveFrom, String effectiveTo, String status, String quotationNo, String isEQApprover)throws CGBusinessException,
	CGSystemException{
		
		List<RateQuotationListViewTO> rateQuotationListViewTOList = new ArrayList<RateQuotationListViewTO>();

			LOGGER.trace("RateQuotationServiceImpl::searchQuotationForCorp::START------------>:::::::");
			List<RateQuotationWrapperDO> rateQuotationDOs=rateQuotationDAO.searchQuotationDtlsForCorp(rateIndustryCategryIdList, effectiveFrom, effectiveTo, status, quotationNo, isEQApprover);
			if(!CGCollectionUtils.isEmpty(rateQuotationDOs)){
				rateQuotationListViewTOList = (List<RateQuotationListViewTO>)CGObjectConverter.createTOListFromDomainList(rateQuotationDOs, RateQuotationListViewTO.class);
	
			} 
			if(CGCollectionUtils.isEmpty(rateQuotationListViewTOList))
				return null;
		LOGGER.trace("RateQuotationServiceImpl::searchQuotationForCorp::END------------>:::::::");
		 return rateQuotationListViewTOList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationListViewTO> rateQuotationListViewSEDetails(
			Integer userId, String fromDate, String toDate, String quotationNo, String status)
			throws CGBusinessException, CGSystemException {
		List<RateQuotationWrapperDO> rqwDOList = null;
		List<RateQuotationListViewTO> rqwlvTOList = null;
			LOGGER.trace("RateQuotationServiceImpl::rateQuotationListViewSEDetails::START------------>:::::::");
			rqwDOList = rateQuotationDAO.rateQuotationListViewSEDetails(userId, fromDate, toDate, quotationNo, status);
			
			if(!CGCollectionUtils.isEmpty(rqwDOList)){
				//rqwlvTOList = new ArrayList<RateQuotationListViewTO>();
				
				rqwlvTOList = (List<RateQuotationListViewTO>)CGObjectConverter.createTOListFromDomainList(rqwDOList, RateQuotationListViewTO.class);
				
			}
		
		
		LOGGER.trace("RateQuotationServiceImpl::rateQuotationListViewSEDetails::END------------>:::::::");
		return rqwlvTOList;
	}
	
	private void setCustomerType(RateQuotationTO rateQuotationTO, RateQuotationDO quotationDO) throws CGSystemException, CGBusinessException{
		LOGGER.trace("RateQuotationServiceImpl::setCustomerType::START------------>:::::::");
		Integer customerTypeId = null;

		customerTypeId = rateCommonService.getCustomerTypeId(rateQuotationTO
				.getRateQuotationType(), rateQuotationTO.getIndCatCode(), null,
				rateQuotationTO.getCustomer().getBusinessType());
		
		if(!StringUtil.isNull(customerTypeId)){
			CustomerTypeDO ctDO = new CustomerTypeDO();
			ctDO.setCustomerTypeId(customerTypeId);
			if(rateQuotationTO.getQuotationUsedFor().equals("Q")){
				quotationDO.getQuotedCustomer().setCustomerType(ctDO);
			}else{
				quotationDO.getCustomer().setCustomerType(ctDO);
			}
		}
		LOGGER.trace("RateQuotationServiceImpl::setCustomerType::END------------>:::::::");
	}
	
	public RateQuotationDO createCopyQuotation(RateQuotationDO copyQuotationDO, String quotationNo, String process) throws CGSystemException{
		RateQuotationDO quotationDO = new RateQuotationDO();
		RateQuotationRTOChargesDO quotationRTOChargesDO = null;
		try{
			LOGGER.trace("RateQuotationDAOImpl :: createCopyQuotation() :: Start --------> ::::::");
			/* Prepare Customer DO */ 
			BaseCustomerDO customerNewDO = null;
			if(process.equals(RateQuotationConstants.CHAR_Q)){
				customerNewDO = copyQuotationDO.getQuotedCustomer();
				copyQuotationDO.setQuotedCustomer((RateQuotationCustomerDO)customerNewDO);
			}else{
				customerNewDO = copyQuotationDO.getCustomer();
				copyQuotationDO.setCustomer((CustomerDO)customerNewDO);
			}
			
			if(process.equals(RateQuotationConstants.CHAR_Q)){
				customerNewDO.setCustomerId(null);
			}
			customerNewDO.setDtToBranch(CommonConstants.CHARACTER_R);
			if(StringUtil.isStringEmpty(customerNewDO.getCustomerCode())){
				customerNewDO.setCustomerCode(null);
			}
			AddressDO addressDO = null;
			ContactDO primaryContactDO = null;
			ContactDO secondaryContactDO = null;
			if(process.equals(RateQuotationConstants.CHAR_Q)){
				addressDO = copyQuotationDO.getQuotedCustomer().getAddressDO();
				primaryContactDO = copyQuotationDO.getQuotedCustomer().getPrimaryContactDO();
				secondaryContactDO = copyQuotationDO.getQuotedCustomer().getSecondaryContactDO();
			}else{
				addressDO = copyQuotationDO.getCustomer().getAddressDO();
				primaryContactDO = copyQuotationDO.getCustomer().getPrimaryContactDO();
					secondaryContactDO = copyQuotationDO.getCustomer().getSecondaryContactDO();
			}
				

			if (!StringUtil.isNull(addressDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					addressDO.setAddressId(null);
				}
				addressDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setAddressDO(addressDO);
			}

			if (!StringUtil.isNull(secondaryContactDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					secondaryContactDO.setContactId(null);
				}
				secondaryContactDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setSecondaryContactDO(secondaryContactDO);
			}

			if (!StringUtil.isNull(primaryContactDO)) {
				if(process.equals(RateQuotationConstants.CHAR_Q)){
					primaryContactDO.setContactId(null);
				}
				primaryContactDO.setDtToBranch(CommonConstants.NO);
				customerNewDO.setPrimaryContactDO(primaryContactDO);
			}

		/* Prepare RateQuotationFixedCharges DO */
		Set<RateQuotationFixedChargesDO> rqFixedDO = copyQuotationDO.getRateFixedChargeDO();
		Set<RateQuotationFixedChargesDO> rqCopyFixedDO = new HashSet<RateQuotationFixedChargesDO>();
		for (RateQuotationFixedChargesDO fixedChargesDO : rqFixedDO) {
			fixedChargesDO.setRateQuotationFixedChargesId(null);
			fixedChargesDO.setRateQuotationDO(copyQuotationDO);
			rqCopyFixedDO.add(fixedChargesDO);
		}
		copyQuotationDO.setRateFixedChargeDO(rqCopyFixedDO);
		
		/* Prepare RateQuotationProductCategoryHeader DO */
		Set<RateQuotationProductCategoryHeaderDO> rqProductCatDO = copyQuotationDO.getRateQuotationProductCategoryHeaderDO();
		Set<RateQuotationProductCategoryHeaderDO> rqCopyProductCatDO = new HashSet<RateQuotationProductCategoryHeaderDO>();
		Set<RateQuotationWeightSlabDO> rateQuotationWeightSlabDO = new HashSet<RateQuotationWeightSlabDO>();
		Set<RateQuotationSlabRateDO> rateQuotationSlabRateDO = new HashSet<RateQuotationSlabRateDO>();
		Set<RateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO = new HashSet<RateQuotationSpecialDestinationDO>();

		for (RateQuotationProductCategoryHeaderDO categoryHeaderDO : rqProductCatDO) {
			categoryHeaderDO.setRateQuotationProductCategoryHeaderId(null);
			categoryHeaderDO.setRateQuotationDO(copyQuotationDO);
			for (RateQuotationWeightSlabDO quotationWeightSlabDO : categoryHeaderDO.getRateQuotationWeightSlabDO()) {
				quotationWeightSlabDO.setRateQuotationWeightSlabId(null);
				quotationWeightSlabDO.setRateQuotProductCategoryHeaderDO(categoryHeaderDO);
				for (RateQuotationSlabRateDO quotationSlabRateDO : quotationWeightSlabDO.getRateQuotationSlabRateDO()) {
					quotationSlabRateDO.setSlabRateId(null);
					quotationSlabRateDO.setRateQuotationWeightSlabDO(quotationWeightSlabDO);
					quotationSlabRateDO.setRateQuotationProductCategoryHeader(categoryHeaderDO);
					rateQuotationSlabRateDO.add(quotationSlabRateDO);
				}
				for (RateQuotationSpecialDestinationDO destinationDO : quotationWeightSlabDO.getRateQuotationSpecialDestinationDO()) {
					destinationDO.setRateQuotationSpecialDestinationId(null);
					destinationDO.setRateQuotationWeightSlabDO(quotationWeightSlabDO);
					destinationDO.setRateQuotationProductCategoryHeaderDO(categoryHeaderDO);
					rateQuotationSpecialDestinationDO.add(destinationDO);
				}
				rateQuotationWeightSlabDO.add(quotationWeightSlabDO);
			}
			rqCopyProductCatDO.add(categoryHeaderDO);
		}

		copyQuotationDO.setRateQuotationProductCategoryHeaderDO(rqCopyProductCatDO);

		if (copyQuotationDO.getRateQuotationType().equals(RateQuotationConstants.ECOMMERCE)) {
			List<RateQuotationCODChargeDO> chargeDOList = 
					rateQuotationDAO.loadQuotationCodCharge(copyQuotationDO.getRateQuotationId());
			Set<RateQuotationCODChargeDO> chargeDOs = new HashSet<RateQuotationCODChargeDO>(chargeDOList);

			Set<RateQuotationCODChargeDO> rqCODChargeDO = new HashSet<RateQuotationCODChargeDO>();
			for (RateQuotationCODChargeDO do1 : chargeDOs) {
				do1.setRateQuotationCODChargeId(null);
				do1.setRateQuotationDO(copyQuotationDO);
				rqCODChargeDO.add(do1);

			}
			copyQuotationDO.setCodChargeDO(rqCODChargeDO);
		}

		/* Prepare RateQuotationProductCategoryHeader DO */
		/*quotationRTOChargesDO = loadRTOChargesDefault(copyQuotationDO
				.getRateQuotationId().toString());
		if(!StringUtil.isNull(quotationRTOChargesDO)){
		quotationRTOChargesDO.setRateQuotationRTOChargesId(null);
		copyQuotationDO.setRateQuotationRtoChargesDO(quotationRTOChargesDO);
		quotationDO
				.setRateQuotationId(copyQuotationDO.getRateQuotationId());
		quotationRTOChargesDO.setRateQuotationDO(copyQuotationDO);
		}*/
		if(!StringUtil.isNull(copyQuotationDO.getRateQuotationRtoChargesDO())){
			quotationRTOChargesDO = copyQuotationDO.getRateQuotationRtoChargesDO();
			quotationRTOChargesDO.setRateQuotationRTOChargesId(null);
			copyQuotationDO.setRateQuotationRtoChargesDO(quotationRTOChargesDO);
			quotationRTOChargesDO.setRateQuotationDO(copyQuotationDO);
			}
		quotationDO
		.setRateQuotationId(copyQuotationDO.getRateQuotationId());
		/*RateQuotationFixedChargesConfigDO chargesConfigDO=loadDefaultFixedChargesConfigValue(copyQuotationDO
		.getRateQuotationId());
		if(!StringUtil.isNull(chargesConfigDO)){
			chargesConfigDO.setQuotaionFixedChargesConfigId(null);
			copyQuotationDO.setFixedChargesConfigDO(chargesConfigDO);
			quotationDO
					.setRateQuotationId(copyQuotationDO.getRateQuotationId());
			chargesConfigDO.setRateQuotationDO(copyQuotationDO);
			}*/
		Set<RateQuotationFixedChargesConfigDO> chargesConfigDO=copyQuotationDO.getFixedChargesConfigDO();
		if(!CGCollectionUtils.isEmpty(chargesConfigDO)){
		Set<RateQuotationFixedChargesConfigDO> fixedChargesConfigDO=new HashSet<RateQuotationFixedChargesConfigDO>();
		for(RateQuotationFixedChargesConfigDO fccDO :  chargesConfigDO){
			fccDO.setQuotaionFixedChargesConfigId(null);
			fixedChargesConfigDO.add(fccDO);
		}
		copyQuotationDO.setFixedChargesConfigDO(fixedChargesConfigDO);
		}
		
		copyQuotationDO.setRateQuotationId(null);

		if (copyQuotationDO.getRateQuotationType().equals(RateQuotationConstants.ECOMMERCE)) {
			copyQuotationDO.setRateQuotationType(RateQuotationConstants.ECOMMERCE);

		} else {
			copyQuotationDO.setRateQuotationType(RateQuotationConstants.NORMAL);
		}
		
		if(quotationNo.equals(copyQuotationDO.getRateQuotationNo())){
			copyQuotationDO.setQuotationUsedFor(RateQuotationConstants.CONTRACT);
		}
		else{
			copyQuotationDO.setQuotationUsedFor(RateQuotationConstants.QUOTATION);
			copyQuotationDO.setRateQuotationOriginatedfromType(RateQuotationConstants.COPY);
			copyQuotationDO.setStatus(RateQuotationConstants.NEW);
			copyQuotationDO.setRatequotationOriginatedFrom(quotationDO);
			copyQuotationDO.setExcecutiveRemarks(null);
			copyQuotationDO.setApproversRemarks(null);
			copyQuotationDO.setApprovalRequired(null);
			copyQuotationDO.setApprovedAt(null);
		}
		copyQuotationDO.setQuotationCreatedDate(DateUtil.getCurrentDate());
		copyQuotationDO.setRateQuotationNo(quotationNo);
		} catch (Exception e) {
			LOGGER.error("Error occured in RateQuotationDAOImpl :: createCopyQuotation()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		LOGGER.trace("RateQuotationDAOImpl :: createCopyQuotation() :: END --------> ::::::");
		return copyQuotationDO;
	}

	@Override
	public RateQuotationDO getContractQuotation(RateQuotationTO rateQuotationTO) throws CGBusinessException,
			CGSystemException {
		RateQuotationDO copyQuotationDO = rateQuotationDAO.getRateQuotation(rateQuotationTO.getRateQuotationNo(),RateQuotationConstants.CHAR_C,rateQuotationTO.getRateQuotationId());
		return createCopyQuotation(copyQuotationDO, rateQuotationTO.getRateQuotationNo(), RateQuotationConstants.CHAR_C);
	}
	
	private void prepareCODChargesList(RateQuotaionFixedChargesTO fixedChargesTO, List<RateQuotationCODChargeTO> codList, RateQuotationDO rateQuotationDO, Set<RateQuotationCODChargeDO> quotationCOD){
		RateQuotationCODChargeDO quotationCodDO = null;
		Integer updatedby = fixedChargesTO.getRateQuotation().getUpdatedBy();
		int codLen = codList.size();
		for (int i = 0; i < codLen; i++) {
			quotationCodDO = new RateQuotationCODChargeDO();

			CodChargeDO codChargeDO = new CodChargeDO();
			codChargeDO.setCodChargeId(codList.get(i).getCodChargeId());
			quotationCodDO.setCodChargeDO(codChargeDO);

			quotationCodDO.setPercentileValue(codList.get(i)
					.getPercentileValue());
			quotationCodDO.setFixedChargeValue(codList.get(i)
					.getFixedChargeValue());
			quotationCodDO.setConsiderFixed(codList.get(i).getConsiderFixed());
			quotationCodDO.setConsiderHigherFixedOrPercent(codList.get(i)
					.getConsideeHigherFixedPercent());
			quotationCodDO.setCreatedBy(updatedby);
			quotationCodDO.setUpdatedBy(updatedby);
			quotationCodDO.setRateQuotationDO(rateQuotationDO);
			quotationCOD.add(quotationCodDO);
		}
	}
	
	private void prepareCustomerDO(RateQuotationTO rateQuotationTO, RateQuotationDO rateQuotationDO, BaseCustomerDO customerNewDO) {

		String quotUsedFor = rateQuotationTO.getQuotationUsedFor();
		
		CustomerTO custTO = null;
		custTO = rateQuotationTO.getCustomer();
		if(!StringUtil.isNull(rateQuotationDO.getCustomer())
		   && !StringUtil.isStringEmpty(rateQuotationDO.getCustomer().getStatus()) && rateQuotationDO.getQuotationUsedFor().equals("C")){
			customerNewDO.setStatus(rateQuotationDO.getCustomer().getStatus());
		}else if(!StringUtil.isNull(rateQuotationDO.getQuotedCustomer())
				   && !StringUtil.isStringEmpty(rateQuotationDO.getQuotedCustomer().getStatus()) && rateQuotationDO.getQuotationUsedFor().equals("Q")){
			customerNewDO.setStatus(rateQuotationDO.getQuotedCustomer().getStatus());
		}else{
			customerNewDO.setStatus(RateQuotationConstants.STATUS_INACTIVE);
			customerNewDO.setCreatedBy(rateQuotationTO.getCreatedBy());
			customerNewDO.setCreatedDate(new Date());
		}
		customerNewDO.setUpdatedBy(rateQuotationTO.getUpdatedBy());
		customerNewDO.setUpdatedDate(new Date());
		if (!StringUtil.isNull(custTO.getBusinessType())) {
			customerNewDO.setBusinessType(rateQuotationTO.getCustomer()
					.getBusinessType());
		}else{
			customerNewDO.setBusinessType(null);
		}

		if (!StringUtil.isNull(custTO.getSalesOffice())) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(rateQuotationTO.getCustomer().getSalesOffice()
					.getOfficeId());
			customerNewDO.setSalesOfficeDO(officeDO);
		}else{
			customerNewDO.setSalesOfficeDO(null);
		}

		if (!StringUtil.isNull(custTO.getSalesPerson())) {
			EmployeeDO employeeDO = new EmployeeDO();
			employeeDO.setEmployeeId(rateQuotationTO.getCustomer()
					.getSalesPerson().getEmployeeId());
			customerNewDO.setSalesPersonDO(employeeDO);
		}else{
			customerNewDO.setSalesPersonDO(null);
		}

		if (!StringUtil.isNull(custTO.getIndustryCategory())) {
			RateIndustryCategoryDO industryCategoryDO = new RateIndustryCategoryDO();
			industryCategoryDO
					.setRateIndustryCategoryId(Integer.parseInt(rateQuotationTO
							.getCustomer().getIndustryCategory()
							.split(CommonConstants.TILD)[0]));

			RateCustomerCategoryDO categoryDO = new RateCustomerCategoryDO();
			categoryDO
					.setRateCustomerCategoryId(Integer.parseInt(rateQuotationTO
							.getCustomer().getIndustryCategory()
							.split(CommonConstants.TILD)[1]));
			customerNewDO.setIndustryCategoryDO(industryCategoryDO);
			customerNewDO.setCustomerCategoryDO(categoryDO);
		}else{
			customerNewDO.setCustomerCategoryDO(null);				
		}

		if (!StringUtil.isNull(custTO.getIndustryType())) {
			RateIndustryTypeDO industryTypeDO = new RateIndustryTypeDO();

			industryTypeDO.setRateIndustryTypeId(Integer
					.parseInt(custTO.getIndustryType()
							.split(CommonConstants.TILD)[0]));
			customerNewDO.setIndustryTypeDO(industryTypeDO);
		}else{
			customerNewDO.setIndustryTypeDO(null);				
		}

		if (!StringUtil.isNull(custTO.getAddress())) {
			AddressDO addressDO = new AddressDO();
			PickupDeliveryAddressTO addressTO = custTO.getAddress();
			addressDO.setAddress1(addressTO.getAddress1());
			addressDO.setAddress2(addressTO.getAddress2());
			addressDO.setAddress3(addressTO.getAddress3());
			PincodeDO pincodeDO = new PincodeDO();
			pincodeDO.setPincodeId(addressTO.getPincode().getPincodeId());
			CityDO cityDO = new CityDO();
			cityDO.setCityId(addressTO.getCity().getCityId());
			addressDO.setPincodeDO(pincodeDO);
			addressDO.setCityDO(cityDO);
			if(!StringUtil.isNull(rateQuotationDO.getQuotedCustomer()) && (quotUsedFor.equals("Q")) 
					&& !StringUtil.isNull(rateQuotationDO.getQuotedCustomer().getAddressDO()) 
					&& !StringUtil.isNull(rateQuotationDO.getQuotedCustomer().getAddressDO().getAddressId())){
				addressDO.setAddressId(rateQuotationDO.getQuotedCustomer().getAddressDO().getAddressId());
			}else if(!StringUtil.isNull(rateQuotationDO.getCustomer()) 
					&& !StringUtil.isNull(rateQuotationDO.getCustomer().getAddressDO()) 
					&& !StringUtil.isNull(rateQuotationDO.getCustomer().getAddressDO().getAddressId())){
				addressDO.setAddressId(rateQuotationDO.getCustomer().getAddressDO().getAddressId());
			}
			customerNewDO.setAddressDO(addressDO);
		}else{
			customerNewDO.setAddressDO(null);				
		}

		if (!StringUtil.isNull(custTO.getPrimaryContact().getTitle())) {
			ContactDO contactDO = new ContactDO();
			ContactTO primaryContractTO = custTO.getPrimaryContact();	
			contactDO.setTitle(primaryContractTO.getTitle());
			contactDO.setDesignation(primaryContractTO.getDesignation());
			contactDO.setDepartment(primaryContractTO.getDepartment());
			contactDO.setEmail(primaryContractTO.getEmail());
			contactDO.setMobile(primaryContractTO.getMobile());
			contactDO.setExtension(primaryContractTO.getExtension());
			contactDO.setContactId(primaryContractTO.getContactId());
			contactDO.setFax(primaryContractTO.getFax());
			contactDO.setName(primaryContractTO.getName());
			contactDO.setContactNo(primaryContractTO.getContactNo());
			customerNewDO.setPrimaryContactDO(contactDO);
		}else{
			customerNewDO.setPrimaryContactDO(null);
		}
		if (!StringUtil.isNull(custTO
				.getSecondaryContact().getTitle())) {
			ContactDO contactDO = new ContactDO();
			ContactTO secContractTO = custTO.getSecondaryContact();	
			contactDO.setTitle(secContractTO.getTitle());
			contactDO.setDesignation(secContractTO.getDesignation());
			contactDO.setDepartment(secContractTO.getDepartment());
			contactDO.setEmail(secContractTO.getEmail());
			contactDO.setMobile(secContractTO.getMobile());
			contactDO.setExtension(secContractTO.getExtension());
			contactDO.setContactId(secContractTO.getContactId());
			contactDO.setFax(secContractTO.getFax());
			contactDO.setName(secContractTO.getName());
			contactDO.setContactNo(secContractTO.getContactNo());
			customerNewDO.setSecondaryContactDO(contactDO);
		}else{
			customerNewDO.setSecondaryContactDO(null);
		}
		if (!StringUtil.isNull(custTO.getGroupKey())) {
			CustomerGroupDO customerGroupDO = new CustomerGroupDO();
			customerGroupDO.setCustomerGroupId(custTO
					.getGroupKey().getCustomerGroupId());
			customerNewDO.setGroupKeyDO(customerGroupDO);
		}else{
			customerNewDO.setGroupKeyDO(null);
		}
		if (!StringUtil.isNull(custTO.getBusinessName())) {
			customerNewDO.setBusinessName(custTO
					.getBusinessName());
		}else{
			customerNewDO.setBusinessName(null);
		}

		if (!StringUtil.isNull(custTO.getPanNo())) {
			customerNewDO.setPanNo(custTO.getPanNo());
		}
		
		if (!StringUtil.isNull(custTO.getTanNo())) {
			customerNewDO.setTanNo(custTO.getTanNo());
		}
		
		if (!StringUtil.isNull(custTO.getCustomerCode())) {
			customerNewDO.setCustomerCode(custTO.getCustomerCode());
		}
		
		if (!StringUtil.isNull(custTO.getLegacyCustomerCode())) {
			customerNewDO.setLegacyCustomerCode(custTO.getLegacyCustomerCode());
		}
		
		if (!StringUtil.isNull(custTO.getSalesOffice())) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(custTO.getSalesOffice()
					.getOfficeId());
			customerNewDO.setSalesOfficeDO(officeDO);
		}else{
			customerNewDO.setSalesOfficeDO(null);
		}
		if (!StringUtil.isEmptyInteger(custTO
				.getCustomerId())) {
			customerNewDO.setCustomerId((custTO
					.getCustomerId()));
		}
	
	}

	@Async
	public void proceedUploadRateQuotation(RateQuotationTO rateQuotationTO,
			OfficeTO loggedInOffice, String filePath, String jobNumber)
			throws CGBusinessException, CGSystemException {
		int errorSize = 0;
		
		//String jobNumber = getJobNumber();
		try {
			
			
			
			List<Object> list = setUpRateQuotationDos(rateQuotationTO, loggedInOffice, filePath, jobNumber);
			
			List<RateQuotationDO> dataList = (List)list.get(0);
			int totalCount = dataList.size();
			resetJobStatus("Data read and validation completed", jobNumber, null, null, 90, "I");
			//BulkReturnObject failedBookings = handleBulkBooking(list, null, null, jobNumber);
			//List failedBookings = handleBulkBooking(list, null, null, jobNumber);
			List<List<String>> errorList = (List)list.get(1); //failedBookings.getErrList();
			List<List> errList = null;
			
			ByteArrayOutputStream errorByteStream = null;
			ByteArrayOutputStream successByteStream = null;
			if(errorList != null && !errorList.isEmpty()) {
				errList = new ArrayList<List>();
				
				List<String> headerList = getQuotationHeaderList();
				headerList.add("ERROR_DESC");
				errList.add(headerList);
				errorSize = ((List)list.get(1)).size();
				for(List<String> eList : errorList){
					errList.add(eList);
				}
				
				XSSFWorkbook xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errList);
				errorByteStream = new ByteArrayOutputStream();
				xssfWorkbook.write(errorByteStream);
				LOGGER.debug("byte array size====>"+ errorByteStream.size());
				errList = null;
				//uploadFile("D:/Users/rmaladi/Desktop/folder", "quotationUploadFile"+DateUtil.getCurrentTimeInMilliSeconds()+".xls", byteStream.toByteArray());
			}
			if(dataList != null && !dataList.isEmpty()){
				List<String> headerList = getQuotationHeaderList();
				List<List<String>> successList = (List)list.get(2); 
				headerList.add("QUOTATION_NUMBER");
				headerList.add("ERROR_CODE");
				int quotSize =  dataList.size();
				for(int i=0; i<quotSize; i++ ){
					
					try{
					rateQuotationDAO.saveOrUpdateBasicInfo(dataList.get(i));
					}catch(Exception e){
						successList.get(i).add(e.getCause().getCause().getMessage());
					}
				}
				
				dataList = null;
				//rateQuotationDAO.saveBulkRateQuotaions(dataList);
				
				List<List> sucList = new ArrayList<List>();
				
				//headerList.add("QUOTATION_NUMBER");
				sucList.add(headerList);
				//suceessSize = ((List)list.get(2)).size();
				for(List<String> eList : successList){
					sucList.add(eList);
				}
				
				XSSFWorkbook xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(sucList);
				successByteStream = new ByteArrayOutputStream();
				xssfWorkbook.write(successByteStream);
				
				sucList = null;
				
				
			}
			String message= null;
			if(errorList == null || errorList.isEmpty()) {
				message = "All Quotation created successfully";
				resetJobStatus(message, jobNumber, null, successByteStream, 100, "S");
			} 
			if(!errorList.isEmpty()){
				message = errorSize  + " quotation(s) failed out of " +  (totalCount+errorSize) ;//createFailureMessage(errorList);
				resetJobStatus(message, jobNumber, errorByteStream, successByteStream, 100, "F");
			}
			
			
		} catch (CGBusinessException e) {
			String message = e.getMessage();
			resetJobStatus(message, jobNumber, null, null, 100, "F");
			LOGGER.error("Error occured in RateQuotationDAOImpl :: proceedUploadRateQuotation()..:"
					,e);
		} catch (CGSystemException e) {
			String message = e.getMessage();
			resetJobStatus(message, jobNumber, null, null, 100, "F");
			LOGGER.error("Error occured in RateQuotationDAOImpl :: proceedUploadRateQuotation()..:"
					,e);
		} catch (IOException e) {
			String message = "Unable to perform quotation upload operation";//ExceptionUtil.getMessageFromException(e);
			resetJobStatus(message, jobNumber, null, null, 100, "F");
			LOGGER.error("Error occured in RateQuotationDAOImpl :: proceedUploadRateQuotation()..:"
					,e);
			
		}
	
		
	}
	
	public boolean uploadFile(String dirPath, String fileName,
			byte[] formFile) throws IOException {
		LOGGER.trace("CGExcelUploadUtil :: uploadFile() :: START");
		boolean upload = Boolean.FALSE;
		FileOutputStream outputStream = null;
		try {
			File file = new File(dirPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String path = dirPath + file.separator + fileName;
			file = new File(path);
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(formFile);
			upload = Boolean.TRUE;
		} finally {
			if(outputStream!=null)
			outputStream.close();
		}
		LOGGER.trace("CGExcelUploadUtil :: uploadFile() :: END");
		return upload;
	}
	
	private List<Object> setUpRateQuotationDos(
			RateQuotationTO rateQuotationTO,
			OfficeTO loggedInOffice,
			 String filePath, String jobNumber) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BulkBookingAction::setUpBulkBookingTos::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Excel parsing
		final FormFile myFile = rateQuotationTO.getQuotationUploadFile();
		final String fileName = myFile.getFileName();		
		final String fileUrl = filePath + fileName;
		Boolean isValidHeader = Boolean.FALSE;
		List<Object> list = null;
		
		LOGGER.debug("RateQuotationServiceImpl::getAllRowsValues::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<List> quotationDetails = CGExcelUploadUtil.getAllRowsValues(fileUrl,
				myFile);
		resetJobStatus("Reading data from excel completed", jobNumber, null, null, 20, "I");
		LOGGER.debug("RateQuotationServiceImpl::getAllRowsValues::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> excelHeaderList = quotationDetails.get(0);
		isValidHeader = validateQuotationFileUploadHeader(excelHeaderList);
		resetJobStatus("Data Validation is started", jobNumber, null, null, 25, "I");
		if (isValidHeader.equals(Boolean.TRUE)) {
			resetJobStatus("Data Processing is started", jobNumber, null, null, 30, "I");
			list = rateQuotationDOConvertor(quotationDetails);
							
		} else {
			throw new CGBusinessException("Quotation upload Failed");
			//throw new CGBusinessException("Invalid Header");
		}
		LOGGER.debug("RateQuotationServiceImpl::setUpRateQuotationTos::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return list;

	}
	
	
	private List<Object> rateQuotationDOConvertor(List<List> quotationDetails) throws CGBusinessException, CGSystemException{
		List<Object> list = new ArrayList<>();
		List<RateQuotationDO> quotationDOs = new ArrayList<>(quotationDetails.size());
		List<List> invalidQuotationList = new ArrayList<>(quotationDetails.size());
		List<List> validQuotationList = new ArrayList<>(quotationDetails.size());
		
		RateQuotationDO rateQuotationDO = null;
		RateQuotationCustomerDO customerDO = null;
		AddressDO addressDO = null;
		ContactDO primaryContactDO = null;
		ContactDO secondaryContactDO = null;
		
		
		
		String customerCode = null;
		String salesOfcCode = null;
		String salesEmpCode = null;
		String indTypeCode = null;
		String custGroup = null;
		String custType = null;
		String custName = null;
		String address1 = null;
		String address2 = null;
		String address3 = null;
		String cityCode = null;
		String pincode = null;
		String contactNo = null;
		String extNo = null;
		String mobile = null;
		String fax = null;
		String email = null;
		String primaryTitle = null;
		String primaryDeptCode = null;
		String primaryPersonName = null;
		String primaryDesignation = null;
		String primaryEmail = null;
		String primaryContactNo = null;
		String primaryExtNo = null;
		String primaryMobile = null;
		String primaryFax = null;
		String secondaryTitle = null;
		String secondaryDeptCode = null;
		String secondaryPersonName = null;
		String secondaryDesignation = null;
		String secondaryEmail = null;
		String secondaryContactNo = null;
		String secondaryExtNo = null;
		String secondaryMobile = null;
		String secondaryFax = null;
		String billingCycle = null;
		String legacyCustCode = null;
		String panNo = null;
		String tanNo = null;
		String businessType = null;
		String indCategory = null;
		String custCategory = null;
		String quotationType = null;
		String ownerType = null;
		String salesUserName = null;
		String salesUserOfcCode = null;
		UserTO userTO = null;
		EmployeeTO empTO = null;
		OfficeTO ofcTO = null;
		RateIndustryTypeTO indTypeTO = null;
		CustomerGroupTO custGroupTO = null;
		RateCustomerCategoryTO custCatTO = null;
		RateIndustryCategoryTO indCatTO = null;
		CustomerTypeTO custTypeTO = null;
		
		List<String> invalidQuotations = null;
		List<String> errorCodes = null;
		RateIndustryCategoryDO indCatDO = null;
		
		Integer offcId = null;
		Integer ofcId = null;
		Integer userId = null;
		
		
		for(List<String> quotTO : quotationDetails){
			
			if (!StringUtil.isStringEmpty(quotTO.get(0))) {
				if (quotTO.get(0).equalsIgnoreCase(
						RateQuotationExcelConstants.CUSTOMER_NO))
					continue;
			}
			
			invalidQuotations = new ArrayList<String>();
			errorCodes = new ArrayList<String>();
			rateQuotationDO = new RateQuotationDO();
			customerDO = new RateQuotationCustomerDO();
			primaryContactDO = new ContactDO();
			secondaryContactDO = new ContactDO();
			
			addressDO = new AddressDO();
			
			 customerCode = quotTO.get(0);
			 
			 if(!StringUtil.isStringEmpty(customerCode.trim())){
				 customerDO.setCustomerCode(customerCode.trim());
			 }
			 invalidQuotations.add(StringUtils.isNotEmpty(customerCode) ? customerCode
						: CommonConstants.EMPTY_STRING);
			 
			 
			 
			 
			 salesOfcCode = quotTO.get(1);
			 
			 invalidQuotations.add(salesOfcCode); 
			 if(!StringUtil.isStringEmpty(salesOfcCode.trim())){
				 salesOfcCode = salesOfcCode.replaceAll("\\u00a0","");
				 offcId = rateQuotationDAO.getOfcIdByOfcCode(salesOfcCode.trim());
				if(!StringUtil.isNull(offcId)){
			 		OfficeDO ofcDO = new OfficeDO();
			 		ofcDO.setOfficeId(offcId);
			 		customerDO.setSalesOfficeDO(ofcDO);
			 	}else{
					 errorCodes.add("Sales office code is not valid");
				 }
		 	 }else{
		 		 errorCodes.add("Sales office code is required");		 		 
		 	 }
			 
			 
			 salesEmpCode = quotTO.get(2);
			 invalidQuotations.add(salesEmpCode); 
			 if(!StringUtil.isStringEmpty(salesEmpCode.trim())){
				 salesEmpCode = salesEmpCode.replaceAll("\\u00a0","");
				 empTO = rateCommonService.getEmployeeByEmpCode(salesEmpCode.trim());
				 if(!StringUtil.isNull(empTO)){
					 //salesEmpId = empTO.getEmployeeId();
					 EmployeeDO employeeDO = new EmployeeDO();
					 employeeDO = (EmployeeDO)CGObjectConverter.createDomainFromTo(empTO, employeeDO);
					 customerDO.setSalesPersonDO(employeeDO);
				 }else{
					 errorCodes.add("sales employee code not valid");
				 }
			 }else{
				 errorCodes.add("sales employee code is required");		 		  
		 	 }
			 
			 
			 indTypeCode = quotTO.get(3);
			 invalidQuotations.add(indTypeCode); 
			 if(!StringUtil.isStringEmpty(indTypeCode.trim())){
				 String indCode = indTypeCode.trim();
				 if(indCode.contains("-")){
					 indCode = indTypeCode.substring(0, indTypeCode.indexOf("-")).trim();
				 }
				 indCode = indCode.replaceAll("\\u00a0","");
				 indTypeTO = rateCommonService.getRateIndustryTypeByCode(indCode.trim());
				 if(!StringUtil.isNull(indTypeTO)){
					 RateIndustryTypeDO indTypeDO = new RateIndustryTypeDO();
					 indTypeDO = (RateIndustryTypeDO)CGObjectConverter.createDomainFromTo(indTypeTO, indTypeDO);
					 customerDO.setIndustryTypeDO(indTypeDO);
				 }else{
					errorCodes.add("industry type code is not valid");
				 }
			 }else{
				 errorCodes.add("industry type code is required");		 		  
		 	 }
			 
			 custGroup = quotTO.get(4);
			 invalidQuotations.add(custGroup); 
			 if(!StringUtil.isStringEmpty(custGroup.trim())){
				 custGroup = custGroup.replaceAll("\\u00a0","");
				 custGroupTO = rateCommonService.getCustomerGroupByCode(custGroup.trim());
				 if(!StringUtil.isNull(custGroupTO)){
					 CustomerGroupDO custGropuDO = new CustomerGroupDO(); 
					 custGropuDO = (CustomerGroupDO)CGObjectConverter.createDomainFromTo(custGroupTO, custGropuDO);					 
					 customerDO.setGroupKeyDO(custGropuDO);
				 }else{
					 errorCodes.add("customer group is not valid");
				 }
			 }else{
				 errorCodes.add("customer group is required");		 		  
		 	 }
			 
			 custType = quotTO.get(5);
			 invalidQuotations.add(custType); 
			 if(!StringUtil.isStringEmpty(custType.trim())){
				 String custTypeCode = custType.trim();
				 if(custTypeCode.contains("-")){
					 custTypeCode = custType.substring(0, custType.indexOf("-")).trim();
				 }
				 custTypeCode = custTypeCode.replaceAll("\\u00a0","");
				 custTypeTO = rateCommonService.getCustomerTypeByCode(custTypeCode.trim());
				 if(!StringUtil.isNull(custTypeTO)){
					 CustomerTypeDO custTypeDO = new CustomerTypeDO();
					 custTypeDO = (CustomerTypeDO)CGObjectConverter.createDomainFromTo(custTypeTO, custTypeDO);					 
					 customerDO.setCustomerType(custTypeDO);
				 }else{
					 errorCodes.add("customer type is not valid");
				 }
			 }/*else{
				 errorCodes.add("customer type is required");		 		
		 	 }*/
			 
			 
			 custName = quotTO.get(6);
			 invalidQuotations.add(custName); 
			 if(!StringUtil.isStringEmpty(custName.trim())){
				 customerDO.setBusinessName(custName);
			 }else{
				 errorCodes.add("customer name  is required"); 
		 	 }
			 
			
			 
			 address1 = quotTO.get(7);
			 invalidQuotations.add(address1); 
			 if(!StringUtil.isStringEmpty(address1.trim())){
				 addressDO.setAddress1(address1.trim());
			 }else{
				 errorCodes.add("address1 is required"); 
		 	 }
			 
			 address2 = quotTO.get(8);
			 invalidQuotations.add(address2); 
			 if(!StringUtil.isStringEmpty(address2.trim())){
				 addressDO.setAddress2(address2.trim());
			 }else{
				 errorCodes.add("address2 is required"); 
		 	 }
			 
			 address3 = quotTO.get(9);			 
			 invalidQuotations.add(address3); 
			 if(!StringUtil.isStringEmpty(address3.trim())){
				 addressDO.setAddress3(address3.trim());				 
			 }else{
				 errorCodes.add("address3 is required");
		 	 }
			 
			 cityCode = quotTO.get(10);
			 invalidQuotations.add(cityCode); 
			 
			 pincode = quotTO.get(11);
			 invalidQuotations.add(pincode); 
			 if(!StringUtil.isStringEmpty(pincode.trim())){
				 PincodeTO pincodeTO = new PincodeTO();
				 pincode = pincode.replaceAll("\\u00a0","");
				 pincodeTO.setPincode(pincode.trim());
				 pincodeTO = rateCommonService.validatePincode(pincodeTO);
				 
				 if(!StringUtil.isNull(pincodeTO)){
					 PincodeDO pincodeDO = new PincodeDO();
					 pincodeDO = (PincodeDO)CGObjectConverter.createDomainFromTo(pincodeTO, pincodeDO);
					 addressDO.setPincodeDO(pincodeDO);
				 
					 CityTO cityTO = rateCommonService.getCityByPincode(pincode);
					 if(!StringUtil.isNull(cityTO)){
						 CityDO cityDO = new CityDO();
						 cityDO = (CityDO)CGObjectConverter.createDomainFromTo(cityTO, cityDO);
						 addressDO.setCityDO(cityDO);
					 }else{
						 errorCodes.add("city details not found for given pincode"); 
					 }
				 }else{
					 errorCodes.add("pincode is not valid");
				 }
			 }else{
				 errorCodes.add("pincode is required");
		 	 }
			 
			 contactNo = quotTO.get(12);
			 invalidQuotations.add(contactNo); 
			 if(!StringUtil.isStringEmpty(contactNo.trim())){
				 contactNo = contactNo.replaceAll("\\u00a0","");
				 contactNo = contactNo.trim();
				 if(StringUtils.isNumeric(contactNo)){
					 customerDO.setPhone(contactNo);
				 }else{
					 errorCodes.add("contact Number is not Valid");
				 }
			 }
			 
			 extNo = quotTO.get(13);
			 if(!StringUtil.isStringEmpty(extNo.trim()) && !StringUtils.isNumeric(extNo.trim())){
				 errorCodes.add("extention no is not Valid");
			 }
			 invalidQuotations.add(StringUtils.isNotEmpty(extNo)? extNo :CommonConstants.EMPTY_STRING);
			 
			 
			 
			 mobile = quotTO.get(14);
			 invalidQuotations.add(mobile); 
			 if(!StringUtil.isStringEmpty(mobile.trim())){
				 mobile = mobile.replaceAll("\\u00a0","");
				 mobile = mobile.trim();
				 if(StringUtils.isNumeric(mobile) && (mobile.length() == 10)){
					 customerDO.setMobile(mobile);
				 }else{
					 errorCodes.add("mobile number is not Valid");
				 }
			 }
			 
			 fax = quotTO.get(15);
			 invalidQuotations.add(fax); 
			 if(!StringUtil.isStringEmpty(fax.trim())){
				 fax = fax.replaceAll("\\u00a0","");
				 fax = fax.trim();				 
				 if(StringUtils.isNumeric(fax)){
					 customerDO.setFax(fax);
				 }else{
					 errorCodes.add("fax number is not Valid");
				 }
			 }
			 
			 email = quotTO.get(16);
			 invalidQuotations.add(email); 
			 if(!StringUtil.isStringEmpty(email.trim())){
				 email = email.replaceAll("\\u00a0","");
				 email = email.trim();
				 if(validateEmail(email)){
					 customerDO.setEmail(email);
				 }else{
					 errorCodes.add("email is not Valid"); 
				 }
			 }
			 
			 primaryTitle = quotTO.get(17);
			 invalidQuotations.add(primaryTitle); 
			 if(!StringUtil.isStringEmpty(primaryTitle.trim())){
				 primaryContactDO.setTitle(primaryTitle.trim());
			 }else{
				 errorCodes.add("primary contact Title is required"); 
		 	 }
			 
			 primaryDeptCode = quotTO.get(18);
			 invalidQuotations.add(primaryDeptCode); 
			 if(!StringUtil.isStringEmpty(primaryDeptCode.trim())){
				 String primaryDptCode = primaryDeptCode.trim();
				 if(primaryDptCode.contains("-")){
					 primaryDptCode = primaryDptCode.substring(0, primaryDptCode.indexOf("-")).trim();
				 }
				 primaryDptCode = primaryDptCode.replaceAll("\\u00a0","");
				 DepartmentTO deptTO = rateCommonService.getDepartmentByCode(primaryDptCode.trim());
				 if(!StringUtil.isNull(deptTO)){
					 //DepartmentDO deptDO = new DepartmentDO();
					 //deptDO = (DepartmentDO)CGObjectConverter.createDomainFromTo(deptTO, deptDO);
					 primaryContactDO.setDepartment(deptTO.getDepartmentId().toString());
				 }
				 
			 }else{
				 errorCodes.add("primary contact Department Code is required");
		 	 }
			 
			 primaryPersonName = quotTO.get(19);
			 invalidQuotations.add(primaryPersonName); 
			 if(!StringUtil.isStringEmpty(primaryPersonName.trim())){
				 primaryContactDO.setName(primaryPersonName.trim());
			 }else{
				 errorCodes.add("primary contact PersonName is required"); 
		 	 }
			 
			 primaryDesignation = quotTO.get(20);
			 invalidQuotations.add(primaryDesignation); 
			 if(!StringUtil.isStringEmpty(primaryDesignation.trim())){
				 primaryContactDO.setDesignation(primaryDesignation.trim());
			 }else{
				 errorCodes.add("primary contact Designation is required"); 
		 	 }
			 
			 primaryEmail = quotTO.get(21);
			 invalidQuotations.add(primaryEmail); 
			 if(!StringUtil.isStringEmpty(primaryEmail.trim())){
				 primaryEmail = primaryEmail.replaceAll("\\u00a0","");
				 primaryEmail = primaryEmail.trim();
				 if(validateEmail(primaryEmail)){
					 primaryContactDO.setEmail(primaryEmail);
				 }else{
					 errorCodes.add("primary Contact Email is not valid"); 
				 }
			 }else{
				 errorCodes.add("primary Contact Email is required"); 
		 	 }
			 
			 primaryContactNo = quotTO.get(22);
			 invalidQuotations.add(primaryContactNo); 
			 if(!StringUtil.isStringEmpty(primaryContactNo.trim())){
				 primaryContactNo = primaryContactNo.replaceAll("\\u00a0","");
				 primaryContactNo = primaryContactNo.trim();
				 if(StringUtils.isNumeric(primaryContactNo)){
					 primaryContactDO.setContactNo(primaryContactNo);
				 }else{
					 errorCodes.add("primary contact ContactNo is not valid");
				 }
			 }else{
				 errorCodes.add("primary contact ContactNo is required");
		 	 }
			 
			 primaryExtNo = quotTO.get(23);
			 invalidQuotations.add(primaryExtNo); 
			 if(!StringUtil.isStringEmpty(primaryExtNo.trim())){
				 primaryExtNo = primaryExtNo.replaceAll("\\u00a0","");
				 primaryExtNo = primaryExtNo.trim();
				 if(StringUtils.isNumeric(primaryExtNo)){
					 primaryContactDO.setExtension(primaryExtNo);
				 }else{
					 errorCodes.add("primary contact extention No is not valid");
				 }
			 }
			 
			 primaryMobile = quotTO.get(24);
			 invalidQuotations.add(primaryMobile); 
			 if(!StringUtil.isStringEmpty(primaryMobile.trim())){
				 primaryMobile = primaryMobile.replaceAll("\\u00a0","");
				 primaryMobile = primaryMobile.trim();
				 if(StringUtils.isNumeric(primaryMobile) && (primaryMobile.length() == 10)){
					 primaryContactDO.setMobile(primaryMobile);
				 }else{
					 errorCodes.add("primary contact mobile No is not valid");
				 }
			 }
			 
			 primaryFax = quotTO.get(25);
			 invalidQuotations.add(primaryFax);
			 if(!StringUtil.isStringEmpty(primaryFax.trim())){
				 primaryFax = primaryFax.replaceAll("\\u00a0","");
				 primaryFax = primaryFax.trim();
				 if(StringUtils.isNumeric(primaryFax)){
					 primaryContactDO.setFax(primaryFax);
				 }else{
					 errorCodes.add("primary contact fax No is not valid"); 
				 }
			 }
			 
			 secondaryTitle = quotTO.get(26);
			 invalidQuotations.add(secondaryTitle);
			 secondaryDeptCode = quotTO.get(27);
			 invalidQuotations.add(secondaryDeptCode);
			 secondaryPersonName = quotTO.get(28);
			 invalidQuotations.add(secondaryPersonName);
			 secondaryDesignation = quotTO.get(29);
			 invalidQuotations.add(secondaryDesignation);
			 secondaryEmail = quotTO.get(30);
			 invalidQuotations.add(secondaryEmail);
			 secondaryContactNo = quotTO.get(31);
			 invalidQuotations.add(secondaryContactNo);
			 secondaryExtNo = quotTO.get(32);
			 invalidQuotations.add(secondaryExtNo);
			 secondaryMobile = quotTO.get(33);
			 invalidQuotations.add(secondaryMobile);
			 secondaryFax = quotTO.get(34);
			 invalidQuotations.add(secondaryFax);
			 if(!StringUtil.isStringEmpty(secondaryTitle.trim())){
				 secondaryContactDO.setTitle(secondaryTitle.trim());
				
				 if(!StringUtil.isStringEmpty(secondaryDeptCode.trim())){
					 String secondaryDptCode = secondaryDeptCode.trim();
					 if(secondaryDptCode.contains("-")){
						 secondaryDptCode = secondaryDptCode.substring(0, secondaryDptCode.indexOf("-")).trim();
					 }
					 secondaryDptCode = secondaryDptCode.replaceAll("\\u00a0","");
					 DepartmentTO deptTO = rateCommonService.getDepartmentByCode(secondaryDptCode.trim());
					 if(!StringUtil.isNull(deptTO)){
						secondaryContactDO.setDepartment(deptTO.getDepartmentId().toString());
					 }
				 }else{
					 errorCodes.add("secondary contact Department Code is required");
			 	 }
				 
				 
				 if(!StringUtil.isStringEmpty(secondaryPersonName.trim())){
					 secondaryContactDO.setName(secondaryPersonName.trim());
				 }else{
					 errorCodes.add("secondary contact PersonName is required");
			 	 }
				 
				 
				 if(!StringUtil.isStringEmpty(secondaryDesignation.trim())){
					 secondaryContactDO.setDesignation(secondaryDesignation.trim());
				 }else{
					 errorCodes.add("secondary contact Designation is required"); 
			 	 }
				 
				  
				 if(!StringUtil.isStringEmpty(secondaryEmail.trim())){
					 secondaryEmail = secondaryEmail.replaceAll("\\u00a0","");
					 secondaryEmail = secondaryEmail.trim();
					 if(validateEmail(secondaryEmail)){
						 secondaryContactDO.setEmail(secondaryEmail);
					 }else{
						 errorCodes.add("secondary contact Email is not valid");
					 }
				 }else{
					 errorCodes.add("secondary contact Email is required");
			 	 }
				 
				  
				 if(!StringUtil.isStringEmpty(secondaryContactNo.trim())){
					 secondaryContactNo = secondaryContactNo.replaceAll("\\u00a0","");
					 secondaryContactNo = secondaryContactNo.trim();
					 if(StringUtils.isNumeric(secondaryContactNo)){
						 secondaryContactDO.setContactNo(secondaryContactNo);
					 }else{
						 errorCodes.add("secondary contact ContactNo is not valid");
					 }
				 }else{
					 errorCodes.add("secondary contact ContactNo is required");
			 	 }
				 
				  
				 if(!StringUtil.isStringEmpty(secondaryExtNo.trim())){
					 secondaryExtNo = secondaryExtNo.replaceAll("\\u00a0","");
					 secondaryExtNo = secondaryExtNo.trim();
					 if(StringUtils.isNumeric(secondaryExtNo)){
						 secondaryContactDO.setExtension(secondaryExtNo);
					 }else{
						 errorCodes.add("secondary contact extention no is not valid");
					 }
				 }
				 
				
				 if(!StringUtil.isStringEmpty(secondaryMobile.trim())){
					 secondaryMobile = secondaryMobile.replaceAll("\\u00a0","");
					 secondaryMobile = secondaryMobile.trim();
					 if(StringUtils.isNumeric(secondaryMobile) && (secondaryMobile.length() == 0)){
						 secondaryContactDO.setMobile(secondaryMobile);
					 }else{
						 errorCodes.add("secondary contact Mobileno is not valid");
					 }
				 }
				 
				  
				 if(!StringUtil.isStringEmpty(secondaryFax.trim())){
					 secondaryFax = secondaryFax.replaceAll("\\u00a0","");
					 secondaryFax = secondaryFax.trim();
					 if(StringUtils.isNumeric(secondaryFax)){
						 secondaryContactDO.setFax(secondaryFax);
					 }else{
						 errorCodes.add("secondary contact Faxno is not valid");
					 }
				 }
				 customerDO.setSecondaryContactDO(secondaryContactDO);
			 }
			 
			 billingCycle = quotTO.get(35);
			 invalidQuotations.add(billingCycle); 
			 if(!StringUtil.isStringEmpty(billingCycle.trim())){
				 String bCycle = billingCycle.trim();
				 if(bCycle.contains("-")){
					 bCycle = bCycle.substring(0, bCycle.indexOf("-")).trim();
				 }
				 bCycle = bCycle.replaceAll("\\u00a0","");
				 if(bCycle.equals("M") || bCycle.equals("F")){
					 customerDO.setBillingCycle(bCycle);
				 }else{
					 errorCodes.add("billing cycle is not valid");
				 }
			 }
			 
			 legacyCustCode = quotTO.get(36);
			 invalidQuotations.add(legacyCustCode); 
			 if(!StringUtil.isStringEmpty(legacyCustCode.trim())){
				 legacyCustCode = legacyCustCode.replaceAll("\\u00a0","");
				 customerDO.setLegacyCustomerCode(legacyCustCode.trim());
			 }else{
				 errorCodes.add("legacy Customer Code is required");
		 	 }
			 
			 panNo = quotTO.get(37);
			 invalidQuotations.add(panNo); 
			 if(!StringUtil.isStringEmpty(panNo.trim())){
				 panNo = panNo.replaceAll("\\u00a0","");
				 panNo = panNo.trim();
				 if(StringUtils.isAlphanumeric(panNo)){
					 customerDO.setPanNo(panNo.trim());
				 }else{
					 errorCodes.add("Pan no is not valid");
				 }
			 }
			 
			 tanNo = quotTO.get(38);
			 invalidQuotations.add(tanNo); 
			 if(!StringUtil.isStringEmpty(tanNo.trim())){
				 tanNo = tanNo.replaceAll("\\u00a0","");
				 tanNo = tanNo.trim();
				 if(StringUtils.isAlphanumeric(tanNo)){
					 customerDO.setTanNo(tanNo);
				 }else{
					 errorCodes.add("Tan no is not valid");
				 }
			 }
			 
			 quotationType = quotTO.get(42);
			 
			 if(!StringUtil.isStringEmpty(quotationType.trim())){
				 quotationType = quotationType.trim();
				 if(quotationType.contains("-")){
					 quotationType = quotationType.substring(0, quotationType.indexOf("-")).trim();
				 }
				 quotationType = quotationType.replaceAll("\\u00a0","");
				 if(quotationType.trim().equals("N") || quotationType.trim().equals("E")){
					 rateQuotationDO.setRateQuotationType(quotationType);
				 }else{
					 errorCodes.add("quotation type is not valid");
				 }
			 }else{
				 errorCodes.add("quotation type is required"); 
		 	 }
			 
			 
			 indCategory = quotTO.get(40);
			 
			 if(!StringUtil.isStringEmpty(indCategory.trim())){
				 String indCategoryCode = indCategory.trim();
				 if(indCategoryCode.contains("-")){
					 indCategoryCode = indCategoryCode.substring(0, indCategoryCode.indexOf("-")).trim();
				 }
				 indCategoryCode = indCategoryCode.replaceAll("\\u00a0","");
				 indCatTO = rateCommonService.getIndustryCategoryByCode(indCategoryCode.trim());
				 if(!StringUtil.isNull(indCatTO)){
					 indCatDO = new RateIndustryCategoryDO();
					 indCatDO = (RateIndustryCategoryDO)CGObjectConverter.createDomainFromTo(indCatTO, indCatDO);
					 customerDO.setIndustryCategoryDO(indCatDO);
				 }else{
					 errorCodes.add("industry category is not valid");
				 }
			 }else{
				 if(quotationType.trim().equals("E")){
					 indCatTO = rateCommonService.getIndustryCategoryByCode("GNRL");
					 if(!StringUtil.isNull(indCatTO)){
						 indCatDO = new RateIndustryCategoryDO();
						 indCatDO = (RateIndustryCategoryDO)CGObjectConverter.createDomainFromTo(indCatTO, indCatDO);
						 customerDO.setIndustryCategoryDO(indCatDO);
					 }
				 }else{
					 errorCodes.add("industry category is required");
				 }
			 }
			 
			 businessType = quotTO.get(39);
			 invalidQuotations.add(businessType);
			 if(indCategory.contains("BFSI")){
				 if(!StringUtil.isStringEmpty(businessType.trim())){
					 if(businessType.contains("-")){
						 businessType = businessType.substring(0, businessType.indexOf("-")).trim();
					 }
					 businessType = businessType.replaceAll("\\u00a0","");
					 businessType = businessType.trim();
					 if(businessType.equals("CC") || businessType.equals("LC") || businessType.equals("NA")){
						 customerDO.setBusinessType(businessType);
					 }else{
						 errorCodes.add("Business type is not valid"); 
					 }
				 }else{
					 errorCodes.add("Business type is required");
				 }
			 }
			 
			 invalidQuotations.add(indCategory); 
			 custCategory = quotTO.get(41);
			 invalidQuotations.add(custCategory); 
			 if(!StringUtil.isStringEmpty(custCategory.trim())){
				 String custCategoryCode = custCategory.trim();
				 if(custCategoryCode.contains("-")){
					 custCategoryCode = custCategoryCode.substring(0, custCategoryCode.indexOf("-")).trim();
				 }
				 custCategoryCode = custCategoryCode.replaceAll("\\u00a0","");
				 custCatTO = rateCommonService.getRateCustCategoryByCode(custCategoryCode.trim());
				 if(!StringUtil.isNull(custCatTO)){
					 RateCustomerCategoryDO custCatDO = new RateCustomerCategoryDO();
					 custCatDO = (RateCustomerCategoryDO)CGObjectConverter.createDomainFromTo(custCatTO, custCatDO);
					 customerDO.setCustomerCategoryDO(custCatDO);
				 }else{
					 errorCodes.add("customer category is not valid");
				 }
			 }else{
				 if(!StringUtil.isNull(indCatDO)){
					 RateCustomerCategoryDO custCatDO = new RateCustomerCategoryDO();
					 custCatDO.setRateCustomerCategoryId(indCatDO.getRateCustomerCategoryId());
					 customerDO.setCustomerCategoryDO(custCatDO);
				 }else{
					 errorCodes.add(CommonConstants.EMPTY_STRING);
				 }
		 	 }
			 
			 invalidQuotations.add(quotationType); 
			 
			 ownerType = quotTO.get(43);
			 invalidQuotations.add(StringUtils.isNotEmpty(ownerType)? ownerType:CommonConstants.EMPTY_STRING); 
			 if(StringUtil.isStringEmpty(ownerType.trim())){
				 errorCodes.add("owner type is required"); 
			 }else{
				 ownerType = ownerType.trim();
				 if(ownerType.contains("-")){
					 ownerType = ownerType.substring(0, ownerType.indexOf("-")).trim();
				 }
				 ownerType = ownerType.replaceAll("\\u00a0","");
				 if(!(ownerType.trim().equals("C")) && !(ownerType.trim().equals("E"))){
					 errorCodes.add("owner type is not valid"); 
				 }
			 }
			 
			 salesUserName = quotTO.get(44);
			 invalidQuotations.add(salesUserName); 
			 if(!StringUtil.isStringEmpty(salesUserName.trim())){
				 salesUserName = salesUserName.replaceAll("\\u00a0","");
				 userId = rateQuotationDAO.getUserIdByUserName(salesUserName.trim());
				 if(!StringUtil.isEmptyInteger(userId)){
					 rateQuotationDO.setQuotationCreatedBy(userId);
				 }else{
					 errorCodes.add("sales user name is not valid"); 
				 }
			 }else{
				 errorCodes.add("sales user name is required");		 		  
		 	 }
			 
			 rateQuotationDO.setQuotationCreatedDate(new Date());
			 salesUserOfcCode = quotTO.get(45);
			 invalidQuotations.add(StringUtils.isNotEmpty(salesUserOfcCode)? salesUserOfcCode:CommonConstants.EMPTY_STRING); 
			 salesUserOfcCode = salesUserOfcCode.replaceAll("\\u00a0","");
			 if(StringUtil.isStringEmpty(salesUserOfcCode.trim())){
				 errorCodes.add("sales user office code is required"); 
			 }else{
				 ofcId = rateQuotationDAO.getOfcIdByOfcCode(salesUserOfcCode.trim());
				 	if(StringUtil.isEmptyInteger(ofcId)){
				 		errorCodes.add("Sales user office code is not valid");
				 	}
			 }
			 
			 
			 customerDO.setStatus("I");
			 customerDO.setPrimaryContactDO(primaryContactDO);
			 customerDO.setAddressDO(addressDO);
			 rateQuotationDO.setQuotedCustomer(customerDO);
			 rateQuotationDO.setQuotationUsedFor(RateQuotationConstants.CHAR_Q);
			 rateQuotationDO.setStatus(RateQuotationConstants.NEW);
			 rateQuotationDO.setQuotationCreatedFrom("L");
			 
			 if(CGCollectionUtils.isEmpty(errorCodes)){
				 List<String> seqNOs = generateSequenceNo(
							Integer.parseInt(RateQuotationConstants.ONE),
							RateQuotationConstants.QUOTATION_NO);
				 
					String quotationNo = RateQuotationConstants.CHAR_Q
							+ salesUserOfcCode + seqNOs.get(0);
				 rateQuotationDO.setRateQuotationNo(quotationNo);
				 invalidQuotations.add(quotationNo);
				 quotationDOs.add(rateQuotationDO);
				 validQuotationList.add(invalidQuotations);
			 }else{
				 invalidQuotations.add(getErrorMessages(errorCodes));
				 invalidQuotationList.add(invalidQuotations);
			 }
			 
		}
		
		//if(!CGCollectionUtils.isEmpty(quotationDOs)){
			list.add(quotationDOs);
			list.add(invalidQuotationList);
			list.add(validQuotationList);
		//}
		return list;
	}

	private boolean validateEmail(String email){
		/*Pattern pattern;
		Matcher matcher;
	 
		String emailPattern = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		pattern = Pattern.compile(emailPattern);
		matcher = pattern.matcher(email);
		return matcher.matches();*/
		return true;
	}
	private String getErrorMessages(List<String> errorCodes) {
		StringBuilder errorMsgs = new StringBuilder();
		//ResourceBundle errorMessages = null;
		//errorMessages = ResourceBundle
		//		.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
		for (String errorCode : errorCodes) {
			//String errorMsg = errorMessages.getString(errorCode);
			//errorMsgs.append(errorMsg);
			errorMsgs.append(errorCode);
			errorMsgs.append(CommonConstants.COMMA);
		}
		return errorMsgs.toString();
	}
	
	private Boolean validateQuotationFileUploadHeader(
			List<String> excelHeaderList) {
		LOGGER.debug("RateQuotationServiceImpl::validateQuotationFileUploadHeader::START------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		boolean result = true;
		List<String> headerLst = getQuotationHeaderList();
		int headerListSize = headerLst.size();
		String excelHeaderElement = null;
		if (excelHeaderList != null && !excelHeaderList.isEmpty()
				&& (headerLst.size() == excelHeaderList.size())) {
			for (int i = 0; i < headerListSize; i++) {
				excelHeaderElement = excelHeaderList.get(i);
				excelHeaderElement = excelHeaderElement.replaceAll("\\u00a0","").trim();
				if (!headerLst.get(i).equalsIgnoreCase(excelHeaderElement)) {
					result = false;
					break;
				}
			}
		} else
			result = false;
		LOGGER.debug("RateQuotationServiceImpl::validateQuotationFileUploadHeader::End------------>:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return result;
	}

	public static List<String> getQuotationHeaderList() {
		List<String> headerList = new ArrayList<String>();
		headerList.add(RateQuotationExcelConstants.CUSTOMER_NO);
		headerList.add(RateQuotationExcelConstants.SALES_OFFICE_CODE);
		headerList.add(RateQuotationExcelConstants.SALES_EMPLOYEE_CODE);
		headerList.add(RateQuotationExcelConstants.INDUSTRY_TYPE_CODE);
		headerList.add(RateQuotationExcelConstants.CUSTOMER_GROUP);				
		headerList.add(RateQuotationExcelConstants.CUSTOMER_TYPE);
		headerList.add(RateQuotationExcelConstants.CUST_NAME);
		headerList.add(RateQuotationExcelConstants.ADDRESS_1);
		headerList.add(RateQuotationExcelConstants.ADDRESS_2);
		headerList.add(RateQuotationExcelConstants.ADDRESS_3);
		headerList.add(RateQuotationExcelConstants.CITY_CODE);
		headerList.add(RateQuotationExcelConstants.PINCODE);
		headerList.add(RateQuotationExcelConstants.CUST_CONTACT_NO);
		headerList.add(RateQuotationExcelConstants.CUST_EXTENTION);
		headerList.add(RateQuotationExcelConstants.CUST_MOBILE);
		headerList.add(RateQuotationExcelConstants.CUST_FAX);
		headerList.add(RateQuotationExcelConstants.CUST_EMAIL);
		headerList.add(RateQuotationExcelConstants.PRI_TITLE);
		headerList.add(RateQuotationExcelConstants.PRI_DEPT_CODE);
		headerList.add(RateQuotationExcelConstants.PRI_PERSON_NAME);
		headerList.add(RateQuotationExcelConstants.PRI_DESIGNATION);
		headerList.add(RateQuotationExcelConstants.PRI_EMAIL);
		headerList.add(RateQuotationExcelConstants.PRI_CONTACT_NO);
		headerList.add(RateQuotationExcelConstants.PRI_EXTENTION);
		headerList.add(RateQuotationExcelConstants.PRI_MOBILE);
		headerList.add(RateQuotationExcelConstants.PRI_FAX);		
		headerList.add(RateQuotationExcelConstants.SEC_TITLE);
		headerList.add(RateQuotationExcelConstants.SEC_DEPT_CODE);
		headerList.add(RateQuotationExcelConstants.SEC_PERSON_NAME);
		headerList.add(RateQuotationExcelConstants.SEC_DESIGNATION);
		headerList.add(RateQuotationExcelConstants.SEC_EMAIL);
		headerList.add(RateQuotationExcelConstants.SEC_CONTACT_NO);
		headerList.add(RateQuotationExcelConstants.SEC_EXTENTION);
		headerList.add(RateQuotationExcelConstants.SEC_MOBILE);
		headerList.add(RateQuotationExcelConstants.SEC_FAX);
		headerList.add(RateQuotationExcelConstants.BILLING_CYCLE);
		headerList.add(RateQuotationExcelConstants.LEGACY_CODE);
		headerList.add(RateQuotationExcelConstants.PAN_NO);
		headerList.add(RateQuotationExcelConstants.TAN_NO);
		headerList.add(RateQuotationExcelConstants.BUSINESS_TYPE);
		headerList.add(RateQuotationExcelConstants.INDUSTRY_CATEGORY);
		headerList.add(RateQuotationExcelConstants.CUSTOMER_CATEGORY);
		headerList.add(RateQuotationExcelConstants.QUOTATION_TYPE);
		headerList.add(RateQuotationExcelConstants.OWNER_TYPE);
		headerList.add(RateQuotationExcelConstants.SALES_USER_NAME);
		headerList.add(RateQuotationExcelConstants. SALES_USER_OFFICE_CODE);
		
		return headerList;

	}

	private void resetJobStatus(String message, String jobNumber, ByteArrayOutputStream errorByteStream, ByteArrayOutputStream sucsessByteStream, int percentedCompleted, String status) {
		
		JobServicesTO jobTO = new JobServicesTO();
		jobTO.setProcessCode("QUOTATION");
		jobTO.setJobNumber(jobNumber);
		jobTO.setJobStatus(status);
		jobTO.setPercentageCompleted(percentedCompleted);
		jobTO.setRemarks(message);
		jobTO.setFailureFile(errorByteStream == null ? null :errorByteStream.toByteArray());
		jobTO.setSuccessFile(sucsessByteStream == null ? null :sucsessByteStream.toByteArray());
		jobTO.setFileNameFailure(errorByteStream == null ? null :"Quotation_upload_Failure.xls");
		jobTO.setFileNameSuccess(sucsessByteStream == null ? null :"Quotation_upload_Success.xls");
		jobTO.setUpdateDate(new Date());
		try {
			jobTO = jobService.saveOrUpdateJobService(jobTO);
			jobNumber = jobTO.getJobNumber();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("Error occured in RateQuotationDAOImpl :: resetJobStatus()..:"
					,e);
		}
	}
	
	private void setFlags(RateQuotationTO quotationTO, RateQuotationDO rateQuotationDO){
		if(!CGCollectionUtils.isEmpty(rateQuotationDO.getRateQuotationProductCategoryHeaderDO())){
			Set<RateQuotationProductCategoryHeaderDO> rqpchDOSet = rateQuotationDO.getRateQuotationProductCategoryHeaderDO();
			for(RateQuotationProductCategoryHeaderDO rqpchDO : rqpchDOSet){
				if(rqpchDO.getRateProductCategory().getRateProductCategoryCode().equals("CO")){
					quotationTO.setProposedRatesCO("Y");
					Set<RateQuotationWeightSlabDO> rqwsDOSet = rqpchDO.getRateQuotationWeightSlabDO();
					for(RateQuotationWeightSlabDO rqwsDO : rqwsDOSet){
						if(!StringUtil.isStringEmpty(rqwsDO.getRateConfiguredType())){
							if(rqwsDO.getRateConfiguredType().equals("D")){
								quotationTO.setProposedRatesD("Y");
							}else if(rqwsDO.getRateConfiguredType().equals("P")){
								quotationTO.setProposedRatesP("Y");
							}else if(rqwsDO.getRateConfiguredType().equals("B")){
								quotationTO.setProposedRatesB("Y");
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isEcommerceQuotationApprover(Integer userId, String screenCode)
			throws CGBusinessException, CGSystemException {
		return rateQuotationDAO.isEcommerceQuotationApprover(userId, screenCode);
		
	}
}


	