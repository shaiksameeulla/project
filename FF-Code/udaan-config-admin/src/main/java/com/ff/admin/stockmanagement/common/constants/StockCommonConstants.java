package com.ff.admin.stockmanagement.common.constants;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Interface StockCommonConstants.
 *
 * @author mohammes
 */
public interface StockCommonConstants {

	/* ###### Struts mapping Constants START#######*/
	/** The success forward. */
	String SUCCESS_FORWARD="success";
	
	String URL="url";
	
	
	/* ###### Struts mapping Constants END######*/
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/* ###### Hibernate mapping Constants START########*/
	
		/** The qry get active item types. */
	String QRY_GET_ACTIVE_ITEM_TYPES = "getActiveItemTypes";
		
		/** The qry param is active. */
		String QRY_PARAM_IS_ACTIVE = "isActive";
		
		/** The qry get material by type. */
		String QRY_GET_MATERIAL_BY_TYPE = "getMaterialByType";
		
		/** The qry param item type id. */
		String QRY_PARAM_ITEM_TYPE_ID = "itemTypeId";
		
		/** The qry get item typ as map. */
		String QRY_GET_ITEM_TYP_AS_MAP = "getItemTypAsMap";
		
		/** The qry get material by type as map. */
		String QRY_GET_MATERIAL_BY_TYPE_AS_MAP="getMaterialByTypeAsMap";
		
		/** The qry get material by type and item id. */
		String QRY_GET_MATERIAL_BY_TYPE_AND_ITEM_ID = "getMaterialByTypeAndItemId";
		
		/** The qry get material item id. */
		String QRY_GET_MATERIAL_ITEM_ID = "getMaterialByItemId";
		
		/** The qry param item id. */
		String QRY_PARAM_ITEM_ID="itemId";
		
		/** The qry find req dtls by req number. */
		String QRY_FIND_REQ_DTLS_BY_REQ_NUMBER="findReqDtlsByReqNumber";
		
		/** The qry find req dtls by req number approve. */
		String QRY_FIND_REQ_DTLS_BY_REQ_NUMBER_APPROVE="findReqDtlsByReqNumberForApprove";
		
		/** The qry find issue dtls by issue number. */
		String QRY_FIND_ISSUE_DTLS_BY_ISSUE_NUMBER="findIssueDtlsByIssueNumber";
		
		/** The qry find issue dtls by req number for issue. */
		String QRY_FIND_ISSUE_DTLS_BY_REQ_NUMBER_FOR_ISSUE="findReqDtlsByReqNumberForIssue";
		
		/** The qry find stock transfer dtls. */
		String QRY_FIND_STOCK_TRANSFER_DTLS ="findTransferDtlsByTransferNumber";
		
		String QRY_GET_REQ_DTLS_BY_REQUISITION_OFFICE ="searchReqDtlsByRequisitonOffice";
		
		String QRY_GET_ALL_REQ_BY_SUPPLYING_OFFICE ="searchAllReqDtlsBySupplyingOffice";
		
		/** The qry param req number. */
		String QRY_PARAM_REQ_NUMBER="requisitionNumber";
		
		/** The qry param transaction status. */
		String QRY_PARAM_TRANSACTION_STATUS="transactionStatus";
		
		/** The qry param officeid. */
		String QRY_PARAM_OFFICEID="officeId";
		
		/** The qry param logged in office id. */
		String QRY_PARAM_LOGGED_IN_OFFICE_ID="loggedInOfficeId";
		
		/** The qry get material dtls as map. */
		String QRY_GET_MATERIAL_DTLS_AS_MAP = "getMaterialDtlsAsMap";
		
		/** The get stock std type by type name. */
		String GET_STOCK_STD_TYPE_BY_TYPE_NAME = "getStockStdTypeByTypeName";
		
		/** The get stock std type by type name map. */
		String GET_STOCK_STD_TYPE_BY_TYPE_NAME_MAP = StockUniveralConstants.GET_STOCK_STD_TYPE_BY_TYPE_NAME_MAP;
		
