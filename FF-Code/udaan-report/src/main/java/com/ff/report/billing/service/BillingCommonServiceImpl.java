package com.ff.report.billing.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.report.billing.action.BillingParallelRateCalc;
import com.ff.report.billing.constants.BillingConstants;
import com.ff.report.billing.converter.BillingCommonConverter;
import com.ff.report.billing.converter.InvoiceRunSheetPrintingConverter;
import com.ff.report.billing.dao.BillingCommonDAO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.FinancialProductDO;
import com.ff.domain.billing.InvoiceRunSheetHeaderDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.BillTO;
import com.ff.to.billing.FinancialProductTO;
import com.ff.to.billing.InvoiceRunSheetTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.universe.billing.service.BillingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
//import com.google.common.collect.Lists;
/*import java.math.*;
import java.lang.*;*/
// TODO: Auto-generated Javadoc
/**
 * The Class BillingCommonServiceImpl.
 *
 * @author narmdr
 */
public class BillingCommonServiceImpl implements BillingCommonService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BillingCommonServiceImpl.class);

	/** The billing universal service. */
	private transient BillingUniversalService billingUniversalService;
	
	/** The billing common dao. */
	private transient BillingCommonDAO billingCommonDAO;
	
	/** The service offering common service. */
	private transient ServiceOfferingCommonService serviceOfferingCommonService;
	
	/** The organization common service. */
	private transient OrganizationCommonService organizationCommonService;
	
	/** The geography common service. */
	private transient GeographyCommonService geographyCommonService;
	
	/** The global universal service. */
	private transient GlobalUniversalService globalUniversalService;
	
	/** The business common service. */
	private transient BusinessCommonService businessCommonService;
	
	/** The sequence generator service. */
	private transient SequenceGeneratorService sequenceGeneratorService;
	
	/** The pickup management common service. */
	private transient PickupManagementCommonService pickupManagementCommonService;
	
	private transient RateCalculationUniversalService rateCalculationUniversalService;
	
	private transient BillingParallelRateCalc billingParallelRateCalc;
	
	/**
	 * @param billingParallelRateCalc the billingParallelRateCalc to set
	 */
