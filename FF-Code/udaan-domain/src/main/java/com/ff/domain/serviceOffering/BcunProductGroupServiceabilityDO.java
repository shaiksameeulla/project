package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunProductGroupServiceabilityDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5133970314256608469L;
	/**
	 * 
	 */

	private Integer prodGroupId;
	private String progGroupCode;
	private String prodGroupName;
	private String status = "A";
	private String isOriginUndefined = "N";

	/**
	 * @return the prodGroupId
	 */
	public Integer getProdGroupId() {
		return prodGroupId;
	}

	/**
	 * @param prodGroupId
	 *            the prodGroupId to set
	 */
	public void setProdGroupId(Integer prodGroupId) {
		this.prodGroupId = prodGroupId;
	}

	/**
	 * @return the progGroupCode
	 */
	public String getProgGroupCode() {
		return progGroupCode;
	}

	/**
	 * @param progGroupCode
	 *            the progGroupCode to set
	 */
	public void setProgGroupCode(String progGroupCode) {
		this.progGroupCode = progGroupCode;
	}

	/**
	 * @return the prodGroupName
	 */
	public String getProdGroupName() {
		return prodGroupName;
	}

	/**
	 * @param prodGroupName
	 *            the prodGroupName to set
	 */
	public void setProdGroupName(String prodGroupName) {
		this.prodGroupName = prodGroupName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the isOriginUndefined
	 */
	public String getIsOriginUndefined() {
		return isOriginUndefined;
	}

	/**
	 * @param isOriginUndefined the isOriginUndefined to set
	 */
	public void setIsOriginUndefined(String isOriginUndefined) {
		this.isOriginUndefined = isOriginUndefined;
	}
	
}
