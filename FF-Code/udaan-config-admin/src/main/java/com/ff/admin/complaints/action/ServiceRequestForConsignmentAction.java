

package com.ff.admin.complaints.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.ServiceRequestForConsignmentForm;
import com.ff.admin.complaints.service.ServiceRequestForConsignment;
import com.ff.complaints.ServiceRequestForConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingConsignmentTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author sdalli
 *
 */
public class ServiceRequestForConsignmentAction extends AbstractComplaintsAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestForConsignmentAction.class);	
	private ServiceRequestForConsignment serviceRequestForConsignment;
	
	private ServiceRequestForConsignment getServiceRequestForServiceReqService() {
		if (StringUtil.isNull(serviceRequestForConsignment)){
			serviceRequestForConsignment = (ServiceRequestForConsignment) getBean(ComplaintsCommonConstants.SERVICE_REQUEST_FOR_CONSIGNMENT);
		}
		return serviceRequestForConsignment;
	}
	public ActionForward preparePage(final ActionMapping mapping, final  ActionForm form,
			final  HttpServletRequest request, final  HttpServletResponse response) {
		
		LOGGER.debug("ServiceRequestForConsignmentAction::preparePage::START------------>:::::::");
		
		ServiceRequestForConsignmentForm serviceRequestForConsignmentForm =null;
		ServiceRequestForConsignmentTO serviceRequestForConsignmentTO = null;
		try{
			
			serviceRequestForConsignmentForm = (ServiceRequestForConsignmentForm)form;		
			serviceRequestForConsignmentTO =(ServiceRequestForConsignmentTO)serviceRequestForConsignmentForm.getTo();

			// Getting Serivce For the Action
			serviceRequestForConsignment=getServiceRequestForServiceReqService();
			
			// Setting Default Values
			formInitializerForServiceRequest(request,serviceRequestForConsignmentTO);
			
			// Generating Reference Number
			/*if(StringUtil.isStringEmpty(referenceNumber)){
				String generateReferenceNumber= serviceRequestForConsignment.generateReferenceNumber(serviceRequestForConsignmentTO.getLoginOfficeCode());
				serviceRequestForConsignmentTO.setReferenceNo(generateReferenceNumber);	
			}else{
				serviceRequestForConsignmentTO.setReferenceNo(referenceNumber.trim());
			}*/
			
			List<StockStandardTypeTO> complaintsStatus;
			complaintsStatus = serviceRequestForConsignment.getStatusbyType();
			request.setAttribute(ComplaintsCommonConstants.COMPLAINTS_STATUS_LIST, complaintsStatus);
			
			// Search Query list
			List<StockStandardTypeTO> searchCategoryList;
			searchCategoryList = serviceRequestForConsignment.getSearchCategoryList();
			request.setAttribute(ComplaintsCommonConstants.SEARCH_CATEGORY_LIST, searchCategoryList);
			
			// Get List of BackLine Employee Details
			List<EmployeeUserTO> backLineEmplList;
			
			if(!StringUtil.isEmptyInteger(serviceRequestForConsignmentTO.getLoginOfficeId())){
				backLineEmplList= serviceRequestForConsignment.getBackLineEmpList(serviceRequestForConsignmentTO.getLoginOfficeId(), ComplaintsCommonConstants.DESIGNATION_CODE);
				request.setAttribute(ComplaintsCommonConstants.BACKlINE_EMP_LIST, backLineEmplList);
			}
			
			((ServiceRequestForConsignmentForm)form).setTo(serviceRequestForConsignmentTO);
		}catch(Exception e){
			LOGGER.error("Error occured in ServiceRequestForConsignmentAction :: preparePage() ::",e);
		}
		LOGGER.debug("ServiceRequestForConsignmentAction::preparePage::END------------>:::::::");
		// return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_CONSIGNMENT);
		return mapping.findForward("serviceTrackingConsig");
		}
	
	public ActionForward savePlanServiceConsigDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.debug("ServiceRequestForConsignmentAction::savePlanServiceConsigDetails::START------------>:::::::");
		ServiceRequestForConsignmentTO serviceTO = null;
		ServiceRequestForConsignmentForm serviceForm = null;
		ActionMessage actionMessage = null;
		ActionForward actionForward = null;
		serviceForm = (ServiceRequestForConsignmentForm) form;
		serviceTO = (ServiceRequestForConsignmentTO) serviceForm.getTo();
		try {
			serviceRequestForConsignment=getServiceRequestForServiceReqService();
			serviceRequestForConsignment.saveOrUpdateServiceConsigDtls(serviceTO);
			actionMessage = new ActionMessage(
					ComplaintsCommonConstants.SAVING_COMPLAINTS_DETAILS,serviceTO.getReferenceNo()
					);
			
		} catch (Exception e) {
			LOGGER.error("Exception happened in savePlanServiceConsigDetails of ServiceRequestForConsignmentAction..."
					+ e.getMessage());
			actionMessage = new ActionMessage(ComplaintsCommonConstants.ERROR_IN_SAVING_COMPLAINTS_DETAILS);

		}finally{
			prepareActionMessage(request, actionMessage);
			actionForward = preparePage(mapping, form, request, response);
		}
		LOGGER.debug("ServiceRequestForConsignmentAction::savePlanServiceConsigDetails::END------------>:::::::");
		//return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_SERVICE);
		return actionForward;
	}
	public ActionForward sendEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRequestForConsignmentForm serviceRequestForConsignmentForm =null;
		ServiceRequestForConsignmentTO serviceRequestForConsignmentTO = null;
		try {
			serviceRequestForConsignmentForm = (ServiceRequestForConsignmentForm)form;		
			serviceRequestForConsignmentTO =(ServiceRequestForConsignmentTO)serviceRequestForConsignmentForm.getTo();
			serviceRequestForConsignment=getServiceRequestForServiceReqService();
			if(!StringUtil.isNull(serviceRequestForConsignmentTO.getCallerEmail())){
			serviceRequestForConsignment.sendEmailByPlainText(serviceRequestForConsignmentTO.getCallerEmail());
			}
		} catch (Exception e) {
			LOGGER.error("ServiceRequestForConsignmentAction :: sendEmail() ::" , e);
		}
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_CONSIGNMENT); 
	}
	
	public ActionForward sendSMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ConsignmentTO consignmentTO =  new ConsignmentTO();
			serviceRequestForConsignment=getServiceRequestForServiceReqService();
			String cosnsigNo = request.getParameter("cosnsigNo");
			String consignor = request.getParameter("consignor");
			String consignee = request.getParameter("consignee");
			//To get the consignment details
			if(!StringUtil.isNull(cosnsigNo) ){
				consignmentTO = serviceRequestForConsignment.getConsignmentDtls(cosnsigNo);
			}
			if(!StringUtil.isNull(consignor) && !StringUtil.isNull(consignmentTO.getConsignorTO().getMobile()))
			{
				//serviceRequestForConsignment.sendSMS(serviceRequestForConsignmentTO.getCallerPhone(), response);
				serviceRequestForConsignment.sendSMS(consignmentTO.getConsignorTO().getMobile(), response);
			}
			if(!StringUtil.isNull(consignee) && !StringUtil.isNull(consignmentTO.getConsigneeTO().getMobile()))
			{
				serviceRequestForConsignment.sendSMS(consignmentTO.getConsigneeTO().getMobile(), response);
			}		
			
		} catch (Exception e) {
			LOGGER.error("ServiceRequestForConsignmentAction :: sendSMS() ::" , e);
		}
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_CONSIGNMENT); 
	}
	//getTrackingDtls 
	
	public ActionForward getTrackingDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ServiceRequestForConsignmentAction::getTrackingDtls::START------------>:::::::");
		try{
			
		} catch (Exception e) {
			LOGGER.error("Exception happened in getTrackingDtls of ServiceRequestForConsignmentAction..."
					+ e.getMessage());
			//actionMessage = new ActionMessage(ComplaintsCommonConstants.ERROR_IN_SAVING_COMPLAINTS_DETAILS);

		}finally{
			//prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ServiceRequestForConsignmentAction::getTrackingDtls::END ------------>:::::::");
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_FOR_TRACKING_POPUP);
	}
	
	public void viewTrackInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		
		PrintWriter out = null;
		String manifestTOJSON = null;
		LOGGER.info("ConsignmentTrackingAction::viewTrackInformation::START----->");
		TrackingConsignmentTO trackingTO = null;
		try {
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			String loginUserType = userInfoTO.getUserto().getUserType();
			out = response.getWriter();
			String type = request.getParameter("type");
			String number = request.getParameter("number");
			//consignmentTrackingService = (ConsignmentTrackingService) getBean(AdminSpringConstants.CONSIGNMENT_TRACKING_SERVICE);
			serviceRequestForConsignment=getServiceRequestForServiceReqService();
			if(type.equalsIgnoreCase("CN")){
				String consgNum = number;
				trackingTO = serviceRequestForConsignment.viewTrackInformation(consgNum,null, loginUserType);
			}
			else if(type.equalsIgnoreCase("RN")){
				String refNum = number;
				trackingTO = serviceRequestForConsignment.viewTrackInformation(null,refNum, loginUserType);
				
			}
		} catch (CGSystemException | CGBusinessException e) {
			LOGGER.error("ServiceRequestForConsignmentAction ::viewTrackInformation::Exception (CGSystem/business Exception)",e);
		}
		catch (IOException e) {
			LOGGER.error("ServiceRequestForConsignmentAction ::viewTrackInformation::Exception (IO Exception)",e);
		}
		finally {
			manifestTOJSON = JSONSerializer.toJSON(trackingTO)
					.toString();
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		
	}
	
}// Class End
