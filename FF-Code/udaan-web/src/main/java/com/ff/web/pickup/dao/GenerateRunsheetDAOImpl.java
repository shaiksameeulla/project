package com.ff.web.pickup.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.web.pickup.constants.PickupManagementConstants;

/**
 * The Class GenerateRunsheetDAOImpl.
 */
public class GenerateRunsheetDAOImpl extends CGBaseDAO implements
		GenerateRunsheetDAO {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GenerateRunsheetDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeDO> getBranchPickupEmployees(Integer loginOfficeId,List<Integer> branchList) throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: getBranchPickupEmployees() :: Start --------> ::::::");
		List<EmployeeDO> employeeDtls = null;
		String hql = "getPickupEmployeeUnderHub";
		String[] params = { "createdAtOfficeId", "branchList" };
		Object[] values = { loginOfficeId, branchList };
		try {
			employeeDtls = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(hql, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetDAOImpl::getBranchPickupEmployees :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: getBranchPickupEmployees() :: End --------> ::::::");
		return employeeDtls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RunsheetAssignmentHeaderDO> getAssignedRunsheets(
			Integer assignedEmployeeId, Integer createdAtOfficeId,
			Integer createdForOfficeId, String generationDate,String empList,List<Integer> branchList)
			throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: getAssignedRunsheets() :: Start --------> ::::::");
		List<RunsheetAssignmentHeaderDO> assignedRunsheets = null;
		try {
			String queryName = "getAssignedPickupRunsheets";
			String[] params = { PickupManagementConstants.EMPLOYEE_ID, "emplist",
					"createdAtOffice", "branchList"};
			Object[] values = { assignedEmployeeId, empList, createdAtOfficeId,
					branchList};
			assignedRunsheets = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(queryName, params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetDAOImpl::getAssignedRunsheets :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: getAssignedRunsheets() :: End --------> ::::::");
		return assignedRunsheets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getGeneratedMasterAssignments(
			List<Integer> assignmentHeaderIds, Date runsheetDate)
			throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: getGeneratedMasterAssignments() :: Start --------> ::::::");
		List<Integer> generatedAssignmentHeaderIdList = null;
		try {
			String[] params = { PickupManagementConstants.ASSIGNMENT_HEADER_ID};
			Object[] values = { assignmentHeaderIds};
			generatedAssignmentHeaderIdList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							"getGeneratedMasterRunsheets", params, values);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetDAOImpl::getGeneratedMasterAssignments :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: getGeneratedMasterAssignments() :: End --------> ::::::");
		return generatedAssignmentHeaderIdList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PickupRunsheetHeaderDO> getRunsheetNo(
			Integer assignmentHeaderId, Date runsheetDate)
			throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: getRunsheetNo() :: Start --------> ::::::");
		List<PickupRunsheetHeaderDO> runsheetHeaderDOs = null;
		try {
			String[] params = { PickupManagementConstants.ASSIGNMENT_HEADER_ID};
			Object[] values = { assignmentHeaderId};
			runsheetHeaderDOs = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							PickupManagementConstants.GET_RUNSHEET_NUMBER,
							params, values);

		} catch (Exception e) {
			LOGGER.error("ERROR : GenerateRunsheetDAOImpl :: getRunsheetNo :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: getRunsheetNo() :: End --------> ::::::");
		return runsheetHeaderDOs;
	}

	@Override
	public List<PickupRunsheetHeaderDO> savePickupRunsheet(
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs)
			throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: savePickupRunsheet() :: Start --------> ::::::");
		List<PickupRunsheetHeaderDO> runsheetHeaderDOs = new ArrayList<>();
		try {
			for (PickupRunsheetHeaderDO runsheetHeader : pickupRunsheetHeaderDOs) {
				getHibernateTemplate().save(runsheetHeader);
				runsheetHeaderDOs.add(runsheetHeader);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetDAOImpl::savePickupRunsheet :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: savePickupRunsheet() :: End --------> ::::::");
		return runsheetHeaderDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBranchesUnderHub(Integer loginOfficeId)
			throws CGSystemException {
		LOGGER.trace("GenerateRunsheetDAOImpl :: getBranchesUnderHub() :: Start --------> ::::::");
		List<Object[]> branchDtls = null;
		try {
			branchDtls = getHibernateTemplate().findByNamedQueryAndNamedParam(
					PickupManagementConstants.GET_BRANCHES_UNDER_HUB,
					PickupManagementConstants.LOGIN_OFFICE_ID, loginOfficeId);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetDAOImpl::getBranchesUnderHub :: ", e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetDAOImpl :: getBranchesUnderHub() :: End --------> ::::::");
		return branchDtls;
	}
}
