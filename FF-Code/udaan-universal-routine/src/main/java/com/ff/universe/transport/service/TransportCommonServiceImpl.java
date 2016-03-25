/**
 * 
 */
package com.ff.universe.transport.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.ff.domain.transport.AirlineDO;
import com.ff.domain.transport.FlightDO;
import com.ff.domain.transport.TrainDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.transport.AirlineTO;
import com.ff.transport.FlightTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.VehicleTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.transport.dao.TransportCommonDAO;

/**
 * @author narmdr
 *
 */
public class TransportCommonServiceImpl implements TransportCommonService {

	private TransportCommonDAO transportCommonDAO;
	
	
	public void setTransportCommonDAO(TransportCommonDAO transportCommonDAO) {
		this.transportCommonDAO = transportCommonDAO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.transport.service.TransportCommonService#getAllTransportModeList()
	 */
	@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> transportModeList = new ArrayList<LabelValueBean>();
		List<TransportModeDO> transportModeDOList = transportCommonDAO.getAllTransportModeList();
		if(!CGCollectionUtils.isEmpty(transportModeDOList)){
		for (TransportModeDO transportModeDO : transportModeDOList) {
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(transportModeDO.getTransportModeDesc());
			lvb.setValue(transportModeDO.getTransportModeId() + CommonConstants.TILD
					+ transportModeDO.getTransportModeCode() + CommonConstants.TILD
					+ transportModeDO.getTransportModeDesc());
			transportModeList.add(lvb);
		} 
		}else{
			throw new CGBusinessException();
		}
		return transportModeList;
	}

	@Override
	public List<LabelValueBean> getVehicleNoListByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		List<LabelValueBean> vehicleNoList = new ArrayList<LabelValueBean>();
		List<VehicleDO> vehicleDOList = transportCommonDAO.getVehicleListByOfficeId(officeId);
		
		for (VehicleDO vehicleDO : vehicleDOList) {
			LabelValueBean lvb = new LabelValueBean();	
			lvb.setLabel(vehicleDO.getRegNumber());
			lvb.setValue(vehicleDO.getVehicleId().toString() + CommonConstants.TILD
					+ vehicleDO.getRegNumber());
			vehicleNoList.add(lvb);
		}
		LabelValueBean lvb = new LabelValueBean();
		lvb.setLabel(UdaanCommonConstants.OTHERS_VEHICLE);
		lvb.setValue(UdaanCommonConstants.OTHERS_VEHICLE_CODE);
		vehicleNoList.add(lvb);
		
