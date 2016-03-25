package com.cg.lbs.bcun.scheduler.inbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.cg.lbs.bcun.service.inbound.InboundCentralReconciliationDataProcessor;

/**
 * @author bmodala	
 * Aug 24, 2015
 * 
 * This class will create a scheduler according to configured interval to
 * schedule the in bound central BCUN activities for all the configured process.
 * BCUN activities are configured based on bcun.properties file 
 * and process are configured based on ReconcillationConfig.properties
 * 
 */
public class InboundCentralReconciliationScheduler extends QuartzJobBean {

	/**
	 * Logger used to log the messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InboundCentralReconciliationScheduler.class);
	
	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	InboundCentralReconciliationDataProcessor inboundFileProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("InboundCentralReconciliationScheduler::executeInternal::start===>");
		try{	
		inboundFileProcessor.proceedFileProcessing();	
		}catch (HttpException e) {
			logger.error("InboundCentralReconciliationScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			logger.error("InboundCentralReconciliationScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			logger.error("InboundCentralReconciliationScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			logger.error("InboundCentralReconciliationScheduler::executeInternal::Exception::" , e);
		}
		logger.info("InboundCentralReconciliationScheduler::executeInternal::end===>");
	}
	
	/**
	 * Used for spring's setter injection
	 * @param inboundFileProcessor
	 */

	public void setInboundFileProcessor(
			InboundCentralReconciliationDataProcessor inboundFileProcessor) {
		this.inboundFileProcessor = inboundFileProcessor;
	}
}
