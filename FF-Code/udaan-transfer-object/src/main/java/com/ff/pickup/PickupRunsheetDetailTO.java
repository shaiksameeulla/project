package com.ff.pickup;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PickupRunsheetDetailTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779134106440977566L;
	private Integer runsheetDetailId;
	/*private PickupRunsheetHeaderDO runsheetHeaderDtls;
	private RunsheetAssignmentDetailDO pickupAssignmentDtls;*/
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
