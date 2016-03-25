package com.ff.admin.master.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ProductGroupServiceabilityTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.master.PinCodeMasterTO;
import com.ff.organization.OfficeTO;

public interface PincodeMasterService {

	List<RegionTO> getRegions() throws CGBusinessException, CGSystemException;

	List<StateTO> getStatesByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException;

	List<CityTO> getCitysByStateId(Integer stateId) throws CGBusinessException,
			CGSystemException;
	
	List<CityTO> getCitysByStateIdAndRegionID(Integer stateId,Integer regionId) throws CGBusinessException,
	CGSystemException;

	List<ProductTO> getAllProduct() throws CGBusinessException,
			CGSystemException;

	List<ProductGroupServiceabilityTO> getAllProductGroup()
			throws CGBusinessException, CGSystemException;


	List<OfficeTO> getBranchesByCity(Integer regionid,Integer officeTypeId) throws CGBusinessException, CGSystemException;
	
	public List<CNPaperWorksTO> getAllPaperWorks() throws CGSystemException,CGBusinessException;
	
	public boolean savePincodeMaster(PinCodeMasterTO pincodeTO);

	public PinCodeMasterTO searchPincodeDetails(String pincodeNO);
	
	public boolean activateDeactivatePincode(PinCodeMasterTO pincodeTo);
	
	public List<CityTO> getAllCities() throws CGSystemException,CGBusinessException;

}
