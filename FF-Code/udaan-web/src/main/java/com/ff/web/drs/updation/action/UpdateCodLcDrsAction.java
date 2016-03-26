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
import com.ff.to.drs.CodLcDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.form.CodLcToPayDrsForm;
import com.ff.web.drs.updation.service.UpdateCodLcDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class UpdateCodLcDrsAction.
 *
 * @author nihsingh
 */
public class UpdateCodLcDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateCodLcDrsAction.class);
	
	private UpdateCodLcDrsService updateCoDLcDrsService;
	
	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;
	
	/**
	 * Gets the update co d lc drs service.
	 *
	 * @return the update co d lc drs service
	 */
	public UpdateCodLcDrsService getUpdateCoDLcDrsService() {
		
		if(StringUtil.isNull(updateCoDLcDrsService)){
			updateCoDLcDrsService = (UpdateCodLcDrsService)getBean(SpringConstants.DRS_UPDATE_COD_LC_SERVICE);
		}
		return updateCoDLcDrsService;
	}
	
	/**
	 * Sets the service offering common service.
	 * 
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}
	
	/**
	 * Gets the service offering common service.
	 * 
	 * @return the serviceOfferingCommonService
	 */
	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}


	public ActionForward viewUpdateDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("UpdateCodLcDrsAction::viewUpdateDrsPage ..START");
		
		final CodLcDrsTO drsTo = new CodLcDrsTO();
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		initilaizeForm(drsTo,request);
		((CodLcToPayDrsForm) form).setTo(drsTo);
		
		
		LOGGER.debug("UpdateCodLcDrsAction::viewUpdateDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final CodLcDrsTO drsTo,final HttpServletRequest request) {
		/** defining DRS type i.e Updation-U */
		drsTo.setDrsType(DrsConstants.DRS_TYPE_UPDATE);
		drsTo.setDrsScreenCode(DrsConstants.COD_LC_TO_PAY_SCREEN_CODE);
		/** defining DRS's Consignment type i.e DOX */
		drsTo.setConsignmentType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		drsTo.setAllowedSeries(getConfigParamValue(request, UniversalDeliveryContants.COD_LC_DOX_DRS_CONFIG_PARAMS_SERIES));
		drsTo.setModeOfPaymentCheque(DrsConstants.MODE_OF_PAYMENT_CHEQUE);
		drsTo.setModeOfPaymentCash(DrsConstants.MODE_OF_PAYMENT_CASH);
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
		LOGGER.debug("UpdateCodLcDrsAction::findDrsNumberForUpdate ..START");
		ActionMessage actionMessage=null;
		final CodLcToPayDrsForm drsForm = (CodLcToPayDrsForm)form;
		CodLcDrsTO drsTo =(CodLcDrsTO) drsForm.getTo();
		initilaizeForm(drsTo,request);
		
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		updateCoDLcDrsService = getUpdateCoDLcDrsService();
		try {
			drsTo = updateCoDLcDrsService.findDrsByDrsNumberForDoxUpdate(drsTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("UpdateCodLcDrsAction::findDrsNumberForUpdate ..CGBusinessException :",e);
			drsTo = new CodLcDrsTO();
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("UpdateCodLcDrsAction::findDrsNumberForUpdate ..CGSystemException :",e);
			drsTo = new CodLcDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			drsTo = new CodLcDrsTO();
			LOGGER.error("UpdateCodLcDrsAction::findDrsNumberForUpdate ..Exception :",e);
			drsTo = new CodLcDrsTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}finally{
			prepareActionMessage(request, actionMessage);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
			setGridStandardTypeDetails(request);
			setCodLcGridStandardTypeDetails(request);
		}
		((CodLcToPayDrsForm) form).setTo(drsTo);
		LOGGER.debug("UpdateCodLcDrsAction::findDrsNumberForUpdate ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * setStandardTypeDetails : To set particular Standard type details in the Dropdowns
	 * @param request
	 */
	public void setCodLcGridStandardTypeDetails(HttpServletRequest request) {
		setModeOfPaymentDtls(request);
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
		LOGGER.debug("UpdateCodLcDrsAction::updateDrs ..START");
		Boolean result=Boolean.FALSE;

		final CodLcToPayDrsForm drsForm = (CodLcToPayDrsForm)form;
		final CodLcDrsTO drsTo =(CodLcDrsTO) drsForm.getTo();
		updateCoDLcDrsService =getUpdateCoDLcDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = updateCoDLcDrsService.updateDeliveredDrsForDox(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_MODIFIED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("UpdateCodLcDrsAction::updateDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("UpdateCodLcDrsAction::updateDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("UpdateCodLcDrsAction::updateDrs ..Exception :",e);
			//String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("UpdateCodLcDrsAction::updateDrs ..END");
	}
	
}
