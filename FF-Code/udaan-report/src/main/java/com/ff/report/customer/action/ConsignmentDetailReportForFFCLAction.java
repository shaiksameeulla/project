package com.ff.report.customer.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.report.action.ReportBaseAction;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.customer.service.CustomerReportService;
import com.ff.serviceOfferring.ProductTO;

public class ConsignmentDetailReportForFFCLAction extends ReportBaseAction{

	private final static Logger LOGGER = LoggerFactory.getLogger(ConsignmentDetailReportAction.class);
	
	private CustomerReportService customerReportService;
	
	public CustomerReportService getCustomerReportService() {
		if(customerReportService==null){
			customerReportService = (CustomerReportService) getBean(CommonReportConstant.CUSTOMER_REPORT_SERVICE);
		}
		return customerReportService;
	}
	
	public ActionForward getConsignmentDetailReportForFFCL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);                
			//addProductList(request, CommonReportConstant.PRODUCT_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			// need to plan an error page
			
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			//need to plan an error page
		}
		
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public void addProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		customerReportService=getCustomerReportService();
		List<ProductTO> productTo = customerReportService.getProducts();
		request.setAttribute(attributeName, productTo);
	}
}