		/** The qry find return dtls by issue number for return. */
		String QRY_FIND_RETURN_DTLS_BY_ISSUE_NUMBER_FOR_RETURN="findRetDtlsByIssNumberForReturn";
		
		/** The qry receipt max number. */
		String QRY_RECEIPT_MAX_NUMBER = "getMaxReceiptNumber";
		
		/** The qry return max number. */
		String QRY_RETURN_MAX_NUMBER = "getMaxStockReturnNumber";
		
		/** The qry get balance qnty issue. */
		String QRY_GET_BALANCE_QNTY_ISSUE = "getBalanceQntyForIssue";
		
		/** The qry cancellation max number. */
		String QRY_CANCELLATION_MAX_NUMBER = "getMaxCancellationNumber";
		
		/** The qry transfer max number. */
		String QRY_TRANSFER_MAX_NUMBER = "getMaxTransferNumber";
		
		
		
		/** The qry update req bal qnty by app qnty for issue. */
		String QRY_UPDATE_REQ_BAL_QNTY_BY_APP_QNTY_FOR_ISSUE = "updateBalanceQntyWithApprQntyForIssue";
		
		/** The qry update req bal qnty for issue. */
		String QRY_UPDATE_REQ_BAL_QNTY_FOR_ISSUE = "updateBalanceQntyWithIssueQntyForIssue";
		
		/** The qry update by issue qnty for si receipt. */
		String QRY_UPDATE_BY_ISSUE_QNTY_FOR_SI_RECEIPT = "updateBalanceQntyWithIssueQntyForSIReceipt";
		
		/** The qry update bal qnty for si receipt. */
		String QRY_UPDATE_BAL_QNTY_FOR_SI_RECEIPT = "updateBalanceQntyWithReceiptQntyForSIReceipt";
		
		/** The qry update bal qnty with appr qnty for pr receipt. */
		String QRY_UPDATE_BAL_QNTY_WITH_APPR_QNTY_FOR_PR_RECEIPT = "updateBalanceQntyWithApprQntyForPRReceipt";
		
		/** The qry update bal qnty with recpt qnty for pr receipt. */
		String QRY_UPDATE_BAL_QNTY_WITH_RECPT_QNTY_FOR_PR_RECEIPT = "updateBalanceQntyWithRecptQntyForPRReceipt";
		
		
		
		/** The qry get issued balance qnty receipt. */
		String QRY_GET_ISSUED_BALANCE_QNTY_RECEIPT = "getIssuedBalanceQntyForReceipt";
		
		/** The qry get pr balance qnty receipt. */
		String QRY_GET_PR_BALANCE_QNTY_RECEIPT = "getReceivedBalanceQntyForReceipt";
		
		String QRY_GET_BALANCE_RETURNED_QNTY_FOR_RETURN="getBalanceReturnedQntyForReturn";
		
		String QRY_UPDATE_RETURN_BAL_QNTY_WITH_RETURN_QNTY_FOR_RETURN="updateBalanceReceiptQntyWithReturnQntyForReturnAgainstReceipt";
		String QRY_UPDATE_RETURN_BAL_QNTY_WITH_BAL_RETURN_QNTY_FOR_RETURN="updateBalanceReceiptQntyWithBalanceRetQntyForReturnAgainstReceipt";
		
		/** The qry param number. */
		String QRY_PARAM_NUMBER="transactionNumber";
		
		/** The qry param received qnty. */
		String QRY_PARAM_RECEIVED_QNTY = "receivedQuantity";
		
		/** The qry param type name. */
		String QRY_PARAM_TYPE_NAME = StockUniveralConstants.QRY_PARAM_TYPE_NAME;
		
		/** The qry param prefix. */
		String QRY_PARAM_PREFIX="prefix";
		
		/** The qry requisition max number. */
		String QRY_REQUISITION_MAX_NUMBER = "getMaxRequisitionNumber";
		
