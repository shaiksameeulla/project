package com.ff.domain.manifest;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;


/**
 * The Class BcunConsignmentReturnDO.
 *
 * @author narmdr
 */
public class BcunConsignmentReturnDO extends CGFactDO {
	
	private static final long serialVersionUID = -3974825812062330832L;
	private Integer consignmentReturnId;
	private String isEmailValidated = CommonConstants.NO; //Y-Yes, N-No
	private String contactNumber;
	private String returnType; //R-RTO, H-RTH
	//private OfficeDO officeDO;
	private Integer officeId;//loginOfficeId
	private ConsignmentDO consignmentDO;
	//private ProcessDO processDO;
	private Integer processId;
	private Date drsDateTime;
	
	private Set<BcunConsignmentReturnReasonDO> consignmentReturnReasonDOs;
	
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
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * @return the consignmentReturnReasonDOs
	 */
	public Set<BcunConsignmentReturnReasonDO> getConsignmentReturnReasonDOs() {
		return consignmentReturnReasonDOs;
	}
	/**
	 * @param consignmentReturnReasonDOs the consignmentReturnReasonDOs to set
	 */
	public void setConsignmentReturnReasonDOs(
			Set<BcunConsignmentReturnReasonDO> consignmentReturnReasonDOs) {
		this.consignmentReturnReasonDOs = consignmentReturnReasonDOs;
	}
}
