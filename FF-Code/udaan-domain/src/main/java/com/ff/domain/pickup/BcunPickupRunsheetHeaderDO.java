package com.ff.domain.pickup;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BcunPickupRunsheetHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -163672826761179168L;
	private Integer runsheetHeaderId;
	private String runsheetNo;
	private BcunRunsheetAssignmentHeaderDO pickupAssignmentHeader;
	private Date runsheetDate;
	private String runsheetStatus;
	@JsonManagedReference
	private Set<BcunPickupRunsheetDetailDO> runsheetDetails;
	
	public Integer getRunsheetHeaderId() {
		return runsheetHeaderId;
	}
	public void setRunsheetHeaderId(Integer runsheetHeaderId) {
		this.runsheetHeaderId = runsheetHeaderId;
	}
	public String getRunsheetNo() {
		return runsheetNo;
	}
	public void setRunsheetNo(String runsheetNo) {
		this.runsheetNo = runsheetNo;
	}
	public BcunRunsheetAssignmentHeaderDO getPickupAssignmentHeader() {
		return pickupAssignmentHeader;
	}
	public void setPickupAssignmentHeader(
			BcunRunsheetAssignmentHeaderDO pickupAssignmentHeader) {
		this.pickupAssignmentHeader = pickupAssignmentHeader;
	}
	public Date getRunsheetDate() {
		return runsheetDate;
	}
	public void setRunsheetDate(Date runsheetDate) {
		this.runsheetDate = runsheetDate;
	}
	public Set<BcunPickupRunsheetDetailDO> getRunsheetDetails() {
		return runsheetDetails;
	}
	public void setRunsheetDetails(Set<BcunPickupRunsheetDetailDO> runsheetDetails) {
		this.runsheetDetails = runsheetDetails;
	}
	public String getRunsheetStatus() {
		return runsheetStatus;
	}
	public void setRunsheetStatus(String runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}
}
