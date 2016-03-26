/**
 * 
 */
package com.ff.web.pickup.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.organization.OfficeTO;

/**
 * @author uchauhan
 * 
 */
public interface ConfirmPickupOrderDAO {

	/**
	 * @param officeTO
	 *            set with the logged in office Id
	 * @return list of pending requests for the logged in branch
	 * @throws CGSystemException
	 *             if any database error occurs
	 */
	List<ReversePickupOrderDetailDO> getPickupOrderRequestList(OfficeTO officeTO)
			throws CGSystemException;

	/**
	 * @param status
	 *            which is to be updated to database. A- Accepted and D-
	 *            Declined
	 * @param officeId
	 *            logged in office Id
	 * @param detailId
	 *            the order number for which status is to be updated
	 * @param updatedBy 
	 * @return
	 * @throws CGSystemException
	 *             if any database error occurs
	 */
	boolean updatePickupOrderDetails(String status, Integer officeId,
			Integer detailId, Integer updatedBy) throws CGSystemException;

	boolean updateBranchOrderDetails(String status, Integer officeId,
			Integer detailId, Integer updatedBy) throws CGSystemException;

	boolean updateforDataSync(Integer officeId, Integer detailId)
			throws CGSystemException;

	List<ReversePickupOrderDetailDO> getPickupOrderBranchMappingDtls(Integer detailId)
			throws CGSystemException;
}
