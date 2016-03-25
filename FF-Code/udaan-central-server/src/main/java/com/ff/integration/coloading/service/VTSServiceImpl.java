package com.ff.integration.coloading.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.integration.coloading.dao.ColoadingCentralDAO;

/**
 * The Class ColoadingUniversalServiceImpl.
 * 
 * @author narmdr
 */
public class VTSServiceImpl implements VTSService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VTSServiceImpl.class);
	
	private ColoadingCentralDAO coloadingCentralDAO;

	/**
	 * @param coloadingCentralDAO the coloadingCentralDAO to set
	 */
	public void setColoadingCentralDAO(ColoadingCentralDAO coloadingCentralDAO) {
		this.coloadingCentralDAO = coloadingCentralDAO;
	}

	@Override
	public String validateAndSaveVTS(String vehicleRegNumber, String date,
			Integer ot, Integer openingKm, Integer closingKm, Integer officeId)
			throws CGBusinessException, CGSystemException {
		//vehicleRegNumber, date, ot, openingKm, closingKm, officeId

		String message = null;
		LOGGER.trace("ColoadingUniversalServiceImpl::validateAndSaveVTS::START------------>:::::::");

		//Validate All mandatory fields.
		message = validateMandatoryVTSFields(vehicleRegNumber, date, ot, openingKm, closingKm, officeId);
		if(StringUtils.isNotBlank(message)){
			return message;
		}
		boolean isVehicleContract = coloadingCentralDAO
				.isVehicleContractExist(date, vehicleRegNumber);
		ColoadingVehicleServiceEntryDO coloadingVehicleServiceEntryDO = prepareVehicleServiceEntryDO(vehicleRegNumber, date, ot, openingKm, closingKm, officeId);
		if (isVehicleContract) {
			coloadingCentralDAO
					.saveVehicleServiceEntryDO(coloadingVehicleServiceEntryDO);
			message = "VTS info saved successfully.";
		} else {
			message = "Vehicle Contract does not exist.";
		}
		LOGGER.trace("ColoadingUniversalServiceImpl::validateAndSaveVTS::END------------>:::::::");
		return message;
	}

	private String validateMandatoryVTSFields(String vehicleRegNumber,
			String date, Integer ot, Integer openingKm, Integer closingKm,
			Integer officeId) {
		StringBuilder errorMsg = new StringBuilder();
		List<String> fields = new ArrayList<>();
		if(StringUtils.isBlank(vehicleRegNumber)){
			fields.add("Vehicle Number");
		}
		if(StringUtils.isBlank(date)){
			fields.add("Date");
		}
		if(StringUtil.isEmptyInteger(ot)){
			fields.add("OT");
		}
		if(StringUtil.isEmptyInteger(openingKm)){
			fields.add("Opening Km");
		}
		if(StringUtil.isEmptyInteger(closingKm)){
			fields.add("Closing Km");
		}
		if(StringUtil.isEmptyInteger(officeId)){
			fields.add("OfficeId");
		}
		if(!StringUtil.isEmptyList(fields)){
			errorMsg.append("Please Enter mandatory fields for VTS : ");
			errorMsg.append(fields);
			return errorMsg.toString();
		}
		return null;
	}

	private ColoadingVehicleServiceEntryDO prepareVehicleServiceEntryDO(String vehicleRegNumber, String date,
			Integer ot, Integer openingKm, Integer closingKm, Integer officeId) {
		ColoadingVehicleServiceEntryDO vehicleServiceEntryDO = new ColoadingVehicleServiceEntryDO();
		vehicleServiceEntryDO.setDate(DateUtil
				.slashDelimitedstringToDDMMYYYYFormat(date));
		vehicleServiceEntryDO.setVehNumber(vehicleRegNumber);
		vehicleServiceEntryDO.setOt(ot);
		vehicleServiceEntryDO.setOpeningKm(openingKm);
		vehicleServiceEntryDO.setClosingKm(closingKm);
		vehicleServiceEntryDO.setOfficeId(officeId);

		vehicleServiceEntryDO.setCreatedBy(2);
		vehicleServiceEntryDO.setUpdatedBy(2);
		vehicleServiceEntryDO.setUpdatedDate(DateUtil.getCurrentDate());
		vehicleServiceEntryDO.setCreatedDate(DateUtil.getCurrentDate());

		return vehicleServiceEntryDO;
	}
}
