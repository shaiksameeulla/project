/**
 * 
 */
package com.ff.admin.stockmanagement.excelupload.action;

import java.io.File;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.stockmanagement.common.action.AbstractStockAction;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.excelupload.form.StockExcelUploadForm;
import com.ff.admin.stockmanagement.excelupload.service.StockExcelUploadService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockExcelUploadTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.umc.UserTO;

/**
 * @author mohammes
 *
 */
public class StockExcelUploadAction extends AbstractStockAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockExcelUploadAction.class);
	
	/** The cancellation service. */
	public StockExcelUploadService stockExcelUploadService;
	       
	/**
	 * View form details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("StockExcelUploadAction::viewFormDetails ..Start");
		
		StockExcelUploadTO excelUploadTO = new StockExcelUploadTO();
		pageStartup(request, excelUploadTO);
		((StockExcelUploadForm) form).setTo(excelUploadTO);
		
		LOGGER.debug("StockExcelUploadAction::viewFormDetails ..END");
		
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}
	
	
	/**
	 * Gets the stock cancellation service.
	 *
	 * @return the stock cancellation service
	 */
	private StockExcelUploadService getStockExcelUploadService() {
		if(StringUtil.isNull(stockExcelUploadService)){
			stockExcelUploadService = (StockExcelUploadService)getBean(AdminSpringConstants.STOCK_EXCEL_UPLOAD_SERVICE);
		}
			return stockExcelUploadService;
	}
	
/**
 * Cancellation start up.
 *
 * @param request the request
 * @param to the to
 */
	private void pageStartup(HttpServletRequest request,StockExcelUploadTO to) {

		UserTO userTo = getLoginUserTO(request);
		if(userTo!=null) {
			to.setLoggedInUserId(userTo.getUserId());


			OfficeTO officeTo = getLoginOfficeTO(request);

			if(officeTo!=null){
				to.setLoggedInOfficeId(officeTo.getOfficeId());

				to.setLoggedInOfficeCode(officeTo.getOfficeCode());
			}

			if(StringUtil.isStringEmpty(to.getExcelUploadDateStr())){
				to.setExcelUploadDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
			}

			setGlobalDetails(request,(StockHeaderTO)to);
			if(!StringUtil.isStringEmpty(to.getOfficeType())){
				to.setCanUpdate(StockCommonConstants.CAN_UPDATE);
			}
		}
	}

/**
 * Find details by cancellation number.
 *
 * @param mapping the mapping
 * @param form the form
 * @param request the request
 * @param response the response
 * @return the action forward
 */
public ActionForward saveStockExcelUpload(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response){

	LOGGER.debug("StockExcelUploadAction::saveStockExcelUpload ..Start");
	
	stockExcelUploadService = getStockExcelUploadService();
	StockExcelUploadForm cancelForm = (StockExcelUploadForm)form;
	StockExcelUploadTO excelUploadTO = (StockExcelUploadTO)cancelForm.getTo();
	ActionMessage actionMessage = null;
	Boolean result=false;
	
	 try {
		 excelUploadTO.setFilePath(getServlet().getServletContext().getRealPath(
					File.separator));
		 result = stockExcelUploadService.saveStockExcelUpload(excelUploadTO);
		final boolean errorStatus1 = ExceptionUtil.checkError(excelUploadTO);
		 if(errorStatus1) {
			ExceptionUtil.prepareActionMessage(excelUploadTO, request);
			saveActionMessage(request);
		 }
		 if(result){
			 //actionMessage =  new ActionMessage(AdminErrorConstants.STOCK_EXCEL_SAVED_WITH_NUMBER,excelUploadTO.getAcknowledgementNumber());
		 }
	} catch (CGBusinessException e) {
		excelUploadTO = new StockExcelUploadTO();
		LOGGER.error("StockExcelUploadAction::saveStockExcelUpload::CGBusinessException ..Exception",e);
		getBusinessError(request,e);
	} catch (CGSystemException e) {
		excelUploadTO = new StockExcelUploadTO();
		getSystemException(request, e);
		LOGGER.error("StockExcelUploadAction::saveStockExcelUpload::CGSystemException  ..Exception",e);
	}catch (Exception e) {
		excelUploadTO = new StockExcelUploadTO();
		getGenericException(request, e);
		LOGGER.error("StockExcelUploadAction::saveStockExcelUpload::Exception  ..Exception",e);
	} finally {
		setUrl(request,"./stockExcelUpload.do?submitName=viewFormDetails");
		prepareActionMessage(request, actionMessage);
		pageStartup(request, excelUploadTO);
	}
	((StockExcelUploadForm) form).setTo(excelUploadTO);
	LOGGER.debug("StockExcelUploadAction::saveStockExcelUpload ..END");
	return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);

	
}




}
