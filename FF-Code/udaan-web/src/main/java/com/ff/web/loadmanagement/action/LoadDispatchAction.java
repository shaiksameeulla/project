package com.ff.web.loadmanagement.action;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.LoadMovementVendorTO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.routeserviced.RouteTO;
import com.ff.routeserviced.ServiceByTypeTO;
import com.ff.routeserviced.ServicedByTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.routeserviced.TripTO;
import com.ff.to.stockmanagement.StockIssueValidationTO;
import com.ff.transport.TransportModeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.form.LoadManagementForm;
import com.ff.web.loadmanagement.service.LoadDispatchService;

/**
 * The Class LoadDispatchAction.
 * 
 * @author narmdr
 */
public class LoadDispatchAction extends LoadManagementAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadDispatchAction.class);

	/** The load dispatch service. */
	private transient LoadDispatchService loadDispatchService;

	/**
	 * View load dispatch.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward and Forwarding to XXX page
	 */
	public ActionForward viewLoadDispatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::viewLoadDispatch::START------------>:::::::");
		LoadMovementTO loadMovementTO = new LoadMovementTO();
		try {
			getDefaultUIValues(request, loadMovementTO);
			((LoadManagementForm) form).setTo(loadMovementTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewLoadDispatch of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewLoadDispatch of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			//prepareActionMessage(request, LoadManagementConstants.ERROR_IN_LOADING_PAGE);
			LOGGER.error("Exception happened in viewLoadDispatch of LoadDispatchAction..."
					, e);
		}
		LOGGER.debug("LoadDispatchAction::viewLoadDispatch::END------------>:::::::");

		return mapping
				.findForward(LoadManagementConstants.URL_VIEW_LOAD_DISPATCH);
	}

	/**
	 * Gets the default ui values.
	 * 
	 * @param request
	 *            the request
	 * @param loadMovementTO
	 *            the load movement to
	 * @return the default ui values
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private void getDefaultUIValues(HttpServletRequest request,
			LoadMovementTO loadMovementTO) throws CGBusinessException,
			CGSystemException {
		loadDispatchService = (LoadDispatchService) getBean(SpringConstants.LOAD_DISPATCH_SERVICE);
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();

		OfficeTO regionalOfficeTO = loadDispatchService
				.getOfficeByOfficeId(loggedInofficeTO.getReportingRHO());
		List<LabelValueBean> transportModeList = getAllTransportModeList();
		List<LabelValueBean> destOfficeTypeList = loadDispatchService
				.getOfficeTypeListForDispatch();
		List<LabelValueBean> vehicleNoList = loadDispatchService
				.getVehicleNoListByOfficeId(regionalOfficeTO.getOfficeId());

		loadMovementTO.setDispatchDateTime(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat());
		loadMovementTO.setOriginOffice(loggedInofficeTO.getOfficeCode()
				+ CommonConstants.HYPHEN + loggedInofficeTO.getOfficeName());
		loadMovementTO.setOriginOfficeId(loggedInofficeTO.getOfficeId());
		loadMovementTO.setOriginCityId(loggedInofficeTO.getCityId());
		if (loggedInofficeTO.getOfficeTypeTO() != null) {
			loadMovementTO.setOriginOfficeType(loggedInofficeTO
					.getOfficeTypeTO().getOffcTypeCode());
		}

		loadMovementTO.setRegionalOfficeId(regionalOfficeTO.getOfficeId());
		loadMovementTO.setRegionalOffice(regionalOfficeTO.getOfficeCode()
				+ CommonConstants.HYPHEN + regionalOfficeTO.getOfficeName());

		loadMovementTO.setTransportModeList(transportModeList);
		loadMovementTO.setDestOfficeTypeList(destOfficeTypeList);
		loadMovementTO.setVehicleNoList(vehicleNoList);

		loadMovementTO.setProcessCode(CommonConstants.PROCESS_DISPATCH);
	}

	/**
	 * get All the destination Offices By officeTypeId, origin cityId and not
	 * origin office. If officeType is branch then get all the offices of logged
	 * in Office city. If officeType is hub then get all the offices of India.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the destination offices
	 */
	public void getDestinationOffices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::getOfficesByOfficeTypeId::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer officeTypeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.OFFICE_TYPE_ID));
			Integer originOfficeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.ORIGIN_OFFICE_ID));
			String originCityIdStr = request
					.getParameter(LoadManagementConstants.ORIGIN_CITY_ID);
			Integer originCityId = null;

			if (StringUtils.isNotBlank(originCityIdStr)) {
				originCityId = Integer.valueOf(originCityIdStr);
			}

			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(originOfficeId);
			officeTO.setCityId(originCityId);
			OfficeTypeTO officeTypeTO = new OfficeTypeTO();
			officeTypeTO.setOffcTypeId(officeTypeId);
			officeTO.setOfficeTypeTO(officeTypeTO);
			officeTO.setIsExcludeOfficeId(true);

			List<OfficeTO> officeTOList = loadDispatchService
					.getDestinationOffices(officeTO);
			if(!CGCollectionUtils.isEmpty(officeTOList)){
				Collections.sort(officeTOList, new Comparator<OfficeTO>() {
					@Override
					public int compare(OfficeTO officeTO1, OfficeTO officeTO2) {
						int result=0;
						if(officeTO1!=null && officeTO2!=null && !StringUtil.isStringEmpty(officeTO1.getOfficeCode()) && !StringUtil.isStringEmpty(officeTO2.getOfficeCode())){
							result= officeTO1.getOfficeCode().compareTo(officeTO2.getOfficeCode());
						}
						return result;
					}
				});
			}
			jsonResult = JSONSerializer.toJSON(officeTOList)
					.toString();

		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getOfficesByOfficeTypeId of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getOfficesByOfficeTypeId of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getOfficesByOfficeTypeId of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getOfficesByOfficeTypeId::END------------>:::::::");
	}

	/**
	 * Gets the route by origin city and dest city.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the route by origin city and dest city
	 */
	public void getRouteByOriginCityAndDestCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::getRouteByOriginCityAndDestCity::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer originCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.ORIGIN_CITY_ID));
			Integer destCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.DEST_CITY_ID));
			Integer routeId = loadDispatchService
					.getRouteIdByOriginCityIdAndDestCityId(originCityId,
							destCityId);
			if (!StringUtil.isEmptyInteger(routeId)) {
				jsonResult = routeId.toString();
			}
			//out.print(routeId);
		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getRouteByOriginCityAndDestCity of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getRouteByOriginCityAndDestCity of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getRouteByOriginCityAndDestCity of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getRouteByOriginCityAndDestCity::END------------>:::::::");
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
		LOGGER.debug("LoadDispatchAction::getServiceByTypeListByTransportModeId::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer transportModeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.TRANSPORT_MODE_ID));
			List<LabelValueBean> serviceByTypeList = loadDispatchService
					.getServiceByTypeListByTransportModeId(transportModeId);
			jsonResult = JSONSerializer.toJSON(serviceByTypeList).toString();
			jsonResult = jsonResult.trim();

		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getServiceByTypeListByTransportModeId of LoadDispatchAction...."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getServiceByTypeListByTransportModeId of LoadDispatchAction...."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getServiceByTypeListByTransportModeId of LoadDispatchAction...."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getServiceByTypeListByTransportModeId::END------------>:::::::");
	}

	/**
	 * Gets the trip serviced by to list by route id, transport mode id, service
	 * by type id.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the trip serviced by to list by route id transport mode id
	 *         service by type id
	 */
	public void getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String routeIdStr = request
					.getParameter(LoadManagementConstants.ROUTE_ID);
			Integer routeId = null;
			if (StringUtils.isNotBlank(routeIdStr)) {
				routeId = Integer.valueOf(routeIdStr);
			}

			Integer transportModeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.TRANSPORT_MODE_ID));
			Integer serviceByTypeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.SERVICE_BY_TYPE_ID));

			List<TripServicedByTO> tripServicedByTOList = loadDispatchService
					.getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
							routeId, transportModeId, serviceByTypeId);
			jsonResult = JSONSerializer.toJSON(tripServicedByTOList).toString();
			jsonResult = jsonResult.trim();

		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId::END------------>:::::::");
	}
	
	/**
	 * Gets the load movement to by gate pass number.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the load movement to by gate pass number
	 */
	public void getLoadMovementTOByGatePassNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::getLoadMovementTOByGatePassNumber::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String gatePassNumber = request
					.getParameter(LoadManagementConstants.GATE_PASS_NUMBER);

			LoadMovementTO loadMovementTO = loadDispatchService
					.getLoadMovementTOByGatePassNumber(gatePassNumber);
			jsonResult = JSONSerializer.toJSON(loadMovementTO).toString();
			jsonResult = jsonResult.trim();

		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getLoadMovementTOByGatePassNumber of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getLoadMovementTOByGatePassNumber of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getLoadMovementTOByGatePassNumber of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getLoadMovementTOByGatePassNumber::END------------>:::::::");
	}

	/**
	 * Validate manifest number.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void validateManifestNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::validateManifestNumber::START------------>:::::::");
		PrintWriter out = null;
		String manifestTOJSON = null;
		ManifestTO manifestTO = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			String manifestNumber = request
					.getParameter(LoadManagementConstants.MANIFEST_NUMBER);
			Integer loggedInOfficeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.LOGGED_IN_OFFICE_ID));
			Integer destCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.DEST_CITY_ID));

			final ManifestTO manifestTO1 = new ManifestTO();
			OfficeTO originOfficeTO = new OfficeTO();
			CityTO destinationCityTO = new CityTO();

			destinationCityTO.setCityId(destCityId);
			originOfficeTO.setOfficeId(loggedInOfficeId);
			manifestTO1.setOriginOfficeTO(originOfficeTO);
			manifestTO1.setDestinationCityTO(destinationCityTO);
			manifestTO1.setManifestNumber(manifestNumber);
			manifestTO1.setLoadOriginOfficeType(request
					.getParameter("originOfficeType"));
			manifestTO1.setLoadDestOfficeType(request
					.getParameter("destOfficeType"));

			prepareStockIssueValidationTO(request, manifestTO1);
			
			manifestTO = loadDispatchService
					.validateManifestNumber(manifestTO1);

		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateManifestNumber of LoadDispatchAction..."
					, e);
		} finally {
			if (manifestTO == null) {
				manifestTO = new ManifestTO();
			}
			manifestTO.setErrorMsg(errorMsg);
			manifestTOJSON = JSONSerializer.toJSON(manifestTO).toString();
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::validateManifestNumber::END------------>:::::::");
	}

	/**
	 * Prepare stock issue validation to.
	 *
	 * @param request the request
	 * @param manifestTO the manifest to
	 */
	private void prepareStockIssueValidationTO(HttpServletRequest request,
			ManifestTO manifestTO) {
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		OfficeTO officeTO = userInfoTO.getOfficeTo();

		StockIssueValidationTO stockIssueValidationTO = new StockIssueValidationTO();
		stockIssueValidationTO.setStockItemNumber(manifestTO.getManifestNumber());
		//stockIssueValidationTO.setSeriesType(seriesType);
		stockIssueValidationTO
				.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		stockIssueValidationTO.setIssuedTOPartyId(officeTO
				.getRegionTO().getRegionId());
		stockIssueValidationTO.setRegionCode(officeTO.getRegionTO()
				.getRegionCode());
		
		char bplMbplChar = manifestTO.getManifestNumber().charAt(3);
		if (bplMbplChar == 'M') {
			stockIssueValidationTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS);
		} else {// 'B'
			stockIssueValidationTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);			
		}
		manifestTO.setStockIssueValidationTO(stockIssueValidationTO);
	}

	/**
	 * Save or update load dispatch.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void saveOrUpdateLoadDispatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::saveLoadDispatch::START------------>:::::::");
		LoadMovementTO loadMovementTO = null;
		LoadMovementTO loadMovementTO2 = null;
		String errorMessage = null;
		String successMessage = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			LoadManagementForm loadManagementForm = (LoadManagementForm) form;
			loadMovementTO = (LoadMovementTO) loadManagementForm.getTo();
			getAndSetLoggedInOfficeDtls(request, loadMovementTO);
			
			loadMovementTO2 = loadDispatchService
					.saveOrUpdateLoadDispatch(loadMovementTO);
			
			//calling TwoWayWrite service to save same in central
			twoWayWrite(loadMovementTO2);
			
			successMessage = getMessageFromErrorBundle(request,
					LoadManagementConstants.GATEPASS_NUMBER_DETAILS_SAVED,
					new Object[] { loadMovementTO2.getGatePassNumber() });
			
		} catch (CGSystemException e) {
			errorMessage = getSystemExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveLoadDispatch of LoadDispatchAction.."
					, e);
		} catch (CGBusinessException e) {
			errorMessage = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveLoadDispatch of LoadDispatchAction.."
					, e);			
		} catch (Exception e) {
			errorMessage = getGenericExceptionMessage(request, e);
			//message = prepareErrorMessageSystemException(request, e, LoadManagementConstants.ERROR_IN_SAVING_GATEPASS_NUMBER_DETAILS);
			LOGGER.error("Exception happened in saveLoadDispatch of LoadDispatchAction.."
					, e);
		} finally {
			if(loadMovementTO2==null){
				loadMovementTO2 = new LoadMovementTO();
			}
			loadMovementTO2.setErrorMessage(errorMessage);
			loadMovementTO2.setSuccessMessage(successMessage);
			String loadMovementTOJSON = JSONSerializer.toJSON(loadMovementTO2)
					.toString();
			out.write(loadMovementTOJSON);
		}
		LOGGER.debug("LoadDispatchAction::saveLoadDispatch::END------------>:::::::");
	}

	/**
	 * Validate transshipment route.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void validateTransshipmentRoute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::validateTransshipmentRoute::START------------>:::::::");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer transshipmentCityId = Integer
					.valueOf(request
							.getParameter(LoadManagementConstants.TRANSSHIPMENT_CITY_ID));
			Integer servicedCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.SERVICED_CITY_ID));

			TransshipmentRouteTO transshipmentRouteTO = new TransshipmentRouteTO();

			transshipmentRouteTO.setTransshipmentCityId(transshipmentCityId);
			transshipmentRouteTO.setServicedCityId(servicedCityId);

			boolean isTransshipmentFlag = loadDispatchService
					.validateTransshipmentRoute(transshipmentRouteTO);
			//response.getWriter().print(isTransshipmentFlag);
			jsonResult = isTransshipmentFlag + "";
			
		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in validateTransshipmentRoute of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in validateTransshipmentRoute of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in validateTransshipmentRoute of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::validateTransshipmentRoute::END------------>:::::::");
	}

	/**
	 * Prints the load dispatch.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward printLoadDispatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::printLoadDispatch::START------------>:::::::");
		LoadMovementTO loadMovementTO = null;
		String gatePassNumber = null;
		String url = null;
		try {
			gatePassNumber = request
					.getParameter(LoadManagementConstants.GATE_PASS_NUMBER);

			 loadMovementTO = loadDispatchService
					.getLoadMovementTOByGatePassNumber4Print(gatePassNumber);
			 HttpSession session = (HttpSession) request.getSession(false);
				UserInfoTO userInfoTO = (UserInfoTO) session
						.getAttribute(UmcConstants.USER_INFO);
				OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
				loadMovementTO.setLoggedInOfficeTO(loggedInofficeTO);
			 request.setAttribute("loadMovementTO", loadMovementTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in printLoadDispatch of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in printLoadDispatch of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in printLoadDispatch of LoadDispatchAction..."
					, e);
		} finally {
			if (loadMovementTO == null) {
				url = LoadManagementConstants.URL_VIEW_LOAD_DISPATCH;
			} else {
				url = LoadManagementConstants.URL_PRINT_LOAD_DISPATCH;
			}
		}
		LOGGER.debug("LoadDispatchAction::printLoadDispatch::END------------>:::::::");
		return mapping.findForward(url);
		
	}

	/**
	 * Gets the trip serviced by t os for transport.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the trip serviced by t os for transport
	 */
	public void getTripServicedByTOsForTransport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadDispatchAction::getTripServicedByTOsForTransport::START------------>:::::::");

		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String routeIdStr = request
					.getParameter(LoadManagementConstants.ROUTE_ID);
			Integer routeId = null;
			if (StringUtils.isNotBlank(routeIdStr)) {
				routeId = Integer.valueOf(routeIdStr);
			}

			Integer transportModeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.TRANSPORT_MODE_ID));
			Integer serviceByTypeId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.SERVICE_BY_TYPE_ID));
			Integer vendorId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.VENDOR_ID));

			RouteTO routeTO = new RouteTO();
			routeTO.setRouteId(routeId);
			
			TransportModeTO transportModeTO = new TransportModeTO();
			transportModeTO.setTransportModeId(transportModeId);
			
			ServiceByTypeTO serviceByTypeTO = new ServiceByTypeTO();
			serviceByTypeTO.setServiceByTypeId(serviceByTypeId);
			
			LoadMovementVendorTO loadMovementVendorTO = new LoadMovementVendorTO();
			loadMovementVendorTO.setVendorId(vendorId);
			
			TripTO tripTO = new TripTO();
			tripTO.setRouteTO(routeTO);
			
			ServicedByTO servicedByTO = new ServicedByTO();
			servicedByTO.setLoadMovementVendorTO(loadMovementVendorTO);
			servicedByTO.setServiceByTypeTO(serviceByTypeTO);

			TripServicedByTO tripServicedByTO = new TripServicedByTO();			
			tripServicedByTO.setTransportModeTO(transportModeTO);
			tripServicedByTO.setTripTO(tripTO);
			tripServicedByTO.setServicedByTO(servicedByTO);
			
			List<TripServicedByTO> tripServicedByTOs = loadDispatchService
					.getTripServicedByTOsForTransport(tripServicedByTO);
			jsonResult = JSONSerializer.toJSON(
					tripServicedByTOs).toString();
			//response.getWriter().print(jsonResult);
			
		} catch (CGSystemException e) {
			//errorMsg = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));

			LOGGER.error("Exception happened in getTripServicedByTOsForTransport of LoadDispatchAction..."
					, e);
		} catch (CGBusinessException e) {
			//errorMsg = getBusinessErrorFromWrapper(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getTripServicedByTOsForTransport of LoadDispatchAction..."
					, e);
		} catch (Exception e) {
			//errorMsg = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getTripServicedByTOsForTransport of LoadDispatchAction..."
					, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("LoadDispatchAction::getTripServicedByTOsForTransport::END------------>:::::::");
	}

}
