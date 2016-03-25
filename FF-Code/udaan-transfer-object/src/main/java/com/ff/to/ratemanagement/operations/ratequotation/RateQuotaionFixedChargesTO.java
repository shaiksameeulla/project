package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateQuotaionFixedChargesTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -283412833588386867L;
	private Integer quotaionFixedChargesId;
	private RateQuotationTO rateQuotation;
	private String rateComponentCode;
	private Double value;
	
	private String riskSurchrge;
	private Double riskSurchrgeValue;
	private Double fuelSurcharge;
	private Double airportCharges;
	private Double surchargeOnST;
	private Double otherCharges;
	private Double parcelCharges;
	private Double eduCharges;
	private Double higherEduCharges;
	private Double documentCharges;
	private Double toPayCharges;
	private Double serviceTax;
	private Double stateTax;
	private Double lcCharges;
	private Double octroiServiceCharges;
	private String octroiBourneBy;
	private Double fixedCharges;
	private Double codCharges;
	private Double vwDenominator;
	private int rowId;
	
	private Double[] codPercent = new Double[rowId];
	private Double[] fixedChargesEco = new Double[rowId];
	private String[] fixedChargesRadio = new String[rowId];
	private String[] fcOrCODRadio = new String[rowId];
	private Integer[] codChargeId = new Integer[rowId];
	
	
	private String taxApplicableChk;
	private String codPercentChk;
	private String riskSurchrgeChk;
	private String fuelSurchargeChk;
	private String airportChargesChk;
	private String surchargeOnSTChk;
	private String otherChargesChk;
	private String parcelChargesChk;
	private String eduChargesChk;
	private String higherEduChargesChk;
	private String documentChargesChk;
	private String toPayChargesChk;
	private String serviceTaxChk;
	private String stateTaxChk;
	private String lcChargesChk;
	private String octroiServiceChargesChk;
	private String octroiBourneByChk;
	private String fixedChargesChk;
	private String codChargesChk;
	private String vwDenominatorChk;
	private String transMsg;
	private boolean isSaved = Boolean.FALSE;
	
	
	
	
	
	
	
	/**
	 * @return the isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved the isSaved to set
	 */
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	public String getTransMsg() {
		return transMsg;
	}
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}
	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	/**
	 * @return the quotaionFixedChargesId
	 */
	public Integer getQuotaionFixedChargesId() {
		return quotaionFixedChargesId;
	}
	/**
	 * @param quotaionFixedChargesId the quotaionFixedChargesId to set
	 */
	public void setQuotaionFixedChargesId(Integer quotaionFixedChargesId) {
		this.quotaionFixedChargesId = quotaionFixedChargesId;
	}
	/**
	 * @return the rateQuotation
	 */
	public RateQuotationTO getRateQuotation() {
		return rateQuotation;
	}
	/**
	 * @param rateQuotation the rateQuotation to set
	 */
	public void setRateQuotation(RateQuotationTO rateQuotation) {
		this.rateQuotation = rateQuotation;
	}
	
	/**
	 * @return the rateComponentCode
	 */
	public String getRateComponentCode() {
		return rateComponentCode;
	}
	/**
	 * @param rateComponentCode the rateComponentCode to set
	 */
	public void setRateComponentCode(String rateComponentCode) {
		this.rateComponentCode = rateComponentCode;
	}
	
	/**
	 * @return the riskSurchrge
	 */
	public String getRiskSurchrge() {
		return riskSurchrge;
	}
	/**
	 * @param riskSurchrge the riskSurchrge to set
	 */
	public void setRiskSurchrge(String riskSurchrge) {
		this.riskSurchrge = riskSurchrge;
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
	 * @return the airportCharges
	 */
	public Double getAirportCharges() {
		return airportCharges;
	}
	/**
	 * @param airportCharges the airportCharges to set
	 */
	public void setAirportCharges(Double airportCharges) {
		this.airportCharges = airportCharges;
	}
	/**
	 * @return the surchargeOnST
	 */
	public Double getSurchargeOnST() {
		return surchargeOnST;
	}
	/**
	 * @param surchargeOnST the surchargeOnST to set
	 */
	public void setSurchargeOnST(Double surchargeOnST) {
		this.surchargeOnST = surchargeOnST;
	}
	/**
	 * @return the otherCharges
	 */
	public Double getOtherCharges() {
		return otherCharges;
	}
	/**
	 * @param otherCharges the otherCharges to set
	 */
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	/**
	 * @return the parcelCharges
	 */
	public Double getParcelCharges() {
		return parcelCharges;
	}
	/**
	 * @param parcelCharges the parcelCharges to set
	 */
	public void setParcelCharges(Double parcelCharges) {
		this.parcelCharges = parcelCharges;
	}
	/**
	 * @return the eduCharges
	 */
	public Double getEduCharges() {
		return eduCharges;
	}
	/**
	 * @param eduCharges the eduCharges to set
	 */
	public void setEduCharges(Double eduCharges) {
		this.eduCharges = eduCharges;
	}
	/**
	 * @return the higherEduCharges
	 */
	public Double getHigherEduCharges() {
		return higherEduCharges;
	}
	/**
	 * @param higherEduCharges the higherEduCharges to set
	 */
	public void setHigherEduCharges(Double higherEduCharges) {
		this.higherEduCharges = higherEduCharges;
	}
	/**
	 * @return the documentCharges
	 */
	public Double getDocumentCharges() {
		return documentCharges;
	}
	/**
	 * @param documentCharges the documentCharges to set
	 */
	public void setDocumentCharges(Double documentCharges) {
		this.documentCharges = documentCharges;
	}
	/**
	 * @return the toPayCharges
	 */
	public Double getToPayCharges() {
		return toPayCharges;
	}
	/**
	 * @param toPayCharges the toPayCharges to set
	 */
	public void setToPayCharges(Double toPayCharges) {
		this.toPayCharges = toPayCharges;
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
	 * @return the lcCharges
	 */
	public Double getLcCharges() {
		return lcCharges;
	}
	/**
	 * @param lcCharges the lcCharges to set
	 */
	public void setLcCharges(Double lcCharges) {
		this.lcCharges = lcCharges;
	}
	/**
	 * @return the octroiServiceCharges
	 */
	public Double getOctroiServiceCharges() {
		return octroiServiceCharges;
	}
	/**
	 * @param octroiServiceCharges the octroiServiceCharges to set
	 */
	public void setOctroiServiceCharges(Double octroiServiceCharges) {
		this.octroiServiceCharges = octroiServiceCharges;
	}
	/**
	 * @return the octroiBourneBy
	 */
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	/**
	 * @param octroiBourneBy the octroiBourneBy to set
	 */
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	/**
	 * @return the riskSurchrgeChk
	 */
	public String getRiskSurchrgeChk() {
		return riskSurchrgeChk;
	}
	/**
	 * @param riskSurchrgeChk the riskSurchrgeChk to set
	 */
	public void setRiskSurchrgeChk(String riskSurchrgeChk) {
		this.riskSurchrgeChk = riskSurchrgeChk;
	}
	/**
	 * @return the fuelSurchargeChk
	 */
	public String getFuelSurchargeChk() {
		return fuelSurchargeChk;
	}
	/**
	 * @param fuelSurchargeChk the fuelSurchargeChk to set
	 */
	public void setFuelSurchargeChk(String fuelSurchargeChk) {
		this.fuelSurchargeChk = fuelSurchargeChk;
	}
	/**
	 * @return the airportChargesChk
	 */
	public String getAirportChargesChk() {
		return airportChargesChk;
	}
	/**
	 * @param airportChargesChk the airportChargesChk to set
	 */
	public void setAirportChargesChk(String airportChargesChk) {
		this.airportChargesChk = airportChargesChk;
	}
	/**
	 * @return the surchargeOnSTChk
	 */
	public String getSurchargeOnSTChk() {
		return surchargeOnSTChk;
	}
	/**
	 * @param surchargeOnSTChk the surchargeOnSTChk to set
	 */
	public void setSurchargeOnSTChk(String surchargeOnSTChk) {
		this.surchargeOnSTChk = surchargeOnSTChk;
	}
	/**
	 * @return the otherChargesChk
	 */
	public String getOtherChargesChk() {
		return otherChargesChk;
	}
	/**
	 * @param otherChargesChk the otherChargesChk to set
	 */
	public void setOtherChargesChk(String otherChargesChk) {
		this.otherChargesChk = otherChargesChk;
	}
	/**
	 * @return the parcelChargesChk
	 */
	public String getParcelChargesChk() {
		return parcelChargesChk;
	}
	/**
	 * @param parcelChargesChk the parcelChargesChk to set
	 */
	public void setParcelChargesChk(String parcelChargesChk) {
		this.parcelChargesChk = parcelChargesChk;
	}
	/**
	 * @return the eduChargesChk
	 */
	public String getEduChargesChk() {
		return eduChargesChk;
	}
	/**
	 * @param eduChargesChk the eduChargesChk to set
	 */
	public void setEduChargesChk(String eduChargesChk) {
		this.eduChargesChk = eduChargesChk;
	}
	/**
	 * @return the higherEduChargesChk
	 */
	public String getHigherEduChargesChk() {
		return higherEduChargesChk;
	}
	/**
	 * @param higherEduChargesChk the higherEduChargesChk to set
	 */
	public void setHigherEduChargesChk(String higherEduChargesChk) {
		this.higherEduChargesChk = higherEduChargesChk;
	}
	/**
	 * @return the documentChargesChk
	 */
	public String getDocumentChargesChk() {
		return documentChargesChk;
	}
	/**
	 * @param documentChargesChk the documentChargesChk to set
	 */
	public void setDocumentChargesChk(String documentChargesChk) {
		this.documentChargesChk = documentChargesChk;
	}
	/**
	 * @return the toPayChargesChk
	 */
	public String getToPayChargesChk() {
		return toPayChargesChk;
	}
	/**
	 * @param toPayChargesChk the toPayChargesChk to set
	 */
	public void setToPayChargesChk(String toPayChargesChk) {
		this.toPayChargesChk = toPayChargesChk;
	}
	/**
	 * @return the serviceTaxChk
	 */
	public String getServiceTaxChk() {
		return serviceTaxChk;
	}
	/**
	 * @param serviceTaxChk the serviceTaxChk to set
	 */
	public void setServiceTaxChk(String serviceTaxChk) {
		this.serviceTaxChk = serviceTaxChk;
	}
	/**
	 * @return the stateTaxChk
	 */
	public String getStateTaxChk() {
		return stateTaxChk;
	}
	/**
	 * @param stateTaxChk the stateTaxChk to set
	 */
	public void setStateTaxChk(String stateTaxChk) {
		this.stateTaxChk = stateTaxChk;
	}
	/**
	 * @return the lcChargesChk
	 */
	public String getLcChargesChk() {
		return lcChargesChk;
	}
	/**
	 * @param lcChargesChk the lcChargesChk to set
	 */
	public void setLcChargesChk(String lcChargesChk) {
		this.lcChargesChk = lcChargesChk;
	}
	/**
	 * @return the octroiServiceChargesChk
	 */
	public String getOctroiServiceChargesChk() {
		return octroiServiceChargesChk;
	}
	/**
	 * @param octroiServiceChargesChk the octroiServiceChargesChk to set
	 */
	public void setOctroiServiceChargesChk(String octroiServiceChargesChk) {
		this.octroiServiceChargesChk = octroiServiceChargesChk;
	}
	/**
	 * @return the octroiBourneByChk
	 */
	public String getOctroiBourneByChk() {
		return octroiBourneByChk;
	}
	/**
	 * @param octroiBourneByChk the octroiBourneByChk to set
	 */
	public void setOctroiBourneByChk(String octroiBourneByChk) {
		this.octroiBourneByChk = octroiBourneByChk;
	}
	
	/**
	 * @return the riskSurchrgeValue
	 */
	public Double getRiskSurchrgeValue() {
		return riskSurchrgeValue;
	}
	/**
	 * @param riskSurchrgeValue the riskSurchrgeValue to set
	 */
	public void setRiskSurchrgeValue(Double riskSurchrgeValue) {
		this.riskSurchrgeValue = riskSurchrgeValue;
	}
	/**
	 * @return the fixedCharges
	 */
	public Double getFixedCharges() {
		return fixedCharges;
	}
	/**
	 * @param fixedCharges the fixedCharges to set
	 */
	public void setFixedCharges(Double fixedCharges) {
		this.fixedCharges = fixedCharges;
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
	 * @return the vwDenominator
	 */
	public Double getVwDenominator() {
		return vwDenominator;
	}
	/**
	 * @param vwDenominator the vwDenominator to set
	 */
	public void setVwDenominator(Double vwDenominator) {
		this.vwDenominator = vwDenominator;
	}
	/**
	 * @return the fixedChargesChk
	 */
	public String getFixedChargesChk() {
		return fixedChargesChk;
	}
	/**
	 * @param fixedChargesChk the fixedChargesChk to set
	 */
	public void setFixedChargesChk(String fixedChargesChk) {
		this.fixedChargesChk = fixedChargesChk;
	}
	/**
	 * @return the codChargesChk
	 */
	public String getCodChargesChk() {
		return codChargesChk;
	}
	/**
	 * @param codChargesChk the codChargesChk to set
	 */
	public void setCodChargesChk(String codChargesChk) {
		this.codChargesChk = codChargesChk;
	}
	/**
	 * @return the vwDenominatorChk
	 */
	public String getVwDenominatorChk() {
		return vwDenominatorChk;
	}
	/**
	 * @param vwDenominatorChk the vwDenominatorChk to set
	 */
	public void setVwDenominatorChk(String vwDenominatorChk) {
		this.vwDenominatorChk = vwDenominatorChk;
	}
	
	/**
	 * @return the taxApplicableChk
	 */
	public String getTaxApplicableChk() {
		return taxApplicableChk;
	}
	/**
	 * @param taxApplicableChk the taxApplicableChk to set
	 */
	public void setTaxApplicableChk(String taxApplicableChk) {
		this.taxApplicableChk = taxApplicableChk;
	}
	/**
	 * @return the codPercentChk
	 */
	public String getCodPercentChk() {
		return codPercentChk;
	}
	/**
	 * @param codPercentChk the codPercentChk to set
	 */
	public void setCodPercentChk(String codPercentChk) {
		this.codPercentChk = codPercentChk;
	}
	/**
	 * @return the rowId
	 */
	public int getRowId() {
		return rowId;
	}
	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	/**
	 * @return the codPercent
	 */
	public Double[] getCodPercent() {
		return codPercent;
	}
	/**
	 * @param codPercent the codPercent to set
	 */
	public void setCodPercent(Double[] codPercent) {
		this.codPercent = codPercent;
	}
	/**
	 * @return the fixedChargesEco
	 */
	public Double[] getFixedChargesEco() {
		return fixedChargesEco;
	}
	/**
	 * @param fixedChargesEco the fixedChargesEco to set
	 */
	public void setFixedChargesEco(Double[] fixedChargesEco) {
		this.fixedChargesEco = fixedChargesEco;
	}
	/**
	 * @return the fixedChargesRadio
	 */
	public String[] getFixedChargesRadio() {
		return fixedChargesRadio;
	}
	/**
	 * @param fixedChargesRadio the fixedChargesRadio to set
	 */
	public void setFixedChargesRadio(String[] fixedChargesRadio) {
		this.fixedChargesRadio = fixedChargesRadio;
	}
	/**
	 * @return the fcOrCODRadio
	 */
	public String[] getFcOrCODRadio() {
		return fcOrCODRadio;
	}
	/**
	 * @param fcOrCODRadio the fcOrCODRadio to set
	 */
	public void setFcOrCODRadio(String[] fcOrCODRadio) {
		this.fcOrCODRadio = fcOrCODRadio;
	}
	/**
	 * @return the codChargeId
	 */
	public Integer[] getCodChargeId() {
		return codChargeId;
	}
	/**
	 * @param codChargeId the codChargeId to set
	 */
	public void setCodChargeId(Integer[] codChargeId) {
		this.codChargeId = codChargeId;
	}
	
	
}