package com.ff.admin.billing.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.form.BulkCustModificationForm;
import com.ff.admin.billing.service.BulkCustModificationService;
import com.ff.admin.billing.service.CnModificationCommonService;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationService;
import com.ff.rate.calculation.service.RateCalculationServiceFactory;
import com.ff.to.billing.BulkCustModificationTO;
import com.ff.to.billing.CustModificationAliasTO;
import com.ff.to.billing.CustModificationTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;

public class BulkCustModificationAction extends AbstractBillingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BulkCustModificationAction.class);
	private BulkCustModificationService bulkCustModificationService;
	private CnModificationCommonService cnModificationCommonService;
	private GlobalUniversalService globalUniversalService;
	
	public BulkCustModificationService getBulkCustModificationService(){
		if(StringUtil.isNull(bulkCustModificationService))
			bulkCustModificationService = (BulkCustModificationService)getBean(AdminSpringConstants.BULK_CUST_MODIFICATION_SERVICE);
		return bulkCustModificationService;
		
	}
	public CnModificationCommonService getCnModificationCommonService(){
		if(StringUtil.isNull(cnModificationCommonService))
			cnModificationCommonService = (CnModificationCommonService)getBean(AdminSpringConstants.CN_MODIFICATION_COMMON_SERVICE);
		return cnModificationCommonService;
	}
	public GlobalUniversalService getGlobalUniversalService(){
		if(StringUtil.isNull(globalUniversalService))
			globalUniversalService = (GlobalUniversalService) getBean("globalUniversalService");
		return globalUniversalService;
	}

	public ActionForward preparePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("BulkCustModificationAction::preparePage::START----->");
		String url =  null;
		try {
			String loginOfficeTypeCode = getLoggedInOfficeType(request);
			/*if(StringUtils.equalsIgnoreCase(loginOfficeTypeCode, CommonConstants.OFF_TYPE_CORP_OFFICE)
					|| StringUtils.equalsIgnoreCase(loginOfficeTypeCode, CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)){*/
				if(StringUtils.equalsIgnoreCase(loginOfficeTypeCode, CommonConstants.OFF_TYPE_CORP_OFFICE)){
					setRegion(request);
				}else{
					final HttpSession session = (HttpSession) request.getSession(false);
					final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
					final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
					List<RegionTO> regionTOs = new ArrayList<>();
					regionTOs.add(0, loggedInOfficeTO.getRegionTO());
					request.setAttribute(BillingConstants.REGION_TO,regionTOs);
					
					List<CityTO> cityTOs = getBulkCustModificationService().getCitysByStateId(loggedInOfficeTO.getRegionTO().getRegionId());
					request.setAttribute("cityTO", cityTOs);
				}
				BulkCustModificationTO bulkCustModificationTO = new BulkCustModificationTO();
				bulkCustModificationTO.setCurrentDate(DateUtil.todaySystemDate());
				((BulkCustModificationForm) form).setTo(bulkCustModificationTO);
				String backBateToBook = getGlobalUniversalService().getConfigParamValueByName(CommonConstants.CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED);
				
				request.setAttribute(CommonConstants.CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED, backBateToBook);
				url =  "success";
			/*}else{
				url = UmcConstants.WELCOME;
				ActionMessage actionMessage = new ActionMessage(BillingConstants.CN_MODIFICATION_ALLOWED_AT_CORPORATE_RHO_OFFICE);
				prepareActionMessage(request, actionMessage);
			}		*/	
		}catch (CGBusinessException e) {
			LOGGER.error("BulkCustModificationAction::preparePage ..CGBusinessException :" + e);
		} catch (CGSystemException e) {
			LOGGER.error("BulkCustModificationAction::preparePage ..CGSystemException :" + e);
		}

		LOGGER.debug("BulkCustModificationAction::preparePage()::END");
		return mapping.findForward(url);
	}
	
	private void setRegion(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {

		LOGGER.debug("BulkCustModificationAction::setRegion::START------------>:::::::");
		List<RegionTO> regionTo = getBulkCustModificationService().getRegions();
		if (!CGCollectionUtils.isEmpty(regionTo)) {
			request.setAttribute(BillingConstants.REGION_TO, regionTo);
		} else {
			prepareActionMessage(request, new ActionMessage(BillingConstants.REGION_DTLS_NOT_EXIST));
			LOGGER.warn("BulkCustModificationAction:: setRegion :: Region Details Does not exist");
		}
	}
	
	public void getCitysByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BulkCustModificationAction::getCitysByRegion::START----->");
		String region = request.getParameter("regionId");
		Integer stateId = Integer.parseInt(region);
		String cityJson = null;
		PrintWriter out = null;
		try {
			List<CityTO> cityTO;
			cityTO = getBulkCustModificationService().getCitysByStateId(stateId);
			request.setAttribute("cityTO", cityTO);
			out = response.getWriter();
			if (cityTO != null)
				cityJson = JSONSerializer.toJSON(cityTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("BulkCustModificationAction::getCitysByRegion::Exception :", e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("BulkCustModificationAction::getCitysByRegion::Exception :"+ e.getMessage());
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("BulkCustModificationAction::getCitysByRegion::Exception :"+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			cityJson = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(cityJson);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkCustModificationAction::getCitysByRegion::END----->");
	}
	
	public void saveOrUpdateBulkCustModification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("CustModificationAction::saveOrUpdateBulkCustModification::START----->Start Time::" + startTimeInMilis);
		PrintWriter out = null;
		List<String> consgNoList = null;		
		String cnValidationJSON = CommonConstants.SUCCESS;	
		try {
			CustModificationTO  custModificationTO = null;
			out = response.getWriter();
			BulkCustModificationForm bulkCustModificationForm = (BulkCustModificationForm) form;
			BulkCustModificationTO bulkTO = (BulkCustModificationTO) bulkCustModificationForm.getTo();			
			
			String consignMode = bulkTO.getCnModSelMode();
			custModificationTO = (CustModificationTO)bulkTO;
			custModificationTO.setScreenName("BULK");
			//This is for if customer selection mode as Multiple 
			if (!StringUtil.isNull(consignMode) && consignMode.equals(BillingConstants.MULTIPLE_TYPE_CN_SELECTION)){
				validConsignmentNoFormat(bulkTO, request);
				consgNoList = bulkTO.getValidConsgList();
			}else{				
				consgNoList = prepareConsignmentSetBySeries(bulkTO);
				bulkTO.setValidConsgList(consgNoList);
				//validate customer code and product code				
				custModificationTO.setCongNo(bulkTO.getStartConsgNo());
				validateProductForContract(custModificationTO);
			}
			Set<String> consgNoSet = new HashSet<String>(consgNoList);
			if (!StringUtil.isEmptyColletion(consgNoSet)){
				if(consgNoSet.size() <= 100) {				
					for (String consgNo : consgNoSet) {											
						try{
							if(StringUtils.isNotEmpty(consgNo)){
								ConsignmentTO consignmentTO = prepareConsignmentTO(request, bulkTO);
								consignmentTO.setConsgNo(consgNo);
								if(!StringUtil.isNull(consignmentTO.getCustomerTO()) && !StringUtil.isNull(consignmentTO.getCustomerTO().getShippedToCode())){
									CustModificationAliasTO custModificationAliasTO = getCnModificationCommonService().validateConsignment2Modify(consgNo);
									custModificationTO.setIsCustEditable(custModificationAliasTO.getIsCustEditable());
									getCnModificationCommonService().saveOrUpdateConsignmentModification(consignmentTO, custModificationTO);
								}else{
									throw new CGBusinessException(AdminErrorConstants.NO_SHIPPEDTO_CODE);
								}
							}
						}catch(CGBusinessException e){
							handleExceptionMessage(bulkTO, consgNo, getBusinessErrorFromWrapper(request, e));
							continue;
						}catch(Exception e){
							handleExceptionMessage(bulkTO, consgNo, getGenericExceptionMessage(request, e));
							continue;
						}
					}
				}else{
					throw new CGBusinessException(AdminErrorConstants.CUST_CONSIGN_EXCEED_LIMIT);
				}
			}
			Set<String> inputCnSet = new HashSet<String>();
			inputCnSet.addAll(bulkTO.getValidConsgList());
			inputCnSet.addAll(bulkTO.getInValidConsgList());
			
			request.getSession().removeAttribute(BillingConstants.LIST_OF_FAILED_CNS);
			request.getSession().setAttribute(BillingConstants.LIST_OF_FAILED_CNS, bulkTO.getInValidConsgWithErrorDesc());
			
			if(StringUtil.isEmptyColletion(bulkTO.getInValidConsgList())){
				cnValidationJSON = prepareCommonException(FrameworkConstants.SUCCESS_FLAG, getMessageFromErrorBundle(request,
						BillingConstants.ALL_CONSIGNMENTS_MODIFIED_SUCCESSFULLY, null));
			}else if(StringUtil.isEmptyColletion(bulkTO.getValidConsgList()) || inputCnSet.size() == bulkTO.getInValidConsgList().size()){
				cnValidationJSON = prepareCommonException(BillingConstants.PARTIAL_SUCCESS_FLAG, getMessageFromErrorBundle(request,
						BillingConstants.ALL_BULK_CN_MODICATION_FAILED, null));
			} else {
				cnValidationJSON = prepareCommonException(BillingConstants.PARTIAL_SUCCESS_FLAG, getMessageFromErrorBundle(request,
						BillingConstants.PARTIALLY_BULK_CN_MODICATION_SAVED, null));
			}			
		}catch (CGBusinessException e) {
			LOGGER.error("BulkCustModificationAction :: saveOrUpdateBulkCustModification() ::"+e);
			cnValidationJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		} catch(Exception e){
			LOGGER.error("BulkCustModificationAction :: saveOrUpdateBulkCustModification() ::"+e);
			String exception = getGenericExceptionMessage(request,e);
			cnValidationJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		LOGGER.debug("BulkCustModificationAction::saveOrUpdateBulkCustModification::END----->:: End Time : " +
				+ endTimeInMilis
				+" :: Time Diff in miliseconds :: "+(diff)
				+" ::Time Diff in HH:MM:SS :: "
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}
	
	private List<String> prepareConsignmentSetBySeries(BulkCustModificationTO bulkTO)
			throws CGBusinessException, CGSystemException {
		List<String> consgNoSet = null;
		String startCNDigits = null;
		String endCNDigits = null;
		String startCnTemp = null;
		String endCnTemp = null;
		
		String startCnNumber = bulkTO.getStartConsgNo();
		String endCnNumber = bulkTO.getEndConsgNo();
				
		if(!StringUtil.isNull(startCnNumber)){
			startCNDigits = startCnNumber.substring(7, 12);
			startCnTemp = startCnNumber.substring(0, 5);
		}
		if(!StringUtil.isNull(endCnNumber)){
			endCNDigits = endCnNumber.substring(7, 12);
			endCnTemp = endCnNumber.substring(0, 5);
		}
		if(!startCnTemp.equals(endCnTemp)){
			throw new CGBusinessException(BillingConstants.CONSG_SEIRES_NOT_MATCH);
		}
		Integer startValue = Integer.parseInt(startCNDigits);
		Integer endValue = Integer.parseInt(endCNDigits);
		Integer quantity = endValue - startValue;
		if(quantity == 0){
			throw new CGBusinessException(BillingConstants.NO_CONSG_DETAILS_FOUND);
		}else if(quantity < 0){
			throw new CGBusinessException(BillingConstants.CHECK_CONSG_RANGE);
		}else{
			quantity++;
			consgNoSet = seriesConverter(consgNoSet, startCnNumber, quantity);
		}
		return consgNoSet;
	}
	
	private void validateProductForContract(
			CustModificationTO custModificationTO) throws CGBusinessException,
			CGSystemException {
		boolean isValidProduct = Boolean.FALSE;
		ProductToBeValidatedInputTO productToBeValidatedInputTO = getCnModificationCommonService().setProductContractInputTO(custModificationTO);
		RateCalculationServiceFactory serviceFactory = (RateCalculationServiceFactory) getBean("rateCalcFactory");
		RateCalculationService rateService = serviceFactory .getService(productToBeValidatedInputTO.getRateType());
		isValidProduct = rateService.isProductValidForContract(productToBeValidatedInputTO);
		if(!isValidProduct){
			throw new CGBusinessException(BillingConstants.PROD_NOT_CONTRACT_BY_CUST);
		}
	}
	
	private ConsignmentTO prepareConsignmentTO(HttpServletRequest request, BulkCustModificationTO bulkTO) {
		ConsignmentTO consignmentTO=new ConsignmentTO();		
		Date bookingDate = DateUtil.getTimeStampFromDateSlashFormatString(bulkTO.getExBookingDate());
		consignmentTO.setBookingDate(bookingDate);				
	
		consignmentTO.setOrgOffId(bulkTO.getExOffice());
		consignmentTO.setOperatingOffice(bulkTO.getExOffice());
		
		CustomerTO newCustomerTO = bulkTO.getNewCustTO();		
		if(!StringUtil.isNull(newCustomerTO)){
			consignmentTO.setCustomerTO(newCustomerTO);
		}
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		if(!StringUtil.isEmptyInteger(userInfoTO.getUserto().getUserId())){
			consignmentTO.setCreatedBy(userInfoTO.getUserto().getUserId());
		}
		consignmentTO.setCreatedDate(Calendar.getInstance().getTime());
		
		return consignmentTO;		
	}
	
	private void handleExceptionMessage(BulkCustModificationTO to, String consgNumber, String errorMsg) {
		List<List<String>> inValidConsgWithErrorDescList = to.getInValidConsgWithErrorDesc();
		
		int invalidCnCnt = inValidConsgWithErrorDescList.size();
		List<String> inValidConsgWithErrorDesc = new ArrayList<String>();
		inValidConsgWithErrorDesc.add(String.valueOf(invalidCnCnt+1));
		inValidConsgWithErrorDesc.add(consgNumber);
		inValidConsgWithErrorDesc.add(errorMsg);
		
		inValidConsgWithErrorDescList.add(inValidConsgWithErrorDesc);
		
		List<String> inValidConsgList = to.getInValidConsgList();
		inValidConsgList.add(consgNumber);
	}
	
	private List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException {
		try {
			LOGGER.debug("BulkCustModificationAction::seriesConverter::START------------>:::::::");
			seriesList = StockSeriesGenerator.globalSeriesCalculater(consgNumber, quantity);
		} catch (Exception e) {
			throw new CGBusinessException(UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		LOGGER.debug("BulkCustModificationAction::seriesConverter::End------------>:::::::");
		return seriesList;
	}

	/**
	 * Consignment Format Validation
	 * @param consgNum
	 * @param bulkTO
	 * @return
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	public BulkCustModificationTO validConsignmentNoFormat(BulkCustModificationTO bulkTO, HttpServletRequest request) throws CGBusinessException, CGSystemException {
		LOGGER.trace("BulkCustModificationAction::validConsignmentNoFormat()::START");
		List<String> validConsgList = new ArrayList<>();

		String consgNoStr = StringUtil.trimAllWhitespace(bulkTO.getConsgNumbers());
		List<String> consgNoList = StringUtil.parseStringList(consgNoStr, CommonConstants.COMMA);
		
		String numpattern = ".*[0-9].*";
		String alphaNumeric = "[a-zA-Z0-9]*$";
		for (String num : consgNoList) {
			try{
				if(StringUtils.isEmpty(num)){
					continue;
				}
				num = StringUtil.trimWhitespace(num);
				int consgSize = num.length();
				Pattern pattern1 = Pattern.compile(alphaNumeric);
				Pattern pattern2 = Pattern.compile(numpattern);
				Matcher matcher1 = null;
				Matcher matcher2 = null;
				if(consgSize >= 5){
					matcher1 = pattern1.matcher(num.substring(0, 5));
					matcher2 = pattern2.matcher(num.substring(5));
				}

				if (consgSize == 12 && matcher1.matches() && matcher2.matches()) {
					CustModificationTO  custModificationTO = (CustModificationTO)bulkTO;
					custModificationTO.setCongNo(num);
					validateProductForContract(custModificationTO);
					
					validConsgList.add(num);				
				} else {
					throw new CGBusinessException(AdminErrorConstants.INVALID_CONSIGNMENT_FORMAT);
				}
			}catch(CGBusinessException e){
				handleExceptionMessage(bulkTO, num, getBusinessErrorFromWrapper(request, e));
				continue;
			}catch (Exception e) {
				handleExceptionMessage(bulkTO, num, getGenericExceptionMessage(request, e));
				continue;
			}
		}
		if(StringUtil.isEmptyColletion(validConsgList) && StringUtil.isEmptyColletion(bulkTO.getInValidConsgWithErrorDesc())){
			throw new CGBusinessException(BillingConstants.PLEASE_ENTER_ATLEAST_ONE_CONSIGNMENT);		
		}else{
			bulkTO.setValidConsgList(validConsgList);
		}	

		LOGGER.trace("BulkCustModificationAction::validConsignmentNoFormat()::END");
		return bulkTO;
	}

	public void getErrorCnList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BulkCustModificationAction::getErrorCnList::START------------>:::::::");
		String fileName = request.getParameter("fileName");
		List<List> errorList = (List<List>) request.getSession().getAttribute("ListOfErrorCns");
		XSSFWorkbook xssfWorkbook;
		try {
			if(!StringUtil.isEmptyColletion(errorList)){
				addHeader(errorList);
				xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errorList);
				response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\" ");
				xssfWorkbook.write(response.getOutputStream());
			}			
		} catch (Exception e) {
			LOGGER.error("BulkCustModificationAction :: getErrorCnList() ::", e);
		}
		LOGGER.debug("BulkCustModificationAction::getErrorCnList::END------------>:::::::"+ DateUtil.getCurrentTimeInMilliSeconds());
	}
	private void addHeader(List<List> errorList) {
		List<String> inValidConsgWithErrorDesc = new ArrayList<String>();
		inValidConsgWithErrorDesc.add("S.No");
		inValidConsgWithErrorDesc.add("Consignment No's");
		inValidConsgWithErrorDesc.add("Reason");
		errorList.add(0, inValidConsgWithErrorDesc);
	}
}
