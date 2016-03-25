package com.ff.universe.drs.scheduler;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DrsCollectionIntegrationWrapperDO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.drs.service.DeliveryUniversalService;

/**
 * @author mohammes
 *
 */
public class DrsExpenseIntegratorScheduler extends QuartzJobBean{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DrsExpenseIntegratorScheduler.class);
	
	private transient DeliveryUniversalService deliveryUniversalService;
	
	/**
	 * @return the deliveryUniversalService
	 */
	public DeliveryUniversalService getDeliveryUniversalService() {
		return deliveryUniversalService;
	}
	/**
	 * @param deliveryUniversalService the deliveryUniversalService to set
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}
	public volatile  Map<String,Integer> paymentTypeMap=null;
	public volatile Set<String> cnList=null;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			{
		cnList=null;
		LOGGER.debug("DrsExpenseIntegratorScheduler ::Scheduler ::START ");
		try {
			drsExpenseIntegrator();
			drsExpenseIntegratorForBA();
			drsExpenseIntegratorForMisc();
		}catch (HttpException e) {
			LOGGER.error("DrsExpenseIntegratorScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("DrsExpenseIntegratorScheduler::executeInternal::ClassNotFoundException::" ,e);
		} catch (IOException e) {
			LOGGER.error("DrsExpenseIntegratorScheduler::executeInternal::IOException::"  ,e);
		} catch(Exception e) {
			LOGGER.error("DrsExpenseIntegratorScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.debug("DrsExpenseIntegratorScheduler ::Scheduler ::END ");
		
	}
	/**
	 * stockExpenseIntegrator
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void drsExpenseIntegrator() throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException{
		
		List<DrsCollectionIntegrationWrapperDO> deliveryDtlsDoList =deliveryUniversalService.getDeliveryDetailsForCollection(UniversalDeliveryContants.QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION);
		if(!CGCollectionUtils.isEmpty(deliveryDtlsDoList)){
			cnList= new HashSet<>(deliveryDtlsDoList.size());
			if(CGCollectionUtils.isEmpty(paymentTypeMap)){
				paymentTypeMap=deliveryUniversalService.getPaymentModeTypeForCollection();
			}
			for(DrsCollectionIntegrationWrapperDO deliveryDtlsDo :deliveryDtlsDoList){
				try {
					if(!StringUtil.isStringEmpty(deliveryDtlsDo.getDrsConsgNumber())){
						if(!cnList.contains(deliveryDtlsDo.getDrsConsgNumber())){
							cnList.add(deliveryDtlsDo.getDrsConsgNumber());
							deliveryUniversalService.processDeliveryDetailsForCollectionIntegration(deliveryDtlsDo, paymentTypeMap);
							
						}
					}else{
						LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Consg DO Not Exist in DRS ");
					}
				} catch (Exception e) {
					LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Exception:: ",e);
				}
			}
		}else{
			LOGGER.info("DrsExpenseIntegratorScheduler ::Scheduler ::DRS details doesnot exist ");
		}
	}
	private void drsExpenseIntegratorForMisc() throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException{
		List<DrsCollectionIntegrationWrapperDO> deliveryDtlsDoList =deliveryUniversalService.getDeliveryDetailsForCollection(UniversalDeliveryContants.QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION_FOR_MISC_CHARGES);
		if(!CGCollectionUtils.isEmpty(deliveryDtlsDoList)){
			for(DrsCollectionIntegrationWrapperDO deliveryDtlsDo :deliveryDtlsDoList){
				try {
					if(!StringUtil.isStringEmpty(deliveryDtlsDo.getDrsConsgNumber())){
						if(!cnList.contains(deliveryDtlsDo.getDrsConsgNumber())){
							cnList.add(deliveryDtlsDo.getDrsConsgNumber());
							deliveryUniversalService.processDeliveryDetailsForCollectionIntegration(deliveryDtlsDo, paymentTypeMap);
							
						}
					}else{
						LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Consg DO Not Exist in DRS ");
					}
				} catch (Exception e) {
					LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Exception:: ",e);
				}
			}
		}else{
			LOGGER.info("DrsExpenseIntegratorScheduler ::Scheduler ::DRS details doesnot exist ");
		}
		
	}
	private void drsExpenseIntegratorForBA() throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException{
		List<DrsCollectionIntegrationWrapperDO> deliveryDtlsDoList =deliveryUniversalService.getDeliveryDetailsForCollection(UniversalDeliveryContants.QRY_GET_DRS_DTLS_FOR_COLLECTOIN_INTEGRATION_FOR_BA_AMOUNT);
		
		if(!CGCollectionUtils.isEmpty(deliveryDtlsDoList)){
			for(DrsCollectionIntegrationWrapperDO deliveryDtlsDo :deliveryDtlsDoList){
				try {
					if(!StringUtil.isStringEmpty(deliveryDtlsDo.getDrsConsgNumber())){
						if(!cnList.contains(deliveryDtlsDo.getDrsConsgNumber())){
							cnList.add(deliveryDtlsDo.getDrsConsgNumber());
							deliveryUniversalService.processDeliveryDetailsForCollectionIntegration(deliveryDtlsDo, paymentTypeMap);
							
						}
					}else{
						LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Consg DO Not Exist in DRS ");
					}
				} catch (Exception e) {
					LOGGER.error("DrsExpenseIntegratorScheduler ::Scheduler ::Exception:: ",e);
				}
			}
		}else{
			LOGGER.info("DrsExpenseIntegratorScheduler ::Scheduler ::DRS details doesnot exist ");
		}
	}
	

}
