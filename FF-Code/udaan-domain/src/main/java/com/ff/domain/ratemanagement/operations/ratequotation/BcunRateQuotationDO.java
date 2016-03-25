package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.BcunCustomerDO;


public class BcunRateQuotationDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201356147696366813L;
	

	private Integer rateQuotationId;
	private String RateQuotationNo;
	private String status;
	private BcunCustomerDO customer;
	private String rateQuotationType;
	private String rateQuotationOriginatedfromType;
	private Integer ratequotationOriginatedFrom;
	private Set<BcunRateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDO;
	private String approversRemarks;
	private String excecutiveRemarks;
	private Date quotationCreatedDate;
	private Set<BcunRateQuotationFixedChargesDO> rateFixedChargeDO;
	private Set<BcunRateQuotationCODChargeDO> codChargeDO;
	private BcunRateQuotationRTOChargesDO rateQuotationRTOChargesDO;
	private Set<BcunRateQuotationFixedChargesConfigDO> rateQuotationFixedChargesConfigDO;
	private Integer quotationCreatedBy;
	private String approvalRequired;
	private String approvedAt;
	private String quotationUsedFor;
	
	private Integer rateContractId;
	private String quotationCreatedFrom;
	
	
	
	/**
	 * @return the rateContractId
	 */
	public Integer getRateContractId() {
		return rateContractId;
	}
	/**
	 * @param rateContractId the rateContractId to set
	 */
	public void setRateContractId(Integer rateContractId) {
		this.rateContractId = rateContractId;
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
	 * @return the rateQuotationId
	 */
	public Integer getRateQuotationId() {
		return rateQuotationId;
	}
	/**
	 * @return the rateQuotationProductCategoryHeaderDO
	 */
	public Set<BcunRateQuotationProductCategoryHeaderDO> getRateQuotationProductCategoryHeaderDO() {
		return rateQuotationProductCategoryHeaderDO;
	}
	/**
	 * @param rateQuotationProductCategoryHeaderDO the rateQuotationProductCategoryHeaderDO to set
	 */
	public void setRateQuotationProductCategoryHeaderDO(
			Set<BcunRateQuotationProductCategoryHeaderDO> rateQuotationProductCategoryHeaderDO) {
		this.rateQuotationProductCategoryHeaderDO = rateQuotationProductCategoryHeaderDO;
	}
	/**
	 * @param rateQuotationId the rateQuotationId to set
	 */
	public void setRateQuotationId(Integer rateQuotationId) {
		this.rateQuotationId = rateQuotationId;
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
	public BcunCustomerDO getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(BcunCustomerDO customer) {
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
	
	public String getRateQuotationNo() {
		return RateQuotationNo;
	}
	public void setRateQuotationNo(String rateQuotationNo) {
		RateQuotationNo = rateQuotationNo;
	}
	/**
	 * @return the ratequotationOriginatedFrom
	 */
	public Integer getRatequotationOriginatedFrom() {
		return ratequotationOriginatedFrom;
	}
	/**
	 * @param ratequotationOriginatedFrom the ratequotationOriginatedFrom to set
	 */
	public void setRatequotationOriginatedFrom(Integer ratequotationOriginatedFrom) {
		this.ratequotationOriginatedFrom = ratequotationOriginatedFrom;
	}
	/**
	 * @return the approversRemarks
	 */
	public String getApproversRemarks() {
		return approversRemarks;
	}
	/**
	 * @param approversRemarks the approversRemarks to set
	 */
	public void setApproversRemarks(String approversRemarks) {
		this.approversRemarks = approversRemarks;
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
	 * @return the rateFixedChargeDO
	 */
	public Set<BcunRateQuotationFixedChargesDO> getRateFixedChargeDO() {
		return rateFixedChargeDO;
	}
	/**
	 * @param rateFixedChargeDO the rateFixedChargeDO to set
	 */
	public void setRateFixedChargeDO(
			Set<BcunRateQuotationFixedChargesDO> rateFixedChargeDO) {
		this.rateFixedChargeDO = rateFixedChargeDO;
	}
	/**
	 * @return the codChargeDO
	 */
	public Set<BcunRateQuotationCODChargeDO> getCodChargeDO() {
		return codChargeDO;
	}
	/**
	 * @param codChargeDO the codChargeDO to set
	 */
	public void setCodChargeDO(Set<BcunRateQuotationCODChargeDO> codChargeDO) {
		this.codChargeDO = codChargeDO;
	}
	/**
	 * @return the quotationCreatedBy
	 */
	public Integer getQuotationCreatedBy() {
		return quotationCreatedBy;
	}
	/**
	 * @param quotationCreatedBy the quotationCreatedBy to set
	 */
	public void setQuotationCreatedBy(Integer quotationCreatedBy) {
		this.quotationCreatedBy = quotationCreatedBy;
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
	 * @return the rateQuotationRTOChargesDO
	 */
	public BcunRateQuotationRTOChargesDO getRateQuotationRTOChargesDO() {
		return rateQuotationRTOChargesDO;
	}
	/**
	 * @param rateQuotationRTOChargesDO the rateQuotationRTOChargesDO to set
	 */
	public void setRateQuotationRTOChargesDO(
			BcunRateQuotationRTOChargesDO rateQuotationRTOChargesDO) {
		this.rateQuotationRTOChargesDO = rateQuotationRTOChargesDO;
	}
	/**
	 * @return the rateQuotationFixedChargesConfigDO
	 */
	public Set<BcunRateQuotationFixedChargesConfigDO> getRateQuotationFixedChargesConfigDO() {
		return rateQuotationFixedChargesConfigDO;
	}
	/**
	 * @param rateQuotationFixedChargesConfigDO the rateQuotationFixedChargesConfigDO to set
	 */
	public void setRateQuotationFixedChargesConfigDO(
			Set<BcunRateQuotationFixedChargesConfigDO> rateQuotationFixedChargesConfigDO) {
		this.rateQuotationFixedChargesConfigDO = rateQuotationFixedChargesConfigDO;
	}
	public String getQuotationCreatedFrom() {
		return quotationCreatedFrom;
	}
	public void setQuotationCreatedFrom(String quotationCreatedFrom) {
		this.quotationCreatedFrom = quotationCreatedFrom;
	}

}
