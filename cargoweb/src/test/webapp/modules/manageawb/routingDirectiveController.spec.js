// Get references to global variables declared in the variable script files
var manageAwbCntrl = manageAwbCntrl;
var shipmentDetailsScope = {}; // shipmentDetailsScope reference is string so it didn't work

// Summary of >20 CCs
// routing_directive.js: line 533, col 50, (23) -- performRouteSearch
// routing_directive.js: line 2359, col 40, (46) -- splitLot
// routing_directive.js: line 3623, col 100, (33 -- manageAWBService.selectRoute (Inside selectRoute)

describe('AWB Routing Directive Controller', function() {
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

  var $controller, $localStorage, routingDirectiveConstants, airwayBillDataService, $rootScope;

  // Inject the controller provider
  beforeEach(inject(function(_$controller_, _$localStorage_, _routingDirectiveConstants_, _airwayBillDataService_, _$rootScope_){

    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
    $localStorage = _$localStorage_;
    routingDirectiveConstants = _routingDirectiveConstants_;
    airwayBillDataService = _airwayBillDataService_;
    $rootScope = _$rootScope_;

    // console.log("airwayBillDataService",airwayBillDataService);

    // console.log("Pre Describe Block: ", airwayBillDataService);
    // Initialize the common utilities controller so that the rootScope functions are available
    var commonUtilities = $controller('commonUtilities', { $scope: $rootScope.$new() });

    // console.log(window.manageAwbCntrl);

    manageAwbCntrl = window.manageAwbCntrl;

  }));

  beforeEach(function(){
    // Mock the userObject in localStorage so that the Controller can start up
    $localStorage.userObject = { authorizationView: { roleList: ["TEST_AUTH"] } };
    $localStorage.awbContent = '';
    // window.localStorage.awbContent = '""';

  });

  describe('Init Controller', function() {

    // Setup the Service stubs
    var manageAWBService = {};
    var ErrorHandlingService; 
    var ngTableParams;  
    var focus = jasmine.createSpy();

    beforeEach(function(){
      // Reset Service Stubs?
    });

    it('Test changeUnitOfMeasurement as LBS', function() {
      // Create variable to store reference to $scope
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      // Test when the units being used is Lbs
      // Setup the $scope variables
      $scope.actualWeightUnit = routingDirectiveConstants.defaultWeightUnit;

      // Run the changeUnitOfMeasurement function to run the calculations
      $scope.changeUnitOfMeasurement();

      // Assert test conditions
      expect($scope.displayWeightLBS).toBe(true);

    });

    it('Test changeUnitOfMeasurement as KG', function() {
      // Create variable to store reference to $scope
      var $scope = {};
      // var $rootScope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      // Test when the units being used is KG
      // Setup the $scope variables
      $scope.actualWeightUnit = routingDirectiveConstants.KGWeight;

      $scope.getLots = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 20,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 10,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };
      
      // Run the changeUnitOfMeasurement function to run the calculations
      $scope.changeUnitOfMeasurement();

      // Assert Test conditions
      expect($scope.displayWeightLBS).toBe(false);
      expect($scope.getLots.routedLot[0].actualWeightKg).toBeCloseTo(30);

    });

    it('Test updateRouteWeight in LBS', function() {
      // Create variable to store reference to $scope
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      // Test when the units being used is LBS
      // Setup the $scope variables
      $scope.displayWeightLBS = true;
      $scope.getLots = angular.copy(getLotsMock);
      
      // Run the function being tested
      $scope.updateRouteWeight();

      // Actual Test Functions
      expect($scope.getLots.routedLot[0].actualWeight).toBeCloseTo(80);

    });

    it('Test updateRouteWeight in KG', function() {
      // return;
      // Create variable to store reference to $scope
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      // Test when the units being used is KG
      // Setup the $scope variables
      $scope.displayWeightLBS = false;
      $scope.getLots = angular.copy(getLotsMock);
      
      // Run the function being tested
      $scope.updateRouteWeight();

      // Actual Test Functions
      expect($scope.getLots.routedLot[0].actualWeightKg).toBeCloseTo(30);

    });

  });

  describe('Routing Directive validateRoutes', function() {

    // Setup the Service stubs
    var manageAWBService = {};
    var ErrorHandlingService; 
    var ngTableParams;  
    var focus = jasmine.createSpy();

    beforeEach(function(){
      // Reset Service Stubs?
    });

    it('Test validateRoutes - call reRoute Option', function() {
      // Create variable to store reference to $scope
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      // Setup the $scope variables
      $scope.isRouteFirstTime = true;
      spyOn($scope, "openModalDialogueReRoute");
      
      // Run the function being tested
      $scope.validateRoutes();

      // Actual Test Functions
      expect($scope.openModalDialogueReRoute).toHaveBeenCalled();

    });

    it('Test validateRoutes - First Pass', function() {
      // Create variable to store reference to $scope
      var $scope = {};
      manageAwbCntrl = {
        headerData: {
          awbNumber: "123456"
        },
        updatedVersionNumber: 1,
        awbContent: {
          versionNumber: undefined
        }
      };

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      $scope.searchValues = {
        findRoutes: {
          shipDate: "2016/03/05",
          startDepartTime: "060606"
        },
        specifyRoutes: [{
          carrier: "SWA",
          fltNbr: "1337",
          DepDate: "2016/03/05",
          origin: "DAL",
          destination: "DFW"
        },
        {
          carrier: "SWA",
          fltNbr: "1337",
          DepDate: "2016/03/05",
          origin: "DAL",
          destination: "DFW"
        },
        {
          carrier: "SWA",
          fltNbr: "1337",
          DepDate: "2016/03/05",
          origin: "DAL",
          destination: "DFW"
        },
        {
          carrier: "SWA",
          fltNbr: "1337",
          DepDate: "2016/03/05",
          origin: "DAL",
          destination: "DFW"
        },
        {
          carrier: "SWA",
          fltNbr: "1337",
          DepDate: "2016/03/05",
          origin: "DAL",
          destination: "DFW"
        }]
      };

      // Setup the $scope variables
      $scope.isRouteFirstTime = false;
      $scope.getLots = angular.copy(getLotsMock);
      spyOn($scope, "openModalDialogueReRoute");
      
      // Run the function being tested
      $scope.validateRoutes();

      // Actual Test Functions
      // Check the Else condition not having been triggered
      expect($scope.openModalDialogueReRoute).not.toHaveBeenCalled();
      // More assertions about end state...

    });

  });

  describe('Routing Directive splitLot function', function() {
    // ALL_PCS_SAME_LOT_SEL_SPLIT_MSG:"All the pieces from one lot can not be selected for splitting",
    // SEL_MIN_ONE_PC_MSG:"Please select at least one piece for splitting the lot",
    // SEL_SAME_LOT_FR_SPLIT_MSG:"Can only select one lot while splitting.",

    // Setup the Service stubs
    var manageAWBService = {};
    var ErrorHandlingService; 
    var ngTableParams;  
    var focus = jasmine.createSpy();

    beforeEach(function(){
      // Reset Service Stubs?
    });

    it('Test splitLot - Negative Scenario', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      $scope.$root.routingData = {};

      var getLotsMock = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      // console.log("Spec:",$rootScope);//, $scope);

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      
      // Run the function being tested
      $scope.splitLot();

      // Actual Test Functions
      // Check the Else condition not having been triggered
      // expect($scope.openModalDialogueReRoute).not.toHaveBeenCalled();
      // More assertions about end state...

    });

    it('Test splitLot - Negative Scenario - SEL_MIN_ONE_PC_MSG --- INVALID AS OF 3/14', function() {
      // SEL_MIN_ONE_PC_MSG:"Please select at least one piece for splitting the lot"
      // Create variable to store reference to $scope
      // var $scope = {};
      // var $rootScope = {};
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();


      /*********** SINCE REFACTORING - THIS CONDITION WILL NEVER BE MET *******/

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;
      // console.log("manageAwbCntrl specfile: ", manageAwbCntrl);

      // Setup the $scope variables
      $scope.pieceCheckBoxes = [{ isChecked: true }];
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = $scope.getLots.routedLot[0].lotNumber;
      
      // Run the function being tested
      $scope.splitLot();

      // Actual Test Functions
      // expect(manageAwbCntrl.displayDivErrorBarRt).toEqual(true);
      // expect(manageAwbCntrl.dispAccHdrErrorRt).toEqual(true);
      // expect(manageAwbCntrl.fieldNameRt).toEqual(routingDirectiveConstants.SEL_MIN_ONE_PC_MSG);
      // expect($rootScope.changeOrNotManageawb).toBe(false);

    });

    it('Test splitLot - Negative Scenario - ALL_PCS_SAME_LOT_SEL_SPLIT_MSG', function() {
      // ALL_PCS_SAME_LOT_SEL_SPLIT_MSG:"All the pieces from one lot can not be selected for splitting"
      // Create variable to store reference to $scope
      // var $scope = {};
      // var $rootScope = {};
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          lotNumber: 2,
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            }]
          }]
        }] 
      };

      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;
      // console.log("manageAwbCntrl specfile: ", manageAwbCntrl);

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = $scope.getLots.routedLot[0].lotNumber;
      $scope.pieceCheckBoxes = [{
        lotNumber: $scope.getLots.routedLot[0].lotNumber,
        isChecked: true
      }];
      
      // Run the function being tested
      $scope.splitLot();

      // console.log("ALL_PCS_SAME_LOT_SEL_SPLIT_MSG");

      // Actual Test Functions
      expect(manageAwbCntrl.displayDivErrorBarRt).toEqual(true);
      expect(manageAwbCntrl.dispAccHdrErrorRt).toEqual(true);
      expect(manageAwbCntrl.fieldNameRt).toEqual(routingDirectiveConstants.ALL_PCS_SAME_LOT_SEL_SPLIT_MSG);
      expect($rootScope.changeOrNotManageawb).toBe(false);

    });

    it('Test splitLot - Negative Scenario - SEL_SAME_LOT_FR_SPLIT_MSG', function() {
      // SEL_SAME_LOT_FR_SPLIT_MSG:"Can only select one lot while splitting."
      // Create variable to store reference to $scope
      // var $scope = {};
      // var $rootScope = {};
      // console.log("STARTING SEL_SAME_LOT_FR_SPLIT_MSG SPEC");
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          lotNumber: 2,
          actualWeight: 20,
          lines: [{
            pieces: [{
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }]
        }] 
      };

      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;
      // console.log("manageAwbCntrl specfile: ", manageAwbCntrl);

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = $scope.getLots.routedLot[0].lotNumber;
      $scope.lotCheckBoxes = [{
        lotNumber: 99,
        isChecked: true,
        noOfPieces: 2,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        currentLotStatus: "FOH",
        contentDesc: "Santa's Presents"
      }];
      $scope.lineCheckBoxes = [{
        isChecked: true,
        noOfPieces: 1,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        lotNumber: 99
      }];
      $scope.pieceCheckBoxes = [
        {
          lotNumber: 99,
          displayName: 'Jarvis',
          isChecked: true
        },
        {
          lotNumber: 199,
          displayName: 'Jarvis2',
          isChecked: true
        }];
      
      // Run the function being tested
      $scope.splitLot();

      // Actual Test Functions
      expect(manageAwbCntrl.displayDivErrorBarRt).toEqual(true);
      expect(manageAwbCntrl.dispAccHdrErrorRt).toEqual(true);
      expect(manageAwbCntrl.fieldNameRt).toEqual(routingDirectiveConstants.SEL_SAME_LOT_FR_SPLIT_MSG);
      expect($rootScope.changeOrNotManageawb).toBe(false);

    });

    it('Test splitLot - Happy Path - LBS', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();

      // spyOn(airwayBillDataService, "checkIfLotsRouted").and.returnValue(false);

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          lotNumber: 2,
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, // For LBS
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };

      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;
      // console.log("manageAwbCntrl specfile: ", manageAwbCntrl);

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = 2;
      $scope.pieceCheckBoxes = [
        { 
          seqNum: 1234,
          seqNumber: 12340,
          lotNumber: $scope.getLots.routedLot[0].lotNumber,
          isChecked: true,
          displayName: "Parts 1",
          actualWeight: 10,
          piecesActWtLbs: 10, // Otherwise it breaks inside updateRouteWeight()
          // piecesActWtKg,
          volume: 5

        },
        {
          seqNum: 2,
          seqNumber: 20,
          lotNumber: 99,
          isChecked: false,
          displayName: "Parts 2",
          actualWeight: 10,
          piecesActWtLbs: 10,
          // piecesActWtKg,
          volume: 5

        }];

      $scope.lotCheckBoxes = [{
        lotNumber: 2,
        isChecked: true,
        rowType: routingDirectiveConstants.ODD_ROW,
        noOfPieces: 2,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        currentLotStatus: "FOH",
        contentDesc: "Santa's Presents"
      }];

      $scope.lineCheckBoxes = [{
        isChecked: true,
        noOfPieces: 1,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        lotNumber: 2
      }];

      // Run the function being tested
      $scope.splitLot();

      // console.log("Scope", $scope);

      // console.log("getLots is unchanged: ", angular.equals($scope.getLots.routedLot[0], getLotsMock.routedLot[0]));
      // console.log("Original ", getLotsMock);
      // console.log("After ", $scope.getLots);

      // Actual Test Functions
      expect($scope.disableResetLink).toBe(false);
      expect($rootScope.changeOrNotManageawb).toBe(false);
      expect($scope.getLots.routedLot.length).toBe(2);

      var newLot = $scope.getLots.routedLot[1];
      expect(newLot.actualWeight).toBe(10);
      expect(newLot.noOfPieces).toBe(1);
      expect(newLot.lines[0].actualWeight).toBe(10);
      expect(newLot.lines[0].lineActWtLBS).toBe(10);
      expect(newLot.lines[0].noOfPieces).toBe(1);
      expect(newLot.lines[0].seqNum).toBe(1234);
      // expect(newLot.lines[0].volume).toBe(String(5));
      expect(newLot.lines[0].volume).toBeCloseTo(5);
      expect(newLot.lines[0].pieces[0].actualWeight).toBe(10);

      // noOfPieces : totalSelectedPiecesCount,
      // actualWeight : Math.ceil(totalSelectedPiecesWeight),
      // volume : Math.ceil(totalSelectedPiecesVolume),

      //THINGS TO INVESTIGATE:
      // removeDuplicateRecords()
      // checkIfLotsRouted()
      // $scope.appendCommodityCode
      // $scope.appendLotContentDesc

    });

    it('Test splitLot - Happy Path - KG', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      $rootScope.routingData = {};
      var $scope = $rootScope.$new();

      // spyOn(airwayBillDataService, "checkIfLotsRouted").and.returnValue(false);

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        $rootScope: $rootScope,
        routingDirectiveConstants: routingDirectiveConstants });

      var getLotsMock = { 
        routedLot: [{ 
          lotNumber: 2,
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, // For LBS
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };

      $scope.displayWeightLBS = false;
      manageAwbCntrl.headerData.awbStatus = routingDirectiveConstants.AWB_STATUS_MAN;
      // console.log("manageAwbCntrl specfile: ", manageAwbCntrl);

      // Setup the $scope variables
      $scope.getLots = angular.copy(getLotsMock);
      $scope.lotNoSelected = 2;
      $scope.pieceCheckBoxes = [
        { 
          seqNum: 1234,
          seqNumber: 12340,
          lotNumber: $scope.getLots.routedLot[0].lotNumber,
          isChecked: true,
          displayName: "Parts 1",
          actualWeight: 10,
          piecesActWtLbs: 10, // Otherwise it breaks inside updateRouteWeight()
          piecesActWtKg: 10,
          volume: 5

        },
        {
          seqNum: 2,
          seqNumber: 20,
          lotNumber: 99,
          isChecked: false,
          displayName: "Parts 2",
          actualWeight: 10,
          piecesActWtLbs: 10,
          // piecesActWtKg,
          volume: 5

        }];

      $scope.lotCheckBoxes = [{
        lotNumber: 2,
        isChecked: true,
        rowType: routingDirectiveConstants.ODD_ROW,
        noOfPieces: 2,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        currentLotStatus: "FOH",
        contentDesc: "Santa's Presents"
      }];

      $scope.lineCheckBoxes = [{
        isChecked: true,
        noOfPieces: 1,
        actualWeight: 20,
        volume: 10,
        seqNum: 1234,
        lotNumber: 2
      }];

      // Run the function being tested
      $scope.splitLot();

      // console.log("Scope", $scope);

      // console.log("getLots is unchanged: ", angular.equals($scope.getLots.routedLot[0], getLotsMock.routedLot[0]));
      // console.log("Original ", getLotsMock);
      // console.log("After ", $scope.getLots.routedLot[1]);

      // Actual Test Functions
      expect($scope.disableResetLink).toBe(false);
      expect($rootScope.changeOrNotManageawb).toBe(false);
      expect($scope.getLots.routedLot.length).toBe(2);

      var newLot = $scope.getLots.routedLot[1];
      expect(newLot.actualWeight).toBe(10);
      expect(newLot.noOfPieces).toBe(1);
      expect(newLot.lines[0].actualWeight).toBe(10);
      expect(newLot.lines[0].lineActWtKG).toBeCloseTo(10);
      expect(newLot.lines[0].noOfPieces).toBe(1);
      expect(newLot.lines[0].seqNum).toBe(1234);
      // expect(newLot.lines[0].volume).toBe(String(5));
      expect(newLot.lines[0].volume).toBeCloseTo(5);
      expect(newLot.lines[0].pieces[0].actualWeight).toBe(10);

    });

  });

  describe('performRouteSearch function Unit Test', function() {

    // Setup the Service stubs
    var manageAWBService = {};
    var ErrorHandlingService; 
    var ngTableParams;  
    var focus = jasmine.createSpy();

    beforeEach(function(){
      // Reset Service Stubs?
    });

    it('performRouteSearch : routeType = undef', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope });

      // console.log("searchValues:", $scope.searchValues);
      $scope.searchValues.findRoutes = { 
        shipDate: "03/09/2016",
        startDepartTime: "17:27",
        origin: undefined, 
        destination: undefined
      };

      expect($scope.performRouteSearch(undefined)).toBe(undefined);
      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(undefined);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(undefined);

    });

    it('performRouteSearch : routeType = ONE_ROUTE - Success (WIP)', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};
      var manageAWBService = {};
      shipmentDetailsScope.commodityCode1 = "0000";
      manageAWBService.getRoutes = function(){ return { then: function(){} }};

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService });

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
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };
      // FROM APP
      //$scope.tempSearchValues = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727","setNumber":1,"specialHandlingCode":"AVI","weight":10,"volume":100,"commodityCode":"0000"}
      $scope.initialSearch = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727"};

        // [{
        //   "seqNum":"LN1",
        //   "noOfPieces":10,
        //   "actualWeight":10,
        //   "volume":100,
        //   "contentDesc":"MEDICAL SUPPLIES",
        //   "lineDest": "",
        //   "lineOrig": "",
        //   "lineSHC": "AVI",
        //   "lineActWtLBS": 10,
        //   "lotNumber": 1,
        //   "noOfPieces": 10,
        //   "uniqueLineNo": 1,

        //   "specialHandlingCode":[
        //     {
        //       "code":"AVI",
        //       "name":"AVI",
        //       "description":"AVI"
        //     }
        //   ],
        //   "pieces":[
        //   {
        //     "seqNumber":1,
        //     "actualWeight":1,
        //     "actualWeightKg":0.5,
        //     "volume":10,
        //     "displayName":"0001",
        //     "seqNum":"LN1",
        //     "piecesActWtLbs":1,
        //     "piecesActWtKg":0.5,
        //     "$$hashKey":"object:3210"
        //   },
        //   {
        //     "seqNumber":2,
        //     "actualWeight":1,
        //     "actualWeightKg":0.5,
        //     "volume":10,
        //     "displayName":"0002",
        //     "seqNum":"LN1",
        //     "piecesActWtLbs":1,
        //     "piecesActWtKg":0.5,
        //     "$$hashKey":"object:3211"
        //   }//,...
        //   ]
        // }];

      // $scope.$root.awbContent.shipmentInfo = {};
      // shipmentDetailsScope.commodityCode1 = "0000";
      console.log("shipmentDetailsScope",shipmentDetailsScope.commodityCode1);

      var UpdatedData = {};
      UpdatedData.data = {routes: []};
      UpdatedData.data.routes[0] = {
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
        routeId: 1,
      }

      // END FROM APP

      spyOn(manageAWBService,"getRoutes")
      .and.returnValue({ 
        then: function(){ 
          // console.log(searchValues); 
          return UpdatedData;
        }
      });

      // console.log("searchValues:", $scope.searchValues.findRoutes);

      // console.log($scope.setNumber, routingDirectiveConstants.ONE_ROUTE);

      expect($scope.performRouteSearch(routingDirectiveConstants.ONE_ROUTE)).toBe(undefined);

    });

    it('performRouteSearch : Server Error', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};
      var manageAWBService = {};
      manageAWBService.getRoutes = function(){ return { then: function(){} }};
      shipmentDetailsScope.commodityCode1 = "0000";

      // console.log(window.manageAwbCntrl);

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService });

      // var manageAwbCntrl = window.manageAwbCntrl;

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
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };
      // FROM APP

      $scope.initialSearch = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727"};


      var UpdatedData = {};
      UpdatedData.data = {routes: []};
      UpdatedData.data.responseMessage = "Server Error";
      UpdatedData.data.routes[0] = {
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
        routeId: 1,
      }

      // END FROM APP

      spyOn(manageAWBService,"getRoutes")
      .and.returnValue({ 
        then: function(successCallback){ 
          // console.log(searchValues); 
          successCallback(UpdatedData);
        }
      });

      if (!$scope.assignFocusToLots) $scope.assignFocusToLots = function(){};
      spyOn($scope, "assignFocusToLots").and.stub();

      expect($scope.performRouteSearch(routingDirectiveConstants.ONE_ROUTE)).toBe(undefined);
      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(true);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(true);
      expect(window.manageAwbCntrl.fieldNameRt).toBe(UpdatedData.data.responseMessage);
      expect($scope.disableGetMoreRoutes).toBe(true);
      expect($scope.assignFocusToLots).toHaveBeenCalled();

    });

    it('performRouteSearch : Search for ONE_ROUTE - and response is routes as null', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};
      var manageAWBService = {};
      manageAWBService.getRoutes = function(){ return { then: function(){} }};
      shipmentDetailsScope.commodityCode1 = "0000";

      // console.log(window.manageAwbCntrl);

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService });

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
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };
      // FROM APP

      $scope.initialSearch = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727"};


      var UpdatedData = {};
      UpdatedData.data = {routes: []};
      UpdatedData.data.routes = null;

      // END FROM APP

      spyOn(manageAWBService,"getRoutes")
      .and.returnValue({ 
        then: function(successCallback){ 
          // console.log(searchValues); 
          successCallback(UpdatedData);
        }
      });

      if (!$scope.assignFocusToLots) $scope.assignFocusToLots = function(){};
      spyOn($scope, "assignFocusToLots").and.stub();

      expect($scope.performRouteSearch(routingDirectiveConstants.ONE_ROUTE)).toBe(undefined);

      expect($scope.response_values.length).toBe(0);
      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(true);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(true);
      expect(window.manageAwbCntrl.fieldNameRt).toBe(routingDirectiveConstants.NO_ROUTES_FOUND_MSG);
      expect($scope.disableGetMoreRoutes).toBe(true);

      expect($scope.assignFocusToLots).toHaveBeenCalled();

    });

    it('performRouteSearch : Search for MORE_ROUTES - and response is routes as null', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};
      var manageAWBService = {};
      manageAWBService.getRoutes = function(){ return { then: function(){} }};
      shipmentDetailsScope.commodityCode1 = "0000";

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService });

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
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };

      $scope.tempSearchValues = '{"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727","setNumber":1,"specialHandlingCode":"AVI","weight":10,"volume":100,"commodityCode":"0000"}';
      $scope.initialSearch = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727"};

      var UpdatedData = {};
      UpdatedData.data = {routes: []};
      UpdatedData.data.routes = null;

      spyOn(manageAWBService,"getRoutes")
      .and.returnValue({ 
        then: function(successCallback){ 
          // console.log(searchValues); 
          successCallback(UpdatedData);
        }
      });

      if (!$scope.assignFocusToLots) $scope.assignFocusToLots = function(){};
      spyOn($scope, "assignFocusToLots").and.stub();

      expect($scope.performRouteSearch(routingDirectiveConstants.MORE_ROUTES)).toBe(undefined);

      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(true);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(true);
      expect(window.manageAwbCntrl.fieldNameRt).toBe(routingDirectiveConstants.NO_MORE_ROUTES_FOUND);

      expect($scope.assignFocusToLots).toHaveBeenCalled();

    });

    it('performRouteSearch : Search for MORE_ROUTES - and response is routes as null', function() {
      // Nothing happens in this case
      // Create variable to store reference to $scope
      var $scope = {};
      var manageAWBService = {};
      manageAWBService.getRoutes = function(){ return { then: function(){} }};
      shipmentDetailsScope.commodityCode1 = "0000";

      // Replace the Controller dependencies with stub equivalents
      var controller = $controller('RoutingDirectiveCtrl', { 
        $scope: $scope,
        manageAWBService: manageAWBService });

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
          rowType: routingDirectiveConstants.ODD_ROW,
          actualWeight: 80, 
          volume: 20,
          noOfPieces: 2,
          lines: [{
            seqNum: 1234,
            lotNumber: 2,
            actualWeight: 20,
            lineActWtLBS: 80,
            volume: 20,
            noOfPieces: 2,
            pieces: [{
              seqNumber: 1,
              piecesActWtLbs: 50,
              piecesActWtKg: 20,
              volume: 10
            },
            {
              seqNumber: 2,
              piecesActWtLbs: 30,
              piecesActWtKg: 10,
              volume: 10
            }]
          }],
          currentLotStatus: "FOH",
          contentDesc: "Santa's Presents"

        }] 
      };

      $scope.tempSearchValues = '{"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727","setNumber":1,"specialHandlingCode":"AVI","weight":10,"volume":100,"commodityCode":"0000"}';
      $scope.initialSearch = {"showOnlyFlightsWithAvailableCapacity":false,"serviceLevel":"AOG","origin":"DAL","destination":"ATL","shipDateTime":"03/09/2016 1727"};

      var UpdatedData = {};
      UpdatedData.data = {routes: []};
      UpdatedData.data.routes = null;

      spyOn(manageAWBService,"getRoutes")
      .and.returnValue({ 
        then: function(successCallback){ 
          // console.log(searchValues); 
          successCallback(UpdatedData);
        }
      });

      if (!$scope.assignFocusToLots) $scope.assignFocusToLots = function(){};
      spyOn($scope, "assignFocusToLots").and.stub();

      expect($scope.performRouteSearch(routingDirectiveConstants.MORE_ROUTES)).toBe(undefined);

      expect(window.manageAwbCntrl.displayDivErrorBarRt).toBe(true);
      expect(window.manageAwbCntrl.dispAccHdrErrorRt).toBe(true);
      expect(window.manageAwbCntrl.fieldNameRt).toBe(routingDirectiveConstants.NO_MORE_ROUTES_FOUND);

      expect($scope.assignFocusToLots).toHaveBeenCalled();

    });

// Error in data.responseMessage
// Error no routes found

  }); 

});