		/** The qry issue max number. */
		String QRY_ISSUE_MAX_NUMBER = "getMaxIssueNumber";
		
		/** The qry param off type code. */
		String QRY_PARAM_OFF_TYPE_CODE = "offTypeCode";
		
		/** The qry param trans modified date. */
		String QRY_PARAM_TRANS_MODIFIED_DATE="transactionModifiedDate";
		
		/** The qry param approved date. */
		String QRY_PARAM_APPROVED_DATE="approvedDate";
		
		/** The qry param stock item dtls id. */
		String QRY_PARAM_STOCK_ITEM_DTLS_ID = "stockItemDtlsId";
		
		/** The qry param issued qnty. */
		String QRY_PARAM_ISSUED_QNTY = "issuedQuantity";
		
		/** The qry param issue number. */
		String QRY_PARAM_ISSUE_NUMBER = "issueNumber";
		
		/** The qry param transfer number. */
		String QRY_PARAM_TRANSFER_NUMBER = "stockTransferNumber";
		
		/** The qry param ack number. */
		String QRY_PARAM_ACK_NUMBER="acknowledgementNumber";
		
		/** The qry param return number. */
		String QRY_PARAM_RETURN_NUMBER="returnNumber";
		
		/** The qry param cancellation number. */
		String QRY_PARAM_CANCELLATION_NUMBER="cancellationNumber";
		
		/** The qry param number length. */
		String QRY_PARAM_NUMBER_LENGTH = "numberLength";
		
		/** The qry find receipt dtls by ack number. */
		String QRY_FIND_RECEIPT_DTLS_BY_ACK_NUMBER = "findReceiptDtlsByAckNumber";
		
		/** The qry issue dtls by issue number for receipt. */
		String QRY_ISSUE_DTLS_BY_ISSUE_NUMBER_FOR_RECEIPT = "findIssueDtlsByIssueNumberForReceipt";
		
		/** The qry find receipt dtls by req number. */
		String QRY_FIND_RECEIPT_DTLS_BY_REQ_NUMBER = "findReceiptDtlsByReqNumber";
		
		/** The qry find return dtls by ack number. */
		String QRY_FIND_RETURN_DTLS_BY_ACK_NUMBER = "findReturnDtlsByAckNumber";
		
		/** The qry find return dtls by return number. */
		String QRY_FIND_RETURN_DTLS_BY_RETURN_NUMBER = "findReturnDtlsByReturnNumber";
		
		/** The qry find cancel dtls by cancellation number. */
		String QRY_FIND_CANCEL_DTLS_BY_CANCELLATION_NUMBER = "findCancelDtlsByCancellationNumber";
		
		/** The qry req byreqnum for receipt. */
		String QRY_REQ_BYREQNUM_FOR_RECEIPT="findReqDtlsByReqNumberForReceipt";
		
		String QRY_REQ_BYREQNUM_FOR_RECEIPT_AT_RHO="findReqDtlsByReqNumberForReceiptAtRho";
		
		/** The qry get stock issue type. */
		String QRY_GET_STOCK_ISSUE_TYPE="getStockIssueType";
		
		/** The qry find issue cust dtls by issue number for issue to cust. */
		String QRY_FIND_ISSUE_CUST_DTLS_BY_ISSUE_NUMBER_FOR_ISSUE_TO_CUST = "findIssueCustDtlsByIssueNumberForIssueToCustomer";
		
		String QRY_IS_ISSUE_NUMBER_EXIST_FOR_OFFICE="isIssueNumberExistForOffice";
		
		/** The qry param issued to type. */
		String QRY_PARAM_ISSUED_TO_TYPE="issuedToType";
		
		/** The qry to get manifest stock reduction details. */
		String QRY_GET_MANIFEST_STOCK_REDUCTION_DTLS = "getManifestStockReductionDtls";
		
