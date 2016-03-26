package com.ff.umc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.ApproverUserOfficeDO;
import com.ff.domain.umc.AssignApproverDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.AssignApproverTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.AssignApproverDAO;


public class AssignApproverServiceImpl implements AssignApproverService {
	
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AssignApproverServiceImpl.class);
	private AssignApproverDAO assignApproverDAO;
	private AssignApproverCommonService assignApproverCommonService;
	private LoginService loginService;
	
	
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public AssignApproverDAO getAssignApproverDAO() {
		return assignApproverDAO;
	}
	public void setAssignApproverDAO(AssignApproverDAO assignApproverDAO) {
		this.assignApproverDAO = assignApproverDAO;
	}
	public AssignApproverCommonService getAssignApproverCommonService() {
		return assignApproverCommonService;
	}
	public void setAssignApproverCommonService(
			AssignApproverCommonService assignApproverCommonService) {
		this.assignApproverCommonService = assignApproverCommonService;
	}
	
	/** 
     * Get RegionalOffices List
     * This method will return  List of RegionalOffices 
     * @inputparam 
     * @return  	List<OfficeTO> 
     * @author      Rohini  Maladi  
     */
	@Override
	public List<LabelValueBean> getAllRegionalOffices() throws CGBusinessException, CGSystemException{
		LOGGER.trace("AssignApproverServiceImpl::getAllRegionalOffices::START------------>:::::::");
		List<LabelValueBean> offList = null;
		List<OfficeTO> regOfficeList = assignApproverCommonService.getAllRegionalOffices();
		if (!CGCollectionUtils.isEmpty(regOfficeList)) {
			offList = new ArrayList<LabelValueBean>();
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(UmcConstants.PARAM_ALL);
			lvb.setValue(UmcConstants.REGIONAL_OFFICE_CODE);
			offList.add(lvb);
			for (OfficeTO officeTO : regOfficeList) {
				lvb = new LabelValueBean();
				lvb.setLabel(officeTO.getOfficeName());
				lvb.setValue(officeTO.getOfficeId().toString());
				offList.add(lvb);
			}
		}
		LOGGER.trace("AssignApproverServiceImpl::getAllRegionalOffices::END------------>:::::::");
		return offList;
	}
	
	/** 
     * Get Application Screens List
     * This method will return  List of Appl Screens 
     * @inputparam 
     * @return  	List<ApplScreensTO> 
     * @author      Rohini  Maladi  
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplScreensTO> getAllAssignApplScreens()
			throws CGBusinessException, CGSystemException {
		
		LOGGER.trace("AssignApproverServiceImpl::getAllAssignApplScreens::START------------>:::::::");
		List<ApplScreensTO> applScreensTOs = null;
		List<ApplScreenDO> applScreensDOList = assignApproverDAO.getAllAssignApplScreens();
		
		if(applScreensDOList!=null && applScreensDOList.size()>0){
			applScreensTOs = (List<ApplScreensTO>) CGObjectConverter
					.createTOListFromDomainList(applScreensDOList, ApplScreensTO.class);
		}

		LOGGER.trace("AssignApproverServiceImpl::getAllAssignApplScreens::END------------>:::::::");
		return applScreensTOs;
	}
	
	/** 
     * Get Employee User Details
     * This method will return  Employee User details 
     * @inputparam Employee User name and Id
     * @return  	EmployeeUserTO 
     * @author      Rohini  Maladi  
     */
	@Override
	public EmployeeUserTO getEmpDetails(String userName, String userId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("AssignApproverServiceImpl::getEmpDetails::START------------>:::::::");
		EmployeeUserTO empUserTO = null;
		UserTO userTO = new UserTO();
		if(!StringUtil.isStringEmpty(userName))
		userTO.setUserName(userName);
		if(!StringUtil.isStringEmpty(userId))
		userTO.setUserId(StringUtil.parseInteger(userId));	
		
		if(!StringUtil.isNull(userTO)){
			userTO = loginService.getUserByUser(userTO);
			if(!StringUtil.isNull(userTO) && userTO.getUserType().trim().equals(UmcConstants.EMPLOYEE_CODE) && userTO.getActive().trim().equals(UmcConstants.FLAG_Y)){				
				empUserTO = loginService.getEmpUserInfo(userTO.getUserId());
				if(!StringUtil.isNull(empUserTO))
					empUserTO.setUserTO(userTO);
			}else if(!StringUtil.isNull(userTO)){
			empUserTO = new EmployeeUserTO();
			empUserTO.setUserTO(userTO);	
			}else{
				empUserTO = null;
			}
				
		}
		else{
			empUserTO = null;
		}
		LOGGER.trace("AssignApproverServiceImpl::getEmpDetails::END------------>:::::::");
		return empUserTO;
	}
	
	/** 
     * List the all cities with the By Regional Office List 
     * @inputparam  Regional offices Array
     * @return  	List<CityTO>
     * @author      Rohini  Maladi  
     */
	public List<CityTO> getStationsByRegionalOffices(String[] regionalOfficeIdsList) throws CGBusinessException, CGSystemException{
		LOGGER.trace("AssignApproverServiceImpl::getStationsByRegionalOffices::START------------>:::::::");
		List<CityTO> cityTOList = new ArrayList<CityTO>();
		if(!StringUtil.isEmpty(regionalOfficeIdsList)){
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		OfficeTO officeTO = null;
		for(int i =0;i<regionalOfficeIdsList.length;i++){
			officeTO = new OfficeTO();
			officeTO.setOfficeId(Integer.parseInt(regionalOfficeIdsList[i]));
			officeTOList.add(officeTO);
		}
		
		if(!CGCollectionUtils.isEmpty(officeTOList)){
		officeTOList = assignApproverCommonService.getOfficesByOffices(officeTOList);
		
		CityTO cityTO = null;
		for(int i =0;i<officeTOList.size();i++){
			cityTO = new CityTO();
			cityTO.setCityId(officeTOList.get(i).getCityId());
			cityTOList.add(cityTO);
		}
		if(!CGCollectionUtils.isEmpty(cityTOList)){
		cityTOList = assignApproverCommonService.getAllCitiesByCityIds(cityTOList);
		}
		else{
			cityTOList = null;
		}
		}else{
			cityTOList = null;
			}
		}
		else{
			cityTOList = null;
		}
		
		LOGGER.trace("AssignApproverServiceImpl::getStationsByRegionalOffices::END------------>:::::::");
		return cityTOList;
		
	}
	
	/** 
     * List the all Offices By Cities List
     * @inputparam  CitiIds list of Array, List<CityTO>
     * @return  	List<CityTO>
     * @author      Rohini  Maladi  
     */
	
