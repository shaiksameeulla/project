package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunPincodeProductServiceabilityDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 4569201579610087324L;

	private Integer pincodeDeliveryTimeMapId;
	private Integer pincodeId;
	private Integer productId;
	private Integer originCityId;
	private String deliveryTime;
	private String dlvTimeQualification = "A";
	public Integer getPincodeDeliveryTimeMapId() {
		return pincodeDeliveryTimeMapId;
	}
	public void setPincodeDeliveryTimeMapId(Integer pincodeDeliveryTimeMapId) {
		this.pincodeDeliveryTimeMapId = pincodeDeliveryTimeMapId;
	}
	public Integer getPincodeId() {
		return pincodeId;
	}
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getOriginCityId() {
		return originCityId;
	}
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
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



}
