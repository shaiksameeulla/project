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
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.RegionTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;

/**
 * @author ssaraf
 *
 */
public class PODReportAction extends ReportBaseAction{
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PODReportAction.class);

	public ActionForward getPODReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);

		
		try {
			addCommonParams(request);                
			//addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			
		} catch (CGBusinessException e) {
			LOGGER.error("PODReportAction :: getPODReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("PODReportAction :: getPODReport ::"+e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public void addRegionList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {

		List<RegionTO> regionTo =  commonReportService.getRegions();
		request.setAttribute(attributeName, regionTo);
	}

	public void addProductList(HttpServletRequest request, String attributeName) throws CGBusinessException, CGSystemException{
		List<ProductTO> productTo = commonReportService.getProducts();
		request.setAttribute(attributeName, productTo);
		
	}
}
