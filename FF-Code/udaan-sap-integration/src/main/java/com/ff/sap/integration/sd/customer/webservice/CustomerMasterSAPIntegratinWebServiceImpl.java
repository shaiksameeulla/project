package com.ff.sap.integration.sd.customer.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.sd.customer.bs.CustomerMasterSAPIntegrationService;
import com.ff.sap.integration.to.SAPCustomerTO;
import com.ff.sap.integration.to.SAPErrorTO;

/**
 * @author cbhure
 * 
 */
@WebService(endpointInterface = "com.ff.sap.integration.sd.customer.webservice.CustomerMasterSAPIntegrationWebService")
public class CustomerMasterSAPIntegratinWebServiceImpl implements
		CustomerMasterSAPIntegrationWebService {

	/** The LOGGER. */
	private final static Logger LOGGER = Logger
			.getLogger(CustomerMasterSAPIntegratinWebServiceImpl.class);

	/** The integrationService. */
	private CustomerMasterSAPIntegrationService integrationService;
	private Properties sapIntegrationMessageProperties;

	/**
	 * @param integrationService
	 *            the integrationService to set
	 */
	public void setIntegrationService(
			CustomerMasterSAPIntegrationService integrationService) {
		this.integrationService = integrationService;
	}

	public void setSapIntegrationMessageProperties(
			Properties sapIntegrationMessageProperties) {
		this.sapIntegrationMessageProperties = sapIntegrationMessageProperties;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomerDetails(@WebParam(name = "customer") List<SAPCustomerTO> customer) {
		LOGGER.debug("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: START");
		List<SAPCustomerDO> sapCustomerDOs = null;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		
		try {
			// 1) Convert the list
			if (!CGCollectionUtils.isEmpty(customer)) {
				sapCustomerDOs = (List<SAPCustomerDO>) CGObjectConverter.createDomainListFromToList(customer, SAPCustomerDO.class);
			}

			// 2) Save the list in staging table
			if (!CGCollectionUtils.isEmpty(sapCustomerDOs)) {
				integrationService.saveSapCustomerDOs(sapCustomerDOs);
			}
			
			// 3) Process all the pending customers one by one
			getPendingCustomersAndSaveCustDetails(sapErroTOlist, SAPIntegrationConstants.SAP_STATUS);
			
			// 4) Send an email notification in case of any errors
			if(!CGCollectionUtils.isEmpty(sapErroTOlist)) {
				integrationService.sendSapIntgErrorMail(
								sapErroTOlist,
								SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,
								"Error while saving customer data from SAP to CSD");
			  }
		} catch (Exception e) {
			LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: ", e);
		}
		LOGGER.debug("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: END");
	}
	
	@Override
	public void getPendingCustomersAndSaveCustDetails(List<SAPErrorTO> sapErroTOlist, String sapInboundStatus){
		LOGGER.debug("CustomerMasterSAPIntegratinWebServiceImpl :: getPendingCustomersAndSaveCustDetails() :: START");
		List<SAPCustomerDO> pendingSapCustDOs = null;
		
		try{
			// 1) Get the list of all the pending customers
			pendingSapCustDOs = integrationService.getPendingSapCustomerList(sapInboundStatus);
			
			// 2) Save each customer one by one
			if (!CGCollectionUtils.isEmpty(pendingSapCustDOs)) {
				for (SAPCustomerDO sapCustDO : pendingSapCustDOs) {
					try {
							integrationService.saveCustomerDetails(sapCustDO);
							LOGGER.warn("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: Customer saved successfully [" +
											sapCustDO.getCustomerNo() + ", " + sapCustDO.getCustomerName() + ", " + sapCustDO.getContarctNo() + "]");
						} catch (CGBusinessException e) {
							LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: Customer not saved in the database [" +
											sapCustDO.getCustomerNo() + ", " + sapCustDO.getCustomerName() + ", " + sapCustDO.getContarctNo() + "]");
							LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: ", e);
							saveSapCustDoOnException(sapCustDO,sapErroTOlist,e);
						} catch (Exception e) {
							LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: Customer not saved in the database [" +
											sapCustDO.getCustomerNo() + ", " + sapCustDO.getCustomerName() + ", " + sapCustDO.getContarctNo() + "]");
							LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveCustomerDetails() :: ERROR ", e);
							saveSapCustDoOnException(sapCustDO,sapErroTOlist,e);
						}
					} // End of FOR-LOOP
				}// End of IF-CONDITION
		}catch(Exception e){
			LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: getPendingCustomersAndSaveCustDetails() :: ERROR::", e);
		}
		LOGGER.debug("CustomerMasterSAPIntegratinWebServiceImpl :: getPendingCustomersAndSaveCustDetails() :: END");
	}
	
	private void saveSapCustDoOnException(SAPCustomerDO sapCustDO, List<SAPErrorTO> sapErroTOlist, Exception e){
		sapCustDO = prepareSapCustDoOnException(sapCustDO,e);
		try{
			integrationService.saveSapCustomerDO(sapCustDO);
		}catch(Exception ex){
			LOGGER.error("CustomerMasterSAPIntegratinWebServiceImpl :: saveSapCustDoOnException() :: ", ex);
		}
		prepareSapErrorToOnException(sapCustDO, sapErroTOlist);
	}
	
	private SAPCustomerDO prepareSapCustDoOnException(SAPCustomerDO sapCustDO, Exception e){
		sapCustDO.setIsError(CommonConstants.YES);
		sapCustDO.setSapInbound(SAPIntegrationConstants.SAP_STATUS_E);
		sapCustDO.setUpdateDate(DateUtil.getCurrentDate());
		String exceptionMessage = "";
		if(e instanceof CGBusinessException){
			exceptionMessage = sapIntegrationMessageProperties.getProperty(e.getMessage());
		}else{
			exceptionMessage = ExceptionUtil.getMessageFromException(e);
		}
		sapCustDO.setException(exceptionMessage.substring(0,(exceptionMessage.length() >= 499) ? 499 : exceptionMessage.length()));
		return sapCustDO;
	}
	
	private void prepareSapErrorToOnException(SAPCustomerDO sapCustDO, List<SAPErrorTO> sapErroTOlist){
		SAPErrorTO sapErrorTO = new SAPErrorTO();
		sapErrorTO.setErrorMessage(sapCustDO.getException());
		sapErrorTO.setTransactionNo(sapCustDO.getCustomerNo());
		sapErroTOlist.add(sapErrorTO);
	}
	
}
