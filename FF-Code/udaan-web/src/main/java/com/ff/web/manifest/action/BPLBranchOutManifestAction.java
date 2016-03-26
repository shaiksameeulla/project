package com.ff.web.manifest.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.geography.CityTO;
import com.ff.manifest.BplBranchOutManifestTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.form.BplBranchOutManifestForm;
import com.ff.web.manifest.service.BPLBranchOutManifestService;
import com.ff.web.manifest.service.OutManifestCommonService;


/**
 * @author nihsingh
 *
 */
public class BPLBranchOutManifestAction extends OutManifestAction{
	private OutManifestCommonService outManifestCommonService;
	private BPLBranchOutManifestService bplBranchOutManifestService;
	private GeographyCommonService geographyCommonService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxAction.class);
	
	
	/**
	 * @Desc Prepares the page on load
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return BplBranchOutManifest view
	 */
	public ActionForward viewBPLBranchOutManifest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BplBranchOutManifestAction::viewBPLBranchOutManifest::START");
		
		try {
		BplBranchOutManifestTO bplBranchOutManifestTO = null;
		ManifestFactoryTO manifestFactoryTO=new ManifestFactoryTO(); 
		manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
		manifestFactoryTO.setConsgType(OutManifestConstants.BPL_MANIFEST);
		bplBranchOutManifestTO = (BplBranchOutManifestTO) getManifestBasicDtls(
				manifestFactoryTO, request);
		
		// set Destination Office Names     
		outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		List<OfficeTO> officeTOs = outManifestCommonService.getBranchesByHub(bplBranchOutManifestTO.getLoginOfficeId());
		if(!StringUtil.isNull(officeTOs)){
		request.setAttribute("officeTOs", officeTOs);
		bplBranchOutManifestTO.setDestinationOfficeList(officeTOs);
		}else{
			prepareActionMessage(request, new ActionMessage(ManifestErrorCodesConstants.OFFICE_DTLS_NOT_EXIST));
		}
		
		//setDestinationCity in the header
		
		//set Load No DropDown
		List<LabelValueBean> loadNoList =  setLoadDropDown();
		bplBranchOutManifestTO.setLoadList(loadNoList);
		
		//set series Type in TO
		bplBranchOutManifestTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS);
		request.setAttribute("seriesType", bplBranchOutManifestTO.getSeriesType());
		request.setAttribute(OutManifestConstants.PROCESS_CODE, OutManifestConstants.PROCESS_CODE_BOUT);
		//sets the config params in TO
		if (!StringUtil.isEmptyMap(bplBranchOutManifestTO.getConfigurableParams())) {
			bplBranchOutManifestTO.setMaxCNsAllowed(bplBranchOutManifestTO.getConfigurableParams()
					.get(ManifestConstants.BPL_BROM_MAX_CNS_ALLOWED));
			bplBranchOutManifestTO.setMaxWeightAllowed((bplBranchOutManifestTO.getConfigurableParams()
					.get(ManifestConstants.BPL_BROM_MAX_WEIGHT_ALLOWED)));
			bplBranchOutManifestTO.setMaxTolerenceAllowed(bplBranchOutManifestTO.getConfigurableParams()
					.get(ManifestConstants.BPL_BROM_MAX_TOLLRENCE_ALLOWED));
		}
		
		((BplBranchOutManifestForm) form).setTo(bplBranchOutManifestTO);
	}catch (CGBusinessException e) {
		LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
		getBusinessError(request, e);
	}	
		catch (CGSystemException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
			getSystemException(request, e);
		}
		catch (Exception e) {
		LOGGER.error("Error occured in BPLBranchOutManifestAction :: ..:viewBPLBranchOutManifest()",e);
       getGenericException(request,e);
	}
		LOGGER.trace("BplBranchOutManifestAction::viewBPLBranchOutManifest::END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}
	
	
	
	/**
	 * @Desc gets the grid manifest details 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getManifestDtlsByProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BplBranchOutManifestAction::getManifestDtlsByProcess::START");
		PrintWriter out = null;
		String jsonresult = "";
		BplBranchOutManifestTO bplBranchOutManifestTO = null;
		ManifestInputs manifestTOs = new ManifestInputs();
		BplBranchOutManifestForm bplBranchOutManifestForm = null;
		try {
			out = response.getWriter();
			bplBranchOutManifestForm = (BplBranchOutManifestForm) form;
			bplBranchOutManifestTO = (BplBranchOutManifestTO) bplBranchOutManifestForm.getTo();
			
			if (StringUtils.isNotEmpty(request.getParameter(OutManifestConstants.MANIFEST_NO))) {
				manifestTOs.setManifestNumber(request
						.getParameter(OutManifestConstants.MANIFEST_NO));
			}
			if (StringUtils.isNotEmpty(OutManifestConstants.PARAM_HEADER_MANIFEST_NO)) {
				manifestTOs.setHeaderManifestNo(request
						.getParameter(OutManifestConstants.PARAM_HEADER_MANIFEST_NO));
			}
			
			if((StringUtils.isNotEmpty(request.getParameter("selectdDestOffceId")))){
				Integer destOfficId = Integer.parseInt(request.getParameter("selectdDestOffceId"));
				manifestTOs.setManifestDestOfficId(destOfficId);
			}
			manifestTOs.setLoginOfficeId(bplBranchOutManifestTO.getLoginOfficeId());
			manifestTOs.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestTOs.setGridManfstProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestTOs.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
			bplBranchOutManifestService = (BPLBranchOutManifestService) getBean(SpringConstants.BPL_BRANCH_OUT_MANIFEST_SERVICE);
			
			//TODO : Code modification
			//gets the manifest details
			bplBranchOutManifestTO = (BplBranchOutManifestTO) bplBranchOutManifestService.getManifestDtls(manifestTOs);
			
			if (!StringUtil.isNull(bplBranchOutManifestTO)) {
				jsonresult = serializer.toJSON(bplBranchOutManifestTO).toString();
				}
			
			} catch (CGBusinessException e) {
				LOGGER.error("Exception happened in getManifestDtlsByProcess of BplBranchOutManifestAction..."
						+ e.getMessage());
				jsonresult = prepareCommonException(
						FrameworkConstants.ERROR_FLAG,
						getBusinessErrorFromWrapper(request, e));
			} catch (CGSystemException e) {
				LOGGER.error("Exception happened in getManifestDtlsByProcess of BplBranchOutManifestAction..."
						+ e.getMessage());
				jsonresult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
			} catch (Exception e) {
				LOGGER.error("Exception happened in getManifestDtlsByProcess of BplBranchOutManifestAction..."
						+ e.getMessage());
				String exception = getGenericExceptionMessage(request, e);
				jsonresult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						exception);
			} finally {
			out.print(jsonresult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BplBranchOutManifestAction::getManifestDtlsByProcess::END");
	}


/**
 * @Desc saves or updates manifest
 * @param mapping
 * @param form
 * @param request
 * @param response
 */
public void saveOrUpdateBPLBranchOutManifest(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	LOGGER.trace("BplBranchOutManifestAction::saveOrUpdateBPLBranchOutManifest::START");
	BplBranchOutManifestForm bplBranchOutManifestForm = null;
	BplBranchOutManifestTO bplBranchOutManifestTO = null;
	PrintWriter out = null;
	String jsonResult = "";
	boolean isSaved = Boolean.FALSE;
	try {
		out = response.getWriter();
		bplBranchOutManifestForm = (BplBranchOutManifestForm) form;
		bplBranchOutManifestTO = (BplBranchOutManifestTO) bplBranchOutManifestForm.getTo();
		bplBranchOutManifestTO
				.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
		bplBranchOutManifestTO.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
		
		geographyCommonService =  (GeographyCommonService) getBean("geographyCommonService");
		CityTO cityTO = geographyCommonService.getCityByOfficeId(bplBranchOutManifestTO.getDestinationOfficeId());
		if(!StringUtil.isNull(cityTO)){
			bplBranchOutManifestTO.setDestinationCityId(cityTO.getCityId());
		}else{
			prepareActionMessage(request, new ActionMessage(ManifestErrorCodesConstants.CITY_DTLS_NOT_EXIST));
		}
		ConsignmentTypeTO consTypeTO = new ConsignmentTypeTO();
		consTypeTO.setConsignmentCode(OutManifestConstants.BPL_MANIFEST);
		consTypeTO.setConsignmentId(Integer.parseInt("1"));
		bplBranchOutManifestTO.setConsignmentTypeTO(consTypeTO);
		
		if (bplBranchOutManifestTO != null) {
			bplBranchOutManifestService = (BPLBranchOutManifestService) getBean(SpringConstants.BPL_BRANCH_OUT_MANIFEST_SERVICE);
			isSaved = bplBranchOutManifestService
					.saveOrUpdateBplBranchOutManifest(bplBranchOutManifestTO);
			if(bplBranchOutManifestTO.getAction().equalsIgnoreCase(ManifestConstants.SAVE_MANIFEST_ACTION)){
				if(isSaved){
					bplBranchOutManifestTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
							null));
					jsonResult = JSONSerializer.toJSON(bplBranchOutManifestTO).toString();
				}else{
					jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
				}
			}else if(bplBranchOutManifestTO.getAction().equalsIgnoreCase(ManifestConstants.CLOSE_MANIFEST_ACTION)){
				if(isSaved){
					twoWayWrite(bplBranchOutManifestTO);
					bplBranchOutManifestTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_CLOSED_SUCCESSFULLY,
							null));
					jsonResult = JSONSerializer.toJSON(bplBranchOutManifestTO).toString();
				}else{
					jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, ManifestErrorCodesConstants.MANIFEST_NOT_CLOSED);
				}
			}
			
		}
	} catch (CGBusinessException e) {
		LOGGER.error("Error occured in BPLBranchOutManifestAction :: ..:saveOrUpdateBPLBranchOutManifest()"
				+ e.getMessage());
		
		jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
	}catch (CGSystemException e) {
		LOGGER.error("Error occured in BPLBranchOutManifestAction :: ..:saveOrUpdateBPLBranchOutManifest()"
				+ e.getMessage());
		String exception=getSystemExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
	} 
	catch (Exception e) {
		LOGGER.error("Error occured in BPLBranchOutManifestAction :: ..:saveOrUpdateBPLBranchOutManifest()"
				+ e.getMessage());
		
		String exception = getGenericExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
				exception);
	}finally {
		out.print(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.trace("BplBranchOutManifestAction::saveOrUpdateBPLBranchOutManifest::END");

}


/**
 * @Desc search manifest details
 * @param mapping
 * @param form
 * @param request
 * @param response
 */
public void searchManifestDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	LOGGER.trace("BplBranchOutManifestAction::searchManifestDetails::START");
	PrintWriter out = null;
	String jsonResult = "";

	try {
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
					.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestTO
					.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
			manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
			
			bplBranchOutManifestService = (BPLBranchOutManifestService) getBean(SpringConstants.BPL_BRANCH_OUT_MANIFEST_SERVICE);
			//search teh manifest details
			BplBranchOutManifestTO bplBranchOutManifestTO = bplBranchOutManifestService.searchManifestDtls(manifestTO);
			if (!StringUtil.isNull(bplBranchOutManifestTO)) {
			jsonResult = serializer.toJSON(bplBranchOutManifestTO).toString();
			}
		}
	}catch (CGBusinessException e) {
		LOGGER.error("Exception happened in searchManifestDetails of BplBranchOutManifestAction..."
				+ e.getMessage());
		jsonResult = prepareCommonException(
				FrameworkConstants.ERROR_FLAG,
				getBusinessErrorFromWrapper(request, e));
	} catch (CGSystemException e) {
		LOGGER.error("Exception happened in searchManifestDetails of BplBranchOutManifestAction..."
				+ e.getMessage());
		String exception=getSystemExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
	} catch (Exception e) {
		LOGGER.error("Exception happened in searchManifestDetails of BplBranchOutManifestAction..."
				+ e.getMessage());
		String exception = getGenericExceptionMessage(request, e);
		jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
				exception);
	} finally {
		out.print(jsonResult);
		out.flush();
		out.close();
	}
	LOGGER.trace("BplBranchOutManifestAction::searchManifestDetails::END");
}

