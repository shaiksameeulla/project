package com.ff.universe.pickup.dao;

/**
 * 
 */

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.CSDSAPPickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;

/**
 * The Interface PickupCommonDAO.
 *
 * @author kgajare
 */
public interface PickupCommonDAO {
	
	/**
	 * To get pickup run sheet type(s).
	 *
	 * @return the pickup runsheet type
	 * @throws CGSystemException the cG system exception
	 * the pickupAssignmentTypeDOs
	 * CGSystemException
	 */
	public List<PickupAssignmentTypeDO> getPickupRunsheetType()
			throws CGSystemException;

	/**
	 * To get pickup assignment type(s).
	 *
	 * @param assignmentTypeId the assignment type id
	 * @return the pickup assignment type
	 * @throws CGSystemException the cG system exception
	 * assignmentTypeId
	 * CGSystemException
	 */
	public List<Object> getPickupAssignmentType(Integer assignmentTypeId)
			throws CGSystemException;

	/**
	 * To get status of master pickup assignment.
	 *
	 * @param assignmentHeaderId the assignment header id
	 * @param currentDate the current date
	 * @return the status of master pickup assignment
	 * @throws CGBusinessException the cG business exception
	 * assignmentHeaderId
	 * currentDate
	 * CGBusinessException
	 */
	public RunsheetAssignmentHeaderDO getStatusOfMasterPickupAssignment(
			Integer assignmentHeaderId, Date currentDate)
			throws CGSystemException;

	/**
	 * To get pickup delivery location.
	 *
	 * @param customerId the customer id
	 * @param officeId the office id
	 * @return the pickup dlv location
	 * @throws CGBusinessException the cG business exception
	 * customerId
	 * officeId
	 * CGBusinessException
	 */
	public PickupDeliveryLocationDO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGSystemException;
	
	/**
	 * Gets the pickup runsheet details.
	 *
	 * @param runsheetHeaderId the runsheet header id
	 * @return the pickup runsheet details
	 * @throws CGSystemException the cG system exception
	 */
	public List<PickupRunsheetHeaderDO> getPickupRunsheetDetails(
			List<Integer> runsheetHeaderId) throws CGSystemException;

	/**
	 * Gets the master runsheet assignment details by emp id.
	 *
	 * @param employeeId the employee id
	 * @return the master runsheet assignment details by emp id
	 * @throws CGSystemException the cG system exception
	 */
	public List<RunsheetAssignmentDetailDO> getMasterRunsheetAssignmentDetailsByEmpId(
			Integer employeeId) throws CGSystemException;
	
	public List<RunsheetAssignmentHeaderDO> getRunsheetAssignmentHeader(Integer assignmentHeaderId)throws CGSystemException;
	
	public boolean savePickupDeliveryLocation(List<CSDSAPPickupDeliveryLocationDO> pickupDeliveryLocationDOs) throws CGSystemException;

	/**
	 * To get pickup delivery location for SAP customer contract for update
	 * 
	 * @param customerId
	 * @param officeId
	 * @return pickupDeliveryLocationDO
	 * @throws CGSystemException
	 */
	public CSDSAPPickupDeliveryLocationDO getPickupDlvLocForSAPCustContract(
			Integer customerId, Integer officeId) throws CGSystemException;
	
}
