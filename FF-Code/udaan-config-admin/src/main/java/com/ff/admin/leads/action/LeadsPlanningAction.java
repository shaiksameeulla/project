package com.ff.admin.leads.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.ViewUpdateFeedbackForm;
import com.ff.admin.leads.service.LeadsPlanningService;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.leads.ViewUpdateFeedbackTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;

/**
 * The Class LeadsPlanningAction.
 * 
 * @author sdalli
 */
public class LeadsPlanningAction extends AbstractCreateLead {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsPlanningAction.class);
	public transient JSONSerializer serializer;
	private LeadsPlanningService leadsPlanningService;

	private LeadsPlanningService getLeadsPlanningService() {
		if (StringUtil.isNull(leadsPlanningService)) {
			leadsPlanningService = (LeadsPlanningService) getBean(AdminSpringConstants.LEADS_PLANNING_SERVICE);
		}
		return leadsPlanningService;

	}

	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("LeadsPlanningAction::preparePage::START------------>:::::::");
		ViewUpdateFeedbackTO viewUpdateFeedbackTO = null;
		ViewUpdateFeedbackForm viewUpdateFeedbackForm = null;
		try {
			viewUpdateFeedbackForm = (ViewUpdateFeedbackForm) form;
			viewUpdateFeedbackTO = (ViewUpdateFeedbackTO) viewUpdateFeedbackForm
					.getTo();
			setDefaults(request,viewUpdateFeedbackTO);

			((ViewUpdateFeedbackForm) form).setTo(viewUpdateFeedbackTO);

		} /*catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in preparePage of LeadsPlanningAction..."
					+ e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in preparePage of LeadsPlanningAction..."
					+ e);
		}*/ catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in preparePage of LeadsPlanningAction..."
					+ e);
		}
		LOGGER.debug("LeadsPlanningAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
	}

	public ActionForward prepareLeadsFeedBackPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug("LeadsPlanningAction::preparePage::START------------>:::::::");
		ViewUpdateFeedbackTO viewUpdateFeedbackTO = null;
		ViewUpdateFeedbackForm viewUpdateFeedbackForm = null;
		try {
			viewUpdateFeedbackForm = (ViewUpdateFeedbackForm) form;
			viewUpdateFeedbackTO = (ViewUpdateFeedbackTO) viewUpdateFeedbackForm
					.getTo();
			setDefaults(request,viewUpdateFeedbackTO);

			((ViewUpdateFeedbackForm) form).setTo(viewUpdateFeedbackTO);
		} /*catch (CGSystemException e) {
		getSystemException(request, e);
		LOGGER.error("Exception happened in prepareLeadsFeedBackPage of LeadsPlanningAction..."
				+ e);
	} catch (CGBusinessException e) {
		getBusinessError(request, e);
		LOGGER.error("Exception happened in prepareLeadsFeedBackPage of LeadsPlanningAction..."
				+ e);
	}*/ catch (Exception e) {
		getGenericException(request, e);
		LOGGER.error("Exception happened in prepareLeadsFeedBackPage of LeadsPlanningAction..."
				+ e);
	}
		LOGGER.debug("LeadsPlanningAction::preparePage::END------------>:::::::");
		return mapping.findForward("leadFeedback");
	}

	@SuppressWarnings("static-access")
	public ActionForward savePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("LeadsPlanningAction::savePlan::START------------>:::::::");
		ViewUpdateFeedbackTO viewUpdateFeedbackTO = null;
		ViewUpdateFeedbackForm viewUpdateFeedbackForm = (ViewUpdateFeedbackForm) form;
		viewUpdateFeedbackTO = (ViewUpdateFeedbackTO) viewUpdateFeedbackForm
				.getTo();
		String transMag = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			setDefaults(request,viewUpdateFeedbackTO);
			leadsPlanningService = getLeadsPlanningService();
			transMag = leadsPlanningService.savePlan(viewUpdateFeedbackTO);
		} catch (CGSystemException e) {
			 LOGGER.error("Error occured in LeadsPlanningAction :: savePlan() ::"+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_LEADS_PLANNING;
		} catch (CGBusinessException e) {
			 LOGGER.error("Error occured in LeadsPlanningAction :: savePlan() ::"+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_LEADS_PLANNING;
		} catch (Exception e) {
			 LOGGER.error("Error occured in LeadsPlanningAction :: savePlan() ::"+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_LEADS_PLANNING;
		} finally {
			if (viewUpdateFeedbackTO == null) {
				viewUpdateFeedbackTO = new ViewUpdateFeedbackTO();
			}
			viewUpdateFeedbackTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(viewUpdateFeedbackTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LeadsPlanningAction::savePlan::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
	}


	public void getLeadDetailsByLeadNumber(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CGBusinessException,
			IOException {
		LOGGER.debug("LeadsPlanningAction::getLeadDetailsByLeadNumber::START------------>:::::::");
		String jsonResult = "";
		PrintWriter out = null;
		LeadTO leadTO = null;
		String msg = "";
		String leadNumber = request.getParameter("leadNumber").trim();

		try {
			out = response.getWriter();
			leadsPlanningService = getLeadsPlanningService();
			leadTO = leadsPlanningService.getLeadDetails(leadNumber);
		
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadDetailsByLeadNumber() ::"+ e);
			msg = getSystemExceptionMessage(request, e);
		
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadDetailsByLeadNumber() ::"+ e);
			msg = getBusinessErrorFromWrapper(request, e);
		} catch (Exception e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadDetailsByLeadNumber() ::"+ e);
			msg = getGenericExceptionMessage(request, e);
		} finally {
			if (leadTO == null) {
				leadTO = new LeadTO();
			}
			leadTO.setAlertMsg(msg);
			jsonResult = JSONSerializer.toJSON(leadTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LeadsPlanningAction::getLeadDetailsByLeadNumber::END------------>:::::::");
	}

	public void validateVisitedDate(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		LOGGER.debug("LeadsPlanningAction::validateVisitedDate::START------------>:::::::");
		long dateDiff = 0l;
		String dateAlertMsg = "";
		try {
			String visitedDate = request.getParameter("visitedDate");
			Date visitDate = DateUtil.stringToDDMMYYYYFormat(visitedDate);

			dateDiff = DateUtil.DayDifferenceBetweenTwoDatesIncludingBackDate(
					DateUtil.getCurrentDate(), visitDate);
			if (!StringUtil.isNull(dateDiff)) {
				if (dateDiff > 30) {
					dateAlertMsg = getMessageFromErrorBundle(request,
							LeadCommonConstants.MESSAGE_DATE_MORE_THAN_1_MONTH);
				} else if (dateDiff < 0) {
					dateAlertMsg = getMessageFromErrorBundle(request,
							LeadCommonConstants.MESSAGE_BACK_DATE);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception happened in validateVisitedDate of LeadsPlanningAction..."
					+ e);
			dateAlertMsg = getGenericExceptionMessage(request, e);
		} finally {
			response.getWriter().print(dateAlertMsg);
		}
		LOGGER.debug("LeadsPlanningAction::validateVisitedDate::END------------>:::::::");
	}

	public void getFeedBackCodeDetails(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("LeadsPlanningAction::getFeedBackCodeDetails::START------------>:::::::");
		List<StockStandardTypeTO> feedbackCodeTOList = null;
		String jsonResult = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			leadsPlanningService = getLeadsPlanningService();
			feedbackCodeTOList = leadsPlanningService.getFeedbackList();
			Collections.sort(feedbackCodeTOList);
			jsonResult = JSONSerializer.toJSON(feedbackCodeTOList).toString();
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getFeedBackCodeDetails() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getFeedBackCodeDetails() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (Exception e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getFeedBackCodeDetails() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
		
		} finally {
			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LeadsPlanningAction::getFeedBackCodeDetails::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public ActionForward savePlanFeedBackDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LeadsPlanningAction::savePlanFeedBackDetails::START------------>:::::::");
		ViewUpdateFeedbackTO viewUpdateFeedbackTO = null;
		ViewUpdateFeedbackForm viewUpdateFeedbackForm = (ViewUpdateFeedbackForm) form;
		viewUpdateFeedbackTO = (ViewUpdateFeedbackTO) viewUpdateFeedbackForm
				.getTo();
		String transMag = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			setDefaults(request,viewUpdateFeedbackTO);
			leadsPlanningService = getLeadsPlanningService();
			transMag = leadsPlanningService.savePlan(viewUpdateFeedbackTO);
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in savePlanFeedBackDetails of LeadsPlanningAction..."+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_FEEDBACK_DETAILS;
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in savePlanFeedBackDetails of LeadsPlanningAction..."+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_FEEDBACK_DETAILS;
		} catch (Exception e) {
			LOGGER.error("Exception happened in savePlanFeedBackDetails of LeadsPlanningAction..."+ e);
			 transMag = LeadCommonConstants.ERROR_IN_SAVING_FEEDBACK_DETAILS;

		} finally {
			if (viewUpdateFeedbackTO == null) {
				viewUpdateFeedbackTO = new ViewUpdateFeedbackTO();
			}
			viewUpdateFeedbackTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(viewUpdateFeedbackTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LeadsPlanningAction::savePlanFeedBackDetails::END------------>:::::::");
		return mapping.findForward("leadFeedback");
	}

	public void getLeadsPlanDtlsByLeadNumber(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("LeadsPlanningAction::getLeadsPlanDtlsByLeadNumber::START------------>:::::::");
		String jsonResult = "";
		PrintWriter out = null;
		List<PlanTO> planTOList = null;
		String leadNumber = request.getParameter("leadNumber").trim();

		try {
			out = response.getWriter();
			leadsPlanningService = getLeadsPlanningService();
			planTOList = leadsPlanningService
					.getLeadsPlanDtlsByleadNumber(leadNumber);
			jsonResult = JSONSerializer.toJSON(planTOList).toString();
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadsPlanDtlsByLeadNumber() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadsPlanDtlsByLeadNumber() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (Exception e) {
			LOGGER.error("Error occured in LeadsPlanningAction :: getLeadsPlanDtlsByLeadNumber() ::"+ e);
			jsonResult = prepareCommonException(AdminSpringConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
		
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LeadsPlanningAction::getLeadsPlanDtlsByLeadId::END------------>:::::::");
	}
	
	private void setDefaults(HttpServletRequest request, ViewUpdateFeedbackTO viewUpdateFeedbackTO)
	{
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
		
		if(!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId())){
			EmployeeTO emp = new EmployeeTO();
			emp.setEmployeeId(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId());
			viewUpdateFeedbackTO.setCreatedBy(emp);
		}
		if(!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId())){
			EmployeeTO emp = new EmployeeTO();
			emp.setEmployeeId(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId());
			viewUpdateFeedbackTO.setUpdatedBy(emp);
		}
	}
	

}
