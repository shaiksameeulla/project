/**
 * 
 */
package com.ff.admin.stockmanagement.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.autorequisition.constants.AutoRequisitionConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;

/**
 * @author mohammes
 * 
 * Going forward, for the new Stock Module schedulers this Class works as a DAO Layer
 *
 */
public class StockScheduledDAOImpl extends CGBaseDAO implements StockScheduledDAO {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockScheduledDAOImpl.class);
	@Override
	public List<StockIssuePaymentDetailsDO> getStockPaymentDetails() throws CGSystemException{
		List<StockIssuePaymentDetailsDO> stOfficeList=null;
		LOGGER.trace("StockScheduledDAOImpl :: getStockPaymentDetails :: START ");
		String [] paramName={StockCommonConstants.QRY_PARAM_POSTED};
		Object[] value={AutoRequisitionConstants.STOCK_INTEGRATED_WITH_STOCK_NO};
		int defaultFetchCount=getHibernateTemplate().getMaxResults();
		try {
			getHibernateTemplate().setMaxResults(40);
			stOfficeList = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_STOCK_PAYMENT_DTLS_FOR_MISC_EXP, paramName, value);
		}finally{
			getHibernateTemplate().setMaxResults(defaultFetchCount);
		}
		LOGGER.trace("StockScheduledDAOImpl :: getStockPaymentDetails :: END with Out param :["+(CGCollectionUtils.isEmpty(stOfficeList)?0:stOfficeList.size())+"]");
	
		return stOfficeList;
	}

	@Override
	public Integer updateStockPaymentDetails(
			List<StockIssuePaymentDetailsDO> paymentDtls)throws CGSystemException {
		Session session= getSession(false);
		LOGGER.trace("StockScheduledDAOImpl :: updateStockPaymentDetails :: START ");
		Query qry = session.getNamedQuery(StockCommonConstants.QRY_UPDATE_PAYMENT_DTLS);
		int updatedCount=0;
		for(StockIssuePaymentDetailsDO paymnt:paymentDtls){
			qry.setLong(StockCommonConstants.QRY_PARAM_STOK_PAYMNT_ID, paymnt.getStockPaymentId());
			qry.setString(StockCommonConstants.QRY_PARAM_POSTED, paymnt.getIsProcessed());
			updatedCount = updatedCount+qry.executeUpdate();
		}
		LOGGER.trace("StockScheduledDAOImpl :: updateStockPaymentDetails :: ENDS WITH updatedCount: "+updatedCount);
		return updatedCount;
	}
	@Override
	public Integer updateStockPaymentDetails(
			StockIssuePaymentDetailsDO paymentDO)throws CGSystemException {
		Session session= getSession(false);
		LOGGER.trace("StockScheduledDAOImpl :: updateStockPaymentDetails :: START ");
		Query qry = session.getNamedQuery(StockCommonConstants.QRY_UPDATE_PAYMENT_DTLS);
		int updatedCount=0;
		qry.setLong(StockCommonConstants.QRY_PARAM_STOK_PAYMNT_ID, paymentDO.getStockPaymentId());
		qry.setString(StockCommonConstants.QRY_PARAM_POSTED, paymentDO.getIsProcessed());
		updatedCount = qry.executeUpdate();
		LOGGER.trace("StockScheduledDAOImpl :: updateStockPaymentDetails :: ENDS WITH updatedCount: "+updatedCount);
		return updatedCount;
	}
	@Override
	public void saveCollectionDetails(CollectionDO collectionDO)throws CGSystemException {
		LOGGER.trace("StockScheduledDAOImpl :: saveCollectionDetails :: START ");
		getHibernateTemplate().saveOrUpdate(collectionDO);
		LOGGER.trace("StockScheduledDAOImpl :: saveCollectionDetails :: ENDS ");
	}
	
}
