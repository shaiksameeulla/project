package com.ff.web.loadmanagement.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveOutstationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;

/**
 * The Interface LoadReceiveOutstationService.
 *
 * @author narmdr
 */
public interface LoadReceiveOutstationService {
	
	/**
	 * Checks if is receive number exist.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return true, if is receive number exist
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean isReceiveNumberExist(final LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update load receive outstation.
	 *
	 * @param loadReceiveOutstationTO the load receive outstation to
	 * @return true, if successful
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	boolean saveOrUpdateLoadReceiveOutstation(
			final LoadReceiveOutstationTO loadReceiveOutstationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number4 receive outstation.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load receive manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveManifestValidationTO validateManifestNumber4ReceiveOutstation(
			final LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException;
}
