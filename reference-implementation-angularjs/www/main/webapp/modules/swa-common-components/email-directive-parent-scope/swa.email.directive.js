/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation and setting of the Email value.
 * The model holding the value of the Email will only be updated if the inputtedValues are valid
 * The directive accepts 1 params:
 * @param ngModel - Reference to the data model that is holding the Email value
 */
(function(myApp){ 

	myApp.directive('swaEmail', swaEmail);

	function swaEmail(){
		return {
			restrict: 'A', //Use as an Attribute
	        require: 'ngModel', //Require Ngmodel
	        replace: true, //Replacing the parent element with the template
	        templateUrl: 'modules/swa-common-components/email-directive-parent-scope/swa.email.directive.html', //Template URL for Swa-Email
	        link: function (scope, elm, attrs, ctrl) { 

				//Function to test that the entered email value is valid or not
				elm.bind('change', function(){
					//Regex use to test the valid email
					var validEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(ctrl.$viewValue);
		     		
		     		// Set validity based on the output
					ctrl.$setValidity('invalidEmail', validEmail);
					
					//Consoling the output
					// @TODO - Need to comment
					console.log("Email Validation", validEmail, ctrl.$valid);
				});
			}
		 };
	}

})(angular.module("swaCustomComponents"));