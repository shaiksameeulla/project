package com.ff.admin.stopdelivery.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stopdelivery.constants.StopDeliveryConstants;
import com.ff.admin.stopdelivery.service.StopDeliveryService;
import com.ff.consignment.ConsignmentTO;
import com.ff.organization.OfficeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.utilities.BookingStopDlvTO;
import com.ff.to.utilities.StopDeliveryTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

public class StopDeliveryAction extends CGBaseAction {
	
	private StopDeliveryService stopDeliveryService;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(StopDeliveryAction.class);
	
	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("StopDeliveryAction::preparePage::START----->");
		request.setAttribute("currDate", DateUtil.getDateInYYYYMMDDHHMM());
		LOGGER.debug("StopDeliveryAction::preparePage::END----->");
		return mapping.findForward(StopDeliveryConstants.SUCCESS);
	}
	
	public void searchStopDelivery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("StopDeliveryAction::preparePage::START----->");
		String successMessage=null;
		String errorMessage=null;
		BookingStopDlvTO bookingTO=null;
		StopDeliveryTO stopDeliveryTO=new StopDeliveryTO();
		PrintWriter out = null;
		JSONSerializer serializer=null;
		try{
			out = response.getWriter();
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			stopDeliveryService = (StopDeliveryService)getBean(AdminSpringConstants.STOP_DELIVERY_SERVICE);
			String consgNo=request.getParameter("ConsignmentNo");
			ConsignmentTO consignmentTO=stopDeliveryService.getConsignmentOriginOffice(consgNo);
			
			//add check to indentified whether consignment already stop for delivery STOP_DELIVERY (Y)
			if((StringUtil.isNull(consignmentTO.getStopDelivery()) || StringUtil.isNull(consignmentTO.getConsgStatus())) || (!consignmentTO.getStopDelivery().equals("Y") && !consignmentTO.getConsgStatus().equals("X"))){
			if(StringUtil.isNull(consignmentTO.getConsgStatus()) || (!consignmentTO.getConsgStatus().equals("R") && !consignmentTO.getConsgStatus().equals("S") && !consignmentTO.getConsgStatus().equals("D"))){

				OfficeTO consgOrgOff=stopDeliveryService.getOfficeDeatls(consignmentTO.getOrgOffId());
			 /* if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals(consgOrgOff.getOfficeTypeTO().getOffcTypeCode())){*/
				  //did changes checking equal 
				//if(loggedInOfficeTO.getOfficeId().equals(consignmentTO.getOrgOffId())){
				
				if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("CO")){
					 if(consgOrgOff.getOfficeId().equals(loggedInOfficeTO.getOfficeId())){
						 //get details ...because it having accesss
						  String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
							if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
							  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
							  //update consgstatus flag=x
							  stopDeliveryTO= setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
							  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
							  stopDeliveryTO.setReasonList(reasonList);
							  /*List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
							  request.setAttribute("reasonList", reasonList);*/
							  
							}
							
							else if(deliveryStatus.equals("O")){
								
								successMessage="Consignment out for delivery";
							}
							else if(deliveryStatus.equals("D")){
								successMessage="Consignment is delivered";
							}
					  }
					  else{
						  successMessage="This User is Not Authorized for Stop Delivery";
					  }
					
				}else if(consgOrgOff.getOfficeTypeTO().getOffcTypeCode().equals("BO")){
					  
					  if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("BO")){
						  
						  if(consgOrgOff.getOfficeId().equals(loggedInOfficeTO.getOfficeId())){
							  //get details ...because it having accesss
							  String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
								if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
								  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
								  //update consgstatus flag=x
								  stopDeliveryTO= setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
								  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
								  stopDeliveryTO.setReasonList(reasonList);
								  /*List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
								  request.setAttribute("reasonList", reasonList);*/
								  
								}
								
								else if(deliveryStatus.equals("O")){
									
									successMessage="Consignment out for delivery";
								}
								else if(deliveryStatus.equals("D")){
									successMessage="Consignment is delivered";
								}
						  }
						  else{
							  successMessage="This User is Not Authorized for Stop Delivery";
						  }
					   }else if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("RO")){
						  //OfficeDO officeDo=stopDeliveryService.checkOriginOffBelongsToRHO(loggedInOfficeTO.getOfficeId(),consgOrgOff.getOfficeId());
						  if(consgOrgOff.getReportingRHO().equals(loggedInOfficeTO.getOfficeId())){
						  /*if(StringUtil.isNull(officeDo)){
								successMessage="This User is Not Authorized for Stop Delivery";
							}
						  else{*/
							  ////get details ...because it having accesss
							  String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
								if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
								  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
								  //update consgstatus flag=x
								  stopDeliveryTO=setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
								  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
								  stopDeliveryTO.setReasonList(reasonList);
								  //request.setAttribute("reasonList", reasonList);
								}
								
								else if(deliveryStatus.equals("O")){
									
									successMessage="Consignment out for delivery";
								}
								else if(deliveryStatus.equals("D")){
									successMessage="Consignment is delivered";
								}
						  }
						  else{
							  successMessage="This User is Not Authorized for Stop Delivery";
						  }
					  }else if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("HO")){
						 /* OfficeDO officeDo=stopDeliveryService.checkOriginOffBelongsToHO(loggedInOfficeTO.getOfficeId(),consgOrgOff.getOfficeId());
						  if(StringUtil.isNull(officeDo)){
								successMessage="This User is Not Authorized for Stop Delivery";
							}
						  else{*/
						     if(consgOrgOff.getReportingHUB().equals(loggedInOfficeTO.getOfficeId())){
							  ////get details ...because it having accesss
							  String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
								if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
								  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
								  //update consgstatus flag=x
								  stopDeliveryTO= setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
								  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
								  stopDeliveryTO.setReasonList(reasonList);
								  //request.setAttribute("reasonList", reasonList);
								}
								
								else if(deliveryStatus.equals("O")){
									
									successMessage="Consignment out for delivery";
								}
								else if(deliveryStatus.equals("D")){
									successMessage="Consignment is delivered";
								}
						  }
						  else{
							  successMessage="This User is Not Authorized for Stop Delivery"; 
						  }
					  }
					  
				}else if(consgOrgOff.getOfficeTypeTO().getOffcTypeCode().equals("RO")){
					if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("RO")){
						if(loggedInOfficeTO.getOfficeId().equals(consgOrgOff.getOfficeId())){
							// ////get details ...because it having accesss
							 String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
								if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
								  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
								  //update consgstatus flag=x
								  stopDeliveryTO= setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
								  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
								  stopDeliveryTO.setReasonList(reasonList);
								  //request.setAttribute("reasonList", reasonList);
								}
								
								else if(deliveryStatus.equals("O")){
									
									successMessage="Consignment out for delivery";
								}
								else if(deliveryStatus.equals("D")){
									successMessage="Consignment is delivered";
								}
						}
						else{
							successMessage="This User is Not Authorized for Stop Delivery";
						}
						
					}else{
						successMessage="This User is Not Authorized for Stop Delivery";
					}
					
				}
				else if(consgOrgOff.getOfficeTypeTO().getOffcTypeCode().equals("HO")){
					if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("HO")){
						if(loggedInOfficeTO.getOfficeId().equals(consgOrgOff.getOfficeId())){
							// ////get details ...because it having accesss
							String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
							if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
							  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
							  //update consgstatus flag=x
							  stopDeliveryTO=setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
							  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
							  stopDeliveryTO.setReasonList(reasonList);
							 // request.setAttribute("reasonList", reasonList);
							}
							
							else if(deliveryStatus.equals("O")){
								
								successMessage="Consignment out for delivery";
							}
							else if(deliveryStatus.equals("D")){
								successMessage="Consignment is delivered";
							}
						}
						else{
							successMessage="This User is Not Authorized for Stop Delivery";
						}
					}else if(loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals("RO")){
						// OfficeDO officeDo=StopDeliveryService.checkOriginOffBelongsToRHO(loggedInOfficeTO.getOfficeId(),consgOrgOff.getOfficeId());
						if(consgOrgOff.getReportingRHO().equals(loggedInOfficeTO.getOfficeId())){
							// ////get details ...because it having accesss
							String deliveryStatus=stopDeliveryService.getDeliveryStatus(consgNo);
							if(StringUtils.isEmpty(deliveryStatus) || StringUtil.isNull(deliveryStatus) || deliveryStatus.equals("P")){
							  bookingTO =stopDeliveryService.getConsgBookingDeatils(consgNo);
							  //update consgstatus flag=x
							  stopDeliveryTO= setStopDeliveryDetails( consgNo, consignmentTO,deliveryStatus, bookingTO);
							  List<ReasonTO> reasonList=stopDeliveryService.getStopDeliveryReason();
							  stopDeliveryTO.setReasonList(reasonList);
							 // request.setAttribute("reasonList", reasonList);
							}
							
							else if(deliveryStatus.equals("O")){
								
								successMessage="Consignment out for delivery";
							}
							else if(deliveryStatus.equals("D")){
								successMessage="Consignment is delivered";
							}
						}
						else{
							successMessage="This User is Not Authorized for Stop Delivery";
						}
					}
					else{
						successMessage="This User is Not Authorized for Stop Delivery";
					}
					
				}
			
			 }
			
			else{
				
				successMessage="Consignment is Not Valid for Stop Delivery";
		  }	
		}else{
			successMessage="Consignment is Already Stop for Delivery";
		}
			
		}catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in searchStopDelivery of StopDeliveryAction.."
					+ e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in searchStopDelivery of StopDeliveryAction.."
					+ e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in searchStopDelivery of StopDeliveryAction.."
					+ e);
		}finally{
			//prepareActionMessage(request, actionMessage);
			stopDeliveryTO.setErrorMessage(errorMessage);
			stopDeliveryTO.setSuccessMessage(successMessage);
			String stopDelivery = serializer.toJSON(stopDeliveryTO)
					.toString();
			//out.write(loadMovementTOJSON);
			
			out.print(stopDelivery);
			out.flush();
			out.close();
		}
		LOGGER.debug("StopDeliveryAction::searchStopDelivery::END----->");
		//return mapping.findForward(StopDeliveryConstants.SUCCESS);
	}
	
	
	
	public void submitStopDelivery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		String errorMessage=null;
		String successMessage=null;
		JSONSerializer serializer=null;
		PrintWriter out = null;
		StopDeliveryTO stopDeliveryTO=new StopDeliveryTO();
		String body=null;
		String [] emailSendTo ={};
		try	{
			boolean flag=Boolean.FALSE;
			out = response.getWriter();
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			 //set STOP_DELIVERY flag to yes 'Y'
			stopDeliveryService = (StopDeliveryService)getBean(AdminSpringConstants.STOP_DELIVERY_SERVICE);
			String consgNo=request.getParameter("ConsignmentNo");
			String reason=request.getParameter("reasonId");
			Integer reasonId=Integer.parseInt(reason);
			String remark=request.getParameter("remark");
			flag=stopDeliveryService.updateStopDeliveryFlag(consgNo,reasonId,remark,loggedInOfficeTO);
			if(flag){
				ConsignmentTO consignmentTO=stopDeliveryService.getConsignmentOriginOffice(consgNo);
				List<String> emailList=stopDeliveryService.getOfficeEmailIdsByTravelPath(consignmentTO);
				if(!StringUtil.isEmptyList(emailList)){
					emailSendTo = emailList.toArray(new String[emailList.size()]); 
				}
				MailSenderTO emailTO=new MailSenderTO ();
				String subject="Stop Delivery Request for Consignment :"+consgNo;
				 body=" Hi, "
						+ "\n <br/><br/>Stop Delivery request has been raised for Consignment-"+consgNo +"by "+loggedInOfficeTO.getOfficeName()+"office"+"\n"+"Thanks,"+"\n"+loggedInOfficeTO.getOfficeName();
				 emailTO.setMailSubject(subject);
				 emailTO.setPlainMailBody(body);
				 emailTO.setTo(emailSendTo);
				
				stopDeliveryService.sendEmail(emailTO);
				successMessage="Stop Delivery Request Submitted Successfully";
			}
			else{
				successMessage="Stop Delivery Request Not Submitted Successfully";
			}
		
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in submitStopDelivery of StopDeliveryAction.."
					+ e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in submitStopDelivery of StopDeliveryAction.."
					+ e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in submitStopDelivery of StopDeliveryAction.."
					+ e);
		}finally{
			stopDeliveryTO.setErrorMessage(errorMessage);
			stopDeliveryTO.setSuccessMessage(successMessage);
			String stopDelivery = serializer.toJSON(stopDeliveryTO)
					.toString();
			
			out.print(stopDelivery);
			out.flush();
			out.close();
		}
		LOGGER.debug("StopDeliveryAction::submitStopDelivery::END----->");
		//return mapping.findForward(BillingConstants.SUCCESS);

	}

	public StopDeliveryTO setStopDeliveryDetails(String consgNo,ConsignmentTO consignmentTO,String deliveryStatus,BookingStopDlvTO bookingTO){
		StopDeliveryTO stopDeliveryTO=new StopDeliveryTO();
		String customrCodeName=null;
		String codLcTopayAmt=null;
		  //update consgstatus flag=x
		  if(!StringUtil.isNull(bookingTO.getBookingDate())){
			// stopDeliveryTO.setBookingDate(bookingTO.getBookingDate().toString());
			  stopDeliveryTO.setBookingDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(bookingTO.getBookingDate()));
		  }
		  if(!StringUtil.isNull(bookingTO.getConsgNumber())){
			  stopDeliveryTO.setConsgNo(bookingTO.getConsgNumber());
		  }
		  if(!StringUtil.isNull(consignmentTO.getDestPincode().getPincode())){
			  stopDeliveryTO.setPincode(consignmentTO.getDestPincode().getPincode());
		  }
		  if(!StringUtil.isEmptyDouble(consignmentTO.getFinalWeight())){
		  stopDeliveryTO.setWeight(consignmentTO.getFinalWeight().toString());
		  }
		  if(!StringUtil.isNull(bookingTO.getConsignor())){
			  customrCodeName=bookingTO.getCustomerTO().getCustomerCode()+"-" +bookingTO.getConsignor().getBusinessName();
		  }
		  else{
			  if(!StringUtil.isNull(bookingTO.getCustomerTO())){
				  if(!StringUtil.isNull(bookingTO.getCustomerTO().getCustomerTypeTO()))
				  customrCodeName=bookingTO.getCustomerTO().getCustomerCode()+"-" +bookingTO.getCustomerTO().getBusinessName();
			  }
		  }
		  
		  Character tseries=consgNo.charAt(4);
		 
		  if(tseries.equals('T') && !StringUtil.isEmptyDouble(consignmentTO.getCodAmt()) && !StringUtil.isEmptyDouble(consignmentTO.getTopayAmt())){
			  Double totalAmt=(consignmentTO.getCodAmt()+consignmentTO.getTopayAmt());
			  codLcTopayAmt=totalAmt.toString();
		  }
		else{
		  if(!StringUtil.isEmptyDouble(consignmentTO.getCodAmt())){
			  codLcTopayAmt=consignmentTO.getCodAmt().toString();
		  }
		  
		  if(!StringUtil.isEmptyDouble(consignmentTO.getLcAmount())){
			  if(codLcTopayAmt==null){
				  codLcTopayAmt=consignmentTO.getLcAmount().toString();
			  }
			  else{
				  codLcTopayAmt+="/"+consignmentTO.getLcAmount().toString();
			  }
			  
			  
		  }
		  
		  if(!StringUtil.isEmptyDouble(consignmentTO.getTopayAmt())){
			  if(codLcTopayAmt==null){
				  codLcTopayAmt=consignmentTO.getTopayAmt().toString();
			  }
			  else{
				  codLcTopayAmt+="/"+consignmentTO.getTopayAmt().toString();
			  }
		  }
		} 
		  stopDeliveryTO.setCustomer(customrCodeName);
		  stopDeliveryTO.setCodLcTopay(codLcTopayAmt);
		  stopDeliveryTO.setDate(DateUtil.getCurrentDateInDDMMYYYY());
		  return stopDeliveryTO;
		}
		
	 
}
