package com.ff.umc.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.CreateEmployeeForm;
import com.ff.umc.service.LoginService;
import com.ff.umc.service.UserManagementService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;


/**
 * @author nihsingh
 *
 */
public class CreateEmployeeAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);

	public transient JSONSerializer serializer;

	private UserManagementService userMgmtService = null;
	private LoginService loginService = null;
	OrganizationCommonService organizationCommonService=null;
	GeographyCommonService geographyCommonService=null;

	/**
	 * @desc prepares employee login page 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return employee login view
	 */
	public ActionForward employeeLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("CreateEmployeeAction :: employeeLogin() ::starts");
		geographyCommonService =  (GeographyCommonService) getBean("geographyCommonService");
	try {
			List<RegionTO> regionList=geographyCommonService.getAllRegions();
			request.setAttribute(UmcConstants.REGION_LIST, regionList);
			
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("CreateEmployeeAction :: employeeLogin() ::",e);
			
		}
		return mapping.findForward(UmcConstants.CREATE_EMPLOYEE_LOGIN);
	}

	public ActionForward getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateEmployeeAction :: getCitiesByRegion() ::starts");
		organizationCommonService =  (OrganizationCommonService) getBean("organizationCommonService");
		List<CityTO> cityTOs;
		PrintWriter out = null;
		String jsonResult = null;
		try {
			Integer region = Integer.parseInt(request.getParameter(UmcConstants.REGION_ID).toString());
			out = response.getWriter();
			CityTO cityTO = new CityTO();
			cityTO.setRegion(region);
			cityTOs = geographyCommonService.getCitiesByCity(cityTO);
			request.setAttribute(UmcConstants.CITY_LIST, cityTOs);
			
			jsonResult = JSONSerializer.toJSON(cityTOs).toString();
		} catch (CGSystemException  e) {
			LOGGER.error("CreateEmployeeAction :: getCitiesByRegion() ::",e);
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception  e) {
			LOGGER.error("CreateEmployeeAction :: getCitiesByRegion() ::",e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} 
	
		finally{
			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateEmployeeAction :: getCitiesByRegion() ::END");
		return mapping.findForward(UmcConstants.CREATE_EMPLOYEE_LOGIN);
		
	}
	
	public void getEmpByCity(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateEmployeeAction :: getEmpByCity() ::START");

		organizationCommonService =  (OrganizationCommonService) getBean("organizationCommonService");
		List<EmployeeTO> employees;
		PrintWriter out = null;
		String jsonResult = null;
		try {
	out = response.getWriter();
	Integer cityId=0;
	if(!StringUtil.isNull(request.getParameter("cityId"))){
		 cityId = Integer.parseInt(request.getParameter("cityId").toString());//check for Number format exception
	}
	
	employees = organizationCommonService.getEmployeesByCity(cityId);
	request.setAttribute(UmcConstants.EMPLOYEE_LIST, employees);
	
	jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,JSONSerializer.toJSON(employees).toString());
} catch (CGBusinessException e) {
	LOGGER.error("CreateEmployeeAction :: getEmpByCity() ::",e);
	jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
}catch (CGSystemException e) {
	LOGGER.error("CreateEmployeeAction :: getEmpByCity() ::",e);
	String exception=getSystemExceptionMessage(request, e);
	jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
}  catch (Exception e) {
	LOGGER.error("CreateEmployeeAction :: getEmpByCity() ::",e);
	String exception = getGenericExceptionMessage(request, e);
	jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
			exception);
}  
		
