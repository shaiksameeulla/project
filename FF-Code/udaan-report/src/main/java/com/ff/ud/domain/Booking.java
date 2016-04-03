package com.ff.ud.domain;



import com.ff.ud.utils.DateUtils;
import com.ff.ud.utils.StringUtils;




public class Booking {
	private String consg_number;
	private String city_code;
	private String pincode;
	private String actual_weight;
	private String consg_type;
	private String OFFICE_CODE;
	private String booking_date;
	private String BOOKING_TYPE;
	private String customer_code;
	private String LEGACY_CUSTOMER_CODE;
	private String BUSINESS_NAME;
	private String DECLARED_VALUE;
	private String COD_AMOUNT;
	private String LC_AMOUNT;
	private String FINAL_SLAB_RATE;
	private String FUEL_SURCHARGE;
	private String RISK_SURCHARGE;
	private String TO_PAY_CHARGE;
	private String COD_CHARGES;
	private String PARCEL_HANDLING_CHARGE;
	private String AIRPORT_HANDLING_CHARGE;
	private String SERVICE_TAX;
	private String EDUCATION_CESS;
	private String HIGHER_EDUCATION_CES;
	private String DOCUMENT_HANDLING_CHARGE;
	private String OTHER_OR_SPECIAL_CHARGES;
	private String origin;
	
