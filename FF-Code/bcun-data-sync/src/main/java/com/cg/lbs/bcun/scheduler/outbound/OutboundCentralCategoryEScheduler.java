package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.outbound.OutboundCentralCategoryDataProcessor;

/**
 * @author narmdr
 * Aug 14, 2014
 * Out bound central scheduler is used to process the data which
 * has to be transfered to category E branches. This scheduler will fetch the
 * data from database and create a xml file containing JSON string
 * and zip that file and store in a table with respect to branch
 * for which data belong to.Same data will be returned when any 
 * request will be received from branch server for that process.
 */
public class OutboundCentralCategoryEScheduler extends QuartzJobBean  {

	/**
	 * Used to log the message
	 */
	private static final Logger logger = LoggerFactory.getLogger(OutboundCentralCategoryEScheduler.class);
	
	/**
	 * Object used to process data at central server
	 */
	private OutboundCentralCategoryDataProcessor dataProcessor;
	
	/**
	 * @param dataProcessor the dataProcessor to set
	 */
	public void setDataProcessor(OutboundCentralCategoryDataProcessor dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info("OutboundCentralCategoryEScheduler::executeInternal::start=====>");
		//Starting process
		try {
			dataProcessor.proceedOutbountDataPreparation(BcunConstant.OUTBOUND_OFFICE_CATEGORY_E);
		} catch (HttpException e) {
			logger.error("OutboundCentralCategoryEScheduler::executeInternal::HttpException::", e);
		} catch (ClassNotFoundException e) {
			logger.error("OutboundCentralCategoryEScheduler::executeInternal::ClassNotFoundException::", e);
		} catch (IOException e) {
			logger.error("OutboundCentralCategoryEScheduler::executeInternal::IOException::", e);
		} catch(Exception e) {
			logger.error("OutboundCentralCategoryEScheduler::executeInternal::Exception::" , e);
		}
		logger.info("OutboundCentralCategoryEScheduler::executeInternal::end=====>");
	}

}
