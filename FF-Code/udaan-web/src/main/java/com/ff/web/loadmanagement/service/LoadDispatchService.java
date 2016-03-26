package com.ff.web.loadmanagement.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

/**
 * The Interface LoadDispatchService.
 *
 * @author narmdr
 */
public interface LoadDispatchService {

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
	OfficeTO getOfficeByOfficeId(final Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the vehicle no list by office id.
	 *
	 * @param officeId the office id
	 * @return the vehicle no list by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<LabelValueBean> getVehicleNoListByOfficeId(final Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the route id by origin city id and dest city id.
	 *
	 * @param originCityId the origin city id
	 * @param destCityId the dest city id
	 * @return the route id by origin city id and dest city id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Integer getRouteIdByOriginCityIdAndDestCityId(final Integer originCityId,final Integer destCityId)
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
			final Integer transportModeId) throws CGBusinessException, CGSystemException;

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
			final Integer routeId, final Integer transportModeId,final Integer serviceByTypeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update load dispatch.
	 *
	 * @param loadMovementTO the load movement to
	 * @return the load movement to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementTO saveOrUpdateLoadDispatch(final LoadMovementTO loadMovementTO)
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
	LoadMovementTO getLoadMovementTOByGatePassNumber(final String gatePassNumber)
			 throws CGBusinessException, CGSystemException;
	
	/**
	 * get All the destination Offices By officeTypeId, origin cityId and not origin office.
	 * If officeType is branch then get all the offices of logged in Office city.
	 * If officeType is hub then get all the offices of India.
	 *
	 * @param officeTO the office to
	 * @return the destination offices
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getDestinationOffices(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the city.
	 *
	 * @param cityTO the city to
	 * @return the city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	CityTO getCity(final CityTO cityTO) throws CGBusinessException, CGSystemException;

	/**
	 * Validate transshipment route.
	 *
	 * @param transshipmentRouteTO the transshipment route to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean validateTransshipmentRoute(final TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number.
	 *
	 * @param manifestTO the manifest to
	 * @return the manifest to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ManifestTO validateManifestNumber(final ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the load movement to by gate pass number form printing data.
	 *
	 * @param gatePassNumber the gate pass number
	 * @return the load movement to by gate pass number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementTO getLoadMovementTOByGatePassNumber4Print(final String gatePassNumber)
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
			final TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException;
}
