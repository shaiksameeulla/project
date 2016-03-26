package com.ff.umc.service;

/**
 * Author : Narasimha Rao Kattunga
 * @Class : RoleManagementServiceImpl
 * @Desc : Implementation Services for  UMC - Role Management process
 * Creation Date : Nov - 05 - 2012
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.umc.ApplRightsDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.UserRolesDO;
import com.ff.umc.ApplRightsTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.UserRolesTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.RoleManagementDAO;

public class RoleManagementServiceImpl implements RoleManagementService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementServiceImpl.class);
	private RoleManagementDAO userRolesDAO;
	private RoleManagementCommonService userRolesCommonService;

	public RoleManagementDAO getUserRolesDAO() {
		return userRolesDAO;
	}

	public void setUserRolesDAO(RoleManagementDAO userRolesDAO) {
		this.userRolesDAO = userRolesDAO;
	}

	public RoleManagementCommonService getUserRolesCommonService() {
		return userRolesCommonService;
	}

	public void setUserRolesCommonService(
			RoleManagementCommonService userRolesCommonService) {
		this.userRolesCommonService = userRolesCommonService;
	}

	/**
	 * @Method : saveOrUpdateUserRoles
	 * @param : UserRolesTO userRolesTO
	 * @Desc : For User role Saving / Updation
	 * @return :boolean isRolesAdded
	 */
	@Override
	public boolean saveOrUpdateUserRoles(UserRolesTO userRolesTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementServiceImpl::saveOrUpdateUserRoles::START------------>:::::::");
		boolean isRolesAdded = Boolean.FALSE;
		UserRolesDO userRole = null;
		try {
			userRole = userRolesDomainConvertor(userRolesTO);
			isRolesAdded = userRolesDAO.saveOrUpdateUserRoles(userRole);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementServiceImpl :: saveOrUpdateUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementServiceImpl::saveOrUpdateUserRoles::END------------>:::::::");
		return isRolesAdded;

	}

	/**
	 * @Method : getUserRoles
	 * @param : -
	 * @Desc : For getting all user roles
	 * @return : List<UserRolesTO>
	 */
	@Override
	public List<UserRolesTO> getUserRoles() throws CGBusinessException,
			CGSystemException {
		return userRolesCommonService.getUserRoles();
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
		return userRolesCommonService.getUserRoles(roleType);
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
		return userRolesCommonService.getUserRoleById(roleId);
	}

	/**
	 * @Method : getUserRights
	 * @param : -
	 * @Desc : For getting all user rights
	 * @return : List<ApplRightsTO>
	 */
	@Override
	public List<ApplRightsTO> getUserRights() throws CGBusinessException,
			CGSystemException {
		return userRolesCommonService.getUserRights();
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
		return userRolesCommonService.getUserRights(roleId);
	}

	/**
	 * @Method : getApplScreens
	 * @param : accessibleTo
	 * @Desc : For getting all accessible screens (FFCL,Customer and Both)
	 * @return : List<ApplScreensTO>
	 */
	@Override
	public List<ApplScreensTO> getApplScreens(String accessibleTo)
			throws CGBusinessException, CGSystemException {
		return userRolesCommonService.getApplScreens(accessibleTo);
	}

	/**
	 * @Method : activateDeactivateUserRole
	 * @param : UserRolesTO userRole
	 * @Desc : For activating / deactivating user roles
	 * @return : updateStatus
	 */
	public String activateDeactivateUserRole(UserRolesTO userRoles)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RoleManagementServiceImpl::activateDeactivateUserRole::START------------>:::::::");
		String updateStatus = UmcConstants.FAILURE;
		String isRoleAssigned = "";
		boolean isRoleUpdated = Boolean.FALSE;
		try {
			if (StringUtils.equalsIgnoreCase(UmcConstants.USER_ROLES_INACTIVE,
					userRoles.getStatus())) {
				isRoleAssigned = userRolesCommonService
						.isRoleAssigned(userRoles.getRoleId());
				if (!StringUtils.equalsIgnoreCase(isRoleAssigned,
						UmcConstants.USER_ROLES_NOT_ASSIGNED)) {
					updateStatus = UmcConstants.USER_ROLES_ASSIGNED + "#"
							+ isRoleAssigned;
					return updateStatus;
				}
			}
			List<Integer> roleIds = new ArrayList<Integer>(1);
			roleIds.add(userRoles.getRoleId());
			isRoleUpdated = userRolesDAO.activateDeActivateUserRoles(roleIds,
					userRoles.getStatus());
			if (isRoleUpdated)
				updateStatus = UmcConstants.SUCCESS;

		} catch (CGSystemException e) {
			LOGGER.error("Exception in RoleManagementServiceImpl :: activateDeactivateUserRole() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in RoleManagementServiceImpl :: activateDeactivateUserRole() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("RoleManagementServiceImpl::activateDeactivateUserRole::END------------>:::::::");
		return updateStatus;
	}

	/**
	 * @Method : userRolesDomainConvertor
	 * @param : UserRolesTO userRolesTO
	 * @Desc : User Role TO - DO Convertor
	 * @return : UserRolesDO
	 * @throws CGSystemException
	 */
	private UserRolesDO userRolesDomainConvertor(UserRolesTO userRolesTO)
			throws CGSystemException {
		LOGGER.trace("RoleManagementServiceImpl::userRolesDomainConvertor::START------------>:::::::");
		//Created to keep newly assigned screens
		Set<ApplRightsDO> applRights = new HashSet<ApplRightsDO>();
		
		UserRolesDO userRoleDO = null;
		if (userRolesTO != null) {
			
			userRoleDO = new UserRolesDO();
			
			//Set role id in case of existing role
			if (userRolesTO.getRoleId() != null && userRolesTO.getRoleId() > 0) {
				userRoleDO.setRoleId(userRolesTO.getRoleId());
				userRoleDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
				// userRolesDAO.deleteApplRights(userRolesTO.getRoleId());
			} else {
				userRoleDO.setTransactionCreateDate(Calendar.getInstance().getTime());
				userRoleDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
			}
			//Setting role parameters
			userRoleDO.setRoleName(userRolesTO.getRoleName());
			userRoleDO.setRoleDesc(userRolesTO.getRoleDesc());
			userRoleDO.setRoleType(userRolesTO.getRoleType());
			userRoleDO.setStatus(UmcConstants.USER_ROLES_ACTIVE);
			userRoleDO.setDtToBranch("N");
			
			userRoleDO.setUserId(userRolesTO.getUserId());
			userRoleDO.setUpdatedBy(userRolesTO.getUserId());

			//Getting all screen ids in the application
			Integer[] sceenId = userRolesTO.getApplScreenId();
			
			//Getting all the assigned screens
			String[] isAssigned = userRolesTO.getIsRightAssigned();
	
			//Looping through all the screens
			for (int cnt = 0; cnt < sceenId.length; cnt++) {
				//Checking if screen is assigned or not
				if (sceenId[cnt] > 0 && StringUtils.equalsIgnoreCase(CommonConstants.YES, isAssigned[cnt])) {
					//Created and populated aaps right DO for assigned screens
					ApplRightsDO applRight = new ApplRightsDO();
					applRight.setUserId(userRolesTO.getUserId());
					applRight.setUpdatedBy(userRolesTO.getUserId());
					applRight.setDtToBranch("N");
					applRight.setStatus(UmcConstants.USER_ROLES_ACTIVE);
					applRight.setRolesTypeDO(userRoleDO);
					applRight.setTransactionCreateDate(Calendar.getInstance().getTime());
					applRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
					
					//Created and populated aaps screen DO for assigned screens
					ApplScreenDO applScreen = new ApplScreenDO();
					applScreen.setScreenId(userRolesTO.getApplScreenId()[cnt]);
					applScreen.setDtToBranch("N");
					applRight.setAppscreenTypeDO(applScreen);
					applRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
					
					applRights.add(applRight);
					
					
					ApplRightsDO centralizedRight = getCentralizedRight(sceenId[cnt]);
					if(centralizedRight != null) {
						centralizedRight.setRolesTypeDO(userRoleDO);
						centralizedRight.setUserId(userRolesTO.getUserId());
						centralizedRight.setUpdatedBy(userRolesTO.getUserId());
						applRights.add(centralizedRight);
					}
					
				}
			}
			userRoleDO.setApplRights(applRights);
		}
		LOGGER.trace("RoleManagementServiceImpl::userRolesDomainConvertor::END------------>:::::::");
		return userRoleDO;
	}

	private ApplRightsDO  getCentralizedRight(Integer sceenId) throws CGSystemException {
		
		ApplScreenDO screenDO = userRolesDAO.getCenralizedScreensForScreen(sceenId);
		if(screenDO == null)
			return null;
		ApplRightsDO centralizedRight = new ApplRightsDO();
		centralizedRight.setDtToBranch("N");
		centralizedRight.setStatus(UmcConstants.USER_ROLES_ACTIVE);
		centralizedRight.setTransactionCreateDate(Calendar.getInstance().getTime());
		centralizedRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
		
		//Created and populated aaps screen DO for assigned screens
		centralizedRight.setAppscreenTypeDO(screenDO);
		centralizedRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
		
		return centralizedRight;
	}
	/**
	 * @Method : isRoleAssigned
	 * @param : Integer roleId
	 * @Desc : Check for whether role is assigned to user or not
	 * @return : boolean
	 */
	@Override
	public String isRoleAssigned(Integer roleId) throws CGBusinessException,
			CGSystemException {
		return userRolesCommonService.isRoleAssigned(roleId);
	}

	/**
	 * Checks if User role already exists
	 * 
	 * @param roleName
	 * @return : boolean
	 * @throws CGSystemException
	 * @Method : isUserRoleExists
	 * @Desc : Check for whether User role is exists not
	 */
	@Override
	public boolean isUserRoleExists(String roleName)
			throws CGBusinessException, CGSystemException {
		return userRolesDAO.isUserRoleExists(roleName);
	}

}
