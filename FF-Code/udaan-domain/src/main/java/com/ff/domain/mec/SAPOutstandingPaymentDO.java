package com.ff.domain.mec;


import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class SAPOutstandingPaymentDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4689781926550217813L;
	private Long Id;
	private Date billUpto;
	private Date paymentUpto;
	private String empCode;
	private String outStandingFor;
	private String customerName;
	private String customerCode;
	private String ccemail;
	private String officeCode;
	//private String sapStatus = "N";
	//private Date sapTimestamp;
	private String reportName;
	private String exception;
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * @return the sapStatus
	 */
	/*public String getSapStatus() {
		return sapStatus;
	}
	*//**
	 * @param sapStatus the sapStatus to set
	 *//*
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}*/
	/**
	 * @return the sapTimestamp
	 */
	/*public Date getSapTimestamp() {
		return sapTimestamp;
	}
	*//**
	 * @param sapTimestamp the sapTimestamp to set
	 *//*
	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}*/
	
	
	
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
	 * @return the billUpto
	 */
	public Date getBillUpto() {
		return billUpto;
	}
	/**
	 * @param billUpto the billUpto to set
	 */
	public void setBillUpto(Date billUpto) {
		this.billUpto = billUpto;
	}
	/**
	 * @return the paymentUpto
	 */
	public Date getPaymentUpto() {
		return paymentUpto;
	}
	/**
	 * @param paymentUpto the paymentUpto to set
	 */
	public void setPaymentUpto(Date paymentUpto) {
		this.paymentUpto = paymentUpto;
	}
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	/**
	 * @return the outStandingFor
	 */
	public String getOutStandingFor() {
		return outStandingFor;
	}
	/**
	 * @param outStandingFor the outStandingFor to set
	 */
	public void setOutStandingFor(String outStandingFor) {
		this.outStandingFor = outStandingFor;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the ccemail
	 */
	public String getCcemail() {
		return ccemail;
	}
	/**
	 * @param ccemail the ccemail to set
	 */
	public void setCcemail(String ccemail) {
		this.ccemail = ccemail;
	}
	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	
}
