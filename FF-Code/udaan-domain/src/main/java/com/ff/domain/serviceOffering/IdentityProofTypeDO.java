/**
 * 
 */
package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class IdentityProofTypeDO.
 *
 * @author mohammes
 */
public class IdentityProofTypeDO extends CGMasterDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 135445434L;
	
	/** The identity proof type id.Auto generated value */
	private Integer identityProofTypeId;
	
	/** The identity proof type code. EX: DL,PAN etc*/
	private String  identityProofTypeCode;
	
	/** The identity proof type name. EX: Driving License etc */
	private String  identityProofTypeName;
	
	/** The issuing authority. EX: State Government,EC etc*/
	private String  issuingAuthority;
	
	/** The is active. EX: Y-Yes N-NO*/
	//private Boolean  isActive;
	private String  isActive = "N";

	/**
	 * @return the identityProofTypeId
	 */
	public Integer getIdentityProofTypeId() {
		return identityProofTypeId;
	}

	/**
	 * @return the identityProofTypeCode
	 */
	public String getIdentityProofTypeCode() {
		return identityProofTypeCode;
	}

	/**
	 * @return the identityProofTypeName
	 */
	public String getIdentityProofTypeName() {
		return identityProofTypeName;
	}

	/**
	 * @return the issuingAuthority
	 */
	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	/**
	 * @return the isActive
	 */
	/*public Boolean getIsActive() {
		return isActive;
	}*/

	/**
	 * @param identityProofTypeId the identityProofTypeId to set
	 */
	public void setIdentityProofTypeId(Integer identityProofTypeId) {
		this.identityProofTypeId = identityProofTypeId;
	}

	/**
	 * @param identityProofTypeCode the identityProofTypeCode to set
	 */
	public void setIdentityProofTypeCode(String identityProofTypeCode) {
		this.identityProofTypeCode = identityProofTypeCode;
	}

	/**
	 * @param identityProofTypeName the identityProofTypeName to set
	 */
	public void setIdentityProofTypeName(String identityProofTypeName) {
		this.identityProofTypeName = identityProofTypeName;
	}

	/**
	 * @param issuingAuthority the issuingAuthority to set
	 */
	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
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
	 * @param isActive the isActive to set
	 */
	/*public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}*/
	
	
	

}
