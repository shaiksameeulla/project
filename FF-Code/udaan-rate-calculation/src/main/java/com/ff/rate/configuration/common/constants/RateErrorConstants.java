/**
 * 
 */
package com.ff.rate.configuration.common.constants;

/**
 * @author prmeher
 *
 */
public interface RateErrorConstants {
	
	String VALIDATE_CONSGN_TYPE = "ERC001";
	String VALIDATE_PRODUCT_SERIES	= "ERC002";
	String VALIDATE_ORIGIN_CITY_CODE ="ERC003";
	String VALIDATE_DEST_PINCODE = "ERC004";
	String VALIDATE_WEIGHT ="ERC005";
	String VALIDATE_DECLARE_VALUE ="ERC006";
	String VALIDATE_OTHERCHARGES = "ERC007";
	String VALIDATE_DISCOUNT ="ERC008";
	String VALIDATE_CUST_CODE =	"ERC009";
	String VALIDATE_RATE_TYPE = "ERC010";
	String VALIDATE_CONSIGNMENT_TYPE = "ERC011";
	String VALIDATE_DECLARED_VALUE_WHEN_RISK_SURCHARGE_PROVIDED = "ERC012";
	String VALIDATE_SERVICED_ON_FOR_PRIORITY = "ERC013";
	String CONTRACT_UNDEFINED_FOR_RATE_CALC = "ERC014";
	String VALIDATE_BA_CODE =	"ERC015";
	String VALIDATE_CREDIT_CUSTOMER_PRODUCT_HEADER = "ERC016";
	String VALIDATE_DESTINATION_SECTOR = "ERC017";
	String VALIDATE_BA_RATE_PRODUCT_HEADER = "ERC018";
	String VALIDATE_CASH_RATE_PRODUCT_HEADER = "ERC019";
	String VALIDATE_DEST_CITY = "ERC020";
	String VALIDATE_ORIGIN_CITY = "ERC021";
	String ERROR_NO_WEIGHT_SLAB_FOUND = "ERC022";
	String VALIDATE_EB_PREFERENCES = "ERC023";
	String VALIDATE_INSURED_BY_WHEN_RISK_SURCHARGE_PROVIDED = "ERC024";
	String VALIDATE_EB_RATE_PRODUCT_HEADER = "ERC025";
	String VALIDATE_RATE_CALCULATION_DATE = "ERC026";
	String VALIDATE_PRODUCT_FOR_RATE_CALCULATION = "ERC027";
	
	String VALIDATE_CONSIGNMENT_TYPE_TO = "ERC030";
	String VALIDATE_PRODUCT_TO = "ERC031";
	String VALIDATE_OPERATING_OFFICE = "ERC032";
	String VALIDATE_DESTINATION_PINCODE_TO = "ERC033";
	String VALIDATE_EVENT_DATE = "ERC034";
	String VALIDATE_BOOKING_DATE = "ERC035";
	String VALIDATE_CUSTOMER_ID = "ERC036";
	String VALIDATE_CN_PRICING_TO = "ERC037";
	String VALIDATE_RATE_APPLICABLE_FOR_RTO = "ERC042";
	
	String CONTRACT_NOT_SUBMITTED_SUCCESSFULLY = "CON001";
	String CONTRACT_SUBMITTED_SUCCESSFULLY = "CON002";
	
	String EB_RATE_NOT_CONFIGURED = "EB001";
	String TAXES_NOT_CONFIGURED = "EB002";
	String PREFERENCES_NOT_DEACTIVATED_SUCCESSFULLY = "EB003";
	String PREFERENCES_DEACTIVATED_SUCCESSFULLY = "EB004";

	String RATES_NOT_SAVED_SUCCESSFULLY = "RC001";
	String RATES_SAVED_SUCCESSFULLY = "RC002";
	String RATES_NOT_SUBMITTED_SUCCESSFULLY = "RC003";
	String RATES_SUBMITTED_SUCCESSFULLY = "RC004";
	String RATES_NOT_RENEWED_SUCCESSFULLY = "RC005";
	String RATES_RENEWED_SUCCESSFULLY = "RC006";

	String DATA_NOT_SAVED = "RC007";
	String DATA_SAVED_SUCCESSFULLY = "RC008";
	String SEARCH_RESULT_NOT_FOUND = "RC009";
	
	String APPROVED_SUCCESSFULLY = "Q001";
	String NOT_APPROVED_SUCCESSFULLY = "Q002";

