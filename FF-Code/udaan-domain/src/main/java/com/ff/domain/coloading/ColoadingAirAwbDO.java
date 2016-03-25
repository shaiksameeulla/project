package com.ff.domain.coloading;



public class ColoadingAirAwbDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer awbId;
	private ColoadingAirDO coloadingAir;
	private String airLineName;
	private String flightNo;
	private String flightType;
	private double minTariff;
	private double w1;
	private double w2;
	private double w3;
	private double w4;
	private double w5;
	private double w6;
	private double fuelSurcharge;
	private double octroiPerBag;
	private double octroiPerKg;
	private double originTspFlatRate;
	private double originTspPerKgRate;
	private double destTspFlatRate;
	private double destTspPerKgRate;
	private double airportHandlingCharge;
	private double xrayCharge;
	private double originMinUtilCharge;
	private double originUtilChargePerKg;
	private double destMinUtilCharge;
	private double destUtilChargePerKg;
	private double airlineServiceCharge;
	private double surcharge;
	private double airwayBill;
	private double discountedPercentage;
	private double sspRate;
	
	public ColoadingAirAwbDO() {
	}

	

	public Integer getAwbId() {
		return awbId;
	}



	public void setAwbId(Integer awbId) {
		this.awbId = awbId;
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

	public String getFlightType() {
		return this.flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public double getMinTariff() {
		return this.minTariff;
	}

	public void setMinTariff(double minTariff) {
		this.minTariff = minTariff;
	}

	public double getW1() {
		return this.w1;
	}

	public void setW1(double w1) {
		this.w1 = w1;
	}

	public double getW2() {
		return this.w2;
	}

	public void setW2(double w2) {
		this.w2 = w2;
	}

	public double getW3() {
		return this.w3;
	}

	public void setW3(double w3) {
		this.w3 = w3;
	}

	public double getW4() {
		return this.w4;
	}

	public void setW4(double w4) {
		this.w4 = w4;
	}

	public double getW5() {
		return this.w5;
	}

	public void setW5(double w5) {
		this.w5 = w5;
	}

	public double getW6() {
		return this.w6;
	}

	public void setW6(double w6) {
		this.w6 = w6;
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

	public double getOriginTspFlatRate() {
		return this.originTspFlatRate;
	}

	public void setOriginTspFlatRate(double originTspFlatRate) {
		this.originTspFlatRate = originTspFlatRate;
	}

	public double getOriginTspPerKgRate() {
		return this.originTspPerKgRate;
	}

	public void setOriginTspPerKgRate(double originTspPerKgRate) {
		this.originTspPerKgRate = originTspPerKgRate;
	}

	public double getDestTspFlatRate() {
		return this.destTspFlatRate;
	}

	public void setDestTspFlatRate(double destTspFlatRate) {
		this.destTspFlatRate = destTspFlatRate;
	}

	public double getDestTspPerKgRate() {
		return this.destTspPerKgRate;
	}

	public void setDestTspPerKgRate(double destTspPerKgRate) {
		this.destTspPerKgRate = destTspPerKgRate;
	}

	public double getAirportHandlingCharge() {
		return this.airportHandlingCharge;
	}

	public void setAirportHandlingCharge(double airportHandlingCharge) {
		this.airportHandlingCharge = airportHandlingCharge;
	}

	public double getXrayCharge() {
		return this.xrayCharge;
	}

	public void setXrayCharge(double xrayCharge) {
		this.xrayCharge = xrayCharge;
	}

	public double getOriginMinUtilCharge() {
		return this.originMinUtilCharge;
	}

	public void setOriginMinUtilCharge(double originMinUtilCharge) {
		this.originMinUtilCharge = originMinUtilCharge;
	}

	public double getOriginUtilChargePerKg() {
		return this.originUtilChargePerKg;
	}

	public void setOriginUtilChargePerKg(double originUtilChargePerKg) {
		this.originUtilChargePerKg = originUtilChargePerKg;
	}

	public double getDestMinUtilCharge() {
		return this.destMinUtilCharge;
	}

	public void setDestMinUtilCharge(double destMinUtilCharge) {
		this.destMinUtilCharge = destMinUtilCharge;
	}

	public double getDestUtilChargePerKg() {
		return this.destUtilChargePerKg;
	}

	public void setDestUtilChargePerKg(double destUtilChargePerKg) {
		this.destUtilChargePerKg = destUtilChargePerKg;
	}

	public double getAirlineServiceCharge() {
		return this.airlineServiceCharge;
	}

	public void setAirlineServiceCharge(double airlineServiceCharge) {
		this.airlineServiceCharge = airlineServiceCharge;
	}

	public double getSurcharge() {
		return this.surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

	public double getAirwayBill() {
		return this.airwayBill;
	}

	public void setAirwayBill(double airwayBill) {
		this.airwayBill = airwayBill;
	}

	public double getDiscountedPercentage() {
		return this.discountedPercentage;
	}

	public void setDiscountedPercentage(double discountedPercentage) {
		this.discountedPercentage = discountedPercentage;
	}

	public double getSspRate() {
		return this.sspRate;
	}

	public void setSspRate(double sspRate) {
		this.sspRate = sspRate;
	}

}
