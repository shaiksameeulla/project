/**
 * 
 */
package com.ff.report.billing.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.organization.OfficeTO;
import com.ff.report.billing.constants.BillingConstants;
import com.ff.report.billing.form.InvoiceRunSheetPrintingForm;
import com.ff.report.billing.service.InvoiceRunSheetUpdateService;
import com.ff.report.constants.AdminErrorConstants;
import com.ff.report.constants.AdminSpringConstants;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.InvoiceRunSheetDetailsTO;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;

// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetUpdateAction.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetUpdateAction extends AbstractBillingAction{

	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InvoiceRunSheetUpdateAction.class);
	
	/** The invoice run sheet update service. */
	private InvoiceRunSheetUpdateService invoiceRunSheetUpdateService;
	
	/**
	 * Prepare page.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InvoiceRunSheetUpdateAction::preparePage::START------------>:::::::");
		
		InvoiceRunSheetTO invoiceRunSheetTO = null;
		InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
		ActionMessage actionMessage = null;
		try{
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm) form;
			invoiceRunSheetTO = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			invoiceRunSheetUpdateService = getInvoiceRunSheetUpdateService();
			setDefaults(request, invoiceRunSheetTO);
			
			List<StockStandardTypeTO> runSheetStatus;
			runSheetStatus = invoiceRunSheetUpdateService.getRunSheetStatus();
			if(!StringUtil.isNull(runSheetStatus)){
			request.setAttribute(BillingConstants.RUNSHEET_STATUS, runSheetStatus);
			}
		}catch (CGBusinessException e) {
			LOGGER.error("InvoiceRunSheetUpdateAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.NO_RUNSHEETSTATUS_FOUND);
		} catch (CGSystemException e) {
			LOGGER.error("InvoiceRunSheetUpdateAction::preparePage ..CGSystemException :"+e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.NO_RUNSHEETSTATUS_FOUND);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("InvoiceRunSheetUpdateAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("InvoiceRunSheetUpdateAction::preparePage::END------------>:::::::");
		return mapping.findForward(BillingConstants.SUCCESS);
	}
	
	
	private void setDefaults(HttpServletRequest request, InvoiceRunSheetTO invoiceRunSheetTO)
	{
		LOGGER.debug("InvoiceRunSheetUpdateAction::setDefaults::START------------>:::::::");
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(BillingConstants.USER_INFO);
		
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			invoiceRunSheetTO.setLoginOfficeId(officeTO.getOfficeId());
			invoiceRunSheetTO.setLoginOfficeCode(officeTO.getOfficeCode());
		}
		invoiceRunSheetTO.setCreatedDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		invoiceRunSheetTO.setUpdatedDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		
		if(!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpUserId())){
			invoiceRunSheetTO.setCreatedBy(userInfoTO.getEmpUserTo().getEmpUserId());
		}
		if(!StringUtil.isNull(userInfoTO.getEmpUserTo().getEmpUserId())){
			invoiceRunSheetTO.setUpdatedBy(userInfoTO.getEmpUserTo().getEmpUserId());
		}
		LOGGER.debug("InvoiceRunSheetUpdateAction::setDefaults::END------------>:::::::");
	}
	/**
	 * Gets the invoice run sheet.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response get details of Invoicerunsheet number
	 * @return the invoice run sheet
	 */
	@SuppressWarnings("static-access")
	public void getInvoiceRunSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("InvoiceRunSheetUpdateAction::getInvoiceRunSheet::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try{
			out = response.getWriter();
			InvoiceRunSheetTO invoiceRunSheetTO = null;
			InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm)form;
			invoiceRunSheetTO = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			setDefaults(request, invoiceRunSheetTO);
			String invoiceRunSheetNumber = request.getParameter("invoiceRunSheetNumber");
			invoiceRunSheetUpdateService = getInvoiceRunSheetUpdateService();
			List<InvoiceRunSheetTO> invoiceRunSheetTOs = invoiceRunSheetUpdateService.getInvoiceRunSheet(invoiceRunSheetNumber);
			
			if(!CGCollectionUtils.isEmpty(invoiceRunSheetTOs)){
				jsonResult = serializer.toJSON(invoiceRunSheetTOs).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("InvoiceRunSheetUpdateAction::getInvoiceRunSheet::END------------>:::::::");
	}
	
	/**
	 * Sets the invoice run sheet details.
	 *
	 * @param invoiceRunSheetTO to set InvoiceRunSheetDetails
	 */
	private void setInvoiceRunSheetDetails(InvoiceRunSheetTO invoiceRunSheetTO){
		LOGGER.debug("InvoiceRunSheetUpdateAction::setInvoiceRunSheetDetails::START------------>:::::::");
		ArrayList<InvoiceRunSheetDetailsTO> invoiceRunSheetDetailsTOs = new ArrayList<InvoiceRunSheetDetailsTO>();
		int customerIdCount = invoiceRunSheetTO.getCustomerIds().length;
		for(int rowCount =0; rowCount<customerIdCount; rowCount++){
			InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO = new InvoiceRunSheetDetailsTO();
			CustomerTO customerTO = new CustomerTO();
			BillTO billTO = new BillTO();
			if(!StringUtil.isNull(invoiceRunSheetTO.getCustomerIds()[rowCount])){
				customerTO.setCustomerId(invoiceRunSheetTO.getCustomerIds()[rowCount]);
				invoiceRunSheetDetailsTO.setCustomerTO(customerTO);
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getInvoiceIds()[rowCount])){
				billTO.setInvoiceId(invoiceRunSheetTO.getInvoiceIds()[rowCount]);
				invoiceRunSheetDetailsTO.setInvoiceBillTO(billTO);
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getShipToCode()[rowCount])){
				invoiceRunSheetDetailsTO.setShipToCode(invoiceRunSheetTO.getShipToCode()[rowCount]);
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getStatus()[rowCount]) && !StringUtil.isStringEmpty(invoiceRunSheetTO.getStatus()[rowCount])){
				invoiceRunSheetDetailsTO.setStatus(invoiceRunSheetTO.getStatus()[rowCount]);
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getReceiverName()[rowCount])){
				invoiceRunSheetDetailsTO.setReceiverName(invoiceRunSheetTO.getReceiverName()[rowCount]);
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getInvoiceRunSheetId())){
				invoiceRunSheetDetailsTO.setInvoiceRunSheetId(invoiceRunSheetTO.getInvoiceRunSheetId());
				}
			if(!StringUtil.isNull(invoiceRunSheetTO.getInvoiceRunSheetDetailId()[rowCount])){
				invoiceRunSheetDetailsTO.setInvoiceRunSheetDetailId(invoiceRunSheetTO.getInvoiceRunSheetDetailId()[rowCount]);
				}
			if(!StringUtil.isEmptyInteger(invoiceRunSheetTO.getCreatedBy())){
				invoiceRunSheetDetailsTO.setCreatedBy(invoiceRunSheetTO.getCreatedBy());
			}
			if(!StringUtil.isEmptyInteger(invoiceRunSheetTO.getUpdatedBy())){
				invoiceRunSheetDetailsTO.setUpdatedBy(invoiceRunSheetTO.getUpdatedBy());
			}
			if(!StringUtil.isStringEmpty(invoiceRunSheetTO.getCreatedDateStr())){
				invoiceRunSheetDetailsTO.setCreatedDateStr(invoiceRunSheetTO.getCreatedDateStr());
			}
			if(!StringUtil.isStringEmpty(invoiceRunSheetTO.getUpdatedDateStr())){
				invoiceRunSheetDetailsTO.setUpdatedDateStr(invoiceRunSheetTO.getUpdatedDateStr());
			}
			invoiceRunSheetDetailsTO.setSaveOrUpdate("update");
			invoiceRunSheetDetailsTOs.add(invoiceRunSheetDetailsTO);
		}
		invoiceRunSheetTO.setInvoiceRunSheetDetailsList(invoiceRunSheetDetailsTOs);
		LOGGER.debug("InvoiceRunSheetUpdateAction::setInvoiceRunSheetDetails::END------------>:::::::");
	}
	
	/**
	 * Save invoice run sheet.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @throws CGBaseException the cG base exception
	 */
	@SuppressWarnings("static-access")
	public void saveInvoiceRunSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.debug("InvoiceRunSheetUpdateAction::saveInvoiceRunSheet::START------------>:::::::");
		InvoiceRunSheetTO invoiceRunSheetTO = null;
		InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try{
			out = response.getWriter();
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm) form;
			invoiceRunSheetTO = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			invoiceRunSheetUpdateService=getInvoiceRunSheetUpdateService();
			setDefaults(request, invoiceRunSheetTO);
			setInvoiceRunSheetDetails(invoiceRunSheetTO);
			invoiceRunSheetTO.setSaveOrUpdate("update");
			invoiceRunSheetTO = invoiceRunSheetUpdateService.saveInvoiceRunSheet(invoiceRunSheetTO);
			transMag = FrameworkConstants.SUCCESS_FLAG;
			
		}catch (CGBusinessException e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::saveInvoiceRunSheet() .. :"+e);
			//getBusinessError(request, e);
			transMag = AdminErrorConstants.BillINVOICE_DATA_NOT_SAVED;
		} catch (CGSystemException e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::saveInvoiceRunSheet() .. :"+e);
			transMag = AdminErrorConstants.DATABASE_ISSUE;
		}catch (Exception e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::saveInvoiceRunSheet() .. :"+e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			transMag = AdminErrorConstants.BillINVOICE_DATA_NOT_SAVED;
		}finally {
			if(invoiceRunSheetTO == null){
				invoiceRunSheetTO = new InvoiceRunSheetTO();
			}
			invoiceRunSheetTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(invoiceRunSheetTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("InvoiceRunSheetUpdateAction::saveInvoiceRunSheet::END------------>:::::::");
	}

	/**
	 * Gets the invoice run sheet update service.
	 *
	 * @return the invoiceRunSheetUpdateService
	 */
	public InvoiceRunSheetUpdateService getInvoiceRunSheetUpdateService() {
		LOGGER.debug("InvoiceRunSheetUpdateAction::getInvoiceRunSheetUpdateService::START------------>:::::::");
		if (StringUtil.isNull(invoiceRunSheetUpdateService)){
			invoiceRunSheetUpdateService = (InvoiceRunSheetUpdateService) getBean(AdminSpringConstants.INVOICE_RUN_SHEET_UPDATE_SERVICE);
		}
		LOGGER.debug("InvoiceRunSheetUpdateAction::getInvoiceRunSheetUpdateService::END------------>:::::::");
		return invoiceRunSheetUpdateService;
	}
	
	

}
