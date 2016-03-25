package com.cg.lbs.bcun.scheduler.inbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.inbound.InboundCentralDataProcessor;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 * This class will create a scheduler according to configured interval to
 * schedule the in bound central BCUN activities for all the configured process.
 * BCUN activities are configured based on bcun.properties file 
 * and process are configured based on inboundConfig.properties
 * 
 */
public class InboundCentralScheduler extends QuartzJobBean {

	/**
	 * Logger used to log the messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InboundCentralScheduler.class);
	
	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	InboundCentralDataProcessor inboundFileProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("InboundCentralScheduler::executeInternal::start===>");
		try{
		
		inboundFileProcessor.proceedFileProcessing();
		
		}catch (HttpException e) {
			logger.error("InboundCentralScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			logger.error("InboundCentralScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			logger.error("InboundCentralScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			logger.error("InboundCentralScheduler::executeInternal::Exception::" , e);
		}
		logger.info("InboundCentralScheduler::executeInternal::end===>");
	}
	
	/**
	 * Used for spring's setter injection
	 * @param inboundFileProcessor
	 */
	public void setInboundFileProcessor(InboundCentralDataProcessor inboundFileProcessor) {
		this.inboundFileProcessor = inboundFileProcessor;
	}
}
