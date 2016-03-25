package com.ff.domain.pickup;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class SapEmployeePickupDO  extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pickupId;
	private String officeCode;
	private String empCode;
	private Date startDate;
	private Date endDate;
	private Integer pickupCount;
	private Integer deliveryCount;

	public SapEmployeePickupDO() {
	}

	public SapEmployeePickupDO(String officeCode, String empCode,
			Date startDate, Date endDate) {
		this.officeCode = officeCode;
		this.empCode = empCode;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public SapEmployeePickupDO(String officeCode, String empCode,
			Date startDate, Date endDate, Integer pickupCount,
			Integer deliveryCount) {
		this.officeCode = officeCode;
		this.empCode = empCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pickupCount = pickupCount;
		this.deliveryCount = deliveryCount;
	}

	public Integer getPickupId() {
		return this.pickupId;
	}

	public void setPickupId(Integer pickupId) {
		this.pickupId = pickupId;
	}

	public String getOfficeCode() {
		return this.officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPickupCount() {
		return this.pickupCount;
	}

	public void setPickupCount(Integer pickupCount) {
		this.pickupCount = pickupCount;
	}

	public Integer getDeliveryCount() {
		return this.deliveryCount;
	}

	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

}
