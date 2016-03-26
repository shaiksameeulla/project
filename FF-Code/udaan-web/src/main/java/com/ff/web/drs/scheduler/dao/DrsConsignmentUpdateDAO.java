/**
 * 
 */
package com.ff.web.drs.scheduler.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.PincodeDO;

/**
 * @author mohammes
 *
 */
public interface DrsConsignmentUpdateDAO {

	/**
	 * Gets the delivered parent consg dtls.
	 *
	 * @return the delivered parent consg dtls
	 * @throws CGSystemException the cG system exception
	 */
	List<DeliveryDetailsDO> getDeliveredParentConsgDtls()
			throws CGSystemException;

	/**
	 * Update consg details.
	 *
	 * @param consgDO the consg do
	 * @throws CGSystemException the cG system exception
	 */
	void updateConsgDetails(List<DeliveryDetailsDO> consgDO)
			throws CGSystemException;

	/**
	 * Gets the delivered child consg dtls.
	 *
	 * @return the delivered child consg dtls
	 * @throws CGSystemException the cG system exception
	 */
	List<DeliveryDetailsDO> getDeliveredChildConsgDtls()
			throws CGSystemException;

	Integer getGetTranshipBytranscityAndDrsCity(Integer deliveryCity,
			Integer actualDestinationCity) throws CGSystemException;

	PincodeDO getPincodeByCityId(Integer deliveryCity) throws CGSystemException;

	void updateConsgDetailsWithPincode(List<ConsignmentDO> consgDO)
			throws CGSystemException;

}
