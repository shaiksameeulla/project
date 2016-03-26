package com.ff.web.loadmanagement.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.web.common.SpringConstants;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.service.LoadManagementService;

/**
 * The Class LoadManagementAction is common Action class for LoadManagement module.
 *
 * @author narmdr
 */
public abstract class LoadManagementAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadManagementAction.class);
	
	/** The load management common service. */
	private transient LoadManagementCommonService loadManagementCommonService;
	private transient LoadManagementService loadManagementService;
	
	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * Gets the and set logged in office dtls.
	 *
	 * @param request the request
	 * @param loadManagementTO the load management to
	 * @return the and set logged in office dtls
	 */
	public void getAndSetLoggedInOfficeDtls(final HttpServletRequest request,
			LoadManagementTO loadManagementTO) {
		LOGGER.debug("LoadManagementAction::getAndSetLoggedInOfficeDtls::START------------>:::::::");
		try {
			final HttpSession session = (HttpSession) request.getSession(false);
			final UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();

			loadManagementTO.setLoggedInOfficeTO(loggedInOfficeTO);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getAndSetLoggedInOfficeDtls of LoadManagementAction...", e);
		}
		LOGGER.debug("LoadManagementAction::getAndSetLoggedInOfficeDtls::END------------>:::::::");	
	}
	
	/**
	 * Gets the consignment type list.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the consignment type list
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentTypeList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadManagementAction::getConsignmentTypeList::START------------>:::::::");
		
		try {
			loadManagementCommonService = (LoadManagementCommonService) 
					getBean(SpringConstants.LOAD_MANAGEMENT_COMMON_SERVICE);
			List<ConsignmentTypeTO> consignmentTypeTOList  = loadManagementCommonService.getConsignmentTypeTOList();
			String consignmentTypeTOListJSON = serializer.toJSON(consignmentTypeTOList).toString();
			response.getWriter().print(consignmentTypeTOListJSON);
			
		} catch (Exception e) {
			LOGGER.error("Exception happened in getConsignmentTypeList of LoadManagementAction...", e);
		}		
		LOGGER.debug("LoadManagementAction::getConsignmentTypeList::END------------>:::::::");	
	}
	

	/**
	 * Gets the office by office id.
	 *
	 * @param officeId the office id
	 * @return the office by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public OfficeTO getOfficeByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		loadManagementCommonService = (LoadManagementCommonService) 
				getBean(SpringConstants.LOAD_MANAGEMENT_COMMON_SERVICE);
		return loadManagementCommonService.getOfficeByOfficeId(officeId);
	}
	
	/**
	 * Gets the vehicle no list by office id.
	 *
	 * @param officeId the office id
	 * @return the vehicle no list by office id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<LabelValueBean> getVehicleNoListByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		loadManagementCommonService = (LoadManagementCommonService) 
				getBean(SpringConstants.LOAD_MANAGEMENT_COMMON_SERVICE);
		return loadManagementCommonService.getVehicleNoListByOfficeId(officeId);
	}
	
	/**
	 * Gets the city.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the city
	 */
	public void getCity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadManagementAction::getCity::START------------>:::::::");
		try {	
			loadManagementCommonService = (LoadManagementCommonService) 
					getBean(SpringConstants.LOAD_MANAGEMENT_COMMON_SERVICE);
			CityTO cityTO = new CityTO();
			Integer cityId = null;
			String cityIdStr = request.getParameter(LoadManagementConstants.CITY_ID);
			String cityCode = request.getParameter(LoadManagementConstants.CITY_CODE);
			if(StringUtils.isNotBlank(cityIdStr)){
				cityId = Integer.valueOf(cityIdStr);
			}
			if(StringUtils.isNotBlank(cityCode)){
				cityTO.setCityCode(cityCode);
			}
			cityTO.setCityId(cityId);
			
			cityTO = loadManagementCommonService.getCity(cityTO);
			String cityTOJSON = JSONSerializer.toJSON(cityTO).toString();
			response.getWriter().write(cityTOJSON);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getCity of LoadManagementAction...", e);
		}
		LOGGER.debug("LoadManagementAction::getCity::END------------>:::::::");	
	}

	/**
	 * Gets the all transport mode list.
	 *
	 * @return the all transport mode list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("LoadManagementAction::getAllTransportModeList::START------------>:::::::");
		loadManagementCommonService = (LoadManagementCommonService) 
				getBean(SpringConstants.LOAD_MANAGEMENT_COMMON_SERVICE);
		List<LabelValueBean> transportModeList = loadManagementCommonService
				.getAllTransportModeList();
		if (transportModeList == null) {
			transportModeList = new ArrayList<LabelValueBean>();
		}
		LOGGER.debug("LoadManagementAction::getAllTransportModeList::END------------>:::::::");	
		return transportModeList;
	}

	/**
	 * Two way write.
	 *
	 * @param loadManagementTO the load management to
	 * @throws CGBusinessException the cG business exception
	 */
	public void twoWayWrite(LoadManagementTO loadManagementTO)
			throws CGBusinessException {
		loadManagementService = (LoadManagementService) getBean(SpringConstants.LOAD_MANAGEMENT_SERVICE);
		loadManagementService.twoWayWrite(loadManagementTO);
	}
	
/*	public String getMessageFromErrorBundle(HttpServletRequest request,
			CGBusinessException e) {
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(e.getMessage());
		}
		return msg;
	}*/

	/**
	 * getMessageFromErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @param args
	 *            the args
	 * @return message
	 */
/*	public String getMessageFromErrorBundle(HttpServletRequest request,
			String key, Object[] args) {
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(key, args);
		}
		return msg;
	}*/

	/**
	 * getMessageFromErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @return message
	 */
/*	public String getMessageFromErrorBundle(HttpServletRequest request,
			String key) {
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(key);
		}
		return msg;
	}*/

	/**
	 * getErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @return error messages
	 * @throws CGBusinessException 
	 */
/*	private MessageResources getErrorBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}*/

}
