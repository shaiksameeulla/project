package com.ff.admin.tracking.common.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.tracking.TrackingBulkImportTO;

public interface TrackingCommonService {

	/**
	 * gets the bulk tracking details
	 * @param consgs
	 * @param bulkTO
	 * @return
	 * @throws CGSystemException
	 * @throws NumberFormatException
	 * @throws CGBusinessException
	 */
	public List<TrackingBulkImportTO> getBulkTrackDetails(TrackingBulkImportTO bulkTO)throws CGSystemException, NumberFormatException, CGBusinessException;
	
	public TrackingBulkImportTO isValidConsgNum(List<String> consgNum,
			TrackingBulkImportTO bulkTO);
}
