package com.ff.admin.billing.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.dao.CnModificationCommonDAO;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingModifiedDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentModifiedDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.CustModificationAliasTO;
import com.ff.to.billing.CustModificationTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.ProductToBeValidatedInputTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * @author shahnsha
 *
 */
public class CnModificationCommonServiceImpl implements CnModificationCommonService {
	private final static Logger LOGGER = LoggerFactory.getLogger(CnModificationCommonServiceImpl.class);
	
	private GeographyCommonService geographyCommonService;
	private CnModificationCommonDAO cnModificationCommonDAO;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private transient RateCalculationUniversalService rateCalculationUniversalService;
	private GlobalUniversalService globalUniversalService;
	
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}
	
	public void setCnModificationCommonDAO(
			CnModificationCommonDAO cnModificationCommonDAO) {
		this.cnModificationCommonDAO = cnModificationCommonDAO;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.CnModificationCommonService#setProductContractInputTO(com.ff.consignment.ConsignmentTO, java.lang.Integer)
	 */
	@Override
	public ProductToBeValidatedInputTO setProductContractInputTO(CustModificationTO custModificationTO)
			throws CGBusinessException, CGSystemException {
		String productCode=null;
		String consgSeries=null;
		String loginCityCode=null;
		String newCustCode = null;
		ProductToBeValidatedInputTO productToBeValidatedInputTO=null;
		loginCityCode = getCityCodeByCityId(custModificationTO.getCityId());
		
		if (StringUtils.isNotEmpty(custModificationTO.getCongNo())) {
			if(!StringUtil.isNull(custModificationTO.getNewCustTO())){
				newCustCode = custModificationTO.getNewCustTO().getCustomerCode();
			}
			consgSeries = custModificationTO.getCongNo().substring(4,5);
			if(StringUtils.isNumeric(consgSeries)){
				consgSeries=CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
			}
			ProductTO productTO = getProductByConsgSeries(consgSeries);
			productCode = productTO.getProductCode();			
			String rateType = getRateType(newCustCode, custModificationTO.getCongNo());

			//Prepare input TO
			productToBeValidatedInputTO = new ProductToBeValidatedInputTO();
			productToBeValidatedInputTO.setProductCode(productCode);
			productToBeValidatedInputTO.setCustomerCode(newCustCode);
			productToBeValidatedInputTO.setOriginCityCode(loginCityCode);
			productToBeValidatedInputTO.setRateType(rateType);	
			productToBeValidatedInputTO.setCalculationRequestDate(custModificationTO.getExBookingDate());	
		}
		return productToBeValidatedInputTO;
	}
	
	private String getCityCodeByCityId(Integer cityId)throws CGSystemException,
	CGBusinessException{
		String loginCityCode = null;
		CityTO cityTO = new CityTO();
		cityTO.setCityId(cityId);
		cityTO = geographyCommonService.getCity(cityTO);
		if (!StringUtil.isNull(cityTO)) {
			loginCityCode = cityTO.getCityCode();
		}
		return loginCityCode;
	}
	
	public String getRateType(String newCustCode, String consignmentNo)
			throws CGBusinessException,CGSystemException {
		String consgSeries = consignmentNo.substring(4,5);
		if(StringUtils.isNumeric(consgSeries)){
			consgSeries=CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
		}
		String custType = cnModificationCommonDAO.getBookingTypeByCustcode(newCustCode);
		String rateType=null;
		switch(custType){
		case CommonConstants.CUSTOMER_CODE_BA_PICKUP:
			rateType = CommonConstants.CUSTOMER_CODE_BA_PICKUP;  // "BA"
			break;
		case CommonConstants.CUSTOMER_CODE_BA_DELIVERY:
			rateType = CommonConstants.CUSTOMER_CODE_BA_PICKUP;
			break;
		case CommonConstants.CUSTOMER_CODE_ACC:
			List<String> accAllowedCnSeries = new ArrayList<String>();
			accAllowedCnSeries = Arrays.asList("A", "S","N", "P");
			if(!accAllowedCnSeries.contains(consgSeries)){
				throw new CGBusinessException(BillingConstants.ACC_CUSTOMERS_NOT_ALLOWED_SERIES);
			}
			rateType="CH";
			break;
		case CommonConstants.CUSTOMER_CODE_FRANCHISEE:
			rateType = CommonConstants.CUSTOMER_CODE_CREDIT_CARD;
			break;
		default:
			rateType = CommonConstants.CUSTOMER_CODE_CREDIT_CARD;
			break;
		}
		return rateType;
	}
	
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getProductByConsgSeries(consgSeries);
	}
	
	@Override
	public CustModificationAliasTO validateConsignment2Modify(String consgNo) throws CGSystemException, CGBusinessException {
		CustModificationAliasTO custModificationAliasTO = cnModificationCommonDAO.getCnModificationValidationDetails(consgNo);
		if (!StringUtil.isNull(custModificationAliasTO)) {
			String cnSeries = consgNo.substring(4, 5);
			checkBackdatedConsignment(custModificationAliasTO.getBkgDate());
			if(!StringUtil.isNull(custModificationAliasTO.getBillingStatus()) 
					&& custModificationAliasTO.getBillingStatus().equals(CommonConstants.BILLING_STATUS_BLD)){
				throw new CGBusinessException(AdminErrorConstants.CN_BILLED_FORMODIFICATION);
			}
			if (!StringUtils.equalsIgnoreCase(custModificationAliasTO.getIsExcessConsg(), CommonConstants.YES)
					&& custModificationAliasTO.getBookDetails().equals(CommonConstants.NO)) {
				throw new CGBusinessException(AdminErrorConstants.NO_BOOK_DETAILS_FORMODIFICATION);
			}
			if (custModificationAliasTO.getBookDetails().equals(BillingConstants.NO_PRODUCT_SERIES)) {
				throw new CGBusinessException(AdminErrorConstants.NO_CONSG_DETAILS_ON_PRODUCT_SERIES);
			}
			if(StringUtils.equalsIgnoreCase(custModificationAliasTO.getIsExcessConsg(), CommonConstants.YES)
					&& cnSeries.equals(CommonConstants.PRODUCT_SERIES_PRIORITY)){
				throw new CGBusinessException(BillingConstants.CN_MODIFICATION_NOT_ALLOWED_EXCESS_PRIORITY_PRODUCTS);
			}
			if (!StringUtils.equalsIgnoreCase(custModificationAliasTO.getIsExcessConsg(), CommonConstants.YES)
					&& custModificationAliasTO.getCustCheck().equals(CommonConstants.NO)) {
				throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FORMODIFICATION);
			}
			if (custModificationAliasTO.getExpenseCheck().equals(CommonConstants.YES)) {
				throw new CGBusinessException(AdminErrorConstants.EXPENSE_CREATED_FORCN);
			}
			if (custModificationAliasTO.getLiabilityCheck().equals(CommonConstants.YES)) {
				throw new CGBusinessException(AdminErrorConstants.LIABILITY_CREATED_FORCN);
			}
			if (custModificationAliasTO.getLiabilitySapCheck().equals(CommonConstants.YES)) {
				throw new CGBusinessException(AdminErrorConstants.SAP_LIABILITY_ENTRY_CONSUMED);
			}
			//For L, D and T series consignments if Delivery and collection is happened means customer modification is not allowed but weight modification is allowed. 
			List<String> codCnSeries = Arrays.asList(CommonConstants.PRODUCT_SERIES_CASH_COD,
					CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT, CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD);			
			if(codCnSeries.contains(cnSeries)){
				if (custModificationAliasTO.getIsConsgDelivered().equals(CommonConstants.YES)) {
					if (custModificationAliasTO.getCollectionCheck().equals(CommonConstants.YES)) {
						// Modify Weight only
						custModificationAliasTO.setIsWeightEditable(CommonConstants.YES);
						custModificationAliasTO.setIsCustEditable(CommonConstants.NO);
						custModificationAliasTO.setIsCnTypeEditable(CommonConstants.NO);
					}else if (custModificationAliasTO.getCollectionCheck().equals(CommonConstants.NO)){
						// Modify customer, consignment type and Weight 
						custModificationAliasTO.setIsWeightEditable(CommonConstants.YES);
						custModificationAliasTO.setIsCustEditable(CommonConstants.YES);
						custModificationAliasTO.setIsCnTypeEditable(CommonConstants.YES);						
					}
				}
			}else if (custModificationAliasTO.getCollectionCheck().equals(CommonConstants.YES)) {// Other than L, D and T series consignments
				throw new CGBusinessException(AdminErrorConstants.COLLECTION_CREATED_FORCN);
			}
										
			List<String> custCodeList = Arrays.asList(CommonConstants.CUSTOMER_CODE_CREDIT, CommonConstants.CUSTOMER_CODE_CREDIT_CARD, 
															CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS, CommonConstants.CUSTOMER_CODE_COD,
															CommonConstants.CUSTOMER_CODE_LC, CommonConstants.CUSTOMER_CODE_BA_PICKUP,
															CommonConstants.CUSTOMER_CODE_BA_DELIVERY, CommonConstants.CUSTOMER_CODE_FRANCHISEE,
															CommonConstants.CUSTOMER_CODE_ACC, CommonConstants.CUSTOMER_CODE_GOVT_ENTITY);
			if(!StringUtils.equalsIgnoreCase(custModificationAliasTO.getIsExcessConsg(), CommonConstants.YES) 
					&& !custCodeList.contains(custModificationAliasTO.getCustTypeCode())){
				throw new CGBusinessException(AdminErrorConstants.CREDIT_CUSTOMER_ALLOW);
			}
		}else{
			throw new CGBusinessException(AdminErrorConstants.NO_CONSG_DETAILS_FORMODIFICATION);
		}
		return custModificationAliasTO;
	}

	private void checkBackdatedConsignment(Date bkgDate)
			throws CGSystemException, CGBusinessException {
		if (bkgDate != null){
			Long dateDiffernce = DateUtil.DayDifferenceBetweenTwoDates(bkgDate, DateUtil.getCurrentDate());
			int dateDiff =(int)(long) dateDiffernce;
			String maxBackDateBkgAllowedStr = globalUniversalService.getConfigParamValueByName(CommonConstants.CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED);
			int maxBackDateBkgAllowed = Integer.parseInt(maxBackDateBkgAllowedStr); 
			if(dateDiff > maxBackDateBkgAllowed){
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.CN_MODIFICATION_NOT_ALLOWS_OLDER_CONSIGNMENTS, new String[]{maxBackDateBkgAllowedStr});
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.CnModificationCommonService#saveOrUpdateConsignmentModification(com.ff.consignment.ConsignmentTO)
	 */
	@Override
	public boolean saveOrUpdateConsignmentModification(ConsignmentTO consgTO, CustModificationTO custModificationTO)throws CGBusinessException,
		CGSystemException, IllegalAccessException,
		InvocationTargetException, NoSuchMethodException {
		String consignmentNumber = consgTO.getConsgNo();
		LOGGER.debug("CustModificationServiceImpl: saveOrUpdateConsignmentModification(): START------------>CONSIGNMENT NUMBER :: "+consignmentNumber);
		//Get ConsignmentDO from DB
		ConsignmentDO consignmentDO = cnModificationCommonDAO.getConsignmentDetails(consignmentNumber);
		
		//Create ConsignmentModifiedDO
		ConsignmentModifiedDO consignmentModifiedDO = new ConsignmentModifiedDO();
		CGObjectConverter.copyDO2DO(consignmentDO, consignmentModifiedDO);
		consignmentModifiedDO.setConsgId(null);
		consignmentModifiedDO.setCreatedDate(consgTO.getCreatedDate());
			
		//Get BookingDO from DB
		BookingDO bookingDO = cnModificationCommonDAO.getBookingDeatils(consignmentNumber);
		BookingModifiedDO bookingModifiedDO = null;	
		BookingDO newBookingDO = null;	
		if (!StringUtil.isNull(bookingDO)) {			
			//Create BookingModifiedDO if Booking exist 
			bookingModifiedDO = new BookingModifiedDO();
			CGObjectConverter.copyDO2DO(bookingDO, bookingModifiedDO);
			bookingModifiedDO.setBookingId(null);
			bookingModifiedDO.setCreatedDate(new Date());
		}
		
		// Set New values in BookingDO if already exist or Create Booking Record if not exist
		newBookingDO = prepareBookingDO(bookingDO, consgTO, consignmentDO, custModificationTO);
		
		//updating sap_liability_entries flag of CN
		SAPLiabilityEntriesDO sapLiabilityEntriesDO = updateSapLiabilityEntriesOnCustChange(consgTO, consignmentDO);
		
		//Set New values in ConsignmentDO
		updateConsignmentDO(consignmentDO, consgTO, newBookingDO, custModificationTO);
				
		/* Prepare consignmentRateTO */
		ConsignmentTO consignmentRateTO  = prepareConsignmentTOForRateCalculation(consignmentDO, newBookingDO, consgTO);
		
		//Calculate Rate - ConsignmentRateDO
		calculateConsignmentRate(consignmentRateTO, consignmentDO);
				
		LOGGER.debug("CustModificationServiceImpl: saveOrUpdateConsignmentModification(): END------------>CONSIGNMENT NUMBER :: "+consignmentNumber);
		return cnModificationCommonDAO.saveOrUpdateCustModification(consignmentDO, bookingModifiedDO, consignmentModifiedDO, newBookingDO, sapLiabilityEntriesDO);		
	}
	/**
	 * @param bookingDO
	 * @param consgTO
	 * @param consgDo
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private BookingDO prepareBookingDO(BookingDO bookingDO, ConsignmentTO consgTO, ConsignmentDO consgDo, CustModificationTO custModificationTO) throws CGBusinessException, CGSystemException {
		CustomerTO customerTO = consgTO.getCustomerTO();
		BookingDO newBookingDO = null;
		if(StringUtil.isNull(bookingDO)){
			newBookingDO = new BookingDO();
			newBookingDO.setConsgNumber(consgTO.getConsgNo());
			newBookingDO.setBookingDate(consgTO.getBookingDate());
			newBookingDO.setConsgTypeId(consgDo.getConsgType());
			newBookingDO.setNoOfPieces(consgDo.getNoOfPcs());
			newBookingDO.setBookingOfficeId(consgTO.getOrgOffId());
			newBookingDO.setPincodeId(consgDo.getDestPincodeId());
			newBookingDO.setNoOfPieces(consgDo.getNoOfPcs());
			newBookingDO.setActualWeight(consgDo.getActualWeight());
			newBookingDO.setFianlWeight(consgDo.getFinalWeight());
			newBookingDO.setVolWeight(consgDo.getVolWeight());
			newBookingDO.setPaperWorkRefNo(consgDo.getPaperWorkRefNo());
			newBookingDO.setInsurencePolicyNo(consgDo.getInsurencePolicyNo());
			newBookingDO.setInsuredBy(consgDo.getInsuredBy());
			newBookingDO.setDeclaredValue(consgDo.getDeclaredValue());
			newBookingDO.setDeliveryDate(consgDo.getDeliveredDate());
			newBookingDO.setRefNo(consgDo.getRefNo());
			newBookingDO.setPrice(consgDo.getPrice());
			newBookingDO.setHeight(consgDo.getHeight());
			newBookingDO.setLength(consgDo.getLength());
			newBookingDO.setBreath(consgDo.getBreath());
			newBookingDO.setCnContentId(consgDo.getCnContentId());
			newBookingDO.setProcessNumber(consgDo.getProcessNumber());
			newBookingDO.setCreatedDate(new Date());
			
			List<String> nonModifiableBkgTypes = Arrays.asList(CommonConstants.CASH_BOOKING, CommonConstants.EMOTIONAL_BOND_BOOKING, CommonConstants.FOC_BOOKING);
			String custType = cnModificationCommonDAO.getBookingTypeByCustcode(customerTO.getCustomerCode());
			BookingTypeDO bookingTypeDO = gettingBookingTypeByCustType(custType);
			if(!StringUtil.isNull(bookingTypeDO)){
				newBookingDO.setBookingType(bookingTypeDO);
			}else if(bookingTypeDO!=null && nonModifiableBkgTypes.contains(bookingTypeDO.getBookingType())
					&& !StringUtils.equalsIgnoreCase(custType, CommonConstants.CUSTOMER_CODE_ACC)){
				throw new CGBusinessException(AdminErrorConstants.NO_CONSG_DETAILS_ON_PRODUCT_SERIES);
			}
		}else{
			newBookingDO = bookingDO;
		}
		if(!StringUtil.isEmptyDouble(consgTO.getActualWeight())){
			newBookingDO.setActualWeight(consgTO.getActualWeight());
			newBookingDO.setFianlWeight(consgTO.getFinalWeight());
		}
		newBookingDO.setShippedToCode(customerTO.getShippedToCode());
		if(StringUtils.equalsIgnoreCase(custModificationTO.getIsCustEditable(), CommonConstants.YES)){
			CustomerDO custDO = new CustomerDO();
			custDO.setCustomerId(customerTO.getCustomerId());
			newBookingDO.setCustomerId(custDO);
		} else if (StringUtils.equalsIgnoreCase(custModificationTO.getScreenName(), "BULK") && !StringUtil.isNull(customerTO.getCustomerId())) {
			throw new CGBusinessException(AdminErrorConstants.CUST_NOT_MODIFIED);
		}

		if(StringUtils.equalsIgnoreCase(custModificationTO.getScreenName(), "SNGL")){
			getAndSetInsuarnceConfigDtls(newBookingDO, consgTO);
		}

		newBookingDO.setCreatedBy(FrameworkConstants.FFCL_ADMIN_USER_ID);		
		newBookingDO.setUpdatedBy(consgTO.getCreatedBy());
		newBookingDO.setUpdatedDate(Calendar.getInstance().getTime());
		return newBookingDO;
	}
	/**
	 * @param consgTO
	 * @param consignmentDO
	 * @return
	 * @throws CGSystemException
	 */
	private SAPLiabilityEntriesDO updateSapLiabilityEntriesOnCustChange(
			ConsignmentTO consgTO, ConsignmentDO consignmentDO)
			throws CGSystemException {
		SAPLiabilityEntriesDO sapLiabilityEntriesDO = null;
		if (!StringUtil.isNull(consgTO.getCustomerTO()) && consgTO.getCustomerTO().getCustomerId().equals(consignmentDO.getCustomer())) {
			sapLiabilityEntriesDO = cnModificationCommonDAO.checkSapLiabilityEntriesDetails(consgTO.getConsgNo());
			if (!StringUtil.isNull(sapLiabilityEntriesDO)) {				
				sapLiabilityEntriesDO.setSapStatus(CommonConstants.NO);
				sapLiabilityEntriesDO.setCustNo(consgTO.getCustomerTO().getCustomerCode());
				sapLiabilityEntriesDO.setUpdatedDate(Calendar.getInstance().getTime());
				sapLiabilityEntriesDO.setUpdatedBy(consgTO.getCreatedBy());
				sapLiabilityEntriesDO.setStatusFlag(CommonConstants.CONSIGNMENT_STATUS_BOOK);
			}
		}
		return sapLiabilityEntriesDO;
	}
	/**
	 * @param consignmentDO
	 * @param consgTO
	 * @param bookingDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void updateConsignmentDO(ConsignmentDO consignmentDO,
			ConsignmentTO consgTO, BookingDO bookingDO, CustModificationTO custModificationTO) throws CGBusinessException, CGSystemException {
		CustomerTO newCustomer = null;
		String rateType = null;
		if(!StringUtil.isNull(consgTO.getCustomerTO())){
			newCustomer = consgTO.getCustomerTO();
			rateType = getRateType(newCustomer.getCustomerCode(), consignmentDO.getConsgNo());
			consignmentDO.setRateType(rateType);
			
			// Settng Customer Details
			if(StringUtils.equalsIgnoreCase(custModificationTO.getIsCustEditable(), CommonConstants.YES)){			
				if (!StringUtil.isNull(newCustomer.getCustomerId())) {
					consignmentDO.setCustomer(newCustomer.getCustomerId());
					CustomerTO customer = getCustomerByIdOrCode(newCustomer.getCustomerId(), CommonConstants.EMPTY_STRING);
					if (!StringUtil.isNull(customer)) {
						PickupDeliveryAddressTO address = customer.getAddress();
						if (!StringUtil.isNull(address)) {
							setupConsigneeAddress(consignmentDO, consgTO, customer, address);
						}
					}
				}
			}else if(StringUtils.equalsIgnoreCase(custModificationTO.getScreenName(), "BULK") && !StringUtil.isNull(newCustomer.getCustomerId())){
				throw new CGBusinessException(AdminErrorConstants.CUST_NOT_MODIFIED);
			}
			if (!StringUtil.isEmptyDouble(consgTO.getFinalWeight()) && !consignmentDO.getFinalWeight().equals(consgTO.getFinalWeight())
					|| !newCustomer.getCustomerId().equals(consignmentDO.getCustomer())) {
				consignmentDO.setChangedAfterBillingWtDest(CommonConstants.YES);
			}
		}
		
		if(!StringUtil.isNull(consgTO.getBookingDate())){
			consignmentDO.setEventDate(consgTO.getBookingDate());
		}
		if(!StringUtil.isEmptyDouble(consgTO.getActualWeight()))
			consignmentDO.setActualWeight(consgTO.getActualWeight());
		if(!StringUtil.isEmptyDouble(consgTO.getFinalWeight()))
			consignmentDO.setFinalWeight(consgTO.getFinalWeight());
		
		//Setting Declared Value by consignment type
		consignmentDO.setConsgType(bookingDO.getConsgTypeId());
		consignmentDO.setDeclaredValue(bookingDO.getDeclaredValue());
		consignmentDO.setInsuredBy(bookingDO.getInsuredBy());
		consignmentDO.setNoOfPcs(bookingDO.getNoOfPieces());
		
		if(StringUtils.equalsIgnoreCase(consignmentDO.getIsExcessConsg(), CommonConstants.YES)){
			consignmentDO.setOrgOffId(consgTO.getOrgOffId());
		}
					
		consignmentDO.setIsExcessConsg(CommonConstants.NO);
		consignmentDO.setCreatedBy(FrameworkConstants.FFCL_ADMIN_USER_ID);
		consignmentDO.setUpdatedBy(consgTO.getCreatedBy());
		consignmentDO.setUpdatedDate(Calendar.getInstance().getTime());
		consignmentDO.setBillingStatus(CommonConstants.BILLING_STATUS_TBB);
		consignmentDO.setDtToBranch(CommonConstants.NO);		
	}

	/**
	 * @param consgDo
	 * @param bookDo
	 * @param consgTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private ConsignmentTO prepareConsignmentTOForRateCalculation(
			ConsignmentDO consgDo, BookingDO bookDo, ConsignmentTO consignmentTO) throws CGSystemException, CGBusinessException {
		ConsignmentTO consgTO = new ConsignmentTO();
		if(!StringUtil.isNull(consgDo)){
			convertConsgDO2TO(consgDo,consgTO);
			consgTO.setOrgOffId(bookDo.getBookingOfficeId());
			consgTO.setBookingDate(bookDo.getBookingDate());			
			ProductDO productDO = cnModificationCommonDAO.getProductDetails(consgDo.getProductId());
			if(!StringUtil.isNull(productDO)){
				ProductTO productTO = new ProductTO();
				CGObjectConverter.createToFromDomain(productDO, productTO);
				consgTO.setProductTO(productTO);
			}
						
			CNPricingDetailsTO cnPricingDetailsTO = consgTO.getConsgPriceDtls();
			cnPricingDetailsTO.setDeclaredvalue(consignmentTO.getDeclaredValue());
			cnPricingDetailsTO.setRateType(consgDo.getRateType());
			cnPricingDetailsTO.setSplChg(consgDo.getSplChg());
			cnPricingDetailsTO.setDiscount(consgDo.getDiscount());
			cnPricingDetailsTO.setLcAmount(consgDo.getLcAmount());
			cnPricingDetailsTO.setServicesOn(consgDo.getServicedOn());
			cnPricingDetailsTO.setCodAmt(consgDo.getCodAmt());
			cnPricingDetailsTO.setEbPreferencesCodes(consgDo.getEbPreferencesCodes());
			
			consgTO.setConsgPriceDtls(cnPricingDetailsTO);
		}
		return consgTO;
	}
	/**
	 * @param consignmentRateTO
	 * @param consignmentDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void calculateConsignmentRate(ConsignmentTO consignmentRateTO,
			ConsignmentDO consignmentDO) throws CGBusinessException,
			CGSystemException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Set<ConsignmentBillingRateDO> consignmentRateDOs = consignmentDO.getConsgRateDtls();
		consignmentRateTO.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_BOOK);
		ConsignmentRateCalculationOutputTO cnRateCalculationOutputTO = rateCalculationUniversalService.calculateRateForConsignment(consignmentRateTO);
		if(!CGCollectionUtils.isEmpty(consignmentRateDOs)){
			for (ConsignmentBillingRateDO consignmentBillingRateDO : consignmentRateDOs) {
				if(consignmentBillingRateDO.getRateCalculatedFor().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_BOOK)){
					PropertyUtils.copyProperties(consignmentBillingRateDO, cnRateCalculationOutputTO);
				}else if(consignmentBillingRateDO.getRateCalculatedFor().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
					consignmentRateTO.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTOH);
					ConsignmentRateCalculationOutputTO rtoRateTO = rateCalculationUniversalService.calculateRateForConsignment(consignmentRateTO);
					PropertyUtils.copyProperties(consignmentBillingRateDO, rtoRateTO);
				}
				consignmentBillingRateDO.setBilled(CommonConstants.NO);
				consignmentBillingRateDO.setUpdatedDate(Calendar.getInstance().getTime());
			}
		}else{
			consignmentRateDOs = new HashSet<ConsignmentBillingRateDO>();
			ConsignmentBillingRateDO consignmentBillingRateDO = new ConsignmentBillingRateDO();
			PropertyUtils.copyProperties(consignmentBillingRateDO, cnRateCalculationOutputTO);
			consignmentBillingRateDO.setConsignmentDO(consignmentDO);
			consignmentBillingRateDO.setRateCalculatedFor(CommonConstants.CONSIGNMENT_STATUS_BOOK);
			consignmentBillingRateDO.setCreatedDate(Calendar.getInstance().getTime());
			consignmentBillingRateDO.setCreatedBy(FrameworkConstants.FFCL_ADMIN_USER_ID);
			consignmentRateDOs.add(consignmentBillingRateDO);
		}
		consignmentDO.setConsgRateDtls(consignmentRateDOs);
	}
		
	/**
	 * @param consgDo
	 * @param consgTO
	 * @param customer
	 * @param address
	 */
	private void setupConsigneeAddress(ConsignmentDO consgDo,ConsignmentTO consgTO,
			CustomerTO customer, PickupDeliveryAddressTO address) {
		//setting consignee details
		LOGGER.debug("CustModificationServiceImpl::setupConsigneeAddress::START------------>:::::::");
		ConsigneeConsignorDO consignor = new ConsigneeConsignorDO();
		StringBuilder addressBuilder = new StringBuilder();
		addressBuilder.append(address.getAddress1());
		addressBuilder.append(CommonConstants.COMMA);
		addressBuilder.append(address.getAddress2());
		addressBuilder.append(CommonConstants.COMMA);
		addressBuilder.append(address.getAddress3());
		consignor.setAddress(addressBuilder.toString());
		consignor.setFirstName(customer.getBusinessName());
		consignor.setMobile(address.getMobile());
		consignor.setPhone(address.getPhone());
		consignor.setCreatedBy(consgTO.getCreatedBy());
		consignor.setCreatedDate(Calendar.getInstance().getTime());
		consignor.setUpdatedBy(consgTO.getCreatedBy());
		consignor.setPartyType(CommonConstants.PARTY_TYPE_CONSIGNER);
		consgDo.setConsignor(consignor);
		LOGGER.debug("CustModificationServiceImpl::setupConsigneeAddress::END------------>:::::::");
	}
	
	/**
	 * @param consignmentDO
	 * @param consignmentTO
	 * @throws CGBusinessException
	 */
	public void convertConsgDO2TO(ConsignmentDO consignmentDO,ConsignmentTO consignmentTO) throws CGBusinessException{
		CGObjectConverter.createToFromDomain(consignmentDO, consignmentTO);
		if (!StringUtil.isNull(consignmentDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getDestPincodeId(),
					destPin);
			consignmentTO.setDestPincode(destPin);
		}

		if (!StringUtil.isNull(consignmentDO.getConsgType())) {
			ConsignmentTypeTO cnTypeTO = new ConsignmentTypeTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getConsgType(), cnTypeTO);
			consignmentTO.setTypeTO(cnTypeTO);
		}

		if (!StringUtil.isNull(consignmentDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getInsuredBy(),
					insuredBy);
			consignmentTO.setInsuredByTO(insuredBy);
		}

		CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
		if (!StringUtil.isNull(consignmentDO.getRateType())) {
			cNPricingDetailsTO.setRateType(consignmentDO.getRateType());
		}
		if (!StringUtil.isEmptyDouble(consignmentDO.getDiscount())) {
			cNPricingDetailsTO.setDiscount(consignmentDO.getDiscount());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
			cNPricingDetailsTO.setTopayChg(consignmentDO.getTopayAmt());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getSplChg())) {
			cNPricingDetailsTO.setSplChg(consignmentDO.getSplChg());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getDeclaredValue())) {
			cNPricingDetailsTO.setDeclaredvalue(consignmentDO.getDeclaredValue());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
			cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
		}

		if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
			cNPricingDetailsTO.setLcAmount(consignmentDO.getLcAmount());
		}

		if(!StringUtils.isEmpty(consignmentDO.getEbPreferencesCodes())){
			cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO.getEbPreferencesCodes());
		}
		if(!StringUtils.isEmpty(consignmentDO.getServicedOn())){
			cNPricingDetailsTO.setServicesOn(consignmentDO.getServicedOn());
		}

		consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
	}
	
	/**
	 * @param custType
	 * @return
	 * @throws CGSystemException
	 */
	private BookingTypeDO gettingBookingTypeByCustType(String custType)
			throws CGSystemException {
		BookingTypeDO bookingTypeDO = null;
		String bookingType = null;
		if (custType.equalsIgnoreCase(CommonConstants.CUSTOMER_CODE_BA_PICKUP) 
				|| custType.equalsIgnoreCase(CommonConstants.CUSTOMER_CODE_BA_DELIVERY)) { // BA-Business Associates-Pickup , BV-Business Associates-Delivery
			bookingType = CommonConstants.CUSTOMER_CODE_BA_PICKUP; // BA-BA Booking			
		}else {
			bookingType = CommonConstants.CUSTOMER_CODE_CREDIT; // CR-Credit Booking and ACC
		}
		bookingTypeDO = cnModificationCommonDAO.getBookingTypeDO(bookingType);
		return bookingTypeDO;
	}
	private CustomerTO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = null;
		CustomerDO customerDO = null;
		customerDO = cnModificationCommonDAO.getCustomerByIdOrCode(customerId,
				customerCode);
		if (!StringUtil.isNull(customerDO)) {
			customerTO = new CustomerTO();
			customerTO = (CustomerTO) CGObjectConverter.createToFromDomain(
					customerDO, customerTO);
			if (!StringUtil.isNull(customerDO.getAddressDO())) {
				PickupDeliveryAddressTO address = new PickupDeliveryAddressTO();
				address = (PickupDeliveryAddressTO) CGObjectConverter
						.createToFromDomain(customerDO.getAddressDO(), address);
				customerTO.setAddress(address);
			}
			// Setting customer type
			if (!StringUtil.isNull(customerDO.getCustomerType())) {
				CustomerTypeTO custType =StockUtility.populateCustomerTypeFromCustomer(customerDO);
				customerTO.setCustomerTypeTO(custType);
			}
		}
		return customerTO;
	}
	
	public void getAndSetInsuarnceConfigDtls(BookingDO newBookingDO,
			ConsignmentTO consgTO) throws CGSystemException, CGBusinessException {
		if (!StringUtil.isNull(consgTO.getTypeTO())){ 
			ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
			consgTypeDO.setConsignmentId(consgTO.getTypeTO().getConsignmentId());
			consgTypeDO.setConsignmentCode(consgTO.getTypeTO().getConsignmentCode().split(CommonConstants.HASH)[1]);
			newBookingDO.setConsgTypeId(consgTypeDO);
			
			if(StringUtils.equalsIgnoreCase(consgTypeDO.getConsignmentCode(), CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE)) {
				Double declaredVal = consgTO.getDeclaredValue();
				String bookingType = newBookingDO.getBookingType().getBookingType();
				if(!StringUtil.isEmptyDouble(declaredVal)){
					List<InsuranceConfigTO> insuranceConfigTOs = serviceOfferingCommonService.getInsuarnceConfigDtls(declaredVal, bookingType);
					//if the size of insuranceConfigTOs is 1 then only insuredBy value need to set 
					if (!StringUtil.isEmptyColletion(insuranceConfigTOs) && insuranceConfigTOs.size() == 1) {
						InsuranceConfigTO configTO = insuranceConfigTOs.get(0);
						if(!StringUtil.isNull(configTO.getTrasnStatus()) 
								&& configTO.getTrasnStatus().equals(BillingConstants.NOINSURENCEMAPPING)){
							throw new CGBusinessException(AdminErrorConstants.MAX_LIMIT_DECLAREVALUE);
						}
						newBookingDO.setDeclaredValue(consgTO.getDeclaredValue());
						InsuredByDO insuredBy = new InsuredByDO();
						insuredBy.setInsuredById(configTO.getInsuredById());
						newBookingDO.setInsuredBy(insuredBy);
						newBookingDO.setNoOfPieces(CommonConstants.ONE_INTEGER);
					}
				} 
			}else {
				newBookingDO.setDeclaredValue(null);
				newBookingDO.setInsuredBy(null);
				newBookingDO.setNoOfPieces(null);
			}
		}
	}
}
