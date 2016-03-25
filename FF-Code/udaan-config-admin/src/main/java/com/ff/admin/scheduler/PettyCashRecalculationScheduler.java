package com.ff.admin.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.admin.mec.pettycash.service.PettyCashReportService;

/**
 * @author tekulkar
 * 
 */
public class PettyCashRecalculationScheduler extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PettyCashRecalculationScheduler.class);

	/** The pettyCashReportService. */
	private transient PettyCashReportService pettyCashReportService;

	/**
	 * @param pettyCashReportService
	 *            the pettyCashReportService to set
	 */
	public void setPettyCashReportService(
			PettyCashReportService pettyCashReportService) {
		this.pettyCashReportService = pettyCashReportService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOGGER.warn("PettyCashRecalculationScheduler :: Scheduler :: executeInternal() :: START");
		try {
			pettyCashRecalculationToCorrectClosingBalances();
		} catch(Exception e) {
			LOGGER.error("PettyCashRecalculationScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.warn("PettyCashRecalculationScheduler :: Scheduler :: executeInternal() :: END");
	}


	/**
	 * The petty cash report re-calculation if required.
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	private void pettyCashRecalculationToCorrectClosingBalances() 
			throws CGBusinessException,	CGSystemException {
		LOGGER.trace("PettyCashRecalculationScheduler :: pettyCashRecalculationToCorrectClosingBalances() :: START");
			pettyCashReportService.recalculateClosingBalanceForOffices();
		LOGGER.trace("PettyCashRecalculationScheduler :: pettyCashRecalculationToCorrectClosingBalances() :: END");
		}
}
