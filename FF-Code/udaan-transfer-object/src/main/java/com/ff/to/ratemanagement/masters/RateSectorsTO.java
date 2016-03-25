package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateSectorsTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateSectorsId;
	private SectorTO sectorTO;
	private String sectorType;
	private RateCustomerProductCatMapTO rateCustomerProductCatMapTO;
	
	public Integer getRateSectorsId() {
		return rateSectorsId;
	}
	public void setRateSectorsId(Integer rateSectorsId) {
		this.rateSectorsId = rateSectorsId;
	}
	public SectorTO getSectorTO() {
		return sectorTO;
	}
	public void setSectorTO(SectorTO sectorTO) {
		this.sectorTO = sectorTO;
	}
	public String getSectorType() {
		return sectorType;
	}
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	public RateCustomerProductCatMapTO getRateCustomerProductCatMapTO() {
		return rateCustomerProductCatMapTO;
	}
	public void setRateCustomerProductCatMapTO(
			RateCustomerProductCatMapTO rateCustomerProductCatMapTO) {
		this.rateCustomerProductCatMapTO = rateCustomerProductCatMapTO;
	}
		
}
