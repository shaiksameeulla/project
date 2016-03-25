/**
 * 
 */
package com.ff.domain.billing;

import java.io.Serializable;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author cbhure
 *
 */
@SuppressWarnings("serial")
public class SAPBillSalesOrderDO extends CGFactDO implements Serializable{
	
	private Long id;
	private String salesOrderNo;
	private String billNo;
	private String billStatus;
	private String sapInbound;
	private Date sapTimeStamp;
	private Integer summaryId;
	private Double grandTotal;
	private String status;
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
	 * @return the salesOrderNo
	 */
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	/**
	 * @param salesOrderNo the salesOrderNo to set
	 */
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
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
	
}
