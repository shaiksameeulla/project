/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;

/**
 * The Class StockRequisitionDAOImpl.
 *
 * @author mohammes
 */
public class StockRequisitionDAOImpl extends CGBaseDAO implements
		StockRequisitionDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockRequisitionDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockrequisition.dao.StockRequisitionDAO#saveStockRequisition(com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO)
	 */
	@Override
	public Boolean saveStockRequisition(StockRequisitionDO domainEntity)
			throws CGSystemException {
		try {
			getHibernateTemplate().saveOrUpdate(domainEntity);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionDAOImpl ::saveStockRequisition :: Exception ",e);
			throw new CGSystemException(e);
		}
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockrequisition.dao.StockRequisitionDAO#findRequisitionDtlsByReqNumber(com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO)
	 */
	@Override
	public StockRequisitionDO findRequisitionDtlsByReqNumber(
			StockRequisitionTO requisitionTo) throws CGSystemException {
		List<StockRequisitionDO> reqDtls=null;
		final String params[]= {StockCommonConstants.QRY_PARAM_REQ_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		final Object values[]={requisitionTo.getRequisitionNumber().trim(),StockCommonConstants.ACTIVE_STATUS,requisitionTo.getLoggedInOfficeId()};
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_REQ_DTLS_BY_REQ_NUMBER, params, values);
		return !StringUtil.isEmptyList(reqDtls)? reqDtls.get(0):null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockrequisition.dao.StockRequisitionDAO#findReqDtlsByReqNumberForApprove(com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO)
	 */
	@Override
	public StockRequisitionDO findReqDtlsByReqNumberForApprove(
			StockRequisitionTO requisitionTo) throws CGSystemException {
		List<StockRequisitionDO> reqDtls=null;
		long start=System.currentTimeMillis();
		LOGGER.debug("StockRequisitionDAOImpl ::findRequisitionDtlsByReqNumberForApprove---->START"+start);
		//FIXME need to change
		String params[]= {StockCommonConstants.QRY_PARAM_REQ_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_OFF_TYPE_CODE};
		Object values[]={requisitionTo.getRequisitionNumber(),StockCommonConstants.ACTIVE_STATUS,requisitionTo.getLoggedInOfficeId(),CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE};
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_REQ_DTLS_BY_REQ_NUMBER_APPROVE, params, values);
		if(CGCollectionUtils.isEmpty(reqDtls)){
			values[3]=CommonConstants.OFF_TYPE_CORP_OFFICE;
			reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_REQ_DTLS_BY_REQ_NUMBER_APPROVE, params, values);
			LOGGER.debug("StockRequisitionDAOImpl ::findRequisitionDtlsByReqNumberForApprove---->check for Corporate office");
		}
		
		long end=System.currentTimeMillis();
		LOGGER.debug("StockRequisitionDAOImpl ::findRequisitionDtlsByReqNumberForApprove---->END"+end +"Diff :"+(end-start));
		return !StringUtil.isEmptyList(reqDtls)? reqDtls.get(0):null;

	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockrequisition.dao.StockRequisitionDAO#approveRequisition(com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO)
	 */
	@Override
	public Boolean approveRequisition(StockRequisitionDO entityDO)
			throws CGSystemException {
		Boolean result=Boolean.FALSE;
		Session session=null;
		Transaction tx=null;
		
			 try {
				session=createSession();
				tx=session.beginTransaction();
				Query headerQry=session.createQuery(approveStockReqHeader(entityDO));
				headerQry.setTimestamp(StockCommonConstants.QRY_PARAM_APPROVED_DATE, entityDO.getApprovedDate());
				headerQry.executeUpdate();
				
				for(StockRequisitionItemDtlsDO itemDtls:entityDO.getRequisionItemDtls()){
					Query dtlsQry=session.createQuery(approveStockReqDtls(itemDtls));
					dtlsQry.setTimestamp(StockCommonConstants.QRY_PARAM_TRANS_MODIFIED_DATE, itemDtls.getTransactionModifiedDate());
					dtlsQry.executeUpdate();
					
				}
				tx.commit();
				result=Boolean.TRUE;
			} catch (Exception e) {
				// TODO remove later
				tx.rollback();
				LOGGER.error("StockRequisitionDAOImpl ::approveRequisition :: Exception ",e);
				throw new CGSystemException(e);
			}finally{
				closeSession(session);
			}
			
			 return result;
	}
	
	
	//Since we update very less no of columns ,we have decided to use below approach
	/**
	 * Approve stock req header.
	 *
	 * @param regDo the reg do
	 * @return the string
	 */
	private String approveStockReqHeader(StockRequisitionDO regDo){
		StringBuilder qry=new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO headerDo SET  ");
		qry.append("headerDo.approvedDate =:approvedDate");
		qry.append(" , ");
		//qry.append("headerDo.approvedByUserDO.userId ="+regDo.getApprovedByUserDO().getUserId());
		qry.append("headerDo.approvedByUserId ="+regDo.getApprovedByUserId());
		
		qry.append(" where headerDo.stockRequisitionId="+regDo.getStockRequisitionId());
		return qry.toString();
	}
	//Since we update very less no of columns ,we have decided to use below approach
	/**
	 * Approve stock req dtls.
	 *
	 * @param reqDtlsDo the req dtls do
	 * @return the string
	 */
	private String approveStockReqDtls(StockRequisitionItemDtlsDO reqDtlsDo){
		StringBuilder qry=new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO dtlsDo SET  ");
		qry.append("dtlsDo.approvedQuantity ="+reqDtlsDo.getApprovedQuantity());
		qry.append(" , ");
		qry.append("dtlsDo.approveRemarks = '"+reqDtlsDo.getApproveRemarks()+"'");
		qry.append(" , ");
		if(!StringUtil.isStringEmpty(reqDtlsDo.getProcurementType())){
		qry.append("dtlsDo.procurementType ='"+reqDtlsDo.getProcurementType().trim()+"'");
		qry.append(" , ");
		}
		qry.append("dtlsDo.transactionModifiedDate = :transactionModifiedDate");
		qry.append(" where dtlsDo.stockRequisitionItemDtlsId="+reqDtlsDo.getStockRequisitionItemDtlsId());
		return qry.toString();
	}
	
	/**
	 * 
	 * @param requisitionTo
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<StockRequisitionDO> searchReqDtlsByRequisitonOfficeForRhoView(
			ListStockRequisitionTO requisitionTo) throws CGSystemException {
		List<StockRequisitionDO> reqDtls=null;
		long start=System.currentTimeMillis();
		LOGGER.trace("StockRequisitionDAOImpl ::searchRequisitionDetailsForView---->START"+start);
		final String params[]= {StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_LOGGED_IN_OFFICE_ID,StockCommonConstants.QRY_PARAM_FROM_DATE,StockCommonConstants.QRY_PARAM_TO_DATE};
		final Object values[]={StockCommonConstants.ACTIVE_STATUS,requisitionTo.getRequisitionOfficeId(),requisitionTo.getLoggedInOfficeId(),requisitionTo.getFromDate(),requisitionTo.getToDate() };
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_REQ_DTLS_BY_REQUISITION_OFFICE, params, values);
	
		long end=System.currentTimeMillis();
		LOGGER.trace("StockRequisitionDAOImpl ::searchRequisitionDetailsForView---->END"+end +"Diff :"+(end-start));
		return reqDtls;

	}
	/**
	 * 
	 * @param requisitionTo
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public List<StockRequisitionDO> searchAllRequisitionDetailsForRhoView(
			ListStockRequisitionTO requisitionTo) throws CGSystemException {
		List<StockRequisitionDO> reqDtls=null;
		long start=System.currentTimeMillis();
		LOGGER.trace("StockRequisitionDAOImpl ::searchAllRequisitionDetailsForRhoView---->START"+start);
		final String params[]= {StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_LOGGED_IN_OFFICE_ID,StockCommonConstants.QRY_PARAM_FROM_DATE,StockCommonConstants.QRY_PARAM_TO_DATE};
		final Object values[]={StockCommonConstants.ACTIVE_STATUS,requisitionTo.getLoggedInOfficeId(),requisitionTo.getFromDate(),requisitionTo.getToDate()};
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_GET_ALL_REQ_BY_SUPPLYING_OFFICE, params, values);
	
		long end=System.currentTimeMillis();
		LOGGER.trace("StockRequisitionDAOImpl ::searchAllRequisitionDetailsForRhoView---->END"+end +"Diff :"+(end-start));
		return reqDtls;

	}

}
