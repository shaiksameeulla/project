/**
 * 
 */
package com.ff.admin.billing.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.billing.constants.BillingConstants;
import com.ff.admin.billing.dao.ReBillingDAO;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.BillingConsignmentRateDO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ReBillingConsignmentDO;
import com.ff.domain.billing.ReBillingConsignmentRateDO;
import com.ff.domain.billing.ReBillingHeaderDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.ReBillConsgAliasTO;
import com.ff.to.billing.ReBillingTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.ratemanagement.masters.RateContractTO;
import com.ff.universe.billing.service.BillingUniversalService;

/**
 * @author abarudwa
 *
 */
public class ReBillingServiceImpl implements ReBillingService{
	
private final static Logger LOGGER = LoggerFactory.getLogger(ReBillingServiceImpl.class);
	
	private BillingCommonService billingCommonService;
	
	private BillingUniversalService billingUniversalService;
	private BillPrintingService billPrintingService;
	/** The sequence generator service. */
	private transient SequenceGeneratorService sequenceGeneratorService;
	private transient  ReBillingDAO reBillingDAO;
	private RateCalculationUniversalService rateCalculationUniversalService;
	
	
	/**
	 * @param billingUniversalService the billingUniversalService to set
	 */
	public void setBillingUniversalService(
			BillingUniversalService billingUniversalService) {
		this.billingUniversalService = billingUniversalService;
	}

	/**
	 * @param billingCommonService the billingCommonService to set
	 */
	public void setBillingCommonService(BillingCommonService billingCommonService) {
		this.billingCommonService = billingCommonService;
	}

	
	
	public void setBillPrintingService(BillPrintingService billPrintingService) {
		this.billPrintingService = billPrintingService;
	}

	
	
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	
	
