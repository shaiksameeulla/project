/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/**
 * @CodeReviewFix:03-2016
 * This file was modified to address code review comments for Build 2. Above tab is added to the modified sections of the code.
 */
/*
 * @TODO Build 3: In build 3 we need to revisit and remove this global variables.
 * Proper scope needs to be defined in the Controller scope and this directive should inherit.
 */
var routingScope = '',
globalErrorHandlingServices = '';
/**
 * This directive captures the scope and operations for handling the routing
 * section of the AWB
 *
 * @constructor
 * @param{$scope} scope
 * @param{$rootScope} rootScope
 * @param{ErrorHandlingService} ErrorHandlingService
 * @param{airwayBillDataService}airwayBillDataService
 * @param{ngTableParams} ngTableParams
 * @param{$modal} $modal
 * @param{$localStorage} $localStorage
 * @param{manageAWBService} manageAWBService
 * @param{focus} focus
 * @param{$timeout} $timeout
 * @param{routingDirectiveConstants} routingDirectiveConstants
 * @param{ERRORMESSAGES} ERRORMESSAGES
 * @param{CONFIGURATIONS} CONFIGURATIONS
 */
app.directive('awbRouting', function () {
	return {
		restrict : 'A',
		templateUrl : '../modules/manageawb/RoutingTemplate.html',
		scope : true,
		controller : ['$scope', '$rootScope', 'ErrorHandlingService', 'airwayBillDataService', 'ngTableParams', '$modal', '$localStorage', 'manageAWBService', 'focus', '$timeout', 'routingDirectiveConstants', 'ERRORMESSAGES', 'CONFIGURATIONS', 'RoutingLot', 'GetRouteRequest', 'ValidateRouteRequest', function ($scope, $rootScope, ErrorHandlingService, airwayBillDataService, ngTableParams, $modal, $localStorage, manageAWBService, focus, $timeout, routingDirectiveConstants, ERRORMESSAGES, CONFIGURATIONS, RoutingLot, GetRouteRequest, ValidateRouteRequest) {
				//Initialization of controller variables
				globalErrorHandlingServices = ErrorHandlingService;
				routingScope = $scope;
				$scope.actualWeightUnit = routingDirectiveConstants.defaultWeightUnit;
				$scope.dispAccHdrErrorRt = false;
				$scope.dispErrorRouting = 1;
				$scope.fieldNameRt = '';
				$scope.getLots = {};
				$scope.getLotsForSearch = {};
				$scope.getLotsForReset = {};
				$scope.respLength = 0;
				$scope.setNumber = 1;
				$scope.routeLot = {};
				$scope.lotCheckBoxes = [];
				$scope.lineCheckBoxes = [];
				$scope.pieceCheckBoxes = [];
				$scope.isLotClosed = true;
				$scope.lotNoSelected = "";
				$scope.rtIdInc = 1;
				$scope.lineNumberCounter = 0;
				$scope.configConstants = CONFIGURATIONS;
				$scope.searchValues = {};
				$scope.isRoutingOpened = false;
				$scope.errorLabel = ERRORMESSAGES;
				$scope.currentSelectedLot = 0;
				$scope.searchValues.findRoutes = {};
				$scope.searchValues.specifyRoutes = [{
						carrier : "",
						fltNbr : "",
						DepDate : "",
						origin : "",
						destination : ""
					}, {
						carrier : "",
						fltNbr : "",
						DepDate : "",
						origin : "",
						destination : ""
					}, {
						carrier : "",
						fltNbr : "",
						DepDate : "",
						origin : "",
						destination : ""
					}, {
						carrier : "",
						fltNbr : "",
						DepDate : "",
						origin : "",
						destination : ""
					}, {
						carrier : "",
						fltNbr : "",
						DepDate : "",
						origin : "",
						destination : ""
					}
				];
				$scope.enableButtons = true;
				$scope.enableButtonsSplit = true;
				$scope.lotsThatAreRouted = "";
				$scope.enteredRouteNumber = "";
				$scope.routeNumbersToBeValidated = [];
				$scope.areLotsRouted = false;
				$scope.selectedLotNbr = 0;
				$scope.isRouteFirstTime = false;
				$scope.disableResetLink = true;
				$scope.setNumber = 0;
				$scope.doReRouting = false;
				$scope.reRoutingReason = "";
				$scope.displayWeightLBS = true;
				$scope.disableValidateRoute = true;
				$scope.checkMoreRoutesCount = [];
				$scope.initialDivError = "";
				$scope.initialAccError = "";
				$scope.initialDivErrorDd = "";
				$scope.initialAccErrorDd = "";
				$scope.disableGetMoreRoutes = true;
				$scope.enableRouteButton = true;
				$scope.userStation = "";
				$scope.initialUserLotStatus = [];
				$scope.isCallFromReRouteButton = false;
				$scope.isCaseOfSplittingROuted = false;
				$scope.listOfRoutes = [];
				$scope.onlyForSplitRoutedLot = false;
				$scope.shouldOpenSection7 = true;
				$scope.initialInit = true;
				$scope.specifyRoute = false;
				$scope.showAllFlight = false;
				$scope.isReRouteState = false;
				$scope.specifyRoutesCount = {
					specRoute : [{
							id : 0
						}, {
							id : 1
						}, {
							id : 2
						}, {
							id : 3
						}, {
							id : 4
						}
					]
				};
				$scope.searchRoutePanel = true;
				$scope.routesSummary = true;
				$scope.lineTextBox = true;
				$scope.pieceTextBox = true;
				$scope.lineRadio = false;
				$scope.pieceRadio = false;
				$scope.headerRadio = false;
				$scope.hdrRouteNo = true;
				$scope.firstHeaderRadio = false;
				/**
				 * Calculate and update the route weights after iterating
				 * the lots and lines and pieces. Total line and lot weight and
				 * volume are calculated.
				 *
				 * @param{}
				 *
				 */
				$scope.updateRouteWeight = function () {
					//temporary function variables for storing weights and volumes
					var lineTotalWeight = 0,
					pieceWeight = 0,
					currentLineWeight = 0,
					currentLineVolume = 0,
					lineTotalVolume = 0;
					angular.forEach($scope.getLots.routedLot, function (lotItem) {
						lineTotalWeight = 0;
						currentLineWeight = 0;
						lineTotalVolume = 0;
						currentLineVolume = 0;
						//sum up line level wt/vol to get lot level totals
						angular.forEach(lotItem.lines, function (lineItem) {
							currentLineWeight = 0;
							currentLineVolume = 0;
							//sum up piece level wt/vol to get line level totals
							angular.forEach(lineItem.pieces, function (pieceItem) {
								pieceWeight = 0;
								if ($scope.displayWeightLBS) {
									pieceWeight = pieceItem.piecesActWtLbs;
								} else {
									pieceWeight = pieceItem.piecesActWtKg;
								}
								lineTotalWeight = lineTotalWeight + parseFloat(pieceWeight);
								currentLineWeight = currentLineWeight + parseFloat(pieceWeight);
								currentLineVolume = currentLineVolume + Number(pieceItem.volume);
								lineTotalVolume = lineTotalVolume + Number(pieceItem.volume);
							});
							//format decimal places based on LBS (no decimals) ot KGS (1 decimal place)
							if ($scope.displayWeightLBS) {
								lineItem.lineActWtLBS = parseInt(currentLineWeight);
							} else {
								lineItem.lineActWtKG = currentLineWeight.toFixed(1).replace('.0', '');
							}
							currentLineVolume = currentLineVolume.toFixed(1).replace('.0', '');
							lineItem.volume = currentLineVolume;
						});
						//format decimal places based on LBS (no decimals) to KGS (1 decimal place)
						if ($scope.displayWeightLBS) {
							lotItem.actualWeight = parseInt(lineTotalWeight);
						} else {
							lotItem.actualWeightKg = lineTotalWeight.toFixed(1).replace('.0', '');
						}
						lineTotalVolume = lineTotalVolume.toFixed(1).replace('.0', '');
						//update lot with the total volume
						lotItem.volume = lineTotalVolume;
					});
				};
				/**
				 * This function checks the weight unit selected by user
				 * and converts the actual weights accordingly for display selected.
				 *
				 * @param{}
				 *
				 */
				$scope.changeUnitOfMeasurement = function () {
					/*@CodeReviewFix:03-2016 :Start : Moved hard coded	strings to constants */
					if ($scope.actualWeightUnit == routingDirectiveConstants.defaultWeightUnit) {
						$scope.displayWeightLBS = true;
					}
					if ($scope.actualWeightUnit == routingDirectiveConstants.KGWeight) {
						/* @CodeReviewFix:03-2016  : End   */
						$scope.displayWeightLBS = false;
						angular.forEach($scope.getLots.routedLot, function (routeItem) {
							routeItem.actualWeightKg = $rootScope.lbsToKgConversion(routeItem.actualWeight);
						});
					}
					$scope.updateRouteWeight();
				};
				/**
				 * Clearing all the route attributes and setting the default values.
				 * This is done for specify route scenario.
				 *
				 * @param{}
				 *
				 */
				$scope.clear = function () {
					/*Re-Setting all Specify Routes Values to blank Values
					This is done for multiple Specify Route Rows
					 */
					$scope.searchValues.specifyRoutes = [];
					$scope.searchValues.specifyRoutes[0] = {};
					$scope.searchValues.specifyRoutes[1] = {};
					$scope.searchValues.specifyRoutes[2] = {};
					$scope.searchValues.specifyRoutes[3] = {};
					$scope.searchValues.specifyRoutes[4] = {};
					/*Re-Setting all Specify Routes Values to default Values
					This is done for multiple Specify Route Rows
					 */
					/* @CodeReviewFix:03-2016: Start : Moved hard coded	strings to constants */
					$scope.searchValues.specifyRoutes[0].carrier = routingDirectiveConstants.DEFAULT_CARRIER_CODE;
					$scope.searchValues.specifyRoutes[1].carrier = routingDirectiveConstants.DEFAULT_CARRIER_CODE;
					$scope.searchValues.specifyRoutes[2].carrier = routingDirectiveConstants.DEFAULT_CARRIER_CODE;
					$scope.searchValues.specifyRoutes[3].carrier = routingDirectiveConstants.DEFAULT_CARRIER_CODE;
					$scope.searchValues.specifyRoutes[4].carrier = routingDirectiveConstants.DEFAULT_CARRIER_CODE;
					/* @CodeReviewFix:03-2016  : End   */
					$scope.enableValidateRoute = true;
				};
				/**
				 * When the expand all clicked, assign the focus to get routes button
				 *
				 * @param{}
				 *
				 */
				$scope.assignFocusToGetRoutes = function () {
					if (manageAwbCntrl.expandAllClicked === true) {
						manageAwbCntrl.assignFocusToElements("btnGetRoutes");
					}
				};
				/**
				 * In this method, flight number is
				 * validated to check if all are numeric or
				 * not. if there are any alpha characters,
				 * we are setting the flags to true to
				 * display error message on accordion header
				 *
				 * @param {}
				 *
				 */
				$scope.showFltNbrErrorOnTop = function (elementData) {
					if ($scope.initialDivErrorDd === "") {
						$scope.initialDivErrorDd = angular.copy(manageAwbCntrl.displayDivErrorBarRt);
					}
					if ($scope.initialAccErrorDd === "") {
						$scope.initialAccErrorDd = angular.copy(manageAwbCntrl.dispAccHdrErrorRt);
					}
					/* @CodeReviewFix:03-2016 : Start :Removed the jQuery selector code and angular.element
					to read the dynamic elements. Added and moved the hard coded string to constants */
					/*Dynamic elements are created so to access their values using angular selector*/
					if (isNaN(parseInt(elementData))) {
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.NUMBERS_ONLY;
					} else {
						manageAwbCntrl.displayDivErrorBarRt = $scope.initialAccErrorDd;
						manageAwbCntrl.dispAccHdrErrorRt = $scope.initialAccErrorDd;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.BLANK;
					}
					/* @CodeReviewFix:03-2016  : End   */
				};
				/**
				 * Check the strict format for the data, if it matches return TRUE
				 * else return false.
				 *
				 * @param{Date} date value is sent for type check
				 * @Returns {boolean}
				 *
				 */
				$scope.checkValidDateFormat = function (dateParam) {
					return moment(dateParam, routingDirectiveConstants.MMDDYY, true).isValid();
				};
				/**
				 * Validates origin and destination fields of specify routes section
				 * and make section red / green based on entered value
				 *
				 * @param{string} value entered/selected in the UI select
				 *
				 */
				$scope.showOrignAndDestError = function (selectedValue) {
					/* @CodeReviewFix:03-2016 : Start : String hard codings are moved to constants.	*/
					if ($scope.initialDivErrorDd === "") {
						$scope.initialDivErrorDd = angular.copy(manageAwbCntrl.displayDivErrorBarRt);
					}
					if ($scope.initialAccErrorDd === "") {
						$scope.initialAccErrorDd = angular.copy(manageAwbCntrl.dispAccHdrErrorRt);
					}
					if (!selectedValue || (selectedValue && !selectedValue.value.trim())) {
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.INVALID_AIRPORT_CODE_MSG;
					} else {
						manageAwbCntrl.displayDivErrorBarRt = $scope.initialDivErrorDd;
						manageAwbCntrl.dispAccHdrErrorRt = $scope.initialAccErrorDd;
						manageAwbCntrl.fieldNameRt = "";
					}
					/* @CodeReviewFix:03-2016  : End   */
				};
				/**
				 * Validates departure date fields of specify routes section.
				 * If the invalid date format of invalid date range is entered error
				 * messages are set to show on screen.
				 *
				 *  @param{Date} - toDate
				 *  @param{Date} - fromDate
				 *
				 */
				$scope.showDepartDateErrorOnTop = function (enteredValue, prevDate) {
					if ($scope.initialDivErrorDd === "") {
						$scope.initialDivErrorDd = angular.copy(manageAwbCntrl.displayDivErrorBarRt);
					}
					if ($scope.initialAccErrorDd === "") {
						$scope.initialAccErrorDd = angular.copy(manageAwbCntrl.dispAccHdrErrorRt);
					}
					/**Check the entered value is valid date format or else show error message and make section Red**/
					if (!$scope.checkValidDateFormat(enteredValue)) {
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.INVALID_DATE_MSG; // @CodeReviewFix:03-2016 String hard codings are moved to constants.
					} else {
						manageAwbCntrl.displayDivErrorBarRt = $scope.initialDivErrorDd;
						manageAwbCntrl.dispAccHdrErrorRt = $scope.initialDivErrorDd;
						manageAwbCntrl.fieldNameRt = "";
						/*Dynamic elements are created so to access their values using angular selector*/
						if (enteredValue < prevDate) {
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = routingDirectiveConstants.INVALID_FROM_TO_DATE; // @CodeReviewFix:03-2016 String hard codings are moved to constants.
						}
					}
				};

				/**
				 * Function to check if atleast ont lots is routed.
				 */
				$scope.checkIfAtleatOneLotRouted = function () {
					var flag = false;
					angular.forEach($rootScope.routingData.lots, function (routeItem) {
						if (routeItem.flightLegs && routeItem.flightLegs.length > 0) {
							flag = true;
						}
					});
					return flag;
				};

				/**
				 * Function to check if all lots routed or not.
				 */
				$scope.checkIfAllLotsRouted = function () {
					var flag = false;
					angular.forEach($scope.getLots.routedLot, function (routeItem) {
						if (!routeItem.flightLegs || (routeItem.flightLegs && routeItem.flightLegs.length === 0)) {
							flag = true;
						}
					});
					return flag;
				};
				/**
				 * Function called on click of Clear and it clears the orgin, destination
				 * shipdate, time values in section 5
				 *
				 */
				$scope.clr = function () {
					/* @CodeReviewFix:03-2016 : Start : DOM manipulation is removed and
					scope variables are used to modify html */
					$scope.searchValues.findRoutes.origin = "";
					$scope.searchValues.findRoutes.destination = "";
					$scope.searchValues.findRoutes.shipDate = "";
					$scope.searchValues.findRoutes.startDepartTime = "";
					$scope.searchValues.excludedCities = "";
					$scope.preferredFlights = "";
					//other approaches were tried to handle the check box show all flights button
					//hence using DOM manipulation
					angular.element('#chkflt').prop('checked', false);
					/* @CodeReviewFix:03-2016  : End   */
				};
				/**
				 * This method performs the getRoutes() service.
				 * copies all the search route criteria and invokes
				 *
				 * @param {}
				 *
				 */
				$scope.performRouteSearch = function (routeType) {
					$("#divWithRoutesAndLoader").attr("style", "max-height: 210px;display: block;");
					/**
					 * TODO : Population of the getRoutes request (tempSearchValues) should be done using
					 * a service model class instead of doing individual field level get/set
					 *
					 */
					if ($scope.searchValues.findRoutes.origin && $scope.searchValues.findRoutes.destination && $scope.searchValues.findRoutes.origin.key && $scope.searchValues.findRoutes.destination.key) {
						if (routeType !== routingDirectiveConstants.MORE_ROUTES) { // @CodeReviewFix:03-2016 String hard codings are moved to constants.
							$scope.setNumber = 1;
						} else {
							$scope.setNumber = $scope.setNumber + 1;
						}
						if (routeType === routingDirectiveConstants.ONE_ROUTE) { // @CodeReviewFix:03-2016 String hard codings are moved to constants.
							$scope.tempSearchValues = angular.copy($scope.searchValues.findRoutes);
							var departTime = '';
							if ($scope.searchValues.findRoutes.startDepartTime !== undefined) {
								departTime = $scope.searchValues.findRoutes.startDepartTime.toString().replace(/:/g, "");
							}
							$scope.tempSearchValues.shipDateTime = $scope.searchValues.findRoutes.shipDate + " " + departTime;
							$scope.tempSearchValues.serviceLevel = manageAwbCntrl.headerData.serviceLevel;
						} else if (routeType === routingDirectiveConstants.MORE_ROUTES) { // @CodeReviewFix:03-2016 String hard codings are moved to constants.
							$scope.tempSearchValues = $scope.tempSearchValues;
						} else {
							//no number of routes no specified, populate origin and destination
							// to search values method
							$scope.searchValues.findRoutes.origin = {
								key : manageAwbCntrl.headerData.origin,
								value : manageAwbCntrl.headerData.origin
							};
							$scope.searchValues.findRoutes.destination = {
								key : manageAwbCntrl.headerData.destination,
								value : manageAwbCntrl.headerData.destination
							};
							$scope.tempSearchValues = angular.copy($scope.initialSearch);
						}
						$scope.tempSearchValues.setNumber = $scope.setNumber;
						if ($scope.searchValues.excludedCities !== undefined) {
							var tmpExcludedCities = "";
							angular.forEach($scope.searchValues.excludedCities, function (data) {
								tmpExcludedCities = tmpExcludedCities + data.value + ',';
							});
							tmpExcludedCities = tmpExcludedCities.substring(0, tmpExcludedCities.length - 1);
							$scope.tempSearchValues.excludedCities = tmpExcludedCities;
						}
						$scope.tempSearchValues.specialHandlingCode = $scope.getLots.routedLot[0].lines[0].lineSHC;
						if (!$scope.tempSearchValues.showOnlyFlightsWithAvailableCapacity) {
							if ($scope.selectedLotNbr) {
								var capWt = 0;
								var capVol = 0;
								var capFlightLegs = [];
								var item = 0;
								for (item = 0; item < $scope.getLots.routedLot.length; item++) {
									//identify the lot number which was selected, identify the weight and volume for routing
									if ($scope.getLots.routedLot[item].lotNumber === $scope.selectedLotNbr) {
										capWt = capWt + parseInt($scope.getLots.routedLot[item].actualWeight);
										capVol = capVol + parseInt($scope.getLots.routedLot[item].volume);
										capFlightLegs = angular.copy($scope.getLots.routedLot[item].flightLegs);
										break;
									}
								}
								$scope.tempSearchValues.weight = capWt;
								$scope.tempSearchValues.volume = capVol;
								$scope.tempSearchValues.routeFlightLeg = capFlightLegs;
							} else {
								var capWt = 0;
								capVol = 0;
								angular.forEach($scope.getLots.routedLot, function (lotItem) {
									capWt = capWt + parseInt(lotItem.actualWeight);
									capVol = capVol + parseInt(lotItem.volume);
								});
								$scope.tempSearchValues.weight = capWt;
								$scope.tempSearchValues.volume = capVol;
							}
						} else {
							var capWt = 0;
							capVol = 0;
							angular.forEach($scope.getLots.routedLot, function (lotItem) {
								capWt = capWt + parseInt(lotItem.actualWeight);
								capVol = capVol + parseInt(lotItem.volume);
							});
							$scope.tempSearchValues.weight = capWt;
							$scope.tempSearchValues.volume = capVol;
						}
						//populate ship datetime if valid, else show error
						if ($scope.tempSearchValues.shipDateTime !== undefined &&
							($scope.tempSearchValues.shipDateTime.trim() === "" || $scope.searchValues.findRoutes.shipDate.trim() === "") &&
							routeType !== "initial") {
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = routingDirectiveConstants.INVALID_SHIP_DATE_TIME;
						} else {
							//reset the header values if there are no errors
							if (manageAwbCntrl.displayDivErrorBarRt === true && manageAwbCntrl.dispAccHdrErrorRt === true && manageAwbCntrl.fieldNameRt === routingDirectiveConstants.INVALID_SHIP_DATE_TIME) {
								manageAwbCntrl.displayDivErrorBarRt = undefined;
								manageAwbCntrl.dispAccHdrErrorRt = undefined;
								manageAwbCntrl.fieldNameRt = "";
							}
							if (shipmentDetailsScope.commodityCode1 !== undefined) {
								$scope.tempSearchValues.commodityCode = shipmentDetailsScope.commodityCode1;
							} else {
								$scope.tempSearchValues.commodityCode = $rootScope.awbContent.shipmentInfo.shipmentlines[0].commodityCode.code;
							}
							//other approaches were tried to handle the check box show all flights button
							//hence using DOM manipulation
							$scope.tempSearchValues.showOnlyFlightsWithAvailableCapacity = $scope.getShowAllFlightFlag();
							//$scope.tempSearchValues = JSON.stringify($scope.tempSearchValues);
							var tempRequestObj = {};
							tempRequestObj = new GetRouteRequest($scope.tempSearchValues);
							$("#routeResultsDisp").attr("style", "display:none;");
							//@TODO Build 3: The toggle button should capture the state. Based on the agreed custom components and common design for AngularJS
							//this code needs to be fixed to not be set/read the state of the toggle button from the scope variable
							// if (routeType === "initial" && $("#routingBody").attr("style") === "display: none;" && manageAwbCntrl.toggleRoutingSection === true) {
							// $("#routingBody").attr("style", "display:block;");
							// }
							$rootScope.$broadcast('displayLoadingBar');
							//invoke the manageAWBService getRoutes method
							manageAWBService.getRoutes(tempRequestObj).then(function (UpdatedData) {
								//Handle response for getRoutes service
								$rootScope.$broadcast('hideLoadingBar');
								manageAwbCntrl.fieldNameRt = '';
								$scope.displayMessage = '';
								// if (routeType === "initial" && $("#routingBody").attr("style") === "display: none;" && manageAwbCntrl.toggleRoutingSection === true) {
								// $("#routingBody").attr("style", "display:block;");
								// }
								$("#routeResultsDisp").attr("style", "display:table;");
								$scope.tempRespValues = UpdatedData.data.routes;
								if (UpdatedData.data.responseMessage !== null && UpdatedData.data.responseMessage !== "" && UpdatedData.data.responseMessage !== undefined) {
									manageAwbCntrl.displayDivErrorBarRt = true;
									manageAwbCntrl.dispAccHdrErrorRt = true;
									manageAwbCntrl.fieldNameRt = UpdatedData.data.responseMessage;
									$scope.disableGetMoreRoutes = true;
								} else if ($scope.tempRespValues === null && (routeType === routingDirectiveConstants.ONE_ROUTE || routeType === routingDirectiveConstants.INITIAL_ROUTE)) { // @CodeReviewFix:03-2016 String hard codings are moved to constants.
									$scope.response_values = [];
									manageAwbCntrl.displayDivErrorBarRt = true;
									manageAwbCntrl.dispAccHdrErrorRt = true;
									manageAwbCntrl.fieldNameRt = routingDirectiveConstants.NO_ROUTES_FOUND_MSG;
									$scope.disableGetMoreRoutes = true;
								} else if ($scope.tempRespValues === null && routeType === routingDirectiveConstants.MORE_ROUTES) { // @CodeReviewFix:03-2016 String hard codings are moved to constants.
									manageAwbCntrl.displayDivErrorBarRt = true;
									manageAwbCntrl.dispAccHdrErrorRt = true;
									manageAwbCntrl.fieldNameRt = routingDirectiveConstants.NO_MORE_ROUTES_FOUND; // @CodeReviewFix:03-2016 String hard codings are moved to constants.
								} else {
									$scope.disableGetMoreRoutes = false;
									$scope.response_values = [];
									if (manageAwbCntrl.displayDivErrorBarRt === true && manageAwbCntrl.dispAccHdrErrorRt === true && routeType !== routingDirectiveConstants.INITIAL_ROUTE && (manageAwbCntrl.headerData.Fullstatus === routingDirectiveConstants.AWB_STATUS_FOH_EXPAND || manageAwbCntrl.headerData.Fullstatus === routingDirectiveConstants.AWB_STATUS_DRAFT)) {
										manageAwbCntrl.displayDivErrorBarRt = undefined;
										manageAwbCntrl.dispAccHdrErrorRt = undefined;
										manageAwbCntrl.fieldNameRt = "";
									}
									$scope.checkMoreRoutesCount = angular.copy($scope.tempRespValues);
									var cntr = 0;
									for (cntr = 0; cntr < $scope.tempRespValues.length; cntr++) {
										if ($scope.tempRespValues[cntr]) {
											$scope.response_values.push($scope.tempRespValues[cntr]);
										} else {
											break;
										}
									}
									$scope.calculateLegDetails();
									$scope.respLength = $scope.response_values.length;
									if ($scope.respLength === 0) {
										$scope.displayMessage = routingDirectiveConstants.NO_REC_FOUND_FOR_SEARCH_CRIT; // @CodeReviewFix:03-2016 String hard codings are moved to constants.
										$scope.openModalDialogueInfo();
									} else {
										if ($scope.tableParams === undefined && $scope.respLength > 0) {
											$scope.tableParams = new ngTableParams({
													page : 1, // show first page
													count : $scope.response_values.length + 100 // count per page
												}, {
													counts : [],
													total : $scope.response_values.length,
													getData : function ($defer, params) {
														$defer.resolve($scope.response_values.slice((params.page() - 1) * params.count(), params.page() * params.count()));
													}
												});
											$scope.tableParams.settings().$scope = $scope;
										} else {
											$scope.tableParams.reload();
										}
										$scope.rtIdInc = 1;
										if (!angular.isObject($scope.response_values)) {
											$scope.response_values = [];
										}
									}
								}
								$scope.assignFocusToLots();
							});
						}
					} else {
						manageAwbCntrl.displayDivErrorBarRt = undefined;
						manageAwbCntrl.dispAccHdrErrorRt = undefined;
						manageAwbCntrl.fieldNameRt = "";
					}
				};
				/**
				 * Setting focus to origin once the routing section is loaded
				 *
				 * @param{String} - elementId
				 *
				 */
				$scope.setFocusToOrigin = function (elementId) {
					/**timeout is used to set focus once the rendering of html is completed**/
					$timeout(function () {
						var uiSelect = angular.element(document.querySelector('#' + elementId)).controller('uiSelect');
						uiSelect.focusser[0].focus();
					}, 100);
				};
				/**
				 *  Update Routing Header Color Based on Routed and un-routed Lot
				 *
				 *  @param{}
				 */
				$scope.updateRoutingHeader = function () {
					var isRouteSectionValid = true,
					routeLotLength = 0,
					containInvalidLot = false,
					unroutedLot = 0;
					if (!manageAwbCntrl.fieldNameRt) {
						if (routingScope.getLots !== undefined && routingScope.getLots.routedLot && routingScope.getLots.routedLot.length > 0) {
							angular.forEach(routingScope.getLots.routedLot, function (routeItem) {
								if (containInvalidLot === false && routeItem.routingInvalid) {
									containInvalidLot = true; //Contains lots that have routingInvalid set to true
								}
								//identify the unrouted lots to set the flags
								if (!routeItem.flightLegs || (routeItem.flightLegs && routeItem.flightLegs.length === 0)) {
									if (isRouteSectionValid === true) {
										isRouteSectionValid = false; //Lot is unrouted
									}
									unroutedLot = unroutedLot + 1;
								} else {
									routeLotLength = routeLotLength + 1;
								}
							});
							/* If We Have One un-routed Lot then Make Section 5 Red And Else Green*/
							if (isRouteSectionValid === false) {
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
								if (unroutedLot === 1 && routeLotLength === 0 && $scope.isRoutingOpened !== true) {
									manageAwbCntrl.displayDivErrorBarRt = undefined;
									manageAwbCntrl.dispAccHdrErrorRt = undefined;
								}
							} else {
								//All lots are routed
								manageAwbCntrl.displayDivErrorBarRt = false;
								manageAwbCntrl.dispAccHdrErrorRt = false;
							}
							/* One Routed Lot And Section 3 is red Then section 5 Should also be Red */
							if (routeLotLength > 0 && manageAwbCntrl.displayDivErrorBarSd) {
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
							}
						} else {
							//There are no lots
							manageAwbCntrl.displayDivErrorBarRt = undefined;
							manageAwbCntrl.dispAccHdrErrorRt = undefined;
						}
                        //Unit testing fixes for routing header should not be red if we have only one lot unr-routed
						if (containInvalidLot && routeLotLength > 0) {
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
						}
					}
				};
				/**
				 * This method updates the local scope variables from the $rootScope.routingData
				 * (which contains data from latest service response)
				 *
				 * @param {int} - number
				 *
				 */
				$scope.getAwbDetailsFromService = function (awbNumber) {
					if ($rootScope.routingData !== undefined && $rootScope.routingData !== null) {
						$scope.awbContentFromService = angular.copy($rootScope.routingData.lots);
						$scope.initialUserLotStatus = [];
						$scope.userStationStatus = [];
						if ($scope.checkIfLotsExist()) {
							//identifying the station from where the screening done initially by looking at the routed lots.
							/* @CodeReviewFix:03-2016 : Start : updated to angular.forEach function and removed all the DOM manipulations */
							angular.forEach($scope.awbContentFromService, function (contentLot, contentLotIndex) {
								var lotContentDesc = $scope.getLots.routedLot[0].lines[0].contentDesc;
								angular.forEach($scope.getLots.routedLot, function (routeItem) {
									if (contentLot.lotNumber === routeItem.lotNumber) {
										routeItem.currentLotStatus = angular.copy(contentLot.currentLotStatus);
										routeItem.lotStatusStation = angular.copy(contentLot.lotStatusStation);
										routeItem.contentDesc = lotContentDesc;
										if (contentLot.lotStatusStation) {
											$scope.userStation = contentLot.lotStatusStation.split(" - ")[1];
											$scope.userStationStatus.push(contentLot.lotStatusStation.split(" - ")[1]);
											$scope.initialUserLotStatus.push(contentLot.lotStatusStation.split(" - ")[0]);
										}
										routeItem.samePlaneInd = contentLot.hasOwnProperty("samePlaneInd") ? contentLot.samePlaneInd : false;
									}
								});
								/* @CodeReviewFix:03-2016  : End   */
							});
							/** To update the route object at lot, line, and piece level **/
							$scope.updateAllArrays();
							/** Check if the data is available from screening section then change the lot status **/
							if (cargoScreeningDirective && cargoScreeningDirective.cargoScreen) {
								//if the cargo screening is failed assign FOH - Station information to lot.
								//this will be done for each lot in a loop
								if (cargoScreeningDirective.cargoScreen.screeningFailed) {
									angular.forEach($scope.getLots.routedLot, function (routeItem, routeIndex) {
										routeItem.currentLotStatus = routingDirectiveConstants.AWB_STATUS_FOH;
										routeItem.lotStatusStation = routingDirectiveConstants.AWB_STATUS_FOH + routingDirectiveConstants.HYPHEN + $scope.userStationStatus[routeIndex];
									});
								} else {
									//if the cargo screening is successful and status is FOH and SLI and not
									//screened and no errors assign status to lots as SCRN - station
									angular.forEach($scope.getLots.routedLot, function (routeItem, routeIndex) {
										if ((manageAwbCntrl.headerData.Fullstatus === routingDirectiveConstants.AWB_STATUS_FOH_EXPAND || manageAwbCntrl.headerData.Fullstatus === routingDirectiveConstants.AWB_STATUS_SLI) && manageAwbCntrl.displayDivErrorBarCS === false && manageAwbCntrl.displayDivErrorBarRt !== false && routeItem.currentLotStatus.toString().indexOf(routingDirectiveConstants.AWB_STATUS_SCRN) === -1) {
											routeItem.currentLotStatus = routingDirectiveConstants.AWB_STATUS_SCRN;
											routeItem.lotStatusStation = routingDirectiveConstants.AWB_STATUS_SCRN + routingDirectiveConstants.HYPHEN + $scope.userStationStatus[routeIndex];
										}
									});
								}
							} else {
								//else assign no lot status and station
								angular.forEach($scope.getLots.routedLot, function (routeItem, routeIndex) {
									routeItem.currentLotStatus = $scope.initialUserLotStatus[routeIndex];
									routeItem.lotStatusStation = $scope.initialUserLotStatus[routeIndex] + routingDirectiveConstants.HYPHEN + $scope.userStationStatus[routeIndex];
									if (!routeItem.currentLotStatus || routeItem.currentLotStatus === 'undefined') {
										routeItem.currentLotStatus = "";
										routeItem.lotStatusStation = "";
									}
								});
							}
							/** To update the route enable and disabled the route buttons **/
							$scope.updateRouteButton();
							/** To update re-calculate the weight at the piece and line level **/
							$scope.updateRouteWeight();
						} else {
							$timeout(function () {
								if ($scope.getLots.routedLot) {
									$scope.assignFocusToLots();
								}
							}, 100);
							$scope.selectedLotNbr = 0;
							$scope.isReRouteState = false;
							$scope.enableButtonsSplit = true;
						}
					}
					//To call the Element click event when the element is focused using tab
					$scope.callClickEventOnFocus = function (element) {
						$timeout(function () {
							angular.element('#' + element).click();
						});
					};
					$scope.checkIfAllRadioButton = function (lots) {
						var flag = true;
						angular.forEach(lots, function (lotItem) {
							if (!lotItem.flightLegs || (lotItem.flightLegs && lotItem.flightLegs.length === 0)) {
								flag = false;
							}
						});
						return flag;
					};
					//Routing Focus to lots
					$scope.assignFocusToLots = function (selectedLotNumber) {
						var radioSelected = false;
						if ($rootScope.routingData && $rootScope.routingData.lots) {
							var maxLotNumber = $rootScope.routingData.lots.length;
							$timeout(function () {
								if ($scope.checkIfAllRadioButton($scope.getLots.routedLot)) {
									radioSelected = true;
									$('#lotRadioButton1').click();
								} else {
									$scope.enableButtons = true;
								}
								if (!radioSelected) {
									if (selectedLotNumber) {
										$('#lotInputField' + selectedLotNumber).focus().click();
									} else {
										$('#lotInputField' + maxLotNumber).focus().click();
									}
								}
							}, $scope.configConstants.timeConst500);
						}
					};
					if (!$scope.initialInit) {
						$scope.updateRouteButton();
					} else {
						$scope.initialInit = false;
					}
					if (manageAwbCntrl.prevAccType === 'routing' && manageAwbCntrl.routing) {
						$scope.isRoutingOpened = true;
					}
					if ($scope.searchValues.findRoutes.shipDate || $scope.searchValues.findRoutes.startDepartTime) {
						$scope.assignDefaultShipDateTime();
					}
					$scope.disableResetLink = true;
				};
				/**
				 * To hide and show re-route button if radio button is
				 * checked in routed lot
				 *
				 * @param {}
				 */
				$scope.updateRouteButton = function () {
					if ($scope.selectedLotNbr !== 0) {
						/*Checks the section 3 is opened and is valid or Section is valid*/
						if ((shipmentDetailsScope === '' && manageAwbCntrl.displayDivErrorBarSd === false) || shipmentDetailsScope.displayDivErrorBarSd === false) {
							$scope.enableRouteButton = false;
						} else {
							$scope.enableRouteButton = true;
						}
					} else {
						$scope.enableRouteButton = true;
					}
				};
				/**
				 * To Assign Default Ship Date And Time to Routing Section DateTime Fields
				 *
				 *@param {}
				 *
				 */
				/* @CodeReviewFix:03-2016 : Start : Removed all the date manipulation of code and handled using moment to identify current date and time */
				$scope.assignDefaultShipDateTime = function () {
                    //Fix for the issue related to preferred date is not applied in sli scenario
                    if ($rootScope.awbContent.preferredShipDate && moment($rootScope.awbContent.preferredShipDate).format('MM/DD/YYYY') !== 'Invalid date') {
                        $scope.searchValues.findRoutes.shipDate = $rootScope.awbContent.preferredShipDate;
                    } else {
					   $scope.searchValues.findRoutes.shipDate = moment($rootScope.currentDate).format('MM/DD/YYYY');
                    }
					$scope.searchValues.findRoutes.startDepartTime = $rootScope.swaLocationTime;
				};
				/* @CodeReviewFix:03-2016  : End   */
				/**
				 * validate and check if the lots are available are not
				 *
				 *  @param {}
				 *  @returns {boolean} - based on routed lots.
				 *
				 */
				$scope.checkIfLotsExist = function () {
					if ($scope.getLots && $scope.getLots.routedLot && $scope.getLots.routedLot.length > 0) {
						return true;
					}
					return false;
				};
				/**
				 * To initialize the variables required to initiate Routing Section
				 *
				 * @param{}
				 *
				 */
				$scope.init = function () {
					if (manageAwbCntrl.headerData.awbNumber !== undefined) {
						$scope.getAwbDetailsFromService(manageAwbCntrl.headerData.awbNumber.toString());
					}
					//Defect #5075 Fixes
					$scope.actualWeightUnit = routingDirectiveConstants.defaultWeightUnit;
					$scope.displayWeightLBS = true;
					//Defect #5075 Fixes
					$scope.originStart = [];
					$scope.initialSearch = {};
					$scope.airports = [];
					$scope.originOptions = [];
					$scope.destinationOptions = [];
					$scope.carrierOptions = [];
					$scope.searchValues = {};
					$scope.searchValues.findRoutes = {};
					$scope.searchValues.findRoutes.origin = {
						key : manageAwbCntrl.headerData.origin,
						value : manageAwbCntrl.headerData.origin
					};
					$scope.searchValues.findRoutes.destination = {
						key : manageAwbCntrl.headerData.destination,
						value : manageAwbCntrl.headerData.destination
					};
					$scope.carrierOptions = $localStorage.carrierOptionsRouting;
					if (!manageAwbCntrl.expandAllClicked) {
						//Defect #4423 Fixes
						if ($scope.assignFocusToLots) {
							$scope.assignFocusToLots();
						}
					}
					$scope.fields = $localStorage.airportDetailsRouting;
					/**Concanetating String for Orgin**/
					$scope.facilityDetailsConcatenation = function (fieldItem) {
						//Defect #3822 Fixes
						return fieldItem.cityName + ', ' + fieldItem.stateName + ' (' + fieldItem.countryName + ') - ' + fieldItem.airportIata;
					};
					/*Creating A modal for Origin Field
					Field Is An Object of Key Value Pair
					Key Is A concatenating string of cityname, country name, state name and airportIata
					Value contains airportIata value*/
					angular.forEach($scope.fields, function (fieldItem) {
						$scope.originStart.push({
							key : $scope.facilityDetailsConcatenation(fieldItem),
							value : fieldItem.airportIata
						});
					});
					$scope.airports = $scope.originStart;
					$scope.originOptions = $scope.originStart;
					$scope.destinationOptions = $scope.originStart;
					$scope.assignDefaultShipDateTime();
					$timeout(function () {
						$('.selectpicker').selectpicker();
						//$("#radBtnFindRoutes").prop("checked", true);
						$("#from .selectize-input").find("input").attr("maxlength", "3");
						$("#to .selectize-input").find("input").attr("maxlength", "3");
					}, 10);
				};
				/**
				 * Function called on click of a radio button for Find Route & Specify Route
				 * @params{val : routeCondition}
				 *
				 */
				$scope.conditionForRouteOrSpecify = function (routeCondition) {
					if (routeCondition == routingDirectiveConstants.RTE_CONDITION_FIND_ROUTE) {
						$scope.specifyRoute = false;
					} else {
						$scope.specifyRoute = true;
						if ($scope.searchValues.specifyRoutes === undefined) {
							$scope.clear(); //@CodeReviewFix:03-2016 Start - reusing clean method instead of repeating same code.
						}
					}
				};
				/**
				 * To iterate the routes returned and assign route Ids to route and flight legs
				 *
				 * @param{}
				 *
				 */
				$scope.calculateLegDetails = function () {
					/*Iterates over response_values coming from service
					It checks if it is not routed then it creates a listOfRoutes.
					 */
					/* @CodeReviewFix:03-2016 : Start :Removed all the unnecessary code from this block	*/
					angular.forEach($scope.response_values, function (routeItem) {
						if (routeItem.routeId === undefined) {
							routeItem.routeId = $scope.rtIdInc;
							$scope.listOfRoutes.push({
								key : angular.copy($scope.rtIdInc.toString()),
								value : angular.copy($scope.rtIdInc.toString())
							});
						}
						/*And similarly its check the legs of flight are not routed list of legs
						Both creates flightLegs lists which do not contain Routed Flight details
						 */
						angular.forEach(routeItem.flightLegs, function (legItem) {
							if (legItem.routeId === undefined) {
								legItem.routeId = $scope.rtIdInc;
							}
						});
						/*incrementing the Route Id Count*/
						$scope.rtIdInc = $scope.rtIdInc + 1;
					});
					/* @CodeReviewFix:03-2016  : End   */
				};
				/**
				 * decide which section to trigger based on the tab.
				 *
				 * @param{}
				 */
				$scope.decideForTriggeringButtons = function () {
					if ($scope.shouldOpenSection7 === true && $scope.isCaseOfSplittingROuted === false) {
						manageAwbCntrl.triggerNxtSection(routingDirectiveConstants.SECTION_ID_SIX, routingDirectiveConstants.SIGNATURE_SECTION);
					}
					//@CodeReviewFix:03-2016 Moved to constants file
				};
				/**
				 * Method to get the value from Show all flight is checked or not checked
				 *
				 * @param {}
				 *
				 */
				$scope.getShowAllFlightFlag = function () {
					var flag = false;
					if (angular.element('#chkflt').length > 0) {
						flag = angular.element('#chkflt').prop('checked')
					}
					return flag;
				}
				/**
				 * Method to invoke validateRoutes service
				 *
				 * @param {String}
				 *
				 */
				$scope.validateRoutes = function (chkValidate) {
					if (!$scope.isRouteFirstTime || chkValidate === "indirectvalidate") {
						//checkValidateRoute variable will be updated from the web service call
						var checkValidateRoute = true,
						userRoutesList = [],
						routeRowItem = {};
						/* @CodeReviewFix:03-2016 : Start : Removed all the unnecessary codes from this method */
						var tmpReqForValidateRoute = {};
						//Populate tmpReqForValidateRoute with request attributes needed for validate route service
						/**Iterates over the specify object arraylist to create list that are valid**/
						angular.forEach($scope.searchValues.specifyRoutes, function (routeItem) {
							routeRowItem = {};
							if (routeItem.fltNbr !== undefined && routeItem.fltNbr !== "") {
								routeRowItem.carrier = angular.copy(routeItem.carrier);
								routeRowItem.carrier = typeof(routeRowItem.carrier) === "object" ? routeRowItem.carrier.value : routeRowItem.carrier;
								routeRowItem.flightNumber = angular.copy(routeItem.fltNbr);
								routeRowItem.depDate = angular.copy(routeItem.DepDate);
								routeRowItem.origin = angular.copy(routeItem.origin);
								routeRowItem.origin = typeof(routeRowItem.origin) === "object" ? routeRowItem.origin.value : routeRowItem.origin;
								routeRowItem.destination = angular.copy(routeItem.destination);
								routeRowItem.destination = typeof(routeRowItem.destination) === "object" ? routeRowItem.destination.value : routeRowItem.destination;
								userRoutesList.push(routeRowItem);
							}
						});
						tmpReqForValidateRoute.routeFlightLeg = angular.copy(userRoutesList);
						tmpReqForValidateRoute.routedLot = angular.copy($scope.getLots.routedLot);
						angular.forEach(tmpReqForValidateRoute.routedLot, function (lotItem) {
							var chkForBlankPiecesInLine = [];
							angular.forEach(lotItem.lines, function (lineItem) {
								if (lineItem.noOfPieces > 0) {
									chkForBlankPiecesInLine.push(angular.copy(lineItem));
								}
							});
							lotItem.lines = angular.copy(chkForBlankPiecesInLine);
							if ($scope.doReRouting) {
								lotItem.reroutingReason = {
									code : $scope.reRoutingReason,
									name : $scope.reRoutingReason,
                                    //Defect #5316 Fixes : Start
									description : $scope.reRouteReasonDescrition
                                    //Defect #5316 Fixes : End
								};
							}
						});
						tmpReqForValidateRoute.awbNumber = manageAwbCntrl.headerData.awbNumber;
						tmpReqForValidateRoute.awbPrefix = $scope.configConstants.AWB_PREFIX_NO;
						//other approaches were tried to handle the check box show all flights button
						//hence using DOM manipulation
						tmpReqForValidateRoute.showOnlyFlightsWithAvailableCapacity = $scope.getShowAllFlightFlag();
						tmpReqForValidateRoute.shipDateTime = $scope.searchValues.findRoutes.shipDate + " " + $scope.searchValues.findRoutes.startDepartTime;
						tmpReqForValidateRoute.serviceLevel = manageAwbCntrl.headerData.serviceLevel;
						tmpReqForValidateRoute.commodityCode = $scope.getLots.routedLot[0].commodityCode;
						tmpReqForValidateRoute.specialHandlingCodes = $scope.getLots.routedLot[0].lines[0].lineSHC;
						tmpReqForValidateRoute.awbVersion = ((manageAwbCntrl.updatedVersionNumber < manageAwbCntrl.awbContent.versionNumber) ? manageAwbCntrl.awbContent.versionNumber : manageAwbCntrl.updatedVersionNumber);
						/* @CodeReviewFix:03-2016 : Start : removed all the delete calls and implemented data maapers for request    */
						var tempRequestInfo = {};
						tempRequestInfo = new ValidateRouteRequest(tmpReqForValidateRoute);
						tempRequestInfo.volume = parseFloat(tempRequestInfo.volume);
						manageAWBService.validateRoute(JSON.stringify(tempRequestInfo)).then(function (responseData) {
							//Handle the validate route response
							if (responseData.data.errorMessage === null || responseData.data.errorMessage === undefined) {
								$rootScope.routingData.lots = angular.copy(responseData.data.routedLot);
								if (shipmentDetailsScope && shipmentDetailsScope.generatedLots) {
									shipmentDetailsScope.generatedLots.routedLot = angular.copy($scope.getLots.routedLot);
								}
								//update the version number of the AWB
								manageAwbCntrl.updatedVersionNumber = responseData.data.awbVersion;
								//if no error message, take the lots and assign the routing details that got validated for each lot.
								for (var ltCntr1 = 0; ltCntr1 < $scope.getLots.routedLot.length; ltCntr1++) {
									for (var ltCntr2 = 0; ltCntr2 < responseData.data.routedLot.length; ltCntr2++) {
										if ($scope.getLots.routedLot[ltCntr1].lotNumber === responseData.data.routedLot[ltCntr2].lotNumber) {
											$scope.getLots.routedLot[ltCntr1].noOfPieces = responseData.data.routedLot[ltCntr2].noOfPieces;
											$scope.getLots.routedLot[ltCntr1].actualWeight = responseData.data.routedLot[ltCntr2].actualWeight;
											$scope.getLots.routedLot[ltCntr1].volume = responseData.data.routedLot[ltCntr2].volume;
											$scope.getLots.routedLot[ltCntr1].currentLotStatus = responseData.data.routedLot[ltCntr2].currentLotStatus;
											$scope.getLots.routedLot[ltCntr1].lotStatusStation = responseData.data.routedLot[ltCntr2].lotStatusStation;
											$scope.getLots.routedLot[ltCntr1].versionNumber = responseData.data.routedLot[ltCntr2].versionNumber;
											$scope.getLots.routedLot[ltCntr1].routingInvalid = responseData.data.routedLot[ltCntr2].routingInvalid;
											if (responseData.data.routedLot[ltCntr2].assignedRunner !== undefined) {
												$scope.getLots.routedLot[ltCntr1].assignedRunner = responseData.data.routedLot[ltCntr2].assignedRunner;
											}
											if (responseData.data.routedLot[ltCntr2].wareHouseLocation !== undefined) {
												$scope.getLots.routedLot[ltCntr1].wareHouseLocation = responseData.data.routedLot[ltCntr2].wareHouseLocation;
											}
											if (responseData.data.routedLot[ltCntr2].deliveredToCustomer !== undefined) {
												$scope.getLots.routedLot[ltCntr1].deliveredToCustomer = responseData.data.routedLot[ltCntr2].deliveredToCustomer;
											}
											$scope.getLots.routedLot[ltCntr1].flightLegs = [];
											for (var flt2 = 0; flt2 < responseData.data.routedLot[ltCntr2].flightLegs.length; flt2++) {
												var fromRespFlts = {};
												fromRespFlts.carrier = responseData.data.routedLot[ltCntr2].flightLegs[flt2].carrierCode.code;
												fromRespFlts.destination = responseData.data.routedLot[ltCntr2].flightLegs[flt2].destination.code;
												fromRespFlts.origin = responseData.data.routedLot[ltCntr2].flightLegs[flt2].origin.code;
												fromRespFlts.estimatedArrivalTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estArrTime;
												fromRespFlts.estimatedDepartureTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estDepTime;
												fromRespFlts.estimatedArrivalDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estimatedArrDate;
												fromRespFlts.estimatedDepartureDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estimatedDepDate;
												fromRespFlts.scheduledArrivalDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedArrDate;
												fromRespFlts.scheduledDepartureDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedDepDate;
												fromRespFlts.scheduledArrivalTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].scheduledArrTime;
												fromRespFlts.scheduledDepartureTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].scheduledDepTime;
												fromRespFlts.schedDepDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedDepDateTimeLocal;
												fromRespFlts.schedArrDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedArrDateTimeLocal;
												fromRespFlts.estArrDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estArrDateTimeLocal;
												fromRespFlts.origArrunk = responseData.data.routedLot[ltCntr2].flightLegs[flt2].origArrunk;
												fromRespFlts.destArrunk = responseData.data.routedLot[ltCntr2].flightLegs[flt2].destArrunk;
												fromRespFlts.estDepDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estDepDateTimeLocal;
												fromRespFlts.flightLegKey = responseData.data.routedLot[ltCntr2].flightLegs[flt2].flightLegKey;
												fromRespFlts.forceBooking = responseData.data.routedLot[ltCntr2].flightLegs[flt2].forceBooking;
												fromRespFlts.flightNumber = responseData.data.routedLot[ltCntr2].flightLegs[flt2].flightNumber;
												fromRespFlts.seqNum = responseData.data.routedLot[ltCntr2].flightLegs[flt2].seqNum;
												$scope.getLots.routedLot[ltCntr1].flightLegs.push(fromRespFlts);
											}
											break;
										}
									}
									$scope.calculateFlightLegDetails($scope.getLots.routedLot);
									$scope.updateAllArrays();
								}
                                //Fixes for : lot was getting removed when we re-route in routing section5 using specify route
								$rootScope.routingData.lots = angular.copy($scope.getLots.routedLot);
								if (airwayBillDataService.checkIfLotsRouted($scope.getLots.routedLot)) {
									//Remove the error message from route panel header
									var whetherHeaderGreen = true;
									for (var chkAllLots = 0; chkAllLots < $scope.getLots.routedLot.length; chkAllLots++) {
										if ($scope.getLots.routedLot[chkAllLots].routingInvalid === true) {
											whetherHeaderGreen = false;
											break;
										}
									}
									if (whetherHeaderGreen) {
										manageAwbCntrl.displayDivErrorBarRt = false;
										manageAwbCntrl.dispAccHdrErrorRt = false;
										manageAwbCntrl.fieldNameRt = "";
									} else {
										manageAwbCntrl.displayDivErrorBarRt = true;
										manageAwbCntrl.dispAccHdrErrorRt = true;
										manageAwbCntrl.fieldNameRt = "";
									}
									$scope.searchRoutePanel = false;
									$scope.routesSummary = false;
									$scope.lineTextBox = false;
									$scope.pieceTextBox = false;
									$scope.lineRadio = true;
									$scope.pieceRadio = true;
									$scope.headerRadio = true;
									$scope.hdrRouteNo = false;
									//$("#routingBody").attr("style", "display:none;");
									$scope.enableButtons = true;
									$scope.enableRouteButton = true;
									$scope.enableButtonsSplit = true;
									$scope.disableResetLink = true;
									var checkWhetherRouteOrReroute = false;
									angular.forEach($scope.getLots.routedLot, function (lotItem) {
										if (lotItem.fltNbr === undefined) {
											checkWhetherRouteOrReroute = true;
										}
									});
									if (checkWhetherRouteOrReroute) {
										$scope.areLotsRouted = false;
									} else {
										$scope.areLotsRouted = true;
									}
									$scope.isRouteFirstTime = false;
									if (!$scope.doReRouting) {
										$scope.navToNextSection('', '', true);
									} else {
										$scope.doReRouting = false;
										$scope.addCommentObject();
									}
								} else {
									$scope.nextUnroutedLot = 0;
									angular.forEach($scope.getLots.routedLot, function (routeItem) {
										if (!routeItem.flightLegs && $scope.nextUnroutedLot === 0) {
											$scope.nextUnroutedLot = routeItem.lotNumber;
										}
									});
									$timeout(function () {
										$scope.assignFocusToLots($scope.nextUnroutedLot.toString());
									}, 100);
								}
								if (responseData.data.outsideOperatingHours === true) {
									$scope.displayMessage = responseData.data.limitationReasons;
									$scope.openModalDialogueError();
								}
								//Timeout is used to maintain the expand of lot after the ng-repeat finish its rendering in UI.
								$timeout(function() {
									angular.forEach($scope.getLots.routedLot, function (routeItem) {
										if ($("#plusMinusLines" + routeItem.lotNumber.toString()).hasClass("minus")) {
											$(".trLineClass" + routeItem.lotNumber.toString()).removeClass("ng-hide");
										}
									});
								});
							} else {
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
								manageAwbCntrl.fieldNameRt = responseData.data.errorMessage;
							}
						});
					} else {
						$scope.openModalDialogueReRoute();
					}
				};
				/* @CodeReviewFix:03-2016  : End   */
				$scope.enableCheckBox = function (routeItem) {
					var flag = false;
					if (routeItem !== $scope.currentSelectedLot) {
						//Check the which button is displayed i.e Select Route or Route button
						if ($scope.areLotsRouted) {
							//Check the lot not routed
							if (!routeItem.flightLegs || (routeItem.flightLegs && routeItem.flightLegs.length === 0)) {
								flag = true;
							}
						} else {
							//Check the lot is routed
							if (routeItem.flightLegs && routeItem.flightLegs.length > 0) {
								flag = true;
							}
						}
					}
					return flag;
				};
				/**
				 * Show and hide lines based on the UI selection
				 *
				 * @param{int} - line number that got selected
				 *
				 */
				$scope.openDetailRowLines = function (lineNumber) {
					if (!$(".toggleLineClass").hasClass("minus") || lineNumber === $scope.lotNoSelected) {
						if ($("#lineTable" + lineNumber).hasClass("ng-hide")) {
							$("#lineTable" + lineNumber).removeClass("ng-hide");
							$scope.isLotClosed = false;
							$scope.lotNoSelected = angular.copy(lineNumber);
						} else {
							$("#lineTable" + lineNumber).addClass("ng-hide");
							$scope.isLotClosed = true;
						}
						angular.element(".trLineClass" + lineNumber).toggleClass('ng-hide');
						angular.element("#plusMinusLines" + lineNumber).toggleClass("minus");
						if ($("#plusMinusLines" + lineNumber.toString()).hasClass("minus")) {
							$(".clubbedLine" + lineNumber.toString() + " tbody").removeClass("ng-hide");
						}
					}
				};
				/**
				 * Close all the expanded lots.
				 *
				 */
				$scope.closeAllExpandedLot = function () {
					if ($scope.lotNoSelected) {
						var lineNumber = $scope.lotNoSelected;
						$("#lineTable" + lineNumber).addClass("ng-hide");
						$(".trLineClass" + lineNumber).addClass("ng-hide");
						$("#plusMinusLines" + lineNumber).addClass("minus");
						$(".clubbedLine" + lineNumber.toString()).find("tbody").addClass("ng-hide");
					}
				};
				//Defect #4423 Fixes : Start
				/**
				 * To clear and show error message when invalied route number is entered in #Rte input box
				 *
				 */
				$scope.checkFlightNumber = function (element, type) {
					var flag = true;
					manageAwbCntrl.fieldNameRt = '';
					if ($scope.listOfRoutes && $scope.listOfRoutes.length > 0) {
						if (element.val().trim() === '' || parseInt(element.val()) === 0 || (element.val() > $scope.listOfRoutes.length)) {
							if (element.val().trim() !== '') {
								element.val('');
								$timeout(function () {
									if (type === 'tab') {
										element.focus();
									}
									manageAwbCntrl.displayDivErrorBarRt = true;
									manageAwbCntrl.dispAccHdrErrorRt = true;
									manageAwbCntrl.fieldNameRt = routingDirectiveConstants.INVALID_ROUTE_SELECTED_MSG;
								});
							} else {
								$scope.updateRoutingHeader();
							}
							element.trigger('input');
							flag = false;
						}
					} else {
						element.val('');
						if (type === 'tab') {
							$timeout(function () {
								element.focus();
							});
						}
						flag = false;
					}
					flag = element.val() === '' ? false : flag;
					return flag;
				};
				/**
				 * To navigate to next section when value is not present in #RTE field and tabbed
				 *
				 *@param {Event} - to identify the event which caused this
				 *@param {String} - button type
				 *@param {boolean} - next section navigate
				 *
				 */
				/* @CodeReviewFix:03-2016 : Start : Added new method to handle section navigation    */
				$scope.navToNextSection = function (event, type, navNextSec) {
					if (((event.which == 9 || event.keyCode == 9) && !event.shiftKey) || navNextSec) {
						if ((event !== '' && !$(event.target).val().trim()) || type === 'routeButton' || navNextSec) {
							$("#routingBody").attr("style", "display:none;");
							// Start-for defect 5074
							$("#routingAcrdn").removeClass("active");
							// End-for defect 5074
							if ($scope.isReRouteState) {
								//This is the case when user has tabbed out of RTE# feiled while rerouting (without entering a route)
								//Hence reverting back to the original lots
								//Copy Routing data from rootScope to Routing section
								$scope.getLots = {};
								$scope.getLots.routedLot = angular.copy($rootScope.routingData.lots);
							}
							var tempPanelCntr = $("#routingAcrdn").next();
							for (var openPanelCntr = 0; openPanelCntr < 5; openPanelCntr++) {
								if (tempPanelCntr.hasClass("disabled")) {
									tempPanelCntr = tempPanelCntr.next();
								} else {
									tempPanelCntr.addClass("active");
									tempPanelCntr.find(".accordionBody").attr("style", "display:block;");
									manageAwbCntrl.autoSaveData("signatures");
									if (manageAwbCntrl.expandAllClicked === true) {
										manageAwbCntrl.assignFocusToElements("signatureRText");
									}
									break;
								}
							}
						} else {
							$scope.checkFlightNumber($(event.target), 'tab');
						}
					}
				};
				/* @CodeReviewFix:03-2016  : End   */
				//Defect #4423 Fixes : End
				/**
				 * To check any of the piece checkbox is selected
				 *
				 * @param {}
				 */
				$scope.checkIsAnyCheckboxIsSelected = function () {
					var flag = false;
					angular.forEach($scope.pieceCheckBoxes, function (pieceItem) {
						if (pieceItem.isChecked) {
							flag = true;
						}
					});
					return flag;
				};
				/**
				 * Route numbers are assigned based on the lotNumber
				 *
				 * @param {int} - lot number
				 */
				$scope.assignRouteToLot = function (lotNumber) {
					//Defect #4423 Fixes
					var routeElement = $("#lotInputField" + lotNumber.toString());
					if (($scope.checkFlightNumber(routeElement) && $scope.response_values !== undefined && routeElement.val() !== '') || $scope.checkIfRouteIsEntered()) { //@CodeReviewFix:03-2016  Removed DOM manipulation code.
						if ((shipmentDetailsScope === '' && manageAwbCntrl.displayDivErrorBarSd === false) || shipmentDetailsScope.displayDivErrorBarSd === false) {
							$scope.enableButtons = false;
							$scope.enableRouteButton = false;
						} else {
							$scope.enableButtons = true;
							$scope.enableRouteButton = true;
						}
						$timeout(function () {
							focus('enableButtons');
							if (manageAwbCntrl.expandAllClicked) {
								$("#signatureRText").removeClass("ng-invalid ng-invalid-required ng-valid-maxlength ng-touched"); //@CodeReviewFix:03-2016  removed unnecessasry lines
							}
						}, 100);
						$scope.enableButtonsSplit = true;
						$scope.lotsThatAreRouted = $scope.lotsThatAreRouted + "," + lotNumber.toString() + ",";
						var tempFlightLegs = [];
						var tempRouteData = {};
						//Defect #4423 Fixes : Start
						angular.forEach($scope.response_values, function (flightItem) { //@CodeReviewFix:03-2016  using Angular.for also fixed variables name
							if (flightItem.routeId.toString() === routeElement.val()) {
								tempFlightLegs = flightItem.flightLegs;
								tempRouteData.flightNumber = flightItem.flightNumber;
								tempRouteData.origin = flightItem.origin;
								tempRouteData.destination = flightItem.destination;
								tempRouteData.scheduledDepartureDate = flightItem.scheduledDepartureDate;
								tempRouteData.scheduledArrivalTime = flightItem.scheduledArrivalTime;
								tempRouteData.estimatedArrivalTime = flightItem.estimatedArrivalTime;
							}
						});
						var tempRouteObj = {
							routeId : routeElement.val(),
							lotId : lotNumber,
							flightLegs : tempFlightLegs,
							routeData : tempRouteData
						}; //@CodeReviewFix:03-2016  proper code indendation to fix readability.
						//Defect #4423 Fixes : End
						if (!$scope.containsObject(tempRouteObj, $scope.routeNumbersToBeValidated)) {
							$scope.routeNumbersToBeValidated.push(tempRouteObj);
						} else {
							$scope.updateObject(tempRouteObj, $scope.routeNumbersToBeValidated);
						}
					} else {
						$scope.enableButtons = true;
						$scope.enableRouteButton = true;
					}
				};
				/**
				 * assigning the routes to lots for splits
				 *
				 * @param {String} - lot number
				 * @param {String} - route number
				 *
				 */
				$scope.assignRoutesToLotsForSplit = function (lotNumber, routeNo) {
					var tempFlightLegs = [],
					tempRouteData = {};
					angular.forEach($scope.response_values, function (responseItem) { //@CodeReviewFix:03-2016  Replaced with Angula for, also fixed the variable names
						if (responseItem.routeId.toString() === routeNo.toString()) {
							tempFlightLegs = responseItem.flightLegs;
							tempRouteData.flightNumber = responseItem.flightNumber;
							tempRouteData.origin = responseItem.origin;
							tempRouteData.destination = responseItem.destination;
							tempRouteData.scheduledDepartureDate = responseItem.scheduledDepartureDate;
							tempRouteData.scheduledArrivalTime = responseItem.scheduledArrivalTime;
							tempRouteData.estimatedArrivalTime = responseItem.estimatedArrivalTime;
						}
					});
					var tempRouteObj = {
						routeId : routeNo.toString(),
						lotId : lotNumber,
						flightLegs : tempFlightLegs,
						routeData : tempRouteData
					}; //@CodeReviewFix:03-2016  Fixed code indendation
					if (!$scope.containsObject(tempRouteObj, $scope.routeNumbersToBeValidated)) {
						$scope.routeNumbersToBeValidated.push(tempRouteObj);
					} else {
						$scope.updateObject(tempRouteObj, $scope.routeNumbersToBeValidated);
					}
				};
				/**
				 * Function to identify and create flight leg information from the existing lots.
				 *
				 * @param{String} - lotNumber
				 */
				$scope.assignRoutesToLotsForSearch = function (lotNumber) {
					var tempFlightLegs = [],
					tempRouteData = {};
					/**To update the route object with the flight leg selected in routing section
					And this updated object will be sent to service to perform select route
					 **/
					angular.forEach($scope.getLots.routedLot, function (routeItem) { //@CodeReviewFix:03-2016  Replaced with Angula for, also fixed the variable names
						if (routeItem.lotNumber.toString() === lotNumber.toString()) {
							if (routeItem.hasOwnProperty("flightLegs")) {
								angular.forEach(routeItem.flightLegs, function (legItem, legIndex) {
									tempFlightLegs.push({
										carrier : legItem.carrier,
										destination : legItem.destination,
										origin : legItem.origin,
										destinationTimeZone : legItem.destinationTimeZone,
										estimatedArrivalDate : legItem.estimatedArrivalDate,
										estimatedArrivalTime : legItem.estimatedArrivalTime,
										estimatedDepartureDate : legItem.estimatedDepartureDate,
										estimatedDepartureTime : legItem.estimatedDepartureTime,
										flightNumber : legItem.flightNumber,
										originTimeZone : legItem.originTimeZone,
										routeSeqNumber : (legIndex + 1),
										scheduledArrivalDate : legItem.scheduledArrivalDate,
										scheduledArrivalTime : legItem.scheduledArrivalTime,
										scheduledDepartureDate : legItem.scheduledDepartureDate,
										scheduledDepartureTime : legItem.scheduledDepartureTime
									});
								});
							}
							tempRouteData.flightNumber = routeItem.fltNbr;
							tempRouteData.origin = routeItem.lotsOrig;
							tempRouteData.destination = routeItem.destination;
							tempRouteData.scheduledDepartureDate = routeItem.scheduledDepartureDate;
							tempRouteData.scheduledArrivalTime = routeItem.scheduledArrivalTime;
							tempRouteData.estimatedArrivalTime = routeItem.estimatedArrivalTime;
						}
					});
					var tempRouteObj = {
						lotId : lotNumber,
						flightLegs : tempFlightLegs,
						routeData : tempRouteData
					}; //@CodeReviewFix:03-2016  Fixed code indendation
					if (!$scope.containsObject(tempRouteObj, $scope.routeNumbersToBeValidated)) {
						$scope.routeNumbersToBeValidated.push(tempRouteObj);
					} else {
						$scope.updateObjectForSearch(tempRouteObj, $scope.routeNumbersToBeValidated);
					}
				};
				/**
				 * Function to test if the lotId matches in List.
				 *
				 * @param{Obj} - contains the lotId details
				 * @param{List} - Contains the list of all lotIds
				 * @returns {boolean} - Flag to return value present or not in the list
				 *
				 */
				$scope.containsObject = function (obj, list) {
					var ret = false;
					for (var i = 0; i < list.length; i++) {
						if (list[i].lotId === obj.lotId) {
							ret = true;
							break;
						}
					}
					return ret;
				};
				/**
				 * Function to test if the lotNumber matches in List.
				 *
				 * @param{Obj} - contains the lotId details
				 * @param{List} - Contains the list of all lotNumbers
				 * @returns {boolean} - Flag to return value present or not in the list
				 *
				 */
				$scope.containsObjectLotNo = function (obj, list) {
					var ret = false;
					for (var i = 0; i < list.length; i++) {
						if (list[i].lotNumber === obj.lotNumber) {
							ret = true;
							break;
						}
					}
					return ret;
				};
				/**
				 * Function to test if lotNumber and sequence number matches in List.
				 *
				 * @param{Obj} - contains the lotId details
				 * @param{List} - Contains the list of all lotNumbers
				 * @returns {boolean} - Flag to return value present or not in the list
				 *
				 */
				$scope.containsObjectLineNo = function (obj, list) {
					var ret = false;
					for (var i = 0; i < list.length; i++) {
						if (list[i].lotNumber === obj.lotNumber && list[i].seqNum === obj.seqNum) {
							ret = true;
							break;
						}
					}
					return ret;
				};
				/**
				 * Function to test if the lotNumber and sequence number matches in List.
				 *
				 * @param{Obj} - contains the lotId details
				 * @param{List} - Contains the list of all lotNumbers
				 * @returns {boolean} - Flag to return value present or not in the list
				 *
				 */
				$scope.containsObjectPieceNo = function (obj, list) {
					var ret = false;
					for (var i = 0; i < list.length; i++) {
						//Each piece has unique "displayName"
						if (list[i].displayName === obj.displayName) {
							ret = true;
							break;
						}
					}
					return ret;
				};
				/**
				 * For matching lot id, update routeId, flightLegs, routeData
				 *
				 *  @param {obj} - contains lot id/route Id, flightLegs, routeData
				 *  @param {list} - list of all the route details
				 *
				 */
				$scope.updateObject = function (obj, list) {
					for (var i = 0; i < list.length; i++) {
						if (list[i].lotId === obj.lotId) {
							list[i].routeId = obj.routeId;
							list[i].flightLegs = obj.flightLegs;
							list[i].routeData = obj.routeData;
							break;
						}
					}
				};
				/**
				 * For matching lot id, update routeId, flightLegs, routeData
				 *
				 *  @param {obj} - contains lot id/route Id, flightLegs, routeData
				 *  @param {list} - list of all the route details
				 *
				 */
				$scope.updateObjectForSearch = function (obj, list) {
					for (var i = 0; i < list.length; i++) {
						if (list[i].lotId === obj.lotId) {
							list[i].flightLegs = obj.flightLegs;
							list[i].routeData = obj.routeData;
							break;
						}
					}
				};
				/**
				 * Open/close the list of pieces based on the selection
				 *
				 *  @param {String} - lotNumber
				 *  @param {String} - pieceNumber
				 *
				 */
				$scope.openDetailRowPieces = function (lotNumber, pieceNumber) {
					if ($(".trPieceClass" + lotNumber + pieceNumber).hasClass("ng-hide")) {
						$(".trPieceClass" + lotNumber + pieceNumber).removeClass("ng-hide");
					} else {
						$(".trPieceClass" + lotNumber + pieceNumber).addClass("ng-hide");
					}
					if ($("#plusMinusPieces" + lotNumber + pieceNumber).hasClass("minus")) {
						$("#plusMinusPieces" + lotNumber + pieceNumber).removeClass("minus");
					} else {
						$("#plusMinusPieces" + lotNumber + pieceNumber).addClass("minus");
					}
					$(".trPieceClass" + lotNumber + pieceNumber).attr("style", "display:table-row");
				};
				/**
				 * Check and uncheck all the lines and pieces based on the checkbox selection at lot level
				 *
				 * @param {String} - lotNumber
				 *
				 */
				$scope.checkLots = function (lotNumber) {
					angular.forEach($scope.lotCheckBoxes, function (lotItem) {
						/* @CodeReviewFix:03-2016 : Start : removed all the $this references and jquery related codes    */
						if (lotItem.lotNumber === lotNumber) {
							if (!lotItem.isChecked) {
								$(".linesChecked" + lotNumber).prop("checked", true);
								$(".piecesChecked" + lotNumber).prop("checked", true);
							} else {
								$(".linesChecked" + lotNumber).prop("checked", false);
								$(".piecesChecked" + lotNumber).prop("checked", false);
							}
							lotItem.isChecked = !lotItem.isChecked;
						}
					});
					angular.forEach($scope.lineCheckBoxes, function (lineItem) {
						if (lineItem.lotNumber === lotNumber) {
							lineItem.isChecked = !lineItem.isChecked;
						}
					});
					angular.forEach($scope.pieceCheckBoxes, function (pieceItem) {
						if (pieceItem.lotNumber === lotNumber) {
							pieceItem.isChecked = !pieceItem.isChecked;
						}
					});
					$scope.clearEnteredValues();
					/* @CodeReviewFix:03-2016 : End */
				};
				/**
				 * check lines checked in the lot
				 *
				 *@param{seqNum} - sequence number for the line
				 *@param{lotNumber} - lot number under with line number selected
				 *
				 */
				$scope.checkLines = function (seqNum, lotNumber) {
					var checkLotVar = 0,
					linesOfParticularLot = 0,
					pieceFlag = false,
					shouldSplitButtonEnable = false;
					if (manageAwbCntrl.fieldNameRt === routingDirectiveConstants.ALL_PCS_SAME_LOT_SEL_SPLIT_MSG) { //@CodeReviewFix:03-2016 Moved messages to constants file
						manageAwbCntrl.fieldNameRt = '';
					}
					angular.forEach($scope.lineCheckBoxes, function (lineItem) {
						if (lineItem.seqNum === seqNum && lineItem.lotNumber === lotNumber) {
							if ($('#seqNum' + lineItem.lotNumber + seqNum).prop('checked')) {
								$("input[name='piecesChecked" + lotNumber + seqNum + "']").prop("checked", true);
								pieceFlag = true;
								lineItem.isChecked = true;
							} else {
								angular.forEach($scope.lotCheckBoxes, function (lotItem) {
									if (lotItem.lotNumber === lotNumber) {
										if (lotItem.isChecked) {
											$(".lotsChecked" + lotNumber).prop("checked", false);
											lotItem.isChecked = !lotItem.isChecked;
										}
									}
								});
								lineItem.isChecked = false;
								$("input[name='piecesChecked" + lotNumber + seqNum + "']").prop("checked", false);
							}
							angular.forEach($scope.pieceCheckBoxes, function (pieceItem) {
								if (pieceItem.seqNum === seqNum && pieceItem.lotNumber === lotNumber) {
									pieceItem.isChecked = pieceFlag;
								}
							});
						}
						if (lineItem.isChecked && lineItem.lotNumber === lotNumber) {
							checkLotVar = checkLotVar + 1;
						}
						if (lineItem.lotNumber === lotNumber) {
							linesOfParticularLot = linesOfParticularLot + 1;
						}
					});
					if (checkLotVar === linesOfParticularLot) {
						angular.forEach($scope.lotCheckBoxes, function (lotItem) {
							if (lotItem.lotNumber === lotNumber) {
								if (!lotItem.isChecked) {
									$(".lotsChecked" + lotNumber).prop("checked", true);
									lotItem.isChecked = !lotItem.isChecked;
								}
							}
						});
					}
					shouldSplitButtonEnable = $scope.checkIsAnyCheckboxIsSelected();
					if ((shipmentDetailsScope === '' && manageAwbCntrl.displayDivErrorBarSd === false) || shipmentDetailsScope.displayDivErrorBarSd === false) {
						$scope.enableButtonsSplit = shouldSplitButtonEnable ? false : true;
					} else {
						$scope.enableButtonsSplit = true;
					}
					$scope.clearEnteredValues();
				};
				/**
				 * To update Lot Level Checkbox based on Piece Level Checkbox
				 *
				 * @param{String} - pieceNo which was selcted
				 * @param {String} - sequence Number of the selcted piece
				 * @param {String} - lot Number of the seleted piece
				 */
				$scope.checkPieces = function (pieceNo, seqNum, lotNumber, element) {
					var checkLineVar = 0,
					pieceOfParticularLine = 0,
					lineOfSelectedLot = 0,
					checkLineForLotVar = 0,
					shouldSplitButtonEnable = false;
					//remove the display message for all pieces selected when handling split lot scenario
					if (manageAwbCntrl.fieldNameRt === routingDirectiveConstants.ALL_PCS_SAME_LOT_SEL_SPLIT_MSG) {
						manageAwbCntrl.fieldNameRt = '';
					}
					angular.forEach($scope.pieceCheckBoxes, function (pieceItem) {
						if (pieceItem.seqNumber === pieceNo && pieceItem.lotNumber === lotNumber && pieceItem.seqNum === seqNum) {
							if (pieceItem.isChecked) {
								// Unselecting Lot Level Checkbox
								angular.forEach($scope.lotCheckBoxes, function (lotItem) {
									if (lotItem.lotNumber === lotNumber) {
										$(".lotsChecked" + lotNumber).prop("checked", false);
										lotItem.isChecked = false;
									}
								});
								// Unselecting Line Level Checkbox
								angular.forEach($scope.lineCheckBoxes, function (lineItem) {
									if (lineItem.seqNum === seqNum && lineItem.lotNumber === lotNumber) {
										$("#seqNum" + lotNumber + seqNum).prop("checked", false);
										lineItem.isChecked = false;
									}
								});
							}
							pieceItem.isChecked = !pieceItem.isChecked;
						}
						if (pieceItem.isChecked && pieceItem.seqNum === seqNum && pieceItem.lotNumber === lotNumber) {
							checkLineVar = checkLineVar + 1;
						}
						if (pieceItem.seqNum === seqNum) {
							pieceOfParticularLine = pieceOfParticularLine + 1;
						}
					});
					//toggle logic to check and uncheck lot and line
					if (checkLineVar === pieceOfParticularLine) {
						angular.forEach($scope.lineCheckBoxes, function (lineItem) {
							if (lineItem.seqNum === seqNum && lineItem.lotNumber === lotNumber) {
								$("#seqNum" + lotNumber + seqNum).prop("checked", true);
								lineItem.isChecked = !lineItem.isChecked;
							}
							if (lineItem.isChecked && lineItem.lotNumber === lotNumber) {
								checkLineForLotVar = checkLineForLotVar + 1;
							}
							if (lineItem.lotNumber === lotNumber) {
								lineOfSelectedLot = lineOfSelectedLot + 1;
							}
						});
						if (checkLineForLotVar === lineOfSelectedLot) {
							angular.forEach($scope.lotCheckBoxes, function (lotItem) {
								if (lotItem.lotNumber === lotNumber) {
									$(".lotsChecked" + lotNumber).prop("checked", true);
									lotItem.isChecked = !lotItem.isChecked;
								}
							});
						}
					}
					for (var x = 0; x < $scope.pieceCheckBoxes.length; x++) {
						if ($scope.pieceCheckBoxes[x].isChecked) {
							shouldSplitButtonEnable = true;
							break;
						}
					}
					if ((shipmentDetailsScope === '' && manageAwbCntrl.displayDivErrorBarSd === false) || shipmentDetailsScope.displayDivErrorBarSd === false) {
						$scope.enableButtonsSplit = shouldSplitButtonEnable ? false : true;
					} else {
						$scope.enableButtonsSplit = true;
					}
					$scope.clearEnteredValues();
					if ($scope.pieceCheckBoxes.length === 1) {
						if ($scope.pieceCheckBoxes[0].isChecked === false && manageAwbCntrl.fieldNameRt !== undefined && manageAwbCntrl.fieldNameRt !== "") {
							if ($scope.getLots.routedLot[0].fltNbr !== undefined) {
								manageAwbCntrl.displayDivErrorBarRt = false;
								manageAwbCntrl.dispAccHdrErrorRt = false;
								manageAwbCntrl.fieldNameRt = "";
							} else {
								manageAwbCntrl.displayDivErrorBarRt = undefined;
								manageAwbCntrl.dispAccHdrErrorRt = undefined;
								manageAwbCntrl.fieldNameRt = "";
							}
						}
					}
				};
				/**
				 * For Clearing the route values entered in #RTE Input field and unselect the radio button
				 *
				 * @param{}
				 */
				$scope.clearEnteredValues = function () {
					angular.forEach($scope.getLots.routedLot, function (lotItem) {
						angular.element('#lotInputField' + lotItem.lotNumber).scope().enteredRouteNumber = '';
						angular.element('#lotRadioButton' + lotItem.lotNumber).scope().checkLotNumber = false;
						//$scope.selectedLotNbr = 0;
					});
					if ($scope.enableButtonsSplit) {
						$scope.assignFocusToLots();
					}
				};
				$scope.checkIfRouteIsEntered = function () {
					var flag = false,
					elementValue = '';
					angular.forEach($scope.getLots.routedLot, function (routeItem) {
						if (!routeItem.flightLegs || (routeItem.flightLegs && routeItem.flightLegs.length === 0)) {
							elementValue = angular.element('#lotInputField' + routeItem.lotNumber).scope().enteredRouteNumber;
							if (elementValue) {
								flag = true;
							}
						}
					});
					return flag;
				};
				/**
				 * For removing all the duplicate lots, lines and pices from the routing section
				 *
				 * @param{}
				 */
				$scope.removeDuplicateRecords = function () {
					var lotCheckBoxesNoDup = [],
					lineCheckBoxesNoDup = [],
					pieceCheckBoxesNoDup = [];
					var tmpLotPush = {},
					tmpLinePush = {},
					tmpPiecePush = {};
					angular.forEach($scope.lotCheckBoxes, function (lotItem) { //@CodeReviewFix:03-2016 using angular for
						if (!$scope.containsObjectLotNo(lotItem, lotCheckBoxesNoDup)) {
							//identify and push only non duplicate lots
							tmpLotPush = {};
							tmpLotPush = angular.copy(lotItem);
							lotCheckBoxesNoDup.push(tmpLotPush);
						}
					});
					angular.forEach($scope.lineCheckBoxes, function (lineItem) { //@CodeReviewFix:03-2016 using angular for
						//identify and push only non duplicate lots
						if (!$scope.containsObjectLineNo(lineItem, lineCheckBoxesNoDup)) {
							tmpLinePush = {};
							tmpLinePush = angular.copy(lineItem);
							lineCheckBoxesNoDup.push(tmpLinePush);
						}
					});
					angular.forEach($scope.pieceCheckBoxes, function (pieceItem) { //@CodeReviewFix:03-2016 using angular for
						//identify and push only non duplicate lots
						if (!$scope.containsObjectPieceNo(pieceItem, pieceCheckBoxesNoDup)) {
							tmpPiecePush = {};
							tmpPiecePush = angular.copy(pieceItem);
							pieceCheckBoxesNoDup.push(tmpPiecePush);
						}
					});
					$scope.lotCheckBoxes = angular.copy(lotCheckBoxesNoDup);
					$scope.lineCheckBoxes = angular.copy(lineCheckBoxesNoDup);
					$scope.pieceCheckBoxes = angular.copy(pieceCheckBoxesNoDup);
				};
				$scope.enableValidateRoute = true;
				/**
				 * To enable or disable validate route button based on valid rows
				 * in the validate route pane in section 5
				 *
				 * @param{}
				 */
				$scope.updateValidateButton = function () {
					var carrier = '',
					flight = '',
					depDate = '',
					origin = '',
					destination = '',
					validRowCount = 0,
					makeLineDirty = false;
					$timeout(function () {
						$scope.enableValidateRoute = false;
						angular.forEach($scope.specifyRoutesCount.specRoute, function (routeItem, routeIndex) {
							if ($scope.enableValidateRoute === false) {
								//read the entered values from the validate route lines
								carrier = $('#carrier' + routeIndex);
								flight = $('#flight' + routeIndex);
								depDate = $('#DepDate2' + routeIndex);
								origin = $('#origin' + routeIndex + ' span').text().trim();
								destination = $('#destination' + routeIndex + ' span').text().trim();
								makeLineDirty = false;
								if (flight.val().trim() !== '' || depDate.val().trim() !== '' || origin !== '' || destination !== '') {
									makeLineDirty = true;
									validRowCount = validRowCount + 1;
								}
								//if all the conditions meets, then enable the validate Route button
								if ($('#carrier' + routeIndex + '  option:selected').text().trim() === '') {
									$scope.enableValidateRoute = true;
								}
								if ((flight.val().trim() === '' && makeLineDirty) || (flight.hasClass('ng-invalid') && makeLineDirty)) {
									$scope.enableValidateRoute = true;
								}
								if ((depDate.val().trim() === '' && makeLineDirty) || (depDate.hasClass('ng-invalid') && makeLineDirty)) {
									$scope.enableValidateRoute = true;
								}
								if (origin === '' && makeLineDirty) { //@CodeReviewFix:03-2016 removed DOM access/manipulation logic
									$scope.enableValidateRoute = true;
								}
								if (destination === '' && makeLineDirty) { //@CodeReviewFix:03-2016 removed DOM access/manipulation logic
									$scope.enableValidateRoute = true;
								}
							}
						});
						if ($scope.enableValidateRoute === false && validRowCount === 0) {
							$scope.enableValidateRoute = true;
						}
					});
				};
				/**
				 * Function for handling Split lot scenarios
				 *
				 * @param {}
				 *
				 */
				$scope.splitLot = function () {
					manageAwbCntrl.fieldNameRt = "";
					$scope.removeDuplicateRecords();
					var checkForMultipleLots = 0,
					checkForPieceSelected = 0,
					selectedLotNumber = 0,
					totalPiecesOfOneLot = 0;
					//checking if multiple lots selected or single lot
					angular.forEach($scope.pieceCheckBoxes, function (pieceItem) { //@CodeReviewFix:03-2016 using angular for and proper variable names instead of $scope reference
						if (pieceItem.isChecked && selectedLotNumber !== 0 && selectedLotNumber !== pieceItem.lotNumber) {
							selectedLotNumber = pieceItem.lotNumber;
							checkForMultipleLots = checkForMultipleLots + 1;
						}
						//if piece selecte with in lot, count how many selected
						if (pieceItem.isChecked) {
							checkForPieceSelected = checkForPieceSelected + 1;
							selectedLotNumber = pieceItem.lotNumber;
						}
					});
					//checking total pieces in a lot
					for (var a = 0; a < $scope.getLots.routedLot.length; a++) {
						if ($scope.getLots.routedLot[a].lotNumber === selectedLotNumber) {
							angular.forEach($scope.getLots.routedLot[a].lines, function (lineItem) {
								angular.forEach(lineItem.pieces, function (pieceItem) {
									totalPiecesOfOneLot = totalPiecesOfOneLot + 1;
								});
							});
							break;
						}
					}
					if (checkForMultipleLots === 0 && checkForPieceSelected > 0 && checkForPieceSelected !== totalPiecesOfOneLot) {
						$scope.fetchTheMaxLotNo = 0;
						//if reset pressed then copy the lot details for revert
						$scope.getLotsForReset = angular.copy($scope.getLots);
						$scope.removeDuplicateRecords();
						var linesCheckedArray = [],
						piecesCheckedArray = [],
						currentRowType = "",
						lotStatusOfPrvLot = "",
						lotStatusStation = '';
						if (!airwayBillDataService.checkIfLotsRouted($scope.getLots.routedLot)) {
							$scope.disableResetLink = false;
						} else {
							$scope.disableResetLink = true;
						}
						//identify the lot number selected and then push to new array for new lot creation
						for (var lotNbr = 0; lotNbr < $scope.lotCheckBoxes.length; lotNbr++) {
							if ($scope.lotCheckBoxes[lotNbr].lotNumber === selectedLotNumber) {
								for (var lineNbr = 0; lineNbr < $scope.lineCheckBoxes.length; lineNbr++) {
									if ($scope.lineCheckBoxes[lineNbr].isChecked) {
										linesCheckedArray.push($scope.lineCheckBoxes[lineNbr]);
									}
								}
								for (var pieceNbr = 0; pieceNbr < $scope.pieceCheckBoxes.length; pieceNbr++) {
									if ($scope.pieceCheckBoxes[pieceNbr].isChecked) {
										piecesCheckedArray.push($scope.pieceCheckBoxes[pieceNbr]);
										for (var lineNbr = 0; lineNbr < $scope.lineCheckBoxes.length; lineNbr++) {
											if ($scope.pieceCheckBoxes[pieceNbr].seqNum === $scope.lineCheckBoxes[lineNbr].seqNum && $scope.pieceCheckBoxes[pieceNbr].lotNumber === $scope.lineCheckBoxes[lineNbr].lotNumber) {
												linesCheckedArray.push($scope.lineCheckBoxes[lineNbr]);
											}
										}
									}
								}
								for (var ctr1 = 0; ctr1 < $scope.getLots.routedLot.length; ctr1++) {
									if ($scope.getLots.routedLot[ctr1].lotNumber === selectedLotNumber) {
										lotStatusOfPrvLot = $scope.getLots.routedLot[ctr1].currentLotStatus;
										lotStatusStation = $scope.getLots.routedLot[ctr1].lotStatusStation;
										break;
									}
								}
							}
							if (lotNbr === $scope.lotCheckBoxes.length - 1) {
								currentRowType = $scope.lotCheckBoxes[lotNbr].rowType;
							}
						}
						linesCheckedArray = linesCheckedArray.filter(function (item, i, ar) {
								return ar.indexOf(item) === i;
							});
						if ($scope.selectedLotNbr !== 0 && $scope.getLots.routedLot[$scope.selectedLotNbr - 1].flightLegs.length === 0 && $scope.areLotsRouted) {
							$scope.resetReRouteState('split');
						}
						var tempLines = [];
						var linesArrayForWeightAndVolume = [];
						var increamentalPieceNo = 1;
						var increamentalLineNo = 1;
						var totalSelectedPiecesCount = 0;
						var totalSelectedPiecesWeight = 0;
						var totalSelectedPiecesVolume = 0;
						//based on the lines selected, create new array structures to be copied to create new lots
						for (var lineInc = 0; lineInc < linesCheckedArray.length; lineInc++) {
							var tempPieces = [];
							var tempPiecesOfLine = [];
							var totalSelectedPiecesCountOfLine = 0;
							var totalSelectedPiecesWeightOfLine = 0;
							var totalSelectedPiecesVolumeOfLine = 0;
							var decidePieceWtForSplitLot = 0;
							var decidePieceVolForSplitLot = 0;
							var decidePieceWtForSplitLine = 0;
							var decidePieceVolForSplitLine = 0;
							for (var x = 0; x < $scope.getLots.routedLot.length; x++) {
								if ($scope.getLots.routedLot[x].lotNumber === linesCheckedArray[lineInc].lotNumber) {
									decidePieceWtForSplitLot = $scope.getLots.routedLot[x].actualWeight / $scope.getLots.routedLot[x].noOfPieces;
									decidePieceVolForSplitLot = $scope.getLots.routedLot[x].volume / $scope.getLots.routedLot[x].noOfPieces;
									for (var y = 0; y < $scope.getLots.routedLot[x].lines.length; y++) {
										if ($scope.getLots.routedLot[x].lines[y].seqNum === linesCheckedArray[lineInc].seqNum) {
											decidePieceWtForSplitLine = $scope.getLots.routedLot[x].lines[y].actualWeight / $scope.getLots.routedLot[x].lines[y].noOfPieces;
											decidePieceVolForSplitLine = $scope.getLots.routedLot[x].lines[y].volume / $scope.getLots.routedLot[x].lines[y].noOfPieces;
										}
									}
								}
							}
							for (var pieceInc = 0; pieceInc < piecesCheckedArray.length; pieceInc++) {
								if (piecesCheckedArray[pieceInc].seqNum === linesCheckedArray[lineInc].seqNum) {
									var dispPieceWtInLbs = 0;
									var dispPieceWtInKg = 0;
									if ($scope.displayWeightLBS) {
										dispPieceWtInLbs = piecesCheckedArray[pieceInc].actualWeight;
									} else {
										dispPieceWtInKg = piecesCheckedArray[pieceInc].actualWeight;
									}
									//add piece information to new array
									tempPieces.push({
										seqNum : "LN" + increamentalLineNo,
										seqNumber : increamentalPieceNo,
										displayName : piecesCheckedArray[pieceInc].displayName,
										actualWeight : piecesCheckedArray[pieceInc].actualWeight,
										piecesActWtLbs : piecesCheckedArray[pieceInc].piecesActWtLbs,
										piecesActWtKg : piecesCheckedArray[pieceInc].piecesActWtKg,
										volume : piecesCheckedArray[pieceInc].volume
									});
									increamentalPieceNo = increamentalPieceNo + 1;
									totalSelectedPiecesCount = totalSelectedPiecesCount + 1;
									totalSelectedPiecesVolume = totalSelectedPiecesVolume + decidePieceVolForSplitLot;
									totalSelectedPiecesCountOfLine = totalSelectedPiecesCountOfLine + 1;
									totalSelectedPiecesWeightOfLine = totalSelectedPiecesWeightOfLine + piecesCheckedArray[pieceInc].actualWeight;
									totalSelectedPiecesVolumeOfLine = totalSelectedPiecesVolumeOfLine + decidePieceVolForSplitLot;
									tempPiecesOfLine.push({
										seqNumber : piecesCheckedArray[pieceInc].seqNumber
									});
								}
							}
							totalSelectedPiecesWeight = totalSelectedPiecesWeight + totalSelectedPiecesWeightOfLine;
							var dispLineWtInLbs = 0;
							var dispLineWtInKg = 0;
							if ($scope.displayWeightLBS) {
								dispLineWtInLbs = totalSelectedPiecesWeightOfLine;
							} else {
								dispLineWtInKg = totalSelectedPiecesWeightOfLine;
							}
							if ($scope.displayWeightLBS) {
								dispLineWtInLbs = Math.ceil(totalSelectedPiecesWeightOfLine);
							}
							var shcArray = linesCheckedArray[lineInc].lineSHC.split(",");
							var shcArrayList = [];
							for (var ct = 0; ct < shcArray.length; ct++) {
								shcArrayList.push({
									code : shcArray[ct],
									name : shcArray[ct],
									description : shcArray[ct]
								});
							}
							//push the line information by reating new line and add assigning new lot number
							tempLines.push({
								lotNumber : ($scope.getMaxLotNo() + 1),
								seqNum : linesCheckedArray[lineInc].seqNum,
								noOfPieces : totalSelectedPiecesCountOfLine,
								actualWeight : Math.ceil(totalSelectedPiecesWeightOfLine),
								lineActWtLBS : dispLineWtInLbs,
								lineActWtKG : dispLineWtInKg,
								volume : Math.ceil(totalSelectedPiecesVolumeOfLine),
								contentDesc : linesCheckedArray[lineInc].contentDesc,
								lineSHC : linesCheckedArray[lineInc].lineSHC,
								pieces : tempPieces,
								uniqueLineNo : linesCheckedArray[lineInc].uniqueLineNo,
								specialHandlingCode : shcArrayList
							});
							linesArrayForWeightAndVolume.push({
								seqNum : linesCheckedArray[lineInc].seqNum,
								noOfPieces : totalSelectedPiecesCountOfLine,
								actualWeight : totalSelectedPiecesWeightOfLine,
								volume : totalSelectedPiecesVolumeOfLine,
								pieces : tempPiecesOfLine
							});
							increamentalLineNo = increamentalLineNo + 1;
						}
						$scope.ifLotContainsRouteInfo = false;
						$scope.flightLegsForRoutedLot = [];
						$scope.appendCommodityCode = "";
						$scope.appendLotContentDesc = "";
						//also identify the route information and assign to lots
						for (var ltRtChk = 0; ltRtChk < $scope.getLots.routedLot.length; ltRtChk++) {
							if ($scope.getLots.routedLot[ltRtChk].lotNumber === selectedLotNumber) {
								$scope.appendLotContentDesc = angular.copy($scope.getLots.routedLot[ltRtChk].contentDesc);
								$scope.appendCommodityCode = angular.copy($scope.getLots.routedLot[ltRtChk].commodityCode);
								if ($scope.getLots.routedLot[ltRtChk].flightLegs && $scope.getLots.routedLot[ltRtChk].flightLegs.length > 0) {
									$scope.ifLotContainsRouteInfo = true;
									$scope.flightLegsForRoutedLot = angular.copy($scope.getLots.routedLot[ltRtChk].flightLegs);
									$rootScope.isRoutingSave = false; //Routed Dont allow save Routing Info
									$scope.disableResetLink = true; //Disable Reset link
								} else {
									$rootScope.isRoutingSave = true; //Allow Save Routing Info
									$scope.disableResetLink = false; //Enable Reset Link
								}
								break;
							}
						}
						//if lot contains routed information then also assign the flighleg details at the flight level
						if ($scope.ifLotContainsRouteInfo) {
							$scope.getLots.routedLot.push({
								rowType : (currentRowType === "even" ? "odd" : "even"),
								lotNumber : ($scope.getMaxLotNo() + 1),
								noOfPieces : totalSelectedPiecesCount,
								actualWeight : Math.ceil(totalSelectedPiecesWeight),
								volume : Math.ceil(totalSelectedPiecesVolume),
								lines : tempLines,
								currentLotStatus : lotStatusOfPrvLot,
								commodityCode : $scope.appendCommodityCode,
								flightLegs : $scope.flightLegsForRoutedLot,
								contentDesc : $scope.appendLotContentDesc,
								lotStatusStation : lotStatusStation
							});
							$scope.fetchTheMaxLotNo = $scope.getMaxLotNo() + 1;
						} else {
							//if no flight leg information then push without that into routedLot array
							$scope.getLots.routedLot.push({
								rowType : (currentRowType === "even" ? "odd" : "even"),
								lotNumber : ($scope.getMaxLotNo() + 1),
								noOfPieces : totalSelectedPiecesCount,
								actualWeight : Math.ceil(totalSelectedPiecesWeight),
								volume : Math.ceil(totalSelectedPiecesVolume),
								lines : tempLines,
								currentLotStatus : lotStatusOfPrvLot,
								commodityCode : $scope.appendCommodityCode,
								contentDesc : $scope.appendLotContentDesc,
								lotStatusStation : lotStatusStation
							});
							$scope.fetchTheMaxLotNo = $scope.getMaxLotNo() + 1;
						}
						//update route scope
						$rootScope.totalLotsForDisc = $scope.getLots.routedLot.length;
						//update the selected lot with the reduced information to make the adjustments for the new lot created.
						for (var ltCntr = 0; ltCntr < $scope.getLots.routedLot.length; ltCntr++) {
							if ($scope.getLots.routedLot[ltCntr].lotNumber === selectedLotNumber) {
								$scope.getLots.routedLot[ltCntr].noOfPieces = $scope.getLots.routedLot[ltCntr].noOfPieces - totalSelectedPiecesCount;
								$scope.getLots.routedLot[ltCntr].actualWeight = Math.ceil($scope.getLots.routedLot[ltCntr].actualWeight - totalSelectedPiecesWeight);
								$scope.getLots.routedLot[ltCntr].volume = Math.ceil($scope.getLots.routedLot[ltCntr].volume - totalSelectedPiecesVolume);
								for (var lnCntr = 0; lnCntr < $scope.getLots.routedLot[ltCntr].lines.length; lnCntr++) {
									var dispseqNum = 0;
									for (var tmpLnCntr = 0; tmpLnCntr < linesArrayForWeightAndVolume.length; tmpLnCntr++) {
										if (linesArrayForWeightAndVolume[tmpLnCntr].seqNum === $scope.getLots.routedLot[ltCntr].lines[lnCntr].seqNum) {
											dispseqNum = dispseqNum + 1;
											$scope.getLots.routedLot[ltCntr].lines[lnCntr].noOfPieces = $scope.getLots.routedLot[ltCntr].lines[lnCntr].noOfPieces - linesArrayForWeightAndVolume[tmpLnCntr].noOfPieces;
											$scope.getLots.routedLot[ltCntr].lines[lnCntr].actualWeight = Math.ceil($scope.getLots.routedLot[ltCntr].lines[lnCntr].actualWeight - linesArrayForWeightAndVolume[tmpLnCntr].actualWeight);
											if ($scope.displayWeightLBS) {
												$scope.getLots.routedLot[ltCntr].lines[lnCntr].lineActWtLBS = $scope.getLots.routedLot[ltCntr].lines[lnCntr].actualWeight;
											} else {
												$scope.getLots.routedLot[ltCntr].lines[lnCntr].lineActWtKG = $scope.getLots.routedLot[ltCntr].lines[lnCntr].actualWeight;
											}
											$scope.getLots.routedLot[ltCntr].lines[lnCntr].volume = Math.ceil($scope.getLots.routedLot[ltCntr].lines[lnCntr].volume - linesArrayForWeightAndVolume[tmpLnCntr].volume);
											var piecesToBeRemovedFromLine = [];
											for (var pcCntr = 0; pcCntr < $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces.length; pcCntr++) {
												for (var tmpPcCntr = 0; tmpPcCntr < linesArrayForWeightAndVolume[tmpLnCntr].pieces.length; tmpPcCntr++) {
													if ($scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].seqNumber === linesArrayForWeightAndVolume[tmpLnCntr].pieces[tmpPcCntr].seqNumber) {
														//identify the pieces to be removed from the line
														piecesToBeRemovedFromLine.push($scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr]);
													}
												}
											}
											$scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces = $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces.filter(function (x) {
													return piecesToBeRemovedFromLine.indexOf(x) < 0;
												});
											$("#lineIcon" + $scope.getLots.routedLot[ltCntr].lines[lnCntr].lotNumber.toString() + $scope.getLots.routedLot[ltCntr].lines[lnCntr].seqNum.toString()).removeClass("ng-hide");
											for (var a = 0; a < $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces.length; a++) {
												$scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[a].seqNumber = a + 1;
											}
										}
									}
								}
							}
						}
						//update all the rows with new information
						$scope.updateAllArrays();
						//update the route weights by clculating from new information.
						$scope.updateRouteWeight();
						$timeout(function () { //@CodeReviewFix:03-2016 removed setTimeOut and updated to timeout
							//update the new created lot with all the information
							if ($scope.ifLotContainsRouteInfo) {
								$scope.isCaseOfSplittingROuted = true;
								$scope.calculateFlightLegDetails($scope.getLots.routedLot);
								//show the new lot created
								// $("#lineRadioWithId" + $scope.fetchTheMaxLotNo.toString()).removeClass("ng-hide");
								// $("#pieceRadioWithId" + $scope.fetchTheMaxLotNo.toString()).removeClass("ng-hide");
								// $("#lineTextBoxWithId" + $scope.fetchTheMaxLotNo.toString()).addClass("ng-hide");
								// $("#pieceTextBoxWithId" + $scope.fetchTheMaxLotNo.toString()).addClass("ng-hide");
								$(".trlineclass" + $scope.lotNoSelected.toString()).removeClass("ng-hide");
								$scope.updateAllArrays();
								$timeout(function () {
									$("#lineTable" + $scope.lotNoSelected.toString()).find("tbody").removeClass("ng-hide");
									$scope.doConditionalSelectRoute('routing');
									$scope.selectRoute('routing');
								}, 1000);
								$scope.onlyForSplitRoutedLot = true;
								var tmpRadioBtnSelect = {};
								tmpRadioBtnSelect.values = {};
								tmpRadioBtnSelect.values.lotNumber = $scope.fetchTheMaxLotNo;
								//make the newly selected lot default selected
								$scope.assignFocusToLots();
								manageAwbCntrl.assignFocusToElements("btnDoReRouting");
							} else {
								if (!$(".trLineClass" + $scope.lotNoSelected.toString()).hasClass("ng-hide")) {
									$(".trLineClass" + $scope.lotNoSelected.toString()).addClass("ng-hide");
									$("#lineTable" + $scope.lotNoSelected.toString()).addClass("ng-hide");
								}
								if ($("#plusMinusLines" + $scope.lotNoSelected.toString()).hasClass("minus")) {
									$("#plusMinusLines" + $scope.lotNoSelected.toString()).removeClass("minus");
								}
								//Defect #4423 Fixes
								$scope.assignFocusToLots();
							}
							$scope.enableButtonsSplit = true;
						}, 100);
					} else if (checkForMultipleLots === 0 && checkForPieceSelected === 0 && checkForPieceSelected !== totalPiecesOfOneLot) {
						//when minimum one piece not selected from single lot, show message
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_MIN_ONE_PC_MSG;
					} else if (checkForMultipleLots === 0 && checkForPieceSelected > 0 && checkForPieceSelected === totalPiecesOfOneLot) {
						//when all pieces selected from a single lot
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.ALL_PCS_SAME_LOT_SEL_SPLIT_MSG;
					} else if (checkForMultipleLots > 0 && checkForPieceSelected > 0 && checkForPieceSelected !== totalPiecesOfOneLot) {
						//when multiple lots selected show business error message
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_SAME_LOT_FR_SPLIT_MSG;
					}
					if (manageAwbCntrl.headerData.awbStatus !== undefined && manageAwbCntrl.headerData.awbStatus === routingDirectiveConstants.AWB_STATUS_MAN) {
						$rootScope.changeOrNotManageawb = false;
					}
					//Upadting the root level object for routing
					$rootScope.routingData.lots = angular.copy($scope.getLots.routedLot);
					if (shipmentDetailsScope && shipmentDetailsScope.generatedLots) {
						shipmentDetailsScope.generatedLots.routedLot = angular.copy($scope.getLots.routedLot);
					}
				};
				/**
				 *
				 * Based on the screen section, decide to fetch the routing details.
				 *
				 * @param{String} screenName
				 *
				 */
				$scope.doConditionalSelectRoute = function (screenName) {
					if (screenName !== "routing") {
						$scope.routeNumbersToBeValidated = [];
						angular.forEach($scope.getLots.routedLot, function (lotItem) {
							$scope.assignRoutesToLotsForSearch(lotItem.lotNumber);
						});
						$scope.selectRoute('shipment');
					} else {
						$scope.routeNumbersToBeValidated = [];
						angular.forEach($scope.getLots.routedLot, function (lotItem) {
							$scope.assignRoutesToLotsForSearch(lotItem.lotNumber);
						});
					}
				};
				/**
				 * If shipment modified then get details for modified shipment details.
				 *
				 * @param{}
				 */
				$scope.doConditionalSelectRouteForEditedShipment = function () {
					$scope.routeNumbersToBeValidated = [];
					angular.forEach($scope.getLots.routedLot, function (lotItem) {
						//if routing valid then assign the routes to lots in case of updated shipments
						if (lotItem.routingInvalid) {
							$scope.assignRoutesToLotsForSearch(lotItem.lotNumber);
						}
					});
				};
				/**
				 * From the flight leg identify the route id
				 *
				 * @param{FlightLeg} - contains the flight leg information
				 * @returns {routeId} - routeId associated with the flight leg
				 *
				 */
				$scope.findTheRouteId = function (fltLeg) {
					var retRtId = 0;
					for (var i = 0; i < $scope.response_values.length; i++) {
						for (var j = 0; j < $scope.response_values[i].flightLegs.length; j++) {
							if ($scope.response_values[i].flightLegs[j].carrier === fltLeg.carrier && $scope.response_values[i].flightLegs[j].origin === fltLeg.origin && $scope.response_values[i].flightLegs[j].flightNumber === fltLeg.flightNumber && $scope.response_values[i].flightLegs[j].scheduledDepartureDate === fltLeg.scheduledDepartureDate && $scope.response_values[i].flightLegs[j].scheduledDepartureTime === fltLeg.scheduledDepartureTime && $scope.response_values[i].flightLegs[j].estimatedDepartureDate === fltLeg.estimatedDepartureDate && $scope.response_values[i].flightLegs[j].estimatedDepartureTime === fltLeg.estimatedDepartureTime) {
								retRtId = $scope.response_values[i].routeId;
								break;
							}
						}
						if (retRtId !== 0) {
							break;
						}
					}
					return retRtId;
				};
				/**
				 * Update the routing section array with all the new information that was created.
				 *
				 * @param{}
				 *
				 */
				$scope.updateAllArrays = function () {
					$scope.lotCheckBoxes = [];
					$scope.lineCheckBoxes = [];
					$scope.pieceCheckBoxes = [];
					if (!$scope.checkIfLotsExist()) {
						return;
					}
					for (var ltCntr = 0; ltCntr < $scope.getLots.routedLot.length; ltCntr++) { //@CodeReviewFix:03-2016 removed unused if condition
						//identify the duplicate records and get it updated in the main routing section array
						if (!$scope.containsObjectLotNo($scope.getLots.routedLot[ltCntr], $scope.lotCheckBoxes)) {
							for (var lnCntr = 0; lnCntr < $scope.getLots.routedLot[ltCntr].lines.length; lnCntr++) {
								for (var pcCntr = 0; pcCntr < $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces.length; pcCntr++) {
									//push piece level informatio
									$scope.pieceCheckBoxes.push({
										pieceNo : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].seqNumber,
										isChecked : false,
										seqNum : $scope.getLots.routedLot[ltCntr].lines[lnCntr].seqNum,
										lotNumber : $scope.getLots.routedLot[ltCntr].lotNumber,
										seqNumber : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].seqNumber,
										displayName : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].displayName,
										actualWeight : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].actualWeight,
										volume : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].volume,
										piecesActWtKg : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].piecesActWtKg,
										piecesActWtLbs : $scope.getLots.routedLot[ltCntr].lines[lnCntr].pieces[pcCntr].piecesActWtLbs
									});
								}
								//push line level information
								$scope.lineCheckBoxes.push({
									seqNum : $scope.getLots.routedLot[ltCntr].lines[lnCntr].seqNum,
									isChecked : false,
									lotNumber : $scope.getLots.routedLot[ltCntr].lotNumber,
									noOfPieces : $scope.getLots.routedLot[ltCntr].lines[lnCntr].noOfPieces,
									actualWeight : $scope.getLots.routedLot[ltCntr].lines[lnCntr].actualWeight,
									volume : $scope.getLots.routedLot[ltCntr].lines[lnCntr].volume,
									contentDesc : $scope.getLots.routedLot[ltCntr].lines[lnCntr].contentDesc,
									lineSHC : $scope.getLots.routedLot[ltCntr].lines[lnCntr].lineSHC,
									uniqueLineNo : $scope.getLots.routedLot[ltCntr].lines[lnCntr].uniqueLineNo
								});
							}
							//push lot level information
							$scope.lotCheckBoxes.push({
								lotNumber : $scope.getLots.routedLot[ltCntr].lotNumber,
								isChecked : false,
								rowType : $scope.getLots.routedLot[ltCntr].rowType,
								noOfPieces : $scope.getLots.routedLot[ltCntr].noOfPieces,
								actualWeight : $scope.getLots.routedLot[ltCntr].actualWeight,
								volume : $scope.getLots.routedLot[ltCntr].volume,
								currentLotStatus : $scope.getLots.routedLot[ltCntr].currentLotStatus,
								contentDesc : $scope.getLots.routedLot[ltCntr].contentDesc
							});
						}
					}
					//update the row_tye information for css style
					var rowTypeCounter = 0;
					angular.forEach($scope.getLots.routedLot, function (data) {
						if (rowTypeCounter % 2 === 0) {
							data.rowType = routingDirectiveConstants.ODD_ROW;
						} else {
							data.rowType = routingDirectiveConstants.EVEN_ROW;
						}
						rowTypeCounter = rowTypeCounter + 1;
					});
				};
				/**
				 * Identify the maximum lot number
				 *
				 * @param {}
				 * @returns {int} - returns the maximum lot numbem
				 *
				 */
				$scope.getMaxLotNo = function () {
					var maxLotNo = 0;
					angular.forEach($scope.lotCheckBoxes, function (lotCheckItem) {
						maxLotNo = maxLotNo < lotCheckItem.lotNumber ? lotCheckItem.lotNumber : maxLotNo;
					});
					return maxLotNo;
				};
				/**
				 * Identifies the maximum piece number information.
				 *
				 *  @param {}
				 *  @returns {int} - maximum piece number
				 */
				$scope.getMaxPieceNo = function () {
					var maxPieceNo = 0;
					angular.forEach($scope.pieceCheckBoxes, function (pieceCheckItem) {
						maxPieceNo = maxPieceNo < pieceCheckItem.seqNumber ? pieceCheckItem.seqNumber : maxPieceNo;
					});
					return maxPieceNo;
				};
				/**
				 *  Gets the maximum line number from routing section data
				 *  @param {}
				 *  @returns {int} - maximum line number
				 */
				$scope.getMaxLineNo = function () {
					var maxLineNo = $scope.lineNumberCounter;
					angular.forEach($scope.lineCheckBoxes, function (lineCheckItem) {
						if (!isNaN(lineCheckBoxes.seqNum)) {
							maxLineNo = maxLineNo < lineCheckBoxes.seqNum ? lineCheckBoxes.seqNum : maxLineNo;
						} else {
							$scope.lineNumberCounter = $scope.lineNumberCounter + 1;
							maxLineNo = $scope.lineNumberCounter;
						}
					});
					return maxLineNo;
				};
				/**
				 * Routing for a particular lot.
				 *
				 */
				$scope.route = function () {
					// Start-for defect 5133
					$scope.assignDefaultShipDateTime();
					// End-for defect 5133
					$scope.isReRouteState = !$scope.isReRouteState;
					$scope.shouldOpenSection7 = false;
					$scope.keepTheInitialValueOfSectionHdr1 = "";
					$scope.keepTheInitialValueOfSectionHdr2 = "";
					if ($scope.isRouteFirstTime) {
						//Open re-route reason pop up
						var isRouteValid = true;
						if ($scope.routeNumbersToBeValidated.length === 0) {
							$scope.keepTheInitialValueOfSectionHdr1 = angular.copy(manageAwbCntrl.displayDivErrorBarRt);
							$scope.keepTheInitialValueOfSectionHdr2 = angular.copy(manageAwbCntrl.dispAccHdrErrorRt);
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = "";
							isRouteValid = false;
						} else {
							manageAwbCntrl.displayDivErrorBarRt = $scope.keepTheInitialValueOfSectionHdr1;
							manageAwbCntrl.dispAccHdrErrorRt = $scope.keepTheInitialValueOfSectionHdr2;
							manageAwbCntrl.fieldNameRt = "";
							isRouteValid = true;
							angular.forEach($scope.routeNumbersToBeValidated, function (routeItem) {
								if (routeItem.routeId !== undefined) {
									if (!$scope.checkRouteValidity(routeItem.routeId)) {
										//Display the error message in front of the lot
										manageAwbCntrl.displayDivErrorBarRt = true;
										manageAwbCntrl.dispAccHdrErrorRt = true;
										manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG; //@CodeReviewFix:03-2016  Hard coded business error message has been moved to constant.
										globalErrorHandlingServices.insertRoutingErrorMessage({
											routeNotAvailable : routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG //@CodeReviewFix:03-2016  Hard coded business error message has been moved to constant.
										});
										isRouteValid = false;
									}
								}
							});
						}
						if (isRouteValid) {
							$scope.isCallFromReRouteButton = true;
							$scope.isReRouteState = true;
							$scope.openModalDialogueReRoute();
						}
					} else {
						angular.element('#lotInputField' + $scope.selectedLotNbr).scope().enteredRouteNumber = '';
						$scope.enableRouteButton = true;
						$scope.performRouteSearch('initial');
						$scope.searchRoutePanel = true;
						$scope.routesSummary = true;
						$scope.lineTextBox = true;
						$scope.pieceTextBox = true;
						$scope.lineRadio = false;
						$scope.pieceRadio = false;
						$scope.headerRadio = false;
						$scope.hdrRouteNo = true;
						$scope.firstHeaderRadio = false;
						angular.forEach($scope.getLots.routedLot, function (routeItem) { //@CodeReviewFix:03-2016  using angular for and proper variable names
							if (routeItem.lotNumber === $scope.selectedLotNbr) {
								routeItem.fltNbr = "";
								routeItem.lotsOrig = "";
								routeItem.destination = "";
								routeItem.scheduledDepartureDate = "";
								routeItem.scheduledArrivalTime = "";
								routeItem.estimatedArrivalTime = "";
								routeItem.scheduledDepartureTime = "";
								routeItem.scheduledArrivalDate = "";
								routeItem.flightLegs = [];
							}
						});
						$(".routeNbrLot").not("#lotInputField" + $scope.selectedLotNbr.toString()).addClass("ng-hide");
						$scope.isRouteFirstTime = true;
						$timeout(function () { //@CodeReviewFix:03-2016  removed setTimeOut
							$(".routeNbrLot").attr("style", "width: 40px;display: block !important;");
							$(".routeNbrLot").not("#lotInputField" + $scope.selectedLotNbr.toString()).attr("style", "width: 40px;");
							$scope.assignFocusToLots($scope.selectedLotNbr.toString());
						}, 10);
					}
				};
				/**
				 * Checking the route validity based on the route ID
				 *
				 *  @param{String} - routeId
				 *
				 */
				$scope.checkRouteValidity = function (routeId) {
					if (routeId) {
						var bRet = false;
						for (var rtCntr = 0; rtCntr < $scope.response_values.length; rtCntr++) {
							if ($scope.response_values[rtCntr].routeId.toString() === routeId) {
								bRet = true;
								break;
							}
						}
						return bRet;
					} else {
						return true;
					}
				};
				/**
				 * Save routing information onTab event
				 *
				 * @param {}
				 */
				$scope.saveRouteInfoOnTabOut = function () {
					$scope.saveRoutingInfo();
				};
				/**
				 * Generate the routing oject for the general save flow
				 *
				 * @returns {LotsList} - List of all lots
				 *
				 */
				/* @CodeReviewFix:03-2016 : Start : corrected all formatting issues*/
				$scope.getRoutingObjectForGeneralSave = function () {
					$scope.tempRequestInfoSave = angular.copy($scope.getLots);
					if ($scope.tempRequestInfoSave.lots !== undefined) {
						delete $scope.tempRequestInfoSave["lots"];
					}
					$scope.tempRequestInfoSave.lots = angular.copy($scope.tempRequestInfoSave.routedLot);
					delete $scope.tempRequestInfoSave["routedLot"];
					angular.forEach($scope.tempRequestInfoSave.lots, function (lotItem) {
						var chkForBlankPiecesInLine = [];
						for (var a = 0; a < lotItem.lines.length; a++) {
							if (lotItem.lines[a].noOfPieces > 0) {
								chkForBlankPiecesInLine.push(angular.copy(lotItem.lines[a]));
							}
						}
						lotItem.lines = angular.copy(chkForBlankPiecesInLine);
						if (lotItem.rowType !== undefined) {
							delete lotItem["rowType"];
						}
						if (lotItem.destination !== undefined) {
							delete lotItem["destination"];
						}
						if (lotItem.estimatedArrivalTime !== undefined) {
							delete lotItem["estimatedArrivalTime"];
						}
						if (lotItem.fltNbr !== undefined) {
							delete lotItem["fltNbr"];
						}
						if (lotItem.lotsOrig !== undefined) {
							delete lotItem["lotsOrig"];
						}
						if (lotItem.scheduledArrivalTime !== undefined) {
							delete lotItem["scheduledArrivalTime"];
						}
						if (lotItem.scheduledDepartureDate !== undefined) {
							delete lotItem["scheduledDepartureDate"];
						}
						delete lotItem["actualWeightKg"];
						delete lotItem["contentDesc"];
						delete lotItem["destination"];
						delete lotItem["estimatedArrivalTime"];
						delete lotItem["lotsOrig"];
						delete lotItem["scheduledArrivalTime"];
						delete lotItem["scheduledDepartureDate"];
						delete lotItem["scheduledDepartureTime"];
						delete lotItem["scheduledArrivalDate"];
						delete lotItem["estimatedArrivalDate"];
						delete lotItem["estimatedDepartureDate"];
						delete lotItem["estimatedDepartureTime"];
						delete lotItem["fltNbr"];
						if ($scope.doReRouting) {
							lotItem.reroutingReason = {
								code : $scope.reRoutingReason,
								name : $scope.reRoutingReason,
								description : $("#ddRerouteReason button span")[0].innerHTML
							};
						}
						if (lotItem.flightLegs) {
							angular.forEach(lotItem.flightLegs, function (legItem) {
								if (legItem.$$hashKey !== undefined) {
									delete legItem["$$hashKey"];
								}
								if (legItem.acType !== undefined) {
									delete legItem["acType"];
								}
								if (legItem.authorizedFlightCapacity !== undefined) {
									delete legItem["authorizedFlightCapacity"];
								}
								if (legItem.availableBucketCapacity !== undefined) {
									delete legItem["availableBucketCapacity"];
								}
								if (legItem.availableFlightCapacity !== undefined) {
									delete legItem["availableFlightCapacity"];
								}
								if (legItem.bookedBucketCapacity !== undefined) {
									delete legItem["bookedBucketCapacity"];
								}
								if (legItem.noOfConnections !== undefined) {
									delete legItem["noOfConnections"];
								}
								if (legItem.noOfLegs !== undefined) {
									delete legItem["noOfLegs"];
								}
								if (legItem.routeId !== undefined) {
									delete legItem["routeId"];
								}
								if (legItem.transitTime !== undefined) {
									delete legItem["transitTime"];
								}
								if (legItem.limitationReasons !== undefined) {
									delete legItem["limitationReasons"];
								}
								delete legItem["arrivalRecoveryTime"];
								delete legItem["limitationReasons"];
								delete legItem["outsideOperatingHours"];
								delete legItem["routeSeqNumber"];
								legItem.schedDepDate = legItem.scheduledDepartureDate;
								delete legItem["scheduledDepartureDate"];
								legItem.estimatedDepDate = legItem.estimatedDepartureDate;
								delete legItem["estimatedDepartureDate"];
								legItem.schedArrDate = legItem.scheduledArrivalDate;
								delete legItem["scheduledArrivalDate"];
								legItem.estimatedArrDate = legItem.estimatedArrivalDate;
								delete legItem["estimatedArrivalDate"];
								legItem.scheduledDepTime = legItem.scheduledDepartureTime;
								delete legItem["scheduledDepartureTime"];
								legItem.scheduledArrTime = legItem.scheduledArrivalTime;
								delete legItem["scheduledArrivalTime"];
								legItem.estArrTime = legItem.estimatedArrivalTime;
								delete legItem["estimatedArrivalTime"];
								legItem.estDepTime = legItem.estimatedDepartureTime;
								delete legItem["estimatedDepartureTime"];
								legItem.carrierCode = {
									code : legItem.carrier,
									name : legItem.carrier,
									description : legItem.carrier
								};
								delete legItem["carrier"];
								legItem.origin = {
									code : legItem.origin,
									name : legItem.origin,
									description : legItem.origin
								};
								legItem.destination = {
									code : legItem.destination,
									name : legItem.destination,
									description : legItem.destination
								};
							});
						}
						var maxLine = 0;
						angular.forEach(lotItem.lines, function (lineItem) {
							if (lineItem.$$hashKey !== undefined) {
								delete lineItem["$$hashKey"];
							}
							var shcArray = lineItem.lineSHC.split(",");
							var shcArrayList = [];
							for (var ct = 0; ct < shcArray.length; ct++) {
								shcArrayList.push({
									code : shcArray[ct],
									name : shcArray[ct],
									description : shcArray[ct]
								});
							}
							if (shcArrayList.length > 0) {
								lineItem.specialHandlingCode = shcArrayList;
							}
							if (lineItem.lineSHC !== undefined) {
								delete lineItem["lineSHC"];
							}
							if (lineItem.lineOrig !== undefined) {
								delete lineItem["lineOrig"];
							}
							if (lineItem.lineDest !== undefined) {
								delete lineItem["lineDest"];
							}
							if (lineItem.lotNumber !== undefined) {
								delete lineItem["lotNumber"];
							}
							//Remove the below lines when toggling between LBS to KG is done
							if (lineItem.lineActWtKG !== undefined) {
								delete lineItem["lineActWtKG"];
							}
							if (lineItem.lineActWtLBS !== undefined) {
								delete lineItem["lineActWtLBS"];
							}
							if (lineItem.seqNum.toString().indexOf("LN") !== -1) {
								lineItem.seqNum = lineItem.seqNum.toString().replace("LN", "");
							}
							if (lineItem.contentDesc === undefined) {
								lineItem.contentDesc = {
									code : "",
									name : "",
									description : ""
								};
							} else {
								lineItem.contentDesc = {
									code : lineItem.contentDesc,
									name : lineItem.contentDesc,
									description : lineItem.contentDesc
								};
							}
							angular.forEach(lineItem.pieces, function (pieceItem) {
								if (pieceItem.$$hashKey !== undefined) {
									delete pieceItem["$$hashKey"];
								}
								if (pieceItem.seqNum !== undefined) {
									delete pieceItem["seqNum"];
								}
								//Remove the below lines when toggling between LBS to KG is done
								if (pieceItem.piecesActWtKg !== undefined) {
									pieceItem.actualWeightKg = angular.copy(pieceItem.piecesActWtKg);
									delete pieceItem["piecesActWtKg"];
								}
								if (pieceItem.piecesActWtLbs !== undefined) {
									delete pieceItem["piecesActWtLbs"];
								}
								if (pieceItem.displayName.toString().indexOf("PCID") === -1) {
									pieceItem.displayName = "PCID " + pieceItem.displayName.toString();
								}
							});
						});
					});
					return $scope.tempRequestInfoSave;
				};
				/* @CodeReviewFix:03-2016 : End :*/
				/**
				 * Save the routing info details by invoking saveRoutingInfoData of manageAWBService
				 *
				 */
				$scope.saveRoutingInfo = function () {
					/* @CodeReviewFix:03-2016 : Start : removed all the delete calls and implemented data maapers for Save Route Info     */
					$scope.tempRequestInfo = {};
					$scope.tempRequestInfo.routing = [];
					var tempGeneratedLots = angular.copy($scope.getLots.routedLot);
					angular.forEach(tempGeneratedLots, function (lotItem) {
						var chkForBlankPiecesInLine = [];
						for (var a = 0; a < lotItem.lines.length; a++) {
							if (lotItem.lines[a].noOfPieces > 0) {
								chkForBlankPiecesInLine.push(angular.copy(lotItem.lines[a]));
							}
						}
						lotItem.lines = angular.copy(chkForBlankPiecesInLine);
						var requestLotObj = new RoutingLot(lotItem);
						$scope.tempRequestInfo.routing.push(requestLotObj);
					});
					$scope.tempRequestInfo.awbNumber = manageAwbCntrl.headerData.awbNumber;
					$scope.tempRequestInfo.awbPrefix = $scope.configConstants.AWB_PREFIX_NO; //@CodeReviewFix:03-2016  moved hard coding to constants file
					$scope.tempRequestInfo.awbVersion = ((manageAwbCntrl.updatedVersionNumber < manageAwbCntrl.awbContent.versionNumber) ? manageAwbCntrl.awbContent.versionNumber : manageAwbCntrl.updatedVersionNumber); //@CodeReviewFix:03-2016  fixed code not to read rootscope
					$rootScope.$broadcast('displayLoadingBar');
					manageAWBService.saveRoutingInfoData(JSON.stringify($scope.tempRequestInfo)).then(function (responseData) {
						$rootScope.$broadcast('hideLoadingBar');
						manageAwbCntrl.isAWBAutoSave = responseData.data.awbedit;
						manageAwbCntrl.updatedVersionNumber = responseData.data.airwaybill.versionNumber;
						if (responseData.data.errorMessage === null) {
							for (var cnt = 0; cnt < responseData.data.airwaybill.routeInfo.lots.length; cnt++) {
								for (var ct2 = 0; ct2 < $scope.getLots.routedLot.length; ct2++) {
									if (responseData.data.airwaybill.routeInfo.lots[cnt].lotNumber === $scope.getLots.routedLot[ct2].lotNumber) {
										$scope.getLots.routedLot[ct2].currentLotStatus = angular.copy(responseData.data.airwaybill.routeInfo.lots[cnt].currentLotStatus);
										$scope.getLots.routedLot[ct2].lotStatusStation = angular.copy(responseData.data.airwaybill.routeInfo.lots[cnt].lotStatusStation);
									}
								}
							}
							$scope.updateAllArrays();
							$timeout(function () {
								var whetherHeaderGreen = true;
								for (var chkAllLots = 0; chkAllLots < $scope.getLots.routedLot.length; chkAllLots++) {
									if ($scope.getLots.routedLot[chkAllLots].routingInvalid === true) {
										whetherHeaderGreen = false;
										break;
									}
								}
								if (whetherHeaderGreen) {
									manageAwbCntrl.displayDivErrorBarRt = false;
									manageAwbCntrl.dispAccHdrErrorRt = false;
									manageAwbCntrl.fieldNameRt = "";
								} else {
									manageAwbCntrl.displayDivErrorBarRt = true;
									manageAwbCntrl.dispAccHdrErrorRt = true;
									manageAwbCntrl.fieldNameRt = "";
								}
							}, 100);
							/* @CodeReviewFix:03-2016 : Start : code to handle close operation    */
							// Code Fixes for #5205 and 5220 - Removed Unnecessary Check
							if (manageAwbCntrl.closeOrsaveAwb == routingDirectiveConstants.CLOSE_BUTTON && $scope.isAWBAutoSave != 'TRUE') {
								manageAwbCntrl.closeAwbPage();
							}
							/* @CodeReviewFix:03-2016  : End   */
						} else {
							if (responseData.data.errorCode == routingDirectiveConstants.ERR_CODE_CONCURRENT_UPDATE) {
								manageAwbCntrl.showConcurrent(responseData.data.errorMessage);
							} else {
								$scope.displayMessage = responseData.data.errorMessage;
							}
							$scope.openModalDialogueError();
						}
					});
				};
				/**
				 * Select route for a lot
				 *
				 * @param {String} screen name
				 * @param {Lots} lots information
				 * @param {action} - action which triggered this invocation
				 *
				 */
				$scope.selectRoute = function (screenName, dataParam, action) {
					//Checking the Route id entered in the #RTE is valid or not
					/* @CodeReviewFix:03-2016 : Start : removed checkawbIsLocked() calls */
					var allRoutesValid = true;
					for (var x = 0; x < $scope.routeNumbersToBeValidated.length; x++) {
						if (screenName !== "shipment" && !$scope.isCaseOfSplittingROuted) {
							allRoutesValid = $scope.checkRouteValidity($scope.routeNumbersToBeValidated[x].routeId);
						}
						if (!allRoutesValid) {
							break;
						}
					}
					//If not valid, make section red
					if (!allRoutesValid) {
						manageAwbCntrl.displayDivErrorBarRt = true;
						manageAwbCntrl.dispAccHdrErrorRt = true;
						manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG;
					} else { //If valid, generate the request for select route
						manageAwbCntrl.displayDivErrorBarRt = undefined;
						manageAwbCntrl.dispAccHdrErrorRt = undefined;
						manageAwbCntrl.fieldNameRt = "";
						$scope.tempRequest = {};
						var getTheCorrectRoutedLot = [];
						angular.forEach($scope.getLots.routedLot, function (initData) {
							for (var i = 0; i < $scope.routeNumbersToBeValidated.length; i++) {
								if ($scope.doReRouting) {
									if (initData.lotNumber === $scope.selectedLotNbr && initData.lotNumber === $scope.routeNumbersToBeValidated[i].lotId) {
										var tmp1 = angular.copy(initData);
										tmp1.flightLegs = angular.copy($scope.routeNumbersToBeValidated[i].flightLegs);
										getTheCorrectRoutedLot.push(tmp1);
									}
								} else {
									if (initData.lotNumber === $scope.routeNumbersToBeValidated[i].lotId) {
										if (screenName == 'shipment') {
											var tmp2 = angular.copy(initData);
											//tmp2.flightLegs = angular.copy($scope.routeNumbersToBeValidated[i].flightLegs);
											getTheCorrectRoutedLot.push(tmp2);
										} else if ($scope.routeNumbersToBeValidated[i].routeId || $scope.isCaseOfSplittingROuted) {
											var tmp2 = angular.copy(initData);
											tmp2.flightLegs = angular.copy($scope.routeNumbersToBeValidated[i].flightLegs);
											getTheCorrectRoutedLot.push(tmp2);
										}
									}
								}
							}
						});
						/* @CodeReviewFix:03-2016 : Start : removed all the delete calls and implemented data maapers for Select Route request    */
						$scope.tempRequest = {};
						$scope.tempRequest.routedLot = [];
						var tempGeneratedLots = angular.copy(getTheCorrectRoutedLot);
						angular.forEach(tempGeneratedLots, function (lotItem) {
							var chkForBlankPiecesInLine = [];
							for (var a = 0; a < lotItem.lines.length; a++) {
								if (lotItem.lines[a].noOfPieces > 0) {
									chkForBlankPiecesInLine.push(angular.copy(lotItem.lines[a]));
								}
							}
							lotItem.lines = angular.copy(chkForBlankPiecesInLine);
							if ($scope.doReRouting) {
								lotItem.reroutingReason = {
									code : $scope.reRoutingReason,
									name : $scope.reRoutingReason,
									description : $("#ddRerouteReason button span")[0].innerHTML
								};
							}
							var requestLotObj = new RoutingLot(lotItem);
							$scope.tempRequest.routedLot.push(requestLotObj);
						});
						if ($scope.tempRequest.lots !== undefined) {
							delete $scope.tempRequest["lots"];
						}
						$scope.tempRequest.awbNumber = manageAwbCntrl.headerData.awbNumber;
						$scope.tempRequest.awbPrefix = $scope.configConstants.AWB_PREFIX_NO;
						$scope.tempRequest.internalCall = false;
						$rootScope.$broadcast('displayLoadingBar');
						$scope.tempRequest.awbVersion = ((manageAwbCntrl.updatedVersionNumber < manageAwbCntrl.awbContent.versionNumber) ? manageAwbCntrl.awbContent.versionNumber : manageAwbCntrl.updatedVersionNumber);
						if (screenName == 'shipment' && action == routingDirectiveConstants.SAVE_BUTTON) {
							$scope.tempRequest = {};
							$scope.tempRequest.awbNumber = manageAwbCntrl.headerData.awbNumber;
							$scope.tempRequest.awbPrefix = $scope.configConstants.AWB_PREFIX_NO;
							$scope.tempRequest.awbVersion = ((manageAwbCntrl.updatedVersionNumber < manageAwbCntrl.awbContent.versionNumber) ? manageAwbCntrl.awbContent.versionNumber : manageAwbCntrl.updatedVersionNumber);
							$scope.tempRequest.routedLot = [];
							$scope.tempRequest.routedLot = angular.copy(dataParam.lots);
							$scope.tempRequest.internalCall = true;
						}
						//invoke manageAWb selectRoute call
						manageAWBService.selectRoute(JSON.stringify($scope.tempRequest)).then(function (responseData) {
							$scope.disableResetLink = true;
							if (responseData.data.awbStatus) {
								manageAwbCntrl.setAWBHeaderStatus(responseData.data.awbStatus);
							}
							//Version number check
							var isValid = responseData.data && responseData.data.awbVersion > 0;
							manageAwbCntrl.updatedVersionNumber = isValid ? responseData.data.awbVersion : manageAwbCntrl.updatedVersionNumber;
							$rootScope.$broadcast('hideLoadingBar');
							if ((responseData.data.errorMessage === null || responseData.data.errorMessage === undefined) && responseData.data.errorCode !== routingDirectiveConstants.ERR_CODE_CNCT_HELP_DESK) {
								$rootScope.isRoutingSave = false;
								for (var ltRtCntr = 0; ltRtCntr < $scope.routeNumbersToBeValidated.length; ltRtCntr++) {
									var allowRouteValid = true;
									if ($scope.routeNumbersToBeValidated[ltRtCntr].routeId !== undefined && screenName !== "shipment") {
										allowRouteValid = $scope.checkRouteValidity($scope.routeNumbersToBeValidated[ltRtCntr].routeId);
									}
									if (allowRouteValid) {
										//Fetch the response from web service
										if (responseData.data.routedLot !== undefined) {
											for (var ltCntr1 = 0; ltCntr1 < $scope.getLots.routedLot.length; ltCntr1++) {
												for (var ltCntr2 = 0; ltCntr2 < responseData.data.routedLot.length; ltCntr2++) {
													if ($scope.getLots.routedLot[ltCntr1].lotNumber === responseData.data.routedLot[ltCntr2].lotNumber) {
														$scope.getLots.routedLot[ltCntr1].noOfPieces = responseData.data.routedLot[ltCntr2].noOfPieces;
														$scope.getLots.routedLot[ltCntr1].actualWeight = responseData.data.routedLot[ltCntr2].actualWeight;
														$scope.getLots.routedLot[ltCntr1].volume = responseData.data.routedLot[ltCntr2].volume;
														$scope.getLots.routedLot[ltCntr1].lotStatusStation = responseData.data.routedLot[ltCntr2].lotStatusStation;
														$scope.getLots.routedLot[ltCntr1].currentLotStatus = responseData.data.routedLot[ltCntr2].currentLotStatus;
														$scope.getLots.routedLot[ltCntr1].versionNumber = responseData.data.routedLot[ltCntr2].versionNumber;
														$scope.getLots.routedLot[ltCntr1].routingInvalid = responseData.data.routedLot[ltCntr2].routingInvalid;
														$scope.getLots.routedLot[ltCntr1].samePlaneInd = responseData.data.routedLot[ltCntr2].hasOwnProperty("samePlaneInd") ? responseData.data.routedLot[ltCntr2].samePlaneInd : false;
														if (responseData.data.routedLot[ltCntr2].assignedRunner !== undefined) {
															$scope.getLots.routedLot[ltCntr1].assignedRunner = responseData.data.routedLot[ltCntr2].assignedRunner;
														}
														if (responseData.data.routedLot[ltCntr2].wareHouseLocation !== undefined) {
															$scope.getLots.routedLot[ltCntr1].wareHouseLocation = responseData.data.routedLot[ltCntr2].wareHouseLocation;
														}
														if (responseData.data.routedLot[ltCntr2].deliveredToCustomer !== undefined) {
															$scope.getLots.routedLot[ltCntr1].deliveredToCustomer = responseData.data.routedLot[ltCntr2].deliveredToCustomer;
														}
														$scope.getLots.routedLot[ltCntr1].flightLegs = [];
														for (var flt2 = 0; flt2 < responseData.data.routedLot[ltCntr2].flightLegs.length; flt2++) {
															var fromRespFlts = {};
															fromRespFlts.carrier = responseData.data.routedLot[ltCntr2].flightLegs[flt2].carrierCode.code;
															fromRespFlts.destination = responseData.data.routedLot[ltCntr2].flightLegs[flt2].destination.code;
															fromRespFlts.origin = responseData.data.routedLot[ltCntr2].flightLegs[flt2].origin.code;
															fromRespFlts.estimatedArrivalTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estArrTime;
															fromRespFlts.estimatedDepartureTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estDepTime;
															fromRespFlts.estimatedArrivalDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estimatedArrDate;
															fromRespFlts.estimatedDepartureDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estimatedDepDate;
															fromRespFlts.scheduledArrivalDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedArrDate;
															fromRespFlts.scheduledDepartureDate = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedDepDate;
															fromRespFlts.scheduledArrivalTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].scheduledArrTime;
															fromRespFlts.scheduledDepartureTime = responseData.data.routedLot[ltCntr2].flightLegs[flt2].scheduledDepTime;
															fromRespFlts.schedDepDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedDepDateTimeLocal;
															fromRespFlts.schedArrDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].schedArrDateTimeLocal;
															fromRespFlts.estArrDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estArrDateTimeLocal;
															fromRespFlts.origArrunk = responseData.data.routedLot[ltCntr2].flightLegs[flt2].origArrunk;
															fromRespFlts.destArrunk = responseData.data.routedLot[ltCntr2].flightLegs[flt2].destArrunk;
															fromRespFlts.estDepDateTimeLocal = responseData.data.routedLot[ltCntr2].flightLegs[flt2].estDepDateTimeLocal;
															fromRespFlts.flightLegKey = responseData.data.routedLot[ltCntr2].flightLegs[flt2].flightLegKey;
															fromRespFlts.forceBooking = responseData.data.routedLot[ltCntr2].flightLegs[flt2].forceBooking;
															fromRespFlts.flightNumber = responseData.data.routedLot[ltCntr2].flightLegs[flt2].flightNumber;
															fromRespFlts.seqNum = responseData.data.routedLot[ltCntr2].flightLegs[flt2].seqNum;
															$scope.getLots.routedLot[ltCntr1].flightLegs.push(fromRespFlts);
														}
													}
												}
											}
										}
										$scope.calculateFlightLegDetails($scope.getLots.routedLot);
										$scope.updateAllArrays();
										$scope.updateRoutingHeader();
									} else {
										//Display the error message in front of the lot
										manageAwbCntrl.displayDivErrorBarRt = true;
										manageAwbCntrl.dispAccHdrErrorRt = true;
										manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG;
										globalErrorHandlingServices.insertRoutingErrorMessage({
											routeNotAvailable : routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG
										});
									}
								}
								if (!airwayBillDataService.checkIfAnyLotNonRouted($scope.getLots.routedLot)) {
									//Remove the error message from route panel header
									var whetherHeaderGreen = true;
									for (var chkAllLots = 0; chkAllLots < $scope.getLots.routedLot.length; chkAllLots++) {
										if ($scope.getLots.routedLot[chkAllLots].routingInvalid === true) {
											whetherHeaderGreen = false;
											break;
										}
									}
									if (whetherHeaderGreen) {
										manageAwbCntrl.displayDivErrorBarRt = false;
										manageAwbCntrl.dispAccHdrErrorRt = false;
										manageAwbCntrl.fieldNameRt = "";
									} else {
										manageAwbCntrl.displayDivErrorBarRt = true;
										manageAwbCntrl.dispAccHdrErrorRt = true;
										manageAwbCntrl.fieldNameRt = "";
									}
									$scope.searchRoutePanel = false;
									$scope.routesSummary = false;
									$scope.lineTextBox = false;
									$scope.pieceTextBox = false;
									$scope.lineRadio = true;
									$scope.pieceRadio = true;
									$scope.headerRadio = true;
									$scope.hdrRouteNo = false;
									//$("#routingBody").attr("style", "display:none;");
									$scope.enableButtons = true;
									$scope.enableButtonsSplit = true;
									var checkWhetherRouteOrReroute = false;
									angular.forEach($scope.getLots.routedLot, function (lotItem) {
										if (lotItem.fltNbr === undefined) {
											checkWhetherRouteOrReroute = true;
										}
									});
									if (checkWhetherRouteOrReroute && $scope.isReRouteState === false) {
										$scope.areLotsRouted = false;
									} else {
										$scope.areLotsRouted = true;
									}
									$scope.isRouteFirstTime = false;
									if (whetherHeaderGreen && $localStorage.isSearchPageCall === true) {
										manageAwbCntrl.updatedVersionNumber = responseData.data.awbVersion;
									}
									if (whetherHeaderGreen && !manageAwbCntrl.shouldAllowSaveShipment && screenName === 'shipment') {
										var routedLot = angular.copy($scope.getLots.routedLot);
										angular.forEach(manageAwbCntrl.getSaveShipmentData.routedLot, function (routeItem, routeIndex) {
											angular.forEach(routeItem.flightLegs, function (flightLegItem, flightLegIndex) {
												if (flightLegItem.estArrDateTimeLocal === undefined) {
													flightLegItem.estArrDateTimeLocal = routedLot[routeIndex].flightLegs[flightLegIndex].estArrDateTimeLocal;
												}
												if (flightLegItem.origArrunk === undefined) {
													flightLegItem.origArrunk = routedLot[routeIndex].flightLegs[flightLegIndex].origArrunk;
												}
												if (flightLegItem.destArrunk === undefined) {
													flightLegItem.destArrunk = routedLot[routeIndex].flightLegs[flightLegIndex].destArrunk;
												}
												if (flightLegItem.estDepDateTimeLocal === undefined) {
													flightLegItem.estDepDateTimeLocal = routedLot[routeIndex].flightLegs[flightLegIndex].estDepDateTimeLocal;
												}
												if (flightLegItem.schedArrDateTimeLocal === undefined) {
													flightLegItem.schedArrDateTimeLocal = routedLot[routeIndex].flightLegs[flightLegIndex].schedArrDateTimeLocal;
												}
												if (flightLegItem.schedDepDateTimeLocal === undefined) {
													flightLegItem.schedDepDateTimeLocal = routedLot[routeIndex].flightLegs[flightLegIndex].schedDepDateTimeLocal;
												}
												if (flightLegItem.seqNum === undefined) {
													flightLegItem.seqNum = flightLegIndex;
												}
											});
										});
										/* @CodeReviewFix:03-2016 : Start : Removed the saveShipmentDetails related service calls*/
										$timeout(function () {
											if (airwayBillDataService.checkIfAnyLotNonRouted($scope.getLots.routedLot)) {
												manageAwbCntrl.toPutNoOfPiecesInFocus = true;
												$scope.searchRoutePanel = true;
												$scope.routesSummary = true;
												routingScope.areLotsRouted = false;
												// angular.forEach(routingScope.getLots.routedLot, function(lotItem) {
												// if (lotItem.fltNbr !== undefined || (!lotItem.flightLegs && (lotItem.flightLegs && lotItem.flightLegs.length === 0))) {
												// $("#lineRadioWithId" + lotItem.lotNumber.toString()).addClass("ng-hide");
												// $("#lineTextBoxWithId" + lotItem.lotNumber.toString()).removeClass("ng-hide");
												// $("#pieceRadioWithId" + lotItem.lotNumber.toString()).addClass("ng-hide");
												// $("#pieceTextBoxWithId" + lotItem.lotNumber.toString()).removeClass("ng-hide");
												// }
												// });
											} else if (manageAwbCntrl.editingSingleLineWithNoPieces === true) {
												manageAwbCntrl.toPutNoOfPiecesInFocus = true;
												$scope.searchRoutePanel = true;
												$scope.routesSummary = true;
												routingScope.areLotsRouted = false;
												manageAwbCntrl.displayDivErrorBarRt = true;
												manageAwbCntrl.dispAccHdrErrorRt = true;
												manageAwbCntrl.fieldNameRt = "";
											}
										}, 100);
										$rootScope.changeOrNotManageawb = false;
									}
									if (!$scope.doReRouting) {
										if ($scope.ifLotContainsRouteInfo !== true && screenName === "routing") {
											var tempPanelCntr = $("#routingAcrdn").next();
											$scope.navToNextSection('', '', true);
										}
									} else {
										$scope.addCommentObject();
									}
									$timeout(function () { //@CodeReviewFix:03-2016  Changed from setTimeOut
										if ($scope.doReRouting) {
											$scope.doReRouting = false;
											angular.forEach($scope.getLots.routedLot, function (routeItem) {
												if ($("#plusMinusLines" + routeItem.lotNumber.toString()).hasClass("minus")) {
													$(".trLineClass" + routeItem.lotNumber.toString()).removeClass("ng-hide");
												}
											});
										} else {
											angular.forEach($scope.routeNumbersToBeValidated, function (routeNumberItem) {
												if ($("#plusMinusLines" + routeNumberItem.lotId.toString()).hasClass("minus")) {
													$(".trLineClass" + routeNumberItem.lotId.toString()).removeClass("ng-hide");
													if ($scope.ifLotContainsRouteInfo === true) {
														$(".clubbedLine" + routeNumberItem.lotId.toString()).removeClass("ng-hide");
													}
												}
											});
										}
									}, 100);
									// Code Fixes for #5205 and 5220 Removed Unnecessary Check
									if (manageAwbCntrl.closeOrsaveAwb == routingDirectiveConstants.CLOSE_BUTTON && $scope.isAWBAutoSave != 'TRUE') {
										manageAwbCntrl.closeAwbPage();
									}
								} else {
									$scope.nextUnroutedLot = 0;
									angular.forEach($scope.getLots.routedLot, function (lotItem) {
										if (!lotItem.flightLegs && $scope.nextUnroutedLot === 0) {
											$scope.nextUnroutedLot = lotItem.lotNumber;
										}
									});
									$timeout(function () {
										$scope.assignFocusToLots();
									}, 100);
								}
								$timeout(function () {
									angular.forEach($scope.getLots.routedLot, function (routeItem) {
										if ($("#plusMinusLines" + routeItem.lotNumber.toString()).hasClass("minus")) {
											$(".trLineClass" + routeItem.lotNumber.toString()).removeClass("ng-hide");
										}
									});
								}, 100)
								$rootScope.routingData.lots = angular.copy($scope.getLots.routedLot);
								if (shipmentDetailsScope && shipmentDetailsScope.generatedLots) {
									shipmentDetailsScope.generatedLots.routedLot = angular.copy($scope.getLots.routedLot);
								}
							} else if (responseData.data.errorCode === routingDirectiveConstants.ERR_CODE_NO_RTE_AVAILABLE) {
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
								manageAwbCntrl.fieldNameRt = routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG;
								globalErrorHandlingServices.insertRoutingErrorMessage({
									routeNotAvailable : routingDirectiveConstants.SEL_RTE_NOT_AVAIL_MSG
								});
								//opearion to invoke route search
								$scope.performRouteSearch(routingDirectiveConstants.ONE_ROUTE);
							} else if (responseData.data.errorCode === routingDirectiveConstants.ERR_CODE_CNCT_HELP_DESK) {
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
								manageAwbCntrl.fieldNameRt = responseData.data.errorMessage;
							} else if (responseData.data.errorCode == routingDirectiveConstants.ERR_CODE_CONCURRENT_UPDATE) {
								manageAwbCntrl.showConcurrent(responseData.data.errorMessage);
							} else {
								if ($scope.isCaseOfSplittingROuted) {
									$scope.getLots = angular.copy($scope.getLotsForReset);
									$scope.updateAllArrays();
								}
								//for lots which fligh legs not assoiated, remove the flightLegs details
								angular.forEach($scope.getLots.routedLot, function (lotItem) {
									for (var q = 0; q < $scope.tempRequest.routedLot.length; q++) {
										if (lotItem.lotNumber === $scope.tempRequest.routedLot[q].lotNumber) {
											if (lotItem.fltNbr === undefined) {
												delete lotItem.flightLegs;
											}
										}
									}
								});
								manageAwbCntrl.displayDivErrorBarRt = true;
								manageAwbCntrl.dispAccHdrErrorRt = true;
								manageAwbCntrl.fieldNameRt = responseData.data.errorMessage;
								$scope.enableButtons = true;
								$scope.enableButtonsSplit = true;
							}
						});
					}
				};
				/**
				Add Comment on comment section after reroute
				 **/
				$scope.addCommentObject = function () {
					var commentObj = {};
					commentObj.comment = routingScope.reRouteReasonDescrition;
					commentObj.updatedBy = {};
					commentObj.updatedBy.userId = $localStorage.userObject.userId;
					commentObj.updatedBy.firstName = $localStorage.userObject.firstName;
					commentObj.updatedBy.lastName = $localStorage.userObject.lastName;
					commentObj.localDate = $rootScope.currentDate + ' ' + $rootScope.currentTime + ' ' + $rootScope.timezone;
					commentObj.updatedDateTime = $rootScope.currentDate + ' ' + $rootScope.currentTime + ' ' + $rootScope.timezone;
					$rootScope.commentsData.unshift(commentObj);
					manageAwbCntrl.dispAccHdrErrorCom = false;
					manageAwbCntrl.displayDivErrorBarCom = false;
				}
				/**
				 * Enable and disable radio based on the lot route status
				 * @param {String} - lot number
				 */
				$scope.routeRadioClicked = function (lotId) {
					$scope.currentSelectedLot = lotId.values.lotNumber;
					$scope.selectedLotNbr = lotId.values.lotNumber;
					if ((shipmentDetailsScope === '' && manageAwbCntrl.displayDivErrorBarSd === false) || shipmentDetailsScope.displayDivErrorBarSd === false) {
						$scope.enableRouteButton = false;
					} else {
						$scope.enableRouteButton = true;
					}
					$timeout(function () {
						$scope.areLotsRouted = true;
						if (manageAwbCntrl.headerData.awbStatus !== undefined && (manageAwbCntrl.headerData.awbStatus.toUpperCase() === routingDirectiveConstants.AWB_STATUS_CANCELLED || manageAwbCntrl.headerData.awbStatus.toUpperCase() === routingDirectiveConstants.AWB_STATUS_CXLD || manageAwbCntrl.headerData.awbStatus.toUpperCase() === routingDirectiveConstants.AWB_STATUS_VOID)) {
							$scope.enableRouteButton = true;
						}
					}, 10);
					angular.forEach($scope.getLots.routedLot, function (routeItem) {
						if (!routeItem.flightLegs || (routeItem.flightLegs && routeItem.flightLegs.length === 0)) {
							angular.element('#lotInputField' + routeItem.lotNumber).scope().enteredRouteNumber = '';
							$('#btnDoReRouting').text() === 'Route';
						}
					});
				};
				/**
				 * Clear the Radio button selected in the Routing Section
				 *
				 *@param {}
				 */
				$scope.clearRadioSelected = function (routeItem) {
					$scope.currentSelectedLot = routeItem.lotNumber;
					angular.forEach($scope.getLots.routedLot, function (routeItem) {
						if (routeItem.flightLegs && (routeItem.flightLegs && routeItem.flightLegs.length > 0)) {
							angular.element('#lotRadioButton' + routeItem.lotNumber).scope().checkLotNumber = false;
							if (!$scope.isReRouteState) {
								$scope.areLotsRouted = false;
								$scope.selectedLotNbr = 0;
							}
						}
					});
				};
				/**
				 * Reset the lots for resetting the split lot scenario
				 *
				 *@param {}
				 */
				$scope.resetLots = function () {
					// @TODO Make the web service call to re fetch the older data
					if ($scope.getLots.routedLot.length > 1) {
						$scope.getLots = angular.copy($scope.getLotsForReset);
						$scope.updateAllArrays();
						$timeout(function () {
							if ($("#plusMinusLines" + $scope.lotNoSelected.toString()).hasClass("minus")) {
								$("#lineTable" + $scope.lotNoSelected.toString() + " tbody").removeClass("ng-hide");
							}
						}, 10);
					}
					$rootScope.routingData.lots = angular.copy($scope.getLots.routedLot);
					$scope.disableResetLink = true;
				};

				/**
				 * Reset the Re-Route state and assign original values
				 *
				 *@param {}
				 */
				$scope.resetReRouteState = function (type) {
					$scope.initializeGetLots(type);
					$scope.isReRouteState = false;
					$scope.isRouteFirstTime = false;
					$scope.updateRoutingHeader();
					$timeout(function () {
						if ($("#plusMinusLines" + $scope.lotNoSelected.toString()).hasClass("minus")) {
							$("#lineTable" + $scope.lotNoSelected.toString() + " tbody").removeClass("ng-hide");
						}
					}, 10);
					if (type !== 'split') {
						$scope.assignFocusToLots();
					}
				}

				/**
				 * calculate the flight leg details to display in the routing screen
				 *
				 * @param {lots}
				 *
				 */
				$scope.calculateFlightLegDetails = function (lots) {
					angular.forEach(lots, function (lotItem) {
						if (lotItem.flightLegs && lotItem.flightLegs.length > 0) {
							var chkFlightNo = true;
							angular.forEach(lotItem.flightLegs, function (flightlegItem, flightLegIndex) {
								if (flightLegIndex === lotItem.flightLegs.length - 1) {
									if (lotItem.flightLegs[0].flightNumber !== flightlegItem.flightNumber) {
										chkFlightNo = false;
									}
								} else {
									if (flightlegItem.flightNumber !== lotItem.flightLegs[flightLegIndex + 1].flightNumber) {
										chkFlightNo = false;
									}
								}
							});
							//if its multi leg then display flight number as *
							if (!chkFlightNo && lotItem.flightLegs.length > 1) {
								lotItem.fltNbr = "*";
							} else {
								lotItem.fltNbr = lotItem.flightLegs[0].flightNumber;
							}
							//origin is first leg origin
							if (lotItem.flightLegs[0].origin) {
								lotItem.lotsOrig = lotItem.flightLegs[0].origin.code ? lotItem.flightLegs[0].origin.code : lotItem.flightLegs[0].origin;
							} else {
								lotItem.lotsOrig = '';
							}
							//identify if single leg or multi leg and set the proper leg information to be displayed.
							lotItem.destination = lotItem.flightLegs[lotItem.flightLegs.length - 1].destination.code !== undefined ? lotItem.flightLegs[lotItem.flightLegs.length - 1].destination.code : lotItem.flightLegs[lotItem.flightLegs.length - 1].destination;
							lotItem.scheduledDepartureDate = lotItem.flightLegs[0].scheduledDepDate !== undefined ? lotItem.flightLegs[0].scheduledDepDate : lotItem.flightLegs[0].scheduledDepartureDate;
							lotItem.scheduledArrivalTime = lotItem.flightLegs[lotItem.flightLegs.length - 1].scheduledArrTime !== undefined ? lotItem.flightLegs[lotItem.flightLegs.length - 1].scheduledArrTime : lotItem.flightLegs[lotItem.flightLegs.length - 1].scheduledArrivalTime;
							lotItem.estimatedArrivalTime = lotItem.flightLegs[lotItem.flightLegs.length - 1].estimatedArrTime !== undefined ? lotItem.flightLegs[lotItem.flightLegs.length - 1].estimatedArrTime : lotItem.flightLegs[lotItem.flightLegs.length - 1].estimatedArrivalTime;
							lotItem.scheduledDepartureTime = lotItem.flightLegs[0].scheduledDepartureTime;
							lotItem.scheduledArrivalDate = lotItem.flightLegs[0].scheduledArrivalDate;
						}
					});
					$scope.getLots.routedLot = angular.copy(lots);
				};
				//Defect #4809 Fixes
				/**
				 * It create string with comma seperated list of SHC code that is displayed at Lot Level
				 *
				 * @params {lotItem} - Contains lot information
				 *
				 */
				$scope.caculateSHCCode = function (lotItem) {
					var mergedSHCCode = '';
					angular.forEach(lotItem.lines, function (lineItem) {
						if (lineItem.lineSHC) {
							angular.forEach(lineItem.lineSHC.split(','), function (shcItem) {
								if (mergedSHCCode.indexOf(shcItem.trim()) < 0) {
									if (mergedSHCCode === '') {
										mergedSHCCode = shcItem;
									} else {
										mergedSHCCode = mergedSHCCode + ', ' + shcItem;
									}
								}
							});
						}
					});
                    //Handled null check for merged SHC. If it is null, make it empty
					if (!mergedSHCCode || mergedSHCCode === 'null') {
						mergedSHCCode = '';
					}
					return mergedSHCCode;
				};
				//Defect #4809 Fixes
				/**
				 * This section initializes the routing section when opened
				 *
				 * @params {}
				 *
				 */
				$scope.initializeGetLots = function (type) {
					//Defect #5075 Fixes
					$scope.actualWeightUnit = routingDirectiveConstants.defaultWeightUnit;
					$scope.displayWeightLBS = true;
					//Defect #5075 Fixes
					$scope.getLots = {};
					if (type !== 'split') {
						$scope.selectedLotNbr = 0;
					}
					$scope.specifyRoute = false;
					if ($rootScope.awbContent.prefFlightNumbers !== undefined) {
						var dispPrefFlights = "";
						for (var pflt = 0; pflt < $rootScope.awbContent.prefFlightNumbers.length; pflt++) {
							dispPrefFlights = dispPrefFlights + $rootScope.awbContent.prefFlightNumbers[pflt] + ",";
						}
						dispPrefFlights = dispPrefFlights.substring(0, dispPrefFlights.length - 1);
						$scope.preferredFlights = dispPrefFlights;
					}
					if ($rootScope.routingData) {
						$scope.getLotsForSearch = angular.copy($rootScope.routingData.lots);
					}
					var rowTypeCntr = 0;
					var appndUnqLineNo = 1;
					angular.forEach($scope.getLotsForSearch, function (lotItem) { //@CodeReviewFix:03-2016  replaced with angular.forEach
						//assign row type for style handling
						if (rowTypeCntr % 2 === 0) {
							$scope.getLotsForSearch.rowType = routingDirectiveConstants.ODD_ROW;
						} else {
							$scope.getLotsForSearch.rowType = routingDirectiveConstants.EVEN_ROW;
						}
						if (lotItem.lines[0] != undefined) {
							if (lotItem.lines[0].contentDesc && typeof(lotItem.lines[0].contentDesc) === "object") {
								lotItem.contentDesc = lotItem.lines[0].contentDesc.code;
							} else {
								lotItem.contentDesc = lotItem.lines[0].contentDesc;
							}
						}
						//initialize special handling code and content description
						lotItem.contentDesc = lotItem.contentDesc !== undefined ? lotItem.contentDesc : "";
						angular.forEach(lotItem.lines, function (lineItem) {
							var shcList = "";
							if (lineItem.specialHandlingCode !== undefined && lineItem.specialHandlingCode !== null) {
								for (var ct = 0; ct < lineItem.specialHandlingCode.length; ct++) {
									if (lineItem.specialHandlingCode[ct].code !== "") {
										shcList = shcList + lineItem.specialHandlingCode[ct].code + ",";
									}
								}
								shcList = shcList.substring(0, shcList.length - 1);
							}
							if (lineItem.contentDesc && typeof(lineItem.contentDesc) === "object") {
								lineItem.contentDesc = lineItem.contentDesc.code;
							} else {
								lineItem.contentDesc = lineItem.contentDesc;
							}
							lineItem.contentDesc = lineItem.contentDesc !== undefined ? lineItem.contentDesc : "";
							lineItem.lineSHC = shcList !== "" ? shcList : "";
							lineItem.lineOrig = "";
							lineItem.lineDest = "";
							lineItem.lotNumber = lotItem.lotNumber;
							if (lineItem.seqNum.toString().indexOf("LN") === -1) {
								lineItem.seqNum = "LN" + lineItem.seqNum.toString();
							}
							if (lineItem.uniqueLineNo === undefined || lineItem.uniqueLineNo === null) {
								lineItem.uniqueLineNo = appndUnqLineNo;
								appndUnqLineNo = appndUnqLineNo + 1;
							}
							if ($scope.displayWeightLBS) {
								lineItem.lineActWtLBS = lineItem.actualWeight;
							} else {
								lineItem.lineActWtKG = lineItem.actualWeight;
							}
							//assign piece ids
							angular.forEach(lineItem.pieces, function (pieceItem) {
								pieceItem.seqNum = lineItem.seqNum;
								if (pieceItem.displayName.toString().indexOf("PCID ") !== "-1") {
									pieceItem.displayName = pieceItem.displayName.toString().replace("PCID ", "");
								}
								if ($scope.displayWeightLBS) {
									pieceItem.piecesActWtLbs = pieceItem.actualWeight;
								} else {
									pieceItem.piecesActWtKg = pieceItem.actualWeight;
								}
								if (pieceItem.actualWeightKg !== undefined) {
									pieceItem.piecesActWtKg = pieceItem.actualWeightKg;
								}
							});
						});
						rowTypeCntr = rowTypeCntr + 1;
					});
					$scope.getLots.routedLot = angular.copy($scope.getLotsForSearch);
					////////Fetch the response from web service////////
					angular.forEach($scope.getLots.routedLot, function (lotItem) { //@CodeReviewFix:03-2016  replaced with angular.forEach
						lotItem.actualWeight = Math.ceil(lotItem.actualWeight);
						lotItem.volume = Math.ceil(lotItem.volume);
						if (manageAwbCntrl.headerData.Fullstatus.toUpperCase() !== routingDirectiveConstants.AWB_STATUS_CANCELLED && manageAwbCntrl.headerData.Fullstatus.toUpperCase() !== routingDirectiveConstants.AWB_STATUS_VOID) { //@CodeReviewFix:03-2016  replaced with constants
							//initialise proper flight leg information
							angular.forEach(lotItem.flightLegs, function (legItem) {
								legItem.carrier = legItem.carrierCode ? legItem.carrierCode.code : legItem.carrier;
								legItem.destination = legItem.destination.code ? legItem.destination.code : legItem.destination;
								legItem.origin = legItem.origin.code ? legItem.origin.code : legItem.origin;
								legItem.estimatedArrivalTime = legItem.estimatedArrivalTime ? legItem.estimatedArrivalTime : legItem.estArrTime;
								legItem.estimatedDepartureTime = legItem.estimatedDepartureTime ? legItem.estimatedDepartureTime : legItem.estDepTime;
								legItem.estimatedArrivalDate = legItem.estimatedArrivalDate ? legItem.estimatedArrivalDate : legItem.estimatedArrDate;
								legItem.estimatedDepartureDate = legItem.estimatedDepartureDate ? legItem.estimatedDepartureDate : legItem.estimatedDepDate;
								legItem.scheduledArrivalDate = legItem.scheduledArrivalDate ? legItem.scheduledArrivalDate : legItem.schedArrDate;
								legItem.scheduledDepartureDate = legItem.scheduledDepartureDate ? legItem.scheduledDepartureDate : legItem.schedDepDate;
								legItem.scheduledArrivalTime = legItem.scheduledArrivalTime ? legItem.scheduledArrivalTime : legItem.scheduledArrTime;
								legItem.scheduledDepartureTime = legItem.scheduledDepartureTime ? legItem.scheduledDepartureTime : legItem.scheduledDepTime;
							});
						}
					});
					$scope.calculateFlightLegDetails($scope.getLots.routedLot);
					//update all the lines and pieces details
					$scope.updateAllArrays();
					if (airwayBillDataService.checkIfLotsRouted($scope.getLots.routedLot)) { //All lots are routed
						var whetherHeaderGreen = true;
						for (var chkAllLots = 0; chkAllLots < $scope.getLots.routedLot.length; chkAllLots++) {
							if ($scope.getLots.routedLot[chkAllLots].routingInvalid === true) {
								whetherHeaderGreen = false;
								break;
							}
						}
						if (whetherHeaderGreen && (manageAwbCntrl.displayDivErrorBarRt !== true)) {
							manageAwbCntrl.displayDivErrorBarRt = false;
							manageAwbCntrl.dispAccHdrErrorRt = false;
							manageAwbCntrl.fieldNameRt = "";
						} else {
							manageAwbCntrl.displayDivErrorBarRt = true;
							manageAwbCntrl.dispAccHdrErrorRt = true;
							manageAwbCntrl.fieldNameRt = "";
						}
						$scope.searchRoutePanel = false;
						$scope.routesSummary = false;
						if (manageAwbCntrl.isRoutingByExpandAll === true) {
							manageAwbCntrl.isRoutingByExpandAll = false;
							$('.accordionWrapper .accordion:eq(4) .accordionBody').slideDown(200);
						}
						$scope.lineTextBox = false;
						$scope.pieceTextBox = false;
						$scope.lineRadio = true;
						$scope.pieceRadio = true;
						$scope.headerRadio = true;
						$scope.hdrRouteNo = false;
						$scope.enableButtons = true;
						$scope.enableButtonsSplit = true;
						$scope.areLotsRouted = true;
						$scope.disableResetLink = true;
						if ($scope.getLots.routedLot.length === 1) {
							var tmpRadioBtnSelect = {};
							tmpRadioBtnSelect.values = {};
							tmpRadioBtnSelect.values.lotNumber = 1;
							$scope.routeRadioClicked(tmpRadioBtnSelect);
						}
						$scope.assignFocusToLots();
					} else {
						if (manageAwbCntrl.headerData.Fullstatus.toUpperCase() !== routingDirectiveConstants.AWB_STATUS_CANCELLED && manageAwbCntrl.headerData.Fullstatus.toUpperCase() !== routingDirectiveConstants.AWB_STATUS_VOID) {
							$timeout(function () { //@CodeReviewFix:03-2016 changed from setTimeout
								if ($scope.checkIfLotsExist()) {
									angular.forEach($scope.getLots.routedLot, function (lotItem) {
										var chkForFlts = false;
										if (lotItem.flightLegs) {
											if (lotItem.flightLegs.length > 0) {
												chkForFlts = true;
											}
										}
										if (chkForFlts) {
											// $("#lineTextBoxWithId" + lotItem.lotNumber.toString()).addClass("ng-hide");
											// $("#lineRadioWithId" + lotItem.lotNumber.toString()).removeClass("ng-hide");
											// $("#pieceTextBoxWithId" + lotItem.lotNumber.toString()).addClass("ng-hide");
											// $("#pieceRadioWithId" + lotItem.lotNumber.toString()).removeClass("ng-hide");
										} else {
											lotItem.routingInvalid = true;
										}
									});
								}
							}, 100);
						} else {
							$scope.searchRoutePanel = false;
							$scope.routesSummary = false;
							if (manageAwbCntrl.isRoutingByExpandAll === true) {
								manageAwbCntrl.isRoutingByExpandAll = false;
								$('.accordionWrapper .accordion:eq(4) .accordionBody').slideDown(200);
							}
							$scope.lineTextBox = false;
							$scope.pieceTextBox = false;
							$scope.lineRadio = true;
							$scope.pieceRadio = true;
							$scope.headerRadio = true;
							$scope.hdrRouteNo = false;
							$scope.enableButtons = true;
							$scope.enableButtonsSplit = true;
							$scope.areLotsRouted = true;
							$timeout(function () {
								$("#btnDoReRouting").attr("disabled", "disabled");
							}, 10);
						}
					}
					if (!$scope.getLots || !$scope.getLots.routedLot || $scope.getLots.routedLot.length === 0) {
						manageAwbCntrl.assignFocusToElements("btnGetRoutes");
					}
					$scope.updateRouteWeight();
				};
				/**
				 * Code for opening re-route modal content
				 * @param {}
				 * @returns {}
				 */
				$scope.openModalDialogueReRoute = function () {
					var modalInstance = $modal.open({
							templateUrl : 'reRouteModalContent.html',
							size : 'sm',
							backdrop : 'static',
							windowClass : 'Css-Center-Modal',
							keyboard : false,
							controller : 'ReRouteModalInstanceCtrl',
							resolve : {
								selLotNo : function () {
									return $scope.selectedLotNbr;
								}
							}
						});
				};
				/**
				 * Code for modal content info html
				 * @param {}
				 * @returns {}
				 */
				$scope.openModalDialogueInfo = function () {
					var modalInstance = $modal.open({
							templateUrl : 'myModalContentInfo.html',
							size : 'sm',
							backdrop : 'static',
							windowClass : 'Css-Center-Modal',
							keyboard : false,
							controller : 'ModalInstanceCtrlInfo',
							resolve : {
								infoDisp : function () {
									return $scope.displayMessage;
								}
							}
						});
					modalInstance.result.then(function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};
				/**
				 * Code for modal content error
				 * @param {}
				 * @returns {}
				 */
				$scope.openModalDialogueError = function () {
					var modalInstance = $modal.open({
							templateUrl : 'myModalContentError.html',
							size : 'sm',
							backdrop : 'static',
							windowClass : 'Css-Center-Modal',
							keyboard : false,
							controller : 'ModalInstanceCtrlError',
							resolve : {
								infoDisp : function () {
									return $scope.displayMessage;
								}
							}
						});
					modalInstance.result.then(function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};
			}
		] // END OF CONTROLLER FUNCTION
	};
});
/**
 * Controller for handling re route scenario
 *
 *
 * @constructor
 * @param{$scope} $scope
 * @param{$modalInstance} $modalInstance
 * @param{$modal} $modal
 * @param{$log}$log
 * @param{$localStorage} localStorage
 * @param{selLotNo} $selLotNo
 * @param{$localStorage} $localStorage
 * @param{manageAWBService} manageAWBService
 * @param{$timeout} $timeout
 */
