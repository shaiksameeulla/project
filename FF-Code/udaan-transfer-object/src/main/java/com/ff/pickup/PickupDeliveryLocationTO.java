package com.ff.pickup;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PickupDeliveryLocationTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4090599370420384958L;
	private Integer pickupDlvLocId;
	private PickupDeliveryAddressTO pickupDlvAddress;
	private Integer contractId;
	
	//Added for Pickup BCUN Integration
	private String pickupDlvLocCode;
	
	/** Rate Contract - Pickup Details */
	private PickupDeliveryContractTO pickupDlvContract;
	
	
	/**
	 * @return the pickupDlvLocId
	 */
	public Integer getPickupDlvLocId() {
		return pickupDlvLocId;
	}
	/**
	 * @param pickupDlvLocId the pickupDlvLocId to set
	 */
	public void setPickupDlvLocId(Integer pickupDlvLocId) {
		this.pickupDlvLocId = pickupDlvLocId;
	}
	/**
	 * @return the pickupDlvLocCode
	 */
	public String getPickupDlvLocCode() {
		return pickupDlvLocCode;
	}
	/**
	 * @param pickupDlvLocCode the pickupDlvLocCode to set
	 */
	public void setPickupDlvLocCode(String pickupDlvLocCode) {
		this.pickupDlvLocCode = pickupDlvLocCode;
	}
	/**
	 * @return the pickupDlvAddress
	 */
	public PickupDeliveryAddressTO getPickupDlvAddress() {
		return pickupDlvAddress;
	}
	/**
	 * @param pickupDlvAddress the pickupDlvAddress to set
	 */
	public void setPickupDlvAddress(PickupDeliveryAddressTO pickupDlvAddress) {
		this.pickupDlvAddress = pickupDlvAddress;
	}
	/**
	 * @return the contractId
	 */
	public Integer getContractId() {
		return contractId;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	/**
	 * @return the pickupDlvContract
	 */
	public PickupDeliveryContractTO getPickupDlvContract() {
		return pickupDlvContract;
	}
	/**
	 * @param pickupDlvContract the pickupDlvContract to set
	 */
	public void setPickupDlvContract(PickupDeliveryContractTO pickupDlvContract) {
		this.pickupDlvContract = pickupDlvContract;
	}
	
}
