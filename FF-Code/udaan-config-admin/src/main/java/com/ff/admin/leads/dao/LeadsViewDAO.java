/**
 * 
 */
package com.ff.admin.leads.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.umc.UserTO;

/**
 * @author abarudwa
 *
 */
public interface LeadsViewDAO {
	
	List<LeadDO> getLeadsByStatus(final String leadStatusCode) throws CGSystemException;
	
	 List<LeadDO> getLeadsByUser(final UserTO userTO, String effectiveFromDate, String effectiveToDate, String status) throws CGSystemException;
	
	 List<LeadDO> getLeadsByRegion(final Integer regionId, String effectiveFromDate, String effectiveToDate, String status) throws CGSystemException;
	
	 List<EmployeeUserDO> getSalesExecutiveByRegion(final Integer regionId, final String designation)
			throws CGSystemException;


}
