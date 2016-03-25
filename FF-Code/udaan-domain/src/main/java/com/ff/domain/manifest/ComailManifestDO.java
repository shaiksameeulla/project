package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ComailManifestDO.
 */
public class ComailManifestDO extends CGFactDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1485220773728716498L;
	
	/** The co mail manifest id. */
	private Integer coMailManifestId;
	
	/** The comail do. */
	private ComailDO comailDO;
	
	/** The manifest id. */
	private Integer manifestId;
	

	/**
	 * Gets the co mail manifest id.
	 *
	 * @return the co mail manifest id
	 */
	public Integer getCoMailManifestId() {
		return coMailManifestId;
	}
	
	/**
	 * Sets the co mail manifest id.
	 *
	 * @param coMailManifestId the new co mail manifest id
	 */
	public void setCoMailManifestId(Integer coMailManifestId) {
		this.coMailManifestId = coMailManifestId;
	}
	
	/**
	 * Gets the comail do.
	 *
	 * @return the comail do
	 */
	public ComailDO getComailDO() {
		return comailDO;
	}
	
	/**
	 * Sets the comail do.
	 *
	 * @param comailDO the new comail do
	 */
	public void setComailDO(ComailDO comailDO) {
		this.comailDO = comailDO;
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
	
}