	public void setReBillingDAO(ReBillingDAO reBillingDAO) {
		this.reBillingDAO = reBillingDAO;
	}

	
	
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	@Override
	public List<RegionTO> getRegions() throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getRegions::START----->");
		List<RegionTO> regionTOs = billingCommonService.getRegions();
		if (CGCollectionUtils.isEmpty(regionTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_REGION_FOUND);
		}
		LOGGER.debug("ReBillingServiceImpl::getRegions::END----->");
		return regionTOs;
	}

	@Override
	public List<CityTO> getCitiesByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getCitiesByRegionId::START----->");
		List<CityTO> cityTOs = billingCommonService.getCitiesByRegionId(regionId);
		if (CGCollectionUtils.isEmpty(cityTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_STATION_FOUND);
		}
		LOGGER.debug("ReBillingServiceImpl::getCitiesByRegionId::END----->");
		return cityTOs;
	}

	@Override
	public List<OfficeTO> getOfficesByCityId(Integer cityId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getOfficesByCityId::START----->");
		List<OfficeTO> officeTOs = billingCommonService.getOfficesByCityId(cityId);
		if (CGCollectionUtils.isEmpty(officeTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_BRANCHES_FOUND);
		}
		LOGGER.debug("ReBillingServiceImpl::getOfficesByCityId::END----->");
		return officeTOs;
	}

	@Override
	public List<CustomerTO> getCustomersByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getCustomersByOfficeId::START----->");
		//List<CustomerTO> customerTOs = billingCommonService.getCustomersByOfficeId(officeId);
		List<Integer> branchId=new ArrayList<Integer>();
		branchId.add(officeId);
		List<CustomerTO> customerTOs=billPrintingService.getCustomersByBillingBranch(branchId);
		if (CGCollectionUtils.isEmpty(customerTOs)) {
			throw new CGBusinessException(AdminErrorConstants.NO_CUSTOMER_FOUND);
		}
		LOGGER.debug("ReBillingServiceImpl::getCustomersByOfficeId::END----->");
		return customerTOs;
	}

	

	@Override
	public List<RateContractTO> getRateContractsByCustomerIds(
			List<Integer> customerIds) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getRateContractsByCustomerIds::START----->");
		List<RateContractTO> rateContractTOs = billingUniversalService.getRateContractsByCustomerIds(customerIds);
		LOGGER.debug("ReBillingServiceImpl::getRateContractsByCustomerIds::END----->");
		return rateContractTOs;
	}

	@Override
	public List<RateCalculationOutputTO> prepareRateInputs(
			List<ConsignmentTO> consignmentList) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::prepareRateInputs::START----->");
		List<RateCalculationOutputTO> rateCalculationOutputTOs=null;
		/*rateCalculationOutputTOs = billingCommonService.prepareRateInputs(consignmentList);*/
		LOGGER.debug("ReBillingServiceImpl::prepareRateInputs::END----->");
		return rateCalculationOutputTOs;
	}

	@Override
	public List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			Integer customerId, String startDateStr, String endDateStr)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		LOGGER.debug("ReBillingServiceImpl::getBookedConsignmentsByCustIdDateRange::START----->");
		List<ConsignmentTO> consignmentTOs = billingUniversalService.getBookedConsignmentsByCustIdDateRange(customerId, startDateStr, endDateStr);
		LOGGER.debug("ReBillingServiceImpl::getBookedConsignmentsByCustIdDateRange::END----->");
		return consignmentTOs;
	}

	
	public ReBillingTO saveOrUpdateReBilling(ReBillingTO rebillingTO)throws CGBusinessException,
	CGSystemException {
		ReBillingHeaderDO reBillingHeaderDO=new ReBillingHeaderDO();
		String reBillNo=generateReBillingNumber(rebillingTO.getLoginOfficeCode());
		rebillingTO.setRebillingNo(reBillNo);
		prepareRebillingDOFromTO(reBillingHeaderDO,rebillingTO);
		reBillingHeaderDO=reBillingDAO.saveOrUpdateReBilling(reBillingHeaderDO);
		ReBillingTO reBillingTO=new ReBillingTO();
		if(StringUtil.isNull(reBillingHeaderDO.getReBillingNumber())){
			throw new CGBusinessException(BillingConstants.REBILLING_DATA_NOT_FOUND);
		}
		reBillingTO.setRebillingNo(reBillingHeaderDO.getReBillingNumber());
		return reBillingTO;
	}

  public String generateReBillingNumber(String officeCode)throws CGBusinessException, CGSystemException {
		LOGGER.debug("ReBillingServiceImpl::generateReBillingNumber::START----->");
		// I+branch code+7 digit
		StringBuilder rebillingNumber = new StringBuilder(15);
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO
				.setProcessRequesting(BillingConstants.REBILLING_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();

		if (sequenceGeneratorConfigTO.getGeneratedSequences() != null
				&& sequenceGeneratorConfigTO.getGeneratedSequences().size() > 0) {
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences()
					.get(0);
		}

		rebillingNumber
				.append(BillingConstants.RE_BILLING_NO_START_CODE)
				.append(officeCode).append(runningNumber);
		LOGGER.debug("ReBillingServiceImpl::generateReBillingNumber::END----->");
		return rebillingNumber.toString();
	
}

  public void prepareRebillingDOFromTO(ReBillingHeaderDO reBillingHeaderDO,ReBillingTO rebillingTO) throws CGBusinessException, CGSystemException {
	  
	  if(!StringUtil.isEmptyInteger(rebillingTO.getRegionTO())){
		  reBillingHeaderDO.setRegion(rebillingTO.getRegionTO());
	  }
	  
	  if(!StringUtil.isEmptyInteger(rebillingTO.getCityTO())){
		  reBillingHeaderDO.setCity(rebillingTO.getCityTO());
	  }
	  
	  if(!StringUtil.isEmptyInteger(rebillingTO.getOfficeTO())){
		  reBillingHeaderDO.setOffice(rebillingTO.getOfficeTO());
	  }
	  
	  if(!StringUtil.isEmptyInteger(rebillingTO.getCustomerTO())){
		  reBillingHeaderDO.setCustomer(rebillingTO.getCustomerTO());
	  }
	  
	  if(!StringUtil.isNull(rebillingTO.getRebillingNo())){
		  reBillingHeaderDO.setReBillingNumber(rebillingTO.getRebillingNo());
	  }
	  
	  if(!StringUtil.isNull(rebillingTO.getStartDateStr())){
		  //DateUtil.getDateFromString(rebillingTO.getStartDateStr(), "yyyy-MM-dd")
		  reBillingHeaderDO.setStartDate(DateUtil.getDateFromString(rebillingTO.getStartDateStr(), FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
	  }
	  
	  if(!StringUtil.isNull(rebillingTO.getEndDateStr())){
		  reBillingHeaderDO.setEndDate(DateUtil.getDateFromString(rebillingTO.getEndDateStr(),FrameworkConstants.DDMMYYYY_SLASH_FORMAT));
	  }
	  
	  reBillingHeaderDO.setCreatedBy(rebillingTO.getCreatedBy());
	  reBillingHeaderDO.setCreatedDate(Calendar.getInstance().getTime());
  }
  
 /* @Async*/
  public void getRebillingDetails()throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException{
	  long startMilliseconds = System.currentTimeMillis();
	  LOGGER.debug("ReBillingServiceImpl::getRebillingDetails::START------------>:::::::startMilliseconds"
				+ startMilliseconds);
	  List<ReBillingHeaderDO> reBillingHeaderDOs=null;
	  ConsignmentTO consgTO=null;
	  String rateFor="B" ,rateForRto="R";
	  ConsignmentBillingRateDO consignmentBillingRate=null;
	  ReBillingConsignmentRateDO reBillingRateDO=null;
	  ConsignmentDO consingnment=null;
	  ReBillingConsignmentDO rebillConsgDO=null;
	  Integer bookforId=0,rtoforId=0;
	  List<ReBillingConsignmentRateDO> rebillConsgRateList=null;
	  List<ReBillingConsignmentRateDO> rebillConsgRateListForOld=null;
	  reBillingHeaderDOs= reBillingDAO.getRebillingJobDetails();
	  Date currentDate = new Date(System.currentTimeMillis());
	  try{
	  if(!StringUtil.isEmptyColletion(reBillingHeaderDOs)){
		  
		  for(ReBillingHeaderDO reBillingHeaderDO : reBillingHeaderDOs){
			  List<ReBillConsgAliasTO> reBillAliasTOs= reBillingDAO.getRebillConsignmentData(reBillingHeaderDO);
			   for(ReBillConsgAliasTO reBillConsgAliasTO:reBillAliasTOs){
				   try{
				   if(reBillConsgAliasTO.getBill_generated().equals("N")){ //for the cn for which bill is not yet generated
					   consgTO=new ConsignmentTO();
					   prepareConsgTO(reBillConsgAliasTO,consgTO);
					   /** Below condition is added to stamp booking/event as the current date. This change is done so that the rates of the consignment
					    * are calculated according to the latest contract **/
					   if (!StringUtil.isStringEmpty(consgTO.getConsgStatus()) && 
							   CommonConstants.CONSIGNMENT_STATUS_RTOH.equalsIgnoreCase(consgTO.getConsgStatus())) {
							consgTO.setEventDate(currentDate);
						} else {
							consgTO.setBookingDate(currentDate);
						}
					   ConsignmentRateCalculationOutputTO rateOutput=rateCalculationUniversalService.calculateRateForConsignment(consgTO);
					   consingnment = new ConsignmentDO();
			   		   consingnment.setConsgId(reBillConsgAliasTO.getConsgId());
			   		   consignmentBillingRate= reBillingDAO.getAlreadyExistConsgRate(consingnment, rateFor);
			   		   if(!StringUtil.isNull(consignmentBillingRate)){
							prepareConsgRateCalcDomainConverter(rateOutput,consignmentBillingRate);
							consignmentBillingRate=reBillingDAO.saveOrUpdateRateAndStatus(consignmentBillingRate,reBillConsgAliasTO.getConsgNo());
					     }
					     else{
					    	 consignmentBillingRate=new ConsignmentBillingRateDO();
				   			 prepareConsgRateCalcDomainConverter(rateOutput,consignmentBillingRate);
				   			 ConsignmentDO consgDO = new ConsignmentDO();
				   			 consgDO.setConsgId(reBillConsgAliasTO.getConsgId());
				   			 consignmentBillingRate.setConsignmentDO(consgDO);
				   			 consignmentBillingRate.setRateCalculatedFor(rateFor);
				   			 consignmentBillingRate=reBillingDAO.saveOrUpdateRateAndStatus(consignmentBillingRate,reBillConsgAliasTO.getConsgNo());
					     }
				   }
				   else if(reBillConsgAliasTO.getBooking_Rate().equals("Y") && reBillConsgAliasTO.getRto_Rate().equals("Y")){
					   consingnment = new ConsignmentDO();
					   //store new calculated rate in new details D/B and need to check whether calculate both rates according to boo and rto flag
					   consgTO=new ConsignmentTO();
					   prepareConsgTO(reBillConsgAliasTO,consgTO);
					   consgTO.setConsgStatus("B");//setting manually consgStatus dont know current status of cn
					   /** Below line is added to stamp booking/event as the current date. This change is done so that the rates of the consignment
					    * are calculated according to the latest contract **/
					   consgTO.setBookingDate(currentDate);
					   //calculating booking rates for the CN
					   ConsignmentRateCalculationOutputTO rateOutput=rateCalculationUniversalService.calculateRateForConsignment(consgTO);
					   consingnment= reBillingDAO.getConsgDetails(reBillConsgAliasTO.getConsgNo());
					   
					   for(ConsignmentBillingRateDO rateDO:consingnment.getConsgRateDtls()){
						   if(rateDO.getRateCalculatedFor().equals("B")){
							   bookforId=rateDO.getConsignmentRateId();
						   }
						   else if(rateDO.getRateCalculatedFor().equals("R")){ //getting consignment rate Id 
							   rtoforId=rateDO.getConsignmentRateId();
						   }
					   }
					   rebillConsgDO=new ReBillingConsignmentDO();
					   prepareRebillingConsgDO(consingnment,rebillConsgDO);//preparing rebilling consg DO
					   rebillConsgDO.setReBillId(reBillingHeaderDO.getReBillingId());
					   if(!StringUtil.isEmptyInteger(reBillConsgAliasTO.getBillingConsignmentId())){
						   rebillConsgDO.setBillingConsgId(reBillConsgAliasTO.getBillingConsignmentId());
					   }
					   Integer rebillConsgId= reBillingDAO.saveOrUpdateRebillingConsignment(rebillConsgDO);//saving rebilling CN and returning auto-generated key
					   
					   reBillingRateDO=new ReBillingConsignmentRateDO();
			   		   prepareRebillingConsgRateCalcDomainConverter(rateOutput,reBillingRateDO);
			   		   reBillingRateDO.setConsignmentId(reBillConsgAliasTO.getConsgId());
			   		   if(!StringUtil.isEmptyInteger(bookforId)){
			   		     reBillingRateDO.setConsignmentRateId(bookforId);
			   		   }
			   		   if(!StringUtil.isEmptyInteger(rebillConsgId)){
			   			reBillingRateDO.setReBillingConsignmentId(rebillConsgId);
			   		   }
			   		   reBillingRateDO.setContractFor("N");
			   		   reBillingRateDO.setRateCalculatedFor(rateFor);
			   		   rebillConsgRateList=new ArrayList<ReBillingConsignmentRateDO>();
		   			   rebillConsgRateList.add(reBillingRateDO);//adding new booking rates 
		   			   
		   			   //preparing rto rate DO 
		   			    consgTO.setConsgStatus(rateForRto);//setting manually consgStatus dont know current status of cn
		   			    /** Below line is added to stamp booking/event as the current date. This change is done so that the rates of the consignment
						  * are calculated according to the latest contract **/
		   			    consgTO.setEventDate(currentDate);
		   			    
					   //calculating booking rates for the CN
					    rateOutput=rateCalculationUniversalService.calculateRateForConsignment(consgTO);
					    reBillingRateDO=new ReBillingConsignmentRateDO();
					    prepareRebillingConsgRateCalcDomainConverter(rateOutput,reBillingRateDO);
					    reBillingRateDO.setConsignmentId(reBillConsgAliasTO.getConsgId());
				   		if(!StringUtil.isEmptyInteger(rtoforId)){
				   		   reBillingRateDO.setConsignmentRateId(rtoforId);
				   		}
				   		if(!StringUtil.isEmptyInteger(rebillConsgId)){
				   		   reBillingRateDO.setReBillingConsignmentId(rebillConsgId);
				   		}
				   		reBillingRateDO.setContractFor("N");
				   		reBillingRateDO.setRateCalculatedFor(rateForRto);
			   			rebillConsgRateList.add(reBillingRateDO);
			   			
			   			if(!StringUtil.isEmptyList(rebillConsgRateList)){
			   				reBillingDAO.saveOrUpdateRebillingRate(rebillConsgRateList);
			   			}
			   			
			   			//getting rebilling  old rates 
			   			//rateAliasList =reBillingDAO.getRebillingConsignmentRateForOld(reBillConsgAliasTO.getConsgId());1275
			   			//rateAliasList =reBillingDAO.getRebillingConsignmentRateForOld(reBillConsgAliasTO.getConsgId());
			   			List<BillingConsignmentRateDO> billConsgRateDos=
			   					reBillingDAO.getRebillingConsgRateFromBillingConsgRate(reBillConsgAliasTO.getBillingConsignmentId());
			   			//List<BillingConsignmentRateDO> billConsgRateDos=reBillingDAO.getRebillingConsgRateFromBillingConsgRate(1275);
			   			if(!StringUtil.isEmptyColletion(billConsgRateDos)){
			   				rebillConsgRateListForOld=prepareRebillingRateDOFromAlias(billConsgRateDos,rebillConsgId);
			   				if(!StringUtil.isEmptyColletion(rebillConsgRateListForOld)){
			   					reBillingDAO.saveOrUpdateRebillingRate(rebillConsgRateListForOld);
			   				}
			   			}
				   }
				   
				   else{
					   consingnment = new ConsignmentDO();
					   //store new calculated rate in new details D/B and need to check whether calculate both rates according to boo and rto flag
					   consgTO=new ConsignmentTO();
					   prepareConsgTO(reBillConsgAliasTO,consgTO);
					   consgTO.setConsgStatus("B");//setting manually consgStatus dont know current status of cn
					   /** Below line is added to stamp booking/event as the current date. This change is done so that the rates of the consignment
					    * are calculated according to the latest contract **/
					   consgTO.setBookingDate(currentDate);
					   //calculating booking rates for the CN
					   ConsignmentRateCalculationOutputTO rateOutput=rateCalculationUniversalService.calculateRateForConsignment(consgTO);
					   consingnment= reBillingDAO.getConsgDetails(reBillConsgAliasTO.getConsgNo());
					   
					   for(ConsignmentBillingRateDO rateDO:consingnment.getConsgRateDtls()){
						   if(rateDO.getRateCalculatedFor().equals("B")){
							   bookforId=rateDO.getConsignmentRateId();
						   }
						   else if(rateDO.getRateCalculatedFor().equals("R")){ //getting consignment rate Id 
							   rtoforId=rateDO.getConsignmentRateId();
						   }
					   }
					   rebillConsgDO=new ReBillingConsignmentDO();
					   prepareRebillingConsgDO(consingnment,rebillConsgDO);//preparing rebilling consg DO
					   rebillConsgDO.setReBillId(reBillingHeaderDO.getReBillingId());
					   if(!StringUtil.isEmptyInteger(reBillConsgAliasTO.getBillingConsignmentId())){
						   rebillConsgDO.setBillingConsgId(reBillConsgAliasTO.getBillingConsignmentId());
					   }
					   Integer rebillConsgId= reBillingDAO.saveOrUpdateRebillingConsignment(rebillConsgDO);//saving rebilling CN and returning auto-generated key
					   
					   reBillingRateDO=new ReBillingConsignmentRateDO();
			   		   prepareRebillingConsgRateCalcDomainConverter(rateOutput,reBillingRateDO);
			   		   reBillingRateDO.setConsignmentId(reBillConsgAliasTO.getConsgId());
			   		   if(!StringUtil.isEmptyInteger(bookforId)){
			   		     reBillingRateDO.setConsignmentRateId(bookforId);
			   		   }
			   		   if(!StringUtil.isEmptyInteger(rebillConsgId)){
			   			reBillingRateDO.setReBillingConsignmentId(rebillConsgId);
			   		   }
			   		   reBillingRateDO.setContractFor("N");
			   		   reBillingRateDO.setRateCalculatedFor(rateFor);
			   		   rebillConsgRateList=new ArrayList<ReBillingConsignmentRateDO>();
		   			   rebillConsgRateList.add(reBillingRateDO);//adding new booking rates 
		   			   if(!StringUtil.isEmptyList(rebillConsgRateList)){
		   				reBillingDAO.saveOrUpdateRebillingRate(rebillConsgRateList);
		   			  }
		   			   
		   			//getting rebilling  old rates 
		   			//Integer id=4786;   
		   			List<BillingConsignmentRateDO> billConsgRateDos=
		   					reBillingDAO.getRebillingConsgRateFromBillingConsgRate(reBillConsgAliasTO.getBillingConsignmentId());
		   			if(!StringUtil.isEmptyColletion(billConsgRateDos)){
		   				rebillConsgRateListForOld=prepareRebillingRateDOFromAlias(billConsgRateDos,rebillConsgId);
		   				if(!StringUtil.isEmptyColletion(rebillConsgRateListForOld)){
		   					reBillingDAO.saveOrUpdateRebillingRate(rebillConsgRateListForOld);
		   				}
		   			}
				   }
				 }catch(Exception e){
					 LOGGER.error("ReBillingServiceImpl::getRebillingDetails::----->"+e+"Exception occurs for consg"+reBillConsgAliasTO.getConsgNo());
				 }
			   }
			   
			   //updating rebilling Header calculated flag to 'Y'
	   			reBillingDAO.updateRebillingHeaderFlag(reBillingHeaderDO.getReBillingNumber());
		  }
	   }
	  }catch(Exception e){
		  LOGGER.error("ReBillingServiceImpl::getRebillingDetails::----->"+e);
 	 }
	  long endMilliSeconds = System.currentTimeMillis();
	  long diff = endMilliSeconds - startMilliseconds;
	  LOGGER.debug("ReBillingServiceImpl::getRebillingDetails::End------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
  }
  
 public void prepareConsgTO(ReBillConsgAliasTO reBillConsgAliasTO,ConsignmentTO consgTO)throws CGBusinessException, CGSystemException {
	 LOGGER.debug("ReBillingServiceImpl::prepareConsgTO::START----->");
	 if(!StringUtil.isEmptyInteger(reBillConsgAliasTO.getConsgId())){
		 consgTO.setConsgId(reBillConsgAliasTO.getConsgId());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getConsgNo())){
		 consgTO.setConsgNo(reBillConsgAliasTO.getConsgNo());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getConsgStatus())){
		 consgTO.setConsgStatus(reBillConsgAliasTO.getConsgStatus().toString());
	 }
	 
	 if(!StringUtil.isEmptyInteger(reBillConsgAliasTO.getOperaOffice())){
		 consgTO.setOperatingOffice(reBillConsgAliasTO.getOperaOffice());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getFinalWt())){
		 consgTO.setFinalWeight(reBillConsgAliasTO.getFinalWt());
	 }
	 
	 if(!StringUtil.isEmptyInteger(reBillConsgAliasTO.getCustomer())){
		 consgTO.setCustomer(reBillConsgAliasTO.getCustomer());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getConsgCode())){
		 ConsignmentTypeTO typeTO=new ConsignmentTypeTO();
		 typeTO.setConsignmentCode(reBillConsgAliasTO.getConsgCode());
		 consgTO.setTypeTO(typeTO);
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getDestPincode())){
		 PincodeTO destPincode=new PincodeTO();
		 destPincode.setPincode(reBillConsgAliasTO.getDestPincode());
		 consgTO.setDestPincode(destPincode);
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getProductCode())){
		 ProductTO productTO=new ProductTO();
		 productTO.setProductCode(reBillConsgAliasTO.getProductCode());
		 consgTO.setProductTO(productTO);
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getInsuredCode())){
		 InsuredByTO insuredByTO=new InsuredByTO();
		 insuredByTO.setInsuredByCode(reBillConsgAliasTO.getInsuredCode().toString());
		 consgTO.setInsuredByTO(insuredByTO);
	 }
	 
	 CNPricingDetailsTO consgPriceDtls =new CNPricingDetailsTO();
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getDeclareValue())){
		 consgPriceDtls.setDeclaredvalue(reBillConsgAliasTO.getDeclareValue());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getSplChg())){
		 consgPriceDtls.setSplChg(reBillConsgAliasTO.getSplChg());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getDiscount())){
		 consgPriceDtls.setDiscount(reBillConsgAliasTO.getDiscount());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getLcAmt())){
		 consgPriceDtls.setLcAmount(reBillConsgAliasTO.getLcAmt());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getCodAmt())){
		 consgPriceDtls.setCodAmt(reBillConsgAliasTO.getCodAmt());
	 }
	 
	 if(!StringUtil.isEmptyDouble(reBillConsgAliasTO.getTopayAmt())){
		 consgPriceDtls.setTopayChg(reBillConsgAliasTO.getTopayAmt());
	 }
	 if(!StringUtil.isNull(reBillConsgAliasTO.getServiceOn())){
		 consgPriceDtls.setServicesOn(reBillConsgAliasTO.getServiceOn());
	 }
	 
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getRateType())){
		 consgPriceDtls.setRateType(reBillConsgAliasTO.getRateType());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getEbPrefCode())){
		 consgPriceDtls.setEbPreferencesCodes(reBillConsgAliasTO.getEbPrefCode());
	 }
	 
	 consgTO.setConsgPriceDtls(consgPriceDtls);
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getEventDate())){
		 consgTO.setEventDate(reBillConsgAliasTO.getEventDate());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getBookDate())){
		 consgTO.setBookingDate(reBillConsgAliasTO.getBookDate());
	 }
	 
	 if(!StringUtil.isNull(reBillConsgAliasTO.getConsgStatus())){
		 consgTO.setConsgStatus(reBillConsgAliasTO.getConsgStatus().toString());
	 }
	 LOGGER.debug("ReBillingServiceImpl::prepareConsgTO::End----->"); 
 }
 
 private void  prepareConsgRateCalcDomainConverter(ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO,ConsignmentBillingRateDO consignmentBillingRateDO) throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	 LOGGER.debug("ReBillingServiceImpl::prepareConsgRateCalcDomainConverter::START------------>:::::::");
		 if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){

			 PropertyUtils.copyProperties(consignmentBillingRateDO, consignmentRateCalculationOutputTO);
			 consignmentBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
			 consignmentBillingRateDO.setUpdatedBy(1);
			 consignmentBillingRateDO.setBilled("N");
		 }
		
		
		 LOGGER.debug("ReBillingServiceImpl::prepareConsgRateCalcDomainConverter::END------------>:::::::");
	}
 
 private void prepareRebillingConsgRateCalcDomainConverter(ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO,ReBillingConsignmentRateDO reBillingRateDO) throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgRateCalcDomainConverter::START------------>:::::::");
	 if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){
		 PropertyUtils.copyProperties(reBillingRateDO, consignmentRateCalculationOutputTO);
		 reBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
		 reBillingRateDO.setUpdatedBy(1);
		 reBillingRateDO.setCreatedDate(DateUtil.getCurrentDate());
		 reBillingRateDO.setCreatedBy(1);
	 }
	
	
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgRateCalcDomainConverter::END------------>:::::::");
 }
 
 private void prepareRebillingConsgDO(ConsignmentDO consingnment,ReBillingConsignmentDO rebillConsgDO )throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgDO::START------------>:::::::");
	 if(!StringUtil.isNull(consingnment)){
		 PropertyUtils.copyProperties(rebillConsgDO, consingnment);
		 if(!StringUtil.isNull(consingnment.getDestPincodeId())){
			 PincodeDO pincode = new PincodeDO();
			 PropertyUtils.copyProperties(pincode, consingnment.getDestPincodeId());
			 rebillConsgDO.setDestPincodeDO(pincode);
		 }
          
		 if(!StringUtil.isNull(consingnment.getConsgType())){
			 ConsignmentTypeDO type = new ConsignmentTypeDO();
			 PropertyUtils.copyProperties(type, consingnment.getConsgType());
			 rebillConsgDO.setConsignmentTypeDO(type);
		 }
          
		 if(!StringUtil.isEmptyInteger(consingnment.getProductId())){
			 ProductDO product=new ProductDO();
			 product.setProductId(consingnment.getProductId());
			 rebillConsgDO.setProductDO(product);
		 }
		 
		 if(!StringUtil.isNull(consingnment.getUpdatedProcess())){
			 ProcessDO process=new ProcessDO();
			 PropertyUtils.copyProperties(process, consingnment.getUpdatedProcess());
			 rebillConsgDO.setUpdatedProcessDO(process);
		 }
		 
		 if(!StringUtil.isNull(consingnment.getCnContentId())){
			 CNContentDO content=new CNContentDO();
			 PropertyUtils.copyProperties(content, consingnment.getCnContentId());
			 rebillConsgDO.setCnContentDO(content);
		 }
		 
		 if(!StringUtil.isNull(consingnment.getCnPaperWorkId())){
			 CNPaperWorksDO paperwork=new CNPaperWorksDO();
			 PropertyUtils.copyProperties(paperwork, consingnment.getCnPaperWorkId());
			 rebillConsgDO.setCnPaperWorksDO(paperwork);
		 }
		 
		 if(!StringUtil.isNull(consingnment.getInsuredBy())){
			 InsuredByDO insuredBy=new InsuredByDO();
			 PropertyUtils.copyProperties(insuredBy, consingnment.getInsuredBy());
			 rebillConsgDO.setInsuredByDO(insuredBy);
			 
		 }
		 
		 if(!StringUtil.isNull(consingnment.getConsignor())){
			 ConsigneeConsignorDO consignor=new ConsigneeConsignorDO();
			 PropertyUtils.copyProperties(consignor, consingnment.getConsignor());
			 rebillConsgDO.setConsignor(consignor);
		 }
		 
		 
		 if(!StringUtil.isNull(consingnment.getConsignee())){
			 ConsigneeConsignorDO consignee=new ConsigneeConsignorDO();
			 PropertyUtils.copyProperties(consignee, consingnment.getConsignee());
			 rebillConsgDO.setConsignee(consignee);
		 }
		 
	 }
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgDO::END------------>:::::::");
 }
 
 
 public  List<ReBillingConsignmentRateDO> prepareRebillingRateDOFromAlias(List<BillingConsignmentRateDO> rateList,Integer rebillConsgId)throws CGBusinessException, CGSystemException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgDO::END------------>:::::::");
	 List<ReBillingConsignmentRateDO> rebillingRateForOld=new ArrayList<ReBillingConsignmentRateDO>();
	 if(!StringUtil.isEmptyColletion(rateList)){
		 for(BillingConsignmentRateDO billingRate:rateList){
			 ReBillingConsignmentRateDO rebillingRateDO=new ReBillingConsignmentRateDO();
			 //reBillingRateAliasTO.setRateCalculatedFor(rateCalculatedFor)
			 PropertyUtils.copyProperties(rebillingRateDO, billingRate);
			 rebillingRateDO.setReBillingConsignmentId(rebillConsgId);
			 //rebillingRateDO.setRateCalculatedFor(reBillingRateAliasTO.getRateCalculated().toString());
			 rebillingRateDO.setContractFor("O");
			 rebillingRateDO.setCreatedDate(DateUtil.getCurrentDate());
			 rebillingRateDO.setCreatedBy(1);
			 rebillingRateForOld.add(rebillingRateDO);
		 }
	 }
	 
	 LOGGER.debug("ReBillingServiceImpl::prepareRebillingConsgDO::END------------>:::::::");
	 return rebillingRateForOld;
	 
 }

@Override
public void checkOldContractAvailability(Integer bookingOfficeId,
		Integer customerId, Date fromDate) throws CGBusinessException,
		CGSystemException {
	Integer contractCount = reBillingDAO.checkOldContractAvailability(bookingOfficeId, customerId, fromDate);
	if (contractCount == 0){
		throw new CGBusinessException(AdminErrorConstants.NO_CONTRACT_AVAILABLE_IN_BOOKING_PERIOD);
	}
	
}
 

}