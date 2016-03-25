package com.ff.manifest;

import com.ff.geography.RegionTO;

/**
 * The Class ManifestRegionTO.
 */
public class ManifestRegionTO {
	//possible values : PURE / TRANSHIPMENT
	/** The manifest type. */
	private String manifestType;
	
	/** The region to. */
	private RegionTO regionTO;
	
	/**
	 * Gets the manifest type.
	 *
	 * @return the manifest type
	 */
	public String getManifestType() {
		return manifestType;
	}
	
	/**
	 * Sets the manifest type.
	 *
	 * @param manifestType the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}
	
	/**
	 * Gets the region to.
	 *
	 * @return the region to
	 */
	public RegionTO getRegionTO() {
		return regionTO;
	}
	
	/**
	 * Sets the region to.
	 *
	 * @param regionTO the new region to
	 */
	public void setRegionTO(RegionTO regionTO) {
		this.regionTO = regionTO;
	}
}
