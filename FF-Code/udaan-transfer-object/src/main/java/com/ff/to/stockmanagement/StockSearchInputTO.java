/**
 * 
 */
package com.ff.to.stockmanagement;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class StockSearchInputTO.
 *
 * @author mohammes
 */
public class StockSearchInputTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8825438483277801090L;

	/** The party type. */
	private String partyType;
	
	/** The party type id. */
	private Integer partyTypeId;
	
	/** The item id. */
	private Integer itemId;
	
	/**
	 * Gets the party type.
	 *
	 * @return the party type
	 */
	public String getPartyType() {
		return partyType;
	}
	
	/**
	 * Sets the party type.
	 *
	 * @param partyType the new party type
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public Integer getItemId() {
		return itemId;
	}
	
	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * Gets the party type id.
	 *
	 * @return the party type id
	 */
	public Integer getPartyTypeId() {
		return partyTypeId;
	}
	
	/**
	 * Sets the party type id.
	 *
	 * @param partyTypeId the new party type id
	 */
	public void setPartyTypeId(Integer partyTypeId) {
		this.partyTypeId = partyTypeId;
	}
	
}
