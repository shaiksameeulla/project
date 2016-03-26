/**
 * 
 */
package com.ff.web.pickup.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import org.springframework.beans.BeansException;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupAssignmentTypeTO;
import com.ff.pickup.PickupCustomerTO;
import com.ff.pickup.RunsheetAssignmentDetailTO;
import com.ff.pickup.RunsheetAssignmentTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.pickup.constant.UniversalPickupConstant;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.form.CreateRunsheetForm;
import com.ff.web.pickup.service.PickupAssignmentService;
import com.ff.web.pickup.service.PickupGatewayService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author kgajare
 * 
 */
public class PickupAssignmentAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreatePickupOrderAction.class);

	private PickupAssignmentService pickupAssignmentService;
	private PickupGatewayService pickupGatewayService;
	private PickupManagementCommonService pickupManagementCommonService;
	private OrganizationCommonService organizationCommonService;
	public transient JSONSerializer serializer;

	public void setPickupAssignmentService(
			PickupAssignmentService pickupAssignmentService) {
		this.pickupAssignmentService = pickupAssignmentService;
	}

	public void setPickupGatewayService(
			PickupGatewayService pickupGatewayService) {
		this.pickupGatewayService = pickupGatewayService;
	}

	@SuppressWarnings("static-access")
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: preparePage() :: Start --------> ::::::");
		CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
		RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm.getTo();
		HttpSession session = request.getSession(false);
		String forward = "";
		try {

			/*
			 * Get PickupAssignmentService
			 */
			this.pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			/*
			 * Get PickupGatewayService
			 */
			this.pickupGatewayService = (PickupGatewayService) springApplicationContext
					.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);

			/*
			 * PickupManagementCommonService
			 */
			this.pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
					.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);

			/*
			 * Set Created At office to same Office as logged in user
			 */
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute("user");
			runsheetAssignmentTO.setCreatedAtBranch(userInfoTO.getOfficeTo());
			runsheetAssignmentTO.setCreatedAt(userInfoTO.getOfficeTo()
					.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch, HO -
															// HUB

			/*
			 * Check if the assignment is created at Branch or HUB office type.
			 * If it is created at Branch then set Office Created For to same
			 * Office as logged in user
			 */
			forward = "branch";
			if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {

				runsheetAssignmentTO.setCreatedFor(userInfoTO.getOfficeTo()
						.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch,
																// HO -
				// HUB
			} else if (userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {

			}
			runsheetAssignmentTO.setRunsheetStatus("U");

			/*
			 * Get pickup assignment type list
			 */

			runsheetAssignmentTO
					.setPickupAssignmentTypeTOs(pickupAssignmentService
							.getPickupRunsheetType());

			if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				/*
				 * Get employee List if the Office Type of logged in user is
				 * Branch
				 */
				List<EmployeeTO> employeeTOs = pickupGatewayService
						.getBranchEmployees(userInfoTO.getOfficeTo()
								.getOfficeId());
				for (EmployeeTO employeeTO : employeeTOs) {
					String empName = employeeTO.getFirstName();
					if (StringUtils.isNotEmpty(employeeTO.getLastName()))
						empName = empName + " " + employeeTO.getLastName();

					employeeTO.setLabel(empName + " - "
							+ employeeTO.getEmpCode());
					employeeTO.setValue(employeeTO.getEmployeeId());
				}
				Collections.sort(employeeTOs);
				runsheetAssignmentTO.setEmployeeTOs(employeeTOs);
				prepareBranchDropDownForBranch(request);
				runsheetAssignmentTO.setCreatedForBranchId(userInfoTO
						.getOfficeTo().getOfficeId());

			} else {
				/*
				 * Get list of branches under hub if the Office Type of logged
				 * in user is HUB
				 */
				List<LabelValueBean> officeTOs = pickupGatewayService
						.getBranchesUnderHUB(userInfoTO.getOfficeTo()
								.getOfficeId());
				// As discussed and confirmed by Somesh, in Create pickup run
				// sheet assignment at Hub For Branch UI, in the branch dropdown
				// the logged in office (hub Office) should not appear.
				/*
				 * LabelValueBean hubOffice = new LabelValueBean(userInfoTO
				 * .getOfficeTo().getOfficeCode() + " - " +
				 * userInfoTO.getOfficeTo().getOfficeName(), userInfoTO
				 * .getOfficeTo().getOfficeId() + ""); officeTOs.add(hubOffice);
				 */
				runsheetAssignmentTO.setBranchTOs(officeTOs);
				prepareBranchDropDownForBranch(request);

			}

			/*
			 * set empty object of runsheet assignment detail
			 */
			List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = new ArrayList<RunsheetAssignmentDetailTO>();
			RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = new RunsheetAssignmentDetailTO();

			runsheetAssignmentDetailTO.setCreatedDate(new Date());
			runsheetAssignmentDetailTO.setCurrentlyMapped(false);

			runsheetAssignmentDetailTOs.add(runsheetAssignmentDetailTO);
			String assignmentDetails = CGJasonConverter.serializer.toJSON(
					runsheetAssignmentDetailTOs).toString();
			runsheetAssignmentTO.setAssignmentDetails(assignmentDetails);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePage------------>:::::::"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePage------------>:::::::"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePage------------>:::::::"
					+ e);
			getGenericException(request, e);
		}
		LOGGER.trace("PickupAssignmentAction :: preparePage() :: End --------> ::::::");
		return mapping.findForward(forward);
	}

	public ActionForward preparePageForHub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: preparePageForHub() :: Start --------> ::::::");
		RunsheetAssignmentTO runsheetAssignmentTO = new RunsheetAssignmentTO();
		String forward = "hub";
		try {
			/*
			 * Get PickupAssignmentService
			 */
			this.pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			/*
			 * Set Created At office to same Office as logged in user
			 */
			UserInfoTO userInfoTO = getLoginUserInfoTO(request);

			/*
			 * Check if the assignment is created at Branch or HUB office type.
			 * If it is created at Branch then set Office Created For to same
			 * Office as logged in user
			 */
			if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				setGlobalFieldsForHub(request, runsheetAssignmentTO);

			} else {
				// alert the User that Logged in office is not HUB and block the
				// user
				prepareUserMessage(request, runsheetAssignmentTO);
			}

			runsheetAssignmentTO.setRunsheetStatus("U");

			/*
			 * Get pickup assignment type list
			 */

			((CreateRunsheetForm) form).setTo(runsheetAssignmentTO);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePageForHub------------>:::::::"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePageForHub------------>:::::::"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::preparePageForHub------------>:::::::"
					+ e);
			getGenericException(request, e);
		}
		LOGGER.trace("PickupAssignmentAction :: preparePageForHub() :: End --------> ::::::");
		return mapping.findForward(forward);
	}

	/**
	 * @param request
	 * @param runsheetAssignmentTO
	 * @throws CGBusinessException
	 *             ,
	 */
	private void setGlobalFieldsForHub(HttpServletRequest request,
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: setGlobalFieldsForHub() :: Start --------> ::::::");
		removeSessionAttributesForHub(request);
		UserInfoTO userInfoTO = getLoginUserInfoTO(request);

		try {
			prepareBranchDropDownForHub(request);
			prepareEmpDropDownForHub(request);
			prepareRunsheetTypeDropDown(request);

			List<PickupAssignmentTypeTO> pickupAssignmentTypeTOs = pickupAssignmentService
					.getPickupRunsheetType();
			runsheetAssignmentTO
					.setPickupAssignmentTypeTOs(pickupAssignmentTypeTOs);
			runsheetAssignmentTO
					.setRunsheetTypeId(PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("ERROR :: PickupAssignmentAction::setGlobalFieldsForHub------------>:::::::"
					+ e);
			throw e;
		}
		runsheetAssignmentTO.setCreatedAtBranch(userInfoTO.getOfficeTo());
		runsheetAssignmentTO.setCreatedAt(userInfoTO.getOfficeTo()
				.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch, HO - HUB
		runsheetAssignmentTO.setCreatedAtBranchId(getLoginOfficeTO(request)
				.getOfficeId());
		runsheetAssignmentTO
				.setRadioButtonType(CommonConstants.OFF_TYPE_HUB_OFFICE);
		runsheetAssignmentTO
				.setAssignmentStatusGenerated(UdaanCommonConstants.RUNSHEET_ASSIGNMENT_STATUS_GENERATE);
		runsheetAssignmentTO
				.setAssignmentStatusUnused(UdaanCommonConstants.RUNSHEET_ASSIGNMENT_STATUS_UNUSED);
		LOGGER.trace("PickupAssignmentAction :: setGlobalFieldsForHub() :: End --------> ::::::");

	}

	/**
	 * @param request
	 * @param runsheetAssignmentTO
	 */
	private void prepareUserMessage(HttpServletRequest request,
			RunsheetAssignmentTO runsheetAssignmentTO) {
		ActionMessage actionMessage = null;
		actionMessage = new ActionMessage(UdaanWebErrorConstants.OFFICE_NOT_HUB);
		prepareActionMessage(request, actionMessage);
		runsheetAssignmentTO.setIsValidUser(false);
	}

	@SuppressWarnings("static-access")
	public void getBranchEmployees(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: getBranchEmployees() :: Start --------> ::::::");
		CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
		RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm.getTo();
		request.getSession();
		String jsonResult = null;
		PrintWriter out = null;
		/*
		 * Get employee List if the Office Type of logged in user is Branch
		 */
		try {
			response.setContentType("text/javascript");
			out = response.getWriter();
			List<EmployeeTO> employeeTOs = pickupGatewayService
					.getBranchEmployees(runsheetAssignmentTO
							.getCreatedForBranch().getOfficeId());
			for (EmployeeTO employeeTO : employeeTOs) {
				employeeTO.setLabel(employeeTO.getEmpCode() + " - "
						+ employeeTO.getFirstName() + " "
						+ employeeTO.getLastName());
				employeeTO.setValue(employeeTO.getEmployeeId());
			}

			jsonResult = CGJasonConverter.serializer.toJSON(employeeTOs)
					.toString();

			LOGGER.debug(jsonResult);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getBranchEmployees() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getBranchEmployees() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getBranchEmployees() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PickupAssignmentAction :: getBranchEmployees() :: End --------> ::::::");
	}

	@SuppressWarnings("static-access")
	public void ajaxEmployeesByOffice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: ajaxEmployeesByOffice() :: Start --------> ::::::");
		String jsonResult = "";
		Integer officeId = null;
		PrintWriter pw = null;
		List<EmployeeTO> employeeTOs = null;
		String branchId = request
				.getParameter(PickupManagementConstants.BRANCH_ID);
		try {
			pw = response.getWriter();
			response.setContentType("text/javascript");
			officeId = StringUtil.isInteger(branchId) ? StringUtil
					.parseInteger(branchId) : -1;
			// employeeTOs = pickupGatewayService.getBranchEmployees(officeId);

			organizationCommonService = (OrganizationCommonService) springApplicationContext
					.getBean(PickupManagementConstants.ORGANIZATION_COMMON_SERVICE);
			EmployeeTO emp = new EmployeeTO();
			emp.setOfficeId(officeId);
			employeeTOs = organizationCommonService
					.getAllEmployeesUnderRegion(emp);
			
			if (!StringUtil.isEmptyList(employeeTOs)) {
				Collections.sort(employeeTOs);
				jsonResult = CGJasonConverter.serializer.toJSON(employeeTOs)
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::ajaxEmployeesByOffice() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::ajaxEmployeesByOffice() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::ajaxEmployeesByOffice() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			pw.write(jsonResult);
			pw.flush();
			pw.close();
		}
		LOGGER.trace("PickupAssignmentAction :: ajaxEmployeesByOffice() :: End --------> ::::::");
	}

	/**
	 * to get customer list in Pick up assignment AT hub for Branch (at Brach
	 * for Branch)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */

	@SuppressWarnings("static-access")
	public void getCustomerListForAssignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: getCustomerListForAssignment() :: Start --------> ::::::");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
			String branchId = request
					.getParameter(PickupManagementConstants.BRANCH_ID);
			String employeeId = request
					.getParameter(PickupManagementConstants.EMPLOYEE_ID);

			RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm
					.getTo();
			response.setContentType("text/javascript");
			out = response.getWriter();

			HttpSession session = request.getSession();

			/*
			 * Get PickupAssignmentService
			 */
			this.pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			/*
			 * Get PickupGatewayService
			 */
			this.pickupGatewayService = (PickupGatewayService) springApplicationContext
					.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);

			/*
			 * Set Created At office to same Office as logged in user
			 */
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute("user");
			runsheetAssignmentTO.setCreatedAtBranch(userInfoTO.getOfficeTo());
			runsheetAssignmentTO.setCreatedAt(userInfoTO.getOfficeTo()
					.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch, HO -
			// HUB

			/*
			 * Check if the assignment is created at Branch or HUB office type.
			 * If it is created at Branch then set Office Created For to same
			 * Office as logged in user. Else if it is created at HUB and HUB
			 * radio button is selected then set Office Created For to same
			 * office as logged in user. Else if it is created at HUB and Branch
			 * radio button is selected then set Office Created For to object of
			 * selected branch's office.
			 */
			if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				runsheetAssignmentTO.setCreatedForBranch(userInfoTO
						.getOfficeTo());
				runsheetAssignmentTO.setCreatedFor(userInfoTO.getOfficeTo()
						.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch,
				// HO - HUB
			} else if (userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {

				OfficeTO createdForOfficeTO = pickupGatewayService
						.getOfficeDetails(StringUtil.parseInteger(branchId));
				runsheetAssignmentTO.setCreatedForBranch(createdForOfficeTO);
				runsheetAssignmentTO.setCreatedFor(createdForOfficeTO
						.getOfficeTypeTO().getOffcTypeCode());
			}
			runsheetAssignmentTO.getEmployeeTO().setEmployeeId(
					StringUtil.parseInteger(employeeId));

			RunsheetAssignmentTO runsheetAssignmentOutputTO = null;
			runsheetAssignmentOutputTO = pickupAssignmentService
					.getCustomerListForAssignment(runsheetAssignmentTO);

			runsheetAssignmentTO
					.setAssignmentHeaderId(runsheetAssignmentOutputTO
							.getAssignmentHeaderId());
			List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOList = runsheetAssignmentOutputTO
					.getRunsheetAssignmentDetailTOs();
			Collections.sort(runsheetAssignmentDetailTOList);
			runsheetAssignmentTO
					.setRunsheetAssignmentDetailTOs(runsheetAssignmentDetailTOList);

			PickupManagementCommonService pickupCommonService = (PickupManagementCommonService) getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
			String generatedStatus = pickupCommonService
					.getGenerationStatusOfPickupAssignment(runsheetAssignmentTO);

			runsheetAssignmentOutputTO.setAssignmentGenerated(generatedStatus);
			jsonResult = CGJasonConverter.serializer.toJSON(
					runsheetAssignmentOutputTO).toString();

			LOGGER.debug(jsonResult);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PickupAssignmentAction :: getCustomerListForAssignment() :: End --------> ::::::");
	}

	@SuppressWarnings("static-access")
	public void getAssignedCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: getAssignedCustomerList() :: Start --------> ::::::");
		String jsonResult = null;
		PrintWriter out = null;
		try {

			CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
			RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm
					.getTo();
			response.setContentType("text/javascript");
			out = response.getWriter();
			HttpSession session = request.getSession();

			/*
			 * Get PickupAssignmentService
			 */
			this.pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			/*
			 * Get PickupGatewayService
			 */
			this.pickupGatewayService = (PickupGatewayService) springApplicationContext
					.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);

			/*
			 * Set Created At office to same Office as logged in user
			 */
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute("user");
			runsheetAssignmentTO.setCreatedAtBranch(userInfoTO.getOfficeTo());
			runsheetAssignmentTO.setCreatedAt(userInfoTO.getOfficeTo()
					.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch, HO -
			// HUB

			/*
			 * Check if the assignment is created at Branch or HUB office type.
			 * If it is created at Branch then set Office Created For to same
			 * Office as logged in user. Else if it is created at HUB and HUB
			 * radio button is selected then set Office Created For to same
			 * office as logged in user. Else if it is created at HUB and Branch
			 * radio button is selected then set Office Created For to object of
			 * selected branch's office.
			 */
			if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
					.equalsIgnoreCase("BO")) {
				runsheetAssignmentTO.setCreatedForBranch(userInfoTO
						.getOfficeTo());
				runsheetAssignmentTO.setCreatedFor(userInfoTO.getOfficeTo()
						.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch,
				// HO - HUB
			} else if (userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode().equalsIgnoreCase("HO")) {

				if (runsheetAssignmentTO.getCreatedAt().equalsIgnoreCase(
						runsheetAssignmentTO.getCreatedFor())) {
					runsheetAssignmentTO.setCreatedForBranch(userInfoTO
							.getOfficeTo());
					runsheetAssignmentTO.setCreatedFor(userInfoTO.getOfficeTo()
							.getOfficeTypeTO().getOffcTypeCode()); // BO -
																	// Branch,
					// HO - HUB
				} else {
					OfficeTO createdForOfficeTO = pickupGatewayService
							.getOfficeDetails(runsheetAssignmentTO
									.getCreatedForBranch().getOfficeId());
					runsheetAssignmentTO
							.setCreatedForBranch(createdForOfficeTO);
					runsheetAssignmentTO.setCreatedFor(createdForOfficeTO
							.getOfficeTypeTO().getOffcTypeCode());
				}

			}

			RunsheetAssignmentTO runsheetAssignmentOutputTO = null;
			runsheetAssignmentOutputTO = pickupAssignmentService
					.getAssignedCustomerList(runsheetAssignmentTO);

			runsheetAssignmentTO
					.setAssignmentHeaderId(runsheetAssignmentOutputTO
							.getAssignmentHeaderId());
			runsheetAssignmentTO
					.setRunsheetAssignmentDetailTOs(runsheetAssignmentOutputTO
							.getRunsheetAssignmentDetailTOs());

			jsonResult = CGJasonConverter.serializer.toJSON(
					runsheetAssignmentOutputTO).toString();

			// Sorting
			List<RunsheetAssignmentDetailTO> runsheeetDtails = runsheetAssignmentOutputTO
					.getRunsheetAssignmentDetailTOs();
			if (runsheeetDtails != null && !runsheeetDtails.isEmpty())
				Collections.sort(runsheeetDtails);

			LOGGER.debug(jsonResult);

		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getAssignedCustomerList() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getAssignedCustomerList() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getAssignedCustomerList() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PickupAssignmentAction :: getAssignedCustomerList() :: End --------> ::::::");
	}

	public void savePickupAssignment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: savePickupAssignment() :: Start --------> ::::::");
		CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
		RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm.getTo();
		PrintWriter out = null;
		String jsonResult = null;
		try {
			out = response.getWriter();

			setFieldsFromSession(request, runsheetAssignmentTO);
			List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = prepareDetailsTO(runsheetAssignmentTO);
			runsheetAssignmentTO
					.setRunsheetAssignmentDetailTOs(runsheetAssignmentDetailTOs);
			runsheetAssignmentTO = pickupAssignmentService
					.savePickupAssignment(runsheetAssignmentTO);
			if (runsheetAssignmentTO.isSaved()) {
				//calling TwoWayWrite service to save same in central
				twoWayWrite(runsheetAssignmentTO);
				
				jsonResult = prepareCommonException(
						UniversalPickupConstant.RESP_SUCCESS,
						getMessageFromErrorBundle(request,
								UniversalPickupConstant.RESP_SUCCESS_MSG, null));
			} else {
				jsonResult = prepareCommonException(
						UniversalPickupConstant.RESP_ERROR,
						getMessageFromErrorBundle(request,
								UniversalPickupConstant.RESP_ERROR_MSG, null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::savePickupAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::savePickupAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::savePickupAssignment() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("PickupAssignmentAction :: savePickupAssignment() :: End --------> ::::::");
	}

	private void setFieldsFromSession(HttpServletRequest request,
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: setFieldsFromSession() :: Start --------> ::::::");
		HttpSession session = request.getSession();
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute("user");
		runsheetAssignmentTO.setCreatedAtBranch(userInfoTO.getOfficeTo());
		runsheetAssignmentTO.setCreatedAt(userInfoTO.getOfficeTo()
				.getOfficeTypeTO().getOffcTypeCode());
		OfficeTO createdForOfficeTO = null;
		if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
				.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
			runsheetAssignmentTO.setCreatedForBranch(userInfoTO.getOfficeTo());
			runsheetAssignmentTO.setCreatedFor(userInfoTO.getOfficeTo()
					.getOfficeTypeTO().getOffcTypeCode()); // BO - Branch,HO -
															// HUB

		} else if (userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeCode()
				.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			createdForOfficeTO = pickupGatewayService
					.getOfficeDetails(runsheetAssignmentTO
							.getCreatedForBranchId());
			runsheetAssignmentTO.setCreatedForBranch(createdForOfficeTO);
			runsheetAssignmentTO.setCreatedFor(createdForOfficeTO
					.getOfficeTypeTO().getOffcTypeCode());

		}
		Integer assignmentHeaderId = runsheetAssignmentTO
				.getAssignmentHeaderId();
		if (null == assignmentHeaderId || assignmentHeaderId == 0) {
			runsheetAssignmentTO.setAssignmentHeaderId(null);
			runsheetAssignmentTO.setDataTransferStatus("P");
			runsheetAssignmentTO.setCreatedBy(userInfoTO.getUserto()
					.getUserId());
			runsheetAssignmentTO.setCreatedDate(DateUtil
					.getDDMMYYYYDateString(new Date()));
		} else {
			runsheetAssignmentTO.setUpdatedBy(userInfoTO.getUserto()
					.getUserId());
			runsheetAssignmentTO.setUpdatedDate(DateUtil
					.getDDMMYYYYDateString(new Date()));
		}
		LOGGER.trace("PickupAssignmentAction :: setFieldsFromSession() :: End --------> ::::::");
	}

	private List<RunsheetAssignmentDetailTO> prepareDetailsTO(
			RunsheetAssignmentTO runsheetAssignmentTO) {
		LOGGER.trace("PickupAssignmentAction :: prepareDetailsTO() :: Start --------> ::::::");
		List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = null;
		String[] assignmentDetailIds = runsheetAssignmentTO
				.getAssignmentDetailIds() != null ? runsheetAssignmentTO
				.getAssignmentDetailIds().split(",") : null;
		String[] orderNumbers = runsheetAssignmentTO.getOrderNumbers() != null ? runsheetAssignmentTO
				.getOrderNumbers().split(",") : null;
		String[] customerCodes = runsheetAssignmentTO.getCustomerCodes() != null ? runsheetAssignmentTO
				.getCustomerCodes().split(",") : null;
		String[] currentSelected = runsheetAssignmentTO.getCurrentSelected() != null ? runsheetAssignmentTO
				.getCurrentSelected().split(",") : null;
		String[] previousSelected = runsheetAssignmentTO.getPreviousSelected() != null ? runsheetAssignmentTO
				.getPreviousSelected().split(",") : null;
		String[] pickupLocIds = runsheetAssignmentTO.getPickupLocIds() != null ? runsheetAssignmentTO
				.getPickupLocIds().split(",") : null;
		String[] revPickupIds = runsheetAssignmentTO.getRevPickupIds() != null ? runsheetAssignmentTO
				.getRevPickupIds().split(",") : null;

		if (previousSelected != null && previousSelected.length > 0) {
			runsheetAssignmentDetailTOs = new ArrayList<RunsheetAssignmentDetailTO>(
					previousSelected.length);
			for (int i = 0; i < previousSelected.length; i++) {
				try {
					boolean isCurrentlySelected = currentSelected != null
							&& currentSelected.length > i
							&& currentSelected[i].equals("checked") ? true
							: false;
					boolean isPrevSelected = previousSelected != null
							&& previousSelected.length > i
							&& previousSelected[i].equals("true") ? true
							: false;
					String detailId = assignmentDetailIds != null
							&& assignmentDetailIds.length > i ? assignmentDetailIds[i]
							: null;
					if (isCurrentlySelected || isPrevSelected
							|| !detailId.equals("0")) {
						RunsheetAssignmentDetailTO detailTo = new RunsheetAssignmentDetailTO();

						detailTo.setCurrentlyMapped(isCurrentlySelected);
						detailTo.setPreviouslyMapped(isPrevSelected);
						if (assignmentDetailIds != null
								&& assignmentDetailIds.length > i) {
							detailTo.setAssignmentDetailId(!detailId
									.equals("0") ? Integer.parseInt(detailId)
									: null);
						}

						if (orderNumbers != null && orderNumbers.length > i) {
							String orderNumber = orderNumbers[i];
							detailTo.setReversePickupOrderNumber(!orderNumber
									.equals("-") ? orderNumber : null);
							if (orderNumber.equals("-")) {
								detailTo.setPickupType("S");
							} else {
								detailTo.setPickupType("R");
							}
						}

						if (customerCodes != null && customerCodes.length > i) {
							String customerCode = customerCodes[i];
							if (!customerCode.equals("")) {
								detailTo.setCustomerCode(customerCode);
							}
						}
						if (pickupLocIds != null && pickupLocIds.length > i) {
							String pickupLocId = pickupLocIds[i];
							detailTo.setPickupLocationId(pickupLocId != null
									&& !pickupLocId.equals("0") ? Integer
									.parseInt(pickupLocId) : null);
						}
						if (revPickupIds != null && revPickupIds.length > 0) {
							String revPicupId = revPickupIds[i];
							detailTo.setRevPickupId(revPicupId != null
									&& !revPicupId.equals("0") ? Integer
									.parseInt(revPicupId) : null);
						}
						detailTo.setCreatedDate(DateUtil.parseStringDateToDDMMYYYYHHMMSSFormat(runsheetAssignmentTO.getCreatedDate()));
						detailTo.setCreatedBy(runsheetAssignmentTO.getCreatedBy());
						detailTo.setUpdatedBy(runsheetAssignmentTO.getUpdatedBy());
						runsheetAssignmentDetailTOs.add(detailTo);
					}
				} catch (Exception ex) {
					LOGGER.error("Exception Occured in::PickupAssignmentAction::prepareDetailsTO() :: "
							+ ex);
					throw ex;
				}
			}
		}
		LOGGER.trace("PickupAssignmentAction :: prepareDetailsTO() :: End --------> ::::::");
		return runsheetAssignmentDetailTOs;

	}

	/**
	 * @param request
	 * @throws throws CGBusinessException, CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void prepareRunsheetTypeDropDown(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: prepareRunsheetTypeDropDown() :: Start --------> ::::::");
		try {
			Map<Integer, String> runsheetType = null;
			HttpSession session = request.getSession(false);
			pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);
			runsheetType = (Map<Integer, String>) session
					.getAttribute(PickupManagementConstants.RUNSHEET_TYPE_DROP_DOWN);
			if (CGCollectionUtils.isEmpty(runsheetType)) {
				List<PickupAssignmentTypeTO> TypeTOList = null;

				TypeTOList = pickupAssignmentService.getPickupRunsheetType();

				if (!StringUtil.isEmptyColletion(TypeTOList)) {
					runsheetType = new HashMap<>(TypeTOList.size());
					for (PickupAssignmentTypeTO typeTo : TypeTOList) {
						runsheetType.put(typeTo.getAssignmentTypeId(),
								typeTo.getAssignmentTypeDescription());
					}
					session.setAttribute(
							PickupManagementConstants.RUNSHEET_TYPE_DROP_DOWN,
							runsheetType);
					request.setAttribute(
							PickupManagementConstants.RUNSHEET_TYPE_DROP_DOWN,
							runsheetType);
				}
			} else {
				request.setAttribute(
						PickupManagementConstants.RUNSHEET_TYPE_DROP_DOWN,
						runsheetType);
			}
		} catch (Exception e) {
			throw e;
		}
		LOGGER.trace("PickupAssignmentAction :: prepareRunsheetTypeDropDown() :: End --------> ::::::");
	}

	/**
	 * Name : getLoginUserInfoTO purpose : to get UserInfoTO from Session Input
	 * : HttpServletRequest request return : UserInfoTO
	 */
	public UserInfoTO getLoginUserInfoTO(HttpServletRequest request) {
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		return userInfo;
	}

	/**
	 * Name : getLoginEmpTO purpose : to get EmployeeTO from Session Input :
	 * HttpServletRequest request return : EmployeeTO
	 */
	public EmployeeTO getLoginEmpTO(HttpServletRequest request) {
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if (userInfo != null) {
			EmployeeUserTO empUserTo = userInfo.getEmpUserTo();
			if (empUserTo != null) {
				return empUserTo.getEmpTO();
			}
		}
		return null;
	}

	/**
	 * Name : getLoginOfficeTO purpose : to get OfficeTO from Session Input :
	 * HttpServletRequest request return : OfficeTO
	 */
	public OfficeTO getLoginOfficeTO(HttpServletRequest request) {
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if (userInfo != null) {
			return userInfo.getOfficeTo();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void prepareBranchDropDownForHub(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: prepareBranchDropDownForHub() :: Start --------> ::::::");
		OfficeTO loggedInOfficeTO = getLoginOfficeTO(request);
		OfficeTO branchTO = new OfficeTO();
		branchTO.setOfficeId(loggedInOfficeTO.getOfficeId());
		branchTO.setReportingHUB(loggedInOfficeTO.getOfficeId());
		List<OfficeTO> officeList = null;
		Map<Integer, String> branchMap = null;
		HttpSession session = request.getSession(false);
		branchMap = (Map<Integer, String>) session
				.getAttribute(PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN_HUB);
		this.pickupGatewayService = (PickupGatewayService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
		if (CGCollectionUtils.isEmpty(branchMap)) {
			try {
				officeList = pickupGatewayService
						.getOfficeListByOfficeTO(branchTO);
			} catch (CGBusinessException | CGSystemException e) {
				LOGGER.error("Exception Occured in::PickupAssignmentAction::prepareBranchDropDownForHub() :: "
						+ e);
				throw e;
			}
			if (!StringUtil.isEmptyColletion(officeList)) {
				branchMap = new HashMap<>(officeList.size());
				for (OfficeTO officeTo : officeList) {
					branchMap.put(officeTo.getOfficeId(),
							officeTo.getOfficeCode() + CommonConstants.HYPHEN
									+ officeTo.getOfficeName());
				}
			} else {
				branchMap = new HashMap<>(1);
				branchMap.put(
						loggedInOfficeTO.getOfficeId(),
						loggedInOfficeTO.getOfficeCode()
								+ CommonConstants.HYPHEN
								+ loggedInOfficeTO.getOfficeName());

			}
			session.setAttribute(
					PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN_HUB,
					branchMap);
		}
		request.setAttribute(
				PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN_HUB,
				branchMap);
		LOGGER.trace("PickupAssignmentAction :: prepareBranchDropDownForHub() :: End --------> ::::::");
	}

	public void prepareBranchDropDownForBranch(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: prepareBranchDropDownForBranch() :: Start --------> ::::::");
		OfficeTO loggedInOfficeTO = getLoginOfficeTO(request);
		OfficeTO branchTO = new OfficeTO();

		if (!StringUtil.isNull(loggedInOfficeTO)
				&& !StringUtil.isNull(loggedInOfficeTO.getOfficeTypeTO())) {
			String officeType = loggedInOfficeTO.getOfficeTypeTO()
					.getOffcTypeCode();
			if (!StringUtil.isStringEmpty(officeType)
					&& officeType
							.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				branchTO.setOfficeId(loggedInOfficeTO.getOfficeId());
			} else if (!StringUtil.isStringEmpty(officeType)
					&& officeType
							.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				branchTO.setReportingHUB(loggedInOfficeTO.getOfficeId());
			} else {
				branchTO.setOfficeId(loggedInOfficeTO.getOfficeId());
			}
		} else {
			branchTO.setOfficeId(loggedInOfficeTO.getOfficeId());
		}

		getBranchList(request, branchTO);
		LOGGER.trace("PickupAssignmentAction :: prepareBranchDropDownForBranch() :: End --------> ::::::");
	}

	/**
	 * @param request
	 * @param branchTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	private void getBranchList(HttpServletRequest request, OfficeTO branchTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: getBranchList() :: Start --------> ::::::");
		List<OfficeTO> officeList = null;
		Map<Integer, String> branchMap = null;
		HttpSession session = request.getSession(false);
		branchMap = (Map<Integer, String>) session
				.getAttribute(PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN);
		this.pickupGatewayService = (PickupGatewayService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
		if (CGCollectionUtils.isEmpty(branchMap)) {
			officeList = pickupGatewayService.getOfficeListByOfficeTO(branchTO);
			if (!StringUtil.isEmptyColletion(officeList)) {
				branchMap = new HashMap<>(officeList.size());
				for (OfficeTO officeTo : officeList) {
					branchMap.put(officeTo.getOfficeId(),
							officeTo.getOfficeCode() + CommonConstants.HYPHEN
									+ officeTo.getOfficeName());
				}
				branchMap = CGCollectionUtils.sortByValue(branchMap);
				session.setAttribute(
						PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN,
						branchMap);
			}
		}
		request.setAttribute(
				PickupManagementConstants.RUNSHEET_BRANCH_DROP_DOWN, branchMap);

		LOGGER.trace("PickupAssignmentAction :: getBranchList() :: End --------> ::::::");
	}

	@SuppressWarnings("unchecked")
	public void prepareEmpDropDownForHub(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: prepareEmpDropDownForHub() :: Start --------> ::::::");
		OfficeTO loggedInOfficeTO = getLoginOfficeTO(request);
		this.pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
		Map<Integer, String> empMap = null;
		HttpSession session = request.getSession(false);
		empMap = (Map<Integer, String>) session
				.getAttribute(PickupManagementConstants.RUNSHEET_EMP_DROP_DOWN);

		if (CGCollectionUtils.isEmpty(empMap)) {
			// try {
			EmployeeTO emp = new EmployeeTO();
			//emp.setOfficeId(loggedInOfficeTO.getOfficeId());
			//empMap = pickupManagementCommonService.getEmployeesByEmpTO(emp);
			//Production defect : artf3500507 
			empMap = pickupManagementCommonService.getEmployeesByOfficeId(loggedInOfficeTO.getOfficeId());
			if (!CGCollectionUtils.isEmpty(empMap)) {
				session.setAttribute(
						PickupManagementConstants.RUNSHEET_EMP_DROP_DOWN,
						empMap);
				request.setAttribute(
						PickupManagementConstants.RUNSHEET_EMP_DROP_DOWN,
						empMap);

			}
		} else {
			request.setAttribute(
					PickupManagementConstants.RUNSHEET_EMP_DROP_DOWN, empMap);
		}
		LOGGER.trace("PickupAssignmentAction :: prepareEmpDropDownForHub() :: End --------> ::::::");
	}

	@SuppressWarnings("static-access")
	public void getCustomerListForCreatePickupRunsheetForHub(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: getCustomerListForCreatePickupRunsheetForHub() :: Start --------> ::::::");

		List<PickupCustomerTO> list = null;
		String jsonResult = "";
		PrintWriter pw = null;

		try {
			CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
			RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm
					.getTo();
			pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			pw = response.getWriter();
			response.setContentType("text/javascript");
			list = pickupAssignmentService
					.getCustomerDetailsForRunsheetAtHub(runsheetAssignmentTO);
			// Added by Narasimha
			if (!StringUtil.isEmptyList(list))
				Collections.sort(list);
			list = appendCustomersFromCreatedAssignmentDetailsForHub(request,
					list, runsheetAssignmentTO);

			// get the generated status
			PickupManagementCommonService pickupCommonService = (PickupManagementCommonService) getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
			String generatedStatus = pickupCommonService
					.getGenerationStatusOfPickupAssignment(runsheetAssignmentTO);

			RunsheetAssignmentTO runsheetAssignmentOutputTO = new RunsheetAssignmentTO();
			runsheetAssignmentOutputTO.setCustomerList(list);
			runsheetAssignmentOutputTO.setAssignmentGenerated(generatedStatus);

			if (!StringUtil.isEmptyList(list)) {
				jsonResult = CGJasonConverter.serializer.toJSON(
						runsheetAssignmentOutputTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForCreatePickupRunsheetForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForCreatePickupRunsheetForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCustomerListForCreatePickupRunsheetForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			pw.write(jsonResult);
			pw.flush();
			pw.close();
		}

		LOGGER.trace("PickupAssignmentAction :: getCustomerListForCreatePickupRunsheetForHub() :: End --------> ::::::");

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            It populates Grid in the Screen
	 */
	@SuppressWarnings("static-access")
	public void getCreatedAssignmentDetailsForHub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: getCreatedAssignmentDetailsForHub() :: Start --------> ::::::");

		String jsonResult = "";
		PrintWriter pw = null;
		List<RunsheetAssignmentDetailTO> list = null;
		Map<Integer, RunsheetAssignmentDetailTO> masterPickup = null;
		HttpSession session = request.getSession(false);
		try {
			CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
			RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm
					.getTo();
			pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);

			pw = response.getWriter();
			response.setContentType("text/javascript");
			list = pickupAssignmentService
					.getAssignmentDetailsForRunsheetAtHub(runsheetAssignmentTO);
			// Removing session
			// removeSessionAttributesForHub(request);
			if (!StringUtil.isEmptyList(list)
					&& !StringUtil.isEmptyInteger(runsheetAssignmentTO
							.getRunsheetTypeId())
					&& runsheetAssignmentTO.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER) {
				masterPickup = new HashMap<>(list.size());
				session.removeAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_SAVE);
				for (RunsheetAssignmentDetailTO detailTo : list) {
					masterPickup
							.put(detailTo.getAssignmentDetailId(), detailTo);
				}
				session.setAttribute(
						PickupManagementConstants.RUNSHEET_DETAIL_FOR_SAVE,
						masterPickup);
			}
			processCreatedAssignmentDetailsForHub(request, list);
			if (!StringUtil.isEmptyList(list)) {
				jsonResult = CGJasonConverter.serializer.toJSON(list)
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCreatedAssignmentDetailsForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCreatedAssignmentDetailsForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::getCreatedAssignmentDetailsForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			pw.write(jsonResult);
			pw.flush();
			pw.close();
		}

		LOGGER.trace("PickupAssignmentAction :: getCreatedAssignmentDetailsForHub() :: End --------> ::::::");
	}

	public void savePickupAssignmentForHub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PickupAssignmentAction :: savePickupAssignmentForHub() :: Start --------> ::::::");
		CreateRunsheetForm createRunsheetForm = (CreateRunsheetForm) form;
		RunsheetAssignmentTO runsheetAssignmentTO = createRunsheetForm.getTo();
		java.io.PrintWriter out = null;
		String jsonResult = null;
		try {
			out = response.getWriter();
			pickupAssignmentService = (PickupAssignmentService) springApplicationContext
					.getBean(PickupManagementConstants.CREATE_RUNSHEET_ASSIGNMENT_SERVICE);
			pickupGatewayService = (PickupGatewayService) springApplicationContext
					.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			prepareRunsheetAssignmentTO(request, runsheetAssignmentTO);
			runsheetAssignmentTO = pickupAssignmentService
					.savePickupAssignment(runsheetAssignmentTO);
			if (runsheetAssignmentTO.isSaved()) {
				//calling TwoWayWrite service to save same in central
				twoWayWrite(runsheetAssignmentTO);
				
				jsonResult = prepareCommonException(
						UniversalPickupConstant.RESP_SUCCESS,
						getMessageFromErrorBundle(request,
								UniversalPickupConstant.RESP_SUCCESS_MSG, null));
			} else {
				jsonResult = prepareCommonException(
						UniversalPickupConstant.RESP_ERROR,
						getMessageFromErrorBundle(request,
								UniversalPickupConstant.RESP_ERROR_MSG, null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::savePickupAssignmentForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentAction::savePickupAssignmentForHub() :: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("PickupAssignmentAction :: savePickupAssignmentForHub ::Exception"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PickupAssignmentAction :: savePickupAssignmentForHub() :: End --------> ::::::");
	}

	/**
	 * @param request
	 * @param runsheetAssignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void prepareRunsheetAssignmentTO(HttpServletRequest request,
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupAssignmentAction :: prepareRunsheetAssignmentTO() :: Start --------> ::::::");
		runsheetAssignmentTO.setCreatedFor(runsheetAssignmentTO
				.getRadioButtonType());
		runsheetAssignmentTO.setCreatedAt(CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (runsheetAssignmentTO.getCreatedFor().equalsIgnoreCase(
				CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			OfficeTO createdAtOfficeTO = pickupGatewayService
					.getOfficeDetails(runsheetAssignmentTO
							.getCreatedAtBranchId());
			runsheetAssignmentTO.setCreatedAtBranch(createdAtOfficeTO);
			runsheetAssignmentTO.setCreatedForBranch(createdAtOfficeTO);

		} else {
			if (!StringUtil.isEmptyInteger(runsheetAssignmentTO
					.getCreatedForBranchId())) {
				OfficeTO createdForOfficeTO = pickupGatewayService
						.getOfficeDetails(runsheetAssignmentTO
								.getCreatedForBranchId());
				runsheetAssignmentTO.setCreatedForBranch(createdForOfficeTO);
			}
			if (!StringUtil.isEmptyInteger(runsheetAssignmentTO
					.getCreatedAtBranchId())) {
				OfficeTO createdAtOfficeTO = pickupGatewayService
						.getOfficeDetails(runsheetAssignmentTO
								.getCreatedAtBranchId());
				runsheetAssignmentTO.setCreatedAtBranch(createdAtOfficeTO);
			}
		}
		if (runsheetAssignmentTO.getEmployeeTO() != null) {
			runsheetAssignmentTO.getEmployeeTO().setEmployeeId(
					runsheetAssignmentTO.getEmployeeId());
		}
		Integer assignmentHeaderId = runsheetAssignmentTO
				.getAssignmentHeaderId();
		UserInfoTO userInfoTO = getLoginUserInfoTO(request);
		if (null == assignmentHeaderId || assignmentHeaderId == 0) {
			runsheetAssignmentTO.setAssignmentHeaderId(null);
			runsheetAssignmentTO.setDataTransferStatus("P");
			runsheetAssignmentTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			runsheetAssignmentTO.setCreatedDate(DateUtil
					.getDDMMYYYYDateString(new Date()));
		} else {
			runsheetAssignmentTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			runsheetAssignmentTO.setUpdatedBy(userInfoTO.getUserto().getUserId());
			runsheetAssignmentTO.setUpdatedDate(DateUtil
					.getDDMMYYYYDateString(new Date()));
		}
		List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = null;
		Map<Integer, RunsheetAssignmentDetailTO> masterPickup = null;
		Set<Integer> unmappedDetailIds = null;
		HttpSession session = request.getSession(false);
		if (!StringUtil
				.isEmptyInteger(runsheetAssignmentTO.getRunsheetTypeId())
				&& runsheetAssignmentTO.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER) {
			masterPickup = (Map<Integer, RunsheetAssignmentDetailTO>) session
					.getAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_SAVE);
		}
		runsheetAssignmentTO.setDataTransferStatus("P");
		int size = !StringUtil.isEmpty(runsheetAssignmentTO.getRowCustomerId()) ? runsheetAssignmentTO
				.getRowCustomerId().length : 0;
		Set<Integer> runsheetDtlsIds = null;
		if (!CGCollectionUtils.isEmpty(masterPickup)) {
			runsheetDtlsIds = masterPickup.keySet();
			unmappedDetailIds = new HashSet<>(runsheetDtlsIds.size());
			unmappedDetailIds.addAll(runsheetDtlsIds);
		}
		if (size > 0) {
			runsheetAssignmentDetailTOs = new ArrayList<>(size);
			Integer customerId[] = runsheetAssignmentTO.getRowCustomerId();
			Integer customerBranchId[] = runsheetAssignmentTO
					.getRowCustomerBranchId();
			Integer assignmentDetailId[] = runsheetAssignmentTO
					.getRowAssignmentDetailId();
			Integer pickupLocationId[] = runsheetAssignmentTO
					.getRowPickupLocationId();
			Integer revPickupDtlId[] = runsheetAssignmentTO
					.getRowRevPickupDtlId();
			String orderNumber[] = runsheetAssignmentTO.getRowOrderNumber();
			String pickupType[] = runsheetAssignmentTO.getRowPickupType();

			for (int counter = 0; counter < size; counter++) {
				if (!StringUtil.isEmpty(customerId)
						&& !StringUtil.isEmpty(pickupType)) {
					RunsheetAssignmentDetailTO detailTO = new RunsheetAssignmentDetailTO();
					if (!StringUtil.isEmptyInteger(customerId[counter])) {
						detailTO.setCustomerId(customerId[counter]);
					}
					if (!StringUtil.isEmpty(assignmentDetailId)
							&& !StringUtil
									.isEmptyInteger(assignmentDetailId[counter])) {
						detailTO.setAssignmentDetailId(assignmentDetailId[counter]);
						if (!CGCollectionUtils.isEmpty(runsheetDtlsIds)
								&& runsheetDtlsIds
										.contains(assignmentDetailId[counter])) {
							unmappedDetailIds
									.remove(assignmentDetailId[counter]);
						}
						detailTO.setCurrentlyMapped(true);
					} else {
						detailTO.setCurrentlyMapped(true);
					}
					if (!StringUtil.isEmpty(pickupLocationId)
							&& !StringUtil
									.isEmptyInteger(pickupLocationId[counter])) {
						detailTO.setPickupLocationId(pickupLocationId[counter]);
					}
					if (!StringUtil.isEmpty(revPickupDtlId)
							&& !StringUtil
									.isEmptyInteger(revPickupDtlId[counter])) {
						detailTO.setReversePickupOrderDetailId(revPickupDtlId[counter]);
						detailTO.setRevPickupId(revPickupDtlId[counter]);
					}
					if (!StringUtil.isEmpty(orderNumber)
							&& !StringUtil.isStringEmpty(orderNumber[counter])) {
						detailTO.setReversePickupOrderNumber(orderNumber[counter]);
					}
					if (!StringUtil.isEmpty(pickupType)
							&& !StringUtil.isStringEmpty(pickupType[counter])) {
						detailTO.setPickupType(pickupType[counter]);
					}
					if (!StringUtil.isEmpty(customerBranchId)
							&& !StringUtil
									.isEmptyInteger(customerBranchId[counter])) {
						detailTO.setPickupBranchId(customerBranchId[counter]);
					}
					detailTO.setCreatedBy(runsheetAssignmentTO.getCreatedBy());
					detailTO.setUpdatedBy(runsheetAssignmentTO.getUpdatedBy());
					
					runsheetAssignmentDetailTOs.add(detailTO);

				}

			}

		} else {
			size = CGCollectionUtils.isEmpty(unmappedDetailIds) ? 0
					: unmappedDetailIds.size();
			runsheetAssignmentDetailTOs = new ArrayList<>(size);
		}
		// Identify and add RunsheetAssignmentDetailTO which is not available in
		// runsheetAssignmentDetailTOs list but it's in session variable
		// masterPickup
		if (!CGCollectionUtils.isEmpty(unmappedDetailIds)) {
			for (Integer key : unmappedDetailIds) {
				RunsheetAssignmentDetailTO detailTO = (RunsheetAssignmentDetailTO) masterPickup
						.get(key);
				detailTO.setCurrentlyMapped(false);
				detailTO.setRevPickupId(detailTO
						.getReversePickupOrderDetailId());
				runsheetAssignmentDetailTOs.add(detailTO);
			}
		}
		runsheetAssignmentTO
				.setRunsheetAssignmentDetailTOs(runsheetAssignmentDetailTOs);
		LOGGER.trace("PickupAssignmentAction :: prepareRunsheetAssignmentTO() :: End --------> ::::::");
	}

	/**
	 * 
	 * @param request
	 * @param list
	 *            Purpose : holds the created assignment details List,it will be
	 *            appended at the time of getting the Customer List retrieval
	 */
	private void processCreatedAssignmentDetailsForHub(
			HttpServletRequest request, List<RunsheetAssignmentDetailTO> list) {
		LOGGER.trace("PickupAssignmentAction :: processCreatedAssignmentDetailsForHub() :: Start --------> ::::::");
		HttpSession session = request.getSession(false);
		Map<Integer, List<RunsheetAssignmentDetailTO>> officeWiseDetails = null;
		if (!StringUtil.isEmptyList(list)) {
			session.removeAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_CUSTOMER_LIST);
			officeWiseDetails = new TreeMap<>();
			for (RunsheetAssignmentDetailTO detailsTO : list) {
				List<RunsheetAssignmentDetailTO> runsheetList = null;
				Integer branchId = detailsTO.getPickupBranchId();
				if (!StringUtil.isEmptyInteger(branchId)
						&& officeWiseDetails.containsKey(branchId)) {
					runsheetList = officeWiseDetails.get(branchId);
					runsheetList.add(detailsTO);
					officeWiseDetails.put(branchId, runsheetList);
				} else {
					runsheetList = new ArrayList<>(list.size() / 2);
					runsheetList.add(detailsTO);
					officeWiseDetails.put(branchId, runsheetList);
				}
			}
			session.setAttribute(
					PickupManagementConstants.RUNSHEET_DETAIL_FOR_CUSTOMER_LIST,
					officeWiseDetails);
		}
		LOGGER.trace("PickupAssignmentAction :: processCreatedAssignmentDetailsForHub() :: End --------> ::::::");
	}

	@SuppressWarnings("unchecked")
	private List<PickupCustomerTO> appendCustomersFromCreatedAssignmentDetailsForHub(
			HttpServletRequest request, List<PickupCustomerTO> list,
			RunsheetAssignmentTO runsheetAssignmentTO) {
		LOGGER.trace("PickupAssignmentAction :: appendCustomersFromCreatedAssignmentDetailsForHub() :: Start --------> ::::::");
		HttpSession session = request.getSession(false);
		if (!StringUtil
				.isEmptyInteger(runsheetAssignmentTO.getRunsheetTypeId())
				&& runsheetAssignmentTO.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_TEMPORARY) {
			return list;
		}

		List<PickupCustomerTO> finalResult = null;
		Map<Integer, List<RunsheetAssignmentDetailTO>> officeWiseDetails = null;
		officeWiseDetails = (Map<Integer, List<RunsheetAssignmentDetailTO>>) session
				.getAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_CUSTOMER_LIST);
		Integer createdForOfficeId = runsheetAssignmentTO
				.getCreatedForBranchId();
		if (!CGCollectionUtils.isEmpty(officeWiseDetails)
				&& !StringUtil.isEmptyInteger(createdForOfficeId)
				&& officeWiseDetails.containsKey(createdForOfficeId)) {
			List<RunsheetAssignmentDetailTO> assignmentDtls = officeWiseDetails
					.get(createdForOfficeId);
			if (CGCollectionUtils.isEmpty(list)) {
				finalResult = new ArrayList<>(assignmentDtls.size());
			} else {
				finalResult = list;
			}
			// Commented because getting duplicate customers in assigned
			// runsheet
			/*
			 * for (RunsheetAssignmentDetailTO detailsTO : assignmentDtls) {
			 * PickupCustomerTO customerTO = new PickupCustomerTO();
			 * customerTO.setCustomerId(detailsTO.getCustomerId());
			 * customerTO.setCustomerCode(detailsTO.getCustomerCode());
			 * customerTO.setBusinessName(detailsTO.getCustomerName());
			 * customerTO.setPickupType(detailsTO.getPickupType());
			 * customerTO.setPickupLocation(detailsTO.getPickupLocation());
			 * customerTO.setPickupLocationId(detailsTO.getPickupLocationId());
			 * customerTO.setOrderNumber(detailsTO
			 * .getReversePickupOrderNumber()); customerTO.setDetailId(detailsTO
			 * .getReversePickupOrderDetailId());
			 * customerTO.setAssignmentDetailId(detailsTO
			 * .getAssignmentDetailId());
			 * customerTO.setHeaderId(detailsTO.getRevPickupId());
			 * finalResult.add(customerTO); }
			 */
			// Added By Narasimha
			Collections.sort(finalResult);
		} else {
			finalResult = list;
		}
		LOGGER.trace("PickupAssignmentAction :: appendCustomersFromCreatedAssignmentDetailsForHub() :: End --------> ::::::");
		return finalResult;
	}

	private void removeSessionAttributesForHub(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_CUSTOMER_LIST);
		session.removeAttribute(PickupManagementConstants.RUNSHEET_DETAIL_FOR_SAVE);
	}
	private void twoWayWrite(RunsheetAssignmentTO runsheetAssignmentTO) {
		try{
			PickupGatewayService pickupGatewayService = (PickupGatewayService) getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			pickupGatewayService.twoWayWrite(runsheetAssignmentTO.getPickupTwoWayWriteTO());
		} catch (Exception e) {
			LOGGER.error("PickupAssignmentAction:: twoWayWrite", e);
		}
	}
}
