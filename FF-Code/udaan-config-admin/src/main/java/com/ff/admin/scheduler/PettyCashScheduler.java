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
public class PettyCashScheduler extends QuartzJobBean {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PettyCashScheduler.class);

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
		LOGGER.warn("PettyCashScheduler :: Scheduler :: executeInternal() :: START");
		try {
			pettyCashReportScheduler();
		} catch(Exception e) {
			LOGGER.error("PettyCashScheduler::executeInternal::Exception::" , e);
		}
		LOGGER.warn("PettyCashScheduler :: Scheduler :: executeInternal() :: END");
	}

	/**
	 * The petty cash report scheduler which calls all business logic(s).
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @throws InterruptedException 
	 */
	private void pettyCashReportScheduler() throws InterruptedException {
		LOGGER.trace("PettyCashScheduler :: pettyCashReportScheduler() :: START");
		try {
			pettyCashReportService.executePettyCashAutoCorrection();
			pettyCashReportService.executePettyCashScheduler();
		}
		catch (Exception e) {
			LOGGER.error("PettyCashScheduler::pettyCashReportScheduler::Exception::" , e);
			/* In the event of any database connection failure, if the exception propagates to this point, then
			 * the scheduler will wait for a period of 30 minutes and then execute the entire code again  */
			LOGGER.error("PettyCashScheduler::pettyCashReportScheduler::Exception:: Waiting for 5 minutes");
			Thread.sleep(300000); // Waiting period of 5 minutes
			LOGGER.error("PettyCashScheduler::pettyCashReportScheduler::Exception:: Waiting period over. Running the scheduler again");
			pettyCashReportScheduler();
		}
		LOGGER.trace("PettyCashScheduler :: pettyCashReportScheduler() :: END");
	}
}
