package com.ff.admin.routeservice.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.routeserviced.RouteDO;
import com.ff.domain.routeserviced.RouteModeMappingDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TransshipmentRouteDO;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.transport.TransportDO;
import com.ff.routeserviced.TripServicedByTO;
/**
 * @author rmaladi
 *
 */
public interface RouteServicedDAO {
	/** 
     * Save Pure Route
     * This method insert or update the pure route details in database
     * @inputparam  TripDOlist and RouteModeMappingDO objects
     * @return  	Boolean value (True/False)
     * @author      Rohini  Maladi  
     */
	 boolean savePureRoute(List<TripDO> tripDOList, RouteModeMappingDO routeModeMappingDO, Integer[] tripIdsArr) throws CGSystemException;
	
	 /** 
	     * Save RouteModeMapping details
	     * This method insert or update the RouteModeMapping details in database
	     * @inputparam  RouteModeMappingDO object
	     * @return  	Boolean value (True/False)
	     * @author      Rohini  Maladi  
	     */
	 boolean saveRouteModeMapping(RouteModeMappingDO routeModeMappingDO) throws CGSystemException;
	 
	 /** 
	     * Save Transport details
	     * This method insert the Tranpsort details in database
	     * @inputparam  TranpsortDO object
	     * @return  
	     * @author      Rohini  Maladi  
	     */
	 void saveTransport(TransportDO transportDO) throws CGSystemException;
	 
	 /** 
	     * Save Route details
	     * This method insert the Route details in database
	     * @inputparam  RouteDO object
	     * @return  
	     * @author      Rohini  Maladi  
	     */
	 void saveRoute(RouteDO routeDO) throws CGSystemException;
	 
	 /** 
	     * Save TransshipmentRoute details
	     * This method insert the TransshipmentRoute details in database
	     * @inputparam  TransshipmentRouteDO object
	     * @return  	Boolean value (True/False)
	     * @author      Rohini  Maladi  
	     */
	 boolean saveTransshipmentRoute(List<TransshipmentRouteDO> transshipmentRouteDOList, Integer[] transshipmentIdsArr) throws CGSystemException;
	 
	 /** 
	     * Save ServicedBy details
	     * This method insert the ServicedBy details in database
	     * @inputparam  ServicedByDO object
	     * @return  
	     * @author      Rohini  Maladi  
	     */
	 void saveServicedBy(ServicedByDO servicedByDO) throws CGSystemException;
	 
	 /** 
	     * Save TripServicedBy details
	     * This method insert or update the TripServicedBy details in database
	     * @inputparam  TripServicedByDO List object
	     * @return  	Boolean value (True/False)
	     * @author      Rohini  Maladi  
	     */
	 boolean saveTripServicedBy(List<TripServicedByDO> tripServicedByDOList, Integer[] tripServicedByIdsArr) throws CGSystemException;
	 
	 /** 
	     * get Trip details using TripServicedBy details
	     * Load the Trip details by TripServicedBy details from database into list of Trip object
	     * @inputparam  TripServicedByDO object
	     *					  <li>Origin City Id
	     *					  <li>Destination City Id
	     *                    <li>ServicedByType Id
	     *                    <li>TransportMode Id
     	 *                    <li>effectiveFrom
     	 *                    <li>effectiveTo
     	 *                    <li>Vendor/Employee Id                                          
	     * @return  	List<TripDO>
	     * @author      Rohini  Maladi  
	     */
	 List<TripDO> getTripDetailsByTripServicedBy(TripServicedByTO tripServicedByTO) throws CGSystemException;
	 
	 /** 
	     * get RouteModeMappingDetails details using RouteId and TransportModeId
	     * Load the RouteModeMapping from the database
	     * @inputparam  RouteId Integer
	     * 				TransportModeId Integer
	     * @return  	List<RouteModeMappingDO>
	     * @author      Rohini  Maladi  
	     */
	 List<RouteModeMappingDO> getRouteModeMappingDetails(Integer routeId, Integer transportModeId) throws CGSystemException;
	 
	 boolean isTripCreated(Integer originCityId, Integer destCityId, Integer transportModeId, String transportModeCode, Integer regionId) throws CGSystemException;
}


