/**
 * 
 */
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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.form.CreateLeadForm;
import com.ff.admin.leads.service.CreateLeadService;
import com.ff.admin.leads.service.LeadsCommonService;
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

/**
 * @author sdalli
 * 
 */
public class CreateLeadAction extends AbstractCreateLead {

	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreateLeadAction.class);

	private CreateLeadService createLeadService = null;
	
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
		LOGGER.debug("CreateLeadAction::preparePage::START------------>:::::::");
		LeadTO leadTO = null;
		CreateLeadForm createLeadForm = null;
		ActionMessage actionMessage = null;
		// String officeCode = null;
		try {
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			createLeadService = getCreateLeadService();
			//String leadNumber = request.getParameter("leadNumber");
			setDefaults(request, leadTO);
			/*
			 * if(StringUtil.isStringEmpty(leadNumber)){ String
			 * generateLeadNumber=
			 * createLeadService.generateLeadNumber(leadTO.getLoginOfficeCode
			 * ()); leadTO.setLeadNumber(generateLeadNumber); }else{
			 * leadTO.setLeadNumber(leadNumber.trim()); }
			 */

			// populate competitor list
			List<CompetitorTO> competitorTOs;
			competitorTOs = createLeadService.getCompetitorList();
			Collections.sort(competitorTOs);
			request.setAttribute(LeadCommonConstants.COMPETITOR_TO,
					competitorTOs);

			// populate industry category list
			List<StockStandardTypeTO> industryCategoryList;
			industryCategoryList = createLeadService.getIndustryCategoryList();
			Collections.sort(industryCategoryList);
			request.setAttribute(LeadCommonConstants.INDUSTRY_CATEGORY_LIST,
					industryCategoryList);

			// populate lead source list
			List<StockStandardTypeTO> leadSourceList;
			leadSourceList = createLeadService.getLeadSourceList();
			Collections.sort(leadSourceList);
			request.setAttribute(LeadCommonConstants.LEAD_SOURCE_LIST,
					leadSourceList);

			// populate regional branch list

			List<OfficeTO> officeTOList = new ArrayList<>();
			if (leadTO.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
				officeTOList = createLeadService.getBranchesUnderReportingRHO(
						leadTO.getLoginOfficeId(), leadTO.getUserId());
				Boolean isExist = Boolean.TRUE;
				for (OfficeTO officeTO : officeTOList) {
					if (officeTO.getOfficeId().equals(
							leadTO.getOfficeTO().getOfficeId())) {
						isExist = Boolean.FALSE;
						break;
					}
				}
				if (isExist)
					officeTOList.add(leadTO.getOfficeTO());
			} else if (leadTO.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				officeTOList = createLeadService.getBranchesUnderReportingHub(
						leadTO.getLoginOfficeId(), leadTO.getUserId());
				Boolean isExist = Boolean.TRUE;
				for (OfficeTO officeTO : officeTOList) {
					if (officeTO.getOfficeId().equals(
							leadTO.getOfficeTO().getOfficeId())) {
						isExist = Boolean.FALSE;
						break;
					}
				}
				if (isExist)
					officeTOList.add(leadTO.getOfficeTO());
			} else if (leadTO.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_CORP_OFFICE)) {
				officeTOList = createLeadService
						.getBranchesUnderCorporateOffice();
			} else if (leadTO.getLoginOfficeTypeCode().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				officeTOList = createLeadService.getOfficesMappedToUser(leadTO
						.getUserId());
				Boolean isExist = Boolean.TRUE;
				if (!StringUtil.isEmptyColletion(officeTOList)) {
					for (OfficeTO officeTO : officeTOList) {
						if (officeTO.getOfficeId().equals(
								leadTO.getOfficeTO().getOfficeId())) {
							isExist = Boolean.FALSE;
							break;
						}
					}
					if (isExist)
						officeTOList.add(leadTO.getOfficeTO());
				} else {
					officeTOList = new ArrayList<>();
					officeTOList.add(leadTO.getOfficeTO());
				}
			}
			Collections.sort(officeTOList);
			request.setAttribute(LeadCommonConstants.OFFICETO_LIST,
					officeTOList);

			List<EmployeeTO> salesPersonTitleList;
			salesPersonTitleList = createLeadService
					.getSalesPersonsTitlesList(LeadCommonConstants.DEPARTMENT_NAME_TYPE);
			Collections.sort(salesPersonTitleList);
			request.setAttribute(LeadCommonConstants.SALES_PERSON_TITLE_LIST,
					salesPersonTitleList);

			List<StockStandardTypeTO> competitorProductList;
			competitorProductList = createLeadService
					.getCompetitorProductList();
			Collections.sort(competitorProductList);
			request.setAttribute(LeadCommonConstants.COMPETITOR_PRODUCT_LIST,
					competitorProductList);
			((CreateLeadForm) form).setTo(leadTO);

		} catch (CGBusinessException e) {
			LOGGER.error("CreateLeadAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("CreateLeadAction::preparePage ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("CreateLeadAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CreateLeadAction::preparePage::END------------>:::::::");
		return mapping.findForward(LeadCommonConstants.SUCCESS);

	}

	@SuppressWarnings("static-access")
	public void saveLead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateLeadAction :: saveLead() :: Start --------> ::::::");
		LeadTO leadTO = null;
		CreateLeadForm createLeadForm = null;
		String transMag = null;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			createLeadService = getCreateLeadService();
			setDefaults(request, leadTO);
			setCompetitorDetails(leadTO);
			String generateLeadNumber = createLeadService
					.generateLeadNumber(leadTO.getLoginOfficeCode());
			leadTO.setLeadNumber(generateLeadNumber);
			transMag = createLeadService.saveLead(leadTO);
			transMag = ("The generated lead number is " + leadTO
					.getLeadNumber());
		} catch (CGBusinessException e) {
			LOGGER.error("CreateLeadAction::saveLead() .. :" + e);
			transMag = LeadCommonConstants.ERROR_IN_SAVING_LEAD;
		} catch (CGSystemException e) {
			LOGGER.error("CreateLeadAction::saveLead() .. :" + e);
			transMag = LeadCommonConstants.ERROR_IN_SAVING_LEAD;
		} catch (Exception e) {
			LOGGER.error("CreateLeadAction::saveLead() .. :" + e);
			transMag = LeadCommonConstants.ERROR_IN_SAVING_LEAD;
		} finally {
			if (leadTO == null) {
				leadTO = new LeadTO();
			}
			leadTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(leadTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateLeadAction :: saveLead() :: End --------> ::::::");
	}

	private void setDefaults(HttpServletRequest request, LeadTO leadTO) {
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(LeadCommonConstants.USER_INFO);

		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if (!StringUtil.isNull(officeTO)) {
			leadTO.setLoginOfficeId(officeTO.getOfficeId());
			leadTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if (!StringUtil.isNull(officeTO.getRegionTO())) {
				leadTO.setRegionId(officeTO.getRegionTO().getRegionId());
			}
			leadTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			leadTO.setLoginOfficeTypeCode(officeTO.getOfficeTypeTO()
					.getOffcTypeCode());
			leadTO.setOfficeTO(officeTO);

		}
		if (StringUtil.isStringEmpty(leadTO.getDate())) {
			leadTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}

		if (!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpTO()
				.getEmployeeId())) {
			EmployeeTO emp = new EmployeeTO();
			emp.setEmployeeId(userInfoTO.getEmpUserTo().getEmpTO()
					.getEmployeeId());
			leadTO.setCreatedBy(emp);
		}
		if (!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpTO()
				.getEmployeeId())) {
			EmployeeTO emp = new EmployeeTO();
			emp.setEmployeeId(userInfoTO.getEmpUserTo().getEmpTO()
					.getEmployeeId());
			leadTO.setUpdatedBy(emp);
		}

		LeadStatusTO leadStatusTO = new LeadStatusTO();
		leadStatusTO.setStatusDescription("New");
		leadTO.setStatus(leadStatusTO);
		
		leadTO.setSalesExecutiveRole(LeadCommonConstants.SALES_EXECUTIVE);
		leadTO.setControlTeamRole(LeadCommonConstants.CONTROL_TEAM_MEMBER);
		leadTO.setUserId(userInfoTO.getUserto().getUserId());
		try{
			
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
		}catch(Exception e){
			LOGGER.error("CreateLeadAction :: setDefaults() ::"	+ e);
			getGenericException(request, e);
		}

	}

	private CreateLeadService getCreateLeadService() {
		if (StringUtil.isNull(createLeadService)) {
			createLeadService = (CreateLeadService) getBean(AdminSpringConstants.CREATE_LEAD_SERVICE);
		}
		return createLeadService;
	}

	// to populate Assigned To dropdown
	@SuppressWarnings("static-access")
	public void getSalesExecutiveDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreateLeadAction :: getSalesDesignation() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<EmployeeUserJoinBean> regionalSalesPersonList = null;
		try {
			out = response.getWriter();
			String office = request.getParameter("office");
			Integer officeId = Integer.parseInt(office);
			createLeadService = getCreateLeadService();
			String designation = request.getParameter("designation");
			LeadTO leadTO = null;
			CreateLeadForm createLeadForm = null;
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();

			setDefaults(request, leadTO);
			regionalSalesPersonList = createLeadService
					.getRegionalSalesPersonsList(officeId, designation);
			jsonResult = serializer.toJSON(regionalSalesPersonList).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("CreateLeadAction :: getSalesExecutiveDetails() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("CreateLeadAction :: getSalesExecutiveDetails() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CreateLeadAction :: getSalesExecutiveDetails() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateLeadAction :: getSalesExecutiveDetails() :: End --------> ::::::");
	}

	private void setCompetitorDetails(LeadTO leadTO) {

		String[] potential = new String[leadTO.getPotential().length];
		String[] expVolume = new String[leadTO.getExpectedVolume().length];
		int j = 0;
		int potentialCount = leadTO.getPotential().length;
		int productCodeCount = leadTO.getProductCode().length;
		for (int i = 0; i < potentialCount; i++) {
			if (!StringUtil.isStringEmpty(leadTO.getPotential()[i])) {
				potential[j] = leadTO.getPotential()[i];
				expVolume[j] = leadTO.getExpectedVolume()[i];
				j++;
			}
		}
		leadTO.setPotential(potential);
		leadTO.setExpectedVolume(expVolume);
		ArrayList<CompetitorListTO> competitorListTOs = new ArrayList<CompetitorListTO>();
			for (int rowCount = 0; rowCount < productCodeCount; rowCount++) {
				if (!StringUtil
						.isEmptyInteger(leadTO.getCompetitorIds()[rowCount])) {
					CompetitorListTO competitorListTO = new CompetitorListTO();
					CompetitorTO competitorTO = new CompetitorTO();
					ProductTO productTo = new ProductTO();
					if (!StringUtil.isNull(leadTO.getCompetitorIds()[rowCount])) {
						competitorTO
								.setCompetitorId(leadTO.getCompetitorIds()[rowCount]);
						competitorListTO.setCompetitor(competitorTO);
					}

					if (!StringUtil.isNull(leadTO.getProductCode()[rowCount])) {
						productTo
								.setStdTypeCode(leadTO.getProductCode()[rowCount]);
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
					if (!StringUtil.isNull(leadTO.getCreatedBy()
							.getEmployeeId())) {
						EmployeeTO employeeTO = new EmployeeTO();
						employeeTO.setEmployeeId(leadTO.getCreatedBy()
								.getEmployeeId());
						competitorListTO.setCreatedByEmployeeTO(employeeTO);
					}
					if (!StringUtil.isNull(leadTO.getDate())) {
						competitorListTO.setDate(leadTO.getDate());
					}

					competitorListTOs.add(competitorListTO);
				}
		}

		leadTO.setCompetitorList(competitorListTOs);
	}

	// to populate Designation dropdown
	@SuppressWarnings("static-access")
	public void getSalesDesignation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreateLeadAction :: getSalesDesignation() :: Start --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		List<EmployeeUserTO> regionalSalesPersonList = null;
		Set<EmployeeUserTO> salesPersonDesignationSet = new LinkedHashSet<EmployeeUserTO>();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String office = request.getParameter("office");
			Integer officeId = 0;
			if(!office.isEmpty())
			{
				officeId = Integer.parseInt(office);
			}
			
			createLeadService = getCreateLeadService();
			LeadTO leadTO = null;
			CreateLeadForm createLeadForm = null;
			createLeadForm = (CreateLeadForm) form;
			leadTO = (LeadTO) createLeadForm.getTo();
			setDefaults(request, leadTO);
			regionalSalesPersonList = createLeadService
					.getSalesExecutive(officeId);

			List<String> desg = new ArrayList<String>();
			for (EmployeeUserTO emp : regionalSalesPersonList) {
				if (!desg.contains(emp.getEmpTO().getDesignation())) {
					desg.add(emp.getEmpTO().getDesignation());
					salesPersonDesignationSet.add(emp);
				}
			}
			jsonResult = serializer.toJSON(salesPersonDesignationSet)
					.toString();

		} catch (CGBusinessException e) {
			LOGGER.error("CreateLeadAction :: getSalesDesignation() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("CreateLeadAction :: getSalesDesignation() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CreateLeadAction :: getSalesDesignation() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreateLeadAction :: getSalesDesignation() :: End --------> ::::::");
	}

}
