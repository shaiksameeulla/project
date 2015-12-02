
package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface TrackingConstants.
 */

/**
 * @author mohammal
 *
 */
public interface TrackingConstants {
	
	//Added by Anwar
	/** The consignment booked by fr. */
	String CONSIGNMENT_BOOKED_BY_FR = "F";
	
	/** The consignment booked by dp. */
	String CONSIGNMENT_BOOKED_BY_DP = "D";
	
	/** The consignment booked by cc. */
	String CONSIGNMENT_BOOKED_BY_CC = "C";
	
	/** The consignment booked by pb. */
	String CONSIGNMENT_BOOKED_BY_PB = "P";
	
	/** The consignment booked by wc. */
	String CONSIGNMENT_BOOKED_BY_WC = "W";
	
	/** The packet manifest details outgoing. */
	int PACKET_MANIFEST_DETAILS_OUTGOING = 0;
	
	/** The doc bag manifest details outgoing. */
	int DOC_BAG_MANIFEST_DETAILS_OUTGOING = 1;
	
	/** The non doc bag manifest details outgoing. */
	int NON_DOC_BAG_MANIFEST_DETAILS_OUTGOING = 2;
	
	/** The master bag manifest details outgoing. */
	int MASTER_BAG_MANIFEST_DETAILS_OUTGOING = 3;
	
	/** The master bag manifest details incoming. */
	int MASTER_BAG_MANIFEST_DETAILS_INCOMING = 4;
	
	/** The non doc bag manifest details incoming. */
	int NON_DOC_BAG_MANIFEST_DETAILS_INCOMING = 5;
	
	/** The doc bag manifest details incoming. */
	int DOC_BAG_MANIFEST_DETAILS_INCOMING = 6;
	
	/** The packet manifest details incoming. */
	int PACKET_MANIFEST_DETAILS_INCOMING = 7;
	
	//Query Name
	/** The outgoing manifest details query. */
	String OUTGOING_MANIFEST_DETAILS_QUERY = "getOutMFDetails";
	
	/** The incomming manifest details query. */
	String INCOMMING_MANIFEST_DETAILS_QUERY = "getInMFDetails";
	
	/** The get manifest details params. */
	String GET_MANIFEST_DETAILS_PARAMS = "consgNumber,officeId,mnfstCode,manifestType";
	
	/** The utility tracking process booking. */
	String UTILITY_TRACKING_PROCESS_BOOKING = "Booking Details";
	
	/** The utility tracking process packet. */
	String UTILITY_TRACKING_PROCESS_PACKET = "Packet Details";
	
	/** The utility tracking process bag. */
	String UTILITY_TRACKING_PROCESS_BAG = "Bag Details";
	
	/** The utility tracking consignment query. */
	String UTILITY_TRACKING_CONSIGNMENT_QUERY = "Consignment No";
	
	/** The utility tracking ref query. */
	String UTILITY_TRACKING_REF_QUERY = "Reference No";
	
	/** The get booking details by cn query. */
	String GET_BOOKING_DETAILS_BY_CN_QUERY = "getBookingDetailsTrackingByConsgNo";
	
	/** The get booking details by cn param. */
	String GET_BOOKING_DETAILS_BY_CN_PARAM = "consgNum";
	
	/** The get booking details by ref query. */
	String GET_BOOKING_DETAILS_BY_REF_QUERY = "getBookingDetailsTrackingByRefNo";
	
	/** The get booking details by ref param. */
	String GET_BOOKING_DETAILS_BY_REF_PARAM = "refNum";
	
	/** The get agnt details by cn query. */
	String GET_AGNT_DETAILS_BY_CN_QUERY = "getAgentDetailsQuery";
	
	/** The get child consignment. */
	String GET_CHILD_CONSIGNMENT = "getChildConsignments";
	
	/** The get child consignment param. */
	String GET_CHILD_CONSIGNMENT_PARAM = "parentCN";
	
	/** The get consignment alert history. */
	String GET_CONSIGNMENT_ALERT_HISTORY = "getConsignmentsAlertHistory";
	
