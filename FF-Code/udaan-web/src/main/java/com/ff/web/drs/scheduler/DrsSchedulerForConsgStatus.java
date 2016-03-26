/**
 * 
 */
package com.ff.web.drs.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ff.web.drs.scheduler.service.DrsConsignmentUpdateService;

/**
 * @author mohammes
 *
 */
public class DrsSchedulerForConsgStatus {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DrsSchedulerForConsgStatus.class);
	 private DrsConsignmentUpdateService drsConsignmentUpdateService;

	/**
	 * @return the drsConsignmentUpdateService
	 */
	public DrsConsignmentUpdateService getDrsConsignmentUpdateService() {
		return drsConsignmentUpdateService;
	}

	/**
	 * @param drsConsignmentUpdateService the drsConsignmentUpdateService to set
	 */
	public void setDrsConsignmentUpdateService(
			DrsConsignmentUpdateService drsConsignmentUpdateService) {
		this.drsConsignmentUpdateService = drsConsignmentUpdateService;
	}
	/**
	 * updateDeliveredConsignment :: it updates the Consignment status in the delivery table
	 */
	public void updateConsignmentStatus() {
		LOGGER.debug("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: START(updateDeliveredParentConsg)");
		try {
			drsConsignmentUpdateService.updateDeliveredParentConsg();
		} catch (Exception e) {
			LOGGER.error("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: Exception(updateDeliveredParentConsg)",e);
		}
		LOGGER.debug("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: END(updateDeliveredParentConsg)");
		LOGGER.debug("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: START(updateDeliveredChildConsg)");
		try {
			drsConsignmentUpdateService.updateDeliveredChildConsg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: Exception(updateDeliveredChildConsg)",e);
		}
		LOGGER.debug("DrsSchedulerForConsgStatus ::updateConsignmentStatus :: END(updateDeliveredChildConsg)");
	}
	
	
}
