package com.ff.admin.routeservice.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.LoadMovementVendorTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.routeserviced.RouteTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.to.routeservice.PureRouteTO;
import com.ff.transport.AirlineTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.TransportTO;
/**
 * @author rmaladi
 *
 */
public interface RouteServicedService 
{
	/** 
     * Get TransportMode list
     * This method will return  TransportMode List 
     * @inputparam 
     * @return  	TransportMode List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
	List<LabelValueBean> getAllTransportModeList() throws CGBusinessException, CGSystemException;
	
	/** 
     * Get TransportMode list
     * This method will return  Regions List 
     * @inputparam 
     * @return  	Regions List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
	List<RegionTO> getAllRegions() throws CGBusinessException, CGSystemException;
	
	/** 
     * Get Cities List
     * This method will return  List of Cities 
     * @inputparam 
     * @return  	List<CityTO> 
     * @author      Rohini  Maladi  
     */
	List<CityTO> getAllCities() throws CGBusinessException, CGSystemException;
	
	/** 
     * Save Pure Route
     * This method insert or update the pure route details in database
     * @inputparam  PureRouteTO Object
     * @return  	String (Success/Failure)
     * @author      Rohini  Maladi  
     */
	String savePureRoute(PureRouteTO pureRouteTO, int userId) throws CGBusinessException, CGSystemException;
	
	/** 
     * Load Trip details into TO object
     * This method get the trip details using route object and transport mode object
     * @inputparam  RouteTO Object, TransportModeTO object
     * @return  	PureRouteTO
     * @author      Rohini  Maladi  
	 * @param regionId 
     */
	PureRouteTO getTripDetailsByRoute(RouteTO routeTO,TransportModeTO transportModeTO, Integer regionId) throws CGBusinessException, CGSystemException;
	
	/** 
     * Load Trasnport details into Transport object
     * This method get the transport details using transportTO object
     * @inputparam  TransportTO Object
     * @return  	TransportTO
     * @author      Rohini  Maladi  
     */
	TransportTO getTransport(TransportTO transportTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * List the all cities with the region     * 
     * @inputparam  RegionTO object
     * @return  	List<CityTO>
     * @author      Rohini  Maladi  
     */
	List<CityTO> getStationsByRegion(RegionTO regionTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * Transshipment Route details into TO object
     * This method get the transshipmetn route details using transshipment route object
     * @inputparam  TransshipmentRouteTO Object
     * @return  	TransshipmentRouteTO
     * @author      Rohini  Maladi  
     */
	List<TransshipmentRouteTO> getTransshipmentRouteDetails(TransshipmentRouteTO transshipmentRouteTO) throws CGBusinessException, CGSystemException;
	
	/** 
     * Save Transshipment Route
     * This method insert or update the transshipment route details in database
     * @inputparam  TransshipmentRouteTO Object
     * @return  	String (Success/Failure)
     * @author      Rohini  Maladi  
     */
	String saveTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO) throws CGBusinessException, CGSystemException;

	/** 
     * Load serviceByType details into List object
     * This method get the serviceByType details using transportmode
     * @inputparam  TransportModeId
     * @return  	ServiceByType List<LabelValueBean>
     * @author      Rohini  Maladi  
     */
	List<LabelValueBean> getServiceBytypeByTransportModeId(Integer transportModeId) throws CGBusinessException, CGSystemException;

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
     */
	List<TripServicedByTO> getTripServicedByDetails(Integer originCityId,Integer destinationCityId,Integer transportModeId,Integer serviceByTypeId,String serviceByTypeCode, String effectiveFrom, String effectiveTo, String transportModeCode, Integer regionId, String screenName) throws CGBusinessException, CGSystemException;

	/** 
     * Load LoadMovementVendor details into LoadMovementVendorTO List object	     
     * @inputparam  ServiceByTypeCode 
     * @return  	LoadMovementVendorTO List
     * @author      Rohini  Maladi  
     */
	List<LoadMovementVendorTO> getVendorsListByServiceTypeAndCity(String serviceByTypecode, Integer cityId)throws CGBusinessException, CGSystemException;
	
	/** 
     * Load Trip details into TripTO List object	     
     * @inputparam  RouteTO object, TransportModeTO object 
     * @return  	Trip List
     * @author      Rohini  Maladi  
     */	
	List<TripTO> getTripDetails(RouteTO routeTO,TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException;

	/** 
     * insert or update the ServicedBy object into Database	     
     * @inputparam  TripServicedByTO object 
     * @return  	String (Success/Failure)
     * @author      Rohini  Maladi  
     */
	String saveTripServicedBy(TripServicedByTO tripServicedByTO) throws CGBusinessException, CGSystemException;
	
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
     */
	List<TripTO> getTripDetailsByVendor(RouteTO routeTO,TransportModeTO transportModeTO, String effectiveFrom, String effectiveTo, Integer vendorId, 
			Integer serviceByTypeId, String serviceByTypeCode, Integer regionId)
			throws CGBusinessException, CGSystemException; // , String coLoaderModule

	/** 
     * Load Employee details into EmployeeTO List	     
     * @inputparam  
     * @return  	EmployeeTO List
     * @author      Rohini  Maladi  
     */
	List<EmployeeTO> getAllEmployees() throws CGBusinessException, CGSystemException;
	
	/**
	 * @param regionId 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	String getTransportDetails(String mode, String String, Integer regionId )throws CGBusinessException, CGSystemException;
	
	/**
	 * @param transportMode
	 * @param originCity
	 * @param destinationCity
	 * @param serviceByType
	 * @param serviceByTypeCode
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param regionId 
	 * @param transportModeCode 
	 * @param screenName 
	 * @param co-loader module being referred
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List getRouteServicedByDetails(String transportMode, String originCity,
			String destinationCity, String serviceByType, String serviceByTypeCode,
			String effectiveFrom, String effectiveTo, Integer regionId, String transportModeCode, String screenName)throws CGBusinessException, CGSystemException; // , String coLoaderModule
	
	/**
	 * @param transportMode
	 * @param originCity
	 * @param destinationCity
	 * @param serviceByType
	 * @param serviceByTypeCode
	 * @param effectiveFrom
	 * @param effectiveTo
	 * @param vendor
	 * @param regionId 
	 * @param transportModeCode 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<TripTO> getTripList(String transportMode, String originCity,
			String destinationCity, String serviceByType, String serviceByTypeCode,
			String effectiveFrom, String effectiveTo, String vendor, String transportModeCode, Integer regionId) 
					throws CGBusinessException, CGSystemException;  // , String coLoaderModule

	/**
	 * Gets the airline details.
	 *
	 * @param airtelTO the airtel to
	 * @return the airline details
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	List<AirlineTO> getAllAirlineDetails()
			throws CGBusinessException, CGSystemException;
}
