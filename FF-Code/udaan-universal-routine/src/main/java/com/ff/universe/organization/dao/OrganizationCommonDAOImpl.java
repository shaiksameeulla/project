package com.ff.universe.organization.dao;

/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.CSDSAPVendorRegionMapDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.BranchPincodeServiceabilityDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * @author kgajare
 * 
 */
public class OrganizationCommonDAOImpl extends CGBaseDAO implements
		OrganizationCommonDAO {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OrganizationCommonDAOImpl.class);

	/**
	 * @see com.ff.web.organization.dao.OrganizationServiceDAO#getBranchEmployees(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGSystemException
	 *             getBranchEmployees OrganizationServiceDAO kgajare
	 */
	@Override
	public List<EmployeeDO> getBranchEmployees(Integer branchId)
			throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		try {

			String queryName = "getBranchEmployeesForAssignment";
			employeeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "branchId", branchId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getBranchEmployees", e);
			throw new CGSystemException(e);
		}
		return employeeDOs;
	}

	/**
	 * @see com.ff.web.organization.dao.OrganizationServiceDAO#getMasterCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGSystemException
	 *             getMasterCustomerList OrganizationServiceDAO kgajare
	 */
	@Override
	public List<CustomerDO> getMasterCustomerList(Integer branchId)
			throws CGSystemException {
		List<CustomerDO> customerDOs = null;
		try {

			String queryName = "getMasterCustomers";
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "branchId", branchId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getMasterCustomerList",
					e);
			throw new CGSystemException(e);
		}
		return customerDOs;
	}

	/**
	 * @see com.ff.web.organization.dao.OrganizationServiceDAO#getReverseCustomerList(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param branchId
	 * @return
	 * @throws CGSystemException
	 *             getReverseCustomerList OrganizationServiceDAO kgajare
	 */
	@Override
	public List<CustomerDO> getReverseCustomerList(Integer branchId)
			throws CGSystemException {
		List<CustomerDO> customerDOs = null;
		try {

			String queryName = "getReverseCustomerList";
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "branchId", branchId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getReverseCustomerList",
					e);
			throw new CGSystemException(e);
		}
		return customerDOs;
	}

	/**
	 * @see com.ff.web.organization.dao.OrganizationServiceDAO#getBranchesUnderHUB(java.lang.Integer)
	 *      Nov 11, 2012
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 *             getBranchesUnderHUB OrganizationServiceDAO kgajare
	 */
	@Override
	public List<OfficeDO> getBranchesUnderHUB(Integer officeId)
			throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		try {

			String queryName = "getBranchesUnderHUB";
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "officeId", officeId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getBranchesUnderHUB", e);
			throw new CGSystemException(e);
		}
		return officeDOs;
	}

	@Override
	public List<EmployeeDO> getAllEmployees() throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		try {
			employeeDOs = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_EMPLOYEES);

		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllEmployees", e);
		}

		return employeeDOs;
	}

	/**
	 * @see com.ff.web.organization.dao.OrganizationServiceDAO#getApprovers
	 * 
	 * @param officeTO
	 * @return List<EmployeeDO>
	 * @throws CGSystemException
	 *             getApprovers OrganizationCommonDAOImpl uchauhan
	 */
	@Override
	public List<EmployeeDO> getApproversUnderRegion(Integer regionId)
			throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		//Session session = null;
		try {
			//session = getHibernateTemplate().getSessionFactory().openSession();
			employeeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getApproversUnderRegion", "regionId", regionId);
			if (!StringUtil.isEmptyList(employeeDOs)) {
				employeeDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getApproversByRegion",
								"regionId", regionId);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllEmployees", e);
		} finally {
			//closeSession(session);
		}
		return employeeDOs;
	}

	@Override
	public List<EmployeeDO> geScreentApproversByOffice(Integer screenId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		LOGGER.info("  OrganizationCommonDAOImpl.geScreentApproversByOffice");
		List<EmployeeDO> employeeDOs = null;
		String hql = "getScreenApproversUnderOffice";
		String[] params = { "screenId", "officeId" };
		Object[] values = { screenId, officeId };
		try {
			employeeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					hql, params, values);
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllEmployees", e);
			throw new CGSystemException(e);
		}
		LOGGER.info(" END: OrganizationCommonDAOImpl.geScreentApproversByOffice");
		return employeeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByCity(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFFICES_BY_CITY,
					UdaanCommonConstants.PARAM_CITY_ID, cityId);

		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllBranchOfficesByCity(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_ALL_BRANCH_OFFICES_BY_CITY,
					UdaanCommonConstants.PARAM_CITY_ID, cityId);

		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllBranchAndStandaloneOfficesByCity(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_ALL_BRANCH_AND_STANDALONE_OFFICES_BY_CITY,
					UdaanCommonConstants.PARAM_CITY_ID, cityId);

		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByCityList(List<CityTO> cityTOList)
			throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;

		Criteria criteria = null;
		try {

			session = createSession();
			criteria = session.createCriteria(OfficeDO.class, "office");

			List<Integer> cityId = new ArrayList<Integer>();
			if (!CGCollectionUtils.isEmpty(cityTOList)) {

				for (CityTO cityTO : cityTOList) {
					if (!StringUtil.isEmptyInteger(cityTO.getCityId()))
						cityId.add(cityTO.getCityId());
				}

				if (!CGCollectionUtils.isEmpty(cityId)) {
					criteria.add(Restrictions.in("office.cityId", cityId));
				}

				officeDOs = criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getCitiesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		String ParamNames[] = { "cityId", "offTypeId" };
		Object values[] = { cityId, officeTypeId };
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_OFFICES_BY_CITY_AND_OFFICETYPE,
							ParamNames, values);
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}
	
	
	@Override
	public List<OfficeDO> getAllHubOfficesByCity(Integer cityId,
			String officeTypeCode) throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		String ParamNames[] = { "cityId", "offTypeCode" };
		Object values[] = { cityId, officeTypeCode };
		
		/*String ParamNames[] = { "cityId" };
		Object values[] = { cityId};*/
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_ALL_HUBS_BY_CITY,
							ParamNames, values);
			
			/*offices = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_REPORTING_HUB_OF_LOGGED_IN_BRANCH,
							ParamNames, values);*/
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllHubOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}

	@Override
	public List<EmployeeDO> getAllEmployeesUnderRegion(EmployeeDO employeeDO)
			throws CGSystemException {
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: Start --------> ::::::");
		Session session = null;
		List<EmployeeDO> employeeDolist = null;
		Criteria employeeCriteria = null;
		DetachedCriteria empOffice = null;
		try {
			session = createSession();
			employeeCriteria = session.createCriteria(EmployeeDO.class,
					"employee");
			if(StringUtil.isStringEmpty(employeeDO.getEmpVirtual())|| employeeDO.getEmpVirtual().equalsIgnoreCase(CommonConstants.NO)){
				// Added by Narasimha to avoid the virtual employees
				employeeCriteria.add(Restrictions.eq("employee.empVirtual", "N"));
			}else{
				employeeCriteria.add(Restrictions.disjunction()
						.add(Restrictions.eq("employee.empVirtual",CommonConstants.NO))
						.add(Restrictions.eq("employee.empVirtual",CommonConstants.YES)));
			}
			
			employeeCriteria.add(Restrictions.eq("employee.empStatus",CommonConstants.RECORD_STATUS_ACTIVE ));
			
			if (!StringUtil.isEmptyInteger(employeeDO.getEmployeeId())) {
				employeeCriteria.add(Restrictions.eq("employee.employeeId",
						employeeDO.getEmployeeId()));
			}
			if (!StringUtil.isStringEmpty(employeeDO.getEmpCode())) {
				employeeCriteria.add(Restrictions.eq("employee.empCode",
						employeeDO.getEmpCode()));
			}
			if (!StringUtil.isEmptyInteger(employeeDO.getOfficeId())) {
				// for Sub Query ****START****
				empOffice = DetachedCriteria.forClass(OfficeDO.class,
						"empOffice");
				Disjunction disc = Restrictions.disjunction();
				if (!StringUtil.isEmptyInteger(employeeDO.getOfficeId())) {
					disc.add(Restrictions.eq("empOffice.officeId",
							employeeDO.getOfficeId()));
				}
				if (!StringUtil.isEmptyInteger(employeeDO.getEmpOfficeRHOId())) {
					disc.add(Restrictions.eq("empOffice.reportingRHO",
							employeeDO.getEmpOfficeRHOId()));
				}
				if (!StringUtil.isEmptyInteger(employeeDO.getEmpOfficeHUBId())) {
					disc.add(Restrictions.eq("empOffice.reportingHUB",
							employeeDO.getEmpOfficeHUBId()));
				}
				empOffice.add(disc);
				empOffice.setProjection(Projections
						.property("empOffice.officeId"));
				// for Sub Query ****END****

				// for Main Query Join Condition
				employeeCriteria.add(Property.forName("employee.officeId").in(
						empOffice));

			}
			employeeDolist = (List<EmployeeDO>) employeeCriteria.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getOfficesByOffice() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: End --------> ::::::");
		return employeeDolist;
	}

	@Override
	public List<OfficeDO> getAllOfficeByOfficeType(OfficeTypeDO officeType)
			throws CGSystemException {
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: Start --------> ::::::");
		Session session = null;
		List<OfficeDO> officeDoList = null;
		Criteria officeCriteria = null;
		DetachedCriteria officeTypeCriteria = null;
		try {
			session = createSession();
			officeCriteria = session.createCriteria(OfficeDO.class, "office");

			if (!StringUtil.isStringEmpty(officeType.getOffcTypeCode())) {
				// for Sub Query ****START****
				officeTypeCriteria = DetachedCriteria.forClass(
						OfficeTypeDO.class, "officeType");
				officeTypeCriteria
						.add(Restrictions.eq("officeType.offcTypeCode",
								officeType.getOffcTypeCode()));
				officeTypeCriteria.setProjection(Projections
						.property("officeType.offcTypeId"));
				// for Sub Query ****END****
				// for Main Query Join Condition
				officeCriteria.add(Property.forName(
						"office.officeTypeDO.offcTypeId")
						.in(officeTypeCriteria));
			}
			officeDoList = officeCriteria.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getOfficesByOffice() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("OrganizationServiceDAOImpl :: getOfficesByOffice() :: End --------> ::::::");
		return officeDoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateBranchPincodeServiceability(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGSystemException {
		boolean isValidPincode = Boolean.FALSE;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session
					.createCriteria(BranchPincodeServiceabilityDO.class);
			criteria.add(Restrictions.eq(UdaanCommonConstants.OFFICE_ID,
					pincodeServicabilityTO.getOfficeId()));
			criteria.add(Restrictions.eq(UdaanCommonConstants.PINCODE_ID,
					pincodeServicabilityTO.getPincodeId()));
			criteria.add(Restrictions.eq(UdaanCommonConstants.STATUS,
					UdaanCommonConstants.STATUS_ACTIVE));
			List<BranchPincodeServiceabilityDO> branchPincodeServiceableDOList = criteria
					.list();
			if (!StringUtil.isEmptyList(branchPincodeServiceableDOList)) {
				isValidPincode = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::validateBranchPincodeServiceability() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return isValidPincode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByRegion(Integer regionId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFFICES_BY_REGION,
					UdaanCommonConstants.PARAM_MAPPED_TO_REGION, regionId);
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByRegionalOfficeExcludeOffice(
			OfficeTO officeTO) throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.GET_OFFICES_BY_REGIONAL_OFFICE_EXCLUDE_OFFICE_QUERY,
							new String[] {
									UdaanCommonConstants.REGIONAL_OFFICE_ID_PARAM,
									UdaanCommonConstants.OFFICE_ID_PARAM },
							new Object[] { officeTO.getReportingRHO(),
									officeTO.getOfficeId() });
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesByRegionalOfficeExcludeOffice",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllRegionalOffices() throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_ALL_REGIONAL_OFFICES,
							new String[] { UdaanCommonConstants.QRY_PARAM_OFFICE_TYPE_CODE },
							new Object[] { UdaanCommonConstants.QRY_PARAM_VALUE_OFFICE_TYPE_CODE });
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getAllRegionalOffices",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByOffices(List<OfficeTO> officeTOList)
			throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			List<Integer> officeId = new ArrayList<Integer>();

			criteria = session.createCriteria(OfficeDO.class, "office");

			for (OfficeTO officeTO : officeTOList) {
				if (!StringUtil.isEmptyInteger(officeTO.getOfficeId()))
					officeId.add(officeTO.getOfficeId());
			}

			if (!CGCollectionUtils.isEmpty(officeId)) {
				criteria.add(Restrictions.in("office.officeId", officeId));
			}

			officeDOs = criteria.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getAllRegionalOffices",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByCityAndRHO(List<CityTO> cityTOList)
			throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();

			Integer[] cityId = new Integer[cityTOList.size()];
			int i = 0;
			for (CityTO cityTO : cityTOList) {
				if (!StringUtil.isEmptyInteger(cityTO.getCityId()))
					cityId[i] = cityTO.getCityId();
				i++;
			}

			officeDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_ALL_REGIONAL_OFFICES_BY_CITI_RHO,
							new String[] {
									UdaanCommonConstants.QRY_PARAM_OFFICE_TYPE_CODE,
									UdaanCommonConstants.QRY_PARAM_CITY_ID },
							new Object[] {
									UdaanCommonConstants.QRY_PARAM_VALUE_OFFICE_TYPE_CODE,
									cityId });
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getAllRegionalOffices",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@Override
	public OfficeDO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws  CGSystemException {
		LOGGER.debug("OrganizationCommonDAOImpl::getOfficeByIdOrCode::start------------>:::::::");
		Session session = null;
		Criteria criteria = null;
		OfficeDO office = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(OfficeDO.class);
			if (!StringUtil.isEmptyInteger(officeId))
				criteria.add(Restrictions.eq("officeId", officeId));
			else if (StringUtils.isNotEmpty(offCode))
				criteria.add(Restrictions.eq("officeCode", offCode));
			if (!StringUtil.isEmptyInteger(officeId) || StringUtils.isNotEmpty(offCode))
				office = (OfficeDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeByIdOrCode", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OrganizationCommonDAOImpl::getOfficeByIdOrCode::ENd------------>:::::::");
		return office;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		try {
			employeeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_EMPLOYEES_BY_OFFICE_ID,
					UdaanCommonConstants.OFFICE_ID_PARAM,
					officeTO.getOfficeId());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getEmployeesOfOffice", e);
			throw new CGSystemException(e);
		}
		return employeeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeTypeDO getOfficeTypeIdByOfficeTypeCode(String officeType) {
		LOGGER.debug("OrganizationCommonDAOImpl :: getOfficeTypeIdByOfficeTypeCode :: Start");
		List<OfficeTypeDO> officeTypeDO = null;
		OfficeTypeDO officeTypeDo = null;
		try {
			officeTypeDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_OFFICE_TYPE_BY_OFFICE_TYPE_CODE,
							UdaanCommonConstants.OFFICE_TYPE_CODE, officeType);
			if (!StringUtil.isEmptyColletion(officeTypeDO)) {
				officeTypeDo = officeTypeDO.get(0);
			}

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeTypeIdByOfficeTypeCode",
					e);
		}
		LOGGER.debug("OrganizationCommonDAOImpl :: getOfficeTypeIdByOfficeTypeCode :: End");
		return officeTypeDo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeIdByReportingRHOCode(String reportingRHOCode) {
		LOGGER.debug("OrganizationCommonDAOImpl :: getOfficeIdByReportingRHOCode :: Start");
		List<OfficeDO> officeDO = null;
		try {
			officeDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFFICE_ID_BY_RHO_CODE,
					UdaanCommonConstants.REPORTING_RHO_CODE, reportingRHOCode);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeIdByReportingRHOCode",
					e);
		}
		LOGGER.debug("OrganizationCommonDAOImpl :: getOfficeIdByReportingRHOCode :: End");
		return officeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getEmployeeDetails(EmployeeTO empTO)
			throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		Criteria criteria = null;
		Session session = null;
		try {
			session = createSession();
			criteria = session.createCriteria(EmployeeDO.class, "empDO");
			// Added by Narasimha to avoid the virtual employees
			criteria.add(Restrictions.eq("empDO.empVirtual", "N"));
			if (!StringUtil.isNull(empTO.getEmployeeId()))
				criteria.add(Restrictions.eq("empDO.employeeId",
						empTO.getEmployeeId()));
			if (!StringUtil.isNull(empTO.getEmpCode()))
				criteria.add(Restrictions.eq("empDO.empCode",
						empTO.getEmpCode()));
			criteria.add(Restrictions.eq("empDO.empStatus",CommonConstants.RECORD_STATUS_ACTIVE ));
			employeeDOs = criteria.list();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getEmployeesOfOffice", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return employeeDOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.organization.dao.OrganizationCommonDAO#
	 * getOfficeTypeDOByOfficeTypeIdOrCode
	 * (com.ff.domain.organization.OfficeTypeDO)
	 */
	@Override
	public OfficeTypeDO getOfficeTypeDOByOfficeTypeIdOrCode(
			OfficeTypeTO officeTypeTO) throws CGSystemException {
		OfficeTypeDO officeTypeDo = null;
		Criteria criteria = null;
		Session session = null;
		try {
			session = createSession();
			criteria = session.createCriteria(OfficeTypeDO.class);
			if (!StringUtil.isNull(officeTypeTO.getOffcTypeId()))
				criteria.add(Restrictions.eq("offcTypeId",
						officeTypeTO.getOffcTypeId()));
			if (!StringUtil.isNull(officeTypeTO.getOffcTypeCode()))
				criteria.add(Restrictions.eq("offcTypeCode",
						officeTypeTO.getOffcTypeCode()));
			officeTypeDo = (OfficeTypeDO) criteria.uniqueResult();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeTypeDOByOfficeTypeIdOrCode",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return officeTypeDo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByCityAndOfficeTypes(CityTO cityTO,
			List<String> officeTypes) throws CGSystemException {
		List<OfficeDO> officeDos = null;
		Criteria criteria = null;
		Session session = null;
		try {
			session = createSession();
			criteria = session.createCriteria(OfficeDO.class);
			criteria.add(Restrictions.eq("cityId", cityTO.getCityId()));
			criteria.createAlias("officeTypeDO", "officeType");
			criteria.add(Restrictions
					.in("officeType.offcTypeCode", officeTypes));

			officeDos = criteria.list();

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesByCityAndOfficeTypes",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return officeDos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPLoadMovementVendorDO getVendorByCode(String vendorCode)
			throws CGSystemException {
		 logger.debug("OrganizationCommonDAOImpl :: getVendorByCode :: Start");
		List<CSDSAPLoadMovementVendorDO> vendorDOList = null;
		CSDSAPLoadMovementVendorDO vendorDO = null;
		try {
			vendorDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_VENDOR_BY_CODE,
							UdaanCommonConstants.VENDOR_CODE, vendorCode);
			if (!StringUtil.isNull(vendorDOList)
					&& (!StringUtil.isEmptyColletion(vendorDOList))) {
				vendorDO = vendorDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getVendorByCode", e);
			throw new CGSystemException(e);
		}
		logger.debug(" OrganizationCommonDAOImpl :: getVendorByCode :: End");
		return vendorDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByEmpId(Integer EmpId) throws CGSystemException {
		List<OfficeDO> result = null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UdaanCommonConstants.QRY_GET_OFFICE_BY_EMPID,
				UdaanCommonConstants.EMPLOYEE_ID, EmpId);

		if (result.isEmpty())
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPEmployeeDO getEmployeeDetailsByEmpCode(String empCode)
			throws CGSystemException {
		LOGGER.debug("OrganizationCommonDAOImpl :: getEmployeeDetailsByEmpCode ::Start");
		List<CSDSAPEmployeeDO> empDOList = null;
		CSDSAPEmployeeDO empDO = null;
		try {
			empDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_EMPLOYEE_BY_CODE_SAP,
					UdaanCommonConstants.EMP_CODE, empCode);
			if (!StringUtil.isNull(empDOList)
					&& (!StringUtil.isEmptyColletion(empDOList))) {
				empDO = empDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getEmployeeDetailsByEmpCode",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("OrganizationCommonDAOImpl :: getEmployeeDetailsByEmpCode :: End");
		return empDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPCustomerDO getCustomerDetailsByCode(String customerCode)
			throws CGSystemException {
		LOGGER.debug("OrganizationCommonDAOImpl :: getCustomerDetailsByCode ::START");
		List<CSDSAPCustomerDO> custDOList = null;
		CSDSAPCustomerDO custDO = null;
		try {
			custDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_CUSTOMER_BY_CODE_SAP,
					UdaanCommonConstants.CUSTOMER_CODE, customerCode);
			if (!StringUtil.isNull(custDOList) && (!StringUtil.isEmptyColletion(custDOList))) {
				custDO = custDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getCustomerDetailsByCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("OrganizationCommonDAOImpl :: getCustomerDetailsByCode :: END");
		return custDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCustomerIdByContractNo(String contractNo)
			throws CGSystemException {
		LOGGER.debug("OrganizationCommonDAOImpl :: getContractDtlsByNo :: START");
		List<Integer> custIdList = null;
		Integer customerId = null;
		try {
			custIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_CUSTOMER_ID_BY_CONTRACT_NO,
					UdaanCommonConstants.CONTRACT_NO, contractNo);
			
			if (!StringUtil.isEmptyColletion(custIdList)) {
				customerId = custIdList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getContractDtlsByNo", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("OrganizationCommonDAOImpl :: getContractDtlsByNo :: End");
		return customerId;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public CSDSAPBANewDO getBACustomerDetailsByCode(String baCode)
	 * throws CGSystemException { LOGGER.debug(
	 * "OrganizationCommonDAOImpl :: getBACustomerDetailsByCode ::Start");
	 * List<CSDSAPBANewDO> baCustDOList = null; CSDSAPBANewDO baCustDO = null;
	 * try{ baCustDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
	 * UdaanCommonConstants.QRY_GET_BA_CUSTOMER_BY_CODE_SAP,
	 * UdaanCommonConstants.BA_CODE, baCode);
	 * if(!StringUtil.isNull(baCustDOList)
	 * &&(!StringUtil.isEmptyColletion(baCustDOList))){ baCustDO =
	 * baCustDOList.get(0); } }catch(Exception e){
	 * LOGGER.error("ERROR : OrganizationCommonDAOImpl.getBACustomerDetailsByCode"
	 * ,e); throw new CGSystemException(e); }
	 * LOGGER.debug("OrganizationCommonDAOImpl :: getBACustomerDetailsByCode :: End"
	 * ); return baCustDO; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getRHOOfficesByUserId(Integer userId)
			throws CGSystemException {

		List<OfficeDO> officeDoList = null;
		Session session = null;

		try {
			session = createSession();

			String query = UdaanCommonConstants.QRY_GET_RHO_OFFICES_BY_USER_ID;

			// String[] params = new String[]
			// {UdaanCommonConstants.QRY_PARAM_USER_ID,
			// UdaanCommonConstants.QRY_PARAM_OFF_TYPE_CODE};

			String[] params = new String[] { UdaanCommonConstants.QRY_PARAM_USER_ID };

			// Object[] values = new Object[] {userId,
			// UdaanCommonConstants.QRY_PARAM_VALUE_OFFICE_TYPE_CODE};

			Object[] values = new Object[] { userId };
			officeDoList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeTypeDOByOfficeTypeIdOrCode",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}

		return officeDoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VendorTypeDO getVendorTypeByAccGroup(String accGroupSAP)
			throws CGSystemException {
		List<VendorTypeDO> vendorTypeDOList = null;
		VendorTypeDO vendorTypeDO = null;
		try {
			vendorTypeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UdaanCommonConstants.QRY_GET_VENDOR_TYPE_BY_ACC_GRP,
							UdaanCommonConstants.ACC_GRP_SAP, accGroupSAP);
			if (!StringUtil.isNull(vendorTypeDOList)
					&& (!StringUtil.isEmptyColletion(vendorTypeDOList))) {
				vendorTypeDO = vendorTypeDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getVendorTypeByAccGroup",
					e);
			throw new CGSystemException(e);
		}
		return vendorTypeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPVendorRegionMapDO getVendorRegionMapping(Integer vendorId,
			Integer regionId) {
		List<CSDSAPVendorRegionMapDO> vendorRegionMapDO = null;
		CSDSAPVendorRegionMapDO vendorMapDO = null;
		String hql = "getVendorRegionMapping";
		String[] params = { "vendorId", "regionId" };
		Object[] values = { vendorId, regionId };
		try {
			vendorRegionMapDO = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(hql, params, values);
			if (!StringUtil.isEmptyColletion(vendorRegionMapDO)) {
				vendorMapDO = vendorRegionMapDO.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getVendorRegionMapping",
					e);
		}
		return vendorMapDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByCityListExceptCOOfc(
			List<CityTO> cityTOList) throws CGSystemException {

		List<OfficeDO> officeDOs = null;
		Session session = null;

		Criteria criteria = null;
		try {

			session = createSession();
			criteria = session.createCriteria(OfficeDO.class, "office");

			List<Integer> cityId = new ArrayList<Integer>();
			if (!CGCollectionUtils.isEmpty(cityTOList)) {

				for (CityTO cityTO : cityTOList) {
					if (!StringUtil.isEmptyInteger(cityTO.getCityId()))
						cityId.add(cityTO.getCityId());
				}

				if (!CGCollectionUtils.isEmpty(cityId)) {
					criteria.add(Restrictions.in("office.cityId", cityId));
				}

				criteria.createAlias("office.officeTypeDO", "ofcType");
				criteria.add(Restrictions.ne("ofcType.offcTypeCode",
						UdaanCommonConstants.CORPORATE_OFFICE_TYPE_CODE));
				criteria.addOrder(Order.asc("office.officeName"));
				officeDOs = criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByCityId(Integer cityId) throws CGSystemException {
		List<OfficeDO> ofcDOList = null;
		OfficeDO ofcDO = null;
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getOfcByCityID", "cityId", cityId);
			if (!StringUtil.isEmptyColletion(ofcDOList)) {
				// if(ofcDOList.size()>1){
				// ofcDO = ofcDOList.get(1);
				// }else{
				ofcDO = ofcDOList.get(0);
				// }
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getOfficeByCityId", e);
			throw new CGSystemException(e);
		}
		return ofcDO;
	}

	@Override
	public List<EmployeeDO> getEmployeesByCity(Integer cityId)
			throws CGSystemException {
		List<EmployeeDO> employeeDOs = null;
		try {
			String[] paramNames={"cityId","status"};
			Object[] values={cityId,"A"};
			employeeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(UdaanCommonConstants.GET_EMPLOYEES_BY_CITY, paramNames, values);

		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getAllEmployees", e);
		}

		return employeeDOs;
	}

	@Override
	public List<CustomerDO> getCustomersByOffice(List<Integer> officeList)
			throws CGSystemException {
		List<CustomerDO> customerDOs = null;
		try {
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.GET_CUSTOMERS_BY_OFFICE, "officeList",
					officeList);

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getCustomersByOffice", e);
		}

		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesAndHubOfficesServicedByPincode(String pincode)
			throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode :: START");
		List<OfficeDO> officeDOs = null;
		try {
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFC_AND_HUB_OFC_SERVICED_BY_PINCODE, UdaanCommonConstants.PARAM_PINCODE, pincode);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode :: END");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByUserId(Integer userId) throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode :: START");
		List<OfficeDO> officeDOList = null;
		OfficeDO officeDO = null;
		try {
			officeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFC_BY_USER_ID, UdaanCommonConstants.PARAM_USER_ID, userId);
			if(!CGCollectionUtils.isEmpty(officeDOList)){
				officeDO = officeDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeAndHubOfficesServicedByPincode :: END");
		return officeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllOfficesByRegionID(Integer regionId) throws CGSystemException {
		List<Integer> officeIds = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			officeIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFFICES_BY_REGION_ID,
					UdaanCommonConstants.PARAM_MAPPED_TO_REGION, regionId);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByOfcCode(String ofcCode) throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeByOfcCode :: START");
		List<OfficeDO> officeDOList = null;
		OfficeDO officeDO = null;
		try {
			officeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFC_BY_OFC_CODE, UdaanCommonConstants.PARAM_OFC_CODE, ofcCode);
			if(!CGCollectionUtils.isEmpty(officeDOList)){
				officeDO = officeDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeByOfcCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeByOfcCode :: END");
		return officeDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDO getEmployeeByEmpCode(String empCode) throws CGSystemException {
		List<EmployeeDO> result = null;
		
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UdaanCommonConstants.QRY_GET_EMP_BY_CODE, UdaanCommonConstants.PARAM_EMP_CODE, empCode);
		
		if (StringUtil.isEmptyList(result))
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DepartmentDO getDepartmentByCode(String deptCode)
			throws CGSystemException {
		List<DepartmentDO> departmentDOList = null;
		DepartmentDO deptDO = null;
		try {
			departmentDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_DEPARTMENT_BY_CODE,UdaanCommonConstants.PARAM_DEPARTMENT_CODE,deptCode);
			if(!CGCollectionUtils.isEmpty(departmentDOList)){
				deptDO = departmentDOList.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getDepartmentByCode", e);
			throw new CGSystemException(e);
		}
		return deptDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getRHOOfcIdByRegion(Integer originRegionId)
			throws CGSystemException {
		List<OfficeDO> officeDOList = null;
		OfficeDO officeDO = null;
		try {
			
			String query = "getRegionalOfficeByRegionIdAndOfficeTypeCode";
			
			String params[] = {"officeTypeCode","regionId"};
		
			Object values[] = {UdaanCommonConstants.QRY_PARAM_VALUE_OFFICE_TYPE_CODE, originRegionId};
			
			officeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
			if(!CGCollectionUtils.isEmpty(officeDOList)){
				officeDO = officeDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficeByOfcCode", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficeByOfcCode :: END");
		return officeDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficesByRegionAndOfficeType(Integer regionId) throws CGSystemException {
		LOGGER.debug("OrganizationCommonDAOImpl.getOfficesByRegionAndOfficeType :: Start");
		List<OfficeDO> offices = null;
		OfficeDO ofcDO = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.GET_OFFICES_BY_REGION_AND_OFFICE_TYPE,
					UdaanCommonConstants.PARAM_MAPPED_TO_REGION, regionId);
			if(!CGCollectionUtils.isEmpty(offices)){
				ofcDO = offices.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : OrganizationCommonDAOImpl.getOfficesByRegionAndOfficeType", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("OrganizationCommonDAOImpl.getOfficesByRegionAndOfficeType :: END");
		return ofcDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesAndAllHubOfficesofCityServicedByPincode(
			String pincode) throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesAndAllHubOfficesofCityServicedByPincode :: START");
		List<OfficeDO> officeDOs = null;
		try {
			
			String query = UdaanCommonConstants.QRY_GET_OFC_AND_HUB_OFC_OF_CITY_SERVICED_BY_PINCODE;
			
			String params[] = {UdaanCommonConstants.PARAM_PINCODE,UdaanCommonConstants.PARAM_HUB_OFC_CODE};
			
			Object values[] = {pincode,CommonConstants.OFF_TYPE_HUB_OFFICE};
			
			officeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params,	values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesAndAllHubOfficesofCityServicedByPincode", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesAndAllHubOfficesofCityServicedByPincode :: END");
		return officeDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByType(String offType)
			throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl :: getAllOfficesByType() :: START ");
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_OFFICES_BY_TYPE,
					UdaanCommonConstants.PARAM_OFF_TYPE, offType);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in OrganizationCommonDAOImpl :: getAllOfficesByType() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("OrganizationCommonDAOImpl :: getAllOfficesByType() :: END ");
		return offices;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean validateBranchPincodeServiceabilityForHubOffice(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGSystemException {
		LOGGER.trace("OrganizationCommonDAOImpl.validateBranchPincodeServiceabilityForHubOffice :: START");
		boolean isValidPincode = Boolean.FALSE;
		List<BranchPincodeServiceabilityDO> branchPincodeServiceableDOList =  null;
		try {
			if (!StringUtil.isNull(pincodeServicabilityTO.getOfficeId())){
				String query = "validateBranchPincodeServiceabilityForHubOffice";
				String params[] = { UdaanCommonConstants.OFFICE_ID,
						UdaanCommonConstants.PINCODE_ID };
				Object values[] = { pincodeServicabilityTO.getOfficeId(),
						pincodeServicabilityTO.getPincodeId() };
				branchPincodeServiceableDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(query, params, values);
			} else if(!StringUtil.isNull(pincodeServicabilityTO.getCityId())){
				String query = "validateBranchPincodeServiceabilityForHubOfficeByCity";
				String params[] = { UdaanCommonConstants.CITY_ID,
						UdaanCommonConstants.PINCODE_ID };
				Object values[] = { pincodeServicabilityTO.getCityId(),
						pincodeServicabilityTO.getPincodeId() };
				branchPincodeServiceableDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(query, params, values);
			}
			
			if (!StringUtil.isEmptyList(branchPincodeServiceableDOList)) {
				isValidPincode = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::validateBranchPincodeServiceabilityForHubOffice() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} 
		LOGGER.trace("OrganizationCommonDAOImpl.validateBranchPincodeServiceabilityForHubOffice :: END");
		return isValidPincode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesReportedToCity(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.getOfficecByCityIDForReport,
					UdaanCommonConstants.PARAM_CITY_ID, cityId);

		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesAndRHOOfcByCity(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.getAllOfficesAndRHOOfcByCity,
					UdaanCommonConstants.PARAM_CITY_ID, cityId);

		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;

	}

	@Override
	public List<OfficeDO> getAllOfficesByRegionAndOfficeType(Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		List<OfficeDO> offices = null;
		Session session = null;
		String ParamNames[] = { "regionId", "offTypeId" };
		Object values[] = { regionId, officeTypeId };
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getOfficeByRegionAndOfficeType",
							ParamNames, values);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return offices;
	}
}