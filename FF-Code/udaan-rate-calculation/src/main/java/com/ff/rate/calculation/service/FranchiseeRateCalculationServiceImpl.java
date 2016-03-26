package com.ff.rate.calculation.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;

public class FranchiseeRateCalculationServiceImpl extends AbstractRateCalculationServiceImpl{

	@Override
	public void validateInputs(RateCalculationInputTO input)
			throws CGBusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RateComponentDO> getRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isProductValidForContract(ProductToBeValidatedInputTO input)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RateComponentDO> getOctroiRateComponents(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateOctroiInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		
	}


}
