package com.ff.sap.integration.schedular;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.ff.sap.integration.to.SAPErrorTO;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.sd.customer.webservice.CustomerMasterSAPIntegrationWebService;

public class CustomerMasterErrorProcessingScheduler extends QuartzJobBean {

	Logger LOGGER = Logger.getLogger(CustomerMasterErrorProcessingScheduler.class);
	private CustomerMasterSAPIntegrationWebService customerIntegrationWebservice;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("CustomerMasterErrorProcessingScheduler :: executeInternal :: START");
		List<SAPErrorTO> sapErrorTOList = new ArrayList<>();
		customerIntegrationWebservice.getPendingCustomersAndSaveCustDetails(sapErrorTOList, SAPIntegrationConstants.SAP_STATUS_E);
		LOGGER.debug("CustomerMasterErrorProcessingScheduler :: executeInternal :: END");
	}

	/**
	 * @param customerIntegrationWebservice
	 */
	public void setCustomerIntegrationWebservice(
			CustomerMasterSAPIntegrationWebService customerIntegrationWebservice) {
		this.customerIntegrationWebservice = customerIntegrationWebservice;
	}
}
