package com.ff.domain.coloading;

import java.io.Serializable;
import java.util.Date;

public class CSDSAPColoaderInvoiceDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer transactionNo;
	private String invoiceNo;
	private String sapStatus;
	private Date sapTimestamp;
	
	
	
	
	public Date getSapTimestamp() {
		return sapTimestamp;
	}
	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}
	public String getSapStatus() {
		return sapStatus;
	}
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(Integer transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
}
