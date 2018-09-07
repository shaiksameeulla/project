/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation of Swa Date value.
 * The model holding the value of the Date will only be updated if the inputtedValues are valid
 * The directive accepts 1 params:
 * @param ngModel - Reference to the data model that is holding the Date value
 */
(function(app) {

  app.directive('swaDatePicker', swaDatePicker);

  function swaDatePicker() {
    return {
      restrict: 'A', //Use as an Attribute
      require: 'ngModel', //Require Ngmodel
      link: function(scope, element, attrs, ngModelCtrl) {
        $(function() {
          //configuring the date picker element
          element.datetimepicker({
            language: 'en',
            showToday: true,
            sideBySide: true,
            format: 'MM/DD/YYYY',
            autoclose: true,
            pickTime: false,
            pick12HourFormat: false,
            useCurrent: false,
            inputType: attrs["data"],
            onSelect: function(date) { //upating the viewValue on selection
              scope.$apply(function() {
                ngModelCtrl.$setViewValue(date);
              });
            }
          }).on('dp.change', function(e) { //change event
            $(this).blur();
			//Checking the inputted date is valid or not.
			var validDate = moment(ngModelCtrl.$viewValue, 'MM/DD/YYYY', true).isValid();
			//if the date is not valid, set the validity of invalidDate flag to true
			ngModelCtrl.$setValidity("invalidDate", !validDate);
          });
        });
      }
    };
  };
})(angular.module("swaCustomComponents"));