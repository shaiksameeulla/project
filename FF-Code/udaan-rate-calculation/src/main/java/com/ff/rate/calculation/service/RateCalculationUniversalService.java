package com.ff.rate.calculation.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;

/**
 * @author prmeher
 *
 */
public interface RateCalculationUniversalService {

	
	/**
	 * Calculate rate for consignment.
	 * 
	 * @param consignmentDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	public ConsignmentRateCalculationOutputTO calculateRateForConsignment(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @param consignmentTO
	 * @return OctroiRateCalculationOutputTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OctroiRateCalculationOutputTO calculateOCTROI(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	public List<ConsignmentBilling> getConsignmentForRate(int i, String rateType)throws CGBusinessException, CGSystemException;

	public List<ConsignmentTO> convertConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs)throws CGBusinessException, CGSystemException;
	
	public RateCalculationOutputTO calculateRate(
			RateCalculationInputTO inputTO)throws CGBusinessException, CGSystemException;
}
