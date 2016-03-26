package com.ff.web.locationsearch.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.to.utilities.LocationSearchTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.web.locationsearch.constants.LocationSearchConstants;

public class LocationSearchAction extends AbstractRateAction {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LocationSearchAction.class);
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/**
	 * 
	 * @return
	 */
	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::START------------>:::::::");
		if (StringUtil.isNull(serviceOfferingCommonService)) {
			serviceOfferingCommonService = (ServiceOfferingCommonService) getBean("serviceOfferingCommonService");
		}
		LOGGER.debug("InvoiceRunSheetPrintingAction::getInvoiceRunSheetPrintingService::END------------>:::::::");
		return serviceOfferingCommonService;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward prepareSearchPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.debug("LocationSearchAction::prepareSearchPage::-------------->START");
		List<String> locationList = null;
		HttpSession session = (HttpSession) request.getSession(false);
		try {

			serviceOfferingCommonService = getServiceOfferingCommonService();
			locationList = (List<String>) session.getAttribute("locAddress");

			if (CGCollectionUtils.isEmpty(locationList)) {
				locationList = serviceOfferingCommonService
						.getAllServicingSearchLocation();
			}

			session.setAttribute("locationList", locationList);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("SearchByPincodeAction::prepareSearchPage::ERROR::", e);
		}

		LOGGER.debug("LocationSearchAction::prepareSearchPage::-------------->END");
		return mapping.findForward(LocationSearchConstants.LOCATION_SEARCH);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getLocationMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("LocationSearchAction::getLocationMapping::START----->");
		String locationAddrs = request.getParameter("location");
		String cityName = request.getParameter("cityName");
		serviceOfferingCommonService = (ServiceOfferingCommonService) getBean(LocationSearchConstants.LOCATION_MAPPING_SERVICE);
		List<LocationSearchTO> locationSecTo = null;
		OfficeTO loggedInOffice = null;
		String jsonResult = null;
		PrintWriter writer = response.getWriter();
		HttpSession session = (HttpSession) request.getSession(false);
		try {
			loggedInOffice = getLoggedInOffice(request);

			if (!StringUtils.isEmpty(locationAddrs)) {
				locationSecTo = serviceOfferingCommonService
						.getlocationDetails(locationAddrs, cityName, loggedInOffice);
			}

			if (!StringUtil.isNull(locationSecTo)) {
				jsonResult = JSONSerializer.toJSON(locationSecTo).toString();
			}
			session.setAttribute("locationSecTo", locationSecTo);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("LocationSearchAction::getLocationMapping::ERROR::", e);
		} finally {
			writer.print(jsonResult);
			writer.flush();
			writer.close();
		}
		LOGGER.debug("LocationSearchAction::getLocationMapping::END----->");

	}

	/**
	 * Will return current logged in office
	 * 
	 * @param request
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public OfficeTO getLoggedInOffice(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("BookingAction::getLoggedInOffice::Start------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		LOGGER.debug("BookingAction::getLoggedInOffice::End------------>:::::::");
		return userInfoTO.getOfficeTo();
	}
}
