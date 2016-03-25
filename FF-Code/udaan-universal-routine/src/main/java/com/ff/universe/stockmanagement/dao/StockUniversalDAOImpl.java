/**
 * 
 */
package com.ff.universe.stockmanagement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.coloading.CSDSAPColoaderInvoiceDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockBusinessAssociateMappingDO;
import com.ff.domain.stockmanagement.masters.StockCustomerMappingDO;
import com.ff.domain.stockmanagement.masters.StockEmployeeMappingDO;
import com.ff.domain.stockmanagement.masters.StockFranchiseeMappingDO;
import com.ff.domain.stockmanagement.masters.StockOfficeMappingDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnItemDtlsDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.to.stockmanagement.StockSearchInputTO;
import com.ff.to.stockmanagement.StockUpdateInputTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockUniversalDAOImpl.
 *
 * @author mohammes
 */
public class StockUniversalDAOImpl extends CGBaseDAO implements
		StockUniversalDAO {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(StockUniversalDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesCancelled(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Long isSeriesCancelled(StockValidationTO validationTO)throws CGSystemException{
		List<Long> cancelledList=null;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getOfficeProduct(),validationTO.getStartLeaf(),validationTO.getEndLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		cancelledList= getHibernateTemplate().findByNamedQueryAndNamedParam(StockUniveralConstants.QRY_SERIES_CANCELLED, paramNames, values);
		return !StringUtil.isEmptyColletion(cancelledList)?cancelledList.get(0):null;
	}
	@Override
	public Long isSeriesCancelledForIntegration(StockIssueValidationTO validationTo)throws CGSystemException{
		List<Long> cancelledList=null;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_START_SERIES_NO,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTo.getStockItemNumber(),StockUniveralConstants.TRANSACTION_STATUS};
		cancelledList= getHibernateTemplate().findByNamedQueryAndNamedParam(StockUniveralConstants.QRY_SERIES_CANCELLED_FOR_INTEGRATION, paramNames, values);
		return !StringUtil.isEmptyColletion(cancelledList)?cancelledList.get(0):null;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesIssuedToPartyType(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesIssuedToPartyType(String qryName,
			StockValidationTO validationTO) throws CGSystemException {
		List<Long> issueList=null;
		List<Long> issuedList=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
		issueList= ht.findByNamedQueryAndNamedParam(qryName, paramNames, values);
		if(issueList.isEmpty()){
			issuedList.add(leaf);
			//break;
		}
		}
		return issuedList;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesExistAtTransferredTOPartyType(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesExistAtTransferredTOPartyType(String qryName,StockValidationTO validationTO) throws CGSystemException {
		List<Long> transferrredList=null;
		List<Long> notTransfrd=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			transferrredList= ht.findByNamedQueryAndNamedParam(qryName, paramNames, values);
			if(transferrredList.isEmpty()){
				notTransfrd.add(leaf);
				//break;
			}
		}
		return notTransfrd;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesTransferredFromPartyType(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesTransferredFromPartyType(String qryName,StockValidationTO validationTO) throws CGSystemException {
		List<Long> transferrredList=null;
		List<Long> transffred=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			transferrredList= ht.findByNamedQueryAndNamedParam(qryName, paramNames, values);
			if(!transferrredList.isEmpty()){
				transffred.add(leaf);
				//break;
			}
		}
		return transffred;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesAvailableWithTransferFromPartyType(java.lang.String, com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesAvailableWithTransferFromPartyType(String qryName,StockValidationTO validationTO) throws CGSystemException {
		List<Long> transferrredList=null;
		List<Long> notExist=new ArrayList<>(1);
		HibernateTemplate ht=getHibernateTemplate();
		//FIXME need to modify
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			transferrredList= ht.findByNamedQueryAndNamedParam(qryName, paramNames, values);
			//if it's empty then latest series does not exist with this party
			if(transferrredList.isEmpty()){
				notExist.add(leaf);
				break;
			}
		}
		return notExist;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesReturned(com.ff.to.stockmanagement.stockrequisition.StockValidationTO, java.lang.String)
	 */
	@Override
	public Long isSeriesReturned(StockValidationTO validationTO,String returnType) throws CGSystemException {
		Session session=null;
		List<Long> result=null;
		Criteria criteria=null;
		try {
			session=createSession();
			criteria = session.createCriteria(StockReturnItemDtlsDO.class,"returnDtls");
			criteria.add(Restrictions.eq("returnDtls.officeProductCodeInSeries", validationTO.getOfficeProduct()));
			
			if(!StringUtil.isStringEmpty(returnType)){
				if(returnType.equalsIgnoreCase(StockUniveralConstants.RETURN_TYPE_RECEIPT)){
					criteria.add(Restrictions.conjunction()
							.add(Restrictions.le("returnDtls.startLeaf", validationTO.getStartLeaf()))
							.add(Restrictions.ge("returnDtls.endLeaf", validationTO.getEndLeaf())));
				}else if(returnType.equalsIgnoreCase(StockUniveralConstants.RETURN_TYPE_RETURN)){
					criteria.add(Restrictions.disjunction()
							.add(Restrictions.le("returnDtls.startLeaf", validationTO.getStartLeaf()))
							.add(Restrictions.ge("returnDtls.endLeaf", validationTO.getEndLeaf())));
				}
			}
			
			criteria.add(Restrictions.eq("returnDtls.officeProductCodeInSeries", validationTO.getOfficeProduct()));
			prepareGoodsReturnHeaderSubQry(validationTO, returnType, criteria);
			prepareSubQryForReturn(validationTO, criteria);
			criteria.setProjection(Projections.property("returnDtls.stockReturnItemDtlsId"));
			result= criteria.list();
			
			
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::isSeriesReturned :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return !StringUtil.isEmptyColletion(result)?result.get(0):null;
	}

/**
 * Prepare sub qry for return.
 *
 * @param validationTO the validation to
 * @param criteria the criteria
 */
	private void prepareSubQryForReturn(StockValidationTO validationTO,
			Criteria criteria) {
		DetachedCriteria  subQuery=DetachedCriteria.forClass(StockReturnItemDtlsDO.class,"subQry");
		prepareSubQrySeriesDisjunction(validationTO, subQuery);
		criteria.add(Property.forName("returnDtls.transactionModifiedDate").in(subQuery));
	}
	
	/**
	 * Prepare goods return header sub qry.
	 *
	 * @param validationTO the validation to
	 * @param returnType the return type
	 * @param criteria the criteria
	 */

	private void prepareGoodsReturnHeaderSubQry(StockValidationTO validationTO,
			String returnType, Criteria criteria) {
		DetachedCriteria  subQuery=DetachedCriteria.forClass(StockReturnDO.class,"header");
		if(!StringUtil.isStringEmpty(returnType)){
			if(returnType.equalsIgnoreCase(StockUniveralConstants.RETURN_TYPE_RECEIPT)){
				subQuery.add(Restrictions.eq("header.issuedOfficeDO.officeId", validationTO.getPartyTypeId()));
			}else if(returnType.equalsIgnoreCase(StockUniveralConstants.RETURN_TYPE_RETURN)){
				subQuery.add(Restrictions.eq("header.returningOfficeDO.officeId", validationTO.getPartyTypeId()));
			}
		}
		subQuery.setProjection(Projections.property("header.stockReturnId"));
		criteria.add(Property.forName("returnDtls.returnDo.stockReturnId").in(subQuery));
	}

	
/**
 * Prepare sub qry series disjunction.
 *
 * @param validationTO the validation to
 * @param subQuery purpose : common Reusable method for SubQury
 */
	private void prepareSubQrySeriesDisjunction(StockValidationTO validationTO,
			DetachedCriteria subQuery) {
		subQuery.add(Restrictions.eq("subQry.officeProductCodeInSeries", validationTO.getOfficeProduct()));
		subQuery.add(Restrictions.eq("subQry.itemDO.itemId", validationTO.getItemId()));
		subQuery.add(Restrictions.disjunction()
				.add(Restrictions.le("subQry.startLeaf", validationTO.getStartLeaf()))
				.add(Restrictions.ge("subQry.endLeaf", validationTO.getEndLeaf())));
		subQuery.add(Restrictions.eq("subQry.transactionStatus", StockUniveralConstants.TRANSACTION_STATUS));
		subQuery.setProjection(Projections.max("subQry.transactionModifiedDate"));
		subQuery.addOrder(Order.desc("subQry.transactionModifiedDate"));
		
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesAcknowledged(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Long isSeriesAcknowledged(StockValidationTO validationTO) throws CGSystemException {
		Session session=null;
		List<Long> result=null;
		Criteria criteria=null;
		try {
			session=createSession();
			criteria = session.createCriteria(StockReceiptItemDtlsDO.class,"receiptDtls");
			criteria.add(Restrictions.eq("receiptDtls.itemDO.itemId", validationTO.getItemId()));
			criteria.add(Restrictions.eq("receiptDtls.officeProductCodeInSeries", validationTO.getOfficeProduct()));
			criteria.add(Restrictions.conjunction()
					.add(Restrictions.le("receiptDtls.startLeaf", validationTO.getStartLeaf()))
					.add(Restrictions.ge("receiptDtls.endLeaf", validationTO.getEndLeaf())));
			DetachedCriteria  headerQuery=DetachedCriteria.forClass(StockReceiptDO.class,"headerQry");
			headerQuery.add(Restrictions.eq("headerQry.receiptOfficeId.officeId",validationTO.getPartyTypeId()));
			headerQuery.setProjection(Projections.property("headerQry.stockReceiptId"));
			criteria.add(Property.forName("receiptDtls.stockReceiptDO.stockReceiptId").in(headerQuery));
			prepareSubQryForReceipt(validationTO, criteria);
			criteria.add(Restrictions.eq("receiptDtls.transactionStatus", StockUniveralConstants.TRANSACTION_STATUS));
			criteria.setProjection(Projections.property("receiptDtls.stockReceiptItemDtlsId"));
			result= criteria.list();
			
			
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::isSeriesAcknowledged :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return !StringUtil.isEmptyColletion(result)?result.get(0):null;
	}

	/**
	 * Prepare sub qry for receipt.
	 *
	 * @param validationTO the validation to
	 * @param criteria the criteria
	 */
	private void prepareSubQryForReceipt(StockValidationTO validationTO,
			Criteria criteria) {
		DetachedCriteria  subQuery=DetachedCriteria.forClass(StockReceiptItemDtlsDO.class,"subQry");
		prepareSubQrySeriesDisjunction(validationTO, subQuery);
		criteria.add(Property.forName("receiptDtls.transactionModifiedDate").in(subQuery));
	}
	
	/**
	 * Gets the latest series date.
	 *
	 * @param qryName the qry name
	 * @param paramValuePair the param value pair
	 * @return DATE
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Date getLatestSeriesDate(String qryName,Map paramValuePair) throws CGSystemException {
		Session session=createSession();
		List<Date> transactionDate=null;
		try {
			Query qry = session.getNamedQuery(qryName);
			qry.setProperties(paramValuePair);
			
			qry.setMaxResults(1);
			transactionDate = qry.list();
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestSeriesDate :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		return transactionDate!=null && !transactionDate.isEmpty()? transactionDate.get(0):null;
	}
	
	/**
	 * *
	 * * @param qryName.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the latest date with series
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Map<Long,Date> getLatestDateWithSeries(String qryName,StockValidationTO validationTO) throws CGSystemException {
		Session session=createSession();
		List<Date> transactionDate=null;
		Map<Long,Date> dateResult=new HashMap<>(validationTO.getLeafList().size());
		try {
			Query qry = session.getNamedQuery(qryName);
			//qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, validationTO.getPartyTypeId());
			String qString=qry.getQueryString();
			if(!StringUtil.isStringEmpty(qString)&& qString.contains(StockUniveralConstants.QRY_PARAM_TYPE_ID)){
				qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, validationTO.getPartyTypeId());
			}
			qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, validationTO.getItemId());
			qry.setString(StockUniveralConstants.QRY_PARAM_OFFICE_CODE, validationTO.getOfficeProduct());
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS, StockUniveralConstants.TRANSACTION_STATUS);
			//qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, validationTO.getLeaf());
			qry.setMaxResults(1);
			for(Long leaf:validationTO.getLeafList()){
				qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, leaf);
				transactionDate = qry.list();
				if(transactionDate.isEmpty()){
					continue;
				}
				dateResult.put(leaf, !transactionDate.isEmpty()?transactionDate.get(0):null);
			}
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestSeriesDate :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		return dateResult;
	}
	@Override
	public Date getLatestDateByLeaf(String qryName,StockValidationTO validationTO) throws CGSystemException {
		Session session=getCreatedSession();
		List<Date> transactionDate=null;
		try {
			Query qry = session.getNamedQuery(qryName);
			//qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, validationTO.getPartyTypeId());
			String qString=qry.getQueryString();
			if(!StringUtil.isStringEmpty(qString)&& qString.contains(StockUniveralConstants.QRY_PARAM_TYPE_ID)){
				qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, validationTO.getPartyTypeId());
			}
			qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, validationTO.getItemId());
			qry.setString(StockUniveralConstants.QRY_PARAM_OFFICE_CODE, validationTO.getOfficeProduct());
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS, StockUniveralConstants.TRANSACTION_STATUS);
			qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, validationTO.getLeaf());
			qry.setMaxResults(1);
			transactionDate = qry.list();
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestDateWithSeriesLeaf :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeTransactionalSession(session);
		}
		return !CGCollectionUtils.isEmpty(transactionDate)?transactionDate.get(0):null;
	}
	@Override
	public Map<Long,Date> getLatestDateWithSeriesWithoutOffice(String qryName,StockValidationTO validationTO) throws CGSystemException {
		Session session=createSession();
		List<Date> transactionDate=null;
		Map<Long,Date> dateResult=new HashMap<>(validationTO.getLeafList().size());
		try {
			Query qry = session.getNamedQuery(qryName);
			//qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, validationTO.getPartyTypeId());
			
			qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, validationTO.getItemId());
			qry.setString(StockUniveralConstants.QRY_PARAM_OFFICE_CODE, validationTO.getOfficeProduct());
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS, StockUniveralConstants.TRANSACTION_STATUS);
			//qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, validationTO.getLeaf());
			qry.setMaxResults(1);
			for(Long leaf:validationTO.getLeafList()){
				qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, leaf);
				transactionDate = qry.list();
				if(transactionDate.isEmpty()){
					continue;
				}
				dateResult.put(leaf, !transactionDate.isEmpty()?transactionDate.get(0):null);
			}
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestSeriesDate :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		return dateResult;
	}

	@Override
	public Date getLatestDateByleafWithoutOffice(String qryName,StockValidationTO validationTO) throws CGSystemException {
		Session session=getCreatedSession();
		List<Date> transactionDate=null;
		try {
			Query qry = session.getNamedQuery(qryName);
			qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, validationTO.getItemId());
			qry.setString(StockUniveralConstants.QRY_PARAM_OFFICE_CODE, validationTO.getOfficeProduct());
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS, StockUniveralConstants.TRANSACTION_STATUS);
			qry.setLong(StockUniveralConstants.QRY_PARAM_LEAF, validationTO.getLeaf());
			qry.setMaxResults(1);
			transactionDate = qry.list();
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestDateByleafWithoutOffice :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeTransactionalSession(session);
		}
		return !CGCollectionUtils.isEmpty(transactionDate)?transactionDate.get(0):null;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesAlreadyIssuedFromBranch(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesAlreadyIssuedFromBranch(StockValidationTO validationTO)
			throws CGSystemException {
		List<Long> result=null;
		List<Long> issuedList=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String Qry=StockUniveralConstants.QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		
		if(!StringUtil.isEmptyLong(validationTO.getItemDetailsId())){
		 Qry=StockUniveralConstants.QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH_EXCLUDE_ROW;
		 String paramNames1[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_DETAILS_ID,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		 Object values1[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),validationTO.getItemDetailsId(),StockUniveralConstants.TRANSACTION_STATUS};
		 paramNames=paramNames1;
		 values=values1;
		}
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			result= ht.findByNamedQueryAndNamedParam(Qry, paramNames, values);
			if(!result.isEmpty()){
				issuedList.add(leaf);
			}
		}
		return issuedList;
	}
	
	@Override
	public Boolean isSeriesAlreadyIssuedFromBranchByLeaf(StockValidationTO validationTO)
			throws CGSystemException {
		List<Long> result=null;
		HibernateTemplate ht=getHibernateTemplate();
		String Qry=StockUniveralConstants.QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		
		if(!StringUtil.isEmptyLong(validationTO.getItemDetailsId())){
		 Qry=StockUniveralConstants.QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH_EXCLUDE_ROW;
		 String paramNames1[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_ITEM_DETAILS_ID,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		 Object values1[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getLeaf(),validationTO.getItemDetailsId(),StockUniveralConstants.TRANSACTION_STATUS};
		 paramNames=paramNames1;
		 values=values1;
		}
		
		result= ht.findByNamedQueryAndNamedParam(Qry, paramNames, values);
		
		return !CGCollectionUtils.isEmpty(result)&& !StringUtil.isEmptyLong(result.get(0))?true:false;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#getStockQuantityByItemAndPartyType(com.ff.to.stockmanagement.StockSearchInputTO)
	 */
	@Override
	public Integer getStockQuantityByItemAndPartyType(StockSearchInputTO searchTO)
			throws CGSystemException {
		Session session=null;
		List<Integer> result=null;
		Criteria criteria=null;
		try {
			logger.trace("StockUniversalDAOImpl :: getStockQuantityByItemAndPartyType :: START");
			session=createSession();
			criteria= prepareCriteria4Stock(searchTO, session);
			if(!StringUtil.isStringEmpty(searchTO.getPartyType())&&searchTO.getPartyType().equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BRANCH) ){
			criteria.add(Restrictions.eq("stock.itemDO.itemId", searchTO.getItemId()));
			}else{
				criteria.add(Restrictions.eq("stock.itemId", searchTO.getItemId()));
			}
			criteria.add(Restrictions.eq("stock.transactionStatus", StockUniveralConstants.TRANSACTION_STATUS));
			criteria.setProjection(Projections.property("stock.quantity"));
			result= criteria.list();
			logger.trace("StockUniversalDAOImpl :: getStockQuantityByItemAndPartyType :: END");
		}catch(Exception e){
			logger.trace("StockUniversalDAOImpl :: getStockQuantityByItemAndPartyType :: Exception::",e);
			throw new CGSystemException(e.getMessage(), e);
			
		}
		
		finally{
			closeSession(session);
		}
		logger.info("StockUniversalDAOImpl :: getStockQuantityByItemAndPartyType :: RESULTs :"+result);
		return !StringUtil.isEmptyColletion(result)?result.get(0):null;
	}

	/**
	 * Prepare criteria4 stock.
	 *
	 * @param searchTO the search to
	 * @param session the session
	 * @return the criteria
	 */
	private Criteria prepareCriteria4Stock(StockSearchInputTO searchTO,
			Session session) {
		Criteria criteria=null;
		if(!StringUtil.isStringEmpty(searchTO.getPartyType())){
			switch(searchTO.getPartyType()){
			case UdaanCommonConstants.ISSUED_TO_BA:
				criteria = session.createCriteria(StockBusinessAssociateMappingDO.class,"stock");
				//criteria.add(Restrictions.eq("stock.businessAssociateDO.baId", searchTO.getPartyTypeId()));
				criteria.add(Restrictions.eq("stock.customerId", searchTO.getPartyTypeId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_FR:
				criteria = session.createCriteria(StockFranchiseeMappingDO.class,"stock");
				//criteria.add(Restrictions.eq("stock.franchiseeDO.franchiseeId", searchTO.getPartyTypeId()));
				criteria.add(Restrictions.eq("stock.customerId", searchTO.getPartyTypeId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				criteria = session.createCriteria(StockOfficeMappingDO.class,"stock");
				//criteria.add(Restrictions.eq("stock.officeDO.officeId", searchTO.getPartyTypeId()));
				criteria.add(Restrictions.eq("stock.officeDO.officeId", searchTO.getPartyTypeId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				criteria = session.createCriteria(StockEmployeeMappingDO.class,"stock");
				//criteria.add(Restrictions.eq("stock.employeeDO.employeeId", searchTO.getPartyTypeId()));
				criteria.add(Restrictions.eq("stock.employeeId", searchTO.getPartyTypeId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				criteria = session.createCriteria(StockCustomerMappingDO.class,"stock");
			//	criteria.add(Restrictions.eq("stock.customerDO.customerId", searchTO.getPartyTypeId()));
				criteria.add(Restrictions.eq("stock.customerId", searchTO.getPartyTypeId()));
				break;

			}
		}
		return criteria;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#updateStockByPartyType(com.ff.to.stockmanagement.StockUpdateInputTO)
	 */
	@Override
	public Integer updateStockByPartyType(StockUpdateInputTO searchTO)
			throws CGSystemException {
		Session session=null;
		Integer result=null;
		try {
			session=createSession();
			StockUtility.updateUniversalStock(session, searchTO);
		}catch (Exception e) {
			logger.error("StockUniversalDAOImpl::updateStockByPartyType :: Exception",e);
			throw new CGSystemException(e.getMessage(),e);
		}finally{
			closeSession(session);
		}
		
		return result;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#getAllItemsByType(com.ff.domain.stockmanagement.masters.ItemDO)
	 */
	@Override
	public List<ItemDO> getAllItemsByType(ItemDO itemDO) throws CGSystemException {
		Session session=null;
		List<ItemDO> result=null;
		Criteria criteria=null;
		try {
			session=createSession();
			criteria = session.createCriteria(ItemDO.class,"itemDO");
			if(!StringUtil.isEmptyInteger(itemDO.getItemId())){
			criteria.add(Restrictions.eq("itemDO.itemId", itemDO.getItemId()));
			}
			
			criteria.add(Restrictions.isNotNull("itemDO.itemCode"));
			criteria.add(Restrictions.isNotNull("itemDO.itemName"));
			
			if(!StringUtil.isStringEmpty(itemDO.getItemCode())){
				criteria.add(Restrictions.eq("itemDO.itemCode", itemDO.getItemCode()));
				}
			if(!StringUtil.isStringEmpty(itemDO.getItemSeries())){
				criteria.add(Restrictions.eq("itemDO.itemSeries", itemDO.getItemSeries()));
				}
			if(itemDO.getIsSeriesVerifier()!=null && itemDO.getIsSeriesVerifier()){
				if(StringUtil.isStringEmpty(itemDO.getItemSeries())){
					criteria.add(Restrictions.isNull("itemDO.itemSeries"));
					}
			}
			
			if(itemDO.getItemTypeDO()!=null){
				DetachedCriteria  headerQuery=DetachedCriteria.forClass(ItemTypeDO.class,"itemTypeDO");
				if(!StringUtil.isEmptyInteger(itemDO.getItemTypeDO().getItemTypeId())){
					headerQuery.add(Restrictions.eq("itemTypeDO.itemTypeId",itemDO.getItemTypeDO().getItemTypeId()));
				}
				if(!StringUtil.isStringEmpty(itemDO.getItemTypeDO().getItemTypeCode())){
					headerQuery.add(Restrictions.eq("itemTypeDO.itemTypeCode",itemDO.getItemTypeDO().getItemTypeCode()));
				}
				if(!StringUtil.isStringEmpty(itemDO.getItemTypeDO().getItemHasSeries())){
					headerQuery.add(Restrictions.eq("itemTypeDO.itemHasSeries",itemDO.getItemTypeDO().getItemHasSeries()));
				}
				
				headerQuery.add(Restrictions.eq("itemTypeDO.isActive",StockUniveralConstants.TRANSACTION_STATUS));
				headerQuery.setProjection(Projections.property("itemTypeDO.itemTypeId"));
				criteria.add(Property.forName("itemDO.itemTypeDO.itemTypeId").in(headerQuery));
			}
			criteria.add(Restrictions.eq("itemDO.isActive", StockUniveralConstants.TRANSACTION_STATUS));
			result= criteria.list();
			
			
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getAllItemsByType :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#getStockIssuedDtls(com.ff.to.stockmanagement.StockIssueValidationTO)
	 */
	@Override
	public StockIssueDO getStockIssuedDtls(StockIssueValidationTO  issueTO) throws CGSystemException {
		Session session=null;
		List<StockIssueDO> result=null;
		Criteria criteria=null;
		try {
			session=createSession();
			criteria = session.createCriteria(StockIssueItemDtlsDO.class,"issueDtls");
			if(!StringUtil.isEmptyInteger(issueTO.getItemId())){
				criteria.add(Restrictions.eq("issueDtls.itemDO.itemId", issueTO.getItemId()));
			}
			criteria.add(Restrictions.eq("issueDtls.officeProductCodeInSeries", issueTO.getOfficeProductCode()));
			criteria.add(Restrictions.conjunction()
					.add(Restrictions.le("issueDtls.startLeaf",issueTO.getLeaf()))
					.add(Restrictions.ge("issueDtls.endLeaf", issueTO.getLeaf())));
			DetachedCriteria headerQuery = prepareHeaderQryForIssue(issueTO);
			criteria.add(Property.forName("issueDtls.stockIssueDO.stockIssueId").in(headerQuery));
			prepareSubQryForIssue(issueTO,criteria);
			//criteria.setProjection(Projections.property("issueDtls.stockIssueDO"));
			Criteria tbl1Criteria = criteria.createCriteria("issueDtls.stockIssueDO","issueDO");
			tbl1Criteria.setProjection(Projections.projectionList()
					.add(Projections.alias(Projections.property("issueDO.stockIssueDate"), "stockIssueDate"))
					.add(Projections.alias(Projections.property("issueDO.issueOfficeDO"), "issueOfficeDO"))
					.add(Projections.alias(Projections.property("issueDO.issuedToType"), "issuedToType"))
					.add(Projections.alias(Projections.property("issueDO.issuedToOffice"), "issuedToOffice"))
					.add(Projections.alias(Projections.property("issueDO.issuedToBA"), "issuedToBA"))
					.add(Projections.alias(Projections.property("issueDO.issuedToFranchisee"), "issuedToFranchisee"))
					.add(Projections.alias(Projections.property("issueDO.issuedToPickupBoy"), "issuedToPickupBoy"))
					.add(Projections.alias(Projections.property("issueDO.issuedToCustomer"), "issuedToCustomer"))
					.add(Projections.alias(Projections.property("issueDO.shippedToCode"), "shippedToCode"))
					.add(Projections.alias(Projections.property("issueDO.stockIssueNumber"), "stockIssueNumber"))
					.add(Projections.alias(Projections.property("issueDO.transactionCreateDate"), "transactionCreateDate"))
					.add(Projections.alias(Projections.property("issueDO.transactionModifiedDate"), "transactionModifiedDate"))
					);
			criteria.setResultTransformer(Transformers.aliasToBean(StockIssueDO.class));
			result= criteria.list();
			
			
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getStockIssuedDtls :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return !StringUtil.isEmptyColletion(result)?result.get(0):null;
	}

	/**
	 * Prepare header qry for issue.
	 *
	 * @param issueTO the issue to
	 * @return the detached criteria
	 */
	private DetachedCriteria prepareHeaderQryForIssue(
			StockIssueValidationTO issueTO) {
		DetachedCriteria  headerQuery=DetachedCriteria.forClass(StockIssueDO.class,"headerQry");
		
		headerQuery.add(Restrictions.ne("headerQry.issuedToType",CommonConstants.EMPTY_STRING));
		headerQuery.add(Restrictions.isNotNull("headerQry.issuedToType"));
		headerQuery.add(Restrictions.in("headerQry.issuedToType",new Object[]{UdaanCommonConstants.ISSUED_TO_BA,UdaanCommonConstants.ISSUED_TO_BRANCH,UdaanCommonConstants.ISSUED_TO_CUSTOMER,UdaanCommonConstants.ISSUED_TO_EMPLOYEE,UdaanCommonConstants.ISSUED_TO_FR}));
		if(!StringUtil.isStringEmpty(issueTO.getIssuedTOPartyType())){
			headerQuery.add(Restrictions.eq("headerQry.issuedToType", issueTO.getIssuedTOPartyType()));
			}
		if(!StringUtil.isEmptyInteger(issueTO.getIssuingOfficeId())){
			headerQuery.add(Restrictions.eq("headerQry.issueOfficeDO.officeId", issueTO.getIssuingOfficeId()));
		}
		if(!StringUtil.isEmptyInteger(issueTO.getIssuedTOPartyId())){
			switch(issueTO.getIssuedTOPartyType()){
			case UdaanCommonConstants.ISSUED_TO_BA:

				headerQuery.add(Restrictions.eq("headerQry.issuedToBA.customerId", issueTO.getIssuedTOPartyId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_FR:
				headerQuery.add(Restrictions.eq("headerQry.issuedToFranchisee.customerId", issueTO.getIssuedTOPartyId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				headerQuery.add(Restrictions.eq("headerQry.issuedToOffice.officeId", issueTO.getIssuedTOPartyId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				headerQuery.add(Restrictions.eq("headerQry.issuedToPickupBoy.employeeId", issueTO.getIssuedTOPartyId()));
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				headerQuery.add(Restrictions.eq("headerQry.issuedToCustomer.customerId", issueTO.getIssuedTOPartyId()));
				break;

			}
		}
		headerQuery.setProjection(Projections.property("headerQry.stockIssueId"));
		return headerQuery;
	}
	
	/**
	 * Prepare sub qry for issue.
	 *
	 * @param validationTO the validation to
	 * @param criteria the criteria
	 */
	private void prepareSubQryForIssue(StockIssueValidationTO validationTO,
			Criteria criteria) {
		DetachedCriteria  subQuery=DetachedCriteria.forClass(StockIssueItemDtlsDO.class,"subQry");
		if(!StringUtil.isEmptyInteger(validationTO.getItemId())){
			subQuery.add(Restrictions.eq("subQry.itemDO.itemId", validationTO.getItemId()));
		}
		subQuery.add(Restrictions.eq("subQry.officeProductCodeInSeries", validationTO.getOfficeProductCode()));
		subQuery.add(Restrictions.conjunction()
				.add(Restrictions.le("subQry.startLeaf", validationTO.getLeaf()))
				.add(Restrictions.ge("subQry.endLeaf", validationTO.getLeaf())));
		subQuery.add(Restrictions.eq("subQry.transactionStatus", StockUniveralConstants.TRANSACTION_STATUS));
		subQuery.setProjection(Projections.max("subQry.transactionModifiedDate"));
		subQuery.addOrder(Order.desc("subQry.transactionModifiedDate"));
		criteria.add(Property.forName("issueDtls.transactionModifiedDate").in(subQuery));
	}
	
	
	/* (non-Javadoc)
	 * @see com.ff.universe.stockmanagement.dao.StockUniversalDAO#isSeriesExistInBranch(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public List<Long> isSeriesExistInBranch(StockValidationTO validationTO)
			throws CGSystemException {
		List<Long> result=null;
		List<Long> invalidSeries=new ArrayList<>();
		HibernateTemplate ht=getHibernateTemplate();
		String qry=StockUniveralConstants.QRY_IS_SERIES_EXIST_IN_OFFICE;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getStartLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		for(Long leaf:validationTO.getLeafList()){
			values[3]=leaf;
			result= ht.findByNamedQueryAndNamedParam(qry, paramNames, values);
			if(result.isEmpty()){
				invalidSeries.add(leaf);
				break;
			}
		}
		return invalidSeries;
	}
	/**
	 * isSeriesExistInBranchByLeaf: 
	 * @param validationTO
	 * @return
	 * @throws CGSystemException
	 */
	@Override
	public Long isSeriesExistInBranchByLeaf(StockValidationTO validationTO)
			throws CGSystemException {
		List<Long> result=null;
		HibernateTemplate ht=getHibernateTemplate();
		String qry=StockUniveralConstants.QRY_IS_SERIES_EXIST_IN_OFFICE;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getPartyTypeId(),validationTO.getItemId(),validationTO.getOfficeProduct(),validationTO.getLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
		result= ht.findByNamedQueryAndNamedParam(qry, paramNames, values);
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}
	
	/**
	 * Gets the stock receipt details for branch.
	 *it's For Integration purpose
	 * @param validationTO the validation to
	 * @return the stock receipt details for branch
	 */
	@Override
	public Long getStockReceiptDetailsForBranch(StockIssueValidationTO validationTO){
		List<Long> result;
		HibernateTemplate ht=getHibernateTemplate();
		String qry=StockUniveralConstants.QRY_SERIES_EXIST_IN_OFFICE_FOR_INTEGRATION;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getIssuedTOPartyId(),validationTO.getItemId(),validationTO.getOfficeProductCode(),validationTO.getLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
			result= ht.findByNamedQueryAndNamedParam(qry, paramNames, values);
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}
	
	/**
	 * Gets the stock receipt details under region.
	 *
	 * @param validationTO the validation to
	 * @return the stock receipt details under region
	 */
	@Override
	public Long getStockReceiptDetailsUnderRegion(StockIssueValidationTO validationTO){
		List<Long> result;
		HibernateTemplate ht=getHibernateTemplate();
		String qry=StockUniveralConstants.QRY_SERIES_EXIST_IN_REGION_FOR_INTEGRATION;
		String paramNames[]={StockUniveralConstants.QRY_PARAM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID,StockUniveralConstants.QRY_PARAM_OFFICE_CODE,StockUniveralConstants.QRY_PARAM_LEAF,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={validationTO.getIssuedTOPartyId(),validationTO.getItemId(),validationTO.getOfficeProductCode(),validationTO.getLeaf(),StockUniveralConstants.TRANSACTION_STATUS};
			result= ht.findByNamedQueryAndNamedParam(qry, paramNames, values);
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}
	
	/**
	 * Gets the stock item dtls for stock series. For Stock And other module integration
	 *
	 * @param qry the qry
	 * @param stockSeries the stock series
	 * @return the stock item dtls for stock series
	 */
	@Override
	public ItemDO getStockItemDtlsForStockSeries(String qry,String stockSeries){
		List<ItemDO> result;
		HibernateTemplate ht=getHibernateTemplate();
		String paramNames[]={StockUniveralConstants.QRY_PARAM_START_SERIES_NO,StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS};
		Object values[]={stockSeries,StockUniveralConstants.TRANSACTION_STATUS};
			result= ht.findByNamedQueryAndNamedParam(qry, paramNames, values);
		return !CGCollectionUtils.isEmpty(result)?result.get(0):null;
	}
	
	@SuppressWarnings("unchecked")
	public CSDSAPItemDO getItemDtlsByCode(String itemCode) throws CGSystemException{
		List<CSDSAPItemDO> itemDOList = null;
		CSDSAPItemDO itemDO = null;
		try{
			String queryName = "getItemByCode";
			itemDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "itemCode", itemCode);
			if(!StringUtil.isEmptyColletion(itemDOList)){
				itemDO = itemDOList.get(0);
			}
		}catch(Exception e){
			logger.debug("StockUniversalDAOImpl :: getItemTypeDtlsByCode :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e); 
		}
		return itemDO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemTypeDO getItemTypeByCode(String itemTypeCode)
			throws CGSystemException {
		List<ItemTypeDO> itemTypeList = null;
		ItemTypeDO itemTypeDO = null;
		try{
			String queryName = "getItemTypeByCode";
			itemTypeList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "itemTypeCode", itemTypeCode);
			if(!StringUtil.isEmptyColletion(itemTypeList)){
				itemTypeDO = itemTypeList.get(0);
			}
		}catch(Exception e){
			logger.debug("StockUniversalDAOImpl :: getItemTypeDtlsByCode :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e); 
		}
		return itemTypeDO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date getLatestTransferDateBySerialNumber(String qryName,String serialNumber) throws CGSystemException{
		Session session=createSession();
		List<Date> transactionDate=null;
		try {
			Query qry = session.getNamedQuery(qryName);
			qry.setString(StockUniveralConstants.QRY_PARAM_START_SERIES_NO,serialNumber);
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.TRANSACTION_STATUS);
			
			qry.setMaxResults(1);
			transactionDate = qry.list();
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getLatestSeriesDate :: Exception"+e.getLocalizedMessage());
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		return transactionDate!=null && !transactionDate.isEmpty()? transactionDate.get(0):null;
	}
	
	@Override
	public List<?> getItemTypeAsMap() throws CGSystemException {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockUniveralConstants.QRY_GET_ITEM_TYP_AS_MAP, StockUniveralConstants.QRY_PARAM_IS_ACTIVE, StockUniveralConstants.ACTIVE_STATUS);
	}
	@Override
	public List<StockCustomerWrapperDO> getContractCustomerListForStock(CustomerDO customerDO) throws CGSystemException {
		String queryName=null;
		Session session=createSession();
		List<StockCustomerWrapperDO> customerShippedMap=null;
		/*List<StockCustomerWrapperDO> rlCustomerShippedMap=null;*/
		Object[] values=null;
		if(!StringUtil.isStringEmpty(customerDO.getCustomerType().getCustomerTypeCode())&& customerDO.getCustomerType().getCustomerTypeCode().contains(FrameworkConstants.CHARACTER_COMMA)){
			values=customerDO.getCustomerType().getCustomerTypeCode().split(FrameworkConstants.CHARACTER_COMMA);
		}else{
			values= new Object[]{customerDO.getCustomerType().getCustomerTypeCode()};
		}
		if(!StringUtil.isNull(customerDO.getOfficeMappedDO())){
			if(!StringUtil.isEmptyInteger(customerDO.getOfficeMappedDO().getReportingRHO())){
				queryName=StockUniveralConstants.QRY_STOCK_CONTRACT_CUSTOMERS_FOR_RHO;
			}else if (!StringUtil.isEmptyInteger(customerDO.getOfficeMappedDO().getReportingHUB())){
				queryName=StockUniveralConstants.QRY_STOCK_CONTRACT_CUSTOMERS_FOR_HUB;
			}else{
				queryName=StockUniveralConstants.QRY_STOCK_CONTRACT_CUSTOMERS;
			}
			
		}
		
		try {
			Query qry = session.getNamedQuery(queryName);
			qry.setParameterList(StockUniveralConstants.QRY_PARAM_CUSTOMER_TYPE,values);
			qry.setInteger(StockUniveralConstants.QRY_PARAM_OFFICE_ID,customerDO.getOfficeMappedDO().getOfficeId());
			qry.setString(StockUniveralConstants.QRY_PARAM_TRANSACTION_STATUS,StockUniveralConstants.TRANSACTION_STATUS);
			qry.setString(StockUniveralConstants.QRY_PARAM_PICK_UP_TYPE,CommonConstants.PICKUP_TYPE_PICK_UP);
			customerShippedMap = qry.list();
			/*if(Arrays.asList(values).contains(CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS)){
				qry.setParameterList(StockUniveralConstants.QRY_PARAM_CUSTOMER_TYPE,new Object[]{CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS});
				qry.setString(StockUniveralConstants.QRY_PARAM_PICK_UP_TYPE,CommonConstants.PICKUP_TYPE_DELIVERY);
				rlCustomerShippedMap= qry.list();
			}*/
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getContractCustomerListForStock :: Exception",e);
		throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}
		/*if(CGCollectionUtils.isEmpty(customerShippedMap)){
			customerShippedMap=rlCustomerShippedMap;
		}else if(!CGCollectionUtils.isEmpty(rlCustomerShippedMap)){
			customerShippedMap.addAll(rlCustomerShippedMap);
		}*/
		return customerShippedMap;
	}
	
	
	
	@Override
	public List<?> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException {
		String params[] = {StockUniveralConstants.QRY_PARAM_IS_ACTIVE, StockUniveralConstants.QRY_PARAM_ITEM_TYPE_ID};
		Object value[] = {StockUniveralConstants.ACTIVE_STATUS, itemTypeId};
		return getHibernateTemplate().findByNamedQueryAndNamedParam(StockUniveralConstants.QRY_GET_MATERIAL_BY_TYPE_AS_MAP, params, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ItemDO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId) 
			throws CGSystemException {
		List<ItemDO> itemList=null;
		String params[]={StockUniveralConstants.QRY_PARAM_IS_ACTIVE,StockUniveralConstants.QRY_PARAM_ITEM_TYPE_ID,StockUniveralConstants.QRY_PARAM_ITEM_ID};
		Object value[] = {StockUniveralConstants.ACTIVE_STATUS,itemTypeId,itemId};
		itemList=getHibernateTemplate().findByNamedQueryAndNamedParam(StockUniveralConstants.QRY_GET_MATERIAL_BY_TYPE_AND_ITEM_ID,params, value);
		return !StringUtil.isEmptyList(itemList)?itemList.get(0):null;
	}
	@Override
	public Boolean isAtleastOneConsignmentBooked(StockValidationTO validationTO)
			throws CGBusinessException {
		List<Long> result = null;
		boolean isBooked=false;
		String params[]={StockUniveralConstants.QRY_PARAM_START_LEAF,StockUniveralConstants.QRY_PARAM_END_LEAF};
		Object value[] = {validationTO.getStartSerialNumber(),validationTO.getEndSerialNumber()};
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				StockUniveralConstants.QRY_IS_CONSG_BOOKED_FOR_STOCK,
				params, value);
		if(!CGCollectionUtils.isEmpty(result) && result.get(0) > 0L  ){
			isBooked=true;
		}
		//will uncomment if require
		/*
		
		else {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					StockUniveralConstants.QRY_IS_CONSG_BOOKED_FOR_STOCK_FOR_CHILD,
					params, value);
		}
		if(!CGCollectionUtils.isEmpty(result) && result.get(0) > 0L  ){
			isBooked=true;
		}*/
		return isBooked;

	}
	@Override
	public StockTransferDO getStockTransferDtls(StockIssueValidationTO  issueTO) throws CGSystemException {
		Session session=null;
		List<StockTransferDO> result=null;
		Criteria criteria=null;
		try {
			session=createSession();
			criteria = session.createCriteria(StockTransferDO.class,"transferDetails");
			if(!StringUtil.isEmptyInteger(issueTO.getItemId())){
				criteria.add(Restrictions.eq("transferDetails.itemDO.itemId", issueTO.getItemId()));
			}
			criteria.add(Restrictions.eq("transferDetails.officeProductCodeInSeries", issueTO.getOfficeProductCode()));
			criteria.add(Restrictions.conjunction()
					.add(Restrictions.le("transferDetails.startLeaf",issueTO.getLeaf()))
					.add(Restrictions.ge("transferDetails.endLeaf", issueTO.getLeaf())));
			if(!StringUtil.isStringEmpty(issueTO.getIssuedTOPartyType())){
				criteria.add(Restrictions.eq("transferDetails.transferTOType",issueTO.getIssuedTOPartyType()));
				switch(issueTO.getIssuedTOPartyType()){
				case UdaanCommonConstants.ISSUED_TO_BA:
					if(!StringUtil.isEmptyInteger(issueTO.getIssuedTOPartyId())){
					criteria.add(Restrictions.eq("transferDetails.transferTOBaDO.customerId", issueTO.getIssuedTOPartyId()));
					}
					break;
				case UdaanCommonConstants.ISSUED_TO_BRANCH:
					if(!StringUtil.isEmptyInteger(issueTO.getIssuedTOPartyId())){
					criteria.add(Restrictions.eq("transferDetails.transferTOOfficeDO.officeId", issueTO.getIssuedTOPartyId()));
					}
					break;
				case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
					if(!StringUtil.isEmptyInteger(issueTO.getIssuedTOPartyId())){
					criteria.add(Restrictions.eq("transferDetails.transferTOEmpDO.employeeId", issueTO.getIssuedTOPartyId()));
					}
					break;
				case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
					if(!StringUtil.isEmptyInteger(issueTO.getIssuedTOPartyId())){
						criteria.add(Restrictions.eq("transferDetails.transferTOCustomerDO.customerId", issueTO.getIssuedTOPartyId()));
					}
					break;

				}
			}
			
			//criteria.addOrder(Order.desc("transferDetails.transactionModifiedDate"));
			
			DetachedCriteria  subQuery=DetachedCriteria.forClass(StockTransferDO.class,"subQry");
			if(!StringUtil.isEmptyInteger(issueTO.getItemId())){
				subQuery.add(Restrictions.eq("subQry.itemDO.itemId", issueTO.getItemId()));
			}
			subQuery.add(Restrictions.eq("subQry.officeProductCodeInSeries", issueTO.getOfficeProductCode()));
			subQuery.add(Restrictions.conjunction()
					.add(Restrictions.le("subQry.startLeaf", issueTO.getLeaf()))
					.add(Restrictions.ge("subQry.endLeaf", issueTO.getLeaf())));
			subQuery.add(Restrictions.eq("subQry.transactionStatus", StockUniveralConstants.TRANSACTION_STATUS));
			subQuery.setProjection(Projections.max("subQry.transactionModifiedDate"));
			subQuery.addOrder(Order.desc("subQry.transactionModifiedDate"));
			criteria.add(Property.forName("transferDetails.transactionModifiedDate").in(subQuery));
			criteria.setMaxResults(1);
			result= criteria.list();
		} catch (Exception e) {
			logger.error("StockUniversalDAOImpl::getStockTransferDtls :: Exception",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		
		return !StringUtil.isEmptyColletion(result)?result.get(0):null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ColoaderRatesDO getColoaderDtlsByID(Integer id) throws CGSystemException {
		logger.debug("StockUniversalDAOImpl :: getColoaderDtlsByID :: Start");
		List<ColoaderRatesDO> coloaderDOs = null;
		ColoaderRatesDO coloaderDO = null;
		try{
			String queryName = "getColoaderDtls";
			coloaderDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "id", id);
			if(!StringUtil.isEmptyColletion(coloaderDOs)){
				coloaderDO = coloaderDOs.get(0);
			}
		}catch(Exception e){
			logger.debug("StockUniversalDAOImpl :: getColoaderDtlsByID :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e); 
		}
		logger.debug("StockUniversalDAOImpl :: getColoaderDtlsByID :: End");
		return coloaderDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CSDSAPColoaderInvoiceDO getStagingColoaderDtlsByID(Integer id)
			throws CGSystemException {
		logger.debug("COLOADERINNVOICE ::  StockUniversalDAOImpl :: getStagingColoaderDtlsByID :: Start");
		List<CSDSAPColoaderInvoiceDO> stagingColoaderDOs = null;
		CSDSAPColoaderInvoiceDO stagingColoaderDO = null;
		try{
			String queryName = "getStagingColoaderDtls";
			stagingColoaderDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "id", id);
			if(!StringUtil.isEmptyColletion(stagingColoaderDOs)){
				stagingColoaderDO = stagingColoaderDOs.get(0);
			}
		}catch(Exception e){
			logger.error("COLOADERINNVOICE ::  StockUniversalDAOImpl :: getStagingColoaderDtlsByID :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e); 
		}
		logger.debug("COLOADERINNVOICE ::  StockUniversalDAOImpl :: getStagingColoaderDtlsByID :: End");
		return stagingColoaderDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CSDSAPColoaderInvoiceDO> getStagingColoaderDtls(String sapStatus) throws CGSystemException {
		logger.debug("StockUniversalDAOImpl :: getStagingColoaderDtls :: Start");
		List<CSDSAPColoaderInvoiceDO> stagingColoaderDOs = null;
		try{
			String queryName = "getStagingColoaderInvoiceDtls";
			stagingColoaderDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(
					queryName, "sapStatus", sapStatus);
		}catch(Exception e){
			logger.debug("StockUniversalDAOImpl :: getStagingColoaderDtls :: Exception"+e.getLocalizedMessage());
			throw new CGSystemException(e); 
		}
		logger.debug("StockUniversalDAOImpl :: getStagingColoaderDtls :: End");
		return stagingColoaderDOs;
	}
	
}
