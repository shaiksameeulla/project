package com.ff.web.manifest.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.OutManifestDestinationDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.manifest.Utils.OutManifestTOFactory;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

public abstract class OutManifestBaseConverter {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestBaseConverter.class);

	/** The geographyCommonService. */
	public static GeographyCommonService geographyCommonService;

	/**
	 * @return the geographyCommonService
	 */
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	@SuppressWarnings("static-access")
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public static OutManifestDetailBaseTO setUpManifestDtlsTOs(
			OutManifestBaseTO manifestBaseTO, int rowCount)
			throws CGBusinessException, CGSystemException {
		ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
		if (manifestBaseTO.getConsignmentTypeTO() != null)
			if (manifestBaseTO.getConsignmentTypeTO().getConsignmentCode() != null)
				manifestFactoryTO.setConsgType(manifestBaseTO
						.getConsignmentTypeTO().getConsignmentCode());
		if (manifestBaseTO.getManifestType() != null)
			manifestFactoryTO.setManifestType(manifestBaseTO.getManifestType());

		OutManifestDetailBaseTO manifestTO = OutManifestTOFactory
				.getOutManifestDetailBaseTO(manifestFactoryTO);

		if (!StringUtil.isEmpty(manifestBaseTO.getConsgNos())
				&& StringUtils
						.isNotEmpty(manifestBaseTO.getConsgNos()[rowCount])) {
			manifestTO.setConsgNo(manifestBaseTO.getConsgNos()[rowCount]);
		}

		if (!StringUtil.isEmpty(manifestBaseTO.getConsgIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getConsgIds()[rowCount])) {
			manifestTO.setConsgId(manifestBaseTO.getConsgIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getManifestNos())
				&& StringUtils
						.isNotEmpty(manifestBaseTO.getManifestNos()[rowCount])) {
			manifestTO.setManifestNo(manifestBaseTO.getManifestNos()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getManifestIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getManifestIds()[rowCount])) {
			manifestTO.setManifestId(manifestBaseTO.getManifestIds()[rowCount]);
			setUpdateFlag4DBSync(manifestTO);
		} else {
			setSaveFlag4DBSync(manifestTO);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getDestCityIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getDestCityIds()[rowCount]))
			manifestTO.setDestCityId(manifestBaseTO.getDestCityIds()[rowCount]);

		if (!StringUtil.isEmpty(manifestBaseTO.getDestCitys())
				&& StringUtils
						.isNotEmpty(manifestBaseTO.getDestCitys()[rowCount]))
			manifestTO.setDestCity(manifestBaseTO.getDestCitys()[rowCount]);

		if (!StringUtil.isEmpty(manifestBaseTO.getOldWeights())
				&& !StringUtil
						.isEmptyDouble(manifestBaseTO.getOldWeights()[rowCount])) {
			manifestTO.setOldWeight(manifestBaseTO.getOldWeights()[rowCount]);
		}

		if (!StringUtil.isEmpty(manifestBaseTO.getWeights())
				&& !StringUtil
						.isEmptyDouble(manifestBaseTO.getWeights()[rowCount])) {
			manifestTO.setWeight(manifestBaseTO.getWeights()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getPincodes())
				&& StringUtils
						.isNotEmpty(manifestBaseTO.getPincodes()[rowCount]))
			manifestTO.setPincode(manifestBaseTO.getPincodes()[rowCount]);

		if (!StringUtil.isEmpty(manifestBaseTO.getPincodeIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getPincodeIds()[rowCount])) {
			manifestTO.setPincodeId(manifestBaseTO.getPincodeIds()[rowCount]);
			manifestTO
					.setBkgPincodeId(manifestBaseTO.getPincodeIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getIsCNProcessedFromPickup())
				&& StringUtils.isNotEmpty(manifestBaseTO
						.getIsCNProcessedFromPickup()[rowCount])) {
			manifestTO.setIsPickupCN(manifestBaseTO
					.getIsCNProcessedFromPickup()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getBookingIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getBookingIds()[rowCount])) {
			manifestTO.setBookingId(manifestBaseTO.getBookingIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getBookingTypeIds())
				&& !StringUtil.isEmptyInteger(manifestBaseTO
						.getBookingTypeIds()[rowCount])) {
			manifestTO
					.setBookingTypeId(manifestBaseTO.getBookingTypeIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getCustomerIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getCustomerIds()[rowCount])) {
			manifestTO.setCustomerId(manifestBaseTO.getCustomerIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getRunsheetNos())
				&& StringUtils
						.isNotEmpty(manifestBaseTO.getRunsheetNos()[rowCount])) {
			manifestTO.setRunsheetNo(manifestBaseTO.getRunsheetNos()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getConsignorIds())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getConsignorIds()[rowCount])) {
			manifestTO
					.setConsignorId(manifestBaseTO.getConsignorIds()[rowCount]);
		}
		if (!StringUtil.isEmpty(manifestBaseTO.getPosition())
				&& !StringUtil
						.isEmptyInteger(manifestBaseTO.getPosition()[rowCount])) {
			manifestTO.setPosition(manifestBaseTO.getPosition()[rowCount]);
		}

		return manifestTO;
	}

	public static OutManifestBaseTO cnDtlsToOutMnfstBaseConverter(
			ConsignmentModificationTO consignmentModificationTO,
			ManifestFactoryTO manifestFactoryTO) {
		OutManifestBaseTO outManifestBaseTO = null;
		if (consignmentModificationTO.getConsignee() != null) {
			outManifestBaseTO = OutManifestTOFactory
					.getOutManifestBaseTO(manifestFactoryTO);
		}

		return outManifestBaseTO;
	}

	public static OutManifestDetailBaseTO cnDtlsToOutMnfstDtlBaseConverter(
			ConsignmentModificationTO consignmentModificationTO,
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		ConsignmentTO consignmentTO = null;
		OutManifestDetailBaseTO outManifestDetailBaseTO = null;
		if (!StringUtil.isNull(consignmentModificationTO)) {
			outManifestDetailBaseTO = OutManifestTOFactory
					.getOutManifestDetailBaseTO(manifestFactoryTO);
			consignmentTO = consignmentModificationTO.getConsigmentTO();
			if (!StringUtil.isNull(consignmentTO)) {
				outManifestDetailBaseTO.setConsgNo(consignmentTO.getConsgNo());
				outManifestDetailBaseTO.setConsgId(consignmentTO.getConsgId());
				PincodeTO pincodeTO = consignmentTO.getDestPincode();
				if (!StringUtil.isNull(pincodeTO)) {
					outManifestDetailBaseTO.setPincodeId(pincodeTO
							.getPincodeId());
					outManifestDetailBaseTO.setBkgPincodeId(pincodeTO
							.getPincodeId());
					outManifestDetailBaseTO.setPincode(pincodeTO.getPincode());
				}

				outManifestDetailBaseTO.setWeight(consignmentTO
						.getFinalWeight());
				outManifestDetailBaseTO.setBkgWeight(consignmentTO
						.getFinalWeight());
				outManifestDetailBaseTO.setGridItemType("C");
			}
			// Pickup consignments
			else if (!StringUtil.isNull(consignmentModificationTO)) {
				outManifestDetailBaseTO.setBookingId(consignmentModificationTO
						.getBookingId());
				outManifestDetailBaseTO
						.setBookingTypeId(consignmentModificationTO
								.getBookingTypeId());
				if (!StringUtil.isNull(consignmentModificationTO.getCustomer()))
					outManifestDetailBaseTO
							.setCustomerId(consignmentModificationTO
									.getCustomer().getCustomerId());
				if (!StringUtil
						.isNull(consignmentModificationTO.getConsignor()))
					outManifestDetailBaseTO
							.setConsignorId(consignmentModificationTO
									.getConsignor().getPartyId());
				outManifestDetailBaseTO.setRunsheetNo(consignmentModificationTO
						.getRunsheetNo());
				outManifestDetailBaseTO.setConsgNo(consignmentModificationTO
						.getConsgNumber());
				outManifestDetailBaseTO.setPincode(consignmentModificationTO
						.getPincode());
				outManifestDetailBaseTO.setPincodeId(consignmentModificationTO
						.getPincodeId());
				outManifestDetailBaseTO.setDestCityId(consignmentModificationTO
						.getCityId());
				outManifestDetailBaseTO.setDestCity(consignmentModificationTO
						.getCityName());
				outManifestDetailBaseTO.setWeight(consignmentModificationTO
						.getFinalWeight());
				outManifestDetailBaseTO.setBkgWeight(consignmentModificationTO
						.getFinalWeight());
			}
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST);
		}
		return outManifestDetailBaseTO;
	}

	public static OutManifestBaseTO outManifestDomainConverter(
			ManifestDO manifestDO, ManifestFactoryTO manifestFactoryTO)
			throws CGBusinessException {
		OutManifestBaseTO manifestBaseTO = OutManifestTOFactory
				.getOutManifestBaseTO(manifestFactoryTO);
		manifestBaseTO.setManifestId(manifestDO.getManifestId());
		manifestBaseTO
				.setManifestDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(manifestDO
								.getManifestDate()));
		manifestBaseTO.setManifestNo(manifestDO.getManifestNo().toUpperCase());
		manifestBaseTO.setFinalWeight(manifestDO.getManifestWeight());
		manifestBaseTO.setManifestType(manifestDO.getManifestType());
		manifestBaseTO.setManifestStatus(manifestDO.getManifestStatus());
		manifestBaseTO.setBagRFID(manifestDO.getBagRFID());
		manifestBaseTO.setBagLockNo(manifestDO.getBagLockNo());
		manifestBaseTO.setProcessCode(manifestDO.getManifestProcessCode());
		manifestBaseTO.setIsMulDestination(manifestDO.getMultipleDestination());
		Set<OutManifestDestinationDO> manifestDestinationDOs = manifestDO
				.getMultipleDestinations();
		if (!StringUtil.isEmptyColletion(manifestDestinationDOs)) {
			StringBuilder outMnfstDestIds = new StringBuilder();
			for (OutManifestDestinationDO outManifestDestinationDO : manifestDestinationDOs) {
				outMnfstDestIds.append(outManifestDestinationDO
						.getOutManifestDestinationId());
				outMnfstDestIds.append(CommonConstants.HASH);
				if (!StringUtil.isNull(outManifestDestinationDO.getOffice())) {
					outMnfstDestIds.append(outManifestDestinationDO.getOffice()
							.getOfficeId());
				}
				outMnfstDestIds.append(CommonConstants.COMMA);
			}
			manifestBaseTO.setOutMnfstDestIds(outMnfstDestIds.toString());
		}
		OfficeDO officeDO = null;
		officeDO = manifestDO.getOriginOffice();
		manifestBaseTO.setLoginOfficeId(officeDO.getOfficeId());
		manifestBaseTO.setLoginOfficeName(officeDO.getOfficeName());
		manifestBaseTO.setLoginOfficeAddress1(officeDO.getAddress1());
		manifestBaseTO.setLoginOfficeAddress2(officeDO.getAddress2());
		manifestBaseTO.setLoginOfficeAddress3(officeDO.getAddress3());
		manifestBaseTO.setLoginOfficePincode(officeDO.getPincode());
		manifestBaseTO.setRegionId(officeDO.getReportingRHO());
		if (!StringUtil.isNull(manifestDO.getOriginOffice().getOfficeTypeDO())) {
			manifestBaseTO.setLoginOfficeType(manifestDO.getOriginOffice()
					.getOfficeTypeDO().getOffcTypeCode());
		}

		officeDO = manifestDO.getDestOffice();
		if (!StringUtil.isNull(officeDO)) {
			manifestBaseTO.setDestinationOfficeId(officeDO.getOfficeId());
			manifestBaseTO.setDestinationOfficeName(officeDO.getOfficeName());
			manifestBaseTO.setBplDestOfficeCityId(officeDO.getCityId());
		}
		if (!StringUtil.isNull(officeDO)) {
			if (officeDO.getOfficeTypeDO() != null) {
				manifestBaseTO.setOfficeTypeId(officeDO.getOfficeTypeDO()
						.getOffcTypeId());
				manifestBaseTO.setOfficeCode(officeDO.getOfficeTypeDO()
						.getOffcTypeCode());
			}
		}
		if (!StringUtil.isNull(officeDO)
				&& !StringUtil.isNull(officeDO.getMappedRegionDO())) {
			RegionTO regionTO = new RegionTO();
			regionTO.setRegionId(officeDO.getMappedRegionDO().getRegionId());
			regionTO.setRegionName(officeDO.getMappedRegionDO().getRegionName());
			manifestBaseTO.setDestRegionTO(regionTO);
		}
		if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
			ConsignmentTypeTO to = new ConsignmentTypeTO();
			to.setConsignmentId(manifestDO.getManifestLoadContent()
					.getConsignmentId());
			to.setConsignmentCode(manifestDO.getManifestLoadContent()
					.getConsignmentCode());
			to.setConsignmentName(manifestDO.getManifestLoadContent()
					.getConsignmentName());
			manifestBaseTO.setConsignmentTypeTO(to);
		}
		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			CityDO cityDO = manifestDO.getDestinationCity();
			CityTO cityTO = new CityTO();
			try {
				PropertyUtils.copyProperties(cityTO, cityDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				throw new CGBusinessException(e);
			}
			/*
			 * cityTO.setCityId(cityDO.getCityId());
			 * cityTO.setCityCode(cityDO.getCityCode());
			 * cityTO.setCityName(cityDO.getCityName());
			 * cityTO.setRegion(cityDO.getRegion());
			 */
			manifestBaseTO.setDestinationCityTO(cityTO);
		}

		if (!StringUtil.isNull(manifestDO.getUpdatingProcess())) {
			ProcessDO processDO = manifestDO.getUpdatingProcess();
			manifestBaseTO.setProcessId(processDO.getProcessId());
		}

		manifestBaseTO.setFinalWeight(manifestDO.getManifestWeight());
		// Newly added fields for BCUN purpose
		manifestBaseTO.setManifestDirection(manifestDO.getManifestDirection());

		// Newly added fields for BCUN - Third Party DOX/BPL
		// Load Lot Id
		if (!StringUtil.isEmptyInteger(manifestDO.getLoadLotId())) {
			manifestBaseTO.setLoadNoId(manifestDO.getLoadLotId());
		}
		// Third Party Type
		if (!StringUtil.isStringEmpty(manifestDO.getThirdPartyType())) {
			manifestBaseTO.setThirdPartyType(manifestDO.getThirdPartyType());
		}
		// Third Party Name - Vendor Id
		if (!StringUtil.isEmptyInteger(manifestDO.getVendorId())) {
			/*
			 * manifestBaseTO.setThirdPartyName(manifestDO
			 * .getVendorId()+CommonConstants.EMPTY_STRING);
			 */
			manifestBaseTO.setVendorId(manifestDO.getVendorId());
		}
		// Added by shahnaz
		// manifestBaseTO.setOperatingLevel(manifestDO.getOperatingLevel());

		return manifestBaseTO;
	}

	public static ManifestDO outManifestTransferObjConverter(
			OutManifestBaseTO outManifestBaseTO) {
		ManifestDO outManifestDO = new ManifestDO();
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getManifestId())) {
			outManifestDO.setManifestId(outManifestBaseTO.getManifestId());
			outManifestDO.setDtUpdateToCentral(CommonConstants.YES);
			outManifestDO.setDtToCentral(CommonConstants.NO);
		} else {
			outManifestDO.setDtToCentral(CommonConstants.NO);
		}

		outManifestDO.setManifestNo(outManifestBaseTO.getManifestNo()
				.toUpperCase());
		outManifestDO.setManifestWeight(outManifestBaseTO.getFinalWeight());
