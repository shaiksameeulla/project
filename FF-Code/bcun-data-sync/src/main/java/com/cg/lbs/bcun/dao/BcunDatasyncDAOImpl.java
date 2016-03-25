package com.cg.lbs.bcun.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.domain.CGBcunInbundDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.to.ManualDownloadInputTO;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.to.TwoWayWriteTO;
import com.ff.domain.bcun.OutboundDataPacketWrapperDO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.organization.OfficeDO;

/**
 * @author mohammal Jan 15, 2013
 * 
 */
public class BcunDatasyncDAOImpl extends CGBaseDAO implements BcunDatasyncDAO {

	private String blobLoadFactor="1";
	private static final Logger logger = Logger
			.getLogger(BcunDatasyncDAOImpl.class);

	/**
	 * @return the blobLoadFactor
	 */
	public Integer getBlobLoadFactorForEachBranch() {
		Integer loadFactor=StringUtil.parseInteger(blobLoadFactor);
		return StringUtil.isEmptyInteger(loadFactor)?1:loadFactor;
	}
	/**
	 * @return the blobLoadFactor
	 */
	public String getBlobLoadFactor() {
		return blobLoadFactor;
	}

	/**
	 * @param blobLoadFactor the blobLoadFactor to set
	 */
	public void setBlobLoadFactor(String blobLoadFactor) {
		this.blobLoadFactor = blobLoadFactor;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndRowCount(String namedQuery,
			Integer rowCount) {
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<CGBaseDO> unSyncList = null;
		Session session = null;
		//Getting current hibernate template
		HibernateTemplate template = getHibernateTemplate();
		try {
			//Setting default row count if it is null
			if (rowCount == null) {
				rowCount = BcunConstant.BCUN_DEFAULT_MAX_ROW_FETCH_COUNT;
				logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::setting default row count====>"
						+ rowCount);
			}
			//opening session from template
			session = template.getSessionFactory().openSession();
			//creating query from named query
			Query q = session.getNamedQuery(namedQuery);
			//setting row count in query
			q.setMaxResults(rowCount);
			//fetching data based on query  
			unSyncList = q.list();
			logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			q = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					, e);
			throw e;
		} finally {
			//releasing session if not
			if (session != null)
				session.close();
			template = null;
		}
		return unSyncList;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getOfficeDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<CGBaseDO> getOfficeDataByNamedQueryAndRowCount(
			String namedQuery, Integer rowCount, Integer officeId) {
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<CGBaseDO> unSyncList = null;
		try {
			if (officeId == null || officeId == 0)
				return null;
			//Setting default row count if not configured in properties file
			if (rowCount == null) {
				rowCount = BcunConstant.BCUN_DEFAULT_MAX_ROW_FETCH_COUNT;
				logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::setting default row count====>"
						+ rowCount);
			}
			String paramName = "branchId";
			//Fetching data from database based on parameters
			unSyncList = getDataByNamedQueryNamedParamAndRowCount(namedQuery,
					paramName, officeId, rowCount);
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					, e);
			throw e;
		}
		return unSyncList;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getDataByNamedQueryNamedParamAndRowCount(java.lang.String, java.lang.String, java.lang.Object, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQueryNamedParamAndRowCount(
			String namedQuery, String paramName, Object paramValue,
			Integer rowCount) {
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<CGBaseDO> unSyncList = null;
		Session session = null;
		//Getting hibernate templete
		HibernateTemplate template = getHibernateTemplate();
		try {
			//Opening session
			session = openTransactionalSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			query.setMaxResults(rowCount);
			query.setParameter(paramName, paramValue);
			//Getting result set from query
			unSyncList = query.list();
			logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					, e);
			throw e;
		} finally {
			//Closing session if not closed
			closeTransactionalSession(session);
			template = null;
		}
		return unSyncList;
	}

	public List<OutboundOfficePacketDO> getOfficePacketByNamedQueryNamedParamAndRowCount(
			String namedQuery, String paramName, Object paramValue,
			Integer rowCount) {
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<OutboundOfficePacketDO> unSyncList = null;
		Session session = null;
		//Getting hibernate templete
		//HibernateTemplate template = getHibernateTemplate();
		try {
			//Opening session
			//session=openTransactionalSession();
			session=createSession();
			//session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			int rowCountTemp=rowCount/getBlobLoadFactorForEachBranch();
			query.setMaxResults(rowCountTemp);
			query.setParameter(paramName, paramValue);
			//Getting result set from query
			unSyncList = query.list();
			logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					, e);
			throw e;
		} finally {
			//Closing session if not closed
			if (session != null)
			{closeTransactionalSession(session);}
			//template = null;
		}
		return unSyncList;
	}
	@Override
	public List<OutboundDataPacketWrapperDO> getDataPacketByOffice(OutboundBranchDataTO inputTO) {
		String hqlName = "getPacketDtlsByOfficeCodewrapper";
		String paramName = "branchCode";
		List<OutboundDataPacketWrapperDO> unSyncList = getDataPacketForOfficeByNamedQueryNamedParamAndRowCount(
				hqlName, paramName, inputTO.getBranchCode(),
				inputTO.getMaxRecords());
		return unSyncList;
	}
	public List<OutboundDataPacketWrapperDO> getDataPacketForOfficeByNamedQueryNamedParamAndRowCount(
			String namedQuery, String paramName, Object paramValue,
			Integer rowCount) {
		logger.debug("BcunDatasyncDAOImpl::getDataPacketForOfficeByNamedQueryNamedParamAndRowCount::start====>");
		List<OutboundDataPacketWrapperDO> unSyncList = null;
		Session session = null;
		//Getting hibernate templete
		//HibernateTemplate template = getHibernateTemplate();
		try {
			//Opening session
			//session=openTransactionalSession();
			session=createSession();
			//session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			int rowCountTemp=rowCount/getBlobLoadFactorForEachBranch();
			query.setMaxResults(rowCountTemp);
			query.setParameter(paramName, paramValue);
			//Getting result set from query
			unSyncList = query.list();
			logger.trace("BcunDatasyncDAOImpl::getDataPacketForOfficeByNamedQueryNamedParamAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataPacketForOfficeByNamedQueryNamedParamAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataPacketForOfficeByNamedQueryNamedParamAndRowCount::Exception::"
					, e);
			throw e;
		} finally {
			//Closing session if not closed
			if (session != null)
			{closeTransactionalSession(session);}
			//template = null;
		}
		return unSyncList;
	}
	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getDataByNamedQuery(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQuery(String namedQuery) {
		return getHibernateTemplate().findByNamedQuery(namedQuery);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends Number> getNumberByNamedQuery(String namedQuery) {
		return getHibernateTemplate().findByNamedQuery(namedQuery);
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery,
			String param, Object value) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				param, value);
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				params, values);
	}

	/**
	 * Gets the numbers by named query and named param.
	 *
	 * @param namedQuery the named query
	 * @param params the params
	 * @param values the values
	 * @return the numbers by named query and named param
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getNumbersByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				params, values);
	}

	@Override
	public List<Integer> getDestinationOfficeForStockCustomer(String namedQuery, String officeType,Integer loggedInoffice,List<Integer> officeList) {
		List<Integer> destinationList=null;
		Session session = null;
		//Getting hibernate templete
		HibernateTemplate template = getHibernateTemplate();
		try {
			//Opening session
			session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			query.setInteger(BcunDataFormaterConstants.QRY_PARAM_BRANCHID, loggedInoffice);
			if(!StringUtil.isStringEmpty(officeType)){
				if(officeType.contains(FrameworkConstants.CHARACTER_COMMA)){
					query.setParameterList(BcunDataFormaterConstants.QRY_PRARAM_OFFICE_TYPE_CODE,officeType.split(FrameworkConstants.CHARACTER_COMMA));
				}else{
					query.setString(BcunDataFormaterConstants.QRY_PRARAM_OFFICE_TYPE_CODE,officeType);
				}
			}
			query.setParameterList(BcunDataFormaterConstants.QRY_PARAM_BRANCH_LIST,officeList);
			destinationList=query.list();
		}finally{
			closeSession(session);
		}
		return destinationList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<?> getAnonymusTypeDataByNamedQueryAndNamedParam(String namedQuery,
			String[] params, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				params, values);
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#saveOrUpdateTransferedEntity(com.capgemini.lbs.framework.domain.CGBaseDO)
	 */
	@Override
	public void saveOrUpdateTransferedEntity(CGBaseDO baseEntity) {
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::startxxxxxxxxxxxxxxxxxxxx====>");
		if (baseEntity == null) {
			logger.trace("BcunDatasyncDAOImpl::updateTransferedEntity::entity which has to update should not be null....");
			return;
		}
		//Saving/Updating entity if not saved
		//getHibernateTemplate().saveOrUpdate(baseEntity);
		baseEntity=getHibernateTemplate().merge(baseEntity);
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::end====>");
		//throw new RuntimeException("Testing.......");
	}
	@Override
	public void persistOrUpdateTransferedEntity(CGBaseDO baseEntity) {
		logger.debug("BcunDatasyncDAOImpl::persistOrUpdateTransferedEntity::startxxxxxxxxxxxxxxxxxxxx====>");
		if (baseEntity == null) {
			logger.trace("BcunDatasyncDAOImpl::persistOrUpdateTransferedEntity::entity which has to update should not be null....");
			return;
		}
		//Saving/Updating entity if not saved
		//getHibernateTemplate().saveOrUpdate(baseEntity);
		baseEntity=getHibernateTemplate().merge(baseEntity);
		logger.debug("BcunDatasyncDAOImpl::persistOrUpdateTransferedEntity::end====>");
		//throw new RuntimeException("Testing.......");
	}
	@Override
	public void updateEntityStatus(CGBaseDO baseEntity) {
		logger.debug("BcunDatasyncDAOImpl::updateEntityStatus::start====>");
		if (baseEntity == null) {
			logger.trace("BcunDatasyncDAOImpl::updateEntityStatus::entity which has to update should not be null....");
			return;
		}
		//Updating entity if not saved
		getHibernateTemplate().update(baseEntity);
		logger.debug("BcunDatasyncDAOImpl::updateEntityStatus::end====>");
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#saveOrUpdateTransferedEntities(java.util.List)
	 */
	@Override
	public void saveOrUpdateTransferedEntities(List<CGBaseDO> baseEntities) {
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::start====>");
		if (baseEntities != null && !baseEntities.isEmpty()) {
			logger.trace("BcunDatasyncDAOImpl::updateTransferedEntities::baseEntities::"
					+ baseEntities);
			//int count = 0;
			//Saving/Updating all entities
			/*for (CGBaseDO baseEntity : baseEntities) {
				logger.trace("BcunDatasyncDAOImpl::updateTransferedEntities::updating ["
						+ count + "] entity");
				saveOrUpdateTransferedEntity(baseEntity);
			}*/
			getHibernateTemplate().saveOrUpdateAll(baseEntities);
		} else {
			logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::entities list is empty or null");
		}
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::end====>");

	}

	@Override
	public void saveOrUpdateTransferedEntitiesForRateContract(List<Integer> primaryKeyList, String qryName){
		Session session = null;
		logger.debug("BcunDatasyncDAOImpl : saveOrUpdateTransferedEntitiesForRateContract(): START with params"
				+ primaryKeyList);
		try {
			session = openTransactionalSession();
			//tx=session.beginTransaction();
			Query qry = session.getNamedQuery(qryName);
			if(!CGCollectionUtils.isEmpty(primaryKeyList)){
				qry.setParameterList("pojoPrimarykey", primaryKeyList);
				logger.debug("BcunDatasyncDAOImpl : saveOrUpdateTransferedEntitiesForRateContract(): END updated count:["
						+ qry.executeUpdate() + "]");
			}else{
				logger.error("BcunDatasyncDAOImpl :saveOrUpdateTransferedEntitiesForRateContract: contractIDs empty");
			}
			//tx.commit();
		} catch (Exception obj) {
			logger.error("BcunDatasyncDAOImpl::saveOrUpdateTransferedEntitiesForRateContract::Exception occured:"
					,obj);
			//tx.rollback();
			throw obj;
		} finally {
			closeTransactionalSession(session);
		}

		//getHibernateTemplate().bulkUpdate(hql, packetInfo.getListOfIds());
	}

	@Override
	public void updateBcunDataTransferFlag(List<CGBcunInbundDO> cgBcunInboundList, String qryName){
		Session session = null;
		logger.debug("BcunDatasyncDAOImpl : updateBcunDataTransferFlag(): START with Query"
				+ qryName);
		try {
			session = openTransactionalSession();
			//tx=session.beginTransaction();
			Query qry = session.getNamedQuery(qryName);

			if(!CGCollectionUtils.isEmpty(cgBcunInboundList)){
				Number primaryKey=cgBcunInboundList.get(0).getPojoPrimarykey();
				boolean isLong=primaryKey instanceof Long;
				boolean isInteger=primaryKey instanceof Integer;
				for(CGBcunInbundDO inboundDO : cgBcunInboundList){
					if(!StringUtil.isNull(inboundDO.getPojoPrimarykey())){
						if (isLong) {
							qry.setLong("pojoPrimarykey", (Long)inboundDO.getPojoPrimarykey());
						}else if(isInteger){
							qry.setInteger("pojoPrimarykey", (Integer)inboundDO.getPojoPrimarykey());
						}
					}
					if(!StringUtil.isStringEmpty(inboundDO.getBusinesskey())){
						qry.setString("businesskey", inboundDO.getBusinesskey());
					}
					logger.debug("BcunDatasyncDAOImpl : updateBcunDataTransferFlag(): END updated count:["
							+ qry.executeUpdate() + "]");

				}
			}else{
				logger.error("BcunDatasyncDAOImpl :updateBcunDataTransferFlag: contractIDs empty");
			}
			//tx.commit();
		} catch (Exception obj) {
			logger.error("BcunDatasyncDAOImpl::updateBcunDataTransferFlag::Exception occured:"
					,obj);
			//tx.rollback();
			throw obj;
		} finally {
			closeTransactionalSession(session);
		}

		//getHibernateTemplate().bulkUpdate(hql, packetInfo.getListOfIds());
	}


	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getOutboundBranchData(com.cg.lbs.bcun.to.OutboundBranchDataTO)
	 */
	@Override
	public List<OutboundOfficePacketDO> getOutboundBranchData(OutboundBranchDataTO inputTO) {
		String hqlName = "getPacketDtlsByOfficeCode";
		String paramName = "branchCode";
		List<OutboundOfficePacketDO> unSyncList = getOfficePacketByNamedQueryNamedParamAndRowCount(
				hqlName, paramName, inputTO.getBranchCode(),
				inputTO.getMaxRecords());
		return unSyncList;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.dao.BcunDatasyncDAO#getUniqueId(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer getUniqueId(String queryName, String[] params,Object[] values){
		List<Integer> uniqueIds = null;
		uniqueIds = getHibernateTemplate().findByNamedQueryAndNamedParam(
				queryName, params, values);
		return (!StringUtil.isEmptyList(uniqueIds)) ? uniqueIds.get(0) : null;
	}

	@Override
	public List<CGBaseDO> getOutboundMasterDataFromDB(String namedQuery,
			Integer maxRowFetch) {
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<CGBaseDO> unSyncList = null;
		Session session = null;
		//Getting hibernate templete
		HibernateTemplate template = getHibernateTemplate();
		try {
			//Opening session
			session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.createQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			if(maxRowFetch == null || maxRowFetch == 0)
				maxRowFetch = 200;

			query.setMaxResults(maxRowFetch);	
			//Getting result set from query
			unSyncList = query.list();
			logger.trace("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					, e);
			throw e;
		} finally {
			//Closing session if not closed
			if (session != null)
				session.close();
			template = null;
		}
		return unSyncList;
	}

	/**
	 * Delete from database.
	 *
	 * @param qryName the qry name
	 * @param paramWithValues the param with values
	 * @return true, if successful
	 * @throws CGSystemException 
	 */
	@Override
	public boolean loadAndDeleteFromDatabase(String qryName,Map<Object,Object> paramWithValues) throws CGSystemException {
		logger.debug("BcunDatasyncDAOImpl::deleteFromDatabase::start====>");
		List<CGBaseDO> results=null;
		Session session = null;
		//Getting hibernate templete
		HibernateTemplate template = getHibernateTemplate();
		Transaction tx=null;
		try {
			//Opening session
			session = template.getSessionFactory().openSession();
			tx =session.beginTransaction();
			//Creating query from session
			Query query = session.getNamedQuery(qryName);
			//Setting max number of row which has to be fetched from query
			query.setProperties(paramWithValues);
			results=query.list();
			query = null;


			if(!CGCollectionUtils.isEmpty(results)){
				for( CGBaseDO baseDO:results ){
					session.delete(baseDO);
				}
			}
			tx.commit();
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::deleteFromDatabase::Exception::"
					, e);
			tx.rollback();
			throw  new CGSystemException(e);
		} finally {
			//Closing session if not closed
			if (session != null)
				session.close();
			template = null;
		}
		return true;
	}

	@Override
	public void updatePacketStatus(OutboundBranchDataTO packetInfo, String qryName) throws CGSystemException {
		Session session = null;
		logger.debug("BcunDatasyncDAOImpl : updatePacketStatus(): START with params"
				+ packetInfo.getPacketIds());
		Transaction tx=null;
		try {
			session = openTransactionalSession();
			//tx=session.beginTransaction();
			Query qry = session.getNamedQuery(qryName);
			if(!CGCollectionUtils.isEmpty(packetInfo.getPacketIds())){
				qry.setParameterList("datasyncId", packetInfo.getPacketIds());
				logger.debug("BcunDatasyncDAOImpl : updatePacketStatus(): END updated count:["
						+ qry.executeUpdate() + "]");
			}else{
				logger.error("BcunDatasyncDAOImpl :updatePacketStatus: Packet Id list should be not be null");
			}
			//tx.commit();
		} catch (Exception obj) {
			logger.error("BcunDatasyncDAOImpl::updatePacketStatus::Exception occured:"
					,obj);
			//tx.rollback();
			throw new CGSystemException(obj);
		} finally {
			closeTransactionalSession(session);
		}

		//getHibernateTemplate().bulkUpdate(hql, packetInfo.getListOfIds());
	}

	@Override
	public List<OutboundDataPacketWrapperDO> getPacketDetailsForManualDownload(ManualDownloadInputTO manualDownLoadTO) throws CGSystemException{
		Session session = null;
		logger.debug("BcunDatasyncDAOImpl : getPacketDetailsForManualDownload(): START with params"
				+ manualDownLoadTO.getOfficeCode()+ "no of records :["+manualDownLoadTO.getMaxRecordsToFetch()+"]");
		Criteria criteria=null;
		List<OutboundDataPacketWrapperDO> blobList=null;
		try {
			session =createSession();
			criteria = session.createCriteria(OutboundOfficePacketDO.class,
					"officePacket");
			if (!StringUtil.isStringEmpty(manualDownLoadTO.getBlobStatus())) {
				if(manualDownLoadTO.getBlobStatus().equalsIgnoreCase("N")){
					criteria.add(Restrictions.in("officePacket.transferStatus",new String[]{
							manualDownLoadTO.getBlobStatus(),"I"}));
				}else{
					criteria.add(Restrictions.eq("officePacket.transferStatus",
							manualDownLoadTO.getBlobStatus()));
				}
			}
			preparSubQueryForBlob(manualDownLoadTO, criteria);
			prepareSubQueryForOffice(manualDownLoadTO, criteria);
			criteria.addOrder(Order.asc("officePacket.datasyncId"));
			if(!StringUtil.isEmptyInteger(manualDownLoadTO.getMaxRecordsToFetch())){
				criteria.setMaxResults(manualDownLoadTO.getMaxRecordsToFetch());
			}
			criteria.setProjection(
					Projections.projectionList()
					.add(Projections.alias(Projections.property("officePacket.datasyncId"), "dataOfficePacketId"))
					.add(Projections.alias(Projections.property("packet.packetData"), "packetData"))
					);
			criteria.setResultTransformer(Transformers.aliasToBean(OutboundDataPacketWrapperDO.class));
			blobList=criteria.list();
		} catch (Exception obj) {
			logger.error("BcunDatasyncDAOImpl::getPacketDetailsForManualDownload::Exception occured:"
					,obj);
			//tx.rollback();
			throw new CGSystemException(obj);
		} finally {
			closeTransactionalSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl : getPacketDetailsForManualDownload(): END");
		return blobList;
	}

	private void preparSubQueryForBlob(ManualDownloadInputTO manualDownLoadTO,
			Criteria criteria) {
		if(!StringUtil.isStringEmpty(manualDownLoadTO.getBlobType()) || (!StringUtil.isNull(manualDownLoadTO.getStartDate())&&!StringUtil.isNull(manualDownLoadTO.getEndDate()))){

			Criteria subQuery = criteria.createCriteria("officePacket.packetDO","packet");
			if (!StringUtil.isStringEmpty(manualDownLoadTO.getBlobType())) {
				subQuery.add(Restrictions.like("packet.fileName",
						manualDownLoadTO.getBlobType()));

			}
			if (!StringUtil.isNull(manualDownLoadTO.getStartDate())&&!StringUtil.isNull(manualDownLoadTO.getEndDate())) {
				subQuery.add(Restrictions.between("packet.transactionCreateDate",
						manualDownLoadTO.getStartDate(),manualDownLoadTO.getEndDate()));

			}

		}
	}
	private void prepareSubQueryForOffice(ManualDownloadInputTO manualDownLoadTO,
			Criteria criteria) {
		if(!StringUtil.isStringEmpty(manualDownLoadTO.getOfficeCode())){
			DetachedCriteria  subQuery=DetachedCriteria.forClass(OfficeDO.class,"officeDO");
			subQuery.setProjection(Projections.property("officeDO.officeId"));
			subQuery.add(Restrictions.eq("officeDO.officeCode",
					manualDownLoadTO.getOfficeCode()));
			criteria.add(Property.forName("officePacket.outboundOfficeId").in(subQuery));
		}
	}
	@Override
	public void updateDownloadedPacketStatus(List<OutboundOfficePacketDO> baseEntities) {
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::start====>");
		if (baseEntities != null && !baseEntities.isEmpty()) {
			logger.trace("BcunDatasyncDAOImpl::updateDownloadedPacket::baseEntities::"
					+ baseEntities);

			getHibernateTemplate().saveOrUpdateAll(baseEntities);
		} else {
			logger.debug("BcunDatasyncDAOImpl::updateDownloadedPacket::entities list is empty or null");
		}
		logger.debug("BcunDatasyncDAOImpl::updateDownloadedPacket::end====>");

	}

	@Override
	public CGBaseDO getCGBaseDOById(TwoWayWriteTO twoWayWriteTO) throws CGSystemException {
		logger.debug("BcunDatasyncDAOImpl :: getCGBaseDOById() :: Start --------> ::::::");
		Session session = null;
		CGBaseDO cgBaseDO = null;
		Transaction tx=null;
		try {
			session = createSession();
			tx = session.beginTransaction();
			//session = getSessionFactory().getCurrentSession();
			///session = openTransactionalSession();
			cgBaseDO = (CGBaseDO) session.get(twoWayWriteTO.getProcessClass(), twoWayWriteTO.getId());
			cgBaseDO.setDtToCentral(twoWayWriteTO.getDtToCentral());
			session.flush();
			tx.commit();
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getCGBaseDOById::Exception occured:" , e);
			tx.rollback();
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
			//closeTransactionalSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl :: getCGBaseDOById() :: End --------> ::::::");
		return cgBaseDO;
	}

	@Override
	public boolean updateConsgBillingStatus(String consgNumber)
			throws CGSystemException {
		logger.debug("BcunDatasyncDAOImpl :: updateConsgBillingStatus() :: Start :: consgNumber :: " + consgNumber + "--------> ::::::");
		Session session = null;
		Boolean update = Boolean.FALSE; 
		try {
			//session = openTransactionalSession();
			session=createSession();
			Query query = session
					.getNamedQuery("updateConsgBillingStatus");
			query.setString("consgNumber", consgNumber);
			int i = query.executeUpdate();
			update = i>0? Boolean.TRUE:Boolean.FALSE;

		} catch (Exception e) {
			logger.error("Exception Occured in::BcunDatasyncDAOImpl::updateConsgBillingStatus() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			//closeTransactionalSession(session);
			closeSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl :: updateConsgBillingStatus() :: End --------> ::::::");
		return update;
	}
	@Override
	public boolean updateConsgStatusForDelivery(DeliveryDetailsDO deliveryDtlsDO)
			throws CGSystemException {
		logger.debug("BcunDatasyncDAOImpl :: updateConsgStatusForDelivery() :: Start :: consgNumber :: " + deliveryDtlsDO.getConsignmentDO() + "--------> ::::::");
		Session session = null;
		Boolean update = Boolean.FALSE; 
		ConsignmentDO	consgDO	=null;
		try {
			session=openTransactionalSession();
			consgDO	=deliveryDtlsDO.getConsignmentDO();
			Query query = session
					.getNamedQuery("updateConsgDtlsByConsgForBcun");
			query.setInteger("consgId", consgDO.getConsgId());
			query.setString("recvNameOrCompName", consgDO.getRecvNameOrCompName());
			query.setString("deliveryStatus", consgDO.getConsgStatus());
			query.setTimestamp("deliveryDateTime",  consgDO.getDeliveredDate());
			int i = query.executeUpdate();
			if(i>0 && deliveryDtlsDO.getIsDestinationMismatch().equalsIgnoreCase(BcunConstant.YES)){
				query = session
						.getNamedQuery("updateConsgDtlsByPincodeForCentralDrsBcun");
				query.setInteger("pincodeId", deliveryDtlsDO.getConsignmentDO().getDestPincodeId().getPincodeId());
				query.setInteger("consgId", deliveryDtlsDO.getConsignmentDO().getConsgId());
				i = query.executeUpdate();
				if(i>0){
					query = session
							.getNamedQuery("updateConsgRateDtlsForCentralDrsBcun");
					query.setInteger("consgId", deliveryDtlsDO.getConsignmentDO().getConsgId());
					i = query.executeUpdate();
				}
			}
			update = i>0? Boolean.TRUE:Boolean.FALSE;
			logger.info("BcunDatasyncDAOImpl :: updateConsgStatusForDelivery() :: Start :: consgNumber :: " + consgDO.getConsgNo() + "--------> updated count :["+i+"] and status :["+consgDO.getConsgStatus()+"]");
		} catch (Exception e) {
			logger.error("Exception Occured in::BcunDatasyncDAOImpl::updateConsgStatusForDelivery() :: for CN "+ consgDO.getConsgNo()
					, e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl :: updateConsgStatusForDelivery() :: END :: consgNumber :: " + consgDO.getConsgNo() + "--------> ::::::");
		return update;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends CGBaseDO> getReconcillationData(
			ReconcillationConfigPropertyTO configPropertyTO)
					throws CGSystemException {
		logger .debug("BcunDatasyncDAOImpl: getReconcillationDataFromCentral(): START");
		Session session = null;
		Query query=null;
		List<BcunReconcillationDO> reconcillationDos=null;
		try {
			session=createSession();
			query=session.getNamedQuery(configPropertyTO.getNamedQuery());
			query.setParameter("transactionDate",configPropertyTO.getTransactionDate());
			query.setResultTransformer(Transformers.aliasToBean(BcunReconcillationDO.class));
			reconcillationDos=query.list();
			logger .debug("---Count of rows for reconcillation------"+reconcillationDos.size());
		}catch(Exception ex){
			logger.error("BcunDatasyncDAOImpl :: getReconcillationDataFromCentral()::::::", ex);
			throw new CGSystemException(ex);
		}finally{
			closeSession(session);
		}
		logger .debug("BcunDatasyncDAOImpl: getReconcillationDataFromCentral(): END");
		return reconcillationDos;
	}
	@Override
	public List<? extends CGBaseDO> getReconcillationDataForBlobCreation(
			ReconcillationConfigPropertyTO configPropertyTO)
					throws CGSystemException {
		logger .debug("BcunDatasyncDAOImpl: getReconcillationDataForBlobCreation(): START");
		Session session = null;
		Query query=null;
		List<? extends CGBaseDO> reconcillationBlobsDos=null;
		try {
			session=createSession();
			query=session.getNamedQuery(configPropertyTO.getBlobPreparationQuery());
			query.setParameter("transactionOfficeId",configPropertyTO.getTransactionOfficeId());
			query.setParameter("transactionDate",configPropertyTO.getTransactionDate());
			query.setResultTransformer(Transformers.aliasToBean(Class.forName(configPropertyTO.getBlobPreparationDomain())));
			reconcillationBlobsDos=query.list();
			logger .debug("---Count of rows for reconcillation------"+reconcillationBlobsDos.size());
		}catch(Exception ex){
			logger.error("BcunDatasyncDAOImpl :: getReconcillationDataForBlobCreation()::::::", ex);
			throw new CGSystemException(ex);
		}finally{
			closeSession(session);
		}
		logger .debug("BcunDatasyncDAOImpl: getReconcillationDataForBlobCreation(): END");
		return reconcillationBlobsDos;
	}
	@Override
	public boolean executeReleasedSQLScripts(String sqlQuery)
			throws CGSystemException {
		logger .debug("BcunDatasyncDAOImpl: runOneByOneSQLScripts(): START");
		Query query=null;
		Session session=null;
		boolean isExecute=false;
		Transaction tx = null;
		try{
			session=createSession();
			tx = session.beginTransaction();
			query=session.createSQLQuery(sqlQuery);
			query.executeUpdate();
			tx.commit();
			isExecute=true;
			
		}catch(Exception e){
			if(tx!=null) tx.rollback();
			logger.error("BcunDatasyncDAOImpl :: runOneByOneSQLScripts()::::::", e);
			throw new CGSystemException(e);
		}finally{
			closeSession(session);
		}
		logger .debug("BcunDatasyncDAOImpl: runOneByOneSQLScripts(): END");
		return isExecute;
	}
}
