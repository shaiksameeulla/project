package com.ff.admin.leads.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.converter.LeadConverter;
import com.ff.admin.leads.dao.CreateLeadDAO;
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

/**
 * @author abarudwa
 *
 */
public class CreateLeadServiceImpl implements CreateLeadService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateLeadServiceImpl.class);	
	
	private CreateLeadDAO createLeadDAO;
	
	private LeadsCommonService leadsCommonService;

	public void setCreateLeadDAO(CreateLeadDAO createLeadDAO) {
		this.createLeadDAO = createLeadDAO;
	}
	
	/**
	 * @param leadsCommonService the leadsCommonService to set
	 */
	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}


	
	@Override
	public String generateLeadNumber(final String officeCode) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CreateLeadServiceImpl :: generateLeadNumber() :: Start --------> ::::::");
		String generatedNumber = LeadManagementUtils.generateLeadNumber(LeadCommonConstants.LEAD_NUMBER_START_CODE, officeCode, LeadCommonConstants.LEAD_NUMBER);
		if(StringUtil.isStringEmpty(generatedNumber)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GENERATING_LEAD_NUMBER);
		}
		    
		LOGGER.trace("CreateLeadServiceImpl :: generateLeadNumber() :: End --------> ::::::");
		return generatedNumber;
		
	}
	
	public List<CompetitorTO> getCompetitorList() throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreateLeadServiceImpl :: getCompetitorList() :: Start --------> ::::::");
		List<CompetitorTO> competitorTOList = null;
		competitorTOList = leadsCommonService.getCompetitorList();
		if(StringUtil.isEmptyColletion(competitorTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_LIST);
		}
		    
		LOGGER.trace("CreateLeadServiceImpl :: getCompetitorList() :: End --------> ::::::");
		return competitorTOList;
		
	}
	
	@Override
	public List<StockStandardTypeTO> getIndustryCategoryList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getIndustryCategoryList() :: Start --------> ::::::");
		List<StockStandardTypeTO> stockStandardTypeTO = leadsCommonService.getIndustryCategoryList();
		if(StringUtil.isEmptyColletion(stockStandardTypeTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_INDUSTRY_CATEGORY_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getIndustryCategoryList() :: End --------> ::::::");
		return stockStandardTypeTO;	
	}
	
	@Override
	public List<StockStandardTypeTO> getLeadSourceList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getLeadSourceList() :: Start --------> ::::::");
		List<StockStandardTypeTO> stockStandardTypeTOs = leadsCommonService.getLeadSourceList();
		if(StringUtil.isEmptyColletion(stockStandardTypeTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEAD_SOURCE_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getLeadSourceList() :: End --------> ::::::");
		return stockStandardTypeTOs;
	}
	
	@Override
	public List<OfficeTO> getRegionalBranchesList(final Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreateLeadServiceImpl :: getRegionalBranchesList() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getRegionalBranchesList(regionId, officeTypeId);
		if(StringUtil.isEmptyColletion(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getRegionalBranchesList() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<EmployeeUserJoinBean> getRegionalSalesPersonsList(final Integer officeId, final String designation)
			throws CGSystemException, CGBusinessException {
	LOGGER.trace("CreateLeadServiceImpl :: getRegionalSalesPersonsList() :: Start --------> ::::::");
	List<EmployeeUserJoinBean> salesPersonList = leadsCommonService.getRegionalSalesPersonsList(officeId,designation);
	if(StringUtil.isEmptyColletion(salesPersonList)){
		throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_SALES_PERSON_LIST);
	}
	LOGGER.trace("CreateLeadServiceImpl :: getRegionalSalesPersonsList() :: End --------> ::::::");	
	return salesPersonList;
	}
	
	@Override
	public List<EmployeeTO> getSalesPersonsTitlesList(final String DepartmentType)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getSalesPersonsTitlesList() :: Start --------> ::::::");
		List<EmployeeTO> salesPersonsTitlesList = leadsCommonService.getSalesPersonsTitlesList(DepartmentType);
		if(StringUtil.isEmptyColletion(salesPersonsTitlesList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getSalesPersonsTitlesList() :: End --------> ::::::");
		return salesPersonsTitlesList;
	}
	
	@Override
	public List<StockStandardTypeTO> getCompetitorProductList()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getCompetitorProductList() :: Start --------> ::::::");
		List<StockStandardTypeTO> competitorProductList = leadsCommonService.getCompetitorProductList();
		if(StringUtil.isEmptyColletion(competitorProductList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_COMPETITOR_PRODUCT_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getCompetitorProductList() :: End --------> ::::::");
		return competitorProductList;
	}

	@Override
	public String saveLead(final LeadTO leadTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CreateLeadServiceImpl :: saveLead() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		String isLeadSaved = "N";
		LeadDO leadDO2 = LeadConverter.leadConverter(leadTO);
		if (!StringUtil.isNull(leadDO2)) {
			isSaved = createLeadDAO.saveLead(leadDO2);
		}else if(StringUtil.isNull(leadDO2)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_SAVING_LEAD);
		}
		if (isSaved){
			isLeadSaved = LeadCommonConstants.SUCCESS;
		}
		LOGGER.trace("CreateLeadServiceImpl :: saveLead() :: End --------> ::::::");
		return isLeadSaved;
	
	}
	
	@Override
	public List<EmployeeUserTO> getSalesExecutive(final Integer officeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getSalesExecutive() :: Start --------> ::::::");
		List<EmployeeUserTO> employeeTOs= null;
		
		List<EmployeeUserDO> employeeUserDOs = createLeadDAO
				.getSalesExecutive(officeId);
		if (StringUtil.isEmptyColletion(employeeUserDOs)) {
			throw new CGBusinessException(LeadCommonConstants.NO_SALES_EXECUTIVE_FOR_SELECTD_BRANCH);
		}

		if(!StringUtil.isNull(employeeUserDOs)){
			 employeeTOs=LeadConverter.convertTosfromDomainObject(employeeUserDOs);
		}
		if (StringUtil.isEmptyColletion(employeeTOs)) {
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_SALES_EXECUTIVE_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getSalesExecutive() :: End --------> ::::::");
		return employeeTOs;
	}

	@Override
	public List<OfficeTO> getBranchesUnderReportingRHO(Integer officeId,Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderReportingRHO() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getBranchesUnderReportingRHO(officeId);
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
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderReportingRHO() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getBranchesUnderReportingHub(Integer officeId,
			Integer userId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderReportingHub() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService
				.getBranchesUnderReportingHub(officeId);
		if (StringUtil.isEmptyColletion(officeTOList)) {
			throw new CGBusinessException(
					LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		List<OfficeTO> officeTOs = getOfficesMappedToUser(userId);
		if (!StringUtil.isEmptyColletion(officeTOs)) {
			for (OfficeTO officeTO : officeTOs) {
				Boolean isExist = Boolean.FALSE;
				for (OfficeTO officeTO2 : officeTOList) {
					if (officeTO2.getOfficeId().equals(officeTO.getOfficeId())) {
						isExist = Boolean.TRUE;
						break;
					}
				}
				if (!isExist) {
					officeTOList.add(officeTO);
				}
			}
		}
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderReportingHub() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getBranchesUnderCorporateOffice()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderCorporateOffice() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getBranchesUnderCorporateOffice();
		if(StringUtil.isEmptyColletion(officeTOList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_REGIONAL_BRANCH_LIST);
		}
		LOGGER.trace("CreateLeadServiceImpl :: getBranchesUnderCorporateOffice() :: End --------> ::::::");
		return officeTOList;
	}

	@Override
	public List<OfficeTO> getOfficesMappedToUser(Integer userId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreateLeadServiceImpl :: getOfficesMappedToUser() :: Start --------> ::::::");
		List<OfficeTO> officeTOList = leadsCommonService.getOfficesMappedToUser(userId);
		LOGGER.trace("CreateLeadServiceImpl :: getOfficesMappedToUser() :: End --------> ::::::");
		return officeTOList;
	}


}
