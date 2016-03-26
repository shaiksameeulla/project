/***/
package com.ff.web.drs.common.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Interface DrsConstants.
 * 
 * @author mohammes Purpose: this Constant file holds all global constants which
 *         are specific to web project
 */
public interface DrsConstants {

	/* ###### Struts mapping Constants START####### */

	/** The success forward. */
	String SUCCESS_FORWARD = "success";
	String SUCCESS_FORWARD_BLK_HUB = "bulkCnPendingDrsHub";
	String SUCCESS_FORWARD_BLK_BRANCH = "bulkCnPendingDrsBranch";

	/* ###### Struts mapping Constants END###### */

	/** The success flag. */
	String SUCCESS_FLAG = FrameworkConstants.SUCCESS_FLAG;

	/** The error flag. */
	String ERROR_FLAG = FrameworkConstants.ERROR_FLAG;

	/** The logged in city. */
	String LOGGED_IN_CITY = "CITY_TO";

	/** The drs for std type. */
	String DRS_FOR_STD_TYPE = "DRS_FOR";
	/** The drs for std type. */
	String DRS_PENDING_BULK_LOAD_TYPE = "DRS_BULK_LOAD_TYPE";

	String DRS_PAYMENT_STD_TYPE = "DELIVERY_PAYMENT_TYPE";

	/** The delivery type std type. */
	String DELIVERY_TYPE_STD_TYPE = "DELIVERY_TYPE";

	/** The yp drs std type. */
	String YP_DRS_STD_TYPE = "GLOBAL_BOOLEAN_TYPE";

	/** The drs seal std type. */
	String DRS_SEAL_STD_TYPE = "DRS_SEAL_SIGN";

	String DELIVERY_TYPE_RTO = "DELIVERY_TYPE_RTO";

	/** The mf manual drs type. Standard type constant */
	String MF_MANUAL_DRS_TYPE = "MF_MANUAL_DRS_TYPE";

	// ####YP_DRS Constanst START
	/** The yp drs yes. */
	String YP_DRS_YES = "Y";

	/** The yp drs no. */
	String YP_DRS_NO = "N";
	String M_DRS_YES = "M";
	
	String BULK_DRS_TYPE = "BK";

	// ####YP_DRS Constanst END

	// ####DRS FOR Constanst START
	/** The drs for field staff. */
	String DRS_FOR_FIELD_STAFF = "FS";

	/** The drs for co courier. */
	String DRS_FOR_CO_COURIER = "CC";

	/** The drs for ba. */
	String DRS_FOR_BA = "BA";

	/** The drs for fr. */
	String DRS_FOR_FR = "FR";

	// ####DRS FOR Constanst END

	// ####DRS TYPE Constanst START

	/** The drs type preparation. */
	String DRS_TYPE_PREPARATION = "P";

	/** The drs type update. */
	String DRS_TYPE_UPDATE = "U";
	// ####DRS TYPE Constanst END

	/** The drs . */
	String DRS = "D";
	/** The drs . */
	String YP_DRS = "YP" + DRS;

	/** The drs running number length. */
	Integer DRS_RUNNING_NUMBER_LENGTH = 7;

	/** The drs number length. */
	Integer DRS_NUMBER_LENGTH = 12;

	/** The Ydrs number length. */
	Integer YDRS_NUMBER_LENGTH = 14;
	Integer M_DRS_NUMBER_LENGTH = 13;
	
	int DRS_NUMBER_LENGTH_12 = 12;
	int DRS_NUMBER_LENGTH_13 = 13;
	int DRS_NUMBER_LENGTH_14 = 14;
	int DRS_NUMBER_LENGTH_11 = 11;

	// ####SRS SCREEN CODE START
	/** The normal priority dox drs. */
	String NORMAL_PRIORITY_DOX_DRS_SCREEN_CODE = "NPDOX";

	/** The normal priority ppx drs. */
	String NORMAL_PRIORITY_PPX_DRS_SCREEN_CODE = "NPPPX";

	/** The cc q drs. */
	String CC_Q_DRS_SCREEN_CODE = "CCQ";

	/** The cod lc to pay. */
	String COD_LC_TO_PAY_SCREEN_CODE = "CODLC";

	/** The rto cod. */
	String RTO_COD_SCREEN_CODE = UniversalDeliveryContants.RTO_COD_SCREEN_CODE;

	/** The list drs. */
	String LIST_DRS_SCREEN_CODE = "LDRS";

