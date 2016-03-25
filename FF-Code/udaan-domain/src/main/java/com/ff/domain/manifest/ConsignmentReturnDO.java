package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;


/**
 * The Class ConsignmentReturnDO.
 *
 * @author narmdr
 */
public class ConsignmentReturnDO extends CGFactDO {
	
	private static final long serialVersionUID = 1711296665323188317L;
	private Integer consignmentReturnId;
	private String isEmailValidated = CommonConstants.NO; //Y-Yes, N-No
	private String contactNumber;
	private String returnType; //R-RTO, H-RTH
	private OfficeDO officeDO;
	private ConsignmentDO consignmentDO;
	private ProcessDO processDO;
	private Date drsDateTime;
	
	@JsonManagedReference
	private Set<ConsignmentReturnReasonDO> consignmentReturnReasonDOs;
	
	/**
	 * @return the consignmentReturnId
	 */
	public Integer getConsignmentReturnId() {
		return consignmentReturnId;
	}
	/**
	 * @param consignmentReturnId the consignmentReturnId to set
	 */
	public void setConsignmentReturnId(Integer consignmentReturnId) {
		this.consignmentReturnId = consignmentReturnId;
	}
	/**
	 * @return the isEmailValidated
	 */
	public String getIsEmailValidated() {
		return isEmailValidated;
	}
	/**
	 * @param isEmailValidated the isEmailValidated to set
	 */
	public void setIsEmailValidated(String isEmailValidated) {
		this.isEmailValidated = isEmailValidated;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	/**
	 * @return the officeDO
	 */
	public OfficeDO getOfficeDO() {
		return officeDO;
	}
	/**
	 * @param officeDO the officeDO to set
	 */
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
	}
	/**
	 * @return the consignmentDO
	 */
	public ConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}
	/**
	 * @param consignmentDO the consignmentDO to set
	 */
	public void setConsignmentDO(ConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}
	/**
	 * @return the processDO
	 */
	public ProcessDO getProcessDO() {
		return processDO;
	}
	/**
	 * @param processDO the processDO to set
	 */
	public void setProcessDO(ProcessDO processDO) {
		this.processDO = processDO;
	}
	/**
	 * @return the consignmentReturnReasonDOs
	 */
	public Set<ConsignmentReturnReasonDO> getConsignmentReturnReasonDOs() {
		return consignmentReturnReasonDOs;
	}
	/**
	 * @param consignmentReturnReasonDOs the consignmentReturnReasonDOs to set
	 */
	public void setConsignmentReturnReasonDOs(
			Set<ConsignmentReturnReasonDO> consignmentReturnReasonDOs) {
		this.consignmentReturnReasonDOs = consignmentReturnReasonDOs;
	}
	/**
	 * @return the drsDateTime
	 */
	public Date getDrsDateTime() {
		return drsDateTime;
	}
	/**
	 * @param drsDateTime the drsDateTime to set
	 */
	public void setDrsDateTime(Date drsDateTime) {
		this.drsDateTime = drsDateTime;
	}
		
}