	String REJECTED_SUCCESSFULLY = "Q003";
	String NOT_REJECTED_SUCCESSFULLY = "Q004";

	String DETAILS_DOES_NOT_EXIST = "Q005";
	String COD_CHARGE_DTLS_NOT_EXIST = "Q006";
	String EMPLOYEE_DTLS_NOT_EXIST = "Q008";
	String CITY_DTLS_NOT_EXIST = "Q007";
	String VW_DENO_NOT_EXIST = "Q009";
	String BUSINESS_TYPE_DTLS_NOT_EXIST = "Q010";
	String CUSTOMER_DEPARTMENT_DTLS_NOT_EXIST = "Q011";
	String OCTROI_BOURNE_BY_DTLS_NOT_EXIST = "Q012";
	String RATE_QUOTATION_TITLE_NOT_EXIST = "Q013";
	String CUSTOMER_GROUP_DTLS_NOT_EXIST = "Q014";
	String INSURED_BY_DTLS_NOT_EXIST = "Q015";
	String RATE_INDUSTRY_TYPE_DTLS_NOT_EXIST = "Q016";
	String INVALID_PINCODE = "Q017";
	String OFFICE_DTLS_NOT_FOUND = "Q018";
	String OCTROI_CHARGE_VALUE_NOT_FOUND = "Q019";
	String QUOTATION_COPIED_SUCCESSFULLY = "Q020";
	String QUOTATION_NOT_COPIED_SUCCESSFULLY = "Q021";

	String QUOTATION_SUBMITTED_SUCCESSFULLY = "Q022";
	String QUOTATION_NOT_SUBMITTED_SUCCESSFULLY = "Q023";

	String CONTRACT_NOT_CREATED_SUCCESSFULLY = "Q024";

	String RATE_CUSTOMER_CATEGORY_DTLS_NOT_EXIST = "Q025";

	String DATA_NOT_SAVED_SLAB_RATES_ARE_LOWER = "Q026";
	
	String VALIDATE_SEQ_GEN_ERROR = "ERC038";
	String VALIDATE_RATE = "ERC039";
	String RATE_INDUSTRY_CATEGORY_DTLS_NOT_EXIST = "Q027";
	String STANDARD_TYPE_NOT_EXIST = "Q028";
	String BRANCHES_NOT_FOUND ="Q029";
	String CUSTOMER_NOT_BLOCK_UNBLOCK="Q030";
	String CITY_DETAILS_NOT_FOUND = "Q031";
	String EMP_DETAILS_NOT_FOUND = "Q032";
	String USER_ZONE_CODE_NOT_FOUND = "Q033";
	String CITY_NOT_FOUND = "Q034";
	String REGIONAL_OFC_DTLS_NOT_EXIST = "Q035";
	String USER_OFC_DTLS_NOT_EXIST = "Q036";
	String PRODUCT_CATEGORY_DTLS_NOT_EXIST = "Q037";
	String CUSTOMER_CATEGORY_DTLS_NOT_EXIST = "Q038";
	String URL_IS_INVALID = "Q039";
	String VALIDATE_OCTROI_AMOUNT = "ERC040";
	String VALIDATE_OCTROI_STATE = "ERC041";
	String DATE_SHOULD_NOT_EXCEED_ONE_YEAR = "Q042";
	String DATE_PARSE_ERROR = "Q043";
	String MINIMUM_CHARGE_WEIGHT_DTLS_NOT_EXIST = "Q044";
	String VOB_DTLS_NOT_EXIST = "Q045";
	String WEIGHT_SLAB_DTLS_NOT_EXIST = "Q046";
	String SECTOR_DTLS_NOT_EXIST = "Q047";
	
	String APPROVER_SAVED_SUCCESSFULLY = "RC010";
	String APPROVER_NOT_SAVED_SUCCESSFULLY = "RC011";
	String EMPTY_SLOD_TO_CODE = "RC012";
	String EMPTY_PICKUP_SHIP_TO_CODE = "RC013";
	String EMPTY_DELIVERY_SHIP_TO_CODE = "RC014";
	String EMPTY_VALID_FROM_DATE = "RC015";
	String INVALID_VALID_FROM_DATE = "RC016";
	String INVALID_RENEW_VALID_FROM_DATE = "RC017";
	String MULTIPLE_LOCATIONS_FOR_DBDP_CONTRACT = "RC018";
	
}
