/**
 * 
 */
package com.ff.web.drs.updation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.RtoCodDrsTO;


/**
 * @author mohammes
 *
 */
public interface UpdateRtoDrsService {

	/**
	 * Find drs by drs number for dox update.
	 *
	 * @param drsInputTo the drs input to
	 * @return the normal priority drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	RtoCodDrsTO findDrsByDrsNumberForUpdate(
			RtoCodDrsTO drsInputTo) throws CGBusinessException,
			CGSystemException;

	
	/**
	 * Update delivered drs for dox.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateDeliveredDrs(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;

	
}
