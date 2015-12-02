package src.com.dtdc.mdbServices.pickup;

import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PickUpMDBService.
 */
public interface PickUpMDBService {

	/**
	 * Save pick up generation.
	 *
	 * @param cgPickUpTo the cg pick up to
	 * @throws Exception the exception
	 */
	void savePickUpGeneration(CGBaseTO cgPickUpTo) throws Exception;
	
	/**
	 * Save pick up generation.
	 *
	 * @param pickUpTo the pick up to
	 * @throws Exception the exception
	 */
	void savePickUpGeneration(PickUpTO pickUpTo) throws Exception;
	
	/**
	 * Modify pick up request.
	 *
	 * @param cgPickUpTo the cg pick up to
	 * @return the pick up to
	 * @throws Exception the exception
	 */
	PickUpTO modifyPickUpRequest(CGBaseTO cgPickUpTo) throws Exception;
	
	/**
	 * Modify pick up request.
	 *
	 * @param to the to
	 * @return the pick up to
	 * @throws Exception the exception
	 */
	PickUpTO modifyPickUpRequest(PickUpTO to) throws Exception;
	
	/**
	 * Save daily pickup data.
	 *
	 * @param cgPickUpTo the cg pick up to
	 * @throws Exception the exception
	 */
	void saveDailyPickupData(CGBaseTO cgPickUpTo) throws Exception;
	
	/**
	 * Save daily pickup data.
	 *
	 * @param pickupTo the pickup to
	 * @throws Exception the exception
	 */
	void saveDailyPickupData(PickUpTO pickupTo) throws Exception;
	
	/**
	 * Save pickup product child data.
	 *
	 * @param cgPickUpTo the cg pick up to
	 * @throws Exception the exception
	 */
	void savePickupProductChildData(CGBaseTO cgPickUpTo) throws Exception;
	
	/**
	 * Save pickup product child data.
	 *
	 * @param pickupTo the pickup to
	 * @throws Exception the exception
	 */
	void savePickupProductChildData(PickUpTO pickupTo) throws Exception;
	
	
}
