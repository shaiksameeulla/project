package com.ff.universe.geography.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.CountryDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.geography.PincodeProductServiceabilityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.geography.ZoneDO;
import com.ff.domain.organization.BranchPincodeServiceabilityDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;

public class GeographyServiceDAOImpl extends CGBaseDAO implements
		GeographyServiceDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(GeographyServiceDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCity(String Pincode) throws CGSystemException {
		CityDO city = null;
		try {
			List<CityDO> cityDOs = null;
			String queryName = "getCity";
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "Pincode", Pincode);
			if (!StringUtil.isEmptyList(cityDOs)) {
				city = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		return city;
	}

	@Override
	public List<PincodeDO> validatePincode(PincodeTO pincodeTo)
			throws CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("GeographyServiceDAOImpl::validatePincode----------->START:::::::");
		Session session = null;
		Criteria pincodeCriteria = null;
		List<PincodeDO> pincodeDOs = null;
		try {
			session = createSession();
			pincodeCriteria = session.createCriteria(PincodeDO.class);
			if(pincodeTo!=null){
				if (!StringUtil.isStringEmpty(pincodeTo.getPincode())) {
					pincodeCriteria
					.add(Restrictions.eq("pincode", pincodeTo.getPincode()));
				}
				if (pincodeTo.getPincodeId() != null) {
					pincodeCriteria.add(Restrictions.eq("pincodeId",
							pincodeTo.getPincodeId()));
				}
				
				if (!StringUtil.isEmptyInteger(pincodeTo.getCityId())) {
					pincodeCriteria
					.add(Restrictions.eq("cityId", pincodeTo.getCityId()));
				}
			}
			pincodeCriteria.add(Restrictions.eq("status","A"));
			
			pincodeDOs = pincodeCriteria.list();
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.validatePincode", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("GeographyServiceDAOImpl::validatePincode----------->END:::::::");
		return pincodeDOs;
	}

	public PincodeDO validatePincodeServiceability(String pinCode,
			Integer officeId) throws CGSystemException {
		PincodeDO pincode = null;
		List<PincodeDO> pincodeDOs = null;
		try {
			String[] params = { "officeId", "pincode" };
			Object[] values = { officeId, pinCode };
			pincodeDOs = (List<PincodeDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"validatePincodeServiceability", params, values);
			if (pincodeDOs != null && pincodeDOs.size() > 0)
				pincode = pincodeDOs.get(0);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.validatePincodeServiceability", e);
			throw new CGSystemException(e);
		}
		return pincode;
	}

	@SuppressWarnings("unchecked")
	public List<PincodeProductServiceabilityDO> getPincodeDlvTimeMaps(
			String pinCode, Integer cityId, String consgSeries)
			throws CGSystemException {
		List<PincodeProductServiceabilityDO> pincodeDlvTimeMapDOs = null;
		try {
			String[] params = { "pincode", "consgSeries" };
			Object[] values = { pinCode, consgSeries };
			pincodeDlvTimeMapDOs = (List<PincodeProductServiceabilityDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getPincodeProdServiceability", params, values);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getPincodeDlvTimeMaps", e);
			throw new CGSystemException(e);
		}
		return pincodeDlvTimeMapDOs;
	}
	
	@Override
	public List<Integer> isValidPincodeProductServiceability(
			Integer pincodeId,Integer productId)
			throws CGSystemException {
		List<Integer> pincodeDlvTimeMapDOs = null;
		try {
			String[] params = { "pincodeId", "productId" };
			Object[] values = { pincodeId, productId };
			pincodeDlvTimeMapDOs =  getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"isValidPincodeProdServiceabilityByPincodeIdAndSeries", params, values);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getPincodeDlvTimeMaps", e);
			throw new CGSystemException(e);
		}
		return pincodeDlvTimeMapDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionDO> getAllRegions() throws CGSystemException {
		List<RegionDO> regionDOs = null;
		try {
			regionDOs = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_REGIONS);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getAllRegions", e);
			throw new CGSystemException(e);
		}
		return regionDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByRegion(Integer regionId)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {
			String queryName = UdaanCommonConstants.QRY_GET_CITIES_BY_REGION;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, UdaanCommonConstants.REGION_ID, regionId);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByRegion", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getTranshipmentCitiesByRegion(RegionTO regionTO)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {
			String queryName = UdaanCommonConstants.QRY_GET_TRANSHIPMENT_CITIES_BY_REGION;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, UdaanCommonConstants.REGION_ID,
					regionTO.getRegionId());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GeographyServiceDAOImpl.getTranshipmentCitiesByRegion",
					e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCity(CityTO cityTO) throws CGSystemException {
		try {
			List<CityDO> cityDOs = null;
			String queryName = null;

			if (cityTO.getCityId() != null) {
				queryName = "getCityByCityId";
				cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
						queryName, "cityId", cityTO.getCityId());
			} else {
				queryName = "getCityByCityCode";
				cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
						queryName, "cityCode", cityTO.getCityCode());
			}
			if (cityDOs != null && cityDOs.size() > 0) {
				return cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean pincodeServiceabilityByCity(PincodeTO pincodeTO)
			throws CGSystemException {
		boolean isValidPincode = Boolean.FALSE;
		String[] paramNames = { UdaanCommonConstants.CITY_ID,
				UdaanCommonConstants.PINCODE_ID };
		Object[] values = { pincodeTO.getCityIdsList(),
				pincodeTO.getPincodeId() };
		List<PincodeDO> pincodeDOs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						UdaanCommonConstants.QRY_PINCODE_SERVICEABILITY_BY_CITY,
						paramNames, values);
		if (!StringUtil.isEmptyList(pincodeDOs)) {
			isValidPincode = Boolean.TRUE;
		}
		return isValidPincode;
	}

	@Override
	public List<CityDO> getAllCities() throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {
			cityDOs = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_CITIES);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getAllCities", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByCity(CityTO cityTO) throws CGSystemException {
		List<CityDO> cityDOs = null;
		Session session = null;

		Criteria criteria = null;
		try {

			session = createSession();
			criteria = session.createCriteria(CityDO.class, "city");
			if(!StringUtil.isNull(cityTO)){
				if (!StringUtil.isEmptyInteger(cityTO.getCityId())) {
					criteria.add(Restrictions.eq("city.cityId", cityTO.getCityId()));
				}
				if (!StringUtil.isStringEmpty(cityTO.getCityCode())) {
					criteria.add(Restrictions.eq("city.cityCode",
							cityTO.getCityCode()));
				}
				if (!StringUtil.isEmptyInteger(cityTO.getRegion())) {
					criteria.add(Restrictions.eq("city.region", cityTO.getRegion()));
				}
			}
			criteria.addOrder(Order.asc("city.cityName"));
			cityDOs = criteria.list();

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		// LOGGER.info("PureRouteDAOImpl :: getFlightDetails() :: End --------> ::::::");

		return cityDOs;
	}

	@SuppressWarnings("unchecked")
	public List<CityDO> getAllCitiesByCityIds(List<CityTO> cityTOList)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		Session session = null;

		Criteria criteria = null;
		try {

			session = createSession();
			criteria = session.createCriteria(CityDO.class, "city");

			List<Integer> cityId = new ArrayList<Integer>();
			List<String> cityCode = new ArrayList<String>();
			List<Integer> cityRegion = new ArrayList<Integer>();
			if (!CGCollectionUtils.isEmpty(cityTOList)) {

				for (CityTO cityTO : cityTOList) {
					if (!StringUtil.isEmptyInteger(cityTO.getCityId())) {
						cityId.add(cityTO.getCityId());
					}
					if (!StringUtil.isStringEmpty(cityTO.getCityCode())) {
						cityCode.add(cityTO.getCityCode());
					}
					if (!StringUtil.isEmptyInteger(cityTO.getRegion())) {
						cityRegion.add(cityTO.getRegion());
					}
				}

				if (!CGCollectionUtils.isEmpty(cityId)) {
					criteria.add(Restrictions.in("city.cityId", cityId));
				}
				if (!CGCollectionUtils.isEmpty(cityCode)) {
					criteria.add(Restrictions.in("city.cityCode", cityCode));
				}
				if (!CGCollectionUtils.isEmpty(cityRegion)) {
					criteria.add(Restrictions.in("city.region", cityRegion));
				}
				criteria.addOrder(Order.asc("city.cityName"));

				cityDOs = criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return cityDOs;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isRegionExists(String regionCode) throws CGSystemException {
		List<RegionDO> regionDOs = null;
		boolean isRegionExists = Boolean.FALSE;
		try {
			regionDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.IS_REGION_EXISTS, "regionCode",
					regionCode);
			if (!regionDOs.isEmpty()) {
				isRegionExists = Boolean.TRUE;
			} else {
				isRegionExists = Boolean.FALSE;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.isRegionExists", e);
			throw new CGSystemException(e);
		}
		return isRegionExists;
	}

	@Override
	public boolean isPincodeServicedByProduct(
			PincodeServicabilityTO pincodeProdServicebility)
			throws CGSystemException,CGBusinessException {
		List<PincodeProductServiceabilityDO> pinProdSerDos = null;
		Session session = null;
		boolean isPincodeServicedByProduct = Boolean.FALSE;
		PincodeProductServiceabilityDO pinProdServiceabilityDO = null;
		try {
			pinProdSerDos = getPincodeDlvTimeMaps(
					pincodeProdServicebility.getPincode(),
					pincodeProdServicebility.getCityId(),
					pincodeProdServicebility.getConsgSeries());
			if (!StringUtil.isEmptyList(pinProdSerDos)) {
				pinProdServiceabilityDO = pinProdSerDos.get(0);
				if (!StringUtil.isStringEmpty(pinProdServiceabilityDO
						.getProductGroupId().getIsOriginUndefined())) {
					if (StringUtil.endsWithIgnoreCase(CommonConstants.NO,
							pinProdServiceabilityDO.getProductGroupId()
									.getIsOriginUndefined())) {
						for (PincodeProductServiceabilityDO pinCodeProd : pinProdSerDos) {
							if (!StringUtil.isNull(pinCodeProd
									.getOriginCity()) && !StringUtil.isEmptyInteger(pinCodeProd
									.getOriginCity().getCityId())) {
								if (pincodeProdServicebility.getCityId()
										.intValue() == pinCodeProd
										.getOriginCity().getCityId().intValue()) {
									isPincodeServicedByProduct = Boolean.TRUE;
									return isPincodeServicedByProduct;

								}
							} /*
							 * else { ExceptionUtil
							 * .prepareBusinessException(UniversalErrorConstants
							 * .ORIGIN_NOT_DEFINED_FOR_PRODUCT); }
							 */
						}
						if (!isPincodeServicedByProduct) {
							throw new CGBusinessException(UniversalErrorConstants.ORIGIN_NOT_DEFINED_FOR_PRODUCT);
						}

					} else {
						isPincodeServicedByProduct = (!StringUtil
								.isEmptyList(pinProdSerDos)) ? Boolean.TRUE
								: Boolean.FALSE;
					}
				} else {
					throw new CGBusinessException(UniversalErrorConstants.ORIGIN_NOT_DEFINED_FOR_PRODUCT);
				}
			} else {

				isPincodeServicedByProduct = (!StringUtil
						.isEmptyList(pinProdSerDos)) ? Boolean.TRUE
						: Boolean.FALSE;
			}

		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GeographyServiceDAOImpl.isPincodeServicedByProduct",
					e);
			throw e;
		} finally {
			closeSession(session);
		}
		return isPincodeServicedByProduct;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCitiesByOffices(List<OfficeTO> officeTOList)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		Session session = null;

		try {

			session = createSession();

			Integer[] officeId = new Integer[officeTOList.size()];
			int i = 0;
			for (OfficeTO officeTO : officeTOList) {
				if (!StringUtil.isEmptyInteger(officeTO.getOfficeId()))
					officeId[i] = officeTO.getOfficeId();
				i++;
			}

			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.QRY_GET_CITIES_BY_OFFICE,
					new String[] { UdaanCommonConstants.QRY_PARAM_OFFICE_ID },
					new Object[] { officeId });

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCitiesByOffices",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return cityDOs;

	}

	@Override
	public PincodeDO getPincodeByCityId(Integer cityId)
			throws CGSystemException {
		PincodeDO pincodeDO = null;
		try {
			List<PincodeDO> pincodeDOs = null;
			String queryName = "getPincodeByCityId";
			pincodeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "cityId", cityId);
			if (!StringUtil.isEmptyList(pincodeDOs)) {
				pincodeDO = pincodeDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getPincodeByCityId",
					e);
			throw new CGSystemException(e);
		}
		return pincodeDO;
	}

	/**
	 * Get Region By id or name This method will return RegionTO
	 * 
	 * @inputparam String regionCode,Integer regionId
	 * @return RegionTO
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RegionDO getRegionByIdOrName(String regionCode, Integer regionId)
			throws CGSystemException {
		RegionDO regionDO = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(RegionDO.class);
			if (!StringUtil.isEmptyInteger(regionId)) {
				criteria.add(Restrictions.eq("regionId", regionId));
			}
			if (!StringUtil.isStringEmpty(regionCode)) {
				criteria.add(Restrictions.eq("regionCode", regionCode));
			}
			regionDO = (RegionDO) criteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getRegionByIdOrName",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return regionDO;
	}

	@Override
	public StateDO getState(Integer stateId) throws CGSystemException {
		List<StateDO> stateDOs = null;
		StateDO stateDO = null;
		try {

			String queryName = "getState";
			stateDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "stateId", stateId);
			if (!StringUtil.isEmptyList(stateDOs)) {
				stateDO = stateDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getState", e);
			throw new CGSystemException(e);
		}

		return stateDO;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException {
		List<Integer> servicedCities = null;
		try {
			servicedCities = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getServicedCityByTransshipmentCity",
							"transshipmentCityId", transCityId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GeographyServiceDAOImpl.getServicedCityByTransshipmentCity",
					e);
			throw new CGSystemException(e);
		}
		return servicedCities;
	}

	@Override
	public PincodeDO getPincode(Integer pincodeId) throws CGSystemException {
		List<PincodeDO> pinDOs = null;
		PincodeDO pincodeDO = null;
		try {

			String queryName = "getPincode";
			pinDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "pincodeId", pincodeId);
			if (!StringUtil.isEmptyList(pinDOs)) {
				pincodeDO = pinDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getState", e);
			throw new CGSystemException(e);
		}

		return pincodeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityByPincodeId(Integer pincodeId)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		CityDO cityDO = null;
		try {
			String queryName = "getCityByPincodeId";
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "pincodeId", pincodeId);
			if (!StringUtil.isEmptyList(cityDOs)) {
				cityDO = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getState", e);
			throw new CGSystemException(e);
		}
		return cityDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityByName(String cityName) throws CGSystemException {
		List<CityDO> cityDOs = null;
		CityDO cityDO = null;
		try {
			String queryName = "getCityByCityName";
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "cityName", cityName);
			if (!StringUtil.isEmptyList(cityDOs)) {
				cityDO = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getState", e);
			throw new CGSystemException(e);
		}

		return cityDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZoneDO> getZoneByZoneId(Integer zoneId)
			throws CGSystemException {
		List<ZoneDO> zoneDOs = null;
		try {
			String queryName = "getZoneByZoneId";
			zoneDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "zoneId", zoneId);

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getState", e);
			throw new CGSystemException(e);
		}

		return zoneDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DepartmentDO getDepartmentByName(String departmentName)
			throws CGSystemException {
		List<DepartmentDO> deptDOs = null;
		DepartmentDO deptDO = null;
		try {
			String queryName = "getDeptByDeptName";
			deptDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "departmentName", departmentName);
			if (!StringUtil.isEmptyList(deptDOs)) {
				deptDO = deptDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getDepartmentByName",
					e);
			throw new CGSystemException(e);
		}
		return deptDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RegionDO getRegionByReportingRHOCode(String regionCode)
			throws CGSystemException {
		List<RegionDO> regionDOs = null;
		RegionDO regionDO = null;
		try {
			regionDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UdaanCommonConstants.IS_REGION_EXISTS, "regionCode",
					regionCode);
			if (!StringUtil.isEmptyList(regionDOs)) {
				regionDO = regionDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getDepartmentByName",
					e);
			throw new CGSystemException(e);
		}
		return regionDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {

			String queryName = UdaanCommonConstants.QRY_GET_CITIES_OF_REPORTED_OFC_RHO;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, UdaanCommonConstants.PARAM_RHO_OFFICE_ID,
					rhoOfficeId);

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}
	@Override
	public List<?> getAllCitiesByRHOOfficeAsMap(Integer rhoOfficeid) throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getAllCitiesByRHOOfficeAsMap", UdaanCommonConstants.PARAM_RHO_OFFICE_ID, rhoOfficeid);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityListOfAssignedOffices(Integer userId)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {

			String queryName = UdaanCommonConstants.QRY_GET_CITIES_OF_ASSIGNED_OFC_FOR_USER;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, UdaanCommonConstants.PARAM_USER_ID, userId);

		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@Override
	public StateDO getStateByCode(String stateCode) throws CGSystemException {
		Session session = null;
		Criteria stateCriteria = null;
		StateDO stateDO = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			stateCriteria = session.createCriteria(StateDO.class);
			stateCriteria.add(Restrictions.eq("stateCode", stateCode));
			stateDO = (StateDO) stateCriteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getStateByCide", e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return stateDO;
	}

	@Override
	public CountryDO getCountryByCode(String conCode) throws CGSystemException {
		Session session = null;
		Criteria stateCriteria = null;
		CountryDO countryDO = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			stateCriteria = session.createCriteria(CountryDO.class);
			stateCriteria.add(Restrictions.eq("countryCode", conCode));
			countryDO = (CountryDO) stateCriteria.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCountryByCode", e);
			throw new CGSystemException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return countryDO;
	}

	public Integer getOfficeIdByPincode(Integer pincodeId)
			throws CGSystemException {
		Session session = null;
		Integer officeId = 0;
		List<BranchPincodeServiceabilityDO> brPincodeServ = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session
					.createCriteria(BranchPincodeServiceabilityDO.class);
			criteria.add(Restrictions.eq("pincodeId", pincodeId));
			brPincodeServ = (List<BranchPincodeServiceabilityDO>) criteria
					.list();
			officeId = (!StringUtil.isEmptyList(brPincodeServ)) ? brPincodeServ
					.get(0).getOfficeId() : 0;
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getOfficeIdByPincode() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {
			if (!CGCollectionUtils.isEmpty(ofcList)) {
				int ofcLen = ofcList.size();
				Integer[] officeList = new Integer[ofcLen];
				for (int i = 0; i < ofcLen; i++) {
					officeList[i] = ofcList.get(i).getOfficeId();
				}
				String queryName = UdaanCommonConstants.QRY_GET_CITIES_OF_ALL_REPORTED_OFC_RHO;
				cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
						queryName, UdaanCommonConstants.PARAM_RHO_OFFICE_LIST,
						officeList);

			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityByOfficeId(Integer officeId) throws CGSystemException {
		List<CityDO> cityDOs = null;
		CityDO cityDO = null;
		try {
			String queryName = "getCityByOfficeId";
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "officeId", officeId);
			if (!StringUtil.isEmptyList(cityDOs)) {
				cityDO = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCityByOfficeId", e);
			throw new CGSystemException(e);
		}

		return cityDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ZoneDO getZoneCodeByCityId(Integer cityId) throws CGSystemException {
		LOGGER.debug("GeographyServiceDAOImpl :: getZoneCodeByCityId :: Start");
		List<ZoneDO> zoneDOs = null;
		ZoneDO zoneDO = null;
		try {
			String queryName = "getZoneByCityId";
			// zoneDOs = getHibernateTemplate().find(queryName, cityId);
			zoneDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "cityId", cityId);
			// zones =
			// getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
			// "cityId", cityId);
			if (!StringUtil.isEmptyList(zoneDOs)) {
				zoneDO = zoneDOs.get(0);
				// zoneDO = zoneDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception In : GeographyServiceDAOImpl.getZoneCodeByCityId",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("GeographyServiceDAOImpl :: getZoneCodeByCityId :: End");
		return zoneDO;
	}

	@SuppressWarnings("unchecked")
	public PincodeProductServiceabilityDO getPincodeProdServiceabilityDtls(
			Integer pincodeProductMapId) throws CGSystemException {
		List<PincodeProductServiceabilityDO> pincodeDlvTimeMapDOs = null;
		try {
			pincodeDlvTimeMapDOs = (List<PincodeProductServiceabilityDO>) getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getPincodeProdServiceabilityDtls",
							"pincodeProductMapId", pincodeProductMapId);
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getPincodeProdServiceabilityDtls", e);
			throw new CGSystemException(e);
		}
		return !CGCollectionUtils.isEmpty(pincodeDlvTimeMapDOs) ? pincodeDlvTimeMapDOs
				.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BranchPincodeServiceabilityDO> getServicableOfficeIdByPincode(Integer pincodeId)
			throws CGSystemException {
		Session session = null;
		List<BranchPincodeServiceabilityDO> brPincodeServ = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session
					.createCriteria(BranchPincodeServiceabilityDO.class);
			criteria.add(Restrictions.eq("pincodeId", pincodeId));
			brPincodeServ = (List<BranchPincodeServiceabilityDO>) criteria
					.list();
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::OrganizationServiceDAOImpl::getServicableOfficeIdByPincode() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return brPincodeServ;
	}
	
	@SuppressWarnings("unchecked")
	public List<OfficeDO> getAllOfficeByOfficeIds(List<OfficeTO> officeTO)
			throws CGSystemException {
		List<OfficeDO> officeDOs = null;
		Session session = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(OfficeDO.class, "office");

			List<Integer> officeId = new ArrayList<Integer>();
			if (!CGCollectionUtils.isEmpty(officeTO)) {

				for (OfficeTO TO : officeTO) {
					if (!StringUtil.isEmptyInteger(TO.getOfficeId())) {
						officeId.add(TO.getOfficeId());
					}
					if (!CGCollectionUtils.isEmpty(officeId)) {
						criteria.add(Restrictions.in("office.officeId",
								officeId));
					}
				}
				criteria.addOrder(Order.asc("office.officeName"));
				officeDOs = criteria.list();
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GeographyServiceDAOImpl.getAllOfficeByOfficeIds",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return officeDOs;

	}
	
	
	@Override
	public List<OfficeDO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId) throws CGSystemException {
		List<OfficeDO> officeDoList = null;
		try {
			String queryName = "getBranchDtlsForPincodeServiceByPincode";
			officeDoList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "pincodeId", pincodeId);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getBranchDtlsForPincodeServiceByPincode", e);
			throw new CGSystemException(e);
		}

		return officeDoList;
	}
	@Override
	public List<?> getAllPincodeAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQuery("getAllPincodeWithCity");
	}
	@Override
	public List<?> getAllBranchesAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQuery("getAllOfficeAsMap");
	}
	
	@Override
	public List<?> getAllRHOListAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getAllOfficeByTypeAsMap", "offTypeCode", new Object[]{CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE});
	}
	@Override
	public List<?> getAllRHOAndCOListAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getAllOfficeByTypeAsMap", "offTypeCode", new Object[]{CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,CommonConstants.OFF_TYPE_CORP_OFFICE});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StateDO> getStatesList() throws CGSystemException {
		List<StateDO> stateDOs = null;
		try {
			stateDOs = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_STATES);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getStatesList", e);
			throw new CGSystemException(e);
		}
		return stateDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityDO> getCityListByStateId(Integer stateId)
			throws CGSystemException {
		List<CityDO> cityDOs = null;
		try {
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getCitiesByState",UdaanCommonConstants.STATE_ID,stateId);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getCityListByStateId", e);
			throw new CGSystemException(e);
		}
		return cityDOs;
	}

	@Override
	public List<SectorDO> getSectors() throws CGSystemException {
		List<SectorDO> sectorList = null;
		try {
			sectorList = getHibernateTemplate().findByNamedQuery(
					UdaanCommonConstants.GET_ALL_SECTORS);
			
		} catch (Exception e) {
			LOGGER.error("ERROR : GeographyServiceDAOImpl.getStatesList", e);
			throw new CGSystemException(e);
		}
		return sectorList;
	}
	
	@Override
	public List<?> getAllPincodeAsMapWithoutStatus() throws CGSystemException {
		return getHibernateTemplate().findByNamedQuery("getAllPincodesWithoutStatus");
	}
	
	@Override
	public List<String> getProductsDescMappedToPincode(Integer pincodeId) throws CGSystemException, CGBusinessException {
		String namedQuery = "getProductsServicedByPincode";
		String paramName = "pincodeId";
		List<String> productCategoryList = new ArrayList<>();

		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>START");
		Session session = null;
		HibernateTemplate template = getHibernateTemplate();
		try
		{
			session = template.getSessionFactory().openSession();
			Query query = session.getNamedQuery(namedQuery);
			query.setParameter(paramName, pincodeId);
			productCategoryList = query.list();
			query = null;
		}
		catch(Exception e)
		{
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>ERROR",e);
			throw e;
		}
		finally
		{
			//releasing session if not
			if(null != session)
				session.close();
			template = null;
		}
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>END");
		return productCategoryList;
	
	}
	
	@Override
	public List<String> getPincodeAndCityListForAutoComplete() throws CGSystemException, CGBusinessException {
		String namedQuery = "getPincodeAndCityListForLocationSearch";
		List<String> pincodeCityList = null;

		logger.debug("GeographyServiceDAOImpl::getPincodeAndCityListForAutoComplete::------------>START");
		Session session = null;
		HibernateTemplate template = getHibernateTemplate();
		try
		{
			session = template.getSessionFactory().openSession();
			Query query = session.getNamedQuery(namedQuery);
			pincodeCityList = query.list();
			query = null;
		}
		catch(Exception e)
		{
			logger.error("GeographyServiceDAOImpl::getPincodeAndCityListForAutoComplete::------------>ERROR",e);
			throw e;
		}
		finally
		{
			//releasing session if not
			if(null != session)
				session.close();
			template = null;
		}
		logger.debug("GeographyServiceDAOImpl::getPincodeAndCityListForAutoComplete::------------>END");
		return pincodeCityList;
	
	}
}
