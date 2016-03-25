package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundCentralDataProcessor;

/**
 * @author mohammal
 * Jan 15, 2013
 * Out bound central scheduler is used to process the data which
 * has to be transfered to branches. This scheduler will fetch the
 * data from database and create a xml file containing JSON string
 * and zip that file and store in a table with respect to branch
 * for which data belong to.Same data will be returned when any 
 * request will be received from branch server for that process.
 */
public class OutboundCentralScheduler extends QuartzJobBean  {

	/**
	 * Used to log the message
	 */
	private static final Logger logger = LoggerFactory.getLogger(OutboundCentralScheduler.class);
	
	/**
	 * Object used to process data at central server
	 */
	private OutboundCentralDataProcessor dataProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
		logger.info("OutboundCentralScheduler::executeInternal::start=====>");
		//Starting process
		dataProcessor.proceedOutbountDataPreparation();
		}catch (HttpException e) {
			logger.error("OutboundCentralScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			logger.error("OutboundCentralScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			logger.error("OutboundCentralScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			logger.error("OutboundCentralScheduler::executeInternal::Exception::" , e);
		}
		logger.info("OutboundCentralScheduler::executeInternal::end=====>");
	}

	/**
	 * Used to inject the dependent object by spring's setter injection
	 * @param dataProcessor
	 */
	public void setDataProcessor(OutboundCentralDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

}