		/** The qry to get consg. stock reduction details. */
		String QRY_GET_CONSG_STOCK_REDUCTION_DTLS = "getConsgStockReductionDtls";
		/** The qry to get consg. stock reduction details. */
		String QRY_GET_COMAIL_STOCK_REDUCTION_DTLS = "getComailDtlsForStockReduction";
		
		/** The qry to get stock consolidation details. */
		String QRY_GET_STOCK_CONSOLIDATION_DTLS = "getStockConsolidationDtls";
				
		/** The qry to get bag lock stock reduction details. */
		String QRY_GET_BAG_LOCK_STOCK_REDUCTION_DTLS = "getBagLockStockReductionDtls";
		
		/** The qry to get item details by item type code. */
		String QRY_GET_ITEM_DTLS_BY_ITEM_TYPE_CODE = "getItemDtlsByItemTypeCode";
		
		String QRY_GET_CHILD_CONS_LIST_BY_PARENT_CN = "getChildConsListByParentCn";
		
		/** The qry param item type code. */
		String QRY_PARAM_ITEM_TYPE_CODE = "itemTypeCode";
		
		/** The qry param item number length. */
		String QRY_PARAM_ITEM_NO_LENGTH = "itemNoLength";
		
		/** The manifest number length. i.e. 10 */
		Integer MANIFEST_NO_LENGTH = 10;
		
		/** The bag lock number length. i.e. 8 */
		Integer BAG_LOCK_NO_LENGTH = 8;
		
		/** The qry get max TXN number for stock consolidation. */
		String QRY_GET_MAX_TXN_NO = "getMaxTxnNo";
		
		/** The stock consolidation transaction number length. i.e. 12 */
		Integer STOCK_CONSOLIDATION_TX_NO_LENGTH = 5;
	/* ###### Hibernate mapping Constants END########*/
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/* ###### Application  Constants START########*/
		/** The details. */
	String DETAILS=" details ";
		
		/** The active status. */
		String ACTIVE_STATUS=StockUniveralConstants.TRANSACTION_STATUS;
		
		/** The type id. */
		String TYPE_ID = StockUniveralConstants.TYPE_ID;
		
		/** The type name. */
		String TYPE_NAME = StockUniveralConstants.TYPE_NAME;
		
		/** The item type req params. */
		String ITEM_TYPE_REQ_PARAMS="itemType";
		
		/** The item req params. */
		String ITEM_REQ_PARAMS="item";
		
		/** The issue req params. */
		String ISSUE_REQ_PARAMS="issuedToMap";
		
		String ISSUE_PAYMNT_REQ_PARAMS="issuePaymntMap";
		
		/** The transfer to req params. */
		String TRANSFER_TO_REQ_PARAMS="transferTOMap";
		
		/** The transfer from req params. */
		String TRANSFER_FROM_REQ_PARAMS="transferFromMap";
		
		/** The transfer to party map req params. */
		String TRANSFER_TO_PARTY_MAP_REQ_PARAMS="transferTOPartyMap";
		
		/** The transfer from party map req params. */
		String TRANSFER_FROM_PARTY_MAP_REQ_PARAMS="transferFromPartyMap";
		
		String REQUISITION_STATUS_REQ_PARAMS="reqStatusMap";
		
		
		/** The number. */
		String NUMBER = " No ";
		
		/** The requisition. */
		String REQUISITION = "Requisition";
		
		/** The material type. */
		String MATERIAL_TYPE = "Material Type";
		
		/** The material. */
		String MATERIAL = "Material ";
		
		/** The at line no. */
		String AT_LINE_NO = "at Line : ";
		
		/** The approved. */
		String APPROVED = "Approved ";
		
		/** The stock requisition. */
		String STOCK_REQUISITION = "Stock "+REQUISITION;
		
		/** The stock requisition num. */
		String STOCK_REQUISITION_NUM = STOCK_REQUISITION +NUMBER;
		
		/** The issue. */
		String ISSUE = "Issue";
		
		/** The stock issue. */
		String STOCK_ISSUE = "Stock "+ISSUE;
		
