package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.consignment.BcunConsignmentDO;

/**
 * The Class BcunConsignmentManifestDO.
 */
public class BcunConsignmentManifestDO extends CGFactDO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 318542926281738743L;

	/** The consignment manifest id. */
	private Integer consignmentManifestId;
	
	/** The consignment. */
	private BcunConsignmentDO consignment;
	
	private Integer manifestId;
		
	private Boolean isConsgUpdated = Boolean.FALSE;

	/**
	 * @return the consignmentManifestId
	 */
	public Integer getConsignmentManifestId() {
		return consignmentManifestId;
	}

	/**
	 * @param consignmentManifestId the consignmentManifestId to set
	 */
	public void setConsignmentManifestId(Integer consignmentManifestId) {
		this.consignmentManifestId = consignmentManifestId;
	}

	
	/**
	 * @return the consignment
	 */
	public BcunConsignmentDO getConsignment() {
		return consignment;
	}

	/**
	 * @param consignment the consignment to set
	 */
	public void setConsignment(BcunConsignmentDO consignment) {
		this.consignment = consignment;
	}

	/**
	 * @return the manifestId
	 */
	public Integer getManifestId() {
		return manifestId;
	}

	/**
	 * @param manifestId the manifestId to set
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	/**
	 * @return the isConsgUpdated
	 */
	public Boolean getIsConsgUpdated() {
		return isConsgUpdated;
	}

	/**
	 * @param isConsgUpdated the isConsgUpdated to set
	 */
	public void setIsConsgUpdated(Boolean isConsgUpdated) {
		this.isConsgUpdated = isConsgUpdated;
	}
	
	
}
