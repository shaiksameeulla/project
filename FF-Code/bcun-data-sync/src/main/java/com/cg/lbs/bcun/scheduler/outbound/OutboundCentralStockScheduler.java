package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundCentralStockDataProcessor;

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
public class OutboundCentralStockScheduler extends QuartzJobBean  {

	/**
	 * Used to log the message
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundCentralStockScheduler.class);
	
	/**
	 * Object used to process data at central server
	 */
	private OutboundCentralStockDataProcessor dataProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try{
		LOGGER.info("OutboundCentralStockScheduler::executeInternal::start=====>");
		//Starting process
		dataProcessor.proceedOutbountDataPreparation();
		LOGGER.info("OutboundCentralStockScheduler::executeInternal::end=====>");
		}catch (HttpException e) {
			LOGGER.error("OutboundCentralStockScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			LOGGER.error("OutboundCentralStockScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			LOGGER.error("OutboundCentralStockScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("OutboundCentralStockScheduler::executeInternal::Exception::" , e);
		}
	}

	/**
	 * Used to inject the dependent object by spring's setter injection
	 * @param dataProcessor
	 */
	public void setDataProcessor(OutboundCentralStockDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

}
