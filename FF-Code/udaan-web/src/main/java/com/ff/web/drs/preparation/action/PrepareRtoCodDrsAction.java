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

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.RtoCodDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.RtoCodDrsForm;
import com.ff.web.drs.preparation.service.PrepareRtoCodDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class PrepareRtoCodDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareRtoCodDrsAction.class);

	private transient PrepareRtoCodDrsService prepareRtoCodDrsService;


	
	
	/**
	 * Gets the prepare rto cod drs service.
	 *
	 * @return the prepare rto cod drs service
	 */
	public PrepareRtoCodDrsService getPrepareRtoCodDrsService() {
		if(StringUtil.isNull(prepareRtoCodDrsService)){
			prepareRtoCodDrsService = (PrepareRtoCodDrsService)getBean(SpringConstants.DRS_PREP_RTO_COD_SERVICE);
		}
		return prepareRtoCodDrsService;
	}
	/**
	 * Name : viewPrepareDrsPage
	 * purpose : to view DRS RTO COD  preparation sheet form
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
		LOGGER.debug("PrepareRtoCodDrsAction::viewPrepareDrsPage ..START");
		final RtoCodDrsTO drsTo = new RtoCodDrsTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		setHeaderStandardTypeDetails(request);
		((RtoCodDrsForm) form).setTo(drsTo);
		LOGGER.debug("PrepareRtoCodDrsAction::viewPrepareDrsPage ..END");
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
		LOGGER.debug("PrepareRtoCodDrsAction::findDrsDetailsByDrsNumber ..START");
		ActionMessage actionMessage=null;
		final RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		initilaizeForm(drsTo,request);
		prepareRtoCodDrsService =getPrepareRtoCodDrsService();
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		try {
			drsTo = prepareRtoCodDrsService.findDrsByDrsNumber(drsTo);

			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareRtoCodDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",e);
			getBusinessError(request, e);
		}catch (CGSystemException e) {
			LOGGER.error("PrepareRtoCodDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",e);
			drsTo = new RtoCodDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			drsTo = new RtoCodDrsTO();
			LOGGER.error("PrepareRtoCodDrsAction::findDrsDetailsByDrsNumber ..Exception :",e);
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
		}

		((RtoCodDrsForm) form).setTo(drsTo);

		LOGGER.debug("PrepareRtoCodDrsAction::findDrsDetailsByDrsNumber ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final RtoCodDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Preparation-P */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_PREPARATION);
		drsTo.setDrsScreenCode(DrsConstants.RTO_COD_SCREEN_CODE);
		/** defining DRS's Consignment type ,As NA-Not applicable */
		drsTo.setConsignmentType(DrsConstants.CONSG_TYPE_NA);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.RTO_COD_DRS_CONFIG_PARAMS_SERIES));
		drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.RTO_COD_DRS_GRID_MAX_CN));
		drsTo.setIsNormalCnoteAllowed(DrsConstants.FLAG_YES);
		drsTo.setIsPricingInfoRequired(true);
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
		LOGGER.debug("PrepareRtoCodDrsAction::savePrepareDrs ..START");
		Boolean result=Boolean.FALSE;

		final RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		final RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		prepareRtoCodDrsService =getPrepareRtoCodDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareRtoCodDrsService.savePrepareDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_GENERATED}));
				jsonResult = serializer.toJSON(drsTo).toString();
				performTwoWayWrite(drsTo);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareRtoCodDrsAction::savePrepareDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareRtoCodDrsAction::savePrepareDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PrepareRtoCodDrsAction::savePrepareDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareRtoCodDrsAction::savePrepareDrs ..END");
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
		LOGGER.debug("PrepareRtoCodDrsAction::discardDrs ..START");
		final RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		 RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		prepareRtoCodDrsService =getPrepareRtoCodDrsService();
		ActionMessage actionMessage = null;
		try {
			Boolean result =prepareRtoCodDrsService.discardDrs(drsTo);
			
			if(result) {
				performTwoWayWrite(drsTo);
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NO_DICARDED,drsTo.getDrsNumber());
			}else{
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED,drsTo.getDrsNumber());
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareRtoCodDrsAction::discardDrs ..CGBusinessException :",e);
			getBusinessError(request, e);
		}catch (CGSystemException e) {
			LOGGER.error("PrepareRtoCodDrsAction::discardDrs ..CGSystemException :",e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("PrepareRtoCodDrsAction::discardDrs ..Exception :",e);
			actionMessage=  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED_DB_ISSUE,drsTo.getDrsNumber());
		}finally{
			drsTo= new RtoCodDrsTO();
			initilaizeForm(drsTo,request);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			prepareActionMessage(request, actionMessage);
			((RtoCodDrsForm) form).setTo(drsTo);
		}
		
		LOGGER.debug("PrepareRtoCodDrsAction::discardDrs ..END");
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
		LOGGER.debug("PrepareRtoCodDrsAction::modifyDrs ..START");
		Boolean result=Boolean.FALSE;

		RtoCodDrsForm drsForm = (RtoCodDrsForm)form;
		final RtoCodDrsTO drsTo =(RtoCodDrsTO) drsForm.getTo();
		prepareRtoCodDrsService =getPrepareRtoCodDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareRtoCodDrsService.modifyDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_NOT_MODIFIED,new String[]{drsTo.getDrsNumber()});
			}else{
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				performTwoWayWrite(drsTo);
				jsonResult = serializer.toJSON(drsTo).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareRtoCodDrsAction::modifyDrs ..CGBusinessException :",e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareRtoCodDrsAction::modifyDrs ..CGSystemException :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request, e));
		}catch (Exception e) {
			LOGGER.error("PrepareRtoCodDrsAction::modifyDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareRtoCodDrsAction::savePrepareDrs ..END");
	}
	
	/**
	 * Ajax validate consg for rto cod drs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxValidateConsgForRtoCodDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		final RtoCodDrsTO drsToa =(RtoCodDrsTO)((RtoCodDrsForm)form).getTo();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String consgNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_CONSGNUMBER);
		DeliveryConsignmentTO dlvCnTo=null;
		try {
			out=response.getWriter();
			drsToa.setConsignmentNumber(consgNumber);
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			prepareRtoCodDrsService = getPrepareRtoCodDrsService();
			dlvCnTo=prepareRtoCodDrsService.validateRtoCodConsgments(drsToa);
			if(!StringUtil.isNull(dlvCnTo)){
				jsonResult = serializer.toJSON(dlvCnTo).toString();
			}

		}catch(CGBusinessException e){
			LOGGER.error("PrepareRtoCodDrsAction::ajaxValidateConsgForRtoCodDrs ..CGBusinessException :",e);
			jsonResult=prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareRtoCodDrsAction::ajaxValidateConsgForRtoCodDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PrepareRtoCodDrsAction:: ajaxValidateConsignment", e.getMessage());
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		
		finally {
			
				out.print(jsonResult);

			out.flush();
			out.close();
		}

	}
}
