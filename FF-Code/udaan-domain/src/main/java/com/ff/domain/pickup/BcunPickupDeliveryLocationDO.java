/**
 * 
 */
package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mohammes
 *
 */
public class BcunPickupDeliveryLocationDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602761266020321451L;
	private BcunPickupDeliveryContractDO pickupDlvContract;
	//private Integer addressId;
	private BcunAddressDO addressDO;
	private Integer pickupDlvLocId;
	private String pickupDlvLocCode;
	
	/**
	 * @return the pickupDlvContractId
	 */
	public BcunPickupDeliveryContractDO getPickupDlvContract() {
		return pickupDlvContract;
	}
	/**
	 * @param pickupDlvContractId the pickupDlvContractId to set
	 */
	public void setPickupDlvContract(
			BcunPickupDeliveryContractDO pickupDlvContract) {
		this.pickupDlvContract = pickupDlvContract;
	}
	/**
	 * @return the addressId
	 */
	/*public Integer getAddressId() {
		return addressId;
	}
	*//**
	 * @param addressId the addressId to set
	 *//*
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}*/
	/**
	 * @return the pickupDlvLocId
	 */
	public Integer getPickupDlvLocId() {
		return pickupDlvLocId;
	}
	/**
	 * @return the pickupDlvLocCode
	 */
	public String getPickupDlvLocCode() {
		return pickupDlvLocCode;
	}
	/**
	 * @param pickupDlvLocId the pickupDlvLocId to set
	 */
	public void setPickupDlvLocId(Integer pickupDlvLocId) {
		this.pickupDlvLocId = pickupDlvLocId;
	}
	/**
	 * @param pickupDlvLocCode the pickupDlvLocCode to set
	 */
	public void setPickupDlvLocCode(String pickupDlvLocCode) {
		this.pickupDlvLocCode = pickupDlvLocCode;
	}
	/**
	 * @return the addressDO
	 */
	public BcunAddressDO getAddressDO() {
		return addressDO;
	}
	/**
	 * @param addressDO the addressDO to set
	 */
	public void setAddressDO(BcunAddressDO addressDO) {
		this.addressDO = addressDO;
	}

}
