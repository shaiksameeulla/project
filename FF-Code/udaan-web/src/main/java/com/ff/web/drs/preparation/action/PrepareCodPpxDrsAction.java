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
import com.ff.to.drs.CodLcDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.CodLcToPayDrsForm;
import com.ff.web.drs.preparation.service.PrepareCodPpxDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author nihsingh
 *
 */
public class PrepareCodPpxDrsAction extends AbstractDeliveryAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareCodPpxDrsAction.class);
	
	private PrepareCodPpxDrsService prepareCodPpxDrsService;
	
	
	/**
	 * Gets the prepare cod ppx drs service.
	 *
	 * @return the prepare cod ppx drs service
	 */
	public PrepareCodPpxDrsService getPrepareCodPpxDrsService() {
		
		if(StringUtil.isNull(prepareCodPpxDrsService)){
			prepareCodPpxDrsService = (PrepareCodPpxDrsService)getBean(SpringConstants.DRS_COD_LC_PPX_SERVICE);
		}
		return prepareCodPpxDrsService;
	}
	
	public ActionForward viewPrepareDrsPage(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response) {
	
		LOGGER.debug("PrepareCodPpxDrsAction::viewPrepareCodDoxDrsPage ..START");
		
		final CodLcDrsTO drsCodLcSeriesTo = new CodLcDrsTO();
		initilaizeForm(drsCodLcSeriesTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsCodLcSeriesTo);
		setHeaderStandardTypeDetails(request);

		((CodLcToPayDrsForm) form).setTo(drsCodLcSeriesTo);

		LOGGER.debug("PrepareCodPpxDrsAction::viewPrepareCodDoxDrsPage ..END");
		
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	

	/**
	 * @param drsCodLcSeriesTo
	 */
	private void initilaizeForm(final CodLcDrsTO drsTo,HttpServletRequest request) {
		/** defining DRS type i.e Preparation-P */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_PREPARATION);
		drsTo.setDrsScreenCode(DrsConstants.COD_LC_TO_PAY_SCREEN_CODE);
		/** defining DRS's Consignment type i.e DOX */
		drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.COD_LC_PPX_DRS_CONFIG_PARAMS_SERIES));
		drsTo.setIsPricingInfoRequired(true);
		drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.COD_LC_TO_PAY_PPX_DRS_GRID_MAX_CN));
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
	public void saveCodLcPpxPrepareDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PrepareCodPpxDrsAction::saveCodLcPpxPrepareDrs ..START");
		Boolean result=Boolean.FALSE;

		final CodLcToPayDrsForm drsForm = (CodLcToPayDrsForm)form;
		final CodLcDrsTO drsTo =(CodLcDrsTO) drsForm.getTo();
		prepareCodPpxDrsService =getPrepareCodPpxDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareCodPpxDrsService.savePrepareDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_GENERATED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareCodPpxDrsAction::saveCodLcPpxPrepareDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareCodPpxDrsAction::saveCodLcPpxPrepareDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PrepareCodPpxDrsAction::saveCodLcPpxPrepareDrs ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareCodDoxDrsAction::saveCodLcDoxPrepareDrs ..END");
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
		LOGGER.debug("PrepareCodPpxDrsAction::findDrsDetailsByDrsNumber ..START");
		ActionMessage actionMessage=null;
		final CodLcToPayDrsForm ccLcDrsForm = (CodLcToPayDrsForm)form;
		CodLcDrsTO drsTo =(CodLcDrsTO) ccLcDrsForm.getTo();
		initilaizeForm(drsTo,request);
		prepareCodPpxDrsService =getPrepareCodPpxDrsService();
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		try {
			drsTo = prepareCodPpxDrsService.findDrsByDrsNumber(drsTo);

			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareCodPpxDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",e);
			getBusinessError(request, e);
		}  catch (CGSystemException e) {
			LOGGER.error("PrepareCodPpxDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",e);
			drsTo = new CodLcDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			LOGGER.error("PrepareCodPpxDrsAction::findDrsDetailsByDrsNumber ..Exception :",e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			drsTo = new CodLcDrsTO();
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
			getGenericException(request, e);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
		}

		((CodLcToPayDrsForm) form).setTo(drsTo);

		LOGGER.debug("PrepareCodPpxDrsAction::findDrsDetailsByDrsNumber ..END");
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
		LOGGER.debug("PrepareCodPpxDrsAction::modifyDrs ..START");
		Boolean result=Boolean.FALSE;

		CodLcToPayDrsForm drsForm = (CodLcToPayDrsForm)form;
		final CodLcDrsTO drsTo =(CodLcDrsTO) drsForm.getTo();
		prepareCodPpxDrsService =getPrepareCodPpxDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = prepareCodPpxDrsService.modifyDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_NOT_MODIFIED,new String[]{drsTo.getDrsNumber()});
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("PrepareCodPpxDrsAction::modifyDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PrepareCodPpxDrsAction::modifyDrs ..CGSystemException :",e);
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}catch (Exception e) {
			LOGGER.error("PrepareCodPpxDrsAction::modifyDrs ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PrepareCodPpxDrsAction::modifyDrs ..END");
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
		LOGGER.debug("PrepareCodPpxDrsAction::discardDrs ..START");
		final CodLcToPayDrsForm drsForm = (CodLcToPayDrsForm)form;
		 CodLcDrsTO drsTo =(CodLcDrsTO) drsForm.getTo();
		prepareCodPpxDrsService =getPrepareCodPpxDrsService();
		ActionMessage actionMessage = null;
		try {
			Boolean result =prepareCodPpxDrsService.discardDrs(drsTo);
			
			if(result) {
				performTwoWayWrite(drsTo);
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NO_DICARDED,drsTo.getDrsNumber());
			}else{
				//FIXME  need to add
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED,drsTo.getDrsNumber());
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PrepareCodPpxDrsAction::discardDrs ..CGBusinessException :",e);
			getBusinessError(request, e);
		}catch (CGSystemException e) {
			LOGGER.error("PrepareCodPpxDrsAction::discardDrs ..CGSystemException :",e);
			getSystemException(request, e);
		} catch (Exception e) {
			//actionMessage=  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_DISCARDED_DB_ISSUE,drsTo.getDrsNumber());
			getGenericException(request, e);
			LOGGER.error("PrepareCodPpxDrsAction::discardDrs ..Exception :",e);
		}finally{
			drsTo=new CodLcDrsTO();
			prepareActionMessage(request, actionMessage);
			initilaizeForm(drsTo,request);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			((CodLcToPayDrsForm) form).setTo(drsTo);
		}
		
		LOGGER.debug("PrepareCodPpxDrsAction::discardDrs ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}

	
}
