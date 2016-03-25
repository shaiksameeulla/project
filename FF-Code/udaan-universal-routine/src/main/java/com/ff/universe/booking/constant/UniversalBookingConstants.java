package com.ff.universe.booking.constant;

/**
 * @author uchauhan
 * 
 */
public interface UniversalBookingConstants {

	// Query Constants
	String GET_CONTENT_VALUES = "getContentValues";
	String GET_INSURED_BY = "getInsuredBy";
	String GET_INSURENCE_CONFIG_DTLS = "getInsurenceConfigDtls";
	String PARAM_DECLARED_VALUE = "declaredvalue";
	String GET_PAPER_WORKS = "getPaperWorks";
	String GET_PAPER_WORK_BY_PIN = "getPaperWorkByPincode";
	String QRY_GET_BOOKING_PRODUCT_MAPPINGS = "getBookingProductMap";
	String QRY_GET_PRIVILEGE_CARD_TRANS_DTLS = "getPrivilegeCardTransDtls";
	String GET_CONSIGNMENT_TYPE_CONFIG = "getConsignmentTypeConfig";
	String QRY_IS_PRODUCT_SERVICED_BY_BOOKING = "isProductServicedByBooking";
	String QRY_IS_NORMAL_PRODUCT_SERVICED_BY_BOOKING = "isNormalProductServicedByBooking";
	String QRY_GET_BOOKING_TYPE_BY_CONSG_NUMBER = "getBookingTypeByConsgNumber";
	String QRY_GET_CONSIGNMENT_DETAILS = "getConsignmentDetails";
	String QRY_GET_CONSIGNMENT_ID_BY_CONSG_NO = "getConsignmentIdByConsgNo";
	String QRY_GET_CHILD_CONSIGNMENT_ID_BY_CONSG_NO = "getChildConsignmentId";
	String QRY_UPDATE_CONSIGNMENT_STATUS = "updateConsignmentStatus";
	String QRY_GET_BOOKED_CONSIGNMENTS_BY_DATE_RANGE = "getBookedConsignmentsByDateRange";
	String QRY_GET_BOOKED_TRANSFERRED_CONSIGNMENTS_BY_DATE_RANGE = "getBookedTransferredConsignmentsByDateRange";
	String QRY_IS_CONSGNO_MANIFESTED_FOR_BOOKING = "isConsgNoManifestedForBooking";
	String QRY_GET_CONSG_DTLS_BY_BOOKING_REF_NO = "getConsgDtlsByBookingRefNo";

	// Params
	String PARAM_BOOKING_TYPE = "bookingType";
	String PARAM_CONSG_SERIES = "consgSeries";
	String PARAM_PRIVILEGE_CARD_NO = "privilegeCardNo";
	String QRY_IS_CN_BOOKED = "isConsgBooked";
	String QRY_PARAM_CONSG = "consgNumber";
	String QRY_PARAM_CONSG1 = "consg.consgNo";
	String QRY_PARAM_CONSG2 = "consgNo";
	String QRY_PARAM_PROD_CODE = "productCode";
	String QRY_PARAM_CONSIGNMENT_STATUS = "consignmentStatus";
	String QRY_PARAM_PROCESS_CODE = "processCode";
	String QRY_PARAM_CONSIGNMENT_NOS = "consignmentNos";
	String PARAM_START_DATE = "startDate";
	String PARAM_END_DATE = "endDate";
	String PARAM_CUSTOMER_ID = "customerId";
	String QRY_PARAM_BOOKING_REF_NO = "bookingRefNo";

	String INSURED_BY_CUSTOMER = "Customer";
	String INSURED_BY_FIRST_FLIGHT = "FFCL";
	
	String QRY_EMAIL_FOC_BOOKING_DETAILS_BY_DATE = "getFocBookingDetailsByDate";
	
	// FOC EMAIL
	String GET_FOC_BOOKING_EMAIL_SUBJECT = "Weekly data FOC Bookings";
	String GET_FOC_BOOKING_EMAIL_TEMPLATE_NAME = "focBookingEmail.vm";
	
	String QRY_GET_CONSIGNEE_MOBILE_NUMBER="getConsigneeMobileNumber";
	String QRY_GET_CONSIGNOR_MOBILE_NUMBER="getConsignorMobileNumber";
	
	String CASH_DISCOUNT_EMAIL_TO = "cashDiscountEmailTO";
	String FOC_BOOKING_EMAIL_TO = "focBookingEmailTO";
	
	String CASH_BOOKING_DISCOUNT_SUBJECT = "Cash Booking Discount";
	
	String REQUEST_PARAM_IS_WEIGHING_MACHINE_CONNECTED = "isWeighingMachineConnected";
	
	String QRY_UPDATE_CONFIG_PARAM_VALUE_BY_PARAM_NAME = "updateConfigurableParamValueByParamName";
}
