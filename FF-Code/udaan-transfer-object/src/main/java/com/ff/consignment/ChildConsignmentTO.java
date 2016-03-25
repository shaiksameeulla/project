/**
 * 
 */
package com.ff.consignment;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nkattung
 * 
 */
public class ChildConsignmentTO extends CGBaseTO implements Comparable<ChildConsignmentTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5777876765687508291L;
	private Integer bookingChildCNId;
	private String childConsgNumber;
	private Double childConsgWeight;
	private Integer consignmentId;
	private Integer createdBy ;
	private Integer updatedBy;
	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
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
	public Integer getConsignmentId() {
		return consignmentId;
	}
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}
	@Override
	public int compareTo(ChildConsignmentTO arg0) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.childConsgNumber)) {
			returnVal = this.childConsgNumber.compareTo(arg0.childConsgNumber);
		}
		return returnVal;
	}
	
	
}
