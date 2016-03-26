/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;

/**
 * @author uchauhan
 *
 */
public interface ConfirmPickupOrderService {
	
	/**
	 * @param pickupOrderTO set with the number of selected check boxes  
	 * @param status with which the record in the database is to be updated
	 * @return true if updated successfully
	 * @throws CGBusinessException if any Buisness Rules fail
	 * @throws CGSystemException if any database error occurs
	 */
	public PickupOrderTO updatePickupOrderDetails(PickupOrderTO pickupOrderTO, String status ) throws CGBusinessException, CGSystemException;

	/**
	 * @param officeTO set with the logged in officeId
	 * @return list of pending request in the descending order of the created date
	 * @throws CGBusinessException if any Buisness Rules fail
	 * @throws CGSystemException if any database error occurs
	 */
	public List<PickupOrderDetailsTO> getPickupRequestList(OfficeTO officeTO) throws CGBusinessException, CGSystemException;

}
