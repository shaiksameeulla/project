/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.tracking.consignmentTracking.converter.ConsignmentTrackingConverter;
import com.ff.admin.tracking.consignmentTracking.dao.ConsignmentTrackingDAO;
import com.ff.booking.BookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.geography.StateTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessMapTO;
import com.ff.tracking.TrackingConsignmentTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;
import com.ff.universe.tracking.service.TrackingUniversalService;

/**
 * @author uchauhan
 * 
 */
public class ConsignmentTrackingServiceImpl implements
		ConsignmentTrackingService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentTrackingServiceImpl.class);

	private ConsignmentTrackingDAO consignmentTrackingDAO;

	private GeographyCommonService geographyCommonService;
	
	private TrackingUniversalService trackingUniversalService;
	
	private BusinessCommonService businessCommonService;

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setConsignmentTrackingDAO(
			ConsignmentTrackingDAO consignmentTrackingDAO) {
		this.consignmentTrackingDAO = consignmentTrackingDAO;
	}	

	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	public void setBusinessCommonService(BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	@Override
	public TrackingConsignmentTO viewTrackInformation(String consgNum,
			String refNum, String loginUserType) throws CGSystemException, CGBusinessException {
		LOGGER.debug("ConsignmentTrackingServiceImpl::viewTrackInformation()::START");
		TrackingConsignmentTO trackingConsignmentTO = new TrackingConsignmentTO();
		ConsignmentTO consgTO = new ConsignmentTO();
		ConsignmentDO consignmentDO =null;
		BookingDO bookingDO = null;
		
		//Booking information
		List<BookingDO> bookingDOList = consignmentTrackingDAO.viewTrackInformation(consgNum, refNum);
		if(StringUtils.isNotEmpty(refNum)){
			// Multiple records found for the reference number then need to show an alert “Multiple details found with Reference No. Please track with Consignment No”.
			if(!StringUtil.isEmptyList(bookingDOList) && bookingDOList.size() > 1){
				ExceptionUtil.prepareBusinessException(												
						AdminErrorConstants.MULTIPLE_RESULTS_FOUND, new String[] {
								"Reference No", "Consignment No" });
			}else if(StringUtil.isEmptyList(bookingDOList)){
				//	For the reference no record not found in booking table means need to show an alert “Tracking details not found”.
				throw new CGBusinessException(AdminErrorConstants.TRACKING_DETAILS_NOT_FOUND);
			}
		}
		if (!StringUtil.isEmptyList(bookingDOList)) {
			bookingDO = bookingDOList.get(0);
			consgNum = bookingDO.getConsgNumber();
			String username = null;
			Integer userId = bookingDO.getCreatedBy();
			if (userId != null) {
				UserDO userDO = consignmentTrackingDAO.getCreatedByDeatils(userId);
				username = userDO.getUserName();
			}
			BookingParcelTO bookingTO = ConsignmentTrackingConverter
					.convertBookingDO(bookingDO);
			if (StringUtils.isNotEmpty(bookingDO.getPickRunsheetNo())) {
				trackingConsignmentTO.setPckDate((DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(bookingDO
								.getCreatedDate())).toString());								
			}
			trackingConsignmentTO.setBookingTO(bookingTO);
			trackingConsignmentTO.setBookedBy(username);
		}
				
		/*In case of tracking with reference no number get the corresponding consignment number from booking table 
		 * and Provide the tracking information for the respective consignment number. So by default refNum making as null */
		
		// List<ConsignmentDO> consignmentDOList = consignmentTrackingDAO.getConsignmentDtls(consgNum, refNum);
		List<ConsignmentDO> consignmentDOList = consignmentTrackingDAO.getConsignmentDtls(consgNum, null);
		if(!StringUtil.isEmptyList(consignmentDOList)){
			consignmentDO = consignmentDOList.get(0);
			consgTO = (ConsignmentTO) CGObjectConverter.createToFromDomain(consignmentDO, consgTO);
			trackingConsignmentTO.setConsignmentTO(consgTO);
			// consigner consignee details
			setConsignorConsigneeDtls(consgTO, consignmentDO);

			//Request from client : show the final weight which is considered for billing in Final Weight Field
			if(!StringUtil.isNull(trackingConsignmentTO.getBookingTO())){
				trackingConsignmentTO.getBookingTO().setActualWeight(consignmentDO.getActualWeight());
				trackingConsignmentTO.getBookingTO().setFinalWeight(consignmentDO.getFinalWeight());
			}

			// Child Consignments details
			trackingConsignmentTO.setChildCNTO(getAndSetChildConsignmentDtls(consignmentDO));
			// set Content desc
			if (!StringUtil.isNull(consignmentDO.getCnContentId())) {
				CNContentTO cnContentTO = new CNContentTO();
				if(StringUtils.equalsIgnoreCase(consignmentDO.getCnContentId().getCnContentCode(), "999")){
					cnContentTO.setCnContentDesc(consignmentDO.getCnContentId().getCnContentDesc() +" - "+ consignmentDO.getOtherCNContent());
				}else{
					cnContentTO.setCnContentDesc(consignmentDO.getCnContentId().getCnContentDesc());
				}
				consgTO.setCnContents(cnContentTO);
			}
			//If booking details are not available then consider the consignment details for booking date and creation date.
			if (StringUtil.isNull(bookingDO)) {
				String username = null;
				Integer userId = consignmentDO.getCreatedBy();
				if (userId != null) {
					UserDO userDO = consignmentTrackingDAO.getCreatedByDeatils(userId);
					if(!StringUtil.isNull(userDO))
						username = userDO.getUserName();
				}
				BookingParcelTO bookingTO = ConsignmentTrackingConverter.convertConsignmentDO(consignmentDO);
				trackingConsignmentTO.setBookingTO(bookingTO);
				trackingConsignmentTO.setBookedBy(username);
				
				//Set Customer code
				if(!StringUtil.isEmptyInteger(consgTO.getCustomer())){
					CustomerTO customerTO = businessCommonService.getCustomer(consgTO.getCustomer());
					if(!StringUtil.isNull(customerTO)){
						consgTO.setCustomerTO(customerTO);
					}
				}
			}
		}
				
		if(StringUtil.isNull(bookingDO) && StringUtil.isNull(consignmentDO)){
			throw new CGBusinessException(AdminErrorConstants.BOOKING_DETAILS_NOT_FOUND);
		}
		// Detailed consignment tracking information		
		//If only booking data is available :		
		if(!StringUtil.isNull(bookingDO) && StringUtil.isNull(consignmentDO)){
			getPartialConsignmentInfo(trackingConsignmentTO, bookingDO);
		} else {
			getConsignmentDetailedTracking(trackingConsignmentTO, loginUserType);			
		}
				
		LOGGER.debug("ConsignmentTrackingServiceImpl::viewTrackInformation()::END");
		return trackingConsignmentTO;
	}

	private List<ChildConsignmentTO> getAndSetChildConsignmentDtls(ConsignmentDO consignmentDO) {
		List<ChildConsignmentTO> childDetailsTO = null;
		Set<ChildConsignmentDO> childDetailsDO = consignmentDO.getChildCNs();
		if (!StringUtil.isNull(childDetailsDO)) {
			childDetailsTO = ConsignmentTrackingConverter.convertChildDetailsDO(childDetailsDO);
			Collections.sort(childDetailsTO);
		}
		return childDetailsTO;
	}

	private void getConsignmentDetailedTracking(TrackingConsignmentTO trackingConsignmentTO,String loginUserType)
			throws CGSystemException, CGBusinessException {
		String consgNum = trackingConsignmentTO.getBookingTO().getConsgNumber();
		long startTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("Consignment No :: "+ consgNum +" Tracking SP Start Time :: "+startTimeInMilis);
		List<ProcessMapDO> processMapDOs = consignmentTrackingDAO.getDetailedTrackingInformation(consgNum);
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		LOGGER.debug("Consignment No :: "+ consgNum +" Tracking SP Duration :: "+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		if (StringUtil.isEmptyList(processMapDOs)) {
			throw new CGBusinessException(AdminErrorConstants.PROCESS_DETAILS_NOT_FOUND);
		}
		if (!StringUtil.isEmptyColletion(processMapDOs)) {
			long startTimeInMilis1 = System.currentTimeMillis();
			LOGGER.debug("Consignment No :: "+ consgNum +" setProcessMapTO Start Time :: "+startTimeInMilis1);
			if(StringUtils.equalsIgnoreCase(loginUserType, CommonConstants.USER_TYPE_CUSTOMER)){
				setProcessMapToForCustomer(processMapDOs, trackingConsignmentTO);
			}else{
				setProcessMapTO(processMapDOs, trackingConsignmentTO);
			}
						
			long endTimeInMilis1 = System.currentTimeMillis();
			long diff1 = endTimeInMilis1 - startTimeInMilis1;
			LOGGER.debug("Consignment No :: "+ consgNum +" setProcessMapTO Duration :: "+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff1));
		}
	}

	private void getPartialConsignmentInfo(
			TrackingConsignmentTO trackingConsignmentTO, BookingDO bookingDO)
			throws CGBusinessException, CGSystemException {
		List<ProcessMapTO> processMapTOs = new ArrayList<>();
		ProcessMapTO processMapTO=null;
		if(StringUtils.isNotEmpty(bookingDO.getPickRunsheetNo())){
			//Update Pickup
			//In detailed tracking only show the pickup information
			ProcessMapDO processMapDO = new ProcessMapDO();
			processMapDO.setProcessNumber(bookingDO.getUpdatedProcess().getProcessCode());
			processMapDO.setKey1("originOff");
			processMapDO.setKey2("orgCity");
			processMapTO = preparePickupPath(bookingDO, processMapDO);
			processMapTOs.add(processMapTO);
			
			//Consignment status
			if(StringUtils.equalsIgnoreCase(bookingDO.getUpdatedProcess().getProcessCode(), CommonConstants.PROCESS_PICKUP)){
				String cnStatus = getConsignmentStatus(processMapDO);
				cnStatus = cnStatus + " - "+ processMapTO.getConsignmentPath();
				trackingConsignmentTO.getBookingTO().setCnStatus(cnStatus);
			}
		}
		if(!StringUtil.isNull(bookingDO.getUpdatedProcess()) 
				&& StringUtils.equalsIgnoreCase(bookingDO.getUpdatedProcess().getProcessCode(), CommonConstants.PROCESS_BOOKING)){
			//Normal Booking
			//In detailed tracking only show the booking information
			ProcessMapDO processMapDO = new ProcessMapDO();
			processMapDO.setProcessNumber(bookingDO.getUpdatedProcess().getProcessCode());
			processMapDO.setKey1("originOff");
			processMapDO.setKey2("orgCity");
			processMapTO = prepareBookingPath(bookingDO, processMapDO);
			processMapTOs.add(processMapTO);
			
			//Consignment status
			String cnStatus = getConsignmentStatus(processMapDO);
			cnStatus = cnStatus + " - "+ processMapTO.getConsignmentPath();
			trackingConsignmentTO.getBookingTO().setCnStatus(cnStatus);
		}
		trackingConsignmentTO.setProcessMapTO(processMapTOs);
		//Flash a message Complete Consignment details still not available for Tracking
		trackingConsignmentTO.setIncompleteData(AdminErrorConstants.COMPLETE_CONSIGNMENT_DETAILS_NOT_AVAILABLE);		
	}

	private ProcessMapTO prepareBookingPath(BookingDO bookingDO,
			ProcessMapDO processMapDO) throws CGBusinessException,
			CGSystemException {
		ProcessMapTO processMapTO = new ProcessMapTO();
		if(!StringUtil.isEmptyInteger(bookingDO.getBookingOfficeId())){
			//	processMapDO.setKey1("originOff");
			processMapDO.setValue1(bookingDO.getBookingOfficeId().toString());
			//	processMapDO.setKey2("orgCity");	
			CityTO cityTO=geographyCommonService.getCityByOfficeId(bookingDO.getBookingOfficeId());
			if(!StringUtil.isNull(cityTO)){
				processMapDO.setValue2(cityTO.getCityId().toString());
			}
		}
		processMapDO.setKey3("weight");
		processMapDO.setValue3(bookingDO.getFianlWeight().toString());
		processMapDO.setKey4("noOfPieces");
		if(!StringUtil.isEmptyInteger(bookingDO.getNoOfPieces())){
			processMapDO.setValue4(bookingDO.getNoOfPieces().toString());
		}
		String consgTemplate = bookingDO.getUpdatedProcess().getTrackingTxt();
		//	"Booked at Office : {originOff} in {city} having Weight : {weight} Kg., No. Of pieces : {noOfPieces}";
		processMapTO.setConsignmentPath(trackingUniversalService.formatArtifactPath(consgTemplate, processMapDO));
		processMapTO.setManifestType(bookingDO.getUpdatedProcess().getProcessDesc());
		if (bookingDO.getBookingDate() != null) {
			processMapTO.setDateAndTime(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(bookingDO.getBookingDate()));
		}
		return processMapTO;
	}

	private ProcessMapTO preparePickupPath(BookingDO bookingDO,
			ProcessMapDO processMapDO) throws CGBusinessException,
			CGSystemException {
		ProcessMapTO processMapTO = new ProcessMapTO();
		//	processMapDO.setKey1("originOff");	
		Integer originOff=bookingDO.getBookingOfficeId();
		if(!StringUtil.isEmptyInteger(originOff)){
			processMapDO.setValue1(originOff.toString());
		}
		//	processMapDO.setKey2("orgCity");
		CityTO cityTO=geographyCommonService.getCityByOfficeId(originOff);
		if(!StringUtil.isNull(cityTO)){
			processMapDO.setValue2(cityTO.getCityId().toString());
		}
		processMapDO.setKey4("custName");
		if(!StringUtil.isNull(bookingDO.getCustomerId())){
			processMapDO.setValue4(bookingDO.getCustomerId().getCustomerId().toString());
		}				
		String consgTemplate="Picked up from Customer {custName}, {orgCity}";				
		processMapTO.setConsignmentPath(trackingUniversalService.formatArtifactPath(
				consgTemplate, processMapDO));
		processMapTO.setManifestType(CommonConstants.PICKUP_PROCESS_TYPE);
		if (bookingDO.getCreatedDate() != null) {
			processMapTO.setDateAndTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(bookingDO.getCreatedDate()));
		}
		return processMapTO;
	}

	private void setConsignorConsigneeDtls(ConsignmentTO consgTO,ConsignmentDO consignmentDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentTrackingServiceImpl::setConsignorConsigneeDtls()::START");
		String pincode = "";
		Integer stateId = null;
		CityTO city = null;
		StateTO state = null;
		ConsignorConsigneeTO consignee = new ConsignorConsigneeTO();
		ConsigneeConsignorDO consignee1 = null;

		ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
		ConsigneeConsignorDO consignor1 = null;
		consignee1 = consignmentDO.getConsignee();
		if (!StringUtil.isNull(consignee1)) {
			CGObjectConverter.createToFromDomain(consignee1, consignee);
		}
		consgTO.setConsigneeTO(consignee);

		consignor1 = consignmentDO.getConsignor();
		if (!StringUtil.isNull(consignor1)) {
			CGObjectConverter.createToFromDomain(consignor1, consignor);
		}

		consgTO.setConsignorTO(consignor);
		
		Integer officeId = consignmentDO.getOrgOffId();
		OfficeTO offcTO = trackingUniversalService.getReportingOffice(officeId);
		if (!StringUtil.isNull(offcTO)) {
			//Pincode id is not mapped with office. So taking pincode from office..
			pincode = offcTO.getPincode();
		}
		consignor.setOrgPincode(pincode);
		city = geographyCommonService.getCity(pincode);
		if (!StringUtil.isNull(city)) {
			stateId = city.getState();
			consignor.setOrgCity(city.getCityName());
		}

		state = geographyCommonService.getState(stateId);
		if (!StringUtil.isNull(state)) {
			consignor.setOrgState(state.getStateName());
		}

		if (!StringUtil.isNull(consignmentDO.getDestPincodeId())) {
			pincode = consignmentDO.getDestPincodeId().getPincode();
			consignee.setDestPincode(pincode);
		}

		city = geographyCommonService.getCity(pincode);
		if (!StringUtil.isNull(city)) {
			stateId = city.getState();
			consignee.setDestCity(city.getCityName());
		}

		state = geographyCommonService.getState(stateId);
		if (!StringUtil.isNull(state)) {
			consignee.setDestState(state.getStateName());
		}
		LOGGER.trace("ConsignmentTrackingServiceImpl::setConsignorConsigneeDtls()::END");
	}

	private void setProcessMapToForCustomer(List<ProcessMapDO> processMapDOs,
			TrackingConsignmentTO trackingConsignmentTO) throws NumberFormatException, CGBusinessException, CGSystemException {
		String cnStatus = null;
		List<ProcessMapTO> processTOs = new ArrayList<>();
		for (ProcessMapDO processMapDO : processMapDOs) {
			if(StringUtils.equalsIgnoreCase(processMapDO.getArtifactType(), CommonConstants.CONSIGNMENT)){
				ProcessMapTO mapTO = new ProcessMapTO();
				mapTO.setDateAndTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(processMapDO.getProcessDate()));
				
				String manifestType = "";
				String consignmentPath = "";
				String orgOffice = "";
				OfficeTO officeTO = trackingUniversalService.getOfficeByOfficeId(Integer.parseInt(processMapDO.getValue1()));
				orgOffice = officeTO.getOfficeName();
				
				switch (processMapDO.getProcessNumber()) {
				case CommonConstants.PROCESS_PICKUP:
					manifestType = "Picked up";					
					consignmentPath = orgOffice;					
					break;
				case CommonConstants.PROCESS_BOOKING:
					manifestType = "Booked at";
					consignmentPath = orgOffice;
					break;
				case CommonConstants.PROCESS_DISPATCH:
					manifestType = "Forwarded from";
					consignmentPath = orgOffice +" via Bag No "+processMapDO.getValue6();
					break;
				case CommonConstants.PROCESS_RECEIVE:
					manifestType = "Received at";
					consignmentPath = orgOffice;
					break;
				case CommonConstants.PROCESS_HELD_UP:
					manifestType = "Heldup By ";
					consignmentPath = orgOffice;
					break;
				case CommonConstants.PROCESS_STOP_DELIVERY:
					manifestType = "Stop Delivered at ";
					consignmentPath = orgOffice;
					break;	
				case CommonConstants.PROCESS_DELIVERY_RUN_SHEET:					
					if(!StringUtils.equalsIgnoreCase(processMapDO.getValue4(), CommonConstants.DELIVERY_STATUS_OUT_DELIVERY)){
						manifestType = "Out for delivery from";						
						consignmentPath = orgOffice +" via DRS No "+ processMapDO.getValue3();
						mapTO.setManifestType(manifestType);
						mapTO.setConsignmentPath(consignmentPath);
						processTOs.add(mapTO);
						
						mapTO = new ProcessMapTO();
						mapTO.setDateAndTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(processMapDO.getProcessDate()));
					}
					if(StringUtils.equalsIgnoreCase(processMapDO.getValue4(), CommonConstants.DELIVERY_STATUS_PENDING)){
						manifestType = "Pending at ";						
						consignmentPath = orgOffice;
					}else if(StringUtils.equalsIgnoreCase(processMapDO.getValue4(), CommonConstants.DELIVERY_STATUS_DELIVERED)){
						manifestType = "Delivered to";
						consignmentPath = processMapDO.getValue6();
					}if(StringUtils.equalsIgnoreCase(processMapDO.getValue4(), CommonConstants.CONSIGNMENT_STATUS_RTO_DRS)){
						manifestType = "RTO Delivered to";
						consignmentPath = processMapDO.getValue6();
					}
					break;
				default:
					manifestType = processMapDO.getProcessNumber();
					break;
				}
				mapTO.setManifestType(manifestType);
				mapTO.setConsignmentPath(consignmentPath);
				
				processTOs.add(mapTO);				
			}			
		}
		//IN CASE OF DELIVERY WE MAY GET EXTRA TOS FOR OUT FOR DELIVERY STATUS
		if (!StringUtil.isEmptyColletion(processMapDOs)) {
			ProcessMapDO processMapDO = processMapDOs.get(processMapDOs.size()-1);
			cnStatus = getConsignmentStatus(processMapDO);
			// cnStatus = cnStatus + " - "+ mapTO.getConsignmentPath();
		}
		trackingConsignmentTO.setProcessMapTO(processTOs);
		trackingConsignmentTO.getBookingTO().setCnStatus(cnStatus);
	}
	private void setProcessMapTO(
			List<ProcessMapDO> processMapDOs,
			TrackingConsignmentTO trackingConsignmentTO)
			throws CGSystemException, NumberFormatException,
			CGBusinessException {
		LOGGER.trace("ConsignmentTrackingServiceImpl::setProcessMapTO()::START");
		ProcessMapTO processMapTO = null;
		List<ProcessMapTO> processTOs = new ArrayList<>();
		Map<String, ProcessDO> codePathMap = getAllPath();
		String cnStatus = null;
		String drsNo = null;
		String newDrsNo = null;
		for (ProcessMapDO processMapDO : processMapDOs) {
			String processCode = processMapDO.getProcessNumber();	
			ProcessDO processDO = codePathMap.get(processCode);			
			processMapTO = new ProcessMapTO();
			// RTH/RTO Manifest Type should be specific
			// Key12 - contains MANIFEST_TYPE in SP for manifest queries
			String manifestType = null;
			if (processCode.equalsIgnoreCase(CommonConstants.PROCESS_RTO_RTH) 
					&& StringUtils.equalsIgnoreCase(processMapDO.getKey12(),"manifestType")) {	
				if(StringUtils.equalsIgnoreCase(processMapDO.getValue12(), CommonConstants.MANIFEST_TYPE_RTH)){
					manifestType = "RTH Manifest";		
				}else if(StringUtils.equalsIgnoreCase(processMapDO.getValue12(), CommonConstants.MANIFEST_TYPE_RTO)){
					manifestType = "RTO Manifest";			
				}
			}else{
				manifestType = processDO.getProcessName();
			}
			processMapTO.setManifestType(manifestType);
			if (processMapDO.getProcessDate() != null) {
				processMapTO.setDateAndTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(processMapDO.getProcessDate()));
			}					
			String path = trackingUniversalService.formatArtifactPath(processDO.getTrackingTxt(), processMapDO);			
			processMapTO.setConsignmentPath(path);
						
			if (processCode.equalsIgnoreCase(CommonConstants.PROCESS_DELIVERY_RUN_SHEET)) {								
				//Add Pending/Delivered consignment path
				if (StringUtils.isNotEmpty(processMapDO.getValue4())
						&& !StringUtils.equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_OUT_DELIVERY, processMapDO.getValue4())) {
					// Logic added to create a new row for 'Out for delivery' for each new DRS preparation
					if (StringUtils.isNotEmpty(processMapDO.getValue3()) && StringUtils.equalsIgnoreCase(processMapDO.getKey3(),"DRSNo")) {
						newDrsNo = processMapDO.getValue3();
						if(StringUtils.isNotEmpty(newDrsNo) && !newDrsNo.equalsIgnoreCase(drsNo)){
							drsNo = newDrsNo;
							processTOs.add(processMapTO);
						}else{
							newDrsNo = null;
						}
					}
					processMapTO.setDateAndTime(processMapDO.getValue9());
//					processTOs.add(processMapTO);
					processMapTO = addDeliveryOrPendingStatus(processMapDO,processDO);
					processMapTO.setManifestType(processDO.getProcessName());
				}else{
					processMapDO.setValue4(UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_OUT_DELIVERY);
				}
			}
			processTOs.add(processMapTO);
			//IN CASE OF DELIVERY WE MAY GET EXTRA TOS FOR OUT FOR DELIVERY STATUS
			if (processMapDOs.size() <= processTOs.size()) {
				cnStatus = getConsignmentStatus(processMapDO);
				cnStatus = cnStatus + " - "+ processMapTO.getConsignmentPath();
			}
		}
		LOGGER.trace("ConsignmentTrackingServiceImpl::setProcessMapTO()::END");
