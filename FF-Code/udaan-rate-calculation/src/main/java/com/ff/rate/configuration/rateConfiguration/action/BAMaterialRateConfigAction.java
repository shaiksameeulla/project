package com.ff.rate.configuration.rateConfiguration.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.StateTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.rateConfiguration.form.BAMaterialRateConfigForm;
import com.ff.rate.configuration.rateConfiguration.service.BAMaterialRateConfigService;
import com.ff.rate.configuration.ratecontract.constants.RateContractConstants;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.to.ratemanagement.operations.rateconfiguration.BAMaterialRateConfigTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author hkansagr
 */

public class BAMaterialRateConfigAction extends CGBaseAction{

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BAMaterialRateConfigAction.class);
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/** The rateCommonService. */
	public RateCommonService rateCommonService;
	
	/** The baMaterialRateConfigService. */
	public BAMaterialRateConfigService baMaterialRateConfigService;
	
	
	/**
	 * To view BA Material Rate
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view baMaterialRate
	 * @throws Exception
	 */
	public ActionForward viewBAMaterialRateConfig(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BAMaterialRateConfigAction::viewBAMaterialRateConfig()::START");
		try {
			BAMaterialRateConfigTO to = new BAMaterialRateConfigTO();
			getDefaultValues(request, to);
			/* To search current tariff details */
			baMaterialRateConfigService = getBAMaterialRateConfigService();
			to = baMaterialRateConfigService.searchBAMaterialRateDtls(to);
			saveToken(request);
			((BAMaterialRateConfigForm)form).setTo(to);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigAction::viewBAMaterialRateConfig()::"
					+ e.getMessage());
		}
		LOGGER.debug("BAMaterialRateConfigAction::viewBAMaterialRateConfig()::END");
		return mapping.findForward(RateCommonConstants.VIEW_BA_MATERIAL_RATE_CONFIG);
	}
	
	/**
	 * To get default values for BA Material Rate
	 * 
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getDefaultValues(HttpServletRequest request, 
			BAMaterialRateConfigTO to) throws Exception {
		LOGGER.debug("BAMaterialRateConfigAction::getDefaultValues()::START");
		HttpSession session = request.getSession(false);
		
		/** To get user info i.e. office, user etc. */
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(RateContractConstants.USER_INFO);
		
		/** Logged In Office Id */
		OfficeTO officeTO = userInfo.getOfficeTo();
		to.setLoggedInOfficeId(officeTO.getOfficeId());
		
		UserTO userTO = userInfo.getUserto();
		if(!StringUtil.isNull(userTO)){
			/** Created By */
			if(StringUtil.isNull(to.getCreatedBy())){
				to.setCreatedBy(userTO.getUserId());
			}
			/** Updated By */
			to.setUpdatedBy(userTO.getUserId());
		}
		
		rateCommonService = getRateCommonService();
		
		/** Prepare Item Type Map. */
		Map<Integer, String> itemTypeMap = (Map<Integer,String>) session
				 .getAttribute(RateCommonConstants.PARAM_ITEM_TYPE);
		if(CGCollectionUtils.isEmpty(itemTypeMap)) {
			itemTypeMap = rateCommonService.getItemTypeAsMap();
			session.setAttribute(RateCommonConstants.PARAM_ITEM_TYPE, itemTypeMap);
		}
		request.setAttribute(RateCommonConstants.PARAM_ITEM_TYPE, itemTypeMap);
		
		/** Load default Taxes */
		loadDefaultTaxComponent(request, to);
		
		/** TODAYs Date/Current Date */
		request.setAttribute(RateContractConstants.PARAM_TODAY_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute(RateCommonConstants.PARAM_YES, CommonConstants.YES);/* Y */
		request.setAttribute(RateCommonConstants.PARAM_NO, CommonConstants.NO);/* N */
		/** To Set LoggedInDate/CurrentDate */
		to.setLoggedInDateStr(DateUtil.getCurrentDateInDDMMYYYY());
		LOGGER.debug("BAMaterialRateConfigAction::getDefaultValues()::END");
	}
	
	/**
	 * To load default tax components
	 * @param request
	 * @param to
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void loadDefaultTaxComponent(HttpServletRequest request,
			BAMaterialRateConfigTO to) throws Exception {
		LOGGER.debug("BAMaterialRateConfigAction::loadDefaultTaxComponent()::START");
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
			for(RateTaxComponentTO taxCmptTO : taxCmptTOs){
				if(!StringUtil.isNull(taxCmptTO.getRateComponentId())){
					/* Service Tax */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_SERVICE_TAX)){
						to.setServiceTax(taxCmptTO.getTaxPercentile());
					}
					/* Edu. Cess */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_EDUCATION_CESS)){
						to.setEduCess(taxCmptTO.getTaxPercentile());
					}
					/* Higher Edu. Cess */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_HIGHER_EDUCATION_CESS)){
						to.setHigherEduCess(taxCmptTO.getTaxPercentile());
					}
					/* State Tax */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_STATE_TAX)){
						to.setStateTax(taxCmptTO.getTaxPercentile());
					}
					/* Surcharge On ST */
					if(taxCmptTO.getRateComponentId()
							.getRateComponentCode().equalsIgnoreCase(
									RateCommonConstants.RATE_COMPONENT_TYPE_SURCHARGE_ON_ST)){
						to.setSurchargeOnST(taxCmptTO.getTaxPercentile());
					}
				}/* END INNER IF - taxCmptTO */
			}/* END FOR LOOP */
		}/* END OUTER IF - taxCmptTOs */
		LOGGER.debug("BAMaterialRateConfigAction::loadDefaultTaxComponent()::END");
	}
	
	/**
	 * To save BA Material Rate Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveBAMaterialRateDtls(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BAMaterialRateConfigAction::saveBAMaterialRateDtls()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		serializer = CGJasonConverter.getJsonObject();
		try {
			out = response.getWriter();
			BAMaterialRateConfigForm baMtrlForm = (BAMaterialRateConfigForm)form;
			BAMaterialRateConfigTO to = (BAMaterialRateConfigTO) baMtrlForm.getTo();
			baMaterialRateConfigService = getBAMaterialRateConfigService();
			boolean result = baMaterialRateConfigService.saveBAMaterialRateDtls(to);
			if(result){
				jsonResult = serializer.toJSON(to).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigAction::saveBAMaterialRateDtls()::"
					+ e.getMessage());
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BAMaterialRateConfigAction::saveBAMaterialRateDtls()::END");
	}
	
	/**
	 * To get item list by item type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getItemByTypeMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BAMaterialRateConfigAction::getItemByTypeMap()::START");
		PrintWriter out = null;
		Map<Integer,String> itemMap = null;
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			String itemType = request.getParameter(RateCommonConstants.PARAM_TYPE_ID);
			if(StringUtil.isInteger(itemType)){
				Integer itemTypeId = StringUtil.parseInteger(itemType);
				rateCommonService = getRateCommonService();
				itemMap = rateCommonService.getItemByTypeAsMap(itemTypeId);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigAction::getItemByTypeMap()::"
				+e.getMessage());
		} finally {
			out.print(CollectionUtils.isEmpty(itemMap)?null:itemMap.toString());
			out.flush();
			out.close();
		}
		LOGGER.debug("BAMaterialRateConfigAction::getItemByTypeMap()::END");
	}
	
	/**
	 * To get item details by itemId and itemTypeId
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void getItemDtlsByItemIdAndTypeId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BAMaterialRateConfigAction::getItemDtlsByItemIdAndTypeId()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		ItemTO itemTO = null;
		try {
			out = response.getWriter();
			response.setContentType("text/javascript");
			String itemType = request.getParameter(RateCommonConstants.PARAM_ITEM_TYPE_ID);
			String item = request.getParameter(RateCommonConstants.PARAM_ITEM_ID);
			serializer = CGJasonConverter.getJsonObject();
			if(StringUtil.isInteger(itemType) && StringUtil.isInteger(item)){
				Integer itemId = StringUtil.parseInteger(item);
				Integer itemTypeId= StringUtil.parseInteger(itemType);
				rateCommonService = getRateCommonService();
				itemTO = rateCommonService.getItemByItemTypeAndItemId(itemTypeId, itemId);
			}
			if(!StringUtil.isNull(itemTO)){
				jsonResult = serializer.toJSON(itemTO).toString();
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigAction::getItemDtlsByItemIdAndTypeId()::"
				+e.getMessage());
			try {
				JSONObject detailObj = new JSONObject(); 
				detailObj.put(StockUniveralConstants.RESP_ERROR, StockUniveralConstants.RESP_ERROR_MSG);
				jsonResult  = detailObj.toString();
			} catch (Exception jsonExcep) {
				LOGGER.error("Exception occurs in BAMaterialRateConfigAction::getItemDtlsByItemIdAndTypeId()::"
						+jsonExcep.getMessage());
			}
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BAMaterialRateConfigAction::getItemDtlsByItemIdAndTypeId()::END");
	}
	
	/**
	 * To renew BA Material Rate Configuration
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view baMaterialRateConfig.jsp
	 * @throws Exception
	 */
	public ActionForward renewBAMaterialRateConfig(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BAMaterialRateConfigAction::renewBAMaterialRateConfig()::START");
		try {
			BAMaterialRateConfigTO to = new BAMaterialRateConfigTO();
			Long baMtrlRateId = 0L;
			String baMtrlRate = request.getParameter(RateCommonConstants.PARAM_BA_MTRL_RATE_ID);
			if(!StringUtil.isStringEmpty(baMtrlRate)){
				baMtrlRateId = Long.parseLong(baMtrlRate);
			}
			if(!StringUtil.isEmptyLong(baMtrlRateId)){
				to.setPrevBAMtrlRateId(baMtrlRateId);
			}
			to.setIsRenew(CommonConstants.YES);
			getDefaultValues(request, to);
			baMaterialRateConfigService = getBAMaterialRateConfigService();
			to = baMaterialRateConfigService.searchRenewedBAMaterialRateDtls(to);
			saveToken(request);
			((BAMaterialRateConfigForm)form).setTo(to);
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BAMaterialRateConfigAction::renewBAMaterialRateConfig()::"
					+ e.getMessage());
		}
		LOGGER.debug("BAMaterialRateConfigAction::renewBAMaterialRateConfig()::END");
		return mapping.findForward(RateCommonConstants.RENEW_BA_MATERIAL_RATE_CONFIG);
	}
	
	/**
	 * To get RateCommonService
	 * @return rateCommonService
	 */
	public RateCommonService getRateCommonService()	{
		if(StringUtil.isNull(rateCommonService)) {
			rateCommonService = (RateCommonService)
					getBean(RateSpringConstants.RATE_COMMON_SERVICE);
		}
		return rateCommonService;
	}
	
	/**
	 * To get BAMaterialRateConfigService
	 * @return baMaterialRateConfigService
	 */
	public BAMaterialRateConfigService getBAMaterialRateConfigService()	{
		if(StringUtil.isNull(baMaterialRateConfigService)) {
			baMaterialRateConfigService = (BAMaterialRateConfigService)
					getBean(RateSpringConstants.BA_MATERIAL_RATE_CONFIG_SERVICE);
		}
		return baMaterialRateConfigService;
	}
	
}
