package com.ff.geography;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ProductTO;

public class PincodeProductServiceabilityTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5649886953237374129L;
	private Integer pincodeDeliveryTimeMapId;
	private PincodeTO pincodeTO;
	private ProductTO productTO;
	private OfficeTO officeTO;
	private String deliveryTime;
	private String dlvTimeQualification;
	private Integer orgCity;

	public Integer getPincodeDeliveryTimeMapId() {
		return pincodeDeliveryTimeMapId;
	}

	public void setPincodeDeliveryTimeMapId(Integer pincodeDeliveryTimeMapId) {
		this.pincodeDeliveryTimeMapId = pincodeDeliveryTimeMapId;
	}

	public PincodeTO getPincodeTO() {
		return pincodeTO;
	}

	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}

	public ProductTO getProductTO() {
		return productTO;
	}

	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}

	public OfficeTO getOfficeTO() {
		return officeTO;
	}

	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDlvTimeQualification() {
		return dlvTimeQualification;
	}

	public void setDlvTimeQualification(String dlvTimeQualification) {
		this.dlvTimeQualification = dlvTimeQualification;
	}

	/**
	 * @return the orgCity
	 */
	public Integer getOrgCity() {
		return orgCity;
	}

	/**
	 * @param orgCity the orgCity to set
	 */
	public void setOrgCity(Integer orgCity) {
		this.orgCity = orgCity;
	}

}
