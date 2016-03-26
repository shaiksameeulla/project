/**
 * 
 */
package com.ff.web.drs.updation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.updation.form.PendingDrsForm;
import com.ff.web.drs.updation.service.PendingDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class PendingDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PendingDrsAction.class);

	private PendingDrsService pendingDrsService;

	
	/**
	 * Gets the pending drs service.
	 *
	 * @return the pending drs service
	 */
	public PendingDrsService getPendingDrsService() {

		if(StringUtil.isNull(pendingDrsService)){
			pendingDrsService = (PendingDrsService)getBean(SpringConstants.DRS_PENDING_CONSG_SERVICE);
		}
		return pendingDrsService;
	}
	/**
	 * Name : viewPendingDrsPage
	 * purpose : to view Update DRS for Pending consignments
	 * Input :
	 * return : populate form with default values.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewPendingDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PendingDrsAction::viewPendingDrsPage ..START");
		final PendingDrsHeaderTO drsTo = new PendingDrsHeaderTO();
		initilaizeForm(drsTo,request);
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		((PendingDrsForm) form).setTo(drsTo);
		LOGGER.debug("PendingDrsAction::viewPendingDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param drsTo
	 */
	private void initilaizeForm(final PendingDrsHeaderTO drsTo,final HttpServletRequest request) {
		drsTo.setDrsScreenCode(DrsConstants.PENDING_DRS_SCREEN_CODE);
		drsTo.setReasonsForMisCard(getConfigParamValue(request, UniversalDeliveryContants.DRS_MISSED_CARD_REASON));
		drsTo.setDrsType(DrsConstants.DRS_TYPE_UPDATE);
	}

	public ActionForward searchPendingConsignments(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PendingDrsAction::searchPendingConsignments ..START");

		PendingDrsForm drsForm = (PendingDrsForm)form;
		PendingDrsHeaderTO drsTo =(PendingDrsHeaderTO) drsForm.getTo();
		pendingDrsService = getPendingDrsService();
		try {
			pendingDrsService.searchPendingConsignments(drsTo);
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PendingDrsAction::searchPendingConsignments ..CGBusinessException :",e);
			drsTo = new PendingDrsHeaderTO();
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("PendingDrsAction::searchPendingConsignments ..CGSystemException :",e);
			drsTo = new PendingDrsHeaderTO();
			getSystemException(request, e);
		}catch (Exception e) {
			LOGGER.error("PendingDrsAction::searchPendingConsignments ..Exception :",e);
			drsTo = new PendingDrsHeaderTO();
			//String exception=ExceptionUtil.getMessageFromException(e);
			//prepareActionMessage(request,new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception));
			getGenericException(request, e);
		}finally{
			initilaizeForm(drsTo, request);
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			setHeaderStandardTypeDetails(request);
		}
		((PendingDrsForm) form).setTo(drsTo);
		LOGGER.debug("PendingDrsAction::searchPendingConsignments ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	/**
	 * Ajax validate consignment by drs number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxValidateConsignmentByDrsNumber(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request,final HttpServletResponse response)
			{
		java.io.PrintWriter out=null;
		PendingDrsForm drsForm = (PendingDrsForm)form;
		final AbstractDeliveryTO drsToa =(AbstractDeliveryTO) drsForm.getTo();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		final String consgNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_CONSGNUMBER);
		//final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		DeliveryDetailsTO dlvCnTo=null;
		try {
			out=response.getWriter();
			drsToa.setConsignmentNumber(consgNumber);
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			deliveryCommonService = getCommonServiceForDelivery();
			dlvCnTo=deliveryCommonService.validateConsignmentByDrsNumber(drsToa);
			if(!StringUtil.isNull(dlvCnTo)){
				jsonResult = serializer.toJSON(dlvCnTo).toString();
			}
		}catch(CGBusinessException e){
			LOGGER.error("PendingDrsAction::ajaxValidateConsignmentByDrsNumber ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PendingDrsAction::ajaxValidateConsignmentByDrsNumber ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PendingDrsAction:: ajaxValidateConsignmentByDrsNumber", e.getMessage());
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		
		finally {
				out.print(jsonResult);
				out.flush();
				out.close();
		}

	}
	
	
	/**
	 * Update undelivered drs.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void updateUndeliveredDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("PendingDrsAction::updateUndeliveredDrs ..START");
		Boolean result=Boolean.FALSE;

		final PendingDrsForm drsForm = (PendingDrsForm)form;
		final PendingDrsHeaderTO drsTo =(PendingDrsHeaderTO) drsForm.getTo();
		
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			pendingDrsService = getPendingDrsService();
			result = pendingDrsService.updateUndeliveredDrsConsg(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_NOT_MODIFIED,new String[]{drsTo.getDrsNumber()});
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),drsTo.getDrsStatus()}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("PendingDrsAction::updateUndeliveredDrs ..CGBusinessException :",e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("PendingDrsAction::updateUndeliveredDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("PendingDrsAction::updateUndeliveredDrs ..Exception :",e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,getGenericExceptionMessage(request, e));
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("updateUndeliveredDrs::updateUndeliveredDrs ..END");
	}
}
