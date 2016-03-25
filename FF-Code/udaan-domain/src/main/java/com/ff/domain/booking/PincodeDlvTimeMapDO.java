package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;

public class PincodeDlvTimeMapDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569201579610087324L;
	
    private Integer pincodeDlvTimeMapId;
	private PincodeDO pincodeId;
	private ProductDO productId;
	private OfficeDO officeId;
	private String deliveryTime;
	private String IsDiscountAllowed;
	
	
	public Integer getPincodeDlvTimeMapId() {
		return pincodeDlvTimeMapId;
	}
	public void setPincodeDlvTimeMapId(Integer pincodeDlvTimeMapId) {
		this.pincodeDlvTimeMapId = pincodeDlvTimeMapId;
	}
	public PincodeDO getPincodeId() {
		return pincodeId;
	}
	public void setPincodeId(PincodeDO pincodeId) {
		this.pincodeId = pincodeId;
	}
	public ProductDO getProductId() {
		return productId;
	}
	public void setProductId(ProductDO productId) {
		this.productId = productId;
	}
	public OfficeDO getOfficeId() {
		return officeId;
	}
	public void setOfficeId(OfficeDO officeId) {
		this.officeId = officeId;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getIsDiscountAllowed() {
		return IsDiscountAllowed;
	}
	public void setIsDiscountAllowed(String isDiscountAllowed) {
		IsDiscountAllowed = isDiscountAllowed;
	}
}
