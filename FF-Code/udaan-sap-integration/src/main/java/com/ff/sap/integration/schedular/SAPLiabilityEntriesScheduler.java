package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;

/**
 * @author cbhure
 *
 */
public class SAPLiabilityEntriesScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPLiabilityEntriesScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("CODLC BOOKING :: SAPLiabilityEntriesScheduler :: executeInternal() :: Start");
		
		SAPLiabilityEntriesTO sapLiEntriesTO = new SAPLiabilityEntriesTO();
		sapLiEntriesTO.setSapStatus(SAPIntegrationConstants.SAP_STATUS); // N
		sapLiEntriesTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK); // 10000
		//Set Max Limit constant
		try {
			miscSAPService.findLiabilityEntriesDtlsForSAPIntegration(sapLiEntriesTO);
		} catch (Exception e) { 
			logger.error("CODLC BOOKING :: SAPLiabilityEntriesScheduler :: executeInternal() :: error::",e);
		}
		logger.debug("CODLC BOOKING :: SAPLiabilityEntriesScheduler :: executeInternal() :: End");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}
}

