package com.ff.universe.stockmanagement.constant;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * The Interface StockUniveralConstants.
 * 
 * @author mohammes
 */
public interface StockUniveralConstants {

	/** The qry series cancelled. */
	String QRY_SERIES_CANCELLED = "isSeriesCancelled";

	String QRY_SERIES_CANCELLED_FOR_INTEGRATION = "isLeafCancelledForIntegration";

	/** The qry series issued to fr. */
	String QRY_SERIES_ISSUED_TO_FR = "isSeriesIssuedToFR";

	/** The qry series issued to emp. */
	String QRY_SERIES_ISSUED_TO_EMP = "isSeriesIssuedToEmp";

	/** The qry series issued to customer. */
	String QRY_SERIES_ISSUED_TO_CUSTOMER = "isSeriesIssuedToCustomer";

	/** The qry series issued to office. */
	String QRY_SERIES_ISSUED_TO_OFFICE = "isSeriesIssuedToOffice";

	/** The qry series issued to ba. */
	String QRY_SERIES_ISSUED_TO_BA = "isSeriesIssuedToBA";

	/** The qry series already issued from branch. */
	String QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH = "isSeriesAlreadyIssued";

	/** The qry get item id and issue no. */
	String QRY_GET_ITEM_ID_AND_ISSUE_NO = "getItemIdAndIssueNo";

	/** The qry series already issued from branch exclude row. */
	String QRY_SERIES_ALREADY_ISSUED_FROM_BRANCH_EXCLUDE_ROW = "isSeriesAlreadyIssuedExcludeRowId";

	/** The qry series transferred to emp. */
	String QRY_SERIES_TRANSFERRED_TO_EMP = "isSeriesTransferredTOEmp";

	/** The qry series transferred to customer. */
	String QRY_SERIES_TRANSFERRED_TO_CUSTOMER = "isSeriesTransferredTOCust";

	/** The qry series transferred to office. */
	String QRY_SERIES_TRANSFERRED_TO_OFFICE = "isSeriesTransferredTOOffice";

	/** The qry series transferred to ba. */
	String QRY_SERIES_TRANSFERRED_TO_BA = "isSeriesTransferredTOBa";

	/** The qry series transferred from emp. */
	String QRY_SERIES_TRANSFERRED_FROM_EMP = "isSeriesTransferredFromEmp";

	/** The qry series transferred from customer. */
	String QRY_SERIES_TRANSFERRED_FROM_CUSTOMER = "isSeriesTransferredFromCust";

	/** The qry series transferred from ba. */
	String QRY_SERIES_TRANSFERRED_FROM_BA = "isSeriesTransferredFromBa";

	/** The qry series returned to plant. */
	String QRY_SERIES_RETURNED_TO_PLANT = "isSeriesReturnTOPlant";

	/** The qry series returned from plant. */
	String QRY_SERIES_RETURNED_FROM_PLANT = "isSeriesReturnFromPlant";

	/** The qry series issued with issue number. */
	String QRY_SERIES_ISSUED_WITH_ISSUE_NUMBER = "isSeriesIssuedWithIssueNumber";
	String QRY_IS_SERIES_RECEIVED_WITH_RECEIPT_NUMBER_BY_RANGE = "isSeriesReceivedWithReceiptNumberByRange";
	String QRY_IS_SERIES_RECEIVED_WITH_LATEST_RECEIPT_NUMBER_BY_RANGE = "isSeriesReceivedWithLatestReceiptNumberByRange";
	String QRY_IS_SERIES_RECEIVED_WITH_RECEIPT_NUMBER_WITH_DTLS_ID_BY_RANGE = "isSeriesReceivedWithReceiptNumberWithSameRowByRange";

	String QRY_SERIES_ISSUED_WITH_RECEIPT_NUMBER = "isSeriesReceivedWithAcknowledgementNumber";

	/** The qry series issued with issue number detls id. */
	String QRY_SERIES_ISSUED_WITH_ISSUE_NUMBER_DETLS_ID = "isSeriesIssuedWithIssueNumberAndItemDetailsId";

	/** The qry is series exist in office. */
	String QRY_IS_SERIES_EXIST_IN_OFFICE = "isSeriesExistInOffice";