public List<OfficeTO> getOfficesByCityList(String[] citiIdsList, List<CityTO> cityTOs) throws CGBusinessException, CGSystemException{
		LOGGER.trace("AssignApproverServiceImpl::getOfficesByCityList::START------------>:::::::");
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		
		if(CGCollectionUtils.isEmpty(cityTOs) && !StringUtil.isEmpty(citiIdsList)){
		List<CityTO> cityTOList = new ArrayList<CityTO>();
		CityTO cityTO = null;
		for(int i =0;i<citiIdsList.length;i++){
			cityTO = new CityTO();
			cityTO.setCityId(Integer.parseInt(citiIdsList[i]));
			cityTOList.add(cityTO);
		}
		
		if(!CGCollectionUtils.isEmpty(cityTOList)){
			officeTOList = assignApproverCommonService.getAllOfficesByCityListExceptCOOfc(cityTOList);
		}else{
			officeTOList = null;
		}
		}else if(!CGCollectionUtils.isEmpty(cityTOs)){
			officeTOList = assignApproverCommonService.getAllOfficesByCityListExceptCOOfc(cityTOs);
		}
		else{
			officeTOList = null;
		}
		
		LOGGER.trace("AssignApproverServiceImpl::getOfficesByCityList::END------------>:::::::");
		return officeTOList;
		
}

