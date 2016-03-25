package com.ff.admin.master.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.billing.action.InvoiceRunSheetPrintingAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.master.constants.PincodeMasterConstants;
import com.ff.admin.master.constants.PincodeMasterErrorConstants;
import com.ff.admin.master.form.PinCodeMasterForm;
import com.ff.admin.master.service.PincodeMasterService;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.master.PinCodeMasterTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ProductGroupServiceabilityTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

public class PincodemasterAction extends CGBaseAction {
	/** The Pincode master service. */
	private PincodeMasterService pincodeMasterService;

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InvoiceRunSheetPrintingAction.class);
	
	public transient JSONSerializer serializer;

	/**
	 * Prepare page.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("PincodemasterAction::preparePage::START------------>:::::::");

		pincodeMasterService = (PincodeMasterService) getBean(AdminSpringConstants.PINCODE_MASTER_SERVICE);

		try {
			setRegion(request);

			setProductList(request);
			
			setPaperWorkList(request);
			
			setAllCities(request);

		} catch (CGBusinessException e) {
			LOGGER.error("PincodemasterAction::preparePage ..CGBusinessException :"
					+ e);
		} catch (CGSystemException e) {
			LOGGER.error("PincodemasterAction::preparePage ..CGSystemException :"
					+ e);
		}

		LOGGER.debug("PincodemasterAction::preparePage::END------------>:::::::");
		return mapping.findForward(PincodeMasterConstants.SUCCESS);
	}

	private void setProductList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PincodemasterAction::setProductList::START------------>:::::::");
		List<ProductTO> productTO = pincodeMasterService.getAllProduct();
		if (CGCollectionUtils.isEmpty(productTO)) {
			prepareActionMessage(request, new ActionMessage(
					PincodeMasterErrorConstants.PRODUCT_DTLS_NOT_EXIST));
			LOGGER.warn("PincodemasterAction:: setRegion :: Region Details Does not exist");
		}
		List<ProductGroupServiceabilityTO> productGroupServiceabilityTOs = pincodeMasterService
				.getAllProductGroup();
		if (CGCollectionUtils.isEmpty(productGroupServiceabilityTOs)) {
			prepareActionMessage(request, new ActionMessage(
					PincodeMasterErrorConstants.PRODUCT_GROUP_DTLS_NOT_EXIST));
			LOGGER.warn("PincodemasterAction:: setRegion :: Region Details Does not exist");
		}

		List<LabelValueBean> gropupName = new ArrayList<LabelValueBean>();
		prepareGroupName(gropupName, productTO, productGroupServiceabilityTOs);
		request.setAttribute("gropupName", gropupName);
		LOGGER.debug("PincodemasterAction::setProductList::END------------>:::::::");
	}

	private void setRegion(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		
		LOGGER.debug("PincodemasterAction::setRegion::START------------>:::::::");
		List<RegionTO> regionTo = pincodeMasterService.getRegions();
		if (!CGCollectionUtils.isEmpty(regionTo)) {
			request.setAttribute(PincodeMasterConstants.REGION_TO, regionTo);
		} else {
			prepareActionMessage(request, new ActionMessage(
					PincodeMasterErrorConstants.REGION_DTLS_NOT_EXIST));
			LOGGER.warn("PincodemasterAction:: setRegion :: Region Details Does not exist");
		}

	}

	private void prepareGroupName(List<LabelValueBean> groupName,
			List<ProductTO> productTO,
			List<ProductGroupServiceabilityTO> productGroupServiceabilityTOs) {
		String labelName1 = "";
		String labelName2 = "";
		String labelName3 = "";
		String labelName4 = "";
		String labelName5 = "";
		LabelValueBean prodName1 = new LabelValueBean();
		LabelValueBean prodName2 = new LabelValueBean();
		LabelValueBean prodName3 = new LabelValueBean();
		LabelValueBean prodName4 = new LabelValueBean();
		LabelValueBean prodName5 = new LabelValueBean();

		for (ProductTO TO : productTO) {
			if (TO.getProductGroupTO().getProdGroupName()
					.equalsIgnoreCase(CommonConstants.GROUP1)) {
				if (labelName1.length() > 0) {
					labelName1 += CommonConstants.COMMA;
				}
				labelName1 += TO.getConsgSeries();
			}
			if (TO.getProductGroupTO().getProdGroupName()
					.equalsIgnoreCase(CommonConstants.GROUP2)) {
				if (labelName2.length() > 0) {
					labelName2 += CommonConstants.COMMA;
				}
				labelName2 += TO.getConsgSeries();
			}
			if (TO.getProductGroupTO().getProdGroupName()
					.equalsIgnoreCase(CommonConstants.GROUP3)) {
				if (labelName3.length() > 0) {
					labelName3 += CommonConstants.COMMA;
				}
				labelName3 += TO.getConsgSeries();
			}
			
			
			
			
			
			
			if (TO.getProductGroupTO().getProdGroupName()
					.equalsIgnoreCase(CommonConstants.GROUP4)) {
				if (labelName4.length() > 0) {
					labelName4 += CommonConstants.COMMA;
				}
				labelName4 += TO.getConsgSeries();
			}
			if (TO.getProductGroupTO().getProdGroupName()
					.equalsIgnoreCase(CommonConstants.GROUP5)) {
				if (labelName5.length() > 0) {
					labelName5 += CommonConstants.COMMA;
				}
				labelName5 += TO.getConsgSeries();
			}
		}

		for (ProductGroupServiceabilityTO serviceabilityTO : productGroupServiceabilityTOs) {
			if (serviceabilityTO.getProdGroupName().equalsIgnoreCase(
					CommonConstants.GROUP1)) {
				prodName1.setLabel(serviceabilityTO.getProdGroupName()
						+ CommonConstants.OPENING_ROUND_BRACE + labelName1
						+ CommonConstants.CLOSING_ROUND_BRACE);
				prodName1
						.setValue(serviceabilityTO.getProdGroupId().toString());
				groupName.add(prodName1);
			}
			if (serviceabilityTO.getProdGroupName().equalsIgnoreCase(
					CommonConstants.GROUP2)) {
				prodName2.setLabel(serviceabilityTO.getProdGroupName()
						+ CommonConstants.OPENING_ROUND_BRACE + labelName2
						+ CommonConstants.CLOSING_ROUND_BRACE);
				prodName2
						.setValue(serviceabilityTO.getProdGroupId().toString());
				groupName.add(prodName2);
			}
			if (serviceabilityTO.getProdGroupName().equalsIgnoreCase(
					CommonConstants.GROUP3)) {
				prodName3.setLabel(serviceabilityTO.getProdGroupName()
						+ CommonConstants.OPENING_ROUND_BRACE + labelName3
						+ CommonConstants.CLOSING_ROUND_BRACE);
				prodName3
						.setValue(serviceabilityTO.getProdGroupId().toString());
				groupName.add(prodName3);
			}
			if (serviceabilityTO.getProdGroupName().equalsIgnoreCase(
					CommonConstants.GROUP4)) {
				prodName4.setLabel(serviceabilityTO.getProdGroupName()
						+ CommonConstants.OPENING_ROUND_BRACE + labelName4
						+ CommonConstants.CLOSING_ROUND_BRACE);
				prodName4
						.setValue(serviceabilityTO.getProdGroupId().toString());
				groupName.add(prodName4);
			}
			if (serviceabilityTO.getProdGroupName().equalsIgnoreCase(
					CommonConstants.GROUP5)) {
				prodName5.setLabel(serviceabilityTO.getProdGroupName()
						+ CommonConstants.OPENING_ROUND_BRACE + labelName5
						+ CommonConstants.CLOSING_ROUND_BRACE);
				prodName5
						.setValue(serviceabilityTO.getProdGroupId().toString());
				groupName.add(prodName5);
			}
		}
		LOGGER.debug("PincodemasterAction::setRegion::END------------>:::::::");
	}

	// to populate States dropdown
	/**
	 * Gets the states.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 */
	public void getStatesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("PincodemasterAction::getStatesByRegion::START----->");

		String region = request.getParameter("regionId");
		Integer regionId = Integer.parseInt(region);
		String stateJson = FrameworkConstants.EMPTY_STRING;
		PrintWriter out = null;

		try {
			List<StateTO> stateTO;
			stateTO = pincodeMasterService.getStatesByRegionId(regionId);
			out = response.getWriter();

			if (!CGCollectionUtils.isEmpty(stateTO))
				stateJson = JSONSerializer.toJSON(stateTO).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("PincodemasterAction::getStatesByRegion::Exception :"
					, e);
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("PincodemasterAction::getStatesByRegion::Exception :"
					+ e.getMessage());
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("PincodemasterAction::getStatesByRegion::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			stateJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(stateJson);
			out.flush();
			out.close();
		}

		LOGGER.debug("PincodemasterAction::getStatesByRegion::END----->");
	}

	// to populate Citys dropdown
	/**
	 * Gets the citys.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 */
	public void getCitysByState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("PincodemasterAction::getCitysByState::START----->");

		String state = request.getParameter("stateId");
		Integer stateId = Integer.parseInt(state);
		String cityJson = FrameworkConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			List<CityTO> cityTO;
			cityTO = pincodeMasterService.getCitysByStateId(stateId);
			request.setAttribute("cityTO", cityTO);
			out = response.getWriter();
			if (cityTO != null)
				cityJson = JSONSerializer.toJSON(cityTO).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("PincodemasterAction::getCitysByState::Exception :"
					, e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("PincodemasterAction::getCitysByState::Exception :"
					+ e.getMessage());
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("PincodemasterAction::getCitysByState::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(cityJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("PincodemasterAction::getCitysByState::END----->");
	}

	// to populate Branches dropdown
	/**
	 * Gets the branches.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the stations
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	public void getBranchesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CGBusinessException, CGSystemException {

		LOGGER.debug("PincodemasterAction::getBranchesByCity::START----->");

		String city = request.getParameter("cityId");
		Integer cityId = Integer.parseInt(city);
		String branchJson = FrameworkConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			List<OfficeTO> officeTO = pincodeMasterService
					.getBranchesByCity(cityId,Integer.parseInt(PincodeMasterConstants.BRANCH_TYPE_ID));
			out = response.getWriter();
			if (!CGCollectionUtils.isEmpty(officeTO)){
				request.setAttribute("branchOffices", officeTO);
				branchJson = JSONSerializer.toJSON(officeTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("PincodemasterAction::getBranchesByCity::Exception :"
					, e);
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("PincodemasterAction::getBranchesByCity::Exception :"
					+ e.getMessage());
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("PincodemasterAction::getBranchesByCity::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			branchJson = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(branchJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("PincodemasterAction::getBranchesByCity::END----->");
	
		//return mapping.findForward(PincodeMasterConstants.SUCCESS);
	}
	
	private void setPaperWorkList(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PincodemasterAction:: setPaperWorkList :: START");
		List<CNPaperWorksTO> cnPaperWorkTOs = pincodeMasterService.getAllPaperWorks();
		if (!CGCollectionUtils.isEmpty(cnPaperWorkTOs)) {
			request.setAttribute(PincodeMasterConstants.PAPER_WORK, cnPaperWorkTOs);
		} else {
			prepareActionMessage(request, new ActionMessage(
					PincodeMasterErrorConstants.PAPER_DTLS_NOT_EXIST));
			LOGGER.warn("PincodemasterAction:: setPaperWorkList :: Paper Details Does not exist");
		}
		LOGGER.debug("PincodemasterAction:: setPaperWorkList :: END");
	}
	
	
	public ActionForward savePincodeMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug("PincodemasterAction:: savePincodeMaster :: START");
		PinCodeMasterForm pincodeMastrForm = (PinCodeMasterForm) form;
		boolean isSaved = Boolean.FALSE;
		HttpSession session = (HttpSession) request.getSession(false);

		try {
			serializer = CGJasonConverter.getJsonObject();
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);

			PinCodeMasterTO pincodeMasterTO = (PinCodeMasterTO) pincodeMastrForm
					.getTo();

			pincodeMasterTO.setCreatedDate(DateUtil
					.getCurrentDateInYYYYMMDDHHMM());
			pincodeMasterTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			pincodeMasterTO.setUpdatedate(DateUtil
					.getCurrentDateInYYYYMMDDHHMM());
			pincodeMasterTO.setUpdatedBy(userInfoTO.getUserto().getUserId());

			isSaved = pincodeMasterService.savePincodeMaster(pincodeMasterTO);

			if (isSaved) {
				request.setAttribute("pincodeSaved", "Saved successfully");

				setRegion(request);

				setProductList(request);

				setPaperWorkList(request);

				setAllCities(request);

				pincodeMastrForm.setTo(new PinCodeMasterTO());
			} else {
				request.setAttribute("pincodeNotSaved",
						"Not Saved successfully");
				setRegion(request);

				setProductList(request);

				setPaperWorkList(request);

				setAllCities(request);

				pincodeMastrForm.setTo(new PinCodeMasterTO());
			}

		} catch (Exception e) {
			LOGGER.debug(
					"PincodemasterAction:: savePincodeMaster :: EXCEPTION", e);
		}
		LOGGER.debug("PincodemasterAction:: savePincodeMaster :: END");
		return mapping.findForward(PincodeMasterConstants.SUCCESS);

	}
	
	public void searchPincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws CGBusinessException,CGSystemException {
		
		LOGGER.trace("PincodemasterAction :: searchPincode() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try{
			out = response.getWriter();

			
			pincodeMasterService = (PincodeMasterService) getBean("pincodeMasterService");

			String pincodeNo = request.getParameter("pincodeNO");
			
			PinCodeMasterTO pincodeMasterTO = pincodeMasterService.searchPincodeDetails(pincodeNo);
			
			
			jsonResult = JSONSerializer.toJSON(pincodeMasterTO).toString();
		}
		catch(Exception e){
			LOGGER.error("PincodemasterAction :: searchPincode() :: Exception", e);
		}
			finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
		LOGGER.trace("PincodemasterAction :: searchPincode() :: END");
		
	}
	
	
	public void activateDeactivatePincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws CGBusinessException,CGSystemException {
		
		LOGGER.trace("PincodemasterAction :: activateDeactivatePincode() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PinCodeMasterForm pincodeForm = (PinCodeMasterForm)form;
		PrintWriter out = null;
		 HttpSession session = (HttpSession) request.getSession(false);
		boolean flag = Boolean.FALSE;
		try{
			out = response.getWriter();
			  UserInfoTO userInfoTO =  (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
			 PinCodeMasterTO pincodeMasterTO = (PinCodeMasterTO)pincodeForm.getTo();
			pincodeMasterService = (PincodeMasterService) getBean("pincodeMasterService");

			String pincodeNo = request.getParameter("pincodeNO");
			String status = request.getParameter("status");
			
			pincodeMasterTO.setStatus(status);
			pincodeMasterTO.setPincodeNo(pincodeNo);
			pincodeMasterTO.setUpdatedate(DateUtil.getCurrentDateInYYYYMMDDHHMM());
			pincodeMasterTO.setUpdatedBy(userInfoTO.getUserto().getUserId());
			
			flag = pincodeMasterService.activateDeactivatePincode(pincodeMasterTO);
			if(flag){
				if(status.equalsIgnoreCase("A")){
					jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,"Pincode Activated Successfully");
				}else if(status.equalsIgnoreCase("I")){
					jsonResult=prepareCommonException(FrameworkConstants.SUCCESS_FLAG,"Pincode De-Activated Successfully");
				}
				
			}else{
				if(status.equalsIgnoreCase("A")){
					jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,"Pincode could not be activated successfully");
				}else if(status.equalsIgnoreCase("I")){
					jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,"Pincode could not be de-activated successfully");
				}
				
			}
	
		}
		catch(Exception e){
			LOGGER.error("PincodemasterAction :: activateDeactivatePincode() :: Exception", e);
		}
			finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		
		LOGGER.trace("PincodemasterAction :: activateDeactivatePincode() :: END");
		
	}
	
	
	private void setAllCities(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		
		LOGGER.debug("PincodemasterAction::setAllCities::START------------>:::::::");
		List<CityTO> cityTO = pincodeMasterService.getAllCities();
		if (!CGCollectionUtils.isEmpty(cityTO)) {
			request.setAttribute(PincodeMasterConstants.CITY_TO, cityTO);
		} else {
			prepareActionMessage(request, new ActionMessage(
					PincodeMasterErrorConstants.CITIES_NOT_FOUND));
			LOGGER.warn("PincodemasterAction:: setAllCities :: City Details Does not exist");
		}

	}
	
	
	
}