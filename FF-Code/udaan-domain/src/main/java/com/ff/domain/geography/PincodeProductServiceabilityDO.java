package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;

public class PincodeProductServiceabilityDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 4569201579610087324L;

	private Integer pincodeDeliveryTimeMapId;
	private PincodeDO pincode;
	private ProductGroupServiceabilityDO productGroupId;
	private CityDO originCity;
	private String deliveryTime;
	private String dlvTimeQualification = "A";
	private String status = "A";

	public Integer getPincodeDeliveryTimeMapId() {
		return pincodeDeliveryTimeMapId;
	}

	public void setPincodeDeliveryTimeMapId(Integer pincodeDeliveryTimeMapId) {
		this.pincodeDeliveryTimeMapId = pincodeDeliveryTimeMapId;
	}

	
	

	/**
	 * @return the pincode
	 */
	public PincodeDO getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(PincodeDO pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the productGroupId
	 */
	public ProductGroupServiceabilityDO getProductGroupId() {
		return productGroupId;
	}

	/**
	 * @param productGroupId
	 *            the productGroupId to set
	 */
	public void setProductGroupId(ProductGroupServiceabilityDO productGroupId) {
		this.productGroupId = productGroupId;
	}

	public CityDO getOriginCity() {
		return originCity;
	}

	public void setOriginCity(CityDO originCity) {
		this.originCity = originCity;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDlvTimeQualification() {
		return dlvTimeQualification;
	}

	public void setDlvTimeQualification(String dlvTimeQualification) {
		this.dlvTimeQualification = dlvTimeQualification;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
