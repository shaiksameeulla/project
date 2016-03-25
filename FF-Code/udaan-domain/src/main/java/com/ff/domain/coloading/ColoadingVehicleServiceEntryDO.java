package com.ff.domain.coloading;

import java.util.Date;

public class ColoadingVehicleServiceEntryDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date date;
	private String vehNumber;
	private String isRateCalculated = "N";
	private Integer ot;
	private Integer openingKm;
	private Integer closingKm;
	private Integer officeId;

	public ColoadingVehicleServiceEntryDO() {
	}

	
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

	public String getVehNumber() {
		return this.vehNumber;
	}

	public void setVehNumber(String vehNumber) {
		this.vehNumber = vehNumber;
	}

	public String getIsRateCalculated() {
		return isRateCalculated;
	}


	public void setIsRateCalculated(String isRateCalculated) {
		this.isRateCalculated = isRateCalculated;
	}


	public Integer getOt() {
		return this.ot;
	}

	public void setOt(Integer ot) {
		this.ot = ot;
	}

	public Integer getOpeningKm() {
		return this.openingKm;
	}

	public void setOpeningKm(Integer openingKm) {
		this.openingKm = openingKm;
	}

	public Integer getClosingKm() {
		return this.closingKm;
	}

	public void setClosingKm(Integer closingKm) {
		this.closingKm = closingKm;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((vehNumber == null) ? 0 : vehNumber.hashCode());
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
		ColoadingVehicleServiceEntryDO other = (ColoadingVehicleServiceEntryDO) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (vehNumber == null) {
			if (other.vehNumber != null)
				return false;
		} else if (!vehNumber.equals(other.vehNumber))
			return false;
		return true;
	}


	public Integer getOfficeId() {
		return officeId;
	}


	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	
	
	
	
}
