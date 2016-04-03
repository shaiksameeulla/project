package com.ff.report.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
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
import com.ff.business.ConsignmentCustomerTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.report.customer.service.CustomerReportService;
import com.ff.serviceOfferring.ProductTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.report.CommonReportWrapperTO;

/**
 * @author khassan
 * 
 */
public class ReportBaseAction extends CGBaseAction {
	
	public enum UserType{
		BO, HO, RO, C, CO, ADM;
	}

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReportBaseAction.class);
	private CommonReportService commonReportService;
	private CustomerReportService customerReportService;
	        

	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
	}
	
	/*public void setServlet1(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		customerReportService = (CustomerReportService) getBean(CommonReportConstant.CUSTOMER_REPORT_SERVICE);
	}*/
	
	
	public CustomerReportService getCustomerReportService() {
		if(customerReportService==null){
			customerReportService = (CustomerReportService) getBean(CommonReportConstant.CUSTOMER_REPORT_SERVICE);
		}
		return customerReportService;
	}


	/**
	 * @param request
	 * @param attributeName
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	public void addRegionList(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		
		
		HttpSession session=request.getSession(false);		
		List<RegionTO> regionTo = new ArrayList<RegionTO>();
		regionTo = (List<RegionTO>)session.getAttribute(attributeName);

		if(CGCollectionUtils.isEmpty(regionTo)) {
			//regionTo = new ArrayList<RegionTO>();
			commonReportService = (CommonReportService) getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			regionTo = commonReportService.getRegions();
			
			session.setAttribute(attributeName, regionTo);
		}
		
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
	
	/*public void addCustomerType(HttpServletRequest request, String attributeName)
			throws CGBusinessException, CGSystemException {
		List<CustomerTypeTO> CustomerTypeTo = commonReportService.getCustomerType();
		request.setAttribute(attributeName, CustomerTypeTo);
	}*/
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getStations::START----->");
		
		String jsonResult = null;
		PrintWriter out = null;
		Integer userId = getUserId(request);
		Integer officeId = getOfficeId(request);
		UserType userType = getUserType(request);
		List<CityTO> stationList = new ArrayList<CityTO>();

		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			Integer regionId = Integer.parseInt(region);
			
			switch(userType){
			case BO:
				stationList = commonReportService.getCityByRegionForBranchUser(userId, regionId);
				break;
			case HO: 
				stationList = commonReportService.getCitiesByRegionForHubUser(userId, officeId, regionId);
				break;
			case RO:
				// stationList = commonReportService.getCitiesByRegionForRhoUser(userId, officeId, regionId);
				stationList = commonReportService.getCitiesByRegionId(regionId);
				break;
			default :
				stationList = commonReportService.getCitiesByRegionId(regionId);
				break;
			}

			if (!CGCollectionUtils.isEmpty(stationList)) {
				jsonResult = JSONSerializer.toJSON(stationList).toString();
			}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReportBaseAction :: getStations() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("ReportBaseAction :: getStations() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ReportBaseAction :: getStations() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ReportBaseAction::getStations::END----->");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getBranchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getBranchList::START----->");
		String jsonResult = null;
		PrintWriter out = null;
		Integer userId = getUserId(request);
		Integer officeId = getOfficeId(request);
		UserType userType = getUserType(request);
		//HttpSession session = request.getSession(false);
		//UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		//UserTO userto = userInfoTO.getUserto();
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();

		try {
			out = response.getWriter();
			String city = request.getParameter("cityID");
			
			Integer cityID = Integer.parseInt(city);
			
			switch(userType){
			case BO:
				officeTOList = commonReportService.getOfficeForBOUser(cityID, userId);
				break;
			case HO : 
				officeTOList = commonReportService.getOfficeByUserType(UserType.HO.toString(), userId, officeId, cityID);
				break;
			case RO : 
				//officeTOList = commonReportService.getReportingOfficesForRhoUser(userId, cityID, officeId);
				officeTOList = commonReportService.getOfficesByCityIdForReport(cityID);
				break;
			default :
				officeTOList = commonReportService.getOfficesByCityIdForReport(cityID);
				//officeTOList = commonReportService.getOfficecByCityIDAndUserIDForReport(userId, cityID);
				
			}
			 
			jsonResult = JSONSerializer.toJSON(officeTOList).toString();

		} catch (IOException e) {
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getBranchList::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			//String branch = request.getParameter("branch");
			//Integer[] branchID = Integer.parseInt(branch);
			// List<CustomerTO> customerTO = commonReportService.getCustomersByOfficeId(branchID);
			
			String branch = request.getParameter("branch");	
			String[] branchContent = branch.split(",");
			Integer[] branchId = new Integer[branchContent.length];
			for(int i = 0; i < branchContent.length; i++) {
				branchId[i] = Integer.parseInt(branchContent[i].trim());
			}
			List<CustomerTO> customerTO = commonReportService.getCustomersByOfficeIds(branchId);
			jsonResult = JSONSerializer.toJSON(customerTO).toString();

		} catch (IOException e) {
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ReportBaseAction :: getBranchList() ::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}

	/**
	 * To get customer list for report i.e. LC/COD/Topay
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getCustomerListForReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ReportBaseAction :: getCustomerListForReport() :: START ::");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String regionIdStr = request.getParameter("regionId");
			Integer regionId = null;
			if (!StringUtil.isNull(regionIdStr)) {
				regionId = Integer.parseInt(regionIdStr);
			}
			String branch = request.getParameter("branch");
			String[] branchContent = branch.split(",");
			Integer[] branchId = new Integer[branchContent.length];
			for (int i = 0; i < branchContent.length; i++) {
				branchId[i] = Integer.parseInt(branchContent[i].trim());
			}
			List<CustomerTO> customerTO = commonReportService
					.getCustomersByOffIds(branchId, regionId);
			jsonResult = JSONSerializer.toJSON(customerTO).toString();
		} catch (IOException e) {
			LOGGER.error(
					"ReportBaseAction :: getCustomerListForReport() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());
		} catch (CGSystemException e) {
			LOGGER.error(
					"ReportBaseAction :: getCustomerListForReport() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error(
					"ReportBaseAction :: getCustomerListForReport() :: ",
					e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ReportBaseAction :: getCustomerListForReport() :: END ::");
	}
	
	public void addCommonParams(HttpServletRequest request) throws CGBusinessException, CGSystemException{
		
		UserInfoTO userInfoTO = null;
		//OfficeTO officeTO = null;
		//String officeType = null;
		HttpSession session = request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		/*if(userInfoTO != null){
			officeTO = userInfoTO.getOfficeTo();
		}*/
		
		/*if ( officeTO.getOfficeTypeTO() != null
				&& officeTO.getOfficeTypeTO().getOffcTypeCode() != null) {
			officeType = userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode();
		}*/
		
		String cust = userInfoTO.getUserto().getUserType();
		if(cust.equalsIgnoreCase("C")){
			addparamsForCustomer(userInfoTO, request);
		}
		
		UserType userType = getUserType(request);
		if(userType == null )
			throw new CGBusinessException("User Type or Office Type is null");
		
		switch(userType){
		case BO:
			addPraramsForBranchUser(userInfoTO, request);
			break;
		case HO: 
			addPraramsForHubUser(userInfoTO, request);
			break;
		case RO:
			addPraramsForRhoUser(userInfoTO, request);
			break;
		case C:
			addparamsForCustomer(userInfoTO, request);
			break;
		case CO:
			addRegionEditableParams(request);
			break;
		default : 
			addRegionEditableParams(request);
		}
		
		
	}
	
	public void addPraramsForBranchUser(UserInfoTO userInfoTO, HttpServletRequest request) throws CGBusinessException, CGSystemException{
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		UserTO userto = userInfoTO.getUserto();
		Integer userId = userto.getUserId();
		Integer officeId = userInfoTO.getOfficeTo().getOfficeId();
		if(userId == null)
			throw new CGBusinessException("User Id for branch user can't be null ");
		
		List<RegionTO> regionList = new ArrayList<RegionTO>();
		List<CityTO> cityTOList  = new ArrayList<CityTO>();
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		regionList = commonReportService.getAllRegionForBoUser(userId, officeId);
		commonReportWrapperTO.getRegionTO().addAll(regionList);
		if(regionList.size() == 1){
			cityTOList = commonReportService.getCityByRegionForBranchUser(userId, regionList.get(0).getRegionId());
			
			if(cityTOList != null)
				commonReportWrapperTO.getCityTO().addAll(cityTOList);
		}
		
		if(cityTOList.size() == 1){
			 officeTOList = commonReportService.getOfficeForBOUser(cityTOList.get(0).getCityId(), userId);
			
			 if(officeTOList != null)
				 commonReportWrapperTO.getOfficeTO().addAll(officeTOList);
		}
		
		request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
		request.setAttribute("officeType", getUserType(request));
		request.setAttribute("branchOfficeType", UserType.BO);
	}
	
	public void addPraramsForHubUser(UserInfoTO userInfoTO, HttpServletRequest request) throws CGSystemException, CGBusinessException{
		
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		List<RegionTO> regionList = new ArrayList<RegionTO>();
		List<CityTO> cityTOList  = new ArrayList<CityTO>();
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		UserTO userto = userInfoTO.getUserto();
		Integer userId = userto.getUserId();
		Integer officeId= userInfoTO.getOfficeTo().getOfficeId();
		if(userId == null)
			throw new CGBusinessException("User Id for Hub user can't be null ");
		
		if(officeId == null)
			throw new CGBusinessException("Office Id for Hub user can't be null ");
		
		regionList = commonReportService.getAllRegionByUserType(userId, officeId, UserType.HO.toString());
		if(regionList.size() == 1){
			cityTOList = commonReportService.getCitiesByRegionForHubUser(userId, officeId, regionList.get(0).getRegionId());
			if(cityTOList != null)
				commonReportWrapperTO.getCityTO().addAll(cityTOList);
		}
		
		if(cityTOList.size() == 1){
			officeTOList = commonReportService.getOfficeByUserType(UserType.HO.toString(), userId, officeId, cityTOList.get(0).getCityId());
			 if(officeTOList != null)
				 commonReportWrapperTO.getOfficeTO().addAll(officeTOList);
		}
		
		commonReportWrapperTO.getRegionTO().addAll(regionList);
		request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
		request.setAttribute("officeType", getUserType(request));
		request.setAttribute("hubOfficeType", UserType.HO);
	}
	
