package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundCentralConsignmentDataProcessor;

/**
 * @author narmdr 
 * 		   Jan 03, 2014 Out bound central Consignment scheduler is used to process
 *         the Consignment data which has to be transfered to branches. This scheduler will
 *         fetch the data from database and create a xml file containing JSON
 *         string and zip that file and store in a table with respect to branch
 *         for which data belong to.Same data will be returned when any request
 *         will be received from branch server for that process.
 */
public class OutboundCentralConsignmentScheduler extends QuartzJobBean {

	/**
	 * Used to log the message
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(OutboundCentralConsignmentScheduler.class);

	/**
	 * Object used to process data at central server
	 */
	private transient OutboundCentralConsignmentDataProcessor dataProcessor;
	
	/**
	 * Sets the data processor.
	 *
	 * @param dataProcessor the new data processor
	 */
	public void setDataProcessor(
			OutboundCentralConsignmentDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info("OutboundCentralConsignmentScheduler::executeInternal::start=====>");
		try{
		
		// Starting process
		dataProcessor.proceedOutbountDataPreparation();
		}catch (HttpException e) {
			logger.error("OutboundCentralConsignmentScheduler::executeInternal::HttpException::", e  );
		} catch (ClassNotFoundException e) {
			logger.error("OutboundCentralConsignmentScheduler::executeInternal::ClassNotFoundException::" , e  );
		} catch (IOException e) {
			logger.error("OutboundCentralConsignmentScheduler::executeInternal::IOException::" , e  );
		} catch(Exception e) {
			logger.error("OutboundCentralConsignmentScheduler::executeInternal::Exception::" , e);
		}
		logger.info("OutboundCentralConsignmentScheduler::executeInternal::end=====>");
	}

}
