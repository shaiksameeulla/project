package com.ff.admin.tracking.multipleTracking.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.consignmentTracking.service.ConsignmentTrackingService;
import com.ff.admin.tracking.multipleTracking.form.MultipleTrackingForm;
import com.ff.admin.tracking.multipleTracking.service.MultipleTrackingService;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingBulkImportTO;

public class MultipleTrackingAction extends CGBaseAction {

	public transient JSONSerializer serializer;
	
	private ConsignmentTrackingService consignmentTrackingService;
	private MultipleTrackingService multipleTrackingService;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MultipleTrackingAction.class);

	public ActionForward viewMultipleTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("MultipleTrackingAction::viewMultipleTracking::START----->");
		ActionMessage actionMessage = null;
		try {
			consignmentTrackingService = (ConsignmentTrackingService) getBean(AdminSpringConstants.CONSIGNMENT_TRACKING_SERVICE);
			List<StockStandardTypeTO> typeNameTo = consignmentTrackingService
					.getTypeName();
			request.setAttribute(AdminSpringConstants.TYPENAME_TO, typeNameTo);
		} catch (CGBusinessException e) {
			LOGGER.error("MultipleTrackingAction::viewMultipleTracking ..CGBusinessException :",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("MultipleTrackingAction::viewMultipleTracking ..CGSystemException :",e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("MultipleTrackingAction::viewMultipleTracking ..Exception :",e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("MultipleTrackingAction::viewMultipleTracking::END----->");
		return mapping.findForward(CommonConstants.SUCCESS);
	}

	public ActionForward getMultipleConsgDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("MultipleTrackingAction::getMultipleConsgDetails()::START--------->/n Start Time::" + startTimeInMilis);
		PrintWriter out = null;
		String jsonResult = "";
		MultipleTrackingForm multipleTrackingForm = (MultipleTrackingForm) form;
		TrackingBulkImportTO bulkTO = (TrackingBulkImportTO) multipleTrackingForm.getTo();
		try {
			out = response.getWriter();
			multipleTrackingService = (MultipleTrackingService) getBean(AdminSpringConstants.MULTIPLE_TRACKING_SERVICE);
			List<TrackingBulkImportTO> bulkTOs = multipleTrackingService.getMultipleConsgDetails(bulkTO);
			if (!StringUtil.isEmptyList(bulkTOs)) {
				jsonResult = serializer.toJSON(bulkTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MultipleTrackingAction::getMultipleConsgDetails ..CGBusinessException :",e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));	
		} catch (CGSystemException e) {
			LOGGER.error("MultipleTrackingAction::getMultipleConsgDetails ..CGSystemException :",e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MultipleTrackingAction::getMultipleConsgDetails ..Exception :",e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request, e));
		}finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("MultipleTrackingAction::getMultipleConsgDetails()::END----->:: End Time : " +
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		
		return mapping.findForward(CommonConstants.SUCCESS);
	}
}
