package com.ff.web.loadmanagement.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.loadmanagement.LoadReceiveValidationTO;

/**
 * The Interface LoadManagementDAO.
 *
 * @author narmdr
 */
public interface LoadManagementDAO {

	/**
	 * Save or update load movement.
	 *
	 * @param loadMovementDO the load movement do
	 * @return the load movement do
	 * @throws CGSystemException the cG system exception
	 */
	LoadMovementDO saveOrUpdateLoadMovement(LoadMovementDO loadMovementDO)
			throws CGSystemException;

	/**
	 * Delete load connected d os by load connected id list.
	 *
	 * @param loadConnectedIdList the load connected id list
	 * @return true, if successful
	 * @throws CGSystemException the cG system exception
	 */
	boolean deleteLoadConnectedDOsByLoadConnectedIdList(
			List<Integer> loadConnectedIdList) throws CGSystemException;

	/**
	 * Gets the load connected from dispatch as not received.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return the load connected from dispatch as not received
	 * @throws CGSystemException the cG system exception
	 */
	List<LoadConnectedDO> getLoadConnectedFromDispatchAsNotReceived(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException;

	/**
	 * Find load receive edit bag details.
	 *
	 * @param validationTO the validation to
	 * @return the load connected do
	 * @throws CGSystemException the cG system exception
	 */
	LoadConnectedDO findLoadReceiveEditBagDetails(LoadReceiveValidationTO 
			validationTO) throws CGSystemException;
	
	/**
	 * Gets the load connected ids as not received.
	 *
	 * @param loadReceiveValidationTO the load receive validation to
	 * @return the load connected ids as not received
	 * @throws CGSystemException the cG system exception
	 */
	List<Integer> getLoadConnectedIdsAsNotReceived(
			LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGSystemException;

	/**
	 * Save or update load receive edit bag details.
	 *
	 * @param loadConnectedDO the load connected do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveOrUpdateLoadReceiveEditBagDetails(LoadConnectedDO loadConnectedDO)
			throws CGSystemException;

	/**
	 * Update load movement dt to central.
	 *
	 * @param loadMovementDO the load movement do
	 * @throws CGSystemException the cG system exception
	 */
	void updateLoadMovementDtToCentral(LoadMovementDO loadMovementDO)
			throws CGSystemException;
}
