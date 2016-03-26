/*
 * 
 */
package com.cg.lbs.opsmanintg.constant;

public interface BcunDataFormaterConstants {

	/** The dispatch direction. */
	String DISPATCH_DIRECTION = "D";
	
	/** The receive direction. */
	String RECEIVE_DIRECTION = "R";
	
	/** The receive type local. */
	String RECEIVE_TYPE_LOCAL="L";
	
	/** The receive type outstation. */
	String RECEIVE_TYPE_OUTSTATION="O";
	
	
	/** The qry get manifest embeddedin by manifest dtls. */
	String QRY_GET_MANIFEST_EMBEDDEDIN_BY_MANIFEST_DTLS ="getManifestEmbeddedInByManifestDtls";
	
	/** The qry get manifestid by manifestno destofficeid manifesttype. */
	String QRY_GET_MANIFESTID_BY_MANIFESTNO_DESTOFFICEID_MANIFESTTYPE = "getManifestIdbyManifestNoDestOfficeIdManifestType";
	
	/** The qry get update manifest. */
	String QRY_GET_UPDATE_MANIFEST ="getUpdateManifest";	
	
	
	String QRY_GET_LOADCONNECTED_FOR_DISPATCH="getUnsyncInboundUpdateLoadConnected4Dispatch";

	String QRY_LOAD_MOVEMENT_ID_FOR_DISPATCH = "getLoadMovementId4Dispatch";
	String QRY_LOAD_MOVEMENT_ID_FOR_RECEIVE_LOCAL = "getLoadMovementId4ReceiveLocal";
	String QRY_LOAD_MOVEMENT_ID_FOR_RECEIVE_OUTSTATION = "getLoadMovementId4ReceiveOutStation";
	
	String QRY_UPDATE_LOADMOVEMENT_RECEIVE_LOCAL_GRID="getUnsyncInboundUpdateLoadMovement4ReceiveLocalGrid";	
	String QRY_LOADMOVEMENT_FOR_RECEIVE_OUTSTATION_GRID_DTLS="getUnsyncInboundUpdateLoadMovement4ReceiveOutStationGridDtls";	
	String QRY_GET_PROCESS_MAP_ID_BY_CONSIG_NUM ="getProcessMapIdByConsigNum";

	String QRY_GET_TRIP_SERVICED_BY_ID_4_FLIGHT = "getTripServicedById4Flight";
	String QRY_GET_TRIP_SERVICED_BY_ID_4_TRAIN = "getTripServicedById4Train";
	String QRY_GET_TRIP_SERVICED_BY_ID_4_VEHICLE = "getTripServicedById4Vehicle";
	
/*Stock Module BCUN Data Sync Constants Started */
	
	String STOCK_ISSUE_NUMBER = "stockIssueNumber";
	String ROW_NUMBER="rowNumber";
	String STOCK_ISSUE_ID ="stockIssueId";
	String CANCELLATIO_NNUMBER="cancellationNumber";
	String STOCK_TRANSFER_NUMBER="stockTransferNumber";
	
	String RETURN_NNUMBER="returnNumber";
	String STOCK_RETURN_ID="returnDo";
	String ACKNOWLEDGEMENT_NUMBER="acknowledgementNumber";
	
	String STOCK_RECEIPT_ID="stockReceiptDO";
	
	String QRY_GET_STOCK_ISSUE_ID = "getstockIssueId";
	String QRY_GET_STOCK_PAYMENT_ID = "getstockPaymentId";
	String QRY_GET_STOCK_ISSUE_ITEM_DTLS_ID = "getstockIssueItemDtlsId";
	String QRY_GET_STOCK_CANCELLED_ID="getstockCancelledId";
	
	String QRY_GET_STOCK_TRANSFER_ID = "getstockTransferId";
	
	String QRY_GET_STOCK_RETURN_ID="getstockReturnId";
	
	String QRY_GET_STOCK_RECEIPT_ID="getstockReceiptId";
	
	/*Stock Module BCUN Data Sync Constants Started */
	//Start - Create Runsheet Assignment
	String PICKUP_ASSIGNMENT_GENERATED="G";
	String PICKUP_RUNSHEET_CLOSE="C";
	String PICKUP_TYPE_REVERSE="R";
	String QRY_PARAM_CREATED_AT_OFFICE_ID="createdAtOfficeId";
	String QRY_PARAM_CREATED_FOR_OFFICE_ID="createdForOfficeId";
	String QRY_PARAM_EMPLOYEE_FIELD_STAFF_ID="employeeFieldStaffId";
	String QRY_PARAM_PICKUP_ASSIGNMENT_TYPE="pickupAssignmentType";
	String QRY_PARAM_PICKUP_ASSIGNMENT_CREATED_DATE="pkupAssignmentCreateDate";

