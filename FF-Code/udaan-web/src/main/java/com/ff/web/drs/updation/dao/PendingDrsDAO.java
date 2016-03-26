/**
 * 
 */
package com.ff.web.drs.updation.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDO;

/**
 * @author mohammes
 *
 */
public interface PendingDrsDAO {

	/**
	 * Update undelivered drs consg.
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateUndeliveredDrsConsg(DeliveryDO deliveryDO)
			throws CGSystemException;

}
