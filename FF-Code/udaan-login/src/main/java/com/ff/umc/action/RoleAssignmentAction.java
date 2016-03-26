package com.ff.umc.action;

import java.io.IOException;
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
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.OfficeTO;
import com.ff.umc.RoleAssignmentTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserOfficeRightsMappingTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserRolesTO;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.UserRoleAssignmentForm;
import com.ff.umc.service.RoleAssignmentService;

public class RoleAssignmentAction extends CGBaseAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleAssignmentAction.class);
	private RoleAssignmentService roleAssignmentSerevice = null;
	public transient JSONSerializer serializer;
	

	/**
	 * @Method : addRoleAssignments
	 * @param : UserRolesTO
	 * @Desc : For Creating / Showing User roles and rights
	 * @return : Forwarding to addUserRoles page
	 */
	public ActionForward assignUserRoles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		try {
			LOGGER.debug("RoleAssignmentAction:assignUserRoles..Start");
			roleAssignmentSerevice = (RoleAssignmentService) getBean(SpringConstants.USER_ROLE_ASSIGNMENT_SERVICE);

		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentAction :: assignUserRoles() ::"
					+ e.getMessage());
		}
		LOGGER.debug("RoleAssignmentAction:assignUserRoles..END");
		return mapping.findForward(UmcConstants.ASSIGN_USER_ROLES);

	}

	/**
	 * @Method : save Role assignments
	 * @param : UserRolesTO
	 * @Desc : For Creating / Showing User roles and rights
	 * @return : Forwarding to addUserRoles page
	 * @throws IOException
	 */
	public void saveOrUpdateRoleAssignments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException, IOException {
		String trsnsStatus = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			LOGGER.trace("RoleAssignmentAction:saveOrUpdateRoleAssignments..Start");
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			UserRoleAssignmentForm userRightsForm = (UserRoleAssignmentForm) form;
			RoleAssignmentTO roleAssignmentTO = (RoleAssignmentTO) userRightsForm
					.getTo();
			roleAssignmentTO.setLoginUserId(userInfoTO.getUserto().getUserId());
			roleAssignmentSerevice = (RoleAssignmentService) getBean(SpringConstants.USER_ROLE_ASSIGNMENT_SERVICE);
			trsnsStatus = roleAssignmentSerevice
					.saveOrUpdateRoleAssignments(roleAssignmentTO);

		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentAction :: saveOrUpdateRoleAssignments() ::"
					+ e.getMessage());
		}
		LOGGER.trace("RoleAssignmentAction:saveOrUpdateRoleAssignments..END");
		response.getWriter().write(trsnsStatus);

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
		LOGGER.trace("RoleAssignmentAction:getUserRoles..Start");
		List<UserRolesTO> userRoles = null;
		String roleType = CommonConstants.EMPTY_STRING;
		String userRolesJSON = CommonConstants.EMPTY_STRING;
		try {
			roleType = request.getParameter(UmcConstants.ROLE_TYPE);
			roleAssignmentSerevice = (RoleAssignmentService) getBean(SpringConstants.USER_ROLE_ASSIGNMENT_SERVICE);
			userRoles = roleAssignmentSerevice.getActiveUserRoles(roleType);
			if (userRoles != null && userRoles.size() > 0) {
				Collections.sort(userRoles);
				serializer = CGJasonConverter.getJsonObject();
				userRolesJSON = serializer.toJSON(userRoles).toString();
				response.setContentType("text/javascript");
			} else
				userRolesJSON = UmcConstants.NOROLES;
			response.getWriter().write(userRolesJSON);
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleAssignmentAction :: getUserRoles() ::"
					+ e.getMessage());
		}
		LOGGER.trace("RoleAssignmentAction:getUserRoles..END");
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : To get the user roles based on selected role type
	 * @return : userRolesJSON json Object
	 */
	@SuppressWarnings("static-access")
	public void getUserDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		LOGGER.trace("RoleAssignmentAction:getUserDetails..Start");
		List<UserRightsTO> userRights = null;
		String roleType = CommonConstants.EMPTY_STRING;
		String userCode = CommonConstants.EMPTY_STRING;
		StringBuffer jsonObjects = new StringBuffer();
		UserRoleAssignmentForm userRoleAssignmntForm = (UserRoleAssignmentForm) form;
		RoleAssignmentTO roleAssignmntTO= null;
		try {
			roleType = request.getParameter(UmcConstants.ROLE_TYPE);
			userCode = request.getParameter(UmcConstants.USER_CODE);
			roleAssignmntTO=(RoleAssignmentTO)userRoleAssignmntForm.getTo();
			roleAssignmentSerevice = (RoleAssignmentService) getBean(SpringConstants.USER_ROLE_ASSIGNMENT_SERVICE);
			
			userRights = roleAssignmentSerevice.getUserDetails(userCode,
					roleType,roleAssignmntTO);
			if (userRights != null && userRights.size() > 0) {
				Collections.sort(userRights);
				List<UserOfficeRightsMappingTO> usrMappings = userRights.get(0)
						.getUserMappings();
				Collections.sort(usrMappings);
				String userRightsJSON = serializer.toJSON(userRights)
						.toString();
				jsonObjects = appendListName(jsonObjects, userRightsJSON,
						"userRights");
				if (usrMappings != null && usrMappings.size() > 0) {
					String userOfficeJSON = serializer.toJSON(usrMappings)
							.toString();
					jsonObjects = appendListName(jsonObjects, userOfficeJSON,
							"userOffices");
				}
			} else {
				jsonObjects.append(UmcConstants.INVALID_USER);
			}
			response.setContentType("text/javascript");
			response.getWriter().write(jsonObjects.toString());
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleAssignmentAction :: getUserDetails() ::"
					+ e.getMessage());
		}
	}

	/**
	 * @Method : getOfficeDetails
	 * @param : mappingType
	 * @Desc : To get the office details based on selected mapping type
	 * @return : officeObjJSON json object
	 */
	@SuppressWarnings("static-access")
	public void getOfficeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException {
		LOGGER.trace("RoleAssignmentAction:getOfficeDetails..Start");
		String mappingType = CommonConstants.EMPTY_STRING;
		String officeObjJSON = CommonConstants.EMPTY_STRING;
		List<OfficeTO> offices = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		Integer cityId = null;
		
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			mappingType = request.getParameter(UmcConstants.MAPPING_TYPE);
			
			cityId=Integer.parseInt(request.getParameter("userCityId"));
			
			roleAssignmentSerevice = (RoleAssignmentService) getBean(SpringConstants.USER_ROLE_ASSIGNMENT_SERVICE);
			if (StringUtils.equalsIgnoreCase(UmcConstants.USER_TYPE_FFCL,
					userInfoTO.getUserto().getUserType())) {
				//cityId = userInfoTO.getOfficeTo().getCityId();
			} else if (StringUtils.equalsIgnoreCase(
					UmcConstants.USER_TYPE_CUSTOMER, userInfoTO.getUserto()
							.getUserType())) {
				// Todo
				/*
				 * cityId = userInfoTO.getCustUserTo().getCustTO()
				 * .getMappedOffice();
				 */

			}

			if (StringUtils.equalsIgnoreCase(UmcConstants.MAPPED_AREA,
					mappingType)) {
				offices = roleAssignmentSerevice.getAllOfficesByCity(cityId);
			}
			if (StringUtils.equalsIgnoreCase(UmcConstants.MAPPED_RHO,
					mappingType)) {
				offices = roleAssignmentSerevice
						.getAllOfficesByType(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE);
			}
			if (offices != null && offices.size() > 0) {
				Collections.sort(offices);
				serializer = CGJasonConverter.getJsonObject();
				officeObjJSON = serializer.toJSON(offices).toString();
				response.setContentType("text/javascript");
			} else {
				officeObjJSON = "NOOFFICES";
			}
			response.getWriter().write(officeObjJSON);
		} catch (CGBaseException e) {
			LOGGER.error("Error occured in RoleAssignmentAction :: getOfficeDetails() ::"
					+ e.getMessage());
		}
		LOGGER.trace("RoleAssignmentAction:getOfficeDetails..END");
	}

	/**
	 * @Method : appendListName
	 * @param : StringBuffer stringBuffer,String ajaxResponse, String listName
	 * @Desc : To append the list names
	 * @return : StringBuffer stringBuffer
	 */

	private StringBuffer appendListName(StringBuffer stringBuffer,
			String ajaxResponse, String listName) {
		stringBuffer
				.append(CommonConstants.TILD)
				.append(CommonConstants.OPENING_CURLY_BRACE)
				.append(CommonConstants.OPENING_INNER_QOUTES)
				.append(listName)
				.append(CommonConstants.CLOSING_INNER_QOUTES)
				.append(CommonConstants.CHARACTER_COLON).append(ajaxResponse)
				.append(CommonConstants.CLOSING_CURLY_BRACE);

		return stringBuffer;
	}
}
