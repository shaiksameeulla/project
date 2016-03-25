/**
 * 
 */
package com.ff.booking;

import java.util.List;

/**
 * The Class EmotionalBondBookingTO.
 * 
 * @author uchauhan
 */
public class EmotionalBondBookingTO extends BookingGridTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2846169350886835809L;

	/** The count. */
	private int count;

	/** The delv date time. */
	private String delvDateTime;

	/** The dlv time. */
	private String dlvTime;

	/** The eb number. */
	private String ebNumber;

	/** The status id. */
	private String statusId;

	/** The status code. */
	private String statusCode;

	/** The status name. */
	private String statusName;

	/** The relation. */
	private String relation;

	/** The email. */
	private String email;

	/** The instruction. */
	private String instruction;

	/** The amount. */
	private String amount;

	/** The booking branch. */
	private String bookingBranch;

	/** The dlv branch. */
	private String dlvBranch;
	
	private String dlvBranchId;

	/** The chkboxes. */
	private String chkboxes = "";

	/** The is checked. */
	private String[] isChecked = new String[count];

	/** The felicitation. */
	private List<String> felicitation;

	/** The status. */
	private String[] status = new String[count];

	/** The preference ids. */
	private String preferenceIds;

	private Integer destBranchId;
	
	private String otherPref;
	
	
	

	/**
	 * @return the dlvBranchId
	 */
	public String getDlvBranchId() {
		return dlvBranchId;
	}

	/**
	 * @param dlvBranchId the dlvBranchId to set
	 */
	public void setDlvBranchId(String dlvBranchId) {
		this.dlvBranchId = dlvBranchId;
	}

	/**
	 * @return the otherPref
	 */
	public String getOtherPref() {
		return otherPref;
	}

	/**
	 * @param otherPref the otherPref to set
	 */
	public void setOtherPref(String otherPref) {
		this.otherPref = otherPref;
	}

	/**
	 * Gets the booking branch.
	 * 
	 * @return the bookingBranch
	 */
	public String getBookingBranch() {
		return bookingBranch;
	}

	/**
	 * Sets the booking branch.
	 * 
	 * @param bookingBranch
	 *            the bookingBranch to set
	 */
	public void setBookingBranch(String bookingBranch) {
		this.bookingBranch = bookingBranch;
	}

	/**
	 * Gets the dlv branch.
	 * 
	 * @return the dlvBranch
	 */
	public String getDlvBranch() {
		return dlvBranch;
	}

	/**
	 * Sets the dlv branch.
	 * 
	 * @param dlvBranch
	 *            the dlvBranch to set
	 */
	public void setDlvBranch(String dlvBranch) {
		this.dlvBranch = dlvBranch;
	}

	/**
	 * Gets the delv date time.
	 * 
	 * @return the delvDateTime
	 */
	public String getDelvDateTime() {
		return delvDateTime;
	}

	/**
	 * Sets the delv date time.
	 * 
	 * @param delvDateTime
	 *            the delvDateTime to set
	 */
	public void setDelvDateTime(String delvDateTime) {
		this.delvDateTime = delvDateTime;
	}

	/**
	 * Gets the eb number.
	 * 
	 * @return the ebNumber
	 */
	public String getEbNumber() {
		return ebNumber;
	}

	/**
	 * Sets the eb number.
	 * 
	 * @param ebNumber
	 *            the ebNumber to set
	 */
	public void setEbNumber(String ebNumber) {
		this.ebNumber = ebNumber;
	}

	/**
	 * Gets the relation.
	 * 
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * Sets the relation.
	 * 
	 * @param relation
	 *            the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the instruction.
	 * 
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * Sets the instruction.
	 * 
	 * @param instruction
	 *            the instruction to set
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/**
	 * Gets the amount.
	 * 
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 * 
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Gets the status id.
	 * 
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * Sets the status id.
	 * 
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * Gets the status name.
	 * 
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * Sets the status name.
	 * 
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * Gets the dlv time.
	 * 
	 * @return the dlvTime
	 */
	public String getDlvTime() {
		return dlvTime;
	}

	/**
	 * Sets the dlv time.
	 * 
	 * @param dlvTime
	 *            the dlvTime to set
	 */
	public void setDlvTime(String dlvTime) {
		this.dlvTime = dlvTime;
	}

	/**
	 * Gets the checks if is checked.
	 * 
	 * @return the checks if is checked
	 */
	public String[] getIsChecked() {
		return isChecked;
	}

	/**
	 * Sets the checks if is checked.
	 * 
	 * @param isChecked
	 *            the new checks if is checked
	 */
	public void setIsChecked(String[] isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * Gets the chkboxes.
	 * 
	 * @return the chkboxes
	 */
	public String getChkboxes() {
		return chkboxes;
	}

	/**
	 * Sets the chkboxes.
	 * 
	 * @param chkboxes
	 *            the chkboxes to set
	 */
	public void setChkboxes(String chkboxes) {
		this.chkboxes = chkboxes;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String[] getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String[] status) {
		this.status = status;
	}

	/**
	 * Gets the status code.
	 * 
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 * 
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the felicitation.
	 * 
	 * @return the felicitation
	 */
	public List<String> getFelicitation() {
		return felicitation;
	}

	/**
	 * Sets the felicitation.
	 * 
	 * @param felicitation
	 *            the felicitation to set
	 */
	public void setFelicitation(List<String> felicitation) {
		this.felicitation = felicitation;
	}

	/**
	 * Gets the preference ids.
	 * 
	 * @return the preference ids
	 */
	public String getPreferenceIds() {
		return preferenceIds;
	}

	/**
	 * Sets the preference ids.
	 * 
	 * @param preferenceIds
	 *            the new preference ids
	 */
	public void setPreferenceIds(String preferenceIds) {
		this.preferenceIds = preferenceIds;
	}

	/**
	 * @return the destBranchId
	 */
	public Integer getDestBranchId() {
		return destBranchId;
	}

	/**
	 * @param destBranchId
	 *            the destBranchId to set
	 */
	public void setDestBranchId(Integer destBranchId) {
		this.destBranchId = destBranchId;
	}

}
