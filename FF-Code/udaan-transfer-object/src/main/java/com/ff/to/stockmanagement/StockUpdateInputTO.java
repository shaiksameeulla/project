/**
 * 
 */
package com.ff.to.stockmanagement;


/**
 * The Class StockUpdateInputTO.
 *
 * @author mohammes
 */
public class StockUpdateInputTO extends StockSearchInputTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 23343545790671L;
	
	/** The is decrease. */
	private boolean isDecrease;//Mandatory field
	
	/** The quantity. */
	private Integer quantity;
	
	/**
	 * Gets the checks if is decrease.
	 *
	 * @return the checks if is decrease
	 */
	public Boolean getIsDecrease() {
		return isDecrease;
	}
	
	/**
	 * Sets the checks if is decrease.
	 *
	 * @param isDecrease the new checks if is decrease
	 */
	public void setIsDecrease(Boolean isDecrease) {
		this.isDecrease = isDecrease;
	}
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


}
