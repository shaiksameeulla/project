package basic.serialization.composition;

import java.io.Serializable;

public class Address implements Serializable,Cloneable{
	@Override
	public String toString() {
		return "Address [location=" + location + ", country=" + country + "]";
	}
	private String location;
	private static String country;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Address cloneObject() throws CloneNotSupportedException{
		Address clonEmp = (Address)this.clone();
		return clonEmp;
	}

}
