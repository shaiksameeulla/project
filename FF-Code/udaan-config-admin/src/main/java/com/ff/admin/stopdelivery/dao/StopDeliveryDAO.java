package com.ff.admin.stopdelivery.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserTO;

public interface StopDeliveryDAO {
	
	public BookingDO getConsgBookingDeatils(String consgNo) throws CGSystemException;
	public OfficeDO checkOriginOffBelongsToRHO(Integer rhoId,Integer branchOffId) throws CGSystemException;
	public OfficeDO checkOriginOffBelongsToHO(Integer hubOffId,Integer branchOffId)throws CGSystemException;
	public boolean updateStopDeliveryFlag(String consgNo,Integer reasonId,String remark, UserTO userTO,OfficeTO loggedInOfficeTO)throws CGSystemException;
	public ManifestDO getOfficeEmailIdsByTravelPath(ConsignmentTO consignmentTO)throws CGSystemException;
	public List<OfficeDO> getAllHubOff(OfficeTO officeTO)throws CGSystemException;
}
