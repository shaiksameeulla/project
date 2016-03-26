package com.ff.web.manifest.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.BranchOutManifestDoxDetailsTO;
import com.ff.manifest.BranchOutManifestDoxTO;
import com.ff.manifest.ManifestDoxPrintTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.BranchOutManifestDoxConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.form.BranchOutManifestDoxForm;
import com.ff.web.manifest.service.BranchOutManifestDoxService;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;
import com.ff.web.manifest.validator.ManifestValidator;


/**
 * @author nihsingh
 *
 */
public class BranchOutManifestDoxAction extends OutManifestAction {
	private OutManifestCommonService outManifestCommonService;
	private BranchOutManifestDoxService branchOutManifestDoxService;
	public transient JSONSerializer serializer;
	private GeographyCommonService geographyCommonService;
	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;
	private ManifestCommonService manifestCommonService;
	/** The manifest validator. */
	private ManifestValidator manifestValidator;
	
	
	
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BranchOutManifestParcelAction.class);
	
	/**
	 * Sets the manifest validator.
	 * 
	 * @param manifestValidator
	 *            the new manifest validator
	 */
	public void setManifestValidator(ManifestValidator manifestValidator) {
		this.manifestValidator = manifestValidator;
	}

	
	/**@desc Populates the details in the screen on load
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return return BranchOutManifestDox view
	 */
	public ActionForward viewBranchOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestDoxAction::viewBranchOutManifestDox::START");
		try {
			BranchOutManifestDoxTO branchOutManifestDoxTO = null;
			ManifestFactoryTO manifestFactoryTO=new ManifestFactoryTO(); 
			manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
			manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			branchOutManifestDoxTO = (BranchOutManifestDoxTO) getManifestBasicDtls(
					manifestFactoryTO, request);
			
			// set Destination Office DropDown   
			setDestinationOfficeDropDown(branchOutManifestDoxTO,request);
		
			
			//setDestinationCity in the header
			//branchOutManifestDoxTO.setDestinationCityId(loginOfficId);
			
			//set Load No DropDown
			List<LabelValueBean> loadNoList =  setLoadDropDown();
			branchOutManifestDoxTO.setLoadList(loadNoList);
			
			
			//set series Type in TO
			branchOutManifestDoxTO.setSeriesType(UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS);
			request.setAttribute(OutManifestConstants.SERIES_TYPE, branchOutManifestDoxTO.getSeriesType());
			request.setAttribute(OutManifestConstants.PROCESS_CODE, OutManifestConstants.PROCESS_CODE_BOUT);
			//set config params in TO
			if (!StringUtil.isEmptyMap(branchOutManifestDoxTO.getConfigurableParams())) {
				branchOutManifestDoxTO.setMaxCNsAllowed(branchOutManifestDoxTO.getConfigurableParams()
						.get(ManifestConstants.BROM_DOX_MAX_CNS_ALLOWED));
				branchOutManifestDoxTO.setMaxComailsAllowed((branchOutManifestDoxTO.getConfigurableParams()
						.get(ManifestConstants.BROM_DOX_MAX_CO_MAILS_ALLOWED)));
				
				request.setAttribute(
						OutManifestConstants.COMAIL_START_SERIES,
						branchOutManifestDoxTO.getConfigurableParams().get(
								ManifestConstants.CO_MAIL_START_WITH));
			}
			//Allowed consignment(s) for BMS, RTO, RTH, Origin Misroute manifest(s). 
			//TODO Add manifestType for RTH 
			String allowedConsgManifestedType = 
					ManifestConstants.BRANCH_MISROUTE + "," + 
							ManifestConstants.MANIFEST_TYPE_RTO + "," + 
								ManifestConstants.ORIGIN_MISROUTE;  
			branchOutManifestDoxTO.setAllowedConsgManifestedType(allowedConsgManifestedType);
			
			((BranchOutManifestDoxForm) form).setTo(branchOutManifestDoxTO);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
			getBusinessError(request, e);
		}	
		catch (CGSystemException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
			getSystemException(request, e);
		}	
		catch (Exception e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:viewBranchOutManifestDox()"
					,e);
           getGenericException(request,e);
		}
		LOGGER.trace("BranchOutManifestDoxAction::viewBranchOutManifestDox::END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
}

	
	/**@Desc Gets the consignment details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestDoxAction::getConsignmentDtls::START");
		PrintWriter out = null;
		String jsonResult = "";
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
			OfficeTO officeTO= null;
			CityTO cityTO=null;
			try {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				out = response.getWriter();
				cnValidateTO = new OutManifestValidate();
				if (StringUtils.isNotEmpty(request
						.getParameter("manifestDirection"))) {
					cnValidateTO.setManifestDirection(request
							.getParameter("manifestDirection"));
				}
				if (!StringUtil.isStringEmpty(request
						.getParameter("allowedConsgManifestedType"))) {
					cnValidateTO.setAllowedConsgManifestedType(request
							.getParameter("allowedConsgManifestedType").split(
									CommonConstants.COMMA));
				} else {
					cnValidateTO
							.setAllowedConsgManifestedType(new String[] { CommonConstants.EMPTY_STRING });
				}
				if (StringUtils.isNotEmpty(request.getParameter("loginOfficeId"))) {
					officeTO = new OfficeTO();
					officeTO.setOfficeId(Integer.parseInt(request
							.getParameter("loginOfficeId")));
					cnValidateTO.setOriginOffice(officeTO);
				}

				if (StringUtils.isNotEmpty(request.getParameter("consgNumber"))) {
					cnValidateTO
							.setConsgNumber(request.getParameter("consgNumber"));
				}
				if (StringUtils.isNotEmpty(request.getParameter("officeId"))) {
					
					officeTO = outManifestCommonService.getOfficeDetails(Integer.parseInt(request
							.getParameter("officeId")));
				
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
				}
				
				
				manifestFactoryTO=new ManifestFactoryTO();
				manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());
				
				ConsignmentTypeTO  consTypeTO = new ConsignmentTypeTO();
				consTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);
				cnValidateTO.setConsignmentTypeTO(consTypeTO);
				
				cnValidateTO.setManifestProcessCode(OutManifestConstants.PROCESS_CODE_BOUT);
				
				/*cnValidateTO = outManifestCommonService
						.validateConsignment(cnValidateTO);*/
				cnValidateTO = outManifestCommonService.validateConsignmentForBranchManifest(cnValidateTO);
				
				
				//else{
				//gets the consignment details
				branchOutManifestDoxService = (BranchOutManifestDoxService)getBean(SpringConstants.BRANCH_OUT_MANIFEST_DOX_SERVICE);
				BranchOutManifestDoxDetailsTO branchOutManifestDoxDtlTO = null;
				branchOutManifestDoxDtlTO = branchOutManifestDoxService.getConsignmentDtls(cnValidateTO);
				if(branchOutManifestDoxDtlTO!=null){
				jsonResult=serializer.toJSON(branchOutManifestDoxDtlTO).toString();
				}else{
					jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,"No Details found");
				}
				//}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:getConsignmentDtls()"
					,e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}
			catch (CGSystemException e) {
				LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:getConsignmentDtls()"
						,e);
				String exception=getSystemExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			}
			catch (Exception e) {
				LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:getConsignmentDtls()"
						,e);
				String exception = getGenericExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						exception);
				
			}finally{
				out.print(jsonResult);
				out.flush();
				out.close();
			}
		LOGGER.trace("BranchOutManifestDoxAction::getConsignmentDtls::END");
	}
	
	
	/**@Desc saves or updates the manifest
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateBranchOutManifestDox(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestDoxAction::saveOrUpdateBranchOutManifestDox::START");
		BranchOutManifestDoxForm branchOutMnfstDoxForm = null;
		BranchOutManifestDoxTO branchOutManifestDoxTO  = null;
		String transMsg = "";
		PrintWriter out = null;
		String jsonResult = "";
		List<BranchOutManifestDoxDetailsTO> branchOutmanifestDtls = null;
		try {
			
			out = response.getWriter();
			branchOutMnfstDoxForm = (BranchOutManifestDoxForm) form;
			branchOutManifestDoxTO = (BranchOutManifestDoxTO) branchOutMnfstDoxForm.getTo();

			branchOutManifestDoxTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
			branchOutManifestDoxTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
			geographyCommonService =  (GeographyCommonService) getBean("geographyCommonService");
			CityTO cityTO = geographyCommonService.getCityByOfficeId(branchOutManifestDoxTO.getDestinationOfficeId());
			if(!StringUtil.isNull(cityTO)){
				branchOutManifestDoxTO.setDestinationCityId(cityTO.getCityId());
			}else{
				prepareActionMessage(request, new ActionMessage(ManifestErrorCodesConstants.CITY_DTLS_NOT_EXIST));
			}
			
			
			
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
					
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			branchOutManifestDoxTO.setConsignmentTypeTO(consgType);
			
			setBranchOutmanifestDoxTO(branchOutManifestDoxTO);
			
			if (!StringUtil.isNull(branchOutManifestDoxTO)) {
				if (StringUtil.isEmpty(branchOutManifestDoxTO.getConsgNos())
						&& StringUtil.isEmpty(branchOutManifestDoxTO.getComailNos())) {
					//throw new CGBusinessException("ERROROMD001");
					prepareActionMessage(request, new ActionMessage("ERROROMD001"));
				}
				branchOutmanifestDtls =BranchOutManifestDoxConverter
						.prepareBranchOutManifestDtlDoxList(branchOutManifestDoxTO);
				branchOutManifestDoxTO.setBranchOutManifestDoxDetailsTOList(branchOutmanifestDtls);
				ManifestDO manifestDO = null;
				/*ManifestProcessDO manifestProcessDO = new ManifestProcessDO();*/
				List<BookingDO> allBooking = new ArrayList<BookingDO>();
				List<ConsignmentDO> bookingConsignment = new ArrayList<ConsignmentDO>();
				Set<ConsignmentDO> allConsignments = new LinkedHashSet<ConsignmentDO>();
				manifestDO = prepareManifestForBranchOutDox(manifestDO, 
						branchOutManifestDoxTO, allBooking, bookingConsignment, allConsignments);
				manifestDO.setConsignments(allConsignments);

				
				branchOutManifestDoxService = (BranchOutManifestDoxService) getBean(SpringConstants.BRANCH_OUT_MANIFEST_DOX_SERVICE);
				
				transMsg = branchOutManifestDoxService.saveOrUpdateBranchOutManifestDox(
						manifestDO,branchOutManifestDoxTO, allBooking,
						bookingConsignment, allConsignments);
				
				
				
				if(transMsg.equalsIgnoreCase("O")){
					branchOutManifestDoxTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
							null));
					
					
					jsonResult = JSONSerializer.toJSON(branchOutManifestDoxTO).toString();
					
				}else if(transMsg.equalsIgnoreCase("C")){
					twoWayWrite(branchOutManifestDoxTO);
					branchOutManifestDoxTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_CLOSED_SUCCESSFULLY,
							null));
					
					
					jsonResult = JSONSerializer.toJSON(branchOutManifestDoxTO).toString();
				}
		
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:saveOrUpdateBranchOutManifestDox()"
					,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} 
		catch (CGSystemException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:saveOrUpdateBranchOutManifestDox()"
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			
		}catch (Exception e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: ..:saveOrUpdateBranchOutManifestDox()"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}
		finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BranchOutManifestDoxAction::saveOrUpdateBranchOutManifestDox::END");
	}
	
	
	/**@Desc Search the manifest
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void searchManifestDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestDoxAction::searchManifestDetails::START");
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
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				branchOutManifestDoxService = (BranchOutManifestDoxService) getBean(SpringConstants.BRANCH_OUT_MANIFEST_DOX_SERVICE);
				//search the manifest details
				BranchOutManifestDoxTO branchOutManifestDoxTO = branchOutManifestDoxService
						.searchManifestDtls(manifestTO);
				//jsonResult = serializer.toJSON(branchOutManifestDoxTO).toString();
				if (!StringUtil.isNull(branchOutManifestDoxTO)) {
					jsonResult = serializer.toJSON(branchOutManifestDoxTO).toString();
					}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestDoxAction..."
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestDoxAction..."
					+ e.getMessage());
			String exception=getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch (Exception e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestDoxAction..."
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally { 
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BranchOutManifestDoxAction::searchManifestDetails::END");
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
		ActionMessage actionMessage = null;
		try{
		String manifestNo = request.getParameter(OutManifestConstants.MANIFEST_NO);
		String loginOfficeId = request.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
		Integer loginOffId = Integer.parseInt(loginOfficeId);
		
		if (StringUtils.isNotEmpty(manifestNo)) {
			ManifestInputs manifestTO = new ManifestInputs();
			manifestTO.setLoginOfficeId(loginOffId);
			manifestTO.setManifestNumber(manifestNo);
			manifestTO.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			manifestTO.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
			manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
			branchOutManifestDoxService = (BranchOutManifestDoxService) getBean(SpringConstants.BRANCH_OUT_MANIFEST_DOX_SERVICE);
			BranchOutManifestDoxTO branchOutManifestDoxTO = branchOutManifestDoxService
						.searchManifestDtls(manifestTO);
			
			//List<BranchManifestPage> page =branchOutManifestDoxService.preparePrint(branchOutManifestDoxTO);
			
			//set branchOutManifestDoxTO to request
			request.setAttribute("branchOutManifestDoxTO", branchOutManifestDoxTO);//FIXME change the name according to naming convention
			
			
			List<BranchOutManifestDoxDetailsTO> manifestDtls = branchOutManifestDoxTO.getBranchOutManifestDoxDetailsTOList();
			
			List<ManifestDoxPrintTO> mainList = new ArrayList<ManifestDoxPrintTO>();
			Integer totRecords = manifestDtls.size();
			int rowsPerColm = 45;
			List<List<BranchOutManifestDoxDetailsTO>> branchlists = createLists(rowsPerColm,
					manifestDtls);
			int sz = branchlists.size();
			for (int i = 0; i < sz; i = i + 2) {
				ManifestDoxPrintTO dtlsTO = new ManifestDoxPrintTO();
				List<BranchOutManifestDoxDetailsTO> firstCol = new ArrayList<BranchOutManifestDoxDetailsTO>();
				List<BranchOutManifestDoxDetailsTO> secondCol = new ArrayList<BranchOutManifestDoxDetailsTO>();
				firstCol.addAll(branchlists.get(i));
				int j = i + 1;
				if (j < sz) {
					secondCol.addAll(branchlists.get(j));
					dtlsTO.setRightBranchDoxList(secondCol);
				}
				dtlsTO.setLeftBranchDoxList(firstCol);

				mainList.add(dtlsTO);
			}
			request.setAttribute("mainList", mainList);
			request.setAttribute("totSize", totRecords);
			
			//request.setAttribute("page", page);
		}
		}catch (CGBusinessException e) {
			LOGGER.error("BranchOutManifestDoxAction::printManifestDtls ..CGBusinessException :"+e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("BranchOutManifestDoxAction::printManifestDtls ..CGSystemException :"+e);
			getSystemException(request,e);
		}catch (Exception e) {
			LOGGER.error("BranchOutManifestDoxAction::printManifestDtls ..Exception :"+e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
	
	LOGGER.debug("BranchOutManifestDoxAction::printManifestDtls::END");
	return mapping.findForward(ManifestConstants.URL_PRINT_BRANCH_OUTMANIFEST_DOX);//FIXME use "PRINT" constant
}


public List<List<BranchOutManifestDoxDetailsTO>> createLists(int chunkSize,
		List<BranchOutManifestDoxDetailsTO> podList) {
	List<List<BranchOutManifestDoxDetailsTO>> lists = new ArrayList<List<BranchOutManifestDoxDetailsTO>>();
	int totCol, totsize, i, j, k, m, n;

	totsize = podList.size();
	totCol = totsize / chunkSize;

	for (i = 0; i < totCol; i++) {
		m = i * chunkSize;
		n = (i + 1) * chunkSize;
		List<BranchOutManifestDoxDetailsTO> chunk = new ArrayList<BranchOutManifestDoxDetailsTO>();
		for (j = m; j < n; j++) {
			BranchOutManifestDoxDetailsTO obj = podList.get(j);
			//obj = convertDate(obj);
			obj.setSrNo((j + 1));
			chunk.add(obj);
		}
		lists.add(chunk);
	}
	List<BranchOutManifestDoxDetailsTO> chunk1 = new ArrayList<BranchOutManifestDoxDetailsTO>();
	for (k = (totCol * chunkSize); k < totsize; k++) {
		BranchOutManifestDoxDetailsTO obj = podList.get(k);
		//obj = convertDate(obj);
		obj.setSrNo((k + 1));
		chunk1.add(obj);
	}
	if (!chunk1.isEmpty()) {
		lists.add(chunk1);
	}
	return lists;
}


private ManifestDO prepareManifestForBranchOutDox(ManifestDO manifestDO,
		BranchOutManifestDoxTO branchOutmanifestDoxTO, List<BookingDO> allBooking,
		List<ConsignmentDO> bookingConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
		CGSystemException {
	Set<ComailDO> comailSet = new LinkedHashSet<>();
	/* prepare Manifest for search */
	manifestCommonService = (ManifestCommonService) getBean(SpringConstants.MANIFEST_COMMON_SERVICE);
	manifestDO = OutManifestBaseConverter
			.prepateManifestDO(branchOutmanifestDoxTO);
	//specific header data
	manifestDO.setLoadLotId(branchOutmanifestDoxTO.getLoadNo());
	

	//set grid position
	GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
	if (!StringUtil.isEmpty(branchOutmanifestDoxTO.getConsgNos())) {
		gridItemOrderDO.setConsignments(branchOutmanifestDoxTO.getConsgNos());
	}
	if (!StringUtil.isEmpty(branchOutmanifestDoxTO.getComailNos())) {
		gridItemOrderDO.setComails(branchOutmanifestDoxTO.getComailNos());
	}
	gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
			ManifestConstants.ACTION_SAVE);
	manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition()
			.toString());
	
	
	

	for (int i = 0; i < branchOutmanifestDoxTO.getConsgNos().length; i++) {
		
				if(!StringUtil.isStringEmpty(branchOutmanifestDoxTO.getConsgNos()[i])){
				/** prepare consignment */
		
				//ConsignmentDO consignmentDO = new ConsignmentDO();
				
				/* Setting consignment number */
				//consignmentDO.setConsgNo(branchOutmanifestDoxTO.getConsgNos()[i]);
					String consgNo = branchOutmanifestDoxTO.getConsgNos()[i];
				ConsignmentDO consignmentDO = manifestCommonService
						.getConsignment(consgNo);
				/* Setting Pincode */
				/*PincodeDO pincodeDO = new PincodeDO();
				pincodeDO.setPincodeId(outmanifestDoxTO.getPincodeIds()[i]);
				consignmentDO.setDestPincodeId(pincodeDO);*/

				/* Setting Final and Actual Weight */
				/*consignmentDO.setActualWeight(branchOutmanifestDoxTO
						.getBookingWeights()[i]);
				consignmentDO.setFinalWeight(branchOutmanifestDoxTO
						.getBookingWeights()[i]);*/

				/* Setting Process */
				if (!StringUtil.isEmptyInteger(branchOutmanifestDoxTO
						.getProcessId())) {
					ProcessDO updatedProcessDO = new ProcessDO();
					updatedProcessDO.setProcessId(branchOutmanifestDoxTO
							.getProcessId());
					consignmentDO.setUpdatedProcess(updatedProcessDO);
				}

			

				/* Setting status */
				/*consignmentDO.setConsgStatus("T");*/

				/* Origin office */
				/*consignmentDO.setOrgOffId(branchOutmanifestDoxTO
						.getLoginOfficeId());*/

				/* Operation Level */
				ConsignmentTO consigmentTO = new ConsignmentTO();
				consigmentTO.setOrgOffId(branchOutmanifestDoxTO
						.getLoginOfficeId());
				consigmentTO.setDestOffice(null);
				if (branchOutmanifestDoxTO.getDestinationOfficeId() != null) {
					OfficeTO destoffTO = new OfficeTO();
					destoffTO.setOfficeId(branchOutmanifestDoxTO
							.getDestinationOfficeId());
					consigmentTO.setDestOffice(destoffTO);
				}
				/*CityTO destCityTO = new CityTO();
				if(!StringUtil.isNull(branchOutmanifestDoxTO.getDestCityIds()[i])){
				destCityTO.setCityId(branchOutmanifestDoxTO.getDestCityIds()[i]);
				consigmentTO.setDestCity(destCityTO);
				}*/
				OfficeTO offTO = new OfficeTO();
				offTO.setOfficeId(branchOutmanifestDoxTO.getLoginOfficeId());
				/*Integer operatingLevel = outManifestCommonService
						.getConsgOperatingLevel(consigmentTO, offTO);
				consignmentDO.setOperatingLevel(operatingLevel);*/

				/*
				 * operating level needs to be set in case of
				 * weight/destination changes
				 */
				consignmentDO.setOperatingOffice(branchOutmanifestDoxTO
						.getLoginOfficeId());

				
				allConsignments.add(consignmentDO);
				}//end of if loop

			} /* END of FOR Loop - Outer */
	

	manifestDO.setConsignments(allConsignments);

	
	/** Set ComailDO */
	if (!StringUtil.isEmpty(branchOutmanifestDoxTO.getComailNos())) {
		for (int i = 0; i < branchOutmanifestDoxTO.getComailNos().length; i++) {
			String comail = branchOutmanifestDoxTO.getComailNos()[i];
			if (!StringUtils.isEmpty(comail)) {
				ComailDO comailDO = prepairComailDO(comail,
						branchOutmanifestDoxTO);
				comailSet.add(comailDO);
			}
		}
	}
	manifestDO.setComails(comailSet);
	
	
	
	
	/* prepare Manifest */
	manifestDO = BranchOutManifestDoxConverter.prepareManifestDO(
			branchOutmanifestDoxTO, manifestDO);

	return manifestDO;
}
private void setBranchOutmanifestDoxTO(BranchOutManifestDoxTO branchOutmanifestDoxTO)
		throws CGSystemException, CGBusinessException {
	LOGGER.trace("BranchOutManifestDoxAction :: setBranchOutmanifestDoxTO() :: START ::------------>:::::::");
	OfficeTO loginOfficeTO = null;
	Integer operatingLevel = branchOutmanifestDoxTO.getOperatingLevel();
	// If manifest is creating first time, set the below values
	if (StringUtil.isEmptyInteger(branchOutmanifestDoxTO.getManifestId())) {
		outManifestUniversalService = (OutManifestUniversalService) getBean(SpringConstants.OUT_MANIFEST_UNIVERSAL_SERVICE);
		// Setting Login Office details
		loginOfficeTO = new OfficeTO();
		loginOfficeTO.setOfficeId(branchOutmanifestDoxTO.getLoginOfficeId());
		if (StringUtils.isNotEmpty(branchOutmanifestDoxTO.getOfficeCode())) {
			loginOfficeTO.setOfficeCode(branchOutmanifestDoxTO.getOfficeCode());
		}
		
		
		// Setting manifested product series
		/*if (StringUtils.isNotEmpty(branchOutmanifestDoxTO
				.getManifestedProductSeries())) {
			ProductTO productTO = outManifestCommonService
					.getProductByConsgSeries(branchOutmanifestDoxTO
							.getManifestedProductSeries());
			outmanifestDoxTO.setProduct(productTO);
		}*/
		// calculating operating level
		operatingLevel = outManifestUniversalService.calcOperatingLevel(
				branchOutmanifestDoxTO, loginOfficeTO);
		branchOutmanifestDoxTO.setOperatingLevel(operatingLevel);

		// Getting Consignment Type details based on
		List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
				.getConsignmentTypes(branchOutmanifestDoxTO
						.getConsignmentTypeTO());
		// Setting Consignment type id
		if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
			ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs
					.get(0);
			branchOutmanifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
		}
		// Setting process id
		ProcessTO processTO = new ProcessTO();
		processTO
				.setProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
		processTO = outManifestUniversalService.getProcess(processTO);
		branchOutmanifestDoxTO.setProcessId(processTO.getProcessId());
		branchOutmanifestDoxTO.setProcessCode(processTO.getProcessCode());

		// Setting process number
		String processNumber = outManifestCommonService
				.createProcessNumber(processTO, loginOfficeTO);
		branchOutmanifestDoxTO.setProcessNo(processNumber);
	}

	// The below attributes will always get updated
	if(StringUtil.isNull(branchOutmanifestDoxTO.getDestinationOfficeId())){
	if (branchOutmanifestDoxTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
		branchOutmanifestDoxTO.setIsMulDestination(CommonConstants.YES);
	}
	}
	
	LOGGER.trace("BranchOutManifestDoxAction :: setBranchOutmanifestDoxTO() :: END------------>:::::::");
}

