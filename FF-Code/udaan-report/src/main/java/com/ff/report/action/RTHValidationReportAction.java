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

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;



public class RTHValidationReportAction extends ReportBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(RTHValidationReportAction.class);
	
	/** The transfer service. */
	
	private CommonReportService commonReportService;
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}		

	
	public ActionForward getRthValidationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
	
			if (commonReportService != null) {
				List<RegionTO> regionTo=null;
				List<ProductTO> productTo=null;				
				try {	
					addCommonParams(request);				
					productTo= commonReportService.getProducts();//get all products			
				} catch (CGBusinessException e) {			
					LOGGER.error("RTHValidationReportAction :: getRthValidationReport() ::",e);
				} catch (CGSystemException e) {				
					LOGGER.error("RTHValidationReportAction :: getRthValidationReport() ::",e);
				}
				if (!StringUtil.isEmptyColletion(regionTo)) {
					request.setAttribute(CommonReportConstant.REGION_TO, regionTo);
					
				}
				if (!StringUtil.isEmptyColletion(productTo)) {				
					request.setAttribute(CommonReportConstant.PRODUCT_TO, productTo);
				}
				
			}
			return  mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	
		}
	
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
		}
		
		public void getCustomerList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){
			LOGGER.debug("ConsignmentBookingReportAction::getBranchList::START----->");
			String jsonResult =null;
			PrintWriter out = null;
			
			 try {
				out = response.getWriter();
				String region =	request.getParameter("cityID");	
				Integer cityID = Integer.parseInt(region);
				List<CustomerTO> officeTO =  commonReportService.getCustomersByOfficeId(cityID);
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
			} 
			finally{
				out.print(jsonResult);
				out.flush();
				out.close();
			}
			
		}
		
	
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	   
		LOGGER.debug("ConsignmentBookingReportAction::getStations::START----->");
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
}
