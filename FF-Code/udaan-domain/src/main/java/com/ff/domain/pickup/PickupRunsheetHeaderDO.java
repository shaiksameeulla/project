package com.ff.domain.pickup;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class PickupRunsheetHeaderDO.
 */
public class PickupRunsheetHeaderDO extends CGFactDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9001465911708143483L;
	
	/** The runsheet header id. */
	private Integer runsheetHeaderId;
	
	/** The runsheet no. */
	private String runsheetNo;
	
	/** The pickup assignment header. */
	private RunsheetAssignmentHeaderDO pickupAssignmentHeader;
	
	/** The runsheet date. */
	private Date runsheetDate;
	
	/** The runsheet status. */
	private String runsheetStatus;
	
	/** The runsheet details. */
	private Set<PickupRunsheetDetailDO> runsheetDetails;
	
	/**
	 * Gets the runsheet header id.
	 *
	 * @return the runsheet header id
	 */
	public Integer getRunsheetHeaderId() {
		return runsheetHeaderId;
	}
	
	/**
	 * Sets the runsheet header id.
	 *
	 * @param runsheetHeaderId the new runsheet header id
	 */
	public void setRunsheetHeaderId(Integer runsheetHeaderId) {
		this.runsheetHeaderId = runsheetHeaderId;
	}
	
	/**
	 * Gets the runsheet no.
	 *
	 * @return the runsheet no
	 */
	public String getRunsheetNo() {
		return runsheetNo;
	}
	
	/**
	 * Sets the runsheet no.
	 *
	 * @param runsheetNo the new runsheet no
	 */
	public void setRunsheetNo(String runsheetNo) {
		this.runsheetNo = runsheetNo;
	}
	
	/**
	 * Gets the runsheet date.
	 *
	 * @return the runsheet date
	 */
	public Date getRunsheetDate() {
		return runsheetDate;
	}
	
	/**
	 * Gets the pickup assignment header.
	 *
	 * @return the pickup assignment header
	 */
	public RunsheetAssignmentHeaderDO getPickupAssignmentHeader() {
		return pickupAssignmentHeader;
	}
	
	/**
	 * Sets the pickup assignment header.
	 *
	 * @param pickupAssignmentHeader the new pickup assignment header
	 */
	public void setPickupAssignmentHeader(
			RunsheetAssignmentHeaderDO pickupAssignmentHeader) {
		this.pickupAssignmentHeader = pickupAssignmentHeader;
	}
	
	/**
	 * Sets the runsheet date.
	 *
	 * @param runsheetDate the new runsheet date
	 */
	public void setRunsheetDate(Date runsheetDate) {
		this.runsheetDate = runsheetDate;
	}
	
	/**
	 * Gets the runsheet status.
	 *
	 * @return the runsheet status
	 */
	public String getRunsheetStatus() {
		return runsheetStatus;
	}
	
	/**
	 * Sets the runsheet status.
	 *
	 * @param runsheetStatus the new runsheet status
	 */
	public void setRunsheetStatus(String runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}
	
	/**
	 * Gets the runsheet details.
	 *
	 * @return the runsheet details
	 */
	public Set<PickupRunsheetDetailDO> getRunsheetDetails() {
		return runsheetDetails;
	}
	
	/**
	 * Sets the runsheet details.
	 *
	 * @param runsheetDetails the new runsheet details
	 */
	public void setRunsheetDetails(Set<PickupRunsheetDetailDO> runsheetDetails) {
		this.runsheetDetails = runsheetDetails;
	}
}
