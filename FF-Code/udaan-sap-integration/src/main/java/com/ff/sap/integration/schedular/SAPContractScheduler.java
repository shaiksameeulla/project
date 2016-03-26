package com.ff.sap.integration.schedular;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.SAPContractDO;
import com.ff.sap.integration.common.service.CommonSapIntegrationService;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.sd.service.SDSAPIntegrationService;
import com.ff.sap.integration.to.SAPContractTO;
import com.firstflight.sd.csdtosap.contract.DTCSDContract;
import com.firstflight.sd.csdtosap.contract.SICSDContractOut;

/**
 * @author cbhure
 * 
 */
public class SAPContractScheduler extends QuartzJobBean {

	/** The LOGGER. */
	Logger LOGGER = Logger.getLogger(SAPContractScheduler.class);

	/** The client. */
	private SICSDContractOut client;

	/** The sdSAPIntegrationServiceForContract. */
	private SDSAPIntegrationService sdSAPIntegrationServiceForContract;
	
	private CommonSapIntegrationService commonSapIntegrationService;

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(SICSDContractOut client) {
		this.client = client;
	}

	/**
	 * @param sdSAPIntegrationServiceForContract
	 *            the sdSAPIntegrationServiceForContract to set
	 */
	public void setSdSAPIntegrationServiceForContract(
			SDSAPIntegrationService sdSAPIntegrationServiceForContract) {
		this.sdSAPIntegrationServiceForContract = sdSAPIntegrationServiceForContract;
	}

