package com.ff.admin.mec.collection.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;

public class BillCollectionDAOImpl extends CGBaseDAO implements BillCollectionDAO{
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillCollectionDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.ff.admin.mec.collection.dao.BillCollectionDAO#searchBillCollectionDtls(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CollectionDO searchBillCollectionDtls(String transactionNo, String collectionType)
			throws CGSystemException {
		LOGGER.trace("BillCollectionDAOImpl :: searchBillCollectionDtls() :: Start --------> ::::::");
		CollectionDO collectionDO = null;
		List<CollectionDO> collectionDOs = null;
		try {
			String[] paramNames = { MECCommonConstants.TRANSACTION_NUMBER,
					MECCommonConstants.COLLECTION_TYPE};
			Object[] values = { transactionNo, collectionType};
			collectionDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					MECCommonConstants.QRY_GET_BILL_COLLECTION_DTLS,
					paramNames, values);
			collectionDO = !StringUtil.isEmptyList(collectionDOs) ? collectionDOs
					.get(0) : null;

		} catch (Exception e) {
			LOGGER.error("BillCollectionDAOImpl :: searchBillCollectionDtls()..:",e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("BillCollectionDAOImpl :: searchBillCollectionDtls() :: END --------> ::::::");
		return collectionDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDtlsDO> getCollectionDetailsByBillNumber(
			List<String> billNo) throws CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillCollectionDAOImpl::getCollectionDetailsByBillNumber()::START");
		List<CollectionDtlsDO> collectionDtlsDOs = null;
		Session session = null;
		try {
			session = createSession();
			Criteria cr = session.createCriteria(CollectionDtlsDO.class,
					"collectionDetails");
			cr.add(Restrictions.in("collectionDetails.billNo", billNo));
			collectionDtlsDOs = cr.list();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BillCollectionDAOImpl::getCollectionDetailsByBillNumber()::",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("BillCollectionDAOImpl::getCollectionDetailsByBillNumber()::END");
		return collectionDtlsDOs;
	}
}
