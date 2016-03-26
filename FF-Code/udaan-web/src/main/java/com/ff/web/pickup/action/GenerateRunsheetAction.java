package com.ff.web.pickup.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.form.PickupRunsheetForm;
import com.ff.web.pickup.service.GenerateRunsheetService;
import com.ff.web.pickup.service.PickupGatewayService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class GenerateRunsheetAction.
 */
public class GenerateRunsheetAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GenerateRunsheetAction.class);
	public transient JSONSerializer serializer;
	/** The generate runsheet service. */
	private GenerateRunsheetService generateRunsheetService;

	/** The pickup management common service. */
//	private PickupManagementCommonService pickupManagementCommonService;

	public ActionForward viewGeneratePickupRunSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("GenerateRunsheetAction::viewGeneratePickupRunSheet::START------------>:::::::");
		
		try {
			generateRunsheetService = (GenerateRunsheetService) getBean(PickupManagementConstants.GENERATE_RUNSHEET_SERVICE);
			final PickupRunsheetForm pkupRunsheetForm = (PickupRunsheetForm) form;
			PickupRunsheetTO pickupRunsheetTO = (PickupRunsheetTO) pkupRunsheetForm
					.getTo();
			getAndSetLoginDetails(request, pickupRunsheetTO);
			setConstants(request);
			
			// retrieves all run sheet assignments for the selection in employee
			// List (ALL)
			pickupRunsheetTO = generateRunsheetService
					.getAssignedRunsheets(pickupRunsheetTO);

			if (!StringUtil.isNull(pickupRunsheetTO)
					&& !CGCollectionUtils.isEmpty(pickupRunsheetTO
							.getAssignedRunsheetTOList())) {
				request.setAttribute(PickupManagementConstants.RUNSHEET_LIST,
						pickupRunsheetTO.getAssignedRunsheetTOList());
			}
			((PickupRunsheetForm) form).setTo(pickupRunsheetTO);
		} catch (CGBusinessException e) {			
			LOGGER.error("ERROR :: GenerateRunsheetAction :: viewGeneratePickupRunSheet() ::"
					+ e);
			getBusinessError(request,e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction::viewGeneratePickupRunSheet------------>:::::::"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction::viewGeneratePickupRunSheet------------>:::::::"
					+ e);
			prepareCommonException(FrameworkConstants.ERROR_FLAG, UdaanWebErrorConstants.ERROR_PAGE_LOAD);
		}
		LOGGER.debug("GenerateRunsheetAction::viewGeneratePickupRunSheet::END------------>:::::::");

		return mapping
				.findForward(PickupManagementConstants.VIEW_GENERATE_PICKUP_RUNSHEET);
	}

	private void getAndSetLoginDetails(HttpServletRequest request,
			PickupRunsheetTO pickupRunsheetTO) throws CGBusinessException, CGSystemException {
		pickupRunsheetTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSSSlashFormat());
		List<EmployeeTO> assignedEmployeeList = null;
		List<Integer> createdForOfficeIds=null;
		Integer createdAtOfficeId=null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		pickupRunsheetTO.setLoginOfficeTO(officeTO);
		if (StringUtils.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE,
				officeTO.getOfficeTypeTO().getOffcTypeCode())) {
			if(StringUtils.isEmpty(pickupRunsheetTO.getHubOrBranch())){
				pickupRunsheetTO
					.setHubOrBranch(CommonConstants.OFF_TYPE_HUB_OFFICE);				
			}
			
			if(StringUtils.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE,pickupRunsheetTO.getHubOrBranch())){
				//Logged in HUB office - All Branches(Hub radio selection in Hub login screen)
				pickupRunsheetTO.setBranchId(pickupRunsheetTO.getLoginOfficeTO()
						.getOfficeId());
				createdForOfficeIds = new ArrayList<>();
				createdForOfficeIds.add(pickupRunsheetTO.getBranchId());
			}else{
				//Logged in HUB office - All Branches(Branch radio selection in Hub login screen)  
				pickupRunsheetTO.setBranchId(-1);	
				List<OfficeTO> loginHubBranchesList = generateRunsheetService
						.getBranchesUnderHUB(pickupRunsheetTO.getLoginOfficeTO()
								.getOfficeId());
				createdForOfficeIds = new ArrayList<>();
				if(!StringUtil.isEmptyList(loginHubBranchesList)){
					for (OfficeTO branchOffTO : loginHubBranchesList) {
						createdForOfficeIds.add(branchOffTO.getOfficeId());
					}
				}
				pickupRunsheetTO.setBranchTOsList(loginHubBranchesList);							
			}
						
		}else{
			//Logged in Branch office - All Branches
			pickupRunsheetTO.setBranchId(pickupRunsheetTO.getLoginOfficeTO().getOfficeId());
			createdForOfficeIds = new ArrayList<>();
			createdForOfficeIds.add(pickupRunsheetTO.getBranchId());
		}
		// retrieves all employees for which run sheet is to be
		// generated/already generated
		createdAtOfficeId = pickupRunsheetTO.getLoginOfficeTO()
				.getOfficeId();		
		assignedEmployeeList = generateRunsheetService
				.getBranchPickupEmployees(createdAtOfficeId, createdForOfficeIds);
		pickupRunsheetTO.setAssignedEmployeeTOList(assignedEmployeeList);
	}

	private void setConstants(HttpServletRequest request) {
		request.setAttribute(PickupManagementConstants.UN_USED,
				PickupManagementConstants.UN_USED);
		request.setAttribute(PickupManagementConstants.OPEN,
				PickupManagementConstants.OPEN);
		request.setAttribute(PickupManagementConstants.STATUS_UPDATED,
				PickupManagementConstants.STATUS_UPDATED);
		request.setAttribute(PickupManagementConstants.STATUS_CLOSED,
				PickupManagementConstants.STATUS_CLOSED);
		request.setAttribute("OFF_TYPE_CODE_HUB",
				CommonConstants.OFF_TYPE_HUB_OFFICE);
		request.setAttribute("OFF_TYPE_CODE_BRANCH",
				CommonConstants.OFF_TYPE_BRANCH_OFFICE);
	}

	@SuppressWarnings("static-access")
	public void getBranchEmployees(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("GenerateRunsheetAction::getBranchEmployees::START------------>:::::::");
		PrintWriter out = null;
		String employeesJSON = "";
		try {
			out = response.getWriter();
			String branchOffice = "";
			branchOffice = request.getParameter("branch");
			if (StringUtils.isNotEmpty(branchOffice)) {
				List<Integer> branchOfficeId=new ArrayList<>();
				HttpSession session = null;
				UserInfoTO userInfoTO = null;
				session = (HttpSession) request.getSession(false);
				userInfoTO = (UserInfoTO) session
						.getAttribute(UmcConstants.USER_INFO);
				OfficeTO officeTO = userInfoTO.getOfficeTo();
				Integer loggedInOfficeId = officeTO.getOfficeId();
				branchOfficeId.add(Integer.parseInt(branchOffice));
				if(StringUtil.isEmptyList(branchOfficeId)){
					branchOfficeId.add(loggedInOfficeId);
				}
				generateRunsheetService = (GenerateRunsheetService) getBean(PickupManagementConstants.GENERATE_RUNSHEET_SERVICE);
				List<EmployeeTO> employeeList = generateRunsheetService
						.getBranchPickupEmployees(loggedInOfficeId,branchOfficeId);
				if (!StringUtil.isEmptyList(employeeList)) {
					employeesJSON = serializer.toJSON(employeeList).toString();
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: getBranchEmployees() ::"
					+ e);
			employeesJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));			
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: getBranchEmployees() ::"
					+ e);
			employeesJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction::getBranchEmployees------------>:::::::"
					+ e);
			employeesJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(employeesJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("GenerateRunsheetAction::getBranchEmployees::END------------>:::::::");
	}

	public void searchAssignedRunsheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("GenerateRunsheetAction::searchAssignedRunsheet::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		PickupRunsheetTO pkupRunsheetTO = null;
		try {
			out = response.getWriter();
			PickupRunsheetForm pkupRunsheetForm = (PickupRunsheetForm) form;
			pkupRunsheetTO = (PickupRunsheetTO) pkupRunsheetForm.getTo();			
			generateRunsheetService = (GenerateRunsheetService) getBean(PickupManagementConstants.GENERATE_RUNSHEET_SERVICE);

			pkupRunsheetTO = generateRunsheetService
					.getAssignedRunsheets(pkupRunsheetTO);			
			jsonResult = createJsonObject(pkupRunsheetTO);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: searchAssignedRunsheet() ::"
					+ e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));	
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: searchAssignedRunsheet() ::"
					+ e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));	
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetAction::searchAssignedRunsheet", e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("GenerateRunsheetAction::searchAssignedRunsheet::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	private String createJsonObject(PickupRunsheetTO pkupRunsheetTO) {
		LOGGER.debug("GenerateRunsheetAction :: createJsonObject() :: START------------>:::::::");
		StringBuilder jsonResult = new StringBuilder();
		if (!StringUtil.isNull(pkupRunsheetTO)) {			
			String runsheetToJson = serializer.toJSON(pkupRunsheetTO).toString();
			String detailToJson = null;
			jsonResult = appendListName(jsonResult, runsheetToJson,"runsheetTO");
			if (!StringUtil.isEmptyList(pkupRunsheetTO
					.getAssignedRunsheetTOList())) {
				// Collections.sort(pkupRunsheetTO.getAssignedRunsheetTOList());
				detailToJson = serializer.toJSON(
						pkupRunsheetTO.getAssignedRunsheetTOList()).toString();
			}
			jsonResult = appendListName(jsonResult, detailToJson, "detailTO");
		}
		LOGGER.debug("GenerateRunsheetAction :: createJsonObject() :: END------------>:::::::");
		return jsonResult.toString();
	}

	private StringBuilder appendListName(StringBuilder stringBuilder,
			String ajaxResponse, String listName) {
		stringBuilder
				.append(CommonConstants.TILD)
				.append(CommonConstants.OPENING_CURLY_BRACE)
				.append(CommonConstants.OPENING_INNER_QOUTES + listName
						+ CommonConstants.CLOSING_INNER_QOUTES)
				.append(CommonConstants.CHARACTER_COLON).append(ajaxResponse)
				.append(CommonConstants.CLOSING_CURLY_BRACE);

		return stringBuilder;
	}

	public void generatePickupRunsheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("GenerateRunsheetAction :: generatePickupRunsheet() :: START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		PickupRunsheetTO pkupRunsheetTO = null;
		try {
			out = response.getWriter();
			PickupRunsheetForm pkupRunsheetForm = (PickupRunsheetForm) form;
			pkupRunsheetTO = (PickupRunsheetTO) pkupRunsheetForm.getTo();
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			//Audit columns
			pkupRunsheetTO.setLoggedInUserId(userInfoTO.getUserto().getUserId());
			generateRunsheetService = (GenerateRunsheetService) getBean(PickupManagementConstants.GENERATE_RUNSHEET_SERVICE);
			pkupRunsheetTO = generateRunsheetService
					.savePickupRunsheet(pkupRunsheetTO);
			
			//calling TwoWayWrite service to save same in central
			twoWayWrite(pkupRunsheetTO);
			
			pkupRunsheetTO.setTransactionMsg(getMessageFromErrorBundle(request, PickupManagementConstants.RUNSHEET_GENERATED_SUCCESSFULLY, null));
			jsonResult = createJsonObject(pkupRunsheetTO);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: generatePickupRunsheet() ::"
					+ e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: generatePickupRunsheet() ::"
					+ e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetAction::generatePickupRunsheet", e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("GenerateRunsheetAction :: generatePickupRunsheet() :: END------------>:::::::");
	}
	public void twoWayWrite(PickupRunsheetTO pkupRunsheetTO)
			throws CGBusinessException {
		try{
			PickupGatewayService pickupGatewayService = (PickupGatewayService) getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			pickupGatewayService.twoWayWrite(pkupRunsheetTO.getPickupTwoWayWriteTO());
		} catch (Exception e) {
			LOGGER.error("GenerateRunsheetAction:: twoWayWrite", e);
		}
	}
	public ActionForward printGenPickupRunSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException{
		LOGGER.debug("GenerateRunsheetAction::printGenPickupRunSheet::START------------>:::::::");
		PickupRunsheetForm pkupRunsheetForm = null;
		PickupRunsheetTO pkupRunsheetTO = null;
		String url = null;
		HttpSession session = request.getSession();
		try {
			pkupRunsheetForm = (PickupRunsheetForm) form;
			pkupRunsheetTO = (PickupRunsheetTO) pkupRunsheetForm.getTo();
			String runsheetHeaderIds = request
					.getParameter("RunsheetHeaderIds");
			String[] runsheetHeaderId = runsheetHeaderIds.split(",");
			List<String> HeaderIds = Arrays.asList(runsheetHeaderId);
			List<Integer> runsheetId = new ArrayList<Integer>(HeaderIds.size());
			for (String id : HeaderIds) {
				Integer idds = Integer.parseInt(id);
				runsheetId.add(idds);
			}
			PickupManagementCommonService pickupManagementCommonService = (PickupManagementCommonService) getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
			List<List<PickupRunsheetTO>> runsheetList = pickupManagementCommonService
					.printPickupRunsheet(pkupRunsheetTO, runsheetId);
			List<GeneratePickUpPage> page = generateRunsheetService
					.preparePrint(runsheetList);

			if (!StringUtil.isEmptyColletion(runsheetList)) {
				session.setAttribute("runsheetList", runsheetList);
				session.setAttribute("page", page);
				session.setAttribute("pkupRunsheetTO", pkupRunsheetTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: printGenPickupRunSheet() ::"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: GenerateRunsheetAction :: printGenPickupRunSheet() ::"
					,e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception happened in printGenPickupRunSheet of GenerateRunsheetAction..."
					+ e);
			getGenericException(request, e);
		}
		if (pkupRunsheetTO == null) {
			url = PickupManagementConstants.VIEW_GENERATE_PICKUP_RUNSHEET;
		} else {
			url = PickupManagementConstants.URL_PRINT_GENERATE_PICKUP_RUNSHEET;
		}
		LOGGER.debug("GenerateRunsheetAction::printGenPickupRunSheet::END------------>:::::::");
		return mapping.findForward(url);
	}

	public ActionForward printGenPickup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("GenerateRunsheetAction::printGenPickup::START------------>:::::::");
		String url = null;
		HttpSession session = request.getSession();
		PickupRunsheetTO pkupRunsheetTO = (PickupRunsheetTO) session
				.getAttribute("pkupRunsheetTO");
		request.setAttribute("page", session.getAttribute("page"));
		request.setAttribute("pkupRunsheetTO",
				session.getAttribute("pkupRunsheetTO"));
		if (pkupRunsheetTO == null) {
			url = PickupManagementConstants.VIEW_GENERATE_PICKUP_RUNSHEET;
		} else {
			url = PickupManagementConstants.URL_PRINT_GENERATE_PICKUP_RUNSHEET;
		}
		session.removeAttribute("page");
		session.removeAttribute("pkupRunsheetTO");
		session.removeAttribute("runsheetList");
		LOGGER.debug("GenerateRunsheetAction::printGenPickup::END------------>:::::::");
		return mapping.findForward(url);
	}
}