	public void setCommonSapIntegrationService(
			CommonSapIntegrationService commonSapIntegrationService) {
		this.commonSapIntegrationService = commonSapIntegrationService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("CONTRACT :: SAPContractScheduler :: executeInternal :: START =======>");
		DTCSDContract contract = null;
		List<DTCSDContract.Contract> elements = null;
		List<SAPContractDO> sapContractDOs = null;

		SAPContractTO sapContractTO = new SAPContractTO();
		sapContractTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS);
		sapContractTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);

		try {
			contract = new DTCSDContract();
			elements = contract.getContract();

			sapContractDOs = sdSAPIntegrationServiceForContract.findContractDtlsForSAPIntegration(sapContractTO);

			if (!CGCollectionUtils.isEmpty(sapContractDOs)) {

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (SAPContractDO sapContractDO : sapContractDOs) {
					DTCSDContract.Contract cntrct = new DTCSDContract.Contract();
					// Address 1
					if (!StringUtil.isStringEmpty(sapContractDO.getAddress1())) {
						cntrct.setAddress1(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getAddress1()));
					}
					// Address 2
					if (!StringUtil.isStringEmpty(sapContractDO.getAddress2())) {
						cntrct.setAddress2(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getAddress2()));
					}
					// Address 3
					if (!StringUtil.isStringEmpty(sapContractDO.getAddress3())) {
						cntrct.setAddress3(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getAddress3()));
					}

					// Billing Cycle
					if (!StringUtil.isStringEmpty(sapContractDO.getBillingCycle())) {
						cntrct.setBillingCycle(sapContractDO.getBillingCycle());
					}
					// City
					if (!StringUtil.isStringEmpty(sapContractDO.getCity())) {
						cntrct.setCity(sapContractDO.getCity());
					}
					// Contract No
					if (!StringUtil.isStringEmpty(sapContractDO.getContractNo())) {
						cntrct.setContractNo(sapContractDO.getContractNo());
					}
					// Customer Group
					if (!StringUtil.isStringEmpty(sapContractDO.getCustomerGroup())) {
						cntrct.setCustomerGroup(sapContractDO.getCustomerGroup());
					}
					// Customer Name
					if (!StringUtil.isStringEmpty(sapContractDO.getCustomerName())) {
						cntrct.setCustName(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getCustomerName()));
					}
					// Customer No
					if (!StringUtil.isStringEmpty(sapContractDO.getCustomerNo())) {
						cntrct.setCustomerNo(sapContractDO.getCustomerNo());
					}
					// Distributed Channel
					if (!StringUtil.isStringEmpty(sapContractDO.getDisChannel())) {
						cntrct.setDistChannel(sapContractDO.getDisChannel());
					}
					// Group Key
					if (!StringUtil.isStringEmpty(sapContractDO.getGroupKey())) {
						cntrct.setGroupKey(sapContractDO.getGroupKey());
					}
					// Industry Type Code
					if (!StringUtil.isStringEmpty(sapContractDO.getIndustryTypeCode())) {
						cntrct.setIndustryTypeCode(sapContractDO.getIndustryTypeCode());
					}
					// PAN No
					if (!StringUtil.isStringEmpty(sapContractDO.getPanNo())) {
						cntrct.setPANNo(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getPanNo()));
					}
					// Payment Terms Code
					if (!StringUtil.isStringEmpty(sapContractDO.getPaymentTermsCode())) {
						cntrct.setPaymentTerms(sapContractDO.getPaymentTermsCode());
					}
					// Pincode
					if (!StringUtil.isStringEmpty(sapContractDO.getPincode())) {
						cntrct.setPincode(sapContractDO.getPincode());
					}
					// Plant Code
					if (!StringUtil.isStringEmpty(sapContractDO.getPlantCode())) {
						cntrct.setPlantCode(sapContractDO.getPlantCode());
					}
					// Primary Contract
					DTCSDContract.Contract.PrimaryContact primaryContact = new DTCSDContract.Contract.PrimaryContact();
					if (!StringUtil.isStringEmpty(sapContractDO.getPriContactNo())) {
						primaryContact.setContactNo(sapContractDO.getPriContactNo());
					}
					// Primary Email
					if (!StringUtil.isStringEmpty(sapContractDO.getPriEmail())) {
						primaryContact.setEmail(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getPriEmail()));
					}
					// Primary Extension
					if (!StringUtil.isStringEmpty(sapContractDO.getPriExt())) {
						primaryContact.setExtention(sapContractDO.getPriExt());
					}
					// Primary Fax
					if (!StringUtil.isStringEmpty(sapContractDO.getPriFax())) {
						primaryContact.setFax(sapContractDO.getPriFax());
					}
					// Primary Mobile
					if (!StringUtil.isStringEmpty(sapContractDO.getPriMobile())) {
						primaryContact.setMobile(sapContractDO.getPriMobile());
					}
					// Primary Person Name
					if (!StringUtil.isStringEmpty(sapContractDO.getPriPersonName())) {
						primaryContact.setPersonName(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getPriPersonName()));
					}
					// Primary Title
					if (!StringUtil.isStringEmpty(sapContractDO.getPriTitle())) {
						primaryContact.setTitle(sapContractDO.getPriTitle());
					}
					cntrct.setPrimaryContact(primaryContact);

					DTCSDContract.Contract.SecondaryContact secondaryContact = new DTCSDContract.Contract.SecondaryContact();
					if (!StringUtil.isStringEmpty(sapContractDO.getSecContactNo())) {
						secondaryContact.setContactNo(sapContractDO.getSecContactNo());
					}
					// Secondary Email
					if (!StringUtil.isStringEmpty(sapContractDO.getSecEmail())) {
						secondaryContact.setEmail(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getSecEmail()));
					}
					// Secondary Extension
					if (!StringUtil.isStringEmpty(sapContractDO.getSecExt())) {
						secondaryContact.setExtention(sapContractDO.getSecExt());
					}
					// Secondary Fax
					if (!StringUtil.isStringEmpty(sapContractDO.getSecFax())) {
						secondaryContact.setFax(sapContractDO.getSecFax());
					}
					// Secondary Mobile
					if (!StringUtil.isStringEmpty(sapContractDO.getSecMobile())) {
						secondaryContact.setMobile(sapContractDO.getSecMobile());
					}
					// Secondary Person Name
					if (!StringUtil.isStringEmpty(sapContractDO.getSecPersonName())) {
						secondaryContact.setPersonName(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getSecPersonName()));
					}
					// Secondary Title
					if (!StringUtil.isStringEmpty(sapContractDO.getSecTitle())) {
						secondaryContact.setTitle(sapContractDO.getSecTitle());
					}
					cntrct.setSecondaryContact(secondaryContact);

					// Sales office
					if (!StringUtil.isStringEmpty(sapContractDO.getSalesOfcCode())) {
						cntrct.setSalesOfficeCode(sapContractDO.getSalesOfcCode());
					}
					// Sales District
					if (!StringUtil.isStringEmpty(sapContractDO.getSalesDistrict())) {
						cntrct.setSalesDistrict(sapContractDO.getSalesDistrict());
					}
					// Sales Person Code
					if (!StringUtil.isStringEmpty(sapContractDO.getSalesPersonCode())) {
						cntrct.setSalesPersonCode(sapContractDO.getSalesPersonCode());
					}
					// Customer Group
					if (!StringUtil.isStringEmpty(sapContractDO.getCustomerGroup())) {
						cntrct.setCustomerGroup(sapContractDO.getCustomerGroup());
					}
					// State
					if (!StringUtil.isStringEmpty(sapContractDO.getState())) {
						cntrct.setState(sapContractDO.getState());
					}
					// TAN No
					if (!StringUtil.isStringEmpty(sapContractDO.getTanNo())) {
						cntrct.setTANNo(commonSapIntegrationService.removeJunkCharactersFromString(sapContractDO.getTanNo()));
					}
					// Legacy Customer Code
					if (!StringUtil.isStringEmpty(sapContractDO.getLegacyCustCode())) {
						cntrct.setLegacyCustCode(sapContractDO.getLegacyCustCode());
					}
					// Time stamp
					Date today = Calendar.getInstance().getTime();
					String dateStamp = df.format(today);
					cntrct.setTimestamp(dateStamp);

					elements.add(cntrct);
				} // End of FOR LOOP

				if (!StringUtil.isEmptyList(contract.getContract())) {
					String sapStatus = null;
					String exception = null;
					try {
						client.siCSDContractOut(contract);
						sapStatus = "C"; // Complete
					} catch (Exception e) {
						sapStatus = "N"; // New
						exception = e.getMessage();
						LOGGER.error("CONTRACT :: SAPContractScheduler.executeInternal() :: Error is =======> ", e);
					} finally {
						try {
							sdSAPIntegrationServiceForContract
									.updateContractStagingStatusFlag(sapStatus,
											sapContractDOs, exception);
						} catch (CGSystemException e) {
							LOGGER.error(
									"CONTRACT :: Exception in SAPContractScheduler.executeInternal() :: updateContractStagingStatusFlag :: ",
									e);
						}
					}
				} // End of IF

			}// END of MAIN-IF
		} catch (Exception e) {
			LOGGER.error("CONTRACT :: Exception in SAPContractScheduler :: executeInternal :: ", e);
		}
		LOGGER.debug("CONTRACT :: SAPContractScheduler :: executeInternal :: AFTER WEBSERVICE CALL =======>");
		LOGGER.debug("CONTRACT :: SAPContractScheduler :: executeInternal :: END =======>");
	}
}
