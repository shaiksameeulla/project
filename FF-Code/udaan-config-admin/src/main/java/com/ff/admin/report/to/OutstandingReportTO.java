package com.ff.admin.report.to;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author khassan
 *
 */
public class OutstandingReportTO extends CGBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7691286504665338860L;
	/**
	 * 
	 */
	String billUpto;
	String paymentUpto;
	String outStandingFor;
	String customerName;
	String customerCode;
	String ccemail;
	String reportFor;
	
	public String getBillUpto() {
		return billUpto;
	}
	public void setBillUpto(String billUpto) {
		this.billUpto = billUpto;
	}
	public String getReportFor() {
		return reportFor;
	}
	public void setReportFor(String reportFor) {
		this.reportFor = reportFor;
	}
	public String getPaymentUpto() {
		return paymentUpto;
	}
	public void setPaymentUpto(String paymentUpto) {
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
	
	
	

}
