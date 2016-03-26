/**
 * 
 */
package com.ff.web.drs.updation.dao;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDO;


/**
 * @author mohammes
 *
 */
public interface NormalPriorityDrsDAO {

	/**
	 * Update delivered drs for dox/ppx.
	 *
	 * @param deliveryDo the delivery do
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateDeliveredDrs(DeliveryDO deliveryDo)
			throws CGBusinessException, CGSystemException;

	
}
