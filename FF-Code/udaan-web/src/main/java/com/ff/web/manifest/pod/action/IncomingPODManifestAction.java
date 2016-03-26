/**
 * 
 */
package com.ff.web.manifest.pod.action;

import java.io.IOException;
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
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.RegionTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.pod.PODConsignmentDtlsTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;
import com.ff.web.manifest.pod.converter.PODManifestConverter;
import com.ff.web.manifest.pod.form.IncomingPODManifestForm;
import com.ff.web.manifest.pod.service.IncomingPODManifestService;
import com.ff.web.manifest.pod.service.PODManifestCommonService;

/**
 * @author nkattung
 * 
 */
public class IncomingPODManifestAction extends PODManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(IncomingPODManifestAction.class);
	private PODManifestCommonService podManifestCommonService;

	private IncomingPODManifestService incomingPODManifestService;

	/**
	 * View form details.
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
	public ActionForward viewIncomingPODManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("IncomingPODManifestAction ::viewIncomingPODManifest :: START");
		podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
		List<RegionTO> regionTOs;
		try {
			LOGGER.debug("IncomingPODManifestAction ::viewIncomingPODManifest :: getAllRegions(Before)");
			regionTOs = podManifestCommonService.getAllRegions();
			LOGGER.debug("IncomingPODManifestAction ::viewIncomingPODManifest :: getAllRegions(After)");
			request.setAttribute(OutManifestConstants.REGION_TOS, regionTOs);
			setUpDefaultValues(request);
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction :: viewIncomingPODManifest() :: ", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction :: viewIncomingPODManifest() :: ", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction :: viewIncomingPODManifest() :: ", e);
			getGenericException(request, e);
		}
		LOGGER.debug("IncomingPODManifestAction ::viewIncomingPODManifest :: END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws IOException
	 * @throws CGSystemException
	 */
	public void getOutgoingPODConsgDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, IOException, CGSystemException {
		LOGGER.trace("IncomingPODManifestAction ::getOutgoingPODConsgDtls :: START");
		incomingPODManifestService = (IncomingPODManifestService) getBean(SpringConstants.INCOMING_POD_MANIFEST_SERVICE);
		String jsonResult = "";
		PrintWriter out = null;
		String errorMsg =null;
		PODManifestDtlsTO podManifestDtls = null;
		try {
			out = response.getWriter();
			String consignment = request.getParameter("consignment");
			String manifestNo = request.getParameter("manifestNo");
			
			Integer loggdOffc = Integer.parseInt(request
					.getParameter("loggdOffice"));
			
			
			podManifestDtls = incomingPODManifestService
					.getOutgoingPODConsgDtls(consignment, loggdOffc,
							PODManifestConstants.POD_MANIFEST_IN, manifestNo);
			jsonResult = JSONSerializer.toJSON(podManifestDtls).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::getOutgoingPODConsgDtls ::",e);
			errorMsg = getBusinessErrorFromWrapper(request, e);
			podManifestDtls = new PODManifestDtlsTO();
			podManifestDtls.setErrorMsg(errorMsg);
			podManifestDtls.setIsValidCN(CommonConstants.NO);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::getOutgoingPODConsgDtls ::",e);
			errorMsg = getSystemExceptionMessage(request, e);
			podManifestDtls = new PODManifestDtlsTO();
			podManifestDtls.setErrorMsg(errorMsg);
			podManifestDtls.setIsValidCN(CommonConstants.NO);
		} catch (Exception e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::getOutgoingPODConsgDtls ::",e);
			errorMsg = getGenericExceptionMessage(request, e);
			podManifestDtls = new PODManifestDtlsTO();
			podManifestDtls.setErrorMsg(errorMsg);
			podManifestDtls.setIsValidCN(CommonConstants.NO);
		} finally {
			jsonResult = JSONSerializer.toJSON(podManifestDtls).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("IncomingPODManifestAction ::getOutgoingPODConsgDtls :: END");
	}

	/**
	 * Save or update incoming POD Manifest.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public void saveOrUpdateIncomingPODMnfst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("IncomingPODManifestAction ::saveOrUpdateIncomingPODMnfst :: START");
		PODManifestTO podManifestTO = null;
		IncomingPODManifestForm incomingPODManifestForm = null;
		String transMag = "";
		PrintWriter out = null;
		Integer loginOfficId = null;
		try {
			out = response.getWriter();
			incomingPODManifestForm = (IncomingPODManifestForm) form;
			podManifestTO = (PODManifestTO) incomingPODManifestForm.getTo();
			HttpSession session = (HttpSession) request.getSession(false);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			podManifestTO.setDispachOfficeTO(userInfoTO.getOfficeTo());
			OfficeTO destinationOffice = podManifestCommonService
					.getOfficeDetailsById(podManifestTO.getDestOffId());
			podManifestTO.setDestOfficeTO(destinationOffice);
			podManifestTO.setLogingUserId(userInfoTO.getUserto().getUserId());
			 loginOfficId = Integer.parseInt(request
						.getParameter("loggdOffcId"));
				 
				 podManifestTO.setOperatingOffice(loginOfficId);
			if (podManifestTO != null) {
				// Preparing List of Details TO's from UI
				setUpPODManifestDetails(podManifestTO);
				incomingPODManifestService = (IncomingPODManifestService) getBean(SpringConstants.INCOMING_POD_MANIFEST_SERVICE);
				transMag = incomingPODManifestService
						.saveOrUpdateIncomingPODMnfst(podManifestTO);
				//for two way write
				OutManifestBaseTO outManifestBaseTO = new OutManifestBaseTO();
				outManifestBaseTO.setTwoWayManifestId(podManifestTO.getManifestId());
				outManifestBaseTO.setManifestProcessId(podManifestTO.getManifestProcessId());
				twoWayWrite(outManifestBaseTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::saveOrUpdateIncomingPODMnfst ::",e);
			transMag = getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::saveOrUpdateIncomingPODMnfst ::",e);
			transMag = getSystemExceptionMessage(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::saveOrUpdateIncomingPODMnfst ::",e);
			transMag = getGenericExceptionMessage(request, e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		LOGGER.trace("IncomingPODManifestAction ::saveOrUpdateIncomingPODMnfst :: END");
	}

	/**
	 * Sets the incoming pod manifest details
	 * 
	 * @param PODManifestTO
	 *            podManifestTO
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private void setUpPODManifestDetails(PODManifestTO podManifestTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("IncomingPODManifestAction ::setUpPODManifestDetails :: START");
		ProcessTO process = getProcess();
		podManifestTO.setProcess(process);
		podManifestTO = PODManifestConverter
				.setUpPODMnfstDetails(podManifestTO);
		LOGGER.trace("IncomingPODManifestAction ::setUpPODManifestDetails :: END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void outPODManifestDestDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("IncomingPODManifestAction ::outPODManifestDestDtls :: START");
		PODManifestTO podManifestDtls = new PODManifestTO();
		String manifestNo = request.getParameter("manifestNo");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		Integer orginOfficeId = 0;
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request.getParameter("orginOfficeId")))
				orginOfficeId = Integer.parseInt(request
						.getParameter("orginOfficeId"));
			podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
			podManifestDtls = podManifestCommonService.searchOutgoingPODManifetsDtls(
					manifestNo, PODManifestConstants.POD_MANIFEST_OUT,
					CommonConstants.PROCESS_POD, orginOfficeId);
			if (!StringUtil.isNull(podManifestDtls)) {
				jsonResult = serializer.toJSON(podManifestDtls).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::outPODManifestDestDtls ::",e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::outPODManifestDestDtls ::",e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::outPODManifestDestDtls ::",e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("IncomingPODManifestAction ::outPODManifestDestDtls :: END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void podConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("IncomingPODManifestAction ::podConsignmentDtls :: START");
		String manifestNo = request.getParameter("manifestNo");
		String consigNos = request.getParameter("consigNos");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			incomingPODManifestService = (IncomingPODManifestService) getBean(SpringConstants.INCOMING_POD_MANIFEST_SERVICE);
			PODConsignmentDtlsTO podConsigDtlsTO = new PODConsignmentDtlsTO();
			podConsigDtlsTO = (PODConsignmentDtlsTO) incomingPODManifestService
					.podConsignmentDtls(consigNos, manifestNo);
			jsonResult = serializer.toJSON(podConsigDtlsTO).toString();
			
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::podConsignmentDtls ::",e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::podConsignmentDtls ::",e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ERROR :: IncomingPODManifestAction ::podConsignmentDtls ::",e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("IncomingPODManifestAction ::podConsignmentDtls :: END");
	}

}
