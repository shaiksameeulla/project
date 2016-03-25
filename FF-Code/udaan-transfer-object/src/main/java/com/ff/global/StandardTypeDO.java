/**
 * 
 */
package com.ff.global;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author nkattung
 * 
 */
public class StandardTypeDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8878388296321527041L;
	private Integer id;
	private String stdTypeCode;
	private String description;
	private String parentType;
	private String typeName;
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

}
