/**
 * 
 */
package com.ff.admin.mec.collection.dao;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.mec.collection.CNCollectionTO;

/**
 * @author prmeher
 * 
 */
public class CollectionCommonDAOImpl extends CGBaseDAO implements
		CollectionCommonDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CollectionCommonDAOImpl.class);

	@Override
	public boolean saveOrUpdateCollection(CollectionDO collectionDO)
			throws CGSystemException {
		boolean isSaved = Boolean.FALSE;
		LOGGER.debug("CollectionCommonDAOImpl :: saveOrUpdateCollection() :: Start --------> ::::::");
		try {
			getHibernateTemplate().saveOrUpdate(collectionDO);
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CollectionCommonDAOImpl::saveOrUpdateCollection() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: saveOrUpdateCollection() ::  End --------> ::::::");
		return isSaved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCollectionStatus(String transactionNo)
			throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl :: getCollectionStatus() :: Start --------> ::::::");
		String collectionStatus = null;
		List<String> status = null;
		try {
			String[] paramNames = { MECCommonConstants.TRANSACTION_NUMBER };
			Object[] values = { transactionNo };
			status = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_COLLECTION_STATUS, paramNames,
					values);
			collectionStatus = !StringUtil.isEmptyList(status) ? status.get(0)
					: null;

		} catch (Exception e) {
			LOGGER.error("CollectionCommonDAOImpl :: getCollectionStatus()..:",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: getCollectionStatus() :: END --------> ::::::");
		return collectionStatus;
	}

	/**
	 * Get all Delivered Consg Details by Date
	 * 
	 * @param cnCollectionTO
	 * @return collectionDtlsDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDtlsDO> getAllDeliverdConsgDtlsByDate(
			CNCollectionTO cnCollectionTO) throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliverdConsgDtlsByDate() :: Start --------> ::::::");
		List<CollectionDtlsDO> collectionDtlsDOs = null;
		try {
			String[] params = { MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.NEXT_DATE,
					MECCommonConstants.CONSG_ORIGIN_OFF_ID };
			Object[] values = { cnCollectionTO.getCurrentDt(),
					cnCollectionTO.getNextDt(),
					cnCollectionTO.getOriginOfficeId() };
			collectionDtlsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_ALL_DELIVERED_CN_DTLS_BY_DATE,
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in CollectionCommonDAOImpl :: getAllDeliverdConsgDtlsByDate()..:",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliverdConsgDtlsByDate() :: END --------> ::::::");
		return collectionDtlsDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CollectionDO searchCollectionDtlsByTxnNo(String transactionNo)
			throws CGSystemException {
		LOGGER.trace("CollectionCommonDAOImpl :: searchCollectionDtlsByTxnNo() :: START --------> ::::::");
		CollectionDO collectionDO = null;
		List<CollectionDO> collectionDOs = null;
		try {
			String[] paramNames = { MECCommonConstants.TRANSACTION_NUMBER };
			Object[] values = { transactionNo };
			collectionDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_COLLECTION_DETAILS_BXN_NO,
							paramNames, values);
			collectionDO = !StringUtil.isEmptyList(collectionDOs) ? collectionDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error(
					"CollectionCommonDAOImpl :: searchCollectionDtlsByTxnNo()..:",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("CollectionCommonDAOImpl :: searchCollectionDtlsByTxnNo() :: END --------> ::::::");
		return collectionDO;
	}

	@Override
	public CollectionDO saveOrUpdateCNCollection(CollectionDO collectionDO)
			throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl :: saveOrUpdateCNCollection() :: Start --------> ::::::");
		try {
			getHibernateTemplate().merge(collectionDO);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CollectionCommonDAOImpl::saveOrUpdateCNCollection() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: saveOrUpdateCNCollection() ::  End --------> ::::::");
		return collectionDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @author hkansagr
	 */
	public List<DeliveryDetailsDO> getAllDeliveredConsgForCollectionDtls(
			CNCollectionTO cnCollectionTO) throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliveredConsgForCollectionDtls() ::  START --------> ::::::");
		List<DeliveryDetailsDO> deliveryDtlDOs = null;
		try {
			String[] params = { MECCommonConstants.CURRENT_DATE,
					MECCommonConstants.NEXT_DATE, MECCommonConstants.OFFICE_ID,
					MECCommonConstants.DELIVERY_STATUS };
			Object[] values = { cnCollectionTO.getCurrentDt(),
					cnCollectionTO.getNextDt(),
					cnCollectionTO.getOriginOfficeId(),
					MECCommonConstants.DELIVERY_STATUS_DELIVERED };
			deliveryDtlDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_ALL_DELIVERED_CN_DTLS_FOR_COLLECTION_DTLS,
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CollectionCommonDAOImpl::getAllDeliveredConsgForCollectionDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliveredConsgForCollectionDtls() ::  END --------> ::::::");
		return deliveryDtlDOs;
	}

	/**
	 * @author hkansagr
	 */
	@Override
	public boolean saveAllDeliveredCNToCollectionDtls(
			List<CollectionDtlsDO> collectionDtlsDOs) throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl::saveAllDeliveredCNToCollectionDtls()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			for (CollectionDtlsDO domain : collectionDtlsDOs) {
				session.setFlushMode(FlushMode.AUTO);
				session.saveOrUpdate(domain);
			}
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in CollectionCommonDAOImpl::saveAllDeliveredCNToCollectionDtls()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CollectionCommonDAOImpl::saveAllDeliveredCNToCollectionDtls()::END");
		return result;
	}

	/**
	 * @author hkansagr
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryDetailsDO> getAllDeliveredDtlsForExpenseDtls(
			CNCollectionTO cnCollectionTO) throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliveredDtlsForExpenseDtls() ::  START --------> ::::::");
		List<DeliveryDetailsDO> deliveryDtlDOs = null;
		try {
			String[] params = { MECCommonConstants.OFFICE_ID,
					MECCommonConstants.DELIVERY_STATUS };
			Object[] values = { cnCollectionTO.getOriginOfficeId(),
					MECCommonConstants.DELIVERY_STATUS_DELIVERED };
			deliveryDtlDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_ALL_DELIVERED_CN_DTLS_FOR_EXPENSE_DTLS,
							params, values);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::CollectionCommonDAOImpl::getAllDeliveredDtlsForExpenseDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("CollectionCommonDAOImpl :: getAllDeliveredDtlsForExpenseDtls() ::  END --------> ::::::");
		return deliveryDtlDOs;
	}

	/**
	 * @author hkansagr
	 */
	@Override
	public boolean updateCnDeliveryDate(CollectionDtlsDO collectionDtlsDO)
			throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl::updateCnDeliveryDate()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		Query query = null;
		try {
			session = createSession();
			query = session
					.getNamedQuery(MECCommonConstants.QRY_UPDATE_CN_DELIVERY_DATE);
			query.setParameter(MECCommonConstants.PARAM_CONSG_ID,
					collectionDtlsDO.getConsgDO().getConsgId());
			query.setParameter(MECCommonConstants.PARAM_DELIVERY_DATE,
					collectionDtlsDO.getConsgDeliveryDate());
			int i = query.executeUpdate();
			if (i > 0)
				result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.debug(
					"Exception occurs in CollectionCommonDAOImpl::updateCnDeliveryDate()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CollectionCommonDAOImpl::updateCnDeliveryDate()::END");
		return result;
	}

	@Override
	public boolean updateCollectionStatus(String status, Integer collectionId)
			throws CGSystemException {
		LOGGER.debug("CollectionCommonDAOImpl::updateCollectionStatus()::START");
		boolean result = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(MECCommonConstants.QRY_UPDATE_COLLECTION_STATUS);
			query.setParameter("status", status);
			query.setParameter("sapStatus", CommonConstants.YES);
			query.setParameter("collectionId", collectionId);
			int i = query.executeUpdate();
			if (i > 0) {
				result = Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.debug(
					"Exception occurs in CollectionCommonDAOImpl::updateCollectionStatus()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("CollectionCommonDAOImpl::updateCollectionStatus()::END");
		return result;
	}

}
