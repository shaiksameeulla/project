package com.ff.admin.tracking.gatepassTracking.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;

public class GatePassTrackingConverter {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GatePassTrackingConverter.class);
	public static final NumberFormat formatter = new DecimalFormat("#0.000");
	
	public static LoadMovementTO convertLoadMovementDO(
			LoadMovementDO loadMovementDO,LoadMovementTO loadMovementTO) throws CGBusinessException {
		LOGGER.trace("GatePassTrackingConverter::convertLoadMovementDO()::START");
		if (loadMovementDO != null) {
			if(StringUtil.isNull(loadMovementTO)){
				loadMovementTO = new LoadMovementTO();
			} 
			OfficeTO originOffice = new OfficeTO();
			OfficeDO originOffice1 = new OfficeDO();
			OfficeTO destOffice = new OfficeTO();
			OfficeDO destOffice1 = new OfficeDO();
			originOffice1 = loadMovementDO.getOriginOfficeDO();
			if (originOffice1 != null) {
				CGObjectConverter.createToFromDomain(originOffice1,
						originOffice);
				loadMovementTO.setOriginOffice(originOffice.getOfficeName());
			}

			destOffice1 = loadMovementDO.getDestOfficeDO();
			if (destOffice1 != null) {
				CGObjectConverter.createToFromDomain(destOffice1, destOffice);
				loadMovementTO.setDestOffice(destOffice.getOfficeName());
			}
			loadMovementTO.setLoadingTime(loadMovementDO.getLoadingDate()
					.toString());
			if(!StringUtil.isNull(loadMovementDO.getTransportModeDO())){
				loadMovementTO.setTransportMode(loadMovementDO.getTransportModeDO()
						.getTransportModeDesc());
			}
			String transportNumber = getTransportNo(loadMovementDO);
			if(StringUtils.isNotEmpty(transportNumber)){
				loadMovementTO.setVehicleNumber(transportNumber);
			}			
		}
		LOGGER.trace("GatePassTrackingConverter::convertLoadMovementDO()::END");
		return loadMovementTO;
	}
	private static String getTransportNo(LoadMovementDO loadMovement) {
		String transportNumber = "";
		if(StringUtils.isNotEmpty(loadMovement.getVehicleRegNumber())){
			transportNumber = loadMovement.getVehicleRegNumber();
		}else if(!StringUtil.isNull(loadMovement.getVehicleDO())){
			transportNumber = loadMovement.getVehicleDO().getRegNumber();
		}else if(!StringUtil.isNull(loadMovement.getTripServicedByDO()) 
				&& !StringUtil.isNull(loadMovement.getTripServicedByDO().getTripDO()) 
				&& !StringUtil.isNull(loadMovement.getTripServicedByDO().getTripDO().getTransportDO())){
			if(!StringUtil.isNull(loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getFlightDO())){
				transportNumber = loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getFlightDO().getFlightNumber();
			}else if(!StringUtil.isNull(loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getTrainDO())){
				transportNumber = loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getTrainDO().getTrainNumber();
			}else if(!StringUtil.isNull(loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getVehicleDO())){
				transportNumber = loadMovement.getTripServicedByDO().getTripDO().getTransportDO().getVehicleDO().getRegNumber();
			}
		}
		return transportNumber;
	}
	public static List<ManifestTO> convertGatepassManifest(
			List<ManifestDO> manifestDOs) throws CGBusinessException {
		LOGGER.trace("GatePassTrackingConverter::convertGatepassManifest()::START");
		ManifestTO manifestTO = null;
		List<ManifestTO> detailsTO = new ArrayList<>(manifestDOs.size());
		for (ManifestDO manifestDO : manifestDOs) {
			manifestTO = new ManifestTO();
			manifestTO.setManifestNumber(manifestDO.getManifestNo());
			if(!StringUtil.isEmptyDouble(manifestDO.getManifestWeight())){
				manifestTO.setMnfstWeight(formatter.format(manifestDO.getManifestWeight()));
			}
			manifestTO.setManifestStatus(manifestDO.getManifestStatus());
			detailsTO.add(manifestTO);
		}
		LOGGER.trace("GatePassTrackingConverter::convertGatepassManifest()::END");
		return detailsTO;

	}

	public static ManifestTO convertCDRAWBManifest(ManifestDO manifestDO)
			throws CGBusinessException {
		LOGGER.trace("GatePassTrackingConverter::convertCDRAWBManifest()::START");
		ManifestTO manifestTO = null;
		manifestTO = new ManifestTO();
		manifestTO.setManifestNumber(manifestDO.getManifestNo());
		if(!StringUtil.isEmptyDouble(manifestDO.getManifestWeight())){
			manifestTO.setMnfstWeight(formatter.format(manifestDO.getManifestWeight()));
		}
		manifestTO.setManifestStatus(manifestDO.getManifestStatus());
		LOGGER.trace("GatePassTrackingConverter::convertCDRAWBManifest()::END");
		return manifestTO;
	}
	public static ManifestTO convertCDRAWBManifest(LoadConnectedDO loadConnectedDO, List<ManifestTO> manifestTOs, String direction)
			throws CGBusinessException {
		LOGGER.trace("GatePassTrackingConverter::convertCDRAWBManifest()::START");
		ManifestTO manifestTO = new ManifestTO();
		ManifestDO manifestDO = loadConnectedDO.getManifestDO();					
		manifestTO.setManifestNumber(manifestDO.getManifestNo());
		if(!StringUtil.isEmptyDouble(loadConnectedDO.getDispatchWeight())){
			manifestTO.setMnfstWeight(formatter.format(loadConnectedDO.getDispatchWeight()));
		}
		String status = "-";
		if(StringUtils.equalsIgnoreCase(direction,"O")){
			status = "Recieved"; 
			direction = "R";
		}else if(StringUtils.equalsIgnoreCase(direction,"R")){
			if(StringUtils.equalsIgnoreCase(loadConnectedDO.getReceivedStatus(),"R")){
				status = "Recieved"; 
			}else if(StringUtils.equalsIgnoreCase(loadConnectedDO.getReceivedStatus(),"N")){
				status = "Not Recieved"; 
			}else if(StringUtils.equalsIgnoreCase(loadConnectedDO.getReceivedStatus(),"E")){
				status = "Excess"; 
			}
		}else if(StringUtils.equalsIgnoreCase(direction,"D")){
			status = "Dispatched";
		}
		manifestTO.setManifestStatus(status);
		manifestTO.setManifestType(direction);
		manifestTO.setRemarks(loadConnectedDO.getRemarks());
		
		LOGGER.trace("GatePassTrackingConverter::convertCDRAWBManifest()::END");
		return manifestTO;
	}
	
}
