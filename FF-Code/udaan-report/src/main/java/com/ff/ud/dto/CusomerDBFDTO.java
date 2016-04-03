package com.ff.ud.dto;

public class CusomerDBFDTO {
	
	private String origin;
	private String bkDate;
	private String conNo;
	private String script;
	private String name;
	private String doorNo;
	private String street;
	private String area;
	private String pincode;
	private String phone;
	private String mobile;
	private String cneePhone;
	private String cneeLand;
	private String approBy;
	private String discount;
	private String webupd;
	private String filename;
	private String bkMode;
	
	public boolean assignValuesToDBFArray(Object[] dataArray,CusomerDBFDTO cusDTO){
		try{
			
			dataArray[0]= cusDTO.getOrigin();
			dataArray[1]= cusDTO.getBkDate();
			dataArray[2]= cusDTO.getConNo();
			dataArray[3]= cusDTO.getScript();
			dataArray[4]= cusDTO.getName();
			dataArray[5]= cusDTO.getDoorNo();
			dataArray[6]= cusDTO.getStreet();
			dataArray[7]= cusDTO.getArea();
			dataArray[8]= cusDTO.getPincode();
			dataArray[9]= cusDTO.getPhone();
			dataArray[10]= cusDTO.getMobile();
			dataArray[11]= cusDTO.getCneePhone();
			dataArray[12]= cusDTO.getCneeLand();
			dataArray[13]= cusDTO.getApproBy();
			dataArray[14]= cusDTO.getDiscount();
			dataArray[15]= cusDTO.getWebupd();
			dataArray[16]= cusDTO.getFilename();
			dataArray[17]= cusDTO.getBkMode();
			
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getBkDate() {
		return bkDate;
	}

	public void setBkDate(String bkDate) {
		this.bkDate = bkDate;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoorNo() {
		return doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCneePhone() {
		return cneePhone;
	}

	public void setCneePhone(String cneePhone) {
		this.cneePhone = cneePhone;
	}

	public String getCneeLand() {
		return cneeLand;
	}

	public void setCneeLand(String cneeLand) {
		this.cneeLand = cneeLand;
	}

	public String getApproBy() {
		return approBy;
	}

	public void setApproBy(String approBy) {
		this.approBy = approBy;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getWebupd() {
		return webupd;
	}

	public void setWebupd(String webupd) {
		this.webupd = webupd;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBkMode() {
		return bkMode;
	}

	public void setBkMode(String bkMode) {
		this.bkMode = bkMode;
	}
	
	

	
	
}
