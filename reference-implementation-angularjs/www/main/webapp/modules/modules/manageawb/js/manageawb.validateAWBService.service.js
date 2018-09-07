// Incomplete, do not use as reference
(function(app){

	app.factory("ValidateAWBService", ValidateAWBService);

	ValidateAWBService.$inject = ["$rootScope"];

	function ValidateAWBService ($rootScope) {
		
		// API Declaration
		var service = {
			validateShipper: validateShipper
		};

		function validateShipper (){
			if (!$rootScope.AWB) {
				return { success: false, code: "AWB Not initialized"};
			}

			else return { success: true };
		};

		console.log("ValidateAWBService", service);

		return service;

	};

})(angular.module("CargoApp"));