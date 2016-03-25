package com.ff.admin.stopdelivery.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.organization.OfficeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.utilities.BookingStopDlvTO;

public interface StopDeliveryService {

	public ConsignmentTO getConsignmentOriginOffice(String consgNo) throws CGSystemException,CGBusinessException;
	public BookingStopDlvTO getConsgBookingDeatils(String consgNo)throws CGSystemException,CGBusinessException;
	public String getDeliveryStatus(String consgNo)throws CGSystemException,CGBusinessException;
	public OfficeDO checkOriginOffBelongsToRHO(Integer rhoId,Integer branchOffId)throws CGSystemException,CGBusinessException;
	public OfficeTO getOfficeDeatls(Integer orgOffId)throws CGSystemException,CGBusinessException;
	public OfficeDO checkOriginOffBelongsToHO(Integer hubOffId,Integer branchOffId)throws CGSystemException,CGBusinessException;
	public List<ReasonTO> getStopDeliveryReason()throws CGSystemException,CGBusinessException;
	public boolean  updateStopDeliveryFlag(String consgNo,Integer reasonId,String remark,OfficeTO loggedInOfficeTO) throws CGSystemException,CGBusinessException;
	public void sendEmail(MailSenderTO emailTO)throws CGSystemException,CGBusinessException;
	public List<String> getOfficeEmailIdsByTravelPath(ConsignmentTO consignmentTO)throws CGSystemException,CGBusinessException;
	public List<String> getHubOffice(OfficeTO officeTO)throws CGSystemException,CGBusinessException;
}
