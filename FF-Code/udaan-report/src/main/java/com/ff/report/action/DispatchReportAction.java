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
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.BookingReportTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.form.BookingReportForm;


public class DispatchReportAction extends ReportBaseAction {
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DispatchReportAction.class);

	public ActionForward getBrBkngBranchReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request);	
		} catch (CGBusinessException e) {
			LOGGER.error("DispatchReportAction :: getBrBkngBranchReport ::" + e);
		} catch (CGSystemException e) {
			LOGGER.error("DispatchReportAction :: getBrBkngBranchReport ::" + e);
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public ActionForward getDiscrepencyCounterReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request);	
		} catch (CGBusinessException e) {
			LOGGER.error("DispatchReportAction :: getDiscrepencyCounterReport ::"+ e);
		} catch (CGSystemException e) {
			LOGGER.error("DispatchReportAction :: getDiscrepencyCounterReport ::"+ e);
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	public ActionForward getloadConfirmationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<RegionTO> regionTo=null;
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		try {
			regionTo=commonReportService.getRegions();
			addCommonParams(request);	
			BookingReportForm reportForm = (BookingReportForm)form;
			BookingReportTO to = (BookingReportTO) reportForm.getTo();
			
			// Populate Transport Mode
			List<LabelValueBean> transportModeList = commonReportService.getAllTransportModeList();
			to.setTransportModeList(transportModeList);
			
		} catch (CGBusinessException e) {
			LOGGER.error("DispatchReportAction :: getloadConfirmationReport ::" + e);
		} catch (CGSystemException e) {
			LOGGER.error("DispatchReportAction :: getloadConfirmationReport ::" + e);
		}
		request.setAttribute("destRegionList", regionTo);
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	public ActionForward getTptLoadHandledReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			addCommonParams(request);	
		} catch (CGBusinessException e) {
			LOGGER.error("DispatchReportAction :: getTptLoadHandledReport ::"+ e);
		} catch (CGSystemException e) {
			LOGGER.error("DispatchReportAction :: getTptLoadHandledReport ::"+ e);
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	/**
	 * Returns Hubs for given City
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getHubOffices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("DispatchReportAction::getHubOffices::START");
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		String jsonResult =null;
		PrintWriter out = null;
		try {
			    out = response.getWriter();
				String station =	request.getParameter("station");	
				Integer stationId = Integer.parseInt(station);
				if(getUserType(request)!=UserType.BO){
					List<OfficeTO> officeList =commonReportService.getAllHubsByCityID(stationId);
					if(!CGCollectionUtils.isEmpty(officeList)){
						jsonResult = JSONSerializer.toJSON(officeList).toString();
					}
				}
		} catch (CGBusinessException businessException) {
			LOGGER.error("DispatchReportAction :: getHubOffices() ::"+ businessException);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,businessException));
		}catch (CGSystemException systemException) {
			LOGGER.error("DispatchReportAction :: getHubOffices() ::"+ systemException);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,systemException));
		} catch(Exception exception){
			LOGGER.error("DispatchReportAction :: getHubOffices() ::"+ exception);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,exception));
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DispatchReportAction::getStations::END");
	}
	
	public void addRegionList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {

		List<RegionTO> regionTo = commonReportService.getRegions();
		request.setAttribute(attributeName, regionTo);
	}

	
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("DispatchReportAction::getStations::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			Integer regionId = Integer.parseInt(region);
			List<CityTO> stationList = commonReportService
					.getCitiesByRegionId(regionId);

			if (!CGCollectionUtils.isEmpty(stationList)) {

				jsonResult = JSONSerializer.toJSON(stationList).toString();
			}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("DispatchReportAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("DispatchReportAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("DispatchReportAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DispatchReportAction::getStations::END----->");

	}

	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("DispatchReportAction::getBranchList::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String region = request.getParameter("cityID");
			Integer cityID = Integer.parseInt(region);
			List<OfficeTO> officeTO = commonReportService
					.getOfficesByCityIdForReport(cityID);
			jsonResult = JSONSerializer.toJSON(officeTO).toString();

		} catch (IOException e) {
			LOGGER.error("DispatchReportAction :: getBranchList() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("DispatchReportAction :: getBranchList() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("DispatchReportAction :: getBranchList() ::" + e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
	
	/**
	 * Gets the service by type list by transport mode id.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the service by type list by transport mode id
	 */
	public void getServiceByTypeListByTransportModeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("DispatchReportAction::getServiceByTypeListByTransportModeId::START------------>:::::::");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			out = response.getWriter();
			Integer transportModeId = Integer.valueOf(request
					.getParameter("transportModeId"));
			List<LabelValueBean> serviceByTypeList = commonReportService
					.getServiceByTypeListByTransportModeId(transportModeId);
			jsonResult = JSONSerializer.toJSON(serviceByTypeList).toString();
			jsonResult = jsonResult.trim();

		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
			LOGGER.error(
					"Exception happened in getServiceByTypeListByTransportModeId of DispatchReportAction....",
					e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error(
					"Exception happened in getServiceByTypeListByTransportModeId of DispatchReportAction....",
					e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
			LOGGER.error(
					"Exception happened in getServiceByTypeListByTransportModeId of DispatchReportAction....",
					e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DispatchReportAction::getServiceByTypeListByTransportModeId::END------------>:::::::");
	}
	
}
