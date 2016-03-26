package com.ff.rate.configuration.rateConfiguration.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigAdditionalChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigFixedChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigHeaderProductDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateConfigRTOChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateSlabRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateSpecialDestinationDO;
import com.ff.geography.CityTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.rateConfiguration.dao.CashRateConfigurationDAO;
import com.ff.rate.configuration.ratequotation.service.RateQuotationService;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigProductTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigRTOChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateSlabRateTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateSpecialDestinationTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;

/**
 * @author hkansagr
 */

public class CashRateConfigurationServiceImpl implements CashRateConfigurationService {
	
	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashRateConfigurationServiceImpl.class);
	
	/** The cashRateConfigurationDAO. */
	private CashRateConfigurationDAO cashRateConfigurationDAO;
	
	private RateCommonService rateCommonService;
	
	private RateQuotationService rateQuotationService;
	
	public RateCommonService getRateCommonService() {
		return rateCommonService;
	}

	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}

	public void setRateQuotationService(RateQuotationService rateQuotationService) {
		this.rateQuotationService = rateQuotationService;
	}

	/**
	 * @param cashRateConfigurationDAO the cashRateConfigurationDAO to set
	 */
	public void setCashRateConfigurationDAO(
			CashRateConfigurationDAO cashRateConfigurationDAO) {
		this.cashRateConfigurationDAO = cashRateConfigurationDAO;
	}
	
	@Override
	public CashRateConfigHeaderTO saveOrUpdateCashRateProductDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException,CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateCashRateProductDtls()::START");
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		try{
			/* To check whether cash rate configuration is already exist or not */
			if(StringUtil.isEmptyInteger(to.getCashRateHeaderId()) 
					&& !StringUtil.equals(to.getIsRenew(), CommonConstants.YES)){
				boolean isCashRateConfigExist = cashRateConfigurationDAO
						.isCashRateConfigExist(to);
				if(isCashRateConfigExist) {
					throw new CGBusinessException(
							RateCommonConstants.CASH_RATE_CNF_ALREADY_EXIST);
				}
			}
			prepareCommonBaseTO(to);
			if(!StringUtil.isEmptyInteger(to.getCashRateHeaderId())){
				domain = cashRateConfigurationDAO.getCashRateConfigDetails(to.getCashRateHeaderId());
			}
			convertCashRateConfigHeaderTO2DO(to, domain);
			domain = cashRateConfigurationDAO
					.saveOrUpdateCashRateConfig(domain);
			if(!StringUtil.isEmptyInteger(domain.getCashRateHeaderId())) {
				/* To get all required id.s (PKs). - convertCashRateConfigHeaderDO2TO */
				/*if(to.getProductCatType().equals("P")){
					searchCashRateProductDtls(to);
				}*/
				domain = cashRateConfigurationDAO.getCashRateConfigDetails(domain.getCashRateHeaderId());
				convertCashRateConfigHeaderDO2TO(domain, to);
			} else {
				throw new CGBusinessException(
						RateCommonConstants.CASH_RATE_CNF_NOT_SAVED);
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::saveOrUpdateCashRateProductDtls()::"
				+ e.getMessage());
			ResourceBundle errorMessages = ResourceBundle
					.getBundle(RateCommonConstants.ERR_MSG_CNF_ADMIN_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			to.setErrorMsg(errorMsg);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateCashRateProductDtls()::END");
		return to;
	}
	/**
	 * To convert CashRateConfigHeaderTO to CashRateConfigHeaderDO
	 * 
	 * @param to
	 * @param domain
	 * @throws Exception
	 */
	private void convertCashRateConfigHeaderTO2DO(CashRateConfigHeaderTO to, 
			CashRateConfigHeaderDO domain) throws CGSystemException,CGBusinessException {
		LOGGER.trace("CashRateConfigurationServiceImpl::convertCashRateConfigHeaderTO2DO()::START");
		/* Set properties in CashRateConfigHeaderDO - Common */
		cashRateCommonDomainConverter(to, domain);
		
		Set<CashRateConfigHeaderProductDO> cashRateProductDOs = 
				new HashSet<CashRateConfigHeaderProductDO>();
		Set<CashRateSlabRateDO> cashRateSlabRateDOs = new HashSet<CashRateSlabRateDO>();
		Set<CashRateSpecialDestinationDO> cashRateSplDestDOs = new HashSet<CashRateSpecialDestinationDO>();
		
		CashRateConfigProductTO cashProductHeaderTO = to.getCommonBasedProductTO();
		/* Set properties in CashRateConfigHeaderProductDO - Common */
		CashRateConfigHeaderProductDO cashProductHeaderDO = new CashRateConfigHeaderProductDO();
		if(!CGCollectionUtils.isEmpty(domain.getCashRateProductDOs())){
			cashRateProductDOs = new CopyOnWriteArraySet<CashRateConfigHeaderProductDO>(domain.getCashRateProductDOs());
			for(CashRateConfigHeaderProductDO cspDO : cashRateProductDOs){
				if(cspDO.getHeaderProductMapId().equals(cashProductHeaderTO
						.getHeaderProductMapId())){
					if(to.getProductCatType().equals("N")){
						cashRateProductDOs.remove(cspDO);
					}else{
						cashProductHeaderDO = cspDO;
						if(!CGCollectionUtils.isEmpty(cspDO.getCashRateSlabRateDOs())){
						cashRateSlabRateDOs = new CopyOnWriteArraySet<CashRateSlabRateDO>(cspDO.getCashRateSlabRateDOs());
						String servicedOn = to.getServicedOn();
							for(CashRateSlabRateDO crsDO :  cashRateSlabRateDOs){
								if((StringUtil.isStringEmpty(servicedOn) && StringUtil.isStringEmpty(crsDO.getServicedOn())) 
										|| (!StringUtil.isStringEmpty(servicedOn) && !StringUtil.isStringEmpty( crsDO.getServicedOn()) && crsDO.getServicedOn().equals(servicedOn))){
									cashRateSlabRateDOs.remove(crsDO);
								}
							}
						}
						cashProductHeaderDO.setCashRateSlabRateDOs(cashRateSlabRateDOs);
						if(!CGCollectionUtils.isEmpty(cspDO.getCashRateSplDestDOs())){
							cashRateSplDestDOs = new CopyOnWriteArraySet<CashRateSpecialDestinationDO>(cspDO.getCashRateSplDestDOs());
							String servicedOn = to.getServicedOn();
								for(CashRateSpecialDestinationDO crsdDO :  cashRateSplDestDOs){
									if((StringUtil.isStringEmpty(servicedOn) && StringUtil.isStringEmpty(crsdDO.getServicedOn())) 
											|| (!StringUtil.isStringEmpty(servicedOn) && !StringUtil.isStringEmpty(crsdDO.getServicedOn()) && crsdDO.getServicedOn().equals(servicedOn))){
										cashRateSplDestDOs.remove(crsdDO);
									}
								}
						}
						cashProductHeaderDO.setCashRateSplDestDOs(cashRateSplDestDOs);
					}
				}
			}
		}
		
		if(!StringUtil.isNull(cashProductHeaderTO)){
			/* Product Header Map Id */
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getHeaderProductMapId())){
				cashProductHeaderDO.setHeaderProductMapId(cashProductHeaderTO
						.getHeaderProductMapId());
			}
			/* Header Reference */
			cashProductHeaderDO.setHeaderDO(domain);
			/* Minimum chargeable weight */
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getMinChargeableWeight())){
				cashProductHeaderDO.setMinChargeableWeight(cashProductHeaderTO.getMinChargeableWeight());
			}
			/* Product Id */
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getProductId())){
				RateProductCategoryDO rateProductDO = new RateProductCategoryDO();
				rateProductDO.setRateProductCategoryId(
						cashProductHeaderTO.getProductId());
				cashProductHeaderDO.setRateProductDO(rateProductDO);
			}
		}
		
		/* Prepare CashRateSlabRateDO Set - Slab Rate */
		
		
		CashRateSlabRateTO cashSlabRateTO = to.getCommonBasedSlabRateTO();
		if(!StringUtil.isNull(cashSlabRateTO)){
			int length = cashSlabRateTO.getSlabRates().length;
			for (int i=0; i<length; i++) {
				if(!StringUtil.isEmptyDouble(cashSlabRateTO.getSlabRates()[i])){
					CashRateSlabRateDO cashSlabRateDO = new CashRateSlabRateDO();
					/* Cash Slab Rate Id */
					if (!StringUtil.isEmptyInteger(cashSlabRateTO.getSlabRateIds()[i])) {
						cashSlabRateDO.setSlabRateId(cashSlabRateTO.getSlabRateIds()[i]);
					}
					/* Product Map Id */
					if (!StringUtil.isEmptyInteger(cashProductHeaderTO
							.getHeaderProductMapId())) {
						CashRateConfigHeaderProductDO rateHeaderProductDO = 
								new CashRateConfigHeaderProductDO(); 
						rateHeaderProductDO.setHeaderProductMapId(
								cashProductHeaderTO.getHeaderProductMapId());
						cashSlabRateDO.setRateHeaderProductDO(rateHeaderProductDO);
					}
					/* Weight Slab */
					WeightSlabDO weightSlabDO = new WeightSlabDO();
					weightSlabDO.setWeightSlabId(cashSlabRateTO.getWeightSlabIds()[i]);
					cashSlabRateDO.setWeightSlabDO(weightSlabDO);
					/* Origin Sector */
					if(!StringUtil.isEmptyInteger(cashSlabRateTO.getOriginSectorId())){
						cashSlabRateDO.setOriginSectorId(cashSlabRateTO
								.getOriginSectorId());
					}
					/* Destination Sector */
					cashSlabRateDO.setDestinationSectorId(cashSlabRateTO
							.getDestSectorIds()[i]);
					/* Slab Rate */
					if(!StringUtil.isEmptyDouble(cashSlabRateTO.getSlabRates()[i])){
						cashSlabRateDO.setSlabRate(cashSlabRateTO.getSlabRates()[i]);
					}
					/* Service On */
					if(to.getProductCatType().equals("P") && !StringUtil.isStringEmpty(to.getServicedOn())){
						cashSlabRateDO.setServicedOn(to.getServicedOn());
					}
					cashRateSlabRateDOs.add(cashSlabRateDO);
				}
			}
			cashProductHeaderDO.setCashRateSlabRateDOs(cashRateSlabRateDOs);
		}
		
		/* Prepare CashRateSpecialDestinationDO Set - Special Destination */
		//Set<CashRateSpecialDestinationDO> cashRateSplDestDOs = null;
		CashRateSpecialDestinationTO cashRateSplDestTO = to.getCommonBasedSplDestTO(); 
		if(!StringUtil.isNull(cashRateSplDestTO)){
			
			int j=0;
			for (int row=0; row<cashRateSplDestTO.getStateAry().length; row++) {
				if (!StringUtil.isEmptyInteger(cashRateSplDestTO.getStateAry()[row])) {
					for (int col=0; col<cashRateSplDestTO.getSplDestColCount(); col++) {
						if(!StringUtil.isEmptyInteger(cashRateSplDestTO.getStateAry()[row]) 
								&& !StringUtil.isEmptyDouble(cashRateSplDestTO.getSpecialDestRates()[j])){
							CashRateSpecialDestinationDO cashRateSplDestDO = 
									new CashRateSpecialDestinationDO();
							/* Special Destination Id */
							if (!StringUtil.isEmptyInteger(cashRateSplDestTO
									.getSpecialDestIds()[j])) {
								cashRateSplDestDO.setSpecialDestId(cashRateSplDestTO
										.getSpecialDestIds()[j]);
							}
							/* Product Map Id */
							if (!StringUtil.isEmptyInteger(cashProductHeaderTO
									.getHeaderProductMapId())) {
								CashRateConfigHeaderProductDO rateHeaderProductDO = 
										new CashRateConfigHeaderProductDO(); 
								rateHeaderProductDO.setHeaderProductMapId(
										cashProductHeaderTO.getHeaderProductMapId());
								cashRateSplDestDO.setRateHeaderProductDO(rateHeaderProductDO);
							}
							/* Weight Slab */
							WeightSlabDO weightSlabDO = new WeightSlabDO();
							weightSlabDO.setWeightSlabId(cashSlabRateTO
									.getWeightSlabIds()[col]);
							cashRateSplDestDO.setWeightSlab(weightSlabDO);
							/* CityDO */
							if(!StringUtil.isEmptyInteger(cashRateSplDestTO.getCityIds()[row])){
								CityDO cityDO = new CityDO();
								cityDO.setCityId(cashRateSplDestTO.getCityIds()[row]);
								cashRateSplDestDO.setCityDO(cityDO);
							}
							
							/* Origin Sector */
							if(!StringUtil.isEmptyInteger(cashSlabRateTO.getOriginSectorId())){
								cashRateSplDestDO.setOriginSector(cashSlabRateTO
										.getOriginSectorId());
							}
							/* Pincode */
							cashRateSplDestDO.setStateId(cashRateSplDestTO
									.getStateAry()[row]);
							/* Slab Rate */
							if(!StringUtil.isEmptyDouble(cashRateSplDestTO
									.getSpecialDestRates()[j])){
								cashRateSplDestDO.setSlabRate(cashRateSplDestTO
										.getSpecialDestRates()[j]);
							}
							/* Service On */
							if(to.getProductCatType().equals("P") && !StringUtil.isStringEmpty(to.getServicedOn())){
								cashRateSplDestDO.setServicedOn(to.getServicedOn());
							}
							cashRateSplDestDOs.add(cashRateSplDestDO);
						}/* END IF - splDestRate */
						j++;
					}/* END FOR LOOP - col */
				}/* END IF - pincode */
			}/* END FOR LOOP - row */
			cashProductHeaderDO.setCashRateSplDestDOs(cashRateSplDestDOs);
		}
		
		cashRateProductDOs.add(cashProductHeaderDO);
		domain.setCashRateProductDOs(cashRateProductDOs);
		LOGGER.trace("CashRateConfigurationServiceImpl::convertCashRateConfigHeaderTO2DO()::END");
	}
	/**
	 * To convert CashRateConfigHeaderTO to CashRateConfigHeaderDO - Common
	 * 
	 * @param to
	 * @param domain
	 * @throws Exception
	 */
	private void cashRateCommonDomainConverter(CashRateConfigHeaderTO to, 
			CashRateConfigHeaderDO domain) {
		LOGGER.trace("CashRateConfigurationServiceImpl::cashRateCommonDomainConverter()::START");
		/* Cash Rate Header Id */
		if (!StringUtil.isEmptyInteger(to.getCashRateHeaderId())) {
			domain.setCashRateHeaderId(to.getCashRateHeaderId());
		}
		/* Region */
		if (!StringUtil.isEmptyInteger(to.getRegionId())) {
			domain.setRegionId(to.getRegionId());
		}
		/* From Date */
		if(!StringUtil.isStringEmpty(to.getFromDateStr())){
			domain.setFromDate(DateUtil.stringToDDMMYYYYFormat(to.getFromDateStr()));
		}
		/* To Date */
		if(!StringUtil.isStringEmpty(to.getToDateStr())){
			domain.setToDate(DateUtil.stringToDDMMYYYYFormat(to.getToDateStr()));
		}
		/* Header Status */
		if(!StringUtil.isStringEmpty(to.getHeaderStatus())){
			domain.setHeaderStatus(to.getHeaderStatus());
			if((to.getHeaderStatus()).equalsIgnoreCase(CommonConstants.STATUS_INACTIVE)){
				domain.setDtToBranch(CommonConstants.YES);
			} else {
				domain.setDtToBranch(CommonConstants.NO);
			}
		}
		
		LOGGER.trace("CashRateConfigurationServiceImpl::cashRateCommonDomainConverter()::END");
	}
	/**
	 * To prepare common base TOs depends on product code
	 * 
	 * @param to
	 * @throws Exception
	 */
	private void prepareCommonBaseTO(CashRateConfigHeaderTO to) {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareCommonBaseTO()::START");
		CashRateSlabRateTO slabRateTO = null;
		CashRateConfigProductTO productTO = null;
		CashRateSpecialDestinationTO splDestTO = null;
		CashRateConfigFixedChargesTO fixedChrgsTO = to.getFixedChargesTO();
		CashRateConfigRTOChargesTO rtoChrgsTO = to.getRtoChargesTO();
		
		if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_COURIER)) {/* CO */
			slabRateTO = to.getCourierSlabRateTO();
			productTO = to.getCourierProductTO();
			splDestTO = to.getCourierSplDestTO();
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_AIR_CARGO)) {/* AR */
			slabRateTO = to.getAirCargoSlabRateTO();
			productTO = to.getAirCargoProductTO();
			splDestTO = to.getAirCargoSplDestTO();
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_TRAIN)) {/* TR */
			slabRateTO = to.getTrainSlabRateTO();
			productTO = to.getTrainProductTO();
			splDestTO = to.getTrainSplDestTO();
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_PRIORITY)) {/* PR */
			slabRateTO = to.getPrioritySlabRateTO();
			productTO = to.getPriorityProductTO();
			splDestTO = to.getPrioritySplDestTO();
			fixedChrgsTO = to.getPriorityFixedChargesTO();
			rtoChrgsTO = to.getPriorityRtoChargesTO();
		}
		/* Slab Rate */
		if(!StringUtil.isNull(slabRateTO)){
			to.setCommonBasedSlabRateTO(slabRateTO);
		}
		/* Product */
		if(!StringUtil.isNull(productTO)){
			to.setCommonBasedProductTO(productTO);
		}
		/* Special Destination */
		if(!StringUtil.isNull(splDestTO)){
			to.setCommonBasedSplDestTO(splDestTO);
		}
		/* Fixed Charges */
		if(!StringUtil.isNull(fixedChrgsTO)){
			to.setCommonBasedFixedChargesTO(fixedChrgsTO);
		}
		/* RTO Charges */
		if(!StringUtil.isNull(rtoChrgsTO)){
			to.setCommonBasedRtoChargesTO(rtoChrgsTO);
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareCommonBaseTO()::START");
	}
	/**
	 * To convert CashRateConfigHeaderDO to CashRateConfigHeaderTO
	 * 
	 * @param domain
	 * @param to
	 * @throws Exception
	 */
	private void convertCashRateConfigHeaderDO2TO(CashRateConfigHeaderDO domain,
			CashRateConfigHeaderTO to) throws Exception {
		LOGGER.trace("CashRateConfigurationServiceImpl::convertCashRateConfigHeaderDO2TO()::START");
		List<CityTO> cityTOList= null;
		/* Header */
		PropertyUtils.copyProperties(to, domain);
		if(!StringUtil.isEmptyInteger(domain.getCashRateHeaderId())){/* Header Id */
			to.setCashRateHeaderId(domain.getCashRateHeaderId());
		}
		if(!StringUtil.isNull(domain.getFromDate())){/* From date */
			to.setFromDateStr(DateUtil
					.getDDMMYYYYDateToString(domain.getFromDate()));
		}
		if(!StringUtil.isNull(domain.getToDate())){/* To date */
			to.setToDateStr(DateUtil
					.getDDMMYYYYDateToString(domain.getToDate()));
		}
		to.setPriorityRatesCheck("N");
		to.setNonPriorityRatesCheck("N");
		to.setPriorityBRatesCheck("N");
		to.setPriorityARatesCheck("N");
		to.setPrioritySRatesCheck("N");	
		/* Product */
		List<CashRateConfigProductTO> cashRateProductTOList = null;
		if(!StringUtil.isEmptyColletion(domain.getCashRateProductDOs())) {
			cashRateProductTOList = new ArrayList<CashRateConfigProductTO>(
					domain.getCashRateProductDOs().size());
			for(CashRateConfigHeaderProductDO productDO : domain.getCashRateProductDOs()){
				CashRateConfigProductTO productTO = new CashRateConfigProductTO();
				PropertyUtils.copyProperties(productTO, productDO);
				/* Header Id */
				if(!StringUtil.isNull(productDO.getHeaderDO())){
					if(!StringUtil.isEmptyInteger(productDO
							.getHeaderDO().getCashRateHeaderId())){
						productTO.setHeaderId(productDO.getHeaderDO()
								.getCashRateHeaderId());
					}
				}
				/* Product Category */
				if(!StringUtil.isNull(productDO.getRateProductDO())){
					if(!StringUtil.isEmptyInteger(productDO.getRateProductDO()
							.getRateProductCategoryId())){
						productTO.setProductId(productDO.getRateProductDO()
								.getRateProductCategoryId());
					}
					if(!StringUtil.isStringEmpty(productDO.getRateProductDO()
							.getRateProductCategoryCode())){
						productTO.setProductCode(productDO.getRateProductDO()
								.getRateProductCategoryCode());
						if(productDO.getRateProductDO()
								.getRateProductCategoryCode().equals("PR")){
							to.setPriorityRatesCheck("Y");													
						}else{
							to.setNonPriorityRatesCheck("Y");
						}
					}
				}
				
				Set<CashRateSlabRateDO> slabRateDOSet = productDO.getCashRateSlabRateDOs();
				/* Slab Rate */
				List<CashRateSlabRateTO> slabRateTOList = null;
				if(!StringUtil.isEmptyColletion(slabRateDOSet)){
					slabRateTOList = new ArrayList<CashRateSlabRateTO>(
							slabRateDOSet.size());
					for(CashRateSlabRateDO slabRateDO : slabRateDOSet){
						CashRateSlabRateTO slabRateTO = new CashRateSlabRateTO();
						
						PropertyUtils.copyProperties(slabRateTO, slabRateDO);
						
						/* weightSlabId */
						if(!StringUtil.isNull(slabRateDO.getWeightSlabDO())){
							if(!StringUtil.isEmptyInteger(slabRateDO
									.getWeightSlabDO().getWeightSlabId())){
								slabRateTO.setWeightSlabId(slabRateDO
										.getWeightSlabDO().getWeightSlabId());
							}
						}
						/* Product Header from slab rate */
						if(!StringUtil.isNull(slabRateDO.getRateHeaderProductDO())){
							if(!StringUtil.isEmptyInteger(slabRateDO
									.getRateHeaderProductDO().getHeaderProductMapId())){
								slabRateTO.setRateProductMapId(slabRateDO
										.getRateHeaderProductDO().getHeaderProductMapId());
							}
						}
						/* Serviced On */
						if(!StringUtil.isStringEmpty(slabRateTO.getServicedOn())){ 
							if(StringUtil.isStringEmpty(to.getServicedOn())){
								to.setServicedOn(slabRateTO.getServicedOn());
							}
							if(slabRateTO.getServicedOn().equals("B")){
								to.setPriorityBRatesCheck("Y");
							}else if(slabRateTO.getServicedOn().equals("A")){
								to.setPriorityARatesCheck("Y");
							}else if(slabRateTO.getServicedOn().equals("S")){
								to.setPrioritySRatesCheck("Y");
							}
						}
						slabRateTOList.add(slabRateTO);
					}/* END FOR LOOP - Slab Rate */
					productTO.setSlabRateTOList(slabRateTOList);
				}/* END IF - Slab Rate */
				
				/* Special Destination */
				List<CashRateSpecialDestinationTO> specialDestTOList = null;
				
				Set<CashRateSpecialDestinationDO> cashSpecialDestDOList = productDO.getCashRateSplDestDOs();
				if(!StringUtil.isEmptyColletion(cashSpecialDestDOList)){
					specialDestTOList = new ArrayList<CashRateSpecialDestinationTO>(
							cashSpecialDestDOList.size());
					
					Integer[] stateIds = new Integer[slabRateDOSet.size()];
					Integer state = null;
					int j=0;
					boolean stateExist = Boolean.FALSE;					
					Map<Integer, List<CityTO>> cityMap = new HashMap<Integer,List<CityTO>>();
					
					for(CashRateSpecialDestinationDO splDestDO : cashSpecialDestDOList){
						CashRateSpecialDestinationTO splDestTO = new CashRateSpecialDestinationTO();
						
						stateExist = Boolean.FALSE;
						
						PropertyUtils.copyProperties(splDestTO, splDestDO);
						
						
						/* weightSlabId */
						if(!StringUtil.isNull(splDestDO.getWeightSlab())){
							if(!StringUtil.isEmptyInteger(splDestDO.getWeightSlab().getWeightSlabId())){
								splDestTO.setWeightSlabId(splDestDO.getWeightSlab().getWeightSlabId());
							}
						}
						
						
						state = splDestDO.getStateId();
						for(int i=0;i<j;i++){
							if(!StringUtil.isEmptyInteger(stateIds[i]) && stateIds[i].equals(state)){
								splDestTO.setCityList(cityMap.get(state));
								stateExist = Boolean.TRUE;
								break;
							}
						}
						
						if(!stateExist){
							stateIds[j] = state;
							cityTOList = rateCommonService.getCityListByStateId(state);
							cityMap.put(splDestTO.getStateId(), cityTOList);
							if(!CGCollectionUtils.isEmpty(cityTOList)){
								splDestTO.setCityList(cityTOList);
							}
							j++;
						}
						
						/* City */
						if(!StringUtil.isNull(splDestDO.getCityDO())){
							CityTO cityTO = new CityTO();
							PropertyUtils.copyProperties(cityTO, splDestDO.getCityDO());
							splDestTO.setCityTO(cityTO);
						}
						/* Product Header from Special Destination */
						if(!StringUtil.isNull(splDestDO.getRateHeaderProductDO())){
							if(!StringUtil.isEmptyInteger(splDestDO
									.getRateHeaderProductDO().getHeaderProductMapId())){
								splDestTO.setProductMapId(splDestDO
										.getRateHeaderProductDO().getHeaderProductMapId());
							}
						}
						specialDestTOList.add(splDestTO);
					}/* END FOR LOOP - Special Destination */
					productTO.setSpecialDestTOList(specialDestTOList);
				}/* END IF - Special Destination */
				cashRateProductTOList.add(productTO);
				
			}/* END FOR LOOP - Product */ 
			to.setCashRateProductTOList(cashRateProductTOList);
		}/* END IF - Product */
		Set<CashRateConfigAdditionalChargesDO> ccacDOSet = domain.getCashRateConfigAdditionalChargesDOs();
		if(!CGCollectionUtils.isEmpty(ccacDOSet)){
			for(CashRateConfigAdditionalChargesDO acDO : ccacDOSet){
				if(acDO.getPriorityInd().equalsIgnoreCase("Y")){
					to.setPriorityChargesCheck("Y");
				}
				else if(acDO.getPriorityInd().equalsIgnoreCase("N")){
					to.setNonPriorityChargesCheck("Y");
				}
			}
		}else{
			to.setPriorityChargesCheck("N");
			to.setNonPriorityChargesCheck("N");
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::convertCashRateConfigHeaderDO2TO()::END");
	}
	
	
	@Override
	public CashRateConfigHeaderTO saveOrUpdateFixedChrgsDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateFixedChrgsDtls()::START");
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		/*Set<CashRateConfigFixedChargesDO> fixChrgDOs = 
				new HashSet<CashRateConfigFixedChargesDO>();
		Set<CashRateConfigAdditionalChargesDO> additionalChrgDOs = 
				new HashSet<CashRateConfigAdditionalChargesDO>();*/
		try{
			domain = cashRateConfigurationDAO.getCashRateConfigDetails(to.getCashRateHeaderId());
			prepareProductDOForFixedChrgs(to, domain);
			
			/*
			 * Prepare Product Map Id List to delete fixed & additional chrg detail 
			 * from database, before it gets save.
			 */
		/*	List<Integer> proMapIds = new ArrayList<Integer>(
					domain.getCashRateProductDOs().size());*/
			
			/*for(CashRateConfigHeaderProductDO productDO : domain.getCashRateProductDOs()) {
				if(!StringUtil.isEmptyInteger((productDO
						.getHeaderProductMapId()))){
					proMapIds.add(productDO.getHeaderProductMapId());
				
				if(!StringUtil.isEmptyColletion(productDO.getCashRateFixedChrgsDOs())){
					fixChrgDOs.addAll(productDO.getCashRateFixedChrgsDOs());
				}
				if(!StringUtil.isEmptyColletion(productDO.getCashRateAdditionalChrgsDOs())){
					additionalChrgDOs.addAll(productDO.getCashRateAdditionalChrgsDOs());
				}
				}
			}*/
			
			/* if list size greater than zero, then proceed to delete operation */
			/*if(proMapIds.size()>0){
				 Deleting Fixed Charges 
				cashRateConfigurationDAO.deleteFixedChrgs(proMapIds);
				 Deleting Additional Charges 
				cashRateConfigurationDAO.deleteAdditionalChrgs(proMapIds);
			}*/
			
			/*boolean result1 = cashRateConfigurationDAO
					.saveOrUpdateFixedChrgsDtls(fixChrgDOs);
			boolean result2 = cashRateConfigurationDAO
					.saveOrUpdateAdditionalChrgsDtls(additionalChrgDOs);*/
			/*if(!result1 && !result2) {
				throw new CGBusinessException();
			}*/
			cashRateConfigurationDAO
			.saveOrUpdateCashRateConfig(domain);
			
			if(to.getProductCatType().equals(RateCommonConstants.RATE_PRO_CAT_TYPE_N))
				to.setNonPriorityChargesCheck("Y");
			else if (to.getProductCatType().equals(RateCommonConstants.RATE_PRO_CAT_TYPE_P))
				to.setPriorityChargesCheck("Y");
			
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::saveOrUpdateFixedChrgsDtls()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateFixedChrgsDtls()::END");
		return to;
	}
	/**
	 * To prepare CashRateConfigHeaderProductDO for Fixed Charges
	 * 
	 * @param to
	 * @param domain
	 * @throws Exception prepareProductDOForFixedChrgs
	 */
	private void prepareProductDOForFixedChrgs(CashRateConfigHeaderTO to, 
			CashRateConfigHeaderDO domain) throws Exception {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareProductDOForFixedChrgs()::START");
		/* Set properties in CashRateConfigHeaderDO - Common */
		cashRateCommonDomainConverter(to, domain);
		
		/*Set<CashRateConfigHeaderProductDO> cashRateProductDOs = 
				new HashSet<CashRateConfigHeaderProductDO>();
		
		String productCode[] = null;
		if(StringUtil.equals(to.getProductCatType(), 
				RateCommonConstants.RATE_PRO_CAT_TYPE_N)) {// N 
			productCode = new String[]{
				RateCommonConstants.PRO_CODE_COURIER, 
				RateCommonConstants.PRO_CODE_AIR_CARGO, 
				RateCommonConstants.PRO_CODE_TRAIN };
		} else if(StringUtil.equals(to.getProductCatType(), 
				RateCommonConstants.RATE_PRO_CAT_TYPE_P)) {// P 
			productCode = new String[]{ RateCommonConstants.PRO_CODE_PRIORITY };
		}
		
		for(int i=0; i<productCode.length; i++) {
			to.setProductCode(productCode[i]);
			prepareCommonBaseTO(to);
			 Set properties in CashRateConfigHeaderProductDO - Common 
			CashRateConfigHeaderProductDO cashProductHeaderDO =
					prepareFixedChrgsDOs(to, domain);
			
			cashRateProductDOs.add(cashProductHeaderDO);
		} //END FOR LOOP 
		
		domain.setCashRateProductDOs(cashRateProductDOs);
*/
			Set<CashRateConfigAdditionalChargesDO> acDOSet =  new CopyOnWriteArraySet<CashRateConfigAdditionalChargesDO>(domain.getCashRateConfigAdditionalChargesDOs());
			if(!CGCollectionUtils.isEmpty(acDOSet)){
				for(CashRateConfigAdditionalChargesDO acDO : acDOSet){
					if(StringUtil.equals(to.getProductCatType(), 
							RateCommonConstants.RATE_PRO_CAT_TYPE_N)){
						if(acDO.getPriorityInd().equals("N")){
							acDOSet.remove(acDO);
						}
					}else if(StringUtil.equals(to.getProductCatType(), 
							RateCommonConstants.RATE_PRO_CAT_TYPE_P)){
						if(acDO.getPriorityInd().equals("Y")){
							acDOSet.remove(acDO);
						}
					}
				}
			}else{
				acDOSet = new HashSet<CashRateConfigAdditionalChargesDO>();
			}
			
			domain.setCashRateConfigAdditionalChargesDOs(acDOSet);
			Set<CashRateConfigFixedChargesDO> fcDOSet = new CopyOnWriteArraySet<CashRateConfigFixedChargesDO>(domain.getCashRateConfigFixedChargesDOs());
			if(!CGCollectionUtils.isEmpty(fcDOSet)){
				for(CashRateConfigFixedChargesDO fcDO : fcDOSet){
					if(StringUtil.equals(to.getProductCatType(), 
							RateCommonConstants.RATE_PRO_CAT_TYPE_N)){
						if(fcDO.getPriorityInd().equals("N")){
							fcDOSet.remove(fcDO);
						}
					}else if(StringUtil.equals(to.getProductCatType(), 
							RateCommonConstants.RATE_PRO_CAT_TYPE_P)){
						if(fcDO.getPriorityInd().equals("Y")){
							fcDOSet.remove(fcDO);
						}
					}
				}
			}else{
				fcDOSet = new HashSet<CashRateConfigFixedChargesDO>();
			}
			domain.setCashRateConfigFixedChargesDOs(fcDOSet);
		prepareFixedChrgsDOs(to, domain);
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareProductDOForFixedChrgs()::END");
	}
	/**
	 * To prepare CashRateConfigFixedChargesDO Set
	 * 
	 * @param to
	 * @param domain
	 * @return cashProductHeaderDO
	 * @throws Exception
	 */
	/*private CashRateConfigHeaderProductDO prepareFixedChrgsDOs(
			CashRateConfigHeaderTO to, CashRateConfigHeaderDO domain) throws Exception {*/
	private void prepareFixedChrgsDOs(
			CashRateConfigHeaderTO to, CashRateConfigHeaderDO domain) throws Exception {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareFixedChrgsDOs()::START");
		//CashRateConfigHeaderProductDO cashProductHeaderDO = new CashRateConfigHeaderProductDO();
		//CashRateConfigProductTO cashProductHeaderTO = to.getCommonBasedProductTO();
	/*	if(!StringUtil.isNull(cashProductHeaderTO)){
			 Product Header Map Id 
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getHeaderProductMapId())){
				cashProductHeaderDO.setHeaderProductMapId(cashProductHeaderTO
						.getHeaderProductMapId());
			}
			 Header Reference 
			cashProductHeaderDO.setHeaderDO(domain);
			 Product Id 
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getProductId())){
				RateProductCategoryDO rateProductDO = new RateProductCategoryDO();
				rateProductDO.setRateProductCategoryId(
						cashProductHeaderTO.getProductId());
				cashProductHeaderDO.setRateProductDO(rateProductDO);
			}
		}*/
				
		/* Prepare CashRateConfigFixedChargesDO & CashRateConfigAdditionalChargesDO Set - Fixed Charges */
		//CashRateConfigFixedChargesTO fixedChrgsTO = to.getCommonBasedFixedChargesTO();
		CashRateConfigFixedChargesTO fixedChrgsTO = null;
		if(StringUtil.equals(to.getProductCatType(), 
				RateCommonConstants.RATE_PRO_CAT_TYPE_N)){
			fixedChrgsTO = to.getFixedChargesTO();
		}else{
			fixedChrgsTO = to.getPriorityFixedChargesTO();
		}
		to.getFixedChargesTO();
		if(!StringUtil.isNull(fixedChrgsTO)){
			/* Fixed Charges */
			Set<CashRateConfigFixedChargesDO> cashRateFixedChrgsDOs = domain.getCashRateConfigFixedChargesDOs();
			if(CGCollectionUtils.isEmpty(cashRateFixedChrgsDOs)){
				cashRateFixedChrgsDOs =  new HashSet<CashRateConfigFixedChargesDO>();
			}
			if (!(StringUtil.isNull(fixedChrgsTO.getOctroiBourneByChk()))) {
				if (fixedChrgsTO.getOctroiBourneByChk()
						.equalsIgnoreCase(RateCommonConstants.ON)) {
					cashRateFixedChrgsDOs = new HashSet<CashRateConfigFixedChargesDO>();
					CashRateConfigFixedChargesDO fixedChrgsDO = new CashRateConfigFixedChargesDO();
					/* Fixed Charges Id */
					/*if(!StringUtil.isEmptyInteger(fixedChrgsTO.getCashFixedChargesConfigId())){
						fixedChrgsDO.setCashFixedChargesConfigId(fixedChrgsTO
								.getCashFixedChargesConfigId());
					}*/
					fixedChrgsDO.setOctroiBourneBy(fixedChrgsTO.getOctroiBourneBy());
					fixedChrgsDO.setCashRateHeaderDO(domain);
					if(StringUtil.equals(to.getProductCatType(), 
							RateCommonConstants.RATE_PRO_CAT_TYPE_N)){
						fixedChrgsDO.setPriorityInd("N");
					}else{
						fixedChrgsDO.setPriorityInd("Y");
					}
					//cashRateFixedChrgsDOs.add(fixedChrgsDO);
					domain.getCashRateConfigFixedChargesDOs().add(fixedChrgsDO);
				}
			}
		}
			/* Additional Charges */
			Set<CashRateConfigAdditionalChargesDO> cashRateAdditionalChrgsDOs = 
					domain.getCashRateConfigAdditionalChargesDOs();
			if(CGCollectionUtils.isEmpty(cashRateAdditionalChrgsDOs)){
				cashRateAdditionalChrgsDOs =  new HashSet<CashRateConfigAdditionalChargesDO>();
			}
			
			//fixedChrgsTO = to.getFixedChargesTO();
			String priorityInd = fixedChrgsTO.getPriorityInd();
			
			/* Fuel Surcharge Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getFuelSurchargeChk(), fixedChrgsTO.getFuelSurcharge(), 
					RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE, priorityInd,
					domain, cashRateAdditionalChrgsDOs);
			/* Other Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getOtherChargesChk(), fixedChrgsTO.getOtherCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Octroi Service Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getOctroiServiceChargesChk(), fixedChrgsTO.getOctroiServiceCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Parcel Handling Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getParcelChargesChk(), fixedChrgsTO.getParcelCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Document Handling Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getDocChargesChk(), fixedChrgsTO.getDocCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_DOCUMENT_HANDLING_CAHRGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Airport Handling Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getAirportChargesChk(), fixedChrgsTO.getAirportCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* To-Pay Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getToPayChk(), fixedChrgsTO.getToPay(),
					RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* LC Charges */
			prepareAdditionalChrgDO(fixedChrgsTO.getLcChargesChk(), fixedChrgsTO.getLcCharges(),
					RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Service Tax */
			prepareAdditionalChrgDO(fixedChrgsTO.getServiceTaxChk(), fixedChrgsTO.getServiceTax(),
					RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Education Cess */
			prepareAdditionalChrgDO(fixedChrgsTO.getEduCessChk(), fixedChrgsTO.getEduCess(),
					RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Higher Education Cess */
			prepareAdditionalChrgDO(fixedChrgsTO.getHigherEduCessChk(), fixedChrgsTO.getHigherEduCess(),
					RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* State Tax */
			prepareAdditionalChrgDO(fixedChrgsTO.getStateTaxChk(), fixedChrgsTO.getStateTax(),
					RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			/* Surcharge On ST */
			prepareAdditionalChrgDO(fixedChrgsTO.getSurchargeOnSTChk(), fixedChrgsTO.getSurchargeOnST(),
					RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST, priorityInd, 
					domain, cashRateAdditionalChrgsDOs);
			
			domain.setCashRateConfigAdditionalChargesDOs(cashRateAdditionalChrgsDOs);
			
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareFixedChrgsDOs()::END");
		//return cashProductHeaderDO;
	}
	/**
	 * To prepare CashRateConfigAdditionalChargesDO
	 * 
	 * @param componentChk
	 * @param componentValue
	 * @param componentCode
	 * @param priorityInd
	 * @param cashRateHeaderProductDO
	 * @param additionalChrgsDOs
	 * @throws Exception
	 */
	private void prepareAdditionalChrgDO(String componentChk, Double componentValue, 
		String componentCode, String priorityInd, CashRateConfigHeaderDO domain, 
			Set<CashRateConfigAdditionalChargesDO> additionalChrgsDOs) {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareAdditionalChrgDO()::START");
		CashRateConfigAdditionalChargesDO additionalChrgsDO = null;
		if (!StringUtil.isNull(componentChk)) {
			if (componentChk.equalsIgnoreCase(RateCommonConstants.ON)) {
				additionalChrgsDO = new CashRateConfigAdditionalChargesDO();
				additionalChrgsDO.setComponentValue(componentValue);
				additionalChrgsDO.setComponentCode(componentCode);
				additionalChrgsDO.setPriorityInd(priorityInd);
				additionalChrgsDO.setCashRateHeaderDO(domain);
				additionalChrgsDOs.add(additionalChrgsDO);
			}
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareAdditionalChrgDO()::END");
	}
	
	
	@Override
	public CashRateConfigHeaderTO saveOrUpdateRTOChrgsDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateRTOChrgsDtls()::START");
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		/*Set<CashRateConfigRTOChargesDO> rtoChrgDOs = 
				new HashSet<CashRateConfigRTOChargesDO>();*/
		try{
			domain = cashRateConfigurationDAO.getCashRateConfigDetails(to.getCashRateHeaderId());
			to.setIsDelReq(RateCommonConstants.NO);
			prepareProductDOForRTOChrgs(to, domain);
			
			/*
			 * Prepare Product Map Id List to delete fixed & additional chrg detail 
			 * from database, before it gets save.
			 */
			/*List<Integer> proMapIds = new ArrayList<Integer>(
					domain.getCashRateProductDOs().size());
			
			for(CashRateConfigHeaderProductDO productDO : domain.getCashRateProductDOs()) {
				if(!StringUtil.isEmptyInteger(productDO
						.getHeaderProductMapId())){
					proMapIds.add(productDO.getHeaderProductMapId());
				
				if(!StringUtil.isEmptyColletion(productDO.getCashRateRtoChrgsDOs())){
					rtoChrgDOs.addAll(productDO.getCashRateRtoChrgsDOs());
				}
				}
			}*/
			/*if(to.getIsDelReq().equals(RateCommonConstants.YES)){
				 Deleting RTO charges 
				cashRateConfigurationDAO.deleteRTOChrgs(proMapIds);
			} else {
				 Saving RTO Charges Details 
				boolean result = cashRateConfigurationDAO
					.saveOrUpdateRTOChrgsDtls(rtoChrgDOs);
				if(result) {
					 To get all required id.s (PKs). - convertCashRateHeaderDO2TOForRTOChrgs  
					List<CashRateConfigRTOChargesTO> rtoChrgTOs = 
							convertCashRateHeaderDO2TOForRTOChrgs(rtoChrgDOs);
					if(!StringUtil.isEmptyColletion(rtoChrgTOs)){
						to.setRtoChrgsTOList(rtoChrgTOs);
					}
				} else {	
					throw new CGBusinessException();
				}
			}*/
			cashRateConfigurationDAO.saveOrUpdateCashRateConfig(domain);
		} catch(Exception e) {
			LOGGER.error("Exception occurs in saveOrUpdateRTOChrgsDtls::saveOrUpdateFixedChrgsDtls()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::saveOrUpdateRTOChrgsDtls()::END");
		return to;
	}
	/**
	 * To prepare CashRateConfigHeaderProductDO for RTO Charges
	 * 
	 * @param to
	 * @param domain
	 * @throws Exception
	 */
	private void prepareProductDOForRTOChrgs(CashRateConfigHeaderTO to, 
			CashRateConfigHeaderDO domain) throws Exception {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareProductDOForRTOChrgs()::START");
		/* Set properties in CashRateConfigHeaderDO - Common */
		/*cashRateCommonDomainConverter(to, domain);
		
		Set<CashRateConfigHeaderProductDO> cashRateProductDOs = 
				new HashSet<CashRateConfigHeaderProductDO>();
		
		String productCode[] = null;
		if(StringUtil.equals(to.getProductCatType(), 
				RateCommonConstants.RATE_PRO_CAT_TYPE_N)) { //N 
			productCode = new String[]{
				RateCommonConstants.PRO_CODE_COURIER, 
				RateCommonConstants.PRO_CODE_AIR_CARGO, 
				RateCommonConstants.PRO_CODE_TRAIN };
		} else if(StringUtil.equals(to.getProductCatType(), 
				RateCommonConstants.RATE_PRO_CAT_TYPE_P)) { //P 
			productCode = new String[]{ RateCommonConstants.PRO_CODE_PRIORITY };
		}
		
		for(int i=0; i<productCode.length; i++) {
			to.setProductCode(productCode[i]);
			prepareCommonBaseTO(to);
			 Set properties in CashRateConfigHeaderProductDO - Common 
			CashRateConfigHeaderProductDO cashProductHeaderDO =
					prepareRTOChrgsDOs(to, domain);
		
			cashRateProductDOs.add(cashProductHeaderDO);
		} //END FOR Loop 
		*/
		
		Set<CashRateConfigRTOChargesDO> rtoDOSet =  new CopyOnWriteArraySet<CashRateConfigRTOChargesDO>(domain.getCashRateConfigRtoChargesDOs());
		if(!CGCollectionUtils.isEmpty(rtoDOSet)){
			for(CashRateConfigRTOChargesDO rtoDO : rtoDOSet){
				if(StringUtil.equals(to.getProductCatType(), 
						RateCommonConstants.RATE_PRO_CAT_TYPE_N)){
					if(rtoDO.getPriorityInd().equals("N")){
						rtoDOSet.remove(rtoDO);
					}
				}else if(StringUtil.equals(to.getProductCatType(), 
						RateCommonConstants.RATE_PRO_CAT_TYPE_P)){
					if(rtoDO.getPriorityInd().equals("Y")){
						rtoDOSet.remove(rtoDO);
					}
				}
			}
		}else{
			rtoDOSet = new HashSet<CashRateConfigRTOChargesDO>();
		}
		domain.setCashRateConfigRtoChargesDOs(rtoDOSet);
		prepareRTOChrgsDOs(to,domain);
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareProductDOForRTOChrgs()::END");
	}
	/**
	 * To prepare CashRateConfigRTOChargesDO Set
	 * 
	 * @param to
	 * @param domain
	 * @return cashProductHeaderDO
	 * @throws Exception
	 */
	private void prepareRTOChrgsDOs(CashRateConfigHeaderTO to, 
			CashRateConfigHeaderDO domain) throws Exception {
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareRTOChrgsDOs()::START");
		/*CashRateConfigHeaderProductDO cashProductHeaderDO = new CashRateConfigHeaderProductDO();
		CashRateConfigProductTO cashProductHeaderTO = to.getCommonBasedProductTO();*/
		/*if(!StringUtil.isNull(cashProductHeaderTO)){
			 Product Header Map Id 
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getHeaderProductMapId())){
				cashProductHeaderDO.setHeaderProductMapId(cashProductHeaderTO
						.getHeaderProductMapId());
			}
			 Header Reference 
			cashProductHeaderDO.setHeaderDO(domain);
			 Product Id 
			if(!StringUtil.isEmptyInteger(cashProductHeaderTO.getProductId())){
				RateProductCategoryDO rateProductDO = new RateProductCategoryDO();
				rateProductDO.setRateProductCategoryId(
						cashProductHeaderTO.getProductId());
				cashProductHeaderDO.setRateProductDO(rateProductDO);
			}
		}*/

		/* Prepare CashRateConfigRTOChargesDO Set - RTO Charges */
		CashRateConfigRTOChargesTO cashRateRTOChrgsTO = null;
		if(to.getProductCatType().equals("N")){
			cashRateRTOChrgsTO = to.getRtoChargesTO();
		}else{
			cashRateRTOChrgsTO = to.getPriorityRtoChargesTO();
		}
		if(!StringUtil.isNull(cashRateRTOChrgsTO)){
			Set<CashRateConfigRTOChargesDO> cashRateRTOChrgsDOs = domain.getCashRateConfigRtoChargesDOs();
			CashRateConfigRTOChargesDO cashRateRTOChrgsDO = new CashRateConfigRTOChargesDO();
			/* RTO Charges Id */
			getCommonBasedRtoChrgsId(to, cashRateRTOChrgsTO);
			if(!StringUtil.isEmptyInteger(cashRateRTOChrgsTO.getRateCashRTOChargesId()) && to.getProductCatType().equals("N")){
				cashRateRTOChrgsDO.setRateCashRTOChargesId(cashRateRTOChrgsTO
						.getRateCashRTOChargesId());
			}
			else if(!StringUtil.isEmptyInteger(cashRateRTOChrgsTO.getPrRTOChargesId()) && to.getProductCatType().equals("P")){
				cashRateRTOChrgsDO.setRateCashRTOChargesId(cashRateRTOChrgsTO
						.getPrRTOChargesId());
			}
			/* RTO Charge Applicable */
			cashRateRTOChrgsDO.setCashRateHeaderDO(domain);
			if(!StringUtil.isNull(cashRateRTOChrgsTO.getRtoChargeApplicableChk())) {
				if(cashRateRTOChrgsTO.getRtoChargeApplicableChk().equalsIgnoreCase(RateCommonConstants.ON)) {
					cashRateRTOChrgsDO.setRtoChargeApplicable(CommonConstants.YES);
				} else {
					cashRateRTOChrgsDO.setRtoChargeApplicable(CommonConstants.NO);
					/*if(!StringUtil.isEmptyInteger(cashRateRTOChrgsDO
							.getRateCashRTOChargesId())){
						to.setIsDelReq(RateCommonConstants.YES);
					}*/
				}
			} else {
				 cashRateRTOChrgsDO.setRtoChargeApplicable(CommonConstants.NO); 
				/*if(!StringUtil.isEmptyInteger(cashRateRTOChrgsDO
						.getRateCashRTOChargesId())){
					to.setIsDelReq(RateCommonConstants.YES);
				}*/
			}
			/* Same As Slab Rate */
			if(!StringUtil.isNull(cashRateRTOChrgsTO.getSameAsSlabRateChk())) {
				if(cashRateRTOChrgsTO.getSameAsSlabRateChk().equalsIgnoreCase(RateCommonConstants.ON)) {
					cashRateRTOChrgsDO.setSameAsSlabRate(CommonConstants.YES);
				} else {
					cashRateRTOChrgsDO.setSameAsSlabRate(CommonConstants.NO);
				}
			} else {
				cashRateRTOChrgsDO.setSameAsSlabRate(CommonConstants.NO);
			}
			/* Discount */
			if(!StringUtil.isEmptyDouble(cashRateRTOChrgsTO.getDiscountOnSlab())) {
				cashRateRTOChrgsDO.setDiscountOnSlab(cashRateRTOChrgsTO
						.getDiscountOnSlab());
			}
			cashRateRTOChrgsDO.setRateComponentCode(RateCommonConstants.RTO_CODE);
			//cashRateRTOChrgsDO.setCashRateHeaderDO(cashProductHeaderDO);
			cashRateRTOChrgsDO.setPriorityInd(cashRateRTOChrgsTO.getPriorityInd());
			
			cashRateRTOChrgsDOs.add(cashRateRTOChrgsDO);
			domain.setCashRateConfigRtoChargesDOs(cashRateRTOChrgsDOs);
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareRTOChrgsDOs()::END");
		//return cashProductHeaderDO;
	}
	
	/**
	 * To get common based RTO Charges Id
	 * 
	 * @param to
	 * @param rtoChrgsTO
	 * @throws Exception
	 */
	private void getCommonBasedRtoChrgsId(CashRateConfigHeaderTO to, 
			CashRateConfigRTOChargesTO rtoChrgsTO){
		LOGGER.trace("CashRateConfigurationServiceImpl::getCommonBasedRtoChrgsId()::START");
		if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_COURIER)) {/* CO */
			rtoChrgsTO.setCommonBasedRTOChargesId(
					rtoChrgsTO.getCoRTOChargesId());
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_AIR_CARGO)) {/* AR */
			rtoChrgsTO.setCommonBasedRTOChargesId(
					rtoChrgsTO.getArRTOChargesId());
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_TRAIN)) {/* TR */
			rtoChrgsTO.setCommonBasedRTOChargesId(
					rtoChrgsTO.getTrRTOChargesId());
		} else if(StringUtil.equals(to.getProductCode(), 
				RateCommonConstants.PRO_CODE_PRIORITY)) {/* PR */
			rtoChrgsTO.setCommonBasedRTOChargesId(
					rtoChrgsTO.getPrRTOChargesId());
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::getCommonBasedRtoChrgsId()::END");
	}

	@Override
	public CashRateConfigHeaderTO searchCashRateProductDtls(CashRateConfigHeaderTO to) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::searchCashRateProductDtls()::START");
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		try{
			cashRateCommonDomainConverter(to, domain);
			if(StringUtil.equals(to.getIsRenew(), RateCommonConstants.YES)){
				Date fromDate = cashRateConfigurationDAO
						.getFromDateByCashRateHeaderId(to);
				if(!StringUtil.isNull((fromDate))){
					String loggedInDate = DateUtil.getCurrentDateInDDMMYYYY();
					if(fromDate.compareTo(DateUtil.slashDelimitedstringToDDMMYYYYFormat(loggedInDate))<=0){
						domain = cashRateConfigurationDAO
							.searchCashRateProductDtlsForRenew(domain, fromDate);
					}
					if(StringUtil.isNull(domain)){
						domain = saveRenewCashRateConfiguration(to);
					}
				}
			} else {
				domain = cashRateConfigurationDAO
						.searchCashRateProductDtls(domain);
			}
			if(!StringUtil.isNull(domain)){
				convertCashRateConfigHeaderDO2TO(domain, to);
			} else {
				throw new CGBusinessException();
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::searchCashRateProductDtls()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::searchCashRateProductDtls()::END");
		return to;
	}

	@Override
	public CashRateConfigHeaderTO searchFixedChrgsDtls(Integer cashRateHeaderId, String productType) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::searchFixedChrgsDtls()::START");
		CashRateConfigHeaderTO to = new CashRateConfigHeaderTO();
		CashRateConfigFixedChargesTO fixedChrgsTO = new CashRateConfigFixedChargesTO();
		List<RateTaxComponentTO> rateTaxComponentToList = null;
		
		try{
			CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
			Set<CashRateConfigFixedChargesDO> fixChrgDOs = 
					new HashSet<CashRateConfigFixedChargesDO>();
			Set<CashRateConfigAdditionalChargesDO> additionalChrgsDOs = 
					new HashSet<CashRateConfigAdditionalChargesDO>();
				domain = cashRateConfigurationDAO.getCashRateConfigDetails(cashRateHeaderId);
				
				/* Fixed Charges config --> ff_f_rate_cash_fixed_charges_config */
				if(!StringUtil.isEmptyColletion(domain.getCashRateConfigFixedChargesDOs())){
					fixChrgDOs = domain.getCashRateConfigFixedChargesDOs();
					for(CashRateConfigFixedChargesDO fixChrgDO : fixChrgDOs){
						if(productType.equals("N") && fixChrgDO.getPriorityInd().equals("N")){
							PropertyUtils.copyProperties(fixedChrgsTO, fixChrgDO);
						//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
							fixedChrgsTO.setPriorityInd(fixChrgDO.getPriorityInd());
						//}
						}else if(productType.equals("P") && fixChrgDO.getPriorityInd().equals("Y")){
							PropertyUtils.copyProperties(fixedChrgsTO, fixChrgDO);
							//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
								fixedChrgsTO.setPriorityInd(fixChrgDO.getPriorityInd());
							//}
							}
					}
				}	
				
				/* Additional Charges --> ff_f_rate_cash_additional_charges */	
			if(!StringUtil.isEmptyColletion(domain.getCashRateConfigAdditionalChargesDOs())){
				additionalChrgsDOs = domain.getCashRateConfigAdditionalChargesDOs();
				for(CashRateConfigAdditionalChargesDO additionalChrgsDO : additionalChrgsDOs){
					if(productType.equals("N") && additionalChrgsDO.getPriorityInd().equals("N")){
					prepareFixedChrgTOFromAdditionalChrgsDO(
							additionalChrgsDO, fixedChrgsTO);
					//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
						fixedChrgsTO.setPriorityInd(additionalChrgsDO.getPriorityInd());
					//}
					}else if(productType.equals("P") && additionalChrgsDO.getPriorityInd().equals("Y")){
						prepareFixedChrgTOFromAdditionalChrgsDO(
								additionalChrgsDO, fixedChrgsTO);
						//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
							fixedChrgsTO.setPriorityInd(additionalChrgsDO.getPriorityInd());
						//}
						}
				}
			}
			
			/* Configuring charges from ff_d_rate_tax_component table
			 * 5 charges will always be picked up from this table : 
			 * 1) Service Tax
			 * 2) Education Cess Tax (Now known as Swachh Bharat Cess)
			 * 3) State Tax
			 * 4) Surcharge on state tax
			 * 5) Higher Education Cess 
			 * */
			rateTaxComponentToList = rateQuotationService.loadDefaultRateTaxComponentValue(null);
			if (!StringUtil.isEmptyColletion(rateTaxComponentToList)) {
				for (RateTaxComponentTO rateTaxComponentTo : rateTaxComponentToList) {
					if (RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX.equals(rateTaxComponentTo.getRateComponentId().getRateComponentCode())) {
						fixedChrgsTO.setServiceTax(rateTaxComponentTo.getTaxPercentile());
					}
					else if (RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS.equals(rateTaxComponentTo.getRateComponentId().getRateComponentCode())) {
						fixedChrgsTO.setEduCess(rateTaxComponentTo.getTaxPercentile());
					}
					else if (RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX.equals(rateTaxComponentTo.getRateComponentId().getRateComponentCode())) {
						fixedChrgsTO.setStateTax(rateTaxComponentTo.getTaxPercentile());
					}
					else if (RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST.equals(rateTaxComponentTo.getRateComponentId().getRateComponentCode())) {
						fixedChrgsTO.setSurchargeOnST(rateTaxComponentTo.getTaxPercentile());
					}
					else if (RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS.equals(rateTaxComponentTo.getRateComponentId().getRateComponentCode())) {
						fixedChrgsTO.setHigherEduCess(rateTaxComponentTo.getTaxPercentile());
					}
					else;
				}
			}
			
			to.setFixedChargesTO(fixedChrgsTO);
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::searchFixedChrgsDtls()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::searchFixedChrgsDtls()::END");
		return to;
	}
	/**
	 * To prepare CashRateConfigFixedChargesTO from CashRateConfigAdditionalChargesDO
	 * 
	 * @param additionalChrgsDO
	 * @param fixedChrgsTO
	 * @throws Exception
	 */
	private void prepareFixedChrgTOFromAdditionalChrgsDO(
			CashRateConfigAdditionalChargesDO additionalChrgsDO, 
				CashRateConfigFixedChargesTO fixedChrgsTO) throws Exception{
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareFixedChrgTOFromAdditionalChrgsDO()::START");
		/* Fuel Surcharge Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE)){
			fixedChrgsTO.setFuelSurcharge(additionalChrgsDO.getComponentValue());
		}
		/* Other Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES)){
			fixedChrgsTO.setOtherCharges(additionalChrgsDO.getComponentValue());
		}
		/* Octroi Service Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE)){
			fixedChrgsTO.setOctroiServiceCharges(additionalChrgsDO.getComponentValue());
		}
		/* Parcel Handling Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES)){
			fixedChrgsTO.setParcelCharges(additionalChrgsDO.getComponentValue());
		}
		/* Document Handling Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_DOCUMENT_HANDLING_CAHRGES)){
			fixedChrgsTO.setDocCharges(additionalChrgsDO.getComponentValue());
		}
		/* Airport Handling Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES)){
			fixedChrgsTO.setAirportCharges(additionalChrgsDO.getComponentValue());
		}
		/* To-Pay Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES)){
			fixedChrgsTO.setToPay(additionalChrgsDO.getComponentValue());
		}
		/* LC Charges */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_LC_CHARGES)){
			fixedChrgsTO.setLcCharges(additionalChrgsDO.getComponentValue());
		}
		/* Service Tax */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX)){
			fixedChrgsTO.setServiceTax(additionalChrgsDO.getComponentValue());
		}
		/* Education Cess */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS)){
			fixedChrgsTO.setEduCess(additionalChrgsDO.getComponentValue());
		}
		/* Higher Education Cess */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS)){
			fixedChrgsTO.setHigherEduCess(additionalChrgsDO.getComponentValue());
		}
		/* State Tax */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX)){
			fixedChrgsTO.setStateTax(additionalChrgsDO.getComponentValue());
		}
		/* Surcharge On ST */
		if(additionalChrgsDO.getComponentCode().equalsIgnoreCase(
				RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST)){
			fixedChrgsTO.setSurchargeOnST(additionalChrgsDO.getComponentValue());
		}
		LOGGER.trace("CashRateConfigurationServiceImpl::prepareFixedChrgTOFromAdditionalChrgsDO()::END");
	}
	
	@Override
	public CashRateConfigHeaderTO searchRTOChrgsDtls(Integer productMapId, String productType) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::searchRTOChrgsDtls()::START");
		CashRateConfigHeaderTO to = new CashRateConfigHeaderTO();
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		CashRateConfigRTOChargesTO rtoChrgsTO = new CashRateConfigRTOChargesTO();
		try{
			/*CashRateConfigRTOChargesDO rtoChrgsDO =	cashRateConfigurationDAO
					.searchRTOChrgsDtls(productMapId);*/
			Set<CashRateConfigRTOChargesDO> rtoChrgsDOSet = new HashSet<CashRateConfigRTOChargesDO>();
			domain = cashRateConfigurationDAO.getCashRateConfigDetails(productMapId);
			/*if(!StringUtil.isNull(rtoChrgsDO)){
				 Fixed Charges 
				PropertyUtils.copyProperties(rtoChrgsTO, rtoChrgsDO);
			}
			*/
			
			if(!StringUtil.isEmptyColletion(domain.getCashRateConfigRtoChargesDOs())){
				/* Additional Charges */
				rtoChrgsDOSet = domain.getCashRateConfigRtoChargesDOs();
				for(CashRateConfigRTOChargesDO rtoChrgDO : rtoChrgsDOSet){
					if(productType.equals("N") && rtoChrgDO.getPriorityInd().equals("N")){
						PropertyUtils.copyProperties(rtoChrgsTO, rtoChrgDO);
					//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
						rtoChrgsTO.setPriorityInd(rtoChrgDO.getPriorityInd());
					//}
					}else if(productType.equals("P") && rtoChrgDO.getPriorityInd().equals("Y")){
						PropertyUtils.copyProperties(rtoChrgsTO, rtoChrgDO);
						//if(StringUtil.isStringEmpty(fixedChrgsTO.getPriorityInd())){
						rtoChrgsTO.setPriorityInd(rtoChrgDO.getPriorityInd());
						//}
						}
				}
			}
			
			
			
			
			to.setRtoChargesTO(rtoChrgsTO);
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::searchRTOChrgsDtls()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::searchRTOChrgsDtls()::END");
		return to;
	}

	@Override
	public boolean submitCashRateDtls(Integer cashRateHeaderId, String fromDateStr, String toDateStr)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::submitCashRateDtls()::START");
		LOGGER.debug("CashRateConfigurationServiceImpl::submitCashRateDtls()::END");
		return cashRateConfigurationDAO.submitCashRateDtls(cashRateHeaderId, fromDateStr, toDateStr);
	}

	@Override
	public SectorTO getOriginSectorByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::getOriginSectorByRegionId()::START");
		SectorDO sectorDO = null;
		SectorTO sectorTO = new SectorTO();
		try{
			sectorDO = cashRateConfigurationDAO
					.getOriginSectorByRegionId(regionId);
			PropertyUtils.copyProperties(sectorTO, sectorDO);
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::getOriginSectorByRegionId()::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::getOriginSectorByRegionId()::END");
		return sectorTO;
	}
	
	@Override
	public boolean updateCashRateConfigToDate(Integer cashRateHeaderId, String toDate)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::updateCashRateConfigToDate()::START");
		LOGGER.debug("CashRateConfigurationServiceImpl::updateCashRateConfigToDate()::END");
		return cashRateConfigurationDAO.updateCashRateConfigToDate(cashRateHeaderId, toDate);
	}
	
	@Override
	public CashRateConfigHeaderTO getCurrentPeriodCashConfig(CashRateConfigHeaderTO to) 
			throws CGBusinessException,	CGSystemException {
		LOGGER.debug("CashRateConfigurationServiceImpl::getCurrentPeriodCashConfig()::START");
		CashRateConfigHeaderDO domain = new CashRateConfigHeaderDO();
		try{
			domain = cashRateConfigurationDAO
					.getCurrentPeriodCashConfig(to);
			if(!StringUtil.isNull(domain)){
				convertCashRateConfigHeaderDO2TO(domain, to);
			} else {
				throw new CGBusinessException();
			}
		} catch(Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationServiceImpl::getCurrentPeriodCashConfig()::"
				+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CashRateConfigurationServiceImpl::getCurrentPeriodCashConfig()::END");
		return to;
	}
	
	public CashRateConfigHeaderDO saveRenewCashRateConfiguration(
			CashRateConfigHeaderTO to) throws CGBusinessException,
			CGSystemException {
		CashRateConfigHeaderDO crchDO = null;
		//CashRateConfigHeaderDO copyCrchDO = null;
		crchDO = cashRateConfigurationDAO.getCashRateConfigDetails(to.getPrevCashRateHeaderId());
		copyCashRateConfigDO(crchDO);
		crchDO = cashRateConfigurationDAO.saveOrUpdateCashRateConfig(crchDO);
		return crchDO;
	}
	
	private void copyCashRateConfigDO(CashRateConfigHeaderDO crchDO) throws CGBusinessException{
		
		crchDO.setFromDate(DateUtil.getFutureDate(1));
		crchDO.setToDate(null);
		crchDO.setCashRateHeaderId(null);
		crchDO.setHeaderStatus(RateCommonConstants.STATUS_INACTIVE);
		/******************* Products ***********/
		if(!CGCollectionUtils.isEmpty(crchDO.getCashRateProductDOs())){
			Set<CashRateConfigHeaderProductDO> crchpDOSet = crchDO.getCashRateProductDOs();
			Set<CashRateConfigHeaderProductDO> productDOSet = new HashSet<CashRateConfigHeaderProductDO>(crchDO.getCashRateProductDOs().size()); 
			for(CashRateConfigHeaderProductDO crchpDO : crchpDOSet){
				crchpDO.setHeaderProductMapId(null);
				crchpDO.setHeaderDO(crchDO);
				productDOSet.add(crchpDO);
				if(!CGCollectionUtils.isEmpty(crchpDO.getCashRateSlabRateDOs())){
					Set<CashRateSlabRateDO> crsrDOSet = crchpDO.getCashRateSlabRateDOs();
					Set<CashRateSlabRateDO> slabDOSet = new HashSet<CashRateSlabRateDO>(crchpDO.getCashRateSlabRateDOs().size()); 
					for(CashRateSlabRateDO crsrDO : crsrDOSet){
						crsrDO.setSlabRateId(null);
						crsrDO.setRateHeaderProductDO(crchpDO);
						slabDOSet.add(crsrDO);
					}
					crchpDO.setCashRateSlabRateDOs(slabDOSet);
				}
				if(!CGCollectionUtils.isEmpty(crchpDO.getCashRateSplDestDOs())){
					Set<CashRateSpecialDestinationDO> crsdDOSet = crchpDO.getCashRateSplDestDOs();
					Set<CashRateSpecialDestinationDO> splDOSet = new HashSet<CashRateSpecialDestinationDO>(crchpDO.getCashRateSplDestDOs().size()); 
					for(CashRateSpecialDestinationDO crsdDO : crsdDOSet){
						crsdDO.setSpecialDestId(null);
						crsdDO.setRateHeaderProductDO(crchpDO);
						splDOSet.add(crsdDO);
					}
					crchpDO.setCashRateSplDestDOs(splDOSet);
				}
			}
		}
		
		/************ Additional Charges *********************/
		if(!CGCollectionUtils.isEmpty(crchDO.getCashRateConfigAdditionalChargesDOs())){
			Set<CashRateConfigAdditionalChargesDO> crcaDOSet = crchDO.getCashRateConfigAdditionalChargesDOs();
			Set<CashRateConfigAdditionalChargesDO> additionalChrgDOSet = new HashSet<CashRateConfigAdditionalChargesDO>(crchDO.getCashRateConfigAdditionalChargesDOs().size()); 
			for(CashRateConfigAdditionalChargesDO crcaDO : crcaDOSet){
				crcaDO.setRateCashAdditionalChrgId(null);
				crcaDO.setCashRateHeaderDO(crchDO);
				additionalChrgDOSet.add(crcaDO);
			}
			crchDO.setCashRateConfigAdditionalChargesDOs(additionalChrgDOSet);
		}
		
		/************ Config fixed Charges *********************/
		if(!CGCollectionUtils.isEmpty(crchDO.getCashRateConfigFixedChargesDOs())){
			Set<CashRateConfigFixedChargesDO> crcfcDOSet = crchDO.getCashRateConfigFixedChargesDOs();
			Set<CashRateConfigFixedChargesDO> configChrgDOSet = new HashSet<CashRateConfigFixedChargesDO>(crchDO.getCashRateConfigFixedChargesDOs().size()); 
			for(CashRateConfigFixedChargesDO crcfcDO : crcfcDOSet){
				crcfcDO.setCashFixedChargesConfigId(null);
				crcfcDO.setCashRateHeaderDO(crchDO);
				configChrgDOSet.add(crcfcDO);
			}
			crchDO.setCashRateConfigFixedChargesDOs(configChrgDOSet);
		}
		
		/************ RTO Charges *********************/
		if(!CGCollectionUtils.isEmpty(crchDO.getCashRateConfigRtoChargesDOs())){
			Set<CashRateConfigRTOChargesDO> crcrcDOSet = crchDO.getCashRateConfigRtoChargesDOs();
			Set<CashRateConfigRTOChargesDO> rtoChrgDOSet = new HashSet<CashRateConfigRTOChargesDO>(crchDO.getCashRateConfigRtoChargesDOs().size()); 
			for(CashRateConfigRTOChargesDO crcrcDO : crcrcDOSet){
				crcrcDO.setRateCashRTOChargesId(null);
				crcrcDO.setCashRateHeaderDO(crchDO);
				rtoChrgDOSet.add(crcrcDO);
			}
			crchDO.setCashRateConfigRtoChargesDOs(rtoChrgDOSet);
		}
	}
}