//		outManifestDO.setManifestDate(DateUtil
//				.parseStringDateToDDMMYYYYHHMMFormat(outManifestBaseTO
//						.getManifestDate()));
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		outManifestDO.setManifestDate(Calendar.getInstance().getTime());
		// Ami added to save user details in manifest table
		outManifestDO.setCreatedBy(outManifestBaseTO.getCreatedBy());
		outManifestDO.setUpdatedBy(outManifestBaseTO.getUpdatedBy());

		OfficeDO officeDO = null;
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getLoginOfficeId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(outManifestBaseTO.getLoginOfficeId());
			outManifestDO.setOriginOffice(officeDO);
		}
		if (!StringUtils.equalsIgnoreCase(
				outManifestBaseTO.getIsMulDestination(), CommonConstants.YES)
				&& !StringUtil.isEmptyInteger(outManifestBaseTO
						.getDestinationOfficeId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(outManifestBaseTO.getDestinationOfficeId());
			outManifestDO.setDestOffice(officeDO);
		}
		// Setting all the Hub office ids of the selected city in case of Office
		// as "ALL"
		try {
			Set<OutManifestDestinationDO> multipleDests = prepareOutManifestDestinations(outManifestBaseTO);
			if (!StringUtil.isEmptyColletion(multipleDests)) {
				outManifestDO.setMultipleDestinations(multipleDests);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::OutManifestBaseConverter::outManifestTransferObjConverter() :: ",
					e);
		}

		outManifestDO.setMultipleDestination(outManifestBaseTO
				.getIsMulDestination());
		outManifestDO.setManifestStatus(outManifestBaseTO.getManifestStatus());
		outManifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
		if (!StringUtil.isNull(outManifestBaseTO.getConsignmentTypeTO())
				&& !StringUtil.isEmptyInteger(outManifestBaseTO
						.getConsignmentTypeTO().getConsignmentId())) {
			ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
			consgTypeDO.setConsignmentId(outManifestBaseTO
					.getConsignmentTypeTO().getConsignmentId());
			outManifestDO.setManifestLoadContent(consgTypeDO);
		}
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getBagRFID()))
			outManifestDO.setBagRFID(outManifestBaseTO.getBagRFID());
		outManifestDO.setBagLockNo(outManifestBaseTO.getBagLockNo());
		if (!StringUtil
				.isEmptyInteger(outManifestBaseTO.getDestinationCityId())) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(outManifestBaseTO.getDestinationCityId());
			outManifestDO.setDestinationCity(cityDO);
		}
		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(outManifestBaseTO.getProcessId());
		outManifestDO.setUpdatingProcess(processDO);
		outManifestDO
				.setManifestProcessCode(outManifestBaseTO.getProcessCode());

		// Newly added fields for BCUN
		outManifestDO.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
		// outManifestDO.setOperatingLevel(outManifestBaseTO.getOperatingLevel());
		outManifestDO.setOperatingOffice(outManifestBaseTO.getLoginOfficeId());

		// Newly added fields for BCUN - Third Party DOX/BPL
		// Load Lot Id
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getLoadNoId())) {
			outManifestDO.setLoadLotId(outManifestBaseTO.getLoadNoId());
		}
		// Third Party Type
		if (!StringUtil.isStringEmpty(outManifestBaseTO.getThirdPartyType())) {
			outManifestDO.setThirdPartyType(outManifestBaseTO
					.getThirdPartyType());
		}
		// Third Party Name - Vendor Id
		if (!StringUtil.isStringEmpty(outManifestBaseTO.getThirdPartyName())) {
			outManifestDO.setVendorId(Integer.parseInt(outManifestBaseTO
					.getThirdPartyName()));
		}
		return outManifestDO;
	}

	public static ManifestDO outManifestTransferObjConverter(
			OutManifestBaseTO outManifestBaseTO, ManifestDO outManifestDO) {
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getManifestId())) {
			outManifestDO.setManifestId(outManifestBaseTO.getManifestId());
			outManifestDO.setDtUpdateToCentral(CommonConstants.YES);
			outManifestDO.setDtToCentral(CommonConstants.NO);
		} else {
			outManifestDO.setDtToCentral(CommonConstants.NO);
		}

		outManifestDO.setManifestNo(outManifestBaseTO.getManifestNo()
				.toUpperCase());
		outManifestDO.setManifestWeight(outManifestBaseTO.getFinalWeight());
