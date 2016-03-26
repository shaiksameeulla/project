/**
 * 
 */
package com.ff.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.rate.RateComponentCalculatedTO;
import com.ff.to.rate.RateComponentTO;

/**
 * @author prmeher
 *
 */
@Component
@Service("RESTRateCalculatorService")
public class RESTRateCalculatorServiceImpl implements RESTRateCalculatorService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(RESTRateCalculatorServiceImpl.class);
	
	/** RateCalculationServiceFactory */
	private RateCalculationServiceFactory rateCalculationServiceFactory;
	
	/**
	 * @param rateCalculationServiceFactory the rateCalculationServiceFactory to set
	 */
	public void setRateCalculationServiceFactory(
			RateCalculationServiceFactory rateCalculationServiceFactory) {
		this.rateCalculationServiceFactory = rateCalculationServiceFactory;
	}

	@Override
	@POST
	@Path("/getCalculatedRates")
	public ConsignmentRateCalculationOutputTO calculateRate(
			RateCalculationInputTO rateCalculationInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RESTRateCalculatorServiceImpl::calculateRate::START");
		// Rate Calculation Type is not provided
		if (StringUtils.isEmpty(rateCalculationInputTO.getRateType())
				|| rateCalculationInputTO.getRateType() == null)
			throw new CGBusinessException(RateErrorConstants.VALIDATE_RATE_TYPE);
		RateCalculationService rateService = rateCalculationServiceFactory
				.getService(rateCalculationInputTO.getRateType());
		RateCalculationOutputTO rates = rateService
				.calculateRate(rateCalculationInputTO);
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO = prepareConsignmentRateOutput(rates);
		LOGGER.debug("RESTRateCalculatorServiceImpl::calculateRate::END");
		return consignmentRateCalculationOutputTO;
	}
	
	private ConsignmentRateCalculationOutputTO prepareConsignmentRateOutput(
			RateCalculationOutputTO ratesOutputTO) {
		LOGGER.debug("RESTRateCalculatorServiceImpl::prepareConsignmentRateOutput::START");
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
				consignRateCalOutputTO.setSwachhBharatCess(rateComponent
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
		LOGGER.debug("RESTRateCalculatorServiceImpl::prepareConsignmentRateOutput::END");
		return consignRateCalOutputTO;
	}

}
