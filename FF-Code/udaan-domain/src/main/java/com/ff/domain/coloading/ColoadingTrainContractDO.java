package com.ff.domain.coloading;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ColoadingTrainContractDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int originRegionId;
	private int originCityId;
	private int destRegionId;
	private int destCityId;
	private int vendorId;
	private Date effectiveFrom;
	private Date effectiveTill;
	private Set<ColoadingTrainContractRateDetailsDO> coloadingTrainContractRateDtls;

	public ColoadingTrainContractDO() {
	}

	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOriginRegionId() {
		return this.originRegionId;
	}

	public void setOriginRegionId(int originRegionId) {
		this.originRegionId = originRegionId;
	}

	public int getOriginCityId() {
		return this.originCityId;
	}

	public void setOriginCityId(int originCityId) {
		this.originCityId = originCityId;
	}

	public int getDestRegionId() {
		return this.destRegionId;
	}

	public void setDestRegionId(int destRegionId) {
		this.destRegionId = destRegionId;
	}

	public int getDestCityId() {
		return this.destCityId;
	}

	public void setDestCityId(int destCityId) {
		this.destCityId = destCityId;
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public Date getEffectiveFrom() {
		return this.effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveTill() {
		return this.effectiveTill;
	}

	public void setEffectiveTill(Date effectiveTill) {
		this.effectiveTill = effectiveTill;
	}

	public Set<ColoadingTrainContractRateDetailsDO> getColoadingTrainContractRateDtls() {
		return coloadingTrainContractRateDtls;
	}



	public void setColoadingTrainContractRateDtls(
			Set<ColoadingTrainContractRateDetailsDO> coloadingTrainContractRateDtls) {
		this.coloadingTrainContractRateDtls = coloadingTrainContractRateDtls;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ColoadingTrainContractDO other = (ColoadingTrainContractDO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