/**
 * @Desc print branch OutManifest parcel details from database
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @throws Exception
 */
public ActionForward printManifestDtls(ActionMapping mapping, ActionForm form,
		HttpServletRequest request,	HttpServletResponse response) {
	LOGGER.trace("BplBranchOutManifestAction::printManifestDtls::START");
	try {
		String manifestNo = request.getParameter(OutManifestConstants.MANIFEST_NO);
		String loginOfficeId = request.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
		Integer loginOffId = Integer.parseInt(loginOfficeId);
		BplBranchOutManifestTO bplBranchOutManifestTO1 = null;
		
		if (StringUtils.isNotEmpty(manifestNo)) {
			ManifestInputs manifestTO = new ManifestInputs();
			manifestTO.setLoginOfficeId(loginOffId);
			manifestTO.setManifestNumber(manifestNo);
			manifestTO.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestTO.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
			manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
			bplBranchOutManifestService = (BPLBranchOutManifestService) getBean(SpringConstants.BPL_BRANCH_OUT_MANIFEST_SERVICE);
			BplBranchOutManifestTO bplBranchOutManifestTO = bplBranchOutManifestService.searchManifestDtls(manifestTO);
			
			if (!StringUtil.isNull(bplBranchOutManifestTO
					.getBplBranchOutManifestDetailsTOsList())) {
				bplBranchOutManifestTO1 = bplBranchOutManifestService
						.getTotalConsignmentCount(bplBranchOutManifestTO
								.getBplBranchOutManifestDetailsTOsList());
			}

			if (!StringUtil.isNull(bplBranchOutManifestTO1)) {
				if (!StringUtil.isNull(bplBranchOutManifestTO1.getConsigTotal())) {
					bplBranchOutManifestTO.setConsigTotal(bplBranchOutManifestTO1
							.getConsigTotal());
				}

				if (!StringUtil.isEmptyInteger(bplBranchOutManifestTO1
						.getRowcount())) {
					bplBranchOutManifestTO.setRowcount(bplBranchOutManifestTO1
							.getRowcount());
				}
			}
			
			//set branchOutManifestDoxTO to request
			request.setAttribute("bplbranchOutManifestTO", bplBranchOutManifestTO);//FIXME change the name according to naming convention
		}
	} catch (Exception e) {
		LOGGER.error("Exception occurs in BplBranchOutManifestAction::printManifestDtls::"
				,e);
	} finally {
		//out.print(jsonResult);
		//out.flush();
		//out.close();
	}
	LOGGER.trace("BplBranchOutManifestAction::printManifestDtls::END");
	return mapping.findForward(ManifestConstants.URL_PRINT_BPL_BRANCH_OUTMANIFEST);//FIXME use "PRINT" constant
}

}
