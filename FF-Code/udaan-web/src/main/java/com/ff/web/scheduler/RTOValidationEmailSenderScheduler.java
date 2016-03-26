package com.ff.web.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.web.manifest.rthrto.service.RthRtoValidationService;

/**
 * The Class RTOValidationEmailSenderScheduler.
 * 
 * @author narmdr
 */
public class RTOValidationEmailSenderScheduler extends QuartzJobBean {
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RTOValidationEmailSenderScheduler.class);
	RthRtoValidationService rthRtoValidationService;
	

	public void setRthRtoValidationService(
			RthRtoValidationService rthRtoValidationService) {
		this.rthRtoValidationService = rthRtoValidationService;
	}


	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			rthRtoValidationService.generateEodReport4OnHoldConsignment();
		} catch (HttpException e) {
			LOGGER.error("RTOValidationEmailSenderScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("RTOValidationEmailSenderScheduler::executeInternal::ClassNotFoundException::" ,e);
		} catch (IOException e) {
			LOGGER.error("RTOValidationEmailSenderScheduler::executeInternal::IOException::",e);
		} catch(Exception e) {
			LOGGER.error("RTOValidationEmailSenderScheduler::executeInternal::Exception::" , e);
		}
	}

}
