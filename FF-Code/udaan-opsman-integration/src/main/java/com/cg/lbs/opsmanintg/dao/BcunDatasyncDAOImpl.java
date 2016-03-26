package com.cg.lbs.opsmanintg.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.opsmanintg.constant.BcunConstant;
import com.cg.lbs.opsmanintg.constant.BcunDataFormaterConstants;
import com.cg.lbs.opsmanintg.to.OutboundBranchDataTO;
import com.cg.lbs.opsmanintg.to.TwoWayWriteTO;
import com.ff.domain.bcun.OutboundOfficePacketDO;
import com.ff.domain.booking.BcunBookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.OpsmanManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.universe.booking.constant.UniversalBookingConstants;
import com.opsmanintg.constant.OpsmanConstant;

/**
 * @author mohammal Jan 15, 2013
 * 
 */
public class BcunDatasyncDAOImpl extends CGBaseDAO implements BcunDatasyncDAO {

	private static final Logger logger = Logger
			.getLogger(BcunDatasyncDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer)
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
				logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::setting default row count====>"
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
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			q = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					,e);
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
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getOfficeDataByNamedQueryAndRowCount(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<CGBaseDO> getOfficeDataByNamedQueryAndRowCount(String namedQuery, Integer rowCount, Integer officeId) 
	{
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::start====>");
		List<CGBaseDO> unSyncList = null;
		try 
		{
			if (officeId == null || officeId == 0)
				return null;
			//Setting default row count if not configured in properties file
			if (rowCount == null) 
			{
				rowCount = BcunConstant.BCUN_DEFAULT_MAX_ROW_FETCH_COUNT;
				logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::setting default row count====>" + rowCount);
			}
			String paramName = "officeId";
			//Fetching data from database based on parameters
			unSyncList = getDataByNamedQueryNamedParamAndRowCount(namedQuery,paramName, officeId, rowCount);
		} 
		catch (Exception e) 
		{
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"	,e);
			throw e;
		}
		return unSyncList;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getDataByNamedQueryNamedParamAndRowCount(java.lang.String, java.lang.String, java.lang.Object, java.lang.Integer)
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
		try 
		{
			//Opening session
			session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			query.setMaxResults(rowCount);
			query.setParameter(paramName, paramValue);
			//Getting result set from query
			unSyncList = query.list();
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					,e);
			throw e;
		} finally {
			//Closing session if not closed
			//closeTransactionalSession(session);
			closeSession(session);
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
			session=openTransactionalSession();
			//session = template.getSessionFactory().openSession();
			//Creating query from session
			Query query = session.getNamedQuery(namedQuery);
			//Setting max number of row which has to be fetched from query
			query.setMaxResults(rowCount);
			query.setParameter(paramName, paramValue);
			//Getting result set from query
			unSyncList = query.list();
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					,e);
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
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getDataByNamedQuery(java.lang.String)
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
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndNamedParam(String namedQuery,
			String param, Object value) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery,
				param, value);
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getDataByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
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
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#saveOrUpdateTransferedEntity(com.capgemini.lbs.framework.domain.CGBaseDO)
	 */
	@Override
	public void saveOrUpdateTransferedEntity(CGBaseDO baseEntity) 
	{
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::start====>");
		if (baseEntity == null) {
			logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::entity which has to update should not be null....");
			return;
		}
		//Saving/Updating entity if not saved
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try
		{
			logger.debug("BcunDatasyncDAOImpl::saveOrUpdateTransferedEntity::saving entity ["+ baseEntity.getClass().getName() + "]");
			session.saveOrUpdate(baseEntity);
			tx.commit();
		}
		catch(Exception e)
		{
			logger.error("BcunDatasyncDAOImpl::updateTransferedEntity::ERROR\n",e);
			tx.rollback();
			throw e;
		}
		finally
		{
			if(!StringUtil.isNull(session))
			{
				try
				{
					//session.flush();
					session.close();
				}
				catch(Exception e)
				{
					logger.error("BcunDatasyncDAOImpl::updateTransferedEntity::ERROR\n",e);
				}
			}
		}
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::end====>");
		//throw new RuntimeException("Testing.......");
	}
	
	@Override
	public void saveDrsData(CGBaseDO baseEntity) 
	{
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::start====>");
		if (baseEntity == null) {
			logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::entity which has to update should not be null....");
			return;
		}
		//Saving/Updating entity if not saved
		//Session session = getHibernateTemplate().getSessionFactory().openSession();
		try
		{
			logger.debug("BcunDatasyncDAOImpl::saveOrUpdateTransferedEntity::saving entity ["+ baseEntity.getClass().getName() + "]");
			getHibernateTemplate().saveOrUpdate(baseEntity);
			getHibernateTemplate().flush();
			//session.saveOrUpdate(baseEntity);
		}
		catch(Exception e)
		{
			logger.error("BcunDatasyncDAOImpl::updateTransferedEntity::ERROR\n",e);
			throw e;
		}
		/*finally
		{
			if(!StringUtil.isNull(session))
			{
				try
				{
					session.close();
				}
				catch(Exception e)
				{
					logger.error("BcunDatasyncDAOImpl::updateTransferedEntity::ERROR\n",e);
				}
			}
		}*/
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntity::end====>");
		//throw new RuntimeException("Testing.......");
	}
	
	
	@Override
	public void updateEntityStatus(CGBaseDO baseEntity) {
		logger.debug("BcunDatasyncDAOImpl::updateEntityStatus::start====>");
		if (baseEntity == null) {
			logger.debug("BcunDatasyncDAOImpl::updateEntityStatus::entity which has to update should not be null....");
			return;
		}
		//Updating entity if not saved
		getHibernateTemplate().update(baseEntity);
		logger.debug("BcunDatasyncDAOImpl::updateEntityStatus::end====>");
	}

	
	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#saveOrUpdateTransferedEntities(java.util.List)
	 */
	@Override
	public void saveOrUpdateTransferedEntities(List<CGBaseDO> baseEntities) 
	{
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::start====>");
		if (baseEntities != null && !baseEntities.isEmpty()) 
		{
			logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::baseEntities::" + baseEntities);
			//Saving/Updating all entities
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			try
			{
				for (CGBaseDO baseEntity : baseEntities) 
				{
					logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::saving entity ["+ baseEntity.getClass().getName() + "]");
					session.saveOrUpdate(baseEntity);
					//session.merge(baseEntity);
				}
				tx.commit();
			}
			catch(Exception e)
			{
				logger.error("BcunDatasyncDAOImpl::updateTransferedEntities::ERROR",e);
				tx.rollback();
				throw e;
			}
			finally
			{
				if(!StringUtil.isNull(session))
				{
					try
					{
						//session.flush();
						session.close();
					}
					catch(Exception e)
					{
						logger.error("BcunDatasyncDAOImpl::updateTransferedEntities::ERROR",e);
					}
				}
			}
			//getHibernateTemplate().saveOrUpdateAll(baseEntities);
		} else {
			logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::entities list is empty or null");
		}
		logger.debug("BcunDatasyncDAOImpl::updateTransferedEntities::end====>");

	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getOutboundBranchData(com.cg.lbs.opsmanintg.to.OutboundBranchDataTO)
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
	 * @see com.cg.lbs.opsmanintg.dao.BcunDatasyncDAO#getUniqueId(java.lang.String, java.lang.String[], java.lang.Object[])
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
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"
					+ unSyncList);
			query = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::"
					,e);
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
			logger.error("BcunDatasyncDAOImpl::deleteFromDatabase::Exception::",e);
			tx.rollback();
			throw  new CGSystemException(e);
		} finally {
			//Closing session if not closed
			if (session != null)
				session.close();
			template = null;
		}
		logger.debug("BcunDatasyncDAOImpl::deleteFromDatabase::End====>");
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
				qry.setParameterList("datasyncId", packetInfo.getPacketIds());
				logger.debug("CentralDataExtractorDAOImpl : restoreDataExtractionStatusToTransmission(): END updated count:["
						+ qry.executeUpdate() + "]");
				//tx.commit();
			} catch (Exception obj) {
				logger.error("BcunDatasyncDAOImpl::updatePacketStatus::Exception occured:"
						+obj.getMessage());
				//tx.rollback();
				throw new CGSystemException(obj);
			} finally {
				closeTransactionalSession(session);
			}
					
			//getHibernateTemplate().bulkUpdate(hql, packetInfo.getListOfIds());
	}
	
	@Override
	public CGBaseDO getCGBaseDOById(TwoWayWriteTO twoWayWriteTO) throws CGSystemException {
		logger.debug("BcunDatasyncDAOImpl :: getCGBaseDOById() :: Start --------> ::::::");
		Session session = null;
		CGBaseDO cgBaseDO = null;
		try {
			session = createSession();
			//session = getSessionFactory().getCurrentSession();
			///session = openTransactionalSession();
			cgBaseDO = (CGBaseDO) session.get(twoWayWriteTO.getProcessClass(), twoWayWriteTO.getId());
			cgBaseDO.setDtToCentral(twoWayWriteTO.getDtToCentral());
			session.flush();
		} catch (Exception e) {
			logger.error("BcunDatasyncDAOImpl::getCGBaseDOById::Exception occured:" + e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
			//closeTransactionalSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl :: getCGBaseDOById() :: End --------> ::::::");
		return cgBaseDO;
	}
	
	
	@Override
	public CustomerDO getCustomerDtlsFromCustId(Integer custId) throws CGSystemException, CGBusinessException 
	{
		logger.debug("BcunDatasyncDAOImpl::getCustomerDtlsFromCustId::------------>START");
		CustomerDO custDO = null;
		String[] param = { "customerId" };
		Object[] value = { custId};
		String namedQuery = "getCustomer";
		try
		{
			List<CustomerDO> custDoList =(List<CustomerDO>)getHibernateTemplate().findByNamedQueryAndNamedParam(namedQuery, param, value);
			if(!StringUtil.isEmptyList(custDoList))
			{
				custDO = custDoList.get(0);
			}
		}
		catch(Exception e)
		{
			logger.error("Error in getCustomerDtlsFromCustId--",e);
			throw new CGSystemException(e);
		}
		logger.debug("BcunDatasyncDAOImpl::getCustomerDtlsFromCustId::------------>END");
		return custDO;
	}
	
	
	@Override
	public List<CNPaperWorksDO> getPaperWorkByPincode(String pincode) throws CGSystemException, CGBusinessException 
	{
		logger.debug("BcunDatasyncDAOImpl::getPaperWorkByPincode::------------>START");
		List<CNPaperWorksDO> cnPaperWorksDOs = null;
		try 
		{
			String queryName = UniversalBookingConstants.GET_PAPER_WORKS;
			String[] params = { "pincode"};
			Object[] values = { pincode };
			cnPaperWorksDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
		} 
		catch (Exception e) 
		{
			logger.error("ERROR : BcunDataSyncDAOImpl.getPaperWorkByPincode", e);
			throw new CGSystemException(e);
		}
		logger.debug("BcunDatasyncDAOImpl::getPaperWorkByPincode::------------>END");
		return cnPaperWorksDOs;
	}

	
	@Override
	public OfficeDO getOfficeByIdOrCode(Integer officeId, String officeCode) throws CGBusinessException, CGSystemException 
	{
		logger.debug("BcunDatasyncDAOImpl::getOfficeByIdOrCode::------------>START");
		Session session = null;
		Criteria criteria = null;
		OfficeDO office = null;
		try 
		{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(OfficeDO.class);
			if (!StringUtil.isEmptyInteger(officeId))
				criteria.add(Restrictions.eq("officeId", officeId));
			else if (StringUtils.isNotEmpty(officeCode))
				criteria.add(Restrictions.eq("officeCode", officeCode));
			if (!StringUtil.isEmptyInteger(officeId) || StringUtils.isNotEmpty(officeCode))
				office = (OfficeDO) criteria.uniqueResult();
		} 
		catch (Exception e) 
		{
			logger.error("ERROR : BcunDatasyncDAOImpl.getOfficeByIdOrCode", e);
			throw new CGSystemException(e);
		} 
		finally 
		{
			closeSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl::getOfficeByIdOrCode::------------>END");
		return office;
	}
	
	@Override
	public CityDO getCityDtlsByCityCode(String cityCode) throws CGSystemException, CGBusinessException 
	{
		logger.debug("BcunDatasyncDAOImpl::getCityDtlsByCityCode::------------>START");
		List<CityDO> cityDOs = null;
		CityDO cityDO = null;
		try 
		{
			String queryName = "getCityByCityCode";
			String[] params = { "cityCode"};
			Object[] values = { cityCode };
			cityDOs = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(cityDOs))
			{
				cityDO = cityDOs.get(0);
			}
			
		} 
		catch (Exception e) 
		{
			logger.error("ERROR : BcunDataSyncDAOImpl.getCityDtlsByCityCode", e);
			throw new CGSystemException(e);
		}
		logger.debug("BcunDatasyncDAOImpl::getCityDtlsByCityCode::------------>END");
		return cityDO;
	}
	
	@Override
	public CityDO getCityDtlsByCityIdOrCode(Integer cityId, String cityCode) throws CGBusinessException, CGSystemException 
	{
		logger.debug("BcunDatasyncDAOImpl::getCityDtlsByCityIdOrCode::------------>START");
		Session session = null;
		Criteria criteria = null;
		CityDO city = null;
		try 
		{
			session = getHibernateTemplate().getSessionFactory().openSession();
			criteria = session.createCriteria(CityDO.class);
			if (!StringUtil.isEmptyInteger(cityId))
				criteria.add(Restrictions.eq("cityId", cityId));
			else if (StringUtils.isNotEmpty(cityCode))
				criteria.add(Restrictions.eq("cityCode", cityCode));
			if (!StringUtil.isEmptyInteger(cityId) || StringUtils.isNotEmpty(cityCode))
				city = (CityDO) criteria.uniqueResult();
		} 
		catch (Exception e) 
		{
			logger.error("ERROR : BcunDatasyncDAOImpl.getCityDtlsByCityIdOrCode", e);
			throw new CGSystemException(e);
		} 
		finally 
		{
			closeSession(session);
		}
		logger.debug("BcunDatasyncDAOImpl::getCityDtlsByCityIdOrCode::------------>END");
		return city;
	}
	
	@Override
	public ConfigurableParamsDO getStockSeriesFromConfigParams(String searchParam) throws CGSystemException, CGBusinessException
	{
		logger.debug("BcunDatasyncDAOImpl::getStockSeriesFromConfigParams::------------>START");
		List<ConfigurableParamsDO> paramDoList = null;
		ConfigurableParamsDO configParamDo = null;
		try
		{
			String queryName = "getConfigurableParamsForStockSeries";
			String[] params = { "searchParam" };
			Object[] values = { searchParam };
			paramDoList = getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
			if(!StringUtil.isEmptyColletion(paramDoList))
			{
				configParamDo = paramDoList.get(0);
			}
		}
		catch(Exception e)
		{
			logger.error("ERROR : BcunDataSyncDAOImpl.getStockSeriesFromConfigParams", e);
			throw new CGSystemException(e);
		}
		logger.debug("BcunDatasyncDAOImpl::getStockSeriesFromConfigParams::------------>END");
		return configParamDo;
	}
	
	@Override
	public List<CGBaseDO> getDataByNamedQueryAndFetchingParams(String namedQuery,Integer startLimit,Integer endLimit) 
	{
		logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndFetchingParams::start====>");
		List<CGBaseDO> unSyncList = null;
		Session session = null;
		//Getting current hibernate template
		HibernateTemplate template = getHibernateTemplate();
		try 
		{
			//Setting default row count if it is null
			if (endLimit == null) 
			{
				endLimit = BcunConstant.BCUN_DEFAULT_MAX_ROW_FETCH_COUNT;
				logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndFetchingParams::setting default row count====>"+ endLimit);
			}
			//opening session from template
			session = template.getSessionFactory().openSession();
			//creating query from named query
			Query q = session.getNamedQuery(namedQuery);
			//setting row count in query
			q.setFirstResult(startLimit);
			q.setMaxResults(endLimit);
			//fetching data based on query  
			unSyncList = q.list();
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::fetched list data====>"+ unSyncList);
			q = null;
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::end====>");
		} 
		catch (Exception e) 
		{
			logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryAndRowCount::Exception::",e);
			throw e;
		} 
		finally 
		{
			//releasing session if not
			if (session != null)
				session.close();
			template = null;
		}
		return unSyncList;
	}
	
	@Override
	public List<CGBaseDO> getDataByNamedQueryNamedParamAndFetchingParams(String namedQuery,String paramName,
			Collection<Integer> paramValue,Integer startLimit,Integer rowCount)
		{
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>START");
			List<CGBaseDO> unSyncList = null;
			Session session = null;
			HibernateTemplate template = getHibernateTemplate();
			
			try
			{
				session = template.getSessionFactory().openSession();
				Query query = session.getNamedQuery(namedQuery);
				if(StringUtil.isEmptyInteger(rowCount))
				{
					rowCount = BcunConstant.BCUN_DEFAULT_MAX_ROW_FETCH_COUNT;
					logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::setting default row count::------------> ["+ rowCount + "]");
				}
				
				query.setMaxResults(rowCount);
				query.setFirstResult(startLimit);
				query.setParameterList(paramName, paramValue);
				unSyncList = query.list();
				query = null;
			}
			catch(Exception e)
			{
				logger.error("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>ERROR",e);
				throw e;
			}
			finally
			{
				//releasing session if not
				if(null != session)
					session.close();
				template = null;
			}
			logger.debug("BcunDatasyncDAOImpl::getDataByNamedQueryNamedParamAndFetchingParams::------------>END");
			return unSyncList;
		}
	
	@Override
	public List<CGBaseDO> updatePublishedEntityStatusForManifest(List<CGBaseDO> unSyncedData,String statusFlag) 
	{
		logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::------------>START");
		List<CGBaseDO> updatedList = new ArrayList<CGBaseDO>();
		if (unSyncedData == null) {
			logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::entity which has to be updated should not be null");
			return null;
		}
		//Saving/Updating entity if not saved
		Session session = null;
		Transaction tx = null;
		Query query = null;
		Integer count = 0;
		try
		{
			session = getHibernateTemplate().getSessionFactory().openSession();
			
			for(CGBaseDO baseDo : unSyncedData)
			{
				OpsmanManifestDO opsmanManifestDo = (OpsmanManifestDO)baseDo;
				try{
					tx = session.beginTransaction();
					query = session.getNamedQuery(OpsmanConstant.NAMED_QUERY_FOR_UPDATING_MANIFEST);
					query.setParameter("manifestId", opsmanManifestDo.getManifestId());
					query.setParameter("status", statusFlag);
					count = query.executeUpdate();
					tx.commit();
					updatedList.add(baseDo);
				}
				catch(Exception e){
					logger.error("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::ERROR\n",e);
					tx.rollback();
				}
			}
			logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::count of entities for which status has been updated = " + count);
		}
		catch(Exception e)
		{
			logger.error("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::ERROR\n",e);
			tx.rollback();
			throw e;
		}
		finally
		{
			if(!StringUtil.isNull(session))
			{
				try
				{
					//session.flush();
					session.close();
				}
				catch(Exception e)
				{
					logger.error("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::ERROR\n",e);
				}
			}
		}
		logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatusForManifest::------------>END");
		//throw new RuntimeException("Testing.......");
		return updatedList;
	}
	
	
	@Override
	public void updatePublishedEntityStatus(CGBaseDO baseEntity) 
	{
		logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatus::------------>START");
		if (baseEntity == null) {
			logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatus::entity which has to update should not be null");
			return;
		}
		//Saving/Updating entity if not saved
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try
		{
			logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatus::updating entity ["+ baseEntity.getClass().getName() + "]");
			session.merge(baseEntity);
			tx.commit();
		}
		catch(Exception e)
		{
			tx.rollback();
			logger.error("BcunDatasyncDAOImpl::updatePublishedEntityStatus::ERROR\n",e);
			throw e;
		}
		finally
		{
			if(!StringUtil.isNull(session))
			{
				try
				{
					//session.flush();
					session.close();
				}
				catch(Exception e)
				{
					logger.error("BcunDatasyncDAOImpl::updatePublishedEntityStatus::ERROR\n",e);
				}
			}
		}
		logger.debug("BcunDatasyncDAOImpl::updatePublishedEntityStatus::------------>END");
		//throw new RuntimeException("Testing.......");
	}
	
	@Override
	public void updateBookingStatusAsRejected(List<CGBaseDO> rejectedList, String recordStatus) {
		logger.debug("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::------------>START");
		//Saving/Updating entity if not saved
		Session session = null;
		Transaction tx = null;
		Query query = null;
		Integer count = 0;
		try
		{
			session = getHibernateTemplate().getSessionFactory().openSession();
			for(CGBaseDO baseDo : rejectedList)
			{
				BcunBookingDO bcunBookingDO = (BcunBookingDO)baseDo;
				try{
					tx = session.beginTransaction();
					query = session.getNamedQuery(OpsmanConstant.NAMED_QUERY_FOR_UPDATING_BOOKING);
					query.setParameter("bookingId", bcunBookingDO.getBookingId());
					query.setParameter("status", recordStatus);
					count = query.executeUpdate();
					tx.commit();
				}
				catch(Exception e){
					logger.error("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::ERROR\n",e);
					tx.rollback();
				}
			}
			logger.debug("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::count of entities for which status has been updated = " + count);
		}
		catch(Exception e)
		{
			logger.error("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::ERROR\n",e);
			tx.rollback();
			throw e;
		}
		finally
		{
			if(!StringUtil.isNull(session))
			{
				try
				{
					//session.flush();
					session.close();
				}
				catch(Exception e)
				{
					logger.error("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::ERROR\n",e);
				}
			}
		}
		logger.debug("BcunDatasyncDAOImpl::updateBookingStatusAsRejected::------------>END");
		//throw new RuntimeException("Testing.......");
	}
	
}
