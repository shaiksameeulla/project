package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.OfficeDO;

// TODO: Auto-generated Javadoc
/**
 * The Class OutManifestDestinationDO.
 */
public class OutManifestDestinationDO extends CGFactDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 413749879570857945L;
	
	/** The out manifest destination id. */
	private Integer outManifestDestinationId;
	
	/** The office. */
	private OfficeDO office;
	
	/** The manifest id. */
	private Integer manifestId;

	private Boolean isOutManifestDestinationUpdated = Boolean.FALSE;
	/**
	 * Gets the out manifest destination id.
	 *
	 * @return the out manifest destination id
	 */
	public Integer getOutManifestDestinationId() {
		return outManifestDestinationId;
	}
	
	/**
	 * Sets the out manifest destination id.
	 *
	 * @param outManifestDestinationId the new out manifest destination id
	 */
	public void setOutManifestDestinationId(Integer outManifestDestinationId) {
		this.outManifestDestinationId = outManifestDestinationId;
	}	
	
	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer getManifestId() {
		return manifestId;
	}
	
	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}
	
	/**
	 * Gets the office.
	 *
	 * @return the office
	 */
	public OfficeDO getOffice() {
		return office;
	}
	
	/**
	 * Sets the office.
	 *
	 * @param office the new office
	 */
	public void setOffice(OfficeDO office) {
		this.office = office;
	}

	/**
	 * @return the isOutManifestDestinationUpdated
	 */
	public Boolean getIsOutManifestDestinationUpdated() {
		return isOutManifestDestinationUpdated;
	}

	/**
	 * @param isOutManifestDestinationUpdated the isOutManifestDestinationUpdated to set
	 */
	public void setIsOutManifestDestinationUpdated(
			Boolean isOutManifestDestinationUpdated) {
		this.isOutManifestDestinationUpdated = isOutManifestDestinationUpdated;
	}
	
	
	
	
}
