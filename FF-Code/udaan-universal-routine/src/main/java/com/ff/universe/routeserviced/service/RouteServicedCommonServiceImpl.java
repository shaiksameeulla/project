package com.ff.universe.routeserviced.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.routeserviced.RouteDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TransshipmentRouteDO;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.transport.TransportDO;
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
import com.ff.transport.AirlineTO;
import com.ff.transport.FlightTO;
import com.ff.transport.PortTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.TransportTO;
import com.ff.transport.VehicleTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.routeserviced.constant.RouteUniversalConstants;
import com.ff.universe.routeserviced.dao.RouteServicedCommonDAO;
import com.ff.universe.transport.service.TransportCommonService;

public class RouteServicedCommonServiceImpl implements RouteServicedCommonService{
	private final static Logger LOGGER = LoggerFactory.getLogger(RouteServicedCommonServiceImpl.class);
	private RouteServicedCommonDAO routeServicedCommonDAO;
	private TransportCommonService transportCommonService;
	private GeographyCommonService geographyCommonService;
	private BusinessCommonService businessCommonService;
	private OrganizationCommonService organizationCommonService;
	
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public BusinessCommonService getBusinessCommonService() {
		return businessCommonService;
	}

	public void setBusinessCommonService(BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	public void setRouteServicedCommonDAO(
			RouteServicedCommonDAO routeServicedCommonDAO) {
		this.routeServicedCommonDAO = routeServicedCommonDAO;
	}

	@Override
	public List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getServiceByTypeListByTransportModeId::START------------>:::::::");
		List<LabelValueBean> serviceByTypeList = new ArrayList<LabelValueBean>();
		List<ServiceByTypeDO> serviceByTypeDOList = routeServicedCommonDAO.
				getServiceByTypeListByTransportModeId(transportModeId);
		for (ServiceByTypeDO serviceByTypeDO : serviceByTypeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(serviceByTypeDO.getServiceByTypeDesc());
			lvb.setValue(serviceByTypeDO.getServiceByTypeId() + CommonConstants.TILD
					+ serviceByTypeDO.getServiceByTypeCode());
			serviceByTypeList.add(lvb);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::getServiceByTypeListByTransportModeId::END------------>:::::::");
		return serviceByTypeList;
	}

	public TransportCommonService getTransportCommonService() {
		return transportCommonService;
	}

	public void setTransportCommonService(
			TransportCommonService transportCommonService) {
		this.transportCommonService = transportCommonService;
	}

	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public RouteServicedCommonDAO getRouteServicedCommonDAO() {
		return routeServicedCommonDAO;
	}

	@Override
	public Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getRouteIdByOriginCityIdAndDestCityId::START------------>:::::::");
		Integer routeId = null;
		RouteDO routeDO =  routeServicedCommonDAO.getRouteByOriginCityIdAndDestCityId(
				originCityId, destCityId);
		if(routeDO!=null){
			routeId = routeDO.getRouteId();
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::getRouteIdByOriginCityIdAndDestCityId::END------------>:::::::");
		return routeId;
	}

	@Override
	public List<TripServicedByTO> getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId::START------------>:::::::");
		List<TripServicedByDO> tripServicedByDOList = routeServicedCommonDAO.
				getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId(
						routeId, transportModeId, serviceByTypeId);

		//List<TripServicedByTO> tripServicedByTOs = tripServicedByTransferListConverter(tripServicedByDOList);
		List<TripServicedByTO> tripServicedByTOs = convertTripServicedByDOsTOsWithValidOperationDays(tripServicedByDOList);
		
		//TODO Remove Duplicate Vendor here only if serviceByType is not Direct
		tripServicedByTOs = removeDuplicateVandorFromTripServicedByTOs(tripServicedByTOs);
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId::END------------>:::::::");
		return tripServicedByTOs;
	}

