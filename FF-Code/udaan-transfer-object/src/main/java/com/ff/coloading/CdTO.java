package com.ff.coloading;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CdTO extends CGBaseTO implements
Comparable<CdTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cdId;
	private String airLine;
	private String flightNo;
	private Double billingRate;
	private Double fuelSurcharge;
	private Double octroiPerBag;
	private Double octroiPerKG;
	private Double surcharge;
	private Double otherCharges;
	private Double cd;
	private Double sSPRate;
	private Integer createdBy ;
	private String createdDate;
	private char storeStatus;
	private String erroString;
	
	private Set<String> flightSet;

	public Integer getCdId() {
		return cdId;
	}
	public void setCdId(Integer cdId) {
		this.cdId = cdId;
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
	
	
	public Double getBillingRate() {
		return billingRate;
	}
	public void setBillingRate(Double billingRate) {
		this.billingRate = billingRate;
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
	public Double getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}
	public Double getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}
	public Double getCd() {
		return cd;
	}
	public void setCd(Double cd) {
		this.cd = cd;
	}
	public Double getsSPRate() {
		return sSPRate;
	}
	public void setsSPRate(Double sSPRate) {
		this.sSPRate = sSPRate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CdTO cdTo) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(flightNo)) {
			returnVal = this.flightNo.compareTo(cdTo.getFlightNo());
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
		CdTO other = (CdTO) obj;
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
