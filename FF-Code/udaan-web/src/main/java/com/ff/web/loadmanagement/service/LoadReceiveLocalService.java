package com.ff.web.loadmanagement.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadReceiveLocalTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.transport.TransportModeTO;

/**
 * The Interface LoadReceiveLocalService.
 *
 * @author narmdr
 */
public interface LoadReceiveLocalService {

	/**
	 * Gets the transport mode.
	 *
	 * @param transportModeTO the transport mode to
	 * @return the transport mode
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	TransportModeTO getTransportMode(final TransportModeTO transportModeTO) 
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the load receive local to.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveLocalTO getLoadReceiveLocalTO(
			final LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update load receive local.
	 *
	 * @param loadReceiveLocalTO the load receive local to
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveLocalTO saveOrUpdateLoadReceiveLocal(
			final LoadReceiveLocalTO loadReceiveLocalTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the origin offices.
	 *
	 * @param officeTO the office to
	 * @return the origin offices
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getOriginOffices(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate manifest number4 receive local.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load receive manifest validation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveManifestValidationTO validateManifestNumber4ReceiveLocal(
			final LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Print the load receive local to.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	LoadReceiveLocalTO printLoadReceiveLocalTO(
			final LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException;

	
}
