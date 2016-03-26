package com.ff.rate.configuration.ebrate.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.ratemanagement.masters.EBRateConfigDO;

public interface EmotionalBondRateDAO {

	Boolean deactivatePreferences(List<Integer> prefIds)
			throws CGSystemException;

	EBRateConfigDO saveOrUpdateEBRate(EBRateConfigDO ebRateConfigDO)
			throws CGSystemException;

	void updateEBRateTODate(Integer currentEBRateConfigId, Date date)
			throws CGSystemException;

	List<EBRateConfigDO> loadDefaultEBRates(Integer stateId)
			throws CGSystemException;

	EBRateConfigDO isRateRenewed(List<EBRateConfigDO>  ebRateConfigTO)
			throws CGSystemException;
}
