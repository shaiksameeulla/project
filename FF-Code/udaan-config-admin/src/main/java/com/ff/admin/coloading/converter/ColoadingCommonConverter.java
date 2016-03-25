package com.ff.admin.coloading.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.AwbTO;
import com.ff.coloading.CdTO;
import com.ff.coloading.ColoadingVehicleContractTO;
import com.ff.coloading.FuelRateEntryTO;
import com.ff.coloading.SurfaceRateEntryTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.coloading.TrainDetailsTO;
import com.ff.coloading.VehicleServiceEntryTO;
import com.ff.domain.coloading.ColoadingAirAwbDO;
import com.ff.domain.coloading.ColoadingAirCdDO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingFuelRateEntryDO;
import com.ff.domain.coloading.ColoadingSurfaceRateEntryDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.coloading.ColoadingTrainContractRateDetailsDO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;

/**
 * This is the Common Converter for Coloading
 * 
 * @author isawarka
 * 
 */
public class ColoadingCommonConverter {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingCommonConverter.class);

	public static ColoadingAirDO convertAirColodingToToAirColoadingDO(
			AirColoadingTO airColoadingTo, ColoadingAirDO airDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertAirColodingToToAirColoadingDO::START");
		if (airColoadingTo != null) {
			if (airDO == null) {
				airDO = new ColoadingAirDO();
			}
			airDO.setOriginRegionId(airColoadingTo.getOrigionRegionID());
			airDO.setDestinationRegionId(airColoadingTo.getDestinationRegionID());
			airDO.setOriginCityId(airColoadingTo.getOrigionCityID());
			airDO.setDestinationCityId(airColoadingTo.getDestinationCityID());
			airDO.setVendorId(airColoadingTo.getVendorId());
			airDO.setEffectiveFrom(DateUtil.slashDelimitedstringToDDMMYYYYFormat(airColoadingTo.getEffectiveFrom()));
			airDO.setCdType(airColoadingTo.getCdType());
//			airDO.setSspRateAboveKg(airColoadingTo.getSspWeightSlab());
			airDO.setStoreStatus(airColoadingTo.getStoreStatus().charAt(0));
			
			if (ColoadingConstants.CD.equals(airColoadingTo.getCdType())) {
				airDO.setSspRateAboveKg(new Integer(0)); // Make it 0 in case of CD
				List<CdTO> cdTos = airColoadingTo.getCdToList();
				if (!CGCollectionUtils.isEmpty(cdTos)) {
					Set<ColoadingAirCdDO> airCdDOs = new HashSet<>(cdTos.size());
					for (CdTO cdTo : cdTos) {
						ColoadingAirCdDO airCdDO = convertCdToToCdDo(cdTo, airColoadingTo.getStoreStatus().charAt(0));
						airCdDO.setColoadingAir(airDO);
						airCdDOs.add(airCdDO);
					}
					airDO.setColoadingAirCds(airCdDOs);
				}
			} else if (ColoadingConstants.AWB.equals(airColoadingTo.getCdType())) {
				airDO.setSspRateAboveKg(airColoadingTo.getSspWeightSlab());
				List<AwbTO> awbTos = airColoadingTo.getAwbToList();
				if (!CGCollectionUtils.isEmpty(awbTos)) {
					Set<ColoadingAirAwbDO> airAwbDOs = new HashSet<>(
							awbTos.size());
					for (AwbTO awbTo : awbTos) {
						ColoadingAirAwbDO awbDO = convertAwbToToAwbDo(awbTo,
								airColoadingTo.getStoreStatus().charAt(0));
						awbDO.setColoadingAir(airDO);
						airAwbDOs.add(awbDO);
					}
					airDO.setColoadingAirAwbs(airAwbDOs);
				}
			}
			
		}
		LOGGER.debug("ColoadingCommonConverter::convertAirColodingToToAirColoadingDO::END");
		return airDO;
	}

	public static ColoadingTrainContractDO convertTrainDoFromTrainTo(
			TrainColoadingTO trainColoadingTo,
			ColoadingTrainContractDO coloadingTrainContractDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertTrainDoFromTrainTo::START");
		if (trainColoadingTo != null) {
			if (coloadingTrainContractDO == null) {
				coloadingTrainContractDO = new ColoadingTrainContractDO();
			}
			coloadingTrainContractDO.setOriginRegionId(trainColoadingTo
					.getOrigionRegionID());
			coloadingTrainContractDO.setDestRegionId(trainColoadingTo
					.getDestinationRegionID());
			coloadingTrainContractDO.setOriginCityId(trainColoadingTo
					.getOrigionCityID());
			coloadingTrainContractDO.setDestCityId(trainColoadingTo
					.getDestinationCityID());
			coloadingTrainContractDO
					.setVendorId(trainColoadingTo.getVendorId());
			coloadingTrainContractDO.setEffectiveFrom(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(trainColoadingTo
							.getEffectiveFrom()));
			coloadingTrainContractDO.setStoreStatus(trainColoadingTo
					.getStoreStatus().charAt(0));
			List<TrainDetailsTO> list = trainColoadingTo.getTrainDetailsList();
			if (!CGCollectionUtils.isEmpty(list)) {
				Set<ColoadingTrainContractRateDetailsDO> detailsDOs = new HashSet<>(
						list.size());
				ColoadingTrainContractRateDetailsDO detailsDO = null;
				for (TrainDetailsTO trainDetailsTo : list) {
					detailsDO = convertDetailsToToDetailsDo(trainDetailsTo,
							trainColoadingTo.getStoreStatus().charAt(0));
					detailsDO
							.setColoadingTrainContract(coloadingTrainContractDO);
					detailsDOs.add(detailsDO);
				}
				coloadingTrainContractDO
						.setColoadingTrainContractRateDtls(detailsDOs);
			}
		}
		LOGGER.debug("ColoadingCommonConverter::convertTrainDoFromTrainTo::END");
		return coloadingTrainContractDO;
	}

	public static TrainColoadingTO convertTrainToFromTrainDo(
			TrainColoadingTO trainColoadingTo,
			ColoadingTrainContractDO coloadingTrainContractDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertTrainToFromTrainDo::START");
		if (coloadingTrainContractDO != null) {
			if (coloadingTrainContractDO.getId() != null) {
				trainColoadingTo.setOrigionRegionID(coloadingTrainContractDO
						.getOriginRegionId());
				trainColoadingTo
						.setDestinationRegionID(coloadingTrainContractDO
								.getDestRegionId());
				trainColoadingTo.setOrigionCityID(coloadingTrainContractDO
						.getOriginCityId());
				trainColoadingTo.setDestinationCityID(coloadingTrainContractDO
						.getDestCityId());
				trainColoadingTo.setVendorId(coloadingTrainContractDO
						.getVendorId());
				trainColoadingTo.setEffectiveFrom(DateUtil
						.getDDMMYYYYDateToString(coloadingTrainContractDO
								.getEffectiveFrom()));
				trainColoadingTo.setStoreStatus(String
						.valueOf(coloadingTrainContractDO.getStoreStatus()));
			}
			Set<ColoadingTrainContractRateDetailsDO> detailsDOs = coloadingTrainContractDO
					.getColoadingTrainContractRateDtls();
			if (!CGCollectionUtils.isEmpty(detailsDOs)) {
				List<TrainDetailsTO> list = new ArrayList<>(detailsDOs.size());
				TrainDetailsTO detailsTo = null;
				for (ColoadingTrainContractRateDetailsDO detailsDO : detailsDOs) {
					detailsTo = convertDetailsToFromDetailsDo(detailsDO);
					list.add(detailsTo);
				}
				trainColoadingTo.setTrainDetailsList(list);
			}
		}
		LOGGER.debug("ColoadingCommonConverter::convertTrainToFromTrainDo::END");
		return trainColoadingTo;
	}

	private static ColoadingAirAwbDO convertAwbToToAwbDo(AwbTO awbTo,
			char storeStatus) {
		LOGGER.debug("ColoadingCommonConverter::convertCdToToCdDo::STRAT");
		ColoadingAirAwbDO airAwbDO = new ColoadingAirAwbDO();
		airAwbDO.setAwbId(awbTo.getAwbId());
		airAwbDO.setAirLineName(awbTo.getAirLine());
		airAwbDO.setFlightNo(awbTo.getFlightNo());
		airAwbDO.setFlightType(awbTo.getFlightType());
		airAwbDO.setMinTariff(awbTo.getMinTariff());
		airAwbDO.setW1(awbTo.getW1());
		airAwbDO.setW2(awbTo.getW2());
		airAwbDO.setW3(awbTo.getW3());
		airAwbDO.setW4(awbTo.getW4());
		airAwbDO.setW5(awbTo.getW5());
		airAwbDO.setW6(awbTo.getW6());
		airAwbDO.setFuelSurcharge(awbTo.getFuelSurcharge());
		airAwbDO.setOctroiPerBag(awbTo.getOctroiPerBag());
		airAwbDO.setOctroiPerKg(awbTo.getOctroiPerKG());
		airAwbDO.setOriginTspFlatRate(awbTo.getOriginTSPFlatRate());
		airAwbDO.setOriginTspPerKgRate(awbTo.getOriginTSPPerKGRate());
		airAwbDO.setDestTspFlatRate(awbTo.getDestinationTSPFlatRate());
		airAwbDO.setDestTspPerKgRate(awbTo.getDestinationTSPPerKGRate());
		airAwbDO.setAirportHandlingCharge(awbTo.getAirportHandlingCharges());
		airAwbDO.setXrayCharge(awbTo.getxRayCharge());
		airAwbDO.setOriginMinUtilCharge(awbTo.getOriginMinUtilizationCharge());
		airAwbDO.setOriginUtilChargePerKg(awbTo
				.getOriginUtilizationChargesPerKG());
		airAwbDO.setDestMinUtilCharge(awbTo
				.getDestinationMinUtilizationCharge());
		airAwbDO.setDestUtilChargePerKg(awbTo.getDestinationUtilizationChargesPerKG());
		airAwbDO.setAirlineServiceCharge(awbTo.getServiceChargeOfAirline());
		airAwbDO.setSurcharge(awbTo.getSurcharge());
		airAwbDO.setAirwayBill(awbTo.getAirWayBill());
		airAwbDO.setDiscountedPercentage(awbTo.getDiscountedPercent());
		airAwbDO.setSspRate(awbTo.getsSPRate());
		if (awbTo.getCreatedDate() != null) {
			airAwbDO.setCreatedBy(awbTo.getCreatedBy());
			airAwbDO.setCreatedDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(awbTo
							.getCreatedDate()));
		}
		airAwbDO.setStoreStatus(storeStatus);
		LOGGER.debug("ColoadingCommonConverter::convertCdToToCdDo::END");
		return airAwbDO;
	}

	private static ColoadingAirCdDO convertCdToToCdDo(CdTO cdTo,
			char storeStatus) {
		LOGGER.debug("ColoadingCommonConverter::convertCdToToCdDo::START");
		ColoadingAirCdDO airCdDO = new ColoadingAirCdDO();
		airCdDO.setCdId(cdTo.getCdId());
		airCdDO.setAirLineName(cdTo.getAirLine());
		airCdDO.setFlightNo(cdTo.getFlightNo());
		airCdDO.setBillingRate(cdTo.getBillingRate());
		airCdDO.setFuelSurcharge(cdTo.getFuelSurcharge());
		airCdDO.setOctroiPerBag(cdTo.getOctroiPerBag());
		airCdDO.setOctroiPerKg(cdTo.getOctroiPerKG());
		airCdDO.setSurcharge(cdTo.getSurcharge());
		airCdDO.setOtherCharges(cdTo.getOtherCharges());
		airCdDO.setCdValue(cdTo.getCd());
		airCdDO.setSspRate(cdTo.getsSPRate());
		if (cdTo.getCreatedDate() != null) {
			airCdDO.setCreatedBy(cdTo.getCreatedBy());
			airCdDO.setCreatedDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(cdTo.getCreatedDate()));
		}
		airCdDO.setStoreStatus(storeStatus);
		LOGGER.debug("ColoadingCommonConverter::convertCdToToCdDo::END");
		return airCdDO;
	}

	private static ColoadingTrainContractRateDetailsDO convertDetailsToToDetailsDo(
			TrainDetailsTO trainDetailsTo, char storeStatus) {
		LOGGER.debug("ColoadingCommonConverter::convertDetailsToToDetailsDo::START");
		ColoadingTrainContractRateDetailsDO detailsDO = new ColoadingTrainContractRateDetailsDO();
		detailsDO.setId(trainDetailsTo.getId());
		detailsDO.setTrainNo(trainDetailsTo.getTrainNo());
		detailsDO.setMinChargeableRate(trainDetailsTo.getMinChargeableRate());
		detailsDO.setRatePerKg(trainDetailsTo.getRatePerKG());
		detailsDO.setOtherChargesPerKg(trainDetailsTo.getOtherChargesPerKG());
		if (trainDetailsTo.getCreatedDate() != null) {
			detailsDO.setCreatedBy(trainDetailsTo.getCreatedBy());
			detailsDO.setCreatedDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(trainDetailsTo
							.getCreatedDate()));
		}
		detailsDO.setStoreStatus(storeStatus);
		LOGGER.debug("ColoadingCommonConverter::convertDetailsToToDetailsDo::END");
		return detailsDO;
	}

	public static TrainDetailsTO convertDetailsToFromDetailsDo(
			ColoadingTrainContractRateDetailsDO detailsDO) {
		LOGGER.debug("ColoadingCommonConverter::convertDetailsToFromDetailsDo::Start");
		TrainDetailsTO detailsTo;
		detailsTo = new TrainDetailsTO();
		if (detailsDO.getId() != null) {
			detailsTo.setId(detailsDO.getId());
			detailsTo.setMinChargeableRate(detailsDO.getMinChargeableRate());
			detailsTo.setRatePerKG(detailsDO.getRatePerKg());
			detailsTo.setOtherChargesPerKG(detailsDO.getOtherChargesPerKg());
			detailsTo.setStoreStatus(detailsDO.getStoreStatus());
			detailsTo.setCreatedBy(detailsDO.getCreatedBy());
			detailsTo.setCreatedDate(DateUtil.getDDMMYYYYDateToString(detailsDO
					.getCreatedDate()));
		}
		detailsTo.setTrainNo(detailsDO.getTrainNo());
		LOGGER.debug("ColoadingCommonConverter::convertDetailsToFromDetailsDo::END");
		return detailsTo;
	}

	public static AirColoadingTO convertAirColoadingToFromAirColoadingDO(ColoadingAirDO coloadingAirDO, AirColoadingTO coloadingTo, ArrayList<String> flightList) {
		LOGGER.debug("ColoadingCommonConverter::convertAirColoadingToFromAirColoadingDO::START");
		coloadingTo.setOrigionRegionID(coloadingAirDO.getOriginRegionId());
		coloadingTo.setOrigionCityID(coloadingAirDO.getOriginCityId());
		coloadingTo.setDestinationRegionID(coloadingAirDO
				.getDestinationRegionId());
		coloadingTo.setDestinationCityID(coloadingAirDO.getDestinationCityId());
		coloadingTo.setVendorId(coloadingAirDO.getVendorId());
		coloadingTo.setEffectiveFrom(DateUtil
				.getDDMMYYYYDateToString(coloadingAirDO.getEffectiveFrom()));
		coloadingTo.setCdType(coloadingAirDO.getCdType());
		coloadingTo.setSspWeightSlab(coloadingAirDO.getSspRateAboveKg());
		coloadingTo.setStoreStatus(String.valueOf(coloadingAirDO
				.getStoreStatus()));
		if (coloadingAirDO.getStoreStatus() == ColoadingConstants.RENEW) {
			coloadingTo.setIsRenewalAllow(false);
		}
		
		Set<ColoadingAirAwbDO> awbDOs = coloadingAirDO.getColoadingAirAwbs();
		if (awbDOs != null && awbDOs.size() > 0) {
			List<AwbTO> awbTos = new ArrayList<>(awbDOs.size());
			AwbTO awbTo = null;
			for (ColoadingAirAwbDO airAwbDO : awbDOs) {
				if(flightList.contains(airAwbDO.getFlightNo())) {
					awbTo = new AwbTO();
					awbTo.setAwbId(airAwbDO.getAwbId());
					awbTo.setAirLine(airAwbDO.getAirLineName());
					awbTo.setFlightNo(airAwbDO.getFlightNo().toUpperCase());
					awbTo.setFlightType(airAwbDO.getFlightType());
					awbTo.setMinTariff(airAwbDO.getMinTariff());
					awbTo.setW1(airAwbDO.getW1());
					awbTo.setW2(airAwbDO.getW2());
					awbTo.setW3(airAwbDO.getW3());
					awbTo.setW4(airAwbDO.getW4());
					awbTo.setW5(airAwbDO.getW5());
					awbTo.setW6(airAwbDO.getW6());
					awbTo.setFuelSurcharge(airAwbDO.getFuelSurcharge());
					awbTo.setOctroiPerBag(airAwbDO.getOctroiPerBag());
					awbTo.setOctroiPerKG(airAwbDO.getOctroiPerKg());
					awbTo.setOriginTSPFlatRate(airAwbDO.getOriginTspFlatRate());
					awbTo.setOriginTSPPerKGRate(airAwbDO.getOriginTspPerKgRate());
					awbTo.setDestinationTSPFlatRate(airAwbDO.getDestTspFlatRate());
					awbTo.setDestinationTSPPerKGRate(airAwbDO.getDestTspPerKgRate());
					awbTo.setAirportHandlingCharges(airAwbDO.getAirportHandlingCharge());
					awbTo.setxRayCharge(airAwbDO.getXrayCharge());
					awbTo.setOriginMinUtilizationCharge(airAwbDO
							.getOriginMinUtilCharge());
					awbTo.setOriginUtilizationChargesPerKG(airAwbDO
							.getOriginUtilChargePerKg());
					awbTo.setDestinationMinUtilizationCharge(airAwbDO
							.getDestMinUtilCharge());
					awbTo.setDestinationUtilizationChargesPerKG(airAwbDO
							.getDestUtilChargePerKg());
					awbTo.setServiceChargeOfAirline(airAwbDO
							.getAirlineServiceCharge());
					awbTo.setSurcharge(airAwbDO.getSurcharge());
					awbTo.setAirWayBill(airAwbDO.getAirwayBill());
					awbTo.setDiscountedPercent(airAwbDO.getDiscountedPercentage());
					awbTo.setsSPRate(airAwbDO.getSspRate());
					awbTo.setStoreStatus(airAwbDO.getStoreStatus());
					awbTo.setCreatedBy(airAwbDO.getCreatedBy());
					awbTo.setCreatedDate(DateUtil.getDDMMYYYYDateToString(airAwbDO
							.getCreatedDate()));

					awbTos.add(awbTo);
				}	
			}
			coloadingTo.setAwbToList(awbTos);
		}

		Set<ColoadingAirCdDO> cdDOs = coloadingAirDO.getColoadingAirCds();
		if (cdDOs != null && cdDOs.size() > 0) {
			List<CdTO> cdTos = new ArrayList<>(cdDOs.size());
			CdTO cdTo = null;
			for (ColoadingAirCdDO cdDO : cdDOs) {
				if(flightList.contains(cdDO.getFlightNo())) {				
					cdTo = new CdTO();
					cdTo.setCdId(cdDO.getCdId());
					cdTo.setAirLine(cdDO.getAirLineName());
					cdTo.setFlightNo(cdDO.getFlightNo().toUpperCase());
					cdTo.setBillingRate(cdDO.getBillingRate());
					cdTo.setFuelSurcharge(cdDO.getFuelSurcharge());
					cdTo.setOctroiPerBag(cdDO.getOctroiPerBag());
					cdTo.setOctroiPerKG(cdDO.getOctroiPerKg());
					cdTo.setSurcharge(cdDO.getSurcharge());
					cdTo.setOtherCharges(cdDO.getOtherCharges());
					cdTo.setCd(cdDO.getCdValue());
					cdTo.setsSPRate(cdDO.getSspRate());
					cdTo.setStoreStatus(cdDO.getStoreStatus());
					cdTo.setCreatedBy(cdDO.getCreatedBy());
					cdTo.setCreatedDate(DateUtil.getDDMMYYYYDateToString(cdDO.getCreatedDate()));
					cdTos.add(cdTo);
				}
			}
			coloadingTo.setCdToList(cdTos);
		}
		
		LOGGER.debug("ColoadingCommonConverter::convertAirColoadingToFromAirColoadingDO::END");
		return coloadingTo;
	}

	public static AwbTO populateAwbTo(AwbTO awbTo, Object[] values, ColoadingService coloadingService, List<AwbTO> awbTos, Set<String> flightSet, 
			ArrayList<String> flightList, Map<String, Set<String>> vendorToFlightsMap, String vendorId) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::populateAwbTo::START");
		StringBuffer errorMessage = new StringBuffer();
		
		String airlineName = ((String) values[0]).trim();
		
		awbTo.setAirLine(airlineName);
		String flightNumber = ((String) values[1]).toUpperCase().trim();
		
		awbTo.setFlightNo(flightNumber);
		
		if(! flightSet.contains(airlineName+flightNumber)) {
			if(StringUtil.isStringEmpty((String) values[1])){
				errorMessage.append("Invalid Content for FlightNo,");	
			}else{
				String fn = coloadingService.validateFlightNo(awbTo.getFlightNo());
				if (fn == null || fn.equals("NA")) {
					if(!flightList.contains(awbTo.getFlightNo())) {
						errorMessage.append("Invalid Flight Number,");
					} else {
						errorMessage.append("Invalid Air line,");
					}
					
					flightSet.add(airlineName+flightNumber);
					awbTo.setFlightSet(flightSet);
				}else if(!fn.equals("NA") && fn.equalsIgnoreCase(airlineName)){
						awbTo.setAirLine(fn);
						
						flightSet.add(airlineName+flightNumber);
						awbTo.setFlightSet(flightSet);
						
						if(!flightList.contains(flightNumber)) {
							errorMessage.append("Invalid Flight Number,");
						}
				}
				
				if(vendorId != null && vendorToFlightsMap != null && !vendorToFlightsMap.isEmpty()) {
					Set<String> vendorFlightSet = vendorToFlightsMap.get(vendorId);
					if(vendorFlightSet == null || (vendorFlightSet!= null && !vendorFlightSet.contains(flightNumber))) {
						errorMessage.append(" Flight Number not available for this vendor,");
					}
				} else {
					// No flight mapped to this vendor through 'Route Serviced By'
					errorMessage.append(" Flight Number not available for this vendor,");
				}
			}
			
			awbTo.setFlightType((String) values[2]);
			
			if(isNumeric((String) values[3]) && !StringUtil.isStringEmpty((String) values[3])){
				awbTo.setMinTariff(roundToTwo(Double.parseDouble((String) values[3])));
			}else{
				errorMessage.append("Invalid Content for mintariff,");
			}
			
			if(isNumeric((String) values[4]) && !StringUtil.isStringEmpty((String) values[4])){
				awbTo.setW1(roundToTwo(Double.parseDouble((String) values[4])));
			}else{
				errorMessage.append("Invalid Content for W1(0-44),");
			}
			if(isNumeric((String) values[5]) && !StringUtil.isStringEmpty((String) values[5])){
				awbTo.setW2(roundToTwo(Double.parseDouble((String) values[5])));
			}else{
				errorMessage.append("Invalid Content for W2(44.1-99),");
			}
		
			if(isNumeric((String) values[6]) && !StringUtil.isStringEmpty((String) values[6])){
				awbTo.setW3(roundToTwo(Double.parseDouble((String) values[6])));
			}else{
				errorMessage.append("Invalid Content for W3(99.1-249),");
			}
			if(isNumeric((String) values[7]) && !StringUtil.isStringEmpty((String) values[7])){
				awbTo.setW4(roundToTwo(Double.parseDouble((String) values[7])));
			}else{
				errorMessage.append("Invalid Content for W4(249.1-499),");
			}
			if(isNumeric((String) values[8]) && !StringUtil.isStringEmpty((String) values[8])){
				awbTo.setW5(roundToTwo(Double.parseDouble((String) values[8])));
			}else{
				errorMessage.append("Invalid Content for W5(499.1-999),");
			}
			if(isNumeric((String) values[9]) && !StringUtil.isStringEmpty((String) values[9])){
				awbTo.setW6(roundToTwo(Double.parseDouble((String) values[9])));
			}else{
				errorMessage.append("Invalid Content for W6(Above 999.1),");
			}
			
	
			if(isNumeric((String) values[10]) && !StringUtil.isStringEmpty((String) values[10])){
				awbTo.setFuelSurcharge(roundToTwo(Double.parseDouble((String) values[10])));
			}else{
				errorMessage.append("Invalid Content for FuelSurcharge,");
			}
			
			if(isNumeric((String) values[11]) && !StringUtil.isStringEmpty((String) values[11])){
				awbTo.setOctroiPerBag(roundToTwo(Double.parseDouble((String) values[11])));
			}else{
				errorMessage.append("Invalid Content for OctroiPerBag,");
			}
			
			if(isNumeric((String) values[12]) && !StringUtil.isStringEmpty((String) values[12])){
				awbTo.setOctroiPerKG(roundToTwo(Double.parseDouble((String) values[12])));
			}else{
				errorMessage.append("Invalid Content for OctroiPerKG,");
			}
			
			if(isNumeric((String) values[13]) && !StringUtil.isStringEmpty((String) values[13])){
				awbTo.setOriginTSPFlatRate(roundToTwo(Double.parseDouble((String) values[13])));
			}else{
				errorMessage.append("Invalid Content for OriginTSPFlatRate,");
			}
			
			if(isNumeric((String) values[14]) && !StringUtil.isStringEmpty((String) values[14])){
				awbTo.setOriginTSPPerKGRate(roundToTwo(Double.parseDouble((String) values[14])));
			}else{
				errorMessage.append("Invalid Content for OriginTSPPerKGRate,");
			}
			
			if(isNumeric((String) values[15]) && !StringUtil.isStringEmpty((String) values[15])){
				awbTo.setDestinationTSPFlatRate(roundToTwo(Double.parseDouble((String) values[15])));
			}else{
				errorMessage.append("Invalid Content for DestinationTSPFlatRate,");
			}
			
			if(isNumeric((String) values[16]) && !StringUtil.isStringEmpty((String) values[16])){
				awbTo.setDestinationTSPPerKGRate(roundToTwo(Double.parseDouble((String) values[16])));
			}else{
				errorMessage.append("Invalid Content for DestinationTSPPerKGRate,");
			}
			
			if(isNumeric((String) values[17]) && !StringUtil.isStringEmpty((String) values[17])){
				awbTo.setAirportHandlingCharges(roundToTwo(Double.parseDouble((String) values[17])));
			}else{
				errorMessage.append("Invalid Content for AirportHandlingCharges,");
			}
			
			if(isNumeric((String) values[18]) && !StringUtil.isStringEmpty((String) values[18])){
				awbTo.setxRayCharge(roundToTwo(Double.parseDouble((String) values[18])));
			}else{
				errorMessage.append("Invalid Content for X-RayCharge,");
			}
			
			if(isNumeric((String) values[19]) && !StringUtil.isStringEmpty((String) values[19])){
				awbTo.setOriginMinUtilizationCharge(roundToTwo(Double.parseDouble((String) values[19])));
			}else{
				errorMessage.append("Invalid Content for OriginMinUtilizationCharge,");
			}
			
			if(isNumeric((String) values[20]) && !StringUtil.isStringEmpty((String) values[20])){
				awbTo.setOriginUtilizationChargesPerKG(roundToTwo(Double.parseDouble((String) values[20])));
			}else{
				errorMessage.append("Invalid Content for OriginUtilizationChargesPerKG,");
			}
			
			if(isNumeric((String) values[21]) && !StringUtil.isStringEmpty((String) values[21])){
				awbTo.setDestinationMinUtilizationCharge(roundToTwo(Double.parseDouble((String) values[21])));
			}else{
				errorMessage.append("Invalid Content for DestinationMinUtilizationCharge,");
			}
			
			if(isNumeric((String) values[22]) && !StringUtil.isStringEmpty((String) values[22])){
				awbTo.setDestinationUtilizationChargesPerKG(roundToTwo(Double.parseDouble((String) values[22])));
			}else{
				errorMessage.append("Invalid Content for DestinationUtilizationChargesPerKG,");
			}
			
			if(isNumeric((String) values[23]) && !StringUtil.isStringEmpty((String) values[23])){
				awbTo.setServiceChargeOfAirline(roundToTwo(Double.parseDouble((String) values[23])));
			}else{
				errorMessage.append("Invalid Content for setSurcharge,");
			}
			
			if(isNumeric((String) values[24]) && !StringUtil.isStringEmpty((String) values[24])){
				awbTo.setSurcharge(roundToTwo(Double.parseDouble((String) values[24])));
			}else{
				errorMessage.append("Invalid Content for ServiceChargeOfAirline,");
			}
			
			if(isNumeric((String) values[25]) && !StringUtil.isStringEmpty((String) values[25])){
				awbTo.setAirWayBill(roundToTwo(Double.parseDouble((String) values[25])));
			}else{
				errorMessage.append("Invalid Content for AirWayBill,");
			}
			
			if(isNumeric((String) values[26]) && !StringUtil.isStringEmpty((String) values[26])){
				awbTo.setDiscountedPercent(roundToTwo(Double.parseDouble((String) values[26])));
			}else{
				errorMessage.append("Invalid Content for DiscountedPercent,");
			}
			
			if(isNumeric((String) values[27]) && !StringUtil.isStringEmpty((String) values[27])){
				awbTo.setsSPRate(roundToTwo(Double.parseDouble((String) values[27])));
			}else{
				errorMessage.append("Invalid Content for SSPRate,");
			}
			if (!"".equals(errorMessage.toString())) {
				awbTo.setErroString(errorMessage.toString());
			}
			LOGGER.debug("ColoadingCommonConverter::populateAwbTo::END");
		} else {
			// errorMessage.append("Flight Number already uploaded,");
			errorMessage.append("Duplicate Flight Number,");
			awbTo.setAirLine(airlineName);
			awbTo.setErroString(errorMessage.toString());
		}
		
		return awbTo;
	}

	private static boolean isDuplicateFlights(AwbTO awbTO, List<AwbTO> awbTos) {
		if(StringUtils.isNotBlank(awbTO.getFlightNo()) && !StringUtil.isEmptyList(awbTos)){
			for (AwbTO awbTO2 : awbTos) {
				if(StringUtils.equals(awbTO2.getFlightNo(), awbTO.getFlightNo())){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public static CdTO populateCdTo(CdTO cdTo, Object[] values, ColoadingService coloadingService, Set<String> flightSet, ArrayList<String> flightList, 
			Map<String, Set<String>> vendorToFlightsMap, String vendorId) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::populateCdTo::START");
		StringBuffer errorMessage = new StringBuffer();
		String airName = ((String) values[0]).trim();
		String airlineName = airName.trim();
		//cdTo.setAirLine((String) values[0]);
		String flightNum = ((String) values[1]).toUpperCase().trim();
		String flightNumber = flightNum.trim();
		
		if(! flightSet.contains(airlineName+flightNumber)) {			
			cdTo.setAirLine(airlineName);
			cdTo.setFlightNo(flightNumber);
			
			if(StringUtil.isStringEmpty(((String) values[1]).trim())){
				errorMessage.append("Invalid Content for FlightNo,");
			}
			else{
				String fn = coloadingService.validateFlightNo(cdTo.getFlightNo());
				if (fn == null || fn.equals("NA")) {
					
					if(!flightList.contains(cdTo.getFlightNo())) {
						errorMessage.append("Invalid Flight Number,");
					} else {
						errorMessage.append("Invalid Air line,");
					}
					
					flightSet.add(airlineName+flightNumber);
					cdTo.setFlightSet(flightSet);
					
				}else // {
					if(!fn.equals("NA") && fn.equalsIgnoreCase(airlineName)){
						cdTo.setAirLine(fn);
						flightSet.add(airlineName+flightNumber);
						cdTo.setFlightSet(flightSet);
						
						if(!flightList.contains(flightNumber)) {
							errorMessage.append("Invalid Flight Number,");
						}						
					} 
					
					if(vendorId != null && vendorToFlightsMap != null && !vendorToFlightsMap.isEmpty()) {
						Set<String> vendorFlightSet = vendorToFlightsMap.get(vendorId);
						if(vendorFlightSet == null || (vendorFlightSet!= null && !vendorFlightSet.contains(flightNumber))) {
							errorMessage.append(" Flight Number not available for this vendor,");
						}
					}else {
						// No flight mapped to this vendor through 'Route Serviced By'
						errorMessage.append(" Flight Number not available for this vendor,");
					}				
			}
		
			if(isNumeric((String) values[2]) && !StringUtil.isStringEmpty((String) values[2])){
				cdTo.setBillingRate(roundToTwo(Double.parseDouble((String) values[2])));
			}else{
				errorMessage.append("Invalid Content for BillingRate,");
			}
			if(isNumeric((String) values[3]) && !StringUtil.isStringEmpty((String) values[3])){
				cdTo.setFuelSurcharge(roundToTwo(Double.parseDouble((String) values[3])));
			}else{
				errorMessage.append("Invalid Content for FuelSurcharge,");
			}
			if(isNumeric((String) values[4]) && !StringUtil.isStringEmpty((String) values[4])){
				cdTo.setOctroiPerBag(roundToTwo(Double.parseDouble((String) values[4])));
			}else{
				errorMessage.append("Invalid Content for OctroiPerBag,");
			}
			if(isNumeric((String) values[5]) && !StringUtil.isStringEmpty((String) values[5])){
				cdTo.setOctroiPerKG(roundToTwo(Double.parseDouble((String) values[5])));
			}else{
				errorMessage.append("Invalid Content for OctroiPerKG,");
			}
			if(isNumeric((String) values[6]) && !StringUtil.isStringEmpty((String) values[6])){
				cdTo.setSurcharge(roundToTwo(Double.parseDouble((String) values[6])));
			}else{
				errorMessage.append("Invalid Content for Surcharge,");
			}
			if(isNumeric((String) values[7]) && !StringUtil.isStringEmpty((String) values[7])){
				cdTo.setOtherCharges(roundToTwo(Double.parseDouble((String) values[7])));
			}else{
				errorMessage.append("Invalid Content for OtherCharges,");
			}
			
			if(isNumeric((String) values[8]) && !StringUtil.isStringEmpty((String) values[8])){
				cdTo.setCd(roundToTwo(Double.parseDouble((String) values[8])));
			}else{
				errorMessage.append("Invalid Content for Cd,");
			}
			if(isNumeric((String) values[9]) && !StringUtil.isStringEmpty((String) values[9])){
				cdTo.setsSPRate(roundToTwo(Double.parseDouble((String) values[9])));
			}else{
				errorMessage.append("Invalid Content for SSPRate,");
			}
			if (!"".equals(errorMessage.toString())) {
				cdTo.setErroString(errorMessage.toString());
			}
		} else {	
			errorMessage.append("Duplicate Flight Number,");	
			cdTo.setAirLine(airlineName);
			cdTo.setErroString(errorMessage.toString());
		}
		
		LOGGER.debug("ColoadingCommonConverter::populateCdTo::END");
		return cdTo;
	}

	public static ColoadingVehicleContractDO convertVehicleDoFromVehicleTo(
			ColoadingVehicleContractTO vehicleContractTO,
			ColoadingVehicleContractDO vehicleContractDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertVehicleDoFromVehicleTo::START");
		
		if (vehicleContractTO != null) {
			if (vehicleContractDO == null) {
				vehicleContractDO = new ColoadingVehicleContractDO();
			}
			vehicleContractDO.setVehicleNo(vehicleContractTO.getVehicleNo());
			vehicleContractDO.setVendorId(vehicleContractTO.getVendorId());
			vehicleContractDO
					.setVehicleType(vehicleContractTO.getVehicleType());
			vehicleContractDO.setCapacity(vehicleContractTO.getCapacity());
			vehicleContractDO.setEffectiveFrom(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(vehicleContractTO
							.getEffectiveFrom()));
			vehicleContractDO.setRateType(vehicleContractTO.getRateType());
			vehicleContractDO.setDutyHours(vehicleContractTO.getDutyHours());
			vehicleContractDO.setAverage(vehicleContractTO.getAverage());
			vehicleContractDO.setRent(vehicleContractTO.getRent());
			vehicleContractDO.setFreeKm(vehicleContractTO.getFreeKm());
			vehicleContractDO.setPerKmRate(vehicleContractTO.getPerKmRate());
			vehicleContractDO
					.setPerHourRate(vehicleContractTO.getPerHourRate());
			vehicleContractDO.setFuelType(vehicleContractTO.getFuelType());
			vehicleContractDO.setOthers(vehicleContractTO.getOthers());
			vehicleContractDO.setNoOfDays(vehicleContractTO.getNoOfDays());
			vehicleContractDO.setStoreStatus(vehicleContractTO.getStoreStatus()
					.charAt(0));
			
			if(vehicleContractTO.getGpsEnabled() == null) {
				vehicleContractDO.setGpsEnabled(false);
			}
			else if(null != vehicleContractTO.getGpsEnabled() && vehicleContractTO.getGpsEnabled()) {
				vehicleContractDO.setGpsEnabled(vehicleContractTO.getGpsEnabled());
			}
		}
		LOGGER.debug("ColoadingCommonConverter::convertVehicleDoFromVehicleTo::END");
		return vehicleContractDO;
	}

	public static ColoadingVehicleContractTO convertVehicleToFromVehicleDo(
			ColoadingVehicleContractTO vehicleContractTO,
			ColoadingVehicleContractDO vehicleContractDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertVehicleToFromVehicleDo::START");
		if (vehicleContractDO != null && vehicleContractTO != null) {
			vehicleContractTO.setVehicleNo(vehicleContractDO.getVehicleNo());
			vehicleContractTO.setVendorId(vehicleContractDO.getVendorId());
			vehicleContractTO
					.setVehicleType(vehicleContractDO.getVehicleType());
			vehicleContractTO.setCapacity(vehicleContractDO.getCapacity());
			vehicleContractTO
					.setEffectiveFrom(DateUtil
							.getDDMMYYYYDateToString(vehicleContractDO
									.getEffectiveFrom()));
			vehicleContractTO.setRateType(vehicleContractDO.getRateType());
			vehicleContractTO.setDutyHours(vehicleContractDO.getDutyHours());
			vehicleContractTO.setAverage(vehicleContractDO.getAverage());
			vehicleContractTO.setRent(vehicleContractDO.getRent());
			vehicleContractTO.setFreeKm(vehicleContractDO.getFreeKm());
			vehicleContractTO.setPerKmRate(vehicleContractDO.getPerKmRate());
			vehicleContractTO
					.setPerHourRate(vehicleContractDO.getPerHourRate());
			vehicleContractTO.setFuelType(vehicleContractDO.getFuelType());
			vehicleContractTO.setOthers(vehicleContractDO.getOthers());
			vehicleContractTO.setNoOfDays(vehicleContractDO.getNoOfDays());
			vehicleContractTO.setStoreStatus(String.valueOf(vehicleContractDO
					.getStoreStatus()));
			
			vehicleContractTO.setGpsEnabled(vehicleContractDO.getGpsEnabled());
		}
		LOGGER.debug("ColoadingCommonConverter::convertVehicleToFromVehicleDo::END");
		return vehicleContractTO;
	}

	public static ColoadingFuelRateEntryDO convertFuelRateEntryDoFromFuelRateEntryTo(
			FuelRateEntryTO fuelRateEntryTO,
			ColoadingFuelRateEntryDO fuelRateEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryDoFromFuelRateEntryTo::START");
		if (fuelRateEntryTO != null) {
			if (fuelRateEntryDO == null) {
				fuelRateEntryDO = new ColoadingFuelRateEntryDO();
			}
			fuelRateEntryDO.setId(fuelRateEntryTO.getId());
			fuelRateEntryDO.setCityId(fuelRateEntryTO.getCityId());
			fuelRateEntryDO.setEffectiveFrom(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(fuelRateEntryTO
							.getEffectiveFrom()));
			fuelRateEntryDO.setPetrol(fuelRateEntryTO.getPetrol());
			fuelRateEntryDO.setDiesel(fuelRateEntryTO.getDiesel());
			fuelRateEntryDO.setCng(fuelRateEntryTO.getCng());
			fuelRateEntryDO.setLpg(fuelRateEntryTO.getLpg());
			fuelRateEntryDO.setStoreStatus(fuelRateEntryTO.getStoreStatus()
					.charAt(0));
		}
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryDoFromFuelRateEntryTo::END");
		return fuelRateEntryDO;
	}

	public static FuelRateEntryTO convertFuelRateEntryToFromFuelRateEntryDo(
			FuelRateEntryTO fuelRateEntryTO,
			ColoadingFuelRateEntryDO fuelRateEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryToFromFuelRateEntryDo::START");
		if (fuelRateEntryDO != null && fuelRateEntryTO != null) {
			fuelRateEntryTO.setId(fuelRateEntryDO.getId());
			fuelRateEntryTO.setCityId(fuelRateEntryDO.getCityId());
			fuelRateEntryTO.setEffectiveFrom(DateUtil
					.getDDMMYYYYDateToString(fuelRateEntryDO.getEffectiveFrom()));
			fuelRateEntryTO.setPetrol(fuelRateEntryDO.getPetrol());
			fuelRateEntryTO.setDiesel(fuelRateEntryDO.getDiesel());
			fuelRateEntryTO.setCng(fuelRateEntryDO.getCng());
			fuelRateEntryTO.setLpg(fuelRateEntryDO.getLpg());
			fuelRateEntryTO.setStoreStatus(String.valueOf(fuelRateEntryDO
					.getStoreStatus()));
		}
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryToFromFuelRateEntryDo::END");
		return fuelRateEntryTO;
	}

	public static VehicleServiceEntryTO convertFuelVSEToFromVSEDo(
			VehicleServiceEntryTO vehicleServiceEntryTO,
			ColoadingVehicleServiceEntryDO vehicleServiceEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertFuelVSEToFromVSEDo::START");
		if (vehicleServiceEntryDO != null && vehicleServiceEntryTO != null) {
			vehicleServiceEntryTO.setId(vehicleServiceEntryDO.getId());
			vehicleServiceEntryTO.setDate(DateUtil
					.getDDMMYYYYDateToString(vehicleServiceEntryDO.getDate()));
			vehicleServiceEntryTO.setVehicalNumber(vehicleServiceEntryDO
					.getVehNumber());
			vehicleServiceEntryTO.setOt(vehicleServiceEntryDO.getOt());
			vehicleServiceEntryTO.setOpeningKm(vehicleServiceEntryDO
					.getOpeningKm());
			vehicleServiceEntryTO.setClosingKm(vehicleServiceEntryDO
					.getClosingKm());
		}
		LOGGER.debug("ColoadingCommonConverter::convertFuelVSEToFromVSEDo::END");
		return vehicleServiceEntryTO;
	}

	public static ColoadingVehicleServiceEntryDO convertFuelVSEDoFromVSETo(
			VehicleServiceEntryTO vehicleServiceEntryTO,
			ColoadingVehicleServiceEntryDO vehicleServiceEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryDoFromFuelRateEntryTo::START");
		if (vehicleServiceEntryTO != null) {
			if (vehicleServiceEntryDO == null) {
				vehicleServiceEntryDO = new ColoadingVehicleServiceEntryDO();
			}
			vehicleServiceEntryDO.setId(vehicleServiceEntryTO.getId());
			vehicleServiceEntryDO.setDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(vehicleServiceEntryTO
							.getDate()));
			vehicleServiceEntryDO.setVehNumber(vehicleServiceEntryTO
					.getVehicalNumber());
			vehicleServiceEntryDO.setOt(vehicleServiceEntryTO.getOt());
			vehicleServiceEntryDO.setOpeningKm(vehicleServiceEntryTO
					.getOpeningKm());
			vehicleServiceEntryDO.setClosingKm(vehicleServiceEntryTO
					.getClosingKm());
		}
		LOGGER.debug("ColoadingCommonConverter::convertFuelRateEntryDoFromFuelRateEntryTo::END");
		return vehicleServiceEntryDO;
	}

	public static SurfaceRateEntryTO convertSurfaceRateEntryToFromSurfaceRateEntryDo(
			SurfaceRateEntryTO surfaceRateEntryTO,
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertSurfaceRateEntryToFromSurfaceRateEntryDo::START");
		if (surfaceRateEntryDO != null && surfaceRateEntryTO != null) {
			surfaceRateEntryTO.setFromDate(DateUtil
					.getDDMMYYYYDateToString(surfaceRateEntryDO.getFromDate()));
			surfaceRateEntryTO.setVendorId(surfaceRateEntryDO.getVendorId());
			surfaceRateEntryTO.setToWeight(surfaceRateEntryDO.getToWeight());
			surfaceRateEntryTO.setRate(surfaceRateEntryDO.getRate());
			surfaceRateEntryTO.setAdditionalPerKg(surfaceRateEntryDO
					.getAdditionalPerKg());

		}
		LOGGER.debug("ColoadingCommonConverter::convertSurfaceRateEntryToFromSurfaceRateEntryDo::END");
		return surfaceRateEntryTO;
	}

	public static ColoadingSurfaceRateEntryDO convertSurfaceRateEntryDoFromSurfaceRateEntryTo(
			SurfaceRateEntryTO surfaceRateEntryTO,
			ColoadingSurfaceRateEntryDO surfaceRateEntryDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingCommonConverter::convertSurfaceRateEntryDoFromSurfaceRateEntryTo::START");
		if (surfaceRateEntryTO != null) {
			if (surfaceRateEntryDO == null) {
				surfaceRateEntryDO = new ColoadingSurfaceRateEntryDO();
			}
			surfaceRateEntryDO.setFromDate(DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(surfaceRateEntryTO
							.getFromDate()));
			surfaceRateEntryDO.setVendorId(surfaceRateEntryTO.getVendorId());
			surfaceRateEntryDO.setToWeight(surfaceRateEntryTO.getToWeight());
			surfaceRateEntryDO.setRate(surfaceRateEntryTO.getRate());
			surfaceRateEntryDO.setAdditionalPerKg(surfaceRateEntryTO
					.getAdditionalPerKg());
		}
		LOGGER.debug("ColoadingCommonConverter::convertSurfaceRateEntryDoFromSurfaceRateEntryTo::END");
		return surfaceRateEntryDO;
	} 

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static double roundToTwo(double value) {
	    return(Math.round(value * 100.00) / 100.00);
	}
	
}
