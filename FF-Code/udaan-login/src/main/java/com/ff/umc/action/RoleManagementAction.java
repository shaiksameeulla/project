package com.ff.umc.action;

/**
 * Author : Narasimha Rao Kattunga
 * @Class : RoleManagementAction
 * @Desc : Actions For UMC - Role Management process
 * Creation Date : Nov - 05 - 2012
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.umc.ApplRightsTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserRolesTO;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.UserManagementRoleForm;
import com.ff.umc.service.RoleManagementService;

public class RoleManagementAction extends CGBaseAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementAction.class);
	private RoleManagementService userRoleService = null;
	public transient JSONSerializer serializer;

	/**
	 * @Method : addUserRoles
	 * @param : UserRolesTO
	 * @Desc : For Creating / Showing User roles and rights
	 * @return : Forwarding to addUserRoles page
	 */
	public ActionForward addUserRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException {
		Integer roleId = 0;
		try {
			LOGGER.debug("RoleManagementAction:addUserRoles..Start");
			List<ApplScreensTO> applScreens = null;
			List<UserRolesTO> userRoles = null;
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			UserManagementRoleForm userRolesForm = (UserManagementRoleForm) form;
			UserRolesTO userRolesTO = (UserRolesTO) userRolesForm.getTo();
			if (StringUtils.isNotEmpty(userRolesTO.getRoleType())) {
				applScreens = userRoleService.getApplScreens(userRolesTO
						.getRoleType());
				Collections.sort(applScreens);
				userRoles = userRoleService.getUserRoles(userRolesTO
						.getRoleType());
			}
			if (!StringUtil.isNull(request.getAttribute("roleName"))) {
				String roleName = (String) request.getAttribute("roleName");
				if (!StringUtil.isEmptyList(userRoles)) {
					for (UserRolesTO userRole : userRoles) {
						if (StringUtils.equalsIgnoreCase(roleName,
								userRole.getRoleName())) {
							roleId = userRole.getRoleId();
							break;
						}
					}
				}
			}
			request.setAttribute(UmcConstants.APPL_SCREENS, applScreens);
			request.setAttribute(UmcConstants.USER_ROLES, userRoles);
			request.setAttribute(UmcConstants.ROLE_TYPE,
					userRolesTO.getRoleType());
			request.setAttribute(UmcConstants.USER_ROLE_ID, roleId);
			// request.setAttribute("trasnMode", "ADD");
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleManagementAction :: addUserRoles() ::"
					+ e.getMessage());
		}
		LOGGER.debug("RoleManagementAction:addUserRoles..END");
		return mapping.findForward(UmcConstants.ADD_USER_ROLES);
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : To get the user roles based on selected role type
	 * @return : userRolesJSON json Object
	 */
	@SuppressWarnings("static-access")
	public void getUserRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		LOGGER.debug("RoleManagementAction:getUserRoles..Start");
		List<UserRolesTO> userRoles = null;
		String roleType = CommonConstants.EMPTY_STRING;
		String userRolesJSON = CommonConstants.EMPTY_STRING;
		try {
			roleType = request.getParameter(UmcConstants.ROLE_TYPE);
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			userRoles = userRoleService.getUserRoles(roleType);
			if (userRoles != null && userRoles.size() > 0) {
				Collections.sort(userRoles);
				serializer = CGJasonConverter.getJsonObject();
				userRolesJSON = serializer.toJSON(userRoles).toString();
				response.setContentType("text/javascript");
			} else
				userRolesJSON = UmcConstants.NOROLES;
			response.getWriter().write(userRolesJSON);
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleManagementAction :: getUserRoles() ::"
					+ e.getMessage());
		}
		LOGGER.debug("RoleManagementAction:getUserRoles..END");
	}

	/**
	 * @Method : saveOrUpdateUserRoles
	 * @param : UserRolesTO
	 * @Desc : For User Roles save / update
	 * @return : transStatus
	 */
	public ActionForward saveOrUpdateUserRoles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			IOException {
		LOGGER.trace("RoleManagementAction:: saveOrUpdateUserRoles..START");
		HttpSession session = null;
		String transStatus = UmcConstants.FAILURE;
		Boolean isRolesAdded=Boolean.FALSE;
		PrintWriter printWriter = response.getWriter();
		
		try {
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			UserManagementRoleForm userRolesForm = (UserManagementRoleForm) form;
			UserRolesTO userRolesTO = (UserRolesTO) userRolesForm.getTo();
			
			if(userRolesTO.getRoleId().equals(0)){
				
				Integer[] userRightsArray =userRolesTO.getUserRightIds();
				
				for (int i = 0; i < userRightsArray.length; i++) {
					userRightsArray[i] = 0;
				}
				userRolesTO.setUserRightIds(userRightsArray);
			}
			
			
			
			userRolesTO.setUserId(userInfoTO.getUserto().getUserId());
			isRolesAdded = userRoleService.saveOrUpdateUserRoles(userRolesTO);
			if (isRolesAdded) {
				transStatus = UmcConstants.SUCCESS;
			}
			// For Demo
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					userRolesTO.getIsUpdationMode()))
				request.setAttribute("trasnMode", "UPDATE");
			else
				request.setAttribute("trasnMode", "ADD");

			request.setAttribute("isUpdationMode",
					userRolesTO.getIsUpdationMode());
			request.setAttribute("roleName", userRolesTO.getRoleName());
			addUserRoles(mapping, userRolesForm, request, response);

		} catch (CGBaseException baseException) {
			LOGGER.error("Error occured in RoleManagementAction :: saveOrUpdateUserRoles()..:"
					, baseException);
		} finally {
			printWriter.print(transStatus);
			printWriter.flush();
		}
		
		LOGGER.trace("RoleManagementAction:: saveOrUpdateUserRoles..END");
		return mapping.findForward(UmcConstants.ADD_USER_ROLES);
	}

	/**
	 * @Method : activateDeactivateUserRole
	 * @param : UserRolesTO
	 * @Desc : For User Roles activate / deactivate
	 * @return : transStatus
	 */
	public void activateDeactivateUserRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			IOException {
		LOGGER.trace("RoleManagementAction:: activateDeactivateUserRole..START");
		String transStatus = CommonConstants.EMPTY_STRING;
		try {
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			UserManagementRoleForm userRolesForm = (UserManagementRoleForm) form;
			UserRolesTO userRolesTO = (UserRolesTO) userRolesForm.getTo();
			transStatus = userRoleService
					.activateDeactivateUserRole(userRolesTO);
			response.getWriter().write(transStatus);
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleManagementAction :: activateDeactivateUserRole()..:"
					+ e.getMessage());
		}
		LOGGER.trace("RoleManagementAction:: activateDeactivateUserRole..END");
	}

	/**
	 * @Method : getUserRights
	 * @param : userRoleId
	 * @Desc : For User Roles activate / deactivate
	 * @return : userRightsJSON json object
	 */
	@SuppressWarnings("static-access")
	public void getUserRights(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		LOGGER.trace("RoleManagementAction:: getUserRights..START");
		List<ApplRightsTO> userRights = null;
		Integer userRoleId = CommonConstants.ZERO;
		String userRightsJSON = CommonConstants.EMPTY_STRING;
		try {
			if (StringUtils.isNotEmpty(request
					.getParameter(UmcConstants.USER_ROLE_ID)))
				userRoleId = Integer.parseInt(request
						.getParameter(UmcConstants.USER_ROLE_ID));
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			userRights = userRoleService.getUserRights(userRoleId);
			if (userRights != null) {
				serializer = CGJasonConverter.getJsonObject();
				userRightsJSON = serializer.toJSON(userRights).toString();
				response.setContentType("text/javascript");
			} else
				userRightsJSON = UmcConstants.NO_USER_RIGHTS;
			response.getWriter().write(userRightsJSON);
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleManagementAction :: getUserRights()..:"
					+ e.getMessage());
		}
		LOGGER.trace("RoleManagementAction:: getUserRights..END");
	}

	/**
	 * @Method : getUserRights
	 * @param : userRoleId
	 * @Desc : For User Roles activate / deactivate
	 * @return : userRightsJSON json object
	 */
	public void isUserRoleExists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		LOGGER.trace("RoleManagementAction:: isUserRoleExists..START");
		Boolean isRoleExists = null;
		String userRoleName = CommonConstants.EMPTY_STRING;
		String isUserRoleExists = CommonConstants.NO;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			userRoleName = request.getParameter("userRoleName");
			userRoleService = (RoleManagementService) getBean(SpringConstants.USER_ROLE_SERVICE);
			isRoleExists = userRoleService.isUserRoleExists(userRoleName);
			if (isRoleExists)
				isUserRoleExists = CommonConstants.YES;
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleManagementAction :: isUserRoleExists()..:"
					+ e.getMessage());
			isUserRoleExists = CommonConstants.NO;
			
		} finally {
			out.print(isUserRoleExists);
			out.flush();
			out.close();
		}
		LOGGER.trace("RoleManagementAction:: isUserRoleExists..END");
	}
}
