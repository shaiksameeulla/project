package com.ff.admin.tracking.gatepassTracking.action;

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
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.gatepassTracking.service.GatepassTrackingService;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingGatepassTO;

public class GatepassTrackingAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(GatepassTrackingAction.class);

	private GatepassTrackingService gatepassTrackingService;

	public ActionForward viewGatepassTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug("GatepassTrackingAction::viewGatepassTracking::START----->");
		ActionMessage actionMessage = null;
		try {
			gatepassTrackingService = (GatepassTrackingService) getBean(AdminSpringConstants.GATEPASS_TRACKING_SERVICE);
			List<StockStandardTypeTO> typeNameTo = gatepassTrackingService
					.getTypeName();
			request.setAttribute(AdminSpringConstants.TYPENAME_TO, typeNameTo);
		} catch (CGBusinessException e) {
			LOGGER.error("GatepassTrackingAction::viewGatepassTracking ..CGBusinessException :",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("GatepassTrackingAction::viewGatepassTracking ..CGSystemException :",e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("GatepassTrackingAction::viewGatepassTracking ..Exception :",e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("GatepassTrackingAction::viewGatepassTracking::END----->");

		return mapping.findForward(CommonConstants.SUCCESS);

	}

	public void viewGatepassTrackInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("GatepassTrackingAction::viewGatepassTrackInformation()::START----->");
		PrintWriter out = null;
		String manifestTOJSON = null;
		TrackingGatepassTO trackingTO = null;
		try {
			out = response.getWriter();
			String type = request.getParameter("type");
			String number = request.getParameter("number");
			logger.append("\nGatepassTrackingAction::viewGatepassTrackInformation()::Track -----> "+type+" NO :"+ number);
			logger.append(" ::Start Time::" + startTimeInMilis);
			LOGGER.debug(logger.toString());
			gatepassTrackingService = (GatepassTrackingService) getBean(AdminSpringConstants.GATEPASS_TRACKING_SERVICE);
			trackingTO = gatepassTrackingService.viewTrackInformation(number,
					type);
			manifestTOJSON = JSONSerializer.toJSON(trackingTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("GatepassTrackingAction::viewGatepassTrackInformation ..CGBusinessException :",e);
			manifestTOJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("GatepassTrackingAction::viewGatepassTrackInformation ..CGSystemException :",e);
			String exception=getSystemExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}catch (Exception e) {
			LOGGER.error("GatepassTrackingAction :: viewGatepassTrackInformation() :: ERROR :: ",e);
			String exception=getGenericExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("GatepassTrackingAction::viewGatepassTrackInformation()::END----->:: End Time : " +
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}
	
}
