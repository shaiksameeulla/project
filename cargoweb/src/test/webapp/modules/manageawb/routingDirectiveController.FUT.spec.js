describe('AWB Routing Directive Controller - Functional Unit Testing', function() {
  // Load up all the dependency modules here for the Controller being tested
  // New Dependencies from 3/7 Build:
  // '$rootScope', 'ErrorHandlingService', 'airwayBillDataService', 'ngTableParams', '$modal', '$localStorage', 
  // 'manageAWBService', 'focus', '$timeout','routingDirectiveConstants','ERRORMESSAGES'

  // Modules for CargoApp from app.js 3/7 Build:
  // 'ngRoute', 'CargoApp.services', 'angularFileUpload', 'CargoApp.controllers', 'CargoApp.directives', 
  // 'ui.bootstrap', 'ngTable', 'infinite-scroll', 'ngStorage', 'ngSanitize', 'ui.select', 'pasvaz.bindonce'

  // Config from app.js is called by some services...
  var ServiceURL = { "baseUrl":"/CargoSystem/services" };
  
  // Inject the dependent modules
  beforeEach(module('CargoApp')); // To load up all the app.constants and app.values
  beforeEach(module('CargoApp.controllers'));
  beforeEach(module('CargoApp.services'));
  beforeEach(module('ngStorage'));

  var $controller, $localStorage, routingDirectiveConstants, 
      airwayBillDataService, $rootScope, manageAWBService, $timeout, $httpBackend;

  // Inject the controller provider
  beforeEach(inject(function(_$controller_, _$localStorage_, _routingDirectiveConstants_, 
                            _airwayBillDataService_, _$rootScope_, _manageAWBService_, _$timeout_,_$httpBackend_){

    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
    $localStorage = _$localStorage_;
    routingDirectiveConstants = _routingDirectiveConstants_;
    airwayBillDataService = _airwayBillDataService_;
    manageAWBService = _manageAWBService_;

    // Retain rootScope between tests to chain the tests
    if (!$rootScope){
      $rootScope = _$rootScope_;
    }

    $timeout = _$timeout_;
    $httpBackend = _$httpBackend_;

  }));

  describe('Init Controller', function() {

    // Setup the Service stubs
    var manageAWBService = {};
    var ErrorHandlingService; 
    var ngTableParams;  
    var focus = jasmine.createSpy();

    var SHARED_SCOPE = {};
    window.manageAwbCntrl = {};
    window.shipmentDetailsScope = {};

    beforeEach(function(){
      // Reset Service Stubs?
      // window.manageAwbCntrl = {};
    });

    it('Routing Directive - init function', function() {
      // Create variable to store reference to $scope
      var $scope = {};
      var CURRENT_TIME = "15:24";
      var CURRENT_DATE = "03/21/2016";

      // Setup default configurations for the app based on User login
      $rootScope.swaLocationTime = CURRENT_TIME;

      $rootScope.awbContent = { prefFlightNumbers: undefined, preferredShipDate: CURRENT_DATE };

      // When a AWB is created, it is assigned a new number. 
      // This number is saved inside the manageAWBdefaultController.
      // Mocking the manageAWBdefaultController
      window.manageAwbCntrl = {};
      window.manageAwbCntrl.updatedVersionNumber = 4; // Needed for scope.selectroute()
      window.manageAwbCntrl.headerData = { 
        awbNumber: 12345678,
        origin: 'DAL',
        destination: 'HOU',
        Fullstatus: routingDirectiveConstants.AWB_STATUS_FOH
      };
      window.manageAwbCntrl.awbContent = {
        versionNumber: ""
      }

      // Mocking the carrier options stored inside localStorage, based on user profile
      // The mock is only a subset of all the data normally inside localStorage
      $localStorage.carrierOptionsRouting = [
        { key: 'IB', value: 'IB' },
        { key: 'LH', value: 'LH' },
        { key: 'OP', value: 'OP' },
        { key: 'WN', value: 'WN' },
        { key: 'WS', value: 'WS' }
      ];

      // Mocking of the airport details for various form inputs
      $localStorage.airportDetailsRouting = [
        {"airportIata":"ABQ","cityName":"ALBUQUERQUE","countryName":"US","stateName":"NM"},
        {"airportIata":"AMA","cityName":"AMARILLO","countryName":"US","stateName":"TX"},
        {"airportIata":"MSY","cityName":"KENNER","countryName":"US","stateName":"LA"}
      ];

      // Initialize controllers with stubbed dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      // Run the Initializaton function for the Routing Directive Controller
      $scope.init();

      // TEST ASSERTIONS
      // Default weight setting is LBS, ensure weight display is set for LBS
      expect($scope.displayWeightLBS).toBe(true);

      // The init() sets up internal data models for tracking routing form input information
      // The assertions are ensuring that the collection lengths are equivalent to the mock data collection lengths
      expect($scope.originStart.length).toEqual($localStorage.airportDetailsRouting.length);
      expect($scope.airports.length).toEqual($scope.originStart.length);
      expect($scope.originOptions.length).toEqual($scope.originStart.length);
      expect($scope.destinationOptions.length).toEqual($scope.originStart.length);

      // The init() calls assignDefaultShipDateTime function internally
      // Verify assignments from assignDefaultShipDateTime()
      expect($scope.searchValues.findRoutes.shipDate).toEqual(CURRENT_DATE);
      expect($scope.searchValues.findRoutes.startDepartTime).toEqual(CURRENT_TIME);

      SHARED_SCOPE = $scope;

    });

    it('Routing Directive - initializeGetLots function', function(){
      // Function is testing routingScope.initializeGetLots

      var $scope = angular.copy(SHARED_SCOPE);
      // Setup mock data for rootScope
      // $rootScope.awbContent = { prefFlightNumbers: undefined };
      $rootScope.routingData = { 
        lots: [{ 
          lotNumber: 2,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            // actualWeight: 20, -- added by initializeGetLots()
            // lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              displayName: "Hammer",
              actualWeight: 20,
              // piecesActWtLbs: 50, -- added by initializeGetLots()
              // piecesActWtKg: 20,

              volume: 10
            },
            {
              seqNumber: 2,
              displayName: "Nails",
              actualWeight: 10,
              // piecesActWtLbs: 30,
              // piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"
        }]
      };

      // Initialize controllers with stubbed dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      // The initializeGetLots function is called by the manageAWBDefaultController after the
      // routingScope.init function completes
      // The function sets up the internal data models for tracking the lot information gathered in section 3
      $scope.initializeGetLots();
      // Write explaination of what the function does at highlevel to give context for assertions

      // TEST ASSERTIONS
      // Default setting is LBS, so ensure the ActWtLbs attributes are the correct value, and ActWtKg is undefined
      expect($scope.getLots.routedLot[0].lines[0].lineActWtLBS).toBe(30);
      expect($scope.getLots.routedLot[0].lines[0].pieces[0].piecesActWtLbs).toBe(20);
      expect($scope.getLots.routedLot[0].lines[0].pieces[0].piecesActWtKg).toBe(undefined);
      expect($scope.getLots.routedLot[0].lines[0].pieces[1].piecesActWtLbs).toBe(10);
      expect($scope.getLots.routedLot[0].lines[0].pieces[1].piecesActWtKg).toBe(undefined);
      // Ensure the odd/even attributes are assigned correctly
      expect($scope.getLots.routedLot[0].rowType).toBe(routingDirectiveConstants.ODD_ROW);

      // Ensure the check box data models have been populated with all the lot information
      expect($scope.lotCheckBoxes.length).toBe(1);
      expect($scope.lineCheckBoxes.length).toBe(1);
      expect($scope.pieceCheckBoxes.length).toBe(2);

      // Hold a reference to scope and pass it on to the next test block
      SHARED_SCOPE = $scope;

    });

    it('Routing Directive - manageAWBDefaultCntrl.afterInitalizeLot function', function(){

      // Setup the mock data for initializing the controller
      var $scope = angular.copy(SHARED_SCOPE);
      // Setup a stub for the manageAWBService - this service is used to manage communications to the server
      var manageAWBService = {};
      manageAWBService = jasmine.createSpyObj("manageAWBService",['getRoutes']);
      
      // Setup some mock data at rootScope
      $rootScope.currentDate = "03/09/2016"; // 'MM/DD/YYYY'
      $rootScope.currentTime = "0109"; // 'HHmm'
      window.shipmentDetailsScope = { commodityCode1: "0000" };

      // After the initializeGetLots() -- manageAWBDefaultController calls the RoutingDateTimeObject function
      // This is a copy and paste of the function block in order to setup the routingScope.initialSearch data model
      var MOCK_INITIAL_SEARCH = {};
      MOCK_INITIAL_SEARCH.initialSearch = {};
      MOCK_INITIAL_SEARCH.initialSearch.showOnlyFlightsWithAvailableCapacity = false;
      MOCK_INITIAL_SEARCH.initialSearch.serviceLevel = manageAwbCntrl.headerData.serviceLevel;
      MOCK_INITIAL_SEARCH.initialSearch.origin = manageAwbCntrl.headerData.origin;
      MOCK_INITIAL_SEARCH.initialSearch.destination = manageAwbCntrl.headerData.destination;
      MOCK_INITIAL_SEARCH.initialSearch.shipDateTime = $rootScope.currentDate + " " + $rootScope.currentTime;
      
      if (MOCK_INITIAL_SEARCH.searchValues === undefined){
        MOCK_INITIAL_SEARCH.searchValues = {};
      }
      if (MOCK_INITIAL_SEARCH.searchValues.findRoutes === undefined){
        MOCK_INITIAL_SEARCH.searchValues.findRoutes = {};
      }
      MOCK_INITIAL_SEARCH.searchValues.findRoutes.origin = {
          key: manageAwbCntrl.headerData.origin,
          value: manageAwbCntrl.headerData.origin
      };
      MOCK_INITIAL_SEARCH.searchValues.findRoutes.destination = {
          key: manageAwbCntrl.headerData.destination,
          value: manageAwbCntrl.headerData.destination
      };

      // Mock results from the call to assignDefaultShipDateTime which is done inside initializeGetLots
      MOCK_INITIAL_SEARCH.searchValues.findRoutes.shipDate = $rootScope.currentDate;
      MOCK_INITIAL_SEARCH.searchValues.findRoutes.startDepartTime =  $rootScope.swaLocationTime;

      // Mock server response to manageAWBService.getRoutes call
      var SERVER_RESPONSE_ROUTES = {};
      SERVER_RESPONSE_ROUTES.data = {routes: []};
      SERVER_RESPONSE_ROUTES.data.routes[0] = {
        noOfConnections: 0,
        noOfLegs : 1,
        flightLegs: [{
          carrier:"WN",
          flightNumber:5024,
          origin:"DAL",
          destination:"ATL",
          scheduledDepartureDate:"03/09/2016",
          scheduledArrivalDate:"03/09/2016",
          estimatedDepartureDate:"03/09/2016",
          scheduledDepartureTime:"17:55",
          estimatedDepartureTime:"17:55",
          scheduledArrivalTime:"20:55",
          estimatedArrivalTime:"20:55",
          estimatedArrivalDate:"03/09/2016",
          routeSeqNumber:1,
          acType:"73W",
          transitTime:"2h 00m",
          authorizedFlightCapacity:null,
          availableFlightCapacity:null,
          bookedBucketCapacity:null,
          availableBucketCapacity:{
            weight:5989,
            volume:497
          },
          originTimeZone:"America/Chicago",
          arrivalRecoveryTime:0,
          destinationTimeZone:"America/Detroit",
          routeId:1,
        }],
        routeTransitTime : "2h 00m",
        routeAuthorizedFlightCapacity: null,
        routeAvailableFlightCapacity: null,
        routeBucketBookedCapacity: null,
        routeBucketAvailableCapacity: {
          volume: 497,
          weight: 5989
        },
        limitationReasons: null,
        outsideOperatingHours: false,
        carrier: "WN",
        flightNumber: 5024,
        origin:"DAL",
        destination: "ATL",
        scheduledDepartureDate:"03/09/2016",
        scheduledArrivalDate:  "03/09/2016",
        scheduledDepartureTime: "17:55",
        scheduledArrivalTime: "20:55",
        estimatedDepartureDate: "03/09/2016",
        estimatedDepartureTime: "17:55",
        estimatedArrivalTime: "20:55",
        estimatedArrivalDate: "03/09/2016",
        acType: "73W",
        // routeId: 1, -- set by performRouteSearch call chain
      }

      // Initialize controllers with stubbed dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        manageAWBService: manageAWBService,
        routingDirectiveConstants: routingDirectiveConstants 
      });

      // Setup the scope to be in the correct state
      $scope.initialSearch = MOCK_INITIAL_SEARCH.initialSearch;
      $scope.searchValues = MOCK_INITIAL_SEARCH.searchValues;
      $scope.getLots = angular.copy(SHARED_SCOPE.getLots); // Had to do it because getLots was being overwritten somehow...


      // Stub the getRoutes function to return the mocked server data
      manageAWBService.getRoutes
      .and.callFake(function (searchValues){ 
        return {
          then: function(successCallback){ 
            // console.log("getRoutes searchValues: ", searchValues);
            successCallback(angular.copy(SERVER_RESPONSE_ROUTES));
          }
        }
      });

      // performRouteSearch function is called by the manageAWBDefaultController.RoutingDateTimeObject() after
      // the initializeGetLots function. This function does the intial search for routes when the user enter this section
      // The search is conducted using the parameters entered by the user up until section 4. And the returned values 
      // are stored inside a internal data model
      $scope.performRouteSearch('initial');

      // Internal tracking data models are updated. The assertions check the all the response values
      // were store in the models
      expect($scope.response_values.length).toBe(SERVER_RESPONSE_ROUTES.data.routes.length);
      expect($scope.listOfRoutes.length).toBe(SERVER_RESPONSE_ROUTES.data.routes.length);

      // Ensure no errors were triggered -- errors are stores in the manageAwbCntrl scope
      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(undefined);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(undefined);
      expect(window.manageAwbCntrl.fieldNameRt).toBe("");

      // Preserve the scope for next testing block
      SHARED_SCOPE = $scope;

    });

    it('Routing Directive - select route and save section after tabbing out', function(){
    // W I P
    // SELECT ROUTE USING assignRouteToLot(values.lotNumber) on ng-blur in view

      var $scope = angular.copy(SHARED_SCOPE);
      var manageAWBService = jasmine.createSpyObj("manageAWBService",['selectRoute']);
      $rootScope.routingData = {};

      // Mock the autoSaveData function for the manageAwbCntrl
      window.manageAwbCntrl.autoSaveData = function(whyNot){};
      window.manageAwbCntrl.setAWBHeaderStatus = function(whyNot){};
      // Since the function is triggered by an event, a tab event object must be mocked
      var mockEvent = { keyCode: 9 };
      // Since the function relies on jQuery to retrieve element values, a jQueryElementObj must be mocked
      var jQueryMockObj = {
          val: function(){ return "1"; },
          attr: function(){ return ""; },
          removeClass: function(){ return ""; },
          hasClass: function(){ return ""; },
          addClass: function(){ return ""; },
          find: function(){ return angular.copy(jQueryMockObj); },
          next: function(){ return angular.copy(jQueryMockObj); },
          trigger: function(){ return; }
        };

      var SERVER_RESPONSE_SELECT_ROUTE = {};
      // Data pulled from a browser test AWB -- Assuming current awbVersion = 4
      // TODO: CURRENTLY NOT USING THE SAME FLIGHT AS THE ONE BEING SENT FOR CONFIRMATION
      SERVER_RESPONSE_SELECT_ROUTE.data = JSON.parse('{"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":0,"updateInfo":null,"errorCode":null,"errorMessage":null,"routedLot":[{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":2.0,"volume":2.0,"assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"SCRN","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":1,"noOfPieces":2,"actualWeight":2.0,"volume":2.0,"contentDesc":{"code":"LEATHER GOODS","name":"LEATHER GOODS","description":"LEATHER GOODS"},"specialHandlingCode":[],"pieces":[{"seqNumber":1,"actualWeight":1,"actualWeightKg":0.5,"volume":1.0,"displayName":"PCID 0001"},{"seqNumber":2,"actualWeight":1,"actualWeightKg":0.5,"volume":1.0,"displayName":"PCID 0002"}],"uniqueLineNo":1}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[{"seqNum":1,"schedDepDate":"03/22/2016","schedArrDate":"03/22/2016","scheduledDepTime":"11:00","scheduledArrTime":"12:05","destination":{"code":"HOU","name":"HOU","description":"HOU"},"origin":{"code":"DAL","name":"DAL","description":"DAL"},"flightNumber":17,"carrierCode":{"code":"WN","name":"WN","description":"WN"},"flightLegSuffix":null,"originTimeZone":null,"schedDepDateTimeLocal":201603221100,"schedArrDateTimeLocal":201603221205,"estArrDateTimeLocal":201603221205,"estDepDateTimeLocal":201603221100,"flightLegKey":"WN0017DAL20160322","origArrunk":false,"destArrunk":false,"destinationTimeZone":null,"estimatedArrDate":"03/22/2016","estimatedDepDate":"03/22/2016","estArrTime":"12:05","estDepTime":"11:00","forceBooking":false}],"routingInvalid":false,"samePlaneInd":false}],"awbNumber":0,"awbPrefix":0,"limitationReasons":null,"outsideOperatingHours":false,"awbVersion":5,"awbStatus":"FOH"}');

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        manageAWBService: manageAWBService,
        routingDirectiveConstants: routingDirectiveConstants 
      });

      // Setup the scope to be in the prepared state
      $scope.getLots = angular.copy(SHARED_SCOPE.getLots);
      $scope.enteredRouteNumber = 1; // -- routeId value of the first route 

      // console.log("$scope.response_values",$scope.response_values);
      // console.log("SERVER_RESPONSE_SELECT_ROUTE",SERVER_RESPONSE_SELECT_ROUTE.data.routedLot[0].flightLegs[0]);

      // Create a flag for the save function inside manageAwbCntrl so that after the function call we can
      // check whether the save function was called or not
      var autoSaveDataCall = spyOn(window.manageAwbCntrl,'autoSaveData');
      var setAWBHeaderStatusCall = spyOn(window.manageAwbCntrl,'setAWBHeaderStatus');

      manageAWBService.selectRoute
      .and.callFake(function (searchValues){ 
        return {
          then: function(successCallback){ 
            // console.log("getRoutes searchValues: ", searchValues);
            successCallback(angular.copy(SERVER_RESPONSE_SELECT_ROUTE));
          }
        }
      });
      
      // Make a jQuery stub to process the conditional logic inside navToNextSection, other receive undefined .trim() error
      var element = spyOn(window, "$").and.callFake(function(){ 
        return jQueryMockObj;
      });

      // Overwriting angular.element so that it doesn't fail inside checkIfRouteIsEntered()
      var angular_element_holder = angular.element;
      angular.element = function(args){ return { scope: function(){ return $scope; } } };

      // The function retrieves the input from the route# input field and verifies that it is a valid input
      // before validating the route and assigning the route to the lot
      $scope.assignRouteToLot($scope.getLots.routedLot[0].lotNumber);

      // Restore angular.element after function's need of the stub has expired
      angular.element = angular_element_holder;

      // Clicking on the Select Route button calls selectRoute('routing')
      $scope.selectRoute('routing');

      // The function determines whether to move to the next section or not. And before moving make some changes
      // to the internal data model.
      $scope.navToNextSection(mockEvent, 'routeButton');


      // Calls manageAWBDefaultCntrl for saving the AWB
      expect(autoSaveDataCall).toHaveBeenCalled();

      // console.log("DONE: ", $scope.getLots);

      // SHARED_SCOPE = $scope;
      SHARED_SCOPE = undefined;
      angular.element = angular_element_holder;

    });

  });

  describe('Routing Directive splitLot function', function() {

    // Setup the Service stubs
    var manageAWBService = {};

    it('Test splitLot - Happy Path - LBS', function() {

      // console.log("rootScope",$rootScope);

      $rootScope.routingData = {};
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          lotNumber: 2,
          isChecked: false,
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents",
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 90, // For LBS
          volume: 20,
          noOfPieces: 3,
          lines: [{
            isChecked: false,
            seqNum: 1,
            lotNumber: 2,
            actualWeight: 90,
            lineActWtLBS: 90,
            volume: 20,
            lineSHC: "",
            noOfPieces: 3,
            pieces: [{
              seqNum: 1,
              seqNumber: 1,
              lotNumber: 2,
              displayName: 'Hammer 0',
              isChecked: true,
              actualWeight: 30, // New lot.line.actualWeight will equal NaN if you don't include this
              piecesActWtLbs: 30,
              volume: 10
            },
            {
              seqNum: 1,
              seqNumber: 2,
              lotNumber: 2,
              displayName: 'Hammer 1',
              isChecked: true,
              actualWeight: 30,
              piecesActWtLbs: 30,
              volume: 10
            },
            {
              seqNum: 1,
              seqNumber: 3,
              lotNumber: 2,
              displayName: 'Hammer 2',
              isChecked: false,
              actualWeight: 30,
              piecesActWtLbs: 30,
              volume: 10
            }]
          }]
        }] 
      };

      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = 2;

      $scope.lotCheckBoxes = angular.copy($scope.getLots.routedLot);
      $scope.lineCheckBoxes = angular.copy($scope.getLots.routedLot[0].lines);
      $scope.pieceCheckBoxes = angular.copy($scope.getLots.routedLot[0].lines[0].pieces);

      // Run the function being tested
      $scope.splitLot();

      // console.log("After:",$scope.getLots.routedLot);

      // Actual Test Functions
      expect($scope.disableResetLink).toBe(false);
      expect($rootScope.changeOrNotManageawb).toBe(false);
      expect($scope.getLots.routedLot.length).toBe(2);

      // New lot should be an incremeted value
      expect($scope.getLots.routedLot[1].lotNumber).toBe(3);

      // Make sure Lot weights were calculated correctly
      expect($scope.getLots.routedLot[0].actualWeight).toBe(30);
      expect($scope.getLots.routedLot[1].actualWeight).toBe(60);

      // Make sure the piece counts were calculated correctly
      expect($scope.getLots.routedLot[0].noOfPieces).toBe(1);
      expect($scope.getLots.routedLot[1].noOfPieces).toBe(2);

      // Make sure 1st lot line weights, volumes, and counts were calculated correctly
      expect($scope.getLots.routedLot[0].lines[0].actualWeight).toBe(30);
      expect($scope.getLots.routedLot[0].lines[0].lineActWtLBS).toBe(30);
      expect($scope.getLots.routedLot[0].lines[0].noOfPieces).toBe(1);
      expect($scope.getLots.routedLot[0].lines[0].volume).toBeCloseTo(10);

      // Make sure 2nd lot line weights and counts were calculated correctly
      expect($scope.getLots.routedLot[1].lines[0].actualWeight).toBe(60);
      expect($scope.getLots.routedLot[1].lines[0].lineActWtLBS).toBe(60);
      expect($scope.getLots.routedLot[1].lines[0].noOfPieces).toBe(2);
      expect($scope.getLots.routedLot[1].lines[0].volume).toBeCloseTo(20);

      // Make sure piece weights were not altered
      expect($scope.getLots.routedLot[0].lines[0].pieces[0].actualWeight).toBe(30);
      expect($scope.getLots.routedLot[1].lines[0].pieces[0].actualWeight).toBe(30);
      expect($scope.getLots.routedLot[1].lines[0].pieces[1].actualWeight).toBe(30);

      // NOTE: splitLot does not update the newLot.line.pieces.lotNumber with the new lot number, it remained undefined

    });

  });

  describe('Toggling Find Route & Assign Route', function(){

    it('Toggles to SPECIFY ROUTE view state from FIND ROUTE view state and initializes the searchValues.specifyRoutes', function() {

      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants 
      });

      // Clicking on the Specify Route radio button triggers conditionForRouteOrSpecify('SPECIFYROUTE') 
      $scope.conditionForRouteOrSpecify('SPECIFYROUTE');

      // ng-model for showing the specify route view should be true
      expect($scope.specifyRoute).toBe(true);
      expect($scope.searchValues.specifyRoutes.length).toBe(5);
      expect($scope.searchValues.specifyRoutes[0]).toEqual({ carrier: "", fltNbr: "", DepDate: "", origin: "", destination: "" });
      expect($scope.searchValues.specifyRoutes[1]).toEqual({ carrier: "", fltNbr: "", DepDate: "", origin: "", destination: "" });
      expect($scope.searchValues.specifyRoutes[2]).toEqual({ carrier: "", fltNbr: "", DepDate: "", origin: "", destination: "" });
      expect($scope.searchValues.specifyRoutes[3]).toEqual({ carrier: "", fltNbr: "", DepDate: "", origin: "", destination: "" });
      expect($scope.searchValues.specifyRoutes[4]).toEqual({ carrier: "", fltNbr: "", DepDate: "", origin: "", destination: "" });

    });

    it('Toggles to FIND ROUTE view state from SPECIFY ROUTE view state', function() {

      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants 
      }); 

      // Setup the SPECIFY ROUTE state
      $scope.specifyRoute = true;

      $scope.searchValues.findRoutes.origin = "DAL";
      $scope.searchValues.findRoutes.destination = "HOU";
      $scope.searchValues.findRoutes.shipDate = "03/09/2016";
      $scope.searchValues.findRoutes.startDepartTime = "17:55";
      $scope.searchValues.excludedCities = "ATL";

      // Clicking on the Specify Route radio button triggers conditionForRouteOrSpecify('FINDROUTES') 
      $scope.conditionForRouteOrSpecify(routingDirectiveConstants.RTE_CONDITION_FIND_ROUTE);

      // ng-model for showing the specify route view should be false
      expect($scope.specifyRoute).toBe(false);

      // Make assertions about $scope.searchValues.findRoutes not changing
      expect($scope.searchValues.findRoutes.origin).toBe("DAL");
      expect($scope.searchValues.findRoutes.destination).toBe("HOU");
      expect($scope.searchValues.findRoutes.shipDate).toBe("03/09/2016");
      expect($scope.searchValues.findRoutes.startDepartTime).toBe("17:55");
      expect($scope.searchValues.excludedCities).toBe("ATL");
      
    });

  });

  describe('Validating specified routes', function(){

    it('enables the Validate Route button', function() {

      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        $timeout: $timeout,
        routingDirectiveConstants: routingDirectiveConstants 
      }); 

      var element = spyOn(window, "$").and.callFake(function (query){ 
        return { val: function(){ return "Stuff"; }, hasClass: function(){ return false; }, text: function(){ return "Text Stuff"; }}
      });

      // NOTE: 
      // $httpBackend error was being triggered by $timeout.flush() so I added an expect/flush for httpBackend as well
      $httpBackend.expect('GET','home/homepage.html').respond('');
      $httpBackend.flush();

      //
      $scope.enableValidateRoute = true;

      $scope.updateValidateButton();

      // Flush the timeout queue so that the async function within updateValidateButton can complete
      $timeout.flush();

      // Assertions about end state
      expect($scope.enableValidateRoute).toBe(false);

    });

    it('send a request to the webservice to validate route', function(){

      var $scope = {};
      var REQUESTED_SEARCH_VALUE = undefined;
      var EXPECTED_SEARCH_VALUE = {"awbNumber":30036565,"awbPrefix":526,"awbVersion":5,"commodityCode":"0000","serviceLevel":"AOG","shipDateTime":"03/23/2016 16:28","specialHandlingCodes":"AVI","weight":10,"volume":20,"showOnlyFlightsWithAvailableCapacity":false,"existingRouteFlightLeg":[],"routeFlightLeg":[{"carrier":"WN","flightNumber":"39","depDate":"03/24/2016","origin":"DAL","destination":"HOU"}],"routedLot":[{"actualWeight":10,"assignedRunner":null,"commodityCode":"0000","currentLotStatus":"SCRN","deliveredToCustomer":null,"flightLegs":[],"lines":[{"actualWeight":10,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"noOfPieces":2,"pieces":[{"actualWeight":5,"actualWeightKg":2.3,"seqNumber":1,"volume":10,"displayName":"PCID 0001"},{"actualWeight":5,"actualWeightKg":2.2,"seqNumber":2,"volume":10,"displayName":"PCID 0002"}],"seqNum":"1","specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"uniqueLineNo":1,"volume":"20"}],"lotId":null,"lotNumber":1,"lotPieces":null,"lotStatusStation":"SCRN - DAL","noOfPieces":2,"previousLotStatus":"DRFT","reroutingReason":null,"routingInvalid":true,"samePlaneInd":false,"totalPieces":null,"totalWeightKgs":null,"totalWeightLbs":null,"volume":"20","wareHouseLocation":null}]};
      window.manageAwbCntrl.headerData = { awbNumber: 30036565 };
      window.manageAwbCntrl.awbContent = { versionNumber: 4 };
      // Setup a an stub to catch the request object beig sent to the web service
      var manageAWBService = jasmine.createSpyObj("manageAWBService",['validateRoute']);
      manageAWBService.validateRoute
      .and.callFake(function (searchValues){ 
        // Save the values for assertions
        REQUESTED_SEARCH_VALUE = angular.copy(searchValues);
        return {
          then: function(successCallback){ 
            successCallback({ data: { errorMessage: "INVALID ROUTE" } });
          }
        }
      });

      // INITIALIZE the CONTROLLER for testing with injected dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService
      });
        
      // SETUP SCOPE STATE
      $scope.searchValues = {
        specifyRoutes: [{"carrier":"WN","fltNbr":"39","DepDate":"03/24/2016","origin":{"key":"DALLAS, TX (US) - DAL","value":"DAL"},"destination":{"key":"HOUSTON, TX (US) - HOU","value":"HOU"}}],
        // Have to initialize findRoute as well because validateRoutes uses it to determine the tmpReqForValidateRoute.shipDateTime
        findRoutes: {"origin":{"key":"DAL","value":"DAL"},"destination":{"key":"HOU","value":"HOU"},"shipDate":"03/23/2016","startDepartTime":"16:28"}
      }

      $scope.getLots = { 
        routedLot: [{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":10,"volume":"20","assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"DRFT","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":"LN1","noOfPieces":2,"actualWeight":10,"volume":"20","contentDesc":"MEDICAL SUPPLIES","specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"pieces":[{"seqNumber":1,"actualWeight":5,"actualWeightKg":2.3,"volume":10,"displayName":"0001","seqNum":"LN1","piecesActWtLbs":5,"piecesActWtKg":2.3},{"seqNumber":2,"actualWeight":5,"actualWeightKg":2.2,"volume":10,"displayName":"0002","seqNum":"LN1","piecesActWtLbs":5,"piecesActWtKg":2.2}],"uniqueLineNo":1,"lineSHC":"AVI","lineOrig":"","lineDest":"","lotNumber":1,"lineActWtLBS":10}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[],"routingInvalid":true,"samePlaneInd":false,"contentDesc":"MEDICAL SUPPLIES","rowType":"odd"}]
      };

      // run function
      $scope.validateRoutes('directvalidate');

      // make assertions
      var requestObj = JSON.parse(REQUESTED_SEARCH_VALUE);
      // console.log("REQUESTED_SEARCH_VALUE",requestObj,REQUESTED_SEARCH_VALUE);

      // console.log("START");
      // console.log(requestObj["routedLot"],EXPECTED_SEARCH_VALUE["routedLot"]);
      // console.log("END");

      // First round of assertions about the request object
      expect(requestObj["awbNumber"]).toBe(EXPECTED_SEARCH_VALUE["awbNumber"]);
      expect(requestObj["awbPrefix"]).toBe(EXPECTED_SEARCH_VALUE["awbPrefix"]);
      expect(requestObj["awbVersion"]).toBe(EXPECTED_SEARCH_VALUE["awbVersion"]);
      expect(requestObj["commodityCode"]).toBe(EXPECTED_SEARCH_VALUE["commodityCode"]);
      expect(requestObj["shipDateTime"]).toBe(EXPECTED_SEARCH_VALUE["shipDateTime"]);
      expect(requestObj["specialHandlingCodes"]).toBe(EXPECTED_SEARCH_VALUE["specialHandlingCodes"]);
      expect(requestObj["weight"]).toBe(EXPECTED_SEARCH_VALUE["weight"]);
      expect(requestObj["volume"]).toBeCloseTo(EXPECTED_SEARCH_VALUE["volume"]);
      expect(requestObj["showOnlyFlightsWithAvailableCapacity"]).toBe(EXPECTED_SEARCH_VALUE["showOnlyFlightsWithAvailableCapacity"]);
      expect(requestObj["existingRouteFlightLeg"].length).toBe(EXPECTED_SEARCH_VALUE["existingRouteFlightLeg"].length);
      
      // Deep Assertion about lot details
      expect(angular.equals(requestObj["routedLot"],EXPECTED_SEARCH_VALUE["routedLot"])).toBe(true);
      expect(angular.equals(requestObj["routeFlightLeg"],EXPECTED_SEARCH_VALUE["routeFlightLeg"])).toBe(true);

      // Deep Assertion on Line level details
      // expect(angular.equals(requestObj["routedLot"][0]["lines"],EXPECTED_SEARCH_VALUE["routedLot"][0]["lines"])).toBe(true);
      // Deep assertions about flight leg details
      // expect(angular.equals(requestObj["routedLot"][0]["flightLegs"],EXPECTED_SEARCH_VALUE["routedLot"][0]["flightLegs"])).toBe(true);
      
      // CAPTURED VALUES FROM APPLICATION
      var SUCCESS = {
        REQUEST: {"awbNumber":30036576,"awbPrefix":526,"awbVersion":4,"commodityCode":"0000","serviceLevel":"AOG","shipDateTime":"03/23/2016 16:28","specialHandlingCodes":"AVI","weight":10,"volume":20,"showOnlyFlightsWithAvailableCapacity":false,"existingRouteFlightLeg":[],"routeFlightLeg":[{"carrier":"WN","flightNumber":"39","depDate":"03/24/2016","origin":"DAL","destination":"HOU"}],"routedLot":[{"actualWeight":10,"assignedRunner":null,"commodityCode":"0000","currentLotStatus":"SCRN","deliveredToCustomer":null,"flightLegs":[],"lines":[{"actualWeight":10,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"noOfPieces":2,"pieces":[{"actualWeight":5,"actualWeightKg":2.3,"seqNumber":1,"volume":10,"displayName":"PCID 0001"},{"actualWeight":5,"actualWeightKg":2.2,"seqNumber":2,"volume":10,"displayName":"PCID 0002"}],"seqNum":"1","specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"uniqueLineNo":1,"volume":"20"}],"lotId":null,"lotNumber":1,"lotPieces":null,"lotStatusStation":"SCRN - DAL","noOfPieces":2,"previousLotStatus":"DRFT","reroutingReason":null,"routingInvalid":true,"samePlaneInd":false,"totalPieces":null,"totalWeightKgs":null,"totalWeightLbs":null,"volume":"20","wareHouseLocation":null}]},
        RESPONSE: {"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":0,"updateInfo":null,"errorCode":null,"errorMessage":null,"routedLot":[{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"SCRN","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"pieces":[{"seqNumber":1,"actualWeight":5,"actualWeightKg":2.3,"volume":10.0,"displayName":"PCID 0001"},{"seqNumber":2,"actualWeight":5,"actualWeightKg":2.2,"volume":10.0,"displayName":"PCID 0002"}],"uniqueLineNo":1}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[{"seqNum":1,"schedDepDate":"03/24/2016","schedArrDate":"03/24/2016","scheduledDepTime":"16:00","scheduledArrTime":"17:05","destination":{"code":"HOU","name":"HOU","description":"HOU"},"origin":{"code":"DAL","name":"DAL","description":"DAL"},"flightNumber":39,"carrierCode":{"code":"WN","name":"WN","description":"WN"},"flightLegSuffix":null,"originTimeZone":null,"schedDepDateTimeLocal":201603241600,"schedArrDateTimeLocal":201603241705,"estArrDateTimeLocal":201603241705,"estDepDateTimeLocal":201603241600,"flightLegKey":"WN0039DAL20160324","origArrunk":false,"destArrunk":false,"destinationTimeZone":null,"estimatedArrDate":"03/24/2016","estimatedDepDate":"03/24/2016","estArrTime":"17:05","estDepTime":"16:00","forceBooking":false}],"routingInvalid":false,"samePlaneInd":false}],"awbNumber":0,"awbPrefix":0,"limitationReasons":null,"outsideOperatingHours":false,"awbVersion":5,"awbStatus":"FOH"}
      }

    });

    it('send a request to the webservice to validate route', function(){

      var $scope = {};
      $rootScope.routingData = {};
      var RESPONSE = {"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":0,"updateInfo":null,"errorCode":null,"errorMessage":null,"routedLot":[{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"SCRN","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"pieces":[{"seqNumber":1,"actualWeight":5,"actualWeightKg":2.3,"volume":10.0,"displayName":"PCID 0001"},{"seqNumber":2,"actualWeight":5,"actualWeightKg":2.2,"volume":10.0,"displayName":"PCID 0002"}],"uniqueLineNo":1}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[{"seqNum":1,"schedDepDate":"03/24/2016","schedArrDate":"03/24/2016","scheduledDepTime":"16:00","scheduledArrTime":"17:05","destination":{"code":"HOU","name":"HOU","description":"HOU"},"origin":{"code":"DAL","name":"DAL","description":"DAL"},"flightNumber":39,"carrierCode":{"code":"WN","name":"WN","description":"WN"},"flightLegSuffix":null,"originTimeZone":null,"schedDepDateTimeLocal":201603241600,"schedArrDateTimeLocal":201603241705,"estArrDateTimeLocal":201603241705,"estDepDateTimeLocal":201603241600,"flightLegKey":"WN0039DAL20160324","origArrunk":false,"destArrunk":false,"destinationTimeZone":null,"estimatedArrDate":"03/24/2016","estimatedDepDate":"03/24/2016","estArrTime":"17:05","estDepTime":"16:00","forceBooking":false}],"routingInvalid":false,"samePlaneInd":false}],"awbNumber":0,"awbPrefix":0,"limitationReasons":null,"outsideOperatingHours":false,"awbVersion":5,"awbStatus":"FOH"}
      window.manageAwbCntrl.headerData = { awbNumber: 30036565 };
      // Setup a an stub to catch the request object beig sent to the web service
      var manageAWBService = jasmine.createSpyObj("manageAWBService",['validateRoute']);
      manageAWBService.validateRoute
      .and.callFake(function (searchValues){ 
        // Save the values for assertions
        return {
          then: function(successCallback){ 
            successCallback({ data: RESPONSE });
          }
        }
      });

      // INITIALIZE the CONTROLLER for testing with injected dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        manageAWBService: manageAWBService
      });
        
      // SETUP SCOPE STATE
      $scope.searchValues = {
        specifyRoutes: [{"carrier":"WN","fltNbr":"39","DepDate":"03/24/2016","origin":{"key":"DALLAS, TX (US) - DAL","value":"DAL"},"destination":{"key":"HOUSTON, TX (US) - HOU","value":"HOU"}}],
        // Have to initialize findRoute as well because validateRoutes uses it to determine the tmpReqForValidateRoute.shipDateTime
        findRoutes: {"origin":{"key":"DAL","value":"DAL"},"destination":{"key":"HOU","value":"HOU"},"shipDate":"03/23/2016","startDepartTime":"16:28"}
      }

      $scope.getLots = { 
        routedLot: [{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":10,"volume":"20","assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"DRFT","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":"LN1","noOfPieces":2,"actualWeight":10,"volume":"20","contentDesc":"MEDICAL SUPPLIES","specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"pieces":[{"seqNumber":1,"actualWeight":5,"actualWeightKg":2.3,"volume":10,"displayName":"0001","seqNum":"LN1","piecesActWtLbs":5,"piecesActWtKg":2.3},{"seqNumber":2,"actualWeight":5,"actualWeightKg":2.2,"volume":10,"displayName":"0002","seqNum":"LN1","piecesActWtLbs":5,"piecesActWtKg":2.2}],"uniqueLineNo":1,"lineSHC":"AVI","lineOrig":"","lineDest":"","lotNumber":1,"lineActWtLBS":10}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[],"routingInvalid":true,"samePlaneInd":false,"contentDesc":"MEDICAL SUPPLIES","rowType":"odd"}]
      };

      // run function
      $scope.validateRoutes('directvalidate')

      // Top Level state assertions
      expect(window.manageAwbCntrl.updatedVersionNumber).toBe(RESPONSE.awbVersion);

      // Lot assignment Assertions
      expect($scope.getLots.routedLot[0].lotNumber).toBe(RESPONSE.routedLot[0].lotNumber);
      expect($scope.getLots.routedLot[0].noOfPieces).toBe(RESPONSE.routedLot[0].noOfPieces);
      expect($scope.getLots.routedLot[0].actualWeight).toBe(RESPONSE.routedLot[0].actualWeight);
      expect($scope.getLots.routedLot[0].volume).toBe(RESPONSE.routedLot[0].volume);
      expect($scope.getLots.routedLot[0].currentLotStatus).toBe(RESPONSE.routedLot[0].currentLotStatus);
      expect($scope.getLots.routedLot[0].lotStatusStation).toBe(RESPONSE.routedLot[0].lotStatusStation);
      expect($scope.getLots.routedLot[0].versionNumber).toBe(RESPONSE.routedLot[0].versionNumber);
      expect($scope.getLots.routedLot[0].routingInvalid).toBe(RESPONSE.routedLot[0].routingInvalid);
      expect($scope.getLots.routedLot[0].assignedRunner).toBe(RESPONSE.routedLot[0].assignedRunner);
      expect($scope.getLots.routedLot[0].wareHouseLocation).toBe(RESPONSE.routedLot[0].wareHouseLocation);
      expect($scope.getLots.routedLot[0].deliveredToCustomer).toBe(RESPONSE.routedLot[0].deliveredToCustomer);

      // Flight leg assignment Assertions
      expect($scope.getLots.routedLot[0].flightLegs[0].carrier).toBe(RESPONSE.routedLot[0].flightLegs[0].carrierCode.code);
      expect($scope.getLots.routedLot[0].flightLegs[0].destination).toBe(RESPONSE.routedLot[0].flightLegs[0].destination.code);
      expect($scope.getLots.routedLot[0].flightLegs[0].origin).toBe(RESPONSE.routedLot[0].flightLegs[0].origin.code);
      expect($scope.getLots.routedLot[0].flightLegs[0].estimatedArrivalTime).toBe(RESPONSE.routedLot[0].flightLegs[0].estArrTime);
      expect($scope.getLots.routedLot[0].flightLegs[0].estimatedDepartureTime).toBe(RESPONSE.routedLot[0].flightLegs[0].estDepTime);
      expect($scope.getLots.routedLot[0].flightLegs[0].estimatedArrivalDate).toBe(RESPONSE.routedLot[0].flightLegs[0].estimatedArrDate);
      expect($scope.getLots.routedLot[0].flightLegs[0].estimatedDepartureDate).toBe(RESPONSE.routedLot[0].flightLegs[0].estimatedDepDate);
      expect($scope.getLots.routedLot[0].flightLegs[0].scheduledArrivalDate).toBe(RESPONSE.routedLot[0].flightLegs[0].schedArrDate);
      expect($scope.getLots.routedLot[0].flightLegs[0].scheduledDepartureDate).toBe(RESPONSE.routedLot[0].flightLegs[0].schedDepDate);
      expect($scope.getLots.routedLot[0].flightLegs[0].scheduledArrivalTime).toBe(RESPONSE.routedLot[0].flightLegs[0].scheduledArrTime);
      expect($scope.getLots.routedLot[0].flightLegs[0].scheduledDepartureTime).toBe(RESPONSE.routedLot[0].flightLegs[0].scheduledDepTime);
      expect($scope.getLots.routedLot[0].flightLegs[0].schedDepDateTimeLocal).toBe(RESPONSE.routedLot[0].flightLegs[0].schedDepDateTimeLocal);
      expect($scope.getLots.routedLot[0].flightLegs[0].schedArrDateTimeLocal).toBe(RESPONSE.routedLot[0].flightLegs[0].schedArrDateTimeLocal);
      expect($scope.getLots.routedLot[0].flightLegs[0].estArrDateTimeLocal).toBe(RESPONSE.routedLot[0].flightLegs[0].estArrDateTimeLocal);
      expect($scope.getLots.routedLot[0].flightLegs[0].origArrunk).toBe(RESPONSE.routedLot[0].flightLegs[0].origArrunk);
      expect($scope.getLots.routedLot[0].flightLegs[0].destArrunk).toBe(RESPONSE.routedLot[0].flightLegs[0].destArrunk);
      expect($scope.getLots.routedLot[0].flightLegs[0].estDepDateTimeLocal).toBe(RESPONSE.routedLot[0].flightLegs[0].estDepDateTimeLocal);
      expect($scope.getLots.routedLot[0].flightLegs[0].flightLegKey).toBe(RESPONSE.routedLot[0].flightLegs[0].flightLegKey);
      expect($scope.getLots.routedLot[0].flightLegs[0].forceBooking).toBe(RESPONSE.routedLot[0].flightLegs[0].forceBooking);
      expect($scope.getLots.routedLot[0].flightLegs[0].flightNumber).toBe(RESPONSE.routedLot[0].flightLegs[0].flightNumber);
      expect($scope.getLots.routedLot[0].flightLegs[0].seqNum).toBe(RESPONSE.routedLot[0].flightLegs[0].seqNum);

      // No need to validate external calls
      // $scope.calculateFlightLegDetails($scope.getLots.routedLot); -- updates flight leg information
      // $scope.updateAllArrays(); -- updates check box models and the odd/even assignments
      
      // CAPTURED VALUES FROM APPLICATION
      // var SUCCESS = {
      //   REQUEST: {"awbNumber":30036576,"awbPrefix":526,"awbVersion":4,"commodityCode":"0000","serviceLevel":"AOG","shipDateTime":"03/23/2016 16:28","specialHandlingCodes":"AVI","weight":10,"volume":20,"showOnlyFlightsWithAvailableCapacity":false,"existingRouteFlightLeg":[],"routeFlightLeg":[{"carrier":"WN","flightNumber":"39","depDate":"03/24/2016","origin":"DAL","destination":"HOU"}],"routedLot":[{"actualWeight":10,"assignedRunner":null,"commodityCode":"0000","currentLotStatus":"SCRN","deliveredToCustomer":null,"flightLegs":[],"lines":[{"actualWeight":10,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"noOfPieces":2,"pieces":[{"actualWeight":5,"actualWeightKg":2.3,"seqNumber":1,"volume":10,"displayName":"PCID 0001"},{"actualWeight":5,"actualWeightKg":2.2,"seqNumber":2,"volume":10,"displayName":"PCID 0002"}],"seqNum":"1","specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"uniqueLineNo":1,"volume":"20"}],"lotId":null,"lotNumber":1,"lotPieces":null,"lotStatusStation":"SCRN - DAL","noOfPieces":2,"previousLotStatus":"DRFT","reroutingReason":null,"routingInvalid":true,"samePlaneInd":false,"totalPieces":null,"totalWeightKgs":null,"totalWeightLbs":null,"volume":"20","wareHouseLocation":null}]},
      //   RESPONSE: {"lastUpdatedBy":null,"lastUpdatedDate":null,"versionNumber":0,"updateInfo":null,"errorCode":null,"errorMessage":null,"routedLot":[{"lotId":null,"totalPieces":null,"lotPieces":null,"totalWeightLbs":null,"totalWeightKgs":null,"lotNumber":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"assignedRunner":null,"wareHouseLocation":null,"deliveredToCustomer":null,"previousLotStatus":"SCRN","lotStatusStation":"SCRN - DAL","lines":[{"seqNum":1,"noOfPieces":2,"actualWeight":10.0,"volume":20.0,"contentDesc":{"code":"MEDICAL SUPPLIES","name":"MEDICAL SUPPLIES","description":"MEDICAL SUPPLIES"},"specialHandlingCode":[{"code":"AVI","name":"AVI","description":"AVI"}],"pieces":[{"seqNumber":1,"actualWeight":5,"actualWeightKg":2.3,"volume":10.0,"displayName":"PCID 0001"},{"seqNumber":2,"actualWeight":5,"actualWeightKg":2.2,"volume":10.0,"displayName":"PCID 0002"}],"uniqueLineNo":1}],"currentLotStatus":"SCRN","commodityCode":"0000","reroutingReason":null,"flightLegs":[{"seqNum":1,"schedDepDate":"03/24/2016","schedArrDate":"03/24/2016","scheduledDepTime":"16:00","scheduledArrTime":"17:05","destination":{"code":"HOU","name":"HOU","description":"HOU"},"origin":{"code":"DAL","name":"DAL","description":"DAL"},"flightNumber":39,"carrierCode":{"code":"WN","name":"WN","description":"WN"},"flightLegSuffix":null,"originTimeZone":null,"schedDepDateTimeLocal":201603241600,"schedArrDateTimeLocal":201603241705,"estArrDateTimeLocal":201603241705,"estDepDateTimeLocal":201603241600,"flightLegKey":"WN0039DAL20160324","origArrunk":false,"destArrunk":false,"destinationTimeZone":null,"estimatedArrDate":"03/24/2016","estimatedDepDate":"03/24/2016","estArrTime":"17:05","estDepTime":"16:00","forceBooking":false}],"routingInvalid":false,"samePlaneInd":false}],"awbNumber":0,"awbPrefix":0,"limitationReasons":null,"outsideOperatingHours":false,"awbVersion":5,"awbStatus":"FOH"}
      // }

    });

  });
    
  describe('Getting route list with performRouteSearch', function() {

    it('sends the correct request object to the web service', function() {

      var $scope = {};
      var manageAWBService = {};
      window.shipmentDetailsScope = { commodityCode1: "0000" };

      var REQUEST_SEARCH_VALUE = undefined;
      var EXPECTED_SEARCH_VALUE = undefined;

      // Setup a an stub to catch the request object beig sent to the web service
      var manageAWBService = jasmine.createSpyObj("manageAWBService",['getRoutes']);
      manageAWBService.getRoutes
      .and.callFake(function (searchValues){ 
        // Save the values for assertions
        REQUEST_SEARCH_VALUE = angular.copy(searchValues);
        return {
          then: function(successCallback){ 
            successCallback({ data: { routes: null } });
          }
        }
      });

      // Setting up a stub for the assignFocustoLots function
      $scope.assignFocusToLots = function(){};

      // INITIALIZE the CONTROLLER for testing with injected dependencies
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService 
      });

      // Setup the state parameters before performRouteSearch call
      $scope.searchValues.findRoutes = { 
        shipDate: "03/09/2016",
        startDepartTime: "17:27",
        origin: { 
          key: "YYZ",
          value: "YYZ" 
        }, 
        destination: { 
          key: "DAL",
          value: "DAL" 
        }
      };

      $scope.getLots = { 
        routedLot: [{ 
          lotNumber: 2,
          isChecked: false,
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents",
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 90, // For LBS
          volume: 20,
          noOfPieces: 3,
          lines: [{
            isChecked: false,
            seqNum: 1,
            lotNumber: 2,
            actualWeight: 90,
            lineActWtLBS: 90,
            volume: 20,
            noOfPieces: 3,
            pieces: [{
              seqNum: 1,
              seqNumber: 1,
              lotNumber: 2,
              displayName: 'Hammer 0',
              isChecked: true,
              actualWeight: 30, // New lot.line.actualWeight will equal NaN if you don't include this
              piecesActWtLbs: 30,
              volume: 10
            },
            {
              seqNum: 1,
              seqNumber: 2,
              lotNumber: 2,
              displayName: 'Hammer 1',
              isChecked: true,
              actualWeight: 30,
              piecesActWtLbs: 30,
              volume: 10
            },
            {
              seqNum: 1,
              seqNumber: 3,
              lotNumber: 2,
              displayName: 'Hammer 2',
              isChecked: false,
              actualWeight: 30,
              piecesActWtLbs: 30,
              volume: 10
            }]
          }]
        }] 
      };

      $scope.initialSearch = {
        "showOnlyFlightsWithAvailableCapacity":false,
        "serviceLevel":"AOG",
        "origin":"DAL",
        "destination":"ATL",
        "shipDateTime":"03/09/2016 1727"
      };
      
      // setup the data model to save the expected values for the request object
      EXPECTED_SEARCH_VALUE = {
        shipDateTime: $scope.searchValues.findRoutes.shipDate + " " + $scope.searchValues.findRoutes.startDepartTime.replace(':',''),
        origin: $scope.searchValues.findRoutes.origin.key,
        destination: $scope.searchValues.findRoutes.destination.key,
        routeFlightLeg: [],
        showOnlyFlightsWithAvailableCapacity: false,
        commodityCode: window.shipmentDetailsScope.commodityCode1,
        setNumber: 1,
        weight: 90,
        volume: 20
      };

      // Execute function
      $scope.performRouteSearch(routingDirectiveConstants.ONE_ROUTE);

      // TEST ASSERTIONS about the REQUEST object
      expect(REQUEST_SEARCH_VALUE["shipDateTime"]).toBe(EXPECTED_SEARCH_VALUE["shipDateTime"]);
      expect(REQUEST_SEARCH_VALUE["origin"]).toBe(EXPECTED_SEARCH_VALUE["origin"]);
      expect(REQUEST_SEARCH_VALUE["destination"]).toBe(EXPECTED_SEARCH_VALUE["destination"]);
      expect(REQUEST_SEARCH_VALUE["routeFlightLeg"].length).toBe(EXPECTED_SEARCH_VALUE["routeFlightLeg"].length);
      expect(REQUEST_SEARCH_VALUE["showOnlyFlightsWithAvailableCapacity"]).toBe(EXPECTED_SEARCH_VALUE["showOnlyFlightsWithAvailableCapacity"]);
      expect(REQUEST_SEARCH_VALUE["commodityCode"]).toBe(EXPECTED_SEARCH_VALUE["commodityCode"]);
      expect(REQUEST_SEARCH_VALUE["setNumber"]).toBe(EXPECTED_SEARCH_VALUE["setNumber"]);
      expect(REQUEST_SEARCH_VALUE["weight"]).toBe(EXPECTED_SEARCH_VALUE["weight"]);
      expect(REQUEST_SEARCH_VALUE["volume"]).toBe(EXPECTED_SEARCH_VALUE["volume"]);

      // Hardcoded version
      // expect(REQUEST_SEARCH_VALUE["shipDateTime"]).toBe("03/09/2016 1727");
      // expect(REQUEST_SEARCH_VALUE["origin"]).toBe("YYZ");
      // expect(REQUEST_SEARCH_VALUE["destination"]).toBe("DAL");
      // expect(REQUEST_SEARCH_VALUE["routeFlightLeg"]).toBe([]);
      // expect(REQUEST_SEARCH_VALUE["showOnlyFlightsWithAvailableCapacity"]).toBe(false);
      // expect(REQUEST_SEARCH_VALUE["commodityCode"]).toBe("0000");
      // expect(REQUEST_SEARCH_VALUE["setNumber"]).toBe(1);
      // expect(REQUEST_SEARCH_VALUE["weight"]).toBe(90);
      // expect(REQUEST_SEARCH_VALUE["volume"]).toBe(20);

    });

  });

});