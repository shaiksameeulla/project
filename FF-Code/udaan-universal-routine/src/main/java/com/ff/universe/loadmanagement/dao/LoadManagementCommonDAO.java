
package com.ff.universe.loadmanagement.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;

/**
 * The Interface LoadManagementCommonDAO.
 *
 * @author narmdr
 */
public interface LoadManagementCommonDAO {

	/**
	 * Gets the load movement do by gate pass number.
	 *
	 * @param gatePassNumber the gate pass number
	 * @return the load movement do by gate pass number
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementDO getLoadMovementDOByGatePassNumber(String gatePassNumber) throws CGSystemException;

	/**
	 * Gets the load connected do by manifest no office id movement direction.
	 *
	 * @param manifestNumber the manifest number
	 * @param officeId the office id
	 * @param movementDirection the movement direction
	 * @return the load connected do by manifest no office id movement direction
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO getLoadConnectedDOByManifestNoOfficeIdMovementDirection(
			String manifestNumber, Integer officeId,
			String movementDirection) throws CGSystemException;

	/**
	 * Gets the load movement for receive local by dispatch.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load movement for receive local by dispatch
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementDO getLoadMovementForReceiveLocalByDispatch(
			LoadManagementValidationTO loadManagementValidationTO)
					 throws CGSystemException;

	/**
	 * Gets the load movement for receive local by receive.
	 *
	 * @param loadManagementValidationTO the load management validation to
	 * @return the load movement for receive local by receive
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementDO getLoadMovementForReceiveLocalByReceive(
			LoadManagementValidationTO loadManagementValidationTO)
					throws CGSystemException;

	/**
	 * Gets the load connected do.
	 *
	 * @param manifestTO the manifest to
	 * @param dispatchDirection the dispatch direction
	 * @return the load connected do
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO getLoadConnectedDO(ManifestTO manifestTO,
			String dispatchDirection) throws CGSystemException;

	/**
	 * Gets the load connected4 receive local by receive manifest no.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load connected4 receive local by receive manifest no
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO getLoadConnected4ReceiveLocalByReceiveManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
					 throws CGSystemException;

	/**
	 * Gets the load connected4 receive local by dispatch manifest no.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load connected4 receive local by dispatch manifest no
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO getLoadConnected4ReceiveLocalByDispatchManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
					throws CGSystemException;

	/**
	 * Checks if is manifest received.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return true, if is manifest received
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestReceived(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
					throws CGSystemException;

	/**
	 * Checks if is receive number exist.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return true, if is receive number exist
	 * @throws CGSystemException the cG system exception
	 */
	boolean isReceiveNumberExist(LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException;

	/**
	 * Checks if is manifest received4 outstation.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return true, if is manifest received4 outstation
	 * @throws CGSystemException the cG system exception
	 */
	boolean isManifestReceived4Outstation(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException;

	/**
	 * Gets the load connected4 receive outstation by dispatch manifest no.
	 *
	 * @param loadReceiveManifestValidationTO the load receive manifest validation to
	 * @return the load connected4 receive outstation by dispatch manifest no
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO getLoadConnected4ReceiveOutstationByDispatchManifestNo(
			LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGSystemException;
}
