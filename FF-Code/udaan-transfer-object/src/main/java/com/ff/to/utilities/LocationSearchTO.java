package com.ff.to.utilities;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class LocationSearchTO extends CGBaseTO{
	
	private static final long serialVersionUID = 1L;
	private String enteredLocation;
	private String pincodeMapped;
	private String officeName;
	private String productMapped;
	
	
	public LocationSearchTO(String enteredLocation, String pincodeMapped,
			String officeName, String productMapped) {
		super();
		this.enteredLocation = enteredLocation;
		this.pincodeMapped = pincodeMapped;
		this.officeName = officeName;
		this.productMapped = productMapped;
	}
	public LocationSearchTO() {
		// TODO Auto-generated constructor stub
	}
	public String getEnteredLocation() {
		return enteredLocation;
	}
	public void setEnteredLocation(String enteredLocation) {
		this.enteredLocation = enteredLocation;
	}
	public String getPincodeMapped() {
		return pincodeMapped;
	}
	public void setPincodeMapped(String pincodeMapped) {
		this.pincodeMapped = pincodeMapped;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getProductMapped() {
		return productMapped;
	}
	public void setProductMapped(String productMapped) {
		this.productMapped = productMapped;
	}
	
	
}
