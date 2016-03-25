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

public interface CreateLeadService {

	public String generateLeadNumber(final String officeCode)
			throws CGBusinessException, CGSystemException;

	public List<CompetitorTO> getCompetitorList() throws CGBusinessException,
			CGSystemException;

	public List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException;

	public List<StockStandardTypeTO> getLeadSourceList()
			throws CGSystemException, CGBusinessException;

	public List<OfficeTO> getRegionalBranchesList(final Integer regionId,
			final Integer officeTypeId) throws CGBusinessException,
			CGSystemException;

	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(
			final Integer officeId, final String designation)
			throws CGSystemException, CGBusinessException;

	public List<EmployeeTO> getSalesPersonsTitlesList(
			final String DepartmentType) throws CGSystemException,
			CGBusinessException;

	public List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException;

	public String saveLead(final LeadTO leadTO) throws CGBusinessException,
			CGSystemException;

	public List<EmployeeUserTO> getSalesExecutive(final Integer officeId)
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
