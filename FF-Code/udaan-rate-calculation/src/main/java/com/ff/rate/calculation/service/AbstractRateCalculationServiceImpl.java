package com.ff.rate.calculation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.RateComponentCalculatedDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateMinChargeableWeightDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.ConsignmentRateDO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.constants.RateCalculationConstants;
import com.ff.rate.calculation.dao.RateCalculationDAO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateCalculationTO;
import com.ff.to.rate.RateComponentTO;

/**
 * @author mohammal May 23, 2013
 * 
 */
public abstract class AbstractRateCalculationServiceImpl implements
		RateCalculationService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractRateCalculationServiceImpl.class);
	protected RateCalculationDAO rateCalcDAO;

	/* Set Rate Type */
	private String rateType;
	
	/* Set Rate Type */
	private String customerCode;


	public void setRateCalcDAO(RateCalculationDAO rateCalcDAO) {
		this.rateCalcDAO = rateCalcDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.rate.calculation.service.RateCalculationService#calculateRate(
	 * com.ff.to.rate.RateCalculationInputTO)
	 */
	@Override
	public RateCalculationOutputTO calculateRate(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateRate() :: Start --------> ::::::");
		RateCalculationOutputTO output = null;

		// Validating rate type inputs
		validateInputs(input);

		// Set Rate Type
		rateType = input.getRateType();
		
		// Set Customer Code
		customerCode = input.getCustomerCode();

		// Get Rate Components
		List<RateComponentDO> components = getRateComponents(input);

		// Calculate the rate with all the retrieved components
		output = calculateComponentsRate(components, input);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateRate() :: End --------> ::::::");
		return output;
	}

	@Override
	public SectorDO getDestinationSector(RateCalculationTO rateTO)
			throws CGBusinessException {
		// SectorDO sector = null;
		// Checking for within city sector

		return null;
	}

	protected void setMinimumChargeableWt(RateCalculationInputTO input,
			RateMinChargeableWeightDO minWtDO) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: setMinimumChargeableWt() :: Start --------> ::::::");
		Double minimumChrgWt = minWtDO.getMinChargeableWeight();
		// checking for chargeable weight and applying the greater one
		if (minimumChrgWt != null && minimumChrgWt > input.getWeight()) {
			input.setWeight(minimumChrgWt);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: setMinimumChargeableWt() :: End --------> ::::::");
	}

	protected void setMinimumChargeableWt(RateCalculationInputTO input,
			Double minChrgWt) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: setMinimumChargeableWt() :: Start --------> ::::::");
		if (minChrgWt != null && minChrgWt > input.getWeight()) {
			input.setWeight(minChrgWt);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: setMinimumChargeableWt() :: End --------> ::::::");
	}

	// public abstract void validateInputs(RateCalculationInputTO input) throws
	// CGBusinessException;

	@Override
	public void validateCommonInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateCommonInputs() :: Start --------> ::::::");
		// Product Code is missing in input
		if (input.getProductCode() == null
				|| StringUtils.isEmpty(input.getProductCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_SERIES);
		// Consignment Type is Missing in the input
		if (input.getConsignmentType() == null
				|| StringUtils.isEmpty(input.getConsignmentType()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSGN_TYPE);
		// Origin is not provided
		if (input.getOriginCityCode() == null
				|| StringUtils.isEmpty(input.getOriginCityCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_ORIGIN_CITY_CODE);
		// Destination Pincode is missing
		if (input.getDestinationPincode() == null
				|| StringUtils.isEmpty(input.getDestinationPincode())
				|| !isValidPincode(input.getDestinationPincode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_DEST_PINCODE);
		// Validate Origin City
		CityTO originCity = getCityByCityCode(input.getOriginCityCode());
		if (StringUtil.isNull(originCity)) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_ORIGIN_CITY);
		}
		input.setOriginCityTO(originCity);

		// Validate Destination Pincode
		CityTO destCity = getCity(input.getDestinationPincode());
		if (StringUtil.isNull(destCity)) {
			throw new CGBusinessException(RateErrorConstants.VALIDATE_DEST_CITY);
		}
		input.setDestCityTO(destCity);

		// Weight is not provided
		if (!RateCommonConstants.PRODUCT_CODE_EMOTIONAL_BOND
				.equalsIgnoreCase(input.getProductCode())) {
			if (StringUtil.isEmptyDouble(input.getWeight()))
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_WEIGHT);
		}
		// Rate Calculation Type is not provided
		if (StringUtils.isEmpty(input.getRateType())
				|| input.getRateType() == null)
			throw new CGBusinessException(RateErrorConstants.VALIDATE_RATE_TYPE);
		// Declared value is missing for calculation of Risk Sur-charge
		if (!StringUtil.isEmptyDouble(input.getRiskSurcharge())) { // Risk
																	// Surcharge
																	// is
																	// provided
																	// as input
			if (StringUtil.isEmptyDouble(input.getDeclaredValue())) { // Declared
																		// value
																		// is
																		// not
																		// provided
																		// as
																		// input
				throw new CGBusinessException(
						RateErrorConstants.VALIDATE_DECLARED_VALUE_WHEN_RISK_SURCHARGE_PROVIDED);
			}
		}
		if (StringUtils.isEmpty(input.getCalculationRequestDate())) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_RATE_CALCULATION_DATE);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateCommonInputs() :: End --------> ::::::");
	}

	@Override
	public CityTO getCity(String Pinocde) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCity() :: Start --------> ::::::");
		CityDO cityDO = new CityDO();
		CityTO cityTO = new CityTO();
		cityDO = rateCalcDAO.getCity(Pinocde);
		if (!StringUtil.isNull(cityDO))
			cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
					cityTO);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCity() :: End --------> ::::::");
		return cityTO;
	}

	/*
	 * public abstract CGBaseDO getSlabCharge(RateCalculationInputTO input)
	 * throws CGBusinessException ;
	 */

	/**
	 * Validate Pincode number
	 * 
	 * @param pinCode
	 * @return
	 */
	public Boolean isValidPincode(String pinCode) {
		String pinCodePattern = RateCommonConstants.PIN_CODE_PATTERN;
		return pinCode.matches(pinCodePattern);
	}

	/**
	 * To Calculate Rate Depends on depending Components
	 * 
	 * @param input
	 * 
	 * @param Map
	 *            <String, RateComponentDO>
	 * @throws CGSystemException
	 */
	// @Override
	public RateCalculationOutputTO calculateComponentsRate(
			List<RateComponentDO> components, RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateComponentsRate() :: Start --------> ::::::");
		ConsignmentRateDO consignmentRateDO = null;
		RateCalculationOutputTO rateCalculationOutput = null;
		List<ConsignmentRateDO> finalCalculatedComponent = new ArrayList<ConsignmentRateDO>();
		// Checking for configured components availability
		if (components == null || components.isEmpty())
			return null;
		// Creating a container to hold component amount
		Map<String, List<RateComponentCalculatedDO>> dependentComponent = createMapForDependentComponent(components);

		List<RateComponentCalculatedDO> dependentRateComponents = null;
		// Iterating through all the configured components
		for (RateComponentDO componentToBeCalculated : components) {

			// For each component get dependent components
			dependentRateComponents = dependentComponent
					.get(componentToBeCalculated.getRateComponentCode());

			// C- Calculated component
			if ("C".equalsIgnoreCase(componentToBeCalculated
					.getRateAmountDeviationType())) {
				/** Check if Airport handling charge applicable */
				if (input.getIsAirportHandlingChrgApplicable()
						.equalsIgnoreCase(CommonConstants.NO)
						&& componentToBeCalculated
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES)) {
					componentToBeCalculated.setRateGlobalConfigValue(0.0);
				}
				// organize the component to be calculated
				consignmentRateDO = organizeComponentsToBeCalculatedAndCalculate(
						componentToBeCalculated.getRateComponentCode(),
						componentToBeCalculated, components,
						dependentRateComponents, input
						.getIsRTO());
				changeAllDependentComponents(
						consignmentRateDO.getCalculatedValue(),
						componentToBeCalculated.getRateComponentCode(),
						dependentComponent);
			} else {
				/**
				 * store the result of calculation along with the component in
				 * the list
				 */
				consignmentRateDO = new ConsignmentRateDO();
				consignmentRateDO.setActualValue(componentToBeCalculated
						.getRateGlobalConfigValue());
				Double finalValue = componentToBeCalculated
						.getRateGlobalConfigValue();
				finalValue = getRoundOffValue(finalValue); 
				//Revert TOpay charge and LC charge for RTO CN
				if (RateCommonConstants.YES.equalsIgnoreCase(input
								.getIsRTO()) && (componentToBeCalculated.getRateComponentCode()
								.equalsIgnoreCase("TPCHG") || (componentToBeCalculated.getRateComponentCode()
										.equalsIgnoreCase("LCCHG"))) && !StringUtil.isEmptyDouble(finalValue)){
					finalValue = -finalValue;
				}
				consignmentRateDO.setCalculatedValue(finalValue);
				consignmentRateDO.setRateComponentDO(componentToBeCalculated);

			}
			finalCalculatedComponent.add(consignmentRateDO);

		}
		// Convert finalCalculatedComponent into list of components in
		rateCalculationOutput = getCalculatedRateComponents(finalCalculatedComponent);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateComponentsRate() :: End --------> ::::::");
		return rateCalculationOutput;
	}

	private void changeAllDependentComponents(Double calculatedValue,
			String rateComponentCode,
			Map<String, List<RateComponentCalculatedDO>> dependentComponent) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: changeAllDependentComponents() :: Start --------> ::::::");
		for (String key : dependentComponent.keySet()) {
			List<RateComponentCalculatedDO> rateComponentCalculateds = dependentComponent
					.get(key);
			for (RateComponentCalculatedDO rateComponentCalculatedObject : rateComponentCalculateds) {
				if (rateComponentCalculatedObject
						.getRateComponentCalculatedOn().getRateComponentCode()
						.equalsIgnoreCase(rateComponentCode)) {
					rateComponentCalculatedObject
							.getRateComponentCalculatedOn()
							.setRateGlobalConfigValue(calculatedValue);
				}

			}

		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: changeAllDependentComponents() :: End --------> ::::::");
	}

	/**
	 * Organizing the components and dependent Components And calculating Final
	 * Rate
	 * 
	 * @param key
	 * @param componentToBeCalculated
	 * @param components
	 * @param dependentRateComponents
	 * @param isRTO 
	 * @return
	 */
	private ConsignmentRateDO organizeComponentsToBeCalculatedAndCalculate(
			String key, RateComponentDO componentToBeCalculated,
			List<RateComponentDO> components,
			List<RateComponentCalculatedDO> dependentRateComponents, String isRTO) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: organizeComponentsToBeCalculatedAndCalculate() :: Start --------> ::::::");
		double finalValue = 0.0;
		ConsignmentRateDO consignmentRateDO = null;
		consignmentRateDO = new ConsignmentRateDO();
		consignmentRateDO.setActualValue(componentToBeCalculated
				.getRateGlobalConfigValue());
		finalValue = componentToBeCalculated.getRateGlobalConfigValue() == null ? 0.0
				: componentToBeCalculated.getRateGlobalConfigValue();
		// Organize the dependent component for the component to be calculated
		for (RateComponentCalculatedDO dc : dependentRateComponents) {
			// calculate the final value of the component

			finalValue = getCalculatedAmount(dc.getOperationUsedInCalc(),
					finalValue, dc.getRateComponentCalculatedOn()
							.getRateGlobalConfigValue(), isRTO);
		}
		// store the result of calculation along with the component in the list
		finalValue = getRoundOffValue(finalValue); 
		// finalValue = Math.round(finalValue * 100.0)/100.0;
		consignmentRateDO.setCalculatedValue(finalValue);
		componentToBeCalculated.setRateGlobalConfigValue(finalValue);

		consignmentRateDO.setRateComponentDO(componentToBeCalculated);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: organizeComponentsToBeCalculatedAndCalculate() :: End --------> ::::::");
		return consignmentRateDO;
	}

	/**
	 * It is Creating Map for all Dependent components
	 * 
	 * @param dependentComponent
	 * @return
	 * @throws CGSystemException
	 */
	protected Map<String, List<RateComponentCalculatedDO>> createMapForDependentComponent(
			List<RateComponentDO> components) throws CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: createMapForDependentComponent() :: Start --------> ::::::");
		Map<String, List<RateComponentCalculatedDO>> dependentComponentContainer = new HashMap<String, List<RateComponentCalculatedDO>>();
		// create the dao call to get all dependent components
		List<RateComponentCalculatedDO> dependentComponent = null;
		dependentComponent = rateCalcDAO
				.getDependentRateComponentForCalculation();
		for (RateComponentCalculatedDO dc : dependentComponent) {
			List<RateComponentCalculatedDO> dpComp = dependentComponentContainer
					.get(dc.getRateComponentCalculated().getRateComponentCode());
			findComponentForDependentComponent(dc, components);
			if (dpComp == null)
				dpComp = new ArrayList<RateComponentCalculatedDO>();
			dpComp.add(dc);
			dependentComponentContainer.put(dc.getRateComponentCalculated()
					.getRateComponentCode(), dpComp);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: createMapForDependentComponent() :: End --------> ::::::");
		return dependentComponentContainer;
	}

	private void findComponentForDependentComponent(
			RateComponentCalculatedDO dc, List<RateComponentDO> components) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: findComponentForDependentComponent() :: Start --------> ::::::");
		for (RateComponentDO rateComponent : components) {
			if (rateComponent.getRateComponentCode().equalsIgnoreCase(
					dc.getRateComponentCalculatedOn().getRateComponentCode())) {
				dc.getRateComponentCalculatedOn().setRateGlobalConfigValue(
						rateComponent.getRateGlobalConfigValue());
			}
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: findComponentForDependentComponent() :: End --------> ::::::");
	}

	/**
	 * Converting
	 * 
	 * @param rateContainer
	 * @param rateComps
	 * @return
	 */
	private RateCalculationOutputTO getCalculatedRateComponents(
			List<ConsignmentRateDO> finalCalculatedComponent) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCalculatedRateComponents() :: Start --------> ::::::");
		RateCalculationOutputTO rateCalculationOutputTO = new RateCalculationOutputTO();
		List<RateComponentTO> rateComponents = new ArrayList<RateComponentTO>(
				finalCalculatedComponent.size());
		for (ConsignmentRateDO finalComponent : finalCalculatedComponent) {
			RateComponentTO rateComp = new RateComponentTO();
			if (finalComponent.getRateComponentDO().getRateComponentCode()
					.equalsIgnoreCase("GTTAX")) {
				Double value = 0.0;
				if (!StringUtil.isEmptyDouble(finalComponent
						.getCalculatedValue())){
					/*value =  new BigDecimal(finalComponent
							.getCalculatedValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();*/
					value = getRoundOffValue(finalComponent
								.getCalculatedValue());
				}
				
				if (rateType.equalsIgnoreCase("CH") && StringUtil.isStringEmpty(customerCode)) {
					Long finalVal = Math.round(value);
					value = finalVal.doubleValue();
				}

				rateComp.setCalculatedValue(value);
			} else {
				Double value = getRoundOffValue(finalComponent
						.getCalculatedValue());
				rateComp.setCalculatedValue(value);
			}
			rateComp.setRateComponentCode(finalComponent.getRateComponentDO()
					.getRateComponentCode());
			rateComp.setRateComponentDesc(finalComponent.getRateComponentDO()
					.getRateComponentDesc());
			rateComp.setActualValue(finalComponent.getActualValue());
			rateComponents.add(rateComp);
		}
		rateCalculationOutputTO.setComponents(rateComponents);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCalculatedRateComponents() :: End --------> ::::::");
		return rateCalculationOutputTO;
	}

	/**
	 * Round off calculated value up to Two decimal digit
	 * 
	 * @param calculatedValue
	 * @return
	 */
	private Double getRoundOffValue(Double calculatedValue) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getRoundOffValue() :: Start --------> ::::::");
		//DecimalFormat df = new DecimalFormat("0.000");
		Double finalValue = 0.0;
		if (!StringUtil.isEmptyDouble(calculatedValue)){
			//String formate = df.format(calculatedValue);
			//finalValue = Double.parseDouble(formate);
			finalValue = new BigDecimal(calculatedValue).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getRoundOffValue() :: End --------> ::::::");
		return finalValue;
	}

	/**
	 * Calculate the result of this operation
	 * 
	 * @param operation
	 * @param result
	 * @param operand
	 * @param isRTO 
	 */
	private Double getCalculatedAmount(String operation, Double result,
			Double operand, String isRTO) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCalculatedAmount() :: Start --------> ::::::");
		if (!RateCommonConstants.YES.equalsIgnoreCase(isRTO) && (operand == null || operand == 0)) {
			return result;
		} else if (operand == null){
			operand = 0.0;
		}
		switch (operation) {
		case RateCalculationConstants.RATE_COMPONENT_OPERATION_ADD:
			result += operand;
			break;
		case RateCalculationConstants.RATE_COMPONENT_OPERATION_SUB:
			result -= operand;
			break;
		case RateCalculationConstants.RATE_COMPONENT_OPERATION_PPERCENTILE_AND_SUBTRACT:
			if (!StringUtil.isEmptyDouble(operand))
				result -= (operand * result / 100);
			break;
		case RateCalculationConstants.RATE_COMPONENT_OPERATION_PERCENTILE:
			if (!StringUtil.isEmptyDouble(operand))
				result = (operand * result / 100);
			else if(RateCommonConstants.YES.equalsIgnoreCase(isRTO)) 
				result = (operand * result / 100);
			break;
		default:
			if (!StringUtil.isEmptyDouble(operand))
				result = operand * result / 100;
			break;

		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCalculatedAmount() :: End --------> ::::::");
		return result;
	}

	protected CityTO getCityByCityCode(String originCityCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCityByCityCode() :: Start --------> ::::::");
		CityTO cityTO = new CityTO();
		CityDO cityDO = rateCalcDAO.getCityByCityCode(originCityCode);
		if (!StringUtil.isNull(cityDO)) {
			cityTO = (CityTO) CGObjectConverter.createToFromDomain(cityDO,
					cityTO);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getCityByCityCode() :: End --------> ::::::");
		return cityTO;
	}

	/**
	 * To get Rate Tax components.
	 * 
	 * @param originPincode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	protected Map<String, Double> getTaxComponents(CityTO city,
			Date currentDate, String taxGroup) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getTaxComponents() :: Start --------> ::::::");
		// Get tax components for SEC

		List<RateTaxComponentDO> rateTaxComponents = rateCalcDAO
				.getTaxComponents(city.getState(), currentDate, taxGroup);

		Map<String, Double> taxFigures = new HashMap<>();
		for (RateTaxComponentDO rateTaxComponent : rateTaxComponents) {
			taxFigures.put(rateTaxComponent.getRateComponentCode(),
					rateTaxComponent.getTaxPercentile());
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: getTaxComponents() :: End --------> ::::::");
		return taxFigures;
	}

	public void validateCommonInputsForValidProduct(
			ProductToBeValidatedInputTO input) throws CGBusinessException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateCommonInputsForValidProduct() :: Start --------> ::::::");
		// Product Code is missing in input
		if (input.getProductCode() == null
				|| StringUtils.isEmpty(input.getProductCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_SERIES);
		// Origin is not provided
		if (input.getOriginCityCode() == null
				|| StringUtils.isEmpty(input.getOriginCityCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_ORIGIN_CITY_CODE);

		// Rate Calculation Type is not provided
		if (StringUtils.isEmpty(input.getRateType())
				|| input.getRateType() == null)
			throw new CGBusinessException(RateErrorConstants.VALIDATE_RATE_TYPE);
		// Rate Calculation Date is not provided
		if (StringUtils.isEmpty(input.getCalculationRequestDate())) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_RATE_CALCULATION_DATE);
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateCommonInputsForValidProduct() :: End --------> ::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.rate.calculation.service.RateCalculationService#calculateOCTROI
	 * (com.ff.to.rate.RateCalculationInputTO)
	 */
	@Override
	public OctroiRateCalculationOutputTO calculateOCTROI(
			RateCalculationInputTO input) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateOCTROI() :: Start --------> ::::::");
		RateCalculationOutputTO output = null;

		OctroiRateCalculationOutputTO octroiOutput = null;

		// Validating rate type inputs
		validateOctroiInputs(input);

		// Set Rate Type
		rateType = input.getRateType();

		// Get Rate Components
		List<RateComponentDO> components = getOctroiRateComponents(input);

		// Calculate the rate with all the retrieved components
		output = calculateComponentsRate(components, input);

		// Preparing octroi output to

		octroiOutput = prepareOctroiRateCalculationOutputTO(output,
				octroiOutput);
		// Set Octroi Bourne by
		octroiOutput.setOctroiBourneBy(input.getOctroiBourneBy());

		octroiOutput.setComponents(output.getComponents());
		LOGGER.debug("AbstractRateCalculationServiceImpl :: calculateOCTROI() :: End --------> ::::::");
		return octroiOutput;
	}

	/**
	 * @param output
	 * @param octroiOutput
	 * @return
	 */
	private OctroiRateCalculationOutputTO prepareOctroiRateCalculationOutputTO(
			RateCalculationOutputTO output,
			OctroiRateCalculationOutputTO octroiOutput) {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: prepareOctroiRateCalculationOutputTO() :: Start --------> ::::::");
		List<RateComponentTO> calculatedComponents = output.getComponents();
		octroiOutput = new OctroiRateCalculationOutputTO();
		for (RateComponentTO rateComponentTO : calculatedComponents) {
			switch (rateComponentTO.getRateComponentCode()) {
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI:
				octroiOutput.setOctroiServiceCharge(rateComponentTO
						.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_CODE:
				octroiOutput.setOctroi(rateComponentTO.getCalculatedValue());
				break;
			case RateCommonConstants.SERVICE_CHARGE_ON_OCTROI_CODE:
				octroiOutput.setServiceTaxOnOctroiServiceCharge(rateComponentTO
						.getCalculatedValue());
				break;
			case RateCommonConstants.EDU_CESS_ON_OCTROI_CODE:
				octroiOutput.setEduCessOnOctroiServiceCharge(rateComponentTO
						.getCalculatedValue());
				break;
			case RateCommonConstants.HIGHER_EDU_CESS_CODE:
				octroiOutput
						.setHigherEduCessOnOctroiServiceCharge(rateComponentTO
								.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_STATE_TAX_CODE:
				octroiOutput.setStateTaxOnOctroiServiceCharge(rateComponentTO
						.getCalculatedValue());
				break;
			case RateCommonConstants.OCTROI_SURCHARGE_ON_STATE_TAX_CODE:
				octroiOutput
						.setSurchargeOnStateTaxOnoctroiServiceCharge(rateComponentTO
								.getCalculatedValue());
				break;
			}
		}
		LOGGER.debug("AbstractRateCalculationServiceImpl :: prepareOctroiRateCalculationOutputTO() :: End --------> ::::::");
		return octroiOutput;
	}

	@Override
	public void validateOctroiCommonInputs(RateCalculationInputTO input)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateOctroiCommonInputs() :: Start --------> ::::::");
		// Product Code is missing in input
		if (input.getProductCode() == null
				|| StringUtils.isEmpty(input.getProductCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_PRODUCT_SERIES);
		// Consignment Type is Missing in the input
		if (input.getConsignmentType() == null
				|| StringUtils.isEmpty(input.getConsignmentType()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_CONSGN_TYPE);
		// Origin is not provided
		if (input.getOriginCityCode() == null
				|| StringUtils.isEmpty(input.getOriginCityCode()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_ORIGIN_CITY_CODE);
		// Validate Origin City
		CityTO originCity = getCityByCityCode(input.getOriginCityCode());
		if (StringUtil.isNull(originCity)) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_ORIGIN_CITY);
		}
		input.setOriginCityTO(originCity);

		// Rate Calculation Type is not provided
		if (StringUtils.isEmpty(input.getRateType())
				|| input.getRateType() == null)
			throw new CGBusinessException(RateErrorConstants.VALIDATE_RATE_TYPE);
		// Declared value is missing for calculation of Risk Sur-charge

		if (StringUtils.isEmpty(input.getCalculationRequestDate())) {
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_RATE_CALCULATION_DATE);
		}
		/** Validate Octroi Amont */
		if (StringUtil.isEmptyDouble(input.getOctroiAmount()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_OCTROI_AMOUNT);
		/** Validate Octroi State */
		if (StringUtil.isEmptyInteger(input.getOctroiState()))
			throw new CGBusinessException(
					RateErrorConstants.VALIDATE_OCTROI_STATE);
		LOGGER.debug("AbstractRateCalculationServiceImpl :: validateOctroiCommonInputs() :: End --------> ::::::");
	}

	/**
	 * @param orgSector
	 * @param destSector
	 * @return
	 */
	public String isAirportHandlingChargeApplicable(SectorDO orgSector,
			SectorDO destSector) {
		String zone = RateCalculationConstants.ZONE_NAMES;
		String isAirptHandngChargeApplicable = CommonConstants.NO;
		if (zone.contains(orgSector.getSectorName().toUpperCase())
				&& zone.contains(destSector.getSectorName().toUpperCase())
				&& (!orgSector.getSectorName().equalsIgnoreCase(
						destSector.getSectorName()))) {
			isAirptHandngChargeApplicable = CommonConstants.YES;
		}
		return isAirptHandngChargeApplicable;
	}
}
