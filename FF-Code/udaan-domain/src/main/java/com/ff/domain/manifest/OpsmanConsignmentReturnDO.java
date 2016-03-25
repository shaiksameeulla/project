package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.OpsmanConsignmentDO;

public class OpsmanConsignmentReturnDO extends CGFactDO {
	
	
	private static final long serialVersionUID = -5533584885757993427L;
	private Integer consignmentReturnId;
	private String isEmailValidated = CommonConstants.NO; //Y-Yes, N-No
	private String contactNumber;
	private String returnType; //R-RTO, H-RTH
	private Integer officeId;//loginOfficeId
	private OpsmanConsignmentDO consignmentDO;
	private Integer processId;
	private Date drsDateTime;
	private Set<BcunConsignmentReturnReasonDO> consignmentReturnReasonDOs;

	public Integer getConsignmentReturnId() {
		return consignmentReturnId;
	}

	public void setConsignmentReturnId(Integer consignmentReturnId) {
		this.consignmentReturnId = consignmentReturnId;
	}

	public String getIsEmailValidated() {
		return isEmailValidated;
	}

	public void setIsEmailValidated(String isEmailValidated) {
		this.isEmailValidated = isEmailValidated;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public OpsmanConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}

	public void setConsignmentDO(OpsmanConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Date getDrsDateTime() {
		return drsDateTime;
	}

	public void setDrsDateTime(Date drsDateTime) {
		this.drsDateTime = drsDateTime;
	}

	public Set<BcunConsignmentReturnReasonDO> getConsignmentReturnReasonDOs() {
		return consignmentReturnReasonDOs;
	}

	public void setConsignmentReturnReasonDOs(
			Set<BcunConsignmentReturnReasonDO> consignmentReturnReasonDOs) {
		this.consignmentReturnReasonDOs = consignmentReturnReasonDOs;
	}

}
