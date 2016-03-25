/**
 * 
 */
package com.ff.domain.stockmanagement.masters;

import com.ff.domain.organization.OfficeDO;


/**
 * The Class StockOfficeMappingDO.
 *
 * @author mohammes
 */
public class StockOfficeMappingDO extends StockBaseDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4519693092093021183L;
	
	/** The office do. */
	private OfficeDO officeDO;
	
	/** The reorder level quantity. */
	private Integer reorderLevelQuantity;
	
	/** The reorder req quantity. */
	private Integer reorderReqQuantity;
	
	/** The is auto requisiton required. */
	private String isAutoRequisitonRequired="N";
	
	
	/**
	 * Gets the reorder req quantity.
	 *
	 * @return the reorder req quantity
	 */
	public Integer getReorderReqQuantity() {
		return reorderReqQuantity;
	}
	
	/**
	 * Sets the reorder req quantity.
	 *
	 * @param reorderReqQuantity the new reorder req quantity
	 */
	public void setReorderReqQuantity(Integer reorderReqQuantity) {
		this.reorderReqQuantity = reorderReqQuantity;
	}
	
	/**
	 * Gets the reorder level quantity.
	 *
	 * @return the reorder level quantity
	 */
	public Integer getReorderLevelQuantity() {
		return reorderLevelQuantity;
	}
	
	/**
	 * Sets the reorder level quantity.
	 *
	 * @param reorderLevelQuantity the new reorder level quantity
	 */
	public void setReorderLevelQuantity(Integer reorderLevelQuantity) {
		this.reorderLevelQuantity = reorderLevelQuantity;
	}
	
	/**
	 * Gets the checks if is auto requisiton required.
	 *
	 * @return the isAutoRequisitonRequired
	 */
	public String getIsAutoRequisitonRequired() {
		return isAutoRequisitonRequired;
	}
	
	/**
	 * Sets the checks if is auto requisiton required.
	 *
	 * @param isAutoRequisitonRequired the isAutoRequisitonRequired to set
	 */
	public void setIsAutoRequisitonRequired(String isAutoRequisitonRequired) {
		this.isAutoRequisitonRequired = isAutoRequisitonRequired;
	}
	
	/**
	 * Gets the office do.
	 *
	 * @return the officeDO
	 */
	public OfficeDO getOfficeDO() {
		return officeDO;
	}
	
	/**
	 * Sets the office do.
	 *
	 * @param officeDO the officeDO to set
	 */
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
	}
}
