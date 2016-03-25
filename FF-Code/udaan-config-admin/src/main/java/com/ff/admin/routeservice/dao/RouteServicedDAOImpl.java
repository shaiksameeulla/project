package com.ff.admin.routeservice.dao;

import java.util.List; 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.domain.routeserviced.RouteDO;
import com.ff.domain.routeserviced.RouteModeMappingDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.routeserviced.TransshipmentRouteDO;
import com.ff.domain.routeserviced.TripDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.transport.TransportDO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.universe.routeserviced.constant.RouteUniversalConstants;


/**
 * @author rmaladi
 *
 */
public class RouteServicedDAOImpl extends CGBaseDAO implements RouteServicedDAO {

	
	
private final static Logger LOGGER = LoggerFactory.getLogger(RouteServicedDAOImpl.class);
	

	@Override
	public boolean savePureRoute(List<TripDO> tripDOList,RouteModeMappingDO routeModeMappingDO, Integer[] tripIdsArr) throws CGSystemException {
		LOGGER.trace("RouteServicedDAOImpl :: savePureRoute() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isTripCrated = Boolean.FALSE;

		try{
			
			session = createSession();
			tx = session.beginTransaction();
			if(!CGCollectionUtils.isEmpty(tripDOList)){
			for(TripDO  tripDO :tripDOList)
				session.merge(tripDO);
				session.merge(routeModeMappingDO);
			}	
				if(!StringUtil.isNull(tripIdsArr)){
				Query qry=null;
				qry= session.getNamedQuery(RouteServiceCommonConstants.QRY_TRIP_IN_ACTIVE);
				qry.setString(RouteServiceCommonConstants.PARAM_ACTIVE, RouteServiceCommonConstants.FLAG_N);
				qry.setParameterList(RouteServiceCommonConstants.PARAM_TRIP_ID, tripIdsArr);
				
				qry.executeUpdate();
				}
				tx.commit();
			
			isTripCrated = Boolean.TRUE;
			
		
			
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::savePureRoute :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RouteServicedDAOImpl :: savePureRoute() :: End --------> ::::::");
		return isTripCrated;
	}
	@Override
	public boolean saveRouteModeMapping(RouteModeMappingDO routeModeMappingDO) throws CGSystemException {
		LOGGER.trace("RouteServicedDAOImpl :: saveRouteModeMapping() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isRouteModeMapped = Boolean.FALSE;
		try{
			session = createSession();
			tx = session.beginTransaction();
					
			session.saveOrUpdate(routeModeMappingDO);
			
			tx.commit();
			isRouteModeMapped = Boolean.TRUE;
		}
		catch(Exception e){
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::saveRouteModeMapping :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RouteServicedDAOImpl :: saveRouteModeMapping() :: End --------> ::::::");
		return isRouteModeMapped;

	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<RouteDO> getRouteDetailsByRouteDO(RouteDO routeDO) throws CGSystemException{
		Session session = null;
		 List<RouteDO> routeDOList = null;
		 Criteria criteria = null;
			try{
				session = createSession();
				criteria = session.createCriteria(RouteDO.class,"route");
				
				if(!StringUtil.isEmptyInteger(routeDO.getOriginCityDO().getCityId()))
					criteria.add(Restrictions.eq("originCityDO.cityId", routeDO.getOriginCityDO().getCityId()));
				if(!StringUtil.isEmptyInteger(routeDO.getDestCityDO().getCityId()))
					criteria.add(Restrictions.eq("destCityDO.cityId", routeDO.getDestCityDO().getCityId()));

				
				routeDOList = criteria.list();		
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getRouteDetailsByRouteDO", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			//LOGGER.info("PureRouteDAOImpl :: getFlightDetails() :: End --------> ::::::");
			return routeDOList;		
	}*/
	
	@Override
	public void saveTransport(TransportDO transportDO) throws CGSystemException{
		LOGGER.trace("RouteServicedDAOImpl :: saveTransport() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(transportDO);				
			tx.commit();
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::savePureRoute :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.trace("RouteServicedDAOImpl :: saveTransport() :: End --------> ::::::");
		
	}
	
	@Override
	public void saveRoute(RouteDO routeDO) throws CGSystemException{
		LOGGER.trace("RouteServicedDAOImpl :: saveRoute() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(routeDO);				
			tx.commit();
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::saveRoute :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("RouteServicedDAOImpl :: saveRoute() :: End --------> ::::::");
	}
	
	@Override
	public boolean saveTransshipmentRoute(List<TransshipmentRouteDO> transshipmentRouteDOList, Integer[] transshipmentIdsArr) throws CGSystemException{
		LOGGER.info("RouteServicedDAOImpl :: saveTransshipmentRoute() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isTransshipmentCrated = Boolean.FALSE;
		
		try{
			
			session = createSession();
			tx = session.beginTransaction();
			if(!CGCollectionUtils.isEmpty(transshipmentRouteDOList)){
			for(TransshipmentRouteDO  transshipmentRouteDO :transshipmentRouteDOList)
				session.merge(transshipmentRouteDO);
			}
			if(!StringUtil.isNull(transshipmentIdsArr)){
				Query qry=null;
				qry= session.getNamedQuery(RouteServiceCommonConstants.QRY_TRANSSHIPMENT_ROUTE_IN_ACTIVE);
				qry.setString(RouteServiceCommonConstants.PARAM_ACTIVE, RouteServiceCommonConstants.FLAG_N);
				qry.setParameterList(RouteServiceCommonConstants.PARAM_TRANSSHIPMENT_ROUTE_ID, transshipmentIdsArr);
				
				qry.executeUpdate();
			}
				tx.commit();
			
			isTransshipmentCrated = Boolean.TRUE;
			
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::saveTransshipmentRoute() :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);		
		}		
		LOGGER.info("RouteServicedDAOImpl :: saveTransshipmentRoute() :: End --------> ::::::");
		return isTransshipmentCrated;
	}
	
	@Override
	public void saveServicedBy(ServicedByDO servicedByDO)
			throws CGSystemException {
		LOGGER.trace("RouteServicedDAOImpl :: saveServicedBy() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		try{
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(servicedByDO);				
			tx.commit();
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::saveServicedBy :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("RouteServicedDAOImpl :: saveServicedBy() :: End --------> ::::::");

		
	}
	
	@Override
	public boolean saveTripServicedBy(
			List<TripServicedByDO> tripServicedByDOList, Integer[] tripServicedByIdsArr)
			throws CGSystemException {
		LOGGER.info("RouteServicedDAOImpl :: saveTripServicedBy() :: Start --------> ::::::");
		Session session = null;
		Transaction tx = null;
		boolean isTripServicedByCrated = Boolean.FALSE;

		try{
			
			session = createSession();
			tx = session.beginTransaction();
			if(!CGCollectionUtils.isEmpty(tripServicedByDOList)){
			for(TripServicedByDO  tripServicedByDO :tripServicedByDOList)
				session.merge(tripServicedByDO);
			}
			if(!StringUtil.isNull(tripServicedByIdsArr)){
				Query qry=null;
				qry= session.getNamedQuery(RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_IN_ACTIVE);
				qry.setString(RouteServiceCommonConstants.PARAM_ACTIVE, RouteServiceCommonConstants.FLAG_N);
				qry.setParameterList(RouteServiceCommonConstants.PARAM_TRIP_SERVICED_BY_ID, tripServicedByIdsArr);
				
				qry.executeUpdate();
			}
			tx.commit();
			
			isTripServicedByCrated = Boolean.TRUE;
			
		
			
		}
		catch(Exception e){			
			if(!StringUtil.isNull(tx))
			tx.rollback();
			LOGGER.error("Exception Occured in::RouteServicedDAOImpl::saveTripServicedBy :: " + e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("RouteServicedDAOImpl :: saveTripServicedBy() :: End --------> ::::::");
		return isTripServicedByCrated;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TripDO> getTripDetailsByTripServicedBy(
			TripServicedByTO tripServicedByTO) throws CGSystemException {
		LOGGER.trace("RouteServicedDAOImpl :: getTripDetailsByTripServicedBy() :: Start --------> ::::::");
		List<TripDO> tripDODtls = null;
		try{
			
			if(!StringUtil.isNull(tripServicedByTO)){	
			
				/*if((tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeCode().equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT))
						|| (!StringUtil.isNull(tripServicedByTO.getServicedByTO()) && !StringUtil.isNull(tripServicedByTO.getServicedByTO())
						 &&  StringUtil.isNull(tripServicedByTO.getServicedByTO().getLoadMovementVendorTO()))){*/
				if((tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeCode().equals(RouteServiceCommonConstants.CODE_SERVICE_BY_TYPE_DIRECT))){
					if(!StringUtil.isStringEmpty(tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeCode())
							&& tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE)
							&& !StringUtil.isEmptyInteger(tripServicedByTO.getTripTO().getTransportTO().getRegionId())){
						String query = RouteServiceCommonConstants.QRY_GET_TRIP_DETAILS_BY_EMP_TRIP_SERVICED_BY_FOR_VEHICLE;
						
						String params[] = {RouteServiceCommonConstants.QRY_ORIGIN_CITY,RouteServiceCommonConstants.QRY_DEST_CITY,
								RouteServiceCommonConstants.QRY_TRANSPORT_MODE,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_FROM,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_TO,
								RouteServiceCommonConstants.QRY_SERVICE_BY_TYPE,
								//RouteServiceCommonConstants.QRY_EMPLOYEE,
								RouteServiceCommonConstants.PARAM_ACTIVE,
								RouteServiceCommonConstants.PARAM_OFC_TYPE_CODE,
								RouteServiceCommonConstants.PARAM_REGION_ID};
						
						Object values[] = {tripServicedByTO.getTripTO().getRouteTO().getOriginCityTO().getCityId(),
								tripServicedByTO.getTripTO().getRouteTO().getDestCityTO().getCityId(),tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeId(),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr()),
								tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId(),
								//tripServicedByTO.getServicedByTO().getEmployeeTO().getEmployeeId(),
								RouteServiceCommonConstants.FLAG_Y,
								CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,
								tripServicedByTO.getTripTO().getTransportTO().getRegionId()};
							
						tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else{
						String query = RouteServiceCommonConstants.QRY_GET_TRIP_DETAILS_BY_EMP_TRIP_SERVICED_BY;
						
						String params[] = {RouteServiceCommonConstants.QRY_ORIGIN_CITY,RouteServiceCommonConstants.QRY_DEST_CITY,
								RouteServiceCommonConstants.QRY_TRANSPORT_MODE,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_FROM,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_TO,
								RouteServiceCommonConstants.QRY_SERVICE_BY_TYPE,
								//RouteServiceCommonConstants.QRY_EMPLOYEE,
								RouteServiceCommonConstants.PARAM_ACTIVE};
						
						Object values[] = {tripServicedByTO.getTripTO().getRouteTO().getOriginCityTO().getCityId(),
								tripServicedByTO.getTripTO().getRouteTO().getDestCityTO().getCityId(),tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeId(),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr()),
								tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId(),
								//tripServicedByTO.getServicedByTO().getEmployeeTO().getEmployeeId(),
								RouteServiceCommonConstants.FLAG_Y};
							
						tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}
				}else{
					if(!StringUtil.isStringEmpty(tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeCode())
							&& tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeCode().equals(RouteUniversalConstants.ROAD_CODE)
							&& !StringUtil.isEmptyInteger(tripServicedByTO.getTripTO().getTransportTO().getRegionId())){
						String query = RouteServiceCommonConstants.QRY_GET_TRIP_DETAILS_BY_VENDOR_TRIP_SERVICED_BY_FOR_VEHICLE;
						
						String params[] = {
								RouteServiceCommonConstants.QRY_ORIGIN_CITY,
								RouteServiceCommonConstants.QRY_DEST_CITY,
								RouteServiceCommonConstants.QRY_TRANSPORT_MODE,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_FROM,
								RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_TO,
								RouteServiceCommonConstants.QRY_SERVICE_BY_TYPE,
								RouteServiceCommonConstants.QRY_VENDOR,
								RouteServiceCommonConstants.PARAM_ACTIVE,
								RouteServiceCommonConstants.PARAM_OFC_TYPE_CODE,
								RouteServiceCommonConstants.PARAM_REGION_ID								
								};
						
						Object values[] = {
								tripServicedByTO.getTripTO().getRouteTO().getOriginCityTO().getCityId(),
								tripServicedByTO.getTripTO().getRouteTO().getDestCityTO().getCityId(),
								tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeId(),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr()),
								tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId(),
								tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId(),
								RouteServiceCommonConstants.FLAG_Y,
								CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,
								tripServicedByTO.getTripTO().getTransportTO().getRegionId()
								};
						
						tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}else{
						String query = RouteServiceCommonConstants.QRY_GET_TRIP_DETAILS_BY_VENDOR_TRIP_SERVICED_BY;
						
						String params[] = {RouteServiceCommonConstants.QRY_ORIGIN_CITY,RouteServiceCommonConstants.QRY_DEST_CITY,RouteServiceCommonConstants.QRY_TRANSPORT_MODE,RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_FROM,RouteServiceCommonConstants.QRY_TRIP_SERVICED_BY_EFFECTIVE_TO,RouteServiceCommonConstants.QRY_SERVICE_BY_TYPE,RouteServiceCommonConstants.QRY_VENDOR,RouteServiceCommonConstants.PARAM_ACTIVE};
						
						Object values[] = {tripServicedByTO.getTripTO().getRouteTO().getOriginCityTO().getCityId(),
								tripServicedByTO.getTripTO().getRouteTO().getDestCityTO().getCityId(),tripServicedByTO.getTripTO().getTransportTO().getTransportModeTO().getTransportModeId(),
								DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveFromStr()),DateUtil.stringToDDMMYYYYFormat(tripServicedByTO.getEffectiveToStr()),tripServicedByTO.getServicedByTO().getServiceByTypeTO().getServiceByTypeId(),tripServicedByTO.getServicedByTO().getLoadMovementVendorTO().getVendorId(),RouteServiceCommonConstants.FLAG_Y};
						
						tripDODtls = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
					}
				}
			}
			
		}catch (Exception e) {
			LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getTripDetailsByRoute", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RouteServicedDAOImpl :: getTripDetailsByTripServicedBy() :: END --------> ::::::");
		return tripDODtls;
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<RouteModeMappingDO> getRouteModeMappingDetails(Integer routeId,
			Integer transportModeId) throws CGSystemException {
		LOGGER.trace("RouteServicedDAOImpl :: getRouteModeMappingDetails() :: Start --------> ::::::");
		Session session = null;
		 List<RouteModeMappingDO> routeModeMappingDOList = null;
		 Criteria criteria = null;
			try{
				session = createSession();
				criteria = session.createCriteria(RouteModeMappingDO.class,"routeModeMapping");
				
				if(!StringUtil.isEmptyInteger(routeId))
					criteria.add(Restrictions.eq("routeModeMapping.routeDO.routeId", routeId));
				if(!StringUtil.isEmptyInteger(transportModeId))
					criteria.add(Restrictions.eq("routeModeMapping.transportModeDO.transportModeId", transportModeId));

				
				routeModeMappingDOList = criteria.list();		
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getRouteDetailsByRouteDO", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.trace("RouteServicedDAOImpl :: getRouteModeMappingDetails() :: END --------> ::::::");
			return routeModeMappingDOList;		

		
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean isTripCreated(Integer originCityId, Integer destCityId,
			Integer transportModeId, String transportModeCode, Integer regionId) throws CGSystemException{
		boolean tripCreated = Boolean.FALSE;
		List<Integer> tripIds = null;
		try{
		if(transportModeCode.equals(RouteUniversalConstants.ROAD_CODE) && !StringUtil.isEmptyInteger(regionId)){
			String query = RouteServiceCommonConstants.QRY_GET_TRIP_IDS_FOR_VEHICLE_BY_REGION;
			
			String params[] = {RouteServiceCommonConstants.QRY_ORIGIN_CITY,RouteServiceCommonConstants.QRY_DEST_CITY,RouteServiceCommonConstants.QRY_TRANSPORT_MODE,
					RouteServiceCommonConstants.PARAM_OFC_TYPE_CODE, RouteServiceCommonConstants.PARAM_REGION_ID};
			
			Object values[] = {originCityId,destCityId,transportModeId, CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE, regionId};
			
			tripIds = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		}else{
			String query = RouteServiceCommonConstants.QRY_GET_TRIP_IDS_BY_ROUTE_AND_TRANSPORT_MODE;
			
			String params[] = {RouteServiceCommonConstants.QRY_ORIGIN_CITY,RouteServiceCommonConstants.QRY_DEST_CITY,RouteServiceCommonConstants.QRY_TRANSPORT_MODE};
			
			Object values[] = {originCityId,destCityId,transportModeId};
			
			tripIds = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		}
		if(!CGCollectionUtils.isEmpty(tripIds)){
			tripCreated = Boolean.TRUE;
		}
		}catch (Exception e) {
			LOGGER.error("ERROR : RouteServicedDAOImpl.isTripCreated", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("RouteServicedDAOImpl :: isTripCreated() :: END --------> ::::::");
		return tripCreated; 
	}
}
