/**
 * 
 */
package com.ff.web.drs.preparation.action;

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
import com.ff.web.drs.preparation.service.PrepareNormPpxDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class PrepareNormPpxDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareNormPpxDrsAction.class);

	private PrepareNormPpxDrsService prepareNormPpxDrsService;


	
	/**
	 * Gets the prepare norm ppx drs service.
	 *
	 * @return the prepare norm ppx drs service
	 */
	public PrepareNormPpxDrsService getprepareNormPpxDrsService() {
		if(StringUtil.isNull(prepareNormPpxDrsService)){
			prepareNormPpxDrsService = (PrepareNormPpxDrsService)getBean(SpringConstants.DRS_NP_PPX_SERVICE);
		}
		return prepareNormPpxDrsService;
	}
	/**
	 * Name : viewPrepareDrsPage
	 * purpose : to view DRS Normal priority Dox preparation sheet form
	 * Input :
	 * return : populate form with default values.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewPrepareDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareNormPpxDrsAction::viewPrepareDrsPage ..START");
		final NormalPriorityDrsTO drsTo = new NormalPriorityDrsTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		setHeaderStandardTypeDetails(request);
		((NormalPriorityDrsForm) form).setTo(drsTo);
		LOGGER.debug("PrepareNormPpxDrsAction::viewPrepareDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}

	/**
	 * Find drs details by drs number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findDrsDetailsByDrsNumber(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareNormPpxDrsAction::findDrsDetailsByDrsNumber ..START");
		ActionMessage actionMessage=null;
		final NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		initilaizeForm(drsTo,request);
		prepareNormPpxDrsService =getprepareNormPpxDrsService();
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		try {
			drsTo = prepareNormPpxDrsService.findDrsByDrsNumber(drsTo);

			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareNormPpxDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",e);
			getBusinessError(request, e);
			drsTo = new NormalPriorityDrsTO();
		} catch (CGSystemException e) {
			LOGGER.error("PrepareNormPpxDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",e);
			drsTo = new NormalPriorityDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			drsTo = new NormalPriorityDrsTO();
			LOGGER.error("PrepareNormPpxDrsAction::findDrsDetailsByDrsNumber ..Exception :",e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
		}

		((NormalPriorityDrsForm) form).setTo(drsTo);

		LOGGER.debug("PrepareNormPpxDrsAction::findDrsDetailsByDrsNumber ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final NormalPriorityDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Preparation-P */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_PREPARATION);
		drsTo.setDrsScreenCode(DrsConstants.NORMAL_PRIORITY_PPX_DRS_SCREEN_CODE);
		/** defining DRS's Consignment type i.e PPX */
		drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.NP_PPX_DRS_CONFIG_PARAMS_SERIES));
		drsTo.setIsNormalCnoteAllowed(DrsConstants.FLAG_YES);
		drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.N_P_PPX_DRS_GRID_MAX_CN));
	}
	


	/**
	 * savePrepareDrs :: It prepares the new DRS sheet by generating Unique DRS number
	 * AJAX call
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void savePrepareDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareNormPpxDrsAction::savePrepareDrs ..START");
		Boolean result=Boolean.FALSE;

		final NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		final NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		prepareNormPpxDrsService =getprepareNormPpxDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareNormPpxDrsService.savePrepareDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_GENERATED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareNormPpxDrsAction::savePrepareDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareNormPpxDrsAction::savePrepareDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PrepareNormPpxDrsAction::savePrepareDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareNormPpxDrsAction::savePrepareDrs ..END");
	}
	
	
	/**
	 * Discard drs. Discarding the DRS Sheet generated earlier
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward discardDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareNormPpxDrsAction::discardDrs ..START");
		final NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		 NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		prepareNormPpxDrsService =getprepareNormPpxDrsService();
		ActionMessage actionMessage = null;
		try {
			Boolean result =prepareNormPpxDrsService.discardDrs(drsTo);
			
			if(result) {
				performTwoWayWrite(drsTo);
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NO_DICARDED,drsTo.getDrsNumber());
			}else{
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED,drsTo.getDrsNumber());
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareNormPpxDrsAction::discardDrs ..CGBusinessException :",e);
			getBusinessError(request, e);
		}catch (CGSystemException e) {
			LOGGER.error("PrepareNormPpxDrsAction::discardDrs ..CGSystemException :",e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("PrepareNormPpxDrsAction::discardDrs ..Exception :",e);
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED_DB_ISSUE,drsTo.getDrsNumber());
			getGenericException(request, e);
		}finally{
			drsTo=new NormalPriorityDrsTO();
			initilaizeForm(drsTo,request);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			prepareActionMessage(request, actionMessage);
			((NormalPriorityDrsForm) form).setTo(drsTo);
		}
		
		LOGGER.debug("PrepareNormPpxDrsAction::discardDrs ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	
	/**
	 * Modify drs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void modifyDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareNormPpxDrsAction::modifyDrs ..START");
		Boolean result=Boolean.FALSE;

		NormalPriorityDrsForm drsForm = (NormalPriorityDrsForm)form;
		final NormalPriorityDrsTO drsTo =(NormalPriorityDrsTO) drsForm.getTo();
		prepareNormPpxDrsService =getprepareNormPpxDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareNormPpxDrsService.modifyDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_NOT_MODIFIED,new String[]{drsTo.getDrsNumber()});
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareNormPpxDrsAction::modifyDrs ..CGBusinessException :",e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareNormPpxDrsAction::modifyDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PrepareNormPpxDrsAction::modifyDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareNormPpxDrsAction::savePrepareDrs ..END");
	}
}
