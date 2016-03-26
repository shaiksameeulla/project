package com.ff.rate.configuration.rateConfiguration.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.rateConfiguration.constants.RateConfigurationConstants;
import com.ff.rate.configuration.rateConfiguration.form.BARateConfigurationForm;
import com.ff.rate.configuration.rateConfiguration.service.BARateConfigurationService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.configuration.ratequotation.constants.RateQuotationConstants;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.masters.WeightSlabTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BACODChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.BARateHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.ratemanagement.constant.RateUniversalConstants;

/**
 * @author prmeher
 * 
 */
public class BARateConfigurationAction extends AbstractRateAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BARateConfigurationAction.class);
	/** The rate common service. */
	private RateCommonService rateCommonService;

	/** BA Rate Configuration Service */
	private BARateConfigurationService baRateConfigurationService;

	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * Load Ba rate configuration page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewBARateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.debug("BARateConfigurationAction::viewBARateConfiguration::START------------>:::::::");

		setUpDefaultValues(request);

		LOGGER.debug("BARateConfigurationAction::viewBARateConfiguration::END------------>:::::::");

		return mapping.findForward(RateCommonConstants.SUCCESS);
	}

	/**
	 * get all the cities based on zone.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the cities by region
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::getCitiesByRegion::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		
		try {
			RegionTO regionTO = new RegionTO();
			String region = request.getParameter(RateCommonConstants.REGION_ID);
			if (StringUtils.isNotEmpty(region)) {
				regionTO.setRegionId(Integer.parseInt(region));
			}
			rateCommonService = (RateCommonService) getBean(RateCommonConstants.RATE_COMMON_SERVICE);
			List<CityTO> cityTOs = rateCommonService
					.getCitiesByRegion(regionTO);

			jsonResult = serializer.toJSON(cityTOs).toString();

		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: getCitiesByRegion() ::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::getCitiesByRegion::END------------>:::::::");
	}

	/**
	 * Set Default Param
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void setUpDefaultValues(HttpServletRequest request) throws Exception {
		LOGGER.trace("BARateConfigurationAction::setUpDefaultValues::START------------>:::::::");
		request.setAttribute(RateConfigurationConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		serializer = CGJasonConverter.getJsonObject();
		rateCommonService = (RateCommonService) getBean(RateCommonConstants.RATE_COMMON_SERVICE);
		baRateConfigurationService = getbaRateConfigurationService();

		// Populate the Region
		List<RegionTO> regionTOs = rateCommonService.getAllRegions();
		request.setAttribute(RateCommonConstants.REGION_TOS, regionTOs);

		// Populate BA type - drop down
		List<CustomerTypeDO> baTypes = baRateConfigurationService
				.getBATypeList(RateCommonConstants.CUSTOMER_CODE_BA);
		request.setAttribute(RateCommonConstants.BA_TYPE_LIST, baTypes);

		// Insured By List
		List<InsuredByDO> insuredByList = baRateConfigurationService
				.getInsuredByDetails();
		request.setAttribute(RateCommonConstants.INSURED_BY_LIST, insuredByList);

		// Courier - Sector
		setRateSectorList(request,
				RateCommonConstants.BA_RATE_PRODUCT_CATEGORY_COURIER,
				RateConfigurationConstants.RATE_SECTORS_FOR_COURIER);

		// Courier - Weight Slab
		setRateWeightSlabsList(request,
				RateCommonConstants.BA_RATE_PRODUCT_CATEGORY_COURIER,
				RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER);

		// Air-Cargo - Sector
		setRateSectorList(request, RateCommonConstants.PRO_CODE_AIR_CARGO,
				RateConfigurationConstants.RATE_SECTORS_FOR_AIR_CARGO);

		// Air-Cargo - Weight Slab
		setRateWeightSlabsList(request, RateCommonConstants.PRO_CODE_AIR_CARGO,
				RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_AIR_CARGO);

		// Train - Sector
		setRateSectorList(request, RateCommonConstants.PRO_CODE_TRAIN,
				RateConfigurationConstants.RATE_SECTORS_FOR_TRAIN);

		// Train - Weight Slab
		setRateWeightSlabsList(request, RateCommonConstants.PRO_CODE_TRAIN,
				RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_TRAIN);

		// Priority - Sector
		setRateSectorList(request,
				RateCommonConstants.BA_RATE_PRODUCT_CATEGORY_PRIORITY,
				RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);

		// Priority - Weight Slab
		setRateWeightSlabsList(request,
				RateCommonConstants.BA_RATE_PRODUCT_CATEGORY_PRIORITY,
				RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);

		// State List - Special Destination
		List<StateTO> statesList = getStatesList(request);
		if (!CGCollectionUtils.isEmpty(statesList)) {
			request.setAttribute("statesList", serializer.toJSON(statesList)
					.toString());
		}

		/*
		 * Setting weight slab by weight slab category - MC - Minimun Chargable
		 * weight
		 */
		setWeightSlabByWtSlabCate(request);
		setWeightSlabByWtSlabCateAndEndWt(request);

		// To set customer category type
		RateCustomerCategoryTO rateCustomerCategoryCode = baRateConfigurationService
				.getRateCustCategoryByCode(RateUniversalConstants.RATE_CUST_CAT_BA);
		if (!StringUtil.isNull(rateCustomerCategoryCode)) {
			request.setAttribute(RateUniversalConstants.CUST_CATEGORY_CODE,
					rateCustomerCategoryCode);
		}

		List<CodChargeTO> codChargeTOs = baRateConfigurationService
				.getDeclaredValueCodChargeForBA(
						RateCommonConstants.COD_CONFIGURED_FOR_NON_PRIORITY,
						rateCustomerCategoryCode.getRateCustomerCategoryId());
		if (!StringUtil.isEmptyColletion(codChargeTOs)) {
			request.setAttribute("codChargeTOs", codChargeTOs);
		}
		
		// Populate Priority product drop-down
		getStandardType(request, RateCommonConstants.STD_TYPE_NAME_SERVICED_ON, 
				RateCommonConstants.PARAM_SERVICED_ON_LIST);
		LOGGER.trace("BARateConfigurationAction::setUpDefaultValues::END------------>:::::::");
	}

	/**
	 * To get and set weight slab by weight slab category - MC - Minimum
	 * chargeable weight
	 * 
	 * @param request
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	@SuppressWarnings("unchecked")
	private void setWeightSlabByWtSlabCate(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationAction :: setWeightSlabByWtSlabCate() :: START");
		HttpSession session = request.getSession(false);
		String scopeVar = RateConfigurationConstants.WEIGHT_SLAB_MIN_CHARG;
		// Setting Weight Slab - MC - Minimum Chargeable weight
		List<WeightSlabTO> wtSlabMCList = (List<WeightSlabTO>) session
				.getAttribute(scopeVar);
		if (CGCollectionUtils.isEmpty(wtSlabMCList)) {
			rateCommonService = getRateCommonService();
			wtSlabMCList = rateCommonService
					.getWeightSlabByWtSlabCate(RateUniversalConstants.WT_SLAB_CAT_MC);
			session.setAttribute(scopeVar, wtSlabMCList);
		}
		if (!CGCollectionUtils.isEmpty(wtSlabMCList)) {
			if (!StringUtil.isNull(wtSlabMCList.get(0))) {
				request.setAttribute(
						RateConfigurationConstants.ZERO_WEIGHT_SLAB_ID,
						wtSlabMCList.get(0).getWeightSlabId());
			}
			request.setAttribute(scopeVar, wtSlabMCList);
		}
		LOGGER.trace("BARateConfigurationAction :: setWeightSlabByWtSlabCate() :: END");
	}

	/**
	 * To get and set weight slab by weight slab category - MC - Minimum
	 * chargeable weight
	 * 
	 * @param request
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	@SuppressWarnings("unchecked")
	private void setWeightSlabByWtSlabCateAndEndWt(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BARateConfigurationAction :: setWeightSlabByWtSlabCateAndEndWt() :: START");
		HttpSession session = request.getSession(false);
		String scopeVar = RateConfigurationConstants.NEXT_COL_MIN_CHARG;
		// Setting Weight Slab - MC - Minimum Chargeable weight
		List<WeightSlabTO> wtSlabMCList = (List<WeightSlabTO>) session
				.getAttribute(scopeVar);
		if (CGCollectionUtils.isEmpty(wtSlabMCList)) {
			rateCommonService = getRateCommonService();
			wtSlabMCList = rateCommonService.getWeightSlabByWtSlabCateAndEndWt(
					RateUniversalConstants.WT_SLAB_CAT_PK, 25.000);
			session.setAttribute(scopeVar, wtSlabMCList);
		}
		if (!CGCollectionUtils.isEmpty(wtSlabMCList)) {
			request.setAttribute(scopeVar, wtSlabMCList);
			request.setAttribute(scopeVar + RateConfigurationConstants.JSON,
					JSONSerializer.toJSON(wtSlabMCList).toString());
		}
		LOGGER.trace("BARateConfigurationAction :: setWeightSlabByWtSlabCateAndEndWt() :: END");
	}

	/**
	 * To set destination sector to session and request scope variable.
	 * 
	 * @param request
	 * @param productCat
	 * @param scopeVar
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setRateSectorList(HttpServletRequest request,
			String productCat, String scopeVar) throws Exception {
		LOGGER.trace("BARateConfigurationAction::setRateSectorList::START------------>:::::::");
		HttpSession session = request.getSession(false);
		// Setting Sector
		List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
				.getAttribute(scopeVar);
		if (CGCollectionUtils.isEmpty(sectorsList)) {
			sectorsList = getRateSectorList(productCat,
					RateCommonConstants.RATE_CUSTOMER_CATEGORY);
			session.setAttribute(scopeVar, sectorsList);
		}
		if (!CGCollectionUtils.isEmpty(sectorsList)) {
			request.setAttribute(scopeVar, sectorsList);
			request.setAttribute(scopeVar + RateConfigurationConstants.JSON,
					JSONSerializer.toJSON(sectorsList).toString());
		}
		LOGGER.trace("BARateConfigurationAction::setRateSectorList::END------------>:::::::");
	}

	/**
	 * To set weight slab to session and request scope variable.
	 * 
	 * @param request
	 * @param productCat
	 * @param scopeVar
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setRateWeightSlabsList(HttpServletRequest request,
			String productCat, String scopeVar) throws Exception {
		LOGGER.trace("BARateConfigurationAction::setRateWeightSlabsList::START------------>:::::::");
		HttpSession session = request.getSession(false);
		// Setting Weight Slab
		List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(scopeVar);

		// Prepare product category map (code,id)
		Map<String, Integer> prodCateMap = (Map<String, Integer>) session
				.getAttribute("productCategoryMap");
		if (CGCollectionUtils.isEmpty(prodCateMap)) {
			prodCateMap = new HashMap<String, Integer>();
			session.setAttribute("productCategoryMap", prodCateMap);
			prodCateMap = (Map<String, Integer>) session
					.getAttribute("productCategoryMap");
		}

		if (CGCollectionUtils.isEmpty(wtSlabList)) {
			wtSlabList = getRateWeightSlabsList(productCat,
					RateCommonConstants.RATE_CUSTOMER_CATEGORY);
			session.setAttribute(scopeVar, wtSlabList);
		}
		if (!CGCollectionUtils.isEmpty(wtSlabList)) {
			request.setAttribute(scopeVar, wtSlabList);
			request.setAttribute(scopeVar + RateConfigurationConstants.JSON,
					JSONSerializer.toJSON(wtSlabList).toString());
			if (!prodCateMap.containsKey(productCat)) {
				prodCateMap.put(productCat, wtSlabList.get(0)
						.getRateCustomerProductCatMapTO()
						.getRateProductCategoryTO().getRateProductCategoryId());
			}
		}
		LOGGER.trace("BARateConfigurationAction::setRateWeightSlabsList::END------------>:::::::");
	}

	/**
	 * Get Sectors list
	 * 
	 * @param request
	 * @param baRateProductCategoryCourier
	 * @param rateCustomerCategory
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<RateSectorsTO> getRateSectorList(String baRateProductCategory,
			String rateCustomerCategory) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BARateConfigurationAction::getRateSectorList::START------------>:::::::");
		List<RateSectorsTO> sectorsList = null;
		baRateConfigurationService = getbaRateConfigurationService();
		sectorsList = baRateConfigurationService
				.getRateSectorListForBARateConfiguration(baRateProductCategory,
						rateCustomerCategory);
		LOGGER.trace("BARateConfigurationAction::getRateSectorList::END------------>:::::::");
		return sectorsList;
	}

	/**
	 * @param request
	 * @param baRateProductCategory
	 * @param rateCustomerCategory
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RateWeightSlabsTO> getRateWeightSlabsList(
			String baRateProductCategory, String rateCustomerCategory)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BARateConfigurationAction::getRateWeightSlabsList::START------------>:::::::");
		List<RateWeightSlabsTO> wtSlabList = null;
		baRateConfigurationService = getbaRateConfigurationService();
		wtSlabList = baRateConfigurationService.getRateWeightSlabsList(
				baRateProductCategory, rateCustomerCategory);
		LOGGER.trace("BARateConfigurationAction::getRateWeightSlabsList::END------------>:::::::");
		return wtSlabList;
	}

	/**
	 * Returns BARateConfigurationService
	 * 
	 * @return
	 */
	BARateConfigurationService getbaRateConfigurationService() {
		return (BARateConfigurationService) getBean(RateCommonConstants.BA_RATE_CONFIGURATION_SERVICE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getCityName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.trace("BARateConfigurationAction::getCityName::START------------>:::::::");
		CityTO cityTO = null;//new CityTO();
		String cityTOJSON = null;
		String pincode = null;
		PrintWriter out = null;
		out = response.getWriter();
		

		try {
			serializer = CGJasonConverter.getJsonObject();
			if (StringUtils.isNotEmpty(request.getParameter("pincode"))) {
				pincode = request.getParameter("pincode");
			}
			GeographyCommonService geographyCommonService = (GeographyCommonService) getBean("geographyCommonService");
			cityTO = geographyCommonService.getCity(pincode);

			if (!StringUtil.isNull(cityTO.getCityId())) {

				cityTOJSON = serializer.toJSON(cityTO).toString();
			} else {
				cityTOJSON = "INVALID";
			}

		} catch (Exception e) {
			cityTOJSON = "INVALID";

			LOGGER.error("BARateConfigurationAction :: getCity() ::"
					+ e.getMessage());
		} finally {

			out.print(cityTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::getCityName::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void getPincode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.trace("BARateConfigurationAction::getPincode::START------------>:::::::");
		PincodeTO pincodeTO = new PincodeTO();
		String pincodeTOJSON = null;

		PrintWriter out = null;
		out = response.getWriter();
		
		try {
			serializer = CGJasonConverter.getJsonObject();
			if (StringUtils.isNotEmpty(request.getParameter("pincode"))) {
				pincodeTO.setPincode(request.getParameter("pincode"));
			}
			GeographyCommonService geographyCommonService = (GeographyCommonService) getBean("geographyCommonService");
			pincodeTO = geographyCommonService.validatePincode(pincodeTO);

			if (!StringUtil.isNull(pincodeTO.getPincodeId())) {

				pincodeTOJSON = serializer.toJSON(pincodeTO).toString();
			} else {
				pincodeTOJSON = "INVALID";
			}

		} catch (Exception e) {
			pincodeTOJSON = "INVALID";

			LOGGER.error("BARateConfigurationAction :: getPincode() ::"
					+ e.getMessage());
		} finally {

			out.print(pincodeTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::getPincode::END------------>:::::::");
	}

	/**
	 * Save and update courier rates
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void saveOrUpdateBARate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateBARate::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			HttpSession session = request.getSession(false);
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm.getTo();
			baRateHeader.setProductCategory(RateConfigurationConstants.COURIER_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			baRateHeader = baRateConfigurationService.saveOrUpdateBARateConfiguration(baRateHeader);
			List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER);
			List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_COURIER);
			baRateHeader.setSectorsList(sectorsList);
			baRateHeader.setWtSlabList(wtSlabList);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateBARate() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateBARate::END------------>:::::::");
	}

	/**
	 * Save or update ba rate configuration for priority
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void saveOrUpdateBARateConfgForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateBARateConfgForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			HttpSession session = request.getSession(false);
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateHeader
					.setProductCategory(RateConfigurationConstants.PRIORITY_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			baRateHeader = baRateConfigurationService.saveOrUpdateBARateConfiguration(baRateHeader);
			List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
			List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);
			baRateHeader.setPrioritySectorsList(sectorsList);
			baRateHeader.setPriorityWtSlabList(wtSlabList);

			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateBARateConfgForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateBARateConfgForPriority::END------------>:::::::");
	}

	/**
	 * Search BA rate configuration
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void searchBARateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction::searchBARateConfiguration::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		BARateHeaderTO baRateHeader = null;
		Integer priorityProductId = null;
		Integer courierProductId = null;
		Integer regionId = null;
		Date fromDate = null;
		Date toDate = null;
		serializer = CGJasonConverter.getJsonObject();
		if (!StringUtil.isStringEmpty(request.getParameter("fromDate"))) {
			fromDate = DateUtil.stringToDDMMYYYYFormat(request
					.getParameter("fromDate"));
		}
		if (!StringUtil.isStringEmpty(request.getParameter("toDate"))) {
			toDate = DateUtil.stringToDDMMYYYYFormat(request
					.getParameter("toDate"));
		}
		Integer cityId = Integer.parseInt(request.getParameter("city"));
		Integer baTypeId = Integer.parseInt(request.getParameter("baType"));
		if (!StringUtil.isStringEmpty(request.getParameter("courierProductId"))) {
			courierProductId = Integer.parseInt(request
					.getParameter("courierProductId"));
		}
		if (!StringUtil
				.isStringEmpty(request.getParameter("priorityProductId"))) {
			priorityProductId = Integer.parseInt(request
					.getParameter("priorityProductId"));
		}
		if (!StringUtil.isStringEmpty(request.getParameter("regionId"))) {
			regionId = Integer.parseInt(request.getParameter("regionId"));
		}
		Integer headerId = null;
		if (!StringUtil.isStringEmpty(request.getParameter("headerId"))) {
			headerId = Integer.parseInt(request.getParameter("headerId"));
		}
		try {
			HttpSession session = request.getSession(false);
			baRateConfigurationService = getbaRateConfigurationService();
			if (!StringUtil.isEmptyInteger(headerId)) {
				baRateHeader = baRateConfigurationService
						.searchBARateConfigurationByHeaderId(headerId, cityId,
								baTypeId, courierProductId, priorityProductId);
			} else {
				baRateHeader = baRateConfigurationService
						.searchBARateConfiguration(fromDate, toDate, cityId,
								baTypeId, courierProductId, priorityProductId);
			}

			List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER);
			List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_COURIER);

			List<RateWeightSlabsTO> priorityWtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
			List<RateSectorsTO> prioritySectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);
			if (StringUtil.isNull(baRateHeader)) {
				baRateHeader = new BARateHeaderTO();
			}
			baRateHeader.setSectorsList(sectorsList);
			baRateHeader.setWtSlabList(wtSlabList);
			baRateHeader.setPrioritySectorsList(prioritySectorsList);
			baRateHeader.setPriorityWtSlabList(priorityWtSlabList);
			baRateHeader
					.setSectorCode(getSectorCodeByRegion(request, regionId));
			// baRateHeader.setStatesList(getStatesList(request));

			session.setAttribute("CourierSlabSearchResult", baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchBARateConfiguration() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchBARateConfiguration::END------------>:::::::");
	}

	/**
	 * Search Renewd BA rate configuration
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void searchRenewedBARateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction::searchRenewedBARateConfiguration::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		BARateHeaderTO baRateHeader = null;
		String productCode = "";
		serializer = CGJasonConverter.getJsonObject();
		Integer cityId = Integer.parseInt(request.getParameter("city"));
		Integer baTypeId = Integer.parseInt(request.getParameter("baType"));
		Date toDate = DateUtil.stringToDDMMYYYYFormat(request
				.getParameter("toDate"));
		Integer courierProductId = Integer.parseInt(request
				.getParameter("courierProductId"));
		Integer priorityProdductId = Integer.parseInt(request
				.getParameter("priorityProductId"));
		Integer regionId = Integer.parseInt(request.getParameter("regionId"));
		Integer headerId = Integer.parseInt(request.getParameter("headerId"));
		if (!StringUtil.isStringEmpty(request.getParameter("productCode"))) {
			productCode = request.getParameter("productCode");
		}
		try {
		
			HttpSession session = request.getSession(false);
			Map<String, Integer> prodCateMap = (Map<String, Integer>) session
					.getAttribute("productCategoryMap");
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.searchRenewedBARateConfiguration(cityId, baTypeId,
							courierProductId, priorityProdductId, toDate,
							headerId, prodCateMap, session);
			/*
			 * List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>)
			 * session
			 * .getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER
			 * ); List<RateSectorsTO> sectorsList = (List<RateSectorsTO>)
			 * session
			 * .getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_COURIER
			 * ); List<RateWeightSlabsTO> priorityWtSlabList =
			 * (List<RateWeightSlabsTO>) session
			 * .getAttribute(RateConfigurationConstants
			 * .WEIGHT_SLAB_LIST_FOR_PRIORITY); List<RateSectorsTO>
			 * prioritySectorsList = (List<RateSectorsTO>) session
			 * .getAttribute(
			 * RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY); if
			 * (StringUtil.isNull(baRateHeader)) { baRateHeader = new
			 * BARateHeaderTO(); } baRateHeader.setSectorsList(sectorsList);
			 * baRateHeader.setWtSlabList(wtSlabList);
			 * baRateHeader.setPrioritySectorsList(prioritySectorsList);
			 * baRateHeader.setPriorityWtSlabList(priorityWtSlabList);
			 */
			setBARateCommonValues(request, baRateHeader);
			baRateHeader
					.setSectorCode(getSectorCodeByRegion(request, regionId));
			baRateHeader.setCommonProdCatCode(productCode);
			session.setAttribute("CourierSlabSearchResult", baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchRenewedBARateConfiguration() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchRenewedBARateConfiguration::END------------>:::::::");
	}

	/**
	 * Search Rates for courier
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void searchRateForCourier(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction::searchRateForCourier::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		BARateHeaderTO baRateHeader = null;
		serializer = CGJasonConverter.getJsonObject();
		try {
			HttpSession session = request.getSession(false);
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO header = (BARateHeaderTO) baRateConfgForm.getTo();
			if (!StringUtil.isEmptyInteger(header.getHeaderId())) {
				baRateHeader = (BARateHeaderTO) session
						.getAttribute("CourierSlabSearchResult");
			}
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchBARateConfiguration() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchRateForCourier::End------------>:::::::");
	}

	/**
	 * Save or update fixed charges
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateFixedChargesForCourier(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateFixedChargesForCourier::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm.getTo();
			baRateHeader.setProductCategory(RateConfigurationConstants.COURIER_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			// To prepare CODCharges list
			List<BACODChargesTO> codList = null;//new ArrayList<>();
			codList = prepareCODCharges(request,
					baRateHeader.getBaCourierFixedChargesTO());
			baRateHeader = baRateConfigurationService.saveOrUpdateFixedCharges(baRateHeader, codList);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateFixedChargesForCourier() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateFixedChargesForCourier::End------------>:::::::");
	}

	/**
	 * Save or update fixed charges for priority
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateFixedChargesForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateFixedChargesForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateHeader
					.setProductCategory(RateConfigurationConstants.PRIORITY_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			baRateHeader = baRateConfigurationService.saveOrUpdateFixedCharges(
					baRateHeader, null);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateFixedChargesForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateFixedChargesForPriority::END------------>:::::::");
	}

	/**
	 * Search fixed charges for courier
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void searchFixedChargesForCourier(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::searchFixedChargesForCourier::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.getFixedChargesForCourier(baRateHeader);
			loadDefaultTaxComponent(baRateHeader);

			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchFixedChargesForCourier() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchFixedChargesForCourier::END------------>:::::::");
	}

	/**
	 * Search RTO charges for courier
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void searchRTOChargesForCourier(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::searchRTOChargesForCourier::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.getRTOChargesForCourier(baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchRTOChargesForCourier() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchRTOChargesForCourier::END------------>:::::::");
	}

	/**
	 * Search rates for priority
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void searchBARateConfigurationForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::searchBARateConfigurationForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		BARateHeaderTO baRateHeader = null;
		serializer = CGJasonConverter.getJsonObject();
		try {
			HttpSession session = request.getSession(false);
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			baRateHeader = (BARateHeaderTO) baRateConfgForm.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.searchBARateConfigurationForPriorityProduct(DateUtil
							.stringToDDMMYYYYFormat(baRateHeader.getFrmDate()),
							DateUtil.stringToDDMMYYYYFormat(baRateHeader
									.getToDate()), baRateHeader.getCityId(),
							baRateHeader.getBaType(), baRateHeader
									.getProductCategoryIdForPriority(),
							baRateHeader.getHeaderId());
			List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
			List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);
			baRateHeader.setPrioritySectorsList(sectorsList);
			baRateHeader.setPriorityWtSlabList(wtSlabList);
			// session.setAttribute("prioritySlabSearchResult", baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchBARateConfigurationForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchBARateConfigurationForPriority::END------------>:::::::");
	}

	/**
	 * Get RTO Charges For Priority Product
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void searchRTOChargesForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::searchRTOChargesForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.getRTOChargesForPriority(baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchRTOChargesForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchRTOChargesForPriority::END------------>:::::::");
	}

	/**
	 * get Fixed Charges For Priority Product
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void searchFixedChargesForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::searchFixedChargesForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader = baRateConfigurationService
					.getFixedChargesForPriority(baRateHeader);
			loadDefaultTaxComponent(baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: searchFixedChargesForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::searchFixedChargesForPriority::END------------>:::::::");
	}

	/**
	 * save or update RTO charges for courier
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRTOChargesForCourier(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateRTOChargesForCourier::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateHeader
					.setProductCategory(RateConfigurationConstants.COURIER_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			baRateHeader = baRateConfigurationService.saveOrUpdateRTOCharges(baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateRTOChargesForCourier() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateRTOChargesForCourier::END------------>:::::::");
	}

	/**
	 * save or update RTO charges for priority
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRTOChargesForPriority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateRTOChargesForPriority::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm
					.getTo();
			baRateHeader
					.setProductCategory(RateConfigurationConstants.PRIORITY_PRODUCT_CODE);
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			baRateHeader = baRateConfigurationService.saveOrUpdateRTOCharges(baRateHeader);
			baRateHeaderTOJSON = serializer.toJSON(baRateHeader).toString();
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: saveOrUpdateRTOChargesForPriority() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::saveOrUpdateRTOChargesForPriority::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void submitBaRateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.trace("BARateConfigurationAction::submitBaRateConfiguration::START------------>:::::::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		String isSubmited = "";
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO baRateHeader = (BARateHeaderTO) baRateConfgForm.getTo();
			baRateConfigurationService = getbaRateConfigurationService();
			baRateHeader.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			isSubmited = baRateConfigurationService.submitBaRateConfiguration(baRateHeader);
			baRateHeaderTOJSON = isSubmited;
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: submitBaRateConfiguration() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::submitBaRateConfiguration::END------------>:::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward renewBARateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.debug("BARateConfigurationAction::renewBARateConfiguration::START------------>:::::::");
		String oldHeaderId = request.getParameter("headerId");
		String oldfromDate = request.getParameter("fromDate");
		String oldtoDate = request.getParameter("toDate");
		String oldCity = request.getParameter("city");
		String oldbaType = request.getParameter("baType");
		String isRenewWindow = request.getParameter("isRenewWindow");
		String oldRegion = request.getParameter("region");
		request.setAttribute("oldbaType", oldbaType);
		request.setAttribute("oldHeaderId", oldHeaderId);
		request.setAttribute("oldfromDate", oldfromDate);
		request.setAttribute("oldtoDate", oldtoDate);
		request.setAttribute("oldCity", oldCity);
		request.setAttribute("isRenewWindow", isRenewWindow);
		request.setAttribute("oldRegion", oldRegion);

		setUpDefaultValues(request);

		LOGGER.debug("BARateConfigurationAction::renewBARateConfiguration::END------------>:::::::");

		return mapping
				.findForward(RateCommonConstants.RENEW_BA_RATE_CONFIGURATION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void isExistsBaRateConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BARateConfigurationAction::isExistsBaRateConfiguration::START------------>:::::::");
		PrintWriter out = null;
		String baRateHeaderTOJSON = "";
		serializer = CGJasonConverter.getJsonObject();
		String isExists = "";
		try {
			out = response.getWriter();
			Integer city = Integer.parseInt(request.getParameter("city"));
			Integer baType = Integer.parseInt(request.getParameter("baType"));
			baRateConfigurationService = getbaRateConfigurationService();
			isExists = baRateConfigurationService.isExistsBaRateConfiguration(
					city, baType);
			baRateHeaderTOJSON = isExists;
		} catch (Exception e) {
			LOGGER.error("BARateConfigurationAction :: isExistsBaRateConfiguration() ::"
					+ e.getMessage());
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction::isExistsBaRateConfiguration::END------------>:::::::");
	}

	/**
	 * To load default tax components
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	private void loadDefaultTaxComponent(BARateHeaderTO headerTO)
			throws Exception {
		LOGGER.trace("BARateConfigurationAction::loadDefaultTaxComponent::START------------>:::::::");
		rateCommonService = (RateCommonService) getBean(RateCommonConstants.RATE_COMMON_SERVICE);
		BARateConfigFixedChargesTO fixedChargesTO = null;
		BARateConfigFixedChargesTO priorityFixedChargesTO = null;
		List<RateTaxComponentTO> taxCmptTOs = null;
		CityTO cityTO = null;
		boolean isStateTaxApplicable = false;
		Integer cityId = headerTO.getCityId();
		Integer stateId = null;
		if (!StringUtil.isEmptyInteger(cityId)) {
			cityTO = new CityTO();
			cityTO.setCityId(cityId);
			cityTO = rateCommonService.getCity(cityTO);
		}
		if (!StringUtil.isNull(cityTO)) {
			stateId = cityTO.getState();
		}
		/* To populate Tax Component */
		taxCmptTOs = rateCommonService
				.loadDefaultRateTaxComponentValueForConfiguration(stateId);

		for (RateTaxComponentTO taxCmptTO : taxCmptTOs) {
			if (!StringUtil.isNull(taxCmptTO.getRateComponentId())) {
				// Service Tax
				if (taxCmptTO
						.getRateComponentId()
						.getRateComponentCode()
						.equalsIgnoreCase(
								RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX)) {
					isStateTaxApplicable = true;
					break;
				}
			}
		}

		if (!StringUtil.isEmptyColletion(taxCmptTOs)) {
			fixedChargesTO = headerTO.getBaCourierFixedChargesTO();
			priorityFixedChargesTO = headerTO.getBaPriorityFixedChargesTO();
			if (StringUtil.isNull(fixedChargesTO)) {
				fixedChargesTO = new BARateConfigFixedChargesTO();
			}
			if (StringUtil.isNull(fixedChargesTO)) {
				priorityFixedChargesTO = new BARateConfigFixedChargesTO();
			}

			for (RateTaxComponentTO taxCmptTO : taxCmptTOs) {
				if (!StringUtil.isNull(taxCmptTO.getRateComponentId())) {
					if (!isStateTaxApplicable) {
						// Service Tax
						if (taxCmptTO
								.getRateComponentId()
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX)) {
							fixedChargesTO.setServiceTax(taxCmptTO
									.getTaxPercentile());
							priorityFixedChargesTO.setServiceTax(taxCmptTO
									.getTaxPercentile());
						}
						// Edu. Cess
						if (taxCmptTO
								.getRateComponentId()
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS)) {
							fixedChargesTO.setEduCharges(taxCmptTO
									.getTaxPercentile());
							priorityFixedChargesTO.setEduCharges(taxCmptTO
									.getTaxPercentile());
						}
						// Higher Edu. Cess
						if (taxCmptTO
								.getRateComponentId()
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS)) {
							fixedChargesTO.setHigherEduCharges(taxCmptTO
									.getTaxPercentile());
							priorityFixedChargesTO
									.setHigherEduCharges(taxCmptTO
											.getTaxPercentile());
						}
					}
					if (isStateTaxApplicable) {
						// State Tax
						if (taxCmptTO
								.getRateComponentId()
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX)) {
							fixedChargesTO.setStateTax(taxCmptTO
									.getTaxPercentile());
							priorityFixedChargesTO.setStateTax(taxCmptTO
									.getTaxPercentile());
						}
						// Surcharge On ST
						if (taxCmptTO
								.getRateComponentId()
								.getRateComponentCode()
								.equalsIgnoreCase(
										RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST)) {
							fixedChargesTO.setSurchargeOnST(taxCmptTO
									.getTaxPercentile());
							priorityFixedChargesTO.setSurchargeOnST(taxCmptTO
									.getTaxPercentile());
						}
					}
				}// END INNER IF - taxCmptTO
			}// END FOR LOOP
			headerTO.setBaCourierFixedChargesTO(fixedChargesTO);
			headerTO.setBaPriorityFixedChargesTO(priorityFixedChargesTO);
		}// END OUTER IF - taxCmptTOs
		LOGGER.trace("BARateConfigurationAction::loadDefaultTaxComponent::END------------>:::::::");
	}

	public ActionForward viewBARateConfigAtRHO(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BARateConfigurationAction::viewBARateConfigAtRHO()::START");
		try {
			BARateConfigurationForm bConfigurationForm = (BARateConfigurationForm) form;
			BARateHeaderTO to = (BARateHeaderTO) bConfigurationForm.getTo();
			setUpDefaultValues(request);

			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfo = (UserInfoTO) session
					.getAttribute(RateContractConstants.USER_INFO);

			OfficeTO officeTO = userInfo.getOfficeTo();
			to.setRegionId(officeTO.getRegionTO().getRegionId());

			RegionTO regionTO = new RegionTO();
			regionTO.setRegionId(to.getRegionId());
			rateCommonService = (RateCommonService) getBean(RateCommonConstants.RATE_COMMON_SERVICE);
			List<CityTO> cityTOs = rateCommonService
					.getCitiesByRegion(regionTO);
			request.setAttribute(RateCommonConstants.CITY_TOS, cityTOs);

			to.setCityId(officeTO.getCityId());
			/*
			 * if(!(StringUtil.isNull(userInfo.getCustUserTo()))) {
			 * to.setBaType(
			 * userInfo.getCustUserTo().getCustTO().getCustomerTypeTO
			 * ().getCustomerTypeId()); }
			 */

			saveToken(request);
			((BARateConfigurationForm) form).setTo(to);

		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BARateConfigurationAction::viewBARateConfigAtRHO()::",
					e);
		}
		LOGGER.debug("BARateConfigurationAction::viewBARateConfigAtRHO()::END");
		return mapping
				.findForward(RateCommonConstants.VIEW_BA_RATE_CONFIG_AT_RHO);
	}

	public String getSectorCodeByRegion(HttpServletRequest request,
			Integer regionId) {

		SectorTO sectorTO = null;
		HttpSession session = request.getSession(false);
		String sectorCode = CommonConstants.EMPTY_STRING;
		try {

			//sectorTO = new SectorTO();
			// sectorCode = (String)
			// session.getAttribute(RateCommonConstants.BA_SECTOR_CODE);
			// if (StringUtil.isNull(sectorCode)) {
			rateCommonService = (RateCommonService) getBean(RateCommonConstants.RATE_COMMON_SERVICE);
			sectorTO = rateCommonService.getSectorByRegionId(regionId);
			if (!StringUtil.isNull(sectorTO)) {
				sectorCode = sectorTO.getSectorCode();
				session.setAttribute(RateCommonConstants.BA_SECTOR_CODE,
						sectorCode);
			}
			// }
		} catch (CGBusinessException e) {
			LOGGER.error("AbstractRouteAction:: getRateIndustryCategoryList", e);
		} catch (CGSystemException e) {
			LOGGER.error("AbstractRouteAction:: getRateIndustryCategoryList", e);
		} catch (Exception e) {
			LOGGER.error("AbstractRouteAction:: getRateIndustryCategoryList", e);
		}
		return sectorCode;
	}

	private List<BACODChargesTO> prepareCODCharges(HttpServletRequest request,
			BARateConfigFixedChargesTO fixedChargesTO) {
		List<BACODChargesTO> codList = new ArrayList<BACODChargesTO>();
		int fixedChrgsLen = fixedChargesTO.getCodChargeId().length;
		for (int i = 1; i <= fixedChrgsLen; i++) {
			String selected = request
					.getParameter(RateQuotationConstants.FIXED_CHRGS_TYPE + i);
			if (!StringUtil
					.isEmptyDouble(fixedChargesTO.getFixedChargesEco()[i - 1])
					|| !StringUtil
							.isEmptyDouble(fixedChargesTO.getCodPercent()[i - 1])) {
				if (selected.equals(RateQuotationConstants.FIXED_PERCENT)) {
					BACODChargesTO codTO = new BACODChargesTO();
					codTO.setConsiderFixed(CommonConstants.YES);
					codTO.setBaCodChargesId(fixedChargesTO.getCodChargeId()[i - 1]);
					codTO.setFixedChargeValue(fixedChargesTO
							.getFixedChargesEco()[i - 1]);
					codTO.setPercentileValue(fixedChargesTO.getCodPercent()[i - 1]);
					codList.add(codTO);

				} else if (selected.equals(RateQuotationConstants.COD_PERCENT)) {
					BACODChargesTO codTO = new BACODChargesTO();
					codTO.setConsiderHigherFixedOrPercent(CommonConstants.YES);
					codTO.setBaCodChargesId(fixedChargesTO.getCodChargeId()[i - 1]);
					codTO.setPercentileValue(fixedChargesTO.getCodPercent()[i - 1]);
					codTO.setFixedChargeValue(fixedChargesTO
							.getFixedChargesEco()[i - 1]);
					codList.add(codTO);
				}
			}
		}
		return codList;
	}

	/**
	 * To save or update product slab rate and special destination.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @author hkansagr
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateBARatesDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction :: saveOrUpdateBARatesDtls() :: START");
		PrintWriter out = null;
		out = response.getWriter();
		
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			BARateConfigurationForm baRateConfgForm = (BARateConfigurationForm) form;
			BARateHeaderTO to = (BARateHeaderTO) baRateConfgForm.getTo();
			HttpSession session = request.getSession(false);
			
			String productCatCode = to.getCommonProdCatCode();
			Integer regionId = to.getRegionId();
			to.setProductCategory(productCatCode);

			Map<String, Integer> prodCateMap = (Map<String, Integer>) session.getAttribute("productCategoryMap");

			baRateConfigurationService = getbaRateConfigurationService();
			to.setLoggedInUserId(baRateConfigurationService.getLoggedInUserIdToSaveInDatabase(request));
			to = baRateConfigurationService._saveOrUpdateBARatesDtls(to, prodCateMap, session);

			// Sector Code By Region
			to.setSectorCode(getSectorCodeByRegion(request, regionId));

			// Setting product code
			to.setCommonProdCatCode(productCatCode);

			jsonResult = JSONSerializer.toJSON(to).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in BARateConfigurationAction :: saveOrUpdateBARatesDtls() :: CGBusinessException ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in BARateConfigurationAction :: saveOrUpdateBARatesDtls() :: CGSystemException ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BARateConfigurationAction :: saveOrUpdateBARatesDtls() :: Exception ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction :: saveOrUpdateBARatesDtls() :: END");
	}

	/**
	 * To search BA rate configuration - product level
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @author hkansagr
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked" })
	public void searchBARatesDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, CGBusinessException, IOException {
		LOGGER.trace("BARateConfigurationAction :: searchBARatesDtls() :: START ::");
		PrintWriter out = null;
		out = response.getWriter();
		
		String baRateHeaderTOJSON = CommonConstants.EMPTY_STRING;
		BARateHeaderTO to = null;

		Integer headerId = null;
		Integer productId = null;
		Integer regionId = null;
		Integer cityId = null;
		Integer baTypeId = null;
		String productCode = CommonConstants.EMPTY_STRING;
		String servicedOn = CommonConstants.EMPTY_STRING;

		// Header Id
		if (!StringUtil.isStringEmpty(request.getParameter("headerId"))) {
			headerId = Integer.parseInt(request.getParameter("headerId"));
		}
		// Product Id
		if (!StringUtil.isStringEmpty(request.getParameter("productId"))) {
			productId = Integer.parseInt(request.getParameter("productId"));
		}
		// Product Code - CO, AR, TR, PR
		if (!StringUtil.isStringEmpty(request.getParameter("productCode"))) {
			productCode = request.getParameter("productCode");
		}
		// Region Id
		if (!StringUtil.isStringEmpty(request.getParameter("regionId"))) {
			regionId = Integer.parseInt(request.getParameter("regionId"));
		}
		// City
		if (!StringUtil.isStringEmpty(request.getParameter("city"))) {
			cityId = Integer.parseInt(request.getParameter("city"));
		}
		// BA Type
		if (!StringUtil.isStringEmpty(request.getParameter("baType"))) {
			baTypeId = Integer.parseInt(request.getParameter("baType"));
		}
		// Serviced On
		if (!StringUtil.isStringEmpty(request.getParameter("servicedOn"))) {
			servicedOn = request.getParameter("servicedOn");
		}

		try {
			HttpSession session = request.getSession(false);

			Map<String, Integer> prodCateMap = (Map<String, Integer>) session
					.getAttribute("productCategoryMap");

			baRateConfigurationService = getbaRateConfigurationService();
			if (!StringUtil.isEmptyInteger(headerId)) {
				to = baRateConfigurationService._searchBARatesDtls(headerId,
						cityId, baTypeId, productId, servicedOn, prodCateMap, session);
			} else {
				to = baRateConfigurationService._searchBARatesDtls(cityId,
						baTypeId, productId, servicedOn, prodCateMap,session);
			}

			if (StringUtil.isNull(to)) {
				to = new BARateHeaderTO();
			}

			// Courier
			List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER);
			List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_COURIER);
			to.setSectorsList(sectorsList);
			to.setWtSlabList(wtSlabList);

			// Train
			wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_TRAIN);
			sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_TRAIN);
			to.setTrainSectorsList(sectorsList);
			to.setTrainWtSlabList(wtSlabList);

			// Air Cargo
			wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_AIR_CARGO);
			sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_AIR_CARGO);
			to.setAirCargoSectorsList(sectorsList);
			to.setAirCargoWtSlabList(wtSlabList);

			// Priority
			wtSlabList = (List<RateWeightSlabsTO>) session
					.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
			sectorsList = (List<RateSectorsTO>) session
					.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);
			to.setPrioritySectorsList(sectorsList);
			to.setPriorityWtSlabList(wtSlabList);

			// Sector Code By Region
			to.setSectorCode(getSectorCodeByRegion(request, regionId));

			// Setting product code
			to.setCommonProdCatCode(productCode);

			/*
			 * session.setAttribute(
			 * RateConfigurationConstants.BA_SLAB_SEARCH_RESULT, baRateHeader);
			 */
			baRateHeaderTOJSON = JSONSerializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BARateConfigurationAction :: searchBARatesDtls() ::",
					e);
		} finally {
			out.print(baRateHeaderTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("BARateConfigurationAction :: searchBARatesDtls() :: END ::");
	}

	@SuppressWarnings("unchecked")
	public void setBARateCommonValues(HttpServletRequest request,
			BARateHeaderTO to) {
		HttpSession session = request.getSession(false);

		// Courier
		List<RateWeightSlabsTO> wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_COURIER);
		List<RateSectorsTO> sectorsList = (List<RateSectorsTO>) session
				.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_COURIER);
		to.setSectorsList(sectorsList);
		to.setWtSlabList(wtSlabList);

		// Train
		wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_TRAIN);
		sectorsList = (List<RateSectorsTO>) session
				.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_TRAIN);
		to.setTrainSectorsList(sectorsList);
		to.setTrainWtSlabList(wtSlabList);

		// Air Cargo
		wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_AIR_CARGO);
		sectorsList = (List<RateSectorsTO>) session
				.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_AIR_CARGO);
		to.setAirCargoSectorsList(sectorsList);
		to.setAirCargoWtSlabList(wtSlabList);

		// Priority
		wtSlabList = (List<RateWeightSlabsTO>) session
				.getAttribute(RateConfigurationConstants.WEIGHT_SLAB_LIST_FOR_PRIORITY);
		sectorsList = (List<RateSectorsTO>) session
				.getAttribute(RateConfigurationConstants.RATE_SECTORS_FOR_PRIORITY);
		to.setPrioritySectorsList(sectorsList);
		to.setPriorityWtSlabList(wtSlabList);

		// Sector Code By Region
		// to.setSectorCode(getSectorCodeByRegion(request, regionId));

		// Setting product code
		// to.setCommonProdCatCode(productCode);
	}
}
