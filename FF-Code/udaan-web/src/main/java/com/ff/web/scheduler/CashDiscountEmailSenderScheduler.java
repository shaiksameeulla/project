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
import com.ff.web.booking.service.CashBookingService;

public class CashDiscountEmailSenderScheduler extends QuartzJobBean {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashDiscountEmailSenderScheduler.class);
	private CashBookingService cashBookingService;

	/**
	 * @param cashBookingService
	 *            the cashBookingService to set
	 */
	public void setCashBookingService(CashBookingService cashBookingService) {
		this.cashBookingService = cashBookingService;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("CashDiscountEmailSenderScheduler::executeInternal::START::----------------->");
		try {
			cashBookingService.sendCashDiscountEmail();
		} catch (HttpException e) {
			LOGGER.error("CashDiscountEmailSenderScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("CashDiscountEmailSenderScheduler::executeInternal::ClassNotFoundException::",e);
		} catch (IOException e) {
			LOGGER.error("CashDiscountEmailSenderScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("CashDiscountEmailSenderScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("CashDiscountEmailSenderScheduler::executeInternal::END::----------------->");
	}
}
