package com.ff.admin.tracking.gatepassTracking.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
//import com.ff.admin.tracking.manifestTracking.dao.ManifestTrackingDAOImpl;

public class GatepassTrackingDAOImpl extends CGBaseDAO implements GatepassTrackingDAO {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GatepassTrackingDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<LoadMovementDO> getGatePassDeatils(String number)throws CGSystemException {
		LOGGER.trace("GatepassTrackingDAOImpl::getGatePassDeatils()::START");
		List<LoadMovementDO> loadMovementDO = null;
		try {
			loadMovementDO = getHibernateTemplate().findByNamedQueryAndNamedParam(AdminSpringConstants.GET_GATEPASS_DETAILS,AdminSpringConstants.GATEPASS_NO,number);
		} catch (Exception e) {
			LOGGER.error("GatepassTrackingDAOImpl :: getGatePassDeatils():: ERROR ::::"
					,e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GatepassTrackingDAOImpl::getGatePassDeatils()::END");
		return loadMovementDO;
	}
	
	public List<LoadMovementDO> getCRAWBDeatils(String number)
			throws CGSystemException {
		LOGGER.trace("GatepassTrackingDAOImpl::getCRAWBDeatils()::START");
		List<LoadMovementDO> loadMovementDOList = null;
		try {
			loadMovementDOList = getHibernateTemplate().findByNamedQueryAndNamedParam("trackByTokenNumber","tokenNumber", number);
		} catch (Exception e) {
			LOGGER.error(
					"GatepassTrackingDAOImpl::Error occured in getCRAWBDeatils()..:",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GatepassTrackingDAOImpl::getCRAWBDeatils()::END");
		return loadMovementDOList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeDO> getTypeName() throws CGSystemException {
		LOGGER.trace("GatepassTrackingDAOImpl::getTypeName()::START");
		List<StockStandardTypeDO> stockTypeDOList = null;
		try {
			stockTypeDOList = getHibernateTemplate().findByNamedQueryAndNamedParam(AdminSpringConstants.QRY_GET_MANIFEST_Type,"Type",AdminSpringConstants.GATEPASS_TYPE);
		} catch (Exception e) {
			LOGGER.error("ERROR :: GatepassTrackingDAOImpl :: getTypeName()::::::"
					,e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GatepassTrackingDAOImpl::getTypeName()::END");
		return stockTypeDOList;
	}
}
