package com.ff.admin.mec.pettycash.action;

import java.io.PrintWriter;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.pettycash.form.PettyCashReportForm;
import com.ff.admin.mec.pettycash.service.PettyCashReportService;
import com.ff.organization.OfficeTO;
import com.ff.to.mec.pettycash.PettyCashReportTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author hkansagr
 */

public class PettyCashReportAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PettyCashReportAction.class);

	/** The pettyCashReportService. */
	private PettyCashReportService pettyCashReportService;

	/**
	 * To view petty cash report on screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view pettyCashReport.jsp
	 * @throws Exception
	 */
	public ActionForward viewPettyCashReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PettyCashReportAction :: viewPettyCashReport() :: START");
		PettyCashReportTO to = null;
		String url = MECCommonConstants.PETTY_CASH_REPORT;
		ActionMessage actionMessage = null;
		try {
			if (!isBranchOrHubOffice(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_BRANCH_OR_HUB);
			} else {
				to = new PettyCashReportTO();
				pettyCashReportStartup(request, to);
				saveToken(request);
				((PettyCashReportForm) form).setTo(to);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("PettyCashReportAction :: viewPettyCashReport() :: CGBusinessException :",	e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("PettyCashReportAction :: viewPettyCashReport() :: CGSystemException :", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("PettyCashReportAction :: viewPettyCashReport() :: Exception :", e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("PettyCashReportAction :: viewPettyCashReport() :: END");
		return mapping.findForward(url);
	}

	/**
	 * To set default values on start up of petty cash report
	 * 
	 * @param request
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void pettyCashReportStartup(HttpServletRequest request,
			PettyCashReportTO to) throws CGBusinessException,CGSystemException {
		LOGGER.trace("PettyCashReportAction :: pettyCashReportStartup() :: START");

		/** To get User Info from session attribute */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		/** To set the todays date i.e. DD/MM/YYYY to displaying on report */
		String pettyCashDate = request
				.getParameter(MECCommonConstants.PARAM_PETTY_CASH_DATE);
		if (!StringUtil.isNull(pettyCashDate)
				&& !StringUtil.isStringEmpty(pettyCashDate)) {
			to.setTodaysDate(pettyCashDate);
			to.setPettyCashURL(composeReportUrl(userInfo
					.getConfigurableParams()));
		} else {
			to.setTodaysDate(DateUtil.getCurrentDateInDDMMYYYY());
		}

		/** To set the current date i.e. YYYY-MM-DD to data access purpose */
		to.setCurrentDate(getCurrentDateInYYYYMMDD(to.getTodaysDate()));

		/** The closing date - It may contain negative value(s). */
		pettyCashReportService = getPettyCashReportService();
		// format: DD/MM/YYYY (i.e. todaysDate - 1)
		String dateOneDayB4 = pettyCashReportService.decreaseDateByOne(to
				.getTodaysDate());
		// format: YYYY-MM-DD
		to.setClosingDate(getCurrentDateInYYYYMMDD(dateOneDayB4));

		/** To set logged in office id and region id */
		OfficeTO officeTO = userInfo.getOfficeTo();
		if (!StringUtil.isNull(officeTO)) {
			// To set logged in office id
			to.setLoggedInOfficeId(officeTO.getOfficeId());
			if (!StringUtil.isNull(officeTO.getRegionTO())) {
				// To set region id
				to.setRegionId(officeTO.getRegionTO().getRegionId());
			}
		}

		/** To set payment mode as CASH -CA */
		to.setPaymentMode(CommonConstants.PAYMENT_MODE_CODE_CASH);

		LOGGER.trace("PettyCashReportAction :: pettyCashReportStartup() :: END");
	}

	/**
	 * To get date in YYYY-MM-DD Format
	 * 
	 * @param date
	 * @return date in string i.e. YYYY-MM-DD
	 */
	private String getCurrentDateInYYYYMMDD(String date) {
		String dateStr = CommonConstants.EMPTY_STRING;
		String dateArr[] = date.split(CommonConstants.SLASH_CONST);
		String day = dateArr[0];
		String month = dateArr[1];
		String year = dateArr[2];
		dateStr = year + CommonConstants.HYPHEN + month
				+ CommonConstants.HYPHEN + day;
		return dateStr;
	}

	/**
	 * To check whether Logged In Office is branch/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isBranchOrHubOffice(HttpServletRequest request) {
		LOGGER.trace("PettyCashReportAction :: isBranchOrHubOffice() :: START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO != null
				&& loggedInOfficeTO.getOfficeTypeTO() != null
				&& (loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE) || loggedInOfficeTO
						.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_HUB_OFFICE))) {
			result = true;
		}
		LOGGER.trace("PettyCashReportAction :: isBranchOrHubOffice() :: END");
		return result;
	}

	/**
	 * AJAX - To view petty cash report on screen viewer - .rptdesign
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ajaxPettyCashReportViewer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.trace("PettyCashReportAction :: ajaxPettyCashReportViewer() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		String exception = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			PettyCashReportTO to = new PettyCashReportTO();
			pettyCashReportStartup(request, to);
			jsonResult = JSONSerializer.toJSON(to).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"PettyCashReportAction :: ajaxPettyCashReportViewer() :: CGBusinessException :",
					e);
			exception = getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"PettyCashReportAction :: ajaxPettyCashReportViewer() :: CGSystemException :",
					e);
			exception = getSystemExceptionMessage(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"PettyCashReportAction :: ajaxPettyCashReportViewer() :: Exception :",
					e);
			exception = getGenericExceptionMessage(request, e);
		} finally {
			if (!StringUtil.isStringEmpty(exception)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG, exception);
			}
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PettyCashReportAction :: ajaxPettyCashReportViewer() :: END");
	}

	/**
	 * To get pettyCashReportService
	 * 
	 * @return pettyCashReportService
	 */
	private PettyCashReportService getPettyCashReportService() {
		if (pettyCashReportService == null) {
			pettyCashReportService = (PettyCashReportService) getBean(AdminSpringConstants.PETTY_CASH_REPORT_SERVICE);
		}
		return pettyCashReportService;
	}

}
