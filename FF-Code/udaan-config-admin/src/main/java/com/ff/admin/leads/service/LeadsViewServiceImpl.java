/**
 * 
 */
package com.ff.admin.leads.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.converter.LeadConverter;
import com.ff.admin.leads.dao.LeadsViewDAO;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.leads.LeadTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserTO;
import com.ff.universe.global.service.GlobalUniversalService;

/**
 * @author abarudwa
 *
 */
public class LeadsViewServiceImpl implements LeadsViewService
{
	private LeadsCommonService leadsCommonService;
	
	private LeadsViewDAO leadsViewDAO;
	
	private final static Logger LOGGER= LoggerFactory.getLogger(LeadsViewServiceImpl.class);


	public void setLeadsViewDAO(LeadsViewDAO leadsViewDAO) {
		this.leadsViewDAO = leadsViewDAO;
	}

	private GlobalUniversalService globalUniversalService;
	
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}

	@Override
	public List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType)
			throws CGSystemException, CGBusinessException {
		List<EmployeeTO> salesPersonTitleList = leadsCommonService.getSalesPersonsTitlesList(DepartmentType);
		if(StringUtil.isEmptyList(salesPersonTitleList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST);
		}
		return salesPersonTitleList;
	}

	@Override
	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId,
			final String designation) throws CGSystemException, CGBusinessException {
		List<EmployeeUserJoinBean> regionalSalesPersonList = leadsCommonService.getRegionalSalesPersonsList(officeId, designation);
		if(StringUtil.isEmptyList(regionalSalesPersonList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_SALES_PERSON_LIST);
		}
		return regionalSalesPersonList;
	}
	
	public List<StockStandardTypeTO> getLeadStatusList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> leadsStatusList = globalUniversalService.getStandardTypesByTypeName(LeadCommonConstants.LEAD_LEAD_STATUS);
		if(StringUtil.isEmptyList(leadsStatusList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_STAUS_LIST);
		}
		return leadsStatusList;
	}

	@Override
	public List<LeadTO> getLeadsByStatus(String leadStatusCode)
			throws CGBusinessException, CGSystemException {		
		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByStatus() :: Start --------> ::::::");
		List<LeadDO> leadDOs = leadsViewDAO.getLeadsByStatus(leadStatusCode);
		if(StringUtil.isEmptyList(leadDOs)){
			ExceptionUtil.prepareBusinessException(LeadCommonConstants.NO_LEADS_AVAILABLE, new String[]{leadStatusCode});
			//throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
 		List<LeadTO> leadTOs = new ArrayList<LeadTO>();
 		for(LeadDO leadDO:leadDOs){
 			LeadTO leadTO = LeadConverter.getLeadDetailsConverter(leadDO);
 			leadTOs.add(leadTO);	
 		}
 		if(StringUtil.isEmptyList(leadTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
 		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByStatus() :: End --------> ::::::");
		return leadTOs;
	}

	@Override
	public List<LeadTO> getLeadsByUser(UserTO userTO, String effectiveFromDate, String effectiveToDate, String status) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByUser() :: Start --------> ::::::");
		List<LeadDO> leadDOs =leadsViewDAO.getLeadsByUser(userTO, effectiveFromDate, effectiveToDate, status);
		List<LeadTO> leadTOs = new ArrayList<LeadTO>();
		if(!StringUtil.isEmptyColletion(leadDOs)){
			for(LeadDO leadDO:leadDOs){
				LeadTO leadTO = LeadConverter.getLeadDetailsConverter(leadDO);
				leadTOs.add(leadTO);
				}
		}
/*		else{
			throw new CGBusinessException(LeadCommonConstants.NO_LEADS_AVAILABLE_FOR_PROCESSING);
		}*/
		
		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByUser() :: End --------> ::::::");
		return leadTOs;
	}
	
	@Override
	public List<UserRightsTO> getUserRoleById(Integer roleId)
			throws CGBusinessException, CGBaseException {
		LOGGER.trace("LeadsViewServiceImpl :: getUserRoleById() :: Start --------> ::::::");
		List<UserRightsTO> userRightsTOList = null;
		userRightsTOList = leadsCommonService.getUserRoleById(roleId);

		if (StringUtil.isEmptyList(userRightsTOList)) {
			throw new CGBusinessException(
					LeadCommonConstants.ERROR_IN_GETTING_USER_ROLES);
		}

		LOGGER.trace("LeadsViewServiceImpl :: getUserRoleById() :: End --------> ::::::");
		return userRightsTOList;
	}

	@Override
	public List<LeadTO> getLeadsByRegion(final Integer regionId, String effectiveFromDate, String effectiveToDate, String status) 
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByRegion() :: Start --------> ::::::");
		List<LeadDO> leadDOs = leadsViewDAO.getLeadsByRegion(regionId, effectiveFromDate, effectiveToDate, status);
		List<LeadTO> leadTOs = new ArrayList<LeadTO>();
		if(!StringUtil.isEmptyColletion(leadDOs)){
			for (LeadDO leadDO : leadDOs) {
				LeadTO leadTO = LeadConverter.getLeadDetailsConverter(leadDO);
				leadTOs.add(leadTO);
			}
		}
/*		else{
			// throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
			throw new CGBusinessException(LeadCommonConstants.NO_LEADS_AVAILABLE_FOR_PROCESSING);
		}*/
		LOGGER.trace("LeadsViewServiceImpl :: getLeadsByRegion() :: End --------> ::::::");
		return leadTOs;
	}

	@Override
	public List<EmployeeUserTO> getSalesExecutiveByRegion(final Integer regionId,final String designation)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsViewServiceImpl :: getSalesExecutiveByRegion() :: Start --------> ::::::");
		List<EmployeeUserTO> employeeUserTOs= null;
		
		List<EmployeeUserDO> employeeUserDOs = leadsViewDAO
				.getSalesExecutiveByRegion(regionId,designation);
		if(StringUtil.isEmptyList(employeeUserDOs)){
			ExceptionUtil.prepareBusinessException(LeadCommonConstants.NO_SALES_EXECUTIVE_PRESENT, new String[]{designation});
			//throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_EXECUTIVE_LIST);
		}
		
		if(!StringUtil.isNull(employeeUserDOs)){
			employeeUserTOs=LeadConverter.convertTosfromDomainObject(employeeUserDOs);
		}
		if(StringUtil.isEmptyList(employeeUserTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_EXECUTIVE_LIST);
		}
		LOGGER.trace("LeadsViewServiceImpl :: getSalesExecutiveByRegion() :: End --------> ::::::");
		return employeeUserTOs;
	}


}
