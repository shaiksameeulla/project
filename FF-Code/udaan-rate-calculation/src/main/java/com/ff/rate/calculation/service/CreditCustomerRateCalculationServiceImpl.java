package com.ff.rate.calculation.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeInsuredByDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationApplicableSlabsDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationSpecialDestDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.constants.RateCalculationConstants;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;

/**
 * @author mohammal Jun 5, 2013
 * 
 */
public class CreditCustomerRateCalculationServiceImpl extends
		AbstractRateCalculationServiceImpl {

	Logger LOGGER = Logger
			.getLogger(CreditCustomerRateCalculationServiceImpl.class);

	@Override
	public void validateInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::validateInputs::START------------>:::::::");
		// common input validations
		validateCommonInputs(input);
		// specific input validations
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_CUST_CODE);
		if (input.getRiskSurcharge() != null
				&& !StringUtil.isEmptyDouble(input.getRiskSurcharge())) {
			if (StringUtil.isEmptyDouble(input.getDeclaredValue()))
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_DECLARE_VALUE);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::validateInputs::END------------>:::::::");
	}

	@Override
	public List<RateComponentDO> getRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateComponents::START ------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> rateComponents = null;
		// get the Quotation product header
		// Which will also give me the quotation header
		RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO = getRateQuotationProductCategoryHeader(input);

		// Check whether RTO Charge is applicable or not
		boolean rtoToBeCalculated = checkRTOTOBeCalculated(
				rateCalculationProductCategoryHeaderDO, input);
		if (rtoToBeCalculated
				|| !RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
			// Set the Minimum Chargeable weight
			findMinimumChargeableWeight(input,
					rateCalculationProductCategoryHeaderDO);

			// get the other components
			rateComponents = getAplicableComponents(input,
					rateCalculationProductCategoryHeaderDO.getRateQuotationDO()
							.getRateQuotationId());

			// get All the externally configured rate parameters

			getExternallyConfiguredRateParameters(
					rateCalculationProductCategoryHeaderDO, rateComponents,
					input);

			// add into components

			/*
			 * Map<String, RateComponentDO> rateComponentMap = createRateMap(
			 * rateComponents, input);
			 */
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return rateComponents;
	}

	private boolean checkRTOTOBeCalculated(
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			RateCalculationInputTO input) throws CGSystemException {
		RateQuotationRTOChargesDO rtoDo = null;
		Boolean isRTOToBeCalculated = Boolean.FALSE;
		if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
			rtoDo = rateCalcDAO
					.getRTOChargesForRateQuotation(rateCalculationProductCategoryHeaderDO
							.getRateQuotationDO().getRateQuotationId());
			if (!StringUtil.isNull(rtoDo)
					&& RateCommonConstants.YES.equalsIgnoreCase(rtoDo
							.getRtoChargeApplicable())) {
				isRTOToBeCalculated = Boolean.TRUE;
			}
		}

		return isRTOToBeCalculated;
	}

	/**
	 * It gives externally configured rate components
	 * 
	 * @param rateCalculationProductCategoryHeaderDO
	 * @param rateComponents
	 * @param input
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getExternallyConfiguredRateParameters(
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			List<RateComponentDO> rateComponents, RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getExternallyConfiguredRateParameters::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		String taxGroup = null;
		Map<String, Double> taxFigures = null;
		double octroiCharge = 0.0;
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = rateCalcDAO
				.getFixedChargesConfigForQuotation(rateCalculationProductCategoryHeaderDO
						.getRateQuotationDO().getRateQuotationId());

		for (RateComponentDO rateComp : rateComponents) {

			switch (rateComp.getRateComponentCode()) {
			case RateCommonConstants.SLAB_RATE_CODE:
				// get the slab rateComponents
				Double slabCharge = getRateSlabCharge(input,
						rateCalculationProductCategoryHeaderDO);
				rateComp.setRateGlobalConfigValue(slabCharge);
				break;

			case RateCommonConstants.SERVICE_TAX_CODE:
			case RateCommonConstants.EDU_CESS_CODE:
			case RateCommonConstants.HIGHER_EDU_CES_CODE:
				if (StringUtil.isNull(rateComp.getRateGlobalConfigValue())) {
					rateComp.setRateGlobalConfigValue(0.0);
				} else {
					if (taxGroup == null
							|| CGCollectionUtils.isEmpty(taxFigures)) {
						taxGroup = RateCommonConstants.TAX_GROUP_SEC;
						taxFigures = getTaxComponents(
								input.getOriginCityTO(),
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					if (taxFigures != null) {
						rateComp.setRateGlobalConfigValue(taxFigures
								.get(rateComp.getRateComponentCode()));
					}
				}

				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				if (StringUtil.isNull(rateComp.getRateGlobalConfigValue())) {
					rateComp.setRateGlobalConfigValue(0.0);
				} else {
					if (octroiCharge != 0.0) {
						if (taxGroup == null
								|| taxGroup
										.equalsIgnoreCase(RateCommonConstants.TAX_GROUP_SSU)) {
							taxGroup = RateCommonConstants.TAX_GROUP_SEC;
							taxFigures = getTaxComponents(
									input.getOriginCityTO(),
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
											.getCalculationRequestDate()),
									taxGroup);
						}
						String compCode = rateComp.getRateComponentCode();
						compCode = compCode
								.equalsIgnoreCase(RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE) ? RateCommonConstants.SERVICE_TAX_CODE
								: compCode
										.equalsIgnoreCase(RateCommonConstants.EDU_CESS_ON_OCTROI_CODE) ? RateCommonConstants.EDU_CESS_CODE
										: RateCommonConstants.HIGHER_EDU_CES_CODE;
						rateComp.setRateGlobalConfigValue(taxFigures
								.get(compCode));
					}
				}
				break;
			case RateCommonConstants.OCTROI_STATE_TAX_CODE:
			case RateCommonConstants.OCTROI_SURCHARGE_ON_STATE_TAX_CODE:
				if (octroiCharge != 0.0) {
					if (StringUtil.isNull(rateComp.getRateGlobalConfigValue())) {
						rateComp.setRateGlobalConfigValue(0.0);
					} else {
						if (taxGroup == null
								|| taxGroup
										.equalsIgnoreCase(RateCommonConstants.TAX_GROUP_SEC)) {
							taxGroup = RateCommonConstants.TAX_GROUP_SSU;
							taxFigures = getTaxComponents(
									input.getOriginCityTO(),
									DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
											.getCalculationRequestDate()),
									taxGroup);
						}
						String compCode = rateComp.getRateComponentCode();
						compCode = compCode
								.equalsIgnoreCase(RateCommonConstants.OCTROI_STATE_TAX_CODE) ? RateCommonConstants.STATE_TAX_CODE
								: RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE;
						rateComp.setRateGlobalConfigValue(taxFigures
								.get(compCode));

					}
				}
				break;
			case RateCommonConstants.STATE_TAX_CODE:
			case RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE:
				if (StringUtil.isNull(rateComp.getRateGlobalConfigValue())) {
					rateComp.setRateGlobalConfigValue(0.0);
				} else {
					if (taxGroup == null) {
						taxGroup = RateCommonConstants.TAX_GROUP_SSU;
						taxFigures = getTaxComponents(
								input.getOriginCityTO(),
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					rateComp.setRateGlobalConfigValue(taxFigures.get(rateComp
							.getRateComponentCode()));
				}
				break;
			case RateCommonConstants.RISK_CHARGE_CODE:
				Double riskSurcharge = getRiskSurcharge(input,
						rateCalculationProductCategoryHeaderDO,
						fixedChargesConfigResult);
				rateComp.setRateGlobalConfigValue(riskSurcharge);
				break;
			case RateCommonConstants.COD_CHARGE_CODE:
				// Changed by SAUMYA DG
				// Instead of Declared COD Amount is passed as input
				Double codCharge = calculateCODCharge(input.getCodAmount(),
						rateCalculationProductCategoryHeaderDO
								.getRateQuotationDO().getRateQuotationId());
				if (!StringUtil.isEmptyDouble(codCharge)
						&& RateCommonConstants.YES.equalsIgnoreCase(input
								.getIsRTO())) {
					codCharge = -codCharge;
				}
				rateComp.setRateGlobalConfigValue(codCharge);
				break;
			case RateCommonConstants.RTO_CODE:
				if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {

					Double RTODiscount = getRTODiscount(rateCalculationProductCategoryHeaderDO
							.getRateQuotationDO().getRateQuotationId());

					rateComp.setRateGlobalConfigValue(RTODiscount);
				}

				break;
			case RateCommonConstants.DISCOUNT_CODE:
				if (!StringUtil.isEmptyDouble(input.getDiscount())) {
					rateComp.setRateGlobalConfigValue(input.getDiscount());
				}
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI:
				/*
				 * if (fixedChargesConfigResult != null) { if
				 * (RateCommonConstants.CE
				 * .equalsIgnoreCase(fixedChargesConfigResult
				 * .getOctroiBourneBy())) {
				 * rateComp.setRateGlobalConfigValue(0.0); } if
				 * (RateCommonConstants.CONSIGNOR
				 * .equalsIgnoreCase(fixedChargesConfigResult
				 * .getOctroiBourneBy())) { octroiCharge =
				 * rateComp.getRateGlobalConfigValue() == null ? 0.0 :
				 * rateComp.getRateGlobalConfigValue();
				 * rateComp.setRateGlobalConfigValue(octroiCharge); } }
				 */
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.DECLARED_VALUE_CODE:
				if (!StringUtil.isEmptyDouble(input.getDeclaredValue())) {
					rateComp.setRateGlobalConfigValue(input.getDeclaredValue());
				}
				break;
			case RateCommonConstants.OTHER_CHARGES_CODE:
				//if (!StringUtil.isEmptyDouble(input.getOtherCharges())) {
					// Total special charges - DB configuration + user input
					// value
				// Considering only db value for credit customer.
					if (StringUtil.isEmptyDouble(rateComp
							.getRateGlobalConfigValue()))
						rateComp.setRateGlobalConfigValue(0.0);
					/*double totalOtherCharges = rateComp
							.getRateGlobalConfigValue()
							+ input.getOtherCharges();
					rateComp.setRateGlobalConfigValue(totalOtherCharges);*/
				//}
				break;
			case RateCommonConstants.LC_AMOUNT:
				if (!StringUtil.isEmptyDouble(input.getLcAmount())) {
					rateComp.setRateGlobalConfigValue(input.getLcAmount());
				}
				break;
			case RateCommonConstants.COD_AMOUNT:
				if (!StringUtil.isEmptyDouble(input.getCodAmount())) {
					rateComp.setRateGlobalConfigValue(input.getCodAmount());
				}
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES:
				if (!StringUtil.isEmptyDouble(input.getCodAmount())
						&& input.getProductCode().equalsIgnoreCase(
								RateCalculationConstants.T_SERIES_PRODUCT_CODE) && input.getCodAmount() > 0) {
					rateComp.setRateGlobalConfigValue(0.0);
				} else if (!StringUtil.isEmptyDouble(input.getToPayCharge())) {
					rateComp.setRateGlobalConfigValue(input.getToPayCharge()
							+ rateComp.getRateGlobalConfigValue());
				}
				break;
			}
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getExternallyConfiguredRateParameters::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}

	/**
	 * Returns RTO Discount
	 * 
	 * @param rateQuotationId
	 * @return
	 * @throws CGSystemException
	 */
	private Double getRTODiscount(Integer rateQuotationId)
			throws CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRTODiscount::START------------>:::::::");
		Double RTODiscount = 0.0;
		RateQuotationRTOChargesDO rateQuotationRTOCharges = rateCalcDAO
				.getRTOChargesForRateQuotation(rateQuotationId);
		if (rateQuotationRTOCharges != null
				&& rateQuotationRTOCharges.getRtoChargeApplicable()
						.equalsIgnoreCase(RateCommonConstants.YES)) {
			if (rateQuotationRTOCharges.getSameAsSlabRate().equalsIgnoreCase(
					RateCommonConstants.NO)) {
				RTODiscount = rateQuotationRTOCharges.getDiscountOnSlab();
			}

		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRTODiscount::END------------>:::::::");
		return RTODiscount;
	}

	/**
	 * Returns Risk Surcharge.
	 * 
	 * @param input
	 * @param rateCalculationProductCategoryHeaderDO
	 * @param fixedChargesConfigResult
	 * @return
	 * @throws CGSystemException
	 */
	private Double getRiskSurcharge(
			RateCalculationInputTO input,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			RateCalculationFixedChargesConfigDO fixedChargesConfigResult)
			throws CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRiskSurcharge::START------------>:::::::");
		if (fixedChargesConfigResult == null)
			return 0.0;
		RiskSurchargeDO riskSurcharge = rateCalcDAO.getRiskSurcharge(
				input.getDeclaredValue(),
				rateCalculationProductCategoryHeaderDO.getRateQuotationDO()
						.getCustomer().getCustomerCategoryDO()
						.getRateCustomerCategoryId());

		if (fixedChargesConfigResult == null || riskSurcharge == null)
			return 0.0;

		if (riskSurcharge.getChargeApplicable().equalsIgnoreCase(
				RateCommonConstants.NO))
			return 0.0;

		switch (riskSurcharge.getDataFrom()) {
		case "C":
			return fixedChargesConfigResult.getPercentile();
		case "D":

			RiskSurchargeInsuredByDO riskSurchargeInsuredByDO = rateCalcDAO
					.getInsuredBy(riskSurcharge.getInsuredBy());

			return riskSurchargeInsuredByDO.getPercentile();
		case "U":
			return input.getRiskSurcharge();
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRiskSurcharge::START------------>:::::::");
		return 0.0;
	}

	/**
	 * Returns getRateQuotationProductCategoryHeader
	 * 
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private RateCalculationProductCategoryHeaderDO getRateQuotationProductCategoryHeader(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateQuotationProductCategoryHeader::START------------>:::::::");
		String rateQuotationType = null;
		rateQuotationType = RateCommonConstants.RATE_QUOTATION_TYPE_N;
		if (input.getProductCode().equalsIgnoreCase(
				RateCommonConstants.COD_PRODUCT)) {
			rateQuotationType = RateCommonConstants.RATE_QUOTATION_TYPE_E;
		}
		RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO = rateCalcDAO
				.getQuotationForCustomerAndProduct(input, rateQuotationType);

		if (StringUtil.isNull(rateCalculationProductCategoryHeaderDO)) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CREDIT_CUSTOMER_PRODUCT_HEADER);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateQuotationProductCategoryHeader::END------------>:::::::");
		return rateCalculationProductCategoryHeaderDO;
	}

	/**
	 * Set Minimum chargeable weight
	 * 
	 * @param input
	 * @param rateCalculationProductCategoryHeaderDO
	 * @throws CGBusinessException
	 */
	private void findMinimumChargeableWeight(
			RateCalculationInputTO input,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO)
			throws CGBusinessException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::findMinimumChargeableWeight::START------------>:::::::");
		double minimumChargableWeight = 0.0;
		if (rateCalculationProductCategoryHeaderDO == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CREDIT_CUSTOMER_PRODUCT_HEADER);
		}
		if (!StringUtil.isNull(rateCalculationProductCategoryHeaderDO
				.getMinimumChargeableWeightDO())) {
			minimumChargableWeight = rateCalculationProductCategoryHeaderDO
					.getMinimumChargeableWeightDO().getMinChargeableWeight();
		}
		if (minimumChargableWeight > input.getWeight())
			input.setWeight(minimumChargableWeight);
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::findMinimumChargeableWeight::END------------>:::::::");
	}

	/**
	 * Get Rate Slab charge
	 * 
	 * @param input
	 * @param rateCalculationProductCategoryHeaderDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Double getRateSlabCharge(
			RateCalculationInputTO input,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateSlabCharge::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		Double slab = null;
		// Checking destination sector
		SectorDO destSector = null;
		if (rateCalculationProductCategoryHeaderDO.getRateProductCategory()
				.getIsOriginConsidered()
				.equalsIgnoreCase(RateCommonConstants.YES)) {
			destSector = rateCalcDAO.getDestinationSector(input
					.getOriginCityTO().getCityId(), input
					.getDestinationPincode());

		} else {
			destSector = rateCalcDAO.getSectorByPincode(input.getOriginCityTO()
					.getCityId(), input.getDestinationPincode());

		}

		if (StringUtil.isNull(destSector)) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DESTINATION_SECTOR);
		}

		// Set destination city and state in Destination Sector
		destSector.setCityId(input.getDestCityTO().getCityId());
		destSector.setStateId(input.getDestCityTO().getState());

		// Getting origin sector
		SectorDO orgSector = rateCalcDAO.getSectorByPincode(input
				.getOriginCityCode());

		orgSector.setCityId(input.getOriginCityTO().getCityId());
		orgSector.setStateId(input.getOriginCityTO().getState());

		// Check is airport handling charge applicable
		input.setIsAirportHandlingChrgApplicable(isAirportHandlingChargeApplicable(
				orgSector, destSector));

		// Determine the destination to which rate to be calculated
		List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs = determineApplicableDestination(
				orgSector, destSector, rateCalculationProductCategoryHeaderDO,
				input);

		// Check calculation method for Train Cargo.
		if (rateCalculationProductCategoryHeaderDO
				.getRateProductCategory()
				.getRateProductCategoryCode()
				.equalsIgnoreCase(
						RateCalculationConstants.TRAIN_CARGO_RATE_PRODUCT_CODE)) {
			slab = getSlabRate(rateCalculationApplicableSlabs,
					rateCalculationProductCategoryHeaderDO
							.getCalculationMethod(), input.getWeight());

		} else {
			slab = getSlabRate(rateCalculationApplicableSlabs,
					rateCalculationProductCategoryHeaderDO
							.getRateProductCategory().getCalculationType(),
					input.getWeight());
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getRateSlabCharge::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return slab;
	}

	/**
	 * Determine Applicable Destination for rate calculation
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param rateCalculationProductCategoryHeaderDO
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<RateCalculationApplicableSlabsDO> determineApplicableDestination(
			SectorDO orgSector,
			SectorDO destSector,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::determineApplicableDestination::START------------>:::::::");
		List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs = null;
		// Checking special destination sector and getting all the slabs

		if (destSector == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DESTINATION_SECTOR);
		}
		String rateCalculatedFor = getRateCalculatedFor(input
				.getConsignmentType());
		// get normal Destination slabs
		if (destSector.getSectorName().equalsIgnoreCase(
				RateCalculationConstants.INTRA_CITY)) {
			/* Get normal slabs on the basis of DOX, Parcel */
			rateCalculationApplicableSlabs = getNormalDesinationSlabs(
					orgSector, destSector,
					rateCalculationProductCategoryHeaderDO, rateCalculatedFor);

			return rateCalculationApplicableSlabs;
		} else {
			// Get Special Destination if applicable
			rateCalculationApplicableSlabs = getSpecialDesinationSlabs(
					orgSector, destSector,
					rateCalculationProductCategoryHeaderDO, rateCalculatedFor);

			// Return Normal slabs if Special destination slabs are not
			// available
			if (rateCalculationApplicableSlabs.isEmpty()) {
				rateCalculationApplicableSlabs = getNormalDesinationSlabs(
						orgSector, destSector,
						rateCalculationProductCategoryHeaderDO,
						rateCalculatedFor);
			}
		}

		if (CGCollectionUtils.isEmpty(rateCalculationApplicableSlabs)) {
			throw new CGBusinessException(
					RateErrorConstants.ERROR_NO_WEIGHT_SLAB_FOUND);
		}

		return rateCalculationApplicableSlabs;
	}

	/**
	 * @param consignmentType
	 * @return
	 */
	private String getRateCalculatedFor(String consignmentType) {
		String rateCalculatedFor = "";
		switch (consignmentType.toUpperCase()) {
		case "DOX":
			rateCalculatedFor = "D";
			break;
		case "PPX":
			rateCalculatedFor = "P";
			break;
		}
		return rateCalculatedFor;
	}

	/**
	 * Get Normal Destination Slabs
	 * 
	 * @param destSector
	 * @param rateCalculatedFor
	 * @param normalDestinationSlabs
	 * @return
	 * @throws CGSystemException
	 */
	private List<RateCalculationApplicableSlabsDO> getNormalDesinationSlabs(
			SectorDO orgSector,
			SectorDO destSector,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			String rateCalculatedFor) throws CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getNormalDesinationSlabs::START------------>:::::::");
		RateCalculationApplicableSlabsDO rateCalculationApplicableSlabsDO = null;
		List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs = new ArrayList<RateCalculationApplicableSlabsDO>();

		// Create list of RateCalculationSlabRateDO for specific destination
		// sector

		List<RateCalculationQuotationSlabRateDO> normalDestinationSlabs = rateCalcDAO
				.getNormalRateSlabs(rateCalculationProductCategoryHeaderDO
						.getRateQuotationProductCategoryHeaderId(), orgSector
						.getSectorId(), destSector.getSectorId(),
						rateCalculationProductCategoryHeaderDO
								.getRateProductCategory()
								.getIsOriginConsidered(), rateCalculatedFor);

		for (RateCalculationQuotationSlabRateDO normalSlabs : normalDestinationSlabs) {

			rateCalculationApplicableSlabsDO = new RateCalculationApplicableSlabsDO();
			rateCalculationApplicableSlabsDO.setRate(normalSlabs.getRate());
			rateCalculationApplicableSlabsDO
					.setRateCalculationWeightSlabDO(normalSlabs
							.getRateCalculationWeightSlabDO());
			rateCalculationApplicableSlabsDO.setRateCalculatedFor(normalSlabs
					.getRateCalculatedFor());
			rateCalculationApplicableSlabs
					.add(rateCalculationApplicableSlabsDO);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getNormalDesinationSlabs::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	/**
	 * Get Special destination slabs.
	 * 
	 * @param destSector
	 * @param rateCalculatedFor
	 * @param specialDestinationSlabs
	 * @return
	 * @throws CGSystemException
	 */
	private List<RateCalculationApplicableSlabsDO> getSpecialDesinationSlabs(
			SectorDO orgSector,
			SectorDO destSector,
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			String rateCalculatedFor) throws CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSpecialDesinationSlabs::START------------>:::::::");
		RateCalculationApplicableSlabsDO rateCalculationApplicableSlabsDO = null;
		List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs = new ArrayList<RateCalculationApplicableSlabsDO>();

		List<RateCalculationSpecialDestDO> specialSlabs = rateCalcDAO
				.getSpecialDestinationSlabs(destSector.getCityId(), destSector
						.getStateId(), rateCalculationProductCategoryHeaderDO
						.getRateQuotationProductCategoryHeaderId(),
						rateCalculationProductCategoryHeaderDO
								.getRateProductCategory()
								.getIsOriginConsidered(), orgSector
								.getSectorId(), rateCalculatedFor);

		for (RateCalculationSpecialDestDO specialSlabDO : specialSlabs) {
			rateCalculationApplicableSlabsDO = new RateCalculationApplicableSlabsDO();
			rateCalculationApplicableSlabsDO.setRate(specialSlabDO.getRate());
			rateCalculationApplicableSlabsDO
					.setRateCalculationWeightSlabDO(specialSlabDO
							.getRateQuotationWeightSlabDO());
			rateCalculationApplicableSlabsDO.setRateCalculatedFor(specialSlabDO
					.getRateCalculatedFor());
			rateCalculationApplicableSlabs
					.add(rateCalculationApplicableSlabsDO);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSpecialDesinationSlabs::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	/**
	 * To get Special Destination Slab Rate
	 * 
	 * @param string
	 * 
	 * @param specialDestSlabs
	 * @param inputWt
	 * @return
	 * @throws CGBusinessException
	 */
	private Double getSlabRate(
			List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs,
			String rateCalculationMethod, Double inputWt)
			throws CGBusinessException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSlabRate::START------------>:::::::");
		RateCalculationApplicableSlabsDO applicableSlab = new RateCalculationApplicableSlabsDO();
		RateCalculationApplicableSlabsDO addSlab = new RateCalculationApplicableSlabsDO();
		String rateConfiguredFor = null;
		Double slabRate = null;
		boolean isCalcPerKgRate = false;

		/* Get rate Configured for type i.e DOX - D, PPX - P, Both- B */
		if (!CGCollectionUtils.isEmpty(rateCalculationApplicableSlabs)) {
			rateConfiguredFor = rateCalculationApplicableSlabs.get(0)
					.getRateCalculatedFor();
		}

		/* Find applicable slabs for DOX and Non-Courier product */
		if (StringUtil.isNull(rateConfiguredFor)
				|| (!StringUtil.isNull(rateConfiguredFor) && rateConfiguredFor
						.equalsIgnoreCase("D"))) {
			getApplicableSlabs(rateCalculationApplicableSlabs, applicableSlab,
					addSlab, inputWt);
			slabRate = getSlabRateValue(applicableSlab, rateCalculationMethod,
					addSlab, inputWt);
		} else {
			/*
			 * Find applicable slabs for PPX or Both type rate configuration for
			 * Courier product
			 */
			isCalcPerKgRate = getApplicableSlabsForPpxOrBoth(
					rateCalculationApplicableSlabs, applicableSlab, addSlab,
					inputWt, isCalcPerKgRate);
			if (isCalcPerKgRate) {
				rateCalculationMethod = RateCommonConstants.CALCULATION_TYPE_PKG;
			}
			slabRate = getSlabRateValue(applicableSlab, rateCalculationMethod,
					addSlab, inputWt);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSlabRate::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param rateCalculationApplicableSlabs
	 * @param applicableSlab
	 * @param addSlab
	 * @param inputWt
	 * @param isCalcPerKgRate
	 * @throws CGBusinessException
	 */
	private boolean getApplicableSlabsForPpxOrBoth(
			List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs,
			RateCalculationApplicableSlabsDO applicableSlab,
			RateCalculationApplicableSlabsDO addSlab, Double inputWt,
			Boolean isCalcPerKgRate) throws CGBusinessException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getApplicableSlabsForPpxOrBoth::START------------>:::::::");
		Boolean isKUConfigured = false;
		List<RateCalculationApplicableSlabsDO> newApplicableSlabList = null;
		WeightSlabDO kuWeightSlab = null;
		Integer kuPosition = null;
		/* find relative position of KU */
		for (RateCalculationApplicableSlabsDO rateCalculationApplicableSlabsDO : rateCalculationApplicableSlabs) {
			if ((!StringUtil.isNull(rateCalculationApplicableSlabsDO
					.getRateCalculationWeightSlabDO()))
					&& (!StringUtil.isNull(rateCalculationApplicableSlabsDO
							.getRateCalculationWeightSlabDO().getStartWeight()))) {
				kuWeightSlab = rateCalculationApplicableSlabsDO
						.getRateCalculationWeightSlabDO().getStartWeight();
				if (kuWeightSlab.getWeightSlabCategory().equalsIgnoreCase("KU")) {
					isKUConfigured = Boolean.TRUE;
					kuPosition = rateCalculationApplicableSlabs
							.indexOf(rateCalculationApplicableSlabsDO);
					break;
				}
			}
		}
		/* If KU is configured */
		if (isKUConfigured) {
			/* Compare Consignment weight with KU weight */
			/*
			 * If Consignment weight greater than KU weight then calculate per
			 * kg rate on and beyound KU
			 */
			if ((!StringUtil.isNull(kuWeightSlab))
					&& (inputWt >= kuWeightSlab.getStartWeight())) {
				newApplicableSlabList = rateCalculationApplicableSlabs.subList(
						kuPosition, (rateCalculationApplicableSlabs.size()));
				getApplicableSlabs(newApplicableSlabList, applicableSlab,
						addSlab, inputWt);
				/* Per kg rate calculation applicable */
				isCalcPerKgRate = true;
			} else {
				/*
				 * If Consignment weight less than or equal to KU weight then
				 * calculate Abs rate upto KU starting from begining
				 */
				newApplicableSlabList = rateCalculationApplicableSlabs.subList(
						0, kuPosition);
				getApplicableSlabs(newApplicableSlabList, applicableSlab,
						addSlab, inputWt);
			}

		} else { /* If KU is not configured then Abs rate */
			getApplicableSlabs(rateCalculationApplicableSlabs, applicableSlab,
					addSlab, inputWt);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getApplicableSlabsForPpxOrBoth::END------------>:::::::");
		return isCalcPerKgRate;
	}

	/**
	 * @param rateCalculationApplicableSlabs
	 * @param applicableSlab
	 * @param addSlab
	 * @param inputWt
	 * @throws CGBusinessException
	 */
	private void getApplicableSlabs(
			List<RateCalculationApplicableSlabsDO> rateCalculationApplicableSlabs,
			RateCalculationApplicableSlabsDO applicableSlab,
			RateCalculationApplicableSlabsDO addSlab, Double inputWt)
			throws CGBusinessException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getApplicableSlabs::START------------>:::::::");
		WeightSlabDO endSlbWtDO = null;
		/** Check Rate slab available or not */
		Boolean isSlabExists = Boolean.FALSE;
		for (RateCalculationApplicableSlabsDO slab : rateCalculationApplicableSlabs) {

			// Looking for slab rate based on input weight
			WeightSlabDO startSlbWtDO = slab.getRateCalculationWeightSlabDO()
					.getStartWeight();
			String weightSlabCat = slab.getRateCalculationWeightSlabDO()
					.getStartWeight().getWeightSlabCategory();
			if (RateCommonConstants.WEIGHT_SLAB_CATEGORY_PK
					.equalsIgnoreCase(weightSlabCat)) {
				endSlbWtDO = slab.getRateCalculationWeightSlabDO()
						.getStartWeight();
				slab.getRateCalculationWeightSlabDO().setEndWeight(endSlbWtDO);
			} else {
				endSlbWtDO = slab.getRateCalculationWeightSlabDO()
						.getEndWeight();
			}
			// Weight slab will be additional if end weight is null
			if (weightSlabCat
					.compareToIgnoreCase(RateCommonConstants.WEIGHT_SLAB_CATEGORY_PK) != 0
					&& !StringUtil.isNull(startSlbWtDO.getAdditional())
					&& RateCommonConstants.YES.compareToIgnoreCase(startSlbWtDO
							.getAdditional()) == 0) {
				try {
					PropertyUtils.copyProperties(addSlab, slab);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					LOGGER.error(
							"ERROR :: CreditCustomerRateCalculationServiceImpl::getApplicableSlabs::::",
							e);
				}
				// copySlabProperies(addSlab,slab);
			} else {
				// If the end weight is Null & not Additional
				if (StringUtil.isNull(endSlbWtDO)) {
					continue;
				}
				Double startWt = startSlbWtDO.getStartWeight();
				Double endWt = endSlbWtDO.getEndWeight();
				// applicableSlab = slab;
				try {
					PropertyUtils.copyProperties(applicableSlab, slab);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					LOGGER.error(
							"ERROR :: CreditCustomerRateCalculationServiceImpl::getApplicableSlabs::::",
							e);
				}

				if (startWt <= inputWt && inputWt <= endWt) {
					isSlabExists = Boolean.TRUE;
					break;
				}
			}
		}
		/** Check Weight slab is available or not */
		if (!isSlabExists
				&& StringUtil.isNull(addSlab.getRateCalculationWeightSlabDO())) {
			throw new CGBusinessException(
					RateErrorConstants.ERROR_NO_WEIGHT_SLAB_FOUND);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getApplicableSlabs::END------------>:::::::");
	}

	/**
	 * @param applicableSlab
	 * @param rateCalculationMethod
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double getSlabRateValue(
			RateCalculationApplicableSlabsDO applicableSlab,
			String rateCalculationMethod,
			RateCalculationApplicableSlabsDO addSlab, double finalWt) {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSlabRateValue::START------------>:::::::");
		Double slabRate = null;
		if (rateCalculationMethod // ABS
				.equalsIgnoreCase(RateCommonConstants.CALCULATION_TYPE_ABS)) {
			slabRate = calculateAbsolute(applicableSlab, addSlab, finalWt);
		} else {// for PKG
			slabRate = calculatePerKG(applicableSlab, addSlab, finalWt);

		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getSlabRateValue::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculateAbsolute(
			RateCalculationApplicableSlabsDO applicableSlab,
			RateCalculationApplicableSlabsDO addSlab, double finalWt) {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculateAbsolute::START------------>:::::::");
		Double slabRate = applicableSlab.getRate();
		if (addSlab != null
				&& !StringUtil.isNull(addSlab.getRateCalculationWeightSlabDO())) {
			Double slabEndwt = applicableSlab.getRateCalculationWeightSlabDO()
					.getEndWeight().getEndWeight();
			double incrementValue = (finalWt - slabEndwt)
					/ addSlab.getRateCalculationWeightSlabDO().getStartWeight()
							.getStartWeight();
			incrementValue = Math.ceil(incrementValue);
			Double additionalRate = incrementValue * addSlab.getRate();
			slabRate = slabRate + additionalRate;
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculateAbsolute::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculatePerKG(
			RateCalculationApplicableSlabsDO applicableSlab,
			RateCalculationApplicableSlabsDO addSlab, double finalWt) {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculatePerKG::START------------>:::::::");
		double incrementValue = 0.0;
		Double slabRate = 0.0;
		finalWt = Math.ceil(finalWt);
		// If applicable Slab is null - Compute the rate using additional only
		if (StringUtil.isNull(applicableSlab)
				|| StringUtil.isNull(applicableSlab
						.getRateCalculationWeightSlabDO())) {
			incrementValue = (finalWt / addSlab
					.getRateCalculationWeightSlabDO().getStartWeight()
					.getStartWeight());
			incrementValue = Math.ceil(incrementValue);
			slabRate = incrementValue * addSlab.getRate();
		} else {
			// else use the last applicable slab and apply additional
			Double slabEndwt = applicableSlab.getRateCalculationWeightSlabDO()
					.getEndWeight().getEndWeight();
			if (finalWt <= slabEndwt) {
				slabRate = applicableSlab.getRate() * finalWt;
			} else {
				slabRate = applicableSlab.getRate() * slabEndwt;
			}

			if (addSlab != null
					&& !StringUtil.isNull(addSlab
							.getRateCalculationWeightSlabDO())) {
				incrementValue = (finalWt - slabEndwt)
						/ addSlab.getRateCalculationWeightSlabDO()
								.getStartWeight().getStartWeight();
				incrementValue = Math.ceil(incrementValue);
				Double additionalRate = incrementValue * addSlab.getRate();
				slabRate = slabRate + additionalRate;
			}

		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculatePerKG::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param rateTO
	 * @param rateQuotationId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RateComponentDO> getAplicableComponents(
			RateCalculationInputTO rateTO, Integer rateQuotationId)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getAplicableComponents::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> compList = null;
		compList = rateCalcDAO.getRateComponentList(rateTO, rateQuotationId);
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getAplicableComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return compList;
	}

	/**
	 * @param declaredValue
	 * @param rateQuotation
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Double calculateCODCharge(Double declaredValue, Integer rateQuotation)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculateCODCharge::START------------>:::::::");
		Double codCharge = 0.0;
		// Changed by SAUMYA
		if (!StringUtil.isEmptyDouble(declaredValue)) {
			RateQuotationCODChargeDO codChargeDO = rateCalcDAO
					.calculateCODCharge(declaredValue, rateQuotation);
			if (codChargeDO != null) {
				if (!StringUtil.isNull(codChargeDO.getConsiderFixed())
						&& codChargeDO.getConsiderFixed().equals(
								RateCommonConstants.YES)) {
					codCharge = codChargeDO.getFixedChargeValue();
					return codCharge;
				}
				if (!StringUtil.isNull(codChargeDO
						.getConsiderHigherFixedOrPercent())
						&& codChargeDO.getConsiderHigherFixedOrPercent()
								.equalsIgnoreCase(RateCommonConstants.YES)) {
					Double percentValue = codChargeDO.getPercentileValue();
					Double percentileCOD = declaredValue * (percentValue / 100);
					if (percentileCOD > codChargeDO.getFixedChargeValue())
						return percentileCOD;
					else
						return codChargeDO.getFixedChargeValue();
				}

			}
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::calculateCODCharge::END------------>:::::::");
		return codCharge;
	}

	public CityTO getCity(String Pinocde) {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getCity::START------------>:::::::");
		CityDO cityDO = new CityDO();
		CityTO cityTO = new CityTO();
		try {
			cityDO = rateCalcDAO.getCity(Pinocde);
			if (!StringUtil.isNull(cityDO))
				cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
						cityTO);
		} catch (Exception ex) {
			LOGGER.error("ERROR : CreditCustomerRateCalculationServiceImpl", ex);
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getCity::END------------>:::::::");
		return cityTO;
	}

	@Override
	public Boolean isProductValidForContract(ProductToBeValidatedInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::isProductValidForContract::START------------>:::::::");
		// common input validations
		validateCommonInputsForValidProduct(input);

		// specific input validations
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_CUST_CODE);
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::isProductValidForContract::END------------>:::::::");
		return rateCalcDAO.isProductValidForCreditCustomerContract(input);
	}

	@Override
	public List<RateComponentDO> getOctroiRateComponents(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getOctroiRateComponents::START------------>:::::::");
		// get the Quotation product header
		// Which will also give me the quotation header
		RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO = getRateQuotationProductCategoryHeader(input);

		// get the other components
		List<RateComponentDO> rateComponents = getAplicableComponents(input,
				rateCalculationProductCategoryHeaderDO.getRateQuotationDO()
						.getRateQuotationId());

		// get All the externally configured rate parameters

		getOctroiExternallyConfiguredRateParameters(
				rateCalculationProductCategoryHeaderDO, rateComponents, input);
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getOctroiRateComponents::END------------>:::::::");
		return rateComponents;
	}

	private void getOctroiExternallyConfiguredRateParameters(
			RateCalculationProductCategoryHeaderDO rateCalculationProductCategoryHeaderDO,
			List<RateComponentDO> rateComponents, RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::START------------>:::::::");
		String taxGroup = null;
		Map<String, Double> taxFigures = null;
		double octroiCharge = 0.0;
		CityTO city = new CityTO();
		city.setState(input.getOctroiState());
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = rateCalcDAO
				.getFixedChargesConfigForQuotation(rateCalculationProductCategoryHeaderDO
						.getRateQuotationDO().getRateQuotationId());

		for (RateComponentDO rateComp : rateComponents) {
			switch (rateComp.getRateComponentCode()) {
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				if (octroiCharge != 0.0) {
					if (taxGroup == null
							|| taxGroup
									.equalsIgnoreCase(RateCommonConstants.TAX_GROUP_SSU)) {
						taxGroup = RateCommonConstants.TAX_GROUP_SEC;
						taxFigures = getTaxComponents(
								city,
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					String compCode = rateComp.getRateComponentCode();
					compCode = compCode
							.equalsIgnoreCase(RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE) ? RateCommonConstants.SERVICE_TAX_CODE
							: compCode
									.equalsIgnoreCase(RateCommonConstants.EDU_CESS_ON_OCTROI_CODE) ? RateCommonConstants.EDU_CESS_CODE
									: RateCommonConstants.HIGHER_EDU_CES_CODE;
					rateComp.setRateGlobalConfigValue(taxFigures.get(compCode));
				}

				break;
			case RateCommonConstants.OCTROI_STATE_TAX_CODE:
			case RateCommonConstants.OCTROI_SURCHARGE_ON_STATE_TAX_CODE:
				if (octroiCharge != 0.0) {
					if (taxGroup == null
							|| taxGroup
									.equalsIgnoreCase(RateCommonConstants.TAX_GROUP_SEC)) {
						taxGroup = RateCommonConstants.TAX_GROUP_SSU;
						taxFigures = getTaxComponents(
								city,
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					String compCode = rateComp.getRateComponentCode();
					compCode = compCode
							.equalsIgnoreCase(RateCommonConstants.OCTROI_STATE_TAX_CODE) ? RateCommonConstants.STATE_TAX_CODE
							: RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE;
					rateComp.setRateGlobalConfigValue(taxFigures.get(compCode));
				}
				break;

			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI:
				if (fixedChargesConfigResult != null) {
					if (RateCommonConstants.CE
							.equalsIgnoreCase(fixedChargesConfigResult
									.getOctroiBourneBy())) {
						// rateComp.setRateGlobalConfigValue(0.0);
						input.setOctroiBourneBy(RateCommonConstants.CE);
					}
					if (RateCommonConstants.CONSIGNOR
							.equalsIgnoreCase(fixedChargesConfigResult
									.getOctroiBourneBy())) {
						input.setOctroiBourneBy(RateCommonConstants.CONSIGNOR);
					}
					octroiCharge = rateComp.getRateGlobalConfigValue() == null ? 0.0
							: rateComp.getRateGlobalConfigValue();
					rateComp.setRateGlobalConfigValue(octroiCharge);
				} else {
					input.setOctroiBourneBy(RateCommonConstants.CE);
					octroiCharge = rateComp.getRateGlobalConfigValue() == null ? 0.0
							: rateComp.getRateGlobalConfigValue();
					rateComp.setRateGlobalConfigValue(octroiCharge);
				}
				break;
			case RateCommonConstants.OCTROI_CODE:
				rateComp.setRateGlobalConfigValue(input.getOctroiAmount() == null ? 0.0
						: input.getOctroiAmount());
				break;
			}
		}
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::END------------>:::::::");
	}

	@Override
	public void validateOctroiInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::validateOctroiInputs::START------------>:::::::");
		// common input validations
		validateOctroiCommonInputs(input);
		// specific input validations
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_CUST_CODE);
		LOGGER.debug("CreditCustomerRateCalculationServiceImpl::validateOctroiInputs::END------------>:::::::");
	}
}
