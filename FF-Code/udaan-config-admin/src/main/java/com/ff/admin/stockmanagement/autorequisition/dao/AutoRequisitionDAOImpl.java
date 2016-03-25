/**
 * 
 */
package com.ff.admin.stockmanagement.autorequisition.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.autorequisition.constants.AutoRequisitionConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.StockOfficeMappingDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class AutoRequisitionDAOImpl.
 *
 * @author mohammes
 */
public class AutoRequisitionDAOImpl extends CGBaseDAO implements
		AutoRequisitionDAO {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoRequisitionDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.autorequisition.dao.AutoRequisitionDAO#getOfficeDtlsForAutoReq()
	 */
	@Override
	public List<Integer> getOfficeDtlsForAutoReq() throws CGSystemException {
		List<Integer> stOfficeList=null;
		LOGGER.trace("AutoRequisitionDAOImpl :: getOfficeDtlsForAutoReq :: START ");
		String [] paramName={AutoRequisitionConstants.QRY_PARAM_IS_AUTO_REQ_REQUIRED,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFF_TYPE_CODE};
		
		Object[] value={AutoRequisitionConstants.AUTO_REQ_STATUS_YES,StockCommonConstants.ACTIVE_STATUS,CommonConstants.OFF_TYPE_BRANCH_OFFICE};
		stOfficeList = getHibernateTemplate().findByNamedQueryAndNamedParam(AutoRequisitionConstants.QRY_GET_OFFICE_DTLS_FOR_AUTO_REQ, paramName, value);
		
		LOGGER.trace("AutoRequisitionDAOImpl :: getOfficeDtlsForAutoReq :: END with Out param :["+(CGCollectionUtils.isEmpty(stOfficeList)?0:stOfficeList.size())+"]");
	
		return stOfficeList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.autorequisition.dao.AutoRequisitionDAO#getStockDtlsForAutoReqByOffice(java.lang.Integer)
	 */
	@Override
	public List<StockOfficeMappingDO> getStockDtlsForAutoReqByOffice(
			Integer officeId) throws CGSystemException {
		List<StockOfficeMappingDO> stOfficeList=null;
		int defaultResultSet=getHibernateTemplate().getMaxResults();
		try {
			getHibernateTemplate().setMaxResults(10);
			LOGGER.trace("AutoRequisitionDAOImpl :: getStockDtlsForAutoReqByOffice :: START with In param :["+officeId+"]");
			String [] paramName={AutoRequisitionConstants.QRY_PARAM_IS_AUTO_REQ_REQUIRED,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_ID};
			Object[] value={AutoRequisitionConstants.AUTO_REQ_STATUS_YES,StockCommonConstants.ACTIVE_STATUS,officeId};
			stOfficeList = getHibernateTemplate().findByNamedQueryAndNamedParam(AutoRequisitionConstants.QRY_GET_STOCK_DTLS_FOR_AUTO_REQ, paramName, value);
			LOGGER.trace("AutoRequisitionDAOImpl :: getStockDtlsForAutoReqByOffice :: END with Out param :["+(CGCollectionUtils.isEmpty(stOfficeList)?0:stOfficeList.size())+"]");
		}finally{
			getHibernateTemplate().setMaxResults(defaultResultSet);
		}
		
		return stOfficeList;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.autorequisition.dao.AutoRequisitionDAO#updateStatusForAutoReq(java.util.List)
	 */
	@Override
	public Integer updateStatusForAutoReq(List<Integer> stockIdList)
			throws CGSystemException {
		LOGGER.trace("AutoRequisitionDAOImpl :: updateStatusForAutoReq :: START");
		LOGGER.trace("AutoRequisitionDAOImpl :: updateStatusForAutoReq :: Input params :["+stockIdList.toString()+"]");
		Session session=openTransactionalSession();
		Query qry=null;
		int result=0;
		try {
			qry= session.getNamedQuery(AutoRequisitionConstants.QRY_UPDATE_STATUS_FOR_AUTO_REQ);
			qry.setString(AutoRequisitionConstants.QRY_PARAM_AUTO_REQ_STATUS, AutoRequisitionConstants.AUTO_REQ_STATUS_NO);
			qry.setParameterList(AutoRequisitionConstants.QRY_PARAM_STOCK_ID,stockIdList);
			LOGGER.trace("AutoRequisitionDAOImpl :: updateStatusForAutoReq :: END");
			result= qry.executeUpdate();
			LOGGER.trace("AutoRequisitionDAOImpl :: updateStatusForAutoReq :: Updated Count :[" +result+"]");
		} finally{
			closeTransactionalSession(session);
		}
		LOGGER.trace("AutoRequisitionDAOImpl :: updateStatusForAutoReq :: END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.autorequisition.dao.AutoRequisitionDAO#saveAutoReq(com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO)
	 */
	@Override
	public Boolean saveAutoReq(StockRequisitionDO requisitionDO)
			throws CGSystemException {
		getHibernateTemplate().save(requisitionDO);
		return true;
	}
	@Override
	public List<String> getAllRHOCodes() throws CGSystemException {
		List<String> stOfficeList=null;
		LOGGER.trace("AutoRequisitionDAOImpl :: getAllRhoCodes :: START ");
		String [] paramName={AutoRequisitionConstants.QRY_PARAM_OFFICE_TYPE};
		Object[] value={CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE};
		stOfficeList = getHibernateTemplate().findByNamedQueryAndNamedParam(AutoRequisitionConstants.QRY_GET_ALL_RHO_CODES, paramName, value);
		LOGGER.trace("AutoRequisitionDAOImpl :: getAllRHOCodes :: END with Out param :["+(CGCollectionUtils.isEmpty(stOfficeList)?0:stOfficeList.size())+"]");
		return stOfficeList;
	}
	
	/**
	 * Gets the office by id.
	 *
	 * @param officeId the office id
	 * @return the office by id
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public OfficeDO getOfficeById(Integer officeId) throws CGSystemException {
		List<OfficeDO> stOfficeList=null;
		stOfficeList = getHibernateTemplate()
				.findByNamedQueryAndNamedParam("getOfficeDetails",
						"officeId", officeId);
		return !CGCollectionUtils.isEmpty(stOfficeList)?stOfficeList.get(0):null;
	}
	@Override
	public List<StockRequisitionItemDtlsDO> getRequisitionDtlsForConsolidation(String rhoCode) throws CGSystemException {
		List<StockRequisitionItemDtlsDO> reqDtls=null;
		LOGGER.trace("AutoRequisitionDAOImpl :: getRequisitionDtlsForConsolidation :: START ");
		String [] paramName={AutoRequisitionConstants.QRY_PARAM_FOR_RHO_CODE,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,AutoRequisitionConstants.QRY_PARAM_IS_CONSOLIDATED,StockCommonConstants.REQ_PARAM_PROCUREMENT};
		Object[] value={rhoCode,StockCommonConstants.ACTIVE_STATUS,AutoRequisitionConstants.CONSOLIDATED_FLAG_NO,StockCommonConstants.STOCK_EXTERNAL_PROCUREMENT};
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(AutoRequisitionConstants.QRY_REQUISITION_DTLS_FOR_RHO, paramName, value);
		LOGGER.trace("AutoRequisitionDAOImpl :: getRequisitionDtlsForConsolidation :: END with Out param :["+(CGCollectionUtils.isEmpty(reqDtls)?0:reqDtls.size())+"]");
		return reqDtls;
	}
	
	@Override
	public void updateRequisitionConsolidatedFlag(List<StockRequisitionItemDtlsDO> reqItemDtls) throws CGSystemException {
		LOGGER.trace("AutoRequisitionDAOImpl :: updateRequisitionConsolidatedFlag :: START ");
		getHibernateTemplate().saveOrUpdateAll(reqItemDtls);
		LOGGER.trace("AutoRequisitionDAOImpl :: updateRequisitionConsolidatedFlag :: END ");
	}
	
}
