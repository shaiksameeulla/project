package com.ff.web.pickup.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;

/**
 * The Interface GenerateRunsheetDAO.
 */
public interface GenerateRunsheetDAO {
	public List<EmployeeDO> getBranchPickupEmployees(Integer loginOfficeId,	List<Integer> branchList) throws CGSystemException;

	public List<RunsheetAssignmentHeaderDO> getAssignedRunsheets(
			Integer assignedEmployeeId, Integer createdAtOfficeId,
			Integer createdForOfficeId, String generationDate,String empList,List<Integer> branchList)
			throws CGSystemException;

	public List<Integer> getGeneratedMasterAssignments(
			List<Integer> assignmentHeaderIds, Date runsheetDate)
			throws CGSystemException;

	public List<PickupRunsheetHeaderDO> getRunsheetNo(
			Integer assignmentHeaderId, Date runsheetDate)
			throws CGSystemException;

	public List<PickupRunsheetHeaderDO> savePickupRunsheet(
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDO)
			throws CGSystemException;

	public List<Object[]> getBranchesUnderHub(Integer loginOfficeId)
			throws CGSystemException;
}
