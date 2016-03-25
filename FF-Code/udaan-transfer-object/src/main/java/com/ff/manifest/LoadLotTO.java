package com.ff.manifest;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class LoadLotTO.
 */
public class LoadLotTO extends CGBaseTO{

	/** The load lot id. */
	private Integer loadLotId;
	
	/** The load no. */
	private Integer loadNo;
	
	/** The load desc. */
	private  String  loadDesc;
	
	/**
	 * Gets the load lot id.
	 *
	 * @return the load lot id
	 */
	public Integer getLoadLotId() {
		return loadLotId;
	}
	
	/**
	 * Sets the load lot id.
	 *
	 * @param loadLotId the new load lot id
	 */
	public void setLoadLotId(Integer loadLotId) {
		this.loadLotId = loadLotId;
	}
	
	/**
	 * Gets the load no.
	 *
	 * @return the load no
	 */
	public Integer getLoadNo() {
		return loadNo;
	}
	
	/**
	 * Sets the load no.
	 *
	 * @param loadNo the new load no
	 */
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}
	
	/**
	 * Gets the load desc.
	 *
	 * @return the load desc
	 */
	public String getLoadDesc() {
		return loadDesc;
	}
	
	/**
	 * Sets the load desc.
	 *
	 * @param loadDesc the new load desc
	 */
	public void setLoadDesc(String loadDesc) {
		this.loadDesc = loadDesc;
	}
	
	
}
