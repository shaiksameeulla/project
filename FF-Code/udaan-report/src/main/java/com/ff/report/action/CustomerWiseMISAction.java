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



public class CustomerWiseMISAction extends ReportBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(StockTransferReportAction.class);
	
	/** The transfer service. */
	
	private CommonReportService commonReportService;
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}		

	
	
	public ActionForward getCustomerWiseMISReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	

			if (commonReportService != null) {
				List<RegionTO> regionTo=null;
				List<ProductTO> productTo=null;
				List<ItemTypeTO>  itemtypeTo=null;
				List<EmployeeTO>  salesPersonTO=null;
				List<RateIndustryTypeTO>  businessTypeTO=null;
				List<SectorTO>  sectorTO=null;
				List monthList = new ArrayList();
				try {		
					
					addCommonParams(request);
					productTo= commonReportService.getProducts();//get all products			
					monthList=commonReportService.getMonthList();
				} catch (CGBusinessException e) {			
					LOGGER.error("CustomerWiseMISAction :: getCustomerWiseMISReport() ::",e);
				} catch (CGSystemException e) {				
					LOGGER.error("CustomerWiseMISAction :: getCustomerWiseMISReport() ::",e);
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
	
	
	public void getCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("CustomerWiseMISAction::getCustomerList::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		 try {
			out = response.getWriter();
			String region =	request.getParameter("cityID");	
			Integer cityID = Integer.parseInt(region);
			List<CustomerTO> officeTO =  commonReportService.getCustomersByOfficeId(cityID);
			jsonResult = JSONSerializer.toJSON(officeTO).toString();
			
		} catch (IOException e) {
			LOGGER.error("CustomerWiseMISAction :: getCustomerList() ::",e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, e.toString());
			
		} catch (CGSystemException e) {
			LOGGER.error("CustomerWiseMISAction :: getCustomerList() ::",e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (CGBusinessException e) {
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
			LOGGER.error("CustomerWiseMISAction :: getCustomerList() ::",e);
		} catch(Exception e){
			
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
		} catch(Exception e){
			
		}
		finally{
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}
	

}
