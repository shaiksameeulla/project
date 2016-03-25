package com.ff.admin.report.dao;

import java.util.List;

import com.capgemini.lbs.framework.constants.CommonConstants;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.domain.mec.SAPReportDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;

/**
 * @author khassan
 *
 */
public class OutstandingReportDAOImpl extends CGBaseDAO implements
		OutstandingReportDAO {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutstandingReportDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.report.dao.OutstandingReportDAO#saveReportData(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean saveReportData(SAPOutstandingPaymentDO outstandingReportDO) throws CGSystemException {
		

		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx= session.beginTransaction();
			session.save(outstandingReportDO);//data insertion
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockReturnDAOImpl:::saveReturnDetails:: Exception  ", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
	
		return Boolean.TRUE;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SAPReportDO> getReportList() throws CGSystemException
			{
		List<SAPReportDO> reportNameList = null;
		
		try{
			 reportNameList= getHibernateTemplate().findByNamedQuery(MECCommonConstants.REPORT_NAME_LIST);
		}catch(Exception e){
			LOGGER.error("Unable to featch report name",e);
			throw new CGSystemException(e);	
		}
		
		return reportNameList;
	}

}
