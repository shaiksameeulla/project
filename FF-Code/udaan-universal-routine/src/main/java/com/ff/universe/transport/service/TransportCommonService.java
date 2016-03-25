package com.ff.universe.transport.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.transport.AirlineTO;
import com.ff.transport.FlightTO;
import com.ff.transport.TrainTO;
import com.ff.transport.TransportModeTO;
import com.ff.transport.VehicleTO;

/**
 * @author narmdr
 *
 */
public interface TransportCommonService {

	/**@Desc:return All Transport Mode List
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<LabelValueBean> getAllTransportModeList() throws CGBusinessException, CGSystemException;

	/**@Desc:return Vehicle No List By Office Id
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<LabelValueBean> getVehicleNoListByOfficeId(Integer officeId) 
			throws CGBusinessException, CGSystemException;

	/**@Desc:return Flight Details
	 * @param flightTO
	 * @return
	 * @throws CGSystemException
	 */
	List<FlightTO> getFlightDetails(FlightTO flightTO) throws CGBusinessException, CGSystemException; 	 
	
	/**@Desc:return Train Details
	 * @param trainTO
	 * @return
	 * @throws CGSystemException
	 */
	List<TrainTO> getTrainDetails(TrainTO trainTO)  throws CGBusinessException, CGSystemException;
	
	/**@Desc:return Vehicle Details
	 * @param vehicleTO
	 * @return
	 * @throws CGSystemException
	 */
	List<VehicleTO> getVehicleDetails(VehicleTO vehicleTO) throws CGBusinessException, CGSystemException;

	/**@Desc:return Transport Mode
	 * @param transportModeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	TransportModeTO getTransportMode(TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException;

	List<VehicleTO> getVehicleDetailsByRHOOfcAndRegion(VehicleTO vehicleTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the airline details.
	 *
	 * @param airlineTO the airline to
	 * @return the airline details
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	List<AirlineTO> getAirlineDetails(AirlineTO airlineTO)
			throws CGBusinessException, CGSystemException;

}
