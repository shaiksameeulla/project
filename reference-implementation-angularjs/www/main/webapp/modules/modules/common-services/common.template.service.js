/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This service will manage all the actions related to making remote calls to the api endpoint.
 *
 * @param $window - Angular window wrapper
 * @param $log - Angular logging service
 * @param $rootScope - Angular rootscope provider

 */
(function(app) {
    "use strict";

    app.factory('CommonService', CommonService);

    CommonService.$inject = ['$window', '$log', '$rootScope'];

    function CommonService($window, $log, $rootScope) {
        $rootScope.showLoading = false;

        var $showLoadingScreen = {
            show: function() {
                $rootScope.showLoading = true;
                console.warn("Show Loading Screen: " + $rootScope.showLoading);
            },
            hide: function() {
                console.warn("Hide Loading Screen");
                 $rootScope.showLoading = false;
            }
        }

        return {
            preAction: function(data) {
                if (data.$invalid) {
                    // Do not proceed further
                    $log.info("Model was invalid: ", data.$invalid);
                    return false;
                } else {
                    blockUserInterface();
                    return true;
                }
            },

            postAction: function(focusOnNextElement) {
                if (focusOnNextElement) {
                    focusElement(focusOnNextElement);
                }

                releaseUserInterface();

            }
        }

        // Function is wrapper for invoking a service for blocking UI and showing the loading screen
        function blockUserInterface() {
            // Using stubs for POC
            $window.GLOBALS.BLOCK_INPUT = true;
            $showLoadingScreen.show();
        }

        // focusElement service stub
        function focusElement(elementId) {
            $log.info("Setting focus to ", elementId);
            // @TODO: Find out what the angular way of setting focus
            document.querySelector(elementId).focus();
        }

        function releaseUserInterface() {
            // Using stubs for POC
            $window.GLOBALS.BLOCK_INPUT = false;
            $showLoadingScreen.hide();
        }

    }

})(angular.module("CargoApp"));