finally{
	
	out.print(jsonResult);
	out.flush();
	out.close();
}
LOGGER.trace("CreateEmployeeAction :: getEmpByCity() ::END");


}
	
	
	
	/**
	 * @desc saves or updates the employee user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveUpdateEmpLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateEmployeeAction :: saveUpdateEmpLogin() ::START");
		CreateEmployeeForm empForm = (CreateEmployeeForm) form;
		EmployeeUserTO empUserTO = null;
		boolean isEmpDetailsSaved = false;
		//PrintWriter out = null;
		String jsonResult = "";
		java.io.PrintWriter out=null;
		String userName="";
		try {
			 out = response.getWriter();
			UserInfoTO userinfoTo = (UserInfoTO) request.getSession().getAttribute(UmcConstants.USER_INFO); 
			
			Integer loginUserId = userinfoTo.getUserto().getUserId();
			String onlyEmail=request.getParameter("onlyEmail");
			if(StringUtil.isNull(onlyEmail))
				userName = request.getParameter("selectedUsers");
			//Integer empID=Integer.parseInt(request.getParameter("empID").toString());
			String emailID=request.getParameter("emailID");
		    empUserTO = (EmployeeUserTO) empForm.getTo();
			empUserTO.setEmpTO(((EmployeeUserTO) empForm.getTo()).getEmpTO());
			String empCode=((EmployeeUserTO) empForm.getTo()).getEmpTO().getEmpCode();
			EmployeeTO empTo = userMgmtService.getEmpTOByEmpCode(empCode);
			if(!StringUtil.isNull(onlyEmail) && onlyEmail.equals("onlyEmail")){
				saveOnlyEmpEmail(empTo.getEmployeeId(),emailID);
				jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,UmcConstants.EMP_MAIL_MODIFIED_SUCCESSFULLY);
			}else{
				isEmpDetailsSaved= userMgmtService.saveSelectedUsers(loginUserId,userName,empTo.getEmployeeId(),emailID);
			
				if (isEmpDetailsSaved) {
					jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,UmcConstants.EMP_USER_CREATED_SUCCESSFULLY);
				}else{
					jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,UmcConstants.EMP_USER_NOT_CREATED_SUCCESSFULLY);
				}
			}
			
			
			/*out.print(transStatus);
			out.flush();*/
		} 
		catch (CGBusinessException  e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: saveUpdateEmpLogin() :"
					+ e.getMessage());
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException  e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: saveUpdateEmpLogin() :"
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}  
		catch (Exception e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: saveUpdateEmpLogin() :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		
		} finally{
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateEmployeeAction :: saveUpdateEmpLogin() ::END");

	}

	/**
	 * @desc search employee details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return json object
	 */
	public ActionForward searchEmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateEmployeeAction :: searchEmp..Start");
		String jsonResult = "";
		java.io.PrintWriter out=null;
	//	CreateEmployeeForm empForm = (CreateEmployeeForm) form;
	//	String empName = ((EmployeeUserTO) empForm.getTo()).getEmpTO().getFirstName();
	//	String empNames[]=empName.split("-");
		//String empCode=((EmployeeUserTO) empForm.getTo()).getEmpTO().getEmpCode();
		String empName = request.getParameter("eName");
		String empNames[]=empName.split("~");
		String empCode="";
		if(!StringUtil.isEmpty(empNames)){
			 empCode=empNames[1];//check for array Index out of bound exception
		}
		userMgmtService = (UserManagementService) getBean(SpringConstants.UMC_SERVICE);
		Integer userId;
		EmployeeUserTO empUserTO = null;
		loginService = (LoginService) getBean(SpringConstants.LOGIN_SERVICE);
		try {
			out=response.getWriter();
			response.setContentType("text/javascript");
			serializer = CGJasonConverter.getJsonObject();
		//	EmployeeTO empTo = userMgmtService.getEmpTOByEmpName(empNames[0],empNames[1]);
			EmployeeTO empTo = userMgmtService.getEmpTOByEmpCode(empCode);
			// to get user id from emp id
			if(empTo!=null)
			{
				empUserTO = userMgmtService.getEmpUserTObyEmpId(empTo
						.getEmployeeId());
				if (empUserTO == null) {
					empUserTO = new EmployeeUserTO();
					empUserTO.setEmpTO(empTo);
				} else {
					empUserTO.setEmpTO(empTo);
					userId = empUserTO.getUserId();
					UserTO userTo = loginService.getUserDetailsByUserId(userId);
					empUserTO.setUserTO(userTo);
				}
			}
			
			if(empUserTO!=null)
				jsonResult = JSONSerializer.toJSON(empUserTO).toString();
			else{
				jsonResult = UmcConstants.EMPTY_STRING;
			}
			//response.setContentType("text/javascript");
			
			//request.setAttribute("jsonResult", jsonResult);
		} catch (CGBusinessException  e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: searchEmp() :"
					+ e.getMessage());
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			
		}catch (CGSystemException  e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: searchEmp() :"
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			
		}  
		catch (Exception e) {
			LOGGER.error("Error occured in CreateEmployeeAction :: searchEmp() :"
					+ e.getMessage());
			
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		
		} finally{
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateEmployeeAction :: searchEmp..END");
		return null;//mapping.findForward(UmcConstants.CREATE_EMPLOYEE_LOGIN);

	}


	/**
	 * @desc activate Deactivate EmpLogin
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException 
	 */
	public void activateDeactivateEmpLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException {

		LOGGER.trace("CreateEmployeeAction::activateDeactivateEmpLogin::Start-------->");

		String jsonResult = CommonConstants.EMPTY_STRING;
		java.io.PrintWriter out = null;

		boolean isUserUpdated = Boolean.FALSE;
		try {

			out = response.getWriter();
			response.setContentType("text/javascript");
			serializer = CGJasonConverter.getJsonObject();

			// get the context path
			String contextPath = request.getContextPath();
			String userStatus = request
					.getParameter(UmcConstants.USER_ID_STATUS);
			
			String updateMessage = CommonConstants.EMPTY_STRING;
			LOGGER.debug("CreateEmployeeAction::activateDeactivateEmpLogin::USER_ID_STATUS : "
					+ userStatus);

			userMgmtService = (UserManagementService) getBean(SpringConstants.UMC_SERVICE);

			isUserUpdated = updateUserStatus(userStatus, contextPath);

			String[] userNameAndStatus = null;

			if (isUserUpdated) {
				if (!StringUtil.isStringEmpty(userStatus)) {
					String[] userStatusArr = userStatus.split(CommonConstants.COMMA);
					for (String userStat : userStatusArr) {
						if (userStat != null && !userStat.isEmpty()) {
							userNameAndStatus = userStat
									.split(CommonConstants.TILD);
						}
						
						if (!StringUtil.isEmpty(userNameAndStatus)) {
							if (userNameAndStatus[1].equalsIgnoreCase("active"))
								updateMessage = updateMessage + userNameAndStatus[0]
										+ CommonConstants.SPACE
										+ UmcConstants.ACTIVATED_SUCCESSFULLY;
							else if (userNameAndStatus[1]
									.equalsIgnoreCase("de-active"))
								updateMessage = updateMessage + userNameAndStatus[0]
										+ CommonConstants.SPACE
										+ UmcConstants.DEACTIVATED_SUCCESSFULLY;
						}
						
						updateMessage = updateMessage + CommonConstants.CHARACTER_NEW_LINE;
					}
					
					jsonResult = prepareCommonException(CommonConstants.SUCCESS, updateMessage);
				}

			} else

				jsonResult = prepareCommonException(UmcConstants.FAILURE,
						UmcConstants.INVALID_EMAIL_ID);

		} catch (CGBusinessException businessException) {
			LOGGER.error(
					"CreateEmployeeAction::activateDeactivateEmpLogin::ERROR",
					businessException);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, businessException));
		} catch (Exception exception) {
			LOGGER.error(
					"CreateEmployeeAction::activateDeactivateEmpLogin::ERROR",
					exception);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, exception));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * @desc resets the password
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	
	public void resetPassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreateEmployeeAction :: resetPassword..Start");
		java.io.PrintWriter out=null;
      String jsonResult = CommonConstants.EMPTY_STRING;//added by niharika on 17 july
      try{
    	//get the context path
			String contextPath = request.getContextPath(); 
      String empUserName=request.getParameter("empUserName");
      String operationName=request.getParameter("operationName");
      boolean isPasswordReset=Boolean.FALSE;//added by niharika on 17 july
      out = response.getWriter();
      if(selectedUsersResetPaswd(empUserName,operationName,contextPath)){
            if(operationName.equals("reset"))
            //request.setAttribute("message", "Password reset successfully");
                  isPasswordReset=Boolean.TRUE;//added by niharika on 17 july
            else
            request.setAttribute("message", "Password generated and email sent successfully");
      }else{
            if(operationName.equals("reset"))
            //request.setAttribute("message", "Unable to reset password ");
                  isPasswordReset=Boolean.FALSE;//added by niharika on 17 july
            else
            request.setAttribute("message", "Unable to generate password");
      }
      if(isPasswordReset){
    	  jsonResult =prepareCommonException(FrameworkConstants.SUCCESS_FLAG,UmcConstants.PASWD_RESET_SUCCESSFULLY);
      }else{
    	  jsonResult =prepareCommonException(FrameworkConstants.ERROR_FLAG, UmcConstants.UNABLE_TO_RESET);
      }
      }//end of try
      	catch(CGBusinessException e){
    	  LOGGER.error("Error occured in CreateCustomerAction :: resetPassword() :"
    			  ,e);
	            jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
	      }
			catch(CGSystemException e){
			 LOGGER.error("Error occured in CreateCustomerAction :: resetPassword() :"
					 ,e);
	            String exception=getSystemExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
	      }
      		catch(Exception e){
			 LOGGER.error("Error occured in CreateCustomerAction :: resetPassword() :"
					 ,e);
	            String exception = getGenericExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						exception);
	      }
     
      finally{
            out.write(jsonResult);
            out.flush();
            out.close();
      }
      LOGGER.trace("CreateEmployeeAction :: resetPassword..END");
}

	
	/**
	 * @desc resets the password for selected users
	 * @param userNames
	 * @param operationName
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Boolean selectedUsersResetPaswd(String userNames,String operationName,String contextPath) throws CGBusinessException, CGSystemException{
		boolean isMailSent = Boolean.FALSE;
		if(userNames!=null&&!userNames.isEmpty()){
			String[] userNamesArr=userNames.split(",");
			for(int i=0;i<userNamesArr.length;i++){
				if(userMgmtService.resetPassword(userNamesArr[i],operationName,contextPath)){
					isMailSent=Boolean.TRUE;
				}
			}
		}
		return isMailSent;
	}
	
	/**
	 * @desc updates the user status
	 * @param empstatus
	 * @throws CGBusinessException 
	 */
	private boolean updateUserStatus(String empstatus,String contextPath) throws CGBusinessException{
		boolean isUserUpdated=Boolean.FALSE;
		if(!StringUtil.isStringEmpty(empstatus)){
			String[] userStatusArr=empstatus.split(CommonConstants.COMMA);
			for(String userStatus:userStatusArr){
				if(userStatus!=null&&!userStatus.isEmpty()){
					String[] userNameAndStatus=userStatus.split(CommonConstants.TILD);
					isUserUpdated= userMgmtService.updateUserStatus(userNameAndStatus[0],userNameAndStatus[1],contextPath);
						
				}
			}
		}
		return isUserUpdated;
	}
	
	/**
	 * @desc updates the emp email
	 * @param empID
	 * @param email
	 * @return boolean value
	 * @throws CGSystemException 
	 */
	private boolean saveOnlyEmpEmail(Integer empID,String email) throws CGSystemException{
		boolean isEmpEmailSaved=Boolean.FALSE;
		isEmpEmailSaved = userMgmtService.updateOnlyEmpEmail(empID,email);
		return isEmpEmailSaved;
	}
	public String prepareCommonException(String flag,String message) {
		LOGGER.trace("CreateEmployeeAction :: prepareCommonException..Start");
		String jsonResult=null;
		try{
			JSONObject detailObj = new JSONObject(); 
			detailObj.put(flag, message);
			jsonResult  = detailObj.toString();
		}catch (Exception jsonExcep) {
			LOGGER.error("AbstractDeliveryAction:: prepareCommonException::  ::Exception", jsonExcep);
		}
		LOGGER.trace("CreateEmployeeAction :: prepareCommonException..END");
		return jsonResult;
	}
}
