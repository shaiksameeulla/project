package com.ff.admin.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.billing.service.BillingCommonService;
import com.ff.admin.billing.service.BillingCommonServiceImpl;

public class BillingRateCalProcessScheduler extends QuartzJobBean {

	private BillingCommonService billingCommonService;
	private final static Logger LOGGER = LoggerFactory.getLogger(BillingCommonServiceImpl.class);
	/**
	 * @param billingCommonService
	 *            the billingCommonService to set
	 */
	public void setBillingCommonService(
			BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}


	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("BillingRateCalProcessScheduler::executeInternal::START----->");
		try {
			
			long start1=System.currentTimeMillis();
			billingCommonService.getConsignmentsForRate();
			
			LOGGER.debug("BillingRateCalProcessScheduler::executeInternal::Calling Billing Stored proc start----->");
			long start=System.currentTimeMillis();
		    //calling stored procedure
			billingCommonService.billing_consolidation_Proc();
			billingCommonService.billing_Stock_consolidation_Proc();
			long endTime=System.currentTimeMillis();
			long elapse=endTime-start;
			LOGGER.debug("BillingRateCalProcessScheduler::executeInternal::Calling Billing Stored proc end with execution time----->"+elapse);
			long endTime1=System.currentTimeMillis();
			long elapse1=endTime1-start1;
			LOGGER.debug("BillingRateCalProcessScheduler::executeInternal::Total Time required for Billing----->"+elapse1);
			

		} catch (CGBusinessException | CGSystemException | InterruptedException e) {
			LOGGER.error("Exception occurs in BillingRateCalProcessScheduler::executeInternal()::" 
					+ e);
		}
		LOGGER.debug("BillingRateCalProcessScheduler::executeInternal::END----->");

	}


}
