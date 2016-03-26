package com.ff.sap.integration.sd.customer.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.AddressDO;
import com.ff.domain.ratemanagement.masters.AccountGroupSapDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.domain.ratemanagement.masters.ContactDO;
import com.ff.domain.ratemanagement.masters.CustomerGroupDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPCustomerTO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.ratemanagement.dao.RateUniversalDAO;

public class CustomerMasterSAPTransactionServiceImpl implements
		CustomerMasterSAPTransactionService {
	Logger logger = Logger.getLogger(CustomerMasterSAPIntegrationServiceImpl.class);
	private OrganizationCommonDAO organizationCommonDAO;
	private GeographyServiceDAO geographyServiceDAO;
	private RateUniversalDAO rateUniversalDAO;
	private SAPIntegrationDAO integrationDAO;
	private EmailSenderUtil emailSenderUtil;
	private PickupManagementCommonService pickUpManagementCommnService;

	/**
	 * @param organizationCommonDAO
	 *            the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(
			OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	/**
	 * @param geographyServiceDAO
	 *            the geographyServiceDAO to set
	 */
	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}

	/**
	 * @param rateUniversalDAO
	 *            the rateUniversalDAO to set
	 */
	public void setRateUniversalDAO(RateUniversalDAO rateUniversalDAO) {
		this.rateUniversalDAO = rateUniversalDAO;
	}

	/**
	 * @param integrationDAO
	 *            the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * @param pickUpManagementCommnService
	 */
	public void setPickUpManagementCommnService(
			PickupManagementCommonService pickUpManagementCommnService) {
		this.pickUpManagementCommnService = pickUpManagementCommnService;
	}

	@Override
	public CSDSAPCustomerDO getAndSetNonContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException,
			CGBusinessException {
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: getUpdateCustDOFromTOForOtherCustType :: START");
		CSDSAPCustomerDO csdSapCustDO = null;
		CSDSAPCustomerDO customerNew = null;
		SAPCustomerTO sapCustTO = new SAPCustomerTO();
		csdSapCustDO = organizationCommonDAO.getCustomerDetailsByCode(sapCustDO.getCustomerNo());
		sapCustTO = (SAPCustomerTO)CGObjectConverter.createToFromDomain(sapCustDO, sapCustTO);
		if(StringUtil.isNull(csdSapCustDO)){
			csdSapCustDO = new CSDSAPCustomerDO();
		}
		customerNew = convertSAPCustDOToCustomerDO(sapCustTO, csdSapCustDO);
		customerNew.setUpdatedDate(DateUtil.getCurrentDate());
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: getUpdateCustDOFromTOForOtherCustType :: END");
		return customerNew;
	}

	@Override
	public void saveNonContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException {
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: saveNonContractualCustomer :: START");
		CSDSAPCustomerDO updateCustDO = getAndSetNonContractualCustomer(sapCustDO);
		saveOrupdateDetailsForCustNew(updateCustDO);
		if (!StringUtil.isNull(updateCustDO.getCustomerType())
				&& updateCustDO.getCustomerType().getCustomerTypeCode().equalsIgnoreCase(SAPIntegrationConstants.ACC_CUSTOMER)) {
			pickUpManagementCommnService.createPickupContractAndLocationOfAccCustomer(updateCustDO);
		}
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: saveNonContractualCustomer :: END");
	}
	
	private CSDSAPCustomerDO convertSAPCustDOToCustomerDO(SAPCustomerTO sapCustTO, CSDSAPCustomerDO custNewDO)
			throws CGSystemException, CGBusinessException {

		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: convertCustomerTOtoDO :: START");
		
		// CUSTOMER ID
		if(StringUtil.isEmptyInteger(custNewDO.getCustomerId())){
			custNewDO.setCustomerId(null);
		}
		
		// CUSTOMER NO
		if (!StringUtil.isStringEmpty(sapCustTO.getCustomerNo())) {
			custNewDO.setCustomerCode(sapCustTO.getCustomerNo());
		}

		// CONTRACT NO
		if (!StringUtil.isStringEmpty(sapCustTO.getContarctNo())) {
			custNewDO.setContractNo(sapCustTO.getContarctNo());
		}
		 
		// ADDRESS
		AddressDO addDO = null;
		if (StringUtil.isNull(custNewDO.getAddress())) {
			addDO = new AddressDO();
			addDO.setAddressId(!StringUtil.isEmptyInteger(sapCustTO.getAddressId()) ? sapCustTO.getAddressId() : null);
		} else {
			addDO = custNewDO.getAddress();
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getAddress1())) {
			addDO.setName(sapCustTO.getAddress1());
			addDO.setAddress1(sapCustTO.getAddress1());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getAddress2())) {
			addDO.setAddress2(sapCustTO.getAddress2());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getAddress3())) {
			addDO.setAddress3(sapCustTO.getAddress3());
		}

		addDO.setStatus(CommonConstants.RECORD_STATUS_ACTIVE);
		// As discussed from Pincode find pincodeID and cityId -> StateID
		List<PincodeDO> pincodeList = null;
		PincodeTO pincodeTO = null;
		PincodeDO pincodeDO = null;
		if (!StringUtil.isNull(sapCustTO.getPincode())) {
			pincodeTO = new PincodeTO();
			pincodeTO.setPincode(sapCustTO.getPincode());
			pincodeList = geographyServiceDAO.validatePincode(pincodeTO);
			if (!StringUtil.isEmptyColletion(pincodeList)) {
				pincodeDO = pincodeList.get(0);
				addDO.setPincodeDO(pincodeDO);

				CityDO cityDO = new CityDO();
				Integer pincodeId = pincodeDO.getPincodeId();
				cityDO = geographyServiceDAO.getCityByPincodeId(pincodeId);
				addDO.setCityDO(cityDO);

				StateDO stateDO = new StateDO();
				Integer stateId = cityDO.getState();
				// stateDO = geographyServiceDAO.getState(stateId);
				stateDO.setStateId(stateId);
				addDO.setStateDO(stateDO);
			}
		}
		custNewDO.setAddress(addDO);

		// PRIMARY CONTACT
		ContactDO contactDO = null;
		if (StringUtil.isNull(custNewDO.getPrimaryContact())) {
			contactDO = new ContactDO();
			contactDO.setContactId(!StringUtil.isEmptyInteger(sapCustTO.getContactId()) ? sapCustTO.getContactId() : null);
		} else {
			contactDO = custNewDO.getPrimaryContact();
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriTitle())) {
			contactDO.setTitle(sapCustTO.getPriTitle());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriPersonName())) {
			contactDO.setName(sapCustTO.getPriPersonName());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriEmail())) {
			contactDO.setEmail(sapCustTO.getPriEmail());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriContactNo())) {
			contactDO.setContactNo(sapCustTO.getPriContactNo());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriExt())) {
			contactDO.setExtension(sapCustTO.getPriExt());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriMobile())) {
			contactDO.setMobile(sapCustTO.getPriMobile());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getPriFax())) {
			contactDO.setFax(sapCustTO.getPriFax());
		}

		custNewDO.setPrimaryContact(contactDO);

		// SECONDARY CONTACT
		ContactDO secContactDO = null;
		if (StringUtil.isNull(custNewDO.getSecondaryContact())) {
			secContactDO = new ContactDO();
			secContactDO.setContactId(!StringUtil.isEmptyInteger(sapCustTO.getContactId()) ? sapCustTO.getContactId() : null);
		} else {
			secContactDO = custNewDO.getSecondaryContact();
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecTitle())) {
			secContactDO.setTitle(sapCustTO.getSecTitle());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecPersonName())) {
			secContactDO.setName(sapCustTO.getSecPersonName());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecEmail())) {
			secContactDO.setEmail(sapCustTO.getSecEmail());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecContactNo())) {
			secContactDO.setContactNo(sapCustTO.getSecContactNo());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecExt())) {
			secContactDO.setExtension(sapCustTO.getSecExt());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecMobile())) {
			secContactDO.setMobile(sapCustTO.getSecMobile());
		}

		if (!StringUtil.isStringEmpty(sapCustTO.getSecFax())) {
			secContactDO.setFax(sapCustTO.getSecFax());
		}

		custNewDO.setSecondaryContact(secContactDO);
		
		// SALES OFFICE		
		OfficeDO officeCodeDO = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getSalesOfcCode())) {
			officeCodeDO = new OfficeDO();
			String offCode = sapCustTO.getSalesOfcCode();
			Integer officeId = null;
			officeCodeDO = organizationCommonDAO.getOfficeByIdOrCode(officeId,offCode);
			if (!StringUtil.isNull(officeCodeDO)) {
				OfficeDO ofcDO = new OfficeDO();
				ofcDO.setOfficeId(officeCodeDO.getOfficeId());
				custNewDO.setSalesOffice(ofcDO);
			}
		}

		// SALES PERSON
		List<EmployeeDO> empDO = null;
		EmployeeTO empTO = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getSalesPersonCode())) {
			empDO = new ArrayList<EmployeeDO>();
			empTO = new EmployeeTO();
			empTO.setEmpCode(sapCustTO.getSalesPersonCode());
			EmployeeDO employeeDO = new EmployeeDO();
			empDO = organizationCommonDAO.getEmployeeDetails(empTO);
			if (!StringUtil.isEmptyColletion(empDO)) {
				employeeDO = empDO.get(0);
				if (!StringUtil.isNull(employeeDO)) {
					EmployeeDO empNewDO = new EmployeeDO();
					if (!StringUtil.isStringEmpty(employeeDO.getEmpCode())) {
						empNewDO.setEmpCode(employeeDO.getEmpCode());
						custNewDO.setSalesPerson(empNewDO);
					}
				}
			}
		}

		// INDUSTRY TYPE
		RateIndustryTypeDO industrTypeDO = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getIndustryTypeCode())) {
			industrTypeDO = new RateIndustryTypeDO();
			String rateIndustryTypeCode = sapCustTO.getIndustryTypeCode();
			industrTypeDO = rateUniversalDAO.getIndustryTypeByCode(rateIndustryTypeCode);
			RateIndustryTypeDO industrTypeNewDO = new RateIndustryTypeDO();
			if (!StringUtil.isNull(industrTypeDO)) {
				industrTypeNewDO.setRateIndustryTypeId(industrTypeDO.getRateIndustryTypeId());
				custNewDO.setIndustryType(industrTypeNewDO);
			}
		}

		// CUSTOMER TYPE
		CustomerTypeDO custType = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getCustomerGroup())) {
			String customerTypeCode = sapCustTO.getCustomerGroup();
			custType = rateUniversalDAO.getCustomerTypeByCode(customerTypeCode);
			if (!StringUtil.isNull(custType)) {
				custNewDO.setCustomerType(custType);
			}
		}

		// on basis of Customer Group Find Industry Category from Account Group
		// SAP
		AccountGroupSapDO accGroupSAPDO = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getCustomerGroup())) {
			String customerGroup = sapCustTO.getCustomerGroup();
			accGroupSAPDO = rateUniversalDAO
					.getRateCustCategoryByCustGroup(customerGroup);
			if (!StringUtil.isNull(accGroupSAPDO)
					&& !StringUtil.isEmptyInteger(accGroupSAPDO
							.getRateCustomerCategoryId()
							.getRateCustomerCategoryId())) {
				RateCustomerCategoryDO rateCustCategoryDO = new RateCustomerCategoryDO();
				rateCustCategoryDO.setRateCustomerCategoryId(accGroupSAPDO
						.getRateCustomerCategoryId()
						.getRateCustomerCategoryId());
				custNewDO.setCustomerCategory(rateCustCategoryDO);
			}
		}

		// DISTRIBUTION CHANNELS
		if (!StringUtil.isStringEmpty(sapCustTO.getDisChannel())) {
			custNewDO.setDistributionChannels(sapCustTO.getDisChannel());
		}

		// GROUP KEY
		CustomerGroupDO custGrpDO = null;
		if (!StringUtil.isStringEmpty(sapCustTO.getGroupKey())) {
			String customerGroupCode = sapCustTO.getGroupKey();
			custGrpDO = rateUniversalDAO.getCustomerGroupByCode(customerGroupCode);
			CustomerGroupDO custmerGrpDo = new CustomerGroupDO();
			if (!StringUtil.isNull(custGrpDO)) {
				custmerGrpDo.setCustomerGroupId(custGrpDO.getCustomerGroupId());
				custNewDO.setGroupKey(custmerGrpDo);
			}
		}

		// CUSTOMER NAME
		if (!StringUtil.isStringEmpty(sapCustTO.getCustomerName())) {
			custNewDO.setBusinessName(sapCustTO.getCustomerName());
		}

		// BILLING CYCLE
		if (!StringUtil.isStringEmpty(sapCustTO.getBillingCycle())) {
			custNewDO.setBillingCycle(sapCustTO.getBillingCycle());
		}

		// PAYMENT TERMS CODE
		if (!StringUtil.isStringEmpty(sapCustTO.getPaymentTermsCode())) {
			custNewDO.setPaymentTerm(sapCustTO.getPaymentTermsCode());
		}

		// OFFICE MAPPED TO
		if (!StringUtil.isStringEmpty(sapCustTO.getPlantCode())) {
			officeCodeDO = new OfficeDO();
			String offCode = sapCustTO.getPlantCode();
			Integer officeId = null;
			officeCodeDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
			OfficeDO ofcDO = new OfficeDO();
			if (!StringUtil.isNull(officeCodeDO)) {
				ofcDO.setOfficeId(officeCodeDO.getOfficeId());
				custNewDO.setOfficeMappedTO(ofcDO);
			}
		}

		// TAN NO
		if (!StringUtil.isStringEmpty(sapCustTO.getTanNo())) {
			custNewDO.setTanNo(sapCustTO.getTanNo());
		}

		// PAN NO
		if (!StringUtil.isStringEmpty(sapCustTO.getPanNo())) {
			custNewDO.setPanNo(sapCustTO.getPanNo());
		}

		// LEGACY CODE
		if (!StringUtil.isStringEmpty(sapCustTO.getLegacyCustCode())) {
			custNewDO.setLegacyCustCode(sapCustTO.getLegacyCustCode());
		}

		// DT TO BRANCH
		custNewDO.setDtToBranch(CommonConstants.NO);
		
		// CUSTOMER STATUS
		if(!StringUtil.isStringEmpty(sapCustTO.getStatus())){
			custNewDO.setCurrentStatus(sapCustTO.getStatus());
		}else{
			if(StringUtil.isEmptyInteger(custNewDO.getCustomerId())){
				custNewDO.setCurrentStatus(CommonConstants.RECORD_STATUS_INACTIVE);
			}
		}

		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if (!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())) {
			custNewDO.setCreatedBy(userDO.getUserId());
			custNewDO.setUpdatedBy(userDO.getUserId());
			Date today = Calendar.getInstance().getTime();
			custNewDO.setCreatedDate(today);
			custNewDO.setUpdatedDate(today);
		}

		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: convertCustomerTOtoDO :: END");
		return custNewDO;
	}

	@Override
	public void saveOrupdateDetailsForCustNew(CSDSAPCustomerDO updateCustDO) throws CGSystemException {
		logger.debug("CustomerMasterSAPTransactionServiceImpl :: updateDetailsForCustNew :: START");
		try {
			integrationDAO.updateDetailsForCustNew(updateCustDO);
		} catch (Exception e) {
			logger.error("Exception IN :: CustomerMasterSAPTransactionServiceImpl :: updateDetailsForCustNew :: ",e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public boolean saveCustomerDetails(List<CSDSAPCustomerDO> custDO)
			throws CGSystemException {
		logger.debug("CUSTOMER :: CustomerMasterSAPTransactionServiceImpl :: saveCustomerDetails :: START");
		boolean isUpdate = false;
		try {
			isUpdate = integrationDAO.saveCustomerDetails(custDO);
		} catch (Exception e) {
			logger.error(
					"CUSTOMER :: Exception IN :: CustomerMasterSAPTransactionServiceImpl :: saveCustomerDetails :: ",
					e);
			throw new CGSystemException(e);
		}
		logger.debug("CUSTOMER :: CustomerMasterSAPTransactionServiceImpl :: saveCustomerDetails :: END");
		return isUpdate;
	}

	@Override
	public boolean saveDetailsOneByOne(List<CSDSAPCustomerDO> updateCustDO) {
		logger.debug("CUSTOMER :: CustomerMasterSAPTransactionServiceImpl :: saveDetailsOneByOne :: Start");
		List<CSDSAPCustomerDO> errorcustomerDOs = null;
		List<SAPCustomerDO> stagingCustomerDOs = null;
		SAPCustomerDO stagingCustomerDO = null;
		boolean isSaved = false;
		try {
			// new method created
			errorcustomerDOs = integrationDAO
					.saveDetailsOneByOneForCustomers(updateCustDO);
			if (!StringUtil.isEmptyColletion(errorcustomerDOs)) {
				for (CSDSAPCustomerDO errorRecord : errorcustomerDOs) {
					stagingCustomerDO = new SAPCustomerDO();
					stagingCustomerDO = convertErrorRecordsToStagingDOForCustomer(errorRecord);
					stagingCustomerDOs.add(stagingCustomerDO);
				}
				if (!StringUtil.isEmptyColletion(stagingCustomerDOs)) {
					isSaved = integrationDAO
							.saveCustomerErrorRecords(stagingCustomerDOs);

					if (isSaved) {
						// If Saved Successfully into staging table trigger mail
						// to SAP
						sendEmail(stagingCustomerDOs);
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"Exception IN :: VendorMasterSAPTransactionServiceImpl :: saveOrUpdateVendorDetails :: ",
					e);
		}
		logger.debug("CUSTOMER :: CustomerMasterSAPTransactionServiceImpl :: saveDetailsOneByOne :: End");
		return isSaved;
	}

	private SAPCustomerDO convertErrorRecordsToStagingDOForCustomer(
			CSDSAPCustomerDO errorRecord) throws CGSystemException {

		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDOForCustomer :: Start");

		// SAPVendorDO stagingVendorDO = new SAPVendorDO();
		SAPCustomerDO stagingCustomerDO = new SAPCustomerDO();
		if ((!StringUtil.isNull(errorRecord) && (!StringUtil
				.isEmptyInteger(errorRecord.getCustomerId())))) {
			stagingCustomerDO.setId(errorRecord.getCustomerId().longValue());
		}/*
		 * else{
		 * stagingVendorDO.setId(!StringUtil.isEmptyInteger(errorRecord.getVendorId
		 * ())?errorRecord.getVendorId() :null); }
		 */
		logger.debug("Staging Customer ID ----------------->"
				+ stagingCustomerDO.getId());

		if (!StringUtil.isStringEmpty(errorRecord.getCustomerCode())) {
			stagingCustomerDO.setCustomerNo(errorRecord.getCustomerCode());
		}
		logger.debug("Staging Customer Code----------------->"
				+ stagingCustomerDO.getCustomerNo());

		if (!StringUtil.isNull(errorRecord.getSalesOffice())) {
			stagingCustomerDO.setSalesOfcCode(errorRecord.getSalesOffice()
					.toString());
		}
		logger.debug("Customer Sales office ----------------->"
				+ stagingCustomerDO.getSalesOfcCode());

		if (!StringUtil.isNull(errorRecord.getSalesPerson())) {
			stagingCustomerDO.setSalesPersonCode(errorRecord.getSalesPerson()
					.toString());
		}
		logger.debug("Customer sales person ----------------->"
				+ stagingCustomerDO.getSalesPersonCode());

		if (!StringUtil.isNull(errorRecord.getIndustryType())) {
			stagingCustomerDO.setIndustryTypeCode(errorRecord.getIndustryType()
					.toString());
		}
		logger.debug("Industry type ----------------->"
				+ stagingCustomerDO.getIndustryTypeCode());

		if (!StringUtil.isNull(errorRecord.getCustomerCategory())) {
			stagingCustomerDO.setCustomerGroup(errorRecord
					.getCustomerCategory().toString());
		}
		logger.debug("Customer category ----------------->"
				+ stagingCustomerDO.getCustomerGroup());

		if (!StringUtil.isNull(errorRecord.getGroupKey())) {
			stagingCustomerDO.setGroupKey(errorRecord.getGroupKey().toString());
		}
		logger.debug("Group key ----------------->"
				+ stagingCustomerDO.getGroupKey());

		if (!StringUtil.isNull(errorRecord.getPrimaryContact())) {
			stagingCustomerDO.setPriContactNo(errorRecord.getPrimaryContact()
					.toString());
		}
		logger.debug("PrimaryContact ----------------->"
				+ stagingCustomerDO.getPriContactNo());

		if (!StringUtil.isNull(errorRecord.getSecondaryContact())) {
			stagingCustomerDO.setSecContactNo(errorRecord.getSecondaryContact()
					.toString());
		}
		logger.debug("SecondaryContact ----------------->"
				+ stagingCustomerDO.getSecContactNo());

		if (!StringUtil.isStringEmpty(errorRecord.getBillingCycle())) {
			stagingCustomerDO.setBillingCycle(errorRecord.getBillingCycle());
		}
		logger.debug("BillingCycle----------------->"
				+ stagingCustomerDO.getBillingCycle());

		if (!StringUtil.isStringEmpty(errorRecord.getPaymentTerm())) {
			stagingCustomerDO.setPaymentTermsCode(errorRecord.getPaymentTerm());
		}
		logger.debug("PaymentTerm ----------------->"
				+ stagingCustomerDO.getPaymentTermsCode());

		if (!StringUtil.isNull(errorRecord.getTanNo())) {
			stagingCustomerDO.setTanNo(errorRecord.getTanNo());
		}
		logger.debug("TanNo ----------------->" + stagingCustomerDO.getTanNo());

		if (!StringUtil.isNull(errorRecord.getPanNo())) {
			stagingCustomerDO.setPanNo(errorRecord.getPanNo());
		}
		logger.debug("PanNo ----------------->" + stagingCustomerDO.getPanNo());

		if (!StringUtil.isNull(errorRecord.getCurrentStatus())) {
			stagingCustomerDO.setStatus(errorRecord.getCurrentStatus());
		}
		logger.debug("CurrentStatus----------------->"
				+ stagingCustomerDO.getStatus());

		/*
		 * if(!StringUtil.isEmptyInteger(errorRecord.getDepartment())){
		 * stagingEmployeeDO.setDepartment(errorRecord.getDepartment()); }
		 * logger
		 * .debug("SeriesLength ----------------->"+stagingEmployeeDO.getDepartment
		 * ());
		 */

		/*
		 * if(!StringUtil.isNull(errorRecord.getPurchaseDate())){
		 * stagingItemDO.setPurchaseDate(errorRecord.getPurchaseDate()); }
		 * logger
		 * .debug("PurchaseDate ----------------->"+stagingItemDO.getPurchaseDate
		 * ());
		 */

		// Add RHO CODE
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDOForCustomer :: End");
		return stagingCustomerDO;
	}

	private void sendEmail(List<SAPCustomerDO> stagingCostomerDOs)
			throws CGBusinessException, CGSystemException {
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: sendEmail :: Start");
		try {
			List<MailSenderTO> mailerList = new ArrayList<>();
			// Prepare Subject and add it into Mail Body
			StringBuilder plainMailBody = prepareMailBody(stagingCostomerDOs);
			// String
			// subject="your complaint with reference number "+serviceRequestFollowupDO.getServiceRequestDO().getServiceRequestNo()+" has been followup";
			// subject=subject+" for consignment/Booking ref no: "+serviceRequestFollowupDO.getServiceRequestDO().getBookingNo();
			// StringBuilder plainMailBody = getMailBody(subject);
			prepareCallerMailAddress(mailerList, plainMailBody);
			// prepareExecutiveMail(serviceRequestFollowupDO, mailerList);
			for (MailSenderTO senderTO : mailerList) {
				emailSenderUtil.sendEmail(senderTO);
			}
		} catch (Exception e) {
			logger.error(
					"ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",
					e);
		}
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: sendEmail :: End");
	}

	private StringBuilder prepareMailBody(List<SAPCustomerDO> stagingCostomerDOs) {
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: Start");
		StringBuilder plainMailBody = new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody
				.append("<br/><br/>Error came while processing Vendor Records to CSD Database");
		if (!StringUtil.isEmptyColletion(stagingCostomerDOs)) {
			for (SAPCustomerDO stagingCustomerDO : stagingCostomerDOs) {
				if (!StringUtil
						.isStringEmpty(stagingCustomerDO.getCustomerNo())) {
					plainMailBody.append("<br/><br/>Vendor Code : "
							+ stagingCustomerDO.getCustomerNo());
				}
				if (!StringUtil.isStringEmpty(stagingCustomerDO.getException())) {
					plainMailBody.append("<br/><br/>Exception : "
							+ stagingCustomerDO.getException());
				}
			}
		}
		plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: End");
		return plainMailBody;
	}

	private void prepareCallerMailAddress(List<MailSenderTO> mailerList,
			StringBuilder plainMailBody) {
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: Start");
		MailSenderTO callerSenderTO = new MailSenderTO();
		callerSenderTO
				.setTo(new String[] { SAPIntegrationConstants.EMAILD_ID });
		// callerSenderTO.setMailSubject(subject);
		callerSenderTO.setPlainMailBody(plainMailBody.toString());
		mailerList.add(callerSenderTO);
		logger.debug("MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: End");
	}

	@Override
	public List<CSDSAPCustomerDO> getCustDOFromTO(List<SAPCustomerTO> customer)
			throws CGBusinessException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("CUSTOMER :: CustomerMasterSAPIntegrationServiceImpl :: getCustDOFromTO :: Start");
		List<CSDSAPCustomerDO> baseList = null;
		if (customer != null && !customer.isEmpty()) {
			baseList = new ArrayList<CSDSAPCustomerDO>(customer.size());
			CSDSAPCustomerDO custNewDO = null;
			for (SAPCustomerTO customerTO : customer) {
				if (!StringUtil.isNull(customerTO)) {
					// String custGrp = customerTO.getCustomerGroup();
					String baCode = customerTO.getCustomerNo();
					custNewDO = organizationCommonDAO.getCustomerDetailsByCode(baCode);
					if (StringUtil.isNull(custNewDO)) {
						// Added by Himal - Even if SAP system sent wrong data
						// to CSD system then, CSD does not allow that data to
						// proceed and throws Exception
						if (!StringUtil.isStringEmpty(customerTO
								.getContarctNo())) {
							throw new CGBusinessException(SAPIntegrationConstants.ERR_BA_CUST_SHOULD_NOT_HAVE_CONTRACT);
						}
						CSDSAPCustomerDO customerNew = null;
						// boolean isUpdate = false;
						// customerNew = convertCustomerTOtoDO(customerTO,	isUpdate, custNewDO);
						baseList.add(customerNew);
					}
				}
			}
		}
		logger.debug("CUSTOMER :: CustomerMasterSAPIntegrationServiceImpl :: getCustDOFromTO :: End");
		return baseList;
	}

	@Override
	public void saveContractualCustomer(SAPCustomerDO sapCustDO) throws CGSystemException, CGBusinessException {
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: saveContractualCustomer() :: START");
		CSDSAPCustomerDO custNewDO = new CSDSAPCustomerDO(); 
		Integer customerId = null;
		try {
			if (!StringUtil.isStringEmpty(sapCustDO.getCustomerNo()) && !StringUtil.isStringEmpty(sapCustDO.getContarctNo())) {
				String customerNo = sapCustDO.getCustomerNo();
				String contractNo = sapCustDO.getContarctNo();
				customerId = organizationCommonDAO.getCustomerIdByContractNo(contractNo);
				if (!StringUtil.isEmptyInteger(customerId)) {
					custNewDO.setContractNo(contractNo);
					custNewDO.setCustomerCode(customerNo);
					custNewDO.setCustomerId(customerId);
					integrationDAO.updateCustNoAgaistContarct(custNewDO);  // save-1
					Integer rateContractId = integrationDAO.getContractIdByContractNo(contractNo); 
					if (!StringUtil.isEmptyInteger(rateContractId)) {
						integrationDAO.updateRateCustStatusAgaistContarct(contractNo); // save-2
						integrationDAO.updateShipToCode(rateContractId,customerNo); // save-3
					}else{
						throw new CGBusinessException(SAPIntegrationConstants.ERR_CONTRACT_NOT_FOUND);
					}
				}else{
					throw new CGBusinessException(SAPIntegrationConstants.ERR_CUSTOMER_NOT_FOUND);
				}
			}else{
				throw new CGBusinessException(SAPIntegrationConstants.ERR_CONTRACT_CUSTOMER_NUMBER_NULL);
			}
		}
		catch(CGBusinessException e){
			throw e;
		}
		catch(CGSystemException e) {
			throw e;
		}
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: saveContractualCustomer() :: END");
	}
	
	@Override
	public boolean isCustomerContractual(SAPCustomerDO sapCustDO)
			throws CGSystemException, CGBusinessException {
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: isCustomerContractual :: START");
		boolean isCustomerContractual = false;
		String customerNumberBeginsWith = sapCustDO.getCustomerNo().substring(0, 4);
		if(!StringUtil.isStringEmpty(sapCustDO.getContarctNo())){
			if(SAPIntegrationConstants.CREDIT_CUSTOMER_OTHER_THAN_COD.equals(customerNumberBeginsWith) ||
					SAPIntegrationConstants.COD_CUSTOMER.equals(customerNumberBeginsWith) || 
						SAPIntegrationConstants.FRANCHISEE_CUSTOMER.equals(customerNumberBeginsWith)){
				isCustomerContractual = true;
			}
			else{
				throw new CGBusinessException(SAPIntegrationConstants.ERR_NON_CONTRACTUAL_CUSTOMER);
			}
		}else{
			if(SAPIntegrationConstants.CREDIT_CUSTOMER_OTHER_THAN_COD.equals(customerNumberBeginsWith) ||
					SAPIntegrationConstants.COD_CUSTOMER.equals(customerNumberBeginsWith)){
				throw new CGBusinessException(SAPIntegrationConstants.ERR_CONTRACTUAL_CUSTOMER);
			}
		}
		logger.debug("CustomerMasterSAPIntegrationServiceImpl :: isCustomerContractual :: END");
		return isCustomerContractual;
	}
	
}
