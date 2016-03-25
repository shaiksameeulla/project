package com.ff.admin.mec.common.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;

public interface MECCommonConstants {

	/* Common Constants */
	String TX_NUMBER = "Transaction Number";
	String STATUS_OPENED = MECUniversalConstants.STATUS_OPENED;
	String STATUS_SAVE = "O";
	String STATUS_SUBMITTED = MECUniversalConstants.STATUS_SUBMITTED;
	String STATUS_VALIDATED = MECUniversalConstants.STATUS_VALIDATED;
	String TX_CODE_EX = MECUniversalConstants.TX_CODE_EX;
	String TX_CODE_BC = MECUniversalConstants.TX_CODE_BC;
	String TX_CODE_CC = MECUniversalConstants.TX_CODE_CC;
	String TX_CODE_LP = MECUniversalConstants.TX_CODE_LP;
	String ONE = "1";
	String DOUBLE_ZERO = "0.0";
	String REASON_TYPE_MEC = "MEC";
	String REASONS_LIST = "reasonsList";
	String REASON_LIST = "reasonList";
	String MODES_LIST = "modesList";
	String CN_FOR_LIST = "cnForList";

	String OFFICE = "Office";
	String CONSIGNMENT = "Consignment";
	String EMPLOYEE = "Employee";
	String TODAYS_DATE = "todaysDate";
	String REGIONTOs = "regionTOs";
	String IS_OCTROI_GL = "isOctroiGL";

	/* expense constants */
	String EXPENSE = "Expense";

	String EXPENSE_OFFICE = "expenseOffice";
	String EXPENSE_EMPLOYEE = "expenseEmployee";
	String EXPENSE_CONSIGNMENT = "expenseConsignment";
	String VALIDATE_EXPENSE = "validateExpense";
	String STD_TYPE_EXPENSE_FOR = "EXPENSE_FOR";

	String SAVED = "Saved";
	String UPDATED = "Updated";
	String SUBMITTED = "Submitted";
	String VALIDATED = "Validated";

	String EXPENSE_URL = "expenseUrl";
	String CHQ = CommonConstants.PAYMENT_MODE_CODE_CHEQUE;
	String CASH = CommonConstants.PAYMENT_MODE_CODE_CASH;
	String MEC_PAYMENT_MODE = "DD";

	String EX_FOR_OFFICE = "O";
	String EX_FOR_EMP = "E";
	String EX_FOR_CN = "C";

	String COLLECTION_FOR_FFCL = "F";
	String COLLECTION_FOR_CUSTOMER = "C";

	String VALIDATE_EXPENSE_OFFICE = "validateExpenseOffice";
	String VALIDATE_EXPENSE_EMPLOYEE = "validateExpenseEmployee";
	String VALIDATE_EXPENSE_CONSIGNMENT = "validateExpenseConsignment";

	/* Error Constants */
	String DTLS_SAVED_SUCCESS = "MEC001";
	String DTLS_NOT_SAVED = "MEC002";
	String NO_TX_NO_FOUND_FOR_BRANCH = "MEC003";
	String MEC_ONLY_ALLOWED_AT_BRANCH_OR_HUB = "MECC001";
	String MEC_ONLY_ALLOWED_AT_RHO = "MECC002";
	String CN_EXP_DTLS_NOT_SAVE_COLLECTION_DTLS = "MECC003";
	String RATE_NOT_CALC_FOR_CN = "MEC004";
	String PETTY_CASH_REPORT_NOT_SAVED = "MEC005";
	String MEC_ONLY_ALLOWED_AT_RHO_OR_CORP = "MECC004";
	String TXNS_VALIDATE_SUCCESSFULLY = "MEC108";
	String TXNS_NOT_VALIDATED = "MEC109";
	String CHILD_CN_NOT_ALLOWED = "MEC110";
	String DTLS_NOT_FOUND_FOR_GIVEN_TXN = "MEC111";
	String BULK_COLLECTION_VALIDATION_ACCESS_RIGHTS = "MECC007";
	// MEC email body text message with date param
	String MEC_EMAIL_BODY_TEXT_MSG = "MSGMEC0001";
	String MEC_EMAIL_SIGNATURE = "MSGMEC0002";
	String MEC_EMAIL_DO_NOT_REPLAY = "MSGMEC0003";
	String MEC_EMAIL_SUBJECT = "MSGMEC0004";
	String MEC_EMAIL_SHEET_NAME = "MSGMEC0005";
	String MEC_EMAIL_XLS_TITLE = "MSGMEC0006";
	String MEC_EMAIL_AMOUNT_IN_WORD = "MSGMEC0007";

