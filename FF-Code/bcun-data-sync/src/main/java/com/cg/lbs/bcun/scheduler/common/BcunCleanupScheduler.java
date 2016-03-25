package com.cg.lbs.bcun.scheduler.common;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.common.BcunCleanupFileProcessor;

/**
 * @author mohammes
 * Jan 15, 2013
 * 
 * This class act as clean up scheduler based Time interval in the  bcun-cleanup-scheduler.
 * it's for one of the BCUN activities ie for cleaning of the folders/files(moving to other folders)
 * 
 */
public class BcunCleanupScheduler extends QuartzJobBean {

	/**
	 * Logger used to log the messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BcunCleanupScheduler.class);
	
	/**
	 * @return the cleanupFileProcessor
	 */
	public BcunCleanupFileProcessor getCleanupFileProcessor() {
		return cleanupFileProcessor;
	}

	/**
	 * @param cleanupFileProcessor the cleanupFileProcessor to set
	 */
	public void setCleanupFileProcessor(
			BcunCleanupFileProcessor cleanupFileProcessor) {
		this.cleanupFileProcessor = cleanupFileProcessor;
	}

	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	private BcunCleanupFileProcessor cleanupFileProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		try {
			logger.debug("BcunCleanupScheduler::executeInternal::start===>");
			cleanupFileProcessor.cleanupProcess();
			logger.debug("BcunCleanupScheduler::executeInternal::end===>");
		}catch (HttpException e) {
			logger.error("BcunCleanupScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			logger.error("BcunCleanupScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			logger.error("BcunCleanupScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			logger.error("BcunCleanupScheduler::executeInternal::Exception::" , e);
		}	
	
}
	
}
