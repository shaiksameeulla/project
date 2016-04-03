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
import com.ff.manifest.LoadLotTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;

/**
 * The Class BRRCommonReportAction.
 */
public class BRRCommonReportAction extends ReportBaseAction{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BRRCommonReportAction.class);
	private CommonReportService commonReportService;
	       
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			try {
				addCommonParams(request);
				getFilterFields(request);
			} catch (CGBusinessException e) {
				LOGGER.error("BRRReportAction ::viewFormDetails ", e);
			} catch (CGSystemException e) {
				LOGGER.error("BRRReportAction :: viewFormDetails ::", e);
			}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	public void getFilterFields(HttpServletRequest request) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ProductTO> products=null;
		List<ConsignmentTypeTO> cnTypes=null;
		List<CategoryReportAliasTO> category=null;
		List<LoadLotTO> load=null;
		try {
			products = commonReportService.getProducts();
			cnTypes = commonReportService.getConsignmentType();
			category=commonReportService.getCategory();
			load= commonReportService.getLoad();
			
		} catch (CGBusinessException e) {
			LOGGER.error("BRRReportAction ::getFilterFields ", e);
		} catch (CGSystemException e) {
			LOGGER.error("BRRReportAction ::getFilterFields ", e);
		}
		request.setAttribute(CommonReportConstant.PRODUCTS, products);
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		request.setAttribute(CommonReportConstant.CATEGORY, category);
		request.setAttribute(CommonReportConstant.LOAD, load);

	}
		
}
