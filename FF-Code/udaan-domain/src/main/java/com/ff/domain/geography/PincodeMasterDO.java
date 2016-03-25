package com.ff.domain.geography;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.BranchPincodeMasterServiceabilityDO;
import com.ff.domain.serviceOffering.PaperworksPincodeMasterMapDO;

public class PincodeMasterDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 646706858979810788L;
	private Integer pincodeId;
	private String pincode;
	private Integer cityId;
	private String location;
	private String status;

	// added by Niharika
	Set<BranchPincodeMasterServiceabilityDO> branchPincodeServiceables;
	Set<PaperworksPincodeMasterMapDO> paperWorkPincodes;
	Set<PincodeMasterProductServiceabilityDO> productPincodeServiceables;

	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return the branchPincodeServiceables
	 */
	public Set<BranchPincodeMasterServiceabilityDO> getBranchPincodeServiceables() {
		return branchPincodeServiceables;
	}

	/**
	 * @param branchPincodeServiceables
	 *            the branchPincodeServiceables to set
	 */
	public void setBranchPincodeServiceables(
			Set<BranchPincodeMasterServiceabilityDO> branchPincodeServiceables) {
		this.branchPincodeServiceables = branchPincodeServiceables;
	}

	/**
	 * @return the paperWorkPincodes
	 */
	public Set<PaperworksPincodeMasterMapDO> getPaperWorkPincodes() {
		return paperWorkPincodes;
	}

	/**
	 * @param paperWorkPincodes
	 *            the paperWorkPincodes to set
	 */
	public void setPaperWorkPincodes(
			Set<PaperworksPincodeMasterMapDO> paperWorkPincodes) {
		this.paperWorkPincodes = paperWorkPincodes;
	}

	/**
	 * @return the productPincodeServiceables
	 */
	public Set<PincodeMasterProductServiceabilityDO> getProductPincodeServiceables() {
		return productPincodeServiceables;
	}

	/**
	 * @param productPincodeServiceables
	 *            the productPincodeServiceables to set
	 */
	public void setProductPincodeServiceables(
			Set<PincodeMasterProductServiceabilityDO> productPincodeServiceables) {
		this.productPincodeServiceables = productPincodeServiceables;
	}

}
