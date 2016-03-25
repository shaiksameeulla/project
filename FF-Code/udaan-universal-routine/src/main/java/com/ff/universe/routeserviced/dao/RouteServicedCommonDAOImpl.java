package com.ff.universe.routeserviced.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
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
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.routeserviced.constant.RouteUniversalConstants;

public class RouteServicedCommonDAOImpl extends CGBaseDAO
		implements RouteServicedCommonDAO{

	private final static Logger LOGGER = LoggerFactory.getLogger(RouteServicedCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceByTypeDO> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getServiceByTypeListByTransportModeId() :: Start --------> ::::::");
		Session session = null;
		List<ServiceByTypeDO> serviceByTypeDOList = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			serviceByTypeDOList = (List<ServiceByTypeDO>)session.createCriteria(ServiceByTypeDO.class)
					.add(Restrictions.eq(UdaanCommonConstants.TRANSPORT_MODE_DO_TRANSPORT_MODE_ID_PARAM, transportModeId))
					.list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getServiceByTypeListByTransportModeId() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getServiceByTypeListByTransportModeId() :: End --------> ::::::");
		return serviceByTypeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RouteDO getRouteByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getRouteByOriginCityIdAndDestCityId() :: Start --------> ::::::");
		Session session = null;
		RouteDO routeDO = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			List<RouteDO> routeDOList = (List<RouteDO>)session.createCriteria(RouteDO.class)
					.add(Restrictions.eq(UdaanCommonConstants.ORIGIN_CITY_DO_CITY_ID_PARAM, originCityId))
					.add(Restrictions.eq(UdaanCommonConstants.DEST_CITY_DO_CITY_ID_PARAM, destCityId))
					.list();
			if(routeDOList!=null && routeDOList.size()>0){
				routeDO = routeDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getRouteByOriginCityIdAndDestCityId() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getRouteByOriginCityIdAndDestCityId() :: End --------> ::::::");
		return routeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServicedByDO> getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
			throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId() :: Start --------> ::::::");
		List<TripServicedByDO> tripServicedByDOList = null;
		try{
			if(routeId!=null){
			    tripServicedByDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
		    			UdaanCommonConstants.GET_TRIP_SERVICED_BY_LIST_BY_ROUTE_ID_TRANSPORT_MODE_ID_SERVICE_BY_TYPE_ID,
		    			new String[]{
		    				UdaanCommonConstants.ROUTE_ID_PARAM,
				    		UdaanCommonConstants.TRANSPORT_MODE_ID_PARAM,
				    		UdaanCommonConstants.SERVICE_BY_TYPE_ID_PARAM},			    		
				    	new Object[]{routeId, transportModeId, serviceByTypeId});
			}else{
			    tripServicedByDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
		    			UdaanCommonConstants.GET_TRIP_SERVICED_BY_LIST_BY_TRANSPORT_MODE_ID_SERVICE_BY_TYPE_ID,
		    			new String[]{
	    					UdaanCommonConstants.TRANSPORT_MODE_ID_PARAM,
	    					UdaanCommonConstants.SERVICE_BY_TYPE_ID_PARAM},			    		
	    				new Object[]{transportModeId, serviceByTypeId});
			}

		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId() :: " + e.getMessage());
			throw new CGSystemException(e);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByRouteIdTransportModeIdServiceByTypeId() :: End --------> ::::::");
		return tripServicedByDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransshipmentRouteDO getTransshipmentRoute(
			TransshipmentRouteTO transshipmentRouteTO) throws CGSystemException {
		TransshipmentRouteDO transshipmentRouteDO = null;
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTransshipmentRoute() :: Start --------> ::::::");
		try {
			List<TransshipmentRouteDO> transshipmentRouteDOs = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getTransshipmentRoute", 
					new String[]{"transshipmentCityId", "servicedCityId"}, 
					new Object[]{transshipmentRouteTO.getTransshipmentCityId(),
							transshipmentRouteTO.getServicedCityId()});

			if(transshipmentRouteDOs!=null && transshipmentRouteDOs.size()>0){
				transshipmentRouteDO = transshipmentRouteDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getTransshipmentRoute() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTransshipmentRoute() :: End --------> ::::::");
		return transshipmentRouteDO;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransshipmentRouteDO> getAllTransshipmentRoute(
			TransshipmentRouteTO transshipmentRouteTO) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getAllTransshipmentRoute() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<TransshipmentRouteDO> transshipmentRouteDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(TransshipmentRouteDO.class,"transshipmentRoute");
				
				if(!StringUtil.isEmptyInteger(transshipmentRouteTO.getTransshipmentCityId())){
					criteria.add(Restrictions.eq("transshipmentRoute.transshipmentCityId", transshipmentRouteTO.getTransshipmentCityId()));
				}
				if(!StringUtil.isEmptyInteger(transshipmentRouteTO.getServicedCityId())){
				criteria.add(Restrictions.eq("transshipmentRoute.servicedCityId", transshipmentRouteTO.getServicedCityId()));
				}
							
				transshipmentRouteDOList = criteria.list(); 
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedCommonDAOImpl.getAllTransshipmentRoute", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RouteServicedCommonDAOImpl :: getAllTransshipmentRoute() :: End --------> ::::::");
			return transshipmentRouteDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripDO> getTripDetails(TripTO tripTO)  throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripDetails() :: Start --------> ::::::");
		List<TripDO> tripDODtls = null;
		
		try{
			
		if(!StringUtil.isEmptyInteger(tripTO.getTransportTO().getRegionId()) && 
				!StringUtil.isStringEmpty(tripTO.getTransportTO().getTransportModeTO().getTransportModeCode())
						&& (tripTO.getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE) ) ){
			String query = RouteUniversalConstants.QRY_GET_TRIP_DETAILS_OF_VEHICLE__BY_REGION;
			
			String params[] = {RouteUniversalConstants.QRY_ORIGIN_CITY,RouteUniversalConstants.QRY_DEST_CITY,RouteUniversalConstants.QRY_TRANSPORT_MODE, RouteUniversalConstants.OFC_TYPE_CODE,RouteUniversalConstants.PARAM_REGION_ID};
			
			Object values[] = {tripTO.getRouteTO().getOriginCityTO().getCityId(),tripTO.getRouteTO().getDestCityTO().getCityId(),tripTO.getTransportTO().getTransportModeTO().getTransportModeId(),CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,tripTO.getTransportTO().getRegionId() };
			
			tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);

		}else{
			String query = RouteUniversalConstants.QRY_GET_TRIP_DETAILS;
			
			String params[] = {RouteUniversalConstants.QRY_ORIGIN_CITY,RouteUniversalConstants.QRY_DEST_CITY,RouteUniversalConstants.QRY_TRANSPORT_MODE};
			
			Object values[] = {tripTO.getRouteTO().getOriginCityTO().getCityId(),tripTO.getRouteTO().getDestCityTO().getCityId(),tripTO.getTransportTO().getTransportModeTO().getTransportModeId()};
			
			tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getTripDetailsByRoute", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripDetails() :: END --------> ::::::");
		return tripDODtls; 
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransportDO> getTransportDetails(TransportTO transportTO) throws CGSystemException{
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTransportDetails() :: Start --------> ::::::");
		 List<TransportDO> transportDOList = new ArrayList<TransportDO>();
		 String query = "";
		 
		 
			try{
				if(transportTO.getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.AIR_CODE)){	
					query = RouteUniversalConstants.QRY_GET_TRANSPORT_DETAILS_BY_FLIGHT;
					String params[]  = {RouteUniversalConstants.QRY_FLIGHT_NUMBER,RouteUniversalConstants.QRY_TRANSPORT_MODE};
					Object values[] = {transportTO.getFlightTO().getFlightNumber(),transportTO.getTransportModeTO().getTransportModeCode()};
					transportDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}
				else if(transportTO.getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.TRAIN_CODE)){	
					query = RouteUniversalConstants.QRY_GET_TRANSPORT_DETAILS_BY_TRAIN;
					String params[]  = {RouteUniversalConstants.QRY_TRAIN_NUMBER,RouteUniversalConstants.QRY_TRANSPORT_MODE};
					Object values[] = {transportTO.getTrainTO().getTrainNumber(),transportTO.getTransportModeTO().getTransportModeCode()};
					transportDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
				}else if(transportTO.getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE)){
					if(!StringUtil.isEmptyInteger(transportTO.getRegionId())){
						query = RouteUniversalConstants.QRY_GET_TRANSPORT_DETAILS_BY_ROAD_RHO_OFC_AND_REGION;
						String params[]  = {RouteUniversalConstants.QRY_VEHICLE_REG_NUMBER,RouteUniversalConstants.QRY_TRANSPORT_MODE,RouteUniversalConstants.OFC_TYPE_CODE,RouteUniversalConstants.PARAM_REGION_ID};
						Object values[] = {transportTO.getVehicleTO().getRegNumber(),transportTO.getTransportModeTO().getTransportModeCode(),CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE, transportTO.getRegionId()};
						transportDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else if(!StringUtil.isEmptyInteger(transportTO.getVehicleTO().getRhoOfcId())){	
						query = RouteUniversalConstants.QRY_GET_TRANSPORT_DETAILS_BY_ROAD_AND_RHOOFC;
						String params[]  = {RouteUniversalConstants.QRY_VEHICLE_REG_NUMBER,RouteUniversalConstants.QRY_TRANSPORT_MODE,RouteUniversalConstants.PARAM_OFC_ID};
						Object values[] = {transportTO.getVehicleTO().getRegNumber(),transportTO.getTransportModeTO().getTransportModeCode(),transportTO.getVehicleTO().getRhoOfcId()};
						transportDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else{	
						query = RouteUniversalConstants.QRY_GET_TRANSPORT_DETAILS_BY_ROAD;
						String params[]  = {RouteUniversalConstants.QRY_VEHICLE_REG_NUMBER,RouteUniversalConstants.QRY_TRANSPORT_MODE};
						Object values[] = {transportTO.getVehicleTO().getRegNumber(),transportTO.getTransportModeTO().getTransportModeCode()};
						transportDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}
				}
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getTransportDetails", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("RouteServicedCommonDAOImpl :: getTransportDetails() :: END --------> ::::::");
			return transportDOList; 
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServicedByDO> getServicedByListByServicedBy(
			ServicedByTO servicedByTO) throws CGSystemException {

		LOGGER.trace("RouteServicedCommonDAOImpl :: getServicedByListByServicedBy() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<ServicedByDO> servicedByDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(ServicedByDO.class,"servicedBy");
				
				if(!StringUtil.isNull(servicedByTO.getServiceByTypeTO())){
				if(!StringUtil.isEmptyInteger(servicedByTO.getServiceByTypeTO().getServiceByTypeId())){
					criteria.add(Restrictions.eq("servicedBy.serviceByTypeDO.serviceByTypeId", servicedByTO.getServiceByTypeTO().getServiceByTypeId()));
				}
				if(!StringUtil.isStringEmpty(servicedByTO.getServiceByTypeTO().getServiceByTypeCode())){
					criteria.add(Restrictions.eq("servicedBy.serviceByTypeDO.serviceByTypeCode", servicedByTO.getServiceByTypeTO().getServiceByTypeCode()));
				}
				}
				
				
				if(!StringUtil.isNull(servicedByTO.getServiceByTypeTO()) && !StringUtil.isNull(servicedByTO.getServiceByTypeTO().getTransportModeTO())){
					criteria.createAlias("servicedBy.serviceByTypeDO", "stype");
				if(!StringUtil.isEmptyInteger(servicedByTO.getServiceByTypeTO().getTransportModeTO().getTransportModeId())){
					criteria.add(Restrictions.eq("stype.transportModeDO.transportModeId", servicedByTO.getServiceByTypeTO().getTransportModeTO().getTransportModeId()));
				}
				if(!StringUtil.isStringEmpty(servicedByTO.getServiceByTypeTO().getServiceByTypeCode())){
					criteria.add(Restrictions.eq("stype.transportModeDO.transportModeCode", servicedByTO.getServiceByTypeTO().getTransportModeTO().getTransportModeCode()));
				}
				}
				
				if(!StringUtil.isNull(servicedByTO.getLoadMovementVendorTO())){
				if(!StringUtil.isEmptyInteger(servicedByTO.getLoadMovementVendorTO().getVendorId())){
				criteria.add(Restrictions.eq("servicedBy.loadMovementVendorDO.vendorId", servicedByTO.getLoadMovementVendorTO().getVendorId()));
				}
				if(!StringUtil.isStringEmpty(servicedByTO.getLoadMovementVendorTO().getVendorType())){
					criteria.add(Restrictions.eq("servicedBy.loadMovementVendorDO.vendorType", servicedByTO.getLoadMovementVendorTO().getVendorType()));
				}
				}
				if(!StringUtil.isNull(servicedByTO.getEmployeeTO())){
				if(!StringUtil.isEmptyInteger(servicedByTO.getEmployeeTO().getEmployeeId())){
					criteria.add(Restrictions.eq("servicedBy.employeeDO.employeeId", servicedByTO.getEmployeeTO().getEmployeeId()));
				}
				if(!StringUtil.isStringEmpty(servicedByTO.getEmployeeTO().getEmpCode())){
					criteria.add(Restrictions.eq("servicedBy.employeeDO.empCode", servicedByTO.getEmployeeTO().getEmpCode()));
				}
				}
				servicedByDOList = criteria.list(); 
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedCommonDAOImpl.getServicedByListByServicedBy", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RouteServicedCommonDAOImpl :: getServicedByListByServicedBy() :: End --------> ::::::");
			return servicedByDOList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServicedByDO> getTripServicedByDOListByTripServicedBy(
			TripServicedByTO tripServicedByTO, String screenName) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getServicedByListByServicedBy() :: Start --------> ::::::");
		 
		 Session session = null;
		 List<TripServicedByDO> tripServicedByDOList = null;
		 Criteria criteria=null;
			try{
				if(!StringUtil.isNull(tripServicedByTO)){
				session = createSession();
				criteria = session.createCriteria(TripServicedByDO.class,"tripServicedBy");
				
				
					if(!StringUtil.isNull(tripServicedByTO.getTripTO())){
						if(!StringUtil.isNull(tripServicedByTO.getTripTO().getRouteTO())){	
							if(!StringUtil.isEmptyInteger(tripServicedByTO.getTripTO().getRouteTO().getRouteId())){
								criteria.createAlias("tripServicedBy.tripDO", "trip");
								criteria.add(Restrictions.eq("trip.routeDO.routeId", tripServicedByTO.getTripTO().getRouteTO().getRouteId()));
								criteria.add(Restrictions.eq("trip.active", RouteUniversalConstants.FLAG_Y));
							}
						}
					}
					if(!StringUtil.isNull(tripServicedByTO.getTransportModeTO())){
						criteria.createAlias("tripServicedBy.transportModeDO", "transportModeDO");
						if(!StringUtil.isEmptyInteger(tripServicedByTO.getTransportModeTO().getTransportModeId())){
							criteria.add(Restrictions.eq("transportModeDO.transportModeId", tripServicedByTO.getTransportModeTO().getTransportModeId()));
						}
						if(!StringUtil.isStringEmpty(tripServicedByTO.getTransportModeTO().getTransportModeCode())){
							criteria.add(Restrictions.eq("transportModeDO.transportModeCode", tripServicedByTO.getTransportModeTO().getTransportModeCode()));
						}
					}
					if(!StringUtil.isNull(tripServicedByTO.getServicedByTO())){
						if(!StringUtil.isNull(tripServicedByTO.getServicedByTO().getServiceByTypeTO())){
							criteria.createAlias("tripServicedBy.servicedByDO", "servicedBy");
							if(!StringUtil.isEmptyInteger(tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId())){
								criteria.add(Restrictions.eq("servicedBy.serviceByTypeDO.serviceByTypeId", tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId()));
							}
							if(!StringUtil.isStringEmpty(tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeCode())){
								criteria.add(Restrictions.eq("servicedBy.serviceByTypeDO.serviceByTypeCode", tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeCode()));
							}
						}
					}
					
					if(!StringUtil.isNull(tripServicedByTO.getEffectiveFromStr())){
						criteria.add(Restrictions.eq("tripServicedBy.effectiveFrom", DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr())));
					}
					
					if(null == screenName || !(screenName.equalsIgnoreCase("Air Co-loader Rate Entry - AWB/CD"))) {
						if(!StringUtil.isNull(tripServicedByTO.getEffectiveToStr())){
							 criteria.add(Restrictions.eq("tripServicedBy.effectiveTo", DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr())));
						}
					}
					
					criteria.add(Restrictions.eq("tripServicedBy.active", RouteUniversalConstants.FLAG_Y));
					
					criteria.addOrder(Order.asc("tripServicedBy.tripServicedById"));
					
					tripServicedByDOList = criteria.list(); 
				}
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedCommonDAOImpl.getServicedByListByServicedBy", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RouteServicedCommonDAOImpl :: getServicedByListByServicedBy() :: End --------> ::::::");
			return tripServicedByDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServicedByDO> getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId(
			TripServicedByTO tripServicedByTO) throws CGSystemException {

		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId() :: Start --------> ::::::");
		List<TripServicedByDO> tripServicedByDOList = null;
		try{
		    tripServicedByDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
	    			UdaanCommonConstants.QRY_GET_TRIP_SERVICED_BY_LIST_BY_ROUTE_ID_MODE_ID_SERVICE_BY_TYPE_ID_VENDOR_ID,
	    			new String[]{
	    				UdaanCommonConstants.ROUTE_ID_PARAM,
			    		UdaanCommonConstants.TRANSPORT_MODE_ID_PARAM,
			    		UdaanCommonConstants.PARAM_VENDOR_ID,
			    		UdaanCommonConstants.SERVICE_BY_TYPE_ID_PARAM},			    		
			    	new Object[]{
	    					tripServicedByTO.getTripTO().getRouteTO().getRouteId(), 
	    					tripServicedByTO.getTransportModeTO().getTransportModeId(), 
	    					tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId(),
	    					tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId()
	    					});

		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId() :: " + e.getMessage());
			throw new CGSystemException(e);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOsByRouteIdModeIdServiceByTypeIdVendorId() :: End --------> ::::::");
		return tripServicedByDOList;
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripServicedByDO> getTripServicedByDOListByTripServicedByForAirColoading(TripServicedByTO tripServicedByTO) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByTripServicedByForAirColoading() :: Start --------> ::::::");
		List<TripServicedByDO> tripServicedByDOList = null;
		try{
		    tripServicedByDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
	    			RouteUniversalConstants.QRY_GET_TRIP_SERVICED_BY_LIST_BY_TRIP_SERVICED_BY_FOR_AIR_COLOADING,
	    			new String[]{
	    				UdaanCommonConstants.ROUTE_ID_PARAM,
			    		UdaanCommonConstants.TRANSPORT_MODE_ID_PARAM,
			    		UdaanCommonConstants.TRANSPORT_MODE_CODE_PARAM,
			    		UdaanCommonConstants.PARAM_EFFECTIVE_FROM
			    	},			    		
			    	new Object[]{
	    					tripServicedByTO.getTripTO().getRouteTO().getRouteId(), 
	    					tripServicedByTO.getTransportModeTO().getTransportModeId(), 
	    					tripServicedByTO.getTransportModeTO().getTransportModeCode(),
	    					DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr())
	    			});			
		} catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getTripServicedByDOListByTripServicedByForAirColoading() :: " + e.getMessage());
			throw new CGSystemException(e);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByTripServicedByForAirColoading() :: End --------> ::::::");
		
		return tripServicedByDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TripServicedByDO> getTripServicedByDOListByTripServicedByForVehicle(
			TripServicedByTO tripServicedByTO) throws CGSystemException {
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByTripServicedByForVehicle() :: Start --------> ::::::");
		List<TripServicedByDO> tripServicedByDOList = null;
		try{
		    tripServicedByDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
	    			RouteUniversalConstants.QRY_GET_TRIP_SERVICED_BY_LIST_BY_TRIP_SERVICED_BY_FOR_VEHICLE,
	    			new String[]{
	    				UdaanCommonConstants.ROUTE_ID_PARAM,
			    		UdaanCommonConstants.TRANSPORT_MODE_ID_PARAM,
			    		UdaanCommonConstants.PARAM_MAPPED_TO_REGION,
			    		UdaanCommonConstants.SERVICE_BY_TYPE_ID_PARAM,
			    		UdaanCommonConstants.QRY_PARAM_OFF_TYPE_CODE,
			    		UdaanCommonConstants.PARAM_EFFECTIVE_FROM,
			    		UdaanCommonConstants.PARAM_EFFECTIVE_TO
			    		},			    		
			    	new Object[]{
	    					tripServicedByTO.getTripTO().getRouteTO().getRouteId(), 
	    					tripServicedByTO.getTransportModeTO().getTransportModeId(), 
	    					tripServicedByTO.getTripTO().getTransportTO().getRegionId(),
	    					tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId(),
	    					CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,
	    					DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()),
	    					DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr())
	    					});

		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::RouteServicedCommonDAOImpl::getTripServicedByDOListByTripServicedByForVehicle() :: " + e.getMessage());
			throw new CGSystemException(e);
		}		
		LOGGER.trace("RouteServicedCommonDAOImpl :: getTripServicedByDOListByTripServicedByForVehicle() :: End --------> ::::::");
		return tripServicedByDOList;
	}	
}
