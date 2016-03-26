package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;

/**
 * @author CBHURE
 *
 */
public class DeliveredStagingCODLCScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CODLC DELIVERED :: DeliveredStagingCODLCScheduler::executeInternal::start=======>");
		SAPLiabilityEntriesTO sapCODLCTO = new SAPLiabilityEntriesTO();
		sapCODLCTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS_C);
		sapCODLCTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		try {
			miscSAPService.findConsgForDeliveredFromStaging(sapCODLCTO);
		} catch (Exception e) {
			logger.error("CODLC DELIVERED :: DeliveredStagingCODLCScheduler :: Exception :",e);
		}
		
		logger.debug("CODLC DELIVERED :: DeliveredStagingCODLCScheduler::executeInternal::after webservice call=======>");
		logger.debug("CODLC DELIVERED :: DeliveredStagingCODLCScheduler::executeInternal::end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	
	
	
	
}
