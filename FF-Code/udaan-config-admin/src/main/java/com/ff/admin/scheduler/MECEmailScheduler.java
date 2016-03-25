package com.ff.admin.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.admin.mec.email.service.MECEmailService;

/**
 * @author hkansagr
 */
public class MECEmailScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MECEmailScheduler.class);

	/** The MEC email service. */
	private MECEmailService mecEmailService;

	/**
	 * @param mecEmailService
	 *            the mecEmailService to set
	 */
	public void setMecEmailService(MECEmailService mecEmailService) {
		this.mecEmailService = mecEmailService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.trace("MECEmailScheduler :: Scheduler :: executeInternal() :: START");
		try {
			triggerEmailToRHO();
		} catch (HttpException e) {
			LOGGER.error("MECEmailScheduler::executeInternal::HttpException::" + e.getMessage());
		} catch (ClassNotFoundException e) {
			LOGGER.error("MECEmailScheduler::executeInternal::ClassNotFoundException::" + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("MECEmailScheduler::executeInternal::IOException::" + e.getMessage());
		} catch(Exception e) {
			LOGGER.error("MECEmailScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("MECEmailScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * To send email to all RHO office(s) with attached excel, which contain all
	 * respective branches expense/collection details
	 * 
	 * @throws Exception
	 */
	private void triggerEmailToRHO() throws Exception {
		LOGGER.trace("MECEmailScheduler :: triggerEmailToRHO() :: START");
		mecEmailService.triggerEmailToRHO();
		LOGGER.trace("MECEmailScheduler :: triggerEmailToRHO() :: END");
	}

}
