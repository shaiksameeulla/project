package com.ff.admin.heldup.constants;

import com.ff.universe.constant.UdaanCommonConstants;

// TODO: Auto-generated Javadoc
/**
 * The Interface HeldUpConstants.
 *
 * @author narmdr
 */
public interface HeldUpConstants {

	/** The url view held up. */
	String URL_VIEW_HELD_UP = "viewHeldUp";
	
	/** The held up service. */
	String HELD_UP_SERVICE = "heldUpService";
	
	/** The user. */
	String USER = "user";
	
	/** The reason type for held up. */
	String REASON_TYPE_FOR_HELD_UP = UdaanCommonConstants.REASON_TYPE_FOR_HELD_UP;
	
	/** The reason type for held up location. */
	String REASON_TYPE_FOR_HELD_UP_LOCATION  = UdaanCommonConstants.REASON_TYPE_FOR_HELD_UP_LOCATION ;

	/** The transaction type consignment. */
	String TRANSACTION_TYPE_CONSIGNMENT = "CONG";
	
	/** The transaction type open manifest. */
	String TRANSACTION_TYPE_OPEN_MANIFEST = "OPMF";
	
	/** The transaction type ogm. */
	String TRANSACTION_TYPE_OGM = "OGMF";
	
	/** The transaction type bpl document. */
	String TRANSACTION_TYPE_BPL_DOCUMENT = "BPLD";
	
	/** The transaction type bpl parcel. */
	String TRANSACTION_TYPE_BPL_PARCEL = "BPLP";
	
	/** The transaction type mbpl. */
	String TRANSACTION_TYPE_MBPL = "MBPL";
	
	/** The transaction type mbl dispatch. */
	String TRANSACTION_TYPE_MBL_DISPATCH = "DSPT";
	
	/** The transaction type awb cd rr. */
	String TRANSACTION_TYPE_AWB_CD_RR = "AWBR";
	
	/** The std type name for heldup transaction type. */
	String STD_TYPE_NAME_FOR_HELDUP_TRANSACTION_TYPE = "HELDUP_TRANSACTION_TYPE";

	String HELD_UP_SAVED = "H001";

	String ERROR_IN_SAVING_HELD_UP = "H002";
	String INVALID_HELD_UP_NUMBER = "H003";
	
	//Query
	String QRY_GET_HELD_UP_BY_HELD_UP_NO_AND_OFFICE_ID = "getHeldUpByHeldUpNoAndOfficeId";
	
	//param for query

	String PARAM_OFFICE_ID = "officeId";
	String PARAM_HELD_UP_NUMBER = "heldUpNumber";
	String ERROR_IN_LOADING_PAGE = "H004";
	
}
