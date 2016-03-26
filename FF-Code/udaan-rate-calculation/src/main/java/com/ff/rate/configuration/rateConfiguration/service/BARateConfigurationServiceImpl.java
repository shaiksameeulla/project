/**
 * 
 */
package com.ff.rate.configuration.rateConfiguration.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
import com.ff.domain.ratemanagement.operations.ba.BARateConfigAdditionalChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BARateConfigCODChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BARateConfigSpecialDestinationRateDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigProductHeaderDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigSlabRateDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigurationFixedChargesConfigDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.geography.CityTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.rateConfiguration.constants.RateConfigurationConstants;
import com.ff.rate.configuration.rateConfiguration.dao.BARateConfigurationDAO;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateCustomerProductCatMapTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BACODChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigRTOChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigSlabRateTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateHeaderTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BaRateProductTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BaRateWeightSlabTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BaSlabRateTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BaSpecialDestinationRateTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * @author prmeher
 * 
 */
public class BARateConfigurationServiceImpl implements
		BARateConfigurationService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BARateConfigurationServiceImpl.class);
	/** BA RateConfiguration DAO */
	private BARateConfigurationDAO baRateConfigurationDAO;
	private RateCommonService rateCommonService;

	public RateCommonService getRateCommonService() {
		return rateCommonService;
	}

	public void setRateCommonService(RateCommonService rateCommonService) {
		this.rateCommonService = rateCommonService;
	}

	public BARateConfigurationDAO getBaRateConfigurationDAO() {
		return baRateConfigurationDAO;
	}

	/**
	 * Gets BA types
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("BARateConfigurationServiceImpl::getStockStdType::START");
		List<StockStandardTypeDO> typeDoList = null;
		List<StockStandardTypeTO> typeToList = null;
		typeDoList = baRateConfigurationDAO.getStockStdType(typeName);
		if (!StringUtil.isEmptyColletion(typeDoList)) {
			typeToList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(typeDoList,
							StockStandardTypeTO.class);
		} else {
			LOGGER.warn("BARateConfigurationServiceImpl::getStockStdType details does not exist for type Name "
					+ typeName);
		}
		LOGGER.info("BARateConfigurationServiceImpl::getStockStdType::END");
		return typeToList;
	}

	/**
	 * @param baRateConfigurationDAO
	 *            the baRateConfigurationDAO to set
	 */
	public void setBaRateConfigurationDAO(
			BARateConfigurationDAO baRateConfigurationDAO) {
		this.baRateConfigurationDAO = baRateConfigurationDAO;
	}

	@Override
	public List<RateSectorsTO> getRateSectorListForBARateConfiguration(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("BARateConfigurationServiceImpl::getRateSectorListForBARateConfiguration::START");
		List<RateSectorsTO> rateSectorsTOList = null;
		try {
			List<RateSectorsDO> rateSectorsDOList = baRateConfigurationDAO
					.getRateSectorListForBARateConfiguration(
							baRateProductCategory, rateCustomerCategory);

			if (!CGCollectionUtils.isEmpty(rateSectorsDOList)) {
				rateSectorsTOList = new ArrayList<RateSectorsTO>();
				converterRateSectorsDOList2RateSectorsTOList(rateSectorsDOList,
						rateSectorsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BARateConfigurationServiceImpl :: getRateSectorListForBARateConfiguration ::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.info("BARateConfigurationServiceImpl::getRateSectorListForBARateConfiguration::END");
		return rateSectorsTOList;

	}

	/**
	 * get Weight Slabs
	 */

	@Override
	public List<RateWeightSlabsTO> getRateWeightSlabsList(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGBusinessException, CGSystemException {
		LOGGER.info("BARateConfigurationServiceImpl::getRateWeightSlabsList::END");
		List<RateWeightSlabsTO> rateWtSlabsTOList = null;
		try {
			List<RateWeightSlabsDO> rateWtSlabsDOList = baRateConfigurationDAO
					.getRateWeightSlabsList(baRateProductCategory,
							rateCustomerCategory);

			if (!CGCollectionUtils.isEmpty(rateWtSlabsDOList)) {
				rateWtSlabsTOList = new ArrayList<RateWeightSlabsTO>();
				converterRateWtSlabsDOList2RateWtSlabsTOList(rateWtSlabsDOList,
						rateWtSlabsTOList);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR :: BARateConfigurationServiceImpl :: getRateWeightSlabsList ::"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.info("BARateConfigurationServiceImpl::getRateWeightSlabsList::END");
		return rateWtSlabsTOList;
	}

	/**
	 * @param rateWtSlabsDOList
	 * @param rateWtSlabsTOList
	 * @throws CGBusinessException
	 */
	private void converterRateWtSlabsDOList2RateWtSlabsTOList(
			List<RateWeightSlabsDO> rateWtSlabsDOList,
			List<RateWeightSlabsTO> rateWtSlabsTOList)
			throws CGBusinessException {
		LOGGER.info("BARateConfigurationServiceImpl::converterRateWtSlabsDOList2RateWtSlabsTOList::START");
		RateWeightSlabsTO rateWtSlabsTO = null;
		WeightSlabTO wtSlabTO = null;
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateWtSlabsDOList)
				&& rateWtSlabsDOList.size() > 0) {
			for (RateWeightSlabsDO rateWtSlabsDO : rateWtSlabsDOList) {
				if (!StringUtil.isNull(rateWtSlabsDO)) {
					rateWtSlabsTO = new RateWeightSlabsTO();
					rateWtSlabsTO = (RateWeightSlabsTO) CGObjectConverter
							.createToFromDomain(rateWtSlabsDO, rateWtSlabsTO);
					if (!StringUtil.isNull(rateWtSlabsDO.getWeightSlabDO())) {
						wtSlabTO = new WeightSlabTO();
						wtSlabTO = (WeightSlabTO) CGObjectConverter
								.createToFromDomain(
										rateWtSlabsDO.getWeightSlabDO(),
										wtSlabTO);
						rateWtSlabsTO.setWeightSlabTO(wtSlabTO);
					}
					if (!StringUtil.isNull(rateWtSlabsDO
							.getRateCustomerProductCatMapDO())) {
						rcpmTO = new RateCustomerProductCatMapTO();
						rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
								.createToFromDomain(rateWtSlabsDO
										.getRateCustomerProductCatMapDO(),
										rcpmTO);
						if (!StringUtil.isNull(rateWtSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateCustomerCategoryDO())) {
							rccTO = new RateCustomerCategoryTO();
							rccTO = (RateCustomerCategoryTO) CGObjectConverter
									.createToFromDomain(rateWtSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateCustomerCategoryDO(), rccTO);
							rcpmTO.setRateCustomerCategoryTO(rccTO);
						}
						if (!StringUtil.isNull(rateWtSlabsDO
								.getRateCustomerProductCatMapDO()
								.getRateProductCategoryDO())) {
							rpcTO = new RateProductCategoryTO();
							rpcTO = (RateProductCategoryTO) CGObjectConverter
									.createToFromDomain(rateWtSlabsDO
											.getRateCustomerProductCatMapDO()
											.getRateProductCategoryDO(), rpcTO);
							rcpmTO.setRateProductCategoryTO(rpcTO);
						}
						rateWtSlabsTO.setRateCustomerProductCatMapTO(rcpmTO);
					}
					rateWtSlabsTOList.add(rateWtSlabsTO);
				}

			}
		}
		LOGGER.info("BARateConfigurationServiceImpl::converterRateWtSlabsDOList2RateWtSlabsTOList::END");
	}

	/**
	 * @param rateSectorsDOList
	 * @param rateSectorsTOList
	 * @throws CGBusinessException
	 */
	private void converterRateSectorsDOList2RateSectorsTOList(
			List<RateSectorsDO> rateSectorsDOList,
			List<RateSectorsTO> rateSectorsTOList) throws CGBusinessException {
		LOGGER.info("BARateConfigurationServiceImpl::converterRateSectorsDOList2RateSectorsTOList::START");
		RateSectorsTO rateSectorsTO = null;
		SectorTO sectorTO = null;
		RateCustomerProductCatMapTO rcpmTO = null;
		RateCustomerCategoryTO rccTO = null;
		RateProductCategoryTO rpcTO = null;
		if (!CGCollectionUtils.isEmpty(rateSectorsDOList)
				&& rateSectorsDOList.size() > 0) {
			for (RateSectorsDO rateSectorsDO : rateSectorsDOList) {
				if (!StringUtil.isNull(rateSectorsDO)) {
					rateSectorsTO = new RateSectorsTO();
					rateSectorsTO = (RateSectorsTO) CGObjectConverter
							.createToFromDomain(rateSectorsDO, rateSectorsTO);

					if (!StringUtil.isNull(rateSectorsDO.getSectorDO())) {
						sectorTO = new SectorTO();
						sectorTO = (SectorTO) CGObjectConverter
								.createToFromDomain(
										rateSectorsDO.getSectorDO(), sectorTO);
						rateSectorsTO.setSectorTO(sectorTO);
					}
					if (!StringUtil.isNull(rateSectorsDO
							.getRateCustomerProductCatMapDO())) {
						rcpmTO = new RateCustomerProductCatMapTO();
						rcpmTO = (RateCustomerProductCatMapTO) CGObjectConverter
								.createToFromDomain(rateSectorsDO
										.getRateCustomerProductCatMapDO(),
										rcpmTO);
						if (!StringUtil.isNull(rateSectorsDO
								.getRateCustomerProductCatMapDO()
								.getRateCustomerCategoryDO())) {
							rccTO = new RateCustomerCategoryTO();
							rccTO = (RateCustomerCategoryTO) CGObjectConverter
									.createToFromDomain(rateSectorsDO
											.getRateCustomerProductCatMapDO()
											.getRateCustomerCategoryDO(), rccTO);
							rcpmTO.setRateCustomerCategoryTO(rccTO);
						}
						if (!StringUtil.isNull(rateSectorsDO
								.getRateCustomerProductCatMapDO()
								.getRateProductCategoryDO())) {
							rpcTO = new RateProductCategoryTO();
							rpcTO = (RateProductCategoryTO) CGObjectConverter
									.createToFromDomain(rateSectorsDO
											.getRateCustomerProductCatMapDO()
											.getRateProductCategoryDO(), rpcTO);
							rcpmTO.setRateProductCategoryTO(rpcTO);
						}
						rateSectorsTO.setRateCustomerProductCatMapTO(rcpmTO);
					}
					rateSectorsTOList.add(rateSectorsTO);
				}

			}
		}
		LOGGER.info("BARateConfigurationServiceImpl::converterRateSectorsDOList2RateSectorsTOList::START");
	}

	/**
	 * get Sector By city Code
	 */
	@Override
	public SectorDO getSectorByCityCode(String cityCode)
			throws CGBusinessException, CGSystemException {
		return baRateConfigurationDAO.getSectorByCityCode(cityCode);
	}

	@Override
	public List<CustomerTypeDO> getBATypeList(String customerCode)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("BARateConfigurationServiceImpl::getBATypeList::START");
		List<CustomerTypeDO> baTypeList = baRateConfigurationDAO
				.getBATypeList(customerCode);

		LOGGER.trace("BARateConfigurationServiceImpl::getStockStdType::END");
		return baTypeList;
	}

	@Override
	public BARateHeaderTO saveOrUpdateBARateConfiguration(
			BARateHeaderTO baRateHeaderTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateBARateConfiguration::START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		// boolean savedStatus = false;
		BARateConfigSlabRateTO slabRateTO = null;
		Integer productCategoryId = null;
		Integer spldestColCount = null;
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			slabRateTO = baRateHeaderTO.getBaCourierSlabRateTO();
			productCategoryId = baRateHeaderTO.getProductCategoryIdForCourier();
			spldestColCount = slabRateTO.getCourierSplDestcoloumCount();
		}
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			slabRateTO = baRateHeaderTO.getBaPrioritySlabRateTO();
			productCategoryId = baRateHeaderTO
					.getProductCategoryIdForPriority();
			spldestColCount = slabRateTO.getPrioritySplDestcoloumCount();
		}
		baRateHeaderDO = baRateConfigurationDAO
				.getBARateConfigurationDetailsByHeaderId(baRateHeaderTO
						.getHeaderId());
		if (StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderDO = new BaRateConfigHeaderDO();
		}
		/*
		 * BaRateConfigProductHeaderDO baRateProductHeaderDO =
		 * domainConverterBARateHeaderTO2BARateProductHeaderDO( baRateHeaderTO,
		 * slabRateTO, baRateHeaderDO, productCategoryId, spldestColCount);
		 */
		domainConverterBARateHeaderTO2BARateProductHeaderDO(baRateHeaderTO,
				slabRateTO, baRateHeaderDO, productCategoryId, spldestColCount);
		// Search BA Configuration
		/*
		 * BaRateConfigHeaderDO headerDO = getBaSlabRatesConfigDetails(
		 * baRateHeaderTO, baRateProductHeaderDO, productCategoryId); if
		 * (!StringUtil.isNull(headerDO)) { baRateHeaderDO = headerDO; }
		 */
		baRateHeaderDO = baRateConfigurationDAO
				.saveOrUpdateBARateConfiguration(baRateHeaderDO);
		if ((baRateHeaderTO.getIsRenewWindow()
				.equalsIgnoreCase(RateCommonConstants.YES))
				&& StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())) {
			baRateConfigurationDAO.updateBAConfgRenewStatus(baRateHeaderTO
					.getOldHeaderId());
		}
		if (!StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())) {
			try {
				convertBaRateHeaderDOtoBARateHeaderTO(baRateHeaderDO,
						baRateHeaderTO);
				setFlags(baRateHeaderTO, baRateHeaderDO,
						baRateHeaderTO.getProductCategoryIdForCourier(),
						baRateHeaderTO.getProductCategoryIdForPriority());
			} catch (Exception e) {
				LOGGER.error("BARateConfigurationServiceImpl::saveOrUpdateBARateConfiguration::"
						+ e.getMessage());
			}
			baRateHeaderTO.setIsSaved("saved");
		}
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateBARateConfiguration::END");
		return baRateHeaderTO;
	}

	/**
	 * @param baRateHeaderTO
	 * @param baRateProductHeaderDO
	 * @param productCategoryId
	 * @return
	 */
	/*
	 * private BaRateConfigHeaderDO getBaSlabRatesConfigDetails( BARateHeaderTO
	 * baRateHeaderTO, BaRateConfigProductHeaderDO baRateProductHeaderDO,
	 * Integer productCategoryId) throws CGBusinessException, CGSystemException
	 * { LOGGER.trace(
	 * "BARateConfigurationServiceImpl::getBaSlabRatesConfigDetails::START");
	 * BaRateConfigHeaderDO headerDO = null; boolean flag = false; if
	 * (!StringUtil.isEmptyInteger(baRateHeaderTO.getHeaderId())) { headerDO =
	 * baRateConfigurationDAO
	 * .getBARateConfigurationDetailsByHeaderId(baRateHeaderTO .getHeaderId());
	 * } if (!StringUtil.isNull(headerDO) &&
	 * !CGCollectionUtils.isEmpty(headerDO.getBaRateProductDO())) {
	 * Set<BaRateConfigProductHeaderDO> baRateProducts = null; baRateProducts =
	 * headerDO.getBaRateProductDO(); for (BaRateConfigProductHeaderDO
	 * baRateConfigProductHeaderDO : baRateProducts) { if
	 * (!StringUtil.isEmptyInteger(baRateConfigProductHeaderDO
	 * .getRateProductCategory()) && (productCategoryId
	 * .equals(baRateConfigProductHeaderDO .getRateProductCategory()))) {
	 * baRateConfigProductHeaderDO .setBaSlabRateDO(baRateProductHeaderDO
	 * .getBaSlabRateDO()); baRateConfigProductHeaderDO
	 * .setBaSpecialDestinationRateDO(baRateProductHeaderDO
	 * .getBaSpecialDestinationRateDO());
	 * baRateProductHeaderDO.setIsSave(RateCommonConstants.YES); flag = true; }
	 * } if (!flag) { baRateProductHeaderDO.setBaRateHeaderDO(headerDO);
	 * baRateProducts.add(baRateProductHeaderDO);
	 * headerDO.setBaRateProductDO(baRateProducts); } } LOGGER.trace(
	 * "BARateConfigurationServiceImpl::getBaSlabRatesConfigDetails::END");
	 * return headerDO; }
	 */

	/**
	 * @param baRateHeaderDO
	 * @param baRateHeaderTO
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void convertBaRateHeaderDOtoBARateHeaderTO(
			BaRateConfigHeaderDO baRateHeaderDO, BARateHeaderTO baRateHeaderTO)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::convertBaRateHeaderDOtoBARateHeaderTO::START");
		baRateHeaderTO.setHeaderId(baRateHeaderDO.getHeaderId());
		baRateHeaderTO.setIsRenew(baRateHeaderDO.getIsRenew());
		// List<Integer,Integer> slabRate = new ArrayList<>();
		List<BaRateProductTO> baRateProductTOList = new ArrayList<BaRateProductTO>();
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateProductDO())) {
			for (BaRateConfigProductHeaderDO baRateHeader : baRateHeaderDO
					.getBaRateProductDO()) {
				BaRateProductTO productTO = new BaRateProductTO();
				PropertyUtils.copyProperties(productTO, baRateHeader);
				// List BaSlabRateTO
				prepareListOfBaSlabRateTO(productTO, baRateHeader);
				// List BaSpecialDestinationRateTO
				prepareListOFBaSpecialDestinationRateTO(productTO, baRateHeader);
				baRateProductTOList.add(productTO);
			}
		}
		baRateHeaderTO.setBaRateProductTOList(baRateProductTOList);
		LOGGER.trace("BARateConfigurationServiceImpl::convertBaRateHeaderDOtoBARateHeaderTO::END");
	}

	/**
	 * @param baRateHeaderTO
	 * @param baRateHeaderTO
	 * @param baRateHeaderDO
	 * @param slabRateTO
	 * @param productCategoryId
	 * @param baRateHeaderDO
	 * @param productCategoryId
	 * @param spldestColCount
	 * @return
	 */
	private void domainConverterBARateHeaderTO2BARateProductHeaderDO(
			BARateHeaderTO baRateHeaderTO, BARateConfigSlabRateTO slabRateTO,
			BaRateConfigHeaderDO baRateHeaderDO, Integer productCategoryId,
			Integer spldestColCount) {
		LOGGER.trace("BARateConfigurationServiceImpl::DomainConverterBARateHeaderTO2BARateProductHeaderDO::START");
		// Set properties in BaRateHeaderDO
		Set<BaRateConfigSlabRateDO> csrDOSet = new HashSet<BaRateConfigSlabRateDO>();
		Set<BARateConfigSpecialDestinationRateDO> csdrDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>();
		prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO(baRateHeaderTO,
				baRateHeaderDO);
		Set<BaRateConfigProductHeaderDO> baRateProductDOSet = null;
		BaRateConfigProductHeaderDO baRateProductHeaderDO = new BaRateConfigProductHeaderDO();
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateProductDO())) {
			baRateProductDOSet = new CopyOnWriteArraySet<BaRateConfigProductHeaderDO>(
					baRateHeaderDO.getBaRateProductDO());

			for (BaRateConfigProductHeaderDO rdDO : baRateProductDOSet) {
				if (rdDO.getRateProductCategory().equals(productCategoryId)) {
					if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
							RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
						baRateProductHeaderDO = rdDO;
						baRateProductDOSet.remove(rdDO);
					} else if (baRateHeaderTO
							.getProductCategory()
							.equalsIgnoreCase(
									RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
						baRateProductHeaderDO = rdDO;
						if (!CGCollectionUtils.isEmpty(rdDO.getBaSlabRateDO())) {
							csrDOSet = new CopyOnWriteArraySet<BaRateConfigSlabRateDO>(
									rdDO.getBaSlabRateDO());
							String servicedOn = slabRateTO.getServicedOn();
							for (BaRateConfigSlabRateDO csrDO : csrDOSet) {
								if (csrDO.getServicedOn().equals(servicedOn)) {
									csrDOSet.remove(csrDO);
								}
							}
						}
					}
					baRateProductHeaderDO.setBaSlabRateDO(csrDOSet);

					if (!CGCollectionUtils.isEmpty(rdDO
							.getBaSpecialDestinationRateDO())) {
						csdrDOSet = new CopyOnWriteArraySet<BARateConfigSpecialDestinationRateDO>(
								rdDO.getBaSpecialDestinationRateDO());
						String servicedOn = slabRateTO.getServicedOn();
						for (BARateConfigSpecialDestinationRateDO csdrDO : csdrDOSet) {
							if (!StringUtil.isStringEmpty(servicedOn)) {
								if (csdrDO.getServicedOn().equals(servicedOn)) {
									csdrDOSet.remove(csdrDO);
								}
							} else {
								csdrDOSet.remove(csdrDO);
							}
						}
					}
					baRateProductHeaderDO
							.setBaSpecialDestinationRateDO(csdrDOSet);
				}
			}
		} else {
			baRateProductDOSet = new HashSet<BaRateConfigProductHeaderDO>();
		}
		/*
		 * baRateProductHeaderDO.setBaRateHeaderDO(baRateHeaderDO); if
		 * (!StringUtil.isEmptyInteger(baRateHeaderTO
		 * .getCourierProductHeaderId())) {
		 * baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
		 * .getCourierProductHeaderId()); }
		 */
		if (!StringUtil.isEmptyInteger(productCategoryId)) {
			baRateProductHeaderDO.setRateProductCategory(productCategoryId);
		}
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getCourierProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getCourierProductHeaderId());
			}

		}
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getPriorityProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getPriorityProductHeaderId());
			}
		}
		baRateProductHeaderDO.setIsSave(RateCommonConstants.YES);

		// Set baSlabRateDOSet
		setBaSlabRateDOSet(slabRateTO, baRateProductHeaderDO);
		// Create set of BaSpecialDestinationRateDO
		setBaSpecialSlabRateDOSet(slabRateTO, baRateProductHeaderDO,
				spldestColCount);
		baRateProductDOSet.add(baRateProductHeaderDO);
		baRateHeaderDO.setBaRateProductDO(baRateProductDOSet);
		LOGGER.trace("BARateConfigurationServiceImpl::DomainConverterBARateHeaderTO2BARateProductHeaderDO::END");
		// return baRateProductHeaderDO;
	}

	/**
	 * @param slabRateTO
	 * @param baRateProductHeaderDO
	 * @param spldestColCount
	 */
	private void setBaSpecialSlabRateDOSet(BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			Integer spldestColCount) {
		LOGGER.trace("BARateConfigurationServiceImpl::setBaCourierSpecialSlabRateDOSet::START");
		Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDOSet = null;
		baSpecialDestinationRateDOSet = baRateProductHeaderDO
				.getBaSpecialDestinationRateDO();
		if (CGCollectionUtils.isEmpty(baSpecialDestinationRateDOSet)) {
			baSpecialDestinationRateDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>();
		}
		int k = 0;
		for (int coSplDestRow = 0; coSplDestRow < slabRateTO.getStateId().length; coSplDestRow++) {
			if (!StringUtil
					.isEmptyInteger(slabRateTO.getStateId()[coSplDestRow])) {
				for (int coSplDestCols = 0; coSplDestCols < spldestColCount; coSplDestCols++) {
					BARateConfigSpecialDestinationRateDO baSpecialDestinationRateDO = new BARateConfigSpecialDestinationRateDO();
					if (!StringUtil.isEmptyInteger(slabRateTO
							.getSpecialDestinationId()[k])) {
						baSpecialDestinationRateDO
								.setSpecialDestinationId(slabRateTO
										.getSpecialDestinationId()[k]);
					}
					if (!StringUtil
							.isEmptyInteger(slabRateTO.getCityIds()[coSplDestRow])) {
						CityDO cityDO = new CityDO();
						cityDO.setCityId(slabRateTO.getCityIds()[coSplDestRow]);
						baSpecialDestinationRateDO.setCity(cityDO);
					}
					baSpecialDestinationRateDO.setStateId(slabRateTO
							.getStateId()[coSplDestRow]);
					WeightSlabDO weightSlab = new WeightSlabDO();
					weightSlab
							.setWeightSlabId(slabRateTO.getBaWeightSlabId()[coSplDestCols]);

					baSpecialDestinationRateDO.setWeightSlab(weightSlab);
					baSpecialDestinationRateDO
							.setBaRateProductDO(baRateProductHeaderDO);
					baSpecialDestinationRateDO.setRate(slabRateTO
							.getSpecialDestinationRate()[k]);
					if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
						baSpecialDestinationRateDO.setServicedOn(slabRateTO
								.getServicedOn());
					}
					k++;
					baSpecialDestinationRateDOSet
							.add(baSpecialDestinationRateDO);
				}
			}
		}
		baRateProductHeaderDO
				.setBaSpecialDestinationRateDO(baSpecialDestinationRateDOSet);
		LOGGER.trace("BARateConfigurationServiceImpl::setBaCourierSpecialSlabRateDOSet::END");
	}

	/**
	 * @param slabRateTO
	 * @param baRateProductHeaderDO
	 */
	private void setBaSlabRateDOSet(BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO) {
		LOGGER.trace("BARateConfigurationServiceImpl::setBaCourierSlabRateDOSet::START");
		Set<BaRateConfigSlabRateDO> baSlabRateDOSet = baRateProductHeaderDO
				.getBaSlabRateDO();
		if (CGCollectionUtils.isEmpty(baSlabRateDOSet)) {
			baSlabRateDOSet = new HashSet<BaRateConfigSlabRateDO>();
		}

		int secLen = slabRateTO.getDestinationSectorId().length;
		for (int i = 0; i < secLen; i++) {
			if (!StringUtil.isEmptyDouble(slabRateTO.getRate()[i])) {
				BaRateConfigSlabRateDO baSlabRateDO = new BaRateConfigSlabRateDO();
				if (!StringUtil.isEmptyInteger(slabRateTO.getBaSlabRateid()[i])) {
					baSlabRateDO
							.setBaSlabRateId(slabRateTO.getBaSlabRateid()[i]);
				}
				baSlabRateDO.setBaRateProductDO(baRateProductHeaderDO);
				baSlabRateDO.setDestinationSector(slabRateTO
						.getDestinationSectorId()[i]);
				WeightSlabDO weightSlabDO = new WeightSlabDO();
				weightSlabDO.setWeightSlabId(slabRateTO.getBaWeightSlabId()[i]);
				baSlabRateDO.setWeightSlab(weightSlabDO);
				baSlabRateDO.setRate(slabRateTO.getRate()[i]);
				if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
					baSlabRateDO.setServicedOn(slabRateTO.getServicedOn());
				}
				baSlabRateDOSet.add(baSlabRateDO);
			}

		}
		baRateProductHeaderDO.setBaSlabRateDO(baSlabRateDOSet);
		LOGGER.trace("BARateConfigurationServiceImpl::setBaCourierSlabRateDOSet::START");
	}

	/**
	 * @param baRateHeaderTO
	 * @param baRateHeaderDO
	 */
	private void prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO(
			BARateHeaderTO baRateHeaderTO, BaRateConfigHeaderDO baRateHeaderDO) {
		LOGGER.trace("BARateConfigurationServiceImpl::prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO::START");
		if (!StringUtil.isEmptyInteger(baRateHeaderTO.getHeaderId())) {
			baRateHeaderDO.setHeaderId(baRateHeaderTO.getHeaderId());
		}
		baRateHeaderDO.setBaTypeId(baRateHeaderTO.getBaType());
		baRateHeaderDO.setCityId(baRateHeaderTO.getCityId());
		baRateHeaderDO.setToDate(DateUtil.stringToDDMMYYYYFormat(baRateHeaderTO.getToDate()));
		baRateHeaderDO.setFromDate(DateUtil.stringToDDMMYYYYFormat(baRateHeaderTO.getFrmDate()));
		if (!StringUtil.isEmptyInteger(baRateHeaderTO.getProductCategoryIdForCourier())) {
			RateCustomerCategoryDO rateCustomerCategoryDO = new RateCustomerCategoryDO();
			rateCustomerCategoryDO.setRateCustomerCategoryId(RateConfigurationConstants.BA_RATE_CUSTOMER_CATEGORY);
			baRateHeaderDO.setRateCustomerCategory(rateCustomerCategoryDO);
		}
		if (!StringUtil.isEmptyInteger(baRateHeaderTO.getBaType())) {
			CustomerTypeDO customerTypeDO = new CustomerTypeDO();
			customerTypeDO.setCustomerTypeId(baRateHeaderTO.getBaType());
			baRateHeaderDO.setCustomerTypeDO(customerTypeDO);
		}
		if (StringUtils.isEmpty(baRateHeaderTO.getHeaderStatus())) {
			baRateHeaderTO.setHeaderStatus(CommonConstants.STATUS_INACTIVE);
		}
		if (!StringUtils.isEmpty(baRateHeaderTO.getHeaderStatus())) {
			baRateHeaderDO.setHeaderStatus(baRateHeaderTO.getHeaderStatus());
			if ((baRateHeaderTO.getHeaderStatus()).equalsIgnoreCase(CommonConstants.STATUS_INACTIVE)) {
				baRateHeaderDO.setDtToBranch(CommonConstants.YES);
			} else {
				baRateHeaderDO.setDtToBranch(CommonConstants.NO);
			}
		}
		
		// Setting created by & created date fields
		if (StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())) {
			baRateHeaderDO.setCreatedBy(baRateHeaderTO.getLoggedInUserId());
			baRateHeaderDO.setCreatedDate(DateUtil.getCurrentDate());
		}
				
		// Setting updated by & updated date fields
		baRateHeaderDO.setUpdatedBy(baRateHeaderTO.getLoggedInUserId());
		baRateHeaderDO.setUpdatedDate(DateUtil.getCurrentDate());
		
		LOGGER.trace("BARateConfigurationServiceImpl::prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO::END");
	}

	@Override
	public BARateHeaderTO searchBARateConfiguration(Date fromDate, Date toDate,
			Integer cityId, Integer baTypeId, Integer courierProductId,
			Integer priorityProductId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfiguration::START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = null;
		baRateHeaderDO = baRateConfigurationDAO.getBARateConfigurationDetails(
				fromDate, toDate, cityId, baTypeId, courierProductId);

		if (!StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderTO = prepareBARateHeaderTO(baRateHeaderDO, cityId,
					baTypeId, courierProductId);
			setFlags(baRateHeaderTO, baRateHeaderDO, courierProductId,
					priorityProductId);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfiguration::END");
		return baRateHeaderTO;
	}

	/**
	 * @param baRateHeaderDO
	 * @param cityId
	 * @param baTypeId
	 * @param courierProdductId
	 * @return
	 * @throws CGBusinessException
	 */
	private BARateHeaderTO prepareBARateHeaderTO(
			BaRateConfigHeaderDO baRateHeaderDO, Integer cityId,
			Integer baTypeId, Integer productCategoryId)
			throws CGBusinessException {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateHeaderTO::START");
		BARateHeaderTO baRateHeaderTO = new BARateHeaderTO();
		List<BaRateProductTO> baRateProductTOList = null;
		try {
			if (baRateHeaderDO != null) {
				baRateHeaderTO.setHeaderId(baRateHeaderDO.getHeaderId());
				baRateHeaderTO.setBaType(baRateHeaderDO.getBaTypeId());
				baRateHeaderTO.setFrmDate(DateUtil
						.getDDMMYYYYDateToString(baRateHeaderDO.getFromDate()));
				baRateHeaderTO.setToDate(DateUtil
						.getDDMMYYYYDateToString(baRateHeaderDO.getToDate()));
				baRateHeaderTO.setCityId(baRateHeaderDO.getCityId());
				baRateHeaderTO
						.setHeaderStatus(baRateHeaderDO.getHeaderStatus());
				baRateHeaderTO.setIsRenew(baRateHeaderDO.getIsRenew());
				if (!CGCollectionUtils.isEmpty(baRateHeaderDO
						.getBaRateProductDO())) {
					for (BaRateConfigProductHeaderDO baRateProductDO : baRateHeaderDO
							.getBaRateProductDO()) {
						if (baRateProductDO.getRateProductCategory().equals(
								productCategoryId)
								&& baRateProductDO.getBaRateHeaderDO()
										.getCityId().equals(cityId)
								&& baRateProductDO.getBaRateHeaderDO()
										.getCustomerTypeDO()
										.getCustomerTypeId().equals(baTypeId)) {
							baRateProductTOList = new ArrayList<BaRateProductTO>();
							BaRateProductTO baproductTO = new BaRateProductTO();
							PropertyUtils.copyProperties(baproductTO,
									baRateProductDO);
							// List BaSlabRateTO
							//prepareListOfBaSlabRateTO(baproductTO,
							//		baRateProductDO);
							// List BaSpecialDestinationRateTO
							//prepareListOFBaSpecialDestinationRateTO(
							//		baproductTO, baRateProductDO);
							_prepareListOfBaWeightSlabRateTO(baproductTO,
									baRateProductDO, null);
							baRateProductTOList.add(baproductTO);
						}
					}

					baRateHeaderTO.setBaRateProductTOList(baRateProductTOList);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationServiceImpl :: prepareBARateHeaderTO ::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateHeaderTO::END");
		return baRateHeaderTO;
	}

	/**
	 * @param baproductTO
	 * @param baRateProductDO
	 * @throws CGBusinessException
	 *             , CGSystemException
	 */
	private void prepareListOFBaSpecialDestinationRateTO(
			BaRateProductTO baproductTO,
			BaRateConfigProductHeaderDO baRateProductDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareListOFBaSpecialDestinationRateTO::START");
		List<BaSpecialDestinationRateTO> baSplDestTOList = null;
		baSplDestTOList = new ArrayList<BaSpecialDestinationRateTO>();
		// Map<String, List<BaSpecialDestinationRateTO>> splmap = new HashMap();
		Set<BARateConfigSpecialDestinationRateDO> baSplDestDOList = null;
		List<CityTO> cityTOList = null;
		if (!CGCollectionUtils.isEmpty(baRateProductDO
				.getBaSpecialDestinationRateDO())) {

			baSplDestDOList = baRateProductDO.getBaSpecialDestinationRateDO();

			Integer[] stateIds = new Integer[baSplDestDOList.size()];
			Integer state = null;
			int j = 0;
			boolean stateExist = Boolean.FALSE;
			Map<Integer, List<CityTO>> cityMap = new HashMap<Integer, List<CityTO>>();
			for (BARateConfigSpecialDestinationRateDO baSpecialDestDO : baRateProductDO
					.getBaSpecialDestinationRateDO()) {
				stateExist = Boolean.FALSE;
				BaSpecialDestinationRateTO baSpecialDestinationRateTO = new BaSpecialDestinationRateTO();

				baSpecialDestinationRateTO = (BaSpecialDestinationRateTO) CGObjectConverter
						.createToFromDomain(baSpecialDestDO,
								baSpecialDestinationRateTO);

				baSpecialDestinationRateTO.setWeightSlabId(baSpecialDestDO
						.getWeightSlab().getWeightSlabId());

				/*
				 * if (!StringUtil.isEmptyInteger(baSpecialDestinationRateTO
				 * .getStateId())) { if
				 * (splmap.containsKey(baSpecialDestinationRateTO
				 * .getStateId())) { List<BaSpecialDestinationRateTO> spdest =
				 * splmap .get(baSpecialDestinationRateTO.getStateId());
				 * spdest.add(baSpecialDestinationRateTO);
				 * splmap.put(baSpecialDestinationRateTO
				 * .getStateId().toString(), spdest); } else {
				 * List<BaSpecialDestinationRateTO> spldest = new
				 * ArrayList<BaSpecialDestinationRateTO>();
				 * spldest.add(baSpecialDestinationRateTO);
				 * splmap.put(baSpecialDestinationRateTO
				 * .getStateId().toString(), spldest); }
				 * 
				 * }
				 */

				state = baSpecialDestinationRateTO.getStateId();

				for (int i = 0; i < j; i++) {
					if (!StringUtil.isEmptyInteger(stateIds[i])
							&& stateIds[i].equals(state)) {
						baSpecialDestinationRateTO.setCityList(cityMap
								.get(state));
						stateExist = Boolean.TRUE;
						break;
					}
				}

				if (!stateExist) {
					stateIds[j] = state;
					cityTOList = rateCommonService.getCityListByStateId(state);
					cityMap.put(baSpecialDestinationRateTO.getStateId(),
							cityTOList);
					if (!CGCollectionUtils.isEmpty(cityTOList)) {
						baSpecialDestinationRateTO.setCityList(cityTOList);
					}
					j++;
				}

				if (!StringUtil.isNull(baSpecialDestDO.getCity())) {
					CityTO cityTO = new CityTO();
					cityTO = (CityTO) CGObjectConverter.createToFromDomain(
							baSpecialDestDO.getCity(), cityTO);
					baSpecialDestinationRateTO.setCityTO(cityTO);
				}

				baSplDestTOList.add(baSpecialDestinationRateTO);
			}
			baproductTO.setBaSpecialDestinationRateTOList(baSplDestTOList);
			/*
			 * List<SpecialDestinationSlabsTO> specialSlabsList = new
			 * ArrayList<SpecialDestinationSlabsTO>(); for (String stateId :
			 * splmap.keySet()) { SpecialDestinationSlabsTO specialSlabs = new
			 * SpecialDestinationSlabsTO();
			 * specialSlabs.setStateId(Integer.parseInt(stateId));
			 * specialSlabs.setSpecialSlabs(splmap.get(stateId));
			 * specialSlabsList.add(specialSlabs); }
			 * baproductTO.setSpecialSlabsList(specialSlabsList);
			 */
		}
		LOGGER.trace("BARateConfigurationServiceImpl::prepareListOFBaSpecialDestinationRateTO::END");
	}

	/**
	 * @param baproductTO
	 * @param baRateProductDO
	 */
	private void prepareListOfBaSlabRateTO(BaRateProductTO baproductTO,
			BaRateConfigProductHeaderDO baRateProductDO) {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareListOfBaSlabRateTO::START");
		List<BaSlabRateTO> baSlabTOList = null;
		baSlabTOList = new ArrayList<BaSlabRateTO>();
		if (!CGCollectionUtils.isEmpty(baRateProductDO.getBaSlabRateDO())) {
			for (BaRateConfigSlabRateDO baSlabRateDO : baRateProductDO
					.getBaSlabRateDO()) {
				BaSlabRateTO baSlabRateTO = new BaSlabRateTO();
				try {
					PropertyUtils.copyProperties(baSlabRateTO, baSlabRateDO);
				} catch (Exception e) {
					LOGGER.error(
							"ERROR :: BARateConfigurationServiceImpl::prepareListOfBaSlabRateTO::",
							e);
				}
				baSlabRateTO.setWeightSlabId(baSlabRateDO.getWeightSlab()
						.getWeightSlabId());
				baSlabTOList.add(baSlabRateTO);
			}
			baproductTO.setBaSlabRateList(baSlabTOList);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::prepareListOfBaSlabRateTO::END");
	}

	@Override
	public BARateHeaderTO saveOrUpdateFixedCharges(
			BARateHeaderTO baRateHeaderTO, List<BACODChargesTO> codList)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateFixedChargesForCourier::START");
		// Set<BaRateConfigProductHeaderDO> baRateProductDOSet = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;
		// boolean savedStatus = false;
		// Integer productCategoryId = null;
		BARateConfigFixedChargesTO fixedCharges = null;
		BaRateConfigProductHeaderDO baRateProductHeaderDO = new BaRateConfigProductHeaderDO();

		baRateHeaderDO = baRateConfigurationDAO.getBARateConfigurationDetailsByHeaderId(baRateHeaderTO.getHeaderId());
		if (StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderDO = new BaRateConfigHeaderDO();
		}

		prepareBAProductHeaderDO(baRateHeaderTO, baRateHeaderDO, baRateProductHeaderDO);

		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			fixedCharges = baRateHeaderTO.getBaCourierFixedChargesTO();
			// productCategoryId =
			// baRateHeaderTO.getProductCategoryIdForCourier();
		}
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			fixedCharges = baRateHeaderTO.getBaPriorityFixedChargesTO();
			// productCategoryId = baRateHeaderTO
			// .getProductCategoryIdForPriority();
		}
		prepareBARateFixedChargeDOList(baRateHeaderTO, baRateProductHeaderDO, baRateHeaderDO, fixedCharges, codList);

		RateCustomerCategoryDO rateCustomerCategoryDO = new RateCustomerCategoryDO();
		rateCustomerCategoryDO.setRateCustomerCategoryId(RateConfigurationConstants.BA_RATE_CUSTOMER_CATEGORY);
		baRateHeaderDO.setRateCustomerCategory(rateCustomerCategoryDO);
		// baRateProductDOSet = new HashSet<>();
		// baRateProductDOSet.add(baRateProductHeaderDO);
		// baRateHeaderDO.setBaRateProductDO(baRateProductDOSet);
		if ((baRateHeaderTO.getIsRenewWindow()
				.equalsIgnoreCase(RateCommonConstants.YES))
				&& StringUtil.isEmptyInteger(baRateHeaderTO.getHeaderId())) {
			baRateConfigurationDAO.updateBAConfgRenewStatus(baRateHeaderTO
					.getOldHeaderId());
		}
		// Search BA Configuration
		// BaRateConfigHeaderDO headerDO = setBaConfigDetails(baRateHeaderTO,
		// baRateProductHeaderDO, productCategoryId);
		/*
		 * if (!StringUtil.isNull(headerDO)) { baRateHeaderDO = headerDO; }
		 */
		baRateHeaderDO = baRateConfigurationDAO.saveOrUpdateBARateConfiguration(baRateHeaderDO);
		if (!StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())) {
			convertProductHeaderDOtoBaRateProductTO(baRateHeaderTO, baRateHeaderDO);
			setFlags(baRateHeaderTO, baRateHeaderDO,
					baRateHeaderTO.getProductCategoryIdForCourier(),
					baRateHeaderTO.getProductCategoryIdForPriority());
		}
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateFixedChargesForCourier::END");
		return baRateHeaderTO;
	}

	/**
	 * @param baRateHeaderTO
	 * @param baRateHeaderDO
	 * @param baRateProductHeaderDO
	 */
	private void prepareBAProductHeaderDO(BARateHeaderTO baRateHeaderTO,
			BaRateConfigHeaderDO baRateHeaderDO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO) {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBAProductHeaderDO::START");
		prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO(baRateHeaderTO,
				baRateHeaderDO);
		/*
		 * baRateProductHeaderDO.setBaRateHeaderDO(baRateHeaderDO); if
		 * (!StringUtil.isEmptyInteger(baRateHeaderTO
		 * .getCourierProductHeaderId())) {
		 * baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
		 * .getCourierProductHeaderId()); } if
		 * (!StringUtil.isEmptyInteger(baRateHeaderTO
		 * .getProductCategoryIdForCourier())) {
		 * baRateProductHeaderDO.setRateProductCategory(baRateHeaderTO
		 * .getProductCategoryIdForCourier()); }
		 */

		/*
		 * Set<BaRateConfigProductHeaderDO> phDOset = new
		 * CopyOnWriteArraySet<BaRateConfigProductHeaderDO
		 * >(baRateHeaderDO.getBaRateProductDO());
		 * if(!CGCollectionUtils.isEmpty(phDOset)){
		 * for(BaRateConfigProductHeaderDO phDO : phDOset){
		 * if(phDO.getBaProductHeaderId
		 * ().equals(baRateHeaderTO.getCourierProductHeaderId())){
		 * baRateProductHeaderDO = phDO; phDOset.remove(phDO); } } }else{
		 * phDOset = new HashSet<BaRateConfigProductHeaderDO>(); }
		 * phDOset.add(baRateProductHeaderDO);
		 * baRateHeaderDO.setBaRateProductDO(phDOset);
		 */
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBAProductHeaderDO::END");
	}

	/**
	 * prepare Ba fixed charges DO list
	 * 
	 * @param baRateHeaderTO
	 * @param baRateProductHeaderDO
	 */
	private void prepareBARateFixedChargeDOList(BARateHeaderTO baRateHeaderTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			BaRateConfigHeaderDO baRateHeaderDO,
			BARateConfigFixedChargesTO baFixedChargesTO,
			List<BACODChargesTO> codList) {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateFixedChargeDOList::START");

		Set<BARateConfigCODChargesDO> baCODSet = new HashSet<BARateConfigCODChargesDO>();
		/*
		 * Set<BaRateConfigProductHeaderDO> phDOset = new
		 * CopyOnWriteArraySet<BaRateConfigProductHeaderDO
		 * >(baRateHeaderDO.getBaRateProductDO());
		 * 
		 * if(!CGCollectionUtils.isEmpty(phDOset)){
		 * for(BaRateConfigProductHeaderDO phDO : phDOset){ if
		 * (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
		 * RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
		 * if(phDO.getBaProductHeaderId
		 * ().equals(baRateHeaderTO.getCourierProductHeaderId())){
		 * baRateProductHeaderDO = phDO; //phDOset.remove(phDO); } }else if
		 * (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
		 * RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
		 * if(phDO.getBaProductHeaderId
		 * ().equals(baRateHeaderTO.getPriorityProductHeaderId())){
		 * baRateProductHeaderDO = phDO; } } } }else{ phDOset = new
		 * HashSet<BaRateConfigProductHeaderDO>(); }
		 * phDOset.add(baRateProductHeaderDO);
		 * baRateHeaderDO.setBaRateProductDO(phDOset);
		 */
		// Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDOs = null;

		Set<BARateConfigAdditionalChargesDO> baAdditionalChargesDOs = new CopyOnWriteArraySet<BARateConfigAdditionalChargesDO>(
				baRateHeaderDO.getBaAdditionalChargesDO());
		if (!CGCollectionUtils.isEmpty(baAdditionalChargesDOs)) {
			for (BARateConfigAdditionalChargesDO acDO : baAdditionalChargesDOs) {
				if (StringUtil.equals(baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.NO)) {
					if (acDO.getPriorityIndicator().equals(CommonConstants.NO)) {
						baAdditionalChargesDOs.remove(acDO);
					}
				} else if (StringUtil.equals(
						baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.YES)) {
					if (acDO.getPriorityIndicator().equals(CommonConstants.YES)) {
						baAdditionalChargesDOs.remove(acDO);
					}
				}
			}
		} else {
			baAdditionalChargesDOs = new HashSet<BARateConfigAdditionalChargesDO>();
		}

		/*
		 * if(!StringUtil.isNull(baRateHeaderDO.getBaAdditionalChargesDO())){
		 * baAdditionalChargesDOs = new
		 * CopyOnWriteArraySet<BARateConfigAdditionalChargesDO
		 * >(baRateHeaderDO.getBaAdditionalChargesDO()); }
		 * if(!CGCollectionUtils.isEmpty(baAdditionalChargesDOs)){
		 * for(BARateConfigAdditionalChargesDO acDO : baAdditionalChargesDOs){
		 * if
		 * (acDO.getBaRateConfigHeaderDO().getHeaderId().equals(baRateHeaderDO.
		 * getHeaderId())){ baAdditionalChargesDOs.remove(acDO); } } }else{
		 * baAdditionalChargesDOs = new
		 * HashSet<BARateConfigAdditionalChargesDO>(); }
		 */
		Set<BARateConfigurationFixedChargesConfigDO> baFixedConfig = null;
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getFixedChargeConfig())) {
			baFixedConfig = new CopyOnWriteArraySet<BARateConfigurationFixedChargesConfigDO>(
					baRateHeaderDO.getFixedChargeConfig());
		}

		if (!CGCollectionUtils.isEmpty(baFixedConfig)) {
			for (BARateConfigurationFixedChargesConfigDO fcDO : baFixedConfig) {
				if (StringUtil.equals(baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.NO)) {
					if (fcDO.getPriorityIndicator().equals(CommonConstants.NO)) {
						baFixedConfig.remove(fcDO);
					}
				} else if (StringUtil.equals(
						baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.YES)) {
					if (fcDO.getPriorityIndicator().equals(CommonConstants.YES)) {
						baFixedConfig.remove(fcDO);
					}
				}
			}
		} else {
			baFixedConfig = new HashSet<BARateConfigurationFixedChargesConfigDO>();
		}

		/*
		 * if(!CGCollectionUtils.isEmpty(baFixedConfig)){
		 * for(BARateConfigurationFixedChargesConfigDO fcDO : baFixedConfig){
		 * if(
		 * fcDO.getBaRateConfigHeaderDO().getHeaderId().equals(baRateHeaderDO.
		 * getHeaderId()) &&
		 * baRateHeaderTO.getIsPriorityProduct().equals(fcDO.getPriorityIndicator
		 * ())){ baFixedConfig.remove(fcDO); } } }else{ baFixedConfig =new
		 * HashSet<BARateConfigurationFixedChargesConfigDO>(); }
		 */

		if (!(StringUtil.isNull(baFixedChargesTO.getOctroiBourneByChk()))) {
			if (baFixedChargesTO.getOctroiBourneByChk().equals("on")) {
				// baFixedConfig = new HashSet<>();
				BARateConfigurationFixedChargesConfigDO configFixedCharge = new BARateConfigurationFixedChargesConfigDO();
				configFixedCharge.setOctroiBourneBy(baFixedChargesTO
						.getOctroiBourneBy());
				if(!StringUtil.isNull(baRateHeaderDO) &&  !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//configFixedCharge.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					configFixedCharge.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				configFixedCharge.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baFixedConfig.add(configFixedCharge);
				baRateHeaderDO.setFixedChargeConfig(baFixedConfig);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getFuelSurchargeChk()))) {
			if (baFixedChargesTO.getFuelSurchargeChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getFuelSurcharge());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getOtherChargesChk()))) {
			if (baFixedChargesTO.getOtherChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getOtherCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getAirportChargesChk()))) {
			if (baFixedChargesTO.getAirportChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getAirportCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getParcelChargesChk()))) {
			if (baFixedChargesTO.getParcelChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getParcelCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getOctroiServiceChargesChk()))) {
			if (baFixedChargesTO.getOctroiServiceChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getOctroiServiceCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (!(StringUtil.isNull(baFixedChargesTO.getServiceTaxChk()))) {
			if (baFixedChargesTO.getServiceTaxChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getServiceTax());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(baFixedChargesTO.getEduChargesChk()))) {
			if (baFixedChargesTO.getEduChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getEduCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(baFixedChargesTO.getHigherEduChargesChk()))) {
			if (baFixedChargesTO.getHigherEduChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getHigherEduCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(baFixedChargesTO.getStateTaxChk()))) {
			if (baFixedChargesTO.getStateTaxChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO
						.setComponentValue(baFixedChargesTO.getStateTax());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}
		if (!(StringUtil.isNull(baFixedChargesTO.getSurchargeOnSTChk()))) {
			if (baFixedChargesTO.getSurchargeOnSTChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getSurchargeOnST());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}
		// to save to pay charges
		if (!(StringUtil.isNull(baFixedChargesTO.getToPayChargesChk()))) {
			if (baFixedChargesTO.getToPayChargesChk().equals("on")) {
				BARateConfigAdditionalChargesDO fixedChargesDO = new BARateConfigAdditionalChargesDO();
				fixedChargesDO.setComponentValue(baFixedChargesTO
						.getToPayCharges());
				fixedChargesDO
						.setComponentCode(RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES);
				if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
					//fixedChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
					fixedChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
				}
				fixedChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baAdditionalChargesDOs.add(fixedChargesDO);
			}
		}

		if (StringUtil.equals(baRateHeaderTO.getIsPriorityProduct(),
				CommonConstants.NO)) {
			prepareCODChargesList(baRateHeaderTO, codList, baRateHeaderDO,
					baCODSet);
			baRateHeaderDO.setBaCODChargesDOs(baCODSet);
		}

		baRateHeaderDO.setBaAdditionalChargesDO(baAdditionalChargesDOs);
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateFixedChargeDOList::END");
	}

	@Override
	public BARateHeaderTO getFixedChargesForCourier(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::getFixedChargesForCourier::START");
		BaRateConfigHeaderDO headerDO = baRateConfigurationDAO
				.getFixedChargesByHeader(baRateHeader.getHeaderId(),
						baRateHeader.getPriorityIndicator());
		convertFixedChargesDOtoFixedChargesTO(baRateHeader, headerDO);
		LOGGER.trace("BARateConfigurationServiceImpl::getFixedChargesForCourier::END");
		return baRateHeader;
	}

	/**
	 * @param baRateHeader
	 * @param productDO
	 * @throws CGBusinessException
	 */
	private void convertFixedChargesDOtoFixedChargesTO(
			BARateHeaderTO baRateHeader, BaRateConfigHeaderDO headerDO)
			throws CGBusinessException {
		LOGGER.trace("BARateConfigurationServiceImpl::convertFixedChargesDOtoFixedChargesTO::START");
		List<BARateConfigAdditionalChargesDO> baFixedCharges = headerDO
				.getBaRateConfigAdditionalCharges();
		List<BARateConfigurationFixedChargesConfigDO> fixedConfig = headerDO
				.getFixedConfig();
		List<BARateConfigCODChargesDO> codChargesDO = new ArrayList<>();
		if (!StringUtil.isEmptyList(headerDO.getCodCharges())) {
			codChargesDO = headerDO.getCodCharges();
		}
		BARateConfigFixedChargesTO baCourierFixedChargesTO = new BARateConfigFixedChargesTO();
		for (BARateConfigurationFixedChargesConfigDO baRateConfigurationFixedChargesConfigDO : fixedConfig) {
			if (!StringUtils.isEmpty(baRateConfigurationFixedChargesConfigDO
					.getOctroiBourneBy())) {
				baCourierFixedChargesTO
						.setOctroiBourneBy(baRateConfigurationFixedChargesConfigDO
								.getOctroiBourneBy());
			}
		}
		// Prepare BACourierFixedChargesTO
		for (BARateConfigAdditionalChargesDO baFixedChargeDO : baFixedCharges) {
			switch (baFixedChargeDO.getComponentCode()) {
			case RateCommonConstants.RATE_COMPONENT_TYPE_OCTROI_SERVICE_CHARGE:
				baCourierFixedChargesTO.setOctroiServiceCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_PARCEL_HANDLING_CHARGES:
				baCourierFixedChargesTO.setParcelCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_AIRPORT_HANDLING_CAHRGES:
				baCourierFixedChargesTO.setAirportCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_OTHER_CHARGES:
				baCourierFixedChargesTO.setOtherCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_FUEL_SURCHARGE:
				baCourierFixedChargesTO.setFuelSurcharge(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX:
				baCourierFixedChargesTO.setServiceTax(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX:
				baCourierFixedChargesTO.setStateTax(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST:
				baCourierFixedChargesTO.setSurchargeOnST(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS:
				baCourierFixedChargesTO.setEduCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS:
				baCourierFixedChargesTO.setHigherEduCharges(baFixedChargeDO
						.getComponentValue());
				break;
			case RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES:
				baCourierFixedChargesTO.setToPayCharges(baFixedChargeDO
						.getComponentValue());
				break;
			}
		}
		// prepare CODCharges list
		List<BACODChargesTO> codChargesTOs = new ArrayList<BACODChargesTO>();
		for (BARateConfigCODChargesDO bacodChargesDo : codChargesDO) {
			BACODChargesTO bacodChargesTO = new BACODChargesTO();
			bacodChargesTO = (BACODChargesTO) CGObjectConverter
					.createToFromDomain(bacodChargesDo, bacodChargesTO);
			if (!StringUtil.isNull(bacodChargesDo.getCodCharge())) {
				CodChargeDO codChargeDO = bacodChargesDo.getCodCharge();
				CodChargeTO codChargeTO = new CodChargeTO();
				codChargeTO.setCodChargeId(codChargeDO.getCodChargeId());
				codChargeTO.setMinimumDeclaredValue(codChargeDO
						.getMinimumDeclaredValue());
				codChargeTO.setMaximumDeclaredValue(codChargeDO
						.getMaximumDeclaredValue());
				codChargeTO.setCodChargeType(codChargeDO.getCodChargeType());
				if (!StringUtil.isNull(codChargeDO.getRateComponent())) {
					RateComponentTO rateComponentTO = new RateComponentTO();
					rateComponentTO = (RateComponentTO) CGObjectConverter
							.createToFromDomain(codChargeDO.getRateComponent(),
									rateComponentTO);
					codChargeTO.setRateComponent(rateComponentTO);
				}
				codChargeTO
						.setPercentileValue(codChargeDO.getPercentileValue());
				codChargeTO.setFixedValue(codChargeDO.getFixedValue());
				codChargeTO.setConfiguredFor(codChargeDO.getConfiguredFor());
				codChargeTO.setMinimumDeclaredValLabel(codChargeDO
						.getMinimumDeclaredValLabel());
				codChargeTO.setMaximumDeclaredValLabel(codChargeDO
						.getMaximumDeclaredValLabel());
				if (!StringUtil.isNull(codChargeDO.getRateCustomerCategory())) {
					RateCustomerCategoryTO rateCustomerCategoryTO = new RateCustomerCategoryTO();
					rateCustomerCategoryTO = (RateCustomerCategoryTO) CGObjectConverter
							.createToFromDomain(
									codChargeDO.getRateCustomerCategory(),
									rateCustomerCategoryTO);
					codChargeTO
							.setRateCustomerCategoryTO(rateCustomerCategoryTO);
				}

				bacodChargesTO.setCodChargeTO(codChargeTO);
			}
			codChargesTOs.add(bacodChargesTO);
		}
		baCourierFixedChargesTO.setCodChargesTOs(codChargesTOs);
		baRateHeader.setBaCourierFixedChargesTO(baCourierFixedChargesTO);
		LOGGER.trace("BARateConfigurationServiceImpl::convertFixedChargesDOtoFixedChargesTO::END");
	}

	@Override
	public List<InsuredByDO> getInsuredByDetails() throws CGBusinessException,
			CGSystemException {

		LOGGER.trace("BARateConfigurationServiceImpl::getInsuredByDetails::START");
		List<InsuredByDO> insuredBy = baRateConfigurationDAO
				.getInsuredByDetails();

		LOGGER.trace("BARateConfigurationServiceImpl::getInsuredByDetails::END");
		return insuredBy;
	}

	@Override
	public BARateHeaderTO getRTOChargesForCourier(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::getRTOChargesForCourier::START");
		BARateConfigRTOChargesDO rtoChargesDO = baRateConfigurationDAO
				.getRTOChargesByHeader(baRateHeader.getHeaderId(),
						baRateHeader.getPriorityIndicator());
		BARateConfigRTOChargesTO baCourierRTOChargesTO = new BARateConfigRTOChargesTO();
		try {
			if(!StringUtil.isNull(rtoChargesDO)){
				PropertyUtils.copyProperties(baCourierRTOChargesTO, rtoChargesDO);
			}
			baRateHeader.setBaCourierRTOChargesTO(baCourierRTOChargesTO);

		} catch (IllegalAccessException e) {
			LOGGER.error("BARateConfigurationServiceImpl::getRTOChargesForCourier::"
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error("BARateConfigurationServiceImpl::getRTOChargesForCourier::"
					+ e.getMessage());
		} catch (NoSuchMethodException e) {
			LOGGER.error("BARateConfigurationServiceImpl::getRTOChargesForCourier::"
					+ e.getMessage());
		}
		LOGGER.trace("BARateConfigurationServiceImpl::getRTOChargesForCourier::END");
		return baRateHeader;

	}

	@Override
	public BARateHeaderTO searchBARateConfigurationForPriorityProduct(
			Date fromDate, Date toDate, Integer cityId, Integer baType,
			Integer productCategoryIdForPriority, Integer headerId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfigurationForPriorityProduct::START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = new BARateHeaderTO();
		if (!StringUtil.isEmptyInteger(headerId)) {
			baRateHeaderDO = baRateConfigurationDAO
					.getBARateConfigurationDetailsByHeaderId(headerId);
		} else {
			baRateHeaderDO = baRateConfigurationDAO
					.getBARateConfigurationDetails(fromDate, toDate, cityId,
							baType, productCategoryIdForPriority);
		}

		baRateHeaderTO = prepareBARateHeaderTO(baRateHeaderDO, cityId, baType,
				productCategoryIdForPriority);
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfigurationForPriorityProduct::END");
		return baRateHeaderTO;
	}

	@Override
	public BARateHeaderTO getRTOChargesForPriority(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::getRTOChargesForPriority::START");
		BARateConfigRTOChargesDO rtoChargesDO = baRateConfigurationDAO
				.getRTOChargesByHeader(baRateHeader.getHeaderId(),
						baRateHeader.getPriorityIndicator());
		BARateConfigRTOChargesTO baPriorityRTOChargesTO = new BARateConfigRTOChargesTO();
		try {
			if(!StringUtil.isNull(rtoChargesDO)){
				PropertyUtils.copyProperties(baPriorityRTOChargesTO, rtoChargesDO);
			}
			baRateHeader.setBaPriorityRTOChargesTO(baPriorityRTOChargesTO);
		} catch (IllegalAccessException e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationServiceImpl :: getRTOChargesForPriority ::",
					e);
		} catch (InvocationTargetException e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationServiceImpl :: getRTOChargesForPriority ::",
					e);
		} catch (NoSuchMethodException e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationServiceImpl :: getRTOChargesForPriority ::",
					e);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::getRTOChargesForPriority::END");
		return baRateHeader;
	}

	@Override
	public BARateHeaderTO getFixedChargesForPriority(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::getFixedChargesForPriority::START");
		BaRateConfigHeaderDO headerDO = baRateConfigurationDAO
				.getFixedChargesByHeader(baRateHeader.getHeaderId(),
						baRateHeader.getPriorityIndicator());
		convertFixedChargesDOtoFixedChargesTO(baRateHeader, headerDO);
		LOGGER.trace("BARateConfigurationServiceImpl::getFixedChargesForPriority::END");
		return baRateHeader;
	}

	@Override
	public BARateHeaderTO saveOrUpdateRTOCharges(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateRTOChargesForCourier::START");
		// boolean savedStatus = false;
		BARateConfigRTOChargesTO rtoCharges = null;
		// Set<BaRateConfigProductHeaderDO> baRateProductDOSet = null;
		// Integer productCategoryId = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BaRateConfigProductHeaderDO baRateProductHeaderDO = new BaRateConfigProductHeaderDO();
		baRateHeaderDO = baRateConfigurationDAO
				.getBARateConfigurationDetailsByHeaderId(baRateHeader
						.getHeaderId());
		if (StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderDO = new BaRateConfigHeaderDO();
		}
		prepareBAProductHeaderDO(baRateHeader, baRateHeaderDO,
				baRateProductHeaderDO);
		if (baRateHeader.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			rtoCharges = baRateHeader.getBaCourierRTOChargesTO();
			// productCategoryId =
			// baRateHeader.getProductCategoryIdForCourier();
		} else if (baRateHeader.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			rtoCharges = baRateHeader.getBaPriorityRTOChargesTO();
			// productCategoryId =
			// baRateHeader.getProductCategoryIdForPriority();
		}
		prepareBARateRTOChargeDOList(baRateHeader, baRateProductHeaderDO,
				baRateHeaderDO, rtoCharges);
		// baRateProductDOSet = new HashSet<>();
		// baRateProductDOSet.add(baRateProductHeaderDO);
		// baRateHeaderDO.setBaRateProductDO(baRateProductDOSet);
		if ((baRateHeader.getIsRenewWindow()
				.equalsIgnoreCase(RateCommonConstants.YES))
				&& StringUtil.isEmptyInteger(baRateHeader.getHeaderId())) {
			baRateConfigurationDAO.updateBAConfgRenewStatus(baRateHeader
					.getOldHeaderId());
		}
		// Search BA Configuration
		/*
		 * BaRateConfigHeaderDO headerDO = setBaConfigRTODetails(baRateHeader,
		 * baRateHeaderDO, baRateProductHeaderDO, productCategoryId); if
		 * (!StringUtil.isNull(headerDO)) { baRateHeaderDO = headerDO; }
		 */
		baRateHeaderDO = baRateConfigurationDAO
				.saveOrUpdateBARateConfiguration(baRateHeaderDO);
		if (!StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())) {
			convertProductHeaderDOtoBaRateProductTO(baRateHeader,
					baRateHeaderDO);
			setFlags(baRateHeader, baRateHeaderDO,
					baRateHeader.getProductCategoryIdForCourier(),
					baRateHeader.getProductCategoryIdForPriority());
		}
		LOGGER.trace("BARateConfigurationServiceImpl::saveOrUpdateRTOChargesForCourier::END");
		return baRateHeader;
	}

	/*
	 * private BaRateConfigHeaderDO setBaConfigRTODetails( BARateHeaderTO
	 * baRateHeaderTO, BaRateConfigHeaderDO baRateHeaderDO,
	 * BaRateConfigProductHeaderDO baRateProductHeaderDO, Integer
	 * productCategoryId) throws CGBusinessException, CGSystemException {
	 * LOGGER.
	 * trace("BARateConfigurationServiceImpl::setBaConfigRTODetails::START");
	 * BaRateConfigHeaderDO headerDO = null; if
	 * (!StringUtil.isEmptyInteger(baRateHeaderTO.getHeaderId())) { headerDO =
	 * baRateConfigurationDAO
	 * .getBARateConfigurationDetailsByHeaderId(baRateHeaderTO .getHeaderId());
	 * } if (!StringUtil.isNull(headerDO) &&
	 * !CGCollectionUtils.isEmpty(headerDO.getBaRateProductDO())) {
	 * Set<BaRateConfigProductHeaderDO> baRateProducts = null; baRateProducts =
	 * headerDO.getBaRateProductDO(); for (BaRateConfigProductHeaderDO
	 * baRateConfigProductHeaderDO : baRateProducts) { if
	 * (!StringUtil.isEmptyInteger(baRateConfigProductHeaderDO
	 * .getRateProductCategory()) && (productCategoryId
	 * .equals(baRateConfigProductHeaderDO .getRateProductCategory()))) {
	 * baRateConfigProductHeaderDO .setBaRateRTOChargesDO(baRateProductHeaderDO
	 * .getBaRateRTOChargesDO()); baRateHeaderDO = headerDO; } } }
	 * LOGGER.trace("BARateConfigurationServiceImpl::setBaConfigRTODetails::END"
	 * ); return headerDO; }
	 */

	/**
	 * @param baRateHeader
	 * @param baRateProductHeaderDO
	 * @param baRateConfigRTOChargesTO
	 */
	private void prepareBARateRTOChargeDOList(BARateHeaderTO baRateHeaderTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			BaRateConfigHeaderDO baRateHeaderDO,
			BARateConfigRTOChargesTO baRateConfigRTOChargesTO) {
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateRTOChargeDOList::START");
		// Set<BARateConfigRTOChargesDO> rtoChargesDOs = new HashSet<>();
		/*
		 * Set<BaRateConfigProductHeaderDO> phDOset = new
		 * CopyOnWriteArraySet<BaRateConfigProductHeaderDO
		 * >(baRateHeaderDO.getBaRateProductDO());
		 * if(!CGCollectionUtils.isEmpty(phDOset)){
		 * for(BaRateConfigProductHeaderDO phDO : phDOset){ if
		 * (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
		 * RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
		 * if(phDO.getBaProductHeaderId
		 * ().equals(baRateHeaderTO.getCourierProductHeaderId())){
		 * baRateProductHeaderDO = phDO; //phDOset.remove(phDO); } }else if
		 * (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
		 * RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
		 * if(phDO.getBaProductHeaderId
		 * ().equals(baRateHeaderTO.getPriorityProductHeaderId())){
		 * baRateProductHeaderDO = phDO; } } } }else{ phDOset = new
		 * HashSet<BaRateConfigProductHeaderDO>(); }
		 * 
		 * phDOset.add(baRateProductHeaderDO);
		 * baRateHeaderDO.setBaRateProductDO(phDOset);
		 */
		Set<BARateConfigRTOChargesDO> rtoChargesDOSet = new CopyOnWriteArraySet<BARateConfigRTOChargesDO>(
				baRateHeaderDO.getBaRateRTOChargesDO());

		if (!CGCollectionUtils.isEmpty(rtoChargesDOSet)) {
			for (BARateConfigRTOChargesDO rcDO : rtoChargesDOSet) {
				if (StringUtil.equals(baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.NO)) {
					if (rcDO.getPriorityIndicator().equals(CommonConstants.NO)) {
						rtoChargesDOSet.remove(rcDO);
					}
				} else if (StringUtil.equals(
						baRateHeaderTO.getIsPriorityProduct(),
						CommonConstants.YES)) {
					if (rcDO.getPriorityIndicator().equals(CommonConstants.YES)) {
						rtoChargesDOSet.remove(rcDO);
					}
				}
			}
		} else {
			rtoChargesDOSet = new HashSet<BARateConfigRTOChargesDO>();
		}

		/*
		 * if(!CGCollectionUtils.isEmpty(rtoChargesDOSet)){
		 * for(BARateConfigRTOChargesDO rcDO : rtoChargesDOSet){
		 * if(rcDO.getBaRateConfigHeaderDO
		 * ().getHeaderId().equals(baRateHeaderDO.getHeaderId())){
		 * rtoChargesDOSet.remove(rcDO); } } }else{ rtoChargesDOSet = new
		 * HashSet<BARateConfigRTOChargesDO>(); }
		 */
		BARateConfigRTOChargesDO rtoChargesDO = new BARateConfigRTOChargesDO();
		if (!(StringUtil.isNull(baRateConfigRTOChargesTO
				.getRtoChargesApplicablechk()))) {
			if (baRateConfigRTOChargesTO.getRtoChargesApplicablechk().equals(
					"on")) {
				rtoChargesDO.setRtoChargeApplicable(CommonConstants.YES);
			} else {
				rtoChargesDO.setRtoChargeApplicable(CommonConstants.NO);
			}
		} else {
			rtoChargesDO.setRtoChargeApplicable(CommonConstants.NO);
		}
		if (!(StringUtil.isNull(baRateConfigRTOChargesTO.getSameAsSlabRate()))) {
			if (baRateConfigRTOChargesTO.getSameAsSlabRate().equals("on")) {
				rtoChargesDO.setSameAsSlabRate(CommonConstants.YES);
			} else {
				rtoChargesDO.setSameAsSlabRate(CommonConstants.NO);
			}
		} else {
			rtoChargesDO.setSameAsSlabRate(CommonConstants.NO);
		}
		if (!StringUtil.isNull(baRateConfigRTOChargesTO.getDiscountOnSlab())) {
			rtoChargesDO.setDiscountOnSlab(baRateConfigRTOChargesTO
					.getDiscountOnSlab());
		}
		rtoChargesDO
				.setPriorityIndicator(baRateHeaderTO.getIsPriorityProduct());
		rtoChargesDO.setRateComponentCode(RateCommonConstants.RTO_CODE);
		if(!StringUtil.isNull(baRateHeaderDO) && !StringUtil.isEmptyInteger(baRateHeaderDO.getHeaderId())){
			//rtoChargesDO.setBaRateConfigHeaderId(baRateHeaderDO.getHeaderId());
			rtoChargesDO.setBaRateConfigHeaderDO(baRateHeaderDO);
		}
		rtoChargesDOSet.add(rtoChargesDO);
		baRateHeaderDO.setBaRateRTOChargesDO(rtoChargesDOSet);
		LOGGER.trace("BARateConfigurationServiceImpl::prepareBARateRTOChargeDOList::END");
	}

	/**
	 * @param baRateHeaderTO
	 * @param baRateHeaderDO
	 */
	private void convertProductHeaderDOtoBaRateProductTO(
			BARateHeaderTO baRateHeaderTO, BaRateConfigHeaderDO baRateHeaderDO) {
		LOGGER.trace("BARateConfigurationServiceImpl::convertProductHeaderDOtoBaRateProductTO::START");
		baRateHeaderTO.setIsSaved("saved");
		baRateHeaderTO.setHeaderId(baRateHeaderDO.getHeaderId());
		List<BaRateProductTO> baRateProductTOs = new ArrayList<>();
		if (!StringUtil.isNull(baRateHeaderDO.getBaRateProductDO())) {
			for (BaRateConfigProductHeaderDO baRateConfigProductHeaderDO : baRateHeaderDO
					.getBaRateProductDO()) {
				BaRateProductTO productTO = new BaRateProductTO();
				try {
					PropertyUtils.copyProperties(productTO,
							baRateConfigProductHeaderDO);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					LOGGER.error(
							"BARateConfigurationServiceImpl::convertProductHeaderDOtoBaRateProductTO:: ",
							e);
				}
				baRateProductTOs.add(productTO);
			}
		}
		baRateHeaderTO.setBaRateProductTOList(baRateProductTOs);
		LOGGER.trace("BARateConfigurationServiceImpl::convertProductHeaderDOtoBaRateProductTO::END");
	}

	/**
	 * Submit BA RAte Configuration
	 */
	@Override
	public String submitBaRateConfiguration(BARateHeaderTO baRateHeader)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::submitBaRateConfiguration::START");
		boolean savedStatus = false;
		String isSubmitted = "No";
		Date toDate = null;
		savedStatus = baRateConfigurationDAO.submitBaRateConfiguration(
				baRateHeader.getHeaderId(), baRateHeader.getFrmDate(),
				baRateHeader.getToDate(), baRateHeader.getLoggedInUserId(), DateUtil.getCurrentDate());
		if (savedStatus) {
			isSubmitted = "Yes";
			if ((baRateHeader.getIsRenewWindow()
					.equalsIgnoreCase(RateCommonConstants.YES))) {
				// Date oldTODate =
				// DateUtil.stringToDDMMYYYYFormat(baRateHeader.getOldtoDate());
				// Date newFromDate =
				// DateUtil.stringToDDMMYYYYFormat(baRateHeader.getFrmDate());
				// if (newFromDate.compareTo(oldTODate) >= 0) {
				toDate = addOrRemoveDaysFromDate(baRateHeader.getFrmDate(), -1);
				baRateConfigurationDAO.updateBAConfgTODate(toDate,
						baRateHeader.getOldHeaderId());
				// }
				/*
				 * else if (newFromDate.compareTo(oldTODate) > 0) { Date
				 * newFrmDate = addOrRemoveDaysFromDate(
				 * baRateHeader.getOldtoDate(), 1);
				 * baRateConfigurationDAO.updateBAConfgFromDate(newFrmDate,
				 * baRateHeader.getHeaderId()); }
				 */
			}
		}
		LOGGER.trace("BARateConfigurationServiceImpl::submitBaRateConfiguration::END");
		return isSubmitted;
	}

	/**
	 * Add or remove days in given string Date
	 * 
	 * @param dateStr
	 * @param dayCount
	 * @return
	 */
	public Date addOrRemoveDaysFromDate(String dateStr, Integer dayCount) {
		LOGGER.trace("BARateConfigurationServiceImpl::addOrRemoveDaysFromDate::START");
		SimpleDateFormat sdf = new SimpleDateFormat(
				FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			LOGGER.error(
					"BARateConfigurationServiceImpl::addOrRemoveDaysFromDate::",
					e);
		}
		c.add(Calendar.DATE, dayCount); // number of days to add
		dateStr = sdf.format(c.getTime());

		Date date = DateUtil.getDateFromString(dateStr,
				FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		LOGGER.trace("BARateConfigurationServiceImpl::addOrRemoveDaysFromDate::END");
		return date;
	}

	@Override
	public String isExistsBaRateConfiguration(Integer cityId, Integer baTypeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::isExistsBaRateConfiguration::START");
		Boolean isexist = null;
		String isExists = null;
		isexist = baRateConfigurationDAO.isExistsBaRateConfiguration(cityId,
				baTypeId);
		if (isexist) {
			isExists = "Yes";
		}
		LOGGER.trace("BARateConfigurationServiceImpl::isExistsBaRateConfiguration::END");
		return isExists;
	}

	@Override
	public BARateHeaderTO searchRenewedBARateConfiguration(Integer cityId,
			Integer baTypeId, Integer courierProductId,
			Integer priorityProductId, Date toDate, Integer headerId, Map<String, Integer> prodCateMap, HttpSession session)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::searchRenewedBARateConfiguration::START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = null;

		Date fromDate = baRateConfigurationDAO
				.getFromDateByBARateHeaderId(headerId);

		baRateHeaderDO = baRateConfigurationDAO
				.getRenewedBARateConfigurationDetails(cityId, baTypeId,
						courierProductId, fromDate);
		if (StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderDO = saveRenewBARateConfiguration(headerId);
		}
		if (!StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderTO = prepareBARateHeaderTO(baRateHeaderDO, cityId,
					baTypeId, courierProductId);
			/*setFlags(baRateHeaderTO, baRateHeaderDO, courierProductId,
					priorityProductId);*/
			_setProdCateIds(baRateHeaderTO, prodCateMap);
			_setFlags(baRateHeaderTO, baRateHeaderDO, session);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::searchRenewedBARateConfiguration::END");
		return baRateHeaderTO;
	}

	private BaRateConfigHeaderDO saveRenewBARateConfiguration(Integer headerId)
			throws CGBusinessException, CGSystemException {

		BaRateConfigHeaderDO brchDO = null;

		brchDO = baRateConfigurationDAO
				.getBARateConfigurationDetailsByHeaderId(headerId);
		copyBARateConfigDO(brchDO);
		brchDO = baRateConfigurationDAO.saveOrUpdateBARateConfiguration(brchDO);
		return brchDO;
	}

	private void copyBARateConfigDO(BaRateConfigHeaderDO brchDO)
			throws CGBusinessException {

		brchDO.setFromDate(DateUtil.getFutureDate(1));
		brchDO.setToDate(null);
		brchDO.setHeaderId(null);
		brchDO.setHeaderStatus(RateCommonConstants.STATUS_INACTIVE);
		/******************* Products ***********/
		if (!CGCollectionUtils.isEmpty(brchDO.getBaRateProductDO())) {
			
			for (BaRateConfigProductHeaderDO brchpDO : brchDO.getBaRateProductDO()) {
				
				brchpDO.setBaProductHeaderId(null);
				brchpDO.setBaRateHeaderDO(brchDO);
				
				
				if (!CGCollectionUtils.isEmpty(brchpDO.getBaRateWeightSlabDOs())) {
					
					for(BaRateWeightSlabDO brwsDO : brchpDO.getBaRateWeightSlabDOs()){
						
						brwsDO.setBaWeightSlabId(null);
						brwsDO.setBaRateConfigProductHeaderDO(brchpDO);
						
						if(!CGCollectionUtils.isEmpty(brwsDO.getBaSlabRateDO()))
						{	
							for (BaRateConfigSlabRateDO brsrDO : brwsDO.getBaSlabRateDO()) {
								brsrDO.setBaSlabRateId(null);
								brsrDO.setBaRateWeightSlabDO(brwsDO);
								brsrDO.setBaRateProductDO(brchpDO);
							}
							
							
						}
						
						
						if(!CGCollectionUtils.isEmpty(brwsDO
								.getBaSpecialDestinationRateDO()))
						{	
							for (BARateConfigSpecialDestinationRateDO brcsdrDO : brwsDO
									.getBaSpecialDestinationRateDO()) {
								brcsdrDO.setSpecialDestinationId(null);
								brcsdrDO.setBaRateWeightSlabDO(brwsDO);
								brcsdrDO.setBaRateProductDO(brchpDO);								
							}
							
						}
						
					}
					
				}
				if (!CGCollectionUtils.isEmpty(brchpDO
						.getBaSpecialDestinationRateDO())) {
					Set<BARateConfigSpecialDestinationRateDO> brsdDOSet = brchpDO
							.getBaSpecialDestinationRateDO();
					Set<BARateConfigSpecialDestinationRateDO> splDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>(
							brchpDO.getBaSpecialDestinationRateDO().size());
					for (BARateConfigSpecialDestinationRateDO brsdDO : brsdDOSet) {
						brsdDO.setSpecialDestinationId(null);
						brsdDO.setBaRateProductDO(brchpDO);
						splDOSet.add(brsdDO);
					}
					brchpDO.setBaSpecialDestinationRateDO(splDOSet);
				}

			}
		}

		/************ Additional Charges *********************/
		if (!CGCollectionUtils.isEmpty(brchDO.getBaAdditionalChargesDO())) {
			Set<BARateConfigAdditionalChargesDO> brcaDOSet = brchDO.getBaAdditionalChargesDO();
			Set<BARateConfigAdditionalChargesDO> additionalChrgDOSet = 
					new HashSet<BARateConfigAdditionalChargesDO>(brchDO.getBaAdditionalChargesDO().size());
			for (BARateConfigAdditionalChargesDO brcaDO : brcaDOSet) {
				brcaDO.setAdditionalChrgId(null);
				if(!StringUtil.isNull(brchDO) &&  !StringUtil.isEmptyInteger(brchDO.getHeaderId())){
					//brcaDO.setBaRateConfigHeaderId(brchDO.getHeaderId());
					brcaDO.setBaRateConfigHeaderDO(brchDO);
				}
				additionalChrgDOSet.add(brcaDO);
			}
			brchDO.setBaAdditionalChargesDO(additionalChrgDOSet);
		}

		/************ Config fixed Charges *********************/
		if (!CGCollectionUtils.isEmpty(brchDO.getFixedChargeConfig())) {
			Set<BARateConfigurationFixedChargesConfigDO> brcfcDOSet = brchDO
					.getFixedChargeConfig();
			Set<BARateConfigurationFixedChargesConfigDO> configChrgDOSet = new HashSet<BARateConfigurationFixedChargesConfigDO>(
					brchDO.getFixedChargeConfig().size());
			for (BARateConfigurationFixedChargesConfigDO brcfcDO : brcfcDOSet) {
				brcfcDO.setBaFixedChargesConfigId(null);
				if(!StringUtil.isNull(brchDO) &&  !StringUtil.isEmptyInteger(brchDO.getHeaderId())){
					//brcfcDO.setBaRateConfigHeaderId(brchDO.getHeaderId());
					brcfcDO.setBaRateConfigHeaderDO(brchDO);
				}
				configChrgDOSet.add(brcfcDO);
			}
			brchDO.setFixedChargeConfig(configChrgDOSet);
		}

		/************COD Charges **********************/
		
		if (!CGCollectionUtils.isEmpty(brchDO.getBaCODChargesDOs())) {
			for (BARateConfigCODChargesDO brcccDO : brchDO.getBaCODChargesDOs()) {
				brcccDO.setBaCodChargesId(null);
				brcccDO.setBaRateConfigHeaderDO(brchDO);
			}
		}
		
		/************ RTO Charges *********************/
		if (!CGCollectionUtils.isEmpty(brchDO.getBaRateRTOChargesDO())) {
			Set<BARateConfigRTOChargesDO> brcrcDOSet = brchDO
					.getBaRateRTOChargesDO();
			Set<BARateConfigRTOChargesDO> rtoChrgDOSet = new HashSet<BARateConfigRTOChargesDO>(
					brchDO.getBaRateRTOChargesDO().size());
			for (BARateConfigRTOChargesDO brcrcDO : brcrcDOSet) {
				brcrcDO.setRateBARTOChargesId(null);
				if(!StringUtil.isNull(brchDO) &&  !StringUtil.isEmptyInteger(brchDO.getHeaderId())){
					//brcrcDO.setBaRateConfigHeaderId(brchDO.getHeaderId());
					brcrcDO.setBaRateConfigHeaderDO(brchDO);
				}
				rtoChrgDOSet.add(brcrcDO);
			}
			brchDO.setBaRateRTOChargesDO(rtoChrgDOSet);
		}

	}

	@Override
	public BARateHeaderTO searchBARateConfigurationByHeaderId(Integer headerId,
			Integer cityId, Integer baTypeId, Integer courierProductId,
			Integer priorityProductId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfigurationByHeaderId::START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = null;
		baRateHeaderDO = baRateConfigurationDAO
				.getBARateConfigurationDetailsByHeaderId(headerId);
		if (!StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderTO = prepareBARateHeaderTO(baRateHeaderDO, cityId,
					baTypeId, courierProductId);
			setFlags(baRateHeaderTO, baRateHeaderDO, courierProductId,
					priorityProductId);
		}
		LOGGER.trace("BARateConfigurationServiceImpl::searchBARateConfigurationByHeaderId::END");
		return baRateHeaderTO;
	}

	private void setFlags(BARateHeaderTO baRateHeaderTO,
			BaRateConfigHeaderDO baRateHeaderDO, Integer courierProductId,
			Integer priorityProductId) {
		baRateHeaderTO.setCourierRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setAirCargoRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setTrainRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityBRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityARatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPrioritySRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setCourierChargesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityChargesCheck(CommonConstants.NO);
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateProductDO())) {
			Set<BaRateConfigProductHeaderDO> cphDOSet = baRateHeaderDO
					.getBaRateProductDO();
			for (BaRateConfigProductHeaderDO cphDO : cphDOSet) {

				if (!StringUtil.isEmptyInteger(courierProductId)
						&& cphDO.getRateProductCategory().equals(
								courierProductId)) {
					baRateHeaderTO.setCourierRatesCheck(CommonConstants.YES);
					if (!CGCollectionUtils.isEmpty(baRateHeaderDO
							.getBaAdditionalChargesDO())) {
						baRateHeaderTO
								.setCourierChargesCheck(CommonConstants.YES);
					}
				} else if (!StringUtil.isEmptyInteger(priorityProductId)
						&& cphDO.getRateProductCategory().equals(
								priorityProductId)) {
					Set<BaRateConfigSlabRateDO> srDOSet = cphDO
							.getBaSlabRateDO();
					if (!CGCollectionUtils.isEmpty(srDOSet)) {
						for (BaRateConfigSlabRateDO srDO : srDOSet) {
							if (srDO.getServicedOn().equals("B")) {
								baRateHeaderTO
										.setPriorityBRatesCheck(CommonConstants.YES);
							} else if (srDO.getServicedOn().equals("A")) {
								baRateHeaderTO
										.setPriorityARatesCheck(CommonConstants.YES);
							} else if (srDO.getServicedOn().equals("S")) {
								baRateHeaderTO
										.setPrioritySRatesCheck(CommonConstants.YES);
							}
						}
					}
					if (!CGCollectionUtils.isEmpty(baRateHeaderDO
							.getBaAdditionalChargesDO())) {
						baRateHeaderTO
								.setPriorityChargesCheck(CommonConstants.YES);
					}
				}
			}
		}
	}

	@Override
	public RateCustomerCategoryTO getRateCustCategoryByCode(
			String rateCustomerCategoryCode) throws CGBusinessException,
			CGSystemException {
		return rateCommonService
				.getRateCustCategoryByCode(rateCustomerCategoryCode);
	}

	@Override
	public List<CodChargeTO> getDeclaredValueCodChargeForBA(
			String configuredType, Integer rateCustomerCategoryId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BARateConfigurationServiceImpl::getDeclaredValueCodChargeForBA::START");
		List<CodChargeTO> codChargeTOs = rateCommonService
				.getDeclaredValueCodChargeForBA(configuredType,
						rateCustomerCategoryId);

		LOGGER.trace("BARateConfigurationServiceImpl::getDeclaredValueCodChargeForBA::START");
		return codChargeTOs;
	}

	private void prepareCODChargesList(BARateHeaderTO baRateHeaderTO,
			List<BACODChargesTO> codList,
			BaRateConfigHeaderDO baRateConfigHeaderDO,
			Set<BARateConfigCODChargesDO> baCODSet) {
		if (!StringUtil.isEmptyList(codList)) {
			BARateConfigCODChargesDO baRateConfigCODChargesDO = null;
			int codLen = codList.size();
			for (int i = 0; i < codLen; i++) {
				baRateConfigCODChargesDO = new BARateConfigCODChargesDO();

				CodChargeDO codChargeDO = new CodChargeDO();
				codChargeDO.setCodChargeId(codList.get(i).getBaCodChargesId());
				baRateConfigCODChargesDO.setCodCharge(codChargeDO);

				baRateConfigCODChargesDO.setPercentileValue(codList.get(i)
						.getPercentileValue());
				baRateConfigCODChargesDO.setFixedChargeValue(codList.get(i)
						.getFixedChargeValue());
				baRateConfigCODChargesDO.setConsiderFixed(codList.get(i)
						.getConsiderFixed());
				baRateConfigCODChargesDO
						.setConsiderHigherFixedOrPercent(codList.get(i)
								.getConsiderHigherFixedOrPercent());
				
				if (!StringUtil.isNull(baRateConfigHeaderDO)
						&& !StringUtil.isEmptyInteger(baRateConfigHeaderDO
								.getHeaderId())) {
					/*baRateConfigCODChargesDO
							.setBaRateConfigHeaderId(baRateConfigHeaderDO
									.getHeaderId());*/
					baRateConfigCODChargesDO
					.setBaRateConfigHeaderDO(baRateConfigHeaderDO);
				}
				
				baRateConfigCODChargesDO.setPriorityIndicator(baRateHeaderTO
						.getIsPriorityProduct());
				baCODSet.add(baRateConfigCODChargesDO);
			}
		}
	}

	/*
	 * To save or update BA rate configuration rate details
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ff.rate.configuration.rateConfiguration.service.
	 * BARateConfigurationService
	 * #saveOrUpdateBARatesDtls(com.ff.to.ratemanagement
	 * .operations.rateconfiguration.BARateHeaderTO)
	 * 
	 * @author hkansagr
	 */
	@Override
	public BARateHeaderTO _saveOrUpdateBARatesDtls(
			BARateHeaderTO baRateHeaderTO, Map<String, Integer> prodCateMap, HttpSession session)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _saveOrUpdateBARatesDtls() :: START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateConfigSlabRateTO slabRateTO = null;
		Integer productCategoryId = null;
		Integer spldestColCount = null;
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			slabRateTO = baRateHeaderTO.getBaCourierSlabRateTO();
			productCategoryId = baRateHeaderTO.getProductCategoryIdForCourier();
			spldestColCount = slabRateTO.getCourierSplDestcoloumCount();
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
			slabRateTO = baRateHeaderTO.getBaTrainSlabRateTO();
			productCategoryId = baRateHeaderTO.getProductCategoryIdForTrain();
			spldestColCount = slabRateTO.getCourierSplDestcoloumCount();
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)) {
			slabRateTO = baRateHeaderTO.getBaAirCargoSlabRateTO();
			productCategoryId = baRateHeaderTO.getProductCategoryIdForAirCargo();
			spldestColCount = slabRateTO.getCourierSplDestcoloumCount();
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			slabRateTO = baRateHeaderTO.getBaPrioritySlabRateTO();
			productCategoryId = baRateHeaderTO.getProductCategoryIdForPriority();
			spldestColCount = slabRateTO.getPrioritySplDestcoloumCount();
		}
		baRateHeaderDO = baRateConfigurationDAO.getBARateConfigurationDetailsByHeaderId(baRateHeaderTO.getHeaderId());
		if (StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderDO = new BaRateConfigHeaderDO();
		}
		BaRateConfigHeaderDO headerDO = _domainConverterBARateHeaderTO2BARateProductHeaderDO(
				baRateHeaderTO, slabRateTO, baRateHeaderDO, productCategoryId, spldestColCount);
		headerDO = baRateConfigurationDAO.saveOrUpdateBARateConfiguration(headerDO);
		if ((baRateHeaderTO.getIsRenewWindow().equalsIgnoreCase(RateCommonConstants.YES))
				&& StringUtil.isEmptyInteger(headerDO.getHeaderId())) {
			baRateConfigurationDAO.updateBAConfgRenewStatus(baRateHeaderTO.getOldHeaderId());
		}
		if (!StringUtil.isEmptyInteger(headerDO.getHeaderId())) {
			try {
				baRateHeaderTO = _searchBARatesDtls(headerDO.getHeaderId(),
						baRateHeaderTO.getCityId(), baRateHeaderTO.getBaType(),
						productCategoryId, baRateHeaderTO.getServicedOnCmn(),
						prodCateMap, session);
				// _setFlags(baRateHeaderTO, baRateHeaderDO);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in BARateConfigurationServiceImpl :: _saveOrUpdateBARatesDtls() :: ",
						e);
			}
			baRateHeaderTO.setIsSaved("saved");
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _saveOrUpdateBARatesDtls() :: END");
		return baRateHeaderTO;
	}

	/**
	 * To domain BA Rate Header converter - product header
	 * 
	 * @param baRateHeaderTO
	 * @param slabRateTO
	 * @param baRateHeaderDO
	 * @param productCategoryId
	 * @param spldestColCount
	 * @return headerDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private BaRateConfigHeaderDO _domainConverterBARateHeaderTO2BARateProductHeaderDO(
			BARateHeaderTO baRateHeaderTO, BARateConfigSlabRateTO slabRateTO,
			BaRateConfigHeaderDO baRateHeaderDO, Integer productCategoryId,
			Integer spldestColCount) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _domainConverterBARateHeaderTO2BARateProductHeaderDO() :: START");
		// Set properties in BaRateHeaderDO
		Set<BaRateConfigSlabRateDO> csrDOSet = new HashSet<BaRateConfigSlabRateDO>();
		Set<BARateConfigSpecialDestinationRateDO> csdrDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>();
		BaRateConfigHeaderDO headerDO = new BaRateConfigHeaderDO();
		prepairBaRateHeaderDOCommonDetailsFromBARateHeaderTO(baRateHeaderTO, headerDO);

		// Setting Fixed charges
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getFixedChargeConfig())) {
			headerDO.setFixedChargeConfig(baRateHeaderDO.getFixedChargeConfig());
		} else {
			headerDO.setFixedChargeConfig(new HashSet<BARateConfigurationFixedChargesConfigDO>());
		}

		// Setting Additional charges
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO
				.getBaAdditionalChargesDO())) {
			headerDO.setBaAdditionalChargesDO(baRateHeaderDO
					.getBaAdditionalChargesDO());
		} else {
			headerDO.setBaAdditionalChargesDO(new HashSet<BARateConfigAdditionalChargesDO>());
		}

		// Setting RTO charges
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateRTOChargesDO())) {
			headerDO.setBaRateRTOChargesDO(baRateHeaderDO
					.getBaRateRTOChargesDO());
		} else {
			headerDO.setBaRateRTOChargesDO(new HashSet<BARateConfigRTOChargesDO>());
		}

		// Setting COD charges
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaCODChargesDOs())) {
			headerDO.setBaCODChargesDOs(baRateHeaderDO.getBaCODChargesDOs());
		} else {
			headerDO.setBaCODChargesDOs(new HashSet<BARateConfigCODChargesDO>());
		}

		Set<BaRateConfigProductHeaderDO> baRateProductDOSet = null;
		Set<BaRateWeightSlabDO> rwsDOs = null;
		BaRateConfigProductHeaderDO baRateProductHeaderDO = new BaRateConfigProductHeaderDO();
		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateProductDO())) {
			baRateProductDOSet = new CopyOnWriteArraySet<BaRateConfigProductHeaderDO>(
					baRateHeaderDO.getBaRateProductDO());
			for (BaRateConfigProductHeaderDO rdDO : baRateProductDOSet) {
				if (rdDO.getRateProductCategory().equals(productCategoryId)) {
					if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
						// baRateProductHeaderDO = rdDO;
						baRateProductDOSet.remove(rdDO);
					} 
					else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
						// baRateProductHeaderDO = rdDO;
						baRateProductDOSet.remove(rdDO);
					} 
					else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)) {
						// baRateProductHeaderDO = rdDO;
						baRateProductDOSet.remove(rdDO);
					} 
					else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
						baRateProductHeaderDO = rdDO;
						baRateProductDOSet.remove(rdDO);
						/*rwsDOs = new CopyOnWriteArraySet<BaRateWeightSlabDO>(
								baRateProductHeaderDO.getBaRateWeightSlabDOs());
						baRateProductDOSet.remove(rdDO);
						for (BaRateWeightSlabDO rwsDO : rwsDOs) {

							if (!CGCollectionUtils.isEmpty(rwsDO
									.getBaSlabRateDO())) {
								csrDOSet = new CopyOnWriteArraySet<BaRateConfigSlabRateDO>(
										rwsDO.getBaSlabRateDO());
								String servicedOn = slabRateTO.getServicedOn();
								for (BaRateConfigSlabRateDO csrDO : csrDOSet) {
									if (!StringUtil.isStringEmpty(servicedOn)) {
										if (csrDO.getServicedOn()
												.equalsIgnoreCase(servicedOn)) {
											csrDOSet.remove(csrDO);
										}
									} else {
										csrDOSet.remove(csrDO);
									}
								}
							}// END IF - SLAB RATE

							if (!CGCollectionUtils.isEmpty(rwsDO
									.getBaSpecialDestinationRateDO())) {
								csdrDOSet = new CopyOnWriteArraySet<BARateConfigSpecialDestinationRateDO>(
										rwsDO.getBaSpecialDestinationRateDO());
								String servicedOn = slabRateTO.getServicedOn();
								for (BARateConfigSpecialDestinationRateDO csdrDO : csdrDOSet) {
									if (!StringUtil.isStringEmpty(servicedOn)) {
										if (csdrDO.getServicedOn()
												.equalsIgnoreCase(servicedOn)) {
											csdrDOSet.remove(csdrDO);
										}
									} else {
										csdrDOSet.remove(csdrDO);
									}
								}
							}// END IF - Special Destination
							rwsDO.setBaSlabRateDO(csrDOSet);
							rwsDO.setBaSpecialDestinationRateDO(csdrDOSet);

						}// END of FOR EACH
						baRateProductHeaderDO.setBaRateWeightSlabDOs(rwsDOs);*/
					}
				}
			}
		} else {
			baRateProductDOSet = new HashSet<BaRateConfigProductHeaderDO>();
		}
		if (!StringUtil.isEmptyInteger(productCategoryId)) {
			baRateProductHeaderDO.setRateProductCategory(productCategoryId);
		}
		if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.COURIER_PRODUCT_CODE)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getCourierProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getCourierProductHeaderId());
			}
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateCommonConstants.PRO_CODE_TRAIN)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getTrainProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getTrainProductHeaderId());
			}
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateCommonConstants.PRO_CODE_AIR_CARGO)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getAirCargoProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getAirCargoProductHeaderId());
			}
		} else if (baRateHeaderTO.getProductCategory().equalsIgnoreCase(
				RateConfigurationConstants.PRIORITY_PRODUCT_CODE)) {
			if (!StringUtil.isEmptyInteger(baRateHeaderTO
					.getPriorityProductHeaderId())) {
				baRateProductHeaderDO.setBaProductHeaderId(baRateHeaderTO
						.getPriorityProductHeaderId());
			}
		}
		baRateProductHeaderDO.setIsSave(RateCommonConstants.YES);
		
		if (StringUtil.isEmptyInteger(baRateProductHeaderDO.getBaProductHeaderId())) {
			baRateProductHeaderDO.setCreatedBy(baRateHeaderTO.getLoggedInUserId());
			baRateProductHeaderDO.setCreatedDate(DateUtil.getCurrentDate());
		}
		baRateProductHeaderDO.setUpdatedBy(baRateHeaderTO.getLoggedInUserId());
		baRateProductHeaderDO.setUpdatedDate(DateUtil.getCurrentDate());
		// BA Rate Weight Slab DO set
		/*Set<BaRateWeightSlabDO> baRateWtSlabDOSet = _getBaWeightSlabRateDOSet(
				slabRateTO, baRateProductHeaderDO,
				baRateHeaderTO.getProductCategory());*/
		// Set baSlabRateDOSet
		/*_setBaSlabRateDOSet(slabRateTO, baRateProductHeaderDO,
				baRateWtSlabDOSet, baRateHeaderTO.getProductCategory());
		*/// Create set of BaSpecialDestinationRateDO
		/*_setBaSpecialSlabRateDOSet(slabRateTO, baRateProductHeaderDO,
				spldestColCount, baRateWtSlabDOSet);*/
		
		
		Set<BaRateWeightSlabDO> baRateWtSlabDOSet = _getLatestBaWeightSlabRateDOSet(
				slabRateTO, baRateProductHeaderDO,
				baRateHeaderTO.getProductCategory());
		
		// Setting ba rate header DO
		baRateProductHeaderDO.setBaRateHeaderDO(headerDO);
		baRateProductDOSet.add(baRateProductHeaderDO);
		headerDO.setBaRateProductDO(baRateProductDOSet);

		LOGGER.trace("BARateConfigurationServiceImpl :: _domainConverterBARateHeaderTO2BARateProductHeaderDO() :: END");
		return headerDO;
	}

	/**
	 * To get BA Weight Slab Rate DO Set
	 * 
	 * @param slabRateTO
	 * @param baRateProductHeaderDO
	 * @param productCode
	 * @return baRateWeightSlabDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private Set<BaRateWeightSlabDO> _getBaWeightSlabRateDOSet(
			BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			String productCode) throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _getBaWeightSlabRateDOSet() :: START");
		/*
		 * Set<BaRateWeightSlabDO> baRateWeightSlabDOs = baRateProductHeaderDO
		 * .getBaRateWeightSlabDOs();
		 */
		Set<BaRateWeightSlabDO> baRateWeightSlabDOs = null;
		if (CGCollectionUtils.isEmpty(baRateWeightSlabDOs)) {
			baRateWeightSlabDOs = new HashSet<BaRateWeightSlabDO>();
		}
		int len = 0;
		if (productCode
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)
				|| productCode
						.equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
			// If product category is Train or Air-Cargo, then it should +1
			// column (Minimum chargeable weight)
			len = slabRateTO.getCourierSplDestcoloumCount() + 1;
		} else if (productCode
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)) {
			// Priority Product only
			len = slabRateTO.getPrioritySplDestcoloumCount();
		} else {
			len = slabRateTO.getCourierSplDestcoloumCount();
		}
		for (int i = 0; i < len; i++) {
			// BA rate weight slab - START
			BaRateWeightSlabDO baRateWeightSlabDO = new BaRateWeightSlabDO();
			// Ba weight slab id
			if (!StringUtil.isEmptyInteger(slabRateTO.getBaWeightSlabId()[i])) {
				baRateWeightSlabDO.setBaWeightSlabId(slabRateTO
						.getBaWeightSlabId()[i]);
			}
			// Start weight
			WeightSlabDO startWeight = new WeightSlabDO();
			startWeight.setWeightSlabId(slabRateTO.getStartWeightSlabId()[i]);
			baRateWeightSlabDO.setStartWeight(startWeight);
			// End weight
			WeightSlabDO endWeight = new WeightSlabDO();
			endWeight.setWeightSlabId(slabRateTO.getEndWeightSlabId()[i]);
			baRateWeightSlabDO.setEndWeight(endWeight);
			// Product Header
			baRateWeightSlabDO
					.setBaRateConfigProductHeaderDO(baRateProductHeaderDO);
			// Position - Slab Order
			baRateWeightSlabDO.setSlabOrder(slabRateTO.getPosition()[i]);

			// BA rate weight slab - END
			baRateWeightSlabDOs.add(baRateWeightSlabDO);
		}

		if (productCode.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)) {
			if (!CGCollectionUtils.isEmpty(baRateProductHeaderDO
					.getBaRateWeightSlabDOs())) {
				setAllServicedOnRates(baRateWeightSlabDOs,
						baRateProductHeaderDO);
			}
		}

		baRateProductHeaderDO.setBaRateWeightSlabDOs(baRateWeightSlabDOs);
		LOGGER.trace("BARateConfigurationServiceImpl :: _getBaWeightSlabRateDOSet() :: END");
		return baRateWeightSlabDOs;
	}

	/**
	 * To set all serviced on rates values to BA Weight Rate Header
	 * 
	 * @param baRateWeightSlabDOs
	 * @param baRateProductHeaderDO
	 */
	private void setAllServicedOnRates(
			Set<BaRateWeightSlabDO> baRateWeightSlabDOs,
			BaRateConfigProductHeaderDO baRateProductHeaderDO) {
		LOGGER.trace("BARateConfigurationServiceImpl :: setAllServicedOnRates() :: START");
		Set<BaRateConfigSlabRateDO> sladSet = null;
		Set<BARateConfigSpecialDestinationRateDO> splDestSet = null;
		for (BaRateWeightSlabDO baRateWeightSlabDO : baRateWeightSlabDOs) {
			for (BaRateWeightSlabDO object : baRateProductHeaderDO
					.getBaRateWeightSlabDOs()) {
				if (object
						.getStartWeight()
						.getWeightSlabId()
						.equals(baRateWeightSlabDO.getStartWeight()
								.getWeightSlabId())) {
					sladSet = new CopyOnWriteArraySet<BaRateConfigSlabRateDO>(
							object.getBaSlabRateDO());
					splDestSet = new CopyOnWriteArraySet<BARateConfigSpecialDestinationRateDO>(
							object.getBaSpecialDestinationRateDO());
					for (BaRateConfigSlabRateDO slab : sladSet) {
						slab.setBaSlabRateId(null);
					}
					for (BARateConfigSpecialDestinationRateDO splDest : splDestSet) {
						splDest.setSpecialDestinationId(null);
					}
					baRateWeightSlabDO.setBaSlabRateDO(sladSet);
					baRateWeightSlabDO
							.setBaSpecialDestinationRateDO(splDestSet);
					break;
				}// IF - END
			}// INNER - FOR EACH END
		}// OUT - FOR EACH END
		LOGGER.trace("BARateConfigurationServiceImpl :: setAllServicedOnRates() :: END");
	}

	/**
	 * To set BA Rate slab rate details
	 * 
	 * @param slabRateTO
	 * @param baRateProductHeaderDO
	 * @param baRateWtSlabDOSet
	 * @param productCode
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private void _setBaSlabRateDOSet(BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			Set<BaRateWeightSlabDO> baRateWtSlabDOSet, String productCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _setBaCourierSlabRateDOSet() :: START");
		for (BaRateWeightSlabDO object : baRateWtSlabDOSet) {
			Set<BaRateConfigSlabRateDO> baSlabRateDOSet = null;
			if (!CGCollectionUtils.isEmpty(object.getBaSlabRateDO())) {
				baSlabRateDOSet = new CopyOnWriteArraySet<BaRateConfigSlabRateDO>(
						object.getBaSlabRateDO());
			}
			if (CGCollectionUtils.isEmpty(baSlabRateDOSet)) {
				baSlabRateDOSet = new HashSet<BaRateConfigSlabRateDO>();
			}
			int secLen = 0;
			if (productCode
					.equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)
					|| productCode
							.equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
				secLen = slabRateTO.getDestinationSectorId().length;
			} else {
				secLen = slabRateTO.getRate().length;
			}
			for (int i = 0; i < secLen; i++) {
				if (!StringUtil.isEmptyDouble(slabRateTO.getRate()[i])
						&& object.getStartWeight().getWeightSlabId()
								.equals(slabRateTO.getStartWeightSlabId()[i])
						&& object.getEndWeight().getWeightSlabId()
								.equals(slabRateTO.getEndWeightSlabId()[i])) {
					BaRateConfigSlabRateDO baSlabRateDO = new BaRateConfigSlabRateDO();
					if (!StringUtil
							.isEmptyInteger(slabRateTO.getBaSlabRateid()[i])) {
						baSlabRateDO.setBaSlabRateId(slabRateTO
								.getBaSlabRateid()[i]);
					}
					baSlabRateDO.setBaRateProductDO(baRateProductHeaderDO);
					baSlabRateDO.setDestinationSector(slabRateTO
							.getDestinationSectorId()[i]);

					// Setting baRateWeightSlabDO
					baSlabRateDO.setBaRateWeightSlabDO(object);

					baSlabRateDO.setRate(slabRateTO.getRate()[i]);
					if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
						baSlabRateDO.setServicedOn(slabRateTO.getServicedOn());
					}
					baSlabRateDOSet.add(baSlabRateDO);
				}
			}// i - loop end

			object.setBaSlabRateDO(baSlabRateDOSet);
		}// foreach - loop end
		LOGGER.trace("BARateConfigurationServiceImpl :: _setBaCourierSlabRateDOSet() :: END");
	}

	/**
	 * To set BA Special rate DO set
	 * 
	 * @param slabRateTO
	 * @param baRateProductHeaderDO
	 * @param spldestColCount
	 * @param baRateWtSlabDOSet
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private void _setBaSpecialSlabRateDOSet(BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			Integer spldestColCount, Set<BaRateWeightSlabDO> baRateWtSlabDOSet)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _setBaSpecialSlabRateDOSet() :: START");
		for (BaRateWeightSlabDO object : baRateWtSlabDOSet) {

			Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDOSet = null;
			
			if (!CGCollectionUtils.isEmpty(object
					.getBaSpecialDestinationRateDO())) {
				baSpecialDestinationRateDOSet = new CopyOnWriteArraySet<BARateConfigSpecialDestinationRateDO>(
						object.getBaSpecialDestinationRateDO());
			}

			if (CGCollectionUtils.isEmpty(baSpecialDestinationRateDOSet)) {
				baSpecialDestinationRateDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>();
			}
			object.setBaSpecialDestinationRateDO(baSpecialDestinationRateDOSet);
			int splDestCellCount = slabRateTO.getSpecialDestinationRate().length;
			int coSplDestRow = 0;
			if (!StringUtil
					.isEmptyInteger(slabRateTO.getStateId()[coSplDestRow])) {
				for (int l = 0; l < splDestCellCount; l++) {
					if (!StringUtil.isEmptyDouble(slabRateTO
							.getSpecialDestinationRate()[l])
							/*&& object
									.getStartWeight()
									.getWeightSlabId()
									.equals(slabRateTO.getStartWeightSlabId()[l])
							&& object.getEndWeight().getWeightSlabId()
									.equals(slabRateTO.getEndWeightSlabId()[l])*/) {
						BARateConfigSpecialDestinationRateDO baSpecialDestinationRateDO = new BARateConfigSpecialDestinationRateDO();
						/*if (!StringUtil.isEmptyInteger(slabRateTO
								.getSpecialDestinationId()[l])) {
							baSpecialDestinationRateDO
									.setSpecialDestinationId(slabRateTO
											.getSpecialDestinationId()[l]);
						}*/
						if (!StringUtil
								.isEmptyInteger(slabRateTO.getCityIds()[coSplDestRow])) {
							CityDO cityDO = new CityDO();
							cityDO.setCityId(slabRateTO.getCityIds()[coSplDestRow]);
							baSpecialDestinationRateDO.setCity(cityDO);
						}
						baSpecialDestinationRateDO.setStateId(slabRateTO
								.getStateId()[coSplDestRow]);

						baSpecialDestinationRateDO
								.setBaRateWeightSlabDO(object);

						baSpecialDestinationRateDO
								.setBaRateProductDO(baRateProductHeaderDO);
						baSpecialDestinationRateDO.setRate(slabRateTO
								.getSpecialDestinationRate()[l]);
						if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
							baSpecialDestinationRateDO.setServicedOn(slabRateTO
									.getServicedOn());
						}
						baSpecialDestinationRateDOSet
								.add(baSpecialDestinationRateDO);
						coSplDestRow++;
					}// IF END
				}// inner - FOR
			}// IF - END

			
		}// for each - END
		LOGGER.trace("BARateConfigurationServiceImpl :: _setBaSpecialSlabRateDOSet() :: END");
	}

	@Override
	public BARateHeaderTO _searchBARatesDtls(Integer headerId, Integer cityId,
			Integer baTypeId, Integer productId, String servicedOn,
			Map<String, Integer> prodCateMap, HttpSession session) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _searchBARatesDtls(1) :: START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = null;
		baRateHeaderDO = baRateConfigurationDAO.getBARateConfigurationDetailsByHeaderId(headerId);
		if (!StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderTO = _prepareBARateHeaderTO(baRateHeaderDO, cityId,
					baTypeId, productId, servicedOn, prodCateMap);
			_setFlags(baRateHeaderTO, baRateHeaderDO, session);
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _searchBARatesDtls(1) :: END");
		return baRateHeaderTO;
	}

	@Override
	public BARateHeaderTO _searchBARatesDtls(Integer cityId, Integer baTypeId,
			Integer productId, String servicedOn,
			Map<String, Integer> prodCateMap, HttpSession session) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _searchBARatesDtls(2) :: START");
		BaRateConfigHeaderDO baRateHeaderDO = null;
		BARateHeaderTO baRateHeaderTO = null;
		baRateHeaderDO = baRateConfigurationDAO._getBARatesDtls(cityId,
				baTypeId);
		if (!StringUtil.isNull(baRateHeaderDO)) {
			baRateHeaderTO = _prepareBARateHeaderTO(baRateHeaderDO, cityId,
					baTypeId, productId, servicedOn, prodCateMap);
			_setFlags(baRateHeaderTO, baRateHeaderDO, session);
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _searchBARatesDtls(2) :: END");
		return baRateHeaderTO;
	}

	/**
	 * To prepare BA rate header transfer object - BARateHeaderTO
	 * 
	 * @param baRateHeaderDO
	 * @param cityId
	 * @param baTypeId
	 * @param courierProdductId
	 * @param servicedOn
	 * @return baRateHeaderTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private BARateHeaderTO _prepareBARateHeaderTO(
			BaRateConfigHeaderDO baRateHeaderDO, Integer cityId,
			Integer baTypeId, Integer productCategoryId, String servicedOn,
			Map<String, Integer> prodCateMap) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareBARateHeaderTO() :: START");
		BARateHeaderTO baRateHeaderTO = new BARateHeaderTO();
		List<BaRateProductTO> baRateProductTOList = null;
		try {
			if (baRateHeaderDO != null) {
				baRateHeaderTO.setHeaderId(baRateHeaderDO.getHeaderId());
				baRateHeaderTO.setBaType(baRateHeaderDO.getBaTypeId());
				baRateHeaderTO.setFrmDate(DateUtil
						.getDDMMYYYYDateToString(baRateHeaderDO.getFromDate()));
				baRateHeaderTO.setToDate(DateUtil
						.getDDMMYYYYDateToString(baRateHeaderDO.getToDate()));
				baRateHeaderTO.setCityId(baRateHeaderDO.getCityId());
				baRateHeaderTO
						.setHeaderStatus(baRateHeaderDO.getHeaderStatus());
				baRateHeaderTO.setIsRenew(baRateHeaderDO.getIsRenew());

				// Setting product category ids form domain object to transfer
				// object
				_setProdCateIds(baRateHeaderTO, prodCateMap);

				if (!CGCollectionUtils.isEmpty(baRateHeaderDO
						.getBaRateProductDO())) {
					for (BaRateConfigProductHeaderDO baRateProductDO : baRateHeaderDO
							.getBaRateProductDO()) {
						if (baRateProductDO.getRateProductCategory().equals(
								productCategoryId)
								&& baRateProductDO.getBaRateHeaderDO()
										.getCityId().equals(cityId)
								&& baRateProductDO.getBaRateHeaderDO()
										.getCustomerTypeDO()
										.getCustomerTypeId().equals(baTypeId)) {
							baRateProductTOList = new ArrayList<BaRateProductTO>();
							BaRateProductTO baproductTO = new BaRateProductTO();
							PropertyUtils.copyProperties(baproductTO,
									baRateProductDO);

							// List BaWeightSlabTO
							_prepareListOfBaWeightSlabRateTO(baproductTO,
									baRateProductDO, servicedOn);

							baRateProductTOList.add(baproductTO);
						}
					}

					baRateHeaderTO.setBaRateProductTOList(baRateProductTOList);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationServiceImpl :: _prepareBARateHeaderTO() ::",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareBARateHeaderTO() :: END");
		return baRateHeaderTO;
	}

	/**
	 * Setting product category id(s) form product category map object to
	 * transfer object
	 * 
	 * @param baRateHeaderTO
	 * @param prodCateMap
	 */
	private void _setProdCateIds(BARateHeaderTO baRateHeaderTO,
			Map<String, Integer> prodCateMap) {
		LOGGER.trace("BARateConfigurationServiceImpl :: _setProdCateIds() :: START");
		if (!CGCollectionUtils.isEmpty(prodCateMap)) {
			// Courier
			if (!StringUtil.isEmptyInteger(prodCateMap
					.get(RateCommonConstants.PRO_CODE_COURIER))) {
				baRateHeaderTO
						.setProductCategoryIdForCourier((Integer) prodCateMap
								.get(RateCommonConstants.PRO_CODE_COURIER));
			}
			// Train
			if (!StringUtil.isEmptyInteger(prodCateMap
					.get(RateCommonConstants.PRO_CODE_TRAIN))) {
				baRateHeaderTO
						.setProductCategoryIdForTrain((Integer) prodCateMap
								.get(RateCommonConstants.PRO_CODE_TRAIN));
			}
			// Air Cargo
			if (!StringUtil.isEmptyInteger(prodCateMap
					.get(RateCommonConstants.PRO_CODE_AIR_CARGO))) {
				baRateHeaderTO
						.setProductCategoryIdForAirCargo((Integer) prodCateMap
								.get(RateCommonConstants.PRO_CODE_AIR_CARGO));
			}
			// Priority
			if (!StringUtil.isEmptyInteger(prodCateMap
					.get(RateCommonConstants.PRO_CODE_PRIORITY))) {
				baRateHeaderTO
						.setProductCategoryIdForPriority((Integer) prodCateMap
								.get(RateCommonConstants.PRO_CODE_PRIORITY));
			}
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _setProdCateIds() :: END");
	}

	/**
	 * To prepare List Of BA Weight Slab Rate
	 * 
	 * @param baproductTO
	 * @param baRateProductDO
	 * @param servicedOnCmn
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private void _prepareListOfBaWeightSlabRateTO(BaRateProductTO baproductTO,
			BaRateConfigProductHeaderDO baRateProductDO, String servicedOnCmn)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOfBaWeightSlabRateTO() :: START");
		List<BaRateWeightSlabTO> baRateWeightSlabTOList = new ArrayList<BaRateWeightSlabTO>();
		for (BaRateWeightSlabDO baRateWtSlabDO : baRateProductDO
				.getBaRateWeightSlabDOs()) {
			BaRateWeightSlabTO baRateWtSlabTO = new BaRateWeightSlabTO();

			// Copy Common Properties
			try {
				PropertyUtils.copyProperties(baRateWtSlabTO, baRateWtSlabDO);
			} catch (Exception e) {
				LOGGER.error(
						"ERROR :: BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: ",
						e);
			}

			// Start Weight - Copy Properties
			try {
				baRateWtSlabTO.setStartWeightTO(new WeightSlabTO());
				PropertyUtils.copyProperties(baRateWtSlabTO.getStartWeightTO(),
						baRateWtSlabDO.getStartWeight());
			} catch (Exception e) {
				LOGGER.error(
						"ERROR :: BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: START Weight ::",
						e);
			}
			// End Weight - Copy Properties
			try {
				baRateWtSlabTO.setEndWeightTO(new WeightSlabTO());
				PropertyUtils.copyProperties(baRateWtSlabTO.getEndWeightTO(),
						baRateWtSlabDO.getEndWeight());
			} catch (Exception e) {
				LOGGER.error(
						"ERROR :: BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: END Weight ::",
						e);
			}
			// Product Header Id
			baRateWtSlabTO.setBaRateConfigProductHeaderId(baRateWtSlabDO
					.getBaRateConfigProductHeaderDO().getBaProductHeaderId());

			// List BaSlabRateTO
			_prepareListOfBaSlabRateTO(baRateWtSlabTO, baRateWtSlabDO,
					servicedOnCmn);

			// List BaSpecialDestinationRateTO
			_prepareListOFBaSpecialDestinationRateTO(baRateWtSlabTO,
					baRateWtSlabDO, servicedOnCmn);

			baRateWeightSlabTOList.add(baRateWtSlabTO);
		}
		baproductTO.setBaRateWeightSlabTOList(baRateWeightSlabTOList);
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOfBaWeightSlabRateTO() :: END");
	}

	/**
	 * To prepare List of BA Slab Rate
	 * 
	 * @param baRateWeightSlabTO
	 * @param baRateWeightSlabDO
	 * @param servicedOnCmn
	 * @author hkansagr
	 */
	private void _prepareListOfBaSlabRateTO(
			BaRateWeightSlabTO baRateWeightSlabTO,
			BaRateWeightSlabDO baRateWeightSlabDO, String servicedOnCmn) {
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: START");
		List<BaSlabRateTO> baSlabTOList = null;
		baSlabTOList = new ArrayList<BaSlabRateTO>();
		if (!CGCollectionUtils.isEmpty(baRateWeightSlabDO.getBaSlabRateDO())) {
			for (BaRateConfigSlabRateDO baSlabRateDO : baRateWeightSlabDO
					.getBaSlabRateDO()) {
				// if product is priority
				if (!StringUtil.isStringEmpty(servicedOnCmn)
						&& !baSlabRateDO.getServicedOn().equals(servicedOnCmn)) {
					continue;
				}
				BaSlabRateTO baSlabRateTO = new BaSlabRateTO();
				// Copy Common Properties
				try {
					PropertyUtils.copyProperties(baSlabRateTO, baSlabRateDO);
				} catch (Exception e) {
					LOGGER.error(
							"ERROR :: BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: ",
							e);
				}

				// BA Rate Weight Slab Rate Id
				if (!StringUtil.isNull(baSlabRateDO.getBaRateWeightSlabDO())
						&& !StringUtil.isEmptyInteger(baSlabRateDO
								.getBaRateWeightSlabDO().getBaWeightSlabId())) {
					baSlabRateTO.setBaWeightSlabId(baSlabRateDO
							.getBaRateWeightSlabDO().getBaWeightSlabId());
				}
				baSlabTOList.add(baSlabRateTO);
			}
			baRateWeightSlabTO.setBaSlabRateList(baSlabTOList);
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOfBaSlabRateTO() :: END");
	}

	/**
	 * To prepare list of BA special destination rate transfer object -
	 * baRateWeightSlabTO (BaRateWeightSlabTO)
	 * 
	 * @param baRateWeightSlabTO
	 * @param baRateWeightSlabDO
	 * @param servicedOnCmn
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	private void _prepareListOFBaSpecialDestinationRateTO(
			BaRateWeightSlabTO baRateWeightSlabTO,
			BaRateWeightSlabDO baRateWeightSlabDO, String servicedOnCmn)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOFBaSpecialDestinationRateTO() :: START");
		List<BaSpecialDestinationRateTO> baSplDestTOList = null;
		baSplDestTOList = new ArrayList<BaSpecialDestinationRateTO>();
		Set<BARateConfigSpecialDestinationRateDO> baSplDestDOList = null;
		List<CityTO> cityTOList = null;
		if (!CGCollectionUtils.isEmpty(baRateWeightSlabDO
				.getBaSpecialDestinationRateDO())) {
			baSplDestDOList = baRateWeightSlabDO
					.getBaSpecialDestinationRateDO();
			Integer[] stateIds = new Integer[baSplDestDOList.size()];
			Integer state = null;
			int j = 0;
			boolean stateExist = Boolean.FALSE;
			Map<Integer, List<CityTO>> cityMap = new HashMap<Integer, List<CityTO>>();
			for (BARateConfigSpecialDestinationRateDO baSpecialDestDO : baRateWeightSlabDO
					.getBaSpecialDestinationRateDO()) {
				// if product is priority
				if (!StringUtil.isStringEmpty(servicedOnCmn)
						&& !baSpecialDestDO.getServicedOn().equals(
								servicedOnCmn)) {
					continue;
				}
				stateExist = Boolean.FALSE;
				BaSpecialDestinationRateTO baSpecialDestinationRateTO = new BaSpecialDestinationRateTO();
				baSpecialDestinationRateTO = (BaSpecialDestinationRateTO) CGObjectConverter
						.createToFromDomain(baSpecialDestDO,
								baSpecialDestinationRateTO);
				state = baSpecialDestinationRateTO.getStateId();
				for (int i = 0; i < j; i++) {
					if (!StringUtil.isEmptyInteger(stateIds[i])
							&& stateIds[i].equals(state)) {
						baSpecialDestinationRateTO.setCityList(cityMap
								.get(state));
						stateExist = Boolean.TRUE;
						break;
					}
				}
				if (!stateExist) {
					stateIds[j] = state;
					cityTOList = rateCommonService.getCityListByStateId(state);
					cityMap.put(baSpecialDestinationRateTO.getStateId(),
							cityTOList);
					if (!CGCollectionUtils.isEmpty(cityTOList)) {
						baSpecialDestinationRateTO.setCityList(cityTOList);
					}
					j++;
				}
				if (!StringUtil.isNull(baSpecialDestDO.getCity())) {
					CityTO cityTO = new CityTO();
					cityTO = (CityTO) CGObjectConverter.createToFromDomain(
							baSpecialDestDO.getCity(), cityTO);
					baSpecialDestinationRateTO.setCityTO(cityTO);
				}

				// BA Rate Weight Slab Rate Id
				if (!StringUtil.isNull(baSpecialDestDO.getBaRateWeightSlabDO())
						&& !StringUtil.isEmptyInteger(baSpecialDestDO
								.getBaRateWeightSlabDO().getBaWeightSlabId())) {
					baSpecialDestinationRateTO
							.setBaWeightSlabId(baSpecialDestDO
									.getBaRateWeightSlabDO()
									.getBaWeightSlabId());
				}

				baSplDestTOList.add(baSpecialDestinationRateTO);
			}
			baRateWeightSlabTO
					.setBaSpecialDestinationRateTOList(baSplDestTOList);
		}
		LOGGER.trace("BARateConfigurationServiceImpl :: _prepareListOFBaSpecialDestinationRateTO() :: END");
	}

	/**
	 * To set flags after save and/or search operation
	 * 
	 * @param baRateHeaderTO
	 * @param baRateHeaderDO
	 * @param session 
	 */
	@SuppressWarnings("unchecked")
	private void _setFlags(BARateHeaderTO baRateHeaderTO,
			BaRateConfigHeaderDO baRateHeaderDO, HttpSession session) {

		// Non-priority
		baRateHeaderTO.setCourierRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setAirCargoRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setTrainRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setCourierChargesCheck(CommonConstants.NO);
		baRateHeaderTO.setCourierRTOCheck(CommonConstants.NO);

		// Priority
		baRateHeaderTO.setPriorityBRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityARatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPrioritySRatesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityChargesCheck(CommonConstants.NO);
		baRateHeaderTO.setPriorityRTOCheck(CommonConstants.NO);

		if (!CGCollectionUtils.isEmpty(baRateHeaderDO.getBaRateProductDO())) {
			Set<BaRateConfigProductHeaderDO> cphDOSet = baRateHeaderDO.getBaRateProductDO();
			for (BaRateConfigProductHeaderDO cphDO : cphDOSet) {
				if (!StringUtil.isEmptyInteger(baRateHeaderTO.getProductCategoryIdForCourier())
						&& cphDO.getRateProductCategory().equals(baRateHeaderTO.getProductCategoryIdForCourier())) {
					// Courier Slab Rates
					baRateHeaderTO.setCourierRatesCheck(CommonConstants.YES);

				} else if (!StringUtil.isEmptyInteger(baRateHeaderTO.getProductCategoryIdForAirCargo())
						&& cphDO.getRateProductCategory().equals(baRateHeaderTO.getProductCategoryIdForAirCargo())) {
					// Air-Cargo Slab Rates
					baRateHeaderTO.setAirCargoRatesCheck(CommonConstants.YES);

				} else if (!StringUtil.isEmptyInteger(baRateHeaderTO.getProductCategoryIdForTrain())
						&& cphDO.getRateProductCategory().equals(baRateHeaderTO.getProductCategoryIdForTrain())) {
					// Train Slab Rates
					baRateHeaderTO.setTrainRatesCheck(CommonConstants.YES);

				} else if (!StringUtil.isEmptyInteger(baRateHeaderTO.getProductCategoryIdForPriority())
						&& cphDO.getRateProductCategory().equals(baRateHeaderTO.getProductCategoryIdForPriority())) {
					// All Priority product - slab rates for B, A, S
					Set<BaRateWeightSlabDO> brwDOSet = cphDO.getBaRateWeightSlabDOs();
					List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
					if (!CGCollectionUtils.isEmpty(brwDOSet) && !CGCollectionUtils.isEmpty(wtSlabList)) {
						for (BaRateWeightSlabDO brwDO : brwDOSet) {
							for (RateWeightSlabsTO rateWeightSlabsTO : wtSlabList) {
								if (brwDO.getStartWeight().getWeightSlabId().equals(rateWeightSlabsTO.getWeightSlabTO().getWeightSlabId())) {
									// Slab rates
									Set<BaRateConfigSlabRateDO> srDOSet = brwDO.getBaSlabRateDO();
									if (!CGCollectionUtils.isEmpty(srDOSet)) {
										for (BaRateConfigSlabRateDO srDO : srDOSet) {
											if (srDO.getServicedOn().equals(UdaanCommonConstants.SERVICED_ON_BEFORE_14_00)) {
												baRateHeaderTO.setPriorityBRatesCheck(CommonConstants.YES);
											} else if (srDO.getServicedOn().equals(UdaanCommonConstants.SERVICED_ON_AFTER_14_00)) {
												baRateHeaderTO.setPriorityARatesCheck(CommonConstants.YES);
											} else if (srDO.getServicedOn().equals(UdaanCommonConstants.SERVICED_ON_SUNDAY)) {
												baRateHeaderTO.setPrioritySRatesCheck(CommonConstants.YES);
											}
										}// INNER FOR EACH - END
									}// IF END
								} // IF wt slab END
							}// FOR wt slab END
						}// FOR - End
					}
				}

				// Fixed Charges
				if (!CGCollectionUtils.isEmpty(baRateHeaderDO
						.getBaAdditionalChargesDO())) {
					for (BARateConfigAdditionalChargesDO additionalDO : baRateHeaderDO
							.getBaAdditionalChargesDO()) {
						if (additionalDO.getPriorityIndicator()
								.equalsIgnoreCase(CommonConstants.NO)) {
							baRateHeaderTO
									.setCourierChargesCheck(CommonConstants.YES);
						} else if (additionalDO.getPriorityIndicator()
								.equalsIgnoreCase(CommonConstants.YES)) {
							baRateHeaderTO
									.setPriorityChargesCheck(CommonConstants.YES);
						}
					} // FOR - END
				}

				// RTO Charges
				if (!CGCollectionUtils.isEmpty(baRateHeaderDO
						.getBaRateRTOChargesDO())) {
					for (BARateConfigRTOChargesDO rtoDO : baRateHeaderDO
							.getBaRateRTOChargesDO()) {
						if (rtoDO.getPriorityIndicator().equalsIgnoreCase(
								CommonConstants.NO)) {
							baRateHeaderTO
									.setCourierRTOCheck(CommonConstants.YES);
						} else if (rtoDO.getPriorityIndicator()
								.equalsIgnoreCase(CommonConstants.YES)) {
							baRateHeaderTO
									.setPriorityRTOCheck(CommonConstants.YES);
						}
					} // FOR - END
				}
			}// FOR EACH - End
		}// If End
	}

	private Set<BaRateWeightSlabDO> _getLatestBaWeightSlabRateDOSet(
			BARateConfigSlabRateTO slabRateTO,
			BaRateConfigProductHeaderDO baRateProductHeaderDO,
			String productCode) throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationServiceImpl :: _getBaWeightSlabRateDOSet() :: START");
		
		Set<BaRateWeightSlabDO> baRateWeightSlabDOs = null;
		
		if (productCode
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)){
			if(!StringUtil.isNull(baRateProductHeaderDO) && !CGCollectionUtils.isEmpty(baRateProductHeaderDO.getBaRateWeightSlabDOs())){
				baRateWeightSlabDOs = baRateProductHeaderDO.getBaRateWeightSlabDOs();
			}
		}
		//Set<BaRateWeightSlabDO> baRateWeightSlabDOs = null;
		if (CGCollectionUtils.isEmpty(baRateWeightSlabDOs)) {
			baRateWeightSlabDOs = new HashSet<BaRateWeightSlabDO>();
		}
		int len = 0;
		if (productCode
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)
				|| productCode
						.equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
			// If product category is Train or Air-Cargo, then it should +1
			// column (Minimum chargeable weight)
			len = slabRateTO.getCourierSplDestcoloumCount() + 1;
		} else if (productCode
				.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)) {
			// Priority Product only
			len = slabRateTO.getPrioritySplDestcoloumCount();
		} else {
			len = slabRateTO.getCourierSplDestcoloumCount();
		}
		for (int i = 0; i < len; i++) {
			// BA rate weight slab - START
			BaRateWeightSlabDO baRateWeightSlabDO = new BaRateWeightSlabDO();
			// Ba weight slab id
			if (!StringUtil.isEmptyInteger(slabRateTO.getBaWeightSlabId()[i])) {
				baRateWeightSlabDO.setBaWeightSlabId(slabRateTO
						.getBaWeightSlabId()[i]);
			}
			// Start weight
			WeightSlabDO startWeight = new WeightSlabDO();
			startWeight.setWeightSlabId(slabRateTO.getStartWeightSlabId()[i]);
			baRateWeightSlabDO.setStartWeight(startWeight);
			// End weight
			WeightSlabDO endWeight = new WeightSlabDO();
			endWeight.setWeightSlabId(slabRateTO.getEndWeightSlabId()[i]);
			baRateWeightSlabDO.setEndWeight(endWeight);
			// Product Header
			baRateWeightSlabDO
					.setBaRateConfigProductHeaderDO(baRateProductHeaderDO);
			// Position - Slab Order
			baRateWeightSlabDO.setSlabOrder(slabRateTO.getPosition()[i]);

			// BA rate weight slab - END
			
			boolean exist = false;
			for(BaRateWeightSlabDO brwsDO  : baRateWeightSlabDOs){
				if(slabRateTO.getPosition()[i].equals(brwsDO.getSlabOrder())){
					brwsDO.setStartWeight(startWeight);
					brwsDO.setEndWeight(endWeight);
				}
				if(brwsDO.getStartWeight().getWeightSlabId().equals(baRateWeightSlabDO.getStartWeight().getWeightSlabId())){
					baRateWeightSlabDO = brwsDO;
					exist = true;
				}
			}
			
			if(!exist){
				baRateWeightSlabDOs.add(baRateWeightSlabDO);
			}
			
				Set<BaRateConfigSlabRateDO> baSlabRateDOSet = null;
					baSlabRateDOSet = new HashSet<BaRateConfigSlabRateDO>();
					
				Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDOSet = null;
					baSpecialDestinationRateDOSet = new HashSet<BARateConfigSpecialDestinationRateDO>();	
				
				if (productCode
							.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)){
					if(!CGCollectionUtils.isEmpty(baRateProductHeaderDO.getBaRateWeightSlabDOs())){
					for(BaRateWeightSlabDO brwsDO  :baRateProductHeaderDO.getBaRateWeightSlabDOs()){
						if(brwsDO.getStartWeight().getWeightSlabId().equals(baRateWeightSlabDO.getStartWeight().getWeightSlabId())){
							if(!CGCollectionUtils.isEmpty(brwsDO.getBaSlabRateDO())){
							for(BaRateConfigSlabRateDO brcsDO  :brwsDO.getBaSlabRateDO()){
								if(!brcsDO.getServicedOn().equals(slabRateTO.getServicedOn())){
									baSlabRateDOSet.add(brcsDO);
								}
							}
							}
							
							if(!CGCollectionUtils.isEmpty(brwsDO.getBaSpecialDestinationRateDO())){
							for(BARateConfigSpecialDestinationRateDO bcsdDO  :brwsDO.getBaSpecialDestinationRateDO()){
								if(!bcsdDO.getServicedOn().equals(slabRateTO.getServicedOn())){
									baSpecialDestinationRateDOSet.add(bcsdDO);
								}
							}
							}
						}
					}
				}
				}
					
					
				
				int secLen = 0;
				if (productCode
						.equalsIgnoreCase(RateCommonConstants.PRO_CODE_AIR_CARGO)
						|| productCode
								.equalsIgnoreCase(RateCommonConstants.PRO_CODE_TRAIN)) {
					secLen = slabRateTO.getDestinationSectorId().length;
				} else {
					secLen = slabRateTO.getRate().length;
				}
				secLen = secLen/len;
				int k = 0;
				for (int j = 0; j < secLen; j++) {
					
					if(j==0){
						k = i;
					}else{
						k = k+len;
					}
					if (!StringUtil.isEmptyDouble(slabRateTO.getRate()[k])
							&& baRateWeightSlabDO.getStartWeight().getWeightSlabId()
									.equals(slabRateTO.getStartWeightSlabId()[k])
							&& baRateWeightSlabDO.getEndWeight().getWeightSlabId()
									.equals(slabRateTO.getEndWeightSlabId()[k])) {
						BaRateConfigSlabRateDO baSlabRateDO = new BaRateConfigSlabRateDO();
						/*if (!StringUtil
								.isEmptyInteger(slabRateTO.getBaSlabRateid()[k])) {
							baSlabRateDO.setBaSlabRateId(slabRateTO
									.getBaSlabRateid()[k]);
						}*/
						baSlabRateDO.setBaRateProductDO(baRateProductHeaderDO);
						baSlabRateDO.setDestinationSector(slabRateTO
								.getDestinationSectorId()[k]);

						// Setting baRateWeightSlabDO
						baSlabRateDO.setBaRateWeightSlabDO(baRateWeightSlabDO);

						baSlabRateDO.setRate(slabRateTO.getRate()[k]);
						if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
							baSlabRateDO.setServicedOn(slabRateTO.getServicedOn());
						}
						baSlabRateDOSet.add(baSlabRateDO);
					}
				}// i - loop end

				baRateWeightSlabDO.setBaSlabRateDO(baSlabRateDOSet);
			// foreach - loop end
			
				
				

					
					
						baRateWeightSlabDO.setBaSpecialDestinationRateDO(baSpecialDestinationRateDOSet);
					int splDestCellCount = slabRateTO.getSpecialDestinationRate().length;
					int colSplLen = splDestCellCount/len;
					int coSplDestRow = 0;
					 k = 0;
						for (int l = 0; l < colSplLen; l++) {
							if (!StringUtil
									.isEmptyInteger(slabRateTO.getStateId()[l])) {
							
							if(l==0){
								k = i;
							}else{
								k = k+len;
							}
							if (!StringUtil.isEmptyDouble(slabRateTO
									.getSpecialDestinationRate()[k])
									/*&& object
											.getStartWeight()
											.getWeightSlabId()
											.equals(slabRateTO.getStartWeightSlabId()[l])
									&& object.getEndWeight().getWeightSlabId()
											.equals(slabRateTO.getEndWeightSlabId()[l])*/) {
								BARateConfigSpecialDestinationRateDO baSpecialDestinationRateDO = new BARateConfigSpecialDestinationRateDO();
								/*if (!StringUtil.isEmptyInteger(slabRateTO
										.getSpecialDestinationId()[l+k])) {
									baSpecialDestinationRateDO
											.setSpecialDestinationId(slabRateTO
													.getSpecialDestinationId()[l+k]);
								}*/
								if (!StringUtil
										.isEmptyInteger(slabRateTO.getCityIds()[l])) {
									CityDO cityDO = new CityDO();
									cityDO.setCityId(slabRateTO.getCityIds()[l]);
									baSpecialDestinationRateDO.setCity(cityDO);
								}
								baSpecialDestinationRateDO.setStateId(slabRateTO
										.getStateId()[l]);

								baSpecialDestinationRateDO
										.setBaRateWeightSlabDO(baRateWeightSlabDO);

								baSpecialDestinationRateDO
										.setBaRateProductDO(baRateProductHeaderDO);
								baSpecialDestinationRateDO.setRate(slabRateTO
										.getSpecialDestinationRate()[k]);
								if (!StringUtils.isEmpty(slabRateTO.getServicedOn())) {
									baSpecialDestinationRateDO.setServicedOn(slabRateTO
											.getServicedOn());
								}
								baSpecialDestinationRateDOSet
										.add(baSpecialDestinationRateDO);
								coSplDestRow++;
							}// IF END
							
							
						}// inner - FOR
					}// IF - END

					baRateWeightSlabDO.setBaSpecialDestinationRateDO(baSpecialDestinationRateDOSet);
				
			
		}

		/*if (productCode.equalsIgnoreCase(RateCommonConstants.PRO_CODE_PRIORITY)) {
			if (!CGCollectionUtils.isEmpty(baRateProductHeaderDO
					.getBaRateWeightSlabDOs())) {
				setAllServicedOnRates(baRateWeightSlabDOs,
						baRateProductHeaderDO);
			}
		}*/

		baRateProductHeaderDO.setBaRateWeightSlabDOs(baRateWeightSlabDOs);
		LOGGER.trace("BARateConfigurationServiceImpl :: _getBaWeightSlabRateDOSet() :: END");
		return baRateWeightSlabDOs;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.rate.configuration.rateConfiguration.service.BARateConfigurationService#getLoggedUserDetails(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public UserInfoTO getLoggedInUserDetails(HttpServletRequest request) {
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTo = (UserInfoTO)session.getAttribute(RateQuotationConstants.USER_INFO);
		return userInfoTo;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.rate.configuration.rateConfiguration.service.BARateConfigurationService#getLoggedInUserIdToStamp(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Integer getLoggedInUserIdToSaveInDatabase(HttpServletRequest request) {
		Integer userId = null;
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTo = (UserInfoTO)session.getAttribute(RateQuotationConstants.USER_INFO);
		if (!StringUtil.isNull(userInfoTo)) {
			userId = userInfoTo.getUserto().getUserId();
		}
		return userId;
	}
}
