/**
 * Copyright (c) 2016 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/**
 * This controller acts as a child controller of AWB Management functionality, managing AWB sections
 * Non standard Angular Paramters that are passed to this Controller are listed below with their purpose
 *
 *  @constructor
 *  @param ShipperController - Name of the controller
 *  @param constructor function;
 *  @param $filter - Filters are used for formatting data displayed to the user.
 *  @param manageAWBService - Manage AWB Service on the AngularJS that interacts with the AWB Service.
 *  @param ServiceURL - Service URL constants.
 *  @param ERRORMESSAGES - Success & Error messages added in the constant.
 *  @param CONFIGURATIONS - config.js configurations.
 */
 (function(){
    "use strict"; 
     angular.module("CargoApp").controller("ShipperController",ShipperController); //Declare controller
     ShipperController.$inject=[ //Inject dependency in alphabet order
         '$filter',
         '$location',
         '$rootScope',
         '$scope',
         '$window'
     ];
    
    /**@ngInject**/
    function ShipperController($filter,$location,$rootScope,$scope,$window){
        /**Named functions declaration**/
        $scope.validateMasterAccountId = validateMasterAcc; 
        
        function validateMasterAcc(isValid){
            console.log("data"+isValid);
        }
    };
     
 })();