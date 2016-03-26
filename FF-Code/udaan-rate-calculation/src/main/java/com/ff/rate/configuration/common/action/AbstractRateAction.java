package com.ff.rate.configuration.common.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.geography.ZoneTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateProductCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateVobSlabsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;

public class AbstractRateAction   extends CGBaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRateAction.class);
	public RateCommonService rateCommonService;
	public transient JSONSerializer serializer;	
	
	
	
	/** 
     * Get RateIndustryCategory List
     * This method will return  RateIndustryCategory List 
     * @inputparam 
     * @return  	RateIndustryCategoryTO List<RateIndustryCategoryTO>
     * @author      Rohini  Maladi  
     */
	
	@SuppressWarnings("unchecked")
	public List<RateIndustryCategoryTO> getRateIndustryCategoryList(
			HttpServletRequest request, String applicable)
			throws CGBusinessException, CGSystemException {
	
		List<RateIndustryCategoryTO> industryCategoryToList = null;
		HttpSession session=request.getSession(false);
		String listElement = null;
			if(applicable == RateCommonConstants.RATE_BENCH_MARK){
				listElement = RateCommonConstants.RATE_BENCH_MARK_IND_CAT_LIST;
			}else if(applicable == RateCommonConstants.RATE_QUOTATION){
				listElement = RateCommonConstants.RATE_QUOT_IND_CAT_LIST;
			}
			industryCategoryToList = new ArrayList<RateIndustryCategoryTO>();
			industryCategoryToList = (List<RateIndustryCategoryTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(industryCategoryToList)) {
				rateCommonService = getRateCommonService();
				industryCategoryToList  = rateCommonService.getRateIndustryCategoryList(applicable);
			if(!CGCollectionUtils.isEmpty(industryCategoryToList)){
				session.setAttribute(listElement, industryCategoryToList);
				}
			}
			return industryCategoryToList;
		}

	/**
	 * Get RateProductCategory List
	 * @inputparam 
     * @return  	RateProductCategoryTO List<RateProductCategoryTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateProductCategoryTO> getRateProductCategoryList(
			HttpServletRequest request, String module, String type)
			throws CGBusinessException, CGSystemException {
	
	
		
		List<RateProductCategoryTO> prodCatList = null;
		HttpSession session=request.getSession(false);
		String listElement = "";
			if(module == RateCommonConstants.RATE_BENCH_MARK){
				listElement = RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_LIST;
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type == RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N)){
				listElement = RateCommonConstants.RATE_QUOT_PROD_CAT_N_LIST;
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type == RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E)){
				listElement = RateCommonConstants.RATE_QUOT_PROD_CAT_E_LIST;
			}
			prodCatList = (List<RateProductCategoryTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(prodCatList)) {
				rateCommonService = getRateCommonService();
			prodCatList = rateCommonService.getRateProductCategoryList(type);
			RateProductCategoryTO prodTO = new RateProductCategoryTO();
			for(RateProductCategoryTO prodCatTO : prodCatList){
				if(prodCatTO.getRateProductCategoryCode().equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_CODE)){
					prodTO = prodCatTO;
				}
			}
			if(module == RateCommonConstants.RATE_BENCH_MARK || type == RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N){
			prodCatList.remove(prodTO);
			prodCatList.add(0,prodTO);
			}
			
			if(!CGCollectionUtils.isEmpty(prodCatList)){
				session.setAttribute(listElement, prodCatList);
			}
			}
			return prodCatList;

			
		}	
	
	
	
	/**
	 * Get RateCustomerCategory List
	 * 
	 * @inputparam
	 * @return RateCustomerCategoryTO List<RateCustomerCategoryTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateCustomerCategoryTO> getRateCustomerCategoryList(
			HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		List<RateCustomerCategoryTO> custCatList = null;
		HttpSession session = request.getSession(false);
		custCatList = (List<RateCustomerCategoryTO>) session
				.getAttribute(RateCommonConstants.RATE_CUSTOMER_CATEGORY_LIST);
		
		if (CGCollectionUtils.isEmpty(custCatList)) {
			rateCommonService = getRateCommonService();
			custCatList = rateCommonService.getRateCustomerCategoryList();
			if (!CGCollectionUtils.isEmpty(custCatList)) {
				session.setAttribute(
						RateCommonConstants.RATE_CUSTOMER_CATEGORY_LIST,
						custCatList);
			} else {
				
				ExceptionUtil
						.prepareBusinessException(RateErrorConstants.CUSTOMER_CATEGORY_DTLS_NOT_EXIST);
			}
		}
		return custCatList;

	}
	
	/**
	 * Get RateMinChargeableWeight List
	 * @inputparam 
     * @return  	RateMinChargeableWeightTO List<RateMinChargeableWeightTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateMinChargeableWeightTO> getRateMinChargeWtList(
			HttpServletRequest request, String module, String type,
			String custCode) throws CGBusinessException, CGSystemException {
	
	
		
		List<RateMinChargeableWeightTO> minChrgWtList = null;
		HttpSession session=request.getSession(false);	
		String listElement = "";
		
			if(module == RateCommonConstants.RATE_BENCH_MARK){
				listElement = RateCommonConstants.RATE_BENCH_MARK_MIN_CHAG_WT_LIST;
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_MIN_CHAG_WT_LIST_N_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_MIN_CHAG_WT_LIST_N_FR;
				}
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_MIN_CHAG_WT_LIST_E_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_MIN_CHAG_WT_LIST_E_FR;
				}
			}				
			minChrgWtList = (List<RateMinChargeableWeightTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(minChrgWtList)) {
				rateCommonService = getRateCommonService();
			minChrgWtList = rateCommonService.getRateMinChrgWtList(type,custCode);
			if(!CGCollectionUtils.isEmpty(minChrgWtList)){
				session.setAttribute(listElement, minChrgWtList);
			}
			}
			
			return minChrgWtList;
	}
	
	
			
	/**
	 * Get RateVobSlabs List
	 * @inputparam 
     * @return  	RateVobSlabsTO List<RateVobSlabsTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateVobSlabsTO> getRateVobSlabsList(HttpServletRequest request,
			String module, String type, String custCode)
			throws CGBusinessException, CGSystemException {
		
		List<RateVobSlabsTO> vobSlabsList = null;
		HttpSession session=request.getSession(false);	
		String listElement = "";
		if (module == RateCommonConstants.RATE_BENCH_MARK){
			listElement = RateCommonConstants.RATE_BENCH_MARK_VOB_SLAB_LIST;
		}else if (module == RateCommonConstants.RATE_QUOTATION
				&& (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N))) {
			if (custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
				listElement = RateCommonConstants.RATE_QUOT_VOB_SLAB_LIST_N_CO;
			}else if (custCode.equals(RateCommonConstants.RATE_CUST_FR)){
				listElement = RateCommonConstants.RATE_QUOT_VOB_SLAB_LIST_N_FR;
			}
		} else if (module == RateCommonConstants.RATE_QUOTATION
				&& (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E))) {
			if (custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
				listElement = RateCommonConstants.RATE_QUOT_VOB_SLAB_LIST_E_CO;
			}
			else if (custCode.equals(RateCommonConstants.RATE_CUST_FR)){
				listElement = RateCommonConstants.RATE_QUOT_VOB_SLAB_LIST_E_FR;
			}
		}

		vobSlabsList = (List<RateVobSlabsTO>) session.getAttribute(listElement);
		if (CGCollectionUtils.isEmpty(vobSlabsList)) {
			rateCommonService = getRateCommonService();
			vobSlabsList = rateCommonService
					.getRateVobSlabsList(type, custCode);
			if (!CGCollectionUtils.isEmpty(vobSlabsList)) {
				session.setAttribute(listElement, vobSlabsList);
			}
		}

		return vobSlabsList;
	}
	
	/**
	 * Get RateWeightSlabs List
	 * @inputparam 
     * @return  	RateWeightSlabsTO List<RateWeightSlabsTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateWeightSlabsTO> getRateWeightSlabsList(
			HttpServletRequest request, String module, String type,
			String custCode) throws CGBusinessException, CGSystemException {

		List<RateWeightSlabsTO> wtSlabList = null;
		HttpSession session = request.getSession(false);
		String listElement = "";
		if (module == RateCommonConstants.RATE_BENCH_MARK){
			listElement = RateCommonConstants.RATE_BENCH_MARK_WT_SLAB_LIST;
		}else if (module == RateCommonConstants.RATE_QUOTATION
				&& (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N))) {
			if (custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
				listElement = RateCommonConstants.RATE_QUOT_WT_SLAB_LIST_N_CO;
			}else if (custCode.equals(RateCommonConstants.RATE_CUST_FR)){
				listElement = RateCommonConstants.RATE_QUOT_WT_SLAB_LIST_N_FR;
			}
		} else if (module == RateCommonConstants.RATE_QUOTATION
				&& (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E))) {
			if (custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
				listElement = RateCommonConstants.RATE_QUOT_WT_SLAB_LIST_E_CO;
			}else if (custCode.equals(RateCommonConstants.RATE_CUST_FR)){
				listElement = RateCommonConstants.RATE_QUOT_WT_SLAB_LIST_E_FR;
			}
		}

		wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(listElement);
		if (CGCollectionUtils.isEmpty(wtSlabList)) {
			rateCommonService = getRateCommonService();
			wtSlabList = rateCommonService.getRateWeightSlabsList(type,
					custCode);
			if (!CGCollectionUtils.isEmpty(wtSlabList)) {
				session.setAttribute(listElement, wtSlabList);
			}
		}
		return wtSlabList;

	}
	
	/**
	 * Get RateSectors List
	 * @inputparam 
     * @return  	RateSectorsTO List<RateSectorsTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateSectorsTO> getRateSectorsList(HttpServletRequest request,
			String module, String type, String custCode)
			throws CGBusinessException, CGSystemException {
		
		List<RateSectorsTO> sectorsList = null;
		HttpSession session=request.getSession(false);	
		String listElement = CommonConstants.EMPTY_STRING;
			if(module == RateCommonConstants.RATE_BENCH_MARK){
				listElement = RateCommonConstants.RATE_BENCH_MARK_SECTOR_LIST;
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_N_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_N_FR;
				}
			} else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_E_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_E_FR;
				}
			} else if (module == RateCommonConstants.CASH_RATE_CONFIG) {
				if(type.equals(RateCommonConstants.RATE_PRO_CAT_TYPE_N)) {
					listElement = RateCommonConstants.PARAM_ORIGIN_SECTOR_LIST;
				}
			}
			
			sectorsList = (List<RateSectorsTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(sectorsList)) {
				rateCommonService = getRateCommonService();
				sectorsList = rateCommonService
						.getRateSectorsList(type, custCode);
				if(!CGCollectionUtils.isEmpty(sectorsList)){
					session.setAttribute(listElement, sectorsList);
				}
			}
		return sectorsList;
	}
	
	/**
	 * Get RateSectors List
	 * @inputparam 
     * @return  	RateSectorsTO List<RateSectorsTO>
	 */
	@SuppressWarnings("unchecked")
	public List<RateSectorsTO> getRateConfigSectorsList(
			HttpServletRequest request, String module, String type,
			String custCode) throws CGBusinessException, CGSystemException {
		List<RateSectorsTO> sectorsList = null;
		HttpSession session=request.getSession(false);	
		String listElement = "";
			if(module == RateCommonConstants.RATE_BENCH_MARK){
				listElement = RateCommonConstants.RATE_BENCH_MARK_SECTOR_LIST;
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_N))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_N_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_N_FR;
				}
			}else if (module == RateCommonConstants.RATE_QUOTATION && (type.equals(RateCommonConstants.RATE_BENCH_MARK_PROD_CAT_TYPE_E))){
				if(custCode.equals(RateCommonConstants.RATE_CUST_CRDT)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_E_CO;
				}else if(custCode.equals(RateCommonConstants.RATE_CUST_FR)){
					listElement = RateCommonConstants.RATE_QUOT_SECTOR_LIST_E_FR;
				}
			}
			
			sectorsList = (List<RateSectorsTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(sectorsList)) {
			rateCommonService = getRateCommonService();
			sectorsList = rateCommonService.getRateConfigSectorsList(type,custCode);
			if(!CGCollectionUtils.isEmpty(sectorsList)){
				session.setAttribute(listElement, sectorsList);
			}
			}
			
			return sectorsList;
	}
	
	/**
	 * Get Employee Details by Employee Code
	 * @inputparam 
     * @return  	EmployeeTO Object as JSON object
	 */		
	
	@SuppressWarnings("static-access")
	public void getEmpDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		String jsonResult = FrameworkConstants.EMPTY_STRING;
		PrintWriter out = null;
		String empCode = FrameworkConstants.EMPTY_STRING;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			empCode = request.getParameter("empCode");

			rateCommonService = getRateCommonService();
			EmployeeTO empTO = rateCommonService.getEmployeeDetails(empCode);
			
			if (!StringUtil.isNull(empTO)){
				jsonResult = serializer.toJSON(empTO).toString();
			}else{
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								RateErrorConstants.EMP_DETAILS_NOT_FOUND, null));
			}
		}catch (CGSystemException e) {
			LOGGER.error("Exception happened in getEmpDetails of AbstractRateAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,getSystemExceptionMessage(request,e));
		}catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getEmpDetails of AbstractRateAction..."+e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG , getBusinessErrorFromWrapper(request,e));
		}catch (Exception e) {
			LOGGER.error("Exception happened in getEmpDetails of AbstractRateAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * @return rateCommonService
	 */
	public RateCommonService getRateCommonService()	{
		if(StringUtil.isNull(rateCommonService)) {
			rateCommonService = (RateCommonService)getBean(RateSpringConstants.RATE_COMMON_SERVICE);
		}
		return rateCommonService;
	}

	/**
	 * To Get Standard Type
	 * 
	 * @param type
	 * @return stockStandardTypeTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @return List of standard type
	 * @author hkansagr
	 */
	@SuppressWarnings("unchecked")
	public void getStandardType(HttpServletRequest request, String type, String reqAttribute){
		
		HttpSession session = null;
		ActionMessage actionMessage = null;
		try{
			session = request.getSession(Boolean.FALSE);
			List<StockStandardTypeTO> stdrdTypeTOList = (List<StockStandardTypeTO>) session
					.getAttribute(reqAttribute);
			
			if (CGCollectionUtils.isEmpty(stdrdTypeTOList)) {
				rateCommonService=getRateCommonService();
				stdrdTypeTOList = rateCommonService.getStandardType(type);

				if (!CGCollectionUtils.isEmpty(stdrdTypeTOList)) {
					session.setAttribute(reqAttribute, stdrdTypeTOList);
					request.setAttribute(reqAttribute, stdrdTypeTOList);
				}else{
					actionMessage = new ActionMessage(
							RateErrorConstants.DETAILS_DOES_NOT_EXIST,
							RateErrorConstants.STANDARD_TYPE_NOT_EXIST);
					LOGGER.warn("AbstractRateAction:: getStandardType :: "+type+" Details Does not exist");
				}
			} else {
				request.setAttribute(reqAttribute, stdrdTypeTOList);
			}
			}catch(CGBusinessException e){
				LOGGER.error("Exception happened in getStandardType of AbstractRateAction..."+e.getMessage());
				getBusinessError(request, e);
			}catch(CGSystemException e){
				LOGGER.error("Exception happened in getStandardType of AbstractRateAction..."+e.getMessage());
				getSystemException(request, e);
			}catch(Exception e){
				LOGGER.error("Exception happened in getStandardType of AbstractRateAction..."+e.getMessage());
				String exception = getGenericExceptionMessage(request, e);
				actionMessage =  new ActionMessage(exception);
			}finally{
				prepareActionMessage(request, actionMessage);
				
			} 
	}
	
	/**
	 * To get pickup branch(s) by pin code 
	 * (Rate Contract - Pickup Details Normal & ECommerce)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return List of Office as JSON Object
	 * @author hkansagr
	 */
	@SuppressWarnings("static-access")
	public void getPickupBranchsByPincode(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response){
		LOGGER.debug("AbstractRateAction::getPickupBranchByPincode()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			String pincode = request.getParameter(RateContractConstants.PARAM_PINCODE);
			List<OfficeTO> offices = rateCommonService.getPickupBranchsByPincode(pincode);
			if (!StringUtil.isEmptyList(offices)){
				jsonResult = serializer.toJSON(offices).toString();
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,RateErrorConstants.BRANCHES_NOT_FOUND,null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("RateQuotationAction::getRateQuotationListViewDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRateAction::getPickupBranchByPincode()::END");
	}
	
	/**
	 * To block or unblock customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void blockOrUnblockCustomer(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("AbstractRateAction::blockOrUnblockCustomer()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		/*ResourceBundle messages = ResourceBundle
				.getBundle(RateCommonConstants.RATE_MESSAGES_RESOURCE_FILE);*/
		try {
			out = response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			String custId = request.getParameter(RateCommonConstants.PARAM_CUSTOMER_ID);
			Integer customerId = Integer.parseInt(custId);
			String status = request.getParameter(RateCommonConstants.PARAM_STATUS);
			String rateConId = request.getParameter(RateCommonConstants.PARAM_RATE_CONTRACT_ID);
			Integer rateContractId = Integer.parseInt(rateConId);
			rateCommonService = getRateCommonService();
			boolean result = rateCommonService.blockOrUnblockCustomer(customerId, status);
			boolean contractResult = Boolean.FALSE;
			if(result){
				RateContractTO rateTO=new RateContractTO();
				if(StringUtil.equals(status, RateContractConstants.ACTIVE)){//A
					rateTO.setCustomerStatus(RateContractConstants.ACTIVE);
					rateTO.setRateContractId(rateContractId);
					/* TO update contract status to --> A-Active */
					rateTO.setContractStatus(RateCommonConstants
							.CONTRACT_STATUS_ACTIVE);
					rateCommonService.updateContractStatus(rateTO);
					jsonResult = serializer.toJSON(rateTO).toString();
					//jsonResult = messages.getString(RateContractConstants.CUST_UNBLOCKED);
				} else if(StringUtil.equals(status, RateContractConstants.INACTIVE)) {//I
					rateTO.setCustomerStatus(RateContractConstants.INACTIVE);
					rateTO.setRateContractId(rateContractId);
					/* To update contract status to --> B-Blocked */
					rateTO.setContractStatus(RateCommonConstants
							.CONTRACT_STATUS_BLOCKED);
					contractResult = rateCommonService.updateContractStatus(rateTO);
					if(contractResult){
						sendEmailForBlockCustomer(rateTO);
					}
					jsonResult = serializer.toJSON(rateTO).toString();
					//jsonResult = messages.getString(RateContractConstants.CUST_BLOCKED);
				} 
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,RateErrorConstants.CUSTOMER_NOT_BLOCK_UNBLOCK,null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRateAction::blockOrUnblockCustomer()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("AbstractRateAction::blockOrUnblockCustomer()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("AbstractRateAction::blockOrUnblockCustomer()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("AbstractRateAction::blockOrUnblockCustomer()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getCityByPincode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		String jsonResult = null;
		PrintWriter out = null;
		String pincode = null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			pincode = request.getParameter("pincode");

			rateCommonService = getRateCommonService();
			CityTO cityTO = new CityTO(); 
					cityTO = getCityTOByPincode(pincode);
			
			if (!StringUtil.isNull(cityTO) && !StringUtil.isEmptyInteger(cityTO.getCityId())){
				jsonResult = serializer.toJSON(cityTO).toString();
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,RateErrorConstants.CITY_DETAILS_NOT_FOUND,null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRateAction::getCityByPincode()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("AbstractRateAction::getCityByPincode()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("AbstractRateAction::getCityByPincode()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
			
			
		}
	
	/**
	 * @param pincode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CityTO getCityTOByPincode(String pincode) throws CGBusinessException, CGSystemException{
		return rateCommonService.getCityByPincode(pincode);
	}
	
	/**
	 * Name : getLoginUserTO
	 * purpose : to get UserTO from Session
	 * Input : HttpServletRequest request
	 * return : UserTO.
	 *
	 * @param request the request
	 * @return the login user to
	 */
	public UserTO getLoginUserTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			return userInfo.getUserto();
		}
		return null;
	}
	
	/**
	 * Name : getLoginUserInfoTO
	 * purpose : to get UserInfoTO from Session
	 * Input : HttpServletRequest request
	 * return : UserInfoTO.
	 *
	 * @param request the request
	 * @return the login user info to
	 */
	public UserInfoTO getLoginUserInfoTO(HttpServletRequest request) {
		HttpSession session =request.getSession(Boolean.FALSE);
		return (UserInfoTO)session.getAttribute(RateQuotationConstants.USER_INFO);		
	}
	
	/**
	 * Name : getLoginEmpTO
	 * purpose : to get EmployeeTO from Session
	 * Input : HttpServletRequest request
	 * return : EmployeeTO.
	 *
	 * @param request the request
	 * @return the login emp to
	 */
	public EmployeeTO getLoginEmpTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			EmployeeUserTO empUserTo = userInfo.getEmpUserTo();
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
	 * return : OfficeTO.
	 *
	 * @param request the request
	 * @return the login office to
	 */
	public OfficeTO getLoginOfficeTO(HttpServletRequest request){
		UserInfoTO userInfo = getLoginUserInfoTO(request);
		if(userInfo!=null){
			return  userInfo.getOfficeTo();
		}
		return null;
	}
	
	/**
	 * Name : getLoginRegionTO
	 * purpose : to get RegionTO from Session
	 * Input : HttpServletRequest request
	 * return : RegionTO.
	 *
	 * @param request the request
	 * @return the login region to
	 */
	public RegionTO getLoginRegionTO(HttpServletRequest request){
		OfficeTO officeTo = getLoginOfficeTO(request);
		if(officeTo!=null){
			return  officeTo.getRegionTO();
		}
		return null;
	}
	
	/**
	 * Name : isEmployeeLoggedIn
	 * purpose : to verify whether logged-in code is Of employee from Session
	 * Input : HttpServletRequest request
	 * return : Boolean.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	public Boolean isEmployeeLoggedIn(HttpServletRequest request){
		EmployeeTO employeeTo=getLoginEmpTO(request);
		if(!StringUtil.isNull(employeeTo)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public List<OfficeTO> getLoginUserRHOOffice(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		
		List<OfficeTO> officeTOList = null;
		UserTO userTO = getLoginUserTO(request);
		
		rateCommonService = getRateCommonService();
		officeTOList = rateCommonService.getRHOOfficesByUserId(userTO.getUserId());
		
		return officeTOList;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public String getUserZoneCode(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		
		ZoneTO zoneTO = null;
		HttpSession session=request.getSession(false);
		String zoneCode = "";
			zoneTO = new ZoneTO();
			zoneCode = (String) session.getAttribute(RateCommonConstants.USER_ZONE_CODE);
			if(StringUtil.isNull(zoneCode)) {
				rateCommonService = getRateCommonService();
				zoneTO  = rateCommonService.getZoneByZoneId(getLoginRegionTO(request).getZone());
				
			if(!StringUtil.isNull(zoneTO) && !StringUtil.isNull(zoneTO.getZoneCode())){
				zoneCode = zoneTO.getZoneCode();
				session.setAttribute(RateCommonConstants.USER_ZONE_CODE, zoneCode);
				}else{
					ExceptionUtil.prepareBusinessException(RateErrorConstants.USER_ZONE_CODE_NOT_FOUND);
				}
			}
			
			return zoneCode;
	}
	
	/**
	 * @param ofcList
	 * @return
	 */
	public List<CityTO> getCityListOfReportedOffices(Integer ofcList)
			throws CGBusinessException, CGSystemException {
	
		rateCommonService = getRateCommonService();
		
		return rateCommonService.getCityListOfReportedOffices(ofcList);
		
	}
	
	
	public List<CityTO> getCityListOfReportedOfficesOfRHO(List<OfficeTO> ofcList)
			throws CGBusinessException, CGSystemException {
		
		rateCommonService = getRateCommonService();
		
		return rateCommonService.getCityListOfReportedOfficesOfRHO(ofcList);
		
	}
	
	
	/**
	 * @param userId
	 * @return
	 */
	public List<CityTO> getCityListOfAssignedOffices(Integer userId)
			throws CGBusinessException, CGSystemException {
		
		rateCommonService = getRateCommonService();		
		return rateCommonService.getCityListOfAssignedOffices(userId);
		
	
	}

	/**
	 * @param empId
	 * @return
	 */
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException {
		
		OfficeTO ofcTO = null;
		
		rateCommonService = getRateCommonService();
		ofcTO = rateCommonService.getOfficeByempId(empId);
		return ofcTO;
	}
	
	/**
	 * @param pincodeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PincodeTO validatePincode(PincodeTO pincodeTO)
			throws CGBusinessException, CGSystemException {
		rateCommonService = getRateCommonService();
		return rateCommonService.validatePincode(pincodeTO);
	}
	
	public BookingTypeConfigTO getBookingTypeConfigVWDeno()
			throws CGSystemException, CGBusinessException {
		rateCommonService = getRateCommonService();
		return rateCommonService.getBookingTypeConfigVWDeno();
	}
	
	@SuppressWarnings("unchecked")
	public List<InsuredByTO> getRiskSurchargeInsuredBy(
			HttpServletRequest request) throws CGSystemException,
			CGBusinessException {
		
		List<InsuredByTO> insuredByTOList = null;
		HttpSession session=request.getSession(false);
		String riskSurChrgInsuredBy = "RISKINSURED";
			insuredByTOList = (List<InsuredByTO>)session.getAttribute(riskSurChrgInsuredBy);
			if(CGCollectionUtils.isEmpty(insuredByTOList)) {
				rateCommonService = getRateCommonService();
				insuredByTOList  = rateCommonService.getRiskSurchargeInsuredBy();
			if(!CGCollectionUtils.isEmpty(insuredByTOList)){
				session.setAttribute(riskSurChrgInsuredBy, insuredByTOList);
				}
			}
			return insuredByTOList;
	}
	
	public String getSectorCodeByRegion(HttpServletRequest request,
			Integer regionId) throws CGBusinessException, CGSystemException {
		
		SectorTO sectorTO = null;
		HttpSession session = request.getSession(false);
		String sectorCode = "";
		sectorTO = new SectorTO();
		sectorCode = (String) session
				.getAttribute(RateCommonConstants.BA_SECTOR_CODE);
		if (StringUtil.isNull(sectorCode)) {
			rateCommonService = getRateCommonService();
			sectorTO = rateCommonService.getSectorByRegionId(regionId);

			if (!StringUtil.isNull(sectorTO) && !StringUtil.isNull(sectorTO.getSectorCode())) {
				sectorCode = sectorTO.getSectorCode();
				session.setAttribute(RateCommonConstants.BA_SECTOR_CODE,
						sectorCode);
			}else{
					ExceptionUtil.prepareBusinessException(RateErrorConstants.USER_ZONE_CODE_NOT_FOUND);
				}
		}
		return sectorCode;
	}
	
	@SuppressWarnings("unchecked")
	public List<OfficeTO> getAllRegionalOffices(HttpServletRequest request) throws CGBusinessException, CGSystemException {
		
		
		List<OfficeTO> rhoOfficeList = null;
		HttpSession session=request.getSession(false);
		String listElement = "rhoOfcList";
			
			rhoOfficeList = (List<OfficeTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(rhoOfficeList)) {
				rateCommonService = getRateCommonService();
				rhoOfficeList = rateCommonService.getAllRegionalOffices();
				if(!CGCollectionUtils.isEmpty(rhoOfficeList)){
					session.setAttribute(listElement, rhoOfficeList);
				}
			}
			return rhoOfficeList;
	}
	
	@SuppressWarnings("static-access")
	public void getAllCities(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
			
			
		String jsonResult = null;
		PrintWriter out = null;
		Integer rhoOfcId = null;
		List<CityTO> cityList = null;
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			if(!StringUtil.isStringEmpty(request.getParameter("rhoOfcId"))){
				rhoOfcId = Integer.parseInt(request.getParameter("rhoOfcId"));
			}

			cityList = getAllCities(rhoOfcId);
			if (!CGCollectionUtils.isEmpty(cityList)){
				jsonResult = serializer.toJSON(cityList).toString();
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,RateErrorConstants.CITY_DETAILS_NOT_FOUND,null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		}
	 
	public List<CityTO> getAllCities(Integer rhoOfcid) throws CGBusinessException, CGSystemException{
		
		rateCommonService = getRateCommonService();
		return rateCommonService.getCityListOfReportedOffices(rhoOfcid);
	}
	
	public String getOfficeByofficeId(Integer rhoOfcid) throws CGBusinessException, CGSystemException{
		
		OfficeTO ofcTo = null;
		String ofcName = FrameworkConstants.EMPTY_STRING;
		rateCommonService = getRateCommonService();
		 ofcTo = rateCommonService.getOfficeDetails(rhoOfcid);
		 if(!StringUtil.isNull(ofcTo)){
			 ofcName = ofcTo.getOfficeName();
		 }
		return ofcName;
	}
	
	public OfficeTO getOfficeByUserId(Integer userId)  throws CGBusinessException, CGSystemException{
		OfficeTO ofcTo = null;
		rateCommonService = getRateCommonService();
		 ofcTo = rateCommonService. getOfficeByUserId(userId);
		
		return ofcTo;
	}

	private void sendEmailForBlockCustomer(RateContractTO rateTO) throws CGSystemException, CGBusinessException{
		
		if(!StringUtil.isEmptyInteger(rateTO.getRateContractId())){
			rateCommonService = getRateCommonService();
			rateCommonService.sendEmailForBlockContractCustomer(rateTO);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<StateTO> getStatesList(HttpServletRequest request) throws CGBusinessException, CGSystemException {
		List<StateTO> statesList = null;
		HttpSession session=request.getSession(false);	
		String listElement = "statesList";
	
			
		statesList = (List<StateTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(statesList)) {
			rateCommonService = getRateCommonService();
			statesList = rateCommonService.getStatesList();
			if(!CGCollectionUtils.isEmpty(statesList)){
				session.setAttribute(listElement, statesList);
			}
			}
			
			return statesList;
	}
	
	@SuppressWarnings({ "static-access" })
	public void getCityListByStateId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException, CGSystemException {
		List<CityTO> cityList = null;
		Integer stateId = null;
		String jsonResult = null;
		PrintWriter out = null;
		
		try {
			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
		
			stateId = Integer.parseInt(request.getParameter("stateId"));
			rateCommonService = getRateCommonService();
			cityList = rateCommonService.getCityListByStateId(stateId);
			
			if (!CGCollectionUtils.isEmpty(cityList)){
				jsonResult = serializer.toJSON(cityList).toString();
			}else{
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,RateErrorConstants.CITY_DETAILS_NOT_FOUND,null));
			}
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("AbstractRateAction::getAllCities()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CodChargeTO> getDeclaredValueCodCharge(HttpServletRequest request, String configureType, String codType)
			throws CGBusinessException, CGSystemException{
		List<CodChargeTO> codList = null;
		HttpSession session=request.getSession(false);	
		String listElement = codType;
	
			
		codList = (List<CodChargeTO>)session.getAttribute(listElement);
			if(CGCollectionUtils.isEmpty(codList)) {
			rateCommonService = getRateCommonService();
			codList = rateCommonService.getDeclaredValueCodCharge(configureType);
			if(!CGCollectionUtils.isEmpty(codList)){
				session.setAttribute(listElement, codList);
			}
			}
			
			return codList;
	}
	
	public List<OfficeTO> getAllOfficesByCity(Integer loginCityId)
			throws CGBusinessException, CGSystemException {
		rateCommonService = getRateCommonService();
		return rateCommonService.getAllOfficesByCity(loginCityId);
	}
	
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		rateCommonService = getRateCommonService();
		return rateCommonService.getEmployeesOfOffice(officeTO);
	}
	
	public void compositePrintUrl(HttpServletRequest request){
		HttpSession session = (HttpSession) request.getSession(false);
		
		String reportURL = "contractPrintUrl";
		String contractPrintUrl = null;
		
		contractPrintUrl = (String)session.getAttribute(reportURL);
		
		if(StringUtil.isStringEmpty(contractPrintUrl)){
			UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(RateQuotationConstants.USER_INFO);
			contractPrintUrl = composeReportUrl(userInfoTO.getConfigurableParams());
		}	
		
		request.setAttribute("contractPrintUrl",contractPrintUrl);

	}
	
	public Map<String, String> getConfigParams(final HttpServletRequest request){
		UserInfoTO uinforTo= getLoginUserInfoTO(request);
		if(!StringUtil.isNull(uinforTo)&& !CGCollectionUtils.isEmpty(uinforTo.getConfigurableParams())){
			configurableParams=uinforTo.getConfigurableParams();
		}
		return configurableParams;
	}
	
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
	
	public List<OfficeTO> getAllOfficesByCityAndOfcType(Integer cityId, String ofcTypeCode)
			throws CGBusinessException, CGSystemException {
		rateCommonService = getRateCommonService();
		return rateCommonService.getAllOfficesByCityAndOfcTypeCode(cityId, ofcTypeCode);
	}
}

