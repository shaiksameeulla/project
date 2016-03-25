package com.ff.universe.transport.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.transport.AirlineDO;
import com.ff.domain.transport.FlightDO;
import com.ff.domain.transport.TrainDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.transport.FlightTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.VehicleTO;

/**
 * @author narmdr
 *
 */
public interface TransportCommonDAO {

	/**@Desc:return All Transport Mode List
	 * @return List<TransportModeDO>
	 * @throws CGSystemException
	 */
	List<TransportModeDO> getAllTransportModeList() throws CGSystemException;

	/**@Desc:return Vehicle No List By Office Id
	 * @param officeId
	 * @return:List<VehicleDO>
	 * @throws CGSystemException
	 */
	List<VehicleDO> getVehicleListByOfficeId(Integer officeId) throws CGSystemException;

	/**@Desc:return Flight Details
	 * @param flightTO
	 * @return List<FlightDO>
	 * @throws CGSystemException
	 */
	List<FlightDO> getFlightDetails(FlightTO flightTO) throws CGSystemException; 	 
	
	/**@Desc:return Train Details
	 * @param trainTO
	 * @return
	 * @throws CGSystemException
	 */
	List<TrainDO> getTrainDetails(TrainTO trainTO)  throws CGSystemException;
	
	/**@Desc:return Vehicle Details
	 * @param vehicleTO
	 * @return List<VehicleDO>
	 * @throws CGSystemException
	 */
	List<VehicleDO> getVehicleDetails(VehicleTO vehicleTO) throws CGSystemException;	
	
	/**@Desc:return Transport Mode
	 * @param transportModeTO
	 * @return TransportModeDO
	 * @throws CGSystemException
	 */
	TransportModeDO getTransportMode(TransportModeTO transportModeTO) throws CGSystemException;

	List<VehicleDO> getVehicleDetailsByRHOOfcAndRegion(VehicleTO vehicleTO) throws CGSystemException;

	List<AirlineDO> getAirlineDetails(AirlineDO airlineDO)
			throws CGSystemException;

	List<VehicleDO> getAllVehicleDetailsByRHOOfcAndRegion(VehicleTO vehicleTO)
			throws CGSystemException;

	List<VehicleDO> getVehicleAvailablity(VehicleTO vehicleTO)
			throws CGSystemException;
}
