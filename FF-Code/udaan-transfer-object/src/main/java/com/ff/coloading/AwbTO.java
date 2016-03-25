package com.ff.coloading;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class AwbTO extends CGBaseTO implements Comparable<AwbTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer awbId;
	private String airLine;
	private String flightNo;
	private String flightType;
	private Double minTariff;
	private Double w1;
	private Double w2;
	private Double w3;
	private Double w4;
	private Double w5;
	private Double w6;
	private Double fuelSurcharge;
	private Double octroiPerBag;
	private Double octroiPerKG;
	private Double originTSPFlatRate;
	private Double originTSPPerKGRate;
	private Double destinationTSPFlatRate;
	private Double destinationTSPPerKGRate;
	private Double airportHandlingCharges;
	private Double xRayCharge;
	private Double originMinUtilizationCharge;
	private Double originUtilizationChargesPerKG;
	private Double destinationMinUtilizationCharge;
	private Double destinationUtilizationChargesPerKG;
	private Double serviceChargeOfAirline;
	private Double surcharge;
	private Double airWayBill;
	private Double discountedPercent;
	private Double sSPRate;
	private char storeStatus;
	private Integer createdBy ;
	private String createdDate;
	private String erroString;
	private Set<String> flightSet;

	public Integer getAwbId() {
		return awbId;
	}

	public void setAwbId(Integer awbId) {
		this.awbId = awbId;
	}

	public String getAirLine() {
		return airLine;
	}

	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public Double getMinTariff() {
		return minTariff;
	}

	public void setMinTariff(Double minTariff) {
		this.minTariff = minTariff;
	}

	public Double getW1() {
		return w1;
	}

	public void setW1(Double w1) {
		this.w1 = w1;
	}

	public Double getW2() {
		return w2;
	}

	public void setW2(Double w2) {
		this.w2 = w2;
	}

	public Double getW3() {
		return w3;
	}

	public void setW3(Double w3) {
		this.w3 = w3;
	}

	public Double getW4() {
		return w4;
	}

	public void setW4(Double w4) {
		this.w4 = w4;
	}

	public Double getW5() {
		return w5;
	}

	public void setW5(Double w5) {
		this.w5 = w5;
	}

	public Double getW6() {
		return w6;
	}

	public void setW6(Double w6) {
		this.w6 = w6;
	}

	public Double getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(Double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public Double getOctroiPerBag() {
		return octroiPerBag;
	}

	public void setOctroiPerBag(Double octroiPerBag) {
		this.octroiPerBag = octroiPerBag;
	}

	public Double getOctroiPerKG() {
		return octroiPerKG;
	}

	public void setOctroiPerKG(Double octroiPerKG) {
		this.octroiPerKG = octroiPerKG;
	}

	public Double getOriginTSPFlatRate() {
		return originTSPFlatRate;
	}

	public void setOriginTSPFlatRate(Double originTSPFlatRate) {
		this.originTSPFlatRate = originTSPFlatRate;
	}

	public Double getOriginTSPPerKGRate() {
		return originTSPPerKGRate;
	}

	public void setOriginTSPPerKGRate(Double originTSPPerKGRate) {
		this.originTSPPerKGRate = originTSPPerKGRate;
	}

	public Double getDestinationTSPFlatRate() {
		return destinationTSPFlatRate;
	}

	public void setDestinationTSPFlatRate(Double destinationTSPFlatRate) {
		this.destinationTSPFlatRate = destinationTSPFlatRate;
	}

	public Double getDestinationTSPPerKGRate() {
		return destinationTSPPerKGRate;
	}

	public void setDestinationTSPPerKGRate(Double destinationTSPPerKGRate) {
		this.destinationTSPPerKGRate = destinationTSPPerKGRate;
	}

	public Double getAirportHandlingCharges() {
		return airportHandlingCharges;
	}

	public void setAirportHandlingCharges(Double airportHandlingCharges) {
		this.airportHandlingCharges = airportHandlingCharges;
	}

	public Double getxRayCharge() {
		return xRayCharge;
	}

	public void setxRayCharge(Double xRayCharge) {
		this.xRayCharge = xRayCharge;
	}

	public Double getOriginMinUtilizationCharge() {
		return originMinUtilizationCharge;
	}

	public void setOriginMinUtilizationCharge(Double originMinUtilizationCharge) {
		this.originMinUtilizationCharge = originMinUtilizationCharge;
	}

	public Double getOriginUtilizationChargesPerKG() {
		return originUtilizationChargesPerKG;
	}

	public void setOriginUtilizationChargesPerKG(
			Double originUtilizationChargesPerKG) {
		this.originUtilizationChargesPerKG = originUtilizationChargesPerKG;
	}

	public Double getDestinationMinUtilizationCharge() {
		return destinationMinUtilizationCharge;
	}

	public void setDestinationMinUtilizationCharge(
			Double destinationMinUtilizationCharge) {
		this.destinationMinUtilizationCharge = destinationMinUtilizationCharge;
	}

	public Double getDestinationUtilizationChargesPerKG() {
		return destinationUtilizationChargesPerKG;
	}

	public void setDestinationUtilizationChargesPerKG(
			Double destinationUtilizationChargesPerKG) {
		this.destinationUtilizationChargesPerKG = destinationUtilizationChargesPerKG;
	}

	public Double getServiceChargeOfAirline() {
		return serviceChargeOfAirline;
	}

	public void setServiceChargeOfAirline(Double serviceChargeOfAirline) {
		this.serviceChargeOfAirline = serviceChargeOfAirline;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public Double getAirWayBill() {
		return airWayBill;
	}

	public void setAirWayBill(Double airWayBill) {
		this.airWayBill = airWayBill;
	}

	public Double getDiscountedPercent() {
		return discountedPercent;
	}

	public void setDiscountedPercent(Double discountedPercent) {
		this.discountedPercent = discountedPercent;
	}

	public Double getsSPRate() {
		return sSPRate;
	}

	public void setsSPRate(Double sSPRate) {
		this.sSPRate = sSPRate;
	}

	@Override
	public int compareTo(AwbTO awbTo) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(flightNo)) {
			returnVal = this.flightNo.compareTo(awbTo.getFlightNo());
		}
		return returnVal;
	}

	public char getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(char storeStatus) {
		this.storeStatus = storeStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((flightNo == null) ? 0 : flightNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AwbTO other = (AwbTO) obj;
		if (flightNo == null) {
			if (other.flightNo != null)
				return false;
		} else if (!flightNo.equals(other.flightNo))
			return false;
		return true;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getErroString() {
		return erroString;
	}

	public void setErroString(String erroString) {
		this.erroString = erroString;
	}

	public Set<String> getFlightSet() {
		if(flightSet == null) {
			flightSet = new HashSet<String>();
			return flightSet;
		} else {
			return flightSet;
		}
	}
	
	public void setFlightSet(Set<String> flightSet) {
		this.flightSet = flightSet;
	}
	
}
