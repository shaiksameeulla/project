package com.ff.umc.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;

public interface AssignApproverCommonService {
	List<OfficeTO> getAllRegionalOffices() throws CGBusinessException, CGSystemException;
	List<OfficeTO> getOfficesByOffices(List<OfficeTO> officeTOList) throws CGBusinessException, CGSystemException;
	List<OfficeTO> getAllOfficesByCityListExceptCOOfc(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;
	List<CityTO> getAllCitiesByCityIds(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;
	List<CityTO> getCitiesByOffices(List<OfficeTO> officeTOList) throws CGBusinessException, CGSystemException;
	List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;
	Map<Integer, String> getUsersByType(String userType)	throws CGBusinessException, CGSystemException;
}	
