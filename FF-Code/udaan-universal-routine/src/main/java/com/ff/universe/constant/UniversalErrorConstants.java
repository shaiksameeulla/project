/**
 * 
 */
package com.ff.universe.constant;

/**
 * @author mohammes
 *
 */
public interface UniversalErrorConstants {
	String PRODUCT_MIS_MATCH="ERRSTOCK001";
	String SERIES_MUST_STARTS_WITH_REGION="ERRSTOCK002";
	String INVALID_SERIES_LENGTH="ERRSTOCK003";
	String SERIES_MUST_STARTS_WITH="ERRSTOCK004";
	String EMPTY_SERIES="ERRSTOCK005";
	String ISSUE_DETAILS_DOES_NOT_EXIST="ERRSTOCK006";
	String SERIES_ALREADY_CANCELLED="ERRSTOCK007";
	String INVALID_SERIES_FORMAT="ERRSTOCK008";
	String INVALID_OPR_RECEIPT="ERRSTOCK009";
	String REGION_CODE_EMPTY="ERRSTOCK010";
	String DATA_ISSUE="ERRSTOCK011";
	String CITY_CODE_EMPTY="ERRSTOCK012";
	String SERIES_MUST_STARTS_WITH_CITY="ERRSTOCK013";
	String SERIES_MUST_STARTS_WITH_CM="ERRSTOCK014";
	String OFFICE_CODE_EMPTY="ERRSTOCK015";
	String SERIES_MUST_STARTS_WITH_BRANCH_CODE="ERRSTOCK016";
	String INVALID_MATERIAL_INFO="ERRSTOCK017";
	String CANCELLED_SERIES="ERRSTOCK018";
	String ALREADY_ISSUED_TO_OTHERS="ERRSTOCK019";
	String STOCK_TRANSFERRED_TO_OFFICE="ERRSTOCK020";
	String STOCK_ALREADY_ISSUED="ERRSTOCK021";
	
	String STOCK_ALREADY_EXPIRED="ERRSTOCK022";
	String STOCK_TRANSCTION_OFFICE_CONFIGURATION="ERRSTOCK024";
	String STOCK_MISMATCH_BA_CITY_AND_TRANSCTION_CITY="ERRSTOCK025";
	String ERROR = "ERR007";
	
	String MANIFEST_NOT_ISSUED_REGION="ERRORMN001";
	String CONSG_NOT_FOUND="ERRORCN001";
	
	String NO_OFFICE_DETAILS_FOUND="ERRORMN002";
	String NO_CUSTOMERS_FOUND="ERRORMN003";
	String CITY_DETAILS_NOT_EXIST="ERRORGL001";
	String NO_EMPLOYESS_FOUND="ERRORMN004";
	String BA_ITEM_RATE_NOT_CONFIGURED="ERRSTRATE001";
	String INVALID_INPUT_FOR_BA_RATE="ERRSTRATE002";
	String INVALID_MATERIAL="ERRSTRATE003";
	String TAX_COMPONENTS_NOT_CONFIGURED="ERRSTRATE004";
	
	String CONSG_ORIGIN_OFFICE_NOT_EXIST="ERRDRS0001";
	
	String CONSG_COD_LC_TO_PAY_MANDATORY="ERRDRS0002";
	
	String STOCK_CANCELED = "ERRMANIFEST0001";
	String STOCK_QUANTITY_ZERO="ERRSTOCK023";
	String ORIGIN_NOT_DEFINED_FOR_PRODUCT = "ERROR0001";
	String IN_VALID_LOGGED_OFFICE_OFFICE_TYPE="ERRORGL002";
	
	String CONSIGNMENT_SERIES_DOESNOT_EXIST="CONSG0004";
	
}
