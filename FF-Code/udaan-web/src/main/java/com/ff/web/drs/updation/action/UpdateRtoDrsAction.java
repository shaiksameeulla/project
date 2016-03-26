/**
 * 
 */
package com.ff.web.drs.updation.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.RtoCodDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.RtoCodDrsForm;
import com.ff.web.drs.updation.service.UpdateRtoDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class UpdateRtoDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateRtoDrsAction.class);
	
	
	private UpdateRtoDrsService updateRtoDrsService;


	
	
	public UpdateRtoDrsService getRtoDrsUpdateService() {

		if(StringUtil.isNull(updateRtoDrsService)){
			updateRtoDrsService = (UpdateRtoDrsService)getBean(SpringConstants.DRS_UPDATE_RTO_SERVICE);
		}
		return updateRtoDrsService;
	}
	public ActionForward viewUpdateDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateRtoDrsAction::viewUpdateDrsPage ..START");
		final RtoCodDrsTO drsTo = new RtoCodDrsTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		((RtoCodDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateRtoDrsAction::viewUpdateDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final RtoCodDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Update-U */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_UPDATE);
		drsTo.setDrsScreenCode(DrsConstants.RTO_COD_SCREEN_CODE);
		/** defining DRS's Consignment type i.e NA*/
		drsTo.setConsignmentType(DrsConstants.CONSG_TYPE_NA);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.RTO_COD_DRS_CONFIG_PARAMS_SERIES));
	}
	
	
	/**
	 * Find drs number for update.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDrsNumberForUpdate(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateRtoDrsAction::findDrsNumberForUpdate ..START");
		final RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		ActionMessage actionMessage=null;
		initilaizeForm(drsTo,request);
		
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		updateRtoDrsService = getRtoDrsUpdateService();
		try {
			drsTo = updateRtoDrsService.findDrsByDrsNumberForUpdate(drsTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("UpdateRtoDrsAction::findDrsNumberForUpdate ..CGBusinessException :",e);
			drsTo= new RtoCodDrsTO();
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("UpdateRtoDrsAction::findDrsNumberForUpdate ..CGSystemException :",e);
			drsTo= new RtoCodDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			LOGGER.error("UpdateRtoDrsAction::findDrsNumberForUpdate ..Exception :",e);
			getGenericException(request, e);
			drsTo= new RtoCodDrsTO();
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			setGridStandardTypeForRTODrsDetails(request);
		}
		((RtoCodDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateRtoDrsAction::findDrsNumberForUpdate ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	
	
	
	/**
	 * Update drs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void updateDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateRtoDrsAction::savePrepareDrs ..START");
		Boolean result=Boolean.FALSE;

		final RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		final RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		updateRtoDrsService =getRtoDrsUpdateService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = updateRtoDrsService.updateDeliveredDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("UpdateRtoDrsAction::savePrepareDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("UpdateRtoDrsAction::savePrepareDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("UpdateRtoDrsAction::savePrepareDrs ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("UpdateRtoDrsAction::savePrepareDrs ..END");
	}
	public void setGridStandardTypeForRTODrsDetails(HttpServletRequest request) {
		Map<String, String> deliveryStatusMap;
		deliveryCommonService = getCommonServiceForDelivery();
		HttpSession session=request.getSession(false);
		
		/** Preparing constants from standard types For Delivery Type */
		deliveryStatusMap = (Map<String,String> )session.getAttribute(DrsCommonConstants.DELIVERY_STATUS_REQ_PARAMS);
		try {
			if(CGCollectionUtils.isEmpty(deliveryStatusMap)) {
				deliveryStatusMap =deliveryCommonService.getStandardTypesAsMap(DrsConstants.DELIVERY_TYPE_RTO);
						
			session.setAttribute(DrsCommonConstants.DELIVERY_STATUS_REQ_PARAMS, deliveryStatusMap);
			}
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails:: Delivery Type Dropdown ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(deliveryStatusMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.DLV_STATUS_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: setStandardTypeDetails :: Delivery Status Dropdown Details Does not exist");
		}
		request.setAttribute(DrsCommonConstants.DELIVERY_STATUS_REQ_PARAMS, deliveryStatusMap);
		Map<Integer, String> pendingReasons=null;
		
		try {
			 pendingReasons=getPendingReasonsForDRS(request);
		} catch (Exception e) {
			LOGGER.error("AbstractDeliveryAction:: setStandardTypeDetails:: Pending reason Type Dropdown ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(deliveryStatusMap)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,DrsCommonConstants.PENDING_REASON_NOT_EXIST));
			LOGGER.warn("AbstractDeliveryAction:: Set Reason Types ::Pending reason Dropdown Details Does not exist");
		}else{
			request.setAttribute(DrsCommonConstants.DRS_NON_DLV_REASON_REQ_PARAMS, pendingReasons);
		}
	}
}
