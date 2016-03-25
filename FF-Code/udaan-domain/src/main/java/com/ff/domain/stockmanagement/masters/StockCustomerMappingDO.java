/**
 * 
 */
package com.ff.domain.stockmanagement.masters;


/**
 * The Class StockCustomerMappingDO.
 *
 * @author mohammes
 */
public class StockCustomerMappingDO extends StockBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 774036666872616966L;
	
	//private CustomerDO customerDO;
	/** The customer id. */
	private Integer customerId;
	
	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	
	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
}
