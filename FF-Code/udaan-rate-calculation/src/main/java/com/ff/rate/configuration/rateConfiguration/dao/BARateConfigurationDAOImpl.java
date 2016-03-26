/**
 * 
 */
package com.ff.rate.configuration.rateConfiguration.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.RateWeightSlabsDO;
import com.ff.domain.ratemanagement.masters.SectorDO;
import com.ff.domain.ratemanagement.operations.ba.BARateConfigAdditionalChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BARateConfigCODChargesDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigHeaderDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateWeightSlabDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigRTOChargesDO;
import com.ff.domain.ratemanagement.operations.ratecalculation.BARateConfigurationFixedChargesConfigDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;

/**
 * @author prmeher
 * 
 */
public class BARateConfigurationDAOImpl extends CGBaseDAO implements
		BARateConfigurationDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BARateConfigurationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::getStockStdType()::START");
		List<StockStandardTypeDO> typesList = null;
		String params[] = { RateCommonConstants.PARAM_TYPE_NAME };
		Object[] values = new Object[] { typeName };
		typesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_STOCK_STD_TYPE_BY_TYPE_NAME,
				params, values);
		LOGGER.trace("BARateConfigurationDAOImpl::getStockStdType()::END");
		return typesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateSectorsDO> getRateSectorListForBARateConfiguration(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGSystemException {
		LOGGER.info("BARateConfigurationDAOImpl :: getRateSectorListForBARateConfiguration() :: Start --------> ::::::");

		Session session = null;
		List<RateSectorsDO> rateSectorsDOList = null;

		try {
			String query = RateUniversalConstants.QRY_GET_BA_RATE_SECTORS;
			String params[] = { RateCommonConstants.PARAM_RATE_PROD_CAT_TYPE,
					RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE };
			Object values[] = { baRateProductCategory, rateCustomerCategory };
			rateSectorsDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(query, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BARateConfigurationDAOImpl.getRateSectorListForBARateConfiguration",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("BARateConfigurationDAOImpl :: getRateSectorListForBARateConfiguration() :: End --------> ::::::");
		return rateSectorsDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateWeightSlabsDO> getRateWeightSlabsList(
			String baRateProductCategory, String rateCustomerCategory) {
		LOGGER.info("BARateConfigurationDAOImpl :: getRateWeightSlabsList() :: Start --------> ::::::");
		Session session = null;
		List<RateWeightSlabsDO> rateWeightSlabsDOList = null;
		try {
			String query = RateUniversalConstants.QRY_GET_RATE_WEIGHT_SLAB_LIST_BY_EFFECTIVE_DATE_FOR_COURIER;
			String params[] = { RateUniversalConstants.PARAM_EFFECTIVE_FROM,
					RateUniversalConstants.PARAM_EFFECTIVE_TO,
					RateUniversalConstants.PARAM_RATE_CUST_CAT_CODE,
					RateUniversalConstants.PARAM_RATE_PROD_CAT_TYPE };
			Date date = DateUtil.stringToDDMMYYYYFormat(DateUtil.getCurrentDateInDDMMYYYY());
			Object values[] = { date, date, rateCustomerCategory, baRateProductCategory };
			rateWeightSlabsDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(query, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : BARateConfigurationDAOImpl.getRateWeightSlabsList",
					e);
		} finally {
			closeSession(session);
		}
		LOGGER.info("BARateConfigurationDAOImpl :: getRateWeightSlabsList() :: End --------> ::::::");
		return rateWeightSlabsDOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SectorDO getSectorByCityCode(String cityCode) {
		SectorDO sectorDO = null;
		String hql = RateCommonConstants.QRY_GET_SECTOR_BY_CITY_CODE;
		List<SectorDO> sector = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(hql,
						RateCommonConstants.CITY_CODE, cityCode);
		if (sector != null && !sector.isEmpty())
			sectorDO = sector.get(0);
		return sectorDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerTypeDO> getBATypeList(String customerCode) {
		LOGGER.debug("BARateConfigurationDAOImpl::getBATypeList()::START");
		List<CustomerTypeDO> baTypeList = null;
		String params[] = { RateCommonConstants.CUSTOMER_CODE };
		Object[] values = new Object[] { customerCode };
		baTypeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_BA_CUSTOMER_TYPE_BY_CUSTOMER_TYPE,
				params, values);
		LOGGER.debug("BARateConfigurationDAOImpl::getBATypeList()::END");
		return baTypeList;
	}

	@Override
	public BaRateConfigHeaderDO saveOrUpdateBARateConfiguration(
			BaRateConfigHeaderDO baRateHeaderDO) throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl :: saveOrUpdateBARateConfiguration() :: Start --------> ::::::");
		try {
			baRateHeaderDO = (BaRateConfigHeaderDO) getHibernateTemplate()
					.merge(baRateHeaderDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in BARateConfigurationDAOImpl :: saveOrUpdateBARateConfiguration() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("BARateConfigurationDAOImpl :: saveOrUpdateBARateConfiguration() :: End --------> ::::::");
		return baRateHeaderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaRateConfigHeaderDO getBARateConfigurationDetails(Date fromDate,
			Date toDate, Integer cityId, Integer baTypeId,
			Integer courierProdductId) throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl::getBARateConfigurationDetails()::START");
		List<BaRateConfigHeaderDO> baRateHeaderDOList = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;
		Session session = null;
		try {
			session = createSession();
			/*
			 * String currentDate = DateUtil.getCurrentDateInDDMMYYYY(); String
			 * params[] = { "toDate", "cityId", "baTypeId" };
			 */
			Query qry = session
					.getNamedQuery(RateCommonConstants.QRY_GET_BA_RATE_DETAILS);
			qry.setInteger("cityId", cityId);
			qry.setInteger("baTypeId", baTypeId);

			qry.setMaxResults(2);
			baRateHeaderDOList = qry.list();
			if (!CGCollectionUtils.isEmpty(baRateHeaderDOList)) {
				if (baRateHeaderDOList.size() == 1) {
					baRateHeaderDO = baRateHeaderDOList.get(0);
				} else if (!StringUtil.isNull(baRateHeaderDOList.get(1)
						.getToDate())
						&& (baRateHeaderDOList.get(1).getToDate()
								.compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil
										.getCurrentDateInYYYYMMDDHHMM()))) < 0
						&& baRateHeaderDOList.get(0).getHeaderStatus()
								.equals(RateCommonConstants.STATUS_ACTIVE)) {
					baRateHeaderDO = baRateHeaderDOList.get(0);
				} else {
					baRateHeaderDO = baRateHeaderDOList.get(1);
				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"Error :: BARateConfigurationDAOImpl::getBARateConfigurationDetails()::",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("BARateConfigurationDAOImpl::getBARateConfigurationDetails()::END");
		return baRateHeaderDO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.rate.configuration.rateConfiguration.dao.BARateConfigurationDAO
	 * #getFixedChargesByProductHeader(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaRateConfigHeaderDO getFixedChargesByHeader(
			Integer productHeaderId, String priorityIndicator) {
		LOGGER.trace("BARateConfigurationDAOImpl::getFixedChargesByProductHeader()::START");
		List<BARateConfigAdditionalChargesDO> baRateConfigAdditionalCharges = null;
		List<BARateConfigurationFixedChargesConfigDO> fixedConfig = null;
		List<BARateConfigCODChargesDO> codCharges = null;
		BaRateConfigHeaderDO headerDO = new BaRateConfigHeaderDO();
		String params[] = { RateCommonConstants.BA_RATE_HEADER_DO,
				RateCommonConstants.PRIORITY_INDICATOR };
		Object[] values = new Object[] { productHeaderId, priorityIndicator };
		baRateConfigAdditionalCharges = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_BA_ADDITIONAL_CHARGES,
						params, values);
		fixedConfig = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_BA_FIXED_CHARGES_CONFIG, params,
				values);
		codCharges = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_BA_COD_CHARGES_CONFIG_DETAILS,
				params, values);
		headerDO.setBaRateConfigAdditionalCharges(baRateConfigAdditionalCharges);
		headerDO.setFixedConfig(fixedConfig);
		headerDO.setCodCharges(codCharges);
		LOGGER.trace("BARateConfigurationDAOImpl::getFixedChargesByProductHeader()::END");
		return headerDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuredByDO> getInsuredByDetails() {
		LOGGER.debug("BARateConfigurationDAOImpl::getInsuredByDetails()::START");
		List<InsuredByDO> insuredBy = null;
		insuredBy = getHibernateTemplate().findByNamedQuery("getInsuredBy");
		LOGGER.debug("BARateConfigurationDAOImpl::getInsuredByDetails()::END");
		return insuredBy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BARateConfigRTOChargesDO getRTOChargesByHeader(Integer headerId,
			String priorityIndicator) throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::getRTOChargesByHeader()::START");
		List<BARateConfigRTOChargesDO> rtoChargesList = null;
		BARateConfigRTOChargesDO rtoCharge = new BARateConfigRTOChargesDO();
		String params[] = { RateCommonConstants.BA_RATE_HEADER_DO,
				RateCommonConstants.PRIORITY_INDICATOR };
		Object[] values = new Object[] { headerId, priorityIndicator };
		rtoChargesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				RateCommonConstants.QRY_GET_BA_RTO_CHARGES_BY_HEADER_ID,
				params, values);
		rtoCharge = !StringUtil.isEmptyList(rtoChargesList) ? rtoChargesList
				.get(0) : null;
		LOGGER.trace("BARateConfigurationDAOImpl::getRTOChargesByHeader()::END");
		return rtoCharge;
	}

	@Override
	public boolean submitBaRateConfiguration(Integer headerId, String fromDate,
			String toDate,  Integer updatedBy, Date updateDate) throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::submitBaRateConfiguration()::START");
		Session session = null;
		Boolean isSubmitted = false;
		// Transaction tx = null;
		try {
			session = createSession();
			// tx = session.beginTransaction();
			Query query = session.getNamedQuery(RateCommonConstants.QRY_SUBMIT_BA_RATE_CONFIGURATION);
			query.setParameter(RateCommonConstants.BA_HEADER, headerId);
			query.setParameter(RateCommonConstants.PARAM_FROM_DATE, DateUtil.stringToDDMMYYYYFormat(fromDate));
			query.setParameter(RateCommonConstants.PARAM_TO_DATE, DateUtil.stringToDDMMYYYYFormat(toDate));
			query.setParameter(RateCommonConstants.PARAM_UPDATED_BY, updatedBy);
			query.setParameter(RateCommonConstants.PARAM_UPDATE_DATE, updateDate);
			query.executeUpdate();
			isSubmitted = true;

		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: BARateConfigurationDAOImpl :: submitBaRateConfiguration()..:",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("BARateConfigurationDAOImpl::submitBaRateConfiguration()::END");
		return isSubmitted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean isExistsBaRateConfiguration(Integer cityId, Integer baTypeId) {
		LOGGER.debug("BARateConfigurationDAOImpl::isExistsBaRateConfiguration()::START");
		List<BaRateConfigHeaderDO> baRateHeaderDOList = null;
		Boolean isExists = false;
		String params[] = { "cityId", "baTypeId" };
		Object[] values = new Object[] { cityId, baTypeId };
		baRateHeaderDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_BA_RATE_CONFIGURATION_DETAILS_BY_CITY_ID,
						params, values);
		isExists = !StringUtil.isEmptyList(baRateHeaderDOList) ? true : false;
		LOGGER.debug("BARateConfigurationDAOImpl::isExistsBaRateConfiguration()::END");
		return isExists;
	}

	@Override
	public boolean updateBAConfgRenewStatus(Integer headerId)
			throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgRenewStatus()::START");
		Session session = null;
		Boolean isSubmitted = false;
		session = openTransactionalSession();
		Query query = session
				.getNamedQuery(RateCommonConstants.QRY_UPDATE_BA_RATE_CONFIGURATION_RENEW_STATUS);
		query.setParameter(RateCommonConstants.BA_HEADER, headerId);
		query.executeUpdate();
		isSubmitted = true;
		closeTransactionalSession(session);
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgRenewStatus()::END");
		return isSubmitted;
	}

	/**
	 * Update TO date of old Configuration if new contract TO date less than old
	 * contract TODATE
	 */
	@Override
	public boolean updateBAConfgTODate(Date toDate, Integer headerId)
			throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgTODate()::START");
		Session session = null;
		Boolean isSubmitted = false;
		try {
			session = createSession();

			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_UPDATE_BA_RATE_CONFIGURATION_TODATE);
			query.setParameter(RateCommonConstants.BA_HEADER, headerId);
			query.setParameter(RateCommonConstants.TO_DATE, toDate);
			query.executeUpdate();
			isSubmitted = true;
		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgTODate()::END");
		return isSubmitted;
	}

	@Override
	public boolean updateBAConfgFromDate(Date toDate, Integer headerId)
			throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgFromDate()::START");
		Session session = null;
		Boolean isSubmitted = false;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(RateCommonConstants.QRY_UPDATE_BA_RATE_CONFIGURATION_FORMDATE);
			query.setParameter(RateCommonConstants.BA_HEADER, headerId);
			query.setParameter(RateCommonConstants.TO_DATE, toDate);
			query.executeUpdate();
			isSubmitted = true;

		} catch (Exception e) {
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.trace("BARateConfigurationDAOImpl::updateBAConfgFromDate()::END");
		return isSubmitted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.rate.configuration.rateConfiguration.dao.BARateConfigurationDAO
	 * #getRenewedBARateConfigurationDetails(java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaRateConfigHeaderDO getRenewedBARateConfigurationDetails(
			Integer cityId, Integer baTypeId, Integer courierProdductId,
			Date fromDate) throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl::getRenewedBARateConfigurationDetails()::START");
		List<BaRateConfigHeaderDO> baRateHeaderDOList = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;

		/*
		 * Calendar c = Calendar.getInstance(); c.setTime(toDate);
		 * c.add(Calendar.DATE, 1); // number of days to add SimpleDateFormat
		 * sdf = new SimpleDateFormat(
		 * FrameworkConstants.DDMMYYYY_SLASH_FORMAT); String dateStr =
		 * sdf.format(c.getTime()); toDate = DateUtil.getDateFromString(dateStr,
		 * FrameworkConstants.DDMMYYYY_SLASH_FORMAT);
		 */

		String params[] = { "fromDate", "cityId", "baTypeId" };
		Object[] values = new Object[] { fromDate, cityId, baTypeId };
		baRateHeaderDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_BA_RATE_CONFIGURATION_RENEWED_DETAILS,
						params, values);
		baRateHeaderDO = !StringUtil.isEmptyList(baRateHeaderDOList) ? baRateHeaderDOList
				.get(0) : null;
		LOGGER.debug("BARateConfigurationDAOImpl::getRenewedBARateConfigurationDetails()::END");
		return baRateHeaderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaRateConfigHeaderDO getBARateConfigurationDetailsByHeaderId(
			Integer headerId) throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl::getBARateConfigurationDetailsByHeaderId()::START");
		List<BaRateConfigHeaderDO> baRateHeaderDOList = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;
		String params[] = { "headerId" };
		Object[] values = new Object[] { headerId };
		baRateHeaderDOList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_BA_RATE_CONFIGURATION_DETAILS_BY_HEADER_ID,
						params, values);
		baRateHeaderDO = !StringUtil.isEmptyList(baRateHeaderDOList) ? baRateHeaderDOList
				.get(0) : null;
		LOGGER.debug("BARateConfigurationDAOImpl::getBARateConfigurationDetailsByHeaderId()::END");
		return baRateHeaderDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getFromDateByBARateHeaderId(Integer headerId)
			throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl::getFromDateByBARateHeaderId()::START");
		String[] params = { RateCommonConstants.PARAM_BA_RATE_HEADER_ID, };
		Object[] values = { headerId };
		List<Date> fromDates = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						RateCommonConstants.QRY_GET_FROM_DATE_BY_BA_RATE_HEADER_ID,
						params, values);
		Date fromDate = null;
		if (!StringUtil.isEmptyColletion(fromDates)) {
			fromDate = fromDates.get(0);
		}
		LOGGER.debug("BARateConfigurationDAOImpl::getFromDateByBARateHeaderId()::END");
		return fromDate;
	}

	@Override
	public void saveOrUpdateBARateWtSlab(BaRateWeightSlabDO baRateWeightSlabDO)
			throws CGSystemException {
		LOGGER.trace("BARateConfigurationDAOImpl :: saveOrUpdateBARateWtSlab() :: START ::");
		try {
			getHibernateTemplate().saveOrUpdate(baRateWeightSlabDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in BARateConfigurationDAOImpl :: saveOrUpdateBARateWtSlab() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BARateConfigurationDAOImpl :: saveOrUpdateBARateWtSlab() :: END ::");
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaRateWeightSlabDO searchBARateWtSlabByBaWtSlabId(Integer baWtSlabId)
			throws CGSystemException {
		Session session = null;
		Criteria cr = null;
		BaRateWeightSlabDO domain = null;
		try {
			session = createSession();
			cr = session.createCriteria(BaRateWeightSlabDO.class,
					"baWeightSlab");
			cr.add(Restrictions.eq("baWeightSlab.baWeightSlabId", baWtSlabId));
			List<BaRateWeightSlabDO> baRateWeightSlabDOs = cr.list();
			if (!CGCollectionUtils.isEmpty(baRateWeightSlabDOs)) {
				domain = baRateWeightSlabDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in BARateConfigurationDAOImpl :: saveOrUpdateBARateWtSlab() :: ",
					e);
			throw new CGSystemException(e);
		}finally {
			session.close();
		}
		return domain;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaRateConfigHeaderDO _getBARatesDtls(Integer cityId, Integer baTypeId)
			throws CGSystemException {
		LOGGER.debug("BARateConfigurationDAOImpl :: _getBARatesDtls() :: START");
		List<BaRateConfigHeaderDO> baRateHeaderDOList = null;
		BaRateConfigHeaderDO baRateHeaderDO = null;
		Session session = null;
		try {
			session = createSession();
			Query qry = session
					.getNamedQuery(RateCommonConstants.QRY_GET_BA_RATE_DETAILS);
			qry.setInteger("cityId", cityId);
			qry.setInteger("baTypeId", baTypeId);
			qry.setMaxResults(2);
			baRateHeaderDOList = qry.list();
			if (!CGCollectionUtils.isEmpty(baRateHeaderDOList)) {
				if (baRateHeaderDOList.size() == 1) {
					baRateHeaderDO = baRateHeaderDOList.get(0);
				} else if (!StringUtil.isNull(baRateHeaderDOList.get(1)
						.getToDate())
						&& (baRateHeaderDOList.get(1).getToDate()
								.compareTo(DateUtil.stringToDDMMYYYYFormat(DateUtil
										.getCurrentDateInYYYYMMDDHHMM()))) < 0
						&& baRateHeaderDOList.get(0).getHeaderStatus()
								.equals(RateCommonConstants.STATUS_ACTIVE)) {
					baRateHeaderDO = baRateHeaderDOList.get(0);
				} else {
					baRateHeaderDO = baRateHeaderDOList.get(1);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"Error :: BARateConfigurationDAOImpl :: _getBARatesDtls() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("BARateConfigurationDAOImpl :: _getBARatesDtls() :: END");
		return baRateHeaderDO;
	}

}
