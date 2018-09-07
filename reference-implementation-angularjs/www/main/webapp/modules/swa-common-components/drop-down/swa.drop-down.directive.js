/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation and setting of a dropdown value.
 *
 * The directive accepts 5 params:
 * @param model - Reference to the data model that is holding the input value
 * @param options - Reference to the array of possible options in format of: [{ code: UNIQUE_STRING, value: STRING }]
 * @param defaultIndex - Optional value which sets the default value of the drop down to the array index value
 * @param blankOption - BOOLEAN, if false && defaultIndex is not provided, then the drop down selects the first 
 * element in the array. Otherwise, the drop down is initialized with a blank value
 */

/*
// @TODO: Shift from plain select statement to selectpicker.
// I have added the bootstrap-select library to the libraries directory. https://github.com/lordfriend/nya-bootstrap-select
// The module is called 'nya.bootstrap.select'. I have also loaded the ui.select library, but I was having problems trying to implement it. 
// Please decide on best course of action and re-implment based on your decision.
// The updated directive should behave exactly as currently implemented (obstacles to implementation should be documented) - Check the consingee-view template for a reference implementation
// <swa-drop-down model="DATA_MODEL_REF" options="[{ key: 'yes', value: 'yes'},{ key: 'no', value: 'no'}]" default-index="1" blank-option="false"></swa-drop-down>
*/

(function(app){

	app.directive("swaDropDown", swaDropDown);

	// swaDropDown.$inject = [""];

	function swaDropDown () {
		
		return {
			restrict: 'E',
			template: '<select ng-model="model" ng-options="option.value for option in options track by option.key"><option ng-value="option.value"></option></select>',
			require: 'ngModel',
			scope: {
				model: "=",
				options: "=",
				fieldId: "@",
				defaultIndex: "@",
				blankOption: "="
			},
			replace: true, // The directive is a wrapper for the input field, so remove the element after it has been compiled
			// @TODO: link functions should be named
			link: function (scope, element, attrs, ngModelCtrl, transclude){

				console.log("Linking dropDown directive: ",scope, element, attrs, ngModelCtrl);
				console.log("scope.defaultIndex && scope.blankOption", scope.defaultIndex, scope.blankOption, scope.model, scope.options);

				// If there is a default index given, and the blankOption flag was not set, then set the model to the default index
				if (scope.defaultIndex && !scope.blankOption){
					console.log("default setting: ", scope.defaultIndex, scope.options[scope.defaultIndex]);
					scope.model = scope.options[scope.defaultIndex].value;
				}

				// If there is no blank option, and there is no default index given, set the model to the first index if it exists
				else if (scope.blankOption === false){

					console.log("blank option is false: ", scope.blankOption, scope.options[0]);

					// Check if the first index exists
					if (scope.options[0]){
						scope.model = scope.options[0].value;
					}
				}

				else {
						// scope.model = "";
				}

			}
		}
	};

})(angular.module("swaCustomComponents"));