package com.ff.rate.calculation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.rate.calculation.dao.RateCalculationDAO;

/**
 * @author mohammal
 * May 17, 2013
 * 
 */
public class RateCalculationServiceFactory {
	
	private static final Logger LOGGER = Logger.getLogger(RateCalculationServiceFactory.class);
	
	/**
	 * 
	 */
	@Autowired private ApplicationContext applicationContext;
	
	/**
	 * 
	 */
	private Map<String, RateCalculationService> serviceImpl;
	
	protected RateCalculationDAO rateCalcDAO;

	public void setRateCalcDAO(RateCalculationDAO rateCalcDAO) {
		this.rateCalcDAO = rateCalcDAO;
	}

	public void setServiceImpl(Map<String, RateCalculationService> serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	
	/**
	 * @param serviceName
	 * @return
	 * @throws CGBusinessException
	 */
	
	public RateCalculationService getService(String serviceName) throws CGBusinessException {
		LOGGER.debug("RateCalculationServiceFactory::getService::looking for service [" + serviceName + "]");
		//Checking the bean name looking for 
		if (serviceName == null) {
			//Returning the message code from RateMessageResource.properties file
			throw new CGBusinessException("RCI000");
		}
		//
		RateCalculationService service = serviceImpl.get(serviceName);
		if(service == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("RateCalculationServiceFactory::getService::there is no service configured for rate with name [" + serviceName + "]");
			}
			try {
				service = applicationContext.getBean(serviceName, RateCalculationService.class);
			} catch (ClassCastException ex) {
				LOGGER.error("RateCalculationServiceFactory::getService::ClassCastException::asking service is not a rate calculation service");
			} catch (Exception ex) {
				LOGGER.error("RateCalculationServiceFactory::getService::Exception::" + ex.getMessage());
			}
			
		}
		return service;
	}

	public List<BookingPreferenceDetailsTO> getBookingPrefDetails() throws CGSystemException, CGBusinessException {
		LOGGER.trace("EmotionalBondBookingServiceImpl::getBookingPrefDetails::START------------>:::::::");
		// TODO Auto-generated method stub
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		List<BookingPreferenceDetailsTO> bookingPrefTOs = null;
		bookingPrefDOs = rateCalcDAO.getBookingPrefDetails();
		if (!StringUtil.isEmptyColletion(bookingPrefDOs)) {
			bookingPrefTOs = new ArrayList<>();
			bookingPrefTOs = (List<BookingPreferenceDetailsTO>) CGObjectConverter
					.createTOListFromDomainList(bookingPrefDOs,
							BookingPreferenceDetailsTO.class);
		}
		LOGGER.trace("EmotionalBondBookingServiceImpl::getBookingPrefDetails::END------------>:::::::");
		return bookingPrefTOs;
	}
	
	
	
}
