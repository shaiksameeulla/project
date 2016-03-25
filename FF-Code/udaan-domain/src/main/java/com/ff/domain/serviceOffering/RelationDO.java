/**
 * 
 */
package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class RelationDO.
 *
 * @author mohammes
 */
public class RelationDO extends CGMasterDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 955093081643496989L;
	
	/** The relation id. */
	private Integer relationId;
	
	/** The relation code. EX F,Sec'tary,M*/
	private String relationCode;
	
	/** The relation description.F-father, Sec'tary, M-mother */
	private String relationDescription;
	
	/** The is active. EX :Y- Yes,N-NO*/
	//private Boolean isActive;
	private String isActive;
	/**
	 * @return the relationId
	 */
	public Integer getRelationId() {
		return relationId;
	}

	/**
	 * @return the relationCode
	 */
	public String getRelationCode() {
		return relationCode;
	}

	/**
	 * @return the relationDescription
	 */
	public String getRelationDescription() {
		return relationDescription;
	}

	/**
	 * @return the isActive
	 */
	/*public Boolean getIsActive() {
		return isActive;
	}*/

	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param relationCode the relationCode to set
	 */
	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	/**
	 * @param relationDescription the relationDescription to set
	 */
	public void setRelationDescription(String relationDescription) {
		this.relationDescription = relationDescription;
	}

	/**
	 * @param isActive the isActive to set
	 */
	/*public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}*/
	
	

}

