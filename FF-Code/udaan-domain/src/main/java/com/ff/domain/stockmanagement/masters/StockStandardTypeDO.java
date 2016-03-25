package com.ff.domain.stockmanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class StockStandardTypeDO.
 *
 * @author hkansagr
 */

public class StockStandardTypeDO extends CGMasterDO 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
 	
	/** primary key. */
	private Integer id;
	
	/**  code i.e. FR for FRANCHISEE  */
	private String stdTypeCode;
	
	/** description for code. */
	private String description;
	
	/** module name. */
	private String parentType;
	
	/** category of code. */
	private String typeName;
	
	/**  current status i.e. Active/Inactive  */
	private String activeStatus;
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the std type code.
	 *
	 * @return the stdTypeCode
	 */
	public String getStdTypeCode() {
		return stdTypeCode;
	}
	
	/**
	 * Sets the std type code.
	 *
	 * @param stdTypeCode the stdTypeCode to set
	 */
	public void setStdTypeCode(String stdTypeCode) {
		this.stdTypeCode = stdTypeCode;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the parent type.
	 *
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}
	
	/**
	 * Sets the parent type.
	 *
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	
	/**
	 * Gets the type name.
	 *
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Sets the type name.
	 *
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * Gets the active status.
	 *
	 * @return the active status
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	
	/**
	 * Sets the active status.
	 *
	 * @param activeStatus the new active status
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
}
