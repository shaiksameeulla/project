package com.ff.admin.stockmanagement.stockissue.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.StockBusinessAssociateMappingDO;
import com.ff.domain.stockmanagement.masters.StockCustomerMappingDO;
import com.ff.domain.stockmanagement.masters.StockEmployeeMappingDO;
import com.ff.domain.stockmanagement.masters.StockFranchiseeMappingDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockIssueDAOImpl.
 */
public class StockIssueDAOImpl extends CGBaseDAO implements StockIssueDAO {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockIssueDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#saveIssueDtls(com.ff.domain.stockmanagement.operations.issue.StockIssueDO)
	 */
	@Override
	public Boolean saveIssueDtls(StockIssueDO domainEntity)
			throws CGSystemException {
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::saveIssueDtls ..Start Time:[ "+starttime+"]");
		Session session = null;
		Transaction tx=null;
		try {
			session = createSession();
			LOGGER.debug("StockIssueDAOImpl::saveIssueDtls after create session ..Start Time:[ "+System.currentTimeMillis()+"]");
			tx =session.beginTransaction();
			session.saveOrUpdate(domainEntity);
			updatePartialIssueDtls(domainEntity, session);
			updateStockForIssue(domainEntity, session);
			LOGGER.debug("StockIssueDAOImpl::saveIssueDtls before commit ..Start Time:[ "+System.currentTimeMillis()+"]");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOGGER.error("StockIssueDAOImpl::saveIssueDtls ::Exception ",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		long endttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::saveIssueDtls ..End Time:[ "+endttime+"]  Difference :["+(endttime-starttime)+"]");
		return Boolean.TRUE;
	}

/**
 * Update stock for issue.
 *
 * @param domainEntity the domain entity
 * @param session the session
 * @return the boolean
 * @throws CGSystemException the cG system exception
 */
private Boolean updateStockForIssue(StockIssueDO domainEntity,
		Session session) throws CGSystemException {
	String issuedType=domainEntity.getIssuedToType();
	
	decreaseStockForOffice(domainEntity, session);
	if(!issuedType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BRANCH)){
	increaseStockForPartyType(domainEntity, session);
	}
	return true;
}

/**
 * Increase stock for party type.
 *
 * @param domainEntity the domain entity
 * @param session the session
 */
private void increaseStockForPartyType(StockIssueDO domainEntity, Session session) {
	String qryName=null;
	String issuedType=domainEntity.getIssuedToType();
	Integer partyTypeId=null; 
	
	switch(issuedType){
	case UdaanCommonConstants.ISSUED_TO_BA:
		qryName=StockUniveralConstants.QRY_STOCK_INCREASE_BA;
		partyTypeId=domainEntity.getIssuedToBA().getCustomerId();
		break;
	case UdaanCommonConstants.ISSUED_TO_FR:
		qryName=StockUniveralConstants.QRY_STOCK_INCREASE_FR;
		partyTypeId=domainEntity.getIssuedToFranchisee().getCustomerId();
		break;
	case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
		qryName=StockUniveralConstants.QRY_STOCK_INCREASE_CUSTOMER;
		partyTypeId=domainEntity.getIssuedToCustomer().getCustomerId();
		break;
	case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
		qryName=StockUniveralConstants.QRY_STOCK_INCREASE_EMP;
		partyTypeId=domainEntity.getIssuedToPickupBoy().getEmployeeId();
		break;
	}
	
	
	for(StockIssueItemDtlsDO issue:domainEntity.getIssueItemDtlsDO()){
		Query qry=null;
		int result=0;
		qry= session.getNamedQuery(qryName);
		qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, partyTypeId);
		qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, issue.getItemDO().getItemId());
		qry.setInteger(StockUniveralConstants.QRY_PARAM_QUANTITY, issue.getIssuedQuantity());
		result= qry.executeUpdate();
		if(result<=0){
			createStockForParty(session, issuedType, partyTypeId, issue);
			
		}
	}
}

/**
 * Creates the stock for party.
 *
 * @param session the session
 * @param issuedType the issued type
 * @param partyTypeId the party type id
 * @param issue the issue
 */
