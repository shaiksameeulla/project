
package com.ff.web.loadmanagement.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;

/**
 * The Class LoadManagementDAOImpl.
 *
 * @author narmdr
 */
public class LoadManagementDAOImpl extends CGBaseDAO implements
		LoadManagementDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadManagementDAOImpl.class);	

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#saveOrUpdateLoadMovement(com.ff.domain.loadmanagement.LoadMovementDO)
	 */
	@Override
	public LoadMovementDO saveOrUpdateLoadMovement(LoadMovementDO loadMovementDO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: saveOrUpdateLoadMovement() :: Start --------> ::::::");
		try{
			getHibernateTemplate().saveOrUpdate(loadMovementDO);
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementDAOImpl::saveOrUpdateLoadMovement() :: " , e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("LoadManagementDAOImpl :: saveOrUpdateLoadMovement() :: End --------> ::::::");
		return loadMovementDO;
	}


	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#deleteLoadConnectedDOsByLoadConnectedIdList(java.util.List)
	 */
	@Override
	public boolean deleteLoadConnectedDOsByLoadConnectedIdList(
			List<Integer> loadConnectedIdList) throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: deleteLoadConnectedDOsByLoadConnectedIdList() :: Start --------> ::::::");
		Session session = null;
		//Transaction tx = null;
		boolean isDeleted = Boolean.FALSE;
		try{
			//session = getHibernateTemplate().getSessionFactory().openSession();
			session = openTransactionalSession();
			//tx = session.beginTransaction();
			
			Query query = session.getNamedQuery(
					LoadManagementConstants.DELETE_LOAD_CONNECTED_BY_ID_LIST);
			query.setParameterList(LoadManagementConstants.LOAD_CONNECTED_ID_LIST_PARAM,
					loadConnectedIdList);
			int deletedRows = query.executeUpdate();

			if(deletedRows>0){
				isDeleted = Boolean.TRUE;
			}			
			//tx.commit();
		}
		catch(Exception e){
			//tx.rollback();
			LOGGER.error("Exception Occured in::LoadManagementDAOImpl::deleteLoadConnectedDOsByLoadConnectedIdList() :: " , e);
			throw new CGSystemException(e);
		}
		finally{
			//closeSession(session);
			closeTransactionalSession(session);
		}		
		LOGGER.debug("LoadManagementDAOImpl :: deleteLoadConnectedDOsByLoadConnectedIdList() :: End --------> ::::::");
		return isDeleted;		
	}

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#getLoadConnectedFromDispatchAsNotReceived(com.ff.loadmanagement.LoadReceiveValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadConnectedDO> getLoadConnectedFromDispatchAsNotReceived(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedFromDispatchAsNotReceived() :: Start --------> ::::::");
		List<LoadConnectedDO> loadConnectedDOList = null;
		try {
			loadConnectedDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementConstants.GET_LOAD_CONNECTED_FROM_DISPATCH_AS_NOT_RECEIVED_QUERY,
							new String[] {
									LoadManagementConstants.LOAD_MOVEMENT_ID_PARAM,
									LoadManagementConstants.MANIFEST_NUMBER_LIST_PARAM },
							new Object[] {
									loadReceiveValidationTO
											.getReceivedAgainstId(),
									loadReceiveValidationTO
											.getManifestNumberList() });

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementDAOImpl::getLoadConnectedFromDispatchAsNotReceived() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedFromDispatchAsNotReceived() :: End --------> ::::::");
		return loadConnectedDOList;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#findLoadReceiveEditBagDetails(com.ff.loadmanagement.LoadReceiveValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO findLoadReceiveEditBagDetails(LoadReceiveValidationTO validationTO) 
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedByManifestNo() :: Start --------> ::::::");
		List<LoadConnectedDO> loadConnectedDOList = null;
		try {
			String params[] = new String[]{LoadManagementConstants.MANIFEST_NUMBER_PARAM};
			Object values[] = new Object[]{validationTO.getManifestNumber()};
			loadConnectedDOList = getHibernateTemplate().
				findByNamedQueryAndNamedParam(LoadManagementConstants.GET_LOAD_CONNECTED_BY_MANIFEST_NO_QUERY, params, values);
			
		} catch (Exception e) {
			LOGGER.error("Exception Occured in LoadManagementDAOImpl :: getLoadConnectedFromDispatchAsNotReceived() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedByManifestNo() :: End --------> ::::::");
		return (!StringUtil.isEmptyList(loadConnectedDOList))?loadConnectedDOList.get(0):null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#getLoadConnectedIdsAsNotReceived(com.ff.loadmanagement.LoadReceiveValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLoadConnectedIdsAsNotReceived(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedIdsAsNotReceived() :: Start --------> ::::::");
		List<Integer> loadConnectedIds = null;
		try {
			loadConnectedIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementConstants.GET_LOAD_CONNECTED_IDS_AS_NOT_RECEIVED_QUERY,
							LoadManagementConstants.LOAD_MOVEMENT_ID_PARAM,
							loadReceiveValidationTO.getLoadMovementId());

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementDAOImpl::getLoadConnectedIdsAsNotReceived() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementDAOImpl :: getLoadConnectedIdsAsNotReceived() :: End --------> ::::::");
		return loadConnectedIds;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.dao.LoadManagementDAO#saveOrUpdateLoadReceiveEditBagDetails(com.ff.domain.loadmanagement.LoadConnectedDO)
	 */
	@Override
	public Boolean saveOrUpdateLoadReceiveEditBagDetails(LoadConnectedDO loadConnectedDO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: saveOrUpdateLoadReceiveEditBagDetails() :: Start --------> ::::::");
		Boolean result = Boolean.FALSE;
		try {
			getHibernateTemplate().saveOrUpdate(loadConnectedDO);
			result = Boolean.TRUE;
		} catch(Exception e) {
			LOGGER.error("Exception Occured in LoadManagementDAOImpl :: saveOrUpdateLoadReceiveEditBagDetails() :: " 
					, e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("LoadManagementDAOImpl :: saveOrUpdateLoadReceiveEditBagDetails() :: End --------> ::::::");
		return result;
	}


	@Override
	public void updateLoadMovementDtToCentral(LoadMovementDO loadMovementDO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementDAOImpl :: updateLoadMovementDtToCentral() :: Start --------> ::::::");
		Session session = null;
		try {
			session = openTransactionalSession();
			Query query = session
					.getNamedQuery(LoadManagementConstants.QRY_UPDATE_LOAD_MOVEMENT_DT_TO_CENTRAL);
			query.setInteger(LoadManagementConstants.LOAD_MOVEMENT_ID_PARAM,
					loadMovementDO.getLoadMovementId());
			query.executeUpdate();

		} catch (Exception e) {
			LOGGER.error("Exception Occured in LoadManagementDAOImpl :: updateLoadMovementDtToCentral() :: "
					, e);
			throw new CGSystemException(e);
		} finally {
			closeTransactionalSession(session);
		}
		LOGGER.debug("LoadManagementDAOImpl :: updateLoadMovementDtToCentral() :: End --------> ::::::");
	}
	
}
