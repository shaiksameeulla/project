package com.ff.umc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserOfficeRightsMappingDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.domain.umc.UserRolesDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.RoleAssignmentTO;
import com.ff.umc.UserOfficeRightsMappingTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserRolesTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.RoleAssignmentDAO;

public class RoleAssignmentServiceImpl implements RoleAssignmentService {
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RoleManagementServiceImpl.class);
	private RoleAssignmentDAO userRoleAssignmentDAO;
	private RoleAssignmentCommonService roleAssignmentCommonService;
	private LoginService loginService = null;

	public RoleAssignmentDAO getUserRoleAssignmentDAO() {
		return userRoleAssignmentDAO;
	}

	public void setUserRoleAssignmentDAO(RoleAssignmentDAO userRoleAssignmentDAO) {
		this.userRoleAssignmentDAO = userRoleAssignmentDAO;
	}

	public RoleAssignmentCommonService getRoleAssignmentCommonService() {
		return roleAssignmentCommonService;
	}

	public void setRoleAssignmentCommonService(
			RoleAssignmentCommonService roleAssignmentCommonService) {
		this.roleAssignmentCommonService = roleAssignmentCommonService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	/**
	 * @Method : saveOrUpdateRoleAssignments
	 * @param : UserRolesDO userRole
	 * @Desc : For User role Saving / Updation
	 * @return :trasnStatus
	 */

	public String saveOrUpdateRoleAssignments(RoleAssignmentTO roleAssignmentTO)
			throws CGBusinessException, CGBaseException {
		String trasnStatus = "";
		UserDO user = null;
		boolean isSaved = Boolean.FALSE;
		try {
			user = userRightsDomainConverter(roleAssignmentTO);
			if (user != null) {
				isSaved = userRoleAssignmentDAO
						.saveOrUpdateRoleAssignments(user);
				if (isSaved)
					trasnStatus = UmcConstants.SUCCESS;
				else
					trasnStatus = UmcConstants.FAILURE;
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentServiceImpl :: ..:saveOrUpdateRoleAssignments()"
					+ e.getMessage());
			trasnStatus = UmcConstants.FAILURE;

		}
		return trasnStatus;
	}
	/**
	 * @Method : getActiveUserRoles
	 * @param :String roleType
	 * @Desc : For getting the active user roles
	 * @return :List<UserRolesTO>
	 */

	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGBaseException {
		return roleAssignmentCommonService.getActiveUserRoles(roleType);
	}

	/**
	 * @Method : getUserDetails
	 * @param : String userCode, String roleType
	 * @Desc : For getting the user details
	 * @return :UserRightsTO userRights
	 */
	public List<UserRightsTO> getUserDetails(String userCode, String roleType, RoleAssignmentTO roleAssignmntTo)
			throws CGBusinessException, CGBaseException {
		List<UserRightsTO> userRights = null;
		List<UserRightsDO> userRightsDOs = null;
		EmployeeUserTO employeeUser = null;
		CustomerUserTO customerUser = null;
		Integer userId = 0;
		String userName = "";
		List<UserOfficeRightsMappingTO> userMappingTOs = null;
		try {
			userRightsDOs = userRoleAssignmentDAO.getUserRights(userCode,
					roleType);
			if (userRightsDOs != null && userRightsDOs.size() > 0) {
				userRights = new ArrayList<>(userRightsDOs.size());
				userId = userRightsDOs.get(0).getUserDO().getUserId();
				if (StringUtils.equalsIgnoreCase("E", roleType)) {
					employeeUser = loginService.getEmpUserInfo(userId);
					EmployeeTO employee = employeeUser.getEmpTO();
					userName = employee.getFirstName() + " "
							+ employee.getLastName();
					roleAssignmntTo.setUserCityId(employee.getCityId());
					// Setting user office mapping
					Set<UserOfficeRightsMappingDO> userOfficeMappings = userRightsDOs
							.get(0).getUserDO().getUserOfficeRightMappings();
					if (userOfficeMappings != null
							&& userOfficeMappings.size() > 0) {
						userMappingTOs = new ArrayList(userOfficeMappings.size());
						for (UserOfficeRightsMappingDO userMappingDO : userOfficeMappings) {
							if(userMappingDO.getStatus().equalsIgnoreCase(UmcConstants.USER_ROLES_ACTIVE)){
								
							UserOfficeRightsMappingTO userMappingTO = new UserOfficeRightsMappingTO();
							userMappingTO.setOfficeId(userMappingDO.getOffice()
									.getOfficeId());
							userMappingTO.setOfficeCode(userMappingDO
									.getOffice().getOfficeCode());
							userMappingTO.setOfficeName(userMappingDO
									.getOffice().getOfficeName());
							userMappingTO.setMappedTo(userMappingDO
									.getMappedTo());
							userMappingTO.setUserId(userMappingDO.getUser()
									.getUserId());
							userMappingTO.setMappedTo(userMappingDO
									.getMappedTo());
							userMappingTOs.add(userMappingTO);
						}
						}
					}

				} else if (StringUtils.equalsIgnoreCase("C", roleType)) {
					customerUser = loginService.getCustUserInfo(userId);
					CustomerTO customer = customerUser.getCustTO();
					userName = customer.getBusinessName();
				}

				for (UserRightsDO userRightsDO : userRightsDOs) {
					UserRightsTO userRight = new UserRightsTO();
					userRight.setUserRightsId(userRightsDO.getUserRightsId());
					userRight.setUserId(userId);
					userRight.setUserCityId(roleAssignmntTo.getUserCityId());
					userRight.setUserCode(userCode);
					userRight.setUserName(userName);
					userRight.setRoleId(userRightsDO.getRoleDO().getRoleId());
					userRight.setRoleCode(userRightsDO.getRoleDO()
							.getRoleName());
					userRight.setRoleName(userRightsDO.getRoleDO()
							.getRoleDesc());
					if (userMappingTOs != null && userMappingTOs.size() > 0)
						userRight.setUserMappings(userMappingTOs);
					userRights.add(userRight);
				}
			} else {
				userRights = new ArrayList<>(1);
				
				userId = loginService.getUserIdByUserNameType(userCode,
						roleType);
				if (userId > 0) {
					if (StringUtils.equalsIgnoreCase("E", roleType)) {
						employeeUser = loginService.getEmpUserInfo(userId);
						EmployeeTO employee = employeeUser.getEmpTO();
						userName = employee.getFirstName() + " "
								+ employee.getLastName();
						roleAssignmntTo.setUserCityId(employee.getCityId());
					} else if (StringUtils.equalsIgnoreCase("C", roleType)) {
						customerUser = loginService.getCustUserInfo(userId);
						CustomerTO customer = customerUser.getCustTO();
						userName = customer.getBusinessName();
					}
					UserRightsTO userRight = new UserRightsTO();
					userRight.setUserName(userName);
					userRight.setUserId(userId);
					userRight.setUserCityId(roleAssignmntTo.getUserCityId());
					userRights.add(userRight);
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentServiceImpl :: ..:getUserDetails()"
					+ e.getMessage());
		}
		return userRights;
	}
	/**
	 * @Method : userRolesDomainConvertor
	 * @param : UserRolesTO userRolesTO
	 * @Desc : User Role TO - DO Convertor
	 * @return : UserRolesDO
	 */
	private UserDO userRightsDomainConverter(RoleAssignmentTO roleAssignmentTO) {
		Set<UserRightsDO> userRights = null;
		List<Integer> assignRoleIds = null;
		Set<UserOfficeRightsMappingDO> userMappings = null;
		UserTO userTO = null;
		UserDO userDO = new UserDO();
		;
		try {
			if (roleAssignmentTO != null) {
				userTO = loginService.getUserById(roleAssignmentTO.getUserId());
				if (userTO != null) {
					CGObjectConverter.createDomainFromTo(userTO, userDO);

					if (StringUtils.equalsIgnoreCase(
							UmcConstants.SRC_ACCESS_TO_FFCL,
							roleAssignmentTO.getRoleType())) {
						if (roleAssignmentTO.getAssignedOfficeIds() != null) {
							userMappings = new HashSet();
							List<Integer> assignedOfficeIds = null;
							assignedOfficeIds = StringUtil.parseIntegerList(
									roleAssignmentTO.getAssignedOfficeIds(),
									CommonConstants.COMMA);
							for (Integer officeId : assignedOfficeIds) {
								UserOfficeRightsMappingDO userMapping = new UserOfficeRightsMappingDO();
								OfficeDO office = new OfficeDO();
								office.setOfficeId(officeId);
								userMapping.setOffice(office);
								UserDO user = new UserDO();
								user.setUserId(roleAssignmentTO.getUserId());
								userMapping.setUser(user);
								userMapping.setMappedTo(roleAssignmentTO
										.getMappingTO());
								// For update
								Integer userOfficeRightId = userRoleAssignmentDAO
										.getUserOfficeRightsId(
												roleAssignmentTO.getUserId(),
												officeId);
								if (userOfficeRightId > 0)
									userMapping
											.setUserRightappingId(userOfficeRightId);
								userMapping
										.setStatus(UmcConstants.USER_ROLES_ACTIVE);
								userMappings.add(userMapping);
							}
						}
					}
					// Setting user mapping
					if (userMappings != null && userMappings.size() > 0)
						userDO.setUserOfficeRightMappings(userMappings);
					// Setting user rights
					assignRoleIds = StringUtil.parseIntegerList(
							roleAssignmentTO.getAssignedUserRoleIds(),
							CommonConstants.COMMA);
					userRights = new HashSet();
					for (Integer roleId : assignRoleIds) {
						UserRightsDO userRight = new UserRightsDO();
						UserRolesDO userRole = new UserRolesDO();
						UserDO user = new UserDO();
						userRole.setRoleId(roleId);
						userRight.setRoleDO(userRole);
						userRight.setUserId(roleAssignmentTO.getLoginUserId());
						userRight.setUpdatedBy(roleAssignmentTO
								.getLoginUserId());
						
						
						user.setUserId(roleAssignmentTO.getUserId());
						userRight.setStatus(UmcConstants.USER_ROLES_ACTIVE);
						userRight.setUserDO(userDO);
						// for update
						Integer rightId = userRoleAssignmentDAO.getRightsId(
								roleId, roleAssignmentTO.getUserId());
						if (rightId > 0){
							userRight.setUserRightsId(rightId);
							userRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
						} else {
							userRight.setTransactionCreateDate(Calendar.getInstance().getTime());
							userRight.setTransactionModifiedDate(Calendar.getInstance().getTime());
						}
						userRights.add(userRight);
					}
					if (userRights != null && userRights.size() > 0)
						userDO.setUserRights(userRights);

				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in RoleAssignmentServiceImpl :: ..:userRightsDomainConverter()"
					+ e.getMessage());
		}
		return userDO;

	}
	/**
	 * @Method :getAllOfficesByType
	 * @param : String offType
	 * @Desc : For getting all offices by type
	 * @return :List<OfficeTO>
	 */
	@Override
	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException {
		return loginService.getAllOfficesByType(offType);
	}

	
	/**
	 * @Method :getAllOfficesByCity
	 * @param : Integer cityId
	 * @Desc : For getting all the offices based on the cityId
	 * @return :List<OfficeTO> 
	 */
	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		return loginService.getAllOfficesByCity(cityId);
	}

	
}
