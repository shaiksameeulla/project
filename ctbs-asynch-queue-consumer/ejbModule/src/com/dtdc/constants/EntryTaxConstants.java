package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * Responsible for holding all constants related to
 * UC 27.
 */
public interface EntryTaxConstants {

	//forwarding pages
	/** The Constant TAXSEARCH. */
	static final String TAXSEARCH = "taxSearchPage";
	
	/** The Constant TAXDETAILS. */
	static final String TAXDETAILS = "taxDtlsPage";
	
	/** The Constant TAXCONFIG. */
	static final String TAXCONFIG = "taxConfigPage";
			
	//set attributes
	/** The Constant PAYMENT_DTL_LIST. */
	static final String PAYMENT_DTL_LIST = "paymentDetailsList";
	
	/** The Constant ENTYTAX_DTL_LIST. */
	static final String  ENTYTAX_DTL_LIST = "detailsList";
	
	//error key
	/** The Constant UPDATE_EXCEPTION. */
	static final String UPDATE_EXCEPTION = "pikup.update.exception";
	
	/** The Constant LOG_ERROR_IN_SAVE. */
	static final String LOG_ERROR_IN_SAVE = "Error occured in save() ..";
	
	/** The Constant LOG_ERROR_IN_SHOW. */
	static final String LOG_ERROR_IN_SHOW = "Error occured in show()";
	
	/** The Constant LOG_ERROR_IN_PRINT. */
	static final String LOG_ERROR_IN_PRINT = "Error occured in print()";
	
	
	//service class name for split model
	/** The Constant ENTRYTAX_SERVICE_NAME. */
	static final String ENTRYTAX_SERVICE_NAME = "EntryTaxService";
	
	/** The Constant ENTRYTAX_SAVECONFIG. */
	static final String ENTRYTAX_SAVECONFIG = "saveTaxConfigDetails";
	
	/** The Constant ENTRYTAX_SAVETAXDETAILS. */
	static final String ENTRYTAX_SAVETAXDETAILS = "saveEntryTaxDetails";
	
	//method attributes
	/** The Constant OCTROI_PAYMENT_TYPE. */
	static final String OCTROI_PAYMENT_TYPE = "PAYMENT_TYPE"; 
	
	/** The Constant OCTROI_CUSTOMER_TYPE. */
	static final String OCTROI_CUSTOMER_TYPE = "CUSTOMER";	
	
	/** The Constant OCTROI_TAX_TYPE. */
	static final String OCTROI_TAX_TYPE = "TAX_TYPE";
	
	/** The Constant OCTROI_CHARGE_TYPE. */
	static final String OCTROI_CHARGE_TYPE = "CHARGE_TYPE";
	
	/** The Constant OCTROI_CUST_FRANCHISEE. */
	static final String OCTROI_CUST_FRANCHISEE = "FR";
	
	/** The Constant OCTROI_CUST_DParty. */
	static final String OCTROI_CUST_DParty = "GDP";
	
	/** The Constant ENTRYTAX_AGENT. */
	static final String ENTRYTAX_AGENT = "AGENT";
	
	/** The Constant OCTROI_CUST_ALL. */
	static final String OCTROI_CUST_ALL = "ALL";
	
	/** The Constant OCTROI_COMMODITYWISE. */
	static final String OCTROI_COMMODITYWISE = "CommodityWise";
	
	/** The Constant OCTROI_ENTRYTAX. */
	static final String OCTROI_ENTRYTAX = "EntryTax";
	
	/** The Constant OCTROI_OCTROITAX. */
	static final String OCTROI_OCTROITAX = "Octroi";
	
	/** The Constant ALERT_YES. */
	static final String ALERT_YES = "YES";
	
	/** The Constant ALERT_NO. */
	static final String ALERT_NO = "NO";
	
	/** The Constant FRANCHISEE. */
	static final String FRANCHISEE = "FR";
	
	/** The Constant OFFICE_RO. */
	static final String OFFICE_RO = "RO";
	
	/** The Constant OFFICE_BO. */
	static final String OFFICE_BO = "BO";
	
	/** The Constant OFFICE_CO. */
	static final String OFFICE_CO = "CO";
	
	/** The Constant IS_SEARCH_YES. */
	static final String IS_SEARCH_YES = "YES";
	
	/** The Constant ENTRYTAX_COMM_ACTIVE. */
	static final String ENTRYTAX_COMM_ACTIVE = "A";
	
	/** The Constant ENTRYTAX_COMM_INACTIVE. */
	static final String ENTRYTAX_COMM_INACTIVE = "I";
	
	//NamedQuery
	/** The Constant OCTROI_REPOFFICES. */
	static final String OCTROI_REPOFFICES = "getRepOfficeDetails";
	
	/** The Constant ENTRYTAX_GETCONFIGDETAILS_CUSTOMER. */
	static final String ENTRYTAX_GETCONFIGDETAILS_CUSTOMER="getConfigDetailsForCustomerId";
	
	/** The Constant ENTRYTAX_GETCONFIGDETAILS_FRANCHISEE. */
	static final String ENTRYTAX_GETCONFIGDETAILS_FRANCHISEE="getConfigDetailsForFranchiseeId";
	
	/** The Constant CASECLOSE_YES. */
	static final String CASECLOSE_YES = "YES";
	
	/** The Constant CASECLOSE_NO. */
	static final String CASECLOSE_NO = "NO";
	
	/** The Constant GET_SERVICE_TAX_DETAILS_BY_STATE_ID. */
	public static final String GET_SERVICE_TAX_DETAILS_BY_STATE_ID="getServiceTaxByState";
	
	/** The Constant STATE_ID. */
	public static final String STATE_ID = "stateId";
	
	/** The cn list param. */
	String CN_LIST_PARAM = "cnList";
	
	/** The get tax dtls by cn numbers qry. */
	String GET_TAX_DTLS_BY_CN_NUMBERS_QRY = "getTaxDtlsByCnNumbers";
	
	/** The delete tax dtls by cn numbers. */
	String DELETE_TAX_DTLS_BY_CN_NUMBERS = "deleteTaxDtlsByCnNumbers";
}
