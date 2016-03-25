/**
 * 
 */
package com.ff.to.billing;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;

/**
 * @author abarudwa
 *
 */
public class InvoiceRunSheetDetailsTO extends CGBaseTO implements
Comparable<InvoiceRunSheetDetailsTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CustomerTO customerTO;
	private String billNumber;
	private String signature;
	private String shipToCode; 
	private BillTO invoiceBillTO;
	private String status;
	private String receiverName;
	private Integer invoiceRunSheetDetailId;
	private Integer invoiceRunSheetId;
	private Integer createdBy;
	private String createdDateStr;
	private Integer updatedBy;
	private String updatedDateStr;
	private String saveOrUpdate;
	/**
	 * @return the customerTO
	 */
	public CustomerTO getCustomerTO() {
		return customerTO;
	}
	/**
	 * @param customerTO the customerTO to set
	 */
	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
	}
	/**
	 * @return the billNumber
	 */
	public String getBillNumber() {
		return billNumber;
	}
	/**
	 * @param billNumber the billNumber to set
	 */
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
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
	 * @return the invoiceBillTO
	 */
	public BillTO getInvoiceBillTO() {
		return invoiceBillTO;
	}
	/**
	 * @param invoiceBillTO the invoiceBillTO to set
	 */
	public void setInvoiceBillTO(BillTO invoiceBillTO) {
		this.invoiceBillTO = invoiceBillTO;
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
	 * @return the invoiceRunSheetId
	 */
	public Integer getInvoiceRunSheetId() {
		return invoiceRunSheetId;
	}
	/**
	 * @param invoiceRunSheetId the invoiceRunSheetId to set
	 */
	public void setInvoiceRunSheetId(Integer invoiceRunSheetId) {
		this.invoiceRunSheetId = invoiceRunSheetId;
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
	 * @return the createdDateStr
	 */
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	/**
	 * @param createdDateStr the createdDateStr to set
	 */
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
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
	 * @return the updatedDateStr
	 */
	public String getUpdatedDateStr() {
		return updatedDateStr;
	}
	/**
	 * @param updatedDateStr the updatedDateStr to set
	 */
	public void setUpdatedDateStr(String updatedDateStr) {
		this.updatedDateStr = updatedDateStr;
	}
	/**
	 * @return the saveOrUpdate
	 */
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}
	/**
	 * @param saveOrUpdate the saveOrUpdate to set
	 */
	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	
	@Override
	public int compareTo(InvoiceRunSheetDetailsTO invoiceRunSheetDetailsTO) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(invoiceBillTO.getInvoiceNumber())) {
			returnVal = this.invoiceBillTO.getInvoiceNumber().compareTo(invoiceRunSheetDetailsTO.getInvoiceBillTO().getInvoiceNumber());
		}
		return returnVal;
	}

}