app_controllers.controller('ReRouteModalInstanceCtrl', function ($scope, $modalInstance, $modal, $log, $localStorage, selLotNo, manageAWBService, $timeout) {
	$scope.reRouteReason = "";
	$scope.routeOptions = [];
	var rerouteScopeVar = $scope;
	$scope.init = function () {
		manageAWBService.getRefDataForRoutes().then(function (responseData) {
			var referenceData = responseData.data;
			$scope.routeOptions = JSON.parse(referenceData.referenceDataHolder.reRouteReason[0].value);
			$timeout(function () {
				$('.selectpicker').selectpicker();
			}, 10);
		});
	};
	$scope.ok = function () {
		routingScope.doReRouting = true;
		routingScope.reRoutingReason = $scope.reRouteReason;
		if ($("#ddRerouteReason button span")[0]) {
			routingScope.reRouteReasonDescrition = $("#ddRerouteReason button span")[0].innerHTML;
		}
		if (routingScope.isCallFromReRouteButton) {
			routingScope.selectRoute('routing');
		} else {
			routingScope.validateRoutes('indirectvalidate');
		}
		routingScope.isReRouteState = false;
		$modalInstance.dismiss('cancel');
		$scope.isReRouteState = false;
		routingScope.isCallFromReRouteButton = false;
	};
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});
/**
 * Controller for modal info window
 *
 * @constructor
 * @param{$scope} $scope
 * @param{$modalInstance} $modalInstance
 * @param{infoDisp} infoDisp
 */
app_controllers.controller('ModalInstanceCtrlInfo', function ($scope, $modalInstance, infoDisp) {
	$scope.infoMsg = infoDisp;
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});
/**
 * Controller for modal error window
 *
 * @constructor
 * @param{$scope} $scope
 * @param{$modalInstance} $modalInstance
 * @param{infoDisp} infoDisp
 */
app_controllers.controller('ModalInstanceCtrlError', function ($scope, $modalInstance, infoDisp) {
	$scope.infoMsg = infoDisp;
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});