package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.RateCalculationCustomerDO;

public class RateCalculationQuotationDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201356147696366813L;
	

	private Integer rateQuotationId;
	private String rateQuotationNo;
	private String status;
	private RateCalculationCustomerDO customer;
	private String rateQuotationType;
	private Date quotationCreatedDate;
	private String quotationUsedFor;
	/**
	 * @return the rateQuotationId
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @param rateQuotationId the rateQuotationId to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
	}
	/**
	 * @return the rateQuotationNo
	 */
	public String getRateQuotationNo() {
		return rateQuotationNo;
	}
	/**
	 * @param rateQuotationNo the rateQuotationNo to set
	 */
	public void setRateQuotationNo(String rateQuotationNo) {
		this.rateQuotationNo = rateQuotationNo;
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
	 * @return the customer
	 */
	public RateCalculationCustomerDO getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(RateCalculationCustomerDO customer) {
		this.customer = customer;
	}
	/**
	 * @return the rateQuotationType
	 */
	public String getRateQuotationType() {
		return rateQuotationType;
	}
	/**
	 * @param rateQuotationType the rateQuotationType to set
	 */
	public void setRateQuotationType(String rateQuotationType) {
		this.rateQuotationType = rateQuotationType;
	}
	/**
	 * @return the quotationCreatedDate
	 */
	public Date getQuotationCreatedDate() {
		return quotationCreatedDate;
	}
	/**
	 * @param quotationCreatedDate the quotationCreatedDate to set
	 */
	public void setQuotationCreatedDate(Date quotationCreatedDate) {
		this.quotationCreatedDate = quotationCreatedDate;
	}
	/**
	 * @return the quotationUsedFor
	 */
	public String getQuotationUsedFor() {
		return quotationUsedFor;
	}
	/**
	 * @param quotationUsedFor the quotationUsedFor to set
	 */
	public void setQuotationUsedFor(String quotationUsedFor) {
		this.quotationUsedFor = quotationUsedFor;
	}
	
}
