package com.ff.rate.calculation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.EBRateConfigDO;
import com.ff.domain.ratemanagement.masters.EBRatePreferenceDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeInsuredByDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.cash.CashCODChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateHeaderProductDO;
import com.ff.domain.ratemanagement.operations.cash.CashSlabRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashSpecialDestinationDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.CashRateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationFixedChargesConfigDO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.constants.RateCalculationConstants;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;

public class CashRateCalculationServiceImpl extends
		AbstractRateCalculationServiceImpl {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashRateCalculationServiceImpl.class);

	/**
	 * Get All Rate Components
	 */
	@Override
	public List<RateComponentDO> getRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CashRateCalculationServiceImpl::getRateComponents::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> rateComponents = null;
		// Slab Rate Calculation for EB
		if (!input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.EB_PRODUCT_CODE)) {
			CashRateHeaderProductDO header = getProductCategoryHeader(input);
			// Check whether RTO Charge is applicable or not
			boolean rtoToBeCalculated = checkRTOTOBeCalculated(header, input);
			if (rtoToBeCalculated
					|| !RateCommonConstants.YES.equalsIgnoreCase(input
							.getIsRTO())) {
				// Set the Minimum Chargeable weight
				findMinimumChargeableWeight(input, header);

				// get the other components
				rateComponents = getAplicableComponents(input, header);

				// get All the externally configured rate parameters
				getExternallyConfiguredRateParameters(rateComponents, input,
						header);
			}

		} else {

			// get the other components
			rateComponents = getAplicableComponents(input);

			// get All the externally configured rate parameters
			getExternallyConfiguredRateParameters(rateComponents, input);

		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CashRateCalculationServiceImpl::getRateComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return rateComponents;

	}

	private boolean checkRTOTOBeCalculated(CashRateHeaderProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		CashRateRTOChargesDO rtoDo = null;
		Boolean isRTOToBeCalculated = Boolean.FALSE;
		String priorityInd = RateCalculationConstants.NO;
		if (input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
			rtoDo = rateCalcDAO.getRTOChargesForCash(header.getHeaderDO()
					.getCashRateHeaderId(), priorityInd);
			if (!StringUtil.isNull(rtoDo)
					&& RateCommonConstants.YES.equalsIgnoreCase(rtoDo
							.getRtoChargeApplicable())) {
				isRTOToBeCalculated = Boolean.TRUE;
			}
		}
		return isRTOToBeCalculated;
	}

	private void findMinimumChargeableWeight(RateCalculationInputTO input,
			CashRateHeaderProductDO header) throws CGBusinessException {
		LOGGER.debug("CashRateCalculationServiceImpl::findMinimumChargeableWeight::START------------>:::::::");
		Double minimumChargableWeight = 0.0;
		if (header == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CASH_RATE_PRODUCT_HEADER);
		}
		if (StringUtil.isNull(header.getMinChargeableWeightDO())) {
			minimumChargableWeight = 0.0;
		} else {
			minimumChargableWeight = header.getMinChargeableWeightDO()
					.getMinChargeableWeight();
		}
		if (StringUtil.isEmptyDouble(minimumChargableWeight)) {
			minimumChargableWeight = 0.0;
		}

		if (minimumChargableWeight > input.getWeight())
			input.setWeight(minimumChargableWeight);
		LOGGER.debug("CashRateCalculationServiceImpl::findMinimumChargeableWeight::END------------>:::::::");
	}

	private void getExternallyConfiguredRateParameters(
			List<RateComponentDO> rateComponents, RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getExternallyConfiguredRateParameters::START------------>:::::::");
		String taxGroup = null;
		Map<String, Double> taxFigures = null;

		for (RateComponentDO rateComp : rateComponents) {
			switch (rateComp.getRateComponentCode()) {
			case RateCommonConstants.SLAB_RATE_CODE:
				// get the slab rate
				Double slabCharge = getCashSlabCharge(input);
				rateComp.setRateGlobalConfigValue(slabCharge);
				break;
			case RateCommonConstants.SERVICE_TAX_CODE:
			case RateCommonConstants.EDU_CESS_CODE:
			case RateCommonConstants.HIGHER_EDU_CES_CODE:
				if ((!StringUtil.isNull(input.getEbRateConfigTO()
						.getServiceTaxApplicable())
						&& input.getEbRateConfigTO().getServiceTaxApplicable()
								.equalsIgnoreCase("Y") && rateComp
						.getRateComponentCode().equalsIgnoreCase(
								RateCommonConstants.SERVICE_TAX_CODE))
						|| (!StringUtil.isNull(input.getEbRateConfigTO()
								.getCessTaxApplicable())
								&& input.getEbRateConfigTO()
										.getCessTaxApplicable()
										.equalsIgnoreCase("Y") && rateComp
								.getRateComponentCode().equalsIgnoreCase(
										RateCommonConstants.EDU_CESS_CODE))
						|| (!StringUtil.isNull(input.getEbRateConfigTO()
								.getHcesstaxApplicable())
								&& input.getEbRateConfigTO()
										.getHcesstaxApplicable()
										.equalsIgnoreCase("Y") && rateComp
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.HIGHER_EDU_CES_CODE))) {
					if (taxGroup == null
							|| CGCollectionUtils.isEmpty(taxFigures)) {
						taxGroup = RateCommonConstants.TAX_GROUP_SEC;
						taxFigures = getTaxComponents(
								input.getOriginCityTO(),
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					if (taxFigures != null)
						rateComp.setRateGlobalConfigValue(taxFigures
								.get(rateComp.getRateComponentCode()));
				} else {
					rateComp.setRateGlobalConfigValue(0.0);
				}
				break;
			case RateCommonConstants.STATE_TAX_CODE:
			case RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE:
				if ((!StringUtil.isNull(input.getEbRateConfigTO()
						.getStateTaxApplicable())
						&& input.getEbRateConfigTO().getStateTaxApplicable()
								.equalsIgnoreCase("Y") && rateComp
						.getRateComponentCode().equalsIgnoreCase(
								RateCommonConstants.STATE_TAX_CODE))
						|| (!StringUtil.isNull(input.getEbRateConfigTO()
								.getSurchargeOnSTApplicable())
								&& input.getEbRateConfigTO()
										.getSurchargeOnSTApplicable()
										.equalsIgnoreCase("Y") && rateComp
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE))) {
					if (taxGroup == null) {
						taxGroup = RateCommonConstants.TAX_GROUP_SSU;
						taxFigures = getTaxComponents(
								input.getOriginCityTO(),
								DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
										.getCalculationRequestDate()), taxGroup);
					}
					rateComp.setRateGlobalConfigValue(taxFigures.get(rateComp
							.getRateComponentCode()));
				} else {
					rateComp.setRateGlobalConfigValue(0.0);
				}
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.OCTROI_STATE_TAX_CODE:
			case RateCommonConstants.OCTROI_SURCHARGE_ON_STATE_TAX_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.DISCOUNT_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.RISK_CHARGE_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.COD_CHARGE_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.RTO_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.DECLARED_VALUE_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.OTHER_CHARGES_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.LC_AMOUNT:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.COD_AMOUNT:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			}
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getExternallyConfiguredRateParameters::END------------>:::::::");
	}

	private Double getCashSlabCharge(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::START------------>:::::::");
		EBRatePreferenceDO ebPreferences = null;
		EBRateConfigTO ebRateConfigTO = null;
		EBRateConfigDO ebRateConfigDO = null;
		Double slab = 0.0;
		// Slab Rate Calculation for EB
		if (input.getProductCode().equalsIgnoreCase("PC000001")) {
			ebPreferences = rateCalcDAO.getEBRatePreferenceDetails(input);
			if (StringUtil.isNull(ebPreferences)) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_EB_RATE_PRODUCT_HEADER);
			}
			if (!StringUtil.isNull(ebPreferences)) {
				slab = ebPreferences.getRate();
				ebRateConfigDO = ebPreferences.getEbRateConfigDO();
				try {
					ebRateConfigTO = new EBRateConfigTO();
					PropertyUtils
							.copyProperties(ebRateConfigTO, ebRateConfigDO);
					ebRateConfigTO.setServiceTaxApplicable(ebRateConfigDO
							.getServiceTaxApplicable());
					ebRateConfigTO.setCessTaxApplicable(ebRateConfigDO
							.getCessTaxApplicable());
					ebRateConfigTO.setHcesstaxApplicable(ebRateConfigDO
							.getHcesstaxApplicable());
					ebRateConfigTO.setStateTaxApplicable(ebRateConfigDO
							.getStateTaxApplicable());
					ebRateConfigTO.setSurchargeOnSTApplicable(ebRateConfigDO
							.getSurchargeSTtaxApplicable());
					input.setEbRateConfigTO(ebRateConfigTO);
				} catch (Exception e) {
					throw new CGBusinessException(
							RateErrorConstants.VALIDATE_EB_RATE_PRODUCT_HEADER);
				}
			}
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::END------------>:::::::");
		return slab;
	}

	private List<RateComponentDO> getAplicableComponents(
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::START------------>:::::::");
		List<RateComponentDO> compList = null;
		compList = rateCalcDAO.getRateComponentListForCash(input);
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::END------------>:::::::");
		return compList;
	}

	/**
	 * Get Product header for Cash.
	 * 
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private CashRateHeaderProductDO getProductCategoryHeader(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CashRateCalculationServiceImpl::getProductCategoryHeader::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		CashRateHeaderProductDO productHeader = rateCalcDAO
				.getCashProductHeaderMap(input);
		if (StringUtil.isNull(productHeader)) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CASH_RATE_PRODUCT_HEADER);
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CashRateCalculationServiceImpl::getProductCategoryHeader::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return productHeader;
	}

	/**
	 * Get All Applicable Components
	 * 
	 * @param rateTO
	 * @param header
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<RateComponentDO> getAplicableComponents(
			RateCalculationInputTO rateTO, CashRateHeaderProductDO header)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CashRateCalculationServiceImpl::getAplicableComponents::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> compList = null;
		String priorityInd = RateCalculationConstants.NO;
		if (rateTO.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		compList = rateCalcDAO.getRateComponentListForCash(rateTO, header
				.getHeaderDO().getCashRateHeaderId(), priorityInd);
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CashRateCalculationServiceImpl::getAplicableComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return compList;
	}

	/**
	 * Get Externally Configured components
	 * 
	 * @param rateComponents
	 * @param input
	 * @param header
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getExternallyConfiguredRateParameters(
			List<RateComponentDO> rateComponents, RateCalculationInputTO input,
			CashRateHeaderProductDO header) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CashRateCalculationServiceImpl::getExternallyConfiguredRateParameters::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		String taxGroup = null;
		Map<String, Double> taxFigures = null;
		double octroiCharge = 0.0;
		String priorityInd = RateCalculationConstants.NO;
		if (input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}

		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = rateCalcDAO
				.getFixedChargesConfigForCash(header.getHeaderDO()
						.getCashRateHeaderId(), priorityInd);

		for (RateComponentDO rateComp : rateComponents) {

			switch (rateComp.getRateComponentCode()) {
			case RateCommonConstants.SLAB_RATE_CODE:
				// get the slab rate
				Double slabCharge = getCashSlabCharge(input, header);
				rateComp.setRateGlobalConfigValue(slabCharge);
				break;
			case RateCommonConstants.SERVICE_TAX_CODE:
			case RateCommonConstants.EDU_CESS_CODE:
			case RateCommonConstants.HIGHER_EDU_CES_CODE:
				if (taxGroup == null || CGCollectionUtils.isEmpty(taxFigures)) {
					taxGroup = RateCommonConstants.TAX_GROUP_SEC;
					taxFigures = getTaxComponents(input.getOriginCityTO(),
							DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
									.getCalculationRequestDate()), taxGroup);
				}
				if (taxFigures != null)
					rateComp.setRateGlobalConfigValue(taxFigures.get(rateComp
							.getRateComponentCode()));
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				if (octroiCharge != 0.0) {
					if (taxGroup == null
							|| taxGroup
									.equalsIgnoreCase(RateCommonConstants.TAX_GROUP_SSU)) {
						taxGroup = RateCommonConstants.TAX_GROUP_SEC;
						taxFigures = getTaxComponents(
								input.getOriginCityTO(),
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
								input.getOriginCityTO(),
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
			case RateCommonConstants.STATE_TAX_CODE:
			case RateCommonConstants.SURCHARGE_ON_STATE_TAX_CODE:
				if (taxGroup == null) {
					taxGroup = RateCommonConstants.TAX_GROUP_SSU;
					taxFigures = getTaxComponents(input.getOriginCityTO(),
							DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
									.getCalculationRequestDate()), taxGroup);
				}
				rateComp.setRateGlobalConfigValue(taxFigures.get(rateComp
						.getRateComponentCode()));
				break;
			case RateCommonConstants.RISK_CHARGE_CODE:
				Double riskSurcharge = getRiskSurcharge(input, header,
						fixedChargesConfigResult);
				rateComp.setRateGlobalConfigValue(riskSurcharge);
				break;
			case RateCommonConstants.COD_CHARGE_CODE:
				// Changed by SAUMYA DG
				// Instead of Declared COD Amount is passed as input
				Double codCharge = calculateCashCODCharge(input.getCodAmount(),
						header.getHeaderProductMapId());
				if (!StringUtil.isEmptyDouble(codCharge)
						&& RateCommonConstants.YES.equalsIgnoreCase(input
								.getIsRTO())) {
					codCharge = -codCharge;
				}
				rateComp.setRateGlobalConfigValue(codCharge);
				break;
			case RateCommonConstants.RTO_CODE:
				if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
					Double RTODiscount = getRTODiscount(header.getHeaderDO()
							.getCashRateHeaderId(), priorityInd);
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
				if (!StringUtil.isEmptyDouble(input.getOtherCharges())) {
					// Total special charges - DB configuration + user input
					// value
					if (StringUtil.isEmptyDouble(rateComp
							.getRateGlobalConfigValue()))
						rateComp.setRateGlobalConfigValue(0.0);
					double totalOtherCharges = rateComp
							.getRateGlobalConfigValue()
							+ input.getOtherCharges();
					rateComp.setRateGlobalConfigValue(totalOtherCharges);
				}
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
		LOGGER.debug("CashRateCalculationServiceImpl::getExternallyConfiguredRateParameters::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}

	private Double getRTODiscount(Integer headerId, String priorityInd)
			throws CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getRTODiscount::START------------>:::::::");
		Double RTODiscount = 0.0;
		CashRateRTOChargesDO cashRTOCharges = rateCalcDAO.getRTOChargesForCash(
				headerId, priorityInd);
		if (cashRTOCharges != null
				&& cashRTOCharges.getRtoChargeApplicable().equalsIgnoreCase(
						RateCommonConstants.YES)) {
			if (cashRTOCharges.getSameAsSlabRate().equalsIgnoreCase(
					RateCommonConstants.NO)) {
				RTODiscount = cashRTOCharges.getDiscountOnSlab();
			}

		}
		LOGGER.debug("CashRateCalculationServiceImpl::getRTODiscount::END------------>:::::::");
		return RTODiscount;
	}

	/**
	 * Calculate COD charge
	 * 
	 * @param declaredValue
	 * @param headerProductMapId
	 * @return
	 * @throws CGSystemException
	 */
	private Double calculateCashCODCharge(double declaredValue,
			Integer headerProductMapId) throws CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::calculateCashCODCharge::START------------>:::::::");
		Double codCharge = 0.0;
		// Changed by SAUMYA
		if (!StringUtil.isEmptyDouble(declaredValue)) {
			CashCODChargesDO codChargeDO = rateCalcDAO.calculateCashCODCharge(
					declaredValue, headerProductMapId);
			if (codChargeDO != null) {
				if (codChargeDO.getConsiderFixed().equals(
						RateCommonConstants.YES)) {
					codCharge = codChargeDO.getFixedChargeValue();
					return codCharge;
				}
				if (codChargeDO.getConsiderHigherFixedOrPercent()
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
		LOGGER.debug("CashRateCalculationServiceImpl::calculateCashCODCharge::END------------>:::::::");
		return codCharge;
	}

	/**
	 * Calculate Risk Surcharge
	 * 
	 * @param input
	 * @param header
	 * @param fixedChargesConfigResult
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Double getRiskSurcharge(RateCalculationInputTO input,
			CashRateHeaderProductDO header,
			RateCalculationFixedChargesConfigDO fixedChargesConfigResult)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getRiskSurcharge::START------------>:::::::");
		/* Risk Surcharge is provided as input */
		if (!StringUtil.isEmptyDouble(input.getRiskSurcharge())) {
			/* Insured By is not provided as input */
			if (StringUtils.isEmpty(input.getInsuredBy())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_INSURED_BY_WHEN_RISK_SURCHARGE_PROVIDED);
			}
		}

		/*
		 * if (fixedChargesConfigResult == null) return 0.0;
		 */
		RiskSurchargeDO riskSurcharge = rateCalcDAO.getRiskSurcharge(
				input.getDeclaredValue(),
				RateCalculationConstants.RATE_CASH_CUSTOMER_CATEGORY_ID);
		if (riskSurcharge == null)
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
			if (StringUtils.isEmpty(input.getInsuredBy())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_INSURED_BY_WHEN_RISK_SURCHARGE_PROVIDED);
			}
			RiskSurchargeInsuredByDO riskSurchargeInsuredBy = rateCalcDAO
					.getInsuredByInsuredByCode(input.getInsuredBy());
			return riskSurchargeInsuredBy.getPercentile();
			// return input.getRiskSurcharge();
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getRiskSurcharge::END------------>:::::::");
		return 0.0;
	}

	/**
	 * Calculate Slab charge
	 * 
	 * @param input
	 * @param header
	 * @return
	 * @throws CGBusinessExceptiongetDestinationSector
	 * @throws CGSystemException
	 */
	private Double getCashSlabCharge(RateCalculationInputTO input,
			CashRateHeaderProductDO header) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		Double slab = 0.0;
		SectorDO destSector = null;
		// Checking destination sector

		if (header.getRateProductCategoryDO().getIsOriginConsidered()
				.equalsIgnoreCase(RateCommonConstants.YES)) {
			destSector = rateCalcDAO.getDestinationSector(input
					.getOriginCityTO().getCityId(), input
					.getDestinationPincode());
		} else {
			destSector = rateCalcDAO.getSectorByPincode(input.getOriginCityTO()
					.getCityId(), input.getDestinationPincode());
		}
		/** Set destination city and state in Destination Sector */
		CityTO destCity = getCity(input.getDestinationPincode());
		destSector.setCityId(destCity.getCityId());
		destSector.setStateId(destCity.getState());

		// Getting origin sector
		SectorDO orgSector = rateCalcDAO.getSectorByPincode(input
				.getOriginCityCode());
		/** Set origin State and city */
		orgSector.setCityId(input.getOriginCityTO().getCityId());
		orgSector.setStateId(input.getOriginCityTO().getState());

		// Check is airport handling charge applicable
		input.setIsAirportHandlingChrgApplicable(isAirportHandlingChargeApplicable(
				orgSector, destSector));

		// Determine the destination to which rate to be calculated
		List<CashSlabRateDO> rateCalculationApplicableSlabs = determineApplicableDestination(
				orgSector, destSector, header, input);

		if (StringUtil.isEmptyColletion(rateCalculationApplicableSlabs)) {
			throw new CGBusinessException(
					RateErrorConstants.ERROR_NO_WEIGHT_SLAB_FOUND);
		}
		slab = getSlabRate(rateCalculationApplicableSlabs, header
				.getRateProductCategoryDO().getCalculationType(),
				input.getWeight());
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("CashRateCalculationServiceImpl::getCashSlabCharge::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return slab;
	}

	/**
	 * Determine applicable destination
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<CashSlabRateDO> determineApplicableDestination(
			SectorDO orgSector, SectorDO destSector,
			CashRateHeaderProductDO header, RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::determineApplicableDestination::START------------>:::::::");

		List<CashSlabRateDO> rateCalculationApplicableSlabs = null;
		// Checking special destination sector and getting all the slabs

		if (destSector == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DESTINATION_SECTOR);
		}

		// get normal Destination slabs
		if (destSector.getSectorName().equalsIgnoreCase(
				RateCalculationConstants.INTRA_CITY)) {
			rateCalculationApplicableSlabs = getNormalDesinationSlabs(
					orgSector, destSector, header, input);
		} else {
			// Get Special Destination if applicable
			rateCalculationApplicableSlabs = getSpecialDesinationSlabs(
					orgSector, destSector, header, input);
			// Return Normal slabs if Special destination slabs are not
			// available
			if (StringUtil.isEmptyColletion(rateCalculationApplicableSlabs)) {
				rateCalculationApplicableSlabs = getNormalDesinationSlabs(
						orgSector, destSector, header, input);
			}
		}
		if (CGCollectionUtils.isEmpty(rateCalculationApplicableSlabs)) {
			throw new CGBusinessException(
					RateErrorConstants.ERROR_NO_WEIGHT_SLAB_FOUND);
		}
		LOGGER.debug("CashRateCalculationServiceImpl::determineApplicableDestination::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	/**
	 * To get Slabs for normal destination
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGSystemException
	 */
	private List<CashSlabRateDO> getNormalDesinationSlabs(SectorDO orgSector,
			SectorDO destSector, CashRateHeaderProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getNormalDesinationSlabs::START------------>:::::::");
		String servicedOn = null;
		if (header.getRateProductCategoryDO().getRateProductCategoryId() == 5) {
			servicedOn = input.getServiceOn();
		}
		List<CashSlabRateDO> normalDestinationSlabs = rateCalcDAO
				.getCashNormalRateSlabs(header.getHeaderProductMapId(),
						orgSector.getSectorId(), destSector.getSectorId(),
						servicedOn, header.getRateProductCategoryDO()
								.getIsOriginConsidered());
		LOGGER.debug("CashRateCalculationServiceImpl::getNormalDesinationSlabs::END------------>:::::::");
		return normalDestinationSlabs;
	}

	/**
	 * To get Slabs for special destination
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGSystemException
	 */
	private List<CashSlabRateDO> getSpecialDesinationSlabs(SectorDO orgSector,
			SectorDO destSector, CashRateHeaderProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getSpecialDesinationSlabs::START------------>:::::::");

		CashSlabRateDO rateCalculationApplicableSlabsDO = null;
		List<CashSlabRateDO> rateCalculationApplicableSlabs = new ArrayList<CashSlabRateDO>();

		String servicedOn = null;
		if (header.getRateProductCategoryDO().getRateProductCategoryId() == 5) {
			servicedOn = input.getServiceOn();
		}
		List<CashSpecialDestinationDO> specialSlabs = rateCalcDAO
				.getCashSpecialDestinationSlabs(destSector.getCityId(),
						destSector.getStateId(),
						header.getHeaderProductMapId(), servicedOn, header
								.getRateProductCategoryDO()
								.getIsOriginConsidered(), orgSector
								.getSectorId());
		for (CashSpecialDestinationDO specialSlabDO : specialSlabs) {
			rateCalculationApplicableSlabsDO = new CashSlabRateDO();
			rateCalculationApplicableSlabsDO.setSlabRate(specialSlabDO
					.getSlabRate());
			rateCalculationApplicableSlabsDO.setWeightSlabDO(specialSlabDO
					.getWeightSlab());
			rateCalculationApplicableSlabs
					.add(rateCalculationApplicableSlabsDO);
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getSpecialDesinationSlabs::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	/**
	 * calculate Slab Rate
	 * 
	 * @param rateCalculationApplicableSlabs
	 * @param inputWt
	 * @return
	 * @throws CGBusinessException
	 */
	private Double getSlabRate(
			List<CashSlabRateDO> rateCalculationApplicableSlabs,
			String rateCalculationMethod, Double inputWt)
			throws CGBusinessException {
		LOGGER.debug("CashRateCalculationServiceImpl::getSlabRate::START------------>:::::::");
		CashSlabRateDO applicableSlab = null;
		CashSlabRateDO addSlab = null;

		for (CashSlabRateDO slab : rateCalculationApplicableSlabs) {

			// Looking for slab rate based on input weight
			WeightSlabDO startSlbWtDO = slab.getWeightSlabDO();

			WeightSlabDO endSlbWtDO = slab.getWeightSlabDO();

			// Weight slab will be additional if end weight is null
			if (!StringUtil.isNull(startSlbWtDO.getAdditional())
					&& !RateCommonConstants.WEIGHT_SLAB_CATEGORY_PK
							.equalsIgnoreCase(endSlbWtDO
									.getWeightSlabCategory())
					&& RateCommonConstants.YES.compareToIgnoreCase(startSlbWtDO
							.getAdditional()) == 0) {
				addSlab = slab;
			} else {
				Double startWt = startSlbWtDO.getStartWeight();
				Double endWt = endSlbWtDO.getEndWeight();
				applicableSlab = slab;
				if (startWt <= inputWt && inputWt <= endWt) {
					break;
				}
			}
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getSlabRate::END------------>:::::::");
		return getSlabRateValue(applicableSlab, addSlab, inputWt,
				rateCalculationMethod);
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @param rateCalculationMethod
	 * @return
	 */
	private Double getSlabRateValue(CashSlabRateDO applicableSlab,
			CashSlabRateDO addSlab, double finalWt, String rateCalculationMethod) {
		LOGGER.debug("CashRateCalculationServiceImpl::getSlabRateValue::START------------>:::::::");
		Double slabRate = null;
		if (rateCalculationMethod // ABS
				.equalsIgnoreCase(RateCommonConstants.CALCULATION_TYPE_ABS)) {
			slabRate = calculateAbsolute(applicableSlab, addSlab, finalWt);
		} else {// for PKG
			slabRate = calculatePerKG(applicableSlab, addSlab, finalWt);

		}
		LOGGER.debug("CashRateCalculationServiceImpl::getSlabRateValue::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculatePerKG(CashSlabRateDO applicableSlab,
			CashSlabRateDO addSlab, double finalWt) {
		LOGGER.debug("CashRateCalculationServiceImpl::calculatePerKG::START------------>:::::::");
		Double slabRate = applicableSlab.getSlabRate() * finalWt;
		LOGGER.debug("CashRateCalculationServiceImpl::calculatePerKG::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculateAbsolute(CashSlabRateDO applicableSlab,
			CashSlabRateDO addSlab, double finalWt) {
		LOGGER.debug("CashRateCalculationServiceImpl::savePickupBooking::START------------>:::::::");
		Double slabRate = applicableSlab.getSlabRate();
		if (addSlab != null) {
			Double slabEndwt = applicableSlab.getWeightSlabDO().getEndWeight();
			double incrementValue = (finalWt - slabEndwt)
					/ addSlab.getWeightSlabDO().getStartWeight();
			incrementValue = Math.ceil(incrementValue);
			Double additionalRate = incrementValue * addSlab.getSlabRate();
			slabRate = slabRate + additionalRate;
		}
		LOGGER.debug("CashRateCalculationServiceImpl::savePickupBooking::START------------>:::::::");
		return slabRate;
	}

	@Override
	public void validateInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::savePickupBooking::START------------>:::::::");
		// common input validations
		validateCommonInputs(input);
		// specific input validations
		// For Priority Product the Serviced On is Mandatory
		if (RateCommonConstants.PRODUCT_CODE_PRIORITY.equalsIgnoreCase(input
				.getProductCode())) {
			if (StringUtil.isStringEmpty(input.getServiceOn())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_SERVICED_ON_FOR_PRIORITY);
			}
		}
		if (RateCommonConstants.PRODUCT_CODE_EMOTIONAL_BOND
				.equalsIgnoreCase(input.getProductCode())) {
			if (StringUtil.isStringEmpty(input.getEbPreference())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_EB_PREFERENCES);
			}
		}
		LOGGER.debug("CashRateCalculationServiceImpl::getOctroiRateComponents::END------------>:::::::");
	}

	@Override
	public Boolean isProductValidForContract(ProductToBeValidatedInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::isProductValidForContract::START------------>:::::::");
		Boolean isValid = false;
		// common input validations
		validateCommonInputsForValidProduct(input);

		if (RateCommonConstants.PRODUCT_CODE_EMOTIONAL_BOND
				.equalsIgnoreCase(input.getProductCode())) {
			// specific input validations
			if (StringUtil.isStringEmpty(input.getEbPreference())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_EB_PREFERENCES);
			}

			isValid = rateCalcDAO.isProductValidForEBCashContract(input);
		} else {
			isValid = rateCalcDAO.isProductValidForCashContract(input);
		}
		LOGGER.debug("CashRateCalculationServiceImpl::isProductValidForContract::END------------>:::::::");
		return isValid;
	}

	@Override
	public List<RateComponentDO> getOctroiRateComponents(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::getOctroiRateComponents::START------------>:::::::");
		List<RateComponentDO> rateComponents = null;

		CashRateHeaderProductDO header = getProductCategoryHeader(input);

		// Set the Minimum Chargeable weight
		findMinimumChargeableWeight(input, header);

		// get the other components
		rateComponents = getAplicableComponents(input, header);

		// get All the externally configured rate parameters
		getOctroiExternallyConfiguredRateParameters(rateComponents, input,
				header);
		LOGGER.debug("CashRateCalculationServiceImpl::getOctroiRateComponents::END------------>:::::::");
		return rateComponents;
	}

	/**
	 * @param rateComponents
	 * @param input
	 * @param header
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void getOctroiExternallyConfiguredRateParameters(
			List<RateComponentDO> rateComponents, RateCalculationInputTO input,
			CashRateHeaderProductDO header) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("CashRateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::START------------>:::::::");
		String taxGroup = null;
		Map<String, Double> taxFigures = null;
		double octroiCharge = 0.0;
		String priorityInd = RateCalculationConstants.NO;
		if (input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = rateCalcDAO
				.getFixedChargesConfigForCash(header.getHeaderDO()
						.getCashRateHeaderId(), priorityInd);

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
								input.getOriginCityTO(),
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
								input.getOriginCityTO(),
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

		LOGGER.debug("CashRateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::END------------>:::::::");
	}

	@Override
	public void validateOctroiInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateCalculationServiceImpl::validateOctroiInputs::START------------>:::::::");
		// common input validations
		validateOctroiCommonInputs(input);
		LOGGER.debug("CashRateCalculationServiceImpl::validateOctroiInputs::END------------>:::::::");
	}
}
