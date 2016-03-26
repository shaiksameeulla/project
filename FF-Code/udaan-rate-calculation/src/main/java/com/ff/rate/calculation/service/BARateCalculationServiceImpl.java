package com.ff.rate.calculation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeInsuredByDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateCalculationCODChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateProductDO;
import com.ff.domain.ratemanagement.operations.ba.BaSlabRateDO;
import com.ff.domain.ratemanagement.operations.ba.BaSpecialDestinationRateDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationFixedChargesConfigDO;
import com.ff.rate.calculation.constants.RateCalculationConstants;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;

public class BARateCalculationServiceImpl extends
		AbstractRateCalculationServiceImpl {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BARateCalculationServiceImpl.class);

	/**
	 * Validate BA Rate Calculation Input
	 * 
	 * @throws CGSystemException
	 */
	@Override
	public void validateInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::validateInputs::START------------>:::::::");
		// common input validations
		validateCommonInputs(input);
		// specific input validations
		// Validate BA Code
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_BA_CODE);
		// For Priority Product the Serviced On is Mandatory
		if (RateCommonConstants.PRODUCT_CODE_PRIORITY.equalsIgnoreCase(input
				.getProductCode())) {
			if (StringUtil.isStringEmpty(input.getServiceOn())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_SERVICED_ON_FOR_PRIORITY);
			}
		}
		LOGGER.debug("BARateCalculationServiceImpl::validateInputs::END------------>:::::::");
	}

	/**
	 * Get All Rate Components
	 */
	@Override
	public List<RateComponentDO> getRateComponents(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("BARateCalculationServiceImpl::getRateComponents::START ------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> rateComponents = null;
		// Get Product Header
		BaRateProductDO header = getProductCategoryHeader(input);

		// Check whether RTO Charge is applicable or not
		boolean rtoToBeCalculated = checkRTOTOBeCalculated(header, input);
		if (rtoToBeCalculated
				|| !RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
			// get the other components
			rateComponents = getAplicableComponents(input, header);

			// get All the externally configured rate parameters
			getExternallyConfiguredRateParameters(rateComponents, input, header);
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("BARateCalculationServiceImpl::getRateComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return rateComponents;
	}

	private boolean checkRTOTOBeCalculated(BaRateProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		BARateRTOChargesDO rtoDo = null;
		Boolean isRTOToBeCalculated = Boolean.FALSE;
		String priorityInd = RateCalculationConstants.NO;
		if (input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
			rtoDo = rateCalcDAO.getRTOChargesForBA(header.getBaRateHeaderDO()
					.getHeaderId(), priorityInd);
			if (!StringUtil.isNull(rtoDo)
					&& RateCommonConstants.YES.equalsIgnoreCase(rtoDo
							.getRtoChargeApplicable())) {
				isRTOToBeCalculated = Boolean.TRUE;
			}
		}
		return isRTOToBeCalculated;
	}

	/**
	 * Get BA Product Category header
	 * 
	 * @param input
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private BaRateProductDO getProductCategoryHeader(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("BARateCalculationServiceImpl::getProductCategoryHeader::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		BaRateProductDO baRateProductHeader = rateCalcDAO
				.getBaRateProductHeader(input);
		if (baRateProductHeader == null) {
			throw new CGBusinessException(
					RateErrorConstants.CONTRACT_UNDEFINED_FOR_RATE_CALC);
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("BARateCalculationServiceImpl::getProductCategoryHeader::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return baRateProductHeader;
	}

	/**
	 * Return All Applicable components
	 * 
	 * @param rateTO
	 * @param header
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<RateComponentDO> getAplicableComponents(
			RateCalculationInputTO rateTO, BaRateProductDO header)
			throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("BARateCalculationServiceImpl::getAplicableComponents::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
		List<RateComponentDO> compList = null;
		String priorityInd = RateCalculationConstants.NO;
		if (rateTO.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		compList = rateCalcDAO.getRateComponentListForBA(rateTO, header
				.getBaRateHeaderDO().getHeaderId(), priorityInd);
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("BARateCalculationServiceImpl::getAplicableComponents::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return compList;
	}

	/**
	 * Get Externally configured rate components
	 * 
	 * @param rateComponents
	 * @param input
	 * @param header
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void getExternallyConfiguredRateParameters(
			List<RateComponentDO> rateComponents, RateCalculationInputTO input,
			BaRateProductDO header) throws CGBusinessException,
			CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("BARateCalculationServiceImpl::getExternallyConfiguredRateParameters::START------------>:::::::startMilliseconds"
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
				.getFixedChargesConfigForBA(header.getBaRateHeaderDO()
						.getHeaderId(), priorityInd);
		for (RateComponentDO rateComp : rateComponents) {

			switch (rateComp.getRateComponentCode()) {
			case RateCommonConstants.SLAB_RATE_CODE:
				// get the slab rate
				Double slabCharge = getBARateSlabCharge(input, header);
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
			case RateCommonConstants.RISK_CHARGE_CODE:
				Double riskSurcharge = getRiskSurcharge(input, header,
						fixedChargesConfigResult);
				rateComp.setRateGlobalConfigValue(riskSurcharge);
				break;
			case RateCommonConstants.COD_CHARGE_CODE:
				// Changed by SAUMYA DG
				// Instead of Declared COD Amount is passed as input
				Double codCharge = calculateBACODCharge(input.getCodAmount(),
						header.getBaRateHeaderDO().getHeaderId(), priorityInd);
				if (!StringUtil.isEmptyDouble(codCharge)
						&& RateCommonConstants.YES.equalsIgnoreCase(input
								.getIsRTO())) {
					codCharge = -codCharge;
				}
				rateComp.setRateGlobalConfigValue(codCharge);
				break;
			case RateCommonConstants.RTO_CODE:
				rateComp.setRateGlobalConfigValue(0.0);
				if (RateCommonConstants.YES.equalsIgnoreCase(input.getIsRTO())) {
					Double RTODiscount = getRTODiscount(header
							.getBaRateHeaderDO().getHeaderId(), priorityInd);
					rateComp.setRateGlobalConfigValue(RTODiscount);
				}
				break;
			case RateCommonConstants.DISCOUNT_CODE:
				if (!StringUtil.isEmptyDouble(input.getDiscount())) {
					rateComp.setRateGlobalConfigValue(input.getDiscount());
				}
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI:
				rateComp.setRateGlobalConfigValue(0.0);
				break;
			case RateCommonConstants.DECLARED_VALUE_CODE:
				if (!StringUtil.isEmptyDouble(input.getDeclaredValue())) {
					rateComp.setRateGlobalConfigValue(input.getDeclaredValue());
				}
				break;
			case RateCommonConstants.OTHER_CHARGES_CODE:
				if (!StringUtil.isEmptyDouble(input.getOtherCharges())) {
					/**
					 * Total special charges - DB configuration + user input
					 * value
					 */
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
				if (!StringUtil.isEmptyDouble(input.getToPayCharge())) {
					rateComp.setRateGlobalConfigValue(input.getToPayCharge()
							+ rateComp.getRateGlobalConfigValue());
				}
				break;
			}
		}
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("BARateCalculationServiceImpl::getExternallyConfiguredRateParameters::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}

	/**
	 * Return Risk Surcharge
	 * 
	 * @param input
	 * @param header
	 * @param fixedChargesConfigResult
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException 
	 */
	private Double getRiskSurcharge(RateCalculationInputTO input,
			BaRateProductDO header,
			RateCalculationFixedChargesConfigDO fixedChargesConfigResult)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BARateCalculationServiceImpl::getRiskSurcharge::START------------>:::::::");
		RiskSurchargeDO riskSurcharge = rateCalcDAO.getRiskSurcharge(
				input.getDeclaredValue(), header.getBaRateHeaderDO()
						.getRateCustomerCategory().getRateCustomerCategoryId());
		RiskSurchargeInsuredByDO riskSurchargeInsuredByDO =  null;
		if (riskSurcharge == null)
			return 0.0;

		if (riskSurcharge.getChargeApplicable().equalsIgnoreCase(
				RateCommonConstants.NO))
			return 0.0;

		switch (riskSurcharge.getDataFrom()) {
		case "C":
			return fixedChargesConfigResult.getPercentile();
		case "D":
			riskSurchargeInsuredByDO = rateCalcDAO
					.getInsuredBy(riskSurcharge.getInsuredBy());
			return riskSurchargeInsuredByDO.getPercentile();
		case "U":
			if (StringUtils.isEmpty(input.getInsuredBy())) {
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_INSURED_BY_WHEN_RISK_SURCHARGE_PROVIDED);
			}
			riskSurchargeInsuredByDO = rateCalcDAO
			.getInsuredByInsuredByCode(input.getInsuredBy());
	       return riskSurchargeInsuredByDO.getPercentile();
			//return input.getRiskSurcharge();
		}
		LOGGER.debug("BARateCalculationServiceImpl::getRiskSurcharge::END------------>:::::::");
		return 0.0;
	}

	private Double getRTODiscount(int baRateHeaderId, String priorityInd)
			throws CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::getRTODiscount::START------------>:::::::");
		Double RTODiscount = 0.0;
		BARateRTOChargesDO rateBARTOCharges = rateCalcDAO.getRTOChargesForBA(
				baRateHeaderId, priorityInd);
		if (rateBARTOCharges != null
				&& rateBARTOCharges.getRtoChargeApplicable().equalsIgnoreCase(
						RateCommonConstants.YES)) {
			if (rateBARTOCharges.getSameAsSlabRate().equalsIgnoreCase(
					RateCommonConstants.NO)) {
				RTODiscount = rateBARTOCharges.getDiscountOnSlab();
			}

		}
		LOGGER.debug("BARateCalculationServiceImpl::getRTODiscount::END------------>:::::::");
		return RTODiscount;
	}

	/**
	 * Calculate COD charge
	 * 
	 * @param declaredValue
	 * @param headerId
	 * @param priorityInd
	 * @return
	 * @throws CGSystemException
	 */
	private Double calculateBACODCharge(double declaredValue, int headerId,
			String priorityInd) throws CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::calculateBACODCharge::START------------>:::::::");
		Double codCharge = 0.0;
		// Changed by SAUMYA
		if (!StringUtil.isEmptyDouble(declaredValue)) {
			BaRateCalculationCODChargesDO codChargeDO = rateCalcDAO
					.calculateBACODCharge(declaredValue, headerId, priorityInd);
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
		LOGGER.debug("BARateCalculationServiceImpl::calculateBACODCharge::END------------>:::::::");
		return codCharge;
	}

	/**
	 * Get Slab Charge for BA
	 * 
	 * @param input
	 * @param header
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Double getBARateSlabCharge(RateCalculationInputTO input,
			BaRateProductDO header) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::getBARateSlabCharge::START------------>:::::::");
		// Checking destination sector
		SectorDO destSector = null;
		if (header.getRateProductCategory().getIsOriginConsidered()
				.equalsIgnoreCase(RateCommonConstants.YES)) {
			destSector = rateCalcDAO.getDestinationSector(input
					.getOriginCityTO().getCityId(), input
					.getDestinationPincode());
		} else {
			destSector = rateCalcDAO.getSectorByPincode(input.getOriginCityTO()
					.getCityId(), input.getDestinationPincode());
		}
		// Set destination city and state in Destination Sector
		destSector.setCityId(input.getDestCityTO().getCityId());
		destSector.setStateId(input.getDestCityTO().getState());

		// Getting origin sector
		SectorDO orgSector = rateCalcDAO.getSectorByPincode(input
				.getOriginCityCode());
		// CityTO originCity = getCity(input.getOriginPincode());
		orgSector.setCityId(input.getOriginCityTO().getCityId());
		orgSector.setStateId(input.getOriginCityTO().getState());

		// Check is airport handling charge applicable
		input.setIsAirportHandlingChrgApplicable(isAirportHandlingChargeApplicable(
				orgSector, destSector));

		// Determine the destination to which rate to be calculated
		List<BaSlabRateDO> rateCalculationApplicableSlabs = determineApplicableDestination(
				orgSector, destSector, header, input);

		/**
		 * If Product Train cargo or Air cargo then Upper Integer value should
		 * be consider
		 */
		if (header.getRateProductCategory().getRateProductCategoryCode()
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)
				|| header.getRateProductCategory().getRateProductCategoryCode()
						.equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
			input.setWeight(Math.ceil(input.getWeight()));
		}

		Double slab = getSlabRate(rateCalculationApplicableSlabs,
				input.getWeight(), header.getRateProductCategory()
						.getCalculationType());
		LOGGER.debug("BARateCalculationServiceImpl::getBARateSlabCharge::END------------>:::::::");
		return slab;
	}

	/**
	 * Determine Applicable Destination - Special or Normal Destination
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<BaSlabRateDO> determineApplicableDestination(
			SectorDO orgSector, SectorDO destSector, BaRateProductDO header,
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::determineApplicableDestination::START------------>:::::::");
		List<BaSlabRateDO> rateCalculationApplicableSlabs = null;
		// Checking special destination sector and getting all the slabs

		if (destSector == null) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DESTINATION_SECTOR);
		}

		// get normal Destination slabs
		if (destSector.getSectorName().equalsIgnoreCase(
				RateCalculationConstants.INTRA_CITY)) {
			return getNormalDesinationSlabs(orgSector, destSector, header,
					input);
		}

		// Get Special Destination if applicable
		rateCalculationApplicableSlabs = getSpecialDesinationSlabs(orgSector,
				destSector, header, input);
		// Return Normal slabs if Special destination slabs are not available
		if (rateCalculationApplicableSlabs.isEmpty()) {
			rateCalculationApplicableSlabs = getNormalDesinationSlabs(
					orgSector, destSector, header, input);
		}
		if (CGCollectionUtils.isEmpty(rateCalculationApplicableSlabs)) {
			throw new CGBusinessException(
					RateErrorConstants.ERROR_NO_WEIGHT_SLAB_FOUND);
		}
		LOGGER.debug("BARateCalculationServiceImpl::determineApplicableDestination::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	/**
	 * Calculate Slab Rate
	 * 
	 * @param rateCalculationApplicableSlabs
	 * @param inputWt
	 * @param string
	 * @return
	 * @throws CGBusinessException
	 */
	private Double getSlabRate(
			List<BaSlabRateDO> rateCalculationApplicableSlabs, Double inputWt,
			String rateCalculationMethod) throws CGBusinessException {
		LOGGER.debug("BARateCalculationServiceImpl::getSlabRate::START------------>:::::::");
		BaSlabRateDO applicableSlab = null;
		BaSlabRateDO addSlab = null;

		for (BaSlabRateDO slab : rateCalculationApplicableSlabs) {

			// Looking for slab rate based on input weight
			WeightSlabDO startSlbWtDO = slab.getWeightSlab().getStartWeight();

			WeightSlabDO endSlbWtDO = slab.getWeightSlab().getEndWeight();

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
					if (endSlbWtDO.getWeightSlabCategory().equalsIgnoreCase(
							RateUniversalConstants.WT_SLAB_CAT_MC)) {
						inputWt = endWt;
					}
					break;
				}
			}
		}
		LOGGER.debug("BARateCalculationServiceImpl::getSlabRate::END------------>:::::::");
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
	private Double getSlabRateValue(BaSlabRateDO applicableSlab,
			BaSlabRateDO addSlab, double finalWt, String rateCalculationMethod) {
		LOGGER.debug("BARateCalculationServiceImpl::getSlabRateValue::START------------>:::::::");
		Double slabRate = null;
		if (rateCalculationMethod // ABS
				.equalsIgnoreCase(RateCommonConstants.CALCULATION_TYPE_ABS)) {
			slabRate = calculateAbsolute(applicableSlab, addSlab, finalWt);
		} else {// for PKG
			slabRate = calculatePerKG(applicableSlab, addSlab, finalWt);

		}
		LOGGER.debug("BARateCalculationServiceImpl::getSlabRateValue::END------------>:::::::");
		return slabRate;

	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculatePerKG(BaSlabRateDO applicableSlab,
			BaSlabRateDO addSlab, double finalWt) {
		LOGGER.debug("BARateCalculationServiceImpl::calculatePerKG::START------------>:::::::");
		double incrementValue = 0.0;
		Double slabRate = 0.0;
		finalWt = Math.ceil(finalWt);
		Double slabEndwt = applicableSlab.getWeightSlab().getEndWeight()
				.getEndWeight();
		slabRate = applicableSlab.getRate() * finalWt;
		/** To solve multiplication issue in java we used following code */
		// slabRate =
		// (BigDecimal.valueOf(applicableSlab.getRate()).multiply(BigDecimal.valueOf(finalWt))).doubleValue();
		slabRate = Math.round(slabRate * 100.0) / 100.0;
		if (addSlab != null) {
			incrementValue = (finalWt - slabEndwt)
					/ addSlab.getWeightSlab().getStartWeight().getStartWeight();
			incrementValue = Math.ceil(incrementValue);
			Double additionalRate = incrementValue * addSlab.getRate();
			slabRate = slabRate + additionalRate;
		}

		LOGGER.debug("BARateCalculationServiceImpl::calculatePerKG::END------------>:::::::");
		return slabRate;
	}

	/**
	 * @param applicableSlab
	 * @param addSlab
	 * @param finalWt
	 * @return
	 */
	private Double calculateAbsolute(BaSlabRateDO applicableSlab,
			BaSlabRateDO addSlab, double finalWt) {
		LOGGER.debug("BARateCalculationServiceImpl::calculateAbsolute::START------------>:::::::");
		Double slabRate = applicableSlab.getRate();
		if (addSlab != null) {
			Double slabEndwt = applicableSlab.getWeightSlab().getEndWeight()
					.getEndWeight();
			// Double slabEndwt =
			// applicableSlab.getWeightSlab().getStartWeight();
			double incrementValue = (finalWt - slabEndwt)
					/ addSlab.getWeightSlab().getStartWeight().getStartWeight();
			incrementValue = Math.ceil(incrementValue);
			Double additionalRate = incrementValue * addSlab.getRate();
			slabRate = slabRate + additionalRate;
		}
		LOGGER.debug("BARateCalculationServiceImpl::calculateAbsolute::END------------>:::::::");
		return slabRate;
	}

	/**
	 * Get Normal Destination Slabs
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGSystemException
	 */
	private List<BaSlabRateDO> getNormalDesinationSlabs(SectorDO orgSector,
			SectorDO destSector, BaRateProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::getNormalDesinationSlabs::START------------>:::::::");
		String servicedOn = null;
		if (header.getRateProductCategory().getRateProductCategoryId() == 5) {
			servicedOn = input.getServiceOn();
		}
		List<BaSlabRateDO> normalDestinationSlabs = rateCalcDAO
				.getBANormalRateSlabs(header.getBaProductHeaderId(),
						orgSector.getSectorId(), destSector.getSectorId(),
						servicedOn);
		LOGGER.debug("BARateCalculationServiceImpl::getNormalDesinationSlabs::END------------>:::::::");
		return normalDestinationSlabs;
	}

	/**
	 * To find Special Destination
	 * 
	 * @param orgSector
	 * @param destSector
	 * @param header
	 * @return
	 * @throws CGSystemException
	 */
	private List<BaSlabRateDO> getSpecialDesinationSlabs(SectorDO orgSector,
			SectorDO destSector, BaRateProductDO header,
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::getSpecialDesinationSlabs::START------------>:::::::");

		BaSlabRateDO rateCalculationApplicableSlabsDO = null;
		List<BaSlabRateDO> rateCalculationApplicableSlabs = new ArrayList<BaSlabRateDO>();

		String servicedOn = null;
		if (header.getRateProductCategory().getRateProductCategoryId() == 5) {
			servicedOn = input.getServiceOn();
		}

		List<BaSpecialDestinationRateDO> specialSlabs = rateCalcDAO
				.getBASpecialDestinationSlabs(destSector.getCityId(),
						destSector.getStateId(), header.getBaProductHeaderId(),
						servicedOn);
		for (BaSpecialDestinationRateDO specialSlabDO : specialSlabs) {
			rateCalculationApplicableSlabsDO = new BaSlabRateDO();
			rateCalculationApplicableSlabsDO.setRate(specialSlabDO.getRate());
			rateCalculationApplicableSlabsDO.setWeightSlab(specialSlabDO
					.getWeightSlab());
			rateCalculationApplicableSlabs
					.add(rateCalculationApplicableSlabsDO);
		}
		LOGGER.debug("BARateCalculationServiceImpl::getSpecialDesinationSlabs::END------------>:::::::");
		return rateCalculationApplicableSlabs;
	}

	@Override
	public Boolean isProductValidForContract(ProductToBeValidatedInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::isProductValidForContract::START------------>:::::::");
		// common input validations
		validateCommonInputsForValidProduct(input);

		// specific input validations
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_CUST_CODE);
		LOGGER.debug("BARateCalculationServiceImpl::isProductValidForContract::END------------>:::::::");
		return rateCalcDAO.isProductValidForBACustomerContract(input);
	}

	@Override
	public List<RateComponentDO> getOctroiRateComponents(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::getOctroiRateComponents::START------------>:::::::");
		// Get Product Header
		BaRateProductDO header = getProductCategoryHeader(input);

		// get the other components
		List<RateComponentDO> rateComponents = getAplicableComponents(input,
				header);

		// get All the externally configured rate parameters
		getOctroiExternallyConfiguredRateParameters(rateComponents, input,
				header);
		LOGGER.debug("BARateCalculationServiceImpl::getOctroiRateComponents::END------------>:::::::");
		return rateComponents;
	}

	private void getOctroiExternallyConfiguredRateParameters(
			List<RateComponentDO> rateComponents, RateCalculationInputTO input,
			BaRateProductDO header) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BARateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::START------------>:::::::");
		String taxGroup = null;
		Map<String, Double> taxFigures = null;
		double octroiCharge = 0.0;
		String priorityInd = RateCalculationConstants.NO;
		if (input.getProductCode().equalsIgnoreCase(
				RateCalculationConstants.PRIORITY_PRODUCT_CODE)) {
			priorityInd = RateCalculationConstants.YES;
		}
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = rateCalcDAO
				.getFixedChargesConfigForBA(header.getBaRateHeaderDO()
						.getHeaderId(), priorityInd);

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

				input.setOctroiBourneBy(RateCommonConstants.CE);
				octroiCharge = rateComp.getRateGlobalConfigValue() == null ? 0.0
						: rateComp.getRateGlobalConfigValue();
				rateComp.setRateGlobalConfigValue(octroiCharge);
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
		LOGGER.debug("BARateCalculationServiceImpl::getOctroiExternallyConfiguredRateParameters::END------------>:::::::");
	}

	@Override
	public void validateOctroiInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BARateCalculationServiceImpl::validateOctroiInputs::START------------>:::::::");
		// common input validations
		validateOctroiCommonInputs(input);
		// specific input validations
		if (input.getCustomerCode() == null
				|| StringUtils.isEmpty(input.getCustomerCode()))
			throw new CGBusinessException(RateErrorConstants.VALIDATE_CUST_CODE);
		LOGGER.debug("BARateCalculationServiceImpl::validateOctroiInputs::END------------>:::::::");
	}

}
