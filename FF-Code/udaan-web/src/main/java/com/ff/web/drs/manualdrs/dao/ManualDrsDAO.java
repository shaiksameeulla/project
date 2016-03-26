/**
 * 
 */
package com.ff.web.drs.manualdrs.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.to.drs.AbstractDeliveryTO;

/**
 * @author mohammes
 *
 */
public interface ManualDrsDAO {

	
	/**
	 * Save manual drs.
	 *
	 * @param deliveryDO the delivery do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveManualDrs(DeliveryDO deliveryDO) throws CGSystemException;

	/**
	 * Gets the manifest details by manifest number.
	 *
	 * @param drsTO the drs to
	 * @return the manifest details by manifest number
	 * @throws CGSystemException the cG system exception
	 */
	ManifestDO getManifestDetailsByManifestNumber(AbstractDeliveryTO drsTO)
			throws CGSystemException;

	/**
	 * Gets the manifest consignemtn count for drs.
	 *
	 * @param drsTO the drs to
	 * @return the manifest consignemtn count for drs
	 * @throws CGSystemException the cG system exception
	 */
	Integer getManifestConsignemtnCountForDrs(AbstractDeliveryTO drsTO)
			throws CGSystemException;

}
