package com.ff.admin.routeservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.admin.routeservice.dao.RouteServicedDAO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.routeserviced.RouteDO;
import com.ff.domain.routeserviced.RouteModeMappingDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TransshipmentRouteDO;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.transport.FlightDO;
import com.ff.domain.transport.TrainDO;
import com.ff.domain.transport.TransportDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.RouteTO;
import com.ff.routeserviced.ServiceByTypeTO;
import com.ff.routeserviced.ServicedByTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.to.routeservice.PureRouteTO;
import com.ff.transport.AirlineTO;
import com.ff.transport.FlightTO;
import com.ff.transport.PortTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.TransportTO;
import com.ff.transport.VehicleTO;
import com.ff.universe.routeserviced.constant.RouteUniversalConstants;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;
/**
 * @author rmaladi
 * 
 */

public class RouteServicedServiceImpl implements RouteServicedService
{
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RouteServicedServiceImpl.class);
	
	private RouteServicedDAO routeServicedDAO;
	
	public RouteServicedDAO getRouteServicedDAO() {
		return routeServicedDAO;
	}

	public void setRouteServicedDAO(RouteServicedDAO routeServicedDAO) {
		this.routeServicedDAO = routeServicedDAO;
	}


	private RouteServicedCommonService routeServicedCommonService;
	
	public RouteServicedCommonService getRouteServicedCommonService() {
		return routeServicedCommonService;
	}

	public void setRouteServicedCommonService(
			RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}
	
	/** 
     * Get TransportMode list
     * This method will return  TransportMode List 
     * @inputparam 
     * @return  	TransportMode List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
		@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		return routeServicedCommonService.getAllTransportModeList();
	}
		
	/** 
     * Get Regions List
     * This method will return  List of Regions 
     * @inputparam 
     * @return  	List<RegionTO> 
     * @author      Rohini  Maladi  
     */
		@Override
	public List<RegionTO> getAllRegions()
			throws CGBusinessException, CGSystemException {
		return routeServicedCommonService.getAllRegions();
	}
	
	/** 
     * Get Cities List
     * This method will return  List of Cities 
     * @inputparam 
     * @return  	List<CityTO> 
     * @author      Rohini  Maladi  
     */
	
	public List<CityTO> getAllCities() throws CGBusinessException, CGSystemException{
		
		return routeServicedCommonService.getAllCities();

	}
	
	/** 
     * Save Pure Route
     * This method save the pure route details in database
     * @inputparam  PureRouteTO Object
     * @return  	
     * @author      Rohini  Maladi  
     */
	@Override
	public String savePureRoute(PureRouteTO pureRouteTO, int userId)throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedServiceImpl::savePureRoute::START------------>:::::::");
		boolean routeStatus = Boolean.FALSE;
		String status = CommonConstants.FAILURE;
		Integer[] tripIdsArr = null;
		
		if(!StringUtil.isNull(pureRouteTO)){
		
			List<TripDO> tripDOList = new ArrayList<TripDO>();
			RouteModeMappingDO routeModeMappingDO = new RouteModeMappingDO();
			pureRouteDomainConverter(pureRouteTO, tripDOList, routeModeMappingDO, userId);
			String tripIdsStr = pureRouteTO.getTripIdsArrStr();
			
			if(!StringUtil.isStringEmpty(tripIdsStr)){
				String[] tripIdsStrArr = tripIdsStr.split(CommonConstants.COMMA);
				int arrLen = tripIdsStrArr.length;
				tripIdsArr = new Integer[arrLen];
				for(int i = 0;i<arrLen;i++)
					tripIdsArr[i] = StringUtil.parseInteger(tripIdsStrArr[i]);
			}
			if(!CGCollectionUtils.isEmpty(tripDOList) && !StringUtil.isNull(routeModeMappingDO)){
				routeStatus = routeServicedDAO.savePureRoute(tripDOList,routeModeMappingDO,tripIdsArr);
			}else if(!StringUtil.isNull(tripIdsArr)){
				routeStatus = routeServicedDAO.savePureRoute(null,null,tripIdsArr);
			}
			if(routeStatus){
				status = CommonConstants.SUCCESS;
			}
			
		}
		
		LOGGER.debug("RouteServicedServiceImpl::savePureRoute::END------------>:::::::");
		return status;
	}
	
	/** 
     * Convert TO object to DO Object
     * This method Converts PureRouteTO object to TripDO object and provides List of TripDO
     * @inputparam  PureRouteTO Object
     * @return  	List<TripDO>
     * @author      Rohini  Maladi  
     */
		private void pureRouteDomainConverter(PureRouteTO pureRouteTO,
			List<TripDO> tripDOList, RouteModeMappingDO routeModeMappingDO, int userId) throws CGBusinessException, CGSystemException {		

			LOGGER.debug("RouteServicedServiceImpl::pureRouteDomainConverter::START------------>:::::::");
		RouteDO routeDO = new RouteDO();
		CityDO originCityDO = new CityDO();
		Integer rhoOfcId = null;
		
		if(!StringUtil.isEmptyInteger(pureRouteTO.getOriginStationId())){
		originCityDO.setCityId(pureRouteTO.getOriginStationId());
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.ORIGIN_CITY});
		}
		CityDO destinationCityDO = new CityDO();
		if(!StringUtil.isEmptyInteger(pureRouteTO.getOriginStationId())){
		destinationCityDO.setCityId(pureRouteTO.getDestinationStationId());
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DEST_CITY});
		}
		TransportTO transportTO = new TransportTO();
		TransportModeTO transportModeTO = new TransportModeTO();
		TransportModeDO transportModeDO = new TransportModeDO();
		TransportDO transportDO = new TransportDO();
		
		routeDO.setOriginCityDO(originCityDO);				
		routeDO.setDestCityDO(destinationCityDO);
		
		Integer routeId = null;
		routeId = routeServicedCommonService.getRouteIdByOriginCityIdAndDestCityId(originCityDO.getCityId(),destinationCityDO.getCityId());
		if(StringUtil.isNull(routeId)){
			saveRoute(routeDO);
			routeId = routeServicedCommonService.getRouteIdByOriginCityIdAndDestCityId(originCityDO.getCityId(),destinationCityDO.getCityId());
		}
		
		if(!StringUtil.isNull(routeId)){
		routeDO.setRouteId(routeId);
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_STATION_ERROR, new String[]{RouteServiceCommonConstants.ORIGIN_CITY, RouteServiceCommonConstants.DEST_CITY});
		}
		
		if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.ROAD_CODE)){
			OfficeTO rhoOfcTO = routeServicedCommonService.getRHOOfcIdByRegion(Integer.parseInt(pureRouteTO.getOriginRegion()));
			if(!StringUtil.isNull(rhoOfcTO)){
				rhoOfcId = rhoOfcTO.getOfficeId();
			}
		}
			
		if(!StringUtil.isStringEmpty(pureRouteTO.getTransportMode())){
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportMode().split(CommonConstants.TILD)[0])){
				transportModeDO.setTransportModeId(Integer.parseInt(pureRouteTO.getTransportMode().split(CommonConstants.TILD)[0]));
				transportModeTO.setTransportModeId(Integer.parseInt(pureRouteTO.getTransportMode().split(CommonConstants.TILD)[0]));
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
			}
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportMode().split(CommonConstants.TILD)[0])){
				transportModeTO.setTransportModeCode(pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
			}
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
		}
		routeModeMappingDO.setRouteDO(routeDO);
		routeModeMappingDO.setTransportModeDO(transportModeDO);
		
		
		if(!StringUtil.isNull(routeId) && !StringUtil.isNull(transportModeDO.getTransportModeId())){
			List<RouteModeMappingDO> routeModeMappingDOList = routeServicedDAO.getRouteModeMappingDetails(routeId, transportModeDO.getTransportModeId());
			if(!CGCollectionUtils.isEmpty(routeModeMappingDOList)){
			routeModeMappingDO.setRouteModeMappingId(routeModeMappingDOList.get(0).getRouteModeMappingId());
			}
		}
		
		
		
		TripDO tripdo = new TripDO();
		TransportTO trTO = null;
		int len = pureRouteTO.getTransportNumber().length;
		if(len>0){
		for(int i=0;i<len;i++)
		{
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportNumber()[i])){
			transportTO = new TransportTO();	
			trTO = new TransportTO();
			tripdo = new TripDO();
			transportDO = new TransportDO();
			transportTO.setTransportModeTO(transportModeTO);
			
			//capturing Flight Details
			if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.AIR_CODE))
			{
			FlightTO flightTO = new FlightTO();
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportNumber()[i])){
				flightTO.setFlightNumber(pureRouteTO.getTransportNumber()[i]);
				String airlineCode="";
				if(!StringUtil.isEmpty(pureRouteTO.getAirlineCode()) && !StringUtil.isStringEmpty(pureRouteTO.getAirlineCode()[i])){
					airlineCode=pureRouteTO.getAirlineCode()[i];
				}
				flightTO.setAirlineCode(airlineCode);
				if(!CGCollectionUtils.isEmpty(pureRouteTO.getAirlinesMap())){
					flightTO.setAirlineName(pureRouteTO.getAirlinesMap().get(airlineCode));
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT});
			}
			if(!StringUtil.isEmptyInteger(pureRouteTO.getTransporterId()[i])){
				flightTO.setFlightId(pureRouteTO.getTransporterId()[i]);
			}
			transportTO.setFlightTO(flightTO);
			
			} //capturing Train Details
			else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.TRAIN_CODE))
			{
			TrainTO trainTO = new TrainTO();
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportNumber()[i])){
				trainTO.setTrainNumber(pureRouteTO.getTransportNumber()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN});
			}
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportNumber()[i])){
				trainTO.setTrainName(pureRouteTO.getTransportName()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN});
			}	
			
			if(!StringUtil.isEmptyInteger(pureRouteTO.getTransporterId()[i])){
				trainTO.setTrainId(pureRouteTO.getTransporterId()[i]);			
			}
			transportTO.setTrainTO(trainTO);
			}//capturing Vehicle Details
			else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.ROAD_CODE))
			{
			VehicleTO vehicleTO = new VehicleTO();
			if(!StringUtil.isEmptyInteger(rhoOfcId)){
				vehicleTO.setRhoOfcId(rhoOfcId);
			}
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportNumber()[i])){
				vehicleTO.setRegNumber(pureRouteTO.getTransportNumber()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE});
			}
			if(!StringUtil.isStringEmpty(pureRouteTO.getTransportName()[i])){
				vehicleTO.setVehicleType(pureRouteTO.getTransportName()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE});
			}
			if(!StringUtil.isEmptyInteger(pureRouteTO.getTransporterId()[i])){
				vehicleTO.setVehicleId(pureRouteTO.getTransporterId()[i]);
			}
			transportTO.setVehicleTO(vehicleTO);
			}
			// capturing transport details
			if(!StringUtil.isEmptyInteger(pureRouteTO.getTransportId()[i])){
				transportTO.setTransportId(pureRouteTO.getTransportId()[i]);
				transportDO = convertTransportTO2TransportDO(transportTO, userId);
			}else{
				trTO = getTransportDetails(transportTO);
				if(StringUtil.isNull(trTO) || (!StringUtil.isNull(trTO) && StringUtil.isEmptyInteger(trTO.getTransportId()))){
					transportDO = convertTransportTO2TransportDO(transportTO, userId);
					saveTransport(transportDO);
					trTO = getTransportDetails(transportTO);
					transportDO = convertTransportTO2TransportDO(trTO, userId);
					}
				else{
					transportDO = convertTransportTO2TransportDO(trTO, userId);
				}
			}
				
			tripdo.setRouteDO(routeDO);
			tripdo.setTransportDO(transportDO);
			if(!StringUtil.isStringEmpty(pureRouteTO.getExpDepartureTime()[i])){
				tripdo.setDepartureTime(pureRouteTO.getExpDepartureTime()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DEPT_TIME} );
			}
			if(!StringUtil.isStringEmpty(pureRouteTO.getExpDepartureTime()[i])){
				tripdo.setArrivalTime(pureRouteTO.getExpArrivalTime()[i]);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.ARVL_TIME} );
			}
			tripdo.setActive(RouteServiceCommonConstants.FLAG_Y);
			if(!StringUtil.isEmptyInteger(pureRouteTO.getTripNumber()[i])){
				tripdo.setTripId(pureRouteTO.getTripNumber()[i]);	
			}
		    tripDOList.add(tripdo);
			}/*else{
				if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.AIR_CODE)){
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT} );
				}else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.TRAIN_CODE)){
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN} );
				}else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.ROAD_CODE)){
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE} );
				}
			}*/
		  }
		}else{
			if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.AIR_CODE)){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT} );
			}else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.TRAIN_CODE)){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN} );
			}else if((pureRouteTO.getTransportMode().split(CommonConstants.TILD)[1]).equals(RouteUniversalConstants.ROAD_CODE)){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE} );
			}	
		}
		
		LOGGER.debug("RouteServicedServiceImpl::pureRouteDomainConverter::END------------>:::::::");
		}
		
		
		/** 
	     * Load Trip details into TO object
	     * This method get the trip details using route object and transport mode object
	     * @inputparam  RouteTO Object, TransportModeTO object
	     * @return  	PureRouteTO
	     * @author      Rohini  Maladi  
	     */
	@Override
	public PureRouteTO getTripDetailsByRoute(RouteTO routeTO,TransportModeTO transportModeTO, Integer regionId) throws CGBusinessException, CGSystemException{

		LOGGER.debug("RouteServicedServiceImpl::getTripDetailsByRoute::START------------>:::::::");
		PureRouteTO pureRouteTO = null;
				
		TripTO trTO = new TripTO();
		TransportTO transportTO = new TransportTO();
		transportTO.setRegionId(regionId);
		transportTO.setTransportModeTO(transportModeTO);
		trTO.setRouteTO(routeTO);
		trTO.setTransportTO(transportTO);
		
		
		List<TripTO> tripTOList = routeServicedCommonService.getTripDetails(trTO);
		if(!(CGCollectionUtils.isEmpty(tripTOList))){
		int length = tripTOList.size();
		String number []= new String[length];
		String transportName []= new String[length];
		String airlineCode []= new String[length];
		String depTime []= new String[length];
		String arrTime []= new String[length];
		Integer tripNumber []= new Integer[length];
		Integer transportId []= new Integer[length];
		Integer transporterId []= new Integer[length];
		
		String flightNumber = null;
		String AirlineName = null;
		String arrivalTime = null;
		String departureTime = null;
		
		Set<String> FlightSet = new HashSet<String>();
		
		Set<String> TrainSet = new HashSet<String>();
		String trainNumber = null;
		String trainName = null;
		String trainArrivalTime = null;
		String trainDepartureTime = null;
		
		Set<String> vehicleSet = new HashSet<String>();
		String vehicleNumber = null;
		String VehicleType = null;
		String vehicleDeparture = null;
		String vehicleArrival = null;
		
		int i=0;
		for(TripTO tripTO: tripTOList){
			if( (StringUtil.isNull(tripTO.getActive())) || (!(StringUtil.isNull(tripTO.getActive())) && !tripTO.getActive().equals(RouteServiceCommonConstants.FLAG_N)))	{				
			
				if(tripTO.getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.AIR_CODE)){
					flightNumber = tripTO.getTransportTO().getFlightTO().getFlightNumber();
					AirlineName = tripTO.getTransportTO().getFlightTO().getAirlineName();
					arrivalTime = tripTO.getArrivalTime();
					departureTime = tripTO.getDepartureTime();
					
					// To avoid duplicate flight entries on pure route screen
					if(! FlightSet.contains(flightNumber+AirlineName+arrivalTime+departureTime)) {
						number[i] = tripTO.getTransportTO().getFlightTO().getFlightNumber();
						transporterId[i] = tripTO.getTransportTO().getFlightTO().getFlightId();
						if(!StringUtil.isStringEmpty(tripTO.getTransportTO().getFlightTO().getAirlineCode())){
							airlineCode[i]=tripTO.getTransportTO().getFlightTO().getAirlineCode();
						}else{
							airlineCode[i]="";
						}
						
						depTime[i]= tripTO.getDepartureTime();
						arrTime[i]= tripTO.getArrivalTime();
						tripNumber[i] = tripTO.getTripId();
						transportId[i] = tripTO.getTransportTO().getTransportId();
						
						FlightSet.add(flightNumber+AirlineName+arrivalTime+departureTime);
						 
						i++;
					}
				}
				
				if(tripTO.getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.TRAIN_CODE))
				{
					trainNumber = tripTO.getTransportTO().getTrainTO().getTrainNumber();
					trainName = tripTO.getTransportTO().getTrainTO().getTrainName();
					trainArrivalTime = tripTO.getArrivalTime();
					trainDepartureTime = tripTO.getDepartureTime();
					
					 if(! TrainSet.contains(trainNumber+trainName+trainArrivalTime+trainDepartureTime)) {
						number[i] = tripTO.getTransportTO().getTrainTO().getTrainNumber();
						transportName[i] = tripTO.getTransportTO().getTrainTO().getTrainName();
						transporterId[i] = tripTO.getTransportTO().getTrainTO().getTrainId();
						
						depTime[i]= tripTO.getDepartureTime();
						arrTime[i]= tripTO.getArrivalTime();
						tripNumber[i] = tripTO.getTripId();
						transportId[i] = tripTO.getTransportTO().getTransportId();
						
						TrainSet.add(trainNumber+trainName+trainArrivalTime+trainDepartureTime);
						 
						i++;				
					}
				}
				
				if(tripTO.getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE))
				{
					vehicleNumber = tripTO.getTransportTO().getVehicleTO().getRegNumber();
					VehicleType = tripTO.getTransportTO().getVehicleTO().getVehicleType();
					vehicleDeparture = tripTO.getDepartureTime();
					vehicleArrival = tripTO.getDepartureTime();
							
					if(! vehicleSet.contains(vehicleNumber+VehicleType+vehicleDeparture+vehicleArrival)) {
						number[i] = tripTO.getTransportTO().getVehicleTO().getRegNumber();
						transportName[i] = tripTO.getTransportTO().getVehicleTO().getVehicleType();
						transporterId[i] = tripTO.getTransportTO().getVehicleTO().getVehicleId();
						
						depTime[i]= tripTO.getDepartureTime();
						arrTime[i]= tripTO.getArrivalTime();
						tripNumber[i] = tripTO.getTripId();
						transportId[i] = tripTO.getTransportTO().getTransportId();
						
						vehicleSet.add(vehicleNumber+VehicleType+vehicleDeparture+vehicleArrival);
						 
						i++;		
					}
				}	
			}
		}
		
		
		//j = --i;
		if(i != 0){
		pureRouteTO = new PureRouteTO();		
		pureRouteTO.setOriginStaionId(tripTOList.get(0).getRouteTO().getOriginCityTO().getCityId());
		pureRouteTO.setDestinationStaitonId(tripTOList.get(0).getRouteTO().getDestCityTO().getCityId());
		pureRouteTO.setRouteId(tripTOList.get(0).getRouteTO().getRouteId());
		//pureRouteTO.settra(tripTOList.get(0).getTransportTO().getTransportModeTO().getTransportModeId());
		pureRouteTO.setTransportName(transportName);
		pureRouteTO.setTransportNumber(number);
		pureRouteTO.setExpDepartureTime(depTime);
		pureRouteTO.setExpArrivalTime(arrTime);
		pureRouteTO.setRowCount(length);
		pureRouteTO.setTripNumber(tripNumber);
		pureRouteTO.setTransportId(transportId);
		pureRouteTO.setTransporterId(transporterId);
		pureRouteTO.setAirlineCode(airlineCode);
		}
		}
		LOGGER.debug("RouteServicedServiceImpl::getTripDetailsByRoute::END------------>:::::::");
		return pureRouteTO;
	
	}
	
	/** 
     * Load Trasnport details into Transport object
     * This method get the transport details using transportTO object
     * @inputparam  TransportTO Object
     * @return  	TransportTO
     * @author      Rohini  Maladi  
     */
	@Override
	public TransportTO getTransport(TransportTO transportTO) throws CGBusinessException, CGSystemException{
		
		LOGGER.debug("RouteServicedServiceImpl::getTransport::START------------>:::::::");
		TransportTO trTO = null;
		
		if(!StringUtil.isNull(transportTO))
		trTO = getTransportDetails(transportTO);
		
		if(StringUtil.isNull(trTO)){
			if(!StringUtil.isStringEmpty(transportTO.getTransportModeTO().getTransportModeCode())){
				String mode = transportTO.getTransportModeTO().getTransportModeCode();
				trTO = new TransportTO();
				if(mode.equals(RouteUniversalConstants.AIR_CODE)){
					if(!StringUtil.isNull(transportTO.getFlightTO())){
						FlightTO flightTO = getFlightDetails(transportTO.getFlightTO());
						if(!StringUtil.isNull(flightTO)){
							trTO.setFlightTO(flightTO);
						}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT} );
					}
				}else if(mode.equals(RouteUniversalConstants.ROAD_CODE)){
					if(!StringUtil.isNull(transportTO.getVehicleTO())){
						VehicleTO vehicleTO = getVehicleDetails(transportTO.getVehicleTO());
						if(!StringUtil.isNull(vehicleTO)){
							trTO.setVehicleTO(vehicleTO);
						}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE} );
					}
				}else if(transportTO != null &&  mode.equals(RouteUniversalConstants.TRAIN_CODE)){
					if(!StringUtil.isNull(transportTO.getTrainTO())){
						TrainTO trainTO = getTrainDetails(transportTO.getTrainTO());
						if(!StringUtil.isNull(trainTO)){
							trTO.setTrainTO(trainTO);
						}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN} );
					}
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE} );
			}
		}
		LOGGER.debug("RouteServicedServiceImpl::getTransport::END------------>:::::::");
		return trTO;
		
	}
	
	/** 
     * List the all cities with the region     * 
     * @inputparam  RegionTO object
     * @return  	List<CityTO>
     * @author      Rohini  Maladi  
     */
	@Override
	public List<CityTO> getStationsByRegion(RegionTO regionTO) throws CGBusinessException, CGSystemException{
			LOGGER.debug("RouteServicedServiceImpl::getStationsByRegion::START------------>:::::::");
			List<CityTO> cityTOList = null;
		
			CityTO cityTO = new CityTO();
			cityTO.setRegion(regionTO.getRegionId());
			cityTOList = routeServicedCommonService.getCitiesByCity(cityTO);
			LOGGER.debug("RouteServicedServiceImpl::getStationsByRegion::END------------>:::::::");
			return cityTOList;
	}
	
	/** 
     * List the Flight Details
     * @inputparam  FlightTO object
     * @return  	FlightTO
     * @author      Rohini  Maladi  
     */
	
	public FlightTO getFlightDetails(FlightTO flightTO) throws CGBusinessException, CGSystemException{
		
		List<FlightTO> flightTOList = routeServicedCommonService.getFlightDetails(flightTO);
		
		if(CGCollectionUtils.isEmpty(flightTOList))
			return null;
		
		
		return flightTOList.get(0);
		
	}
	
	/** 
     * List the Train Details
     * @inputparam  TrainTO object
     * @return  	TrainTO
     * @author      Rohini  Maladi  
     */
	public TrainTO getTrainDetails(TrainTO trainTO)  throws CGBusinessException, CGSystemException{
		
		List<TrainTO> trainTOList = routeServicedCommonService.getTrainDetails(trainTO);
		
		if(CGCollectionUtils.isEmpty(trainTOList))
			return null;
		
		
		return trainTOList.get(0);
		
	}
	
	/** 
     * List the Vehicle Details
     * @inputparam  VehicleTO object
     * @return  	VehicleTO
     * @author      Rohini  Maladi  
     */
	public VehicleTO getVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException{
		
		List<VehicleTO> vehicleTOList = routeServicedCommonService.getVehicleDetails(vehicleTO);
		
		if(CGCollectionUtils.isEmpty(vehicleTOList))
			return null;
		
		
		return vehicleTOList.get(0);
	}

	/** 
     * List the Transport Details
     * @inputparam  TransportTO object
     * @return  	TransportTO
     * @author      Rohini  Maladi  
     */
	public TransportTO getTransportDetails(TransportTO transportTO) throws CGBusinessException, CGSystemException{
		
		List<TransportTO> transportTOList = null;
		
		transportTOList = routeServicedCommonService.getTransportDetails(transportTO);
		
		if(CGCollectionUtils.isEmpty(transportTOList))
			return null;
		
		return transportTOList.get(0);
	}

	/** 
     * Save Transport Object
     * This method save the transport details in database
     * @inputparam  TransportDO Object
     * @return  	
     * @author      Rohini  Maladi  
     */
	private void saveTransport(TransportDO transportDO) throws CGSystemException{
		
		routeServicedDAO.saveTransport(transportDO);
		
	}

	/** 
     * Save Route Object
     * This method save the Route details in database
     * @inputparam  RouteDO Object
     * @return  	
     * @author      Rohini  Maladi  
     */
	private void saveRoute(RouteDO routeDO) throws CGSystemException{
		routeServicedDAO.saveRoute(routeDO);
	}

	/** 
     * Save Transshipment Route
     * This method save the transshipment route details in database
     * @inputparam  TransshipmentRouteTO Object
     * @return  	
     * @author      Rohini  Maladi  
     */
	
	public String saveTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO) throws CGBusinessException, CGSystemException{
		LOGGER.debug("RouteServicedServiceImpl::saveTransshipmentRoute::START------------>:::::::");
		boolean transshipmentStatus = Boolean.FALSE;
		String status = CommonConstants.FAILURE;
		Integer[] transshipmentIdsArr = null;
		
		List<TransshipmentRouteDO> transshipmentRouteDOList = new ArrayList<TransshipmentRouteDO>();
		transshipmentRouteDomainConverter(transshipmentRouteTO, transshipmentRouteDOList);
		
		String transshipmentIdsStr = transshipmentRouteTO.getTransshipmentIdsArrStr();
		if(!StringUtil.isStringEmpty(transshipmentIdsStr)){
			String[] transshipmentIdsStrArr = transshipmentIdsStr.split(CommonConstants.COMMA);
			int arrLen = transshipmentIdsStrArr.length;
			transshipmentIdsArr = new Integer[arrLen];
			for(int i = 0;i<arrLen;i++)
				transshipmentIdsArr[i] = StringUtil.parseInteger(transshipmentIdsStrArr[i]);
		}
		if(!CGCollectionUtils.isEmpty(transshipmentRouteDOList)){		
			transshipmentStatus = routeServicedDAO.saveTransshipmentRoute(transshipmentRouteDOList,transshipmentIdsArr);
		}else{
			transshipmentStatus = routeServicedDAO.saveTransshipmentRoute(null,transshipmentIdsArr);
		}
		if(transshipmentStatus){
		status = CommonConstants.SUCCESS;
		}
		LOGGER.debug("RouteServicedServiceImpl::saveTransshipmentRoute::END------------>:::::::");
		return status;
	}
	
	
	/** 
     * Convert TO object to DO Object
     * This method Converts TransshipmentRouteTO object to TransshipmentDO object and provides List of TripDO
     * @inputparam  TransshipmentRouteTO Object
     * @return  	List<TransshipmentRouteDO>
     * @author      Rohini  Maladi  
     */
	
		private void transshipmentRouteDomainConverter(TransshipmentRouteTO transshipmentRouteTO,
			List<TransshipmentRouteDO> transshipmentRouteDOList) throws CGBusinessException, CGSystemException {		
			LOGGER.debug("RouteServicedServiceImpl::transshipmentRouteDomainConverter::START------------>:::::::");
			TransshipmentRouteDO transshipmentRouteDO = null;
			int len = transshipmentRouteTO.getServicedCityIds().length;
			if(len > 0){
				for(int i=0; i<len;i++){
					if(!StringUtil.isEmptyInteger(transshipmentRouteTO.getServicedCityIds()[i])){	
						transshipmentRouteDO = new TransshipmentRouteDO();
						if(!StringUtil.isEmptyInteger(transshipmentRouteTO.getTransshipmentCityId())){
							transshipmentRouteDO.setTransshipmentCityId(transshipmentRouteTO.getTransshipmentCityId());
						}else{
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRANSPMENT_ROUTE});
						}
						transshipmentRouteDO.setServicedCityId(transshipmentRouteTO.getServicedCityIds()[i]);
						if(!StringUtil.isEmptyInteger(transshipmentRouteTO.getTransshipmentNumber()[i]))
							transshipmentRouteDO.setTransshipmentRouteId(transshipmentRouteTO.getTransshipmentNumber()[i]);
						transshipmentRouteDO.setActive(RouteServiceCommonConstants.FLAG_Y);
						transshipmentRouteDOList.add(transshipmentRouteDO);
					}
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRANSPMENT_ROUTE});
			}
			LOGGER.debug("RouteServicedServiceImpl::transshipmentRouteDomainConverter::END------------>:::::::");
		}

		/** 
	     * Transshipment Route details into TO object
	     * This method get the transshipmetn route details using transshipment route object
	     * @inputparam  TransshipmentRouteTO Object
	     * @return  	TransshipmentRouteTO
	     * @author      Rohini  Maladi  
	     */
	
			
		@Override	
	public List<TransshipmentRouteTO> getTransshipmentRouteDetails(TransshipmentRouteTO transshipmentRouteTO) throws CGBusinessException, CGSystemException{
		LOGGER.debug("RouteServicedServiceImpl::getTransshipmentRouteDetails::START------------>:::::::");
		List<TransshipmentRouteTO> transshipmentRouteTOList = null;
		List<TransshipmentRouteTO> transshipRouteTOList = null;
		
		transshipmentRouteTOList = routeServicedCommonService.getAllTransshipmentRoute(transshipmentRouteTO);
		List<CityTO> cityTOList = new ArrayList<CityTO>();
		RegionTO regionTO = null;		
		transshipRouteTOList = new ArrayList<TransshipmentRouteTO>(); 
		if(!CGCollectionUtils.isEmpty(transshipmentRouteTOList)){
			for(TransshipmentRouteTO transRouteTO:transshipmentRouteTOList){
			CityTO cityTO = new CityTO();
			cityTO.setCityId(transRouteTO.getServicedCityId());
			cityTOList.add(cityTO);
			}
			cityTOList = routeServicedCommonService.getAllCitiesByCityIds(cityTOList);
			if(!CGCollectionUtils.isEmpty(cityTOList)){
				Integer[] region = new Integer[cityTOList.size()];
				int j=0;
				int size = 0;
				Map<Integer, Integer> map = new HashMap<Integer,Integer>();
				boolean regExist = Boolean.FALSE;
				List<CityTO> cityTOs = new ArrayList<CityTO>();
			for(TransshipmentRouteTO transshipRouteTO:transshipmentRouteTOList){
				if( (StringUtil.isNull(transshipRouteTO.getActive())) || (!(StringUtil.isNull(transshipRouteTO.getActive())) 
						&& !transshipRouteTO.getActive().equals(RouteServiceCommonConstants.FLAG_N)))	{
			for(CityTO cityto:cityTOList){
				regExist = Boolean.FALSE;
				if(cityto.getCityId().equals(transshipRouteTO.getServicedCityId())){
					transshipmentRouteTO = new TransshipmentRouteTO();
					transshipmentRouteTO.setServicedRegionId(cityto.getRegion());
					transshipmentRouteTO.setServicedCityId(transshipRouteTO.getServicedCityId());
					transshipmentRouteTO.setTransshipmentRouteId(transshipRouteTO.getTransshipmentRouteId());										
						for(int i=0;i<j;i++){
							if(region[i] != null && region[i].equals(cityto.getRegion())){
								transshipmentRouteTO.setServicedCityList(transshipRouteTOList.get(map.get(region[i])).getServicedCityList());
								regExist = Boolean.TRUE;
								break;
							}
						}
					if(!regExist){
					region[j] = cityto.getRegion();
					map.put(region[j], size);
					j++;
					regionTO = new RegionTO();
					regionTO.setRegionId(cityto.getRegion());
					cityTOs = getStationsByRegion(regionTO);
					transshipmentRouteTO.setServicedCityList(cityTOs);
					}
					}
					}
				size++;
				transshipRouteTOList.add(transshipmentRouteTO);
				}
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DB_ISSUE);
			}
		}
		LOGGER.debug("RouteServicedServiceImpl::getTransshipmentRouteDetails::END------------>:::::::");	
		return transshipRouteTOList;
	}


		/** 
	     * convert TransportTO into TransportDO object
	     * This method convert the TransportTO object to TransportDO object
	     * @inputparam  TransportTO Object
	     * @return  	TransportDo
	     * @author      Rohini  Maladi  
	     */
		public TransportDO convertTransportTO2TransportDO(TransportTO transportTO, int userId) throws CGBusinessException, CGSystemException{
			LOGGER.debug("RouteServicedServiceImpl::convertTransportTO2TransportDO::START------------>:::::::");
			TransportDO transportDO = null;
			
				transportDO = new TransportDO();
			CGObjectConverter.createDomainFromTo(
					transportTO, 
					transportDO);
		
				if(!StringUtil.isNull(transportTO.getTransportModeTO())){
					TransportModeDO transportModeDO = new TransportModeDO();
					CGObjectConverter.createDomainFromTo(
							transportTO.getTransportModeTO(), 
							transportModeDO);
					transportDO.setTransportModeDO(transportModeDO);
				}else{
					
				}
				if(!StringUtil.isNull(transportTO.getFlightTO())){
					FlightDO flightDO = new FlightDO();
					CGObjectConverter.createDomainFromTo(
							transportTO.getFlightTO(), 
							flightDO);
					transportDO.setFlightDO(flightDO);
				}/*else{
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT} );
				}*/
				if(!StringUtil.isNull(transportTO.getTrainTO())){
					TrainDO trainDO = new TrainDO();
					CGObjectConverter.createDomainFromTo(
							transportTO.getTrainTO(), 
							trainDO);
					transportDO.setTrainDO(trainDO);
				}/*else{
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN} );
				}*/
				if(!StringUtil.isNull(transportTO.getVehicleTO())){
					VehicleDO vehicleDO = new VehicleDO();
					CGObjectConverter.createDomainFromTo(
							transportTO.getVehicleTO(), 
							vehicleDO);
					OfficeDO rhoOfcDO = new OfficeDO();
					rhoOfcDO.setOfficeId(transportTO.getVehicleTO().getRhoOfcId());
					vehicleDO.setRegionalOfficeDO(rhoOfcDO);
					
					vehicleDO.setUpdatedBy(userId);
					vehicleDO.setUpdatedDate(Calendar.getInstance().getTime());		
					
					transportDO.setVehicleDO(vehicleDO);
				}/*else{
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE} );
				}*/
				LOGGER.debug("RouteServicedServiceImpl::convertTransportTO2TransportDO::END------------>:::::::");
				return transportDO;
			
		}
		
		/** 
	     * Load serviceByType details into List object
	     * This method get the serviceByType details using transportmode
	     * @inputparam  TransportModeId
	     * @return  	List
	     * @author      Rohini  Maladi  
	     */
		@Override
		public List<LabelValueBean> getServiceBytypeByTransportModeId(
				Integer transportModeId) throws CGBusinessException,
				CGSystemException {
			return routeServicedCommonService.getServiceByTypeListByTransportModeId(transportModeId);
		}

		/** 
	     * Load TripServicedBy details into TripServicedBy List object	     
	     * @inputparam  <li> OrigincityId
	     * 				<li> DestinationcityId
	     * 				<li> transportModeId
	     * 				<li> serviceByTypeId
	     * 				<li> effectiveFrom
	     * 				<li> effectiveTo 
	     * @return  	TripServicedByTO List
	     * @author      Rohini  Maladi  
		 * @param serviceByTypeCode 
		 * @param regionId 
		 * @param transportModeCode 
	     */
		@Override
		public List<TripServicedByTO> getTripServicedByDetails(
				Integer originCityId, Integer destinationCityId,
				Integer transportModeId, Integer serviceByTypeId, String serviceByTypeCode,
				String effectiveFrom, String effectiveTo, String transportModeCode, Integer regionId, String screenName)
				throws CGBusinessException, CGSystemException {
			LOGGER.debug("RouteServicedServiceImpl::getTripServicedByDetails::START------------>:::::::");
			List<TripServicedByTO> tripServicedByTOList = null;
			
			RouteTO routeTO = new RouteTO();
			
			Integer routeId = null;
			routeId = routeServicedCommonService.getRouteIdByOriginCityIdAndDestCityId(originCityId,destinationCityId);
			
			
			if(!StringUtil.isNull(routeId))
			routeTO.setRouteId(routeId);		
			
			TransportModeTO transportModeTO = new TransportModeTO();
				
			transportModeTO.setTransportModeId(transportModeId);
			transportModeTO.setTransportModeCode(transportModeCode);

			ServiceByTypeTO serviceByTypeTO = new ServiceByTypeTO();
			
			serviceByTypeTO.setServiceByTypeId(serviceByTypeId);
			serviceByTypeTO.setTransportModeTO(transportModeTO);
			serviceByTypeTO.setServiceByTypeCode(serviceByTypeCode);
			
			ServicedByTO servicedByTO = new ServicedByTO();
			servicedByTO.setServiceByTypeTO(serviceByTypeTO);	
			
			
			TripTO tripTO = new TripTO();
			tripTO.setRouteTO(routeTO);
			
			TransportTO transportTO = new TransportTO();
			transportTO.setRegionId(regionId);
			tripTO.setTransportTO(transportTO);
			TripServicedByTO tripServicedByTO = new TripServicedByTO();
			tripServicedByTO.setTransportModeTO(transportModeTO);
			tripServicedByTO.setTripTO(tripTO);
			
			tripServicedByTO.setServicedByTO(servicedByTO);
			
			tripServicedByTO.setEffectiveFromStr(effectiveFrom);
			tripServicedByTO.setEffectiveToStr(effectiveTo);
			
			
			 
			//List<TripServicedByTO> tripServicedByTOList = routeServicedCommonService.getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
				//	routeId, transportModeId, serviceByTypeId);
			tripServicedByTOList = routeServicedCommonService.getTripServicedByDOListByTripServicedBy(tripServicedByTO, screenName);		
			LOGGER.debug("RouteServicedServiceImpl::getTripServicedByDetails::END------------>:::::::");
			return tripServicedByTOList;
		}

		/** 
	     * Load LoadMovementVendor details into LoadMovementVendorTO List object	     
	     * @inputparam  ServiceByTypeCode 
	     * @return  	LoadMovementVendorTO List
	     * @author      Rohini  Maladi  
	     */
		@Override
		public List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(
				String serviceByTypeCode, Integer cityId)
				throws CGBusinessException, CGSystemException {
		
			//LoadMovementVendorTO loadMovementVendorTO = new LoadMovementVendorTO();
			//loadMovementVendorTO.setVendorType(serviceByTypeCode);

			//return routeServicedCommonService.getVendorsList(loadMovementVendorTO);
			return routeServicedCommonService.getVendorsListByServiceTypeAndCity(serviceByTypeCode,cityId);
		}
		
		/** 
	     * Load Trip details into TripTO List object	     
	     * @inputparam  RouteTO object, TransportModeTO object 
	     * @return  	Trip List
	     * @author      Rohini  Maladi  
	     */	
		@Override
		public List<TripTO> getTripDetails(RouteTO routeTO,TransportModeTO transportModeTO) throws CGBusinessException, CGSystemException{

			LOGGER.debug("RouteServicedServiceImpl::getTripDetails::START------------>:::::::");
			TripTO trTO = new TripTO();
			TransportTO transportTO = new TransportTO();
			transportTO.setTransportModeTO(transportModeTO);
			trTO.setRouteTO(routeTO);
			trTO.setTransportTO(transportTO);
			
			LOGGER.debug("RouteServicedServiceImpl::getTripDetails::END------------>:::::::");
			return routeServicedCommonService.getTripDetails(trTO);
		}

		/** 
	     * Store the ServicedBy object into Database	     
	     * @inputparam  TripServicedByTO object 
	     * @return  	
	     * @author      Rohini  Maladi  
	     */
		@Override
		public String saveTripServicedBy(TripServicedByTO tripServicedByTO)
				throws CGBusinessException, CGSystemException {
			LOGGER.debug("RouteServicedServiceImpl::saveTripServicedBy::START------------>:::::::");
			boolean tripServicedByStatus = Boolean.FALSE;
			String status = CommonConstants.FAILURE;
			Integer[] tripServicedByIdsArr = null;
			
			if(!StringUtil.isNull(tripServicedByTO)){
			
				List<TripServicedByDO> tripServicedByDOList = new ArrayList<TripServicedByDO>();				
				tripServicedByDomainConverter(tripServicedByTO, tripServicedByDOList);
				String tripServicedIdsStr = tripServicedByTO.getTripServicedIdsArrStr();
				if(!StringUtil.isStringEmpty(tripServicedIdsStr)){
					String[] tripIdsStrArr = tripServicedIdsStr.split(CommonConstants.COMMA);
					int arrLen = tripIdsStrArr.length;
					tripServicedByIdsArr = new Integer[arrLen];
					for(int i = 0;i<arrLen;i++)
						tripServicedByIdsArr[i] = StringUtil.parseInteger(tripIdsStrArr[i]);
				}
				
				if(!CGCollectionUtils.isEmpty(tripServicedByDOList)){
					tripServicedByStatus = routeServicedDAO.saveTripServicedBy(tripServicedByDOList,tripServicedByIdsArr);
				}else{	
					tripServicedByStatus = routeServicedDAO.saveTripServicedBy(null,tripServicedByIdsArr);
				}
				if(tripServicedByStatus){
					status = CommonConstants.SUCCESS;
				}
			}
			LOGGER.debug("RouteServicedServiceImpl::saveTripServicedBy::END------------>:::::::");		
			return status;		
		}
		
		
		/** 
	     * Convert TripServicedByTO object to TripServicedByDO List object	     
	     * @inputparam  TripServicedByTO object 
	     * @return  	TripServicedByDO List
	     * @author      Rohini  Maladi  
	     */
		private void tripServicedByDomainConverter(TripServicedByTO tripServicedByTO, List<TripServicedByDO> tripServicedByDOList) throws CGBusinessException, CGSystemException {		

			LOGGER.debug("RouteServicedServiceImpl::tripServicedByDomainConverter::START------------>:::::::");
			TripServicedByDO tripServicedByDO = null;
			TransportModeDO transportModeDO = new TransportModeDO();
			TripDO tripDO = null;
			ServicedByDO servicedByDO = null;
			ServiceByTypeTO serviceByTypeTO = null;
			LoadMovementVendorTO loadMovementVendorTO = null;			
			ServicedByTO servicedByTO = null;
			TransportModeTO transportModeTO = null;
			int transportModeId;
			
			if(!StringUtil.isStringEmpty(tripServicedByTO.getTransportMode())){
				if(!StringUtil.isStringEmpty(tripServicedByTO.getTransportMode().split(CommonConstants.TILD)[0])){
					transportModeId = Integer.parseInt(tripServicedByTO.getTransportMode().split(CommonConstants.TILD)[0]);
					transportModeDO.setTransportModeId(transportModeId);
				
			int len = tripServicedByTO.getTripNumber().length;
			String serviceType = tripServicedByTO.getServiceByType().split(CommonConstants.TILD)[1];
			if(len > 0){
			for(int i=0;i<len;i++)
			{
				if ((serviceType.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT) 
						&& !StringUtil.isEmptyInteger(tripServicedByTO.getTripNumber()[i]))
						|| (!serviceType.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT) 
						&& !StringUtil.isEmptyInteger(tripServicedByTO.getVendorNumber()[i]))) {
					
				tripServicedByDO = new TripServicedByDO();
				
				tripServicedByDO.setTransportModeDO(transportModeDO);
				
				tripDO = new TripDO();
				if(!StringUtil.isNull(tripServicedByTO.getTripNumber()) && !StringUtil.isEmptyInteger(tripServicedByTO.getTripNumber()[i])){
				tripDO.setTripId(tripServicedByTO.getTripNumber()[i]);
				
				tripServicedByDO.setTripDO(tripDO);
				
				
				
				servicedByDO = new ServicedByDO();
				
				if(!StringUtil.isEmptyInteger(tripServicedByTO.getServicedByNumber()[i])){
				
					servicedByDO.setServicedById(tripServicedByTO.getServicedByNumber()[i]);
				
				}else{
					servicedByTO = new ServicedByTO();
					serviceByTypeTO = new ServiceByTypeTO();
					transportModeTO = new TransportModeTO();
					transportModeTO.setTransportModeId(transportModeId);
					transportModeTO.setTransportModeCode(tripServicedByTO.getTransportMode().split(CommonConstants.TILD)[1]);
					if(!StringUtil.isStringEmpty(tripServicedByTO.getServiceByType()) 
							&& !StringUtil.isStringEmpty(tripServicedByTO.getServiceByType().split(CommonConstants.TILD)[1])){
					if(tripServicedByTO.getServiceByType().split(CommonConstants.TILD)[1].equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT)){
						/*employeeTO = new EmployeeTO();
						employeeTO.setEmployeeId(tripServicedByTO.getVendorNumber()[i]);
						servicedByTO.setEmployeeTO(employeeTO);*/
						servicedByTO.setServicedByType(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_EMPLOYEE);
					}else {
						loadMovementVendorTO = new LoadMovementVendorTO();
						loadMovementVendorTO.setVendorId(tripServicedByTO.getVendorNumber()[i]);
						servicedByTO.setLoadMovementVendorTO(loadMovementVendorTO);
						servicedByTO.setServicedByType(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_VENDOR);
					} 
					
					serviceByTypeTO.setTransportModeTO(transportModeTO);
					if(!StringUtil.isStringEmpty(tripServicedByTO.getServiceByType().split(CommonConstants.TILD)[0])){
						serviceByTypeTO.setServiceByTypeId(Integer.parseInt(tripServicedByTO.getServiceByType().split(CommonConstants.TILD)[0]));
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE} );
					}
					servicedByTO.setServiceByTypeTO(serviceByTypeTO);
					
					ServicedByTO sTO= getServicedBy(servicedByTO);
					if(StringUtil.isNull(sTO)){
						servicedByDO = convertServicedByTO2DO(servicedByTO);
						saveServicedBy(servicedByDO);
						servicedByTO = getServicedBy(servicedByTO);
						servicedByDO = convertServicedByTO2DO(servicedByTO);
					}else{
						servicedByDO = convertServicedByTO2DO(sTO);
					}
					
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE} );
					}
				}
				
				tripServicedByDO.setServicedByDO(servicedByDO);
				
				tripServicedByDO.setEffectiveFrom(DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()));
				tripServicedByDO.setEffectiveTo(DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr()));
				if(!StringUtil.isEmptyInteger(tripServicedByTO.getTripServicedByNumber()[i]))
					tripServicedByDO.setTripServicedById(tripServicedByTO.getTripServicedByNumber()[i]);
				tripServicedByDO.setOperationDays(tripServicedByTO.getOperationDaysArr()[i]);
				tripServicedByDO.setAllDays(tripServicedByTO.getAllDaysArr()[i]);
				tripServicedByDO.setActive(RouteServiceCommonConstants.FLAG_Y);
				tripServicedByDOList.add(tripServicedByDO);
				}
				}/*else{
					prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRIP} );
				}*/
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRIP_SERVICED} );
			}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE} );
			}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE} );
			}
			LOGGER.debug("RouteServicedServiceImpl::tripServicedByDomainConverter::END------------>:::::::");
		}
		
		/** 
	     * Load servicedBy details into ServicedByTO object	     
	     * @inputparam  ServicedByTO object 
	     * @return  	ServicedByTO
	     * @author      Rohini  Maladi  
	     */
		public ServicedByTO getServicedBy(
				ServicedByTO servicedByTO) throws CGBusinessException, CGSystemException {
			LOGGER.debug("RouteServicedServiceImpl::getServicedBy::START------------>:::::::");
			List<ServicedByTO> servicedByTOList = routeServicedCommonService.getServicedByListByServicedBy(servicedByTO);
			
			if(CGCollectionUtils.isEmpty(servicedByTOList))
				return null;
			LOGGER.debug("RouteServicedServiceImpl::getServicedBy::END------------>:::::::");
			return servicedByTOList.get(0); 
		}
		
		/** 
	     * Convert ServicedByTO object to ServicedByDO List object	     
	     * @inputparam  ServicedByTO object 
	     * @return  	ServicedByDO
	     * @author      Rohini  Maladi  
	     */
		public ServicedByDO convertServicedByTO2DO(
				ServicedByTO servicedByTO) throws CGBusinessException, CGSystemException {
			LOGGER.debug("RouteServicedServiceImpl::convertServicedByTO2DO::START------------>:::::::");
			ServicedByDO servicedByDO = null;
			
			servicedByDO = new ServicedByDO();
			if(!StringUtil.isNull(servicedByTO)){
				CGObjectConverter.createDomainFromTo(servicedByTO, servicedByDO);
				
				if(!StringUtil.isNull(servicedByTO.getLoadMovementVendorTO())){
				LoadMovementVendorDO loadMovementVendorDO = new LoadMovementVendorDO();
				CGObjectConverter.createDomainFromTo(servicedByTO.getLoadMovementVendorTO(), loadMovementVendorDO);
				servicedByDO.setLoadMovementVendorDO(loadMovementVendorDO);
				}
				if(!StringUtil.isNull(servicedByTO.getEmployeeTO())){
					EmployeeDO employeeDO = new EmployeeDO();
					CGObjectConverter.createDomainFromTo(servicedByTO.getEmployeeTO(),employeeDO);
					servicedByDO.setEmployeeDO(employeeDO);
				}
				if(!StringUtil.isNull(servicedByTO.getServiceByTypeTO())){
					ServiceByTypeDO serviceByTypeDO = new ServiceByTypeDO();
					CGObjectConverter.createDomainFromTo(servicedByTO.getServiceByTypeTO(),serviceByTypeDO);					
					if(!StringUtil.isNull(servicedByTO.getServiceByTypeTO().getTransportModeTO())){
						TransportModeDO transportModeDO = new TransportModeDO();
						CGObjectConverter.createDomainFromTo(servicedByTO.getServiceByTypeTO().getTransportModeTO(),transportModeDO);
						serviceByTypeDO.setTransportModeDO(transportModeDO);
					}
					servicedByDO.setServiceByTypeDO(serviceByTypeDO);
				}
			}
			LOGGER.debug("RouteServicedServiceImpl::convertServicedByTO2DO::END------------>:::::::");
			return servicedByDO; 
		}
		
		public void saveServicedBy(ServicedByDO servicedByDO) throws CGBusinessException, CGSystemException{
			
			routeServicedDAO.saveServicedBy(servicedByDO);
		}
		
		/** 
	     * Convert TripDO List to TripTO List object	     
	     * @inputparam  TripDO List 
	     * @return  	TripTO List
	     * @author      Rohini  Maladi  
	     */
		public List<TripTO> convertTripDOList2TripTOList(List<TripDO> tripDOList) throws CGBusinessException, CGSystemException{
			LOGGER.debug("RouteServicedServiceImpl::convertTripDOList2TripTOList::START------------>:::::::");
			List<TripTO> tripTOList = new ArrayList<TripTO>();
			for (TripDO tripDO : tripDOList) {
				TripTO tripTO = TripDO2TripTOconverter(tripDO);
				tripTOList.add(tripTO);
			}
			LOGGER.debug("RouteServicedServiceImpl::convertTripDOList2TripTOList::END------------>:::::::");
			return tripTOList;
		}
		
		/** 
	     * Convert TripDO object to TripTO	     
	     * @inputparam  TripDO object 
	     * @return  	TripTO List
	     * @author      Rohini  Maladi  
	     */
		private TripTO TripDO2TripTOconverter(TripDO tripDO) throws CGBusinessException{
			LOGGER.debug("RouteServicedServiceImpl::TripDO2TripTOconverter::START------------>:::::::");
			TripTO tripTO = null;
			
			tripTO = new TripTO();
			RouteTO routeTO = null;
			TransportTO transportTO = null;
			PortTO originPortTO = null;
			PortTO destPortTO = null;
			
			
			CGObjectConverter.createToFromDomain(
					tripDO, tripTO);
			
			if(!StringUtil.isNull(tripDO.getRouteDO())){
				routeTO = new RouteTO();
				CGObjectConverter.createToFromDomain(
						tripDO.getRouteDO(), routeTO);
				
				if(!StringUtil.isNull(tripDO.getRouteDO().getOriginCityDO())){
					CityTO originCityTO = new CityTO();
					CGObjectConverter.createToFromDomain(
							tripDO.getRouteDO().getOriginCityDO(), 
							originCityTO);
					routeTO.setOriginCityTO(originCityTO);
				}
				if(!StringUtil.isNull(tripDO.getRouteDO().getDestCityDO())){
					CityTO destCityTO = new CityTO();
					CGObjectConverter.createToFromDomain(
							tripDO.getRouteDO().getDestCityDO(), 
							destCityTO);
					routeTO.setDestCityTO(destCityTO);
				}
				tripTO.setRouteTO(routeTO);
			}

			if(!StringUtil.isNull(tripDO.getTransportDO())){
				transportTO = new TransportTO();
				transportTO = converterTransportDO2TransportTO(tripDO.getTransportDO());
				tripTO.setTransportTO(transportTO);
			}
			
			if(!StringUtil.isNull(tripDO.getOriginPortDO())){
				originPortTO = new PortTO();
				CGObjectConverter.createToFromDomain(
						tripDO.getOriginPortDO(), originPortTO);
				tripTO.setOriginPortTO(originPortTO);
			}
			if(!StringUtil.isNull(tripDO.getDestPortDO())){
				destPortTO = new PortTO();
				CGObjectConverter.createToFromDomain(
						tripDO.getDestPortDO(), destPortTO);
				tripTO.setDestPortTO(destPortTO);
			}

			
			if(!StringUtil.isStringEmpty(tripDO.getArrivalTime())){
			tripTO.setArrivalTime(tripDO.getArrivalTime());
			}
			if(!StringUtil.isStringEmpty(tripDO.getDepartureTime())){
			tripTO.setDepartureTime(tripDO.getDepartureTime());
			}
			LOGGER.debug("RouteServicedServiceImpl::TripDO2TripTOconverter::END------------>:::::::");
			return tripTO;
		
		}

		/** 
	     * Convert TransportDO object to TransportTO object	     
	     * @inputparam  TransportDO object 
	     * @return  	TransportTO List
	     * @author      Rohini  Maladi  
	     */
		private TransportTO converterTransportDO2TransportTO(TransportDO transportDO) throws CGBusinessException{
			LOGGER.debug("RouteServicedServiceImpl::converterTransportDO2TransportTO::START------------>:::::::");
			TransportTO transportTO  = null;
			
				transportTO = new TransportTO();
			
				CGObjectConverter.createToFromDomain(
						transportDO, 
						transportTO);
				if(!StringUtil.isNull(transportDO.getTransportModeDO())){
					TransportModeTO transportModeTO = new TransportModeTO();
					CGObjectConverter.createToFromDomain(
							transportDO.getTransportModeDO(), 
							transportModeTO);
					transportTO.setTransportModeTO(transportModeTO);
				}
				if(!StringUtil.isNull(transportDO.getFlightDO())){
					FlightTO flightTO = new FlightTO();
					CGObjectConverter.createToFromDomain(
							transportDO.getFlightDO(), 
							flightTO);
					transportTO.setFlightTO(flightTO);
				}
				if(!StringUtil.isNull(transportDO.getTrainDO())){
					TrainTO trainTO = new TrainTO();
					CGObjectConverter.createToFromDomain(
							transportDO.getTrainDO(), 
							trainTO);
					transportTO.setTrainTO(trainTO);
				}
				if(!StringUtil.isNull(transportDO.getVehicleDO())){
					VehicleTO vehicleTO = new VehicleTO();
					CGObjectConverter.createToFromDomain(
							transportDO.getVehicleDO(), 
							vehicleTO);
					transportTO.setVehicleTO(vehicleTO);
				}
				
				LOGGER.debug("RouteServicedServiceImpl::converterTransportDO2TransportTO::END------------>:::::::");	
			return transportTO;
		
		}

		
		/** 
	     * Load Trip details into TripTO List	     
	     * @inputparam  It contains 
	     *                          <li> RouteTO 
		 *						    <li> TransportModeTO
		 *						    <li> EffectiveFrom
		 *							<li> EffectiveTO
		 *							<li> VendorId (Co-Loader/OTC/OBC/Direct)
		 *							<li> ServiceByTypeId
		 *							<li> ServiceByTypeCode  
	     * @return  	TripTO List
	     * @author      Rohini  Maladi  
		 * @param regionId 
	     */

		@Override
		public List<TripTO> getTripDetailsByVendor(RouteTO routeTO,
				TransportModeTO transportModeTO, String effectiveFrom,
				String effectiveTo, Integer vendorId, Integer serviceByTypeId, String serviceByTypeCode, Integer regionId) 
						throws CGBusinessException, CGSystemException { // , String coLoaderModule
			LOGGER.debug("RouteServicedServiceImpl::getTripDetailsByVendor::START------------>:::::::");
			String trainNumber = null;
			Set<String> trainNumberSet = new HashSet<String>();			
			String vehicleNumber = null;
			Set<String> vehicleNumberSet = new HashSet<String>();
			List<TripTO> tripTOList = null;
			
				TripTO trTO = new TripTO();
				TransportTO transportTO = new TransportTO();
				transportTO.setTransportModeTO(transportModeTO);
				transportTO.setRegionId(regionId);
				trTO.setRouteTO(routeTO);
				trTO.setTransportTO(transportTO);
				
				TripServicedByTO tripServicedByTO = new TripServicedByTO();
				tripServicedByTO.setTripTO(trTO);
				tripServicedByTO.setEffectiveFromStr(effectiveFrom);
				
//				if(coLoaderModule.equalsIgnoreCase(RouteServiceCommonConstants.ROUTE_SERVICED_BY)) {
					tripServicedByTO.setEffectiveToStr(effectiveTo);
//				}
				
				
				ServicedByTO servicedByTO = new ServicedByTO();
				
				if(serviceByTypeCode.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT)){					
					/*EmployeeTO employeeTO = new EmployeeTO();
					employeeTO.setEmployeeId(vendorId);
					servicedByTO.setEmployeeTO(employeeTO);*/
				}else {
					//if(!StringUtil.isEmptyInteger(vendorId)){
						LoadMovementVendorTO loadMovementVendorTO = new LoadMovementVendorTO();
						loadMovementVendorTO.setVendorId(vendorId);
						servicedByTO.setLoadMovementVendorTO(loadMovementVendorTO);
					//}
				}
				ServiceByTypeTO serviceByTypeTO = new ServiceByTypeTO();
				serviceByTypeTO.setServiceByTypeId(serviceByTypeId);
				serviceByTypeTO.setServiceByTypeCode(serviceByTypeCode);
				servicedByTO.setServiceByTypeTO(serviceByTypeTO);
				
				tripServicedByTO.setServicedByTO(servicedByTO);
				
				//List<TripTO> tripTOList = routeServicedCommonService.getTripDetails(trTO);
				List<TripDO> tripDOList = routeServicedDAO.getTripDetailsByTripServicedBy(tripServicedByTO);
				
				// To avoid duplicate train numbers while adding new coloader-train mapping
				if(transportModeTO.getTransportModeCode().equalsIgnoreCase("Rail")) {
					Iterator<TripDO> itr = tripDOList.iterator();
					while (itr.hasNext()) {
						TripDO  tripDO = itr.next();
						trainNumber = tripDO.getTransportDO().getTrainDO().getTrainNumber();
						if(! trainNumberSet.contains(trainNumber)) {
							trainNumberSet.add(trainNumber);
						} else {
							itr.remove();
						}
					}
				} else if(transportModeTO.getTransportModeCode().equalsIgnoreCase("Road")) {
					Iterator<TripDO> vehicleItr = tripDOList.iterator();
					while (vehicleItr.hasNext()) {
						TripDO  vehicleTripDO = vehicleItr.next();
						vehicleNumber = vehicleTripDO.getTransportDO().getVehicleDO().getRegNumber();
						if(! vehicleNumberSet.contains(vehicleNumber)) {
							vehicleNumberSet.add(vehicleNumber);
						} else {
							vehicleItr.remove();
						}
					}
				}
				
				tripTOList = convertTripDOList2TripTOList(tripDOList);
				
				//return routeServicedCommonService.getTripDetails(trTO);
				LOGGER.debug("RouteServicedServiceImpl::getTripDetailsByVendor::END------------>:::::::");
				return tripTOList;
			}

		/** 
	     * Load Employee details into EmployeeTO List	     
	     * @inputparam  
	     * @return  	EmployeeTO List
	     * @author      Rohini  Maladi  
	     */
		@Override
		public List<EmployeeTO> getAllEmployees() throws CGBusinessException,
				CGSystemException {
			
			return routeServicedCommonService.getAllEmployees();
		}
		
		@Override
		public String getTransportDetails(String mode, String transportNumber, Integer regionId)throws CGBusinessException,
								CGSystemException {
			LOGGER.debug("RouteServicedServiceImpl::getTransportDetails::START------------>:::::::");
			TransportTO transportTO = new TransportTO();
			TransportModeTO transportModeTO = new TransportModeTO();
			String transportId = null;
			String transporterId = null;
			JSONObject  detailObj = new JSONObject();
			if(!StringUtil.isStringEmpty(mode)){
				if(!StringUtil.isStringEmpty(transportNumber)){
					
					transportModeTO.setTransportModeCode(mode);

					if(mode.equals(RouteUniversalConstants.AIR_CODE)){
						FlightTO flightTO = new FlightTO();
						flightTO.setFlightNumber(transportNumber);			
						transportTO.setFlightTO(flightTO);
					}else if(mode.equals(RouteUniversalConstants.ROAD_CODE)){
						VehicleTO vehicleTO = new VehicleTO();
						vehicleTO.setRegNumber(transportNumber);
						vehicleTO.setRegionId(regionId);
						transportTO.setVehicleTO(vehicleTO);
					}else if(mode.equals(RouteUniversalConstants.TRAIN_CODE)){
						TrainTO trainTO = new TrainTO();
						trainTO.setTrainNumber(transportNumber);			
						transportTO.setTrainTO(trainTO);
					} 
					transportTO.setTransportModeTO(transportModeTO);
					transportTO.setRegionId(regionId);	
					transportTO = getTransport(transportTO);

					if(!StringUtil.isNull(transportTO) ){
						
						if(!StringUtil.isNull(transportTO.getFlightTO())
								&& !StringUtil.isNull(transportTO.getFlightTO().getFlightId())
								&& mode.equals(RouteUniversalConstants.AIR_CODE)){			
							transporterId = transportTO.getFlightTO().getFlightId().toString();
							detailObj.put("AIRLINE_CODE", transportTO.getFlightTO().getAirlineCode());
						}else if( !StringUtil.isNull(transportTO.getVehicleTO())
								&& !StringUtil.isNull(transportTO.getVehicleTO().getVehicleId())
								&& !StringUtil.isNull(transportTO.getVehicleTO()) && mode.equals(RouteUniversalConstants.ROAD_CODE)){
							transporterId = transportTO.getVehicleTO().getVehicleId().toString();
						}else if(!StringUtil.isNull(transportTO.getTrainTO())
								&& !StringUtil.isNull(transportTO.getTrainTO().getTrainId())
								&&  mode.equals(RouteUniversalConstants.TRAIN_CODE)){
							transporterId = transportTO.getTrainTO().getTrainId().toString();
						} 

						if(!StringUtil.isNull(transportTO) && !StringUtil.isEmptyInteger(transportTO.getTransportId())){
							detailObj.put("TRANSPORT_ID", transportTO.getTransportId());
						}
						if(!StringUtil.isStringEmpty(transporterId )){
							detailObj.put("TRANSPORTER_ID", transporterId);
						}
					}
				}else{
					if(mode.equals(RouteUniversalConstants.AIR_CODE)){
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.FLIGHT} );
					}else if(mode.equals(RouteUniversalConstants.TRAIN_CODE)){
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRAIN} );
					}else if(mode.equals(RouteUniversalConstants.ROAD_CODE)){
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.VEHICLE} );
					}
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
			}
			LOGGER.debug("RouteServicedServiceImpl::getTransportDetails::END------------>:::::::");
			return detailObj.toString();
		}

		@Override
		public List getRouteServicedByDetails(String transportMode, String originCity,
				String destinationCity, String serviceByType, String serviceByTypeCode,
				String effectiveFrom, String effectiveTo, Integer regionId, String transportModeCode, String screenName) throws CGBusinessException,
				CGSystemException { // , String coLoaderModule
			LOGGER.debug("RouteServicedServiceImpl::getRouteServicedByDetails::START------------>:::::::");
			Integer transportModeId = null;
			Integer originCityId = null;
			Integer destinationCityId = null;
			Integer serviceByTypeId = null;
			List tsByList = new ArrayList(4);
			List vendorList = null;
			List<TripTO> tripTOList = null;
			List<TripServicedByTO> tripServicedByList = null;
			boolean tripCreated = Boolean.FALSE;
			Map<String,List<TripTO>> map= new HashMap<String,List<TripTO>>();
			Map<String, Set<String>> vendorToFlightsMap = new HashMap<String, Set<String>>();
			String flightNo = null;
			Set<String> flightNoSet = null;   // new HashSet<String>();
			
			String trainNo = null;
			Map<String, Set<String>> vendorToTrainMap = new HashMap<String, Set<String>>();
			Set<String> trainNoSet = null;
			
			
/*			if(coLoaderModule.equalsIgnoreCase(RouteServiceCommonConstants.ROUTE_SERVICED_BY) && (!StringUtil.isStringEmpty(effectiveTo))){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
			}*/
			
				if(!StringUtil.isStringEmpty(transportMode)){
						if(!StringUtil.isStringEmpty(originCity)){
								if(!StringUtil.isStringEmpty(destinationCity)){
										if(!StringUtil.isStringEmpty(serviceByType)){
												if(!StringUtil.isStringEmpty(serviceByTypeCode)){
														if(!StringUtil.isStringEmpty(effectiveFrom)){
																if(!StringUtil.isStringEmpty(effectiveTo)){
																	
										transportModeId = StringUtil.parseInteger(transportMode);
										originCityId = StringUtil.parseInteger(originCity);
										destinationCityId = StringUtil.parseInteger(destinationCity);
										serviceByTypeId = StringUtil.parseInteger(serviceByType);
										
										
										if(serviceByTypeCode.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT)){
											//vendorList = getAllEmployees();
										}else{
											vendorList = getVendorsListByServiceTypeAndCity(serviceByTypeCode,originCityId);
										}
										
										if(((StringUtil.isNull(vendorList) || CGCollectionUtils.isEmpty(vendorList))  
												&& serviceByTypeCode.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT))
												|| !CGCollectionUtils.isEmpty(vendorList)){
											tripCreated = routeServicedDAO.isTripCreated(originCityId, destinationCityId, transportModeId, transportModeCode, regionId);
											if(tripCreated){
											tripTOList = getTripList(transportMode, originCity,
													destinationCity, serviceByType, serviceByTypeCode,
													effectiveFrom, effectiveTo,null, transportModeCode, regionId); // , coLoaderModule
											
											List<TripTO> tripList = null;
											HashSet<String> flightNumberSet = new HashSet<>();
											List uniqueTripTOList = new ArrayList();
											
											HashSet<String> trainNumberSet = new HashSet<>();
											
											if(!CGCollectionUtils.isEmpty(tripTOList))
												{
													tripServicedByList = getTripServicedByDetails(originCityId,destinationCityId,transportModeId,serviceByTypeId, 
															serviceByTypeCode, effectiveFrom, effectiveTo, transportModeCode, regionId, screenName);
														Integer trip[] = new Integer[tripServicedByList.size()];
														boolean tripExist = Boolean.FALSE;
														int j=0;
														Integer vendor = null;
													if(!serviceByTypeCode.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT) && !CGCollectionUtils.isEmpty(tripServicedByList)){	
														for(TripServicedByTO tripServicedByTO : tripServicedByList){
															if(!StringUtil.isNull(tripServicedByTO)){
																		tripExist = Boolean.FALSE;
																if(serviceByTypeCode.equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT)) {
																	vendor = tripServicedByTO.getServicedByTO().getEmployeeTO().getEmployeeId();	
																}
																else {
																	vendor = tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId();
																	if(transportMode.equals("1")) {
																		flightNo = tripServicedByTO.getTripTO().getTransportTO().getFlightTO().getFlightNumber();
																		if(! vendorToFlightsMap.containsKey(Integer.toString(vendor))) {
																			flightNoSet = new HashSet<String>();
																			flightNoSet.add(flightNo);
																			vendorToFlightsMap.put(Integer.toString(vendor), flightNoSet);
																		} else {
																			flightNoSet = vendorToFlightsMap.get(Integer.toString(vendor));
																			flightNoSet.add(flightNo);
																			vendorToFlightsMap.put(Integer.toString(vendor), flightNoSet);
																		}	
																	} else if(transportMode.equals("2")) {
																		trainNo = tripServicedByTO.getTripTO().getTransportTO().getTrainTO().getTrainNumber();
																		if(! vendorToTrainMap.containsKey(Integer.toString(vendor))) {
																			trainNoSet = new HashSet<String>();
																			trainNoSet.add(trainNo);
																			vendorToTrainMap.put(Integer.toString(vendor), trainNoSet);
																		} else {
																			trainNoSet = vendorToTrainMap.get(Integer.toString(vendor));
																			trainNoSet.add(trainNo);
																			vendorToTrainMap.put(Integer.toString(vendor), trainNoSet);
																		}																		
																	}
																}
																
																
																for(int i=0;i<j;i++){
																	if(trip[i] != null && trip[i] == vendor){
																		tripExist = Boolean.TRUE;
																		break;
																	}
																}
																
																if(!tripExist){
																	trip[j] = vendor;				
																	tripList = getTripList(transportMode, originCity, destinationCity, serviceByType, serviceByTypeCode,
																			effectiveFrom, effectiveTo,trip[j].toString(), transportModeCode, regionId); // , coLoaderModule
																	
																	for (int i=0;i< tripList.size(); i++) {
																		//String flightNumber = tripList.get(i).getTransportTO().getFlightTO().getFlightNumber();
																		TransportTO  transportTO  = tripTOList.get(i).getTransportTO();
																		if(null != transportTO.getFlightTO()) {
																			String flightNumber = transportTO.getFlightTO().getFlightNumber();
																			if(! flightNumberSet.contains(flightNumber)) {
																				uniqueTripTOList.add(tripList.get(i));
																				flightNumberSet.add(flightNumber);
																			}
																		} else if(null != transportTO.getTrainTO()) {
																			String trainNumber = transportTO.getTrainTO().getTrainNumber();
																			if(! trainNumberSet.contains(trainNumber)) {
																				uniqueTripTOList.add(tripList.get(i));
																				trainNumberSet.add(trainNumber);
																			}
																		}
																	}						
																	
																	map.put(trip[j].toString(), uniqueTripTOList);
																	j++;
																}
														}
													}
												 }
													
												if(tripServicedByList.size() > 0) {
													tsByList.add(vendorList);
												} else {
													if(null != screenName) {
														vendorList.clear();
													}
													tsByList.add(vendorList);
												}
												
												HashSet<String> flightSet = new HashSet<>();
												HashSet<String> trainSet = new HashSet<>();
												
												List uniqueTripTOs = new ArrayList();
												for (int i=0;i< tripTOList.size(); i++) {
													TransportTO  transportTO  = tripTOList.get(i).getTransportTO();
													
													if(null != transportTO.getFlightTO()) {
														String flightNumber = transportTO.getFlightTO().getFlightNumber();
														if(! flightSet.contains(flightNumber)) {
															uniqueTripTOs.add(tripTOList.get(i));
															flightSet.add(flightNumber);
														}
													} 			
													
													else if(null != transportTO.getTrainTO()) {
														String trainNumber = transportTO.getTrainTO().getTrainNumber();
														if(! trainSet.contains(trainNumber)) {
															uniqueTripTOs.add(tripTOList.get(i));
															trainSet.add(trainNumber);
														}
													}														
												}									
												
												if(uniqueTripTOs != null && uniqueTripTOs.size() > 0) {
													tsByList.removeAll(tripTOList);
													tsByList.add(uniqueTripTOs);
												// tsByList.add(tripTOList);	
												} else {
													tsByList.add(tripTOList);
												}
												
												tsByList.add(tripServicedByList);
												
												tsByList.add(map);
												
												if(screenName != null && (screenName.equalsIgnoreCase("Air Co-loader Rate Entry - AWB/CD"))) {
													tsByList.add(vendorToFlightsMap);
												} else if(transportMode.equals("2")) {
													tsByList.add(vendorToTrainMap);
												}
													
												
												}else{
													ExceptionUtil.prepareBusinessException(AdminErrorConstants.TRIP_SERVICE_DETAILS_NOT_FOUND);
												}
										}else{
											ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_NOT_EXIST);
										}
										}else{
											ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_VENDORS_NOT_EXIST);				
										}
									} else{
										ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
									}
								}else{
									ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.STATION});
								}
							}else{
								ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.STATION});
							}
						}else{
							ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE});
						}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE});
					}
				}else{
					ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DATE_VAL});				
				}
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DATE_VAL});				
			}
			
			LOGGER.debug("RouteServicedServiceImpl::getRouteServicedByDetails::END------------>:::::::");
			return tsByList;
		}

		@Override
		public List<TripTO> getTripList(String transportMode,
				String originCity, String destinationCity,
				String serviceByType, String serviceByTypeCode,
				String effectiveFrom, String effectiveTo, String vendor, String transportModeCode, Integer regionId)
				throws CGBusinessException, CGSystemException { // , String coLoaderModule
			
				LOGGER.debug("RouteServicedServiceImpl::getTripList::START------------>:::::::");
				
				Integer transportModeId = null;
				Integer originCityId = null;
				Integer destinationCityId = null;
				Integer serviceByTypeId = null;
				Integer vendorId = null;
				List<TripTO> tripTOList = null;
				
/*				if(coLoaderModule.equalsIgnoreCase(RouteServiceCommonConstants.ROUTE_SERVICED_BY) && (!StringUtil.isStringEmpty(effectiveTo))){
					ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
				}	*/				
				
				
					
				if(!StringUtil.isStringEmpty(transportMode)){
					if(!StringUtil.isStringEmpty(originCity)){
						if(!StringUtil.isStringEmpty(destinationCity)){
							if(!StringUtil.isStringEmpty(serviceByType)){
								if(!StringUtil.isStringEmpty(serviceByTypeCode)){
									if(!StringUtil.isStringEmpty(effectiveFrom)){
										if(!StringUtil.isStringEmpty(effectiveTo)){
				transportModeId = StringUtil.parseInteger(transportMode);
				originCityId = StringUtil.parseInteger(originCity);
				destinationCityId = StringUtil.parseInteger(destinationCity);
				serviceByTypeId = StringUtil.parseInteger(serviceByType);

				/*if(StringUtil.isNull(vendor)){
					if(!StringUtil.isNull(request.getParameter(RouteServiceCommonConstants.PARAM_VENDOR))){
						vendorId = StringUtil.parseInteger(request.getParameter(RouteServiceCommonConstants.PARAM_VENDOR));
					}
				}
				else*/
				if(!StringUtil.isStringEmpty(vendor))
				vendorId = StringUtil.parseInteger(vendor);	
				
					RouteTO routeTO = new RouteTO();
					CityTO originCityTO = new CityTO();
					CityTO destCityTO = new CityTO();
					
					originCityTO.setCityId(originCityId);
					destCityTO.setCityId(destinationCityId);
					
					routeTO.setOriginCityTO(originCityTO);
					routeTO.setDestCityTO(destCityTO);
					
					TransportModeTO transportModeTO = new TransportModeTO();
					transportModeTO.setTransportModeId(transportModeId);
					transportModeTO.setTransportModeCode(transportModeCode);
					
					//if(!StringUtil.isNull(vendorId)){
					tripTOList = getTripDetailsByVendor(routeTO, transportModeTO, effectiveFrom, effectiveTo, vendorId, serviceByTypeId, serviceByTypeCode, regionId);
					//}
					}
					else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.TRNSPRT_MODE});
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.STATION});
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.STATION});
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE});
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.SERVICE_BY_TYPE});
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DATE_VAL});				
					}
					}else{
						ExceptionUtil.prepareBusinessException(AdminErrorConstants.ROUTE_DTLS_ERROR, new String[]{RouteServiceCommonConstants.DATE_VAL});				
					}
				LOGGER.debug("RouteServicedServiceImpl::getTripList::END------------>:::::::");
				return tripTOList;
			}
		@Override
		public List<AirlineTO> getAllAirlineDetails()
				throws CGBusinessException, CGSystemException {
			return routeServicedCommonService.getAirlineDetails(new AirlineTO());
		}
		
}

