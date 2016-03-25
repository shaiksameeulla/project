package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.cg.lbs.bcun.service.outbound.OutboundBranchReconcileDataProcessor;

/**
 * @author bmodala
 * 18 Aug,2015
 * Out bound branch scheduler is used to fetch out bound data from 
 * central server. central server information will be configured 
 * in side the bcun.properties file. Branch scheduler will send the
 * request along with process name and branch code to central server
 * and central server will process the request and respond it accordingly.
 *  
 */
public class OutboundBranchReconcileDataProcessorScheduler  extends QuartzJobBean {
	
	/**
	 * variable used to log the messages
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchReconcileDataProcessorScheduler.class);

	/**
	 * Processor used to process out bound branch request.
	 */
	private OutboundBranchReconcileDataProcessor outboundBranchReconcileDataProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			LOGGER.debug("OutboundBranchReconcileDataProcessorScheduler::executeInternal:: start======>");
			//Starting out bound branch scheduling
			outboundBranchReconcileDataProcessor.processBranchdata();
			//Ending out bound branch scheduling
			LOGGER.debug("OutboundBranchReconcileDataProcessorScheduler::executeInternal:: end======>");
		} catch (HttpException e) {
			LOGGER.error("OutboundBranchReconcileDataProcessorScheduler::executeInternal::HttpException::" , e );
		} catch (ClassNotFoundException e) {
			LOGGER.error("OutboundBranchReconcileDataProcessorScheduler::executeInternal::ClassNotFoundException::" , e );
		} catch (IOException e) {
			LOGGER.error("OutboundBranchReconcileDataProcessorScheduler::executeInternal::IOException::" , e );
		} catch(Exception e) {
			LOGGER.error("OutboundBranchReconcileDataProcessorScheduler::executeInternal::Exception::" , e);
		}
	}

	/**
	 * Used for spring's setter injection
	 * @param outboundBranchProcessor
	 */

	public void setOutboundBranchReconcileDataProcessor(
			OutboundBranchReconcileDataProcessor outboundBranchReconcileDataProcessor) {
		this.outboundBranchReconcileDataProcessor = outboundBranchReconcileDataProcessor;
	}
	
}
