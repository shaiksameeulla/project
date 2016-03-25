/**
 * 
 */
package com.ff.admin.complaints.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.CriticalClaimComplaintForm;
import com.ff.admin.complaints.service.CriticalClaimComplaintService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.complaints.CriticalClaimComplaintTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author cbhure
 * 
 */
public class CriticalClaimComplaintAction extends AbstractComplaintsAction {

	private final static Logger logger = LoggerFactory
			.getLogger(CriticalClaimComplaintAction.class);

	private CriticalClaimComplaintService criticalClaimComplaintService;

	public void viewCriticalClaimComplaint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("CriticalClaimComplaintAction :: viewCriticalClaimComplaint :: Start");

		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		CriticalClaimComplaintTO criticalClaimComplaintTO = null;
		try {
			out = response.getWriter();
			criticalClaimComplaintTO = (CriticalClaimComplaintTO) ((CriticalClaimComplaintForm) form)
					.getTo();

			String complaintNo = request
					.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_NO);
			if (!StringUtil.isStringEmpty(complaintNo))
				criticalClaimComplaintTO.setComplaintNumber(complaintNo);

			setDefaultValues(request, criticalClaimComplaintTO);
			jsonResult = JSONSerializer.toJSON(criticalClaimComplaintTO)
					.toString();

		} catch (Exception e) {
			logger.error(
					"Exception occurs in CriticalComplaintAction :: viewCriticalClaimComplaint() :: ",
					e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		logger.debug("CriticalClaimComplaintAction :: viewCriticalClaimComplaint :: END");

		// return
		// mapping.findForward(ComplaintsCommonConstants.VIEW_CRITICAL_CLAIM_COMPLAINT);
	}

	private void setDefaultValues(HttpServletRequest request,
			CriticalClaimComplaintTO to) throws CGBusinessException,
			CGSystemException {
		logger.trace("CriticalClaimComplaintAction :: setDefaultValues() :: START");

		/* To get User Info from session attribute. */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		/* Setting created & updated by user id. */
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			if (StringUtil.isEmptyInteger(to.getCreatedBy())) {
				to.setCreatedBy(userTO.getUserId());
			}
			to.setUpdatedBy(userTO.getUserId());
		}

		/* Setting current date and time */
		to.setCreationDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

		to.setIsActualClaim(CommonConstants.YES);
		to.setIsActualClaim(CommonConstants.NO);
		to.setIsNegotiableClaim(CommonConstants.YES);
		to.setIsNegotiableClaim(CommonConstants.NO);
		to.setIsSettlement(CommonConstants.PARAM_LOCAL);
		to.setIsSettlement(CommonConstants.PARAM_CORP);

		to.setTodaysDate(DateUtil.getCurrentDateInDDMMYYYY());

		request.setAttribute(ComplaintsCommonConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute(ComplaintsCommonConstants.PARAM_YES,
				CommonConstants.YES);
		request.setAttribute(ComplaintsCommonConstants.PARAM_NO,
				CommonConstants.NO);
		request.setAttribute(ComplaintsCommonConstants.PARAM_LOCAL,
				CommonConstants.PARAM_LOCAL);
		request.setAttribute(ComplaintsCommonConstants.PARAM_CORP,
				CommonConstants.PARAM_CORP);
		/*
		 * request.setAttribute(ComplaintsCommonConstants.COMPLAINT_ID,
		 * CommonConstants.COMPLAINT_ID);
		 * request.setAttribute(ComplaintsCommonConstants.CONSG_NUMBER,
		 * CommonConstants.CONSG_NUMBER);
		 */

		/** To set CLAIM complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_CLAIM_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_CLAIM_COMPLAINT_STATUS_LIST);
		/** To set LEGAL complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_LEGAL_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_LEGAL_COMPLAINT_STATUS_LIST);

		logger.trace("CriticalClaimComplaintAction :: setDefaultValues() :: END");
	}

	public void saveCriticalClaim(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {

		logger.debug("CriticalClaimComplaintAction :: saveCriticalClaim ..Start");
		CriticalClaimComplaintTO criticalComplaintTO = null;
		String transMag = null;

		PrintWriter out = null;
		// String jsonResult = CommonConstants.EMPTY_STRING;
		// CriticalClaimComplaintForm
		// criticalClaimComplaintForm=(CriticalClaimComplaintForm)form;
		// CriticalClaimComplaintTO criticalComplaintTO =
		// (CriticalClaimComplaintTO)criticalClaimComplaintForm.getTo();
		// setDefaultValues(request, criticalComplaintTO);
		try {

			criticalComplaintTO = (CriticalClaimComplaintTO) ((CriticalClaimComplaintForm) form)
					.getTo();
			criticalClaimComplaintService = getCriticalComplaintService();
			Boolean isSave = criticalClaimComplaintService
					.saveCriticalClaimComplaint(criticalComplaintTO);
			if (isSave) {
				transMag = "SUCCESS";
			}

			out = response.getWriter();
			// criticalClaimComplaintService = getCriticalComplaintService();
			// criticalClaimComplaintService.saveCriticalClaimComplaint(criticalComplaintTO);

			/*
			 * String transMsg = getMessageFromBundle(request,
			 * ComplaintsCommonConstants.DTLS_SAVED_SUCCESSFULLY, null,
			 * FrameworkConstants.UNIVERSAL_MESSAGE_BUNDLE_KEY);
			 * criticalComplaintTO.setTransMsg(transMsg);
			 * prepareActionMessage(request, actionMessage); jsonResult =
			 * JSONSerializer.toJSON(criticalComplaintTO).toString();
			 */
		} catch (CGBusinessException e) {
			logger.error(
					"Exception occurs in CriticalClaimComplaintAction :: saveCriticalClaim() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			logger.error(
					"Exception occurs in CriticalClaimComplaintAction :: saveCriticalClaim() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			logger.error(
					"Exception occurs in CriticalClaimComplaintAction :: saveCriticalClaim() :: ",
					e);
			getGenericException(request, e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}

		logger.trace("CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: END");
		// return mapping.findForward(url);
	}

	/*
	 * private void setUrl(HttpServletRequest request, String url) {
	 * request.setAttribute(
	 * ComplaintsCommonConstants.PARAM_CRITICAL_COMPLAINT_URL, url); }
	 */
	/**
	 * @param criticalClaimComplaintService
	 *            the criticalClaimComplaintService to set
	 */
	public void setCriticalClaimComplaintService(
			CriticalClaimComplaintService criticalClaimComplaintService) {
		this.criticalClaimComplaintService = criticalClaimComplaintService;
	}

	private CriticalClaimComplaintService getCriticalComplaintService() {
		if (StringUtil.isNull(criticalClaimComplaintService)) {
			criticalClaimComplaintService = (CriticalClaimComplaintService) getBean(AdminSpringConstants.CRITICAL_CLAIM_COMPLAINT_SERVICE);
		}
		return criticalClaimComplaintService;
	}

	public void getCriticalClaimComplaintDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		logger.debug("CriticalClaimComplaintAction::getCriticalClaimComplaintDtls ..Start");
		PrintWriter out = null;
		String jsonResult = "";
		CriticalClaimComplaintForm criticalClaimComplaintForm = (CriticalClaimComplaintForm) form;
		CriticalClaimComplaintTO criticalComplaintTO = (CriticalClaimComplaintTO) criticalClaimComplaintForm
				.getTo();
		String complaintNo = criticalComplaintTO.getComplaintNo();
		setDefaultValues(request, criticalComplaintTO);
		criticalClaimComplaintService = getCriticalComplaintService();
		try {
			out = response.getWriter();
			criticalComplaintTO = criticalClaimComplaintService
					.getCriticalClaimComplaintDtls(criticalComplaintTO);
			if (StringUtil.isNull(criticalComplaintTO)) {
				criticalComplaintTO = new CriticalClaimComplaintTO();
				criticalComplaintTO = criticalClaimComplaintService
						.getCriticalComplaintDtls(complaintNo);
				// set serviceRequestComplaintId
			}
			if (!StringUtil.isNull(criticalComplaintTO)) {
				criticalComplaintTO.setTodaysDate(DateUtil
						.getCurrentDateInYYYYMMDDHHMM());
			}
			jsonResult = JSONSerializer.toJSON(criticalComplaintTO).toString();
		} catch (Exception e) {
			logger.error(
					"Exception occurs in CriticalClaimComplaintAction :: getCriticalClaimComplaintDtls() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
}
