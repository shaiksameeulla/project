package com.ff.admin.leads.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.CompetitorDO;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.leads.PlanFeedbackDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserRightsDO;

/**
 * @author sdalli
 *
 */
public interface LeadCommonDAO {
	 /*Create Lead Module START*/
	 List<EmployeeDO> getSalesPersonsTitlesList(final String DepartmentType) throws CGSystemException;
	
	 List<CompetitorDO> getCompetitorList() throws CGSystemException;
	
	 List<OfficeDO> getRegionalBranchesList(final Integer regionId, final Integer officeTypeId) throws CGSystemException;
	
	//branchId and designation type
	 List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId,final String designation)throws CGSystemException;
	 /*Create Lead Module END*/
	 
	 /*Lead Planning Module START*/
	 LeadDO getLeadDetails(final String leadNumber) throws CGSystemException;
	 
	 void savePlan(final PlanFeedbackDO planFeedbackDO)throws CGSystemException;
	 /*Lead Planning Module END*/
	 
	 /*Lead FeedBack Module START*/
	 PlanFeedbackDO getPlanFeedbackDetails(final String leadNumber)throws CGSystemException;
	 
	 /*Lead FeedBack Module END*/
	 
	 /*lead Schedular Start*/
	 
	 List<LeadDO> getLeadUpdateDate() throws CGSystemException;
	 /*lead Schedular END */
	 
	 
	 Boolean savePlan(final List<PlanFeedbackDO> planFeedbackDOList)throws CGSystemException;
	 
	 List<PlanFeedbackDO> getLeadsPlanningDtlsOrdeByTimeDesc(final String leadNumber)throws CGSystemException;
	 
	 
	 void updateLeadStatus(Integer leadId,String updatingStatus)throws CGSystemException;
	 
	 public List<OfficeDO> getBranchesUnderReportingRHO(final Integer officeId) throws CGSystemException;
	 
	 public List<OfficeDO> getBranchesUnderReportingHub(final Integer officeId) throws CGSystemException;
	 
	 public List<OfficeDO> getBranchesUnderCorporateOffice() throws CGSystemException;
	 
	 public List<UserRightsDO> getUserRoleById(Integer userId) throws CGSystemException;
	 
	 public List<OfficeDO> getOfficesMappedToUser(Integer userId) throws CGSystemException;
	 
}
