/**
 * 
 */
package com.ff.domain.billing;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author cbhure
 *
 */
@SuppressWarnings("serial")
public class SAPBillSalesOrderStagingDO extends CGFactDO implements Serializable{
	
	private Long id;
	private String salesOrderNumber;
	private String billNumber;
	private String billStatus;
	private String sapInbound = "N";
	private Date sapTimeStamp = Calendar.getInstance().getTime();
	private Integer summaryId;
	private Double grandTotal;
	private Date billCreationDate;
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
	 * @return the sapInbound
	 */
	public String getSapInbound() {
		return sapInbound;
	}
	/**
	 * @param sapInbound the sapInbound to set
	 */
	public void setSapInbound(String sapInbound) {
		this.sapInbound = sapInbound;
	}
	/**
	 * @return the sapTimeStamp
	 */
	public Date getSapTimeStamp() {
		return sapTimeStamp;
	}
	/**
	 * @param sapTimeStamp the sapTimeStamp to set
	 */
	public void setSapTimeStamp(Date sapTimeStamp) {
		this.sapTimeStamp = sapTimeStamp;
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
	
	
	
}
