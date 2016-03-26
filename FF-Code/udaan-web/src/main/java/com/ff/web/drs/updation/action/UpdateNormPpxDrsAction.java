/**
 * 
 */
package com.ff.web.drs.updation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.NormalPriorityDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.NormalPriorityDrsForm;
import com.ff.web.drs.updation.service.UpdateNormalPriorityDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class UpdateNormPpxDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateNormPpxDrsAction.class);
	
	
	private UpdateNormalPriorityDrsService updateNpDrsService;


	
	/**
	 * Gets the np drs update service.
	 *
	 * @return the np drs update service
	 */
	public UpdateNormalPriorityDrsService getNpDrsUpdateService() {

		if(StringUtil.isNull(updateNpDrsService)){
			updateNpDrsService = (UpdateNormalPriorityDrsService)getBean(SpringConstants.DRS_UPDATE_NP_SERVICE);
		}
		return updateNpDrsService;
	}
	public ActionForward viewUpdateDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateNormPpxDrsAction::viewUpdateDrsPage ..START");
		final NormalPriorityDrsTO drsTo = new NormalPriorityDrsTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		((NormalPriorityDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateNormPpxDrsAction::viewUpdateDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final NormalPriorityDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Preparation-P */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_UPDATE);
		drsTo.setDrsScreenCode(DrsConstants.NORMAL_PRIORITY_PPX_DRS_SCREEN_CODE);
		/** defining DRS's Consignment type i.e PPX */
		drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.NP_PPX_DRS_CONFIG_PARAMS_SERIES));
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
		LOGGER.debug("UpdateNormPpxDrsAction::findDrsNumberForUpdate ..START");
		final NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		initilaizeForm(drsTo,request);
		ActionMessage actionMessage = null;
		
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		updateNpDrsService = getNpDrsUpdateService();
		try {
			drsTo = updateNpDrsService.findDrsByDrsNumberForUpdate(drsTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("UpdateNormPpxDrsAction::findDrsNumberForUpdate ..CGBusinessException :",e);
			drsTo= new NormalPriorityDrsTO();
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("UpdateNormPpxDrsAction::findDrsNumberForUpdate ..CGSystemException :",e);
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
			drsTo= new NormalPriorityDrsTO();
		}catch (Exception e) {
			drsTo= new NormalPriorityDrsTO();
			LOGGER.error("UpdateNormPpxDrsAction::findDrsNumberForUpdate ..Exception :",e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
			getGenericException(request, e);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			setGridStandardTypeDetails(request);
			setRelationDtls(request);
		}
		((NormalPriorityDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateNormPpxDrsAction::findDrsNumberForUpdate ..END");
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
		LOGGER.debug("UpdateNormPpxDrsAction::savePrepareDrs ..START");
		Boolean result=Boolean.FALSE;

		final NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		final NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		updateNpDrsService =getNpDrsUpdateService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = updateNpDrsService.updateDeliveredDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("UpdateNormPpxDrsAction::savePrepareDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("UpdateNormPpxDrsAction::savePrepareDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("UpdateNormPpxDrsAction::savePrepareDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("UpdateNormPpxDrsAction::savePrepareDrs ..END");
	}
}
