package com.ff.universe.business.constant;

public interface BusinessUniversalConstants {
	//Query Names
	String QRY_GET_PARTY_NAMES = "getPartyNamesFromPartyType";
	String QRY_GET_PARTY_CODE_BY_PARTY_ID = "getPartyNameByPartyId";

	String QRY_GET_CONSIGNEE_CONSIGNOR_DTLS="getConsigneeConsignorDtls";
	String QRY_GET_CONSIGNOR_DTLS="getConsignorDtls";
	String QRY_GET_CUSTOMERS_BY_OFFICE_ID = "getCustomersByOfficeId";
	//Query Params
	String CONSGINMENT_NUMBER="consgNumber";
	String PARAM_OFFICE_ID = "officeId";
	String QRY_GET_VENDORS_BY_SRVC_TYPE_AND_CITY = "getVendorsListByServiceTypeAndCity";
	String PARAM_VENDOR_TYPE_CODE="vendorTypeCode";
	String PARAM_CITY_ID="cityId";
	String QRY_GET_CUSTOMER_ID_BY_SHIPPED_TO_CODE = "getCustomerByShippedToCode";
	String QRY_PARAM_SHIPPEDTOCODE = "shippedToCode";
	String _TOP = "_TOP";
	String _BOTTOM = "_BOTTOM";
	
}
