package com.cg.lbs.bcun.scheduler.outbound;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundCentralReconciliationDataProcessor;
/**
 * @author bmodala
 * 18 Aug,2015
 */
public class OutboundCentralReconciliationScheduler extends QuartzJobBean  {

	/**
	 * Used to log the message
	 */
	private static final Logger logger = LoggerFactory.getLogger(OutboundCentralReconciliationScheduler.class);
	
	/**
	 * Object used to process data at central server
	 */
	private OutboundCentralReconciliationDataProcessor dataProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
		logger.info("OutboundCentralReconciliationScheduler::executeInternal::start=====>");
		//Starting process
		dataProcessor.createCentralBlob();
		logger.debug("OutboundCentralReconciliationScheduler::executeInternal::end=====>");
		} catch(Exception e) {
			logger.error("OutboundCentralReconciliationScheduler::executeInternal::Exception::" , e);
		}
		logger.info("OutboundCentralReconciliationScheduler::executeInternal::END=====>");
	}
	/**
	 * Used to inject the dependent object by spring's setter injection
	 * @param dataProcessor
	 */
	public void setDataProcessor(
			OutboundCentralReconciliationDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

	
}
