/**
 * 
 */
package com.ff.web.drs.list.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.list.ListDrsHeaderTO;
import com.ff.web.common.SpringConstants;
import com.ff.web.drs.common.action.AbstractDeliveryAction;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.list.form.ListDrsForm;
import com.ff.web.drs.list.service.ListDrsService;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class ListDrsAction extends AbstractDeliveryAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ListDrsAction.class);
	public transient ListDrsService listDrsService;
	
	
	public ListDrsService getServiceForListDrs() {
		
		if(StringUtil.isNull(listDrsService)){
			listDrsService = (ListDrsService)getBean(SpringConstants.DRS_LIST_SERVICE);
		}
		return listDrsService;
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
	public ActionForward viewListDrsPage(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("ListDrsAction::ListDrsAction ..START");
		final ListDrsHeaderTO drsTo = new ListDrsHeaderTO();
		listDrsService=getServiceForListDrs();
		formInitializer(request, drsTo);
		((ListDrsForm) form).setTo(drsTo);
		
		LOGGER.debug("ListDrsAction::ListDrsAction ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	/**
	 * @param request
	 * @param drsTo
	 */
	private void formInitializer(final HttpServletRequest request,
			final ListDrsHeaderTO drsTo) {
		setGlobalDetails(request, (AbstractDeliveryTO)drsTo);
		//Map<Integer,String> dlvEmp=null;
		Map<String,String> dlvEmpBA=null;
		try {
				// dlvEmp=listDrsService.getAllDeliveryEmployees(drsTo);
			dlvEmpBA=listDrsService.getDrsPartyDetailsByDate(drsTo);
						
		} catch (Exception e) {
			LOGGER.error("ListDrsAction:: viewListDrsPage:: FieldStaff Dropdown ::Exception", e);
		}
		if(CGCollectionUtils.isEmpty(dlvEmpBA)) {
			prepareActionMessage(request, new ActionMessage(UdaanWebErrorConstants.DRS_NO_DRS_CREATED_FOR_FS));
			LOGGER.warn("ListDrsAction:: viewListDrsPage :: FieldStaff Dropdown Dropdown Details Does not exist");
		}
		request.setAttribute(DrsCommonConstants.DLV_FIELD_STAFF_REQ_PARAM, dlvEmpBA);
		
		prepareLoadNumbers(request);
		drsTo.setDrsScreenCode(DrsConstants.LIST_DRS_SCREEN_CODE);
	}
	
	/**
	 * getAllDrsByOfficeAndEmployee
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAllDrsByOfficeAndEmployee(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.debug("ListDrsAction::getAllDrsByOfficeAndEmployee ..START");
		ListDrsForm drsForm = (ListDrsForm)form;
		ListDrsHeaderTO drsTo =(ListDrsHeaderTO) drsForm.getTo();
		listDrsService=getServiceForListDrs();
		try {
			listDrsService.getAllDrsByOfficeAndEmployee(drsTo);
		} catch (CGBusinessException e) {
			getBusinessError(request,e);
			LOGGER.debug("ListDrsAction::getAllDrsByOfficeAndEmployee::CGBusinessException ..",e);
		}catch (CGSystemException e) {
			LOGGER.error("ListDrsAction::getAllDrsByOfficeAndEmployee ..CGSystemException :",e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ListDrsAction::getAllDrsByOfficeAndEmployee::Exception ..",e);
			getGenericException(request, e);
			//prepareActionMessage(request,new ActionMessage(UdaanWebErrorConstants.DRS_NOT_FOUND_REASON,exception));
		}finally{
			formInitializer(request, drsTo);
			((ListDrsForm) form).setTo(drsTo);
		}
		
		LOGGER.debug("ListDrsAction::getAllDrsByOfficeAndEmployee ..END");
		return mapping.findForward(DrsConstants.SUCCESS_FORWARD);
	}
	
	
}
