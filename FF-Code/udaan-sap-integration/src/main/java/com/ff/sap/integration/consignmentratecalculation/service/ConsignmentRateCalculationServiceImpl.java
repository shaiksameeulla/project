package com.ff.sap.integration.consignmentratecalculation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.geography.PincodeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.sap.integration.consignmentratecalculation.dao.ConsignmentRateCalculationDAO;
import com.ff.sap.integration.sd.service.ParallelRateCalculation;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.google.common.collect.Lists;

/**
 * @author hkansagr
 * 
 */
public class ConsignmentRateCalculationServiceImpl implements
		ConsignmentRateCalculationService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentRateCalculationServiceImpl.class);

	/** The consignmentRateCalculationDAO. */
	private ConsignmentRateCalculationDAO consignmentRateCalculationDAO;

	/** The rateCalculationUniversalService. */
	private RateCalculationUniversalService rateCalculationUniversalService;

	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;
	
	private transient ParallelRateCalculation parallelRateCalculation;

	/**
	 * @param consignmentRateCalculationDAO
	 *            the consignmentRateCalculationDAO to set
	 */
	public void setConsignmentRateCalculationDAO(
			ConsignmentRateCalculationDAO consignmentRateCalculationDAO) {
		this.consignmentRateCalculationDAO = consignmentRateCalculationDAO;
	}

	/**
	 * @param rateCalculationUniversalService
	 *            the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	/**
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	@Override
	public void executeConsignmentRateCalculation(String rateCalculatedfor) throws CGBusinessException,
			CGSystemException, InterruptedException {
		LOGGER.debug("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: START");
		List<ConsignmentTO> consignmentTOs = null;
		List<ConsignmentBilling> dbconsignmentDOs= null;
		List<ParallelRateCalculation> cnThreads=new ArrayList<ParallelRateCalculation>();
		Integer maxThreadLimit = 25;
	
		if(rateCalculatedfor.equalsIgnoreCase("B")){
			//Booking Consignment
			dbconsignmentDOs= consignmentRateCalculationDAO.getConsgDtlsWhoseRateIsNull();
		} else {
			// RTO Consignment
			dbconsignmentDOs= consignmentRateCalculationDAO.getRTOConsgDtlsWhoseRateIsNull();
		}
		
		LOGGER.debug("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: Total Null rate ==>" + dbconsignmentDOs.size());

		// 2. calculate rate 1 by 1 and save
		if (!StringUtil.isEmptyColletion(dbconsignmentDOs)) {
			LOGGER.debug("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() ::calcRateForCNInConsgRate:: Total Consignments For Rate calc :: ----->"
					+ dbconsignmentDOs.size());
			 consignmentTOs = convertBillingConsignmentDOsToTOs(dbconsignmentDOs);
		}
		if (!CGCollectionUtils.isEmpty(consignmentTOs)) {
		if(consignmentTOs.size()>10){
			List<List<ConsignmentTO>> partitions = null;
			if(consignmentTOs.size() > maxThreadLimit){
				partitions = Lists.partition(consignmentTOs, consignmentTOs.size()/maxThreadLimit);
			} else {
				partitions = Lists.partition(consignmentTOs, 1);
			}
		   for(List<ConsignmentTO> consignmentSubList : partitions) {
			   parallelRateCalculation=new ParallelRateCalculation(consignmentSubList, this.rateCalculationUniversalService, this.consignmentRateCalculationDAO, rateCalculatedfor);
			   cnThreads.add(parallelRateCalculation);
			}
			 ExecutorService executor = Executors.newFixedThreadPool(partitions.size());
			 long startTmMulti=System.currentTimeMillis();  
			 //Calculating rate using Multithreading
			 executor.invokeAll(cnThreads);
			 executor.shutdown();
			 long endTmMulti=System.currentTimeMillis();
			 LOGGER.debug("BillingSummaryCreationScheduler - :: SAPBillingRateCalculationScheduler  :: BillingCommonServiceImpl::calcRateForCNInConsgRate::elapsedTime for MultiThreading----->"+(endTmMulti-startTmMulti));
		} 
		}
		LOGGER.debug("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: END");
	}

	/**
	 * @param consignmentDOs
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<ConsignmentTO> convertBillingConsignmentDOsToTOs(
			List<ConsignmentBilling> consignmentDOs)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentTO> consignmentTOs = null;
		ConsignmentTO consignmentTO = null;
		if (!StringUtil.isEmptyColletion(consignmentDOs)) {
			consignmentTOs = new ArrayList<>(consignmentDOs.size());
			for (ConsignmentBilling consignmentDO : consignmentDOs) {
				consignmentTO = new ConsignmentTO();
				consignmentTO.setBookingType(consignmentDO.getBOOKING_TYPE());
				if (!StringUtils.isEmpty(consignmentDO.getBOOKING_TYPE())) {
					consignmentTO.setBookingType(consignmentDO
							.getBOOKING_TYPE());
					CGObjectConverter.createToFromDomain(consignmentDO,
							consignmentTO);
					if (!StringUtil.isNull(consignmentDO.getBOOKING_DATE())) {
						consignmentTO.setBookingDate(consignmentDO
								.getBOOKING_DATE());
					}

					if (!StringUtils.isEmpty(consignmentDO.getPRODUCT_CODE())) {
						ProductTO product = new ProductTO();
						product.setProductCode(consignmentDO.getPRODUCT_CODE());
						consignmentTO.setProductTO(product);
					}

					if (!StringUtil.isNull(consignmentDO.getEventDate())) {
						consignmentTO
								.setEventDate(consignmentDO.getEventDate());
					}

					if (!StringUtil.isNull(consignmentDO.getDestPincodeId())) {
						PincodeTO destPin = new PincodeTO();
						CGObjectConverter.createToFromDomain(
								consignmentDO.getDestPincodeId(), destPin);
						consignmentTO.setDestPincode(destPin);
					}

					if (!StringUtil.isNull(consignmentDO.getConsgType())) {
						ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
						CGObjectConverter.createToFromDomain(
								consignmentDO.getConsgType(), typeTO);
						consignmentTO.setTypeTO(typeTO);
					}

					if (!StringUtil.isNull(consignmentDO.getInsuredBy())) {
						InsuredByTO insuredBy = new InsuredByTO();
						CGObjectConverter.createToFromDomain(
								consignmentDO.getInsuredBy(), insuredBy);
						consignmentTO.setInsuredByTO(insuredBy);
					}

					CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
					if (!StringUtil.isNull(consignmentDO.getRateType())) {
						cNPricingDetailsTO.setRateType(consignmentDO
								.getRateType());
					}
					if (!StringUtil.isEmptyDouble(consignmentDO.getDiscount())) {
						cNPricingDetailsTO.setDiscount(consignmentDO
								.getDiscount());
					}

					if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
						cNPricingDetailsTO.setTopayChg(consignmentDO
								.getTopayAmt());
					}

					if (!StringUtil.isEmptyDouble(consignmentDO.getSplChg())) {
						cNPricingDetailsTO.setSplChg(consignmentDO.getSplChg());
					}

					if (!StringUtil.isEmptyDouble(consignmentDO
							.getDeclaredValue())) {
						cNPricingDetailsTO.setDeclaredvalue(consignmentDO
								.getDeclaredValue());
					}

					if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
						cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
					}

					if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
						cNPricingDetailsTO.setLcAmount(consignmentDO
								.getLcAmount());
					}

					if (!StringUtils.isEmpty(consignmentDO
							.getEbPreferencesCodes())) {
						cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO
								.getEbPreferencesCodes());
					}
					if (!StringUtils.isEmpty(consignmentDO.getServicedOn())) {
						cNPricingDetailsTO.setServicesOn(consignmentDO
								.getServicedOn());
					}

					consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
					consignmentTOs.add(consignmentTO);
				}
			}

		}

		return consignmentTOs;
	}

}
