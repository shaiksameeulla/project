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
import com.ff.to.drs.CreditCardDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.CreditCardDrsForm;
import com.ff.web.drs.preparation.service.PrepareCreditCardDrsService;
import com.ff.web.util.UdaanWebErrorConstants;


public class PrepareCreditCardDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareCreditCardDrsAction.class);
	
	
	/** The prepare credit card service. */
	private PrepareCreditCardDrsService prepareCreditCardService;


	/**
	 * Gets the prepare credit card drs service.
	 *
	 * @return the prepare credit card drs service
	 */
	public PrepareCreditCardDrsService getPrepareCreditCardDrsService() {

		if(StringUtil.isNull(prepareCreditCardService)){
			prepareCreditCardService = (PrepareCreditCardDrsService)getBean(SpringConstants.DRS_CC_DOX_SERVICE);
		}
		return prepareCreditCardService;
	}
	
	public ActionForward viewPrepareDrsPage(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response) {
	
		LOGGER.debug("PrepareCreditCardDrsAction::viewPrepareCCAndQDrsPage ..START");
		final CreditCardDrsTO drsCCAndQSeriesTo = new CreditCardDrsTO();
		initilaizeForm(drsCCAndQSeriesTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsCCAndQSeriesTo);
		setHeaderStandardTypeDetails(request);

		((CreditCardDrsForm) form).setTo(drsCCAndQSeriesTo);

		LOGGER.debug("PrepareCreditCardDrsAction::viewPrepareCCAndQDrsPage ..END");
		
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}


/**
 * @param drsCCAndQSeriesTo
 */
