/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation of Military Time value.
 * The model holding the value of the Time will only be updated if the inputtedValues are valid
 * The directive accepts 1 params:
 * @param ngModel - Reference to the data model that is holding the Email value
 * @param mandatory - An optional string value to set the required attribute to the input field
 */
(function(myApp){ 

	myApp.directive('swaMilitaryTime', swaMilitaryTime);
	var ERROR_MESSAGE = "Invalid Time";

	function swaMilitaryTime(){
		return {
			restrict: 'A', //Use as an Attribute
	        require: 'ngModel', //Require Ngmodel
	        scope: {
	          model: '=ngModel', //Isolated scope
			  mandatory: '@' // One way binding of Mandatory field
	        },
	        replace: true, //Replacing the parent element with the template
	        templateUrl: 'modules/swa-common-components/military-time/swa.military-time.directive.html', //Template URL for Swa-time 
	        link: function (scope, elm, attrs, ctrl) {

				// Initialize the time object for the directive
				scope.time = '';

				//Function to test that the entered time value is valid or not and also append colon after 2 character. (Military Format HH:MM)
				scope.inputValidate = function (e, form) {
					var validTime = false; // Default the time is invalid

					//If the length is 2, append : to make the time in military format
		            if (scope.time.length == 2 && scope.time >= 0 && scope.time <= 23 && scope.time.indexOf(':') == -1 && !(/\s/g.test(scope.time)) && !(e.which == 8 || e.keyCode == 8)) {
						scope.time = scope.time + ':';
						validTime = false; 

					//If the length is 5 and the first part is less than 24 and second part is less than 59, make the validTime flag as true
		            } else if (scope.time.length == 5 && scope.time.substring(0, 2) <= 23 && scope.time.substring(3, 5) <= 59 && !(/\s/g.test(scope.time)) && !(e.which == 8 || e.keyCode == 8)) {
						scope.time = scope.time.substring(0, 2) + ':' + scope.time.substring(3, 5) ;
						validTime = true;
		            }

					// Set validity based on the output
					form.time.$setValidity('invalidTime', validTime);

					//Consoling the output
					// @TODO - Need to comment
					console.log("Time Validation", validTime, ctrl.$valid);

					// If the time is valid, then update the data model
					if(validTime){
						scope.model = scope.time;
					}
				};
			}
	 	};
	}

})(angular.module("swaCustomComponents"));