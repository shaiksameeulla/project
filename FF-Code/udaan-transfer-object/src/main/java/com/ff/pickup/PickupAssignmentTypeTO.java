/**
 * 
 */
package com.ff.pickup;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author kgajare
 * 
 */
public class PickupAssignmentTypeTO extends CGBaseTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2439479345307204656L;
    private Integer assignmentTypeId;
    private String assignmentTypeCode;
    private String assignmentTypeDescription;
    public Integer getAssignmentTypeId() {
        return assignmentTypeId;
    }
    public void setAssignmentTypeId(Integer assignmentTypeId) {
        this.assignmentTypeId = assignmentTypeId;
    }
    public String getAssignmentTypeCode() {
        return assignmentTypeCode;
    }
    public void setAssignmentTypeCode(String assignmentTypeCode) {
        this.assignmentTypeCode = assignmentTypeCode;
    }
    public String getAssignmentTypeDescription() {
        return assignmentTypeDescription;
    }
    public void setAssignmentTypeDescription(String assignmentTypeDescription) {
        this.assignmentTypeDescription = assignmentTypeDescription;
    }
    /**
     * @see java.lang.Object#toString()
     * Nov 27, 2012
     * @return
     * toString
     * com.ff.pickup.PickupAssignmentTypeTO
     * kgajare
     */
    @Override
    public String toString() {
	return "PickupAssignmentTypeTO [assignmentTypeId=" + assignmentTypeId
		+ ", assignmentTypeCode=" + assignmentTypeCode
		+ ", assignmentTypeDescription=" + assignmentTypeDescription
		+ "]";
    }
    
    
    

}
