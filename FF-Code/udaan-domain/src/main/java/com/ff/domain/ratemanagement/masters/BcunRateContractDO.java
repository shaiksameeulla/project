package com.ff.domain.ratemanagement.masters;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.operations.ratequotation.BcunRateQuotationDO;

/**
 * The Class RateContractDO.
 *
 * @author narmdr
 */
public class BcunRateContractDO extends CGFactDO {
	
	private static final long serialVersionUID = -4823091798225417894L;
	
	private Integer rateContractId;
	private String rateContractNo;
	private Date validFromDate;
	private Date validToDate;
	private String billingContractType;////N - Normal, R - Reverse Logistics
	private String typeOfBilling;//'DBDP','CBCP','DBCP'
	private String modeOfBilling;//H - Hard Copy, S - Soft Copy
	private String billingCycle;//M - Monthly, F - Fortnightly
	private String paymentTerm;//'P001','P002','P003','P004','P005','P006'
	private String octraiBourneBy;//CO - Consigner, CE - Consignee 
	private String contractFor;//L - Local
	private String rateContractType;//N - Normal, E - ECommerce 
	private String contractStatus;//C- Created,S- Submitted,A- Active,I- Inactive,B- Blocked
	
	private Integer customerId;
	private BcunRateQuotationDO rateQuotationDO;
	private Integer contractCreatedBy;
	private Date createdDate;
	private Integer originatedRateContractId;
	private String isRenewed;
	
	private Set<BcunContractPaymentBillingLocationDO> conPayBillLocDO;
	
	
	/**
	 * @return the isRenewed
	 */
	public String getIsRenewed() {
		return isRenewed;
	}
	/**
	 * @param isRenewed the isRenewed to set
	 */
	public void setIsRenewed(String isRenewed) {
		this.isRenewed = isRenewed;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	
	/**
	 * @return the billingContractType
	 */
	public String getBillingContractType() {
		return billingContractType;
	}
	/**
	 * @param billingContractType the billingContractType to set
	 */
	public void setBillingContractType(String billingContractType) {
		this.billingContractType = billingContractType;
	}
	/**
	 * @return the rateContractType
	 */
	public String getRateContractType() {
		return rateContractType;
	}
	/**
	 * @param rateContractType the rateContractType to set
	 */
	public void setRateContractType(String rateContractType) {
		this.rateContractType = rateContractType;
	}
	/**
	 * @return the contractStatus
	 */
	public String getContractStatus() {
		return contractStatus;
	}
	/**
	 * @param contractStatus the contractStatus to set
	 */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	/**
	 * @return the contractCreatedBy
	 */
	public Integer getContractCreatedBy() {
		return contractCreatedBy;
	}
	/**
	 * @param contractCreatedBy the contractCreatedBy to set
	 */
	public void setContractCreatedBy(Integer contractCreatedBy) {
		this.contractCreatedBy = contractCreatedBy;
	}
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
	 * @return the rateContractNo
	 */
	public String getRateContractNo() {
		return rateContractNo;
	}
	/**
	 * @param rateContractNo the rateContractNo to set
	 */
	public void setRateContractNo(String rateContractNo) {
		this.rateContractNo = rateContractNo;
	}
	/**
	 * @return the validFromDate
	 */
	public Date getValidFromDate() {
		return validFromDate;
	}
	/**
	 * @param validFromDate the validFromDate to set
	 */
	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}
	/**
	 * @return the validToDate
	 */
	public Date getValidToDate() {
		return validToDate;
	}
	/**
	 * @param validToDate the validToDate to set
	 */
	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}
	/**
	 * @return the typeOfBilling
	 */
	public String getTypeOfBilling() {
		return typeOfBilling;
	}
	/**
	 * @param typeOfBilling the typeOfBilling to set
	 */
	public void setTypeOfBilling(String typeOfBilling) {
		this.typeOfBilling = typeOfBilling;
	}
	/**
	 * @return the modeOfBilling
	 */
	public String getModeOfBilling() {
		return modeOfBilling;
	}
	/**
	 * @param modeOfBilling the modeOfBilling to set
	 */
	public void setModeOfBilling(String modeOfBilling) {
		this.modeOfBilling = modeOfBilling;
	}
	/**
	 * @return the billingCycle
	 */
	public String getBillingCycle() {
		return billingCycle;
	}
	/**
	 * @param billingCycle the billingCycle to set
	 */
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	/**
	 * @return the paymentTerm
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}
	/**
	 * @param paymentTerm the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	/**
	 * @return the octraiBourneBy
	 */
	public String getOctraiBourneBy() {
		return octraiBourneBy;
	}
	/**
	 * @param octraiBourneBy the octraiBourneBy to set
	 */
	public void setOctraiBourneBy(String octraiBourneBy) {
		this.octraiBourneBy = octraiBourneBy;
	}
	/**
	 * @return the contractFor
	 */
	public String getContractFor() {
		return contractFor;
	}
	/**
	 * @param contractFor the contractFor to set
	 */
	public void setContractFor(String contractFor) {
		this.contractFor = contractFor;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the originatedRateContractId
	 */
	public Integer getOriginatedRateContractId() {
		return originatedRateContractId;
	}
	/**
	 * @param originatedRateContractId the originatedRateContractId to set
	 */
	public void setOriginatedRateContractId(Integer originatedRateContractId) {
		this.originatedRateContractId = originatedRateContractId;
	}
	/**
	 * @return the conPayBillLocDO
	 */
	public Set<BcunContractPaymentBillingLocationDO> getConPayBillLocDO() {
		return conPayBillLocDO;
	}
	/**
	 * @param conPayBillLocDO the conPayBillLocDO to set
	 */
	public void setConPayBillLocDO(
			Set<BcunContractPaymentBillingLocationDO> conPayBillLocDO) {
		this.conPayBillLocDO = conPayBillLocDO;
	}
	/**
	 * @return the rateQuotationDO
	 */
	public BcunRateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	/**
	 * @param rateQuotationDO the rateQuotationDO to set
	 */
	public void setRateQuotationDO(BcunRateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
}
