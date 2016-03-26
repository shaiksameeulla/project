package com.ff.web.loadmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.loadmanagement.LoadReceiveEditBagTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.web.loadmanagement.dao.LoadManagementDAO;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;

/**
 * The Class LoadReceiveEditBagServiceImpl.
 */
public class LoadReceiveEditBagServiceImpl implements LoadReceiveEditBagService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadReceiveEditBagServiceImpl.class);
	
	/** The load management dao. */
	private LoadManagementDAO loadManagementDAO;
	
	/** The load management common service. */
	private LoadManagementCommonService loadManagementCommonService;
	
	/**
	 * Gets the load management dao.
	 *
	 * @return the loadManagementDAO
	 */
	public LoadManagementDAO getLoadManagementDAO() {
		return loadManagementDAO;
	}
	
	/**
	 * Sets the load management dao.
	 *
	 * @param loadManagementDAO the loadManagementDAO to set
	 */
	public void setLoadManagementDAO(LoadManagementDAO loadManagementDAO) {
		this.loadManagementDAO = loadManagementDAO;
	}
	
	/**
	 * Gets the load management common service.
	 *
	 * @return the loadManagementCommonService
	 */
	public LoadManagementCommonService getLoadManagementCommonService() {
		return loadManagementCommonService;
	}
	
	/**
	 * Sets the load management common service.
	 *
	 * @param loadManagementCommonService the loadManagementCommonService to set
	 */
	public void setLoadManagementCommonService(
			LoadManagementCommonService loadManagementCommonService) {
		this.loadManagementCommonService = loadManagementCommonService;
	}
	
	/**
	 * get LoadReceiveEditBagTO by loadNumber.
	 *
	 * @param validationTO the validation to
	 * @return LoadReceiveEditBagTO :: If all the validations are passed then return LoadReceiveEditBagTO will
	 * get filled with all the details required for Load Receive Edit Bag.
	 * else throw CGBusinessException OR display appropriate message on screen.
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author hkansagr
	 */
	@Override
	public LoadReceiveEditBagTO findLoadReceiveEditBagDetails(final LoadReceiveValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LoadReceiveEditBagServiceImpl :: getBagDetails() :: START --------> ::::::");
		LoadReceiveEditBagTO loadReceiveEditBagTO = null;
		LoadConnectedDO loadConnectedDO = null;
		try	{
			loadConnectedDO = loadManagementDAO.findLoadReceiveEditBagDetails(validationTO);
			if(loadConnectedDO!=null){
				loadReceiveEditBagTO = new LoadReceiveEditBagTO();
				loadReceiveEditBagTO = loadReceiveEditBagTrasnferObjConvertor(loadConnectedDO);
			}/* else {
				throw new CGBusinessException(); //FIXME remove this later if not required
			}*/
		} catch(Exception e) {
			LOGGER.error("Exception Occured in LoadReceiveEditBagServiceImpl :: getBagDetails() :: "
					, e);
		}
		LOGGER.debug("LoadReceiveEditBagServiceImpl :: getBagDetails() :: END --------> ::::::");
		return loadReceiveEditBagTO;
	}
	
	/**
	 * convert LoadConnectedDO to LoadReceiveEditBagTO.
	 *
	 * @param domain the domain
	 * @return LoadReceiveEditBagTO
	 * @throws Exception the exception
	 * @author hkansagr
	 */
	private LoadReceiveEditBagTO loadReceiveEditBagTrasnferObjConvertor(final LoadConnectedDO domain)  {
		LoadReceiveEditBagTO to = null;
		to = new LoadReceiveEditBagTO();
		to.setLoadConnectedId(domain.getLoadConnectedId());
		to.setLockNumber(domain.getLockNumber());
		to.setRemarks(domain.getRemarks());
		if(domain.getLoadMovementDO()!=null){
			to.setLoadMovementId(domain.getLoadMovementDO().getLoadMovementId());
		}
		to.setWeight(domain.getDispatchWeight());
		to.setTokenNumber(domain.getTokenNumber());
		if(domain.getManifestDO()!=null){
			to.setManifestId(domain.getManifestDO().getManifestId());			
			to.setLoadNumber(domain.getManifestDO().getManifestNo());
			to.setManifestWeight(domain.getManifestDO().getManifestWeight());
			if(domain.getManifestDO().getDestinationCity()!=null){
				to.setManifestDestCity(domain.getManifestDO().getDestinationCity().getCityName());
			}
		}
		to.setTransportNumber(domain.getRecvTransportNumber());
		to.setVendor(domain.getRecvVendorName());
		if(domain.getRecvTransportModeDO()!=null){
			to.setTransportModeId(domain.getRecvTransportModeDO().getTransportModeId());
			to.setTransportMode(domain.getRecvTransportModeDO().getTransportModeDesc());
		}
		to.setGatePassNumber(domain.getRecvGatepassNumber());

		return to;
	}

	/**
	 * Update weight, lockNumber, transportNumber by loadConnectedId. To achieve this,
	 * the following service is to be called:
	 *
	 * @param to the to
	 * @return if all these details updated successfully return true else return false
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author hkansagr
	 */
	@Override
	public Boolean saveOrUpdateLoadReceiveEditBagDetails(final LoadReceiveEditBagTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LoadReceiveEditBagServiceImpl :: saveOrUpdateEditBagDetails() :: Start --------> ::::::");
		Boolean result = Boolean.FALSE;
		LoadConnectedDO loadConnectedDO = null; 
		try {
			if(to!=null) {
				//WeightTolerance is not required
				/*if(to.getWeightTolerance().equals(CommonConstants.YES)){
					ManifestTO manifestTO = new ManifestTO();
					manifestTO.setManifestId(to.getManifestId());
					manifestTO.setManifestWeight(to.getWeight());

					//Manifest Weight update is not required after discussion even greater than 10% tolerance
					//loadManagementCommonService.updateManifestWeight(manifestTO);
				}*/
				loadConnectedDO = loadReceiveEditBagDomainObjConvertor(to);
				result = loadManagementDAO.saveOrUpdateLoadReceiveEditBagDetails(loadConnectedDO);
				loadManagementDAO.updateLoadMovementDtToCentral(loadConnectedDO.getLoadMovementDO());
			}
		} catch(Exception e) {
			LOGGER.error("Exception Occured in LoadReceiveEditBagServiceImpl :: saveOrUpdateEditBagDetails() :: " 
					+ e);
		}
		LOGGER.debug("LoadReceiveEditBagServiceImpl :: saveOrUpdateEditBagDetails() :: End --------> ::::::");
		return result;
	}
	
	/**
	 * convert LoadReceiveEditBagTO to LoadConnectedDO.
	 *
	 * @param to the to
	 * @return LoadConnectedDO
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @author hkansagr
	 */
	private LoadConnectedDO loadReceiveEditBagDomainObjConvertor(final LoadReceiveEditBagTO to) 
			throws CGBusinessException, CGSystemException {
		LoadConnectedDO loadConnectedDO = null;
		loadConnectedDO = new LoadConnectedDO();
		//InManifestUtils.setCreatedByUpdatedBy(loadConnectedDO);		
		loadConnectedDO.setLoadConnectedId(to.getLoadConnectedId());
		LoadManagementUtils.setUpdateFlag4DBSync(loadConnectedDO);//setting dbsync flag
		loadConnectedDO.setLockNumber(to.getLockNumber()); //modifying
		loadConnectedDO.setRemarks(to.getRemarks());
		if(!StringUtil.isEmptyInteger(to.getLoadMovementId())) {
			LoadMovementDO loadMovementDO = new LoadMovementDO();
			loadMovementDO.setLoadMovementId(to.getLoadMovementId());
			loadConnectedDO.setLoadMovementDO(loadMovementDO);
		}
		loadConnectedDO.setDispatchWeight(to.getWeight()); //modifying
		loadConnectedDO.setTokenNumber(to.getTokenNumber());
		if(!StringUtil.isEmptyInteger(to.getManifestId())) {
			ManifestDO manifestDO = new ManifestDO();
			manifestDO.setManifestId(to.getManifestId());
			loadConnectedDO.setManifestDO(manifestDO);
		}
		loadConnectedDO.setRecvTransportNumber(to.getTransportNumber()); //modifying
		loadConnectedDO.setRecvVendorName(to.getVendor());		
		if(!StringUtil.isEmptyInteger(to.getTransportModeId())) {
			TransportModeDO mode = new TransportModeDO();
			mode.setTransportModeId(to.getTransportModeId());
			loadConnectedDO.setRecvTransportModeDO(mode);
		}
		loadConnectedDO.setRecvGatepassNumber(to.getGatePassNumber());
		return loadConnectedDO;
	}
	
}
