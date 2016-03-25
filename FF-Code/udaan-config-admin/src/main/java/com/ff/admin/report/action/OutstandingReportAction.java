package com.ff.admin.report.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.report.form.OutstandingReportForm;
import com.ff.admin.report.service.OutstandingReportService;
import com.ff.admin.report.to.OutstandingReportTO;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.domain.mec.SAPReportDO;
import com.ff.domain.pickup.PickupDeliveryContractWrapperDO;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.ratemanagement.service.RateUniversalService;

/**
 * @author khassan
 *
 */
public class OutstandingReportAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutstandingReportAction.class);
	private OutstandingReportService outstandingReportService;
	private RateUniversalService rateUniversalService;
	private Boolean isSaved = false;
	
	
	/**
	 *  This method is for landing to  outstanding report page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward displayOutstandingReportPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("in OutstandingReportAction class >> displayOutstandingReportPage");
		Integer officeID = null;
		List<PickupDeliveryContractWrapperDO> pickupDeliveryContratctWrapperList = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		isSaved = false;
		List<SAPReportDO> reportList = null;
		
		rateUniversalService =(RateUniversalService) getBean(RateQuotationConstants.RATE_UNIVERSAL_SERVICE);
		outstandingReportService = (OutstandingReportService) getBean("outstandingReportService");
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		
		if(userInfoTO != null && userInfoTO.getEmpUserTo() != null && userInfoTO.getEmpUserTo().getEmpTO() != null){
			officeID = userInfoTO.getEmpUserTo().getEmpTO().getOfficeId();
		}
		
		if(officeID != null){
			try {
				pickupDeliveryContratctWrapperList = rateUniversalService.getPickUpDeliveryContratWrapperDOByOfficeId(officeID);
				if(pickupDeliveryContratctWrapperList != null){
					LOGGER.trace(pickupDeliveryContratctWrapperList.toString());
				}
			} catch (CGSystemException e) {
				LOGGER.error("Unable to fetch customer data for auto fill dropdown",e);
			}catch(Exception e){
				LOGGER.error("Unable to fetch customer data for auto fill dropdown",e);
			}
		}
		try {
			reportList = outstandingReportService.getReportList();
		} catch (CGSystemException e) {
			LOGGER.error("Unable to fecth report list",e);
		}
		request.setAttribute(MECCommonConstants.CUSTOMER_LIST, pickupDeliveryContratctWrapperList);
		request.setAttribute("isSaved", isSaved);
		request.setAttribute("reportList", reportList);
		
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * This method is to save report data to and update client about the same.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("in OutstandingReportAction class >> saveData");
		rateUniversalService =(RateUniversalService) getBean(RateQuotationConstants.RATE_UNIVERSAL_SERVICE);
		outstandingReportService = (OutstandingReportService) getBean("outstandingReportService");
		List<PickupDeliveryContractWrapperDO> pickupDeliveryContratctWrapperList = null;
		Integer officeID = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		List<SAPReportDO> reportList = null;
		OutstandingReportTO  outstandingReportTO= (OutstandingReportTO)((OutstandingReportForm)form).getTo();
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		
		if(userInfoTO != null && userInfoTO.getEmpUserTo() != null && userInfoTO.getEmpUserTo().getEmpTO() != null){
			officeID = userInfoTO.getEmpUserTo().getEmpTO().getOfficeId();
		}
		
		if(officeID != null){
			try {
				pickupDeliveryContratctWrapperList = rateUniversalService.getPickUpDeliveryContratWrapperDOByOfficeId(officeID);
				
			} catch (CGSystemException e) {
				LOGGER.error("unable to load customer name and customer code", e);
			}
		}
		
		
		try {
			reportList = outstandingReportService.getReportList();
		} catch (CGSystemException  e) {
			LOGGER.error("unable to fetch report list", e);
		}
		
		try {
			isSaved = outstandingReportService.saveReportData(outstandingReportTO, userInfoTO);
			if(isSaved == null){
				isSaved = false;
			}
			
		} catch (CGSystemException e) {
			LOGGER.error("Unable to save report data ",e);
		} catch (CGBusinessException e) {
			LOGGER.error("Unable to save report data " + e.getMessage());
		}
		
		request.setAttribute(MECCommonConstants.CUSTOMER_LIST, pickupDeliveryContratctWrapperList);
		request.setAttribute("reportList", reportList);
		request.setAttribute("isSaved", isSaved);
		
		
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

}
