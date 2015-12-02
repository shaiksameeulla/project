/**
 * 
 */
package com.dtdc.centralserver.centralservices.dmc;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralDmcReportService.
 *
 * @author mohammes
 */
public interface CentralDmcReportService {
	
	/**
	 * Find fdm details.
	 *
	 * @param baseTO the base to
	 * @return the cG base to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public CGBaseTO findFdmDetails(CGBaseTO baseTO)
	throws CGBusinessException,CGSystemException;
}
