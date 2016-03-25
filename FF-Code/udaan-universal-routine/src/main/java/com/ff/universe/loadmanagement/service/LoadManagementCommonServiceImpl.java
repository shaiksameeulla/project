package com.ff.universe.loadmanagement.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.sms.SmsSenderUtil;
import com.capgemini.lbs.framework.to.SmsSenderTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.routeserviced.ServicedByDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadDispatchDetailsTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.LoadReceiveDetailsTO;
import com.ff.loadmanagement.LoadReceiveLocalTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveOutstationDetailsTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.transport.TransportModeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.loadmanagement.constant.LoadManagementUniversalConstants;
import com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.universe.transport.service.TransportCommonService;
import com.ff.universe.util.UdaanContextService;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadManagementCommonServiceImpl.
 *
 * @author narmdr
 */
public class LoadManagementCommonServiceImpl implements
		LoadManagementCommonService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadManagementCommonServiceImpl.class);
	
	/** The load management common dao. */
	private LoadManagementCommonDAO loadManagementCommonDAO;
	
	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;
	
	/** The transport common service. */
	private TransportCommonService transportCommonService;
	
	/** The route serviced common service. */
	private RouteServicedCommonService routeServicedCommonService;
	
	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;
	
	/** The geography common service. */
	private GeographyCommonService geographyCommonService;
	
	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The udaan context service. */
	private transient UdaanContextService udaanContextService;
	
	/** The stock universal service. */
	@SuppressWarnings("unused")
	private StockUniversalService stockUniversalService;
	
	/** The tracking universal service. */
	private TrackingUniversalService trackingUniversalService;
	
	/**
	 * Sets the load management common dao.
	 *
	 * @param loadManagementCommonDAO the new load management common dao
	 */
	public void setLoadManagementCommonDAO(
			LoadManagementCommonDAO loadManagementCommonDAO) {
		this.loadManagementCommonDAO = loadManagementCommonDAO;
	}
	
	/**
	 * Sets the organization common service.
	 *
	 * @param organizationCommonService the new organization common service
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}	
	
	/**
	 * Sets the transport common service.
	 *
	 * @param transportCommonService the new transport common service
	 */
	public void setTransportCommonService(
			TransportCommonService transportCommonService) {
		this.transportCommonService = transportCommonService;
	}	
	
	/**
	 * Sets the route serviced common service.
	 *
	 * @param routeServicedCommonService the new route serviced common service
	 */
	public void setRouteServicedCommonService(
			RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}	
	
	/**
	 * Sets the service offering common service.
	 *
	 * @param serviceOfferingCommonService the new service offering common service
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}	
	
	/**
	 * Sets the geography common service.
	 *
	 * @param geographyCommonService the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}	
	
	/**
	 * Sets the out manifest universal service.
	 *
	 * @param outManifestUniversalService the new out manifest universal service
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}
	
	/**
	 * Sets the stock universal service.
	 *
	 * @param stockUniversalService the new stock universal service
	 */
	public void setStockUniversalService(StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}	
	
	/**
	 * Sets the tracking universal service.
	 *
	 * @param trackingUniversalService the new tracking universal service
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}	

	/**
	 * @param udaanContextService the udaanContextService to set
	 */
	public void setUdaanContextService(UdaanContextService udaanContextService) {
		this.udaanContextService = udaanContextService;
	}

	/**
	 * Gets the all transport mode list.
	 *
	 * @return the all transport mode list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		return transportCommonService.getAllTransportModeList();
	}
	
	/**
	 * Gets the office type list for dispatch.
	 *
	 * @return the office type list for dispatch
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<LabelValueBean> getOfficeTypeListForDispatch()
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeTypeListForDispatch();
	}
	
	/**
	 * Gets the office by office id.
	 *
	 * @param officeId the office id
	 * @return the office by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public OfficeTO getOfficeByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(officeId);
	}
	
	/**
	 * Gets the vehicle no list by office id.
	 *
	 * @param officeId the office id
	 * @return the vehicle no list by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<LabelValueBean> getVehicleNoListByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return transportCommonService.getVehicleNoListByOfficeId(officeId);
	}
	
	/**
	 * Gets the route id by origin city id and dest city id.
	 *
	 * @param originCityId the origin city id
	 * @param destCityId the dest city id
	 * @return the route id by origin city id and dest city id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGBusinessException, CGSystemException {
		return routeServicedCommonService.getRouteIdByOriginCityIdAndDestCityId(
				originCityId, destCityId);
	}
	
	/**
	 * Gets the service by type list by transport mode id.
	 *
	 * @param transportModeId the transport mode id
	 * @return the service by type list by transport mode id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<LabelValueBean> getServiceByTypeListByTransportModeId(
			Integer transportModeId) throws CGBusinessException,
			CGSystemException {
		return routeServicedCommonService.getServiceByTypeListByTransportModeId(transportModeId);
	}
	
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
	@Override
	public List<TripServicedByTO> getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			Integer routeId, Integer transportModeId, Integer serviceByTypeId)
			throws CGBusinessException, CGSystemException {
		return routeServicedCommonService.
				getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
				routeId, transportModeId, serviceByTypeId);
	}
	
	/**
	 * Gets the consignment type to list.
	 *
	 * @return the consignment type to list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypeTOList()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}
	
	/**
	 * Gets the load movement to by gate pass number.
	 *
	 * @param gatePassNumber the gate pass number
	 * @return the load movement to by gate pass number
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public LoadMovementTO getLoadMovementTOByGatePassNumber(
			String gatePassNumber) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadMovementTOByGatePassNumber::START------------>:::::::");
		LoadMovementTO LoadMovementTO = null;
		LoadMovementDO loadMovementDO = loadManagementCommonDAO.getLoadMovementDOByGatePassNumber(gatePassNumber);
		if(loadMovementDO!=null){
			LoadMovementTO = loadMovementTransferConverter(loadMovementDO);
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadMovementTOByGatePassNumber::END------------>:::::::");
		return LoadMovementTO;
	}
	
	/**
	 * Load movement transfer converter.
	 *
	 * @param loadMovementDO the load movement do
	 * @return the load movement to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public LoadMovementTO loadMovementTransferConverter(
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::loadMovementTransferConverter::START------------>:::::::");
		LoadMovementTO loadMovementTO = new LoadMovementTO();

		loadMovementTO.setLoadMovementId(loadMovementDO.getLoadMovementId());
		loadMovementTO.setMovementDirection(loadMovementDO.getMovementDirection());
		loadMovementTO.setGatePassNumber(loadMovementDO.getGatePassNumber());
		loadMovementTO.setProcessNumber(loadMovementDO.getProcessNumber());
		
		if(loadMovementDO.getProcessDO()!=null){
			loadMovementTO.setProcessId(loadMovementDO.getProcessDO().getProcessId());
		}
		//loadMovementTO.setVehicleType(loadMovementDO.getVehicleType());
		if(loadMovementDO.getVehicleDO()!=null){
			loadMovementTO.setVehicleNumber(loadMovementDO.getVehicleDO().getVehicleId() + 
					CommonConstants.TILD + loadMovementDO.getVehicleDO().getRegNumber());			
		}else if(StringUtils.isNotBlank(loadMovementDO.getVehicleRegNumber())){
			loadMovementTO.setVehicleNumber(LoadManagementUniversalConstants.OTHERS_VEHICLE_CODE);			
			loadMovementTO.setOtherVehicleNumber(loadMovementDO.getVehicleRegNumber());			
		}
		loadMovementTO.setDriverName(loadMovementDO.getDriverName());
		if(loadMovementDO.getLoadingDate()!=null){
			loadMovementTO.setDispatchDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
					(loadMovementDO.getLoadingDate()));			
		}
		loadMovementTO.setLoadingTime(loadMovementDO.getLoadingTime());
		
		if(loadMovementDO.getOriginOfficeDO()!=null){
			loadMovementTO.setOriginOfficeId(loadMovementDO.getOriginOfficeDO().getOfficeId());
			loadMovementTO.setOriginOffice(loadMovementDO.getOriginOfficeDO().getOfficeCode() + 
					CommonConstants.HYPHEN + loadMovementDO.getOriginOfficeDO().getOfficeName());
			OfficeTO regionalOfficeTO = organizationCommonService.getOfficeDetails(
					loadMovementDO.getOriginOfficeDO().getReportingRHO());
			loadMovementTO.setRegionalOffice(regionalOfficeTO.getOfficeCode() + 
					CommonConstants.HYPHEN + regionalOfficeTO.getOfficeName());
		}

		if(loadMovementDO.getDestOfficeDO()!=null){
			loadMovementTO.setDestOffice(loadMovementDO.getDestOfficeDO().getOfficeId() + 
					CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getCityId() +
					CommonConstants.TILD +  loadMovementDO.getDestOfficeDO().getOfficeCode() +
					CommonConstants.HYPHEN +  loadMovementDO.getDestOfficeDO().getOfficeName());
			if(loadMovementDO.getDestOfficeDO().getOfficeTypeDO()!=null){
				loadMovementTO.setDestOfficeType(loadMovementDO.getDestOfficeDO().getOfficeTypeDO().getOffcTypeId() + 
						CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getOfficeTypeDO().getOffcTypeCode());
			}
		}

		final TransportModeDO transportModeDO = loadMovementDO.getTransportModeDO();
		String transportModeCode = null;// A, R, T
		if(transportModeDO!=null){
			transportModeCode = transportModeDO.getTransportModeCode();
			loadMovementTO.setTransportMode(
					transportModeDO.getTransportModeId() +
					CommonConstants.TILD + 
					transportModeDO.getTransportModeCode() +
					CommonConstants.TILD + 
					transportModeDO.getTransportModeDesc());
		}
		
		//Start of setting  TripServicedBy
		if(loadMovementDO.getTripServicedByDO()!=null){

			loadMovementTO.setTripServicedById(loadMovementDO.getTripServicedByDO().getTripServicedById());
			String serviceByTypeCode = null; //C, O, B, D
			//String servicedByType = null; //E(Emp), V(LoadMovementVendor)
			

			ServicedByDO servicedByDO = loadMovementDO.getTripServicedByDO().getServicedByDO();

			if(servicedByDO!=null){
				if(servicedByDO.getServiceByTypeDO()!=null){
					serviceByTypeCode = servicedByDO.getServiceByTypeDO().getServiceByTypeCode();
					loadMovementTO.setServiceByType(servicedByDO.getServiceByTypeDO().getServiceByTypeDesc() +
							CommonConstants.TILD + servicedByDO.getServiceByTypeDO().getServiceByTypeCode());
				}
				if(!StringUtils.equals(serviceByTypeCode, LoadManagementUniversalConstants.DIRECT_CODE)){
					if(StringUtils.equals(servicedByDO.getServicedByType(), 
							LoadManagementUniversalConstants.VENDOR_CODE) && 
							servicedByDO.getLoadMovementVendorDO()!=null){
						loadMovementTO.setLoadMovementVendor(
								servicedByDO.getLoadMovementVendorDO().getBusinessName());
					}else if(StringUtils.equals(servicedByDO.getServicedByType(), 
							LoadManagementUniversalConstants.Employee_CODE) &&
							servicedByDO.getEmployeeDO()!=null){
						loadMovementTO.setLoadMovementVendor(
								servicedByDO.getEmployeeDO().getFirstName() + 
								servicedByDO.getEmployeeDO().getLastName());
					}
				}				
			}
			
			//Road
			//if(!StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.ROAD_CODE)){

			if(StringUtils.isBlank(loadMovementDO.getVehicleType()) &&
					loadMovementDO.getTripServicedByDO().getTripDO()!=null){

				loadMovementTO.setExpectedDeparture(
						loadMovementDO.getTripServicedByDO().getTripDO().getDepartureTime());
				loadMovementTO.setExpectedArrival(
						loadMovementDO.getTripServicedByDO().getTripDO().getArrivalTime());
				
				//start
				if(StringUtils.isNotBlank(loadMovementDO.getRouteServicedTransportType())){
					
					if(loadMovementDO.getRouteServicedTransportType().equals
							(LoadManagementUniversalConstants.OTHERS_CODE)){
						loadMovementTO.setTransportNumber(LoadManagementUniversalConstants.OTHERS_CODE);
						loadMovementTO.setOtherTransportNumber(loadMovementDO.getRouteServicedTransportNumber());
						
					}else if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO()!=null){
						if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.AIR_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getFlightDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getFlightDO().getFlightNumber());
							}
						}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.TRAIN_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getTrainDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getTrainDO().getTrainNumber());
							}							
						}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.ROAD_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getVehicleDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getVehicleDO().getRegNumber());
							}							
						}
					}						
				}//end
				
			}
			
		}//End of setting  TripServicedBy
		

		List<LoadDispatchDetailsTO> loadDispatchDetailsTOs = new ArrayList<LoadDispatchDetailsTO>();
		if(loadMovementDO.getLoadConnectedDOs()!=null && loadMovementDO.getLoadConnectedDOs().size()>0){
			
			/*int rowCount = loadMovementDO.getLoadConnectedDOs().size();
			Integer[] loadConnectedId = new Integer[rowCount];
			String[] loadNumber = new String[rowCount];
			String[] docType = new String[rowCount];
			Double[] weight = new Double[rowCount];
			Double[] cdWeight = new Double[rowCount];
			String[] lockNumber = new String[rowCount];
			String[] tokenNumber = new String[rowCount];
			String[] remarks = new String[rowCount];
			Integer[] manifestId = new Integer[rowCount];
			Double[] manifestWeight = new Double[rowCount];
			String[] manifestDestCity = new String[rowCount];//cityId~code~name
			String[] manifestDestCityDetails = new String[rowCount];			
			//int i=0;*/

			Set<LoadConnectedDO> loadConnectedDOList = loadMovementDO.getLoadConnectedDOs();
			for (LoadConnectedDO loadConnectedDO : loadConnectedDOList) {
				LoadDispatchDetailsTO loadDispatchDetailsTO = new LoadDispatchDetailsTO();
				
				/*loadConnectedId[i] = loadConnectedDO.getLoadConnectedId();
				weight[i] = loadConnectedDO.getDispatchWeight();
				cdWeight[i] = loadConnectedDO.getConnectWeight();
				remarks[i] = loadConnectedDO.getRemarks();
				tokenNumber[i] = loadConnectedDO.getTokenNumber();*/
				
				loadDispatchDetailsTO.setLoadConnectedId(loadConnectedDO.getLoadConnectedId());
				loadDispatchDetailsTO.setWeight(loadConnectedDO.getDispatchWeight());
				loadDispatchDetailsTO.setCdWeight(loadConnectedDO.getConnectWeight());
				loadDispatchDetailsTO.setTokenNumber(loadConnectedDO.getTokenNumber());
				loadDispatchDetailsTO.setRemarks(loadConnectedDO.getRemarks());
								
				if(loadConnectedDO.getManifestDO()!=null){

					loadDispatchDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
					loadDispatchDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
					loadDispatchDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
					loadDispatchDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
					
					/*//manifestId[i] = loadConnectedDO.getManifestDO().getManifestId();
					loadNumber[i] = loadConnectedDO.getManifestDO().getManifestNo();
					manifestWeight[i] = loadConnectedDO.getManifestDO().getManifestWeight();
					lockNumber[i] = loadConnectedDO.getManifestDO().getBagLockNo();*/
					if(loadConnectedDO.getManifestDO().getManifestLoadContent()!=null){
						loadDispatchDetailsTO.setDocType(loadConnectedDO.getManifestDO().
								getManifestLoadContent().getConsignmentId() + "");
						//docType[i] = loadConnectedDO.getManifestDO().getManifestLoadContent().getConsignmentId() + "";						
					}
					if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
						/*manifestDestCity[i] = loadConnectedDO.getManifestDO().getDestinationCity().getCityCode();
						manifestDestCityDetails[i] = loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName();*/

						loadDispatchDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().
								getDestinationCity().getCityCode());
						loadDispatchDetailsTO.setManifestDestCityDetails(
								loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName());
					}
				}
				//i++;
				loadDispatchDetailsTOs.add(loadDispatchDetailsTO);
			}
			/*loadMovementTO.setLoadConnectedId(loadConnectedId);
			loadMovementTO.setLoadNumber(loadNumber);
			loadMovementTO.setDocType(docType);
			loadMovementTO.setWeight(weight);
			loadMovementTO.setCdWeight(cdWeight);
			loadMovementTO.setLockNumber(lockNumber);
			loadMovementTO.setTokenNumber(tokenNumber);
			loadMovementTO.setRemarks(remarks);
			loadMovementTO.setManifestId(manifestId);
			loadMovementTO.setManifestDestCity(manifestDestCity);
			loadMovementTO.setManifestDestCityDetails(manifestDestCityDetails);
			loadMovementTO.setManifestWeight(manifestWeight);
			//loadMovementTO.set;
			loadMovementTO.setManifestId(manifestId);	*/		
		}
		Collections.sort(loadDispatchDetailsTOs);
		loadMovementTO.setLoadDispatchDetailsTOs(loadDispatchDetailsTOs);
		LOGGER.trace("LoadManagementCommonServiceImpl::loadMovementTransferConverter::END------------>:::::::");
		return loadMovementTO;
	}		

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
	@Override
	public List<OfficeTO> getDestinationOffices(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficesByOffice(officeTO);
	}
	
	/**
	 * Gets the city by id or code.
	 *
	 * @param cityTO the city to
	 * @return the city
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getCity(cityTO);
	}
	
	/**
	 * Gets the transshipment route.
	 *
	 * @param transshipmentRouteTO the transshipment route to
	 * @return the transshipment route
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public TransshipmentRouteTO getTransshipmentRoute(
			TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException {		
		return routeServicedCommonService.getTransshipmentRoute(transshipmentRouteTO);
	}	
	
	/**
	 * validate Weight Tolerance. To achieve this, the following
	 * service is to be called:
	 *
	 * @param loadManagementTO the load management to
	 * @return if the WeightTolerance is "Y" then update manifest weight.
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author	R Narmdeshwar
	 */
	@Override
	public void validateWeightTolerance(LoadManagementTO loadManagementTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::validateWeightTolerance::START------------>:::::::");
		ManifestTO manifestTO = new ManifestTO();
		
		final int length = loadManagementTO.getLoadNumber().length;
		for(int i=0; i<length;i++){
			if(loadManagementTO.getWeightTolerance()[i].equals(CommonConstants.YES)){
				manifestTO.setManifestId(loadManagementTO.getManifestId()[i]);
				manifestTO.setManifestWeight(loadManagementTO.getWeight()[i]);
				
				//added operating level & office
				CityTO destinationCityTO = null;
				if(StringUtils.isNotBlank(loadManagementTO.getManifestDestCityDetails()[i])){
					destinationCityTO = new CityTO();
					destinationCityTO.setCityId(Integer.valueOf(
							loadManagementTO.getManifestDestCityDetails()[i].split
							(CommonConstants.TILD)[0]));					
				}
				manifestTO.setDestinationCityTO(destinationCityTO);
				Integer operatingLevel = calcAndGetOperatingLevel(loadManagementTO, manifestTO);
				setOperatingLevelAndOfficeToManifest(operatingLevel, manifestTO);
				
				updateManifestWeight(manifestTO);
			}
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::validateWeightTolerance::END------------>:::::::");
	}	
	
	/**
	 * Update manifest weight by manifestId. To achieve this, the following
	 * service is to be called:
	 *
	 * @param manifestTO the manifest to
	 * @return if the weight updated successfully return true else return false
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author	R Narmdeshwar
	 */
	@Override
	public boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException {
		return outManifestUniversalService.updateManifestWeight(manifestTO);		
	}
	
	/**
	 * Gets the transport mode.
	 *
	 * @param transportModeTO the transport mode to
	 * @return the transport mode
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public TransportModeTO getTransportMode(TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException {
		return transportCommonService.getTransportMode(transportModeTO);
	}
	
	/**
	 * get LoadReceiveLocalTO by gatePassNumber, regionalOfficeId and road mode only.
	 * <p>
	 * <ul>
	 * <li>Check LoadMovement exist in receive or not by gatePassNumber and regionalOfficeId.
	 * <li>If not check LoadMovement exist in dispatch or not by gatePassNumber and regionalOfficeId.
	 * <li>
	 * <li>
	 * </ul>
	 * <p>
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return LoadReceiveLocalTO :: If all the validations are passed then return LoadReceiveLocalTO will
	 * get filled with all the details required for Load Receive Local.
	 * 
	 * else throw CGBusinessException.
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author R Narmdeshwar
	 */
	@Override
	public LoadReceiveLocalTO getLoadReceiveLocalTO(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadReceiveLocalTO::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = null;
		LoadMovementDO loadMovementDO = null;

		loadMovementDO = loadManagementCommonDAO.
				getLoadMovementForReceiveLocalByReceive(loadManagementValidationTO);

		if(loadMovementDO==null){
			loadMovementDO = loadManagementCommonDAO.
					getLoadMovementForReceiveLocalByDispatch(loadManagementValidationTO);
		}/*else{
			if(loadMovementDO.getReceivedStatus().equals("C")){
				ExceptionUtil.prepareBusinessException(LoadManagementUniversalConstants.GATE_PASS_NUMBER_ALREADY_RECEIVED);
				throw new CGBusinessException(
						LoadManagementUniversalConstants.GATE_PASS_NUMBER_ALREADY_RECEIVED); 
			}
		}*/
		
		//LoadReceiveLocalTransferConverter
		if(loadMovementDO!=null){
			loadReceiveLocalTO = loadReceiveLocalTransferConverter(loadMovementDO);
			
		}else{			
			loadReceiveLocalTO = new LoadReceiveLocalTO();
			loadReceiveLocalTO.setIsNewReceive(true);
			//ExceptionUtil.prepareBusinessException(LoadManagementUniversalConstants.GATE_PASS_NUMBER_ALREADY_RECEIVED);
			/*throw new CGBusinessException(
					LoadManagementUniversalConstants.INVALID_GATEPASS_NUMBER); */
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadReceiveLocalTO::END------------>:::::::");
		return loadReceiveLocalTO;
	}
	
	/**
	 * Load receive local transfer converter.
	 *
	 * @param loadMovementDO the load movement do
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private LoadReceiveLocalTO loadReceiveLocalTransferConverter(
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::loadReceiveLocalTransferConverter::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = new LoadReceiveLocalTO();
		loadReceiveLocalTO.setMovementDirection(loadMovementDO.getMovementDirection());
		loadReceiveLocalTO.setGatePassNumber(loadMovementDO.getGatePassNumber());
		loadReceiveLocalTO.setProcessNumber(loadMovementDO.getProcessNumber());

		if(loadMovementDO.getProcessDO()!=null){
			loadReceiveLocalTO.setProcessId(loadMovementDO.getProcessDO().getProcessId());
		}
		if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
				LoadManagementUniversalConstants.RECEIVE_DIRECTION)){
			loadReceiveLocalTO.setLoadMovementId(loadMovementDO.getLoadMovementId());
			loadReceiveLocalTO.setReceivedAgainstId(loadMovementDO.getReceivedAgainst());	
			loadReceiveLocalTO.setHeaderReceivedStatus(loadMovementDO.getReceivedStatus());	
			loadReceiveLocalTO.setActualArrival(loadMovementDO.getReceivedAtTime());
			if(loadMovementDO.getLoadingDate()!=null){
				loadReceiveLocalTO.setReceiveDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
						(loadMovementDO.getLoadingDate()));			
			}
		}else{
			loadReceiveLocalTO.setReceivedAgainstId(loadMovementDO.getLoadMovementId());	
		}
		
		// vehicle
		if (StringUtils.isNotBlank(loadMovementDO.getVehicleType())) {
			// same city
			if (loadMovementDO.getVehicleDO() != null) {
				loadReceiveLocalTO.setVehicleNumber(loadMovementDO
						.getVehicleDO().getVehicleId()
						+ CommonConstants.TILD
						+ loadMovementDO.getVehicleDO().getRegNumber());
			} else {
				if (StringUtils
						.isNotBlank(loadMovementDO.getVehicleRegNumber())) {
					loadReceiveLocalTO
							.setVehicleNumber(LoadManagementUniversalConstants.OTHERS_VEHICLE_CODE);
					loadReceiveLocalTO.setOtherVehicleNumber(loadMovementDO
							.getVehicleRegNumber());
				}
			}
		} else {
			// different city
			if (StringUtils.isNotBlank(loadMovementDO
					.getRouteServicedTransportType())) {
				if (loadMovementDO.getRouteServicedTransportType().equals(
						LoadManagementUniversalConstants.OTHERS_CODE)) {
					loadReceiveLocalTO
							.setVehicleNumber(LoadManagementUniversalConstants.OTHERS_VEHICLE_CODE);
					loadReceiveLocalTO.setOtherVehicleNumber(loadMovementDO
							.getRouteServicedTransportNumber());

				} else {
					if (loadMovementDO.getTripServicedByDO() != null
							&& loadMovementDO.getTripServicedByDO().getTripDO() != null) {
						loadReceiveLocalTO.setActualArrival(loadMovementDO
								.getTripServicedByDO().getTripDO()
								.getArrivalTime());

						if (loadMovementDO.getTripServicedByDO().getTripDO()
								.getTransportDO() != null
								&& loadMovementDO.getTripServicedByDO()
										.getTripDO().getTransportDO()
										.getVehicleDO() != null) {
							loadReceiveLocalTO.setVehicleNumber(loadMovementDO
									.getTripServicedByDO().getTripDO()
									.getTransportDO().getVehicleDO()
									.getVehicleId()
									+ CommonConstants.TILD
									+ loadMovementDO.getTripServicedByDO()
											.getTripDO().getTransportDO()
											.getVehicleDO().getRegNumber());
						}
					}
				}
			}
		}
		
		loadReceiveLocalTO.setDriverName(loadMovementDO.getDriverName());
		
		if(loadMovementDO.getOriginOfficeDO()!=null){
			loadReceiveLocalTO.setOriginOffice(loadMovementDO.getOriginOfficeDO().getOfficeId() +
					CommonConstants.TILD + loadMovementDO.getOriginOfficeDO().getOfficeCode() + 
					CommonConstants.HYPHEN + loadMovementDO.getOriginOfficeDO().getOfficeName());
			//loadReceiveLocalTO.setOriginOfficeId(loadMovementDO.getOriginOfficeDO().getOfficeId());
			
			//TODO need to check whether we need regionalOffice or not
			//or we need to restrict only logged in regionalOffice
			/*OfficeTO regionalOfficeTO = organizationCommonService.getOfficeDetails(
					loadMovementDO.getOriginOfficeDO().getReportingRHO());
			loadReceiveLocalTO.setRegionalOffice(regionalOfficeTO.getOfficeCode() + 
					CommonConstants.HYPHEN + regionalOfficeTO.getOfficeName());*/
		}

		if(loadMovementDO.getDestOfficeDO()!=null){
			loadReceiveLocalTO.setDestOffice(loadMovementDO.getDestOfficeDO().getOfficeCode() +
					CommonConstants.HYPHEN +  loadMovementDO.getDestOfficeDO().getOfficeName());
			if(loadMovementDO.getDestOfficeDO().getOfficeTypeDO()!=null){
				loadReceiveLocalTO.setDestOfficeType(
						loadMovementDO.getDestOfficeDO().getOfficeTypeDO().getOffcTypeDesc());
			}

			loadReceiveLocalTO.setDestOfficeId(loadMovementDO.getDestOfficeDO().getOfficeId());
			//loggedInoffice treated as destination Office
			loadReceiveLocalTO.setLoggedInOfficeId(loadMovementDO.getDestOfficeDO().getOfficeId());
			//officeId~cityId~ReportingRHOId
			loadReceiveLocalTO.setLoggedInOffice(loadMovementDO.getDestOfficeDO().getOfficeId() + 
					CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getCityId() +
					CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getReportingRHO());
		}

		if(loadMovementDO.getTransportModeDO()!=null){
			loadReceiveLocalTO.setTransportMode(loadMovementDO.getTransportModeDO().
					getTransportModeDesc());
			loadReceiveLocalTO.setTransportModeDetails(
					loadMovementDO.getTransportModeDO().getTransportModeId() +
					CommonConstants.TILD + 
					loadMovementDO.getTransportModeDO().getTransportModeCode() +
					CommonConstants.TILD + 
					loadMovementDO.getTransportModeDO().getTransportModeDesc());
		}

		
		List<LoadReceiveDetailsTO> loadReceiveDetailsTOs = new ArrayList<LoadReceiveDetailsTO>();
		//grid dtails
		if (StringUtils.equals(loadMovementDO.getMovementDirection(),
				LoadManagementUniversalConstants.RECEIVE_DIRECTION)
				&& (loadMovementDO.getLoadConnectedDOs() != null && loadMovementDO
						.getLoadConnectedDOs().size() > 0)) {
			Set<LoadConnectedDO> loadConnectedDOList = loadMovementDO.getLoadConnectedDOs();

			for (LoadConnectedDO loadConnectedDO : loadConnectedDOList) {
				LoadReceiveDetailsTO loadReceiveDetailsTO = new LoadReceiveDetailsTO();
				loadReceiveDetailsTO.setWeight(loadConnectedDO.getDispatchWeight());
				
				if(loadConnectedDO.getManifestDO()!=null){
					loadReceiveDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
					loadReceiveDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
					loadReceiveDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
					if(loadConnectedDO.getManifestDO().getManifestLoadContent()!=null){
						loadReceiveDetailsTO.setDocType(loadConnectedDO.getManifestDO().getManifestLoadContent().getConsignmentId() + "");						
					}
					if (loadConnectedDO.getManifestDO().getDestOffice()!= null) {
						CityTO cityTO = new CityTO();
						cityTO.setCityId(loadConnectedDO.getManifestDO().getDestOffice().getCityId());
						cityTO = getCity(cityTO);
						if (cityTO != null) {
							loadReceiveDetailsTO.setManifestDestCity(cityTO.getCityCode());
						}
					}
					if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
						loadReceiveDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().getDestinationCity().getCityCode());
						loadReceiveDetailsTO.setManifestDestCityDetails(loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName());
					}

					if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
							LoadManagementUniversalConstants.DISPATCH_DIRECTION)){
						loadReceiveDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
					}
				}
				if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
						LoadManagementUniversalConstants.RECEIVE_DIRECTION)){
					loadReceiveDetailsTO.setLoadConnectedId(loadConnectedDO.getLoadConnectedId());
					loadReceiveDetailsTO.setRemarks(loadConnectedDO.getRemarks());
					loadReceiveDetailsTO.setLockNumber(loadConnectedDO.getLockNumber());
					loadReceiveDetailsTO.setReceivedStatus(loadConnectedDO.getReceivedStatus());
				}
				loadReceiveDetailsTOs.add(loadReceiveDetailsTO);
			}
			Collections.sort(loadReceiveDetailsTOs);
		}
		loadReceiveLocalTO.setLoadReceiveDetailsTOs(loadReceiveDetailsTOs);
		LOGGER.trace("LoadManagementCommonServiceImpl::loadReceiveLocalTransferConverter::END------------>:::::::");
		return loadReceiveLocalTO;
	}

	
    /**
     * validate Manifest Number or BPL/MBPL Number
     * <p>
     * <ul>
     * <li>Check Manifest dispatched or not.
     * <li>If not Check manifestNumber exist in Manifest.
     * <li>If manifestNumber not exist then return ManifestTO with isNewManifest(true) field.
     * <li>If manifestNumber exist then check issued to branch(loggedInOffice) or not.
     * <li>If yes Check pure route first by Manifest destination city & dispatch destination city.
     * <li>If not check Transshipment Route by dispatch destination city and Manifest destination city.
     * <li>
     * </ul>
     * <p>
     *
     * @param loadManifestTO the manifest to
     * @return ManifestTO :: If all the validations are passed then return ManifestTO will get filled with the followings:
     * <ul>
     * <li>manifestId
     * <li>manifestNumber
     * <li>bagLockNo
     * <li>manifestWeight
     * <li>consignmentTypeTO
     * <li>originOfficeTO
     * <li>destinationOfficeTO
     * <li>destinationCityTO
     * </ul>
     * else return CGBusinessException.
     * @throws CGBusinessException the cG business exception
     * @throws CGSystemException the cG system exception
     * @author R Narmdeshwar
     */
	@Override
	public ManifestTO validateManifestNumber4Dispatch(ManifestTO loadManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4Dispatch::START------------>:::::::");
		ManifestTO manifestTO1 = null;
		Integer dispatchDestCityId = loadManifestTO.getDestinationCityTO().getCityId();
		//Integer loggedInOfficeId = manifestTO.getOriginOfficeTO().getOfficeId();
		
		LoadConnectedDO loadConnectedDO = loadManagementCommonDAO.getLoadConnectedDO(
						loadManifestTO, LoadManagementUniversalConstants.DISPATCH_DIRECTION);
		
		if(loadConnectedDO!=null && loadConnectedDO.getManifestDO()!=null){
			ExceptionUtil.prepareBusinessException(LoadManagementUniversalConstants.MBPL_ALREADY_DISPATCHED);
			//throw new CGBusinessException(LoadManagementUniversalConstants.MBPL_ALREADY_DISPATCHED);
		}

		List<ManifestTO> manifestTOs = outManifestUniversalService
				.getManifestDtlsForDispatchByManifestNoOriginOffId(loadManifestTO);
		
		if(StringUtil.isEmptyColletion(manifestTOs)){
			//added for direct bag
			manifestTOs = outManifestUniversalService.getManifestDtlsForDispatch(loadManifestTO);
		} else {
			//Recently InManifested BPL/MBPL Number not allowed in dispatch.
			validateIsManifestInManifested(manifestTOs.get(0));
		}
		if(!StringUtil.isEmptyColletion(manifestTOs)){
			//order of getting manifest is O>>I>>R.
			if(manifestTO1==null){
				manifestTO1 = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_OUT);
			}
			if(manifestTO1==null){
				manifestTO1 = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_IN);
			}
			if(manifestTO1==null){
				manifestTO1 = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_RTO);
			}
			if(manifestTO1==null){
				manifestTO1 = manifestTOs.get(0);
			}

			if(manifestTO1!=null && !StringUtils.equals(manifestTO1.getManifestType(), 
					LoadManagementUniversalConstants.MANIFEST_TYPE_OUT)){
				manifestTO1.setManifestId(null);
			}
		}else{
			//create new manifest
			/* No need to create new Manifest
			 manifestTO1 = new ManifestTO();
			manifestTO1.setIsNewManifest(true);
			return manifestTO1;*/
			ExceptionUtil
					.prepareBusinessException(
							LoadManagementUniversalConstants.BPL_MBPL_NEITHER_PREPARED_NOR_INMANIFESTED,
							new String[] { loadManifestTO.getManifestNumber() });
		}

		//Check issued to branch(loggedInOffice) or not.
		/*String ISSUED_TO_BRANCH = "BR";
		StockIssueValidationTO stockIssueValidationTO = new StockIssueValidationTO();
		stockIssueValidationTO.setIssuedTOPartyId(loggedInOfficeId);
		stockIssueValidationTO.setIssuedTOPartyType(ISSUED_TO_BRANCH);
		stockIssueValidationTO.setStockItemNumber(manifestTO1.getManifestNumber());
		stockIssueValidationTO = stockUniversalService.validateStock(stockIssueValidationTO);
		
		if(!stockIssueValidationTO.getIsIssuedTOParty()){
			ExceptionUtil.prepareBusinessException(LoadManagementUniversalConstants.NOT_ISSUED_TO_OFFICE);
			throw new CGBusinessException(LoadManagementConstants.NOT_ISSUED_TO_OFFICE);
		}*/
		
		//stock issue validation Start
		/*StockIssueValidationTO stockIssueValidationTO = stockUniversalService
				.validateStock(manifestTO.getStockIssueValidationTO());
		if (!stockIssueValidationTO.getIsIssuedTOParty()) {
			ExceptionUtil.prepareBusinessException(LoadManagementUniversalConstants.NOT_ISSUED_TO_OFFICE);
			throw new CGBusinessException(
					LoadManagementUniversalConstants.NOT_ISSUED_TO_OFFICE);
		}*/
		//stock issue validation End
		
		//To check whether manifest is closed or not
		validateIsManifestClosed(manifestTO1);
				
		/*
		 * if Branch to Branch, no need to check transshipment Route, same city manifest creation.
		 * if Branch to Hub check Pure & transshipment Route & same city manifest creation.
		 * if Hub to Hub check Pure & transshipment Route only.
		 * if Hub to Branch check Pure & transshipment Route only.
		*/
		String originDestOffTypeOfLoad = getOriginDestOffTypeOfLoad(loadManifestTO);

		boolean isPureRoute = isPureRoute(manifestTO1, dispatchDestCityId);
		boolean isSameCity = isSameCityInLoadManifest(manifestTO1);

		if (originDestOffTypeOfLoad
				.equals(LoadManagementUniversalConstants.BRANCH_TO_BRANCH)) {
			if (!isSameCity || !isPureRoute) {
				ExceptionUtil
						.prepareBusinessException(
								LoadManagementUniversalConstants.OUTSECTOR_BPL_MBPL_NOT_ALLOWED,
								new String[] { manifestTO1.getManifestNumber() });
			}

		} else if (originDestOffTypeOfLoad
				.equals(LoadManagementUniversalConstants.BRANCH_TO_HUB)) {
			if (!isSameCity && !isPureRoute) {
				validateTransshipmentRoute(manifestTO1, dispatchDestCityId);
			}
		} else if (originDestOffTypeOfLoad
				.equals(LoadManagementUniversalConstants.HUB_TO_HUB)) {
			if (!isPureRoute) {
				validateTransshipmentRoute(manifestTO1, dispatchDestCityId);
			}
		} else if (originDestOffTypeOfLoad
				.equals(LoadManagementUniversalConstants.HUB_TO_BRANCH)) {
			if (!isPureRoute) {
				ExceptionUtil
						.prepareBusinessException(
								LoadManagementUniversalConstants.OUTSECTOR_BPL_MBPL_NOT_ALLOWED,
								new String[] { manifestTO1.getManifestNumber() });
			}

		}

		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4Dispatch::END------------>:::::::");
		return manifestTO1;
	}

	private void validateIsManifestInManifested(ManifestTO manifestTO)
			throws CGBusinessException {
		if (manifestTO.getUpdatingProcessTO() != null
				&& !StringUtils.equals(CommonConstants.PROCESS_RECEIVE,
						manifestTO.getUpdatingProcessTO().getProcessCode())
				&& StringUtils.equals(CommonConstants.MANIFEST_TYPE_IN,
						manifestTO.getManifestType())
				&& !StringUtil.isEmptyInteger(manifestTO.getNoOfElements())
				&& manifestTO.getNoOfElements() > 0) {
			ExceptionUtil
					.prepareBusinessException(
							LoadManagementUniversalConstants.BPL_MBPL_NUMBER_ALREADY_IN_MANIFESTED,
							new String[] { manifestTO.getManifestNumber() });
		}
	}

	/**
	 * Checks if is pure route.
	 *
	 * @param manifestTO the manifest t o1
	 * @param dispatchDestCityId the dispatch dest city id
	 * @return true, if is pure route
	 */
	private boolean isPureRoute(ManifestTO manifestTO,
			Integer dispatchDestCityId) {
		boolean isPureRoute = Boolean.FALSE;
		// check pure route
		if (dispatchDestCityId.equals(manifestTO.getDestinationCityTO()
				.getCityId())) {
			isPureRoute = Boolean.TRUE;
		}
		return isPureRoute;
	}

	/**
	 * Checks if is same city in load manifest.
	 *
	 * @param manifestTO the manifest to
	 * @return true, if is same city in load manifest
	 */
	private boolean isSameCityInLoadManifest(ManifestTO manifestTO) {
		boolean isSameCity = Boolean.FALSE;
		OfficeTO loggedInOfficeTO = null;
		if (udaanContextService.getUserInfoTO() != null
				&& udaanContextService.getUserInfoTO().getOfficeTo() != null) {
			loggedInOfficeTO = udaanContextService.getUserInfoTO()
					.getOfficeTo();
		}

		/*
		 * check manifest origin city and dispatch origin/login city for same
		 * city case
		 */
		if ((loggedInOfficeTO != null
				&& manifestTO.getOriginOfficeTO() != null
				&& !StringUtil.isEmptyInteger(manifestTO.getOriginOfficeTO()
						.getCityId()) && loggedInOfficeTO.getCityId().equals(
				manifestTO.getOriginOfficeTO().getCityId()))) {
			isSameCity = Boolean.TRUE;
		}
		return isSameCity;
	}

	/**
	 * Validate transshipment route.
	 *
	 * @param manifestTO the manifest to
	 * @param dispatchDestCityId the dispatch dest city id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void validateTransshipmentRoute(ManifestTO manifestTO,
			Integer dispatchDestCityId) throws CGBusinessException,
			CGSystemException {
		// check Transshipment route
		TransshipmentRouteTO transshipmentRouteTO = new TransshipmentRouteTO();
		transshipmentRouteTO.setTransshipmentCityId(dispatchDestCityId);
		transshipmentRouteTO.setServicedCityId(manifestTO
				.getDestinationCityTO().getCityId());

		TransshipmentRouteTO transshipmentRouteTO1 = getTransshipmentRoute(transshipmentRouteTO);
		if (transshipmentRouteTO1 == null) {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.NO_ROUTE);
		}
	}
	
	/**
	 * Gets the origin dest off type of load.
	 *
	 * @param manifestTO the manifest to
	 * @return the origin dest off type of load
	 */
	private String getOriginDestOffTypeOfLoad(ManifestTO manifestTO) {
		String originDestOffTypeOfLoad = null;

		if (StringUtils.isBlank(manifestTO.getLoadOriginOfficeType())
				|| StringUtils.isBlank(manifestTO.getLoadDestOfficeType())) {
			return originDestOffTypeOfLoad;
		}

		if (manifestTO.getLoadOriginOfficeType().equals(
				CommonConstants.OFF_TYPE_BRANCH_OFFICE)
				&& manifestTO.getLoadDestOfficeType().equals(
						CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
			originDestOffTypeOfLoad = LoadManagementUniversalConstants.BRANCH_TO_BRANCH;

		} else if (manifestTO.getLoadOriginOfficeType().equals(
				CommonConstants.OFF_TYPE_BRANCH_OFFICE)
				&& manifestTO.getLoadDestOfficeType().equals(
						CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			originDestOffTypeOfLoad = LoadManagementUniversalConstants.BRANCH_TO_HUB;

		} else if (manifestTO.getLoadOriginOfficeType().equals(
				CommonConstants.OFF_TYPE_HUB_OFFICE)
				&& manifestTO.getLoadDestOfficeType().equals(
						CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
			originDestOffTypeOfLoad = LoadManagementUniversalConstants.HUB_TO_BRANCH;

		} else if (manifestTO.getLoadOriginOfficeType().equals(
				CommonConstants.OFF_TYPE_HUB_OFFICE)
				&& manifestTO.getLoadDestOfficeType().equals(
						CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			originDestOffTypeOfLoad = LoadManagementUniversalConstants.HUB_TO_HUB;

		}

		return originDestOffTypeOfLoad;
	}

	/**
	 * Validate is manifest closed.
	 *
	 * @param manifestTO the manifest to
	 * @throws CGBusinessException the cG business exception
	 */
	private void validateIsManifestClosed(ManifestTO manifestTO)
			throws CGBusinessException {
		if (StringUtils.isBlank(manifestTO.getManifestStatus())
				|| !StringUtils.equals(CommonConstants.MANIFEST_STATUS_CLOSED,
						manifestTO.getManifestStatus())) {
			ExceptionUtil
					.prepareBusinessException(
							LoadManagementUniversalConstants.ONLY_CLOSED_BPL_MBPL_ARE_ALLOWED,
							new String[] { manifestTO.getManifestNumber() });
		}
	}

	/**
	 * Gets the manifest by manifest type.
	 *
	 * @param manifestTOs the manifest t os
	 * @param manifestType the manifest type
	 * @return the manifest by manifest type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private ManifestTO getManifestByManifestType(List<ManifestTO> manifestTOs,
			String manifestType)throws CGBusinessException, CGSystemException  {		
		ManifestTO manifestTO = null;
		
		for (ManifestTO manifestTO1 : manifestTOs) {
			if(StringUtils.equals(manifestTO1.getManifestType(), manifestType)){
				manifestTO = manifestTO1;
				break;
			}
		}
		return manifestTO;
	}
	
	/**
	 * Gets the origin offices.
	 *
	 * @param officeTO the office to
	 * @return the origin offices
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public List<OfficeTO> getOriginOffices(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficesByRegionalOfficeExcludeOffice(officeTO);
	}
	
	
    /**
     * validate Manifest Number or BPL/MBPL Number for Local Receive
     * <p>
     * <ul>
     * <li>If receivedAgainstId is not null,
     * Check manifestNumber exist in dispatch or not.
     * <li>If not found in above case, Check manifestNumber already received or not.
     * <li>If no in above case, Check manifestNumber exist in Manifest.
     * <li>If manifestNumber not exist in any then return LoadReceiveManifestValidationTO with isNewManifest(true) field.
     * <li>
     * </ul>
     * <p>
     *
     * @param loadReceiveManifestValidationTO the load receive manifest validation to
     * @return ManifestTO :: If all the validations are passed then return LoadReceiveManifestValidationTO.
     * else return CGBusinessException.
     * @throws CGBusinessException the CG business exception
     * @throws CGSystemException the CG system exception
     * @author R Narmdeshwar
     */
	@Override
	public LoadReceiveManifestValidationTO validateManifestNumber4ReceiveLocal(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4ReceiveLocal::START------------>:::::::");
		
		if (loadManagementCommonDAO
				.isManifestReceived(loadReceiveManifestValidationTO)) {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.MBPL_ALREADY_RECEIVED);
			/*throw new CGBusinessException(
					LoadManagementUniversalConstants.MBPL_ALREADY_RECEIVED);*/
		}
		
		LoadReceiveManifestValidationTO loadReceiveManifestValidationTO2 =
				new LoadReceiveManifestValidationTO();
		LoadConnectedDO loadConnectedDO = null;
		

	    /* * 		<li>If loadMovementId is not null, 
	     * 			Check manifestNumber exist in Receive Local as not received.
		//Getting data from Receive Local
		if(loadReceiveManifestValidationTO.getLoadMovementId()!=null){
			loadConnectedDO = loadManagementCommonDAO.
					getLoadConnected4ReceiveLocalByReceiveManifestNo(loadReceiveManifestValidationTO);
			loadReceiveManifestValidationTO2.setIsReceive(true);
		}
		else */
		if(loadReceiveManifestValidationTO.getReceivedAgainstId()!=null){
			//Getting data from Dispatch
			loadConnectedDO = loadManagementCommonDAO.
					getLoadConnected4ReceiveLocalByDispatchManifestNo(loadReceiveManifestValidationTO);
			loadReceiveManifestValidationTO2.setIsReceive(false);
			loadReceiveManifestValidationTO2.setIsDispatch(true);			
		}
		
		if(loadConnectedDO!=null){
			LoadReceiveDetailsTO loadReceiveDetailsTO = new LoadReceiveDetailsTO();
			convertLoadConnectedDOToLoadReceiveDetailsTO(loadConnectedDO, loadReceiveDetailsTO);
			loadReceiveManifestValidationTO2.setLoadReceiveDetailsTO(loadReceiveDetailsTO);
			return loadReceiveManifestValidationTO2;
		}

		//Getting data from Manifest
		ManifestTO manifestTO1 = new ManifestTO();
		manifestTO1.setManifestNumber(loadReceiveManifestValidationTO.getManifestNumber());
		List<ManifestTO> manifestTOs = outManifestUniversalService.getManifestDtlsForDispatch(manifestTO1);
		
		ManifestTO manifestTO = null;
		if(!StringUtil.isEmptyColletion(manifestTOs)){
			//order of getting manifest is O>>R>>I.
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_IN);
			}
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_OUT);
			}
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_RTO);
			}
			if(manifestTO==null){
				manifestTO = manifestTOs.get(0);
			}
			if(manifestTO!=null && !StringUtils.equals(manifestTO.getManifestType(), 
					LoadManagementUniversalConstants.MANIFEST_TYPE_IN)){
				manifestTO.setManifestId(null);
			}
			loadReceiveManifestValidationTO2.setManifestTO(manifestTO);
		}else{
			//create new manifest
			loadReceiveManifestValidationTO2.setIsReceive(false);
			loadReceiveManifestValidationTO2.setIsDispatch(false);
			loadReceiveManifestValidationTO2.setIsNewManifest(true);
			
		}

		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4ReceiveLocal::END------------>:::::::");
		return loadReceiveManifestValidationTO2;
	}
	
	/**
	 * Convert load connected do to load receive details to.
	 *
	 * @param loadConnectedDO the load connected do
	 * @param loadReceiveDetailsTO the load receive details to
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private void convertLoadConnectedDOToLoadReceiveDetailsTO(
			LoadConnectedDO loadConnectedDO,
			LoadReceiveDetailsTO loadReceiveDetailsTO) throws CGBusinessException, CGSystemException {

		LOGGER.trace("LoadManagementCommonServiceImpl::convertLoadConnectedDOToLoadReceiveDetailsTO::START------------>:::::::");
		loadReceiveDetailsTO.setWeight(loadConnectedDO.getDispatchWeight());
		
		if(loadConnectedDO.getManifestDO()!=null){
			//FIXME setManifestId() only when it is Incoming manifest else set null
			if(StringUtils.equals(loadConnectedDO.getManifestDO().getManifestType(), 
					LoadManagementUniversalConstants.MANIFEST_TYPE_IN)){
				loadReceiveDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
			}
			loadReceiveDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
			loadReceiveDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
			loadReceiveDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
			if(loadConnectedDO.getManifestDO().getManifestLoadContent()!=null){
				loadReceiveDetailsTO.setDocType(loadConnectedDO.getManifestDO().getManifestLoadContent().getConsignmentId() + "");						
			}
			if (loadConnectedDO.getManifestDO().getDestOffice()!= null) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(loadConnectedDO.getManifestDO().getDestOffice().getCityId());
				cityTO = getCity(cityTO);
				if (cityTO != null) {
					loadReceiveDetailsTO.setManifestDestCity(cityTO.getCityCode());
				}
			}
			if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
				loadReceiveDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().getDestinationCity().getCityCode());
				loadReceiveDetailsTO.setManifestDestCityDetails(loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
						CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
						CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName());
			}

			//added origin office and destination office for manifest
			if (loadConnectedDO.getManifestDO().getDestOffice() != null) {
				loadReceiveDetailsTO
						.setManifestDestOffId(loadConnectedDO.getManifestDO()
								.getDestOffice().getOfficeId());
			}
			if (loadConnectedDO.getManifestDO().getOriginOffice() != null) {
				loadReceiveDetailsTO
						.setManifestOriginOffId(loadConnectedDO.getManifestDO()
								.getOriginOffice().getOfficeId());
			}
		}
		if(StringUtils.equals(loadConnectedDO.getLoadMovementDO().getMovementDirection(), 
				LoadManagementUniversalConstants.RECEIVE_DIRECTION)){
			//loadReceiveDetailsTO.setLoadConnectedId(loadConnectedDO.getLoadConnectedId());
			loadReceiveDetailsTO.setRemarks(loadConnectedDO.getRemarks());
			loadReceiveDetailsTO.setReceivedStatus(loadConnectedDO.getReceivedStatus());
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::convertLoadConnectedDOToLoadReceiveDetailsTO::END------------>:::::::");
	}

	/**
	 * Gets the process.
	 *
	 * @param processTO the process to
	 * @return the process
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public ProcessTO getProcess(ProcessTO processTO) throws CGSystemException,
			CGBusinessException {
		return trackingUniversalService.getProcess(processTO);
	}
	
	/**
	 * Checks if is receive number exist.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return true, if is receive number exist
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public boolean isReceiveNumberExist(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGBusinessException, CGSystemException {
		boolean isExist = loadManagementCommonDAO
				.isReceiveNumberExist(loadReceiveValidationTO);
		if (isExist) {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.RECEIVE_NO_ALREADY_EXIST);
			/*throw new CGBusinessException(
					LoadManagementUniversalConstants.RECEIVE_NO_ALREADY_EXIST);*/
		}
		return isExist;
	}
			
    /**
     * validate Manifest Number or BPL/MBPL Number for Local Receive
     * <p>
     * <ul>
     * <li>Check manifestNumber already received or not.
     * <li>If not, Check manifestNumber exist in dispatch or not.
     * <li>If no in above case, Check manifestNumber exist in Manifest.
     * <li>If manifestNumber not exist in any then return
     * LoadReceiveManifestValidationTO with isNewManifest(true) field.
     * </ul>
     * <p>
     *
     * @param loadReceiveManifestValidationTO the load receive manifest validation to
     * @return ManifestTO :: If all the validations are passed then return LoadReceiveManifestValidationTO.
     * else throws CGBusinessException.
     * @throws CGBusinessException the cG business exception
     * @throws CGSystemException the cG system exception
     * @author R Narmdeshwar
     */
	@Override
	public LoadReceiveManifestValidationTO validateManifestNumber4ReceiveOutstation(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4ReceiveOutstation::START------------>:::::::");
		
		if (loadManagementCommonDAO
				.isManifestReceived(loadReceiveManifestValidationTO)) {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.MBPL_ALREADY_RECEIVED);
		}
		
		/*if (loadManagementCommonDAO
				.isManifestReceived4Outstation(loadReceiveManifestValidationTO)) {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.MBPL_ALREADY_RECEIVED);
		}*/
		
		LoadReceiveManifestValidationTO loadReceiveManifestValidationTO2 =
				new LoadReceiveManifestValidationTO();
		LoadConnectedDO loadConnectedDO = null;

		//Getting data from Dispatch
		loadConnectedDO = loadManagementCommonDAO
				.getLoadConnected4ReceiveOutstationByDispatchManifestNo(loadReceiveManifestValidationTO);

		if(loadConnectedDO!=null){
			loadReceiveManifestValidationTO2.setIsDispatch(true);
			LoadReceiveOutstationDetailsTO loadReceiveOutstationDetailsTO = new LoadReceiveOutstationDetailsTO();
			convertLoadConnectedDOToLoadReceiveOutstationDetailsTO(loadConnectedDO, loadReceiveOutstationDetailsTO);
			loadReceiveManifestValidationTO2.setLoadReceiveOutstationDetailsTO(loadReceiveOutstationDetailsTO);
			return loadReceiveManifestValidationTO2;
		}
		
		//Getting data from Manifest
		ManifestTO manifestTO1 = new ManifestTO();
		manifestTO1.setManifestNumber(loadReceiveManifestValidationTO.getManifestNumber());
		List<ManifestTO> manifestTOs = outManifestUniversalService.getManifestDtlsForDispatch(manifestTO1);
		
		ManifestTO manifestTO = null;
		if(!StringUtil.isEmptyColletion(manifestTOs)){
			//order of getting manifest is I>>O>>R.
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_IN);
			}
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_OUT);
			}
			if(manifestTO==null){
				manifestTO = getManifestByManifestType(manifestTOs, 
						LoadManagementUniversalConstants.MANIFEST_TYPE_RTO);
			}
			if(manifestTO==null){
				manifestTO = manifestTOs.get(0);
			}
			if(manifestTO!=null && !StringUtils.equals(manifestTO.getManifestType(), 
					LoadManagementUniversalConstants.MANIFEST_TYPE_IN)){
				manifestTO.setManifestId(null);
			}
			loadReceiveManifestValidationTO2.setManifestTO(manifestTO);
		}else{
			//create new manifest
			loadReceiveManifestValidationTO2.setIsDispatch(false);
			loadReceiveManifestValidationTO2.setIsNewManifest(true);			
		}

		LOGGER.trace("LoadManagementCommonServiceImpl::validateManifestNumber4ReceiveOutstation::END------------>:::::::");
		return loadReceiveManifestValidationTO2;	
	}
	
	/**
	 * Convert load connected do to load receive outstation details to.
	 *
	 * @param loadConnectedDO the load connected do
	 * @param loadReceiveOutstationDetailsTO the load receive outstation details to
	 */
	private void convertLoadConnectedDOToLoadReceiveOutstationDetailsTO(
			LoadConnectedDO loadConnectedDO,
			LoadReceiveOutstationDetailsTO loadReceiveOutstationDetailsTO) {

		LOGGER.trace("LoadManagementCommonServiceImpl::convertLoadConnectedDOToLoadReceiveOutstationDetailsTO::START------------>:::::::");
		//cd/awb/rr no.
		loadReceiveOutstationDetailsTO.setTokenNumber(loadConnectedDO.getTokenNumber());
		
		// Manifest Start
		if (loadConnectedDO.getManifestDO() != null) {
			if(StringUtils.equals(loadConnectedDO.getManifestDO().getManifestType(), 
					LoadManagementUniversalConstants.MANIFEST_TYPE_IN)){
				loadReceiveOutstationDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
			}
			loadReceiveOutstationDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
			loadReceiveOutstationDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
			loadReceiveOutstationDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
			if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
				loadReceiveOutstationDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().getDestinationCity().getCityCode());
				loadReceiveOutstationDetailsTO.setManifestDestCityDetails(loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
						CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
						CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName());
			}
			
			//added origin office and destination office for manifest
			if (loadConnectedDO.getManifestDO().getDestOffice() != null) {
				loadReceiveOutstationDetailsTO
						.setManifestDestOffId(loadConnectedDO.getManifestDO()
								.getDestOffice().getOfficeId());
			}
			if (loadConnectedDO.getManifestDO().getOriginOffice() != null) {
				loadReceiveOutstationDetailsTO
						.setManifestOriginOffId(loadConnectedDO.getManifestDO()
								.getOriginOffice().getOfficeId());
			}
			if (loadConnectedDO.getManifestDO().getManifestLoadContent() != null) {
				loadReceiveOutstationDetailsTO.setConsgTypeId(loadConnectedDO
						.getManifestDO().getManifestLoadContent()
						.getConsignmentId());
				loadReceiveOutstationDetailsTO.setDocType(loadConnectedDO
						.getManifestDO().getManifestLoadContent()
						.getConsignmentCode());
			}
		}
		// Manifest End
		
		//Main Start
		if (loadConnectedDO.getLoadMovementDO() != null) {
			loadReceiveOutstationDetailsTO.setGatePassNumber(loadConnectedDO
					.getLoadMovementDO().getGatePassNumber());

			String transportModeCode = null;// A, R, T
			
			if (loadConnectedDO.getLoadMovementDO().getTransportModeDO() != null) {
				loadReceiveOutstationDetailsTO.setTransportMode(loadConnectedDO
						.getLoadMovementDO().getTransportModeDO()
						.getTransportModeId().toString());
				transportModeCode = loadConnectedDO.getLoadMovementDO()
						.getTransportModeDO().getTransportModeCode();
			}

			if (loadConnectedDO.getLoadMovementDO().getTripServicedByDO() != null
					&& loadConnectedDO.getLoadMovementDO()
							.getTripServicedByDO().getServicedByDO() != null
					&& loadConnectedDO.getLoadMovementDO()
							.getTripServicedByDO().getServicedByDO()
							.getLoadMovementVendorDO() != null
					&& StringUtils.equals(loadConnectedDO.getLoadMovementDO()
							.getTripServicedByDO().getServicedByDO()
							.getServicedByType(),
							LoadManagementUniversalConstants.VENDOR_CODE)) {
				
				loadReceiveOutstationDetailsTO.setVendor(loadConnectedDO
						.getLoadMovementDO().getTripServicedByDO()
						.getServicedByDO().getLoadMovementVendorDO()
						.getBusinessName());

			}			
			
			/////////////////////////////////
			//Start Coloader/OTC/OBC/Direct			
			if(loadConnectedDO.getLoadMovementDO().getTripServicedByDO()!=null
					&& loadConnectedDO.getLoadMovementDO().getTripServicedByDO().
					getServicedByDO()!=null &&	loadConnectedDO.getLoadMovementDO().
					getTripServicedByDO().getServicedByDO().getServiceByTypeDO()!=null){
				
				ServicedByDO servicedByDO = loadConnectedDO.getLoadMovementDO()
						.getTripServicedByDO().getServicedByDO();
				
				if (servicedByDO.getServiceByTypeDO() != null
						&& StringUtils.equals(servicedByDO.getServiceByTypeDO()
								.getServiceByTypeCode(),
								LoadManagementUniversalConstants.DIRECT_CODE)) {
					loadReceiveOutstationDetailsTO.setVendor(servicedByDO
							.getServiceByTypeDO().getServiceByTypeDesc());
				}		
			}
			//End Coloader/OTC/OBC/Direct
			/////////////////////////////////			
			
			
			////////////////////////////
			//Start Flight, Train, Vehicle No.
						
			//Start Vehicle No.
			if (loadConnectedDO.getLoadMovementDO().getVehicleDO() != null) {
				loadReceiveOutstationDetailsTO
						.setTransportNumber(loadConnectedDO.getLoadMovementDO()
								.getVehicleDO().getRegNumber());
			} else if (StringUtils.isNotBlank(loadConnectedDO
					.getLoadMovementDO().getVehicleRegNumber())) {// others
				loadReceiveOutstationDetailsTO
						.setTransportNumber(loadConnectedDO.getLoadMovementDO()
								.getVehicleRegNumber());
			}
			//End Vehicle No.
			
			//Start Route Serviced Transport No.
			if(StringUtils.isNotBlank(loadConnectedDO.getLoadMovementDO().getRouteServicedTransportType())){
				
				if(loadConnectedDO.getLoadMovementDO().getRouteServicedTransportType().equals
						(LoadManagementUniversalConstants.OTHERS_CODE)){
					loadReceiveOutstationDetailsTO.setTransportNumber(loadConnectedDO.getLoadMovementDO().getRouteServicedTransportNumber());					
					
				}else if(loadConnectedDO.getLoadMovementDO().getTripServicedByDO()!=null && 
						loadConnectedDO.getLoadMovementDO().getTripServicedByDO().getTripDO()!=null &&
						loadConnectedDO.getLoadMovementDO().getTripServicedByDO().getTripDO().getTransportDO()!=null){
					if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.AIR_CODE) &&
							loadConnectedDO.getLoadMovementDO().getTripServicedByDO().getTripDO().getTransportDO().getFlightDO()!=null){
							loadReceiveOutstationDetailsTO.setTransportNumber(loadConnectedDO.getLoadMovementDO().getTripServicedByDO().
									getTripDO().getTransportDO().getFlightDO().getFlightNumber());
						
					}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.TRAIN_CODE) &&
							loadConnectedDO.getLoadMovementDO().getTripServicedByDO().getTripDO().getTransportDO().getTrainDO()!=null){
							loadReceiveOutstationDetailsTO.setTransportNumber(loadConnectedDO.getLoadMovementDO().getTripServicedByDO().
									getTripDO().getTransportDO().getTrainDO().getTrainNumber());
													
					}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.ROAD_CODE) &&
							loadConnectedDO.getLoadMovementDO().getTripServicedByDO().getTripDO().getTransportDO().getVehicleDO()!=null){
							loadReceiveOutstationDetailsTO.setTransportNumber(loadConnectedDO.getLoadMovementDO().getTripServicedByDO().
									getTripDO().getTransportDO().getVehicleDO().getRegNumber());
													
					}
				}						
			}
			//End Route Serviced Transport No.
			
			//End Flight, Train, Vehicle No.
			////////////////////////////
			
		}//Main End

		LOGGER.trace("LoadManagementCommonServiceImpl::convertLoadConnectedDOToLoadReceiveOutstationDetailsTO::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.service.LoadManagementCommonService#getLoadMovementTOByGatePassNumber4Print(java.lang.String)
	 */
	@Override
	public LoadMovementTO getLoadMovementTOByGatePassNumber4Print(
			String gatePassNumber) throws CGBusinessException,
			CGSystemException {

		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadMovementTOByGatePassNumber4Print::START------------>:::::::");
		LoadMovementTO LoadMovementTO = null;
		LoadMovementDO loadMovementDO = loadManagementCommonDAO.getLoadMovementDOByGatePassNumber(gatePassNumber);
		if(loadMovementDO!=null){
			LoadMovementTO = loadMovementTransferConverter4Print(loadMovementDO);
		} else {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.ERROR_IN_PRINTING_INVALED_GATEPASS_NUMBER);
			// throw new
			// CGBusinessException(LoadManagementUniversalConstants.ERROR_IN_PRINTING_INVALED_GATEPASS_NUMBER);
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::getLoadMovementTOByGatePassNumber4Print::END------------>:::::::");
		return LoadMovementTO;
	}

	private com.ff.loadmanagement.LoadMovementTO loadMovementTransferConverter4Print(
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::loadMovementTransferConverter4Print::START------------>:::::::");

		LoadMovementTO loadMovementTO = new LoadMovementTO();

		loadMovementTO.setLoadMovementId(loadMovementDO.getLoadMovementId());
		loadMovementTO.setMovementDirection(loadMovementDO.getMovementDirection());
		loadMovementTO.setGatePassNumber(loadMovementDO.getGatePassNumber());
		if(loadMovementDO.getProcessDO()!=null){
			loadMovementTO.setProcessId(loadMovementDO.getProcessDO().getProcessId());
		}
		if(loadMovementDO.getVehicleDO()!=null){
			loadMovementTO.setVehicleNumber(loadMovementDO.getVehicleDO().getVehicleId() + 
					CommonConstants.TILD + loadMovementDO.getVehicleDO().getRegNumber());	
			loadMovementTO.setTransportNumber(loadMovementDO.getVehicleDO().getRegNumber());	
		}else if(StringUtils.isNotBlank(loadMovementDO.getVehicleRegNumber())){
			loadMovementTO.setTransportNumber(loadMovementDO.getVehicleRegNumber());
			loadMovementTO.setVehicleNumber(LoadManagementUniversalConstants.OTHERS_VEHICLE_CODE);			
			loadMovementTO.setOtherVehicleNumber(loadMovementDO.getVehicleRegNumber());			
		}
		loadMovementTO.setDriverName(loadMovementDO.getDriverName());
		if(loadMovementDO.getLoadingDate()!=null){
			loadMovementTO.setDispatchDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
					(loadMovementDO.getLoadingDate()));			
		}
		loadMovementTO.setLoadingTime(loadMovementDO.getLoadingTime());
		
	/*	if(loadMovementDO.getOriginOfficeDO()!=null){
			
			final OfficeTO originOfficeTO = new OfficeTO();
			loadMovementTO.setOriginOfficeTO(originOfficeTO);
			CGObjectConverter.createToFromDomain(loadMovementDO.getOriginOfficeDO(), originOfficeTO);
			
			loadMovementTO.setOriginOfficeId(loadMovementDO.getOriginOfficeDO().getOfficeId());
			if(loadMovementDO.getOriginOfficeDO().getMappedRegionDO()!=null){
			loadMovementTO.setOriginOffice(loadMovementDO.getOriginOfficeDO().getMappedRegionDO().getRegionDisplayName() +  CommonConstants.HYPHEN +loadMovementDO.getOriginOfficeDO().getOfficeName());
			}else{
				loadMovementTO.setOriginOffice(loadMovementDO.getOriginOfficeDO().getOfficeName());
			}
			
			if (loadMovementDO.getOriginOfficeDO().getCityId() != null) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(loadMovementDO.getOriginOfficeDO().getCityId());
				cityTO = getCity(cityTO);
				if (cityTO != null) {
					loadMovementTO.setDestCity(cityTO.getCityName());
				}
			}

		}*/
		
		if(loadMovementDO.getOriginOfficeDO()!=null){
			//loadMovementTO.setOriginOfficeId(loadMovementDO.getOriginOfficeDO().getOfficeId());
			loadMovementTO.setOriginOffice(loadMovementDO.getOriginOfficeDO().getOfficeCode() + 
					CommonConstants.HYPHEN + loadMovementDO.getOriginOfficeDO().getOfficeName());
			OfficeTO originOfficeTO = new OfficeTO();
			loadMovementTO.setOriginOfficeTO(originOfficeTO);
			CGObjectConverter.createToFromDomain(loadMovementDO.getOriginOfficeDO(), originOfficeTO);
			
		}	
		

		if(loadMovementDO.getDestOfficeDO()!=null){
			/*if(loadMovementDO.getOriginOfficeDO().getMappedRegionDO()!=null){*/
				loadMovementTO.setDestOffice(loadMovementDO.getDestOfficeDO().getOfficeCode() +  CommonConstants.HYPHEN +loadMovementDO.getDestOfficeDO().getOfficeName());
				/*}*/
			}

		final TransportModeDO transportModeDO = loadMovementDO.getTransportModeDO();
		String transportModeCode = null;// A, R, T
		if(transportModeDO!=null){
			transportModeCode = transportModeDO.getTransportModeCode();
			loadMovementTO.setTransportMode(transportModeDO.getTransportModeDesc());
			
			//
			if(transportModeCode.equals(LoadManagementUniversalConstants.ROAD_CODE)){
				loadMovementTO.setTransportModeLabelPrint("Vehicle Number");
			}
			if(transportModeCode.equals(LoadManagementUniversalConstants.AIR_CODE)){
				loadMovementTO.setTransportModeLabelPrint("Flight Number");
			}
			if(transportModeCode.equals(LoadManagementUniversalConstants.TRAIN_CODE)){
				loadMovementTO.setTransportModeLabelPrint(LoadManagementUniversalConstants.TRAIN_NUMBER_LABEL);
			}
		}
		
		//Start of setting  TripServicedBy
		if(loadMovementDO.getTripServicedByDO()!=null){

			loadMovementTO.setTripServicedById(loadMovementDO.getTripServicedByDO().getTripServicedById());
			String serviceByTypeCode = null; //C, O, B, D
			//String servicedByType = null; //E(Emp), V(LoadMovementVendor)
			

			ServicedByDO servicedByDO = loadMovementDO.getTripServicedByDO().getServicedByDO();

			if(servicedByDO!=null){
				if(servicedByDO.getServiceByTypeDO()!=null){
					serviceByTypeCode = servicedByDO.getServiceByTypeDO().getServiceByTypeCode();
					loadMovementTO.setServiceByType(servicedByDO.getServiceByTypeDO().getServiceByTypeDesc());
				}
				if(!StringUtils.equals(serviceByTypeCode, LoadManagementUniversalConstants.DIRECT_CODE)){
					if(StringUtils.equals(servicedByDO.getServicedByType(), 
							LoadManagementUniversalConstants.VENDOR_CODE) && 
							servicedByDO.getLoadMovementVendorDO()!=null){
						loadMovementTO.setLoadMovementVendor(
								servicedByDO.getLoadMovementVendorDO().getBusinessName());
					}else if(StringUtils.equals(servicedByDO.getServicedByType(), 
							LoadManagementUniversalConstants.Employee_CODE) &&
							servicedByDO.getEmployeeDO()!=null){
						loadMovementTO.setLoadMovementVendor(
								servicedByDO.getEmployeeDO().getFirstName() + 
								servicedByDO.getEmployeeDO().getLastName());
					}
				}				
			}
			
			//Road

			if(StringUtils.isBlank(loadMovementDO.getVehicleType()) &&
					loadMovementDO.getTripServicedByDO().getTripDO()!=null){

				loadMovementTO.setExpectedDeparture(
						loadMovementDO.getTripServicedByDO().getTripDO().getDepartureTime());
				loadMovementTO.setExpectedArrival(
						loadMovementDO.getTripServicedByDO().getTripDO().getArrivalTime());
				
				//start
				if(StringUtils.isNotBlank(loadMovementDO.getRouteServicedTransportType())){
					
					if(loadMovementDO.getRouteServicedTransportType().equals
							(LoadManagementUniversalConstants.OTHERS_CODE)){
						loadMovementTO.setTransportNumber(LoadManagementUniversalConstants.OTHERS_CODE);
						loadMovementTO.setOtherTransportNumber(loadMovementDO.getRouteServicedTransportNumber());
						
					}else if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO()!=null){
						if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.AIR_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getFlightDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getFlightDO().getFlightNumber());
							}
						}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.TRAIN_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getTrainDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getTrainDO().getTrainNumber());
							}							
						}else if(StringUtils.equals(transportModeCode, LoadManagementUniversalConstants.ROAD_CODE)){
							if(loadMovementDO.getTripServicedByDO().getTripDO().getTransportDO().getVehicleDO()!=null){
								loadMovementTO.setTransportNumber(loadMovementDO.getTripServicedByDO().
										getTripDO().getTransportDO().getVehicleDO().getRegNumber());
							}							
						}
					}						
				}//end
			}
		}//End of setting  TripServicedBy
		

		List<LoadDispatchDetailsTO> loadDispatchDetailsTOs = new ArrayList<LoadDispatchDetailsTO>();
		Double totalWeight = 0.0;
		Double totalCdWeightPrint =0.0;
		int bagCount=0;
		if(loadMovementDO.getLoadConnectedDOs()!=null && loadMovementDO.getLoadConnectedDOs().size()>0){
			
			Set<LoadConnectedDO> loadConnectedDOList = loadMovementDO.getLoadConnectedDOs();
			for (LoadConnectedDO loadConnectedDO : loadConnectedDOList) {
				LoadDispatchDetailsTO loadDispatchDetailsTO = new LoadDispatchDetailsTO();
				loadDispatchDetailsTO.setLoadConnectedId(loadConnectedDO.getLoadConnectedId());
				loadDispatchDetailsTO.setWeight(loadConnectedDO.getDispatchWeight());
				loadDispatchDetailsTO.setCdWeight(loadConnectedDO.getConnectWeight());
				if(loadConnectedDO.getDispatchWeight()!=null){
					totalWeight+=loadConnectedDO.getDispatchWeight();
				}
				if(loadConnectedDO.getConnectWeight()!=null){
					totalCdWeightPrint+=loadConnectedDO.getConnectWeight();
				}
				
				loadDispatchDetailsTO.setTokenNumber(loadConnectedDO.getTokenNumber());
				loadDispatchDetailsTO.setRemarks(loadConnectedDO.getRemarks());
								
				if(loadConnectedDO.getManifestDO()!=null){

					loadDispatchDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
					loadDispatchDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
					loadDispatchDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
					loadDispatchDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
					
					if(loadConnectedDO.getManifestDO().getManifestLoadContent()!=null){
						loadDispatchDetailsTO.setDocType(loadConnectedDO.getManifestDO().
								getManifestLoadContent().getConsignmentName());				
					}
					if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
						loadDispatchDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().
								getDestinationCity().getCityName());
					}
				}
				bagCount++;
				loadDispatchDetailsTOs.add(loadDispatchDetailsTO);
			}	
		}
		loadMovementTO.setTotalWeightPrint(totalWeight);
		loadMovementTO.setTotalCdWeightPrint(totalCdWeightPrint);
		loadMovementTO.setTotalBagPrint(bagCount);
		
		Collections.sort(loadDispatchDetailsTOs);
		loadMovementTO.setLoadDispatchDetailsTOs(loadDispatchDetailsTOs);
		LOGGER.trace("LoadManagementCommonServiceImpl::loadMovementTransferConverter4Print::END------------>:::::::");
		return loadMovementTO;
	
	}

	@Override
	public LoadReceiveLocalTO printLoadReceiveLocalTO(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::printLoadReceiveLocalTO::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = null;
		LoadMovementDO loadMovementDO = null;

		loadMovementDO = loadManagementCommonDAO.
				getLoadMovementForReceiveLocalByReceive(loadManagementValidationTO);
		
		
		//LoadReceiveLocalTransferConverter
		if(loadMovementDO!=null){
			loadReceiveLocalTO = loadReceiveLocalTransferConverter4Print(loadMovementDO);
			
		} else {
			ExceptionUtil
					.prepareBusinessException(LoadManagementUniversalConstants.ERROR_IN_PRINTING_INVALED_GATEPASS_NUMBER);
			/*throw new CGBusinessException(
					LoadManagementUniversalConstants.ERROR_IN_PRINTING_INVALED_GATEPASS_NUMBER);*/
		}
		LOGGER.trace("LoadManagementCommonServiceImpl::printLoadReceiveLocalTO::END------------>:::::::");
		return loadReceiveLocalTO;
	}
	
	/**
	 * Load receive local transfer converter.
	 *
	 * @param loadMovementDO the load movement do
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private LoadReceiveLocalTO loadReceiveLocalTransferConverter4Print(
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::loadReceiveLocalTransferConverter4Print::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO = new LoadReceiveLocalTO();

		loadReceiveLocalTO.setMovementDirection(loadMovementDO.getMovementDirection());
		loadReceiveLocalTO.setGatePassNumber(loadMovementDO.getGatePassNumber());

		if(loadMovementDO.getProcessDO()!=null){
			loadReceiveLocalTO.setProcessId(loadMovementDO.getProcessDO().getProcessId());
		}
		if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
				LoadManagementUniversalConstants.RECEIVE_DIRECTION)){
			loadReceiveLocalTO.setLoadMovementId(loadMovementDO.getLoadMovementId());
			loadReceiveLocalTO.setReceivedAgainstId(loadMovementDO.getReceivedAgainst());	
			loadReceiveLocalTO.setHeaderReceivedStatus(loadMovementDO.getReceivedStatus());	
			loadReceiveLocalTO.setActualArrival(loadMovementDO.getReceivedAtTime());
			if(loadMovementDO.getLoadingDate()!=null){
				loadReceiveLocalTO.setReceiveDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
						(loadMovementDO.getLoadingDate()));			
			}
		}else{
			loadReceiveLocalTO.setReceivedAgainstId(loadMovementDO.getLoadMovementId());	
		}
		
		// vehicle
		if (StringUtils.isNotBlank(loadMovementDO.getVehicleType())) {
			// same city
			if (loadMovementDO.getVehicleDO() != null) {
				loadReceiveLocalTO.setVehicleNumber(loadMovementDO.getVehicleDO().getRegNumber());
			} else {
				if (StringUtils
						.isNotBlank(loadMovementDO.getVehicleRegNumber())) {
					loadReceiveLocalTO.setVehicleNumber(loadMovementDO.getVehicleRegNumber());
				}
			}
		} else {
			// different city
			if (StringUtils.isNotBlank(loadMovementDO
					.getRouteServicedTransportType())) {
				if (loadMovementDO.getRouteServicedTransportType().equals(
						LoadManagementUniversalConstants.OTHERS_CODE)) {
					loadReceiveLocalTO
							.setVehicleNumber(loadMovementDO
									.getRouteServicedTransportNumber());
					loadReceiveLocalTO.setOtherVehicleNumber(loadMovementDO
							.getRouteServicedTransportNumber());

				} else {
					if (loadMovementDO.getTripServicedByDO() != null
							&& loadMovementDO.getTripServicedByDO().getTripDO() != null) {
						loadReceiveLocalTO.setActualArrival(loadMovementDO
								.getTripServicedByDO().getTripDO()
								.getArrivalTime());

						if (loadMovementDO.getTripServicedByDO().getTripDO()
								.getTransportDO() != null
								&& loadMovementDO.getTripServicedByDO()
										.getTripDO().getTransportDO()
										.getVehicleDO() != null) {
							loadReceiveLocalTO.setVehicleNumber(loadMovementDO
									.getTripServicedByDO().getTripDO()
									.getTransportDO().getVehicleDO()
									.getRegNumber());
						}
					}
				}
			}
		}
		
		loadReceiveLocalTO.setDriverName(loadMovementDO.getDriverName());
		
		if(loadMovementDO.getOriginOfficeDO()!=null){

			final OfficeTO originOfficeTO = new OfficeTO();
			loadReceiveLocalTO.setOriginOfficeTO(originOfficeTO);
			CGObjectConverter.createToFromDomain(loadMovementDO.getOriginOfficeDO(), originOfficeTO);
			
			loadReceiveLocalTO.setOriginOffice( loadMovementDO.getOriginOfficeDO().getOfficeCode() + 
					CommonConstants.HYPHEN + loadMovementDO.getOriginOfficeDO().getOfficeName());
			//loadReceiveLocalTO.setOriginOfficeId(loadMovementDO.getOriginOfficeDO().getOfficeId());
			
			//TODO need to check whether we need regionalOffice or not
			//or we need to restrict only logged in regionalOffice
			/*OfficeTO regionalOfficeTO = organizationCommonService.getOfficeDetails(
					loadMovementDO.getOriginOfficeDO().getReportingRHO());
			loadReceiveLocalTO.setRegionalOffice(regionalOfficeTO.getOfficeCode() + 
					CommonConstants.HYPHEN + regionalOfficeTO.getOfficeName());*/
			
			if (loadMovementDO.getOriginOfficeDO().getCityId() != null) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(loadMovementDO.getOriginOfficeDO().getCityId());
				cityTO = getCity(cityTO);
				if (cityTO != null) {
					loadReceiveLocalTO.setDestCity(cityTO.getCityName());
					
				}
			}

		}

		if(loadMovementDO.getDestOfficeDO()!=null){
			loadReceiveLocalTO.setDestOffice(loadMovementDO.getDestOfficeDO().getOfficeCode() +
					CommonConstants.HYPHEN +  loadMovementDO.getDestOfficeDO().getOfficeName());
			if(loadMovementDO.getDestOfficeDO().getOfficeTypeDO()!=null){
				loadReceiveLocalTO.setDestOfficeType(
						loadMovementDO.getDestOfficeDO().getOfficeTypeDO().getOffcTypeDesc());
			}

			loadReceiveLocalTO.setDestOfficeId(loadMovementDO.getDestOfficeDO().getOfficeId());
			//loggedInoffice treated as destination Office
			loadReceiveLocalTO.setLoggedInOfficeId(loadMovementDO.getDestOfficeDO().getOfficeId());
			//officeId~cityId~ReportingRHOId
			loadReceiveLocalTO.setLoggedInOffice(loadMovementDO.getDestOfficeDO().getOfficeId() + 
					CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getCityId() +
					CommonConstants.TILD + loadMovementDO.getDestOfficeDO().getReportingRHO());
			
			
		}

		if(loadMovementDO.getTransportModeDO()!=null){
			loadReceiveLocalTO.setTransportMode(loadMovementDO.getTransportModeDO().
					getTransportModeDesc());
			loadReceiveLocalTO.setTransportModeDetails(
					loadMovementDO.getTransportModeDO().getTransportModeId() +
					CommonConstants.TILD + 
					loadMovementDO.getTransportModeDO().getTransportModeCode() +
					CommonConstants.TILD + 
					loadMovementDO.getTransportModeDO().getTransportModeDesc());
		}

		
		List<LoadReceiveDetailsTO> loadReceiveDetailsTOs = new ArrayList<LoadReceiveDetailsTO>();
		Double totalWeight = 0.0;
		int bagCount=0;
		//grid dtails
		if (StringUtils.equals(loadMovementDO.getMovementDirection(),
				LoadManagementUniversalConstants.RECEIVE_DIRECTION)
				&& (loadMovementDO.getLoadConnectedDOs() != null && loadMovementDO
						.getLoadConnectedDOs().size() > 0)) {
			Set<LoadConnectedDO> loadConnectedDOList = loadMovementDO.getLoadConnectedDOs();

			for (LoadConnectedDO loadConnectedDO : loadConnectedDOList) {
				LoadReceiveDetailsTO loadReceiveDetailsTO = new LoadReceiveDetailsTO();
				loadReceiveDetailsTO.setWeight(loadConnectedDO.getDispatchWeight());
				
				if(loadConnectedDO.getDispatchWeight()!=null){
					totalWeight+=loadConnectedDO.getDispatchWeight();
				}
				
				if(loadConnectedDO.getManifestDO()!=null){
					loadReceiveDetailsTO.setManifestId(loadConnectedDO.getManifestDO().getManifestId());
					loadReceiveDetailsTO.setLoadNumber(loadConnectedDO.getManifestDO().getManifestNo());
					loadReceiveDetailsTO.setManifestWeight(loadConnectedDO.getManifestDO().getManifestWeight());
					
					if(loadConnectedDO.getManifestDO().getManifestLoadContent()!=null){
						loadReceiveDetailsTO.setDocType(loadConnectedDO.getManifestDO().
								getManifestLoadContent().getConsignmentName());				
					}
					if(loadConnectedDO.getManifestDO().getDestinationCity()!=null){
						loadReceiveDetailsTO.setManifestDestCity(loadConnectedDO.getManifestDO().getDestinationCity().getCityCode());
						/*loadReceiveDetailsTO.setManifestDestCityDetails(loadConnectedDO.getManifestDO().getDestinationCity().getCityId() +
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityCode() + 
								CommonConstants.TILD + loadConnectedDO.getManifestDO().getDestinationCity().getCityName());*/
						loadReceiveDetailsTO.setManifestDestCityDetails(loadConnectedDO.getManifestDO().getDestinationCity().getCityName());
					}

					if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
							LoadManagementUniversalConstants.DISPATCH_DIRECTION)){
						loadReceiveDetailsTO.setLockNumber(loadConnectedDO.getManifestDO().getBagLockNo());
					}
				}
				if(StringUtils.equals(loadMovementDO.getMovementDirection(), 
						LoadManagementUniversalConstants.RECEIVE_DIRECTION)){
					loadReceiveDetailsTO.setLoadConnectedId(loadConnectedDO.getLoadConnectedId());
					loadReceiveDetailsTO.setRemarks(loadConnectedDO.getRemarks());
					loadReceiveDetailsTO.setLockNumber(loadConnectedDO.getLockNumber());
					
					if(loadConnectedDO.getReceivedStatus().equalsIgnoreCase(LoadManagementUniversalConstants.EXCESS_STATUS)){
					loadReceiveDetailsTO.setReceivedStatus(LoadManagementUniversalConstants.EXCESS_LABEL);
					}else if(loadConnectedDO.getReceivedStatus().equalsIgnoreCase(LoadManagementUniversalConstants.RECEIVE_STATUS)){
						loadReceiveDetailsTO.setReceivedStatus(LoadManagementUniversalConstants.RECEIVE_LABEL);
						}
					else if(loadConnectedDO.getReceivedStatus().equalsIgnoreCase(LoadManagementUniversalConstants.NOT_RECEIVED_STATUS)){
						loadReceiveDetailsTO.setReceivedStatus(LoadManagementUniversalConstants.NOT_RECEIVED_LABEL);
						}
				}
				bagCount++;
				loadReceiveDetailsTOs.add(loadReceiveDetailsTO);
			}
			loadReceiveLocalTO.setTotalWeightPrint(totalWeight);
			loadReceiveLocalTO.setTotalBagPrint(bagCount);
			Collections.sort(loadReceiveDetailsTOs);
		}
		loadReceiveLocalTO.setLoadReceiveDetailsTOs(loadReceiveDetailsTOs);
		LOGGER.trace("LoadManagementCommonServiceImpl::loadReceiveLocalTransferConverter4Print::END------------>:::::::");
		return loadReceiveLocalTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.service.LoadManagementCommonService#createProcessNumber(com.ff.tracking.ProcessTO, com.ff.organization.OfficeTO)
	 */
	@Override
	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService.createProcessNumber(processTO,officeTO);
	}

	@Override
	public List<TripServicedByTO> getTripServicedByTOsForTransport(
			TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException {
		return routeServicedCommonService.getTripServicedByTOsByRouteIdModeIdServiceByTypeIdVendorId(tripServicedByTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.service.LoadManagementCommonService#calcAndGetOperatingLevel(com.ff.loadmanagement.LoadManagementTO, com.ff.loadmanagement.ManifestTO)
	 */
	@Override
	public Integer calcAndGetOperatingLevel(LoadManagementTO loadManagementTO,
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LoadManagementCommonServiceImpl::calcAndGetOperatingLevel::START------------>:::::::");
		Integer operatingLevel =null;
		try {
			OfficeTO loggedInOfficeTO = null;
			if(udaanContextService.getUserInfoTO()!=null && udaanContextService.getUserInfoTO().getOfficeTo()!=null){
				loggedInOfficeTO = udaanContextService.getUserInfoTO().getOfficeTo();
			}else{
				loggedInOfficeTO = new OfficeTO();
				return null;
			}

			OutManifestBaseTO outManifestBaseTO = new OutManifestBaseTO(); 
			outManifestBaseTO.setDestinationOfficeId(loadManagementTO.getDestOfficeId());
			outManifestBaseTO.setLoginOfficeId(loggedInOfficeTO.getOfficeId());
			
			if(manifestTO.getDestinationCityTO()!=null && 
					!StringUtil.isEmptyInteger(manifestTO.getDestinationCityTO().getCityId())){
				outManifestBaseTO.setDestinationCityId(manifestTO.getDestinationCityTO().getCityId());
			}else{
				outManifestBaseTO.setDestinationCityId(loggedInOfficeTO.getCityId());
			}
			
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(loggedInOfficeTO.getOfficeId());
			
			//to calc operating level
			operatingLevel = outManifestUniversalService.calcOperatingLevel(outManifestBaseTO, officeTO);
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Exception Occured in::LoadManagementCommonServiceImpl::calcAndGetOperatingLevel() :: " + e);
		}

		LOGGER.trace("LoadManagementCommonServiceImpl::calcAndGetOperatingLevel::END------------>:::::::");
		return operatingLevel;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.service.LoadManagementCommonService#setOperatingLevelAndOfficeToManifest(java.lang.Integer, com.ff.loadmanagement.ManifestTO)
	 */
	@Override
	public void setOperatingLevelAndOfficeToManifest(
			Integer operatingLevel, ManifestTO manifestTO) {
		
		OfficeTO loggedInOfficeTO = null;
		if(udaanContextService.getUserInfoTO()!=null && udaanContextService.getUserInfoTO().getOfficeTo()!=null){
			loggedInOfficeTO = udaanContextService.getUserInfoTO().getOfficeTo();
		}
		
		//manifestTO.setOperatingLevel(operatingLevel);
		if(loggedInOfficeTO!=null){
			manifestTO.setOperatingOffice(loggedInOfficeTO.getOfficeId());
		}
	}

	@Override
	public void sendSMS(LoadMovementTO loadMovementTO)
			throws CGBusinessException, CGSystemException {
		// SMS Format: Gate pass No, Flight, No. of Bags, Weight.
		StringBuilder msg = new StringBuilder(100);
		msg.append("GatePass No. : ")
				.append(loadMovementTO.getGatePassNumber());
		msg.append(", ")
				.append(loadMovementTO.getEmailTransportLabel())
				.append(" : ")
				.append(StringUtils.isNotBlank(loadMovementTO
						.getEmailTransportNumber()) ? loadMovementTO
						.getEmailTransportNumber() : loadMovementTO
						.getEmailVehicleNumber());
		msg.append(", No. of Bags : ").append(loadMovementTO.getTotalBags());
		msg.append(", Total Weight : ").append(loadMovementTO.getTotalWeight());
		// smsSenderService.sendSMS(loadMovementTO.getDestOfficeTO().getPhone(),
		// msg.toString(), null);

		// smsSenderService.sendSMS("7411624044", msg.toString(), null);
		Integer userId = null;
		if (udaanContextService.getUserInfoTO() != null
				&& udaanContextService.getUserInfoTO().getUserto() != null) {
			userId = udaanContextService.getUserInfoTO().getUserto()
					.getUserId();
		}
		SmsSenderTO smsSenderTO = new SmsSenderTO();
		smsSenderTO.setContactNumber(loadMovementTO.getDestOfficeTO().getPhone());
		//smsSenderTO.setContactNumber("7411624044");
		smsSenderTO.setMessage(msg.toString());
		smsSenderTO.setModuleName(CommonConstants.MODULE_NAME_DISPATCH);
		smsSenderTO.setUserId(userId);

		SmsSenderUtil.sendSms(smsSenderTO);
	}
}
