package com.ff.admin.scheduler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.stockmanagement.stockreduction.service.StockReductionService;

/**
 * @author hkansagr
 * 
 */
public class StockConsumptionConsolidatorScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockConsumptionConsolidatorScheduler.class);

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
		LOGGER.info("StockConsumptionConsolidatorScheduler :: Scheduler :: executeInternal() :: START");
		try {
			/*
			 * To consolidate stock details and save date wise, office wise and
			 * material wise to SAP staging table
			 */
			executeStockConsolidationScheduler();
		} catch (HttpException e) {
			LOGGER.error("StockConsumptionConsolidatorScheduler::executeInternal::HttpException::" ,e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("StockConsumptionConsolidatorScheduler::executeInternal::ClassNotFoundException::" ,e);
		} catch (IOException e) {
			LOGGER.error("StockConsumptionConsolidatorScheduler::executeInternal::IOException::" ,e);
		} catch(Exception e) {
			LOGGER.error("StockConsumptionConsolidatorScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.info("StockConsolidationScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * To execute stock consolidation details and save to SAP staging table
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void executeStockConsolidationScheduler()
			throws CGBusinessException, CGSystemException,HttpException, ClassNotFoundException, IOException {
		LOGGER.info("StockConsumptionConsolidatorScheduler :: StockConsumptionConsolidatorScheduler() :: START");
		stockReductionService.consolidateStockConsumptionDtls();
		LOGGER.info("StockConsumptionConsolidatorScheduler :: StockConsumptionConsolidatorScheduler() :: END");
	}

}