	/* query name */
	String QRY_GET_STOCK_STD_TYPE_BY_TYPE_NAME = "getStockStdTypeByTypeName";
	String QRY_GET_EXPENSE_DTLS_BY_TX_NO = "getExpenseDtlsByTxNo";
	String QRY_GET_LIABILITY_DETAILS = "getLiabilityEntries";
	String QRY_GET_ALL_DELIVERED_CN_DTLS_FOR_COLLECTION_DTLS = "getAllDeliveredConsgDtlsForCollectionDtls";
	String QRY_GET_ALL_DELIVERED_CN_DTLS_FOR_EXPENSE_DTLS = "getAllDeliveredConsgDtlsForExpenseDtls";

	// String QRY_GET_PAYMENT_ID_BY_GL_ID = "getPaymentIdByGLId";
	String QRY_GET_GL_DETAILS_BY_GL_ID = "getGLDetailsByGLId";
	String QRY_GET_TX_NOS_FOR_VALIDATE = "getTxNosForValidate";
	String QRY_GET_TX_NOS_BY_TX_NO_FOR_VALIDATE = "getTxNosByTxNoForValidate";
	String QRY_UPDATE_EXPENSE_STATUS_BY_EXPENSE_ID = "updateExpenseStatusByExpenseId";
	String QRY_VALIDATE_TXNS = "validateTxns";
	String QRY_UPDATE_PETTY_CASH_REPORT_ENTRY = "updatePettyCashReportEntry";

	/* query param */
	String PARAM_TYPE_NAME = "typeName";
	String PARAM_STATUS_OPENED = "STATUS_OPENED";
	String PARAM_STATUS_SUBMITTED = "STATUS_SUBMITTED";
	String PARAM_STATUS_VALIDATED = "STATUS_VALIDATED";
	String PARAM_TX_NUMBER = "txNumber";
	String PARAM_TXNS = "txNos";
	String PARAM_TX_CODE_EX = "TX_CODE_EX";
	String PARAM_TX_CODE_BC = "TX_CODE_BC";
	String PARAM_OFFICE_ID = MECUniversalConstants.PARAM_OFFICE_ID;
	String PARAM_CURR_DATE_TIME = "currentDateTime";
	String PARAM_UPDATED_BY = "updatedBy";

	String PARAM_EXPENSE_FOR_LIST = "expenseForList";
	String PARAM_EXPENSE_TYPE_LIST = "expenseTypeList";
	String PARAM_EXPENSE_MODE_LIST = "expenseModeList";
	String PARAM_BANK_LIST = "bankList";
	String PARAM_EX_FOR_OFFICE = "EX_FOR_OFFICE";
	String PARAM_EX_FOR_EMP = "EX_FOR_EMP";
	String PARAM_EX_FOR_CN = "EX_FOR_CN";
	String PARAM_EX_MODE_CHQ = "EX_MODE_CHQ";
	String PARAM_EX_MODE_CASH = "EX_MODE_CASH";
	String PARAM_EXPENSE_FOR = "expenseFor";
	String PARAM_EMPLOYEE_LIST = "employeeList";
	String PARAM_EMP_LIST = "empList";
	String PARAM_NO_OF_ROWS = "NO_OF_ROWS";
	String PARAM_EMP_DTLS = "empDtls";
	String PARAM_CN_DTLS = "cnDtls";

	String PARAM_REGION_ID = "regionId";
	String PARAM_CUST_ID = "custId";
	String PARAM_GL_ID = "glId";

	String PARAM_STATION_LIST = "stationList";
	String PARAM_OFFICE_LIST = "officeList";
	String PARAM_STATUS_LIST = "statusList";
	String PARAM_CITY_ID = "cityId";

	String PARAM_FROM_DATE = "fromDate";
	String PARAM_TO_DATE = "toDate";
	String PARAM_STATUS = "status";

