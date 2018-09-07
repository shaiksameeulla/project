/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will manage the validation and setting of the phonenumber value.
 * The model holding the value of the phonenumber will only be updated if the inputtedValues are valid
 * The directive accepts 3 params:
 * @param ngModel - Reference to the data model that is holding the phonenumber value
 * @param countryCode - A string value representing the two letter country code of the 
 * @param labelName - An optional string value to set the Label about the input field
 */
(function(app){

  app.directive('swaPhoneNumber', swaPhoneNumber);

  var DEBUG = false;
  var ERROR_MESSAGE = { US: "US/CA Phone number should be in the format of +1 123 123 1234", INT: "International Phone numbers should be a maximum of 11 digits only." };
  var COUNTRYCODE_US = "1";
  var PHONENUMBER_REGEX = {
    US: {
      AREACODE: /^[0-9]{3}$/,
      PHONE_PART_ONE: /^[0-9]{3}$/,
      PHONE_PART_TWO: /^[0-9]{4}$/
    },
    INT: {
      NUMBER: /^[0-9]{14}$/
    }
  };

  function swaPhoneNumber (){
      return {
        restrict: 'EA',
        require: 'ngModel',
        scope: {
          parentModel: '=ngModel',
          countryCode: '=', // Reference binding of Country Code
          labelName: '@' // One way binding of Input Labels
        },
        replace: true,
        templateUrl: 'modules/swa-common-components/phone-number/swa.phone-number.directive.html',
        link: function(scope, element, attrs, ngModelCtrl){

          // Debug Console
          if (DEBUG) console.log("phoneNumber linked", scope, scope.countryCode === 'US');

          scope.VALIDATIONS = PHONENUMBER_REGEX;
          scope.ERROR_MESSAGE = ERROR_MESSAGE;

          // Initialize the zipCode object for the directive
          scope.phoneNumber = { countryCode: COUNTRYCODE_US, areaCode: "", phonePartOne: "", phonePartTwo: "", internationalPhoneNumber: "" };

          // @TODO: If ngModel already has a value, parse it into the internal data model, and set the validation flags

          scope.inputValidate = function () {

            if (DEBUG) console.log('Value changed: ', scope.phoneNumber);

            // Run validator function -- we can also check for country code using scope.countryCode
            var countryCode = scope.countryCode;

            // US & CA Zipcode validation block
            if (/US|CA/.test(scope.countryCode)){
              var validPhoneNumber = true;

              // Check the first part of the phone number is 3 digits
              validPhoneNumber = validPhoneNumber && PHONENUMBER_REGEX.US.AREACODE.test(scope.phoneNumber.areaCode);
              // console.log("AreaCode Validation", scope.phoneNumber.areaCode, PHONENUMBER_REGEX.US.AREACODE.test(scope.phoneNumber.areaCode));
              
              // Check the second part of the phone number is 3 digits
              validPhoneNumber = validPhoneNumber && PHONENUMBER_REGEX.US.PHONE_PART_ONE.test(scope.phoneNumber.phonePartOne);
              
              // Check the third part of the phone number is 4 digits
              validPhoneNumber = validPhoneNumber && PHONENUMBER_REGEX.US.PHONE_PART_TWO.test(scope.phoneNumber.phonePartTwo);

              // Set validity based on the output
              ngModelCtrl.$setValidity('invalidPhoneNumber', validPhoneNumber);

              // Debug output
              if (DEBUG) console.log("US Validation", validPhoneNumber, ngModelCtrl.$valid, PHONENUMBER_REGEX);

              // If the zipcode is valid, then update the data model
              if (validPhoneNumber) {
                scope.parentModel = scope.phoneNumber.countryCode + scope.phoneNumber.areaCode + scope.phoneNumber.phonePartOne + scope.phoneNumber.phonePartTwo;
              } else {
              // Otherwise, model is cleared
                scope.parentModel = ""
              }
 
            } else {
              // Logic block for international numbers that are not CA

              var validPhoneNumber = PHONENUMBER_REGEX.INT.NUMBER.test(scope.phoneNumber.internationalPhoneNumber);
              ngModelCtrl.$setValidity('invalidPhoneNumber', validPhoneNumber);

              // Debug output
              if (DEBUG) console.log("Int Validation", validPhoneNumber, ngModelCtrl.$valid);
              
              // If the zipcode is valid, then update the data model
              if (validPhoneNumber) {
                scope.parentModel = scope.phoneNumber.internationalPhoneNumber;
              } else {
              // Otherwise, model is cleared
                scope.parentModel = ""
              }

            }

          }

        }
      };
  };

})(angular.module("swaCustomComponents"));