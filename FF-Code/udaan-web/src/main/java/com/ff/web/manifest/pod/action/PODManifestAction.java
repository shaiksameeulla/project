package com.ff.web.manifest.pod.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.manifest.pod.PODPrintDtlsTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;
import com.ff.web.manifest.pod.service.PODManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author nkattung
 * 
 */

public abstract class PODManifestAction extends CGBaseAction {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PODManifestAction.class);
	/** The POD Manifest common service. */
	private PODManifestCommonService podManifestCommonService;
	/** The serializer. */
	public transient JSONSerializer serializer;

	private OutManifestCommonService outManifestCommonService;

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
	 */
	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: getCitiesByRegion() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			RegionTO regionTO = new RegionTO();
			String region = request.getParameter(InManifestConstants.REGION_ID);
			if (StringUtils.isNotEmpty(region)) {
				regionTO.setRegionId(Integer.parseInt(region));
			}
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);

			List<CityTO> cityTOs = podManifestCommonService
					.getCitiesByRegion(regionTO);
			jsonResult = serializer.toJSON(cityTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesByRegion() ::", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesByRegion() ::", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesByRegion() ::", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: getCitiesByRegion() :: END");
	}

	/**
	 * get all the offices based on city.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the all offices by city
	 */
	@SuppressWarnings("static-access")
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: getAllOfficesByCity() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if (cityId != null && cityId > 0) {
				podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
				List<OfficeTO> officeTOs = podManifestCommonService
						.getAllOfficesByCity(cityId);
				jsonResult = serializer.toJSON(officeTOs).toString();

			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getAllOfficesByCity() ::", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getAllOfficesByCity() ::", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getAllOfficesByCity() ::", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: getAllOfficesByCity() :: END");
	}

	public void getHubsByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: getHubsByCity() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");

			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}

			if (cityId != null && cityId > 0) {
				podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
				List<OfficeTO> officeTOs = podManifestCommonService
						.getAllHubsByCity(cityId,
								PODManifestConstants.HUB_OFFC_TYPE_CODE);

				jsonResult = serializer.toJSON(officeTOs).toString();

			}
			/*
			 * if (loggdOfficId != null && loggdOfficId > 0) {
			 * podManifestCommonService = (PODManifestCommonService)
			 * getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			 * List<OfficeTO> officeTOs = podManifestCommonService
			 * .getAllHubsByCity
			 * (loggdOfficId,PODManifestConstants.HUB_OFFC_TYPE_CODE); out =
			 * response.getWriter();
			 * 
			 * 
			 * jsonResult = serializer.toJSON(officeTOs).toString();
			 * 
			 * }
			 */
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: getHubsByCity() :: END");
	}

	public void getBranchesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: getHubsByCity() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");

			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}

			if (cityId != null && cityId > 0) {
				podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
				List<OfficeTO> officeTOs = podManifestCommonService
						.getAllBranchesByCity(cityId, 5);

				jsonResult = serializer.toJSON(officeTOs).toString();

			}
			/*
			 * if (loggdOfficId != null && loggdOfficId > 0) {
			 * podManifestCommonService = (PODManifestCommonService)
			 * getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			 * List<OfficeTO> officeTOs = podManifestCommonService
			 * .getAllHubsByCity
			 * (loggdOfficId,PODManifestConstants.HUB_OFFC_TYPE_CODE); out =
			 * response.getWriter();
			 * 
			 * 
			 * jsonResult = serializer.toJSON(officeTOs).toString();
			 * 
			 * }
			 */
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ERROR :: PODManifestAction :: getHubsByCity() ::", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: getHubsByCity() :: END");
	}

	@SuppressWarnings("static-access")
	public void getCitiesOfLoggdInBranchOffic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: getCitiesOfLoggdInBranchOffic() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer loggdOfficId = Integer.parseInt(request
					.getParameter("loggdOffcId"));
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			List<CityTO> cityTOs = podManifestCommonService
					.getCitiesForLoggdInBranchOffice(loggdOfficId);
			jsonResult = serializer.toJSON(cityTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesOfLoggdInBranchOffic() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesOfLoggdInBranchOffic() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: getCitiesOfLoggdInBranchOffic() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: getCitiesOfLoggdInBranchOffic() :: END");
	}

	/**
	 * Set default values
	 * 
	 * @param request
	 */
	public void setUpDefaultValues(HttpServletRequest request) {
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("originOfficeCodeName", userInfoTO
					.getOfficeTo().getOfficeCode()
					+ " - "
					+ userInfoTO.getOfficeTo().getOfficeName());
			request.setAttribute("officeName", userInfoTO.getOfficeTo()
					.getOfficeName());
			request.setAttribute("officeTypeDesc", userInfoTO.getOfficeTo()
					.getOfficeTypeTO().getOffcTypeDesc());
			request.setAttribute("loggedInofficeTypeCode", userInfoTO
					.getOfficeTo().getOfficeTypeTO().getOffcTypeCode());
			request.setAttribute("HUB_OFFICE_TYPE",
					CommonConstants.OFF_TYPE_HUB_OFFICE);
			request.setAttribute("BRANCH_OFFICE_TYPE",
					CommonConstants.OFF_TYPE_BRANCH_OFFICE);
			request.setAttribute("todaysDate",
					DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		} catch (Exception e) {
			LOGGER.error("ERROR :: PODManifestAction :: getCitiesOfLoggdInBranchOffic() ::",e);
		}
	}

	/**
	 * Gets the process.
	 * 
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProcessTO getProcess() throws CGSystemException, CGBusinessException {
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_POD);
		podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
		process = podManifestCommonService.getProcess(process);
		return process;
	}


	/**
	 * Checks if is manifest exists.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void isManifestExists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PODManifestAction :: isManifestExists() :: START");
		String result = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		boolean isManifested = Boolean.FALSE;
		try {
			out = response.getWriter();
			String manifestNo = request.getParameter("manifestNo");
			String manifestDirection = request
					.getParameter("manifestDirection");
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			isManifested = podManifestCommonService.isManifestExists(
					manifestNo, manifestDirection,
					PODManifestConstants.POD_MANIFEST,
					CommonConstants.PROCESS_POD);
			result = (isManifested) ? CommonConstants.YES : CommonConstants.NO;
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: PODManifestAction :: isManifestExists() ::",
					e);
			result = getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: PODManifestAction :: isManifestExists() ::",
					e);
			result = getSystemExceptionMessage(request, e);

		} catch (Exception e) {
			LOGGER.error("ERROR :: PODManifestAction :: isManifestExists() ::",
					e);
			result = getGenericExceptionMessage(request, e);
		} finally {
			out.print(result);
			out.flush();
			out.close();
		}
		LOGGER.trace("PODManifestAction :: isManifestExists() :: END");
	}


	/**
	 * Search pod manifest.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward searchPODManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PODManifestTO podManifestDtls = new PODManifestTO();
		String manifestNo = request.getParameter("manifestNo");
		String manifestType = request.getParameter("manifestType");
		Integer orginOfficeId = 0;
		try {
			if (StringUtils.isNotEmpty(request.getParameter("orginOfficeId")))
				orginOfficeId = Integer.parseInt(request
						.getParameter("orginOfficeId"));
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			podManifestDtls = podManifestCommonService.searchManifetsDtls(
					manifestNo, manifestType, CommonConstants.PROCESS_POD,
					orginOfficeId);
			if (!StringUtil.isNull(podManifestDtls))
				request.setAttribute("podManifestDtls", podManifestDtls);
			setUpDefaultValues(request);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: searchPODManifest() ::", e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: searchPODManifest() ::"
					+ e.getMessage());
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: searchPODManifest() ::", e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages
					.getString(UdaanCommonErrorCodes.SYS_ERROR);
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: searchPODManifest() ::"
					+ e.getMessage());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: searchPODManifest() ::", e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages
					.getString(UdaanCommonErrorCodes.SYS_ERROR);
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: searchPODManifest() ::"
					+ e.getMessage());
		}
		return mapping.findForward("viewPODManifestDetails");
	}

	/*
	 * Prints the pod manifest details
	 */
	public ActionForward printPODManifestDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PODManifestTO podManifestDtls = new PODManifestTO();

		String manifestNo = request.getParameter("manifestNo");
		String manifestType = request.getParameter("manifestType");
		Integer orginOfficeId = 0;
		try {
			if (StringUtils.isNotEmpty(request.getParameter("originOfficeId")))
				orginOfficeId = Integer.parseInt(request
						.getParameter("originOfficeId"));
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			podManifestDtls = podManifestCommonService.searchManifetsDtls(
					manifestNo, manifestType, CommonConstants.PROCESS_POD,
					orginOfficeId);
			if (!StringUtil.isNull(podManifestDtls))
				request.setAttribute("podManifestDtls", podManifestDtls);
			setUpDefaultValues(request);
//			code for Division of print page into two parts
			Set<PODManifestDtlsTO> manifestDtls = podManifestDtls
					.getManifestDtls();
			List<PODManifestDtlsTO> podList = new ArrayList<PODManifestDtlsTO>(
					manifestDtls);
			List<PODPrintDtlsTO> mainList = new ArrayList<PODPrintDtlsTO>();
			Integer totRecords = podList.size();
			int rowsPerColm = 45;

			List<List<PODManifestDtlsTO>> podlists = createLists(rowsPerColm,
					podList);
			int sz = podlists.size();
			for (int i = 0; i < sz; i = i + 2) {
				PODPrintDtlsTO dtlsTO = new PODPrintDtlsTO();
				List<PODManifestDtlsTO> firstCol = new ArrayList<PODManifestDtlsTO>();
				List<PODManifestDtlsTO> secondCol = new ArrayList<PODManifestDtlsTO>();
				firstCol.addAll(podlists.get(i));
				int j = i + 1;
				if (j < sz) {
					secondCol.addAll(podlists.get(j));
					dtlsTO.setSecondCol(secondCol);
				}
				dtlsTO.setFirstCol(firstCol);

				mainList.add(dtlsTO);
			}
			request.setAttribute("mainList", mainList);
			request.setAttribute("totSize", totRecords);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: printPODManifestDetails() ::",
					e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages.getString(e.getMessage());
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: printPODManifestDetails() ::"
					+ e.getMessage());
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: printPODManifestDetails() ::",
					e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages
					.getString(UdaanCommonErrorCodes.SYS_ERROR);
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: printPODManifestDetails() ::"
					+ e.getMessage());
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: PODManifestAction :: printPODManifestDetails() ::",
					e);
			request.setAttribute("NO_RESULT", "NO_RESULT");
			request.setAttribute("PODManifestType", manifestType);
			ResourceBundle errorMessages = null;
			errorMessages = ResourceBundle
					.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
			String errorMsg = errorMessages
					.getString(UdaanCommonErrorCodes.SYS_ERROR);
			request.setAttribute("errorMsg", errorMsg);
			LOGGER.error("PODManifestAction :: printPODManifestDetails() ::"
					+ e.getMessage());
		}

		if (manifestType.equals("O")) {
			return mapping.findForward("printOutPODManifestDetails");
		} else {
			return mapping.findForward("printInPODManifestDetails");
		}

	}

	public List<List<PODManifestDtlsTO>> createLists(int chunkSize,
			List<PODManifestDtlsTO> podList) {
		List<List<PODManifestDtlsTO>> lists = new ArrayList<List<PODManifestDtlsTO>>();
		int totCol, totsize, i, j, k, m, n;

		totsize = podList.size();
		totCol = totsize / chunkSize;

		for (i = 0; i < totCol; i++) {
			m = i * chunkSize;
			n = (i + 1) * chunkSize;
			List<PODManifestDtlsTO> chunk = new ArrayList<PODManifestDtlsTO>();
			for (j = m; j < n; j++) {
				PODManifestDtlsTO obj = podList.get(j);
				obj = convertDate(obj);
				obj.setSrNo((j + 1));
				chunk.add(obj);
			}
			lists.add(chunk);
		}
		List<PODManifestDtlsTO> chunk1 = new ArrayList<PODManifestDtlsTO>();
		for (k = (totCol * chunkSize); k < totsize; k++) {
			PODManifestDtlsTO obj = podList.get(k);
			obj = convertDate(obj);
			obj.setSrNo((k + 1));
			chunk1.add(obj);
		}
		if (!chunk1.isEmpty()) {
			lists.add(chunk1);
		}
		return lists;
	}

	public PODManifestDtlsTO convertDate(PODManifestDtlsTO dtlsTO) {
		String rdate = null;
		String ddate = null;
		if (!StringUtil.isStringEmpty(dtlsTO.getDlvDate())) {
			ddate = dtlsTO.getDlvDate().split("\\s+")[0];
		}
		if (!StringUtil.isStringEmpty(dtlsTO.getReceivedDate())) {
			rdate = dtlsTO.getReceivedDate().split("\\s+")[0];
		}
		dtlsTO.setDlvDate(ddate);
		dtlsTO.setReceivedDate(rdate);
		return dtlsTO;
	}

	public void twoWayWrite(OutManifestBaseTO outManifestBaseTO) {
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		outManifestCommonService.twoWayWrite(outManifestBaseTO);
	}

}
