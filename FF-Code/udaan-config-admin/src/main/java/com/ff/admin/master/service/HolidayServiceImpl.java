/**
 * 
 */
package com.ff.admin.master.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.master.constants.HolidayConstants;
import com.ff.admin.master.constants.PincodeMasterErrorConstants;
import com.ff.admin.master.dao.HolidayDAO;
import com.ff.domain.geography.StateDO;
import com.ff.domain.masters.HolidayDO;
import com.ff.domain.masters.HolidayNameDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.leads.BranchTO;
import com.ff.master.HolidayNameTO;
import com.ff.master.HolidayTO;
import com.ff.universe.geography.service.GeographyCommonService;

/**
 * @author isawarka
 * 
 */
public class HolidayServiceImpl implements HolidayService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(HolidayServiceImpl.class);

	private transient HolidayDAO holidayDAO;

	private GeographyCommonService geographyCommonService;

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public HolidayDAO getHolidayDAO() {
		return holidayDAO;
	}

	public void setHolidayDAO(HolidayDAO holidayDAO) {
		this.holidayDAO = holidayDAO;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HolidayNameTO> getHolidayNameList() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("HolidayServiceImpl::getHolidayNameList()::START");
		List<HolidayNameTO> holidayNameTOs = null;
		List<HolidayNameDO> holidayNameDOs = holidayDAO.getHolidayNameList();
		if (!CGCollectionUtils.isEmpty(holidayNameDOs)) {
			if (!CGCollectionUtils.isEmpty(holidayNameDOs)) {
				holidayNameTOs = (List<HolidayNameTO>) CGObjectConverter
						.createTOListFromDomainList(holidayNameDOs,
								HolidayNameTO.class);
			} else {
				ExceptionUtil
						.prepareBusinessException(PincodeMasterErrorConstants.NO_STATE_FOUND);
			}

		}
		LOGGER.debug("HolidayServiceImpl::getHolidayNameList()::END");
		return holidayNameTOs;
	}

	@Override
	public HolidayDO saveHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayServiceImpl::saveHoliday()::START");
		HolidayDO holidayDO = null;
		if (holidayTO != null) {
			holidayDO = new HolidayDO();
			holidayDO.setDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(holidayTO.getDate()));
			if (holidayTO.getRegionId() != null && holidayTO.getRegionId() != 0) {
				holidayDO.setRegionId(holidayTO.getRegionId());
			}

			if (holidayTO.getStateId() != null && holidayTO.getStateId() != 0) {
				holidayDO.setStateId(holidayTO.getStateId());
			}

			if (holidayTO.getCityId() != null && holidayTO.getCityId() != 0) {
				holidayDO.setCityId(holidayTO.getCityId());
			}
			if (holidayTO.getBranchId() != null && holidayTO.getBranchId() != 0) {
				holidayDO.setBranchId(holidayTO.getBranchId());
			}
			holidayDO.setHolidayNameId(holidayTO.getHolidayNameId());
			holidayDO.setOthers(holidayTO.getOthers());
			holidayDO.setStatus(HolidayConstants.ACTIVE);
		}
		LOGGER.debug("HolidayServiceImpl::saveHoliday()::END");
		return holidayDAO.saveHoliday(holidayDO);
	}

	@Override
	public List<HolidayDO> getHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayServiceImpl::getHoliday()::START");
		List<HolidayDO> holidayList = holidayDAO.getHoliday(holidayTO);
		LOGGER.debug("HolidayServiceImpl::getHoliday()::END");
		return holidayList;
	}

	@Override
	public void updateHoliday(HolidayDO holidayDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::START");
		holidayDAO.updateHoliday(holidayDO);
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::END");

	}

	@Override
	public void updateHoliday(Integer regionId) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::START");
		holidayDAO.updateHoliday(regionId);
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::END");

	}

	@Override
	public void updateHoliday(Integer regionId, Integer stateId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::START");
		holidayDAO.updateHoliday(regionId, stateId);
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::END");

	}

	@Override
	public void updateHoliday(Integer regionId, Integer stateId, Integer cityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::START");
		holidayDAO.updateHoliday(regionId, stateId, cityId);
		LOGGER.debug("HolidayServiceImpl::updateHoliday()::END");

	}

	@Override
	public List<HolidayTO> searchHoliday(HolidayTO holidayTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("HolidayServiceImpl::searchHoliday::START::"
				+ System.currentTimeMillis());
		List<HolidayTO> holidayTOs = null;
		List<HolidayDO> holidayDOs = holidayDAO.searchHoliday(holidayTO);
		if (!CGCollectionUtils.isEmpty(holidayDOs)) {
			holidayTOs = convertHolidayTOsfromHolidayDOs(holidayDOs);
		}
		LOGGER.debug("HolidayServiceImpl::searchHoliday::END::"
				+ System.currentTimeMillis());
		return holidayTOs;
	}

	private List<HolidayTO> convertHolidayTOsfromHolidayDOs(
			List<HolidayDO> holidayDOs) throws CGSystemException,
			CGBusinessException {
		List<HolidayTO> holidayTOs = new ArrayList<HolidayTO>(holidayDOs.size());

		for (HolidayDO holidayDO : holidayDOs) {
			HolidayTO holidayTO = new HolidayTO();
			if (!StringUtil.isNull(holidayDO.getDate())) {
				holidayTO.setDate(DateUtil.getDDMMYYYYDateToString(holidayDO
						.getDate()));
			}
			holidayTO.setRegionId(holidayDO.getRegionId());
			holidayTO.setStateId(holidayDO.getStateId());
			holidayTO.setBranchId(holidayDO.getBranchId());
			holidayTO.setCityId(holidayDO.getCityId());
			holidayTO.setHolidayNameId(holidayDO.getHolidayNameId());

			holidayTO.setId(holidayDO.getId());
			holidayTO.setOthers(holidayDO.getOthers());

			if (!StringUtil.isNull(holidayDO.getRegionId())) {
				RegionTO regionTO = geographyCommonService.getRegionByIdOrName(
						null, holidayDO.getRegionId());
				holidayTO.setRegionName(regionTO.getRegionDisplayName());
			}
			if (!StringUtil.isNull(holidayDO.getStateId())) {
				StateDO stateDO = holidayDAO.getStateById(holidayDO
						.getStateId());
				if (!StringUtil.isNull(stateDO)) {
					holidayTO.setStateName(stateDO.getStateName());
				}
			}
			if (!StringUtil.isNull(holidayDO.getBranchId())) {
				OfficeDO officeDO = holidayDAO.getOfficeByOfficeId(holidayDO
						.getBranchId());
				if (!StringUtil.isNull(officeDO)) {
					holidayTO.setBranchName(officeDO.getOfficeName());
				}
			}
			if (!StringUtil.isNull(holidayDO.getCityId())) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(holidayDO.getCityId());
				cityTO = geographyCommonService.getCity(cityTO);
				if (!StringUtil.isNull(cityTO)) {
					holidayTO.setCityName(cityTO.getCityName());
				}
			}
			if (!StringUtil.isNull(holidayDO.getHolidayNameId())) {
				HolidayNameDO holidayNameDO = holidayDAO.getHolidayNameByHolidayId(holidayDO.getHolidayNameId());
				
				if(!StringUtil.isNull(holidayNameDO)) {
					holidayTO.setHolidayName(holidayNameDO.getHolidayName());
				}
			}
			holidayTOs.add(holidayTO);
		}

		return holidayTOs;
	}

	@Override
	public Boolean editExistingHoliday(HolidayTO holidayTO)
			throws CGSystemException {
		return holidayDAO.editExistingHoliday(holidayTO);
	}

}