	/** The get manifest tracking details query. */
	String GET_MANIFEST_TRACKING_DETAILS_QUERY = "getMFTrackingDetails";
	
	/** The get manifest tracking details param. */
	String GET_MANIFEST_TRACKING_DETAILS_PARAM = "consgNumber,orgBranchCode,destBranchCode,mnfstCode,toDate,fromDate";
	
	/** The get manifest tracking by date query. */
	String GET_MANIFEST_TRACKING_BY_DATE_QUERY = "getMFTrackingByDate";
	
	/** The get manifest tracking by date param. */
	String GET_MANIFEST_TRACKING_BY_DATE_PARAM = "orgBranchCode,destBranchCode,toDate,fromDate";
	
	/** The Constant MANIFEST_TYPE. */
	public static final String MANIFEST_TYPE = "manifest";
	
	/** The Constant CONSIGNMENT_TYPE. */
	public static final String CONSIGNMENT_TYPE ="Consignment";
	
	/** The Constant PACKET_TYPE. */
	public static final String PACKET_TYPE ="packet";
	
	/** The Constant BAG_TYPE. */
	public static final String BAG_TYPE = "bag";
	
	/** The Constant MASTER_BAG_TYPE. */
	public static final String MASTER_BAG_TYPE = "master";
	
	/** The Constant CD_TYPE. */
	public static final String CD_TYPE = "cd";
	
	/** The Constant MANIFEST_TYPE_KEY. */
	public static final String MANIFEST_TYPE_KEY = "manifest.tracking.type";
	
	/** The Constant CONSIGNMENT_TYPE_KEY. */
	public static final String CONSIGNMENT_TYPE_KEY = "consignment.manifest.tracking.type";
	
	/** The Constant PACKET_TYPE_KEY. */
	public static final String PACKET_TYPE_KEY = "packet.manifest.tracking.type";
	
	/** The Constant BAG_TYPE_KEY. */
	public static final String BAG_TYPE_KEY = "bag.manifest.tracking.type";
	
	/** The Constant MASTER_BAG_TYPE_KEY. */
	public static final String MASTER_BAG_TYPE_KEY = "master.bag.manifest.tracking.type";
	
	/** The Constant CD_TYPE_KEY. */
	public static final String CD_TYPE_KEY = "cd.manifest.tracking.type";
	
	//Tracking Utility
	/** The Constant TRACKING_UTILITY_SUMMARY_HEADER_KEY. */
	public static final String TRACKING_UTILITY_SUMMARY_HEADER_KEY = "utility.tracking.report.summary.header";
	
	/** The Constant TRACKING_UTILITY_DETAILED_HEADER_KEY. */
	public static final String TRACKING_UTILITY_DETAILED_HEADER_KEY = "utility.tracking.report.detailed.header";
	
	/** The Constant MANIFEST_TYPE_SESSION_MAP_KEY. */
	public static final String MANIFEST_TYPE_SESSION_MAP_KEY = "manifestMap";
	
	//Utility Tracking Constants
	/** The Constant UTILITY_TRACKING_FILE_TYPE_CSV. */
	public static final String UTILITY_TRACKING_FILE_TYPE_CSV="CSV";
	
	/** The Constant UTILITY_TRACKING_FILE_TYPE_EXCEL. */
	public static final String UTILITY_TRACKING_FILE_TYPE_EXCEL="Excel";
	
	/** The Constant UTILITY_TRACKING_FILE_TYPE_XML. */
	public static final String UTILITY_TRACKING_FILE_TYPE_XML="Xml";
	
	/** The Constant UTILITY_TRACKING_REPORT_TYPE_SUMMARY. */
	public static final String UTILITY_TRACKING_REPORT_TYPE_SUMMARY="CN Status Summary";
	
	/** The Constant UTILITY_TRACKING_REPORT_TYPE_DETAILED. */
	public static final String UTILITY_TRACKING_REPORT_TYPE_DETAILED="CN Status Details";
	
