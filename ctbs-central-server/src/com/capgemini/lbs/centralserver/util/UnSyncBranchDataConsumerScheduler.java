package com.capgemini.lbs.centralserver.util;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.bs.Persist2QueueService;



// TODO: Auto-generated Javadoc
/**
 * The Class UnSyncBranchDataConsumerScheduler.
 */
public class UnSyncBranchDataConsumerScheduler extends QuartzJobBean {
	
	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(UnSyncBranchDataConsumerScheduler.class);
	
	/** The persist2 queue service. */
	private Persist2QueueService persist2QueueService;
	
	/** The active file location. */
	private String activeFileLocation;
	

	/**
	 * Execute internal.
	 *
	 * @param arg0 the arg0
	 * @throws JobExecutionException the job execution exception
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		logger.debug("UnSyncBranchDataConsumerScheduler::executeInternal::start=====>");
		try {

			if(activeFileLocation != null) {
				if(!activeFileLocation.isEmpty()) {
					persist2QueueService.handlePersistProcess(activeFileLocation);
				}
			} 
			
		} catch (Exception ex) {
			logger.error("UnSyncBranchDataConsumerScheduler::executeInternal::Exception occured:"
					+ex.getMessage());
		} 
		logger.debug("UnSyncBranchDataConsumerScheduler::executeInternal::end=====>");
	}

	/**
	 * Sets the persist2 queue service.
	 *
	 * @param persist2QueueService the new persist2 queue service
	 */
	public void setPersist2QueueService(Persist2QueueService persist2QueueService) {
		this.persist2QueueService = persist2QueueService;
	}

	/**
	 * Sets the active file location.
	 *
	 * @param activeFileLocation the new active file location
	 */
	public void setActiveFileLocation(String activeFileLocation) {
		this.activeFileLocation = activeFileLocation;
	}
	
}




