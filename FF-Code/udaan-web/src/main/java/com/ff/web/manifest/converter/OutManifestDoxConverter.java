package com.ff.web.manifest.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.CreditCustomerBookingConsignmentTO;
import com.ff.manifest.OutManifestDoxDetailsTO;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class OutManifestDoxConverter.
 */
public class OutManifestDoxConverter extends OutManifestBaseConverter {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestDoxConverter.class);

	/** The out manifest common service. */
	private static OutManifestCommonService outManifestCommonService;

	/** The manifestCommonService. */
	private static ManifestCommonService manifestCommonService;

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public static void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		OutManifestDoxConverter.manifestCommonService = manifestCommonService;
	}

	/**
	 * Sets the out manifest common service.
	 * 
	 * @param outManifestCommonService
	 *            the new out manifest common service
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		OutManifestDoxConverter.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * Sets the booking dtls to out manifest dox dtl. At the time reading the
	 * Consignment details from consignment table or booking table
	 * 
	 * @param cnModificationTO
	 *            the cn modification to
	 * @return the out manifest dox details to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 */
	public static OutManifestDoxDetailsTO setBookingDtlsToOutManifestDoxDtl(
			ConsignmentModificationTO cnModificationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: setBookingDtlsToOutManifestDoxDtl() :: START------------>:::::::");
		// Setting Common attributes
		OutManifestDoxDetailsTO outManifestDoxDtlTO = (OutManifestDoxDetailsTO) cnDtlsToOutMnfstDtlBaseConverter(
				cnModificationTO, ManifestUtil.prepareFactoryInputs(
						OutManifestConstants.OUT_MANIFEST,
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));

		ConsignmentTO consignmentTO = cnModificationTO.getConsigmentTO();
		if (!StringUtil.isNull(consignmentTO)) {
			CityTO destCity = new CityTO();
			destCity.setCityId(consignmentTO.getDestPincode().getCityId());

			CityTO cityTO = outManifestCommonService.getCity(destCity);
			if (!StringUtil.isNull(cityTO)) {
				outManifestDoxDtlTO.setDestCityId(cityTO.getCityId());
				outManifestDoxDtlTO.setDestCity(cityTO.getCityName());
			}
			/** Setting LC Amount details. */
			Double lcAmount = 0.0;
			String lcBankName = consignmentTO.getLcBankName();
			lcAmount = consignmentTO.getLcAmount();
			if (!StringUtil.isEmptyDouble(lcAmount)
					&& !StringUtil.isStringEmpty(lcBankName)) {
				String lcDtls = Double.toString(lcAmount)
						+ CommonConstants.HASH + lcBankName;
				outManifestDoxDtlTO.setLcDtls(lcDtls);
			}

			// Rate Integration
			/*
			 * double lcAmount = 0.0; String lcBankName =
			 * consignmentTO.getLcBankName(); // Setting LC Amount details.
			 * CNPricingDetailsTO cnPricingDetailsTO = consignmentTO
			 * .getConsgPriceDtls();
			 * 
			 * // If the pricing Details is not null if
			 * (!StringUtil.isNull(cnPricingDetailsTO)) { lcAmount =
			 * cnPricingDetailsTO.getLcAmount(); String lcDtls =
			 * Double.toString(lcAmount) + CommonConstants.HASH + lcBankName;
			 * outManifestDoxDtlTO.setLcDtls(lcDtls);
			 * outManifestDoxDtlTO.setPriceId(cnPricingDetailsTO.getPriceId());
			 * }
			 */
			// Setting specific attributes
			if (!StringUtil.isNull(consignmentTO.getConsigneeTO())) {
				outManifestDoxDtlTO.setMobileNo(consignmentTO.getConsigneeTO()
						.getMobile());
				outManifestDoxDtlTO.setConsigneeId(consignmentTO
						.getConsigneeTO().getPartyId());
			}
		}
		LOGGER.trace("OutManifestDoxConverter :: setBookingDtlsToOutManifestDoxDtl() :: END------------>:::::::");
		return outManifestDoxDtlTO;
	}

	/**
	 * Prepare out manifest dtl dox list.
	 * 
	 * @param outManifestDoxTO
	 *            the out manifest dox to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void prepareOutManifestDtlDoxList(
			OutManifestDoxTO outManifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestDoxConverter :: prepareOutManifestDtlDoxList() :: START------------>:::::::");
		int cnPosition = 1;
		int comailPosition = 1;
		int noOfElements = 0;
		if (!StringUtil.isNull(outManifestDoxTO)) { // If the header is not null

			// The detail is not null and has elements
			if (outManifestDoxTO.getConsgNos() != null
					&& outManifestDoxTO.getConsgNos().length > 0) {

				// if the list only contains co-mail
				if (StringUtils
						.equalsIgnoreCase(outManifestDoxTO.getIsCoMailOnly(),
								CommonConstants.YES)) {
					LOGGER.debug("Manifest contains Co-Mails only...");
					// Populate Comails from input grid
					noOfElements += putInComail(outManifestDoxTO,
							comailPosition, true);

				} else {

					// Populate the consignments from input grid
					noOfElements += putInConsignments(outManifestDoxTO,
							cnPosition);
					// Populate Comails from input grid
					noOfElements += putInComail(outManifestDoxTO,
							comailPosition, false);
				}
				outManifestDoxTO.setNoOfElements(noOfElements);
			}
		}

		/* Setting manifest status */
		if (!StringUtil.equals(outManifestDoxTO.getManifestStatus(),
				OutManifestConstants.CLOSE)) {
			String manifestStatus = determineManifestStatus(outManifestDoxTO);
			outManifestDoxTO.setManifestStatus(manifestStatus);
		}

		/* To set process code for booking process */
		setBookingProcessCode(outManifestDoxTO);

		LOGGER.debug("OutManifestDoxConverter :: prepareOutManifestDtlDoxList() :: END------------>:::::::");
	}

	/**
	 * To set process code for booking
	 * 
	 * @param outManifestDoxTO
	 */
	private static void setBookingProcessCode(OutManifestDoxTO outManifestDoxTO)
			throws CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: setBookingProcessCode() :: START------------>:::::::");
		for (CreditCustomerBookingDoxTO creditCustomerBookingDoxTO : outManifestDoxTO
				.getCreditCustomerBookingDoxTOList()) {
			ProcessTO processTO = new ProcessTO();
			if (StringUtil.equals(outManifestDoxTO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				LOGGER.debug("Manifest Status is CLOSED so need to update Booking UPDATED_PROCESS_FROM column as OPKT");
				processTO.setProcessId(outManifestDoxTO.getProcessId());
				creditCustomerBookingDoxTO.setProcessTO(processTO);
			} else {
				LOGGER.debug("Manifest Status is OPEN so need not to update Booking UPDATED_PROCESS_FROM for Pickedup Consignments");
				ProcessDO processDO = outManifestCommonService
						.getProcess(CommonConstants.PROCESS_PICKUP);
				processTO.setProcessId(processDO.getProcessId());
				creditCustomerBookingDoxTO.setProcessTO(processTO);
			}

		}
		LOGGER.debug("OutManifestDoxConverter :: setBookingProcessCode() :: END------------>:::::::");
	}

	/***
	 * 
	 * @param outManifestDoxTO
	 * @param cnPosition
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private static int putInConsignments(OutManifestDoxTO outManifestDoxTO,
			int cnPosition) throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: START------------>:::::::");
		int noOfElements = 0;
		OutManifestDoxDetailsTO outManifestDoxDetailsTO = null;
		CreditCustomerBookingConsignmentTO creditCustomerBookingConsignmentTO = null;
		ConsignmentTO consignmentTO = null;
		// ComailTO comailTO = null;
		// Grid item as Consignments
		for (int rowCount = 0; rowCount < outManifestDoxTO.getConsgNos().length; rowCount++) {

			if (StringUtils
					.isNotEmpty(outManifestDoxTO.getConsgNos()[rowCount])) {
				LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: CONSIGNMENT NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
				noOfElements++;
				outManifestDoxDetailsTO = new OutManifestDoxDetailsTO();
				// Setting the common grid level attributes
				outManifestDoxDetailsTO = (OutManifestDoxDetailsTO) setUpManifestDtlsTOs(
						outManifestDoxTO, rowCount);

				// Setting the data to indicate whether the consignment is
				// changed
				outManifestDoxDetailsTO.setIsDataMismatch(outManifestDoxTO
						.getIsDataMismatched()[rowCount]);

				if (!StringUtil.isStringEmpty(outManifestDoxTO
						.getIsCNProcessedFromPickup()[rowCount])
						&& StringUtil
								.equals(outManifestDoxTO
										.getIsCNProcessedFromPickup()[rowCount],
										CommonConstants.YES)) {
					// Consignments created from PICKUP process
					LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: START preparion of Booking and Consignment DOs for PICKED UP CONSIGNMENT NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
					outManifestDoxDetailsTO.setGridItemType("P");
					creditCustomerBookingConsignmentTO = prepareCreditCustomerBookingDoxTO(
							outManifestDoxTO, rowCount);
					creditCustomerBookingConsignmentTO
							.setConsignmentTO(consignmentTO);
					outManifestDoxTO.getCreditCustomerBookingDoxTOList().add(
							creditCustomerBookingConsignmentTO
									.getCreditCustomerBookingDoxTO());
					LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: END preparion of Booking and Consignment DOs for PICKED UP CONSIGNMENT NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
				} else {
					if (CommonConstants.YES
							.compareToIgnoreCase(outManifestDoxDetailsTO
									.getIsDataMismatch()) == 0) {
						// Consignments created from BOOKING process
						LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: START preparion of Consignment DO for BOOKING CONSIGNMENT NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
						consignmentTO = prepareConsignmentTO(outManifestDoxTO,
								rowCount);

						outManifestDoxDetailsTO.setGridItemType("C");
						outManifestDoxTO.getConsignmentstoBeUpdatedList().add(
								consignmentTO);
						LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: END preparion of Consignment DO for BOOKING CONSIGNMENT NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
					}
				}
				/*
				 * outManifestDoxDetailsTO.setConsgManifestedId(outManifestDoxTO
				 * .getConsgManifestedIds()[rowCount]);
				 */

				outManifestDoxDetailsTO.setBkgWeight(outManifestDoxTO
						.getBookingWeights()[rowCount]);

				outManifestDoxDetailsTO.setIsDataMismatch(outManifestDoxTO
						.getIsDataMismatched()[rowCount]);
				outManifestDoxDetailsTO.setLcDtls(outManifestDoxTO
						.getLcDetails()[rowCount]);
				outManifestDoxDetailsTO.setMobileNo(outManifestDoxTO
						.getMobileNos()[rowCount]);
				outManifestDoxDetailsTO.setConsigneeId(outManifestDoxTO
						.getConsigneeIds()[rowCount]);

				outManifestDoxDetailsTO.setPosition(cnPosition++);
				outManifestDoxTO.getOutManifestDoxDetailTOs().add(
						outManifestDoxDetailsTO);
			}/* END of IF */
		}/* END of FOR LOOP */
		LOGGER.debug("OutManifestDoxConverter :: putInConsignments() :: END------------>:::::::");
		return noOfElements;
	}

	/**
	 * 
	 * @param outManifestDoxTO
	 * @param comailPosition
	 */
	private static int putInComail(OutManifestDoxTO outManifestDoxTO,
			int comailPosition, boolean comailOnly) {
		LOGGER.trace("OutManifestDoxConverter :: putInComail() :: START------------>:::::::");
		int noOfElements = 0;
		OutManifestDoxDetailsTO outManifestDoxDetailsTO = null;
		ComailTO comailTO = null;

		// Iterating the List
		if (comailOnly) {
			for (int rowCount = 0; rowCount < outManifestDoxTO.getConsgNos().length; rowCount++) {
				// Setting the common grid level attributes
				if (StringUtils
						.isNotEmpty(outManifestDoxTO.getConsgNos()[rowCount])) {
					LOGGER.debug("OutManifestDoxConverter :: putInComail() :: COMAIL NO # "+outManifestDoxTO.getConsgNos()[rowCount]);
					if (!StringUtil.isEmpty(outManifestDoxTO.getConsgNos())
							&& StringUtils.isNotEmpty(outManifestDoxTO
									.getConsgNos()[rowCount])) {
						noOfElements++;
						outManifestDoxDetailsTO = new OutManifestDoxDetailsTO();
						comailTO = new ComailTO();
						outManifestDoxDetailsTO.setComailId(outManifestDoxTO
								.getConsgIds()[rowCount]);
						// UAT
						comailTO.setCoMailId(outManifestDoxTO.getConsgIds()[rowCount]);

						outManifestDoxDetailsTO.setComailNo(outManifestDoxTO
								.getConsgNos()[rowCount]);
						// UAT
						comailTO.setCoMailNo(outManifestDoxTO.getConsgNos()[rowCount]);

						// TODO - Check for Null pointer
						outManifestDoxDetailsTO
								.setComailManifestedId(outManifestDoxTO
										.getConsgManifestedIds()[rowCount]);
					}
					outManifestDoxDetailsTO.setPosition(comailPosition++);
					// UAT
					outManifestDoxDetailsTO.setComailTO(comailTO);
					outManifestDoxTO.getComails().add(comailTO);
					outManifestDoxDetailsTO.setGridItemType("M");

					outManifestDoxTO.getOutManifestDoxDetailTOs().add(
							outManifestDoxDetailsTO);
				}
			}
		}

		// For Comails Only
		for (int rowCount = 0; rowCount < outManifestDoxTO.getComailNos().length; rowCount++) {

			// If the comail list is not empty
			if (StringUtils
					.isNotEmpty(outManifestDoxTO.getComailNos()[rowCount])) {
				LOGGER.debug("OutManifestDoxConverter :: putInComail() :: COMAIL NO # "+outManifestDoxTO.getComailNos()[rowCount]);
				outManifestDoxDetailsTO = new OutManifestDoxDetailsTO();
				// UAT
				comailTO = new ComailTO();
				if (StringUtils
						.isNotEmpty(outManifestDoxTO.getComailNos()[rowCount])) {
					outManifestDoxDetailsTO.setComailNo(outManifestDoxTO
							.getComailNos()[rowCount]);
					comailTO.setCoMailNo(outManifestDoxTO.getConsgNos()[rowCount]);
					if (!StringUtil.isEmpty(outManifestDoxTO.getComailIds())
							&& !StringUtil.isEmptyInteger(outManifestDoxTO
									.getComailIds()[rowCount])) {
						noOfElements++;
						outManifestDoxDetailsTO.setComailId(outManifestDoxTO
								.getComailIds()[rowCount]);
						// UAT
						comailTO.setCoMailId(outManifestDoxTO.getConsgIds()[rowCount]);
						outManifestDoxDetailsTO
								.setComailManifestedId(outManifestDoxTO
										.getComailManifestedIds()[rowCount]);
					}
					outManifestDoxDetailsTO.setPosition(comailPosition++);
					// UAT
					outManifestDoxDetailsTO.setComailTO(comailTO);
					outManifestDoxTO.getComails().add(comailTO);
					outManifestDoxDetailsTO.setGridItemType("M");

				}
				outManifestDoxTO.getOutManifestDoxDetailTOs().add(
						outManifestDoxDetailsTO);
			}
		}
		LOGGER.debug("OutManifestDoxConverter :: putInComail() :: END------------>:::::::");
		return noOfElements;
	}

	// Booking & Consignment
	private static CreditCustomerBookingConsignmentTO prepareCreditCustomerBookingDoxTO(
			OutManifestDoxTO outManifestTO, Integer rowcount)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxConverter :: prepareCreditCustomerBookingDoxTO() :: START------------> Pickedup Consignment No ::" + outManifestTO.getConsgNos()[rowcount]);
		CreditCustomerBookingConsignmentTO creditCustomerBookingConsignmentTO = new CreditCustomerBookingConsignmentTO();

		CreditCustomerBookingDoxTO creditCustomerBookingDoxTO = new CreditCustomerBookingDoxTO();
		ConsignmentTO consignmentTO = null;

		creditCustomerBookingDoxTO.setBookingDate(outManifestTO
				.getManifestDate());
		creditCustomerBookingDoxTO.setConsgTypeId(outManifestTO
				.getConsignmentTypeTO().getConsignmentId());
		creditCustomerBookingDoxTO.setBookingOfficeId(outManifestTO
				.getLoginOfficeId());
		creditCustomerBookingDoxTO
				.setBookingId(outManifestTO.getBookingIds()[rowcount]);
		creditCustomerBookingDoxTO
				.setConsgNumber(outManifestTO.getConsgNos()[rowcount]);
		creditCustomerBookingDoxTO
				.setPincodeId(outManifestTO.getPincodeIds()[rowcount]);
		creditCustomerBookingDoxTO
				.setCityId(outManifestTO.getDestCityIds()[rowcount]);
		creditCustomerBookingDoxTO
				.setFinalWeight(outManifestTO.getWeights()[rowcount]);
		creditCustomerBookingDoxTO
				.setActualWeight(outManifestTO.getWeights()[rowcount]);
		creditCustomerBookingDoxTO.setBookingTypeId(outManifestTO
				.getBookingTypeIds()[rowcount]);
		creditCustomerBookingDoxTO
				.setCustomerId(outManifestTO.getCustomerIds()[rowcount]);
		creditCustomerBookingDoxTO.setPickupRunsheetNo(outManifestTO
				.getRunsheetNos()[rowcount]);
		creditCustomerBookingDoxTO.setReCalcRateReq(Boolean.TRUE);
		ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
		consignorTO.setPartyId(outManifestTO.getConsignorIds()[rowcount]);
		creditCustomerBookingDoxTO.setConsignor(consignorTO);

		consignmentTO = prepareConsignmentTO(outManifestTO, rowcount);

		creditCustomerBookingConsignmentTO
				.setCreditCustomerBookingDoxTO(creditCustomerBookingDoxTO);
		creditCustomerBookingConsignmentTO.setConsignmentTO(consignmentTO);
		LOGGER.debug("OutManifestDoxConverter :: prepareCreditCustomerBookingDoxTO() :: END------------>:::::::");
		return creditCustomerBookingConsignmentTO;
	}

	private static ConsignmentTO prepareConsignmentTO(
			OutManifestDoxTO outManifestTO, Integer rowcount)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxConverter :: prepareConsignmentTO() :: START------------>:::::::");
		ConsignmentTO consigmentTO = new ConsignmentTO();
		consigmentTO.setConsgNo(outManifestTO.getConsgNos()[rowcount]);
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessId(outManifestTO.getProcessId());
		consigmentTO.setUpdatedProcessFrom(processTO);
		LOGGER.debug("OutManifestDoxConverter :: prepareConsignmentTO() :: Consignment No :: "+consigmentTO.getConsgNo());
		
		boolean isDataMismatch = Boolean.FALSE;
		if (outManifestTO.getWeights()[rowcount].doubleValue() > outManifestTO
				.getBookingWeights()[rowcount].doubleValue()) {
			consigmentTO.setFinalWeight(outManifestTO.getWeights()[rowcount]
					.doubleValue());
			isDataMismatch = Boolean.TRUE;
		} else
			consigmentTO
					.setFinalWeight(outManifestTO.getBookingWeights()[rowcount]
							.doubleValue());
		PincodeTO pincodeTO = new PincodeTO();
		pincodeTO.setPincodeId(outManifestTO.getPincodeIds()[rowcount]);
		consigmentTO.setDestPincode(pincodeTO);

		// operating level needs to be set in case of weight/destination changes
		if (isDataMismatch) {
			consigmentTO.setOrgOffId(outManifestTO.getLoginOfficeId());
			consigmentTO.setDestOffice(null);
			if (outManifestTO.getDestinationOfficeId() != null) {
				OfficeTO destoffTO = new OfficeTO();
				destoffTO.setOfficeId(outManifestTO.getDestinationOfficeId());
				consigmentTO.setDestOffice(destoffTO);
			}
			CityTO destCityTO = new CityTO();
			destCityTO.setCityId(outManifestTO.getDestCityIds()[rowcount]);
			consigmentTO.setDestCity(destCityTO);
			OfficeTO offTO = new OfficeTO();
			offTO.setOfficeId(outManifestTO.getLoginOfficeId());
			Integer operatingLevel = outManifestCommonService
					.getConsgOperatingLevel(consigmentTO, offTO);
			consigmentTO.setOperatingLevel(operatingLevel);
			// Setting manifest login office in consignment table
			consigmentTO.setOperatingOffice(outManifestTO.getLoginOfficeId());
		}
		ConsignorConsigneeTO consigneeTO = new ConsignorConsigneeTO();
		consigneeTO.setPartyId(outManifestTO.getConsigneeIds()[rowcount]);
		consigneeTO.setMobile(outManifestTO.getMobileNos()[rowcount]);
		consigmentTO.setConsigneeTO(consigneeTO);

		/*
		 * // Rate Details
		 * 
		 * CNPricingDetailsTO cnPricingDetailsTO = null;
		 * 
		 * // Find out the appropriate detail of the header
		 * OutManifestDoxDetailsTO outManifestDoxDetailsTO = outManifestTO
		 * .getOutManifestDoxDetailTOs().get(rowcount);
		 * 
		 * String cnNumberSeries = consigmentTO.getConsgNo().substring(4, 5); if
		 * (StringUtils.equalsIgnoreCase(cnNumberSeries,
		 * OutManifestConstants.CONSG_SERIES_D) &&
		 * StringUtils.isNotEmpty(outManifestDoxDetailsTO.getLcDtls())) {
		 * cnPricingDetailsTO = new CNPricingDetailsTO();
		 * 
		 * // Splitting the LC Details to derive the Bank Name & LC Amount
		 * String[] lcDtls = outManifestDoxDetailsTO.getLcDtls().split(
		 * CommonConstants.HASH);
		 * cnPricingDetailsTO.setPriceId(outManifestDoxDetailsTO.getPriceId());
		 * Double lcAmount = 0.0; String bankName = ""; if
		 * (!StringUtil.isEmpty(lcDtls)) { if
		 * (StringUtils.isNotEmpty(lcDtls[0])) lcAmount =
		 * Double.parseDouble(lcDtls[0].toString()); if
		 * (StringUtils.isNotEmpty(lcDtls[1])) bankName = lcDtls[1]; }
		 * 
		 * cnPricingDetailsTO.setLcAmount(lcAmount);
		 * cnPricingDetailsTO.setBankName(bankName);
		 * //consigmentTO.setLcBankName(bankName);
		 * 
		 * consigmentTO.setConsgPriceDtls(cnPricingDetailsTO);
		 * 
		 * }
		 */

		LOGGER.debug("OutManifestDoxConverter :: prepareConsignmentTO() :: END------------>:::::::");
		return consigmentTO;
	}

	/**
	 * Prepare manifest do list.
	 * 
	 * @param outmanifestDoxTO
	 *            the outmanifest dox to
	 * @param manifestDO2
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestDO prepareManifestDO(
			OutManifestDoxTO outmanifestDoxTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: prepareManifestDO() :: START------------>:::::::");

		/* Setting HEADER attributes */
		manifestDO = outManifestTransferObjConverter(outmanifestDoxTO,
				manifestDO);

		/* Specific to Out manifest dox */
		manifestDO.setContainsOnlyCoMail(outmanifestDoxTO.getIsCoMailOnly());
		if (!StringUtil.isNull(outmanifestDoxTO.getProduct())) {
			ProductDO productDO = new ProductDO();
			productDO = (ProductDO) CGObjectConverter.createDomainFromTo(
					outmanifestDoxTO.getProduct(), productDO);
			manifestDO.setManifestedProductSeries(productDO);
			manifestDO.setAllowSpecificContent(CommonConstants.YES);
		} else {
			manifestDO.setAllowSpecificContent(CommonConstants.NO);
		}
		/* Newly added fields for BCUN */
		manifestDO.setManifestOpenType(outmanifestDoxTO.getManifestOpenType());

		/*
		 * Setting grid items Set ConsignmentManifestDO Setting corresponding
		 * Consignment Ids in Out manifest details TO
		 */
		/*
		 * if (!StringUtil.isEmptyList(consignmentDOList)) {
		 * setPickedupConsignmentIds(outmanifestDoxTO, consignmentDOList); }
		 */
		String manifestStatus = CommonConstants.EMPTY_STRING;

		int noOfConsg = 0, noOfComail = 0;
		if (!StringUtil.isEmptyColletion(manifestDO.getConsignments())
				&& !StringUtil.isEmptyInteger(manifestDO.getConsignments()
						.size())) {
			noOfConsg = manifestDO.getConsignments().size();
		}

		if (!StringUtil.isEmptyColletion(manifestDO.getComails())
				&& !StringUtil.isEmptyInteger(manifestDO.getComails().size())) {
			noOfComail = manifestDO.getComails().size();
		}

		int noOfElements = noOfConsg + noOfComail;

		/** Set ConsignmentDO */
		/*
		 * Set<ConsignmentDO> consignments = new LinkedHashSet<ConsignmentDO>();
		 * List<String> consgNoList = new ArrayList<String>();
		 * List<ConsignmentDO> consgDOList = null; if
		 * (!StringUtil.isEmpty(outmanifestDoxTO.getConsgNos())) { Prepare
		 * consigment nos. list for booked CN. for (int i = 0; i <
		 * outmanifestDoxTO.getConsgNos().length; i++) { if
		 * (outmanifestDoxTO.getStatus()[i] == "N") {
		 * consgNoList.add(outmanifestDoxTO.getConsgNos()[i]); } }
		 * 
		 * Get all consg. by consg. nos. if
		 * (!StringUtil.isEmptyColletion(consgNoList) && consgNoList.size() > 0)
		 * { consgDOList = manifestCommonService .getConsignments(consgNoList);
		 * consignments.addAll(consgDOList); }
		 * 
		 * if (!StringUtil.isEmptyColletion(consignmentDOList)) {
		 * consignments.addAll(consignmentDOList); } noOfElements +=
		 * consignments.size(); manifestDO.setConsignments(consignments); }
		 *//** Set ComailDO */
		/*
		 * 
		 * Set<ComailDO> comailSet = null; if
		 * (!StringUtil.isEmptyColletion(manifestDO.getComails())) {
		 * 
		 * comailSet = ManifestUtil.setCoMailManifestDtls(outmanifestDoxTO,
		 * outmanifestDoxTO.getOutManifestDoxDetailTOs());
		 * 
		 * comailSet = ManifestUtil.setCoMailDtls(outmanifestDoxTO,
		 * outmanifestDoxTO.getOutManifestDoxDetailTOs(),
		 * manifestDO.getComails()); noOfElements += comailSet.size();
		 * manifestDO.setComails(comailSet); }
		 */

		Double manifestWeight = 0.0;
		for (OutManifestDoxDetailsTO doxDetailsTO : outmanifestDoxTO
				.getOutManifestDoxDetailTOs()) {
			if (!StringUtil.isEmptyDouble(doxDetailsTO.getWeight()))
				manifestWeight = manifestWeight + doxDetailsTO.getWeight();
		}
		outmanifestDoxTO.setFinalWeight(manifestWeight);
		manifestDO.setManifestWeight(manifestWeight);

		manifestStatus = outmanifestDoxTO.getManifestStatus();
		if (StringUtils.isNotEmpty(outmanifestDoxTO.getMaxCNsAllowed())
				&& StringUtils.isNotEmpty(outmanifestDoxTO
						.getMaxComailsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(outmanifestDoxTO
					.getMaxCNsAllowed());
			int maxComailsAllowed = Integer.parseInt(outmanifestDoxTO
					.getMaxComailsAllowed());
			// if Comail is checked all the rows in the grid are considered as
			// co-mails only and max grid size is max CNs and max Co-mails.
			if (StringUtils.equalsIgnoreCase(
					outmanifestDoxTO.getIsCoMailOnly(), CommonConstants.YES)) {
				int maxRows = maxCNsAllowed + maxComailsAllowed;
				if (noOfElements == maxRows) {
					manifestStatus = OutManifestConstants.CLOSE;
				}
			} else if (noOfConsg == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		manifestDO.setManifestStatus(manifestStatus);
		// manifestDO.setComails(comailSet);
		manifestDO.setNoOfElements(noOfElements);
		outmanifestDoxTO.setNoOfElements(noOfElements);
		outmanifestDoxTO.setManifestStatus(manifestStatus);

		/* Added manifest type PURE or TRANSHIPMENT - New C.R. */
		if (StringUtils.equalsIgnoreCase(outmanifestDoxTO.getOgmManifestType(),
				OutManifestConstants.MANIFEST_TYPE_PURE)) {
			manifestDO.setBplManifestType(ManifestConstants.PURE);
		} else if (StringUtils.equalsIgnoreCase(
				outmanifestDoxTO.getOgmManifestType(),
				OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
			manifestDO.setBplManifestType(ManifestConstants.TRANS);
		}

		LOGGER.trace("OutManifestDoxConverter :: prepareManifestDO() :: END------------>:::::::");
		return manifestDO;
	}

	// Setting Pickedup consignment Ids
	/*
	 * private static void setPickedupConsignmentIds( OutManifestDoxTO
	 * outmanifestDoxTO, List<ConsignmentDO> consignmentDOList) { LOGGER.trace(
	 * "OutManifestDoxConverter :: setPickedupConsignmentIds() :: START------------>:::::::"
	 * ); List<OutManifestDoxDetailsTO> outManifestDoxDetailsTOList =
	 * outmanifestDoxTO .getOutManifestDoxDetailTOs(); for
	 * (OutManifestDoxDetailsTO outManifestDoxDetailsTO :
	 * outManifestDoxDetailsTOList) { for (ConsignmentDO consignmentDO :
	 * consignmentDOList) { if
	 * (StringUtils.equalsIgnoreCase(consignmentDO.getConsgNo(),
	 * outManifestDoxDetailsTO.getConsgNo())) {
	 * outManifestDoxDetailsTO.setConsgId(consignmentDO .getConsgId()); } } }
	 * LOGGER.trace(
	 * "OutManifestDoxConverter :: setPickedupConsignmentIds() :: END------------>:::::::"
	 * ); }
	 */

	/**
	 * Prepare manifest process do list.
	 * 
	 * @param outmanifestDoxTO
	 *            the outmanifest dox to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	/*public static List<ManifestProcessDO> prepareManifestProcessDOList(
			OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: prepareManifestProcessDOList() :: START------------>:::::::");
		List<ManifestProcessDO> ManifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;
		// Setting Common attributes
		manifestProcessDO = outManifestBaseTransferObjConverter(outmanifestDoxTO);
		// Specific to Out manifest dox
		manifestProcessDO.setNoOfElements(outmanifestDoxTO.getNoOfElements());
		manifestProcessDO.setManifestStatus(outmanifestDoxTO
				.getManifestStatus());
		manifestProcessDO.setManifestOpenType(outmanifestDoxTO
				.getManifestOpenType());
		manifestProcessDO.setContainsOnlyCoMail(outmanifestDoxTO
				.getIsCoMailOnly());

		ManifestProcessDOs.add(manifestProcessDO);
		LOGGER.trace("OutManifestDoxConverter :: prepareManifestProcessDOList() :: END------------>:::::::");
		return ManifestProcessDOs;
	}
*/
	/**
	 * Out manifest dox domain converter.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @return the out manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static OutManifestDoxTO outManifestDoxDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: outManifestDoxDomainConverter() :: START------------>:::::::");
		int count = 0;
		double consgToatalWt = 0.0;
		double lcTotal = 0.0;

		// Set the common attributes for the header
		OutManifestDoxTO outManifestDoxTO = (OutManifestDoxTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.prepareFactoryInputs(
						OutManifestConstants.OUT_MANIFEST,
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));

		// Set the specific attributes for header
		outManifestDoxTO.setIsCoMailOnly(manifestDO.getContainsOnlyCoMail());

		// Destination Region
		if (!StringUtil.isNull(outManifestDoxTO.getDestinationCityTO())) {
			RegionTO destRegionTO = new RegionTO();
			destRegionTO.setRegionId(outManifestDoxTO.getDestinationCityTO()
					.getRegion());
			outManifestDoxTO.setDestRegionTO(destRegionTO);
		}

		// Newly added fields for BCUN purpose
		if (StringUtils.isNotEmpty(manifestDO.getManifestOpenType()))
			outManifestDoxTO.setManifestOpenType(manifestDO
					.getManifestOpenType());

		/** Added manifest type PURE or TRANSHIPMENT - New C.R. */
		if (!StringUtil.isStringEmpty(manifestDO.getBplManifestType())) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getBplManifestType(),
					ManifestConstants.PURE)) {// PURE
				outManifestDoxTO
						.setOgmManifestType(OutManifestConstants.MANIFEST_TYPE_PURE);
			} else {// TRANSHIPMENT
				outManifestDoxTO
						.setOgmManifestType(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE);
			}
		}

		// prepare consignment details
		Set<ConsignmentDOXDO> doxConsignmentDOs = null;
		// Set the attributes for detailTO
		List<OutManifestDoxDetailsTO> outManifestDoxDetailTOs = new ArrayList<OutManifestDoxDetailsTO>(
				manifestDO.getNoOfElements());
		if (!StringUtil.isEmptyColletion(manifestDO.getDoxConsignments())) {
			doxConsignmentDOs = manifestDO.getDoxConsignments();
			for (ConsignmentDOXDO doxConsignment : doxConsignmentDOs) {
				OutManifestDoxDetailsTO outManifestDoxDtlTO = (OutManifestDoxDetailsTO) ManifestUtil
						.consignmentDomainConverter(
								doxConsignment,
								ManifestUtil
										.prepareFactoryInputs(
												OutManifestConstants.OUT_MANIFEST,
												CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));
				// Set Destination
				CityTO city = outManifestCommonService
						.getCity(outManifestDoxDtlTO.getPincode());
				if (!StringUtil.isNull(city)) {
					outManifestDoxDtlTO.setDestCityId(city.getCityId());
					outManifestDoxDtlTO.setDestCity(city.getCityName());
				}
				// Set Consignee
				if (!StringUtil.isNull(doxConsignment.getConsignee())) {
					outManifestDoxDtlTO.setMobileNo(doxConsignment
							.getConsignee().getMobile());
					outManifestDoxDtlTO.setConsigneeId(doxConsignment
							.getConsignee().getPartyId());
				}
				// Total weight for printer
				if (!StringUtil.isEmptyDouble(outManifestDoxDtlTO.getWeight())) {
					consgToatalWt += outManifestDoxDtlTO.getWeight();
				}
				// Setting LC Amount And Bank Name - D Series
				if (!StringUtil.isEmptyDouble(doxConsignment.getLcAmount())
						&& !StringUtil.isStringEmpty(doxConsignment
								.getLcBankName())) {
					outManifestDoxDtlTO.setLcDtls(doxConsignment.getLcAmount()
							+ CommonConstants.HASH
							+ doxConsignment.getLcBankName());
				}
				// Setting COD Amount - L Series/ T Series
				if (!StringUtil.isEmptyDouble(doxConsignment.getCodAmt())) {
					outManifestDoxDtlTO.setLcDtls(doxConsignment.getCodAmt()
							+ CommonConstants.HASH + CommonConstants.ZERO);
				}
				if (!StringUtil.isEmptyDouble(doxConsignment.getLcAmount())) {
					outManifestDoxDtlTO.setAmount(doxConsignment.getLcAmount());
					lcTotal = (lcTotal + doxConsignment.getLcAmount());
				}
				if (!StringUtil.isNull(doxConsignment.getUpdatedProcess())
						&& doxConsignment
								.getUpdatedProcess()
								.getProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_PICKUP)) {
					outManifestDoxDtlTO.setIsPickupCN(CommonConstants.YES);
				} else {
					outManifestDoxDtlTO.setIsPickupCN(CommonConstants.NO);
				}
				count++;
				outManifestDoxDetailTOs.add(outManifestDoxDtlTO);
			}/* END of FOR EACH */
		}

		// prepare comail details
		Set<ComailDO> comails = null;
		// Set the attributes for detailTO
		if (!StringUtil.isEmptyColletion(manifestDO.getComails())) {
			comails = manifestDO.getComails();

			for (ComailDO comailDO : comails) {
				OutManifestDoxDetailsTO outManifestDoxDtlTO = (OutManifestDoxDetailsTO) ManifestUtil
						.comailDomainConverter(
								comailDO,
								ManifestUtil
										.prepareFactoryInputs(
												OutManifestConstants.OUT_MANIFEST,
												CommonConstants.CONSIGNMENT_TYPE_DOCUMENT));
				outManifestDoxDetailTOs.add(outManifestDoxDtlTO);
			}/* END of FOR EACH */
		}

		// Collections.sort(outManifestDoxDetailTOs);
		outManifestDoxTO.setOutManifestDoxDetailTOs(outManifestDoxDetailTOs);
		outManifestDoxTO.setTotalConsg(count);
		outManifestDoxTO.setTotalLcAmount(lcTotal);
		outManifestDoxTO.setConsigTotalWt(Double.parseDouble(new DecimalFormat(
				"##.###").format(consgToatalWt)));
		LOGGER.trace("OutManifestDoxConverter :: outManifestDoxDomainConverter() :: END------------>:::::::");
		return outManifestDoxTO;
	}

	public static CreditCustomerBookingDoxTO prepareCreditCustomerBookingDoxTO(
			OutManifestDoxDetailsTO outManifestTO,
			CreditCustomerBookingDoxTO doxTO) throws CGSystemException {
		LOGGER.trace("OutManifestDoxConverter :: prepareCreditCustomerBookingDoxTO() :: START------------>:::::::");
		doxTO.setBookingId(outManifestTO.getBookingId());
		doxTO.setConsgNumber(outManifestTO.getConsgNo());
		doxTO.setPincodeId(outManifestTO.getPincodeId());
		doxTO.setCityId(outManifestTO.getDestCityId());
		doxTO.setFinalWeight(outManifestTO.getWeight());
		doxTO.setActualWeight(outManifestTO.getWeight());
		doxTO.setBookingTypeId(outManifestTO.getBookingTypeId());
		doxTO.setCustomerId(outManifestTO.getCustomerId());
		doxTO.setPickupRunsheetNo(outManifestTO.getRunsheetNo());
		doxTO.setReCalcRateReq(Boolean.TRUE);
		ConsignorConsigneeTO consignorTO = new ConsignorConsigneeTO();
		consignorTO.setPartyId(outManifestTO.getConsignorId());
		doxTO.setConsignor(consignorTO);
		if (!StringUtil.isEmptyInteger(outManifestTO.getConsigneeId())) {
			ConsignorConsigneeTO consigneeTO = outManifestCommonService
					.getConsigneeConsignorDtls(outManifestTO.getConsgNo(),
							CommonConstants.PARTY_TYPE_CONSIGNEE);
			if (!StringUtil.isNull(consigneeTO)) {
				doxTO.setConsignee(consigneeTO);
			}
		}
		LOGGER.trace("OutManifestDoxConverter :: prepareCreditCustomerBookingDoxTO() :: END------------>:::::::");
		return doxTO;
	}

	/**
	 * Out manifest dox grid details for in manif consg.
	 * 
	 * @param consTO
	 *            the cons to
	 * @return the out manifest dox details to
	 */
	public static OutManifestDoxDetailsTO outManifestDoxGridDetailsForInManifConsg(
			ConsignmentTO consTO) {
		LOGGER.trace("OutManifestDoxConverter :: outManifestDoxGridDetailsForInManifConsg() :: START------------>:::::::");
		OutManifestDoxDetailsTO outManifestDoxDetailsTO = new OutManifestDoxDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			outManifestDoxDetailsTO.setConsgNo(consTO.getConsgNo());
			outManifestDoxDetailsTO.setConsgId(consTO.getConsgId());
		}

		if (!StringUtil.isNull(consTO.getDestPincode())) {
			outManifestDoxDetailsTO.setPincode(consTO.getDestPincode()
					.getPincode());
			outManifestDoxDetailsTO.setPincodeId(consTO.getDestPincode()
					.getPincodeId());
		}

		if (!StringUtil.isNull(consTO.getDestCity())) {
			outManifestDoxDetailsTO.setDestCityId(consTO.getDestCity()
					.getCityId());
			outManifestDoxDetailsTO.setDestCity(consTO.getDestCity()
					.getCityName());
		}

		if (!StringUtil.isNull(consTO.getFinalWeight())) {
			outManifestDoxDetailsTO.setWeight(consTO.getFinalWeight());
			outManifestDoxDetailsTO.setBkgWeight(consTO.getFinalWeight());
		}
		LOGGER.trace("OutManifestDoxConverter :: outManifestDoxGridDetailsForInManifConsg() :: END------------>:::::::");
		return outManifestDoxDetailsTO;
	}

	/**
	 * To determine Manifest Status
	 * 
	 * @param outManifestDoxTO
	 * @return String
	 */
	private static String determineManifestStatus(
			OutManifestDoxTO outManifestDoxTO) {
		LOGGER.debug("OutManifestDoxConverter :: determineManifestStatus() :: START------------>:::::::");
		String manifestStatus = OutManifestConstants.OPEN;
		int noOfElements = outManifestDoxTO.getNoOfElements();
		if (StringUtils.isNotEmpty(outManifestDoxTO.getMaxCNsAllowed())
				&& StringUtils.isNotEmpty(outManifestDoxTO
						.getMaxComailsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(outManifestDoxTO
					.getMaxCNsAllowed());
			int maxComailsAllowed = Integer.parseInt(outManifestDoxTO
					.getMaxComailsAllowed());
			// if Comail is checked all the rows in the grid are considered as
			// co-mails only and max grid size is max CNs and max Co-mails.
			if (StringUtils.equalsIgnoreCase(
					outManifestDoxTO.getIsCoMailOnly(), CommonConstants.YES)) {
				LOGGER.debug("Manifest contains comails only");
				int maxRows = maxCNsAllowed + maxComailsAllowed;
				if (noOfElements == maxRows) {
					manifestStatus = OutManifestConstants.CLOSE;
					LOGGER.debug("No Of Elements :: "+noOfElements+" :: Max Rows allowed by Manifest :: "+maxRows +" manifest Status :: "+manifestStatus);
				}
			} else if (noOfElements == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
				LOGGER.debug("No Of Elements :: "+noOfElements+" :: Max Rows allowed by Manifest :: "+maxCNsAllowed +" manifest Status :: "+manifestStatus);
			}
		}
		LOGGER.debug("OutManifestDoxConverter :: determineManifestStatus() :: END------------>:::::::");
		return manifestStatus;
	}

}
