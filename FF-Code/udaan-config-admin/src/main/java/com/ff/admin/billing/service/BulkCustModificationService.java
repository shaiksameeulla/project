package com.ff.admin.billing.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;


public interface BulkCustModificationService {
	
	List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;
	List<CityTO> getCitysByStateId(Integer stateId) throws CGBusinessException,
	CGSystemException;
	
}
