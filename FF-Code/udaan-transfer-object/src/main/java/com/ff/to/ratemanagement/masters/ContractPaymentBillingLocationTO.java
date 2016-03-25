package com.ff.to.ratemanagement.masters;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryLocationTO;

/**
 * The Class ContractPaymentBillingLocationTO.
 *
 * @author narmdr
 */
public class ContractPaymentBillingLocationTO extends CGBaseTO {
	
	private static final long serialVersionUID = 3002474606012513961L;
	
	private Integer contractPaymentBillingLocationId;//PK
	private String shippedToCode;
	private String locationOperationType;//B - Billing, P - Payment

	//private Integer pickupDeliveryLocationId;
	private PickupDeliveryLocationTO pickupDeliveryLocationTO;
	private Integer rateContractId;
	
	private Integer createdBy;
	private Integer updatedBy;
	
	private List<OfficeTO> pickupDlvBranchList;//Drop Down
	
	
	/**
	 * @return the pickupDlvBranchList
	 */
	public List<OfficeTO> getPickupDlvBranchList() {
		return pickupDlvBranchList;
	}
	/**
	 * @param pickupDlvBranchList the pickupDlvBranchList to set
	 */
	public void setPickupDlvBranchList(List<OfficeTO> pickupDlvBranchList) {
		this.pickupDlvBranchList = pickupDlvBranchList;
	}
	/**
	 * @return the pickupDeliveryLocationTO
	 */
	public PickupDeliveryLocationTO getPickupDeliveryLocationTO() {
		return pickupDeliveryLocationTO;
	}
	/**
	 * @param pickupDeliveryLocationTO the pickupDeliveryLocationTO to set
	 */
	public void setPickupDeliveryLocationTO(
			PickupDeliveryLocationTO pickupDeliveryLocationTO) {
		this.pickupDeliveryLocationTO = pickupDeliveryLocationTO;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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
	
}
