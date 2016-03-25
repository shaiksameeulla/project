/**
 * 
 */
package com.ff.domain.stockmanagement.masters;


/**
 * The Class StockFranchiseeMappingDO.
 *
 * @author mohammes
 */
public class StockFranchiseeMappingDO extends StockBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 774036666872616966L;
	//private FranchiseeDO franchiseeDO;
	/** The franchisee id. */
	private Integer franchiseeId;
	
	/**
	 * Gets the franchisee id.
	 *
	 * @return the franchisee id
	 */
	public Integer getFranchiseeId() {
		return franchiseeId;
	}
	
	/**
	 * Sets the franchisee id.
	 *
	 * @param franchiseeId the new franchisee id
	 */
	public void setFranchiseeId(Integer franchiseeId) {
		this.franchiseeId = franchiseeId;
	}

	
}
