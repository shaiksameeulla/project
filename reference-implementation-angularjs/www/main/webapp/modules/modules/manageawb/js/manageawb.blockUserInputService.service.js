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

	app.factory('BlockUserInputService', BlockUserInputService);

	BlockUserInputService.$inject = ['$window', '$document'];

	function BlockUserInputService ($window, $document) {

		var DEBUG = false;

		var service = {
			setupGlobalListeners: setupGlobalListeners,
			blockUserInterface: blockUserInterface,
			releaseUserInterface: releaseUserInterface
		};

		return service;

		function setupGlobalListeners () {

			if (DEBUG) console.log("Setting up global listeners for BlockUserInputService...");

			// Setup $window.GLOBALS
			if ($window.GLOBALS) {
				$window.GLOBALS.BLOCK_INPUT = false;

			} else {
				$window.GLOBALS = { BLOCK_INPUT: false };

			}

			// Hook up global block logic for blocking user interaction
			$document.on('keydown', function (e) {

				if (DEBUG) console.log("Document 'keydown' event triggered");

				if ($window.GLOBALS.BLOCK_INPUT === true) {
					if (DEBUG) console.warn("INPUT BLOCKED");
					e.preventDefault();
				}
			});

			$document.on('click', function (e) {

				if (DEBUG) console.log("Document 'click' event triggered");

				if ($window.GLOBALS.BLOCK_INPUT === true) {
					if (DEBUG) console.warn("INPUT BLOCKED");
					e.preventDefault();
				}
			});

		}

		function blockUserInterface () {}

		function releaseUserInterface () {}
	}

})(angular.module("CargoApp"));