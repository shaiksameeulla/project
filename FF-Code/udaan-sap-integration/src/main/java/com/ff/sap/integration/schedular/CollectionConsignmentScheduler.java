package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.miscellaneous.service.MiscellaneousSAPIntegrationService;
import com.ff.sap.integration.to.SAPCollectionTO;

/**
 * @author CBHURE
 *
 */
public class CollectionConsignmentScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(CollectionConsignmentScheduler.class);
	public MiscellaneousSAPIntegrationService miscSAPService;
	
	@Override
	@SuppressWarnings("unused")
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("COLLECTIONCR :: CollectionConsignmentScheduler :: executeInternal :: start=======>");
		boolean isSaved = false;
		SAPCollectionTO sapcollnTO = new SAPCollectionTO();
		sapcollnTO.setSapStatus(SAPIntegrationConstants.DT_SAP_OUTBOUND);
		sapcollnTO.setMaxCheck(SAPIntegrationConstants.MAX_CHECK);
		try {
			isSaved = miscSAPService.findConsignmentCollection(sapcollnTO);
		} catch (Exception e) {
			logger.error("Error IN :: CollectionConsignmentScheduler :: CollectionConsignmentScheduler :: ",e);
		}
		
		logger.debug("COLLECTIONCR :: CollectionConsignmentScheduler :: executeInternal :: after webservice call=======>");
		logger.debug("COLLECTIONCR :: CollectionConsignmentScheduler :: executeInternal :: end=======>");
	}

	/**
	 * @param miscSAPService the miscSAPService to set
	 */
	public void setMiscSAPService(MiscellaneousSAPIntegrationService miscSAPService) {
		this.miscSAPService = miscSAPService;
	}

	
	
	
	
}
