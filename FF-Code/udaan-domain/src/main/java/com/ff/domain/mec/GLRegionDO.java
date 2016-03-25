package com.ff.domain.mec;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.RegionDO;

/**
 * @author hkansagr
 */

public class GLRegionDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Integer glRegionId;
	
	/** FK with GLMasterDO i.e. ff_d_gl_master */
	private GLMasterDO glMasterDO;
	
	/** FK with RegionDO i.e. ff_d_region */
	private RegionDO regionDO;

	
	
	/**
	 * @return the glMasterDO
	 */
	public GLMasterDO getGlMasterDO() {
		return glMasterDO;
	}

	/**
	 * @param glMasterDO the glMasterDO to set
	 */
	public void setGlMasterDO(GLMasterDO glMasterDO) {
		this.glMasterDO = glMasterDO;
	}

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
	 * @return the regionDO
	 */
	public RegionDO getRegionDO() {
		return regionDO;
	}

	/**
	 * @param regionDO the regionDO to set
	 */
	public void setRegionDO(RegionDO regionDO) {
		this.regionDO = regionDO;
	}
	
}