	String QRY_PARAM_ORDER_NUMBER="orderNumber";
	String QRY_PARAM_PICKUP_LOCATION_CODE="pickupLocationCode";
	String QRY_PARAM_PICKUP_LOCATION_ID="pickupDlvLocationId";
	String QRY_PARAM_PICKUP_ASSIGNMENT_HEADER_ID="assignmentHeaderId";
	
	String QRY_GET_ASSIGNMENT_HEADER_ID="getAssignmentHeaderId";
	String QRY_GET_ASSIGNMENT_DETAIL_ID="getAssignmentDetailId";
	String QRY_GET_REVERSE_PICKUP_REQUEST_DETAIL_ID="getRevPickupRequestDetailId";
	//End - Create Runsheet Assignment
	
	//Start - Create Runsheet Assignment
	String QRY_PARAM_RUNSHEET_NUNBER="runsheetNo";
	String QRY_PARAM_ASSIGNMENT_DTL_ID = "pickupAssignmentDtlId";
	String QRY_GET_RUNSHEET_HEADER_ID="getRunsheetHeaderId";
	String QRY_GET_RUNSHEET_DETAIL_ID="getRunsheetDetailId";
	//End - Create Runsheet Assignment

	String QRY_GET_CONSIGNMENTDO__BY_CONS_ID ="getConsignmntDOByConsId";
	String QRY_STOCK_ISSUE_BY_NUMBER="getstockIssueDtlsByNumber";
	
	String QRY_DELETE_STOCK_ISSUE="deleteFromStockIssue";
	
	/***********Route Serviced By Start***************/

	String PARAM_TRANSPORT_MODE_CODE = "transportMode";
	String PARAM_REG_NUMBER = "regNumber";
	String PARAM_TRAIN_NUMBER = "trainNumber";
	String PARAM_FLIGHT_NUMBER = "flightNumber";
	String PARAM_ROUTE_ID = "routeId";
	String PARAM_ARRIVAL_TIME = "arrivalTime";
	String PARAM_DEPARTURE_TIME = "departureTime";
	String PARAM_TRANSPORT_MODE_ID = "transportModeId";
	String PARAM_TRAIN_ID = "trainId";
	String PARAM_VEHICLE_ID = "vehicleId";
	String PARAM_FLIGHT_ID = "flightId";
	String PARAM_SERVICE_BY_TYPE_ID = "serviceByTypeId";
	String PARAM_VENDOR_ID = "vendorId";
	String PARAM_EMPLOYEE_ID = "employeeId";
	String PARAM_SERVICE_BY_ID = "serviceById";
	String PARAM_TRIP_ID = "tripId";
	String PARAM_OPERATION_DAYS = "operationDays";
	String PARAM_EFFECTIVE_FROM = "effectiveFrom";
	
	String PARAM_EFFECTIVE_TO = "effectiveTo";
	
	String QRY_GET_TRANSPORT_DETAILS_BY_FLIGHT = "getTransportDetailsByFlight";
	String QRY_GET_TRANSPORT_DETAILS_BY_TRAIN = "getTransportDetailsByTrain";
	String QRY_GET_TRANSPORT_DETAILS_BY_ROAD = "getTransportDetailsByRoad";
	String QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_FLIGHT = "getTripIdByRouteTransportModeFlight";
	String QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_TRAIN = "getTripIdByRouteTransportModeTrain";
	String QRY_GET_TRIP_ID_BY_ROUTE_TRANSPORT_MODE_VEHICLE = "getTripIdByRouteTransportModeVehicle";
	String QRY_GET_SERVICED_BY_DETAILS_BY_VENDOR = "getServiceByDetailsByVendor";
	String QRY_GET_SERVICED_BY_DETAILS_BY_EMPLOYEE = "getServiceByDetailsByEmployee";
	String QRY_GET_TRIP_SERVICED_BY_ID_BY_TRIP_SERVICED_BY = "getTripServicedByIdByTripServicedBy";
	
