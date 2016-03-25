/**
 * 
 */
package com.ff.admin.leads.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.umc.UserTO;

/**
 * @author abarudwa
 *
 */
public class LeadsViewDAOImpl extends CGBaseDAO implements LeadsViewDAO{
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsViewDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<LeadDO> getLeadsByStatus(final String leadStatusCode) throws CGSystemException {
		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByStatus() :: Start --------> ::::::");
		List<LeadDO> leadDOs = null;
		try{
			leadDOs=getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_LEADS_BY_STATUS,
							new String[] { LeadCommonConstants.LEAD_STATUS_CODE },
							new Object[] { leadStatusCode });
		}
		catch(Exception e){
			LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getLeadsByStatus() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByStatus() :: End --------> ::::::");
		
		return leadDOs;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeadDO> getLeadsByUser(UserTO userTO, String effectiveFromDate, String effectiveToDate, String status) throws  CGSystemException {
		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByUser() :: Start --------> ::::::");
		List<LeadDO> leadDOs = null;
		
		if(status.equalsIgnoreCase("ALL")) {	
			try{
				leadDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
								LeadCommonConstants.QRY_GET_LEADS_BY_USER,
								new String[] {LeadCommonConstants.USER_ID, "effectiveFrom", "effectiveTo"},
								new Object[] {userTO.getUserId(), DateUtil.stringToDDMMYYYYFormat(effectiveFromDate), DateUtil.stringToDDMMYYYYFormat(effectiveToDate)});
			}
			catch(Exception e){
				LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getLeadsByUser() :: " + e);
				throw new CGSystemException(e);
			}
		} else {
			try{
				leadDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
								LeadCommonConstants.QRY_GET_LEADS_BY_USER_FOR_ALL_STATUS,
								new String[] {LeadCommonConstants.USER_ID, "effectiveFrom", "effectiveTo", "leadStatusCode"},
								new Object[] {userTO.getUserId(), DateUtil.stringToDDMMYYYYFormat(effectiveFromDate), DateUtil.stringToDDMMYYYYFormat(effectiveToDate), status});
			}
			catch(Exception e){
				LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getLeadsByUser() :: " + e);
				throw new CGSystemException(e);
			}
			
		}
		
		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByUser() :: End --------> ::::::");
		
		return leadDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeadDO> getLeadsByRegion(Integer regionId, String effectiveFromDate, String effectiveToDate, String status) throws CGSystemException {
		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByRegion() :: Start --------> ::::::");
		List<LeadDO> leadDOs = null;
		if(status.equalsIgnoreCase("ALL")) {
			try{
				leadDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
								LeadCommonConstants.QRY_GET_LEADS_BY_REGION,
								new String[] {LeadCommonConstants.DEPARTMENT_NAME, LeadCommonConstants.REGION_ID, "effectiveFrom", "effectiveTo"},
								new Object[] {LeadCommonConstants.DEPARTMENT_NAME_TYPE, regionId, DateUtil.stringToDDMMYYYYFormat(effectiveFromDate), DateUtil.stringToDDMMYYYYFormat(effectiveToDate)});
			}
			catch(Exception e){
				LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getLeadsByRegion() :: " + e);
				throw new CGSystemException(e);
			}
		} else {
			try{
				leadDOs = getHibernateTemplate()
							.findByNamedQueryAndNamedParam(
								LeadCommonConstants.QRY_GET_LEADS_BY_REGION_AND_STATUS,
								new String[] {LeadCommonConstants.DEPARTMENT_NAME, LeadCommonConstants.REGION_ID, "effectiveFrom", "effectiveTo", "leadStatusCode"},
								new Object[] {LeadCommonConstants.DEPARTMENT_NAME_TYPE, regionId, 
										DateUtil.stringToDDMMYYYYFormat(effectiveFromDate), DateUtil.stringToDDMMYYYYFormat(effectiveToDate), status});
			}
			catch(Exception e){
				LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getLeadsByRegion() :: " + e);
				throw new CGSystemException(e);
			}			
		}

		LOGGER.trace("LeadsViewDAOImpl :: getLeadsByRegion() :: End --------> ::::::");
		
		return leadDOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeUserDO> getSalesExecutiveByRegion(
			 Integer regionId,String designation) throws CGSystemException {
		LOGGER.trace("LeadsViewDAOImpl :: getSalesExecutiveByRegion() :: Start --------> ::::::");
		List<EmployeeUserDO> employeeUserDOList = null;
		try{
			employeeUserDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							LeadCommonConstants.QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_REGION_ID,
							new String[] { LeadCommonConstants.DEPARTMENT_NAME,LeadCommonConstants.REGION_ID,LeadCommonConstants.DESIGNATION},
							new Object[] { LeadCommonConstants.DEPARTMENT_NAME_TYPE,regionId,designation});
			
			}catch(Exception e){
			LOGGER.error("Exception Occured in::LeadsViewDAOImpl::getSalesExecutiveByRegion() :: "
					+ e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("LeadsViewDAOImpl :: getSalesExecutiveByRegion() :: End --------> ::::::");
		return employeeUserDOList;
	}

	
}



