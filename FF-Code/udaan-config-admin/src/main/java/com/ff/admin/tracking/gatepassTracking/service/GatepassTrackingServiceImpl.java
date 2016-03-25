package com.ff.admin.tracking.gatepassTracking.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.tracking.gatepassTracking.converter.GatePassTrackingConverter;
import com.ff.admin.tracking.gatepassTracking.dao.GatepassTrackingDAO;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingGatepassTO;

public class GatepassTrackingServiceImpl implements GatepassTrackingService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GatepassTrackingServiceImpl.class);

	private GatepassTrackingDAO gatepassTrackingDAO;

	/**
	 * @param gatepassTrackingDAO
	 *            the gatepassTrackingDAO to set
	 */
	public void setGatepassTrackingDAO(GatepassTrackingDAO gatepassTrackingDAO) {
		this.gatepassTrackingDAO = gatepassTrackingDAO;
	}

	@Override
	public TrackingGatepassTO viewTrackInformation(String number, String type)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("GatepassTrackingServiceImpl::viewTrackInformation()::START");
		TrackingGatepassTO trackingGatepassTO = null;
		if (type.equalsIgnoreCase("GP")) {
			List<LoadMovementDO> loadMovementDOList = gatepassTrackingDAO
					.getGatePassDeatils(number);
			if(StringUtil.isEmptyInteger( loadMovementDOList.size())){
				throw new CGBusinessException(AdminErrorConstants.LOAD_MOVEMENT_DETAILS_NOT_FOUND);
			}
			trackingGatepassTO = convertLoadMovementDoToTrackingTo(loadMovementDOList);
		} else if (type.equalsIgnoreCase("CRAWB")) {
			List<LoadMovementDO> loadMovementDOList = gatepassTrackingDAO
					.getCRAWBDeatils(number);
			if(StringUtil.isEmptyInteger(loadMovementDOList.size())){
				throw new CGBusinessException(AdminErrorConstants.TRACKING_DETAILS_NOT_FOUND);
			}else if(loadMovementDOList.size() > 2){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.MULTIPLE_RESULTS_FOUND, new String[]{"CD/RR/AWB No", "Gate Pass No"});
			}
			trackingGatepassTO = convertLoadMovementDoToTrackingTo(loadMovementDOList);
		}
		LOGGER.trace("GatepassTrackingServiceImpl::viewTrackInformation()::END");
		return trackingGatepassTO;
	}

	private TrackingGatepassTO convertLoadMovementDoToTrackingTo( List<LoadMovementDO> loadMovementDOList) throws CGBusinessException {
		List<ManifestTO> manifestTOs = new ArrayList<>();
		TrackingGatepassTO trackingGatepassTO = new TrackingGatepassTO();
		ManifestTO manifestTO = null;
		NumberFormat df = new DecimalFormat("#0.000");
		Set<LoadConnectedDO> dispLoadConnectedDOs = null;
		Set<LoadConnectedDO> rcvLoadConnectedDOs = null;
		List<String> manifestNos = new ArrayList<>();
		int bagsdispatch = 0;
		int bagsreceive = 0;
		if (loadMovementDOList != null) {
			for (LoadMovementDO loadMovement : loadMovementDOList) {
				if (loadMovement.getMovementDirection().equalsIgnoreCase("D")) {
					bagsdispatch = loadMovement.getLoadConnectedDOs().size();
					trackingGatepassTO.setBagsDispatch(bagsdispatch);
					trackingGatepassTO.setDispatchDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(loadMovement.getLoadingDate()));
					LoadMovementTO loadMovementTO = GatePassTrackingConverter.convertLoadMovementDO(loadMovement,trackingGatepassTO.getLoadMovementTO());
					trackingGatepassTO.setLoadMovementTO(loadMovementTO);
					dispLoadConnectedDOs = loadMovement
							.getLoadConnectedDOs();
					Double weight = 0.0;
					for (LoadConnectedDO loadConnectedDO : dispLoadConnectedDOs) {
						weight = weight + loadConnectedDO.getDispatchWeight();
					}
					if(!StringUtil.isEmptyDouble(weight)){
						trackingGatepassTO.setDispatchWt(df.format(weight));
					}
				}

				if (loadMovement.getMovementDirection().equalsIgnoreCase("R")) {					
					trackingGatepassTO.setReceiveDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(loadMovement.getLoadingDate()));
					LoadMovementTO loadMovementTO = GatePassTrackingConverter
							.convertLoadMovementDO(loadMovement,trackingGatepassTO.getLoadMovementTO());
					trackingGatepassTO.setLoadMovementTO(loadMovementTO);
					rcvLoadConnectedDOs = loadMovement
							.getLoadConnectedDOs();
					Double weight = 0.0;
					//In case receive type is out station received status is always null. 
					if(StringUtils.equalsIgnoreCase("O", loadMovement.getReceiveType())){
						for (LoadConnectedDO loadConnectedDO : rcvLoadConnectedDOs) {
							bagsreceive = bagsreceive + 1;
							weight = weight + loadConnectedDO.getDispatchWeight();
							manifestTO = GatePassTrackingConverter.convertCDRAWBManifest(loadConnectedDO,manifestTOs,"O");
							manifestNos.add(manifestTO.getManifestNumber());
							manifestTOs.add(manifestTO);
						}
					}else{
						for (LoadConnectedDO loadConnectedDO : rcvLoadConnectedDOs) {
							if(StringUtils.equalsIgnoreCase("R", loadConnectedDO.getReceivedStatus()) || StringUtils.equalsIgnoreCase("E", loadConnectedDO.getReceivedStatus())){
								bagsreceive = bagsreceive + 1;
								weight = weight + loadConnectedDO.getDispatchWeight();
							}
							manifestTO = GatePassTrackingConverter.convertCDRAWBManifest(loadConnectedDO,manifestTOs,"R");
							manifestNos.add(manifestTO.getManifestNumber());
							manifestTOs.add(manifestTO);
						}
					}
					
					trackingGatepassTO.setBagsReceive(bagsreceive);
					if(!StringUtil.isEmptyDouble(weight)){
						trackingGatepassTO.setReceiveWt(df.format(weight));
					}
				}
			}
			//To show the bags which are dispatched but not received..
			if(bagsreceive < bagsdispatch){
				for (LoadConnectedDO loadConnectedDO : dispLoadConnectedDOs) {
					if(!manifestNos.contains(loadConnectedDO.getManifestDO().getManifestNo())){
						manifestTO = GatePassTrackingConverter.convertCDRAWBManifest(loadConnectedDO,manifestTOs,"D");
						manifestTOs.add(manifestTO);
					}
				}
			}
			Collections.sort(manifestTOs);
			trackingGatepassTO.setManifestTOs(manifestTOs);
		}
		return trackingGatepassTO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GatepassTrackingServiceImpl::getTypeName()::START");
		List<StockStandardTypeTO> stockTpeList = null;
		List<StockStandardTypeDO> stockTpeDOList = gatepassTrackingDAO
				.getTypeName();
		if(StringUtil.isEmptyList(stockTpeDOList)){
			throw new CGBusinessException(AdminErrorConstants.NO_STOCK_TYPE);
		}
		stockTpeList = (List<StockStandardTypeTO>) CGObjectConverter
				.createTOListFromDomainList(stockTpeDOList,
						StockStandardTypeTO.class);
		LOGGER.trace("GatepassTrackingServiceImpl::getTypeName()::END");
		return stockTpeList;
	}
}
