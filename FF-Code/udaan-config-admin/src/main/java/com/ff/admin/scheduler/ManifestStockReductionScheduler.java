package com.ff.admin.scheduler;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.admin.stockmanagement.stockreduction.service.StockReductionService;
import com.ff.domain.stockmanagement.operations.reduction.StockConsumptionLevelDO;
import com.ff.to.stockmanagement.StockReductionInputTO;

/**
 * @author hkansagr
 * 
 */
public class ManifestStockReductionScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ManifestStockReductionScheduler.class);

	/** The stockReductionService. */
	private StockReductionService stockReductionService;

	/**
	 * @param stockReductionService
	 *            the stockReductionService to set
	 */
	public void setStockReductionService(
			StockReductionService stockReductionService) {
		this.stockReductionService = stockReductionService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.trace("ManifestStockReductionScheduler :: Scheduler :: executeInternal() :: START");
		try {
			/*
			 * To reduce the manifest stock. it contains material/ item type
			 * like, OGM No. / BPL No. / MBPL No. / Bag Lock No./ Consignment
			 * Note / Child CN
			 */
			executeStockReductionScheduler();
		} catch (HttpException e) {
			LOGGER.error("ManifestStockReductionScheduler::executeInternal::HttpException::" , e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ManifestStockReductionScheduler::executeInternal::ClassNotFoundException::" , e);
		} catch (IOException e) {
			LOGGER.error("ManifestStockReductionScheduler::executeInternal::IOException::" , e);
		} catch(Exception e) {
			LOGGER.error("ManifestStockReductionScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.trace("ManifestStockReductionScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * To execute manifest/consignment stock reduction scheduler
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void executeStockReductionScheduler() throws CGBusinessException,
	CGSystemException ,HttpException, ClassNotFoundException, IOException{
		StockReductionInputTO inputTo=null;
		LOGGER.trace("ManifestStockReductionScheduler :: manifestStockReductionScheduler() :: START");
		inputTo=stockReductionService.prepareStockReductionInputTO();
		try {
			prepareManifestStockReductionDetails(inputTo);
		} catch (Exception e) {
			LOGGER.trace("ManifestStockReductionScheduler :: prepareManifestStockReductionDetails() :: Exception",e);
		}

		try {
			prepareConsignmentStockReductionDetails(inputTo);
		} catch (Exception e) {
			LOGGER.trace("ManifestStockReductionScheduler :: prepareManifestStockReductionDetails() :: Exception",e);
		}

		try {
			prepareComailStockReductionDetails(inputTo);
		} catch (Exception e) {
			LOGGER.trace("ManifestStockReductionScheduler :: prepareComailStockReductionDetails() :: Exception",e);
		}


		LOGGER.trace("ManifestStockReductionScheduler :: manifestStockReductionScheduler() :: END");
	}
	private void prepareManifestStockReductionDetails(StockReductionInputTO stockReductionInputTo) throws CGBusinessException,
	CGSystemException {
		LOGGER.trace("ManifestStockReductionScheduler :: prepareManifestStockReductionDetails() :: START");
		List<StockConsumptionLevelDO> consumptionLevelDOList=stockReductionService.prepareStockConsumptionlevelDtlsFromManifest(stockReductionInputTo);
		if(!CGCollectionUtils.isEmpty(consumptionLevelDOList)){
			stockReductionService.saveStockConsumptionLevelDtlsFromManifestConsignment(consumptionLevelDOList);
		}else{
			LOGGER.warn("ManifestStockReductionScheduler :: prepareManifestStockReductionDetails :: no consuption details to save for manifest");
		}
		LOGGER.trace("ManifestStockReductionScheduler :: prepareManifestStockReductionDetails :: END");
	}
	private void prepareConsignmentStockReductionDetails(StockReductionInputTO stockReductionInputTo) throws CGBusinessException,
	CGSystemException {
		LOGGER.trace("ManifestStockReductionScheduler :: prepareConsignmentStockReductionDetails :: START");
		List<StockConsumptionLevelDO> consumptionLevelDOList=stockReductionService.getStockReductionDtlsFromConsignment(stockReductionInputTo);
		if(!CGCollectionUtils.isEmpty(consumptionLevelDOList)){
			stockReductionService.saveStockConsumptionLevelDtlsFromManifestConsignment(consumptionLevelDOList);
		}else{
			LOGGER.warn("ManifestStockReductionScheduler :: prepareConsignmentStockReductionDetails :: no consuption details to save for Consignment");
		}
		LOGGER.trace("ManifestStockReductionScheduler :: prepareConsignmentStockReductionDetails :: END");
	}
	private void prepareComailStockReductionDetails(StockReductionInputTO stockReductionInputTo) throws CGBusinessException,
	CGSystemException {
		LOGGER.trace("ManifestStockReductionScheduler :: prepareComailStockReductionDetails :: START");
		List<StockConsumptionLevelDO> consumptionLevelDOList=stockReductionService.getStockReductionDtlsFromComail(stockReductionInputTo);
		if(!CGCollectionUtils.isEmpty(consumptionLevelDOList)){
			stockReductionService.saveStockConsumptionLevelDtlsFromManifestConsignment(consumptionLevelDOList);
		}else{
			LOGGER.warn("ManifestStockReductionScheduler :: prepareComailStockReductionDetails :: no consuption details to save for Comail");
		}
		LOGGER.trace("ManifestStockReductionScheduler :: prepareComailStockReductionDetails :: END");
	}




}
