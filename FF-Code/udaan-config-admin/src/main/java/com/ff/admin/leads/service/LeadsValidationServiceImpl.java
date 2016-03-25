package com.ff.admin.leads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.converter.LeadConverter;
import com.ff.admin.leads.dao.LeadsValidationDAO;
import com.ff.admin.leads.utils.LeadManagementUtils;
import com.ff.domain.leads.EmployeeUserJoinBean;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

public class LeadsValidationServiceImpl implements LeadsValidationService{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsValidationService.class);

	private LeadsCommonService leadsCommonService;
	
	private LeadsValidationDAO leadValidationDAO;
	
	
	/**
	 * @param leadValidationDAO the leadValidationDAO to set
	 */
	public void setLeadValidationDAO(LeadsValidationDAO leadValidationDAO) {
		this.leadValidationDAO = leadValidationDAO;
	}


	/**
	 * @param leadsCommonService the leadsCommonService to set
	 */
	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}

	
	@Override
	public String generateLeadNumber(String officeCode) throws CGBusinessException,
			CGSystemException {
		return LeadManagementUtils.generateLeadNumber(LeadCommonConstants.LEAD_NUMBER_START_CODE, officeCode, LeadCommonConstants.LEAD_NUMBER);
	}
	
	public List<CompetitorTO> getCompetitorList() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LeadsValidationServiceImpl :: getCompetitorList() :: Start --------> ::::::");
		List<CompetitorTO> competitorTOList = null;
		competitorTOList = leadsCommonService.getCompetitorList();
		if(StringUtil.isEmptyList(competitorTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getCompetitorList() :: End --------> ::::::");
		return competitorTOList;
	}
	
	@Override
	public List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getIndustryCategoryList() :: Start --------> ::::::");
		List<StockStandardTypeTO> stockStandardTypeTO = leadsCommonService.getIndustryCategoryList();
		if(StringUtil.isEmptyList(stockStandardTypeTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_INDUSTRY_CATEGORY_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getIndustryCategoryList() :: End --------> ::::::");
		return stockStandardTypeTO;	
	}
	
	@Override
	public List<StockStandardTypeTO> getLeadSourceList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getLeadSourceList() :: Start --------> ::::::");
		List<StockStandardTypeTO> stockStandardTypeTOs = leadsCommonService.getLeadSourceList();
		if(StringUtil.isEmptyList(stockStandardTypeTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEAD_SOURCE_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getLeadSourceList() :: End --------> ::::::");
		return stockStandardTypeTOs;
	}
	
	@Override
	public List<OfficeTO> getRegionalBranchesList(Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LeadsValidationServiceImpl :: getRegionalBranchesList() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getRegionalBranchesList(regionId, officeTypeId);
		if(StringUtil.isEmptyList(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getRegionalBranchesList() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId, final String designation)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getRegionalSalesPersonsList() :: Start --------> ::::::");
		List<EmployeeUserJoinBean> salesPersonList = leadsCommonService.getRegionalSalesPersonsList(officeId,designation);
		if(StringUtil.isEmptyList(salesPersonList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_SALES_PERSON_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getRegionalSalesPersonsList() :: End --------> ::::::");
		return salesPersonList;
	}
	
	@Override
	public List<EmployeeTO> getSalesPersonsTitlesList(String DepartmentType)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<EmployeeTO> salesPersonsTitlesList = leadsCommonService.getSalesPersonsTitlesList(DepartmentType);
		if(StringUtil.isEmptyList(salesPersonsTitlesList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
		return salesPersonsTitlesList;
	}
	
	@Override
	public List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getCompetitorProductList() :: Start --------> ::::::");
		List<StockStandardTypeTO> competitorProductList = leadsCommonService.getCompetitorProductList();
		if(StringUtil.isEmptyList(competitorProductList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_PRODUCT_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getCompetitorProductList() :: End --------> ::::::");
		return competitorProductList;
	}

	@Override
	public LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,CGBusinessException{
		LOGGER.trace("LeadsValidationServiceImpl :: getLeadDetails() :: Start --------> ::::::");
		LeadDO leadDO = leadValidationDAO.getLeadDetails(leadNumber);
		LeadTO leadTO =LeadConverter.validationDomainConverter(leadDO);
		if(StringUtil.isNull(leadTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getLeadDetails() :: End --------> ::::::");
		return leadTO;
		
	}
	
	@Override
	public String approveLead(final LeadTO leadTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LeadsValidationServiceImpl :: approveLead() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		String isLeadSaved = null;
		LeadDO leadDO2 = LeadConverter.leadValidationConverter(leadTO);
		if (!StringUtil.isNull(leadDO2)) {
			isSaved = leadValidationDAO.approveLead(leadDO2);
		}
		if(isSaved){
			 isLeadSaved = LeadCommonConstants.SUCCESS;
		}
		if(!isSaved){
			throw new CGBusinessException(LeadCommonConstants.LEAD_NOT_APPROVED);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: approveLead() :: End --------> ::::::");	
		return isLeadSaved;
	
	}
	
	@Override
	public List<EmployeeUserTO> getSalesExecutive(Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getSalesExecutive() :: Start --------> ::::::");
		List<EmployeeUserTO> employeeTOs= null;
		List<EmployeeUserDO> employeeUserDOs = leadValidationDAO
				.getSalesExecutive(officeId);
		if(StringUtil.isEmptyList(employeeUserDOs)){
			throw new CGBusinessException(LeadCommonConstants.NO_SALES_EXECUTIVE_FOR_SELECTD_BRANCH);
		}
		if(!StringUtil.isNull(employeeUserDOs)){
			 employeeTOs=LeadConverter.convertTosfromDomainObject(employeeUserDOs);
		}
		if(StringUtil.isEmptyList(employeeTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_EXECUTIVE_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getSalesExecutive() :: End --------> ::::::");
		return employeeTOs;
	}


	@Override
	public List<OfficeTO> getBranchesUnderReportingRHO(Integer officeId,Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderReportingRHO() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getBranchesUnderReportingRHO(officeId);
		if(StringUtil.isEmptyColletion(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		List<OfficeTO> officeTOs = getOfficesMappedToUser(userId);
		if(!StringUtil.isEmptyColletion(officeTOs)){
			for(OfficeTO officeTO : officeTOs){
				Boolean isExist = Boolean.FALSE;
				for (OfficeTO officeTO2 : officeTOList) {
					if(officeTO2.getOfficeId().equals(officeTO.getOfficeId())){
						isExist = Boolean.TRUE;
						break;
					}
				}
				if(!isExist){
					officeTOList.add(officeTO);
				}
			}
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderReportingRHO() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId,Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getBranchesUnderReportingHub(officeId);
		if(StringUtil.isEmptyColletion(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		List<OfficeTO> officeTOs = getOfficesMappedToUser(userId);
		if(!StringUtil.isEmptyColletion(officeTOs)){
			for(OfficeTO officeTO : officeTOs){
				Boolean isExist = Boolean.FALSE;
				for(OfficeTO officeTO2 : officeTOList){
					if(officeTO2.getOfficeId().equals(officeTO.getOfficeId())){
						isExist = Boolean.TRUE;
						break;
					}
				}
				if(!isExist){
					officeTOList.add(officeTO);
				}
			}
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeTOList;
	}


	@Override
	public List<OfficeTO> getBranchesUnderCorporateOffice()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderCorporateOffice() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getBranchesUnderCorporateOffice();
		if(StringUtil.isEmptyColletion(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		LOGGER.trace("LeadsValidationServiceImpl :: getBranchesUnderCorporateOffice() :: End --------> ::::::");
		return officeTOList;
	}


	@Override
	public List<OfficeTO> getOfficesMappedToUser(Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("LeadsValidationServiceImpl :: getOfficesMappedToUser() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getOfficesMappedToUser(userId);
		LOGGER.trace("LeadsValidationServiceImpl :: getOfficesMappedToUser() :: End --------> ::::::");
		return officeTOList;
	}
	
}
