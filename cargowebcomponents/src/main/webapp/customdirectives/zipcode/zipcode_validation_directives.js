// HTML Example:
// <input sc-zipcode ng-model="dataModel.zipcode" name="zipcode" label-name="{{labelName}}" country-code="{{countryCode}}"></input>
// Output of the directive will be: dataModel.zipcode = 
// { zip1: 'US_ZIPCODE_PART_1', zip2: 'US_ZIPCODE_PART_2', postalCode: 'CA_OR_INT_POSTALCODE' }

angular.module('swacoredirectives',[])
.directive('scZipcode', function(){
        return {
          restrict: 'A',
          require: 'ngModel',
          scope: {
            ngModel: '=',
            countryCode: '=',
            labelName: '='
          },    
          // template: 
          //   '<div>'+
          //   '<label style="font-weight: bold">{{ labelName || "Zip Code/Postal Code"}}</label><br>'+
          //   '<!-- Input fields for Country: US -->'+
          //   '<span ng-if="countryCode === \'US\'">'+
          //     '<input ng-model="ngModel.zip1" ng-change="reValidate()" placeholder="Zip Code"></input>'+
          //     '<input ng-model="ngModel.zip2" ng-change="reValidate()" placeholder="####"></input>'+
          //   '</span>'+
          //   '<!-- Input fields for Country: CA -->'+
          //   '<span ng-if="countryCode !== \'US\'">'+
          //     '<input ng-model="ngModel.postalCode" ng-change="reValidate()" placeholder="Postal Code">'+
          //   '</span>'+
          //   '</div>',
          templateUrl: 'zipcode_template.html',
          link: function(scope, element, attrs, ngModelCtrl){
            console.log(attrs,scope);

            scope.reValidate = function () {

              // Run validator function
              var countryCode = scope.countryCode;
              console.log("Country: ", scope.countryCode);

              // US Zipcode
              if (countryCode == "US"){
                // Check the first part of the zipcode - 5 digits
                var validZipCode = /^[0-9]{5}$/.test(scope.ngModel.zip1);
                console.log("US Validity P1: ", validZipCode, scope.ngModel.zip1);
                
                if (scope.ngModel.zip2){
                  // Check the second part of the zipcode - 4 digits
                  validZipCode = validZipCode && /^[0-9]{4}$/.test(scope.ngModel.zip2);
                  console.log("US Validity P2: ", validZipCode, scope.ngModel.zip2);
                }

                // Set validity based on the output
                ngModelCtrl.$setValidity('invalidZipcode', validZipCode);
                console.log("US Validity Final: ", validZipCode, scope.ngModel);

              } 
              // Canadian or International Postal Code validation
              else {
                // Only accept the format 'A1A 1A1' for Canadian
                
                // Validation not run because there is no universal validation pattern that can be applied:
                var validZipCode = true;
                // var validZipCode = /^[A-Za-z][0-9][A-Za-z]\ [0-9][A-Za-z][0-9]$/.test(scope.ngModel.postalCode);
                ngModelCtrl.$setValidity('invalidZipcode', validZipCode);
                console.log("International Validity: ", validZipCode, ngModelCtrl.$valid, scope.ngModel);
              
              }

            };
          }
        };
      });