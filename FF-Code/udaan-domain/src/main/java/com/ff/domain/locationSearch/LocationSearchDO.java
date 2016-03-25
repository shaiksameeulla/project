package com.ff.domain.locationSearch;



import com.capgemini.lbs.framework.domain.CGFactDO;


public class LocationSearchDO extends CGFactDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8674506052837072698L;
	
	private String enteredLocation;
	private String pincodeMapped;
	private String officeName;
	private String productMapped;
	/**
	 * @return the enteredLocation
	 */
	public String getEnteredLocation() {
		return enteredLocation;
	}
	/**
	 * @param enteredLocation the enteredLocation to set
	 */
	public void setEnteredLocation(String enteredLocation) {
		this.enteredLocation = enteredLocation;
	}
	/**
	 * @return the pincodeMapped
	 */
	public String getPincodeMapped() {
		return pincodeMapped;
	}
	/**
	 * @param pincodeMapped the pincodeMapped to set
	 */
	public void setPincodeMapped(String pincodeMapped) {
		this.pincodeMapped = pincodeMapped;
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the productMapped
	 */
	public String getProductMapped() {
		return productMapped;
	}
	/**
	 * @param productMapped the productMapped to set
	 */
	public void setProductMapped(String productMapped) {
		this.productMapped = productMapped;
	}

	
	
}
