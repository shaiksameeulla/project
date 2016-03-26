/**
 * 
 */
package com.ff.web.ratecalculator.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.geography.CityTO;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rateCalculator.RateCalculatorTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.ratecalculator.dao.RateCalculatorDAO;

/**
 * @author prmeher
 * 
 */
public class RateCalculatorServiceImpl implements RateCalculatorService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCalculatorServiceImpl.class);

	/** The rate calculator DAO. */
	private RateCalculatorDAO rateCalculatorDAO;
	
	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/**
	 * @param geographyCommonService the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductTO> getProductTypes() throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("RateCalculatorServiceImpl::getProductTypes::START------------>:::::::");
		List<ProductTO> productTOList = null;
		List<ProductDO> productDOList = rateCalculatorDAO.getAllProducts();
		if (!CGCollectionUtils.isEmpty(productDOList)) {
			productTOList = (List<ProductTO>) CGObjectConverter
					.createTOListFromDomainList(productDOList, ProductTO.class);
		}
		LOGGER.debug("RateCalculatorServiceImpl::getProductTypes::END------------>:::::::");
		return productTOList;
	}
	
	@Override
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("RateCalculatorServiceImpl::getProductTypes::START------------>:::::::");
		List<ConsignmentTypeTO> consignmentTypeTOs = new ArrayList<>();
		List<ConsignmentTypeDO> consignmanetTypeDOs = new ArrayList<>();
		ConsignmentTypeTO consignmentTypeTO;
		consignmanetTypeDOs = rateCalculatorDAO.getConsignmentType();
		for (ConsignmentTypeDO consignmanetTypeDO : consignmanetTypeDOs) {
			consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO = (ConsignmentTypeTO) CGObjectConverter
					.createToFromDomain(consignmanetTypeDO, consignmentTypeTO);
			consignmentTypeTOs.add(consignmentTypeTO);

		}
		LOGGER.debug("RateCalculatorServiceImpl::getProductTypes::END------------>:::::::");
		return consignmentTypeTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getStockStdTypeByType(String typeName)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RateCalculatorServiceImpl::getStockStdType::START------------>:::::::");
		List<StockStandardTypeDO> typeDoList = null;
		List<StockStandardTypeTO> typeToList = null;
		typeDoList = rateCalculatorDAO.getStockStdTypeByType(typeName);
		if(!StringUtil.isEmptyColletion(typeDoList)){
			typeToList = (List<StockStandardTypeTO>)CGObjectConverter.createTOListFromDomainList(typeDoList, StockStandardTypeTO.class);
		}
		LOGGER.debug("RateCalculatorServiceImpl::getStockStdType::END------------>:::::::");
		return typeToList;
	}

	

	/**
	 * @param rateCalculatorDAO
	 *            the rateCalculatorDAO to set
	 */
	public void setRateCalculatorDAO(RateCalculatorDAO rateCalculatorDAO) {
		this.rateCalculatorDAO = rateCalculatorDAO;
	}

	@Override
	public RateCalculationOutputTO calculateRates(RateCalculatorTO rateCalculatorTO, RateCalculationServiceFactory serviceFactory)
			throws CGBusinessException, CGSystemException {
		RateCalculationInputTO inputTO  = new RateCalculationInputTO();
		RateCalculationOutputTO resultTO =null;
		SetRateCalculationInputTO(inputTO, rateCalculatorTO);
		RateCalculationService rateService = serviceFactory
				.getService("CH");
		if (rateService != null) {
			resultTO = rateService
					.calculateRate(inputTO);
		}
		return resultTO;
	}
	
	private void SetRateCalculationInputTO(RateCalculationInputTO inputTO,
			RateCalculatorTO rateCalculatorTO) throws CGSystemException, CGBusinessException {
		inputTO.setProductCode(rateCalculatorTO.getProductType());
		inputTO.setConsignmentType(rateCalculatorTO.getCNtype());
		inputTO.setDestinationPincode(rateCalculatorTO.getPincode());
		Integer cityId = rateCalculatorTO.getOriginCityId();
		CityTO cityTO  = new CityTO();
		cityTO.setCityId(cityId);
		cityTO = geographyCommonService.getCity(cityTO);
		inputTO.setOriginCityCode(cityTO.getCityCode());
		inputTO.setEbPreference(rateCalculatorTO.getPreferences());
		inputTO.setServiceOn(rateCalculatorTO.getServiceAt());
		double kgs = rateCalculatorTO.getWeightKg();
		double grms = rateCalculatorTO.getWeightGrm();
		grms = grms/1000;
		double totalWeight = kgs + grms;
		inputTO.setWeight(totalWeight);
		inputTO.setRateType("CH");
		inputTO.setCalculationRequestDate(DateUtil.getCurrentDateInDDMMYYYY());
		inputTO.setDeclaredValue(rateCalculatorTO.getDeclaredValue());
		inputTO.setInsuredBy(rateCalculatorTO.getInsuredBy());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingPreferenceDetailsTO> getAllPreferenceDetails()
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("RateCalculatorServiceImpl::getAllPreferenceDetails::START------------>:::::::");
		List<BookingPreferenceDetailsTO> preferenceTOList = null;
		List<BookingPreferenceDetailsDO> preferenceDOList = rateCalculatorDAO.getAllPreferenceDetails();
		if (!CGCollectionUtils.isEmpty(preferenceDOList)) {
			preferenceTOList = (List<BookingPreferenceDetailsTO>) CGObjectConverter
					.createTOListFromDomainList(preferenceDOList, BookingPreferenceDetailsTO.class);
		}
		LOGGER.debug("RateCalculatorServiceImpl::getAllPreferenceDetails::END------------>:::::::");
		return preferenceTOList;
	}

}
