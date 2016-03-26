package com.ff.web.pickup.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;

public interface PickupAssignmentDAO {

	public List<PickupAssignmentTypeDO> getPickupRunsheetType()
			throws CGSystemException;

	public List<PickupDeliveryLocationDO> getUnassignedMasterCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<PickupDeliveryLocationDO> getUnassignedStandardCustomersForTemporary(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<RunsheetAssignmentDetailDO> getAssignedMasterCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<ReversePickupOrderDetailDO> getReversePickupCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<RunsheetAssignmentDetailDO> getAssignedTemporaryCustomers(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public Boolean savePickupAssignment(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<ReversePickupOrderDetailDO> getAllReverseCustomers(
			Integer officeId) throws CGSystemException;

	public PickupDeliveryContractDO getBranchDeliveryContractByCustomer(
			Integer officeId, Integer customerId) throws CGSystemException;

	public PickupDeliveryContractDO getPickupContracByCustomerAndOffice(
			Integer officeId, Integer customerId) throws CGSystemException;

	public Boolean savePickupMasterAssignment(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<PickupDeliveryLocationDO> getUnassignedMasterCustomersForHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<PickupDeliveryLocationDO> getUnassignedStandardCustomersForTemporaryForHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;

	public List<ReversePickupOrderDetailDO> getUnassignedTemporaryCustomersHub(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO)
			throws CGSystemException;
}
