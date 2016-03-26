package com.ff.web.loadmanagement.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.loadmanagement.LoadReceiveEditBagTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;

/**
 * The Interface LoadReceiveEditBagService.
 */
public interface LoadReceiveEditBagService {

	/**
	 * Find load receive edit bag details.
	 *
	 * @param to the to
	 * @return the load receive edit bag to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveEditBagTO findLoadReceiveEditBagDetails(final LoadReceiveValidationTO to) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update load receive edit bag details.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveOrUpdateLoadReceiveEditBagDetails(final LoadReceiveEditBagTO to)
			throws CGBusinessException, CGSystemException;
			
}
