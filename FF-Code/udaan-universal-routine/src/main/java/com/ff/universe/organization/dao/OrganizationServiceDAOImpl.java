package com.ff.universe.organization.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.organization.OfficeTO;
import com.ff.universe.constant.UdaanCommonConstants;

public class OrganizationServiceDAOImpl extends CGBaseDAO implements
		OrganizationServiceDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OrganizationServiceDAOImpl.class);

	@Override
	public List<OfficeDO> getDeliveryBranchesOfCustomer(CustomerTO customerTO)
			throws CGSystemException {

		try {
			List<OfficeDO> officeDOs = null;
			Integer customerId = customerTO.getCustomerId();
			String queryName = "getDeliveryBranchesOfCustomer";
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "customerId", customerId);
			return officeDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getDeliveryBranchesOfCustomer",
					e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getReverseLogisticsCustomerList(OfficeDO officeDO)
			throws CGSystemException {
		Integer officeId = officeDO.getOfficeId();
		String custTypeCode=CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS;
		try {
			List<Object[]> customerDOs = null;
			//String queryName = "getReverseLogisticsCustomerList";
			//String queryName = "getNewReverseLogisticsCustomerList";
			String queryName = "getReverseLogisticsCustomersInContract";
			String[] params= {"officeId","custTypeCode"};
			Object[] values={officeId,custTypeCode};
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,params,values);
			return customerDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getReverseLogisticsCustomerList",
					e);
			throw new CGSystemException(e);
		}

	}

	@Override
	public List<OfficeDO> getBranchesServicing(String pincode)
			throws CGSystemException {
		// TODO Auto-generated method stub
		try {
			List<OfficeDO> officeDOs = null;
			String queryName = "getOfficesServicingPincode";
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "pinCode", pincode);
			return officeDOs;
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getBranchesServicing",
					e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeDetails(Integer officeId)
			throws CGSystemException {
		List<OfficeDO> officeDOs=null;
		try {
			officeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getOfficeDetails",
							"officeId", officeId);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationServiceDAOImpl.getOfficeDetails",
					e);
			throw new CGSystemException(e);
		}
		return officeDOs;
	}

	@Override
	public List<CustomerTO> getCustomer(Integer customerId)
			throws CGSystemException {
		// TODO Auto-generated method stub
		List<CustomerTO> custTO = null;
		try {

			custTO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCustomer", "customerId", customerId);

		} catch (Exception e) {
			throw new CGSystemException(e);
		}
		return custTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer validateHubByOfficeId(Integer officeId)
			throws CGSystemException {
		Integer officeTypeId = null;
		try {
			List<OfficeDO> officeDOs = null;
			String queryName = "validateHubByOfficeId";
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "officeId", officeId);
			if (officeDOs != null && officeDOs.size() > 0) {
				if (officeDOs.get(0).getOfficeTypeDO() != null) {
					officeTypeId = officeDOs.get(0).getOfficeTypeDO()
							.getOffcTypeId();
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.validateHubByOfficeId",
					e);
			throw new CGSystemException(e);
		}
		return officeTypeId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGSystemException {
		List<OfficeDO> officeDOList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOList = (List<OfficeDO>) session
					.createCriteria(OfficeDO.class)
					.createAlias("officeTypeDO", "officeType")
					.add(Restrictions.eq("officeType.offcTypeId",
							officeTypeId)).list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getBOsByOfficeTypeId",
					e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return officeDOList;
	}

	@Override
	// Get employee details : Name and Code by employeeId
	public Object[] getEmployeeDetails(Integer employeeId)
			throws CGSystemException {
		Object[] empDetails = null;
		List<Object[]> result = null;
		try {
			if (employeeId != null && employeeId > 0) {
				result = getHibernateTemplate().findByNamedQueryAndNamedParam(
						UdaanCommonConstants.GET_EMPLOYEE_DETAILS_BY_EMPID,
						UdaanCommonConstants.EMPLOYEE_ID, employeeId);
				if (result != null && result.size() > 0) {
					empDetails = (Object[]) result.get(0);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationServiceDAOImpl.getEmployeeDetails", e);
			throw new CGSystemException(e);
		}
		return empDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeTypeDO> getOfficeTypeListForDispatch()
			throws CGSystemException {
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficeTypeListForDispatch() :: Start --------> ::::::");
		Session session = null;
		List<OfficeTypeDO> officeDOList = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOList = (List<OfficeTypeDO>)session.createCriteria(OfficeTypeDO.class)
					.add(Restrictions.eq(UdaanCommonConstants.AAPLICABLE_FOR_DISPATCH_PARAM, CommonConstants.YES))
					.list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getOfficeTypeListForDispatch() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficeTypeListForDispatch() :: End --------> ::::::");
		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByOffice(OfficeTO officeTO)
			throws CGSystemException {
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: Start --------> ::::::");
		Session session = null;
		List<OfficeDO> officeDOList = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(OfficeDO.class);
			
			/* commented ::criteria.add(Restrictions.ne("officeId", officeTO.getOfficeId()));
			 * Reason : since it's a global Service we can't/should not apply application specific Constraints
			 * If we want put application specific constraints,please create dummy variables in the officeTO
			 *  And apply the logic here .
			 * 
			 */
			if(!StringUtil.isEmptyInteger(officeTO.getOfficeId())){
				if(!StringUtil.isNull(officeTO.getIsExcludeOfficeId()) && 
						officeTO.getIsExcludeOfficeId()){
					criteria.add(Restrictions.ne("officeId", officeTO.getOfficeId()));
				}else{
					if(StringUtil.isEmptyInteger(officeTO.getReportingRHO()) && StringUtil.isEmptyInteger(officeTO.getReportingHUB())){
					criteria.add(Restrictions.eq("officeId", officeTO.getOfficeId()));		
					}
				}
			}
			if(!StringUtil.isNull(officeTO.getOfficeTypeTO())&& !StringUtil.isEmptyInteger(officeTO.getOfficeTypeTO().getOffcTypeId())){
			criteria.add(Restrictions.eq("officeTypeDO.offcTypeId", officeTO.getOfficeTypeTO().getOffcTypeId()));
			}
			if(!StringUtil.isNull(officeTO.getOfficeTypeTO())&& StringUtils.isNotEmpty(officeTO.getOfficeTypeTO().getOffcTypeCode())){
				criteria.createAlias("officeTypeDO", "officeType").add(Restrictions.eq("officeType.offcTypeCode", officeTO.getOfficeTypeTO().getOffcTypeCode()));
			}
			if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
				criteria.add(Restrictions.eq("officeCode", officeTO.getOfficeCode().trim()));
			}
			if(!StringUtil.isEmptyInteger(officeTO.getReportingRHO()) || !StringUtil.isEmptyInteger(officeTO.getReportingHUB())){
				Disjunction disjoint=Restrictions.disjunction();
				if(!StringUtil.isEmptyInteger(officeTO.getReportingRHO())){
					disjoint.add(Restrictions.eq("reportingRHO", officeTO.getReportingRHO()));
				} if(!StringUtil.isEmptyInteger(officeTO.getReportingHUB())){
					disjoint.add(Restrictions.eq("reportingHUB", officeTO.getReportingHUB()));
				}
				if(!StringUtil.isEmptyInteger(officeTO.getOfficeId())){
					disjoint.add(Restrictions.eq("officeId", officeTO.getOfficeId()));
				}

				criteria.add(disjoint);
			}
			if(!StringUtil.isEmptyInteger(officeTO.getCityId())){
				criteria.add(Restrictions.eq("cityId", officeTO.getCityId()));				
			}
			officeDOList = (List<OfficeDO>)criteria.list();
		
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getOfficesByOffice() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: End --------> ::::::");
		return officeDOList;
	}
	/**
	 *  gets the office Details as List<Mape(officeId,OfficeCode+OfficeName)> by criteria reportingOffice=inputOfficeId 
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<?> getOfficeUnderOfficeIdAsMap(Integer officeId) throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(UdaanCommonConstants.QRY_OFFICE_MAP_UNDER_BRANCH, UdaanCommonConstants.QRY_PARAM_BRANCHID, officeId);
	}

	
	

}
