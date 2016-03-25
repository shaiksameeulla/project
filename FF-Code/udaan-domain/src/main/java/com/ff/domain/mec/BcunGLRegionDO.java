package com.ff.domain.mec;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author hkansagr
 */

public class BcunGLRegionDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Integer glRegionId;
	
	/** FK with GLMasterDO i.e. ff_d_gl_master */
	private Integer glMasterId;
	
	/** FK with RegionDO i.e. ff_d_region */
	private Integer regionId;

	/**
	 * @return the glRegionId
	 */
	public Integer getGlRegionId() {
		return glRegionId;
	}

	/**
	 * @param glRegionId the glRegionId to set
	 */
	public void setGlRegionId(Integer glRegionId) {
		this.glRegionId = glRegionId;
	}

	/**
	 * @return the glMasterId
	 */
	public Integer getGlMasterId() {
		return glMasterId;
	}

	/**
	 * @param glMasterId the glMasterId to set
	 */
	public void setGlMasterId(Integer glMasterId) {
		this.glMasterId = glMasterId;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	
}
