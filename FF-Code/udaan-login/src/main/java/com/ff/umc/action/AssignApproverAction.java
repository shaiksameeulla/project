package com.ff.umc.action;

/**
 * Author : Rohini Maladi
 * @Class : AssignApproverAction
 * @Desc : Actions For UMC - Assign Approver
 * Creation Date : Feb - 18 - 2013
 */

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.AssignApproverTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.AssignApproverForm;
import com.ff.umc.service.AssignApproverService;

public class AssignApproverAction extends CGBaseAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AssignApproverAction.class);
	// private RoleManagementService userRoleService = null;
	public transient JSONSerializer serializer;
	public AssignApproverService assignApproverService;

	/**
	 * View Form Details
	 * 
	 * @inputparam
	 * @return Populate the screen with defalut values
	 * @author Rohini Maladi
	 */

	public ActionForward viewAssignApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("AssignApproverAction::viewAssignApprover::START------------>:::::::");
		AssignApproverTO assignApproverTO = new AssignApproverTO();

		getDefultUIValues(request, assignApproverTO);
		((AssignApproverForm) form).setTo(assignApproverTO);

		LOGGER.debug("AssignApproverAction::viewAssignApprover::END------------>:::::::");

		return mapping.findForward(UmcConstants.SUCCESS);
	}

	/**
	 * Load the default values into TO
	 * 
	 * @inputparam AssignApproverTO object
	 * @return Load the values into TO object
	 * @author Rohini Maladi
	 */

	@SuppressWarnings("unchecked")
	private void getDefultUIValues(HttpServletRequest request,
			AssignApproverTO assignApproverTO) {
		LOGGER.trace("AssignApproverAction::getDefultUIValues::START------------>:::::::");
		List<LabelValueBean> offList = null;
		ActionMessage actionMessage = null;
		try {

			HttpSession session = request.getSession(Boolean.FALSE);
			// UserInfoTO userInfoTO
			// =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
			// UserTO userTO = userInfoTO.getUserto();
			assignApproverService = getAssignApproverService();

			offList = (List<LabelValueBean>) session
					.getAttribute(UmcConstants.REGIONAL_OFFICE_LIST);

			if (CGCollectionUtils.isEmpty(offList)) {
				offList = assignApproverService.getAllRegionalOffices();
				if (!CGCollectionUtils.isEmpty(offList)) {
					session.setAttribute(UmcConstants.REGIONAL_OFFICE_LIST,
							offList);
				}
			}
			if (!CGCollectionUtils.isEmpty(offList)) {
				request.setAttribute(UmcConstants.REGIONAL_OFFICE_LIST, offList);
				assignApproverTO.setRegionalOfficeList(offList);
			} else {
				actionMessage = new ActionMessage(
						LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND,
						UmcConstants.REGIONAL_OFC);
				LOGGER.error("Exception happened in getDefultUIValues of RouteServicedByAction...");
			}

			List<ApplScreensTO> screensList = null;
			screensList = (List<ApplScreensTO>) session
					.getAttribute(UmcConstants.APPL_SCREEN_LIST);
			if (CGCollectionUtils.isEmpty(screensList)) {
				screensList = assignApproverService.getAllAssignApplScreens();
				session.setAttribute(UmcConstants.APPL_SCREEN_LIST, screensList);
			}
			if (!CGCollectionUtils.isEmpty(screensList)) {
				request.setAttribute(UmcConstants.APPL_SCREEN_LIST, screensList);
				assignApproverTO.setAssignApplScreensList(screensList);
			} else {
				actionMessage = new ActionMessage(
						LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND,
						UmcConstants.ASSIGN_SCREEN);
				LOGGER.error("Exception happened in getDefultUIValues of AssignApproverAction...");
			}

		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getDefultUIValues of AssignApproverAction..."
					+ e.getMessage());
			getSystemException(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getDefultUIValues of AssignApproverAction..."
					+ e.getMessage());
			actionMessage = new ActionMessage(
					LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND_DB_ISSUE);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getDefultUIValues of AssignApproverAction..."
					+ e.getMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			actionMessage = new ActionMessage(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("AssignApproverAction::getDefultUIValues::END------------>:::::::");

	}

	/**
	 * Get Screen Details
	 * 
	 * @inputparam UserId or User Name
	 * @return Populate the screen with result values
	 * @author Rohini Maladi
	 */

	@SuppressWarnings({ "static-access", "rawtypes" })
	public void getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("AssignApproverAction::getDetails::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		List approverDetails = null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			String userName = "";
			String userId = "";

			userName = request.getParameter(UmcConstants.USER_NAME);
			userId = request.getParameter(UmcConstants.USER_ID);
			assignApproverService = getAssignApproverService();
			approverDetails = assignApproverService.getAssignApproverDetails(userName,
					userId);

			if (!CGCollectionUtils.isEmpty(approverDetails)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,  serializer.toJSON(approverDetails).toString());
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								LoginErrorCodeConstants.ASSIGN_LOGIN_ID_DTLS_NOT_FOUND,
								new String[] { userName }));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getDetails of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getDetails of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getDetails of AssignApproverAction..."
					+ e.getLocalizedMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AssignApproverAction::getDetails::END------------>:::::::");
	}

	/**
	 * Load the Citi list by Regional offices
	 * 
	 * @inputparam request object it contains Regional office object
	 * @return Load the CITI LIST into String object
	 * @author Rohini Maladi
	 */

	@SuppressWarnings("static-access")
	public void getStationsByRegionalOffices(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("AssignApproverAction::getStationsByRegionalOffices::START------------>:::::::");
		String cityList = null;
		PrintWriter out = null;
		String regionalOfficeIds = FrameworkConstants.EMPTY_STRING;
		String[] regionalOfficeIdsList = null;
		List<CityTO> cityTOList = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {
			serializer = CGJasonConverter.getJsonObject();
			regionalOfficeIds = request
					.getParameter(UmcConstants.REGIONAL_OFFICES);
			out = response.getWriter();

			regionalOfficeIdsList = regionalOfficeIds
					.split(CommonConstants.COMMA);

			assignApproverService = getAssignApproverService();
			cityTOList = assignApproverService
					.getStationsByRegionalOffices(regionalOfficeIdsList);

			if (!CGCollectionUtils.isEmpty(cityTOList)) {
				cityList = serializer.toJSON(cityTOList).toString();
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG, cityList);
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND,
								new String[] { UmcConstants.ASSIGN_STATION }));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getStationsByRegionalOffices of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getStationsByRegionalOffices of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getStationsByRegionalOffices of AssignApproverAction..."
					+ e.getLocalizedMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AssignApproverAction::getStationsByRegionalOffices::END------------>:::::::");
	}

	public AssignApproverService getAssignApproverService() {
		if (StringUtil.isNull(assignApproverService)) {
			assignApproverService = (AssignApproverService) getBean(SpringConstants.ASSIGN_APPROVER_SERVICE);
		}
		return assignApproverService;
	}

	/**
	 * Load the office list by cities
	 * 
	 * @inputparam request object it contains citi object
	 * @return Load the Office List into String object
	 * @author Rohini Maladi
	 */

	@SuppressWarnings("static-access")
	public void getOfficesByCityList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("AssignApproverAction::getOfficesByCityList::START------------>:::::::");
		String officeList = null;
		PrintWriter out = null;
		String citiIds = FrameworkConstants.EMPTY_STRING;
		String[] citiIdsList = null;
		List<OfficeTO> officeTOList = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {
			serializer = CGJasonConverter.getJsonObject();
			citiIds = request.getParameter(UmcConstants.CITIES);
			out = response.getWriter();

			citiIdsList = citiIds.split(CommonConstants.COMMA);

			assignApproverService = getAssignApproverService();
			officeTOList = assignApproverService.getOfficesByCityList(
					citiIdsList, null);

			if (!CGCollectionUtils.isEmpty(officeTOList)) {
				officeList = serializer.toJSON(officeTOList).toString();
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG, officeList);
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND,
								new String[] { UmcConstants.ASSIGN_OFFICE }));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getOfficesByCityList of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getOfficesByCityList of AssignApproverAction..."
					+ e.getLocalizedMessage());
			/* String exception=ExceptionUtil.getExceptionStackTrace(e); */
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getOfficesByCityList of AssignApproverAction..."
					+ e.getLocalizedMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AssignApproverAction::getOfficesByCityList::END------------>:::::::");
	}

	/**
	 * Save Approver Rights details This method save the Rights of approver
	 * details in database
	 * 
	 * @inputparam AssignApproverForm will be passed with the following details
	 *             filled in -
	 *             <ul>
	 *             <li>AssignApproverTO
	 *             </ul>
	 * 
	 * @return
	 * @author Rohini Maladi
	 */
	public void saveOrUpdateAssignApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("AssignApproverAction::saveOrUpdateAssignApprover::START------------>:::::::");
		String status = FrameworkConstants.EMPTY_STRING;
		PrintWriter out = null;
		AssignApproverTO assignApproverTO = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {
			serializer = CGJasonConverter.getJsonObject();

			out = response.getWriter();

			AssignApproverForm assignApproverForm = (AssignApproverForm) form;
			assignApproverTO = (AssignApproverTO) assignApproverForm.getTo();

			assignApproverService = getAssignApproverService();
			status = assignApproverService
					.saveOrUpdateAssignApprover(assignApproverTO);

			if (status.equals(CommonConstants.SUCCESS)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						getMessageFromErrorBundle(request,
								LoginErrorCodeConstants.ASS_INFO_SAVED, null));
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						getMessageFromErrorBundle(request,
								LoginErrorCodeConstants.ASS_INFO_NOT_SAVED, null));
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in saveOrUpdateAssignApprover of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in saveOrUpdateAssignApprover of AssignApproverAction..."
					+ e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in saveOrUpdateAssignApprover of AssignApproverAction..."
					+ e.getLocalizedMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AssignApproverAction::saveOrUpdateAssignApprover::END------------>:::::::");
	}

	/**
	 * Load the Employee User list
	 * 
	 * @inputparam User type (E)
	 * @return Load the Employee User List into String object
	 * @author Rohini Maladi
	 */
	public void getUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("AssignApproverAction::getUsers::START------------>:::::::");
		String userType = "";
		Map<Integer, String> users = null;
		java.io.PrintWriter out = null;
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			userType = request.getParameter("userType");
			assignApproverService = getAssignApproverService();
			users = assignApproverService.getUsersByType(userType);
			if (!CGCollectionUtils.isEmpty(users)) {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						users.toString());
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								LoginErrorCodeConstants.ASS_DTLS_NOT_FOUND,
								new String[] { UmcConstants.ASSIGN_USER }));
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getUsers of AssignApproverAction..."
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.SUCCESS_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getUsers of AssignApproverAction..."
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.SUCCESS_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getUsers of AssignApproverAction..."
					+ e.getMessage());
			String exception = ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {

			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("AssignApproverAction::getUsers::END------------>:::::::");
	}

}
