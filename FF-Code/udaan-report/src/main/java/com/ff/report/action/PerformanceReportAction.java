/**
 * 
 */
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
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.LoadLotTO;
import com.ff.organization.OfficeTO;
import com.ff.report.CategoryReportAliasTO;
import com.ff.report.ReportTypeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;

/**
 * @author ssaraf
 * 
 */

public class PerformanceReportAction extends  ReportBaseAction{
	private CommonReportService commonReportService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutGoingHubReportAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.capgemini.lbs.framework.webaction.CGBaseAction#setServlet(org.apache
	 * .struts.action.ActionServlet)
	 */
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);

	}

	public ActionForward getMisrouteDestReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			addCommonParams(request);                
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getMisrouteDestReport ::"
					+ e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getMisrouteDestReport ::"
					+ e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getProductRtoReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductRtoReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductRtoReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}


	public ActionForward getOriginHitRatioReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getOriginHitRatioReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getOriginHitRatioReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPriorityTatFailureReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request);
			//addRegionList(request, CommonReportConstant.REGION_TO);
			//addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getPriorityTatFailureReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getPriorityTatFailureReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public ActionForward getProductStatusReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward getBrrDatewiseStatusReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward getBrrDetailReport(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) {
				commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
				List<ConsignmentTypeTO> cnTypes=null;
				List<CategoryReportAliasTO> category=null;
				List<LoadLotTO> load=null;
				List<ReportTypeTO> reportType=null;
				try {
					addCommonParams(request); 
					addRegionList(request, CommonReportConstant.REGION_TO);
					addProductList(request, CommonReportConstant.PRODUCT_TO);
					cnTypes = commonReportService.getConsignmentType();
					category=commonReportService.getCategory();
					load= commonReportService.getLoad();
					reportType=commonReportService.getReportType();
					request.setAttribute("reportTypeTOs", reportType);
				} catch (CGBusinessException e) {
					LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
							+ e);
					// need to plan an error page

				} catch (CGSystemException e) {
					LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
							+ e);
					// need to plan an error page
				}
				request.setAttribute(CommonReportConstant.LOAD, load);
				request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
				request.setAttribute(CommonReportConstant.CATEGORY, category);
				return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
			}
	
	
	public ActionForward getBrrSummaryReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ConsignmentTypeTO> cnTypes=null;
		List<CategoryReportAliasTO> category=null;
		List<LoadLotTO> load=null;
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			cnTypes = commonReportService.getConsignmentType();
			category=commonReportService.getCategory();
			load= commonReportService.getLoad();
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}
		request.setAttribute(CommonReportConstant.LOAD, load);
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		request.setAttribute(CommonReportConstant.CATEGORY, category);
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public ActionForward getHitRatioBranchwiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ConsignmentTypeTO> cnTypes=null;
		List<CategoryReportAliasTO> category=null;
		List<LoadLotTO> load=null;
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			cnTypes = commonReportService.getConsignmentType();
			category=commonReportService.getCategory();
			load= commonReportService.getLoad();
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		request.setAttribute(CommonReportConstant.CATEGORY, category);
		request.setAttribute(CommonReportConstant.LOAD, load);

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	public ActionForward getHitRatioProductwiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ConsignmentTypeTO> cnTypes=null;
		List<CategoryReportAliasTO> category=null;
		List<LoadLotTO> load=null;
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			cnTypes = commonReportService.getConsignmentType();
			category=commonReportService.getCategory();
			load= commonReportService.getLoad();
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		request.setAttribute(CommonReportConstant.CATEGORY, category);
		request.setAttribute(CommonReportConstant.LOAD, load);

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	public ActionForward getOnlinePendingDatewiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ConsignmentTypeTO> cnTypes=null;
		List<CategoryReportAliasTO> category=null;
		List<LoadLotTO> load=null;
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			cnTypes = commonReportService.getConsignmentType();
			category=commonReportService.getCategory();
			load= commonReportService.getLoad();
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		request.setAttribute(CommonReportConstant.CATEGORY, category);
		request.setAttribute(CommonReportConstant.LOAD, load);

		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	
	
	
	
	
	public ActionForward getHitRatioOriginwiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<ConsignmentTypeTO> cnTypes=null;
		try {
			addCommonParams(request); 
			addRegionList(request, CommonReportConstant.REGION_TO);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			cnTypes = commonReportService.getConsignmentType();
		} catch (CGBusinessException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getProductStatusReport ::"
					+ e);
			// need to plan an error page
		}
		request.setAttribute(CommonReportConstant.CONSG_TYPE, cnTypes);
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

	public void addRegionList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {

		List<RegionTO> regionTo = commonReportService.getRegions();
		request.setAttribute(attributeName, regionTo);
	}

	/**
	 * @param request
	 * @param attributeName
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void addProductList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		List<ProductTO> productTo = commonReportService.getProducts();
		request.setAttribute(attributeName, productTo);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("ConsignmentBookingReportAction::getStations::START----->");
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
			LOGGER.error("PerformanceReportAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PerformanceReportAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("PerformanceReportAction::getStations::END----->");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ConsignmentBookingReportAction::getBranchList::START----->");
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
			LOGGER.error("PerformanceReportAction :: getBranchList() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("PerformanceReportAction :: getBranchList() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("PerformanceReportAction :: getBranchList() ::" + e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}

}
