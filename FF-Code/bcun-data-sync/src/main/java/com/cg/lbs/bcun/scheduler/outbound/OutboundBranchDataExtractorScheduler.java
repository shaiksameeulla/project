package com.cg.lbs.bcun.scheduler.outbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.outbound.OutboundBranchDataExtractProcessor;

/**
 * @author mohammal
 * Feb 12, 2013
 * Out bound branch scheduler is used to fetch out bound data from 
 * central server. central server information will be configured 
 * in side the bcun.properties file. Branch scheduler will send the
 * request along with process name and branch code to central server
 * and central server will process the request and respond it accordingly.
 *  
 */
public class OutboundBranchDataExtractorScheduler  extends QuartzJobBean {
	
	/**
	 * variable used to log the messages
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundBranchDataExtractorScheduler.class);

	/**
	 * Processor used to process out bound branch request.
	 */
	private OutboundBranchDataExtractProcessor outboundBranchProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			LOGGER.debug("OutboundBranchDataExtractorScheduler::executeInternal:: start======>");
			//Starting out bound branch scheduling
			outboundBranchProcessor.processBranchdata();
			//Ending out bound branch scheduling
			LOGGER.debug("OutboundBranchDataExtractorScheduler::executeInternal:: end======>");
		} catch (HttpException e) {
			LOGGER.error("OutboundBranchDataExtractorScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("OutboundBranchDataExtractorScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("OutboundBranchDataExtractorScheduler::executeInternal::IOException::" , e);
		} catch (Exception e) {
			LOGGER.error("OutboundBranchDataExtractorScheduler::executeInternal::Exception::",e);
			//Send email of thread exception in downloading
			outboundBranchProcessor.sendEmailNotificationForBlobDownloadError();
		}
	}

	/**
	 * Used for spring's setter injection
	 * @param outboundBranchProcessor
	 */
	public void setOutboundBranchProcessor(
			OutboundBranchDataExtractProcessor outboundBranchProcessor) {
		this.outboundBranchProcessor = outboundBranchProcessor;
	}
}
