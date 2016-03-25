package com.ff.domain.coloading;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ColoadingAirDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int originRegionId;
	private int originCityId;
	private int destinationRegionId;
	private int destinationCityId;
	private int vendorId;
	private Date effectiveFrom;
	private Date effectiveTill;
	private String cdType;
	private Integer sspRateAboveKg;

	private Set<ColoadingAirAwbDO> coloadingAirAwbs;
	private Set<ColoadingAirCdDO> coloadingAirCds;

	public ColoadingAirDO() {
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

	public int getDestinationRegionId() {
		return this.destinationRegionId;
	}

	public void setDestinationRegionId(int destinationRegionId) {
		this.destinationRegionId = destinationRegionId;
	}

	public int getDestinationCityId() {
		return this.destinationCityId;
	}

	public void setDestinationCityId(int destinationCityId) {
		this.destinationCityId = destinationCityId;
	}

	public int getVendorId() {
		return vendorId;
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

	public String getCdType() {
		return this.cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public Set<ColoadingAirAwbDO> getColoadingAirAwbs() {
		return coloadingAirAwbs;
	}

	public void setColoadingAirAwbs(Set<ColoadingAirAwbDO> coloadingAirAwbs) {
		this.coloadingAirAwbs = coloadingAirAwbs;
	}

	public Set<ColoadingAirCdDO> getColoadingAirCds() {
		return coloadingAirCds;
	}

	public void setColoadingAirCds(Set<ColoadingAirCdDO> coloadingAirCds) {
		this.coloadingAirCds = coloadingAirCds;
	}

	public Integer getSspRateAboveKg() {
		return sspRateAboveKg;
	}

	public void setSspRateAboveKg(Integer sspRateAboveKg) {
		this.sspRateAboveKg = sspRateAboveKg;
	}

	/**
	 * @return the effectiveTill
	 */
	public Date getEffectiveTill() {
		return effectiveTill;
	}

	/**
	 * @param effectiveTill the effectiveTill to set
	 */
	public void setEffectiveTill(Date effectiveTill) {
		this.effectiveTill = effectiveTill;
	}
	
	

}
