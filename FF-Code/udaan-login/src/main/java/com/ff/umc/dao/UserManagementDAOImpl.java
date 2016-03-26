package com.ff.umc.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.CustomerUserDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.umc.constants.UmcConstants;

public class UserManagementDAOImpl extends CGBaseDAO implements
		UserManagementDAO {

	/** LOGGER. */

	private Logger LOGGER = LoggerFactory.getLogger(UserManagementDAOImpl.class);

	@Override
	public boolean activateDeActivateUser(String userName, String statusType)
			throws CGSystemException {
		Session session = null;
		String status = CommonConstants.EMPTY_STRING;
		boolean isUpdated = Boolean.FALSE;
		Transaction tx = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(UmcConstants.QRY_UPDATE_USER_STATUS);

			if (StringUtils.equalsIgnoreCase(UmcConstants.USER_ACTIVE,
					statusType))
				status = UmcConstants.FLAG_Y;
			else if (StringUtils.equalsIgnoreCase(UmcConstants.USER_INACTIVE,
					statusType))
				status = UmcConstants.FLAG_N;

			query.setString(UmcConstants.STATUS, status);
			query.setString(UmcConstants.USER_NAME, userName);
			query.setString(UmcConstants.USER_UNLOCKED,UmcConstants.FLAG_N);
			query.setString(UmcConstants.LOGIN_ATTEMPTS_ZERO,UmcConstants.ZERO);
			query.setString(UmcConstants.LOGIN_ATTEMPTS_ZERO,UmcConstants.ZERO);
			query.setDate("updatedDate", Calendar.getInstance().getTime());
			tx = session.beginTransaction();
			query.executeUpdate();
			tx.commit();
			isUpdated = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementDAOImpl :: activateDeActivateUser()..:"
					+ e.getMessage());
			if (tx != null)
				tx.rollback();
			throw new CGSystemException(e);
		} finally {
			
			session.close();
		}
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustDOByCustName(String custCode)
			throws CGSystemException {

		List<CustomerDO> result = null;
		try{
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETCUST_BYCUSTNAME, UmcConstants.CUST_CODE,
				custCode);
		if (StringUtil.isEmptyList(result))
			return null;
		else
			return result.get(0);
		}catch(Exception e){
			LOGGER.error("Error occured in UserManagementDAOImpl :: getCustDOByCustName()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getCustDescByCustTypeId(String custTypeId)
			throws CGSystemException {

		List<String> result = null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETCUSTTYPE_DESC_BYCUSTTYPEID, UmcConstants.CUST_TYPE_ID,
				custTypeId);
		
		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityDOByOfficId(Integer mappedOfficId)
			throws CGSystemException {

		List<CityDO> result = null;
		try{
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getCitiesByOfficeIds", UmcConstants.OFFIC_ID,
				mappedOfficId);
		
		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);
		}catch(Exception e){
			LOGGER.error("Error occured in UserManagementDAOImpl :: getCityDOByOfficId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public CustomerTypeDO getCustTypeDescByCustTypeId(Integer custTypeId)
			throws CGSystemException{

		List<CustomerTypeDO> result = null;
		try{
		//Integer custTypeID = Integer.parseInt(custTypeId);
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getCustTypeNameByCustTypeId", UmcConstants.CUST_TYPE_ID,
				custTypeId);
		
		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);
		}
		catch(Exception e){
			LOGGER.error("Error occured in UserManagementDAOImpl :: getCustTypeDescByCustTypeId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}

	}
	
	@Override
	public boolean saveEmployeeUser(EmployeeUserDO empUserDO)
			throws CGSystemException {
		boolean isSaved = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(empUserDO);
		
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception occured while inserting UserManagementDAOImpl in saveEmployeeUser()",e);
			throw new CGSystemException(e);
		}
		
		return isSaved;
	}

	@Override
	public void saveUser(UserDO userDO) throws CGSystemException {
	
		try {
			getHibernateTemplate().saveOrUpdate(userDO);
			
		} catch (Exception e) {
			LOGGER.error("Exception occured while inserting UserManagementDAOImpl in saveUser()",e);
			throw new CGSystemException(e);
		}
		
		
	}

	@Override
	public boolean saveCustomerUser(CustomerUserDO custUserDO)
			throws CGSystemException {
		
		boolean isUserAdded = Boolean.FALSE;
		try {
			getHibernateTemplate().save(custUserDO);
			
			isUserAdded = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Exception occured while inserting UserManagementDAOImpl in saveCustomerUser()",e);
			throw new CGSystemException(e);
		}
		return isUserAdded;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDO getEmpDOByFName(String fName,String lName) throws CGSystemException {
		List<EmployeeDO> result = null;
		
		/*result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETEMP_BYNAME, UmcConstants.EMP_NAME, empName);*/
		
		String[] paramNames = { UmcConstants.FIRST_NAME,UmcConstants.LAST_NAME,
		 };
		Object[] values = { fName,lName	};
		result = getHibernateTemplate()
		.findByNamedQueryAndNamedParam(	UmcConstants.QRY_GETEMP_BYNAME,	paramNames, values);

		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerUserDO getCustUserDObyCustId(Integer custId)
			throws CGSystemException {

		List<CustomerUserDO> result = null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETCUSTUSER_BYCUSTID,
				UmcConstants.CUSTOMER_ID, custId);
		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);

	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeUserDO getEmpUserDObyEmpId(Integer empId)
			throws CGSystemException {
		List<EmployeeUserDO> result = null;
		EmployeeUserDO resultDO=null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETEMPUSER_BYEMPID, UmcConstants.EMPLOYEE_ID,
				empId);
		if (!StringUtil.isEmptyList(result))
			resultDO = result.get(0);
		
		return resultDO;
		
	}

	
	@Override
	public boolean updateEmpEmail(Integer empId, String email)throws CGSystemException {
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		//Transaction tx = null;
		
		try{
			
			session = openTransactionalSession();
			Query query = session.getNamedQuery(UmcConstants.QRY_UPDATE_EMP_EMAIL);
			query.setInteger("employeeId", empId);
			query.setString("emailId", email);
			//tx = session.beginTransaction();
			 query.executeUpdate();
			//query.executeUpdate();
			//tx.commit();
			isUpdated = Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Error occured in UserManagementDAOImpl :: updateEmpEmail()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
			
		}
	
		return isUpdated;
	}
	
	@Override
	public boolean updateCustEmail(Integer custId, String email)throws CGSystemException {
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		//Transaction tx = null;
		
		try{
			
			session = openTransactionalSession();
			Query query = session.getNamedQuery(UmcConstants.QRY_UPDATE_CUST_EMAIL);
			query.setInteger("customerId", custId);
			query.setString("emailId", email);
			//tx = session.beginTransaction();
			 query.executeUpdate();
			//tx.commit();
			isUpdated = Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Error occured in UserManagementDAOImpl :: updateCustEmail()..:"
					,e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
			
		}
		return isUpdated;
		
	}
	
	@SuppressWarnings("unchecked")
	public OfficeDO getOfficeNameByOfficeId(Integer officeId){
		List<OfficeDO> result = null;
	result = getHibernateTemplate().findByNamedQueryAndNamedParam(
			UmcConstants.QRY_GETOFFICENAME_BYOFFICEID, UmcConstants.OFFICE_ID,
			officeId);
	return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUserNameExists(String userName)
			throws CGSystemException {
		List<UserDO> result = null;
		boolean isDuplicate=Boolean.FALSE;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GET_USER_BY_USERNAME, UmcConstants.USER_NAME,
				userName);
		if(result.size()>0)
			isDuplicate=Boolean.TRUE;
		return isDuplicate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDO getEmpDOByEmpCode(String empCode) throws CGSystemException {
		List<EmployeeDO> result = null;
		
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GETEMP_BYCODE, UmcConstants.PARAM_EMP_CODE, empCode);
		
		if (StringUtil.isEmptyList(result))
			return null;
		else
			return result.get(0);
	}
	
	@Override
	public boolean updateOnlyEmpEmail(Integer empId, String email) throws CGSystemException {
		Session session = null;
		boolean isUpdated = Boolean.FALSE;
		Transaction tx = null;
		
		try{
			
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery(UmcConstants.QRY_UPDATE_EMP_EMAIL);
			query.setInteger("employeeId", empId);
			query.setString("emailId", email);
			tx = session.beginTransaction();
			query.executeUpdate();
			tx.commit();
			isUpdated = Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Error occured in UserManagementDAOImpl :: updateEmpEmail()..:"
					,e);
			if (tx != null)
				tx.rollback();
			throw new CGSystemException(e);
		} finally {
			
			session.close();
		}
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerByCityId(Integer cityId) {
		LOGGER.trace("UserManagementDAOImpl::getCustomerByCityId::FETCHING DATA------>");
		
			return getHibernateTemplate().findByNamedQueryAndNamedParam(UmcConstants.QRY_GET_CUSTOMER_BY_CITY_ID,UmcConstants.PARAM_CITY_ID, cityId);
	}
	
}