	String PARAM_IS_VALIDATE = "IS_VALIDATE";
	String PARAM_IS_VALIDATE_YES = "IS_VALIDATE_YES";
	String PARAM_CR_NT_YES = "CR_NT_YES";
	String PARAM_IS_CR_NT = "IS_CR_NT";
	String PARAM_EXPENSE_ID = "expenseId";
	String PARAM_PARENT_TYPE = "parentType";
	String PARAM_TXN_NO = "txNumber";
	String PARAM_CONSG_ID = "consgId";
	String PARAM_DELIVERY_DATE = "deliveryDate";
	String PARAM_OCTROI_AMT = "octroiAmount";
	String PARAM_YES = "YES";

	String CURRENT_DATE = "currentDate";
	String NEXT_DATE = "nextDate";
	String PREV_DATE = "prevDate";
	String DELIVERY_STATUS = "deliveryStatus";
	String QRY_PARAM_DATE_OBJ = "paramDateObj";
	String PARAM_GL_CODE ="glCode";
	String PARAM_NATURE_OF_GL= "natureOfGl";
	String GL_CODE_FOR_INTERNATIONAL_CASH_SALES = "30102100";
	String PARAM_CLOSING_BALANCE = "closingBalance";
	String PARAM_UPDATED_DATE = "updatedDate";
	String PARAM_CLOSING_DATE = "closingDate";
	String GL_CODE_FOR_RHO_COLLECTION = "11104520";
	String GL_CODE_FOR_UPS_COP_COLLECTION = "10801106";
	String NATURE_OF_GL = "P";

	/* Collection Constants */

	String VIEW_BILL_COLLECTION_PAGE = "viewBillCollection";
	String STD_TYPE_COLLECTION_AGAINST = "COLLECTION_AGAINST";
	String BUSINESS_COMMON_SERVICE = "BusinessCommonService";
	String CUSTOMER_LIST = "customerList";
	String BILL_COLLECTION_SERVICE = "billCollectionService";
	String BILL_COLLECTION_TYPE = MECUniversalConstants.BILL_COLLECTION_TYPE;
	String TRANSACTION_NUMBER = "txnNo";
	String COLLECTION_TYPE = "collectionCategory";
	String COLL_AGAINST_BILL = MECUniversalConstants.COLL_AGAINST_BILL;
	String COLL_AGAINST_ON_ACCOUNT = MECUniversalConstants.COLL_AGAINST_ON_ACCOUNT;
	String COLL_AGAINST_CREDIT = MECUniversalConstants.COLL_AGAINST_CREDIT;
	String COLL_AGAINST_DEBIT = MECUniversalConstants.COLL_AGAINST_DEBIT;
	String COLL_AGAINST_OCTROI = MECUniversalConstants.COLL_AGAINST_OCTROI;
	String COLL_AGAINST_W = MECUniversalConstants.COLL_AGAINST_W;

	String QRY_GET_BILL_COLLECTION_DTLS = "getBillCollectionDtls";
	String QRY_GET_COLLECTION_DETAILS_BXN_NO = "getCollectionDtlsByTxnNo";
	String QRY_UPDATE_CN_DELIVERY_DATE = "updateCnDeliveryDate";
	String QRY_GET_COLLECTION_STATUS = "getCollectionStatus";
	String QRY_GET_LIABILITY_PAYMENT_MODE = "getLiabilityPaymentMode";
	String QRY_SEARCH_LIABILITY_TXN_NO = "searchLiabilityByTxn";
	String STD_TYPE_PAYMENT_MODE = "PAYMENT_MODE";
	String LIABILITY_PAYMENT_MODE = "liabilityPaymentMode";
	String COLLECTION_COMMON_SERVICE = "collectionCommonService";
	String SAVED_STATUS = "O";
	String SUBMITTED_STATUS = "S";
	String VALIDATED_STATUS = "V";
	String QRY_UPDATE_COLLECTION_STATUS = "updateCollectionStatus";
	String QRY_PARAM_SATUS = "status";
	String VIEW_CN_COLLECTION_PAGE = "viewCnCollection";
	String STD_TYPE_CN_TYPE = "CNFOR";
	String CN_COLLECTION_TYPE = MECUniversalConstants.CN_COLLECTION_TYPE;
	String VIEW_VALIDATE_COLLECTION_PAGE = "viewValidateCollection";
	String CITY_TOS = "cityTOs";
	String MEC_STATUS = "MEC_STATUS";
	String TRANS_STATUS = "tranStatus";
	String VIEW_VALIDATE_COLLECTION_ENTRY_DTLS = "validateBillCollectionEntry";
	String FROM_DATE = "fromDate";
	String TO_DATE = "toDate";
	String STATION_ID = "cityId";
	String OFFICE_ID = "officeId";
	String HEADER_STATUS = "status";
	String HEADER_TRANS_NO = "txnNo";
	String QRY_GET_COLLECTION_DTLS = "getCollectionDtlsForValidate";
	String COLLECTION_AGAINST = "collectionAgainst";
	String BILL_TYPE = "BILL_TYPE";
	String IS_CORRECTION = "IS_CORRECTION_YES";
	String YES = "Y";
	String TXN_CONTAINS = "-1";
	String IS_BANK_GL = "isBankGL";

