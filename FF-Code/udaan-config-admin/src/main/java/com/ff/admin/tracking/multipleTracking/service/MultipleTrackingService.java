package com.ff.admin.tracking.multipleTracking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.tracking.TrackingBulkImportTO;

public interface MultipleTrackingService {

	public List<TrackingBulkImportTO> getMultipleConsgDetails(TrackingBulkImportTO bulkTO)throws CGSystemException, CGBusinessException;
}
