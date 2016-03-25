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

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.ComplaintsBacklineSummaryForm;
import com.ff.admin.complaints.service.ComplaintsBacklineSummaryService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

public class ComplaintsBacklineSummaryAction extends AbstractComplaintsAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComplaintsBacklineSummaryAction.class);
	private ComplaintsBacklineSummaryService complaintsBacklineSummaryService;
	public transient JSONSerializer serializer;
	Integer employeeId = null;

	public ActionForward preparePage(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("ComplaintsBacklineSummaryAction::preparePage::START------------>:::::::");

		ComplaintsBacklineSummaryForm summaryForm = null;
		ServiceRequestTO summaryTO = null;
		ActionMessage actionMessage = null;
		try {
			summaryForm = (ComplaintsBacklineSummaryForm) form;
			summaryTO = (ServiceRequestTO) summaryForm.getTo();
			complaintsBacklineSummaryService = getComplaintsBacklineSummaryService();
			// login user id
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			/*
			 * if(!StringUtil.isNull(userInfoTO) &&
			 * !StringUtil.isNull(userInfoTO.getUserto())){
			 * summaryTO.setUserTO(userInfoTO.getUserto()); }
			 */

			List<ServiceRequestStatusTO> complaintsStatus = null;
			complaintsStatus = complaintsBacklineSummaryService
					.getServiceRequestStatus();
			request.setAttribute(
					ComplaintsCommonConstants.COMPLAINTS_STATUS_LIST,
					complaintsStatus);

			if (!StringUtil.isNull(userInfoTO.getUserto())) {
				EmployeeUserTO employeeUserTO = null;
				employeeUserTO = complaintsBacklineSummaryService
						.getEmployeeUser(userInfoTO.getUserto().getUserId());
				employeeId = employeeUserTO.getEmpTO().getEmployeeId();
				request.setAttribute(ComplaintsCommonConstants.EMPLOYEE_ID,
						employeeId);
			}

			((ComplaintsBacklineSummaryForm) form).setTo(summaryTO);
		} catch (CGBusinessException e) {
			LOGGER.error("ComplaintsBacklineSummaryAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
			// actionMessage = new
			// ActionMessage(AdminErrorConstants.NO_PICKUPBOY);
		} catch (CGSystemException e) {
			LOGGER.error("ComplaintsBacklineSummaryAction::preparePage ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ComplaintsBacklineSummaryAction::preparePage ..Exception :"
					+ e);
			getGenericException(request, e);
			// actionMessage = new ActionMessage(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ComplaintsBacklineSummaryAction::preparePage::END------------>:::::::");

		return mapping
				.findForward(ComplaintsCommonConstants.URL_VIEW_BACKLINE_SUMMARY);
	}

	/*
	 * @SuppressWarnings("static-access") public void
	 * getComplaintDetails(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) { LOGGER.debug(
	 * "ComplaintsBacklineSummaryAction::getComplaintDetails::START------------>:::::::"
	 * ); ComplaintsBacklineSummaryForm summaryForm =null;
	 * ComplaintsBacklineSummaryTO summaryTO = null; PrintWriter out = null;
	 * String jsonResult = null; try{ out=response.getWriter(); summaryForm =
	 * (ComplaintsBacklineSummaryForm)form; summaryTO
	 * =(ComplaintsBacklineSummaryTO)summaryForm.getTo();
	 * if(!StringUtil.isNull(summaryTO)){ complaintsBacklineSummaryService =
	 * getComplaintsBacklineSummaryService(); List<ServiceRequestTO>
	 * serviceRequestTOs
	 * =complaintsBacklineSummaryService.getServiceRequestDetails(summaryTO);
	 * summaryTO.setServiceRequestTOs(serviceRequestTOs); }
	 * 
	 * if (!StringUtil.isNull(summaryTO)) { jsonResult =
	 * serializer.toJSON(summaryTO).toString(); } }catch(Exception e){
	 * LOGGER.error(
	 * "Error occured in ComplaintsBacklineSummaryAction :: getComplaintDetails() ::"
	 * + e.getMessage()); }finally { out.print(jsonResult);
	 * out.flush(); out.close(); } LOGGER.debug(
	 * "ComplaintsBacklineSummaryAction::getComplaintDetails::END------------>:::::::"
	 * ); }
	 */
	@SuppressWarnings("static-access")
	public void getComplaintDetailsByServiceRequestNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByServiceRequestNo::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		try {
			out = response.getWriter();
			String serviceRequestNo = request.getParameter("serviceRequestNo");
			complaintsBacklineSummaryService = getComplaintsBacklineSummaryService();
			List<ServiceRequestTO> serviceRequestTOs = complaintsBacklineSummaryService
					.getComplaintDetailsByServiceRequestNo(serviceRequestNo);

			if (!StringUtil.isNull(serviceRequestTOs)) {
				jsonResult = serializer.toJSON(serviceRequestTOs).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestNo() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestNo() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestNo() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByServiceRequestNo::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void getComplaintDetailsByServiceRequestStatus(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByServiceRequestStatus::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		HttpSession session = request.getSession(false);
		try {
			out = response.getWriter();

			String statusName = request.getParameter("serviceRequestStatusTO");
			complaintsBacklineSummaryService = getComplaintsBacklineSummaryService();
			List<ServiceRequestTO> serviceRequestTOs = complaintsBacklineSummaryService
					.getComplaintDetailsByServiceRequestStatus(statusName,employeeId);
			if (!StringUtil.isEmptyColletion(serviceRequestTOs)) {
				jsonResult = serializer.toJSON(serviceRequestTOs).toString();
				session.setAttribute("serviceRequestTOList", serviceRequestTOs);
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestStatus() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestStatus() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByServiceRequestStatus() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByServiceRequestStatus::END------------>:::::::");
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public void getComplaintDetailsByUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByUser::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		HttpSession session = request.getSession(false);
		try {
			out = response.getWriter();

			complaintsBacklineSummaryService = getComplaintsBacklineSummaryService();
			
			List<ServiceRequestTO> serviceRequestTOList = (List<ServiceRequestTO>) session
					.getAttribute(ComplaintsCommonConstants.SERVICE_REQUEST_TO_LIST);
			if (!StringUtil.isNull(serviceRequestTOList)) {
				jsonResult = serializer.toJSON(serviceRequestTOList).toString();
			}
			
			if (CGCollectionUtils.isEmpty(serviceRequestTOList)) {
				List<ServiceRequestTO> serviceRequestTOs = complaintsBacklineSummaryService
						.getComplaintDetailsByUser(employeeId);

				if (!StringUtil.isNull(serviceRequestTOs)) {
					jsonResult = serializer.toJSON(serviceRequestTOs)
							.toString();
				}
			}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByUser() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByUser() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ComplaintsBacklineSummaryAction :: getComplaintDetailsByUser() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			session.removeAttribute("serviceRequestTOList");
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ComplaintsBacklineSummaryAction::getComplaintDetailsByUser::END------------>:::::::");
	}

	public ComplaintsBacklineSummaryService getComplaintsBacklineSummaryService() {
		if (StringUtil.isNull(complaintsBacklineSummaryService)) {
			complaintsBacklineSummaryService = (ComplaintsBacklineSummaryService) getBean(AdminSpringConstants.COMPLAINTS_BACKLINE_SUMMARY_SERVICE);
		}
		return complaintsBacklineSummaryService;
	}

}
