package com.ff.admin.leads.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

public interface LeadsValidationService {

	String generateLeadNumber(final String officeCode)
			throws CGBusinessException, CGSystemException;

	List<CompetitorTO> getCompetitorList() throws CGBusinessException,
			CGSystemException;

	List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException;

	List<StockStandardTypeTO> getLeadSourceList() throws CGSystemException,
			CGBusinessException;

	List<OfficeTO> getRegionalBranchesList(final Integer regionId,
			final Integer officeTypeId) throws CGBusinessException,
			CGSystemException;

	List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId,
			final String designation) throws CGSystemException,
			CGBusinessException;

	List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType)
			throws CGSystemException, CGBusinessException;

	List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException;

	LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,
			CGBusinessException;

	String approveLead(final LeadTO leadTO) throws CGBusinessException,
			CGSystemException;

	List<EmployeeUserTO> getSalesExecutive(final Integer officeId)
			throws CGSystemException, CGBusinessException;
	
	public List<OfficeTO> getBranchesUnderReportingRHO(final Integer officeId,Integer userId)
			throws CGSystemException, CGBusinessException;

	public List<OfficeTO> getBranchesUnderReportingHub(final Integer officeId,Integer userId)
			throws CGSystemException, CGBusinessException;
	
	public List<OfficeTO> getBranchesUnderCorporateOffice()
			throws CGSystemException, CGBusinessException;
	
	public List<OfficeTO> getOfficesMappedToUser(Integer userId)
			throws CGSystemException, CGBusinessException;
}
