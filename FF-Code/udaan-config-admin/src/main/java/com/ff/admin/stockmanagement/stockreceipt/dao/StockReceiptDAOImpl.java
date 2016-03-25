package com.ff.admin.stockmanagement.stockreceipt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.stockreceipt.constants.StockReceiptConstants;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockReceiptDAOImpl.
 */
@SuppressWarnings("unchecked")
public class StockReceiptDAOImpl extends CGBaseDAO implements StockReceiptDAO {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockReceiptDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#saveReceiptDtls(com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO)
	 */
	@Override
	public Boolean saveReceiptDtls(StockReceiptDO domainEntity)
			throws CGSystemException {
		Session session = null;
		Transaction tx=null;
		long starttime=System.currentTimeMillis();
		try {
			
			LOGGER.debug("StockReceiptDAOImpl::saveReceiptDtls ..Start Time:[ "+starttime+"]");
			session = createSession();
			tx= session.beginTransaction();
			session.save(domainEntity);//data insertion
			updatePartialReceiptDtls(domainEntity, session);//for partial receipt
			stockIncreaseAtBranch(domainEntity, session);//stock increase
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockReceiptDAOImpl:::saveReceiptDtls:: Exception ",e);
			LOGGER.error("StockReceiptDAOImpl:::saveReceiptDtls:: Tx Rollbacked ");
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::saveReceiptDtls ..End Time:[ "+endtime+"] Differece :["+(endtime-starttime)+"]");
		return Boolean.TRUE;
	}

	/**
	 * Stock increase at branch.
	 *
	 * @param domainEntity the domain entity
	 * @param session the session
	 * @throws CGSystemException the cG system exception
	 */
	private void stockIncreaseAtBranch(StockReceiptDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReceiptDAOImpl ::stockIncreaseAtBranch :: START");
		StockUpdateInputTO stockUpdateTo=prepareStockUpdateTO(domainEntity);
		
		for (StockReceiptItemDtlsDO dtlsDO : domainEntity
				.getStockReceiptItemDtls()) {
			stockUpdateTo.setQuantity(dtlsDO.getReceivedQuantity());
			stockUpdateTo.setItemId(dtlsDO.getItemDO().getItemId());
			boolean status=	StockUtility.updateUniversalStock(session, stockUpdateTo);
			
			LOGGER.debug("StockReceiptDAOImpl:::saveReceiptDtls::stockIncreaseAtBranch:: record status :["+status+"]");
		}
		LOGGER.trace("StockReceiptDAOImpl ::stockIncreaseAtBranch :: END");
	}

	/**
	 * Update partial receipt dtls.
	 *
	 * @param domainEntity the domain entity
	 * @param session the session
	 * @throws CGSystemException the cG system exception
	 */
	private void updatePartialReceiptDtls(StockReceiptDO domainEntity,
			Session session) throws CGSystemException {
		LOGGER.trace("StockReceiptDAOImpl ::updatePartialReceiptDtls :: START");
		String transactionType=domainEntity.getTransactionFromType();
			Integer balanceQnty = null;
			for (StockReceiptItemDtlsDO dtlsDO : domainEntity
					.getStockReceiptItemDtls()) {
				balanceQnty = getBalanceQntyForReceipt(session, dtlsDO.getStockItemDtlsId(), domainEntity.getTransactionFromType());
				String qryName = prepareQryForBalanceQnty(transactionType,
						balanceQnty);
				updateIssueDtls(session, qryName, dtlsDO);
			}
			LOGGER.trace("StockReceiptDAOImpl ::updatePartialReceiptDtls :: END");
	}

	/**
	 * Prepare qry for balance qnty.
	 *
	 * @param transactionType the transaction type
	 * @param balanceQnty the balance qnty
	 * @return the string
	 */
	private String prepareQryForBalanceQnty(String transactionType,
			Integer balanceQnty) {
		String qryName = null;
		LOGGER.trace("StockReceiptDAOImpl ::prepareQryForBalanceQnty :: START");
		if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_ISSUE_TYPE)){
			if (StringUtil.isNull(balanceQnty)) {
				// i.e first time receipt against issue then use below
				// formula
				// balanceReceiptQuantity = issuedQuantity -
				// :receivedQuantity
				qryName = StockCommonConstants.QRY_UPDATE_BY_ISSUE_QNTY_FOR_SI_RECEIPT;
			}else{
				// i.e Partial receipt happened earlier and receiving one
				// more time
				// we use below formula
				// balanceReceiptQuantity = balanceReceiptQuantity -
				// :receivedQuantity
				qryName = StockCommonConstants.QRY_UPDATE_BAL_QNTY_FOR_SI_RECEIPT;
			}

		}else if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE)){
			if (StringUtil.isNull(balanceQnty)) {
				qryName = StockCommonConstants.QRY_UPDATE_BAL_QNTY_WITH_APPR_QNTY_FOR_PR_RECEIPT;
			}else{
				qryName = StockCommonConstants.QRY_UPDATE_BAL_QNTY_WITH_RECPT_QNTY_FOR_PR_RECEIPT;
			}
		}
		LOGGER.trace("StockReceiptDAOImpl ::prepareQryForBalanceQnty :: END");
		return qryName;
	}

	// Method for update balance Issue Quantity
	/**
	 * Update issue dtls.
	 *
	 * @param session the session
	 * @param qryName the qry name
	 * @param dtlsDO the dtls do
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	private Integer updateIssueDtls(Session session, String qryName,
			StockReceiptItemDtlsDO dtlsDO) throws CGSystemException {
		Query qry = session.getNamedQuery(qryName);
		qry.setInteger(StockCommonConstants.QRY_PARAM_RECEIVED_QNTY,
				dtlsDO.getReceivedQuantity());
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID,
				dtlsDO.getStockItemDtlsId());
		return qry.executeUpdate();
	}

	/**
	 * Gets the balance qnty for receipt.
	 * @param session TODO
	 * @param issItemDtlsId the iss item dtls id
	 * @param transactionType the transaction type
	 *
	 * @return the balance qnty for receipt
	 * @throws CGSystemException the cG system exception
	 */
	private Integer getBalanceQntyForReceipt(Session session, Long issItemDtlsId, String transactionType)
			throws CGSystemException {
		List<Integer> balanceQnty = null;
		String qryName=null;
		if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_ISSUE_TYPE)){
			qryName=StockCommonConstants.QRY_GET_ISSUED_BALANCE_QNTY_RECEIPT;
		}else if(transactionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE)){
			qryName=StockCommonConstants.QRY_GET_PR_BALANCE_QNTY_RECEIPT;
		}
		Query qry = session.getNamedQuery(qryName);
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID, issItemDtlsId);
		balanceQnty =qry.list();
		/*balanceQnty = getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID,
				issItemDtlsId);*/
		return !StringUtil.isEmptyList(balanceQnty) ? balanceQnty.get(0) : null;
	}

	

	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#findDetailsByReceiptNumber(com.ff.to.stockmanagement.stockreceipt.StockReceiptTO)
	 */
	@Override
	public StockReceiptDO findDetailsByReceiptNumber(StockReceiptTO to)
			throws CGSystemException {
		List<StockReceiptDO> receiptDtls = null;
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByReceiptNumber ..Start Time:[ "+starttime+"]");
		String params[] = { StockCommonConstants.QRY_PARAM_ACK_NUMBER,
				StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,
				StockCommonConstants.QRY_PARAM_OFFICEID };
		Object values[] = { to.getAcknowledgementNumber(),
				StockCommonConstants.ACTIVE_STATUS, to.getLoggedInOfficeId() };
		receiptDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(
				StockCommonConstants.QRY_FIND_RECEIPT_DTLS_BY_ACK_NUMBER,
				params, values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByReceiptNumber ..End Time:[ "+endtime+"] Differece :["+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(receiptDtls) ? receiptDtls.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#findDetailsByIssueNumber(com.ff.to.stockmanagement.stockreceipt.StockReceiptTO)
	 */
	@Override
	public StockIssueDO findDetailsByIssueNumber(StockReceiptTO to)
			throws CGSystemException {
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByIssueNumber ..Start Time:[ "+starttime+"]");
		List<StockIssueDO> issueDtls = null;
		String params[] = { StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,
						StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,
						StockCommonConstants.QRY_PARAM_OFFICEID,
						StockCommonConstants.QRY_PARAM_ISSUED_TO_TYPE };
		Object values[] = { to.getStockIssueNumber(),
						StockCommonConstants.ACTIVE_STATUS, to.getLoggedInOfficeId(),
						UdaanCommonConstants.ISSUED_TO_BRANCH };
	
		issueDtls = getHibernateTemplate()
				.findByNamedQueryAndNamedParam(
						StockCommonConstants.QRY_ISSUE_DTLS_BY_ISSUE_NUMBER_FOR_RECEIPT,
						params, values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByIssueNumber ..End Time:[ "+endtime+"] Differece :["+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(issueDtls) ? issueDtls.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#findDetailsByRequisitionNumber(com.ff.to.stockmanagement.stockreceipt.StockReceiptTO)
	 */
	@Override
	public StockRequisitionDO findDetailsByRequisitionNumber(StockReceiptTO to)
			throws CGSystemException {
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByRequisitionNumber ..Start Time:[ "+starttime+"]");
		List<StockRequisitionDO> reqDtls = null;
		String qry=StockCommonConstants.QRY_REQ_BYREQNUM_FOR_RECEIPT;
		if(!StringUtil.isStringEmpty(to.getScreenType())){
			qry=StockCommonConstants.QRY_REQ_BYREQNUM_FOR_RECEIPT_AT_RHO;
		}
		String params[] = { StockCommonConstants.QRY_PARAM_REQ_NUMBER,
				StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,
				StockCommonConstants.QRY_PARAM_OFFICEID };
		Object values[] = { to.getRequisitionNumber(),
				StockCommonConstants.ACTIVE_STATUS, to.getLoggedInOfficeId() };
		reqDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(
				qry, params,
				values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptDAOImpl::findDetailsByRequisitionNumber ..End Time:[ "+endtime+"] Differece :["+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(reqDtls) ? reqDtls.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#updateReceiptDtls(com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO)
	 */
	@Override
	@Deprecated
	public Boolean updateReceiptDtls(StockReceiptDO domainEntity)
			throws CGSystemException {
		Boolean result = Boolean.FALSE;
		Session session = null;
		Transaction tx = null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			Query headerQry = session
					.createQuery(updateStockReceiptBranchHeader(domainEntity));
			headerQry.executeUpdate();

			for (StockReceiptItemDtlsDO itemDtls : domainEntity
					.getStockReceiptItemDtls()) {
				Query dtlsQry = session
						.createQuery(updateStockReceiptBranchItemDtls(itemDtls));
				dtlsQry.setTimestamp(
						StockCommonConstants.QRY_PARAM_TRANS_MODIFIED_DATE,
						itemDtls.getTransactionModifiedDate());
				dtlsQry.executeUpdate();
			}
			tx.commit();
			result = Boolean.TRUE;
		} catch (Exception e) {
			// TODO remove later
			tx.rollback();
			LOGGER.error("StockReceiptDAOImpl ::updateReceiptDtls :: Exception ",e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		return result;
	}

	// Since we update very less no of columns ,we have decided to use below
	// approach
	/**
	 * Update stock receipt branch header.
	 *
	 * @param recDo the rec do
	 * @return the string
	 */
	@Deprecated
	private String updateStockReceiptBranchHeader(StockReceiptDO recDo) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO headerDo SET ");
		//qry.append("headerDo.updatedByUser.userId = "+ recDo.getUpdatedByUser().getUserId());
		qry.append("headerDo.updatedBy = "+ recDo.getUpdatedBy());
		qry.append(" WHERE headerDo.stockReceiptId = "+ recDo.getStockReceiptId());
		return qry.toString();
	}

	// Since we update very less no of columns ,we have decided to use below
	// approach
	/**
	 * Update stock receipt branch item dtls.
	 *
	 * @param recDtlsDo the rec dtls do
	 * @return the string
	 */
	@Deprecated
	private String updateStockReceiptBranchItemDtls(
			StockReceiptItemDtlsDO recDtlsDo) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO dtlsDo SET ");
		qry.append("dtlsDo.receivedQuantity = "
				+ recDtlsDo.getReceivedQuantity());
		qry.append(CommonConstants.COMMA);
		qry.append("dtlsDo.remarks = '" + recDtlsDo.getRemarks() + "'");
		if (!StringUtil.isStringEmpty(recDtlsDo.getStartSerialNumber())) {
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.officeProductCodeInSeries = '"
					+ recDtlsDo.getOfficeProductCodeInSeries() + "'");
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.startLeaf = " + recDtlsDo.getStartLeaf());
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.endLeaf = " + recDtlsDo.getEndLeaf());
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.startSerialNumber = '"
					+ recDtlsDo.getStartSerialNumber() + "'");
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.endSerialNumber = '"
					+ recDtlsDo.getEndSerialNumber() + "'");
			qry.append(CommonConstants.COMMA);
		}else{
			qry.append(CommonConstants.COMMA);
		}
		qry.append("dtlsDo.transactionModifiedDate = :transactionModifiedDate");
		qry.append(" WHERE dtlsDo.stockReceiptItemDtlsId="
				+ recDtlsDo.getStockReceiptItemDtlsId());
		return qry.toString();
	}

	

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#isSeriesAlreadyReceiedWithReqNumber(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesAlreadyReceiedWithReqNumber(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_EXCLUDE_ID;
		}
		isAlreadyExist=isSeriesAlreadyReceied(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceiedWithReqNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_WITH_RANGE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_EXCLUDE_ID_WITH_RANGE;
		}
		isAlreadyExist=isSeriesAlreadyReceiedWithRange(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceiedWithReqNumberUnderItemType(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEM_TYPE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID;
		}
		isAlreadyExist=isSeriesAlreadyReceied(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceiedWithReqNumberUnderItemTypeWithRange(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEM_TYPE_WITH_RANGE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID_WITH_RANGE;
		}
		isAlreadyExist=isSeriesAlreadyReceiedWithRange(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#isSeriesAlreadyReceivedWithIssueNumber(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesAlreadyReceivedWithIssueNumber(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_EXCLUDE_ID;
		}
		isAlreadyExist=isSeriesAlreadyReceied(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceivedWithIssueNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_WITH_RANGE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_EXCLUDE_ID_WITH_RANGE;
		}
		isAlreadyExist=isSeriesAlreadyReceiedWithRange(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#isSeriesAlreadyReceiedOtherReqNumber(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesAlreadyReceiedOtherReqNumber(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_EXCLUDE_ID;
		}
		isAlreadyExist=isSeriesAlreadyReceied(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceiedOtherReqNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_WITH_RANGE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_EXCLUDE_ID_WITH_RANGE;
		}
		isAlreadyExist=isSeriesAlreadyReceiedWithRange(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	
	@Override
	public Boolean isSeriesAlreadyReceiedOtherReqNumberUnderItmeType(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID;
		}
		isAlreadyExist=isSeriesAlreadyReceied(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	@Override
	public Boolean isSeriesAlreadyReceiedOtherReqNumberUnderItmeTypeWithRange(
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> isAlreadyExist=null;
		String qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_WITH_RANGE;
		if(!StringUtil.isEmptyLong(validationTO.getStockReceiptItemDetailsId())){
			qryName=StockReceiptConstants.QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID_WITH_RANGE;
		}
		isAlreadyExist=isSeriesAlreadyReceiedWithRange(qryName,validationTO);
		return CGCollectionUtils.isEmpty(isAlreadyExist)?false:true;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#isSeriesAlreadyReceied(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesAlreadyReceied(String qryName,StockValidationTO validationTo)
			throws CGSystemException {
		List<Long> numberList=null;
		List<Long> invalidSeries=new ArrayList<Long>(validationTo.getLeafList().size());
		
		String params[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTo.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),1l,validationTo.getItemId()};
		if(!StringUtil.isEmptyLong(validationTo.getStockReceiptItemDetailsId())){
			String params1[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockReceiptConstants.QRY_PARAM_RECEIPT_DTLS_ID};
			Object value1[] = {validationTo.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),1l,validationTo.getItemId(),validationTo.getStockReceiptItemDetailsId()};
			params=params1;
			value=value1;
		}
		HibernateTemplate ht=getHibernateTemplate();
		for(Long leaf:validationTo.getLeafList()){
			value[3]=leaf;
			numberList=ht.findByNamedQueryAndNamedParam(qryName,params, value);
			if(!numberList.isEmpty()){
				invalidSeries.add(leaf);
				break;
			}

		}
		return invalidSeries;
	}
	public List<Long> isSeriesAlreadyReceiedWithRange(String qryName,StockValidationTO validationTo)
			throws CGSystemException {
		List<Long> numberList=null;

		String params[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {validationTo.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),validationTo.getLeafList().get(0),validationTo.getLeafList().get(validationTo.getLeafList().size()-1),validationTo.getItemId()};
		if(!StringUtil.isEmptyLong(validationTo.getStockReceiptItemDetailsId())){
			String params1[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockReceiptConstants.QRY_PARAM_RECEIPT_DTLS_ID};
			Object value1[] = {validationTo.getTransactionNumber(),StockCommonConstants.ACTIVE_STATUS,validationTo.getOfficeProduct(),validationTo.getLeafList().get(0),validationTo.getLeafList().get(validationTo.getLeafList().size()-1),validationTo.getItemId(),validationTo.getStockReceiptItemDetailsId()};
			params=params1;
			value=value1;
		}

		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(qryName,params, value);


		return numberList;
	}
	
	/**
	 * Prepare stock update to.
	 *
	 * @param domainEntity the domain entity
	 * @return the stock update input to
	 */
	private StockUpdateInputTO prepareStockUpdateTO(StockReceiptDO domainEntity) {
		StockUpdateInputTO stockUpdateTo;
		stockUpdateTo = new StockUpdateInputTO();
		stockUpdateTo.setPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		stockUpdateTo.setPartyTypeId(domainEntity.getReceiptOfficeId().getOfficeId());
		stockUpdateTo.setIsDecrease(false);
		return stockUpdateTo;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO#getIssueItemDtlIdWithIssueNumberForReceipt(com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO)
	 */
	@Override
	public	Long getIssueItemDtlIdWithIssueNumberForReceipt(StockReceiptItemDtlsDO detlEntity)throws CGSystemException{
		List<Long> numberList=null;

		String params[]={StockCommonConstants.QRY_PARAM_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {detlEntity.getStockReceiptDO().getIssueNumber(),StockCommonConstants.ACTIVE_STATUS,detlEntity.getOfficeProductCodeInSeries(),detlEntity.getStartLeaf(),detlEntity.getItemDO().getItemId()};

		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockReceiptConstants.QRY_GET_ISSUE_DTL_ID_FOR_RECEIPT,params, value);

		return CGCollectionUtils.isEmpty(numberList)?null :numberList.get(0);

	}
	
	/**
	 * Checks if is requisition number issued.
	 *
	 * @param reqNumber the req number
	 * @return true, if is requisition number issued
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public boolean isRequisitionNumberIssued(String reqNumber)throws CGSystemException{
		List<Long> numberList=null;
		String params[]={StockCommonConstants.QRY_PARAM_REQ_NUMBER};
		Object value[] = {reqNumber};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockReceiptConstants.QRY_IS_REQUISTION_ISSUED,params, value);
		return CGCollectionUtils.isEmpty(numberList)?false :true;
	}
	
	@Override
	public OfficeDO getOfficeDOById(Integer officeId){
		List<OfficeDO> numberList=null;
		String params[]={StockCommonConstants.QRY_PARAM_OFFICEID};
		Object value[] = {officeId};
		numberList=getHibernateTemplate().findByNamedQueryAndNamedParam("getOfficeNameByOfficeId",params, value);
		return !CGCollectionUtils.isEmpty(numberList)?numberList.get(0) :null;
	}
}