	private List<TripServicedByTO> removeDuplicateVandorFromTripServicedByTOs(
			List<TripServicedByTO> tripServicedByTOs) {
		LOGGER.debug("RouteServicedCommonServiceImpl::removeDuplicateVandorFromTripServicedByTOs::START------------>:::::::");
		List<TripServicedByTO> tripServicedByTOs1 =new ArrayList<>();
		//Set<Integer> vendorIds = new TreeSet<>();
		
		//getting unique vendorIds
		/*for (TripServicedByTO tripServicedByTO : tripServicedByTOs) {
			if(tripServicedByTO.getServicedByTO()!=null 
					&& tripServicedByTO.getServicedByTO().getLoadMovementVendorTO()!=null){
				vendorIds.add(tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId());
			}
		}*/
		
		//add only unique vendor in tripServicedByTOs1
		/*for (TripServicedByTO tripServicedByTO : tripServicedByTOs) {

			boolean isDuplicate = Boolean.FALSE;
			
			if(tripServicedByTO.getServicedByTO()!=null && tripServicedByTO.getServicedByTO().getLoadMovementVendorTO()!=null){
				for (Integer vendorId : vendorIds) {
					if(vendorId.equals(tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId())){
						isDuplicate = Boolean.TRUE;
						break;
					}
				}
			}
			if(!isDuplicate){
				tripServicedByTOs1.add(tripServicedByTO);
			}
		}*/
				
		//////////////////////////
		Map<String, TripServicedByTO> linkedHashMap = new LinkedHashMap<>();
		for (TripServicedByTO tripServicedByTO : tripServicedByTOs) {
			if(tripServicedByTO.getServicedByTO()!=null 
					&& tripServicedByTO.getServicedByTO().getLoadMovementVendorTO()!=null){

				linkedHashMap.put(tripServicedByTO.getServicedByTO()
						.getLoadMovementVendorTO().getVendorId().toString() + "V",
						tripServicedByTO);
			}else{
				linkedHashMap.put(tripServicedByTO.getTripServicedById().toString(),
						tripServicedByTO);
			}
		}
		
		for (TripServicedByTO tripServicedByTO : linkedHashMap.values()) {
			tripServicedByTOs1.add(tripServicedByTO);
		}
		
		//////////////////////////
		LOGGER.debug("RouteServicedCommonServiceImpl::removeDuplicateVandorFromTripServicedByTOs::END------------>:::::::");
		return tripServicedByTOs1;
	}

	private List<TripServicedByTO> convertTripServicedByDOsTOsWithValidOperationDays(
			List<TripServicedByDO> tripServicedByDOList) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::convertTripServicedByDOsTOsWithValidOperationDays::START------------>:::::::");
		List<TripServicedByTO> tripServicedByTOList = new ArrayList<TripServicedByTO>();
		for (TripServicedByDO tripServicedByDO : tripServicedByDOList) {
			//TODO isValidOperationDays
			if(!isValidOperationDays(tripServicedByDO)){
				continue;
			}
			TripServicedByTO tripServicedByTO = tripServicedByTransferConverter(tripServicedByDO);
			tripServicedByTOList.add(tripServicedByTO);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::convertTripServicedByDOsTOsWithValidOperationDays::END------------>:::::::");
		return tripServicedByTOList;
	}

	private boolean isValidOperationDays(TripServicedByDO tripServicedByDO) {
		LOGGER.debug("RouteServicedCommonServiceImpl::isValidOperationDays::START------------>:::::::");
	    Calendar calDate = Calendar.getInstance();
	    
        int dayIndex = -1;//mon:0 to sun:6
	    switch (calDate.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY: dayIndex = 0;
				break;
			case Calendar.TUESDAY: dayIndex = 1;
				break;
			case Calendar.WEDNESDAY: dayIndex = 2;
				break;
			case Calendar.THURSDAY: dayIndex = 3;
				break;
			case Calendar.FRIDAY: dayIndex = 4;
				break;
			case Calendar.SATURDAY: dayIndex = 5;
				break;
			case Calendar.SUNDAY: dayIndex = 6;
				break;
			default: dayIndex = -1;
				break;
		}
	    
	    //String operationDays = "N-Y-Y-Y-N-N-N";//start from Mon-Sunday
	    if(tripServicedByDO.getAllDays().equals(CommonConstants.YES)){
	    	return true;
	    }
	    if(StringUtils.isBlank(tripServicedByDO.getOperationDays())){
	    	return false;
	    }
		String operationDaysArr[] = tripServicedByDO.getOperationDays().split(
				CommonConstants.HYPHEN);// start from Mon-Sunday index==>mon-0 to sun-6

	    if(dayIndex != -1 && operationDaysArr[dayIndex].equals(CommonConstants.YES)){
	    	return true;
	    }
	    LOGGER.debug("RouteServicedCommonServiceImpl::isValidOperationDays::END------------>:::::::");
		return false;
	
	}

