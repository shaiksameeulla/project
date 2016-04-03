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
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;


public class RegionWiseSalesRevenueAction extends ReportBaseAction{
	
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory.getLogger(RegionWiseSalesRevenueAction.class);
	
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addCommonParams(request);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("RegionWiseSalesRevenueAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("RegionWiseSalesRevenueAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		}
		return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	/**
	 * @param request
	 * @param attributeName
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void addProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<RateProductCategoryTO> productTo = commonReportService.getRateProdcuts();
		request.setAttribute(attributeName, productTo);
	}
}
