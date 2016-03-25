package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateSectorsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateSectorsId;
	private Integer sectorId;
	private String sectorType;
	private Integer rateCustomerProductCatMapId; 
	
	public Integer getRateSectorsId() {
		return rateSectorsId;
	}
	public void setRateSectorsId(Integer rateSectorsId) {
		this.rateSectorsId = rateSectorsId;
	}
	public String getSectorType() {
		return sectorType;
	}
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	/**
	 * @return the sectorId
	 */
	public Integer getSectorId() {
		return sectorId;
	}
	/**
	 * @param sectorId the sectorId to set
	 */
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	/**
	 * @return the rateCustomerProductCatMapId
	 */
	public Integer getRateCustomerProductCatMapId() {
		return rateCustomerProductCatMapId;
	}
	/**
	 * @param rateCustomerProductCatMapId the rateCustomerProductCatMapId to set
	 */
	public void setRateCustomerProductCatMapId(Integer rateCustomerProductCatMapId) {
		this.rateCustomerProductCatMapId = rateCustomerProductCatMapId;
	}
}
