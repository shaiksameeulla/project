package com.ff.rate.calculation.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.CustomerCustomerRateTypeDO;
import com.ff.domain.ratemanagement.masters.EBRatePreferenceDO;
import com.ff.domain.ratemanagement.masters.RateComponentCalculatedDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.ratemanagement.masters.RateTaxComponentDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeDO;
import com.ff.domain.ratemanagement.masters.RiskSurchargeInsuredByDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateCalculationCODChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateProductDO;
import com.ff.domain.ratemanagement.operations.ba.BaSlabRateDO;
import com.ff.domain.ratemanagement.operations.ba.BaSpecialDestinationRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashCODChargesDO;
import com.ff.domain.ratemanagement.operations.cash.CashRateHeaderProductDO;
import com.ff.domain.ratemanagement.operations.cash.CashSlabRateDO;
import com.ff.domain.ratemanagement.operations.cash.CashSpecialDestinationDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.CashRateRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationFixedChargesConfigDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationQuotationSlabRateDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateCalculationSpecialDestDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationCODChargeDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationProductCategoryHeaderDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationSlabRateDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.to.rate.RateCalculationInputTO;

public class RateCalculationDAOImpl extends CGBaseDAO implements
		RateCalculationDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RateCalculationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getSectorByPincode(Integer orgCityId, String destPincode)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getSectorByPincode ::START ");
		SectorDO sectorDO = null;
		//String sql = RateCommonConstants.SQL_QRY_GET_SECTOR_BY_PINCODE;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			//Query query = session.createSQLQuery(sql).addEntity(SectorDO.class);
			Query query = session.getNamedQuery("getSectorBySourceAndDestPincode");
			query.setInteger(RateCommonConstants.ORIGIN_CITY, orgCityId);
			query.setString(RateCommonConstants.DEST_PINCODE, destPincode);
			List<SectorDO> sectors = query.list();
			if (sectors != null && !sectors.isEmpty())
				sectorDO = sectors.get(0);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getSectorByPincode ::Exception ",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("RateCalculationDAOImpl::getSectorByPincode ::END ");
		return sectorDO;
	}

	/**
	 * Get Rate Components list
	 * 
	 * @throws CGSystemException
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public List<RateComponentDO> getRateComponentList(
			RateCalculationInputTO rateTO, Integer rateQuotationId)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentList ::START ");
		//String sql = RateCommonConstants.SQL_QRY_GET_RATE_COMPONENTS_LIST_FOR_CREDIT_CUSTOMER;
		List<RateComponentDO> compList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getRateComponentListForCreditCustomer");
			//Query query = session.createSQLQuery(sql);
			query.setInteger("rateQuotationId", rateQuotationId);
			query.setString("productCode", rateTO.getProductCode());
			query.setString("consigCode", rateTO.getConsignmentType());
			compList = query.list();
			//compList = prepareRateComponentList(componentsValue);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentList ::END ");
		return compList;
	}

	/**
	 * Prepare Rate Component List
	 * 
	 * @param components
	 * @return
	 */
	//@SuppressWarnings("rawtypes")
	/*private List<RateComponentDO> prepareRateComponentList(List components)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::prepareRateComponentList ::START ");
		List<RateComponentDO> compList = null;
		if (components != null && !components.isEmpty()) {
			compList = new ArrayList<RateComponentDO>();
			// Preparing components and dependent components list
			int size = components.size();
			for (int i = 0; i < size; i++) {
				Object[] row = (Object[]) components.get(i);
				RateComponentDO compCal = new RateComponentDO();
				if (row[0] != null)
					compCal.setRateComponentId(Integer.parseInt(row[0]
							.toString()));
				if (row[1] != null)
					compCal.setRateGlobalConfigValue(Double.parseDouble(row[1]
							.toString()));
				if (row[2] != null)
					compCal.setRateComponentCode(row[3].toString());
				if (row[3] != null)
					compCal.setRateComponentDesc(row[4].toString());
				if (row[11] != null)
					compCal.setCalculationSequence(Integer.parseInt(row[11]
							.toString()));
				if (row[6] != null)
					compCal.setRateAmountDeviationType(row[6].toString());

				compList.add(compCal);
			}
		}
		LOGGER.debug("RateCalculationDAOImpl::prepareRateComponentList ::END ");
		return compList;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getSectorByPincode(String cityCode)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getSectorByPincode ::START ");
		SectorDO sectorDO = null;
		String hql = RateCommonConstants.QRY_GET_SECTOR_BY_CITY_CODE;
		List<SectorDO> sector = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(hql,
						RateCommonConstants.CITY_CODE, cityCode);
		if (sector != null && !sector.isEmpty())
			sectorDO = sector.get(0);
		LOGGER.debug("RateCalculationDAOImpl::getSectorByPincode ::END ");
		return sectorDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateQuotationDO getQuotationByCustomerCode(String customerCode)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getQuotationByCustomerCode ::START ");
		RateQuotationDO rateQuotationDO = null;
		String hql = RateCommonConstants.QRY_GET_QUOTATION_BY_CUSTOMER;
		List<RateQuotationDO> quotations = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(hql,
						RateCommonConstants.CUSTOMER_CODE, customerCode);
		if (quotations != null && !quotations.isEmpty()) {
			rateQuotationDO = quotations.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getQuotationByCustomerCode ::END ");
		return rateQuotationDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateQuotationProductCategoryHeaderDO getQuotationDetailsByCustomerCode(
			String customerCode) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getQuotationDetailsByCustomerCode ::START ");
		RateQuotationProductCategoryHeaderDO rateQuotationDO = null;
		String hql = RateCommonConstants.QRY_GET_CUSTOMER_QUOTATION_DETAILS;
		List<RateQuotationProductCategoryHeaderDO> quotations = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(hql,
						RateCommonConstants.CUSTOMER_CODE, customerCode);
		if (quotations != null && !quotations.isEmpty()) {
			rateQuotationDO = quotations.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getQuotationDetailsByCustomerCode ::END ");
		return rateQuotationDO;
	}

/*	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationSpecialDestinationDO> getSpecialDestByQuotationNPincode(
			RateCalculationInputTO rateTO) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getSpecialDestByQuotationNPincode ::START ");
		List<RateQuotationSpecialDestinationDO> rateQuotationSpecialDestinations = null;
		// productCategoryCode, customerCode
		String[] paramNames = { RateCommonConstants.PRODUCT_SERIES,
				RateCommonConstants.CUSTOMER_CODE,
				RateCommonConstants.DEST_PINCODE };
		Object[] values = { rateTO.getProductCode(), rateTO.getCustomerCode(),
				rateTO.getDestinationPincode() };

		rateQuotationSpecialDestinations = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RATE_QUOTATION_SPECIAL_DESTINATION,
						paramNames, values);
		LOGGER.debug("RateCalculationDAOImpl::getSpecialDestByQuotationNPincode ::END ");
		return rateQuotationSpecialDestinations;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationSlabRateDO> getCourierQuotaionSlabsByCustomer(
			String customerCode, Integer sectorId) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCourierQuotaionSlabsByCustomer ::START ");
		String hql = RateCommonConstants.QRY_GET_COURIER_PRO_SLABS_BY_CUSTOMER_SECTOR;
		String[] params = { RateCommonConstants.CUSTOMER_CODE,
				RateCommonConstants.DEST_SECTOR };
		Object[] values = { customerCode, sectorId };
		LOGGER.debug("RateCalculationDAOImpl::getCourierQuotaionSlabsByCustomer ::END ");
		return getHibernateTemplate().findByNamedQueryAndNamedParam(hql,
				params, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateQuotationSlabRateDO> getRateSlabsByCustomerProductNSector(
			String customerCode, String productSeries, Integer orgSector,
			Integer destSector) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateSlabsByCustomerProductNSector ::START ");
		String hql = RateCommonConstants.QRY_GET_SLAB_BY_CUSTOMER_PRODUCT_AND_SECTOR;
		String[] params = { RateCommonConstants.CUSTOMER_CODE,
				RateCommonConstants.PRODUCT_SERIES,
				RateCommonConstants.ORIGIN_SECTOR_ID,
				RateCommonConstants.DESTSECTOR };
		Object[] values = { customerCode, productSeries, orgSector, destSector };
		LOGGER.debug("RateCalculationDAOImpl::getRateSlabsByCustomerProductNSector ::END ");
		return getHibernateTemplate().findByNamedQueryAndNamedParam(hql,
				params, values);
	}

	@SuppressWarnings("rawtypes")
	public List getDataByNamedQueryAndNamedParam(String hql, String[] params,
			Object[] values) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getDataByNamedQueryAndNamedParam");
		return getHibernateTemplate().findByNamedQueryAndNamedParam(hql,
				params, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateQuotationSlabRateDO getRateQuotationSlabRateForOtherThanSpecialDest(
			Integer orgSector, Integer destSector, RateCalculationInputTO rateTO)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateQuotationSlabRateForOtherThanSpecialDest ::START ");
		RateQuotationSlabRateDO rateQuotationSlabRate = null;
		List<RateQuotationSlabRateDO> rateQuotationSlabRates = null;
		String[] paramNames = { RateCommonConstants.ORIGIN_SECTOR,
				RateCommonConstants.DEST_SECTOR,
				RateCommonConstants.RATE_QUOTATION_PRODUCT_CATEGY_HEADER,
				RateCommonConstants.WEIGHT,

		};
		Object[] values = { orgSector, destSector,
				rateTO.getRateQuotationProductCategoryHeader(),
				rateTO.getWeight(), };
		rateQuotationSlabRates = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RATE_QUOTATION_OTHER_THAN_SPECIAL_DESTINATION,
						paramNames, values);
		rateQuotationSlabRate = !StringUtil.isEmptyList(rateQuotationSlabRates) ? rateQuotationSlabRates
				.get(0) : null;
		LOGGER.debug("RateCalculationDAOImpl::getRateQuotationSlabRateForOtherThanSpecialDest ::END ");
		return rateQuotationSlabRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.rate.calculation.dao.RateCalculationDAO#calculateCODCharge(java
	 * .lang.Double, com.ff.to.rate.RateCalculationInputTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RateQuotationCODChargeDO calculateCODCharge(Double appliedOn,
			Integer rateQuotation) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::calculateCODCharge ::START ");
		RateQuotationCODChargeDO rateQuotationCODCharge = null;
		List<RateQuotationCODChargeDO> rateQuotationCODCharges = null;

		String[] paramNames = { RateCommonConstants.COD_CHARGE_SLAB,
				RateCommonConstants.RATE_QUOTATION_NUMBER, };
		Object[] values = { appliedOn, rateQuotation };

		rateQuotationCODCharges = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_CALCULATED_COD_CHARGE,
						paramNames, values);

		rateQuotationCODCharge = !StringUtil
				.isEmptyList(rateQuotationCODCharges) ? rateQuotationCODCharges
				.get(0) : null;
		LOGGER.debug("RateCalculationDAOImpl::calculateCODCharge ::END ");
		return rateQuotationCODCharge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateComponentDO> getAllRateComponaent()
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getAllRateComponaent ::START");
		List<RateComponentDO> rateComp = getHibernateTemplate()
				.findByNamedQuery(RateCommonConstants.GET_ALL_RATE_COMP);
		LOGGER.debug("RateCalculationDAOImpl::getAllRateComponaent ::END");
		return rateComp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCity(String Pincode) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCity ::START");
		CityDO city = null;
		try {
			List<CityDO> cityDOs = null;
			String queryName = RateCommonConstants.QRY_GET_CITY;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, RateCommonConstants.PINCODE, Pincode);
			if (!StringUtil.isEmptyList(cityDOs)) {
				city = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : RateCalculationDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCalculationDAOImpl::getCity ::END");
		return city;
	}

	/**
	 * Returns all dependent components for calculation
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RateComponentCalculatedDO> getDependentRateComponentForCalculation()
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getDependentRateComponentForCalculation ::START");
		List<RateComponentCalculatedDO> rccDOList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query qry = session
					.getNamedQuery(RateCommonConstants.QRY_GET_DEPENDENT_COMPONENTS_FOR_RATE_CALCULATION);
			rccDOList = qry.list();
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getDependentRateComponentForCalculation ::END");
		return rccDOList;
	}

	/**
	 * Get Quotation For Customer and Product
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RateCalculationProductCategoryHeaderDO getQuotationForCustomerAndProduct(
			RateCalculationInputTO input, String rateQuotationType)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getQuotationForCustomerAndProduct ::START");
		List<RateCalculationProductCategoryHeaderDO> rateProductHeader = null;
		RateCalculationProductCategoryHeaderDO productHeader = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_GET_RATE_CALCULATION_PRODUCT_HEADER);
			query.setParameter(RateCommonConstants.PRODUCT_CODE,
					input.getProductCode());
			query.setParameter(RateCommonConstants.CUSTOMER_CODE,
					input.getCustomerCode());
			query.setParameter(RateCommonConstants.RATE_QUOTATION_TYPE,
					rateQuotationType);
			query.setParameter(RateCommonConstants.CURRENT_DATE, DateUtil
					.slashDelimitedstringToDDMMYYYYFormat(input
							.getCalculationRequestDate()));
			query.setMaxResults(RateCommonConstants.MAX_RESULT_SET_COUNT);
			query.setCacheable(false);
			rateProductHeader = query.list();
			productHeader = !StringUtil.isEmptyList(rateProductHeader) ? rateProductHeader
					.get(0) : null;
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getQuotationForCustomerAndProduct ::END");
		return productHeader;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * To get Normal Slabs
	 */
	public List<RateCalculationQuotationSlabRateDO> getNormalRateSlabs(
			Integer rateQuotationProductCategoryHeaderId, Integer originSector,
			Integer destinationSector, String isOriginConsidered, String rateCalculatedFor)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getNormalRateSlabs ::START");
		List<RateCalculationQuotationSlabRateDO> normalSlabs = null;
		String[] paramNames = {
				RateCommonConstants.RATE_QUOTATION_PRODUCT_CATEGY_HEADER_ID,
				RateCommonConstants.ORIGIN_SECTOR_ID,
				RateCommonConstants.DEST_SECTOR_ID,
				RateCommonConstants.ORIGIN_CONSIDERED,
				RateCommonConstants.RATE_CALCULATED_FOR };
		Object[] values = { rateQuotationProductCategoryHeaderId, originSector,
				destinationSector, isOriginConsidered,rateCalculatedFor };

		normalSlabs = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_NORMAL_SLABS, paramNames, values);
		LOGGER.debug("RateCalculationDAOImpl::getNormalRateSlabs ::END");
		return normalSlabs;
	}

	/**
	 * It gives special Destination Slabs
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RateCalculationSpecialDestDO> getSpecialDestinationSlabs(
			Integer cityId, Integer stateId,Integer rateQuotationProductCategoryHeaderId,
			String originConsidered, Integer originSector, String rateCalculatedFor)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getSpecialDestinationSlabs ::START");
		List<RateCalculationSpecialDestDO> specialDestinationSlabs = null;
		String[] paramNames = {
				RateCommonConstants.RATE_QUOTATION_PRODUCT_CATEGY_HEADER_ID,
				RateCommonConstants.CITY_ID,
				RateCommonConstants.STATE_ID,
				RateCommonConstants.ORIGIN_SECTOR_ID,
				RateCommonConstants.ORIGIN_CONSIDERED,
				RateCommonConstants.RATE_CALCULATED_FOR};
		Object[] values = { rateQuotationProductCategoryHeaderId, cityId, stateId,
				originSector, originConsidered, rateCalculatedFor};

		specialDestinationSlabs = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_SPECIAL_DESTINATION_SLABS,
						paramNames, values);
		LOGGER.debug("RateCalculationDAOImpl::getSpecialDestinationSlabs ::END");
		return specialDestinationSlabs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForQuotation(
			Integer rateQuotationId) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForQuotation ::START");
		List fixedChargesConfigForQuotation = null;
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = null;
		String[] paramNames = { RateCommonConstants.QUOTATION_ID, };
		Object[] values = { rateQuotationId };
		fixedChargesConfigForQuotation = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_FIX_CHARGES_CONFIG_FOR_QUOTATION,
						paramNames, values);
		if (fixedChargesConfigForQuotation != null
				&& !fixedChargesConfigForQuotation.isEmpty()) {
			Object[] fixedChargesConfig = (fixedChargesConfigForQuotation == null) ? null
					: (Object[]) fixedChargesConfigForQuotation.get(0);
			if (fixedChargesConfig != null) {
				fixedChargesConfigResult = new RateCalculationFixedChargesConfigDO();
				if (fixedChargesConfig[0] != null) {
					fixedChargesConfigResult
							.setOctroiBourneBy(fixedChargesConfig[0].toString());
				}
				if (fixedChargesConfig[1] != null) {
					fixedChargesConfigResult.setInsuredby(fixedChargesConfig[1]
							.toString());
				}
				if (fixedChargesConfig[2] != null) {
					fixedChargesConfigResult.setPercentile(Double
							.parseDouble(fixedChargesConfig[2].toString()));
				}
			}
		}
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForQuotation ::END");
		return fixedChargesConfigResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RiskSurchargeDO getRiskSurcharge(Double declaredValue,
			Integer customerCategory) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRiskSurcharge ::START");
		List<RiskSurchargeDO> riskSurcharges = null;
		RiskSurchargeDO riskSurcharge = null;
		String[] paramNames = { RateCommonConstants.DECLARED_VALUE,
				RateCommonConstants.CUSTOMER_CATEGORY };
		Object[] values = { declaredValue, customerCategory };

		riskSurcharges = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_RISK_SURCHARGE, paramNames, values);

		riskSurcharge = (riskSurcharges == null || riskSurcharges.isEmpty()) ? null
				: riskSurcharges.get(0);
		LOGGER.debug("RateCalculationDAOImpl::getRiskSurcharge ::END");
		return riskSurcharge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RateQuotationRTOChargesDO getRTOChargesForRateQuotation(
			Integer rateQuotationId) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForRateQuotation ::START");
		List<RateQuotationRTOChargesDO> rateQuotationRTOCharges = null;
		RateQuotationRTOChargesDO rateQuotationRTOCharge = null;
		String[] paramNames = { RateCommonConstants.QUOTATION_ID, };
		Object[] values = { rateQuotationId };

		rateQuotationRTOCharges = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RTO_CHARGES_FOR_RATE_QUOTATION,
						paramNames, values);
		if (rateQuotationRTOCharges != null
				&& !rateQuotationRTOCharges.isEmpty()) {
			rateQuotationRTOCharge = (rateQuotationRTOCharges == null || rateQuotationRTOCharges
					.isEmpty()) ? null : rateQuotationRTOCharges.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForRateQuotation ::END");
		return rateQuotationRTOCharge;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateTaxComponentDO> getTaxComponents(Integer stateId,
			Date currentDate, String taxGroup) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getTaxComponents ::START");
		List<RateTaxComponentDO> rateTaxComponents = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_GET_TAX_COMPONENTS);
			query.setParameter(RateCommonConstants.STATE_ID, stateId);
			query.setParameter(RateCommonConstants.CURRENT_DATE, currentDate);
			query.setParameter(RateCommonConstants.TAX_GROUP, taxGroup);
			rateTaxComponents = query.list();
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getTaxComponents ::END");
		return rateTaxComponents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RiskSurchargeInsuredByDO getInsuredBy(Integer insuredBy)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getInsuredBy ::START");
		List<RiskSurchargeInsuredByDO> riskSurchargeInsuredBys = null;
		RiskSurchargeInsuredByDO riskSurchargeInsuredBy = null;
		String[] paramNames = { RateCommonConstants.INSURED_BY };
		Object[] values = { insuredBy };

		riskSurchargeInsuredBys = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_INSURED_BY, paramNames,
						values);
		riskSurchargeInsuredBy = (riskSurchargeInsuredBys == null || riskSurchargeInsuredBys
				.isEmpty()) ? null : riskSurchargeInsuredBys.get(0);
		LOGGER.debug("RateCalculationDAOImpl::getInsuredBy ::START");
		return riskSurchargeInsuredBy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaSlabRateDO> getBANormalRateSlabs(
			Integer productCategoryHeaderId, Integer originSector,
			Integer destinationSector, String servicedOn)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getBANormalRateSlabs ::START");
		List<BaSlabRateDO> normalSlabs = null;
		Session session = null;
		Transaction tx = null;
		try {
			Criteria cr = null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			cr = session.createCriteria(BaSlabRateDO.class, "slabRate");
			cr.add(Restrictions.eq("slabRate.baProductHeaderId",
					productCategoryHeaderId));
			Criterion originSec = Restrictions.eq("slabRate.originSector",
					originSector);
			Criterion originSecIsNull = Restrictions
					.isNull("slabRate.originSector");
			cr.add(Restrictions.or(originSec, originSecIsNull));
			cr.add(Restrictions.eq("slabRate.destinationSector",
					destinationSector));
			if (!StringUtil.isStringEmpty(servicedOn)) {
				cr.add(Restrictions.eq("slabRate.servicedOn", servicedOn));
			}
			cr.createAlias("slabRate.weightSlab", "weightSlab");
			cr.addOrder(Order.asc("weightSlab.slabOrder"));
			normalSlabs = cr.list();
			tx.commit();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR ::RateCalculationDAOImpl::getBANormalRateSlabs ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getBANormalRateSlabs ::END");
		return normalSlabs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaSpecialDestinationRateDO> getBASpecialDestinationSlabs(
			Integer cityId, Integer stateId, Integer rateProductCategoryHeaderId,
			String servicedOn) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getBASpecialDestinationSlabs ::START");
		List<BaSpecialDestinationRateDO> specialDestinationSlabs = null;
		Session session = null;
		Transaction tx = null;
		try {
			DetachedCriteria getSplDestCount = DetachedCriteria
					.forClass(BaSpecialDestinationRateDO.class, "baSplDestDetachedQry");
					getSplDestCount.add(Restrictions.eq("baSplDestDetachedQry.stateId", stateId));
					getSplDestCount.add(Restrictions.eq("baSplDestDetachedQry.cityId", cityId));
					getSplDestCount.add(Restrictions.eq("baSplDestDetachedQry.rateProductHeaderId", rateProductCategoryHeaderId));
					if (!StringUtil.isStringEmpty(servicedOn)) {
						getSplDestCount.add(Restrictions.eq("baSplDestDetachedQry.servicedOn", servicedOn));
					}
					getSplDestCount.setProjection(Projections.rowCount());
			Criteria cr = null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			cr = session.createCriteria(BaSpecialDestinationRateDO.class,
					"baSplDest");
			cr.add(Restrictions.eq("baSplDest.rateProductHeaderId",
					rateProductCategoryHeaderId));
			cr.add(Restrictions.eq("baSplDest.stateId", stateId));
			cr.add(Restrictions.or(Restrictions.eq("baSplDest.cityId", cityId),
					Restrictions.and(Restrictions.isNull("baSplDest.cityId"),
							Subqueries.eq(new Long(0), getSplDestCount))));

			if (!StringUtil.isStringEmpty(servicedOn)) {
				cr.add(Restrictions.eq("baSplDest.servicedOn", servicedOn));
			}
			cr.createAlias("baSplDest.weightSlab", "weightSlab");
			cr.addOrder(Order.asc("weightSlab.slabOrder"));
			specialDestinationSlabs = cr.list();
			tx.commit();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR ::RateCalculationDAOImpl::getBASpecialDestinationSlabs ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getBASpecialDestinationSlabs ::END");
		return specialDestinationSlabs;
	}

	@Override
	public BaRateProductDO getBaRateProductHeader(RateCalculationInputTO rateTO)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getBaRateProductHeader ::START");
		BaRateProductDO rateHeader = null;

		String[] paramNames = { "cityCode", RateCommonConstants.PRODUCT_CODE,
				RateCommonConstants.CURRENT_DATE, "baCustomerCode" };
		Object[] values = {
				rateTO.getOriginCityCode(),
				rateTO.getProductCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(rateTO
						.getCalculationRequestDate()), rateTO.getCustomerCode() };
		@SuppressWarnings("unchecked")
		List<BaRateProductDO> rateHeaderList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam("getBARateProductHeader",
						paramNames, values);
		if (rateHeaderList != null && !rateHeaderList.isEmpty())
			rateHeader = rateHeaderList.get(0);
		LOGGER.debug("RateCalculationDAOImpl::getBaRateProductHeader ::END");
		return rateHeader;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CityDO getCityByCityCode(String originCityCode)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCityByCityCode ::START");
		CityDO city = null;
		try {
			List<CityDO> cityDOs = null;
			String queryName = RateCommonConstants.QRY_GET_CITY_BY_CITY_CODE;
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, RateCommonConstants.CITY_CODE, originCityCode);
			if (!StringUtil.isEmptyList(cityDOs)) {
				city = cityDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : RateCalculationDAOImpl.getCity", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCalculationDAOImpl::getCityByCityCode ::END");
		return city;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<RateComponentDO> getRateComponentListForBA(
			RateCalculationInputTO rateTO, Integer headerId, String priorityInd)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForBA ::START");
		//String sql = RateCommonConstants.SQL_QRY_GET_RATE_COMPONENTS_LIST_FOR_BA;
		Session session = null;
		List<RateComponentDO> compList = null;
		//List componentsValue = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getRateComponentListForBA");
			//Query query = session.createSQLQuery(sql);
			query.setInteger("headerId", headerId);
			query.setString("priorityInd", priorityInd);
			query.setString("productCode", rateTO.getProductCode());
			query.setString("consigCode", rateTO.getConsignmentType());
			compList = query.list();
			//compList = prepareRateComponentList(componentsValue);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getRateComponentListForBA ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForBA ::END");
		return compList;
	}

	@SuppressWarnings("unchecked")
	public CashRateHeaderProductDO getCashProductHeaderMap(
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCashProductHeaderMap ::START");
		List<CashRateHeaderProductDO> rateProductHeader = null;
		CashRateHeaderProductDO productHeader = null;
		// productCategoryCode, customerCode

		String[] paramNames = { RateCommonConstants.CITY_CODE,
				RateCommonConstants.CURRENT_DATE,
				RateCommonConstants.PRODUCT_CODE };
		Object[] values = {
				input.getOriginCityCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()), input.getProductCode() };
		rateProductHeader = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RATE_CALCULATION_PRODUCT_HEADER_FOR_CASH,
						paramNames, values);
		productHeader = !StringUtil.isEmptyList(rateProductHeader) ? rateProductHeader
				.get(0) : null;
		LOGGER.debug("RateCalculationDAOImpl::getCashProductHeaderMap ::END");
		return productHeader;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RateComponentDO> getRateComponentListForCash(
			RateCalculationInputTO rateTO, Integer headerId, String priorityInd)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForCash ::START");
		Session session = null;
		List<RateComponentDO> compList = null;
		//List componentsValue = null;
		//String sql = RateCommonConstants.SQL_QRY_GET_RATE_COMPONENTS_LIST_FOR_CASH;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			//Query query = session.createSQLQuery(sql);
			Query query = session.getNamedQuery("getRateComponentListForCashAccUser");
			//Query query = session.createSQLQuery(sql);
			query.setInteger("headerId", headerId);
			query.setString("priorityInd", priorityInd);
			query.setString("productCode", rateTO.getProductCode());
			query.setString("consigCode", rateTO.getConsignmentType());
			compList = query.list();
			//compList = prepareRateComponentList(componentsValue);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getRateComponentListForCash ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForCash ::END");
		return compList;
	}

	@SuppressWarnings("unchecked")
	public List<CashSlabRateDO> getCashNormalRateSlabs(
			Integer productCategoryHeaderId, Integer originSector,
			Integer destinationSector, String servicedOn,
			String originConsidered) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCashNormalRateSlabs ::START");
		List<CashSlabRateDO> normalSlabs = null;
		Session session = null;
		try {
			Criteria cr = null;
			session = getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(CashSlabRateDO.class, "slabRate");
			cr.add(Restrictions.eq("slabRate.rateProductMapId",
					productCategoryHeaderId));
			cr.add(Restrictions.eq("slabRate.destinationSectorId",
					destinationSector));
			if (originConsidered.equalsIgnoreCase(RateCommonConstants.NO)) {
				cr.add(Restrictions.isNull("slabRate.originSectorId"));
			}
			if (originConsidered.equalsIgnoreCase(RateCommonConstants.YES)) {
				cr.add(Restrictions.eq("slabRate.originSectorId", originSector));
			}
			if (!StringUtil.isStringEmpty(servicedOn)) {
				cr.add(Restrictions.eq("slabRate.servicedOn", servicedOn));
			}
			cr.createAlias("slabRate.weightSlabDO", "weightSlab");
			cr.addOrder(Order.asc("weightSlab.sequenceOrder"));
			normalSlabs = cr.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getCashNormalRateSlabs ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getCashNormalRateSlabs ::END");
		return normalSlabs;
	}

	@SuppressWarnings("unchecked")
	public List<CashSpecialDestinationDO> getCashSpecialDestinationSlabs(
			Integer cityId, Integer stateId, Integer rateProductCategoryHeaderId,
			String servicedOn, String originConsidered, Integer originSector)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCashSpecialDestinationSlabs ::START");
		List<CashSpecialDestinationDO> specialDestinationSlabs = null;
		Session session = null;
		try {
			Criteria cr = null;
			DetachedCriteria getSplDestCount = DetachedCriteria
					.forClass(CashSpecialDestinationDO.class, "cashSplDestDetachedQry");
					getSplDestCount.add(Restrictions.eq("cashSplDestDetachedQry.stateId", stateId));
					getSplDestCount.add(Restrictions.eq("cashSplDestDetachedQry.cityId", cityId));
					getSplDestCount.add(Restrictions.eq("cashSplDestDetachedQry.productHeaderMap", rateProductCategoryHeaderId));
					if (!StringUtil.isStringEmpty(servicedOn)) {
						getSplDestCount.add(Restrictions.eq("cashSplDestDetachedQry.servicedOn", servicedOn));
					}
					if (originConsidered.equalsIgnoreCase(RateCommonConstants.NO)) {
						getSplDestCount.add(Restrictions.isNull("cashSplDestDetachedQry.originSector"));
					}
					if (originConsidered.equalsIgnoreCase(RateCommonConstants.YES)) {
						getSplDestCount.add(Restrictions.eq("cashSplDestDetachedQry.originSector", originSector));
					}
					getSplDestCount.setProjection(Projections.rowCount());
			
			session = getHibernateTemplate().getSessionFactory().openSession();
			cr = session.createCriteria(CashSpecialDestinationDO.class,
					"splDest");
			cr.add(Restrictions.eq("splDest.productHeaderMap",
					rateProductCategoryHeaderId));
			cr.add(Restrictions.eq("splDest.stateId", stateId));
			cr.add(Restrictions.or(Restrictions.eq("splDest.cityId", cityId),
					Restrictions.and(Restrictions.isNull("splDest.cityId"),
							Subqueries.eq(new Long(0), getSplDestCount))));
			if (originConsidered.equalsIgnoreCase(RateCommonConstants.NO)) {
				cr.add(Restrictions.isNull("splDest.originSector"));
			}
			if (originConsidered.equalsIgnoreCase(RateCommonConstants.YES)) {
				cr.add(Restrictions.eq("splDest.originSector", originSector));
			}
			if (!StringUtil.isStringEmpty(servicedOn)) {
				cr.add(Restrictions.eq("splDest.servicedOn", servicedOn));
			}
			cr.createAlias("splDest.weightSlab", "wthSlab");
			cr.addOrder(Order.asc("wthSlab.sequenceOrder"));
			specialDestinationSlabs = cr.list();
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getCashSpecialDestinationSlabs ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getCashSpecialDestinationSlabs ::END");
		return specialDestinationSlabs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaRateCalculationCODChargesDO calculateBACODCharge(double declaredValue,
			int headerId, String priorityInd) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::calculateBACODCharge ::START");
		BaRateCalculationCODChargesDO baCODChargesDO = null;
		List<BaRateCalculationCODChargesDO> baCODCharges = null;

		String[] paramNames = { RateCommonConstants.COD_CHARGE_SLAB,
				RateCommonConstants.BA_RATE_PRODUCT_CATEGY_HEADER_ID, RateCommonConstants.PRIORITY_IND};
		Object[] values = { declaredValue, headerId, priorityInd };

		baCODCharges = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_CALCULATED_BA_COD_CHARGE,
				paramNames, values);

		baCODChargesDO = !StringUtil.isEmptyList(baCODCharges) ? baCODCharges
				.get(0) : null;
		LOGGER.debug("RateCalculationDAOImpl::calculateBACODCharge ::END");
		return baCODChargesDO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForBA(
			int baHeaderId, String priorityInd) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForBA ::START");
		List fixedChargesConfigForQuotation = null;
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = null;
		String[] paramNames = { RateCommonConstants.BA_RATE_PRODUCT_CATEGY_HEADER_ID, RateCommonConstants.PRIORITY_IND};
		Object[] values = { baHeaderId, priorityInd};

		fixedChargesConfigForQuotation = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_FIX_CHARGES_CONFIG_FOR_BA,
						paramNames, values);
		if (fixedChargesConfigForQuotation != null
				&& !fixedChargesConfigForQuotation.isEmpty()) {
			Object[] fixedChargesConfig = (fixedChargesConfigForQuotation == null) ? null
					: (Object[]) fixedChargesConfigForQuotation.get(0);
			if (fixedChargesConfig != null) {
				fixedChargesConfigResult = new RateCalculationFixedChargesConfigDO();
				fixedChargesConfigResult
						.setOctroiBourneBy(fixedChargesConfig[0].toString());
				fixedChargesConfigResult.setInsuredby(fixedChargesConfig[1]
						.toString());
				fixedChargesConfigResult.setPercentile(Double
						.parseDouble(fixedChargesConfig[2].toString()));
			}
		}
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForBA ::END");
		return fixedChargesConfigResult;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RateCalculationFixedChargesConfigDO getFixedChargesConfigForCash(
			Integer headerId, String priorityInd) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForCash ::START");
		List fixedChargesConfigForCash = null;
		RateCalculationFixedChargesConfigDO fixedChargesConfigResult = null;
		String[] paramNames = { RateCommonConstants.RATE_PRODUCT_CATEGY_HEADER_ID,
				RateCommonConstants.PRIORITY_IND};
		Object[] values = { headerId, priorityInd};

		fixedChargesConfigForCash = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_FIX_CHARGES_CONFIG_FOR_CASH,
						paramNames, values);
		if (fixedChargesConfigForCash != null
				&& !fixedChargesConfigForCash.isEmpty()) {
			Object[] fixedChargesConfig = (fixedChargesConfigForCash == null) ? null
					: (Object[]) fixedChargesConfigForCash.get(0);
			if (fixedChargesConfig != null) {
				fixedChargesConfigResult = new RateCalculationFixedChargesConfigDO();
				fixedChargesConfigResult
						.setOctroiBourneBy(fixedChargesConfig[0].toString());
				fixedChargesConfigResult.setInsuredby(fixedChargesConfig[1]
						.toString());
				fixedChargesConfigResult.setPercentile(Double
						.parseDouble(fixedChargesConfig[2].toString()));
			}
		}
		LOGGER.debug("RateCalculationDAOImpl::getFixedChargesConfigForCash ::END");
		return fixedChargesConfigResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashCODChargesDO calculateCashCODCharge(double declaredValue,
			Integer headerProductMapId) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::calculateCashCODCharge ::START");
		CashCODChargesDO cashCODChargesDO = null;
		List<CashCODChargesDO> cashCODCharges = null;

		String[] paramNames = { RateCommonConstants.COD_CHARGE_SLAB,
				RateCommonConstants.RATE_PRODUCT_CATEGY_HEADER_ID, };
		Object[] values = { declaredValue, headerProductMapId };

		cashCODCharges = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_CALCULATED_CASH_COD_CHARGE,
				paramNames, values);

		cashCODChargesDO = !StringUtil.isEmptyList(cashCODCharges) ? cashCODCharges
				.get(0) : null;
		LOGGER.debug("RateCalculationDAOImpl::calculateCashCODCharge ::END");
		return cashCODChargesDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BARateRTOChargesDO getRTOChargesForBA(int baRateHeaderId, String priorityInd)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForBA ::START");
		List<BARateRTOChargesDO> baRateRTOCharges = null;
		BARateRTOChargesDO baRateRTOChargesDO = null;
		String[] paramNames = { RateCommonConstants.RATE_PRODUCT_CATEGY_HEADER_ID, RateCommonConstants.PRIORITY_IND};
		Object[] values = { baRateHeaderId, priorityInd};

		baRateRTOCharges = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RTO_CHARGES_FOR_BA,
						paramNames, values);
		if (baRateRTOCharges != null && !baRateRTOCharges.isEmpty()) {
			baRateRTOChargesDO = (baRateRTOCharges == null || baRateRTOCharges
					.isEmpty()) ? null : baRateRTOCharges.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForBA ::END");
		return baRateRTOChargesDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashRateRTOChargesDO getRTOChargesForCash(Integer headerId,
			String priorityInd) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForCash ::START");
		List<CashRateRTOChargesDO> cashRateRTOCharges = null;
		CashRateRTOChargesDO cashRateRTOChargesDO = null;
		String[] paramNames = {
				RateCommonConstants.RATE_PRODUCT_CATEGY_HEADER_ID,
				RateCommonConstants.RATE_PRIORITY_IND };
		Object[] values = { headerId, priorityInd };

		cashRateRTOCharges = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_RTO_CHARGES_FOR_CASH,
						paramNames, values);
		if (cashRateRTOCharges != null && !cashRateRTOCharges.isEmpty()) {
			cashRateRTOChargesDO = (cashRateRTOCharges == null || cashRateRTOCharges
					.isEmpty()) ? null : cashRateRTOCharges.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getRTOChargesForCash ::END");
		return cashRateRTOChargesDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EBRatePreferenceDO getEBRatePreferenceDetails(
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getEBRatePreferenceDetails ::START");
		List<EBRatePreferenceDO> ebRatePreferences = null;
		EBRatePreferenceDO ebRate = null;
		String[] paramNames = { RateCommonConstants.RATE_PREFERENCES,
				RateCommonConstants.RATE_STATE,
				RateCommonConstants.CURRENT_DATE };
		Object[] values = {
				input.getEbPreference(),
				input.getOriginCityTO().getState(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()) };

		ebRatePreferences = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_EB_RATE_PREFERENCE_DETAILS,
						paramNames, values);
		if (ebRatePreferences != null && !ebRatePreferences.isEmpty()) {
			ebRate = (ebRatePreferences == null || ebRatePreferences
					.isEmpty()) ? null : ebRatePreferences.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getEBRatePreferenceDetails ::END");
		return ebRate;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<RateComponentDO> getRateComponentListForCash(
			RateCalculationInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForCash ::START");
		Session session = null;
		List<RateComponentDO> compList = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getRateComponentListForCash");
			// Query query = session.createSQLQuery(sql);
			query.setString("productCode", input.getProductCode());
			query.setString("consgCode", input.getConsignmentType());

			compList = query.list();
			// List<RateComponentDO> compList =
			// prepareRateComponentListForEB(componentsValue);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getRateComponentListForCash ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}

		LOGGER.debug("RateCalculationDAOImpl::getRateComponentListForCash ::END");
		return compList;
	}

	/*
	 * @SuppressWarnings("rawtypes") 
	 */

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean isProductValidForCreditCustomerContract(
			ProductToBeValidatedInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForCreditCustomerContract ::START");
		boolean isproductValid = false;
		String[] paramNames = { RateCommonConstants.CUSTOMER_CODE,
				RateCommonConstants.PRODUCT_CODE,
				RateCommonConstants.CURRENT_DATE };
		Object[] values = {
				input.getCustomerCode(),
				input.getProductCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()) };
		List productValue = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						"isProductValidForCreditCustomerContract", paramNames,
						values);
		if (productValue != null && !productValue.isEmpty())
			isproductValid = true;
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForCreditCustomerContract ::END");
		return isproductValid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean isProductValidForCashContract(
			ProductToBeValidatedInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForCashContract ::START");
		boolean isproductValid = false;
		String[] paramNames = { RateCommonConstants.CITY_CODE,
				RateCommonConstants.PRODUCT_CODE,
				RateCommonConstants.CURRENT_DATE };
		Object[] values = {
				input.getOriginCityCode(),
				input.getProductCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()) };
		List productValue = getHibernateTemplate()
				.findByNamedQueryAndNamedParam("isProductValidForCashContract",
						paramNames, values);
		if (productValue != null && !productValue.isEmpty())
			isproductValid = true;
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForCashContract ::END");
		return isproductValid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean isProductValidForBACustomerContract(
			ProductToBeValidatedInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForBACustomerContract ::START");
		boolean isproductValid = false;
		String[] paramNames = { RateCommonConstants.CITY_CODE,
				RateCommonConstants.PRODUCT_CODE,
				RateCommonConstants.CURRENT_DATE,
				RateCommonConstants.BA_CUSTOMER_CODE };
		Object[] values = {
				input.getOriginCityCode(),
				input.getProductCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()), input.getCustomerCode() };
		List productValue = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						"isProductValidForBACustomerContract", paramNames,
						values);
		if (productValue != null && !productValue.isEmpty())
			isproductValid = true;
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForBACustomerContract ::END");
		return isproductValid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean isProductValidForEBCashContract(
			ProductToBeValidatedInputTO input) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForEBCashContract ::START");
		List productValue = null;
		Boolean isproductValid = false;
		String[] paramNames = { RateCommonConstants.RATE_PREFERENCES,
				RateCommonConstants.CITY_CODE, RateCommonConstants.CURRENT_DATE };
		Object[] values = {
				input.getEbPreference(),
				input.getOriginCityCode(),
				DateUtil.slashDelimitedstringToDDMMYYYYFormat(input
						.getCalculationRequestDate()) };

		productValue = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"isProductValidForEBCashContract", paramNames, values);
		if (productValue != null && !productValue.isEmpty())
			isproductValid = true;
		LOGGER.debug("RateCalculationDAOImpl::isProductValidForEBCashContract ::END");
		return isproductValid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getDestinationSector(Integer orgCityId, String destPincode)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getDestinationSector ::START");
		SectorDO sectorDO = null;
		Session session = null;
		try {
			//String sql = RateCommonConstants.SQL_QRY_GET_DEST_SECTOR;
			session = getHibernateTemplate().getSessionFactory().openSession();
			//Query query = session.createSQLQuery(sql).addEntity(SectorDO.class);
			Query query = session.getNamedQuery("getDestinationSectorByOriginAndDestPincode");
			query.setInteger(RateCommonConstants.ORIGIN_CITY, orgCityId);
			query.setString(RateCommonConstants.DEST_PINCODE, destPincode);
			List<SectorDO> sectors = query.list();
			if (sectors != null && !sectors.isEmpty())
				sectorDO = sectors.get(0);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: RateCalculationDAOImpl::getDestinationSector ::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("RateCalculationDAOImpl::getDestinationSector ::END");
		return sectorDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerCustomerRateTypeDO getCustomerCodeAndRateCustomerCategoryByCustomerId(
			Integer customer) throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getCustomerCodeAndRateCustomerCategoryByCustomerId ::START");
		CustomerCustomerRateTypeDO customerCustomerRateTypeDO = null;
		List<CustomerCustomerRateTypeDO> custDetails = null;
		String sql = RateCommonConstants.QRY_GET_CUSTOMER_CODE_CUTOMER_RATE_TYPE_BY_CUSTOMER_ID;
		String[] paramNames = { RateCommonConstants.CUSTOMER_ID };
		Object[] values = { customer };
		custDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(sql,
				paramNames, values);
		if (custDetails != null && !custDetails.isEmpty()) {
			customerCustomerRateTypeDO = custDetails.get(0);
		}
		LOGGER.debug("RateCalculationDAOImpl::getCustomerCodeAndRateCustomerCategoryByCustomerId ::END");
		return customerCustomerRateTypeDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingPreferenceDetailsDO> getBookingPrefDetails()
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getBookingPrefDetails::START------------>:::::::");
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		try {
			bookingPrefDOs = getHibernateTemplate().findByNamedQuery(
					"getAllPreferenceDetails");
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::RateCalculationDAOImpl::getBookingPrefDetails() :: "
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		LOGGER.debug("RateCalculationDAOImpl::getBookingPrefDetails::END------------>:::::::");
		return bookingPrefDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsignmentBilling> getConsignmentForRate(int limit, String rateType) throws CGSystemException {
		LOGGER.debug("BillingCommonDAOImpl :: getConsignmentForRate() :: Start --------> ::::::");
		List<ConsignmentBilling> consignmentList=new ArrayList<ConsignmentBilling>();
		Session session =null;
		try {
			session = createSession();
			Query query=session.createSQLQuery(RateCommonConstants.GET_CONSIG_DTLS).addEntity(ConsignmentBilling.class);
			query.setString(0, rateType);
			query.setMaxResults(limit);
			consignmentList = query.list();
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getConsignmentForRate()::::::"
 					+ e.getMessage());
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getConsignmentForRate(): END");
		return consignmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BookingDO getConsgBookingDetails(String consgNo)
			throws CGSystemException {
		BookingDO bookingDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			List<BookingDO> bookingDOs = (List<BookingDO>)cr.list();
			if(!StringUtil.isEmptyColletion(bookingDOs)){
				bookingDO = bookingDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getCustomerFromTypeBooking()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getCustomerFromTypeBooking(): END");
		return bookingDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductDO getProduct(Integer productId) throws CGSystemException {
		LOGGER.debug("BillingCommonDAOImpl :: getProduct() :: Start --------> ::::::");
		ProductDO productDO = null;
		Session session = null;
		Criteria cr = null;
		try {
			session = createSession();
			cr = session.createCriteria(ProductDO.class, "product");
			cr.add(Restrictions.eq("product.productId", productId));
			List<ProductDO> productDOs = (List<ProductDO>)cr.list();
			if(!StringUtil.isEmptyColletion(productDOs)){
				productDO = productDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("BillingCommonDAOImpl :: getProduct()::::::"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally{
			closeSession(session);
		}
		 LOGGER .debug("BillingCommonDAOImpl: getProduct(): END");
		return productDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RiskSurchargeInsuredByDO getInsuredByInsuredByCode(String insuredBy)
			throws CGSystemException {
		LOGGER.debug("RateCalculationDAOImpl::getInsuredByInsuredByCode ::START");
		List<RiskSurchargeInsuredByDO> riskSurchargeInsuredBys = null;
		RiskSurchargeInsuredByDO riskSurchargeInsuredBy = null;
		String[] paramNames = { RateCommonConstants.INSURED_BY };
		Object[] values = { insuredBy };

		riskSurchargeInsuredBys = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_INSURED_BY_CODE, paramNames,
						values);
		riskSurchargeInsuredBy = (riskSurchargeInsuredBys == null || riskSurchargeInsuredBys
				.isEmpty()) ? null : riskSurchargeInsuredBys.get(0);
		LOGGER.debug("RateCalculationDAOImpl::getInsuredByInsuredByCode ::START");
		return riskSurchargeInsuredBy;
	}
}
