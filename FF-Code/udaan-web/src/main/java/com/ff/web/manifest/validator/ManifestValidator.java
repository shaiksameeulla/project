/*
 * 
 */
package com.ff.web.manifest.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPaymentTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.manifest.ConsignmentReturnDO;
import com.ff.domain.manifest.ConsignmentReturnReasonDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.web.booking.validator.BookingValidator;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class ManifestValidator.
 */
public class ManifestValidator {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestValidator.class);

	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The stock universal service. */
	private StockUniversalService stockUniversalService;

	/** The geographyCommonService */
	private GeographyCommonService geographyCommonService;

	/** The organizationCommonService */
	private OrganizationCommonService organizationCommonService;

	/** The serviceOfferingCommonService */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The manifestUniversalService */
	private ManifestUniversalService manifestUniversalService;

	private RthRtoManifestCommonService rthRtoManifestCommonService;

	/** The delivery universal service. */
	private DeliveryUniversalService deliveryUniversalService;

	/** The routeServicedCommonService. */
	private RouteServicedCommonService routeServicedCommonService;

	/**
	 * @param routeServicedCommonService
	 *            the routeServicedCommonService to set
	 */
	public void setRouteServicedCommonService(
			RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}

	/**
	 * @param deliveryUniversalService
	 *            the deliveryUniversalService to set
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * @param manifestUniversalService
	 *            the manifestUniversalService to set
	 */
	public void setManifestUniversalService(
			ManifestUniversalService manifestUniversalService) {
		this.manifestUniversalService = manifestUniversalService;
	}

	/**
	 * Sets the out manifest common service.
	 * 
	 * @param outManifestCommonService
	 *            the new out manifest common service
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * Sets the booking validator.
	 * 
	 * @param bookingValidator
	 *            the new booking validator
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
	}

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the new out manifest universal service
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * Sets the stock universal service.
	 * 
	 * @param stockUniversalService
	 *            the new stock universal service
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/**
	 * @param rthRtoManifestCommonService
	 *            the rthRtoManifestCommonService to set
	 */
	public void setRthRtoManifestCommonService(
			RthRtoManifestCommonService rthRtoManifestCommonService) {
		this.rthRtoManifestCommonService = rthRtoManifestCommonService;
	}

	/**
	 * Checks if is valid consignment.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestValidate isValidConsignment(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		BookingDO bookingDO = null;

		boolean isConsInManifestd = Boolean.FALSE;
		boolean isConsignmentPickedUp = Boolean.FALSE;
		boolean isBookedInOperatingOffice = Boolean.FALSE;

		/** Check Stock cancellation */
		manifestUniversalService.isStockCancel(manifestValidateTO
				.getConsgNumber());
		/** Check is it child CN */
		Integer isChildCn = outManifestUniversalService
				.getChildConsgIdByConsgNo(manifestValidateTO.getConsgNumber());
		if (!StringUtil.isNull(isChildCn)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CONSIGNMENT_IS_CHILD_CN);
		}
		// Consignment created through Booking
		ConsignmentTO consignmentTO = outManifestUniversalService
				.getConsingmentDtls(manifestValidateTO.getConsgNumber());

		// If the consignment is not already created
		if (StringUtil.isNull(consignmentTO)) {

			bookingDO = getBookingForConsignment(manifestValidateTO
					.getConsgNumber());

			/*
			 * Setting the attribute to indicate that the consignment is a
			 * picked up consignment
			 */
			// Initialized with NO as Pickup
			manifestValidateTO.setIsCNProcessedFromPickup(CommonConstants.NO);

			if (!StringUtil.isNull(bookingDO)) {
				if (!StringUtil.isNull(bookingDO.getUpdatedProcess())
						&& StringUtils.equalsIgnoreCase(bookingDO
								.getUpdatedProcess().getProcessCode(),
								CommonConstants.PROCESS_PICKUP))
					manifestValidateTO
							.setIsCNProcessedFromPickup(CommonConstants.YES);
				isConsignmentPickedUp = Boolean.TRUE;
				
				// added by somya
				if (bookingDO.getCnStatus().equalsIgnoreCase("I")) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.CONSIGNMENT_NOT_IN_PICKUP);
				}
			} else {
				manifestValidateTO.setIsConsgExists(CommonConstants.NO);
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSG_NOT_FOUND);
			}

		} else {
			if (consignmentTO.getConsgStatus().equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_STATUS_STOPDELV)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSIGNMENT_STOPDELV);
			}

			boolean notNull = (!StringUtil.isEmptyInteger(consignmentTO
					.getOrgOffId())
					&& !StringUtil.isNull(manifestValidateTO.getOriginOffice()) && !StringUtil
					.isEmptyInteger(manifestValidateTO.getOriginOffice()
							.getOfficeId()));
			if (notNull
					&& consignmentTO.getOrgOffId().intValue() == manifestValidateTO
							.getOriginOffice().getOfficeId().intValue()) {
				isBookedInOperatingOffice = Boolean.TRUE;
			}
			manifestValidateTO.setIsCNBooked(CommonConstants.YES);
			if ((consignmentTO.getIsBulkBookedCN()
					.equalsIgnoreCase(CommonConstants.YES))
					&& (consignmentTO.getOrgOffId().intValue() == manifestValidateTO
							.getOriginOffice().getOfficeId().intValue())) {
				manifestValidateTO.setIsBulkBookedCN(CommonConstants.YES);
				manifestValidateTO.setConsgTO(consignmentTO);
			}

			outManifestCommonService.validateConsgnNoOutManifested(manifestValidateTO);
			String isCnManifested = manifestValidateTO.getIsConsInManifestd();
			if (!isBookedInOperatingOffice) {// check if consignmnt is
												// in-manifested
				/*isConsInManifestd = outManifestCommonService
						.isConsgnNoInManifested(manifestValidateTO);*/
				if (StringUtils.isNotEmpty(isCnManifested) && isCnManifested.equalsIgnoreCase(CommonConstants.YES))
					isConsInManifestd = true;
				else
					isConsInManifestd = false;
			}
			
			// discussed with saumya and uncommented for defect raised by
			// shahnaz: consg not booked from this office not excepted
			if (!isBookedInOperatingOffice && !isConsInManifestd) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_NOTBOOKED_NOR_IN_MANIFESTED);
			}


         DeliveryDetailsTO delvdetlTO = null;
         if (manifestValidateTO.getManifestProcessCode().equalsIgnoreCase(
                     OutManifestConstants.PROCESS_CODE_TPBP)) {
               // get the deliveryDetlTO
               delvdetlTO = deliveryUniversalService
                           .getDrsDtlsByConsgNo(manifestValidateTO
                                       .getConsgNumber());
         }

         if (StringUtils.isNotEmpty(isCnManifested) && isCnManifested.equalsIgnoreCase(CommonConstants.YES)) {
               if (!StringUtil.isNull(delvdetlTO)
                           && !StringUtil.isNull(delvdetlTO.getDeliveryStatus())) {
                     if (!delvdetlTO.getDeliveryStatus().equalsIgnoreCase("P")) {
                           manifestValidateTO
                                       .setIsCnManifested(CommonConstants.YES);
                           throw new CGBusinessException(
                                       ManifestErrorCodesConstants.CONSGNMENT_MANIFESTED);
                     }
               } 
         }


			// validation3: Pin code is not serviced by destination.

			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& consignmentTO.getTypeTO().getConsignmentCode()
							.equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
				manifestValidateTO.setIsConsParcel(CommonConstants.YES);
			}
			if (StringUtils.equalsIgnoreCase(
					manifestValidateTO.getIsPincodeServChkReq(),
					CommonConstants.YES)) {
				/*
				 * ConsignmentTO consignmentTO = outManifestUniversalService
				 * .getConsingmentDtls(manifestValidateTO.getConsgNumber());
				 */
				if (!StringUtil.isNull(consignmentTO.getDestPincode())) {
					manifestValidateTO.setCnPincodeTO(consignmentTO
							.getDestPincode());
					manifestValidateTO.setConsgTO(consignmentTO);
					isValidPincode(manifestValidateTO);
				}
			}
			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& !StringUtil.isNull(manifestValidateTO
							.getConsignmentTypeTO())
					&& !StringUtils.equalsIgnoreCase(consignmentTO.getTypeTO()
							.getConsignmentCode(), manifestValidateTO
							.getConsignmentTypeTO().getConsignmentCode())) {
				if (StringUtils.equalsIgnoreCase(manifestValidateTO
						.getConsignmentTypeTO().getConsignmentCode(),
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_DOX_TYPE_CONSG);
				else
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
			}
			if (!StringUtils.isEmpty(consignmentTO.getChildCNsDtls())) {
				manifestValidateTO.setChildCNDetails(consignmentTO
						.getChildCNsDtls());
			}
			manifestValidateTO.setConsgTO(consignmentTO);
		}

		/* To convert bookingDO to consignmentModificationTO */
		manifestValidateTO
				.setConsignmentModificationTO(bookingTransObjConverter(
						bookingDO, consignmentTO, isConsignmentPickedUp));

		// validation4: Allowed Product series validation.
		if (!StringUtil.isNull(manifestValidateTO.getManifestProductMapTO())) {
			List<Object[]> mnfstProdMap = outManifestCommonService
					.getManifestProductMapDtls(manifestValidateTO
							.getManifestProductMapTO());
			if (!StringUtil.isEmptyList(mnfstProdMap)) {
				for (Object[] prodManifest : mnfstProdMap) {
					String allowManifest = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isNull(prodManifest[1]))
						allowManifest = prodManifest[1].toString();
					if (StringUtils.equalsIgnoreCase(allowManifest,
							CommonConstants.NO)) {
						manifestValidateTO
								.setIsAllowedProduct(CommonConstants.NO);
						throw new CGBusinessException(
								ManifestErrorCodesConstants.PRODUCT_NOT_ALLOWED);
					}
				}
			}
		}

		// validation 5: Allow party shifted reason consignments
		if (!StringUtil.isNull(consignmentTO)) {
			boolean isCNPartyShifted = outManifestUniversalService
					.isPartyShiftedConsg(consignmentTO.getConsgId());
			if (isCNPartyShifted)
				manifestValidateTO.setIsCnPartyShifted(CommonConstants.YES);
		}
		// Set Process Details
		if (!StringUtil.isNull(consignmentTO)) {
			manifestValidateTO.setUpdatedProcessFrom(consignmentTO
					.getUpdatedProcessFrom());
		}
		if (!StringUtil.isNull(bookingDO)) {
			ProcessTO processTO = null;
			if (!StringUtil.isNull(bookingDO.getUpdatedProcess())) {
				try {
					processTO = new ProcessTO();
					PropertyUtils.copyProperties(processTO,
							bookingDO.getUpdatedProcess());
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					throw new CGBusinessException("PPX0008");
				}
			}
			manifestValidateTO.setUpdatedProcessFrom(processTO);
		}

		return manifestValidateTO;
	}

	/**
	 * To check whether misroute consignment required for that process or not
	 * 
	 * @param processCodes
	 * @param manifestProcessCode
	 * @return boolean
	 */
	private boolean isMisrouteCheckRequired(String[] processCodes,
			String manifestProcessCode) {
		boolean result = Boolean.FALSE;
		if (!StringUtil.isStringEmpty(manifestProcessCode)) {
			for (String processCode : processCodes) {
				if (processCode.equalsIgnoreCase(manifestProcessCode)) {
					result = Boolean.TRUE;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * To get misroute consignment not allowed processes
	 * 
	 * @return String array
	 */
	private String[] getMisrouteConsgNotAllowProcesses() {
		String[] processCodes = { CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX,
				CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL };
		return processCodes;
	}

	/**
	 * Checks if is valid pincode.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestValidate isValidPincode(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		boolean isValidPincode = Boolean.FALSE;
		String destOfficeType = null;
		// NOTE: for BPL for Parcel kind od screens office type will be
		// available on the screen so referring from request.
		if (!StringUtil.isNull(manifestValidateTO.getDestOffice())
				&& !StringUtil.isNull(manifestValidateTO.getDestOffice()
						.getOfficeTypeTO()))
			destOfficeType = manifestValidateTO.getDestOffice()
					.getOfficeTypeTO().getOffcTypeCode();
		// NOTE: incase of 'ALL office office id is getting zero. so throwing
		// null pointer exception.'
		if (StringUtil.isNull(destOfficeType)
				&& !StringUtil.isNull(manifestValidateTO.getDestOffice())
				&& !StringUtil.isEmptyInteger(manifestValidateTO
						.getDestOffice().getOfficeId())) {
			OfficeTO offTO = outManifestCommonService
					.getOfficeDetails(manifestValidateTO.getDestOffice()
							.getOfficeId());
			destOfficeType = offTO.getOfficeTypeTO().getOffcTypeCode();
		}

		if (!StringUtil.isNull(manifestValidateTO.getDestOffice())
				|| !StringUtil.isNull(manifestValidateTO.getCnPincodeTO())) {
			if (StringUtils.equalsIgnoreCase(
					manifestValidateTO.getChkTransCityPincodeServ(),
					CommonConstants.NO)
					&& !StringUtil.isNull(manifestValidateTO.getDestOffice())
					&& manifestValidateTO.getDestOffice().getOfficeId() != CommonConstants.ZERO
					&& StringUtils.isNotEmpty(destOfficeType)
					&& !destOfficeType
							.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				isValidPincode = outManifestCommonService
						.isValidPincodeByBranch(manifestValidateTO);
			} else {
				isValidPincode = outManifestCommonService
						.isValidPincodeByCity(manifestValidateTO);
				/**validate branch pincode servicability for all reporting offices to selected hub */ 
				if (!isValidPincode) {
					PincodeServicabilityTO pincodeServicabilityTO = new PincodeServicabilityTO();
					pincodeServicabilityTO.setPincodeId(manifestValidateTO
							.getCnPincodeTO().getPincodeId());
					if (!StringUtil.isNull(manifestValidateTO.getDestOffice()) && !StringUtil.isEmptyInteger(manifestValidateTO
								.getDestOffice().getOfficeId())) {
						pincodeServicabilityTO.setOfficeId(manifestValidateTO
								.getDestOffice().getOfficeId());
					} else if (!StringUtil.isNull(manifestValidateTO
							.getDestCityTO())&& !StringUtil.isEmptyInteger(manifestValidateTO
									.getDestCityTO().getCityId())) {
						pincodeServicabilityTO.setCityId(manifestValidateTO
								.getDestCityTO().getCityId());
					}
					isValidPincode = organizationCommonService
							.validateBranchPincodeServiceabilityForHubOffice(pincodeServicabilityTO);
				}
			}
		}
		if (!isValidPincode) {
			if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
					manifestValidateTO.getIsCNProcessedFromPickup())
					&& manifestValidateTO.getManifestProcessCode()
							.equalsIgnoreCase(
									OutManifestConstants.PROCESS_CODE_OPKT)
					&& StringUtils.equalsIgnoreCase(
							manifestValidateTO.getChkTransCityPincodeServ(),
							CommonConstants.YES)) {
				// Added by Himal
				// Pickup consignment validation
				// throw new CGBusinessException(
				// ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
				validateTranshimentCity(manifestValidateTO);
			} else if (manifestValidateTO.getManifestProcessCode()
					.equalsIgnoreCase(OutManifestConstants.PROCESS_CODE_TPDX)
					|| manifestValidateTO.getManifestProcessCode()
							.equalsIgnoreCase(
									OutManifestConstants.PROCESS_CODE_TPBP)
					|| (manifestValidateTO.getManifestProcessCode()
							.equalsIgnoreCase(
									OutManifestConstants.PROCESS_CODE_OPKT) && StringUtils
							.equalsIgnoreCase(manifestValidateTO
									.getChkTransCityPincodeServ(),
									CommonConstants.YES))) {
				validateTranshimentCity(manifestValidateTO);
			} else {
				manifestValidateTO.setIsPincodeServiceable(CommonConstants.NO);
				// Allow party shifted reason consignments
				ConsignmentTO consignmentTO = new ConsignmentTO();
				consignmentTO.setConsgNo(manifestValidateTO.getConsgNumber());
				Integer consgId = outManifestUniversalService
						.getConsignmentIdByConsgNo(consignmentTO);
				if (!StringUtil.isNull(consgId)) {
					boolean isCNPartyShifted = outManifestUniversalService
							.isPartyShiftedConsg(consgId);
					if (isCNPartyShifted) {
						manifestValidateTO
								.setIsCnPartyShifted(CommonConstants.NO);
						throw new CGBusinessException(
								ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
					}
				} else {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
				}
			}
		}
		return manifestValidateTO;
	}

	/**
	 * To validate transhipment city for Third party and OGM - Out Manifest DOX
	 * 
	 * @param manifestValidateTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public void validateTranshimentCity(OutManifestValidate manifestValidateTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("ManifestValidator :: validateTranshimentCity() :: START");
		/*
		 * To check if the pincode can be serviced by third party and OGM
		 * locations,then allow - transhipment
		 */
		TransshipmentRouteTO transshipmentRouteTO = new TransshipmentRouteTO();
		// set the logged in city
		transshipmentRouteTO.setTransshipmentCityId(manifestValidateTO
				.getDestCityTO().getCityId());
		// set the cons dest. city
		transshipmentRouteTO.setServicedCityId(manifestValidateTO.getConsgTO()
				.getDestPincode().getCityId());
		TransshipmentRouteTO transshipmentRouteTO1 = routeServicedCommonService
				.getTransshipmentRoute(transshipmentRouteTO);
		if (StringUtil.isNull(transshipmentRouteTO1)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.PIN_NOT_SERVICE_DEST);
		}
		LOGGER.trace("ManifestValidator :: validateTranshimentCity() :: END");
	}

	// Pin code not serviced by FFCL
	/**
	 * Ffcl validation for pincode.
	 * 
	 * @param manifestValidationTO
	 *            the manifest validation to
	 * @return the out manifest validate
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public OutManifestValidate ffclValidationForPincode(
			OutManifestValidate manifestValidationTO) throws CGSystemException,
			CGBusinessException {
		if (!StringUtil.isNull(manifestValidationTO.getCnPincodeTO())) {
			BookingValidationTO bookingvalidateTO = new BookingValidationTO();
			bookingvalidateTO.setConsgSeries(manifestValidationTO
					.getConsignmentSeries());
			bookingvalidateTO.setPincode(manifestValidationTO.getCnPincodeTO()
					.getPincode());
			bookingvalidateTO.setPincodeId(manifestValidationTO
					.getCnPincodeTO().getPincodeId());
			bookingvalidateTO.setCityId(manifestValidationTO.getDestCityTO()
					.getCityId());
			bookingvalidateTO = bookingValidator
					.isValidPincode(bookingvalidateTO);
			if (!StringUtils.equalsIgnoreCase(
					bookingvalidateTO.getIsValidPincode(), CommonConstants.YES)) {
				manifestValidationTO.setIsValidPincode(bookingvalidateTO
						.getIsValidPincode());
				throw new CGBusinessException(
						ManifestErrorCodesConstants.NOT_VALID_PINCODE);
			} else if (!StringUtil.isNull(bookingvalidateTO)) {
				CityTO destCityTO = new CityTO();
				destCityTO.setCityId(bookingvalidateTO.getCityId());
				destCityTO.setCityName(bookingvalidateTO.getCityName());
				manifestValidationTO.setCnDestCityTO(destCityTO);

				PincodeTO pincodeTO = new PincodeTO();
				pincodeTO.setPincode(manifestValidationTO.getCnPincodeTO()
						.getPincode());
				pincodeTO.setPincodeId(bookingvalidateTO.getPincodeId());
				manifestValidationTO.setCnPincodeTO(pincodeTO);
			}
		}
		return manifestValidationTO;
	}

	/**
	 * Stock issue validation.
	 * 
	 * @param stockValiationTO
	 *            the stock validation to
	 * @return the manifest stock issue inputs
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ManifestStockIssueInputs stockIssueValidation(
			ManifestStockIssueInputs stockValiationTO)
			throws CGBusinessException, CGSystemException {
		
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("ManifestValidator :: stockIssueValidation :: Start ::");
		logger.append(" StartTime :: " + startTimeInMilis);
		LOGGER.debug(logger.toString());
		
		if (StringUtils.equalsIgnoreCase(stockValiationTO.getSeriesType(),
				UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS)
				|| StringUtils.equalsIgnoreCase(
						stockValiationTO.getSeriesType(),
						UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS)
				|| StringUtils.equalsIgnoreCase(
						stockValiationTO.getSeriesType(),
						UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO)
				|| StringUtils.equalsIgnoreCase(
						stockValiationTO.getSeriesType(),
						UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS)
				|| StringUtils.equalsIgnoreCase(
						stockValiationTO.getSeriesType(),
						UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO)) {
			StockIssueValidationTO stockIssueValidationTO = new StockIssueValidationTO();
			stockIssueValidationTO.setStockItemNumber(stockValiationTO
					.getStockItemNumber());
			stockIssueValidationTO.setSeriesType(stockValiationTO
					.getSeriesType());
			stockIssueValidationTO
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockIssueValidationTO.setIssuedTOPartyId(stockValiationTO
					.getIssuedTOPartyId());
			stockIssueValidationTO.setRegionCode(stockValiationTO
					.getRegionCode());
			stockIssueValidationTO.setCityCode(stockValiationTO
					.getLoginCityCode());
			stockIssueValidationTO.setOfficeCode(stockValiationTO
					.getOfficeCode());

			stockIssueValidationTO = stockUniversalService
					.validateStock(stockIssueValidationTO);
			if (!stockIssueValidationTO.getIsIssuedTOParty()) {
				stockValiationTO.setIsIssuedTOParty(CommonConstants.NO);
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_NOT_ISSUED_REGION);
			}
		} else {
			stockValiationTO.setIsManifested(CommonConstants.YES);
			throw new CGBusinessException(
					ManifestErrorCodesConstants.IS_MANIFESTED);
		}
		
		long endTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("ManifestValidator :: stockIssueValidation :: End"
				+ " End Time ::"
				+ endTimeInMilis
				+ " Time Difference in miliseconds:: "+(endTimeInMilis-startTimeInMilis)
				+ " Time Difference in HH:MM:SS :: "
				+ DateUtil
						.convertMilliSecondsTOHHMMSSStringFormat(endTimeInMilis
								- startTimeInMilis));
		return stockValiationTO;
	}

	/**
	 * To convert BookingDO and ConsignmentTO to ConsignmentModificationTO
	 * 
	 * @param booking
	 * @param consg
	 * @param pickedUpConsignment
	 * @return bookingTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ConsignmentModificationTO bookingTransObjConverter(
			BookingDO booking, ConsignmentTO consg, boolean pickedUpConsignment)
			throws CGBusinessException, CGSystemException {
		ConsignmentModificationTO bookingTO = new ConsignmentModificationTO();

		if (!pickedUpConsignment) {
			/** Consignment */
			bookingTO.setConsigmentTO(consg);
			bookingTO.setCnPricingDtls(consg.getConsgPriceDtls());
			if (consg.getActualWeight() != null && consg.getActualWeight() > 0)
				bookingTO.setActualWeight(consg.getActualWeight());
			if (consg.getFinalWeight() != null && consg.getFinalWeight() > 0)
				bookingTO.setFinalWeight(consg.getFinalWeight());
			if (consg.getVolWeight() != null && consg.getVolWeight() > 0)
				bookingTO.setVolWeight(consg.getVolWeight());
			bookingTO.setPrice(consg.getPrice());
			if (consg.getHeight() != null && consg.getHeight() > 0)
				bookingTO.setHeight(consg.getHeight());
			if (consg.getLength() != null && consg.getLength() > 0)
				bookingTO.setLength(consg.getLength());
			if (consg.getBreath() != null && consg.getBreath() > 0)
				bookingTO.setBreath(consg.getBreath());
			bookingTO.setPincodeId(consg.getDestPincode().getPincodeId());
			bookingTO.setPincode(consg.getDestPincode().getPincode());
			CityTO city = geographyCommonService.getCity(consg.getDestPincode()
					.getPincode());
			if (city != null) {
				bookingTO.setCityId(city.getCityId());
				bookingTO.setCityName(city.getCityName());
			}
			bookingTO.setOtherCNContent(consg.getOtherCNContent());
			bookingTO.setPaperWorkRefNo(consg.getPaperWorkRefNo());
			bookingTO.setInsurencePolicyNo(consg.getInsurencePolicyNo());
			// Setting child entities
			if (!StringUtil.isNull(consg.getCnContents())) {
				bookingTO.setCnContents(consg.getCnContents());
			}
			if (!StringUtil.isNull(consg.getCnPaperWorks())) {
				bookingTO.setCnPaperWorks(consg.getCnPaperWorks());
			}
			if (!StringUtil.isNull(consg.getConsigneeTO())) {
				bookingTO.setConsignee(consg.getConsigneeTO());
			}
			if (!StringUtil.isNull(consg.getConsignorTO())) {
				bookingTO.setConsignor(consg.getConsignorTO());
			}
			// Setting insuredBy
			if (!StringUtil.isNull(consg.getInsuredByTO())) {
				bookingTO.setInsuredBy(consg.getInsuredByTO());
			}
		} else {

			/** Booking */
			bookingTO.setBookingId(booking.getBookingId());
			/*
			 * String bookingTime = DateUtil.extractTimeFromDate(booking
			 * .getBookingDate());
			 */
			String bookingDate = DateUtil.getDDMMYYYYDateToString(booking
					.getBookingDate());
			bookingTO.setBookingDate(bookingDate);
			bookingTO.setConsgNumber(booking.getConsgNumber());
			bookingTO.setBookingOfficeId(booking.getBookingOfficeId());
			bookingTO.setNoOfPieces(bookingTO.getNoOfPieces());
			bookingTO.setRefNo(booking.getRefNo());
			if (!StringUtil.isNull(booking.getConsgTypeId())) {
				if (!StringUtil.isEmptyInteger(booking.getConsgTypeId()
						.getConsignmentId())) {
					bookingTO.setConsgTypeId(booking.getConsgTypeId()
							.getConsignmentId());
				}
				if (!StringUtil.isStringEmpty(booking.getConsgTypeId()
						.getConsignmentName())) {
					bookingTO.setConsgTypeName(booking.getConsgTypeId()
							.getConsignmentName());
					if (StringUtils.equalsIgnoreCase(
							CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, booking
									.getConsgTypeId().getConsignmentName())) {
						bookingTO.setActualWeight(booking.getFianlWeight());
					}
				}
			}
			bookingTO.setCnStatus(booking.getCnStatus());
			if (booking.getTrnaspmentChg() != null
					&& booking.getTrnaspmentChg() > 0)
				bookingTO.setTrnaspmentChg(booking.getTrnaspmentChg());
			if (!StringUtil.isEmptyInteger(booking.getApprovedBy())) {
				EmployeeTO employee = organizationCommonService
						.getEmployeeDetails(booking.getApprovedBy());
				bookingTO.setApprovedById(booking.getApprovedBy());
				bookingTO.setApprovedBy(employee.getFirstName());
			}
			bookingTO.setWeightCapturedMode(booking.getWeightCapturedMode());
			if (!StringUtil.isNull(booking.getBookingPayment())) {
				BookingPaymentTO bookingPayment = new BookingPaymentTO();
				CGObjectConverter.createToFromDomain(
						booking.getBookingPayment(), bookingPayment);
				bookingTO.setBookingPayment(bookingPayment);
				Integer paymentID = booking.getBookingPayment()
						.getPaymentModeId();
				PaymentModeTO paymentModeTO = serviceOfferingCommonService
						.getPaymentDetails(paymentID);
				bookingTO.setPaymentMode(paymentModeTO.getPaymentType());
			}
			/* Setting booking type */
			if (!StringUtil.isNull(booking.getBookingType())) {
				bookingTO.setBookingTypeId(booking.getBookingType()
						.getBookingTypeId());
				bookingTO.setBookingType(booking.getBookingType()
						.getBookingType());
			}
			/* Setting runsheet no. */
			if (!StringUtil.isStringEmpty(booking.getPickRunsheetNo())) {
				bookingTO.setRunsheetNo(booking.getPickRunsheetNo());
			}
			/* Customer Id */
			if (!StringUtil.isNull(booking.getCustomerId())) {
				CustomerTO customerTO = new CustomerTO();
				customerTO.setCustomerId(booking.getCustomerId()
						.getCustomerId());
				bookingTO.setCustomer(customerTO);
			}
			// setting consignment status
			boolean isConsgClosed = manifestUniversalService
					.isConsignmentClosed(booking.getConsgNumber(),
							ManifestUniversalConstants.MANIFEST_DIRECTION_OUT,
							ManifestUniversalConstants.MANIFEST_STATUS_CLOSED);
			if (isConsgClosed)
				bookingTO.setIsConsgClosed("Y");
		}
		return bookingTO;
	}

	/**
	 * To get bookingDO for consignment
	 * 
	 * @param consgNo
	 * @return bookingDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private BookingDO getBookingForConsignment(String consgNo)
			throws CGBusinessException, CGSystemException {
		// Consignment created through Update Pickup Runsheet
		BookingDO bookingDO = null;
		List<String> consgNos = new ArrayList<>();

		/* Always one consignment is passed as a parameter */
		consgNos.add(consgNo);
		List<BookingDO> bookingDOs = outManifestUniversalService.getBookings(
				consgNos, "");

		if (!StringUtil.isEmptyList(bookingDOs)) {
			bookingDO = bookingDOs.get(0);
		}
		return bookingDO;
	}

	/**
	 * Checks if is valid consignment.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestValidate isValidConsignmentForMisroute(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		BookingDO bookingDO = null;

		boolean isConsInManifestd = Boolean.FALSE;
		boolean isConsignmentPickedUp = Boolean.FALSE;
		boolean isBookedInOperatingOffice = Boolean.FALSE;

		// Consignment created through Booking
		ConsignmentTO consignmentTO = outManifestUniversalService
				.getConsingmentDtls(manifestValidateTO.getConsgNumber());

		// If the consignment is not already created
		if (StringUtil.isNull(consignmentTO)) {

			bookingDO = getBookingForConsignment(manifestValidateTO
					.getConsgNumber());

			/*
			 * Setting the attribute to indicate that the consignment is a
			 * picked up consignment
			 */
			// Initialized with NO as Pickup
			manifestValidateTO.setIsCNProcessedFromPickup(CommonConstants.NO);

			if (!StringUtil.isNull(bookingDO)) {
				if (!StringUtil.isNull(bookingDO.getUpdatedProcess())
						&& StringUtils.equalsIgnoreCase(bookingDO
								.getUpdatedProcess().getProcessCode(),
								CommonConstants.PROCESS_PICKUP))
					manifestValidateTO
							.setIsCNProcessedFromPickup(CommonConstants.YES);
				isConsignmentPickedUp = Boolean.TRUE;
				boolean notNull = (!StringUtil.isEmptyInteger(bookingDO
						.getOriginOfficeId())
						&& !StringUtil.isNull(manifestValidateTO
								.getOriginOffice()) && !StringUtil
						.isEmptyInteger(manifestValidateTO.getOriginOffice()
								.getOfficeId()));
				if (notNull
						&& bookingDO.getOriginOfficeId().intValue() == manifestValidateTO
								.getOriginOffice().getOfficeId().intValue()) {
					isBookedInOperatingOffice = Boolean.TRUE;
				}
				/**
				 * If the Consignment is not booked in operating office and also
				 * not In-manifested in the Office throw exception
				 * CN_NOTBOOKED_NOR_IN_MANIFESTED
				 */
				if (!isBookedInOperatingOffice) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.CN_NOTBOOKED_NOR_IN_MANIFESTED);
				}

			} else {
				manifestValidateTO.setIsConsgExists(CommonConstants.NO);
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSG_NOT_FOUND);
			}
		} else {
			/*
			 * Check whether the consignment has been booked in the origin
			 * branch or not.
			 */
			
			if (consignmentTO.getConsgStatus().equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_STATUS_STOPDELV)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSIGNMENT_STOPDELV);
			}

			boolean notNull = (!StringUtil.isEmptyInteger(consignmentTO
					.getOrgOffId())
					&& !StringUtil.isNull(manifestValidateTO.getOriginOffice()) && !StringUtil
					.isEmptyInteger(manifestValidateTO.getOriginOffice()
							.getOfficeId()));
			if (notNull
					&& consignmentTO.getOrgOffId().intValue() == manifestValidateTO
							.getOriginOffice().getOfficeId().intValue()
							&& consignmentTO.getIsExcessConsg().equals(CommonConstants.NO)) {
				isBookedInOperatingOffice = Boolean.TRUE;
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSIGNMENT_CANNOT_BE_MISROUTED);

			}
			manifestValidateTO.setIsCNBooked(CommonConstants.YES);
			/*
			 * If consignment is booked in the logged in branch then there is no
			 * need to check whether the consignment is IN-MANIFESTED in this
			 * branch or not.
			 * 
			 * This In-Manifest check will be done only for the consignments
			 * which are not booked in this branch
			 */
			
			outManifestCommonService.validateConsgnNoOutManifested(manifestValidateTO);
			String isCnInManifested= manifestValidateTO.getIsConsInManifestd();
			boolean isCnManifested = false;
			if (StringUtils.isNotEmpty(isCnInManifested) && isCnInManifested.equalsIgnoreCase(CommonConstants.YES))
				isCnManifested = true;
			else
				isCnManifested = false;
			
			if (!isBookedInOperatingOffice) {// check if consignmnt is
												// in-manifested
				/*isConsInManifestd = outManifestCommonService
						.isConsgnNoInManifested(manifestValidateTO);*/
				if (isCnManifested)
					isConsInManifestd = true;
				else
					isConsInManifestd = false;
			}

			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& consignmentTO.getTypeTO().getConsignmentCode()
							.equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
				manifestValidateTO.setIsConsParcel(CommonConstants.YES);
			}

			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& !StringUtil.isNull(manifestValidateTO
							.getConsignmentTypeTO())
					&& !StringUtils.equalsIgnoreCase(consignmentTO.getTypeTO()
							.getConsignmentCode(), manifestValidateTO
							.getConsignmentTypeTO().getConsignmentCode())) {
				if (StringUtils.equalsIgnoreCase(manifestValidateTO
						.getConsignmentTypeTO().getConsignmentCode(),
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_DOX_TYPE_CONSG);
				else
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
			}
			manifestValidateTO.setConsgTO(consignmentTO);
		}

		/* To convert bookingDO to consignmentModificationTO */
		manifestValidateTO
				.setConsignmentModificationTO(bookingTransObjConverter(
						bookingDO, consignmentTO, isConsignmentPickedUp));

		return manifestValidateTO;
	}

	public OutManifestValidate isValidConsignmentForBranchManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		BookingDO bookingDO = null;

		boolean isConsInManifestd = Boolean.FALSE;
		boolean isConsignmentPickedUp = Boolean.FALSE;
		boolean isBookedInOperatingOffice = Boolean.FALSE;

		// Consignment created through Booking
		ConsignmentTO consignmentTO = outManifestUniversalService
				.getConsingmentDtls(manifestValidateTO.getConsgNumber());

		// If the consignment is not already created
		if (StringUtil.isNull(consignmentTO)) {

			bookingDO = getBookingForConsignment(manifestValidateTO
					.getConsgNumber());

			/*
			 * Setting the attribute to indicate that the consignment is a
			 * picked up consignment
			 */
			// Initialized with NO as Pickup
			manifestValidateTO.setIsCNProcessedFromPickup(CommonConstants.NO);

			if (!StringUtil.isNull(bookingDO)) {
				if (!StringUtil.isNull(bookingDO.getUpdatedProcess())
						&& StringUtils.equalsIgnoreCase(bookingDO
								.getUpdatedProcess().getProcessCode(),
								CommonConstants.PROCESS_PICKUP))
					manifestValidateTO
							.setIsCNProcessedFromPickup(CommonConstants.YES);
				isConsignmentPickedUp = Boolean.TRUE;
				// added by somya
				if (bookingDO.getCnStatus().equalsIgnoreCase("I")) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.CONSIGNMENT_NOT_IN_PICKUP);
				}
			} else {
				manifestValidateTO.setIsConsgExists(CommonConstants.NO);
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSG_NOT_FOUND);
			}

		} else {
			/*
			 * Check whether the consignment has been booked in the origin
			 * branch or not.
			 */

			if (!StringUtil.isNull(consignmentTO.getConsgStatus())
					&& consignmentTO.getConsgStatus().equalsIgnoreCase(
							CommonConstants.CONSIGNMENT_STATUS_STOPDELV)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CONSIGNMENT_STOPDELV);
			}

			boolean notNull = (!StringUtil.isEmptyInteger(consignmentTO
					.getOrgOffId())
					&& !StringUtil.isNull(manifestValidateTO.getOriginOffice()) && !StringUtil
					.isEmptyInteger(manifestValidateTO.getOriginOffice()
							.getOfficeId()));
			if (notNull
					&& consignmentTO.getOrgOffId().intValue() == manifestValidateTO
							.getOriginOffice().getOfficeId().intValue()) {
				isBookedInOperatingOffice = Boolean.TRUE;
			}
			manifestValidateTO.setIsCNBooked(CommonConstants.YES);
			/*
			 * If consignment is booked in the logged in branch then there is no
			 * need to check whether the consignment is IN-MANIFESTED in this
			 * branch or not.
			 * 
			 * This In-Manifest check will be done only for the consignments
			 * which are not booked in this branch
			 */
			
			outManifestCommonService.validateConsgnNoOutManifested(manifestValidateTO);
			String isCnManifested = manifestValidateTO.getIsConsInManifestd();
			if (!isBookedInOperatingOffice) {// check if consignmnt is
												// in-manifested
				/*isConsInManifestd = outManifestCommonService
						.isConsgnNoInManifested(manifestValidateTO);*/
				if (StringUtils.isNotEmpty(isCnManifested) && isCnManifested.equalsIgnoreCase(CommonConstants.YES))
					isConsInManifestd = true;
				else
					isConsInManifestd = false;
			}
			/**
			 * If the Consignment is not booked in operating office and also not
			 * In-manifested in the Office throw exception
			 * CN_NOTBOOKED_NOR_IN_MANIFESTED
			 */
			// discussed with saumya and uncommented for defect raised by
			// shahnaz: consg not booked from this office not excepted
			if (!isBookedInOperatingOffice && !isConsInManifestd) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_NOTBOOKED_NOR_IN_MANIFESTED);
			}

			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& consignmentTO.getTypeTO().getConsignmentCode()
							.equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
				manifestValidateTO.setIsConsParcel(CommonConstants.YES);
			}

			if (consignmentTO
					.getUpdatedProcessFrom()
					.getProcessId()
					.equals(Integer
							.parseInt(OutManifestConstants.PROCESS_ID_FOR_RTO))
					&& consignmentTO.getOrgOffId().equals(
							manifestValidateTO.getDestOffice().getOfficeId())) {
				// allow it without checking the destPincode

			} else {

				if (StringUtils.equalsIgnoreCase(
						manifestValidateTO.getIsPincodeServChkReq(),
						CommonConstants.YES)) {
					/*
					 * ConsignmentTO consignmentTO = outManifestUniversalService
					 * .getConsingmentDtls(manifestValidateTO.getConsgNumber());
					 */
					if (!StringUtil.isNull(consignmentTO.getDestPincode())) {
						manifestValidateTO.setCnPincodeTO(consignmentTO
								.getDestPincode());

						// rto validation
						ReasonDO reasonDO = rthRtoManifestCommonService
								.validateConsNoForBranchOut(
										CommonConstants.CONSIGNMENT_STATUS_RTOH,
										manifestValidateTO.getOriginOffice()
												.getOfficeId(),
										manifestValidateTO.getConsgNumber());
						if (!StringUtil.isNull(reasonDO)) {
							if (reasonDO.getReasonCode().equalsIgnoreCase(
									CommonConstants.REASON_REDIRECT_CODE)) {
								// dont chek pincode servicblty
							} else if (reasonDO
									.getReasonCode()
									.equalsIgnoreCase(
											CommonConstants.REASON_REDISPATCH_CODE)) {
								// check for serviceabilty
								isValidPincode(manifestValidateTO);
							} else {
								// dont allow this consignmnet
								throw new CGBusinessException(
										ManifestErrorCodesConstants.CN_ALREADY_RTO);
							}
						} else {
							isValidPincode(manifestValidateTO);
						}
					}
				}
			}

			if (!StringUtil.isNull(consignmentTO.getTypeTO())
					&& !StringUtil.isNull(manifestValidateTO
							.getConsignmentTypeTO())
					&& !StringUtils.equalsIgnoreCase(consignmentTO.getTypeTO()
							.getConsignmentCode(), manifestValidateTO
							.getConsignmentTypeTO().getConsignmentCode())) {
				if (StringUtils.equalsIgnoreCase(manifestValidateTO
						.getConsignmentTypeTO().getConsignmentCode(),
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_DOX_TYPE_CONSG);
				else
					throw new CGBusinessException(
							ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
			}
			if (!StringUtils.isEmpty(consignmentTO.getChildCNsDtls())) {
				manifestValidateTO.setChildCNDetails(consignmentTO
						.getChildCNsDtls());
			}
			manifestValidateTO.setConsgTO(consignmentTO);
		}

		/* To convert bookingDO to consignmentModificationTO */
		manifestValidateTO
				.setConsignmentModificationTO(bookingTransObjConverter(
						bookingDO, consignmentTO, isConsignmentPickedUp));

		// validation4: Allowed Product series validation.
		if (!StringUtil.isNull(manifestValidateTO.getManifestProductMapTO())) {
			List<Object[]> mnfstProdMap = outManifestCommonService
					.getManifestProductMapDtls(manifestValidateTO
							.getManifestProductMapTO());
			if (!StringUtil.isEmptyList(mnfstProdMap)) {
				for (Object[] prodManifest : mnfstProdMap) {
					String allowManifest = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isNull(prodManifest[1]))
						allowManifest = prodManifest[1].toString();
					if (StringUtils.equalsIgnoreCase(allowManifest,
							CommonConstants.NO)) {
						manifestValidateTO
								.setIsAllowedProduct(CommonConstants.NO);
						throw new CGBusinessException(
								ManifestErrorCodesConstants.PRODUCT_NOT_ALLOWED);
					}
				}
			}
		}

		// validation 5: Allow party shifted reason consignments
		if (!StringUtil.isNull(consignmentTO)) {
			boolean isCNPartyShifted = outManifestUniversalService
					.isPartyShiftedConsg(consignmentTO.getConsgId());
			if (isCNPartyShifted)
				manifestValidateTO.setIsCnPartyShifted(CommonConstants.YES);
		}
		// Set Process Details
		if (!StringUtil.isNull(consignmentTO)) {
			manifestValidateTO.setUpdatedProcessFrom(consignmentTO
					.getUpdatedProcessFrom());
		}
		if (!StringUtil.isNull(bookingDO)) {
			ProcessTO processTO = null;
			if (!StringUtil.isNull(bookingDO.getUpdatedProcess())) {
				try {
					processTO = new ProcessTO();
					PropertyUtils.copyProperties(processTO,
							bookingDO.getUpdatedProcess());
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					throw new CGBusinessException("PPX0008");
				}
			}
			manifestValidateTO.setUpdatedProcessFrom(processTO);
		}

		return manifestValidateTO;
	}

	public OutManifestValidate isValidConsignmentForThirdParty(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		BookingDO bookingDO = null;

		boolean isConsInManifestd = Boolean.FALSE;
		boolean isConsignmentPickedUp = Boolean.FALSE;
		boolean isBookedInOperatingOffice = Boolean.FALSE;

		String consDelivStatus = deliveryUniversalService
				.getConsignmentStatusFromConsg(manifestValidateTO
						.getConsgNumber());

		if (!StringUtil.isNull(consDelivStatus)
				&& consDelivStatus
						.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CN_ALREADY_DELIVERED);
		}
		// Consignment created through Booking
		ConsignmentTO consignmentTO = outManifestUniversalService
				.getConsingmentDtls(manifestValidateTO.getConsgNumber());

		// If the consignment is null
		if (StringUtil.isNull(consignmentTO)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST);
		}

		if (!StringUtil.isNull(consignmentTO.getConsgStatus())
				&& consignmentTO.getConsgStatus().equalsIgnoreCase(
						CommonConstants.CONSIGNMENT_STATUS_STOPDELV)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CONSIGNMENT_STOPDELV);
		}

		boolean notNull = (!StringUtil.isEmptyInteger(consignmentTO
				.getOrgOffId())
				&& !StringUtil.isNull(manifestValidateTO.getOriginOffice()) && !StringUtil
				.isEmptyInteger(manifestValidateTO.getOriginOffice()
						.getOfficeId()));
		if (notNull
				&& consignmentTO.getOrgOffId().intValue() == manifestValidateTO
						.getOriginOffice().getOfficeId().intValue()) {
			isBookedInOperatingOffice = Boolean.TRUE;
		}
		manifestValidateTO.setIsCNBooked(CommonConstants.YES);
		/*
		 * If consignment is booked in the logged in branch then there is no
		 * need to check whether the consignment is IN-MANIFESTED in this branch
		 * or not.
		 * 
		 * This In-Manifest check will be done only for the consignments which
		 * are not booked in this branch
		 */
		if (!isBookedInOperatingOffice) {// check if consignmnt is
											// in-manifested
			isConsInManifestd = outManifestCommonService
					.isConsgnNoInManifested(manifestValidateTO);
			if (isConsInManifestd)
				manifestValidateTO.setIsConsInManifestd(CommonConstants.YES);
			else
				manifestValidateTO.setIsConsInManifestd(CommonConstants.NO);
		}

		// Check whether misroute process is done or not in same logged in
		// office
		// Added by Himal on 29/11/2013
		String[] processCodes = getMisrouteConsgNotAllowProcesses();
		boolean isMisrouteCheckReq = isMisrouteCheckRequired(processCodes,
				manifestValidateTO.getManifestProcessCode());
		if (isMisrouteCheckReq) {
			boolean isConsMisroute = outManifestCommonService
					.isConsgnNoMisroute(manifestValidateTO);
			if (isConsMisroute)
				throw new CGBusinessException(
						ManifestErrorCodesConstants.CN_IS_ALREADY_MISROUTE);
		}

		/**
		 * If the Consignment is not booked in operating office and also not
		 * In-manifested in the Office throw exception
		 * CN_NOTBOOKED_NOR_IN_MANIFESTED
		 */
		// discussed with saumya and uncommented for defect raised by
		// shahnaz: consg not booked from this office not excepted
		if (!isBookedInOperatingOffice && !isConsInManifestd) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.CN_NOTBOOKED_NOR_IN_MANIFESTED);
		}

		// validation2: CN Number already manifested
		boolean isCnManifested = outManifestCommonService
				.isConsgnNoManifested(manifestValidateTO);

					
					if (isCnManifested) {
						// get the deliveryDetlTO
						DeliveryDetailsTO delvdetlTO = deliveryUniversalService
								.getDrsDtlsByConsgNo(manifestValidateTO.getConsgNumber());
						
						if (!StringUtil.isNull(delvdetlTO)&& !StringUtil.isNull(delvdetlTO.getDeliveryStatus())) {
							if(delvdetlTO.getDeliveryStatus().equalsIgnoreCase("P")){
								//allow
							}else{
								manifestValidateTO.setIsCnManifested(CommonConstants.YES);
								throw new CGBusinessException(
										ManifestErrorCodesConstants.CONSGNMENT_MANIFESTED);
							}
						}else{
							manifestValidateTO.setIsCnManifested(CommonConstants.YES);
							throw new CGBusinessException(
									ManifestErrorCodesConstants.CONSGNMENT_MANIFESTED);
						}
						
						}


		// validation3: Pin code is not serviced by destination.

		if (!StringUtil.isNull(consignmentTO.getTypeTO())
				&& consignmentTO.getTypeTO().getConsignmentCode()
						.equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
			manifestValidateTO.setIsConsParcel(CommonConstants.YES);
		}
		if (StringUtils.equalsIgnoreCase(
				manifestValidateTO.getIsPincodeServChkReq(),
				CommonConstants.YES)) {
			/*
			 * ConsignmentTO consignmentTO = outManifestUniversalService
			 * .getConsingmentDtls(manifestValidateTO.getConsgNumber());
			 */
			if (!StringUtil.isNull(consignmentTO.getDestPincode())) {
				manifestValidateTO.setCnPincodeTO(consignmentTO
						.getDestPincode());
				manifestValidateTO.setConsgTO(consignmentTO);
				isValidPincode(manifestValidateTO);
			}

		}
		if (!StringUtil.isNull(consignmentTO.getTypeTO())
				&& !StringUtil
						.isNull(manifestValidateTO.getConsignmentTypeTO())
				&& !StringUtils.equalsIgnoreCase(consignmentTO.getTypeTO()
						.getConsignmentCode(), manifestValidateTO
						.getConsignmentTypeTO().getConsignmentCode())) {
			if (StringUtils.equalsIgnoreCase(manifestValidateTO
					.getConsignmentTypeTO().getConsignmentCode(),
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))
				throw new CGBusinessException(
						ManifestErrorCodesConstants.ALLOW_DOX_TYPE_CONSG);
			else
				throw new CGBusinessException(
						ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
		}
		if (!StringUtils.isEmpty(consignmentTO.getChildCNsDtls())) {
			manifestValidateTO.setChildCNDetails(consignmentTO
					.getChildCNsDtls());
		}
		manifestValidateTO.setConsgTO(consignmentTO);

		/* To convert bookingDO to consignmentModificationTO */
		manifestValidateTO
				.setConsignmentModificationTO(bookingTransObjConverter(
						bookingDO, consignmentTO, isConsignmentPickedUp));

		// validation4: Allowed Product series validation.
		if (!StringUtil.isNull(manifestValidateTO.getManifestProductMapTO())) {
			List<Object[]> mnfstProdMap = outManifestCommonService
					.getManifestProductMapDtls(manifestValidateTO
							.getManifestProductMapTO());
			if (!StringUtil.isEmptyList(mnfstProdMap)) {
				for (Object[] prodManifest : mnfstProdMap) {
					String allowManifest = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isNull(prodManifest[1]))
						allowManifest = prodManifest[1].toString();
					if (StringUtils.equalsIgnoreCase(allowManifest,
							CommonConstants.NO)) {
						manifestValidateTO
								.setIsAllowedProduct(CommonConstants.NO);
						throw new CGBusinessException(
								ManifestErrorCodesConstants.PRODUCT_NOT_ALLOWED);
					}
				}
			}
		}

		// validation 5: Allow party shifted reason consignments
		if (!StringUtil.isNull(consignmentTO)) {
			boolean isCNPartyShifted = outManifestUniversalService
					.isPartyShiftedConsg(consignmentTO.getConsgId());
			if (isCNPartyShifted)
				manifestValidateTO.setIsCnPartyShifted(CommonConstants.YES);
		}
		// Set Process Details
		if (!StringUtil.isNull(consignmentTO)) {
			manifestValidateTO.setUpdatedProcessFrom(consignmentTO
					.getUpdatedProcessFrom());
		}
		if (!StringUtil.isNull(bookingDO)) {
			ProcessTO processTO = null;
			if (!StringUtil.isNull(bookingDO.getUpdatedProcess())) {
				try {
					processTO = new ProcessTO();
					PropertyUtils.copyProperties(processTO,
							bookingDO.getUpdatedProcess());
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					throw new CGBusinessException("PPX0008");
				}
			}
			manifestValidateTO.setUpdatedProcessFrom(processTO);
		}

		return manifestValidateTO;
	}

}
