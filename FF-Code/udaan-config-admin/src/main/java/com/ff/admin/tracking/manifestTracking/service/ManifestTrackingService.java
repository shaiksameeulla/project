package com.ff.admin.tracking.manifestTracking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ManifestTrackingTO;


public interface ManifestTrackingService {
	
	public ManifestTrackingTO viewTrackInformation(String manifestNumber,String manifestType) throws CGSystemException,CGBusinessException;
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,CGSystemException;

}
