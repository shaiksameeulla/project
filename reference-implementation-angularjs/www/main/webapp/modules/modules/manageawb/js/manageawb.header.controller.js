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
 * @param AirwayBillDataService - service for maintaining the AWB data structure
 */
(function(app){
	"use strict";

	app.controller("manageawb.header.controller", HeaderController);

	HeaderController.$inject = ['$scope', 'AirwayBillDataService'];

	function HeaderController ($scope, AirwayBillDataService) {
        
        $scope.AWB = AirwayBillDataService.AWB;

	}

})(angular.module("CargoApp"));