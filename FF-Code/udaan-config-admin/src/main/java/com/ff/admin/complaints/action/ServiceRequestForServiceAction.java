package com.ff.admin.complaints.action;

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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.constants.ComplaintsServiceRequestConstants;
import com.ff.admin.complaints.form.ServiceRequestForServiceForm;
import com.ff.admin.complaints.service.ServiceRequestForServiceReqService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.complaints.ComplaintDtlsTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestValidationTO;
import com.ff.complaints.ServiceTransferTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;

/**
 * @author sdalli
 *
 */
public class ServiceRequestForServiceAction extends AbstractComplaintsAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestForServiceAction.class);	

	private ServiceRequestForServiceReqService serviceRequestForServiceReqService;

	private ServiceRequestForServiceReqService getServiceRequestForServiceReqService() {
		if (StringUtil.isNull(serviceRequestForServiceReqService)){
			serviceRequestForServiceReqService = (ServiceRequestForServiceReqService) getBean(ComplaintsCommonConstants.SERVICE_REQUEST_FOR_SERVICE_REQ_SERVICE);
		}
		return serviceRequestForServiceReqService;
	}

	public ActionForward preparePage( ActionMapping mapping,   ActionForm form,
			HttpServletRequest request,   HttpServletResponse response) {

		LOGGER.debug("ServiceRequestForServiceAction::preparePage::START------------>:::::::");

		ServiceRequestTO serviceTO = new ServiceRequestTO();
		setGlobalValues(serviceTO);
		formInitializerForServiceRequest(request,serviceTO);
		((ServiceRequestForServiceForm) form).setTo(serviceTO);
		LOGGER.debug("ServiceRequestForServiceAction::preparePage::END------------>:::::::");
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_SERVICE);
	}
	public ActionForward preparePageForTransfer( ActionMapping mapping,   ActionForm form,
			HttpServletRequest request,   HttpServletResponse response) {

		LOGGER.debug("ServiceRequestForServiceAction::preparePageForTransfer::START------------>:::::::");

		ServiceRequestTO serviceTO = new ServiceRequestTO();
		ServiceTransferTO transferTO = (ServiceTransferTO)request.getSession(Boolean.FALSE).getAttribute(ComplaintsCommonConstants.SESSION_PARAM_TRANSFER_DTLS);
		if(transferTO!=null){
			serviceTO.setTransferTO(transferTO);
			serviceTO.setServiceRequestId(transferTO.getServiceRequestId());
			serviceTO.setServiceRequestNo(transferTO.getServiceReqNo());
		}
		formInitializerForServiceRequestTransfer(request, serviceTO);
		((ServiceRequestForServiceForm) form).setTo(serviceTO);
		LOGGER.debug("ServiceRequestForServiceAction::preparePageForTransfer::END------------>:::::::");
		return mapping.findForward(ComplaintsCommonConstants.SUCCESS_FORWARD);
	}

	private void loadPageDropDownValues(HttpServletRequest request)
	{
		// Complaints Status
		try {
			getComplaintStatusMap(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::getComplaintStatusMap::EXCEPTION------------>:::::::",e);
		} 
		try {
			getComplaintsSourceOfQueryList(request);
		}catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::getComplaintsSourceOfQueryList::EXCEPTION------------>:::::::",e);
		} 
		// Search Query list
		try {
			getServiceTypeList(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::getServiceTypeList::EXCEPTION------------>:::::::",e);
		} 
		//getServiceTypeList(request);
		// Consignment Type 
		try {
			prepareConsignmentTypeMap(request);
		}catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::prepareConsignmentTypeMap::EXCEPTION------------>:::::::",e);
		} 
		// Customer Type list
		try {
			prepareComplaintCustomerTypeMap(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::prepareComplaintCustomerTypeMap::EXCEPTION------------>:::::::",e);
		} 
		try {
			prepareComplaintTypeMap(request);
		}catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::prepareComplaintTypeMap::EXCEPTION------------>:::::::",e);
		} 
		try {
			prepareServiceQueryTypeDtlsAsMap(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::prepareServiceQueryTypeDtlsAsMap::EXCEPTION------------>:::::::",e);
		} 
		try {
			populateProductMap(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::populateProductMap::EXCEPTION------------>:::::::",e);
		} 
		try {
			populateAllCities(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::populateAllCities::EXCEPTION------------>:::::::",e);
		} 
		try {
			getIndustryCategoryForComplaints(request);
		} catch (CGSystemException |CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::loadPageDropDownValues::getIndustryCategoryForComplaints::EXCEPTION------------>:::::::",e);
		} 
	}



	private void setGlobalValues(ServiceRequestTO srvReqTo) {
		srvReqTo.setComplaintStatusBackline(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_BACKLINE);
		srvReqTo.setComplaintStatusFollowup(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_FOLLOWUP);
		srvReqTo.setComplaintStatusResolved(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_RESOLVED);

		srvReqTo.setServiceRequestConsgQueryTypeComplaint(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT);
		srvReqTo.setServiceRequestConsgQueryTypePodStatus(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_POD);
		//srvReqTo.setServiceRequestConsgQueryTypeCriticalComplaint(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT);
		//srvReqTo.setServiceRequestConsgQueryTypeEscalationComplaint(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT);
		//srvReqTo.setServiceRequestConsgQueryTypeFinancialComplaint(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT);

		srvReqTo.setServiceRequestServiceQueryTypeEmotionalBond(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_EMOTIONAL_BOND);
		srvReqTo.setServiceRequestServiceQueryTypeGeneralInfo(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_GENERAL_INFO);
		srvReqTo.setServiceRequestServiceQueryTypeLeadCall(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_LEAD_CALL);
		srvReqTo.setServiceRequestServiceQueryTypePickupCall(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_PICKUP_CALL);

		srvReqTo.setServiceRequestServiceQueryTypePaperwork(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_PAPERWORK);
		srvReqTo.setServiceRequestServiceQueryTypeServiceCheck(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_SERVICE_CHECK);
		srvReqTo.setServiceRequestServiceQueryTypeTariffEnquiry(ComplaintsServiceRequestConstants.SERVICE_REQUEST_SERVICE_QUERY_TYPE_TARIFF_ENQUIRY);
		srvReqTo.setComplaintSourceOfQueryPhone(ComplaintsServiceRequestConstants.COMPLAINT_SOURCE_OF_QUERY_PHONE);

	}

	public ActionForward saveServiceReqDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.debug("ServiceRequestForServiceAction::saveServiceReqDetails::START------------>:::::::");
		ServiceRequestTO serviceTO = null;
		ActionMessage actionMessage = null;
		ServiceRequestForServiceForm serviceForm = (ServiceRequestForServiceForm) form;
		serviceTO = (ServiceRequestTO) serviceForm.getTo();
		try {
			serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
			serviceTO.setFromEmailId(getConfigParamsValue(FrameworkConstants.CONFIG_PARAM_FOR_FROM_EMAIL_ID,request));
			serviceRequestForServiceReqService.saveOrUpdateServiceReqDtls(serviceTO);
			if(StringUtil.isEmptyInteger(serviceTO.getServiceRequestId())){
				actionMessage = new ActionMessage(
						ComplaintsCommonConstants.SAVING_COMPLAINTS_DETAILS,serviceTO.getServiceRequestNo()
						);
			}else{
				actionMessage = new ActionMessage(
						AdminErrorConstants.COMPLAINTS_UPDATE_SERVICE_REQUEST,serviceTO.getServiceRequestNo()
						);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::saveServiceReqDetails ..CGBusinessException::"+e.getLocalizedMessage());
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestForServiceAction::saveServiceReqDetails ..CGSystemException::"+e.getLocalizedMessage());

			getSystemException(request, e);

		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceAction::saveServiceReqDetails ..Generic Exception::"+e.getLocalizedMessage());
			getGenericException(request, e);
		}finally{
			setUrl(request, "./serviceRequestForService.do?submitName=preparePage");
			serviceTO= new ServiceRequestTO();
			prepareActionMessage(request, actionMessage);
			setGlobalValues(serviceTO);
			formInitializerForServiceRequest(request,serviceTO);
			((ServiceRequestForServiceForm) form).setTo(serviceTO);
		}
		LOGGER.debug("ServiceRequestForServiceAction::saveServiceReqDetails::END------------>:::::::");
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_SERVICE);
	}

	public ActionForward searchServiceReqDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.debug("ServiceRequestForServiceAction::searchServiceReqDetails::START------------>:::::::");
		ServiceRequestTO serviceTO = null;
		List<ServiceRequestTO> serviceRequeestDtls=null;
		ActionMessage actionMessage = null;
		ServiceRequestForServiceForm serviceForm = (ServiceRequestForServiceForm) form;
		serviceTO = (ServiceRequestTO) serviceForm.getTo();
		try {
			String bookingNo= request.getParameter(ComplaintsCommonConstants.COMPLAINT_SERVICE_REQ_NUMBER);
			serviceTO=new ServiceRequestTO();
			serviceTO.setBookingNo(bookingNo);
			serviceTO.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);
			serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
			serviceRequeestDtls= serviceRequestForServiceReqService.searchServiceRequestDtls(serviceTO);
			if(serviceRequeestDtls.size()>1){
				HttpSession session=request.getSession(false);
				session.removeAttribute(ComplaintsCommonConstants.SERVICE_REQUEST_TO_LIST);
				session.setAttribute(ComplaintsCommonConstants.SERVICE_REQUEST_TO_LIST, serviceRequeestDtls);
				response.sendRedirect("./backlikeSummary.do?submitName=preparePage");
			}else{
				serviceTO=serviceRequeestDtls.get(0);
			}
		} catch (CGBusinessException e) {
			serviceTO= new ServiceRequestTO();
			LOGGER.error("ServiceRequestForServiceAction::searchServiceReqDetails ..CGBusinessException::"+e.getLocalizedMessage());
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestForServiceAction::searchServiceReqDetails ..CGSystemException::"+e.getLocalizedMessage());
			serviceTO= new ServiceRequestTO();
			getSystemException(request, e);

		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceAction::searchServiceReqDetails ..Generic Exception::"+e.getLocalizedMessage());
			getGenericException(request, e);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalValues(serviceTO);
			formInitializerForServiceRequest(request,serviceTO);
			((ServiceRequestForServiceForm) form).setTo(serviceTO);
		}
		LOGGER.debug("ServiceRequestForServiceAction::searchServiceReqDetails::END------------>:::::::");
		return mapping.findForward(ComplaintsCommonConstants.URL_VIEW_SERVICE_REQUEST_FOR_SERVICE);
	}




	public void formInitializerForServiceRequest(HttpServletRequest request,
			ServiceRequestTO serviceRequestTO){
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(ComplaintsCommonConstants.USER_INFO);

		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			serviceRequestTO.setLoginOfficeId(officeTO.getOfficeId());
			serviceRequestTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if(officeTO.getRegionTO()!=null){
				serviceRequestTO.setRegionId(officeTO.getRegionTO().getRegionId());
			}
			if(officeTO.getOfficeTypeTO()!=null){
				serviceRequestTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			}

		}
		if(StringUtil.isStringEmpty(serviceRequestTO.getDate())){
			serviceRequestTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		serviceRequestTO.setBacklineExecutiveRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE,request));
		serviceRequestTO.setSalesCoordinatorRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_SALES_COORDINATOR,request));
		serviceRequestTO.setServiceRequestTypeForConsg(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG);
		serviceRequestTO.setServiceRequestTypeForService(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);
		serviceRequestTO.setServiceRequestTypeForBref(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF);
		serviceRequestTO.setComplaintSourceOfQueryPhone(ComplaintsServiceRequestConstants.COMPLAINT_SOURCE_OF_QUERY_PHONE);
		serviceRequestTO.setConsignmentTypeDox(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);
		serviceRequestTO.setCsmRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_CSM_ROLE,request));
		serviceRequestTO.setCcsmRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_CORP_CSM_ROLE,request));

		if(!StringUtil.isNull(userInfoTO.getUserto()) && !StringUtil.isEmptyInteger(userInfoTO.getUserto().getUserId())){
			serviceRequestTO.setLogginUserId(userInfoTO.getUserto().getUserId());

		}

		loadPageDropDownValues(request);
		if(serviceRequestTO.getEmplyoeeMap()!=null){
			request.setAttribute("employeeMap", serviceRequestTO.getEmplyoeeMap());
		}
		if(serviceRequestTO.getPincodeMap()!=null){
			request.setAttribute("pincodeMap", serviceRequestTO.getPincodeMap());
		}
		if(serviceRequestTO.isOriginBranchRequireToLoad()){
			request.removeAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS);
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS, getAllOfficeList(request));
		}
		if(serviceRequestTO.getTransferTO()!=null &&  !StringUtil.isStringEmpty(serviceRequestTO.getStatus())&& (isRoleExistForLoggedInUser(request,serviceRequestTO.getCsmRole())|| isRoleExistForLoggedInUser(request,serviceRequestTO.getCcsmRole()))  && (serviceRequestTO.getStatus().contains(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_BACKLINE)||serviceRequestTO.getStatus().contains(ComplaintsServiceRequestConstants.COMPLAINT_STATUS_FOLLOWUP))){
			session.removeAttribute(ComplaintsCommonConstants.SESSION_PARAM_TRANSFER_DTLS);
			session.setAttribute(ComplaintsCommonConstants.SESSION_PARAM_TRANSFER_DTLS, serviceRequestTO.getTransferTO());
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_TRANSFER_LINK, serviceRequestTO.getServiceRequestNo());
		}

	}

	public void formInitializerForServiceRequestTransfer(HttpServletRequest request,
			ServiceRequestTO serviceRequestTO){
		try {
			serviceRequestTO.setBacklineExecutiveRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE,request));
			getAllRHOListForComplaint(request);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::formInitializerForServiceRequestTransfer::ERROR=======>",e);
		}
		
	}
	public void ajaxEnquiryForServiceRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails::Start=======>");
		String cityIdStr=request.getParameter(ComplaintsCommonConstants.CITY_ID);
		String pincodeStr=request.getParameter(ComplaintsCommonConstants.PINCODE);
		String weight=request.getParameter(ComplaintsServiceRequestConstants.REQ_PARAM_WEIGHT);
		String product=request.getParameter(ComplaintsServiceRequestConstants.REQ_PARAM_PRODUCT);
		String customerType=request.getParameter(ComplaintsServiceRequestConstants.REQ_PARAM_CUSTOMER_TYPE);
		String consignmentType=request.getParameter(ComplaintsServiceRequestConstants.REQ_PARAM_CONSIGNMENT_TYPE);
		String queryType=request.getParameter(ComplaintsServiceRequestConstants.REQ_PARAM_QUERY_TYPE);
		java.io.PrintWriter out=null;
		String result="";
		ServiceRequestValidationTO validationTO=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			validationTO=new ServiceRequestValidationTO();
			if(!StringUtil.isStringEmpty(cityIdStr) && StringUtil.isStringContainsInteger(cityIdStr)){
				validationTO.setCityId(StringUtil.parseInteger(cityIdStr));
			}
			validationTO.setConsignmentType(consignmentType);
			if(!StringUtil.isStringEmpty(product) && StringUtil.isStringContainsInteger(product)){
				validationTO.setProductId(StringUtil.parseInteger(product));
			}
			validationTO.setCustomerType(customerType);
			if(!StringUtil.isStringEmpty(pincodeStr)&&StringUtil.isStringContainsInteger(pincodeStr)){
				validationTO.setPincodeId(StringUtil.parseInteger(pincodeStr));
			}
			if(!StringUtil.isStringEmpty(weight)){
				validationTO.setWeight(StringUtil.parseDouble(weight));
			}
			validationTO.setServiceQueryType(queryType);

			serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
			validationTO=serviceRequestForServiceReqService.serviceEquiryValidation(validationTO);
			if(!StringUtil.isNull(validationTO)){
				result = JSONSerializer.toJSON(validationTO)
						.toString();
			}

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetPartyTypeDetails::end=======>");
	}
	
	public void updateServiceTransferDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.debug("ServiceRequestForServiceAction::updateServiceTransferDetails::START------------>:::::::");
		ServiceRequestTO serviceTO = null;
		ServiceRequestForServiceForm serviceForm = (ServiceRequestForServiceForm) form;
		serviceTO = (ServiceRequestTO) serviceForm.getTo();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
			boolean flag=serviceRequestForServiceReqService.updateServiceTransferDetails(serviceTO);
			if(flag){
				jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG, "Successfully Transfered");
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("ServiceRequestForServiceAction::updateServiceTransferDetails ..CGBusinessException::"+e.getLocalizedMessage());
			jsonResult=getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestForServiceAction::updateServiceTransferDetails ..CGSystemException::"+e.getLocalizedMessage());
			jsonResult=getSystemExceptionMessage(request, e);

		} catch (Exception e) {
			LOGGER.error("ServiceRequestForServiceAction::updateServiceTransferDetails ..Generic Exception::"+e.getLocalizedMessage());
			jsonResult=getGenericExceptionMessage(request, e);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ServiceRequestForServiceAction::updateServiceTransferDetails::END------------>:::::::");
		
	}
	
	/**
	 * To search service request details on first tab of follow up screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void searchServiceReqDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ServiceRequestForServiceAction :: searchServiceReqDtls() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		ServiceRequestTO to = null;
		List<ServiceRequestTO> serviceRequestTOList = null;
		try {
			out = response.getWriter();

			ServiceRequestForServiceForm serviceForm = (ServiceRequestForServiceForm) form;
			to = (ServiceRequestTO) serviceForm.getTo();

			// The serviceRequestNo.
			String bookingNo = request
					.getParameter(ComplaintsCommonConstants.COMPLAINT_SERVICE_REQ_NUMBER);
			to = new ServiceRequestTO();
			to.setBookingNo(bookingNo);
			to.setServiceType(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);

			serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
			serviceRequestTOList = serviceRequestForServiceReqService
					.searchServiceRequestDtls(to);
			if (!CGCollectionUtils.isEmpty(serviceRequestTOList)) {
				to = serviceRequestTOList.get(0);
				ComplaintDtlsTO cmpltDtlsTO = convertServiceReqToCmpltDtlsTO(to);
				jsonResult = JSONSerializer.toJSON(cmpltDtlsTO).toString();
			}
		} catch (CGBusinessException e) {
			to = new ServiceRequestTO();
			LOGGER.error(
					"ServiceRequestForServiceAction :: searchServiceReqDtls() ..CGBusinessException::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ServiceRequestForServiceAction :: searchServiceReqDtls() ..CGSystemException::",
					e);
			to = new ServiceRequestTO();
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);

		} catch (Exception e) {
			LOGGER.error(
					"ServiceRequestForServiceAction :: searchServiceReqDtls() ..Generic Exception::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ServiceRequestForServiceAction :: searchServiceReqDtls() :: END");
	}

	/**
	 * To convert service request transfer object to complaints details to
	 * object. (ServiceRequestTO -> ComplaintDtlsTO)
	 * 
	 * @param to
	 * @return cmpltDtlsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ComplaintDtlsTO convertServiceReqToCmpltDtlsTO(ServiceRequestTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ServiceRequestForServiceAction :: convertServiceReqToCmpltDtlsTO() :: START");
		ComplaintDtlsTO cmptDtlsTO = new ComplaintDtlsTO();

		complaintsCommonService = getComplaintCommonService();

		// Setting complaint details drop down value(s).
		cmptDtlsTO.setServiceType(to.getServiceType());
		if (!StringUtil.isNull(to.getServiceRequestQueryTypeTO())) {
			cmptDtlsTO.setServiceRelated(to.getServiceRequestQueryTypeTO()
					.getQueryTypeDescription());
		}
		if (!StringUtil.isNull(to.getServiceRequestComplaintTypeTO())) {
			cmptDtlsTO.setComplaintCategory(to
					.getServiceRequestComplaintTypeTO()
					.getComplaintTypeDescription());
		}
		cmptDtlsTO.setCustomerType(to.getCustomerType());
		if (to.isOriginBranchRequireToLoad()) {
			// To populate origin branch
			OfficeTO officeTO = complaintsCommonService.getOfficeDetails(to
					.getOriginCityId());
			cmptDtlsTO.setOriginCityId(officeTO.getOfficeCode()
					+ CommonConstants.HYPHEN + officeTO.getOfficeName());
		} else {
			if (!StringUtil.isStringEmpty(to.getOriginLabel())) {
				cmptDtlsTO.setOriginCityId(to.getOriginLabel());
			}
		}
		if (!StringUtil.isStringEmpty(to.getProductLabel())) {
			cmptDtlsTO.setProductId(to.getProductLabel());
		}
		if (!CGCollectionUtils.isEmpty(to.getPincodeMap())) {
			cmptDtlsTO.setPincodeId((String) to.getPincodeMap().get(
					to.getPincodeId()));
		}
		if (!StringUtil.isNull(to.getTransferTO())
				&& !StringUtil.isStringEmpty(to.getTransferTO().getEmpName())) {
			cmptDtlsTO.setEmployeeId(to.getTransferTO().getEmpName());
		}
		cmptDtlsTO.setConsignmentType(to.getConsignmentType());
		cmptDtlsTO.setIndustryType(to.getIndustryType());
		cmptDtlsTO.setSourceOfQuery(to.getSourceOfQuery());
		if (!StringUtil.isNull(to.getServiceRequestStatusTO())) {
			cmptDtlsTO.setStatus(to.getServiceRequestStatusTO()
					.getStatusDescription());
		}

		// To populate the drop down values and label
		cmptDtlsTO
				.setServiceTypeList(complaintsCommonService
						.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_SEARCH));
		cmptDtlsTO
				.setIndustryTypeList(complaintsCommonService
						.getStandardTypesByTypeName(LeadCommonConstants.LEAD_INDUSTRY_CATEGORY));
		cmptDtlsTO
				.setSrcOfQryList(complaintsCommonService
						.getStandardTypesByTypeName(ComplaintsCommonConstants.STD_TYPE_SOURCE_OF_QUERY));

		// Setting complaint details text field and check box value(s).
		cmptDtlsTO.setBookingNo(to.getBookingNo());
		cmptDtlsTO.setServiceRequestNo(to.getServiceRequestNo());
		cmptDtlsTO.setLinkedServiceReqNo(to.getLinkedServiceReqNo());
		cmptDtlsTO.setIsLinkedWith(to.getIsLinkedWith());
		cmptDtlsTO.setCallerName(to.getCallerName());
		cmptDtlsTO.setCallerPhone(to.getCallerPhone());
		cmptDtlsTO.setCallerEmail(to.getCallerEmail());
		cmptDtlsTO.setWeightKgs(to.getWeightKgs());
		cmptDtlsTO.setWeightGrm(to.getWeightGrm());
		cmptDtlsTO.setEmpEmailId(to.getEmpEmailId());
		cmptDtlsTO.setEmpPhone(to.getEmpPhone());
		cmptDtlsTO.setServiceResult(to.getServiceResult());
		cmptDtlsTO.setRemark(to.getRemark());
		cmptDtlsTO.setSmsToConsignor(to.getSmsToConsignor());
		cmptDtlsTO.setSmsToConsignee(to.getSmsToConsignee());
		cmptDtlsTO.setEmailToCaller(to.getEmailToCaller());
		LOGGER.trace("ServiceRequestForServiceAction :: convertServiceReqToCmpltDtlsTO() :: START");
		return cmptDtlsTO;
	}
	
}
