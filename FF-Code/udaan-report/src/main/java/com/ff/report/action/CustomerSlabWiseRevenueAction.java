package com.ff.report.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.geography.CityTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;


public class CustomerSlabWiseRevenueAction extends ReportBaseAction{
	
	/*private BillPrintingService billPrintingService;
	private BillingCommonService billingCommonService;*/
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerSlabWiseRevenueAction.class);
	
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addCommonParams(request);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("CustomerSlabWiseRevenueAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("CustomerSlabWiseRevenueAction :: getBookingSummaryReport ::"+e);
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
	
	public void getSlabList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("CustomerSlabWiseRevenueAction::getSlabList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		try {
			    out = response.getWriter();
				String product =	request.getParameter("product");	
				Integer productId = Integer.parseInt(product);
				List<VobSlabTO> slabTOs=commonReportService.getSlabList(productId);
				
				if(!CGCollectionUtils.isEmpty(slabTOs)){
					
					jsonResult = JSONSerializer.toJSON(slabTOs).toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CustomerSlabWiseRevenueAction :: getSlabList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CustomerSlabWiseRevenueAction :: getSlabList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("CustomerSlabWiseRevenueAction :: getSlabList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CustomerSlabWiseRevenueAction::getSlabList::END----->");
	}

}
