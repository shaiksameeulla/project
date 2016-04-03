package com.ff.ud.dto;

import com.ff.ud.utils.DateUtils;



public class BookingDetailsDTO implements Cloneable {
	private String ConsgNumber;
	private String CityCode;
	private String Pincode;
	private String ActualWeight;
	private String ConsgType;
	private String OfficeCode;
	private String BookingDate;
	private String BookingType;
	private String CustomerCode;
	private String LegacyCustomerCode;
	private String BusinessName;
	private String DeclaredValue;
	private String CodAmount;
	private String LcAmount;
	private String FinalSlabRate;
	private String FuelSurcharge;
	private String RiskSurcharge;
	private String ToPayCharge;
	private String CodCharges;
	private String ParcelHandlingCharge;
	private String AirportHandlingCharge;
	private String ServiceTax;
	private String EducationCess;
	private String HigherEducationCes;
	private String DocumentHandlingCharge;
	private String OtherOrSpecialCharges;
	private String Origin;
	
	public String getConsgNumber() {
		return ConsgNumber;
	}
	public void setConsgNumber(String consgNumber) {
		ConsgNumber = consgNumber;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public String getPincode() {
		return Pincode;
	}
	public void setPincode(String pincode) {
		Pincode = pincode;
	}
	public String getActualWeight() {
		return ActualWeight;
	}
	public void setActualWeight(String actualWeight) {
		ActualWeight = actualWeight;
	}
	public String getConsgType() {
		return ConsgType;
	}
	public void setConsgType(String consgType) {
		ConsgType = consgType;
	}
	public String getOfficeCode() {
		return OfficeCode;
	}
	public void setOfficeCode(String officeCode) {
		OfficeCode = officeCode;
	}
	public String getBookingDate() {
		return BookingDate;
	}
	public void setBookingDate(String bookingDate) {
		BookingDate = bookingDate;
	}
	public String getBookingType() {
		return BookingType;
	}
	public void setBookingType(String bookingType) {
		BookingType = bookingType;
	}
	public String getCustomerCode() {
		return CustomerCode;
	}
	public void setCustomerCode(String customerCode) {
		CustomerCode = customerCode;
	}
	public String getLegacyCustomerCode() {
		return LegacyCustomerCode;
	}
	public void setLegacyCustomerCode(String legacyCustomerCode) {
		LegacyCustomerCode = legacyCustomerCode;
	}
	public String getBusinessName() {
		return BusinessName;
	}
	public void setBusinessName(String businessName) {
		BusinessName = businessName;
	}
	public String getDeclaredValue() {
		return DeclaredValue;
	}
	public void setDeclaredValue(String declaredValue) {
		DeclaredValue = declaredValue;
	}
	public String getCodAmount() {
		return CodAmount;
	}
	public void setCodAmount(String codAmount) {
		CodAmount = codAmount;
	}
	public String getLcAmount() {
		return LcAmount;
	}
	public void setLcAmount(String lcAmount) {
		LcAmount = lcAmount;
	}
	public String getFinalSlabRate() {
		return FinalSlabRate;
	}
	public void setFinalSlabRate(String finalSlabRate) {
		FinalSlabRate = finalSlabRate;
	}
	public String getFuelSurcharge() {
		return FuelSurcharge;
	}
	public void setFuelSurcharge(String fuelSurcharge) {
		FuelSurcharge = fuelSurcharge;
	}
	public String getRiskSurcharge() {
		return RiskSurcharge;
	}
	public void setRiskSurcharge(String riskSurcharge) {
		RiskSurcharge = riskSurcharge;
	}
	public String getToPayCharge() {
		return ToPayCharge;
	}
	public void setToPayCharge(String toPayCharge) {
		ToPayCharge = toPayCharge;
	}
	public String getCodCharges() {
		return CodCharges;
	}
	public void setCodCharges(String codCharges) {
		CodCharges = codCharges;
	}
	public String getParcelHandlingCharge() {
		return ParcelHandlingCharge;
	}
	public void setParcelHandlingCharge(String parcelHandlingCharge) {
		ParcelHandlingCharge = parcelHandlingCharge;
	}
	public String getAirportHandlingCharge() {
		return AirportHandlingCharge;
	}
	public void setAirportHandlingCharge(String airportHandlingCharge) {
		AirportHandlingCharge = airportHandlingCharge;
	}
	public String getServiceTax() {
		return ServiceTax;
	}
	public void setServiceTax(String serviceTax) {
		ServiceTax = serviceTax;
	}
	public String getEducationCess() {
		return EducationCess;
	}
	public void setEducationCess(String educationCess) {
		EducationCess = educationCess;
	}
	public String getHigherEducationCes() {
		return HigherEducationCes;
	}
	public void setHigherEducationCes(String higherEducationCes) {
		HigherEducationCes = higherEducationCes;
	}
	
	
	
	public String getDocumentHandlingCharge() {
		return DocumentHandlingCharge;
	}
	public void setDocumentHandlingCharge(String documentHandlingCharge) {
		DocumentHandlingCharge = documentHandlingCharge;
	}
	public String getOtherOrSpecialCharges() {
		return OtherOrSpecialCharges;
	}
	public void setOtherOrSpecialCharges(String otherOrSpecialCharges) {
		OtherOrSpecialCharges = otherOrSpecialCharges;
	}
	
	
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public boolean assignValuesToDBFArray(Object[] dataArray,BookingDetailsDTO bookingDTO){
		try{
			 
			    dataArray[0]=bookingDTO.getConsgNumber();
			    dataArray[1]=bookingDTO.getCityCode();
			    dataArray[2]=bookingDTO.getPincode();
			    dataArray[3]=bookingDTO.getActualWeight();
			    dataArray[4]=bookingDTO.getConsgType();
			    dataArray[5]=bookingDTO.getOfficeCode();
			    if(null !=bookingDTO.getBookingDate() && ""!=bookingDTO.getBookingDate()){
				 dataArray[6]= DateUtils.getDateFromString(bookingDTO.getBookingDate(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
			    }else{
				 dataArray[6]=null;
			    }
				dataArray[7]=bookingDTO.getBookingType();
				dataArray[8]=bookingDTO.getCustomerCode();
				dataArray[9]=bookingDTO.getLegacyCustomerCode();
				dataArray[10]=bookingDTO.getBusinessName();
				dataArray[11]=bookingDTO.getDeclaredValue();
				dataArray[12]=bookingDTO.getCodAmount();
				dataArray[13]=bookingDTO.getLcAmount();
				dataArray[14]=bookingDTO.getFinalSlabRate();
				dataArray[15]=bookingDTO.getFuelSurcharge();
				dataArray[16]=bookingDTO.getRiskSurcharge();
				dataArray[17]=bookingDTO.getToPayCharge();
				dataArray[18]=bookingDTO.getCodCharges();
				dataArray[19]=bookingDTO.getParcelHandlingCharge();
				dataArray[20]=bookingDTO.getAirportHandlingCharge();
				dataArray[21]=bookingDTO.getServiceTax();
				dataArray[22]=bookingDTO.getEducationCess();
				dataArray[23]=bookingDTO.getHigherEducationCes();
				dataArray[24]=bookingDTO.getDocumentHandlingCharge();
				dataArray[25]=bookingDTO.getOtherOrSpecialCharges();
				dataArray[26]=bookingDTO.getOrigin();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
	}

	
	

	

}
