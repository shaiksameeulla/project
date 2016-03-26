package com.ff.umc.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.umc.service.UserManagementCommonService;

public class AssignApproverCommonServiceImpl implements AssignApproverCommonService {
	
	private OrganizationCommonService organizationCommonService;
	private GeographyCommonService geographyCommonService;
	private UserManagementCommonService umcCommonService;

	public UserManagementCommonService getUmcCommonService() {
		return umcCommonService;
	}
	public void setUmcCommonService(
			UserManagementCommonService umcCommonService) {
		this.umcCommonService = umcCommonService;
	}
	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}
	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}
	
	
	@Override
	public List<OfficeTO> getAllRegionalOffices() throws CGBusinessException,
			CGSystemException {
		return organizationCommonService.getAllRegionalOffices();
	}
	@Override
	public List<OfficeTO> getOfficesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficesByOffices(officeTOList);
	}
	
	@Override
	public List<OfficeTO> getAllOfficesByCityListExceptCOOfc(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCityListExceptCOOfc(cityTOList);
	}
	
	@Override
	public List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList) 
			throws CGBusinessException, CGSystemException{
		return geographyCommonService.getAllCitiesByCityIds(cityTOList);
	}
	@Override
	public List<CityTO> getCitiesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException {
		
		return geographyCommonService.getCitiesByOffices(officeTOList);
	}
	@Override
	public List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException {
		
		return organizationCommonService.getOfficesByCityAndRHO(cityTOList);
	}
	
	@Override
	public Map<Integer, String> getUsersByType(String userType)
			throws CGBusinessException, CGSystemException {
		return umcCommonService.getUsersByType(userType);
	}

	
	
}
