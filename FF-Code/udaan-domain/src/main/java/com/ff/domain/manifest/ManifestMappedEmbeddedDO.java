package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;


// TODO: Auto-generated Javadoc
/**
 * The Class ConsignmentManifestDO.
 */
public class ManifestMappedEmbeddedDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5196395309229414912L;

	/** The map id. */
	private Integer mapId;
	
	/** The manifestId. */
	@JsonBackReference
	private ManifestDO embeddedIn;
	
	/** The embedded Manifest Id. */
	private Integer manifestId;
	
	/** The position. */
	private Integer position;

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public ManifestDO getEmbeddedIn() {
		return embeddedIn;
	}

	public void setEmbeddedIn(ManifestDO embeddedIn) {
		this.embeddedIn = embeddedIn;
	}

	public Integer getManifestId() {
		return manifestId;
	}

	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	

		
}
