/**
 * 
 */
package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.business.RateRevisionDO;
import com.ff.geography.CityTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author shashsax
 * 
 */
public class RateRevisionAnalysisReportAction extends ReportBaseAction {

	private CommonReportService commonReportService;
		
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentBookingReportAction.class);

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
	}

	public ActionForward getRateRevisionAnalysisDetailsReport(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("RateRevisionAnalysisReportAction::getRateRevisionAnalysisDetailsReport::START::------->");
		try {
			//addRegionList(request, CommonReportConstant.REGION_TO);
			addCommonParams(request);
			addProductList(request, CommonReportConstant.PRODUCT_TO);
			addFuelPercent(request, CommonReportConstant.FUEL_TO);
			addSector(request, CommonReportConstant.SECTOR_TO);
		} catch (CGBusinessException cgBusinessException) {
			LOGGER.error("RateRevisionAnalysisReportAction::getRateRevisionAnalysisDetailsReport::"
					+ cgBusinessException);
		} catch (CGSystemException cgSystemException) {
			LOGGER.error("RateRevisionAnalysisReportAction::getRateRevisionAnalysisDetailsReport::"
					+ cgSystemException);
		}
		LOGGER.trace("RateRevisionAnalysisReportAction::getRateRevisionAnalysisDetailsReport::END::-------->");
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}

/*	public void getFuelPercent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RateRevisionAnalysisReportAction::getFuelPercent::START::------->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			List<StockStandardTypeTO> stockStandardTypeTO = globalUniversalService
					.getStandardTypesByTypeName("RATE REVISION ANALYSIS");
			jsonResult = JSONSerializer.toJSON(stockStandardTypeTO).toString();

		} catch (IOException e) {
		} catch (CGBusinessException e) {
		} catch (CGSystemException e) {
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateRevisionAnalysisReportAction::getFuelPercent::END::-------->");
	}*/

	public void getCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RateRevisionAnalysisReportAction::getCustomerList::START::------->");

		String jsonResult = null;
		PrintWriter out = null;
		HttpSession session = request.getSession(false);
		try {
			out = response.getWriter();
			String station = request.getParameter("station");
			List<String> station1 = Arrays.asList(station.split(", "));
			List<Integer> stationNum = new ArrayList<Integer>();
			for (String stationStrin : station1) {
				Integer station_int = Integer.parseInt(stationStrin);
				stationNum.add(station_int);

			}
			
			//String[] stationIdContent = station.split(",");
			//Integer[] stationID = new Integer[stationIdContent.length];
//			for(int i = 0; i < stationID.length; i++) {
//				stationID[i] = Integer.parseInt(stationIdContent[i]);
//			}
			
			
			//Integer stationID = Integer.parseInt(station);
			List<RateRevisionDO> customerTO = commonReportService.getCustomerByStation2(stationNum);
			if(!StringUtil.isEmptyColletion(customerTO)) {
				session.setAttribute("rateRevision_customerTO", customerTO);
			}
			jsonResult = JSONSerializer.toJSON(customerTO).toString();

		} catch (IOException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getCustomerList::"
					+ e);
		} catch (CGBusinessException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getCustomerList::"
					+ e);
		} catch (CGSystemException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getCustomerList::"
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateRevisionAnalysisReportAction::getCustomerList::END::-------->");
	}

	//getProductByCustomers
	public void getProductByCustomers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RateRevisionAnalysisReportAction::getProductByCustomers::START::------->");

		String jsonResult = null;
		PrintWriter out = null;
		HttpSession session = request.getSession(false);
		try {
			out = response.getWriter();
			String customers = request.getParameter("customerIds");
			String[] customerArray = customers.split(",");
			Integer[] customerIds = new Integer[customerArray.length];
			
			if(customerArray.length == 1 && customerArray[0].equals("0")) {
				List<RateRevisionDO> rateRevisionTOs = (List<RateRevisionDO>)session.getAttribute("rateRevision_customerTO");
				customerIds = new Integer[rateRevisionTOs.size()];
				int i = 0;
				for (RateRevisionDO customerTO : rateRevisionTOs) {
					customerIds[i] = customerTO.getCustomerId();
					i++;
				}
			} else {
				for(int i = 0; i < customerArray.length; i++) {
					customerIds[i] = Integer.parseInt(customerArray[i].trim());
				}
			}
			List<ProductTO> productTOs = commonReportService
					.getProductByCustomers(customerIds);
			jsonResult = JSONSerializer.toJSON(productTOs).toString();

		} catch (IOException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getProductByCustomers::"
					+ e);
		} catch (CGBusinessException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getProductByCustomers::"
					+ e);
		} catch (CGSystemException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getProductByCustomers::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateRevisionAnalysisReportAction::getProductByCustomers::END::-------->");
	}
	
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("RateRevisionAnalysisReportAction::getStations::START::------->");

		String jsonResult = null;
		PrintWriter out = null;
		List<CityTO> stationList = new ArrayList<CityTO>();
		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			Integer regionId = Integer.parseInt(region);
			stationList = commonReportService.getCitiesByRegionId(regionId);
			if (!CGCollectionUtils.isEmpty(stationList)) {
				jsonResult = JSONSerializer.toJSON(stationList).toString();
			}
		} catch (IOException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getStations::" + e);
		} catch (CGBusinessException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getStations::" + e);
		} catch (CGSystemException e) {
			LOGGER.error("RateRevisionAnalysisReportAction::getStations::" + e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("RateRevisionAnalysisReportAction::getStations::END::-------->");
	}
	
	@SuppressWarnings("unchecked")
	public void addFuelPercent(HttpServletRequest request, String fuelParam) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RateRevisionAnalysisReportAction::addFuelPercent::START::------->");
			
		
		HttpSession session=request.getSession(false);		
		List<StockStandardTypeTO> stockStandardTypeTO = new ArrayList<StockStandardTypeTO>();
		stockStandardTypeTO = (List<StockStandardTypeTO>)session.getAttribute(fuelParam);

		if(CGCollectionUtils.isEmpty(stockStandardTypeTO)) {
			stockStandardTypeTO = commonReportService
					.getStandardTypesByTypeName("RATE REVISION ANALYSIS");
			
			session.setAttribute(fuelParam, stockStandardTypeTO);
		}
		
			request.setAttribute(fuelParam, stockStandardTypeTO);
		
		LOGGER.trace("RateRevisionAnalysisReportAction::addFuelPercent::END::-------->");
		
		 
	}
	
	/**
	 * 
	 * @param request
	 * @param sectorParam
	 */
	@SuppressWarnings("unchecked")
	public void addSector(HttpServletRequest request, String sectorParam ) {
		LOGGER.trace("RateRevisionAnalysisReportAction::addSector::START::------->");
		
		HttpSession session=request.getSession(false);
		List<SectorTO> sectorTO = new ArrayList<SectorTO>();
		sectorTO = (List<SectorTO>)session.getAttribute(sectorParam);
		
		if(CGCollectionUtils.isEmpty(sectorTO)) {
			try {
				sectorTO = commonReportService.getSectors();
			} catch (CGSystemException | CGBusinessException e) {
				LOGGER.error("RateRevisionAnalysisReportAction::addSector::" + e);
			}
			
			session.setAttribute(sectorParam, sectorTO);
		}
		
			request.setAttribute(sectorParam, sectorTO);
		LOGGER.trace("RateRevisionAnalysisReportAction::addSector::END::-------->");
	}
	
}
