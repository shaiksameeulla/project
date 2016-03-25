package com.ff.universe.geography.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.CountryDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.geography.PincodeProductServiceabilityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.geography.ZoneDO;
import com.ff.domain.organization.BranchPincodeServiceabilityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.geography.CityTO;
import com.ff.geography.CountryTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.OfficeTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.util.UniversalConverterUtil;

public class GeographyCommonServiceImpl implements GeographyCommonService {

	private GeographyServiceDAO geographyServiceDAO;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GeographyCommonServiceImpl.class);

	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}

	@Override
	public CityTO getCity(String Pinocde) {
		LOGGER.debug("GeographyCommonServiceImpl::getCity----------->START:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		CityDO cityDO = new CityDO();
		CityTO cityTO = new CityTO();
		try {
			cityDO = geographyServiceDAO.getCity(Pinocde);
			if (!StringUtil.isNull(cityDO))
				cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : OrganizationCommonServiceImpl.getCity", ex);
		}
		LOGGER.debug("GeographyCommonServiceImpl::getCity----------->END:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return cityTO;
	}

	@Override
	public PincodeTO validatePincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("GeographyCommonServiceImpl::validatePincode----------->START:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		// TODO Auto-generated method stub
		PincodeTO pinTO = new PincodeTO();
		PincodeDO pinDO = null;
		PincodeTO pincodeTO = null;

		List<PincodeDO> pincodeDOList = geographyServiceDAO
				.validatePincode(pincode);
		if (pincodeDOList != null && !pincodeDOList.isEmpty()) {
			pinDO = pincodeDOList.get(0);
			pincodeTO = (PincodeTO) CGObjectConverter.createToFromDomain(pinDO,
					pinTO);
		}
		LOGGER.debug("GeographyCommonServiceImpl::validatePincode----------->END:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return pincodeTO;

	}
	
	@Override
	public List<PincodeTO> getPincodeDetailsByPincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("GeographyCommonServiceImpl::getPincodeDetailsByPincode----------->START:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		// TODO Auto-generated method stub
		List<PincodeTO> pinTOList = null;

		List<PincodeDO> pincodeDOList = geographyServiceDAO
				.validatePincode(pincode);
		if (!CGCollectionUtils.isEmpty(pincodeDOList)) {
			pinTOList = (List<PincodeTO>) CGObjectConverter.createTOListFromDomainList(pincodeDOList, PincodeTO.class);
					
		}
		LOGGER.debug("GeographyCommonServiceImpl::getPincodeDetailsByPincode----------->END:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return pinTOList;

	}

	public PincodeTO validatePincodeServiceability(String pinCode,
			Integer officeId) throws CGSystemException, CGBusinessException {
		PincodeDO pinDO = null;
		PincodeTO pincodeTO = null;
		LOGGER.debug("GeographyCommonServiceImpl::validatePincodeServiceability----------->Start:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		try {
			pinDO = geographyServiceDAO.validatePincodeServiceability(pinCode,
					officeId);
			if (pinDO != null) {
				pincodeTO = new PincodeTO();
				pincodeTO = (PincodeTO) CGObjectConverter.createToFromDomain(
						pinDO, pincodeTO);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.validatePincodeServiceability",
					ex);
		}
		LOGGER.debug("GeographyCommonServiceImpl::validatePincodeServiceability----------->END:::::::"+DateUtil.getCurrentTimeInMilliSeconds());
		return pincodeTO;
	}

	public List<PincodeProductServiceabilityTO> getPincodeDlvTimeMaps(
			String pinCode, Integer cityId, String consgSeries)
			throws CGSystemException {
		List<PincodeProductServiceabilityDO> pinDlvTimeDOs = null;
		List<PincodeProductServiceabilityTO> pinDlvTimeUpdatedTOs = null;
		try {
			pinDlvTimeDOs = geographyServiceDAO.getPincodeDlvTimeMaps(pinCode,
					cityId, consgSeries);
			if(!CGCollectionUtils.isEmpty(pinDlvTimeDOs)){
				pinDlvTimeUpdatedTOs = new ArrayList<PincodeProductServiceabilityTO>(pinDlvTimeDOs.size());
				for (PincodeProductServiceabilityDO dlvTimeMap : pinDlvTimeDOs) {
					if(!StringUtil.isNull(dlvTimeMap.getOriginCity())){
						if (dlvTimeMap.getOriginCity().getCityId().intValue() == cityId
								.intValue()) {
							PincodeProductServiceabilityTO dlvTimeMapTO = new PincodeProductServiceabilityTO();
							dlvTimeMapTO = (PincodeProductServiceabilityTO) CGObjectConverter
									.createToFromDomain(dlvTimeMap, dlvTimeMapTO);
							if (StringUtils.equalsIgnoreCase("A",
									dlvTimeMap.getDlvTimeQualification())) {
								String dlvTimeAfter = "After "
										+ dlvTimeMap.getDeliveryTime();
								dlvTimeMapTO.setDeliveryTime(dlvTimeAfter);
							}
							if (StringUtils.equalsIgnoreCase("B",
									dlvTimeMap.getDlvTimeQualification())) {
								String dlvTimeAfter = "Before "
										+ dlvTimeMap.getDeliveryTime();
								dlvTimeMapTO.setDeliveryTime(dlvTimeAfter);
							}
							if (StringUtils.equalsIgnoreCase("S",
									dlvTimeMap.getDlvTimeQualification())) {
								String dlvTimeAfter = "Till "
										+ dlvTimeMap.getDeliveryTime();
								dlvTimeMapTO.setDeliveryTime(dlvTimeAfter);
							}
							pinDlvTimeUpdatedTOs.add(dlvTimeMapTO);
						}
	
					}
					
				}// end for loop
			}

			/*
			 * if (!StringUtil.isEmptyList(pinDlvTimeDOs)) { pinDlvTimeTOs =
			 * (List<PincodeProductServiceabilityTO>) CGObjectConverter
			 * .createTOListFromDomainList(pinDlvTimeDOs,
			 * PincodeProductServiceabilityTO.class); for
			 * (PincodeProductServiceabilityTO dlvTimeMap : pinDlvTimeTOs) { if
			 * (dlvTimeMap.getOrgCity().intValue() == cityId.intValue()) { if
			 * (StringUtils.equalsIgnoreCase("A",
			 * dlvTimeMap.getDlvTimeQualification())) { String dlvTimeAfter =
			 * "After " + dlvTimeMap.getDeliveryTime();
			 * dlvTimeMap.setDeliveryTime(dlvTimeAfter); } if
			 * (StringUtils.equalsIgnoreCase("B",
			 * dlvTimeMap.getDlvTimeQualification())) { String dlvTimeAfter =
			 * "Before " + dlvTimeMap.getDeliveryTime();
			 * dlvTimeMap.setDeliveryTime(dlvTimeAfter); }
			 * pinDlvTimeUpdatedTOs.add(dlvTimeMap); } } }
			 */
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.validatePincodeServiceability",
					ex);
			throw new CGSystemException(ex);
		}
		return pinDlvTimeUpdatedTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		List<RegionTO> regionTOs = null;
		try {
			List<RegionDO> regionDOList = geographyServiceDAO.getAllRegions();
			if (regionDOList != null && regionDOList.size() > 0) {
				regionTOs = (List<RegionTO>) CGObjectConverter
						.createTOListFromDomainList(regionDOList,
								RegionTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getAllRegions", ex);
		}
		return regionTOs;
	}

	/**
	 * Get City details by cityId or cityCode
	 * <p>
	 * <ul>
	 * <li>if there exists a city by cityId or cityCode, the method will return
	 * a valid CityTO with all the details filled else return null.
	 * </ul>
	 * <p>
	 * 
	 * @param CityTO
	 *            :: The input cityTO will be passed with the following details
	 *            filled in -
	 *            <ul>
	 *            <li>cityId OR <li>cityCode
	 *            </ul>
	 * @return CityTO :: CityTO will get filled with all the city details.
	 * 
	 * @author R Narmdeshwar
	 * 
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		CityTO cityTO1 = null;
		try {
			CityDO cityDO = geographyServiceDAO.getCity(cityTO);
			if (cityDO != null) {
				cityTO1 = new CityTO();
				cityTO1 = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO1);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : OrganizationCommonServiceImpl.getCity", ex);
		}
		return cityTO1;
	}

	@Override
	public boolean pincodeServiceabilityByCity(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException {
		return geographyServiceDAO.pincodeServiceabilityByCity(pincodeTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getAllCities() throws CGBusinessException,
			CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO.getAllCities();
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = new ArrayList<>();
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getAllRegions", ex);
		}
		return cityTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCitiesByCity(cityTO);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = new ArrayList<>();
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCitiesByCity",
					ex);
			throw new CGSystemException("ERRCOM002", ex);
		}
		return cityTOs;
	}

	@SuppressWarnings("unchecked")
	public List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getAllCitiesByCityIds(cityTOList);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = new ArrayList<>();
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCitiesByCity",
					ex);
			throw new CGSystemException(ex);
		}
		return cityTOs;

	}

		@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCitiesByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCitiesByRegion(regionId);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCitiesByZone",
					ex);
		}
		return cityTOs;
	}
	 

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getTranshipmentCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getTranshipmentCitiesByRegion(regionTO);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
			// Please Don't handle these Exception
			/*
			 * else { ExceptionUtil
			 * .prepareBusinessException(UniversalErrorConstants
			 * .CITY_DETAILS_NOT_EXIST); }
			 */

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in GeographyCommonServiceImpl :: getTranshipmentCitiesByRegion()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in GeographyCommonServiceImpl :: getTranshipmentCitiesByRegion()..:"
					+ e.getMessage());
			throw e;
		}
		return cityTOs;
	}

	@Override
	public boolean isRegionExists(String regionCode)
			throws CGBusinessException, CGSystemException {
		boolean isRegion = Boolean.FALSE;
		try {
			isRegion = geographyServiceDAO.isRegionExists(regionCode);

		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getAllRegions", ex);
		}
		return isRegion;
	}

	@Override
	public boolean isPincodeServicedByProduct(
			PincodeServicabilityTO pincodeProdServicebility)
			throws CGBusinessException, CGSystemException {
		return geographyServiceDAO
				.isPincodeServicedByProduct(pincodeProdServicebility);
	}

	@SuppressWarnings("unchecked")
	public List<CityTO> getCitiesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCitiesByOffices(officeTOList);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCitiesByZone",
					ex);
		}
		return cityTOs;
	}

	@Override
	public PincodeTO getPincodeByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException {
		PincodeDO pincodeDO = new PincodeDO();
		PincodeTO pincodeTO = new PincodeTO();
		try {
			pincodeDO = geographyServiceDAO.getPincodeByCityId(cityId);
			if (!StringUtil.isNull(pincodeDO))
				pincodeTO = (PincodeTO) CGObjectConverter.createToFromDomain(
						pincodeDO, pincodeTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCity", ex);
		}
		return pincodeTO;
	}

	@Override
	public RegionTO getRegionByIdOrName(String regionCode, Integer regionId)
			throws CGSystemException {
		RegionDO regionDO = new RegionDO();
		RegionTO regionTO = new RegionTO();
		try {
			regionDO = geographyServiceDAO.getRegionByIdOrName(regionCode,
					regionId);
			if (!StringUtil.isNull(regionDO))
				regionTO = (RegionTO) CGObjectConverter.createToFromDomain(
						regionDO, regionTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.regionTO", ex);
		}
		return regionTO;
	}

	@Override
	public StateTO getState(Integer stateId) throws CGBusinessException,
			CGSystemException {
		StateTO stateTO = new StateTO();
		StateDO stateDO = null;
		try {
			stateDO = geographyServiceDAO.getState(stateId);
			if (!StringUtil.isNull(stateDO))
				stateTO = (StateTO) CGObjectConverter.createToFromDomain(
						stateDO, stateTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getState", ex);
		}

		return stateTO;
	}

	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException, CGSystemException {
		return geographyServiceDAO
				.getServicedCityByTransshipmentCity(transCityId);
	}

	@Override
	public PincodeTO getPincode(Integer pincodeId) throws CGBusinessException,
			CGSystemException {

		PincodeTO pincodeTO = new PincodeTO();
		PincodeDO pincodeDO = null;
		try {
			pincodeDO = geographyServiceDAO.getPincode(pincodeId);
			if (!StringUtil.isNull(pincodeDO))
				pincodeTO = (PincodeTO) CGObjectConverter.createToFromDomain(
						pincodeDO, pincodeTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getState", ex);
		}

		return pincodeTO;
	}

	@Override
	public CityTO getCityByPincodeId(Integer pincodeId)
			throws CGBusinessException, CGSystemException {

		CityTO cityTO = new CityTO();
		CityDO cityDO = null;
		try {
			cityDO = geographyServiceDAO.getCityByPincodeId(pincodeId);
			if (!StringUtil.isNull(cityDO))
				cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getState", ex);
		}

		return cityTO;
	}

	@Override
	public CityTO getCityByName(String cityName) throws CGBusinessException,
			CGSystemException {
		CityTO cityTO = new CityTO();
		CityDO cityDO = null;
		try {
			cityDO = geographyServiceDAO.getCityByName(cityName);
			if (!StringUtil.isNull(cityDO))
				cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCityByName", ex);
		}
		return cityTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZoneTO> getZoneByZoneId(Integer zoneId)
			throws CGBusinessException, CGSystemException {
		List<ZoneTO> zoneTOList = null;
		try {
			List<ZoneDO> zoneDOList = geographyServiceDAO
					.getZoneByZoneId(zoneId);
			if (!CGCollectionUtils.isEmpty(zoneDOList))
				zoneTOList = (List<ZoneTO>) CGObjectConverter
						.createTOListFromDomainList(zoneDOList, ZoneTO.class);

		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCityByName", ex);
		}
		return zoneTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityListOfReportedOffices(Integer rhoOfficeId)
			throws CGBusinessException, CGSystemException {

		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCityListOfReportedOffices(rhoOfficeId);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.getCityListOfReportedOffices",
					ex);
		}
		return cityTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCityListOfAssignedOffices(userId);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.getCityListOfReportedOffices",
					ex);
		}
		return cityTOs;
	}

	@Override
	public StateTO getStateByCode(String stateCode) throws CGBusinessException,
			CGSystemException {
		StateTO stateTO = new StateTO();
		StateDO stateDO = null;
		try {
			stateDO = geographyServiceDAO.getStateByCode(stateCode);
			if (!StringUtil.isNull(stateDO))
				stateTO = (StateTO) CGObjectConverter.createToFromDomain(
						stateDO, stateTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getStateByCode",
					ex);
		}
		return stateTO;
	}

	@Override
	public CountryTO getCountryByCode(String string)
			throws CGBusinessException, CGSystemException {
		CountryTO countryTO = new CountryTO();
		CountryDO countryDO = null;
		try {
			countryDO = geographyServiceDAO.getCountryByCode(string);
			if (!StringUtil.isNull(countryDO))
				countryTO = (CountryTO) CGObjectConverter.createToFromDomain(
						countryDO, countryTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : GeographyCommonServiceImpl.getCountryByCode",
					ex);
		}
		return countryTO;
	}

	public Integer getOfficeIdByPincode(Integer pincodeId)
			throws CGSystemException {
		return geographyServiceDAO.getOfficeIdByPincode(pincodeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		try {
			List<CityDO> cityDOList = geographyServiceDAO
					.getCityListOfReportedOfficesOfRHO(ofcList);
			if (cityDOList != null && cityDOList.size() > 0) {
				cityTOs = (List<CityTO>) CGObjectConverter
						.createTOListFromDomainList(cityDOList, CityTO.class);
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.getCityListOfReportedOffices",
					ex);
		}
		return cityTOs;
	}

	@Override
	public CityTO getCityByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		CityTO cityTO = new CityTO();
		CityDO cityDO = null;
		try {
			cityDO = geographyServiceDAO.getCityByOfficeId(officeId);
			if (!StringUtil.isNull(cityDO))
				cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO);
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.getCityByOfficeId", ex);
		}
		return cityTO;
	}

	public PincodeProductServiceabilityTO getPincodeProdServiceabilityDtls(
			Integer pincodeProductMapId) throws CGSystemException,
			CGBusinessException {
		PincodeProductServiceabilityTO dlvTimeMapTO = null;
		PincodeProductServiceabilityDO pincodeProdServiceabilityDO = null;
		try {
			pincodeProdServiceabilityDO = geographyServiceDAO
					.getPincodeProdServiceabilityDtls(pincodeProductMapId);
			if (!StringUtil.isNull(pincodeProdServiceabilityDO)) {
				dlvTimeMapTO = new PincodeProductServiceabilityTO();
				dlvTimeMapTO = (PincodeProductServiceabilityTO) CGObjectConverter
						.createToFromDomain(pincodeProdServiceabilityDO,
								dlvTimeMapTO);
				if (StringUtils.equalsIgnoreCase("A",
						pincodeProdServiceabilityDO.getDlvTimeQualification())) {
					String dlvTimeAfter = "After "
							+ pincodeProdServiceabilityDO.getDeliveryTime();
					dlvTimeMapTO.setDeliveryTime(dlvTimeAfter);
				}
				if (StringUtils.equalsIgnoreCase("B",
						pincodeProdServiceabilityDO.getDlvTimeQualification())) {
					String dlvTimeAfter = "Before "
							+ pincodeProdServiceabilityDO.getDeliveryTime();
					dlvTimeMapTO.setDeliveryTime(dlvTimeAfter);
				}
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : GeographyCommonServiceImpl.getPincodeProdServiceabilityDtls",
					ex);
		}
		return dlvTimeMapTO;
	}
	
	public List<PincodeServicabilityTO> getServicableOfficeIdByPincode(
			Integer pincodeId) throws CGSystemException, CGBusinessException{
		List<BranchPincodeServiceabilityDO> serviceabilityDOs = null;
		List<PincodeServicabilityTO> servicabilityTOs = null;
		serviceabilityDOs = geographyServiceDAO
				.getServicableOfficeIdByPincode(pincodeId);
		if (!CGCollectionUtils.isEmpty(serviceabilityDOs)) {
			servicabilityTOs = new ArrayList<>(serviceabilityDOs.size());
			for (BranchPincodeServiceabilityDO serviceabilityDO : serviceabilityDOs) {
				PincodeServicabilityTO servicabilityTO = new PincodeServicabilityTO();
				servicabilityTO.setOfficeId(serviceabilityDO.getOfficeId());
				servicabilityTO.setPincodeId(serviceabilityDO.getPincodeId());
				servicabilityTOs.add(servicabilityTO);
			}
		}

		return servicabilityTOs;
	}
	
	public List<OfficeTO> getAllOfficeByOfficeIds(List<OfficeTO> officeTO)  throws CGSystemException, CGBusinessException{
		List<OfficeDO> officeDOs = null;
		List<OfficeTO> officeTOs = null;
		officeDOs = geographyServiceDAO
				.getAllOfficeByOfficeIds(officeTO);
		if (!CGCollectionUtils.isEmpty(officeDOs)) {
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO to = new OfficeTO();
				to.setOfficeId(officeDO.getOfficeId());
				to.setOfficeName(officeDO.getOfficeName());
				officeTOs.add(to);
			}
		}

		return officeTOs;
		
		
	}
	
	@Override
	public Boolean isPincodeServiceableByProductSeries(
			Integer pincodeId,Integer productId) throws CGSystemException, CGBusinessException{
		Boolean isValid=false;
		List<Integer> DoList=geographyServiceDAO.isValidPincodeProductServiceability(pincodeId,productId);
		if(!CGCollectionUtils.isEmpty(DoList)){
			isValid=true;
		}
	return isValid;
	}
	
	@Override
	public List<OfficeTO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId) throws CGSystemException, CGBusinessException{
		List<OfficeDO> officeDOs = null;
		List<OfficeTO> officeTOs = null;
		officeDOs = geographyServiceDAO
				.getBranchDtlsForPincodeServiceByPincode(pincodeId);
		if (!CGCollectionUtils.isEmpty(officeDOs)) {
			officeTOs = new ArrayList<>(officeDOs.size());
			for (OfficeDO officeDO : officeDOs) {
				OfficeTO to = new OfficeTO();
				to.setOfficeId(officeDO.getOfficeId());
				to.setOfficeName(officeDO.getOfficeName());
				to.setOfficeCode(officeDO.getOfficeCode());
				to.setBuildingName(officeDO.getBuildingName());
				to.setAddress1(officeDO.getAddress1());
				to.setAddress2(officeDO.getAddress2());
				to.setAddress3(officeDO.getAddress3());
				to.setPhone(officeDO.getPhone());
				to.setMobileNo(officeDO.getMobileNo());
				
				officeTOs.add(to);
			}
		}

		return officeTOs;
		
		
	
	}
	
	@Override
	public Map<Integer, String> getAllPincodesAsMap() throws CGSystemException ,CGBusinessException{
		List<?> allPincodeAsListOfMap=null;
		Map<Integer, String> allPincodeAsMap=null;
		allPincodeAsListOfMap = geographyServiceDAO.getAllPincodeAsMap();
		allPincodeAsMap = UniversalConverterUtil.prepareMapFromList(allPincodeAsListOfMap);
		if(!CGCollectionUtils.isEmpty(allPincodeAsMap)){
			allPincodeAsMap= CGCollectionUtils.sortByValue(allPincodeAsMap);
		}
		return allPincodeAsMap;
	}
	@Override
	public Map<Integer, String> getAllBranchesAsMap() throws CGSystemException ,CGBusinessException{
		List<?> allPincodeAsListOfMap=null;
		Map<Integer, String> allPincodeAsMap=null;
		allPincodeAsListOfMap = geographyServiceDAO.getAllBranchesAsMap();
		allPincodeAsMap = UniversalConverterUtil.prepareMapFromList(allPincodeAsListOfMap);
		if(!CGCollectionUtils.isEmpty(allPincodeAsMap)){
			allPincodeAsMap= CGCollectionUtils.sortByValue(allPincodeAsMap);
		}
		return allPincodeAsMap;
	}
	
	@Override
	public Map<Integer, String> getAllRHOOfficesAsMap() throws CGSystemException ,CGBusinessException{
		List<?> allRHOList=null;
		Map<Integer, String> allRHOMap=null;
		allRHOList = geographyServiceDAO.getAllRHOListAsMap();
		allRHOMap = UniversalConverterUtil.prepareMapFromList(allRHOList);
		if(!CGCollectionUtils.isEmpty(allRHOMap)){
			allRHOMap= CGCollectionUtils.sortByValue(allRHOMap);
		}
		return allRHOMap;
	}
	
	/**
	 * Gets the all rho and co offices as map.
	 *
	 * @return the all rho and co offices as map
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Map<Integer, String> getAllRHOAndCOOfficesAsMap() throws CGSystemException ,CGBusinessException{
		List<?> allRHOAndCOList=null;
		Map<Integer, String> allRHOAndCOMap=null;
		allRHOAndCOList = geographyServiceDAO.getAllRHOAndCOListAsMap();
		allRHOAndCOMap = UniversalConverterUtil.prepareMapFromList(allRHOAndCOList);
		if(!CGCollectionUtils.isEmpty(allRHOAndCOMap)){
			allRHOAndCOMap= CGCollectionUtils.sortByValue(allRHOAndCOMap);
		}
		return allRHOAndCOMap;
	}
	@Override
	public Map<Integer, String> getAllCitiesByRhoOfficeAsMap(Integer rhoOfficeid) throws CGSystemException ,CGBusinessException{
		List<?> cityList=null;
		Map<Integer, String> cityMap=null;
		cityList = geographyServiceDAO.getAllCitiesByRHOOfficeAsMap(rhoOfficeid);
		cityMap = UniversalConverterUtil.prepareMapFromList(cityList);
		if(!CGCollectionUtils.isEmpty(cityMap)){
			cityMap= CGCollectionUtils.sortByValue(cityMap);
		}
		return cityMap;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StateTO> getStatesList() throws CGSystemException,
			CGBusinessException {
		List<StateTO> stateTOList = null;
		List<StateDO> stateDOList = null;
		stateDOList = geographyServiceDAO.getStatesList();
		if (!StringUtil.isEmptyColletion(stateDOList)){
			stateTOList = new ArrayList<StateTO>();
			stateTOList = (List<StateTO>) CGObjectConverter.createTOListFromDomainList(stateDOList, StateTO.class);
		}
		

		return stateTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityTO> getCityListByStateId(Integer stateId)
			throws CGSystemException, CGBusinessException {
		List<CityTO> cityTOList = null;
		List<CityDO> cityDOList = null;
		cityDOList = geographyServiceDAO.getCityListByStateId(stateId);
		if (!StringUtil.isEmptyColletion(cityDOList)){
			cityTOList = new ArrayList<CityTO>();
			cityTOList = (List<CityTO>) CGObjectConverter.createTOListFromDomainList(cityDOList, CityTO.class);
		}
		

		return cityTOList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SectorTO> getSectors() throws CGSystemException, CGBusinessException {
		List<SectorDO> sectorDOList = null;
		List<SectorTO> sectorTOList = null;
		sectorDOList = geographyServiceDAO.getSectors();
		if (!StringUtil.isEmptyColletion(sectorDOList)){
			sectorTOList = new ArrayList<SectorTO>();
			sectorTOList = (List<SectorTO>) CGObjectConverter.createTOListFromDomainList(sectorDOList, SectorTO.class);
		}
		return sectorTOList;
	}
	
	@Override
	public Map<Integer, String> getAllPincodesAsMapForLocationSearch() throws CGSystemException ,CGBusinessException{
		List<?> allPincodeAsListOfMap=null;
		Map<Integer, String> allPincodeAsMap=null;
		allPincodeAsListOfMap = geographyServiceDAO.getAllPincodeAsMap();
		allPincodeAsMap = UniversalConverterUtil.prepareMapFromList(allPincodeAsListOfMap);
		if(!CGCollectionUtils.isEmpty(allPincodeAsMap)){
			allPincodeAsMap= CGCollectionUtils.sortByValue(allPincodeAsMap);
		}
		return allPincodeAsMap;
	}
	
	@Override
	public String getProductsDescMappedToPincode(Integer pincodeId)	throws CGSystemException, CGBusinessException {
		List<String> productDescList = geographyServiceDAO.getProductsDescMappedToPincode(pincodeId);
		String prodDescString = "";
		StringBuffer stringBuffer = new StringBuffer();
		
		for(int i=0; i<productDescList.size(); i++ ){
			stringBuffer.append(productDescList.get(i));
			if(i != (productDescList.size() - 1)){
				stringBuffer.append(",");
			}
		}
		prodDescString = stringBuffer.toString() ;
		return prodDescString;
	}
	
	@Override
	public List<String> getPincodeAndCityListForAutoComplete() throws CGSystemException, CGBusinessException {
		List<String> pincodeCityList = geographyServiceDAO.getPincodeAndCityListForAutoComplete();
		return pincodeCityList;
	}
}
