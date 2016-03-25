/**
 * 
 */
package com.ff.admin.complaints.converter;

import java.util.Calendar;

import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.complaints.ServiceRequestFollowupTO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestFollowupDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author prmeher
 * 
 */
public class ServiceRequestFollowupConverter {

	/**
	 * Conver TO to Do
	 * 
	 * @param serviceRequestFollowupTO
	 * @return
	 */
	public static ServiceRequestFollowupDO followupDomainConverter(
			ServiceRequestFollowupTO serviceRequestFollowupTO) {
		ServiceRequestFollowupDO followupDO = new ServiceRequestFollowupDO();
		ServiceRequestDO complaint = null;
		EmployeeDO employee = null;
		followupDO.setFollowUpDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(serviceRequestFollowupTO
						.getFollowUpDate()));
		followupDO.setCallFrom(serviceRequestFollowupTO.getCallFrom());
		followupDO.setCaller(serviceRequestFollowupTO.getCaller());
		followupDO.setCustomerName(serviceRequestFollowupTO.getCustomerName());
		followupDO.setEmail(serviceRequestFollowupTO.getEmail());
		followupDO.setFollowupNote(serviceRequestFollowupTO.getFollowupNote());
		followupDO.setCreatedBy(serviceRequestFollowupTO.getLoginUserId());
		followupDO.setUpdatedBy(serviceRequestFollowupTO.getLoginUserId());
		if (!StringUtil.isEmptyInteger(serviceRequestFollowupTO.getComplaintId())){
			complaint = new ServiceRequestDO();
			complaint.setServiceRequestId(serviceRequestFollowupTO.getComplaintId());
			followupDO.setServiceRequestDO(complaint);
		}
		if (!StringUtil.isEmptyInteger(serviceRequestFollowupTO.getEmployeeId())){
			employee = new EmployeeDO();
			employee.setEmployeeId(serviceRequestFollowupTO.getEmployeeId());
			followupDO.setEmployeeDO(employee);
		}
		followupDO.setCreatedDate(Calendar.getInstance().getTime());
		followupDO.setUpdatedDate(Calendar.getInstance().getTime());
		return followupDO;
	}

	/**
	 * @param followupDO
	 * @return
	 */
	public static ServiceRequestFollowupTO followupTransferObjectConverter(
			ServiceRequestFollowupDO followupDO) {
		ServiceRequestFollowupTO followupTO = new ServiceRequestFollowupTO();
		followupTO.setFollowupDetails(followupDO.getServiceRequestDO().getServiceRequestNo());
		followupTO.setConsigNo(followupDO.getServiceRequestDO().getBookingNo());
		followupTO.setFollowUpDate(DateUtil.getDDMMYYYYDateString(followupDO.getFollowUpDate()));
		followupTO.setFollowupTime(DateUtil.getTimeFromDate(followupDO.getFollowUpDate()));
		followupTO.setCallFrom(followupDO.getCallFrom());
		followupTO.setCaller(followupDO.getCaller());
		followupTO.setCustomerName(followupDO.getCustomerName());
		followupTO.setEmail(followupDO.getEmail());
		followupTO.setFollowupNote(followupDO.getFollowupNote());
		if(!StringUtil.isNull(followupDO.getEmployeeDO())){
			EmployeeDO emp = followupDO.getEmployeeDO();
			followupTO.setEmpDetails(emp.getEmpCode()+ "-" + emp.getFirstName() +" " + emp.getLastName());
		}
		return followupTO;
	}

}
