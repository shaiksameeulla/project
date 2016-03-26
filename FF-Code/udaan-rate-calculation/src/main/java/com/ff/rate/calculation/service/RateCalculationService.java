package com.ff.rate.calculation.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.geography.CityTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateCalculationTO;

/**
 * @author mohammal May 17, 2013 This service class will define the rules for
 *         rate calculation.
 */
public interface RateCalculationService {

	public CityTO getCity(String Pinocde) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public RateCalculationOutputTO calculateRate(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param input
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	public void validateInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;
	/**
	 * @param input
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	public void validateCommonInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;
	/**
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 */
	public SectorDO getDestinationSector(RateCalculationTO input)
			throws CGBusinessException;

	/**
	 * 
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RateComponentDO> getRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param productToBeValidatedInputTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean isProductValidForContract(ProductToBeValidatedInputTO productToBeValidatedInputTO)
			throws CGBusinessException, CGSystemException;
	

	/**
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OctroiRateCalculationOutputTO calculateOCTROI(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<RateComponentDO> getOctroiRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @param input
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void validateOctroiInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @param input
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void validateOctroiCommonInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException;

}
