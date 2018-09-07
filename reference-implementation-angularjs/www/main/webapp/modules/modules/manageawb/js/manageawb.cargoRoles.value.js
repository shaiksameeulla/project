
/**
* Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
* 75235, U.S.A. All rights reserved.
* 
 * This software is the confidential and proprietary information of Southwest
* Airlines, Co.
**/

/**
* @CodeReviewFix:03-2016 
 * This file was modified to address code review comments for Build 2. Above tab is added to the modified sections of the code.
*
*/

/**PROPERTY FILES FOR LABELS**/
(function (app) {

	app.constant('USER_PRIVILEGES', {
		role_RM : 0,
		role_SUPERVISOR : 0,
		// privilage_change_flight_capacity : 0,
		// privilage_flight_capacity_override : 0,
		role_VIEWREFERENCEDATA : 0,
		role_UPDATEREFERENCEDATA : 0,
		role_UPDATECARGOFACILITY : 0,
		role_UPDATECARGOCONTACT : 0,
		role_VIEWCARGODATA : 0,
		role_UPDATE_WAREHOUSE_LOCATIONS : 0,
		role_VIEW_WAREHOUSE_LOCATIONS : 0,
		role_MANAGEGROUPPRIVILEGE : 0,
		role_CAPACITY_PARAMETER_CONSTRAINTS : 0,
		role_VIEW_ROUTES : 0,
		role_VIEW_CARGO_HANDLING_TIME_PARAM : 0,
		role_UPDATE_CARGO_HANDLING_TIME_PARAM : 0,
		role_UPDATE_RESTRICTIONS : 0,
		role_VIEW_RESTRICTIONS : 0,
		role_UPDATE_LIMITATIONS : 0,
		role_VIEW_LIMITATIONS : 0,
		role_UPDATE_BOOKING_WINDOW_SETUP : 0,
		role_VIEW_BOOKING_WINDOW_SETUP : 0,
		role_UPDATE_INTERLINE_FLIGHT_FILTER_PARAM : 0,
		role_UPDATE_CAPACITY_RULES_AND_ALLERTS : 0,
		role_UPDATE_CAPACITY_EXCEPTIONS : 0,
		role_VIEW_CAPACITY_EXCEPTIONS : 0,
		role_CHANGE_FLIGHTCAPACITY : 0,
		role_MODIFY_DELETE_SCREENING_DETAILS : 0,
		role_MY_FACILITY : 0,
		role_TMP_USER_ASSGN : 0,
		role_VIEW_CARGO_HOMEPAGE : 0,
		role_TEMP_ONLY_MY_FACILITY : 0,
		role_TEMP_ALL_FACILITIES : 0,
		role_VIEW_FIT : 0,
		role_UPDATE_FIT : 0,
		role_SEARCH_CUST : 0,
		role_GENERAL_VIEW : 0,
		role_LOCATION_VIEW : 0,
		role_INVOICING_PROFILE_VIEW : 0,
		role_GENERAL_EDIT : 0,
		role_LOCATION_EDIT : 0,
		role_INVOICING_PROFILE_EDIT : 0,
		role_CREATE_AWB : 0,
		role_CREATE_AWB_LINK : 0,
		/**UC17**/
		role_VIEW_LOCKED_FLIGHT_AWB : 0,
		role_LOCK_FLIGHT_AWB : 0,
		role_UNLOCK_FLIGHT_AWB : 0,
		role_EMER : 0,
		/**UC42**/
		role_VIEW_USER_ACTIVITY_LOG : 0,
		role_DOWNLOAD_AUDIT_LOG : 0,
		/**UC45**/
		role_UPDATE_ASSIGN_AWB : 0,
		role_VIEW_ASSIGN_AWB : 0,
		role_VIEW_AND_SEARCH_RESEARCH_AWB : 0,
		/***AWB-PRIVILEGES**/
		role_VOID_AWB : 0,
		role_PRINT_LOT_LABELS : 0,
		role_CREATE_ID_VERIFICATION_FORM : 0,
		role_CREATE_1196_RELEASE_FORM : 0,
		role_AWB_DRFT_SECTION1_UPDATE : 0,
		role_AWB_DRFT_SECTION1_READ : 0,
		role_AWB_DRFT_SECTION2_UPDATE : 0,
		role_AWB_DRFT_SECTION2_READ : 0,
		role_AWB_DRFT_SECTION3_UPDATE : 0,
		role_AWB_DRFT_SECTION3_READ : 0,
		role_AWB_DRFT_SECTION4_UPDATE : 0,
		role_AWB_DRFT_SECTION4_READ : 0,
		role_AWB_DRFT_SECTION5_UPDATE : 0,
		role_AWB_DRFT_SECTION5_READ : 0,
		role_AWB_DRFT_SECTION6_UPDATE : 0,
		role_AWB_DRFT_SECTION6_READ : 0,
		role_AWB_DRFT_SECTION7_UPDATE : 0,
		role_AWB_DRFT_SECTION7_READ : 0,
		role_AWB_DRFT_SECTION8_UPDATE : 0,
		role_AWB_DRFT_SECTION8_READ : 0,
		role_AWB_DRFT_SECTION9_UPDATE : 0,
		role_AWB_DRFT_SECTION9_READ : 0,
		role_AWB_DRFT_SECTION10_UPDATE : 0,
		role_AWB_DRFT_SECTION10_READ : 0,
		/**UC24**/
		role_VIEW_INBOUND_SHIPMENTS : 0,
		role_VIEW_TRANSFER_SHIPMENTS : 0,
		role_VIEW_PICKUP_SHIPMENTS : 0,
		role_VIEW_MISSING_SHIPMENTS : 0,
		role_RECEIVE_SHIPMENT : 0,
		role_DELIVER_SHIPMENT : 0,
		role_ASSIGN_RUNNER : 0,
		role_PRINT_RUNNER_ASSIGNMENTS : 0,
		role_VIEW_MANIFESTED_FLIGHTS_INBOUND : 0,
		role_UPDATE_AIR_WAYBILL_FLIGHT_STATUS_UPDATES : 0,
		/**UC25**/
		role_VIEW_OUTBOUND_SHIPMENTS : 0,
		role_VIEW_MANIFESTED_FLIGHTS_OUTBOUND : 0,
		/**UC26**/
		role_VIEW_AIR_WAYBILL_HISTORY : 0,
		role_CANCEL_AWB : 0,
		role_VIEW_DISCREPANCY_TAB : 0,
		role_SEARCH_AIR_WAYBILL : 0,
		/**UC27**/
		role_MARK_DISCREPANCY_SHIPMENT_MISSING : 0,
		role_MARK_DISCREPANCY_SHIPMENT_TRACE : 0,
		role_MARK_DISCREPANCY_SHIPMENT_LOST : 0,
		/**UC 009B**/
		role_FORCED_BOOKING_HOME : 0,
		role_FORCED_BOOKING_ALL : 0,
		/**UC 77**/
		role_COMMODITY_TO_DESCRIPTION : 0,
		role_SERVICELEVEL_TO_BUCKET : 0
	});

})(angular.module("CargoApp"));