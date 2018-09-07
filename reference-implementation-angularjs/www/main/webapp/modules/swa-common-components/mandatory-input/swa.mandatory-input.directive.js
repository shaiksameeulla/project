/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation and setting of an input value.
 * The model holding the value of the input will only be updated if the inputted values are valid
 *
 * The directive accepts 5 params:
 * @param ngModel - Reference to the data model that is holding the input value
 * @param callbackHandler - A callback function to run custom validation. This is added to the input parser pipeline.
 * @param fieldId - The fieldId to be associated with this input field
 * @param maxLength - The maximum length of the input value
 * @param min - The minimum length of the input value
 */
(function(app){

	app.directive("swaMandatoryInput", swaMandatoryInput);

	// swaMandatoryInput.$inject = [""];

	function swaMandatoryInput () {
		
		return {
			restrict: 'E',
			template: '<input type="text" ng-required class="inputWidthFull" ng-maxlength="{{maxLength}}" ng-minlength="{{min}}">',
			require: 'ngModel',
			scope: {
				model: "=ngModel",
				callbackHandler: "&",
				fieldId: "@",
				maxLength: "@",
				min: "@"
			},
			replace: true, // The directive is a wrapper for the input field, so remove the element after it has been compiled
			link: function (scope, element, attrs, ngModelCtrl, transclude){

				// Resolve the reference to callbackHandler
				scope.callbackHandler = scope.callbackHandler();

				// If callbackHandler Exists then add to the $parsers validation pipline
				// $parsers pipeline is run whenever the input field is updated by the view, this is the complemented by the $formatters pipeline
				if (scope.callbackHandler){
					ngModelCtrl.$parsers.push(function(value){

						// console.log("Input updated!");

						// RESULT OBJECT EXPECTED STRUCTURE
						// result = {
						// 	success: BOOLEAN,
						// 	errorCode: STRING
						// };

						var result = scope.callbackHandler(value);

						if (result.success){
							// Set model as valid (reset all other flags)
							ngModelCtrl.$setValidity("fieldDataValid", result.success); // Is there an angular form general invalid flag

							console.log("ngModelCtrl", ngModelCtrl);

							// Set all existing custom error flags are true
							// Ref: http://stackoverflow.com/a/25739987/4765841
							var errors = Object.keys(ngModelCtrl.$error);
							errors.forEach(function (prop){
								// Debug output:
								// console.log(prop, ngModelCtrl.$error[prop]);
								ngModelCtrl.$setValidity(prop, true);

							});

							return true;
						
						} else {
							// If the check result was not successful, then set the invalid flag

							// Check if errorCode exists, and if not then use a substitute
							result.errorCode ? result.errorCode : result.errorCode = "NO_CODE";

							// Append the error code to the fieldId to the errorCode --> fieldId + "-" + errorCode
							var validityKey = scope.fieldId + "-" + result.errorCode;

							console.log("validityKey: ", validityKey);
							ngModelCtrl.$setValidity(validityKey, false);

							return false;
						}

					});
				}

			}
		}
	};

})(angular.module("swaCustomComponents"));