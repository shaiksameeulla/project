/**
 * 
 */
package com.ff.admin.leads.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.leads.CompetitorDO;
import com.ff.domain.leads.LeadCompetitorDO;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.leads.PlanFeedbackDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.leads.BranchTO;
import com.ff.leads.CompetitorListTO;
import com.ff.leads.CompetitorTO;
import com.ff.leads.LeadStatusTO;
import com.ff.leads.LeadTO;
import com.ff.leads.PlanTO;
import com.ff.leads.ProductTO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

/**
 * @author abarudwa
 *
 */
public class LeadConverter {
	
	public static CompetitorTO competitorConverter(CompetitorDO competitorDO)throws CGSystemException, CGBusinessException {
		CompetitorTO competitorTO = new CompetitorTO();
		competitorTO.setCompetitorId(competitorDO.getCompetitorId());
		competitorTO.setCompetitorName(competitorDO.getCompetitorName());
		return competitorTO;
		
	}
	
	public static LeadDO leadConverter(LeadTO leadTO)throws CGBusinessException,CGSystemException
	{
		LeadDO leadDO = new LeadDO();
		
		if(!StringUtil.isStringEmpty(leadTO.getCustomerName())){
			leadDO.setCustomerName(leadTO.getCustomerName());
		}
		if(!StringUtil.isEmptyInteger(leadTO.getLeadId())){
			leadDO.setLeadId(leadTO.getLeadId());
		} 
		if(!StringUtil.isStringEmpty(leadTO.getLeadNumber())){
			leadDO.setLeadNumber(leadTO.getLeadNumber());
		}
		if(!StringUtil.isStringEmpty(leadTO.getContactPerson())){
			leadDO.setContactPerson(leadTO.getContactPerson());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPhoneNoSTD())){
			leadDO.setPhoneNoSTD(leadTO.getPhoneNoSTD());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPhoneNo())){
			leadDO.setPhoneNo(leadTO.getPhoneNo());
		}
		if(!StringUtil.isStringEmpty(leadTO.getMobileNo())){
			leadDO.setMobileNo(leadTO.getMobileNo());
		}
		if(!StringUtil.isStringEmpty(leadTO.getDoorNoBuilding())){
			leadDO.setDoorNoBuilding(leadTO.getDoorNoBuilding());
		}
		if(!StringUtil.isStringEmpty(leadTO.getStreet())){
			leadDO.setStreet(leadTO.getStreet());
		}
		if(!StringUtil.isStringEmpty(leadTO.getLocation())){
			leadDO.setLocation(leadTO.getLocation());
		}
		if(!StringUtil.isStringEmpty(leadTO.getCity())){
			leadDO.setCity(leadTO.getCity());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPincode())){
			leadDO.setPincode(leadTO.getPincode());
		}
		if(!StringUtil.isStringEmpty(leadTO.getDesignation())){
			leadDO.setDesignation(leadTO.getDesignation());
		}
		if(!StringUtil.isStringEmpty(leadTO.getEmailAddress())){
			leadDO.setEmailAddress(leadTO.getEmailAddress());
		}
		
		if(!StringUtil.isNull(leadTO.getIndustryCategory().getCategoryId()) 
				&& (!StringUtil.isStringEmpty(leadTO.getIndustryCategory().getCategoryId()))){
			leadDO.setIndustryCategoryCode(leadTO.getIndustryCategory().getCategoryId());	
		}else{
			leadDO.setIndustryCategoryCode(leadTO.getIndustryCategoryCode());
		}
		
		if(!StringUtil.isStringEmpty(leadTO.getLeadSource().getSourceId())){
			leadDO.setLeadSourceCode(leadTO.getLeadSource().getSourceId());
		}else{
			leadDO.setLeadSourceCode(leadTO.getLeadSourceCode());
		}
		
		if(!StringUtil.isStringEmpty(leadTO.getSecondaryContact())){
			leadDO.setSecondaryContact(leadTO.getSecondaryContact());
		}
		
		if(!StringUtil.isEmptyInteger(leadTO.getAssignedTo().getEmpUserId())){

			EmployeeUserDO assigneddo = new EmployeeUserDO();
			assigneddo.setEmpUserId(leadTO.getAssignedTo().getEmpUserId());
			leadDO.setAssignedToEmployeeDO(assigneddo);
		}
		if(!StringUtil.isNull(leadTO.getStatus().getStatusDescription())){
			leadDO.setLeadStatusCode(leadTO.getStatus().getStatusDescription());
		}
		if(!StringUtil.isEmptyInteger(leadTO.getCreatedBy().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(leadTO.getCreatedBy().getEmployeeId());
			leadDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isStringEmpty(leadTO.getBusinessType())){
			leadDO.setBusinessType(leadTO.getBusinessType());
		}
		
		if(!StringUtil.isNull(leadTO.getDate())){
			leadDO.setCreatedDate(DateUtil
					.stringToDDMMYYYYFormat(leadTO.getDate()));
		}
		Set<LeadCompetitorDO>  leadCompetitorDOSet = new HashSet<>();
		for (CompetitorListTO competitorListTO : leadTO.getCompetitorList()){
			competitorListTO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(leadTO.getDateOfUpdate()));//sets updateDate in competitrListTO
			competitorListTO.setUpdatedByEmployeeTO(leadTO.getUpdatedBy());//sets updateBy in competitrListTO
			LeadCompetitorDO leadCompetitorDO = CompetitorConverter(competitorListTO);
			leadCompetitorDO.setLeadDO(leadDO);
			leadCompetitorDOSet.add(leadCompetitorDO);
		}
		leadDO.setCompetitorListDOs(leadCompetitorDOSet);
		
		if(!StringUtil.isNull(leadTO.getDateOfUpdate())){
			leadDO.setUpdatedDate(DateUtil
					.stringToDDMMYYYYFormat(leadTO.getDateOfUpdate()));
		}
		
		return leadDO;
		
	}

	
	public static PlanFeedbackDO converterDomainObjectFromTO(PlanTO planTO)throws CGSystemException, CGBusinessException{
		PlanFeedbackDO planFeedbackDO = new PlanFeedbackDO();
		return planFeedbackDO;
	}
	
	public static LeadTO getLeadDetailsConverter(LeadDO leadDO)throws CGSystemException, CGBusinessException{
		LeadTO leadTO = null;
		if(!StringUtil.isNull(leadDO)){
			leadTO = new LeadTO();
			leadTO.setLeadId(leadDO.getLeadId());
			leadTO.setCustomerName(leadDO.getCustomerName());
			leadTO.setLeadNumber(leadDO.getLeadNumber());
			leadTO.setContactPerson(leadDO.getContactPerson());
			Set<LeadCompetitorDO> leadCompetitorDOList = new HashSet<LeadCompetitorDO>();
			leadCompetitorDOList = leadDO.getCompetitorListDOs();
			ArrayList<CompetitorListTO> competitorListTOList = new ArrayList<CompetitorListTO>();
			for(LeadCompetitorDO leadCompetitorDO :leadCompetitorDOList){
				CompetitorListTO competitorListTO = new CompetitorListTO();
				
				if(!StringUtil.isStringEmpty(leadCompetitorDO.getLeadProductCode())){
					ProductTO productTO = new ProductTO();
					productTO.setStdTypeCode(leadCompetitorDO.getLeadProductCode());
					competitorListTO.setProduct(productTO);
				}
				
				if(!StringUtil.isNull(leadCompetitorDO.getCompetitorDO())){
					CompetitorTO competitorTO = new CompetitorTO();
					competitorTO.setCompetitorId(leadCompetitorDO.getCompetitorDO().getCompetitorId());
					competitorTO.setCompetitorName(leadCompetitorDO.getCompetitorDO().getCompetitorName());
					competitorListTO.setCompetitor(competitorTO);
				}
				competitorListTO.setPotential(leadCompetitorDO.getPotential());
				competitorListTO.setExpectedVolume(leadCompetitorDO.getExpectedVolume());
				competitorListTOList.add(competitorListTO);
			}
			leadTO.setCompetitorList(competitorListTOList);
			leadTO.setPhoneNoSTD(leadDO.getPhoneNoSTD());
			leadTO.setPhoneNo(leadDO.getPhoneNo());
			leadTO.setMobileNo(leadDO.getMobileNo());
			leadTO.setDoorNoBuilding(leadDO.getDoorNoBuilding());
			leadTO.setStreet(leadDO.getStreet());
			leadTO.setLocation(leadDO.getLocation());
			leadTO.setCity(leadDO.getCity());
			leadTO.setPincode(leadDO.getPincode());
			leadTO.setDesignation(leadDO.getDesignation());
			leadTO.setEmailAddress(leadDO.getEmailAddress());
			leadTO.setIndustryCategoryCode(leadDO.getIndustryCategoryCode());
			leadTO.setLeadSourceCode(leadDO.getLeadSourceCode());
			leadTO.setSecondaryContact(leadDO.getSecondaryContact());
						
			if(!StringUtil.isNull(leadDO.getAssignedToEmployeeDO())){
				EmployeeUserTO employeeUserTO = new EmployeeUserTO();
				EmployeeUserDO employeeUserDO = leadDO.getAssignedToEmployeeDO();
				employeeUserTO.setEmpUserId(employeeUserDO.getEmpUserId());
				employeeUserTO.setUserId(employeeUserDO.getUserId());
				if (!StringUtil.isNull(employeeUserDO.getEmpDO())) {
					EmployeeTO employeeTO = new EmployeeTO();
					employeeTO = (EmployeeTO) CGObjectConverter
							.createToFromDomain(employeeUserDO.getEmpDO(), employeeTO);
					employeeUserTO.setEmpTO(employeeTO);
				}
				leadTO.setAssignedTo(employeeUserTO);

				
				Integer officeId = employeeUserDO.getEmpDO().getOfficeId();
				
				 BranchTO officeTO = new BranchTO();
				 officeTO.setBranchId(officeId);
				 leadTO.setBranchTO(officeTO);
				 
				 String designation = employeeUserDO.getEmpDO().getDesignation();
				 leadTO.setDesignation(designation);


			}
			
			if(!StringUtil.isNull(leadDO.getCreatedByEmployeeDO())){
				EmployeeTO employeeTO = new EmployeeTO();
				employeeTO=	(EmployeeTO)CGObjectConverter
						.createToFromDomain(leadDO.getCreatedByEmployeeDO(),
								employeeTO);
				leadTO.setCreatedBy(employeeTO);
			}
				
			if(!StringUtil.isNull(leadDO.getLeadStatusCode())){
				LeadStatusTO leadStatusTO = null;
				leadStatusTO = new LeadStatusTO();
				leadStatusTO.setStatusDescription(leadDO.getLeadStatusCode());
				leadTO.setStatus(leadStatusTO);
			}
			
			if(!StringUtil.isNull(leadDO.getCreatedDate())){
			String createDate=DateUtil.getDDMMYYYYDateString(leadDO.getCreatedDate());
			leadTO.setDate(createDate);
			}
			
		}
		
		return leadTO;
	}
	
	public static PlanFeedbackDO savePlanConverter(PlanTO planTO)
			throws CGSystemException, CGBusinessException {
		PlanFeedbackDO planFeedbackDO = new PlanFeedbackDO();

		if (!StringUtil.isNull(planTO)) {
			planFeedbackDO.setPlanFeedbackId(planTO.getPlanId());
			// private LeadDO leadDO;
			if(!StringUtil.isEmptyInteger(planTO.getPlanId())){
				LeadDO leadDO = new LeadDO();
				leadDO.setLeadId(planTO.getPlanId());
				planFeedbackDO.setLeadDO(leadDO);
			}
			List<StockStandardTypeTO> stockStandardTypeTO = planTO
					.getFeedBackStandardTypeTO();
			for (StockStandardTypeTO standardTypeTO : stockStandardTypeTO) {
				planFeedbackDO.setFeedbackCode(standardTypeTO.getStdTypeCode());
			}
			Date visitDate = DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(planTO.getDate());
			planFeedbackDO.setVisitDate(visitDate);
			planFeedbackDO.setPurposeOfVisit(planTO.getPurposeOfVisit());
			planFeedbackDO.setContactPerson(planTO.getContactPerson());
			planFeedbackDO.setTime(planTO.getDateTime());
		}
		return planFeedbackDO;
	}
	
	public static PlanTO getPlanFeedBackDetailsConverter(PlanFeedbackDO planFeedbackDO)throws CGSystemException, CGBusinessException {
		PlanTO planTO = new PlanTO();
		planTO.setPlanId(planFeedbackDO.getPlanFeedbackId());
		String visitDate=DateUtil.getDDMMYYYYDateString(planFeedbackDO.getVisitDate());
		planTO.setDate(visitDate);
		planTO.setPurposeOfVisit(planFeedbackDO.getPurposeOfVisit());
		planTO.setContactPerson(planFeedbackDO.getContactPerson());
		planTO.setDateTime(planFeedbackDO.getTime());
		
		return planTO;
	}
	
	public static List<EmployeeUserTO> convertTosfromDomainObject(
			List<EmployeeUserDO> employeeUserDOs) throws CGSystemException,
			CGBusinessException {

		List<EmployeeUserTO> employeeUserTOs = null;
		EmployeeUserTO employeeUserTO= null;

		if(!StringUtil.isNull(employeeUserDOs)){
			employeeUserTOs = new ArrayList<>();
			for(EmployeeUserDO employeeUserDO : employeeUserDOs){
				employeeUserTO= new EmployeeUserTO();
				employeeUserTO.setEmpUserId(employeeUserDO.getEmpUserId());
				employeeUserTO.setUserId(employeeUserDO.getUserId());
				if (!StringUtil.isNull(employeeUserDO.getEmpDO())) {
					EmployeeTO employeeTO = new EmployeeTO();
					employeeTO = (EmployeeTO) CGObjectConverter
							.createToFromDomain(employeeUserDO.getEmpDO(), employeeTO);
					employeeUserTO.setEmpTO(employeeTO);
				}
				employeeUserTOs.add(employeeUserTO);
			}
		}
		return employeeUserTOs;
	}
	
	public static LeadCompetitorDO CompetitorConverter(CompetitorListTO competitorListTO)throws CGBusinessException,CGSystemException{
		LeadCompetitorDO leadCompetitorDO = new LeadCompetitorDO();
		if(!StringUtil.isNull(competitorListTO.getCompetitor().getCompetitorId())){
			CompetitorDO competitorDO = new CompetitorDO();
			competitorDO.setCompetitorId(competitorListTO.getCompetitor().getCompetitorId());
			leadCompetitorDO.setCompetitorDO(competitorDO);	
		}
		if(!StringUtil.isNull(competitorListTO.getProduct().getStdTypeCode())){
			leadCompetitorDO.setLeadProductCode(competitorListTO.getProduct().getStdTypeCode());
		}
		if(!StringUtil.isNull(competitorListTO.getPotential())){
			leadCompetitorDO.setPotential(competitorListTO.getPotential());
		}
		if(!StringUtil.isNull(competitorListTO.getExpectedVolume())){
			leadCompetitorDO.setExpectedVolume(competitorListTO.getExpectedVolume());
		}	
		
		if(!StringUtil.isEmptyInteger(competitorListTO.getCreatedByEmployeeTO().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(competitorListTO.getCreatedByEmployeeTO().getEmployeeId());
			leadCompetitorDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isEmptyInteger(competitorListTO.getCreatedByEmployeeTO().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(competitorListTO.getCreatedByEmployeeTO().getEmployeeId());
			leadCompetitorDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isNull(competitorListTO.getDate())){
			leadCompetitorDO.setCreatedDate(DateUtil
					.stringToDDMMYYYYFormat(competitorListTO.getDate()));
		}
				
		return leadCompetitorDO;
		
	}
	
	public static PlanTO convertFeedbackTOfromDO(PlanTO planTO ,PlanFeedbackDO planFeedbackDO){
		
		String visitDate=DateUtil.getDDMMYYYYDateToString(planFeedbackDO.getVisitDate());
		planTO.setDate(visitDate);
		planTO.setPurposeOfVisit(planFeedbackDO.getPurposeOfVisit());
		planTO.setContactPerson(planFeedbackDO.getContactPerson());
		planTO.setDateTime(planFeedbackDO.getTime());
		if(!StringUtil.isStringEmpty(planFeedbackDO.getFeedbackCode())){
			planTO.setFeedbackCode(planFeedbackDO.getFeedbackCode());
		}
		if(!StringUtil.isStringEmpty(planFeedbackDO.getRemarks())){
			planTO.setRemarks(planFeedbackDO.getRemarks());	
		}
		if(!StringUtil.isEmptyInteger(planFeedbackDO.getPlanFeedbackId())){
			planTO.setPlanFeedbackId(planFeedbackDO.getPlanFeedbackId());
		}
		
		
		return planTO;
		
	}
	
	public static LeadDO leadValidationConverter(LeadTO leadTO)throws CGBusinessException,CGSystemException
	{
		LeadDO leadDO = new LeadDO();
		
		if(!StringUtil.isStringEmpty(leadTO.getCustomerName())){
			leadDO.setCustomerName(leadTO.getCustomerName());
		}
		if(!StringUtil.isEmptyInteger(leadTO.getLeadId())){
			leadDO.setLeadId(leadTO.getLeadId());
		} 
		if(!StringUtil.isStringEmpty(leadTO.getLeadNumber())){
			leadDO.setLeadNumber(leadTO.getLeadNumber());
		}
		if(!StringUtil.isStringEmpty(leadTO.getContactPerson())){
			leadDO.setContactPerson(leadTO.getContactPerson());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPhoneNoSTD())){
			leadDO.setPhoneNoSTD(leadTO.getPhoneNoSTD());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPhoneNo())){
			leadDO.setPhoneNo(leadTO.getPhoneNo());
		}
		if(!StringUtil.isStringEmpty(leadTO.getMobileNo())){
			leadDO.setMobileNo(leadTO.getMobileNo());
		}
		if(!StringUtil.isStringEmpty(leadTO.getDoorNoBuilding())){
			leadDO.setDoorNoBuilding(leadTO.getDoorNoBuilding());
		}
		if(!StringUtil.isStringEmpty(leadTO.getStreet())){
			leadDO.setStreet(leadTO.getStreet());
		}
		if(!StringUtil.isStringEmpty(leadTO.getLocation())){
			leadDO.setLocation(leadTO.getLocation());
		}
		if(!StringUtil.isStringEmpty(leadTO.getCity())){
			leadDO.setCity(leadTO.getCity());
		}
		if(!StringUtil.isStringEmpty(leadTO.getPincode())){
			leadDO.setPincode(leadTO.getPincode());
		}
		if(!StringUtil.isStringEmpty(leadTO.getContPersonDesig())){
			leadDO.setDesignation(leadTO.getContPersonDesig());
		}
		if(!StringUtil.isStringEmpty(leadTO.getEmailAddress())){
			leadDO.setEmailAddress(leadTO.getEmailAddress());
		}
		
		if(!StringUtil.isNull(leadTO.getIndustryCategory().getCategoryId()) 
				&& (!StringUtil.isStringEmpty(leadTO.getIndustryCategory().getCategoryId()))){
			leadDO.setIndustryCategoryCode(leadTO.getIndustryCategory().getCategoryId());	
		}else{
			leadDO.setIndustryCategoryCode(leadTO.getIndustryCategoryCode());
		}
		
		if(!StringUtil.isStringEmpty(leadTO.getLeadSource().getSourceId())){
			leadDO.setLeadSourceCode(leadTO.getLeadSource().getSourceId());
		}else{
			leadDO.setLeadSourceCode(leadTO.getLeadSourceCode());
		}
		
		
		
		if(!StringUtil.isStringEmpty(leadTO.getSecondaryContact())){
			leadDO.setSecondaryContact(leadTO.getSecondaryContact());
		}
				
		if(!StringUtil.isEmptyInteger(leadTO.getAssignedTo().getEmpUserId())){

			EmployeeUserDO assigneddo = new EmployeeUserDO();
			assigneddo.setEmpUserId(leadTO.getAssignedTo().getEmpUserId());
			leadDO.setAssignedToEmployeeDO(assigneddo);
		}
		if(!StringUtil.isNull(leadTO.getStatus().getStatusDescription())){
			leadDO.setLeadStatusCode(leadTO.getStatus().getStatusDescription());
		}
		
		
		if(!StringUtil.isEmptyInteger(leadTO.getCreatedBy().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(leadTO.getCreatedBy().getEmployeeId());
			leadDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isNull(leadTO.getDate())){
			leadDO.setCreatedDate(DateUtil
					.stringToDDMMYYYYFormat(leadTO.getDate()));
		}
		
		Set<LeadCompetitorDO>  leadCompetitorDOSet = new HashSet<>();
		for (CompetitorListTO competitorListTO : leadTO.getCompetitorList()){
			competitorListTO.setUpdatedDate(DateUtil.stringToDDMMYYYYFormat(leadTO.getDateOfUpdate()));//sets updateDate in competitrListTO
			competitorListTO.setUpdatedByEmployeeTO(leadTO.getUpdatedBy());//sets updateBy in competitrListTO
			LeadCompetitorDO leadCompetitorDO = validationCompetitorConverter(competitorListTO);
			leadCompetitorDO.setLeadDO(leadDO);
			leadCompetitorDOSet.add(leadCompetitorDO);
		}
		leadDO.setCompetitorListDOs(leadCompetitorDOSet);
		
		if(!StringUtil.isEmptyInteger(leadTO.getUpdatedBy().getEmployeeId())){
			EmployeeDO updatedBy = new EmployeeDO();
			updatedBy.setEmployeeId(leadTO.getUpdatedBy().getEmployeeId());
			leadDO.setUpdatedByEmployeeDO(updatedBy);
		}
		
		if(!StringUtil.isNull(leadTO.getDateOfUpdate())){
			leadDO.setUpdatedDate(DateUtil
					.stringToDDMMYYYYFormat(leadTO.getDateOfUpdate()));
		}
		
		if(!StringUtil.isStringEmpty(leadTO.getBusinessType())){
			leadDO.setBusinessType(leadTO.getBusinessType());
		}
		
		return leadDO;
		
	}
	
	public static LeadCompetitorDO validationCompetitorConverter(CompetitorListTO competitorListTO)throws CGBusinessException,CGSystemException{
		LeadCompetitorDO leadCompetitorDO = new LeadCompetitorDO();
		if(!StringUtil.isNull(competitorListTO.getCompetitor().getCompetitorId())){
			CompetitorDO competitorDO = new CompetitorDO();
			competitorDO.setCompetitorId(competitorListTO.getCompetitor().getCompetitorId());
			leadCompetitorDO.setCompetitorDO(competitorDO);	
		}
		if(!StringUtil.isNull(competitorListTO.getProduct().getStdTypeCode())){
			leadCompetitorDO.setLeadProductCode(competitorListTO.getProduct().getStdTypeCode());
		}
		if(!StringUtil.isNull(competitorListTO.getPotential())){
			leadCompetitorDO.setPotential(competitorListTO.getPotential());
		}
		if(!StringUtil.isNull(competitorListTO.getExpectedVolume())){
			leadCompetitorDO.setExpectedVolume(competitorListTO.getExpectedVolume());
		}	
		
		if(!StringUtil.isEmptyInteger(competitorListTO.getCreatedByEmployeeTO().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(competitorListTO.getCreatedByEmployeeTO().getEmployeeId());
			leadCompetitorDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isEmptyInteger(competitorListTO.getCreatedByEmployeeTO().getEmployeeId())){
			EmployeeDO createdBy = new EmployeeDO();
			createdBy.setEmployeeId(competitorListTO.getCreatedByEmployeeTO().getEmployeeId());
			leadCompetitorDO.setCreatedByEmployeeDO(createdBy);
		}
		
		if(!StringUtil.isNull(competitorListTO.getDate())){
			leadCompetitorDO.setCreatedDate(DateUtil
					.stringToDDMMYYYYFormat(competitorListTO.getDate()));
		}
		
		if(!StringUtil.isEmptyInteger(competitorListTO.getUpdatedByEmployeeTO().getEmployeeId())){
			EmployeeDO updatedBy = new EmployeeDO();
			updatedBy.setEmployeeId(competitorListTO.getUpdatedByEmployeeTO().getEmployeeId());
			leadCompetitorDO.setUpdatedByEmployeeDO(updatedBy);
		}
		
		if(!StringUtil.isNull(competitorListTO.getUpdatedDate())){
			leadCompetitorDO.setUpdatedDate((competitorListTO.getUpdatedDate()));
		}
		
		if(!StringUtil.isNull(competitorListTO.getLeadId())){
			LeadDO leadDO = new LeadDO();
			leadDO.setLeadId(competitorListTO.getLeadId());
			leadCompetitorDO.setLeadDO(leadDO);
		}
		if(!StringUtil.isNull(competitorListTO.getLeadCompetitorId())){
			leadCompetitorDO.setLeadCompetitorId(competitorListTO.getLeadCompetitorId());
		}
		return leadCompetitorDO;
		
	}
	
	public static LeadTO validationDomainConverter(LeadDO leadDO)throws CGSystemException, CGBusinessException{
		LeadTO leadTO = null;
		if(!StringUtil.isNull(leadDO)){
			leadTO = new LeadTO();
			leadTO.setLeadId(leadDO.getLeadId());
			leadTO.setCustomerName(leadDO.getCustomerName());
			leadTO.setLeadNumber(leadDO.getLeadNumber());
			leadTO.setContactPerson(leadDO.getContactPerson());
			Set<LeadCompetitorDO> leadCompetitorDOList = new HashSet<LeadCompetitorDO>();
			leadCompetitorDOList = leadDO.getCompetitorListDOs();
			ArrayList<CompetitorListTO> competitorListTOList = new ArrayList<CompetitorListTO>();
			for(LeadCompetitorDO leadCompetitorDO :leadCompetitorDOList){
				CompetitorListTO competitorListTO = new CompetitorListTO();
				
				if(!StringUtil.isStringEmpty(leadCompetitorDO.getLeadProductCode())){
					ProductTO productTO = new ProductTO();
					productTO.setStdTypeCode(leadCompetitorDO.getLeadProductCode());
					competitorListTO.setProduct(productTO);
				}
				
				if(!StringUtil.isNull(leadCompetitorDO.getCompetitorDO())){
					CompetitorTO competitorTO = new CompetitorTO();
					competitorTO.setCompetitorId(leadCompetitorDO.getCompetitorDO().getCompetitorId());
					competitorTO.setCompetitorName(leadCompetitorDO.getCompetitorDO().getCompetitorName());
					competitorListTO.setCompetitor(competitorTO);
				}
				competitorListTO.setPotential(leadCompetitorDO.getPotential());
				competitorListTO.setExpectedVolume(leadCompetitorDO.getExpectedVolume());
				competitorListTO.setLeadCompetitorId(leadCompetitorDO.getLeadCompetitorId());
				//leadTO.setLeadCompetitorId(leadCompetitorDO.getLeadCompetitorId());
				competitorListTOList.add(competitorListTO);
			}
			leadTO.setCompetitorList(competitorListTOList);
			leadTO.setPhoneNoSTD(leadDO.getPhoneNoSTD());
			leadTO.setPhoneNo(leadDO.getPhoneNo());
			leadTO.setMobileNo(leadDO.getMobileNo());
			leadTO.setDoorNoBuilding(leadDO.getDoorNoBuilding());
			leadTO.setStreet(leadDO.getStreet());
			leadTO.setLocation(leadDO.getLocation());
			leadTO.setCity(leadDO.getCity());
			leadTO.setPincode(leadDO.getPincode());
			leadTO.setDesignation(leadDO.getDesignation());
			leadTO.setEmailAddress(leadDO.getEmailAddress());
			leadTO.setIndustryCategoryCode(leadDO.getIndustryCategoryCode());
			leadTO.setLeadSourceCode(leadDO.getLeadSourceCode());
			leadTO.setSecondaryContact(leadDO.getSecondaryContact());
			leadTO.setBusinessType(leadDO.getBusinessType());
			
			leadTO.setContPersonDesig(leadDO.getDesignation());
			if(!StringUtil.isNull(leadDO.getAssignedToEmployeeDO())){
				EmployeeUserTO employeeUserTO = new EmployeeUserTO();
				EmployeeUserDO employeeUserDO = leadDO.getAssignedToEmployeeDO();
				employeeUserTO.setEmpUserId(employeeUserDO.getEmpUserId());
				employeeUserTO.setUserId(employeeUserDO.getUserId());
				if (!StringUtil.isNull(employeeUserDO.getEmpDO())) {
					EmployeeTO employeeTO = new EmployeeTO();
					employeeTO = (EmployeeTO) CGObjectConverter
							.createToFromDomain(employeeUserDO.getEmpDO(), employeeTO);
					employeeUserTO.setEmpTO(employeeTO);
				}
				leadTO.setAssignedTo(employeeUserTO);

				
				Integer officeId = employeeUserDO.getEmpDO().getOfficeId();
				
				 BranchTO officeTO = new BranchTO();
				 officeTO.setBranchId(officeId);
				 leadTO.setBranchTO(officeTO);
				 
				 String designation = employeeUserDO.getEmpDO().getDesignation();
				 leadTO.setDesignation(designation);


			}
			
			if(!StringUtil.isNull(leadDO.getCreatedByEmployeeDO())){
				EmployeeTO employeeTO = new EmployeeTO();
				employeeTO=	(EmployeeTO)CGObjectConverter
						.createToFromDomain(leadDO.getCreatedByEmployeeDO(),
								employeeTO);
				leadTO.setCreatedBy(employeeTO);
			}
				
			if(!StringUtil.isNull(leadDO.getLeadStatusCode())){
				LeadStatusTO leadStatusTO = null;
				leadStatusTO = new LeadStatusTO();
				leadStatusTO.setStatusDescription(leadDO.getLeadStatusCode());
				leadTO.setStatus(leadStatusTO);
			}
			
			if(!StringUtil.isNull(leadDO.getCreatedDate())){
			leadTO.setDate(DateUtil.getDDMMYYYYDateString(Calendar.getInstance().getTime()));
			}
			
		}
		
		return leadTO;
	}
	

	
	
}
