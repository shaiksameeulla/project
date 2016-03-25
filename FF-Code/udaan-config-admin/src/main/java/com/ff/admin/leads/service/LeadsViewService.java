/**
 * 
 */
package com.ff.admin.leads.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.leads.LeadTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserTO;
import com.ff.domain.leads.EmployeeUserJoinBean;
/**
 * @author abarudwa
 *
 */
public interface LeadsViewService 
{
	 List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType)
			throws CGSystemException, CGBusinessException;
	
	 List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId, final String designation)
			throws CGSystemException,CGBusinessException ;
	
	 List<StockStandardTypeTO> getLeadStatusList()
			throws CGSystemException, CGBusinessException;
	
	 List<LeadTO> getLeadsByStatus(final String leadStatusCode) 
			throws CGBusinessException,CGSystemException;
	
	 List<LeadTO> getLeadsByUser(UserTO userTO, String effectiveFromDate, String effectiveToDate, String status) throws CGBusinessException, CGSystemException;
	
	 List<UserRightsTO> getUserRoleById(Integer roleId)
			throws CGBusinessException, CGBaseException;
	
	 List<LeadTO> getLeadsByRegion(Integer regionId, String effectiveFromDate, String effectiveToDate, String status) throws CGBusinessException, CGSystemException;
	
	 List<EmployeeUserTO> getSalesExecutiveByRegion(final Integer regionId,final String designation)
			throws CGSystemException,CGBusinessException ;
}
