/**
 * 
 */
package com.ff.web.drs.blkpending.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.pending.PendingDrsHeaderTO;

/**
 * @author mohammes
 *
 */
public interface BulkCnPendingDrsService {

	boolean saveBulkPendingDrs(PendingDrsHeaderTO drsInputTo)
			throws CGSystemException, CGBusinessException;

	void findBulkPendingDrs(PendingDrsHeaderTO drsInputTo)
			throws CGBusinessException, CGSystemException;


}
