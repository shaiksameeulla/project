package com.ff.domain.ratemanagement.masters;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ff.domain.ratemanagement.operations.ratequotation.RateQuotationDO;

/**
 * The Class RateContractDO.
 *
 * @author narmdr
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="rateContractId")
public class RateContractDO extends CGFactDO {
	
	private static final long serialVersionUID = -4823091798225417894L;
	
	private Integer rateContractId;
	private String rateContractNo;
	private Date validFromDate;
	private Date validToDate;
	private String validFromDateStr;
	private String validToDateStr;
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
	private RateQuotationDO rateQuotationDO;
	private Integer contractCreatedBy;
	private Date createdDate;
	private String createdDateStr;
	private RateContractDO originatedRateContractDO;
	private String isRenewed;
	
	
	private Set<ContractPaymentBillingLocationDO> conPayBillLocDO;
	
	private Set<RateContractSpocDO> rateContractSpocDO;
	
	
	/**
	 * @return the conPayBillLocDO
	 */
	public Set<ContractPaymentBillingLocationDO> getConPayBillLocDO() {
		return conPayBillLocDO;
	}
	/**
	 * @return the originatedRateContractDO
	 */
	public RateContractDO getOriginatedRateContractDO() {
		return originatedRateContractDO;
	}
	/**
	 * @param originatedRateContractDO the originatedRateContractDO to set
	 */
	public void setOriginatedRateContractDO(RateContractDO originatedRateContractDO) {
		this.originatedRateContractDO = originatedRateContractDO;
	}
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
	 * @param conPayBillLocDO the conPayBillLocDO to set
	 */
	public void setConPayBillLocDO(
			Set<ContractPaymentBillingLocationDO> conPayBillLocDO) {
		this.conPayBillLocDO = conPayBillLocDO;
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
		this.createdDateStr = DateUtil.getDDMMYYYYDateToString(createdDate);
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
		this.validFromDateStr = DateUtil.getDDMMYYYYDateToString(validFromDate);
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
		this.validToDateStr = DateUtil.getDDMMYYYYDateToString(validToDate);
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
	 * @return the rateQuotationDO
	 */
	public RateQuotationDO getRateQuotationDO() {
		return rateQuotationDO;
	}
	/**
	 * @param rateQuotationDO the rateQuotationDO to set
	 */
	public void setRateQuotationDO(RateQuotationDO rateQuotationDO) {
		this.rateQuotationDO = rateQuotationDO;
	}
	/**
	 * @return the rateContractSpocDO
	 */
	public Set<RateContractSpocDO> getRateContractSpocDO() {
		return rateContractSpocDO;
	}
	/**
	 * @param rateContractSpocDO the rateContractSpocDO to set
	 */
	public void setRateContractSpocDO(Set<RateContractSpocDO> rateContractSpocDO) {
		this.rateContractSpocDO = rateContractSpocDO;
	}
	/**
	 * @return the validFromDateStr
	 */
	public String getValidFromDateStr() {
		return validFromDateStr;
	}
	/**
	 * @param validFromDateStr the validFromDateStr to set
	 */
	public void setValidFromDateStr(String validFromDateStr) {
		this.validFromDateStr = validFromDateStr;
	}
	/**
	 * @return the validToDateStr
	 */
	public String getValidToDateStr() {
		return validToDateStr;
	}
	/**
	 * @param validToDateStr the validToDateStr to set
	 */
	public void setValidToDateStr(String validToDateStr) {
		this.validToDateStr = validToDateStr;
	}
	/**
	 * @return the createdDateStr
	 */
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	/**
	 * @param createdDateStr the createdDateStr to set
	 */
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	
}
