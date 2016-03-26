package com.ff.notification.service;

import java.io.StringWriter;
import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.sms.SmsSenderService;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.notification.NotificationConstant;
import com.ff.notification.dao.TrackingNotificationDAO;

public class TrackingNotificationServiceImpl implements TrackingNotificationService{
	
	private final static Logger loggger = LoggerFactory
			.getLogger(TrackingNotificationServiceImpl.class);
	
	private EmailSenderUtil emailSenderUtil;
	/** Dependency: Data access object **/
	private TrackingNotificationDAO trackingnotificationDao;

	/** Dependency: The velocity engine. */
	private VelocityEngine velocityEngine;

	// smsSenderServic
	SmsSenderService smsSenderServic;
	
	
	
	
	@Override
	public boolean sendSMSNotification(String cnNumber, String mobile) throws CGBusinessException, CGSystemException {
		boolean track=false;
		if(cnNumber == null || cnNumber.isEmpty() || mobile == null || mobile.isEmpty())
			throw new CGBusinessException("consignment and mobile number cannot be null");
		
		String smsText = null;
		try {
			
			smsText=getSmsNotificationStatusText(cnNumber);
			smsSenderServic.prepareAndSendSms(getSenderTO(smsText, mobile));
			track=true;
		} catch (Exception e) {
			track=false;
			e.printStackTrace();
		}
		return track;
		
	}
	@Override
	public void sendSMSNotification(SmsSenderTO smsSenderTO)  throws CGBusinessException, CGSystemException {
		if(!StringUtil.isNull(smsSenderTO)){
			String smsText = "";
			try {
				smsText = getSmsNotificationStatusText(smsSenderTO.getCnNumber());
				smsSenderTO.setMessage(smsText);
				smsSenderServic.prepareAndSendSms(smsSenderTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	private SmsSenderTO getSenderTO(String smsText, String mobile) {
		SmsSenderTO to = new SmsSenderTO();
		to.setMessage(smsText);
		to.setContactNumber(mobile);
		to.setImmediateSend(true);
		return to;
	}
	/**
	 * Sending Sms Current Status of details  
	 * @param cnNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String getSmsNotificationStatusText(String cnNumber)throws Exception {
		loggger.debug("ConsignmentNotificationServiceImpl::getSmsNotificationStatusText::Stated ");
		String smsText = null;
		DeliveryDetailsDO deliveryDtl=null;
		/**
		 * Finding Booking Details 
		 */
		BookingDO booking = trackingnotificationDao.getBookingDetailsByCnNumber(cnNumber);
		if(booking !=null){
		int bookingOffice =booking.getBookingOfficeId();
		Double price=booking.getPrice();
		PincodeDO pincodeId=booking.getPincodeId();
		OfficeDO reportingOffice=trackingnotificationDao.getReportingOfficeFindByofficeId(bookingOffice);
		OfficeDO destOffice=trackingnotificationDao.getDestinationOfficeByPincodeId(pincodeId.getPincodeId());
		ConsignmentDO consgDetails = trackingnotificationDao.getConsgDetailsByConNumber(cnNumber);
		
		if(consgDetails !=null ){
			/*
			 * finding delivery details
			 */
			
			if(consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_DELIVERY) 
			           || consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_RTO_DELIVERY)){
					 deliveryDtl = getDeliveryDetailsByCn(cnNumber);
					 
					 if(deliveryDtl == null) {
						 smsText = getTransitStatusTextByTemplate();
					 } else {
						 smsText = getDeliveryStatusSMSText(deliveryDtl, cnNumber,null);
					 }
			}
			 
		
		if(deliveryDtl ==null && consgDetails !=null){
			try {
				ConsignmentManifestDO manifestDetails=null;
				DeliveryDetailsDO   deliveryDetails=null;
				DeliveryDetailsDO   outDeliveryDetails=null;
				String reasonName=null;
				try{
					/**
					 * Finding manifest Details
					 */
					manifestDetails= trackingnotificationDao.getManifestDetailsByCon(cnNumber);
				}catch(Exception e){}
	
				 try{
					 outDeliveryDetails=trackingnotificationDao.getDeliveryDetailsByCon(cnNumber);
				 }catch(Exception e){}
				
				 if(outDeliveryDetails !=null && 
						 outDeliveryDetails.getDeliveryStatus().equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_PENDING)){
					 try{
						 deliveryDetails=trackingnotificationDao.getPendingDetailsByCon(cnNumber);
						 reasonName=deliveryDetails.getReasonDO().getReasonName();
						
					}catch(Exception e){}
				 }
				 
				 
				 /**
					 * Pending Details 
					 */
				if(deliveryDetails !=null && consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.BOOKING_STATUS) ||
						consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.MISROUT_STATUS)){
					smsText=getDeliveryStatusSMSText(deliveryDetails,cnNumber,price);
					
				}else if(deliveryDetails !=null && consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.RTO_STATUS)
						&& ! manifestDetails.getManifest().getManifestProcessCode().equalsIgnoreCase(NotificationConstant.MANIFEST_PROCESS_CODE_FOR_RTOH)){
					smsText=getDeliveryStatusSMSText(deliveryDetails,cnNumber,price);
					
				  }
				 
				/**
				 * Out For Delivery Message
				 */
				else if(outDeliveryDetails !=null && 
						outDeliveryDetails.getDeliveryStatus().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_OUT)){
					smsText=getDeliveryStatusSMSText(outDeliveryDetails,cnNumber,price);
				}
				
				/**
				 * RTH Details
				 */
				else if(consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.MISROUT_STATUS) && 
						manifestDetails.getManifest().getManifestProcessCode().equalsIgnoreCase(NotificationConstant.MANIFEST_PROCESS_CODE_FOR_RTOH) )
				  {
					smsText=getRTHStatusSMSText(manifestDetails,cnNumber,reasonName,outDeliveryDetails);
				  }
				/**
				 * RTO Details(Return To Origin Not Delivered)
				 */
				else if(consgDetails.getConsgStatus().equalsIgnoreCase(NotificationConstant.RTO_STATUS) && 
						manifestDetails.getManifest().getManifestProcessCode().equalsIgnoreCase(NotificationConstant.MANIFEST_PROCESS_CODE_FOR_RTOH) 
						&& manifestDetails.getManifest().getManifestType().equalsIgnoreCase(NotificationConstant.RTO_STATUS)){
					smsText=getManifestStatusSMSText(manifestDetails,cnNumber,reasonName,outDeliveryDetails);
					/**
					 * Destination Branch In Message
					 */
				}else if(manifestDetails !=null
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_IN) 
						&& destOffice.getOfficeId().equals(manifestDetails.getManifest().getOperatingOffice())){
					smsText=getDestBranchInStatusSMSText(manifestDetails,cnNumber);
				}
	              /**
	               * Destination Hub out Message
	               */
				else if(manifestDetails !=null){
				 if(manifestDetails.getOriginOfficeId() !=null  
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_OUT) &&
						manifestDetails.getOriginOfficeId().equals(reportingOffice.getOfficeId())){
					smsText=getDestHubOutStatusSMSText(manifestDetails,cnNumber);
				
	             /**
	              * Destination HUb In Message
	              */
				}else if(manifestDetails !=null
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_IN) &&
						manifestDetails.getManifest().getOriginOffice().getOfficeId().equals(reportingOffice.getOfficeId())){
					smsText=getDestHubInStatusSMSText(manifestDetails,cnNumber);
	
				}
				}
				
				/**
				 * Origin Hub In Message
				 */
				else if(manifestDetails !=null
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_IN) &&
						reportingOffice.getReportingHUB().equals(manifestDetails.getManifest().getOperatingOffice())){
					smsText=getOriginHubStatusSMSText(manifestDetails,cnNumber);
	
					/**
					 * Origin Hub Out Message
					 */
				}else if(manifestDetails !=null
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_OUT) &&
						reportingOffice.getReportingHUB().equals(manifestDetails.getManifest().getOperatingOffice())){
					smsText=getOriginHubStatusSMSText(manifestDetails,cnNumber);
	             /**
	              * Origin branch out Message
	              */
				}else if(manifestDetails !=null
						&& manifestDetails.getManifest().getManifestDirection().equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_OUT) &&
						booking.getBookingOfficeId().equals(manifestDetails.getManifest().getOperatingOffice())){
					smsText=getOriginBranchStatusSMSText(manifestDetails,cnNumber);
	
				}else if(manifestDetails ==null && consgDetails !=null && booking !=null && outDeliveryDetails==null){
					smsText=getOriginBookingStatusSMSText(booking);
	
				}
			    
				
				}catch(Exception e){
				    smsText = getTransitStatusTextByTemplate();
				    e.printStackTrace();
				}
		 }
				}else if(booking !=null){
						
					smsText=getOriginBookingStatusSMSText(booking);
	
				}
		
		
		}else {
			smsText = getTransitStatusTextByTemplate();
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getSmsNotificationStatusText::End");
	
		return smsText;
	}
	

