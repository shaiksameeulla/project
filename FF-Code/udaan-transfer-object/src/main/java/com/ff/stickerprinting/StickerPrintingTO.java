package com.ff.stickerprinting;



import com.capgemini.lbs.framework.to.CGBaseTO;

// TODO: Auto-generated Javadoc
/**
 * The Class StickerPrintingTO.
 *
 * @author Kamal Hassan
 */
public class StickerPrintingTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer quantity;
	private String startSlNo;
	private String officeCode;
	private String date;
	private String time;
	private String consgNo;
	private String consigneeName;
	private String city;
	private String address;
	private String originCityCode;
	private String pinCode;
	public String getOriginCityCode() {
		return originCityCode;
	}
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getStartSlNo() {
		return startSlNo;
	}
	public void setStartSlNo(String startSlNo) {
		this.startSlNo = startSlNo;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
		
}