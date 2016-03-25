package com.ff.domain.reportdo;

import java.sql.Date;

public class OutstandingReportDO {
	
	private Long outstandingReportId;
	
	private Date billUpto;
	private Date paymentUpto;
	private Date sapTimeStamp;

	private String empCode;
	private String outStandingFor;
	private String customerName;
	private String customerCode;
	private String ccemail;
	private String dtSapOutBound = "N";
	private String officeCode;
	
	
	
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	public String getDtSapOutBound() {
		return dtSapOutBound;
	}
	public void setDtSapOutBound(String dtSapOutBound) {
		this.dtSapOutBound = dtSapOutBound;
	}
	public Long getOutstandingReportId() {
		return outstandingReportId;
	}
	public void setOutstandingReportId(Long outstandingReportId) {
		this.outstandingReportId = outstandingReportId;
	}
	public Date getBillUpto() {
		return billUpto;
	}
	public void setBillUpto(Date billUpto) {
		this.billUpto = billUpto;
	}
	public Date getPaymentUpto() {
		return paymentUpto;
	}
	public void setPaymentUpto(Date paymentUpto) {
		this.paymentUpto = paymentUpto;
	}
	public String getOutStandingFor() {
		return outStandingFor;
	}
	public void setOutStandingFor(String outStandingFor) {
		this.outStandingFor = outStandingFor;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCcemail() {
		return ccemail;
	}
	public void setCcemail(String ccemail) {
		this.ccemail = ccemail;
	}
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Date getSapTimeStamp() {
		return sapTimeStamp;
	}
	public void setSapTimeStamp(Date sapTimeStamp) {
		this.sapTimeStamp = sapTimeStamp;
	}
	
}
