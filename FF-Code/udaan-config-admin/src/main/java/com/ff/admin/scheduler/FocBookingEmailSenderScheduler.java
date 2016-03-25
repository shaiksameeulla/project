package com.ff.admin.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.booking.service.BookingUniversalServiceImpl;
/**
 * The Class FocBookingEmailSenderScheduler to implement the functionality to
 * send emails to FOC Booking approvers
 * 
 * @author shashsax
 * 
 */
public class FocBookingEmailSenderScheduler extends QuartzJobBean {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BookingUniversalServiceImpl.class);

	private BookingUniversalService bookingUniversalService;

	/**
	 * @param bookingUniversalService
	 *            the bookingUniversalService to set
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.debug("FocBookingEmailSenderScheduler::executeInternal::START::----------------->");
		try {
			bookingUniversalService.sendEmailForFocBooking();
		} catch (CGBusinessException | CGSystemException exception) {
			LOGGER.error(
					"Exception occur in::FocBookingEmailSenderScheduler::executeInternal()::",
					exception);
		}catch (HttpException e) {
			LOGGER.error("FocBookingEmailSenderScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("FocBookingEmailSenderScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("FocBookingEmailSenderScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("FocBookingEmailSenderScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("FocBookingEmailSenderScheduler::executeInternal::END::----------------->");
	}

}