/*	public void setBillingParallelRateCalc(
			BillingParallelRateCalc billingParallelRateCalc) {
		this.billingParallelRateCalc = billingParallelRateCalc;
	}*/

	/**
	 * Sets the billing universal service.
	 *
	 * @param billingUniversalService the billingUniversalService to set
	 */
	public void setBillingUniversalService(
			BillingUniversalService billingUniversalService) {
		this.billingUniversalService = billingUniversalService;
	}
	
	/**
	 * Sets the billing common dao.
	 *
	 * @param billingCommonDAO the billingCommonDAO to set
	 */
	public void setBillingCommonDAO(BillingCommonDAO billingCommonDAO) {
		this.billingCommonDAO = billingCommonDAO;
	}
	
	/**
	 * Sets the service offering common service.
	 *
	 * @param serviceOfferingCommonService the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}
	
	/**
	 * Sets the organization common service.
	 *
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}
	
	/**
	 * Sets the geography common service.
	 *
	 * @param geographyCommonService the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}
	
	/**
	 * Sets the global universal service.
	 *
	 * @param globalUniversalService the globalUniversalService to set
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}	
	
	/**
	 * Sets the business common service.
	 *
	 * @param businessCommonService the businessCommonService to set
	 */
	public void setBusinessCommonService(BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}
	
	/**
	 * Sets the sequence generator service.
	 *
	 * @param sequenceGeneratorService the sequenceGeneratorService to set
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}	
	
	/**
	 * Sets the pickup management common service.
	 *
	 * @param pickupManagementCommonService the pickupManagementCommonService to set
	 */
	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}
	
	

	/**
	 * @param rateCalculationUniversalService the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}
	
	/*	BillingCommonServiceImpl(ConsignmentTO  consignmentTO){
		this.consignmentTO=consignmentTO;
	}*/

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getPickupBoys(java.lang.Integer)
	 */
	@Override
	public List<EmployeeTO> getPickupBoys(final Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getBranchEmployees(officeId);
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getRegions()
	 */
	@Override
	public List<RegionTO> getRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getProducts()
	 */
	@Override
	public List<FinancialProductTO> getProducts() throws CGBusinessException,
			CGSystemException {
		//return serviceOfferingCommonService.getAllProducts();
		List<FinancialProductDO> products = null;
		List<FinancialProductTO> productTOs = null;
		try {
			products = billingCommonDAO.getAllFinancialProducts();
			if (!StringUtil.isEmptyList(products)) {
				productTOs = new ArrayList(products.size());
				productTOs = (List<FinancialProductTO>) CGObjectConverter
						.createTOListFromDomainList(products, FinancialProductTO.class);
				Collections.sort(productTOs);
				
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : ServiceOfferingCommonServiceImpl.getProductByConsgSeries()",
					e);
		}
		return productTOs;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getCitiesByRegionId(java.lang.Integer)
	 */
	@Override
	public List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException {
		final CityTO cityTO = new CityTO();
		cityTO.setRegion(regionId);
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getOfficesByCityId(java.lang.Integer)
	 */
	@Override
	public List<OfficeTO> getOfficesByCityId(final Integer cityId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCity(cityId);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getCustomersByOfficeId(java.lang.Integer)
	 */
	@Override
	public List<CustomerTO> getCustomersByOfficeId(final Integer officeId)
			throws CGBusinessException, CGSystemException {
		return businessCommonService.getCustomersByOfficeId(officeId);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getStandardTypesByTypeName(java.lang.String)
	 */
	@Override
	public List<StockStandardTypeTO> getStandardTypesByTypeName(
			final String typeName) throws CGBusinessException,
			CGSystemException {
		return globalUniversalService.getStandardTypesByTypeName(typeName);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#generateInvoiceRunsheetNumber(java.lang.String)
	 */
	@Override
	public String generateInvoiceRunsheetNumber(String officeCode)
			throws CGBusinessException, CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::generateInvoiceRunsheetNumber::START----->");
		//I+branch code+7 digit
		StringBuilder invoiceRunsheetNumber = new StringBuilder(15);
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(BillingConstants.BILLING_INVOICE_RUNSHEET_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();
		
		if(sequenceGeneratorConfigTO.getGeneratedSequences()!=null && 
				sequenceGeneratorConfigTO.getGeneratedSequences().size()>0){
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		}
		
		invoiceRunsheetNumber
				.append(BillingConstants.BILLING_INVOICE_RUNSHEET_NO_START_CODE)
				.append(officeCode).append(runningNumber);
		LOGGER.debug("BillingCommonServiceImpl::generateInvoiceRunsheetNumber::END----->");
		return invoiceRunsheetNumber.toString();
	
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getShippedToCodesByCustomerId(java.lang.Integer)
	 */
	@Override
	public List<String> getShippedToCodesByCustomerId(Integer customerId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonServiceImpl::getShippedToCodesByCustomerId::START/END----->");
		return billingUniversalService.getShippedToCodesByCustomerId(customerId);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getCustomers(java.lang.Integer)
	 */
	@Override
	public List<CustomerTO> getCustomers(Integer pickupBoyId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonServiceImpl::getCustomers::START/END----->");
		return pickupManagementCommonService.getMasterPickupAssignmentCustomers(pickupBoyId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getBills(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getBills(String shipToCode, String startDate, String endDate)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillingCommonServiceImpl::getBills::START/END----->");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getBillsData(java.util.List)
	 */
	@Override
	public List<BillTO> getBillsData(List<String> invoiceNos)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillingCommonServiceImpl::getBillsData::START/END----->");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getBillsData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillTO> getBillsData(String shipToCode, String startDate,
			String endDate) throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonServiceImpl::getBillsData::START/END----->");
		return billingUniversalService.getBillsData(shipToCode, startDate, endDate);
	}
	
	public List<BillTO> getBillsByCustomerId(Integer custId,String startDate,String endDate)throws CGBusinessException, CGSystemException {
		LOGGER.debug("BillingCommonServiceImpl::getBillsByCustomerId::START/END----->");
		return billingUniversalService.getBillsByCustomerId(custId, startDate, endDate);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#saveInvoiceRunSheet(com.ff.to.billing.InvoiceRunSheetTO)
	 */
	@Override
	public InvoiceRunSheetHeaderDO saveInvoiceRunSheet(InvoiceRunSheetTO invoiceRunSheetTO) throws CGBusinessException,
	CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::saveInvoiceRunSheet::START----->");
		InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO = new InvoiceRunSheetHeaderDO();
		invoiceRunSheetHeaderDO = InvoiceRunSheetPrintingConverter.invoiceRunSheetDomainConverter(invoiceRunSheetTO);
		if (!StringUtil.isNull(invoiceRunSheetHeaderDO)) {
			invoiceRunSheetHeaderDO = billingCommonDAO.saveInvoiceRunSheet(invoiceRunSheetHeaderDO);
		}
		/*else{
			throw new CGBusinessException(AdminErrorConstants.BillINVOICE_DATA_NOT_SAVED);
		}*/
		LOGGER.debug("BillingCommonServiceImpl::saveInvoiceRunSheet::END----->");
		return invoiceRunSheetHeaderDO;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getInvoiceRunSheet(java.lang.String)
	 */
	@Override
	public List<InvoiceRunSheetTO> getInvoiceRunSheet(
			String invoiceRunSheetNumber) throws CGBusinessException,
			CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::getInvoiceRunSheet::START----->");
		// TODO Auto-generated method stub
		List<InvoiceRunSheetHeaderDO> invoiceRunSheetHeaderDOs = billingCommonDAO.getInvoiceRunSheet(invoiceRunSheetNumber);
		List<InvoiceRunSheetTO> invoiceRunSheetTOs = new ArrayList<InvoiceRunSheetTO>();
		for(InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO:invoiceRunSheetHeaderDOs){
			InvoiceRunSheetTO invoiceRunSheetTO = InvoiceRunSheetPrintingConverter.invoiceRunsheetTOConverter(invoiceRunSheetHeaderDO);
			invoiceRunSheetTOs.add(invoiceRunSheetTO);
		}
		LOGGER.debug("BillingCommonServiceImpl::getInvoiceRunSheet::END----->");
		return invoiceRunSheetTOs;
	}
	
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getRunSheetStatus()
	 */
	@Override
	public List<StockStandardTypeTO> getRunSheetStatus()
			throws CGSystemException, CGBusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("BillingCommonServiceImpl::getRunSheetStatus::START/END----->");
		return globalUniversalService.getStandardTypesByTypeName(BillingConstants.INVOICE_RUNSHEET_STATUS);
	}
	
	
	/* (non-Javadoc)
	 * @see com.ff.admin.billing.service.BillingCommonService#getConsignmentsForRate()
	 */
	@Override
	public void  getConsignmentsForRate()throws CGBusinessException,CGSystemException, InterruptedException{
		
		LOGGER.debug("BillingCommonServiceImpl::getConsignmentsForRate::START----->");
		Long limit=billingCommonDAO.getLimitOfRecordProcessedForBilling();
		Long recordCount=billingCommonDAO.getTotalCNForBillingJob();
		
		for(Long i=1L;i<=recordCount;i=i+limit){
			
		    calcRateForCNInConsgRate(limit);
			
		}
		LOGGER.debug("BillingCommonServiceImpl::getConsignmentsForRate::END----->");
	}
	
	@Override
	public ProductTO getProduct(Integer productId) throws CGBusinessException,
			CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::getProduct::START----->");
		ProductTO productTO = null;
		ProductDO productDO = null;
		try{
			productDO=billingCommonDAO.getProduct(productId);
			if(!StringUtil.isNull(productDO)){
				productTO = new ProductTO();
				CGObjectConverter.createToFromDomain(productDO, productTO);
			}
		} catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::getProduct()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::getProduct::END----->");
		return productTO;
	}
	
	@Override
	public OfficeTO getOffice(Integer officeId)throws CGBusinessException,
	CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::getOffice::START----->");
		OfficeTO officeTO = null;
		OfficeDO officeDO = null;
		try{
			officeDO=billingCommonDAO.getOffice(officeId);
			if(!StringUtil.isNull(officeDO)){
				officeTO = new OfficeTO();
				CGObjectConverter.createToFromDomain(officeDO, officeTO);
			}
		} catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::getOffice()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::getOffice::END----->");
		return officeTO;
	}
	
	
	public CityTO getCityByOffice(Integer cityId)throws CGBusinessException,
	CGSystemException {
		
		LOGGER.debug("BillingCommonServiceImpl::getCityByOffice::START----->");
		CityTO cityTO = null;
		CityDO cityDO = null;
		try{
			cityDO=billingCommonDAO.getCityByOffice(cityId);
			if(!StringUtil.isNull(cityDO)){
				cityTO = new CityTO();
				CGObjectConverter.createToFromDomain(cityDO, cityTO);
			}
		} catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::getCityByOffice()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::getCityByOffice::END----->");
		return cityTO;
	
	}
	
	
	public void UpdateConsignmentBillingStatus(String consgNo)throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonServiceImpl::UpdateConsignmentBillingStatus::START----->");
		try{
		/*	if(!updateStatus){
			throw new CGBusinessException();
		}*/
			
		} catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::UpdateConsignmentBillingStatus()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::UpdateConsignmentBillingStatus::END----->");
	}
	
		
	public boolean billing_consolidation_Proc()throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonServiceImpl::billing_consolidation_Proc::START----->");		
		boolean flag=false;
		try{
			
			flag=billingCommonDAO.billing_consolidation_Proc();
			
		}catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::billing_consolidation_Proc()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::billing_consolidation_Proc::END----->");
		return flag;
	}
	
	public BookingDO getConsgBookingDetails(String consgNo)throws CGBusinessException,
	CGSystemException{
		LOGGER.debug("BillingCommonServiceImpl::getConsgBookingDate::START----->");
		BookingDO bookingDO = null;
		try{
			bookingDO=billingCommonDAO.getCustomerFromTypeBooking(consgNo);
			if(!StringUtil.isNull(bookingDO)){
				/*if(!StringUtil.isNull(bookingDO.getBookingDate())){
					bookingDate=bookingDO.getBookingDate();
					
				}*/
				return bookingDO;
			}
		} catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::getConsgBookingDate()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::getConsgBookingDate::END----->");
		return null;
	}
	
	private void  prepareConsgRateCalcDomainConverter(ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO,ConsignmentBillingRateDO consignmentBillingRateDO) throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		LOGGER.debug("BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::START------------>:::::::");
		 if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){

			 PropertyUtils.copyProperties(consignmentBillingRateDO, consignmentRateCalculationOutputTO);
			 consignmentBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
			 consignmentBillingRateDO.setUpdatedBy(-1);
		 }
		
		
		LOGGER.debug("BillingCommonServiceImpl::prepareConsgRateCalcDomainConverter::END------------>:::::::");
	}
	
	
	
	@Override
	public void calcRateForCNInConsgRate(Long limit)throws CGBusinessException,CGSystemException, InterruptedException{
		
		LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::START----->");
		long startTime1=0L,stopTime1=0L;
		List<BillingParallelRateCalc> cnThreads=new ArrayList<BillingParallelRateCalc>();
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO = null;
		ConsignmentBillingRateDO consignmentBillingRate=null;
		String  bookStatus="B";
		/*Fetching CN*/
		List<ConsignmentBilling> consignmentDOs=billingCommonDAO.getConsignmentForRate(limit);
		long startTm1=System.currentTimeMillis();
		List<ConsignmentTO> consignmentTOs = BillingCommonConverter.convertConsignmentDOsToTOs(consignmentDOs);
		long endTm1=System.currentTimeMillis();
		long elapseTm1=endTm1-startTm1;
		LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::Elaspse time for converting consgDO to TO ----->"+elapseTm1 +"  for total CN "+consignmentTOs.size());
		
		if(consignmentTOs.size()>10){
		   List<List<ConsignmentTO>> partitions = null;//Lists.partition(consignmentTOs, consignmentTOs.size()/5);
		   for(List<ConsignmentTO> consignmentSubList : partitions) {
			   billingParallelRateCalc=new BillingParallelRateCalc(consignmentSubList, this.rateCalculationUniversalService);
			   cnThreads.add(billingParallelRateCalc);
			}
			 ExecutorService executor = Executors.newFixedThreadPool(partitions.size());
			 @SuppressWarnings({ "unchecked", "rawtypes" })
			 long startTmMulti=System.currentTimeMillis();  
			 //Calculating rate using Multithreading
			 List<Future< Map<String,ConsignmentRateCalculationOutputTO>>> results1 = executor.invokeAll(cnThreads);
			 executor.shutdown();
			 long endTmMulti=System.currentTimeMillis();
			 LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for MultiThreading----->"+(endTmMulti-startTmMulti));
			 
			 for(Future< Map<String,ConsignmentRateCalculationOutputTO>> resultOutpt:results1){
				 try {					
					 Map<String,ConsignmentRateCalculationOutputTO> resultMap=resultOutpt.get();
					 Iterator<Map.Entry<String,ConsignmentRateCalculationOutputTO>> entries = resultMap.entrySet().iterator();
					 while (entries.hasNext()) {
					     Map.Entry<String,ConsignmentRateCalculationOutputTO> entry = entries.next();
					     if(entry.getValue()!=null && entry.getKey()!=null){
					    	 long start=System.currentTimeMillis();
					    	 ConsignmentRateCalculationOutputTO rateOutputTO=entry.getValue();
					    	 ConsignmentDO consingnment = new ConsignmentDO();
				   			 consingnment.setConsgId(rateOutputTO.getConsgId());
				   		     consignmentBillingRate=billingCommonDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
				   		 if(!StringUtil.isNull(consignmentBillingRate)){
							prepareConsgRateCalcDomainConverter(rateOutputTO,consignmentBillingRate);
						   	consignmentBillingRate=billingCommonDAO.saveOrUpdateConsgRate(consignmentBillingRate,entry.getKey());
						   	//UpdateConsignmentBillingStatus(consignmentTO.getConsgNo());
					     }
					     else{
					    	 consignmentBillingRate=new ConsignmentBillingRateDO();
				   			 prepareConsgRateCalcDomainConverter(rateOutputTO,consignmentBillingRate);
				   			 ConsignmentDO consgDO = new ConsignmentDO();
				   			 consingnment.setConsgId(rateOutputTO.getConsgId());
				   			 consignmentBillingRate.setConsignmentDO(consgDO);
				   			 consignmentBillingRate.setRateCalculatedFor(bookStatus);
				   			 consignmentBillingRate=billingCommonDAO.saveOrUpdateConsgRate(consignmentBillingRate,entry.getKey());
					     }
					     long end=System.currentTimeMillis();
					     LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Insert/Update and changing flag ----->"+(end-start)+"  For CN  "+entry.getKey());
					 }
					 else if(entry.getValue()== null && entry.getKey()!=null) {
						//updating status of consg to RTB
			   			long startTm=System.currentTimeMillis();
			   			UpdateConsignmentBillingStatus(entry.getKey());
			   			long endTm=System.currentTimeMillis();
			   			long elapseTm=endTm-startTm;
			   			LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for only updating billing status flag ----->"+elapseTm+"  For CN  "+entry.getKey());
					 }
				  }
				} catch (ExecutionException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
					// TODO Auto-generated catch block
					LOGGER.error("BillingCommonServiceImpl::calcRateForCNInConsgRate::Error"+e);
				}
			  }	 
		  }
		  else{
		      for(ConsignmentTO consignmentTO:consignmentTOs){
		   	  try{
		   		  String bookingType=consignmentTO.getBookingType();
		   		  //if(!bookingType.equals("CS") && !bookingType.equals("EB") && !bookingType.equals("FC") && consignmentTO.getChangedAfterNewRateCmpnt().equals("Y") ){
		   		if(!bookingType.equals("CS") && !bookingType.equals("EB") && !bookingType.equals("FC") && consignmentTO.getChangedAfterBillingWtDest().equals("Y") && consignmentTO.getTypeTO().getConsignmentCode().equals("PPX") && consignmentTO.getBOOKING_RATE_BILLED().equals("N")){	
		   		  LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::Rate for Consg----->"+consignmentTO.getConsgNo());
		   		  long startTime = System.currentTimeMillis();
		   	      //calculating rate for CN
		   		  consignmentRateCalculationOutputTO=rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
		   		  long stopTime = System.currentTimeMillis();
		   		  long elapsedTime = stopTime - startTime;
		   		  LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for calc rate----->"+elapsedTime+" for CN  "+consignmentTO.getConsgNo());
		   			   
		   		  //checking prev consg rates
		   		 startTime1 = System.currentTimeMillis();
		   		 if(StringUtil.isStringEmpty(consignmentTO.getConsgStatus())){
		   			   ConsignmentDO consingnment = new ConsignmentDO();
		   			   consingnment.setConsgId(consignmentTO.getConsgId());
		   		       consignmentBillingRate=billingCommonDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
		   		 }
		   		 else if(consignmentTO.getConsgStatus().equals("R")){
		   		     ConsignmentDO consingnment = new ConsignmentDO();
		   			 consingnment.setConsgId(consignmentTO.getConsgId());
		   			 consignmentBillingRate=billingCommonDAO.getAlreadyExistConsgRate(consingnment,consignmentTO.getConsgStatus());
		   		 }
		   		 else{
		   			  ConsignmentDO consingnment = new ConsignmentDO();
		   			  consingnment.setConsgId(consignmentTO.getConsgId());
		   			  consignmentBillingRate=billingCommonDAO.getAlreadyExistConsgRate(consingnment,bookStatus);
		   		}
		   			
		   		if(!StringUtil.isNull(consignmentBillingRate)){
		   		   prepareConsgRateCalcDomainConverter(consignmentRateCalculationOutputTO,consignmentBillingRate);
		   				
		   				consignmentBillingRate=billingCommonDAO.saveOrUpdateConsgRate(consignmentBillingRate,consignmentTO.getConsgNo());
		   				//UpdateConsignmentBillingStatus(consignmentTO.getConsgNo());
		   		}
		   		else{
		   			 consignmentBillingRate=new ConsignmentBillingRateDO();
		   			 prepareConsgRateCalcDomainConverter(consignmentRateCalculationOutputTO,consignmentBillingRate);
		   			 ConsignmentDO consingnment = new ConsignmentDO();
		   			 consingnment.setConsgId(consignmentTO.getConsgId());
		   			 consignmentBillingRate.setConsignmentDO(consingnment);
		   				
		   			 if(StringUtil.isStringEmpty(consignmentTO.getConsgStatus())){
		   				consignmentBillingRate.setRateCalculatedFor(bookStatus);
		   			 }
		   			 else if(consignmentTO.getConsgStatus().equals("R")){
		   				consignmentBillingRate.setRateCalculatedFor(consignmentTO.getConsgStatus());
		   			}
		   			else{
		   				  consignmentBillingRate.setRateCalculatedFor(bookStatus);
		   			}
		   			  consignmentBillingRate=billingCommonDAO.saveOrUpdateConsgRate(consignmentBillingRate,consignmentTO.getConsgNo());
		   				//UpdateConsignmentBillingStatus(consignmentTO.getConsgNo());
		   		  }
		   			stopTime1 = System.currentTimeMillis();
		   			elapsedTime = stopTime1 - startTime1;
		   			LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Insert/UPDATING and flag changing----->"+elapsedTime+" for CN  "+consignmentTO.getConsgNo());
		   			   
		   		}
		   		else{
		   				//updating status of consg to RTB
		   			long startTm=System.currentTimeMillis();
		   			UpdateConsignmentBillingStatus(consignmentTO.getConsgNo());
		   			long endTm=System.currentTimeMillis();
		   			long elapseTm=endTm-startTm;
		   			LOGGER.debug("BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for Changing only Billing status flag----->"+elapseTm+"   "+consignmentTO.getConsgNo());
		   		}
		   			  
		   		 }catch(Exception e){
		   			  LOGGER.error("BillingCommonServiceImpl::calcRateForCNInConsgRate::END----->"+e);
		   		}
		   	  }
		   }
		}
		       
		
	public boolean billing_Stock_consolidation_Proc()throws CGBusinessException,
	CGSystemException{
		
		LOGGER.debug("BillingCommonServiceImpl::billing_Stock_consolidation_Proc::START----->");		
		boolean flag=false;
		try{
			
			flag=billingCommonDAO.billing_Stock_consolidation_Proc();
			
		}catch(Exception e){
			LOGGER.error("Exception occurs in BillingCommonServiceImpl::billing_Stock_consolidation_Proc()::" 
					+ e);
			throw new CGBusinessException(e);
		}
		LOGGER.debug("BillingCommonServiceImpl::billing_Stock_consolidation_Proc::END----->");
		return flag;
	}
}
