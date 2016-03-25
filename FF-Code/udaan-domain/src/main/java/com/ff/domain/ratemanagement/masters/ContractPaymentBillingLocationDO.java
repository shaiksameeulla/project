package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ff.domain.pickup.PickupDeliveryLocationDO;

/**
 * The Class ContractPaymentBillingLocationDO.
 *
 * @author narmdr
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="contractPaymentBillingLocationId")
public class ContractPaymentBillingLocationDO extends CGFactDO {
	
	private static final long serialVersionUID = -9184960893821339852L;
	
	private Integer contractPaymentBillingLocationId;//PK
	private String shippedToCode;
	private String locationOperationType;//B - Billing, P - Payment

	//private Integer pickupDeliveryLocationId;
	private PickupDeliveryLocationDO pickupDeliveryLocation;
	//private Integer rateContractId;
	//@JsonBackReference
	private RateContractDO rateContractDO;
	private String status;
	
	
	/**
	 * @return the rateContractDO
	 */
	public RateContractDO getRateContractDO() {
		return rateContractDO;
	}
	/**
	 * @param rateContractDO the rateContractDO to set
	 */
	public void setRateContractDO(RateContractDO rateContractDO) {
		this.rateContractDO = rateContractDO;
	}
	/**
	 * @return the pickupDeliveryLocation
	 */
	public PickupDeliveryLocationDO getPickupDeliveryLocation() {
		return pickupDeliveryLocation;
	}
	/**
	 * @param pickupDeliveryLocation the pickupDeliveryLocation to set
	 */
	public void setPickupDeliveryLocation(
			PickupDeliveryLocationDO pickupDeliveryLocation) {
		this.pickupDeliveryLocation = pickupDeliveryLocation;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
