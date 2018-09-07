var routingScope = routingScope;
var shipmentDetailsScope = shipmentDetailsScope;
var manageAwbCntrl = manageAwbCntrl;
var lotLablesScope = lotLablesScope;
describe('ManageAWBController', function() {
    /* Load up all the dependency modules here for the Controller being tested
     New Dependencies from 3/7 Build:
      '$rootScope', 'ErrorHandlingService', 'airwayBillDataService', 'ngTableParams', '$modal', '$localStorage',
      'manageAWBService', 'focus', '$timeout','routingDirectiveConstants','ERRORMESSAGES'

      Modules for CargoApp from app.js 3/7 Build:
      'ngRoute', 'CargoApp.services', 'angularFileUpload', 'CargoApp.controllers', 'CargoApp.directives',
      'ui.bootstrap', 'ngTable', 'infinite-scroll', 'ngStorage', 'ngSanitize', 'ui.select', 'pasvaz.bindonce'

      Config from app.js is called by some services...*/
    var ServiceURL = {
        "baseUrl": "/CargoSystem/services"
    };
    // Inject the dependent modules
    beforeEach(module('CargoApp')); // To load up all the app.constants and app.values
    beforeEach(module('CargoApp.controllers'));
    beforeEach(module('CargoApp.services'));
    beforeEach(module('ngStorage'));
    var $controller;
    var $compile;
    var airwayBillDataService;
    var $rootScope;
    // Inject the controller provider
    beforeEach(inject(function(_$controller_, _$compile_, _$rootScope_, _$localStorage_, _airwayBillDataService_) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $controller = _$controller_;
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $localStorage = _$localStorage_;
        airwayBillDataService = _airwayBillDataService_;
    }));
    beforeEach(function() {
        // Mock the userObject in localStorage so that the Controller can start up
        $localStorage.userObject = {
            authorizationView: {
                roleList: ["TEST_AUTH"]
            }
        };
        $localStorage.awbContent = '';
        window.localStorage.awbContent = '""';
        var store = {
            awbContent: ''
        };
        spyOn(window.localStorage, 'getItem').and.callFake(function(key) {
            return store[key];
        });
        for (prop in $localStorage) {}
    });
    beforeEach(inject(function(_$compile_, $rootScope, _$timeout_) {
        var $compile = _$compile_;
        var $scope = $rootScope.$new();
        var $timeout = _$timeout_;
        var  template  = $compile("<div awb-shipment-details></div>")($scope);
    }));
    describe('Error Flags', function() {
        // Used undefined instead of empty object so that it skips the referenceDataDropdowns in Line#728 (Build 2/20)
        it('Calls $scope.removeFlagMsg to reset the concurrentMsg', function() {
            // Create variable to store reference to $scope
            var $scope = {};
            var referenceDataDropDown = undefined;
            var nonComatService = undefined;
            var airportDetailsRef = undefined;
            var searchShipmentRefData = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            $scope.showBar = true;
            $scope.concurrentMsg = 'THERE IS A MESSAGE';
            $scope.removeFlagMsg();
            // Assert showbar is false
            expect($scope.showBar).toEqual(false);
            //Assert  concurrentMsg ''
            expect($scope.concurrentMsg).toEqual("");
        });
        it('Calls $scope.showConcurrent to set the concurrentMsg message', function() {
            // Create variable to store reference to $scope
            var $scope = {};
            var referenceDataDropDown = undefined;
            var nonComatService = undefined;
            var airportDetailsRef = undefined;
            var searchShipmentRefData = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var TEST_MSG = "MSG";
            $scope.concurrentMsg = 'THERE IS A MESSAGE';
            $scope.showConcurrent(TEST_MSG);
            // Assert concurrentMsg 
            expect($scope.concurrentMsg).toEqual(TEST_MSG);
        });
        it('Calls $scope.showConcurrent to check if null is handled correctly', function() {
            // Create variable to store reference to $scope
            var $scope = {};
            var referenceDataDropDown = undefined;
            var nonComatService = undefined;
            var airportDetailsRef = undefined;
            var searchShipmentRefData = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var TEST_MSG = null;
            $scope.concurrentMsg = 'THERE IS A MESSAGE';
            $scope.showConcurrent(TEST_MSG);
            // Actual Test Functions
            expect($scope.concurrentMsg).toEqual('THERE IS A MESSAGE');
        });
    });
    // End of "Error Flags"
    describe('AWB Privileges', function() {
        // Create variable to store reference to $scope
        var ngTableParams = {};
        var ConsigneeService = {
            getCntrVal: function() {
                return 1;
            },
            getCntrValSd: function() {
                return 1;
            }
        };
        var airportDetailsRef = undefined; //{};
        var searchShipmentRefData = {};
        var nonComatService = undefined; //{};
        var referenceDataDropDown = undefined;
        // Used undefined instead of empty object so that it skips the referenceDataDropdowns in Line#728 (Build 2/20)
        var AUTH_DATA = "SOME_AUTH_DATA",
            MORE_AUTH_DATA = "MORE_AUTH_DATA";
        //Test for function 122 GET UPDATED PRIVILEDGE
        it('Calls $scope.getUpatedAWBPrivileges to get updated privilege', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var AWBSTATUS = "STATUS OK";
            $scope.getUpatedAWBPrivileges(AWBSTATUS);
            // Actual Test Functions
            for (i = 0; i < $scope.sectionPrivilege.length; i++) {
                expect($scope.sectionPrivilege[i]).toEqual("AWB_" + AWBSTATUS + "_SECTION" + (i + 1) + "_UPDATE");
            }
            // for (i = 0; i < $scope.sectionAWBPrivilege.length; i++){
            expect($scope.sectionAWBPrivilege.indexOf(AUTH_DATA)).toEqual(-1);
            expect($scope.sectionAWBPrivilege.indexOf(MORE_AUTH_DATA)).toEqual(-1);
            // }
        });
        it('isSectionHaveRWPermission Exit Condition 1', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = true;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.currentPrivilegeCode = "DISC";
            // Assert for false for section wise priviledge
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toBe(true);
        });
        it('isSectionHaveRWPermission Exit Condition 2', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = true;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.currentPrivilegeCode = "DISC";
            // Assert for Exit Condition 2
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toBe(true);
        });
        it('isSectionHaveRWPermission Exit Condition 3', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = undefined;
            $scope.currentPrivilegeCode = "DISC";
            // Assert for Exit Condition 3
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(undefined);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(jasmine.any(Object));
        });
        it('isSectionHaveRWPermission Exit Condition 4', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = true;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            // Assert for Exit Condition 4
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(undefined);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(jasmine.any(Object));
        });
        it('isSectionHaveRWPermission Exit Condition 5', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA", isEligible];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "TRUE";
            // Assert for Exit Condition 5
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(true);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toBe(false); //toEqual(jasmine.any(Object));
        });
        it('isSectionHaveRWPermission Exit Condition 6', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA", isEligible];
            sectionIdName = "";
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "TRUE";
            // Assert for Exit Condition 6
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(undefined);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(jasmine.any(Object));
        });
        it('isSectionHaveRWPermission Exit Condition 7', function() {
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "TRUE";
            // Assert for Exit Condition 7
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(true);
        });
        it('isSectionHaveRWPermission Exit Condition 8', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "FALSE";
            // Assert for Exit Condition 8
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(true);
        });
        it('isSectionHaveRWPermission Exit Condition 9', function() {
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "UNDEF";
            // Assert for Exit Condition 9 
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(undefined);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(jasmine.any(Object));
        });
        it('isSectionHaveRWPermission Exit Condition 10', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA", isEligible];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "FALSE";
            $scope.headerData = {
                origin: null
            };
            $scope.moveNextSection = true;
            // Assert for Exit Condition 10
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(true);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(false);
        });
        it('isSectionHaveRWPermission Exit Condition 11', function() {
            //define scope
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA", isEligible];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "FALSE";
            $scope.headerData = {
                origin: null
            };
            $scope.moveNextSection = false;
            sectionIdName = "fromShipperForm";
            // Assert for Exit Condition 11
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(true);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(false);
        });
        it('isSectionHaveRWPermission Exit Condition 12', function() {
            var $scope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "FALSE";
            // $scope.headerData = { origin: null };
            $scope.moveNextSection = false;
            sectionIdName = "fromShipperForm";
            // Assert for Exit Condition 12
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(true);
        });
        it('isSectionHaveRWPermission Exit Condition 13', function() {
            //define scope
            var $scope = {};
            // Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                ngTableParams: ngTableParams,
                ConsigneeService: ConsigneeService,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // Setup the $scope variables
            var i = 6,
                awbStatus = "DISC";
            var isEligible = "AWB_" + awbStatus + "_SECTION" + i + "_UPDATE",
                sectionIdName = 'signatureCont';
            $scope.isEmerLocked = false;
            $scope.releaseFormComplete = false;
            $scope.voidStatus = false;
            $scope.sectionAWBPrivilege = ["SOME_AUTH_DATA"];
            $scope.currentPrivilegeCode = "NOT_DISC";
            $scope.isAWBAutoSave = "FALSE";
            // Assert for Exit Condition 13
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).toBe(false);
            expect($scope.isSectionHaveRWPermission(isEligible, sectionIdName)).not.toEqual(true);
        });
    });
    describe('function appendHTMLAutoSave', function() {
        // Create variable to store reference to $scope
        var ngTableParams = {};
        var ConsigneeService = {
            getCntrVal: function() {
                return 1;
            },
            getCntrValSd: function() {
                return 1;
            }
        };
        var airportDetailsRef = undefined; //{};
        var searchShipmentRefData = {};
        var nonComatService = undefined; //{};
        var referenceDataDropDown = undefined;
        var AUTH_DATA = "SOME_AUTH_DATA",
            MORE_AUTH_DATA = "MORE_AUTH_DATA";
        var focus = jasmine.createSpy();
        it('Function is called when user clicks on Lotlabels section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                $compile: $compile,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            $scope.shipmentdetails = false;
            $scope.isAWBAutoSave = "FALSE";
            $scope.trigger = false;
            $scope.lotlables = false;
            //set up scope variables
            $scope.isAWBAutoSave = "FALSE";
            $scope.trigger = "TRUE";
            $scope.headerData.awbStatus = "";
            var linkingFn = ($compile("<div awb-lotlabels></div>"))($scope);
            //Assertion Function triggerManually('7') is called when user clicks on Lotlabels section
            expect($scope.appendHTMLAutoSave("lotlables", "3")).toEqual($scope.triggerManually('7'));
            //Assert lotlables flipped to true if it is originally false
            expect($scope.lotlables).toEqual(true);
        })
        it('Function is called when user clicks on Documents section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            $scope.shipmentdetails = false;
            $scope.isAWBAutoSave = "FALSE";
            $scope.trigger = false;
            $scope.documents = false;
            //set up scope variables
            $scope.isAWBAutoSave = "FALSE";
            $scope.trigger = "TRUE";
            $scope.headerData.awbStatus = "";
            var linkingFn = ($compile("<div awb-documents></div>"))($scope);
            //Assertion Function triggerManually('9') is called when user clicks on Documents section
            expect($scope.appendHTMLAutoSave("documents", "3")).toEqual($scope.triggerManually('9'));
            expect($scope.documents).toEqual(true);
        })
        it('Function is called when user clicks on Comments section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // $scope.trigger = false;
            $scope.comments = false;
            //set up scope variables
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            var linkingFn = ($compile("<div awb-comments></div>"))($scope);
            //Assertion Function triggerManually('8') is called when user clicks on Comments section
            expect($scope.appendHTMLAutoSave("comments", "3")).toEqual($scope.triggerManually('8'));
            expect($scope.comments).toEqual(true);
        })
        it('Function is called when user clicks on Signatures section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // $scope.trigger = false;
            $scope.signatures = false;
            //set up scope variables
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            var linkingFn = ($compile("<div awb-sign></div>"))($scope);
            //Assertion Function triggerManually('6') is called when user clicks on Signatures section
            expect($scope.appendHTMLAutoSave("signatures", "3")).toEqual($scope.triggerManually('6'));
            expect($scope.signatures).toEqual(true);
        })
        it('Function is called when user clicks on Routing section', inject(function($timeout) {
            //define scope and time out
            var $scope = $rootScope.$new();
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // $scope.trigger = false;
            $scope.routing = false;
            $scope.init = function() {}
            $scope.initializeGetLots = function() {}
            $scope.closeAllExpandedLot = function() {}
            routingScope = $scope;
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            $scope.appendHTMLAutoSave("routing", "3");
            //Assertion Function triggerManually('4') is called when user clicks on Routing section
            expect($scope.appendHTMLAutoSave("routing", "3")).toEqual($scope.triggerManually('4'));
        }))
        it('Function is called when user clicks on Cargo Screening section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // $scope.trigger = false;
            $scope.cargoScreen = false;
            //set up scope variables
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            var template = ($compile("<div cargo-screening></div>"))($scope);
            //Assertion Function triggerManually('3') is called when user clicks on Cargo Screening section
            expect($scope.appendHTMLAutoSave("cargoScreen", "3")).toEqual($scope.triggerManually('3'));
            expect($scope.cargoScreen).toEqual(true);
        })
        it('Function is called when user clicks on toconsignee section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            // $scope.trigger = false;
            $scope.toConsignee = false;
            //set up scope variables
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            var template = ($compile("<div awb-to-consignee></div>"))($scope);
            //Assertion Function triggerManually('1') is called when user clicks on Cargo Screening section
            expect($scope.appendHTMLAutoSave("toconsignee", "3")).toEqual($scope.triggerManually('1'));
            expect($scope.toConsignee).toEqual(true);
        })
        it('Function is called when user clicks on shipmentdetails section', function() {
            //define scope and time out
            var $scope = $rootScope.$new();
            var $timeout = {};
            // Controller dependencies
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $timeout: $timeout,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown
            });
            $scope.shipmentdetails = false;
            //set up scope variables
            $scope.headerData.awbStatus = "";
            $scope.isSectionCollapsed = true;
            var template = ($compile("<div awb-shipment-details></div>"))($scope);
            //Assertion Function triggerManually('6') is called when user clicks on Cargo Screening section
            expect($scope.appendHTMLAutoSave("shipmentdetails", "3")).toEqual($scope.triggerManually('2'));
            expect($scope.shipmentdetails).toEqual(true);
        })
    });
    // manageawbdefault_controller.js: line 1273, col 54, This function's cyclomatic complexity is too high. (29)
    describe('commonObjectRoutingandShipment', function() {
        //define scope and time out
        var ngTableParams = {};
        var ConsigneeService = {
            getCntrVal: function() {
                return 1;
            },
            getCntrValSd: function() {
                return 1;
            }
        };
        var airportDetailsRef = undefined; //{};
        var searchShipmentRefData = {};
        var nonComatService = undefined; //{};
        var referenceDataDropDown = undefined;
        // Used undefined instead of empty object so that it skips the referenceDataDropdowns in Line#728 (Build 2/20)
        var AUTH_DATA = "SOME_AUTH_DATA",
            MORE_AUTH_DATA = "MORE_AUTH_DATA";
        it('$scope.shouldAllowSaveShipment false', function() {
            var $scope = {};
            var $rootScope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $rootScope: $rootScope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown,
            });
            $scope.shouldAllowSaveShipment = false;
            $rootScope.routingData = {
                lots: [({
                    actualWeight: "45",
                    assignedRunner: "435",
                    commodityCode: "ABC",
                    currentLotStatus: "SUCCESS",
                    deliveredToCustomer: "YES",
                    flightLegs: [],
                    lines: [],
                    lotId: "TX1",
                    lotNumber: "TX2",
                    lotPieces: "LOT5",
                    lotStatusStation: "12",
                    noOfPieces: "50",
                    previousLotStatus: "Delivered",
                    reroutingReason: "Detour",
                    routingValid: false,
                    samePlaneInd: false,
                    totalPieces: "52",
                    totalWeightKgs: "78",
                    totalWeightLbs: "150",
                    volume: "3CUBIC",
                    wareHouseLocation: "ATL"
                })]
            };
            routingScope = {
                getLots: {
                    routingList: ["List"]
                }
            };
            var expected = angular.copy($rootScope.routingData);
            var results = $scope.commonObjectRoutingandShipment();
            expect(results.lots[0].actualWeight).toEqual(expected.lots[0].actualWeight);
            expect(results.lots[0].assignedRunner).toEqual(expected.lots[0].assignedRunner);
            expect(results.lots[0].commodityCode).toEqual(expected.lots[0].commodityCode);
            expect(results.lots[0].currentLotStatus).toEqual(expected.lots[0].currentLotStatus);
            expect(results.lots[0].deliveredToCustomer).toEqual(expected.lots[0].deliveredToCustomer);
            expect(results.lots[0].flightLegs).toEqual(expected.lots[0].flightLegs);
            expect(results.lots[0].lines).toEqual(expected.lots[0].lines);
            expect(results.lots[0].lotId).toEqual(expected.lots[0].lotId);
            expect(results.lots[0].lotPieces).toEqual(expected.lots[0].lotPieces);
            expect(results.lots[0].lotStatusStation).toEqual(expected.lots[0].lotStatusStation);
            expect(results.lots[0].noOfPieces).toEqual(expected.lots[0].noOfPieces);
            expect(results.lots[0].previousLotStatus).toEqual(expected.lots[0].previousLotStatus);
            expect(results.lots[0].reroutingReason).toEqual(expected.lots[0].reroutingReason);
            expect(results.lots[0].samePlaneInd).toEqual(expected.lots[0].samePlaneInd);
            expect(results.lots[0].totalPieces).toEqual(expected.lots[0].totalPieces);
            expect(results.lots[0].totalWeightKgs).toEqual(expected.lots[0].totalWeightKgs);
            expect(results.lots[0].totalWeightLbs).toEqual(expected.lots[0].totalWeightLbs);
            expect(results.lots[0].volume).toEqual(expected.lots[0].volume);
            expect(results.lots[0].wareHouseLocation).toEqual(expected.lots[0].wareHouseLocation);
        });
        it('Positive case :Check whether Section5 has routed lot, if Section5 has routed lot then it will remain unchanged with red error bar', function() {
            //define scope and rootScope
            var $scope = {};
            var $rootScope = {};
            //spied on method checkIfLotsRouted in airwayBillDataService
            spyOn(airwayBillDataService, "checkIfLotsRouted").and.returnValue(false);
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $rootScope: $rootScope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown,
            });
            shipmentDetailsScope = "TEST";
            $scope.commonObjectRoutingandShipment();
            expect(shipmentDetailsScope.generatedLots).toEqual(undefined);
            expect(routingScope.getLots).toEqual(undefined);
        });
        it('Negative case :Check whether Section5 has routed lot, if Section5 has routed lot then it will remain unchanged with red error bar', function() {
            //define scope and rootScope
            var $scope = {};
            var $rootScope = {};
            spyOn(airwayBillDataService, "checkIfLotsRouted").and.returnValue(true);
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $rootScope: $rootScope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown,
            });
            shipmentDetailsScope = {
                generatedLots: {
                    routedLot: ""
                }
            };
            $scope.commonObjectRoutingandShipment();
            expect(routingScope.getLots).toEqual(undefined);
        });
        it('Positive Test case :Shipment Detail section has valid data', function() {
            //define scope and rootScope
            var $scope = {};
            var $rootScope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $rootScope: $rootScope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown,
            });
            manageAwbCntrl = $scope;
            $scope.displayDivErrorBarSd = false;
            $rootScope.routingData = {
                lots: [({
                    actualWeight: "45",
                    assignedRunner: "435",
                    commodityCode: "ABC",
                    currentLotStatus: "SUCCESS",
                    deliveredToCustomer: "YES",
                    flightLegs: [],
                    lines: [],
                    lotId: "TX1",
                    lotNumber: "TX2",
                    lotPieces: "LOT5",
                    lotStatusStation: "12",
                    noOfPieces: "50",
                    previousLotStatus: "Delivered",
                    reroutingReason: "Detour",
                    routingValid: false,
                    samePlaneInd: false,
                    totalPieces: "52",
                    totalWeightKgs: "78",
                    totalWeightLbs: "150",
                    volume: "3CUBIC",
                    wareHouseLocation: "ATL"
                })]
            };
            shipmentDetailsScope = {
                generatedLots: {
                    routedLot: ""
                },
                generateRoutingData: function() {
                    return "generatedLots"
                }
            };
            routingScope = {
                getLots: {
                    routingList: ["List"]
                },
                updateAllArrays: function() {
                    return "Test Data"
                }
            };
            expect($scope.commonObjectRoutingandShipment()).not.toEqual(angular.copy($rootScope.routingData));
        });
        it('Negative Test case :Shipment Detail section has valid data', function() {
            //define scope and rootScope
            var $scope = {};
            var $rootScope = {};
            // Replace the Controller dependencies with stub equivalents
            var controller = $controller('ManageAWBController', {
                $scope: $scope,
                $rootScope: $rootScope,
                airportDetailsRef: airportDetailsRef,
                searchShipmentRefData: searchShipmentRefData,
                nonComatService: nonComatService,
                referenceDataDropDown: referenceDataDropDown,
            });
            manageAwbCntrl = $scope;
            $scope.displayDivErrorBarSd = false;
            $rootScope.routingData = undefined;
            routingScope = {
                getLots: {
                    routingList: ["List"]
                }
            };
            expect($scope.commonObjectRoutingandShipment()).not.toEqual(angular.copy($rootScope.routingData));
        });
    });
});