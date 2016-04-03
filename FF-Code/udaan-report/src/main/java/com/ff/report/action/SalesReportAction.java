package com.ff.report.action;

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
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author khassan
 * 
 */
public class SalesReportAction extends ReportBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentBookingReportAction.class);
	private CommonReportService commonReportService;

	public ActionForward getBASalesReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request);
			// addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);

		} catch (CGBusinessException e) {
			LOGGER.error("SalesReportAction :: getBASalesReport ::" + e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("SalesReportAction :: getBASalesReport ::" + e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);

	}

	public ActionForward getCustomerWiseQualityReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			addCommonParams(request);
			commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<StockStandardTypeTO> typeTo = commonReportService
					.getStandardTypeForLcCod(CommonReportConstant.CUSTOMER_QUALITY);
			request.setAttribute("typeTO", typeTo);
			List<RateProductCategoryTO> rateProductTo = commonReportService
					.getRateProdcuts();
			request.setAttribute("rateProductTo", rateProductTo);
		} catch (CGBusinessException e) {
			LOGGER.error("SalesReportAction :: getCustomerWiseQualityReport ::"
					+ e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("SalesReportAction :: getCustomerWiseQualityReport ::"
					+ e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);

	}

}
