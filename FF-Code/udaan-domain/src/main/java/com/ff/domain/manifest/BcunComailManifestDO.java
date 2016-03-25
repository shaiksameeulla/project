package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunComailManifestDO extends CGFactDO {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1485220773728716498L;
	
	/** The co mail manifest id. */
	private Integer bcunCoMailManifestId;
	
	/** The comail do. */
	private ComailDO comailDO;
	
	/** The manifest DO. */
	private ManifestDO manifestDO;
	
	/** The position. */
	private Integer position;

	/**
	 * @return the bcunCoMailManifestId
	 */
	public Integer getBcunCoMailManifestId() {
		return bcunCoMailManifestId;
	}

	/**
	 * @param bcunCoMailManifestId the bcunCoMailManifestId to set
	 */
	public void setBcunCoMailManifestId(Integer bcunCoMailManifestId) {
		this.bcunCoMailManifestId = bcunCoMailManifestId;
	}

	/**
	 * @return the comailDO
	 */
	public ComailDO getComailDO() {
		return comailDO;
	}

	/**
	 * @param comailDO the comailDO to set
	 */
	public void setComailDO(ComailDO comailDO) {
		this.comailDO = comailDO;
	}

	/**
	 * @return the manifestDO
	 */
	public ManifestDO getManifestDO() {
		return manifestDO;
	}

	/**
	 * @param manifestDO the manifestDO to set
	 */
	public void setManifestDO(ManifestDO manifestDO) {
		this.manifestDO = manifestDO;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
	
}
