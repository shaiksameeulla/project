package com.ff.sap.integration.schedular;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.sap.integration.sd.service.SDSAPIntegrationService;

/**
 * @author prmeher
 *
 */
public class SAPBillingRateCalculationScheduler  extends QuartzJobBean {

	Logger logger = Logger.getLogger(SAPExpenseScheduler.class);
	//private SICSDSalesOrderOut client; 
	public SDSAPIntegrationService sdSAPIntegrationServiceForBCS;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.debug("BillingSummaryCreationScheduler - for Rate Calculation scheduler :: SAPBillingRateCalculationScheduler :: executeInternal :: start");
		try{
			sdSAPIntegrationServiceForBCS.getConsignmentsForRate();
		} catch (CGSystemException | CGBusinessException  e) {
			logger.error("BillingSummaryCreationScheduler::executeInternal::HttpException::" ,e);
		} catch(Exception e) {
			logger.error("BillingSummaryCreationScheduler::executeInternal::Exception::" , e);
		}
		logger.debug("BillingSummaryCreationScheduler - for Rate Calculation scheduler :: SAPBillingRateCalculationScheduler :: executeInternal::end");
	}
	
	/**
	 * @param sdSAPIntegrationServiceForBCS
	 *            the sdSAPIntegrationServiceForBCS to set
	 */
	public void setSdSAPIntegrationServiceForBCS(
			SDSAPIntegrationService sdSAPIntegrationServiceForBCS) {
		this.sdSAPIntegrationServiceForBCS = sdSAPIntegrationServiceForBCS;
	}
}
