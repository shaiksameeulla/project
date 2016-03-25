/**
 * 
 */
package com.ff.admin.complaints.action;


import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.ComplaintsBacklineSummaryForm;
import com.ff.admin.complaints.form.SolveComplaintForm;
import com.ff.admin.complaints.service.SolveComplaintService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;

/**
 * @author abarudwa
 *
 */
public class SolveComplaintAction extends CGBaseAction{
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SolveComplaintAction.class);	
	
	public transient JSONSerializer serializer;
	private SolveComplaintService solveComplaintService;
	
	public ActionForward preparePage(final ActionMapping mapping, final  ActionForm form,
			final  HttpServletRequest request, final  HttpServletResponse response) {		
		LOGGER.debug("SolveComplaintAction::preparePage::START------------>:::::::");
		
		SolveComplaintForm solveComplaintForm =null;
		ServiceRequestTO summaryTO = null;
		ActionMessage actionMessage = null;
		try{			
			solveComplaintForm = (SolveComplaintForm)form;		
			summaryTO =(ServiceRequestTO)solveComplaintForm.getTo();
			
			solveComplaintService = getSolveComplaintService();
			
			((ComplaintsBacklineSummaryForm)form).setTo(summaryTO);
		}catch (Exception e) {
			LOGGER.error("SolveComplaintAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
			//actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("SolveComplaintAction::preparePage::END------------>:::::::");
		
		return mapping.findForward(ComplaintsCommonConstants.SUCCESS_FORWARD);
	}
	
	@SuppressWarnings("static-access")
	public void getComplaintDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("SolveComplaintAction::getComplaintDetails::START------------>:::::::");
		SolveComplaintForm solveComplaintForm = null;
		ServiceRequestTO summaryTO = null;
		PrintWriter out = null;
		String jsonResult = null;
		Date consgDeliveryDate = null;
		try {
			out = response.getWriter();
			solveComplaintForm = (SolveComplaintForm) form;
			summaryTO = (ServiceRequestTO) solveComplaintForm.getTo();

			String serviceRequestNo = request.getParameter("serviceRequestNo");
			solveComplaintService = getSolveComplaintService();
			ServiceRequestTO serviceRequestTO = solveComplaintService
					.getComplaintDetailsByServiceRequestNo(serviceRequestNo);

			if (!StringUtil.isNull(serviceRequestTO)) {
				/** Get Consignment Delivery Date */
				if(!StringUtil.isNull(serviceRequestTO.getBookingNo())){
					consgDeliveryDate = solveComplaintService.getConsignmentDeliveryDate(serviceRequestTO.getBookingNo().toUpperCase());
					if (!StringUtil.isNull(consgDeliveryDate)){
						serviceRequestTO.setConsgDeliveryDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(consgDeliveryDate));
					}
				}
				jsonResult = serializer.toJSON(serviceRequestTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("SolveComplaintAction :: getComplaintDetails() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("SolveComplaintAction :: getComplaintDetails() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("SolveComplaintAction :: getComplaintDetails() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SolveComplaintAction::getComplaintDetails::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void getStatusList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("SolveComplaintAction::getStatusList::START------------>:::::::");		
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		solveComplaintService = getSolveComplaintService();
		try {
			out = response.getWriter();
			List<ServiceRequestStatusTO> serviceRequestStatusTOs = solveComplaintService.getServiceRequestStatus();
			if(!CGCollectionUtils.isEmpty(serviceRequestStatusTOs)){
				
			jsonResult = serializer.toJSON(serviceRequestStatusTOs).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SolveComplaintAction :: getStatusList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SolveComplaintAction :: getStatusList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SolveComplaintAction :: getStatusList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SolveComplaintAction::getStatusList::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void getServiceRequestTransferList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("SolveComplaintAction::getServiceRequestTransferList::START------------>:::::::");		
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		solveComplaintService = getSolveComplaintService();
		try {
			out = response.getWriter();
			List<ServiceRequestTransfertoTO> serviceRequestTranferTOs = solveComplaintService.getServiceRequestTransferList();
			if(!CGCollectionUtils.isEmpty(serviceRequestTranferTOs)){
			jsonResult = serializer.toJSON(serviceRequestTranferTOs).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SolveComplaintAction :: getServiceRequestTransferList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SolveComplaintAction :: getServiceRequestTransferList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SolveComplaintAction :: getServiceRequestTransferList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SolveComplaintAction::getServiceRequestTransferList::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void saveServiceRequestDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("SolveComplaintAction::saveServiceRequestDetails::START------------>:::::::");
		ServiceRequestTO serviceRequestTO = null;
		SolveComplaintForm solveComplaintForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			solveComplaintForm = (SolveComplaintForm) form;
			serviceRequestTO = (ServiceRequestTO) solveComplaintForm.getTo();
			solveComplaintService = getSolveComplaintService();
			serviceRequestTO = solveComplaintService
					.saveServiceRequestDetails(serviceRequestTO);
			transMag = FrameworkConstants.SUCCESS_FLAG;

		} catch (CGBusinessException e) {
			LOGGER.error("SolveComplaintAction::saveServiceRequestDetails() .. :"
					+ e);
			transMag = ComplaintsCommonConstants.DTLS_NOT_SAVED_SUCCESSFULLY;
		} catch (CGSystemException e) {
			LOGGER.error("SolveComplaintAction::saveServiceRequestDetails() .. :"
					+ e);
			transMag = AdminErrorConstants.DATABASE_ISSUE;
		} catch (Exception e) {
			LOGGER.error("SolveComplaintAction::saveServiceRequestDetails() .. :"
					+ e);
			transMag = ComplaintsCommonConstants.DTLS_NOT_SAVED_SUCCESSFULLY;
		}

		finally {
			if (serviceRequestTO == null) {
				serviceRequestTO = new ServiceRequestTO();
			}
			serviceRequestTO.setTransMsg(transMag);
			jsonResult = serializer.toJSON(serviceRequestTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SolveComplaintAction::saveServiceRequestDetails::END------------>:::::::");
	}

	public SolveComplaintService getSolveComplaintService() {
		if(StringUtil.isNull(solveComplaintService)){
			solveComplaintService = (SolveComplaintService) getBean(AdminSpringConstants.SOLVE_COMPLAINT_SERVICE);
		}
		return solveComplaintService;
	}
	
	

}
