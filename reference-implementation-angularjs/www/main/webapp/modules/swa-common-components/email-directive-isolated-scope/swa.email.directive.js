/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
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
(function(app){ 

app.directive('swaEmail', swaEmail);

var ERROR_MESSAGE = "Invalid Email";

function swaEmail(){
	return {
		restrict: 'A', //Use as an Attribute
        require: 'ngModel', //Require Ngmodel
        scope: {
          model: '=ngModel' //Isolated scope
        },
        replace: true, //Replacing the parent element with the template
        templateUrl: 'modules/swa-common-components/email-directive-isolated-scope/swa.email.directive.html', //Template URL for Swa-Email
        link: function (scope, elm, attrs, ctrl) { 
		if (!ctrl) return;
		//Intializing the email for the directive
		scope.email = '';
		//Function to test that the entered email value is valid or not
		scope.inputValidate = function (form) { 
			//Regex use to test the valid email
			var validEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(scope.email);
     		// Set validity based on the output
			form.email.$setValidity('invalidEmail', validEmail);
			//Consoling the output
			//To Do - Need to comment
			console.log("Email Validation", validEmail, ctrl.$valid);
			// If the Email is valid, then update the data model
			if(validEmail){
				scope.model = scope.email;
			}
		};
	}
 };
};
})(angular.module("swaCustomComponents"));