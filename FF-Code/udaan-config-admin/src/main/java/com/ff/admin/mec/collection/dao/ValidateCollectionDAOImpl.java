/**
 * 
 */
package com.ff.admin.mec.collection.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.collection.CollectionDO;

/**
 * @author prmeher
 * 
 */
public class ValidateCollectionDAOImpl extends CGBaseDAO implements
		ValidateCollectionDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateCollectionDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionDO> searchCollectionDetlsForValidation(Date fromDate,
			Date to_Date, Integer officeId, String headerStatus,
			String headerTransNo) throws CGSystemException {
		LOGGER.trace("ValidateCollectionDAOImpl :: searchCollectionDetlsForValidation() :: Start --------> ::::::");
		List<CollectionDO> collectionDOs = null;
		try {
			String[] paramNames = { MECCommonConstants.FROM_DATE,
					MECCommonConstants.TO_DATE, MECCommonConstants.OFFICE_ID,
					MECCommonConstants.HEADER_STATUS,
					MECCommonConstants.HEADER_TRANS_NO };
			Object[] values = { fromDate, to_Date, officeId, headerStatus,
					headerTransNo };
			collectionDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							MECCommonConstants.QRY_GET_COLLECTION_DTLS,
							paramNames, values);

		} catch (Exception e) {
			LOGGER.error(
					"ValidateCollectionDAOImpl :: searchCollectionDetlsForValidation()..:",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("ValidateCollectionDAOImpl :: searchCollectionDetlsForValidation() :: End --------> ::::::");
		return collectionDOs;

	}

	@Override
	public void validateTxns(List<String> txnsList, Integer updatedBy)
			throws CGSystemException {
		LOGGER.trace("ValidateCollectionDAOImpl :: validateTxns() :: START");
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(MECCommonConstants.QRY_VALIDATE_TXNS);

			// Values to be update.
			query.setParameter(MECCommonConstants.PARAM_STATUS,
					MECCommonConstants.STATUS_VALIDATED);
			query.setParameter(MECCommonConstants.PARAM_UPDATED_BY, updatedBy);
			query.setParameter(MECCommonConstants.PARAM_CURR_DATE_TIME,
					DateUtil.getCurrentDate());

			// Where Txn(s) are,
			query.setParameterList(MECCommonConstants.PARAM_TXNS, txnsList);

			int i = query.executeUpdate();
			int txnsListLen = txnsList.size();
			if (i != txnsListLen) {
				throw new Exception(MECCommonConstants.TXNS_NOT_VALIDATED);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionDAOImpl :: validateTxns() :: ",
					e);
			throw new CGSystemException(MECCommonConstants.TXNS_NOT_VALIDATED,
					e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("ValidateCollectionDAOImpl :: validateTxns() :: END");
	}

}
