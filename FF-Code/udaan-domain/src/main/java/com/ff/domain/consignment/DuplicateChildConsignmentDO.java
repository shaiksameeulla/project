package com.ff.domain.consignment;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class DuplicateChildConsignmentDO.
 * 
 * @author narmdr
 */
public class DuplicateChildConsignmentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2568849904533090253L;
	
	private Integer childCNId;
	private String childConsgNumber;
	private Double childConsgWeight;

	private Integer duplicateConsignmentId;

	/**
	 * @return the childConsgNumber
	 */
	public String getChildConsgNumber() {
		return childConsgNumber;
	}

	/**
	 * @param childConsgNumber the childConsgNumber to set
	 */
	public void setChildConsgNumber(String childConsgNumber) {
		this.childConsgNumber = childConsgNumber;
	}

	/**
	 * @return the childConsgWeight
	 */
	public Double getChildConsgWeight() {
		return childConsgWeight;
	}

	/**
	 * @param childConsgWeight the childConsgWeight to set
	 */
	public void setChildConsgWeight(Double childConsgWeight) {
		this.childConsgWeight = childConsgWeight;
	}

	/**
	 * @return the duplicateConsignmentId
	 */
	public Integer getDuplicateConsignmentId() {
		return duplicateConsignmentId;
	}

	/**
	 * @param duplicateConsignmentId the duplicateConsignmentId to set
	 */
	public void setDuplicateConsignmentId(Integer duplicateConsignmentId) {
		this.duplicateConsignmentId = duplicateConsignmentId;
	}

	/**
	 * @return the childCNId
	 */
	public Integer getChildCNId() {
		return childCNId;
	}

	/**
	 * @param childCNId the childCNId to set
	 */
	public void setChildCNId(Integer childCNId) {
		this.childCNId = childCNId;
	}
	
}