	/**
	 * Returns true if successfully able to add the value to the array
	 * @param dataArray : <code>Object</code> having data to be add into the DBF file
	 * @param outgo : Object having data from DB
	 * @return
	 */
	public boolean assignValuesToDBFArray(Object[] dataArray,Booking booking){
		try{
			
			dataArray[0]=booking.getConsg_number();                                 //"M_CON_NO,C,25";
			dataArray[1]=booking.getCity_code();                                    //"ON_DES_CD,C,3";
			dataArray[2]=StringUtils.getDoubleValueFromString(booking.getPincode());                                      //"PINCODE,C,6";
			dataArray[3]=StringUtils.getDoubleValueFromString(booking.getActual_weight());                                //"WEIGHT,N,8,3";
			dataArray[4]=booking.getConsg_type();                                   //"CONS_TYPE,C,11";
			dataArray[5]=booking.getOFFICE_CODE();                                  //"U_BR_CD,C,20";
			if(null !=booking.getBooking_date() && ""!=booking.getBooking_date()){
					 dataArray[6]= DateUtils.getDateFromString(booking.getBooking_date(), DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);        //"BKING_DATE,D";
				    }else{
					 dataArray[6]=null;
				    }
			dataArray[7]=StringUtils.getDoubleValueFromString(booking.getBOOKING_TYPE());                                 //"M_PAY_MODE,N,11,0";
			dataArray[8]=booking.getCustomer_code();                                                                      //"U_CON_CD,C,10";
			dataArray[9]=booking.getLEGACY_CUSTOMER_CODE();                                                               //"M_CON_CD,C,20";
			dataArray[10]=booking.getBUSINESS_NAME();                                                                     //"M_CON_NAME,C,70";
			dataArray[11]=StringUtils.getDoubleValueFromString(booking.getDECLARED_VALUE());                              //"DECLARE,N,12,2";
			dataArray[12]=StringUtils.getDoubleValueFromString(booking.getCOD_AMOUNT());                                  //"CON_AMT,N,20,6";
			dataArray[13]=StringUtils.getDoubleValueFromString(booking.getLC_AMOUNT());                                   //"LC_AMOUNT,N,20,6";
			dataArray[14]=StringUtils.getDoubleValueFromString(booking.getFINAL_SLAB_RATE());                             //"M_REVEN,N,20,6";
			dataArray[15]=StringUtils.getDoubleValueFromString(booking.getFUEL_SURCHARGE());                              //"FUEL_CHRG,N,20,6";
			dataArray[16]=StringUtils.getDoubleValueFromString(booking.getRISK_SURCHARGE());                              //"INS_CHRG,N,20,6";
			dataArray[17]=StringUtils.getDoubleValueFromString(booking.getTO_PAY_CHARGE());                               //"TOCHRG,N,20,6";
			dataArray[18]=StringUtils.getDoubleValueFromString(booking.getCOD_CHARGES());                                 //"COD_CHRG,N,20,6";
			dataArray[19]=StringUtils.getDoubleValueFromString(booking.getPARCEL_HANDLING_CHARGE());                      //"PPX_CHRG,N,20,6";
			dataArray[20]=StringUtils.getDoubleValueFromString(booking.getAIRPORT_HANDLING_CHARGE());                     //"APT_CHRG,N,20,6";
			dataArray[21]=StringUtils.getDoubleValueFromString(booking.getSERVICE_TAX());                                 //"SERVICETAX,N,20,6";
			dataArray[22]=StringUtils.getDoubleValueFromString(booking.getEDUCATION_CESS());                              //"ED_CESS,N,20,6";
			dataArray[23]=StringUtils.getDoubleValueFromString(booking.getHIGHER_EDUCATION_CES());                        //"HED_CESS,N,20,6";
			dataArray[24]=StringUtils.getDoubleValueFromString(booking.getDOCUMENT_HANDLING_CHARGE());                    //"DOC_CHARS,N,20,6";
			dataArray[25]=StringUtils.getDoubleValueFromString(booking.getOTHER_OR_SPECIAL_CHARGES());                    //"OTHER_CHAR,N,20,6";
			dataArray[26]=booking.getOrigin();
			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}
	public String getConsg_number() {
		return consg_number;
	}
	public void setConsg_number(String consg_number) {
		this.consg_number = consg_number;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getActual_weight() {
		return actual_weight;
	}
	public void setActual_weight(String actual_weight) {
		this.actual_weight = actual_weight;
	}
	public String getConsg_type() {
		return consg_type;
	}
	public void setConsg_type(String consg_type) {
		this.consg_type = consg_type;
	}
	public String getOFFICE_CODE() {
		return OFFICE_CODE;
	}
	public void setOFFICE_CODE(String oFFICE_CODE) {
		OFFICE_CODE = oFFICE_CODE;
	}
	public String getBooking_date() {
		return booking_date;
	}
	public void setBooking_date(String booking_date) {
		this.booking_date = booking_date;
	}
	public String getBOOKING_TYPE() {
		return BOOKING_TYPE;
	}
	public void setBOOKING_TYPE(String bOOKING_TYPE) {
		BOOKING_TYPE = bOOKING_TYPE;
	}
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	public String getLEGACY_CUSTOMER_CODE() {
		return LEGACY_CUSTOMER_CODE;
	}
	public void setLEGACY_CUSTOMER_CODE(String lEGACY_CUSTOMER_CODE) {
		LEGACY_CUSTOMER_CODE = lEGACY_CUSTOMER_CODE;
	}
	public String getBUSINESS_NAME() {
		return BUSINESS_NAME;
	}
	public void setBUSINESS_NAME(String bUSINESS_NAME) {
		BUSINESS_NAME = bUSINESS_NAME;
	}
	public String getDECLARED_VALUE() {
		return DECLARED_VALUE;
	}
	public void setDECLARED_VALUE(String dECLARED_VALUE) {
		DECLARED_VALUE = dECLARED_VALUE;
	}
	public String getCOD_AMOUNT() {
		return COD_AMOUNT;
	}
	public void setCOD_AMOUNT(String cOD_AMOUNT) {
		COD_AMOUNT = cOD_AMOUNT;
	}
	public String getLC_AMOUNT() {
		return LC_AMOUNT;
	}
	public void setLC_AMOUNT(String lC_AMOUNT) {
		LC_AMOUNT = lC_AMOUNT;
	}
	public String getFINAL_SLAB_RATE() {
		return FINAL_SLAB_RATE;
	}
	public void setFINAL_SLAB_RATE(String fINAL_SLAB_RATE) {
		FINAL_SLAB_RATE = fINAL_SLAB_RATE;
	}
	public String getFUEL_SURCHARGE() {
		return FUEL_SURCHARGE;
	}
	public void setFUEL_SURCHARGE(String fUEL_SURCHARGE) {
		FUEL_SURCHARGE = fUEL_SURCHARGE;
	}
	public String getRISK_SURCHARGE() {
		return RISK_SURCHARGE;
	}
	public void setRISK_SURCHARGE(String rISK_SURCHARGE) {
		RISK_SURCHARGE = rISK_SURCHARGE;
	}
	public String getTO_PAY_CHARGE() {
		return TO_PAY_CHARGE;
	}
	public void setTO_PAY_CHARGE(String tO_PAY_CHARGE) {
		TO_PAY_CHARGE = tO_PAY_CHARGE;
	}
	public String getCOD_CHARGES() {
		return COD_CHARGES;
	}
	public void setCOD_CHARGES(String cOD_CHARGES) {
		COD_CHARGES = cOD_CHARGES;
	}
	public String getPARCEL_HANDLING_CHARGE() {
		return PARCEL_HANDLING_CHARGE;
	}
	public void setPARCEL_HANDLING_CHARGE(String pARCEL_HANDLING_CHARGE) {
		PARCEL_HANDLING_CHARGE = pARCEL_HANDLING_CHARGE;
	}
	public String getAIRPORT_HANDLING_CHARGE() {
		return AIRPORT_HANDLING_CHARGE;
	}
	public void setAIRPORT_HANDLING_CHARGE(String aIRPORT_HANDLING_CHARGE) {
		AIRPORT_HANDLING_CHARGE = aIRPORT_HANDLING_CHARGE;
	}
	public String getSERVICE_TAX() {
		return SERVICE_TAX;
	}
	public void setSERVICE_TAX(String sERVICE_TAX) {
		SERVICE_TAX = sERVICE_TAX;
	}
	public String getEDUCATION_CESS() {
		return EDUCATION_CESS;
	}
	public void setEDUCATION_CESS(String eDUCATION_CESS) {
		EDUCATION_CESS = eDUCATION_CESS;
	}
	public String getHIGHER_EDUCATION_CES() {
		return HIGHER_EDUCATION_CES;
	}
	public void setHIGHER_EDUCATION_CES(String hIGHER_EDUCATION_CES) {
		HIGHER_EDUCATION_CES = hIGHER_EDUCATION_CES;
	} 
	
	
	
	public String getDOCUMENT_HANDLING_CHARGE() {
		return DOCUMENT_HANDLING_CHARGE;
	}
	public void setDOCUMENT_HANDLING_CHARGE(String dOCUMENT_HANDLING_CHARGE) {
		DOCUMENT_HANDLING_CHARGE = dOCUMENT_HANDLING_CHARGE;
	}
	public String getOTHER_OR_SPECIAL_CHARGES() {
		return OTHER_OR_SPECIAL_CHARGES;
	}
	public void setOTHER_OR_SPECIAL_CHARGES(String oTHER_OR_SPECIAL_CHARGES) {
		OTHER_OR_SPECIAL_CHARGES = oTHER_OR_SPECIAL_CHARGES;
	}
	
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Override
	public String toString() {
		return "Booking [consg_number=" + consg_number + ", city_code="
				+ city_code + ", pincode=" + pincode + ", actual_weight="
				+ actual_weight + ", consg_type=" + consg_type
				+ ", OFFICE_CODE=" + OFFICE_CODE + ", booking_date="
				+ booking_date + ", BOOKING_TYPE=" + BOOKING_TYPE
				+ ", customer_code=" + customer_code
				+ ", LEGACY_CUSTOMER_CODE=" + LEGACY_CUSTOMER_CODE
				+ ", BUSINESS_NAME=" + BUSINESS_NAME + ", DECLARED_VALUE="
				+ DECLARED_VALUE + ", COD_AMOUNT=" + COD_AMOUNT
				+ ", LC_AMOUNT=" + LC_AMOUNT + ", FINAL_SLAB_RATE="
				+ FINAL_SLAB_RATE + ", FUEL_SURCHARGE=" + FUEL_SURCHARGE
				+ ", RISK_SURCHARGE=" + RISK_SURCHARGE + ", TO_PAY_CHARGE="
				+ TO_PAY_CHARGE + ", COD_CHARGES=" + COD_CHARGES
				+ ", PARCEL_HANDLING_CHARGE=" + PARCEL_HANDLING_CHARGE
				+ ", AIRPORT_HANDLING_CHARGE=" + AIRPORT_HANDLING_CHARGE
				+ ", SERVICE_TAX=" + SERVICE_TAX + ", EDUCATION_CESS="
				+ EDUCATION_CESS + ", HIGHER_EDUCATION_CES="
				+ HIGHER_EDUCATION_CES + ", DOCUMENT_HANDLING_CHARGE="
				+ DOCUMENT_HANDLING_CHARGE + ", OTHER_OR_SPECIAL_CHARGES="
				+ OTHER_OR_SPECIAL_CHARGES + ", origin=" + origin + "]";
	}
	
	
	
	
	
	
	
}