private void createStockForParty(Session session, String issuedType,
		Integer partyTypeId, StockIssueItemDtlsDO issue) {
	switch(issuedType){
	case UdaanCommonConstants.ISSUED_TO_BA:
		StockBusinessAssociateMappingDO  sb=getBaStockEntity(partyTypeId, issue);
		session.save(sb);
		break;
	case UdaanCommonConstants.ISSUED_TO_FR:
		StockFranchiseeMappingDO sf=getFrStockEntity(partyTypeId, issue);
		session.save(sf);
		break;
	case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
		StockCustomerMappingDO sc=getCustomerStockEntity(partyTypeId, issue);
		session.save(sc);
		break;
	case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
		StockEmployeeMappingDO se=getEmployeeStockEntity(partyTypeId, issue);
		session.save(se);
		break;
	}

}

/**
 * Gets the ba stock entity.
 *
 * @param partyTypeId the party type id
 * @param issue the issue
 * @return the ba stock entity
 */
private StockBusinessAssociateMappingDO getBaStockEntity(Integer partyTypeId, StockIssueItemDtlsDO issue) {
	StockBusinessAssociateMappingDO sb;
	sb= new StockBusinessAssociateMappingDO();
	sb.setBaId(partyTypeId);
	sb.setItemId(issue.getItemDO().getItemId());
	sb.setQuantity(issue.getIssuedQuantity());
	return sb;
}

/**
 * Gets the fr stock entity.
 *
 * @param partyTypeId the party type id
 * @param issue the issue
 * @return the fr stock entity
 */
private StockFranchiseeMappingDO getFrStockEntity(Integer partyTypeId, StockIssueItemDtlsDO issue) {
	StockFranchiseeMappingDO sf;
	sf= new StockFranchiseeMappingDO();
	sf.setFranchiseeId(partyTypeId);
	sf.setItemId(issue.getItemDO().getItemId());
	sf.setQuantity(issue.getIssuedQuantity());
	return sf;
}

/**
 * Gets the customer stock entity.
 *
 * @param partyTypeId the party type id
 * @param issue the issue
 * @return the customer stock entity
 */
private StockCustomerMappingDO getCustomerStockEntity(Integer partyTypeId, StockIssueItemDtlsDO issue) {
	StockCustomerMappingDO sf;
	sf= new StockCustomerMappingDO();
	sf.setCustomerId(partyTypeId);
	sf.setItemId(issue.getItemDO().getItemId());
	sf.setQuantity(issue.getIssuedQuantity());
	return sf;
}

/**
 * Gets the employee stock entity.
 *
 * @param partyTypeId the party type id
 * @param issue the issue
 * @return the employee stock entity
 */
private StockEmployeeMappingDO getEmployeeStockEntity(Integer partyTypeId, StockIssueItemDtlsDO issue) {
	StockEmployeeMappingDO sf;
	sf= new StockEmployeeMappingDO();
	sf.setEmployeeId(partyTypeId);
	sf.setItemId(issue.getItemDO().getItemId());
	sf.setQuantity(issue.getIssuedQuantity());
	return sf;
}

/**
 * Decrease stock for office.
 *
 * @param domainEntity the domain entity
 * @param session the session
 */
