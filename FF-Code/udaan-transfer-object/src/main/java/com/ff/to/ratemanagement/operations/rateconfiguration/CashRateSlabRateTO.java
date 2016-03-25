package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class CashRateSlabRateTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;

	private Integer slabRateId;//The slabRateId.
	private Integer rateProductMapId;//The rateProductMapId.
	private Integer weightSlabId;//The weightSlabId.
	private Integer originSectorId;//The originSectorId.
	private Integer destinationSectorId;//The destinationSectorId.
	private Double slabRate;//The slabRate.
	private String servicedOn;//The servicedOn.
	
	int rowCount;
	private Double[] slabRates = new Double[rowCount];
	private Integer[] slabRateIds = new Integer[rowCount];
	private Integer[] weightSlabIds = new Integer[rowCount];
	private Integer[] destSectorIds = new Integer[rowCount];

	private String[] sectorNames = new String[rowCount];
	private Integer sectorRowCount;
	
	
	/**
	 * @return the sectorNames
	 */
	public String[] getSectorNames() {
		return sectorNames;
	}
	/**
	 * @param sectorNames the sectorNames to set
	 */
	public void setSectorNames(String[] sectorNames) {
		this.sectorNames = sectorNames;
	}
	/**
	 * @return the sectorRowCount
	 */
	public Integer getSectorRowCount() {
		return sectorRowCount;
	}
	/**
	 * @param sectorRowCount the sectorRowCount to set
	 */
	public void setSectorRowCount(Integer sectorRowCount) {
		this.sectorRowCount = sectorRowCount;
	}
	/**
	 * @return the slabRates
	 */
	public Double[] getSlabRates() {
		return slabRates;
	}
	/**
	 * @param slabRates the slabRates to set
	 */
	public void setSlabRates(Double[] slabRates) {
		this.slabRates = slabRates;
	}
	/**
	 * @return the slabRateIds
	 */
	public Integer[] getSlabRateIds() {
		return slabRateIds;
	}
	/**
	 * @param slabRateIds the slabRateIds to set
	 */
	public void setSlabRateIds(Integer[] slabRateIds) {
		this.slabRateIds = slabRateIds;
	}
	/**
	 * @return the weightSlabIds
	 */
	public Integer[] getWeightSlabIds() {
		return weightSlabIds;
	}
	/**
	 * @param weightSlabIds the weightSlabIds to set
	 */
	public void setWeightSlabIds(Integer[] weightSlabIds) {
		this.weightSlabIds = weightSlabIds;
	}
	/**
	 * @return the destSectorIds
	 */
	public Integer[] getDestSectorIds() {
		return destSectorIds;
	}
	/**
	 * @param destSectorIds the destSectorIds to set
	 */
	public void setDestSectorIds(Integer[] destSectorIds) {
		this.destSectorIds = destSectorIds;
	}
	/**
	 * @return the slabRateId
	 */
	public Integer getSlabRateId() {
		return slabRateId;
	}
	/**
	 * @param slabRateId the slabRateId to set
	 */
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
	}
	/**
	 * @return the rateProductMapId
	 */
	public Integer getRateProductMapId() {
		return rateProductMapId;
	}
	/**
	 * @param rateProductMapId the rateProductMapId to set
	 */
	public void setRateProductMapId(Integer rateProductMapId) {
		this.rateProductMapId = rateProductMapId;
	}
	/**
	 * @return the weightSlabId
	 */
	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	/**
	 * @param weightSlabId the weightSlabId to set
	 */
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
	}
	/**
	 * @return the originSectorId
	 */
	public Integer getOriginSectorId() {
		return originSectorId;
	}
	/**
	 * @param originSectorId the originSectorId to set
	 */
	public void setOriginSectorId(Integer originSectorId) {
		this.originSectorId = originSectorId;
	}
	/**
	 * @return the destinationSectorId
	 */
	public Integer getDestinationSectorId() {
		return destinationSectorId;
	}
	/**
	 * @param destinationSectorId the destinationSectorId to set
	 */
	public void setDestinationSectorId(Integer destinationSectorId) {
		this.destinationSectorId = destinationSectorId;
	}
	/**
	 * @return the slabRate
	 */
	public Double getSlabRate() {
		return slabRate;
	}
	/**
	 * @param slabRate the slabRate to set
	 */
	public void setSlabRate(Double slabRate) {
		this.slabRate = slabRate;
	}
	/**
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}
	/**
	 * @param servicedOn the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	
}
