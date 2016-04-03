package com.ff.report.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

import net.sf.json.JSONSerializer;

public class SaleDSRReportAction extends ReportBaseAction {
	private final static Logger LOGGER = LoggerFactory.getLogger(SaleDSRReportAction.class);
	private CommonReportService commonReportService;
	public transient JSONSerializer serializer;
	
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addCommonParams(request);
		//	addRegionList(request, CommonReportConstant.REGION_TO);
			//addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("SaleDSRReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("SaleDSRReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		}
		return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward viewDSR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		return  mapping.findForward(CommonReportConstant.VIEW_DSR_REPORT);
	}
	
	
	@SuppressWarnings("static-access")
	public void  calcWorkingDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	    
		LOGGER.debug("SaleDSRReportAction::calcWorkingDays::START----->");
		String jsonResult =null;
		PrintWriter out = null;				
		try {
			    out = response.getWriter();
				String region =	request.getParameter("region");	
				Integer regionId = Integer.parseInt(region);
				String fromDate=request.getParameter("fromDate");
				String totDays =	request.getParameter("totalDays");	
				Integer totalNoDays = Integer.parseInt(totDays);
				commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
				Long totHoliday=commonReportService.getworkingDaysFromHoliday(regionId,fromDate);
				Long totWorking=totalNoDays.longValue()-totHoliday;
			    //Integer abc=   new Integer(totWorking.intValue());
				request.setAttribute("workingDays", totWorking);
				if(!StringUtil.isEmptyLong(totWorking)){
					jsonResult = totWorking.toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("BillPrintingAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
		LOGGER.debug("SaleDSRReportAction::calcWorkingDays::END----->");
		//return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward prepareDsrPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addCommonParams(request);
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<StockStandardTypeTO> typeTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.DAILY_SALES_TYPE);
			request.setAttribute("typeTO", typeTo);
			request.setAttribute("todayDate", DateUtil.todayDate());
		//	addRegionList(request, CommonReportConstant.REGION_TO);
			//addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("SaleDSRReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("SaleDSRReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		}
		return  mapping.findForward(CommonReportConstant.VIEW_DSR);
	}
	
}
