/**
 * 
 */
package com.ff.web.drs.updation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.NormalPriorityDrsTO;


/**
 * @author mohammes
 *
 */
public interface UpdateNormalPriorityDrsService {

	/**
	 * Find drs by drs number for dox update.
	 *
	 * @param drsInputTo the drs input to
	 * @return the normal priority drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	NormalPriorityDrsTO findDrsByDrsNumberForUpdate(
			NormalPriorityDrsTO drsInputTo) throws CGBusinessException,
			CGSystemException;

	
	/**
	 * Update delivered drs for dox.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateDeliveredDrs(NormalPriorityDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;

	
}
