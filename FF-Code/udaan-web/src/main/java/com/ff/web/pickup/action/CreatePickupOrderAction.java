package com.ff.web.pickup.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.business.CustomerTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.form.CreatePickupOrderForm;
import com.ff.web.pickup.service.CreatePickupOrderService;
import com.ff.web.pickup.service.PickupGatewayService;

public class CreatePickupOrderAction extends CGBaseAction {

	private PickupManagementCommonService pickupManagementCommonService;

	private CreatePickupOrderService pickupManagementService;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreatePickupOrderAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	/**
	 * @Method : preparePage
	 * @param :
	 * @Desc : Populates the page with initial values
	 * @return : Forwarding XXX page
	 * @author : uchauhan
	 */

	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			LOGGER.trace("CreatePickupOrderAction :: preparePage() :: Start --------> ::::::");
			CreatePickupOrderForm createPickupOrderForm = (CreatePickupOrderForm) form;
			PickupOrderTO to = createPickupOrderForm.getPickupOrderTO();
			to.setRequestDateStr(DateUtil.todayDate()); // sets the current date
			// gets the User Information from session
			UserInfoTO userInfoTO = (UserInfoTO) request.getSession()
					.getAttribute("user");
			String userType = userInfoTO != null
					&& userInfoTO.getUserto() != null ? userInfoTO.getUserto()
					.getUserType() : null;
			if (userType.equalsIgnoreCase("E")) {
				OfficeTO officeTO = userInfoTO.getOfficeTo();
				String officeType = userInfoTO.getOfficeTo().getOfficeTypeTO()
						.getOffcTypeCode();

				pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
						.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);

				// If the logged in office type is branch get the corresponding
				// reporting hub and reporting RHO
				if (officeType
						.equalsIgnoreCase(PickupManagementConstants.BRANCH)) {
					to.setLoggedInOfficeId(officeTO.getOfficeId());
					to.setLoggedInhubOfficeId(officeTO.getReportingHUB());
					to.setLoggedRegionOfficeId(officeTO.getReportingRHO());
					Integer rhoId = officeTO.getReportingRHO();
					OfficeTO rho = getOfficeDetails(rhoId);
					Integer hubId = officeTO.getReportingHUB();
					OfficeTO hub = getOfficeDetails(hubId);
					to.setRegion(rho.getOfficeName());
					to.setHub(hub.getOfficeName());
					to.setBranch(officeTO.getOfficeName());
				}
				// If the logged in office type is hub get the corresponding
				// reporting RHO
				if (officeType.equalsIgnoreCase(PickupManagementConstants.HUB)) {

					to.setLoggedInhubOfficeId(officeTO.getOfficeId());
					to.setLoggedRegionOfficeId(officeTO.getReportingRHO());
					Integer rhoId = officeTO.getReportingRHO();
					OfficeTO rho = getOfficeDetails(rhoId);
					to.setRegion(rho.getOfficeName());
					to.setHub(officeTO.getOfficeName());

				}
				// If the logged in office type is region set the logged in
				// office Name as region
				else if (officeType
						.equalsIgnoreCase(PickupManagementConstants.REGION)) {
					to.setLoggedInOfficeId(officeTO.getOfficeId());
					to.setRegion(officeTO.getOfficeName());
				}
				to.setLoggedInUserId(userInfoTO.getUserto() != null ? userInfoTO
						.getUserto().getUserId() : null);
				to.setLoggedInOfficeId(officeTO.getOfficeId());

