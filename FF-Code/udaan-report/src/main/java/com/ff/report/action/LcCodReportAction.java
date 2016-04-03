package com.ff.report.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.geography.RegionTO;
import com.ff.report.LcCodReportAliasTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

public class LcCodReportAction extends ReportBaseAction {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LcCodReportAction.class);
	private CommonReportService commonReportService;
	public transient JSONSerializer serializer;
	
	
	public void preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("LcCodReportAction::preparePage::START----->");
		ActionMessage actionMessage = null;
		try {
			addCommonParams(request); 
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<StockStandardTypeTO> sortingOrderTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.GET_SORTING_ORDER);
			List<StockStandardTypeTO> productTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.GET_PRODUCT);
			List<StockStandardTypeTO> typeTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.GET_TYPE);
			List<StockStandardTypeTO> summaryOptionTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.GET_SUMMARY_OPTION);
			List<StockStandardTypeTO> sortingTo=commonReportService.getStandardTypeForLcCod(CommonReportConstant.GET_SORTING);
			List<RegionTO> regionList=commonReportService.getRegions();
			request.setAttribute("regionList", regionList);
			
			request.setAttribute("sortingOrderTo", sortingOrderTo);
			request.setAttribute("productTo", productTo);
			request.setAttribute("typeTo", typeTo);
			request.setAttribute("summaryOptionTo", summaryOptionTo);
			request.setAttribute("sortingTo", sortingTo);
			
			
			
			
			
			
		}  catch (CGBusinessException e) {
			LOGGER.error("LcCodReportAction::findDrsDetailsByDrsNumber ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("LcCodReportAction::findDrsDetailsByDrsNumber ..CGSystemException :"+e);
			//actionMessage =  new ActionMessage(e.getMessage());
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("LcCodReportAction::findDrsDetailsByDrsNumber ..Exception :"+e);
			getGenericException(request,e);
			//prepareCommonException(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		//return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	public ActionForward prepareInSummaryLcCod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LcCodReportAction::prepareInSummaryLcCod::START----->");
		
		preparePage(mapping,form,request,response);
		   
			
		LOGGER.debug("LcCodReportAction::prepareInSummaryLcCod::START----->");
		return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	public ActionForward preparePreAlertLcCod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LcCodReportAction::preparePreAlertLcCod::START----->");
		
		preparePage(mapping,form,request,response);
			
		LOGGER.debug("LcCodReportAction::preparePreAlertLcCod::START----->");
		return  mapping.findForward(CommonReportConstant.LCCOD_PRE_ALERT);
	}
	
	public ActionForward preparePartWiseLcCod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LcCodReportAction::preparePartWiseLcCod::START----->");
		try {
			preparePage(mapping,form,request,response);
			addCommonParams(request);                
			
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		}
		
			
		LOGGER.debug("LcCodReportAction::preparePartWiseLcCod::START----->");
		return  mapping.findForward(CommonReportConstant.LCCOD_PARTY_WISE);
	}
	
	
	public ActionForward prepareOutSummaryLcCod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("LcCodReportAction::prepareOutSummaryLcCod::START----->");
		
		preparePage(mapping,form,request,response);
			
		LOGGER.debug("LcCodReportAction::prepareOutSummaryLcCod::START----->");
		return  mapping.findForward(CommonReportConstant.LCCOD_OUT_SUMMARY);
	}
	
	public void getCustomerByRegionAndProduct (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LOGGER.debug("LcCodReportAction::getCustomerByRegionAndProduct::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		List<LcCodReportAliasTO> CustomerList=new  ArrayList<LcCodReportAliasTO>() ;
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		try {
			 out = response.getWriter();
			String prodSeries =	request.getParameter("ProdSeries");	
			String regionId =	request.getParameter("RegionId");
			String regionIds[]=regionId.split(",");
			List<Integer> rids=new ArrayList<Integer>();
			for(int i=0;i<regionIds.length;i++){
				rids.add(Integer.parseInt(regionIds[i].trim()));
			}
			//Integer region=Integer.parseInt(regionId.trim());
			String branches[]=prodSeries.split(",");
			
			
			List<String> ids=new ArrayList<String>();
			for(int i=0;i<branches.length;i++){
				ids.add(branches[i].trim());
			}
		
			CustomerList=commonReportService.getCustomerByRegionAndProduct(rids,ids);
			
			if(!CGCollectionUtils.isEmpty(CustomerList)){
				jsonResult = serializer.toJSON(CustomerList).toString();
			}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("LcCodReportAction :: getCustomerByRegionAndProduct() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("LcCodReportAction :: getCustomerByRegionAndProduct() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("LcCodReportAction :: getCustomerByRegionAndProduct() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LcCodReportAction::getCustomerByRegionAndProduct::END----->");
	}
}
