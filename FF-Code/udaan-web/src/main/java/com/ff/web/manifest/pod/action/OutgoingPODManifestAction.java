/**
 * 
 */
package com.ff.web.manifest.pod.action;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.geography.RegionTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.pod.PODManifestDtlsTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.pod.constants.PODManifestConstants;
import com.ff.web.manifest.pod.converter.PODManifestConverter;
import com.ff.web.manifest.pod.form.OutgoingPODManifestForm;
import com.ff.web.manifest.pod.service.OutgoingPODManifestService;
import com.ff.web.manifest.pod.service.PODManifestCommonService;

/**
 * @author nkattung
 * 
 */
public class OutgoingPODManifestAction extends PODManifestAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutgoingPODManifestAction.class);

	private PODManifestCommonService podManifestCommonService;
	private OutgoingPODManifestService outgoingPODManifestService;

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
	public ActionForward viewOutgoingPODManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("OutgoingPODManifestAction :: viewOutgoingPODManifest() :: Start");
		podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
		List<RegionTO> regionTOs;
		List<OfficeTypeTO> officeTypeTOs;
		List<String> officeTypeCodes;
		String officeType =null;
		try {
			
			HttpSession session = null;
			UserInfoTO userInfoTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			if(!StringUtil.isNull(userInfoTO.getOfficeTo().getOfficeTypeTO())){
				officeType = userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeDesc();
			}
			
			if(officeType.equalsIgnoreCase(PODManifestConstants.BRANCH_OFFICE)){
				regionTOs=new ArrayList<RegionTO>();
				regionTOs.add(userInfoTO.getOfficeTo().getRegionTO());
				request.setAttribute(OutManifestConstants.REGION_TOS,regionTOs);
				request.setAttribute("officeTypeDesc", userInfoTO.getOfficeTo().getOfficeTypeTO().getOffcTypeDesc());
				setUpDefaultValues(request);
			}else if(officeType.equalsIgnoreCase(PODManifestConstants.HUB_OFFICE)){
				regionTOs = podManifestCommonService.getAllRegions();
				request.setAttribute(OutManifestConstants.REGION_TOS, regionTOs);
				officeTypeCodes = new ArrayList<String>();
				officeTypeCodes.add(PODManifestConstants.BRANCH_OFFC_TYPE_CODE);
				officeTypeCodes.add(PODManifestConstants.HUB_OFFC_TYPE_CODE);
				officeTypeTOs = podManifestCommonService.getOfficeTypeList(officeTypeCodes);
				request.setAttribute(OutManifestConstants.OFFICE_TYPE_TOS, officeTypeTOs);
				setUpDefaultValues(request);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction :: viewOutgoingPODManifest() :: ", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction :: viewOutgoingPODManifest() :: ", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction :: viewOutgoingPODManifest() :: ", e);
			getGenericException(request, e);
		}
		LOGGER.trace("OutgoingPODManifestAction :: viewOutgoingPODManifest() :: END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}

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
	 * @throws CGSystemException
	 * @throws IOException
	 */
	public void getDeliverdConsgDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGSystemException, IOException {
		LOGGER.trace("OutgoingPODManifestAction :: getDeliverdConsgDtls() :: START");
		podManifestCommonService = (PODManifestCommonService) getBean(SpringConstants.POD_MANIFEST_COMMON_SERVICE);
		String jsonResult = "";
		PrintWriter out = null;
		PODManifestDtlsTO deliveryDtls = new PODManifestDtlsTO();
		try {
			out = response.getWriter();
			String consignment = request.getParameter("consignment");
			String loggdOffType =request
					.getParameter("loggdOffcType");
			Integer loggInOffId = Integer.parseInt(request
					.getParameter("logdInOffic"));
			
			
			if(loggdOffType.equalsIgnoreCase(PODManifestConstants.BRANCH_OFFICE)){
				deliveryDtls = podManifestCommonService.getDeliverdConsgDtlsForDestBranchToDestHub(consignment, loggInOffId, PODManifestConstants.POD_MANIFEST_OUT,loggdOffType);
			}else if(loggdOffType.equalsIgnoreCase(PODManifestConstants.HUB_OFFICE)){
				/*deliveryDtls = podManifestCommonService.getDeliverdConsgDtls(
					consignment, originOffId,
					PODManifestConstants.POD_MANIFEST_OUT);*/
				deliveryDtls = podManifestCommonService.getDeliverdConsgDtlsForDestBranchToDestHub(consignment, loggInOffId, PODManifestConstants.POD_MANIFEST_OUT,loggdOffType);
				
				if(StringUtil.isNull(deliveryDtls)){
					
					//check if it has got inPODManifest then allow the consignment here while doing out
					List<ConsignmentManifestDO> consPodManifestdlist=podManifestCommonService.isConsgnNoManifestedToDestHub(consignment,
							PODManifestConstants.POD_MANIFEST, CommonConstants.PROCESS_POD,loggInOffId);
					if(!StringUtil.isEmptyList(consPodManifestdlist)){
						//Integer offiecId = consPodManifestdlist.get(0).getManifest().getOriginOffice().getOfficeId();
						//deliveryDtls =podManifestCommonService.getDeliverdConsgDtlsForDestBranchToDestHub(consignment, offiecId, PODManifestConstants.POD_MANIFEST_OUT,loggdOffType);
						
						//get the details from cons table for dat cons
						deliveryDtls = podManifestCommonService.getPODInManifstdConsingmentDtls(consignment);
					
					}else{
						//ExceptionUtil.prepareBusinessException(PODManifestConstants.INVALID_CN_AT_DESTHUB, new String[]{consignment});
						deliveryDtls = new PODManifestDtlsTO();
						throw new CGBusinessException(PODManifestConstants.INVALID_CN_AT_DESTHUB);
					}
					
				}
				
			}
			jsonResult = JSONSerializer.toJSON(deliveryDtls).toString();
		} catch (CGBusinessException e) {
			String errorMsg = getBusinessErrorFromWrapper(request, e);
			deliveryDtls.setErrorMsg(errorMsg);
			deliveryDtls.setIsValidCN(CommonConstants.NO);
			LOGGER.error(
					"ERROR :: OutgoingPODManifestAction :: getDeliverdConsgDtls() ::",
					e);
		} catch (CGSystemException e) {
			String errorMsg = getSystemExceptionMessage(request, e);
			deliveryDtls.setErrorMsg(errorMsg);
			deliveryDtls.setIsValidCN(CommonConstants.NO);
			LOGGER.error(
					"ERROR :: OutgoingPODManifestAction :: getDeliverdConsgDtls() ::",
					e);
		}  catch (Exception e) {
			String errorMsg = getGenericExceptionMessage(request, e);
			deliveryDtls.setErrorMsg(errorMsg);
			deliveryDtls.setIsValidCN(CommonConstants.NO);
			LOGGER.error(
					"ERROR :: OutgoingPODManifestAction :: getDeliverdConsgDtls() ::",
					e);
		} finally {
			jsonResult = JSONSerializer.toJSON(deliveryDtls).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("OutgoingPODManifestAction :: getDeliverdConsgDtls() :: END");
	}

	/**
	 * Save or update outgoing POD Manifest.
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
	public void saveOrUpdateOutgoingPODMnfst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.trace("OutgoingPODManifestAction :: saveOrUpdateOutgoingPODMnfst() :: START");
		PODManifestTO podManifestTO = null;
		OutgoingPODManifestForm ogPODManifestForm = null;
		String transMag = "";
		PrintWriter out = null;
		Integer loginOfficId = null;
		try {
			out = response.getWriter();
			ogPODManifestForm = (OutgoingPODManifestForm) form;
			podManifestTO = (PODManifestTO) ogPODManifestForm.getTo();
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
				outgoingPODManifestService = (OutgoingPODManifestService) getBean(SpringConstants.POD_MANIFEST_OUTGOING_SERVICE);
				transMag = outgoingPODManifestService
						.saveOrUpdatePODManifest(podManifestTO);

				//for two way write
				OutManifestBaseTO outManifestBaseTO = new OutManifestBaseTO();
				outManifestBaseTO.setTwoWayManifestId(podManifestTO.getManifestId());
				outManifestBaseTO.setManifestProcessId(podManifestTO.getManifestProcessId());
				twoWayWrite(outManifestBaseTO);
			}
		}  catch (CGBusinessException e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction ::saveOrUpdateOutgoingPODMnfst ::",e);
			transMag = getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction ::saveOrUpdateOutgoingPODMnfst ::",e);
			transMag = getSystemExceptionMessage(request, e);
		} catch (Exception e) {
			LOGGER.error("ERROR :: OutgoingPODManifestAction ::saveOrUpdateOutgoingPODMnfst ::",e);
			transMag = getGenericExceptionMessage(request, e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		LOGGER.trace("OutgoingPODManifestAction :: saveOrUpdateOutgoingPODMnfst() :: END");
	}

	/**
	 * Sets the Outgoing pod manifest details
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
		LOGGER.trace("OutgoingPODManifestAction :: setUpPODManifestDetails() :: START");
		ProcessTO process = getProcess();
		podManifestTO.setProcess(process);
		podManifestTO = PODManifestConverter
				.setUpPODMnfstDetails(podManifestTO);
		LOGGER.trace("OutgoingPODManifestAction :: setUpPODManifestDetails() :: END");
	}
}
