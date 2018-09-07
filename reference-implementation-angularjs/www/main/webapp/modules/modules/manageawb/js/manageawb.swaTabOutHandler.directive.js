/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/

/***
 * This directive will trigger a callback on a tab out event
 */
(function(app){

  app.directive('swaTabOutHandler', swaTabOutHandler);

	function swaTabOutHandler (){
    	return {
	        restrict: 'A',
	        scope: {
	        	swaTabOutHandler: '&'
	        },
	        link: function(scope, element, attrs, ngModelCtrl){

	        	// console.log("swaTabOutHandler linked:", scope.swaTabOutHandler);

	        	// If no handler was passed dont do anything
	        	if (!scope.swaTabOutHandler){
	        		// console.warn("No handler passed...", scope.swaTabOutHandler);
	        		return;
	        	}

	        	element.bind('keydown', function (e) {
	        		console.warn("Key Down inside element!", e);

	        		// If tab out occurs, then trigger callback
	        		if (e.keyCode === 9 || e.which === 9) {
	        		console.warn("Tab Detected!", e);
	        			scope.swaTabOutHandler();
	        		}
	        	});

	        }
	    };

	};

})(angular.module("manageawb.directives"));