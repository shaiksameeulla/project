package com.cg.lbs.bcun.scheduler.inbound;


import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.inbound.InboundCentralErrorDataProcessor;

/**
 * @author mohammal
 * Jan 15, 2013
 * 
 * This class will create a scheduler according to configured interval to
 * schedule the in bound branch BCUN activities for all the configured process.
 * BCUN activities are configured based on bcun.properties file 
 * and process are configured based on inboundConfig.properties
 * 
 */
public class InboundCentralErrorFileReProcessScheduler extends QuartzJobBean {

	/**
	 * Logger used to log the messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InboundCentralErrorFileReProcessScheduler.class);
	
	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	private InboundCentralErrorDataProcessor inboundDataProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		logger.info("InboundCentralErrorFileReProcessScheduler::executeInternal::start===>");
		try {
			inboundDataProcessor.processCentraldata();
		}  catch (HttpException e) {
			logger.error("InboundCentralErrorFileReProcessScheduler::executeInternal::HttpException::" ,e );
		} catch (ClassNotFoundException e) {
			logger.error("InboundCentralErrorFileReProcessScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			logger.error("InboundCentralErrorFileReProcessScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			logger.error("InboundCentralErrorFileReProcessScheduler::executeInternal::Exception::" , e);
		}
		logger.info("InboundCentralErrorFileReProcessScheduler::executeInternal::end===>");
	}

	public void setInboundDataProcessor(
			InboundCentralErrorDataProcessor inboundDataProcessor) {
		this.inboundDataProcessor = inboundDataProcessor;
	}
}
