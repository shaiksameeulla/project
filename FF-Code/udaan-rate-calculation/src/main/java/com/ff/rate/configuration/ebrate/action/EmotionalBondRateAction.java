package com.ff.rate.configuration.ebrate.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CountryTO;
import com.ff.geography.StateTO;
import com.ff.rate.configuration.common.constants.RateCommonConstants;
import com.ff.rate.configuration.common.constants.RateErrorConstants;
import com.ff.rate.configuration.common.service.RateCommonService;
import com.ff.rate.configuration.ebrate.constants.EmotionalBondRateConstants;
import com.ff.rate.configuration.ebrate.form.EBRateConfigForm;
import com.ff.rate.configuration.ebrate.service.EmotionalBondRateService;
import com.ff.rate.constants.RateSpringConstants;
import com.ff.to.ratemanagement.operations.ratequotation.EBRateConfigTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;

public class EmotionalBondRateAction extends CGBaseAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EmotionalBondRateAction.class);
	private RateCommonService rateCommonService;
	private EmotionalBondRateService emotionalBondRateService;

	/**
	 * Creates the configEBRate
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return : Forwarding viewEmotionalBondRateConfig page
	 * @throws CGBaseException
	 *             the cG base exception
	 * @Method : configEBRate
	 * @Desc :
	 */
	public ActionForward viewEmotionalBondRateConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {

		try {
			LOGGER.trace("EmotionalBondRateAction::viewEmotionalBondRateConfig::START------------>:::::::");
			EBRateConfigTO configTO = null;
			EBRateConfigForm configForm = (EBRateConfigForm) form;
			configTO = (EBRateConfigTO) configForm.getTo();
			List<LabelValueBean> origin = new ArrayList<LabelValueBean>();
			prepareStates(origin, configTO, request);

			request.setAttribute("states", origin);
			configTO.setOriginList(origin);

			request.setAttribute("jammuKashmir",
					RateCommonConstants.JAMMU_KASHMIR);
			request.setAttribute("india", RateCommonConstants.INDIA);
			request.setAttribute(EmotionalBondRateConstants.PARAM_TODAY_DATE,
					DateUtil.getCurrentDateInDDMMYYYY());
			request.setAttribute("SUBMITED",
					EmotionalBondRateConstants.SUBMITED);
			request.setAttribute("CREATED", EmotionalBondRateConstants.CREATED);
			request.setAttribute("RENEWED", EmotionalBondRateConstants.RENEWED);
			request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
			request.setAttribute("SUCCESS_FLAG",
					FrameworkConstants.SUCCESS_FLAG);

			configForm.setTo(configTO);
		} catch (Exception e) {
			LOGGER.error("Error occured in EmotionalBondRateAction :: viewEmotionalBondRateConfig() ::"
					+ e.getMessage());
		}
		LOGGER.trace("EmotionalBondRateAction::viewEmotionalBondRateConfig::END------------>:::::::");
		return mapping.findForward("viewEmotionalBondRateConfig");

	}

	private void prepareStates(List<LabelValueBean> origin,
			EBRateConfigTO configTO, HttpServletRequest request) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("EmotionalBondRateAction::prepareStates::START------------>:::::::");
		StateTO stateTO = null;
		CountryTO countryTO = null;
		rateCommonService = (RateCommonService) getBean(RateSpringConstants.RATE_COMMON_SERVICE);
		stateTO = rateCommonService
				.getStateByCode(RateCommonConstants.JAMMU_KASHMIR);
		countryTO = rateCommonService
				.getCountryByCode(RateCommonConstants.INDIA);

		if (!StringUtil.isNull(countryTO)) {
			LabelValueBean name = new LabelValueBean();
			name.setLabel(countryTO.getCountryName());
			name.setValue(null);
			origin.add(name);
		}
		if (!StringUtil.isNull(stateTO)) {
			LabelValueBean name = new LabelValueBean();
			configTO.setStateCode(stateTO.getStateCode());
			name.setLabel(stateTO.getStateName());
			name.setValue(stateTO.getStateId().toString());
			request.setAttribute("stateId", stateTO.getStateId());			
			origin.add(name);
		}
		LOGGER.trace("EmotionalBondRateAction::prepareStates::END------------>:::::::");
	}

	public void deactivatePreferences(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Boolean transStatus = Boolean.FALSE;
		String jsonResult = null;
		PrintWriter out = null;
		try {
			LOGGER.trace("EmotionalBondRateAction::deactivatePreferences::START------------>:::::::");
			out = response.getWriter();
			emotionalBondRateService = getEmotionalBondRateService();
			String prefId = request
					.getParameter(EmotionalBondRateConstants.PREF_ID);

			if (StringUtils.isNotEmpty(prefId)) {
				List<Integer> prefIds = StringUtil
						.parseIntegerList(prefId, ",");
				transStatus = emotionalBondRateService
						.deactivatePreferences(prefIds);
			}

			if (!transStatus) {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.PREFERENCES_NOT_DEACTIVATED_SUCCESSFULLY,
								null));
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						getMessageFromErrorBundle(
								request,
								RateErrorConstants.PREFERENCES_DEACTIVATED_SUCCESSFULLY,
								null));

			}

		} catch (CGBusinessException e) {
			LOGGER.error("EmotionalBondRateAction::deactivatePreferences()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("EmotionalBondRateAction::deactivatePreferences()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateAction::deactivatePreferences()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("EmotionalBondRateAction::deactivatePreferences::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void loadDefaultTaxComponent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String jsonResult = "";
		String stateId = null;
		try {
			LOGGER.trace("EmotionalBondRateAction::loadDefaultTaxComponent::START------------>:::::::");
			JSONSerializer serializer = CGJasonConverter.getJsonObject();
			List<RateTaxComponentTO> rateTaxComponentTO = new ArrayList<RateTaxComponentTO>();
			if (!StringUtil.isNull(request
					.getParameter(EmotionalBondRateConstants.STATE_ID))) {
				stateId = request
						.getParameter(EmotionalBondRateConstants.STATE_ID);
			}
			emotionalBondRateService = getEmotionalBondRateService();
			out = response.getWriter();

			rateTaxComponentTO = emotionalBondRateService
					.loadDefaultTaxComponent(stateId);

			jsonResult = serializer.toJSON(rateTaxComponentTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultTaxComponent()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultTaxComponent()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultTaxComponent()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("EmotionalBondRateAction::loadDefaultTaxComponent::END------------>:::::::");
	}

	public void saveOrUpdateEBRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EBRateConfigForm ebRateConfigForm = null;
		EBRateConfigTO ebRateConfigTO = null;
		PrintWriter out = null;
		String jsonResult = "";
		boolean isSaved = Boolean.FALSE;
		try {
			LOGGER.trace("EmotionalBondRateAction::saveOrUpdateEBRate::START------------>:::::::");
			out = response.getWriter();
			ebRateConfigForm = (EBRateConfigForm) form;
			ebRateConfigTO = (EBRateConfigTO) ebRateConfigForm.getTo();

			if (ebRateConfigTO != null) {
				emotionalBondRateService = getEmotionalBondRateService();
				isSaved = emotionalBondRateService
						.saveOrUpdateEBRate(ebRateConfigTO);

			}

			jsonResult = setMessageForSave(request, ebRateConfigTO, isSaved);
		} catch (CGBusinessException e) {
			LOGGER.error("EmotionalBondRateAction::saveOrUpdateEBRate()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("EmotionalBondRateAction::saveOrUpdateEBRate()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateAction::saveOrUpdateEBRate()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("EmotionalBondRateAction::saveOrUpdateEBRate::END------------>:::::::");

	}

	private String setMessageForSave(HttpServletRequest request,
			EBRateConfigTO ebRateConfigTO, boolean isSaved) {
		String jsonResult;
		if (!StringUtil.isNull(ebRateConfigTO.getSaveMode())
				&& ebRateConfigTO.getSaveMode().equalsIgnoreCase(
						EmotionalBondRateConstants.CREATED)) {
			if (!isSaved) {
				jsonResult = getMessageFromErrorBundle(request,
						RateErrorConstants.RATES_NOT_SAVED_SUCCESSFULLY, null);
			} else {
				ebRateConfigTO.setSuccessMessage(getMessageFromErrorBundle(
						request, RateErrorConstants.RATES_SAVED_SUCCESSFULLY,
						null));

			}
		} else if (!StringUtil.isNull(ebRateConfigTO.getSaveMode())
				&& ebRateConfigTO.getSaveMode().equalsIgnoreCase(
						EmotionalBondRateConstants.SUBMITED)) {
			if (!isSaved) {
				jsonResult = getMessageFromErrorBundle(request,
						RateErrorConstants.RATES_NOT_SUBMITTED_SUCCESSFULLY,
						null);
			} else {
				ebRateConfigTO
						.setSuccessMessage(getMessageFromErrorBundle(
								request,
								RateErrorConstants.RATES_SUBMITTED_SUCCESSFULLY,
								null));

			}
		} else {
			if (!isSaved) {
				jsonResult = getMessageFromErrorBundle(request,
						RateErrorConstants.RATES_NOT_RENEWED_SUCCESSFULLY,
						null);
			} else {
				ebRateConfigTO.setSuccessMessage(getMessageFromErrorBundle(
						request,
						RateErrorConstants.RATES_RENEWED_SUCCESSFULLY, null));

			}
		}
		jsonResult = JSONSerializer.toJSON(ebRateConfigTO).toString();
		return jsonResult;
	}

	@SuppressWarnings("static-access")
	public void loadDefaultEBRates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String jsonResult = "";
		String stateId = null;
		String action = null;
		String ebRateConfigId = null;
		try {
			LOGGER.trace("EmotionalBondRateAction::loadDefaultEBRates::START------------>:::::::");
			JSONSerializer serializer = CGJasonConverter.getJsonObject();
			EBRateConfigTO ebRateConfigTO = new EBRateConfigTO();
			if (!StringUtil.isNull(request
					.getParameter(EmotionalBondRateConstants.STATE_ID))) {
				stateId = request
						.getParameter(EmotionalBondRateConstants.STATE_ID);
			}
			if (!StringUtil.isNull(request
					.getParameter(EmotionalBondRateConstants.ACTION))) {
				action = request
						.getParameter(EmotionalBondRateConstants.ACTION);
			}
			if (!StringUtil.isNull(request
					.getParameter(EmotionalBondRateConstants.PARAM_EB_RATE_CONFIG_ID))) {
				ebRateConfigId = request
						.getParameter(EmotionalBondRateConstants.PARAM_EB_RATE_CONFIG_ID);
			}
			emotionalBondRateService = getEmotionalBondRateService();
			out = response.getWriter();

			ebRateConfigTO = emotionalBondRateService
					.loadDefaultEBRates(stateId,action,ebRateConfigId);

			jsonResult = serializer.toJSON(ebRateConfigTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultEBRates()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultEBRates()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("EmotionalBondRateAction::loadDefaultEBRates()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("EmotionalBondRateAction::loadDefaultEBRates::END------------>:::::::");
	}

	/**
	 * get RateBenchMarkService Object
	 * 
	 * @return
	 */
	public EmotionalBondRateService getEmotionalBondRateService() {
		if (StringUtil.isNull(emotionalBondRateService)) {
			emotionalBondRateService = (EmotionalBondRateService) getBean(RateSpringConstants.EMOIONAL_BOND_RATE_SERVICE);
		}
		return emotionalBondRateService;
	}
}