/** 
 * List the all Cities By Offices List
 * @inputparam  officeIds list of Array, List<OfficeTO>
 * @return  	List<CityTO>
 * @author      Rohini  Maladi  
 */
public List<CityTO> getCitiesByOffices(Integer[] officeIdsList, List<OfficeTO> officeIds) throws CGBusinessException, CGSystemException{
	LOGGER.trace("AssignApproverServiceImpl::getCitiesByOffices::START------------>:::::::");
	List<CityTO> cityTOList = new ArrayList<CityTO>();
	
	if(CGCollectionUtils.isEmpty(officeIds) && !StringUtil.isEmpty(officeIdsList)){
	List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
	OfficeTO officeTO = null;
	for(int i =0;i<officeIdsList.length;i++){
		officeTO = new OfficeTO();
		officeTO.setOfficeId(officeIdsList[i]);
		officeTOList.add(officeTO);
	}
	
	if(!CGCollectionUtils.isEmpty(officeTOList)){
		cityTOList = assignApproverCommonService.getCitiesByOffices(officeTOList);
	}
	else{
		cityTOList = null;
	}
	}else if(!CGCollectionUtils.isEmpty(officeIds)){
		cityTOList = assignApproverCommonService.getCitiesByOffices(officeIds);
	}
	else{
		cityTOList = null;
	}
	LOGGER.trace("AssignApproverServiceImpl::getCitiesByOffices::END------------>:::::::");
	return cityTOList;
	
}

/** 
 * Save Approver Rights Details
 * This method save the Approver Rights Details in database
 * @inputparam  AssignApproverTO Object
 * @return  	Status Message
 * @author      Rohini  Maladi  
 */
public String saveOrUpdateAssignApprover(AssignApproverTO assignApproverTO) throws CGBusinessException, CGSystemException{
	
	LOGGER.trace("AssignApproverServiceImpl::saveOrUpdateAssignApprover::START------------>:::::::");
	boolean assignStatus = Boolean.FALSE;
	String status = CommonConstants.FAILURE;
	if(!StringUtil.isNull(assignApproverTO)){
	
		List<AssignApproverDO> assignApproverDOList = new ArrayList<AssignApproverDO>();
		List<ApproverUserOfficeDO> approverUserOfficeDOList = new ArrayList<ApproverUserOfficeDO>();
		domainConverter(assignApproverTO, assignApproverDOList, approverUserOfficeDOList);
		if(!CGCollectionUtils.isEmpty(assignApproverDOList) && !CGCollectionUtils.isEmpty(approverUserOfficeDOList)){
			assignStatus = assignApproverDAO.saveOrUpdateAssignApprover(assignApproverDOList,approverUserOfficeDOList);
		}if(assignStatus){
			status = CommonConstants.SUCCESS;
		}
		
	
	}
	LOGGER.trace("AssignApproverServiceImpl::saveOrUpdateAssignApprover::END------------>:::::::");
	return status;
	
	
}

