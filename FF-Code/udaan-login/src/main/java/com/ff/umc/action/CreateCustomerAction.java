package com.ff.umc.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.LoginErrorCodeConstants;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.form.CreateCustomerForm;
import com.ff.umc.service.LoginService;
import com.ff.umc.service.UserManagementService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;

/**
 * @author nihsingh
 *
 */
public class CreateCustomerAction extends CGBaseAction {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);
	public transient JSONSerializer serializer;
	
	private UserManagementService userMgmtService = null;
	private LoginService loginService=null;
	BusinessCommonService businessCommonService=null;
	GeographyCommonService geographyCommonService=null;
	OrganizationCommonService organizationCommonService=null;
	
	/**
	 * @desc prepares customer login page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return customer login view
	 */
	public ActionForward customerLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateCustomerAction :: customerLogin() ::START");
		businessCommonService =  (BusinessCommonService) getBean("businessCommonService");
		geographyCommonService =  (GeographyCommonService) getBean("geographyCommonService");
		try {
			List<RegionTO> regionList=geographyCommonService.getAllRegions();
			request.setAttribute(UmcConstants.REGION_LIST, regionList);
			//List<CustomerTO> customers = businessCommonService.getAllCustomers();
			//request.setAttribute(UmcConstants.CUSTOMER_LIST, customers);
			
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in CreateCustomerAction :: customerLogin() :"	+ e.getMessage());
			getBusinessError(request, e);
		}	
		catch (CGSystemException e) {
			LOGGER.error("Error occured in CreateCustomerAction :: customerLogin() :"	+ e.getMessage());
			getSystemException(request, e);
		}	
		catch (Exception e) {
			LOGGER.error("Error occured in CreateCustomerAction :: customerLogin() :"	+ e.getMessage());
		}	
		LOGGER.trace("CreateCustomerAction :: customerLogin() ::END");
			return mapping.findForward(UmcConstants.CREATE_CUSTOMER_LOGIN);
	}

	
	public ActionForward getCitiesByRegionForCust(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateCustomerAction :: getCitiesByRegionForCust() ::START");
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
			LOGGER.error("CreateCustomerAction :: getCitiesByRegionForCust() ::",e);
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("CreateCustomerAction :: getCitiesByRegionForCust() ::"+e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
	
		finally{
			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateCustomerAction :: getCitiesByRegionForCust() ::END");
		return mapping.findForward(UmcConstants.CREATE_CUSTOMER_LOGIN);
		
	}
	
	/**
	 * Gets Customer List by City Id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void getCustByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.trace("CreateCustomerAction::getCustByCity::START------------>");

		userMgmtService = (UserManagementService) getBean(SpringConstants.UMC_SERVICE);

		List<String> custTOList=null;
		PrintWriter printWriter = null;
		String jsonResult = null;
		Map<Integer,List<String>> customerListByCityMap = null;
		HttpSession httpSession = request.getSession(false);

		try {
			printWriter = response.getWriter();
			Integer cityId = Integer.parseInt(request.getParameter(UmcConstants.PARAM_CITY_ID)
					.toString());
			if(!StringUtil.isEmptyInteger(cityId)) {
				customerListByCityMap = (Map<Integer, List<String>>)httpSession.getAttribute(UmcConstants.CUSTOMER_DETAILS_IN_SESSION);
				if(customerListByCityMap == null || customerListByCityMap.isEmpty()) {
					custTOList = userMgmtService.getCustomerTObyCityId(cityId);
					if(!CGCollectionUtils.isEmpty(custTOList)){
						customerListByCityMap= new HashMap<>();
						customerListByCityMap.put(cityId, custTOList);
						httpSession.setAttribute(UmcConstants.CUSTOMER_DETAILS_IN_SESSION, customerListByCityMap);

					}
				}else if(customerListByCityMap.containsKey(cityId)){
					custTOList =customerListByCityMap.get(cityId);
				}else{
					custTOList = userMgmtService.getCustomerTObyCityId(cityId);
					if(!CGCollectionUtils.isEmpty(custTOList)){
						customerListByCityMap.put(cityId, custTOList);
					}
				}
			}
			
			if(!CGCollectionUtils.isEmpty(custTOList)){
				jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,JSONSerializer.toJSON(custTOList).toString());
			}else{
				throw new CGBusinessException(LoginErrorCodeConstants.CUSTOMER_CITY_NOT_EXIST);
			}
		} catch (CGBusinessException businessException) {
			LOGGER.error("CreateCustomerAction::getCustByCity::ERROR"
					,businessException);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, businessException));
		} catch (CGSystemException systemException) {
			LOGGER.error("CreateCustomerAction::getCustByCity::ERROR"
					,systemException);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, systemException));
		} catch (Exception exception) {
			LOGGER.error("CreateCustomerAction::getCustByCity::ERROR"
					,exception);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, exception));
		} finally {
			printWriter.print(jsonResult);
			printWriter.flush();
			printWriter.close();
		}

		LOGGER.trace("CreateCustomerAction::getCustByCity::END------------>");
	}
	
	/**
	 * @desc creates customer user
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void createCustLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreateCustomerAction :: createCustLogin..Start");
		String jsonResult = UmcConstants.FAILURE;
		CreateCustomerForm custForm = (CreateCustomerForm)form;
		UserTO userto =null;
		CustomerUserTO custUserTo=null;
		boolean isCustomerDetailsSaved = false;
		PrintWriter out =null ;
	try {
			out = response.getWriter();
			UserInfoTO userinfoTo= (UserInfoTO) request.getSession().getAttribute(UmcConstants.USER_INFO);
			Integer loginUserId = userinfoTo.getUserto().getUserId();
			userto= ((CustomerUserTO) custForm.getTo()).getUserTO();
			custUserTo=(CustomerUserTO) custForm.getTo();
			custUserTo.setCustTO(((CustomerUserTO) custForm.getTo()).getCustTO());
			isCustomerDetailsSaved = userMgmtService.saveUserForCustomer(userto.getUserName(),loginUserId,UmcConstants.FLAG_C,custUserTo);
			//commented for transaction handling
			/*isCustomerDetailsSaved = userMgmtService.saveCustomerUser(custUserTo,userId);
			if(isCustomerDetailsSaved){
				boolean isCustEmailSaved=saveCustEmail(custUserTo.getCustTO().getCustomerId(),custUserTo.getCustTO().getEmail());
				}	*/
			if (isCustomerDetailsSaved) {
				jsonResult =prepareCommonException(FrameworkConstants.SUCCESS_FLAG, UmcConstants.CUST_SAVED_SUCCESSFULLY);
			}else{
				jsonResult =prepareCommonException(FrameworkConstants.ERROR_FLAG, UmcConstants.CUST_NOT_SAVED_SUCCESSFULLY);
			}
						
		} catch (CGBusinessException e)  {
			LOGGER.error("Error occured in CreateCustomerAction :: createCustLogin() :"	+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch(CGSystemException e){
			LOGGER.error("Error occured in CreateCustomerAction :: createCustLogin() :"	+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}catch(Exception e){
			LOGGER.error("Error occured in CreateCustomerAction :: createCustLogin() :"	+ e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
	finally{
		out.write(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.trace("CreateCustomerAction :: createCustLogin..END");
		
	}
	
	/**
	 * @desc search Customer Details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ActionForward searchCust(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreateCustomerAction :: searchCust..START");
		String jsonResult = "";
		PrintWriter out=null;
	//	String custName= ((CustomerUserTO) custForm.getTo()).getCustTO().getBusinessName();
		Integer userId;
		CustomerUserTO custUserTO=null;
		String custName = request.getParameter("cName");
		String custNames[]=custName.split("~");
		String custCode=custNames[1];
		
		userMgmtService=(UserManagementService)getBean(SpringConstants.UMC_SERVICE);
		loginService = (LoginService) getBean(SpringConstants.LOGIN_SERVICE);
		try {
			
			CustomerTO custTo =	userMgmtService.getCustTOByCustName(custCode);
			//to get user id from cust id
			custUserTO= userMgmtService.getCustUserTObyCustId(custTo.getCustomerId());
			if(custUserTO == null){ 
				custUserTO = new CustomerUserTO();
				custUserTO.setCustTO(custTo);
			}
			else{
				custUserTO.setCustTO(custTo);
				userId=custUserTO.getUserId();
				UserTO userTo = loginService.getUserDetailsByUserId(userId);
				custUserTO.setUserTO(userTo);
			}
			out=response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(custUserTO).toString();
			//response.setContentType("text/xml");
			
			} 	catch (CGBusinessException e) {
				LOGGER.error("Error occured in CreateCustomerAction :: searchCust() :"	+ e.getMessage());
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
				} 
				catch (CGSystemException e) {
				LOGGER.error("Error occured in CreateCustomerAction :: searchCust() :"	+ e.getMessage());
				String exception=getSystemExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
				} 
				catch (Exception e) {
				LOGGER.error("Error occured in CreateCustomerAction :: searchCust() :"	+ e.getMessage());
				String exception=ExceptionUtil.getExceptionStackTrace(e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
				} 
		finally{
			
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateCustomerAction :: searchCust..END");
		return null;
	}
	
	
	
	/**
	 * @desc activate Deactivate CustLogin
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void activateDeactivateCustLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreateCustomerAction :: activateDeactivateCustLogin..START");
		String transStatus = CommonConstants.EMPTY_STRING;
		boolean isUserUpdated=Boolean.FALSE;
		PrintWriter out = null;
		try {
			//get the context path
			String contextPath = request.getContextPath();
			out=response.getWriter();
			userMgmtService = (UserManagementService) getBean(SpringConstants.UMC_SERVICE);
			CreateCustomerForm createCustForm = (CreateCustomerForm)  form;
			CustomerUserTO custUserTO=(CustomerUserTO) createCustForm.getTo();
		//	String status = custUserTO.getUserTO().getActive();			
			String status = request.getParameter("status");
			String username=custUserTO.getUserTO().getUserName();
			isUserUpdated=userMgmtService.activateDeActivateCustUser(username,status,contextPath);
			
			
			if(isUserUpdated){
				transStatus = prepareCommonException(FrameworkConstants.SUCCESS_FLAG,(status.contains("`")?status.replaceAll("`", " "):status)+"Successfully");
			//	String result="sucessfully saved";
			//	jsonResult = prepareCommonException("SUCCESS",result);
			}
			else
				transStatus = prepareCommonException(FrameworkConstants.ERROR_FLAG,"Invalid Email Address");
		//	out.write(jsonResult);
			
			
			}catch (IOException e) {
				LOGGER.error("Error occured in CreateCustomerAction :: activateDeactivateCustLogin() :"
						+ e.getMessage());
			}catch(CGBusinessException ex){
				LOGGER.error("Error occured in CreateCustomerAction :: activateDeactivateCustLogin() :"
						+ ex.getMessage());
				transStatus = prepareCommonException("ERROR",getBusinessErrorFromWrapper(request,ex));
			}catch(Exception ex){
				LOGGER.error("Error occured in CreateCustomerAction :: activateDeactivateCustLogin() :"
						+ ex.getMessage());
				String exception=ExceptionUtil.getExceptionStackTrace(ex);
				transStatus = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} finally{
				out.write(transStatus);
				out.flush();
				out.close();
			}
		LOGGER.trace("CreateCustomerAction :: activateDeactivateCustLogin..END");
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
		LOGGER.trace("CreateCustomerAction :: resetPassword..START");
		java.io.PrintWriter out=null;
	      String jsonResult = CommonConstants.EMPTY_STRING;//added by niharika on 17 july
		try{
			//get the context path
			String contextPath = request.getContextPath();
		String operationName=request.getParameter("opName");
		userMgmtService = (UserManagementService) getBean(SpringConstants.UMC_SERVICE);
		CreateCustomerForm custForm= (CreateCustomerForm)form;
		String userName= ((CustomerUserTO) custForm.getTo()).getUserTO().getUserName();
		 boolean isPasswordReset=Boolean.FALSE;
		 out = response.getWriter();
		if(userMgmtService.resetPassword(userName,operationName,contextPath)) {
			//if(operationName.equals("reset"))
			//request.setAttribute("message", "Password reset successfully");
			  isPasswordReset=Boolean.TRUE;
			//else
			//request.setAttribute("message", "Password generated and email sent successfully");	
			
		}else {
			//if(operationName.equals("reset"))
			//request.setAttribute("message", "Unable to reset password. Try after some time");
			 isPasswordReset=Boolean.FALSE;
			//else
			//request.setAttribute("message", "Unable to generate password");	
		}
		
		 if(isPasswordReset){
	            jsonResult =prepareCommonException(FrameworkConstants.SUCCESS_FLAG, UmcConstants.PASWD_RESET_SUCCESSFULLY);
	      }else{
	            jsonResult =prepareCommonException(FrameworkConstants.ERROR_FLAG, UmcConstants.UNABLE_TO_RESET);
	      }
		}
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
	            String exception=ExceptionUtil.getExceptionStackTrace(e);
	            jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
	      }
		finally{
				out.write(jsonResult);
	            out.flush();
	            out.close();
		}
		LOGGER.trace("CreateCustomerAction :: resetPassword..END");
		//return mapping.findForward(UmcConstants.RESET_PASWD);
	}
	
	
	
	
	
}