public void addPraramsForRhoUser(UserInfoTO userInfoTO, HttpServletRequest request) throws CGSystemException, CGBusinessException{
		
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		List<RegionTO> regionList = new ArrayList<RegionTO>();
		List<CityTO> cityTOList  = new ArrayList<CityTO>();
		List<OfficeTO> officeTOList = new ArrayList<OfficeTO>();
		UserTO userto = userInfoTO.getUserto();
		Integer userId = userto.getUserId();
		Integer officeId= userInfoTO.getOfficeTo().getOfficeId();
		if(userId == null)
			throw new CGBusinessException("User Id for Rho user can't be null ");
		
		if(officeId == null)
			throw new CGBusinessException("Office Id for Rho user can't be null ");
		
		regionList = commonReportService.getAllRegionByUserType(userId, officeId, UserType.RO.toString());
		if(regionList.size() == 1){
			cityTOList = commonReportService.getCitiesByRegionForRhoUser(userId, officeId, regionList.get(0).getRegionId());
			if(cityTOList != null)
				commonReportWrapperTO.getCityTO().addAll(cityTOList);
		}
		
		if(cityTOList.size() == 1){
			officeTOList = commonReportService.getReportingOfficesForRhoUser(
					userId, cityTOList.get(0).getCityId(),  officeId);
			//officeTOList = commonReportService.getOfficeByUserType(UserType.RO.toString(), userId, officeId, cityTOList.get(0).getCityId());
			 if(officeTOList != null)
				 commonReportWrapperTO.getOfficeTO().addAll(officeTOList);
		}
		
		commonReportWrapperTO.getRegionTO().addAll(regionList);
		request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
		request.setAttribute("officeType", getUserType(request));
		request.setAttribute("rhoOfficeType", UserType.RO);
	}
	
	
	public void addCityEditableParams(UserInfoTO userInfoTO, HttpServletRequest request) throws CGBusinessException, CGSystemException{
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		RegionTO regTO = new RegionTO();
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		UserTO userto = userInfoTO.getUserto();
		regTO.setRegionId(officeTO.getRegionTO().getRegionId());
		regTO.setRegionName(officeTO.getRegionTO().getRegionName());
		List<CityTO> cityList = commonReportService
				.getCitiesByRegionId(officeTO.getRegionTO().getRegionId());
		
		if(cityList != null && cityList.size() == 1){
			List<OfficeTO> officeTOList = commonReportService.getOfficecByCityIDAndUserIDForReport(userto.getUserId(), officeTO.getCityId());
			commonReportWrapperTO.getOfficeTO().addAll(officeTOList);
		}
		commonReportWrapperTO.getCityTO().addAll(cityList);
		commonReportWrapperTO.getRegionTO().add(regTO);
		
		request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
	}
	
	public void addRegionEditableParams( HttpServletRequest request) throws CGBusinessException, CGSystemException{
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		List<RegionTO> regionTo = commonReportService.getRegions();
		commonReportWrapperTO.getRegionTO().addAll(regionTo);
		request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
	}
	
	
	public UserType getUserType(HttpServletRequest request){
		
		UserInfoTO userInfoTO = null;
		String officeType = null;
		UserType userType = null;
		HttpSession session = request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		OfficeTO officeTO = userInfoTO.getOfficeTo();
		 
		if ( officeTO != null && officeTO.getOfficeTypeTO() != null
				&& officeTO.getOfficeTypeTO().getOffcTypeCode() != null) {
			officeType = userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode();
		}
		
		for (UserType value : UserType.values()) {
			if(value.name().equalsIgnoreCase(officeType))
				return userType = value;
		}
		return userType;
	}
	
	public Integer getUserId(HttpServletRequest request){
		Integer userId = null;
		UserInfoTO userInfoTO = null;
		HttpSession session = request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		UserTO userto = userInfoTO.getUserto();
		userId = userto.getUserId();

		return userId;
	}
	
	public Integer getOfficeId(HttpServletRequest request){
		Integer officeId = null;
		UserInfoTO userInfoTO = null;
		HttpSession session = request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		officeId= userInfoTO.getOfficeTo().getOfficeId();

		return officeId;
	}
	
	public void getCustomerByStationAndProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getCustomerByBranchAndRateProductCategory::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String product = request.getParameter("product");
			String[] content = product.split(",");
			Integer[] productIds = new Integer[content.length];
			for(int i = 0; i < content.length; i++) {
				productIds[i] = Integer.parseInt(content[i]);
			}
			Integer station = Integer.parseInt(request.getParameter("station"));
			List<CustomerTO> customerTO = commonReportService
					.getCustomerByStationAndProduct(productIds,station);
			jsonResult = JSONSerializer.toJSON(customerTO).toString();

		} catch (IOException e) {
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}
	
	public void getCustomerByBranchAndRateProductCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getCustomerByBranchAndRateProductCategory::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			String branch = request.getParameter("branch");
			String[] branchContent = branch.split(",");
			Integer[] officeIds = new Integer[branchContent.length];
			for(int i = 0; i < branchContent.length; i++) {
				officeIds[i] = Integer.parseInt(branchContent[i]);
			}
			Integer rateProduct = Integer.parseInt(request.getParameter("rateProduct"));
			List<CustomerTO> customerTO = commonReportService
					.getCustomerByBranchAndRateProductCategory(officeIds,rateProduct);
			jsonResult = JSONSerializer.toJSON(customerTO).toString();

		} catch (IOException e) {
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
			LOGGER.error("ReportBaseAction :: getCustomerByBranchAndRateProductCategory() ::"
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

	}

	/**
	 * This method gets the customers on the basis of below criteria,
	 * officeIds - 	customers with type CR/CC/LC/CD/GV/FR having given office(s) as its Pickup/Billing office
	 * 				customers with type AC having given office(s) as its Mapped To office
	 * cityId	 -	customers with type BA/BV having given city as city of its Mapped To office
	 * and all Reverse Logistics (RL) customers
	 */
	public void getCustomersByContractBranches(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ReportBaseAction::getCustomersByContractBranches::START----->");
		String jsonResult = null;
		PrintWriter out = null;

		try {
			out = response.getWriter();
			//String branch = request.getParameter("branch");
			//Integer[] branchID = Integer.parseInt(branch);
			// List<CustomerTO> customerTO = commonReportService.getCustomersByOfficeId(branchID);
			
			String offices = request.getParameter("officeIds");	
			String[] officeContent = offices.split(",");
			Integer[] officeIds = new Integer[officeContent.length];
			for(int i = 0; i < officeContent.length; i++) {
				officeIds[i] = Integer.parseInt(officeContent[i].trim());
			}
			
			String cities = request.getParameter("cityId");	
			String[] cityContent = cities.split(",");
			Integer[] cityIds = new Integer[cityContent.length];
			for(int i = 0; i < cityContent.length; i++) {
				cityIds[i] = Integer.parseInt(cityContent[i].trim());
			}
			//Integer cityId = Integer.parseInt(request.getParameter("cityId"));

			//List<CustomerTO> customerTO = commonReportService.getCustomersByContractBranches(officeIds, cityId);
			
			List<ConsignmentCustomerTO> consignmentCustomerTO = commonReportService.getCustomersByContractBranchesForConsignmentDetails(officeIds, cityIds);
			jsonResult = JSONSerializer.toJSON(consignmentCustomerTO).toString();

		} catch (IOException e) {
			LOGGER.error("ReportBaseAction :: getCustomersByContractBranches() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					e.toString());

		} catch (CGSystemException e) {
			LOGGER.error("ReportBaseAction :: getCustomersByContractBranches() ::"
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
	
	public void addparamsForCustomer(UserInfoTO userInfoTO, HttpServletRequest request) throws CGBusinessException, CGSystemException{
		CommonReportWrapperTO commonReportWrapperTO = new CommonReportWrapperTO();
		//UserTO userto = userInfoTO.getUserto();
		CustomerUserTO userto = userInfoTO.getCustUserTo();
		//Integer userId = userto.getUserId();
		Integer userId = userto.getCustTO().getCustomerId();
		Integer cityId = userInfoTO.getOfficeTo().getCityId();
		if(userId == null)
			throw new CGBusinessException("User Id for branch user can't be null ");
		
		//List<RegionTO> regionList = new ArrayList<RegionTO>();
		//List<CityTO> cityTOList  = new ArrayList<CityTO>();
		List<OfficeTO> officeTOList = null;
		/*regionList = commonReportService.getAllRegionForBoUser(userId, officeId);
		commonReportWrapperTO.getRegionTO().addAll(regionList);
		if(regionList.size() == 1){
			cityTOList = commonReportService.getCityByRegionForBranchUser(userId, regionList.get(0).getRegionId());
			
			if(cityTOList != null)
				commonReportWrapperTO.getCityTO().addAll(cityTOList);
		}*/
		request.setAttribute("loggedInCustomer", userId);
		customerReportService = getCustomerReportService();
		officeTOList = customerReportService.getAllOffices(userId,cityId);
				 
				 request.setAttribute("officeTo", officeTOList);
				// request.setAttribute(attributeName, productTo);
		
		/*request.setAttribute(CommonReportConstant.WRAPPER_REPORT_TO, commonReportWrapperTO);
		request.setAttribute("officeType", getUserType(request));
		request.setAttribute("branchOfficeType", UserType.C);*/
	}
}
