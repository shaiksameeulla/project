/**
 * 
 */
package com.ff.web.drs.manualdrs.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.ManualDrsTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public interface ManualDrsService {

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
	 * Save undelivered drs consg.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean saveUndeliveredDrsConsg(PendingDrsHeaderTO drsInputTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save delivered drs consg.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	Boolean saveDeliveredDrsConsg(ManualDrsTO drsInputTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Validate consignment details.
	 *
	 * @param deliveryTO the delivery to
	 * @return the delivery consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryConsignmentTO validateConsignmentDetails(
			AbstractDeliveryTO deliveryTO) throws CGBusinessException,
			CGSystemException;

	ManualDrsTO findDrsByDrsNumberForUpdate(ManualDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;

}
