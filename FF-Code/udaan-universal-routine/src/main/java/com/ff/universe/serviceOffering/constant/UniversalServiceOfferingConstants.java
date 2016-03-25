/**
 * 
 */
package com.ff.universe.serviceOffering.constant;

/**
 * @author uchauhan
 *
 */
public interface UniversalServiceOfferingConstants {
	
	String PARAMS = "declaredValue,bookingType";

	String QRY_GET_REASONS_BY_REASON_TYPE = "getReasonsByReasonType";
	String PARAM_REASON_TYPE = "reasonType";
	
	/** The is active yes. */
	String IS_ACTIVE_YES = "Y";
	String QRY_GET_ALL_SERVICING_OFFICE_BY_PINCODE = "getPincodeServicingBranch";
	String PARAM_PINCODE = "pincode";
	
	String QRY_LOCATION_SEARCH_INFO = "SELECT fdp.PINCODE AS pincodeMapped, "
			+ "fdo.OFFICE_NAME AS officeName, "
			+ "group_concat(DISTINCT fdpr.PRODUCT_NAME ORDER BY fdpr.PRODUCT_NAME ASC "
			+ "SEPARATOR ' | ') AS productMapped " + "FROM ff_d_pincode fdp "
			+ "INNER JOIN ff_d_branch_pincode_serviceablity fdbps "
			+ "ON fdbps.PINCODE_ID = fdp.PINCODE_ID "
			+ "INNER JOIN ff_d_office fdo ON fdo.OFFICE_ID = fdbps.OFFICE_ID "
			+ "INNER JOIN ff_d_pincode_product_serviceability fdpps "
			+ "ON fdpps.PINCODE_ID = fdp.PINCODE_ID "
			+ "INNER JOIN ff_d_product fdpr "
			+ "ON fdpr.PROD_GROUP_ID = fdpps.PROD_GROUP_ID "
			+ "INNER JOIN ff_d_city fdc ON fdc.CITY_ID = fdp.CITY_ID "
			+ "WHERE     fdp.LOCATION = :location "
			+ "AND fdc.CITY_NAME = :cityName "
			+ "AND (   fdpps.ORIGIN_CITY_ID IS NULL "
			+ "OR fdpps.ORIGIN_CITY_ID IN (SELECT fdo2.CITY_ID "
			+ "FROM ff_d_office fdo2 "
			+ "WHERE fdo2.OFFICE_ID = :loggedInOffice)) "
			+ "GROUP BY fdp.PINCODE, fdo.OFFICE_NAME "
			+ "ORDER BY fdo.OFFICE_NAME;";
	
	String QRY_SEARCH_PINCODE_INFO = "SELECT " 
		      + "fdp.PINCODE as pincode, "
		      + "fdc.CITY_NAME as cityName, "
		      + "fds.STATE_NAME as stateName, "
		      + "fdr.REGION_NAME as regionName, "
		      + "fdo.OFFICE_NAME as officeName, "
		      + "fdbps.PINCODE_ID as pincodeId, "
		      + "fdo.ADDRESS_1 as address1, "
		      + "fdo.ADDRESS_2 as address2, "
		      + "fdo.EMAIL as email, "
		      + "fdo.PHONE as phone, "
		      + "fdbps.STATUS AS servStatus "
		      + "FROM ff_d_pincode fdp "
		      + "INNER JOIN ff_d_city fdc "
		      + "ON fdc.CITY_ID = fdp.CITY_ID "
		      + "INNER JOIN ff_d_state fds "
		      + "ON fds.STATE_ID = fdc.STATE "
		      + "INNER JOIN ff_d_region fdr "
		      + "ON fdr.REGION_ID = fdc.REGION "
		      + "INNER JOIN ff_d_branch_pincode_serviceablity fdbps "
		      + "ON fdbps.PINCODE_ID = fdp.PINCODE_ID "
		      + "INNER JOIN ff_d_office fdo "
		      + "ON fdo.office_id = fdbps.office_id "
		      + "INNER JOIN ff_d_address fda "
		      + "ON fda.address_id = fdc.city_id "
		      + "WHERE fdp.PINCODE =:pincode AND fdbps.STATUS='A' " ;

}
