/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation and setting of the postalCode value.
 * The model holding the value of the postalCode will only be updated if the inputtedValues are valid
 * The directive accepts 3 params:
 * @param ngModel - Reference to the data model that is holding the postalCode value
 * @param countryCode - A string value representing the two letter country code of the 
 * @param labelName - An optional string value to set the Label about the input field
 */
(function(app){

  app.directive('swaZipcode', swaZipcode);

  function swaZipcode (){
      return {
        restrict: 'EA',
        require: 'ngModel',
        scope: {
          model: '=ngModel',
          countryCode: '=', // Reference binding of Country Code
          labelName: '@' // One way binding of Input Labels
        },
        replace: true,
        templateUrl: 'modules/swa-common-components/zipcode/swa.zipcode.directive.html',
        link: function(scope, element, attrs, ngModelCtrl){

          // Debug Console
          // console.log("zipCode linked", scope, scope.countryCode === 'US');

          // Initialize the zipCode object for the directive
          scope.zipCode = { zip1: "", zip2: "", postalCode: ""};
          // If the model has a value, split it into the internal data model
          if (scope.model) splitModelValue();

          // Watch value of the model so that if it is updated async or otherwise, the internal model is also updated
          scope.$watch('model', function () {

            // console.log("ZipCode Model:", newValue, oldValue, ngModelCtrl);
            if (scope.model) splitModelValue();

          });
          // scope.$watch(scope.zipCode, 
          scope.inputValidate = function () {

            // var scope.zipCode = scope.zipCode;
            // console.log('Value changed: ', scope.zipCode);

            // Run validator function -- we can also check for country code using scope.countryCode
            var countryCode = scope.countryCode;

            // US Zipcode validation block
            if (countryCode == "US"){
              // Check the first part of the zipcode - 5 digits
              var validZipCode = /^[0-9]{5}$/.test(scope.zipCode.zip1);
              
              // If there is a second part of the zipcode, validate that too
              if (scope.zipCode.zip2){
                // Check the second part of the zipcode - 4 digits
                validZipCode = validZipCode && /^[0-9]{4}$/.test(scope.zipCode.zip2);
              }

              // Set validity based on the output
              ngModelCtrl.$setValidity('invalidZipcode', validZipCode);

              // If the zipcode is valid, then update the data model
              if (validZipCode) {

                scope.model = scope.zipCode.zip1;
                
                if (scope.zipCode.zip2) {
                  scope.model += scope.zipCode.zip2;
                }

              } else {
              // Otherwise clear the model
                 scope.model = "";
              }

            } 
            // Canadian or International Postal Code validation
            else if (countryCode == "CA") {
              // Only accept the format 'A1A 1A1' for CA
              var validZipCode = /^[A-Za-z][0-9][A-Za-z]\ [0-9][A-Za-z][0-9]$/.test(scope.zipCode.postalCode);
              ngModelCtrl.$setValidity('invalidZipcode', validZipCode);
              // console.log("CA Validation", validZipCode, ngModelCtrl.$valid);
              
              // If the zipcode is valid, then update the data model
              if (validZipCode) {
                scope.model = scope.zipCode.postalCode;

              } else {
              // Otherwise clear the model
                 scope.model = "";
              }
            
            } else {
              // Accept only 9 numbers data for international zip codes
              var validZipCode = /^[0-9]{9}$/.test(scope.zipCode.postalCode);
              ngModelCtrl.$setValidity('invalidZipcode', validZipCode);
              // console.log("Int Validation", validZipCode, ngModelCtrl.$valid);
              
              // If the zipcode is valid, then update the data model
              if (validZipCode) {
                scope.model = scope.zipCode.postalCode;

              } else {
              // Otherwise clear the model
                 scope.model = "";
              }

            }

          }

          // Function to split the 
          function splitModelValue () {

            console.log("Model:",scope.model);
            // Split input into the 2 zipCodes
            scope.zipCode.zip1 = scope.model.substr(0,5);
            scope.zipCode.zip2 = scope.model.substr(5,10);

            // String assignment to postalCode
            scope.zipCode.postalCode = scope.model;

          }

        }
      };
  };

})(angular.module("swaCustomComponents"));