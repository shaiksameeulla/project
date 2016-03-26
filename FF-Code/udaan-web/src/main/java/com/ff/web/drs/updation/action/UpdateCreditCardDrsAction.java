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
import com.ff.to.drs.CreditCardDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.CreditCardDrsForm;
import com.ff.web.drs.updation.service.UpdateCreditCardDrsService;
import com.ff.web.util.UdaanWebErrorConstants;



/**
 * @author nihsingh
 *
 */
public class UpdateCreditCardDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateCreditCardDrsAction.class);
	
	
	private UpdateCreditCardDrsService updateCCDrsService;
	
	/**
	 * Gets the update credit card drs service.
	 *
	 * @return the update credit card drs service
	 */
	public UpdateCreditCardDrsService getUpdateCreditCardDrsService() {

		if(StringUtil.isNull(updateCCDrsService)){
			updateCCDrsService = (UpdateCreditCardDrsService)getBean(SpringConstants.DRS_UPDATE_CC_SERVICE);
		}
		return updateCCDrsService;
	}
	
	public ActionForward viewUpdateDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateCreditCardDrsAction::viewUpdateDrsPage ..START");
		
		final CreditCardDrsTO drsTo = new CreditCardDrsTO();
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		initilaizeForm(drsTo,request);
		((CreditCardDrsForm) form).setTo(drsTo);
		
		LOGGER.debug("UpdateCreditCardDrsAction::viewUpdateDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
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
		LOGGER.debug("UpdateCreditCardDrsAction::findDrsNumberForUpdate ..START");
		ActionMessage actionMessage=null;
		final CreditCardDrsForm drsForm = (CreditCardDrsForm)form;
		CreditCardDrsTO drsTo =(CreditCardDrsTO) drsForm.getTo();
		initilaizeForm(drsTo,request);
		
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		updateCCDrsService = getUpdateCreditCardDrsService();
		try {
			drsTo = updateCCDrsService.findDrsByDrsNumberForDoxUpdate(drsTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("UpdateCreditCardDrsAction::findDrsNumberForUpdate ..CGBusinessException :",e);
			drsTo= new CreditCardDrsTO();
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("UpdateCreditCardDrsAction::findDrsNumberForUpdate ..CGSystemException :",e);
			drsTo = new CreditCardDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			drsTo = new CreditCardDrsTO();
			LOGGER.error("UpdateCreditCardDrsAction::findDrsNumberForUpdate ..Exception :",e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
			getGenericException(request, e);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			setGridStandardTypeDetails(request);
			setCCGridStandardTypeDetails(request);
		}
		((CreditCardDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateCreditCardDrsAction::findDrsNumberForUpdate ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final CreditCardDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Preparation-P */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_UPDATE);
		drsTo.setDrsScreenCode(DrsConstants.CC_Q_DRS_SCREEN_CODE);
		/** defining DRS's Consignment type i.e DOX */
		drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.CCQ_DRS_CONFIG_PARAMS_SERIES));
	}
	
	/**
	 * setStandardTypeDetails : To set particular Standard type details in the Dropdowns
	 * @param request
	 */
	public void setCCGridStandardTypeDetails(HttpServletRequest request) {
		setIdProofDtls(request);
		setRelationDtls(request);
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
		LOGGER.debug("UpdateCreditCardDrsAction::updateDrs ..START");
		Boolean result=Boolean.FALSE;

		final CreditCardDrsForm drsForm = (CreditCardDrsForm)form;
		final CreditCardDrsTO drsTo =(CreditCardDrsTO) drsForm.getTo();
		updateCCDrsService =getUpdateCreditCardDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = updateCCDrsService.updateDeliveredDrsForDox(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("UpdateCreditCardDrsAction::updateDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("UpdateCreditCardDrsAction::updateDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("UpdateCreditCardDrsAction::updateDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("UpdateCreditCardDrsAction::updateDrs ..END");
	}
	
}
