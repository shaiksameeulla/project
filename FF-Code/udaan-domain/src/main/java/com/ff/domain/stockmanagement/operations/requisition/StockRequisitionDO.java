/**
 * 
 */
package com.ff.domain.stockmanagement.operations.requisition;

import java.util.Date;
import java.util.Set;

import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.StockCommonBaseDO;
import com.ff.domain.umc.UserDO;

/**
 * The Class StockRequisitionDO.
 *
 * @author mohammes
 */
public class StockRequisitionDO extends StockCommonBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 13543523434L;
	
	/** primary key for stock requisition table. */
	private Long stockRequisitionId;
	
	/** unique stock requisition number EX: PR+OfficeCODE(4)+RunningSerialNumber(5). */
	private String requisitionNumber;
	
	/** Date on which requisition created. */
	private Date reqCreatedDate;
	
	/** Date on which requisition created. */
	private Date approvedDate;
	
	/** Office at which actual issue will be done (Many-to-one relation ship in hbm). */
	private OfficeDO supplyingOfficeDO;
	
	/** Office at which actual requisition is created (Many-to-one relation ship in hbm). */
	private OfficeDO requisitionOfficeDO;

	/** Fk for approved User (Many-to-one relation ship in hbm). */
	private UserDO approvedByUserDO;
	
	/** Fk for created User (Many-to-one relation ship in hbm). */
	private UserDO createdByUserDO;
	
	/** Fk for updated User(created user by default) (Many-to-one relation ship in hbm). */
	private UserDO updatedByUserDO;
	
	/** line items(details in Grid from the screen) (One-to-Many relation ship in hbm). */
	Set<StockRequisitionItemDtlsDO> requisionItemDtls;
	
	
	/** The is pr consolidated.  This Flag only useful for the Requisition consolidation
	 * If this Flag is Y then generated requisition number is Consolidated at RHO
	 *  */
	private String isPrConsolidated="N";
	
	private Integer approvedByUserId;
	
	private String isAutoRequisition="N";

	/**
	 * Gets the stock requisition id.
	 *
	 * @return the stock requisition id
	 */
	public Long getStockRequisitionId() {
		return stockRequisitionId;
	}

	/**
	 * Sets the stock requisition id.
	 *
	 * @param stockRequisitionId the new stock requisition id
	 */
	public void setStockRequisitionId(Long stockRequisitionId) {
		this.stockRequisitionId = stockRequisitionId;
	}

	/**
	 * Gets the requisition number.
	 *
	 * @return the requisition number
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}

	/**
	 * Sets the requisition number.
	 *
	 * @param requisitionNumber the new requisition number
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}

	/**
	 * Gets the req created date.
	 *
	 * @return the req created date
	 */
	public Date getReqCreatedDate() {
		return reqCreatedDate;
	}

	/**
	 * Sets the req created date.
	 *
	 * @param reqCreatedDate the new req created date
	 */
	public void setReqCreatedDate(Date reqCreatedDate) {
		this.reqCreatedDate = reqCreatedDate;
	}

	

	

	/**
	 * Gets the created by user do.
	 *
	 * @return the created by user do
	 */
	public UserDO getCreatedByUserDO() {
		return createdByUserDO;
	}

	/**
	 * Sets the created by user do.
	 *
	 * @param createdByUserDO the new created by user do
	 */
	public void setCreatedByUserDO(UserDO createdByUserDO) {
		this.createdByUserDO = createdByUserDO;
	}

	/**
	 * Gets the updated by user do.
	 *
	 * @return the updated by user do
	 */
	public UserDO getUpdatedByUserDO() {
		return updatedByUserDO;
	}

	/**
	 * Sets the updated by user do.
	 *
	 * @param updatedByUserDO the new updated by user do
	 */
	public void setUpdatedByUserDO(UserDO updatedByUserDO) {
		this.updatedByUserDO = updatedByUserDO;
	}

	/**
	 * Gets the requision item dtls.
	 *
	 * @return the requision item dtls
	 */
	public Set<StockRequisitionItemDtlsDO> getRequisionItemDtls() {
		return requisionItemDtls;
	}

	/**
	 * Sets the requision item dtls.
	 *
	 * @param requisionItemDtls the new requision item dtls
	 */
	public void setRequisionItemDtls(
			Set<StockRequisitionItemDtlsDO> requisionItemDtls) {
		this.requisionItemDtls = requisionItemDtls;
	}

	/**
	 * Gets the supplying office do.
	 *
	 * @return the supplyingOfficeDO
	 */
	public OfficeDO getSupplyingOfficeDO() {
		return supplyingOfficeDO;
	}

	/**
	 * Sets the supplying office do.
	 *
	 * @param supplyingOfficeDO the supplyingOfficeDO to set
	 */
	public void setSupplyingOfficeDO(OfficeDO supplyingOfficeDO) {
		this.supplyingOfficeDO = supplyingOfficeDO;
	}

	/**
	 * Gets the requisition office do.
	 *
	 * @return the requisitionOfficeDO
	 */
	public OfficeDO getRequisitionOfficeDO() {
		return requisitionOfficeDO;
	}

	/**
	 * Sets the requisition office do.
	 *
	 * @param requisitionOfficeDO the requisitionOfficeDO to set
	 */
	public void setRequisitionOfficeDO(OfficeDO requisitionOfficeDO) {
		this.requisitionOfficeDO = requisitionOfficeDO;
	}

	/**
	 * Gets the approved by user do.
	 *
	 * @return the approvedByUserDO
	 */
	public UserDO getApprovedByUserDO() {
		return approvedByUserDO;
	}

	/**
	 * Sets the approved by user do.
	 *
	 * @param approvedByUserDO the approvedByUserDO to set
	 */
	public void setApprovedByUserDO(UserDO approvedByUserDO) {
		this.approvedByUserDO = approvedByUserDO;
	}

	/**
	 * Gets the approved date.
	 *
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * Sets the approved date.
	 *
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the isPrConsolidated
	 */
	public String getIsPrConsolidated() {
		return isPrConsolidated;
	}

	/**
	 * @param isPrConsolidated the isPrConsolidated to set
	 */
	public void setIsPrConsolidated(String isPrConsolidated) {
		this.isPrConsolidated = isPrConsolidated;
	}

	/**
	 * @return the approvedByUserId
	 */
	public Integer getApprovedByUserId() {
		return approvedByUserId;
	}

	/**
	 * @param approvedByUserId the approvedByUserId to set
	 */
	public void setApprovedByUserId(Integer approvedByUserId) {
		this.approvedByUserId = approvedByUserId;
	}

	public String getIsAutoRequisition() {
		return isAutoRequisition;
	}

	public void setIsAutoRequisition(String isAutoRequisition) {
		this.isAutoRequisition = isAutoRequisition;
	}
	
}
