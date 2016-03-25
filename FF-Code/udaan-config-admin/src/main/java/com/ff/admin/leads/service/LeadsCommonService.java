package com.ff.admin.leads.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserRightsTO;

/**
 * @author sdalli
 * 
 */
public interface LeadsCommonService {
	
	List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException;

	List<CompetitorTO> getCompetitorList() throws CGBusinessException,
			CGSystemException;

	List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException;

	List<StockStandardTypeTO> getLeadSourceList() throws CGSystemException,
			CGBusinessException;

	List<OfficeTO> getRegionalBranchesList(final Integer regionId,
			final Integer officeTypeId) throws CGBusinessException,
			CGSystemException;

	List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType)
			throws CGSystemException, CGBusinessException;

	List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId,
			final String designation) throws CGSystemException,
			CGBusinessException;

	LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,
			CGBusinessException;

	void savePlan(final PlanTO planTO) throws CGSystemException,
			CGBusinessException;

	PlanTO getPlanFeedbackDetails(final String leadNumber)
			throws CGSystemException, CGBusinessException;

	List<StockStandardTypeTO> getFeedbackList() throws CGSystemException,
			CGBusinessException;

	void leadManagementPlanCreation() throws CGSystemException,
			CGBusinessException, HttpException, ClassNotFoundException, IOException;

	List<PlanTO> getLeadsPlanningDtlsOrdeByTimeDesc(String leadNumber)
			throws CGSystemException, CGBusinessException;
	
	public List<OfficeTO> getBranchesUnderReportingRHO(final Integer officeId)
			throws CGSystemException,CGBusinessException;

	public List<OfficeTO> getBranchesUnderReportingHub(final Integer officeId)
			throws CGSystemException,CGBusinessException;
	
	public List<OfficeTO> getBranchesUnderCorporateOffice() throws CGSystemException,
			CGBusinessException;
	
	public List<UserRightsTO> getUserRoleById(Integer roleId)
			throws CGBusinessException, CGBaseException;
	
	public List<OfficeTO> getOfficesMappedToUser(Integer userId) throws CGSystemException,
			CGBusinessException;
	
}
