package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class CashRateConfigFixedChargesTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;

	private Integer cashFixedChargesConfigId;//The cashFixedChargesConfigId
	private Integer productMapId;//The productMapId
	private String priorityInd;//The priorityInd. i.e. Y= Priority, N= Non-Priority
	
	private Double fuelSurcharge;
	private Double ifInsuredByFFCL;
	private Double ifInsuredByCustomer;
	private Double otherCharges;
	private String octroiBourneBy;//The octroiBourneBy. i.e. CO= Consignor, CE= Consignee
	private Double octroiServiceCharges;
	private Double parcelCharges;
	private Double docCharges;
	private Double airportCharges;
	private Double toPay;
	private Double lcCharges;
	private Double serviceTax;
	private Double eduCess;
	private Double higherEduCess;
	private Double stateTax;
	private Double surchargeOnST;
	
	/** If checked, then its ON. */
	private String fuelSurchargeChk;
	private String otherChargesChk;
	private String octroiBourneByChk;
	private String octroiServiceChargesChk;
	private String parcelChargesChk;
	private String docChargesChk;
	private String airportChargesChk;
	private String toPayChk;
	private String lcChargesChk;
	private String serviceTaxChk;
	private String eduCessChk;
	private String higherEduCessChk;
	private String stateTaxChk;
	private String surchargeOnSTChk;
	
	
	
	/**
	 * @return the cashFixedChargesConfigId
	 */
	public Integer getCashFixedChargesConfigId() {
		return cashFixedChargesConfigId;
	}
	/**
	 * @param cashFixedChargesConfigId the cashFixedChargesConfigId to set
	 */
	public void setCashFixedChargesConfigId(Integer cashFixedChargesConfigId) {
		this.cashFixedChargesConfigId = cashFixedChargesConfigId;
	}
	/**
	 * @return the productMapId
	 */
	public Integer getProductMapId() {
		return productMapId;
	}
	/**
	 * @param productMapId the productMapId to set
	 */
	public void setProductMapId(Integer productMapId) {
		this.productMapId = productMapId;
	}
	/**
	 * @return the priorityInd
	 */
	public String getPriorityInd() {
		return priorityInd;
	}
	/**
	 * @param priorityInd the priorityInd to set
	 */
	public void setPriorityInd(String priorityInd) {
		this.priorityInd = priorityInd;
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
	 * @return the ifInsuredByFFCL
	 */
	public Double getIfInsuredByFFCL() {
		return ifInsuredByFFCL;
	}
	/**
	 * @param ifInsuredByFFCL the ifInsuredByFFCL to set
	 */
	public void setIfInsuredByFFCL(Double ifInsuredByFFCL) {
		this.ifInsuredByFFCL = ifInsuredByFFCL;
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
	 * @return the docCharges
	 */
	public Double getDocCharges() {
		return docCharges;
	}
	/**
	 * @param docCharges the docCharges to set
	 */
	public void setDocCharges(Double docCharges) {
		this.docCharges = docCharges;
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
	 * @return the toPay
	 */
	public Double getToPay() {
		return toPay;
	}
	/**
	 * @param toPay the toPay to set
	 */
	public void setToPay(Double toPay) {
		this.toPay = toPay;
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
	 * @return the eduCess
	 */
	public Double getEduCess() {
		return eduCess;
	}
	/**
	 * @param eduCess the eduCess to set
	 */
	public void setEduCess(Double eduCess) {
		this.eduCess = eduCess;
	}
	/**
	 * @return the higherEduCess
	 */
	public Double getHigherEduCess() {
		return higherEduCess;
	}
	/**
	 * @param higherEduCess the higherEduCess to set
	 */
	public void setHigherEduCess(Double higherEduCess) {
		this.higherEduCess = higherEduCess;
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
	 * @return the docChargesChk
	 */
	public String getDocChargesChk() {
		return docChargesChk;
	}
	/**
	 * @param docChargesChk the docChargesChk to set
	 */
	public void setDocChargesChk(String docChargesChk) {
		this.docChargesChk = docChargesChk;
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
	 * @return the toPayChk
	 */
	public String getToPayChk() {
		return toPayChk;
	}
	/**
	 * @param toPayChk the toPayChk to set
	 */
	public void setToPayChk(String toPayChk) {
		this.toPayChk = toPayChk;
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
	 * @return the eduCessChk
	 */
	public String getEduCessChk() {
		return eduCessChk;
	}
	/**
	 * @param eduCessChk the eduCessChk to set
	 */
	public void setEduCessChk(String eduCessChk) {
		this.eduCessChk = eduCessChk;
	}
	/**
	 * @return the higherEduCessChk
	 */
	public String getHigherEduCessChk() {
		return higherEduCessChk;
	}
	/**
	 * @param higherEduCessChk the higherEduCessChk to set
	 */
	public void setHigherEduCessChk(String higherEduCessChk) {
		this.higherEduCessChk = higherEduCessChk;
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
		
}
