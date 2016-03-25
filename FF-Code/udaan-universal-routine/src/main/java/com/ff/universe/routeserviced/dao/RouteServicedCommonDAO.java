package com.ff.universe.routeserviced.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.routeserviced.RouteDO;
import com.ff.domain.routeserviced.ServiceByTypeDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TransshipmentRouteDO;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.transport.TransportDO;
import com.ff.routeserviced.ServicedByTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.transport.TransportTO;

public interface RouteServicedCommonDAO {

	/** 
     * get ServiceByType List using TransportModeId
     * Load the ServiceByType details by TransportModeId from database into list of ServiceByTypeDO object
     * @inputparam  transportModeId Integer
     * @return  	List<ServiceByTypeDO>      
     */
	List<ServiceByTypeDO> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGSystemException;

	/** 
     * get Route using origin city and destination city
     * Load the Route details by origin city id and destination city id from database
     * @inputparam  originCityId Integer
     * @inputparam  destinationCityId Integer
     * @return  	RouteDO   
     */	
	RouteDO getRouteByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGSystemException;

	/** 
     * get TripServicedBy details using route , transportmode and servicebytype
     * Load the TripServicedBy details by route id, transportmode id and servicebytype id from database
     * @inputparam  routeId Integer
     * @inputparam  transportmodeId Integer
     * @inputparam  serviceByTypeId Integer
     * @return  	List<TripServicedByDO>   
     */
	List<TripServicedByDO> getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
					 throws CGSystemException;

	/** 
     * get Transshipment Route Details using TransshipmentRoute
     * Load the Transshipment Route details by TransshipmentRouteTO from database
     * @inputparam  TransshipmentRouteTO Object
     * 					  <ul>
     *                    <li>Transshipment City id
     *                    <li>Serviced City Id                                          
     *                    </ul>      
     * @return  	TransshipmentRouteDO   
     */	
	TransshipmentRouteDO getTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO)
			 throws CGSystemException;
	
	/** 
     * get List of Transshipment Route Details using TransshipmentRoute
     * Load the List of Transshipment Route details by TransshipmentRouteTO from database
     * @inputparam  TransshipmentRouteTO Object    
     * 					  <ul>
     *                    <li>Transshipment City id
     *                    <li>Serviced City Id                                          
     *                    </ul>      
     * @return  	List<TransshipmentRouteDO>   
     */
	List<TransshipmentRouteDO> getAllTransshipmentRoute(
			TransshipmentRouteTO transshipmentRouteTO) throws CGSystemException;
	
	/** 
     * get List of Trip Details using TransshipmentRoute
     * Load the List of Trip details by TripTO from database
     * @inputparam  TripTO Object  
     * 					  <ul>
     *                    <li>Origin City id
     *                    <li>Destination City Id
     *                    <li>TransportMode Id                                          
     *                    </ul>  
     * @return  	List<TripDO>   
     */
	List<TripDO> getTripDetails(TripTO tripTO) throws CGSystemException;
	
	/** 
     * get List of Transport Details using Transport
     * Load the List of Transport details by TransportTO from database
     * @inputparam  TransportTO Object
     *					  <ul>
     *                    <li>Flight/Number/Vehicle Reg Number
     *                    <li>TransportMode Code (A/T/R)                                          
     *                    </ul>     
     * @return  	List<TransportDO>   
     */
	List<TransportDO> getTransportDetails(TransportTO transportTO) throws CGSystemException;
	
	/** 
     * get List of ServicedBy Details using ServicedBy
     * Load the List of ServicedBy details by ServicedByTO from database
     * @inputparam  ServicedByTO Object
     *					  <ul>
     *                    <li>ServicedByType Id
     *                    <li>ServicedByType Code                    
     *                    <li>TransportMode Code (A/T/R)
     *                    <li>TransportMode Id
     *                    <li>Vendor/Employee Id
     *                    <li>Vendor Type/Employee Code                                             
     *                    </ul>         
     * @return  	List<ServicedByDO>   
     */
	List<ServicedByDO> getServicedByListByServicedBy(ServicedByTO servicedByTO) throws CGSystemException;
	
	/** 
     * get List of TripServicedBy  Details using TripServicedBy
     * Load the List of TripServicedBy details by TripServicedByTO from database
     * @inputparam  TripServicedByTO Object
     *					  <ul>
     *					  <li>RouteId
     *                    <li>ServicedByType Id
     *                    <li>ServicedByType Code                    
     *                    <li>TransportMode Code (A/T/R)
     *                    <li>TransportMode Id
     *                    <li>effectiveFrom
     *                    <li>effectiveTo                                          
     *                    </ul>         
     * @return  	List<TripServicedByDO>   
     */
	List<TripServicedByDO> getTripServicedByDOListByTripServicedBy(TripServicedByTO tripServicedByTO, String screenName)
					 throws CGSystemException;

	/**
	 * Gets the trip serviced by d os by route id mode id service by type id vendor id.
	 *
	 * @param tripServicedByTO the trip serviced by to
	 * @return the trip serviced by d os by route id mode id service by type id vendor id
	 * @throws CGSystemException the cG system exception
	 */
	List<TripServicedByDO> getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId(
			TripServicedByTO tripServicedByTO) throws CGSystemException;

	List<TripServicedByDO> getTripServicedByDOListByTripServicedByForVehicle(
			TripServicedByTO tripServicedByTO) throws CGSystemException;

	List<TripServicedByDO> getTripServicedByDOListByTripServicedByForAirColoading(
			TripServicedByTO tripServicedByTO) throws CGSystemException;

	
}
