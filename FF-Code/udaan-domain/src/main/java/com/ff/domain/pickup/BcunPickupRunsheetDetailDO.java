package com.ff.domain.pickup;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BcunPickupRunsheetDetailDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8264316949253650490L;
	private Integer runsheetDetailId;
	@JsonBackReference
	private BcunPickupRunsheetHeaderDO runsheetHeaderDtls;
	private BcunRunsheetAssignmentDetailDO pickupAssignmentDtls;
	private Date pickupTime;
	private String startCnNo;
	private String endCnNo;
	private Integer cnCount;
	
	public Integer getRunsheetDetailId() {
		return runsheetDetailId;
	}
	public void setRunsheetDetailId(Integer runsheetDetailId) {
		this.runsheetDetailId = runsheetDetailId;
	}
	public BcunPickupRunsheetHeaderDO getRunsheetHeaderDtls() {
		return runsheetHeaderDtls;
	}
	public void setRunsheetHeaderDtls(BcunPickupRunsheetHeaderDO runsheetHeaderDtls) {
		this.runsheetHeaderDtls = runsheetHeaderDtls;
	}
	public BcunRunsheetAssignmentDetailDO getPickupAssignmentDtls() {
		return pickupAssignmentDtls;
	}
	public void setPickupAssignmentDtls(
			BcunRunsheetAssignmentDetailDO pickupAssignmentDtls) {
		this.pickupAssignmentDtls = pickupAssignmentDtls;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getStartCnNo() {
		return startCnNo;
	}
	public void setStartCnNo(String startCnNo) {
		this.startCnNo = startCnNo;
	}
	public String getEndCnNo() {
		return endCnNo;
	}
	public void setEndCnNo(String endCnNo) {
		this.endCnNo = endCnNo;
	}
	public Integer getCnCount() {
		return cnCount;
	}
	public void setCnCount(Integer cnCount) {
		this.cnCount = cnCount;
	}
}
