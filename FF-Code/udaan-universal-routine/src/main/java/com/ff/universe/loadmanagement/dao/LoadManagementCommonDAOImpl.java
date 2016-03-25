package com.ff.universe.loadmanagement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.universe.loadmanagement.constant.LoadManagementUniversalConstants;

/**
 * The Class LoadManagementCommonDAOImpl.
 *
 * @author narmdr
 */
public class LoadManagementCommonDAOImpl extends CGBaseDAO implements
		LoadManagementCommonDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadManagementCommonDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadMovementDOByGatePassNumber(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadMovementDO getLoadMovementDOByGatePassNumber(
			String gatePassNumber) throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementDOByGatePassNumber() :: Start --------> ::::::");
		Session session = null;
		LoadMovementDO loadMovementDO = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			List<LoadMovementDO> loadMovementDOList = 
					(List<LoadMovementDO>)session.createCriteria(LoadMovementDO.class)
					.add(Restrictions.eq(LoadManagementUniversalConstants.GATE_PASS_NUMBER_PARAM, gatePassNumber))
					.add(Restrictions.eq(LoadManagementUniversalConstants.MOVEMENT_DIRECTION_PARAM, 
							LoadManagementUniversalConstants.DISPATCH_DIRECTION))					
					.addOrder(Order.desc("loadingDate"))
					.setMaxResults(1)
					.list();

			if(!StringUtil.isEmptyList(loadMovementDOList)){
				loadMovementDO = loadMovementDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadMovementDOByGatePassNumber() :: " , e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementDOByGatePassNumber() :: End --------> ::::::");
		return loadMovementDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadConnectedDOByManifestNoOfficeIdMovementDirection(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO getLoadConnectedDOByManifestNoOfficeIdMovementDirection(
			String manifestNumber, Integer officeId,
			String movementDirection) throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnectedDOByManifestNoOfficeIdMovementDirection() :: Start --------> ::::::");
		Session session = null;
		LoadConnectedDO loadConnectedDO = null;
		try{
			session = getHibernateTemplate().getSessionFactory().openSession();
			List<LoadConnectedDO> loadConnectedDOList = 
					(List<LoadConnectedDO>)session.createCriteria(LoadConnectedDO.class)
					.createAlias("manifestDO", "manifest")
					.createAlias("loadMovementDO", "loadMovement")
					.add(Restrictions.eq("manifest.manifestNumber", manifestNumber))
					.add(Restrictions.eq("loadMovement.movementDirection", movementDirection))
					.add(Restrictions.eq("loadMovement.originOfficeDO.officeId", officeId))
					.list();
			if(loadConnectedDOList!=null && loadConnectedDOList.size()>0){
				loadConnectedDO = loadConnectedDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadConnectedDOByManifestNoOfficeIdMovementDirection() :: " , e);
			throw new CGSystemException(e);
		}
		finally{
			closeSession(session);
		}		
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnectedDOByManifestNoOfficeIdMovementDirection() :: End --------> ::::::");
		return loadConnectedDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadMovementForReceiveLocalByDispatch(com.ff.loadmanagement.LoadManagementValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadMovementDO getLoadMovementForReceiveLocalByDispatch(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementForReceiveLocalByDispatch() :: Start --------> ::::::");
		try {
			List<LoadMovementDO> loadMovementDOs = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
					LoadManagementUniversalConstants.
					GET_LOAD_MOVEMENT_FOR_RECEIVE_LOCAL_BY_DISPATCH_QUERY, 
					new String[]{
							LoadManagementUniversalConstants.GATE_PASS_NUMBER_PARAM,
							LoadManagementUniversalConstants.REGIONAL_OFFICE_ID_PARAM,
							LoadManagementUniversalConstants.LOGGED_IN_OFFICE_ID_PARAM
							}, 
					new Object[]{
							loadManagementValidationTO.getGatePassNumber(),
							loadManagementValidationTO.getRegionalOfficeId(),
							loadManagementValidationTO.getLoggedInOfficeId()
							});

			if (loadMovementDOs != null && loadMovementDOs.size() > 0) {
				return loadMovementDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadMovementForReceiveLocalByDispatch() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementForReceiveLocalByDispatch() :: End --------> ::::::");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadMovementForReceiveLocalByReceive(com.ff.loadmanagement.LoadManagementValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadMovementDO getLoadMovementForReceiveLocalByReceive(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementForReceiveLocalByDispatch() :: Start --------> ::::::");
		try {
			List<LoadMovementDO> loadMovementDOs = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
					LoadManagementUniversalConstants.
					GET_LOAD_MOVEMENT_FOR_RECEIVE_LOCAL_BY_RECEIVE_QUERY,
					new String[]{
							LoadManagementUniversalConstants.GATE_PASS_NUMBER_PARAM,
							LoadManagementUniversalConstants.REGIONAL_OFFICE_ID_PARAM
							}, 
					new Object[]{
							loadManagementValidationTO.getGatePassNumber(),
							loadManagementValidationTO.getRegionalOfficeId()
							});

			if (loadMovementDOs != null && loadMovementDOs.size() > 0) {
				return loadMovementDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadMovementForReceiveLocalByDispatch() :: " , e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadMovementForReceiveLocalByDispatch() :: End --------> ::::::");
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadConnectedDO(com.ff.loadmanagement.ManifestTO, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO getLoadConnectedDO(ManifestTO manifestTO,
			String movementDirection) throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnectedDO() :: Start --------> ::::::");
		LoadConnectedDO loadConnectedDO = null;
		try{
			List<LoadConnectedDO> loadConnectedDOList = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.GET_LOAD_CONNECTED_FOR_MANIFEST_QUERY, 
					new String[]{
							LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM, 
							LoadManagementUniversalConstants.ORIGIN_OFFICE_ID_PARAM, 
							LoadManagementUniversalConstants.MOVEMENT_DIRECTION_PARAM}, 
					new Object[]{
							manifestTO.getManifestNumber(),
							manifestTO.getOriginOfficeTO().getOfficeId(),
							movementDirection});

			if(loadConnectedDOList!=null && loadConnectedDOList.size()>0){
				loadConnectedDO = loadConnectedDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadConnectedDO() :: " , e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnectedDO() :: End --------> ::::::");
		return loadConnectedDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadConnected4ReceiveLocalByReceiveManifestNo(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO getLoadConnected4ReceiveLocalByReceiveManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveLocalByReceiveManifestNo() :: Start --------> ::::::");
		LoadConnectedDO loadConnectedDO = null;
		try{
			List<LoadConnectedDO> loadConnectedDOList = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.
							GET_LOAD_CONNECTED_4_RECEIVE_LOCAL_BY_RECEIVE_MANIFEST_NO_QUERY, 
					new String[]{
							LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM, 
							LoadManagementUniversalConstants.LOAD_MOVEMENT_ID_PARAM}, 
					new Object[]{
							loadReceiveManifestValidationTO.getManifestNumber(),
							loadReceiveManifestValidationTO.getLoadMovementId()});

			if(loadConnectedDOList!=null && loadConnectedDOList.size()>0){
				loadConnectedDO = loadConnectedDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadConnected4ReceiveLocalByReceiveManifestNo() :: " , e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveLocalByReceiveManifestNo() :: End --------> ::::::");
		return loadConnectedDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadConnected4ReceiveLocalByDispatchManifestNo(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO getLoadConnected4ReceiveLocalByDispatchManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveLocalByDispatchManifestNo() :: Start --------> ::::::");
		LoadConnectedDO loadConnectedDO = null;
		try{
			List<LoadConnectedDO> loadConnectedDOList = 
					getHibernateTemplate().findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.
							GET_LOAD_CONNECTED_4_RECEIVE_LOCAL_BY_DISPATCH_MANIFEST_NO_QUERY, 
					new String[]{
							LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM, 
							LoadManagementUniversalConstants.LOAD_MOVEMENT_ID_PARAM}, 
					new Object[]{
							loadReceiveManifestValidationTO.getManifestNumber(),
							loadReceiveManifestValidationTO.getReceivedAgainstId()});

			if(loadConnectedDOList!=null && loadConnectedDOList.size()>0){
				loadConnectedDO = loadConnectedDOList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadConnected4ReceiveLocalByDispatchManifestNo() :: " , e);
			throw new CGSystemException(e);
		}		
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveLocalByDispatchManifestNo() :: End --------> ::::::");
		return loadConnectedDO;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#isManifestReceived(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isManifestReceived(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: isManifestReceived() :: Start --------> ::::::");
		try {
			List<Object> isReceivedSize = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.IS_MANIFEST_RECEIVED_QUERY,
							new String[] {
									LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM,
									LoadManagementUniversalConstants.DEST_OFFICE_ID_PARAM },
							new Object[] {
									loadReceiveManifestValidationTO
											.getManifestNumber(),
									loadReceiveManifestValidationTO
											.getDestOfficeId() });

			if (!StringUtil.isEmptyColletion(isReceivedSize)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::isManifestReceived() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: isManifestReceived() :: End --------> ::::::");
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#isReceiveNumberExist(com.ff.loadmanagement.LoadReceiveValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isReceiveNumberExist(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: isReceiveNumberExist() :: Start --------> ::::::");
		try {
			List<Integer> loadMovementIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.IS_RECEIVE_NUMBER_EXIST_QUERY,
							LoadManagementUniversalConstants.RECEIVE_NUMBER_PARAM,
							loadReceiveValidationTO.getReceiveNumber());
			if (!StringUtil.isEmptyColletion(loadMovementIds)) {
				return true;
			}

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::isReceiveNumberExist() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: isReceiveNumberExist() :: End --------> ::::::");
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#isManifestReceived4Outstation(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isManifestReceived4Outstation(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: isManifestReceived4Outstation() :: Start --------> ::::::");
		try {
			List<Object> loadConnectedIds = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.IS_MANIFEST_RECEIVED_FOR_OUTSTATION_QUERY,
							new String[] {
									LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM,
									LoadManagementUniversalConstants.DEST_OFFICE_ID_PARAM },
							new Object[] {
									loadReceiveManifestValidationTO
											.getManifestNumber(),
									loadReceiveManifestValidationTO
											.getDestOfficeId() });

			if (!StringUtil.isEmptyColletion(loadConnectedIds)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::isManifestReceived4Outstation() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: isManifestReceived4Outstation() :: End --------> ::::::");
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ff.universe.loadmanagement.dao.LoadManagementCommonDAO#getLoadConnected4ReceiveOutstationByDispatchManifestNo(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoadConnectedDO getLoadConnected4ReceiveOutstationByDispatchManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException {
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveOutstationByDispatchManifestNo() :: Start --------> ::::::");
		LoadConnectedDO loadConnectedDO = null;
		try {
			List<LoadConnectedDO> loadConnectedDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LoadManagementUniversalConstants.GET_LOAD_CONNECTED_4_RECEIVE_OUTSTATION_BY_DISPATCH_MANIFEST_NO_QUERY,
							LoadManagementUniversalConstants.MANIFEST_NUMBER_PARAM,
							loadReceiveManifestValidationTO.getManifestNumber());

			if (loadConnectedDOList != null && loadConnectedDOList.size() > 0) {
				loadConnectedDO = loadConnectedDOList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoadManagementCommonDAOImpl::getLoadConnected4ReceiveOutstationByDispatchManifestNo() :: "
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoadManagementCommonDAOImpl :: getLoadConnected4ReceiveOutstationByDispatchManifestNo() :: End --------> ::::::");
		return loadConnectedDO;
	}

}
