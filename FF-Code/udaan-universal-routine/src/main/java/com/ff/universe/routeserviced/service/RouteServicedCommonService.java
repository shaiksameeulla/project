package com.ff.universe.routeserviced.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.LoadMovementVendorTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.ServicedByTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.transport.AirlineTO;
import com.ff.transport.FlightTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportTO;
import com.ff.transport.VehicleTO;

public interface RouteServicedCommonService {

	/** 
     * get ServiceByType List using TransportModeId
     * Load the ServiceByType details by TransportModeId from database into list of ServiceByTypeDO object into LabelValueBean
     * @inputparam  transportModeId Integer
     * @return  	List<LabelValueBean>     
     */
	List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException, CGSystemException;

	/** 
     * get Route using origin city and destination city
     * Load the Route details by origin city id and destination city id from database
     * @inputparam  originCityId Integer
     * @inputparam  destinationCityId Integer
     * @return  	RouteId Integer   
     */
	Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId, Integer destCityId)
			throws CGBusinessException, CGSystemException;

	/** 
     * get TripServicedBy details using route , transportmode and servicebytype
     * Load the TripServicedBy details by route id, transportmode id and servicebytype id from database
     * @inputparam  routeId Integer
     * @inputparam  transportmodeId Integer
     * @inputparam  serviceByTypeId Integer
     * @return  	List<TripServicedByDO>   
     */
	List<TripServicedByTO> getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
					throws CGBusinessException, CGSystemException;

	/** 
     * get Transshipment Route Details using TransshipmentRoute
     * Load the Transshipment Route details by TransshipmentRouteTO from database
     * @inputparam  TransshipmentRouteTO Object    
     * @return  	TransshipmentRouteTO Object   
     */	
	TransshipmentRouteTO getTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO)
					throws CGBusinessException, CGSystemException;

	/** 
     * Get TransportMode list
     * This method will return  TransportMode List 
     * @inputparam 
     * @return  	TransportMode List<LabelValueBean>       
     */
	List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException;
	
	/** 
     * Get TransportMode list
     * This method will return  Regions List 
     * @inputparam 
     * @return  	Regions List<LabelValueBean>       
     */
	List<RegionTO> getAllRegions()
		throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Cities List
     * This method will return  List of Cities 
     * @inputparam 
     * @return  	List<CityTO>  
     */
	List<CityTO> getAllCities()
			throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Cities List By City Object
     * This method will return  List of Cities 
     * @inputparam  CityTO Object
     * @return  	List<CityTO>  
     */
	List<CityTO> getCitiesByCity(CityTO cityTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Flight List By Flight Object
     * This method will return  List of Flights 
     * @inputparam  FlightTO Object
     * @return  	List<FlightTO>  
     */
	List<FlightTO> getFlightDetails(FlightTO flightTO) throws CGBusinessException, CGSystemException; 	 
	
	/** 
     * Get Train Details By Train Object
     * This method will return  List of Trains 
     * @inputparam  TrainTO Object
     * @return  	List<TrainTO>  
     */
	List<TrainTO> getTrainDetails(TrainTO trainTO)  throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Vehicle List By Flight Object
     * This method will return  List of Vehicles 
     * @inputparam  VehicleTO Object
     * @return  	List<VehicleTO>  
     */
	List<VehicleTO> getVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * get List of Transshipment Route Details using TransshipmentRoute
     * Load the List of Transshipment Route details by TransshipmentRouteTO from database
     * @inputparam  TransshipmentRouteTO Object    
     * @return  	List<TransshipmentRouteTO>   
     */
	List<TransshipmentRouteTO> getAllTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Cities List By List of City Objects
     * This method will return  List of City Ids 
     * @inputparam  List<CityTO> Object
     * @return  	List<CityTO>  
     */
	List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;

	/** 
     * Get Trip List By Trip Object
     * This method will return  List of Trips 
     * @inputparam  TripTO Object
     * @return  	List<TripTO>  
     */
	List<TripTO> getTripDetails(TripTO tripTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * get List of Transport Details using Transport
     * Load the List of Transport details by TransportTO from database
     * @inputparam  TransportTO Object    
     * @return  	List<TransportTO>   
     */
	List<TransportTO> getTransportDetails(TransportTO transportTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * Load LoadMovementVendor details into LoadMovementVendorTO List object	     
     * @inputparam  ServiceByTypeCode 
     * @return  	LoadMovementVendorTO List
     */
	List<LoadMovementVendorTO> getVendorsList(LoadMovementVendorTO loadMovementVendorTO)throws CGBusinessException, CGSystemException;
	
	/** 
     * get List of ServicedBy Details using ServicedBy
     * Load the List of ServicedBy details by ServicedByTO from database
     * @inputparam  ServicedByTO Object    
     * @return  	List<ServicedByTO>   
     */
	List<ServicedByTO> getServicedByListByServicedBy(ServicedByTO servicedByTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * get List of TripServicedBy  Details using TripServicedBy
     * Load the List of TripServicedBy details by TripServicedByTO from database
     * @inputparam  TripServicedByTO Object    
     * @return  	List<TripServicedByTO>   
     */
	List<TripServicedByTO> getTripServicedByDOListByTripServicedBy(TripServicedByTO tripServicedByTO, String screenName)
			 throws CGBusinessException, CGSystemException;

	/** 
     * Load Employee details into EmployeeTO List	     
     * @inputparam  
     * @return  	EmployeeTO List
     */
	List<EmployeeTO> getAllEmployees() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the trip serviced by t os by route id mode id service by type id vendor id.
	 *
	 * @param tripServicedByTO the trip serviced by to
	 * @return the trip serviced by t os by route id mode id service by type id vendor id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<TripServicedByTO> getTripServicedByTOsByRouteIdModeIdServiceByTypeIdVendorId(
			TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException;

	List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId) throws CGBusinessException,
			CGSystemException;

	OfficeTO getRHOOfcIdByRegion(Integer originRegionId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the airline details.
	 *
	 * @param airtelTO the airtel to
	 * @return the airline details
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	List<AirlineTO> getAirlineDetails(AirlineTO airtelTO)
			throws CGBusinessException, CGSystemException;

	List<VehicleTO> getAllVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException;
}
