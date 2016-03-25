/**
 * 
 */
package com.ff.domain.stockmanagement.operations.requisition;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */
public class SAPStockRequisitionDO extends CGFactDO {

	private static final long serialVersionUID = -1264257484379392692L;

	private Long Id;
	private Integer stockRequisitionItemDtlsId;
	private String requisitionNumber;
	private String officeCode;
	private Integer rowNumber;
	private Integer approvedQty;
	private String description;
	private String itemCode;
	private String itemTypeCode;
	private Date txCreatedDate;
	private String uom;
	private String procurementType;
	private String seriesStartsWith;
	private String prConsolidated;
	private String exception;
	
	
	/**
	 * @return the stockRequisitionItemDtlsId
	 */
	public Integer getStockRequisitionItemDtlsId() {
		return stockRequisitionItemDtlsId;
	}
	/**
	 * @param stockRequisitionItemDtlsId the stockRequisitionItemDtlsId to set
	 */
	public void setStockRequisitionItemDtlsId(Integer stockRequisitionItemDtlsId) {
		this.stockRequisitionItemDtlsId = stockRequisitionItemDtlsId;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	/**
	 * @return the requisitionNumber
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	/**
	 * @param requisitionNumber the requisitionNumber to set
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the approvedQty
	 */
	public Integer getApprovedQty() {
		return approvedQty;
	}
	/**
	 * @param approvedQty the approvedQty to set
	 */
	public void setApprovedQty(Integer approvedQty) {
		this.approvedQty = approvedQty;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the itemTypeCode
	 */
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	/**
	 * @param itemTypeCode the itemTypeCode to set
	 */
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	/**
	 * @return the txCreatedDate
	 */
	public Date getTxCreatedDate() {
		return txCreatedDate;
	}
	/**
	 * @param txCreatedDate the txCreatedDate to set
	 */
	public void setTxCreatedDate(Date txCreatedDate) {
		this.txCreatedDate = txCreatedDate;
	}
	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}
	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
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
	/**
	 * @return the prConsolidated
	 */
	public String getPrConsolidated() {
		return prConsolidated;
	}
	/**
	 * @param prConsolidated the prConsolidated to set
	 */
	public void setPrConsolidated(String prConsolidated) {
		this.prConsolidated = prConsolidated;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	
}
