package com.ff.umc.service;

/**
 * Author : Narasimha Rao Kattunga
 * @Class : RoleManagementCommonServiceImpl
 * @Desc : Implementation Common Services for  UMC - Role Management process
 * Creation Date : Nov - 05 - 2012
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.ApplRightsDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRolesDO;
import com.ff.umc.ApplRightsTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.UserRolesTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.RoleManagementDAO;
import com.ff.umc.dao.RoleManagementDAOImpl;

public class RoleManagementCommonServiceImpl implements
		RoleManagementCommonService {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementDAOImpl.class);
	private RoleManagementDAO userRolesDAO;

	public RoleManagementDAO getUserRolesDAO() {
		return userRolesDAO;
	}

	public void setUserRolesDAO(RoleManagementDAO userRolesDAO) {
		this.userRolesDAO = userRolesDAO;
	}

	/**
	 * @Method : getUserRoles
	 * @param : -
	 * @Desc : For getting all user roles
	 * @return : List<UserRolesTO>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRolesTO> getUserRoles() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoles::START------------>:::::::");
		List<UserRolesTO> userRoleTOs = null;
		List<UserRolesDO> userRoleDOs = null;
		try {
			userRoleDOs = userRolesDAO.getUserRoles();
			if (userRoleDOs != null && userRoleDOs.size() > 0) {
				userRoleTOs = (List<UserRolesTO>) CGObjectConverter
						.createTOListFromDomainList(userRoleDOs,
								UserRolesTO.class);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getUserRoles() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getUserRoles() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoles::END------------>:::::::");
		return userRoleTOs;
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : For getting all user roles
	 * @return : List<UserRolesTO>
	 */
	@Override
	public List<UserRolesTO> getUserRoles(String roleType)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoles::START------------>:::::::");
		List<UserRolesTO> userRoleTOs = null;
		List<UserRolesDO> userRoleDOs = null;
		try {
			userRoleDOs = userRolesDAO.getUserRoles(roleType);
			if (!CGCollectionUtils.isEmpty(userRoleDOs)) {
				userRoleTOs=new ArrayList<>(userRoleDOs.size());
				for(UserRolesDO rolesDO :userRoleDOs){
					UserRolesTO roleTO=new UserRolesTO();
					CGObjectConverter.createToFromDomain(rolesDO, roleTO);
					userRoleTOs.add(roleTO);
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRoles() :",
					e);
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRoles() :",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoles::END------------>:::::::");
		return userRoleTOs;
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : For getting all user roles
	 * @return : List<UserRolesTO>
	 */
	@Override
	public UserRolesTO getUserRoleById(Integer roleId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoleById::START------------>:::::::");
		UserRolesTO userRoleTO = null;
		UserRolesDO userRoleDO = null;
		try {
			userRoleDO = userRolesDAO.getUserRoleById(roleId);
			if (userRoleDO != null) {
				userRoleTO = (UserRolesTO) CGObjectConverter
						.createToFromDomain(userRoleDO, userRoleTO);
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRoleById() :",
					e);
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRoleById() :",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoleById::END------------>:::::::");
		return userRoleTO;
	}

	/**
	 * @Method : getUserRights
	 * @param : -
	 * @Desc : For getting all user rights
	 * @return : List<ApplRightsTO>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplRightsTO> getUserRights() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoleById::START------------>:::::::");
		List<ApplRightsTO> userRightsTOs = null;
		List<ApplRightsDO> userRightsDOs = null;
		try {
			userRightsDOs = userRolesDAO.getUserRights();
			if (userRightsDOs != null && userRightsDOs.size() > 0) {
				userRightsTOs = (List<ApplRightsTO>) CGObjectConverter
						.createTOListFromDomainList(userRightsDOs,
								ApplRightsTO.class);
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRights() :",
					e);
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception in RoleManagementCommonServiceImpl :: getUserRights() :",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRoleById::END------------>:::::::");
		return userRightsTOs;
	}

	/**
	 * @Method : getApplScreens
	 * @param : accessibleTo
	 * @Desc : For getting all accessible screens (FFCL,Customer and Both)
	 * @return : List<ApplScreensTO>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplScreensTO> getApplScreens(String accessibleTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getApplScreens::START------------>:::::::");
		List<ApplScreenDO> applScreens = null;
		List<ApplScreensTO> applScreenTOs = null;
		try {
			applScreens = userRolesDAO.getApplScreens(accessibleTo);
			applScreenTOs = new ArrayList<ApplScreensTO>(applScreens.size());
			if (applScreens != null && applScreens.size() > 0) {
				applScreenTOs = (List<ApplScreensTO>) CGObjectConverter
						.createTOListFromDomainList(applScreens,
								ApplScreensTO.class);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getApplScreens() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getApplScreens() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getApplScreens::END------------>:::::::");
		return applScreenTOs;
	}

	/**
	 * @Method : getUserRights
	 * @param : roleId
	 * @Desc : For getting all user rights based on user role id
	 * @return : List<ApplRightsTO>
	 */
	@Override
	public List<ApplRightsTO> getUserRights(Integer roleId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRights::START------------>:::::::");
		List<ApplRightsDO> userRightsDOs = null;
		List<ApplRightsTO> userRights = null;
		try {
			userRightsDOs = userRolesDAO.getUserRights(roleId);
			userRights = new ArrayList<ApplRightsTO>(userRightsDOs.size());
			if (userRightsDOs != null && userRightsDOs.size() > 0) {
				for (ApplRightsDO userRight : userRightsDOs) {
					ApplRightsTO applRight = new ApplRightsTO();
					applRight.setRightsId(userRight.getRightsId());
					applRight.setScreenId(userRight.getAppscreenTypeDO()
							.getScreenId());
					applRight.setUserRoleId(userRight.getRolesTypeDO()
							.getRoleId());
					applRight.setUserRoleName(userRight.getRolesTypeDO()
							.getRoleName());
					applRight.setUserRoleDesc(userRight.getRolesTypeDO()
							.getRoleDesc());
					userRights.add(applRight);
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getUserRights() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getUserRights::END------------>:::::::");
		return userRights;
	}

	/**
	 * @Method : isRoleAssigned
	 * @param : Integer roleId
	 * @Desc : Check for whether role is assigned to user or not
	 * @return : boolean
	 */
	public String isRoleAssigned(Integer roleId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::isRoleAssigned::START------------>:::::::");
		List<UserDO> roleAssignedUsers = null;
		StringBuffer users = new StringBuffer();
		try {
			roleAssignedUsers = userRolesDAO.isRoleAssigned(roleId);
			if (!StringUtil.isEmptyList(roleAssignedUsers)) {
				for (UserDO user : roleAssignedUsers) {
					users.append(user.getUserName());
					if (roleAssignedUsers.size() > 1)
						users.append(CommonConstants.COMMA);
				}
			} else {
				users.append(UmcConstants.USER_ROLES_NOT_ASSIGNED);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: isRoleAssigned() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::isRoleAssigned::END------------>:::::::");
		return users.toString();
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : For getting all user roles
	 * @return : List<UserRolesTO>
	 */
	@SuppressWarnings("unchecked")
	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementCommonServiceImpl::getActiveUserRoles::START------------>:::::::");
		List<UserRolesTO> userRoleTOs = null;
		List<UserRolesDO> userRoleDOs = null;
		try {
			userRoleDOs = userRolesDAO.getActiveUserRoles(roleType);
			if (userRoleDOs != null && userRoleDOs.size() > 0) {
				userRoleTOs = (List<UserRolesTO>) CGObjectConverter
						.createTOListFromDomainList(userRoleDOs,
								UserRolesTO.class);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementCommonServiceImpl :: getActiveUserRoles() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementCommonServiceImpl::getActiveUserRoles::END------------>:::::::");
		return userRoleTOs;
	}

}
