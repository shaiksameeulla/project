package com.ff.rate.configuration.ebrate.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;

public interface EmotionalBondRateService {

	Boolean deactivatePreferences(List<Integer> prefIds)
			throws CGBusinessException, CGSystemException;

	List<RateTaxComponentTO> loadDefaultTaxComponent(String stateId)
			throws CGBusinessException, CGSystemException;

	boolean saveOrUpdateEBRate(EBRateConfigTO ebRateConfigTO)
			throws CGBusinessException, CGSystemException;

	EBRateConfigTO loadDefaultEBRates(String stateId, String action,
			String ebRateConfigId) throws CGBusinessException,
			CGSystemException;

}
