package com.ff.admin.complaints.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.StringUtils;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.constants.ComplaintsServiceRequestConstants;
import com.ff.admin.complaints.converter.ComplaintsConverter;
import com.ff.admin.complaints.dao.ComplaintsBacklineSummaryDAO;
import com.ff.admin.complaints.dao.ComplaintsCommonDAO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ComplaintsBacklineSummaryTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.complaints.ServiceRequestTO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.organization.EmployeeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

public class ComplaintsBacklineSummaryServiceImpl implements
		ComplaintsBacklineSummaryService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComplaintsBacklineSummaryServiceImpl.class);
	private ComplaintsCommonService complaintsCommonService;
	private ComplaintsCommonDAO complaintsCommonDAO;
	private ComplaintsBacklineSummaryDAO complaintsBacklineSummaryDAO;

	public void setComplaintsCommonService(
			ComplaintsCommonService complaintsCommonService) {
		this.complaintsCommonService = complaintsCommonService;
	}
	public void setComplaintsCommonDAO(ComplaintsCommonDAO complaintsCommonDAO) {
		this.complaintsCommonDAO = complaintsCommonDAO;
	}

	public void setComplaintsBacklineSummaryDAO(
			ComplaintsBacklineSummaryDAO complaintsBacklineSummaryDAO) {
		this.complaintsBacklineSummaryDAO = complaintsBacklineSummaryDAO;
	}
	
	@Override
	public List<StockStandardTypeTO> getComplaintsStatus(String typeName)
			throws CGSystemException, CGBusinessException {
		return complaintsCommonService.getStandardTypesByTypeName(typeName);
	}
	
	@Override
	public List<ServiceRequestTO> getServiceRequestDetails(ComplaintsBacklineSummaryTO summaryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("ComplaintsBacklineSummaryServiceImpl::getServiceRequestDetails::START------------>:::::::");
		List<ServiceRequestTO> serviceRequestTOs=null;
		try{
			ServiceRequestFilters requestFilters = new ServiceRequestFilters();
			requestFilters.setUserId(summaryTO.getUserTO().getUserId());
			requestFilters.setConsignmentNo(summaryTO.getCompDesc());
			requestFilters.setReqStatus(summaryTO.getCompStatus());
			serviceRequestTOs= complaintsCommonService.getServiceRequestDetails(requestFilters);
		}catch(Exception e){
			LOGGER.error("ERROR :: ComplaintsBacklineSummaryServiceImpl :: getServiceRequestDetails() ::", e);
		}
		LOGGER.debug("ComplaintsBacklineSummaryServiceImpl::getServiceRequestDetails::END------------>:::::::");
		return serviceRequestTOs;
	}
	
	@Override
	public List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("ComplaintsBacklineSummaryServiceImpl::getServiceRequestStatus::START------------>:::::::");
		List<ServiceRequestStatusTO> statusTOs = null;
		statusTOs = complaintsCommonService.getServiceRequestStatus();
		if(StringUtil.isEmptyList(statusTOs)){
			ExceptionUtil.prepareBusinessException(ComplaintsCommonConstants.ERROR_IN_GETTING_BACKLINE_SUMMARY_STATUS);
		}
		LOGGER.debug("ComplaintsBacklineSummaryServiceImpl::getServiceRequestStatus::END------------>:::::::");
		return statusTOs;
	}
	
	@Override
	public EmployeeUserTO getEmployeeUser(Integer userId)
			throws CGBusinessException, CGSystemException {
		EmployeeUserTO employeeUserTO = new EmployeeUserTO();
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getEmployeeUser::START------------>:::::::");
		EmployeeUserDO employeeUserDO = complaintsCommonDAO
				.getEmployeeUser(userId);

		if (!StringUtil.isNull(employeeUserDO)) {

			EmployeeTO empTO = new EmployeeTO();
			empTO.setEmployeeId(employeeUserDO.getEmpDO().getEmployeeId());
			empTO.setFirstName(employeeUserDO.getEmpDO().getFirstName());
			empTO.setLastName(employeeUserDO.getEmpDO().getLastName());
			employeeUserTO.setEmpTO(empTO);
		}
	LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getEmployeeUser::END------------>:::::::");
	return employeeUserTO;
	}
	
	@Override
	public List<ServiceRequestTO> getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestNo::START------------>:::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		List<ServiceRequestTO> serviceRequestTOs =null;
		serviceRequestDOs = complaintsBacklineSummaryDAO.getComplaintDetailsByServiceRequestNo(serviceRequestNo);
		if(!CGCollectionUtils.isEmpty(serviceRequestDOs)){
			 serviceRequestTOs = new ArrayList<ServiceRequestTO>(serviceRequestDOs.size());
			for(ServiceRequestDO serviceRequestDO : serviceRequestDOs){
				ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
				serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
				String queryTypeCode = serviceRequestDO.getServiceRequestQueryTypeDO().getQueryTypeCode();
				if (queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT)) {
					serviceRequestTO.setIsLinkEnabled("Y");
				}
				
				if(!StringUtil.isNull(serviceRequestDO.getCreatedBy())){
					StringBuilder frontlineExecName = null;
					EmployeeUserDO empuser = complaintsCommonDAO.getEmployeeUser(serviceRequestDO.getCreatedBy());
					if (!StringUtil.isNull(empuser) && !StringUtil.isNull(empuser.getEmpDO())){
						frontlineExecName = new StringBuilder();
						EmployeeDO emp = empuser.getEmpDO();
						if(!StringUtil.isStringEmpty(emp.getEmpCode())){
							frontlineExecName.append(emp.getEmpCode());
						}
						if(!StringUtil.isStringEmpty(emp.getFirstName())){
							frontlineExecName.append(" - ");
							frontlineExecName.append(emp.getFirstName());
						}
						if(!StringUtil.isStringEmpty(emp.getLastName())){
							frontlineExecName.append(" ");
							frontlineExecName.append(emp.getLastName());
						}
						serviceRequestTO.setFrontlineExecName(frontlineExecName.toString());
					}
				}
				serviceRequestTOs.add(serviceRequestTO);
			}
		}
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestNo::END------------>:::::::");
		return serviceRequestTOs;
	}
	
	@Override
	public List<ServiceRequestTO> getComplaintDetailsByServiceRequestStatus(
			String statusName,Integer employeeId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestStatus::START------------>:::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		serviceRequestDOs = complaintsBacklineSummaryDAO.getComplaintDetailsByServiceRequestStatus(statusName,employeeId);
		List<ServiceRequestTO> serviceRequestTOs = new ArrayList<ServiceRequestTO>();
		for(ServiceRequestDO serviceRequestDO : serviceRequestDOs){
			ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
			serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
			if(serviceRequestDO.getServiceRequestQueryTypeDO()!=null){
				String queryTypeCode = serviceRequestDO.getServiceRequestQueryTypeDO().getQueryTypeCode();
				if (queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT)) {
					serviceRequestTO.setIsLinkEnabled("Y");
				}
			}
			if(!StringUtil.isNull(serviceRequestDO.getCreatedBy())){
				StringBuilder frontlineExecName = null;
				EmployeeUserDO empuser = complaintsCommonDAO.getEmployeeUser(serviceRequestDO.getCreatedBy());
				if (!StringUtil.isNull(empuser) && !StringUtil.isNull(empuser.getEmpDO())){
					frontlineExecName = new StringBuilder();
					EmployeeDO emp = empuser.getEmpDO();
					if(!StringUtil.isStringEmpty(emp.getEmpCode())){
						frontlineExecName.append(emp.getEmpCode());
					}
					if(!StringUtil.isStringEmpty(emp.getFirstName())){
						frontlineExecName.append(" - ");
						frontlineExecName.append(emp.getFirstName());
					}
					if(!StringUtil.isStringEmpty(emp.getLastName())){
						frontlineExecName.append(" ");
						frontlineExecName.append(emp.getLastName());
					}
					serviceRequestTO.setFrontlineExecName(frontlineExecName.toString());
				}
			}
			serviceRequestTOs.add(serviceRequestTO);
		}
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestStatus::END------------>:::::::");
		return serviceRequestTOs;
	}
	@Override
	public List<ServiceRequestTO> getComplaintDetailsByServiceRequestStatus(
			String statusName) throws CGSystemException, CGBusinessException {
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestStatus::START------------>:::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		serviceRequestDOs = complaintsBacklineSummaryDAO.getComplaintDetailsByServiceRequestStatus(statusName);
		List<ServiceRequestTO> serviceRequestTOs = new ArrayList<ServiceRequestTO>();
		for(ServiceRequestDO serviceRequestDO : serviceRequestDOs){
			ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
			serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
			if(serviceRequestDO.getServiceRequestQueryTypeDO()!=null){
				String queryTypeCode = serviceRequestDO.getServiceRequestQueryTypeDO().getQueryTypeCode();
				if (queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT)) {
					serviceRequestTO.setIsLinkEnabled("Y");
				}
			}
			if(!StringUtil.isNull(serviceRequestDO.getCreatedBy())){
				StringBuilder frontlineExecName = null;
				EmployeeUserDO empuser = complaintsCommonDAO.getEmployeeUser(serviceRequestDO.getCreatedBy());
				if (!StringUtil.isNull(empuser) && !StringUtil.isNull(empuser.getEmpDO())){
					frontlineExecName = new StringBuilder();
					EmployeeDO emp = empuser.getEmpDO();
					if(!StringUtil.isStringEmpty(emp.getEmpCode())){
						frontlineExecName.append(emp.getEmpCode());
					}
					if(!StringUtil.isStringEmpty(emp.getFirstName())){
						frontlineExecName.append(" - ");
						frontlineExecName.append(emp.getFirstName());
					}
					if(!StringUtil.isStringEmpty(emp.getLastName())){
						frontlineExecName.append(" ");
						frontlineExecName.append(emp.getLastName());
					}
					serviceRequestTO.setFrontlineExecName(frontlineExecName.toString());
				}
			}
			serviceRequestTOs.add(serviceRequestTO);
		}
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByServiceRequestStatus::END------------>:::::::");
		return serviceRequestTOs;
	}
	@Override
	public List<ServiceRequestTO> getComplaintDetailsByUser(Integer employeeId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByUser::START------------>:::::::");
		List<ServiceRequestDO> serviceRequestDOs = null;
		serviceRequestDOs = complaintsBacklineSummaryDAO.getComplaintDetailsByUser(employeeId);
		List<ServiceRequestTO> serviceRequestTOs = null;
		if(!CGCollectionUtils.isEmpty(serviceRequestDOs)){
			serviceRequestTOs = new ArrayList<ServiceRequestTO>(serviceRequestDOs.size());
			for(ServiceRequestDO serviceRequestDO : serviceRequestDOs){
				ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
				serviceRequestTO = ComplaintsConverter.ConvertTOFromDO(serviceRequestDO);
				String queryTypeCode = serviceRequestDO.getServiceRequestQueryTypeDO().getQueryTypeCode();
				if (queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT)
						|| queryTypeCode.equalsIgnoreCase(ComplaintsServiceRequestConstants.SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT)) {
					serviceRequestTO.setIsLinkEnabled("Y");
				}
				
				serviceRequestTOs.add(serviceRequestTO);
			}
		}
		
		LOGGER.trace("ComplaintsBacklineSummaryServiceImpl::getComplaintDetailsByUser::END------------>:::::::");
		return serviceRequestTOs;
	}
	
}
