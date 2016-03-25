package com.ff.universe.transport.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.transport.AirlineDO;
import com.ff.domain.transport.FlightDO;
import com.ff.domain.transport.TrainDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.transport.FlightTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.VehicleTO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * @author narmdr
 *
 */
public class TransportCommonDAOImpl extends CGBaseDAO implements 
		TransportCommonDAO{

	private final static Logger LOGGER = LoggerFactory.getLogger(TransportCommonDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransportModeDO> getAllTransportModeList()
			throws CGSystemException {
		LOGGER.info("TransportCommonDAOImpl :: getAllTransportModeList() :: Start --------> ::::::");
		List<TransportModeDO> transportModeDOList = null;
		Session session = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			transportModeDOList = (List<TransportModeDO>)session.createCriteria(TransportModeDO.class).list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::TransportCommonDAOImpl::getAllTransportModeList()" + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("TransportCommonDAOImpl :: getAllTransportModeList() :: End --------> ::::::");
		return transportModeDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDO> getVehicleListByOfficeId(Integer officeId)
			throws CGSystemException {
		LOGGER.info("TransportCommonDAOImpl :: getVehicleListByOfficeId() :: Start --------> ::::::");
		Session session = null;
		List<VehicleDO> vehicleDOList = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			vehicleDOList = (List<VehicleDO>)session.createCriteria(VehicleDO.class)
					.add(Restrictions.eq(UdaanCommonConstants.REGIONAL_OFFICE_DO_OFFICE_ID_PARAM, officeId))
					.list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::TransportCommonDAOImpl::getVehicleListByOfficeId() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("TransportCommonDAOImpl :: getVehicleListByOfficeId() :: End --------> ::::::");
		return vehicleDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	 public List<FlightDO> getFlightDetails(FlightTO flightTO) throws CGSystemException {
		 
		 Session session = null;
		 List<FlightDO> flightDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(FlightDO.class,"flight");
				
				if(!StringUtil.isEmptyInteger(flightTO.getFlightId())){
					criteria.add(Restrictions.eq("flight.flightNumber", flightTO.getFlightId()));
				}
				if(!StringUtil.isStringEmpty(flightTO.getFlightNumber())){
				criteria.add(Restrictions.eq("flight.flightNumber", flightTO.getFlightNumber()));
				}
							
				flightDOList = criteria.list(); 
				
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getFlightDetails", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("TransportCommonDAOImpl :: getFlightDetails() :: End --------> ::::::");
			return flightDOList;

	 }
	@SuppressWarnings("unchecked")
	@Override
	 public List<TrainDO> getTrainDetails(TrainTO trainTO)  throws CGSystemException {
		 Session session = null;
		 List<TrainDO> trainDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				criteria = session.createCriteria(TrainDO.class,"train");
				
				if(!StringUtil.isEmptyInteger(trainTO.getTrainId())){
					criteria.add(Restrictions.eq("train.trainId", trainTO.getTrainId()));
					}
				if(!StringUtil.isStringEmpty(trainTO.getTrainNumber())){
				criteria.add(Restrictions.eq("train.trainNumber", trainTO.getTrainNumber()));
				}
				if(!StringUtil.isStringEmpty(trainTO.getTrainNumber())){
					criteria.add(Restrictions.eq("train.trainNumber", trainTO.getTrainNumber()));
					}
				trainDOList=criteria.list();
				
				
			}catch (Exception e) {
				LOGGER.error("ERROR : TransportCommonDAOImpl.getTrainDetails", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("TransportCommonDAOImpl :: getFlightDetails() :: End --------> ::::::");
			return trainDOList;	
	 }
	@SuppressWarnings("unchecked")
	@Override
	 public List<VehicleDO> getVehicleDetails(VehicleTO vehicleTO) throws CGSystemException {
		 Session session = null;
		 List<VehicleDO> vehicleDOList = null;
		 Criteria criteria=null;
			try{
				session = createSession();
				
				criteria = session.createCriteria(VehicleDO.class,"vehicle");
				
				if(!StringUtil.isEmptyInteger(vehicleTO.getVehicleId())){
					criteria.add(Restrictions.eq("vehicle.vehicleId", vehicleTO.getVehicleId()));
				}
				if(!StringUtil.isStringEmpty(vehicleTO.getRegNumber())){
				criteria.add(Restrictions.eq("vehicle.regNumber", vehicleTO.getRegNumber()));
				}
				if(!StringUtil.isStringEmpty(vehicleTO.getVehicleType())){
					criteria.add(Restrictions.eq("vehicle.vehicleType", vehicleTO.getVehicleType()));
				}
				vehicleDOList = criteria.list();
							
			}
			catch (Exception e) {
				LOGGER.error("ERROR : RouteServicedServiceDAOImpl.getVehicleDetails", e);
				throw new CGSystemException(e);
			}
			finally{
				closeSession(session);
			}		
			LOGGER.info("TransportCommonDAOImpl :: getFlightDetails() :: End --------> ::::::");
			return vehicleDOList;	
	 }

	@SuppressWarnings("unchecked")
	@Override
	public TransportModeDO getTransportMode(TransportModeTO transportModeTO)
			throws CGSystemException {
		LOGGER.info("TransportCommonDAOImpl :: getTransportMode() :: Start --------> ::::::");
		try {
			List<TransportModeDO> transportModeDOs = null;
			String queryName = "getTransportModeByCode";
			transportModeDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "transportModeCode", transportModeTO.getTransportModeCode());

			if (transportModeDOs != null && transportModeDOs.size() > 0) {
				return transportModeDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::TransportCommonDAOImpl::getTransportMode() :: " + e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.info("TransportCommonDAOImpl :: getTransportMode() :: End --------> ::::::");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDO> getVehicleDetailsByRHOOfcAndRegion(
			VehicleTO vehicleTO) throws CGSystemException {
	
		 LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: Start --------> ::::::");
		List<VehicleDO> vehicleDOList = null;
		 String query = "";
		 if(vehicleTO.isAvailable()) {
			 vehicleDOList = getVehicleAvailablity(vehicleTO);
			 return vehicleDOList;
		 }
		 else if(vehicleTO.isAllVehiclesInRegion()) {
			 vehicleDOList = getAllVehicleDetailsByRHOOfcAndRegion(vehicleTO);
			 return vehicleDOList;
		 } else {
			try{
				query = "getVehicleDetailsByRegionAndRHOOfc";
				String params[]  = {"regNumber","officeTypeCode","regionId"};
				Object values[] = {vehicleTO.getRegNumber(),CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE, vehicleTO.getRegionId()};
				vehicleDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
			}
			catch (Exception e) {
				LOGGER.error("ERROR : TransportCommonDAOImpl.getVehicleDetailsByRHOOfcAndRegion", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: END --------> ::::::");
			return vehicleDOList;
		 }
//			return vehicleDOList;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDO> getAllVehicleDetailsByRHOOfcAndRegion(VehicleTO vehicleTO) throws CGSystemException {
		LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: Start --------> ::::::");
		List<VehicleDO> vehicleDOList = null;
		 String query = "";
		 
		 
			try{
				query = "getAllVehicleDetailsByRegionAndRHOOfc";
				String params[]  = {"officeTypeCode","regionId"};
				Object values[] = {CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE, vehicleTO.getRegionId()};
				vehicleDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
			}
			catch (Exception e) {
				LOGGER.error("ERROR : TransportCommonDAOImpl.getVehicleDetailsByRHOOfcAndRegion", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: END --------> ::::::");
			
			return vehicleDOList;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDO> getVehicleAvailablity(VehicleTO vehicleTO) throws CGSystemException {
		LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: Start --------> ::::::");
		List<VehicleDO> vehicleDOList = null;
		 String query = "";
		 
			try{
				query = "getVehicleAvailability";
				String params[]  = {"regNumbers"};
				Object values[] = {vehicleTO.getTransportNumbers()};
				vehicleDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
			
			}
			catch (Exception e) {
				LOGGER.error("ERROR : TransportCommonDAOImpl.getVehicleDetailsByRHOOfcAndRegion", e);
				throw new CGSystemException(e);
			}
			LOGGER.trace("TransportCommonDAOImpl :: getVehicleDetailsByRHOOfcAndRegion() :: END --------> ::::::");
			
			return vehicleDOList;	
	}
	
	@Override
	public List<AirlineDO> getAirlineDetails(AirlineDO airlineDO)
			throws CGSystemException {
		LOGGER.info("TransportCommonDAOImpl :: getAllTransportModeList() :: Start --------> ::::::");
		List<AirlineDO> airlinesList = null;
		Session session = null;
		Criteria airlineCriteria=null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			airlineCriteria = session.createCriteria(AirlineDO.class);
			airlinesList=airlineCriteria.list();
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::getAirlineDetails::" , e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.info("TransportCommonDAOImpl :: getAirlineDetails() :: End --------> ::::::");
		return airlinesList;
	}

}
