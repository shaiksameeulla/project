package com.ff.web.loadmanagement.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.loadmanagement.LoadManagementTO;


/**
 * The Interface LoadManagementService.
 *
 * @author narmdr
 */
public interface LoadManagementService {

	/**
	 * Two way write.
	 *
	 * @param loadManagementTO the load management to
	 * @throws CGBusinessException the cG business exception
	 */
	void twoWayWrite(LoadManagementTO loadManagementTO)
			throws CGBusinessException;

}