/** 
 * Convert TO object to DO Object
 * This method Converts AssignApproverTO object to AssignApproverDO object and provides List of AssignApproverDO
 * @inputparam  AssignApproverTO Object
 * @return  	List<AssignApproverDO>
 * @author      Rohini  Maladi  
 */
	private void domainConverter(AssignApproverTO assignApproverTO, List<AssignApproverDO> assignApproverDOList, List<ApproverUserOfficeDO> approverUserOfficeDOList){
				
		LOGGER.trace("AssignApproverServiceImpl::domainConverter::START------------>:::::::");
		AssignApproverDO assignApproverDO = null;
		ApproverUserOfficeDO approverUserOfficeDO = null;
		String mappedTo = "";
		Integer userId = null;
		String[] officeId = null;
		String[] cityId = null;
		String[] regionalOfficeId = null;
		String[] screenId = null;
		
		userId  = assignApproverTO.getUserId();
		
		if(!StringUtil.isStringEmpty(assignApproverTO.getOfficeIdsStr())){
			if(assignApproverTO.getOfficeIdsStr().trim().equals(UmcConstants.OFFICE_CODE)){
				mappedTo = assignApproverTO.getOfficeIdsStr();
				//if(!StringUtil.isNull(assignApproverTO.getCityIdsStr())){
				if(assignApproverTO.getCityIdsStr().trim().equals(UmcConstants.STATION_CODE)){
					mappedTo =  assignApproverTO.getCityIdsStr();
					//if(!StringUtil.isNull(assignApproverTO.getRegionalOfficeIdsStr())){
					if(assignApproverTO.getRegionalOfficeIdsStr().trim().equals(UmcConstants.REGIONAL_OFFICE_CODE)){
						mappedTo =  assignApproverTO.getRegionalOfficeIdsStr();
					}else{
						regionalOfficeId = assignApproverTO.getRegionalOfficeIdsStr().split(CommonConstants.COMMA);
					}
					//}
				}else{
					cityId = assignApproverTO.getCityIdsStr().split(CommonConstants.COMMA);
				}
				//}
			}else{
				officeId = assignApproverTO.getOfficeIdsStr().split(CommonConstants.COMMA);
			}
		}
		
		if(!StringUtil.isEmpty(officeId) && officeId.length>0){
			for(int i = 0; i< officeId.length;i++){
			approverUserOfficeDO =  new ApproverUserOfficeDO();
			approverUserOfficeDO.setUser(userId);
			approverUserOfficeDO.setOffice(StringUtil.parseInteger(officeId[i]));
			approverUserOfficeDOList.add(approverUserOfficeDO);
		}
		}else if(!StringUtil.isEmpty(cityId) && mappedTo.trim().equals(UmcConstants.OFFICE_CODE) && cityId.length>0){
			for(int i = 0; i< cityId.length;i++){
			approverUserOfficeDO =  new ApproverUserOfficeDO();		
			approverUserOfficeDO.setUser(userId);
			approverUserOfficeDO.setMappedTo(mappedTo);
			approverUserOfficeDO.setCity(StringUtil.parseInteger(cityId[i]));
			approverUserOfficeDOList.add(approverUserOfficeDO);
			}
		}else if(!StringUtil.isEmpty(regionalOfficeId) && mappedTo.trim().equals(UmcConstants.STATION_CODE) && regionalOfficeId.length>0){
			for(int i = 0; i< regionalOfficeId.length;i++){
			approverUserOfficeDO =  new ApproverUserOfficeDO();		
			approverUserOfficeDO.setUser(userId);
			approverUserOfficeDO.setMappedTo(mappedTo);
			approverUserOfficeDO.setRegionalOffice(StringUtil.parseInteger(regionalOfficeId[i]));
			approverUserOfficeDOList.add(approverUserOfficeDO);
			}
		}else if(mappedTo.trim().equals(UmcConstants.REGIONAL_OFFICE_CODE) || mappedTo.trim().equals(UmcConstants.OFFICE_CODE) || mappedTo.trim().equals(UmcConstants.STATION_CODE)){
			approverUserOfficeDO =  new ApproverUserOfficeDO();
			approverUserOfficeDO.setUser(userId);
			approverUserOfficeDO.setMappedTo(mappedTo);
			approverUserOfficeDOList.add(approverUserOfficeDO);
		}
		
		if(!StringUtil.isStringEmpty(assignApproverTO.getScreenIdsStr())){
			screenId = assignApproverTO.getScreenIdsStr().split(CommonConstants.COMMA);
			for(int i = 0; i<screenId.length; i++){
				assignApproverDO =  new AssignApproverDO();
				assignApproverDO.setUser(userId);
				assignApproverDO.setScreen(StringUtil.parseInteger(screenId[i]));
				assignApproverDOList.add(assignApproverDO);
			}
				
		}
		
		LOGGER.trace("AssignApproverServiceImpl::domainConverter::END------------>:::::::");
	}
	
	/** 
     * Load Approver Rights details into TO object
     * This method get the Approver Rights details using userId of approver
     * @inputparam  userId Integer
     * @return  	AssignApproverTO
     * @author      Rohini  Maladi  
     */
	public AssignApproverTO getAssignDetails(Integer userId) throws CGBusinessException, CGSystemException{
		
		LOGGER.trace("AssignApproverServiceImpl::getAssignDetails::START------------>:::::::");
		AssignApproverTO assignApproverTO = new AssignApproverTO();
		
		assignApproverTO.setUserId(userId);
		
		List<AssignApproverDO> assignApproverDOList = null;
		List<ApproverUserOfficeDO> approverUserOfficeDOList = null;
		
		assignApproverDOList = assignApproverDAO.getAssignApproverDetails(assignApproverTO);
		approverUserOfficeDOList = assignApproverDAO.getApproverUserOfficeDetails(assignApproverTO);
		
		int len = approverUserOfficeDOList.size();
		Integer[] officeAry = new Integer[len];
		Integer[] cityAry = new Integer[len];
		Integer[] regOfficeAry = new Integer[len];
		String mappingTo = "";
		
		int i = 0;
		for(ApproverUserOfficeDO approverUserOfficeDO : approverUserOfficeDOList){
			if(StringUtil.isStringEmpty(approverUserOfficeDO.getMappedTo())){
				officeAry[i] = approverUserOfficeDO.getOffice();
			}else if(approverUserOfficeDO.getMappedTo().trim().equals(UmcConstants.OFFICE_CODE)){
				mappingTo = approverUserOfficeDO.getMappedTo();
				cityAry[i] = approverUserOfficeDO.getCity();
			}else if(approverUserOfficeDO.getMappedTo().trim().equals(UmcConstants.STATION_CODE)){
				mappingTo = approverUserOfficeDO.getMappedTo();
				regOfficeAry[i] = approverUserOfficeDO.getRegionalOffice();
			}else if(approverUserOfficeDO.getMappedTo().trim().equals(UmcConstants.REGIONAL_OFFICE_CODE)){
				mappingTo = approverUserOfficeDO.getMappedTo();
			}
			i++;
		}
		
		i = 0;
		len = assignApproverDOList.size();
		Integer[] applScreenAry = new Integer[len];
		for(AssignApproverDO assignApproverDO : assignApproverDOList){			
			applScreenAry[i] = assignApproverDO.getScreen();
			i++;
		}
		
		assignApproverTO.setMappingTo(mappingTo);
		assignApproverTO.setCityAry(cityAry);
		assignApproverTO.setOfficeAry(officeAry);
		assignApproverTO.setRegOfficeAry(regOfficeAry);
		
		assignApproverTO.setApplScreenAry(applScreenAry);
		
		LOGGER.trace("AssignApproverServiceImpl::getAssignDetails::END------------>:::::::");
		return assignApproverTO;
		
	}
	
	/** 
	 * List the all Cities By Citi Id List
	 * @inputparam  Citi list of Array
	 * @return  	List<CityTO>
	 * @author      Rohini  Maladi  
	 */
	@Override
	public List<CityTO> getCitiesByCityIds(Integer[] cityAry)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("AssignApproverServiceImpl::getCitiesByCityIds::START------------>:::::::");
		List<CityTO> cityTOList = new ArrayList<CityTO>();
		
		if(!StringUtil.isEmpty(cityAry)){
		
		CityTO cityTO = null;
		for(int i =0;i<cityAry.length;i++){
			cityTO = new CityTO();
			cityTO.setCityId(cityAry[i]);
			cityTOList.add(cityTO);
		}
		
		if(!CGCollectionUtils.isEmpty(cityTOList)){
			cityTOList = assignApproverCommonService.getAllCitiesByCityIds(cityTOList);
		}else{
			cityTOList = null;
		}
		}else{
			cityTOList = null;
		}
		LOGGER.trace("AssignApproverServiceImpl::getCitiesByCityIds::END------------>:::::::");
		return cityTOList;
	}
	
	/** 
	 * List the all Offices By Citi List
	 * @inputparam  CitiTO List
	 * @return  	List<CityTO>
	 * @author      Rohini  Maladi  
	 */
