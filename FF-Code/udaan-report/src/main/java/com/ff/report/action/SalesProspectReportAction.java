package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.VobSlabTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;



public class SalesProspectReportAction extends ReportBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(SalesProspectReportAction.class);
	
	/** The transfer service. */
	
	private CommonReportService commonReportService;
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}		

	
	public ActionForward getSalesProspectsReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
	
			if (commonReportService != null) {
				
				//List<EmployeeTO>  salesPersonTO=null;
				List<RateIndustryTypeTO>  businessTypeTO=null;
				try {	
					addCommonParams(request);					
					//salesPersonTO=commonReportService.getSalesPersonsTitlesList("Sales");
					businessTypeTO=commonReportService.getNatureOfBusinesses();
					addSalesProductList(request, CommonReportConstant.PRODUCT_TO);
				} catch (CGBusinessException e) {			
					LOGGER.error("SalesProspectReportAction :: getSalesProspectsReport() ::",e);
				} catch (CGSystemException e) {				
					LOGGER.error("SalesProspectReportAction :: getSalesProspectsReport() ::",e);
				}
			//					if (!StringUtil.isEmptyColletion(salesPersonTO)) 
			//	{				
			//		request.setAttribute(CommonReportConstant.SALES_PERSON_TO, salesPersonTO);
			//	}
				if (!StringUtil.isEmptyColletion(businessTypeTO)) {				
					request.setAttribute(CommonReportConstant.BUSINESS_TYPE_TO, businessTypeTO);
				}
				
			}
			return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	
		}
	public void getSlabList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("SalesProspectReportAction::getSlabList::START----->");
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
			LOGGER.error("SalesProspectReportAction :: getSlabList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SalesProspectReportAction :: getSlabList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SalesProspectReportAction :: getSlabList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SalesProspectReportAction::getSlabList::END----->");
	}

	
	/**
	 * @param request
	 * @param attributeName
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void addSalesProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<RateProductCategoryTO> productTo = commonReportService.getRateProdcuts();
		request.setAttribute(attributeName, productTo);
	}
	
	
		public void getBranchList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			LOGGER.debug("SalesProspectReportAction::getBranchList::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			
			 try {
				out = response.getWriter();
				String region =	request.getParameter("cityID");	
				Integer cityID = Integer.parseInt(region);
				List<OfficeTO> officeTO =  commonReportService.getOfficesByCityIdForReport(cityID);
				jsonResult = JSONSerializer.toJSON(officeTO).toString();
				
			} catch (CGBusinessException e) {
				// TODO Auto-generated catch block
				LOGGER.error("SalesProspectReportAction :: getBranchList() ::"+e);
				jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			}catch (CGSystemException e) {
				// TODO Auto-generated catch block
				LOGGER.error("SalesProspectReportAction :: getBranchList() ::"+e);
				String exception=getSystemExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} catch(Exception e){
				LOGGER.error("SalesProspectReportAction :: getBranchList() ::"+e);
				String exception=getGenericExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} finally {
				out.print(jsonResult);
				out.flush();
				out.close();
			}
		}
		
		
	
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	   
		LOGGER.debug("SalesProspectReportAction::getStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		try {
			    out = response.getWriter();
				String region =	request.getParameter("region");	
				Integer regionId = Integer.parseInt(region);
				List<CityTO> stationList=commonReportService.getCitiesByRegionId(regionId);
				
				if(!CGCollectionUtils.isEmpty(stationList)){
					
					jsonResult = JSONSerializer.toJSON(stationList).toString();
				}
	
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SalesProspectReportAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SalesProspectReportAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SalesProspectReportAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SalesProspectReportAction::getStations::END----->");
	
		
	}
	
	
	public void getSalesPersonList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ClientGainedReportAction::getSalesPersonList::START----->");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<CGBaseTO> salesPersonList = addSalesPersonList(request);
			if (!CGCollectionUtils.isEmpty(salesPersonList)) {
			jsonResult = JSONSerializer.toJSON(salesPersonList).toString();
			}
		} catch (IOException e) {
			LOGGER.error("ClientGainedReportAction :: getSalesPersonList() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());
		} catch (CGSystemException e) {
			LOGGER.error("ClientGainedReportAction :: getSalesPersonList() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ClientGainedReportAction :: getSalesPersonList() ::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
	
	
	public List<CGBaseTO> addSalesPersonList(HttpServletRequest request) throws CGBusinessException, CGSystemException {
		String officeIdStr = request.getParameter("branch");
		String cityIdStr = request.getParameter("station");
		String regionIdStr = request.getParameter("region");
		Integer officeId = 0;
		Integer cityId = 0;
		Integer regionId = null;
		if (!StringUtil.isStringEmpty(officeIdStr)) {
			officeId = Integer.parseInt(officeIdStr);
		}
		if (!StringUtil.isStringEmpty(cityIdStr)) {
			cityId = Integer.parseInt(cityIdStr);
		}
		if (!StringUtil.isStringEmpty(regionIdStr)) {
			regionId = Integer.parseInt(regionIdStr);
		}
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		return commonReportService.getSalesPersonsForProspects(officeId,cityId, regionId);
}
	
	
}
