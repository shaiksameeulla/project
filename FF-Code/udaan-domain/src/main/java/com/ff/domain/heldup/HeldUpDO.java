package com.ff.domain.heldup;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.umc.UserDO;

/**
 * The Class HeldUpDO.
 *
 * @author narmdr
 */
public class HeldUpDO extends CGFactDO {
	
	private static final long serialVersionUID = 3902743826446462207L;

	private Integer heldUpId;
	private String heldUpNumber;
	private Date heldUpDate;
	private String transactionType;
	private String transactionNumber;
	private String currentLocation;
	private String remarks;
	private OfficeDO officeDO;
	private UserDO userDO;
	private EmployeeDO employeeDO;//Informed By
	private ReasonDO reasonDO;//heldUpReason
	private ProcessDO processDO;
	
	/**
	 * @return the heldUpId
	 */
	public Integer getHeldUpId() {
		return heldUpId;
	}
	/**
	 * @param heldUpId the heldUpId to set
	 */
	public void setHeldUpId(Integer heldUpId) {
		this.heldUpId = heldUpId;
	}
	/**
	 * @return the heldUpNumber
	 */
	public String getHeldUpNumber() {
		return heldUpNumber;
	}
	/**
	 * @param heldUpNumber the heldUpNumber to set
	 */
	public void setHeldUpNumber(String heldUpNumber) {
		this.heldUpNumber = heldUpNumber;
	}
	/**
	 * @return the heldUpDate
	 */
	public Date getHeldUpDate() {
		return heldUpDate;
	}
	/**
	 * @param heldUpDate the heldUpDate to set
	 */
	public void setHeldUpDate(Date heldUpDate) {
		this.heldUpDate = heldUpDate;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the transactionNumber
	 */
	public String getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}
	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the userDO
	 */
	public UserDO getUserDO() {
		return userDO;
	}
	/**
	 * @param userDO the userDO to set
	 */
	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}
	/**
	 * @return the employeeDO
	 */
	public EmployeeDO getEmployeeDO() {
		return employeeDO;
	}
	/**
	 * @param employeeDO the employeeDO to set
	 */
	public void setEmployeeDO(EmployeeDO employeeDO) {
		this.employeeDO = employeeDO;
	}
	/**
	 * @return the reasonDO
	 */
	public ReasonDO getReasonDO() {
		return reasonDO;
	}
	/**
	 * @param reasonDO the reasonDO to set
	 */
	public void setReasonDO(ReasonDO reasonDO) {
		this.reasonDO = reasonDO;
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

}