//		Collections.sort(processTOs);
		trackingConsignmentTO.setProcessMapTO(processTOs);
		trackingConsignmentTO.getBookingTO().setCnStatus(cnStatus);
	}

	private ProcessMapTO addDeliveryOrPendingStatus(ProcessMapDO processMapDO, ProcessDO processDO)
			throws CGBusinessException, CGSystemException {
		String processKey = processMapDO.getKey4();
		String processValue = processMapDO.getValue4();
		ProcessMapTO processMapTO=null;
		if (StringUtils.isNotEmpty(processKey) && processKey.equalsIgnoreCase("status") && StringUtils.isNotEmpty(processValue)) {
			String template = null;
			if(StringUtils.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED, processValue)){
				processMapDO.setValue4(UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_DELIVERED);
				template = UniversalTrackingConstants.DELIVERED_CN_PATH;
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS, processValue)){
				processMapDO.setValue4(UniversalTrackingConstants.DELIVERY_STATUS_DESCRIPTION_RTO_DELIVERED);
				template = UniversalTrackingConstants.RTO_DELIVERED_CN_PATH;
			}else if(StringUtils.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_PENDING, processValue)){
				processMapDO.setValue4(UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_PENDING);
				template = UniversalTrackingConstants.PENDING_CN_PATH;
			}else{
				processMapDO.setValue4(UniversalDeliveryContants.DELIVERY_STATUS_DESCRIPTION_OUT_DELIVERY);
			}
			if(StringUtils.isNotEmpty(template)){
				String consignmentPath = trackingUniversalService.formatArtifactPath(template,processMapDO);
				processMapTO = new ProcessMapTO();
				processMapTO.setConsignmentPath(consignmentPath);
				if(StringUtils.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_PENDING, processValue)){
					processMapTO.setDateAndTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(processMapDO.getProcessDate()));
				}else{
					processMapTO.setDateAndTime(processMapDO.getValue2());
				}				
			}
		}
		return processMapTO;
	}

	private String getConsignmentStatus(ProcessMapDO processMapDO) {
		String cnStatus = null;
		String ProcessCode = processMapDO.getProcessNumber();
		switch (ProcessCode) {
		case CommonConstants.PROCESS_PICKUP:
			cnStatus = "Picked up";
			break;
		case CommonConstants.PROCESS_BOOKING:
			cnStatus = "Booked";
			break;
		case CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX:
			cnStatus = "Out Packet Prepared";
			break;
		case CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX:
		case CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL:
			cnStatus = "Out Bag Prepared";
			break;
		case CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG:
			cnStatus = "Out Master Bag Prepared";
			break;
		case CommonConstants.PROCESS_HELD_UP:
			cnStatus = "Held up";
			break;
		case CommonConstants.PROCESS_DISPATCH:
			cnStatus = "Dispatched";
			break;
		case CommonConstants.PROCESS_RECEIVE:
			cnStatus = "Received";
			break;
		case CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG:
			cnStatus = "Master Bag in Manifested";
			break;
		case CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL:
		case CommonConstants.PROCESS_IN_MANIFEST_DOX:
			cnStatus = "Bag in Manifested";
			break;
		case CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX:
			cnStatus = "Packet in manifested";
			break;
		case CommonConstants.PROCESS_BRANCH_OUT_MANIFEST:
			cnStatus = "Branch Manifest Prepared";
			break;
		case CommonConstants.PROCESS_BRANCH_IN_MANIFEST:
			cnStatus = "Branch manifest received";
			break;
		case CommonConstants.PROCESS_POD:
			cnStatus = "POD Manifested";
			break;
		case CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX:
		case CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL:
			cnStatus = "Third Party Manifest prepared";
			break;
		case CommonConstants.PROCESS_RTO_RTH:
			// RTOH	"H - Returned to Hub, O - Returned to Origin"
			if (processMapDO.getKey12() != null && StringUtils.equalsIgnoreCase("manifestType", processMapDO.getKey12())) {
				switch (processMapDO.getValue12()) {
				case CommonConstants.MANIFEST_TYPE_RTH:
					cnStatus = "Returned to Hub";
					break;
				case CommonConstants.MANIFEST_TYPE_RTO:
					cnStatus = "Returned to Origin";
					break;
				default:
					break;
				}
			}
			break;
		case CommonConstants.PROCESS_DELIVERY_RUN_SHEET:
			// D - Delivered / P - Pending / O - Out for Delivery
			cnStatus = processMapDO.getValue4();
			break;
		default:
			cnStatus = ProcessCode;
			break;
		}
		return cnStatus;
	}

	private Map<String, ProcessDO> getAllPath() throws CGSystemException {
		LOGGER.trace("ConsignmentTrackingServiceImpl::getAllPath()::START");
		Map<String, ProcessDO> processMapPath = new HashMap<>();
		List<ProcessDO> processDOs = consignmentTrackingDAO.getProcessDetails();
		for (ProcessDO processDO : processDOs) {
			processMapPath.put(processDO.getProcessCode(), processDO);
		}
		LOGGER.trace("ConsignmentTrackingServiceImpl::getAllPath()::END");
		return processMapPath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentTrackingServiceImpl::getTypeName()::START");
		List<StockStandardTypeTO> stockTypeList = null;
		List<StockStandardTypeDO> stockTypeDOList = consignmentTrackingDAO
				.getTypeName();
		if(StringUtil.isEmptyList(stockTypeDOList)){
			throw new CGBusinessException(AdminErrorConstants.NO_STOCK_TYPE);
		}
		else{stockTypeList = (List<StockStandardTypeTO>) CGObjectConverter
				.createTOListFromDomainList(stockTypeDOList,
						StockStandardTypeTO.class);
		}
		LOGGER.trace("ConsignmentTrackingServiceImpl::getTypeName()::END");
		return stockTypeList;
	}

}
