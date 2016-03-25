/**
 * 
 */
package com.ff.domain.pickup;

/**
 * @author CBHURE
 *
 */
public class PickupDeliveryContractWrapperDO {

	private Integer contractId;
	private Integer officeId;
	private Integer customerId;
	
	private String customerCode;
    private String businessName;
    
    
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public PickupDeliveryContractWrapperDO(Integer contractId,Integer officeId){
		this.contractId = contractId;
		this.officeId = officeId;
	}
	
	public PickupDeliveryContractWrapperDO(Integer contractId,Integer customerId,Integer officeId){ 
		this.contractId = contractId;
		this.customerId = customerId;
		this.officeId = officeId;
	}
	
	public PickupDeliveryContractWrapperDO(String businessName, String customerCode ){
		this.businessName = businessName;
		this.customerCode = customerCode;
	}
	
	public PickupDeliveryContractWrapperDO(){
		
	}
	
}