		/** The stock issue num. */
		String STOCK_ISSUE_NUM = STOCK_ISSUE +NUMBER;
		
		/** The item type id. */
		String ITEM_TYPE_ID = "itemTypeId";
		
		/** The item id. */
		String ITEM_ID = "itemId";
		
		/** The return. */
		String RETURN = "Return";
		
		/** The stock return. */
		String STOCK_RETURN = "Stock "+RETURN;
		
		/** The stock return num. */
		String STOCK_RETURN_NUM = STOCK_RETURN+NUMBER;
		
		/** The receipt. */
		String RECEIPT = "Receipt";
		
		/** The stock receipt. */
		String STOCK_RECEIPT = "Stock "+ RECEIPT;
		
		/** The stock receipt num. */
		String STOCK_RECEIPT_NUM = STOCK_RECEIPT +NUMBER;
		
		/** The cancellation. */
		String CANCELLATION = "Cancellation";
		
		/** The stock cancellation. */
		String STOCK_CANCELLATION = "Stock "+CANCELLATION;
		
		/** The stock cancellation num. */
		String STOCK_CANCELLATION_NUM = STOCK_CANCELLATION+NUMBER;
		
		/** The transfer. */
		String TRANSFER = "Transfer";
		
		/** The stock transfer. */
		String STOCK_TRANSFER = "Stock "+TRANSFER;
		
		/** The stock transfer num. */
		String STOCK_TRANSFER_NUM = STOCK_TRANSFER+NUMBER;
		
		/** The process requisition. */
		String PROCESS_REQUISITION="PR";
		
		/** The process issue. */
		String PROCESS_ISSUE="SI";
		
		/** The process acknowledge. */
		String PROCESS_ACKNOWLEDGE="SA";
		
		/** The process return. */
		String PROCESS_RETURN="SR";
		
		/** The process transfer. */
		String PROCESS_TRANSFER="ST";
		
		/** The process cancellation. */
		String PROCESS_CANCELLATION="SC";
		
		String PROCESS_CONSOLIDATION_FOR_REDUCTION="STR";
		
		
		/** The can update. */
		String CAN_UPDATE="N";
		
		/** The process running number. */
		int PROCESS_RUNNING_NUMBER=6;
		
		/** The process number length. */
		int PROCESS_NUMBER_LENGTH=12;
		
		/** The no series. */
		String NO_SERIES = "N";
		
		/** The stock std type issue. */
		String STOCK_STD_TYPE_ISSUE="STOCK_ISSUE";
		String STOCK_STD_TYPE_PAYMENT="STOCK_PAYMENT";
		
		
		/** The requisiton status std type. */
		String REQUISITON_STATUS_STD_TYPE="STOCK_REQ_STATUS";
		
		/** The stock std type transfer. */
		String STOCK_STD_TYPE_TRANSFER="STOCK_TRANSFER" +FrameworkConstants.CHARACTER_COMMA +STOCK_STD_TYPE_ISSUE;
		
		/** The code. */
		String CODE="code";
		
		/** The id. */
		String ID="id";
		
		/** The name. */
		String NAME="name";
		
		/** The receiving type. */
		String RECEIVING_TYPE="receivingType";
		
		//Transaction type ie Against PR,Issue,Receipt,Return
		/** The transaction pr type. */
		String TRANSACTION_PR_TYPE="PR";
		
		/** The transaction receipt type. */
		String TRANSACTION_RECEIPT_TYPE="SA";
		
		/** The transaction issue type. */
		String TRANSACTION_ISSUE_TYPE="SI";
		
		/** The transaction return type. */
		String TRANSACTION_RETURN_TYPE="SR";
		
		/** The saved. */
		String SAVED="Saved";
		
		/** The updated. */
		String UPDATED="Updated";
		
		/** The req param start serial number. */
		String REQ_PARAM_START_SERIAL_NUMBER = "startSerialNum";
		
		String REQ_PARAM_END_SERIAL_NUMBER ="endSerialNumber";
		