	/** The Constant UTILITY_TRACKING_QUERY_ON_CN. */
	public static final String UTILITY_TRACKING_QUERY_ON_CN="CN #";
	
	/** The Constant UTILITY_TRACKING_QUERY_ON_REF. */
	public static final String UTILITY_TRACKING_QUERY_ON_REF="Ref #";
	
	
	//Headers for summary tracking utilities
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_CNNO. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_CNNO="CNNO";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_STATUS. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_STATUS="STATUS";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ATMPT_DATE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ATMPT_DATE="ATMPT DATE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ATMPT_TIME. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ATMPT_TIME="ATMPT TIME";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_DRS_NO. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_DRS_NO="DRS NO";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_POD_RCVD_THRO. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_POD_RCVD_THRO="POD RCVD THROUGH";
	
	//Headers for details tracking utilities
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_BOOKING_DATE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_BOOKING_DATE="Booking Date";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_BOOKING_TIME. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_BOOKING_TIME="Booking Time";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_NO1. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_NO1="PMF NO1";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DATE1. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DATE1="PMF DATE1";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE1. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE1="PMF ORG OFFICE1";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE1. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE1="PMF DEST OFFICE1";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_NO2. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_NO2="PMF No2";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DATE2. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DATE2="PMF DATE2";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE2. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE2="PMF ORG OFFICE2";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE2. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE2="PMF DEST OFFICE2";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_NO3. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_NO3="PMF No3";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DATE3. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DATE3="PMF DATE3";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE3. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_ORG_OFFICE3="PMF ORG OFFICE3";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE3. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_PMF_DEST_OFFICE3="PMF DEST OFFICE3";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ROBO_NO. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ROBO_NO="ROBO No";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ROBO_DATE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ROBO_DATE="ROBO DATE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ROBO_DEST_OFFICE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ROBO_DEST_OFFICE="ROBO DEST OFFICE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_RTO_MF_NO. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_RTO_MF_NO="RTO MF No";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_RTO_DATE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_RTO_DATE="RTO DATE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_RTO_DEST_OFFICE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_RTO_DEST_OFFICE="RTO DEST OFFICE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_DELIVERY_OFFICE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_DELIVERY_OFFICE="Delivery Office";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_POD_STATUS. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_POD_STATUS="POD STATUS";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_POD_DEL_DATE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_POD_DEL_DATE="POD DEL DATE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_RCVR_DTL. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_RCVR_DTL="RCVR DTL";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_DEL_TIME. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_DEL_TIME="DEL TIME";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_WEIGHT. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_WEIGHT="WEIGHT";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_NO_OF_PIECES_BOOKING. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_NO_OF_PIECES_BOOKING="NO.OF PIECES(Booking)";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_NO_OF_PIECES_DELIVERY. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_NO_OF_PIECES_DELIVERY="NO.OF PIECES(Delivery)";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ORIGIN_BR_CODE. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ORIGIN_BR_CODE="ORIGIN BR.CODE";
	
	/** The Constant UTILITY_TRACKING_REPORT_HEADER_ORIGIN_BR_NAME. */
	public static final String UTILITY_TRACKING_REPORT_HEADER_ORIGIN_BR_NAME="ORIGIN BR.NAME";
	
	
	/** The Constant TRACKING_SERVICE. */
	public static final String TRACKING_SERVICE = "trackingService";
	
	/** The Constant CONSIGNMENT_NO. */
	public static final String CONSIGNMENT_NO = "consignmentNumber";
	
	/** The Constant BOOKING_TYPE_INTERNATIONAL. */
	public static final String BOOKING_TYPE_INTERNATIONAL = "DD";
	
	/** The Constant BOOKING_TYPE. */
	public static final String BOOKING_TYPE = "bookingType";
	
	/** The Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";
	
	/** The Constant QUERY_GET_CONSIGNMENT_DETAILS. */
	public static final String QUERY_GET_CONSIGNMENT_DETAILS = "getConsignmentDetails";
	
	
	
}