	/* param */
	String PARAM_COLL_AGAINST_BILL = "COLL_AGAINST_BILL";

	String COLLECTION_TYPE_EXPENSE = MECUniversalConstants.COLLECTION_TYPE_EXPENSE;
	String COLLECTION_TYPE_COD = MECUniversalConstants.COLLECTION_TYPE_COD;
	String COLLECTION_TYPE_LC = MECUniversalConstants.COLLECTION_TYPE_LC;
	String COLLECTION_TYPE_TOPAY = MECUniversalConstants.COLLECTION_TYPE_TOPAY;
	String COLLECTION_TYPE_OCTROI = MECUniversalConstants.COLLECTION_TYPE_OCTROI;
	String PETTY_CASH_REPORT = MECUniversalConstants.PETTY_CASH_REPORT;
	String PETTY_CASH_REPORT_VIEWER = MECUniversalConstants.PETTY_CASH_REPORT_VIEWER;
	String COLLECTION_TYPE_MISC_EXP = "MISC_EXP";
	String PARAM_MAXIMUM_ALLOWABLE_DATE = "maximumAllowableDate";

	String MIN_HHMMSS = "00:00:00";
	String MAX_HHMMSS = "23:59:59";

	String REASON_NAME_COLLECTED = "COLLECTED";
	String REASON_NAME_PL = "PARTY LETTER";
	String PARAM_PL = "PARTY_LETTER";

	/** The qry param consg. */
	String QRY_PARAM_DATE = "currentDate";

	/** The qry get delivered cn details. */
	String QRY_GET_ALL_DELIVERED_CN_DTLS_BY_DATE = "getAllDeliverdConsgDtlsByDate";
	/** The qry param delivery status. */
	String QRY_PARAM_DELIVERY_STATUS = "deliveryStatus";
	/**
	 * The qry to get all previous day offices to calculate closing balance
	 * again for next days opening balance.
	 */
	String QRY_GET_ALL_PREV_DAY_OFFICES = "getAllPreviosDayOffices";
	/** The qry to get all collection offices of that day. */
	String QRY_GET_ALL_COLLECTION_OFFICES_OF_THAT_DAY = "getAllCollectionOfficesOfThatDay";
	/** The qry to get all expense offices of that day. */
	String QRY_GET_ALL_EXPENSE_OFFICES_OF_THAT_DAY = "getAllExpenseOfficesOfThatDay";
	/** The qry to get all booking offices of that day. */
	String QRY_GET_ALL_BOOKING_OFFICES_OF_THAT_DAY = "getAllBookingOfficesOfThatDay";
	/** The qry to get total expense of office of that day. */
	String QRY_GET_TOTAL_EXPENSE_OF_OFFICE_OF_THAT_DAY = "getTotalExpenseOfOfficeOfThatDay";
	/** The qry to get cash withdrawal bank amount of office of that day. */
	String QRY_GET_CASH_WITHDRAWAL_BANK_AMT_OF_THAT_DAY = "getCashWithdrawalBankAmtOfThatDay";
	/** The qry to get cash sales as collection of office of that day. */
	String QRY_GET_CASH_SALES_OF_THAT_DAY = "getCashSalesOfThatDay";
	/** The qry to get collection amount of office of that day. */
	String QRY_GET_COLLECTION_AMT_OF_THAT_DAY = "getCollectionAmtOfThatDay";
	/** The qry to get all GL description. */
	String QRY_GET_ALL_GL_DESC = "getAllGLDesc";
	/** The qry to get opening balance of the day. */
	String QRY_GET_OPENING_BALANCE_OF_THAT_DAY = "getOpeningBalanceOfThatDay";
	/** The qry to get expense deduction of that day for credit note */
	String QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY_CR_NOTE = "getExpenseDeductionOfThatDayCrNote";
	/** The qry to get expense deduction of that day */
	String QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY = "getExpenseDeductionOfThatDay";
	/** The qry to get collection deduction of that day */
	String QRY_GET_COLLECTION_DEDUCTION_OF_THAT_DAY = "getCollectionDeductionOfThatDay";
	/** The qry to get debtors collection of that day */
	String QRY_GET_DEBUTORS_COLLECTION_OF_THAT_DAY = "getDebutorsCollectionOfThatDay";