private void decreaseStockForOffice(StockIssueDO domainEntity, Session session) {
	String qryName=StockUniveralConstants.QRY_STOCK_DECREASE_OFFICE;
	for(StockIssueItemDtlsDO issue:domainEntity.getIssueItemDtlsDO()){
		Query qry=null;
		qry= session.getNamedQuery(qryName);
		qry.setInteger(StockUniveralConstants.QRY_PARAM_TYPE_ID, domainEntity.getIssueOfficeDO().getOfficeId());
		qry.setInteger(StockUniveralConstants.QRY_PARAM_ITEM_ID, issue.getItemDO().getItemId());
		qry.setInteger(StockUniveralConstants.QRY_PARAM_QUANTITY, issue.getIssuedQuantity());
		qry.executeUpdate();
	}
}
	
	/**
	 * Update partial issue dtls.
	 *
	 * @param domainEntity the domain entity
	 * @param session the session
	 * @throws CGSystemException the cG system exception
	 */
	private void updatePartialIssueDtls(StockIssueDO domainEntity,
			Session session) throws CGSystemException {
		if(domainEntity.getTransactionFromType().equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE)){
			String qryName=null;
			Integer balanceQnty=null;
			for(StockIssueItemDtlsDO dtlsDO:domainEntity.getIssueItemDtlsDO()){
				balanceQnty= getRequisitionBalanceQnty(session,dtlsDO.getStockItemDtlsId());
				if(StringUtil.isNull(balanceQnty)){
					//i.e first time issue against Requisition then use below formula
					// balanceIssueQuantity = approvedQuantity-:issuedQuantity 
					qryName =  StockCommonConstants.QRY_UPDATE_REQ_BAL_QNTY_BY_APP_QNTY_FOR_ISSUE;
				}else{
					//i.e Partial issue happened earlier and issuing one more time
					//we use below formula
					//balanceIssueQuantity =  balanceIssueQuantity - :issuedQuantity 
					qryName = StockCommonConstants.QRY_UPDATE_REQ_BAL_QNTY_FOR_ISSUE;
				}

				updateRequisitionDtls(session,qryName,dtlsDO);
			}
		}
	}

	/**
	 * Gets the requisition balance qnty.
	 *
	 * @param reqItemDtlsId the req item dtls id
	 * @return the requisition balance qnty
	 * @throws CGSystemException the cG system exception
	 */
	private Integer getRequisitionBalanceQnty(Session session,Long reqItemDtlsId)throws CGSystemException{
		List<Integer> balanceQnty=null;
		Query qry = session.getNamedQuery(StockCommonConstants.QRY_GET_BALANCE_QNTY_ISSUE);
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID, reqItemDtlsId);
		balanceQnty =qry.list();
		//balanceQnty= getHibernateTemplate().findByNamedQueryAndNamedParam( StockCommonConstants.QRY_GET_BALANCE_QNTY_ISSUE,StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID,reqItemDtlsId);
		return !StringUtil.isEmptyList(balanceQnty)?balanceQnty.get(0):null;
	}
	
	//Method for update balance Issue Quantity 
	/**
	 * Update requisition dtls.
	 *
	 * @param session the session
	 * @param qryName the qry name
	 * @param dtlsDO the dtls do
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	private Integer updateRequisitionDtls(Session session,String qryName,StockIssueItemDtlsDO dtlsDO)throws CGSystemException{
		Query qry = session.getNamedQuery(qryName);
		qry.setInteger(StockCommonConstants.QRY_PARAM_ISSUED_QNTY, dtlsDO.getIssuedQuantity());
		qry.setLong(StockCommonConstants.QRY_PARAM_STOCK_ITEM_DTLS_ID, dtlsDO.getStockItemDtlsId());
		return qry.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#findDetailsByRequisitionNumber(com.ff.to.stockmanagement.stockissue.StockIssueTO)
	 */
	@Override
	public StockRequisitionDO findDetailsByRequisitionNumber(StockIssueTO to)
			throws CGSystemException {
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findDetailsByRequisitionNumber ..Start Time:[ "+starttime+"]");
		List<StockRequisitionDO> issueDtls = null;
		String params[] = {StockCommonConstants.QRY_PARAM_REQ_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {to.getRequisitionNumber(),StockCommonConstants.ACTIVE_STATUS,to.getLoggedInOfficeId()};
		issueDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_ISSUE_DTLS_BY_REQ_NUMBER_FOR_ISSUE, params, values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findDetailsByRequisitionNumber ..End Time:[ "+endtime+"]  difference:["+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(issueDtls)? issueDtls.get(0) :null;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#findDetailsByIssueNumber(com.ff.to.stockmanagement.stockissue.StockIssueTO)
	 */
	@Override
	public StockIssueDO findDetailsByIssueNumber(StockIssueTO to)
			throws CGSystemException {
		List<StockIssueDO> issueDtls=null;
		/*List<StockIssueDO> issueDtls = null;
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID};
		Object values[] = {to.getStockIssueNumber(),StockCommonConstants.ACTIVE_STATUS,to.getLoggedInOfficeId()};
		issueDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_ISSUE_DTLS_BY_ISSUE_NUMBER, params, values);
	*/	
		issueDtls = findDetailsByIssNumber(to);
		return !StringUtil.isEmptyList(issueDtls)? issueDtls.get(0) :null;
	}
	
	/**
	 * Find details by iss number.
	 *
	 * @param to the to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	public List<StockIssueDO> findDetailsByIssNumber(StockIssueTO to)
			throws CGSystemException {
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findDetailsByIssNumber ..Start Time:[ "+starttime+"]");
		List<StockIssueDO> issueDtls = null;
			Session session=null;
			Criteria issueCriteria=null;
			DetachedCriteria issueOffice=null;
		try {
			session = createSession();
			//for main Query ######START######
			issueCriteria = session.createCriteria(StockIssueDO.class,"issue");
			issueCriteria.add(Restrictions.in("issue.issuedToType", new String[]{UdaanCommonConstants.ISSUED_TO_BA,UdaanCommonConstants.ISSUED_TO_BRANCH,UdaanCommonConstants.ISSUED_TO_FR}));
			if(!StringUtil.isStringEmpty(to.getStockIssueNumber())){
				issueCriteria.add(Restrictions.eq("issue.stockIssueNumber", to.getStockIssueNumber()));
			}
			issueCriteria.add(Restrictions.eq("issue.transactionStatus", StockCommonConstants.ACTIVE_STATUS));
			issueCriteria.setFetchMode("issue.issueItemDtlsDO", FetchMode.JOIN);
			issueCriteria.setFetchMode("issue.issuePaymentDtlsDO", FetchMode.JOIN);
			if(!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())){
				//for Sub Query ****START****
				issueOffice = DetachedCriteria.forClass(OfficeDO.class, "office");
					issueOffice.add( Restrictions.eq("office.officeId",to.getLoggedInOfficeId()));  
				issueOffice.setProjection(Projections.property("office.officeId"));
				//for Sub Query ****END****

				//for Main Query Join Condition
				issueCriteria.add(Property.forName("issue.issueOfficeDO.officeId").in(issueOffice));
			}
			issueCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			//frOffice.setResultTransformer(Transformers.aliasToBean(FranchiseeDO.class));
			issueDtls = issueCriteria.list();
			//for main Query ######END######
		} catch (Exception e) {
			LOGGER.error("StockIssueDAOImpl::findDetailsByIssNumber ::Exception:",e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findDetailsByIssNumber ..End Time:[ "+endtime+"] difference :["+(endtime-starttime)+"]");
		return issueDtls;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#updateStockIssueBranch(com.ff.domain.stockmanagement.operations.issue.StockIssueDO)
	 */
	@Override
	@Deprecated
	public Boolean updateStockIssueBranch(StockIssueDO entityDO)
			throws CGSystemException {
		Boolean result=Boolean.FALSE;
		Session session=null;
		Transaction tx=null;
		
			 try {
				session=createSession();
				tx=session.beginTransaction();
				Query headerQry=session.createQuery(updateStockIssueBranchHeader(entityDO));
				headerQry.executeUpdate();
				
				for(StockIssueItemDtlsDO itemDtls:entityDO.getIssueItemDtlsDO()){
					Query dtlsQry=session.createQuery(updateStockIssueBranchItemDtls(itemDtls));
					dtlsQry.setTimestamp(StockCommonConstants.QRY_PARAM_TRANS_MODIFIED_DATE, itemDtls.getTransactionModifiedDate());
					dtlsQry.executeUpdate();
				}
				if(!StringUtil.isNull(entityDO.getIssuePaymentDtlsDO()) && !StringUtil.isEmptyLong(entityDO.getIssuePaymentDtlsDO().getStockPaymentId())){
					session.merge(entityDO.getIssuePaymentDtlsDO());
				}
				tx.commit();
				result=Boolean.TRUE;
			} catch (Exception e) {
				// TODO remove later
				tx.rollback();
				throw new CGSystemException(e);
			}finally{
				closeSession(session);
			}
			
			 return result;
	}
	
	
	//Since we update very less no of columns ,we have decided to use below approach
	/**
	 * Update stock issue branch header.
	 *
	 * @param regDo the reg do
	 * @return the string
	 */
	/**
	 * @param regDo
	 * @return
	 */
	@Deprecated
	private String updateStockIssueBranchHeader(StockIssueDO regDo){
		StringBuilder qry=new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.issue.StockIssueDO headerDo SET  ");
		//qry.append("headerDo.updatedByUser.userId ="+regDo.getUpdatedByUser().getUserId());
		if(!StringUtil.isEmptyInteger(regDo.getUpdatedBy())){
			qry.append("headerDo.updatedBy ="+regDo.getUpdatedBy());
		}
		qry.append(" where headerDo.stockIssueId="+regDo.getStockIssueId());
		return qry.toString();
	}
	//Since we update very less no of columns ,we have decided to use below approach
	/**
	 * Update stock issue branch item dtls.
	 *
	 * @param reqDtlsDo the req dtls do
	 * @return the string
	 */
	@Deprecated
	private String updateStockIssueBranchItemDtls(StockIssueItemDtlsDO reqDtlsDo){
		StringBuilder qry=new StringBuilder();
		qry.append("UPDATE com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO dtlsDo SET  ");
		qry.append("dtlsDo.remarks = '"+reqDtlsDo.getRemarks()+"'");
		if(!StringUtil.isStringEmpty(reqDtlsDo.getStartSerialNumber())){
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.officeProductCodeInSeries = '"+reqDtlsDo.getOfficeProductCodeInSeries()+"'");
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.startLeaf = "+reqDtlsDo.getStartLeaf());
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.endLeaf = "+reqDtlsDo.getEndLeaf());
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.startSerialNumber = '"+reqDtlsDo.getStartSerialNumber()+"'");
			qry.append(CommonConstants.COMMA);
			qry.append("dtlsDo.endSerialNumber = '"+reqDtlsDo.getEndSerialNumber()+"'");
			qry.append(CommonConstants.COMMA);
		}else{
			qry.append(CommonConstants.COMMA);
		}
		qry.append("dtlsDo.transactionModifiedDate = :transactionModifiedDate");
		qry.append(" where dtlsDo.stockIssueItemDtlsId="+reqDtlsDo.getStockIssueItemDtlsId());
		return qry.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#saveIssueEmployeeDtls(com.ff.domain.stockmanagement.operations.issue.StockIssueDO)
	 */
	@Override
	public Boolean saveIssueEmployeeDtls(StockIssueDO domainEntity) 
			throws CGSystemException {
		Session session = null;
		Transaction tx=null;
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::saveIssueEmployeeDtls ..Start Time:[ "+starttime+"]");
			try {
				
				session = createSession();
				tx =session.beginTransaction();
				session.saveOrUpdate(domainEntity);
				updateStockForIssue(domainEntity, session);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				LOGGER.error("StockIssueDAOImpl::saveIssueEmployeeDtls ::Exception ",e);
				throw new CGSystemException(e);
			}finally{
				closeSession(session);
			}
			long endtime=System.currentTimeMillis();
			LOGGER.debug("StockIssueDAOImpl::saveIssueEmployeeDtls ..End Time:[ "+endtime+"]  Differece :["+(endtime-starttime)+"]");
			return Boolean.TRUE;}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO#findIssueEmployeeDtls(com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StockIssueDO findIssueEmployeeDtls(StockIssueEmployeeTO to)
			throws CGSystemException {
		List<StockIssueDO> issueDtls = null;
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findIssueEmployeeDtls ..Start Time:[ "+starttime+"]");
		String params[] = {StockCommonConstants.QRY_PARAM_ISSUE_NUMBER,StockCommonConstants.QRY_PARAM_TRANSACTION_STATUS,StockCommonConstants.QRY_PARAM_OFFICEID,StockCommonConstants.QRY_PARAM_ISSUED_TO_TYPE};
		Object values[] = {to.getStockIssueNumber(),StockCommonConstants.ACTIVE_STATUS,to.getLoggedInOfficeId(),to.getIssuedToType()};
		issueDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(StockCommonConstants.QRY_FIND_ISSUE_CUST_DTLS_BY_ISSUE_NUMBER_FOR_ISSUE_TO_CUST, params, values);
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockIssueDAOImpl::findIssueEmployeeDtls ..End Time:[ "+endtime+"] Difference :"+(endtime-starttime)+"]");
		return !StringUtil.isEmptyList(issueDtls)? issueDtls.get(0) :null;
	} 
}
