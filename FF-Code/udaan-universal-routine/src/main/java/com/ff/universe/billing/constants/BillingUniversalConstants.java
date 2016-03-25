package com.ff.universe.billing.constants;


/**
 * The Interface BillingConstants.
 *
 * @author narmdr
 */
public interface BillingUniversalConstants {

	//Params
	String PARAM_CUSTOMER_ID = "customerId";
	String PARAM_CUSTOMER_IDS = "customerIds";
	String PARAM_INVOICE_NUMBERS = "invoiceNumbers";
	String PARAM_SHIPPED_TO_CODE = "shippedToCode";
	String PARAM_START_DATE = "startDate";
	String PARAM_END_DATE = "endDate";
	String PARAM_PRODUCT_ID = "productId";
	String SUMMARY_ID = "billingConsignmentSummaryId";
	String SAP_SUMMARY_ID = "summaryId";
	String SAP_SUMMARY_IDS = "summaryIds";
	String BILL_NUMBER = "billNo";
	String PARAM_LOCATION_OPT_TYPE = "locationOperationType";
	String PARAM_OFFICE_ID = "officeId";
	
	//Query
	String QRY_GET_BILLS_BY_CUSTOMER_ID = "getBillsByCustomerId";
	String QRY_GET_BILLS_BY_INVOICE_NUMBERS = "getBillsByInvoiceNos";
	String QRY_GET_BILLS_BY_SHIPPED_TO_CODE_AND_START_END_DATE = "getBillsByShippedToCodeAndStartEndDate";
	String QRY_GET_BILLS_BY_CUSTOMER_IDS_AND_START_END_DATE = "getBillsByCustomerIdsAndStartEndDate";
	String QRY_GET_BCS_DTLS_BY_SUMMARY_ID = "getBCSDtls";
	String QRY_GET_BILLING_CONSG_DTLS_BY_SUMMARY_ID = "getBillingConsgDtls";
	String QRY_GET_SAP_BILL_SALES_ORDER_DTLS = "getSAPBillSalesOrderDtls";
	String QRY_GET_SAP_BILL_SALES_ORDER_DTLS_BY_BILL ="getSAPBillSalesOrderDtlsByBill";
	String QRY_GET_PAYMENT_BILLS_BY_CUSTOMER_ID = "getPaymentBillsByCustomerId";
	String QRY_GET_SAP_BILLS_BY_CUSTOMER_ID = "getSAPBillsByCustomerId";
	String GETBILLSBYCUSTOMERID =	"  SELECT"
                        		+ "    *"
                        		+ "  FROM"
                        		+ "    ff_f_bill ffb"
                        		+ "  WHERE"
                        		+ "    str_to_date(ffb.BILL_GENERATION_DATE, '%Y-%m-%d') between str_to_date("
                        		+ "                               :startDate,"
                        		+ "                               '%d/%m/%Y')"
                        		+ "                         and str_to_date("
                        		+ "                               :endDate,"
                        		+ "                               '%d/%m/%Y') and"
                        		+ "    ffb.BILLING_OFFICE_ID in (:billingOfficeId) and"
                        		+ "    ffb.FINANCIAL_PRODUCT_ID = :productId and"
                        		+ "    ffb.CUSTOMER in (:customerId)";
	
	String QRY_GET_BILLS_BY_CUSTOMER_AND_START_END_DATE="getBillsByCustomerAndStartEndDate";
	String QRY_GET_SAP_BILL_SALES_ORDER_DTLS_LIST = "getSAPBillSalesOrderDtlsList";
}