/**
 * Delivery,Pending,Out For Delivery Text Message Created 
 * @param deliveryDtl
 * @param cnNo
 * @param price
 * @return
 */
	private String getDeliveryStatusSMSText( DeliveryDetailsDO deliveryDtl, String cnNo, Double price) {
		loggger.debug("ConsignmentNotificationServiceImpl::getDeliveryStatusSMSText::Stated ");
		String status = deliveryDtl.getDeliveryStatus();
		String smsText = null;
		String receiverName=null;
		try {

			if (status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_DELIVERY) ||
					status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_RTO_DELIVERY)) {
				String receiver = deliveryDtl.getReceiverName();
				String comapny = deliveryDtl.getCompanySealSign();
				if("CSS".equalsIgnoreCase(comapny)){
					receiverName= "Company Seal & Sign";
				}else if(receiver !=null && comapny ==null){
					receiverName=receiver;
				}else{
					receiverName="";
				}
				
				Date delDate = deliveryDtl.getDeliveryDate();
				String dateStr = DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(delDate);
				smsText = getDeliveredSMSTextByTemplate(receiverName,
						dateStr, cnNo);
				loggger.debug("ConsignmentNotificationServiceImpl::getDeliveryStatusSMSText::deliText====>"
						+ smsText);
			} else if (status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_STATUS_PENDING)) {
				String reason = deliveryDtl.getReasonDO() == null ? ""
						: deliveryDtl.getReasonDO().getReasonName();
				OfficeDO branchOffice = deliveryDtl.getDeliveryDO()
						.getCreatedOfficeDO();
				// FIXME: replace empty string in below line with corporate
				// office contact number
				String branchContact = branchOffice == null ? ""
						: branchOffice.getPhone() == null ? ""
								: branchOffice.getPhone();
				smsText = getPendingSMSTextByTemplate(cnNo, reason,
						branchContact);
				loggger.debug("ConsignmentNotificationServiceImpl::getDeliveryStatusSMSText::Pending====>"
						+ smsText);

			} else if(status
					.equalsIgnoreCase(NotificationConstant.CONSIGNMENT_OUT_FOR_DELIVERY)){
				Date delDate = deliveryDtl.getTransactionCreateDate();
				String dateStr = DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(delDate);
				
				String amount = null;
				if(price==null){
					amount="0.0";
				}else{
					amount=String.valueOf(price);
				}
				
				
				smsText=getOutForDeliverySMSTextByTemplate(cnNo,dateStr,amount);
				loggger.debug("ConsignmentNotificationServiceImpl::getDeliveryStatusSMSText::OutForDeliveryTex====>"
						+ smsText);
				
			}
		
		} catch (Exception e) {
			loggger.error(
					"ConsignmentNotificationServiceImpl::getSMSText::ERROR====>",
					e);
			e.printStackTrace();
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getDeliveryStatusSMSText::End ");
		return smsText;
	}
	
	
	/**
	 * RTHText Message Created
	 * @param manifestDetails
	 * @param cnNumber
	 * @param reasonName
	 * @return
	 */
	private String getRTHStatusSMSText(ConsignmentManifestDO manifestDetails,String cnNumber,String reasonName,DeliveryDetailsDO outDeliveryDetails ) {
		loggger.debug("ConsignmentNotificationServiceImpl::getRTOStatusSMSText::starts====>");
	//	String manifestType=manifestDetails.getManifest().getManifestType();
	//	String hubName=manifestDetails.get
	//	String originOffice=outDeliveryDetails.getOriginCityDO().getCityName();
		String smsText = null;
		try{
			String manifestType=manifestDetails.getManifest().getManifestType();
			//	String hubName=manifestDetails.get
				String originOffice=outDeliveryDetails.getOriginCityDO().getCityName();
			
			if(manifestType.equalsIgnoreCase(NotificationConstant.RTO_STATUS)){
				smsText=getRtoSMSTextByTemplate(reasonName,cnNumber,originOffice);
				loggger.debug("ConsignmentNotificationServiceImpl::getManifestStatusSMSText::RTOText====>"
						+ smsText);
				
			}

		}catch(Exception e){
			loggger.error(
					"ConsignmentNotificationServiceImpl::getRTOStatusSMSText::ERROR====>",
					e);
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getRTOStatusSMSText::End====>");
		return smsText;
	}
	
	
	/**
	 * RTOText Message Created
	 * @param manifestDetails
	 * @param cnNumber
	 * @param reasonName
	 * @return
	 */
	private String getManifestStatusSMSText(ConsignmentManifestDO manifestDetails,String cnNumber,String reasonName,DeliveryDetailsDO outDeliveryDetails ) {
		loggger.debug("ConsignmentNotificationServiceImpl::getRTOStatusSMSText::starts====>");
		//String manifestType=manifestDetails.getManifest().getManifestType();
		//String originOffice=outDeliveryDetails.getOriginCityDO().getCityName();
		String smsText = null;
		try{
			String manifestType=manifestDetails.getManifest().getManifestType();
			String originOffice=outDeliveryDetails.getOriginCityDO().getCityName();
			
			if(manifestType.equalsIgnoreCase(NotificationConstant.RTO_STATUS)){
				smsText=getRtoSMSTextByTemplate(reasonName,cnNumber,originOffice);
				loggger.debug("ConsignmentNotificationServiceImpl::getManifestStatusSMSText::RTOText====>"
						+ smsText);
				
			}

		}catch(Exception e){
			loggger.error(
					"ConsignmentNotificationServiceImpl::getRTOStatusSMSText::ERROR====>",
					e);
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getRTOStatusSMSText::End====>");
		return smsText;
	}
	
	private String getDestBranchInStatusSMSText(ConsignmentManifestDO manifestDetails, String cnNumber) {
		loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::starts====>");
		String smsText = null;
		try{
			
			smsText=getDestBranchInMSTextByTemplate();
			loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::DestBranchInText====>"
					+ smsText);
		}catch(Exception e){loggger.error(
				"ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::ERROR====>",
				e);
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::End====>");
		return smsText;
	}
	private String getDestHubOutStatusSMSText(ConsignmentManifestDO manifestDetails, String cnNumber) {
		loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::starts====>");
		String smsText = null;
		try{
			Integer officeId=manifestDetails.getDestOfficeId();
			
			 OfficeDO destOffice=trackingnotificationDao.getReportingOfficeFindByofficeId(officeId);
			 String officeName=destOffice.getOfficeName();
			 
			smsText=getDestHubOutMSTextByTemplate(officeName,cnNumber);
			loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::DestHubOutText====>"
					+ smsText);
		}catch(Exception e){loggger.error(
				"ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::ERROR====>",
				e);
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::End====>");
		return smsText;
	}
	
	private String getDestHubInStatusSMSText(ConsignmentManifestDO manifestDetails, String cnNumber) {
		loggger.debug("ConsignmentNotificationServiceImpl::getDestHubInStatusSMSText::starts====>");
		String smsText = null;
		try{
			Integer officeId=manifestDetails.getManifest().getOperatingOffice();
			
			 OfficeDO officeName=trackingnotificationDao.getReportingOfficeFindByofficeId(officeId);
			 String HubName=officeName.getOfficeName();
			
			smsText=getDestHubInSMSTextByTemplate(HubName,cnNumber);
			loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::DestHubInText====>"
					+ smsText);
		}catch(Exception e){loggger.error(
				"ConsignmentNotificationServiceImpl::getDestHubInStatusSMSText::ERROR====>",
				e);
		}
		loggger.debug("ConsignmentNotificationServiceImpl::getDestHubInStatusSMSText::End====>");
		return smsText;
	}
     private String getOriginHubStatusSMSText(ConsignmentManifestDO manifestDetails, String cnNumber) {
    	 loggger.debug("ConsignmentNotificationServiceImpl::getOriginHubStatusSMSText::starts====>");
 		String smsText = null;
 		/*String manifestType=manifestDetails.getManifest().getManifestDirection();
 		Integer officeId=manifestDetails.getManifest().getOperatingOffice();
 		CityDO citydo=manifestDetails.getManifest().getDestinationCity();
		
		 OfficeDO officeName=trackingnotificationDao.getReportingOfficeFindByofficeId(officeId);
		 String originHub=officeName.getOfficeName();
		String cityName=citydo.getCityName();*/
		 
 		try{
 			String manifestType=manifestDetails.getManifest().getManifestDirection();
 	 		Integer officeId=manifestDetails.getManifest().getOperatingOffice();
 	 		CityDO citydo=manifestDetails.getManifest().getDestinationCity();
 			
 			 OfficeDO officeName=trackingnotificationDao.getReportingOfficeFindByofficeId(officeId);
 			 String originHub=officeName.getOfficeName();
 			String cityName=citydo.getCityName();
 			
 			if(manifestType.equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_IN)){
 				
 				smsText=getOriginHubInSMSTextByTemplate(originHub,cnNumber);
 				
 				loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::OriginHubInText====>"
 						+ smsText);
 			}else if(manifestType.equalsIgnoreCase(NotificationConstant.MANIFEST_DIRECTION_OUT)){
 				
 				smsText=getOriginHubOutSMSTextByTemplate(cityName,cnNumber);
 				
 				loggger.debug("ConsignmentNotificationServiceImpl::getDestBranchInStatusSMSText::OriginHubOutText====>"
 						+ smsText);
 			}
 			
 			
 			
 		}catch(Exception e){loggger.error(
 				"ConsignmentNotificationServiceImpl::getOriginHubStatusSMSText::ERROR====>",
 				e);
 		}
 		loggger.debug("ConsignmentNotificationServiceImpl::getOriginHubStatusSMSText::End====>");
 		return smsText;
	}
     
     private String getOriginBranchStatusSMSText(ConsignmentManifestDO manifestDetails, String cnNumber) {
    	 loggger.debug("ConsignmentNotificationServiceImpl::getOriginBranchStatusSMSText::starts====>");
    	 String smsText = null;
    	 try {
    		 Integer officeId=manifestDetails.getOriginOfficeId();
    		 OfficeDO officeName=trackingnotificationDao.getReportingOfficeFindByofficeId(officeId);
    		 String bkOffice=officeName.getOfficeName();
    		 
			smsText=getOriginOutInSMSTextByTemplate(bkOffice,cnNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
    	 loggger.debug("ConsignmentNotificationServiceImpl::getOriginBranchStatusSMSText::End====>");
 		return smsText;
 	}
     private String getOriginBookingStatusSMSText(BookingDO booking) {
    	 loggger.debug("ConsignmentNotificationServiceImpl::getOriginBookingStatusSMSText::starts====>");
    	 
    	 String smsText = null;
    	 try {
    		 String consg=booking.getConsgNumber();
        	 Date bkDate=booking.getBookingDate();
        	 String date = DateUtil.getDateInDDMMYYYYHHMMSlashFormat(bkDate);
    		 
			smsText=getOriginBranchSMSTextByTemplate(consg,date);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	 loggger.debug("ConsignmentNotificationServiceImpl::getOriginBookingStatusSMSText::End====>");
 		return smsText;
 	}

     
	
	private String getOriginBranchSMSTextByTemplate(String consg,String bkDate)throws Exception {
		Template delTemp = velocityEngine.getTemplate("BookingDetailsSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", consg);
		context.put("bkDate", bkDate);
		//context.put("price", price);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	private String getOriginOutInSMSTextByTemplate(String bkOffice, String cnNumber)throws Exception {
		Template delTemp = velocityEngine.getTemplate("OriginBranchOutSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNumber);
		context.put("bkOffice", bkOffice);
	
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	private String getOriginHubOutSMSTextByTemplate(String destCity,String cnNumber)throws Exception {
		Template delTemp = velocityEngine.getTemplate("OriginHubOut.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNumber);
		context.put("destCity", destCity);
		
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	private String getOriginHubInSMSTextByTemplate(String originHub,String cnNumber)throws Exception {
		Template delTemp = velocityEngine.getTemplate("OriginHubInSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNumber);
		context.put("originHub", originHub);
	
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	private String getDestHubInSMSTextByTemplate(String HubName, String cnNo) throws Exception{
		Template delTemp = velocityEngine.getTemplate("DestHubInSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNo);
		context.put("HubName", HubName);
		
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	private String getDestHubOutMSTextByTemplate(String destOffice,String cnNumber) throws Exception{
		Template delTemp = velocityEngine.getTemplate("DestHubOutSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNumber);
		context.put("destOffice", destOffice);
	
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
		
	}

	private String getDestBranchInMSTextByTemplate()throws Exception {
		Template delTemp = velocityEngine.getTemplate("DestBranchInSMS.vm");
		VelocityContext context = new VelocityContext();
		/*context.put("consgNo", cnNo);
		context.put("date", dateStr);
		context.put("price", price);*/
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	public String getOutForDeliverySMSTextByTemplate(String cnNo,String  dateStr,String price) throws Exception{
		Template delTemp = velocityEngine.getTemplate("OutForDelivery.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNo);
		context.put("date", dateStr);
		context.put("amount", price);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
		
	}
	
	public String getRtoSMSTextByTemplate(String reasonName,String  cnNumber,String originOffice) throws Exception{
		Template delTemp = velocityEngine.getTemplate("RtoSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", cnNumber);
		context.put("pendigReason", reasonName);
		context.put("originOffice", originOffice);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
		
	}

	@Override
	public String getPendingSMSTextByTemplate(String consgNo,
			String pendigReason, String delBrContactNo) throws Exception {
		Template delTemp = velocityEngine.getTemplate("PendingSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consgNo", consgNo);
		context.put("pendigReason", pendigReason);
		context.put("delBrContactNo", delBrContactNo);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

	@Override
	public String getDeliveredSMSTextByTemplate(String receiverName,
			String deliveryDate, String consg) throws Exception {
		Template delTemp = velocityEngine.getTemplate("DeliveredSMS.vm");
		VelocityContext context = new VelocityContext();
		context.put("consg", consg);
		context.put("receiverName", receiverName);
		context.put("deliveryDate", deliveryDate);
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}

//TransitStatuMessage.vm
	@Override
	public String getTransitStatusTextByTemplate() throws Exception {
		Template delTemp = velocityEngine.getTemplate("TransitStatuMessage.vm");
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		delTemp.merge(context, writer);
		return writer.toString();
	}
	
	
	
	@Override
	public boolean sendEmailNotification(String cnNumber, String email) throws CGBusinessException, CGSystemException  {
		boolean track=false;
		if(cnNumber == null || cnNumber.isEmpty() || email == null || email.isEmpty())
			throw new CGBusinessException("consignment and mobile number cannot be null");
		
		String smsText = null;
		/*try{
	        	smsText=getEmailSendingNotification(cnNumber);
		}catch(Exception e){}*/
		
		 DeliveryDetailsDO deliveryDtl = getDeliveryDetailsByCn(cnNumber);
		 if(deliveryDtl == null) {
			 smsText = "   Dear Customer,\nYour consignment(s) is in transit please try again later for additional details";
		 } else {
			 smsText = getDeliveryStatusSMSText(deliveryDtl, cnNumber,null);
			 try {
				 
				smsSenderServic.prepareAndSendSms(getSenderTOEmail(smsText, email));
				track=true;
			} catch (CGSystemException e) {
				track=false;
				//throw new CGBusinessException("Messaage");
				e.printStackTrace();
			}
		 }
		return track;
	}
	/**
	 * Email Sending Notification 
	 * @param cnNumber
	 * @return
	 * @throws Exception
	 */
	private String getEmailSendingNotification(String cnNumber)throws Exception {
		String smsText = null;
		
		
		
		return smsText;
	}
	
	private SmsSenderTO getSenderTOEmail(String smsText, String mobile) {
		SmsSenderTO to = new SmsSenderTO();
		to.setMessage(smsText);
		to.setContactNumber(mobile);
		//to.setImmediateSend(true);
		System.out.println("Return values ==========>"+to.toString());
		return to;
	}
	

	@Override
	public DeliveryDetailsDO getDeliveryDetailsByCn(String cn) {
		return trackingnotificationDao.getDeliveryDetailsByCn(cn);
	}

	public EmailSenderUtil getEmailSenderUtil() {
		return emailSenderUtil;
	}

	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	public TrackingNotificationDAO getTrackingnotificationDao() {
		return trackingnotificationDao;
	}

	public void setTrackingnotificationDao(
			TrackingNotificationDAO trackingnotificationDao) {
		this.trackingnotificationDao = trackingnotificationDao;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public SmsSenderService getSmsSenderServic() {
		return smsSenderServic;
	}

	public void setSmsSenderServic(SmsSenderService smsSenderServic) {
		this.smsSenderServic = smsSenderServic;
	}
	
	

}