	String QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY_FOR_EXPENSE = "getExpenseDeductionOfThatDayForExpense";
	String QRY_GET_EXPENSE_DEDUCTION_OF_THAT_DAY_FOR_COLLECTION = "getExpenseDeductionOfThatDayForCollection";
	String QRY_GET_COLLECTION_DEDUCTION_OF_THAT_DAY_FOR_EXPENSE = "getCollectionDeductionOfThatDayForExpense";
	String QRY_GET_COLLECTION_DEDUCTION_OF_THAT_DAY_FOR_COLLECTION = "getCollectionDeductionOfThatDayForCollection";
	String QRY_GET_INTERNATIONAL_CASH_SALES_OF_THAT_DAY = "getInternationalCashSalesOfThatDay";
	String QRY_GET_ALL_BRANCH_AND_HUB_OFFICES = "getAllBranchAndHubOffices";
	String QRY_GET_RHO_CASH_SALES_OF_THAT_DAY = "getRhoCashSalesOfThatDay";
	String QRY_GET_UPS_COP_CASH_SALES_OF_THAT_DAY = "getUpsCopCashSalesOfThatDay";
	String QRY_UPDATE_CONSGS_CONSIDERED_FOR_PETTY_CASH = "updateConsgsConsideredForPettyCash";
	String QRY_GET_BOOKING_OFF_ID_AND_BOOKING_DATE_FOR_PETTY_CASH = "getOfficeIdAndBookingDateForPettyCash";
	String PARAM_WINDOW_SIZE_FOR_PETTY_CASH = "MAX_ALLOWED_NO_OF_DAYS_FOR_PETTY_CASH";

	/** The consg origin off id. */
	String CONSG_ORIGIN_OFF_ID = "orgOffId";
	/** The DELIVERY status delivered. */
	String DELIVERY_STATUS_DELIVERED = "D";
	String LIABILITY_DETAILS_LIST = "liabilityDetailsList";
	String LIABILITY_STATUS = "liabilityStatus";
	String REGION_NAME = "regionName";
	String PARAM_VAL_EXP_OFFICES = "validateExpOffices";

	// Outstanding Report
	String REPORT_NAME_LIST = "getReportsNameList";
	String OUTSTANDING_FOR_CUSTOMER = "C";
	String OUTSTANDING_FOR_PROFITCENTER = "P";
	String OUTSTANDING_FOR_ERROR = "Missing outstanding for";
	String BILL_UPTO_ERROR = "Unable to parse Bill Upto Date";
	String PAYMENT_UPTO_ERROR = "Unable to parse Payment Upto Date";
	String CONSG_BOOKING_TYPE_NORMAL = "B";
	String PARAM_COLL_BANK_LIST = "collectionBankList";
	String PARAM_COLL_BANK_LIST_LBL = "collectionBankListLabel";

	/** N-(negative) for credit note P-(positive) for rest */
	String POSITIVE_GL_NATURE = MECUniversalConstants.POSITIVE_GL_NATURE;
	String NEGATIVE_GL_NATURE = MECUniversalConstants.NEGATIVE_GL_NATURE;

	String PARAM_PAYMENT_MODE = "paymentMode";
	String PARAM_NATURE = "nature";
	String PARAM_BOOKING_TYPE = "bookingType";
	String PARAM_CONSG_SERIES = "consgSeries";
	String PARAM_COLLECTION_TYPE = "collectionType";

	String CASH_SALES_DOM = "DM";
	String CASH_SALES_PRIORITY = "PR";
	String CASH_SALES_AIR_CARGO = "AR";
	String CASH_SALES_TRAIN = "TR";

