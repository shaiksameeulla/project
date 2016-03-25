/**
 * 
 */
package com.ff.to.stockmanagement.masters;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 *
 */
public class StockStandardTypeTO extends CGBaseTO implements Comparable<StockStandardTypeTO>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6300645350237939012L;

	/**  primary key  */
	private Integer id;
	
	/**  code i.e. FR for FRANCHISEE  */
	private String stdTypeCode;
	
	/**  description for code  */
	private String description;
	
	/**  module name  */
	private String parentType;
	
	/**  category of code  */
	private String typeName;
	
	/**  current status i.e. Active/Inactive  */
	private String activeStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStdTypeCode() {
		return stdTypeCode;
	}

	public void setStdTypeCode(String stdTypeCode) {
		this.stdTypeCode = stdTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public int compareTo(StockStandardTypeTO obj) {
		// TODO Auto-generated method stub
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.description)) {
			returnVal = this.description.compareTo(obj.description);
		}
		return returnVal;
	}

	
}
