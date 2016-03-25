package com.ff.to.heldup;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserTO;

/**
 * The Class HeldUpTO.
 * 
 * @author narmdr
 */
public class HeldUpTO extends CGBaseTO {

	private static final long serialVersionUID = -6800308618556632950L;

	private Integer heldUpId;
	private String heldUpDateTime;
	private String heldUpNumber;
	private OfficeTO officeTO;
	private UserTO userTO;
	
	//grid
	private String transactionType;
	private String transactionNumber;
	private String currentLocation;
	private String remarks;
	private String processNumber;
	private EmployeeTO employeeTO;//informed By
	private ReasonTO reasonTO;//heldUpReason
	private Boolean isFind;

	private List<EmployeeTO> employeeTOs;
	private List<ReasonTO> reasonTOs;
	private List<ReasonTO> reasonLocationTOs;
	private List<StockStandardTypeTO> standardTypeTOs;
	
	//transaction Type
	private String transactionTypeConsignment;
	private String transactionTypeOpenManifest;
	private String transactionTypeOGM;
	private String transactionTypeBplDox;
	private String transactionTypeBplParcel;
	private String transactionTypeMbpl;
	private String transactionTypeMblDispatch;
	private String transactionTypeAwbCdRr;
	
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
	 * @return the heldUpDateTime
	 */
	public String getHeldUpDateTime() {
		return heldUpDateTime;
	}
	/**
	 * @param heldUpDateTime the heldUpDateTime to set
	 */
	public void setHeldUpDateTime(String heldUpDateTime) {
		this.heldUpDateTime = heldUpDateTime;
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
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
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
	 * @return the employeeTO
	 */
	public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}
	/**
	 * @param employeeTO the employeeTO to set
	 */
	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}
	/**
	 * @return the reasonTO
	 */
	public ReasonTO getReasonTO() {
		return reasonTO;
	}
	/**
	 * @param reasonTO the reasonTO to set
	 */
	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
	}
	/**
	 * @return the employeeTOs
	 */
	public List<EmployeeTO> getEmployeeTOs() {
		return employeeTOs;
	}
	/**
	 * @param employeeTOs the employeeTOs to set
	 */
	public void setEmployeeTOs(List<EmployeeTO> employeeTOs) {
		this.employeeTOs = employeeTOs;
	}
	/**
	 * @return the reasonTOs
	 */
	public List<ReasonTO> getReasonTOs() {
		return reasonTOs;
	}
	/**
	 * @param reasonTOs the reasonTOs to set
	 */
	public void setReasonTOs(List<ReasonTO> reasonTOs) {
		this.reasonTOs = reasonTOs;
	}
	/**
	 * @return the standardTypeTOs
	 */
	public List<StockStandardTypeTO> getStandardTypeTOs() {
		return standardTypeTOs;
	}
	/**
	 * @param standardTypeTOs the standardTypeTOs to set
	 */
	public void setStandardTypeTOs(List<StockStandardTypeTO> standardTypeTOs) {
		this.standardTypeTOs = standardTypeTOs;
	}
	/**
	 * @return the userTO
	 */
	public UserTO getUserTO() {
		return userTO;
	}
	/**
	 * @param userTO the userTO to set
	 */
	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}
	/**
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}
	/**
	 * @param processNumber the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	/**
	 * @return the transactionTypeConsignment
	 */
	public String getTransactionTypeConsignment() {
		return transactionTypeConsignment;
	}
	/**
	 * @param transactionTypeConsignment the transactionTypeConsignment to set
	 */
	public void setTransactionTypeConsignment(String transactionTypeConsignment) {
		this.transactionTypeConsignment = transactionTypeConsignment;
	}
	/**
	 * @return the transactionTypeOpenManifest
	 */
	public String getTransactionTypeOpenManifest() {
		return transactionTypeOpenManifest;
	}
	/**
	 * @param transactionTypeOpenManifest the transactionTypeOpenManifest to set
	 */
	public void setTransactionTypeOpenManifest(String transactionTypeOpenManifest) {
		this.transactionTypeOpenManifest = transactionTypeOpenManifest;
	}
	/**
	 * @return the transactionTypeOGM
	 */
	public String getTransactionTypeOGM() {
		return transactionTypeOGM;
	}
	/**
	 * @param transactionTypeOGM the transactionTypeOGM to set
	 */
	public void setTransactionTypeOGM(String transactionTypeOGM) {
		this.transactionTypeOGM = transactionTypeOGM;
	}
	/**
	 * @return the transactionTypeBplDox
	 */
	public String getTransactionTypeBplDox() {
		return transactionTypeBplDox;
	}
	/**
	 * @param transactionTypeBplDox the transactionTypeBplDox to set
	 */
	public void setTransactionTypeBplDox(String transactionTypeBplDox) {
		this.transactionTypeBplDox = transactionTypeBplDox;
	}
	/**
	 * @return the transactionTypeBplParcel
	 */
	public String getTransactionTypeBplParcel() {
		return transactionTypeBplParcel;
	}
	/**
	 * @param transactionTypeBplParcel the transactionTypeBplParcel to set
	 */
	public void setTransactionTypeBplParcel(String transactionTypeBplParcel) {
		this.transactionTypeBplParcel = transactionTypeBplParcel;
	}
	/**
	 * @return the transactionTypeAwbCdRr
	 */
	public String getTransactionTypeAwbCdRr() {
		return transactionTypeAwbCdRr;
	}
	/**
	 * @param transactionTypeAwbCdRr the transactionTypeAwbCdRr to set
	 */
	public void setTransactionTypeAwbCdRr(String transactionTypeAwbCdRr) {
		this.transactionTypeAwbCdRr = transactionTypeAwbCdRr;
	}
	/**
	 * @return the transactionTypeMbpl
	 */
	public String getTransactionTypeMbpl() {
		return transactionTypeMbpl;
	}
	/**
	 * @param transactionTypeMbpl the transactionTypeMbpl to set
	 */
	public void setTransactionTypeMbpl(String transactionTypeMbpl) {
		this.transactionTypeMbpl = transactionTypeMbpl;
	}
	/**
	 * @return the transactionTypeMblDispatch
	 */
	public String getTransactionTypeMblDispatch() {
		return transactionTypeMblDispatch;
	}
	/**
	 * @param transactionTypeMblDispatch the transactionTypeMblDispatch to set
	 */
	public void setTransactionTypeMblDispatch(String transactionTypeMblDispatch) {
		this.transactionTypeMblDispatch = transactionTypeMblDispatch;
	}
	/**
	 * @return the isFind
	 */
	public Boolean getIsFind() {
		return isFind;
	}
	/**
	 * @param isFind the isFind to set
	 */
	public void setIsFind(Boolean isFind) {
		this.isFind = isFind;
	}
	
	public List<ReasonTO> getReasonLocationTOs() {
		return reasonLocationTOs;
	}
	
	public void setReasonLocationTOs(List<ReasonTO> reasonLocationTOs) {
		this.reasonLocationTOs = reasonLocationTOs;
	}
	
}
