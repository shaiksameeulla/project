package com.ff.web.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.CreditCustomerBookingParcelTO;

public class BulkBookingHandlingServiceImpl implements BulkBookingHandlingService{
	@Autowired
	private BulkBookingService bulkBookingService = null;

	/**
	 * @return the bulkBookingService
	 */
	public BulkBookingService getBulkBookingService() {
		return bulkBookingService;
	}

	/**
	 * @param bulkBookingService the bulkBookingService to set
	 */
	public void setBulkBookingService(BulkBookingService bulkBookingService) {
		this.bulkBookingService = bulkBookingService;
	}

	
	
	

	
}
