package com.ff.to.billing;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.consignment.ConsignmentTO;

public class ConsignmentBillingRateTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer consignmentRateId;
	private String rateCalculatedFor;
	private Integer consignmentId;
	private Double finalSlabRate;
	private Double fuelSurcharge;
	private Double riskSurcharge;
	
	private Double toPaySurcharge;
	private Double codCharges;
	private Double parcelHandlingCharge;
	private Double airportHandlingCharge;
	private Double documentHandlingCharge;
	private Double rtoDiscount;
	private Double otherSpecialCharges;
	private Double discount;
	private Double serviceTax;
	private Double educationCess;
	private Double higherEducationCes;
	private Double stateTax;
	private Double surchargeOnStateTax;
	private Double octroi;
	private Double serviceChargeOnOctroi;
	private Double serviceTaxOnOctroiService;
	private Double eduCessOnOctroiCharge;
	private Double higherEduCessOnOctroiCharge;
	private Double totalWithoutTax;
	private Double grandTotalTax;
	private Double lcCharge;
	private Double declaredValue;
	private Double slabRate;
	private Double finalSlabRateToRiskSurcharge;
	private Double lcAmount;
	private Double octroiSalesTaxOnCharge;
	private Double octroiSurchargeOnStateTaxCharge;
	private Double codAmount;
	private String billed;
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
	 * @return the toPaySurcharge
	 */
	public Double getToPaySurcharge() {
		return toPaySurcharge;
	}
	/**
	 * @param toPaySurcharge the toPaySurcharge to set
	 */
	public void setToPaySurcharge(Double toPaySurcharge) {
		this.toPaySurcharge = toPaySurcharge;
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
	 * @return the otherSpecialCharges
	 */
	public Double getOtherSpecialCharges() {
		return otherSpecialCharges;
	}
	/**
	 * @param otherSpecialCharges the otherSpecialCharges to set
	 */
	public void setOtherSpecialCharges(Double otherSpecialCharges) {
		this.otherSpecialCharges = otherSpecialCharges;
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
	 * @return the higherEducationCes
	 */
	public Double getHigherEducationCes() {
		return higherEducationCes;
	}
	/**
	 * @param higherEducationCes the higherEducationCes to set
	 */
	public void setHigherEducationCes(Double higherEducationCes) {
		this.higherEducationCes = higherEducationCes;
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
	 * @return the serviceChargeOnOctroi
	 */
	public Double getServiceChargeOnOctroi() {
		return serviceChargeOnOctroi;
	}
	/**
	 * @param serviceChargeOnOctroi the serviceChargeOnOctroi to set
	 */
	public void setServiceChargeOnOctroi(Double serviceChargeOnOctroi) {
		this.serviceChargeOnOctroi = serviceChargeOnOctroi;
	}
	/**
	 * @return the serviceTaxOnOctroiService
	 */
	public Double getServiceTaxOnOctroiService() {
		return serviceTaxOnOctroiService;
	}
	/**
	 * @param serviceTaxOnOctroiService the serviceTaxOnOctroiService to set
	 */
	public void setServiceTaxOnOctroiService(Double serviceTaxOnOctroiService) {
		this.serviceTaxOnOctroiService = serviceTaxOnOctroiService;
	}
	/**
	 * @return the eduCessOnOctroiCharge
	 */
	public Double getEduCessOnOctroiCharge() {
		return eduCessOnOctroiCharge;
	}
	/**
	 * @param eduCessOnOctroiCharge the eduCessOnOctroiCharge to set
	 */
	public void setEduCessOnOctroiCharge(Double eduCessOnOctroiCharge) {
		this.eduCessOnOctroiCharge = eduCessOnOctroiCharge;
	}
	/**
	 * @return the higherEduCessOnOctroiCharge
	 */
	public Double getHigherEduCessOnOctroiCharge() {
		return higherEduCessOnOctroiCharge;
	}
	/**
	 * @param higherEduCessOnOctroiCharge the higherEduCessOnOctroiCharge to set
	 */
	public void setHigherEduCessOnOctroiCharge(Double higherEduCessOnOctroiCharge) {
		this.higherEduCessOnOctroiCharge = higherEduCessOnOctroiCharge;
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
	 * @return the grandTotalTax
	 */
	public Double getGrandTotalTax() {
		return grandTotalTax;
	}
	/**
	 * @param grandTotalTax the grandTotalTax to set
	 */
	public void setGrandTotalTax(Double grandTotalTax) {
		this.grandTotalTax = grandTotalTax;
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
	 * @return the finalSlabRateToRiskSurcharge
	 */
	public Double getFinalSlabRateToRiskSurcharge() {
		return finalSlabRateToRiskSurcharge;
	}
	/**
	 * @param finalSlabRateToRiskSurcharge the finalSlabRateToRiskSurcharge to set
	 */
	public void setFinalSlabRateToRiskSurcharge(Double finalSlabRateToRiskSurcharge) {
		this.finalSlabRateToRiskSurcharge = finalSlabRateToRiskSurcharge;
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
	 * @return the octroiSalesTaxOnCharge
	 */
	public Double getOctroiSalesTaxOnCharge() {
		return octroiSalesTaxOnCharge;
	}
	/**
	 * @param octroiSalesTaxOnCharge the octroiSalesTaxOnCharge to set
	 */
	public void setOctroiSalesTaxOnCharge(Double octroiSalesTaxOnCharge) {
		this.octroiSalesTaxOnCharge = octroiSalesTaxOnCharge;
	}
	/**
	 * @return the octroiSurchargeOnCharge
	 */
	public Double getOctroiSurchargeOnStateTaxCharge() {
		return octroiSurchargeOnStateTaxCharge;
	}
	/**
	 * @param octroiSurchargeOnCharge the octroiSurchargeOnCharge to set
	 */
	public void setOctroiSurchargeOnStateTaxCharge(Double octroiSurchargeOnStateTaxCharge) {
		this.octroiSurchargeOnStateTaxCharge = octroiSurchargeOnStateTaxCharge;
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
	 * @return the billed
	 */
	public String getBilled() {
		return billed;
	}
	/**
	 * @param billed the billed to set
	 */
	public void setBilled(String billed) {
		this.billed = billed;
	}
}
