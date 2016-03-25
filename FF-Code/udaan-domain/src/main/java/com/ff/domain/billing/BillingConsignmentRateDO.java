package com.ff.domain.billing;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * The Class BillingConsignmentRateDO.
 *
 * @author narmdr
 */
public class BillingConsignmentRateDO extends CGFactDO {
	
	private static final long serialVersionUID = 6595269169785678589L;
	
	private Integer billingConsignmentRateId;
	//private ConsignmentBillingRateDO consignmentBillRateDO;
	//private BillingConsignmentDO billingConsignmentDO;
	//private ConsignmentDO consignmentDO;
	private String rateCalculatedFor;
	private Double finalSlabRate;
	private Double fuelSurcharge;
	private Double riskSurcharge;
	private Double tOPayCharge;
	private Double codCharges;
	private Double parcelHandlingCharge;
	private Double airportHandlingCharge;
	private Double documentHandlingCharge;
	private Double rtoDiscount;
	private Double otherOrSpecialCharge;
	private Double discount;
	private Double serviceTax;
	private Double educationCess;
	private Double higherEducationCess;
	private Double stateTax;
	private Double surchargeOnStateTax;
	private Double octroi;
	private Double octroiServiceCharge;
	private Double serviceTaxOnOctroiServiceCharge;
	private Double eduCessOnOctroiServiceCharge;
	private Double higherEduCessOnOctroiServiceCharge;
	private Double totalWithoutTax;
	private Double grandTotalIncludingTax;
	private Double lcCharge;
	private Double declaredValue;
	private Double slabRate;
	private Double finalSlabRateAddedToRiskSurcharge;
	private Double lcAmount;
	private Double stateTaxOnOctroiServiceCharge;
	private Double surchargeOnStateTaxOnoctroiServiceCharge;
	private Double codAmount;
	private Integer createdBy;
	private Integer updatedBy;
	private Date updatedDate;
	private Date createdDate;
	private Integer version;
	private Integer consignmentRateId;
	private Integer billingConsignmentId;
	private Integer consignmentId;
	/*private ConsignmentRateDO consignmentRateDO;
	private RateComponentDO rateComponentDO;
	private Double calculatedValue;
	private Double rtoCalculatedValue;*/
	/**
	 * @return the billingConsignmentRateId
	 */
	public Integer getBillingConsignmentRateId() {
		return billingConsignmentRateId;
	}
	/**
	 * @param billingConsignmentRateId the billingConsignmentRateId to set
	 */
	public void setBillingConsignmentRateId(Integer billingConsignmentRateId) {
		this.billingConsignmentRateId = billingConsignmentRateId;
	}
	/**
	 * @return the consignmentBillRateDO
	 *//*
	public ConsignmentBillingRateDO getConsignmentBillRateDO() {
		return consignmentBillRateDO;
	}
	*//**
	 * @param consignmentBillRateDO the consignmentBillRateDO to set
	 *//*
	public void setConsignmentBillRateDO(
			ConsignmentBillingRateDO consignmentBillRateDO) {
		this.consignmentBillRateDO = consignmentBillRateDO;
	}
	*//**
	 * @return the billingConsignmentDO
	 *//*
	public BillingConsignmentDO getBillingConsignmentDO() {
		return billingConsignmentDO;
	}
	*//**
	 * @param billingConsignmentDO the billingConsignmentDO to set
	 *//*
	public void setBillingConsignmentDO(BillingConsignmentDO billingConsignmentDO) {
		this.billingConsignmentDO = billingConsignmentDO;
	}
	*//**
	 * @return the consignmentDO
	 *//*
	public ConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}
	*//**
	 * @param consignmentDO the consignmentDO to set
	 *//*
	public void setConsignmentDO(ConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}*/
	/**
	 * @return the rateCalculatedFor
	 */
	public String getRateCalculatedFor() {
		return rateCalculatedFor;
	}
	/**
	 * @param rateCalculatedFor the rateCalculatedFor to set
	 */
	public void setRateCalculatedFor(String rateCalculatedFor) {
		this.rateCalculatedFor = rateCalculatedFor;
	}
	/**
	 * @return the finalSlabRate
	 */
	public Double getFinalSlabRate() {
		return finalSlabRate;
	}
	/**
	 * @param finalSlabRate the finalSlabRate to set
	 */
	public void setFinalSlabRate(Double finalSlabRate) {
		this.finalSlabRate = finalSlabRate;
	}
	/**
	 * @return the fuelSurcharge
	 */
	public Double getFuelSurcharge() {
		return fuelSurcharge;
	}
	/**
	 * @param fuelSurcharge the fuelSurcharge to set
	 */
	public void setFuelSurcharge(Double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	/**
	 * @return the riskSurcharge
	 */
	public Double getRiskSurcharge() {
		return riskSurcharge;
	}
	/**
	 * @param riskSurcharge the riskSurcharge to set
	 */
	public void setRiskSurcharge(Double riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	/**
	 * @return the tOPayCharge
	 */
	public Double gettOPayCharge() {
		return tOPayCharge;
	}
	/**
	 * @param tOPayCharge the tOPayCharge to set
	 */
	public void settOPayCharge(Double tOPayCharge) {
		this.tOPayCharge = tOPayCharge;
	}
	/**
	 * @return the codCharges
	 */
	public Double getCodCharges() {
		return codCharges;
	}
	/**
	 * @param codCharges the codCharges to set
	 */
	public void setCodCharges(Double codCharges) {
		this.codCharges = codCharges;
	}
	/**
	 * @return the parcelHandlingCharge
	 */
	public Double getParcelHandlingCharge() {
		return parcelHandlingCharge;
	}
	/**
	 * @param parcelHandlingCharge the parcelHandlingCharge to set
	 */
	public void setParcelHandlingCharge(Double parcelHandlingCharge) {
		this.parcelHandlingCharge = parcelHandlingCharge;
	}
	/**
	 * @return the airportHandlingCharge
	 */
	public Double getAirportHandlingCharge() {
		return airportHandlingCharge;
	}
	/**
	 * @param airportHandlingCharge the airportHandlingCharge to set
	 */
	public void setAirportHandlingCharge(Double airportHandlingCharge) {
		this.airportHandlingCharge = airportHandlingCharge;
	}
	/**
	 * @return the documentHandlingCharge
	 */
	public Double getDocumentHandlingCharge() {
		return documentHandlingCharge;
	}
	/**
	 * @param documentHandlingCharge the documentHandlingCharge to set
	 */
	public void setDocumentHandlingCharge(Double documentHandlingCharge) {
		this.documentHandlingCharge = documentHandlingCharge;
	}
	/**
	 * @return the rtoDiscount
	 */
	public Double getRtoDiscount() {
		return rtoDiscount;
	}
	/**
	 * @param rtoDiscount the rtoDiscount to set
	 */
	public void setRtoDiscount(Double rtoDiscount) {
		this.rtoDiscount = rtoDiscount;
	}
	/**
	 * @return the otherOrSpecialCharge
	 */
	public Double getOtherOrSpecialCharge() {
		return otherOrSpecialCharge;
	}
	/**
	 * @param otherOrSpecialCharge the otherOrSpecialCharge to set
	 */
	public void setOtherOrSpecialCharge(Double otherOrSpecialCharge) {
		this.otherOrSpecialCharge = otherOrSpecialCharge;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the educationCess
	 */
	public Double getEducationCess() {
		return educationCess;
	}
	/**
	 * @param educationCess the educationCess to set
	 */
	public void setEducationCess(Double educationCess) {
		this.educationCess = educationCess;
	}
	/**
	 * @return the higherEducationCess
	 */
	public Double getHigherEducationCess() {
		return higherEducationCess;
	}
	/**
	 * @param higherEducationCess the higherEducationCess to set
	 */
	public void setHigherEducationCess(Double higherEducationCess) {
		this.higherEducationCess = higherEducationCess;
	}
	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}
	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return the surchargeOnStateTax
	 */
	public Double getSurchargeOnStateTax() {
		return surchargeOnStateTax;
	}
	/**
	 * @param surchargeOnStateTax the surchargeOnStateTax to set
	 */
	public void setSurchargeOnStateTax(Double surchargeOnStateTax) {
		this.surchargeOnStateTax = surchargeOnStateTax;
	}
	/**
	 * @return the octroi
	 */
	public Double getOctroi() {
		return octroi;
	}
	/**
	 * @param octroi the octroi to set
	 */
	public void setOctroi(Double octroi) {
		this.octroi = octroi;
	}
	/**
	 * @return the octroiServiceCharge
	 */
	public Double getOctroiServiceCharge() {
		return octroiServiceCharge;
	}
	/**
	 * @param octroiServiceCharge the octroiServiceCharge to set
	 */
	public void setOctroiServiceCharge(Double octroiServiceCharge) {
		this.octroiServiceCharge = octroiServiceCharge;
	}
	/**
	 * @return the serviceTaxOnOctroiServiceCharge
	 */
	public Double getServiceTaxOnOctroiServiceCharge() {
		return serviceTaxOnOctroiServiceCharge;
	}
	/**
	 * @param serviceTaxOnOctroiServiceCharge the serviceTaxOnOctroiServiceCharge to set
	 */
	public void setServiceTaxOnOctroiServiceCharge(
			Double serviceTaxOnOctroiServiceCharge) {
		this.serviceTaxOnOctroiServiceCharge = serviceTaxOnOctroiServiceCharge;
	}
	/**
	 * @return the eduCessOnOctroiServiceCharge
	 */
	public Double getEduCessOnOctroiServiceCharge() {
		return eduCessOnOctroiServiceCharge;
	}
	/**
	 * @param eduCessOnOctroiServiceCharge the eduCessOnOctroiServiceCharge to set
	 */
	public void setEduCessOnOctroiServiceCharge(Double eduCessOnOctroiServiceCharge) {
		this.eduCessOnOctroiServiceCharge = eduCessOnOctroiServiceCharge;
	}
	/**
	 * @return the higherEduCessOnOctroiServiceCharge
	 */
	public Double getHigherEduCessOnOctroiServiceCharge() {
		return higherEduCessOnOctroiServiceCharge;
	}
	/**
	 * @param higherEduCessOnOctroiServiceCharge the higherEduCessOnOctroiServiceCharge to set
	 */
	public void setHigherEduCessOnOctroiServiceCharge(
			Double higherEduCessOnOctroiServiceCharge) {
		this.higherEduCessOnOctroiServiceCharge = higherEduCessOnOctroiServiceCharge;
	}
	/**
	 * @return the totalWithoutTax
	 */
	public Double getTotalWithoutTax() {
		return totalWithoutTax;
	}
	/**
	 * @param totalWithoutTax the totalWithoutTax to set
	 */
	public void setTotalWithoutTax(Double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}
	/**
	 * @return the grandTotalIncludingTax
	 */
	public Double getGrandTotalIncludingTax() {
		return grandTotalIncludingTax;
	}
	/**
	 * @param grandTotalIncludingTax the grandTotalIncludingTax to set
	 */
	public void setGrandTotalIncludingTax(Double grandTotalIncludingTax) {
		this.grandTotalIncludingTax = grandTotalIncludingTax;
	}
	/**
	 * @return the lcCharge
	 */
	public Double getLcCharge() {
		return lcCharge;
	}
	/**
	 * @param lcCharge the lcCharge to set
	 */
	public void setLcCharge(Double lcCharge) {
		this.lcCharge = lcCharge;
	}
	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the slabRate
	 */
	public Double getSlabRate() {
		return slabRate;
	}
	/**
	 * @param slabRate the slabRate to set
	 */
	public void setSlabRate(Double slabRate) {
		this.slabRate = slabRate;
	}
	/**
	 * @return the finalSlabRateAddedToRiskSurcharge
	 */
	public Double getFinalSlabRateAddedToRiskSurcharge() {
		return finalSlabRateAddedToRiskSurcharge;
	}
	/**
	 * @param finalSlabRateAddedToRiskSurcharge the finalSlabRateAddedToRiskSurcharge to set
	 */
	public void setFinalSlabRateAddedToRiskSurcharge(
			Double finalSlabRateAddedToRiskSurcharge) {
		this.finalSlabRateAddedToRiskSurcharge = finalSlabRateAddedToRiskSurcharge;
	}
	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	/**
	 * @return the stateTaxOnOctroiServiceCharge
	 */
	public Double getStateTaxOnOctroiServiceCharge() {
		return stateTaxOnOctroiServiceCharge;
	}
	/**
	 * @param stateTaxOnOctroiServiceCharge the stateTaxOnOctroiServiceCharge to set
	 */
	public void setStateTaxOnOctroiServiceCharge(
			Double stateTaxOnOctroiServiceCharge) {
		this.stateTaxOnOctroiServiceCharge = stateTaxOnOctroiServiceCharge;
	}
	/**
	 * @return the surchargeOnStateTaxOnoctroiServiceCharge
	 */
	public Double getSurchargeOnStateTaxOnoctroiServiceCharge() {
		return surchargeOnStateTaxOnoctroiServiceCharge;
	}
	/**
	 * @param surchargeOnStateTaxOnoctroiServiceCharge the surchargeOnStateTaxOnoctroiServiceCharge to set
	 */
	public void setSurchargeOnStateTaxOnoctroiServiceCharge(
			Double surchargeOnStateTaxOnoctroiServiceCharge) {
		this.surchargeOnStateTaxOnoctroiServiceCharge = surchargeOnStateTaxOnoctroiServiceCharge;
	}
	/**
	 * @return the codAmount
	 */
	public Double getCodAmount() {
		return codAmount;
	}
	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the consignmentRateId
	 */
	public Integer getConsignmentRateId() {
	    return consignmentRateId;
	}
	/**
	 * @param consignmentRateId the consignmentRateId to set
	 */
	public void setConsignmentRateId(Integer consignmentRateId) {
	    this.consignmentRateId = consignmentRateId;
	}
	/**
	 * @return the billingConsignmentId
	 */
	public Integer getBillingConsignmentId() {
	    return billingConsignmentId;
	}
	/**
	 * @param billingConsignmentId the billingConsignmentId to set
	 */
	public void setBillingConsignmentId(Integer billingConsignmentId) {
	    this.billingConsignmentId = billingConsignmentId;
	}
	/**
	 * @return the consignmentId
	 */
	public Integer getConsignmentId() {
	    return consignmentId;
	}
	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(Integer consignmentId) {
	    this.consignmentId = consignmentId;
	}
	
	
	
}
