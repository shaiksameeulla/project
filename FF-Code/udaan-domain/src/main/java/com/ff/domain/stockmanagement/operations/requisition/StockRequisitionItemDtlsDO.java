/**
 * 
 */
package com.ff.domain.stockmanagement.operations.requisition;

import java.util.Date;

import com.ff.domain.stockmanagement.operations.StockCommonDetailsDO;

/**
 * The Class StockRequisitionItemDtlsDO.
 *
 * @author mohammes
 */
public class StockRequisitionItemDtlsDO extends StockCommonDetailsDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1114374023000209824L;

	/** primary key. */
	private Long stockRequisitionItemDtlsId;
	
	
	/** balanceIssueQuantity : updated at the time of issue (partial issue against requisition). */
	private Integer balanceIssueQuantity;
	
	/** balanceReceiptQuantity : updated at the time of issue (partial Receipt against requisition). */
	private Integer balanceReceiptQuantity;
	
	
	
	/** The procurement type.  For External /Internal*/
	private String procurementType;
	
	/** foreign key to parent many-to-one relationship. */
	private StockRequisitionDO requisitionDO;

	
	/**
	 * to stamp sap transaction date and time
	 * */
	private Date sapTimestamp;
	
	/** The is consolidated.  Y-means approved requistion line item consolidated ,N- not yet Consolidated*/
	private String isConsolidated="N";
	
	private String seriesStartsWith;// for  SAP(Acknowledgement at RHO), Series validation  and  Persistable
	
	/**
	 * Gets the stock requisition item dtls id.
	 *
	 * @return the stock requisition item dtls id
	 */
	public Long getStockRequisitionItemDtlsId() {
		return stockRequisitionItemDtlsId;
	}

	/**
	 * Sets the stock requisition item dtls id.
	 *
	 * @param stockRequisitionItemDtlsId the new stock requisition item dtls id
	 */
	public void setStockRequisitionItemDtlsId(Long stockRequisitionItemDtlsId) {
		this.stockRequisitionItemDtlsId = stockRequisitionItemDtlsId;
	}

	

	

	

	/**
	 * Gets the requisition do.
	 *
	 * @return the requisition do
	 */
	public StockRequisitionDO getRequisitionDO() {
		return requisitionDO;
	}

	/**
	 * Sets the requisition do.
	 *
	 * @param requisitionDO the new requisition do
	 */
	public void setRequisitionDO(StockRequisitionDO requisitionDO) {
		this.requisitionDO = requisitionDO;
	}

	

	/**
	 * Gets the balance issue quantity.
	 *
	 * @return the balanceIssueQuantity
	 */
	public Integer getBalanceIssueQuantity() {
		return balanceIssueQuantity;
	}

	/**
	 * Sets the balance issue quantity.
	 *
	 * @param balanceIssueQuantity the balanceIssueQuantity to set
	 */
	public void setBalanceIssueQuantity(Integer balanceIssueQuantity) {
		this.balanceIssueQuantity = balanceIssueQuantity;
	}

	/**
	 * Gets the balance receipt quantity.
	 *
	 * @return the balanceReceiptQuantity
	 */
	public Integer getBalanceReceiptQuantity() {
		return balanceReceiptQuantity;
	}

	/**
	 * Sets the balance receipt quantity.
	 *
	 * @param balanceReceiptQuantity the balanceReceiptQuantity to set
	 */
	public void setBalanceReceiptQuantity(Integer balanceReceiptQuantity) {
		this.balanceReceiptQuantity = balanceReceiptQuantity;
	}

	/**
	 * @return the sapTimestamp
	 */
	public Date getSapTimestamp() {
		return sapTimestamp;
	}

	/**
	 * @param sapTimestamp the sapTimestamp to set
	 */
	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}

	/**
	 * @return the procurementType
	 */
	public String getProcurementType() {
		return procurementType;
	}

	/**
	 * @param procurementType the procurementType to set
	 */
	public void setProcurementType(String procurementType) {
		this.procurementType = procurementType;
	}

	/**
	 * @return the isConsolidated
	 */
	public String getIsConsolidated() {
		return isConsolidated;
	}

	/**
	 * @param isConsolidated the isConsolidated to set
	 */
	public void setIsConsolidated(String isConsolidated) {
		this.isConsolidated = isConsolidated;
	}

	/**
	 * @return the seriesStartsWith
	 */
	public String getSeriesStartsWith() {
		return seriesStartsWith;
	}

	/**
	 * @param seriesStartsWith the seriesStartsWith to set
	 */
	public void setSeriesStartsWith(String seriesStartsWith) {
		this.seriesStartsWith = seriesStartsWith;
	}

	
	
}