	String QRY_SERIES_EXIST_IN_OFFICE_FOR_INTEGRATION = "isSeriesIssuedToOfficeForStockIntegration";

	String QRY_SERIES_EXIST_IN_REGION_FOR_INTEGRATION = "isSeriesIssuedToRegionForStockIntegration";

	/** The qry isseries exist at transferred from ba. */
	String QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_BA = "isSeriesExistAtTransferredFromBa";

	/** The qry isseries exist at transferred from customer. */
	String QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_CUSTOMER = "isSeriesExistAtTransferredFromCust";

	/** The qry isseries exist at transferred from emp. */
	String QRY_ISSERIES_EXIST_AT_TRANSFERRED_FROM_EMP = "isSeriesExistAtTransferredFromEmp";

	String QRY_ITEM_DTLS_FOR_STOCK_ISSUE_BY_SERIAL_NUMBER = "getItemDoForStockIssueBySerialNumber";

	String QRY_ITEM_DTLS_FOR_STOCK_RECEIPT_BY_SERIAL_NUMBER = "getItemDoForStockReceiptBySerialNumber";

	/** The qry param type id. */
	String QRY_PARAM_TYPE_ID = "partyTypeId";

	/** The qry param office code. */
	String QRY_PARAM_OFFICE_CODE = "officeCode";

	/** The qry param transaction status. */
	String QRY_PARAM_TRANSACTION_STATUS = "transactionStatus";

	/** The qry param start leaf. */
	String QRY_PARAM_START_LEAF = "startLeaf";

	/** The qry param end leaf. */
	String QRY_PARAM_END_LEAF = "endLeaf";

	/** The qry param item id. */
	String QRY_PARAM_ITEM_ID = "itemId";

	/** The qry param leaf. */
	String QRY_PARAM_LEAF = "leaf";

	/** The qry param quantity. */
	String QRY_PARAM_QUANTITY = "quantity";

	/** The qry param start series no. */
	String QRY_PARAM_START_SERIES_NO = "startSerialNumber";

	/** The qry param office id. */
	String QRY_PARAM_OFFICE_ID = "officeId";

	String QRY_PARAM_CUSTOMER_TYPE = "customerType";
	String QRY_PARAM_PICK_UP_TYPE = "pickupType";
	String QRY_PARAM_REQ_DETAILS_FOR_SAP = "getRequisitionDetailsForSAP";

	String QRY_PARAM_ISSUE_DETAILS_FOR_SAP = "getStockIssueDetailsForSAP";

	String QRY_PARAM_RECEIPT_DETAILS_FOR_SAP = "getStockReceiptDetailsForSAP";

	/** The transaction status. */
	String TRANSACTION_STATUS = "A";

	/** The stock item has series yes. */
	String STOCK_ITEM_HAS_SERIES_YES = "Y";

	/** The stock item has series no. */
	String STOCK_ITEM_HAS_SERIES_NO = "N";

	/** The return type return. */
	String RETURN_TYPE_RETURN = "RT";// Return

	/** The return type receipt. */
	String RETURN_TYPE_RECEIPT = "RCPT";// Receipt/acknowledgment

	/** The resp error. */
	String RESP_ERROR = FrameworkConstants.ERROR_FLAG;

	/** The resp success. */
	String RESP_SUCCESS = FrameworkConstants.SUCCESS_FLAG;

	/** The resp error msg. */
	String RESP_ERROR_MSG = " Data base connectivity/data issue";

	/** The qry param item details id. */
	String QRY_PARAM_ITEM_DETAILS_ID = "itemDetailsId";

	/** The qry stock issue date. */
	String QRY_STOCK_ISSUE_DATE = "getStockLatestIssueDate";

	/** The qry stock return from date. */
	String QRY_STOCK_RETURN_FROM_DATE = "getStockReturnFromDate";

	/** The qry stock return to date. */
	String QRY_STOCK_RETURN_TO_DATE = "getStockReturnTODate";
	String QRY_LATEST_STOCK_RETURN_DATE = "getLatestReturnDateByLeaf";

	/** The qry series transfer to office date. */
	String QRY_SERIES_TRANSFER_TO_OFFICE_DATE = "getStockLatestTransferTOOfficeDate";

