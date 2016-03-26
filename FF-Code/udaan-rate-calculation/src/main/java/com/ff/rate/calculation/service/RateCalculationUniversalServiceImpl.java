package com.ff.rate.calculation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.ratemanagement.masters.CustomerCustomerRateTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.rate.calculation.dao.RateCalculationDAO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateComponentCalculatedTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.universe.geography.service.GeographyCommonService;

/**
 * @author prmeher
 * 
 */
public class RateCalculationUniversalServiceImpl implements
		RateCalculationUniversalService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCalculationUniversalServiceImpl.class);
	/** RateCalculationServiceFactory */
	private RateCalculationServiceFactory rateCalculationServiceFactory;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The Rate Calculation DAO. */
	private RateCalculationDAO rateCalcDAO;

	/**
	 * @param rateCalcDAO
	 *            the rateCalcDAO to set
	 */
	public void setRateCalcDAO(RateCalculationDAO rateCalcDAO) {
		this.rateCalcDAO = rateCalcDAO;
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
	 * Calculate Rate for consignment.
	 */
	@Override
	public ConsignmentRateCalculationOutputTO calculateRateForConsignment(
			ConsignmentTO consignmentTO) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds=System.currentTimeMillis();
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::calculateRate::START ------------>:::::::startMilliseconds"+ startMilliseconds);
		/** Rate Calculation Input TO */
		RateCalculationInputTO rateCalculationInputTO = null;
		/** Consignment Rate Calculation Output TO */
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO = null;
		rateCalculationInputTO = new RateCalculationInputTO();
		// Validate Input
		validateConsignmentInput(consignmentTO);
		// Prepare rate calculation input TO from consignmentTO
		prepareRateCalculationInput(consignmentTO, rateCalculationInputTO);
		// Get RateCalculationService
		RateCalculationService rateService = rateCalculationServiceFactory
				.getService(rateCalculationInputTO.getRateType());
		if (rateService != null) {
			// Calculate rates
			RateCalculationOutputTO rates = rateService
					.calculateRate(rateCalculationInputTO);
			// Prepare consignmentRateCalculationOutputTO from
			// rateCalculationInputTO
			if (!StringUtil.isNull(rates)){
				consignmentRateCalculationOutputTO = prepareConsignmentRateOutput(rates);
				if(!StringUtil.isStringEmpty(consignmentTO.getConsgStatus()) && "R".equalsIgnoreCase(consignmentTO.getConsgStatus())){
					consignmentRateCalculationOutputTO.setRateCalculatedFor(consignmentTO.getConsgStatus());
				} else {
					consignmentRateCalculationOutputTO.setRateCalculatedFor("B");
				}
				consignmentRateCalculationOutputTO.setConsgId(consignmentTO
					.getConsgId());
			}
			
		}
		long endMilliSeconds=System.currentTimeMillis();
        long diff=endMilliSeconds-startMilliseconds;
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::calculateRate::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return consignmentRateCalculationOutputTO;
	}

	private void validateConsignmentInput(ConsignmentTO consignmentTO)
			throws CGBusinessException {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::validateConsignmentInput::START");
		// Consignment Type TO is Missing in the input
		if (consignmentTO.getTypeTO() == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSIGNMENT_TYPE_TO);
		}
		// Consignment Type is Missing in the input
		if (consignmentTO.getTypeTO().getConsignmentCode() == null
				|| StringUtils.isEmpty(consignmentTO.getTypeTO()
						.getConsignmentCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSGN_TYPE);
		// Product Code is missing in input
		if (consignmentTO.getProductTO() == null)
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_TO);
		// Product Code is missing in input
		if (consignmentTO.getProductTO().getProductCode() == null
				|| StringUtils.isEmpty(consignmentTO.getProductTO()
						.getProductCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_PRODUCT_SERIES);
		// Operating Office Id is missing in input
		if (StringUtil.isEmptyInteger(consignmentTO.getOperatingOffice()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_OPERATING_OFFICE);
		// Destination Pincode TO is missing in input
		if (StringUtil.isNull(consignmentTO.getDestPincode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DESTINATION_PINCODE_TO);
		// Destination Pincode is missing
		if (consignmentTO.getDestPincode().getPincode() == null
				|| StringUtils.isEmpty(consignmentTO.getDestPincode()
						.getPincode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DEST_PINCODE);
		// consignment status
		String consgStatus = consignmentTO.getConsgStatus();
		if (consgStatus != null && consgStatus.equalsIgnoreCase("R")) {
			if (!StringUtil.isEmptyInteger(consignmentTO.getOperatingOffice())){
				consignmentTO.setOperatingOffice(consignmentTO.getOrgOffId());
			}
			// Event Date for RTO is missing in input
			if (StringUtil.isNull(consignmentTO.getEventDate())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_EVENT_DATE);
			}

		} else {
			// Booking Date is missing in input
			if (StringUtil.isNull(consignmentTO.getBookingDate())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_BOOKING_DATE);
			}
		}

		// CN pricing is missing in input
		if (!StringUtil.isNull(consignmentTO.getConsgPriceDtls())) {
			CNPricingDetailsTO cnPricingDetailsTO = consignmentTO
					.getConsgPriceDtls();
			// Rate Calculation Type is not provided
			if (StringUtils.isEmpty(cnPricingDetailsTO.getRateType())
					|| cnPricingDetailsTO.getRateType() == null)
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_RATE_TYPE);
			if (!("CH".equalsIgnoreCase(cnPricingDetailsTO.getRateType()))) {
				// Customer ID is missing in input
				if (StringUtil.isEmptyInteger(consignmentTO.getCustomer()))
					throw new CGBusinessException(
							RateErrorConstants.VALIDATE_CUSTOMER_ID);
			}
		} else {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CN_PRICING_TO);
		}
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::validateConsignmentInput::END");
	}

	/**
	 * Prepare consignmentRateCalculationOutputTO from rateCalculationInputTO
	 * 
	 * @param rates
	 * @return
	 */
	private ConsignmentRateCalculationOutputTO prepareConsignmentRateOutput(
			RateCalculationOutputTO ratesOutputTO) {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareConsignmentRateOutput::START");
		ConsignmentRateCalculationOutputTO consignRateCalOutputTO = new ConsignmentRateCalculationOutputTO();
		List<RateComponentTO> rateComponents = ratesOutputTO.getComponents();
		List<RateComponentCalculatedTO> calculatedRateComponents = new ArrayList<RateComponentCalculatedTO>(
				rateComponents.size());

		for (RateComponentTO rateComponent : rateComponents) {

			// Create List of RateComponentCalculatedTO
			RateComponentCalculatedTO rateComponentCalculated = new RateComponentCalculatedTO();
			rateComponentCalculated.setRateComponentId(rateComponent
					.getRateComponentId());
			rateComponentCalculated.setRateComponentCode(rateComponent
					.getRateComponentCode());
			rateComponentCalculated.setRateComponentDesc(rateComponent
					.getRateComponentDesc());
			rateComponentCalculated.setCalculatedValue(rateComponent
					.getCalculatedValue());
			calculatedRateComponents.add(rateComponentCalculated);

			// Set individual component value

			// Set individual rate component value
			switch (rateComponent.getRateComponentCode()) {
			case RateCommonConstants.SLAB_RATE_CODE:
				consignRateCalOutputTO.setSlabRate(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_FINAL_SLAB_RATE:
				consignRateCalOutputTO.setFinalSlabRate(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RTO_CODE:
				consignRateCalOutputTO.setRtoDiscount(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE:
				consignRateCalOutputTO.setFuelSurcharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RISK_CHARGE_CODE:
				consignRateCalOutputTO.setRiskSurcharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.COD_CHARGE_CODE:
				consignRateCalOutputTO.setCodCharges(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES:
				consignRateCalOutputTO.settOPayCharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_DOCUMENT_HANDLING_CAHRGES:
				consignRateCalOutputTO.setDocumentHandlingCharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES:
				consignRateCalOutputTO.setParcelHandlingCharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES:
				consignRateCalOutputTO.setAirportHandlingCharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TOTAL_WITHOUT_TAX:
				consignRateCalOutputTO.setTotalWithoutTax(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.SERVICE_TAX_CODE:
				consignRateCalOutputTO.setServiceTax(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.EDU_CESS_CODE:
				consignRateCalOutputTO.setEducationCess(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.HIGHER_EDU_CES_CODE:
				consignRateCalOutputTO.setHigherEducationCess(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.STATE_TAX_CODE:
				consignRateCalOutputTO.setStateTax(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE:
				consignRateCalOutputTO.setSurchargeOnStateTax(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_CODE:
				consignRateCalOutputTO.setOctroi(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE:
				consignRateCalOutputTO.setOctroiServiceCharge(rateComponent
						.getCalculatedValue());
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
				consignRateCalOutputTO
						.setServiceTaxOnOctroiServiceCharge(rateComponent
								.getCalculatedValue());
				break;
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
				consignRateCalOutputTO
						.setEduCessOnOctroiServiceCharge(rateComponent
								.getCalculatedValue());
				break;
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				consignRateCalOutputTO
						.setHigherEduCessOnOctroiServiceCharge(rateComponent
								.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_STATE_TAX_CODE:
				consignRateCalOutputTO
						.setStateTaxOnOctroiServiceCharge(rateComponent
								.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_SURCHARGE_ON_STATE_TAX_CODE:
				consignRateCalOutputTO
						.setSurchargeOnStateTaxOnoctroiServiceCharge(rateComponent
								.getCalculatedValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_GRAND_TOTAL_INCLUDING_TAX:
				consignRateCalOutputTO.setGrandTotalIncludingTax(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES:
				consignRateCalOutputTO.setOtherOrSpecialCharge(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.DISCOUNT_CODE:
				consignRateCalOutputTO.setDiscount(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.COD_AMOUNT:
				consignRateCalOutputTO.setCodAmount(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES:
				consignRateCalOutputTO.setLcCharge(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.RATE_COMPONENT_TYPE_DECLARED_VALUE:
				consignRateCalOutputTO.setDeclaredValue(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.LC_AMOUNT:
				consignRateCalOutputTO.setLcAmount(rateComponent
						.getCalculatedValue());
				break;

			case RateCommonConstants.RATE_COMPONENT_FINAL_SLBRATE_ADDEDTO_FUEL_SURCHARGE:
				consignRateCalOutputTO
						.setFinalSlabRateAddedToRiskSurcharge(rateComponent
								.getCalculatedValue());
				break;
			}

		}
		consignRateCalOutputTO
				.setCalculatedRateComponents(calculatedRateComponents);
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareConsignmentRateOutput::END");
		return consignRateCalOutputTO;
	}

	/**
	 * Prepare rate calculation input TO from consignmentTO
	 * 
	 * @param consignmentTO
	 * @param rateCalculationInputTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void prepareRateCalculationInput(ConsignmentTO consignmentTO,
			RateCalculationInputTO rateCalculationInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareRateCalculationInput::START");
		rateCalculationInputTO.setConsignmentType(consignmentTO.getTypeTO()
				.getConsignmentCode());
		rateCalculationInputTO.setProductCode(consignmentTO.getProductTO()
				.getProductCode());

		CityTO originCity = null;
		if (!StringUtil.isEmptyInteger(consignmentTO.getOrgOffId())) {
			originCity = geographyCommonService.getCityByOfficeId(consignmentTO
					.getOrgOffId());
		} else {
			originCity = geographyCommonService.getCityByOfficeId(consignmentTO
					.getOperatingOffice());
		}
		rateCalculationInputTO.setOriginCityCode(originCity.getCityCode());

		rateCalculationInputTO.setDestinationPincode(consignmentTO
				.getDestPincode().getPincode());
		rateCalculationInputTO.setWeight(consignmentTO.getFinalWeight());

		// consignment status
		String consgStatus = consignmentTO.getConsgStatus();

		if (consgStatus != null && consgStatus.equalsIgnoreCase("R")) {
			rateCalculationInputTO.setCalculationRequestDate(DateUtil
					.getDDMMYYYYDateToString(consignmentTO.getEventDate()));
			rateCalculationInputTO.setIsRTO(RateCommonConstants.YES);
		} else {
			rateCalculationInputTO.setCalculationRequestDate(DateUtil
					.getDDMMYYYYDateToString(consignmentTO.getBookingDate()));
		}

		CustomerCustomerRateTypeDO customerDetails = rateCalcDAO
				.getCustomerCodeAndRateCustomerCategoryByCustomerId(consignmentTO
						.getCustomer());
		if (!StringUtil.isNull(customerDetails)) {
			rateCalculationInputTO.setCustomerCode(customerDetails
					.getCustomerCode());
		}
		if (!StringUtil.isNull(consignmentTO.getInsuredByTO())) {
			rateCalculationInputTO.setInsuredBy(consignmentTO.getInsuredByTO()
					.getInsuredByCode());
		}
		if (!StringUtil.isNull(consignmentTO.getConsgPriceDtls())) {
			CNPricingDetailsTO cnPricingDetailsTO = consignmentTO
					.getConsgPriceDtls();
			if (!StringUtil.isNull(cnPricingDetailsTO.getDeclaredvalue())) {
				rateCalculationInputTO.setDeclaredValue(cnPricingDetailsTO
						.getDeclaredvalue());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getSplChg())) {
				rateCalculationInputTO.setOtherCharges(cnPricingDetailsTO
						.getSplChg());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getDiscount())) {
				rateCalculationInputTO.setDiscount(cnPricingDetailsTO
						.getDiscount());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getLcAmount())) {
				rateCalculationInputTO.setLcAmount(cnPricingDetailsTO
						.getLcAmount());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getServicesOn())) {
				rateCalculationInputTO.setServiceOn(cnPricingDetailsTO
						.getServicesOn());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getRateType())) {
				rateCalculationInputTO.setRateType(cnPricingDetailsTO
						.getRateType());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getCodAmt())) {
				rateCalculationInputTO.setCodAmount(cnPricingDetailsTO
						.getCodAmt());
			}
			if (!StringUtil.isNull(cnPricingDetailsTO.getEbPreferencesCodes())) {
				rateCalculationInputTO.setEbPreference(cnPricingDetailsTO
						.getEbPreferencesCodes());
			}
		}
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareRateCalculationInput::END");
	}

	/**
	 * @param rateCalculationServiceFactory
	 *            the rateCalculationServiceFactory to set
	 */
	public void setRateCalculationServiceFactory(
			RateCalculationServiceFactory rateCalculationServiceFactory) {
		this.rateCalculationServiceFactory = rateCalculationServiceFactory;
	}

	@Override
	public OctroiRateCalculationOutputTO calculateOCTROI(
			ConsignmentTO consignmentTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::calculateRate::START");
		/** Rate Calculation Input TO */
		RateCalculationInputTO rateCalculationInputTO = null;
		/** Consignment Rate Calculation Output TO */
		OctroiRateCalculationOutputTO octroiRateCalculationOutputTO = null;
		rateCalculationInputTO = new RateCalculationInputTO();
		// Validate Input
		validateConsignmentOctroiInput(consignmentTO);
		// Prepare rate calculation input TO from consignmentTO
		prepareOctroiRateCalculationInput(consignmentTO, rateCalculationInputTO);
		// Set Octroi Inputs
		rateCalculationInputTO.setOctroiAmount(consignmentTO.getOctroiAmount());
		rateCalculationInputTO.setOctroiState(consignmentTO.getOctroiState());
		// Get RateCalculationService
		RateCalculationService rateService = rateCalculationServiceFactory
				.getService(rateCalculationInputTO.getRateType());
		if (rateService != null) {
			// Calculate rates
			octroiRateCalculationOutputTO = rateService
					.calculateOCTROI(rateCalculationInputTO);
		}
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::calculateRate::END");
		return octroiRateCalculationOutputTO;
	}

	private void validateConsignmentOctroiInput(ConsignmentTO consignmentTO)
			throws CGBusinessException {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::validateConsignmentOctroiInput::START");
		// Consignment Type TO is Missing in the input
		if (consignmentTO.getTypeTO() == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSIGNMENT_TYPE_TO);
		}
		// Consignment Type is Missing in the input
		if (consignmentTO.getTypeTO().getConsignmentCode() == null
				|| StringUtils.isEmpty(consignmentTO.getTypeTO()
						.getConsignmentCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSGN_TYPE);
		// Product Code is missing in input
		if (consignmentTO.getProductTO() == null)
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_TO);
		// Product Code is missing in input
		if (consignmentTO.getProductTO().getProductCode() == null
				|| StringUtils.isEmpty(consignmentTO.getProductTO()
						.getProductCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_SERIES);
		// Operating Office Id is missing in input
		if (StringUtil.isEmptyInteger(consignmentTO.getOrgOffId()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_OPERATING_OFFICE);
		// consignment status
		String consgStatus = consignmentTO.getConsgStatus();
		if (consgStatus != null && consgStatus.equalsIgnoreCase("R")) {
			// Event Date for RTO is missing in input
			if (StringUtil.isNull(consignmentTO.getEventDate())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_EVENT_DATE);
			}

		} else {
			// Booking Date is missing in input
			if (StringUtil.isNull(consignmentTO.getBookingDate())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_BOOKING_DATE);
			}
		}

		// CN pricing is missing in input
		if (!StringUtil.isNull(consignmentTO.getConsgPriceDtls())) {
			CNPricingDetailsTO cnPricingDetailsTO = consignmentTO
					.getConsgPriceDtls();
			// Rate Calculation Type is not provided
			if (StringUtils.isEmpty(cnPricingDetailsTO.getRateType())
					|| cnPricingDetailsTO.getRateType() == null)
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_RATE_TYPE);
			if (!("CH".equalsIgnoreCase(cnPricingDetailsTO.getRateType()))) {
				// Customer ID is missing in input
				if (StringUtil.isEmptyInteger(consignmentTO.getCustomer()))
					throw new CGBusinessException(
							RateErrorConstants.VALIDATE_CUSTOMER_ID);
			}
		} else {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CN_PRICING_TO);
		}
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::validateConsignmentOctroiInput::END");

	}

	private void prepareOctroiRateCalculationInput(ConsignmentTO consignmentTO,
			RateCalculationInputTO rateCalculationInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareOctroiRateCalculationInput::START");
		rateCalculationInputTO.setConsignmentType(consignmentTO.getTypeTO()
				.getConsignmentCode());
		rateCalculationInputTO.setProductCode(consignmentTO.getProductTO()
				.getProductCode());

		CityTO originCity = geographyCommonService
				.getCityByOfficeId(consignmentTO.getOrgOffId());
		rateCalculationInputTO.setOriginCityCode(originCity.getCityCode());

		// consignment status
		String consgStatus = consignmentTO.getConsgStatus();

		if (consgStatus != null && consgStatus.equalsIgnoreCase("R")) {
			rateCalculationInputTO.setCalculationRequestDate(DateUtil
					.getDDMMYYYYDateToString(consignmentTO.getEventDate()));
			rateCalculationInputTO.setIsRTO(RateCommonConstants.YES);
		} else {
			rateCalculationInputTO.setCalculationRequestDate(DateUtil
					.getDDMMYYYYDateToString(consignmentTO.getBookingDate()));
		}

		CustomerCustomerRateTypeDO customerDetails = rateCalcDAO
				.getCustomerCodeAndRateCustomerCategoryByCustomerId(consignmentTO
						.getCustomer());
		if (!StringUtil.isNull(customerDetails)) {
			rateCalculationInputTO.setCustomerCode(customerDetails
					.getCustomerCode());
		}
		if (!StringUtil.isNull(consignmentTO.getConsgPriceDtls())) {
			CNPricingDetailsTO cnPricingDetailsTO = consignmentTO
					.getConsgPriceDtls();
			if (!StringUtil.isNull(cnPricingDetailsTO.getRateType())) {
				rateCalculationInputTO.setRateType(cnPricingDetailsTO
						.getRateType());
			}
		}
		LOGGER.debug("Ratecalc:::RateCalculationUniversalServiceImpl::prepareOctroiRateCalculationInput::END");
	}

	@Override
	public List<ConsignmentBilling> getConsignmentForRate(int i, String rateType)
			throws CGSystemException {
		return rateCalcDAO.getConsignmentForRate(i, rateType);
	}

	@Override
	public List<ConsignmentTO> convertConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BillingCommonConverter::convertConsignmentDOsToTOs::START----->");
		List<ConsignmentTO> consignmentTOs = null;
		ProductTO product = null;
		// BookingDO bookingDO = null;
		ConsignmentTO consignmentTO = null;
		if (!StringUtil.isEmptyColletion(consignmentDOs)) {
			consignmentTOs = new ArrayList<>(consignmentDOs.size());
			for (ConsignmentBilling consignmentDO : consignmentDOs) {
				consignmentTO = new ConsignmentTO();
				// bookingDO =
				// rateCalcDAO.getConsgBookingDetails(consignmentDO.getConsgNo());
				/*
				 * if (!StringUtil.isNull(bookingDO)) {
				 * consignmentTO.setBookingType(bookingDO.getBookingType()
				 * .getBookingType()); }
				 */
				consignmentTO = setUpConsignmentDtls(consignmentDO);
				/*
				 * consignmentTO.setBookingType(bookingDO.getBookingType()
				 * .getBookingType());
				 */
				if (!StringUtil.isEmptyInteger(consignmentDO.getProductId())) {
					ProductDO productDO = rateCalcDAO.getProduct(consignmentDO
							.getProductId());
					if (!StringUtil.isNull(productDO)) {
						product = new ProductTO();
						CGObjectConverter
								.createToFromDomain(productDO, product);
					}
				}
				consignmentTO.setBookingDate(DateUtil.getCurrentDate());
				/*
				 * if(StringUtil.isStringEmpty(consgStatus)){ if
				 * (!StringUtil.isNull(bookingDO)) { bookingDate =
				 * bookingDO.getBookingDate(); if
				 * (!StringUtil.isNull(bookingDate)) {
				 * consignmentTO.setBookingDate(bookingDate); } } } else
				 * if(!consgStatus.equals("R")){ if
				 * (!StringUtil.isNull(bookingDO)) { bookingDate =
				 * bookingDO.getBookingDate(); if
				 * (!StringUtil.isNull(bookingDate)) {
				 * consignmentTO.setBookingDate(bookingDate); } } }
				 */

				if (!StringUtil.isNull(product)) {
					consignmentTO.setProductTO(product);
				}

				CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
				if (!StringUtil.isNull(consignmentDO.getRateType())) {
					cNPricingDetailsTO.setRateType(consignmentDO.getRateType());
				}
				if (!StringUtil.isEmptyDouble(consignmentDO.getDiscount())) {
					cNPricingDetailsTO.setDiscount(consignmentDO.getDiscount());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
					cNPricingDetailsTO.setTopayChg(consignmentDO.getTopayAmt());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getSplChg())) {
					cNPricingDetailsTO.setSplChg(consignmentDO.getSplChg());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getDeclaredValue())) {
					cNPricingDetailsTO.setDeclaredvalue(consignmentDO
							.getDeclaredValue());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
					cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
					cNPricingDetailsTO.setLcAmount(consignmentDO.getLcAmount());
				}

				if (!StringUtil.isNull(product)) {
					if (product.getProductName().equalsIgnoreCase(
							"Emotional Bond")) {
						cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO
								.getEbPreferencesCodes());
					}
					if (product.getProductName().equalsIgnoreCase("Priority")) {
						cNPricingDetailsTO.setServicesOn(consignmentDO
								.getServicedOn());
					}
				}
				consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
				/*
				 * List<ConsignmentBillingRateTO> consignmentBillingRateTOs=
				 * convertBillingConsignmentRateDOsToTOs
				 * (consignmentDO.getConsgRateDtls());
				 * consignmentTO.setConsignmentBillingRateTOs
				 * (consignmentBillingRateTOs);
				 */
				consignmentTOs.add(consignmentTO);

			}
		}
		LOGGER.debug("BillingCommonConverter::convertConsignmentDOsToTOs::END----->");
		return consignmentTOs;

	}

	private static ConsignmentTO setUpConsignmentDtls(ConsignmentBilling consgDO)
			throws CGBusinessException {

		LOGGER.debug("BillingCommonConverter::setUpConsignmentDtls::START----->");
		ConsignmentTO consgTO = new ConsignmentTO();
		convertConsignmentDO2TO(consgDO, consgTO);
		LOGGER.debug("BillingCommonConverter::setUpConsignmentDtls::END----->");
		return consgTO;
	}

	private static void convertConsignmentDO2TO(ConsignmentBilling consgDO,
			ConsignmentTO consgTO) throws CGBusinessException {

		LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::START----->");
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		if (!StringUtil.isNull(consgDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consgDO.getDestPincodeId(),
					destPin);
			consgTO.setDestPincode(destPin);
		}

		if (!StringUtil.isNull(consgDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter
					.createToFromDomain(consgDO.getConsgType(), typeTO);
			consgTO.setTypeTO(typeTO);
		}
		if (!StringUtil.isNull(consgDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consgDO.getInsuredBy(),
					insuredBy);
			consgTO.setInsuredByTO(insuredBy);
		}
		if (!StringUtil.isEmptyDouble(consgDO.getVolWeight())) {
			VolumetricWeightTO volWeightDtls = new VolumetricWeightTO();
			volWeightDtls.setVolWeight(consgDO.getVolWeight());
			volWeightDtls.setHeight(consgDO.getHeight());
			volWeightDtls.setLength(consgDO.getLength());
			volWeightDtls.setBreadth(consgDO.getBreath());
			consgTO.setVolWightDtls(volWeightDtls);
			consgTO.setVolWeight(consgDO.getVolWeight());
			consgTO.setFinalWeight(consgDO.getFinalWeight());
		}
		LOGGER.debug("BillingCommonConverter::convertConsignmentDO2TO::END----->");
	}

	@Override
	public RateCalculationOutputTO calculateRate(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException {

		// Rate Calculation Type is not provided
		if (StringUtils.isEmpty(inputTO.getRateType())
				|| inputTO.getRateType() == null)
			throw new CGBusinessException(RateErrorConstants.VALIDATE_RATE_TYPE);
		// Get RateCalculationService
		RateCalculationService rateService = rateCalculationServiceFactory
				.getService(inputTO.getRateType());
		RateCalculationOutputTO rates = null;
		if (rateService != null) {
			// Calculate rates
			rates = rateService.calculateRate(inputTO);
		}
		return rates;
	}
}
