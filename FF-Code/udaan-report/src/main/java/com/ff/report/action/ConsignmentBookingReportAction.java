package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.PriorityTypeTO;
import com.ff.report.billing.constants.BillingConstants;
import com.ff.report.billing.service.BillingCommonService;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.constants.AdminSpringConstants;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.FinancialProductTO;

/**
 * @author ssaraf
 *
 */
public class ConsignmentBookingReportAction extends ReportBaseAction { 
	
	private CommonReportService commonReportService;
	private BillingCommonService billingCommonService;
	private final static Logger LOGGER = LoggerFactory.getLogger(ConsignmentBookingReportAction.class);
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}
	
	
	public ActionForward getBookingSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);                
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingSummaryReport ::"+e);
			// need to plan an error page
		}
		return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward getBookingDetailsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		billingCommonService = (BillingCommonService)getBean(AdminSpringConstants.BILLING_COMMON_SERVICE);
		try {
			addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);                
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			//addCustomerType(request, CommonReportConstant.CUSTOMER_TYPE_TO);
			List<FinancialProductTO> productTo= billingCommonService.getProducts();
			List<PriorityTypeTO>priorityType=commonReportService.getPriorityType();
			
			request.setAttribute(BillingConstants.PRODUCT_TO, productTo);
			request.setAttribute("PriorityTypeTos", priorityType);
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			// need to plan an error page
			
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			//need to plan an error page
		}
		
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward getBranchCashBookReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);                
		} catch (CGBusinessException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBookingDetailsReport ::"+e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	/*public void addRegionList(HttpServletRequest request, String attributeName) throws CGBusinessException, CGSystemException{
		
		List<RegionTO> regionTo = commonReportService.getRegions();
		request.setAttribute(attributeName, regionTo);
	}*/
	
/*	public void addProductList(HttpServletRequest request, String attributeName) throws CGBusinessException, CGSystemException{
		List<ProductTO> productTo = commonReportService.getProducts();
		request.setAttribute(attributeName, productTo);
	}*/
	

	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

	    
		LOGGER.debug("ConsignmentBookingReportAction::getStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		List<CityTO> stationList = new ArrayList<CityTO>();
		try {
			    out = response.getWriter();
				//String region =	request.getParameter("region");	
				//Integer regionId = Integer.parseInt(region);
				//List<CityTO> stationList=commonReportService.getCitiesByRegionId(regionId);
			    
			    String region = request.getParameter("region");	
				String[] regionIdContent = region.split(",");
				Integer[] regionId = new Integer[regionIdContent.length];
				for(int i = 0; i < regionIdContent.length; i++) {
					regionId[i] = Integer.parseInt(regionIdContent[i]);
				}
				stationList = commonReportService.getCitiesByMultipleRegionId(regionId);
				
				if(!CGCollectionUtils.isEmpty(stationList)){
					
					jsonResult = JSONSerializer.toJSON(stationList).toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ConsignmentBookingReportAction::getStations::END----->");
	
		
	}
	
	//shaheed
	
	public void getAllStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

	    
		LOGGER.debug("ConsignmentBookingReportAction::getAllStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		List<CityTO> stationList = new ArrayList<CityTO>();
		try {
			    out = response.getWriter();
				//String region =	request.getParameter("region");	
				//Integer regionId = Integer.parseInt(region);
				//List<CityTO> stationList=commonReportService.getCitiesByRegionId(regionId);
			    
			    //String region = request.getParameter("region");	
				//String[] regionIdContent = region.split(",");
//				//Integer[] regionId = new Integer[regionIdContent.length];
//				for(int i = 0; i < regionIdContent.length; i++) {
//					regionId[i] = Integer.parseInt(regionIdContent[i]);
//				}
				stationList = commonReportService.getAllCities();
				
				if(!CGCollectionUtils.isEmpty(stationList)){
					
					jsonResult = JSONSerializer.toJSON(stationList).toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getAllStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getAllStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("ConsignmentBookingReportAction :: getAllStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ConsignmentBookingReportAction::getAllStations::END----->");
	
		
	}
	
	
	
	
	
	//shaheed
	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("ConsignmentBookingReportAction::getBranchList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		 try {
			out = response.getWriter();
			String region =	request.getParameter("cityID");	
			Integer cityID = Integer.parseInt(region);
			List<OfficeTO> officeTO =  commonReportService.getOfficesByCityIdForReport(cityID);
			jsonResult = JSONSerializer.toJSON(officeTO).toString();
			
			
			
		} catch (IOException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBranchList() ::"+e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, e.toString());
			
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getBranchList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (CGBusinessException e) {
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			LOGGER.error("ConsignmentBookingReportAction :: getBranchList() ::"+e);
		} finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}
	
	public void getCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("ConsignmentBookingReportAction::getCustomerList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		 try {
			out = response.getWriter();
			String branch =	request.getParameter("branch");	
			Integer branchID = Integer.parseInt(branch);
			List<CustomerTO> customerTO =  commonReportService.getCustomersByOfficeId(branchID);
			jsonResult = JSONSerializer.toJSON(customerTO).toString();
			
		} catch (IOException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getCustomerList() ::"+e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, e.toString());
			
		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getCustomerList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (CGBusinessException e) {
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			LOGGER.error("ConsignmentBookingReportAction :: getCustomerList() ::"+e);
		} finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}

}