	private List<TripServicedByTO> tripServicedByTransferListConverter(
			List<TripServicedByDO> tripServicedByDOList) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::tripServicedByTransferListConverter::START------------>:::::::");
		List<TripServicedByTO> tripServicedByTOList = new ArrayList<TripServicedByTO>();
		for (TripServicedByDO tripServicedByDO : tripServicedByDOList) {
			TripServicedByTO tripServicedByTO = tripServicedByTransferConverter(tripServicedByDO);
			tripServicedByTOList.add(tripServicedByTO);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::tripServicedByTransferListConverter::END------------>:::::::");
		return tripServicedByTOList;
	}

	private TripServicedByTO tripServicedByTransferConverter(
			TripServicedByDO tripServicedByDO) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::tripServicedByTransferConverter::START------------>:::::::");
		TripServicedByTO tripServicedByTO = new TripServicedByTO();
		
		if(tripServicedByDO!=null){
			CGObjectConverter.createToFromDomain(tripServicedByDO, tripServicedByTO);
		}
		
		ServicedByTO servicedByTO = new ServicedByTO();
		TripTO tripTO =  new TripTO();
		TransportModeTO transportModeTO = new TransportModeTO();

		if(tripServicedByDO.getTransportModeDO()!=null){
			CGObjectConverter.createToFromDomain(tripServicedByDO.getTransportModeDO(), transportModeTO);
		}
		if(tripServicedByDO.getServicedByDO()!=null){
			CGObjectConverter.createToFromDomain(tripServicedByDO.getServicedByDO(), servicedByTO);
		}
		if(tripServicedByDO.getTripDO()!=null){
			CGObjectConverter.createToFromDomain(tripServicedByDO.getTripDO(), tripTO);
		}

		tripServicedByTO.setTransportModeTO(transportModeTO);
		tripServicedByTO.setServicedByTO(servicedByTO);
		tripServicedByTO.setTripTO(tripTO);

