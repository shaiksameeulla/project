package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateSectorsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateSectorsId;
	private SectorDO sectorDO;
	private String sectorType;
	private RateCustomerProductCatMapDO rateCustomerProductCatMapDO; 
	
	public RateCustomerProductCatMapDO getRateCustomerProductCatMapDO() {
		return rateCustomerProductCatMapDO;
	}
	public void setRateCustomerProductCatMapDO(
			RateCustomerProductCatMapDO rateCustomerProductCatMapDO) {
		this.rateCustomerProductCatMapDO = rateCustomerProductCatMapDO;
	}
	public Integer getRateSectorsId() {
		return rateSectorsId;
	}
	public void setRateSectorsId(Integer rateSectorsId) {
		this.rateSectorsId = rateSectorsId;
	}
	public SectorDO getSectorDO() {
		return sectorDO;
	}
	public void setSectorDO(SectorDO sectorDO) {
		this.sectorDO = sectorDO;
	}
	public String getSectorType() {
		return sectorType;
	}
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	
	
	
}
