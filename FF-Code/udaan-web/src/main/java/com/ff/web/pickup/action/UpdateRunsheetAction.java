package com.ff.web.pickup.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
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
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.BookingValidationTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.form.PickupRunsheetForm;
import com.ff.web.pickup.service.PickupGatewayService;
import com.ff.web.pickup.service.UpdateRunsheetService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class UpdateRunsheetAction.
 */
public class UpdateRunsheetAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateRunsheetAction.class);

	/** The update runsheet service. */
	private UpdateRunsheetService updateRunsheetService;

	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * Gets the pickup runsheet details.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the pickup runsheet details
	 * @Method : getPickupRunsheetDetails
	 * @Desc : Get the Run sheet Details of the Pickup run sheet No. to display
	 *       on update run sheet grid
	 */
	public ActionForward getPickupRunsheetDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("UpdateRunsheetAction :: getPickupRunsheetDetails() :: Start --------> ::::::");
		List<PickupRunsheetTO> pkupRunsheetDetails = null;
		try {
			PickupRunsheetForm updatePkupRunsheetForm = (PickupRunsheetForm) form;
			PickupRunsheetTO updatePkupRunsheetTO = (PickupRunsheetTO) updatePkupRunsheetForm.getTo();
			// Set the login office details
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			OfficeTO officeTO = userInfoTO.getOfficeTo();
			updatePkupRunsheetTO.setLoginOfficeTO(officeTO);
			updateRunsheetService = (UpdateRunsheetService) springApplicationContext.getBean(PickupManagementConstants.UPDATE_RUNSHEET_SERVICE);

			String runsheetNo = request.getParameter(PickupManagementConstants.RUNSHEET_NO);
			if (StringUtils.isNotEmpty(runsheetNo)) {
				pkupRunsheetDetails = updateRunsheetService.getPickupRunsheetDetails(runsheetNo);
				if (!StringUtil.isEmptyList(pkupRunsheetDetails)) {
					Collections.sort(pkupRunsheetDetails);
					updatePkupRunsheetTO = pkupRunsheetDetails.get(0);
					updatePkupRunsheetTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
					updatePkupRunsheetTO.setSystemTime(DateUtil.getCurrentTime());
					
//					List<String> duplicateCustRow = new ArrayList<String>(pkupRunsheetDetails.size());
					for (PickupRunsheetTO pickupRunsheetTO : pkupRunsheetDetails) {						
						/*duplicateCustRow.add(pickupRunsheetTO.getNewRowField());
						if(duplicateCustRow.size() == pkupRunsheetDetails.size()){
							if(Collections.frequency(duplicateCustRow, "Y") > 0){
								updatePkupRunsheetTO.setNewRowHdr("Y");
							}
						}*/
						if (StringUtils.equalsIgnoreCase(pickupRunsheetTO.getRunsheetHeaderStatus(),
										PickupManagementConstants.RUNSHEET_STATUS_CLOSE)) {
							updatePkupRunsheetTO.setRunsheetHeaderStatus(PickupManagementConstants.RUNSHEET_STATUS_CLOSE);
							break;
						}
					}
				} else {
					prepareActionMessage(request, PickupManagementConstants.PICKUP_RUNSHEET_DETAILS_NOT_EXIST);
				}
			}
			request.setAttribute("OFF_TYPE_CODE_HUB", CommonConstants.OFF_TYPE_HUB_OFFICE);
			request.setAttribute(PickupManagementConstants.UPDATE_PICKUP_RUNSHEET_TO, updatePkupRunsheetTO);
			request.setAttribute(PickupManagementConstants.PICKUP_RUNSHEET_DETAILS, pkupRunsheetDetails);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : UpdateRunsheetAction :: getPickupRunsheetDetails() :: ", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : UpdateRunsheetAction :: getPickupRunsheetDetails() :: ", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR : UpdateRunsheetAction :: getPickupRunsheetDetails() :: ", e);
			getGenericException(request, e);
		}

		LOGGER.trace("UpdateRunsheetAction :: getPickupRunsheetDetails() :: End --------> ::::::");
		return mapping.findForward(PickupManagementConstants.UPDATE_PICKUP_RUNSHEET);
	}

	/**
	 * Update pickup runsheet details.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 * @Method : updatePickupRunsheetDetails
	 * @Desc : Update the details
	 */
	public void updatePickupRunsheetDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("UpdateRunsheetAction :: updatePickupRunsheetDetails() :: Start --------> ::::::");
		String updateStatus = "";
		java.io.PrintWriter out = null;
		String result = null;
		try {
			out = response.getWriter();
			PickupRunsheetForm updatePkupRunsheetForm = (PickupRunsheetForm) form;
			PickupRunsheetTO updatePkupRunsheetTO = (PickupRunsheetTO) updatePkupRunsheetForm.getTo();

			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			//Audit columns
			updatePkupRunsheetTO.setLoggedInUserId(userInfoTO.getUserto().getUserId());

			updateRunsheetService = (UpdateRunsheetService) springApplicationContext
					.getBean(PickupManagementConstants.UPDATE_RUNSHEET_SERVICE);
			updatePkupRunsheetTO = updateRunsheetService
					.updatePickupRunsheet(updatePkupRunsheetTO);
			updateStatus = updatePkupRunsheetTO.getTransactionMsg();
			request.setAttribute(PickupManagementConstants.STATUS, updateStatus);
			if (!StringUtil.isStringEmpty(updateStatus) && updateStatus.equalsIgnoreCase(PickupManagementConstants.UPDATED)) {
				//calling TwoWayWrite service to save same in central
				twoWayWrite(updatePkupRunsheetTO);
				
				result = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, getMessageFromErrorBundle(request,
								UdaanWebErrorConstants.PICK_UP_RUN_SHEET_UPDATED, null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("UpdateRunsheetAction::updatePickupRunsheetDetails::Exception=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("UpdateRunsheetAction::updatePickupRunsheetDetails::Exception=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("UpdateRunsheetAction::updatePickupRunsheetDetails::Exception=======>" + e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.trace("UpdateRunsheetAction :: updatePickupRunsheetDetails() :: End --------> ::::::");
	}

	public void twoWayWrite(PickupRunsheetTO pkupRunsheetTO)
			throws CGBusinessException {
		try{
			PickupGatewayService pickupGatewayService = (PickupGatewayService) getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			pickupGatewayService.twoWayWrite(pkupRunsheetTO.getPickupTwoWayWriteTO());
		} catch (Exception e) {
			LOGGER.error("UpdateRunsheetAction:: twoWayWrite", e);
		}
	}
	/**
	 * Validate consignments.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("static-access")
	public void validateConsignments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException, IOException {
		LOGGER.trace("UpdateRunsheetAction :: validateConsignments() :: Start --------> ::::::");
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		BookingValidationTO cnValidateTO = null;
		String pickupRunsheetNo = "";
		try {
			out = response.getWriter();
			cnValidateTO = prepareCnValidationTO(request);
			/*
			 *******Start******** code to be approve
			 */
			
			//cnValidateTO.setIsBusinessExceptionReq("N");
			
			/*
			 *******End******** code to be approve
			 */
			String consgNumber = null;
			Integer quantity = 0;

			if (StringUtils.isNotEmpty(request.getParameter("startCnNumber"))) {
				consgNumber = request.getParameter("startCnNumber");
				consgNumber=consgNumber.toUpperCase();
			}
			if (StringUtils.isNotEmpty(request.getParameter("quantity"))) {
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			pickupRunsheetNo = request.getParameter("pkupRunsheetNo");

			updateRunsheetService = (UpdateRunsheetService) springApplicationContext
					.getBean(PickupManagementConstants.UPDATE_RUNSHEET_SERVICE);
			cnValidateTO = updateRunsheetService
					.validateConsignment(cnValidateTO,pickupRunsheetNo,consgNumber,quantity);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignment() ::"
					+ e);
			String errorMsg = getBusinessErrorFromWrapper(request, e);
			cnValidateTO.setErrorMsg(errorMsg);

		} catch (CGSystemException e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignment() ::"
					+ e);
			String errorMsg = getSystemExceptionMessage(request, e);
			cnValidateTO.setErrorMsg(errorMsg);
		} catch (Exception e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignment() ::"
					+ e);
			String errorMsg = getGenericExceptionMessage(request, e);
			cnValidateTO.setErrorMsg(errorMsg);
		} finally {
			if (cnValidateTO != null) {
				cnValidationJSON = serializer.toJSON(cnValidateTO).toString();
			}
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("UpdateRunsheetAction :: validateConsignments() :: End --------> ::::::");
	}
	public void validateConsignmentProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.trace("UpdateRunsheetAction :: validateConsignmentProduct() :: Start --------> ::::::");
		String result = null;
		PrintWriter out=null;
		boolean isValidProduct=Boolean.FALSE;
		try{
			out=response.getWriter();
			ProductToBeValidatedInputTO productToBeValidatedInputTO = setProductContractInputTO(request);
			
			RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
			RateCalculationService rateService = serviceFactory
			                                  .getService(productToBeValidatedInputTO.getRateType());
			isValidProduct = rateService.isProductValidForContract(productToBeValidatedInputTO);
			if(!isValidProduct){
				result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								UdaanWebErrorConstants.CUSTOMER_NOT_CONTRACT_PRODUCT,
								null));
			}			
		}catch (CGBusinessException e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignmentProduct() ::"
					+ e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignmentProduct() ::"
					+ e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Error occured in UpdateRunsheetAction :: validateConsignmentProduct() ::"
					+ e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.trace("UpdateRunsheetAction :: validateConsignmentProduct() :: End --------> ::::::");
	}

	private ProductToBeValidatedInputTO setProductContractInputTO(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		String consgNumber = null;
		String productCode=null;
		String consgSeries=null;
		String customerCode = null;
		Integer loginCityId=0;
		String loginCityCode=null;
		//Get all request parameters
		PickupGatewayService pickupGatewayService = (PickupGatewayService) springApplicationContext
				.getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
		if (StringUtils.isNotEmpty(request.getParameter("startCnNumber"))) {
			consgNumber = request.getParameter("startCnNumber");
			consgSeries=consgNumber.substring(4,5);
			if(StringUtils.isNumeric(consgSeries)){
				consgSeries=CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
			}
			//For franchisee customers only Normal series consignments are allowed
			if (StringUtils.isNotEmpty(request.getParameter("customerType"))) {
				String customerType = request.getParameter("customerType");
				if(StringUtils.equalsIgnoreCase(CommonConstants.CUSTOMER_CODE_FRANCHISEE, customerType) 
						&& !StringUtils.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_NORMALCREDIT, consgSeries)){
					throw new CGBusinessException(
							BookingErrorCodesConstants.PRODUCT_IS_NOT_SERVICED_BY_BOOKING);
				}
			}
			
			ProductTO productTO=pickupGatewayService.getProductByConsgSeries(consgSeries);
			productCode=productTO.getProductCode();			
		}
		if (StringUtils.isNotEmpty(request.getParameter("customerCode"))) {
			customerCode = request.getParameter("customerCode");
		}
		if (StringUtils.isNotEmpty(request.getParameter("loginCityId"))) {
			loginCityId = Integer.parseInt(request.getParameter("loginCityId"));				
			CityTO cityTO=pickupGatewayService.getCity(loginCityId);
			if(!StringUtil.isNull(cityTO))
				loginCityCode=cityTO.getCityCode();
		}
		//Prepare input TO
		ProductToBeValidatedInputTO productToBeValidatedInputTO=new ProductToBeValidatedInputTO();
		productToBeValidatedInputTO.setProductCode(productCode);
		productToBeValidatedInputTO.setCustomerCode(customerCode);
		productToBeValidatedInputTO.setOriginCityCode(loginCityCode);
		productToBeValidatedInputTO.setRateType(CommonConstants.CUSTOMER_CODE_CREDIT_CARD);	
		productToBeValidatedInputTO.setCalculationRequestDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat());
		return productToBeValidatedInputTO;
	}

	/**
	 * Prepare cn validation to.
	 * 
	 * @param request
	 *            the request
	 * @return the booking validation to
	 */
	private BookingValidationTO prepareCnValidationTO(HttpServletRequest request) {
		BookingValidationTO cnValidateTO = new BookingValidationTO();
		cnValidateTO.setBookingType(BookingConstants.CCC_BOOKING);
		cnValidateTO.setProcessCode(CommonConstants.PROCESS_PICKUP);
		// Customer
		if (StringUtils.isNotEmpty(request.getParameter("customerId"))) {
			cnValidateTO.setIssuedTOPartyId(Integer.parseInt(request
					.getParameter("customerId")));
		}
		// Login Branch		
		if (StringUtils.isNotEmpty(request.getParameter("officeId"))) {
			cnValidateTO.setIssuedTOPartyId1(Integer.parseInt(request
					.getParameter("officeId")));
		}
		 
		// employee
		if (StringUtils.isNotEmpty(request.getParameter("employeeId"))) {
			cnValidateTO.setIssuedTOPartyId2(Integer.parseInt(request
					.getParameter("employeeId")));
		}
		return cnValidateTO;
	}
}
