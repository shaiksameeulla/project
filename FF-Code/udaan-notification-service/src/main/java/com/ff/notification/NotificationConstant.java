package com.ff.notification;

public interface NotificationConstant {
	public static final int NOTIFICATION_TYPE_NON = -1;
	public static final int NOTIFICATION_TYPE_SMS = 0;
	public static final int NOTIFICATION_TYPE_EMAIL = 1;
	public static final int NOTIFICATION_TYPE_SMS_EMAIL = 2;
	public static final int NOTIFICATION_TYPE_CALL = 3;
	public static final String CONSIGNMENT_STATUS_DELIVERY = "D";
	public static final String CONSIGNMENT_STATUS_RTO_DELIVERY = "S";
	public static final String CONSIGNMENT_STATUS_PENDING = "P";
	public static final String NOTIFY_TO_CNR_CNE = "BOT";
	public static final String NOTIFY_TO_CNR = "CNR";
	public static final String NOTIFY_TO_CNE = "CNE";
	public static final String MANIFEST_DIRECTION_IN = "I";
	public static final String MANIFEST_DIRECTION_OUT = "O";
	public static final String CONSIGNMENT_OUT_FOR_DELIVERY = "O";
	public static final String RTO_STATUS = "R";
	public static final String BOOKING_STATUS = "B";
	public static final String MISROUT_STATUS = "H";
	public static final String MANIFEST_PROCESS_CODE_FOR_RTOH = "RTOH";
	public static final String MOBILE_NUMBER_START_DIGIT_NINE = "9";
	public static final String MOBILE_NUMBER_START_DIGIT_EIGHT = "8";
	public static final String MOBILE_NUMBER_START_DIGIT_SEVEN = "7";
}
