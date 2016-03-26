package com.ff.web.manifest.misroute.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.manifest.misroute.MisrouteTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.manifest.constant.ManifestUniversalConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.misroute.converter.MisrouteConverter;
import com.ff.web.manifest.misroute.form.MisrouteForm;
import com.ff.web.manifest.misroute.service.MisrouteService;

/**
 * @author preegupt
 * 
 */
public class MisrouteAction extends CGBaseAction {

	private MisrouteService misrouteService;

	public transient JSONSerializer serializer;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MisrouteAction.class);

	/**
	 * @Desc : For Preparing the page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to misroute view
	 */
	public ActionForward viewMisroute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			LOGGER.trace("MisrouteAction:: viewMisroute()::START------------>:::::::");
			MisrouteTO misrouteTO = new MisrouteTO();

			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			String officeType = request.getParameter(ManifestConstants.OFFICE);
			request.setAttribute(ManifestConstants.OFFICE, officeType);
			misrouteTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			misrouteTO.setUpdatedBy(userInfoTO.getUserto().getUserId());

			OfficeTO officeTO = userInfoTO.getOfficeTo();
			Integer loggedInOfficeId = officeTO.getOfficeId();
			Integer loginRegionId = officeTO.getRegionTO().getRegionId();
			request.setAttribute("loginRegionId", loginRegionId);
			String loginRegionCode = officeTO.getRegionTO().getRegionCode();
			request.setAttribute("loginRegionCode", loginRegionCode);
			String loginOfficeName = officeTO.getOfficeName();
			String officeCodeProcess = officeTO.getOfficeCode();
			Integer loggedReportingHUB = officeTO.getReportingHUB();
			misrouteTO.setOfficeType(officeTO.getOfficeTypeTO()
					.getOffcTypeCode());

			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			Integer loggedincity = officeTO.getCityId();
			CityTO cityTo = new CityTO();
			cityTo.setCityId(loggedincity);
			request.setAttribute(ManifestConstants.LOGGED_IN_CITY, loggedincity);
			request.setAttribute(ManifestConstants.LOGGED_IN_REPORTING_HUB,
					loggedReportingHUB);
			cityTo = misrouteService.getCityByCityId(cityTo);
			String loginCityCode = cityTo.getCityCode();
			request.setAttribute(ManifestConstants.LOGIN_CITY_CODE,
					loginCityCode);

			// get logged in region
			Integer regionId = cityTo.getRegion();
			RegionTO region = misrouteService
					.getRegionDetailsByRegionId(regionId);
			String regionName = region.getRegionName();
			String loginRegionOffice = regionName + CommonConstants.HYPHEN
					+ loginOfficeName;
			misrouteTO.setOfficeCode(loggedInOfficeId);
			misrouteTO.setOfficeName(loginRegionOffice);
			misrouteTO.setDestinationRegionId(regionId);
			misrouteTO.setMisrouteDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat());
			request.setAttribute(ManifestConstants.LOGIN_OFFICE_ID,
					misrouteTO.getOfficeCode());
			request.setAttribute(ManifestConstants.MANIFEST_TYPE,
					misrouteTO.getManifestDirection());

			request.setAttribute(ManifestConstants.OFFICE_CODE_PROCESS,
					officeCodeProcess);
			// setting logged in region id and name
			request.setAttribute("regionName", loginRegionOffice);

			misrouteTO.setDestinationRegionId(regionId);
			misrouteTO.setOfficeCodeProcess(officeCodeProcess);

			ManifestRegionTO manifestRegionTO = null;
			if (regionId != null) {
				manifestRegionTO = new ManifestRegionTO();
				RegionTO regionTO = new RegionTO();
				regionTO.setRegionId(regionId);
				manifestRegionTO.setRegionTO(regionTO);
			}
			setCities(request, loggedincity, manifestRegionTO);

			setOfficeType(request, misrouteTO);

			request.setAttribute(ManifestConstants.REGION_ID, regionId);
			setRegion(request, misrouteTO);

			setMisrouteType(request);
			setConsgType(request);
			setRequestParam(request);

			((MisrouteForm) form).setTo(misrouteTO);
			session.setAttribute("misrouteTO4Print", misrouteTO);

		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: viewMisroute() ::Exception :"
					+ e.getMessage());
		}
		LOGGER.trace("MisrouteAction:: viewMisroute()::END------------>:::::::");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	private void setCities(HttpServletRequest request, Integer loggedincity,
			ManifestRegionTO manifestRegionTO) throws CGBusinessException,
			CGSystemException {
		try {
			List<CityTO> cityTOs = misrouteService
					.getCitiesByRegion(manifestRegionTO);
			if (!CGCollectionUtils.isEmpty(cityTOs)) {
				request.setAttribute("cityTOs", cityTOs);
			} else {
				prepareActionMessage(request, new ActionMessage(
						ManifestErrorCodesConstants.DETAILS_DOES_NOT_EXIST,
						ManifestErrorCodesConstants.CITY_DTLS_NOT_EXIST));
				LOGGER.warn("MisrouteAction:: setCities :: city Details Does not exist");
			}

			List<OfficeTO> stationTOs = misrouteService
					.getAllOffices(loggedincity);
			if (!CGCollectionUtils.isEmpty(stationTOs)) {
				request.setAttribute(ManifestConstants.STATIONTOs, stationTOs);
			} else {
				prepareActionMessage(request, new ActionMessage(
						ManifestErrorCodesConstants.DETAILS_DOES_NOT_EXIST,
						ManifestErrorCodesConstants.OFFICE_DTLS_NOT_EXIST));
				LOGGER.warn("MisrouteAction:: setCities :: Office Details Does not exist");

			}
		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: setCities()::Exception", e);

		}

	}

	private void setRegion(HttpServletRequest request, MisrouteTO misrouteTO)
			throws CGBusinessException, CGSystemException {
		List<RegionTO> regionTOs = null;
		try {
			regionTOs = misrouteService.getAllRegions();
			if (!CGCollectionUtils.isEmpty(regionTOs)) {
				request.setAttribute(ManifestConstants.REGIONTOs, regionTOs);
				misrouteTO.setDestinationRegionList(regionTOs);
			} else {
				prepareActionMessage(request, new ActionMessage(
						ManifestErrorCodesConstants.DETAILS_DOES_NOT_EXIST,
						ManifestErrorCodesConstants.REGION_DTLS_NOT_EXIST));
				LOGGER.warn("MisrouteAction:: setRegion :: Region Details Does not exist");
			}
		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: setRegion()::Exception", e);

		}

	}

	private void setOfficeType(HttpServletRequest request, MisrouteTO misrouteTO) {
		if (StringUtils.equalsIgnoreCase(misrouteTO.getOfficeType(),
				CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			request.setAttribute(ManifestConstants.MANIFEST_DIRECTION,
					ManifestConstants.ORIGIN_MISROUTE);

		} else if (StringUtils.equalsIgnoreCase(misrouteTO.getOfficeType(),
				CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
			request.setAttribute(ManifestConstants.MANIFEST_DIRECTION,
					ManifestConstants.BRANCH_MISROUTE);
		}
	}

	private void setMisrouteType(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		GlobalService globalService = (GlobalService) getBean("globalService");
		List<StockStandardTypeTO> stockStandardTypeTOList = globalService
				.getAllStockStandardType("MISROUTE_TYPE");
		if (!CGCollectionUtils.isEmpty(stockStandardTypeTOList)) {
			request.setAttribute("stockStandardTypeTOList",
					stockStandardTypeTOList);
		} else {
			prepareActionMessage(request, new ActionMessage(
					ManifestErrorCodesConstants.DETAILS_DOES_NOT_EXIST,
					ManifestErrorCodesConstants.MISROUTE_TYPE_DTLS_NOT_EXIST));
		}
		request.setAttribute(ManifestConstants.PROCESS_CODE,
				CommonConstants.PROCESS_MIS_ROUTE);
	}

	private void setConsgType(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentTypeTO> consgTypes = null;
		try {
			consgTypes = misrouteService.getConsignmentType();
			if (!CGCollectionUtils.isEmpty(consgTypes)) {
				request.setAttribute("consgTypes", consgTypes);
			} else {
				prepareActionMessage(request, new ActionMessage(
						ManifestErrorCodesConstants.DETAILS_DOES_NOT_EXIST,
						ManifestErrorCodesConstants.CONSG_TYPE_DTLS_NOT_EXIST));
				LOGGER.warn("MisrouteAction:: setConsgType :: consignmant Type Details Does not exist");
			}
		} catch (Exception e) {
			LOGGER.error(
					"MisrouteAction :: setConsgType()::Consg Type ::Exception",
					e);

		}

	}

	private void setRequestParam(HttpServletRequest request) {
		request.setAttribute("ERROR_FLAG", FrameworkConstants.ERROR_FLAG);
		request.setAttribute("SUCCESS_FLAG", FrameworkConstants.SUCCESS_FLAG);
		request.setAttribute("CONSIGNMENT", ManifestConstants.CONSIGNMENT);
		request.setAttribute("PACKET", ManifestConstants.PACKET);
		request.setAttribute("BAG", ManifestConstants.BAG);
		request.setAttribute("MASTER_BAG", ManifestConstants.MASTER_BAG);
		request.setAttribute("BranchMisroute", ManifestConstants.BranchMisroute);
		request.setAttribute("OriginMisroute", ManifestConstants.OriginMisroute);
		request.setAttribute("BranchCode", ManifestConstants.BranchCode);
		request.setAttribute("HubCode", ManifestConstants.HubCode);
		request.setAttribute("PARCEL",
				CommonConstants.CONSIGNMENT_TYPE_PARCEL_NAME);
		request.setAttribute("DOCUMENT",
				CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_NAME);
		request.setAttribute("SERIES_TYPE_OGM_STICKERS",
				UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
		request.setAttribute("SERIES_TYPE_BPL_NO",
				UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
		request.setAttribute("BAGLOCK_SERIES",
				UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO);

	}

	/**
	 * @Desc:gets all offices based on region selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<OfficeTO> officeTO = null;
		String OfficeSelectJSON = "";
		PrintWriter out = null;

		try {
			out = response.getWriter();
			LOGGER.trace("MisrouteAction:: getAllOfficesByCity()::START------------>:::::::");
			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			Integer cityId = Integer.parseInt(request.getParameter("cityId"));
			officeTO = misrouteService.getAllOffices(cityId);
			if (officeTO != null)
				OfficeSelectJSON = JSONSerializer.toJSON(officeTO).toString();

		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: getAllOfficesByCity() ::Exception :"
					+ e.getMessage());
		} finally {
			out.print(OfficeSelectJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction:: getAllOfficesByCity()::END------------>:::::::");
	}

	public void getAllCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String CitySelectJSON = "";
		PrintWriter out = null;

		try {
			out = response.getWriter();
			LOGGER.trace("MisrouteAction:: getAllCitiesByRegion()::START------------>:::::::");
			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			Integer regionId = Integer.parseInt(request
					.getParameter("regionId"));
			ManifestRegionTO manifestRegionTO = null;
			if (regionId != null) {
				manifestRegionTO = new ManifestRegionTO();
				RegionTO regionTO = new RegionTO();
				regionTO.setRegionId(regionId);
				// manifestRegionTO.setManifestType(manifestType);
				manifestRegionTO.setRegionTO(regionTO);
			}

			List<CityTO> cityTOs = misrouteService
					.getCitiesByRegion(manifestRegionTO);
			request.setAttribute("cityTOs", cityTOs);
			if (cityTOs != null)
				CitySelectJSON = JSONSerializer.toJSON(cityTOs).toString();

		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: getAllCitiesByRegion() ::Exception :"
					+ e.getMessage());
		} finally {
			out.print(CitySelectJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction:: getAllCitiesByRegion()::END------------>:::::::");
	}

	/**
	 * @Desc : For getting the details of consg No entered in grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("MisrouteAction::getConsignmentDtls::START");
		PrintWriter out = null;
		OfficeTO officeTO = null;
		CityTO cityTO = null;
		String jsonResult = null;
		String officeId = request.getParameter("destOfficeId");
		String consignmentNo = request
				.getParameter(OutManifestConstants.CONSIGNMENT_NO);
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
		MisrouteDetailsTO misrouteDetailsTO = null;

		try {
			out = response.getWriter();
			cnValidateTO = new OutManifestValidate();

			if (StringUtils.isNotEmpty(request
					.getParameter("manifestDirection"))) {
				cnValidateTO.setManifestDirection(request
						.getParameter("manifestDirection"));
			}

			if (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
				officeTO = new OfficeTO();
				officeTO.setOfficeId(Integer.parseInt(request
						.getParameter("loginOfficeId")));
				cnValidateTO.setOriginOffice(officeTO);
			}

			if (StringUtils.isNotEmpty(consignmentNo)) {
				cnValidateTO.setConsgNumber(consignmentNo);
			}

			if (StringUtils.isNotEmpty(officeId)) {
				officeTO = new OfficeTO();
				officeTO.setOfficeId(Integer.parseInt(officeId));
				cnValidateTO.setDestOffice(officeTO);
			}

			if (StringUtils.isNotEmpty(request.getParameter("cityId"))) {
				cityTO = new CityTO();
				cityTO.setCityId(Integer.parseInt(request
						.getParameter("cityId")));
				cnValidateTO.setDestCityTO(cityTO);
			}

			if (StringUtils.isNotEmpty(request.getParameter("manifestNo"))) {
				cnValidateTO.setManifestNumber(request
						.getParameter("manifestNo"));
			} else {
				cnValidateTO.setManifestNumber(CommonConstants.EMPTY_STRING);
			}

			String consgType = request
					.getParameter(OutManifestConstants.CONSIGNMENT_TYPE);

			if (StringUtils.isNotEmpty(consgType)) {
				if (StringUtils.equals(consgType,
						CommonConstants.CONSIGNMENT_TYPE_PARCEL_NAME)) {
					ConsignmentTypeTO consTypeTO = new ConsignmentTypeTO();
					consTypeTO
							.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
					cnValidateTO.setConsignmentTypeTO(consTypeTO);
				} else if (StringUtils.equals(consgType,
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_NAME)) {
					ConsignmentTypeTO consTypeTO = new ConsignmentTypeTO();
					consTypeTO
							.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);
					cnValidateTO.setConsignmentTypeTO(consTypeTO);
				}
			}

			String[] allowedConsgManifestedType = { CommonConstants.MANIFEST_TYPE_IN };
			cnValidateTO
					.setAllowedConsgManifestedType(allowedConsgManifestedType);

			manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());

			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			cnValidateTO = misrouteService.validateConsignment(cnValidateTO);

			if (cnValidateTO.getIsConsInManifestd().equalsIgnoreCase("Y")) {
				misrouteDetailsTO = misrouteService
						.getInManifestConsgDtls(cnValidateTO);
				if (misrouteDetailsTO != null) {
					jsonResult = serializer.toJSON(misrouteDetailsTO)
							.toString();
				} else {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST);
				}

			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.CN_NOT_IN_MANIFESTED,
								null));
			}

		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction::getConsignmentDtls::Exception :", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction::getConsignmentDtls::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction::getConsignmentDtls::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction::getConsignmentDtls::END");
	}

	/**
	 * @Desc : For getting the details of manifest No entered in grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getManifestDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("MisrouteAction::getManifestDtls::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		MisrouteDetailsTO misrouteDetailsTO = null;
		ManifestInputs manifestInputTO = new ManifestInputs();
		String loginOfficeId = null;
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.MANIFEST_NO))) {
				manifestInputTO.setManifestNumber(request
						.getParameter("manifestNo"));
			}
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) {
				loginOfficeId = request
						.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
				Integer loginOffId = Integer.parseInt(loginOfficeId.trim());
				manifestInputTO.setLoginOfficeId(loginOffId);

			}

			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.CONSIGNMENT_TYPE))) {
				String consgType = request.getParameter(
						OutManifestConstants.CONSIGNMENT_TYPE).trim();
				if (StringUtils.equals(consgType,
						CommonConstants.CONSIGNMENT_TYPE_PARCEL_NAME)) {
					manifestInputTO
							.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				} else if (StringUtils.equalsIgnoreCase(consgType,
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_NAME)) {
					manifestInputTO
							.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				}
			}
			if (StringUtils.isNotEmpty(request
					.getParameter(ManifestConstants.MISROUTE_TYPE))) {
				manifestInputTO.setMisrouteType(request
						.getParameter(ManifestConstants.MISROUTE_TYPE));
			}

			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			misrouteDetailsTO = (MisrouteDetailsTO) misrouteService
					.getMisrouteManifestDtls(manifestInputTO);

			if (!StringUtil.isNull(misrouteDetailsTO)) {
				jsonResult = JSONSerializer.toJSON(misrouteDetailsTO)
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction::getManifestDtls::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction::getManifestDtls::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction::getManifestDtls::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {

			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction:: getManifestDtls()::END------------>:::::::");
	}

	/**
	 * @desc:THis method saves the misroute manifest details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateMisroute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MisrouteForm misrouteForm = null;
		MisrouteTO misrouteTO = null;
		PrintWriter out = null;
		String jsonResult = "";
		Boolean isSaved;
		try {
			LOGGER.trace("MisrouteAction:: saveOrUpdateMisroute()::START------------>:::::::");
			out = response.getWriter();
			misrouteForm = (MisrouteForm) form;
			misrouteTO = (MisrouteTO) misrouteForm.getTo();

			if (misrouteTO != null) {

				misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);

				isSaved = misrouteService
						.saveOrUpdateOutMisrouteManifest(misrouteTO);

				twoWayWrite(misrouteTO);

				if (!isSaved) {
					jsonResult = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.MANIFEST_NOT_SAVED,
									new String[] { misrouteTO.getMisrouteNo() }));
				} else {
					jsonResult = prepareCommonException(
							FrameworkConstants.SUCCESS_FLAG,
							getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
									new String[] { misrouteTO.getMisrouteNo() }));
					// jsonResult = serializer.toJSON(jsonResult).toString();
				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction :: saveOrUpdateMisroute() ::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction :: saveOrUpdateMisroute() ::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: saveOrUpdateMisroute() ::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction:: saveOrUpdateMisroute()::END------------>:::::::");

	}

	private void twoWayWrite(MisrouteTO misrouteTO) throws CGBusinessException,
			CGSystemException {
		misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
		ArrayList<Integer> ids = new ArrayList<>(2);
		ArrayList<String> processNames = new ArrayList<>(2);
		ids.add(misrouteTO.getMisrouteId());
		processNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
		if (!StringUtil.isEmptyInteger(misrouteTO.getProcessId())) {
			ids.add(misrouteTO.getProcessId());
			processNames
					.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS);
		}
		misrouteService.twoWayWriteProcess(ids, processNames);
	}

	/**
	 * @desc:used for searching the misroute manifest record
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void searchMisrouteDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String jsonResult = "";
		try {
			LOGGER.trace("MisrouteAction:: searchMisrouteDetails()::START------------>:::::::");
			out = response.getWriter();
			String misroutNo = request
					.getParameter(OutManifestConstants.MISROUTE_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			String manifestType = request
					.getParameter(OutManifestConstants.MANIFEST_TYPE);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(misroutNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(misroutNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_MIS_ROUTE);
				manifestTO.setManifestType(manifestType);
				misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
				MisrouteTO misrouteTO = misrouteService
						.searchManifestDtls(manifestTO);

				jsonResult = serializer.toJSON(misrouteTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction :: searchMisrouteDetails() ::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction :: searchMisrouteDetails() ::Exception :"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: searchMisrouteDetails() ::Exception :"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction:: searchMisrouteDetails()::END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void validateManifestNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("MisrouteAction :: validateManifestNo() :: START------------>:::::::");
		PrintWriter out = null;
		String stockIssueJSON = CommonConstants.EMPTY_STRING;
		ManifestStockIssueInputs stockIssueInputs = null;
		String manifestScanlevel = "";
		String officeCode = "";
		try {
			out = response.getWriter();
			String stockNo = request.getParameter("stockItemNo");
			String seriesType = request.getParameter("seriesType");
			String regionCode = request.getParameter("regionCode");
			String loginCityCode = request.getParameter("loginCityCode");
			String loginOfficeId = request.getParameter("loggedinOfficeId");
			Integer officeId = Integer.parseInt(request
					.getParameter("loggedinOfficeId"));// change for stock
														// validation
			String manifestType = request.getParameter("manifestType");
			String processCode = request
					.getParameter(OutManifestConstants.PROCESS_CODE);
			if (request.getParameter("manifestScanlevel") != null)
				manifestScanlevel = request.getParameter("manifestScanlevel");

			if (request.getParameter("officeCode") != null)
				officeCode = request.getParameter("officeCode");

			stockIssueInputs = new ManifestStockIssueInputs();
			stockIssueInputs.setStockItemNumber(stockNo);
			stockIssueInputs.setSeriesType(seriesType);
			stockIssueInputs
					.setIssuedTOPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockIssueInputs.setIssuedTOPartyId(officeId);// change for stock
															// validation
			stockIssueInputs.setRegionCode(regionCode);
			stockIssueInputs.setManifestType(manifestType);
			stockIssueInputs.setManifestScanlevel(manifestScanlevel);
			stockIssueInputs.setManifestProcessCode(processCode);
			stockIssueInputs.setLoginCityCode(loginCityCode);
			stockIssueInputs.setOfficeCode(officeCode);

			misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
			stockIssueInputs = misrouteService.validateManifestNo(
					stockIssueInputs, loginOfficeId);

			if (!StringUtil.isNull(stockIssueInputs))
				stockIssueJSON = serializer.toJSON(stockIssueInputs).toString();

		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction::validateManifestNo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			stockIssueJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(stockIssueJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("MisrouteAction :: validateManifestNo() :: END------------>:::::::");
	}

	/**
	 * To validate whether manifest is exist for perticular process or not
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void isManifestExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("MisrouteAction :: isManifestExist() :: START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = null;
		try {
			out = response.getWriter();
			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			String manifestProcessCode = request
					.getParameter(OutManifestConstants.MANIFEST_PROCESS_CODE);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO.setManifestProcessCode(manifestProcessCode);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
				misrouteService.isManifestExist(manifestTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction :: isManifestExist() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction :: isManifestExist() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: isManifestExist() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MisrouteAction :: isManifestExist() :: END------------>:::::::");
	}

	/**
	 * @desc:Checks whether the misroute no is already manifested or not
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */

	public ActionForward printMisrouteDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("MisrouteAction::printMisrouteDetails::START------------>:::::::");
		try {

			String misroutNo = request
					.getParameter(OutManifestConstants.MISROUTE_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			String manifestType = request
					.getParameter(OutManifestConstants.MANIFEST_TYPE);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			MisrouteTO misrouteTO1 = null;
			if (StringUtils.isNotEmpty(misroutNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(misroutNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_MIS_ROUTE);
				manifestTO.setManifestType(manifestType);
				misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
				MisrouteTO misrouteTO = misrouteService
						.searchManifestDtls(manifestTO);

				CityTO cityTO = new CityTO();
				if (!StringUtil.isEmptyInteger(misrouteTO
						.getLoginofficeCityId())) {
					cityTO.setCityId(misrouteTO.getLoginofficeCityId());
					cityTO = misrouteService.getCityByCityId(cityTO);
				}

				if (!StringUtil.isNull(cityTO)) {
					misrouteTO.setLoginOfficeName(misrouteTO.getOfficeName());
					misrouteTO.setLoginofficeCity(cityTO.getCityName());
				}

				if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
						ManifestConstants.MASTER_BAG)) {
					if (!StringUtil.isNull(misrouteTO.getMisrouteDetailsTO())) {
						misrouteTO1 = misrouteService
								.getTotalConsignmentCountForMasterBag(misrouteTO);
					}

				}

				if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
						ManifestConstants.BAG)) {
					if (!StringUtil.isNull(misrouteTO.getMisrouteDetailsTO())) {
						misrouteTO1 = misrouteService
								.getTotalConsignmentCountForBag(misrouteTO
										.getMisrouteDetailsTO());
					}
				}
				if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
						ManifestConstants.PACKET)) {

					if (!StringUtil.isNull(misrouteTO.getMisrouteDetailsTO())) {
						misrouteTO1 = misrouteService
								.getTotalConsignmentCountForPacket(misrouteTO
										.getMisrouteDetailsTO());

					}
				}

				if (!StringUtil.isNull(misrouteTO1)) {
					if (!StringUtil.isNull(misrouteTO1.getConsigTotal())) {
						misrouteTO.setConsigTotal(misrouteTO1.getConsigTotal());
					}

					if (!StringUtil.isNull(misrouteTO1.getTotalComail())) {
						misrouteTO.setTotalComail(misrouteTO1.getTotalComail());
					}

					if (!StringUtil.isEmptyInteger(misrouteTO1.getRowcount())) {
						misrouteTO.setRowcount(misrouteTO1.getRowcount());
					}
				}
				MisrouteConverter.setMisroutType(misrouteTO);
				request.setAttribute("misrouteTO", misrouteTO);

			}

		} catch (Exception e) {
			LOGGER.error("MisrouteAction :: printMisrouteDetails() ::Exception :"
					+ e.getMessage());
		}
		LOGGER.debug("MisrouteAction::printMisrouteDetails::END------------>:::::::");
		return mapping.findForward(ManifestConstants.URL_PRINT_MISROUTE);
	}

	public void isValiedBagLockNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("MisrouteAction :: isValiedBagLockNo() :: START------------>:::::::");
		PrintWriter out = null;
		String result = null;
		try {
			out = response.getWriter();
			String bagLockNo = request
					.getParameter(ManifestUniversalConstants.BAG_LOCK_NO);

			if (!StringUtils.isEmpty(bagLockNo)) {
				misrouteService = (MisrouteService) getBean(SpringConstants.MISROUTE_SERVICE);
				Boolean isValied = misrouteService.isValiedBagLockNo(bagLockNo);

				if (isValied) {
					result = prepareCommonException(
							FrameworkConstants.ERROR_FLAG,
							getMessageFromErrorBundle(
									request,
									ManifestErrorCodesConstants.BAG_LOCK_ALREADY_USED,
									null));
				} else {
					result = prepareCommonException(
							FrameworkConstants.SUCCESS_FLAG,
							ManifestErrorCodesConstants.ROUTE_FOUND);
				}

			}

		} catch (CGBusinessException e) {
			LOGGER.error("MisrouteAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MisrouteAction::isValiedBagLockNo()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			result = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.debug("MisrouteAction :: isValiedBagLockNo() :: END------------>:::::::");
	}

}