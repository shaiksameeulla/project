package com.ff.admin.complaints.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.service.ComplaintsCommonService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.complaints.ServiceRequestComplaintTypeTO;
import com.ff.complaints.ServiceRequestCustTypeTO;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.form.RateCalculatorForm;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.BARateCalculationInputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.util.UniversalConverterUtil;

/**
 * @author sdalli
 *
 */
public abstract  class AbstractComplaintsAction extends CGBaseAction {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractComplaintsAction.class);	
	public ComplaintsCommonService complaintsCommonService;
	/** The serializer. */
	public transient JSONSerializer serializer;
	public  transient Map<String, String> configurableParams=null;

	public ComplaintsCommonService getComplaintCommonService() {
		if (StringUtil.isNull(complaintsCommonService)){
			complaintsCommonService = (ComplaintsCommonService) getBean(ComplaintsCommonConstants.COMPLAINTS_COMMON_SERVICE);
		}
		return complaintsCommonService;
	}



	/**
	 * getMessageFromErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @return message
	 */
	public String getMessageFromErrorBundle(HttpServletRequest request,
			String key) {
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(key);
		}
		return msg;
	}

	/**
	 * getErrorBundle.
	 * 
	 * @param request
	 *            the request
	 * @return error messages
	 */
	private MessageResources getErrorBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}

	public void getCity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,CGSystemException {
		LOGGER.debug("AbstractComplaintsAction::getCity::START------------>:::::::");
		try {	
			complaintsCommonService = getComplaintCommonService();
			CityTO cityTO = new CityTO();
			String cityCode = request.getParameter(ComplaintsCommonConstants.CITY_CODE);
			if(StringUtils.isNotBlank(cityCode)){
				cityTO.setCityCode(cityCode);
			}
			cityTO = complaintsCommonService.getCity(cityTO);
			String cityTOJSON = JSONSerializer.toJSON(cityTO).toString();
			response.getWriter().write(cityTOJSON);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getCity of AbstractComplaintsAction...",e);
		}
		LOGGER.debug("AbstractComplaintsAction::getCity::END------------>:::::::");	
	}



	protected void formInitializerForServiceRequest(HttpServletRequest request,
			ServiceRequestTO serviceRequestTO) throws CGBusinessException, CGSystemException {
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO)session.getAttribute(ComplaintsCommonConstants.USER_INFO);

		OfficeTO officeTO = userInfoTO.getOfficeTo();
		if(!StringUtil.isNull(officeTO)){
			serviceRequestTO.setLoginOfficeId(officeTO.getOfficeId());
			serviceRequestTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if(officeTO.getRegionTO()!=null){
				serviceRequestTO.setRegionId(officeTO.getRegionTO().getRegionId());
			}
			if(officeTO.getOfficeTypeTO()!=null){
				serviceRequestTO.setOfficeTypeId(officeTO.getOfficeTypeTO().getOffcTypeId());
			}

		}
		if(StringUtil.isStringEmpty(serviceRequestTO.getDate())){
			serviceRequestTO.setDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}
		serviceRequestTO.setBacklineExecutiveRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE,request));
		serviceRequestTO.setSalesCoordinatorRole(getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_SALES_COORDINATOR,request));
		serviceRequestTO.setServiceRequestTypeForConsg(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONSG);
		serviceRequestTO.setServiceRequestTypeForService(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_SERVICE);
		serviceRequestTO.setServiceRequestTypeForBref(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF);

		if(!StringUtil.isNull(userInfoTO.getUserto()) && !StringUtil.isEmptyInteger(userInfoTO.getUserto().getUserId())){
			UserTO userTO = new UserTO();
			userTO.setUserId(userInfoTO.getUserto().getUserId());
			serviceRequestTO.setLogginUserId(userTO.getUserId());

		}

	}

	public ActionForward calculateRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("AbstractComplaintsAction::calculateRate::start=====>");
		RateCalculatorForm rateForm = (RateCalculatorForm) form;
		RateCalculationInputTO inputTO = (RateCalculationInputTO) rateForm
				.getTo();
		RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
		inputTO = prepareInput(inputTO);
		try {
			RateCalculationService rateService = serviceFactory
					.getService(inputTO.getRateType());
			if (rateService != null) {
				RateCalculationOutputTO result = rateService
						.calculateRate(inputTO);
				request.setAttribute(RateCommonConstants.COMPONENTS,
						result.getComponents());
			}
		} catch (CGBusinessException e) {
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(RateCommonConstants.RATE_ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			LOGGER.error("AbstractComplaintsAction::calculateRate::CGBusinessException::"
					+ errorMsg,e);
		} catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::calculateRate::Exception::"
					,e);
		}
		return mapping.findForward(RateCommonConstants.RESULT_PAGE);
	}
	private RateCalculationInputTO prepareInput(RateCalculationInputTO inputTO) {
		try {
			// RateCalculationInputTO baseInput = getCreditCustomerInputTO();
			RateCalculationInputTO baseInput = getBAInputTO();
			PropertyUtils.copyProperties(baseInput, inputTO);
		} catch (IllegalAccessException |InvocationTargetException |NoSuchMethodException e) {
			LOGGER.error("AbstractComplaintsAction::calculateRate::prepareInput::"
					,e);
		}

		return inputTO;
	}
	private RateCalculationInputTO getBAInputTO() {
		RateCalculationInputTO baseInput = new BARateCalculationInputTO();
		baseInput.setRateType("BA");
		return baseInput;
	}

	public void isValidPincode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,CGSystemException {
		LOGGER.debug("AbstractComplaintsAction::isValidPincode::START------------>:::::::");
		PincodeTO pincodeTO= null;
		try {	
			complaintsCommonService = getComplaintCommonService();
			String pincode = request.getParameter(ComplaintsCommonConstants.PINCODE);
			List<PincodeTO> pincodeTOs = complaintsCommonService.getPincode(pincode);
			if (!CGCollectionUtils.isEmpty(pincodeTOs)) {
				pincodeTO=pincodeTOs.get(0);
			}
			String cityTOJSON = JSONSerializer.toJSON(pincodeTO).toString();
			response.getWriter().write(cityTOJSON);
		} catch (Exception e) {
			LOGGER.error("Exception happened in isValidPincode of AbstractComplaintsAction...",e);
		}
		LOGGER.debug("AbstractComplaintsAction::isValidPincode::END------------>:::::::");	
	}


	public void getPaperworkDtls(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,CGSystemException {
		LOGGER.debug("AbstractComplaintsAction::getPaperworkDtls::START------------>:::::::");
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		String jsonResult = "";
		PrintWriter out = null;
		try{
			out = response.getWriter();
			complaintsCommonService = getComplaintCommonService();
			String pincode = request.getParameter(ComplaintsCommonConstants.PINCODE);
			CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
			paperWorkValidationTO.setPincode(pincode);
			cnPaperWorksTOs=complaintsCommonService.getPaperWorks(paperWorkValidationTO);
			if(!StringUtil.isNull(cnPaperWorksTOs)){
				jsonResult = JSONSerializer.toJSON(cnPaperWorksTOs)
						.toString();
			}

		} catch (Exception e) {
			LOGGER.error("Exception happened in getPaperworkDtls of AbstractComplaintsAction...",e);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction:: getPaperworkDtls ::END------------>:::::::");	

	}

	@Deprecated
	public void prepareServiceQueryTypeDtls(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestQueryTypeTO> serviceRequestQueryType = getServiceQueryTypeList(request);
		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_SERVICE_QUERY_TYPE, serviceRequestQueryType);
	}
	@Deprecated
	public void prepareServiceQueryTypeDtlsAsMap(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestQueryTypeTO> serviceRequestQueryType = getServiceQueryTypeList(request);

		if(!CGCollectionUtils.isEmpty(serviceRequestQueryType)){
			Map<String, String> queryTypeMap = getServiceQueryTypeMap(serviceRequestQueryType);
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_SERVICE_QUERY_TYPE_MAP, queryTypeMap);
		}

	}



	private Map<String, String> getServiceQueryTypeMap(
			List<ServiceRequestQueryTypeTO> serviceRequestQueryType) {
		Map<String,String> queryTypeMap=new HashMap<>(serviceRequestQueryType.size());
		for(ServiceRequestQueryTypeTO queryType:serviceRequestQueryType){
			queryTypeMap.put(queryType.getServiceRequestQueryTypeId()+FrameworkConstants.CHARACTER_TILDE+queryType.getQueryTypeCode(), queryType.getQueryTypeDescription());
		}
		return queryTypeMap;
	}


	@Deprecated
	private List<ServiceRequestQueryTypeTO> getServiceQueryTypeList(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		List<ServiceRequestQueryTypeTO> serviceRequestQueryType=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		serviceRequestQueryType= (List<ServiceRequestQueryTypeTO>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_SERVICE_QUERY_TYPE);
		if(CGCollectionUtils.isEmpty(serviceRequestQueryType)){
			serviceRequestQueryType = complaintsCommonService.getServiceRequestQueryTypeDetails(null);
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_SERVICE_QUERY_TYPE, serviceRequestQueryType);
		}
		return serviceRequestQueryType;
	}

	public void prepareComplaintCustomerTypeList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestCustTypeTO> customerTypeList = getComplaintCustomerTypeList(request);
		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_CUST_TYPE, customerTypeList);
	}

	public void prepareComplaintCustomerTypeMap(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestCustTypeTO> customerTypeList = getComplaintCustomerTypeList(request);
		if(!CGCollectionUtils.isEmpty(customerTypeList)){
			Map<String,String> customerTypeMap=new HashMap<>(customerTypeList.size());
			for(ServiceRequestCustTypeTO productTO:customerTypeList){
				customerTypeMap.put(productTO.getServiceRequestCustomerTypeId()+FrameworkConstants.CHARACTER_TILDE+productTO.getCustomerTypeCode(), productTO.getCustomerTypeDescription());
			}
			customerTypeMap=CGCollectionUtils.sortByValue(customerTypeMap);
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_CUST_TYPE_MAP, customerTypeMap);
		}

	}
	public void getAllRHOListForComplaint(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		boolean isCORequired=false;
		Map<Integer, String> rhoList=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		rhoList= (Map<Integer, String>)session.getAttribute(ComplaintsCommonConstants.SESSION_PARAM_RHO_MAP);
		if(CGCollectionUtils.isEmpty(rhoList)){
			OfficeTO officeTo=getLoginOfficeTO(request);
			if(officeTo!=null && officeTo.getOfficeTypeTO()!=null && !StringUtil.isStringEmpty(officeTo.getOfficeTypeTO().getOffcTypeCode()) && officeTo.getOfficeTypeTO().getOffcTypeCode().equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){
				isCORequired=true;
			}

			rhoList=complaintsCommonService.getAllRHOAndCOOfficesAsMap(isCORequired);
			session.setAttribute(ComplaintsCommonConstants.SESSION_PARAM_RHO_MAP, rhoList);
		}
		request.setAttribute(ComplaintsCommonConstants.SESSION_PARAM_RHO_MAP, rhoList);

	}



	private List<ServiceRequestCustTypeTO> getComplaintCustomerTypeList(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		List<ServiceRequestCustTypeTO>  customerTypeList=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		customerTypeList= (List<ServiceRequestCustTypeTO>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_CUST_TYPE);
		if(CGCollectionUtils.isEmpty(customerTypeList)){
			customerTypeList = complaintsCommonService.getCustomerTypeListForComplaints();
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_CUST_TYPE, customerTypeList);
		}
		return customerTypeList;
	}

	public void prepareComplaintTypeList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestComplaintTypeTO> complaintType = getComplaintTypeList(request);
		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_TYPE, complaintType);
	}

	public void prepareComplaintTypeMap(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ServiceRequestComplaintTypeTO> complaintTypeList = getComplaintTypeList(request);

		if(!CGCollectionUtils.isEmpty(complaintTypeList)){
			Map<String,String> productMap=new HashMap<>(complaintTypeList.size());
			for(ServiceRequestComplaintTypeTO complaintType:complaintTypeList){
				productMap.put(complaintType.getServiceRequestComplaintTypeId()+FrameworkConstants.CHARACTER_TILDE+complaintType.getComplaintTypeCode(), complaintType.getComplaintTypeDescription());
			}
			productMap=CGCollectionUtils.sortByValue(productMap);
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_TYPE_MAP, productMap);
		}

	}



	private List<ServiceRequestComplaintTypeTO> getComplaintTypeList(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		List<ServiceRequestComplaintTypeTO>  complaintType=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		complaintType= (List<ServiceRequestComplaintTypeTO>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_TYPE);
		if(CGCollectionUtils.isEmpty(complaintType)){
			complaintType = complaintsCommonService.getComplaintTypeDetails();
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_COMPLAINT_TYPE, complaintType);
		}
		return complaintType;
	}

	public void populateProductInformation(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		// Get List of Products 
		List<ProductTO> productTOList;
		productTOList = getProductList(request);
		request.setAttribute(ComplaintsCommonConstants.PRODUCT_LIST, productTOList);
	}

	public void populateProductMap(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		// Get List of Products 
		List<ProductTO> productTOList;
		productTOList = getProductList(request);
		if(!CGCollectionUtils.isEmpty(productTOList)){
			Map<Integer,String> productMap=new HashMap<>(productTOList.size());
			for(ProductTO productTO:productTOList){
				productMap.put(productTO.getProductId(), productTO.getProductDesc());
			}
			productMap=CGCollectionUtils.sortByValue(productMap);
			request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_PRODUCT_MAP, productMap);
		}

	}



	private List<ProductTO> getProductList(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		List<ProductTO> productTOList;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		productTOList= (List<ProductTO>)session.getAttribute(ComplaintsCommonConstants.PRODUCT_LIST);
		if(CGCollectionUtils.isEmpty(productTOList)){
			//productTOList = complaintsCommonService.getProductList();
			productTOList = complaintsCommonService.getProductList(getConfigParamsValue(CommonConstants.CONFIG_PARAM_COMPLAINT_PRODUCT, request));
			session.setAttribute(ComplaintsCommonConstants.PRODUCT_LIST, productTOList);
		}
		return productTOList;
	}
	public void populateAllCities(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		// Get List of Products 
		Map<Integer,String> cityMapDtls=null;
		cityMapDtls = getAllCities(request);
		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS, cityMapDtls);
	}



	private Map<Integer, String> getAllCities(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		Map<Integer, String> cityMapDtls;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		cityMapDtls= (Map<Integer,String>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS);
		if(CGCollectionUtils.isEmpty(cityMapDtls)){
			cityMapDtls = complaintsCommonService.getAllCitiesByCity(null);
			if(!CGCollectionUtils.isEmpty(cityMapDtls)){
				cityMapDtls=CGCollectionUtils.sortByValue(cityMapDtls);
			}
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS, cityMapDtls);
		}
		return cityMapDtls;
	}
	public void populateAllBranches(HttpServletRequest request)
	{
		// Get List of Products 
		Map<Integer,String> allBranchList=null;
		allBranchList=getAllOfficeList(request);

		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_BRANCH_DTLS, allBranchList);
	}

	public Map<Integer,String> getAllOfficeList(HttpServletRequest request)
	{
		// Get List of Products 
		Map<Integer,String> allBranchList=null;
		try {
			allBranchList=getAllBranchList(request);
		} catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::populateAllBranches ..Exception :",e);
		}
		return allBranchList;
	}



	private Map<Integer, String> getAllBranchList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		Map<Integer, String> allBranchList=null;;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		allBranchList= (Map<Integer,String>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_BRANCH_DTLS);
		if(CGCollectionUtils.isEmpty(allBranchList)){
			allBranchList = complaintsCommonService.getAllBranchesAsMap();
			if(!CGCollectionUtils.isEmpty(allBranchList)){
				allBranchList=CGCollectionUtils.sortByValue(allBranchList);
			}
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_BRANCH_DTLS, allBranchList);
		}
		return allBranchList;
	}

	@Deprecated
	public void populateAllPincodes(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		// Get List of Products 
		Map<Integer,String> cityMapDtls=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		cityMapDtls= (Map<Integer,String>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS);
		if(CGCollectionUtils.isEmpty(cityMapDtls)){
			cityMapDtls = complaintsCommonService.getAllCitiesByCity(null);
			session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS, cityMapDtls);
		}
		request.setAttribute(ComplaintsCommonConstants.REQ_PARAM_CITY_DTLS, cityMapDtls);
	}



	public  void prepareConsignmentTypeList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentTypeTO> productTOList;

		complaintsCommonService = getComplaintCommonService();
		productTOList = getConsignmentTypeList(request);
		request.setAttribute(ComplaintsCommonConstants.CONSG_TYPES, productTOList);
	}



	private List<ConsignmentTypeTO> getConsignmentTypeList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentTypeTO> productTOList;
		HttpSession session= request.getSession(false);
		productTOList= (List<ConsignmentTypeTO>)session.getAttribute(ComplaintsCommonConstants.CONSG_TYPES);
		if(CGCollectionUtils.isEmpty(productTOList)){
			complaintsCommonService = getComplaintCommonService();
			productTOList = complaintsCommonService
					.getConsignmentType();
			session.setAttribute(ComplaintsCommonConstants.CONSG_TYPES, productTOList);
		}
		return productTOList;
	}
	public  void prepareConsignmentTypeMap(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentTypeTO> productTOList;
		Map<String,String> consignType=null;
		HttpSession session= request.getSession(false);
		consignType= (Map<String,String>)session.getAttribute(ComplaintsCommonConstants.CONSG_TYPE_MAP);
		if(CGCollectionUtils.isEmpty(consignType)){
			complaintsCommonService = getComplaintCommonService();
			productTOList=getConsignmentTypeList(request);
			if(!CGCollectionUtils.isEmpty(productTOList)){
				consignType=new HashMap<>(productTOList.size());
				for(ConsignmentTypeTO cnType:productTOList){
					consignType.put(cnType.getConsignmentCode(), cnType.getConsignmentCode());
				}

				session.setAttribute(ComplaintsCommonConstants.CONSG_TYPE_MAP, consignType);
			}
		}
		request.setAttribute(ComplaintsCommonConstants.CONSG_TYPE_MAP, consignType);
	}

	public void getSearchCategoryList(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		Map<String,String> searchCategoryList = getSearchCategoryForComplaintServiceType(request);
		request.setAttribute(ComplaintsCommonConstants.SEARCH_CATEGORY_LIST, searchCategoryList);
	}


	public void getServiceTypeList(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		Map<String,String> serviceType;
		HttpSession session= request.getSession(false);
		serviceType= (Map<String,String>)session.getAttribute(ComplaintsCommonConstants.SERVICE_TYPE);
		if(CGCollectionUtils.isEmpty(serviceType)){
			Map<String,String> searchCategoryList = getSearchCategoryForComplaintServiceType(request);
			serviceType=new HashMap<>(searchCategoryList);

			//serviceType.remove(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_BOOKING_REF);
			serviceType.remove(ComplaintsCommonConstants.SERVICE_REQUEST_TYPE_FOR_CONTACT_NO);
			for(Map.Entry<String, String> map:serviceType.entrySet()){
				map.setValue( map.getValue().replaceAll("No", ""));
				if((map.getValue().trim()).equalsIgnoreCase("Service Request")){
					map.setValue("Service query");
				}
			}
			session.setAttribute(ComplaintsCommonConstants.SERVICE_TYPE, serviceType);
		}

		request.setAttribute(ComplaintsCommonConstants.SERVICE_TYPE, serviceType);

	}



	private Map<String,String> getSearchCategoryForComplaintServiceType(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		Map<String,String> searchCategoryList;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		searchCategoryList= (Map<String,String>)session.getAttribute(ComplaintsCommonConstants.SEARCH_CATEGORY_LIST);
		if(CGCollectionUtils.isEmpty(searchCategoryList)){
			List<StockStandardTypeTO>	complaintSrchCatgry = complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.COMPLAINTS_SEARCH);
			if(!CGCollectionUtils.isEmpty(complaintSrchCatgry)){
				searchCategoryList = createStringMapFromStdType(complaintSrchCatgry);
			}
			session.setAttribute(ComplaintsCommonConstants.SEARCH_CATEGORY_LIST, searchCategoryList);
		}
		return searchCategoryList;
	}

	public void getIndustryCategoryForComplaints(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		Map<String,String> industryCategory = getLeadIndustryCategory(request);
		if(!CGCollectionUtils.isEmpty(industryCategory)){
			industryCategory=CGCollectionUtils.sortByValue(industryCategory);
		}
		request.setAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_INDUSTRY_CATEGORY, industryCategory);
	}
	private Map<String,String> getLeadIndustryCategory(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		Map<String,String> searchCategoryList;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		searchCategoryList= (Map<String,String>)session.getAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_INDUSTRY_CATEGORY);
		if(CGCollectionUtils.isEmpty(searchCategoryList)){
			List<StockStandardTypeTO>	complaintSrchCatgry = complaintsCommonService.getStandardTypesByTypeName(LeadCommonConstants.LEAD_INDUSTRY_CATEGORY);
			if(!CGCollectionUtils.isEmpty(complaintSrchCatgry)){
				searchCategoryList = createStringMapFromStdType(complaintSrchCatgry);
			}
			session.setAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_INDUSTRY_CATEGORY, searchCategoryList);
		}
		return searchCategoryList;
	}



	private Map<String, String> createStringMapFromStdType(
			List<StockStandardTypeTO> complaintSrchCatgry) {
		Map<String, String> searchCategoryList;
		searchCategoryList= new HashMap<>(complaintSrchCatgry.size());
		for(StockStandardTypeTO stdType:complaintSrchCatgry){
			searchCategoryList.put(stdType.getStdTypeCode(), stdType.getDescription());
		}
		return searchCategoryList;
	}


	public void getComplaintsSourceOfQueryList(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		Map<String,String> searchCategoryList = getStdTypeSourceOfQueryDetails(request);
		request.setAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_SOURCE_OF_QUERY, searchCategoryList);
	}
	private Map<String,String> getStdTypeSourceOfQueryDetails(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		Map<String,String> sourceOfQueryMap;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		sourceOfQueryMap= (Map<String,String>)session.getAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_SOURCE_OF_QUERY);
		if(CGCollectionUtils.isEmpty(sourceOfQueryMap)){
			List<StockStandardTypeTO>	sourceOfQueryTypes = complaintsCommonService.getStandardTypesByTypeName(ComplaintsCommonConstants.STD_TYPE_SOURCE_OF_QUERY);
			if(!CGCollectionUtils.isEmpty(sourceOfQueryTypes)){
				sourceOfQueryMap = createStringMapFromStdType(sourceOfQueryTypes);
				sourceOfQueryMap=CGCollectionUtils.sortByValue(sourceOfQueryMap);
			}
			session.setAttribute(ComplaintsCommonConstants.SESSION_ATTRIBUTE_SOURCE_OF_QUERY, sourceOfQueryMap);
		}
		return sourceOfQueryMap;
	}

	public void getComplaintStatus(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		List<ServiceRequestStatusTO> complaintsStatus = prepareComplaintStatusList(request);
		request.setAttribute(ComplaintsCommonConstants.COMPLAINTS_STATUS_LIST, complaintsStatus);
	}



	private List<ServiceRequestStatusTO> prepareComplaintStatusList(
			HttpServletRequest request) throws CGSystemException,
			CGBusinessException {
		List<ServiceRequestStatusTO> complaintsStatus;
		HttpSession session= request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		complaintsStatus= (List<ServiceRequestStatusTO>)session.getAttribute(ComplaintsCommonConstants.COMPLAINTS_STATUS_LIST);
		if(CGCollectionUtils.isEmpty(complaintsStatus)){
			complaintsStatus = complaintsCommonService.getServiceRequestStatus();
			session.setAttribute(ComplaintsCommonConstants.COMPLAINTS_STATUS_LIST, complaintsStatus);
		}
		return complaintsStatus;
	}

	public void getComplaintStatusMap(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		List<ServiceRequestStatusTO> complaintsStatus = prepareComplaintStatusList(request);
		Map<String,String> complaintStatusMap=null;
		if(!CGCollectionUtils.isEmpty(complaintsStatus)){
			complaintStatusMap= new HashMap<>(complaintsStatus.size());
			for(ServiceRequestStatusTO statusTO:complaintsStatus){
				complaintStatusMap.put(statusTO.getServiceRequestStatusId()+FrameworkConstants.CHARACTER_TILDE+statusTO.getStatusCode(), statusTO.getStatusDescription());
			}
		}
		request.setAttribute(ComplaintsCommonConstants.COMPLAINTS_STATUS_MAP, complaintStatusMap);
	}

	/**
	 * Name : getLoginUserTO
	 * purpose : to get UserTO from Session
	 * Input : HttpServletRequest request
	 * return : UserTO 
	 */
	public UserTO getLoginUserTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			LOGGER.trace("AbstractDeliveryAction::getLoginUserTO");
			return userInfo.getUserto();
		}
		return null;
	}
	
	/**
	 * Gets the logged user role list.
	 *
	 * @param request the request
	 * @return the logged user role list
	 */
	public List<String> getLoggedUserRoleList(final HttpServletRequest request){
		List<String> roleList=null;
		final UserTO userTo =getLoginUserTO(request);
		if(userTo!=null){
			roleList=userTo.getUserRoleList();
		}
		
		return roleList;
	}
	
	/**
	 * Checks if is role exist for logged in user.
	 *
	 * @param request the request
	 * @param inputRoleName the input role name
	 * @return true, if is role exist for logged in user
	 */
	public boolean isRoleExistForLoggedInUser(final HttpServletRequest request,String inputRoleName){
		boolean isValid=false;
		final List<String> roleList=getLoggedUserRoleList(request);
		if(!StringUtil.isStringEmpty(inputRoleName)&&!CGCollectionUtils.isEmpty(roleList) && roleList.contains(inputRoleName)){
			isValid=true;
		}
		
		return isValid;
	}
	/**
	 * Name : getLoginUserInfoTO
	 * purpose : to get UserInfoTO from Session
	 * Input : HttpServletRequest request
	 * return : UserInfoTO 
	 */
	public UserInfoTO getLoginUserInfoTO(final HttpServletRequest request) {
		final HttpSession session =request.getSession(Boolean.FALSE);
		final UserInfoTO userInfo =(UserInfoTO)session.getAttribute(UmcConstants.USER_INFO);
		return userInfo;
	}

	/**
	 * Gets the config params.
	 *
	 * @param request the request
	 * @return the config params
	 */
	public Map<String, String> getConfigParams(final HttpServletRequest request){
		UserInfoTO uinforTo= getLoginUserInfoTO(request);
		if(!StringUtil.isNull(uinforTo)&& !CGCollectionUtils.isEmpty(uinforTo.getConfigurableParams())){
			configurableParams=uinforTo.getConfigurableParams();
		}
		return configurableParams;
	}


	/**
	 * @return
	 */
	public String getConfigParamsValue(String paramKey,final HttpServletRequest request) {
		if(configurableParams==null){
			configurableParams=getConfigParams(request);
		}
		String paramValue=null;
		if(!CGCollectionUtils.isEmpty(configurableParams)){
			paramValue=configurableParams.get(paramKey);
		}
		return !StringUtil.isStringEmpty(paramValue)?paramValue:FrameworkConstants.EMPTY_STRING;
	}
	/**
	 * Name : getLoginEmpTO
	 * purpose : to get EmployeeTO from Session
	 * Input : HttpServletRequest request
	 * return : EmployeeTO 
	 */
	public EmployeeTO getLoginEmpTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			final EmployeeUserTO empUserTo = userInfo.getEmpUserTo();
			if(empUserTo !=null){
				return empUserTo.getEmpTO();
			}
		}
		return null;
	}

	/**
	 * Name : getLoginOfficeTO
	 * purpose : to get OfficeTO from Session
	 * Input : HttpServletRequest request
	 * return : OfficeTO 
	 */
	public OfficeTO getLoginOfficeTO(final HttpServletRequest request){
		final UserInfoTO userInfo = getLoginUserInfoTO(request);
		OfficeTO officeTo=null;
		if(userInfo!=null){
			return  userInfo.getOfficeTo();
		}
		return officeTo;
	}

	/**
	 * Name : getLoginRegionTO
	 * purpose : to get RegionTO from Session
	 * Input : HttpServletRequest request
	 * return : RegionTO 
	 */
	public RegionTO getLoginRegionTO(final HttpServletRequest request){
		OfficeTO officeTo = getLoginOfficeTO(request);
		RegionTO regionTo=null;
		if(officeTo!=null){
			regionTo= officeTo.getRegionTO();
		}
		return regionTo;
	}

	/**
	 * Name : isEmployeeLoggedIn
	 * purpose : to verify whether logged-in code is Of employee from Session
	 * Input : HttpServletRequest request
	 * return : Boolean 
	 */
	public Boolean isEmployeeLoggedIn(final HttpServletRequest request){
		final EmployeeTO employeeTo=getLoginEmpTO(request);
		boolean result = Boolean.FALSE;
		if(!StringUtil.isNull(employeeTo)){
			result = Boolean.TRUE;
		}
		return result;
	}
	/**
	 * Gets the logged in city details.
	 *
	 * @param request the request
	 * @return the logged in city details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public CityTO getLoggedInCityDetails(HttpServletRequest request) throws CGSystemException, CGBusinessException{
		OfficeTO loggedInOfficeTO=getLoginOfficeTO(request);
		Integer cityId=null;
		CityTO city=null;
		HttpSession session=request.getSession(false);
		complaintsCommonService = getComplaintCommonService();
		city= (CityTO)session.getAttribute(FrameworkConstants.LOGGED_IN_CITY);
		if(StringUtil.isNull(city)){
			if(loggedInOfficeTO!=null){
				cityId= loggedInOfficeTO.getCityId();
			}
			if(!StringUtil.isEmptyInteger(cityId)){
				CityTO cityTo=new CityTO();
				cityTo.setCityId(cityId);
				city= complaintsCommonService.getCity(cityTo);
			}
			if(!StringUtil.isNull(city)){
				session.setAttribute(FrameworkConstants.LOGGED_IN_CITY,city);
			}
		}


		return city;
	}


	/**
	 * Ajax action get party type details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void ajaxActionGetPincodeDtlsByCityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails::Start=======>");
		java.io.PrintWriter out=null;
		String result="";
		Map<Integer,String> pincodeDtls=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			//cityId = validateIntegerInput(cityIdStr,"City");
			pincodeDtls = getAllPincodeDtlsForService(request);
			result=CollectionUtils.isEmpty(pincodeDtls)?null:pincodeDtls.toString();

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetPincodeDtlsByCityDetails ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetPartyTypeDetails::end=======>");
	}

	public void ajaxActionGetEmployeeDtslByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRole::Start=======>");
		String role=request.getParameter(ComplaintsCommonConstants.QRY_PARAM_ROLE);
		java.io.PrintWriter out=null;
		String result="";
		Integer officeId=null;
		Map<Integer,String> employeeMapDtls=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			//officeId = validateIntegerInput(officeIdStr,"Office");
			OfficeTO officeTO=getLoginOfficeTO(request);
			if(officeTO!=null){
				officeId=officeTO.getOfficeId();
				if(officeTO.getOfficeTypeTO()!=null&& !StringUtil.isEmptyInteger(officeTO.getReportingRHO()) && !StringUtil.isStringEmpty(officeTO.getOfficeTypeTO().getOffcTypeCode())&& !officeTO.getOfficeTypeTO().getOffcTypeCode().equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){
					officeId=officeTO.getReportingRHO();
				}
			}
			employeeMapDtls = getEmployeeDetlsByRoleAndOffice(request, role,officeId);
			result=CollectionUtils.isEmpty(employeeMapDtls)?null:employeeMapDtls.toString();

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRole ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRole ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRole ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetPartyTypeDetails::end=======>");
	}
	
	public void ajaxActionGetEmployeeDtslByRoleAndCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRoleAndCity::Start=======>");
		String role=request.getParameter(ComplaintsCommonConstants.QRY_PARAM_ROLE);
		String cityIdStr=request.getParameter(ComplaintsCommonConstants.CITY_ID);
		java.io.PrintWriter out=null;
		String result="";
		Integer cityId=null;
		Map<Integer,String> employeeMapDtls=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			cityId = validateIntegerInput(cityIdStr,"City");
			
			employeeMapDtls = getEmployeeDetlsByRoleAndCity(request, role,cityId);
			result=CollectionUtils.isEmpty(employeeMapDtls)?null:employeeMapDtls.toString();

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRoleAndCity ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRoleAndCity ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRoleAndCity ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmployeeDtslByRoleAndCity::end=======>");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void ajaxActionGetCityListByRhoOffice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetCityListByRhoOffice::Start=======>");
		String officeIdStr=request.getParameter(ComplaintsCommonConstants.OFFICEID);
		java.io.PrintWriter out=null;
		String result="";
		Integer officeId=null;
		JSONObject jsonObject= null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			jsonObject= new JSONObject();
			officeId = validateIntegerInput(officeIdStr,"Office");
		
				
			Map<Integer,String>cityMapDtls = getCityListByOfficeId(request,officeId);
			if(CGCollectionUtils.isEmpty(cityMapDtls)){
				Map<Integer,String>employeeMapDtls = getEmployeeDetlsByRoleAndOffice(request, getConfigParamsValue(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE,request),officeId);
				if(!CGCollectionUtils.isEmpty(employeeMapDtls)){
					JSONArray empList= prepareJsonArrayForMaster(cityMapDtls);
					jsonObject.put("EMP",empList);
				}
			}else{
				JSONArray cityList= prepareJsonArrayForMaster(cityMapDtls);
				jsonObject.put("CITY",cityList);
			}
			result=jsonObject.toString();

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetCityListByRhoOffice ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetCityListByRhoOffice ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetCityListByRhoOffice ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetCityListByRhoOffice::end=======>");
	}
	
	public void ajaxActionGetEmpToDtslByEmpId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId::Start=======>");
		String role=request.getParameter(ComplaintsCommonConstants.QRY_PARAM_ROLE);
		String officeIdStr=request.getParameter(ComplaintsCommonConstants.OFFICEID);
		String employeeId=request.getParameter(ComplaintsCommonConstants.REQ_PARAM_EMP_ID);
		String transferScreen=request.getParameter("transferScreen");
		java.io.PrintWriter out=null;
		String result="";
		Integer officeId=null;
		Integer empId=null;
		EmployeeTO empTo=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);
			officeId = validateIntegerInput(officeIdStr,"Office");
			empId = validateIntegerInput(employeeId,"Employee");
			if(StringUtil.isStringEmpty(transferScreen)){
			empTo = getEmployeeToByRoleAndOffice(request,role,officeId,empId);
			}else{
				empTo = getEmployeeToByRoleAndCity(request, role, officeId, empId);
			}
			if(!StringUtil.isNull(empTo)){
				result = serializer.toJSON(empTo).toString();
			}

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId::end=======>");
	}



	private Map<Integer, String> getEmployeeDetlsByRoleAndOffice(
			HttpServletRequest request, String role,
			Integer officeId)
					throws CGSystemException, CGBusinessException {
		Map<Integer, String> employeeMapDtls=null;
		HttpSession session=request.getSession(false);
		Map<Integer,EmployeeTO> employeeMapWithTO=(Map<Integer,EmployeeTO>)session.getAttribute(role+officeId);
		if(CGCollectionUtils.isEmpty(employeeMapWithTO)){
			List<EmployeeTO> empListTo=complaintsCommonService.getEmployeeDetailsByUserRoleAndOffice(role, officeId);
			if(!CGCollectionUtils.isEmpty(empListTo)){
				employeeMapDtls=UniversalConverterUtil.getEmployeeMapFromList(empListTo);
				session.setAttribute(role+officeId, UniversalConverterUtil.getEmployeeMapWithEmpTOFromList(empListTo));
			}
		}else{
			employeeMapDtls=UniversalConverterUtil.getEmployeeMapFromEmpMapWithEmpTO(employeeMapWithTO);
		}
		return employeeMapDtls;
	}
	private Map<Integer, String> getEmployeeDetlsByRoleAndCity(
			HttpServletRequest request, String role,
			Integer cityId)
					throws CGSystemException, CGBusinessException {
		Map<Integer, String> employeeMapDtls=null;
		HttpSession session=request.getSession(false);
		Map<Integer,EmployeeTO> employeeMapWithTO=(Map<Integer,EmployeeTO>)session.getAttribute(role+cityId+"CITY");
		if(CGCollectionUtils.isEmpty(employeeMapWithTO)){
			List<EmployeeTO> empListTo=complaintsCommonService.getEmployeeDetailsByUserRoleAndCity(role, cityId);
			if(!CGCollectionUtils.isEmpty(empListTo)){
				employeeMapDtls=UniversalConverterUtil.getEmployeeMapFromList(empListTo);
				session.setAttribute(role+cityId+"CITY", UniversalConverterUtil.getEmployeeMapWithEmpTOFromList(empListTo));
			}
		}else{
			employeeMapDtls=UniversalConverterUtil.getEmployeeMapFromEmpMapWithEmpTO(employeeMapWithTO);
		}
		return employeeMapDtls;
	}
	
	private Map<Integer, String> getCityListByOfficeId(
			HttpServletRequest request,Integer officeId)
					throws CGSystemException, CGBusinessException {
		Map<Integer, String> cityMap=null;
		HttpSession session=request.getSession(false);
		 cityMap=(Map<Integer,String>)session.getAttribute(officeId+"city");
		if(CGCollectionUtils.isEmpty(cityMap)){
			cityMap=complaintsCommonService.getAllCitiesByRhoOfficeAsMap(officeId);
			session.setAttribute(officeId+"city", cityMap);
		}
		return cityMap;
	}
	public  void setUrl(HttpServletRequest request,String url) {
		request.setAttribute(FrameworkConstants.REQ_PARAM_FOR_RELOAD_URL, url);
	}
	private EmployeeTO getEmployeeToByRoleAndOffice(
			HttpServletRequest request, String role,
			Integer officeId,Integer empId)
					throws CGSystemException, CGBusinessException {
		HttpSession session=request.getSession(false);
		EmployeeTO empTo=null;
		Map<Integer,EmployeeTO> employeeMapWithTO=(Map<Integer,EmployeeTO>)session.getAttribute(role+officeId);
		if(!CGCollectionUtils.isEmpty(employeeMapWithTO)){
			empTo= employeeMapWithTO.get(empId);
		}
		if(empTo==null){
			empTo=complaintsCommonService.getEmployeeDetailsById(empId);
		}
		if(empTo!=null && !CGCollectionUtils.isEmpty(employeeMapWithTO)){
				 employeeMapWithTO.put(empTo.getEmployeeId(), empTo);
		}
		return empTo;
	}
	private EmployeeTO getEmployeeToByRoleAndCity(
			HttpServletRequest request, String role,
			Integer cityId,Integer empId)
					throws CGSystemException, CGBusinessException {
		HttpSession session=request.getSession(false);
		EmployeeTO empTo=null;
		Map<Integer,EmployeeTO> employeeMapWithTO=(Map<Integer,EmployeeTO>)session.getAttribute(role+cityId+"CITY");
		if(!CGCollectionUtils.isEmpty(employeeMapWithTO)){
			empTo= employeeMapWithTO.get(empId);
		}
		if(empTo==null){
			empTo=complaintsCommonService.getEmployeeDetailsById(empId);
		}
		if(empTo!=null && !CGCollectionUtils.isEmpty(employeeMapWithTO)){
				 employeeMapWithTO.put(empTo.getEmployeeId(), empTo);
		}
		return empTo;
	}



	public Integer validateIntegerInput(String IdStr,String type)
			throws CGBusinessException {
		Integer cityId=null;
		if(StringUtil.isStringContainsInteger(IdStr)){
			cityId=StringUtil.parseInteger(IdStr);
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST,new String[]{type});
		}
		return cityId;
	}



	private Map<Integer, String> getAllPincodeDtlsForService(HttpServletRequest request
			) throws CGBusinessException, CGSystemException {
		Map<Integer,String> allPincodeMapDtls=null;
		HttpSession session= request.getSession(false);
		complaintsCommonService=getComplaintCommonService();
		allPincodeMapDtls = (Map<Integer,String>)session.getAttribute(ComplaintsCommonConstants.REQ_PARAM_WITH_PINCODE_DTLS);
		if(CGCollectionUtils.isEmpty(allPincodeMapDtls)) {
			allPincodeMapDtls = complaintsCommonService.getAllPincodeAsMap();
			if(!CGCollectionUtils.isEmpty(allPincodeMapDtls)){
				allPincodeMapDtls=CGCollectionUtils.sortByValue(allPincodeMapDtls);
				if(CGCollectionUtils.isEmpty(allPincodeMapDtls)){
					allPincodeMapDtls= new HashMap<Integer, String>();
				}
				session.setAttribute(ComplaintsCommonConstants.REQ_PARAM_WITH_PINCODE_DTLS, allPincodeMapDtls);
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.COMPLAINTS_PINCODE_DTLS_NOT_EXIST);
			}
		}
		return allPincodeMapDtls;
	}

	@SuppressWarnings({"static-access" })
	public boolean uploadcComplaintFile(String dirPath, String fileName, FormFile formFile) throws IOException{

		boolean upload = Boolean.FALSE;
		FileOutputStream outputStream = null;
		try{
			File file = new File(dirPath);
			if(!file.exists()){
				file.mkdirs();
			}

			String path = dirPath + file.separator+ fileName;	          
			file = new File(path);
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStream.write(formFile.getFileData());
		}finally{
			outputStream.close();
		}
		return upload;

	}

	@SuppressWarnings("unchecked")
	public List<ServiceRequestTransfertoTO> getTransdfettoDetails(HttpServletRequest request) throws CGBusinessException, CGSystemException{

		HttpSession session= request.getSession(false);
		complaintsCommonService=getComplaintCommonService();
		List<ServiceRequestTransfertoTO> transfertoList = null;

		transfertoList = (List<ServiceRequestTransfertoTO>) session.getAttribute("transferICCList");
		if(CGCollectionUtils.isEmpty(transfertoList)){
			transfertoList = complaintsCommonService.getTransfettoDetails();
			if(!CGCollectionUtils.isEmpty(transfertoList)){
				session.setAttribute("transferICCList", transfertoList);
			}
		}

		return transfertoList;
	}

	public void ajaxActionGetServiceRequestQueryTypeByServiceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetServiceQueryTypeByServiceType::Start=======>");
		String serviceType=request.getParameter(ComplaintsCommonConstants.SERVICE_TYPE);

		java.io.PrintWriter out=null;
		String result="";
		Map<String,String> queryTypeMap=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			queryTypeMap = getServiceRequestQueryType(request, serviceType);
			if(queryTypeMap!=null){
				result=queryTypeMap.toString();
			}

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetServiceQueryTypeByServiceType ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetServiceQueryTypeByServiceType ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionGetServiceQueryTypeByServiceType ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId::end=======>");
	}
	public void ajaxActionToGetAllBranches(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionToPopulateAllBranches::Start=======>");

		java.io.PrintWriter out=null;
		String result="";
		Map<Integer,String> queryTypeMap=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			queryTypeMap=getAllBranchList(request);
			if(queryTypeMap!=null){
				result=queryTypeMap.toString();
			}

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionToPopulateAllBranches ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionToPopulateAllBranches ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionToPopulateAllBranches ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId::end=======>");
	}

	public void ajaxActionToGetAllCities(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("AbstractComplaintsAction::ajaxActionToGetAllCities::Start=======>");

		java.io.PrintWriter out=null;
		String result="";
		Map<Integer,String> queryTypeMap=null;

		complaintsCommonService = getComplaintCommonService();
		try {
			out=response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			response.setContentType(FrameworkConstants.MIME_TYPE_TEXT_JAVA_SCRIPT);

			queryTypeMap=getAllCities(request);
			if(queryTypeMap!=null){
				result=queryTypeMap.toString();
			}

		}catch(CGBusinessException bexp){
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,bexp));
			LOGGER.error("AbstractComplaintsAction::ajaxActionToGetAllCities ..CGSystemException :"+bexp.getMessage());
		}catch (CGSystemException e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionToGetAllCities ..CGSystemException :",e);
			result= prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (Exception e) {
			LOGGER.error("AbstractComplaintsAction::ajaxActionToGetAllCities ..Exception :",e);
			String exception=getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,exception);
		}

		finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractComplaintsAction::ajaxActionGetEmpToDtslByEmpId::end=======>");
	}





	private Map<String, String> getServiceRequestQueryType(
			HttpServletRequest request, String serviceType)
					throws CGBusinessException, CGSystemException {
		Map<String, String> queryTypeMap;
		queryTypeMap=(Map<String,String>) request.getSession().getAttribute(serviceType);
		if(CGCollectionUtils.isEmpty(queryTypeMap)){
			List<ServiceRequestQueryTypeTO> queryTypeList=complaintsCommonService.getServiceRequestQueryTypeByServiceType(serviceType);
			if(!CGCollectionUtils.isEmpty(queryTypeList)){
				queryTypeMap = getServiceQueryTypeMap(queryTypeList);
				queryTypeMap=CGCollectionUtils.sortByValue(queryTypeMap);
				request.getSession(false).setAttribute(serviceType, queryTypeMap);
			}
		}
		return queryTypeMap;
	}
	
	/**
	 * To get STD Type by Name and set result list to request scope
	 * 
	 * @param request
	 * @param stdTypeName
	 * @param requestParam
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	public void getStdTypeAndSetToRequest(HttpServletRequest request,
			String stdTypeName, String requestParam)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("AbstractComplaintsAction :: getStdTypeAndSetToRequest() :: START");
		HttpSession session = request.getSession(Boolean.FALSE);
		complaintsCommonService = getComplaintCommonService();
		/** To set claim complaint status - drop down. Added by Himal */
		List<StockStandardTypeTO> stdTypeList = (List<StockStandardTypeTO>) session
				.getAttribute(requestParam);
		if (CGCollectionUtils.isEmpty(stdTypeList)) {
			stdTypeList = complaintsCommonService
					.getStandardTypesByTypeName(stdTypeName);
			session.setAttribute(requestParam, stdTypeList);
		}
		request.setAttribute(requestParam, stdTypeList);
		LOGGER.trace("AbstractComplaintsAction :: getStdTypeAndSetToRequest() :: END");
	}
	
}
