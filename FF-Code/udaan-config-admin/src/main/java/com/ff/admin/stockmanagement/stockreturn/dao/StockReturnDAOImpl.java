/**
 * 
 */
package com.ff.admin.stockmanagement.stockreturn.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockreturn.constants.StockReturnConstants;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnItemDtlsDO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockReturnDAOImpl.
 *
 * @author cbhure
 */
public class StockReturnDAOImpl extends CGBaseDAO implements StockReturnDAO {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockReturnDAOImpl.class);
	

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO#saveReturnDetails(com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO)
	 */
	@Override
	public Boolean saveReturnDetails(StockReturnDO returnDo)
			throws CGSystemException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx= session.beginTransaction();
			session.save(returnDo);//data insertion
			
			stockDecreaseAtBranch(returnDo, session);//stock Decrease
			
			
			stockIncreaseAtRHO(returnDo, session);//Stock Increase At RHO
			updatePartialReceiptDtls(returnDo, session);//for partial receipt
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockReturnDAOImpl:::saveReturnDetails:: Exception  ",e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
	
		return Boolean.TRUE;
	}
	private void updatePartialReceiptDtls(StockReturnDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReturnDAOImpl ::updatePartialReceiptDtls :: START");
		String transactionType=domainEntity.getTransactionFromType();
			Integer balanceQnty = null;
			for (StockReturnItemDtlsDO dtlsDO : domainEntity.getReturnItemDtls()) {
				balanceQnty = getBalanceQntyForReturn(session, dtlsDO.getStockItemDtlsId(), domainEntity.getTransactionFromType());
				String qryName = prepareQryForBalanceQnty(transactionType,
						balanceQnty);
				int rowUpdated=updateIssueDtls(session, qryName, dtlsDO);
				LOGGER.trace("StockReturnDAOImpl ::updatePartialReceiptDtls :: Updated records ["+rowUpdated+"]");
			}
			LOGGER.trace("StockReturnDAOImpl ::updatePartialReceiptDtls :: END");
	}
	private Integer updateIssueDtls(Session session, String qryName,
			StockReturnItemDtlsDO dtlsDO) throws CGSystemException {
		Query qry = session.getNamedQuery(qryName);
		qry.setInteger(StockCommonConstants.QRY_PARAM_RECEIVED_QNTY,
				dtlsDO.getReturningQuantity());
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID,
				dtlsDO.getStockItemDtlsId());
		return qry.executeUpdate();
	}
	private String prepareQryForBalanceQnty(String transactionType,
			Integer balanceQnty) {
		String qryName = null;
		LOGGER.trace("StockReturnDAOImpl ::prepareQryForBalanceQnty :: START");
		if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_RECEIPT_TYPE)){
			if (StringUtil.isNull(balanceQnty)) {
				// i.e first time return against receipt then use below
				// formula
				// balanceReturnQuantity = receiptQuantity -
				// :returningQuantity
				qryName = StockCommonConstants.QRY_UPDATE_RETURN_BAL_QNTY_WITH_RETURN_QNTY_FOR_RETURN;
			}else{
				// i.e Partial return happened earlier and receiving one
				// more time
				// we use below formula
				// balanceReturnQuantity = balanceReturnQuantity -
				// :receivedQuantity
				qryName = StockCommonConstants.QRY_UPDATE_RETURN_BAL_QNTY_WITH_BAL_RETURN_QNTY_FOR_RETURN;
			}

		}
		LOGGER.trace("StockReturnDAOImpl ::prepareQryForBalanceQnty :: END");
		return qryName;
	}
	
	private Integer getBalanceQntyForReturn(Session session, Long issItemDtlsId, String transactionType)
			throws CGSystemException {
		List<Integer> balanceQnty = null;
		String qryName=null;
		if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_RECEIPT_TYPE)){
			qryName=StockCommonConstants.QRY_GET_BALANCE_RETURNED_QNTY_FOR_RETURN;
		}
		Query qry = session.getNamedQuery(qryName);
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID, issItemDtlsId);
		balanceQnty =qry.list();
		return !StringUtil.isEmptyList(balanceQnty) ? balanceQnty.get(0) : null;
	}
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO#findDetailsByReturnNumber(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Override
	public StockReturnDO findDetailsByReturnNumber(StockReturnTO stockReturnTO) throws CGSystemException {
		// TODO Auto-generated method stub
		List<StockReturnDO> returnDtls = null;
		String params[] = {StockCommonConstants.QRY_PARAM_RETURN_NUMBER};
		Object values[] = {stockReturnTO.getStockReturnNumber()};
		returnDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_RETURN_DTLS_BY_RETURN_NUMBER, params, values);
		return !CGCollectionUtils.isEmpty(returnDtls)?returnDtls.get(0):null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO#findDetailsByIssueNumber(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Override
	public StockIssueDO findDetailsByIssueNumber(StockReturnTO stockReturnTO) throws CGSystemException {
		List<StockIssueDO> returnDtls = null;
		
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_ISSUED_TO_TYPE};
		Object values[] = {stockReturnTO.getStockIssueNumber(),StockCommonConstants.ACTIVE_STATUS,stockReturnTO.getLoggedInOfficeId(),UdaanCommonConstants.ISSUED_TO_BRANCH};
		returnDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockReturnConstants.QRY_ISSUE_DTLS_BY_ISSUE_NUMBER_FOR_RETURN, params, values);
		return !CGCollectionUtils.isEmpty(returnDtls)?returnDtls.get(0):null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO#isIssueNumberReceivedForReturn(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Override
	public Long isIssueNumberReceivedForReturn(StockReturnTO stockReturnTO) throws CGSystemException {
		List<Long> returnDtls = null;
		
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {stockReturnTO.getStockIssueNumber(),StockCommonConstants.ACTIVE_STATUS,stockReturnTO.getLoggedInOfficeId()};
		returnDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockReturnConstants.QRY_IS_ISSUE_NUMBER_RECEIVED_FOR_RETURN, params, values);
		return !CGCollectionUtils.isEmpty(returnDtls)?returnDtls.get(0):null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO#getReceivedQntyForIssueNumberForReturn(com.ff.to.stockmanagement.stockreturn.StockReturnTO, java.lang.Integer)
	 */
	@Override
	public Long getReceivedQntyForIssueNumberForReturn(StockReturnTO stockReturnTO,Integer itemId) throws CGSystemException {
		List<Long> receivedQnty = null;
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_ITEM_ID};
		Object values[] = {stockReturnTO.getStockIssueNumber(),StockCommonConstants.ACTIVE_STATUS,itemId};
		
		receivedQnty = getHibernateTemplate().findByNamedQueryAndNamedParam(StockReturnConstants.QRY_RECEIVED_QNTY_FOR_ISSUE_NUMBER_FOR_RETURN, params, values);
		return !CGCollectionUtils.isEmpty(receivedQnty)?receivedQnty.get(0):null;
	}
	
	/**
	 * Stock decrease at branch.
	 *
	 * @param domainEntity the domain entity
	 * @param session the session
	 * @throws CGSystemException the cG system exception
	 */
	private void stockDecreaseAtBranch(StockReturnDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReturnDAOImpl ::stockDecreaseAtBranch :: START");
		StockUpdateInputTO stockUpdateTo=prepareStockUpdateTO(domainEntity);
		
		for (StockReturnItemDtlsDO dtlsDO : domainEntity
				.getReturnItemDtls()) {
			stockUpdateTo.setQuantity(dtlsDO.getReturningQuantity());
			stockUpdateTo.setItemId(dtlsDO.getItemDO().getItemId());
			boolean status=	StockUtility.updateUniversalStock(session, stockUpdateTo);
			LOGGER.debug("StockReturnDAOImpl:::stockDecreaseAtBranch::: record status :["+status+"]");
		}
		LOGGER.trace("StockReturnDAOImpl ::stockDecreaseAtBranch :: END");
	}
	
	/**
	 * Prepare stock update to.
	 *
	 * @param domainEntity the domain entity
	 * @return the stock update input to
	 */
	private StockUpdateInputTO prepareStockUpdateTO(StockReturnDO domainEntity) {
		StockUpdateInputTO stockUpdateTo;
		stockUpdateTo = new StockUpdateInputTO();
		stockUpdateTo.setPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		stockUpdateTo.setPartyTypeId(domainEntity.getReturningOfficeDO().getOfficeId());
		stockUpdateTo.setIsDecrease(true);
		return stockUpdateTo;
	}
	private void stockIncreaseAtRHO(StockReturnDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReturnDAOImpl ::stockIncreaseAtRHO :: START");
		StockUpdateInputTO stockUpdateTo=prepareStockUpdateTOForIncrease(domainEntity);
		
		for (StockReturnItemDtlsDO dtlsDO : domainEntity
				.getReturnItemDtls()) {
			stockUpdateTo.setQuantity(dtlsDO.getReturningQuantity());
			stockUpdateTo.setItemId(dtlsDO.getItemDO().getItemId());
			boolean status=	StockUtility.updateUniversalStock(session, stockUpdateTo);
			LOGGER.debug("StockReturnDAOImpl:::stockIncreaseAtRHO::: record status :["+status+"]");
		}
		LOGGER.trace("StockReturnDAOImpl ::stockIncreaseAtRHO :: END");
	}
	private StockUpdateInputTO prepareStockUpdateTOForIncrease(StockReturnDO domainEntity) {
		StockUpdateInputTO stockUpdateTo;
		stockUpdateTo = new StockUpdateInputTO();
		stockUpdateTo.setPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		stockUpdateTo.setPartyTypeId(domainEntity.getIssuedOfficeDO().getOfficeId());
		stockUpdateTo.setIsDecrease(false);
		return stockUpdateTo;
	}
	@Override
	public StockReceiptDO findDetailsByAcknowledgementNumber(StockReturnTO to) throws CGSystemException {
		List<StockReceiptDO> returnDtls = null;
		StockReceiptDO stock = null;
		String params[] = { StockCommonConstants.QRY_PARAM_ACK_NUMBER,
				StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,
				StockCommonConstants.QRY_PARAM_OFFICEID };
		Object values[] = { to.getAcknowledgementNumber(),
				StockCommonConstants.ACTIVE_STATUS, to.getLoggedInOfficeId() };
		returnDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_RECEIPT_DTLS_BY_ACK_NUMBER, params, values);
		if(returnDtls != null && !returnDtls.isEmpty()) {
			stock = returnDtls.get(0);
		}
		return stock;
	}
	
}
