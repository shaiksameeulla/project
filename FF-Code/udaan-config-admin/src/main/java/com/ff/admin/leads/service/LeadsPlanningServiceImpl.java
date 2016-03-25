package com.ff.admin.leads.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.leads.PlanFeedbackDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.leads.ViewUpdateFeedbackTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.admin.leads.constants.LeadCommonConstants;
import com.ff.admin.leads.dao.LeadCommonDAO;

public class LeadsPlanningServiceImpl implements LeadsPlanningService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LeadsPlanningServiceImpl.class);
	
	private LeadCommonDAO leadCommonDAO;
	private LeadsCommonService leadsCommonService;
	
	

	public void setLeadCommonDAO(LeadCommonDAO leadCommonDAO) {
		this.leadCommonDAO = leadCommonDAO;
	}
	
	public void setLeadsCommonService(LeadsCommonService leadsCommonService) {
		this.leadsCommonService = leadsCommonService;
	}

	@Override
	public String savePlan(ViewUpdateFeedbackTO viewUpdateFeedbackTO)
			throws CGSystemException,CGBusinessException {
		LOGGER.trace("LeadsPlanningServiceImpl :: savePlan() :: Start --------> ::::::");
		boolean isSaved = Boolean.FALSE;
		String isLeadSaved = "N";
		try{
		List<PlanFeedbackDO> planFeedbackDOs =leadplaningConverter(viewUpdateFeedbackTO);
		if(!StringUtil.isEmptyColletion(planFeedbackDOs)){
			isSaved = leadCommonDAO.savePlan(planFeedbackDOs);
			/*if(!StringUtil.isStringEmpty(viewUpdateFeedbackTO.getLeadStatus()) && viewUpdateFeedbackTO.getLeadStatus().equalsIgnoreCase(LeadCommonConstants.LEAD_APPROVED)){
				leadCommonDAO.updateLeadStatus(viewUpdateFeedbackTO.getLeadId(), LeadCommonConstants.LEAD_ACTIVE);
			}*/
			
		}
		if (isSaved){
			isLeadSaved = LeadCommonConstants.SUCCESS;
		}
	}catch(Exception e){
		LOGGER.error("Exception Occured in::LeadsPlanningServiceImpl::savePlan() :: " + e);
		throw new CGBusinessException(LeadCommonConstants.ERROR_IN_SAVING_LEADS_PLANNING);
	}
		LOGGER.trace("LeadsPlanningServiceImpl :: savePlan() :: End --------> ::::::");	
		return isLeadSaved;
	}

	@Override
	public LeadTO getLeadDetails(final String leadNumber) throws CGSystemException,
			CGBusinessException {
		LeadTO leadTO = leadsCommonService.getLeadDetails(leadNumber);
		if(StringUtil.isNull(leadTO)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_LEADS_DETAILS);
		}
		return leadTO;
	}

	private static List<PlanFeedbackDO> leadplaningConverter(ViewUpdateFeedbackTO viewUpdateFeedbackTO)
			throws CGSystemException,CGBusinessException {
		LOGGER.trace("LeadsPlanningServiceImpl :: leadplaningConverter() :: Start --------> ::::::");
		List<PlanFeedbackDO> planFeedbackDOList = new ArrayList<PlanFeedbackDO>();
		if(!StringUtil.isNull(viewUpdateFeedbackTO)){
			int lenght=viewUpdateFeedbackTO.getDateStr().length;	
			for(int i=0;i<lenght;i++){
				if(StringUtils.isBlank(viewUpdateFeedbackTO.getPurposeOfVisitors()[i])){
					continue;
				}
				PlanFeedbackDO planFeedbackDO= new PlanFeedbackDO();
				if(!StringUtil.isEmptyInteger(viewUpdateFeedbackTO.getLeadId())){
					LeadDO leadDO = new LeadDO();
					leadDO.setLeadId(viewUpdateFeedbackTO.getLeadId());
					//to set leads status as active 
					leadDO.setLeadStatusCode(LeadCommonConstants.LEAD_ACTIVE);
					planFeedbackDO.setLeadDO(leadDO);
				}
				Date VistDate=DateUtil.stringToDDMMYYYYFormat(viewUpdateFeedbackTO.getDateStr()[i]);
				planFeedbackDO.setVisitDate(VistDate);
				
				planFeedbackDO.setPurposeOfVisit(viewUpdateFeedbackTO.getPurposeOfVisitors()[i]);
				planFeedbackDO.setContactPerson(viewUpdateFeedbackTO.getContactPersons()[i]);
				planFeedbackDO.setPurposeOfVisit(viewUpdateFeedbackTO.getPurposeOfVisitors()[i]);
				planFeedbackDO.setTime(viewUpdateFeedbackTO.getTimeStr()[i]);
				
				if(!StringUtil.isStringEmpty(viewUpdateFeedbackTO.getLeadFeedCodeSave()) && viewUpdateFeedbackTO.getLeadFeedCodeSave().equalsIgnoreCase(LeadCommonConstants.LEAD_FEEDCODE_SAVE)){
					if(!StringUtil.isStringEmpty(viewUpdateFeedbackTO.getFeedBackCode()[i])){
						planFeedbackDO.setFeedbackCode(viewUpdateFeedbackTO.getFeedBackCode()[i]);
					}
					if(!StringUtil.isStringEmpty(viewUpdateFeedbackTO.getRemarks()[i])){
						planFeedbackDO.setRemarks(viewUpdateFeedbackTO.getRemarks()[i]);
					}
					if(!StringUtil.isEmptyInteger(viewUpdateFeedbackTO.getPlanFeedbackIds()[i])){
						planFeedbackDO.setPlanFeedbackId(viewUpdateFeedbackTO.getPlanFeedbackIds()[i]);
					}
				}
				if(!StringUtil.isEmptyInteger(viewUpdateFeedbackTO.getCreatedBy().getEmployeeId())){
					EmployeeDO createdBy = new EmployeeDO();
					createdBy.setEmployeeId(viewUpdateFeedbackTO.getCreatedBy().getEmployeeId());
					planFeedbackDO.setCreatedByEmployeeDO(createdBy);
				}
				if(!StringUtil.isEmptyInteger(viewUpdateFeedbackTO.getUpdatedBy().getEmployeeId())){
					EmployeeDO updatedByEmployeeDO = new EmployeeDO();
					updatedByEmployeeDO.setEmployeeId(viewUpdateFeedbackTO.getUpdatedBy().getEmployeeId());
					planFeedbackDO.setUpdatedByEmployeeDO(updatedByEmployeeDO);
				}
				planFeedbackDO.setCreatedDate(Calendar.getInstance().getTime());
				planFeedbackDO.setUpdatedDate(Calendar.getInstance().getTime());
				
				planFeedbackDOList.add(planFeedbackDO);
			}
		}
		LOGGER.trace("LeadsPlanningServiceImpl :: leadplaningConverter() :: End --------> ::::::");
		return planFeedbackDOList;
	}

	@Override
	public List<StockStandardTypeTO> getFeedbackList()
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeTO> feedbackList = leadsCommonService.getFeedbackList();
		if(StringUtil.isEmptyList(feedbackList)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_FEEDBACK_LIST);
		}
		return feedbackList;
	}


	public List<PlanTO> getLeadsPlanDtlsByleadNumber(final String leadNumber) throws CGSystemException,
	CGBusinessException {
		List<PlanTO> planTOs = leadsCommonService.getLeadsPlanningDtlsOrdeByTimeDesc(leadNumber);
		if(StringUtil.isEmptyList(planTOs)){
			throw new CGBusinessException(LeadCommonConstants.ERROR_IN_GETTING_PLAN_DETAILS);
		}
		return planTOs;
}
	
	
	
	
}