		/** The req param item id. */
		String REQ_PARAM_ITEM_ID = "itemId";
		
		/** The req param quantity. */
		String REQ_PARAM_QUANTITY = "quantity";
		
		/** The req param issue to party type. */
		String REQ_PARAM_ISSUE_TO_PARTY_TYPE = "issuedTOType";
		
		/** The req param issue party type id. */
		String REQ_PARAM_ISSUE_PARTY_TYPE_ID = "partyTypeId";
		
		/** The req param item details id. */
		String REQ_PARAM_ITEM_DETAILS_ID = "itemDetailsId";
		
		/** The req param cnotes. */
		String REQ_PARAM_CNOTES="cnotes";
		
		/** The req param cnotes. */
		String TAX_SESION_COMP="TAXCOMPONENTS";
		
		/** The req param employee. */
		String REQ_PARAM_EMPLOYEE="employeeMap";
		
		/** The req param customer. */
		String REQ_PARAM_CUSTOMER="customerMap";
		
		String REQ_PARAM_CUSTOMER_TO_LIST="customerMapList";
		
		/** The req param serial number items. */
		String REQ_PARAM_SERIAL_NUMBER_ITEMS="seriesItemMap";
		
		/** The cn start no. */
		String CN_START_NO = "cnStartNo";
		
		/** The ba allowed series. */
		String BA_ALLOWED_SERIES = "B";
		
		/** The stock issue ba series. */
		String STOCK_ISSUE_BA_SERIES="STOCK_ISSUE_BA_SERIES";	
		
		/** The stock issue acc customer series. */
		String STOCK_ISSUE_ACC_CUSTOMER_SERIES="STOCK_ISSUE_ACC_CUSTOMER_SERIES";
		
		/** The stock issue credit customer series. */
		String STOCK_ISSUE_CREDIT_CUSTOMER_SERIES="STOCK_ISSUE_CREDIT_CUSTOMER_SERIES";
		
		/** The stock issue emp series. */
		String STOCK_ISSUE_EMP_SERIES="STOCK_ISSUE_EMP_SERIES";
		
		/** The stock issue fr series. */
		String STOCK_ISSUE_FR_SERIES="STOCK_ISSUE_FR_SERIES";
		
		
		//Customer type config params
		String STOCK_ISSUE_CREDIT_CARD_CUSTOMER_SERIES="STOCK_ISSUE_CREDIT_CARD_CUSTOMER_SERIES";
		String STOCK_ISSUE_RL_CUSTOMER_SERIES="STOCK_ISSUE_RL_CUSTOMER_SERIES";
		String STOCK_ISSUE_LC_CUSTOMER_SERIES="STOCK_ISSUE_LC_CUSTOMER_SERIES";
		String STOCK_ISSUE_COD_CUSTOMER_SERIES="STOCK_ISSUE_COD_CUSTOMER_SERIES";
		String STOCK_ISSUE_GOVT_CUSTOMER_SERIES="STOCK_ISSUE_GOVT_CUSTOMER_SERIES";

		
		/** The stock issue standrd type. */
		String STOCK_ISSUE_STANDRD_TYPE = "Issued to Dropdown ";
		
		String STOCK_STD_PAYMENT_TYPE = "Payment Dropdown ";
		
		String REQUISITION_STATUS_STANDRD_TYPE = "Requisition status  Dropdown ";
		
		/** The stock transfer to standrd type. */
		String STOCK_TRANSFER_TO_STANDRD_TYPE = "Transfer to Dropdown ";
		
		/** The stock transfer from standrd type. */
		String STOCK_TRANSFER_FROM_STANDRD_TYPE = "Transfer From Dropdown ";
		
		/** The stock item dtls not exist. */
		String STOCK_ITEM_DTLS_NOT_EXIST = "Stock Material details ";
		
		/** The stock item type dtls not exist. */
		String STOCK_ITEM_TYPE_DTLS_NOT_EXIST = "Stock Material Type details ";
		
