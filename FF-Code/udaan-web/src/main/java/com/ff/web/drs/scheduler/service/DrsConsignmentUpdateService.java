/**
 * 
 */
package com.ff.web.drs.scheduler.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDetailsDO;

/**
 * @author mohammes
 *
 */
public interface DrsConsignmentUpdateService {
	/**
	 * Update delivered parent consg.
	 *
	 * @param deliveredCN the delivered cn
	 * @param trkingProcessList the trking process list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	boolean modifyParentConsignmentStatus(List<DeliveryDetailsDO> deliveredCN)
			throws CGSystemException, CGBusinessException;

	/**
	 * Modify child consignment status.
	 *
	 * @param deliveredCN the delivered cn
	 * @param trkingProcessList the trking process list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	boolean modifyChildConsignmentStatus(List<DeliveryDetailsDO> deliveredCN)
			throws CGSystemException, CGBusinessException;

	void updateDeliveredChildConsg() throws CGBusinessException, CGSystemException;

	void updateDeliveredParentConsg() throws CGSystemException, CGBusinessException;
}
