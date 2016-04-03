package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.wrapper.ClientGainedWrapperTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;

/**
 * @author
 * 
 */
public class ClientGainedReportAction extends ReportBaseAction {
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ClientWiseReportAction.class);

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
	}

	public ActionForward getClientGainedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/* addRegionList(request, CommonReportConstant.REGION_TO); */
			addCommonParams(request);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			// request.setAttribute( CommonReportConstant.SALES_PERSON_TO,
			// addSalesPersonList(request));

		} catch (CGBusinessException e) {
			LOGGER.error("ClientGainedReportAction :: getClientGainedReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("ClientGainedReportAction :: getClientGainedReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public ActionForward getBranchProjectionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("ClientGainedReportAction :: getClientGainedReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("ClientGainedReportAction :: getClientGainedReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public ActionForward getTargetVsActualSalesReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);
		} catch (CGBusinessException e) {
			LOGGER.error("ClientGainedReportAction :: getTargetVsActualSalesReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("ClientGainedReportAction :: getTargetVsActualSalesReport ::"
					+ e);
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
		List<RateProductCategoryTO> rateProductTo = commonReportService
				.getRateProdcuts();
		request.setAttribute("rateProductTo", rateProductTo);
	}

	/**
	 * @param request
	 * @return employeeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CGBaseTO> addSalesPersonList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
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
		return commonReportService.getSalesPersonsForClientGained(officeId,
				cityId, regionId);
	}

	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("ClientGainedReportAction::getStations::START----->");
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
			LOGGER.error("ClientGainedReportAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ClientGainedReportAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ClientGainedReportAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ClientGainedReportAction::getStations::END----->");

	}

	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ClientGainedReportAction::getBranchList::START----->");
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
			LOGGER.error("ClientGainedReportAction :: getBranchList() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ClientGainedReportAction :: getBranchList() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ClientGainedReportAction :: getBranchList() ::" + e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

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
}