	/** The qry series receipt date. */
	String QRY_SERIES_RECEIPT_DATE = "latestSeriesDateForReceipt";

	String QRY_SERIES_RECEIPT_DATE_WITHOUT_OFFICE = "latestSeriesDateForReceiptWithoutOffice";

	String QRY_LATEST_TRANSFER_DATE_BY_SERIES = "getStockLatestTransferDate";

	// Stock updation START*********************
	/** The qry stock increase office. */
	String QRY_STOCK_INCREASE_OFFICE = "stockIncreaseForOffice";

	/** The qry stock increase ba. */
	String QRY_STOCK_INCREASE_BA = "stockIncreaseForBA";

	/** The qry stock increase customer. */
	String QRY_STOCK_INCREASE_CUSTOMER = "stockIncreaseForCustomer";

	/** The qry stock increase emp. */
	String QRY_STOCK_INCREASE_EMP = "stockIncreaseForEmployee";

	/** The qry stock increase fr. */
	String QRY_STOCK_INCREASE_FR = "stockIncreaseForFranchisee";

	/** The qry stock decrease office. */
	String QRY_STOCK_DECREASE_OFFICE = "stockDecreaseForOffice";

	/** The qry stock decrease ba. */
	String QRY_STOCK_DECREASE_BA = "stockDecreaseForBA";

	/** The qry stock decrease customer. */
	String QRY_STOCK_DECREASE_CUSTOMER = "stockDecreaseForCustomer";

	/** The qry stock decrease emp. */
	String QRY_STOCK_DECREASE_EMP = "stockDecreaseForEmployee";

	/** The qry stock decrease fr. */
	String QRY_STOCK_DECREASE_FR = "stockDecreaseForFranchisee";

	/** The auto requisition remarks. */
	String AUTO_REQUISITION_REMARKS = "Auto Requisition";

	/** The series expired. */
	int SERIES_BA_EXPIRED = 180;
	int SERIES_FR_EXPIRED = 180;

	/** The get stock std type by type name map. */
	String GET_STOCK_STD_TYPE_BY_TYPE_NAME_MAP = "getStockStdTypeByTypeNameAsMap";

	/** The qry param type name. */
	String QRY_PARAM_TYPE_NAME = "typeName";

	/** The qry param trans date. */
	String QRY_PARAM_TRANS_DATE = "transDate";
	String QRY_PARAM_LOGGED_IN_DATE = "loggedInDate";

	/** The type id. */
	String TYPE_ID = "typeId";

	/** The type name. */
	String TYPE_NAME = "typeName";
	String QRY_LATEST_TRANSFER_DATE_BY_SERIAL_NUMBER = "getLatestTransferDateBySerialNumber";

	String QRY_LATEST_ISSUE_DATE_BY_SERIAL_NUMBER = "getLatestIssueDateBySerialNumber";
	String QRY_LATEST_RETURNED_DATE_BY_SERIAL_NUMBER = "getLatestReturnedDateBySerialNumber";
	String QRY_LATEST_RECEIPT_DATE_SERIAL_NUMBER = "getLatestReceiptDateBySerialNumber";

	String QRY_LATEST_TRANSFER_DETAILS_BY_SERIAL_NUMBER = "getLatestTransferDetailsBySerialNumber";
	// Stock updation END*********************

	/** The stock franchisee type. */
	String STOCK_FRANCHISEE_TYPE = CommonConstants.CUSTOMER_CODE_FRANCHISEE;

	/** The stock business associate type. */
	String STOCK_BUSINESS_ASSOCIATE_TYPE = CommonConstants.CUSTOMER_CODE_BA_PICKUP
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_BA_DELIVERY;

	/** The stock customer type. */
	@Deprecated
	String STOCK_CUSTOMER_TYPE = CommonConstants.CUSTOMER_CODE_CREDIT
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_CREDIT_CARD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_COD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_LC
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_ACC
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_GOVT_ENTITY;

	/** The stock contract customer type. */
	String STOCK_CONTRACT_CUSTOMER_TYPE = CommonConstants.CUSTOMER_CODE_CREDIT
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_CREDIT_CARD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_COD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_LC
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_GOVT_ENTITY;
	
