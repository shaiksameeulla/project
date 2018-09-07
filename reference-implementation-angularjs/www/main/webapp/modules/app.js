(function (angular) {

	"use strict";

	// Declare Directive Module to be injected later
	angular.module("manageawb.directives", [])

	angular.module("CargoApp", ['ui.router', 'ui.bootstrap', 'ui.select', 'ngSanitize', 'swaCustomComponents', 'manageawb.directives', 'ngStorage', 'ngMessages'])

		.config(function ($stateProvider, $urlRouterProvider, $locationProvider) {

			$urlRouterProvider.otherwise("/");
			//$locationProvider.html5Mode(true);

			$stateProvider
				.state("awb", {
					url: "/",
					// Using nested views to handle section views
					// https://github.com/angular-ui/ui-router/wiki/Multiple-Named-Views
					views: {
						'header': {
							 controller: "manageawb.header.controller",
							templateUrl: "modules/modules/manageawb/tpls/manageawb.header-view.html"
						},
						'shipper': {
							controller: 'manageawb.shipper.controller',
							templateUrl: "modules/modules/manageawb/tpls/manageawb.shipper-view.html"
						},
						'consignee': {
							controller: 'manageawb.consignee.controller',
							templateUrl: "modules/modules/manageawb/tpls/manageawb.consignee-view.html"
						}
					}
				});

		})

		.run(function($rootScope, $document, $window, AuthorizationService, $localStorage, BlockUserInputService) {

			BlockUserInputService.setupGlobalListeners();

			// LOGIN AND RECEIVE ROLES
			var rolesAtLogin = [ 
				"CAPACITY PARAMETER CONSTRAINTS", 
				"VIEW ROUTES", 
				"VIEW CARGO HANDLING TIME PARAM", 
				"UPDATE CARGO HANDLING TIME PARAM"
			];
			// Copy roles to localStorage

			$localStorage.userRoles = rolesAtLogin;
			AuthorizationService.updateRoles('userLogin');
			// FINISHED ROLES SETUP FROM LOGIC

			// Initialize Active Section Data for accordian views
			$rootScope.sectionsActive = {
				shipper: true,
				consignee: false
			};

		});


})(angular);