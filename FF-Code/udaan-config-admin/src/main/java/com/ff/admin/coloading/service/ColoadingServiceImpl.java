/**
 * 
 */
package com.ff.admin.coloading.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.dao.ColoadingDAO;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.CdAwbModificationDetailsTO;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.coloading.SurfaceRateEntryTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.coloading.VehicleServiceEntryTO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.transport.FlightDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.transport.FlightTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;

/**
 * @author isawarka
 * 
 */
public class ColoadingServiceImpl implements ColoadingService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingServiceImpl.class);

	/** The geography common service. */
	private transient GeographyCommonService geographyCommonService;

	private transient BusinessCommonService businessCommonService;

	private transient ColoadingDAO coloadingDAO;

	public List<RegionTO> getRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	public List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingServiceImpl::getCitiesByRegionId::START");
		final CityTO cityTO = new CityTO();
		cityTO.setRegion(regionId);
		LOGGER.debug("ColoadingServiceImpl::getCitiesByRegionId::END");
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	public List<ColoadingVendorTO> getVendorsList(Integer regionId,
			String serviceType) throws CGBusinessException, CGSystemException {
		return coloadingDAO.getVendorsList(regionId, serviceType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingServiceImpl::getStockStdType::START");
		List<StockStandardTypeDO> typeDoList = null;
		List<StockStandardTypeTO> typeToList = null;
		typeDoList = coloadingDAO.getStockStdType(typeName);
		if (!StringUtil.isEmptyColletion(typeDoList)) {
			typeToList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(typeDoList,
							StockStandardTypeTO.class);
		}
		LOGGER.debug("ColoadingServiceImpl::getStockStdType::END");
		return typeToList;
	}

	public ColoadingTrainContractDO getTrainData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.getTrainData(coloadingTo);
	}

	public ColoadingAirDO getAirData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.getAirData(airColoadingTo);
	}
	
	public List<FlightDO> getFlightData(AirColoadingTO coloadingTo) {
		return coloadingDAO.getFlightList(coloadingTo);
	}
	
	public ColoadingTrainContractDO getTrainFutureData(TrainColoadingTO coloadingTo)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.getTrainFutureData(coloadingTo);
	}

	public ColoadingAirDO getAirFutureData(AirColoadingTO airColoadingTo)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.getAirFutureData(airColoadingTo);
	}
	
	public ColoadingAirDO saveColoadingAir(AirColoadingTO airColoadingTo, int userId)
			throws CGBusinessException, CGSystemException {
		ColoadingAirDO airDO = coloadingDAO.getAirData(airColoadingTo);
		
		airDO = ColoadingCommonConverter.convertAirColodingToToAirColoadingDO(airColoadingTo,airDO);
		return coloadingDAO.saveColoadingAir(airDO,userId,airColoadingTo.getRenewFlag());
	}
	
	@Override
	public ColoadingTrainContractDO saveColoadingTrain(
			TrainColoadingTO trainColoadingTo, int userId)
			throws CGBusinessException, CGSystemException {
		ColoadingTrainContractDO coloadingTrainContractDO = coloadingDAO.getTrainData(trainColoadingTo);
		coloadingTrainContractDO = ColoadingCommonConverter.convertTrainDoFromTrainTo(trainColoadingTo,coloadingTrainContractDO);
		return coloadingDAO.saveColoadingTrain(coloadingTrainContractDO,userId,trainColoadingTo.getRenewFlag());
	}


	public ColoadingDAO getColoadingDAO() {
		return coloadingDAO;
	}

	public void setColoadingDAO(ColoadingDAO coloadingDAO) {
		this.coloadingDAO = coloadingDAO;
	}

	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}


	public BusinessCommonService getBusinessCommonService() {
		return businessCommonService;
	}

	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	
	
	@Override
	public List<String> getVehicleList() throws CGBusinessException,
			CGSystemException {
		return coloadingDAO.getVehicleList();
	}

	@Override
	public ColoadingVehicleContractDO saveColoadingVehicle(
			ColoadingVehicleContractTO vehicleContractTO, int userId, OfficeTO officeTo)
			throws CGBusinessException, CGSystemException {
		ColoadingVehicleContractDO vehicleContractDO = null;
		vehicleContractDO = ColoadingCommonConverter.convertVehicleDoFromVehicleTo(vehicleContractTO,vehicleContractDO);
		return coloadingDAO.saveColoadingVehicle(vehicleContractDO,userId,vehicleContractTO.getRenewFlag(), officeTo);
	}

	@Override
	public List<CityDO> getCityDOList() throws CGBusinessException,
			CGSystemException {
		return coloadingDAO.getCityDOList();
	}

	@Override
	public ColoadingFuelRateEntryDO saveFuelRateEntry(
			FuelRateEntryTO fuelRateEntryTO, int userId)
			throws CGBusinessException, CGSystemException {
		ColoadingFuelRateEntryDO fuelRateEntryDO = null;
		fuelRateEntryDO = ColoadingCommonConverter.convertFuelRateEntryDoFromFuelRateEntryTo(fuelRateEntryTO, fuelRateEntryDO);
		return coloadingDAO.saveFuelRateEntry(fuelRateEntryDO,userId,fuelRateEntryTO.getRenewFlag());
	}
	
	

	/**
	 * This method is used to get the userId for logged in user
	 * 
	 * @param request
	 * @return
	 */
	public int getUserID(HttpServletRequest request) {
		LOGGER.debug("ColoadingServiceImpl:getUserID:Start");
		HttpSession session = request.getSession();
		int userId = 0;
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(ColoadingConstants.USER_INFO);
/*		if (userInfoTO != null) {
			if (!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpUserId())) {
				userId = userInfoTO.getEmpUserTo().getEmpUserId();
			}
		}*/
		
		if (userInfoTO != null) {
			if (!StringUtil.isNull(userInfoTO.getEmpUserTo().getUserId())) {
				userId = userInfoTO.getEmpUserTo().getUserId();
			}
		}		
		
		LOGGER.debug("ColoadingServiceImpl:getUserID:End");
		return userId;
	}
	
	/**
	 * This method is used to get the userId for logged in user
	 * 
	 * @param request
	 * @return
	 */
	public int getOfficeID(HttpServletRequest request) {
		LOGGER.debug("ColoadingServiceImpl:getOfficeID:Start");
		HttpSession session = request.getSession();
		int officeId = 0;
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(ColoadingConstants.USER_INFO);
		if (userInfoTO != null) {
			if (!StringUtil.isNull(userInfoTO.getOfficeTo().getOfficeId())) {
				officeId = userInfoTO.getOfficeTo().getOfficeId();
			}
		}
		LOGGER.debug("ColoadingServiceImpl:getOfficeID:End");
		return officeId;
	}

	@Override
	public ColoadingVehicleServiceEntryDO saveVehicleServiceEntry(
			VehicleServiceEntryTO serviceEntryTO, int userId, int officeID)
			throws CGBusinessException, CGSystemException {
		ColoadingVehicleServiceEntryDO vehicleServiceEntryDO = null;
		vehicleServiceEntryDO = ColoadingCommonConverter.convertFuelVSEDoFromVSETo(serviceEntryTO, vehicleServiceEntryDO);
		vehicleServiceEntryDO.setOfficeId(officeID);
		vehicleServiceEntryDO.setCreatedBy(userId);
		vehicleServiceEntryDO.setUpdatedBy(userId);
		return coloadingDAO.saveVehicleServiceEntry(vehicleServiceEntryDO);
	}

	@Override
	public ColoadingSurfaceRateEntryDO saveSurfaceRateEntry(
			SurfaceRateEntryTO surfaceRateEntryTO, int userId)
			throws CGBusinessException, CGSystemException {
		ColoadingSurfaceRateEntryDO surfaceRateEntryDO = null;
		surfaceRateEntryDO = ColoadingCommonConverter.convertSurfaceRateEntryDoFromSurfaceRateEntryTo(surfaceRateEntryTO, surfaceRateEntryDO);
		return coloadingDAO.saveSurfaceRateEntry(surfaceRateEntryDO, userId);
	}

	@Override
	public ColoadingFuelRateEntryDO loadFuelRateEntryData(
			FuelRateEntryTO fuelRateEntryTO) throws CGBusinessException,
			CGSystemException {
		return coloadingDAO.loadFuelRateEntryData(fuelRateEntryTO);
	}
	
	@Override
	public ColoadingFuelRateEntryDO loadFutureFuelRateEntryData(
			FuelRateEntryTO fuelRateEntryTO) throws CGBusinessException,
			CGSystemException {
		return coloadingDAO.loadFuelRateEntryData(fuelRateEntryTO);
	}

	@Override
	public ColoadingVehicleContractDO searchVehicle(
			ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.searchVehicle(vehicleContractTO);
	}

	@Override
	public ColoadingVehicleContractDO searchVehicleFutureData(
			ColoadingVehicleContractTO vehicleContractTO)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.searchVehicleFutureData(vehicleContractTO);
	}

	@Override
	public String validateFlightNo(String flightNo)
			throws CGBusinessException, CGSystemException {
		return coloadingDAO.validateFlightNo(flightNo);
	}

	@Override
	public ColoadingVehicleContractDO getVehicleContractDO(String date,
			String vehicleNumber) throws CGBusinessException, CGSystemException {
		return coloadingDAO.getVehicleContractDO(date, vehicleNumber);
	}

	@Override
	public void findCdAwbDetails(CdAwbModificationTO cdAwbModificationTO)
			throws CGBusinessException, CGSystemException {
		
		//validateCdAwbDate(cdAwbModificationTO);
		Set<String> CdAwbSet = new HashSet<String>();
		
		List<Object[]> loadConnectedDOs = coloadingDAO
				.getLoadConnectedDOs4CdAwbModifaction(cdAwbModificationTO);
		
		if (StringUtil.isEmptyColletion(loadConnectedDOs)) {
			ExceptionUtil
					.prepareBusinessException(ColoadingConstants.CD_AWB_DETAILS_NOT_FOUND);
		}
		ArrayList<CdAwbModificationDetailsTO> cdAwbModificationDetailsTOs = new ArrayList<>();
		
		for (Object[] object : loadConnectedDOs) {
			String dispatchedUsing = (String) object[3];
			if (StringUtils.equals(cdAwbModificationTO.getStatus(), ColoadingConstants.CD_AWB_STATUS_NEW)
					&& !StringUtils.equals(dispatchedUsing, CommonConstants.ENUM_DEFAULT_NULL)) {
				continue;
			}
			if (StringUtils.equals(cdAwbModificationTO.getStatus(), ColoadingConstants.CD_AWB_STATUS_UPDATED)
					&& StringUtils.equals(dispatchedUsing, CommonConstants.ENUM_DEFAULT_NULL)) {
				continue;
			}
			
			String tokenNumber = (String) object[2];
			String gatePassNumber = (String) object[7];

			if(tokenNumber != null && tokenNumber.length() > 0 && gatePassNumber != null && gatePassNumber.length() > 0 && (! CdAwbSet.contains(tokenNumber+gatePassNumber))) {
				CdAwbModificationDetailsTO cdAwbModificationDetailsTO = new CdAwbModificationDetailsTO();
				cdAwbModificationDetailsTO.setLoadConnectedId((Integer) object[0]);
				cdAwbModificationDetailsTO.setLoadMovementId((Integer) object[1]);
				cdAwbModificationDetailsTO.setTokenNumber(tokenNumber);
				cdAwbModificationDetailsTO.setDispatchedUsing(dispatchedUsing);
				cdAwbModificationDetailsTO.setDispatchedType((String) object[4]);
				cdAwbModificationDetailsTO.setTransportModeCode((String) object[5]);
				cdAwbModificationDetailsTO.setDispatchDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat((Date) object[6]));
				cdAwbModificationDetailsTO.setGatePassNumber(gatePassNumber);
				cdAwbModificationDetailsTO.setVendor((String) object[8]);
				cdAwbModificationDetailsTO.setDestOffice((String) object[9]);
				cdAwbModificationDetailsTOs.add(cdAwbModificationDetailsTO);
				
				CdAwbSet.add(tokenNumber+gatePassNumber);
			}
		}
		cdAwbModificationTO.setCdAwbModificationDetailsTOs(cdAwbModificationDetailsTOs);

		if (StringUtil.isEmptyColletion(cdAwbModificationDetailsTOs)) {
			ExceptionUtil.prepareBusinessException(ColoadingConstants.CD_AWB_DETAILS_NOT_FOUND);
		}
	}

	@Override
	public void updateCdAwbDetails(CdAwbModificationTO cdAwbModificationTO)
			throws CGBusinessException, CGSystemException {
		//Set<Integer> loadMovementIds = new TreeSet<>();
		final int length = cdAwbModificationTO.getLoadConnectedIds().length;
		for (int i = 0; i < length; i++) {
			if (StringUtil.isEmptyInteger(cdAwbModificationTO.getLoadConnectedIds()[i])) {
				continue;
			}
			/*if (!StringUtil.isEmptyInteger(cdAwbModificationTO.getLoadMovementIds()[i])) {
				loadMovementIds.add(cdAwbModificationTO.getLoadMovementIds()[i]);
			}*/
			CdAwbModificationDetailsTO cdAwbModificationDetailsTO = new CdAwbModificationDetailsTO();
			//cdAwbModificationDetailsTO.setLoadConnectedId(cdAwbModificationTO.getLoadConnectedIds()[i]);
			//DispatchedUsings and DispatchedTypes has been updating for based on the loadmovementids.
			cdAwbModificationDetailsTO.setLoadMovementId(cdAwbModificationTO.getLoadMovementIds()[i]);
			
			String dispatchedUsing = cdAwbModificationTO.getDispatchedUsings()[i];//CD/AWB/RR/Z
			String dispatchedType = cdAwbModificationTO.getDispatchedTypes()[i];//CC/FF/Z
			if(dispatchedType.equals("--SELECT--"))
				dispatchedType="";
			
			if(StringUtils.isBlank(dispatchedUsing)){
				dispatchedUsing = CommonConstants.ENUM_DEFAULT_NULL;
			}
			if(StringUtils.isBlank(dispatchedType)){
				dispatchedType = CommonConstants.ENUM_DEFAULT_NULL;
			}
			cdAwbModificationDetailsTO.setDispatchedUsing(dispatchedUsing);
			cdAwbModificationDetailsTO.setDispatchedType(dispatchedType);
			coloadingDAO.updateCdAwbDetails(cdAwbModificationDetailsTO);
		}
		/*if(!StringUtil.isEmptyColletion(loadMovementIds)){
			coloadingDAO.updateLoadMovementDbSyncFlagsByIds(loadMovementIds);
		}*/
	}

	@Override
	public ColoadingSurfaceRateEntryDO loadVendorSavedData(int vendorId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingServiceImpl:loadVendorSavedData:Start");
		ColoadingSurfaceRateEntryDO coloadingSurfaceRateEntryDO =   coloadingDAO.loadVendorSavedData(vendorId);
		LOGGER.debug("ColoadingServiceImpl:loadVendorSavedData:End");
		return coloadingSurfaceRateEntryDO;
	}
}
