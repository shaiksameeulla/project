package com.ff.umc.dao;

/**
 * @author nihsingh
 * @Class : RoleManagementDAOImpl
 * @Desc : Implementation for DAO Services for UMC - Role Management process
 * @Creation Date : Nov - 05 - 2012
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.ApplRightsDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRolesDO;
import com.ff.umc.constants.UmcConstants;


public class RoleManagementDAOImpl extends CGBaseDAO implements
		RoleManagementDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementDAOImpl.class);

	/**
	 * @Method : saveOrUpdateUserRoles
	 * @param : UserRolesDO userRole
	 * @Desc : For User role Saving / Updation
	 * @return :boolean isRolesAdded
	 */
	@Override
	public boolean saveOrUpdateUserRoles(UserRolesDO userRole)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::START------------>:::::::");
		boolean isUserRolesAdded = Boolean.FALSE;
		Session session = null;
		Transaction transaction = null;
		UserRolesDO existingRols  = null;
		
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			// For Update
			boolean isSave = Boolean.FALSE;
			
			if(StringUtil.isEmptyInteger(userRole.getRoleId())){
				isSave = Boolean.TRUE;
				LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::New Role: " + isSave);
			}
			
			// If Role ID Exists then the procedure will go for update with respect to Role Id
			if (userRole != null && !StringUtil.isEmptyInteger(userRole.getRoleId())) {
				
				// Get existing role right assignment
				existingRols = (UserRolesDO) session.load(UserRolesDO.class, userRole.getRoleId());
			
				//map of new appRights
				Map<Integer, ApplRightsDO> newAppRightsMap = new HashMap<Integer, ApplRightsDO>();
				for (ApplRightsDO applRight : userRole.getApplRights()) {
					Integer screenId = applRight.getAppscreenTypeDO().getScreenId();
					newAppRightsMap.put(screenId, applRight);
				}
				
				
				for(ApplRightsDO oldApplRightDO : existingRols.getApplRights()){
					
					if(newAppRightsMap.containsKey(oldApplRightDO.getAppscreenTypeDO().getScreenId())){//if old exists in new
						//if(oldApplRightDO.getStatus().equals("I")){//if old is inactive
						oldApplRightDO.setStatus("A");//then set as Active
						oldApplRightDO.setDtToBranch("N");//set DtToBranch as N
						//}
						newAppRightsMap.remove(oldApplRightDO.getAppscreenTypeDO().getScreenId());//remove from new MAP
					} else {//if(!newAppRightsMap.containsKey(oldApplRightDO.getAppscreenTypeDO().getScreenId())){//if old does not exists in new
						//if(oldApplRightDO.getStatus().equals("A")){//if old is active
						oldApplRightDO.setStatus("I");//set as inactive
						oldApplRightDO.setDtToBranch("N");//set DtToBranch as N
						//}
					}
				}
				
				existingRols.getApplRights().addAll(newAppRightsMap.values());
				
				LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::Updating Role ID: " + existingRols.getRoleId() + "::START---->");
				session.saveOrUpdate(existingRols);
				LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::Updating Role ID: " + existingRols.getRoleId() + "::END---->");
				
		}
		if(isSave){
			LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::Saving New Role::START---->");
			session.saveOrUpdate(userRole);		
			LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::Saving New Role::END---->");
		}
		
		isUserRolesAdded = Boolean.TRUE;
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			LOGGER.error("Error occured in RoleManagementDAOImpl :: saveOrUpdateUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("RoleManagementDAOImpl::saveOrUpdateUserRoles::END------------>:::::::");
		return isUserRolesAdded;
	}

	/**
	 * @Method : deleteApplRights
	 * @param : Integer userRoleId
	 * @Desc : For deleting the application rights
	 * @return :boolean isDeleted
	 */
	public boolean deleteApplRights(Integer userRoleId)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::deleteApplRights::START------------>:::::::");
		Session session = null;
		boolean isDeleted = Boolean.FALSE;
		try {
			if (userRoleId != null && userRoleId > 0) {
				session = getHibernateTemplate().getSessionFactory()
						.openSession();
				Query query = session
						.getNamedQuery(UmcConstants.QRY_DELETE_APPL_RIGHTS);
				query.setInteger(UmcConstants.ROLE_ID, userRoleId);
				query.executeUpdate();
				isDeleted = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: deleteApplRights()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}finally {
			closeSession(session);
		}
		LOGGER.trace("RoleManagementDAOImpl::deleteApplRights::END------------>:::::::");
		return isDeleted;
	}

	/**
	 * @Method : activateDeActivateUserRoles
	 * @param : List<Integer> userRoleIds,statusType
	 * @Desc : For activating / deactivating user roles
	 * @return : boolean isUpdated
	 */
	@Override
	public boolean activateDeActivateUserRoles(List<Integer> userRoleIds,
			String statusType) throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::activateDeActivateUserRoles::START------------>:::::::");
		Session session = null;
		String status = CommonConstants.EMPTY_STRING;
		boolean isUpdated = Boolean.FALSE;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(UmcConstants.QRY_UPDATE_USER_ROLES);
			if (StringUtils.equalsIgnoreCase(UmcConstants.USER_ROLES_ACTIVE,
					statusType))
				status = UmcConstants.USER_ROLES_ACTIVE;
			else if (StringUtils.equalsIgnoreCase(
					UmcConstants.USER_ROLES_INACTIVE, statusType))
				status = UmcConstants.USER_ROLES_INACTIVE;
			query.setString(UmcConstants.STATUS, status);
			query.setParameterList(UmcConstants.USER_ROLE_IDS, userRoleIds);
			query.executeUpdate();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: activateDeActivateUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::activateDeActivateUserRoles::END------------>:::::::");
		return isUpdated;
	}

	/**
	 * @Method : getUserRoles
	 * @param : -
	 * @Desc : For Getting all user roles
	 * @return : List<UserRolesDO> userRoles
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRolesDO> getUserRoles() throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getUserRoles::START------------>:::::::");
		List<UserRolesDO> userRoles = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRoles = (List<UserRolesDO>) session.createCriteria(
					UserRolesDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getUserRoles::END------------>:::::::");
		return userRoles;
	}

	/**
	 * @Method : getUserRoles
	 * @param : String roleType
	 * @Desc : For Getting all user roles
	 * @return : List<UserRolesDO> userRoles
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRolesDO> getUserRoles(String roleType)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getUserRoles::START------------>:::::::");
		List<UserRolesDO> userRoles = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRoles = (List<UserRolesDO>) session
					.createCriteria(UserRolesDO.class)
					.add(Restrictions.eq(UmcConstants.ROLE_TYPE, roleType))
					.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getUserRoles::END------------>:::::::");
		return userRoles;
	}

	/**
	 * @Method : getUserRoles
	 * @param : String roleType
	 * @Desc : For Getting all user roles
	 * @return : List<UserRolesDO> userRoles
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRolesDO> getActiveUserRoles(String roleType)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::START------------>:::::::");
		List<UserRolesDO> userRoles = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRoles = (List<UserRolesDO>) session
					.createCriteria(UserRolesDO.class)
					.add(Restrictions.eq(UmcConstants.ROLE_TYPE, roleType))
					.add(Restrictions.eq(UmcConstants.STATUS,
							UmcConstants.USER_ROLES_ACTIVE)).list();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getActiveUserRoles()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::END------------>:::::::");
		return userRoles;
	}

	/**
	 * @Method : getUserRoleById
	 * @param : Integer roleId
	 * @Desc : For Getting user role by id
	 * @return : List<UserRolesDO> userRoles
	 */
	@Override
	public UserRolesDO getUserRoleById(Integer roleId) throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::START------------>:::::::");
		UserRolesDO userRole = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRole = (UserRolesDO) session.createCriteria(UserRolesDO.class)
					.add(Restrictions.eq(UmcConstants.ROLE_ID, roleId))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRoleById()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::END------------>:::::::");
		return userRole;
	}

	/**
	 * @Method : getUserRights
	 * @param : Integer roleId
	 * @Desc : For Getting user rights for the role
	 * @return : List<ApplRightsDO> userRights
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplRightsDO> getUserRights(Integer roleId)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::START------------>:::::::");
		List<ApplRightsDO> userRights = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRights = (List<ApplRightsDO>) session
					.createCriteria(ApplRightsDO.class)
					.add(Restrictions.eq(UmcConstants.ROLE_ID_1, roleId))
					 .add(Restrictions.eq(UmcConstants.STATUS,UmcConstants.USER_ROLES_ACTIVE))
					.list();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRights()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::END------------>:::::::");
		return userRights;
	}

	/**
	 * @Method : getApplScreens
	 * @param : String accessibleTo
	 * @Desc : For accessible user screens
	 * @return : List<ApplScreenDO> applScreens
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplScreenDO> getApplScreens(String accessibleTo)
			throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::START------------>:::::::");
		List<ApplScreenDO> applScreens = null;
		List<String> assignedTO = new ArrayList<String>(2);
		try {
			assignedTO.add(UmcConstants.SRC_ACCESS_BOTH);
			assignedTO.add(accessibleTo);
			applScreens = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_ACCESS_USER_SCREENS,
					UmcConstants.ACCESSIBLE, assignedTO);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getApplScreens()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementDAOImpl::getActiveUserRoles::END------------>:::::::");
		return applScreens;
	}

	/**
	 * @Method : getUserRights
	 * @param : -
	 * @Desc : Getting all user rights
	 * @return : List<ApplRightsDO>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplRightsDO> getUserRights() throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::getUserRights::START------------>:::::::");
		List<ApplRightsDO> userRights = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			userRights = (List<ApplRightsDO>) session.createCriteria(
					ApplRightsDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRights()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			session.flush();
			session.close();
		}
		LOGGER.trace("RoleManagementDAOImpl::getUserRights::END------------>:::::::");
		return userRights;
	}

	/**
	 * @Method : isRoleAssigned
	 * @param : Integer roleId
	 * @Desc : Check for whether role is assigned to user or not
	 * @return : boolean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> isRoleAssigned(Integer roleId) throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::isRoleAssigned::START------------>:::::::");
		List<UserDO> roleAssignedUsers = null;
		try {
			roleAssignedUsers = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UmcConstants.QRY_IS_ROLE_ASSIGNED_TO_USER,
							UmcConstants.ROLE_ID, roleId);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: isRoleAssigned()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementDAOImpl::isRoleAssigned::END------------>:::::::");
		return roleAssignedUsers;
	}

	/**
	 * Checks if User role already exists
	 * 
	 * @param roleName
	 * @return : boolean
	 * @throws CGBaseException
	 * @Method : isUserRoleExists
	 * @Desc : Check for whether User role is exists not
	 */
	@Override
	public boolean isUserRoleExists(String roleName) throws CGSystemException {
		LOGGER.trace("RoleManagementDAOImpl::isUserRoleExists::START------------>:::::::");
		long count = 0;
		boolean isRoleEsists = Boolean.FALSE;
		try {
			count = (Long) getHibernateTemplate()
					.findByNamedQueryAndNamedParam("isUserRoleExists",
							"roleName", roleName).get(0);
			isRoleEsists = count > 0 ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: isUserRoleExists()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RoleManagementDAOImpl::isUserRoleExists::END------------>:::::::");
		return isRoleEsists;
	}
	
	@SuppressWarnings("unchecked")
	public ApplScreenDO getCenralizedScreensForScreen(Integer screenId) throws CGSystemException {
		ApplScreenDO screen = null;
		try {
			/*session = getHibernateTemplate().getSessionFactory().openSession();
			session.createCriteria(	ApplScreenDO.class);*/
			List<ApplScreenDO> screens = getHibernateTemplate().findByNamedQueryAndNamedParam("getCenralizedScreensForScreen", "screenId", screenId);
			if(!screens.isEmpty())
				screen = screens.get(0);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleManagementDAOImpl :: getUserRights()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return screen;
	}
}
