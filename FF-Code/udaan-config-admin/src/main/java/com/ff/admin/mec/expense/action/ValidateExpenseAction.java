package com.ff.admin.mec.expense.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.expense.form.ValidateExpenseForm;
import com.ff.admin.mec.expense.service.ValidateExpenseService;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.mec.expense.ValidateExpenseTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;

public class ValidateExpenseAction extends CGBaseAction{

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateExpenseAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The MEC common service. */
	private MECCommonService mecCommonService;
	
	/** The Validate Expense Service. */
	private ValidateExpenseService validateExpenseService; 
	
	/**
	 * To view the form details for Validate Expense Entries
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to validateExpense view
	 * @throws Exception
	 * @author hkansagr
	 */
	public ActionForward viewValidateExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("ValidateExpenseAction::viewValidateExpense()::START");
		ValidateExpenseTO to = null;
		String url = MECCommonConstants.VALIDATE_EXPENSE;
		ActionMessage actionMessage = null;
		try{
			if(!isLoggedInOfficeRHO(request)){
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_RHO);
			} else {
				to = new ValidateExpenseTO();
				validateExpStartup(request, to);
				saveToken(request);
				((ValidateExpenseForm)form).setTo(to);
			}
		}catch (CGBusinessException e) {
			LOGGER.error("ValidateExpenseAction :: viewValidateExpense :: CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ValidateExpenseAction :: viewValidateExpense :: CGSystemException :"+e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("ValidateExpenseAction :: viewValidateExpense :: Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ValidateExpenseAction::viewValidateExpense()::END");
		return mapping.findForward(url);
	}
	
	/**
	 * To set default values
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 * @author hkansagr
	 */
	@SuppressWarnings("unchecked")
	private void validateExpStartup(HttpServletRequest request, 
			ValidateExpenseTO to) throws CGBusinessException,CGSystemException {
		LOGGER.debug("ValidateExpenseAction::validateExpStartup()::START");
		/* setting the FROM DATE as current date i.e. DD/MM/YYYY */
		if(StringUtil.isStringEmpty(to.getFromDate())){
			to.setFromDate(DateUtil.getCurrentDateInDDMMYYYY());
		}
		
		/* setting the TO DATE as current date i.e. DD/MM/YYYY */
		if(StringUtil.isStringEmpty(to.getToDate())){
			to.setToDate(DateUtil.getCurrentDateInDDMMYYYY());
		}
		
		/* get User Info from session attribute */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		
		/* setting login officeId & regionId */
		OfficeTO officeTO = userInfo.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			to.setLoginOfficeId(officeTO.getOfficeId());
			to.setLoginOfficeCode(officeTO.getOfficeCode());
			if(!StringUtil.isNull(officeTO.getRegionTO())){
				to.setRegionId(officeTO.getRegionTO().getRegionId());
			}
		}
		
		/* setting created By userId */
		UserTO userTO = userInfo.getUserto();
		if(!StringUtil.isNull(userTO)){
			to.setCreatedBy(userTO.getUserId());
			to.setUpdatedBy(userTO.getUserId());
		}
		
		mecCommonService = getMECCommonService();
		LabelValueBean lvb = null;
		
		/* prepare Station For drop down */
		List<LabelValueBean> list = (List<LabelValueBean>)session
				.getAttribute(MECCommonConstants.PARAM_STATION_LIST);
		if(StringUtil.isEmptyColletion(list)){
			List<CityTO> cityTOs = mecCommonService.getCitiesByRegion(userInfo
				.getOfficeTo().getRegionTO());
			list = new ArrayList<LabelValueBean>();
			for (CityTO cityTO : cityTOs) {
				lvb = new LabelValueBean();
				lvb.setLabel(cityTO.getCityName().toUpperCase());
				lvb.setValue(cityTO.getCityId()+"");
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_STATION_LIST, list);
		}
		request.setAttribute(MECCommonConstants.PARAM_STATION_LIST, list);
		to.setStationList(list);
		
		/* prepare Status For drop down */
		list = (List<LabelValueBean>)session
				.getAttribute(MECCommonConstants.PARAM_STATUS_LIST);
		if(StringUtil.isEmptyColletion(list)){
			List<StockStandardTypeTO> txStatus = mecCommonService
				.getStockStdType(MECCommonConstants.MEC_STATUS);
			list = new ArrayList<LabelValueBean>();
			for (StockStandardTypeTO stdType : txStatus) {
				if(StringUtil.equals(stdType.getStdTypeCode()
						, MECCommonConstants.STATUS_OPENED))continue;
				lvb = new LabelValueBean();
				lvb.setLabel(stdType.getDescription());
				lvb.setValue(stdType.getStdTypeCode());
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_STATUS_LIST, list);
		}
		request.setAttribute(MECCommonConstants.PARAM_STATUS_LIST, list);
		to.setStatusList(list);
		LOGGER.debug("ValidateExpenseAction::validateExpStartup()::END");
	}
	
	/**
	 * To get All the offices by city
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ValidateExpenseAction::getAllOfficesByCity()::START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter(MECCommonConstants.PARAM_CITY_ID);
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if(!StringUtil.isEmptyInteger(cityId)) {
				mecCommonService = getMECCommonService();
				List<OfficeTO> officeTOs = mecCommonService
						.getAllOfficesByCity(cityId);
				jsonResult = serializer.toJSON(officeTOs).toString();
			}
		}catch (CGBusinessException e) {
			LOGGER.error("ValidateExpenseAction::getAllOfficesByCity()::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("ValidateExpenseAction::getAllOfficesByCity()::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ValidateExpenseAction::getAllOfficesByCity()::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ValidateExpenseAction::getAllOfficesByCity()::END");
	}
	
	/**
	 * To search Validate Expense Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return view to ValidateExpense
	 * @throws Exception
	 */
	public ActionForward searchValidateExpenseDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("ValidateExpenseAction::searchValidateExpenseDtls()::START");
		ValidateExpenseForm validateForm = (ValidateExpenseForm)form;
		ValidateExpenseTO to = (ValidateExpenseTO)validateForm.getTo();
		ActionMessage actionMessage = null;
		try {
			validateExpenseService = getValidateExpenseService();
			to = validateExpenseService.searchValidateExpenseDtls(to);
			request.setAttribute(MECCommonConstants.PARAM_STATUS_VALIDATED,
					MECCommonConstants.STATUS_VALIDATED);
			request.setAttribute(MECCommonConstants.PARAM_OFFICE_ID,
					to.getOfficeId());
			saveToken(request);
		} catch(CGBusinessException e) {
			to = new ValidateExpenseTO();
			LOGGER.error("Error Occurs in ValidateExpenseAction::searchValidateExpenseDtls()::",e);
			getBusinessError(request,e);
		}  catch (CGSystemException e) {
			to = new ValidateExpenseTO();
			LOGGER.error("Error Occurs in ValidateExpenseAction::searchValidateExpenseDtls()::",e);
			getSystemException(request,e);
		}catch(Exception e) {
			to = new ValidateExpenseTO();
			LOGGER.error("Error Occurs in ValidateExpenseAction::searchValidateExpenseDtls()::",e);
			actionMessage =  new ActionMessage(AdminErrorConstants
					.DETAILS_NOT_FOUND_DB_ISSUE, MECCommonConstants.EXPENSE);
		} finally {
			prepareActionMessage(request, actionMessage);
			try {
				validateExpStartup(request, to);
			} catch (CGBusinessException e) {
				LOGGER.error("Error Occurs in ValidateExpenseAction::searchValidateExpenseDtls()::",e);
			} catch (CGSystemException e) {
				LOGGER.error("Error Occurs in ValidateExpenseAction::searchValidateExpenseDtls()::",e);
			}
		}
		((ValidateExpenseForm)form).setTo(to);
		LOGGER.debug("ValidateExpenseAction::searchValidateExpenseDtls()::END");
		return mapping.findForward(MECCommonConstants.VALIDATE_EXPENSE);
	}
	
	/**
	 * To get mecCommonService
	 * 
	 * @return mecCommonService
	 * @author hkansagr
	 */
	private MECCommonService getMECCommonService(){
		if(StringUtil.isNull(mecCommonService)){
			mecCommonService = (MECCommonService) getBean(
					AdminSpringConstants.MEC_COMMON_SERVICE);
		}
		return mecCommonService;
	}
	
	/**
	 * To get Validate Expense Service
	 * 
	 * @return validateExpenseService
	 * @author hkansagr
	 */
	private ValidateExpenseService getValidateExpenseService(){
		if(StringUtil.isNull(validateExpenseService)){
			validateExpenseService = (ValidateExpenseService) getBean(
					AdminSpringConstants.VALIDATE_EXPENSE_SERVICE);
		}
		return validateExpenseService;
	}
	
	/**
	 * To check whether Logged In Office is brach/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isLoggedInOfficeRHO(HttpServletRequest request){
		LOGGER.debug("ValidateExpenseAction::isLoggedInOfficeRHO()::START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO!=null && loggedInOfficeTO.getOfficeTypeTO()!=null
				&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
			result = true;
		}
		LOGGER.debug("ValidateExpenseAction::isLoggedInOfficeRHO()::END");
		return result;
	}
	
}
