package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.ff.business.ConsignmentCustomerTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.stockmanagement.masters.ItemTypeTO;

public class StockUsedAndUnusedReportAction extends ReportBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockUsedAndUnusedReportAction.class);

	/** The transfer service. */

	private CommonReportService commonReportService;

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);

	}

	public ActionForward getStockUsedAndUnusedReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (commonReportService != null) {
			List<ItemTypeTO> itemtypeTo = null;
			try {
				addCommonParams(request);
				itemtypeTo = commonReportService.getItemTypeListForUsed();
			} catch (CGBusinessException e) {
				LOGGER.error(
						"StockUsedAndUnusedReportAction :: getStockUsedAndUnusedReport() ::",
						e);
			} catch (CGSystemException e) {
				LOGGER.error(
						"StockUsedAndUnusedReportAction :: getStockUsedAndUnusedReport() ::",
						e);
			}

			if (!StringUtil.isEmptyColletion(itemtypeTo)) {
				request.setAttribute(CommonReportConstant.PRODUCT_TO,
						itemtypeTo);
			}

		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);

	}

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

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"
					+ e);
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
	}

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
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ConsignmentBookingReportAction :: getStations() ::"
					+ e);
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
		LOGGER.debug("ConsignmentBookingReportAction::getStations::END----->");

	}

	public void getCustomerList1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ConsignmentBookingReportAction::getCustomerList::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String office = request.getParameter("officeId");
			String cityIds = request.getParameter("cityId");
			String[] officeIdContent = office.split(",");

			if (StringUtil.isEmpty(officeIdContent)) {
				officeIdContent = new String[] { office };
			}
			Integer[] officeIds = new Integer[officeIdContent.length];

			for (int i = 0; i < officeIdContent.length; i++) {
				officeIds[i] = Integer.parseInt(officeIdContent[i].trim());
			}

			String[] cityIdContent = cityIds.split(", ");

			if (StringUtil.isEmpty(cityIdContent)) {
				cityIdContent = new String[] { cityIds };
			}
			Integer[] cityId = new Integer[officeIdContent.length];

			for (int i = 0; i < cityIdContent.length; i++) {
				cityId[i] = Integer.parseInt(cityIdContent[i].trim());
			}
			// Integer officeId = Integer.parseInt(office);
			// List<CustomerTO> officeTO =
			// commonReportService.getCustomersByOfficeId(cityID);
			// List<CustomerTO> custList = new ArrayList<CustomerTO>();
			// for (Integer officeId : officeIds) {
			// List<CustomerTO> tempCustList =
			// commonReportService.getCustomersByOfficeId(officeId);
			// if (!CGCollectionUtils.isEmpty(tempCustList)) {
			// if (custList.size() == 0) {
			// custList = tempCustList;
			// } else {
			// custList.addAll(tempCustList);
			// }
			// }
			// List<ConsignmentCustomerTO> custList = new
			// ArrayList<ConsignmentCustomerTO>();
			List<ConsignmentCustomerTO> custList = new ArrayList<ConsignmentCustomerTO>();
			for (Integer officeId : officeIds) {
				List<ConsignmentCustomerTO> tempCustList = commonReportService
						.getCustomersByContractBranchesForConsignmentDetails(
								officeIds, cityId);
				if (!CGCollectionUtils.isEmpty(tempCustList)) {
					if (custList.size() == 0) {
						custList = tempCustList;
					} else {
						custList.addAll(tempCustList);
					}
				}

			}

			// }
			// List<CustomerTO> custList =
			// commonReportService.getCustomersByOfficeIdForStockReport(officeId,offTO.getOfficeTypeTO().getOffcTypeCode());
			jsonResult = JSONSerializer.toJSON(custList).toString();

		} catch (IOException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getCustomerList() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ConsignmentBookingReportAction :: getCustomerList() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
	
	/**
	 * To get customer list for stock report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getCustomerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("StockUsedAndUnusedReportAction::getCustomerList::START----->");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String office = request.getParameter("officeId");
			String[] officeIdContent = office.split(",");

			if (StringUtil.isEmpty(officeIdContent)) {
				officeIdContent = new String[] { office };
			}
			Integer[] officeIds = new Integer[officeIdContent.length];

			for (int i = 0; i < officeIdContent.length; i++) {
				officeIds[i] = Integer.parseInt(officeIdContent[i].trim());
			}
			commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<CustomerTO> customerTOList = null;
			for (Integer offId : officeIds) {
				List<CustomerTO> custTOList = commonReportService
						.getAllCustomerByLoggedInOffice(offId,
								getOfficeTypeByOfficeId(offId));
				if (!CGCollectionUtils.isEmpty(custTOList)) {
					if (!CGCollectionUtils.isEmpty(customerTOList)) {
						customerTOList.addAll(custTOList);
					} else {
						customerTOList = custTOList;
					}
				}
			}
			if (!CGCollectionUtils.isEmpty(customerTOList)) {
				Collections.sort(customerTOList, new Comparator<CustomerTO>() {
					@Override
					public int compare(CustomerTO customerTO1, CustomerTO customerTO2) {
						String str1 = customerTO1.getBusinessName();
						String str2 = customerTO2.getBusinessName();
						return str1.compareTo(str2);
					}
				});
			}
			jsonResult = JSONSerializer.toJSON(customerTOList).toString();
		} catch (Exception e) {
			LOGGER.error(
					"StockUsedAndUnusedReportAction:: getCustomerList ::Exception",
					e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("StockUsedAndUnusedReportAction::getCustomerList::END----->");
	}
	
	private String getOfficeTypeByOfficeId(Integer offId)
			throws CGBusinessException, CGSystemException {
		OfficeTO offTO = commonReportService.getOfficeDetailsByOfficeId(offId);
		return offTO.getOfficeTypeTO().getOffcTypeCode();
	}
	

	
}
