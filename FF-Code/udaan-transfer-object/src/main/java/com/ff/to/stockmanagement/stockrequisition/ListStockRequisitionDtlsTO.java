/**
 * 
 */
package com.ff.to.stockmanagement.stockrequisition;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author mohammes
 * 
 *
 */
public class ListStockRequisitionDtlsTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String requisitionNumber;
	private String requisitionDate;
	private String approveRequisitionUrl;
	private Integer rowNumber;
	/**
	 * @return the requisitionNumber
	 */
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	/**
	 * @return the requisitionDate
	 */
	public String getRequisitionDate() {
		return requisitionDate;
	}
	/**
	 * @return the approveRequisitionUrl
	 */
	public String getApproveRequisitionUrl() {
		return approveRequisitionUrl;
	}
	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param requisitionNumber the requisitionNumber to set
	 */
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	/**
	 * @param requisitionDate the requisitionDate to set
	 */
	public void setRequisitionDate(String requisitionDate) {
		this.requisitionDate = requisitionDate;
	}
	/**
	 * @param approveRequisitionUrl the approveRequisitionUrl to set
	 */
	public void setApproveRequisitionUrl(String approveRequisitionUrl) {
		this.approveRequisitionUrl = approveRequisitionUrl;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}



}
