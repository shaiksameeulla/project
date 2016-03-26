package com.ff.sap.integration.sd.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.sap.integration.consignmentratecalculation.dao.ConsignmentRateCalculationDAO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

public class ParallelRateCalculation implements Callable<Map<String,ConsignmentRateCalculationOutputTO>> {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(BillingParallelRateCalc.class);
	
	private List<ConsignmentTO> consignmentList;
	//private Map<String,ConsignmentRateCalculationOutputTO> rateOutputList;
    private RateCalculationUniversalService rateCalculationUniversalService;
    private ConsignmentRateCalculationDAO consignmentRateCalculationDAO;
    private String rateCalculatedfor;
    
    ParallelRateCalculation(){};
    public ParallelRateCalculation(List<ConsignmentTO> consignmentList, RateCalculationUniversalService rateCalculationUniversalService, ConsignmentRateCalculationDAO consignmentRateCalculationDAO, String rateCalculatedfor) {
        this.consignmentList = consignmentList;
        this.rateCalculationUniversalService=rateCalculationUniversalService; 
        this.consignmentRateCalculationDAO = consignmentRateCalculationDAO;
        this.rateCalculatedfor = rateCalculatedfor;
        
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
		for (ConsignmentTO consgTO : consignmentList) {
			try {
				// 2A. calculating consignment rate
				consgTO.setConsgStatus(rateCalculatedfor);
				ConsignmentBillingRateDO consignmentBillingRateDO = calculateConsgRate(consgTO, rateCalculatedfor);
				// Get Consignment
				ConsignmentDO consgDO = consignmentRateCalculationDAO.getConsignmentByConsgNo(consgTO.getConsgNo());
				
				//Set Rate Details
				Set<ConsignmentBillingRateDO> consgRateDtls = consgDO.getConsgRateDtls();
				Set<ConsignmentBillingRateDO> consgRateDtlsFinal = new HashSet<ConsignmentBillingRateDO>();
				boolean isBookRateAdded = false;
				boolean isRTOAdded = false;
				if(!CGCollectionUtils.isEmpty(consgRateDtls)){
					for (ConsignmentBillingRateDO consignmentBillingRateDO2 : consgRateDtls) {
						if(!isBookRateAdded && consignmentBillingRateDO2.getRateCalculatedFor().equalsIgnoreCase(consignmentBillingRateDO.getRateCalculatedFor()) 
								&& rateCalculatedfor.equalsIgnoreCase("B")){
							consignmentBillingRateDO.setConsignmentRateId(consignmentBillingRateDO2.getConsignmentRateId());
							consignmentBillingRateDO.setConsignmentDO(consgDO);
							consgRateDtlsFinal.add(consignmentBillingRateDO);
							isBookRateAdded = true;
						} else if (!isRTOAdded && consignmentBillingRateDO2.getRateCalculatedFor().equalsIgnoreCase(consignmentBillingRateDO.getRateCalculatedFor()) 
								&& rateCalculatedfor.equalsIgnoreCase("R")) {
							consignmentBillingRateDO.setConsignmentRateId(consignmentBillingRateDO2.getConsignmentRateId());
							consignmentBillingRateDO.setConsignmentDO(consgDO);
							consgRateDtlsFinal.add(consignmentBillingRateDO);
							isRTOAdded = true;
						}  else if (!isBookRateAdded && consignmentBillingRateDO2.getRateCalculatedFor().equalsIgnoreCase("B")){
							consgRateDtlsFinal.add(consignmentBillingRateDO2);
							isBookRateAdded = true;
						} else if (!isRTOAdded && consignmentBillingRateDO2.getRateCalculatedFor().equalsIgnoreCase("R")){
							consgRateDtlsFinal.add(consignmentBillingRateDO2);
							isRTOAdded = true;
						}
					}
					if (rateCalculatedfor.equalsIgnoreCase("B") && !isBookRateAdded){
						consignmentBillingRateDO.setConsignmentDO(consgDO);
						consgRateDtlsFinal.add(consignmentBillingRateDO);
						isBookRateAdded = true;
					}  else if (rateCalculatedfor.equalsIgnoreCase("R") && !isRTOAdded){
						consignmentBillingRateDO.setConsignmentDO(consgDO);
						consgRateDtlsFinal.add(consignmentBillingRateDO);
						isRTOAdded = true;
					}
				} else {
					consignmentBillingRateDO.setConsignmentDO(consgDO);
					consgRateDtlsFinal.add(consignmentBillingRateDO);
				}
				consgDO.setConsgRateDtls(consgRateDtlsFinal);
				
				// 2B. save consignment rate
				consignmentRateCalculationDAO.saveOrUpdateConsgDO(consgDO);
				/*consignmentRateCalculationDAO
						.saveOrUpdateConsgDtlsWhoseRateIsCalculated(consignmentBillingRateDO);*/
				/*Update Consignment Billing Status */
				if (rateCalculatedfor.equalsIgnoreCase("R")) {
					List<String> consgNos = new ArrayList<String>();
					consgNos.add(consgTO.getConsgNo());
					consignmentRateCalculationDAO.UpdateConsgnBillStatusByConsgnNo(consgNos, "TBB");
				}
				LOGGER.debug("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: Rate Saved  for ==>" + consgTO.getConsgNo());
			} catch (Exception e) {
				LOGGER.error("ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: Rate Not calculated for ==>" + consgTO.getConsgNo());
				//Set Consignment flag as PFB
				if (rateCalculatedfor.equalsIgnoreCase("B")) {
					List<String> consgNos = new ArrayList<String>();
					consgNos.add(consgTO.getConsgNo());
					consignmentRateCalculationDAO
							.UpdateConsgnBillStatusByConsgnNo(consgNos,
									"PFB");
				} else {
					//Update DT_FROM_OPSMAN = R in booking table
					consignmentRateCalculationDAO.updateBookingOpsmanStatus(consgTO.getConsgNo());					
				}

				LOGGER.error(
						"Exception occurs in ConsignmentRateCalculationServiceImpl :: executeConsignmentRateCalculation() :: ..EXCEPTION : ",
						e);
			}
		} // END of FOR LOOP
		
		return null;
	}   

	
	/**
	 * @param consgTO
	 * @param rateCalculatedfor
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private ConsignmentBillingRateDO calculateConsgRate(ConsignmentTO consgTO, String rateCalculatedfor)
			throws CGBusinessException, CGSystemException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		LOGGER.debug("ConsignmentRateCalculationServiceImpl :: calculateConsgRate() :: START");
		ConsignmentBillingRateDO consignmentBillingRateDO =  null;
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO = null;
		ConsignmentDO consgDO = null;
		if (!StringUtil.isEmptyInteger(consgTO.getConsgId())){
			 consignmentRateCalculationOutputTO = rateCalculationUniversalService
					.calculateRateForConsignment(consgTO);
			consignmentBillingRateDO = new ConsignmentBillingRateDO();
			PropertyUtils.copyProperties(consignmentBillingRateDO,
					consignmentRateCalculationOutputTO);
			consignmentBillingRateDO.setUpdatedDate(DateUtil.getCurrentDate());
			consignmentBillingRateDO.setUpdatedBy(4);
			consignmentBillingRateDO.setBilled("N");
			consgDO = new ConsignmentDO();
			consgDO.setConsgId(consgTO.getConsgId());
		    consignmentBillingRateDO.setConsignmentDO(consgDO);
		    consignmentBillingRateDO.setRateCalculatedFor(rateCalculatedfor);
		}
		LOGGER.debug("ConsignmentRateCalculationServiceImpl :: calculateConsgRate() :: END");
		return consignmentBillingRateDO;
	}
}

