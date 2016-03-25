package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.serviceOffering.ProductGroupServiceabilityDO;

public class PincodeMasterProductServiceabilityDO extends CGFactDO {

	private Integer pincodeDeliveryTimeMapId;
	private PincodeMasterDO pincode;
	private ProductGroupServiceabilityDO productGroupId;
	private CityDO originCity;
	private String deliveryTime;
	private String dlvTimeQualification ="A";
	private String status = "A";
	/**
	 * @return the pincodeDeliveryTimeMapId
	 */
	public Integer getPincodeDeliveryTimeMapId() {
		return pincodeDeliveryTimeMapId;
	}
	/**
	 * @param pincodeDeliveryTimeMapId the pincodeDeliveryTimeMapId to set
	 */
	public void setPincodeDeliveryTimeMapId(Integer pincodeDeliveryTimeMapId) {
		this.pincodeDeliveryTimeMapId = pincodeDeliveryTimeMapId;
	}
	/**
	 * @return the pincode
	 */
	public PincodeMasterDO getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(PincodeMasterDO pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the productGroupId
	 */
	public ProductGroupServiceabilityDO getProductGroupId() {
		return productGroupId;
	}
	/**
	 * @param productGroupId the productGroupId to set
	 */
	public void setProductGroupId(ProductGroupServiceabilityDO productGroupId) {
		this.productGroupId = productGroupId;
	}
	/**
	 * @return the originCity
	 */
	public CityDO getOriginCity() {
		return originCity;
	}
	/**
	 * @param originCity the originCity to set
	 */
	public void setOriginCity(CityDO originCity) {
		this.originCity = originCity;
	}
	/**
	 * @return the deliveryTime
	 */
	public String getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * @return the dlvTimeQualification
	 */
	public String getDlvTimeQualification() {
		return dlvTimeQualification;
	}
	/**
	 * @param dlvTimeQualification the dlvTimeQualification to set
	 */
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