		if(tripServicedByDO.getServicedByDO()!=null){
			ServiceByTypeTO serviceByTypeTO = new ServiceByTypeTO();

			if(tripServicedByDO.getServicedByDO().getLoadMovementVendorDO()!=null){
				LoadMovementVendorTO loadMovementVendorTO = new LoadMovementVendorTO();
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getServicedByDO().getLoadMovementVendorDO(), 
						loadMovementVendorTO);
				servicedByTO.setLoadMovementVendorTO(loadMovementVendorTO);
			}
			if(tripServicedByDO.getServicedByDO().getEmployeeDO()!=null){
				EmployeeTO employeeTO = new EmployeeTO();
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getServicedByDO().getEmployeeDO(), employeeTO);
				servicedByTO.setEmployeeTO(employeeTO);
			}
			if(tripServicedByDO.getServicedByDO().getServiceByTypeDO()!=null){
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getServicedByDO().getServiceByTypeDO(), serviceByTypeTO);
			}

			servicedByTO.setServiceByTypeTO(serviceByTypeTO);	
			
			if(tripServicedByDO.getServicedByDO().getServiceByTypeDO()!=null){
				TransportModeTO transportModeTO2 = new TransportModeTO();
				if(tripServicedByDO.getServicedByDO().getServiceByTypeDO().getTransportModeDO()!=null){
					CGObjectConverter.createToFromDomain(
							tripServicedByDO.getServicedByDO().getServiceByTypeDO().getTransportModeDO(), 
							transportModeTO2);
				}
				serviceByTypeTO.setTransportModeTO(transportModeTO2);
			}
		}

		if(tripServicedByDO.getTripDO()!=null){
			RouteTO routeTO = new RouteTO();
			TransportTO transportTO = new TransportTO();
			PortTO originPortTO = new PortTO();
			PortTO destPortTO = new PortTO();
			
			if(tripServicedByDO.getTripDO().getRouteDO()!=null){
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getTripDO().getRouteDO(), routeTO);
			}

			if(tripServicedByDO.getTripDO().getTransportDO()!=null){
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getTripDO().getTransportDO(), transportTO);
			}
			if(tripServicedByDO.getTripDO().getOriginPortDO()!=null){
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getTripDO().getOriginPortDO(), originPortTO);
			}
			if(tripServicedByDO.getTripDO().getDestPortDO()!=null){
				CGObjectConverter.createToFromDomain(
						tripServicedByDO.getTripDO().getDestPortDO(), destPortTO);
			}

			tripTO.setRouteTO(routeTO);
			tripTO.setTransportTO(transportTO);
			tripTO.setOriginPortTO(originPortTO);
			tripTO.setDestPortTO(destPortTO);
			
			if(tripServicedByDO.getTripDO().getTransportDO()!=null){
				TransportModeTO transportModeTO1 = new TransportModeTO();
				FlightTO flightTO = new FlightTO();
				TrainTO trainTO = new TrainTO();
				VehicleTO vehicleTO = new VehicleTO();

				if(tripServicedByDO.getTripDO().getTransportDO().getTransportModeDO()!=null){
					CGObjectConverter.createToFromDomain(
							tripServicedByDO.getTripDO().getTransportDO().getTransportModeDO(), 
							transportModeTO1);
				}
				if(tripServicedByDO.getTripDO().getTransportDO().getFlightDO()!=null){
					CGObjectConverter.createToFromDomain(
							tripServicedByDO.getTripDO().getTransportDO().getFlightDO(), 
							flightTO);
				}
				if(tripServicedByDO.getTripDO().getTransportDO().getTrainDO()!=null){
					CGObjectConverter.createToFromDomain(
							tripServicedByDO.getTripDO().getTransportDO().getTrainDO(), 
							trainTO);
				}
				if(tripServicedByDO.getTripDO().getTransportDO().getVehicleDO()!=null){
					CGObjectConverter.createToFromDomain(
							tripServicedByDO.getTripDO().getTransportDO().getVehicleDO(), 
							vehicleTO);
				}

				transportTO.setTransportModeTO(transportModeTO1);
				transportTO.setFlightTO(flightTO);
				transportTO.setTrainTO(trainTO);
				transportTO.setVehicleTO(vehicleTO);
			}
		}	
		LOGGER.debug("RouteServicedCommonServiceImpl::tripServicedByTransferConverter::END------------>:::::::");
		return tripServicedByTO;
	}

	@Override
	public TransshipmentRouteTO getTransshipmentRoute(
			TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTransshipmentRoute::START------------>:::::::");
		TransshipmentRouteDO transshipmentRouteDO = routeServicedCommonDAO.
				getTransshipmentRoute(transshipmentRouteTO);
		TransshipmentRouteTO transshipmentRouteTO2 = 
				TransshipmentRouteTransferConverter(transshipmentRouteDO);
		LOGGER.debug("RouteServicedCommonServiceImpl::getTransshipmentRoute::END------------>:::::::");
		return transshipmentRouteTO2;
	}

	private TransshipmentRouteTO TransshipmentRouteTransferConverter(
			TransshipmentRouteDO transshipmentRouteDO) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::TransshipmentRouteTransferConverter::START------------>:::::::");
		if(transshipmentRouteDO==null){
			return null;
		}
		TransshipmentRouteTO transshipmentRouteTO = new TransshipmentRouteTO();
		CGObjectConverter.createToFromDomain(transshipmentRouteDO, transshipmentRouteTO);
		LOGGER.debug("RouteServicedCommonServiceImpl::TransshipmentRouteTransferConverter::END------------>:::::::");
		return transshipmentRouteTO;
	}

	@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		return transportCommonService.getAllTransportModeList();
	}
	
	@Override
	public List<RegionTO> getAllRegions()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getAllRegions();
	}
	
	@Override
	public List<CityTO> getAllCities()
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getAllCities();
	}
	
	@Override
	public List<CityTO> getCitiesByCity(CityTO cityTO) throws CGBusinessException, CGSystemException{
		
		return geographyCommonService.getCitiesByCity(cityTO);
	}
	
	@Override
	public List<FlightTO> getFlightDetails(FlightTO flightTO) throws CGBusinessException, CGSystemException{
		
		return transportCommonService.getFlightDetails(flightTO);
	}
	
	@Override
	public List<TrainTO> getTrainDetails(TrainTO trainTO)  throws CGBusinessException, CGSystemException{
		
		return transportCommonService.getTrainDetails(trainTO);
	}
	
	@Override
	public List<VehicleTO> getVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException{
		List<VehicleTO> vehicleTOLIst = null;
		if(!StringUtil.isEmptyInteger(vehicleTO.getRegionId())){
			vehicleTOLIst =  transportCommonService.getVehicleDetailsByRHOOfcAndRegion(vehicleTO);
		}else{
			vehicleTOLIst = transportCommonService.getVehicleDetails(vehicleTO);
		}
		
		return vehicleTOLIst;
	}
	
	@Override
	public List<VehicleTO> getAllVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException{
		List<VehicleTO> vehicleTOLIst = null;
		if(!StringUtil.isEmptyInteger(vehicleTO.getRegionId())){
			vehicleTOLIst =  transportCommonService.getVehicleDetailsByRHOOfcAndRegion(vehicleTO);
		}else{
			vehicleTOLIst = transportCommonService.getVehicleDetails(vehicleTO);
		}
		
		return vehicleTOLIst;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransshipmentRouteTO> getAllTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException{
		LOGGER.debug("RouteServicedCommonServiceImpl::getAllTransshipmentRoute::START------------>:::::::");
		List<TransshipmentRouteTO> transshipmentRouteTOList = new ArrayList<TransshipmentRouteTO>();
		List<TransshipmentRouteDO> transshipmentRouteDOList = new ArrayList<TransshipmentRouteDO>();
		
		transshipmentRouteDOList = routeServicedCommonDAO.getAllTransshipmentRoute(transshipmentRouteTO);
		if(!CGCollectionUtils.isEmpty(transshipmentRouteDOList))
		transshipmentRouteTOList = (List<TransshipmentRouteTO>) CGObjectConverter.createTOListFromDomainList(transshipmentRouteDOList, TransshipmentRouteTO.class);
		LOGGER.debug("RouteServicedCommonServiceImpl::getAllTransshipmentRoute::END------------>:::::::");
		return transshipmentRouteTOList;
	}
	
	@Override
	public List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException{
		cityTOList = geographyCommonService.getAllCitiesByCityIds(cityTOList);
		return cityTOList;
		
	}

	@Override
	public List<TripTO> getTripDetails(TripTO tripTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripDetails::START------------>:::::::");
		List<TripDO> tripDOList = routeServicedCommonDAO.getTripDetails(tripTO);
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripDetails::END------------>:::::::");
		return tripListDO2TOConverter(tripDOList);
	}
	
	
	private List<TripTO> tripListDO2TOConverter(
			List<TripDO> tripDOList) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::tripListDO2TOConverter::START------------>:::::::");
		List<TripTO> tripTOList = new ArrayList<TripTO>();
		for (TripDO tripDO : tripDOList) {
			TripTO tripTO = converterTripDO2TripTO(tripDO);
			tripTOList.add(tripTO);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::tripListDO2TOConverter::END------------>:::::::");
		return tripTOList;
	}
	
	
	private TripTO converterTripDO2TripTO(TripDO tripDO) throws CGBusinessException{
		LOGGER.debug("RouteServicedCommonServiceImpl::converterTripDO2TripTO::START------------>:::::::");
		TripTO tripTO = new TripTO();
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
		
		LOGGER.debug("RouteServicedCommonServiceImpl::converterTripDO2TripTO::END------------>:::::::");
		return tripTO;
	
	}

	@Override
	public List<TransportTO> getTransportDetails(TransportTO transportTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTransportDetails::START------------>:::::::");
		List<TransportDO> transportDOList = routeServicedCommonDAO.getTransportDetails(transportTO);
		LOGGER.debug("RouteServicedCommonServiceImpl::getTransportDetails::END------------>:::::::");
		return transportListDO2TOConverter(transportDOList);
	}
	
	private List<TransportTO> transportListDO2TOConverter(
			List<TransportDO> transportDOList) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::transportListDO2TOConverter::START------------>:::::::");
		List<TransportTO> transportTOList = new ArrayList<TransportTO>();
		for (TransportDO transportDO : transportDOList) {
			TransportTO transportTO = converterTransportDO2TransportTO(transportDO);
			transportTOList.add(transportTO);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::transportListDO2TOConverter::END------------>:::::::");
		return transportTOList;
	}
	
	private TransportTO converterTransportDO2TransportTO(TransportDO transportDO) throws CGBusinessException{
		LOGGER.debug("RouteServicedCommonServiceImpl::converterTransportDO2TransportTO::START------------>:::::::");
		TransportTO transportTO  = new TransportTO();
		
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
				if(!StringUtil.isNull(transportDO.getVehicleDO().getRegionalOfficeDO())){
					vehicleTO.setRhoOfcId(transportDO.getVehicleDO().getRegionalOfficeDO().getOfficeId());
				}
				transportTO.setVehicleTO(vehicleTO);
			}
			LOGGER.debug("RouteServicedCommonServiceImpl::converterTransportDO2TransportTO::END------------>:::::::");
		return transportTO;
	
	}

	@Override
	public List<LoadMovementVendorTO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO)
			throws CGBusinessException, CGSystemException {

		return businessCommonService.getVendorsList(loadMovementVendorTO);
	}

	@Override
	public List<ServicedByTO> getServicedByListByServicedBy(
			ServicedByTO servicedByTO) throws CGBusinessException,
			CGSystemException {

		LOGGER.debug("RouteServicedCommonServiceImpl::getServicedByListByServicedBy::START------------>:::::::");
		List<ServicedByDO> servicedByDOList = routeServicedCommonDAO.getServicedByListByServicedBy(servicedByTO);
		LOGGER.debug("RouteServicedCommonServiceImpl::getServicedByListByServicedBy::END------------>:::::::");
		return servicedByListDO2TOConverter(servicedByDOList);
	}
	
	
	private List<ServicedByTO> servicedByListDO2TOConverter(
			List<ServicedByDO> servicedByDOList) throws CGBusinessException {
		LOGGER.debug("RouteServicedCommonServiceImpl::servicedByListDO2TOConverter::START------------>:::::::");
		List<ServicedByTO> servicedByTOList = new ArrayList<ServicedByTO>();
		for (ServicedByDO servicedByDO : servicedByDOList) {
			ServicedByTO servicedByTO = converterServicedByDOList2ServicedByTOList(servicedByDO);
			servicedByTOList.add(servicedByTO);
		}
		LOGGER.debug("RouteServicedCommonServiceImpl::servicedByListDO2TOConverter::END------------>:::::::");
		return servicedByTOList;
	}
		
	private ServicedByTO converterServicedByDOList2ServicedByTOList(ServicedByDO servicedByDO) throws CGBusinessException{
		LOGGER.debug("RouteServicedCommonServiceImpl::converterServicedByDOList2ServicedByTOList::START------------>:::::::");
		ServicedByTO servicedByTO =  null;
		LoadMovementVendorTO loadMovementVendorTO = null;
		EmployeeTO employeeTO = null;
		TransportModeTO transportModeTO = null;
		ServiceByTypeTO serviceByTypeTO = null;
		
		
			servicedByTO =  new ServicedByTO();
		
		CGObjectConverter.createToFromDomain(
				servicedByDO, servicedByTO);
		
		if(!StringUtil.isNull(servicedByDO.getLoadMovementVendorDO())){
			loadMovementVendorTO = new LoadMovementVendorTO();
			CGObjectConverter.createToFromDomain(
					servicedByDO.getLoadMovementVendorDO(), loadMovementVendorTO);
			
			servicedByTO.setLoadMovementVendorTO(loadMovementVendorTO);
		}

		if(!StringUtil.isNull(servicedByDO.getEmployeeDO())){
			employeeTO = new EmployeeTO();
			CGObjectConverter.createToFromDomain(
					servicedByDO.getEmployeeDO(), employeeTO);
			
			servicedByTO.setEmployeeTO(employeeTO);
		}
		
		if(!StringUtil.isNull(servicedByDO.getServiceByTypeDO())){
			serviceByTypeTO = new ServiceByTypeTO();
			CGObjectConverter.createToFromDomain(
					servicedByDO.getServiceByTypeDO(), serviceByTypeTO);

			if(!StringUtil.isNull(servicedByDO.getServiceByTypeDO().getTransportModeDO())){
				transportModeTO = new TransportModeTO();
				CGObjectConverter.createToFromDomain(						
						servicedByDO.getServiceByTypeDO().getTransportModeDO(), 
						transportModeTO);
				serviceByTypeTO.setTransportModeTO(transportModeTO);
			}
			servicedByTO.setServiceByTypeTO(serviceByTypeTO);
		}
				
		LOGGER.debug("RouteServicedCommonServiceImpl::converterServicedByDOList2ServicedByTOList::END------------>:::::::");
		return servicedByTO;
	
	}

	@Override
	public List<TripServicedByTO> getTripServicedByDOListByTripServicedBy(
			TripServicedByTO tripServicedByTO, String screenName) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByDOListByTripServicedBy::START------------>:::::::");
		List<TripServicedByDO> tripServicedByDOList = null;
		if(!StringUtil.isNull(tripServicedByTO.getTripTO().getTransportTO())
				&& !StringUtil.isNull(tripServicedByTO.getTransportModeTO())
				&& (tripServicedByTO.getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE))
				&& !StringUtil.isEmptyInteger(tripServicedByTO.getTripTO().getTransportTO().getRegionId())){
			tripServicedByDOList = routeServicedCommonDAO.
					getTripServicedByDOListByTripServicedByForVehicle(tripServicedByTO);
		}else{
			
			tripServicedByDOList = routeServicedCommonDAO.getTripServicedByDOListByTripServicedByForAirColoading(tripServicedByTO);
			// tripServicedByDOList = routeServicedCommonDAO.getTripServicedByDOListByTripServicedBy(tripServicedByTO, screenName);
		}

		List<TripServicedByTO> tripServicedByTOList = tripServicedByTransferListConverter(tripServicedByDOList);		
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByDOListByTripServicedBy::END------------>:::::::");
		return tripServicedByTOList;

	}

	@Override
	public List<EmployeeTO> getAllEmployees() throws CGBusinessException,
			CGSystemException {

		return organizationCommonService.getAllEmployees();
	}

	//For Dispatch Transport
	@Override
	public List<TripServicedByTO> getTripServicedByTOsByRouteIdModeIdServiceByTypeIdVendorId(
			TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByTOsByRouteIdModeIdServiceByTypeIdVendorId::START------------>:::::::");
		List<TripServicedByDO> tripServicedByDOs = routeServicedCommonDAO
				.getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId(tripServicedByTO);
		List<TripServicedByTO> tripServicedByTOList = convertTripServicedByDOsTOsWithValidOperationDays(tripServicedByDOs);
		LOGGER.debug("RouteServicedCommonServiceImpl::getTripServicedByTOsByRouteIdModeIdServiceByTypeIdVendorId::END------------>:::::::");
		return tripServicedByTOList;
	}

	@Override
	public List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId)
			throws CGBusinessException, CGSystemException {
		return businessCommonService.getVendorsListByServiceTypeAndCity(serviceByTypeCode,cityId);
	}

	@Override
	public OfficeTO getRHOOfcIdByRegion(Integer originRegionId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getRHOOfcIdByRegion(originRegionId);
	}
	
	@Override
	public List<AirlineTO> getAirlineDetails(AirlineTO airtelTO)
			throws CGBusinessException, CGSystemException {
		return transportCommonService.getAirlineDetails(airtelTO);
	}



}

