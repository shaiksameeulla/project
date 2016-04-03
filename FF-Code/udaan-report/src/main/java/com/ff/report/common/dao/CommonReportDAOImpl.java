package com.ff.report.common.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignmentCustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.RateRevisionDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.RateIndustryTypeDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.report.wrapper.ClientGainedWrapperDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.manifest.LoadLotTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.LcCodReportAliasTO;
import com.ff.report.PriorityTypeTO;
import com.ff.report.ReportTypeTO;
import com.ff.report.action.ReportBaseAction.UserType;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.mec.LiabilityTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.dao.GeographyServiceDAOImpl;

/**
 * @author khassan
 * 
 */
/**
 * @author satheequ
 * 
 */
public class CommonReportDAOImpl extends CGBaseDAO implements CommonReportDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(GeographyServiceDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.report.common.dao.CommonReportDAO#getOfficesByCityIdForReport(
	 * java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByCityIdForReport(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> ofcDOList = null;
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: START");
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getOfficecByCityIDForReport", "cityId", cityId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesByCityIdForReport",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: END");

		return ofcDOList;
	}

	public List<OfficeDO> getAllHubsByCityID(Integer cityId)
			throws CGSystemException {
		List<OfficeDO> ofcDOList = null;
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getAllHubsByCityID", "cityId", cityId);

		} catch (Exception e) {
			LOGGER.error("CommonReportDAOImpl :: getAllHubsByCityID() ::", e);;
			throw new CGSystemException(e);
		}
		return ofcDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemTypeDO> getItemTypeListForUsed() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getActiveItemTypesForUsed", "itemHasSeries", "Y");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemTypeDO> getItemTypeList() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getActiveItemTypes", "isActive", "A");
	}

	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException {
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeId() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		try {
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCustomersByOfficeId", "officeId", officeId);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BusinessCommonDAOImpl::getCustomersByOfficeId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeId() :: End --------> ::::::");
		return customerDOs;
	}

	public List<EmployeeDO> getEmployeesByOfficeIdForReport(Integer cityId)
			throws CGSystemException {
		List<EmployeeDO> ofcDOList = null;
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: START");
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getEmployeesByOfficeId", "officeId", cityId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesByCityIdForReport",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: END");

		return ofcDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getSalesPersonsTitlesList(
			final String DepartmentType) throws CGSystemException {
		if (DepartmentType == "CLIENTGAINED_SALES") {
			List<EmployeeDO> nwList = null;
			Session session = null;
			String query = null;
			try {
				session = getHibernateTemplate().getSessionFactory()
						.openSession();
				query = CommonReportConstant.SALES_PERSON_QUERY;
				nwList = session
						.createSQLQuery(query)
						.setResultTransformer(
								Transformers.aliasToBean(EmployeeDO.class))
						.list();
			} catch (Exception e) {
				LOGGER.error("ERROR : CommonReportDAOImpl.getSalesPersons", e);
				throw new CGSystemException(e);
			} finally {
				session.close();
				session = null;
			}
			LOGGER.debug("CommonReportDAOImpl :: getSalesPersons() :: End ");
			return nwList;
		} else {
			LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
			List<EmployeeDO> employeeDOList = null;
			try {
				employeeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getEmployeesDtlsByDepartmentType",
								new String[] { "departmentName" },
								new Object[] { DepartmentType });
			} catch (Exception e) {
				LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getSalesPersonsTitlesList() :: "
						+ e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
			return employeeDOList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateIndustryTypeDO> getNatureOfBusinesses()
			throws CGSystemException {
		LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<RateIndustryTypeDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate().findByNamedQuery(
					"getRateIndustryType");
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LeadCommonDAOImpl::getSalesPersonsTitlesList() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadCommonDAOImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateProductCategoryTO> getRateProdcuts()
			throws CGBusinessException, CGSystemException {
		// Made changes for close session by Shaheed
		LOGGER.trace("LeadCommonDAOImpl :: getRateProdcuts() :: Start --------> ::::::");
		List<RateProductCategoryTO> list = null;
		Session session = getSession();
		try {
			if (session != null) {
				list = session
						.createCriteria(RateProductCategoryDO.class)
						.add(Restrictions.in("rateProductCategoryCode",
								new Object[] { "CO", "AR", "TR", "EC" }))
						.setProjection(
								Projections
										.projectionList()
										.add(Projections
												.property("rateProductCategoryId"),
												"rateProductCategoryId")
										.add(Projections
												.property("rateProductCategoryName"),
												"rateProductCategoryName"))
						.setResultTransformer(
								Transformers
										.aliasToBean(RateProductCategoryTO.class))
						.addOrder(Order.asc("rateProductCategoryName")).list();
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getRateProdcuts", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}
		LOGGER.debug("CommonReportDAOImpl :: getRateProdcuts() :: End ");
		return list;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<VobSlabTO> getSlabList(Integer productId)
			throws CGBusinessException, CGSystemException {
		List<VobSlabTO> list = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = CommonReportConstant.SLAB_QUERY;

			list = session
					.createSQLQuery(query)
					.addScalar("vobSlabId")
					.addScalar("lowerLimitLabel")
					.addScalar("upperLimitLabel")
					.setParameter("product", productId)
					.setResultTransformer(
							Transformers.aliasToBean(VobSlabTO.class)).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getRebillingGDRDetails",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("CommonReportDAOImpl :: getRegionSalesList() :: End --------> ::::::");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityByCityID(Integer cityId)
			throws CGBusinessException, CGSystemException {

		List<CityDO> ofcDOList = null;
		LOGGER.trace("CommonReportDAOImpl.getCityByCityID :: START");
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCityByCityId", "cityId", cityId);
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getCityByCityID", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl.getCityByCityID :: END");

		return ofcDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficecByCityIDAndUserIDForReport(Integer userId,
			Integer cityId) throws CGBusinessException, CGSystemException {

		Integer[] values = { userId, cityId };
		String[] params = { "userId", "cityId" };

		List<OfficeDO> ofcDOList = null;
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficecByCityIDAndUserIDForReport :: START");
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getOfficecByCityIDAndUserIDForReport", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficecByCityIDAndUserIDForReport",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficecByCityIDAndUserIDForReport :: END");

		return ofcDOList;
	}

	public Long getworkingDaysFromHoliday(Integer regionId, String fromDate)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getworkingDaysFromHoliday() :: Start --------> ::::::");
		Long count = 0L, count1 = 0L;
		// Session session = openTransactionalSession();
		Session session = null;
		try {
			session = createSession();
			// Query
			// query=session.createSQLQuery("select cn.* from ff_f_consignment cn join ff_f_booking bk on cn.CONSG_NO=bk.CONSG_NUMBER  join ff_d_product p on p.PRODUCT_ID=cn.PRODUCT where cn.BILLING_STATUS ='TBB' AND cn.FINAL_WEIGHT IS NOT NULL AND cn.DEST_PINCODE IS NOT NULL AND ((cn.CHANGED_AFTER_NEW_RATE_COMPONENT='N' AND (date_format(bk.BOOKING_DATE, '%d/%m/%Y') = date_format(DATE_SUB(CURRENT_DATE(),INTERVAL 0 DAY), '%d/%m/%Y') )) OR (cn.CHANGED_AFTER_NEW_RATE_COMPONENT='Y'))");
			Query query = session.createSQLQuery(
					CommonReportConstant.GET_ALL_OVERHOLIDAYS).setString(
					"monthPeriod", fromDate);
			BigInteger c = (BigInteger) query.uniqueResult();
			String a = c.toString();
			count = Long.parseLong(a);

			Query query1 = session
					.createSQLQuery(CommonReportConstant.GET_REGION_HOLIDAY)
					.setString("monthPeriod", fromDate)
					.setInteger("regionId", regionId);
			BigInteger c1 = (BigInteger) query1.uniqueResult();
			String a1 = c1.toString();
			count1 = Long.parseLong(a1);
			count = count + count1;
		} catch (Exception e) {
			LOGGER.error("ERROR :: CommonReportDAOImpl :: getworkingDaysFromHoliday()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			// closeTransactionalSession(session);
			closeSession(session);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDO> getOriginSectors() throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<SectorDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate().findByNamedQuery(
					"getSectors");
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getOriginSectorByRegionId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl :: getOriginSectorByRegionId() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateProductCategoryDO> getProductsByCustomer(Integer customerId)
			throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getProductsByCustomer() :: Start --------> ::::::");
		List<RateProductCategoryDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getProductsByCustomer",
							new String[] { "customerId" },
							new Object[] { customerId });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getProductsByCustomer() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl :: getProductsByCustomer() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByRegionIds(String regionIds)
			throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getCitiesByRegionIds() :: Start --------> ::::::");

		String[] regionIdContent = regionIds.split(",");
		Integer[] regionId = new Integer[regionIdContent.length];
		for (int i = 0; i < regionIdContent.length; i++) {
			regionId[i] = Integer.parseInt(regionIdContent[i]);
		}

		List<CityDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getCitiesByRegion",
							new String[] { "RegionId" },
							new Object[] { regionId });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCitiesByRegionIds() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl :: getCitiesByRegionIds() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByCityIds(String cityIds)
			throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getCitiesByRegionIds() :: Start --------> ::::::");

		String[] cityIdContent = cityIds.split(",");
		Integer[] cityId = new Integer[cityIdContent.length];
		for (int i = 0; i < cityIdContent.length; i++) {
			cityId[i] = Integer.parseInt(cityIdContent[i]);
		}

		List<OfficeDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getOfficeByCity",
							new String[] { "cityId" }, new Object[] { cityId });
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getOfficesByCityIds() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl :: getOfficesByCityIds() :: End --------> ::::::");
		return employeeDOList;
	}

	@SuppressWarnings("rawtypes")
	public List getMonthList() throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl::getMonthList ::START");
		Session session = null;
		List compList = null;
		List componentsValue = null;
		String sql = CommonReportConstant.MONTH_YEAR;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createSQLQuery(sql);
			componentsValue = query.list();
			compList = prepareMonthList(componentsValue);
		} catch (Exception e) {
			LOGGER.error("ERROR :: CommonReportDAOImpl::getMonthList ::", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("CommonReportDAOImpl::getMonthList ::END");
		return compList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List prepareMonthList(List components) throws CGSystemException {
		List compList = new ArrayList(components.size());
		if (components != null && !components.isEmpty()) {
			// Preparing components and dependent components list
			for (int i = 0; i < components.size(); i++) {
				compList.add(components.get(i));
			}
		}

		return compList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionDO> getAllRegionForBoUser(Integer userId, Integer officeId)
			throws CGSystemException, CGBusinessException {

		List<RegionDO> regionDOList = null;
		Integer[] values = { userId, officeId };
		String[] params = { "userId", "officeId" };
		try {
			regionDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getAllRegionForBOUser",
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getAllRegionForBoUser ::", e);
			throw new CGSystemException(e);
		}

		return regionDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityByRegionForBranchUser(Integer userId,
			Integer regionId) throws CGSystemException, CGBusinessException {

		List<CityDO> cityDOList = null;
		Integer[] values = { userId, regionId };
		String[] params = { "userId", "regionID" };
		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByRegionForBranchUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getAllRegionForBoUser ::", e);
			throw new CGSystemException(e);
		}

		return cityDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeForBOUser(Integer cityId, Integer userID)
			throws CGSystemException, CGBusinessException {

		List<OfficeDO> officeDOList = null;
		Integer[] values = { cityId, userID };
		String[] params = { "cityId", "userId" };

		try {
			officeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getOfficecOfBranchUserByCityIDAndUserID", params,
							values);
		} catch (Exception e) {
			LOGGER.error("ERROR :: CommonReportDAOImpl::getOfficeForBOUser ::",
					e);
			throw new CGSystemException(e);
		}

		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionDO> getAllRegionByUserType(Integer userId,
			Integer officeId, String officeType) throws CGSystemException,
			CGBusinessException {

		List<RegionDO> regionDOList = null;
		Object[] values = { officeType, userId, officeId };
		String[] params = { "officeType", "userId", "officeId" };
		try {
			regionDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getAllRegionByUsertype",
							params, values);
		} catch (Exception e) {
			LOGGER.error("ERROR :: CommonReportDAOImpl::getOfficeForBOUser ::",
					e);
			throw new CGSystemException(e);
		}

		return regionDOList;
	}

	@Override
	public List<CityDO> getCitiesByRegionForHubUser(Integer userId,
			Integer officeId, Integer regionID) throws CGSystemException,
			CGBusinessException {

		List<CityDO> cityDOList = null;
		Integer[] values = { userId, officeId, regionID };
		String[] params = { "userId", "officeId", "regionID" };

		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByRegionForHubUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}
		return cityDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeByUserType(String officeType,
			Integer userId, Integer officeId, Integer cityId)
			throws CGSystemException, CGBusinessException {

		List<OfficeDO> officeDOList = null;
		List<OfficeDO> rptOfficeList = null;
		Object[] values = { officeType, userId, cityId };
		String[] params = { "officeType", "userId", "cityId" };
		Object[] nwValues = { officeId, cityId };
		String[] nwParams = { "officeId", "cityId" };
		try {
			if (officeType == UserType.HO.toString()) {
				/** Getting all mapped Hubs */
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getOfficecByUserType",
								params, values);
				/** Getting all mapped branches */
				Object[] newValues = { UserType.BO.toString(), userId, cityId };
				rptOfficeList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getOfficecByUserType",
								params, newValues);
				officeDOList.addAll(rptOfficeList);

			} else if (officeType == UserType.BO.toString()) {
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getOfficecByUserType",
								params, values);

			} else {
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getOfficecByUserType",
								params, values);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}

		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getCitiesByRegionForRhoUser(Integer userId,
			Integer officeId, Integer regionID) throws CGSystemException,
			CGBusinessException {

		List<CityDO> cityDOList = null;
		Integer[] values = { userId, officeId, regionID };
		String[] params = { "userId", "officeId", "regionID" };

		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByRegionForRhoUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}
		return cityDOList;
	}

	@SuppressWarnings("unchecked")
	public List<StockStandardTypeDO> getStandardTypeForLcCod(String typeName)
			throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl::getStandardTypeForLcCod()::START");
		List<StockStandardTypeDO> stockTypeDOList = null;
		try {
			stockTypeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							CommonReportConstant.QRY_GET_Type_Name, "type",
							typeName);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl :: getStandardTypeForLcCod()::::::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl::getStandardTypeForLcCod()::END");
		return stockTypeDOList;
	}

	public List<LcCodReportAliasTO> getCustomerByRegionAndProduct(
			List<Integer> regionId, List<String> prodSeries)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByRegionAndProduct() :: Start --------> ::::::");
		List<LcCodReportAliasTO> billAliasTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = CommonReportConstant.QRY_CUSTOMERSBY_REGION_PRODUCT;
			billAliasTOs = session
					.createSQLQuery(query)
					.addScalar("customerId")
					.addScalar("customerName")
					.addScalar("custCode")
					.setParameterList("regionId", regionId)
					.setParameterList("prodSeries", prodSeries)
					.setResultTransformer(
							Transformers.aliasToBean(LcCodReportAliasTO.class))
					.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : CommonReportDAOImpl.getCustomerByRegionAndProduct",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("CommonReportDAOImpl :: getCustomerByRegionAndProduct() :: End --------> ::::::");
		return billAliasTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeForMultipleCitiesForBOUser(Integer[] cityId,
			Integer userID) throws CGSystemException, CGBusinessException {

		List<OfficeDO> officeDOList = null;

		Object[] values = { cityId, userID };
		String[] params = { "cityId", "userId" };

		try {
			officeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getOfficecOfBranchUserByMultipleCityIDAndUserID",
							params, values);

		} catch (Exception e) {
			LOGGER.error("ERROR :: CommonReportDAOImpl::getOfficeForBOUser ::",
					e);
			throw new CGSystemException(e);
		}

		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficeForMultipleCitiesUserType(String officeType,
			Integer userId, Integer officeId, Integer[] cityId)
			throws CGSystemException, CGBusinessException {

		List<OfficeDO> officeDOList = null;
		List<OfficeDO> rptOfficeList = null;
		Object[] values = { officeType, userId, cityId };
		String[] params = { "officeType", "userId", "cityId" };
		Object[] nwValues = { officeId, cityId };
		String[] nwParams = { "officeId", "cityId" };

		Object[] rhoValues = { userId, cityId, officeId };
		String[] rhoparams = { "userId", "cityId", "officeId" };

		try {
			if (officeType == UserType.HO.toString()) {
				/** Getting all mapped Hubs */
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getMultipleOfficecByUserType", params, values);
				/** Getting all mapped branches */
				Object[] newValues = { UserType.BO.toString(), userId, cityId };
				rptOfficeList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getMultipleOfficecByUserType", params,
								newValues);
				officeDOList.addAll(rptOfficeList);

			} else if (officeType == UserType.BO.toString()) {
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getMultipleOfficecByUserType", params, values);

			} else if (officeType == UserType.RO.toString()) {
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getReportingOfficesForRhoUser", rhoparams,
								rhoValues);
			} else {
				officeDOList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getMultipleOfficecByUserType", params, values);
				rptOfficeList = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getOfficecOfRhoUserByMultipleCityIDAndUserID",
								nwParams, nwValues);
				officeDOList.addAll(rptOfficeList);

			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}

		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getOfficesByMultipleCityIdForReport(Integer[] cityId)
			throws CGBusinessException, CGSystemException {

		List<OfficeDO> ofcDOList = null;
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: START");
		try {
			ofcDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getOfficecByMultipleCityIDForReport", "cityId", cityId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OrganizationCommonDAOImpl.getOfficesByCityIdForReport",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("OrganizationCommonDAOImpl.getOfficesByCityIdForReport :: END");

		return ofcDOList;
	}

	// -------

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityByMultipleRegionForBranchUser(Integer userId,
			Integer[] regionId) throws CGSystemException, CGBusinessException {

		List<CityDO> cityDOList = null;
		Object[] values = { userId, regionId };
		String[] params = { "userId", "regionID" };
		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByMultipleRegionForBranchUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getAllRegionForBoUser ::", e);
			throw new CGSystemException(e);
		}

		return cityDOList;
	}

	@Override
	public List<CityDO> getCitiesByMultipleRegionForHubUser(Integer userId,
			Integer officeId, Integer[] regionID) throws CGSystemException,
			CGBusinessException {

		List<CityDO> cityDOList = null;
		Object[] values = { userId, officeId, regionID };
		String[] params = { "userId", "officeId", "regionID" };

		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByMultipleRegionForHubUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}
		return cityDOList;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getCitiesByMultipleRegionForRhoUser(Integer userId,
			Integer officeId, Integer[] regionID) throws CGSystemException,
			CGBusinessException {

		List<CityDO> cityDOList = null;
		Object[] values = { userId, officeId, regionID };
		String[] params = { "userId", "officeId", "regionID" };

		try {
			cityDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByMultipleRegionForRhoUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}
		return cityDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByMultipleRegionIds(Integer[] regionIds)
			throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getCitiesByRegionIds() :: Start --------> ::::::");

		List<CityDO> employeeDOList = null;
		try {
			employeeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getCitiesByRegion",
							new String[] { "RegionId" },
							new Object[] { regionIds });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCitiesByRegionIds() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CommonReportDAOImpl :: getCitiesByRegionIds() :: End --------> ::::::");
		return employeeDOList;
	}

	/**
	 * Gets the category.
	 * 
	 * @return the category
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CategoryReportAliasTO> getCategory() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CommonReportDAOImpl :: getCategory() :: Start");
		List<String> category = null;
		Session session = null;
		List<CategoryReportAliasTO> categoryAliasTOs = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			categoryAliasTOs = session
					.createSQLQuery(CommonReportConstant.QRY_GET_CATEGORY)
					.addScalar("categoryDesc")
					.addScalar("categoryCode")
					.setResultTransformer(
							Transformers
									.aliasToBean(CategoryReportAliasTO.class))
					.list();
			// category =
			// session.createSQLQuery(CommonReportConstant.QRY_GET_CATEGORY).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getCategory", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}
		LOGGER.debug("CommonReportDAOImpl :: getCategory() :: End :");
		return categoryAliasTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.report.common.dao.CommonReportDAO#getLoad()
	 */
	@SuppressWarnings("unchecked")
	public List<LoadLotTO> getLoad() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CommonReportDAOImpl :: getLoad() :: Start");
		List<LoadLotTO> load = null;
		try {
			load = getHibernateTemplate().findByNamedQuery("getLoadNo");
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getLoad", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getLoad() :: End :");
		return load;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getReportingOfficesForRhoUser(Integer userId,
			Integer cityId, Integer officeId) throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getReportingOfficesForRhoUser() :: Start");
		List<OfficeDO> officeDOList = null;
		Object[] values = { userId, cityId, officeId };
		String[] params = { "userId", "cityId", "officeId" };
		try {
			officeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getReportingOfficesForRhoUser", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: CommonReportDAOImpl::getCitiesByRegionHubUser ::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getReportingOfficesForRhoUser() :: END");
		return officeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomerByBranchAndRateProductCategory(
			Integer[] officeIds, Integer rateProduct) throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByBranchAndRateProductCategory() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		List<Object[]> custDetails = null;
		String[] params = { CommonReportConstant.QRY_PARAM_OFFICE_ID,
				CommonReportConstant.QRY_PARAM_RATE_PRODUCT_CATEGORY };
		Object[] values = { officeIds, rateProduct };
		try {
			custDetails = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							CommonReportConstant.QRY_GET_CUSTOMER_BY_OFFICE_AND_RATE_PRODUCT,
							params, values);
			customerDOs = new ArrayList<CustomerDO>(custDetails.size());
			for (Object[] cust : custDetails) {
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId((Integer) cust[0]);
				if (!StringUtil.isNull(cust[1])) {
					customer.setCustomerCode(cust[1].toString());
				}
				if (!StringUtil.isNull(cust[2])) {
					customer.setBusinessName(cust[2].toString());
				}
				customerDOs.add(customer);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomerByBranchAndRateProductCategory() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByBranchAndRateProductCategory() :: End --------> ::::::");
		return customerDOs;
	}

	@Override
	public List<CustomerDO> getCustomersByOfficeIds(Integer[] officeId)
			throws CGSystemException {

		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeIds() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		try {
			customerDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCustomersByOfficeId", "officeId", officeId);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BusinessCommonDAOImpl::getCustomersByOfficeId() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOfficeIds() :: End --------> ::::::");
		return customerDOs;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomersByOffIds(Integer[] officeIds,
			Integer regionId) throws CGSystemException {
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOffIds() :: START --------> ::");
		List<CustomerDO> customerDOs = null;
		try {
			if (!StringUtil.isNull(regionId)) {
				customerDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								"getCustomersByRegionId", "regionId", regionId);
			} else {
				customerDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam("getCustomersByOffIds",
								"officeIds", officeIds);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in :: BusinessCommonDAOImpl :: getCustomersByOffIds() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BusinessCommonDAOImpl :: getCustomersByOffIds() :: END --------> ::");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomerByStationAndProduct(
			Integer[] productIds, Integer stationId) throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStationAndProduct() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		List<Object[]> custDetails = null;
		String[] params = { "productId", "cityId" };
		Object[] values = { productIds, stationId };
		try {
			custDetails = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							CommonReportConstant.QRY_GET_CUSTOMER_BY_STATION_AND_PRODUCT,
							params, values);
			customerDOs = new ArrayList<CustomerDO>(custDetails.size());
			for (Object[] cust : custDetails) {
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId((Integer) cust[0]);
				if (!StringUtil.isNull(cust[1])) {
					customer.setBusinessName(cust[1].toString());
				}
				if (!StringUtil.isNull(cust[1])) {
					customer.setCustomerCode(cust[1].toString());
				}
				if (!StringUtil.isNull(cust[2])) {
					customer.setBusinessName(cust[2].toString());
				}
				customerDOs.add(customer);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomerByStationAndProduct() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStationAndProduct() :: End --------> ::::::");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomerByStation(Integer stationId)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		List<Object[]> custDetails = null;
		String[] params = { "cityId" };
		Object[] values = { stationId };
		try {
			custDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(
					CommonReportConstant.QRY_GET_CUSTOMER_BY_STATION, params,
					values);
			customerDOs = new ArrayList<CustomerDO>(custDetails.size());
			for (Object[] cust : custDetails) {
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId((Integer) cust[0]);
				if (!StringUtil.isNull(cust[1])) {
					customer.setBusinessName(cust[1].toString());
				}
				if (!StringUtil.isNull(cust[2])) {
					customer.setBusinessName(cust[2].toString());
				}
				customerDOs.add(customer);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomerByStation() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation() :: End --------> ::::::");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductDO> getProductByCustomers(Integer[] customerIds)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: Start --------> ::::::");
		List<ProductDO> productDOs = null;
		String[] params = { "custIds" };
		Object[] values = { customerIds };
		try {
			// logic for All by sending value zero
			int id = customerIds[0];
			if (id == -1) {
				productDOs = getHibernateTemplate()
						.findByNamedQuery(
								CommonReportConstant.QRY_GET_PRODUCTS_FOR_ALL_CUSTOMERS);
			} else {
				productDOs = getHibernateTemplate()
						.findByNamedQueryAndNamedParam(
								CommonReportConstant.QRY_GET_PRODUCTS_BY_CUSTOMERS,
								params, values);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getProductByCustomers() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: End --------> ::::::");
		return productDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LiabilityDO> getchequeNumbers(Integer customerId,
			Date fromDate, Date toDate) throws CGBusinessException,
			CGSystemException {
		// customerChequeNo
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: Start --------> ::::::");
		List<LiabilityDO> liabilityDO = null;
		String[] params = { "customerId", "fromDate", "toDate" };
		Object[] values = { customerId, fromDate, toDate };
		String queryName = "customerChequeNo";
		try {
			liabilityDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getProductByCustomers() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: End --------> ::::::");
		return liabilityDO;
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LiabilityTO> getchequeNumbersByRegion(Integer regionId,
			Date fromDate, Date toDate) throws CGBusinessException,
			CGSystemException {
		// customerChequeNo
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: Start --------> ::::::");
		List<LiabilityTO> categoryAliasTOs = null;
		//List<String> category = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			categoryAliasTOs = session
					.createSQLQuery(CommonReportConstant.QRY_GET_CHEQUE_BY_REGION)
					.addScalar("chqNo")
					.setParameter("regionId", regionId)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.setResultTransformer(
							Transformers
									.aliasToBean(LiabilityTO.class))
					.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getProductByCustomers() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getProductByCustomers() :: End --------> ::::::");
		return categoryAliasTOs;
	}
	
	
	/**
	 * This method gets the customers on the basis of below criteria, officeIds
	 * - customers with type CR/CC/LC/CD/GV/FR having given office(s) as its
	 * Pickup/Billing office customers with type AC having given office(s) as
	 * its Mapped To office cityId - customers with type BA/BV having given city
	 * as city of its Mapped To office and all Reverse Logistics (RL) customers
	 */
	@Override
	public List<CustomerDO> getCustomersByContractBranches(Integer[] officeIds,
			Integer cityId) throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomersByContractBranches() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		List<CustomerDO> custDetails = null;
		String[] params = { "officeIds", "cityId" };
		Object[] values = { officeIds, cityId };
		try {
			custDetails = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							CommonReportConstant.QRY_GET_CUSTOMERS_BY_CONTRACT_BRANCHES,
							params, values);
			customerDOs = new ArrayList<CustomerDO>(custDetails.size());
			for (CustomerDO cust : custDetails) {
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId((Integer) cust.getCustomerId());
				if (!StringUtil.isNull(cust.getBusinessName())) {
					customer.setBusinessName(cust.getBusinessName());
				}
				if (!StringUtil.isNull(cust.getCustomerCode())) {
					customer.setCustomerCode(cust.getCustomerCode());
				}
				customerDOs.add(customer);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomersByContractBranches() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomersByContractBranches() :: End --------> ::::::");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransportModeDO> getAllTransportModeList()
			throws CGSystemException {
		LOGGER.info("CommonReportDAOImpl :: getAllTransportModeList() :: Start --------> ::::::");
		List<TransportModeDO> transportModeDOList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			transportModeDOList = (List<TransportModeDO>) session
					.createCriteria(TransportModeDO.class).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getAllTransportModeList()"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("CommonReportDAOImpl :: getAllTransportModeList() :: End --------> ::::::");
		return transportModeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceByTypeDO> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGSystemException {
		LOGGER.trace("CommonReportDAOImpl :: getServiceByTypeListByTransportModeId() :: Start --------> ::::::");
		Session session = null;
		List<ServiceByTypeDO> serviceByTypeDOList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			serviceByTypeDOList = (List<ServiceByTypeDO>) session
					.createCriteria(ServiceByTypeDO.class)
					.add(Restrictions
							.eq(UdaanCommonConstants.TRANSPORT_MODE_DO_TRANSPORT_MODE_ID_PARAM,
									transportModeId)).list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getServiceByTypeListByTransportModeId() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("CommonReportDAOImpl :: getServiceByTypeListByTransportModeId() :: End --------> ::::::");
		return serviceByTypeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getAllCities() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CommonReportDAOImpl :: getAllCities() :: START --------> ::::::");
		List<CityDO> list = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = "from com.ff.domain.geography.CityDO c order by c.cityName";
			list = session.createQuery(query).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getAllCities", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("CommonReportDAOImpl :: getAllCities() :: End --------> ::::::");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentCustomerTO> getCustomersByContractBranchesForConsignmentDetails(
			Integer[] officeIds, Integer[] cityId) throws CGSystemException {

		LOGGER.debug("CommonReportDAOImpl :: getCustomersByContractBranchesForConsignmentDetails() :: Start --------> ::::::");
		List<ConsignmentCustomerTO> consignmentCustomerTOs = null;
		Session session = null;
		String query = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			query = CommonReportConstant.GET_CUSTOMER_FOR_CONSIGNMENT_REPORT;
			consignmentCustomerTOs = session
					.createSQLQuery(query)
					.addScalar("customerId")
					.addScalar("customerCode")
					.addScalar("businessName")
					.setParameterList("officeIds", officeIds)
					.setParameterList("cityId", cityId)
					.setResultTransformer(
							Transformers
									.aliasToBean(ConsignmentCustomerTO.class))
					.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : CommonReportDAOImpl.getCustomersByContractBranchesForConsignmentDetails",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}

		LOGGER.debug("CommonReportDAOImpl :: getCustomersByContractBranchesForConsignmentDetails() :: End --------> ::::::");
		return consignmentCustomerTOs;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomerByStation(Integer[] stationId)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation() :: Start --------> ::::::");
		List<CustomerDO> customerDOs = null;
		List<Object[]> custDetails = null;
		String[] params = { "cityId" };
		Object[] values = { stationId };
		try {
			custDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(
					CommonReportConstant.QRY_GET_CUSTOMER_BY_STATION, params,
					values);
			customerDOs = new ArrayList<CustomerDO>(custDetails.size());
			for (Object[] cust : custDetails) {
				CustomerDO customer = new CustomerDO();
				customer.setCustomerId((Integer) cust[0]);
				if (!StringUtil.isNull(cust[1])) {
					customer.setBusinessName(cust[1].toString());
				}
				if (!StringUtil.isNull(cust[2])) {
					customer.setBusinessName(cust[2].toString());
				}
				customerDOs.add(customer);
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomerByStation() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation() :: End --------> ::::::");
		return customerDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateRevisionDO> getCustomerByStation1(List<Integer> stationId)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation1() :: Start --------> ::::::");
		List<RateRevisionDO> customerDOs = null;
		Session session = null;
		try {
			
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query =session.getNamedQuery(CommonReportConstant.QRY_GET_CUSTOMER_BY_STATION);
			query.setParameterList("cityId", stationId);
			query.setResultTransformer(Transformers.aliasToBean(RateRevisionDO.class));
			customerDOs = query.list();
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CommonReportDAOImpl::getCustomerByStation1() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CommonReportDAOImpl :: getCustomerByStation1() :: End --------> ::::::");
		return customerDOs;

	}

	/**
	 * 
	 * @param cityId
	 * @param officeId
	 * @param region
	 * @return
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	public List<ClientGainedWrapperDO> getSalesPersonsForClientGained(
			Integer officeId, Integer cityId, Integer regionId)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getSalesPersonsForClientGained() :: START ");
		List<ClientGainedWrapperDO> empDtlsList = null;
		Session session = null;
		String query = null;
		Query qry = null;
		try {
			session = createSession();
			query = CommonReportConstant.SALES_PERSON_QUERY_FOR_CLIENTGAINED;
			qry = session.createSQLQuery(query);
			qry.setParameter("regionId", regionId);
			qry.setParameter("cityId", cityId);
			qry.setParameter("officeId", officeId);
			qry.setResultTransformer(Transformers
					.aliasToBean(ClientGainedWrapperDO.class));
			empDtlsList = (List<ClientGainedWrapperDO>) qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : CommonReportDAOImpl.getSalesPersonsForClientGained",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CommonReportDAOImpl :: getSalesPersonsForClientGained() :: END ");
		return empDtlsList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClientGainedWrapperDO> getSalesPersonsForProspects(
			Integer officeId, Integer cityId, Integer regionId)
			throws CGSystemException {
		LOGGER.debug("CommonReportDAOImpl :: getSalesPersonsForClientGained() :: START ");
		List<ClientGainedWrapperDO> empDtlsList = null;
		Session session = null;
		String query = null;
		Query qry = null;
		try {
			session = createSession();
			query = CommonReportConstant.SALES_PERSON_QUERY_FOR_PROSPECTS;
			qry = session.createSQLQuery(query);
			qry.setParameter("regionId", regionId);
			qry.setParameter("cityId", cityId);
			qry.setParameter("officeId", officeId);
			qry.setResultTransformer(Transformers
					.aliasToBean(ClientGainedWrapperDO.class));
			empDtlsList = (List<ClientGainedWrapperDO>) qry.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : CommonReportDAOImpl.getSalesPersonsForClientGained",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CommonReportDAOImpl :: getSalesPersonsForClientGained() :: END ");
		return empDtlsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportTypeTO> getReportType() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CommonReportDAOImpl :: getCategory() :: Start");
		List<String> category = null;
		Session session = null;
		List<ReportTypeTO> categoryAliasTOs = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			categoryAliasTOs = session
					.createSQLQuery(CommonReportConstant.QRY_GET_REPORT_TYPE)
					.addScalar("id")
					.addScalar("stdTypeCode")
					.addScalar("description")
					.setResultTransformer(
							Transformers
									.aliasToBean(ReportTypeTO.class))
					.list();
			// category =
			// session.createSQLQuery(CommonReportConstant.QRY_GET_CATEGORY).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getCategory", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}
		LOGGER.debug("CommonReportDAOImpl :: getCategory() :: End :");
		return categoryAliasTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriorityTypeTO> getPriorityType() throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CommonReportDAOImpl :: getPriorityType() :: Start");
		List<String> category = null;
		Session session = null;
		List<PriorityTypeTO> categoryAliasTOs = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			categoryAliasTOs = session
					.createSQLQuery(CommonReportConstant.QRY_GET_PRIORITY_TYPE)
					.addScalar("id")
					.addScalar("stdTypeCode")
					.addScalar("description")
					.setResultTransformer(
							Transformers
									.aliasToBean(PriorityTypeTO.class))
					.list();
			// category =
			// session.createSQLQuery(CommonReportConstant.QRY_GET_CATEGORY).list();
		} catch (Exception e) {
			LOGGER.error("ERROR : CommonReportDAOImpl.getPriorityType", e);
			throw new CGSystemException(e);
		} finally {
			session.close();
			session = null;
		}
		LOGGER.debug("CommonReportDAOImpl :: getCategory() :: End :");
		return categoryAliasTOs;
	}

}