	String PARAM_RHO_BANK_GLS = "RHOBankGLs";
	String PARAM_PAYMENT_MODE_LIABILITY = "paymentModeLiability";
	String PARAM_LIABILITY_CUSTOMER = "liabilityCustomer";
	String PARAM_SELECT_REGION_ID = "selectedRegionId";
	String LIABILITY_MAX_PAGING_ROW_ALLOWED = "LIABILITY_MAX_PAGING_ROW_ALLOWED";
	String DEFAULT_TEN_LIABILITY_MAX_PAGING_ROW_ALLOWED = "10";
	String QRY_GET_SAVED_COLLECTION_DTLS_OF_THAT_DAY = "getSavedCollectionDtlsOfThatDay";
	String PARAM_PETTY_CASH_DATE = "pettyCashDate";
	String MSG_AUTO_SUBMIT = "SYSTEM - AUTO SUBMIT";
	String PARAM_TXN_LENGTH = "txNoLength";
	String PARAM_CLC_AGAINST = "collectionAgainst";
	Integer TXN_LENGTH = 12;
	String CLC_AGAINST_B = "B";

	String PARAM_CN_FOR_LC = "CN_FOR_LC";
	String PARAM_CN_FOR_COD = "CN_FOR_COD";
	String QRY_GET_COLLECTION_DTLS_FOR_RECALC = "getCollectionDtlsForRecalculation";
	String QRY_GET_COLLECTION_DETAILS_FOR_PETTY_CASH_CALCULATION = "getCollectionDtlsForPettyCashCalculation";
	String PARAM_IS_RECACL_REQ = "isRecalculationReq";
	String PARAM_COllECTION_IDS = "collectionIds";
	String QRY_UPDATE_COLLECTION_RECALC_FLAG = "updateCollectionRecalcFlag";
	String QRY_GET_PETTY_CASH_DTLS_BY_DATE = "getPettyCashDtlsByDate";
	String QRY_GET_OFFICES_FOR_UPDATION = "getOfficesForUpdation";
	String PARAM_IS_UPDATE_REQ = "isUpdateReq";
	String QRY_GET_CHQ_DEPOSIT_SLIP_DTLS = "getChequeDepositSlipDtls";
	String PARAM_REPORTING_RHO_ID = "reportingRHOId";

	// SAP out bound status New and Complete
	String SAP_OUTBOUND_STATUS_NEW = MECUniversalConstants.SAP_OUTBOUND_STATUS_NEW;
	String SAP_OUTBOUND_STATUS_CMPLT = MECUniversalConstants.SAP_OUTBOUND_STATUS_CMPLT;

	String PARAM_SAP_STATUS = "sapStatus";

	// MEC email constants
	String CHEQUE_DEPOSIT_SLIP = "CDS";
	String MSG_CHQ_DEPO_SLIP_MAIL = "Cheque Deposit Slip Mail";
	String PARAM_DATE = "Date";
	String QRY_GET_MISC_EXPENSE_OF_THAT_DAY = "getMiscellaneousExpenseOfThatDay";
	String QRY_GET_MISC_DEDUCTION_OF_THAT_DAY = "getMiscellaneousDeductionOfThatDay";
	String LIABILITY_PAGE_SESSION = "LIABILITY_PAGE_SESSION";
	
	String LIBILITY_ERROR_DATA_NOT_FOUND="MECC005";
	String LIBILITY_ERROR_DATA_LOST_SAVE="MECC006";
	
	String GET_TOTAL_PAID_LIABILITY_BY_CONSG = "getTotalPaidLiabilityByConsg";

	//Parameters for petty cash
	String PARAM_UPDATED_STATUS = "updatedStatus";
	String PARAM_CONSG_NO_LIST = "consgNumberList";
	String QRY_GET_NUMBER_OF_CLOSING_BALANCES_FOR_GIVEN_DATE = "getNumberOfClosingBalancesForGivenDate";
	String PARAM_NAME_CURRENT_ACTIVE_OFFICES_COUNT_FOR_PETTY_CASH = "CURRENT_ACTIVE_OFFICES_COUNT_FOR_PETTY_CASH";
	
