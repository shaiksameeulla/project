/**
 * 
 */
package com.ff.web.drs.updation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.pending.PendingDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public interface PendingDrsService {

	/**
	 * Search pending consignments.
	 *
	 * @param inputTo the input to
	 * @return the pending drs header to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	PendingDrsHeaderTO searchPendingConsignments(PendingDrsHeaderTO inputTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Update undelivered drs consg.
	 *
	 * @param inputTo the input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean updateUndeliveredDrsConsg(PendingDrsHeaderTO inputTo)
			throws CGSystemException, CGBusinessException;

}
