package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;

public class RateQuotationDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201356147696366813L;
	

	private Integer rateQuotationId;
	private String rateQuotationNo;
	private String status;
	private CustomerDO customer;
	private String rateQuotationType;
	private String rateQuotationOriginatedfromType;
	private RateQuotationDO ratequotationOriginatedFrom;
	private Set<RateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDO;
	private String approversRemarks;
	private String excecutiveRemarks;
	private Date quotationCreatedDate;
	private Set<RateQuotationFixedChargesDO> rateFixedChargeDO;
	private Set<RateQuotationCODChargeDO> codChargeDO;
	private RateQuotationRTOChargesDO rateQuotationRtoChargesDO;
	private Set<RateQuotationFixedChargesConfigDO> fixedChargesConfigDO;
	private Integer quotationCreatedBy;
	private String approvalRequired;
	private String approvedAt;
	private String quotationUsedFor;
	private RateQuotationCustomerDO quotedCustomer;
	private String quotationCreatedFrom;  
	
	
	
	public RateQuotationCustomerDO getQuotedCustomer() {
		return quotedCustomer;
	}
	public void setQuotedCustomer(RateQuotationCustomerDO quotedCustomer) {
		this.quotedCustomer = quotedCustomer;
	}
	public Set<RateQuotationFixedChargesConfigDO> getFixedChargesConfigDO() {
		return fixedChargesConfigDO;
	}
	public void setFixedChargesConfigDO(
			Set<RateQuotationFixedChargesConfigDO> fixedChargesConfigDO) {
		this.fixedChargesConfigDO = fixedChargesConfigDO;
	}
	
	
	/**
	 * @return the approvalRequired
	 */
	public String getApprovalRequired() {
		return approvalRequired;
	}
	/**
	 * @param approvalRequired the approvalRequired to set
	 */
	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}
	/**
	 * @return the approvedAt
	 */
	public String getApprovedAt() {
		return approvedAt;
	}
	/**
	 * @param approvedAt the approvedAt to set
	 */
	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	/**
	 * @return the excecutiveRemarks
	 */
	public String getExcecutiveRemarks() {
		return excecutiveRemarks;
	}
	/**
	 * @param excecutiveRemarks the excecutiveRemarks to set
	 */
	public void setExcecutiveRemarks(String excecutiveRemarks) {
		this.excecutiveRemarks = excecutiveRemarks;
	}
	
	/**
	 * @return the rateQuotationProductCategoryHeaderDO
	 */
	public Set<RateQuotationProductCategoryHeaderDO> getRateQuotationProductCategoryHeaderDO() {
		return rateQuotationProductCategoryHeaderDO;
	}
	/**
	 * @param rateQuotationProductCategoryHeaderDO the rateQuotationProductCategoryHeaderDO to set
	 */
	public void setRateQuotationProductCategoryHeaderDO(
			Set<RateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDO) {
		this.rateQuotationProductCategoryHeaderDO = rateQuotationProductCategoryHeaderDO;
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
	public CustomerDO getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerDO customer) {
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
	 * @return the rateQuotationOriginatedfromType
	 */
	public String getRateQuotationOriginatedfromType() {
		return rateQuotationOriginatedfromType;
	}
	/**
	 * @param rateQuotationOriginatedfromType the rateQuotationOriginatedfromType to set
	 */
	public void setRateQuotationOriginatedfromType(
			String rateQuotationOriginatedfromType) {
		this.rateQuotationOriginatedfromType = rateQuotationOriginatedfromType;
	}
	/**
	 * @return the ratequotationOriginatedFrom
	 */
	public RateQuotationDO getRatequotationOriginatedFrom() {
		return ratequotationOriginatedFrom;
	}
	/**
	 * @param ratequotationOriginatedFrom the ratequotationOriginatedFrom to set
	 */
	public void setRatequotationOriginatedFrom(
			RateQuotationDO ratequotationOriginatedFrom) {
		this.ratequotationOriginatedFrom = ratequotationOriginatedFrom;
	}

	public String getRateQuotationNo() {
		return rateQuotationNo;
	}
	
	/**
	 * @param rateQuotationNo the rateQuotationNo to set
	 */
	public void setRateQuotationNo(String rateQuotationNo) {
		this.rateQuotationNo = rateQuotationNo;
	}
	public Set<RateQuotationFixedChargesDO> getRateFixedChargeDO() {
		return rateFixedChargeDO;
	}
	public void setRateFixedChargeDO(Set<RateQuotationFixedChargesDO> rateFixedChargeDO) {
		this.rateFixedChargeDO = rateFixedChargeDO;
	}
	
	
	public String getApproversRemarks() {
		return approversRemarks;
	}
	public void setApproversRemarks(String approversRemarks) {
		this.approversRemarks = approversRemarks;
	}
	public Date getQuotationCreatedDate() {
		return quotationCreatedDate;
	}
	public void setQuotationCreatedDate(Date date) {
		this.quotationCreatedDate = date;
	}
	public Integer getQuotationCreatedBy() {
		return quotationCreatedBy;
	}
	public void setQuotationCreatedBy(Integer quotationCreatedBy) {
		this.quotationCreatedBy = quotationCreatedBy;
	}
	/**
	 * @return the codChargeDO
	 */
	public Set<RateQuotationCODChargeDO> getCodChargeDO() {
		return codChargeDO;
	}
	/**
	 * @param codChargeDO the codChargeDO to set
	 */
	public void setCodChargeDO(Set<RateQuotationCODChargeDO> codChargeDO) {
		this.codChargeDO = codChargeDO;
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
	 * @return the rateQuotationRtoChargesDO
	 */
	public RateQuotationRTOChargesDO getRateQuotationRtoChargesDO() {
		return rateQuotationRtoChargesDO;
	}
	/**
	 * @param rateQuotationRtoChargesDO the rateQuotationRtoChargesDO to set
	 */
	public void setRateQuotationRtoChargesDO(
			RateQuotationRTOChargesDO rateQuotationRtoChargesDO) {
		this.rateQuotationRtoChargesDO = rateQuotationRtoChargesDO;
	}
	public String getQuotationCreatedFrom() {
		return quotationCreatedFrom;
	}
	public void setQuotationCreatedFrom(String quotationCreatedFrom) {
		this.quotationCreatedFrom = quotationCreatedFrom;
	}
	
	


}
