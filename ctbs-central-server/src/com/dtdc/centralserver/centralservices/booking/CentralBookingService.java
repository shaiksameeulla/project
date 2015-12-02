/*
 * @author soagarwa
 */
package com.dtdc.centralserver.centralservices.booking;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralBookingService.
 */
public interface CentralBookingService {

	/**
	 * Validate consignment.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseTO validateConsignment(CGBaseTO baseTO)
			throws CGSystemException;
	

	/**
	 * Get consignment status.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseTO getCNstatus(CGBaseTO baseTO)
			throws CGSystemException;

}
