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
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.CriticalComplaintForm;
import com.ff.admin.complaints.service.ComplaintsCommonService;
import com.ff.admin.complaints.service.CriticalComplaintService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.complaints.CriticalComplaintTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author hkansagr
 */
public class CriticalComplaintAction extends AbstractComplaintsAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CriticalComplaintAction.class);

	/** The complaintsCommonService. */
	private ComplaintsCommonService complaintsCommonService;

	/** The complaintsCommonService. */
	private CriticalComplaintService criticalComplaintService;

	/**
	 * To view critical complaint screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view of critical complaints
	 * @throws Exception
	 */
	public void viewCriticalComplaint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CriticalComplaintAction :: viewCriticalComplaint() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		CriticalComplaintTO to = null;
		try {
			out = response.getWriter();

			to = (CriticalComplaintTO) ((CriticalComplaintForm) form).getTo();
			criticalComplaintService = getCriticalComplaintService();

			String complaintNo = request
					.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_NO);
			if (!StringUtil.isStringEmpty(complaintNo))
				to.setComplaintNo(complaintNo);

			boolean isCrtlCmpltExist = criticalComplaintService
					.isCriticalComplaintExist(to.getComplaintNo());
			if (isCrtlCmpltExist) {
				to = criticalComplaintService.searchCriticalComplaint(to);
			} else {
				to = criticalComplaintService.getServiceRequestDtls(to);
			}
			setDefaultValues(request, to);
			jsonResult = JSONSerializer.toJSON(to).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: viewCriticalComplaint() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: viewCriticalComplaint() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: viewCriticalComplaint() :: ",
					e);
			getGenericException(request, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CriticalComplaintAction :: viewCriticalComplaint() :: END");
	}

	/**
	 * To set default values
	 * 
	 * @param request
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	private void setDefaultValues(HttpServletRequest request,
			CriticalComplaintTO to) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CriticalComplaintAction :: setDefaultValues() :: START");

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
		if (StringUtil.isStringEmpty(to.getComplaintCreationDateStr())) {
			to.setComplaintCreationDateStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat());
		}

		/* Setting current date */
		if (StringUtil.isStringEmpty(to.getFirDateStr())) {
			to.setFirDateStr(DateUtil.getCurrentDateInDDMMYYYY());
			to.setFirCopy(CommonConstants.NO);
		}
		if (StringUtil.isStringEmpty(to.getTypeDateStr())) {
			to.setTypeDateStr(DateUtil.getCurrentDateInDDMMYYYY());
			to.setCustomerType(CommonConstants.NO);
		}

		complaintsCommonService = getComplaintsCommonService();

		/* To set information given to - drop down. */
		List<StockStandardTypeTO> infoGivenTO = (List<StockStandardTypeTO>) session
				.getAttribute(ComplaintsCommonConstants.PARAM_INFO_GIVEN_TO);
		if (CGCollectionUtils.isEmpty(infoGivenTO)) {
			infoGivenTO = complaintsCommonService
					.getStandardTypesByTypeName(ComplaintsCommonConstants.STD_TYPE_INFORMATION_GIVEN_TO);
			session.setAttribute(ComplaintsCommonConstants.PARAM_INFO_GIVEN_TO,
					infoGivenTO);
		}
		to.setInfoGivenTO(infoGivenTO);
		request.setAttribute(ComplaintsCommonConstants.PARAM_INFO_GIVEN_TO,
				infoGivenTO);

		/* Setting todays date. */
		to.setTodaysDate(DateUtil.getCurrentDateInDDMMYYYY());

		/* Setting request parameters */
		request.setAttribute(ComplaintsCommonConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute(ComplaintsCommonConstants.PARAM_YES,
				CommonConstants.YES);
		request.setAttribute(ComplaintsCommonConstants.PARAM_NO,
				CommonConstants.NO);

		/** To set critical complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_CRITICAL_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_CRITICAL_COMPLAINT_STATUS);

		LOGGER.trace("CriticalComplaintAction :: setDefaultValues() :: END");
	}

	/**
	 * To save or update critical complaint details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view screen of critical complaint
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateCriticalComplaint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: START");
		String url = ComplaintsCommonConstants.VIEW_CRITICAL_COMPLAINT;
		CriticalComplaintTO to = null;
		ActionMessage actionMessage = null;
		String tabInfor = "tabs-2";
		try {
			setDefaultCommonValue(request);

			to = (CriticalComplaintTO) ((CriticalComplaintForm) form).getTo();
			criticalComplaintService = getCriticalComplaintService();
			criticalComplaintService.saveOrUpdateCriticalComplaint(to);
			actionMessage = new ActionMessage(
					ComplaintsCommonConstants.DTLS_SAVED_SUCCESSFULLY);
			resetToken(request);
			request.setAttribute(ComplaintsCommonConstants.INDEX, "3");
			prepareActionMessage(request, actionMessage);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: ",
					e);
			getGenericException(request, e);
		} finally {
			try {
				setDefaultValues(request, to);
			} catch (CGBusinessException e) {
				LOGGER.error(
						"Exception occurs in CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: ",
						e);
			} catch (CGSystemException e) {
				LOGGER.error(
						"Exception occurs in CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: ",
						e);
			}
			to.setTabName(tabInfor);
			String ccURL = prepareUrlString(to);
			setCriticalCmpltUrl(request, ccURL);
		}
		((CriticalComplaintForm) form).setTo(to);
		LOGGER.trace("CriticalComplaintAction :: saveOrUpdateCriticalComplaint() :: END");
		return mapping.findForward(url);
	}

	/**
	 * To set default common values
	 * 
	 * @param request
	 */
	private void setDefaultCommonValue(HttpServletRequest request) {
		LOGGER.trace("CriticalComplaintAction :: setDefaultCommonValue() :: START");
		String complaintNumber = request.getParameter("complaintNumber");
		String complaintId = request.getParameter("complaintId");
		String consignmentNumber = request.getParameter("consignmentNumber");

		request.setAttribute("complaintNumber", complaintNumber);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("consignmentNumber", consignmentNumber);
		LOGGER.trace("CriticalComplaintAction :: setDefaultCommonValue() :: END");
	}

	/**
	 * To prepare critical complaint URL after save to redirect to view page
	 * 
	 * @param to
	 * @return ccURL
	 */
	private String prepareUrlString(CriticalComplaintTO to) {
		String fullURL = "./paperwork.do?submitName=preparePaperwork"
				+ "&complaintNumber=" + to.getComplaintNo() + "&complaintId="
				+ to.getComplaintId() + "&consignmentNumber="
				+ to.getConsignmentNumber();
		if (!StringUtil.isStringEmpty(to.getTabName())) {
			fullURL = fullURL + "&tabName=" + to.getTabName();
		}
		return fullURL;
	}

	/**
	 * To set URL after save operation to redirect to view page
	 * 
	 * @param request
	 * @param url
	 */
	private void setCriticalCmpltUrl(HttpServletRequest request, String url) {
		request.setAttribute(
				ComplaintsCommonConstants.PARAM_CRITICAL_COMPLAINT_URL, url);
	}

	/**
	 * To get complaintsCommonService.
	 * 
	 * @return complaintsCommonService
	 */
	private ComplaintsCommonService getComplaintsCommonService() {
		if (complaintsCommonService == null) {
			complaintsCommonService = (ComplaintsCommonService) getBean(AdminSpringConstants.COMPLAINTS_COMMON_SERVICE);
		}
		return complaintsCommonService;
	}

	/**
	 * To get criticalComplaintService.
	 * 
	 * @return criticalComplaintService
	 */
	private CriticalComplaintService getCriticalComplaintService() {
		if (criticalComplaintService == null) {
			criticalComplaintService = (CriticalComplaintService) getBean(AdminSpringConstants.CRITICAL_COMPLAINT_SERVICE);
		}
		return criticalComplaintService;
	}

}
