// Get references to global variables declared in the variable script files
var manageAwbCntrl = manageAwbCntrl;
var shipmentDetailsScope = {}; // shipmentDetailsScope reference is string so it didn't work
describe('Shipmentdetails directive', function() {
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
    var $controller, $localStorage, airwayBillDataService, shipmentDetailsConstants, routingDirectiveConstants, ShipmentDetailsErrorMsgs, $rootScope;
    // Inject the controller provider
    beforeEach(inject(function(_$controller_, _$localStorage_, _shipmentDetailsConstants_, _routingDirectiveConstants_, _ShipmentDetailsErrorMsgs_, _airwayBillDataService_, _$rootScope_) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $controller = _$controller_;
        $localStorage = _$localStorage_;
        shipmentDetailsConstants = _shipmentDetailsConstants_;
        routingDirectiveConstants = _routingDirectiveConstants_;
        ShipmentDetailsErrorMsgs = _ShipmentDetailsErrorMsgs_;
        airwayBillDataService = _airwayBillDataService_;
        // Retain rootScope between tests to chain the tests
        $rootScope = _$rootScope_;
        // Initialize the manageAwb controller
        window.manageAwbCntrl = {};
    }));
    beforeEach(function() {
        // Mock the required object in localStorage so that the Controller can start up
        $localStorage.awbContent = '';
        $localStorage.serviceLevelComat = {
            F: "C",
            N: "C-MR",
            R: "AOG"
        };
        $localStorage.serviceLevelNonComat = {
            F: "FREIGHT",
            N: "RUSH",
            R: "NFG"
        };
    });
    /* This test suite is used to test shipment details section initialization for
       existing Air way bill number and new AWB number. Test cases are written against init().
       Test cases are written against comat customer, non comat customer, actual dimensional and actual weight unit
       is selected*/
    describe('Test for section initialization with shipmentInfo', function() {
        it('Section initialization for existing AWB', function() {
            // Create variable to store reference to $scope
            var $scope = {};
            var $rootScope = {};
            window.manageAwbCntrl = {};
            window.manageAwbCntrl.headerData = {
                awbNumber: 12345,
                origin: 'ATL',
                destination: 'DAL'
            };
        
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants
            });
            // Setup controller state
            $scope.shipmentInfo = {
                destination: 'DAL',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: {
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 10,
                    lineLength: 20,
                    lineWidth: 5,
                    lineHeight: 7,
                    volume: 35,
                    dimWeight: 23,
                    commodityCode: "FLR",
                    contentDesc: "Beautiful",
                    additionalDesc: "None",
                    slac: 2,
                    specialHandlingCode: "Care",
                    dryIceUnit: "1",
                    isHeavy: "No",
                    dryIceWeight: 2
                }
            };
            $rootScope.comatServiceVisible = true;
            $rootScope.nonComatServiceVisible = true;
            // Calling init function to trigger state changes
            $scope.init();
            //Test for comat customer is selected in from shipper section
            expect($scope.serviceLevelOptionsComat).toEqual($localStorage.serviceLevelComat);
            //Test for non-comat customer is selected in from shipper section
            expect($scope.serviceLevelOptionsNonComat).toEqual($localStorage.serviceLevelNonComat);
            //Test for Shipper changed is skipped ignored since it involves DOM mock window.$('dom-reference')
            //Test for actual weight unit
            expect($scope.shipmentInfo.actualWeightUnit).toEqual(shipmentDetailsConstants.kgWeightUnit);
            //Test for actual dimentional unit
            expect($scope.shipmentInfo.dimUnit).toEqual(shipmentDetailsConstants.cmDimUnit);
        });
        it(' Test for section initialization with out shipmentInfo', function() {
            // Create variable to store reference to $scope
            var $scope = {};
            var $rootScope = {};
            window.manageAwbCntrl = {};
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants
            });
           
            $rootScope.comatServiceVisible = false;
            $rootScope.nonComatServiceVisible = false;
            //calling init method to trigger state changes
            $scope.init();
            //Test for comat customer is selected in from shipper section
            expect($scope.serviceLevelOptionsComat).not.toEqual($localStorage.serviceLevelComat);
            //Test for non-comat customer is selected in from shipper section
            expect($scope.serviceLevelOptionsNonComat).not.toEqual($localStorage.serviceLevelNonComat);
            //Test for Shipper changed is skipped ignored since it involves DOM mock window.$('dom-reference')
            //Test for actual weight unit
            expect($scope.shipmentInfo.actualWeightUnit).not.toEqual(shipmentDetailsConstants.kgWeightUnit);
            //Test for actual dimentional unit
            expect($scope.shipmentInfo.dimUnit).not.toEqual(shipmentDetailsConstants.cmDimUnit);
        });
    });
    /* This test suite is used to test that generates final object is saved. Test cases are written 
       against baseControl(),commonErrorHandling() and convertBetweenKgAndLbs().*/
    describe('Test for function that is used to generate final object for the request of save shipment', function() {
        // Define the Service stubs
        var ErrorHandlingService;
        var ngTableParams;
        var focus = jasmine.createSpy();
        var manageAWBService = {};
        it('Test for function is use to generate final object for the request of save shipment', function() {
            var $scope = {};
            var $rootScope = {};
            var fromShipperCntrl = {
                Shipper: {
                    primarySecurityType: "AUTH"
                }
            };
            var baseControlValue = {
                actualValue: 4,
                totalWeightLbsOrKg: 25,
                actualWeight: 4
            };
            var UpdatedData = {};
            UpdatedData.data = {
                routes: []
            };
            spyOn(airwayBillDataService, "getBaseControlValueService").and.returnValue(baseControlValue);
            // Setup the manageAwbCntrl
            window.manageAwbCntrl = {
                headerData: {
                    awbNumber: "123456"
                },
                updatedVersionNumber: 1,
                awbContent: {
                    versionNumber: "654321"
                }
            };
            var kgValue = 4;
            var lbsValue = 9;
            // Setup rootScope functions that are called by the ShipmentDetailsDirectiveCtrl
            $rootScope.kgToLbsConversion = function(kgValue) {
                return Math.ceil((parseInt(kgValue)) * 2.2046)
            };
            $rootScope.lbsToKgConversion = function(lbsValue) {
                return parseFloat(lbsValue / 2.2).toFixed(1)
            };
            //initializing controller dependencies
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ngTableParams: ngTableParams
            });
            // Setup internal state on the controller
            $scope.shipmentInfo = {
                "totalPieces": 4,
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            // Called this error handling function to make sure, no error throws up before setting up base control value
            $scope.commonErrorHandling();
            //Conversion between KG to Lbs and vice versa, storing the enabled value
            $scope.convertBetweenKgAndLbs();
            // Function is use to generate final object for the request of save shipment
            $scope.baseControl();
            //Assertion to save shipment
            expect($scope.tempSave.shipmentlines[0].actualWeightKg).not.toEqual($scope.shipmentInfo.shipmentlines[0].actualWeightKg);
            expect($scope.tempSave).not.toEqual($scope.shipmentInfo);
            expect(window.saveShipmentScope).toEqual($scope.tempSave);
        });
    });
    /* This test suite is used to test Validate Weight distribution and calculation of lot level weight and piece weight distribution. Test cases are written against init().
    test cases are written against the function that calculate dimensional weight*/
    describe('Validate Weight distribution -  calculation of lot level weight and piece weight distribution', function() {
        // scope variables were placed outside of the it block, to avoid duplicate creation of scope variable in each it block
        //created variable to hold $scope references
        var $scope = {};
        var $rootScope = {};
        it('Function is use to calculate dimensional weight', function() {
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants
            });
            //functional variables
            var noOfPieces = 2;
            var lengthInch = 5;
            var widthInch = 2;
            var heightInch = 3;
            var index = 0;
            // Setup internal state of the controller by defining scope attributes
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 3,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function being called for dimensional weight calculation
            $scope.dimensionalWtCal(noOfPieces, lengthInch, widthInch, heightInch, index);
            var expected = Math.ceil(((noOfPieces * lengthInch * widthInch * heightInch) / 194) / 2.2046);
            var results = $scope.shipmentInfo.shipmentlines[index].dimWeight;
            // Assertion for calculate dimensional weight
            expect(results).toEqual(expected);
        });
    });
    /* This test suite is used to test Validate Floor bearing weight and calculation of floor bearing weights.
    test cases are written against  function that calculate floor bearing weight for different sizes of sides and various weight*/
    describe('Validate Floor bearing weight - calculate floor bearing weights', function() {
        // Define the Service stubs
        var manageAWBService = {};
        var ErrorHandlingService;
        var ngTableParams;
        // created variable to hold $scope references
        var $scope = {};
        var $rootScope = {};
        it('Floor Bearing weight Calculation for side less than 12 and weight less than 151', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs
            });
            //functional variables
            var actualWeightPerPiece = 2;
            var lengthInch = 5;
            var widthInch = 2;
            var heightInch = 3;
            var index = 0;
            // Setup internal state of the controller by setting scope attributes
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 3,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function is being called to calculate floor bearing weight
            $scope.floorBearingWeight(lengthInch, widthInch, heightInch, index, actualWeightPerPiece);
            var expected = ShipmentDetailsErrorMsgs.noNeed;
            var results = $scope.displayMessage;
            //Assertion for Floor bearing weight error message for side less than 12 and weight less than 151
            expect(results).toEqual(expected);
        });
        it('Floor Bearing weight Calculation for length and height less than 12 and weight more than 151', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            //functional variables
            var actualWeightPerPiece = 153;
            var lengthInch = 5;
            var widthInch = 14;
            var heightInch = 3;
            var index = 0;
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 152,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function is being called to calculate floor bearing weight
            $scope.floorBearingWeight(lengthInch, widthInch, heightInch, index, actualWeightPerPiece);
            var expected = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf("."));
            var results = manageAwbCntrl.alertSecurityMsgShipmentDetails;
            //Assertion for Floor bearing weight error message for length and height less than 12 and weight more than 151
            expect(results).toEqual(expected);
        });
        it('Floor Bearing weight Calculation for width and height less than 12 and weight more than 151', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            //functional variables
            var actualWeightPerPiece = 153;
            var lengthInch = 15;
            var widthInch = 4;
            var heightInch = 3;
            var index = 0;
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 152,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function is being called to calculate floor bearing weight
            $scope.floorBearingWeight(lengthInch, widthInch, heightInch, index, actualWeightPerPiece);
            var expected = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf("."));
            var results = manageAwbCntrl.alertSecurityMsgShipmentDetails;
            // Assertion for Floor bearing weight error message for width and height less than 
            // 12 and weight more than 151
            expect(results).toEqual(expected);
        });
        it('Floor Bearing weight Calculation for length and width less than 12 and weight more than 151', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            //define scope variable
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 152,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function is being called to calculate floor bearing weight
            $scope.floorBearingWeight(5, 4, 13, 0, 153);
            var message = $scope.displayMessage;
            var expected = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf("."));
            var results = manageAwbCntrl.alertSecurityMsgShipmentDetails;
            //Assertion for Floor bearing weight error message for length and width less than 12 and weight more than 151
            expect(results).toEqual(expected);
        });
        it('Floor Bearing weight Calculation for length and width and height more than 12 and weight more than 151', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                origin: '',
                destination: 'Austin',
                actualWeightUnit: 'KG',
                dimUnit: 'CM',
                totalSpecialHandlingCodes: [],
                shipmentlines: [{
                    lineNumber: 1,
                    noOfPieces: 2,
                    actualWeight: 152,
                    lineLength: 4,
                    lineWidth: 5,
                    lineHeight: 6,
                    volume: '',
                    dimWeight: 8,
                    commodityCode: "AUS",
                    contentDesc: "DESC",
                    additionalDesc: "ADESC",
                    slac: '',
                    specialHandlingCode: "FOH",
                    dryIceUnit: "LBS",
                    isHeavy: "YES",
                    dryIceWeight: 7
                }]
            };
            $scope.shipmentForm = {};
            // this function is being called to calculate floor bearing weight
            $scope.floorBearingWeight(15, 14, 13, 0, 153);
            var expected = ShipmentDetailsErrorMsgs.noNeed;
            var results = $scope.displayMessage;
            //Assertion for Floor bearing weight error message for length and width and height
            // more than 12 and weight more than 151
            expect(results).toEqual(expected);
        });
    });
    /* This test suite is used to test Weight, volume and dimensional weight calculation, when the user toggle between different weight unit.
     test cases are written against function to identify which unit is enable/disable after save of shipment details. 
     Test cases are written for weightUnitEnableDisable(),convertBetweenKgAndLbs() and HEAIdentify(0)*/
    describe('Weight, volume and dimensional wt cal, when the user toggle between different weight unit', function() {
        //created variable to hold $scope references
        var ErrorHandlingService;
        var ngTableParams;
        var $scope = {};
        var $rootScope = {};
        it('Function to identify which unit is enable/disable after save of shipment details- LBS', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 100,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //Function is called to identify which unit is enable/disable after save of shipment details
            $scope.weightUnitEnableDisable();
            //Assertion to enable LBS weight unit and disable KG weight unit
            expect($scope.disableEnableLBS).toEqual(shipmentDetailsConstants.enable);
            expect($scope.disableEnableKG).toEqual(shipmentDetailsConstants.disable);
            // manageAwbCntrl.isSection3SC =true;
        });
        it('Function to identify which unit is enable/disable after save of shipment details- KG', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            //define scope variable
            $scope.shipmentInfo = {
                "actualWeightUnit": "KG",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 100,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //Function is called to identify which unit is enable/disable after save of shipment details
            $scope.weightUnitEnableDisable();
            //Assertion to enable LBS weight unit and disable KG weight unit
            expect($scope.disableEnableLBS).toEqual(shipmentDetailsConstants.disable);
            expect($scope.disableEnableKG).toEqual(shipmentDetailsConstants.enable);
            // manageAwbCntrl.isSection3SC =true;
        });
        it('Test to convert between Lbs to KG and vice versa, storing the enabled value', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 100,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.actualValueArray[0] = 100;
            $scope.totalWeightLbsOrKgValue = 120;
            // this function is called to convert the weight to the respective unit
            $scope.convertBetweenKgAndLbs();
            //Assert totalWeightLbsOrKgValue equal to totalActualWeight  by keeping actualWeightUnit in LBS
            expect($scope.totalWeightLbsOrKgValue).toEqual($scope.shipmentInfo.totalActualWeight);
        });
        it('Test to convert between LBS to KG and vice versa, storing the enabled value', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "KG",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 100,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.actualValueArray[0] = 100;
            $scope.totalWeightLbsOrKgValue = 120;
            // this function is called to convert the weight to the respective unit
            $scope.convertBetweenKgAndLbs();
            //Assert totalWeightLbsOrKgValue equal to totalActualWeight  by keeping actualWeightUnit in KG
            expect($scope.shipmentInfo.totalActualWeight).toEqual(120);
        })
        it('Test : Weight entered is greater than the allowed heavy weight', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 100,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 100;
            $scope.totalWeightLbsOrKgValue = 120;
            // function is called to find Weight entered is greater than Heavy weight or Not 
            $scope.HEAIdentify(0);
            // expected "Warning weight per piece  is more than allowed for the Commodity Type"
            var expected = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf("."));
            var results = window.manageAwbCntrl.alertSecurityMsgShipmentDetails;
            // If the is weight greater than the allowed heavy weight, add HEA to SHC and display msg
            expect(results).toEqual(expected);
            // Set heavy indiactor to true,
            expect($scope.shipmentInfo.shipmentlines[0].isHeavy).toEqual(true);
        });
        it('Test : if the weight is in between the heavy max and heavy min weight, set heavy indicator to true', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 30,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 30;
            $scope.totalWeightLbsOrKgValue = 120;
            // function is called to find Weight entered is greater than Heavy weight or Not 
            $scope.HEAIdentify(0);
            //Assert if the weight is in between the heavy max and heavy min weight, set heavy indicator to true
            expect($scope.shipmentInfo.shipmentlines[0].isHeavy).toEqual(true);
        });
        it('Test : weight is less than heavy min', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 20;
            $scope.totalWeightLbsOrKgValue = 120;
            // function is called to find Weight entered is greater than Heavy weight or Not 
            $scope.HEAIdentify(0);
            //Assert if the weight is less than heavy min, set heavy indicator to false
            expect($scope.shipmentInfo.shipmentlines[0].isHeavy).toEqual(false);
            //Assert if the weight is less than heavy min, set no warning message
            expect($scope.displayMessage).toEqual(ShipmentDetailsErrorMsgs.noNeed);
        });
        it('Test : Weight, volume and dimensional wt cal, when the user toggle between different weight unit(unit is IN and it is enable use the same) ', function() {
            var val = spyOn(window, "$").and.callFake(function() {
                return jQueryMockObj;
            });
            var jQueryMockObj = {
                val: function() {
                    return 'LBS';
                },
                attr: function() {
                    return undefined;
                },
                removeClass: function() {
                    return "";
                },
                hasClass: function() {
                    return "";
                },
                addClass: function() {
                    return "";
                },
                find: function() {
                    return angular.copy(jQueryMockObj);
                },
                next: function() {
                    return angular.copy(jQueryMockObj);
                }
            };
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables    
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 20;
            $scope.totalWeightLbsOrKgValue = 120;
            //Weight, volume and dimensional wt cal, when the user toggle between different weight unit
            $scope.toggleWeightUnit(val);
            // Unit is IN and it is enable use the same
            // Assertion of total dry ice weight
            expect($scope.shipmentInfo.totalDryIceWeight).toEqual(5);
            // Assertion of total volume
            expect($scope.shipmentInfo.totalVolume).toEqual(1);
            // Assertion of total dimweight
            expect($scope.shipmentInfo.totalDimWeight).toEqual(2);
        });
        it('Test : Weight, volume and dimensional wt cal, when the user toggle between different weight unit(if the unit is inch and it is disable, convert the saved CM unit into in) ', function() {
            window.manageAwbCntrl = {
                isSection3SC: true,
                headerData: {
                    awbNumber: "123456"
                },
                updatedVersionNumber: 1,
                awbContent: {
                    versionNumber: "654321"
                }
            };
            var val = spyOn(window, "$").and.callFake(function() {
                return jQueryMockObj;
            });
            var jQueryMockObj = {
                val: function() {
                    return 'LBS';
                },
                attr: function() {
                    return undefined;
                },
                removeClass: function() {
                    return "";
                },
                hasClass: function() {
                    return "";
                },
                addClass: function() {
                    return "";
                },
                find: function() {
                    return angular.copy(jQueryMockObj);
                },
                next: function() {
                    return angular.copy(jQueryMockObj);
                }
            };
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "IN",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 20;
            $scope.totalWeightLbsOrKgValue = 120;
            $scope.disableEnableInCm = true;
            $scope.insertINCMService.values = [{
                "length": 2,
                "width": 2,
                "height": 3
            }];
            //Weight, volume and dimensional wt cal, when the user toggle between different weight unit
            $scope.toggleWeightUnit(val);
            // if the unit is inch and it is disable, convert the saved CM unit into in
            // Assertion of total dry ice weight
            expect($scope.shipmentInfo.totalDryIceWeight).toEqual(5);
            // Assertion of total volume
            expect($scope.shipmentInfo.totalVolume).toEqual(1);
            // Assertion of total dimweight
            expect($scope.shipmentInfo.totalDimWeight).toEqual(1);
            // Assert if the weight is less than heavy min, set no warning message
        });
        it('Test : Weight, volume and dimensional wt cal, when the user toggle between different weight unit(If unit is cm and it is enable, use the line data)', function() {
            window.manageAwbCntrl = {
                isSection3SC: true,
                headerData: {
                    awbNumber: "123456"
                },
                updatedVersionNumber: 1,
                awbContent: {
                    versionNumber: "654321"
                }
            };
            // created a spy to stub jQuery function calls
            var val = spyOn(window, "$").and.callFake(function() {
                return jQueryMockObj;
            });
            var jQueryMockObj = {
                val: function() {
                    return 'LBS';
                },
                attr: function() {
                    return undefined;
                },
                removeClass: function() {
                    return "";
                },
                hasClass: function() {
                    return "";
                },
                addClass: function() {
                    return "";
                },
                find: function() {
                    return angular.copy(jQueryMockObj);
                },
                next: function() {
                    return angular.copy(jQueryMockObj);
                }
            };
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 20;
            $scope.totalWeightLbsOrKgValue = 120;
            $scope.disableEnableInCm = false;
            //Weight, volume and dimensional wt cal, when the user toggle between different weight unit
            $scope.toggleWeightUnit(val);
            // if the unit is inch and it is disable, convert the saved CM unit into in
            //Assertion of total dry ice weight
            expect($scope.shipmentInfo.totalDryIceWeight).toEqual(5);
            //Assertion of total volume
            expect($scope.shipmentInfo.totalVolume).toEqual(1);
            //Assertion of total dimweight
            expect($scope.shipmentInfo.totalDimWeight).toEqual(1);
            //Assert if the weight is less than heavy min, set no warning message
        });
        it('Test : Weight, volume and dimensional wt cal, when the user toggle between different weight unit(If unit is cm and it is disable, use the saved data for calculating dim weight)', function() {
            window.manageAwbCntrl = {
                isSection3SC: true,
                headerData: {
                    awbNumber: "123456"
                },
                updatedVersionNumber: 1,
                awbContent: {
                    versionNumber: "654321"
                }
            };
            // created a spy to stub jQuery function calls
            var val = spyOn(window, "$").and.callFake(function() {
                return jQueryMockObj;
            });
            var jQueryMockObj = {
                val: function() {
                    return 'LBS';
                },
                attr: function() {
                    return undefined;
                },
                removeClass: function() {
                    return "";
                },
                hasClass: function() {
                    return "";
                },
                addClass: function() {
                    return "";
                },
                find: function() {
                    return angular.copy(jQueryMockObj);
                },
                next: function() {
                    return angular.copy(jQueryMockObj);
                }
            };
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            //set up scope variables
            $scope.commodityCode = [{
                "code": "0001",
                "name": "0002",
                "heavyMaxWtPerPcLbs": "50",
                "heavyMinWtPerPcLbs": "25"
            }];
            $scope.shipmentForm = {};
            $scope.actualValueArray[0] = 20;
            $scope.totalWeightLbsOrKgValue = 120;
            $scope.disableEnableInCm = true;
            $scope.insertINCMService.values = [{
                "length": 2,
                "width": 2,
                "height": 3
            }];
            //Weight, volume and dimensional wt cal, when the user toggle between different weight unit
            $scope.toggleWeightUnit(val);
            // if the unit is inch and it is disable, convert the saved CM unit into in
            //Assertion of total dry ice weight
            expect($scope.shipmentInfo.totalDryIceWeight).toEqual(5);
            //Assertion of total volume
            expect($scope.shipmentInfo.totalVolume).toEqual(1);
            //Assertion of total dimweight
            expect($scope.shipmentInfo.totalDimWeight).toEqual(1);
            //Assert if the weight is less than heavy min, set no warning message
        });
    });
    /* This test suite is used to calculate new lot weights when adding new row. 
    test cases are written against function to check shipmentlines length after adding new rows.
    Test cases are written for addNewRow()*/
    describe('ADD new Row – calculation new lot weights', function() {
        // Define the Service stubs
        var manageAWBService = {};
        //created variable to hold $scope references
        var ErrorHandlingService;
        var ngTableParams;
        var $scope = {};
        var $rootScope = {};
        var focus = jasmine.createSpy();
        it('line should be less than 9999', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            //called this function to push new row into shipment lines
            $scope.addNewRow();
            //Assertion shipment line info length match with line length
            expect($scope.shipmentInfo.shipmentlines.length).toEqual(2);
        });
        it('Test commodity code,ice unit and content desc inherited from line', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            //called this function to push new row into shipment lines
            $scope.addNewRow();
            //Assert commodity code inherited from line 1
            expect($scope.commodityCode1).toEqual($scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode);
            // Assert content desc inherited from line 1
            expect($scope.contentDescription1).toEqual($scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription);
            //Assert dry ice unit inherited from line 1
            expect($scope.unit1).toEqual($scope.shipmentInfo.shipmentlines[0].dryIceUnit);
        });
        it('Test shipmentlines length after inserting row', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            //called this function to push new row into shipment lines
            $scope.addNewRow();
            //Assert shipmentlines after inserting row
            expect($scope.shipmentInfo.shipmentlines.push($scope.row)).toEqual($scope.shipmentInfo.shipmentlines.length);
        });
    });
    /* This test suite is used to calculate updated lot weights when removing row. 
    test cases are written against function to check shipmentlines length after removing new rows.
    Test cases are written for removeItem()*/
    describe('Delete new Row – calculation of updated lot weights', function() {
        // Define the Service stubs
        var manageAWBService = {};
        //created variable to hold $scope references
        var ErrorHandlingService;
        var ngTableParams;
        var $scope = {};
        var $rootScope = {};
        var focus = jasmine.createSpy();
        it('Test to delete Shipment details line', function() {
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            //function is called to delete Shipment details line
            $scope.removeItem(1);
            // unit is IN and it is enable use the same
            //Assertion of total dry ice weight
            expect($scope.shipmentInfo.totalDryIceWeight).toEqual(5);
            //Assertion of total volume
            expect($scope.shipmentInfo.totalVolume).toEqual(1);
            //Assertion of total dimweight
            expect($scope.shipmentInfo.totalDimWeight).toEqual(2);
        });
    });
    /* This test suite is used to Validate Section. Test cases are written against security type IAC and TSC is True and false and cargoScreenTotalPcs is less than shipperTotalPieces .
    Test cases are written for validateCargoScreening()*/
    describe('Validate Section (S3 & S5) - Section based validation and highlighting the sections greed/red', function() {
        // Define the Service stubs
        var manageAWBService = {};
        //created variable to hold $scope references
        var ErrorHandlingService;
        var ngTableParams;
        var $scope = {};
        var focus = jasmine.createSpy();
        it('test for only if security type is IAC and TSC is True', function() {
            var $rootScope = {};
            // Setup the manageAwbCntrl
            window.manageAwbCntrl = {
                alertSecurityMsgScreening: '',
                displayErrorFromSecShipment: '',
                cargoScreeningData: [{
                    "totalPieces": 2,
                    "screeningFailed": true,
                    "screeningLines": 2
                }],
                totalScreenData: {
                    "totalPieces": 2
                }
            };
            $rootScope.validateSecurityTypeIAC = true;
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            // this function is called validate cargo screening section
            $scope.validateCargoScreening();
            //Assertion below manageAwbCntrl parameters for only if security type is IAC and TSC is True
            expect(window.manageAwbCntrl.totalScreenData.totalPieces).toEqual($rootScope.shipperTotalPieces);
            expect(window.manageAwbCntrl.cargoScreeningData[0].noOfPieces).toEqual($rootScope.shipperTotalPieces);
            expect(window.manageAwbCntrl.dispAccHdrErrorCS).toEqual(false);
            expect(window.manageAwbCntrl.displayDivErrorBarCS).toEqual(false);
        });
        it('test for only if security type is IAC and TSC is false', function() {
            var $rootScope = {};
            window.manageAwbCntrl = {};
            // Setup the manageAwbCntrl
            window.manageAwbCntrl = {
                alertSecurityMsgScreening: '',
                displayErrorFromSecShipment: '',
                cargoScreeningData: [{
                    "totalPieces": 2,
                    "screeningFailed": true,
                    "screeningLines": 2
                }],
                totalScreenData: {
                    "totalPieces": 2
                }
            };
            $rootScope.validateSecurityTypeIAC = true;
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            // this function is called validate cargo screening section
            $scope.validateCargoScreening();
            //Assertion below manageAwbCntrl parameters for only if security type is IAC and TSC is false
            expect($rootScope.screenTotalIndicator).toEqual(false);
            expect(window.manageAwbCntrl.alertSecurityMsgScreening).toEqual('');
        });
        it('test for cargoScreenTotalPcs is less than shipperTotalPieces', function() {
            var $rootScope = {};
            // window.manageAwbCntrl = {};
            // Setup the manageAwbCntrl
            window.manageAwbCntrl = {
                alertSecurityMsgScreening: '',
                displayErrorFromSecShipment: '',
                cargoScreeningData: [{
                    "totalPieces": 2,
                    "screeningFailed": true,
                    "screeningLines": 2
                }],
                totalScreenData: {
                    "totalPieces": 2
                }
            };
            $rootScope.validateSecurityTypeIAC = true;
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            // Setup internal state of the controller by setting the scope attributes
            $scope.shipmentInfo = {
                "totalPieces": 10,
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            $rootScope.cargoScreenTotalPcs = 4;
            // this function is called validate cargo screening section
            $scope.validateCargoScreening();
            //Assertion for only if security type is IAC and TSC is true and cargoScreenTotalPcs is less than shipperTotalPieces
            expect(window.manageAwbCntrl.alertSecurityMsgScreening).toEqual($scope.erroMsg.nopiece);
            expect($rootScope.screenTotalIndicator).toEqual(true);
            expect(window.manageAwbCntrl.dispAccHdrErrorCS).toEqual(true);
            expect(window.manageAwbCntrl.displayDivErrorBarCS).toEqual(true);
        });
        it('test for cargoScreenTotalPcs is more than shipperTotalPieces', function() {
            var $rootScope = {};
            // Setup the manageAwbCntrl
            window.manageAwbCntrl = {
                alertSecurityMsgScreening: '',
                displayErrorFromSecShipment: '',
                cargoScreeningData: [{
                    "totalPieces": 2,
                    "screeningFailed": true,
                    "screeningLines": 2
                }],
                totalScreenData: {
                    "totalPieces": 2
                }
            };
            $rootScope.validateSecurityTypeIAC = true;
            //Controller dependencies were replaced with equivalent
            var controller = $controller('ShipmentDetailsDirectiveCtrl', {
                $scope: $scope,
                $rootScope: $rootScope,
                $element: {},
                manageAWBService: manageAWBService,
                shipmentDetailsConstants: shipmentDetailsConstants,
                ShipmentDetailsErrorMsgs: ShipmentDetailsErrorMsgs,
                ngTableParams: ngTableParams
            });
            //define scope variable
            $scope.shipmentInfo = {
                "totalPieces": 4,
                "actualWeightUnit": "LBS",
                "dimUnit": "CM",
                "totalSpecialHandlingCodes": [],
                "shipmentlines": [{
                    "lineNumber": 1,
                    "noOfPieces": 1,
                    "actualWeight": 20,
                    "lineLength": 10,
                    "lineWidth": 5,
                    "lineHeight": 6,
                    "volume": 1,
                    "dimWeight": 2,
                    "commodityCode": {
                        "commodityCode": "0000",
                        "code": "0000",
                        "name": "0000"
                    },
                    "contentDesc": {
                        "code": "LEATHER GOODS",
                        "name": "LEATHER GOODS"
                    },
                    "additionalDesc": "",
                    "slac": "",
                    "specialHandlingCode": [{
                        "code": "",
                        "name": "",
                        "description": ""
                    }],
                    "dryIceUnit": "LBS",
                    "isHeavy": false,
                    "dryIceWeight": "5",
                    "actualWeightKg": "45.5"
                }],
                "origin": {
                    "code": "DAL",
                    "name": "DAL"
                }
            };
            $scope.shipmentForm = {};
            $rootScope.cargoScreenTotalPcs = 10;
            //manageAwbCntrl.$root.shipperTotalPieces = 2;
            // this function is called validate cargo screening section
            $scope.validateCargoScreening();
            //Assertion for only if security type is IAC and TSC is true and cargoScreenTotalPcs is more than shipperTotalPieces
            expect(window.manageAwbCntrl.alertSecurityMsgScreening).toEqual('');
            expect($rootScope.screenTotalIndicator).toEqual(false);
            expect(window.manageAwbCntrl.dispAccHdrErrorCS).toEqual(false);
            expect(window.manageAwbCntrl.displayDivErrorBarCS).toEqual(false);
        });
    });
});