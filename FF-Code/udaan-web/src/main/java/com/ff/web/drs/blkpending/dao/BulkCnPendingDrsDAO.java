/**
 * 
 */
package com.ff.web.drs.blkpending.dao;

import java.util.Date;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.manifest.ManifestDO;
import com.ff.to.drs.AbstractDeliveryTO;

/**
 * @author mohammes
 *
 */
public interface BulkCnPendingDrsDAO {

	ManifestDO getManifestDetailsByManifestNumber(AbstractDeliveryTO deliveryTo)
			throws CGSystemException;

	/**
	 * Gets the manifest date by manifest number.
	 *
	 * @param manifestNumber the manifest number
	 * @return the manifest date by manifest number
	 * @throws CGSystemException the CG system exception
	 */
	Date getManifestDateByManifestNumber(String manifestNumber)
			throws CGSystemException;

}
