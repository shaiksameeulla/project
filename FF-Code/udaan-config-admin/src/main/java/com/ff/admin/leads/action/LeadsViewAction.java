/**
 * 
 */
package com.ff.admin.leads.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.LeadsViewForm;
import com.ff.admin.leads.service.LeadsCommonService;
import com.ff.admin.leads.service.LeadsViewService;
import com.ff.leads.LeadTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserTO;

/**
 * @author abarudwa
 *
 */
public class LeadsViewAction  extends AbstractCreateLead {
	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsViewAction.class);
	
	private LeadsViewService leadsViewService;
	
	private LeadsCommonService leadsCommonService= null;
	
	/**
	 * @return the leadsViewService
	 */
	public LeadsCommonService getLeadsCommonService() {
		if (StringUtil.isNull(leadsCommonService)){
			leadsCommonService = (LeadsCommonService) getBean(AdminSpringConstants.LEADS_COMMON_SERVICE);
		}
		return leadsCommonService;
	}

	
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("LeadsViewAction::preparePage::START------------>:::::::");
		LeadTO leadTO = null;
		LeadsViewForm leadsViewForm = null;
		ActionMessage actionMessage = null;
		try{
			leadsViewService = getLeadsViewService();
			leadsViewForm = (LeadsViewForm)form;
			leadTO = (LeadTO) leadsViewForm.getTo();
			setDefaults(request, leadTO);
			
			//populate sales person designation list
			List<EmployeeTO> salesPersonDesignationList;
			salesPersonDesignationList = leadsViewService.getSalesPersonsTitlesList(LeadCommonConstants.DEPARTMENT_NAME_TYPE);
			Set<EmployeeTO> salesPersonDesignationSet = new LinkedHashSet<EmployeeTO>();
			List<String> desg = new ArrayList<String>();
			for (EmployeeTO emp: salesPersonDesignationList){
				if (!desg.contains(emp.getDesignation())){
					desg.add(emp.getDesignation());
					salesPersonDesignationSet.add(emp);
				}
			}			
			request.setAttribute(LeadCommonConstants.SALES_PERSON_DESIGNATION_SET, salesPersonDesignationSet);
			List<EmployeeTO> salesExecutiveList;
			salesExecutiveList = leadsViewService.getSalesPersonsTitlesList(LeadCommonConstants.DEPARTMENT_NAME_TYPE);
			Collections.sort(salesExecutiveList);
			request.setAttribute(LeadCommonConstants.SALES_EXECUTIVE_LIST, salesExecutiveList);
			
			//populate view by status dropdown
			List<StockStandardTypeTO> leadStatusList;
			leadStatusList = leadsViewService.getLeadStatusList();
			Collections.sort(leadStatusList);
			request.setAttribute(LeadCommonConstants.LEAD_STATUS_LIST, leadStatusList);
			((LeadsViewForm)form).setTo(leadTO);
		}catch (CGBusinessException e) {
			LOGGER.error("LeadsViewAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("LeadsViewAction::preparePage ..CGSystemException :"+e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("LeadsViewAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("LeadsViewAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
		}
	
	private void setDefaults(HttpServletRequest request, LeadTO leadTO)
	{
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
		
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			leadTO.setLoginOfficeId(officeTO.getOfficeId());
			leadTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if(!StringUtil.isNull(officeTO.getRegionTO())){
				leadTO.setRegionId(officeTO.getRegionTO().getRegionId());
				request.setAttribute("regionId", userInfoTO.getOfficeTo().getRegionTO().getRegionId());
			}
			leadTO.setUserId(userInfoTO.getUserto().getUserId());
			leadTO.setUserInfoTO(userInfoTO);
			request.setAttribute("userId", userInfoTO.getUserto().getUserId());
			//request.setAttribute("regionId", userInfoTO.getOfficeTo().getRegionTO().getRegionId());
			
			//List<Integer> userRoles = userInfoTO.getUserto().getUserRoles();
			leadTO.setUserRoles(LeadCommonConstants.CONTROL_TEAM_MEMBER);
			leadTO.setSalesExecutiveRole(LeadCommonConstants.SALES_EXECUTIVE);
			leadTO.setControlTeamRole(LeadCommonConstants.CONTROL_TEAM_MEMBER);
			try{
			/*for(Integer roleId : userRoles){
				UserRightsTO userRolesTO = (UserRightsTO) leadsViewService.getUserRoleById(roleId);	
				if(userRolesTO.getStatus().equalsIgnoreCase("A") && userRolesTO.getRoleName().equals(LeadCommonConstants.SALES_EXECUTIVE)){
					request.setAttribute("userRole", userRolesTO.getRoleName());
					break;
				}
			}*/
				
				leadsCommonService = getLeadsCommonService();
				
				List<UserRightsTO> userRightsTOs = leadsCommonService.getUserRoleById(leadTO.getUserId());
				for(UserRightsTO userRightsTO:userRightsTOs){
					String roleName = userRightsTO.getRoleName().replaceAll("\\s+","");
					if(roleName.equalsIgnoreCase(LeadCommonConstants.SALES_EXECUTIVE) && userRightsTO.getStatus().equalsIgnoreCase("A")){
						request.setAttribute("userRole", roleName.toUpperCase());
						break;
					} else if(roleName.equalsIgnoreCase(LeadCommonConstants.CONTROL_TEAM_MEMBER) && userRightsTO.getStatus().equalsIgnoreCase("A")){
						request.setAttribute("userRole", roleName.toUpperCase());
						break;
					}

				}
			}catch(Exception e){
				LOGGER.error("LeadsViewAction :: setDefaults() ::"	+ e);
				getGenericException(request, e);
			}
			
		}
	}
	
	private LeadsViewService getLeadsViewService(){
		if(StringUtil.isNull(leadsViewService)){
			leadsViewService = (LeadsViewService) getBean(AdminSpringConstants.LEADS_VIEW_SERVICE);
		}
		return leadsViewService;
		
	}
	//to populate SalesExecutive dropdown
	@SuppressWarnings("static-access")
	public void getSalesExecutiveDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsViewAction :: getSalesExecutiveDetails() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String designation = request.getParameter("designation");
			leadsViewService = getLeadsViewService();
			LeadTO leadTO = null;
			LeadsViewForm leadsViewForm = null;
			leadsViewForm = (LeadsViewForm)form;
			leadTO = (LeadTO) leadsViewForm.getTo();
			List<EmployeeUserTO> regionalSalesPersonList;
			setDefaults(request, leadTO);
			regionalSalesPersonList = leadsViewService.getSalesExecutiveByRegion(leadTO.getRegionId(), designation);
			jsonResult = serializer.toJSON(regionalSalesPersonList).toString();
		}catch (CGBusinessException e) {
			LOGGER.error("LeadsViewAction :: getSalesExecutiveDetails() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("LeadsViewAction :: getSalesExecutiveDetails() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LeadsViewAction :: getSalesExecutiveDetails() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("LeadsViewAction::getSalesExecutiveDetails() ::END------------>:::::::");
	}	
	
	@SuppressWarnings("static-access")
	public void getLeadsByStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("LeadsViewAction::getLeadsByStatus::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try{
			out = response.getWriter();
			String status = request.getParameter("status");
			List<LeadTO> leadTOs =  leadsViewService.getLeadsByStatus(status);
			Collections.sort(leadTOs);
			jsonResult = serializer.toJSON(leadTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("LeadsViewAction :: getLeadsByStatus() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("LeadsViewAction :: getLeadsByStatus() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LeadsViewAction :: getLeadsByStatus() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("LeadsViewAction::getLeadsByStatus() ::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void getLeadsByUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LeadsViewAction::getLeadsByUser::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		String msg= null;
		LeadTO leadTO = null;
		List<LeadTO> leadTOs = null;
		try{
			out = response.getWriter();
			LeadsViewForm leadsViewForm = null;
			leadsViewForm = (LeadsViewForm) form;
			leadTO = (LeadTO) leadsViewForm.getTo();
			setDefaults(request, leadTO);
			UserTO userTO = new UserTO();
			/*userTO.setUserId(leadTO.getUserInfoTO().getEmpUserTo().getEmpUserId());
			userTO.setUserId(userInfoTO.getEmpUserTo().getEmpUserId());*/
			userTO.setUserId(leadTO.getUserId());
			
			
			String effectiveFromDate = request.getParameter("effectiveFrom");
			String effectiveToDate = request.getParameter("effectiveTo");
			String status = request.getParameter("status");
			
			leadsViewService = getLeadsViewService();
			leadTOs = leadsViewService.getLeadsByUser(userTO, effectiveFromDate, effectiveToDate, status);
			Collections.sort(leadTOs);
			jsonResult = serializer.toJSON(leadTOs).toString();
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in LeadsViewAction :: getLeadsByUser() ::"+ e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);		
		
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LeadsViewAction :: getLeadsByUser() ::"+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		} catch (Exception e) {
			LOGGER.error("Error occured in LeadsViewAction :: getLeadsByUser() ::"+ e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		
		}finally {
			if(leadTOs==null){
				leadTO = new LeadTO();
			}
			leadTO.setAlertMsg(msg);
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("LeadsViewAction::getLeadsByUser()  ::END------------>:::::::");
	}
	@SuppressWarnings("static-access")
	public void getLeadsBySalesExecutive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LeadsViewAction::getLeadsBySalesExecutive::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try{
			out = response.getWriter();
			LeadTO leadTO = null;
			LeadsViewForm leadsViewForm = null;
			leadsViewForm = (LeadsViewForm)form;
			leadTO = (LeadTO) leadsViewForm.getTo();
			setDefaults(request, leadTO);
			
			String effectiveFromDate = request.getParameter("effectiveFrom");
			String effectiveToDate = request.getParameter("effectiveTo");
			String status = request.getParameter("status");			
			
			String salesExecutive = request.getParameter("salesExecutive");
			Integer salesExecutiveId = Integer.parseInt(salesExecutive);
				UserTO userTO = new UserTO();
				userTO.setUserId(salesExecutiveId);
				leadsViewService = getLeadsViewService();
				List<LeadTO> leadTOs =  leadsViewService.getLeadsByUser(userTO, effectiveFromDate, effectiveToDate, status);
				Collections.sort(leadTOs);
				jsonResult = serializer.toJSON(leadTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("LeadsViewAction :: getLeadsBySalesExecutive() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("LeadsViewAction :: getLeadsBySalesExecutive() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LeadsViewAction :: getLeadsBySalesExecutive() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("LeadsViewAction::getLeadsBySalesExecutive::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void getLeadsByRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("LeadsViewAction::getLeadsByRegion::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		LeadTO leadTO = null;
		List<LeadTO> leadTOs = null;
		try{
			out = response.getWriter();
			LeadsViewForm leadsViewForm = null;
			leadsViewForm = (LeadsViewForm)form;
			leadTO = (LeadTO) leadsViewForm.getTo();
			

			String effectiveFromDate = request.getParameter("effectiveFrom");
			String effectiveToDate = request.getParameter("effectiveTo");
			String status = request.getParameter("status");			
		
			setDefaults(request, leadTO);
			UserInfoTO userInfoTO = null;
			if(!StringUtil.isNull(leadTO.getRegionId())){
				userInfoTO = new UserInfoTO();
				OfficeTO officeTO = new OfficeTO();
				officeTO.setOfficeId(leadTO.getRegionId());
				userInfoTO.setOfficeTo(officeTO);
			}
				leadsViewService = getLeadsViewService();
				leadTOs =  leadsViewService.getLeadsByRegion(leadTO.getRegionId(), effectiveFromDate, effectiveToDate, status);
				Collections.sort(leadTOs);
				jsonResult = serializer.toJSON(leadTOs).toString();
				
		}  catch (CGBusinessException e) {
			LOGGER.error("LeadsViewAction :: getLeadsByRegion() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("LeadsViewAction :: getLeadsByRegion() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LeadsViewAction :: getLeadsByRegion() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("LeadsViewAction::getLeadsByRegion::END------------>:::::::");
	}

}

