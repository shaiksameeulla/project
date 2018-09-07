/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This controller will manage all the actions related to the to consignee section.
 *
 * @param $scope - The $scope of the controller @TODO: Switch to controller as notation
 * @param $state - The $state service used by the ui.router
 * @param manageAWBService - Reference to the manageAWBService.
 * @param $rootScope - Rootscope of the application
 * @param $window - Injecting $window module
 */
(function(app){
	"use strict";

	app.controller("manageawb.consignee.controller", ConsigneeController);

	ConsigneeController.$inject = ['$scope', '$state', '$rootScope', '$window', 'ManageAWBService','AirwayBillDataService'];

	function ConsigneeController ($scope, $state, $rootScope, $window, ManageAWBService,AirwayBillDataService) {
        
		// CONSTANTS
		var DEFAULT_COUNTRY = "US";

		// Setup scope state
        $scope.AWB = AirwayBillDataService.AWB;

		// Mock data to hold country list
		$scope.countryList = [
			{key:"CA", value: "CA"},
			{key:"US", value: "US"},
			{key:"UK", value: "UK"},
			{key:"AN", value: "AN"},
			{key:"IN", value: "IN"},
			{key:"JP", value: "JP"},
			{key:"HK", value: "HK"},
		];

		// Defined scope interfaces
		$scope.initializeStateList = initializeStateList;
		$scope.getStateList = getStateList;
		$scope.isUSorCA = isUSorCA;
		$scope.stateList = $scope.getStateList(DEFAULT_COUNTRY);
		$scope.populateConsigneeDetails = populateConsigneeDetails;

		$scope.AWB.Consignee.consigneeAddress = {
			country: DEFAULT_COUNTRY
		};

		function initializeStateList () {
			$scope.AWB.Consignee.consigneeAddress.state = "";
			$scope.stateList = $scope.getStateList($scope.AWB.Consignee.consigneeAddress.country);
		}

		function getStateList (country){

			if (country === "US") {

				return [
					{key:"CA", value: "CA"},
					{key:"MI", value: "MI"},
					{key:"TX", value: "TX"},
					{key:"NY", value: "NY"}
				];

			} else if (country === "CA") {
				
				return [
					{key:"ON", value: "ON"},
					{key:"SK", value: "SK"},
					{key:"QC", value: "QC"},
					{key:"BC", value: "BC"}
				];

			}

			else {
				return [];
			}
		}

		function isUSorCA () {
			return /US|CA/.test($scope.AWB.Consignee.consigneeAddress.country);
		}

		function populateConsigneeDetails (data){
			console.log("populateConsigneeDetails");

			// $scope.consigneeIdInvalid = false;
			$scope.AWB.Consignee.locationId = data.locationID;
			$scope.AWB.Consignee.consigneeAddress = {
				addressLine1: data.streetAddressLine1,
				addressLine2: (data.streetAddressLine2 != '' && data.streetAddressLine2 != "NULL") ? data.streetAddressLine2 : '',
				city: data.city,
				country: data.country,
				state: data.stateProvince
			}

			$scope.AWB.Consignee.consigneeId = data.masterAcctNbr;
			$scope.previousConsigneeId = data.masterAcctNbr;
			$scope.AWB.Consignee.consigneeName = data.locationName;

			// TODO: COPY PASTED CODE BELOW TO BE CLEANED UP
			/**** BR1-For Known Shipper && Consignee Only ***/
            if ($scope.AWB.Consignee.consigneeAddress.country === "US") {
            	$scope.util = {};
				$scope.util.phNo1 = data.phone.substr(0, 1);
				$scope.util.phNo2 = data.phone.substr(1, 3);
				$scope.util.phNo3 = data.phone.substr(4, 3);
				$scope.util.phNo4 = data.phone.substr(7, 10);
				$scope.util.zip1 = data.postalCode.substr(0, 5);
				$scope.util.zip2 = data.postalCode.substr(5, 9).trim();

            } else if ($scope.AWB.Consignee.consigneeAddress.country === "CA") {
				$scope.util.phNo1 = data.phone.substr(0, 1);
				$scope.util.phNo2 = data.phone.substr(1, 3);
				$scope.util.phNo3 = data.phone.substr(4, 3);
				$scope.util.phNo4 = data.phone.substr(7, 10);
				$scope.AWB.Consignee.consigneeAddress.zipCode = data.postalCode;

			} else {
				$scope.AWB.Consignee.consigneeAddress.phoneNumber = data.phone;
				$scope.AWB.Consignee.consigneeAddress.zipCode = data.postalCode;
			}

			// Should this be here?
			$scope.stateList = $scope.getStateList($scope.AWB.Consignee.consigneeAddress.country);
			// State dropdown should be populated with the country is set ?
		}
	
	}

})(angular.module("CargoApp"));