	/** The pending drs. */
	String PENDING_DRS_SCREEN_CODE = "PDRS";
	
	
	String BULK_PENDING_DRS_SCREEN_CODE_HUB = "BPDRSH";
	String BULK_PENDING_DRS_SCREEN_CODE_BRANCH = "BPDRSB";

	String MANUAL_PENDING_DRS_SCREEN_CODE = "MPDRS";

	String MANUAL_TMF_DRS_SCREEN_CODE = "TMDRS";

	// ####SRS SCREEN CODE END

	// ####DRS STATUS Constanst START
	/** The drs status open. */
	String DRS_STATUS_OPEN = CommonConstants.DRS_STATUS_OPEN;

	/** The drs status updated. */
	String DRS_STATUS_UPDATED = CommonConstants.DRS_STATUS_UPDATED;

	/** The drs status closed. */
	String DRS_STATUS_CLOSED = CommonConstants.DRS_STATUS_CLOSED;
	// ####DRS STATUS Constanst END
	/** The can update. */
	String CAN_UPDATE = "N";

	String FLAG_YES = "Y";

	String FLAG_NO = "N";

	/** The drs mode of payment Type Cheque */
	String MODE_OF_PAYMENT_CHEQUE = StockUniveralConstants.STOCK_PAYMENT_CHQ;

	/** The mode of payment cash. */
	String MODE_OF_PAYMENT_CASH = StockUniveralConstants.STOCK_PAYMENT_CASH;

	/** The mode of payment dd. */
	String MODE_OF_PAYMENT_DD = StockUniveralConstants.STOCK_PAYMENT_DD;

	String CONSG_TYPE_NA = "NA";

	/** The drs type manifest. */
	String MANIFEST_DRS_TYPE_MANIFEST = "TMF";

	/** The drs type drs. */
	String MANIFEST_DRS_TYPE_DRS = "MDRS";

	String URL_PRINT_DRS_NORM_PPX = "printDrsNormPpx";

	String URL_PRINT_DRS_NORM_DOX = "printDrsNormdox";

	String URL_PRINT_DRS_CREDIT_DOX = "printDrsCreditdox";

	String URL_PRINT_DRS_CODLC_DOX = "printDrsCodLcdox";

	String URL_PRINT_DRS_CODLC_PPX = "printDrsCodLcPpx";

	String DRS_CLOSE_IS_REQUIRED = "isDRSCloseRequired";

	String OTHER_CN_CONTENT = "Others";

	/* DRS NORMAL-PRIORITY DOX PRINT START */
	String SR_NO_FIELD = "SR_NO";
	String SR_NO2_FIELD = "SR_NO2";
	String CONSG_NO_FIELD = "CONSG_NO";
	String CONSG_NO2_FIELD = "CONSG_NO2";
	String ORIGIN_FIELD = "ORIGIN";
	String ORIGIN2_FIELD = "ORIGIN2";
	String TOTAL_DLV_CONSG_FIELD = "TOTAL_DLV_CONSG";
	String EMP_NAME_FIELD = "EMP_NAME";
	// To dynamic block
	String CELL_GRID_16_FULL_BOX = "cell_grid_16_full_box";
	String CELL_GRID_16_HALF_BOX = "cell_grid_16_half_box";
	String FOOTER_BOX = "footer_box";
	String HEADER_BOX = "header_box";

	/* DRS NORMAL-PRIORITY PPX PRINT START */
	// To dynamic block
	String CELL_GRID_FULL_BOX = "cell_grid_full_box";
	String WEIGHT_FIELD = "WEIGHT";
	String CONTENTS_FIELD = "CONTENTS";
	String CONTENTS2_FIELD = "CONTENTS2";

	/* DRS L-SERIES DOX/ PPX PRINT START */
	String CONSG_TYPE_FIELD = "CONSG_TYPE";
	String CONTENTS3_FIELD = "CONTENTS3";
	String TOPAY_COD_LC_FIELD = "TOPAY_COD_LC";
	String TOPAY_COD_LC2_FIELD = "TOPAY_COD_LC2";
	String TOPAY_COD_LC3_FIELD = "TOPAY_COD_LC3";
	String TOPAY_COD_LC4_FIELD = "TOPAY_COD_LC4";
	String ADDRESS_1_FIELD = "ADDRESS_1";
	String ADDRESS_2_FIELD = "ADDRESS_2";
	String ADDRESS_3_FIELD = "ADDRESS_3";
	String MOBILE_NO_FIELD = "MOBILE_NO";

}