	String QRY_GET_ALL_HUBS_OF_BRANCH_CITY="getAllHubsOftheBranchCity";
	String QRY_GET_ALL_BRANCH_OF_BRANCH_CITY="getAllBrOftheBranchCity";
	String QRY_GET_OFFICEID_BY_CUSTOMER_ID="getOfficeIdByCustomerId";
	
	String QRY_GET_OFFICE_ID_BY_SHIPPED_TO_CODE="getCustomerOfficeForStockBcun";
	
	String QRY_GET_OFFICEID_BY_CUSTOMER_ID_FOR_CONTRACT="getOfficeIdByCustomerIdForContract";
	
	String QRY_GET_ALL_OFFICE_BY_OFFICE_CITY="getAllOfficeByOfficeTypeAndCity";
	String QRY_GET_ALL_OFFICES_OF_THE_BRANCH_CITY="getAllOfficesOftheBranchCity";
	String QRY_GET_ALL_OFFICES_UNDER_LOGGED_IN_OFFICE="getAllOfficesUnderLoggedInOffice";
	String QRY_GET_ALL_OFFICE_FOR_STOCK_RECEIPT_OUTBOUND="getAllOfficeForStockReceiptOutbound";
	
	String QRY_SIBLINGS_OF_BRANCH="siblingsOfBranch";
	
	String QRY_GET_OFFICE_ID_BY_EMP_ID="getOfficeIdByEmpId";
	
	String QRY_GET_STOCK_TRANSFER_SHIPPED_TO_CODE_BY_CUSTOMER_ID="getStockTransferShippedCodeByCustomerId";
	
	String QRY_GET_ALL_HUBS_FORCUSTOEMR_WITH_LOGGIN_OFFICE="getAllHubsOfBranchCityAndLoggedInOfficeForStockCustomer";
	
	String QRY_GET_STOCK_ISSUE_SHIPPED_TO_CODE_BY_CUSTOMER_ID="getStockIssueShippedCodeByCustomerId";
	
	String QRY_GET_ALL_OFFICE_FROM_BRANCH_CONTRACT_FOR_STOCK_CUSTOMER="getAllOfficeFromBranchContractForStockCustomer";
	
	String QRY_GET_ALL_OFFICE_FROM_RHO_CONTRACT_FOR_STOCK_CUSTOMER="getAllOfficeFromRHOForContractStockCustomer";
	
	String QRY_PARAM_BRANCHID="branchId";
	String QRY_PRARAM_OFFICE_TYPE_CODE="officeTypeCode";
	
	String QRY_GET_OFFICE_TYPE_BY_OFFICE_ID="getOfficeTypeByOfficeId";
	
	//For Booking
	String EMOTIONAL_BOND_BOOKING = "EB";
	String QRY_GET_DESTINATIONS_OFFICES_EB_BOOKING = "outboundEmotionalBondBookingOfficeFinder";
	String QRY_PARAM_BOOKING_OFFICE_ID="bookingOfficeId";
	String QRY_PARAM_BOOKING_TYPE="bookingType";
	
	String QRY_PARAM_SHIPPEDTOCODE="shippedToCode";
	String QRY_PARAM_BRANCH_LIST="branchList";
	

	/***********Route Serviced By End***************/
	

	/*********** Rth Rto Consignment Return Start ***************/
	String QRY_GET_CONSIGNMENT_RETURN_ID_BY_CONSG_NO_OFFICE_ID_RETURN_TYPE = "getConsignmentReturnIdByConsgNoOfficeIdReturnType";
	String QRY_GET_CONSIGNMENT_RETURN_REASONS_ID_BY_CONSG_RETURN_ID_REASON_ID_DATE_TIME = "getConsignmentReturnReasonsIdByConsgReturnIdReasonIdDateTime";
	
	/*********** Rth Rto Consignment Return End *****************/
	
	String QRY_GET_OFFICE_EMAIL_BY_OFFICE_CODE="getOfficeEmailIdByOfficeCode";
	String QRY_GET_CONFIG_PARAM_VALUE_BY_NAME="getConfigurableValueByParam";
	
	String QRY_GET_FILE_RE_PROCESSING_EMAIL_ID="BCUN_FILE_RE_PROCESSING_RECEIVER_EMAIL_ID";
	String QRY_PARAM_BRANCH_CODE="officeCode";
	String QRY_PARAM_PARAM_NAME="paramName";
	
	String QRY_GET_CONSG_WISE_DELIVERY_DTLS_FOR_BCUN="getConsgwiseDeliveryDetailsForBcun";
	String QRY_PARAM_DRS_NUMBER="drsNumber";
}
