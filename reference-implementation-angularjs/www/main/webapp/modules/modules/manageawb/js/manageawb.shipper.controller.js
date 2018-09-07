/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This controller will manage all the actions related to the to shipper section.
 *
 * @param $scope - The $scope of the controller @TODO: Switch to controller as notation
 * @param $state - The $state service used by the ui.router
 * @param manageAWBService - Reference to the manageAWBService.
 * @param $rootScope - Rootscope of the application
 * @param $window - Injecting $window module
 * @param ManageAWBService - 
 * @param $uibModal - ui.bootstrap modal service
 * @param $log - Angular logging service
 * @param $timeout - angular timeout service
 * @param USER_PRIVILEGES - cargo user privileges provider
 * @param CommonService - common service for awb module
 * @param AirwayBillDataService - service for maintaining the AWB data structure
 */
 (function(app){
	"use strict";

	app.controller("manageawb.shipper.controller", ShipperController);

	ShipperController.$inject = ['$scope', '$state', '$rootScope', '$window', 'ManageAWBService', '$uibModal', '$log', '$timeout', 'USER_PRIVILEGES', 'CommonService', 'AirwayBillDataService', 'AuthorizationService'];
	function ShipperController ($scope, $state, $rootScope, $window, ManageAWBService, $uibModal, $log, $timeout, USER_PRIVILEGES, CommonService, AirwayBillDataService, AuthorizationService) {		

		// Constants Declarations
		var DEBUG = true;

		// Initialize controller state
        $scope.AWB = AirwayBillDataService.AWB;
		$scope.PRIVILEGES = USER_PRIVILEGES;

		// @TODO Move service call to locationIdEntered function instead of shipperIdEntered

		// FIELD VALIDATION POC CODE -- FUNCTION STRUCTURE
		$scope.shipperIdEntered = function (shipperIdFormModel, focusOnNextElement) {

			return true;

			// Step 1 -- Pre-Action Logic Block (Standard Block)
			// Ensure the form validation has been passed (Standard Block)
			// Check all the dependent information requirements are met -- ie. locationId exists and is valid
			// In the pre-action Block UI and show a loading screen to notify the user the application is working

            var status = CommonService.preAction(shipperIdFormModel);
            if(!status){
                return false;
            }

			// Step 2 -- Action Logic Block (Specific to field and UC)
			// Trigger the Custom Validation function within the service that does the field validations
			// This function will also handle the chain of operations followed by the service call, i.e. Auto-population

			// Ex. Validation Service Call:
			// ManageAWBService.validateShipperId(shipperIdFormModel)

			// @TODO: Change to validate -> getshipper pattern with Service side caching

			// But for this use case, there is no custom validation, instead there is a service call that is made
			// And based on the response, an appropriate validity is set
			var data = {
				"awbNumber" : $scope.AWB.Header.awbNumber,
				"awbPrefix" : $scope.AWB.Header.awbPrefix,
				"customerSearchAttributes" : {
					"masterAcctNbr" : $scope.AWB.Shipper.masterActNumber
				},
				"locationSearchAttributes" : {
					"locationID" : $scope.AWB.Shipper.locationId
				}
			};
			ManageAWBService.getShipperData(data)
			.then(function (shipperData) {

				$log.info("Shipper Data returned", shipperData);

				// Check if the data was resolved correctly, and set it as valid if it does
				if (shipperData.data.location) {
					shipperIdFormModel.$setValidity('shipperIdExists', true);

					var shipperData = shipperData.data;

					// Auto-populate Form Data based on retrieved values
					$scope.AWB.Shipper.shipperName = shipperData.location[0].locationName;
					$scope.AWB.Shipper.shipperAddress = {
						addressLine: shipperData.location[0].streetAddressLine1,
						addressLine2: shipperData.location[0].streetAddressLine2,
						city: shipperData.location[0].city,
						zipCode: shipperData.location[0].postalCode,
						country: shipperData.location[0].country,
						state: shipperData.location[0].stateProvince,
						phoneNumber: shipperData.location[0].phone
					}

					return true;

				} else {
				// If it didn't resolve correctly, set the invalid flag on the form controller
					shipperIdFormModel.$setValidity('shipperIdExists', false);

					return false;
				}

			}).finally(function (success) {
			// Step 3 -- Post-Action logic block (Standard Block)
			// Trigger the focus event if applicable, and Release the UI
			// This must be inside a finally block so that the UI is always released no matter what the error/precursive flow
			// If necessary, also ensure the digest cycle is triggered

				$log.info("Final Block: ", success, focusOnNextElement);                
                CommonService.postAction(focusOnNextElement);

			});

		}

		$scope.locationIdEntered = function(shipperIdFormModel, locationIdFormModel, focusOnNextElementExt, focusOnNextElementEsp) {

			if (DEBUG) console.log("locationIdEntered: ", locationIdFormModel, shipperIdFormModel, focusOnNextElementExt, focusOnNextElementEsp);

			// Step #1 - Pre-Action -- Ensure models are valid, and block UI if models are valid
			var status = CommonService.preAction(locationIdFormModel, shipperIdFormModel);
            if(!status){
                return false;
            }

            // Pre-Action successful: UI blocked, continue with backend call
            if (DEBUG) console.log("Pre-Action successful: ", status);

            // Step #2 - Action
            // Part 1: Validate the details from the backend
            var data = {
				"awbNumber" : $scope.AWB.Header.awbNumber,
				"awbPrefix" : $scope.AWB.Header.awbPrefix,
				"customerSearchAttributes" : {
					"masterAcctNbr" : $scope.AWB.Shipper.masterActNumber
				},
				"locationSearchAttributes" : {
					"locationID" : $scope.AWB.Shipper.locationId
				}
			};

			ManageAWBService.validateShipperId(data)
			.then(function (valid) {

				if (DEBUG) console.log("validateShipperId response: ", valid);

				// If invalid, change the form control values
				if (valid) {

					if (DEBUG) console.log("shipperId was valid, get shipper details...");

					shipperIdFormModel.$setValidity('shipperIdExists', true);

					// If valid, get the shipper details from the service
					return ManageAWBService.getShipperData(data).then(function (shipperData) { 
						return shipperData; 
					});

				} else {
					shipperIdFormModel.$setValidity('shipperIdExists', false);
					throw new Error("Invalid Shipper ID");
				}

			})
			.then(function (shipperData) {
			// Process response from the shipper data call

				if (DEBUG) console.log("shipperData returned: ", shipperData);

				shipperData = shipperData.data;

				// Check if the data was resolved correctly, and set it as valid if it does
				// if (shipperData.location) {		

					// Auto-populate Form Data based on retrieved values
					$scope.AWB.Shipper.shipperName = shipperData.location[0].locationName;
					$scope.AWB.Shipper.shipperAddress = {
						addressLine: shipperData.location[0].streetAddressLine1,
						addressLine2: shipperData.location[0].streetAddressLine2,
						city: shipperData.location[0].city,
						zipCode: shipperData.location[0].postalCode,
						country: shipperData.location[0].country,
						state: shipperData.location[0].stateProvince,
						phoneNumber: shipperData.location[0].phone
					}

					return true;

				// }


			}).finally(function () {
			// Step #3 - Post-Action -- Release UI and Loading Screen

				if (DEBUG) console.log("locationIdEntered finally block");

				// Determine which element to focus on next:

				CommonService.postAction(focusOnNextElementEsp);
			});


		}

		$scope.showShipperSearchModal = function (size) {

			console.log("showShipperSearchModal", $scope.Shipper.masterActNumber);

		};
		// FIELD VALIDATION POC CODE



		$scope.loginAs_MANAGEGROUPPRIVILEGE = function () {
			console.log("loginAs_MANAGEGROUPPRIVILEGE");
			AuthorizationService.loginAs('role_MANAGEGROUPPRIVILEGE');
		}

		$scope.loginAs_VIEWREFERENCEDATA = function () {
			console.log("loginAs_VIEWREFERENCEDATA");
			AuthorizationService.loginAs('role_VIEWREFERENCEDATA');
		}

	
	}

})(angular.module("CargoApp"));