/**
 * 
 */
package com.ff.sap.integration.to;

import java.util.Date;


/**
 * @author cbhure
 *
 */
public class SAPColoaderTO {
	private Integer id;
	private Long transactionId;
	private String invoiceNo;
	private String sapStatus;
	private String maxCheck;
	private Date sapTimestamp;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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
	 * @return the sapStatus
	 */
	public String getSapStatus() {
		return sapStatus;
	}
	/**
	 * @param sapStatus the sapStatus to set
	 */
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	/**
	 * @return the maxCheck
	 */
	public String getMaxCheck() {
		return maxCheck;
	}
	/**
	 * @param maxCheck the maxCheck to set
	 */
	public void setMaxCheck(String maxCheck) {
		this.maxCheck = maxCheck;
	}
	
	
	
}
