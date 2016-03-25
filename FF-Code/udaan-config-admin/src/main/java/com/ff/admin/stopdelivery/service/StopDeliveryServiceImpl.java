package com.ff.admin.stopdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.EmailUtilTO;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stopdelivery.constants.StopDeliveryConstants;
import com.ff.admin.stopdelivery.dao.StopDeliveryDAO;
import com.ff.booking.BookingGridTO;
import com.ff.booking.BookingTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.utilities.BookingStopDlvTO;
import com.ff.umc.UserTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.util.UdaanContextService;

public class StopDeliveryServiceImpl implements StopDeliveryService {
    
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(StopDeliveryServiceImpl.class);
	private transient ConsignmentCommonService consignmentCommonService;
	private transient StopDeliveryDAO stopDeliveryDAO;
	private transient DeliveryUniversalService deliveryUniversalService;
	private transient OrganizationCommonService organizationCommonService;
	private transient ServiceOfferingCommonService serviceOfferingCommonService;
	private EmailSenderUtil emailSenderUtil;
	private transient GeographyCommonService geographyCommonService;
	private transient UdaanContextService udaanContextService;
	
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}
	
	

	public void setStopDeliveryDAO(StopDeliveryDAO stopDeliveryDAO) {
		this.stopDeliveryDAO = stopDeliveryDAO;
	}



	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}


	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}



	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

    

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}



	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}



	public void setUdaanContextService(UdaanContextService udaanContextService) {
		this.udaanContextService = udaanContextService;
	}



	public ConsignmentTO getConsignmentOriginOffice(String consgNo) throws CGSystemException,CGBusinessException{
		ConsignmentTO consignmentTO=null;
		try{
			
			consignmentTO=consignmentCommonService.getConsingmentDtls(consgNo);
			if(StringUtil.isNull(consignmentTO)){
				throw new CGBusinessException(AdminErrorConstants.NO_CN_DETAILS_FOUND);
			}
		}catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getConsignmentOriginOffice()::" 
					+ e);
			throw new CGBusinessException(AdminErrorConstants.NO_CN_DETAILS_FOUND,e);
		}
		return consignmentTO;
	}
	
	public BookingStopDlvTO getConsgBookingDeatils(String consgNo)throws CGSystemException,CGBusinessException{
		BookingStopDlvTO bookingTO=null;
		try{
			BookingDO bookingDO=stopDeliveryDAO.getConsgBookingDeatils(consgNo);
			//BookingTO bookingTO=new BookingTO();
			if(!StringUtil.isNull(bookingDO)){
			 bookingTO=prepareBookingDO2TO(bookingDO);
			}
			else{
				throw new CGBusinessException(AdminErrorConstants.NO_BOOKING_DETIALS);
			}
			
		}catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getConsgBookingDeatils()::" 
					+ e);
			throw new CGBusinessException(AdminErrorConstants.NO_BOOKING_DETIALS,e);
		}
		return bookingTO;
	}
	
	@SuppressWarnings("null")
	public BookingStopDlvTO prepareBookingDO2TO(BookingDO bookingDO)throws CGSystemException,CGBusinessException{
		BookingStopDlvTO bookingTO=new BookingStopDlvTO();
		//bookingDO.setBookingDate(bookingDO.getBookingDate().toString());
		//CGObjectConverter.createToFromDomain(bookingDO, bookingTO);
		bookingTO.setConsgNumber(bookingDO.getConsgNumber());
		bookingTO.setBookingId(bookingDO.getBookingId());
		bookingTO.setBookingDate(bookingDO.getBookingDate());
		
		if(!StringUtil.isNull(bookingDO.getPincodeId())){
			PincodeTO  pincodeTO=new PincodeTO();
			CGObjectConverter.createToFromDomain(bookingDO.getPincodeId(), pincodeTO);
			bookingTO.setPincode(pincodeTO.getPincode());
		}
		if(!StringUtil.isNull(bookingDO.getCustomerId())){
			CustomerTO  customerTO=new CustomerTO();
			CGObjectConverter.createToFromDomain(bookingDO.getCustomerId(), customerTO);
			/*if(!StringUtil.isNull(bookingDO.getCustomerId().getCustomerCategoryDO())){
				customerTO.setCustomerCategory(bookingDO.getCustomerId().getCustomerCategoryDO().getRateCustomerCategoryCode());
			}*/
			if(!StringUtil.isNull(bookingDO.getCustomerId().getCustomerType())){
				CustomerTypeTO customerTypeTO=new CustomerTypeTO();
				CGObjectConverter.createToFromDomain(bookingDO.getCustomerId().getCustomerType(), customerTypeTO);
				customerTO.setCustomerTypeTO(customerTypeTO);
				if(bookingDO.getCustomerId().getCustomerType().getCustomerTypeCode().equals("WI")){
					if(!StringUtil.isNull(bookingDO.getConsignorId())){
						ConsignorConsigneeTO consignor=new ConsignorConsigneeTO();
						CGObjectConverter.createToFromDomain(bookingDO.getConsignorId(), consignor);
						bookingTO.setConsignor(consignor);
					}
					
					
				}
			}
			
			bookingTO.setCustomerTO(customerTO);
			
		}
		return bookingTO;
		
	}
	
	public String getDeliveryStatus(String consgNo)throws CGSystemException,CGBusinessException{
		String status=null;
		try{
			 status=deliveryUniversalService.getConsignmentStatusFromDelivery(consgNo);
		}catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getDeliveryStatus()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		return status;
	}
	
	public OfficeDO checkOriginOffBelongsToRHO(Integer rhoId,Integer branchOffId)throws CGSystemException,CGBusinessException{
		OfficeDO office=null;
		try{
		   office=stopDeliveryDAO.checkOriginOffBelongsToRHO(rhoId,branchOffId);
		}catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::checkOriginOffBelongsToRHO()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		return office;
	}
	
	public OfficeTO getOfficeDeatls(Integer orgOffId)throws CGSystemException,CGBusinessException{
		OfficeTO office=null;
		try{
			 office=organizationCommonService.getOfficeDetails(orgOffId);
			}catch(Exception e){
				LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getOfficeDeatls()::" 
						+ e);
				throw new CGBusinessException(e);
			}
			return office;
	}
	
	public OfficeDO checkOriginOffBelongsToHO(Integer hubOffId,Integer branchOffId)throws CGSystemException,CGBusinessException{
		OfficeDO office=null;
		try{
		   office=stopDeliveryDAO.checkOriginOffBelongsToHO(hubOffId,branchOffId);
		}catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::checkOriginOffBelongsToRHO()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		return office;
	}
	
 public List<ReasonTO> getStopDeliveryReason()throws CGSystemException,CGBusinessException{
	 List<ReasonTO> reasonList=null;
	 try{
		 ReasonTO reasonTO=new ReasonTO();
		 reasonTO.setReasonType(StopDeliveryConstants.REASON_TYPE);
		 reasonList= serviceOfferingCommonService.getReasonsByReasonType(reasonTO);
	 }catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getStopDeliveryReason()::" 
					+ e);
			throw new CGBusinessException(e);
		}
	return reasonList;
 }
 
 public boolean  updateStopDeliveryFlag(String consgNo,Integer reasonId,String remark,OfficeTO loggedInOfficeTO) throws CGSystemException,CGBusinessException{
	 boolean flag=Boolean.FALSE;
	 try{
		 UserTO userTO=udaanContextService.getUserInfoTO().getUserto();
		 if(!StringUtil.isNull(userTO)){
			 flag= stopDeliveryDAO.updateStopDeliveryFlag(consgNo,reasonId,remark,userTO,loggedInOfficeTO);
		 }
		
		 
	 }catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::updateStopDeliveryFlag()::" 
					+ e);
			throw new CGBusinessException(e);
		}
	return flag;
 }
 
 public void sendEmail(MailSenderTO emailTO)throws CGSystemException,CGBusinessException{
	 try{
     emailSenderUtil.sendEmail(emailTO);
	 }catch(Exception e){
		 LOGGER.error("Exception occurs in StopDeliveryServiceImpl::sendEmail():: error occcurred while sending email" 
					+ e);
	 }
 }
 
 public List<String> getOfficeEmailIdsByTravelPath(ConsignmentTO consignmentTO)throws CGSystemException,CGBusinessException{
	 List<String> emailList=new ArrayList<String>();
	 try{
		 
		 if(!StringUtil.isNull(consignmentTO)){
			 //ManifestDO manifestDO= stopDeliveryDAO.getOfficeEmailIdsByTravelPath(consignmentTO);
				 OfficeTO officeTO=getOfficeDeatls(consignmentTO.getOrgOffId());
				 if(!StringUtil.isNull(officeTO)){
					 if(officeTO.getOfficeTypeTO().getOffcTypeCode().equals("BO")){
					   emailList= getHubOffice(officeTO);
					   emailList.add(officeTO.getEmail());
					 }
					 else if(officeTO.getOfficeTypeTO().getOffcTypeCode().equals("HO")){
						 List<String> emailList1=getHubOffice(officeTO);
						 if(!StringUtil.isEmptyList(emailList1)){
							 emailList.addAll(emailList1);
						 }
					 }
				 }
				Integer OffceId=geographyCommonService.getOfficeIdByPincode(consignmentTO.getDestPincode().getPincodeId());
				if(!StringUtil.isEmptyInteger(OffceId)){
					 OfficeTO destOffice=getOfficeDeatls(OffceId);
					 if(!StringUtil.isNull(destOffice)){
						 if(destOffice.getOfficeTypeTO().getOffcTypeCode().equals("BO")){
							 List<String> emailList2= getHubOffice(officeTO);
							 emailList2.add(destOffice.getEmail());
							 emailList.addAll(emailList2);
						 }
						 else if(destOffice.getOfficeTypeTO().getOffcTypeCode().equals("HO")){
							 List<String> emailList3= getHubOffice(officeTO);
							 if(!StringUtil.isEmptyList(emailList3)){
							   emailList.addAll(emailList3);
							 }
						 }
					 }
				}
			 
		 }
	   }catch(Exception e){
			LOGGER.error("Exception occurs in StopDeliveryServiceImpl::getOfficeEmailIdsByTravelPath()::" 
					+ e);
			throw new CGBusinessException(e);
		}
	 
	 return emailList;
 }
 
 public List<String> getHubOffice(OfficeTO officeTO)throws CGSystemException,CGBusinessException{
	List<String> emailList=new ArrayList<String>();
 	 List<OfficeDO>  officeDOs=stopDeliveryDAO.getAllHubOff(officeTO);
	 if(!StringUtil.isNull(officeDOs)){
		 for(OfficeDO officeDO:officeDOs){
			 emailList.add(officeDO.getEmail());
		 }
	 }
	 
	 return emailList;
 }
}
