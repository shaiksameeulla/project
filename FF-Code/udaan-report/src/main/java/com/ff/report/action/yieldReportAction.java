package com.ff.report.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.report.action.ReportBaseAction.UserType;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateIndustryTypeTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ibm.icu.util.StringTokenizer;



public class yieldReportAction extends ReportBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(yieldReportAction.class);
	
	/** The transfer service. */
	
	private CommonReportService commonReportService;
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}		

	
	
	public ActionForward getYieldReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	

			if (commonReportService != null) {
				
				List<ProductTO> productTo=null;				
				List<EmployeeTO>  salesPersonTO=null;
				List<RateIndustryTypeTO>  businessTypeTO=null;
				List<SectorTO>  sectorTO=null;
				List monthList = null;
				try {		
					
					addCommonParams(request);
					productTo= commonReportService.getProducts();//get all products			
					monthList=commonReportService.getMonthList();
				} catch (CGBusinessException e) {			
					LOGGER.error("yieldReportAction :: getYieldReport() ::",e);
				} catch (CGSystemException e) {				
					LOGGER.error("yieldReportAction :: getYieldReport() ::",e);
				}
				
				if (!StringUtil.isEmptyColletion(productTo)) {				
					request.setAttribute(CommonReportConstant.PRODUCT_TO, productTo);
				}
				if (!StringUtil.isEmptyColletion(salesPersonTO)) {				
					request.setAttribute(CommonReportConstant.SALES_PERSON_TO, salesPersonTO);
				}
				if (!StringUtil.isEmptyColletion(businessTypeTO)) {				
					request.setAttribute(CommonReportConstant.BUSINESS_TYPE_TO, businessTypeTO);
				}
				if (!StringUtil.isEmptyColletion(sectorTO)) {				
					request.setAttribute(CommonReportConstant.SECTOR_TO, sectorTO);
				}				
				if (!StringUtil.isEmptyColletion(monthList)) {				
					request.setAttribute(CommonReportConstant.MONTHS_LIST, monthList);
				}
				
				
			}
			return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	
		}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("yieldReportAction::getBranchList::START----->");
		String jsonResult = null;
		PrintWriter out = null;
		Integer userId = getUserId(request);
		Integer officeId = getOfficeId(request);
		UserType userType = getUserType(request);		
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();

		try {
			out = response.getWriter();
			String city = request.getParameter("cityID");	
			String[] cityIdContent = city.split(",");
			Integer[] cityId = new Integer[cityIdContent.length];
			for(int i = 0; i < cityIdContent.length; i++) {
				cityId[i] = Integer.parseInt(cityIdContent[i]);
			}
			
			switch(userType){
			case BO:
				officeTOList = commonReportService.getOfficeForMultipleCitiesForBOUser(cityId, userId);
				break;
			case HO : 
				officeTOList = commonReportService.getOfficeForMultipleCitiesUserType(UserType.HO.toString(), userId, officeId, cityId);
				break;
			case RO : 
				//officeTOList = commonReportService.getOfficeForMultipleCitiesUserType(UserType.RO.toString(), userId, officeId, cityId);
				officeTOList = commonReportService.getOfficecByMultipleCityIDAndUserIDForReport(cityId);
				break;
			default :
				officeTOList = commonReportService.getOfficecByMultipleCityIDAndUserIDForReport(cityId);
			}
			 
			jsonResult = JSONSerializer.toJSON(officeTOList).toString();

		} catch (IOException e) {
			LOGGER.error("yieldReportAction :: getBranchList() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("yieldReportAction :: getBranchList() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("yieldReportAction :: getBranchList() ::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CustomerSlabWiseRevenueAction::getStations::START----->");
		
		String jsonResult = null;
		PrintWriter out = null;
		Integer userId = getUserId(request);
		Integer officeId = getOfficeId(request);
		UserType userType = getUserType(request);
		List<CityTO> stationList = new ArrayList<CityTO>();

		try {
			out = response.getWriter();			
			
			String region = request.getParameter("region");	
			String[] regionIdContent = region.split(",");
			Integer[] regionId = new Integer[regionIdContent.length];
			for(int i = 0; i < regionIdContent.length; i++) {
				regionId[i] = Integer.parseInt(regionIdContent[i]);
			}			
				
			
			switch(userType){
			case BO:
				stationList = commonReportService.getCityByMultipleRegionForBranchUser(userId, regionId);
				break;
			case HO: 
				stationList = commonReportService.getCitiesByMultipleRegionForHubUser(userId, officeId, regionId);
				break;
			case RO:
				//stationList = commonReportService.getCitiesByMultipleRegionForRhoUser(userId, officeId, regionId);
				stationList = commonReportService.getCitiesByMultipleRegionId(regionId);
				break;
			default :
				stationList = commonReportService.getCitiesByMultipleRegionId(regionId);
				break;
			}

			if (!CGCollectionUtils.isEmpty(stationList)) {
				jsonResult = JSONSerializer.toJSON(stationList).toString();
			}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CustomerSlabWiseRevenueAction :: getStations() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("CustomerSlabWiseRevenueAction :: getStations() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CustomerSlabWiseRevenueAction :: getStations() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CustomerSlabWiseRevenueAction::getStations::END----->");
	}

	
		/*public void getBranchList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			LOGGER.debug("RateRevisionReportAction::getBranchList::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			
			 try {
				out = response.getWriter();
				String city =	request.getParameter("cityID");					
				List<OfficeTO> officeTO =  commonReportService.getOfficesByCityIds(city);
				jsonResult = JSONSerializer.toJSON(officeTO).toString();
				
			} catch (CGBusinessException e) {
				// TODO Auto-generated catch block
				LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
				jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			}catch (CGSystemException e) {
				// TODO Auto-generated catch block
				LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
				String exception=getSystemExceptionMessage(request,e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			} catch(Exception e){
				LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
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
	   
		LOGGER.debug("RateRevisionReportAction::getStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		try {
			    out = response.getWriter();
				String region =	request.getParameter("region");				
				List<CityTO> stationList=commonReportService.getCitiesByRegionIds(region);
				
				if(!CGCollectionUtils.isEmpty(stationList)){
					
					jsonResult = JSONSerializer.toJSON(stationList).toString();
				}
	
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("RateRevisionReportAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("RateRevisionReportAction::getStations::END----->");
	
		
	}*/
	public void getCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("RateRevisionReportAction::getBranchList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		 try {
			out = response.getWriter();
			String region =	request.getParameter("cityID");	
			Integer cityID = Integer.parseInt(region);
			List<CustomerTO> officeTO =  commonReportService.getCustomersByOfficeId(cityID);
			jsonResult = JSONSerializer.toJSON(officeTO).toString();
			
		} catch (IOException e) {
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, e.toString());
			
		} catch (CGSystemException e) {
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (CGBusinessException e) {
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
		} 
		finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}
	
	public void getProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("RateRevisionReportAction::getProductList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		 try {
			out = response.getWriter();
			String customerID =	request.getParameter("customerID");			
			List<RateProductCategoryTO> productTO =  commonReportService.getProductsByCustomer(Integer.parseInt(customerID));
			jsonResult = JSONSerializer.toJSON(productTO).toString();
			
		} catch (IOException e) {
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, e.toString());
			
		} catch (CGSystemException e) {
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (CGBusinessException e) {
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			LOGGER.error("RateRevisionReportAction :: getCustomerList() ::"+e);
		}
		finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}
	

}
