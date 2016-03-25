package com.cg.lbs.bcun.scheduler.inbound;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cg.lbs.bcun.service.inbound.InboundBranchFTPFileProcessor;

/**
 * @author mohammes
 * Nov 29, 2013
 * 
 * This Scheduler Upload All files to central server
 * 
 */
public class InboundBranchFTPScheduler extends QuartzJobBean {

	/**
	 * Logger used to log the messages.
	 */
	private static final Logger logger = LoggerFactory.getLogger(InboundBranchFTPScheduler.class);
	
	/**
	 * Processor which is supposed to process the 
	 * processes. Object will be injected dynamically based on 
	 * spring's setter dependency injection
	 */
	private InboundBranchFTPFileProcessor inboundFileProcessor;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		logger.info("InboundBranchFTPScheduler::executeInternal::START===>");
		try{
		
		inboundFileProcessor.fileUploadProcess();
		}catch (HttpException e) {
			logger.error("InboundBranchFTPScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			logger.error("InboundBranchFTPScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			logger.error("InboundBranchFTPScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			logger.error("InboundBranchFTPScheduler::executeInternal::Exception::" , e);
		}
		logger.info("InboundBranchFTPScheduler::executeInternal::END===>");
	}

	/**
	 * @return the inboundFileProcessor
	 */
	public InboundBranchFTPFileProcessor getInboundFileProcessor() {
		return inboundFileProcessor;
	}

	/**
	 * @param inboundFileProcessor the inboundFileProcessor to set
	 */
	public void setInboundFileProcessor(
			InboundBranchFTPFileProcessor inboundFileProcessor) {
		this.inboundFileProcessor = inboundFileProcessor;
	}

	

}
