/**
 * 
 */
package com.ff.domain.stockmanagement.operations.cancel;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author cbhure
 *
 */
public class SAPStockCancellationDO extends CGFactDO {
	
	private static final long serialVersionUID = -7595485246101532306L;
	
	private Long Id;
	private Date documentDate;
	private String reason;
	private String itemCode;
	private Integer quantity;
	private String cancellationNumber;
	private String cancellationOfcCode;
	private Date cancelledDate;
	private String exception;
	
	
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
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
	 * @return the documentDate
	 */
	public Date getDocumentDate() {
		return documentDate;
	}
	/**
	 * @param documentDate the documentDate to set
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
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
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the cancellationNumber
	 */
	public String getCancellationNumber() {
		return cancellationNumber;
	}
	/**
	 * @param cancellationNumber the cancellationNumber to set
	 */
	public void setCancellationNumber(String cancellationNumber) {
		this.cancellationNumber = cancellationNumber;
	}
	/**
	 * @return the cancellationOfcCode
	 */
	public String getCancellationOfcCode() {
		return cancellationOfcCode;
	}
	/**
	 * @param cancellationOfcCode the cancellationOfcCode to set
	 */
	public void setCancellationOfcCode(String cancellationOfcCode) {
		this.cancellationOfcCode = cancellationOfcCode;
	}
	/**
	 * @return the cancelledDate
	 */
	public Date getCancelledDate() {
		return cancelledDate;
	}
	/**
	 * @param cancelledDate the cancelledDate to set
	 */
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	
	
	
}
