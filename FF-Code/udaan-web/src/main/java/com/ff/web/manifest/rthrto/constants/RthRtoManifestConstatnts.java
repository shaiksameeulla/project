package com.ff.web.manifest.rthrto.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;

// TODO: Auto-generated Javadoc
/**
 * The Interface RthRtoManifestConstatnts.
 * 
 * @author narmdr
 */
public interface RthRtoManifestConstatnts {
	
	/** The success. */
	String SUCCESS = "Success";
	
	/** The return type rth. */
	String RETURN_TYPE_RTH = "H";//don't change this constant it is used for consignment_return table
	
	/** The return type rto. */
	String RETURN_TYPE_RTO = "R";
	
	/** The rto max reasons allowed. */
	String RTO_MAX_REASONS_ALLOWED = "RTO_MAX_REASONS_ALLOWED";
	
	/** The rth max reasons allowed. */
	String RTH_MAX_REASONS_ALLOWED = "RTH_MAX_REASONS_ALLOWED";
	
	/** The manifest direction in. */
	String MANIFEST_DIRECTION_IN = "I";
	
	/** The manifest direction out. */
	String MANIFEST_DIRECTION_OUT = "O";
	
	/** The manifest no. */
	String MANIFEST_NO = "manifestNo";
	
	/** The consignment number. */
	String CONSIGNMENT_NUMBER = "consignmentNumber";
	
	/** The origin office id. */
	String ORIGIN_OFFICE_ID = "originOfficeId";
	
	/** The city id. */
	String CITY_ID="cityId";
	
	/** The manifest type. */
	String MANIFEST_TYPE="manifestType";
	
	/** The manifest type rth. */
	String MANIFEST_TYPE_RTH = "MANIFEST_TYPE_RTH";
	
	/** The manifest type rto. */
	String MANIFEST_TYPE_RTO = "MANIFEST_TYPE_RTO";
	
	/** The destination city id. */
	String DESTINATION_CITY_ID="destCityId";
	
	/** The reason on hold. */
	String REASON_ON_HOLD = "HAD";//34. Hold At Destination (Consignee's Request)
	
	/** The series type ogm no. */
	String SERIES_TYPE_OGM_NO = "SERIES_TYPE_OGM_NO";
	
	/** The series type bpl no. */
	String SERIES_TYPE_BPL_NO = "SERIES_TYPE_BPL_NO";
	
	/** The manifest status close. */
	String MANIFEST_STATUS_CLOSE ="C";
	
	/** The consignment status returned. */
	String CONSIGNMENT_STATUS_RETURNED = CommonConstants.CONSIGNMENT_STATUS_RTOH;
	
	//Page forward strings
	/** The view rth manifest parcel. */
	String VIEW_RTH_MANIFEST_PARCEL="viewRthManifestParcel";
	
	/** The view rto manifest parcel. */
	String VIEW_RTO_MANIFEST_PARCEL="viewRtoManifestParcel";
	
	/** The view rth manifest dox. */
	String VIEW_RTH_MANIFEST_DOX = "viewRthManifestDox";
	
	/** The view rto manifest dox. */
	String VIEW_RTO_MANIFEST_DOX = "viewRtoManifestDox";
	
	/** The reason type. */
	String REASON_TYPE = "reasonType";
	
	/** The cn content other. */
	String CN_CONTENT_OTHER="Other";
	
	/** The reasons list. */
	String REASONS_LIST = "reasonsList";
	
	String CITY_LIST = "cityList";
	
	String LOGIN_OFFICE_TYPE = "loginOfficeType";
	
	/** The office list. */
	String OFFICE_LIST = "officeList";
	
	/** The region list. */
	String REGION_LIST="regionList";
	
	//url
	/** The url view rth validation. */
	String URL_VIEW_RTH_VALIDATION = "viewRthValidation";
	
	/** The url view rto validation. */
	String URL_VIEW_RTO_VALIDATION = "viewRtoValidation";
	
	//error codes
	/** The rth rto validation details saved. */
	String RTH_RTO_VALIDATION_DETAILS_SAVED = "RTHRTOV001";
	
	/** The error in saving rth rto validation details. */
	String ERROR_IN_SAVING_RTH_RTO_VALIDATION_DETAILS = "RTHRTOV002";
	
	/** The invalid consignment number. */
	String INVALID_CONSIGNMENT_NUMBER = "RTHRTOV003";
	
	/** The consignment number out for delivery. */
	String CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY = "RTHRTOV004";
	