		/** The stock cnote dtls not exist. */
		String STOCK_CNOTE_DTLS_NOT_EXIST = "CNote details ";
		
		
		//FIXME need to replace with c-note type
		/** The item type code for cnotes. */
		//String ITEM_TYPE_CODE_FOR_CNOTES="ITEM_TYPE2";//UdaanCommonConstants.SERIES_TYPE_CNOTES
		
		/** The stock transfer from type. */
		String STOCK_TRANSFER_FROM_TYPE = "Stock Transfer From-Type "+DETAILS;
		
		/** The stock transfer to type. */
		String STOCK_TRANSFER_TO_TYPE = "Stock Transfer TO-Type "+DETAILS;
		
		/** The is approved y. */
		String IS_APPROVED_Y="Y";
		
		/** The is approved n. */
		String IS_APPROVED_N="N";
		
		/** The series type. */
		String SERIES_TYPE="seriesType";
		
		/** The satrt end serial number. */
		String SATRT_END_SERIAL_NUMBER="Start/End serial number";
		
		/** The transaction number. */
		String TRANSACTION_NUMBER=QRY_PARAM_NUMBER;
		
		/** The rho office to. */
		String RHO_OFFICE_TO="RHO_OFFICE_TO";
		
		/** The rho office details. */
		String RHO_OFFICE_DETAILS="RHO OFFICE Details";
		
		/** The logged in city. */
		String LOGGED_IN_CITY="CITY_TO";
		String RECEIPIENT_IN_CITY=" Receipient City ";
		
		/** The stock issue receipient. */
		String STOCK_ISSUE_RECEIPIENT="issuedTO";
		String STOCK_CITY_CODE="cityCode";
		
		String STOCK_STD_TYPE_PROCUREMENT="PROCURE_TYPE";
		
		String REQ_PARAM_PROCUREMENT="procureType";
		
		String PROCUREMENT_STANDRD_TYPE = "Procurement Type  Dropdown ";
		
		String STOCK_INTERNAL_PROCUREMENT="I";
		
		String STOCK_EXTERNAL_PROCUREMENT="E";
		
		String STOCK_CUSTOMER_DETAILS = "Customer Details";
		
		String STOCK_EMPLOYEE_DETAILS = "Fieldstaff Details";
		
		String REQ_OFFICE_MAP="reqOfficeMap";
		
		String ST_REQ_OFFICE_DROP_DOWN = "Office Dropdown ";
		
		/** The req param issue party type dtls. */
		String REQ_PARAM_ISSUE_PARTY_TYPE_DTLS="issuedToTypeDtls";
		
		String LOGGED_IN_OFFICE = "Logged In office ";
		
		/** The qry param from date. */
		String QRY_PARAM_FROM_DATE="fromDate";
		
		/** The qry param to date. */
		String QRY_PARAM_TO_DATE="toDate";
		String REQ_PARAM_RHO_SCREEN="rhoScreen";
		String REQ_PARAM_SERIES_STARTS_WITH="seriesStartsWith";
		
		String QRY_STOCK_PAYMENT_DTLS_FOR_MISC_EXP="getPaymentDtlsForMiscExp";
		
		String QRY_UPDATE_PAYMENT_DTLS="updatePaymentDtlsForMiscExp";
		
		String QRY_PARAM_POSTED="isProcessed";
		String QRY_PARAM_STOK_PAYMNT_ID = "stockPaymentId";
		
		String PAYMENT_DTLS = "Payment ";
		
		String STOCK_ISSUE_INPUT_TO="stockIssueInputTo";
		
		String CUSTOMER_TO ="customerTO";
		
		String USER_INFO = "user";
		
		String RHO_OFFICE = "rhoOffice";
		
		String CORP_OFFICE_TO = "corpOfficeTO";
		
		String ISSUE_DATE ="issueDate";
		
		String SUB_TOTAL ="subTotal";
		
		String CORPORATE_OFFICE_TYPE="corpType";
		
		
	/* ###### Application  Constants END########*/
}
