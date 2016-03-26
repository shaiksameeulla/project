/**
 * 
 */
package com.ff.sap.integration.to;

import java.util.Date;

/**
 * @author cbhure
 *
 */
public class SAPSalesOrderTO {
	
	//summary ID as Transaction Number for Interface
	private Long id;
	private Integer summaryId;
	private String salesOrderNumber;
	private String billNumber;
	private String billStatus;
	private Double	grandTotal;
	private Date billCreationDate;
	
	/**
	 * @return the billCreationDate
	 */
	public Date getBillCreationDate() {
		return billCreationDate;
	}
	/**
	 * @param billCreationDate the billCreationDate to set
	 */
	public void setBillCreationDate(Date billCreationDate) {
		this.billCreationDate = billCreationDate;
	}
	/**
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}
	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	/**
	 * @return the salesOrderNumber
	 */
	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}
	/**
	 * @param salesOrderNumber the salesOrderNumber to set
	 */
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
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
	 * @return the billStatus
	 */
	public String getBillStatus() {
		return billStatus;
	}
	/**
	 * @param billStatus the billStatus to set
	 */
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the grandTotal
	 */
	public Double getGrandTotal() {
		return grandTotal;
	}
	/**
	 * @param grandTotal the grandTotal to set
	 */
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
}
