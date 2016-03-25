package com.ff.universe.loadmanagement.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.LoadReceiveLocalTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.transport.TransportModeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoadManagementCommonService.
 *
 * @author narmdr
 */
public interface LoadManagementCommonService {

	/**
	 * Gets the all transport mode list.
	 *
	 * @return the all transport mode list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LabelValueBean> getAllTransportModeList() throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the office type list for dispatch.
	 *
	 * @return the office type list for dispatch
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LabelValueBean> getOfficeTypeListForDispatch() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the office by office id.
	 *
	 * @param officeId the office id
	 * @return the office by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	OfficeTO getOfficeByOfficeId(Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the vehicle no list by office id.
	 *
	 * @param officeId the office id
	 * @return the vehicle no list by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LabelValueBean> getVehicleNoListByOfficeId(Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the route id by origin city id and dest city id.
	 *
	 * @param originCityId the origin city id
	 * @param destCityId the dest city id
	 * @return the route id by origin city id and dest city id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId, Integer destCityId) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the service by type list by transport mode id.
	 *
	 * @param transportModeId the transport mode id
	 * @return the service by type list by transport mode id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the trip serviced by to list by route id transport mode id service by type id.
	 *
	 * @param routeId the route id
	 * @param transportModeId the transport mode id
	 * @param serviceByTypeId the service by type id
	 * @return the trip serviced by to list by route id transport mode id service by type id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<TripServicedByTO> getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
					 throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignment type to list.
	 *
	 * @return the consignment type to list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ConsignmentTypeTO> getConsignmentTypeTOList() throws CGBusinessException, CGSystemException;

	/**
	 * Gets the load movement to by gate pass number.
	 *
	 * @param gatePassNumber the gate pass number
	 * @return the load movement to by gate pass number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementTO getLoadMovementTOByGatePassNumber(String gatePassNumber)
			 throws CGBusinessException, CGSystemException;
	
	/**
	 * Load movement transfer converter.
	 *
	 * @param loadMovementDO the load movement do
	 * @return the load movement to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public LoadMovementTO loadMovementTransferConverter(
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the destination offices.
	 *
	 * @param officeTO the office to
	 * @return the destination offices
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getDestinationOffices(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;	

	/**
	 * Gets the city.
	 *
	 * @param cityTO the city to
	 * @return the city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	CityTO getCity(CityTO cityTO) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the transshipment route.
	 *
	 * @param transshipmentRouteTO the transshipment route to
	 * @return the transshipment route
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	TransshipmentRouteTO getTransshipmentRoute(TransshipmentRouteTO transshipmentRouteTO) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Update manifest weight.
	 *
	 * @param manifestTO the manifest to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the transport mode.
	 *
	 * @param transportModeTO the transport mode to
	 * @return the transport mode
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	TransportModeTO getTransportMode(TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the load receive local to.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveLocalTO getLoadReceiveLocalTO(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Validate weight tolerance.
	 *
	 * @param loadManagementTO the load management to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void validateWeightTolerance(LoadManagementTO loadManagementTO) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number4 dispatch.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestTO validateManifestNumber4Dispatch(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;


	/**
	 * Gets the origin offices.
	 *
	 * @param officeTO the office to
	 * @return the origin offices
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getOriginOffices(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number4 receive local.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load receive manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveManifestValidationTO validateManifestNumber4ReceiveLocal(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the process.
	 *
	 * @param processTO the process to
	 * @return the process
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	ProcessTO getProcess(ProcessTO processTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Checks if is receive number exist.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return true, if is receive number exist
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean isReceiveNumberExist(LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number4 receive outstation.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load receive manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveManifestValidationTO validateManifestNumber4ReceiveOutstation(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException;
	/**
	 * Gets the load movement to by gate pass number.
	 *
	 * @param gatePassNumber the gate pass number
	 * @return the load movement to by gate pass number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementTO getLoadMovementTOByGatePassNumber4Print(String gatePassNumber)
			 throws CGBusinessException, CGSystemException;
	
	/**
	 * Print the load receive local to.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveLocalTO printLoadReceiveLocalTO(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Creates the process number.
	 *
	 * @param processTO the process to
	 * @param officeTO the office to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the trip serviced by t os for transport.
	 *
	 * @param tripServicedByTO the trip serviced by to
	 * @return the trip serviced by t os for transport
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<TripServicedByTO> getTripServicedByTOsForTransport(
			TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Calc and get operating level.
	 *
	 * @param loadManagementTO the load management to
	 * @param manifestTO the manifest to
	 * @return the integer
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer calcAndGetOperatingLevel(LoadManagementTO loadManagementTO,
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Sets the operating level and office to manifest.
	 *
	 * @param operatingLevel the operating level
	 * @param manifestTO the manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void setOperatingLevelAndOfficeToManifest(Integer operatingLevel,
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Send sms.
	 *
	 * @param loadMovementTO the load movement to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void sendSMS(LoadMovementTO loadMovementTO) throws CGBusinessException,
			CGSystemException;
	
}
