/**
 * 
 */
package com.ff.web.ratecalculator.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rateCalculator.RateCalculatorTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author prmeher
 * 
 */
public interface RateCalculatorService {

	/**
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<ProductTO> getProductTypes() throws CGBusinessException,
			CGSystemException;

	List<ConsignmentTypeTO> getConsignmentType() throws CGSystemException,
			CGBusinessException;

	List<StockStandardTypeTO> getStockStdTypeByType(String typeName)
			throws CGBusinessException, CGSystemException;

	RateCalculationOutputTO calculateRates(RateCalculatorTO rateCalculatorTO, RateCalculationServiceFactory serviceFactory)
			throws CGBusinessException, CGSystemException;

	List<BookingPreferenceDetailsTO> getAllPreferenceDetails()throws CGBusinessException, CGSystemException;
}
