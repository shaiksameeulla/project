package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;

/**
 * @author cbhure
 *
 */
public class SAPConsignmentPostingScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPLiabilityEntriesScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CONSG POSTING :: SAPConsignmentPostingScheduler :: executeInternal :: Start");
		try {
			miscSAPService.updateCODLCStagingConsignmentStaus();
		} catch (Exception e) { 
			logger.error("CONSG POSTING :: Exception IN :: SAPConsignmentPostingScheduler :: ",e);
		}
		logger.debug("SAPConsignmentPostingScheduler :: executeInternal :: End");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}
}