private void initilaizeForm(final CreditCardDrsTO drsTo,HttpServletRequest request) {
	/** defining DRS type i.e Preparation-P */
	drsTo.setDrsType(DrsConstants.DRS_TYPE_PREPARATION);
	drsTo.setDrsScreenCode(DrsConstants.CC_Q_DRS_SCREEN_CODE);
	/** defining DRS's Consignment type i.e DOX */
	drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
	drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.CCQ_DRS_CONFIG_PARAMS_SERIES));
	drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.CC_Q_SERIES_DOX_DRS_GRID_MAX_CN));
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
	LOGGER.debug("PrepareCreditCardDrsAction::savePrepareDrs ..START");
	Boolean result=Boolean.FALSE;

	final CreditCardDrsForm drsForm = (CreditCardDrsForm)form;
	final CreditCardDrsTO drsTo =(CreditCardDrsTO) drsForm.getTo();
	prepareCreditCardService =getPrepareCreditCardDrsService();
	String jsonResult=FrameworkConstants.EMPTY_STRING;
	java.io.PrintWriter out=null;
	try {
		serializer = CGJasonConverter.getJsonObject();
		out=response.getWriter();
		response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

		result = prepareCreditCardService.savePrepareDrs(drsTo);
		if(!result){
			jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
		}else{
			performTwoWayWrite(drsTo);
			drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_GENERATED}));
			jsonResult = serializer.toJSON(drsTo).toString();
		}
	} catch (CGBusinessException e) {
		LOGGER.error("PrepareCreditCardDrsAction::savePrepareDrs ..CGBusinessException :",e);
		jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
	}catch (CGSystemException e) {
		LOGGER.error("PrepareCreditCardDrsAction::savePrepareDrs ..CGSystemException :",e);
		jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
	}catch (Exception e) {
		LOGGER.error("PrepareCreditCardDrsAction::savePrepareDrs ..Exception :",e);
		String exception=getGenericExceptionMessage(request, e);
		jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
	}
	finally {
		out.print(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.debug("PrepareCreditCardDrsAction::savePrepareDrs ..END");
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
	LOGGER.debug("PrepareCreditCardDrsAction::findDrsDetailsByDrsNumber ..START");
	final CreditCardDrsForm ccDrsForm = (CreditCardDrsForm)form;
	ActionMessage actionMessage=null;
	CreditCardDrsTO drsTo =(CreditCardDrsTO) ccDrsForm.getTo();
	initilaizeForm(drsTo,request);
	prepareCreditCardService =getPrepareCreditCardDrsService();
	final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
	if(!StringUtil.isStringEmpty(drsNumber)){
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		drsTo.setDrsNumber(drsNumber);
	}
	try {
		drsTo = prepareCreditCardService.findDrsByDrsNumber(drsTo);

		//check any warnings/Business Exceptions
		final boolean errorStatus = ExceptionUtil.checkError(drsTo);
		if(errorStatus) {
			//if so extract them and propagate to screen
			ExceptionUtil.prepareActionMessage(drsTo, request);
			saveActionMessage(request);
		}

	} catch (CGBusinessException e) {
		LOGGER.error("PrepareCreditCardDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",e);
		getBusinessError(request, e);
	}  catch (CGSystemException e) {
		LOGGER.error("PrepareCreditCardDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",e);
		drsTo = new CreditCardDrsTO();
		actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_INVALID_SEARCH_RESULTS);
	}catch (Exception e) {
		LOGGER.error("PrepareCreditCardDrsAction::findDrsDetailsByDrsNumber ..Exception :",e);
		//String exception=ExceptionUtil.getMessageFromException(e);
		drsTo = new CreditCardDrsTO();
		getGenericException(request, e);
		//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
	}finally{
		prepareActionMessage(request, actionMessage);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		setHeaderStandardTypeDetails(request);
	}

	((CreditCardDrsForm) form).setTo(drsTo);

	LOGGER.debug("PrepareCreditCardDrsAction::findDrsDetailsByDrsNumber ..END");
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
	LOGGER.debug("PrepareCreditCardDrsAction::modifyDrs ..START");
	Boolean result=Boolean.FALSE;

	CreditCardDrsForm drsForm = (CreditCardDrsForm)form;
	final CreditCardDrsTO drsTo =(CreditCardDrsTO) drsForm.getTo();
	prepareCreditCardService =getPrepareCreditCardDrsService();
	String jsonResult=FrameworkConstants.EMPTY_STRING;
	java.io.PrintWriter out=null;
	try {
		serializer = CGJasonConverter.getJsonObject();
		out=response.getWriter();
		response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

		result = prepareCreditCardService.modifyDrs(drsTo);
		if(!result){
			jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_NOT_MODIFIED,new String[]{drsTo.getDrsNumber()});
		}else{
			performTwoWayWrite(drsTo);
			drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
			jsonResult = serializer.toJSON(drsTo).toString();
		}
		
	} catch (CGBusinessException e) {
		LOGGER.error("PrepareCreditCardDrsAction::modifyDrs ..CGBusinessException :",e);
		jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
	}catch (CGSystemException e) {
		LOGGER.error("PrepareCreditCardDrsAction::modifyDrs ..CGSystemException :",e);
		String exception=getSystemExceptionMessage(request, e);
		jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
	}catch (Exception e) {
		LOGGER.error("PrepareCreditCardDrsAction::modifyDrs ..Exception :",e);
		String exception=getGenericExceptionMessage(request, e);
		jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
	}
	finally {
		out.print(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.debug("PrepareCreditCardDrsAction::modifyDrs ..END");
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
	LOGGER.debug("PrepareCreditCardDrsAction::discardDrs ..START");
	final CreditCardDrsForm drsForm = (CreditCardDrsForm)form;
	 CreditCardDrsTO drsTo =(CreditCardDrsTO) drsForm.getTo();
	prepareCreditCardService =getPrepareCreditCardDrsService();
	ActionMessage actionMessage = null;
	try {
		Boolean result =prepareCreditCardService.discardDrs(drsTo);
		
		if(result) {
			performTwoWayWrite(drsTo);
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NO_DICARDED,drsTo.getDrsNumber());
		}else{
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED,drsTo.getDrsNumber());
		}

	} catch (CGBusinessException e) {
		LOGGER.error("PrepareCreditCardDrsAction::discardDrs ..CGBusinessException :",e);
		getBusinessError(request, e);
	}catch (CGSystemException e) {
		LOGGER.error("PrepareCreditCardDrsAction::discardDrs ..CGSystemException :",e);
		getSystemException(request, e);
	} catch (Exception e) {
		LOGGER.error("PrepareCreditCardDrsAction::discardDrs ..Exception :",e);
		//actionMessage=  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED_DB_ISSUE,drsTo.getDrsNumber());
		getGenericException(request, e);
		
	}finally{
		drsTo= new CreditCardDrsTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		setHeaderStandardTypeDetails(request);
		prepareActionMessage(request, actionMessage);
		((CreditCardDrsForm) form).setTo(drsTo);
	}
	
	LOGGER.debug("PrepareCreditCardDrsAction::discardDrs ..END");
	return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
}

}