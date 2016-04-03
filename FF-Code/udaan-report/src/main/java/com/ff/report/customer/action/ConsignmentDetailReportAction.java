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

public class ConsignmentDetailReportAction extends ReportBaseAction{

	private final static Logger LOGGER = LoggerFactory.getLogger(ConsignmentDetailReportAction.class);
	
	private CustomerReportService customerReportService;
	
	public CustomerReportService getCustomerReportService() {
		if(customerReportService==null){
			customerReportService = (CustomerReportService) getBean(CommonReportConstant.CUSTOMER_REPORT_SERVICE);
		}
		return customerReportService;
	}
	
	
	public ActionForward getConsignmentDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			addCommonParams(request);  
			//addOfficeList(request, CommonReportConstant.OFFICE_TO);               
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentDetailReportAction :: getConsignmentDetailReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentDetailReportAction :: getConsignmentDetailReport ::"+e);
			// need to plan an error page
		}
		return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	/*@SuppressWarnings("unchecked")
	public void addOfficeList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		
		
		HttpSession session=request.getSession(false);		
		List<OfficeTO> officeTo = new ArrayList<OfficeTO>();
		officeTo = (List<OfficeTO>)session.getAttribute(attributeName);

		if(CGCollectionUtils.isEmpty(officeTo)) {
			//regionTo = new ArrayList<RegionTO>();
			customerReportService = (CustomerReportService) getBean(CommonReportConstant.CUSTOMER_REPORT_SERVICE);
			officeTo = customerReportService.getAllOffices();
			
			session.setAttribute(attributeName, officeTo);
		}
		
			request.setAttribute(attributeName, officeTo);
		
	}*/
	
	public void addProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		customerReportService=getCustomerReportService();
		List<ProductTO> productTo = customerReportService.getProducts();
		request.setAttribute(attributeName, productTo);
	}

	
}
