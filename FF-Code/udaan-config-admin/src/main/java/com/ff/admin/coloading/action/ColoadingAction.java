package com.ff.admin.coloading.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.CGExcelUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.form.AirColoadingForm;
import com.ff.admin.coloading.form.TrainColoadingForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.AirColoadingTO;
import com.ff.coloading.AwbTO;
import com.ff.coloading.CdTO;
import com.ff.coloading.ColoadingVendorTO;
import com.ff.coloading.TrainColoadingTO;
import com.ff.coloading.TrainDetailsTO;
import com.ff.domain.coloading.ColoadingAirDO;
import com.ff.domain.coloading.ColoadingTrainContractDO;
import com.ff.domain.transport.FlightDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * 
 * @author isawarka
 */
public class ColoadingAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/**
	 * Coloading service to perform the business validation and data base call
	 */
	private ColoadingService coloadingService;

	/**
	 * This method is used to render pre-populate data if user enter into the
	 * Air Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingAction:preparePage:Start");
		ActionMessage actionMessage = null;
		try {
			AirColoadingForm coloadingForm = (AirColoadingForm) form;
			AirColoadingTO coloadingTo = (AirColoadingTO) coloadingForm.getTo();
			coloadingTo = new AirColoadingTO();
			loadCommonDataForAir(request, coloadingTo, coloadingForm);
			coloadingTo.setEffectiveFrom(DateUtil
					.getDDMMYYYYDateToString(DateUtil.getFutureDate(1)));
			coloadingTo.setCdType(null); // To select SELECT as default value for CD Type combo
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:preparePage:End");
		return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
	}

	/**
	 * This method is used to render pre-populate data if user enter into the
	 * Train Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preparePageForTrain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingAction:preparePageForTrain:Start");
		ActionMessage actionMessage = null;
		String url = ColoadingConstants.TRAIN_COLOADING_SUCCESS;
		try {
			if(!isLoggedInOfficeRHO(request)){
				url = ColoadingConstants.TRAIN_COLOADING_WELCOME;
				actionMessage = new ActionMessage(ColoadingConstants.TRAIN_COLOADING_ONLY_ALLOWED_AT_RHO);
				
			}else {
				TrainColoadingForm coloadingForm = (TrainColoadingForm) form;
				TrainColoadingTO coloadingTo = (TrainColoadingTO) coloadingForm.getTo();
				coloadingTo = new TrainColoadingTO();
				loadCommonDataForTrain(request, coloadingTo, coloadingForm);
				coloadingTo.setEffectiveFrom(DateUtil.getDDMMYYYYDateToString(DateUtil.getFutureDate(1)));
			}		
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::preparePageForTrain ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::preparePageForTrain ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::preparePageForTrain ..Exception :"
					+ e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:preparePageForTrain:End");
		// return mapping.findForward(ColoadingConstants.TRAIN_COLOADING_SUCCESS);
		return mapping.findForward(url);
	}
	
	
	/**
	 * To check whether Logged In Office is brach/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isLoggedInOfficeRHO(HttpServletRequest request){
		LOGGER.debug("ValidateExpenseAction::isLoggedInOfficeRHO()::START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO!=null && loggedInOfficeTO.getOfficeTypeTO()!=null 
				&& loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode().equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
			result = true;
		}
		LOGGER.debug("ColoadingVehiclesContractAction::isLoggedInOfficeRHO()::END");
		return result;
	}	

	/**
	 * This method is used to render common data if user enter into the Air
	 * Coloading configuration screen
	 * 
	 * @param request
	 * @param coloadingTo
	 * @param coloadingForm
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void loadCommonDataForAir(HttpServletRequest request,
			AirColoadingTO coloadingTo, AirColoadingForm coloadingForm)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingAction:loadCommonDataForAir:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		if (coloadingService != null) {
			List<RegionTO> regionTo = coloadingService.getRegions();
			if (!StringUtil.isEmptyColletion(regionTo)) {
				request.setAttribute(ColoadingConstants.REGION_LIST, regionTo);
			}
			if (coloadingTo != null) {
				if (coloadingTo.getOrigionRegionID() != null
						&& coloadingTo.getOrigionRegionID() != -1) {
					List<CityTO> oStationList = coloadingService
							.getCitiesByRegionId(coloadingTo
									.getOrigionRegionID());
					request.setAttribute(ColoadingConstants.ORIGION_CITY_LIST,
							oStationList);
				}
				if (coloadingTo.getDestinationRegionID() != null
						&& coloadingTo.getDestinationRegionID() != -1) {
					List<CityTO> dStationList = coloadingService
							.getCitiesByRegionId(coloadingTo
									.getDestinationRegionID());
					request.setAttribute(
							ColoadingConstants.DESTINATION_CITY_LIST,
							dStationList);
				}

				//TODO duplicate code need to check
				/*if (coloadingTo.getDestinationRegionID() != null
						&& coloadingTo.getDestinationRegionID() != -1) {
					List<CityTO> dStationList = coloadingService
							.getCitiesByRegionId(coloadingTo
									.getDestinationRegionID());
					request.setAttribute(
							ColoadingConstants.DESTINATION_CITY_LIST,
							dStationList);
				}*/
			}
			List<StockStandardTypeTO> cdTypeList = coloadingService
					.getStockStdType(ColoadingConstants.CD_TYPE);
			if (cdTypeList != null) {
				request.setAttribute(ColoadingConstants.CD_TYPE_LIST,
						cdTypeList);
				cdTypeList = null;
			}
			cdTypeList = coloadingService
					.getStockStdType(ColoadingConstants.SSP_RATE_KG);
			List<StockStandardTypeTO> sspRatekgList =new ArrayList<>();
			
				sspRatekgList.add(0, cdTypeList.get(2));
				sspRatekgList.add(1, cdTypeList.get(0));
				sspRatekgList.add(2, cdTypeList.get(1));
		
			if (sspRatekgList != null) {
				request.setAttribute(ColoadingConstants.SSP_RATE_LIST,
						sspRatekgList);
				sspRatekgList = null;
			}
			if (coloadingTo.getOrigionRegionID() != null
					&& coloadingTo.getOrigionRegionID() != -1) {
				loadVendors(request, coloadingTo.getOrigionRegionID(),
						ColoadingConstants.AIR);
			}
			
			coloadingTo.setColoaderType(ColoadingConstants.AIR);
			
			request.setAttribute("to", coloadingTo);
			coloadingForm.setTo(coloadingTo);
			request.setAttribute("airColoadingForm", coloadingForm);
		}
		LOGGER.debug("ColoadingAction:loadCommonDataForAir:End");

	}

	/**
	 * This method is used to render common data if user enter into the Train
	 * Coloading configuration screen
	 * 
	 * @param request
	 * @param coloadingTo
	 * @param coloadingForm
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void loadCommonDataForTrain(HttpServletRequest request,
			TrainColoadingTO coloadingTo, TrainColoadingForm coloadingForm)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingAction:loadCommonDataForTrain:Start");
		ActionMessage actionMessage = null;
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		if (coloadingService != null) {
			List<RegionTO> regionTo = coloadingService.getRegions();
			if (!StringUtil.isEmptyColletion(regionTo)) {
				request.setAttribute(ColoadingConstants.REGION_LIST, regionTo);
			}

			if (coloadingTo != null && coloadingTo.getOrigionRegionID() != null
					&& coloadingTo.getOrigionRegionID() != -1) {
				List<CityTO> oStationList = coloadingService
						.getCitiesByRegionId(coloadingTo.getOrigionRegionID());
				request.setAttribute(ColoadingConstants.ORIGION_CITY_LIST,
						oStationList);
			}
			if (coloadingTo != null
					&& coloadingTo.getDestinationRegionID() != null
					&& coloadingTo.getDestinationRegionID() != -1) {
				List<CityTO> dStationList = coloadingService
						.getCitiesByRegionId(coloadingTo
								.getDestinationRegionID());
				request.setAttribute(ColoadingConstants.DESTINATION_CITY_LIST,
						dStationList);
			}

			if (coloadingTo != null
					&& coloadingTo.getDestinationRegionID() != null
					&& coloadingTo.getDestinationRegionID() != -1) {
				List<CityTO> dStationList = coloadingService
						.getCitiesByRegionId(coloadingTo
								.getDestinationRegionID());
				request.setAttribute(ColoadingConstants.DESTINATION_CITY_LIST,
						dStationList);
			}

			if (coloadingTo.getOrigionRegionID() != null
					&& coloadingTo.getOrigionRegionID() != -1) {
				loadVendors(request, coloadingTo.getOrigionRegionID(),
						ColoadingConstants.TRAIN);
			}
			
			coloadingTo.setColoaderType("RAIL");
			
			HttpSession oldSession = request.getSession(Boolean.FALSE);
			Map<String, Set<String>> vendorToTrainsMap = (Map<String, Set<String>>) oldSession.getAttribute("vendorToFlightsMap");
			if(vendorToTrainsMap != null && (!vendorToTrainsMap.isEmpty())) {
				// Check if this vendor available
				Set<String> mappedVendors = vendorToTrainsMap.keySet();
				if(mappedVendors != null && (! mappedVendors.isEmpty())) {
					if(null != coloadingTo.getVendorId()) {
						if(! mappedVendors.contains(coloadingTo.getVendorId().toString().trim())) {
							coloadingTo.setTrainDetailsList(null);
							//coloadingTo.setIsRenewalAllow(false); 
							coloadingTo.setStoreStatus("X"); // to hide save and submit buttons if vendor not available 
							actionMessage = new ActionMessage(ColoadingConstants.CL0025 );
							prepareActionMessage(request, actionMessage);
						}
					}
				}

				if(coloadingTo.getVendorId() != null) {
					Set<String> vendorToTrainSet = vendorToTrainsMap.get(coloadingTo.getVendorId().toString().trim());
					if(null != coloadingTo.getTrainDetailsList()) {
						Iterator<TrainDetailsTO> itr = coloadingTo.getTrainDetailsList().iterator();
						while (itr.hasNext()) {
							TrainDetailsTO  trainDetailsTO = itr.next();
							if(null != vendorToTrainSet && (!vendorToTrainSet.contains(trainDetailsTO.getTrainNo()))) {
								itr.remove();
							}
						}
					}
				}
			}

			request.setAttribute("to", coloadingTo);
			coloadingForm.setTo(coloadingTo);
			request.setAttribute("trainColoadingForm", coloadingForm);
		}
		LOGGER.debug("ColoadingAction:loadCommonDataForTrain:End");
	}

	/**
	 * This method is used to load the existing configuration data if user click
	 * on load button from the Train Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward loadTrainDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ColoadingAction:loadTrainDetails:Start");
		ActionMessage actionMessage = null;
		try {
			TrainColoadingForm coloadingForm = (TrainColoadingForm) form;
			TrainColoadingTO coloadingTo = (TrainColoadingTO) coloadingForm
					.getTo();
			
			String efDate = request.getParameter("efDate");
			String[] vendorList = request.getParameterValues("vendorList");
			
			setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);
			
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			ColoadingTrainContractDO coloadingTrainContractDO = coloadingService
					.getTrainData(coloadingTo);
			if (coloadingTrainContractDO != null) {
				coloadingTo.setStoreStatus(ColoadingConstants.SAVE_STRING_T);
				coloadingTo = ColoadingCommonConverter
						.convertTrainToFromTrainDo(coloadingTo,
								coloadingTrainContractDO);
				if (coloadingTrainContractDO.getEffectiveFrom() != null) {
					if (coloadingTo.getIsRenewalAllow()
							&& !ColoadingConstants.SAVE_STRING_T.equals(coloadingTo.getStoreStatus())) {
						Date currentDate = DateUtil.getCurrentDate();
						if (coloadingTrainContractDO.getEffectiveFrom().after(
								currentDate)) {
							coloadingTo.setIsRenewalAllow(false);
						}
					}
				}
				if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())){
					coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
				}
			} else {
				actionMessage = new ActionMessage(ColoadingConstants.CL0004);
				prepareActionMessage(request, actionMessage);
			}
			loadCommonDataForTrain(request, coloadingTo, coloadingForm);
			LOGGER.debug("ColoadingAction:loadTrainDetails:END");
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::loadTrainDetails ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::loadTrainDetails ..CGSystemException :"
					+ e);
			actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::loadTrainDetails ..Exception :" + e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:loadTrainDetails:End");
		return mapping.findForward(ColoadingConstants.TRAIN_COLOADING_SUCCESS);
	}
	
	private ArrayList<String> getFlightListForRoute(AirColoadingTO coloadingTo) {
		ArrayList<String> flightList = new ArrayList<String>();
		List<FlightDO> flightDO = coloadingService.getFlightData(coloadingTo);
		System.out.println(flightDO);
		
		for(FlightDO flightInfo : flightDO) {
			if(!(flightInfo instanceof com.ff.domain.transport.BcunFlightDO)) {
				if(flightInfo.getFlightNumber() != null && (!flightList.contains(flightInfo.getFlightNumber()))) {
					flightList.add(flightInfo.getFlightNumber());
				}
			}
		}
		return flightList;
	}
	
	private void setEffectiveFromDateAndVendorInfo(String efDate, String[] vendorList, HttpServletRequest request) {		
		String[] spilttedArray;
		String[] dashSpilttedArray;
		
		request.setAttribute("effectiveFrom", efDate);
		
		String[] vendorListArray = vendorList[0].split(",");

		List<ColoadingVendorTO> coloadingVendorTOs = new ArrayList<ColoadingVendorTO>();
		ColoadingVendorTO to = null;
		for(int i=0;i<vendorListArray.length;i++) {
			if(vendorListArray[i].equalsIgnoreCase("--Select--=")) {
				continue;
			}
/*			if (i==0 && vendorListArray.length != 1) {
				continue;
			}*/
			
			spilttedArray = vendorListArray[i].split("=");
			
			dashSpilttedArray = spilttedArray[0].split("-");
			to = new ColoadingVendorTO();
			to.setVendorId(Integer.parseInt(spilttedArray[1]));
			to.setVendorCode(dashSpilttedArray[0]);
			to.setBusinessName(dashSpilttedArray[1]);
			coloadingVendorTOs.add(to);	
		}
		
		request.setAttribute("vendorList", coloadingVendorTOs);
	}	

	/**
	 * This method is used to load the existing configuration data if user
	 * select the ciry from the configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadAirDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:loadSavedDataForAir:Start");
			AirColoadingForm coloadingForm = (AirColoadingForm) form;
			AirColoadingTO coloadingTo = (AirColoadingTO) coloadingForm.getTo();

			String efDate = request.getParameter("efDate");
			String[] vendorList = request.getParameterValues("vendorList");
			
			setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);
			
			ColoadingAirDO coloadingAirDO = coloadingService
					.getAirData(coloadingTo);
			if (coloadingAirDO != null) {
				ArrayList<String> flightList = getFlightListForRoute(coloadingTo);
				coloadingTo = ColoadingCommonConverter.convertAirColoadingToFromAirColoadingDO(coloadingAirDO, coloadingTo, flightList);
				
				if (coloadingTo.getIsRenewalAllow()
						&& !"T".equals(coloadingTo.getStoreStatus())) {
					Date currentDate = DateUtil.getCurrentDate();
					if (coloadingAirDO.getEffectiveFrom().after(currentDate)) {	
						coloadingTo.setIsRenewalAllow(false);
					}
				}
			}
			loadCommonDataForAir(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::loadSavedDataForAir ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::loadSavedDataForAir ..CGSystemException :"
					+ e);
			actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::loadSavedDataForAir ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		
		LOGGER.debug("ColoadingAction:loadSavedDataForAir:End");
		return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
	}

	/**
	 * This method is used to load the rates from the selected Excel template
	 * file if user select the correct template for CD type and click on upload
	 * button from the Air Coloading configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward uploadXlsFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		AirColoadingForm coloadingForm = (AirColoadingForm) form;
		AirColoadingTO coloadingTo = (AirColoadingTO) coloadingForm.getTo();
		
		String efDate = request.getParameter("efDate");
		String vendorName = request.getParameter("vendorName");
		String vendorId = request.getParameter("vendorId");
		String vendorArray[] = vendorName.split("-");
		
		HttpSession oldSession = request.getSession(Boolean.FALSE);
		Map<String, Set<String>> vendorToFlightsMap = (Map<String, Set<String>>) oldSession.getAttribute("vendorToFlightsMap");

		AirColoadingTO renewedAirColoadingTo = new AirColoadingTO();	
		if(ColoadingConstants.RENEW_R.equals(request.getParameter(ColoadingConstants.RENEW_R))) {		
			renewedAirColoadingTo.setOrigionRegionID(coloadingTo.getOrigionRegionID());
			renewedAirColoadingTo.setDestinationRegionID(coloadingTo.getDestinationRegionID());
			renewedAirColoadingTo.setOrigionCityID(coloadingTo.getOrigionCityID());
			renewedAirColoadingTo.setDestinationCityID(coloadingTo.getDestinationCityID());
			renewedAirColoadingTo.setVendorId(coloadingTo.getVendorId());
			renewedAirColoadingTo.setEffectiveFrom(coloadingTo.getEffectiveFrom());	
			renewedAirColoadingTo.setCdType(coloadingTo.getCdType());
			renewedAirColoadingTo.setSspWeightSlab(coloadingTo.getSspWeightSlab());
			renewedAirColoadingTo.setStoreStatus(request.getParameter(ColoadingConstants.RENEW_R));
			renewedAirColoadingTo.setRenewFlag(request.getParameter(ColoadingConstants.RENEW_R));
		}
		
		ArrayList<String> flightList = null;
		
		try {
			LOGGER.debug("ColoadingAction:uploadXlsFile:Start");
			coloadingTo.setRenewFlag(request.getParameter(ColoadingConstants.RENEW_R));
				
			ColoadingAirDO coloadingAirDO = coloadingService.getAirData(coloadingTo);
			if (coloadingAirDO != null) {
				coloadingTo.setStoreStatus(String.valueOf(coloadingAirDO.getStoreStatus()));
				
				// ArrayList<String> flightList = getFlightListForRoute(coloadingTo);
				flightList = getFlightListForRoute(coloadingTo);
				coloadingTo = ColoadingCommonConverter.convertAirColoadingToFromAirColoadingDO(coloadingAirDO, coloadingTo, flightList);
				
				if(!ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())) {
					if(ColoadingConstants.SUBMIT_P.equals(String.valueOf(coloadingAirDO
							.getStoreStatus()))){
						if (coloadingTo.getIsRenewalAllow()) {
							Date currentDate = DateUtil.getCurrentDate();
							if (coloadingAirDO.getEffectiveFrom().after(currentDate)) {
								coloadingTo.setIsRenewalAllow(false);							
							}
						}
						
						loadCommonDataForAir(request, coloadingTo, coloadingForm);
//						return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
					}
				}
			}

			final FormFile xlsFile = coloadingTo.getXlsTemplateFile();
			if (xlsFile != null
					&& (ColoadingConstants.XLS.equals(xlsFile.getContentType()) || ColoadingConstants.XLSX
							.equals(xlsFile.getContentType()))) {
				List<List> list = CGExcelUtil.getAllRowsValues(xlsFile
						.getInputStream());
				List<List> errList = new ArrayList();
				List<String> headerList = list.get(0);
				int columnLenght = headerList.size();
				headerList.add(ColoadingConstants.ERROR_DESCRIPTION);
				errList.add(headerList);
				// Removing Header Row
				list.remove(0);	
		
				//In case of Renew we are adding old list to new list 
				if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())
						&&!StringUtil.isEmptyColletion(coloadingTo.getCdToList())){
				for (CdTO cdTo : coloadingTo.getCdToList()) {
					boolean isFlightNoPresent =false;
					List oldList = new ArrayList();
					String oldFlightNo =cdTo.getFlightNo();
					
					for(int i=0;i<list.size();i++){
						//new List comparision
						String newFlightNo =list.get(i).get(1).toString();
						
						if(oldFlightNo.equals(newFlightNo)){
							isFlightNoPresent =true;
							break;
						}
					}	
					if(!isFlightNoPresent){ 
						prepareOldCdListTos(list, cdTo, oldList);
					}		
				}
				}else if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())
						&&!StringUtil.isEmptyColletion(coloadingTo.getAwbToList())){
					
					for (AwbTO awbTo : coloadingTo.getAwbToList()) {
						boolean isFlightNoPresent =false;
						List oldList = new ArrayList();
						String oldFlightNo =awbTo.getFlightNo();
						
						for(int i=0;i<list.size();i++){
							//new List comparision
							String newFlightNo =list.get(i).get(1).toString();
							
							if(oldFlightNo.equals(newFlightNo)){
								isFlightNoPresent =true;
								break;
							}
						}	
						if(!isFlightNoPresent){ 
							prepareOldAwbListTos(list, awbTo, oldList);
						}		
					} 
				}
				if (!StringUtil.isEmptyColletion(list)) {
					// AWB template
					List<AirColoadingTO> coloadingToList = new ArrayList<>();
					List<AwbTO> newAwbRateList = new ArrayList<>();
					
					if (ColoadingConstants.AWB.equals(coloadingTo.getCdType())) {
						List<AwbTO> awbTos = new ArrayList<>(list.size());
						List<AwbTO> awbToOld = coloadingTo.getAwbToList();
						
						flightList = getFlightListForRoute(coloadingTo);

						Set<String> flightSet = new HashSet<String>();
						List<String> secondRow = new ArrayList<String>();
						
						AwbTO awbTo;
						for (List xlsRow : list) {
							
							Object[] rowContents = xlsRow.toArray();
							if(rowContents.length > ColoadingConstants.AWB_LENGTH) {
								for(int i=0; i<ColoadingConstants.AWB_LENGTH; i++) {
									secondRow.add((String) xlsRow.get(i));
								}
							} else {
								secondRow = xlsRow;
							}
							
							Object[] values = secondRow.toArray();
							if(values != null	&& values.length >= ColoadingConstants.AWB_LENGTH) {
							if (columnLenght == ColoadingConstants.AWB_LENGTH) {
								awbTo = new AwbTO();
								awbTo = ColoadingCommonConverter.populateAwbTo(awbTo, values,coloadingService, awbTos, flightSet, flightList, vendorToFlightsMap, coloadingTo.getVendorId().toString());
								flightSet.addAll(awbTo.getFlightSet());
																
								if (awbTo.getErroString() == null) {
									awbTos.add(awbTo);
								} else {
									secondRow.add(awbTo.getErroString());
									errList.add(secondRow);
								}
							} else {
								throw new CGBusinessException(
										ColoadingConstants.CL0002);
							}
							}else{
								if(! (rowContents.length > ColoadingConstants.AWB_LENGTH)) {				
									String[] vendorList = request.getParameterValues("vendorList");
									
									setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);
									
									throw new CGBusinessException(ColoadingConstants.CL0002);
								} else {
									throw new CGBusinessException(ColoadingConstants.CL0019);
								}
							}
						}
						if (!CGCollectionUtils.isEmpty(awbTos)) {
							if (awbToOld != null) {
								List<AwbTO> finalList = new ArrayList<>();
//								List<AwbTO> newAwbRateList = new ArrayList<>();
								
								for(AwbTO to : awbTos){
									if(awbToOld.contains(to) &&  !(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()))){
										for(AwbTO Oldto : awbToOld){
											if(Oldto.getFlightNo().equalsIgnoreCase(to.getFlightNo())){
												to.setAwbId(Oldto.getAwbId());
												to.setCreatedBy(Oldto.getCreatedBy());
												to.setCreatedDate(Oldto.getCreatedDate());
												finalList.add(to);
											}
										}
										
										Iterator<AwbTO> itr = finalList.iterator();
										while (itr.hasNext()) {
											AwbTO  awbTO = itr.next();
											if(!flightList.contains(awbTO.getFlightNo().trim())) {
												itr.remove();
											}									
										}	
										
										awbToOld.removeAll(finalList);
										finalList.addAll(awbToOld);
										   
/*										if(null != newAwbRateList) {
										   finalList.removeAll(newAwbRateList);
										   finalList.addAll(newAwbRateList);
										}*/
										
										coloadingTo.setAwbToList(finalList);
										if(! coloadingToList.contains(coloadingTo)) {
											coloadingToList.add(coloadingTo);
										}	
										
									}
									else if(! awbToOld.contains(to) && !(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()))){
										finalList.removeAll(awbToOld);
										finalList.addAll(awbToOld);
										finalList.add(to);
										coloadingTo.setAwbToList(finalList);
										
										if(! coloadingToList.contains(coloadingTo)) {
											coloadingToList.add(coloadingTo);
										}	
									}
									else{
										newAwbRateList.add(to);
										
										if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())) {
											renewedAirColoadingTo.setAwbToList(newAwbRateList);
											if(! coloadingToList.contains(renewedAirColoadingTo)) {
												coloadingToList.add(renewedAirColoadingTo);
											}			
										} else {
/*											if(null != finalList) {
											    newAwbRateList.removeAll(finalList);
												newAwbRateList.addAll(finalList);
											}*/
											
											coloadingTo.setAwbToList(newAwbRateList);
											if(! coloadingToList.contains(coloadingTo)) {
												coloadingToList.add(coloadingTo);
											}
										}
									}
									
									
									
								}
																
								awbToOld = null;
							} else {
//								flightList = getFlightListForRoute(coloadingTo);
								Iterator<AwbTO> itr = awbTos.iterator();
								while (itr.hasNext()) {
									AwbTO  awbTO = itr.next();
									if(!flightList.contains(awbTO.getFlightNo().trim())) {
										itr.remove();
									}									
								}
								coloadingTo.setAwbToList(awbTos);
								if(! coloadingToList.contains(coloadingTo)) {
									coloadingToList.add(coloadingTo);
								}
							}
						}

					} else if (ColoadingConstants.CD.equals(coloadingTo
							.getCdType())) {
						List<CdTO> cdTos = new ArrayList<>(list.size());
						List<CdTO> cdTosOld = coloadingTo.getCdToList();
						
						CdTO cdTo;	
						
						flightList = getFlightListForRoute(coloadingTo);
						
						Set<String> flightSet = new HashSet<String>();
						
						List<String> secondRow = new ArrayList<String>();
						
						for (List xlsRow : list) {
							Object[] rowContents = xlsRow.toArray();
							if(rowContents.length > ColoadingConstants.CD_LENGTH) {
								for(int i=0; i<ColoadingConstants.CD_LENGTH; i++) {
									secondRow.add((String) xlsRow.get(i));
								}
							} else {
								secondRow = xlsRow;
							}
							
							Object[] values = secondRow.toArray();
							if(values != null	&& values.length >= ColoadingConstants.CD_LENGTH) {
								if (columnLenght == ColoadingConstants.CD_LENGTH) {
									cdTo = new CdTO();
									cdTo = ColoadingCommonConverter.populateCdTo(cdTo, values, coloadingService, flightSet, flightList, vendorToFlightsMap, coloadingTo.getVendorId().toString());
									flightSet.addAll(cdTo.getFlightSet());
									if (cdTo.getErroString() == null) {
										cdTos.add(cdTo);
									} else {
										secondRow.add(cdTo.getErroString());
										errList.add(secondRow);
									}
								} else {
									
									String[] vendorList = request.getParameterValues("vendorList");
									
									setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);									
									
									throw new CGBusinessException(ColoadingConstants.CL0002);
								}
							} else if(values != null && !(secondRow.toArray().length >= ColoadingConstants.CD_LENGTH)) {
								continue;
							}
							else{
								if(! (rowContents.length > ColoadingConstants.CD_LENGTH)) {

									String[] vendorList = request.getParameterValues("vendorList");
									
									setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);
									
									throw new CGBusinessException(ColoadingConstants.CL0002);
								} else {
									throw new CGBusinessException(ColoadingConstants.CL0019);
								}
							}
						}
						
						
						if (!CGCollectionUtils.isEmpty(cdTos)) {
							if (cdTosOld != null) {
								List<CdTO> finalList = new ArrayList<>();
								List<CdTO> newRateList = new ArrayList<>();
//								List<AirColoadingTO> coloadingToList = new ArrayList<>();
								for(CdTO to : cdTos){
									if(cdTosOld.contains(to) && !(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()))){
										for(CdTO Oldto : cdTosOld){
											if(Oldto.getFlightNo().equalsIgnoreCase(to.getFlightNo())){
												to.setCdId(Oldto.getCdId());
												to.setCreatedBy(Oldto.getCreatedBy());
												to.setCreatedDate(Oldto.getCreatedDate());
												finalList.add(to);
											}
										}
										
										Iterator<CdTO> itr = finalList.iterator();
										while (itr.hasNext()) {
											CdTO  cd = itr.next();
											if(!flightList.contains(cd.getFlightNo().trim())) {
												itr.remove();
											}									
										}	
										
										cdTosOld.removeAll(finalList);
										finalList.addAll(cdTosOld);
										
										coloadingTo.setCdToList(finalList);
										if(! coloadingToList.contains(coloadingTo)) {
											coloadingToList.add(coloadingTo);
										}										
																		
									}
									else if(! cdTosOld.contains(to) && !(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag()))){
										finalList.removeAll(cdTosOld);
										finalList.addAll(cdTosOld);
										finalList.add(to);
										coloadingTo.setCdToList(finalList);
										
										if(! coloadingToList.contains(coloadingTo)) {
											coloadingToList.add(coloadingTo);
										}	
									}
									else{
										newRateList.add(to);
										
										if(ColoadingConstants.RENEW_R.equals(coloadingTo.getRenewFlag())) {
											renewedAirColoadingTo.setCdToList(newRateList);
											if(! coloadingToList.contains(renewedAirColoadingTo)) {
												coloadingToList.add(renewedAirColoadingTo);
											}
										} else {	
											coloadingTo.setCdToList(newRateList);
											if(! coloadingToList.contains(coloadingTo)) {
												coloadingToList.add(coloadingTo);
											}	
										}
									}
								}
								
								cdTosOld = null;
							} else {
//								flightList = getFlightListForRoute(coloadingTo);
								Iterator<CdTO> itr = cdTos.iterator();
								while (itr.hasNext()) {
									CdTO  cd = itr.next();
									if(!flightList.contains(cd.getFlightNo().trim())) {
										itr.remove();
									}									
								}	
								coloadingTo.setCdToList(cdTos);
								if(! coloadingToList.contains(coloadingTo)) {
									coloadingToList.add(coloadingTo);
								}
							}
						}
					} else {
						throw new CGBusinessException(ColoadingConstants.CL0002);
					}
					
					
					if (!CGCollectionUtils.isEmpty(coloadingTo.getAwbToList())
							|| !CGCollectionUtils.isEmpty(coloadingTo
									.getCdToList())) {
						int userId = coloadingService.getUserID(request);
						coloadingTo.setStoreStatus(String.valueOf(ColoadingConstants.SAVE_CHAR));
						
						for(AirColoadingTO  airColoadingTo : coloadingToList) {
							airColoadingTo.setStoreStatus(String.valueOf(ColoadingConstants.SAVE_CHAR));
							coloadingAirDO = coloadingService.saveColoadingAir(airColoadingTo, userId);
							if (coloadingAirDO != null) {
								flightList = getFlightListForRoute(airColoadingTo);
								coloadingTo = ColoadingCommonConverter.convertAirColoadingToFromAirColoadingDO(coloadingAirDO, coloadingTo, flightList);
							}
						}
						
						
					}
					loadCommonDataForAir(request, coloadingTo, coloadingForm);
					
					String[] vendorList = request.getParameterValues("vendorList");
					setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);
				}

				if (errList.size() > 1) {
					int invalidCount = 0;
					String fileName = xlsFile.getFileName();
					if (StringUtils.isNotBlank(fileName)) {
						int dot = fileName.lastIndexOf('.');
						String baseFileName = (dot == -1) ? fileName : fileName
								.substring(0, dot);
						String extension = (dot == -1) ? "" : fileName
								.substring(dot + 1);
						fileName = baseFileName + "_" + "ERROR." + extension;
					}
					request.setAttribute("fileName", fileName);
					request.setAttribute("isError", ColoadingConstants.ERROR);
					HttpSession session = request.getSession(false);
					session.setAttribute("errorList", errList);
					
					String errorMessage = null;
					for(int i=0;i<errList.size();i++) {
						if (ColoadingConstants.AWB.equals(coloadingTo.getCdType())) {
							errorMessage = (String) errList.get(i).get(ColoadingConstants.AWB_LENGTH);
						} else {
							errorMessage = (String) errList.get(i).get(ColoadingConstants.CD_LENGTH);
						}
						
						if(errorMessage.contains("Invalid Flight Number,") 
								|| errList.get(i).contains("Invalid Air line,") 
								|| errList.get(i).contains("Invalid Air line for Flight Number,") 
								|| errList.get(i).contains("Invalid Air line/Flight Number,")
								|| errList.get(i).contains("Duplicate Flight Number,")
								|| errList.get(i).contains("Flight Number not available for this vendor,")
								|| errList.get(i).contains("Invalid")
								|| errorMessage.contains("Invalid")		
								|| errorMessage.contains("Flight Number not available for this vendor,")	
								// || errList.get(i).contains("Flight Number already uploaded,")
								) {				
							invalidCount++;					
						}
					}
					
					LOGGER.debug("Counted: " + invalidCount);
					
					if(invalidCount == list.size()) {
						actionMessage = new ActionMessage(ColoadingConstants.CL0021);
					} else if(invalidCount > 0 && invalidCount <= list.size()) {
						actionMessage = new ActionMessage(ColoadingConstants.CL0017);
					}								
					//actionMessage = new ActionMessage(ColoadingConstants.CL0017);
				} else {
					 actionMessage = new ActionMessage(ColoadingConstants.CL0003);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::uploadXlsFile ..CGBusinessException :"
					, e);
			getBusinessError(request, e);
			try {
				loadCommonDataForAir(request, coloadingTo, coloadingForm);
			} catch (CGSystemException| CGBusinessException e1) {
				LOGGER.error("ColoadingAction::uploadXlsFile(CGBusinessException::loadCommonDataForAir) .Exception :"
						, e);
			} 
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::uploadXlsFile ..CGSystemException :"
					, e);
			actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::uploadXlsFile ..Exception :" , e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:uploadXlsFile:End");
		return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
	}

	/**
	 * This method is used to create the old CdTo list in case of renew
	 * @param list
	 * @param cdTo
	 * @param oldList
	 */
	@SuppressWarnings("unchecked")
	private void prepareOldCdListTos(List<List> list, CdTO cdTo, List oldList) {
		oldList.add(cdTo.getAirLine());
		oldList.add(cdTo.getFlightNo());
		oldList.add(String.valueOf(cdTo.getBillingRate()));
		oldList.add(String.valueOf(cdTo.getFuelSurcharge()));
		oldList.add(String.valueOf(cdTo.getOctroiPerBag()));
		oldList.add(String.valueOf(cdTo.getOctroiPerKG()));
		oldList.add(String.valueOf(cdTo.getSurcharge()));
		oldList.add(String.valueOf(cdTo.getOtherCharges()));
		oldList.add(String.valueOf(cdTo.getCd()));
		oldList.add(String.valueOf(cdTo.getsSPRate()));
		list.add(oldList);
	}

	/**
	 * This method is used to create the old awbto list in case of renew
	 * @param list
	 * @param awbTo
	 * @param oldList
	 */
	@SuppressWarnings("unchecked")
	private void prepareOldAwbListTos(List<List> list, AwbTO awbTo, List oldList) {
		oldList.add(awbTo.getAirLine());
		oldList.add(awbTo.getFlightNo());
		oldList.add(String.valueOf(awbTo.getFlightType()));
		oldList.add(String.valueOf(awbTo.getMinTariff()));
		oldList.add(String.valueOf(awbTo.getW1()));
		oldList.add(String.valueOf(awbTo.getW2()));
		oldList.add(String.valueOf(awbTo.getW3()));
		oldList.add(String.valueOf(awbTo.getW4()));
		oldList.add(String.valueOf(awbTo.getW5()));
		oldList.add(String.valueOf(awbTo.getW6()));
		oldList.add(String.valueOf(awbTo.getFuelSurcharge()));
		oldList.add(String.valueOf(awbTo.getOctroiPerBag()));
		oldList.add(String.valueOf(awbTo.getOctroiPerKG()));
		oldList.add(String.valueOf(awbTo.getOriginTSPFlatRate()));
		oldList.add(String.valueOf(awbTo.getOriginTSPPerKGRate()));	
		oldList.add(String.valueOf(awbTo.getDestinationTSPFlatRate()));
		oldList.add(String.valueOf(awbTo.getDestinationTSPPerKGRate()));	
		oldList.add(String.valueOf(awbTo.getAirportHandlingCharges()));
		oldList.add(String.valueOf(awbTo.getxRayCharge()));
		oldList.add(String.valueOf(awbTo.getOriginMinUtilizationCharge()));
		oldList.add(String.valueOf(awbTo.getOriginUtilizationChargesPerKG()));	
		oldList.add(String.valueOf(awbTo.getDestinationMinUtilizationCharge()));
		oldList.add(String.valueOf(awbTo.getDestinationUtilizationChargesPerKG()));	
		oldList.add(String.valueOf(awbTo.getServiceChargeOfAirline()));
		oldList.add(String.valueOf(awbTo.getSurcharge()));
		oldList.add(String.valueOf(awbTo.getAwbId()));
		oldList.add(String.valueOf(awbTo.getDiscountedPercent()));
		oldList.add(String.valueOf(awbTo.getsSPRate()));
		list.add(oldList);
	}

	/**
	 * This method is used to populate Stations drop down values if user select
	 * the Region from region drop down in Coloading Screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingAction::getStations::Start");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			Integer regionId = Integer.parseInt(region);
			List<CityTO> stationList = coloadingService
					.getCitiesByRegionId(regionId);

			if (!CGCollectionUtils.isEmpty(stationList)) {
				jsonResult = serializer.toJSON(stationList).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction :: getStations() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction :: getStations() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction :: getStations() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ColoadingAction::getStations::End");
	}

	/**
	 * This method is used to populate Vendor drop down values if user select
	 * the Region from region drop down in Coloading Screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getVendors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingAction::getVendors::Start");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String region = request.getParameter("region");
			String serviceType = request.getParameter("serviceType");
			Integer regionId = Integer.parseInt(region);
			List<ColoadingVendorTO> coloadingVendorTOs = coloadingService
					.getVendorsList(regionId, serviceType);

			if (!CGCollectionUtils.isEmpty(coloadingVendorTOs)) {
				jsonResult = serializer.toJSON(coloadingVendorTOs).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction :: getVendors() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction :: getVendors() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction :: getVendors() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ColoadingAction::getVendors::End");
	}

	/**
	 * This method is used to store the configured rate into the Data Base for
	 * Air Coloading
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submitAirData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		AirColoadingForm coloadingForm = (AirColoadingForm) form;
		AirColoadingTO coloadingTo = (AirColoadingTO) coloadingForm.getTo();
		
		String efDate = request.getParameter("efDate");
		String vendorName = request.getParameter("vendorName");
		String vendorId = request.getParameter("vendorId");
		String vendorArray[] = vendorName.split("-");
		
		try {
			LOGGER.debug("ColoadingAction:saveAirData:Start");
			int userId = coloadingService.getUserID(request);
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			if (ColoadingConstants.AWB.equals(coloadingTo.getCdType())) {
				populateAwbDataFromPage(request, coloadingTo);
			} else if (ColoadingConstants.CD.equals(coloadingTo.getCdType())) {
				populateCdDateFromPage(request, coloadingTo);
			}
			coloadingTo.setStoreStatus(String
					.valueOf(ColoadingConstants.SUBMIT_CHAR));
			ColoadingAirDO coloadingAirDO = coloadingService.saveColoadingAir(
					coloadingTo, userId);
			if (coloadingAirDO != null) {
				ArrayList<String> flightList = getFlightListForRoute(coloadingTo);
				coloadingTo = ColoadingCommonConverter.convertAirColoadingToFromAirColoadingDO(coloadingAirDO, coloadingTo, flightList);
				actionMessage = new ActionMessage(ColoadingConstants.CL0005);
				Date currentDate = DateUtil.getCurrentDate();
				if (coloadingAirDO.getEffectiveFrom().after(currentDate)) {
					coloadingTo.setIsRenewalAllow(false);
				}
			}
			coloadingAirDO = null;
			loadCommonDataForAir(request, coloadingTo, coloadingForm);
			
			
			// Set following data for displaying data after cd type dropdown value change
			request.setAttribute("effectiveFrom", efDate);
			
			ColoadingVendorTO to = new ColoadingVendorTO();
			List<ColoadingVendorTO> coloadingVendorTOs = new ArrayList<ColoadingVendorTO>();
			to.setVendorId(Integer.parseInt(vendorId));
			to.setVendorCode(vendorArray[0]);
			to.setBusinessName(vendorArray[1]);
			coloadingVendorTOs.add(to);	
			
			request.setAttribute("vendorList", coloadingVendorTOs);			
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::saveAirData ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
			try {
				coloadingTo.setRenewFlag(ColoadingConstants.RENEW_R);
				coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
				loadCommonDataForAir(request, coloadingTo, coloadingForm);
			} catch (CGSystemException | CGBusinessException e1) {
				LOGGER.error("ColoadingAction::saveAirData (CGBusinessException)..Exception :",e1);
			} 
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::saveAirData ..CGSystemException :"
					+ e); // actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::saveAirData ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:saveAirData:End");
		return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
	}

	/**
	 * To load the vendors for selected region
	 * 
	 * @param request
	 * @param regionId
	 * @param serviceType
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void loadVendors(HttpServletRequest request, Integer regionId,
			String serviceType) throws CGBusinessException, CGSystemException {
		LOGGER.debug("ColoadingAction:loadVendors:Start");
		List<ColoadingVendorTO> coloadingVendorTOs = coloadingService
				.getVendorsList(regionId, serviceType);
//		request.setAttribute(ColoadingConstants.VENDOR_LIST, coloadingVendorTOs);
		LOGGER.debug("ColoadingAction:loadVendors:End");
	}

	/**
	 * This method is used to load AirColoadingTo-awbTo from JSP page
	 * 
	 * @param request
	 * @param coloadingTo
	 */
	private void populateAwbDataFromPage(HttpServletRequest request,
			AirColoadingTO coloadingTo) {
		LOGGER.debug("ColoadingAction:populateAwbDataFromPage:Start");
		String[] awbId = request.getParameterValues("awbId");
		if (awbId == null) {
			return;
		}
		String[] airLine = request.getParameterValues("airLine");
		String[] flightNo = request.getParameterValues("flightNo");
		String[] flightType = request.getParameterValues("flightType");
		String[] minTariff = request.getParameterValues("minTariff");
		String[] w1 = request.getParameterValues("w1");
		String[] w2 = request.getParameterValues("w2");
		String[] w3 = request.getParameterValues("w3");
		String[] w4 = request.getParameterValues("w4");
		String[] w5 = request.getParameterValues("w5");
		String[] w6 = request.getParameterValues("w6");
		String[] fuelSurcharge = request.getParameterValues("fuelSurcharge");
		String[] octroiPerBag = request.getParameterValues("octroiPerBag");
		String[] octroiPerKG = request.getParameterValues("octroiPerKG");
		String[] originTSPFlatRate = request
				.getParameterValues("originTSPFlatRate");
		String[] originTSPPerKGRate = request
				.getParameterValues("originTSPPerKGRate");
		String[] destinationTSPFlatRate = request
				.getParameterValues("destinationTSPFlatRate");
		String[] destinationTSPPerKGRate = request
				.getParameterValues("destinationTSPPerKGRate");
		String[] airportHandlingCharges = request
				.getParameterValues("airportHandlingCharges");
		String[] xRayCharge = request.getParameterValues("xRayCharge");
		String[] originMinUtilizationCharge = request
				.getParameterValues("originMinUtilizationCharge");
		String[] originUtilizationChargesPerKG = request
				.getParameterValues("originUtilizationChargesPerKG");
		String[] destinationMinUtilizationCharge = request
				.getParameterValues("destinationMinUtilizationCharge");
		String[] destinationUtilizationChargesPerKG = request
				.getParameterValues("destinationUtilizationChargesPerKG");
		String[] serviceChargeOfAirline = request
				.getParameterValues("serviceChargeOfAirline");
		String[] surcharge = request.getParameterValues("surcharge");
		String[] airWayBill = request.getParameterValues("airWayBill");
		String[] discountedPercent = request
				.getParameterValues("discountedPercent");
		String[] sSPRate = request.getParameterValues("sSPRate");
		String[] storeStatus = request.getParameterValues("storeStatus");
		String[] createdBy = request.getParameterValues("createdBy");
		String[] createdDate = request.getParameterValues("createdDate");
		int length = awbId.length;
		List<AwbTO> awbTos = new ArrayList<>(length);
		AwbTO awbTo = null;
		for (int i = 0; i < length; i++) {
			awbTo = new AwbTO();
			if (!ColoadingConstants.BLANK.equals(awbId[i])) {
				awbTo.setAwbId(Integer.parseInt(awbId[i]));
			}
			awbTo.setAirLine(airLine[i]);
			awbTo.setFlightNo(flightNo[i]);
			awbTo.setFlightType(flightType[i]);
			awbTo.setMinTariff(Double.parseDouble(minTariff[i]));
			awbTo.setW1(Double.parseDouble(w1[i]));
			awbTo.setW2(Double.parseDouble(w2[i]));
			awbTo.setW3(Double.parseDouble(w3[i]));
			awbTo.setW4(Double.parseDouble(w4[i]));
			awbTo.setW5(Double.parseDouble(w5[i]));
			awbTo.setW6(Double.parseDouble(w6[i]));
			awbTo.setFuelSurcharge(Double.parseDouble(fuelSurcharge[i]));
			awbTo.setOctroiPerBag(Double.parseDouble(octroiPerBag[i]));
			awbTo.setOctroiPerKG(Double.parseDouble(octroiPerKG[i]));
			awbTo.setOriginTSPFlatRate(Double.parseDouble(originTSPFlatRate[i]));
			awbTo.setOriginTSPPerKGRate(Double
					.parseDouble(originTSPPerKGRate[i]));
			awbTo.setDestinationTSPFlatRate(Double
					.parseDouble(destinationTSPFlatRate[i]));
			awbTo.setDestinationTSPPerKGRate(Double
					.parseDouble(destinationTSPPerKGRate[i]));
			awbTo.setAirportHandlingCharges(Double
					.parseDouble(airportHandlingCharges[i]));
			awbTo.setxRayCharge(Double.parseDouble(xRayCharge[i]));
			awbTo.setOriginMinUtilizationCharge(Double
					.parseDouble(originMinUtilizationCharge[i]));
			awbTo.setOriginUtilizationChargesPerKG(Double
					.parseDouble(originUtilizationChargesPerKG[i]));
			awbTo.setDestinationMinUtilizationCharge(Double
					.parseDouble(destinationMinUtilizationCharge[i]));
			awbTo.setDestinationUtilizationChargesPerKG(Double
					.parseDouble(destinationUtilizationChargesPerKG[i]));
			awbTo.setServiceChargeOfAirline(Double
					.parseDouble(serviceChargeOfAirline[i]));
			awbTo.setSurcharge(Double.parseDouble(surcharge[i]));
			awbTo.setAirWayBill(Double.parseDouble(airWayBill[i]));
			awbTo.setDiscountedPercent(Double.parseDouble(discountedPercent[i]));
			awbTo.setsSPRate(Double.parseDouble(sSPRate[i]));
			awbTo.setStoreStatus(storeStatus[i].charAt(0));
			awbTo.setCreatedBy(Integer.parseInt(createdBy[i]));
			awbTo.setCreatedDate(createdDate[i]);
			awbTos.add(awbTo);
		}
		coloadingTo.setAwbToList(awbTos);
		LOGGER.debug("ColoadingAction:populateAwbDataFromPage:End");
	}

	/**
	 * This method is used to load AirColoadingTo-cdTo from JSP page
	 * 
	 * @param request
	 * @param coloadingTo
	 */
	private void populateCdDateFromPage(HttpServletRequest request,
			AirColoadingTO coloadingTo) {
		LOGGER.debug("ColoadingAction:populateCdDateFromPage:Start");
		String[] cdId = request.getParameterValues("cdId");
		if (cdId == null) {
			return;
		}
		String[] airLine = request.getParameterValues("airLine");
		String[] flightNo = request.getParameterValues("flightNo");
		String[] billingRate = request.getParameterValues("billingRate");
		String[] fuelSurcharge = request.getParameterValues("fuelSurcharge");
		String[] octroiPerBag = request.getParameterValues("octroiPerBag");
		String[] octroiPerKG = request.getParameterValues("octroiPerKG");
		String[] surcharge = request.getParameterValues("surcharge");
		String[] otherCharges = request.getParameterValues("otherCharges");
		String[] cd = request.getParameterValues("cd");
		String[] sSPRate = request.getParameterValues("sSPRate");
		String[] storeStatus = request.getParameterValues("storeStatus");
		String[] createdBy = request.getParameterValues("createdBy");
		String[] createdDate = request.getParameterValues("createdDate");
		int length = cdId.length;
		List<CdTO> cdTos = new ArrayList<>(length);
		CdTO cdTo = null;
		for (int i = 0; i < length; i++) {
			cdTo = new CdTO();
			if (!ColoadingConstants.BLANK.equals(cdId[i])) {
				cdTo.setCdId(Integer.parseInt(cdId[i]));
			}
			cdTo.setAirLine(airLine[i]);
			cdTo.setFlightNo(flightNo[i]);
			cdTo.setBillingRate(Double.parseDouble(billingRate[i]));
			cdTo.setFuelSurcharge(Double.parseDouble(fuelSurcharge[i]));
			cdTo.setOctroiPerBag(Double.parseDouble(octroiPerBag[i]));
			cdTo.setOctroiPerKG(Double.parseDouble(octroiPerKG[i]));
			cdTo.setSurcharge(Double.parseDouble(surcharge[i]));
			cdTo.setOtherCharges(Double.parseDouble(otherCharges[i]));
			cdTo.setCd(Double.parseDouble(cd[i]));
			cdTo.setsSPRate(Double.parseDouble(sSPRate[i]));
			cdTo.setStoreStatus(storeStatus[i].charAt(0));
			cdTo.setCreatedBy(Integer.parseInt(createdBy[i]));
			cdTo.setCreatedDate(createdDate[i]);
			cdTos.add(cdTo);
		}
		coloadingTo.setCdToList(cdTos);
		LOGGER.debug("ColoadingAction:populateCdDateFromPage:End");
	}

	/**
	 * This method is used to load TrainColoadingTo-TrainDetailsTo from JSP page
	 * 
	 * @param request
	 * @param coloadingTo
	 */
	private void populateTrainDetailsFromPage(HttpServletRequest request,
			TrainColoadingTO coloadingTo) {
		LOGGER.debug("ColoadingAction:populateTrainDetailsFromPage:Start");
		String[] trainId = request.getParameterValues("trainId");
		if (trainId == null) {
			return;
		}
		String[] trainNo = request.getParameterValues("trainNo");
		String[] minChargeableRate = request
				.getParameterValues("minChargeableRate");
		String[] ratePerKG = request.getParameterValues("ratePerKG");
		String[] otherChargesPerKG = request
				.getParameterValues("otherChargesPerKG");
		String[] storeStatus = request.getParameterValues("storeStatus");
		String[] createdBy = request.getParameterValues("createdBy");
		String[] createdDate = request.getParameterValues("createdDate");
		int length = trainId.length;
		List<TrainDetailsTO> trainDetailsTos = new ArrayList<>(length);
		TrainDetailsTO trainDetailsTo = null;
		for (int i = 0; i < length; i++) {
			trainDetailsTo = new TrainDetailsTO();
			if (!ColoadingConstants.BLANK.equals(trainId[i])) {
				trainDetailsTo.setId(Integer.parseInt(trainId[i]));
			}
			trainDetailsTo.setTrainNo(trainNo[i]);
			trainDetailsTo.setMinChargeableRate(Double
					.parseDouble(minChargeableRate[i]));
			trainDetailsTo.setRatePerKG(Double.parseDouble(ratePerKG[i]));
			trainDetailsTo.setOtherChargesPerKG(Double
					.parseDouble(otherChargesPerKG[i]));
			trainDetailsTo.setStoreStatus(storeStatus[i].charAt(0));
			if (!ColoadingConstants.BLANK.equals(createdBy[i])) {
				trainDetailsTo.setCreatedBy(Integer.parseInt(createdBy[i]));
				trainDetailsTo.setCreatedDate(createdDate[i]);
			}
			trainDetailsTos.add(trainDetailsTo);
		}
		coloadingTo.setTrainDetailsList(trainDetailsTos);
		LOGGER.debug("ColoadingAction:populateTrainDetailsFromPage:End");
	}

	/**
	 * This method is used save the Configured Rate into the data base if user
	 * clicks on Save button on Train Coloading screen.Saved data can be
	 * submited later on
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveTrainData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:saveAirData:Start");
			TrainColoadingForm coloadingForm = (TrainColoadingForm) form;
			TrainColoadingTO coloadingTo = (TrainColoadingTO) coloadingForm
					.getTo();
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			int userId = coloadingService.getUserID(request);
			
			String efDate = request.getParameter("efDate");
			String[] vendorList = request.getParameterValues("vendorList");
			
			setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);			
			
			populateTrainDetailsFromPage(request, coloadingTo);
			coloadingTo.setStoreStatus(ColoadingConstants.SAVE_STRING_T);
			ColoadingTrainContractDO coloadingTrainContractDO = coloadingService
					.saveColoadingTrain(coloadingTo, userId);
			if (coloadingTrainContractDO != null) {
				coloadingTo = ColoadingCommonConverter
						.convertTrainToFromTrainDo(coloadingTo,
								coloadingTrainContractDO);
				actionMessage = new ActionMessage(ColoadingConstants.CL0003);
			}
			coloadingTrainContractDO = null;
			loadCommonDataForTrain(request, coloadingTo, coloadingForm);
			coloadingTo.setRenewFlag(ColoadingConstants.SAVE_STRING_T);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::saveTrainData ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::saveTrainData ..CGSystemException :"
					+ e); // actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::saveTrainData ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:saveTrainData:End");
		return mapping.findForward(ColoadingConstants.TRAIN_COLOADING_SUCCESS);
	}

	/**
	 * This method is used Renew the Configured Rate into the data base if user
	 * clicks on Renew Button and then click on Save Button on Train Coloading
	 * screen.Saved data can be Submited later on
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward renewTrain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:renewTrain:Start");
			TrainColoadingForm coloadingForm = (TrainColoadingForm) form;
			TrainColoadingTO coloadingTo = (TrainColoadingTO) coloadingForm
					.getTo();
			
			String efDate = request.getParameter("efDate");
			String[] vendorList = request.getParameterValues("vendorList");	
		
			setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);	
			
			ColoadingTrainContractDO coloadingTrainContractDO = coloadingService
					.getTrainFutureData(coloadingTo);
			if (coloadingTrainContractDO != null) {
				coloadingTo = ColoadingCommonConverter
						.convertTrainToFromTrainDo(coloadingTo,
								coloadingTrainContractDO);
			} else {
				populateTrainDetailsFromPage(request, coloadingTo);
				coloadingTo.setEffectiveFrom(DateUtil
						.getDDMMYYYYDateToString(DateUtil.getFutureDate(1)));
			}
			coloadingTo.setRenewFlag(ColoadingConstants.RENEW_R);
			coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
			loadCommonDataForTrain(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::renewTrain ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::renewTrain ..CGSystemException :"
					+ e); // actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::renewTrain ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:renewTrain:End");
		return mapping.findForward(ColoadingConstants.TRAIN_COLOADING_SUCCESS);
	}

	/**
	 * This method is used Renew the Configured Rate into the data base if user
	 * clicks on Renew Button and then click on Submit Button on Air Coloading
	 * screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward renewAir(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:renewAir:Start");
			
			String efDate = request.getParameter("efDate");
			String vendorName = request.getParameter("vendorName");
			String vendorId = request.getParameter("vendorId");
			String vendorArray[] = vendorName.split("-");			
			
			
			AirColoadingForm coloadingForm = (AirColoadingForm) form;
			AirColoadingTO coloadingTo = (AirColoadingTO) coloadingForm.getTo();
			ColoadingAirDO coloadingAirDO = coloadingService
					.getAirFutureData(coloadingTo);
			if (coloadingAirDO != null) {
				ArrayList<String> flightList = getFlightListForRoute(coloadingTo);
				coloadingTo = ColoadingCommonConverter.convertAirColoadingToFromAirColoadingDO(coloadingAirDO, coloadingTo, flightList);
			}
			coloadingTo.setRenewFlag(ColoadingConstants.RENEW_R);
			coloadingTo.setStoreStatus(ColoadingConstants.RENEW_R);
			
			loadCommonDataForAir(request, coloadingTo, coloadingForm);
			
			// Set following data for displaying data after cd type dropdown value change
			request.setAttribute("effectiveFrom", efDate);
			
			ColoadingVendorTO to = new ColoadingVendorTO();
			List<ColoadingVendorTO> coloadingVendorTOs = new ArrayList<ColoadingVendorTO>();
			to.setVendorId(Integer.parseInt(vendorId));
			to.setVendorCode(vendorArray[0]);
			to.setBusinessName(vendorArray[1]);
			coloadingVendorTOs.add(to);	
			
			request.setAttribute("vendorList", coloadingVendorTOs);			
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::renewAir ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::renewAir ..CGSystemException :" + e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::renewAir ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:renewAir:End");
		return mapping.findForward(ColoadingConstants.AIR_COLOADING_SUCCESS);
	}

	/**
	 * This method is used Submit the Configured Rate into the data base if user
	 * clicks on Submit button on Train Coloading screen.Submited data can not
	 * be changed
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submutTrainData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionMessage actionMessage = null;
		try {
			LOGGER.debug("ColoadingAction:submutTrainData:Start");
			TrainColoadingForm coloadingForm = (TrainColoadingForm) form;
			TrainColoadingTO coloadingTo = (TrainColoadingTO) coloadingForm
					.getTo();
			
			String efDate = request.getParameter("efDate");
			String[] vendorList = request.getParameterValues("vendorList");
			
			setEffectiveFromDateAndVendorInfo(efDate, vendorList, request);	
			
			coloadingTo.setRenewFlag(request
					.getParameter(ColoadingConstants.RENEW_R));
			int userId = coloadingService.getUserID(request);
			populateTrainDetailsFromPage(request, coloadingTo);
			coloadingTo.setStoreStatus(ColoadingConstants.SUBMIT_P);
			ColoadingTrainContractDO coloadingTrainContractDO = coloadingService
					.saveColoadingTrain(coloadingTo, userId);
			if (coloadingTrainContractDO != null) {
				actionMessage = new ActionMessage(ColoadingConstants.CL0005);
				Date currentDate = DateUtil.getCurrentDate();
				if (coloadingTrainContractDO.getEffectiveFrom().after(
						currentDate)) {
					coloadingTo.setIsRenewalAllow(false);
				}
			}
			coloadingTrainContractDO = null;
			loadCommonDataForTrain(request, coloadingTo, coloadingForm);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingAction::submutTrainData ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingAction::submutTrainData ..CGSystemException :"
					+ e);
			actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingAction::submutTrainData ..Exception :" + e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingAction:submutTrainData:End");
		return mapping.findForward(ColoadingConstants.TRAIN_COLOADING_SUCCESS);
	}

	public void loadErrorListFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingAction:loadErrorListFile:End");
		String fileName = request.getParameter("fileName");
		List<List> errorList = (List<List>) request.getSession().getAttribute(
				"errorList");
		XSSFWorkbook xssfWorkbook;
		try {
			xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errorList);
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\" ");
			xssfWorkbook.write(response.getOutputStream());
			request.getSession().setAttribute("errorList",null);
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in ColoadingAction :: loadErrorListFile() ::",
					e);
		}
		LOGGER.debug("ColoadingAction:loadErrorListFile:End");
	}

}
