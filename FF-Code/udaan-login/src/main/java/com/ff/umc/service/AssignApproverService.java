package com.ff.umc.service;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.AssignApproverTO;
import com.ff.umc.EmployeeUserTO;

/**
 * Author : Rohini Maladi
 * @Class : AssignApproverService
 * @Desc : Services for  UMC - Assign Approver process
 * Creation Date : Feb - 19 - 2013
 */

public interface AssignApproverService {

List<LabelValueBean> getAllRegionalOffices() throws CGBusinessException, CGSystemException;

List<ApplScreensTO> getAllAssignApplScreens() throws CGBusinessException, CGSystemException;	
EmployeeUserTO getEmpDetails(String userName, String userId) throws CGBusinessException, CGSystemException;
List<CityTO> getStationsByRegionalOffices(String[] regionalOfficeIdsList) throws CGBusinessException, CGSystemException;
List<OfficeTO> getOfficesByCityList(String[] citiIdsList, List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;
String saveOrUpdateAssignApprover(AssignApproverTO assignApproverTO) throws CGBusinessException, CGSystemException;
AssignApproverTO getAssignDetails(Integer userId) throws CGBusinessException, CGSystemException;
List<CityTO> getCitiesByOffices(Integer[] officeIdsList, List<OfficeTO> officeTOList) throws CGBusinessException, CGSystemException;
List<CityTO> getCitiesByCityIds(Integer[]  cityAry) throws CGBusinessException, CGSystemException;
List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException;
Map<Integer, String> getUsersByType(String userType) throws CGBusinessException, CGSystemException;
@SuppressWarnings("rawtypes")
List getAssignApproverDetails(String userName, String userId) throws CGBusinessException, CGSystemException;
}
