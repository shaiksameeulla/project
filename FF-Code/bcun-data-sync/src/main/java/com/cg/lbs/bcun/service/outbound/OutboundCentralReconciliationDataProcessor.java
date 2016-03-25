package com.cg.lbs.bcun.service.outbound;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.cg.lbs.bcun.utility.ReconcillationPropertyReader;
import com.ff.domain.bcun.reconcillation.BcunReconcillationDO;

/**
 * @author bmodala
 * This Data processor take the count of records from the central
 * and inserting into respective reconcillation table and create the blob file for mismatched records.
 */
public class OutboundCentralReconciliationDataProcessor {


	/**
	 * BCUN service to process the request
	 */
	private BcunDatasyncService bcunService;

	/**
	 * LOGGER to log the process execution information
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundCentralReconciliationDataProcessor.class);

	/**
	 * Proceed scheduling task
	 * @throws HttpException while posting data to central server
	 * @throws ClassNotFoundException while loading/formating instance of configured class 
	 * @throws IOException while posting request to central server
	 */
	public  void createCentralBlob()  {
		LOGGER.debug("OutboundCentralReconciliationDataProcessor::createCentralBlob::start=====>");
		/*try {
			//Getting reconciliation date from central Db.
			getReconcillationDataFromCentral();

		} catch (Exception e) {
			LOGGER.error("OutboundCentralReconciliationDataProcessor::preparing reconcillation statistics::Exception=====>",e);
		}*/
		try {//Validating mismatched records
			validationOnReconcillationData();
		} catch (CGSystemException | CGBusinessException | IOException e) {
			LOGGER.error("OutboundCentralReconciliationDataProcessor::createCentralBlob::Exception=====>",e);
		}
		LOGGER.debug("OutboundCentralReconciliationDataProcessor::createCentralBlob::end=====>");
	}

	/**
	 * This Data processor take the count of records from the central
	 * and inserting into respective reconcillation table.
	 */
	//@SuppressWarnings("unused")
	/*private List<BcunReconcillationDO> getReconcillationDataFromCentral() {
		LOGGER.debug("OutboundCentralReconciliationDataProcessor::getReconcillationDataFromCentral::start=====>");
		List<ReconcillationConfigPropertyTO> configProcess = ReconcillationPropertyReader.getReconcillationConfigProperty();
		List<BcunReconcillationDO> reconcellationDOs =null;
		for (ReconcillationConfigPropertyTO reconcillationConfigPropertyTO : configProcess) {
			try {
				bcunService.prepareReconcillationStatisticsOnCentral(reconcillationConfigPropertyTO);
			} catch (Exception ex) {
				LOGGER.error("ERROR : OutboundCentralReconciliationDataProcessor::getReconcillationDataFromCentral:::",
						ex);
			}
		}
		LOGGER.debug("OutboundCentralReconciliationDataProcessor::getReconcillationDataFromCentral::ByEEEEE=====>");
		return reconcellationDOs;
	}*/
	/**
	 * Creating the zip file for reconciliation mismatched records 
	 * and inserting this zip file into respective table.
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void validationOnReconcillationData() throws CGSystemException,CGBusinessException, IOException{	
		List<CGBaseDO> reconcillationList=null;
		List<ReconcillationConfigPropertyTO> configProcess = ReconcillationPropertyReader.getReconcillationConfigProperty();
		if(!CGCollectionUtils.isEmpty(configProcess)){
			for(ReconcillationConfigPropertyTO processTO:configProcess){
				String namedQuery="getMismatchedReconcillationDataByProcess";
				String paramName="processName";
				reconcillationList=(List<CGBaseDO>) bcunService.getDataByNamedQueryAndNamedParam(namedQuery, paramName, processTO.getProcess());
				for(CGBaseDO baseDO:reconcillationList){
					BcunReconcillationDO bcunReconcillationDO = (BcunReconcillationDO) baseDO;
					ReconcillationConfigPropertyTO configPropertyTO=ReconcillationPropertyReader.getReconcillationPropertyTOByProcess(bcunReconcillationDO.getProcessName());
					if(!StringUtil.isNull(configPropertyTO)&&!StringUtil.isNull(bcunReconcillationDO.getProcessName())){
						configPropertyTO.setTransactionOfficeId(bcunReconcillationDO.getTransactionOfficeId());
						configPropertyTO.setTransactionDate(bcunReconcillationDO.getTransactionDate());
						List<CGBaseDO> reconcillationDos=bcunService.getReconcillationDataForBlob(configPropertyTO);
						//creating blob file 
						if(CGCollectionUtils.isEmpty(reconcillationDos)){
							reconcillationDos=ReconcillationPropertyReader.getArtificialDataByProcess(configPropertyTO);
						}	 
						if(!CGCollectionUtils.isEmpty(reconcillationDos)){
							Set<Integer> officeList=new HashSet<>(1);
							officeList.add(bcunReconcillationDO.getTransactionOfficeId());
							bcunService.prepareAndStoreZipFile(reconcillationDos, officeList, processTO.getPropKey(),null,"DataSync-bcunOutbound-MUL-");	
						}
					}
				}
			}
		}

	}
	/**
	 * Spring's setter injection
	 * @param bcunService
	 */
	public void setBcunService(BcunDatasyncService bcunService) {
		this.bcunService = bcunService;
	}
}
