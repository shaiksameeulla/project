package com.ff.admin.complaints.action;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.SearchServiceRequestForm;
import com.ff.admin.complaints.service.ServiceRequestForServiceReqService;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.complaints.SearchServiceRequestHeaderTO;

/**
 * @author mohammes
 *
 */
public class SearchServiceRequestAction extends AbstractComplaintsAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SearchServiceRequestAction.class);	
	
	private ServiceRequestForServiceReqService serviceRequestForServiceReqService;
	
	private ServiceRequestForServiceReqService getServiceRequestForServiceReqService() {
		if (StringUtil.isNull(serviceRequestForServiceReqService)){
			serviceRequestForServiceReqService = (ServiceRequestForServiceReqService) getBean(ComplaintsCommonConstants.SERVICE_REQUEST_FOR_SERVICE_REQ_SERVICE);
		}
		return serviceRequestForServiceReqService;
	}
	
	public ActionForward preparePage( ActionMapping mapping,   ActionForm form,
			HttpServletRequest request,   HttpServletResponse response) {

		LOGGER.debug("SearchServiceRequestAction::preparePage::START------------>:::::::");

		SearchServiceRequestHeaderTO serviceTO = new SearchServiceRequestHeaderTO();

		loadPageDropDownValues(request);
		((SearchServiceRequestForm) form).setTo(serviceTO);
		LOGGER.debug("SearchServiceRequestAction::preparePage::END------------>:::::::");
		return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
	}

	private void loadPageDropDownValues(HttpServletRequest request)
			 {
		
		// Search Query list
		try {
			getSearchCategoryList(request);
		} catch (CGBusinessException| CGSystemException e) {
			LOGGER.error("SearchServiceRequestAction::loadPageDropDownValues::Exception------------>:::::::",e);
		}
	}

	
		public ActionForward searchServiceReqDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response)  {
			LOGGER.debug("SearchServiceRequestAction::searchServiceReqDetails::START------------>:::::::");
			SearchServiceRequestHeaderTO serviceTO = null;
			ActionMessage actionMessage = null;
			SearchServiceRequestForm serviceForm = (SearchServiceRequestForm) form;
			serviceTO = (SearchServiceRequestHeaderTO) serviceForm.getTo();
			try {
				serviceRequestForServiceReqService = getServiceRequestForServiceReqService();
				serviceRequestForServiceReqService.searchServiceRequestDtls(serviceTO);
				
			} catch (CGBusinessException e) {
				serviceTO= new SearchServiceRequestHeaderTO();
				LOGGER.error("SearchServiceRequestAction::searchServiceReqDetails ..CGBusinessException::"+e.getLocalizedMessage());
				getBusinessError(request, e);
			} catch (CGSystemException e) {
				LOGGER.error("SearchServiceRequestAction::searchServiceReqDetails ..CGSystemException::"+e.getLocalizedMessage());
				serviceTO= new SearchServiceRequestHeaderTO();
				getSystemException(request, e);
				
			} catch (Exception e) {
				LOGGER.error("SearchServiceRequestAction::searchServiceReqDetails ..Generic Exception::"+e.getLocalizedMessage());
				getGenericException(request, e);
			}finally{
				prepareActionMessage(request, actionMessage);
				loadPageDropDownValues(request);
				((SearchServiceRequestForm) form).setTo(serviceTO);
			}
			LOGGER.debug("SearchServiceRequestAction::searchServiceReqDetails::END------------>:::::::");
			return mapping.findForward(StockCommonConstants.SUCCESS_FORWARD);
		}
	
		
		
}
