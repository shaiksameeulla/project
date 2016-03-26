package com.ff.web.pickup.action;

/**
 * 
 */

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.umc.UserInfoTO;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.form.ConfirmPickupOrderForm;
import com.ff.web.pickup.service.ConfirmPickupOrderService;
import com.ff.web.pickup.service.PickupGatewayService;

/**
 * @author uchauhan
 * 
 */
public class ConfirmPickupOrderAction extends CGBaseAction {

	private ConfirmPickupOrderService confirmPickupOrderService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConfirmPickupOrderAction.class);

	/**
	 * Populates the page with initial values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ConfirmPickupOrderAction :: preparePage() :: Start --------> ::::::");
		ConfirmPickupOrderForm confirmPickupOrderForm = (ConfirmPickupOrderForm) form;
		PickupOrderTO to = confirmPickupOrderForm.getTo();
		UserInfoTO userInfoTO = (UserInfoTO) request.getSession().getAttribute(
				"user");
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		confirmPickupOrderService = (ConfirmPickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CONFIRM_PICKUP_ORDER_SERVICE);
		try {
			List<PickupOrderDetailsTO> detailsTOs = confirmPickupOrderService
					.getPickupRequestList(officeTO);
			if (detailsTOs != null && !detailsTOs.isEmpty()) {
				// sorts the detailsTOs on basis of request created date in desc
				// order
				Collections.sort(detailsTOs);
				Set<PickupOrderDetailsTO> dtls = new LinkedHashSet<PickupOrderDetailsTO>(
						detailsTOs);
				// sets the details in form
				to.setDetailsTO(dtls);
				confirmPickupOrderForm.setTo(to);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : ConfirmPickupOrderAction.preparePage", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : ConfirmPickupOrderAction.preparePage", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ConfirmPickupOrderAction :: preparePage() :: ERROR :: --------> ::::::");
			getGenericException(request, e);
		}
		LOGGER.trace("ConfirmPickupOrderAction :: preparePage() :: End --------> ::::::");
		return mapping.findForward(PickupManagementConstants.SUCCESS);
	}

	/**
	 * @Desc updates the status of the selected request as Updated
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward acceptPickupDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ConfirmPickupOrderAction :: acceptPickupDetails() :: Start --------> ::::::");
		//boolean isUpdated = Boolean.FALSE;
		ConfirmPickupOrderForm confirmPickupOrderForm = (ConfirmPickupOrderForm) form;
		confirmPickupOrderService = (ConfirmPickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CONFIRM_PICKUP_ORDER_SERVICE);
		// gets the status from request
		String status = request.getParameter("Status");
		PickupOrderTO pickto = confirmPickupOrderForm.getTo();
		UserInfoTO userInfoTO = (UserInfoTO) request.getSession().getAttribute("user");
		pickto.setLoggedInUserId(userInfoTO.getUserto() != null ? userInfoTO
				.getUserto().getUserId() : null);
		try {
			pickto = confirmPickupOrderService.updatePickupOrderDetails(pickto,
					status);
			if (pickto.isUpdated()) {
				//Two-way write
				twoWayWrite(pickto);
				prepareActionMessage(request, new ActionMessage(
						PickupManagementConstants.REQUEST_ACCEPTED_SUCCESSFULLY));
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.acceptPickupDetails", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.acceptPickupDetails", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.acceptPickupDetails", e);
			getGenericException(request, e);
		}
		LOGGER.trace("ConfirmPickupOrderAction :: acceptPickupDetails() :: End --------> ::::::");
		return mapping.findForward(PickupManagementConstants.ACCEPT);
	}

	/**
	 * @Desc updates the status as declined for selected requests
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward declinePickupDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ConfirmPickupOrderAction :: declinePickupDetails() :: Start --------> ::::::");
		//boolean isUpdated = Boolean.FALSE;
		ConfirmPickupOrderForm confirmPickupOrderForm = (ConfirmPickupOrderForm) form;
		confirmPickupOrderService = (ConfirmPickupOrderService) springApplicationContext
				.getBean(PickupManagementConstants.CONFIRM_PICKUP_ORDER_SERVICE);
		// gets the status from the request
		String status = request.getParameter("Status");
		PickupOrderTO pickto = confirmPickupOrderForm.getTo();

		try {
			pickto = confirmPickupOrderService.updatePickupOrderDetails(pickto,
					status);
			if (pickto.isUpdated()) {
				//Two-way write
				twoWayWrite(pickto);
				prepareActionMessage(request, new ActionMessage(
						PickupManagementConstants.REQUEST_DECLINED_SUCCESSFULLY));
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.declinePickupDetails", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.declinePickupDetails", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : ConfirmPickupOrderAction.declinePickupDetails", e);
			getGenericException(request, e);
		}

		LOGGER.trace("ConfirmPickupOrderAction :: declinePickupDetails() :: End --------> ::::::");
		return mapping.findForward(PickupManagementConstants.DECLINE);
	}
	
	private void twoWayWrite(PickupOrderTO pto) {
		try{
			PickupGatewayService pickupGatewayService = (PickupGatewayService) getBean(PickupManagementConstants.PICKUP_GATEWAY_SERVICE);
			pickupGatewayService.twoWayWrite(pto.getPickupTwoWayWriteTO());
		} catch (Exception e) {
			LOGGER.error("ConfirmPickupOrderAction:: twoWayWrite", e);
		}
	}
}
