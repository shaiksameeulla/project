package com.ff.web.manifest.action;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.manifest.BPLOutManifestDoxDetailsTO;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.ManifestDoxPrintTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
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
import com.ff.web.manifest.form.BPLOutManifestDoxForm;
import com.ff.web.manifest.service.BPLOutManifestDoxService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * The Class BPLOutManifestDoxAction.
 */
public class BPLOutManifestDoxAction extends OutManifestAction {

	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;

	/** The bpl for out manifest dox service. */
	private BPLOutManifestDoxService bplForOutManifestDoxService;

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxAction.class);

	/**
	 * View bpl out manifest dox.
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
	public ActionForward viewBPLOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BPLOutManifestDoxAction :: viewBPLOutManifestDox() :: Start --------> ::::::");

		try {
			BPLOutManifestDoxTO bplOutManifestDoxTO = null;
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO
					.setManifestType(OutManifestConstants.BPL_OUT_MANIFEST_TYPE_PURE);
			manifestFactoryTO
					.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			bplOutManifestDoxTO = (BPLOutManifestDoxTO) getManifestBasicDtls(
					manifestFactoryTO, request);
			// set Regions
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			List<RegionTO> regionTOs = outManifestCommonService.getAllRegions();
			request.setAttribute("regionTOs", regionTOs);
			bplOutManifestDoxTO.setDestinationRegionList(regionTOs);
			bplOutManifestDoxTO
					.setSeriesType(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);

			request.setAttribute("seriesType",
					bplOutManifestDoxTO.getSeriesType());
			request.setAttribute("loginOfficeId",
					bplOutManifestDoxTO.getLoginOfficeId());
			request.setAttribute("regionId", bplOutManifestDoxTO.getRegionId());

			GlobalService globalService = (GlobalService) getBean("globalService");
			List<StockStandardTypeTO> stockStandardTypeTOList = globalService
					.getAllStockStandardType("MANIFEST_TYPE");

			request.setAttribute("stockStandardTypeTOList",
					stockStandardTypeTOList);

			List<OfficeTypeTO> officeTypeList = outManifestCommonService
					.getOfficeTypes();

			request.setAttribute("officeTypeList", officeTypeList);
			request.setAttribute(OutManifestConstants.PROCESS_CODE,
					OutManifestConstants.PROCESS_CODE_OBDX);
			if (!StringUtil.isEmptyMap(bplOutManifestDoxTO
					.getConfigurableParams())) {
				bplOutManifestDoxTO.setMaxCNsAllowed(bplOutManifestDoxTO
						.getConfigurableParams().get(
								ManifestConstants.BPL_DOX_MAX_CNS_ALLOWED));
				bplOutManifestDoxTO.setMaxWeightAllowed((bplOutManifestDoxTO
						.getConfigurableParams()
						.get(ManifestConstants.BPL_DOX_MAX_WEIGHT_ALLOWED)));
				bplOutManifestDoxTO.setMaxTolerenceAllowed(bplOutManifestDoxTO
						.getConfigurableParams()
						.get(ManifestConstants.BPL_BROM_MAX_TOLLRENCE_ALLOWED));
			}

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

			((BPLOutManifestDoxForm) form).setTo(bplOutManifestDoxTO);

		} catch (Exception e) {
			LOGGER.error("Exception happened in BPLOutManifestDoxAction of viewBPLOutManifestDox..."
					+ e);
		}
		LOGGER.trace("BPLOutManifestDoxAction :: viewBPLOutManifestDox() :: End --------> ::::::");
		return mapping.findForward(OutManifestConstants.SUCCESS);

	}

	/**
	 * Gets the manifest dtls by process.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the manifest dtls by process
	 */
	public void getManifestDtlsByProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("BPLOutManifestDoxAction::getManifestDtlsByProcess::START------------>:::::::");
		PrintWriter out = null;
		String manifestTOJSON = null;
		BPLOutManifestDoxTO bplOutManifestDoxTOValues = null;
		ManifestInputs manifestTO = new ManifestInputs();
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.MANIFEST_NO))) {
				manifestTO.setManifestNumber(request
						.getParameter(OutManifestConstants.MANIFEST_NO));
			}
			if (StringUtils.isNotEmpty(request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID))) {
				Integer loginOfficId = Integer.parseInt(request
						.getParameter(OutManifestConstants.LOGIN_OFFICE_ID));
				manifestTO.setLoginOfficeId(loginOfficId);
			}

			if (StringUtils.isNotBlank(request
					.getParameter(OutManifestConstants.OFFICE_TYPE))) {
				String officeType = request
						.getParameter(OutManifestConstants.OFFICE_TYPE);
				manifestTO.setOriginOfficeType(officeType);
			}

			/* The header manifest number. */
			if (StringUtils
					.isNotEmpty(request
							.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO))) {
				manifestTO
						.setHeaderManifestNo(request
								.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO));
			}

			manifestTO
					.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
			manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);

			bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);

			bplOutManifestDoxTOValues = (BPLOutManifestDoxTO) bplForOutManifestDoxService
					.getManifestDtls(manifestTO);
			if (!StringUtil.isNull(bplOutManifestDoxTOValues)) {
				manifestTOJSON = JSONSerializer.toJSON(
						bplOutManifestDoxTOValues).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getManifestDtlsByProcess of BPLOutManifestDoxAction..."
					+ e);
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getManifestDtlsByProcess of BPLOutManifestDoxAction..."
					+ e);
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getManifestDtlsByProcess of BPLOutManifestDoxAction..."
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			manifestTOJSON = prepareCommonException(
					FrameworkConstants.ERROR_FLAG, exception);
		} finally {

			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		LOGGER.info("BPLOutManifestDoxAction::getManifestDtlsByProcess::END------------>:::::::");

	}

	/**
	 * Save or update bpl out manifest dox.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void saveOrUpdateBPLOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BPLOutManifestDoxAction :: saveOrUpdateBPLOutManifestDox() :: Start --------> ::::::");
		BPLOutManifestDoxForm bplForm = null;
		BPLOutManifestDoxTO bplOutmanifestDoxTO = null;
		String jsonResult = "";
		PrintWriter out = null;
		boolean isSaved = Boolean.FALSE;
		try {
			out = response.getWriter();
			bplForm = (BPLOutManifestDoxForm) form;
			bplOutmanifestDoxTO = (BPLOutManifestDoxTO) bplForm.getTo();
			bplOutmanifestDoxTO
					.setManifestType(OutManifestConstants.BPL_OUT_MANIFEST);
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			bplOutmanifestDoxTO.setConsignmentTypeTO(consgType);
			if (bplOutmanifestDoxTO != null) {
				bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);
				isSaved = bplForOutManifestDoxService
						.saveOrUpdateOutManifestBPL(bplOutmanifestDoxTO);
				
				
				if (bplOutmanifestDoxTO.getManifestStatus().equalsIgnoreCase(
						OutManifestConstants.CLOSE)) {
					twoWayWrite(bplOutmanifestDoxTO);
				}
				
				
				if (!StringUtil.isNull(bplOutmanifestDoxTO.getAction())
						&& bplOutmanifestDoxTO.getAction().equalsIgnoreCase(
								ManifestConstants.SAVE_MANIFEST_ACTION)) {
					if (!isSaved) {
						jsonResult = getMessageFromErrorBundle(request,
								ManifestErrorCodesConstants.MANIFEST_NOT_SAVED,
								null);
					} else {
						bplOutmanifestDoxTO
								.setSuccessMessage(getMessageFromErrorBundle(
										request,
										ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
										null));
						jsonResult = JSONSerializer.toJSON(bplOutmanifestDoxTO)
								.toString();
					}
				} else {
					if (!isSaved) {
						jsonResult = getMessageFromErrorBundle(
								request,
								ManifestErrorCodesConstants.MANIFEST_NOT_CLOSED,
								null);
					} else {
						bplOutmanifestDoxTO
								.setSuccessMessage(getMessageFromErrorBundle(
										request,
										ManifestErrorCodesConstants.MANIFEST_SAVED_CLOSED_SUCCESSFULLY,
										null));
						jsonResult = JSONSerializer.toJSON(bplOutmanifestDoxTO)
								.toString();
					}
				}
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: saveOrUpdateBPLOutManifestDox() ::"
					+ e);

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: saveOrUpdateBPLOutManifestDox() ::"
					+ e);

			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("Error occured in BPLOutManifestDoxAction :: saveOrUpdateBPLOutManifestDox() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BPLOutManifestDoxAction :: saveOrUpdateBPLOutManifestDox() :: End --------> ::::::");
	}

	/**
	 * Search manifest dtls for bpl.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public void searchManifestDtlsForBPL(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BPLOutManifestDoxAction :: searchManifestDtlsForBPL() :: Start --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		BPLOutManifestDoxTO bplOutManifestDoxTO = null;
		try {
			out = response.getWriter();
			String manifestNo = request.getParameter(
					OutManifestConstants.MANIFEST_NO).trim();
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
				manifestTO
						.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT); // Manifest_Type
																					// O/C
				bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);
				bplOutManifestDoxTO = bplForOutManifestDoxService
						.searchManifestDtlsForBPL(manifestTO);
				if (!StringUtil.isNull(bplOutManifestDoxTO)) {
					jsonResult = JSONSerializer.toJSON(bplOutManifestDoxTO)
							.toString();
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("BPLOutManifestDoxAction::searchManifestDtlsForBPL()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("BPLOutManifestDoxAction::searchManifestDtlsForBPL()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxAction::searchManifestDtlsForBPL()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BPLOutManifestDoxAction :: searchManifestDtlsForBPL() :: End --------> ::::::");
	}

	/**
	 * Prints the bpl out manifest dox.
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
	 * @throws Exception
	 *             the exception
	 */
	public ActionForward printBPLOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BPLOutManifestDoxAction::printBPLOutManifestDox::START------------>:::::::");
		BPLOutManifestDoxTO bplOutManifestDoxTO = null;
		try {

			String manifestNo = request.getParameter(
					OutManifestConstants.MANIFEST_NO).trim();
			String loginOfficeId = request
					.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO
						.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
				manifestTO
						.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT); // Manifest_Type
																					// O/C

				bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);
				bplOutManifestDoxTO = bplForOutManifestDoxService
						.searchManifestDtlsForBPL(manifestTO);

				List<ManifestDoxPrintTO> mainList = new ArrayList<ManifestDoxPrintTO>();
				int rowsPerColm = 45;
				List<List<BPLOutManifestDoxDetailsTO>> obdxLists = createLists(
						rowsPerColm,
						bplOutManifestDoxTO.getBplOutManifestDoxDetailsTOList());
				
				int sz = obdxLists.size();
				for (int i = 0; i < sz; i = i + 2) {
					ManifestDoxPrintTO dtlsTO = new ManifestDoxPrintTO();
					List<BPLOutManifestDoxDetailsTO> firstCol = new ArrayList<BPLOutManifestDoxDetailsTO>();
					List<BPLOutManifestDoxDetailsTO> secondCol = new ArrayList<BPLOutManifestDoxDetailsTO>();
					firstCol.addAll(obdxLists.get(i));
					int j = i + 1;
					if (j < sz) {
						secondCol.addAll(obdxLists.get(j));
						dtlsTO.setRightBPLDoxList(secondCol);
					}
					dtlsTO.setLeftBPLDoxList(firstCol);
					mainList.add(dtlsTO);
				}

				request.setAttribute("bplOutManifestDoxTO", bplOutManifestDoxTO);
				request.setAttribute("mainList", mainList);

			}
		} catch (Exception e) {
			LOGGER.error("Exception happened in printBPLOutManifestDox of BPLOutManifestDoxAction..."
					+ e);
		}
		LOGGER.debug("BPLOutManifestDoxAction::printBPLOutManifestDox::END------------>:::::::");
		return mapping
				.findForward(ManifestConstants.URL_PRINT_BPL_OUT_MANIFEST_DOX);

	}

	public List<List<BPLOutManifestDoxDetailsTO>> createLists(int chunkSize,
			List<BPLOutManifestDoxDetailsTO> obdxList) {
		List<List<BPLOutManifestDoxDetailsTO>> lists = new ArrayList<List<BPLOutManifestDoxDetailsTO>>();
		int totCol, totsize, i, j, k, m, n;

		totsize = obdxList.size();
		totCol = totsize / chunkSize;

		for (i = 0; i < totCol; i++) {
			m = i * chunkSize;
			n = (i + 1) * chunkSize;
			List<BPLOutManifestDoxDetailsTO> chunk = new ArrayList<BPLOutManifestDoxDetailsTO>();
			for (j = m; j < n; j++) {
				BPLOutManifestDoxDetailsTO obj = obdxList.get(j);
				obj.setSrNo((j + 1));
				chunk.add(obj);
			}
			lists.add(chunk);
		}
		List<BPLOutManifestDoxDetailsTO> chunk1 = new ArrayList<BPLOutManifestDoxDetailsTO>();
		for (k = (totCol * chunkSize); k < totsize; k++) {
			BPLOutManifestDoxDetailsTO obj = obdxList.get(k);
			if (StringUtils.isNotEmpty(obj.getManifestOpenType())) {
				if (StringUtils.equalsIgnoreCase(obj.getManifestOpenType(), "O")) {
					obj.setManifestOpenType("OGM");
				} else if(StringUtils.equalsIgnoreCase(obj.getManifestOpenType(), "P")) {
					obj.setManifestOpenType("Open");
				}
			} else if (StringUtils.isNotEmpty(obj.getBplManifestType())){
				if (StringUtils.equalsIgnoreCase(obj.getBplManifestType(), "T")) {
					obj.setBplManifestType("Transhipment");
				} else if (StringUtils.equalsIgnoreCase(obj.getBplManifestType(), "P")) {
					obj.setBplManifestType("Pure");
				}
			}
			obj.setSrNo((k + 1));
			chunk1.add(obj);
		}
		if (!chunk1.isEmpty()) {
			lists.add(chunk1);
		}
		return lists;
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

			bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);
			isValid = bplForOutManifestDoxService
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

	public void getRouteByOriginCityAndDestCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BPLOutManifestDoxAction::getRouteByOriginCityAndDestCity::START------------>:::::::");
		String routeId = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer originCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.ORIGIN_CITY_ID));
			Integer destCityId = Integer.valueOf(request
					.getParameter(LoadManagementConstants.DEST_CITY_ID));
			bplForOutManifestDoxService = (BPLOutManifestDoxService) getBean(SpringConstants.BPL_OUT_MANIFEST_DOX_SERVICE);
			Integer routeIdInt = bplForOutManifestDoxService
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
			LOGGER.error("BPLOutManifestDoxAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("BPLOutManifestDoxAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("BPLOutManifestDoxAction::getRouteByOriginCityAndDestCity()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			routeId = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(routeId);
			out.flush();
			out.close();
		}

		LOGGER.debug("BPLOutManifestDoxAction::getRouteByOriginCityAndDestCity::END------------>:::::::");
	}

}