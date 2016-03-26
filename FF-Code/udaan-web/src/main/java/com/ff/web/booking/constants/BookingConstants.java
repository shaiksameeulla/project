package com.ff.web.booking.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * The Interface BookingConstants.
 */
public interface BookingConstants {

	/** The error. */
	String ERROR = "Y";
	
	/** The file type backdated. */
	String FILE_TYPE_BACKDATED = "backdated";
	
	/** The file type bulk. */
	String FILE_TYPE_BULK = "bulk";
	
	/** The update booking status. */
	String UPDATE_BOOKING_STATUS = "updateBookingStatus";
	
	/** The params. */
	String PARAMS = "bookingDate,bookingType";
	
	/** The standard booking type. */
	String STANDARD_BOOKING_TYPE = "BOOKING_STATUS";
	
	/** The qry get booking pref details. */
	String QRY_GET_BOOKING_PREF_DETAILS = "getBookingPrefDetails";
	
	/** The qry get eb bookings. */
	String QRY_GET_EB_BOOKINGS = "getEBBookings";
	
	/** The url view ba booking parcel. */
	String URL_VIEW_BA_BOOKING_PARCEL = "viewBABookingParcel";
	
	/** The emotional bond booking url. */
	String EMOTIONAL_BOND_BOOKING_URL = "emotionalBondBooking";
	
	/** The emotional bond booking no. */
	String EMOTIONAL_BOND_BOOKING_NO = "EMOTIONAL_BOND_BOOKING_NO";
	
	/** The emotional bond booking series. */
	String EMOTIONAL_BOND_BOOKING_SERIES = "E";
	
	/** The url cash booking dox. */
	String URL_CASH_BOOKING_DOX = "createCashBookingDox";
	
	/** The url cash booking parcel. */
	String URL_CASH_BOOKING_PARCEL = "createCashBookingParcel";
	
	/** The url view consignment booking. */
	String URL_VIEW_CONSIGNMENT_BOOKING = "viewConsignmentBooking";
	
	/** The url print consignment booking. */
	String URL_PRINT_CONSIGNMENT_BOOKING = "printViewEditConsignmentBooking";
	
	/** The url view ba booking dox. */
	String URL_VIEW_BA_BOOKING_DOX = "viewBABookingDox";
	
	/** The url view credit customer booking dox. */
	String URL_VIEW_CREDIT_CUSTOMER_BOOKING_DOX = "viewCreditCustomerBookingDox";
	
	/** The url view credit customer booking parcel. */
	String URL_VIEW_CREDIT_CUSTOMER_BOOKING_PARCEL = "viewCreditCustomerBookingParcel";
	
	/** The url view foc booking dox. */
	String URL_VIEW_FOC_BOOKING_DOX = "viewFOCBookingDox";
	
	/** The url view foc booking parcel. */
	String URL_VIEW_FOC_BOOKING_PARCEL = "viewFOCBookingParcel";
	
	/** The url print foc booking dox. */
	String URL_PRINT_FOC_BOOKING = "printFOCBooking";
	
	/** The url view backdated booking. */
	String URL_VIEW_BACKDATED_BOOKING = "viewBackdatedBooking";
	
	String URL_BA_BOOKING_PRINT ="printBABooking";
	
	/** The booking pickup process. */
	String BOOKING_PICKUP_PROCESS = "P";
	
	/** The booking normal process. */
	String BOOKING_NORMAL_PROCESS = "B";
	
	/** The cash booking. */
	String CASH_BOOKING = "CS";
	
	/** The ba booking. */
	String BA_BOOKING = "BA";
	
	/** The ccc booking. */
	String CCC_BOOKING = "CR";
	
	/** The emotional bond booking. */
	String EMOTIONAL_BOND_BOOKING = "EB";
	
	/** The foc booking. */
	String FOC_BOOKING = "FC";
	
	/** The bulk booking. */
	String BULK_BOOKING = "BK";
	
	/** The back dated booking. */
	String BACK_DATED_BOOKING = "BD";
	/*String CONSG_TYPE_DOX = "DOCUMENT";
	String CONSG_TYPE_PARCEL = "PARCEL";*/
	/** The booking type. */
	String BOOKING_TYPE = "bookingType";
	
	/** The privilege card status blocked. */
	String PRIVILEGE_CARD_STATUS_BLOCKED = "B";
	
