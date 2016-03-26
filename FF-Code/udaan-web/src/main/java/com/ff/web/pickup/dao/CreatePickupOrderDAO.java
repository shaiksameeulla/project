package com.ff.web.pickup.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.pickup.PickupOrderDetailsTO;

public interface CreatePickupOrderDAO {
	
	 /**
	  * Saves the ReversePickupOrderHeaderDO to database
	 * @param headerDO which is populated with all details to be saved
	 * @return ReversePickupOrderHeaderDO populated with persistent data and generated order Numbers
	 * @throws CGSystemException if any database error occurs
	 */
	public ReversePickupOrderHeaderDO savePickupOrder(ReversePickupOrderHeaderDO headerDO) throws CGSystemException;
	
	 /**
	  * Retrieves the details of the given order Number from database
	 * @param detailTO which is populated with the order Number
	 * @return ReversePickupOrderDetailDO is populated with all the details with corresponding order Number
	 * @throws CGSystemException if any database error occurs
	 */
	public ReversePickupOrderDetailDO getPickupOrderDetail(PickupOrderDetailsTO detailTO) throws CGSystemException;

	public List<Object[]> getCustomersInContractByBranch(Integer officeId) throws CGSystemException;

}
