package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class CSDSAPPickupDeliveryLocationDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4473168912874734493L;
	private Integer pickupDlvLocId;
	//Added for Pickup BCUN Integration
	private String pickupDlvLocCode;
	private CSDSAPPickupDeliveryContractDO pickupDlvContract;
	private AddressDO address;

	/**
	 * @return the pickupDlvLocId
	 */
	public Integer getPickupDlvLocId() {
		return pickupDlvLocId;
	}

	/**
	 * @param pickupDlvLocId
	 *            the pickupDlvLocId to set
	 */
	public void setPickupDlvLocId(Integer pickupDlvLocId) {
		this.pickupDlvLocId = pickupDlvLocId;
	}
	
	/**
	 * @return the pickupDlvContract
	 */
	public CSDSAPPickupDeliveryContractDO getPickupDlvContract() {
		return pickupDlvContract;
	}

	/**
	 * @param pickupDlvContract the pickupDlvContract to set
	 */
	public void setPickupDlvContract(
			CSDSAPPickupDeliveryContractDO pickupDlvContract) {
		this.pickupDlvContract = pickupDlvContract;
	}

	/**
	 * @return the address
	 */
	public AddressDO getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(AddressDO address) {
		this.address = address;
	}

	public String getPickupDlvLocCode() {
		return pickupDlvLocCode;
	}

	public void setPickupDlvLocCode(String pickupDlvLocCode) {
		this.pickupDlvLocCode = pickupDlvLocCode;
	}

	

}
