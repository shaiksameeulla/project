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
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.BranchOutManifestParcelDetailsTO;
import com.ff.manifest.BranchOutManifestParcelTO;
import com.ff.manifest.LoadLotTO;
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
import com.ff.web.manifest.converter.BranchOutManifestParcelConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.form.BranchOutManifestParcelForm;
import com.ff.web.manifest.service.BranchOutManifestParcelService;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * 
 * @author hkansagr
 *
 */

public class BranchOutManifestParcelAction extends OutManifestAction {
	
	/** The LOGGER */
	private final static Logger LOGGER = LoggerFactory
				.getLogger(BranchOutManifestParcelAction.class);
	
	/** The outManifestCommonService. */
	private OutManifestCommonService outManifestCommonService = null;
	
	/** The branchOutManifestParcelService. */
	private BranchOutManifestParcelService branchOutManifestParcelService = null;
	
	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;
	
	private ManifestCommonService manifestCommonService;
	
	private GeographyCommonService geographyCommonService;
	/**
	 * @Desc To show branchOutManifestParcel form 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to branchOutManifestParcel view
	 * @throws Exception
	 */
	public ActionForward viewBranchOutManifestParcel(ActionMapping mapping,	ActionForm form,
			HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.debug("BranchOutManifestParcelAction::viewBranchOutManifestParcel::START");
		BranchOutManifestParcelTO to = null;
		try {
			ManifestFactoryTO manifestFactoryTO = new ManifestFactoryTO();
			manifestFactoryTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
			manifestFactoryTO.setConsgType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			manifestFactoryTO.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
			to = (BranchOutManifestParcelTO)getManifestBasicDtls(manifestFactoryTO,request);
			
			to.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
			
			/* Allowed consignment(s) for BMS, RTO, RTH manifest(s) */
			String allowedConsgManifestedType = 
				ManifestConstants.BRANCH_MISROUTE + CommonConstants.COMMA + 
					CommonConstants.MANIFEST_TYPE_RTO + CommonConstants.COMMA +
						CommonConstants.MANIFEST_TYPE_RTH;  
			to.setAllowedConsgManifestedType(allowedConsgManifestedType);
			
			outManifestCommonService = getOutManifestCommonServiceForParcel();
			
			/* populate destination offices by hub */ 
			List<OfficeTO> officeTOs = outManifestCommonService.getBranchesByHub(to.getLoginOfficeId());
			request.setAttribute("officeTOs", officeTOs);
			to.setDestinationOfficeList(officeTOs);
			
			/* populate load Number */
			List<LoadLotTO> loadLotList = outManifestCommonService.getLoadNo();
			request.setAttribute("loadLotList",loadLotList);
			to.setLoadList(loadLotList);
			
			to.setSeriesType(UdaanCommonConstants
					.SERIES_TYPE_BPL_STICKERS);
			
			if (!StringUtil.isEmptyMap(to.getConfigurableParams())) {
				to.setMaxCNsAllowed(to.getConfigurableParams()
						.get(ManifestConstants.BROM_PAX_MAX_CNS_ALLOWED));
				to.setMaxWeightAllowed((to.getConfigurableParams()
						.get(ManifestConstants.BROM_PAX_MAX_WEIGHT_ALLOWED)));
				to.setMaxTolerenceAllowed(to.getConfigurableParams()
						.get(ManifestConstants.BROM_PAX_MAX_TOLLRENCE_ALLOWED));
			}
			
			request.setAttribute(OutManifestConstants.PROCESS_CODE, 
					OutManifestConstants.PROCESS_CODE_BOUT);
			request.setAttribute(OutManifestConstants.REGION_ID, 
					to.getRegionId());
			((BranchOutManifestParcelForm) form).setTo(to);
		}catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
			getBusinessError(request, e);
		}	
		catch (CGSystemException e) {
			LOGGER.error("Error occured in BranchOutManifestDoxAction :: viewBranchOutManifestDox() :"	+ e.getMessage());
			getSystemException(request, e);
		}	 
		catch (Exception e) {
			LOGGER.error("Exception occurs in BranchOutManifestParcelAction::viewBranchOutManifestParcel::"
					+ e.getMessage());
		}
		LOGGER.debug("BranchOutManifestParcelAction::viewBranchOutManifestParcel::END");
		return mapping.findForward(OutManifestConstants.SUCCESS);
	}
	
	/**
	 * @Desc find branch OutManifest parcel details from database
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void searchManifestDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestParcelAction::searchManifestDetails::START");
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
				branchOutManifestParcelService = (BranchOutManifestParcelService) getBean(SpringConstants.BRANCH_OUT_MANIFEST_PARCEL_SERVICE);
				//search the manifest details
				BranchOutManifestParcelTO branchOutManifestParcelTO = branchOutManifestParcelService
						.searchManifestDtls(manifestTO);
				//jsonResult = serializer.toJSON(branchOutManifestDoxTO).toString();
				if (!StringUtil.isNull(branchOutManifestParcelTO)) {
					jsonResult = serializer.toJSON(branchOutManifestParcelTO).toString();
					}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestParcelAction..."
					+ e.getMessage());
			jsonResult = prepareCommonException(
					FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestParcelAction..."
					+ e.getMessage());
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("Exception happened in searchManifestDetails of BranchOutManifestParcelAction..."
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally { 
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BranchOutManifestParcelAction::searchManifestDetails::END");
	}
	
	/**
	 * @Desc To populate consignment details by consignment No. on view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("BranchOutManifestParcelAction::getConsignmentDtls::START");
		PrintWriter out = null;
		String jsonResult = "";
		OutManifestValidate cnValidateTO = null;
		ManifestFactoryTO manifestFactoryTO = null;
			
			try {
				outManifestCommonService = (OutManifestCommonService) getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
				out = response.getWriter();
				cnValidateTO = new OutManifestValidate();
				
				setRequestParameters(request,cnValidateTO);
				
				manifestFactoryTO=new ManifestFactoryTO();
				manifestFactoryTO.setConsgNumber(cnValidateTO.getConsgNumber());
				
				ConsignmentTypeTO  consTypeTO = new ConsignmentTypeTO();
				consTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE);
				cnValidateTO.setConsignmentTypeTO(consTypeTO);
				
				cnValidateTO.setManifestProcessCode(OutManifestConstants.PROCESS_CODE_BOUT);
				
				/*cnValidateTO = outManifestCommonService
						.validateConsignment(cnValidateTO);*/
				cnValidateTO = outManifestCommonService.validateConsignmentForBranchManifest(cnValidateTO);
			
			/*	if(cnValidateTO.getIsConsInManifestd().equalsIgnoreCase("Y")){
					branchOutManifestParcelService = (BranchOutManifestParcelService)getBean(SpringConstants.BRANCH_OUT_MANIFEST_PARCEL_SERVICE);
					//gets the inmanifstdconsignment details
					BranchOutManifestParcelDetailsTO branchOutManifestParcelDtlTO = null;
					branchOutManifestParcelDtlTO = branchOutManifestParcelService.getInManifestdConsignmentDtls(manifestFactoryTO);
					if(branchOutManifestParcelDtlTO!=null){
					jsonResult = serializer.toJSON(branchOutManifestParcelDtlTO).toString();
					}else{
						jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,"No Details found");
					}
					
				}*/
				//else{
				//gets the consignment details
					branchOutManifestParcelService = (BranchOutManifestParcelService)getBean(SpringConstants.BRANCH_OUT_MANIFEST_PARCEL_SERVICE);
					BranchOutManifestParcelDetailsTO branchOutManifestParcelDtlTO = null;
					branchOutManifestParcelDtlTO = branchOutManifestParcelService.getConsignmentDtls(cnValidateTO);
				if(branchOutManifestParcelDtlTO!=null){
				jsonResult=serializer.toJSON(branchOutManifestParcelDtlTO).toString();
				}else{
					jsonResult= prepareCommonException(FrameworkConstants.ERROR_FLAG,"No Details found");
				}
				//}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:getConsignmentDtls()"
					,e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}
			catch (CGSystemException e) {
				LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:getConsignmentDtls()"
						,e);
				String exception = getSystemExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			}
			catch (Exception e) {
				LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:getConsignmentDtls()"
						,e);
				String exception = getGenericExceptionMessage(request, e);
				jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
						exception);
				
			} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("BranchOutManifestParcelAction::getConsignmentDtls::END");
	}
	
	/**
	 * @Desc To save or update BRANCH OUT MANIFEST PARCEL details to database 
	 * if manifestStatus is close(or "C") then it will closed automatically
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void saveOrUpdateBranchOutManifestParcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
				throws CGBusinessException, CGSystemException {
		LOGGER.trace("BranchOutManifestParcelAction::saveOrUpdateBranchOutManifestParcel::START");
		BranchOutManifestParcelForm branchOutMnfstParcelForm = null;
		BranchOutManifestParcelTO branchOutManifestParcelTO  = null;
		String transMsg = "";
		PrintWriter out = null;
		String jsonResult = "";
		List<BranchOutManifestParcelDetailsTO> branchOutmanifestDtls = null;
		try {
			
			out = response.getWriter();
			branchOutMnfstParcelForm = (BranchOutManifestParcelForm) form;
			branchOutManifestParcelTO = (BranchOutManifestParcelTO) branchOutMnfstParcelForm.getTo();
			
			if((StringUtils.isNotEmpty(request.getParameter("destOfficeId")))){
				Integer destOfficId = Integer.parseInt(request.getParameter("destOfficeId"));
				branchOutManifestParcelTO.setDestinationOfficeId(destOfficId);
			}
			geographyCommonService =  (GeographyCommonService) getBean("geographyCommonService");
			CityTO cityTO = geographyCommonService.getCityByOfficeId(branchOutManifestParcelTO.getDestinationOfficeId());
			if(!StringUtil.isNull(cityTO)){
				branchOutManifestParcelTO.setDestinationCityId(cityTO.getCityId());
			}else{
				prepareActionMessage(request, new ActionMessage(ManifestErrorCodesConstants.CITY_DTLS_NOT_EXIST));
			}
			branchOutManifestParcelTO.setManifestType(OutManifestConstants.BRANCH_MANIFEST);
			branchOutManifestParcelTO.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
					
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			branchOutManifestParcelTO.setConsignmentTypeTO(consgType);
			
			setBranchOutmanifestParcelTO(branchOutManifestParcelTO);
			
			if (!StringUtil.isNull(branchOutManifestParcelTO)) {
				
				branchOutmanifestDtls =BranchOutManifestParcelConverter
						.prepareBranchOutManifestDtlsParcelList(branchOutManifestParcelTO);
				branchOutManifestParcelTO.setBranchOutManifestParcelDetailsList(branchOutmanifestDtls);
				ManifestDO manifestDO = null;
				/*ManifestProcessDO manifestProcessDO = new ManifestProcessDO();*/
				List<BookingDO> allBooking = new ArrayList<BookingDO>();
				List<ConsignmentDO> bookingConsignment = new ArrayList<ConsignmentDO>();
				Set<ConsignmentDO> allConsignments = new LinkedHashSet<ConsignmentDO>();
				manifestDO = prepareManifestForBranchOutParcel(manifestDO,
						branchOutManifestParcelTO, allBooking, bookingConsignment, allConsignments);
				manifestDO.setConsignments(allConsignments);
				
				branchOutManifestParcelService = (BranchOutManifestParcelService) getBean(SpringConstants.BRANCH_OUT_MANIFEST_PARCEL_SERVICE);
				
				transMsg = branchOutManifestParcelService.saveOrUpdateBranchOutManifestParcel(
						manifestDO, branchOutManifestParcelTO, allBooking,
						bookingConsignment, allConsignments);
				
				if(transMsg.equalsIgnoreCase("O")){
					branchOutManifestParcelTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_SAVED_SUCCESSFULLY,
							null));
					
					
					jsonResult = JSONSerializer.toJSON(branchOutManifestParcelTO).toString();
					
				}else if(transMsg.equalsIgnoreCase("C")){
					twoWayWrite(branchOutManifestParcelTO);
					branchOutManifestParcelTO
					.setSuccessMessage(getMessageFromErrorBundle(
							request,
							ManifestErrorCodesConstants.MANIFEST_CLOSED_SUCCESSFULLY,
							null));
					
					
					jsonResult = JSONSerializer.toJSON(branchOutManifestParcelTO).toString();
				}
		
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:saveOrUpdateBranchOutManifestParcel()"
					,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} 
		catch (CGSystemException e) {
			LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:saveOrUpdateBranchOutManifestParcel()"
					+ e.getMessage());
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
			
		}catch (Exception e) {
			LOGGER.error("Error occured in BranchOutManifestParcelAction :: ..:saveOrUpdateBranchOutManifestParcel()"
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
		LOGGER.trace("BranchOutManifestParcelAction::saveOrUpdateBranchOutManifestParcel::END");
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
		LOGGER.debug("BranchOutManifestParcelAction::printManifestDtls::START");
		try {
			String manifestNo = request.getParameter(OutManifestConstants.MANIFEST_NO);
			String loginOfficeId = request.getParameter(OutManifestConstants.LOGIN_OFFICE_ID);
			Integer loginOffId = Integer.parseInt(loginOfficeId);
			
			if (StringUtils.isNotEmpty(manifestNo)) {
				ManifestInputs manifestTO = new ManifestInputs();
				manifestTO.setLoginOfficeId(loginOffId);
				manifestTO.setManifestNumber(manifestNo);
				manifestTO.setManifestProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
				manifestTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
				manifestTO.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
				manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				branchOutManifestParcelService = getBranchOutManifestParcelServiceForParcel();
				BranchOutManifestParcelTO branchOutManifestParcelTO = branchOutManifestParcelService
							.searchManifestDtls(manifestTO);
				/* set branchOutManifestParcelTO to request */
				request.setAttribute("branchOutManifestParcelTO", branchOutManifestParcelTO);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in BranchOutManifestParcelAction::printManifestDtls::"
					+ e.getMessage());
		}
		LOGGER.debug("BranchOutManifestParcelAction::printManifestDtls::END");
		return mapping.findForward(ManifestConstants.URL_PRINT_BRANCH_OUTMANIFEST_PARCEL);
	}
	
	/**
	 * @return outManifestCommonService
	 */
	private OutManifestCommonService getOutManifestCommonServiceForParcel() {
		if(StringUtil.isNull(outManifestCommonService)){
			outManifestCommonService = (OutManifestCommonService)
					getBean(SpringConstants.OUT_MANIFEST_COMMON_SERVICE);
		}
		return outManifestCommonService;
	}
	
	/**
	 * @return branchOutManifestParcelService
	 */
	private BranchOutManifestParcelService getBranchOutManifestParcelServiceForParcel() {
		if(StringUtil.isNull(branchOutManifestParcelService)){
			branchOutManifestParcelService = (BranchOutManifestParcelService) 
					getBean(SpringConstants.BRANCH_OUT_MANIFEST_PARCEL_SERVICE);
		}
		return branchOutManifestParcelService;
	}
	
	
	private void setBranchOutmanifestParcelTO(BranchOutManifestParcelTO branchOutmanifestParcelTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("BranchOutManifestParcelAction :: setBranchOutmanifestParcelTO() :: START ::------------>:::::::");
		OfficeTO loginOfficeTO = null;
		Integer operatingLevel = branchOutmanifestParcelTO.getOperatingLevel();
		// If manifest is creating first time, set the below values
		if (StringUtil.isEmptyInteger(branchOutmanifestParcelTO.getManifestId())) {
			outManifestUniversalService = (OutManifestUniversalService) getBean(SpringConstants.OUT_MANIFEST_UNIVERSAL_SERVICE);
			// Setting Login Office details
			loginOfficeTO = new OfficeTO();
			loginOfficeTO.setOfficeId(branchOutmanifestParcelTO.getLoginOfficeId());
			if (StringUtils.isNotEmpty(branchOutmanifestParcelTO.getOfficeCode())) {
				loginOfficeTO.setOfficeCode(branchOutmanifestParcelTO.getOfficeCode());
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
					branchOutmanifestParcelTO, loginOfficeTO);
			branchOutmanifestParcelTO.setOperatingLevel(operatingLevel);

			// Getting Consignment Type details based on
			List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
					.getConsignmentTypes(branchOutmanifestParcelTO
							.getConsignmentTypeTO());
			// Setting Consignment type id
			if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
				ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs
						.get(0);
				branchOutmanifestParcelTO.setConsignmentTypeTO(consignmentTypeTO);
			}
			// Setting process id
			ProcessTO processTO = new ProcessTO();
			processTO
					.setProcessCode(CommonConstants.PROCESS_BRANCH_OUT_MANIFEST);
			processTO = outManifestUniversalService.getProcess(processTO);
			branchOutmanifestParcelTO.setProcessId(processTO.getProcessId());
			branchOutmanifestParcelTO.setProcessCode(processTO.getProcessCode());

			// Setting process number
			String processNumber = outManifestCommonService
					.createProcessNumber(processTO, loginOfficeTO);
			branchOutmanifestParcelTO.setProcessNo(processNumber);
		}

		// The below attributes will always get updated
		if(!StringUtil.isNull(branchOutmanifestParcelTO.getDestinationOfficeId())){
		if (branchOutmanifestParcelTO.getDestinationOfficeId().intValue() == CommonConstants.ZERO) {
			branchOutmanifestParcelTO.setIsMulDestination(CommonConstants.YES);
		}
		}
		/**
		 * changes related to out manifest destination office ids = origin city
		 * All hub offices + destination city all hub offices + selected
		 * destination office
		 **/

		/*List<Integer> destHubList = null;
		List<OfficeTO> destOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						branchOutmanifestDoxTO.getDestinationCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);*/
		/*if (!StringUtil.isEmptyList(destOfficeTOs)) {
			destHubList = new ArrayList<>();
			for (OfficeTO officeTO1 : destOfficeTOs) {
				destHubList.add(officeTO1.getOfficeId());
			}
			branchOutmanifestDoxTO.setDestHubOffList(destHubList);
		}*/

		// If the Operating level is less than = 10 i.e. the origin Branch
		// then only add the origin hubs in out manifest destinations
		/*if (operatingLevel <= 10) {
			List<Integer> originHubList = null;
			List<OfficeTO> orgOfficeTOs = outManifestCommonService
					.getAllOfficesByCityAndOfficeTypeCode(
							branchOutmanifestDoxTO.getLoginCityId(),
							CommonConstants.OFF_TYPE_HUB_OFFICE);
			if (!StringUtil.isEmptyList(destOfficeTOs)) {
				originHubList = new ArrayList<>();
				for (OfficeTO officeTO2 : orgOfficeTOs) {
					originHubList.add(officeTO2.getOfficeId());
				}
				branchOutmanifestDoxTO.setOriginHubOffList(originHubList);
			}
		}*/
		LOGGER.trace("BranchOutManifestParcelAction :: setBranchOutmanifestParcelTO() :: END------------>:::::::");
	}

	private ManifestDO prepareManifestForBranchOutParcel(ManifestDO manifestDO,
			BranchOutManifestParcelTO branchOutmanifestParcelTO, List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment, Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		/* prepare Manifest for search */
		manifestDO = OutManifestBaseConverter
				.prepateManifestDO(branchOutmanifestParcelTO);

		
		manifestCommonService = (ManifestCommonService) getBean(SpringConstants.MANIFEST_COMMON_SERVICE);

		//setting in the grid
		GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
		if (!StringUtil.isEmpty(branchOutmanifestParcelTO.getConsgNos())) {
			gridItemOrderDO.setConsignments(branchOutmanifestParcelTO.getConsgNos());
		}
		
		gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
				ManifestConstants.ACTION_SAVE);
		manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition()
				.toString());
		
		
		
		for (int i = 0; i < branchOutmanifestParcelTO.getConsgNos().length; i++) {
			
					if(!StringUtil.isStringEmpty(branchOutmanifestParcelTO.getConsgNos()[i])){
					/** prepare consignment */
			
					//ConsignmentDO consignmentDO = new ConsignmentDO();
					
					/* Setting consignment number */
					//consignmentDO.setConsgNo(branchOutmanifestDoxTO.getConsgNos()[i]);
						String consgNo = branchOutmanifestParcelTO.getConsgNos()[i];
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
					if (!StringUtil.isEmptyInteger(branchOutmanifestParcelTO
							.getProcessId())) {
						ProcessDO updatedProcessDO = new ProcessDO();
						updatedProcessDO.setProcessId(branchOutmanifestParcelTO
								.getProcessId());
						consignmentDO.setUpdatedProcess(updatedProcessDO);
					}

					/** Setting Billing Flags - Added By Himal on 6 Dec. 2013 */
					boolean isBillingFlagReq = Boolean.FALSE;
					if (!StringUtil.isEmptyDouble(consignmentDO.getFinalWeight()) 
							&& (consignmentDO.getFinalWeight().doubleValue() != branchOutmanifestParcelTO.getWeights()[i].doubleValue())) {
						isBillingFlagReq = Boolean.TRUE;
					} else if (StringUtil.isEmptyDouble(consignmentDO.getFinalWeight())) {
						isBillingFlagReq = Boolean.TRUE;
					}
					if(isBillingFlagReq) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
					}
					
					/* Set Weight */
					consignmentDO.setFinalWeight(branchOutmanifestParcelTO.getWeights()[i]);
					
					/* Setting status */
				/*	consignmentDO.setConsgStatus("B");

					 Origin office 
					consignmentDO.setOrgOffId(branchOutmanifestParcelTO
							.getLoginOfficeId());

					/* Operation Level */
					ConsignmentTO consigmentTO = new ConsignmentTO();
					consigmentTO.setOrgOffId(branchOutmanifestParcelTO
							.getLoginOfficeId());
					consigmentTO.setDestOffice(null);
					if (branchOutmanifestParcelTO.getDestinationOfficeId() != null) {
						OfficeTO destoffTO = new OfficeTO();
						destoffTO.setOfficeId(branchOutmanifestParcelTO
								.getDestinationOfficeId());
						consigmentTO.setDestOffice(destoffTO);
					}
					/*CityTO destCityTO = new CityTO();
					if(!StringUtil.isNull(branchOutmanifestDoxTO.getDestCityIds()[i])){
					destCityTO.setCityId(branchOutmanifestDoxTO.getDestCityIds()[i]);
					consigmentTO.setDestCity(destCityTO);
					}*/
					OfficeTO offTO = new OfficeTO();
					offTO.setOfficeId(branchOutmanifestParcelTO.getLoginOfficeId());
					/*Integer operatingLevel = outManifestCommonService
							.getConsgOperatingLevel(consigmentTO, offTO);
					consignmentDO.setOperatingLevel(operatingLevel);*/

					/*
					 * operating level needs to be set in case of
					 * weight/destination changes
					 */
					consignmentDO.setOperatingOffice(branchOutmanifestParcelTO
							.getLoginOfficeId());

					
					allConsignments.add(consignmentDO);
					}//end of if loop

				} /* END of FOR Loop - Outer */
		

		manifestDO.setConsignments(allConsignments);

		/* prepare Manifest */
		manifestDO = BranchOutManifestParcelConverter.prepareManifestDO(branchOutmanifestParcelTO, manifestDO);

		return manifestDO;
	}
	
	private void setRequestParameters(HttpServletRequest request,OutManifestValidate cnValidateTO) throws NumberFormatException, CGBusinessException, CGSystemException{
		
		OfficeTO officeTO= null;
		CityTO cityTO=null;
		
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
		officeTO  = outManifestCommonService.getOfficeDetails(Integer.parseInt(request
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
	}
	
}
