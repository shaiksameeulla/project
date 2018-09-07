
/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/**
 * This controller acts as a parent controller for Shipment Management screen
 * Non standard Angular Paramters that are passed to this Controller are listed below with their purpose
 
 *  @constructor
 *  @param ShipmentManagementController - Name of the controller
 *  @param constructor function;
 *  @param {Scope} $scope - controller scope
 *  @param {Root Scope} $rootScope - root level scope
 *  @param {shipmentMgmtService} shipmentMgmtService - Factory for shipment management services
 *  @param {modal} $modal - Modal Service
 *  @param {localStorage} $localStorage - local storage
 *  @param {filter} $filter - Filter service
 *  @param {errorconstant} ERRORMESSAGES - Error message constants
 *  @param {focus} focus - Factory object for focus
 *  @param {focusAttrib} focusAttrib - Factory object for focus for custom dropdown directive
 *  @param CONFIGURATIONS - config.js configurations
 *  @param {roleconstants} roles - list of roles
 *  @param {fetchAirportData} fetchAirportData - Service to fetch airport details
 *  @param ServiceURL  - Service to fetch base URL
*/
(function(){
	"use strict";
	angular
        .module('CargoApp')
        .controller('ShipmentManagementController', ShipmentManagementController);
	
	ShipmentManagementController.$inject = ['$scope', '$filter', '$modal', 'shipmentMgmtService', 'ERRORMESSAGES', '$localStorage', '$rootScope', 'fetchAirportData', 'focus', 'focusAttrib', '$timeout', 'ServiceURL', '$interval', 'CONFIGURATIONS', 'roles', '$location'];

	function ShipmentManagementController ($scope, $filter, $modal, shipmentMgmtService, ERRORMESSAGES, $localStorage, $rootScope, fetchAirportData, focus, focusAttrib, $timeout, ServiceURL, $interval, CONFIGURATIONS, roles, $location) {
		var vm = this;
		
		vm.init = init;
		vm.facilityChanged 
		vm.loadInboundData = loadInboundData;
		vm.loadOutboundData = loadOutboundData;
		vm.loadTransferData = loadTransferData;
		vm.loadDeliveredData = loadDeliveredData;
		vm.loadMissingData = loadMissingData;
		vm.validateStationCode = validateStationCode;
		vm.role = roles;
		vm.errorLabel = ERRORMESSAGES;

		// @TODO: Should this be moved inside of the init method?
		vm.shipmentInboundSearch = {
			"facility" : $localStorage.userObject.swaLocation === CONFIGURATIONS.shipmentManagent.HDQ ? '' : $localStorage.userObject.swaLocation
		};
		
		function init() {
			//TODO:: To call an authorization service for loggedin user privileges. Based on privilege different tabs will be visible. 
			loadInboundData()
		}
		
		/**Load Inbound tab, This method takes care updating COMETD channel and initiate loading inbound data
		**/
		function loadInboundData() {

			if (vm.facilityChanged) {
				vm.selectedStation = vm.shipmentInboundSearch.facility;

			} else {
				vm.selectedStation = $localStorage.userObject.swaLocation;
			}

			vm.currentTab = CONFIGURATIONS.shipmentManagent.tabInbound;

			if (!vm.isSubscribed) {
				//$scope.subscribe($scope.selectedStation);
			} else {
				//$scope.updateSubscription();
			}
			
			$scope.$broadcast('fetchingInboundData');
		};
		
		/**Load outbound tab starts , This method takes care updating COMETD channel and initiate loading outbound data
		**/
		function loadOutboundData() {

			if (vm.facilityChanged) {
				vm.selectedStation = vm.shipmentInboundSearch.facility;

			} else {
				vm.selectedStation = $localStorage.userObject.swaLocation;
			}

			vm.currentTab = CONFIGURATIONS.shipmentManagent.tabOutbound;

			if (!vm.isSubscribed) {
				//$scope.subscribe($scope.selectedStation);
			} else {
				//$scope.updateSubscription();
			}

			$scope.$broadcast('fetchingOutboundData');
		};
		
		/**Load transfer tab starts , This method takes care updating COMETD channel and initiate loading transfer data
		 **/
		function loadTransferData() {

			if (vm.facilityChanged) {
				vm.selectedStation = vm.shipmentInboundSearch.facility;
			} else {
				vm.selectedStation = $localStorage.userObject.swaLocation;
			}
			vm.currentTab =  CONFIGURATIONS.shipmentManagent.xfr;
			if (!vm.isSubscribed) {
				//$scope.subscribe($scope.selectedStation);
			} else {
				//$scope.updateSubscription();
			}

			$scope.$broadcast('fetchingTransferData');
		};

		/**Load delivered tab starts , initiate loading delivered data
		 **/
		function loadDeliveredData() {
			vm.currentTab =  CONFIGURATIONS.shipmentManagent.tabDelivered;
			$scope.$broadcast('fetchingDeliverData');
		};
		
		/**Load missing tab starts ,it initiate loading missing data
		 **/
		function loadMissingData() {
			vm.currentTab = CONFIGURATIONS.shipmentManagent.tabMissing;
			$scope.$broadcast('fetchingMissingData');
		};
	
		/**This function is to check if selected station is not same as home station . Remove privilage.
		 * @param selectedStation
		 **/
		function validateStationCode(selectedStation) {
			vm.selectedStation = selectedStation;
			if (selectedStation !== $localStorage.userObject.swaLocation) {
				//$scope.getFreshState();
				vm.facilityChanged = true;

			} else {
				vm.facilityChanged = false;
			}

			vm.shipmentFacilityChanged = vm.errorLabel.facilityChanged + vm.selectedStation;

			// @TODO: Why are we using a timeout? So that broadcast event can be resolved?
			$timeout(function () {
				vm.shipmentFacilityChanged = '';
				//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
			}, CONFIGURATIONS.TIMEOUT_FIVE_THOUSAND_MS);

			switch (vm.currentTab) {
				// What case is this?
				case 'xfr':
					if (!vm.isSubscribed) {
						//$scope.subscribe($scope.selectedStation);
					} else {
						//$scope.updateSubscription();
					}
					$scope.$broadcast('fetchingTransferDataForStationChanged');
					break;

				// Case descriptions?
				case 'outbound':
					if (!vm.isSubscribed) {
						//$scope.subscribe($scope.selectedStation);
					} else {
						//$scope.updateSubscription();
					}
					$scope.$broadcast('fetchingOutboundDataForStationChanged');
					break;

				case 'missing':
					$scope.$broadcast('fetchingMissingDataForStationChanged');
					break;

				case 'delivered':
					$scope.$broadcast('fetchingDeliverDataForStationChanged');
					break;

				default:
					//$scope.getDataBasedOnState();
			}

		};
	
	}
	
})();
