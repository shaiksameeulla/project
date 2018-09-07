/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/***
 * This directive will manage the validation and setting of a type ahead dropdown value.
 *
 * The directive accepts 4 params:
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
(function(app) {
    app.directive("swaMultiTypeAheadDropDown", swaMultiTypeAheadDropDown);
    swaMultiTypeAheadDropDown.$inject = ["$compile", "$timeout"];

    function swaMultiTypeAheadDropDown($compile, $timeout) {
        return {
            restrict: 'E',
            template: '<ui-select ng-model="datamodel" theme="select2" multiple style="width: 300px;" ng-change="changeUIMulti($select.selected)" append-to-body="true">' +
                '<ui-select-match ui-lock-choice = "{{lockchoice}}">{{$item.code}}</ui-select-match>' +
                '<ui-select-choices repeat="option in optionsdata | filter: $select.search">' +
                '{{option.code | filter: $select.search}}' +
                '</ui-select-choices>' +
                '</ui-select>{{datamodel}}',
            require: 'ngModel',
            scope: {
                datamodel: "=ngModel",
                optionsdata: "=",
                defaultindex: "@",
                blankoption: "=",
                lockchoice: "="
            },
            controller: function($scope) {
                $scope.changeUIMulti = function(selectedValue) {
                    $scope.datamodel = selectedValue;
                }
            },
            // @TODO: link functions should be named
            link: {
                post: function(scope, element, attrs, ngModelCtrl) {
                    var temp = {
                        code: "",
                        value: ""
                    };
                    console.log("Linking dropDown directive: ", scope, element, attrs, ngModelCtrl);
                    console.log("scope.defaultindex && scope.blankoption", scope.defaultindex, scope.blankoption, scope.model, scope.optionsdata, scope.lockchoice);
                    // if there is a default index given, and the balnk option flag is true, then set the model to the default index and add blank value in the option list
                    if (scope.defaultindex && scope.blankoption) {
                        console.log("default setting: ", scope.defaultindex, scope.optionsdata[scope.defaultindex]);
                        scope.datamodel.push(scope.optionsdata[scope.defaultindex]);
                        scope.optionsdata.unshift(temp);
                    }
                    // If there is a default index given, and the blankoption flag was not set, then set the model to the default index
                    else if (scope.defaultindex && !scope.blankoption) {
                        console.log("default setting: ", scope.defaultindex, scope.optionsdata[scope.defaultindex]);
                        scope.datamodel.push(scope.optionsdata[scope.defaultindex]);
                    }
                    // if the balnk option flag is true and there is no default value, add blank value in the option list
                    else if (!scope.defaultindex && scope.blankoption) {
                        scope.optionsdata.unshift(temp);
                        //scope.datamodel.push(scope.optionsdata[0]);
                    } else {
                        scope.datamodel = "";
                    }
                }
            }
        };
    };
})(angular.module("swaCustomComponents"));