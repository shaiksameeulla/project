package com.ff.domain.coloading;

import java.util.Date;


public class ColoaderRatesDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date date;
	private String inoviceNo;
	private Integer vendorId;
	private Integer officeId;
	private String serviceType;
	private String subRateType;
	private String transportRefNo;
	private String connectedAs;
	private Double quantity;
	private Integer uom;
	private Double weight;
	private Double ssp;
	private Double fsc;
	private Double octroi;
	private Double originTsp;
	private Double destinationTsp;
	private Double airportHandlingCharges;
	private Double xrayCharges;
	private Double originUti;
	private Double destinationUti;
	private Double serviceChargeOfAirline;
	private Double surcharge;
	private Double otherCharges;
	private Double basic;
	private Double awbCdCharges;
	private Double total;
	private Double serviceTax;
	private Double educationalCess;
	private Double higherEducationalCess;
	private Double grossTotal;
	private Double discount;
	private Double discountWhenDiscountConsidered;
	private Double netEffectiveAmts;
	private Double freeKm;
	private Double perKmRate;
	private Double perHrOt;
	private Double fuelRate;
	private String sapStatus = "N";
	private Date sapTimestamp;
	private Date dispatchDate;
	private String awb_cd_rr_number;
	private Integer origin_id;
	private Integer destination_id;
	private String dispatchNumber;
	private Integer tripSheetNumber;
	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInoviceNo() {
		return this.inoviceNo;
	}

	public void setInoviceNo(String inoviceNo) {
		this.inoviceNo = inoviceNo;
	}

	public Integer getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getOfficeId() {
		return this.officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSubRateType() {
		return this.subRateType;
	}

	public void setSubRateType(String subRateType) {
		this.subRateType = subRateType;
	}

	public String getTransportRefNo() {
		return this.transportRefNo;
	}

	public void setTransportRefNo(String transportRefNo) {
		this.transportRefNo = transportRefNo;
	}

	public String getConnectedAs() {
		return this.connectedAs;
	}

	public void setConnectedAs(String connectedAs) {
		this.connectedAs = connectedAs;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getUom() {
		return this.uom;
	}

	public void setUom(Integer uom) {
		this.uom = uom;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getSsp() {
		return this.ssp;
	}

	public void setSsp(Double ssp) {
		this.ssp = ssp;
	}

	public Double getFsc() {
		return this.fsc;
	}

	public void setFsc(Double fsc) {
		this.fsc = fsc;
	}

	public Double getOctroi() {
		return this.octroi;
	}

	public void setOctroi(Double octroi) {
		this.octroi = octroi;
	}

	public Double getOriginTsp() {
		return this.originTsp;
	}

	public void setOriginTsp(Double originTsp) {
		this.originTsp = originTsp;
	}

	public Double getDestinationTsp() {
		return this.destinationTsp;
	}

	public void setDestinationTsp(Double destinationTsp) {
		this.destinationTsp = destinationTsp;
	}

	public Double getAirportHandlingCharges() {
		return this.airportHandlingCharges;
	}

	public void setAirportHandlingCharges(Double airportHandlingCharges) {
		this.airportHandlingCharges = airportHandlingCharges;
	}

	public Double getXrayCharges() {
		return this.xrayCharges;
	}

	public void setXrayCharges(Double xrayCharges) {
		this.xrayCharges = xrayCharges;
	}

	public Double getOriginUti() {
		return this.originUti;
	}

	public void setOriginUti(Double originUti) {
		this.originUti = originUti;
	}

	public Double getDestinationUti() {
		return this.destinationUti;
	}

	public void setDestinationUti(Double destinationUti) {
		this.destinationUti = destinationUti;
	}

	public Double getServiceChargeOfAirline() {
		return this.serviceChargeOfAirline;
	}

	public void setServiceChargeOfAirline(Double serviceChargeOfAirline) {
		this.serviceChargeOfAirline = serviceChargeOfAirline;
	}

	public Double getSurcharge() {
		return this.surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public Double getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public Double getBasic() {
		return this.basic;
	}

	public void setBasic(Double basic) {
		this.basic = basic;
	}

	
	public Double getAwbCdCharges() {
		return awbCdCharges;
	}

	public void setAwbCdCharges(Double awbCdCharges) {
		this.awbCdCharges = awbCdCharges;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getServiceTax() {
		return this.serviceTax;
	}

	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Double getEducationalCess() {
		return this.educationalCess;
	}

	public void setEducationalCess(Double educationalCess) {
		this.educationalCess = educationalCess;
	}

	public Double getHigherEducationalCess() {
		return this.higherEducationalCess;
	}

	public void setHigherEducationalCess(Double higherEducationalCess) {
		this.higherEducationalCess = higherEducationalCess;
	}

	public Double getGrossTotal() {
		return this.grossTotal;
	}

	public void setGrossTotal(Double grossTotal) {
		this.grossTotal = grossTotal;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountWhenDiscountConsidered() {
		return this.discountWhenDiscountConsidered;
	}

	public void setDiscountWhenDiscountConsidered(
			Double discountWhenDiscountConsidered) {
		this.discountWhenDiscountConsidered = discountWhenDiscountConsidered;
	}

	public Double getNetEffectiveAmts() {
		return this.netEffectiveAmts;
	}

	public void setNetEffectiveAmts(Double netEffectiveAmts) {
		this.netEffectiveAmts = netEffectiveAmts;
	}

	public Double getFreeKm() {
		return this.freeKm;
	}

	public void setFreeKm(Double freeKm) {
		this.freeKm = freeKm;
	}

	public Double getPerKmRate() {
		return this.perKmRate;
	}

	public void setPerKmRate(Double perKmRate) {
		this.perKmRate = perKmRate;
	}

	public Double getPerHrOt() {
		return this.perHrOt;
	}

	public void setPerHrOt(Double perHrOt) {
		this.perHrOt = perHrOt;
	}

	public Double getFuelRate() {
		return this.fuelRate;
	}

	public void setFuelRate(Double fuelRate) {
		this.fuelRate = fuelRate;
	}

	public Date getSapTimestamp() {
		return this.sapTimestamp;
	}

	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getAwb_cd_rr_number() {
		return awb_cd_rr_number;
	}

	public void setAwb_cd_rr_number(String awb_cd_rr_number) {
		this.awb_cd_rr_number = awb_cd_rr_number;
	}

	public Integer getDestination_id() {
		return destination_id;
	}

	public void setDestination_id(Integer destination_id) {
		this.destination_id = destination_id;
	}

	public String getDispatchNumber() {
		return dispatchNumber;
	}

	public void setDispatchNumber(String dispatchNumber) {
		this.dispatchNumber = dispatchNumber;
	}

	public Integer getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(Integer tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	/**
	 * @return the sapStatus
	 */
	public String getSapStatus() {
		return sapStatus;
	}

	/**
	 * @param sapStatus the sapStatus to set
	 */
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	public Integer getOrigin_id() {
		return origin_id;
	}

	public void setOrigin_id(Integer origin_id) {
		this.origin_id = origin_id;
	}

	
	
}
