package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;

/**
 * @author CBHURE
 *
 */
public class ConsigneeStagingCODLCScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CODLC CONSIGNEE :: ConsigneeStagingCODLCScheduler :: executeInternal :: start=======>");
		try {
			miscSAPService.findConsgForConsigneeFromCollection();
			
		} catch (Exception e) {
			logger.error("CODLC CONSIGNEE :: ConsigneeStagingCODLCScheduler :: executeInternal :: error",e);
		}
		
		logger.debug("CODLC CONSIGNEE :: RTODRSStagingCODLCScheduler :: executeInternal :: after webservice call=======>");
		logger.debug("CODLC CONSIGNEE :: RTODRSStagingCODLCScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	
	
	
	
}
