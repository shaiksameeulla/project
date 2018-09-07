/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/**
 * This directive captures the scope and operations for handling the Shipment
 * section of the AWB
 *
 * @constructor
 * @param{$scope} scope
 * @param{$rootScope} rootScope
 * @param{ErrorHandlingService} ErrorHandlingService
 * @param{airwayBillDataService} airwayBillDataService
 * @param{ngTableParams} ngTableParams
 * @param{$modal} $modal for popup messsage
 * @param{$localStorage} $localStorage
 * @param{manageAWBService} injecting manageAWBService
 * @param{focus} injecting focus service object
 * @param{$timeout} $timeout
 * @param{routingDirectiveConstants} routingDirectiveConstants
 * @param{ERRORMESSAGES} ERRORMESSAGES constants from errorconstants.js
 * @param{$filter} $filter service object to use filter in controller
 * @param{$element} $element
 * @param{shipmentDetailsConstants} shipmentDetailsConstants to read constants from config.js
 * @param{ShipmentDetailsErrorMsgs} ShipmentDetailsErrorMsgs to read error messages from errorconstants.js
 */
var shipmentDetailsScope = ''; //Global Scope uses across the flow
var saveShipmentScope = ''; // Temp scope for storing the final object of shipment
app.directive('awbShipmentDetails', function () { // Shipment Details directive
	return {
		restrict : 'A',
		templateUrl : '../modules/manageawb/shipmentDetails.html',
		scope : true,
		compile : function (element, attributes) {
			return {
				post : function (scope, element, attributes, controller, transcludeFn) {
					scope.init(); //Invoking init after post compilation
				}
			};
		},
		controller : ['$scope', '$rootScope', 'manageAWBService', 'ERRORMESSAGES', 'ngTableParams', 'focus', '$localStorage', 'airwayBillDataService', '$modal', '$filter', '$element', '$timeout', 'shipmentDetailsConstants', 'ShipmentDetailsErrorMsgs','CONFIGURATIONS', function ($scope, $rootScope, manageAWBService, ERRORMESSAGES, ngTableParams, focus, $localStorage, airwayBillDataService, $modal, $filter, $element, $timeout, shipmentDetailsConstants, ShipmentDetailsErrorMsgs,CONFIGURATIONS) {
				//Declaration
				shipmentDetailsScope = $scope; //Global scope
				$scope.erroMsg = ERRORMESSAGES; //Error msg for cargo screening number of pieces field
				$scope.enterKeyCount = 0;
				$scope.shipmentInfo = {}; //shipment scope
				$scope.shipmentInfo.actualWeightUnit = shipmentDetailsConstants.lbsUnit; //default actual weight unit
				$scope.shipmentInfo.dimUnit = shipmentDetailsConstants.inUnit; //default dim unit
				$scope.dryIceUnit = shipmentDetailsConstants.lbsUnit; // default dry ice unit
				$scope.totalAwtShow = false; //total actual weight textbox in editable mode
				$scope.shipmentInfo.totalSpecialHandlingCodes = []; //total special handling code array
				$scope.shcOptions = []; //special handling code array
				$scope.serviceLevelOptions = []; //service level array
				$scope.lbsKgNotation = shipmentDetailsConstants.lbsKgNotation; //actual weight unit notation
				$scope.loadabilityMatrixType = {}; //loadabilityMatrix object
				$scope.originStart = []; //object to capture and modify origin and destination data
				$scope.disableEnableLBS = shipmentDetailsConstants.enable; //Lbs actual weight textbox
				$scope.disableEnableKG = shipmentDetailsConstants.enable; //Kg actual weight textbox
				$scope.actualValueArray = []; //before conversion value of actual weight
				$scope.totalWeightLbsOrKgValue = ''; //before conversion value of total actual weight
				$scope.counter = 0; //calling initial function
				$scope.alertMsg = []; //Error msg array
				var count = 1; //Count for line number
				var conversionCalled = 0; //To decide whether to convert lbs to kg and vice versa at the time of save
				var countForDim = 0;
				var dimDisable = ''; // flag to identify which dimenssional unit is disable
				$scope.generatedLots = {}; //Generated lots for routing
				$scope.origin = []; //Origin list ref data array
				$scope.destination = []; //Destination list ref data array
				$scope.commodityCode = []; //commodity code array
				var arrayReadSHC = []; //Array for adding all the SHC, use in read only mode
				$scope.arrayReadSHCPer = []; //Binding the SHC in read only mode
				$rootScope.serviceHasValue = false; // variable share between fromshipper controller and shipment details controller to identify that service level has
				//Special handling code Data
				$scope.shcOptions = angular.copy($localStorage.specialData);
				//Commodity Code Ref Data
				$scope.commodityCode = angular.copy($localStorage.commodityCodeShipment);
				// If the Awb is dangerous, user can only select isHazmat commoditycode
				if (manageAwbCntrl.isHazmat) {
					$scope.commodityCode = $filter('filter')($scope.commodityCode, {
							isHazmat : shipmentDetailsConstants.yFlag
						});
				}
				$scope.declaredContents = "";
				$scope.commodityDescSHC = []; //Local scope for content description
				$scope.unitOptions = shipmentDetailsConstants.unitOptions; //Dry ice Unit Drop Down Data
				$scope.lengthError = []; //Flag to set length text in red
				$scope.widthError = []; //Flag to set width text in red
				$scope.heightError = []; //Flag to set height text in red
				$scope.volumeError = []; //Flag to set volume field in red
				$scope.dimWeightError = []; //Flag to set Dimensional field in red
				$scope.dryIceError = []; //Flag to set dry ice text in red
                $scope.dryIceDeleteEnable = false; // Unit testing : Flag use to check dryIce shc is system generated or not
				$scope.insertINCMService = {}; //Object to store saved value
				$scope.insertINCMService.values = [];
				/****LIMITING ENTER KEY PRESS IN HANDLING TXTAREA***/
				$scope.limitEnterKey = function (event) {
					if (event.which == '13') {
						$scope.enterKeyCount++;
						$scope.firstEnterLength = ($scope.enterKeyCount == 1) ? $scope.shipmentInfo.handlingInformation.length : $scope.firstEnterLength;
						$scope.secondEnterLength = ($scope.enterKeyCount == 2) ? $scope.shipmentInfo.handlingInformation.length : $scope.secondEnterLength;
						$scope.enterKeyCount > 2 ? event.preventDefault() : '';
					} else if (event.which == '8') {
						if ($scope.shipmentInfo.handlingInformation.length == 0) {
							$scope.enterKeyCount = 0;
							$scope.firstEnterLength = 0;
							$scope.secondEnterLength = 0;
						} else if ($scope.secondEnterLength && $scope.shipmentInfo.handlingInformation.length <= $scope.secondEnterLength) {
							$scope.enterKeyCount = 1;
							$scope.secondEnterLength = 0;
						} else if ($scope.firstEnterLength && $scope.shipmentInfo.handlingInformation.length <= $scope.firstEnterLength) {
							$scope.firstEnterLength = 0;
							$scope.enterKeyCount = 0;
						}
					}
					if ($scope.shipmentInfo.handlingInformation && ($scope.shipmentInfo.handlingInformation.length == 75 || $scope.shipmentInfo.handlingInformation.length == 150) && event.which != '13') {
						$scope.enterKeyCount = (!$scope.enterKeyCount) ? $scope.enterKeyCount = 1 : $scope.enterKeyCount++;
						$scope.shipmentInfo.handlingInformation = $scope.shipmentInfo.handlingInformation + '';
					}
				};
				//Function is used to remove the origin from the destination list (removeDestMatchingOrig)
				$scope.removeDestMatchingOrig = function () {
					if (!$scope.shipmentInfo.origin) { //if origin is blank, set the value same as swalocation
						if ($localStorage.userObject.swaLocation != shipmentDetailsConstants.HDQUser) { // Origin cannot be HDQ
							$scope.defaultOrigin = $.map($scope.origin, function (val, key) {
									if ((val.code) == $localStorage.userObject.swaLocation) {
										return val;
									}
								}); // Filtering the Origin list.
							$scope.shipmentInfo.origin = $scope.defaultOrigin[0]; //Assigning the value to the origin field
							manageAwbCntrl.headerData.origin = $scope.shipmentInfo.origin.code; // Setting the header object
							$scope.destination = $scope.originStart; // destination options scope
							$scope.destination = $.map($scope.destination, function (val, key) {
									if ((val.code) != $scope.shipmentInfo.origin.code && (val.name) != $scope.shipmentInfo.origin.name) {
										return val;
									}
								}); // Removing the station ( Origin ) which is already selected.
						} else {
							//IF the Origin is HDQ, set as blank
							$scope.shipmentInfo.origin = ""; //Assigning the value to the origin field
							manageAwbCntrl.headerData.origin = ""; // Setting the header object
						}
					}
					$rootScope.changeOrNotManageawb = true; // Form changes( UI select event is not capture in change event of form)
				};
				//Function is used to set header destination (removeDestMatchingOrig)
				$scope.setDestOnHeader = function (destChanged) {
					if ($scope.shipmentInfo.destination) { //if destination is not blank, set header with destination else with blank
						manageAwbCntrl.headerData.destination = $scope.shipmentInfo.destination.code; // Setting the header object
					} else {
						manageAwbCntrl.headerData.destination = "";
					}
					//Preventing focus in select box when expand all section is active so that the focus is on on the Section - 1 shipper id field.
					if (!manageAwbCntrl.isSectionCollapsed) {
						if (manageAwbCntrl.headerData.destination) {
							$timeout(function () { //Magic constant is used to handle the focus issue in IE
							//Modified for Part of taborder flow fix : on shipmentDetails load changed focus to destination field.
								if(destChanged){
									var uiSelect = angular.element(document.querySelector('#serviceLeveldrop')).controller('uiSelect');
									uiSelect.focusser[0].focus();
								}else{
									var uiSelect = angular.element(document.querySelector('#destShipment')).controller('uiSelect');
									uiSelect.focusser[0].focus();
								}
							}, shipmentDetailsConstants.TIMEOUT_HUNDRED_MS);
						}
					}
					$rootScope.changeOrNotManageawb = true; // Form changes( UI select event is not capture in change event of form)
				};
				//If the security type is UNKN>16Oz, add row should be disable and actual weight is by default 1.
				$scope.unknShipment = function () {
					$scope.shipmentInfo.shipmentlines = [{
							lineNumber : 1,
							noOfPieces : '',
							actualWeight : '1',
							lineLength : '',
							lineWidth : '',
							lineHeight : '',
							volume : '',
							dimWeight : '',
							commodityCode : "",
							contentDesc : "",
							additionalDesc : "",
							slac : '',
							specialHandlingCode : [],
							dryIceUnit : shipmentDetailsConstants.lbsUnit,
							isHeavy : '',
							dryIceWeight : ''
						}
					];
					$scope.disableAddRow = true;
					$scope.weightUnitEnableDisable();
					$scope.unknSecurity = true;
				};
				//Default line for shipment
				$scope.notUnknShipment = function () {
					$scope.shipmentInfo.shipmentlines = [{
							lineNumber : 1,
							noOfPieces : '',
							actualWeight : '',
							lineLength : '',
							lineWidth : '',
							lineHeight : '',
							volume : '',
							dimWeight : '',
							commodityCode : "",
							contentDesc : "",
							additionalDesc : "",
							slac : '',
							specialHandlingCode : [],
							dryIceUnit : shipmentDetailsConstants.lbsUnit,
							isHeavy : '',
							dryIceWeight : ''
						}
					];
					$scope.disableAddRow = false;
					$scope.unknSecurity = false;
				};
				//Timeout is require to populating the Type ahead dropdown list(Updated list with City, name and IATA), mainly in IE
				$timeout(function () {
					$scope.fields = $localStorage.airportDetailsRouting; // Populating the Origin/Destination options from localstorage
					for (var z = 0; z < $scope.fields.length; z++) { // Pushing into the Array with the specific format "City-State-Country-IATA"
						$scope.originStart.push({
							code : $scope.fields[z].airportIata,
							//Defect #3822 Fixes
							name : $scope.fields[z].cityName + ', ' + $scope.fields[z].stateName + ' (' + $scope.fields[z].countryName + ') - ' + $scope.fields[z].airportIata
						});
					}
					//Assigning the scope to the O/D options
					$scope.origin = $scope.originStart;
					$scope.destination = $scope.originStart;
					$scope.removeDestMatchingOrig(); // Calling the function for removing the already selected option
				}, shipmentDetailsConstants.typeAheadFocusConstant);
				//items to do on initialization
				$scope.init = function () {
					$scope.validateServiceLevel();
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) { // if the actual weight unit is KG, Use K as notation
						$scope.lbsKgNotation = shipmentDetailsConstants.kNotation;
						$scope.disableEnableLBS = shipmentDetailsConstants.disable; //LBS should be disable
						$scope.disableEnableKG = shipmentDetailsConstants.enable; //Kg should be enable
					} else {
						$scope.lbsKgNotation = shipmentDetailsConstants.lbsKgNotation;
						$scope.disableEnableLBS = shipmentDetailsConstants.enable; //LBS should be enable
						$scope.disableEnableKG = shipmentDetailsConstants.disable; //Kg should be disable
					}
					if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit) {
						dimDisable = shipmentDetailsConstants.inUnit; //if dimensional unit is CM, disable inch
						countActualDefaultUnit = 1;
					}
					if (!$scope.shipmentInfo.destination) {
						$timeout(function () {
							var uiSelect = angular.element(document.querySelector('#destShipment')).controller('uiSelect');
							uiSelect.focusser[0].focus();
						}); //Focus should be on Destination
					}
					$scope.validateUnknownShipper();
				};
				//Function is used to validate service Level
				$scope.validateServiceLevel = function(){
					if ($rootScope.comatServiceVisible) { //comat customer is selected in from shipper section
						$scope.serviceLevelOptionsComat = $localStorage.serviceLevelComat; //If comat is selected, display only comat service level
						}
					if ($rootScope.nonComatServiceVisible) { //non-comat customer is selected in from shipper section
						$scope.serviceLevelOptionsNonComat = $localStorage.serviceLevelNonComat; //If non comat is selected, display only renvenue service level
					}
					if ($rootScope.serviceLevelChange) { //if Shipper is changed
						$('#serviceLeveldrop .selectize-input').css("border-color", "red"); // if customer changes make service level dropdown red (Need to use jquery because we have to make service level red which is type ahead)
					}
				};
				//Function is Used to validate unknown shipper < 16 Oz Condition
				$scope.validateUnknownShipper = function(){
					//Fixes for unkn<16Oz condition
					if (fromShipperCntrl.Shipper.primarySecurityType != shipmentDetailsConstants.securityTypeUnkwn16oz) {
						$scope.disableAddRow = false;
						$scope.unknSecurity = false;
					} else {
						$scope.disableAddRow = true;
						$scope.unknSecurity = true;
					}
				}
				$scope.redIndex = [];
				//Function is used to make the actual weight field as red in KG and LBs both.
				$scope.makeActualFieldRed = function (index) {
					$timeout(function () {
						var actualRed = angular.element('#actualWeight' + index).hasClass('ng-touched'); // Toggling flag to identify that the previous textbox is invalid or not, if it is invalidmake the toggled textbox also red
						$scope.redIndex[index] = actualRed;
					}, shipmentDetailsConstants.TIMEOUT_TEN_MS);
				};
				//Function to identify which unit is enable/disable after save of shipment details
				$scope.weightUnitEnableDisable = function () {
					if ($scope.counter == 0) { // Need to decide which unit is enable or disable, if it is already decided leave the loop
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) { // if actual weight is LBS make kg disable
							for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) { //Using vanilla For loop because we need to use break statement
								if ($scope.shipmentInfo.shipmentlines[i].actualWeight) {
									$scope.disableEnableKG = shipmentDetailsConstants.disable; //Kg should be disable
									$scope.disableEnableLBS = shipmentDetailsConstants.enable; // Lbs should be enable
									$scope.counter = 1;
									break;
								}
							}
						} else { // if Actual weight is KG, make Lbs disable
							for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) { //Using vanilla For loop because we need to use break statement
								if ($scope.shipmentInfo.shipmentlines[i].actualWeight) {
									$scope.disableEnableLBS = shipmentDetailsConstants.disable; //LBS should be disable
									$scope.disableEnableKG = shipmentDetailsConstants.enable; //Kg should be enable
									$scope.counter = 1;
									break;
								}
							}
						}
					}
				};
				//Weight, volume and dimensional wt cal, when the user toggle between different weight unit
				$scope.toggleWeightUnit = function (val) {
					//TODO - rename variable isSection3SC, sec3SaveOrNot
					$scope.sec3SaveOrNot = manageAwbCntrl.isSection3SC; //Toggle is only allowed after save
					if ($scope.sec3SaveOrNot) {
						if ($(val.target).attr('checked') === undefined) { //user cannot click multiple times on the same radio button
							$scope.convertBetweenKgAndLbs(); // if the value entered and one unit becomes disable, convert the weight to the respective unit
							for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
								$scope.HEAIdentify(i); //Weight entered is greater than Heavy weight or Not
								//Calculating Dimensional Weight
								//Defect #4388 fixes
								var lineLength = parseInt($scope.shipmentInfo.shipmentlines[i].lineLength) || 0;
								var lineWidth = parseInt($scope.shipmentInfo.shipmentlines[i].lineWidth) || 0;
								var lineHeight = parseInt($scope.shipmentInfo.shipmentlines[i].lineHeight) || 0;
								var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[i].noOfPieces) || 0;
								var lengthInch = 0;
								var widthInch = 0;
								var heightInch = 0;
								//If unit is cm and it is disable, use the saved data for calculating dim weight, //If unit is cm and it is enable, use the line data, if the unit is inch and it is disable, convert the saved CM unit into in and if the unit is IN and it is enable use the same
								if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit && $scope.disableEnableInCm) {
									lengthInch = $scope.insertINCMService.values[i].length;
									widthInch = $scope.insertINCMService.values[i].width;
									heightInch = $scope.insertINCMService.values[i].height;
								} else if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit && !$scope.disableEnableInCm) {
									lengthInch = lineLength * shipmentDetailsConstants.inchConvert;
									widthInch = lineWidth * shipmentDetailsConstants.inchConvert;
									heightInch = lineHeight * shipmentDetailsConstants.inchConvert;
								} else if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inchDimUnit && $scope.disableEnableInCm) {
									lengthInch = $scope.insertINCMService.values[i].length * shipmentDetailsConstants.inchConvert;
									widthInch = $scope.insertINCMService.values[i].width * shipmentDetailsConstants.inchConvert;
									heightInch = $scope.insertINCMService.values[i].height * shipmentDetailsConstants.inchConvert;
								} else {
									lengthInch = lineLength;
									widthInch = lineWidth;
									heightInch = lineHeight;
								}
								//Dimensional weight calculation
								$scope.dimensionalWtCal(noOfPieces, lengthInch, widthInch, heightInch, i);
								//Defect #4388 fixes
							}
							$scope.volumeCal(); // Change of unit - volume calculation
						}
					} else {
						//Unit Testing: Toggling without save back to LBS
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) {
							for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
								$scope.shipmentInfo.shipmentlines[i].actualWeight = parseInt($scope.shipmentInfo.shipmentlines[i].actualWeight);
                                $scope.HEAIdentify(i); //Weight entered is greater than Heavy weight or Not
                            }
                        } else {//Unit Testing: Toggling without save back to KG HEA Indicator applicability handled
                            for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
                                $scope.HEAIdentify(i); //Weight entered is greater than Heavy weight or Not
							}
						}
						$scope.wtNotation(); //Weight Notation in shipment lines
						$scope.volumeCal(); //Volume calculation
					}
				};
				//Weight Notation in Shipment lines
				$scope.wtNotation = function () {
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) { //if the unit is KG, use K
						$scope.lbsKgNotation = shipmentDetailsConstants.kNotation;
					} else { //if the unit is LBS, use L
						$scope.lbsKgNotation = shipmentDetailsConstants.lbsKgNotation;
					}
				};
				//Conversion between KG to Lbs and vice versa, storing the enabled value
				$scope.convertBetweenKgAndLbs = function () {
					$scope.weightUnitEnableDisable(); //check which unit is enable and which is diasable
					if ($scope.counter != 0) { //only do conversion, if enable and disable is decided
						conversionCalled++; //help in saving the data always in enabled unit
						$scope.totalAwtShow = false;
						if ($scope.disableEnableKG == shipmentDetailsConstants.disable) { // If kg is disable, convert the lbs value into kg and store lbs value
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
								angular.forEach($scope.shipmentInfo.shipmentlines, function (line, i) {
									$scope.actualValueArray[i] = line.actualWeight; // store lbs value and convert it to kg
									line.actualWeight = $rootScope.lbsToKgConversion(line.actualWeight);
								});
								$scope.totalWeightLbsOrKgValue = $scope.shipmentInfo.totalActualWeight; //store total lbs value and convert it to convert kg
								$scope.shipmentInfo.totalActualWeight = $rootScope.lbsToKgConversion($scope.shipmentInfo.totalActualWeight); // Conversion
							}
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) {
								angular.forEach($scope.shipmentInfo.shipmentlines, function (line, i) {
									line.actualWeight = $scope.actualValueArray[i]; // as LBS is enable, retrieve that data from the stored value
								});
								$scope.shipmentInfo.totalActualWeight = $scope.totalWeightLbsOrKgValue; // as LBS is enable, retrieve that data from the stored value
							}
						} else if ($scope.disableEnableLBS == shipmentDetailsConstants.disable) {
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) {
								angular.forEach($scope.shipmentInfo.shipmentlines, function (line, i) { // store kg value and convert it to lbs
									$scope.actualValueArray[i] = line.actualWeight;
									line.actualWeight = $rootScope.kgToLbsConversion(line.actualWeight);
								});
								$scope.totalWeightLbsOrKgValue = $scope.shipmentInfo.totalActualWeight;
								$scope.shipmentInfo.totalActualWeight = $rootScope.kgToLbsConversion($scope.shipmentInfo.totalActualWeight);
							}
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
								angular.forEach($scope.shipmentInfo.shipmentlines, function (line, i) {
									line.actualWeight = $scope.actualValueArray[i];
								});
								$scope.shipmentInfo.totalActualWeight = $scope.totalWeightLbsOrKgValue;
							}
						}
					}
					$scope.wtNotation(); //Notation for actual weight unit
					//Using awbdata service to store the enable values
					$scope.basecontrolvalue = {}; //Object to save actual weight
					$scope.basecontrolvalue.actualValue = $scope.actualValueArray;
					$scope.basecontrolvalue.totalWeightLbsOrKg = $scope.totalWeightLbsOrKgValue;
					airwayBillDataService.insertBaseControlValueService($scope.basecontrolvalue);
				};
				//Function used to select only those content desc which are active
				//@Params-updatedData-recieved data from service call.
				$scope.contentDesRefData = function (updatedData) {
					$scope.commodityDescSHC = updatedData.data.commodityCodetoDescMappingRecords.filter(function (value) { // filtering content desc w.r.t effective start and end date
							var effectiveEndDate = value.effectiveEndDate;
							effectiveEndDate = new Date(effectiveEndDate);
							var effectiveStartDate = value.effectiveStartDate;
							effectiveStartDate = new Date(effectiveStartDate);
							var currentDate = $rootScope.currentDate;
							currentDate = new Date(currentDate);
							return ((effectiveEndDate) >= currentDate && (effectiveStartDate) <= currentDate);
						});
					$localStorage.commodityVolumePer = $scope.commodityDescSHC; // commodity code per piece volume if available
				};
				//Retrieve condition,search awb
				if ($localStorage.isSearchPageCall === true) {
					$scope.shipmentInfo = {};
					$scope.shipmentInfo.shipmentlines = [];
					if (angular.isObject($rootScope.awbContent) && $rootScope.awbContent.shipmentInfo) {
						$scope.shipmentInfo = angular.copy($rootScope.awbContent.shipmentInfo); //Rootscope object => local scope
						count = $rootScope.awbContent.shipmentInfo.shipmentlines[($rootScope.awbContent.shipmentInfo.shipmentlines.length - 1)].lineNumber; // Number of lines saved, use for incrementing the line number
						$scope.weightUnitEnableDisable(); // initial value for weight unit conversion
						if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
							dimDisable = shipmentDetailsConstants.cmDimUnit; // if inch, disable cm
						} else {
							dimDisable = shipmentDetailsConstants.inUnit; //if cm, disable inch
							countActualDefaultUnit = 1;
						}
						var tempCC = $.map($scope.commodityCode, function (val, commodityCode) {
								if ((val.commodityCode) == $rootScope.awbContent.shipmentInfo.shipmentlines[0].commodityCode.code) {
									return val;
								}
							}); // validating the commodity code
						$scope.shipmentInfo.shipmentlines[0].commodityCode = tempCC[0] ? tempCC[0] : ""; // assigning to scope
						$scope.commodityCode1 = tempCC[0] ? tempCC[0].commodityCode : ""; //Scope use to assign commodity code to all other line except 1
						//Generate request for content desc
						var tempCom = {};
						tempCom.commodityCode = $scope.shipmentInfo.shipmentlines[0].commodityCode ? $scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode : "";
						tempCom.flag = shipmentDetailsConstants.searchFlag;
						//Service call fof fetching the content desc data against the given commodity code
						manageAWBService.getComDec(JSON.stringify(tempCom)).then(function (updatedData) {
							$scope.contentDesRefData(updatedData);
							var tempCCD = $.map($scope.commodityDescSHC, function (val, contentsDescription) {
									if ((val.contentsDescription) == $rootScope.awbContent.shipmentInfo.shipmentlines[0].contentDesc.code) {
										return val;
									}
								});
							$scope.shipmentInfo.shipmentlines[0].contentDesc = tempCCD[0] ? tempCCD[0] : "";
							$scope.contentDescription1 = tempCCD[0] ? tempCCD[0].contentsDescription : "";
							$scope.declaredContents = $scope.contentDescription1;
						});
						$scope.shipmentInfo.shipmentlines[0].dryIceUnit = $rootScope.awbContent.shipmentInfo.shipmentlines[0].dryIceUnit ? $rootScope.awbContent.shipmentInfo.shipmentlines[0].dryIceUnit : shipmentDetailsConstants.lbsUnit;
						//Summary Level Dry Ice Unit
						$scope.dryIceUnit = $scope.shipmentInfo.shipmentlines[0].dryIceUnit;
						$scope.unit1 = $rootScope.awbContent.shipmentInfo.shipmentlines[0].dryIceUnit; // Dry ice unit for all the line except 1
						$scope.totalSpHandlingCodesForSummary = $rootScope.awbContent.shipmentInfo.totalSpecialHandlingCodes.join(", "); // Array => string (comma separated for TSHC)
						//if the value is invalid, make it blank
						angular.forEach($rootScope.awbContent.shipmentInfo.shipmentlines, function (line, index) {
							$scope.shipmentInfo.shipmentlines[index].volume = line.volume ? line.volume : "";
							$scope.shipmentInfo.shipmentlines[index].dimWeight = line.dimWeight ? line.dimWeight : "";
							$scope.shipmentInfo.shipmentlines[index].isHeavy = (line.isHeavy == "false" || line.isHeavy == false) ? false : true;
							$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = [];
							arrayReadSHC[index] = [];
							for (var j = 0; j < line.specialHandlingCode.length; j++) {
								var spc = '';
								spc = line.specialHandlingCode[j].name;
								if (spc) {
									$scope.shipmentInfo.shipmentlines[index].specialHandlingCode.push({
										key : spc,
										value : spc
									});
									arrayReadSHC[index].push(line.specialHandlingCode[j].name);
								}
							}
							var unique = arrayReadSHC[index].filter(onlyUnique); //Read only array for SHC
							$scope.arrayReadSHCPer[index] = unique.join(", ");
							//If the weight entered in KG
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
								$scope.shipmentInfo.shipmentlines[index].actualWeight = $scope.shipmentInfo.shipmentlines[index].actualWeightKg;
								$scope.lbsKgNotation = shipmentDetailsConstants.kNotation;
								countActualDefault = 1;
							}
							//Assigning the dimensioninto the local scope, so that we can use it in toggling
							$scope.insertINCMService.values[index] = {};
							$scope.insertINCMService.values[index].length = $scope.shipmentInfo.shipmentlines[index].lineLength;
							$scope.insertINCMService.values[index].width = $scope.shipmentInfo.shipmentlines[index].lineWidth;
							$scope.insertINCMService.values[index].height = $scope.shipmentInfo.shipmentlines[index].lineHeight;
						});
						$scope.shipmentInfo.totalActualWeight = $rootScope.awbContent.shipmentInfo.totalActualWeight ? $rootScope.awbContent.shipmentInfo.totalActualWeight : ""; //if the value is 0, make it blank
						$scope.shipmentInfo.totalPieces = $rootScope.awbContent.shipmentInfo.totalPieces ? $rootScope.awbContent.shipmentInfo.totalPieces : ""; //if the value is 0, make it blank
						$scope.shipmentInfo.totalVolume = $rootScope.awbContent.shipmentInfo.totalVolume ? $rootScope.awbContent.shipmentInfo.totalVolume : ""; //if the value is 0, make it blank
						$scope.shipmentInfo.totalDimWeight = $rootScope.awbContent.shipmentInfo.totalDimWeight ? $rootScope.awbContent.shipmentInfo.totalDimWeight : ""; //if the value is 0, make it blank
						//Destination and Origin option array filtering
						$scope.removeDestMatchingOrig();
						$scope.setDestOnHeader();
						/*Timeout is require for checking the security type which is present in from shipper controller, making the service level Red.
						If comat shipper is use, comat service level
						if non-comat shipper is use, renvenue service level*/
						$timeout(function () {
							$scope.shipmentInfo.serviceLevel = {};
							$scope.shipmentInfo.serviceLevel.key = $rootScope.awbContent.shipmentInfo.serviceLevel.code;
							$scope.shipmentInfo.serviceLevel.value = $rootScope.awbContent.shipmentInfo.serviceLevel.code;
							manageAwbCntrl.headerData.serviceLevel = $scope.shipmentInfo.serviceLevel.key;
							$rootScope.serviceHasValue = true;
							if ($rootScope.primarySecurityTypeShipment == shipmentDetailsConstants.securityTypeSwaExt || $rootScope.primarySecurityTypeShipment == shipmentDetailsConstants.securityTypeSwaInt) {
								if (fromShipperCntrl.Shipper.primarySecurityType != shipmentDetailsConstants.securityTypeSwaInt && fromShipperCntrl.Shipper.primarySecurityType != shipmentDetailsConstants.securityTypeSwaExt) {
									$scope.shipmentInfo.serviceLevel = "";
									manageAwbCntrl.headerData.serviceLevel = "";
									$rootScope.primarySecurityTypeShipment = fromShipperCntrl.Shipper.primarySecurityType;
								}
							} else {
								if (fromShipperCntrl.Shipper.primarySecurityType == shipmentDetailsConstants.securityTypeSwaInt || fromShipperCntrl.Shipper.primarySecurityType == shipmentDetailsConstants.securityTypeSwaExt) {
									$scope.shipmentInfo.serviceLevel = "";
									manageAwbCntrl.headerData.serviceLevel = "";
									$rootScope.primarySecurityTypeShipment = fromShipperCntrl.Shipper.primarySecurityType;
								}
							}
							$scope.commonErrorHandling(); //Calling the common errror handling function to make the section green or red
						}, shipmentDetailsConstants.TIMEOUT_HUNDRED_MS);
						/*if the status of awb is of below list, make the add new row button disable*/
						if (manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.RCFNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.DLVNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.DISCNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.CXLDNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.VOIDNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.CLSDNotation && manageAwbCntrl.headerData.awbStatus != shipmentDetailsConstants.DCLSNotation) {
							$scope.disableAddRow = false;
						} else {
							$scope.disableAddRow = true;
						}
						/*if the security type is UNKN<16oZ, make the add row button disable*/
						if ($rootScope.awbContent.shipper.primarySecurityType == shipmentDetailsConstants.securityTypeUnkwn16oz) {
							$scope.disableAddRow = true;
						}
					} else { //Shipment object is blank @ retrieving, creating the default line
						$scope.shipmentInfo.shipmentlines = [];
						$scope.shipmentInfo.actualWeightUnit = shipmentDetailsConstants.lbsUnit; //default actual weight unit
						$scope.shipmentInfo.dimUnit = shipmentDetailsConstants.inUnit; //default dim unit
						//Table Data for first time
						if (fromShipperCntrl.Shipper.primarySecurityType != shipmentDetailsConstants.securityTypeUnkwn16oz) {
							$scope.notUnknShipment();
						} else {
							$scope.unknShipment();
						}
					}
				} else { // Create Condition
					$scope.shipmentInfo.shipmentlines = [];
					//Table Data for first time
					if (fromShipperCntrl.Shipper.primarySecurityType != shipmentDetailsConstants.securityTypeUnkwn16oz) { // Not unkn<16oz
						$scope.notUnknShipment();
					} else {
						$scope.unknShipment();
					}
				}
				/*Function is used to push new row into shipment lines and inherit commodity code, content desc and SHC from the first line*/
				$scope.addNewRow = function () {
					$rootScope.isClickShipmentAddRow = true;
					addIndex = [];
					//line should be less than 9999
					if ($scope.shipmentInfo.shipmentlines.length >= shipmentDetailsConstants.lineLimit) {
						alert('Limit Reached');
					} else {
						//count = $scope.shipmentInfo.shipmentlines.length + 1;
						count = 0;
						angular.forEach($scope.shipmentInfo.shipmentlines, function (line) {
							if (count < line.lineNumber) {
								count = line.lineNumber;
							}
						});
						count = count + 1;
					}
					//New row data
					$scope.row = {
						lineNumber : count, //Continue with the last line number from database,
						noOfPieces : '',
						actualWeight : '',
						lineLength : '',
						lineWidth : '',
						lineHeight : '',
						volume : '',
						dimWeight : '',
						commodityCode : {},
						contentDesc : "",
						additionalDesc : "",
						slac : '',
						specialHandlingCode : [],
						dryIceUnit : '',
						isHeavy : '',
						dryIceWeight : ''
					};
					addIndex.push($scope.shipmentInfo.shipmentlines.length); // new row push into the local scope
					$scope.shipmentInfo.shipmentlines.push($scope.row);
					//For second Row onwards
					$scope.commodityCode1 = $scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode; // commodity code inherited from line 1
					$scope.contentDescription1 = $scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription; // content desc inherited from line 1
					$scope.unit1 = $scope.shipmentInfo.shipmentlines[0].dryIceUnit; // dry ice unit inherited from line 1
					var index = $scope.shipmentInfo.shipmentlines.length - 1;
					focus('noOfPieces' + index);
					$scope.addSHC(); // Adding the SHC, as we are inherting commodity code
					$scope.commonErrorHandling();
				};
				var SHCInCom = [];
				$scope.HEADeleteEnable = false; // Flag use to check HEA shc is system generated or not
				//Add System generated SHC on the basis of content desc.
				$scope.addSHC = function () {
					if ($scope.declaredContents) {
						$scope.removeSHC(); //if content desc is blank, remove system generaed SHC
					}
                    //Unit testing : Manual deletion of System generated Dry Ice SHC restricted
                    $scope.dryIceDeleteEnable = false;
					/*Fetching the SHC from content desc ref data and then pushing it into SHC column*/
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						if ($scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription) {
							SHCInCom = $.map($scope.commodityDescSHC, function (val, key) {
									if ((val.contentsDescription) == $scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription) {
										return val; //fetching the SHC => content desc
									}
								});
							if (SHCInCom[0].specialHandlingCode) {
								var temp = new Array();
								temp = SHCInCom[0].specialHandlingCode.split(",");
								for (var j = 0; j < temp.length; j++) {
									var addSystemGeneratedSHC = [{
											key : temp[j],
											value : temp[j]
										}
									]; //pushing SHC into the local scope
									$scope.shipmentInfo.shipmentlines[i].specialHandlingCode.push(addSystemGeneratedSHC[0]);
									//if SHC is HEA make the delete button of it disable
									if (addSystemGeneratedSHC[0].key === shipmentDetailsConstants.HEANotation) {
										$scope.HEADeleteEnable = true;
                                    }
                                    //Unit testing : Manual deletion of System generated Dry Ice SHC restricted
                                    //if SHC is dryIce make the delete button of it disable
                                    if (addSystemGeneratedSHC[0].key === shipmentDetailsConstants.icecode) {
                                        $scope.dryIceDeleteEnable = true;
									}
								}
							}
						}
						$scope.addReadSHCFunction(i); // Add in read only array
						$scope.sortSHC(i); // Sort the inserted SHC according to Priority
					}
				};
				//Remove system generated SHC from the lines
				$scope.removeSHC = function () {
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						// Fetching the value form SHC object = > cont desc
						SHCInCom = $.map($scope.commodityDescSHC, function (val, key) {
								if ((val.contentsDescription) == $scope.declaredContents) {
									return val;
								}
							});
						if (SHCInCom.length > 0) {
							if (SHCInCom[0].specialHandlingCode) {
								var temp = new Array();
								temp = SHCInCom[0].specialHandlingCode.split(",");
								for (var j = 0; j < temp.length; j++) {
									$scope.shipmentInfo.shipmentlines[i].specialHandlingCode = $.map($scope.shipmentInfo.shipmentlines[i].specialHandlingCode, function (val, key) {
											if ((val.key) != temp[j] && (val.value) != temp[j]) {
												return val;
											}
										});
								}
							}
						}
						$scope.addReadSHCFunction(i); // Add in read only array of SHC
						$scope.sortSHC(i); // Sort the inserted SHC according to priroty
					}
				};
				//Function is used to display error or warning on the header of the section
				//@Params-passing the current message
				$scope.errorMethod = function (message) {
					if (message != ShipmentDetailsErrorMsgs.noNeed) { //if the message is not "no need", push it into the header msg array
						$scope.alertMsg.unshift(message); //Latest msg should be display, thats why using unshift
						manageAwbCntrl.alertSecurityMsgShipmentDetails = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf(".")); //Removing everthing which is after (.)
					} else { //if msg is delete, display the msg next on the list
						if ($scope.alertMsg[0]) {
							manageAwbCntrl.alertSecurityMsgShipmentDetails = $scope.alertMsg[0].substring(0, $scope.alertMsg[0].lastIndexOf("."));
						} else {
							manageAwbCntrl.alertSecurityMsgShipmentDetails = '';
						}
					}
				};
				/*Function is uste to remove the error message( warning message ). As we can add multiple line and each line has its warning/error message, so we are using this method to remove the message from the queue/array of message by value.*/
				Array.prototype.remove = function () {
					var what,
					a = arguments,
					L = a.length,
					ax;
					while (L && this.length) {
						what = a[--L];
						while ((ax = this.indexOf(what)) !== -1) {
							this.splice(ax, 1);
						}
					}
					return this;
				};
				//Delete Shipment details line
				//@Params-index of the line to be removed
				$scope.removeItem = function (index) {
					if (index != 0) {
						$scope.shipmentInfo.shipmentlines.splice(index, 1);
						$scope.totalCalculation(index - 1);
						$scope.commonErrorHandling();
						//					for (var i = 1; i <= $scope.shipmentInfo.shipmentlines.length; i++) {
						//						$scope.shipmentInfo.shipmentlines[i - 1].lineNumber = i;
						//					}
					}
				};
				//Make section header red, when there is any error in the section
				$scope.makeSectionRed = function () {
					manageAwbCntrl.dispAccHdrErrorSd = true;
					manageAwbCntrl.displayDivErrorBarSd = true;
				};
				//Function to make section red if there is any message(hard stop) available in alertMsg array
				$scope.chkErrorArray = function () {
					if ($scope.alertMsg) {
						angular.forEach($scope.alertMsg, function (msg, index) {
							if (msg == ShipmentDetailsErrorMsgs.numberSmall + index.toString() || msg == ShipmentDetailsErrorMsgs.invalidWeight + index.toString() || msg == ShipmentDetailsErrorMsgs.dryIceWeight + index.toString() || msg == ShipmentDetailsErrorMsgs.lineWeightExceeded + index.toString()) {
								$scope.makeSectionRed(); // if there is any msg available in the alert msg array which is hard stop, make section red
							}
						});
					}
				};
				//Common method to handle error
				$scope.commonErrorHandling = function () {
					// if form invalid, make section red
					manageAwbCntrl.dispAccHdrErrorSd = $scope.shipmentForm.$invalid;
					manageAwbCntrl.displayDivErrorBarSd = $scope.shipmentForm.$invalid;
					$scope.chkErrorArray();
					// if UI select object is blank, make section red
					if (!$scope.shipmentInfo.origin || !$scope.shipmentInfo.destination || !$scope.shipmentInfo.serviceLevel || !$scope.shipmentInfo.origin.code || !$scope.shipmentInfo.destination.code || !$scope.shipmentInfo.serviceLevel.key || !$scope.shipmentInfo.shipmentlines[0].commodityCode || !$scope.shipmentInfo.shipmentlines[0].contentDesc) {
						$scope.makeSectionRed();
					}
					//As volume and dimensional weight are read only field, we need to check externally for that
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						if (!$scope.shipmentInfo.shipmentlines[i].volume || !$scope.shipmentInfo.shipmentlines[i].dimWeight || $scope.volumeError[i] || $scope.dimWeightError[i]) {
							$scope.makeSectionRed();
							break;
						}
					}
				};
				//Common function to be called after the change event of commodity code and content description
				//@Params-index-current line index
				$scope.commonFunctionToBeCalled = function (index) {
					$scope.volumeCal(); // Volume calculation
					$scope.totalCalculation(index); // Total Calculation @ Summary line
					$scope.commonErrorHandling(); //Calling the common errror handling function to make the section green or red
					$rootScope.changeOrNotManageawb = true; // Form changes( UI select event is not capture in change event of form)
				};
				/*Selecting commodity code from the list and populating it for all the line, and also  calling content description service for fetching content desc against the selected commodity code*/
				//@Params-index-current line index
				$scope.commodityCodeChange = function (index) {
					if ($scope.shipmentInfo.shipmentlines[0].commodityCode) {
						if ($scope.declaredContents) {
							$scope.removeSHC(); //if content desc is blank, remove the system generated SHC
						}
						//Commodity code for all the line
						$scope.commodityCode1 = $scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode;
						$scope.tempCom = {};
						$scope.tempCom.commodityCode = $scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode;
						$scope.tempCom.flag = shipmentDetailsConstants.searchFlag;
						manageAWBService.getComDec(JSON.stringify($scope.tempCom)).then(function (updatedData) { // fetching content desc = > com code
							$scope.contentDesRefData(updatedData);
						});
						$scope.HEAIdentify(index); //Add or remove HEA Special handling code
					} else {
						$scope.removeSHC(); //remove the system generated SHC
						$scope.commodityCode1 = '';
						$scope.shipmentInfo.shipmentlines[0].commodityCode = '';
						$scope.commodityDescSHC = [];
					}
					$scope.shipmentInfo.shipmentlines[0].contentDesc = '';
					$scope.contentDescription1 = '';
					$scope.declaredContents = '';
					$scope.commonFunctionToBeCalled(index);
					$timeout(function () { //Magic costant to handle the focus issue on IE
						var uiSelect = angular.element(document.querySelector('#contentDesc0')).controller('uiSelect');
						uiSelect.focusser[0].focus(); // Focus on content desc
					}, shipmentDetailsConstants.TIMEOUT_HUNDRED_MS);
				};
				/*Function use to select content desc from the available list, add system generated SHC and calculate the volume*/
				//@Params-index-current line index
				$scope.contentDescriptionChange = function (index) {
					if ($scope.shipmentInfo.shipmentlines[0].contentDesc) {
						if ($scope.declaredContents) {
							$scope.removeSHC(); // if content desc is blank, remove the system generated SHC
						}
						$scope.addSHC(); // ADd SHC w.r.t content desc
						$scope.contentDescription1 = $scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription; //Content desc for all the line
						$scope.declaredContents = $scope.contentDescription1;
						$scope.totalSHC(0); // Total SHC
					} else {
						$scope.removeSHC(); //remove the system generated SHC
						$scope.contentDescription1 = '';
						$scope.shipmentInfo.shipmentlines[0].contentDesc = '';
						$scope.declaredContents = '';
					}
					$scope.commonFunctionToBeCalled(index);
					focus('additionalDesc' + index); //Focus on additional desc field
				};
				//Dry ice Unit Dropdown change
				$scope.unitChange = function () {
					$scope.unit1 = $scope.shipmentInfo.shipmentlines[0].dryIceUnit; //Dry ice unit for all the line
					$scope.dryIceUnit = $scope.shipmentInfo.shipmentlines[0].dryIceUnit;
				};
				//Function is used to check that SLAC is greater than no. of pieces
				//@Params-index-current line index
				$scope.slacBlur = function (index) {
					var slac = parseInt($scope.shipmentInfo.shipmentlines[index].slac);
					var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[index].noOfPieces);
					$scope.displayMessage = ShipmentDetailsErrorMsgs.numberSmall + index.toString();
					if (slac <= noOfPieces) { // if SLAC is less than number of pieces, display messages
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else {
						$scope.noNeedofMsg($scope.displayMessage);
						$scope.commonErrorHandling();
					}
				};
				//Function is used to focus special handling code on the blur of additional desc field
				//@Params-index-current line index
				$scope.additionalDescBlur = function (index, e) {
					var keyCode = e.keyCode || e.which;
					//Focus on SHC field Only on tab click
					if (keyCode ==  CONFIGURATIONS.eventCodeTabOut && !e.shiftKey) { 						
						e.preventDefault();
						e.stopPropagation();
						$scope.focusOnSHC(index);
					}
				};
				//Function is used to focus special handling code/Dry Ice on the blur of handling info field on certain condition
				//@Params-e-current event
				$scope.handlingInfoBlur = function (e) {
					var lastIndex = $scope.shipmentInfo.shipmentlines.length - 1;
					var keyCode = e.keyCode || e.which;
					//Handel focus on elements on shift+ tab click 
					if (keyCode ==  CONFIGURATIONS.eventCodeTabOut && e.shiftKey) {
						e.preventDefault();
						e.stopPropagation();
						if ($scope.dryIcePresentOrNot(lastIndex)) {
							focus('dryIceWt' + lastIndex);
						} else {
							$scope.focusOnSHC(lastIndex);
						}
					}
				};
				//Function is used to focus special handling code/Dry Ice of previous line on the blur of pieces field on certain condition
				//@Params-e-current event
				//@param - index -  Current Line index
				$scope.pcsConditionBasedFocus = function (index, e) {
					if (index > 0) {
						var indexToPass = index - 1;
						var keyCode = e.keyCode || e.which;
						//Handel focus on elements on shift+ tab click 
						if (keyCode ==  CONFIGURATIONS.eventCodeTabOut && e.shiftKey) { 
							e.preventDefault();
							e.stopPropagation();
							if ($scope.dryIcePresentOrNot(indexToPass)) {
								focus('dryIceWt' + indexToPass);
							} else {
								$scope.focusOnSHC(indexToPass);
							}
						}
					}
				};
				/*Function is used to calculate the actual weight of all the line if the total weight is entered(only when actual weight of any line is blank)*/
				$scope.totalActWtBlur = function () {
					var indexArray = [];
					var valueArray = [];
					var pcsArray = [];
					var totalPcstoDivide = 0;
					$scope.weightUnitEnableDisable();
					/*pushing the index and value of all the actual weight field which is not blank. IT is used when we have to divide the calculated weight against the line. if the line contains actual weight than the calculated value is not going to divide in those field which already contains the value*/
					angular.forEach($scope.shipmentInfo.shipmentlines, function (line, index) {
						if (!line.actualWeight) {
							indexArray.push(index);
							if (line.noOfPieces) {
								pcsArray.push(line.noOfPieces);
								totalPcstoDivide = totalPcstoDivide + line.noOfPieces;
							}
						} else {
							valueArray.push(line.actualWeight);
						}
					});
					/*calculating the total available weight (total weight entered in all line)*/
					var totalAvailWt = 0;
					for (var i = 0; i < valueArray.length; i++) { //Calculating the total weight entered across the line
						totalAvailWt = totalAvailWt + parseFloat(valueArray[i]);
					}
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) { // IF LBS, parse int
						$scope.shipmentInfo.totalActualWeight = parseInt($scope.shipmentInfo.totalActualWeight);
					}
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) { // IF KG, parse float
						$scope.shipmentInfo.totalActualWeight = parseFloat($scope.shipmentInfo.totalActualWeight);
					}
					/*display error message if the weight entered in total field is either zero or less than the available weight*/
					var index = shipmentDetailsConstants.errorWithNoLine;
					var totalWeight = parseFloat($scope.shipmentInfo.totalActualWeight) || 0;
					var actualWeight = parseFloat(totalWeight - totalAvailWt);
					$scope.displayMessage = ShipmentDetailsErrorMsgs.invalidWeight + index.toString();
					if (totalWeight <= 0 || totalWeight < totalAvailWt) { // if weight is less than 0 or less than total entered weight
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else {
						$scope.noNeedofMsg($scope.displayMessage);
						$scope.commonErrorHandling();
						/*Calculating piece level weight and the remainder. Remainder is distributed in equal proportion of pieces*/
						var actWtKg = 0;
						var actWtLbs = 0;
						var countKg = 0;
						var equalAmountWtKg = 0;
						var remainderWtKg = 0;
						var countLbs = 0;
						var equalAmountWtLbs = 0;
						var remainderWtLbs = 0;
						var perPieceWeight = [];
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
							actWtKg = actualWeight;
							countKg = parseInt(totalPcstoDivide);
							equalAmountWtKg = Math.floor(actWtKg / countKg); // equal piece level weight
							remainderWtKg = (actWtKg % countKg).toFixed(1);
							remainderWtKg = parseFloat(remainderWtKg); // remainder
						}
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) {
							actWtLbs = actualWeight;
							countLbs = parseInt(totalPcstoDivide);
							equalAmountWtLbs = Math.floor(actWtLbs / countLbs); // equal piece level weight
							remainderWtLbs = actWtLbs % countLbs; // remainder
						}
						/*Equally dividing the weight across all the pieces against each line*/
						for (var p = 0; p < parseInt(totalPcstoDivide); p++) {
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
								perPieceWeight.push(equalAmountWtKg); // Pushing the equal weight in piece array
							} else {
								perPieceWeight.push(equalAmountWtLbs); // Pushing the equal weight in piece array
							}
						}
						/*If the unit is KG, Remainder is equally distributed with 0.1 across all the pieces until the remainder becomes zero and if the unit is LBS, remainder is distributed with 1 until the remainder becomes zero*/
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
							$scope.equallyDivideKgWeight(remainderWtKg, totalPcstoDivide, perPieceWeight);
						}
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit) {
							$scope.equallyDivideLbsWeight(remainderWtLbs, totalPcstoDivide, perPieceWeight);
						}
						$scope.assignEquallyDistributedWt(indexArray, pcsArray, perPieceWeight);
						for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) { // making total actual weight disable, if all the weight are filled
							$scope.totalAwtShow = ($scope.shipmentInfo.shipmentlines[i].actualWeight == "" || isNaN($scope.shipmentInfo.shipmentlines[i].actualWeight));
							$scope.actWtBlur(i); // Actual weight blur of each and every line
						}
					}
				};
				/*Function is used to divide Kg weight equally(add 0.1 to each pieces until reaminder becomes 0)*/
				$scope.equallyDivideKgWeight = function (remainderWtKg, totalPcstoDivide, perPieceWeight) {
					if (remainderWtKg !== "0.0" && remainderWtKg !== 0.0 && remainderWtKg !== 0) {
						var loopVar = Math.ceil(parseInt(remainderWtKg * 10) / totalPcstoDivide);
						for (var k = 0; k < loopVar; k++) {
							for (var p = 0; p < parseInt(totalPcstoDivide); p++) {
								if (remainderWtKg !== "0.0" && remainderWtKg !== 0.0 && remainderWtKg !== 0) {
									perPieceWeight[p] = parseFloat(perPieceWeight[p] + 0.1);
									remainderWtKg = parseFloat(remainderWtKg - 0.1).toFixed(1);
								}
							}
						}
					}
				};
				/*Function is used to Divide LBS weight equally (add 1 to each pieces until reaminder becomes 0)*/
				//@Params-remainderWtLbs
				//@Params-totalPcstoDivide-total no. of pieces
				//@Params-perPieceWeight-per piece weight
				$scope.equallyDivideLbsWeight = function (remainderWtLbs, totalPcstoDivide, perPieceWeight) {
					if (remainderWtLbs !== 0 && remainderWtLbs !== "0") {
						var loopVar = Math.ceil(parseInt(remainderWtLbs) / totalPcstoDivide);
						for (var k = 0; k < loopVar; k++) {
							for (var p = 0; p < parseInt(totalPcstoDivide); p++) {
								if (remainderWtLbs !== 0 && remainderWtLbs !== "0") {
									perPieceWeight[p] = parseInt(perPieceWeight[p] + 1);
									remainderWtLbs = parseInt(remainderWtLbs - 1);
								}
							}
						}
					}
				};
				//Function is used to assign equally distributed weight to all the line
				//@Params-indexArray
				//@Params-pcsArray
				//@Params-perPieceWeight-per piece weight
				$scope.assignEquallyDistributedWt = function (indexArray, pcsArray, perPieceWeight) {
					var perPieceWeightCount = 0;
					for (var x = 0; x < indexArray.length; x++) {
						var pcsCount = pcsArray[x];
						var wtPerTotal = 0;
						for (var y = 0; y < pcsCount; y++) {
							if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
								wtPerTotal = parseFloat(wtPerTotal + perPieceWeight[perPieceWeightCount]);
							} else {
								wtPerTotal = parseInt(wtPerTotal + perPieceWeight[perPieceWeightCount]);
							}
							perPieceWeightCount = perPieceWeightCount + 1;
						}
						$scope.shipmentInfo.shipmentlines[indexArray[x]].actualWeight = $scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.lbsUnit ? wtPerTotal : wtPerTotal.toFixed(1);
					}
				};
				//Function is used to remove HEA from SHC and set Is heavy Indicator flag
				//@Params-isHeavy-isHeavy flag
				//@Params-index-line index
				$scope.deleteHEAfromSHC = function (isHeavy, index) {
					//Unit Testing: We cannot select HEA manually
					if ($scope.heaPresentOrNot(index) && $scope.shipmentInfo.shipmentlines[index].isHeavy && $scope.HEADeleteEnable == false) {
						$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = $.grep($scope.shipmentInfo.shipmentlines[index].specialHandlingCode, function (data, index) {
								return data.value != shipmentDetailsConstants.HEANotation;
							});
					}
					$scope.shipmentInfo.shipmentlines[index].isHeavy = isHeavy; // heavy indicator to true

				};
				//Function is used to check ==> the weight entered in actual weight is heavy or not
				$scope.heavyWeightCal = function (perPeiceWeight, heavyMaxWtPerPc, heavyMinWtPerPc, HEASpecial, index) {
					$scope.displayMessage = ShipmentDetailsErrorMsgs.weightPerPiece + index.toString();
					if (perPeiceWeight > parseInt(heavyMaxWtPerPc)) { // if the weight greater than the allowed heavy weight, set heavy indiactor to true, add HEA to SHC and display msg
						$scope.errorMethod($scope.displayMessage);
						$scope.deleteHEAfromSHC(true, index);
						$scope.shipmentInfo.shipmentlines[index].specialHandlingCode.push(HEASpecial); // add SHC
					} else if (perPeiceWeight > parseInt(heavyMinWtPerPc) && perPeiceWeight <= parseInt(heavyMaxWtPerPc)) { // if the weight is in between the heavy max and heavy min weight, set heavy indicator to true and add SHC
						$scope.deleteHEAfromSHC(true, index);
						$scope.shipmentInfo.shipmentlines[index].specialHandlingCode.push(HEASpecial); // add SHC
						//Defect 4363 : not showing the error message when perPeiceWeight is less than heavyMaxWtPerPc
						$scope.noNeedofMsg($scope.displayMessage);
					} else if (perPeiceWeight < parseInt(heavyMinWtPerPc)) { // weight is less than heavy min
						$scope.deleteHEAfromSHC(false, index);
						$scope.noNeedofMsg($scope.displayMessage);
					}
				};
				//Function is used to apply HEAvy indicator to the line if its applicable
				//@Params-index-line index
				$scope.HEAIdentify = function (index) {
					$scope.heaSpecial = [{ // HEA array
							key : shipmentDetailsConstants.HEANotation,
							value : shipmentDetailsConstants.HEANotation
						}
					];
					var actualWeight = parseInt($scope.shipmentInfo.shipmentlines[index].actualWeight);
					var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[index].noOfPieces);
					var perPeiceWeight = parseInt(actualWeight) / parseInt(noOfPieces);
					if ($scope.shipmentInfo.shipmentlines[0].commodityCode != undefined) {
						$scope.commodityDesc1 = $.map($scope.commodityCode, function (val, key) {
								if ((val.commodityCode) == $scope.shipmentInfo.shipmentlines[0].commodityCode.commodityCode) {
									return val;
								}
							});
					}
					if ($scope.commodityDesc1 && $scope.commodityDesc1.length != 0) {
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
							$scope.heavyWeightCal(perPeiceWeight, $scope.commodityDesc1[0].heavyMaxWtPerPcKg, $scope.commodityDesc1[0].heavyMinWtPerPcKg, $scope.heaSpecial[0], index);
						} else {
							$scope.heavyWeightCal(perPeiceWeight, $scope.commodityDesc1[0].heavyMaxWtPerPcLbs, $scope.commodityDesc1[0].heavyMinWtPerPcLbs, $scope.heaSpecial[0], index);
						}
						$scope.totalSHC(index); //Total SHC
					}
					$scope.addReadSHCFunction(index); //Read only SHC array
					$scope.sortSHC(index); //Sorting SHC
				};
				//Function is used to check the dry ice weight and also use to calculate the total summary
				//@Params-index-line index
				$scope.actWtBlur = function (index) {
					$scope.weightUnitEnableDisable(); // Identify unit of weight
					$scope.HEAIdentify(index); // Adding HEA SHC on the basic of weight
					var actualWeight = parseInt($scope.shipmentInfo.shipmentlines[index].actualWeight);
					$scope.totalAwtShow = (actualWeight == "" || isNaN(actualWeight)); //if blank, total will enable
					var dryIceWt = parseInt($scope.shipmentInfo.shipmentlines[index].dryIceWeight) || 0;
					$scope.displayMessage = ShipmentDetailsErrorMsgs.lineWeightExceeded + index.toString(); // if weight exceeds dry ice weigth, error msg
					if (dryIceWt > actualWeight) {
						$scope.errorMethod($scope.displayMessage);
						$scope.dryIceError[index] = true; // make the dry ice weight value red
					} else {
						$scope.dryIceError[index] = false;
						$scope.noNeedofMsg($scope.displayMessage);
					}
					$scope.totalCalculation(index); // total calculation
					$scope.commonErrorHandling(); //Calling the common errror handling function to make the section green or red
					$scope.addReadSHCFunction(index); // add read only SHC
					$scope.sortSHC(index); // Sorting SHC
					$scope.volumeCal(); //volume calculation
				};
				//unique array
				function onlyUnique(value, index, self) {
					return self.indexOf(value) === index;
				}
				//Read only SHC, used at the time of read only preivileges
				$scope.addReadSHCFunction = function (index) { //ADding into read only array of SHC
					arrayReadSHC[index] = [];
					for (var j = 0; j < $scope.shipmentInfo.shipmentlines[index].specialHandlingCode.length; j++) {
						arrayReadSHC[index].push($scope.shipmentInfo.shipmentlines[index].specialHandlingCode[j].value);
					}
					var unique = arrayReadSHC[index].filter(onlyUnique);
					$scope.arrayReadSHCPer[index] = unique.join(", ");
				};
				//change of specialHandlingCode
				//@Params index-line index
				$scope.shcChangeEvent = function (index) {
					$scope.addSHC(); // Add system generated SHC
					$scope.HEAIdentify(index); //HEA check
					$scope.addReadSHCFunction(index); //Read only SHC
					$scope.sortSHC(index); //Sorting SHC
					if ($scope.shipmentInfo.shipmentlines[index].dryIceWeight) {
						$scope.dryIceWtBlur(index);
					}
					if ($scope.dryIcePresentOrNot(index)) {
						focus('dryIceWt' + index);
					} else {
						focus('handlingInfo');
					}
					$scope.totalSHC(index); //total SHC
					$rootScope.changeOrNotManageawb = true; //Form changes( UI selct )
				};
				//Function is used to focus on handling info/dry ice
				//@Params index-line index
				//@Params e - current event
				$scope.shcConditionBasedFocus = function (index, e) {
					var keyCode = e.keyCode || e.which;
					if (keyCode == 9 && e.shiftKey) { //Shift + Tab key
						e.preventDefault();
						e.stopPropagation();
						focus('additionalDesc' + index);
					} else {
						if (keyCode == 9) { //TAb
							e.preventDefault();
							e.stopPropagation();
							if ($scope.dryIcePresentOrNot(index)) {
								focus('dryIceWt' + index);
							} else {
								focus('handlingInfo');
							}
						}
					}
				}
				//Function is used to remove the duplcate value from then array of objects
				$scope.containsSameObject = function (obj, list) {
					var ret = false;
					for (var i = 0; i < list.length; i++) {
						if (list[i].key === obj.key) {
							ret = true;
							break;
						}
					}
					return ret;
				};
				//If ice is selected, return true
				//@Params index-line index
				$scope.dryIcePresentOrNot = function (index) {
					var focusDryICEField = false;
					//Checking Ice is present in SHC or not
					for (var j = 0; j < $scope.shipmentInfo.shipmentlines[index].specialHandlingCode.length; j++) {
						if ($scope.shipmentInfo.shipmentlines[index].specialHandlingCode[j].value == shipmentDetailsConstants.icecode) {
							focusDryICEField = true;
						}
					}
					return focusDryICEField;
				};
				//If HEA is selected, return true
				//@Params index-line index
				//Unit Testing: We cannot select HEA manually
				$scope.heaPresentOrNot = function (index) {
					var addHEA = false;
					//Checking Ice is present in SHC or not
					for (var j = 0; j < $scope.shipmentInfo.shipmentlines[index].specialHandlingCode.length; j++) {
						if ($scope.shipmentInfo.shipmentlines[index].specialHandlingCode[j].value == shipmentDetailsConstants.HEANotation) {
							addHEA = true;
						}
					}
					return addHEA;
				};
				//Sorting of SHC field
				//@Params index-line index
				$scope.sortSHC = function (index) {
					$scope.sortScope = $localStorage.prioritySHC; // Fetching the Ref data Set
					$scope.presentSHC = []; //Array use to push sort order in temp array of SHC
					$scope.temp = []; //USe for sort
					angular.forEach($scope.sortScope, function (sort) { //Loop against the ref data
						angular.forEach($scope.shipmentInfo.shipmentlines[index].specialHandlingCode, function (shcLine) { //loop against the SHC present int he line
							if (shcLine.key === sort.specialHandlingTypeCode) {
								$scope.presentSHC = [{
										key : shcLine.key,
										value : shcLine.value,
										sortOrder : sort.sortOrder
									}
								]; // maintainng the sort order
								$scope.temp.push($scope.presentSHC[0]); // pushing into temp
							}
						});
					});
					//As there might be a chances that ref data contains duplicate records(with Hazadious/without Hazadious), we need to remove it from the UI
					$scope.temp2 = [];
					angular.forEach($scope.temp, function (NotDuplicate) {
						if (!$scope.containsSameObject(NotDuplicate, $scope.temp2)) { //If doesnt present in temp2 array
							var tmpLotPush = {};
							tmpLotPush = angular.copy(NotDuplicate);
							$scope.temp2.push(tmpLotPush);
						}
					});
					//Sorting the temp SHC array with sort order
					$scope.temp = $filter('orderBy')($scope.temp2, 'sortOrder');
					$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = [];
					$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = angular.copy($scope.temp); //temp into local SHC object
				};
				//Total SHC @ summary level ( Only unique one )
				//@Params index-line index
				$scope.totalSHC = function (index) {
					var arrayOfSHC = [];
					angular.forEach($scope.shipmentInfo.shipmentlines, function (line) {
						if (line.specialHandlingCode) {
							angular.forEach(line.specialHandlingCode, function (shc) {
								arrayOfSHC.push(shc.value);
							});
						}
					});
					var unique = arrayOfSHC.filter(onlyUnique);
					$scope.totalSpHandlingCodesForSummary = unique.join(", "); //Total SHC which is unique across the line
					$scope.shipmentInfo.totalSpecialHandlingCodes = arrayOfSHC.filter(onlyUnique);
				};
				//Function is use to calculate total of all the fields for summary level
				//@Params index-line index
				$scope.totalCalculation = function (index) {
					var totalPcsValue = 0;
					var totalActualWeightValue = 0;
					var totalDryIceWeight = 0;
					var totalVolume = 0;
					var totalDimWeight = 0;
					var decideToMarkRoutingSection = false;
					var toggleForSection4 = false;
					angular.forEach($scope.shipmentInfo.shipmentlines, function (line) {
						var noOfPieces = parseInt(line.noOfPieces) || 0;
						var actualWeight = parseFloat(line.actualWeight) || 0;
						var dryIceWt = parseInt(line.dryIceWeight) || 0;
						var volume = parseInt(line.volume) || 0;
						var dimWeight = parseInt(line.dimWeight) || 0;
						totalPcsValue = totalPcsValue + noOfPieces;
						totalActualWeightValue = totalActualWeightValue + actualWeight;
						totalDryIceWeight = totalDryIceWeight + dryIceWt;
						if (volume !== "" && dimWeight !== "") {
							totalVolume = totalVolume + volume;
							totalDimWeight = totalDimWeight + dimWeight;
						} else {
							totalVolume = "";
							totalDimWeight = "";
						}
					});
					$scope.shipmentInfo.totalPieces = totalPcsValue || "";
					$rootScope.shipperTotalPieces = $scope.shipmentInfo.totalPieces || "";
					$scope.validateCargoScreening();
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
						$scope.shipmentInfo.totalActualWeight = totalActualWeightValue.toFixed(1) || ""; //total weight
					} else {
						$scope.shipmentInfo.totalActualWeight = totalActualWeightValue || ""; //total weight
					}
					$scope.shipmentInfo.totalDryIceWeight = totalDryIceWeight || ""; //total dry ice weight
					$scope.shipmentInfo.totalVolume = totalVolume || ""; //total volume
					$scope.shipmentInfo.totalDimWeight = totalDimWeight || ""; //total dimweight
				};
				//Function is used to validate cargo screening section, if there is any change in number of pieces of shipment
				$scope.validateCargoScreening = function () {
					/**ONLY IF SECURITY TYPE IS IAC AND TSC IS TRUE**/
					if ($rootScope.validateSecurityTypeIAC && manageAwbCntrl.cargoScreeningData != undefined) {
						manageAwbCntrl.totalScreenData.totalPieces = $rootScope.shipperTotalPieces;
						manageAwbCntrl.cargoScreeningData[0].noOfPieces = $rootScope.shipperTotalPieces;
						if (!manageAwbCntrl.alertSecurityMsgScreening && !manageAwbCntrl.displayErrorFromSecShipment) {
							manageAwbCntrl.dispAccHdrErrorCS = manageAwbCntrl.displayDivErrorBarCS = false;
						}
					}
					/**ONLY IF SECURITY TYPE IS IAC AND TSC IS TRUE**/
					$rootScope.shipperTotalPieces = $scope.shipmentInfo.totalPieces;
					if ($rootScope.cargoScreenTotalPcs < $rootScope.shipperTotalPieces && $rootScope.shipperTotalPieces !== "") {
						manageAwbCntrl.alertSecurityMsgScreening = $scope.erroMsg.nopiece;
						$rootScope.screenTotalIndicator = true;
						manageAwbCntrl.dispAccHdrErrorCS = true;
						manageAwbCntrl.displayDivErrorBarCS = true;
					} else if ($rootScope.cargoScreenTotalPcs > $rootScope.shipperTotalPieces && $rootScope.shipperTotalPieces !== "") {
						manageAwbCntrl.alertSecurityMsgScreening = '';
						if (!manageAwbCntrl.alertSecurityMsgScreening && !manageAwbCntrl.displayErrorFromSecShipment) {
							$rootScope.screenTotalIndicator = false;
							manageAwbCntrl.dispAccHdrErrorCS = false;
							manageAwbCntrl.displayDivErrorBarCS = false;
						}
					} else {
						manageAwbCntrl.alertSecurityMsgScreening = '';
						$rootScope.screenTotalIndicator = false;
					}
				};
				//Function is used to focus SHC/additional desc field on certain condtion
				//@Params index-line index
				$scope.conditionBasedFocusDryIce = function (index, e) {
					var keyCode = e.keyCode || e.which;
					if (keyCode == 9 && e.shiftKey) { //Shift + Tab key
						e.preventDefault();
						e.stopPropagation();
						if ($scope.dryIcePresentOrNot(index)) {
							$scope.focusOnSHC(index);
						} else {
							focus('additionalDesc' + index);
						}
					}
				};
				//Dry Ice weight blur
				//@Params index-line index
				$scope.dryIceWtBlur = function (index, e) {
					//Dry ice wt should be less than the actual weight of the line
					if ($scope.shipmentInfo.shipmentlines[index]) {
						var dryIceWt = parseInt($scope.shipmentInfo.shipmentlines[index].dryIceWeight) || 0;
						var actualWeight = parseFloat($scope.shipmentInfo.shipmentlines[index].actualWeight) || 0;
						$scope.displayMessage = ShipmentDetailsErrorMsgs.lineWeightExceeded + index.toString();
						if (dryIceWt > actualWeight) { //if dry ice weight , greater than actual weight the make text red and display msg
							$scope.errorMethod($scope.displayMessage);
							$scope.dryIceError[index] = true;
						} else {
							$scope.dryIceError[index] = false;
							$scope.noNeedofMsg($scope.displayMessage);
						}
						$scope.commonErrorHandling(); // if any error present, make the section red
						$scope.totalCalculation(index); //total calculation for dry ice weight
					}
					$scope.dryIceSHC(index); //Adding ICE into SHC, if dry ice weight present
				};
				//Setting Focus on SHC Field.
				//@Params index-line index
				$scope.focusOnSHC = function (index) {
					$timeout(function () {
						var uiSelect = angular.element(document.querySelector('#specialHandlingCode' + index)).controller('uiSelect');
						uiSelect.focusInput[0].focus();
					}, shipmentDetailsConstants.TIMEOUT_TEN_MS);
				};
				//Method used for adding ICE in SHC.
				//@Params index-line index
				$scope.dryIceSHC = function (index) {
					//Adding ICE into SHC, if dry ice weight present
					if ($scope.shipmentInfo.shipmentlines[index]) {
						$scope.dryIceSpecial = [{ //ICE SHC
								key : shipmentDetailsConstants.icecode,
								value : shipmentDetailsConstants.icecode
							}
						];
						//If ICE is not Present and dry ice has value, add ICE in SHC
						if (!$scope.dryIcePresentOrNot(index)) {
							if ($scope.shipmentInfo.shipmentlines[index].dryIceWeight) {
								$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = $.grep($scope.shipmentInfo.shipmentlines[index].specialHandlingCode, function (data, index) {
										return data.value != shipmentDetailsConstants.icecode;
									});
								$scope.shipmentInfo.shipmentlines[index].specialHandlingCode.push($scope.dryIceSpecial[0]);
							}
						} else { //Remove ICE from SHC, if dry ice weight is blank
                            //Unit testing : Manual deletion of System generated Dry Ice SHC restricted
                            if (!$scope.shipmentInfo.shipmentlines[index].dryIceWeight && !$scope.dryIceDeleteEnable) {
								$scope.shipmentInfo.shipmentlines[index].specialHandlingCode = $.map($scope.shipmentInfo.shipmentlines[index].specialHandlingCode, function (val, key) {
										if ((val.key) != shipmentDetailsConstants.icecode && (val.value) != shipmentDetailsConstants.icecode) {
											return val;
										}
									});
							}
						}
						$scope.totalSHC(index); //Total SHC for Summary
					}
					$scope.addReadSHCFunction(index); //ADd Total SHC in readOnly array
					$scope.sortSHC(index); // Sorting of SHC according to priority order
				};
				//Generate routing data for routing section
				//This function creates a lot object for routing section with data from shipment details section
				//Initially when there is no lot present in routing section, this function is used to create a lot
				$scope.generateRoutingData = function () {
					return airwayBillDataService.generateRoutingData($scope.shipmentInfo);
				};
				//It is used to retrieve the saved weight from the local scope and pass in request
				$scope.retreivingSavedWeightData = function (unit, baseControlValue) {
					$scope.shipmentInfo.actualWeightUnit = unit;
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
                        //Defect #5298 fixes
                        //If actualValue is present then assign it to shipment line's actualWeight else retain shipment line's actualWeight
                        $scope.shipmentInfo.shipmentlines[i].actualWeight = baseControlValue.actualValue[i] ? baseControlValue.actualValue[i] : $scope.shipmentInfo.shipmentlines[i].actualWeight;
					}
					$scope.shipmentInfo.totalActualWeight = baseControlValue.totalWeightLbsOrKg;
					$scope.wtNotation();
				};
				//It is used to retrieve the saved dimension from the local scope and pass in request
				//@Params unit- unit ie inch/cm.
				$scope.retreivingSavedDimensionsData = function (unit) {
					$scope.shipmentInfo.dimUnit = unit;
					$scope.disableEnableInCm = false;
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						$scope.shipmentInfo.shipmentlines[i].lineLength = $scope.insertINCMService.values[i].length;
						$scope.shipmentInfo.shipmentlines[i].lineWidth = $scope.insertINCMService.values[i].width;
						$scope.shipmentInfo.shipmentlines[i].lineHeight = $scope.insertINCMService.values[i].height;
					}
				};
				//Function is used to generate final object for the request of save shipment
				$scope.baseControl = function () {
					$scope.commonErrorHandling();
					var baseControlValue = airwayBillDataService.getBaseControlValueService(); // weight value from service
					var totalVolume = 0;
					var totalDimWeight = 0;
					if (conversionCalled != 0) { // making the unit enable, in which the data is entered
						if ($scope.disableEnableLBS == shipmentDetailsConstants.enable) {
							$scope.retreivingSavedWeightData(shipmentDetailsConstants.lbsUnit, baseControlValue);
						} else {
							$scope.retreivingSavedWeightData(shipmentDetailsConstants.kgWeightUnit, baseControlValue);
						}
						conversionCalled = 0;
					}
					//Making the dimension enable, in which the data is entered
					if (dimDisable == '') {
						if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
							dimDisable = shipmentDetailsConstants.cmDimUnit;
						} else {
							dimDisable = shipmentDetailsConstants.inUnit;
						}
					}
					if (dimDisable == shipmentDetailsConstants.cmDimUnit) {
						if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit) {
							$scope.retreivingSavedDimensionsData(shipmentDetailsConstants.inUnit);
						}
					} else {
						if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
							$scope.retreivingSavedDimensionsData(shipmentDetailsConstants.cmDimUnit);
						}
					}
					$scope.volumeCal(); // volume calculation
					$scope.tempSave = angular.copy($scope.shipmentInfo); // copy the local scope into the temp
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) { // Entering the data in both the units
						$scope.tempSave.shipmentlines[i].commodityCode = $scope.tempSave.shipmentlines[0].commodityCode;
						$scope.tempSave.shipmentlines[i].contentDesc = $scope.tempSave.shipmentlines[0].contentDesc;
						$scope.tempSave.shipmentlines[i].dryIceUnit = $scope.tempSave.shipmentlines[0].dryIceUnit;
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
							$scope.tempSave.shipmentlines[i].actualWeightKg = $scope.tempSave.shipmentlines[i].actualWeight;
							$scope.tempSave.shipmentlines[i].actualWeight = $rootScope.kgToLbsConversion($scope.tempSave.shipmentlines[i].actualWeightKg);
						} else {
							$scope.tempSave.shipmentlines[i].actualWeight = $scope.tempSave.shipmentlines[i].actualWeight;
							$scope.tempSave.shipmentlines[i].actualWeightKg = $rootScope.lbsToKgConversion($scope.tempSave.shipmentlines[i].actualWeight);
						}
					}
					saveShipmentScope = $scope.tempSave; // final object to save
				};
				//Dimensional unit conversion Logic
				//@Params convertStream-Conversion unit ie inch/cm
				//@Params L-length
				//@Params W-Width
				//@Params H-Height
				//@Params index- line index
				$scope.dimunitConversion = function (convertStream, L, W, H, index) {
					if (convertStream == shipmentDetailsConstants.inUnit) {
						$scope.shipmentInfo.shipmentlines[index].lineLength = Math.round(L * shipmentDetailsConstants.cmConvert) || ""; // in to cm
						$scope.shipmentInfo.shipmentlines[index].lineWidth = Math.round(W * shipmentDetailsConstants.cmConvert) || "";
						$scope.shipmentInfo.shipmentlines[index].lineHeight = Math.round(H * shipmentDetailsConstants.cmConvert) || "";
					}
					if (convertStream == shipmentDetailsConstants.cmDimUnit) {
						$scope.shipmentInfo.shipmentlines[index].lineLength = Math.round(L * shipmentDetailsConstants.inchConvert) || ""; // cm to in
						$scope.shipmentInfo.shipmentlines[index].lineWidth = Math.round(W * shipmentDetailsConstants.inchConvert) || "";
						$scope.shipmentInfo.shipmentlines[index].lineHeight = Math.round(H * shipmentDetailsConstants.inchConvert) || "";
					}
				};
				var countActualDefaultUnit = 0;
				//Function is used to call different function when we are toggling between different unit of dimesnion
				$scope.toggleDimUnit = function (val) {
					$scope.sec3SaveOrNot = manageAwbCntrl.isSection3SC;
					if ($scope.sec3SaveOrNot) {
						if ($(val.target).attr('checked') === undefined) {
							if (countActualDefaultUnit == 0) {
								if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
									countActualDefaultUnit = 0;
								} else {
									countActualDefaultUnit = 1;
								}
							}
							if (countActualDefaultUnit == 1) {
								$scope.assigningFindingDimUnit(); // if disable, change the value
								$scope.volumeCal(); //volume calculation
							}
						}
					} else {
						$scope.volumeCal(); //volume calculation
					}
				};
				//Function is used to decide which dimensional unit is disable or enable
				$scope.findingWhichUnitIsDisabled = function () {
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						var lineLength = parseInt($scope.shipmentInfo.shipmentlines[i].lineLength) || "";
						var lineWidth = parseInt($scope.shipmentInfo.shipmentlines[i].lineWidth) || "";
						var lineHeight = parseInt($scope.shipmentInfo.shipmentlines[i].lineHeight) || "";
						if (lineLength || lineWidth || lineHeight) {
							if (countForDim == 0) { //Only one time we need to decide
								$scope.disableEnableInCm = true;
								dimDisable = $scope.shipmentInfo.dimUnit;
								countForDim = 1;
							}
						}
						//Check for the count of dimensions and set the appropriate UoM based on the selected UoM.
						if (countForDim == 1) {
							if (dimDisable == shipmentDetailsConstants.cmDimUnit) {
								if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
									$scope.disableEnableInCm = false;
								} else {
									$scope.disableEnableInCm = true;
								}
							} else {
								if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
									$scope.disableEnableInCm = true;
								} else {
									$scope.disableEnableInCm = false;
								}
							}
						}
					}
				};
				//Function is used to store dimensional data into local scope so that it can be used at the time of toggling
				$scope.storingDimData = function () {
					if ($scope.disableEnableInCm) {
						for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
							$scope.insertINCMService.values[i] = {};
							$scope.insertINCMService.values[i].length = $scope.shipmentInfo.shipmentlines[i].lineLength;
							$scope.insertINCMService.values[i].width = $scope.shipmentInfo.shipmentlines[i].lineWidth;
							$scope.insertINCMService.values[i].height = $scope.shipmentInfo.shipmentlines[i].lineHeight;
						}
					}
				};
				//Function is used to convert one dimensional unit into other
				$scope.assigningFindingDimUnit = function () {
					//Finding which unit is disabled
					$scope.findingWhichUnitIsDisabled();
					//storing the dimension value in local scope, for toggling purpose
					$scope.storingDimData();
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						if (dimDisable == shipmentDetailsConstants.cmDimUnit) {
							if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
								$scope.shipmentInfo.shipmentlines[i].lineLength = $scope.insertINCMService.values[i].length;
								$scope.shipmentInfo.shipmentlines[i].lineWidth = $scope.insertINCMService.values[i].width;
								$scope.shipmentInfo.shipmentlines[i].lineHeight = $scope.insertINCMService.values[i].height;
							} else {
								$scope.dimunitConversion(shipmentDetailsConstants.inUnit, $scope.shipmentInfo.shipmentlines[i].lineLength, $scope.shipmentInfo.shipmentlines[i].lineWidth, $scope.shipmentInfo.shipmentlines[i].lineHeight, i);
							}
						} else {
							if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
								$scope.dimunitConversion(shipmentDetailsConstants.cmDimUnit, $scope.shipmentInfo.shipmentlines[i].lineLength, $scope.shipmentInfo.shipmentlines[i].lineWidth, $scope.shipmentInfo.shipmentlines[i].lineHeight, i);
							} else {
								$scope.shipmentInfo.shipmentlines[i].lineLength = $scope.insertINCMService.values[i].length;
								$scope.shipmentInfo.shipmentlines[i].lineWidth = $scope.insertINCMService.values[i].width;
								$scope.shipmentInfo.shipmentlines[i].lineHeight = $scope.insertINCMService.values[i].height;
							}
						}
					}
				};
				//Volume Calculation
				$scope.volumeCal = function () {
					for (var i = 0; i < $scope.shipmentInfo.shipmentlines.length; i++) {
						var lineLength = parseInt($scope.shipmentInfo.shipmentlines[i].lineLength) || 0;
						var lineWidth = parseInt($scope.shipmentInfo.shipmentlines[i].lineWidth) || 0;
						var lineHeight = parseInt($scope.shipmentInfo.shipmentlines[i].lineHeight) || 0;
						var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[i].noOfPieces) || 0;
						var actualWeight = parseFloat($scope.shipmentInfo.shipmentlines[i].actualWeight) || 0;
						var actualWeightPerPiece = parseFloat($scope.shipmentInfo.shipmentlines[i].actualWeight) || 0;
						if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
							actualWeight = $rootScope.kgToLbsConversion(actualWeight);
							actualWeightPerPiece = (actualWeight / noOfPieces);
						} else {
							actualWeight = actualWeight;
							actualWeightPerPiece = actualWeight / noOfPieces;
						}
						$scope.commonFuncCalledOnLWH(noOfPieces, lineLength, lineWidth, lineHeight, i, actualWeightPerPiece);
					}
				};
				/*Used to call common functionality when we tab out from L W H field*/
				$scope.commonFuncCalledOnLWH = function (noOfPieces, lineLength, lineWidth, lineHeight, index, actualWeightPerPiece) {
					//Volume calculation
					$scope.volumeCalLogic(noOfPieces, lineLength, lineWidth, lineHeight, index);
					//Calcualtion always in inch
					var lengthInch = 0;
					var widthInch = 0;
					var heightInch = 0;
					if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit) {
						lengthInch = lineLength * shipmentDetailsConstants.inchConvert;
						widthInch = lineWidth * shipmentDetailsConstants.inchConvert;
						heightInch = lineHeight * shipmentDetailsConstants.inchConvert;
					} else {
						lengthInch = lineLength;
						widthInch = lineWidth;
						heightInch = lineHeight;
					}
					//Dimensional weight calculation
					if (!$scope.disableEnableInCm && !manageAwbCntrl.sec3SaveOrNot) {
						$scope.dimensionalWtCal(noOfPieces, lengthInch, widthInch, heightInch, index);
					}
					//Floor bearing weight cal
					$scope.floorBearingWeight(lengthInch, widthInch, heightInch, index, actualWeightPerPiece);
					//Total calculation for summary level
					$scope.totalCalculation(index);
				};
				//Function is used to make Dimensional field as red, if the calculated dimensional weight is greater than 99999
				//@Params index-line index
				//@Params dimWt-line Dim weight
				$scope.dimWeightLength = function (index, dimWt) {
					$scope.dimWeightError[index] = false;
					if (dimWt > shipmentDetailsConstants.maxVolAndDim) {
						$scope.shipmentInfo.shipmentlines[index].dimWeight = "";
						$scope.dimWeightError[index] = true;
					} else {
						$scope.dimWeightError[index] = false;
						$scope.shipmentInfo.shipmentlines[index].dimWeight = dimWt;
					}
					$scope.commonErrorHandling();
				};
				//Function is used to make volume field as red, if the calculated volume is greater 99999
				//@Params index-line index
				//@Params volume-line volume
				$scope.volumeLength = function (index, volume) {
					$scope.volumeError[index] = false;
					if (volume > shipmentDetailsConstants.maxVolAndDim) {
						$scope.shipmentInfo.shipmentlines[index].volume = "";
						$scope.volumeError[index] = true;
					} else {
						$scope.volumeError[index] = false;
						$scope.shipmentInfo.shipmentlines[index].volume = volume;
					}
					$scope.commonErrorHandling();
				};
				//Floor Bearing weight Calculation (always in inch)
				/* @Params lengthInch-length in inches
				@Params heightInch- height in inches
				@Params widthInch- width in inches
				@Params actualWeightPerPiece- actual weight per piece
				@Params index- line index */
				$scope.floorBearingWeight = function (lengthInch, widthInch, heightInch, index, actualWeightPerPiece) {
					actualWeightPerPiece = isFinite(actualWeightPerPiece) ? actualWeightPerPiece : 0;
					$scope.displayMessage = ShipmentDetailsErrorMsgs.floorBearingWeight + index.toString();
					if (lengthInch <= shipmentDetailsConstants.floorBearingMinDim && heightInch <= shipmentDetailsConstants.floorBearingMinDim && widthInch <= shipmentDetailsConstants.floorBearingMinDim && actualWeightPerPiece < shipmentDetailsConstants.floorBearingWt151) { // side less than 12 and weight less than 151
						$scope.noNeedofMsg($scope.displayMessage);
						$scope.commonErrorHandling();
					} else if (lengthInch < shipmentDetailsConstants.floorBearingMinDim && heightInch < shipmentDetailsConstants.floorBearingMinDim && actualWeightPerPiece > shipmentDetailsConstants.floorBearingMinWt && lengthInch && heightInch) { // side less than 12 and weight > 150, display msg
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else if (widthInch < shipmentDetailsConstants.floorBearingMinDim && heightInch < shipmentDetailsConstants.floorBearingMinDim && actualWeightPerPiece > shipmentDetailsConstants.floorBearingMinWt && widthInch && heightInch) { // side less than 12 and weight > 150, display msg
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else if (lengthInch < shipmentDetailsConstants.floorBearingMinDim && widthInch < shipmentDetailsConstants.floorBearingMinDim && actualWeightPerPiece > shipmentDetailsConstants.floorBearingMinWt && lengthInch && widthInch) { // side less than 12 and weight > 150, display msg
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else {
						$scope.noNeedofMsg($scope.displayMessage);
						$scope.commonErrorHandling();
					}
				};
				//Function is used to calculate dimensional weight(cal is always in inch)
				/* @Params lengthInch-length in inches
				@Params heightInch- height in inches
				@Params widthInch- width in inches
				@Params noOfPieces- no of pieces
				@Params index- line index */
				$scope.dimensionalWtCal = function (noOfPieces, lengthInch, widthInch, heightInch, index) {
					$scope.terminal = shipmentDetailsConstants.DOMNotation;
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
						var dimWeight = ((noOfPieces * lengthInch * widthInch * heightInch) / shipmentDetailsConstants.dimInchCal) || "";
						dimWeight = Math.ceil(dimWeight / shipmentDetailsConstants.kgConvert); // if in kg, divide by 2.2046
						$scope.dimWeightLength(index, dimWeight);
					} else {
						var dimWeight = Math.ceil(((noOfPieces * lengthInch * widthInch * heightInch) / shipmentDetailsConstants.dimInchCal)) || "";
						$scope.dimWeightLength(index, dimWeight);
					}
					$scope.totalCalculation(index);
				};
				//Function is used to calculate volume if volume is not present in ref data
				/* @Params lineLength-length
				@Params lineWidth- width
				@Params lineHeight- height
				@Params index- line index */
				$scope.simpleVolumeCal = function (noOfPieces, lineLength, lineWidth, lineHeight, index) {
					if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.inUnit) {
						var volume = Math.ceil(((noOfPieces * lineLength * lineWidth * lineHeight) / shipmentDetailsConstants.volInchCal)) || ""; // volume cal for inc
						$scope.volumeLength(index, volume);
					} else {
						var volume = Math.ceil(((noOfPieces * lineLength * lineWidth * lineHeight * shipmentDetailsConstants.volumeConvert))) || ""; // volume cal for cm
						$scope.volumeLength(index, volume);
					}
				};
				//Function is used to calculate volume, dimensional weight and floor bearing weight and also used to check loadability
				//@Params index- line index
				$scope.heightHBlur = function (index) {
					var lineLength = parseInt($scope.shipmentInfo.shipmentlines[index].lineLength) || 0;
					var lineWidth = parseInt($scope.shipmentInfo.shipmentlines[index].lineWidth) || 0;
					var lineHeight = parseInt($scope.shipmentInfo.shipmentlines[index].lineHeight) || 0;
					var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[index].noOfPieces) || 0;
					var actualWeight = parseFloat($scope.shipmentInfo.shipmentlines[index].actualWeight) || 0;
					var actualWeightPerPiece = parseFloat($scope.shipmentInfo.shipmentlines[index].actualWeight) || 0;
					if ($scope.shipmentInfo.actualWeightUnit == shipmentDetailsConstants.kgWeightUnit) {
						actualWeight = $rootScope.kgToLbsConversion(actualWeight);
						actualWeightPerPiece = actualWeight / noOfPieces;
					} else {
						actualWeight = actualWeight;
						actualWeightPerPiece = actualWeight / noOfPieces;
					}
					$scope.commonFuncCalledOnLWH(noOfPieces, lineLength, lineWidth, lineHeight, index, actualWeightPerPiece);
					var lengthInch = 0;
					var widthInch = 0;
					var heightInch = 0;
					if ($scope.shipmentInfo.dimUnit == shipmentDetailsConstants.cmDimUnit) {
						lengthInch = lineLength * shipmentDetailsConstants.inchConvert;
						widthInch = lineWidth * shipmentDetailsConstants.inchConvert;
						heightInch = lineHeight * shipmentDetailsConstants.inchConvert;
					} else {
						lengthInch = lineLength;
						widthInch = lineWidth;
						heightInch = lineHeight;
					}
					//Load ability check
					$scope.loadAbilityCal(Math.round(lengthInch), Math.round(widthInch), Math.round(heightInch), index);
				};
				/*Function is used to calculate volume.*/
				//@Param noOfPieces-no of pieces
				//@Param lineLength-length
				//@Param lineWidth-width
				//@Param lineHeight-height
				//@Param index-line index
				$scope.volumeCalLogic = function (noOfPieces, lineLength, lineWidth, lineHeight, index) {
					if ($scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription) {
						var volumePerPiece = $localStorage.commodityVolumePer;
						var volumePP = $.map(volumePerPiece, function (val, contentsDescription) {
								if ((val.contentsDescription) == $scope.shipmentInfo.shipmentlines[0].contentDesc.contentsDescription) {
									return val;
								}
							});
						//If Ref data has volume then calculate from their, otherwise use simple volume calculation logic
						if (!volumePP[0].volPerPiece) {
							$scope.simpleVolumeCal(noOfPieces, lineLength, lineWidth, lineHeight, index);
						} else {
							var noOfPieces = parseInt($scope.shipmentInfo.shipmentlines[index].noOfPieces);
							var volume = Math.ceil((noOfPieces * volumePP[0].volPerPiece)) || "";
							$scope.volumeLength(index, volume);
						}
					} else {
						$scope.simpleVolumeCal(noOfPieces, lineLength, lineWidth, lineHeight, index);
					}
				};
				/*Function is used to do LoadAbility check.*/
				//@Param noOfPieces-no of pieces
				//@Param lineLength-length
				//@Param lineWidth-width
				//@Param lineHeight-height
				//@Param index-line index
				$scope.loadAbilityCal = function (lineLength, lineWidth, lineHeight, index) {
					$scope.lengthError[index] = false;
					$scope.widthError[index] = false;
					$scope.heightError[index] = false;
					//System will sort the dimensions (L, H, W) by ascending order after the tab on the 3 value
					var arrayLWH = [lineLength, lineWidth, lineHeight];
					arrayLWH.sort(sortAscending);
					$scope.loadabilityMatrixType = $localStorage.loadabilityMatrixType;
					//System will check if the either of the 2 smallest value is not greater than 32 inches or 45 inches respectively, if the 2 smallest values are greater system will display an on screen message <MSG10>
					$scope.displayMessage = ShipmentDetailsErrorMsgs.dimensionExceed + index.toString();
					if (arrayLWH[0] > shipmentDetailsConstants.inch32 || arrayLWH[1] > shipmentDetailsConstants.inch45) { //Two side less than 32/45
						$scope.errorMethod($scope.displayMessage);
						$scope.commonErrorHandling();
					} else {
						$scope.noNeedofMsg($scope.displayMessage);
						$scope.commonErrorHandling();
						/*System will use the 2 smallest values as the key, if either one is not on the primary key system will take the next available number in the table and look up for third value corresponding to the key from the reference data. System will check if the third dimension is either equal or smaller than the third value derived from the reference data.*/
						var tempSide1 = [];
						var tempSide2 = [];
						var tempSide3 = [];
						var tempSide2_1 = [];
						var first = arrayLWH[0];
						var second = arrayLWH[1];
						var third = arrayLWH[2];
						for (var i = 0; i < $scope.loadabilityMatrixType.length; i++) {
							if (parseInt($scope.loadabilityMatrixType[i].sideNo1) >= first) {
								tempSide1.push(parseInt($scope.loadabilityMatrixType[i].sideNo1));
							}
						}
						var tempSide1Value = closest(first, tempSide1);
						tempSide2 = $.map($scope.loadabilityMatrixType, function (val, key) {
								if ((val.sideNo1) == tempSide1Value) {
									return val;
								}
							});
						for (var i = 0; i < tempSide2.length; i++) {
							if (parseInt(tempSide2[i].sideNo2) >= second) {
								tempSide2_1.push(parseInt(tempSide2[i].sideNo2));
							}
						}
						var tempSide2Value = closest(second, tempSide2_1);
						tempSide3 = $.map($scope.loadabilityMatrixType, function (val, key) {
								if (((val.sideNo1) == tempSide1Value) && ((val.sideNo2) == tempSide2Value)) {
									return val;
								}
							});
						/*If the third dimension is greater than the derived value system will display an onscreen message and the text will be displayed in red*/
						$scope.displayMessage = ShipmentDetailsErrorMsgs.dimensionExceed + index.toString();
						if (third > parseInt(tempSide3[0].sideNo3)) {
							$scope.errorMethod($scope.displayMessage);
							$scope.commonErrorHandling();
							if (third == $scope.shipmentInfo.shipmentlines[index].lineLength) {
								$scope.lengthError[index] = true;
							} else if (third == $scope.shipmentInfo.shipmentlines[index].lineWidth) {
								$scope.widthError[index] = true;
							} else if (third == $scope.shipmentInfo.shipmentlines[index].lineHeight) {
								$scope.heightError[index] = true;
							}
						} else {
							$scope.noNeedofMsg($scope.displayMessage);
							$scope.commonErrorHandling();
							$scope.lengthError[index] = false;
							$scope.widthError[index] = false;
							$scope.heightError[index] = false;
						}
					}
				};
				//Function is used to find the next number present in the list
				function closest(num, arr) {
					var curr = arr[0];
					var diff = Math.abs(num - curr);
					for (var val = 0; val < arr.length; val++) {
						var newdiff = Math.abs(num - arr[val]);
						if (newdiff < diff) {
							diff = newdiff;
							curr = arr[val];
						}
					}
					return curr;
				}
				//Sort in Asc
				function sortAscending(data_A, data_B) {
					return (data_A - data_B);
				}
				//Function is used to remove Zero from the field
				$scope.checkIsZero = function (elementId) {
					var element = $('#' + elementId);
					if (parseFloat(element.val().trim()) <= 0) {
						element.val('');
					}
				};
				//Blur of Pieces
				$scope.pcsBlur = function (index) {
					$scope.slacBlur(index); // Checking the slac validation
					$scope.totalCalculation(index);
					$scope.volumeCal(); // volume calculation
				};
				//Function used to delete error message from the queue
				$scope.noNeedofMsg = function (msg) {
					$scope.alertMsg.remove($scope.displayMessage);
					$scope.displayMessage = ShipmentDetailsErrorMsgs.noNeed;
					$scope.errorMethod($scope.displayMessage);
				};
				//Function used to display delete modal popup on the screen
				$scope.openModalDialogue = function (index) {
					if (index != 0) {
						var modalInstance = $modal.open({
								templateUrl : 'myModalDelete.html',
								controller : 'ModalDelete',
								size : 'md',
								backdrop : 'static',
								windowClass : 'Css-Center-Modal',
								keyboard : false,
								resolve : {
									index : function () {
										return index;
									}
								}
							});
					}
				};
				//Change of service level dropdown
				$scope.serviceLevelFocus = function () {
					$rootScope.serviceHasValue = true;
					manageAwbCntrl.headerData.serviceLevel = $scope.shipmentInfo.serviceLevel ? $scope.shipmentInfo.serviceLevel.key : ""; //Assigning Service Level to header data
					if (manageAwbCntrl.headerData.serviceLevel) {
						focus('noOfPieces0'); //Focus on number of pieces
					}
					/*If shipper is change, make the service level dropdown red, Require Jquery because UI select type ahead is always appended to body, so we are not able to find it with angular*/
					if ($rootScope.serviceLevelChange) {
						angular.element('#serviceLeveldrop .selectize-input').css("border-color", "none");
						$rootScope.serviceLevelChange = false;
					}
					$rootScope.changeOrNotManageawb = true;
				};
			}
		] // END OF CONTROLLER FUNCTION
	};
});
//Delete Shipment Line Controller
app_controllers.controller('ModalDelete', function ($scope, $modalInstance, index, focus, $rootScope, $timeout, shipmentDetailsConstants) {
	$scope.index = index;
	$timeout(function () { //Delay use for IE, As modal popup require some time to load
		focus('deleteShipment'); //Focus on Ok button of delete popup
	}, shipmentDetailsConstants.TIMEOUT_HUNDRED_MS);
	$scope.cancel = function () { //Closing Delete Popup
		$modalInstance.close();
	};
	$scope.ok = function () {
		$modalInstance.close();
		shipmentDetailsScope.removeItem($scope.index); //Remove the shipment line from the shipment info object.
		$rootScope.changeOrNotManageawb = true;
		focus('addRow'); //After remove, focus should be on addRow button.
	};
});