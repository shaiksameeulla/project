package com.ff.admin.stockmanagement.stockcancel.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;

/**
 * The Class StockCancellationDAOImpl.
 */
public class StockCancellationDAOImpl extends CGBaseDAO implements StockCancellationDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockCancellationDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockcancel.dao.StockCancellationDAO#saveCancellation(com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO)
	 */
	@Override
	public Boolean saveCancellation(StockCancellationDO cancelDO) throws CGSystemException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(cancelDO);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockCancellationDAOImpl ::saveCancellation ::exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockcancel.dao.StockCancellationDAO#findDetailsByCancellationNumber(com.ff.to.stockmanagement.stockcancel.StockCancellationTO)
	 */
	@Override
	public StockCancellationDO findDetailsByCancellationNumber(StockCancellationTO to) throws CGSystemException {
		// TODO Auto-generated method stub
		List<StockCancellationDO> returnDtls = null;
		
		String params[] = {StockCommonConstants.QRY_PARAM_CANCELLATION_NUMBER,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {to.getCancellationNo(),to.getLoggedInOfficeId()};
		returnDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_CANCEL_DTLS_BY_CANCELLATION_NUMBER, params, values);
		
		return !CGCollectionUtils.isEmpty(returnDtls)?returnDtls.get(0):null;
	}
	
}
