package com.ff.universe.mec.constant;

import com.capgemini.lbs.framework.constants.CommonConstants;

public interface MECUniversalConstants {

	/* universal constants */
	String PROCESS_CODE_MEC = "MEC";
	String STATUS_DELIVERED = "D";
	String COLLECTION_TYPE_EXPENSE = "EXPENSE";
	String COLLECTION_TYPE_COD = "COD";
	String COLLECTION_TYPE_LC = "LC";
	String COLLECTION_TYPE_TOPAY = "TOPAY";
	String COLLECTION_TYPE_OCTROI = "OCTROI";
	String PETTY_CASH_REPORT = "pettyCashReport";
	String PETTY_CASH_REPORT_VIEWER = "pettyCashReportViewer";

	/* query param */
	String PARAM_BANK_ID = "bankId";
	String PARAM_REGION_ID = "regionId";
	String PARAM_CONSG_NO = "consgNo";
	String PARAM_STATUS = "status";
	String PARAM_IS_BANK_GL = "isBankGL";
	String PARAM_OFFICE_ID = "officeId";
	String PARAM_CUSTOMER_TYPE = "customerType";

	/* query name */
	String QRY_GET_BANK_DTLS_BY_ID = "getBankDtlsById";
	String QRY_GET_ALL_BANK_DTLS = "getAllBankDtls";
	String QRY_GET_ALL_BANK_GL_DTLS = "getAllBankGLDtls";
	String QRY_GET_ALL_BANK_GL_DTLS_BY_REGION = "getAllBankGLDtlsByRegion";
	String QRY_GET_GL_DTLS_BY_REGION_ID = "getGLDtlsByRegionId";
	String QRY_IS_CONSG_BOOKED_NOT_DELIVERED = "isConsgBookedNotDelivered";
	String QRY_GET_CONSIGNMENT_BY_CONSG_NO = "getConsignmentByConsgNo";
	String QRY_GET_COD_LC_CUSTOMERS = "getLiabilityCustomers";
	
	String QRY_GET_LIABILITY_CUSTOMERS_FOR_LIABILITY_BY_REGION = "getLiabilityCustomersForLiabilityByRegion";
	String QRY_GET_ALL_LIABILITY_CUSTOMERS_FOR_LIABILITY="getAllLiabilityCustomersForLiability";
	
	String QRY_GET_ALL_COD_LC_CUSTOMERS = "getAllLiabilityCustomers";
	/** The customer for bill collection created by SAP. */
	String QRY_GET_CUSTOMERS_FOR_BILL_COLLECTION = "getCustomersForBillCollection";
	String CONSIGNMENT_STATUS_RETURNED = "R";
	String QRY_GET_COLLECTION_DTLS_FROM_DELIVERY_DTLS = "getCollectionDtlsFromDeliveryDtls";
	String PARAM_CONSG_IDS = "consgIds";

	/** N-(negative) for credit note P-(positive) for rest */
	String POSITIVE_GL_NATURE = "P";
	String NEGATIVE_GL_NATURE = "N";

	String TX_CODE_EX = CommonConstants.TX_CODE_EX;
	String TX_CODE_BC =CommonConstants.TX_CODE_BC;
	String TX_CODE_CC = CommonConstants.TX_CODE_CC;
	String TX_CODE_LP = CommonConstants.TX_CODE_LP;

	/** Collection Category */
	String CN_COLLECTION_TYPE = "C";
	String BILL_COLLECTION_TYPE = "B";

	/** Status */
	String STATUS_OPENED = "O";
	String STATUS_SUBMITTED = "S";
	String STATUS_VALIDATED = "V";

	/** Collection For. */
	String COLLECTION_FOR_FFCL = "F";
	String COLLECTION_FOR_CUSTOMER = "C";

	/** Collection Against. */
	String COLL_AGAINST_BILL = "B";
	String COLL_AGAINST_ON_ACCOUNT = "O";
	String COLL_AGAINST_CREDIT = "C";
	String COLL_AGAINST_DEBIT = "D";
	String COLL_AGAINST_OCTROI = "T";
	String COLL_AGAINST_W = "W";
	String QRY_GET_LIABILITY_DTLS_FOR_SAP_LIABILITY_SCHEDULER = "getLiabilityEntryByConsigNoForSAPLiabilityScheduler";
	
	// Constants for SAP
	String QRY_PARAM_GET_MAX_DATA_COUNT = "getConfigurableValueForSAPIntegration";
	String SAP_OUTBOUND_STATUS_NEW = "N";
	String SAP_OUTBOUND_STATUS_CMPLT = "C";
	String PARAM_NAME = "paramName";
	String PARAM_VALUE = "paramValue";
}
