package com.ff.admin.master.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;

public interface VendorMappingService {
	
	public LoadMovementVendorTO getVendorDetails(String vendorName)throws CGBusinessException,
	CGSystemException;
   
	public List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException;
	
	public List<OfficeTO> getOfficesByCityId(final Integer cityId)
			throws CGBusinessException, CGSystemException;
	
	public Boolean saveOrUpdateVendor(LoadMovementVendorTO vendorTO)throws CGBusinessException,
	CGSystemException;
	
	public boolean saveOrUpdateVendorOffice(LoadMovementVendorTO vendorTO,Integer regionId,List<Integer> officeList,List<OfficeTO> officeTOBeRemovedList)throws CGBusinessException,
	CGSystemException;
	
	public List<String> getAllVendorsList()throws CGBusinessException, CGSystemException;
	
//	getting All selected offices against city of that vendor
	public List<OfficeTO> getSelectedOffices(Integer regionId,Integer cityId,Integer vendorId)throws CGBusinessException, CGSystemException;
}
