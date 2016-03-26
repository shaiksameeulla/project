package com.ff.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.ApproverUserOfficeDO;
import com.ff.domain.umc.AssignApproverDO;
import com.ff.umc.AssignApproverTO;

/**
 * Author : Rohini Maladi
 * 
 * @Class : AssignApproverDAO
 * @Desc : DAO Services for UMC - Assign Approver process
 * @Creation Date : Feb - 18 - 2013
 */

public interface AssignApproverDAO {

	List<ApplScreenDO> getAllAssignApplScreens() throws CGSystemException;

	boolean saveOrUpdateAssignApprover(
			List<AssignApproverDO> assignApproverDOList,
			List<ApproverUserOfficeDO> approverUserOfficeDOList)
			throws CGSystemException;

	List<AssignApproverDO> getAssignApproverDetails(
			AssignApproverTO assignApproverTO) throws CGSystemException;

	List<ApproverUserOfficeDO> getApproverUserOfficeDetails(
			AssignApproverTO assignApproverTO) throws CGSystemException;
	
	List<AssignApproverDO> updateAssignApproverDtls(AssignApproverDO assignApproverDO)
			throws CGSystemException;
	
	public List<AssignApproverDO> updateAssignApproverOfficDtls(ApproverUserOfficeDO assignApproverOffcDO)
			throws CGSystemException ;
	
	public List<AssignApproverDO> getOldAssignApproverDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException ;
	
	public List<AssignApproverDO> updateExistingAssignApproverDtlsToActive(AssignApproverDO assignApproverDO)
			throws CGSystemException ;
	
	public List<ApproverUserOfficeDO> getOldApproverUserOfficeDetails(AssignApproverTO assignApproverTO)
			throws CGSystemException;
	
	public List<AssignApproverDO> updateExistingAssignApproverOfficDtls(ApproverUserOfficeDO assignApproverOffcDO)
			throws CGSystemException;
}
