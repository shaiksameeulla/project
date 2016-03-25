/**
 * 
 */
package com.ff.domain.billing;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;

/**
 * @author abarudwa
 *
 */
public class InvoiceRunSheetDetailDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer invoiceRunSheetDetailId;
	private InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO;//invoiceRunSheetId
	private CustomerDO customerDO;//customer
	private String shipToCode;
	private BillDO billDO;//invoiceId
	private String status;
	private String receiverName;
	private Integer createdBy;
	private Date createdDate;
	private Integer updatedBy;
	private Date updatedDate;
	/**
	 * @return the invoiceRunSheetDetailId
	 */
	public Integer getInvoiceRunSheetDetailId() {
		return invoiceRunSheetDetailId;
	}
	/**
	 * @param invoiceRunSheetDetailId the invoiceRunSheetDetailId to set
	 */
	public void setInvoiceRunSheetDetailId(Integer invoiceRunSheetDetailId) {
		this.invoiceRunSheetDetailId = invoiceRunSheetDetailId;
	}
	/**
	 * @return the invoiceRunSheetHeaderDO
	 */
	public InvoiceRunSheetHeaderDO getInvoiceRunSheetHeaderDO() {
		return invoiceRunSheetHeaderDO;
	}
	/**
	 * @param invoiceRunSheetHeaderDO the invoiceRunSheetHeaderDO to set
	 */
	public void setInvoiceRunSheetHeaderDO(
			InvoiceRunSheetHeaderDO invoiceRunSheetHeaderDO) {
		this.invoiceRunSheetHeaderDO = invoiceRunSheetHeaderDO;
	}
	/**
	 * @return the customerDO
	 */
	public CustomerDO getCustomerDO() {
		return customerDO;
	}
	/**
	 * @param customerDO the customerDO to set
	 */
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}
	/**
	 * @return the shipToCode
	 */
	public String getShipToCode() {
		return shipToCode;
	}
	/**
	 * @param shipToCode the shipToCode to set
	 */
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
	/**
	 * @return the billDO
	 */
	public BillDO getBillDO() {
		return billDO;
	}
	/**
	 * @param billDO the billDO to set
	 */
	public void setBillDO(BillDO billDO) {
		this.billDO = billDO;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
	
}
