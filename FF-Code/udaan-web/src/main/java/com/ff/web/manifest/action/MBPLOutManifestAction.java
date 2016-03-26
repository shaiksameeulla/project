package com.ff.web.manifest.action;

import java.io.PrintWriter;
import java.util.List;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.manifest.MBPLOutManifestTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.global.service.GlobalService;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.form.MBPLOutManifestForm;
import com.ff.web.manifest.service.MBPLOutManifestService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author preegupt
 * 
 */
public class MBPLOutManifestAction extends OutManifestAction {
	private OutManifestCommonService outManifestCommonService;
	private MBPLOutManifestService mbplOutManifestService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxAction.class);

	/**
	 * @Desc : For Preparing the page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to mbplOutManifest view
	 */
	public ActionForward viewMBPL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			LOGGER.trace("MBPLOutManifestAction::viewMBPL::START------------>:::::::");
			MBPLOutManifestTO mbplOutManifestTO = null;
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO
					.setManifestType(OutManifestConstants.MBPL_OUT_MANIFEST);
			manifestFactoryTO.setConsgType(OutManifestConstants.BPL_MANIFEST);
			mbplOutManifestTO = (MBPLOutManifestTO) getManifestBasicDtls(
					manifestFactoryTO, request);
			mbplOutManifestTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS);
			// set Regions
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			List<RegionTO> regionTOs = outManifestCommonService.getAllRegions();
			request.setAttribute("regionTOs", regionTOs);
			mbplOutManifestTO.setDestinationRegionList(regionTOs);
			mbplOutManifestTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS);
			request.setAttribute("seriesType",
					mbplOutManifestTO.getSeriesType());
			request.setAttribute("loginOfficeId",
					mbplOutManifestTO.getLoginOfficeId());
			request.setAttribute("regionId", mbplOutManifestTO.getRegionId());

			GlobalService globalService = (GlobalService) getBean("globalService");
			List<StockStandardTypeTO> stockStandardTypeTOList = globalService
					.getAllStockStandardType("MANIFEST_TYPE");

			request.setAttribute("stockStandardTypeTOList",
					stockStandardTypeTOList);

			List<OfficeTypeTO> officeTypeList = outManifestCommonService
					.getOfficeTypes();

			request.setAttribute("officeTypeList", officeTypeList);
			request.setAttribute(OutManifestConstants.PROCESS_CODE,
					OutManifestConstants.PROCESS_CODE_OMBG);
			if (!StringUtil.isEmptyMap(mbplOutManifestTO
					.getConfigurableParams())) {
				mbplOutManifestTO.setMaxManifestAllowed(mbplOutManifestTO
						.getConfigurableParams().get(
								ManifestConstants.MBPL_MAX_MANIFEST_ALLOWED));
				mbplOutManifestTO.setMaxWeightAllowed((mbplOutManifestTO
						.getConfigurableParams()
						.get(ManifestConstants.MBPL_MAX_WEIGHT_ALLOWED)));
				mbplOutManifestTO.setMaxTolerenceAllowed(mbplOutManifestTO
						.getConfigurableParams().get(
								ManifestConstants.MBPL_MAX_TOLLRENCE_ALLOWED));
			}

			setUserInfo(request);

			((MBPLOutManifestForm) form).setTo(mbplOutManifestTO);

		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestAction :: viewMBPL() ::"
					+ e.getMessage());
		}
		LOGGER.trace("MBPLOutManifestAction::viewMBPL::END------------>:::::::");
		return mapping.findForward(OutManifestConstants.SUCCESS);

	}

	private void setUserInfo(HttpServletRequest request) {
		// logged in office
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
		if (loggedInofficeTO.getOfficeTypeTO() != null) {
			request.setAttribute("originOfficeTypeCode", loggedInofficeTO
					.getOfficeTypeTO().getOffcTypeCode());
		}
		request.setAttribute("hubOfficeType",
				CommonConstants.OFF_TYPE_HUB_OFFICE);
		request.setAttribute("BranchOfficeType",
				CommonConstants.OFF_TYPE_BRANCH_OFFICE);
	}

	/**
	 * @Desc : For getting the details of BPL No entered in grid
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getManifestDtlsByProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("MBPLOutManifestAction:: getManifestDtlsByProcess()::START------------>:::::::");
		PrintWriter out = null;
		String manifestTOJSON = null;
		MBPLOutManifestTO mbplOutManifestTO = null;
		ManifestInputs manifestTOs = new ManifestInputs();
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.MANIFEST_NO))) {
				manifestTOs.setManifestNumber(request
						.getParameter(OutManifestConstants.MANIFEST_NO));
			}
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) {
				manifestTOs.setLoginOfficeId(Integer.parseInt(request
						.getParameter(OutManifestConstants.LOGIN_OFFICE_ID)));
			}

			/* The header manifest number. */
			if (StringUtils
					.isNotEmpty(request
							.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO))) {
				manifestTOs
						.setHeaderManifestNo(request
								.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO));
			}
			mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
			mbplOutManifestTO = (MBPLOutManifestTO) mbplOutManifestService
					.getManifestDtls(manifestTOs);

			if (!StringUtil.isNull(mbplOutManifestTO)) {
				manifestTOJSON = JSONSerializer.toJSON(mbplOutManifestTO)
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MBPLOutManifestAction::getManifestDtlsByProcess()::Exception::"
					+ e.getMessage());
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MBPLOutManifestAction::getManifestDtlsByProcess()::Exception::"
					+ e.getMessage());
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MBPLOutManifestAction::getManifestDtlsByProcess()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("MBPLOutManifestAction:: getManifestDtlsByProcess()::END------------>:::::::");

	}

	/**
	 * @Desc : For saving and closing the master bag
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateOutManifestMBPL(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("MBPLOutManifestAction:: saveOrUpdateOutManifestMBPL()::START------------>:::::::");
		MBPLOutManifestForm mbplOutManifestForm = null;
		MBPLOutManifestTO mbplOutManifestTO = null;
		PrintWriter out = null;
		String jsonResult = "";
		boolean isSaved = Boolean.FALSE;
		try {
			out = response.getWriter();
			mbplOutManifestForm = (MBPLOutManifestForm) form;
			mbplOutManifestTO = (MBPLOutManifestTO) mbplOutManifestForm.getTo();
			mbplOutManifestTO
					.setManifestType(OutManifestConstants.MBPL_OUT_MANIFEST);

			if (mbplOutManifestTO != null) {
				mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
				isSaved = mbplOutManifestService
						.saveOrUpdateOutManifestMBPL(mbplOutManifestTO);
				
				
				if (mbplOutManifestTO.getManifestStatus().equalsIgnoreCase(
						OutManifestConstants.CLOSE)) {
					twoWayWrite(mbplOutManifestTO);
				}

				if (!StringUtil.isNull(mbplOutManifestTO.getAction())
						&& mbplOutManifestTO.getAction().equalsIgnoreCase(
								ManifestConstants.SAVE_MANIFEST_ACTION)) {
					if (!isSaved) {
						jsonResult = getMessageFromErrorBundle(request,
								ManifestErrorCodesConstants.MANIFEST_NOT_SAVED,
								null);
					} else {
						mbplOutManifestTO
								.setSuccessMessage(getMessageFromErrorBundle(
										request,
										ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
										null));
						jsonResult = JSONSerializer.toJSON(mbplOutManifestTO)
								.toString();
					}
				} else {
					if (!isSaved) {
						jsonResult = getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.MANIFEST_NOT_CLOSED,
								null);
					} else {
						mbplOutManifestTO
								.setSuccessMessage(getMessageFromErrorBundle(
										request,
										ManifestErrorCodesConstants.MANIFEST_SAVED_CLOSED_SUCCESSFULLY,
										null));
						jsonResult = JSONSerializer.toJSON(mbplOutManifestTO)
								.toString();
					}
				}

			}
		} catch (CGBusinessException e) {
			LOGGER.error("MBPLOutManifestAction::saveOrUpdateOutManifestMBPL()::Exception::"
					+ e.getMessage());

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MBPLOutManifestAction::saveOrUpdateOutManifestMBPL()::Exception::"
					+ e.getMessage());

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MBPLOutManifestAction::saveOrUpdateOutManifestMBPL()::Exception::"
					+ e.getMessage());

			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("MBPLOutManifestAction:: saveOrUpdateOutManifestMBPL()::END------------>:::::::");

	}

	/**
	 * @Desc : For searching the master bag and its details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */

	public void searchManifestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		String manifestTOJSON = "";

		try {
			LOGGER.trace("MBPLOutManifestAction::searchManifestDetails()::START------------>:::::::");
			out = response.getWriter();
			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
				MBPLOutManifestTO mbplOutManifestTO = mbplOutManifestService
						.searchManifestDtls(manifestTO);

				if (!StringUtil.isNull(mbplOutManifestTO)) {
					manifestTOJSON = JSONSerializer.toJSON(mbplOutManifestTO)
							.toString();
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("MBPLOutManifestAction::searchManifestDetails()::Exception::"
					+ e.getMessage());
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MBPLOutManifestAction::searchManifestDetails()::Exception::"
					+ e.getMessage());
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MBPLOutManifestAction::searchManifestDetails()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.trace("MBPLOutManifestAction::searchManifestDetails()::END------------>:::::::");
	}

	public void getRouteByOriginCityAndDestCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("MBPLOutManifestAction::getRouteByOriginCityAndDestCity::START------------>:::::::");
		String routeId = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer originCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.ORIGIN_CITY_ID));
			Integer destCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.DEST_CITY_ID));
			mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
			Integer routeIdInt = mbplOutManifestService
					.getRouteIdByOriginCityIdAndDestCityId(originCityId,
							destCityId);
			if (StringUtil.isNull(routeIdInt)) {
				routeId = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.NO_ROUTE_FOR_ORIGIN_DEST,
								null));
			} else {
				routeId = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						ManifestErrorCodesConstants.ROUTE_FOUND);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("MBPLOutManifestAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("MBPLOutManifestAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("MBPLOutManifestAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(routeId);
			out.flush();
			out.close();
		}

		LOGGER.debug("MBPLOutManifestAction::getRouteByOriginCityAndDestCity::END------------>:::::::");
	}
	public void isValidTransshipmentRoute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BPLOutManifestDoxAction :: isValidTransshipmentRoute() :: Start --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		Boolean isValid = Boolean.FALSE;
		TransshipmentRouteTO transshipmentRouteTO = null;
		try {
			out = response.getWriter();
			transshipmentRouteTO = new TransshipmentRouteTO();
			Integer cityId = Integer.parseInt(request.getParameter("cityId"));
			Integer transCity = Integer.parseInt(request
					.getParameter("transCity"));
			// header destination city OGMDstCityId
			transshipmentRouteTO.setTransshipmentCityId(transCity);
			// grid destination city
			transshipmentRouteTO.setServicedCityId(cityId);

			mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
			isValid = mbplOutManifestService
					.getRouteServicibility(transshipmentRouteTO);

			if (isValid) {
				jsonResult = prepareCommonException(
						FrameworkConstants.SUCCESS_FLAG,
						ManifestErrorCodesConstants.ROUTE_FOUND);
			} else {
				jsonResult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getMessageFromErrorBundle(request,
								ManifestErrorCodesConstants.NO_ROUTE_FOUND,
								null));

			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: isValidTransshipmentRoute() ::"
					+ e);

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: isValidTransshipmentRoute() ::"
					+ e);

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: isValidTransshipmentRoute() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BPLOutManifestDoxAction :: isValidTransshipmentRoute() :: Start --------> ::::::");

	}

	public ActionForward printMBPLOutManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("MBPLOutManifestAction::printMBPLOutManifest::START------------>:::::::");
		MBPLOutManifestTO mbplOutManifestTO = null;
		MBPLOutManifestTO mbplOutManifestTO1 = null;
		try {

			String manifestNo = request
					.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				mbplOutManifestService = (MBPLOutManifestService) getBean(SpringConstants.MBPL_OUT_MANIFEST_SERVICE);
				mbplOutManifestTO = mbplOutManifestService
						.searchManifestDtls(manifestTO);

				if (!StringUtil.isNull(mbplOutManifestTO
						.getMbplOutManifestDetailsTOsList())) {
					mbplOutManifestTO1 = mbplOutManifestService
							.getTotalConsignmentCount(mbplOutManifestTO
									.getMbplOutManifestDetailsTOsList());
				}

				if (!StringUtil.isNull(mbplOutManifestTO1)) {
					if (!StringUtil.isNull(mbplOutManifestTO1.getConsigTotal())) {
						mbplOutManifestTO.setConsigTotal(mbplOutManifestTO1
								.getConsigTotal());
					}

					if (!StringUtil.isNull(mbplOutManifestTO1.getTotalComail())) {
						mbplOutManifestTO.setTotalComail(mbplOutManifestTO1
								.getTotalComail());
					}

					if (!StringUtil.isEmptyInteger(mbplOutManifestTO1
							.getRowcount())) {
						mbplOutManifestTO.setRowcount(mbplOutManifestTO1
								.getRowcount());
					}
				}
				request.setAttribute("mbplOutManifestTO", mbplOutManifestTO);

			}
		} catch (Exception e) {
			LOGGER.error("MBPLOutManifestAction::printMBPLOutManifest()::Exception::"
					+ e.getMessage());
		}
		LOGGER.debug("MBPLOutManifestAction::printMBPLOutManifest::END------------>:::::::");
		return mapping
				.findForward(ManifestConstants.URL_PRINT_MBPL_OUTMANIFEST);

	}

}
