package com.ff.report.action;


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
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;



public class ValidationReportAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(ValidationReportAction.class);
	
	/** The transfer service. */
	
	private CommonReportService commonReportService;
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		
	}		

	
	public ActionForward getValidationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
	
			if (commonReportService != null) {
				List<RegionTO> regionTo=null;				
				try {				
					regionTo = commonReportService.getRegions();					
				} catch (CGBusinessException e) {			
					LOGGER.error("ValidationReportAction :: getValidationReport() ::",e);
				} catch (CGSystemException e) {				
					LOGGER.error("ValidationReportAction :: getValidationReport() ::",e);
				}
				if (!StringUtil.isEmptyColletion(regionTo)) {
					request.setAttribute(CommonReportConstant.REGION_TO, regionTo);
					
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
