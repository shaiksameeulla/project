/**
 * 
 */
package com.ff.domain.leads;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author abarudwa
 *
 */
public class LeadProductDO extends CGFactDO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String stdTypeCode;
	
	private String description;
	
	private String parentType;
	
	private String typeName;
	
	private String curStatus;
	
	private String dtToBranch;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the stdTypeCode
	 */
	public String getStdTypeCode() {
		return stdTypeCode;
	}

	/**
	 * @param stdTypeCode the stdTypeCode to set
	 */
	public void setStdTypeCode(String stdTypeCode) {
		this.stdTypeCode = stdTypeCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the curStatus
	 */
	public String getCurStatus() {
		return curStatus;
	}

	/**
	 * @param curStatus the curStatus to set
	 */
	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	/**
	 * @return the dtToBranch
	 */
	public String getDtToBranch() {
		return dtToBranch;
	}

	/**
	 * @param dtToBranch the dtToBranch to set
	 */
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