public List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList) throws CGBusinessException, CGSystemException{

return assignApproverCommonService.getOfficesByCityAndRHO(cityTOList);
	
}
/** 
 * List the all Users By UserType
 * @inputparam  UserType (E/C)
 * @return  	Map<Integer, String>
 * @author      Rohini  Maladi  
 */
@Override
public Map<Integer, String> getUsersByType(String userType)
		throws CGBusinessException, CGSystemException {

	return assignApproverCommonService.getUsersByType(userType);
}
@SuppressWarnings({ "unchecked", "rawtypes" })
@Override
public List getAssignApproverDetails(String userName, String userId)
		throws CGBusinessException, CGSystemException {
	LOGGER.trace("AssignApproverServiceImpl::getAssignApproverDetails::START------------>:::::::");
		//String emp = null;
		EmployeeUserTO employeeUserTO = null;
		AssignApproverTO assignApproverTO = null;
		List<CityTO> cityTOList = null;
		List<OfficeTO> officeTOList = null;
		List<OfficeTO> regOfficeTOList = null;
		List detailsObj = null;
	
		employeeUserTO = getEmpDetails(userName, userId);
			if (!StringUtil.isNull(employeeUserTO)
					&& !StringUtil.isNull(employeeUserTO.getEmpTO())
					&& employeeUserTO.getUserTO().getUserType().trim()
							.equals(UmcConstants.EMPLOYEE_CODE)) {
				if (employeeUserTO.getUserTO().getActive().trim()
						.equals(UmcConstants.FLAG_N)) {
					//emp = UmcConstants.IN_ACTIVE;
					ExceptionUtil.prepareBusinessException(LoginErrorCodeConstants.ASSIGN_INACTIVE_LOGIN_ID, new String[]{userName} );
				} else {
					assignApproverTO = new AssignApproverTO();
					assignApproverTO = getAssignDetails(employeeUserTO.getUserId());
					assignApproverTO.setUserId(employeeUserTO.getUserId());
					assignApproverTO.setEmpFirstName(employeeUserTO.getEmpTO()
							.getFirstName());
					assignApproverTO.setEmpLastName(employeeUserTO.getEmpTO()
							.getLastName());
					assignApproverTO.setEmpCode(employeeUserTO.getEmpTO()
							.getEmpCode());
					String mappingTO = assignApproverTO.getMappingTo();
					if (StringUtil.isStringEmpty(mappingTO)) {
						cityTOList = getCitiesByOffices(
								assignApproverTO.getOfficeAry(), null);
						officeTOList = getOfficesByCityList(null, cityTOList);
					} else if (mappingTO.trim().equals(UmcConstants.OFFICE_CODE))
						cityTOList = getCitiesByCityIds(assignApproverTO
										.getCityAry());
				
					if (!CGCollectionUtils.isEmpty(cityTOList)) {
						regOfficeTOList = getOfficesByCityAndRHO(cityTOList);
					}
					
					detailsObj = new ArrayList(4);
					
					detailsObj.add(assignApproverTO);
					detailsObj.add(cityTOList);
					detailsObj.add(officeTOList);
					detailsObj.add(regOfficeTOList);
				}
			}else{
				ExceptionUtil.prepareBusinessException(LoginErrorCodeConstants.ASSIGN_INVALID_LOGIN_ID, new String[]{userName} );
			}
		LOGGER.trace("AssignApproverServiceImpl::getAssignApproverDetails::END------------>:::::::");	
		return detailsObj;
}
		
}