		return vehicleNoList;
	}

	@Override
	public List<FlightTO> getFlightDetails(FlightTO flightTO)
			throws CGBusinessException, CGSystemException {

		List<FlightTO> flightTOList = new ArrayList<FlightTO>();
		List<FlightDO> flightDOList = transportCommonDAO.getFlightDetails(flightTO);
		
		if(!CGCollectionUtils.isEmpty(flightDOList)){
		for(FlightDO flightDO: flightDOList){
				flightTO = new FlightTO();
				flightTO.setFlightId(flightDO.getFlightId());
				flightTO.setFlightNumber(flightDO.getFlightNumber());
				flightTO.setAirlineCode(flightDO.getAirlineCode());
				flightTOList.add(flightTO);
		}
		}
		return flightTOList;
	}

	@Override
	public List<TrainTO> getTrainDetails(TrainTO trainTO)
			throws CGBusinessException, CGSystemException {
		List<TrainTO> trainTOList = new ArrayList<TrainTO>();
		List<TrainDO> trainDOList = transportCommonDAO.getTrainDetails(trainTO);
		
		if(!CGCollectionUtils.isEmpty(trainDOList)){
		for(TrainDO trainDO: trainDOList){
			trainTO = new TrainTO();
			trainTO.setTrainId(trainDO.getTrainId());
			trainTO.setTrainNumber(trainDO.getTrainNumber());
			trainTO.setTrainCode(trainDO.getTrainCode());
			trainTO.setTrainName(trainDO.getTrainName());
				trainTOList.add(trainTO);				
		}
		}
		return trainTOList;
	}

	@Override
	public List<VehicleTO> getVehicleDetails(VehicleTO vehicleTO)
			throws CGBusinessException, CGSystemException {
		List<VehicleTO> vehicleTOList = new ArrayList<VehicleTO>();
		List<VehicleDO> vehicleDOList = transportCommonDAO.getVehicleDetails(vehicleTO);
		
		if(!CGCollectionUtils.isEmpty(vehicleDOList)){
		for(VehicleDO vehicleDO: vehicleDOList){
			vehicleTO = new VehicleTO();
			vehicleTO.setVehicleId(vehicleDO.getVehicleId());
			vehicleTO.setVehicleType(vehicleDO.getVehicleType());
			vehicleTO.setRegNumber(vehicleDO.getRegNumber());
				vehicleTOList.add(vehicleTO);				
		}
		}
		return vehicleTOList;
	}
	
    /** 
     * Get TransportMode by transportModeCode
     * <p>
     * <ul>
     * <li>   if there exists a TransportMode by transportModeCode, 
     * 			the method will return a valid TransportModeTO 
     * 			with all the details filled else return null.
     * </ul>
     * <p>
     *
     * @param TransportModeTO :: The input TransportModeTO will be passed with the following details filled in -
     *						<ul>
     *							<li>transportModeCode
     *						</ul>
     * @return TransportModeTO :: TransportModeTO will get filled with all the TransportMode details.
     * 
     * @author	R Narmdeshwar
     *      
     */
	@Override
	public TransportModeTO getTransportMode(TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException {
		TransportModeDO transportModeDO = transportCommonDAO.getTransportMode(transportModeTO);
		TransportModeTO transportModeTO1 = null;
		if(transportModeDO!=null){
			transportModeTO1 = new TransportModeTO();
			CGObjectConverter.createToFromDomain(transportModeDO, transportModeTO1);
		}
		return transportModeTO1;
	}

	@Override
	public List<VehicleTO> getVehicleDetailsByRHOOfcAndRegion(
			VehicleTO vehicleTO) throws CGBusinessException, CGSystemException {
		List<VehicleTO> vehicleTOList = new ArrayList<VehicleTO>();
		List<VehicleDO> vehicleDOList = transportCommonDAO.getVehicleDetailsByRHOOfcAndRegion(vehicleTO);
		
		if(!CGCollectionUtils.isEmpty(vehicleDOList)){
			for(VehicleDO vehicleDO: vehicleDOList){
				vehicleTO = new VehicleTO();
				vehicleTO.setVehicleId(vehicleDO.getVehicleId());
				vehicleTO.setVehicleType(vehicleDO.getVehicleType());
				vehicleTO.setRegNumber(vehicleDO.getRegNumber());
				
				vehicleTO.setRhoOfcId(vehicleDO.getRegionalOfficeDO().getOfficeId());
//				vehicleTO.setRegionId(vehicleDO.getRegionalOfficeDO().get);
				vehicleTOList.add(vehicleTO);				
			}
		}
		return vehicleTOList;
	}
	
	@Override
	public List<AirlineTO> getAirlineDetails(AirlineTO airlineTO) throws CGBusinessException, CGSystemException {
		AirlineDO airlineDO= new AirlineDO();
		List<AirlineTO> airlineTOList=null;
		if(airlineTO!=null){
			CGObjectConverter.createDomainFromTo(airlineTO, airlineDO);
		}
		List<AirlineDO> airlineDOList = transportCommonDAO.getAirlineDetails(airlineDO);

		if(!CGCollectionUtils.isEmpty(airlineDOList)){
			airlineTOList= new ArrayList<AirlineTO>(airlineDOList.size());
			for(AirlineDO airlineDo: airlineDOList){
				AirlineTO airlineTo= new AirlineTO();
				airlineTo.setAirlineId(airlineDo.getAirlineId());
				airlineTo.setAirlineCode(airlineDo.getAirlineCode());
				airlineTo.setAirlineName(airlineDo.getAirlineName());
				airlineTOList.add(airlineTo);
			}
		}
		return airlineTOList;
	}


}

