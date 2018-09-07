
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

	app.factory('AuthorizationService', AuthorizationService);

	AuthorizationService.$inject = ['USER_PRIVILEGES', '$localStorage'];

	function AuthorizationService (USER_PRIVILEGES, $localStorage) {

		var DEBUG = false;

		var service = {
			loginAs: loginAs,
			initializeRoles: initializeRoles,
			updateRoles: updateRoles
		};

		// Initialize roles values
		function initializeRoles () {

			if (DEBUG) console.log("Running initializeRoles...");

			// Set all roles to 0, for false
			for (i in USER_PRIVILEGES) {
				USER_PRIVILEGES[i] = 0;
			}

		}

		// Set roles values
		function updateRoles (airWayBillEvent) {
			// Have logic that updates the roles based on the event being processed

			// Reset roles files
			initializeRoles();

			// If login event do this ...
			if (airWayBillEvent === 'userLogin') {
				var userRoles = $localStorage.userRoles;

				if (DEBUG) console.log("User Roles: ", userRoles);
				if (DEBUG) console.log("USER_PRIVILEGES: ", angular.copy(USER_PRIVILEGES));


				for (var i = 0; i < userRoles.length; i++) {
					// Loop through the userRoles and set them to 1
					var processedRole = "role_" + userRoles[i].replace(/\ /g, "_");

					// If the role exists, and has a value of 0, update to 1
					if (USER_PRIVILEGES[processedRole] === 0) {
						USER_PRIVILEGES[processedRole] = 1;
					}
				}

				if (DEBUG) console.log("Updated USER_PRIVILEGES: ", USER_PRIVILEGES);

			}

			// If save event do this...
			else if (airWayBillEvent === 'saveAirwayBill') {
				// On save event, update privileges based on the AWB Status
			}

			// If load event do this ...
			else if (airWayBillEvent === 'loadAirwayBill') {
				// One load event, update privileges based on the AWB Status 
			}
		
		}

		function loginAs (roleName) {
			USER_PRIVILEGES[roleName] = !USER_PRIVILEGES[roleName];
		} 

		return service;

	}

})(angular.module("CargoApp"));