	String STOCK_CONTRACT_CREDIT_CUSTOMER_TYPE = CommonConstants.CUSTOMER_CODE_CREDIT
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_CREDIT_CARD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_REVERSE_LOGISTICS
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_COD
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_LC
			+ FrameworkConstants.CHARACTER_COMMA
			+ CommonConstants.CUSTOMER_CODE_GOVT_ENTITY;
			

	/** The stock non contract customer type. */
	String STOCK_NON_CONTRACT_CUSTOMER_TYPE = CommonConstants.CUSTOMER_CODE_ACC;

	/** The qry get item typ as map. */
	String QRY_GET_ITEM_TYP_AS_MAP = "getItemTypAsMap";

	/** The qry param is active. */
	String QRY_PARAM_IS_ACTIVE = "isActive";

	/** The active status. */
	String ACTIVE_STATUS = StockUniveralConstants.TRANSACTION_STATUS;

	/** The qry param item type id. */
	String QRY_PARAM_ITEM_TYPE_ID = "itemTypeId";

	/** The qry get material by type as map. */
	String QRY_GET_MATERIAL_BY_TYPE_AS_MAP = "getMaterialByTypeAsMap";

	/** The qry get material by type and item id. */
	String QRY_GET_MATERIAL_BY_TYPE_AND_ITEM_ID = "getMaterialByTypeAndItemId";

	String STOCK_OPEN_STATUS = "OPEN";

	String STOCK_APPROVED_STATUS = "APPROVED";
	String STOCK_P_APPROVED_STATUS = "PAPPROVED";

	String QRY_IS_CONSG_BOOKED_FOR_STOCK = "isConsgBookedForStock";
	String QRY_IS_CONSG_BOOKED_FOR_STOCK_FOR_CHILD = "isConsgBookedForStockForChildCn";

	String QRY_GET_BA_RATE_CONFIG_BY_MATERIAL = "getBaRateConfigByMaterial";

	String STOCK_MODULE = "STOCK";

	// Stock Issue Payment Type

	/** The stock cash paymnt. */
	String STOCK_CASH_PAYMENT_TYPE = "CASH";

	/** The stock cheque payment. */
	String STOCK_CHEQUE_PAYMENT_TYPE = "CHQ";

	/** The stock dd paymnt. */
	String STOCK_DD_PAYMENT_TYPE = "DD";

	String QRY_STOCK_CONTRACT_CUSTOMERS = "getContractCustomerListForStock";

	String QRY_STOCK_CONTRACT_CUSTOMERS_FOR_RHO = "getRhoOfficeContractCustomerListForStock";
	
	String QRY_STOCK_CONTRACT_CUSTOMERS_FOR_HUB="getHubOfficeContractCustomerListForStock";

	String SHIPPED_CODE = "shippedCode";
	String CUSTOMER = "customer";

	/** The stock payment cash. */
	String STOCK_PAYMENT_CASH = "CASH";

	/** The stock payment dd. */
	String STOCK_PAYMENT_DD = "DD";

	/** The stock payment chq. */
	String STOCK_PAYMENT_CHQ = "CHQUE";

	// Stock Consolidation Constants

	/** The get SAP stock consolidation details. */
	String QRY_GET_SAP_STOCK_CONSOLIDATION_DTLS = "getSAPStockConsolidationDtls";

	/** The qry param for sap transfered status. */
	String QRY_PARAM_SAP_TRANSFER_STATUS = "sapTransferStatus";

	/** The stock consolidation sap transfered status. */
	String SAP_TRANSFER_STATUS = "T";
	
	String QRY_STOCK_HOLDER_DTLS_FOR_STOCK_ISSUE="getStockHolderFromStockIssue";
	String QRY_STOCK_HOLDER_DTLS_FOR_STOCK_TRANSFER="getStockHolderFromStockTransfer";
	String QRY_STOCK_HOLDER_DTLS_FOR_STOCK_RECEIPT="getStockHolderFromStockReceipt";
	String QRY_STOCK_HOLDER_DTLS_FOR_STOCK_RETURN="getStockHolderFromStockReturn";

}
