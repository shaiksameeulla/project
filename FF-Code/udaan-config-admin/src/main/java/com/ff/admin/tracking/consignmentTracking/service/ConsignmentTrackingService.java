/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingConsignmentTO;

/**
 * @author uchauhan
 *
 */
public interface ConsignmentTrackingService {

	public TrackingConsignmentTO viewTrackInformation(String consgNum ,String refNum,String loginUserType) throws CGSystemException,CGBusinessException;
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,CGSystemException;
}
