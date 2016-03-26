package com.ff.umc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserOfficeRightsMappingDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.umc.constants.UmcConstants;

public class RoleAssignmentDAOImpl extends CGBaseDAO implements
		RoleAssignmentDAO {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementDAOImpl.class);

	/**
	 * @Method : saveOrUpdateRoleAssignments
	 * @param : UserRolesDO userRole
	 * @Desc : For User role Saving / Updation
	 * @return :boolean isRolesAdded
	 */
	@Override
	public boolean saveOrUpdateRoleAssignments(UserDO user)
			throws CGBaseException {
		boolean isUserRightsAdded = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		List<UserRightsDO> userRightlist = null;
		List<UserOfficeRightsMappingDO> userOfficelist =null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			boolean isSave = Boolean.FALSE;
			
			// For Update
			UserDO userDO = (UserDO) session.load(UserDO.class,
					user.getUserId());
			Set<UserRightsDO> newUserRight =  userDO.getUserRights();
			for (UserRightsDO userRight : newUserRight) {
				userRightlist = new ArrayList<UserRightsDO>();
				userRightlist.add(userRight);
			}
			
		
			Set<UserOfficeRightsMappingDO> newUserOfficeMapping = userDO.getUserOfficeRightMappings();
			for (UserOfficeRightsMappingDO userofficDO : newUserOfficeMapping) {
				userOfficelist =  new ArrayList<UserOfficeRightsMappingDO>();
				userOfficelist.add(userofficDO);
			}
			
			
		
			
			if(StringUtil.isNull(userRightlist)){
				isSave = Boolean.TRUE;
			}
			if (user != null && !StringUtil.isNull(userRightlist)) {
				 userDO = (UserDO) session.load(UserDO.class,
						user.getUserId());
				
			/*	for (UserRightsDO userRight : userDO.getUserRights()) {
					session.delete(userRight);
				}
				for (UserOfficeRightsMappingDO userOfficeMapping : userDO
						.getUserOfficeRightMappings()) {
					session.delete(userOfficeMapping);
				}
				*/
				
				List<Integer> newRoleIds = new ArrayList<Integer>(); 
				for (UserRightsDO userRightDO : user.getUserRights()) {
					Integer roleId = userRightDO.getRoleDO().getRoleId();
					newRoleIds.add(roleId);
					
				}
				
				List<Integer> newOfficeIds = new ArrayList<Integer>(); 
				if(user.getUserOfficeRightMappings()!=null){
					for (UserOfficeRightsMappingDO userOfficRightMappingDO : user.getUserOfficeRightMappings()) {
						Integer officeId = userOfficRightMappingDO.getOffice().getOfficeId();
						newOfficeIds.add(officeId);
					
					}
				}
				
				List<Integer> oldRoleIds = new ArrayList<Integer>(); 
				List<Integer> oldOfficeIds = new ArrayList<Integer>(); 
			//checks if the previous screen is uncheckd then change the status to I and add to new userRole
				for (UserRightsDO userRight : userDO.getUserRights()) {
					Integer roleId = userRight.getRoleDO().getRoleId();
					oldRoleIds.add(roleId);
						//session.delete(applRight);
							if(!newRoleIds.contains(roleId)){
								
								if(!userRight.getStatus().equalsIgnoreCase(UmcConstants.USER_ROLES_INACTIVE)){
									userRight.setDtToBranch(UmcConstants.FLAG_N);
								}
								userRight.setStatus(UmcConstants.USER_ROLES_INACTIVE);
								user.getUserRights().add(userRight);
							}else if(newRoleIds.contains(roleId)&&userRight.getStatus().equalsIgnoreCase(UmcConstants.USER_ROLES_INACTIVE)){
								userRight.setStatus(UmcConstants.USER_ROLES_ACTIVE);
								user.getUserRights().add(userRight);
								userRight.setDtToBranch(UmcConstants.FLAG_N);
							}
							
				}
				
				
				for (UserOfficeRightsMappingDO userOfficeRightmappDO : userDO.getUserOfficeRightMappings()) {
					Integer officeId = userOfficeRightmappDO.getOffice().getOfficeId();
					oldOfficeIds.add(officeId);
						//session.delete(applRight);
							if(!newOfficeIds.contains(officeId)){
								
								if(!userOfficeRightmappDO.getStatus().equalsIgnoreCase(UmcConstants.USER_ROLES_INACTIVE)){
									userOfficeRightmappDO.setDtToBranch(UmcConstants.FLAG_N);
								}
								userOfficeRightmappDO.setStatus(UmcConstants.USER_ROLES_INACTIVE);
								if(user.getUserOfficeRightMappings()!=null){
									user.getUserOfficeRightMappings().add(userOfficeRightmappDO);
								}else if(user.getUserOfficeRightMappings()==null){
									Set<UserOfficeRightsMappingDO> newUserOfficeMap = new HashSet<UserOfficeRightsMappingDO>();
									user.setUserOfficeRightMappings(newUserOfficeMap);
									user.getUserOfficeRightMappings().add(userOfficeRightmappDO);
								}
							}else if(newOfficeIds.contains(officeId)&&userOfficeRightmappDO.getStatus().equalsIgnoreCase(UmcConstants.USER_ROLES_INACTIVE)){
								userOfficeRightmappDO.setStatus("REASSIGNROLE");
								user.getUserOfficeRightMappings().add(userOfficeRightmappDO);
							}
				}
				
				
				for (UserRightsDO userRight : user.getUserRights()) {
					Integer roleId = userRight.getRoleDO().getRoleId();
					if(!oldRoleIds.contains(roleId)){
						session.saveOrUpdate(userRight);
						userDO.getUserRights().add(userRight);
					}
				}
				
				 
				if(user.getUserOfficeRightMappings()!=null){
				for (UserOfficeRightsMappingDO userOfficmappingDO : user.getUserOfficeRightMappings()) {
					Integer officeId = userOfficmappingDO.getOffice().getOfficeId();
					if(!oldOfficeIds.contains(officeId)){
						session.saveOrUpdate(userOfficmappingDO);
						userDO.getUserOfficeRightMappings().add(userOfficmappingDO);
					}else if(oldOfficeIds.contains(officeId)&&userOfficmappingDO.getStatus().equalsIgnoreCase("REASSIGNROLE")){
						userOfficmappingDO.setStatus(UmcConstants.USER_ROLES_ACTIVE);
						userOfficmappingDO.setDtToBranch(UmcConstants.FLAG_N);
						session.saveOrUpdate(userOfficmappingDO);
						userDO.getUserOfficeRightMappings().add(userOfficmappingDO);
					}
				}
				}			
				
		}else{
			
			for (UserRightsDO userRightDO : user.getUserRights()) {
				
				userDO.getUserRights().add(userRightDO);
				
			}
			
			if(!StringUtil.isNull(user.getUserOfficeRightMappings())){
				for (UserOfficeRightsMappingDO userOfficmappingDO : user.getUserOfficeRightMappings()) {
				
					userDO.getUserOfficeRightMappings().add(userOfficmappingDO);
				
				}
			}
			
		}
				
			
		if(isSave){
			session.saveOrUpdate(userDO);				
		}
			isUserRightsAdded = Boolean.TRUE;
			tx.commit();
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentDAOImpl :: saveOrUpdateRoleAssignments()..:"
					+ e.getMessage());
			tx.rollback();
			
		} finally {
			closeSession(session);
		}
		return isUserRightsAdded;
	}

	/**
	 * @Method : getUserRights
	 * @param : String userCode,roleType
	 * @Desc : For getting the user Rights
	 * @return : UserRightsDO userRights
	 */
	@Override
	public List<UserRightsDO> getUserRights(String userCode, String roleType)
			throws CGBaseException {
		Set<UserRightsDO> userRights = null;
		List<UserRightsDO> userRightsList = null;
		Session session = null;
		UserDO user = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			user = (UserDO) session.createCriteria(UserDO.class)
					.add(Restrictions.eq("userName", userCode))
					.add(Restrictions.eq("userType", roleType)).uniqueResult();
			if (!StringUtil.isNull(user)) {
				//userRightsList = new ArrayList();
				userRights = user.getUserRights();
				userRightsList = new ArrayList<UserRightsDO>(userRights.size());
				
				for (UserRightsDO userRight : userRights) {
					if (StringUtils.equalsIgnoreCase(
							UmcConstants.USER_ROLES_ACTIVE,
							userRight.getStatus())) {
						userRightsList.add(userRight);
					}
				}
				
			}
			/*
			 * userRights = (List<UserRightsDO>) session
			 * .createCriteria(UserRightsDO.class) .createAlias("userDO",
			 * "user") .add(Restrictions .eq(UmcConstants.PARAM_USER_CODE,
			 * userCode)) .add(Restrictions.eq(UmcConstants.STATUS,
			 * UmcConstants.USER_ROLES_ACTIVE)) .add(Restrictions
			 * .eq(UmcConstants.PARAM_USER_TYPE, roleType)).list();
			 */
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentDAOImpl :: getUserRights()..:"
					+ e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return userRightsList;
	}

	/**
	 * @Method : getRightsId
	 * @param : Integer roleId,userId
	 * @Desc : For getting the user Rights Id
	 * @return :Integer rightId
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Integer getRightsId(Integer roleId, Integer userId)
			throws CGBaseException {
		Integer rightId = 0;
		List<Integer> rightIds = null;
		try {
			String[] params = { UmcConstants.ROLE_ID, UmcConstants.USER_ID };
			Object[] values = { roleId, userId };
			rightIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_RIGHTS_ID, params, values);
			if (rightIds != null && rightIds.size() > 0)
				rightId = rightIds.get(0);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentDAOImpl :: getRightsId()..:"
					+ e.getMessage());
		}
		return rightId;
	}

	/**
	 * @Method : getUserOfficeRightsId
	 * @param : Integer userId,officeId
	 * @Desc : For getting the office Rights Id
	 * @return : Integer rightsId
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getUserOfficeRightsId(Integer userId, Integer officeId)
			throws CGBaseException {
		Integer rightId = 0;
		List<Integer> rightIds = null;
		try {
			String[] params = { UmcConstants.USER_ID, UmcConstants.OFFICE_ID };
			Object[] values = { userId, officeId };
			rightIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_USER_OFFICE_RIGHT_ID, params, values);
			if (rightIds != null && rightIds.size() > 0)
				rightId = rightIds.get(0);
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentDAOImpl :: getUserOfficeRightsId()..:"
					+ e.getMessage());
		}
		return rightId;
	}

}
