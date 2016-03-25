/**
 * 
 */
package com.ff.admin.billing.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.form.InvoiceRunSheetPrintingForm;
import com.ff.admin.billing.service.InvoiceRunSheetPrintingService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.business.CustomerTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.InvoiceRunSheetDetailsTO;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class InvoiceRunSheetPrintingAction.
 *
 * @author abarudwa
 */
public class InvoiceRunSheetPrintingAction extends AbstractBillingAction{
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InvoiceRunSheetPrintingAction.class);
	
	/** The invoice run sheet printing service. */
	private InvoiceRunSheetPrintingService invoiceRunSheetPrintingService;
	
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
		LOGGER.debug("InvoiceRunSheetPrintingAction::preparePage::START------------>:::::::");
		
		InvoiceRunSheetTO invoiceRunSheetTO = null;
		InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
		ActionMessage actionMessage = null;
		try{
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("reportUrl", composeReportUrl(userInfoTO.getConfigurableParams()));
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm) form;
			invoiceRunSheetTO = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			invoiceRunSheetPrintingService = getInvoiceRunSheetPrintingService();
			setDefaults(request, invoiceRunSheetTO);
			
			List<EmployeeTO> pickupBoysList;
			pickupBoysList = invoiceRunSheetPrintingService.getPickupBoys(invoiceRunSheetTO.getLoginOfficeId());
			if(!StringUtil.isNull(pickupBoysList)){
				request.setAttribute(BillingConstants.PICKUP_BOYS_LIST, pickupBoysList);
			}
			
			
		}catch (CGBusinessException e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::preparePage ..CGBusinessException :"+e);
			getBusinessError(request, e);
			//actionMessage =  new ActionMessage(AdminErrorConstants.NO_PICKUPBOY);
		} catch (CGSystemException e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::preparePage ..CGSystemException :"+e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("InvoiceRunSheetPrintingAction::preparePage ..Exception :"+e);
			getGenericException(request,e);
			//actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("InvoiceRunSheetPrintingAction::preparePage::END------------>:::::::");
		return mapping.findForward(BillingConstants.SUCCESS);
	}
	
	/**
	 * Sets the defaults.
	 *
	 * @param request the request
	 * @param invoiceRunSheetTO the invoice run sheet to
	 */
	private void setDefaults(HttpServletRequest request, InvoiceRunSheetTO invoiceRunSheetTO)
	{
		LOGGER.debug("InvoiceRunSheetPrintingAction::setDefaults::START------------>:::::::");
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
			
			LOGGER.debug("InvoiceRunSheetPrintingAction::setDefaults::END------------>:::::::");
	}
	
	/**
	 * Sets the invoice run sheet details.
	 *
	 * @param invoiceRunSheetTO to set InvoiceRunSheetDetails
	 */
	private void setInvoiceRunSheetDetails(InvoiceRunSheetTO invoiceRunSheetTO){
		LOGGER.debug("InvoiceRunSheetPrintingAction::setInvoiceRunSheetDetails::START------------>:::::::");
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
			invoiceRunSheetDetailsTOs.add(invoiceRunSheetDetailsTO);
		}
		invoiceRunSheetTO.setInvoiceRunSheetDetailsList(invoiceRunSheetDetailsTOs);
		
		LOGGER.debug("InvoiceRunSheetPrintingAction::setInvoiceRunSheetDetails::END------------>:::::::");
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
		LOGGER.debug("InvoiceRunSheetPrintingAction::saveInvoiceRunSheet::START------------>:::::::");
		InvoiceRunSheetTO invoiceRunSheetTO = null;
		InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try{
			out = response.getWriter();
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm) form;
			invoiceRunSheetTO = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			 invoiceRunSheetPrintingService=getInvoiceRunSheetPrintingService();
			setDefaults(request, invoiceRunSheetTO);
			setInvoiceRunSheetDetails(invoiceRunSheetTO);
			// generate invoiceRunsheetNumber
			String invoiceRunSheetNumber = invoiceRunSheetPrintingService.
					generateInvoiceRunsheetNumber(invoiceRunSheetTO.getLoginOfficeCode());
			invoiceRunSheetTO.setInvoiceRunSheetNumber(invoiceRunSheetNumber);
			invoiceRunSheetTO.setSaveOrUpdate("save");
			invoiceRunSheetTO = invoiceRunSheetPrintingService.saveInvoiceRunSheet(invoiceRunSheetTO);
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
		}
		
		finally {
			if(invoiceRunSheetTO == null){
				invoiceRunSheetTO = new InvoiceRunSheetTO();
			}
			invoiceRunSheetTO.setTransMag(transMag);
			jsonResult = serializer.toJSON(invoiceRunSheetTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("InvoiceRunSheetPrintingAction::saveInvoiceRunSheet::END------------>:::::::");
	}

	
	/**
	 * Gets the customers by pick up boy.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the customers by pick up boy
	 */
	@SuppressWarnings("static-access")
	public void getCustomersByPickUpBoy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("InvoiceRunSheetPrintingAction::getCustomersByPickUpBoy::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<Integer> branchId=new ArrayList<Integer>();
		try{
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
			if(!StringUtil.isNull(loggedInOfficeTO)){
				branchId.add(loggedInOfficeTO.getOfficeId());
			}
			
			out = response.getWriter();
			List<InvoiceRunSheetTO> invoiceRunSheetTOs = new ArrayList<>();
			InvoiceRunSheetTO invoiceRunSheet = null;
			InvoiceRunSheetPrintingForm invoiceRunSheetPrintingForm = null;
			invoiceRunSheetPrintingForm = (InvoiceRunSheetPrintingForm)form;
			invoiceRunSheet = (InvoiceRunSheetTO) invoiceRunSheetPrintingForm.getTo();
			setDefaults(request, invoiceRunSheet);
			String pickUpBoy = request.getParameter("pickUpBoy");
			Integer pickUpBoyId = Integer.parseInt(pickUpBoy);
			invoiceRunSheetPrintingService = getInvoiceRunSheetPrintingService();
			List<CustomerTO> customerTOs =  invoiceRunSheetPrintingService.getCustomers(pickUpBoyId,branchId);
			List<BillTO> billTOs = new ArrayList<>();
			if(!StringUtil.isNull(customerTOs)){
			Set <Integer> cust = new HashSet<>();
			for(CustomerTO customerTO : customerTOs){
				cust.add(customerTO.getCustomerId());
			}
			for(CustomerTO customerTO : customerTOs){
				//custId.add(customerTO.getCustomerId());
				/*if(cust.contains(customerTO.getCustomerId())){
					 shipToCodeList = invoiceRunSheetPrintingService.getShippedToCodesByCustomerId(customerTO.getCustomerId());
					 if(!StringUtil.isNull(shipToCodeList)){
						 for(String shipToCode:shipToCodeList){
						 billTOs = invoiceRunSheetPrintingService.
								getBillsData(shipToCode, invoiceRunSheet.getStartDateStr(), invoiceRunSheet.getEndDateStr());
						 if(billTOs!=null){
							 InvoiceRunSheetTO invoiceRunSheetTO  = new InvoiceRunSheetTO();
							 invoiceRunSheetTO.setBillTOs(billTOs);
							 invoiceRunSheetTO.setCustomerTO(customerTO);
							 invoiceRunSheetTO.setShipToCodeList(shipToCodeList);
							 invoiceRunSheetTOs.add(invoiceRunSheetTO);
						 }
					 }
					 cust.remove(customerTO.getCustomerId());
					 }
				  }*/
				billTOs=invoiceRunSheetPrintingService.getBillsByCustomerId(customerTO.getCustomerId(),invoiceRunSheet.getStartDateStr(), invoiceRunSheet.getEndDateStr());
			    if(!StringUtil.isNull(billTOs)){
				  Collections.sort(billTOs);
			    }  
				 if(billTOs!=null){
					 InvoiceRunSheetTO invoiceRunSheetTO  = new InvoiceRunSheetTO();
					 invoiceRunSheetTO.setBillTOs(billTOs);
					 invoiceRunSheetTO.setCustomerTO(customerTO);
					// invoiceRunSheetTO.setShipToCodeList(shipToCodeList);
					 invoiceRunSheetTOs.add(invoiceRunSheetTO);
				 }
				}
			  }
			
			if(!CGCollectionUtils.isEmpty(invoiceRunSheetTOs)){
				jsonResult = serializer.toJSON(invoiceRunSheetTOs).toString();
			}
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getCustomersByPickUpBoy() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getCustomersByPickUpBoy() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("InvoiceRunSheetPrintingAction :: getCustomersByPickUpBoy() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("InvoiceRunSheetPrintingAction::getCustomersByPickUpBoy::END------------>:::::::");
	}
	
	/**
	 * Gets the invoice run sheet.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response get details of invoicerRunsheetNumber
	 * @return the invoice run sheet
	 */
	@SuppressWarnings("static-access")
	public void getInvoiceRunSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheet::START------------>:::::::");
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
			invoiceRunSheetPrintingService = getInvoiceRunSheetPrintingService();
			List<InvoiceRunSheetTO> invoiceRunsheetList = invoiceRunSheetPrintingService.getInvoiceRunSheet(invoiceRunSheetNumber);
			request.setAttribute(BillingConstants.INVOICE_RUNSHEET_LIST, invoiceRunsheetList);
			if(!CGCollectionUtils.isEmpty(invoiceRunsheetList)){
				jsonResult = serializer.toJSON(invoiceRunsheetList).toString();
			}
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			//String exception=ExceptionUtil.getMessageFromException(e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);		
			//jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch(Exception e){
			LOGGER.error("InvoiceRunSheetPrintingAction :: getInvoiceRunSheet() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}	
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheet::END------------>:::::::");
	}
	
	/**
	 * Gets the invoice run sheet printing service.
	 *
	 * @return the invoiceRunSheetPrintingService
	 */
	public InvoiceRunSheetPrintingService getInvoiceRunSheetPrintingService() {
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::START------------>:::::::");
		if (StringUtil.isNull(invoiceRunSheetPrintingService)){
			invoiceRunSheetPrintingService = (InvoiceRunSheetPrintingService) getBean(AdminSpringConstants.INVOICE_RUN_SHEET_PRINTING_SERVICE);
		}
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::END------------>:::::::");
		return invoiceRunSheetPrintingService;
	}
	
	
}
