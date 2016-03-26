package com.ff.rate.configuration.rateConfiguration.action;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.geography.StateTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.action.AbstractRateAction;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.rateConfiguration.form.CashRateConfigurationForm;
import com.ff.rate.configuration.rateConfiguration.service.CashRateConfigurationService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.to.ratemanagement.masters.RateMinChargeableWeightTO;
import com.ff.to.ratemanagement.masters.RateSectorsTO;
import com.ff.to.ratemanagement.masters.RateWeightSlabsTO;
import com.ff.to.ratemanagement.masters.SectorTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigFixedChargesTO;
import com.ff.to.ratemanagement.operations.rateconfiguration.CashRateConfigHeaderTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.umc.UserInfoTO;


/**
 * @author hkansagr
 */

public class CashRateConfigurationAction extends AbstractRateAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CashRateConfigurationAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The cashRateConfigurationService. */
	private CashRateConfigurationService cashRateConfigurationService;
	
	/** The rateCommonService. */
	private RateCommonService rateCommonService;
	
	/**
	 * To view cash rate configuration
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view of cash rate configuration
	 * @throws Exception
	 */
	public ActionForward viewCashRateConfiguration(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::viewCashRateConfiguration()::START");
		CashRateConfigHeaderTO to=null;
		try {
			
			//CashRateConfigHeaderTO to = (CashRateConfigHeaderTO)cashForm.getTo();
			//CashRateConfigurationForm cashForm = (CashRateConfigurationForm)form;
			 //to = (CashRateConfigHeaderTO)cashForm.getTo();
			 to = new CashRateConfigHeaderTO();
			//saveToken(request);
			//((CashRateConfigurationForm)form).setTo(to);
		} catch (Exception e) {
			 to = new CashRateConfigHeaderTO();
			LOGGER.error("Exception occurs in CashRateConfigurationAction::viewCashRateConfiguration()::"
					+ e.getMessage());
		}finally{
			getCommonDefaultValues(request, to);
			((CashRateConfigurationForm) form).setTo(to);
		}
		
		LOGGER.debug("CashRateConfigurationAction::viewCashRateConfiguration()::END");
		return mapping.findForward(RateCommonConstants.VIEW_CASH_RATE_CONFIG);
	}
	
	/**
	 * To get common default values
	 * 
	 * @param request
	 * @param headerTO
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	private void getCommonDefaultValues(HttpServletRequest request, 
			CashRateConfigHeaderTO headerTO){
		try{
		LOGGER.trace("CashRateConfigurationAction::getCommonDefaultValues()::START");
		
		serializer = CGJasonConverter.getJsonObject();
		HttpSession session = request.getSession(false);
		rateCommonService = getRateCommonService();
				
		/** To get all region(s) */
		List<RegionTO> regionTOs = (List<RegionTO>) session
				.getAttribute(RateCommonConstants.PARAM_REGIONS);
		if (CGCollectionUtils.isEmpty(regionTOs)) {
			regionTOs = rateCommonService.getAllRegions();
			session.setAttribute(RateCommonConstants.PARAM_REGIONS, regionTOs);
		}
		request.setAttribute(RateCommonConstants.PARAM_REGIONS, regionTOs);
		
		/* COURIER */
		/** To get rate sector list - COURIER */
		List<RateSectorsTO> sectorListCourier = null;
		sectorListCourier = (List<RateSectorsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_CO);
		if (CGCollectionUtils.isEmpty(sectorListCourier)) {
			sectorListCourier = rateCommonService.getRateSectorList(
					RateCommonConstants.PRO_CODE_COURIER,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_CO, sectorListCourier);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_CO, sectorListCourier);
		
		/** To get weight slab list - COURIER */
		List<RateWeightSlabsTO> wtSlabListCourier = null;
		wtSlabListCourier = (List<RateWeightSlabsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_CO);
		if (CGCollectionUtils.isEmpty(wtSlabListCourier)) {
			wtSlabListCourier = rateCommonService.getRateWeightSlabList(
					RateCommonConstants.PRO_CODE_COURIER,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_CO, wtSlabListCourier);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_CO, wtSlabListCourier);
		
		/* AIR-CARGO */
		/** To get rate sector list - AIR-CARGO */
		List<RateSectorsTO> sectorListForAir = null;
		sectorListForAir = (List<RateSectorsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_AR);
		if (CGCollectionUtils.isEmpty(sectorListForAir)) {
			sectorListForAir = rateCommonService.getRateSectorList(
					RateCommonConstants.PRO_CODE_AIR_CARGO,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_AR, sectorListForAir);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_AR, sectorListForAir);
		
		/** To get weight slab list - AIR-CARGO */
		List<RateWeightSlabsTO> wtSlabListForAir = null;
		wtSlabListForAir = (List<RateWeightSlabsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_AR);
		if (CGCollectionUtils.isEmpty(wtSlabListForAir)) {
			wtSlabListForAir = rateCommonService.getRateWeightSlabList(
					RateCommonConstants.PRO_CODE_AIR_CARGO,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_AR, wtSlabListForAir);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_AR, wtSlabListForAir);
		
		/* TRAIN */
		/** To get rate sector list - TRAIN */
		List<RateSectorsTO> sectorListForTrain = null;
		sectorListForTrain = (List<RateSectorsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_TR);
		if (CGCollectionUtils.isEmpty(sectorListForTrain)) {
			sectorListForTrain = rateCommonService.getRateSectorList(
					RateCommonConstants.PRO_CODE_TRAIN,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_TR, sectorListForTrain);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_TR, sectorListForTrain);
		
		/** To get weight slab list - TRAIN */
		List<RateWeightSlabsTO> wtSlabListForTrain = null;
		wtSlabListForTrain = (List<RateWeightSlabsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_TR);
		if (CGCollectionUtils.isEmpty(wtSlabListForTrain)) {
			wtSlabListForTrain = rateCommonService.getRateWeightSlabList(
					RateCommonConstants.PRO_CODE_TRAIN,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_TR, wtSlabListForTrain);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_TR, wtSlabListForTrain);
		
		/* PRIORITY */
		/** To get rate sector list - PRIORITY */
		List<RateSectorsTO> sectorListForPriority = null;
		sectorListForPriority = (List<RateSectorsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_PR);
		if (CGCollectionUtils.isEmpty(sectorListForPriority)) {
			sectorListForPriority = rateCommonService.getRateSectorList(
					RateCommonConstants.PRO_CODE_PRIORITY,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_PR, sectorListForPriority);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_RATE_SECTORS_FOR_PR, sectorListForPriority);
		
		/** To get weight slab list - PRIORITY */
		List<RateWeightSlabsTO> wtSlabListForPriority = null;
		wtSlabListForPriority = (List<RateWeightSlabsTO>) session
				.getAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_PR);
		if (CGCollectionUtils.isEmpty(wtSlabListForPriority)) {
			wtSlabListForPriority = rateCommonService.getRateWeightSlabList(
					RateCommonConstants.PRO_CODE_PRIORITY,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_PR, wtSlabListForPriority);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_WT_SLAB_LIST_FOR_PR, wtSlabListForPriority);
		
		/** Origin Sector(s) for Non-Priority Product(s) For CASH Customer i.e. Air-Cargo, Train */
		List<RateSectorsTO> originSectorList = 
				getRateSectorsList(request, RateCommonConstants.CASH_RATE_CONFIG, 
						RateCommonConstants.RATE_PRO_CAT_TYPE_N, 
						RateCommonConstants.RATE_CUST_CATEGORY_CASH);
		request.setAttribute(RateCommonConstants.PARAM_ORIGIN_SECTOR_LIST,
				originSectorList);/* originSectorList */
		
		/** To get service-on list for priority product. */
		getStandardType(request, RateCommonConstants.STD_TYPE_NAME_SERVICED_ON, 
				RateCommonConstants.PARAM_SERVICED_ON_LIST);
		
		/** Fixed Charges - Octroi Born By */
		getStandardType(request, RateContractConstants.OCTROI_BORN_BY_TYPE_NAME, 
				RateContractConstants.OCTROI_BORN_BY);
		
		/** FFCL & Customer Percentile */
		List<InsuredByTO> insuredByTOs = (List<InsuredByTO>) session
				.getAttribute(RateCommonConstants.PARAM_INSURED_BY);
		if(StringUtil.isEmptyColletion(insuredByTOs)){
			insuredByTOs = rateCommonService.getInsuarnceBy();
			if(!StringUtil.isEmptyColletion(insuredByTOs)){
				session.setAttribute(RateCommonConstants.PARAM_INSURED_BY, insuredByTOs);
			}
		}
		if(!StringUtil.isEmptyColletion(insuredByTOs)){
			for(InsuredByTO insuredByTO : insuredByTOs){
				if(StringUtil.equals(insuredByTO.getInsuredByCode(), 
						RateCommonConstants.INSURED_BY_FFCL_CODE)) {
					request.setAttribute(RateCommonConstants.PARAM_FFCL_PERCENTILE, 
							insuredByTO.getPercentile());
				} else if (StringUtil.equals(insuredByTO.getInsuredByCode(), 
						RateCommonConstants.INSURED_BY_CUST_CODE)) {
					request.setAttribute(RateCommonConstants.PARAM_CUST_PERCENTILE, 
							insuredByTO.getPercentile());
				}
			}/* END FOR - InsuredByTO */
		}/* END IF - insuredByTOs */
		
		/** To get Minimum chargeable weight */
		List<RateMinChargeableWeightTO> minChargeableWeightList = (List<RateMinChargeableWeightTO>)session
				.getAttribute(RateCommonConstants.PARAM_CASH_MIN_CHRGL_WT_LIST);
		if(StringUtil.isEmptyColletion(minChargeableWeightList)){
			minChargeableWeightList = rateCommonService.getRateMinChrgWtList(RateCommonConstants.RATE_PRO_CAT_TYPE_N,
					RateCommonConstants.RATE_CUST_CATEGORY_CASH);
			session.setAttribute(RateCommonConstants.PARAM_CASH_MIN_CHRGL_WT_LIST, minChargeableWeightList);
		}
		request.setAttribute(RateCommonConstants.PARAM_CASH_MIN_CHRGL_WT_LIST, minChargeableWeightList);
		
		/** Load default Taxes */
		loadDefaultTaxComponent(request, headerTO);
		
		/** TODAYs Date/Current Date */
		request.setAttribute(RateContractConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		
		request.setAttribute(RateCommonConstants.PARAM_YES,
				CommonConstants.YES);/* Y-Yes */
		request.setAttribute(RateCommonConstants.PARAM_NO,
				CommonConstants.NO);/* N-No */
		request.setAttribute(RateCommonConstants.PARAM_DEST,
				RateCommonConstants.RATE_SEC_TYPE_DEST);/* D-Destination */
		request.setAttribute(RateCommonConstants.PARAM_ORIGIN,
				RateCommonConstants.RATE_SEC_TYPE_ORIGIN);/* O-Origin */
		request.setAttribute(RateContractConstants.PARAM_ACTIVE,
				RateContractConstants.ACTIVE);/* A-Active */
		request.setAttribute(RateContractConstants.PARAM_INACTIVE,
				RateContractConstants.INACTIVE);/* I-Inactive */
		
		/** Set Product Category Code i.e. CO-Courier, AR-Air-Cargo, TR-Train, PR-Priority */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CODE_COURIER,
				RateCommonConstants.PRO_CODE_COURIER);/* CO-Courier */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CODE_AIR_CARGO,
				RateCommonConstants.PRO_CODE_AIR_CARGO);/* AR-Air-Cargo */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CODE_TRAIN,
				RateCommonConstants.PRO_CODE_TRAIN);/* TR-Train */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CODE_PRIORITY,
				RateCommonConstants.PRO_CODE_PRIORITY);/* PR-Priority */
		
		/** Product Categoty Type i.e. N-Non-Priority, P-Priority */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CAT_TYPE_N, 
				RateCommonConstants.RATE_PRO_CAT_TYPE_N);/* N */
		request.setAttribute(RateCommonConstants.PARAM_PRO_CAT_TYPE_P, 
				RateCommonConstants.RATE_PRO_CAT_TYPE_P);/* P */
		
		List<StateTO> statesList = getStatesList(request);
		if(!CGCollectionUtils.isEmpty(statesList)){
			request.setAttribute("statesList",serializer.toJSON(statesList).toString());
		}
		}catch (Exception e) {
			
			LOGGER.error("Exception occurs in CashRateConfigurationAction::getCommonDefaultValues()::"
					+ e.getMessage());
		}
		LOGGER.trace("CashRateConfigurationAction::getCommonDefaultValues()::END");
	}
	
	/**
	 * To load default tax components
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void loadDefaultTaxComponent(HttpServletRequest request,
			CashRateConfigHeaderTO headerTO) throws CGSystemException,CGBusinessException {
		LOGGER.trace("CashRateConfigurationAction::loadDefaultTaxComponent()::START");
		HttpSession session = request.getSession(false);
		rateCommonService = getRateCommonService();
		
		/* To populate Tax Component for INDIA. */
		List<RateTaxComponentTO> indiaTaxCmptTOs = (List<RateTaxComponentTO>) 
				session.getAttribute(RateCommonConstants.PARAM_IND_TAX_CMPTS);
		if(StringUtil.isEmptyColletion(indiaTaxCmptTOs)){
			indiaTaxCmptTOs = rateCommonService
				.loadDefaultRateTaxComponentValue(null);
			session.setAttribute(RateCommonConstants.PARAM_IND_TAX_CMPTS, 
					indiaTaxCmptTOs);
		}
		
		/* To populate Tax Component for JAMMU & KASHMIR. */
		List<RateTaxComponentTO> jkTaxCmptTOs = (List<RateTaxComponentTO>) 
				session.getAttribute(RateCommonConstants.PARAM_JK_TAX_CMPTS);
		if(StringUtil.isEmptyColletion(jkTaxCmptTOs)){
			StateTO stateTO = rateCommonService
					.getStateByCode(RateCommonConstants.JAMMU_KASHMIR);
			if (!StringUtil.isNull(stateTO)) {
				jkTaxCmptTOs = rateCommonService
						.loadDefaultRateTaxComponentValue(stateTO.getStateId());
				session.setAttribute(RateCommonConstants.PARAM_JK_TAX_CMPTS, 
						jkTaxCmptTOs);
			}
		}
		
		/* Setting tax component values to BAMaterialTO */ 
		List<RateTaxComponentTO> taxCmptTOs = new ArrayList<RateTaxComponentTO>(
				indiaTaxCmptTOs.size()+jkTaxCmptTOs.size());
		/* INDIA */
		if(!StringUtil.isEmptyColletion(indiaTaxCmptTOs)){
			taxCmptTOs.addAll(indiaTaxCmptTOs);
		}
		/* J&K */
		if(!StringUtil.isEmptyColletion(jkTaxCmptTOs)){
			taxCmptTOs.addAll(jkTaxCmptTOs);
		}
		if(!StringUtil.isEmptyColletion(taxCmptTOs)){
			CashRateConfigFixedChargesTO fixedChargesTO = new CashRateConfigFixedChargesTO();
			CashRateConfigFixedChargesTO priorityFixedChargesTO = new CashRateConfigFixedChargesTO();
			for(RateTaxComponentTO taxCmptTO : taxCmptTOs){
				if(!StringUtil.isNull(taxCmptTO.getRateComponentId())){
					/* Service Tax */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX)){
						fixedChargesTO.setServiceTax(taxCmptTO.getTaxPercentile());
						priorityFixedChargesTO.setServiceTax(taxCmptTO.getTaxPercentile());
					}
					/* Edu. Cess */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS)){
						fixedChargesTO.setEduCess(taxCmptTO.getTaxPercentile());
						priorityFixedChargesTO.setEduCess(taxCmptTO.getTaxPercentile());
					}
					/* Higher Edu. Cess */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS)){
						fixedChargesTO.setHigherEduCess(taxCmptTO.getTaxPercentile());
						priorityFixedChargesTO.setHigherEduCess(taxCmptTO.getTaxPercentile());
					}
					/* State Tax */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX)){
						fixedChargesTO.setStateTax(taxCmptTO.getTaxPercentile());
						priorityFixedChargesTO.setStateTax(taxCmptTO.getTaxPercentile());
					}
					/* Surcharge On ST */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST)){
						fixedChargesTO.setSurchargeOnST(taxCmptTO.getTaxPercentile());
						priorityFixedChargesTO.setSurchargeOnST(taxCmptTO.getTaxPercentile());
					}
				}/* END INNER IF - taxCmptTO */
			}/* END FOR LOOP */
			headerTO.setFixedChargesTO(fixedChargesTO);
			headerTO.setPriorityFixedChargesTO(priorityFixedChargesTO);
		}/* END OUTER IF - taxCmptTOs */
		LOGGER.trace("CashRateConfigurationAction::loadDefaultTaxComponent()::END");
	}
	
	/**
	 * To save or update courier cash rate details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateCashRateProductDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateCashRateProductDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm) form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO) cashForm.getTo();
			cashRateConfigurationService = getCashRateConfigurationService();
			to = cashRateConfigurationService
					.saveOrUpdateCashRateProductDtls(to);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::saveOrUpdateCashRateProductDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateCashRateProductDtls()::END");
	}
	
	/**
	 * To save or update fixed charges details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateFixedChrgsDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateFixedChrgsDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm) form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO) cashForm.getTo();
			cashRateConfigurationService = getCashRateConfigurationService();
			to = cashRateConfigurationService
					.saveOrUpdateFixedChrgsDtls(to);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::saveOrUpdateFixedChrgsDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateFixedChrgsDtls()::END");
	}
	
	/**
	 * To save or update RTO charges details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateRTOChrgsDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateRTOChrgsDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm) form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO) cashForm.getTo();
			cashRateConfigurationService = getCashRateConfigurationService();
			to = cashRateConfigurationService
					.saveOrUpdateRTOChrgsDtls(to);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::saveOrUpdateRTOChrgsDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::saveOrUpdateRTOChrgsDtls()::END");
	}
	
	/**
	 * To search cash rate product details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void searchCashRateProductDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::searchCashRateProductDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm) form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO) cashForm.getTo();
			
			/* The Product Code i.e. CO- Courier, AR- Air-Cargo, TR- Train, PR- Priority */
			String productCode = request.getParameter(RateCommonConstants.PARAM_PRODUCT_CODE);
			to.setProductCode(productCode);
			
			cashRateConfigurationService = getCashRateConfigurationService();
			to = cashRateConfigurationService
					.searchCashRateProductDtls(to);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::searchCashRateProductDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::searchCashRateProductDtls()::END");
	}
	
	/**
	 * To search fixed charges details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void searchFixedChrgsDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::searchFixedChrgsDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			String productIdStr = request.getParameter(RateCommonConstants.PARAM_PRODUCT_MAP_ID);
			Integer productId = Integer.parseInt(productIdStr);
			String productType = request.getParameter("productType");
			cashRateConfigurationService = getCashRateConfigurationService();
			CashRateConfigHeaderTO to = cashRateConfigurationService
					.searchFixedChrgsDtls(productId, productType);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::searchFixedChrgsDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::searchFixedChrgsDtls()::END");
	}
	
	/**
	 * To search RTO charges details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void searchRTOChrgsDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::searchRTOChrgsDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			String productIdStr = request.getParameter(RateCommonConstants.PARAM_PRODUCT_MAP_ID);
			Integer productId = Integer.parseInt(productIdStr);
			String productType = request.getParameter("productType");
			cashRateConfigurationService = getCashRateConfigurationService();
			CashRateConfigHeaderTO to = cashRateConfigurationService
					.searchRTOChrgsDtls(productId,productType);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::searchRTOChrgsDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::searchRTOChrgsDtls()::END");
	}
	
	/**
	 * To submit cash rate details 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void submitCashRateDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::searchRTOChrgsDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			String cashRateId = request.getParameter(
					RateCommonConstants.PARAM_CASH_RATE_HEADER_ID);
			Integer cashRateHeaderId = Integer.parseInt(cashRateId);
			String fromDateStr = request.getParameter(
					RateCommonConstants.PARAM_FROM_DATE);
			String toDateStr = request.getParameter(
					RateCommonConstants.PARAM_TO_DATE);
			cashRateConfigurationService = getCashRateConfigurationService();
			boolean result = cashRateConfigurationService
					.submitCashRateDtls(cashRateHeaderId, fromDateStr, toDateStr);
			if(result){
				/** previous configuration id */
				String prevIdStr = request.getParameter(
						RateCommonConstants.PARAM_PREV_CASH_RATE_HEADER_ID);
				if(!StringUtil.isStringEmpty(prevIdStr)){
					Integer prevId = Integer.parseInt(prevIdStr);
					String toDateNew = decreaseDateByOne(fromDateStr);
					if(!StringUtil.isStringEmpty(toDateNew)){
						cashRateConfigurationService
								.updateCashRateConfigToDate(prevId, toDateNew);
					}
				}
				CashRateConfigHeaderTO to = new CashRateConfigHeaderTO();
				to.setTransMsg(CommonConstants.SUCCESS);
				jsonResult = serializer.toJSON(to).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::searchRTOChrgsDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::searchRTOChrgsDtls()::END");
	}
	
	/**
	 * To view (RENEW) cash rate configuration - RENEW
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view of cash rate configuration
	 * @throws Exception
	 */
	public ActionForward viewRenewCashRateConfiguration(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::viewRenewCashRateConfiguration()::START");
		try {
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm)form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO)cashForm.getTo();
			getCommonDefaultValues(request, to);
			String cashRateHeaderIdStr = request.getParameter(RateCommonConstants.PARAM_CASH_RATE_HEADER_ID);
			if(!StringUtil.isStringEmpty(cashRateHeaderIdStr)){
				to.setPrevCashRateHeaderId(Integer.parseInt(cashRateHeaderIdStr));
				to.setIsRenew(CommonConstants.YES);
			}
			String regionIdStr = request.getParameter(RateCommonConstants.PARAM_REGION_ID);
			if(!StringUtil.isStringEmpty(regionIdStr)){
				to.setRegionId(Integer.parseInt(regionIdStr));
				SectorTO secTO = cashRateConfigurationService
						.getOriginSectorByRegionId(Integer.parseInt(regionIdStr));
				if(!StringUtil.isNull(secTO) && !StringUtil.isNull(secTO.getSectorId())){
					to.setSectorId(secTO.getSectorId());
				}
			}
			saveToken(request);
			((CashRateConfigurationForm)form).setTo(to);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::viewRenewCashRateConfiguration()::"
					+ e.getMessage());
		}
		LOGGER.debug("CashRateConfigurationAction::viewRenewCashRateConfiguration()::END");
		return mapping.findForward(RateCommonConstants.VIEW_RENEW_CASH_RATE_CONFIG);
	}
	
	@SuppressWarnings("static-access")
	public void getOriginSectorByRegionId(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::getOriginSectorByRegionId()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			String regionIdStr = request.getParameter(
					RateCommonConstants.PARAM_REGION_ID);
			Integer regionId = Integer.parseInt(regionIdStr);
			cashRateConfigurationService = getCashRateConfigurationService();
			SectorTO secTO = cashRateConfigurationService
					.getOriginSectorByRegionId(regionId);
			if(!StringUtil.isNull(secTO)){
				jsonResult = serializer.toJSON(secTO).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::getOriginSectorByRegionId()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::getOriginSectorByRegionId()::END");
	}
	
	/**
	 * To decrease date by one 
	 * @param fromDt
	 * @return String - toDtStr
	 * @throws Exception
	 */
	private String decreaseDateByOne(String fromDt) {
		String toDtStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(fromDt));
			c.add(Calendar.DATE, -1);/* Number of days to reduce */
			toDtStr = sdf.format(c.getTime());/* toDtStr is now the new date */
		} catch (ParseException e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigServiceImpl::decreaseDateByOne()::"
				+ e.getMessage());
		}
		return toDtStr;
	}
	
	/**
	 * To get Cash Rate Configuration Service
	 * 
	 * @return cashRateConfigurationService
	 */
	private CashRateConfigurationService getCashRateConfigurationService(){
		if(cashRateConfigurationService==null){
			cashRateConfigurationService = (CashRateConfigurationService)
					getBean(RateSpringConstants.CASH_RATE_CONFIGURATION_SERVICE);
		}
		return cashRateConfigurationService;
	}
	
	/**
	 * To view cash rate configuration - at RHO (for UDAAN-WEB)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view of cash rate configuration
	 * @throws Exception
	 */
	public ActionForward viewCashRateConfigAtRHO(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::viewCashRateConfigAtRHO()::START");
		try {
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm)form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO)cashForm.getTo();
			getCommonDefaultValues(request, to);
			
			/* get User Info from session attribute */
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfo = (UserInfoTO)session
					.getAttribute(RateContractConstants.USER_INFO);
			
			/* setting login officeId * regionId */
			OfficeTO officeTO = userInfo.getOfficeTo();
			to.setRegionId(officeTO.getRegionTO().getRegionId());
			
			saveToken(request);
			((CashRateConfigurationForm)form).setTo(to);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::viewCashRateConfigAtRHO()::"
					,e);
		}
		LOGGER.debug("CashRateConfigurationAction::viewCashRateConfigAtRHO()::END");
		return mapping.findForward(RateCommonConstants.VIEW_CASH_RATE_CONFIG_AT_RHO);
	}
	
	/**
	 * To get current period cash rate configuration 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void getCurrentPeriodCashConfig(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CashRateConfigurationAction::getCurrentPeriodCashConfig()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			CashRateConfigurationForm cashForm = (CashRateConfigurationForm) form;
			CashRateConfigHeaderTO to = (CashRateConfigHeaderTO) cashForm.getTo();
			
			cashRateConfigurationService = getCashRateConfigurationService();
			to = cashRateConfigurationService
					.searchCashRateProductDtls(to);
			jsonResult = serializer.toJSON(to).toString();
		} catch (Exception e) {
			LOGGER.error("Exception occurs in CashRateConfigurationAction::getCurrentPeriodCashConfig()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("CashRateConfigurationAction::getCurrentPeriodCashConfig()::END");
	}

}
