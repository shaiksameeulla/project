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
public class RTOStagingCODLCScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CODLC RTO :: RTOStagingCODLCScheduler :: executeInternal :: start=======>");
		SAPLiabilityEntriesTO sapCODLCTO = new SAPLiabilityEntriesTO();
		sapCODLCTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS_C);
		sapCODLCTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		try {
			miscSAPService.findConsgForRTOFromStaging(sapCODLCTO);
		} catch (Exception e) {
			logger.error("CODLC RTO :: Exception IN RTOStagingCODLCScheduler :: ",e);
		}
		
		logger.debug("CODLC RTO :: RTOStagingCODLCScheduler :: executeInternal :: after webservice call=======>");
		logger.debug("CODLC RTO :: RTOStagingCODLCScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	
	
	
	
}
