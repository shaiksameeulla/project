/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This service will manage all data.
 *
 * @param $http - Angular http request service
 * @param $q - Angular promise provider
 */
(function(app) {
    "use strict";

    app.factory('AirwayBillDataService', AirwayBillDataService);

    function AirwayBillDataService() {
        var dataService = {};

        dataService.AWB = {
            Header: {
                // Header Details
                awbPrefix: 527,
                awbNumber: 30055933,
                tenderDateGeneration: "04/20/2016",
                origin: "DAL",
                destination: "HOU",
                serviceLevel: "C",
                Fullstatus: "DRAFT",
                screeningStatus: "We is Screening?",
                printState: "Print State?",
                commentsStatus: "This is Comments Status"
            },
            Shipper: {
                // // Section 1 Data Model
                // 	shipperAddress: {
                // 		addressLine: "3400 INNER LOOP ROAD",
                // 		addressLine2: "BRUSER STREET",
                // 		city: "ATLANTA",
                // 		zipCode: "30320",
                // 		country: "US",
                // 		state: "GA",
                // 		phoneNumber: "12147483647"
                // 	},
                // 	primarySecurityType: "SWA-INT",
                // 	iacNumber: "N/A",
                // 	shipperName: "ATL INFLIGHT CREW BASE",
                // 	employeeNumber: null,
                // 	employeeHireDate: null,
                // 	locationId: "001",
                // 	masterActNumber: "000000001",
                // 	ccsf1: "DAL",
                // 	ccsf2: "L",
                // 	ccsf4: "99",
                // 	ccsf5: "9999",
                // 	ccsf6: "999",
                // 	idVerificationAssigned: false,
                // 	shipperReference: "",
                // 	idVerificationNumber: "",
                // 	cocFailure: "YES",
                // 	cocFailureReason: {
                // 		key: "EXPIRED CCSF ID",
                // 		value: "EXPIRED CCSF ID"
                // 	}
            },
            Consignee: {
                // // Section 2 Data Model
                // 	consigneeAddress: {
                // 		country: "US",
                // 		addressLine1: "3400 INNER LOOP ROAD", // Using .addressLine1 instead of .addressLine
                // 		addressLine2: "SPACE G2-CARGO",
                // 		city: "ATLANTA",
                // 		state: "GA",
                // 		phoneNumber: "12147483647",
                // 		zipCode: "30320"
                // 	},
                // 	consigneeId: "000000002",
                // 	locationId: "002",
                // 	consigneeName: "ATL PROVISIONING",
                // 	consigneeReference: ""
            },
            Shipment: {
                // Section 3 Data Model
                // lots: [{
                // 	"lotNumber":1,
                // 	"noOfPieces":2,
                // 	"actualWeight":10,
                // 	"volume":20,
                // 	"lines":[{
                // 		"lotNumber":1,
                // 		"seqNum":"LN1",
                // 		"noOfPieces":2,
                // 		"actualWeight":10,
                // 		"lineActWtKG":4.5,
                // 		"lineActWtLBS":10,
                // 		"volume":20,
                // 		"contentDesc":"MEDICAL SUPPLIES",
                // 		"lineSHC":"AVI",
                // 		"lineOrig":"DAL",
                // 		"lineDest":"HOU",
                // 		"pieces":[{
                // 			"seqNum":"LN1",
                // 			"seqNumber":1,
                // 			"displayName":"0001",
                // 			"actualWeight":5,
                // 			"piecesActWtLbs":5,
                // 			"piecesActWtKg":2.3,
                // 			"volume":10
                // 		},{
                // 			"seqNum":"LN1",
                // 			"seqNumber":2,
                // 			"displayName":"0002",
                // 			"actualWeight":5,
                // 			"piecesActWtLbs":5,
                // 			"piecesActWtKg":2.2,
                // 			"volume":10
                // 		}],
                // 		"uniqueLineNo":1
                // 	}],
                // 	"currentLotStatus":"",
                // 	"commodityCode":"0000",
                // 	"contentDesc":"MEDICAL SUPPLIES",
                // 	"routingInvalid":false
                // }]

            },
            Inspection: {
                // Section 4

            },
            Routing: {
                // Section 5

            },
            Comments: {
                // Section ?

            }
        };

        dataService.SLI = {
            Header: {
                // Header Details
                awbPrefix: 526,
                awbNumber: 30055933,
                tenderDateGeneration: "04/20/2016",
                origin: "DAL",
                destination: "HOU",
                serviceLevel: "C",
                Fullstatus: "DRAFT",
                screeningStatus: "We is Screening?",
                printState: "Print State?",
                commentsStatus: "This is Comments Status"
            },
            Shipper: {
                // // Section 1 Data Model
                // 	shipperAddress: {
                // 		addressLine: "3400 INNER LOOP ROAD",
                // 		addressLine2: "BRUSER STREET",
                // 		city: "ATLANTA",
                // 		zipCode: "30320",
                // 		country: "US",
                // 		state: "GA",
                // 		phoneNumber: "12147483647"
                // 	},
                // 	primarySecurityType: "SWA-INT",
                // 	iacNumber: "N/A",
                // 	shipperName: "ATL INFLIGHT CREW BASE",
                // 	employeeNumber: null,
                // 	employeeHireDate: null,
                // 	locationId: "001",
                // 	masterActNumber: "000000001",
                // 	ccsf1: "DAL",
                // 	ccsf2: "L",
                // 	ccsf4: "99",
                // 	ccsf5: "9999",
                // 	ccsf6: "999",
                // 	idVerificationAssigned: false,
                // 	shipperReference: "",
                // 	idVerificationNumber: "",
                // 	cocFailure: "YES",
                // 	cocFailureReason: {
                // 		key: "EXPIRED CCSF ID",
                // 		value: "EXPIRED CCSF ID"
                // 	}
            },
            Consignee: {
                // // Section 2 Data Model
                // 	consigneeAddress: {
                // 		country: "US",
                // 		addressLine1: "3400 INNER LOOP ROAD", // Using .addressLine1 instead of .addressLine
                // 		addressLine2: "SPACE G2-CARGO",
                // 		city: "ATLANTA",
                // 		state: "GA",
                // 		phoneNumber: "12147483647",
                // 		zipCode: "30320"
                // 	},
                // 	consigneeId: "000000002",
                // 	locationId: "002",
                // 	consigneeName: "ATL PROVISIONING",
                // 	consigneeReference: ""
            },
            Shipment: {
                // Section 3 Data Model
                // lots: [{
                // 	"lotNumber":1,
                // 	"noOfPieces":2,
                // 	"actualWeight":10,
                // 	"volume":20,
                // 	"lines":[{
                // 		"lotNumber":1,
                // 		"seqNum":"LN1",
                // 		"noOfPieces":2,
                // 		"actualWeight":10,
                // 		"lineActWtKG":4.5,
                // 		"lineActWtLBS":10,
                // 		"volume":20,
                // 		"contentDesc":"MEDICAL SUPPLIES",
                // 		"lineSHC":"AVI",
                // 		"lineOrig":"DAL",
                // 		"lineDest":"HOU",
                // 		"pieces":[{
                // 			"seqNum":"LN1",
                // 			"seqNumber":1,
                // 			"displayName":"0001",
                // 			"actualWeight":5,
                // 			"piecesActWtLbs":5,
                // 			"piecesActWtKg":2.3,
                // 			"volume":10
                // 		},{
                // 			"seqNum":"LN1",
                // 			"seqNumber":2,
                // 			"displayName":"0002",
                // 			"actualWeight":5,
                // 			"piecesActWtLbs":5,
                // 			"piecesActWtKg":2.2,
                // 			"volume":10
                // 		}],
                // 		"uniqueLineNo":1
                // 	}],
                // 	"currentLotStatus":"",
                // 	"commodityCode":"0000",
                // 	"contentDesc":"MEDICAL SUPPLIES",
                // 	"routingInvalid":false
                // }]

            },
            Inspection: {
                // Section 4

            },
            Routing: {
                // Section 5

            },
            Comments: {
                // Section ?

            }
        };

        return dataService;
    }

})(angular.module("CargoApp"));