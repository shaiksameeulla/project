/**
 * 
 */
package com.ff.web.drs.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.web.drs.scheduler.dao.DrsConsignmentUpdateDAO;


/**
 * @author mohammes
 *
 */

public class DrsConsignmentUpdateServiceImpl implements
DrsConsignmentUpdateService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DrsConsignmentUpdateServiceImpl.class);

	/** The drs consignment update dao. */
	DrsConsignmentUpdateDAO drsConsignmentUpdateDAO;


	/**
	 * @return the drsConsignmentUpdateDAO
	 */
	public DrsConsignmentUpdateDAO getDrsConsignmentUpdateDAO() {
		return drsConsignmentUpdateDAO;
	}
	/**
	 * @param drsConsignmentUpdateDAO the drsConsignmentUpdateDAO to set
	 */
	public void setDrsConsignmentUpdateDAO(
			DrsConsignmentUpdateDAO drsConsignmentUpdateDAO) {
		this.drsConsignmentUpdateDAO = drsConsignmentUpdateDAO;
	}




	/**
	 * 
	 */
	@Override
	public void updateDeliveredChildConsg() throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredChildConsg :: updateDeliveredChildConsg ::START");
		try {
			List<DeliveryDetailsDO> deliveredChildCN=drsConsignmentUpdateDAO.getDeliveredChildConsgDtls();
			if(!CGCollectionUtils.isEmpty(deliveredChildCN)){

				modifyChildConsignmentStatus(deliveredChildCN);
				LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredChildConsg :: method ::updateDeliveredParentConsg Executed");
			}else{
				LOGGER.warn("DrsConsignmentUpdateServiceImpl ::updateDeliveredChildConsg :: NO Data Found from DB");
			}
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateServiceImpl ::updateDeliveredChildConsg :: Exception",e);
			throw e;
		}
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredChildConsg :: updateDeliveredChildConsg ::END");
	}
	/**
	 * @throws CGSystemException
	 */
	@Override
	public void updateDeliveredParentConsg()  throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredConsignment :: updateDeliveredParentConsg ::START");
		try {
			List<DeliveryDetailsDO> deliveredCN=drsConsignmentUpdateDAO.getDeliveredParentConsgDtls();

			if(!CGCollectionUtils.isEmpty(deliveredCN)){
				modifyParentConsignmentStatus(deliveredCN);
				LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredParentConsg :: method ::modifyParentConsignmentStatus Executed");
			}else{
				LOGGER.warn("DrsConsignmentUpdateServiceImpl ::updateDeliveredParentConsg :: NO Data Found from DB");
			}
		} catch (Exception e) {
			LOGGER.error("DrsConsignmentUpdateServiceImpl ::updateDeliveredParentConsg :: Exception",e);
			throw e;
		}

		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::updateDeliveredConsignment :: updateDeliveredParentConsg ::END");
	}

	/**
	 * @param deliveredCN
	 * @param trkingProcessList
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public boolean modifyParentConsignmentStatus(
			List<DeliveryDetailsDO> deliveredCN)
					throws CGSystemException, CGBusinessException {
		Boolean result=false;

		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::modifyParentConsignmentStatus:: updateDeliveredParentConsg :: START");

		drsConsignmentUpdateDAO.updateConsgDetails(deliveredCN);
		result=true;
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::modifyParentConsignmentStatus:: updateDeliveredParentConsg :: END");
		return result;
	}
	/**
	 * @param deliveredCN
	 * @throws CGSystemException
	 */
	@Deprecated
	public void updateConsignmentDtlsWithPincode(
			List<DeliveryDetailsDO> deliveredCN) throws CGSystemException {
		List<ConsignmentDO> destinationMismatchDO=new ArrayList<ConsignmentDO>(deliveredCN.size());
		for(DeliveryDetailsDO deliveryDtlDO: deliveredCN){
			ConsignmentDO consg= getConsignmentForMismatchDrsPincode(deliveryDtlDO);
			if(consg!=null){
				destinationMismatchDO.add(consg);
			}
		}
		if(!CGCollectionUtils.isEmpty(destinationMismatchDO)){
			drsConsignmentUpdateDAO.updateConsgDetailsWithPincode(destinationMismatchDO);
		}
	}
	@Override
	public boolean modifyChildConsignmentStatus(
			List<DeliveryDetailsDO> deliveredCN
			)
					throws CGSystemException, CGBusinessException {
		Boolean result=false;
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::modifyChildConsignmentStatus:: updateDeliveredParentConsg :: START");
		drsConsignmentUpdateDAO.updateConsgDetails(deliveredCN);
		result=true;
		LOGGER.debug("DrsConsignmentUpdateServiceImpl ::modifyChildConsignmentStatus:: updateDeliveredParentConsg :: END");
		return result;
	}


	public ConsignmentDO getConsignmentForMismatchDrsPincode(
			DeliveryDetailsDO deliveryDetailDO) throws CGSystemException {
		ConsignmentDO consigDO=null;
		if(!deliveryDetailDO.getConsignmentDO().getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS) && !deliveryDetailDO.getConsignmentDO().getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
			Integer deliveryCity = deliveryDetailDO.getDeliveryDO().getCreatedOfficeDO().getCityId();
			Integer actualDestinationCity = deliveryDetailDO.getConsignmentDO().getDestPincodeId()!=null ?deliveryDetailDO.getConsignmentDO().getDestPincodeId().getCityId():null;
			if(deliveryCity!=null && actualDestinationCity!=null  && deliveryCity.intValue() != actualDestinationCity.intValue()){
				//check whether it's delivered by tranship city

				Integer transhipmentId=	drsConsignmentUpdateDAO.getGetTranshipBytranscityAndDrsCity(deliveryCity, actualDestinationCity);
				if(StringUtil.isEmptyInteger(transhipmentId)){
					//if delivery office is not a transhipment office then update consignment table with delivery pincode
					//getPincodeByCityId
					PincodeDO pincodeDO=drsConsignmentUpdateDAO.getPincodeByCityId(deliveryCity);
					if(pincodeDO!=null){
						consigDO=deliveryDetailDO.getConsignmentDO();
						consigDO.setDestPincodeId(pincodeDO);
					}
				}
			}
		}
		return consigDO;
	}

}
