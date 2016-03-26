package com.ff.web.booking.constants;

/**
 * The Interface BookingErrorCodesConstants.
 */
public interface BookingErrorCodesConstants {

	/** The invalid cn. */
	String INVALID_CN = "B000";

	/** The invalid pincode. */
	String INVALID_PINCODE = "B001";

	/** The invalid not severceability. */
	String INVALID_NOT_SEVERCEABILITY = "B002";

	/** The consg booked. */
	String CONSG_BOOKED = "B003";

	/** The discount exceeded. */
	String DISCOUNT_EXCEEDED = "B004";

	/** The privilege card amt exceeded. */
	String PRIVILEGE_CARD_AMT_EXCEEDED = "B005";

	/** The invalid privilege card. */
	String INVALID_PRIVILEGE_CARD = "B006";

	/** The invalid privilege card blocked. */
	String INVALID_PRIVILEGE_CARD_BLOCKED = "B007";

	/** The invalid priority pincode severceability. */
	String INVALID_PRIORITY_PINCODE_SEVERCEABILITY = "B008";

	/** The invlid cn not issued. */
	String INVLID_CN_NOT_ISSUED = "B009";

	/** The declared value exceeded. */
	String DECLARED_VALUE_EXCEEDED = "B010";

	/** The product is not serviced by booking. */
	String PRODUCT_IS_NOT_SERVICED_BY_BOOKING = "B011";

	/** The invalid not issued to customer or branch. */
	String INVALID_NOT_ISSUED_TO_CUSTOMER_OR_BRANCH = "B012";

	/** The invalid not issued to customer pickup boy or branch. */
	String INVALID_NOT_ISSUED_TO_CUSTOMER_PICKUP_BOY_OR_BRANCH = "B013";

	/** The invalid not issued to ba. */
	String INVALID_NOT_ISSUED_TO_BA = "B014";

	/** The invalid not issued to customer. */
	String INVALID_NOT_ISSUED_TO_CUSTOMER = "B015";

	/** The invalid phone no. */
	String INVALID_PHONE_NO = "B016";

	/** The invalid mobile no. */
	String INVALID_MOBILE_NO = "B017";

	/** The invalid consignee consignor. */
	String INVALID_CONSIGNEE_CONSIGNOR = "B018";

	/** The invalid date. */
	String INVALID_DATE = "B019";

	/** The invalid consg does not exists. */
	String INVALID_CONSG_DOES_NOT_EXISTS = "B020";

	/** The invalid consg no format. */
	String INVALID_CONSG_NO_FORMAT = "B021";

	/** The invalid consg weight. */
	String INVALID_CONSG_WEIGHT = "B022";

	/** The invalid booking type. */
	String INVALID_BOOKING_TYPE = "B023";

	/** The invalid document. */
	String INVALID_DOCUMENT = "B024";

	/** The invalid paper work. */
	String INVALID_PAPER_WORK = "B025";

	/** The invalid ba. */
	String INVALID_BA = "B026";

	/** The invalid customer. */
	String INVALID_CUSTOMER = "B027";

	/** The invalid insured by. */
	String INVALID_INSURED_BY = "B028";

	/** The valid bookings. */
	String VALID_BOOKINGS = "B029";

	/** The valid invalid bookings. */
	String VALID_INVALID_BOOKINGS = "B030";

	/** The valid bookings updated. */
	String VALID_BOOKINGS_UPDATED = "B031";

	/** The invalid pincode not serviced by product. */
	String INVALID_PINCODE_NOT_SERVICED_BY_PRODUCT = "B032";
	
	String INVALID_ALT_PINCODE_NOT_SERVICED_BY_PRODUCT = "BLK032";

	/** The invalid cn pick up booking. */
	String INVALID_CN_PICK_UP_BOOKING = "BP0001";

	/** The pick up booking consg in use. */
	String PICK_UP_BOOKING_CONSG_IN_USE = "BP0002";

	/** The no records exists. */
	String NO_RECORDS_EXISTS = "EEEGEN001";

	String ERROR = "ERRBK001";
	String INVALID_BULK_BOOKINGS = "BBKERR001";

	String INVALID_CONTENT = "B033";

	String INVALID_LC_AMT = "B034";
	String INVALID_DECLARED_VALUE = "BK00035";
	String INVALID_DECLARED_VALUE_BULK = "B010";
	
	String BULK_ERROR = "Exception occurred. Bookings are not inserted.";

	String INVALID_COD_AMT = "BK033";
	String CN_NOT_BOOKED = "BK0001";
	String CN_BOOKED = "BK0002";

	String INVALID_POLICY_NO = "BK00036";

	String INVALID_CONSIGNEE_ADDR = "BK00037";

	String INVALID_CONSIGNEE_NAME = "BK00038";

	String LOGIN_CITY_PINCODE_NOT_FOUND = "BK00039";

	String INVALID_PHONE_OR_MOBILE_NUMBER = "BE001";

	String INVALID_ALTERNATE_PINCODE = "BK0040";

	String INVALID_PHONE_MOBILE_NO = "BK0041";
	String INVALID_PINCODE_OR_ALT_PINCODE = "BLK001";

	String RATE_NOT_CALCULATED = "BK0042";

	String CONSG_NO_WRONLY_ENTERED = "BK0043";

	String LC_AMT_NOT_REQUIRED = "BK0044";

	String COD_AMT_NOT_REQUIRED = "BK0045";

	String PAPER_WORK_NOT_REQUIRED = "BK0046";

	String INVALID_PRIORITY = "BK0047";

	String PRIORITY_SERVICE_NOT_REQUIRED = "BK0048";

	String INVALID_PRIORITY_SERVICE = "BK0049";

	String PRIORITY_MANDATORY = "BK0050";

	String INVALID_CONSIGNOR_MOBILE_NO = "BK0051";

	String INVALID_MOBILE_NO_CONSIGNOR ="BK0052";

	String INVALID_MOBILE_NO_CONSIGNEE = "BK0053";

	String INVALID_PHONE_NO_CONSIGNEE = "BK0054";

	String INVALID_PHONE_MOBILE_NO_CONSIGNEE = "BK0055";

	String INVALID_CONSIGNOR_FIRST_NAME = "BK0056";

	String INVALID_CONSIGNEE_FIRST_NAME = "BK0057";

	String INVLID_CN_NOT_ISSUED_PICK_UP_BOY = "BK0058";

	String INVALID_LC_BANK_NAME = "BK0059";

	String LC_BANK_NAME_NOT_REQUIRED = "BK0060";

	String INVALID_NO_OF_PIECES = "BK0061";

	String INVALID_NOT_ISSUED_TO_CUSTOMER_UPDATED_FOR_CUSTOMER = "BK0062";

	String ORIGIN_NOT_DEFINED_FOR_PRODUCT = "BK0063";

	String ORIGIN_NOT_DEFINED_FOR_PRODUCT_ALTERNATE_PINCODE =  "BK0064";

	String ALL_INVALID_BOOKINGS = "BK0065";

	String PREF_NOT_CONFIGURED =  "BK0066";

	String INVALID_PICKUP_CONSG = "BK0067";

	String RATE_RETURN_NULL_FOR_LC = "BK0068";

	String RATE_RETURN_NULL_FOR_COD = "BK0069";

	String BOOKING_RETURN_NULL_FOR_LC = "BK0070";

	String BOOKING_RETURN_NULL_FOR_COD = "BK0071";

	String UPDATED_PROCESS_SET_AS_PICKUP = "BK0072";

}
