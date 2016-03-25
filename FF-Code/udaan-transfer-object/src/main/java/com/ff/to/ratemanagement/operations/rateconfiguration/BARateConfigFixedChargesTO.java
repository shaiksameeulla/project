/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class BARateConfigFixedChargesTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1341418952395137044L;
	private Double fuelSurcharge;
	private Double otherCharges;
	private String octroiBourneBy;
	private Double airportCharges;
	private Double parcelCharges;
	private Double octroiServiceCharges;
	private Double ifInsuredByFF;
	private Double ifInsuredByCustomer;
	private Double toPayCharges;
	
	private Double serviceTax;
	private Double eduCharges;
	private Double higherEduCharges;
	private Double stateTax;
	private Double surchargeOnST;
	
	private String fuelSurchargeChk;
	private String airportChargesChk;
	private String surchargeOnSTChk;
	private String otherChargesChk;
	private String parcelChargesChk;
	private String eduChargesChk;
	private String higherEduChargesChk;
	private String serviceTaxChk;
	private String stateTaxChk;
	private String octroiServiceChargesChk;
	private String octroiBourneByChk;
	private String codChargesChk;
	private String priorityIndicator;
	private String toPayChargesChk;
	
	private int rowId;
	
	private Double[] codPercent = new Double[rowId];
	private Double[] fixedChargesEco = new Double[rowId];
	private String[] fixedChargesRadio = new String[rowId];
	private String[] fcOrCODRadio = new String[rowId];
	private Integer[] codChargeId = new Integer[rowId];
	
	private List<BACODChargesTO> codChargesTOs;
	
	/**
	 * @return the ifInsuredByFF
	 */
	public Double getIfInsuredByFF() {
		return ifInsuredByFF;
	}
	/**
	 * @param ifInsuredByFF the ifInsuredByFF to set
	 */
	public void setIfInsuredByFF(Double ifInsuredByFF) {
		this.ifInsuredByFF = ifInsuredByFF;
	}
	/**
	 * @return the ifInsuredByCustomer
	 */
	public Double getIfInsuredByCustomer() {
		return ifInsuredByCustomer;
	}
	/**
	 * @param ifInsuredByCustomer the ifInsuredByCustomer to set
	 */
	public void setIfInsuredByCustomer(Double ifInsuredByCustomer) {
		this.ifInsuredByCustomer = ifInsuredByCustomer;
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
	public String getCodChargesChk() {
		return codChargesChk;
	}
	public void setCodChargesChk(String codChargesChk) {
		this.codChargesChk = codChargesChk;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public Double[] getCodPercent() {
		return codPercent;
	}
	public void setCodPercent(Double[] codPercent) {
		this.codPercent = codPercent;
	}
	public Double[] getFixedChargesEco() {
		return fixedChargesEco;
	}
	public void setFixedChargesEco(Double[] fixedChargesEco) {
		this.fixedChargesEco = fixedChargesEco;
	}
	public String[] getFixedChargesRadio() {
		return fixedChargesRadio;
	}
	public void setFixedChargesRadio(String[] fixedChargesRadio) {
		this.fixedChargesRadio = fixedChargesRadio;
	}
	public String[] getFcOrCODRadio() {
		return fcOrCODRadio;
	}
	public void setFcOrCODRadio(String[] fcOrCODRadio) {
		this.fcOrCODRadio = fcOrCODRadio;
	}
	public Integer[] getCodChargeId() {
		return codChargeId;
	}
	public void setCodChargeId(Integer[] codChargeId) {
		this.codChargeId = codChargeId;
	}
	public String getPriorityIndicator() {
		return priorityIndicator;
	}
	public void setPriorityIndicator(String priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}
	public List<BACODChargesTO> getCodChargesTOs() {
		return codChargesTOs;
	}
	public void setCodChargesTOs(List<BACODChargesTO> codChargesTOs) {
		this.codChargesTOs = codChargesTOs;
	}
	public Double getToPayCharges() {
		return toPayCharges;
	}
	public void setToPayCharges(Double toPayCharges) {
		this.toPayCharges = toPayCharges;
	}
	public String getToPayChargesChk() {
		return toPayChargesChk;
	}
	public void setToPayChargesChk(String toPayChargesChk) {
		this.toPayChargesChk = toPayChargesChk;
	}
	
	
	
	
}