	/** The privilege card status active. */
	String PRIVILEGE_CARD_STATUS_ACTIVE = "A";
	
	/** The payment mode privilege card. */
	String PAYMENT_MODE_PRIVILEGE_CARD = "PVC";

	// Queries
	/** The qry is cn booked. */
	String QRY_IS_CN_BOOKED = "isConsgBooked";
	
	/** The qry is child cn booked. */
	String QRY_IS_CHILD_CN_BOOKED = "isChildConsgBooked";
	
	/** The qry get booking type configs. */
	String QRY_GET_BOOKING_TYPE_CONFIGS = "getBookingTypeConfig";
	
	/** The qry get booking by process. */
	String QRY_GET_BOOKING_BY_PROCESS = "getBookingByProcess";
	
	/** The qry get pickup booking dtls. */
	String QRY_GET_PICKUP_BOOKING_DTLS = "getPickupBookingDtls";
	
	/** The qry update booking status. */
	String QRY_UPDATE_BOOKING_STATUS = "updateBookingStatus";
	
	/** The qry update booking cn status. */
	String QRY_UPDATE_BOOKING_CN_STATUS = "updateBookingCNStatus";

	// Params
	/** The qry param consg. */
	String QRY_PARAM_CONSG = "consgNumber";
	
	/** The max discount allowed cash booking. */
	Integer MAX_DISCOUNT_ALLOWED_CASH_BOOKING = 25;
	
	/** The booking priority product. */
	String BOOKING_PRIORITY_PRODUCT = "P";
	
	/** The booking eb product. */
	String BOOKING_EB_PRODUCT = "E";
	
	/** The booking cod product. */
	String BOOKING_COD_PRODUCT = "T";
	
	/** The booking cn status active. */
	String BOOKING_CN_STATUS_ACTIVE = "A";
	
	/** The booking cn status in active. */
	String BOOKING_CN_STATUS_IN_ACTIVE = "I";
	
	/** The process id. */
	String PROCESS_ID = "processId";
	
	/** The process code. */
	String PROCESS_CODE = "processCode";
	
	/** The status. */
	String STATUS = "cnStatus";

	/** The standard type get consg printing types. */
	String STANDARD_TYPE_GET_CONSG_PRINTING_TYPES = "CONSG_PRINTING_TYPE";
	
	/** The max back booking date allowed. */
	String MAX_BACK_BOOKING_DATE_ALLOWED = "MAX_BACKDATE_BOOKING_ALLOWED";
	
	String CASH_BOOK_SCREEN = "Create Cash Booking";
	String FOC_BOOK_SCREEN = "Create FOC Booking";
	
	String IS_BULK_SAVE = "Y";
	
	String CONSG_RATE_DTLS = "CONSG_RATE_DTLS";
	
	String BOOKING_CONTRACT_CUSTOMER_TYPE = CommonConstants.CUSTOMER_CODE_CREDIT
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_CREDIT_CARD
		+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS
		+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_COD
		+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_LC
		+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_GOVT_ENTITY
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_FRANCHISEE;
			
			

	String CUSTOMER_CODE_LIST = "customerMap";

	String AFTER_14 = "A-14:00";

	String BEFORE_14 = "B-14:00";

	String SUNDAY = "SUNDAY";
	
	String TILL_48_Hr = "S-TILL48HRS";
	

	String URL_PRINT_CASH_BOOKING = "printCashBooking";
	
	String CONSIGNMENT_NUMBER = "consignmentNumber";
	
	String INSURED_DTLS = "insuredDtls";
	
	String CN_CONTENT_TO = "cnContentTOs";
	
	String CONSIGNMENT_MODIFICATION_TO = "consignmentModificationTO";
	
	String CN_PAPER_WORK_TOS = "cnPaperWorksTOs";
	
	String PAYMENT_MODE_TOS = "paymentModeTOs";
	
	String UPDATE_STATUS = "updateStatus";
	
	String CONSIGNOR_TO = "consignorTO";
	
	String CONSIGNEE_TO = "consigneeTO";
	
	String BOOKING_CITY = "bookingCity";
	
	String DESTINATION_CITY = "destinationCity";
	
	String DATE_WEIGHT = "dateWeight";

	String STATE_ID = "stateId";

	String CURRENT_DATE = "currentDate";

	String CONSIGNEE = "CE";
	
	String CONSIGNOR = "CO";

	String QRY_IS_CONSG_BOOKED = "isConsginmentBooked";
}
