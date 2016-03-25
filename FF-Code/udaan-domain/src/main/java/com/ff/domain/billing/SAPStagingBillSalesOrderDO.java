package com.ff.domain.billing;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class SAPStagingBillSalesOrderDO extends CGFactDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5394680564484073080L;
	
	private Integer id;
	private Integer summaryId;
	private String billNo;
	private String salesOrderNo;
	private String billStatus;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSummaryId() {
		return summaryId;
	}
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	
}
