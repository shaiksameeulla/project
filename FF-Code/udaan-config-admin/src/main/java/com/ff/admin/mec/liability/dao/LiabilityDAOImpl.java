package com.ff.admin.mec.liability.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.collection.dao.BillCollectionDAOImpl;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.LiabilityCollectionWrapperDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;

/**
 * @author amimehta
 */

public class LiabilityDAOImpl extends CGBaseDAO implements LiabilityDAO {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillCollectionDAOImpl.class);

	@Override
	public LiabilityDO saveOrUpdateLiabilityDtlsForNext(LiabilityDO domain)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityDAOImpl::saveOrUpdateLiabilityDtlsForNext::START");
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(domain);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error(
					"Error occured in LiabilityDAOImpl :: saveOrUpdateLiabilityDtlsForNext()..:",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("LiabilityDAOImpl::saveOrUpdateLiabilityDtlsForNext::END");
		return domain;
	}

	@Override
	public boolean saveOrUpdateLiabilityDtls(LiabilityDO domain)
			throws CGBusinessException, CGSystemException {
		boolean isSaved = Boolean.FALSE;
		LOGGER.debug("LiabilityDAOImpl :: saveOrUpdateLiabilityDtls() :: Start --------> ::::::");
		try {
			getHibernateTemplate().merge(domain);
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::LiabilityDAOImpl::saveOrUpdateLiabilityDtls() :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LiabilityDAOImpl :: saveOrUpdateLiabilityDtls() ::  End --------> ::::::");
		return isSaved;

	}
	@Override
	public boolean saveLiability(LiabilityDO domain)
			throws CGBusinessException, CGSystemException {
		boolean isSaved = Boolean.FALSE;
		LOGGER.debug("LiabilityDAOImpl :: saveOrUpdateLiabilityDtls() :: Start --------> ::::::");
		Session session=openTransactionalSession();
		try {
			session.save(domain);			/*for(LiabilityDetailsDO childDO: domain.getLiabilityDetailsList()){
				if(childDO.isPartialPayment()){
					//TODO update flag in collection entries table by entryid
				}
			}*/
			isSaved = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::LiabilityDAOImpl::saveOrUpdateLiabilityDtls() :: ",
					e);
			throw new CGSystemException(e);
		}finally{
			closeTransactionalSession(session);		}
		LOGGER.debug("LiabilityDAOImpl :: saveOrUpdateLiabilityDtls() ::  End --------> ::::::");
		return isSaved;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDtlsDO> getLiabilityDetails(Integer regionId,
			Integer custId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityDAOImpl::getLiabilityDetails::START");
		List<CollectionDtlsDO> collectionDtlsDOs = null;
		try {
			String[] paramNames = { MECCommonConstants.PARAM_CUST_ID };
			Object[] values = { custId };
			collectionDtlsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_LIABILITY_DETAILS,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("LiabilityDAOImpl::getLiabilityDetails::Error", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LiabilityDAOImpl::getLiabilityDetails::END");
		return collectionDtlsDOs;
	}
	@Override
	public List<LiabilityCollectionWrapperDO> getLiabilityDetailsFromCollection(Integer regionId,
			Integer custId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityDAOImpl::getLiabilityDetails::START");
		List<LiabilityCollectionWrapperDO> collectionDtlsDOs = null;
		Session session=null;
		try {
			session=createSession();
			Query query=session.getNamedQuery("getLiabilityEntriesFromCollection");
			query.setInteger(MECCommonConstants.PARAM_CUST_ID,custId);
			query.setMaxResults(5000);//setting max results to 5000 per this session only
			collectionDtlsDOs=query.list();
		} catch (Exception e) {
			LOGGER.error("LiabilityDAOImpl::getLiabilityDetails::Error", e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		LOGGER.trace("LiabilityDAOImpl::getLiabilityDetails::END");
		return collectionDtlsDOs;
	}
	
	@Override
	public Double getTotalPaidLiabilityByConsg(Integer consgId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityDAOImpl::getTotalPaidLiabilityByConsg::START");
		List<Double> collectionDtlsDOs = null;
		Double paidAmount=null;
		try {
			String[] paramNames = { MECCommonConstants.PARAM_CONSG_ID };
			Object[] values = { consgId };
			collectionDtlsDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.GET_TOTAL_PAID_LIABILITY_BY_CONSG,
							paramNames, values);
		} catch (Exception e) {
			LOGGER.error("LiabilityDAOImpl::getTotalPaidLiabilityByConsg::Error", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LiabilityDAOImpl::getTotalPaidLiabilityByConsg::END");
		if(!CGCollectionUtils.isEmpty(collectionDtlsDOs)){
			paidAmount =collectionDtlsDOs.get(0);
		}
		return paidAmount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LiabilityDO searchLiabilityDetails(String txnNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityDAOImpl::searchLiabilityDetails::START");
		List<LiabilityDO> liabilityDos = null;
		try {
			String[] paramNames = { MECCommonConstants.PARAM_TXN_NO };
			Object[] values = { txnNo };
			liabilityDos = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_SEARCH_LIABILITY_TXN_NO,
							paramNames, values);

		} catch (Exception e) {
			LOGGER.error(
					"CollectionCommonDAOImpl :: searchLiabilityDetails()..:", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LiabilityDAOImpl::searchLiabilityDetails::END");
		return !StringUtil.isEmptyList(liabilityDos) ? liabilityDos.get(0)
				: null;

	}
	@Async
	@Override
	public void processPaidLiabilityConsingments(List<Integer> consgList)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LiabilityPaymentServiceImpl::searchLiabilityDetails::START");
		Session session=null;
		Transaction tx=null;
		try {
			session=createSession();
			tx=session.beginTransaction();
			Query query=session.createSQLQuery("update ff_f_collection_entries set BALANCE_AMOUNT=0 where CONSIGNMENT_ID IN (:consgId)");
			query.setParameterList(MECCommonConstants.PARAM_CONSG_ID, consgList);
			int count=query.executeUpdate();
			LOGGER.info("LiabilityPaymentServiceImpl::searchLiabilityDetails::Update Count:"+count);
			tx.commit();
		}catch (Exception e) {
			tx.rollback();
			LOGGER.error(
					"CollectionCommonDAOImpl :: searchLiabilityDetails()..:", e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
	}

}
