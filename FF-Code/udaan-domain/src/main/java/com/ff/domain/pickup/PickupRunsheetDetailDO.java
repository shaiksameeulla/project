package com.ff.domain.pickup;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class PickupRunsheetDetailDO.
 */
public class PickupRunsheetDetailDO extends CGFactDO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8318462501980305350L;
	
	/** The runsheet detail id. */
	private Integer runsheetDetailId;
	
	/** The runsheet header dtls. */
	private PickupRunsheetHeaderDO runsheetHeaderDtls;
	
	/** The pickup assignment dtls. */
	private RunsheetAssignmentDetailDO pickupAssignmentDtls;
	
	/** The pickup time. */
	private Date pickupTime;
	
	/** The start cn no. */
	private String startCnNo;
	
	/** The end cn no. */
	private String endCnNo;
	
	/** The cn count. */
	private Integer cnCount;
	
	private String duplicateCustRow="N";

	/**
	 * Gets the runsheet detail id.
	 *
	 * @return the runsheet detail id
	 */
	public Integer getRunsheetDetailId() {
		return runsheetDetailId;
	}

	/**
	 * Sets the runsheet detail id.
	 *
	 * @param runsheetDetailId the new runsheet detail id
	 */
	public void setRunsheetDetailId(Integer runsheetDetailId) {
		this.runsheetDetailId = runsheetDetailId;
	}

	/**
	 * Gets the runsheet header dtls.
	 *
	 * @return the runsheet header dtls
	 */
	public PickupRunsheetHeaderDO getRunsheetHeaderDtls() {
		return runsheetHeaderDtls;
	}

	/**
	 * Sets the runsheet header dtls.
	 *
	 * @param runsheetHeaderDtls the new runsheet header dtls
	 */
	public void setRunsheetHeaderDtls(PickupRunsheetHeaderDO runsheetHeaderDtls) {
		this.runsheetHeaderDtls = runsheetHeaderDtls;
	}

	/**
	 * Gets the pickup assignment dtls.
	 *
	 * @return the pickup assignment dtls
	 */
	public RunsheetAssignmentDetailDO getPickupAssignmentDtls() {
		return pickupAssignmentDtls;
	}

	/**
	 * Sets the pickup assignment dtls.
	 *
	 * @param pickupAssignmentDtls the new pickup assignment dtls
	 */
	public void setPickupAssignmentDtls(
			RunsheetAssignmentDetailDO pickupAssignmentDtls) {
		this.pickupAssignmentDtls = pickupAssignmentDtls;
	}

	/**
	 * Gets the pickup time.
	 *
	 * @return the pickup time
	 */
	public Date getPickupTime() {
		return pickupTime;
	}

	/**
	 * Sets the pickup time.
	 *
	 * @param pickupTime the new pickup time
	 */
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	/**
	 * Gets the start cn no.
	 *
	 * @return the start cn no
	 */
	public String getStartCnNo() {
		return startCnNo;
	}

	/**
	 * Sets the start cn no.
	 *
	 * @param startCnNo the new start cn no
	 */
	public void setStartCnNo(String startCnNo) {
		this.startCnNo = startCnNo;
	}

	/**
	 * Gets the end cn no.
	 *
	 * @return the end cn no
	 */
	public String getEndCnNo() {
		return endCnNo;
	}

	/**
	 * Sets the end cn no.
	 *
	 * @param endCnNo the new end cn no
	 */
	public void setEndCnNo(String endCnNo) {
		this.endCnNo = endCnNo;
	}

	/**
	 * Gets the cn count.
	 *
	 * @return the cn count
	 */
	public Integer getCnCount() {
		return cnCount;
	}

	/**
	 * Sets the cn count.
	 *
	 * @param cnCount the new cn count
	 */
	public void setCnCount(Integer cnCount) {
		this.cnCount = cnCount;
	}

	public String getDuplicateCustRow() {
		return duplicateCustRow;
	}

	public void setDuplicateCustRow(String duplicateCustRow) {
		this.duplicateCustRow = duplicateCustRow;
	}

}
