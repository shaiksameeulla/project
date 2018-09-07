/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This service will manage all the actions related to making remote calls to the api endpoint.
 *
 * @param $http - Angular http request service
 * @param $q - Angular promise provider
 */
(function(app){
	"use strict";

	var DELAY = 1000;
	var DEBUG = true;

	app.factory('ManageAWBService', ManageAWBService);

	ManageAWBService.$inject = ['$http', '$q'];

	function ManageAWBService ($http, $q) {

		var sampleSuccessData = {
			data: {"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":51,"updateInfo":null,"errorCode":null,"errorMessage":null,"customerName":"CABIN SERVICES","masterAcctNbr":1,"aka":null,"businessType":"COMAT","mailingStreetAddress1":"2702 LOVE FIELD DRIVE","mailingStreetAddress2":"ZSS","city":"1        DALLAS","stateProvince":"TX","postalCode":"75235","country":"US","mailingPhoneNbr":"12147483687","contactName":null,"costCenter":"18000","ismasterAcctSuspended":false,"suspensionReason":null,"date":"","status":"ACTIVE","communicationType":null,"iataAgentNbr":"11-2-1234","salesManager":null,"svictpat":null,"location":[{"locationID":1,"locationName":"ATL INFLIGHT CREW BASE","masterAcctNbr":1,"streetAddressLine1":"3400 INNER LOOP ROAD","streetAddressLine2":"BRUSER STREET","city":"ATLANTA","stateProvince":"GA","postalCode":"30320","country":"US","contactName":null,"phone":"12147483647","email":"AAA.BB@CCC.COM","shippingStatus":"ACTIVE","shipmentsNbrLast6Mon":null,"securityType":"SWA-INT","tsaId":null,"ksmsStatus":null,"ksmsDate":null,"siteValidation":null,"siteValidationExpiration":null,"iacNbr":null,"iacExpiration":null,"tsc":false,"iataAgentNbr":null,"faaId":null,"primary":false,"flag":0,"agentAlert":null,"dupLocationCheck":false}],"email":{"masterAcctNbr":null,"email":"TEST@CARGOSYSTEM.COM","emailTypeId":null},"invoicingProfile":{"invoicingProfileId":1,"masterAcctNbr":null,"fop":null,"invoicingFrequency":null,"cassAgentNbr":null,"streetAddressLine1":"2702 LOVE FIELD DRIVE","streetAddressLine2":"TILL STREET","city":"DALLAS","stateProvince":"TX","postalCode":"75235","country":"US","phone":"12147483647","contactName":null,"email":null},"agentAlert":null,"comat":false}
		};

		var sampleSuccessCustomerSearch = {
			data: {"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":12,"updateInfo":null,"errorCode":null,"errorMessage":null,"customerName":"PROVISIONING","masterAcctNbr":2,"aka":null,"businessType":"CORPORATION","mailingStreetAddress1":"2702 LOVE FIELD DRIVE","mailingStreetAddress2":"SOUTHWEST HDQ","city":"DALLAS","stateProvince":"TX","postalCode":"75235","country":"US","mailingPhoneNbr":"12147484545","contactName":null,"costCenter":"COMMERCIAL","ismasterAcctSuspended":false,"suspensionReason":null,"date":null,"status":"ACTIVE","communicationType":null,"iataAgentNbr":null,"salesManager":null,"svictpat":null,"location":[{"locationID":2,"locationName":"ATL PROVISIONING","masterAcctNbr":2,"streetAddressLine1":"3400 INNER LOOP ROAD","streetAddressLine2":"SPACE G2-CARGO","city":"ATLANTA","stateProvince":"GA","postalCode":"30320","country":"US","contactName":null,"phone":"12147483647","email":null,"shippingStatus":"ACTIVE","shipmentsNbrLast6Mon":null,"securityType":"SWA-INT","tsaId":null,"ksmsStatus":null,"ksmsDate":null,"siteValidation":null,"siteValidationExpiration":null,"iacNbr":null,"iacExpiration":null,"tsc":false,"iataAgentNbr":null,"faaId":null,"primary":false,"flag":0,"agentAlert":null,"dupLocationCheck":false}],"email":{"masterAcctNbr":null,"email":"ASD@GHJK.NET","emailTypeId":null},"invoicingProfile":{"invoicingProfileId":1,"masterAcctNbr":null,"fop":"BILLABLE","invoicingFrequency":"MONTHLY","cassAgentNbr":null,"streetAddressLine1":"2702 LOVE FIELD DRIVE","streetAddressLine2":null,"city":"DALLAS","stateProvince":"TX","postalCode":"75235","country":"US","phone":"12147483647","contactName":"TEST","email":"TEST@GMAIL.COM"},"agentAlert":null,"comat":false}
		}

		var service = {
			validateShipperId: validateShipperId,
			getShipperData: getShipperData,
			searchConsigneeList: searchConsigneeList
		}

		var cache = {
			shippers: {
			}
		};

		return service;

		function validateShipperId (data) {

			return $q(function (resolve, reject) {
				setTimeout(function () {

					var masterAccountLocationKey = data.customerSearchAttributes.masterAcctNbr + 
						"_" + data.locationSearchAttributes.locationID;

					if (cache.shippers && cache.shippers[masterAccountLocationKey]) {
						var cachedValue = angular.copy(cache.shippers[masterAccountLocationKey]);
						resolve(cachedValue);

					} else {
						if (DEBUG) console.log("masterAccount not found in cache, making service call");
						
						resolve(getShipperData(data).then(function (response) {
							// Do some validation and return true or false

							if (DEBUG) console.log("Shipper data retrieved: ", response);
							if (DEBUG) console.log("Conducting validation on search results");

							if (response.data.location) {
								return true;
							} else {
								return false;
							}
							
						}));

					}

				}, DELAY);
			});
		}

		function getShipperData (data) {
			return $q(function(resolve, reject){

				if (DEBUG) console.log("manageAWBService.getShipperData called");
				
				setTimeout(function() {
					console.log("returning data...");

					if (data.customerSearchAttributes.masterAcctNbr == 1) {
						if (DEBUG) console.log("Returning: ", sampleSuccessData);
						resolve(angular.copy(sampleSuccessData));
										
					} else if (data.customerSearchAttributes.masterAcctNbr == 5000) {
						resolve({ data: {} });

					} else { 
						resolve(angular.copy(sampleSuccessCustomerSearch)); 
					}

				}, DELAY);
			});
		}

		function searchConsigneeList (data){
			return $q(function(resolve, reject){
				console.log("manageAWBService.searchConsigneeList called");
				
				setTimeout(function() {
					console.log("returning data...");
					resolve(angular.copy(sampleSuccessCustomerSearch));
				}, DELAY);
			});
		}
	
	}

})(angular.module("CargoApp"));