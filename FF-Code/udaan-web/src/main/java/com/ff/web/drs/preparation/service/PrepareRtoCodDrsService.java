/**
 * 
 */
package com.ff.web.drs.preparation.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.RtoCodDrsTO;

/**
 * The Interface PrepareRtoCodDrsService.
 *
 * @author mohammes
 */
public interface PrepareRtoCodDrsService {

	/**
	 * Save prepare drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean savePrepareDrs(RtoCodDrsTO drsInputTo)throws CGBusinessException,CGSystemException;

	/**
	 * Discard drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean discardDrs(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Find drs by drs number.
	 *
	 * @param drsInputTo the drs input to
	 * @return the prepare np dox drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	RtoCodDrsTO findDrsByDrsNumber(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Modify drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean modifyDrs(RtoCodDrsTO drsInputTo) throws CGBusinessException,
			CGSystemException;

	/**
	 * Validate rto cod consgments.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	DeliveryConsignmentTO validateRtoCodConsgments(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException;
	
}
