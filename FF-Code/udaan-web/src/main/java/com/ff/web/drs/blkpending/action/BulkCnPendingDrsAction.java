/**
 * 
 */
package com.ff.web.drs.blkpending.action;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.blkpending.form.BulkCnPendingDrsForm;
import com.ff.web.drs.blkpending.service.BulkCnPendingDrsService;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class BulkCnPendingDrsAction extends AbstractDeliveryAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkCnPendingDrsAction.class);

	private BulkCnPendingDrsService bulkCnPendingDrsService;

	public BulkCnPendingDrsService getBulkCnPendingDrsService() {

		if(StringUtil.isNull(bulkCnPendingDrsService)){
			bulkCnPendingDrsService = (BulkCnPendingDrsService)getBean(SpringConstants.DRS_BULK_PENDING_SERVICE);
		}
		return bulkCnPendingDrsService;
	}

	/**
	 * View list drs page.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewBulkPendingDrsPageForHub(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("BulkCnPendingDrsAction::viewBulkPendingDrsPage ..START");
		final PendingDrsHeaderTO drsTo = new PendingDrsHeaderTO();
		try {

			initializeFormForHub(request, drsTo);
			if(!drsTo.getLoggedInOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
				prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_BLK_SCRN_NOT_ACCESS));
				drsTo.setCanUpdate(DrsConstants.CAN_UPDATE);
			}
		} catch (Exception e) {
			LOGGER.error("BulkCnPendingDrsAction::viewBulkPendingDrsPageForHUB ..ERROR",e);
		}
		((BulkCnPendingDrsForm) form).setTo(drsTo);
		LOGGER.debug("BulkCnPendingDrsAction::viewBulkPendingDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD_BLK_HUB);
	}

	/**
	 * @param request
	 * @param drsTo
	 */
	public void initializeFormForHub(final HttpServletRequest request,
			final PendingDrsHeaderTO drsTo) {
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		initilaizeBasicForm(drsTo,request);
	}
	/**
	 * View list drs page.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewBulkPendingDrsPageForBranch(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("BulkCnPendingDrsAction::viewBulkPendingDrsPage ..START");
		final PendingDrsHeaderTO drsTo = new PendingDrsHeaderTO();
		try {

			initializeFormForBranch(request, drsTo);
			if(!drsTo.getLoggedInOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)){
				prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_BLK_SCRN_NOT_ACCESS));
				drsTo.setCanUpdate(DrsConstants.CAN_UPDATE);
			}
		} catch (Exception e) {
			LOGGER.error("BulkCnPendingDrsAction::viewBulkPendingDrsPageForBranch ..ERROR",e);
		}
		((BulkCnPendingDrsForm) form).setTo(drsTo);
		LOGGER.debug("BulkCnPendingDrsAction::viewBulkPendingDrsPage ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD_BLK_BRANCH);
	}

	/**
	 * @param request
	 * @param drsTo
	 */
	public void initializeFormForBranch(final HttpServletRequest request,
			final PendingDrsHeaderTO drsTo) {
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		initilaizeBasicForm(drsTo,request);

	}

	/**
	 * @param drsTo
	 */
	private void initilaizeBasicForm(final PendingDrsHeaderTO drsTo,final HttpServletRequest request) {
		drsTo.setConsignmentType(DrsConstants.CONSG_TYPE_NA);
		drsTo.setDrsFor(DrsConstants.DRS_FOR_FIELD_STAFF);
		if(StringUtil.isEmptyInteger(drsTo.getDrsPartyId())){
			drsTo.setDrsPartyId(getLoginEmpTO(request).getEmployeeId());

		}
		if(StringUtil.isStringEmpty(drsTo.getFsOutTimeDateStr())){
			drsTo.setFsOutTimeDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}

		if(!StringUtil.isStringEmpty(drsTo.getLoggedInOfficeType())){

			if(drsTo.getLoggedInOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
				drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.BULK_PENDING_HUB_DRS_GRID_MAX_CN));
				drsTo.setDrsScreenCode(DrsConstants.BULK_PENDING_DRS_SCREEN_CODE_HUB);
			}else if (drsTo.getLoggedInOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)){
				drsTo.setMaxAllowedRows(getMaxRowForDrs(request,UniversalDeliveryContants.BULK_PENDING_BRANCH_DRS_GRID_MAX_CN));
				drsTo.setDrsScreenCode(DrsConstants.BULK_PENDING_DRS_SCREEN_CODE_BRANCH);
			}

		}

		getPendingReasonsForBulkDRS(request);
		prepareBulkDrsLoadTypeDropdownDtls(request);

	}

	public void updatePendingDrs(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("BulkCnPendingDrsAction::updatePendingDrs ..START");
		Boolean result=Boolean.FALSE;

		final BulkCnPendingDrsForm drsForm = (BulkCnPendingDrsForm)form;
		final PendingDrsHeaderTO drsTo =(PendingDrsHeaderTO) drsForm.getTo();
		bulkCnPendingDrsService =getBulkCnPendingDrsService();
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		java.io.PrintWriter out=null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			result = bulkCnPendingDrsService.saveBulkPendingDrs(drsTo);
			if(!result){
				jsonResult = getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_NOT_SAVED,null);
			}else{
				performTwoWayWrite(drsTo);
				drsTo.setSuccessMessage(getMessageFromErrorBundle(request,UdaanWebErrorConstants.DRS_INFO_SAVED,new String[]{drsTo.getDrsNumber(),DrsCommonConstants.DRS_GENERATED}));
				jsonResult = serializer.toJSON(drsTo).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("BulkCnPendingDrsAction::updatePendingDrs ..CGBusinessException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("BulkCnPendingDrsAction::updatePendingDrs ..CGSystemException :",e);
			jsonResult= prepareCommonException(DrsConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("BulkCnPendingDrsAction::updatePendingDrs ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(DrsConstants.ERROR_FLAG,exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkCnPendingDrsAction::updatePendingDrs ..END");
	}

	public ActionForward findDrsDetailsByDrsNumber(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("BulkCnPendingDrsAction::findDrsDetailsByDrsNumber ..START");
		ActionMessage actionMessage=null;
		String drsNavigator=DrsConstants.SUCCESS_FORWARD_BLK_BRANCH;
		final BulkCnPendingDrsForm drsForm = (BulkCnPendingDrsForm)form;
		PendingDrsHeaderTO drsTo =(PendingDrsHeaderTO) drsForm.getTo();
		bulkCnPendingDrsService =getBulkCnPendingDrsService();
		String ypDrs=null;
		final String drsNumber=request.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
		if(!StringUtil.isStringEmpty(drsNumber)){
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			drsTo.setDrsNumber(drsNumber);
		}
		try {
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			initilaizeBasicForm(drsTo,request);
			bulkCnPendingDrsService.findBulkPendingDrs(drsTo);
			ypDrs=drsTo.getYpDrs();
			//check any warnings/Business Exceptions
			final boolean errorStatus = ExceptionUtil.checkError(drsTo);
			if(errorStatus) {
				//if so extract them and propagate to screen
				ExceptionUtil.prepareActionMessage(drsTo, request);
				saveActionMessage(request);
			}


		} catch (CGBusinessException e) {
			LOGGER.error("BulkCnPendingDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",e);
			getBusinessError(request, e);
			drsTo = new PendingDrsHeaderTO();
		} catch (CGSystemException e) {
			LOGGER.error("BulkCnPendingDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",e);
			drsTo = new PendingDrsHeaderTO();
			actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND);
		}catch (Exception e) {
			drsTo = new PendingDrsHeaderTO();
			LOGGER.error("BulkCnPendingDrsAction::findDrsDetailsByDrsNumber ..Exception :",e);
			getGenericException(request, e);
			//actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception);
		}finally{
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
			prepareActionMessage(request, actionMessage);
			initilaizeBasicForm(drsTo, request);
		}
		if(!StringUtil.isStringEmpty(ypDrs)){
			drsTo.setYpDrs(ypDrs);
		}
		if(!StringUtil.isStringEmpty(drsTo.getLoggedInOfficeType()) && drsTo.getLoggedInOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
			drsNavigator=DrsConstants.SUCCESS_FORWARD_BLK_HUB;
		}
		((BulkCnPendingDrsForm) form).setTo(drsTo);

		LOGGER.debug("BulkCnPendingDrsAction::findDrsDetailsByDrsNumber ..END");
		return mapping.findForward(drsNavigator);
	}

	public ActionForward savePendingDrsForHub(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("BulkCnPendingDrsAction::savePendingDrsForHub ..START");
		boolean result=false;
		final BulkCnPendingDrsForm drsForm = (BulkCnPendingDrsForm)form;
		PendingDrsHeaderTO drsTo =(PendingDrsHeaderTO) drsForm.getTo();
		bulkCnPendingDrsService =getBulkCnPendingDrsService();
		ActionMessage actionMessage=null;
		try {
			result=bulkCnPendingDrsService.saveBulkPendingDrs(drsTo);
			if(result){			
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_INFO_SAVED,new Object[]{drsTo.getDrsNumber(),drsTo.getDrsStatus()});

			}else {			
				actionMessage =  new ActionMessage(UdaanWebErrorConstants.DRS_INFO_NOT_SAVED);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("BulkCnPendingDrsAction::savePendingDrsForHub ..CGBusinessException :",e);
			getBusinessError(request, e);
		}catch (CGSystemException e) {
			LOGGER.error("BulkCnPendingDrsAction::savePendingDrsForHub ..CGSystemException :",e);
			getSystemException(request, e);
		}catch (Exception e) {
			LOGGER.error("BulkCnPendingDrsAction::savePendingDrsForHub ..Exception :",e);
			getGenericException(request, e);
			
		}
		finally {
			drsTo= new PendingDrsHeaderTO();
			setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		initilaizeBasicForm(drsTo, request);
		prepareActionMessage(request, actionMessage);
		}
		((BulkCnPendingDrsForm) form).setTo(drsTo);
		LOGGER.debug("BulkCnPendingDrsAction::savePendingDrsForHub ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD_BLK_HUB);

	}
}
