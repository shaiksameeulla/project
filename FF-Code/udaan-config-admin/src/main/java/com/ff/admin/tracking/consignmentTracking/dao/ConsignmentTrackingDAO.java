/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.domain.umc.UserDO;

/**
 * @author uchauhan
 *
 */
public interface ConsignmentTrackingDAO  {

	List<BookingDO> viewTrackInformation(String consgNum, String refNum) throws CGSystemException;

	ConsignmentDO getChildConsgDetails(String consgNumber) throws CGSystemException;

	List<ProcessMapDO> getDetailedTrackingInformation(String consgNum) throws CGSystemException;
	List<ProcessDO> getProcessDetails() throws CGSystemException;
	
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException;
	public UserDO getCreatedByDeatils(Integer userId )throws CGSystemException;

	public List<ConsignmentDO> getConsignmentDtls(String consgNum, String refNum) throws CGBusinessException, CGSystemException;
	
}
