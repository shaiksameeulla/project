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
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;

/**
 * @author ssaraf
 *
 */
public class ClientWiseReportAction extends ReportBaseAction{ 
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ClientWiseReportAction.class);

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
	}

	public ActionForward getClientWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			addCommonParams(request);  
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("ClientWiseReportAction :: getClientWiseReport ::" + e);
			// need to plan an error page
			
			
		} catch (CGSystemException e) {
			LOGGER.error("ClientWiseReportAction :: getClientWiseReport ::" + e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	public ActionForward getClientWiseSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			addCommonParams(request);                
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("ClientWiseReportAction :: getClientWiseSummaryReport ::" + e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("ClientWiseReportAction :: getClientWiseSummaryReport ::" + e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public void addRegionList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {

		List<RegionTO> regionTo = commonReportService.getRegions();
		request.setAttribute(attributeName, regionTo);
	}

	public void addProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		List<ProductTO> productTo = commonReportService.getProducts();
		request.setAttribute(attributeName, productTo);

	}

	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("ClientWiseReportAction::getStations::START----->");
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
			LOGGER.error("ClientWiseReportAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ClientWiseReportAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ClientWiseReportAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ClientWiseReportAction::getStations::END----->");

	}

	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ClientWiseReportAction::getBranchList::START----->");
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
			LOGGER.error("ClientWiseReportAction :: getBranchList() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ClientWiseReportAction :: getBranchList() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ClientWiseReportAction :: getBranchList() ::" + e);
		}  finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
}
