package com.ff.universe.serviceOffering.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.serviceability.PincodeBranchServiceabilityCityNameTO;  
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

public class SearchByPincodeAction extends CGBaseAction {
	public transient JSONSerializer serializer;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SearchByPincodeAction.class);

	@SuppressWarnings("static-access")
	public void getPincodeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.debug("SearchByPincodeAction::getPincodeDetails::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter writer = response.getWriter();
		try {
			String pincode = request.getParameter("pncode");
			serviceOfferingCommonService = getServiceOfferingCommonService();
			List<PincodeBranchServiceabilityCityNameTO> serviceabilityTOs = serviceOfferingCommonService
					.getAllServicingOfficebyPincodeCity(pincode);  
			if (!CGCollectionUtils.isEmpty(serviceabilityTOs)) {
				jsonResult = serializer.toJSON(serviceabilityTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("SearchByPincodeAction :: getPincodeDetails() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("SearchByPincodeAction :: getPincodeDetails() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("SearchByPincodeAction :: getPincodeDetails() ::" , e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			writer.print(jsonResult);
			writer.flush();
			writer.close();
		}
		LOGGER.debug("SearchByPincodeAction::getPincodeDetails::END------------>:::::::");

	}

	public ActionForward searchByPincode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("SearchByPincodeAction::getPincodeDetails::START------------>:::::::");

		LOGGER.debug("SearchByPincodeAction::getPincodeDetails::END------------>:::::::");

		return mapping.findForward("success");
	}

	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::START------------>:::::::");
		LOGGER.debug(serviceOfferingCommonService+"===joooooooooo");
		if (StringUtil.isNull(serviceOfferingCommonService)) {
			serviceOfferingCommonService = (ServiceOfferingCommonService) getBean("serviceOfferingCommonService");
		}
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::END------------>:::::::");
		return serviceOfferingCommonService;
	}
	/*
	 * public void getLoginIdsAutocomplete(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * }
	 */
	
	
}
