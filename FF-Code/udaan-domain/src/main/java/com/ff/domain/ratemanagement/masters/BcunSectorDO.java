package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunSectorDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer sectorId;
	private String sectorName;
	private Integer calculationOrder;
	private String sectorType;
	private String representSectorCode;
	private Integer cityId;
	private String sectorCode;
	
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the sectorCode
	 */
	public String getSectorCode() {
		return sectorCode;
	}
	/**
	 * @param sectorCode the sectorCode to set
	 */
	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}
	/**
	 * @return the calculationOrder
	 */
	public Integer getCalculationOrder() {
		return calculationOrder;
	}
	/**
	 * @return the sectorType
	 */
	public String getSectorType() {
		return sectorType;
	}
	/**
	 * @param sectorType the sectorType to set
	 */
	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
	/**
	 * @return the representSectorCode
	 */
	public String getRepresentSectorCode() {
		return representSectorCode;
	}
	/**
	 * @param representSectorCode the representSectorCode to set
	 */
	public void setRepresentSectorCode(String representSectorCode) {
		this.representSectorCode = representSectorCode;
	}
	/**
	 * @param calculationOrder the calculationOrder to set
	 */
	public void setCalculationOrder(Integer calculationOrder) {
		this.calculationOrder = calculationOrder;
	}
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	
}
