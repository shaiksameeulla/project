/**
 * 
 */
package com.ff.to.stockmanagement.stockrequisition;

import java.util.List;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author mohammes
 * 
 *
 */
public class StockRequisitionTO extends StockHeaderTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long stockRequisitionId;
	private String reqCreatedDateStr;
	private String reqCreatedTimeStr;
	private Integer loggedInOfficeId;
	private String loggedInOfficeName;

	private Integer requisitionOfficeId;
	private String requisitionOfficeName;
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	
	private String  itemTypeReq;
	
	private Integer loggedInRho;
	
	
	private Long rowStockReqItemDtlsId[]= new Long[rowCount];
	
	private String rowStockProcurementType[]= new String[rowCount];
	
	
	private String  status;
	
	private String isApproved;
	
	List<StockRequisitionTO> reqTO;//Only for SAP Integration,  Not for Application
	
 	List<StockRequisitionItemDtlsTO> reqItemDetls ;
 	
	/**
	 * @return the stockRequisitionId
	 */
	public Long getStockRequisitionId() {
		return stockRequisitionId;
	}
	/**
	 * @param stockRequisitionId the stockRequisitionId to set
	 */
	public void setStockRequisitionId(Long stockRequisitionId) {
		this.stockRequisitionId = stockRequisitionId;
	}
	
	/**
	 * @return the reqCreatedDateStr
	 */
	public String getReqCreatedDateStr() {
		return reqCreatedDateStr;
	}
	/**
	 * @param reqCreatedDateStr the reqCreatedDateStr to set
	 */
	public void setReqCreatedDateStr(String reqCreatedDateStr) {
		this.reqCreatedDateStr = reqCreatedDateStr;
	}
	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	/**
	 * @return the requisitionOfficeId
	 */
	public Integer getRequisitionOfficeId() {
		return requisitionOfficeId;
	}
	/**
	 * @param requisitionOfficeId the requisitionOfficeId to set
	 */
	public void setRequisitionOfficeId(Integer requisitionOfficeId) {
		this.requisitionOfficeId = requisitionOfficeId;
	}
	/**
	 * @return the loggedInUserId
	 */
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}
	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	/**
	 * @return the createdByUserId
	 */
	public Integer getCreatedByUserId() {
		return createdByUserId;
	}
	/**
	 * @param createdByUserId the createdByUserId to set
	 */
	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	/**
	 * @return the updatedByUserId
	 */
	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}
	/**
	 * @param updatedByUserId the updatedByUserId to set
	 */
	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	public String getReqCreatedTimeStr() {
		return reqCreatedTimeStr;
	}
	public void setReqCreatedTimeStr(String reqCreatedTimeStr) {
		this.reqCreatedTimeStr = reqCreatedTimeStr;
	}
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}
	
	public String getItemTypeReq() {
		return itemTypeReq;
	}
	public void setItemTypeReq(String itemTypeReq) {
		this.itemTypeReq = itemTypeReq;
	}
	
	public Long[] getRowStockReqItemDtlsId() {
		return rowStockReqItemDtlsId;
	}
	public void setRowStockReqItemDtlsId(Long[] rowStockReqItemDtlsId) {
		this.rowStockReqItemDtlsId = rowStockReqItemDtlsId;
	}
	public Integer getLoggedInRho() {
		return loggedInRho;
	}
	public void setLoggedInRho(Integer loggedInRho) {
		this.loggedInRho = loggedInRho;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StockRequisitionItemDtlsTO> getReqItemDetls() {
		return reqItemDetls;
	}
	public void setReqItemDetls(List<StockRequisitionItemDtlsTO> reqItemDetls) {
		this.reqItemDetls = reqItemDetls;
	}
	/**
	 * @return the requisitionOfficeName
	 */
	public String getRequisitionOfficeName() {
		return requisitionOfficeName;
	}
	/**
	 * @param requisitionOfficeName the requisitionOfficeName to set
	 */
	public void setRequisitionOfficeName(String requisitionOfficeName) {
		this.requisitionOfficeName = requisitionOfficeName;
	}
	/*public List<StockRequisitionTO> getReqTO() {
		return reqTO;
	}
	public void setReqTO(List<StockRequisitionTO> reqTO) {
		this.reqTO = reqTO;
	}*/
	/**
	 * @return the isApproved
	 */
	public String getIsApproved() {
		return isApproved;
	}
	/**
	 * @param isApproved the isApproved to set
	 */
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	/**
	 * @return the rowStockProcurementType
	 */
	public String[] getRowStockProcurementType() {
		return rowStockProcurementType;
	}
	/**
	 * @param rowStockProcurementType the rowStockProcurementType to set
	 */
	public void setRowStockProcurementType(String[] rowStockProcurementType) {
		this.rowStockProcurementType = rowStockProcurementType;
	}
	
}