	/** The consignment number delivered. */
	String CONSIGNMENT_NUMBER_DELIVERED = "RTHRTOV005";

	/** The consignment number is not still validated. */
	String CONSIGNMENT_IS_NOT_STILL_VALIDATED = "RTHRTOV006";
	
	/** The consignment number is not still validated for RTH process. */
	String CONSIGNMENT_IS_NOT_STILL_VALIDATED_FOR_RTH = "RTHRTOV007";
	
	/** The consignment number is not still validated for RTO process. */
	String CONSIGNMENT_IS_NOT_STILL_VALIDATED_FOR_RTO = "RTHRTOV008";
	
	String CONSIGNMENT_IS_ALREADY_MANIFESTED = "RTHRTOV009";
	String CONSIGNMENT_IS_NEITHER_IN_MANIFESTED_NOR_THIRD_PARTY_MANIFESTED = "RTHRTOV010";
	String CN_NO_NEITHER_RTH_MANIFESTED_NOR_EXCESS_INMANIFESTED_NOR_DRS_PROCESS_HAPPENED = "RTHRTOV011";
	String CN_NO_NOT_YET_IN_MANIFESTED = "RTHRTOV012";
	
	/** The rth only allowed at branch office. */
	String RTH_ONLY_ALLOWED_AT_BRANCH_OFFICE = "RTHRTOC001";
	
	/** The rto only allowed at hub office. */
	String RTO_ONLY_ALLOWED_AT_HUB_OFFICE = "RTHRTOC002";
	
	/** The rth manifested consignments allowed. */
	String RTH_MANIFESTED_CONSIGNMENTS_ALLOWED="RTHRTOP001";
	
	/** The rto manifest allowed email trigger origin. */
	String RTO_MANIFEST_ALLOWED_EMAIL_TRIGGER_ORIGIN="RTHRTOP002";
	
	/** The rto validation entry not exist. */
	String RTO_VALIDATION_ENTRY_NOT_EXIST="RTHRTOP003";
	
	/** The consignment not related to selected destination. */
	String CONSIGNMENT_NOT_RELATED_TO_SELECTED_DESTINATION="RTHRTOP004";
	
	/** The consignment status update issue. */
	String CONSIGNMENT_STATUS_UPDATE_ISSUE="RTHRTOP005";
	
	/** The data not saved db issue. */
	String DATA_NOT_SAVED_DB_ISSUE="RTHRTOD001";
	
	/** The consignment rate not found. */
	String CONSIGNMENT_RATE_NOT_FOUND="RTHRTOP006";	

	String CONSIGNMENT_IS_ALREADY_RTO_MANIFESTED = "RTHRTOP007";
	String CONSIGNMENT_IS_ALREADY_RTH_MANIFESTED = "RTHRTOP008";

	//param
	/** The param consignment number. */
	String PARAM_CONSIGNMENT_NUMBER = "consignmentNumber";
	
	/** The param office id. */
	String PARAM_OFFICE_ID = "officeId";
	
	/** The param return type. */
	String PARAM_RETURN_TYPE = "returnType";
	
	/** The param reason code. */
	String PARAM_REASON_CODE = "reasonCode";
	
	//query
	/** The qry get consignment return by consg no office id return type. */
	String QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE = "getConsignmentReturnByConsgNoOfficeIdReturnType";
	
	/** The qry get consignment return by consg no return type. */
	String QRY_GET_CONSIGNMENT_RETURN_BY_CONSG_NO_RETURN_TYPE = "getConsignmentReturnByConsgNoReturnType";	
	
	//Configurable params
	/** The rto ppx email triggering consg series. */
	String RTO_PPX_EMAIL_TRIGGERING_CONSG_SERIES="RTO_PPX_EMAIL_TRIGGERING_CONSG_SERIES";
	
	/** The rto ppx email triggering consg weight. */
	String RTO_PPX_EMAIL_TRIGGERING_CONSG_WEIGHT="RTO_PPX_EMAIL_TRIGGERING_CONSG_WEIGHT";	
	
	/** The request param. */
	String PARAM_SERIES_TYPE = "seriesType";
	String PARAM_CITY_CODE = "cityCode";
	
	String URL_PRINT_RTH_RTO_DOX = "printRthRtoDOX";
	
	String URL_PRINT_RTH_RTO_PPX = "printRthRtoPPX";
	
	String PARAM_RTOH_DOX_URL = "rthRtoDoxURL";
	
	String RTO_MANIFEST_RATE_DTLS="rtoRateDetails";

}