	// Parameters for Bulk Collection Validation
	String QRY_GET_COLLECTION_DETAILS_FOR_BULK_VALIDATION = 
			"SELECT collection.COLLECTION_DATE as collectionDate,"+"\r\n"+
			"       booking.BOOKING_DATE as bookingDate,"+"\r\n"+
			"       collection.COLLECTION_ID as collectionId,"+"\r\n"+
			"       collection.TRANSACTION_NO as transactionNo,"+"\r\n"+
			"       consignment.CONSG_ID as consignmentId,"+"\r\n"+
			"       consignment.CONSG_NO as consignmentNo,"+"\r\n"+
			"       collection.TOTAL_COLLECTION_AMOUNT as totalCollectionAmount,"+"\r\n"+
			"       collection.STATUS as collectionStatus,"+"\r\n"+
			"       collection.COLLECTION_OFFICE_ID as collectionOfficeId,"+"\r\n"+
			"       collection.COLLECTION_CATEGORY as collectionCategory,"+"\r\n"+
			"       paymentMode.PAYMENT_TYPE as paymentType,"+"\r\n"+
			"       consignment.LC_AMT as lcAmount,"+"\r\n"+
			"       consignment.TOPAY_AMT as toPayAmount,"+"\r\n"+
			"       consignment.COD_AMT as codAmount"+"\r\n"+
			"FROM ff_f_consignment consignment"+"\r\n"+
			"INNER JOIN ff_f_booking booking ON consignment.CONSG_NO = booking.CONSG_NUMBER"+"\r\n"+
			"INNER JOIN ff_f_collection_entries collectionEntries ON collectionEntries.CONSIGNMENT_ID = consignment.CONSG_ID"+"\r\n"+
			"INNER JOIN ff_f_collection collection ON collectionEntries.COLLECTION_ID = collection.COLLECTION_ID"+"\r\n"+
			"INNER JOIN ff_d_payment_mode paymentMode FORCE INDEX (primary) ON collection.COLLECTION_MODE_ID = paymentMode.PAYMENT_ID"+"\r\n"+
			"WHERE"+"\r\n"+
			"consignment.CUSTOMER = :custId"+"\r\n"+
			"AND date(booking.BOOKING_DATE) BETWEEN :fromDate AND :toDate"+"\r\n"+
			"AND collection.STATUS = :status"+"\r\n"+
			"AND (paymentMode.PAYMENT_CODE = 'CA' OR paymentMode.PAYMENT_CODE = 'CHQ')"+"\r\n"+
			"ORDER BY booking.BOOKING_DATE,collection.COLLECTION_ID";
	
	String QRY_GET_COUNT_OF_COLLECTION_DETAILS_FOR_BULK_VALIDATION = 
			"SELECT count(collection.COLLECTION_ID)"+"\r\n"+
			"FROM ff_f_consignment consignment"+"\r\n"+
			"INNER JOIN ff_f_booking booking ON consignment.CONSG_NO = booking.CONSG_NUMBER"+"\r\n"+
			"INNER JOIN ff_f_collection_entries collectionEntries ON collectionEntries.CONSIGNMENT_ID = consignment.CONSG_ID"+"\r\n"+
			"INNER JOIN ff_f_collection collection ON collectionEntries.COLLECTION_ID = collection.COLLECTION_ID"+"\r\n"+
			"INNER JOIN ff_d_payment_mode paymentMode FORCE INDEX (primary) ON collection.COLLECTION_MODE_ID = paymentMode.PAYMENT_ID"+"\r\n"+
			"WHERE"+"\r\n"+
			"consignment.CUSTOMER = :custId"+"\r\n"+
			"AND date(booking.BOOKING_DATE) BETWEEN :fromDate AND :toDate"+"\r\n"+
			"AND collection.STATUS = :status"+"\r\n"+
			"AND (paymentMode.PAYMENT_CODE = 'CA' OR paymentMode.PAYMENT_CODE = 'CHQ')";
	
	String ACTION_FORWARD_VIEW_BULK_COLLECTION_VALIDATION = "viewBulkCollectionValidation";
	Integer FIRST_RESULT = 0;
	Integer NO_OF_RECORDS_PER_PAGE = 100;
	String PARAM_NAVIGATION_LABEL = "navigationLabel";
	String NAVIGATION_LABEL_NEXT = "N";
	String NAVIGATION_LABEL_LAST = "L";
	String NAVIGATION_LABEL_PREVIOUS = "P";
	String NAVIGATION_LABEL_FIRST = "F";
	String PARAM_PAGE_NUMBER = "pageNumber";
	String PARAM_PAYMENT_CODE = "paymentCode";
	String PAYMENT_CODE_CASH = "CA";
	String PAYMENT_CODE_CHEQUE = "CHQ";
	
}