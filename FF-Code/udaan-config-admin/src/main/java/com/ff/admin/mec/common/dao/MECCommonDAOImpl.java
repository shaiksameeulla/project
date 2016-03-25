package com.ff.admin.mec.common.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.collection.BulkCollectionValidationWrapperDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author hkansagr
 */

public class MECCommonDAOImpl extends CGBaseDAO implements MECCommonDAO {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MECCommonDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getStockStdType(String typeName)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl::getStockStdType()::START");
		List<StockStandardTypeDO> typesList = null;
		try {
			String params[] = { MECCommonConstants.PARAM_TYPE_NAME };
			Object[] values = new Object[] { typeName };
			typesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_STOCK_STD_TYPE_BY_TYPE_NAME,
					params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl::getStockStdType()::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl :: getStockStdType() :: END");
		return typesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GLMasterDO getGLDtlsById(Integer glId) throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl::getGLDtlsById()::START");
		List<GLMasterDO> glMasterDOs = null;
		try {
			String params[] = { MECCommonConstants.PARAM_GL_ID };
			Object[] values = new Object[] { glId };
			glMasterDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_GL_DETAILS_BY_GL_ID, params,
					values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl::getGLDtlsById()::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl::getGLDtlsById()::END");
		return !StringUtil.isEmptyColletion(glMasterDOs) ? glMasterDOs.get(0)
				: null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO getCustDOByCustName(String custCode)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl::getCustDOByCustName()::START");
		List<CustomerDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GETCUST_BYCUSTNAME,
					UmcConstants.CUST_CODE, custCode);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl::getCustDOByCustName()::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl::getCustDOByCustName()::END");
		if (StringUtil.isEmptyList(result))
			return null;
		else if (result.size() > 1)
			return null;
		else
			return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getLiabilityPaymentMode(
			String MECProcessCode) throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl::getLiabilityPaymentMode()::START");
		List<StockStandardTypeDO> typesList = null;
		try {
			String params[] = { MECCommonConstants.PARAM_TYPE_NAME,
					MECCommonConstants.PARAM_PARENT_TYPE };
			Object[] values = new Object[] {
					MECCommonConstants.STD_TYPE_PAYMENT_MODE, MECProcessCode };
			typesList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_LIABILITY_PAYMENT_MODE, params,
					values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl::getLiabilityPaymentMode()::",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl::getLiabilityPaymentMode()::END");
		return typesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isConsignmentBooked(String consgNo) throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl::isConsignmentBooked()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			Criteria cr = session.createCriteria(BookingDO.class, "booking");
			cr.add(Restrictions.eq("booking.consgNumber", consgNo));
			List<BookingDO> list = cr.list();
			if (list.size() > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl::isConsignmentBooked()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("MECCommonDAOImpl::isConsignmentBooked()::END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDO> getCollectionDtlsForRecalculation()
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: START");
		List<CollectionDO> collectionDOs = null;
		try {
			String queryName = MECCommonConstants.QRY_GET_COLLECTION_DTLS_FOR_RECALC;
			String[] params = { MECCommonConstants.PARAM_IS_RECACL_REQ };
			Object[] values = { CommonConstants.YES };
			collectionDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: ",	e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: END");
		return collectionDOs;
	}

	@Override
	public void updateCollectionRecalcFlag(List<Integer> collectionIds)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: updateCollectionRecalcFlag() :: START");
		Session session = null;
		Query query = null;
		try {
			session = createSession();
			query = session.getNamedQuery(MECCommonConstants.QRY_UPDATE_COLLECTION_RECALC_FLAG);
			query.setParameter(MECCommonConstants.PARAM_IS_RECACL_REQ, CommonConstants.NO);
			query.setParameter(MECCommonConstants.PARAM_CURR_DATE_TIME,	DateUtil.getCurrentDate());
			query.setParameterList(MECCommonConstants.PARAM_COllECTION_IDS,	collectionIds);
			int result = query.executeUpdate();
			if (result != collectionIds.size()) {
				throw new CGBusinessException("Batch update for the given collection Ids failed");
			}
		} catch (Exception e) {
			LOGGER.error("MECCommonDAOImpl :: updateCollectionRecalcFlag() :: ERROR", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("MECCommonDAOImpl :: updateCollectionRecalcFlag() :: END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDO> getChequeDepositSlipDtls(Integer reportingRHOId)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: getChequeDepositSlipDtls() :: START");
		List<CollectionDO> collectionDOs = null;
		try {
			String queryName = MECCommonConstants.QRY_GET_CHQ_DEPOSIT_SLIP_DTLS;
			String[] params = { MECCommonConstants.PARAM_PAYMENT_MODE,
					MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.PARAM_REPORTING_RHO_ID };
			Object[] values = { MECCommonConstants.CHQ,
					MECCommonConstants.STATUS_OPENED, reportingRHOId };
			collectionDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in MECCommonDAOImpl :: getChequeDepositSlipDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl :: getChequeDepositSlipDtls() :: END");
		return collectionDOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCollectionIdAndCollectionDateForPettyCash(Date currentDateObject)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: START");
		List<Object[]> collectionDetails = null;
		try {
			String queryName = MECCommonConstants.QRY_GET_COLLECTION_DETAILS_FOR_PETTY_CASH_CALCULATION;
			String[] params = { MECCommonConstants.PARAM_IS_RECACL_REQ, MECCommonConstants.PARAM_CURR_DATE_TIME };
			Object[] values = { CommonConstants.YES, currentDateObject };
			collectionDetails = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: ",	e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDtlsForRecalculation() :: END");
		return collectionDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.mec.common.dao.MECCommonDAO#getCollectionDetailsForBulkValidation(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BulkCollectionValidationWrapperDO> getCollectionDetailsForBulkValidation(
			Integer customerId, Date fromDate, Date toDate, Integer firstResult)
			throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDetailsForBulkValidation :: START");
		List<BulkCollectionValidationWrapperDO> bulkCollectionValidationWrapperDoList = null;
		Session session = null;
		try {
			session = createSession();
			SQLQuery sqlQuery = session.createSQLQuery(MECCommonConstants.QRY_GET_COLLECTION_DETAILS_FOR_BULK_VALIDATION);
			sqlQuery.addScalar("collectionDate");
			sqlQuery.addScalar("bookingDate");
			sqlQuery.addScalar("collectionId");
			sqlQuery.addScalar("transactionNo");
			sqlQuery.addScalar("consignmentId");
			sqlQuery.addScalar("consignmentNo");
			sqlQuery.addScalar("totalCollectionAmount");
			sqlQuery.addScalar("collectionStatus");
			sqlQuery.addScalar("collectionOfficeId");
			sqlQuery.addScalar("collectionCategory");
			sqlQuery.addScalar("paymentType");
			sqlQuery.addScalar("lcAmount");
			sqlQuery.addScalar("toPayAmount");
			sqlQuery.addScalar("codAmount");
			sqlQuery.setParameter(MECCommonConstants.PARAM_CUST_ID, customerId);
			sqlQuery.setParameter(MECCommonConstants.PARAM_FROM_DATE, fromDate);
			sqlQuery.setParameter(MECCommonConstants.PARAM_TO_DATE, toDate);
			sqlQuery.setParameter(MECCommonConstants.PARAM_STATUS, MECCommonConstants.SUBMITTED_STATUS);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(BulkCollectionValidationWrapperDO.class));
			sqlQuery.setMaxResults(MECCommonConstants.NO_OF_RECORDS_PER_PAGE);
			sqlQuery.setFirstResult(firstResult);
			bulkCollectionValidationWrapperDoList = sqlQuery.list();
		}
		catch (Exception e) {
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("MECCommonDAOImpl :: getCollectionDetailsForBulkValidation :: END");
		return bulkCollectionValidationWrapperDoList;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.mec.common.dao.MECCommonDAO#getTotalNumberOfRecordsForBulkValidation(java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotalNumberOfRecordsForBulkValidation(Integer customerId,
			Date fromDate, Date toDate) throws CGSystemException {
		LOGGER.trace("MECCommonDAOImpl :: getTotalNumberOfRecordsForBulkValidation :: START");
		Session session = null;
		List<BigInteger> countList = null;
		BigInteger numberOfRecords = null;
		try {
			session = createSession();
			SQLQuery sqlQuery = session.createSQLQuery(MECCommonConstants.QRY_GET_COUNT_OF_COLLECTION_DETAILS_FOR_BULK_VALIDATION);	
			sqlQuery.setParameter(MECCommonConstants.PARAM_CUST_ID, customerId);
			sqlQuery.setParameter(MECCommonConstants.PARAM_FROM_DATE, fromDate);
			sqlQuery.setParameter(MECCommonConstants.PARAM_TO_DATE, toDate);
			sqlQuery.setParameter(MECCommonConstants.PARAM_STATUS, MECCommonConstants.SUBMITTED_STATUS);
			countList = sqlQuery.list();
			if (!StringUtil.isEmptyColletion(countList)) {
				numberOfRecords = countList.get(0);
			}
		}
		catch (Exception e) {
			throw new CGSystemException(e);
		}
		finally {
			closeSession(session);
		}
		LOGGER.trace("MECCommonDAOImpl :: getTotalNumberOfRecordsForBulkValidation :: END");
		return numberOfRecords.intValue();
	}
}
