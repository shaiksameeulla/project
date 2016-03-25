package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.pickup.BcunPickupDeliveryLocationDO;

/**
 * The Class ContractPaymentBillingLocationDO.
 *
 * @author narmdr
 */
public class BcunContractPaymentBillingLocationDO extends CGFactDO {
	
	private static final long serialVersionUID = -9184960893821339852L;
	
	private Integer contractPaymentBillingLocationId;//PK
	private String shippedToCode;
	private String locationOperationType;//B - Billing, P - Payment

	//private Integer pickupDeliveryLocationId;
	private BcunPickupDeliveryLocationDO pickupDeliveryLocation;
	//private Integer rateContractId;
	private Integer rateContractId;
	private String status;
	
	/**
	 * @return the contractPaymentBillingLocationId
	 */
	public Integer getContractPaymentBillingLocationId() {
		return contractPaymentBillingLocationId;
	}
	/**
	 * @param contractPaymentBillingLocationId the contractPaymentBillingLocationId to set
	 */
	public void setContractPaymentBillingLocationId(
			Integer contractPaymentBillingLocationId) {
		this.contractPaymentBillingLocationId = contractPaymentBillingLocationId;
	}
	/**
	 * @return the locationOperationType
	 */
	public String getLocationOperationType() {
		return locationOperationType;
	}
	/**
	 * @param locationOperationType the locationOperationType to set
	 */
	public void setLocationOperationType(String locationOperationType) {
		this.locationOperationType = locationOperationType;
	}
	/**
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}
	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}
	/**
	 * @return the rateContractId
	 */
	public Integer getRateContractId() {
		return rateContractId;
	}
	/**
	 * @param rateContractId the rateContractId to set
	 */
	public void setRateContractId(Integer rateContractId) {
		this.rateContractId = rateContractId;
	}
	/**
	 * @return the pickupDeliveryLocation
	 */
	public BcunPickupDeliveryLocationDO getPickupDeliveryLocation() {
		return pickupDeliveryLocation;
	}
	/**
	 * @param pickupDeliveryLocation the pickupDeliveryLocation to set
	 */
	public void setPickupDeliveryLocation(
			BcunPickupDeliveryLocationDO pickupDeliveryLocation) {
		this.pickupDeliveryLocation = pickupDeliveryLocation;
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
