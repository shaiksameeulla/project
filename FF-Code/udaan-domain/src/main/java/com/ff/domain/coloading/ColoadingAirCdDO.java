package com.ff.domain.coloading;


public class ColoadingAirCdDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -255182790418632863L;

	private Integer cdId;
	private ColoadingAirDO coloadingAir;
	private String airLineName;
	private String flightNo;
	private double billingRate;
	private double fuelSurcharge;
	private double octroiPerBag;
	private double octroiPerKg;
	private double surcharge;
	private double otherCharges;
	private double cdValue;
	private double sspRate;
	
	public ColoadingAirCdDO() {
	}

	public Integer getCdId() {
		return cdId;
	}

	public void setCdId(Integer cdId) {
		this.cdId = cdId;
	}

	public ColoadingAirDO getColoadingAir() {
		return coloadingAir;
	}

	public void setColoadingAir(ColoadingAirDO coloadingAir) {
		this.coloadingAir = coloadingAir;
	}

	public String getAirLineName() {
		return this.airLineName;
	}

	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}

	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public double getBillingRate() {
		return this.billingRate;
	}

	public void setBillingRate(double billingRate) {
		this.billingRate = billingRate;
	}

	public double getFuelSurcharge() {
		return this.fuelSurcharge;
	}

	public void setFuelSurcharge(double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public double getOctroiPerBag() {
		return this.octroiPerBag;
	}

	public void setOctroiPerBag(double octroiPerBag) {
		this.octroiPerBag = octroiPerBag;
	}

	public double getOctroiPerKg() {
		return this.octroiPerKg;
	}

	public void setOctroiPerKg(double octroiPerKg) {
		this.octroiPerKg = octroiPerKg;
	}

	public double getSurcharge() {
		return this.surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public double getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public double getCdValue() {
		return this.cdValue;
	}

	public void setCdValue(double cdValue) {
		this.cdValue = cdValue;
	}

	public double getSspRate() {
		return this.sspRate;
	}

	public void setSspRate(double sspRate) {
		this.sspRate = sspRate;
	}

}
