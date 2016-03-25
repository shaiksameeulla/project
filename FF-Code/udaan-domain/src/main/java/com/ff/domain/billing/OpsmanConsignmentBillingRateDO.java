package com.ff.domain.billing;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.consignment.OpsmanConsignmentDO;

public class OpsmanConsignmentBillingRateDO extends CGFactDO {
	
	private static final long serialVersionUID = -6646071741689055705L;
	private Integer consignmentRateId;
	private String rateCalculatedFor = "B";
	@JsonBackReference
	private OpsmanConsignmentDO consignmentDO;
	private Double finalSlabRate;
	private Double fuelSurcharge;
	private Double riskSurcharge;

	private Double toPayCharge;
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
	private String billed="N";
	private Boolean isCnBillingRateUpdated = Boolean.FALSE;
	
	public Integer getConsignmentRateId() {
		return consignmentRateId;
	}
	public void setConsignmentRateId(Integer consignmentRateId) {
		this.consignmentRateId = consignmentRateId;
	}
	public String getRateCalculatedFor() {
		return rateCalculatedFor;
	}
	public void setRateCalculatedFor(String rateCalculatedFor) {
		this.rateCalculatedFor = rateCalculatedFor;
	}
	public OpsmanConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}
	public void setConsignmentDO(OpsmanConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}
	public Double getFinalSlabRate() {
		return finalSlabRate;
	}
	public void setFinalSlabRate(Double finalSlabRate) {
		this.finalSlabRate = finalSlabRate;
	}
	public Double getFuelSurcharge() {
		return fuelSurcharge;
	}
	public void setFuelSurcharge(Double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	public Double getRiskSurcharge() {
		return riskSurcharge;
	}
	public void setRiskSurcharge(Double riskSurcharge) {
		this.riskSurcharge = riskSurcharge;
	}
	public Double getToPayCharge() {
		return toPayCharge;
	}
	public void setToPayCharge(Double toPayCharge) {
		this.toPayCharge = toPayCharge;
	}
	public Double getCodCharges() {
		return codCharges;
	}
	public void setCodCharges(Double codCharges) {
		this.codCharges = codCharges;
	}
	public Double getParcelHandlingCharge() {
		return parcelHandlingCharge;
	}
	public void setParcelHandlingCharge(Double parcelHandlingCharge) {
		this.parcelHandlingCharge = parcelHandlingCharge;
	}
	public Double getAirportHandlingCharge() {
		return airportHandlingCharge;
	}
	public void setAirportHandlingCharge(Double airportHandlingCharge) {
		this.airportHandlingCharge = airportHandlingCharge;
	}
	public Double getDocumentHandlingCharge() {
		return documentHandlingCharge;
	}
	public void setDocumentHandlingCharge(Double documentHandlingCharge) {
		this.documentHandlingCharge = documentHandlingCharge;
	}
	public Double getRtoDiscount() {
		return rtoDiscount;
	}
	public void setRtoDiscount(Double rtoDiscount) {
		this.rtoDiscount = rtoDiscount;
	}
	public Double getOtherOrSpecialCharge() {
		return otherOrSpecialCharge;
	}
	public void setOtherOrSpecialCharge(Double otherOrSpecialCharge) {
		this.otherOrSpecialCharge = otherOrSpecialCharge;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public Double getEducationCess() {
		return educationCess;
	}
	public void setEducationCess(Double educationCess) {
		this.educationCess = educationCess;
	}
	public Double getHigherEducationCess() {
		return higherEducationCess;
	}
	public void setHigherEducationCess(Double higherEducationCess) {
		this.higherEducationCess = higherEducationCess;
	}
	public Double getStateTax() {
		return stateTax;
	}
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	public Double getSurchargeOnStateTax() {
		return surchargeOnStateTax;
	}
	public void setSurchargeOnStateTax(Double surchargeOnStateTax) {
		this.surchargeOnStateTax = surchargeOnStateTax;
	}
	public Double getOctroi() {
		return octroi;
	}
	public void setOctroi(Double octroi) {
		this.octroi = octroi;
	}
	public Double getOctroiServiceCharge() {
		return octroiServiceCharge;
	}
	public void setOctroiServiceCharge(Double octroiServiceCharge) {
		this.octroiServiceCharge = octroiServiceCharge;
	}
	public Double getServiceTaxOnOctroiServiceCharge() {
		return serviceTaxOnOctroiServiceCharge;
	}
	public void setServiceTaxOnOctroiServiceCharge(
			Double serviceTaxOnOctroiServiceCharge) {
		this.serviceTaxOnOctroiServiceCharge = serviceTaxOnOctroiServiceCharge;
	}
	public Double getEduCessOnOctroiServiceCharge() {
		return eduCessOnOctroiServiceCharge;
	}
	public void setEduCessOnOctroiServiceCharge(Double eduCessOnOctroiServiceCharge) {
		this.eduCessOnOctroiServiceCharge = eduCessOnOctroiServiceCharge;
	}
	public Double getHigherEduCessOnOctroiServiceCharge() {
		return higherEduCessOnOctroiServiceCharge;
	}
	public void setHigherEduCessOnOctroiServiceCharge(
			Double higherEduCessOnOctroiServiceCharge) {
		this.higherEduCessOnOctroiServiceCharge = higherEduCessOnOctroiServiceCharge;
	}
	public Double getTotalWithoutTax() {
		return totalWithoutTax;
	}
	public void setTotalWithoutTax(Double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}
	public Double getGrandTotalIncludingTax() {
		return grandTotalIncludingTax;
	}
	public void setGrandTotalIncludingTax(Double grandTotalIncludingTax) {
		this.grandTotalIncludingTax = grandTotalIncludingTax;
	}
	public Double getLcCharge() {
		return lcCharge;
	}
	public void setLcCharge(Double lcCharge) {
		this.lcCharge = lcCharge;
	}
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public Double getSlabRate() {
		return slabRate;
	}
	public void setSlabRate(Double slabRate) {
		this.slabRate = slabRate;
	}
	public Double getFinalSlabRateAddedToRiskSurcharge() {
		return finalSlabRateAddedToRiskSurcharge;
	}
	public void setFinalSlabRateAddedToRiskSurcharge(
			Double finalSlabRateAddedToRiskSurcharge) {
		this.finalSlabRateAddedToRiskSurcharge = finalSlabRateAddedToRiskSurcharge;
	}
	public Double getLcAmount() {
		return lcAmount;
	}
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	public Double getStateTaxOnOctroiServiceCharge() {
		return stateTaxOnOctroiServiceCharge;
	}
	public void setStateTaxOnOctroiServiceCharge(
			Double stateTaxOnOctroiServiceCharge) {
		this.stateTaxOnOctroiServiceCharge = stateTaxOnOctroiServiceCharge;
	}
	public Double getSurchargeOnStateTaxOnoctroiServiceCharge() {
		return surchargeOnStateTaxOnoctroiServiceCharge;
	}
	public void setSurchargeOnStateTaxOnoctroiServiceCharge(
			Double surchargeOnStateTaxOnoctroiServiceCharge) {
		this.surchargeOnStateTaxOnoctroiServiceCharge = surchargeOnStateTaxOnoctroiServiceCharge;
	}
	public Double getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	public String getBilled() {
		return billed;
	}
	public void setBilled(String billed) {
		this.billed = billed;
	}
	public Boolean getIsCnBillingRateUpdated() {
		return isCnBillingRateUpdated;
	}
	public void setIsCnBillingRateUpdated(Boolean isCnBillingRateUpdated) {
		this.isCnBillingRateUpdated = isCnBillingRateUpdated;
	}
	
}