/**
 * Prepair Comail DO
 * 
 * @param comail
 * @param outmanifestDoxTO
 * @return ComailDO
 * @throws CGBusinessException
 * @throws CGSystemException
 */
private ComailDO prepairComailDO(String comail,
		BranchOutManifestDoxTO branchOutmanifestDoxTO) throws CGBusinessException,
		CGSystemException {
	Integer comailId = outManifestCommonService
			.getComailIdByComailNo(comail);
	ComailDO comailDO = new ComailDO();
	if (!StringUtil.isEmptyInteger(comailId)) {
		comailDO.setCoMailId(comailId);
	} else {
		comailDO.setCoMailId(null);
	}
	comailDO.setCoMailNo(comail);
	comailDO.setOriginOffice(branchOutmanifestDoxTO.getLoginOfficeId());
	if (!StringUtil.isEmptyInteger(branchOutmanifestDoxTO
			.getDestinationOfficeId())) {
		comailDO.setDestinationOffice(branchOutmanifestDoxTO
				.getDestinationOfficeId());
	}
	return comailDO;
}

private void setDestinationOfficeDropDown(BranchOutManifestDoxTO branchOutmanifestDoxTO, HttpServletRequest request) throws CGBusinessException, CGSystemException{
	outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
	List<OfficeTO> officeTOs = outManifestCommonService.getBranchesByHub(branchOutmanifestDoxTO.getLoginOfficeId());
	if(!StringUtil.isNull(officeTOs)){
		request.setAttribute("officeTOs", officeTOs);
		branchOutmanifestDoxTO.setDestinationOfficeList(officeTOs);
		
	}else{
		prepareActionMessage(request, new ActionMessage(ManifestErrorCodesConstants.OFFICE_DTLS_NOT_EXIST));
	}
}



}