				to.setCustomerCode("");
				to.setDeliveryOfficeName("");
				((CreatePickupOrderForm) form).setPickupOrderTO(to); // set the
																		// TO to
																		// form
			} else {
				prepareActionMessage(
						request,
						PickupManagementConstants.LOGGEDIN_USER_IS_CUSTOMER_NOT_EMPLOYEE);
			}
		} catch (CGBusinessException e) {
			LOGGER.trace("CreatePickupOrderAction :: preparePage() :: ERROR :: --------> ::::::");
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: CreatePickupOrderAction::preparePage------------>:::::::"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: preparePage() :: ERROR :: --------> ::::::");
			getGenericException(request, e);
		}
		LOGGER.trace("CreatePickupOrderAction :: preparePage() :: End --------> ::::::");
		return mapping.findForward("Success");
	}

	/**
	 * @param OfficeId
	 * @return OfficeTO: Office details of the given OfficeId
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private OfficeTO getOfficeDetails(Integer OfficeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreatePickupOrderAction :: getOfficeDetails() :: Start --------> ::::::");
		OfficeTO officeTO = null;
		pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
		// gets office details for a given officeTd
		officeTO = pickupManagementCommonService.getOfficeDetails(OfficeId);

		LOGGER.trace("CreatePickupOrderAction :: getOfficeDetails() :: End --------> ::::::");
		return officeTO;

	}

	/**
	 * @Method : getDeliveryBranchesOfCustomer
	 * @param :
	 * @Desc : gets delivery branches of given customer
	 * @return : returns list of brances as ajax response
	 * @author : uchauhan
	 */

	public void getDeliveryBranchesOfCustomer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("CreatePickupOrderAction :: getDeliveryBranchesOfCustomer() :: Start --------> ::::::");
		String code = request.getParameter("customerCode");
		Map<String, String> officeList = null;
		java.io.PrintWriter out = null;
		pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
		// gets the Customer code-Id map from session
		Map<String, Integer> codeIdMap = (Map<String, Integer>) request
				.getSession().getAttribute("codeIdMap");
		// prepares customerTO with the customerCode
		CustomerTO customerTO = new CustomerTO();
		customerTO.setCustomerId(codeIdMap.get(code));
		String jsonResult = null;
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			// gets the list of Offices servicing the selected customer
			officeList = pickupManagementCommonService
					.getDeliveryBranchesOfCustomer(customerTO);
			// sets the officeList in request
			request.setAttribute("officeList", officeList);
			jsonResult = officeList.toString();
		} catch (CGBusinessException e) {
			LOGGER.error("CreatePickupOrderAction :: getDeliveryBranchesOfCustomer() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : CreatePickupOrderAction.getDeliveryBranchesOfCustomer",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: getDeliveryBranchesOfCustomer() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("CreatePickupOrderAction :: getDeliveryBranchesOfCustomer() :: End --------> ::::::");
	}

	/**
	 * @Method : getReverseLogisticsCustomerList
	 * @param :
	 * @Desc : gets list of Customer for given office
	 * @return : returns list of customers as ajax response
	 * @author : uchauhan
	 */

	@SuppressWarnings("static-access")
	public void getReverseLogisticsCustomerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("CreatePickupOrderAction :: getReverseLogisticsCustomerList() :: Start --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		// gets the logged in user information from the session
		UserInfoTO userInfoTO = (UserInfoTO) request.getSession().getAttribute(
				"user");
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		pickupManagementService = (CreatePickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CREATE_PICKUP_ORDER_SERVICE);
		Map<String, Integer> codeIdMap = new HashMap<String, Integer>();
		List<Map<String, Object>> custDtls=new ArrayList<>();
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			// gets the list of customer based on the logged In Office
			List<Object[]> customers = pickupManagementService
					.getCustomersInContractByBranch(officeTO);
			// form a map of customerId and Customer Code and set it in the
			// session for setting Customer Code field
			if (!StringUtil.isEmptyList(customers)) {
				for (Object[] custDtl : customers){
					Map<String, Object> customerInfo = new HashMap<String, Object>();
					String custCode = null;
					if(!StringUtil.isNull(custDtl[3])){
						//Set Shipped to code at customer code.
						custCode = custDtl[3].toString();				
					}else{
						custCode = custDtl[2].toString();
					}					
					String custId = custDtl[0].toString();
					codeIdMap.put(custCode, Integer.parseInt(custId));
					customerInfo.put("customerId", custDtl[0]);
					customerInfo.put("businessName", custDtl[1]);
					customerInfo.put("customerCode", custCode);
					
					custDtls.add(customerInfo);
				}
				request.getSession().setAttribute("codeIdMap", codeIdMap);
			} else {
				request.getSession().setAttribute("noCust", Boolean.TRUE);
			}
			// convert to JSON object and set customer List in the response
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(custDtls).toString();

		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : CreatePickupOrderAction.getReverseLogisticsCustomerList",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : CreatePickupOrderAction.getReverseLogisticsCustomerList",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: getReverseLogisticsCustomerList() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("CreatePickupOrderAction :: getReverseLogisticsCustomerList() :: End --------> ::::::");
	}
	
	/**
	 * @Method : getConsignmentType
	 * @param :
	 * @Desc : gets list of consignment Type
	 * @return : returns list of consignments as ajax response
	 * @author : uchauhan
	 */

	@SuppressWarnings("static-access")
	public void getConsignmentType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.trace("CreatePickupOrderAction :: getConsignmentType() :: Start --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
		try {
			response.setContentType("text/javascript");
			out = response.getWriter();
			// get the list of Consignment Type
			List<ConsignmentTypeTO> consignmentTypeTOs = pickupManagementCommonService
					.getConsignmentType();
			// convert the list to JSON Object and set it in response
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(consignmentTypeTOs).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getConsignmentType",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getConsignmentType", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: getConsignmentType() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("CreatePickupOrderAction :: getConsignmentType() :: End --------> ::::::");
	}

	/**
	 * @Method : savePickupOrder
	 * @param :
	 * @Desc : saves the Pickup Order details
	 * @return : returns order number on successful save as ajax response
	 * @author : uchauhan
	 */

	public void savePickupOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreatePickupOrderAction :: savePickupOrder() :: Start --------> ::::::");
		// get the logged in user information from session
		UserInfoTO userInfoTO = (UserInfoTO) request.getSession().getAttribute(
				"user");
		String officeCode = userInfoTO.getOfficeTo().getOfficeCode();
		Integer officeId = userInfoTO.getOfficeTo().getOfficeId();
		CreatePickupOrderForm createPickupOrderForm = (CreatePickupOrderForm) form;
		pickupManagementService = (CreatePickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CREATE_PICKUP_ORDER_SERVICE);
		String jsonResult = null;
		PrintWriter resp = null;
		PickupOrderTO pto = null;
		try {
			resp = response.getWriter();
			response.setContentType("text/javascript");
			Map<String, Integer> codeIdMap = (Map<String, Integer>) request
					.getSession().getAttribute("codeIdMap");
			PickupOrderTO pickto = createPickupOrderForm.getPickupOrderTO();
			String srNo = pickto.getSrNo();
			if (srNo != null) {
				srNo.split(",");
			}
			pickto.setOfficeCode(officeCode);
			pickto.setOriginatingOffice(officeId);
			// set the customer code-Id Map
			if (!codeIdMap.isEmpty() && codeIdMap != null) {
				pickto.setCodeIdMap(codeIdMap);
			}
			// saves the details to database
			pto = pickupManagementService.savePickupOrder(pickto);
			
			//calling TwoWayWrite service to save same in central
			twoWayWrite(pto);
			
			// set the persistent data in form for display
			createPickupOrderForm.setPickupOrderTO(pto);
			// get the map of generated order numbers for corresponding Sr No
			// and set in response
			Map<Integer, String> map = pto.getOrderNum();
			jsonResult = map.toString();

		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.savePickupOrder", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.savePickupOrder", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: savePickupOrder() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		}

		resp.write(jsonResult);
		resp.close();
		LOGGER.trace("CreatePickupOrderAction :: savePickupOrder() :: End --------> ::::::");

	}

	private void twoWayWrite(PickupOrderTO pto) {
		try{
			PickupGatewayService pickupGatewayService = (PickupGatewayService) getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			pickupGatewayService.twoWayWrite(pto.getPickupTwoWayWriteTO());
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction:: twoWayWrite", e);
		}
	}

	/**
	 * @Method : uploadPickupDetails
	 * @param :
	 * @Desc : uploads/saves the data of excel file
	 * @return : Forwarding XXX page
	 * @author : uchauhan
	 */

	@SuppressWarnings("unchecked")
	public ActionForward uploadPickupDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreatePickupOrderAction :: uploadPickupDetails() :: Start --------> ::::::");
		// gets the logged in user Information from session
		UserInfoTO userInfoTO = (UserInfoTO) request.getSession().getAttribute(
				"user");
		String officeCode = userInfoTO.getOfficeTo().getOfficeCode();
		Integer officeId = userInfoTO.getOfficeTo().getOfficeId();
		Map<String, Integer> codeIdMap = (Map<String, Integer>) request
				.getSession().getAttribute("codeIdMap");
		CreatePickupOrderForm createPickupOrderForm = (CreatePickupOrderForm) form;
		pickupManagementService = (CreatePickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CREATE_PICKUP_ORDER_SERVICE);
		// gets the file to be uploaded
		final FormFile myFile = createPickupOrderForm.getFileUpload();
		final String fileName = myFile.getFileName();
		final String filePath = getServlet().getServletContext().getRealPath(
				File.separator);
		final String fileUrl = filePath + fileName;
		PickupOrderTO pickupTO = null;
		try {
			pickupTO = createPickupOrderForm.getPickupOrderTO();
			pickupTO.setOfficeCode(officeCode);
			pickupTO.setOriginatingOffice(officeId);
			if (!codeIdMap.isEmpty() && codeIdMap != null) {
				pickupTO.setCodeIdMap(codeIdMap);
			}
			// saves the file details
			pickupTO = pickupManagementService.uploadPickupDetails(fileUrl,
					pickupTO, myFile);
			//calling TwoWayWrite service to save same in central
			twoWayWrite(pickupTO);
			
			// if the header tags of file are invalid rejects the file and data
			// is not saved
			if (pickupTO.getIsValidHeader() != null
					&& !pickupTO.getIsValidHeader()) {
				ActionMessages msgs = new ActionMessages();
				ActionMessage errMsg = new ActionMessage(
						PickupManagementConstants.INVALID_HEADER);
				msgs.add(CommonConstants.INFO_MESSAGE, errMsg);
				request.setAttribute(CommonConstants.INFO_MESSAGE, msgs);
			}
			// sets the saved data in the form for display in UI grid
			Set<PickupOrderDetailsTO> pickupDetails = pickupTO.getDetailsTO();
			if (pickupDetails != null && !pickupDetails.isEmpty()) {
				pickupTO.setCreateFlag(Boolean.TRUE);
				createPickupOrderForm.setPickupOrderTO(pickupTO);
			}

			List<List> errorLst = pickupTO.getErrList();
			// sets the error list in session for generation of new ERROR file
			if (errorLst != null && errorLst.size() > 1) {
				request.setAttribute("fileName", fileName);
				HttpSession session = request.getSession(false);
				session.setAttribute("errorList", errorLst);
				pickupTO.setIsError("Y");

			}

		} catch (CGSystemException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.uploadPickupDetails", e);
			getSystemException(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.uploadPickupDetails", e);
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.uploadPickupDetails", e);
			getGenericException(request, e);
		}
		LOGGER.trace("CreatePickupOrderAction :: uploadPickupDetails() :: End --------> ::::::");
		return mapping.findForward("Success");
	}

	public void getBulkUploadErrorList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("CreatePickupOrderAction :: getBulkUploadErrorList() :: Start --------> ::::::");
		List<List> errorLst = (List<List>) request.getSession().getAttribute(
				"errorList");
		XSSFWorkbook xssfWorkbook;
		try {
			// gets the fileName form the request
			String fileName = request.getParameter("fileName");
			// converts the file Name in required Format (FileName_ERROR)
			if (StringUtils.isNotBlank(fileName)) {
				int dot = fileName.lastIndexOf('.');
				String baseFileName = (dot == -1) ? fileName : fileName
						.substring(0, dot);
				String extension = (dot == -1) ? "" : fileName
						.substring(dot + 1);
				fileName = baseFileName + "_" + "ERROR." + extension;
			}
			// creates a new excel file with reported error list and set in
			// response
			xssfWorkbook = pickupManagementService
					.reportBulkUploadErrors(errorLst);
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\" ");
			xssfWorkbook.write(response.getOutputStream());
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("ERROR : CreatePickupOrderAction.getBulkUploadErrorList", e);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getBulkUploadErrorList", e);
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getBulkUploadErrorList", e);
			getGenericException(request, e);
		}
		LOGGER.trace("CreatePickupOrderAction :: getBulkUploadErrorList() :: End --------> ::::::");
	}

	/**
	 * @Method : getPickupOrderDetail
	 * @param :
	 * @Desc : gets pickup order details for a given order number
	 * @return : Forwarding XXX page
	 * @author : uchauhan
	 */

	public ActionForward getPickupOrderDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreatePickupOrderAction :: getPickupOrderDetail() :: Start --------> ::::::");
		CreatePickupOrderForm createPickupOrderForm = (CreatePickupOrderForm) form;
		pickupManagementService = (CreatePickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CREATE_PICKUP_ORDER_SERVICE);
		// gets the order number which user clicks on the confirm pickup request
		// screen from request
		String orderNo = request.getParameter("orderNumber");
		// prepares a PickupOrderDetailsTO with order number
		PickupOrderDetailsTO detailTO = new PickupOrderDetailsTO();
		detailTO.setOrderNumber(orderNo);
		try {
			// gets all the details of the given order number
			PickupOrderTO headerTO = pickupManagementService
					.getPickupOrderDetail(detailTO);
			headerTO.setFlag(Boolean.TRUE);
			createPickupOrderForm.setPickupOrderTO(headerTO);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getPickupOrderDetail", e);
			getSystemException(request, e);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getPickupOrderDetail", e);
			getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR : CreatePickupOrderAction.getPickupOrderDetail", e);
			getGenericException(request, e);
		}
		LOGGER.trace("CreatePickupOrderAction :: getPickupOrderDetail() :: End --------> ::::::");
		return mapping.findForward("Success");

	}

	@SuppressWarnings("static-access")
	public void validatePincodeAndGetCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("CreatePickupOrderAction :: validatePincodeAndGetCity() :: Start --------> ::::::");
		pickupManagementCommonService = (PickupManagementCommonService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_COMMON_SERVICE);
		// gets the pincode from the request
		String pincode = request.getParameter("pin");
		PincodeTO pinTO = new PincodeTO();
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			serializer = CGJasonConverter.getJsonObject();
			// prepares the PincodeTO
			pinTO.setPincode(pincode);
			// checks if the given pincode exsists in the database
			pinTO = pickupManagementCommonService
					.validatePincodeAndGetCity(pinTO);
			jsonResult = serializer.toJSON(pinTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("CreatePickupOrderAction :: validatePincodeAndGetCity() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
			LOGGER.error(
					"ERROR : CreatePickupOrderAction.validatePincodeAndGetCity",
					e);
		} catch (Exception e) {
			LOGGER.error("CreatePickupOrderAction :: validatePincodeAndGetCity() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CreatePickupOrderAction :: validatePincodeAndGetCity() :: End --------> ::::::");
	}
}
