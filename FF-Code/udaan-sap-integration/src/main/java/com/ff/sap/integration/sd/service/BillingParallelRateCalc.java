package com.ff.sap.integration.sd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

public class BillingParallelRateCalc implements Callable<Map<String,ConsignmentRateCalculationOutputTO>> {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BillingParallelRateCalc.class);
	
	private List<ConsignmentTO> consignmentList;
	private Map<String,ConsignmentRateCalculationOutputTO> rateOutputList=new HashMap<String,ConsignmentRateCalculationOutputTO>();
	//private Map<String,ConsignmentRateCalculationOutputTO> rateOutputList;
    private RateCalculationUniversalService rateCalculationUniversalService;
    private SAPIntegrationDAO integrationDAO;
    
    BillingParallelRateCalc(){};
    public BillingParallelRateCalc(List<ConsignmentTO> consignmentList, RateCalculationUniversalService rateCalculationUniversalService, SAPIntegrationDAO integrationDAO) {
        this.consignmentList = consignmentList;
        this.rateCalculationUniversalService=rateCalculationUniversalService; 
        this.integrationDAO = integrationDAO;
        
    }

	/*
    *//**
	 * @param rateCalculationUniversalService the rateCalculationUniversalService to set
	 *//*
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}*/
	
	@Override
    public Map<String ,ConsignmentRateCalculationOutputTO> call() throws CGBusinessException, CGSystemException {
		long startMilliseconds = System.currentTimeMillis();
		LOGGER.debug("BillingParallelRateCalc::call::START----->"+Thread.currentThread().getName() + ":::::::startMilliseconds"
				+ startMilliseconds);
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO=null;
		int rateCalculatedFor = 0;
		int exceptionOccured = 0;
		int rateNotCalculatedFor = 0;
		List<String> consgNo = new ArrayList<String>();
		for(ConsignmentTO consignmentTO : consignmentList) {
			consignmentRateCalculationOutputTO = null;
			try{
				String bookingType=consignmentTO.getBookingType();
				if(!bookingType.equals(CommonConstants.CASH_BOOKING) && !bookingType.equals(CommonConstants.EMOTIONAL_BOND_BOOKING) 
						&& !bookingType.equals(CommonConstants.FOC_BOOKING) 
						&& consignmentTO.getChangedAfterBillingWtDest().equals(CommonConstants.YES) 
						// As per new requirement dated 26 August 2015 by Rajesh
						/*&& consignmentTO.getTypeTO().getConsignmentCode().equals(CommonConstants.CONSIGNMENT_TYPE_PARCEL) */
						&& consignmentTO.getBOOKING_RATE_BILLED().equals(CommonConstants.NO)){
					consignmentRateCalculationOutputTO=rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
					LOGGER.debug("BillingParallelRateCalc::call::Rate calculated for CN----->"+consignmentTO.getConsgNo());
					rateCalculatedFor++;
					rateOutputList.put(consignmentTO.getConsgNo(), consignmentRateCalculationOutputTO);
				} else {
					LOGGER.debug("BillingParallelRateCalc::call::Rate NOT  calculated for CN----->"+consignmentTO.getConsgNo());
					rateNotCalculatedFor++;
					rateOutputList.put(consignmentTO.getConsgNo(), null);
				}
			}
			catch(Exception e){
				 LOGGER.error("BillingParallelRateCalc::call::Exception----->"+e +"Exception occur while calc rate for CN "+consignmentTO.getConsgNo());
				 exceptionOccured++;
				 consgNo.add(consignmentTO.getConsgNo());
			} /*finally {
				rateOutputList.add(consignmentRateCalculationOutputTO);
			}*/
		}
		LOGGER.debug("CN Processing Information - rateCalculatedFor: "
				+ rateCalculatedFor + ", rateNotCalculatedFor: "
				+ rateNotCalculatedFor + ", exceptionOccured: "
				+ exceptionOccured);
		long endMilliSeconds = System.currentTimeMillis();
		long diff = endMilliSeconds - startMilliseconds;
		LOGGER.debug("BillingParallelRateCalc::call::END------------>::::::: endMilliSeconds:["
				+ endMilliSeconds
				+ "] Difference"
				+ (diff)
				+ " Difference IN HH:MM:SS format ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		if (!CGCollectionUtils.isEmpty(consgNo)){
			LOGGER.error("BillingParallelRateCalc::call:: Updated Status to PFB for Error Consignments----->");
			integrationDAO.UpdateConsgnBillStatusByConsgnNo(consgNo, "PFB");
		}
        return rateOutputList;
    }   

}

