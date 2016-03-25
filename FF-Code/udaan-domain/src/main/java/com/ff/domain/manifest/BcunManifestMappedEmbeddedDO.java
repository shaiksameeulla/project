package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsignmentManifestDO.
 */
public class BcunManifestMappedEmbeddedDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5196395309229414912L;

	/** The map id. */
	private Integer mapId;

	private Integer manifestId;

	private BcunManifestDO embeddedManifestDO;

	/** The position. */
	private Integer position;
	private Boolean isManifestEmbeddedUpdated = Boolean.FALSE;

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}


	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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
	 * @return the embeddedManifestDO
	 */
	public BcunManifestDO getEmbeddedManifestDO() {
		return embeddedManifestDO;
	}

	/**
	 * @param embeddedManifestDO the embeddedManifestDO to set
	 */
	public void setEmbeddedManifestDO(BcunManifestDO embeddedManifestDO) {
		this.embeddedManifestDO = embeddedManifestDO;
	}

	/**
	 * @return the isManifestEmbeddedUpdated
	 */
	public Boolean getIsManifestEmbeddedUpdated() {
		return isManifestEmbeddedUpdated;
	}

	/**
	 * @param isManifestEmbeddedUpdated the isManifestEmbeddedUpdated to set
	 */
	public void setIsManifestEmbeddedUpdated(Boolean isManifestEmbeddedUpdated) {
		this.isManifestEmbeddedUpdated = isManifestEmbeddedUpdated;
	}

}
