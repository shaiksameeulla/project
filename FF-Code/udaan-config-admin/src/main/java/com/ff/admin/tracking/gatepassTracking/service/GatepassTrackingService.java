package com.ff.admin.tracking.gatepassTracking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingGatepassTO;

public interface GatepassTrackingService {
	
	public TrackingGatepassTO viewTrackInformation(String number, String type)
			throws CGSystemException, CGBusinessException ;
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,CGSystemException;

}
