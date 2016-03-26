package com.ff.sap.integration.schedular;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ff.sap.integration.consignmentratecalculation.service.ConsignmentRateCalculationService;

/**
 * @author prmeher
 * 
 */
public class ConsignmentRTORateCalculationScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConsignmentRTORateCalculationScheduler.class);

	/** The consignmentRateCalculationService. */
	private ConsignmentRateCalculationService consignmentRateCalculationService;

	/**
	 * @param consignmentRateCalculationService
	 *            the consignmentRateCalculationService to set
	 */
	public void setConsignmentRateCalculationService(
			ConsignmentRateCalculationService consignmentRateCalculationService) {
		this.consignmentRateCalculationService = consignmentRateCalculationService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.debug("ConsignmentRateCalculationScheduler :: executeInternal() :: START");
		try {
			consignmentRateCalculationService
					.executeConsignmentRateCalculation("R");
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ConsignmentRateCalculationScheduler :: executeInternal() :: ",
					e);
		}
		LOGGER.debug("ConsignmentRateCalculationScheduler :: executeInternal() :: END");
	}

}
