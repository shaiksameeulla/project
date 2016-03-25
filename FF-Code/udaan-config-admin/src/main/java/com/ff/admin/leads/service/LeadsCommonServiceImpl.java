package com.ff.admin.leads.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.converter.LeadConverter;
import com.ff.admin.leads.dao.LeadCommonDAO;
import com.ff.domain.leads.CompetitorDO;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.leads.PlanFeedbackDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserRightsTO;
import com.ff.universe.global.service.GlobalUniversalService;

/**
 * @author sdalli
 * 
 */
public class LeadsCommonServiceImpl implements LeadsCommonService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsCommonServiceImpl.class);

	private LeadCommonDAO leadCommonDAO = null;
	private GlobalUniversalService globalUniversalService = null;

	public void setLeadCommonDAO(LeadCommonDAO leadCommonDAO) {
		this.leadCommonDAO = leadCommonDAO;
	}

	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	@Override
	public List<CompetitorTO> getCompetitorList() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LeadsCommonServiceImpl :: generateLeadNumber() :: Start --------> ::::::");
		List<CompetitorTO> competitorTOs = null;
		List<CompetitorDO> competitorDOs = leadCommonDAO.getCompetitorList();
		if (StringUtil.isEmptyColletion(competitorDOs)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_LIST);
		}
		competitorTOs = new ArrayList<CompetitorTO>();
		for (CompetitorDO competitorDO : competitorDOs) {
			CompetitorTO competitorTO = LeadConverter
					.competitorConverter(competitorDO);
			competitorTOs.add(competitorTO);
		}
		LOGGER.trace("LeadsCommonServiceImpl :: generateLeadNumber() :: End --------> ::::::");
		return competitorTOs;
	}

	@Override
	public List<OfficeTO> getRegionalBranchesList(final Integer regionId,
			final Integer officeTypeId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LeadsCommonServiceImpl :: generateLeadNumber() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = leadCommonDAO.getRegionalBranchesList(
				regionId, officeTypeId);
		if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}

		}
		LOGGER.trace("LeadsCommonServiceImpl :: generateLeadNumber() :: End --------> ::::::");
		return officeTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getSalesPersonsTitlesList(
			final String DepartmentType) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<EmployeeDO> employeeDOs = leadCommonDAO
				.getSalesPersonsTitlesList(DepartmentType);
		if (StringUtil.isEmptyColletion(employeeDOs)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST);
		}
		
		List<EmployeeTO> employeeTOs = null;
		if (!CGCollectionUtils.isEmpty(employeeDOs)) {
			employeeTOs = (List<EmployeeTO>) CGObjectConverter
					.createTOListFromDomainList(employeeDOs, EmployeeTO.class);
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
		return employeeTOs;
	}

	@Override
	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(
			final Integer officeId, final String designation)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getRegionalSalesPersonsList() :: Start --------> ::::::");

		List<EmployeeUserJoinBean> employeeUsers = leadCommonDAO
				.getRegionalSalesPersonsList(officeId, designation);
		if (StringUtil.isEmptyColletion(employeeUsers)) {
			ExceptionUtil.prepareBusinessException(LeadCommonConstants.NO_SALES_EXECUTIVE_PRESENT, new String[]{designation});
			//throw new CGBusinessException(LeadCommonConstants.NO_SALES_EXECUTIVE_PRESENT);
		}
		/*if (!StringUtil.isNull(employeeUserDOs)) {
			employeeTOs = LeadConverter
					.convertTosfromDomainObject(employeeUserDOs);
		}*/
		LOGGER.trace("LeadsCommonServiceImpl :: getRegionalSalesPersonsList() :: End --------> ::::::");
		return employeeUsers;
	}

	@Override
	public List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> competiorProductList = globalUniversalService
				.getStandardTypesByTypeName(LeadCommonConstants.LEAD_COMPETITOR_PRODUCT);
		if (StringUtil.isEmptyColletion(competiorProductList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_PRODUCT_LIST);
		}
		return competiorProductList;
	}

	@Override
	public List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> industryCategoryList =globalUniversalService
				.getStandardTypesByTypeName(LeadCommonConstants.LEAD_INDUSTRY_CATEGORY);
		if (StringUtil.isEmptyColletion(industryCategoryList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_INDUSTRY_CATEGORY_LIST);
		}
		return industryCategoryList;
	}

	@Override
	public List<StockStandardTypeTO> getLeadSourceList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> leadSourceList =  globalUniversalService
				.getStandardTypesByTypeName(LeadCommonConstants.LEAD_LEAD_SOURCE);
		if (StringUtil.isEmptyColletion(leadSourceList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEAD_SOURCE_LIST);
		}
		return leadSourceList;
	}

	@Override
	public LeadTO getLeadDetails(final String leadNumber)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getLeadDetails() :: Start --------> ::::::");
		LeadDO leadDO = leadCommonDAO.getLeadDetails(leadNumber);
		if (StringUtil.isNull(leadDO)) {
			ExceptionUtil.prepareBusinessException(LeadCommonConstants.LEAD_NUMBER_IS_NOT_VALIED);
		}
		LeadTO leadTO = LeadConverter.getLeadDetailsConverter(leadDO);
		if (StringUtil.isNull(leadTO)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getLeadDetails() :: End --------> ::::::");
		return leadTO;
	}

	@Override
	public void savePlan(final PlanTO planTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: savePlan() :: Start --------> ::::::");
		if (StringUtil.isNull(planTO)) {
			PlanFeedbackDO planFeedbackDO = LeadConverter
					.savePlanConverter(planTO);
			try{
				leadCommonDAO.savePlan(planFeedbackDO);
			}catch(Exception e){
				throw new CGBusinessException(e);
			}
		}
		LOGGER.trace("LeadsCommonServiceImpl :: savePlan() :: End --------> ::::::");
	}

	@Override
	public List<StockStandardTypeTO> getFeedbackList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> feedbackList = globalUniversalService
				.getStandardTypesByTypeName(LeadCommonConstants.LEAD_LEAD_FEEDBACK);
		if (StringUtil.isEmptyColletion(feedbackList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_FEEDBACK_LIST);
		}
		return feedbackList;
	}

	@Override
	public void leadManagementPlanCreation() throws CGSystemException,
			CGBusinessException,HttpException, ClassNotFoundException, IOException{
		LOGGER.trace("LeadsCommonServiceImpl :: leadManagementPlanCreation() :: Start --------> ::::::");
		Date fromdate = null;
		long dateDiff = 0l;

		List<LeadDO> leadList = leadCommonDAO.getLeadUpdateDate();
		if (StringUtil.isEmptyColletion(leadList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		if (!StringUtil.isEmptyColletion(leadList)) {
			for (LeadDO leadDO : leadList) {
				fromdate = leadDO.getUpdatedDate();
				if (!StringUtil.isNull(fromdate)) {
					dateDiff = DateUtil.DayDifferenceBetweenTwoDates(fromdate,
							DateUtil.getCurrentDate());
					if (!StringUtil.isNull(dateDiff) && dateDiff >= 30) {
						
						LOGGER.info("leadManagementPlanCreation schedular plan created");
					}
				}
			}
		}

		LOGGER.trace("LeadsCommonServiceImpl :: leadManagementPlanCreation() :: End --------> ::::::");
	}

	@Override
	public PlanTO getPlanFeedbackDetails(String leadNumber)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getPlanFeedbackDetails() :: Start --------> ::::::");
		PlanTO planTO = null;

		PlanFeedbackDO planFeedbackDO = leadCommonDAO
				.getPlanFeedbackDetails(leadNumber);
		if (StringUtil.isNull(planFeedbackDO)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_FEEDBACK_DETAILS);
		}
		if (!StringUtil.isNull(planFeedbackDO)) {
			planTO = LeadConverter
					.getPlanFeedBackDetailsConverter(planFeedbackDO);
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getPlanFeedbackDetails() :: End --------> ::::::");
		return planTO;
	}

	@Override
	public List<PlanTO> getLeadsPlanningDtlsOrdeByTimeDesc(String leadNumber)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getLeadsPlanningDtlsOrdeByTimeDesc() :: Start --------> ::::::");
		List<PlanTO> planTOs = null;
		List<PlanFeedbackDO> planFeedbackDOs = leadCommonDAO
				.getLeadsPlanningDtlsOrdeByTimeDesc(leadNumber);
		if (StringUtil.isEmptyColletion(planFeedbackDOs)) {
			throw new CGBusinessException(LeadCommonConstants.CREATE_PLAN_BEFORE_FEEDBACK);
		}
		if (!StringUtil.isEmptyColletion(planFeedbackDOs)) {
			planTOs = new ArrayList<PlanTO>();
			for (PlanFeedbackDO planFeedbackDO : planFeedbackDOs) {
				PlanTO planTO = new PlanTO();
				LeadConverter.convertFeedbackTOfromDO(planTO, planFeedbackDO);
				planTOs.add(planTO);
			}

		}
		LOGGER.trace("LeadsCommonServiceImpl :: getLeadsPlanningDtlsOrdeByTimeDesc() :: End --------> ::::::");
		return planTOs;
	}

	@Override
	public List<OfficeTO> getBranchesUnderReportingRHO(Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderReportingRHO() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = leadCommonDAO.getBranchesUnderReportingRHO(officeId);
		if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderReportingRHO() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = leadCommonDAO.getBranchesUnderReportingHub(officeId);
		if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getBranchesUnderCorporateOffice()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderCorporateOffice() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = leadCommonDAO.getBranchesUnderCorporateOffice();
		if (StringUtil.isEmptyColletion(officeDOList)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getBranchesUnderCorporateOffice() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<UserRightsTO> getUserRoleById(Integer roleId)
			throws CGBusinessException, CGBaseException {
		LOGGER.trace("LeadsCommonServiceImpl :: getUserRoleById() :: Start --------> ::::::");
		List<UserRightsTO> userRightsTOList = null;
		List<UserRightsDO> userRightsDOList = null;
			userRightsDOList = leadCommonDAO.getUserRoleById(roleId);
			userRightsTOList = new ArrayList<>();
			if (userRightsDOList != null) {
				for(UserRightsDO userRightsDO :userRightsDOList)
				{
					UserRightsTO userRightsTO = new UserRightsTO();
					userRightsTO = (UserRightsTO) CGObjectConverter
							.createToFromDomain(userRightsDO, userRightsTO);
					userRightsTO.setRoleName(userRightsDO.getRoleDO().getRoleName());
					userRightsTOList.add(userRightsTO);
				}
			}
			if(StringUtil.isEmptyList(userRightsTOList)){
				throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_USER_ROLES);
			}
		
		LOGGER.trace("LeadsCommonServiceImpl :: getUserRoleById() :: End --------> ::::::");
		return userRightsTOList;
	}

	@Override
	public List<OfficeTO> getOfficesMappedToUser(Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsCommonServiceImpl :: getOfficesMappedToUser() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = null;

		List<OfficeDO> officeDOList = leadCommonDAO.getOfficesMappedToUser(userId);
		if (!StringUtil.isEmptyColletion(officeDOList)) {
			officeTOList = new ArrayList<>();
			for (OfficeDO officeDO : officeDOList) {
				OfficeTO officeTO = new OfficeTO();
				officeTO = (OfficeTO) CGObjectConverter.createToFromDomain(
						officeDO, officeTO);
				officeTOList.add(officeTO);
			}
		}
		LOGGER.trace("LeadsCommonServiceImpl :: getOfficesMappedToUser() :: End --------> ::::::");
		return officeTOList;
	}
}
