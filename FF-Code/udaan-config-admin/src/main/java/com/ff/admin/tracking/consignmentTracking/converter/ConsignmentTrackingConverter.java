/**
 * 
 */
package com.ff.admin.tracking.consignmentTracking.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingParcelTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.organization.OfficeTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;

/**
 * @author uchauhan
 * 
 */
public class ConsignmentTrackingConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentTrackingConverter.class);

	private static GeographyCommonService geographyCommonService;

	private static TrackingUniversalService trackingUniversalService;
	
	private static BusinessCommonService businessCommonService;

	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public static void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		ConsignmentTrackingConverter.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		ConsignmentTrackingConverter.geographyCommonService = geographyCommonService;
	}

	public static void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		ConsignmentTrackingConverter.businessCommonService = businessCommonService;
	}
	
	public static BookingParcelTO convertBookingDO(BookingDO bookingDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ConsignmentTrackingConverter::convertBookingDO()::START");
		
		BookingParcelTO bookingTO = new BookingParcelTO();
		bookingTO.setConsgNumber(bookingDO.getConsgNumber());
		bookingTO.setBookingDate((DateUtil.getDateInDDMMYYYYHHMMSlashFormat(bookingDO.getBookingDate())).toString());				
		PincodeDO destCityPin = bookingDO.getPincodeId();
		bookingTO.setCityName(setCnDestination(destCityPin));
		bookingTO.setFinalWeight(bookingDO.getFianlWeight());
		bookingTO.setActualWeight(bookingDO.getActualWeight());
		bookingTO.setVolWeight(bookingDO.getVolWeight());
		bookingTO.setHeight(bookingDO.getHeight());
		bookingTO.setLength(bookingDO.getLength());
		bookingTO.setBreath(bookingDO.getBreath());
		bookingTO.setPaperWorkRefNo(bookingDO.getPaperWorkRefNo());
		bookingTO.setNoOfPieces(bookingDO.getNoOfPieces());
		bookingTO.setDeclaredValue(bookingDO.getDeclaredValue());
		bookingTO.setBookingOfficeId(bookingDO.getBookingOfficeId());
		
		Integer officeId = bookingTO.getBookingOfficeId();		
		OfficeTO offcTO = trackingUniversalService.getReportingOffice(officeId);
		if (offcTO != null) {
			bookingTO.setOfficeName(offcTO.getOfficeName());
		}
		if (bookingDO.getConsgTypeId() != null) {
			bookingTO.setConsgTypeName(bookingDO.getConsgTypeId()
					.getConsignmentName());
		}
		if(!StringUtil.isNull(bookingDO.getCustomerId())){
			bookingTO.setCustomerCodeSingle(bookingDO.getCustomerId().getCustomerCode());
		}			
		if (bookingDO.getCnPaperWorkId() != null) {
			bookingTO.setPaperWork(bookingDO.getCnPaperWorkId()
					.getCnPaperWorkName());
		}
		if (bookingDO.getInsuredBy() != null) {
			bookingTO.setInsuredBy(bookingDO.getInsuredBy()
					.getInsuredByDesc());
		}		

		LOGGER.trace("ConsignmentTrackingConverter::convertBookingDO()::END");
		return bookingTO;
	}

	private static String setCnDestination(PincodeDO destCityPin) throws CGSystemException,
			CGBusinessException {
		String destOffice = null;
		if (!StringUtil.isNull(destCityPin)) {
			List<OfficeTO> destOffices = geographyCommonService
					.getBranchDtlsForPincodeServiceByPincode(destCityPin.getPincodeId());
			if (!StringUtil.isEmptyList(destOffices)) {
				StringBuilder destOfficeNames = new StringBuilder();
				int i = 0;
				for (OfficeTO officeTO : destOffices) {						
					if (i != 0) {
						destOfficeNames.append(CommonConstants.COMMA);
						destOfficeNames.append(CommonConstants.SPACE);
					}
					destOfficeNames.append(officeTO.getOfficeName());
					i++;
				}
				destOffice = destOfficeNames.toString();
			}				
		}
		return destOffice;
	}

	public static BookingParcelTO convertConsignmentDO(
			ConsignmentDO consignmentDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ConsignmentTrackingConverter::convertConsignmentDO()::START");
		BookingParcelTO bookingTO = new BookingParcelTO();
		
		bookingTO.setConsgNumber(consignmentDO.getConsgNo());		
		bookingTO.setBookingOfficeId(consignmentDO.getOrgOffId());
		PincodeDO destCityPin = consignmentDO.getDestPincodeId();
		bookingTO.setCityName(setCnDestination(destCityPin));
		bookingTO.setBookingDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(consignmentDO.getCreatedDate()));
		bookingTO.setFinalWeight(consignmentDO.getFinalWeight());
		bookingTO.setActualWeight(consignmentDO.getActualWeight());
		bookingTO.setVolWeight(consignmentDO.getVolWeight());
		bookingTO.setHeight(consignmentDO.getHeight());
		bookingTO.setLength(consignmentDO.getLength());
		bookingTO.setBreath(consignmentDO.getBreath());
		bookingTO.setPaperWorkRefNo(consignmentDO.getPaperWorkRefNo());
		bookingTO.setDeclaredValue(consignmentDO.getDeclaredValue());
		bookingTO.setNoOfPieces(consignmentDO.getNoOfPcs());
		
		Integer officeId = consignmentDO.getOrgOffId();
		OfficeTO offcTO = trackingUniversalService.getReportingOffice(officeId);
		if (offcTO != null) {
			bookingTO.setOfficeName(offcTO.getOfficeName());
		}
		if (consignmentDO.getConsgType() != null) {
			bookingTO.setConsgTypeName(consignmentDO.getConsgType()
					.getConsignmentName());
		}
		//Set Customer code
		if(!StringUtil.isEmptyInteger(consignmentDO.getCustomer())){
			CustomerTO customerTO = businessCommonService.getCustomer(consignmentDO.getCustomer());
			if(!StringUtil.isNull(customerTO)){
				bookingTO.setCustomerCodeSingle(customerTO.getCustomerCode());
			}
		}
		if (consignmentDO.getCnPaperWorkId() != null) {
			bookingTO.setPaperWork(consignmentDO.getCnPaperWorkId()
					.getCnPaperWorkName());
		}
		if (consignmentDO.getInsuredBy() != null) {
			bookingTO.setInsuredBy(consignmentDO.getInsuredBy().getInsuredByDesc());
		}

		LOGGER.trace("ConsignmentTrackingConverter::convertConsignmentDO()::END");
		return bookingTO;
	}
	
	public static List<ChildConsignmentTO> convertChildDetailsDO(
			Set<ChildConsignmentDO> childDetails) {
		LOGGER.trace("ConsignmentTrackingConverter::convertChildDetailsDO()::START");
		ChildConsignmentTO childDetailTO = null;
		List<ChildConsignmentTO> detailsTO = new ArrayList<>(childDetails.size());

		for (ChildConsignmentDO childDO : childDetails) {
			childDetailTO = new ChildConsignmentTO();
			childDetailTO.setChildConsgNumber(childDO.getChildConsgNumber());
			detailsTO.add(childDetailTO);
		}
		LOGGER.trace("ConsignmentTrackingConverter::convertChildDetailsDO()::END");
		return detailsTO;

	}

}