//		outManifestDO.setManifestDate(DateUtil
//				.parseStringDateToDDMMYYYYHHMMFormat(outManifestBaseTO
//						.getManifestDate()));
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		outManifestDO.setManifestDate(Calendar.getInstance().getTime());
		// Ami added to save user details in manifest table
		outManifestDO.setCreatedBy(outManifestBaseTO.getCreatedBy());
		outManifestDO.setUpdatedBy(outManifestBaseTO.getUpdatedBy());

		OfficeDO officeDO = null;
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getLoginOfficeId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(outManifestBaseTO.getLoginOfficeId());
			outManifestDO.setOriginOffice(officeDO);
		}
		if (!StringUtils.equalsIgnoreCase(
				outManifestBaseTO.getIsMulDestination(), CommonConstants.YES)
				&& !StringUtil.isEmptyInteger(outManifestBaseTO
						.getDestinationOfficeId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(outManifestBaseTO.getDestinationOfficeId());
			outManifestDO.setDestOffice(officeDO);
		}
		// Setting all the Hub office ids of the selected city in case of Office
		// as "ALL"
		try {
			Set<OutManifestDestinationDO> multipleDests = prepareOutManifestDestinations(outManifestBaseTO);
			if (!StringUtil.isEmptyColletion(multipleDests)) {
				outManifestDO.setMultipleDestinations(multipleDests);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::OutManifestBaseConverter::outManifestTransferObjConverter() :: ",
					e);
		}

		outManifestDO.setMultipleDestination(outManifestBaseTO
				.getIsMulDestination());
		outManifestDO.setManifestStatus(outManifestBaseTO.getManifestStatus());
		outManifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
		if (!StringUtil.isNull(outManifestBaseTO.getConsignmentTypeTO())
				&& !StringUtil.isEmptyInteger(outManifestBaseTO
						.getConsignmentTypeTO().getConsignmentId())) {
			ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
			consgTypeDO.setConsignmentId(outManifestBaseTO
					.getConsignmentTypeTO().getConsignmentId());
			outManifestDO.setManifestLoadContent(consgTypeDO);
		}
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getBagRFID()))
			outManifestDO.setBagRFID(outManifestBaseTO.getBagRFID());
		outManifestDO.setBagLockNo(outManifestBaseTO.getBagLockNo());
		if (!StringUtil
				.isEmptyInteger(outManifestBaseTO.getDestinationCityId())) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(outManifestBaseTO.getDestinationCityId());
			outManifestDO.setDestinationCity(cityDO);
		}
		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(outManifestBaseTO.getProcessId());
		outManifestDO.setUpdatingProcess(processDO);
		outManifestDO
				.setManifestProcessCode(outManifestBaseTO.getProcessCode());

		// Newly added fields for BCUN
		outManifestDO.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
		// outManifestDO.setOperatingLevel(outManifestBaseTO.getOperatingLevel());
		outManifestDO.setOperatingOffice(outManifestBaseTO.getLoginOfficeId());

		// Newly added fields for BCUN - Third Party DOX/BPL
		// Load Lot Id
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getLoadNoId())) {
			outManifestDO.setLoadLotId(outManifestBaseTO.getLoadNoId());
		}
		// Third Party Type
		if (!StringUtil.isStringEmpty(outManifestBaseTO.getThirdPartyType())) {
			outManifestDO.setThirdPartyType(outManifestBaseTO
					.getThirdPartyType());
		}
		// Third Party Name - Vendor Id
		if (!StringUtil.isStringEmpty(outManifestBaseTO.getThirdPartyName())) {
			outManifestDO.setVendorId(Integer.parseInt(outManifestBaseTO
					.getThirdPartyName()));
		}
		return outManifestDO;
	}

	protected static Set<OutManifestDestinationDO> prepareOutManifestDestinations(
			OutManifestBaseTO outManifestBaseTO) {
		Set<OutManifestDestinationDO> multipleDests = null;
		if (StringUtils.isNotEmpty(outManifestBaseTO.getOutMnfstDestIds())) {
			// Already existing out manifest destination records
			List<String> outMnfstDestIds = StringUtil.parseStringList(
					outManifestBaseTO.getOutMnfstDestIds(),
					CommonConstants.COMMA);
			multipleDests = new HashSet<>(outMnfstDestIds.size());
			for (String destnOffId : outMnfstDestIds) {
				String[] destIdnOffId = StringUtil.split(destnOffId,
						CommonConstants.HASH);
				Integer destId = 0;
				Integer OfficeId = 0;
				if (StringUtils.isNotEmpty(destIdnOffId[0])) {
					destId = Integer.parseInt(destIdnOffId[0]);
				}
				if (StringUtils.isNotEmpty(destIdnOffId[1])) {
					OfficeId = Integer.parseInt(destIdnOffId[1]);
				}
				OutManifestDestinationDO mnfstDest = setOutManifestDestDO(OfficeId);
				mnfstDest.setOutManifestDestinationId(destId);
				multipleDests.add(mnfstDest);
			}
		} else {
			/**
			 * Origin Branch to All Origin Hubs (for all types of Manifest for
			 * Open/Direct) Origin Hub to Destination Hubs and Destination
			 * Branch
			 **/
			List<Integer> mnfstDestOffices = new ArrayList<>();
			if (StringUtils.equalsIgnoreCase(
					outManifestBaseTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				mnfstDestOffices
						.addAll(outManifestBaseTO.getOriginHubOffList());
			} else if (StringUtils.equalsIgnoreCase(
					outManifestBaseTO.getLoginOfficeType(),
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				mnfstDestOffices.addAll(outManifestBaseTO.getDestHubOffList());
				if (!StringUtil.isEmptyInteger(outManifestBaseTO
						.getDestinationOfficeId()))
					mnfstDestOffices.add(outManifestBaseTO
							.getDestinationOfficeId());
			}
			if (!StringUtil.isEmptyList(mnfstDestOffices)) {
				Set<Integer> destOfficeIdSet = new HashSet<Integer>(
						mnfstDestOffices);
				multipleDests = new HashSet<>(destOfficeIdSet.size());
				for (Integer officeId : destOfficeIdSet) {
					OutManifestDestinationDO mnfstDest = null;
					mnfstDest = setOutManifestDestDO(officeId);
					multipleDests.add(mnfstDest);
				}
			}
		}
		return multipleDests;
	}

	private static OutManifestDestinationDO setOutManifestDestDO(
			Integer officeId) {
		OutManifestDestinationDO mnfstDest = new OutManifestDestinationDO();
		OfficeDO officeDO = new OfficeDO();
		officeDO.setOfficeId(officeId);
		mnfstDest.setOffice(officeDO);
		return mnfstDest;
	}

	/*
	 * public static ManifestProcessDO outManifestBaseTransferObjConverter(
	 * OutManifestBaseTO outMnfstBaseTO) { ManifestProcessDO mnfstProcessDO =
	 * null; if (!StringUtil.isNull(outMnfstBaseTO)) { mnfstProcessDO = new
	 * ManifestProcessDO(); if
	 * (!StringUtil.isNull(outMnfstBaseTO.getManifestProcessTo()) &&
	 * !StringUtil.isEmptyInteger(outMnfstBaseTO
	 * .getManifestProcessTo().getManifestProcessId())) {
	 * mnfstProcessDO.setManifestProcessId(outMnfstBaseTO
	 * .getManifestProcessTo().getManifestProcessId());
	 * mnfstProcessDO.setDtUpdateToCentral(CommonConstants.YES);
	 * mnfstProcessDO.setDtToCentral(CommonConstants.NO); } else {
	 * mnfstProcessDO.setDtToCentral(CommonConstants.NO); } if
	 * (StringUtils.isNotEmpty(outMnfstBaseTO.getBagLockNo())) {
	 * mnfstProcessDO.setBagLockNo(outMnfstBaseTO.getBagLockNo()); } if
	 * (!StringUtil.isEmptyInteger(outMnfstBaseTO.getBagRFID())) {
	 * mnfstProcessDO.setBagRFID(outMnfstBaseTO.getBagRFID()); } if
	 * (!StringUtil.isEmptyInteger(outMnfstBaseTO .getDestinationOfficeId())) {
	 * mnfstProcessDO.setDestOfficeId(outMnfstBaseTO .getDestinationOfficeId());
	 * } mnfstProcessDO.setManifestDate(DateUtil
	 * .parseStringDateToDDMMYYYYHHMMFormat(outMnfstBaseTO .getManifestDate()));
	 * mnfstProcessDO
	 * .setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
	 * mnfstProcessDO.setManifestNo(outMnfstBaseTO.getManifestNo());
	 * mnfstProcessDO.setManifestProcessCode(outMnfstBaseTO .getProcessCode());
	 * mnfstProcessDO.setManifestProcessNo(outMnfstBaseTO.getProcessNo());
	 * mnfstProcessDO.setManifestWeight(outMnfstBaseTO.getFinalWeight()); if
	 * (StringUtils.isNotEmpty(outMnfstBaseTO.getIsMulDestination())) {
	 * mnfstProcessDO.setMultipleDestination(outMnfstBaseTO
	 * .getIsMulDestination()); } mnfstProcessDO
	 * .setManifestStatus(outMnfstBaseTO.getManifestStatus());
	 * mnfstProcessDO.setNoOfElements(outMnfstBaseTO.getRowCount());
	 * mnfstProcessDO.setOriginOfficeId(outMnfstBaseTO.getLoginOfficeId()); if
	 * (!StringUtil.isEmptyInteger(outMnfstBaseTO.getLoadNoId())) {
	 * mnfstProcessDO.setLoadLotId(outMnfstBaseTO.getLoadNoId()); } if
	 * (!StringUtil.isEmptyInteger(outMnfstBaseTO .getDestinationCityId())) {
	 * mnfstProcessDO.setDestCityId(outMnfstBaseTO .getDestinationCityId()); } }
	 * return mnfstProcessDO; }
	 */
	/*
	 * public static List<ManifestProcessTO>
	 * manifestProcessTransferObjConverter( ManifestProcessDO manifestProcess) {
	 * List<ManifestProcessTO> manifestProcessTOs = new ArrayList<>(); // for
	 * (ManifestProcessDO manifestProcess : manifestProcessDO) {
	 * ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
	 * manifestProcessTO.setManifestProcessId(manifestProcess
	 * .getManifestProcessId());
	 */
	/*
	 * if (StringUtils.isNotEmpty(manifestProcess.getManifestDirection()))
	 * manifestProcessTO.setManifestDirection(manifestProcess
	 * .getManifestDirection()); if
	 * (StringUtils.isNotEmpty(manifestProcess.getManifestOpenType()))
	 * manifestProcessTO.setManifestOpenType(manifestProcess
	 * .getManifestOpenType());
	 */
	/*
	 * if (StringUtils.isNotEmpty(manifestProcess.getBplManifestType())) { if
	 * (StringUtils.equalsIgnoreCase( manifestProcess.getBplManifestType(),
	 * ManifestConstants.PURE)) { manifestProcessTO
	 * .setBplManifestType(OutManifestConstants.MANIFEST_TYPE_PURE); } else {
	 * manifestProcessTO
	 * .setBplManifestType(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE
	 * ); } }
	 */
	/*
	 * if (StringUtils.isNotEmpty(manifestProcess.getThirdPartyType()))
	 * manifestProcessTO.setThirdPartyType(manifestProcess
	 * .getThirdPartyType()); if
	 * (!StringUtil.isEmptyInteger(manifestProcess.getVendorId()))
	 * manifestProcessTO.setVendorId(manifestProcess.getVendorId()); if
	 * (!StringUtil.isEmptyInteger(manifestProcess.getLoadLotId()))
	 * manifestProcessTO.setLoadNo(manifestProcess.getLoadLotId()); if
	 * (StringUtils.isNotEmpty(manifestProcess.getBagLockNo()))
	 * manifestProcessTO.setBagLockNo(manifestProcess.getBagLockNo());
	 * if(StringUtils.isNotEmpty(manifestProcess.getManifestProcessCode()))
	 * manifestProcessTO
	 * .setManifestProcessCode(manifestProcess.getManifestProcessCode());
	 */
	/*
	 * manifestProcessTOs.add(manifestProcessTO); // } return
	 * manifestProcessTOs; }
	 */

	public static void setUpdateFlag4DBSync(CGBaseTO cgBaseTO) {
		cgBaseTO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseTO.setDtToCentral(CommonConstants.NO);
	}

	/**
	 * Sets the save flag4 db sync. * @param cgBaseDO the new save flag4 db sync
	 */

	public static void setSaveFlag4DBSync(CGBaseTO cgBaseTO) {
		cgBaseTO.setDtToCentral(CommonConstants.NO);
	}

	/**
	 * To prepare ManifestDO from ManifestInputs
	 * 
	 * @param manifestTO
	 * @return manifestDO
	 */
	public static ManifestDO prepateManifestDO(ManifestInputs manifestTO) {
		ManifestDO manifestDO = new ManifestDO();
		manifestDO.setManifestNo(manifestTO.getManifestNumber());
		manifestDO.setManifestProcessCode(manifestTO.getManifestProcessCode());
		manifestDO.setManifestType(manifestTO.getManifestType());
		manifestDO.setOperatingOffice(manifestTO.getLoginOfficeId());
		manifestDO.setManifestDirection(manifestTO.getManifestDirection());
		return manifestDO;
	}

	/**
	 * To prepare ManifestDO from OutManifestDoxTO
	 * 
	 * @param outManifestDoxTO
	 * @return manifestDO
	 */

	public static ManifestDO prepateManifestDO(OutManifestBaseTO outManifestTO) {
		ManifestDO manifestDO = new ManifestDO();
		manifestDO.setManifestNo(outManifestTO.getManifestNo());
		manifestDO.setManifestProcessCode(outManifestTO.getProcessCode());
		manifestDO.setManifestType(outManifestTO.getManifestType());
		manifestDO.setOperatingOffice(outManifestTO.getLoginOfficeId());
		return manifestDO;
	}

	public static ManifestDO prepateManifestDO(
			BranchOutManifestDoxTO branchOutManifestDoxTO) {
		ManifestDO manifestDO = new ManifestDO();
		manifestDO.setManifestNo(branchOutManifestDoxTO.getManifestNo()
				.toUpperCase());
		manifestDO.setManifestProcessCode(branchOutManifestDoxTO
				.getProcessCode());
		manifestDO.setManifestType(branchOutManifestDoxTO.getManifestType());
		manifestDO
				.setOperatingOffice(branchOutManifestDoxTO.getLoginOfficeId());
		return manifestDO;
	}

	/**
	 * To get rate type
	 * 
	 * @param customerType
	 * @return rateType
	 */
	public static String getRateType(String customerType) {
		LOGGER.trace("OutManifestBaseConverter :: getRateType() :: START");
		String rateType = CommonConstants.EMPTY_STRING;
		switch (customerType) {
		case RateUniversalConstants.RATE_CUST_CAT_CRDT:
		case RateUniversalConstants.RATE_CUST_CAT_FR:
			rateType = RateUniversalConstants.RATE_TYPE_CC;
			break;
		case RateUniversalConstants.RATE_CUST_CAT_CASH:
		case RateUniversalConstants.RATE_CUST_CAT_ACC:
			rateType = RateUniversalConstants.RATE_TYPE_CH;
			break;
		case RateUniversalConstants.RATE_CUST_CAT_BA:
			rateType = RateUniversalConstants.RATE_TYPE_BA;
			break;
		}
		LOGGER.trace("OutManifestBaseConverter :: getRateType() :: END");
		return rateType;
	}

	/**
	 * To prepare ConsignmentBillingRateDO
	 * 
	 * @param cnRateCalcOutputTO
	 * @return consgBillingRateDO
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static ConsignmentBillingRateDO prepareCNBillingRateDO(
			ConsignmentRateCalculationOutputTO cnRateCalcOutputTO)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		LOGGER.trace("OutManifestBaseConverter :: prepareCNBillingRateDO() :: START");
		ConsignmentBillingRateDO consgBillingRateDO = new ConsignmentBillingRateDO();
		PropertyUtils.copyProperties(consgBillingRateDO, cnRateCalcOutputTO);
		consgBillingRateDO.setCreatedDate(DateUtil.getCurrentDate());
		consgBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
		LOGGER.trace("OutManifestBaseConverter :: prepareCNBillingRateDO() :: END");
		return consgBillingRateDO;
	}

	/**
	 * To prepare consignmentTO
	 * 
	 * @param bookingDO
	 * @param outManifestBaseTO
	 * @param rowId
	 * @param outManifestCommonService
	 * @return consignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ConsignmentTO prepareConsignmetTO(BookingDO bookingDO,
			OutManifestBaseTO outManifestBaseTO, int rowId,
			OutManifestCommonService outManifestCommonService)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestBaseConverter :: prepareConsignmetTO() :: START");
		ConsignmentTO consignmentTO = new ConsignmentTO();

		// Set Consignment Type - Mandatory
		if (!StringUtil.isNull(outManifestBaseTO.getConsignmentTypeTO())
				&& !StringUtil.isStringEmpty(outManifestBaseTO
						.getConsignmentTypeTO().getConsignmentCode())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			typeTO.setConsignmentCode(outManifestBaseTO.getConsignmentTypeTO()
					.getConsignmentCode());
			consignmentTO.setTypeTO(typeTO);
		}

		// Set Product Code - Mandatory

		String cnSeries = CommonConstants.EMPTY_STRING;
		Character cnSeriesChar = outManifestBaseTO.getConsgNos()[rowId]
				.substring(4, 5).toCharArray()[0];
		if (Character.isDigit(cnSeriesChar)) {
			cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
		} else {
			cnSeries = cnSeriesChar.toString();
		}

		ProductTO productTO = outManifestCommonService
				.getProductByConsgSeries(cnSeries);
		if (!StringUtil.isNull(productTO)) {
			consignmentTO.setProductTO(productTO);
		}

		// Set Operating office - Mandatory
		consignmentTO.setOperatingOffice(outManifestBaseTO.getLoginOfficeId());

		// Set Pincode - Mandatory
		PincodeTO destPincode = new PincodeTO();
		destPincode.setPincode(outManifestBaseTO.getPincodes()[rowId]);
		consignmentTO.setDestPincode(destPincode);

		// Set Final Weight - Mandatory
		if (!StringUtil.isEmptyDouble(outManifestBaseTO.getWeights()[rowId])) {
			consignmentTO.setFinalWeight(outManifestBaseTO.getWeights()[rowId]);
		}

		// Set consignment status
		consignmentTO.setConsgStatus(BookingConstants.BOOKING_NORMAL_PROCESS);

		// Set Booking date / Event date - Mandatory
		if (!StringUtil.isStringEmpty(consignmentTO.getConsgStatus())
				&& !StringUtil.equals(consignmentTO.getConsgStatus(),
						RthRtoManifestConstatnts.CONSIGNMENT_STATUS_RETURNED)) {
			consignmentTO.setBookingDate(bookingDO.getBookingDate());
		}

		// Set Customer Id
		if (!StringUtil.isNull(bookingDO.getCustomerId())
				&& !StringUtil.isEmptyInteger(bookingDO.getCustomerId()
						.getCustomerId())) {
			consignmentTO
					.setCustomer(bookingDO.getCustomerId().getCustomerId());
		}
		LOGGER.trace("OutManifestBaseConverter :: prepareConsignmetTO() :: END");
		return consignmentTO;
	}

	/**
	 * To calculate rate for Out Manifest DOX and PARCEL
	 * 
	 * @param outmanifestBaseTO
	 * @param rowId
	 * @return consgBillingRateDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ConsignmentBillingRateDO calcRateForOutManifest(
			OutManifestBaseTO outmanifestBaseTO, int rowId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestBaseConverter :: calcRateForOutManifest() :: START :: Consg No :: "
				+ outmanifestBaseTO.getConsgNos()[rowId]);
		ConsignmentBillingRateDO consgBillingRateDO = null;
		ConsignmentRateCalculationOutputTO cnRateCalcOutputTO = null;
		String CnNumver = outmanifestBaseTO.getConsgNos()[rowId];
		try {
			/* To get rate components */
			Map<String, ConsignmentRateCalculationOutputTO> rateComponent = outmanifestBaseTO
					.getRateCompnents();
			if (!CGCollectionUtils.isEmpty(rateComponent)) {
				cnRateCalcOutputTO = rateComponent.get(outmanifestBaseTO
						.getConsgNos()[rowId]);
				LOGGER.debug("OutManifestBaseConverter::calcRateForOutManifest()::consignment["
						+ CnNumver
						+ "] received stored rate is: "
						+ cnRateCalcOutputTO);
				if (!StringUtil.isNull(cnRateCalcOutputTO)) {
					LOGGER.debug("OutManifestBaseConverter::calcRateForOutManifest()::consignment["
							+ CnNumver
							+ "] rate is available. Preparing DO....");
					/* prepare consgBillingRateDO */
					consgBillingRateDO = OutManifestBaseConverter
							.prepareCNBillingRateDO(cnRateCalcOutputTO);
					consgBillingRateDO
							.setRateCalculatedFor(BookingConstants.BOOKING_NORMAL_PROCESS);
					consgBillingRateDO.setBilled(CommonConstants.NO);
					// Created and Updated should be same if record is creating
					consgBillingRateDO.setCreatedBy(outmanifestBaseTO
							.getUpdatedBy());
					consgBillingRateDO.setUpdatedBy(outmanifestBaseTO
							.getUpdatedBy());
					LOGGER.debug("OutManifestBaseConverter::calcRateForOutManifest()::consignment["
							+ CnNumver
							+ "] DO is prepared. Final amount is: "
							+ consgBillingRateDO.getGrandTotalIncludingTax());
				} else {
					LOGGER.debug("OutManifestBaseConverter::calcRateForOutManifest()::consignment["
							+ CnNumver + "] rate is empty or null");
				}
			} else {
				LOGGER.debug("OutManifestBaseConverter::calcRateForOutManifest()::rateComponents cannot be empty or null if manifest contains pickup CNs");
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in OutManifestBaseConverter :: calcRateForOutManifest() :: ",
					e);
			throw new CGBusinessException(
					ManifestErrorCodesConstants.RATE_NOT_CALC_FOR_CN);
		}
		LOGGER.debug("OutManifestBaseConverter :: calcRateForOutManifest() :: END :: Consg No :: "
				+ outmanifestBaseTO.getConsgNos()[rowId]);
		return consgBillingRateDO;
	}

	/**
	 * @param manifestCommonService
	 * @param allBooking
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static Boolean saveOrUpdatePickupRunsheetHeaderDetails(
			ManifestCommonService manifestCommonService,
			List<BookingDO> allBooking, OutManifestBaseTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestBaseConverter :: getPickupRunsheetHeaderByConsignmentNo() :: START");
		Set<PickupRunsheetHeaderDO> pickupRunsheetHeader = new HashSet<PickupRunsheetHeaderDO>(
				allBooking.size());
		PickupRunsheetHeaderDO pickupRunsheetHeaderDO = null;
		boolean isPickupUpdated = Boolean.FALSE;
		String[] consgNos = to.getConsgNos();

		// Get all Headers
		for (String cnNo : consgNos) {
			pickupRunsheetHeaderDO = manifestCommonService
					.getPickupRunsheetHeaderByConsignmentNo(cnNo);
			if (!StringUtil.isNull(pickupRunsheetHeaderDO)) {
				pickupRunsheetHeaderDO
						.setRunsheetStatus(ManifestConstants.PICKUP_RUNSHEET_HEADER_STATUS_CLOSED);
				pickupRunsheetHeaderDO
						.setUpdatedDate(DateUtil.getCurrentDate());
				pickupRunsheetHeaderDO.setUpdatedBy(to.getCreatedBy());
				pickupRunsheetHeaderDO
						.setDtFromOpsman(CommonConstants.DT_FROM_OPSMAN_R);
				pickupRunsheetHeaderDO.setDtToBranch(CommonConstants.NO);
				pickupRunsheetHeaderDO.setDtToCentral(CommonConstants.NO);
				pickupRunsheetHeaderDO.setDtUpdateToCentral(CommonConstants.NO);
				pickupRunsheetHeaderDO.setDtToOpsman(CommonConstants.NO);
				pickupRunsheetHeader.add(pickupRunsheetHeaderDO);
			}
		}
		// Update Pickup Runsheet Details.
		if (!CGCollectionUtils.isEmpty(pickupRunsheetHeader)) {
			isPickupUpdated = manifestCommonService
					.saveOrUpdatePickupRunsheetHeaderDetails(pickupRunsheetHeader);
		}
		LOGGER.trace("OutManifestBaseConverter :: getPickupRunsheetHeaderByConsignmentNo() :: END");
		return isPickupUpdated;
	}

}
