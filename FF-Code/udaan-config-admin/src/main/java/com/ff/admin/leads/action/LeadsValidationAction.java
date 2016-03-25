package com.ff.admin.leads.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.CreateLeadForm;
import com.ff.admin.leads.service.LeadsCommonService;
import com.ff.admin.leads.service.LeadsValidationService;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.leads.CompetitorListTO;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadStatusTO;
import com.ff.leads.LeadTO;
import com.ff.leads.ProductTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserRightsTO;

public class LeadsValidationAction extends AbstractCreateLead{

	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsValidationAction.class);
	
	private LeadsValidationService leadValidationService = null;
	
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

	/**
	 * @return the leadValidationService
	 */
	public LeadsValidationService getLeadValidationService() {
		if (StringUtil.isNull(leadValidationService)){
			leadValidationService = (LeadsValidationService) getBean(AdminSpringConstants.LEAD_VALIDATION_SERVICE);
		}
			return leadValidationService;
	}
	
	
	/**
	 * Prepare page.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("LeadsValidationAction::preparePage::START------------>:::::::");
		LeadTO leadTo = null;
		CreateLeadForm createLeadForm = null;
		ActionMessage actionMessage = null;
		//String officeCode = null;
		try {
			createLeadForm = (CreateLeadForm) form;
			leadTo = (LeadTO) createLeadForm.getTo();
			leadValidationService =getLeadValidationService();
			leadsCommonService = getLeadsCommonService();
			
			String leadNo = request.getParameter("leadNumber");
			//get Lead Details
			 leadTo = leadValidationService.getLeadDetails(leadNo);
			
			 setDefaults(request, leadTo);
			 request.setAttribute("PRODUCT_ROW_LIST", leadTo.getCompetitorList());
			
			 //populate competitor list
			List<CompetitorTO> competitorTOs;
			competitorTOs = leadValidationService.getCompetitorList();
			Collections.sort(competitorTOs);
			request.setAttribute(LeadCommonConstants.COMPETITOR_TO, competitorTOs);
			
			//populate industry category list
			List<StockStandardTypeTO> industryCategoryList;
			industryCategoryList = leadValidationService.getIndustryCategoryList();
			Collections.sort(industryCategoryList);
			request.setAttribute(LeadCommonConstants.INDUSTRY_CATEGORY_LIST, industryCategoryList);
			
			//populate lead source list
			List<StockStandardTypeTO> leadSourceList;
			leadSourceList = leadValidationService.getLeadSourceList();
			Collections.sort(leadSourceList);
			request.setAttribute(LeadCommonConstants.LEAD_SOURCE_LIST, leadSourceList);
			
			//populate regional branch list
			/*List<OfficeTO> officeTOList;
			officeTOList = leadValidationService.getRegionalBranchesList(leadTo.getRegionId(), leadTo.getOfficeTypeId());
			request.setAttribute(LeadCommonConstants.OFFICETO_LIST, officeTOList);*/
			
			List<OfficeTO> officeTOList = new ArrayList<>();
			if (leadTo.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
				officeTOList = leadValidationService
						.getBranchesUnderReportingRHO(
								leadTo.getLoginOfficeId(), leadTo.getUserId());
				Boolean isExist = Boolean.TRUE;
				for (OfficeTO officeTO : officeTOList) {
					if (officeTO.getOfficeId().equals(
							leadTo.getOfficeTO().getOfficeId())) {
						isExist = Boolean.FALSE;
						break;
					}
				}
				if (isExist)
					officeTOList.add(leadTo.getOfficeTO());
			} else if (leadTo.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				officeTOList = leadValidationService
						.getBranchesUnderReportingHub(
								leadTo.getLoginOfficeId(), leadTo.getUserId());
				Boolean isExist = Boolean.TRUE;
				for (OfficeTO officeTO : officeTOList) {
					if (officeTO.getOfficeId().equals(
							leadTo.getOfficeTO().getOfficeId())) {
						isExist = Boolean.FALSE;
						break;
					}
				}
				if (isExist)
					officeTOList.add(leadTo.getOfficeTO());
			} else if (leadTo.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_CORP_OFFICE)) {
				officeTOList = leadValidationService
						.getBranchesUnderCorporateOffice();
			} else if (leadTo.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				officeTOList = leadValidationService
						.getOfficesMappedToUser(leadTo.getUserId());
				Boolean isExist = Boolean.TRUE;
				if (!StringUtil.isEmptyColletion(officeTOList)) {
					for (OfficeTO officeTO : officeTOList) {
						if (officeTO.getOfficeId().equals(
								leadTo.getOfficeTO().getOfficeId())) {
							isExist = Boolean.FALSE;
							break;
						}
					}
					if (isExist) {
						officeTOList.add(leadTo.getOfficeTO());
					}
				} else {
					officeTOList = new ArrayList<>();
					officeTOList.add(leadTo.getOfficeTO());
				}
			}
			Collections.sort(officeTOList);
			request.setAttribute(LeadCommonConstants.OFFICETO_LIST,
					officeTOList);
			
			//populate sales person list
			List<EmployeeUserJoinBean> salesPersonList;
			salesPersonList = leadValidationService.getRegionalSalesPersonsList(leadTo.getBranchTO().getBranchId(),leadTo.getDesignation());
			Collections.sort(salesPersonList);
			request.setAttribute(LeadCommonConstants.REGIONAL_SALES_PERSON_LIST, salesPersonList);
			
			List<EmployeeTO> salesPersonTitleList;
			salesPersonTitleList = leadValidationService.getSalesPersonsTitlesList(LeadCommonConstants.DEPARTMENT_NAME_TYPE);
			Set<EmployeeTO> salesPersonDesignationSet = new LinkedHashSet<EmployeeTO>();
			List<String> desg = new ArrayList<String>();
			//String empName =null;
			for (EmployeeTO emp: salesPersonTitleList){
				
				if (!desg.contains(emp.getDesignation())){
					desg.add(emp.getDesignation());
					salesPersonDesignationSet.add(emp);
				}
			}
			//request.setAttribute("empName", empName);
			request.setAttribute(LeadCommonConstants.SALES_PERSON_DESIGNATION_SET, salesPersonDesignationSet);
			//populate assigned to list
			List<EmployeeTO> salesExecutiveList;
			salesExecutiveList = leadValidationService.getSalesPersonsTitlesList(LeadCommonConstants.DEPARTMENT_NAME_TYPE);
			Collections.sort(salesExecutiveList);
			request.setAttribute(LeadCommonConstants.SALES_EXECUTIVE_LIST, salesExecutiveList);
			
			List<StockStandardTypeTO> competitorProductList;
			competitorProductList = leadValidationService.getCompetitorProductList();
			Collections.sort(competitorProductList);
			request.setAttribute(LeadCommonConstants.COMPETITOR_PRODUCT_LIST, competitorProductList);
			
			((CreateLeadForm)form).setTo(leadTo);
		} catch (CGBusinessException e) {
			LOGGER.error("LeadsValidationAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("LeadsValidationAction::preparePage ..CGSystemException :"+e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("LeadsValidationAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("LeadsValidationAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);
		
	}

	
	public void setDefaults(HttpServletRequest request, LeadTO leadTO) throws CGBusinessException, CGBaseException
	{
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(LeadCommonConstants.USER_INFO);
		String empName =null;
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			leadTO.setLoginOfficeId(officeTO.getOfficeId());
			leadTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if(!StringUtil.isNull(officeTO.getRegionTO())){
				leadTO.setRegionId(officeTO.getRegionTO().getRegionId());
			}
			leadTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			leadTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			leadTO.setLoginOfficeTypeCode(officeTO.getOfficeTypeTO().getOffcTypeCode());
			leadTO.setOfficeTO(officeTO);
			leadTO.setUserId(userInfoTO.getUserto().getUserId());
			
		}
			leadTO.setDateOfUpdate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		
		if(!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId())){
			EmployeeTO emp = new EmployeeTO();
			emp.setEmployeeId(userInfoTO.getEmpUserTo().getEmpTO().getEmployeeId());
			if(StringUtils.isNotBlank(emp.getFirstName())){
				empName=emp.getFirstName()+" "+emp.getLastName();
			}else{
				empName=emp.getLastName();
			}
			request.setAttribute("empName", empName);
			
			leadTO.setUpdatedBy(emp);
		}
		/*if(!StringUtil.isNull(userInfoTO.getUserto().getUserRoles())){
			List<Integer> userRoles = userInfoTO.getUserto().getUserRoles();
			for(Integer roleId : userRoles){
				UserRightsTO userRolesTO = (UserRightsTO) leadsViewService.getUserRoleById(roleId);	
				if(userRolesTO.getStatus().equalsIgnoreCase("A")){
				if(userRolesTO.getRoleName().equals(LeadCommonConstants.SALES_EXECUTIVE)||userRolesTO.getRoleName().equals(LeadCommonConstants.CONTROL_TEAM_MEMBER)){
					leadTO.setUserRoles(userRolesTO.getRoleName());
					request.setAttribute("SALES_EXECUTIVE_ROLE", userRolesTO.getRoleName());
					break;
				}
				}
			}
			
		}*/
		
		leadTO.setSalesExecutiveRole(LeadCommonConstants.SALES_EXECUTIVE);
		leadTO.setControlTeamRole(LeadCommonConstants.CONTROL_TEAM_MEMBER);
		
		leadsCommonService = getLeadsCommonService();
		List<UserRightsTO> userRightsTOs = leadsCommonService.getUserRoleById(leadTO.getUserId());
		for(UserRightsTO userRightsTO : userRightsTOs){
				String roleName = userRightsTO.getRoleName().replaceAll("\\s+","");
				if(roleName.equalsIgnoreCase(LeadCommonConstants.SALES_EXECUTIVE) && userRightsTO.getStatus().equalsIgnoreCase("A")){
					request.setAttribute("userRole", roleName.toUpperCase());
					break;
				} else if(roleName.equalsIgnoreCase(LeadCommonConstants.CONTROL_TEAM_MEMBER) && userRightsTO.getStatus().equalsIgnoreCase("A")){
					request.setAttribute("userRole", roleName.toUpperCase());
					break;
				}
		}
		
	}
	
	@SuppressWarnings("static-access")
	public void approveLead(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("LeadsValidationAction :: approveLead() :: Start --------> ::::::");
		LeadTO leadTO = null;
		CreateLeadForm createLeadForm = null;
		String transMag = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try{
			out = response.getWriter();
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			leadValidationService =getLeadValidationService();
			setDefaults(request, leadTO);
			LeadStatusTO leadStatusTO = new LeadStatusTO();
			leadStatusTO.setStatusDescription(LeadCommonConstants.LEAD_APPROVED);
			leadTO.setStatus(leadStatusTO);
			setCompetitorDetails(leadTO);
			transMag = leadValidationService.approveLead(leadTO);
			
		}catch (CGBusinessException e) {
			LOGGER.error("LeadsValidationAction::approveLead() .. :"+e);
			transMag = LeadCommonConstants.LEAD_NOT_APPROVED;
		} catch (CGSystemException e) {
			LOGGER.error("LeadsValidationAction::approveLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_NOT_APPROVED;
		}catch (Exception e) {
			LOGGER.error("LeadsValidationAction::approveLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_NOT_APPROVED;
		}
		finally {
			if(leadTO == null){
				leadTO = new LeadTO();
			}
			leadTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(leadTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LeadsValidationAction :: approveLead() :: End --------> ::::::");
	}
	
	@SuppressWarnings("static-access")
	public void putOnHoldLead(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.trace("LeadsValidationAction :: putOnHoldLead() :: Start --------> ::::::");
		LeadTO leadTO = null;
		CreateLeadForm createLeadForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try{
			out = response.getWriter();
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			leadValidationService =getLeadValidationService();
			setDefaults(request, leadTO);
			LeadStatusTO leadStatusTO = new LeadStatusTO();
			leadStatusTO.setStatusDescription(LeadCommonConstants.LEAD_ON_HOLD);
			leadTO.setStatus(leadStatusTO);
			setCompetitorDetails(leadTO);
			transMag = leadValidationService.approveLead(leadTO);
		} catch (CGBusinessException e) {
			LOGGER.error("LeadsValidationAction::putOnHoldLead() .. :"+e);
			transMag = LeadCommonConstants.LEAD_COULD_NOT_BE_PUT_ON_HOLD;
		} catch (CGSystemException e) {
			LOGGER.error("LeadsValidationAction::putOnHoldLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_COULD_NOT_BE_PUT_ON_HOLD;
		}catch (Exception e) {
			LOGGER.error("LeadsValidationAction::putOnHoldLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_COULD_NOT_BE_PUT_ON_HOLD;
		}
		finally {
			if(leadTO == null){
				leadTO = new LeadTO();
			}
			leadTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(leadTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LeadsValidationAction :: putOnHoldLead() :: End --------> ::::::");
	}
	
	@SuppressWarnings("static-access")
	public void rejectLead(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		LOGGER.trace("LeadsValidationAction :: rejectLead() :: Start --------> ::::::");
		LeadTO leadTO = null;
		CreateLeadForm createLeadForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try{
			out = response.getWriter();
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			leadValidationService =getLeadValidationService();
			setDefaults(request, leadTO);
			LeadStatusTO leadStatusTO = new LeadStatusTO();
			leadStatusTO.setStatusDescription(LeadCommonConstants.LEAD_REJECTED);
			leadTO.setStatus(leadStatusTO);
			setCompetitorDetails(leadTO);
			transMag = leadValidationService.approveLead(leadTO);
		}catch (CGBusinessException e) {
			LOGGER.error("LeadsValidationAction::rejectLead() .. :"+e);
			transMag = LeadCommonConstants.LEAD_COULD_NOT_BE_REJECTED;
		} catch (CGSystemException e) {
			LOGGER.error("LeadsValidationAction::rejectLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_COULD_NOT_BE_REJECTED;
		}catch (Exception e) {
			LOGGER.error("LeadsValidationAction::rejectLead() .. :"+e);
			transMag =  LeadCommonConstants.LEAD_COULD_NOT_BE_REJECTED;
		}
		finally {
			if(leadTO == null){
				leadTO = new LeadTO();
			}
			leadTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(leadTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LeadsValidationAction :: rejectLead() :: End --------> ::::::");
	}
	
	
		private void setCompetitorDetails(LeadTO leadTO){
		
		String[] potential = new String [leadTO.getPotential().length];
		String[] expVolume = new String [leadTO.getExpectedVolume().length];
		Integer[] leadCompetitorId = new Integer [leadTO.getLeadCompetitorId().length];
		int j = 0;
		int k =0;		
		int potentialCount = leadTO.getPotential().length;
		int productCodeCount = leadTO.getProductCode().length;
		for (int i = 0; i < potentialCount; i++) {
			if (!StringUtil.isStringEmpty(leadTO.getPotential()[i])) {
				potential[j] = leadTO.getPotential()[i];
				expVolume[j] = leadTO.getExpectedVolume()[i];
				j++;
			}
			if(!StringUtil.isEmptyInteger(leadTO.getLeadCompetitorId()[i])){
				leadCompetitorId[k] = leadTO.getLeadCompetitorId()[i];
				k++;
			}
		}
		leadTO.setPotential(potential);
		leadTO.setExpectedVolume(expVolume);
		leadTO.setLeadCompetitorId(leadCompetitorId);
		ArrayList<CompetitorListTO> competitorListTOs = new ArrayList<CompetitorListTO>();
		for(int rowCount =0; rowCount<productCodeCount; rowCount++){
			if (!StringUtil
					.isEmptyInteger(leadTO.getCompetitorIds()[rowCount])) {
			CompetitorListTO competitorListTO = new CompetitorListTO();
			CompetitorTO competitorTO = new CompetitorTO();
			ProductTO productTo = new ProductTO();
			if(!StringUtil.isNull(leadTO.getCompetitorIds()[rowCount])){
			competitorTO.setCompetitorId(leadTO.getCompetitorIds()[rowCount]);
			competitorListTO.setCompetitor(competitorTO);
			}
			
			if(!StringUtil.isNull(leadTO.getProductCode()[rowCount])){
			productTo.setStdTypeCode(leadTO.getProductCode()[rowCount]);
			competitorListTO.setProduct(productTo);
			}
			if (!StringUtil.isNull(leadTO.getPotential()[rowCount])) {
				competitorListTO.setPotential((BigDecimal
						.valueOf(Double.parseDouble(leadTO
								.getPotential()[rowCount]))));
			}
			if (!StringUtil
					.isNull(leadTO.getExpectedVolume()[rowCount])) {
				competitorListTO.setExpectedVolume((BigDecimal
						.valueOf(Double.parseDouble(leadTO
								.getExpectedVolume()[rowCount]))));
			}
			if(!StringUtil.isNull(leadTO.getCreatedBy().getEmployeeId())){
				EmployeeTO employeeTO = new EmployeeTO();
				employeeTO.setEmployeeId(leadTO.getCreatedBy().getEmployeeId());
				competitorListTO.setCreatedByEmployeeTO(employeeTO);
			}
			if(!StringUtil.isNull(leadTO.getDate())){
				competitorListTO.setCreatedDate(DateUtil.stringToDDMMYYYYFormat(leadTO.getDate()));
			}
			if(!StringUtil.isNull(leadTO.getUpdatedBy().getEmployeeId())){
				EmployeeTO employeeTO = new EmployeeTO();
				employeeTO.setEmployeeId(leadTO.getUpdatedBy().getEmployeeId());
				competitorListTO.setUpdatedByEmployeeTO(employeeTO);
			}
			if(!StringUtil.isNull(leadTO.getDateOfUpdate())){
				competitorListTO.setDate(leadTO.getDateOfUpdate());
			}
			if(!StringUtil.isNull(leadTO.getLeadId())){
				competitorListTO.setLeadId(leadTO.getLeadId());
			}
			if(!StringUtil.isNull(leadTO.getLeadCompetitorId()[rowCount])){
				competitorListTO.setLeadCompetitorId(leadTO.getLeadCompetitorId()[rowCount]);
			}
			competitorListTOs.add(competitorListTO);
		}
		}
		leadTO.setCompetitorList(competitorListTOs);
		
}
	
	//to populate Assigned To dropdown
	@SuppressWarnings("static-access")
	public void getSalesExecutiveDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("LeadsValidationAction :: getSalesExecutiveDetails() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String office = request.getParameter("office");
			Integer officeId = Integer.parseInt(office);
			leadValidationService = getLeadValidationService() ;
			String designation = request.getParameter("designation");
			LeadTO leadTO = null;
			CreateLeadForm createLeadForm = null;
			createLeadForm = (CreateLeadForm)form;
			leadTO = (LeadTO) createLeadForm.getTo();
			List<EmployeeUserJoinBean> salesExecutiveList;
			
			setDefaults(request, leadTO);
			salesExecutiveList = leadValidationService.getRegionalSalesPersonsList(officeId, designation);
			request.setAttribute(LeadCommonConstants.SALES_EXECUTIVE_LIST, salesExecutiveList);
			jsonResult = serializer.toJSON(salesExecutiveList).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("LeadsValidationAction :: getSalesExecutiveDetails() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("LeadsValidationAction :: getSalesExecutiveDetails() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LeadsValidationAction :: getSalesExecutiveDetails() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.trace("LeadsValidationAction :: getSalesExecutiveDetails() :: End --------> ::::::");
	}
	
	//to populate Designation dropdown
			@SuppressWarnings("static-access")
			public void getSalesDesignation(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response) {
				LOGGER.trace("LeadsValidationAction :: getSalesDesignation() :: Start --------> ::::::");
				String jsonResult = CommonConstants.EMPTY_STRING;
				PrintWriter out = null;
				try {
					out = response.getWriter();
					String office = request.getParameter("office");
					Integer officeId = Integer.parseInt(office);
					leadValidationService = getLeadValidationService() ;
					LeadTO leadTO = null;
					CreateLeadForm createLeadForm = null;
					createLeadForm = (CreateLeadForm)form;
					leadTO = (LeadTO) createLeadForm.getTo();
					List<EmployeeUserTO> designationList;
					setDefaults(request, leadTO);
					designationList = leadValidationService.getSalesExecutive(officeId);
					request.setAttribute(LeadCommonConstants.DESIGNATION_LIST, designationList);
					Set<EmployeeUserTO> salesPersonDesignationSet = new LinkedHashSet<EmployeeUserTO>();
					List<String> desg = new ArrayList<String>();
					for (EmployeeUserTO emp: designationList){
						if (!desg.contains(emp.getEmpTO().getDesignation())){
							desg.add(emp.getEmpTO().getDesignation());
							salesPersonDesignationSet.add(emp);
						}
					}			
					jsonResult = serializer.toJSON(salesPersonDesignationSet).toString();
				}catch (CGBusinessException e) {
					LOGGER.error("LeadsValidationAction :: getSalesDesignation() ::"+e);
					jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
				}catch (CGSystemException e) {
					LOGGER.error("LeadsValidationAction :: getSalesDesignation() ::"+e);
					String exception=getSystemExceptionMessage(request,e);
					jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
				} catch(Exception e){
					LOGGER.error("LeadsValidationAction :: getSalesDesignation() ::"+e);
					String exception=getGenericExceptionMessage(request,e);
					jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
				}finally {
					out.print(jsonResult);
					out.flush();
					out.close();
				}	
				LOGGER.trace("LeadsValidationAction :: getSalesDesignation() :: End --------> ::::::");	
			}
	
	public ActionForward emailPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		
		return mapping.findForward(LeadCommonConstants.EMAIL);
	}
	
	public ActionForward SMSPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {

		return mapping.findForward(LeadCommonConstants.SMS);
	}
	
	public ActionForward leadPlanPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {

		return mapping.findForward(LeadCommonConstants.PLAN);
	}
	public ActionForward leadFeedbackPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {

		return mapping.findForward(LeadCommonConstants.FEEDBACK);
	}
	
	public ActionForward viewRateQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("LeadsValidationAction::viewRateQuotation::START------------>:::::::");
		LeadTO leadTo = null;
		CreateLeadForm createLeadForm = null;
		ActionMessage actionMessage = null;
		try {
			createLeadForm = (CreateLeadForm) form;
			leadTo = (LeadTO) createLeadForm.getTo();
			((CreateLeadForm)form).setTo(leadTo);
		} catch (Exception e) {
			LOGGER.error("LeadsValidationAction::viewRateQuotation ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("LeadsValidationAction::viewRateQuotation::END------------>:::::::");
			
		return redirectUrl(mapping, request, leadTo,  LeadCommonConstants.VIEW_RATE_QUOTATION);
	}
	
	public ActionForward viewEcommerceRateQuotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("LeadsValidationAction::viewEcommerceRateQuotation::START------------>:::::::");
		LeadTO leadTo = null;
		CreateLeadForm createLeadForm = null;
		ActionMessage actionMessage = null;
		try {
			createLeadForm = (CreateLeadForm) form;
			leadTo = (LeadTO) createLeadForm.getTo();
			((CreateLeadForm)form).setTo(leadTo);
		} catch (Exception e) {
			LOGGER.error("LeadsValidationAction::viewEcommerceRateQuotation ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("LeadsValidationAction::viewEcommerceRateQuotation::END------------>:::::::");

		return redirectUrl(mapping, request, leadTo,  LeadCommonConstants.VIEW_ECOMMERCE_QUOTATION);
	}	

	private ActionRedirect redirectUrl(ActionMapping mapping, HttpServletRequest request, LeadTO leadTo, String url){
		
		
		ActionRedirect redirect = new ActionRedirect(mapping.findForward(url));
		
		redirect.addParameter("sales", request.getParameter("sales"));
		redirect.addParameter("customerName", leadTo.getCustomerName());
		redirect.addParameter("leadNumber", leadTo.getLeadNumber());
		redirect.addParameter("contactPerson", leadTo.getContactPerson());
		redirect.addParameter("contactNo", leadTo.getPhoneNo());
		redirect.addParameter("mobileNo", leadTo.getMobileNo());
		redirect.addParameter("address1", leadTo.getDoorNoBuilding());
		redirect.addParameter("address2", leadTo.getStreet());
		redirect.addParameter("address3", leadTo.getLocation());
		redirect.addParameter("pincode", leadTo.getPincode());
		redirect.addParameter("designation", leadTo.getContPersonDesig());
		redirect.addParameter("email", leadTo.getEmailAddress());
		redirect.addParameter("indTypeCode", leadTo.getIndustryCategoryCode());

		return redirect;
	}
}

	

