package com.ff.domain.consignment;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class ChildConsignmentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343388048684720844L;
	private Integer bookingChildCNId;
	private String childConsgNumber;
	private Double childConsgWeight;
	
	@JsonBackReference
	private ConsignmentDO consignment;
	
	private Boolean isChildConsignmentUpdated = Boolean.FALSE;

	public Integer getBookingChildCNId() {
		return bookingChildCNId;
	}

	public void setBookingChildCNId(Integer bookingChildCNId) {
		this.bookingChildCNId = bookingChildCNId;
	}

	public String getChildConsgNumber() {
		return childConsgNumber;
	}

	public void setChildConsgNumber(String childConsgNumber) {
		this.childConsgNumber = childConsgNumber;
	}

	public Double getChildConsgWeight() {
		return childConsgWeight;
	}

	public void setChildConsgWeight(Double childConsgWeight) {
		this.childConsgWeight = childConsgWeight;
	}

	public ConsignmentDO getConsignment() {
		return consignment;
	}

	public void setConsignment(ConsignmentDO consignment) {
		this.consignment = consignment;
	}

	/**
	 * @return the isChildConsignmentUpdated
	 */
	public Boolean getIsChildConsignmentUpdated() {
		return isChildConsignmentUpdated;
	}

	/**
	 * @param isChildConsignmentUpdated the isChildConsignmentUpdated to set
	 */
	public void setIsChildConsignmentUpdated(Boolean isChildConsignmentUpdated) {
		this.isChildConsignmentUpdated = isChildConsignmentUpdated;
	}

}
