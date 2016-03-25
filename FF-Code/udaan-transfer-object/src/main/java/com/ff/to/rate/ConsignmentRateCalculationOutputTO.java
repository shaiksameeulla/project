/**
 * 
 */
package com.ff.to.rate;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class ConsignmentRateCalculationOutputTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Slab Rate  */
	private Double slabRate;
	private Double finalSlabRate;
	private Double rtoDiscount;
	private Double fuelSurcharge;
	private Double riskSurcharge;
	private Double codCharges;
	private Double tOPayCharge;
	private Double documentHandlingCharge;
	private Double parcelHandlingCharge;
	private Double airportHandlingCharge;
	private Double totalWithoutTax;
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
	private Double stateTaxOnOctroiServiceCharge;
	private Double surchargeOnStateTaxOnoctroiServiceCharge;
	private Double grandTotalIncludingTax;
	
	//For consignment rate 
	private Double lcAmount;
	private Double finalSlabRateAddedToRiskSurcharge;
	private Double declaredValue;
	private Double lcCharge;
	private Double codAmount;
	private Double otherOrSpecialCharge;
	private Double discount;
	/** List of all Rate Component  */
	private List<RateComponentCalculatedTO> calculatedRateComponents;
	private Integer consgId;
	private String rateCalculatedFor;
	
		
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
	 * @return the calculatedRateComponents
	 */
	public List<RateComponentCalculatedTO> getCalculatedRateComponents() {
		return calculatedRateComponents;
	}
	/**
	 * @param calculatedRateComponents the calculatedRateComponents to set
	 */
	public void setCalculatedRateComponents(
			List<RateComponentCalculatedTO> calculatedRateComponents) {
		this.calculatedRateComponents = calculatedRateComponents;
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
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}
	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	
	
}
