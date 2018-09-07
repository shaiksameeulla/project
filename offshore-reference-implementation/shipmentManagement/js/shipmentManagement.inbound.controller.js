
/**
 * Copyright (c) 2014 Southwest Airlines, Co. 2702 Love Field Drive, Dallas, TX
 * 75235, U.S.A. All rights reserved.
 *
 * This software is the confidential and proprietary information of Southwest
 * Airlines, Co.
 **/
/**
 * This controller acts for inbound shipment tab functionality
 * Non standard Angular Paramters that are passed to this Controller are listed below with their purpose
 
 *  @constructor
 *  @param inboundShipment_Controller - Name of the controller
 *  @param constructor function;
 *  @param {Scope} $scope - controller scope
 *  @param {Root Scope} $rootScope - root level scope
 *  @param {shipmentMgmtService} shipmentMgmtService - Factory for shipment management services
 *  @param {modal} $modal - Modal Service
 *  @param {localStorage} $localStorage - local storage
 *  @param {filter} $filter - Filter service
 *  @param {errorconstant} ERRORMESSAGES - Error message constants
 *  @param {focus} focus - Factory object for focus
 *  @param {focusAttrib} focusAttrib - Factory object for focus for custom dropdown directive
 *  @param CONFIGURATIONS - config.js configurations
 *  @param {roleconstants} roles - list of roles
 *  @param {fetchAirportData} fetchAirportData - Service to fetch airport details
 *  @param ServiceURL  - Service to fetch base URL
*/
app_controllers.controller('ShipmentManagementInboundController', ['$scope', '$filter', '$modal', 'shipmentMgmtService','ERRORMESSAGES','$localStorage','$rootScope'
							,'fetchAirportData','focus','focusAttrib','$timeout','ServiceURL','$interval','CONFIGURATIONS','roles','$location'
							,function($scope, $filter, $modal, shipmentMgmtService,ERRORMESSAGES,$localStorage,$rootScope,fetchAirportData
							,focus,focusAttrib,$timeout,ServiceURL,$interval,CONFIGURATIONS,roles,$location) 
 {	
	$scope.inboundLots = [];
	$scope.commonAWBArray = [];
	$scope.diffAWBArray = [];
	$scope.errorObject = {
		diffAWBError : false
	};
	$scope.assignRunner = true;
	$scope.onChangeStaionFilters = {};
	$scope.errorObject.showMsg = CONFIGURATIONS.shipmentManagent.warning.selectLotToBeFromSameAWBMsg;
	$scope.refreshTimer = CONFIGURATIONS.shipmentManagent.timer;
	$scope.srvLvl = {};
	$scope.role = roles;
	$scope.filterFlags = {};
	$scope.selectedRow = [];
	$scope.inboundProcess = [];
	$scope.errorLabel = ERRORMESSAGES;
	$scope.shipmentInboundSearch = {
		"facility" : $localStorage.userObject.swaLocation === CONFIGURATIONS.shipmentManagent.HDQ ? '' : $localStorage.userObject.swaLocation
	};
	$scope.selectedStation = $scope.shipmentInboundSearch.facility;
	$scope.sendUserdetails = {
		"userId" : $localStorage.userObject.userId,
		"firstName" : $localStorage.userObject.firstName,
		"lastName" : $localStorage.userObject.lastName
	};
	$scope.editRunnerName = false;
	$scope.currentTabname = "";
	$scope.currentTabData = "";
	$scope.currentTabnameTime = "";
	$scope.filteredInboundLots = [];
	$scope.inboundDay = moment($rootScope.currentDate).format("YYYYMMDD");
	$scope.masterDropDown = {};
	$scope.headerChkBox = {};
	$scope.runners = [];
	$scope.selectedRunner = "";
	$scope.manifestDataSelected = {};
	//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
	$scope.sortTyped = CONFIGURATIONS.shipmentManagent.awbNumberAwbPrefix; // Holds initial sorted field for manifest report table
	$scope.sortReverses = false;
	//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
	$scope.sortedfield = CONFIGURATIONS.shipmentManagent.expectedArrTime; // Holds initial sorted field for inbound shipment management table
	$scope.orderState = false;
	$scope.isSubscribed = false;
	/**Get inbound tab data on inbound tab load based on default filter selection .
	 **/
	 
	$scope.$on('fetchingInboundData', function (e) {
		$scope.getInboundResults();
	});
	 
	$scope.getInboundResults = function () {
		$scope.serviceLevelCount = CONFIGURATIONS.shipmentManagent.collectionLenZero;
		$scope.currentTab = CONFIGURATIONS.shipmentManagent.tabInbound;
		$rootScope.$broadcast('displayLoadingBar');
		shipmentMgmtService.searchInboundShipment($scope.selectedStation).then(function (responseData) {
			if (angular.isObject(responseData.data.inboundLots)) {
				$scope.inboundLots = angular.copy(responseData.data.inboundLots);
				$rootScope.shipmentRefereshTime = $rootScope.currentTime + " " + $rootScope.timezone;
				$scope.RSITime = moment($rootScope.currentDate).format('YYYYMMDD') + $rootScope.currentTime;
				$scope.lotStatusData = [CONFIGURATIONS.shipmentManagent.ALL];
				$scope.srvLvlData = [CONFIGURATIONS.shipmentManagent.ALL];
				/**Lot status default filter selection**/
				$scope.lotStatus = [];
				$scope.filterLotStsListInbound = CONFIGURATIONS.shipmentManagent.filterLotStsListInbound;
				angular.forEach($scope.filterLotStsListInbound, function (key, index) {
					$scope.lotStatus[key.key] = true;
				});
				$scope.getFreshState();
				/**Service level default filter selection**/
				angular.forEach($scope.serviceLevel, function (key, index) {
					$scope.serviceLevelCount++;
					$scope.srvLvl[key.key] = true;
				});
				$rootScope.$broadcast('hideLoadingBar');
				$scope.filterApplied = false;

			}
		});

		$scope.warehouseLocationList = [];		
		reqData = {
			"station" : $localStorage.userObject.swaLocation,
			"locType" : [CONFIGURATIONS.shipmentManagent.inboundTab, CONFIGURATIONS.shipmentManagent.bothInboundOutbound]
		};
		shipmentMgmtService.getWarehouseLocation(reqData).then(function (responseData) {
			if (angular.isObject(responseData.data.keyValue)) {
				$scope.warehouseLocationList = angular.copy(responseData.data.keyValue);
			}
		});

		if ($scope.facilityChanged) {
			$scope.selectedStation = $scope.shipmentInboundSearch.facility;
		} else {
			$scope.selectedStation = $localStorage.userObject.swaLocation;
		}

		if ($rootScope.handShakeSuccess) {
			if (!$scope.isSubscribed) {
				$scope.subscribe($scope.selectedStation);
			} else {
				$scope.updateSubscription();
			}
		}

		$rootScope.$emit('missingFromNotification', $localStorage.fromNotification);
	};
	/**On successful cometD handshake subcribe chanel with selected station
	 *@param Event
	 *@param String
	 **/
	$rootScope.$on('handshakeSuccessfull', function (e, data) {
		if (!$scope.isSubscribed) {
			$scope.subscribe($scope.selectedStation);
		} else {
			$scope.updateSubscription();
		}
	});
	
	/**This function is invoked-  Missing tab from load by notification link Click
	 *@param Event
	 *@param String
	 **/
	$rootScope.$on('missingFromNotification', function (e, data) {
		if (data) {
			if ($rootScope.has_permission($scope.role.role_VIEW_MISSING_SHIPMENTS)) {
				$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.MISSING);
				$timeout(function () {
					$scope.loadMissingdata();
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
				}, CONFIGURATIONS.TIMEOUT_TWO_THOUSAND_MS);
			}

			$localStorage.fromNotification = false;
		}
	});
	
	/**This function is to check if selected station is not same as home station . Remove privilage.
	 * @Param selected station
	 **/
	$scope.validateStationCode = function (selectedStation) {
		$scope.selectedStation = selectedStation;
		if (selectedStation !== $localStorage.userObject.swaLocation) {
			$scope.getFreshState();
			$scope.facilityChanged = true;

		} else {
			$scope.facilityChanged = false;
		}
		$scope.shipmentFacilityChanged = $scope.errorLabel.facilityChanged + $scope.selectedStation;
		$timeout(function () {
			$scope.shipmentFacilityChanged = '';
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		}, CONFIGURATIONS.TIMEOUT_FIVE_THOUSAND_MS);
		switch ($scope.currentTab) {
		case 'xfr':
			if (!$scope.isSubscribed) {
				$scope.subscribe($scope.selectedStation);
			} else {
				$scope.updateSubscription();
			}
			$scope.$broadcast('fetchingTransferDataForStationChanged');
			break;
		case 'outbound':
			if (!$scope.isSubscribed) {
				$scope.subscribe($scope.selectedStation);
			} else {
				$scope.updateSubscription();
			}
			$scope.$broadcast('fetchingOutboundDataForStationChanged');
			break;
		case 'missing':
			$scope.$broadcast('fetchingMissingDataForStationChanged');
			break;
		case 'delivered':
			$scope.$broadcast('fetchingDeliverDataForStationChanged');
			break;
		default:
			$scope.getDataBasedOnState();

		}

	};
	/***Get airport code / warehouse location**/
	if (angular.isObject(fetchAirportData.data.referenceDataHolder.airport_facility[0])) {
		$scope.facilityOptions = JSON.parse(fetchAirportData.data.referenceDataHolder.airport_facility[0].value);
		$scope.warehouseLocationData = JSON.parse(fetchAirportData.data.referenceDataHolder.wareHouseLocation[0].value);
		$scope.serviceLevel = JSON.parse(fetchAirportData.data.referenceDataHolder.serviceLevel[0].value);
		$localStorage.wareHouseLocation = angular.copy($scope.warehouseLocationData);
		$localStorage.serviceLevel = angular.copy($scope.serviceLevel);

		/**Service level default filter selection**/
		angular.forEach($scope.serviceLevel, function (key, index) {
			$scope.srvLvl[key.key] = true;
		});
	}
	/** Stop other tab auto refresh interval
	 * @param shipmentTab
	 **/
	$scope.stopOtherTimers = function (shipmentTab) {
		$scope.$broadcast('shipmentTab', shipmentTab);
	};
		
     /**This function is to select / desect all the filters
      * @param string 'SELECTALL'
      **/
     $scope.selectAll = function (from) {
     	$scope.filterLotStsListInbound = CONFIGURATIONS.shipmentManagent.filterLotStsListInbound;
     	if (from === CONFIGURATIONS.shipmentManagent.SELECTALL) {
     		angular.forEach($scope.filterLotStsListInbound, function (key, index) {
     			$scope.lotStatus[key.key] = true;
     		});
     		$scope.srvLvl = {};
     		/**SERVICE LEVEL**/
     		angular.forEach($scope.serviceLevel, function (key, index) {
     			$scope.srvLvl[key.key] = true;
     		});
     	} else {
     		angular.forEach($scope.filterLotStsListInbound, function (key, index) {
     			$scope.lotStatus[key.key] = false;
     		});

     		$scope.srvLvl = {};
     	}

     };
	/**Check if selected AWB are having same lot status , or show error message
	 *
	 **/
	$scope.diffAWBFlagStatus = function () {
		if ($scope.commonAWBArray.length > 1) {
			var refAWBNo = $scope.commonAWBArray[0];
				for (i = 1; i <= $scope.commonAWBArray.length - 1; i++) {
					if ($scope.commonAWBArray[i] != refAWBNo) {

						$scope.diffAWBErrorFlag = true;
						break;
					} else {
						$scope.diffAWBErrorFlag = false;
						$scope.errorObject.diffAWBError = false;

					}
				}
		} else {
			$scope.diffAWBErrorFlag = false;
		}
	};
	/**Enable only same lot status in table
	 * @param index
	 * @paaram boolean
	 **/
	$scope.checkLotStatus = function (data, index) {
		$scope.allowToCheck = data;
		count = 0;
		$scope.rcvDlvrCheck = $scope.inboundLots[index].lotStatusStation;

		if ($scope.selectedRow[index] === -1) {
			var index = $scope.commonAWBArray.indexOf($scope.inboundLots[index].awbNumber);
			if (index > -1) {
				$scope.commonAWBArray.splice(index, 1);
			}
		} else {
			$scope.commonAWBArray.push($scope.inboundLots[index].awbNumber);
		}

		$scope.diffAWBFlagStatus();

		angular.forEach($scope.selectedRow, function (value) {
			if (value != null && value != "-1") {
				count++;
			}
		});
		/**Check to reset the checkbox condition**/
		if (count == 0) {
			$scope.allowToCheck = "";
			$scope.accessPermission();
		} else {
			$scope.accessPermission();
		}
	};
	
	/**This function is to enable desable top buttons bases on outbound table row selection
	 **/
	$scope.accessPermission = function () {
		// modified as code review comments.. starts !
		if ($scope.allowToCheck && !$scope.privilegeProvide && $scope.shipmentInboundSearch.facility === $localStorage.userObject.swaLocation) {

			switch ($scope.allowToCheck) {
			case 'RCS':
				$scope.receive = true;
				$scope.undoReceive = false;
				$scope.deliver = false;
				$scope.receiveDeliver = false;
				$scope.assignRunner = true;
				break;
			case 'DEP':
				$scope.receive = true;
				$scope.undoReceive = false;
				$scope.receiveDeliver = false;
				$scope.assignRunner = true;
				$scope.deliver = false;
				break;
			case 'MSNG':
			case 'TRC':
				$scope.receive = false;
				$scope.undoReceive = false;
				$scope.deliver = false;
				$scope.assignRunner = true;
				$scope.receiveDeliver = false;
				break;
			case 'RCF':
				$scope.receive = false;
				$scope.undoReceive = true;
				$scope.receiveDeliver = false;
				$scope.deliver = true;
				$scope.assignRunner = false;
				break;
			case 'XFOH':
				$scope.receive = true;
				$scope.undoReceive = true;
				$scope.receiveDeliver = true;
				$scope.deliver = false;
				$scope.assignRunner = true;
				break;
			case 'ARR':
				$scope.undoReceive = false;
				$scope.deliver = false;
				$scope.assignRunner = false;
				break;
			}
			/**COMMON PERMISSION**/
			$scope.discrepancy = true;
			$scope.printAssignment = true;
			$scope.privilegeProvide = true;

			if ($scope.rcvDlvrCheck === 'ARR - ' + $scope.shipmentInboundSearch.facility) {
				$scope.receive = true;
				$scope.receiveDeliver = true;
			} 
		} else if ($scope.privilegeProvide && !$scope.allowToCheck) {
			$scope.getFreshState();
		}
		// modified as code review comments.. ends !
	};
	
	/**This function is to populate data in tables in popups
	 * Data displayed acording to rows selected in outbound table and seleted popup
	 * @param string popupName
	 **/
	$scope.getSelectedRowValue = function (popupName) {
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (popupName === CONFIGURATIONS.shipmentManagent.discrepancyPopup && $scope.diffAWBErrorFlag) {
			$scope.errorObject.diffAWBError = true;

		} else {
			$scope.inboundProcess = [];
			$scope.commonData = {};
			if (angular.isArray($scope.selectedRow)) {
				angular.forEach($scope.selectedRow, function (value) {
					if (value != null && value != "-1") {
						$scope.inboundProcess.push($scope.createInboundPopupsScope(value, popupName));
					}
				});
			}
			$scope.commonData.empId = $localStorage.userObject.userId;
				//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
			if (popupName === CONFIGURATIONS.shipmentManagent.receivePopup || popupName === CONFIGURATIONS.shipmentManagent.discrepancyPopup) {
				$scope.masterDropDown.warehouseLocation = "";
				$scope.commonData.receiveDateTime = $rootScope.currentDate;
				$scope.commonData.seconds=$rootScope.timeInSeconds;
				$scope.commonData.receiveTime = moment($rootScope.currentTime, 'HHmm').format('HH:mm');
				if (popupName === CONFIGURATIONS.shipmentManagent.receivePopup) {
					$scope.receiveForm.$setPristine();
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
					$timeout(function () {
						focusAttrib("data-id='warehouseLocationsInboundReceive'");
					}, CONFIGURATIONS.TIMEOUT_FOUR_HUNDRED_MS);
				}
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
				if (popupName === CONFIGURATIONS.shipmentManagent.discrepancyPopup &&
						($scope.has_permission($scope.role.role_MARK_DISCREPANCY_SHIPMENT_MISSING))) {
					$scope.discrepancyForm.$setPristine();
					$scope.commonData.discrepancyOption = CONFIGURATIONS.shipmentManagent.MSNG;
				}
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
			} else if (popupName === CONFIGURATIONS.shipmentManagent.deliverPopup || popupName === CONFIGURATIONS.shipmentManagent.receiveDeliverPopup) {
				if (popupName === CONFIGURATIONS.shipmentManagent.receiveDeliverPopup) {
					$scope.deliverReceiveForm.$setPristine();
				}
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
				if (popupName === CONFIGURATIONS.shipmentManagent.deliverPopup) {
					$scope.deliverForm.$setPristine();
				}
				$scope.commonData.customerName = '';
				$scope.commonData.deliverDateTime = $rootScope.currentDate;
				$scope.commonData.deliverDateTimeStr = moment($rootScope.currentTime, 'HHmm').format('HH:mm');
			}

		}
	};
	/**This function is remove error indicator on selection of same AWB

	 **/

	$scope.removeError = function () {
		$scope.errorObject.diffAWBError = false;
	};

	var orderBy = $filter('orderBy'); //represents sorting filter

	/**This function is to do sorting on inbound lots based on field header click and sorting state (assending /decending)
	 * @param string selected field header , boolean sorting state
	 **/
	$scope.order = function (predicate, reverse) {
		if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
			angular.forEach($scope.inboundLots, function (inboundLots) {
				inboundLots.flightNumber = parseInt(inboundLots.flightNumber);
			});
		}
		$scope.inboundLots = orderBy($scope.inboundLots, predicate, reverse);
		if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
			angular.forEach($scope.inboundLots, function (inboundLots) {
				inboundLots.flightNumber = inboundLots.flightNumber.toString();
			});
		}

		$scope.sortedfield = predicate;
		$scope.orderState = reverse;
	};

	/** Method for Assign Runner / Print Assignment Shorting
	 *  @param string selected field header , boolean sorting state
	 **/

	$scope.orderRunnerDetails = function (collection, predicate, reverse) {
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (collection === CONFIGURATIONS.shipmentManagent.printAssignmentPopup) {

			if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
				angular.forEach($scope.filteredInboundLots, function (lot) {
					lot.flightNumber = parseFloat(lot.flightNumber);
				});
			}

			$scope.filteredInboundLots = orderBy($scope.filteredInboundLots, predicate, reverse);

			if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
				angular.forEach($scope.filteredInboundLots, function (lot) {
					lot.flightNumber = lot.flightNumber.toString();
				});
			}
		} else if (collection ===  CONFIGURATIONS.shipmentManagent.assignRunnerPopup) {
			if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
				angular.forEach($scope.assignRunnerRecords, function (lot) {
					lot.flightNumber = parseFloat(lot.flightNumber);
				});
			}
			if (predicate !== CONFIGURATIONS.shipmentManagent.date) {
				$scope.assignRunnerRecords = orderBy($scope.assignRunnerRecords, predicate, reverse);
			} else {
				$scope.assignRunnerRecords = orderBy($scope.assignRunnerRecords, CONFIGURATIONS.shipmentManagent.expectedArrTime, reverse);
			}
			if (predicate === CONFIGURATIONS.shipmentManagent.flightNumber) {
				angular.forEach($scope.assignRunnerRecords, function (lot) {
					lot.flightNumber = lot.flightNumber.toString();
				});
			}

		}

	};
	/**This function is to populate data in tables for popups
	 * Data displayed acording to rows selected in outbound table and seleted popup
	 * @param string popupName
	 * @param index
	 **/
	$scope.createInboundPopupsScope = function (value, popupName) {
		var tmpValues = {};

		/* *  Common data to fetch * */
		tmpValues.awbVersion = $scope.inboundLots[value].awbVersion;
		tmpValues.awbPrefix = $scope.inboundLots[value].awbPrefix;
		tmpValues.awbNumber = $scope.inboundLots[value].awbNumber;
		tmpValues.lotNumber = $scope.inboundLots[value].lotNumber;
		tmpValues.totalPieces = $scope.inboundLots[value].noOfPieces;
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (popupName === CONFIGURATIONS.shipmentManagent.receivePopup) {
			tmpValues.warehouseLocation = "";
			tmpValues.piecesReceived = tmpValues.totalPieces;
		} else if (popupName === CONFIGURATIONS.shipmentManagent.deliverPopup || popupName === CONFIGURATIONS.shipmentManagent.receiveDeliverPopup) {
			tmpValues.piecesDelivered = tmpValues.totalPieces;
		}

		return tmpValues;
	};
	
	/** Functionality for "Undo receive" button on top.
	 **/
	$scope.undoReceiveLots = function () {
		var selectedUndoLots = [];
		if (!angular.equals([], $scope.selectedRow)) {
			angular.forEach($scope.selectedRow, function (value) {
				if (value != null && value != "-1") {
					var tempUndoLots = {};
					tempUndoLots.awbVersion = $scope.inboundLots[value].awbVersion;
					tempUndoLots.awbPrefix = $scope.inboundLots[value].awbPrefix;
					tempUndoLots.awbNumber = $scope.inboundLots[value].awbNumber;
					tempUndoLots.lotNumber = $scope.inboundLots[value].lotNumber;
					selectedUndoLots.push(tempUndoLots);
				}
			});
			var undoLotData = {
				//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
				'tabName' : CONFIGURATIONS.shipmentManagent.inboundTab,
				'undoLots' : selectedUndoLots
			};
			shipmentMgmtService.undoLots(undoLotData).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		}
	};
	/**Get runners and inbound data grouped by unique flight numbers
	 * @param string popupName
	 **/
	$scope.getAssignRunnner = function (popupName) {

		$scope.selectedRunner = "";
		$scope.assignRunnerFormValid = false;
		$scope.headerChkBox.selected = false;
		$scope.headerChkBox = {};
		$scope.runners = [];
		$scope.selectedRunner = "";
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (popupName === CONFIGURATIONS.shipmentManagent.printAssignmentPopup) {
			$scope.filteredInboundLots = [];
			$scope.commonData = {};
			// changed recieved Date & Time to represent User Time @24th March,16 ,defect ID : #5310
			$scope.commonData.receiveDate = $rootScope.currentDate;//moment().format('MM/DD/YYYY');
			$scope.commonData.receiveTime = moment($rootScope.currentTime, 'HHmm').format('HH:mm');//moment().format('HH:mm');
			$scope.printAssignRunnerForm.$setPristine();
		} else {
			$scope.assignRunnerForm.$setPristine();
		}
		// Service call on assign runner popup click
		shipmentMgmtService.getInboundRunner().then(function (responseData) {
			if (angular.isObject(responseData.data.inbFlightDetails)) {
				if (popupName === CONFIGURATIONS.shipmentManagent.assignRunnerPopup) {
					$scope.assignRunnerRecords = responseData.data.inbFlightDetails;
					angular.forEach($scope.assignRunnerRecords, function (row, index) {
						$scope.assignRunnerRecords[index].itemSelected = false;
					});
					$('#assignRunner').modal('show');
				}
			}
			if (angular.isObject(responseData.data.assignedRunners)) {
				$scope.runners = responseData.data.assignedRunners;
			}

		});

	};
	
	/** Warewhouse location master dropdown changes in receive popup
	 *  @param selected value
	 **/
	$scope.upateMasterLocationValue = function (selectValue) {
		if (selectValue) {
			angular.forEach($scope.inboundProcess, function (row, index) {
				$scope.inboundProcess[index].warehouseLocation = selectValue;
			});
		} else {
			angular.forEach($scope.inboundProcess, function (row, index) {
				$scope.inboundProcess[index].warehouseLocation = "";
			});
		}
	};
	/** Function is to create RECEIVE INBOUND Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendReceiveInbound = function (popupId) {
		var data;
		data = {
			"receiveLots" : $scope.inboundProcess,
			"tabName" :  CONFIGURATIONS.shipmentManagent.inboundTab,
			"empId" : $scope.commonData.empId,
			"receiveDateTimeStr" : ($scope.commonData.receiveDateTime + " " + $scope.commonData.receiveTime),
			"station" : $scope.selectedStation
		};
		$scope.popUpSaveService(data, popupId);
	};
	
	
	/** Function is to create DELIVER INBOUND Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendDeliverInbound = function (popupId) {
		var data;
		data = {
			"deliverLots" : $scope.inboundProcess,
			"tabName" :  CONFIGURATIONS.shipmentManagent.inboundTab,
			"empId" : $scope.commonData.empId,
			"deliveredDateTimeStr" : ($scope.commonData.deliverDateTime + " " + $scope.commonData.deliverDateTimeStr),
			"customerName" : $scope.commonData.customerName,
			"station" : $scope.selectedStation
		};
		$scope.popUpSaveService(data, popupId);
	};
	
	
	/** Function is to create receive and deliver inbound Popup request data and invoke save popup service call
	 * @param string popupid
	**/
	$scope.sendReceiveDeliverInbound=function(popupId)
	{
	  var data;
	  //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
	  data={"deliverLots":$scope.inboundProcess,"tabName": CONFIGURATIONS.shipmentManagent.inboundTab,"empId": $scope.commonData.empId,"deliveredDateTimeStr": ($scope.commonData.deliverDateTime+" "+ $scope.commonData.deliverDateTimeStr),"customerName":$scope.commonData.customerName, "station": $scope.selectedStation};
	  $scope.popUpSaveService(data,popupId);
	};
	
		/** Function is to reset tje seconds if user updates date Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	 $scope.resetSeconds=function(){
	   $scope.commonData.seconds=CONFIGURATIONS.defaultSeconds;
	 };
	 
	
	/** Function is to create DISCREPANCY SHIPMENTS Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendDiscrepancyInbound = function (popupId) {
		var data;
		data = {
			"discrepancyLotList" : $scope.inboundProcess,
			"tabName" :  CONFIGURATIONS.shipmentManagent.inboundTab,
			"status" : $scope.commonData.discrepancyOption,
			"empId" : $scope.commonData.empId,
			// Added to capture Seconds for Discrepancy.Defect id : 5076.
			"discrepancyDateTime" : moment($scope.commonData.receiveDateTime).format('YYYYMMDD') +
									$scope.commonData.receiveTime.toString().substring(0, 2) + 
									$scope.commonData.receiveTime.toString().substring(3, 5)+
									$scope.commonData.seconds,
			"notes" : $scope.commonData.userComments,
			"station" : $scope.selectedStation,
			"updatedBy" : null
		};

		$scope.popUpSaveService(data, popupId);
	};
	
	
	/** Function is to create PRINT RECEIVE AND DELIVER INBOUND Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendPrintReceiveDeliverInbound = function (popupId) {
		var data;
		data = {
			"deliverLots" : $scope.inboundProcess,
			"tabName" :  CONFIGURATIONS.shipmentManagent.inboundTab,
			"empId" : $scope.commonData.empId,
			"deliveredDateTimeStr" : ($scope.commonData.deliverDateTime + " " + $scope.commonData.deliverDateTimeStr),
			"customerName" : $scope.commonData.customerName,
			"station" : $scope.selectedStation
		};
		$scope.popUpSaveService(data, popupId);

	};
	/** Function is to create ASSIGN LOCATION INBOUND Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendAssignLocationInbound = function (popupId) {
		var data;
		data = {
			"receiveLots" : $scope.inboundProcess,
			"tabName" : $scope.currentTabname,
			"updatedBy" : $scope.sendUserdetails,
			"empId" : $scope.commonData.empId,
			"receiveDateTimeStr" : ($scope.commonData.receiveDateTime + " " + $scope.commonData.receiveTime),
			"customerName" : $scope.commonData.customerName
		};
		$scope.popUpSaveService(data, popupId);

	};
	/** Function is to create ASSIGN RUNNER INBOUND Popup request data and invoke save popup service call
	 * @param string popupid
	 **/
	$scope.sendAssignRunnerInbound = function (popupId) {
		var data;
		var inboundRunners = [];
		angular.forEach($scope.assignRunnerRecords, function (runnerDetail) {
			if (runnerDetail.itemSelected) {
				inboundRunners.push({
					"expectedArrTime" : runnerDetail.expectedArrTime,
					"inGateNumber" : runnerDetail.inGateNumber,
					"flightNumber" : runnerDetail.flightNumber
				});
			}
		});
		data = {
			"inboundRunners" : inboundRunners,
			"station" : $localStorage.userObject.swaLocation,
			"assignedRunners" : $scope.selectedRunner
		};

		$scope.popUpSaveService(data, popupId);
		 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (popupId === CONFIGURATIONS.shipmentManagent.assignAndPrintRunner && $scope.selectedRunner === "") {
			/** This block of code is to show error message if user has not selected any runner from runner dropdown and clicked on print assign runner**/
			$rootScope.warningMsg = CONFIGURATIONS.shipmentManagent.warning.noRunnerAssignedWarning;
			$modal.open({
				templateUrl : 'confirmBox/warning_box.html',
				size : 'sm',
				backdrop : 'static',
				windowClass : 'Css-Center-Modal',
				keyboard : false,
				scope : $scope,
				controller : function ($scope, $modalInstance) {
					$scope.ok = function () {

						$modalInstance.dismiss('ok');

					};
				}
			});
		}

	};
	/** A common method to save modified data for popups into backend.
	 * @pram object - data to save.
	 * @param string  - popup name.
	 **/
	$scope.popUpSaveService = function (data, popupId) {
		$scope.isResponse = false;
		 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
		if (popupId === CONFIGURATIONS.shipmentManagent.receivePopup) {
			shipmentMgmtService.receiveShpmnts(data).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.deliverPopup) {
			shipmentMgmtService.deliverShpmnts(data).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.receivedeliver) {
			shipmentMgmtService.receivedeliverShpmnts(data).then(function (responseData) {

				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.receivedeliverPrint) {
			shipmentMgmtService.receivedeliverShpmnts(data).then(function (responseData) {
				$scope.printDeliverManifestInbound(data);
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.assignLocPopup) {
			shipmentMgmtService.assignLocation(data).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.assignRunnerPopup) {
			shipmentMgmtService.assignRunner(data).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});

		} else if (popupId === CONFIGURATIONS.shipmentManagent.discrepancyPopup) {
			shipmentMgmtService.discrepancy(data).then(function (responseData) {
				$scope.getDataBasedOnState();
				$scope.getFreshState();
			});
		} else if (popupId === CONFIGURATIONS.shipmentManagent.assignAndPrintRunner) {
			shipmentMgmtService.assignRunner(data).then(function (responseData) {
				if (responseData.data.updateRunner == true && $scope.selectedRunner != "") {
					$("#assignRunner").modal("hide");
					$scope.getDataBasedOnState();
					$scope.getFreshState();
					var sessionTime = $filter('colonFilter')($rootScope.currentDateTime);
					var baseurl = ServiceURL.baseUrl; // Fixed for picking session time for reports .Added field assignedDateTimeZone
					// changed assignedDateStr . Now this will consist Date as per User Time @24th March,16 ,defect ID : #5310
					var shpmyPrint = "&runner=" + $scope.selectedRunner + "&assignedDateStr=" + moment($rootScope.currentDateTime, 'MM/DD/YYYY HHmm z').format("YYYYMMDDHHmm")+ "&assignedDateTimeZone=" +sessionTime;
					window.open(baseurl + '/shpmnt/printIBRunnerAssgnmt?' + shpmyPrint + "&station=" + $localStorage.userObject.swaLocation);
				}
			});

		}
	};
	/**Function to fetch inbound data from backend based on filter selection.
	 **/
	$scope.applyFilter = function () {
		$scope.filter = {};
		$scope.appliedFilter = {};
		$scope.filter.lotStatus = [];
		$scope.filter.serviceLevel = [];

		$scope.filter.station = $scope.selectedStation;
		if ($scope.lotStatus.ARR === true){
			$scope.filter.lotStatus.push(CONFIGURATIONS.shipmentManagent.ARR);
		}if ($scope.lotStatus.DEP === true){
			$scope.filter.lotStatus.push(CONFIGURATIONS.shipmentManagent.DEP);
		}if ($scope.lotStatus.RCS === true){
			$scope.filter.lotStatus.push(CONFIGURATIONS.shipmentManagent.RCS);
		}if ($scope.lotStatus.RCF === true){
			$scope.filter.lotStatus.push(CONFIGURATIONS.shipmentManagent.RCF);
		}if ($scope.lotStatus.XFOH === true){
			$scope.filter.lotStatus.push(CONFIGURATIONS.shipmentManagent.XFOH);
		}
		/**Service level checking**/
		angular.forEach($scope.srvLvl, function (value, key) {
			if (value === true) {
				$scope.filter.serviceLevel.push(key);
			}
		});
		/**Filtered notification**/
		$scope.lotStatusData = ($scope.filter.lotStatus.length != CONFIGURATIONS.shipmentManagent.inboundFilterLotStatusesTotalCount) ? $scope.filter.lotStatus : [CONFIGURATIONS.shipmentManagent.ALL];
		$scope.srvLvlData = ($scope.filter.serviceLevel.length != $scope.serviceLevelCount) ? $scope.filter.serviceLevel : [CONFIGURATIONS.shipmentManagent.ALL];
		$scope.appliedFilter.serviceLevel = $scope.filter.serviceLevel;
		$scope.appliedFilter.lotStatus = $scope.filter.lotStatus;
		$scope.appliedFilter.station = $scope.filter.station;
		$rootScope.$broadcast('displayLoadingBar');
		shipmentMgmtService.applyFliterInboundShipment($scope.filter).then(function (responseData) {
			$scope.filterApplied = true;
			$scope.inboundLots = responseData.data.inboundLots;
			$rootScope.shipmentRefereshTime = $rootScope.currentTime + " " + $rootScope.timezone;
			$scope.allowToCheck = false;
			$scope.getFreshState();
			$scope.accessPermission();
			$rootScope.$broadcast('hideLoadingBar');
		});
	};

    /**This method is to get manifest popup data .
     * @pram object - tabdata to save.
     * @param string  - tab name.
     * @param object - tab data details
     * @param time
     **/
    $scope.getManifestPopupwindow = function (tabname, tabData, tabDataDetails, time) {
    	$scope.currentTabname = tabname;
    	$scope.currentTabData = tabData;
    	$scope.currentTabnameTime = time;
    	$scope.manifestPopupDetails = [];
    	$scope.inbound = {};

    	$scope.popupDataSend = {};

    	if (!tabDataDetails.lot.flightLegDepartureDate) {
    		tabDataDetails.lot.flightLegDepartureDate = "";
    	}
    	if (!tabDataDetails.lot.flightLegSuffix) {
    		tabDataDetails.lot.flightLegSuffix = "";
    	}

    	if (tabDataDetails.lot.carrierCode) {
    		$scope.manifestDataSelected.carrier = tabDataDetails.lot.carrierCode;
    	} else {
    		tabDataDetails.lot.carrierCode = CONFIGURATIONS.shipmentManagent.carrierWN;
    		$scope.manifestDataSelected.carrier = tabDataDetails.lot.carrierCode;
    	}
		 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
    	if ($scope.currentTabnameTime === CONFIGURATIONS.shipmentManagent.ETA) {
    		$scope.manifestDataSelected.flightNumber = tabDataDetails.lot.flightNumber;

    		if ($scope.currentTabname ===  CONFIGURATIONS.shipmentManagent.tabInbound && $scope.currentTabData === CONFIGURATIONS.shipmentManagent.tabTransfer) {
    			$scope.manifestDataSelected.originStr = tabDataDetails.lot.inFlightLegOrigin;
    			$scope.manifestDataSelected.destination = tabDataDetails.lot.inFlightLegDestination;

    			if (tabDataDetails.lot.scheduledArrTime) {
    				$scope.manifestDataSelected.date = moment(tabDataDetails.lot.scheduledArrTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    			} else {
    				$scope.manifestDataSelected.date = "";
    			}

    			if (tabDataDetails.lot.inExpectedArrTime) {
    				$scope.manifestDataSelected.estimatedArrivalTime = moment(tabDataDetails.lot.inExpectedArrTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			} else {
    				$scope.manifestDataSelected.estimatedArrivalTime = "";
    			}

    			if (tabDataDetails.lot.inExpectedDepTime) {
    				$scope.manifestDataSelected.estimatedDepartureTime = moment(tabDataDetails.lot.inExpectedDepTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			} else {
    				$scope.manifestDataSelected.estimatedDepartureTime = "";
    			}
    		} else {
    			$scope.manifestDataSelected.originStr = tabDataDetails.lot.origin.code;
    			$scope.manifestDataSelected.destination = tabDataDetails.lot.destination.code;
    			if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabInbound ) {
    				$scope.manifestDataSelected.originStr = tabDataDetails.lot.flightLegOrigin;
    				$scope.manifestDataSelected.destination = tabDataDetails.lot.flightLegDestination;
    			}
    			$scope.manifestDataSelected.date = moment(tabDataDetails.lot.scheduledArrTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    			$scope.manifestDataSelected.estimatedArrivalTime = moment(tabDataDetails.lot.expectedArrTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			$scope.manifestDataSelected.estimatedDepartureTime = moment(tabDataDetails.lot.expectedDepTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    		}

    		$scope.manifestDataSelected.inbound = true;
		 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
    	} else if ($scope.currentTabnameTime === CONFIGURATIONS.shipmentManagent.ETD) {

    		if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabInbound  && $scope.currentTabData === CONFIGURATIONS.shipmentManagent.tabTransfer) {
    			$scope.manifestDataSelected.flightNumber = tabDataDetails.lot.outGoingFlightNumber;
    			$scope.manifestDataSelected.originStr = tabDataDetails.lot.outFlightLegOrigin;
    			$scope.manifestDataSelected.destination = tabDataDetails.lot.outFlightLegDestination;

    			if (tabDataDetails.lot.scheduledDepTime) {
    				$scope.manifestDataSelected.date = moment(tabDataDetails.lot.scheduledDepTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    			} else {
    				$scope.manifestDataSelected.date = "";
    			}

    			if (tabDataDetails.lot.outExpectedArrTime) {
    				$scope.manifestDataSelected.estimatedArrivalTime = moment(tabDataDetails.lot.outExpectedArrTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			} else {
    				$scope.manifestDataSelected.estimatedArrivalTime = "";
    			}

    			if (tabDataDetails.lot.outExpectedDepTime) {
    				$scope.manifestDataSelected.estimatedDepartureTime = moment(tabDataDetails.lot.outExpectedDepTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			} else {
    				$scope.manifestDataSelected.estimatedDepartureTime = "";
    			}
    		} else {
    			$scope.manifestDataSelected.flightNumber = tabDataDetails.lot.flightNumber;
    			$scope.manifestDataSelected.originStr = tabDataDetails.lot.origin.code;
    			$scope.manifestDataSelected.destination = tabDataDetails.lot.destination.code;
    			if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabOutbound) {
    				$scope.manifestDataSelected.originStr = tabDataDetails.lot.flightLegOrigin;
    				$scope.manifestDataSelected.destination = tabDataDetails.lot.flightLegDestination;
    			}
    			$scope.manifestDataSelected.date = moment(tabDataDetails.lot.scheduledDepTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    			$scope.manifestDataSelected.estimatedArrivalTime = moment(tabDataDetails.lot.expectedArrTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    			$scope.manifestDataSelected.estimatedDepartureTime = moment(tabDataDetails.lot.expectedDepTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    		}

    		$scope.manifestDataSelected.inbound = false;
    	} else {
    		$scope.manifestDataSelected.date = moment(tabDataDetails.lot.expectedArrTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    		$scope.manifestDataSelected.flightNumber = tabDataDetails.lot.flightNumber;
    		$scope.manifestDataSelected.date = moment(tabDataDetails.lot.expectedArrTime.toString().substr(0, 8), 'YYYYMMDD').format('MM/DD/YYYY');
    		$scope.manifestDataSelected.estimatedArrivalTime = moment(tabDataDetails.lot.expectedArrTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    		$scope.manifestDataSelected.estimatedDepartureTime = moment(tabDataDetails.lot.expectedDepTime.toString().substr(8, 12), 'HHmm').format('HH:mm');
    		$scope.manifestDataSelected.inbound = false;
    	}

    	$scope.manifestDataSelected.flightLegSuffix = tabDataDetails.lot.flightLegSuffix;
    	$scope.manifestDataSelected.flightLegDepartureDate = tabDataDetails.lot.flightLegDepartureDate;
    	$scope.manifestDataSelected.lockDown = false;

    	$scope.popupDataSend = $scope.manifestDataSelected;
    	$scope.getManifestPopup($scope.popupDataSend);

    };
  /**This method  is to load manifest popup data .
   * @pram object - popup data to save.
   **/
  $scope.getManifestPopup = function (popupdata) {
  	$scope.manifestPopupDetails.mainFlagAsterisk = false;
  	shipmentMgmtService.getManifestPopup(popupdata).then(function (responseData) {
  		if (angular.isObject(responseData.data) && responseData.data != '') {
  			$scope.manifestPopupDetails = responseData.data;
  			$scope.manifestPopupDetails.carrierType = $scope.manifestPopupDetails.carrier;
  			$scope.manifestPopupDetails.inbound = $scope.currentTabname;

  			/***Data from Flight Table**/
  			$scope.manifestPopupDetails.originStr = popupdata.originStr;
  			$scope.manifestPopupDetails.destination = popupdata.destination;
  			$scope.manifestPopupDetails.estimatedArrivalTime = popupdata.estimatedArrivalTime;
  			$scope.manifestPopupDetails.estimatedDepartureTime = popupdata.estimatedDepartureTime;
  			$scope.manifestPopupDetails.flightNumber = popupdata.flightNumber;
  			$scope.manifestPopupDetails.date = moment(popupdata.date).format('DD MMM');
			 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
  			if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabInbound ) {
				//Modified for defect #2255 Start
  				$scope.manifestPopupDetails.inbound = CONFIGURATIONS.shipmentManagent.headerInbound ;
				//Modified for defect #2255 end
				//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
  				$scope.manifestPopupDetails.flightServiceNameDetail = CONFIGURATIONS.shipmentManagent.consignee;
				//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
  				$scope.manifestPopupDetails.terminatingLabel = CONFIGURATIONS.shipmentManagent.terminating;
  				$scope.manifestPopupDetails.timeLabel = CONFIGURATIONS.shipmentManagent.ETA;
  				$scope.manifestPopupDetails.timeValue = $scope.manifestPopupDetails.estimatedArrivalTime;
  				$scope.manifestPopupDetails.orginatingTerminatingShipments = $scope.manifestPopupDetails.terminatingShipments;
  			} else if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabOutbound) {
				 //@CodeReviewFix:03-2016 Hard coded data has been moved to constant. Modified for defect #2255 start
  				$scope.manifestPopupDetails.inbound = CONFIGURATIONS.shipmentManagent.headerOutbound;
				//Modified for defect #2255 end
  				$scope.manifestPopupDetails.flightServiceNameDetail = CONFIGURATIONS.shipmentManagent.shipper;
  				$scope.manifestPopupDetails.terminatingLabel = CONFIGURATIONS.shipmentManagent.originating;
  				$scope.manifestPopupDetails.timeLabel = CONFIGURATIONS.shipmentManagent.ETD;
  				$scope.manifestPopupDetails.timeValue = $scope.manifestPopupDetails.estimatedDepartureTime;
  				$scope.manifestPopupDetails.orginatingTerminatingShipments = $scope.manifestPopupDetails.originatingShipments;
  				$scope.manifestPopupDetails.totalShipmentsTerminating = $scope.manifestPopupDetails.totalShipmentsOriginating;
  			}

  			if ($scope.currentTabname === CONFIGURATIONS.shipmentManagent.tabInbound  && $scope.currentTabData === CONFIGURATIONS.shipmentManagent.tabTransfer) {
  				if ($scope.currentTabnameTime === CONFIGURATIONS.shipmentManagent.ETA) {
  					$scope.manifestPopupDetails.inbound = CONFIGURATIONS.shipmentManagent.tabInbound ;
  					$scope.manifestPopupDetails.timeLabel = CONFIGURATIONS.shipmentManagent.ETA;
  					$scope.manifestPopupDetails.flightServiceNameDetail = CONFIGURATIONS.shipmentManagent.consignee;
  					$scope.manifestPopupDetails.terminatingLabel = CONFIGURATIONS.shipmentManagent.terminating;
  					$scope.manifestPopupDetails.timeValue = $scope.manifestPopupDetails.estimatedArrivalTime;
  					$scope.manifestPopupDetails.orginatingTerminatingShipments = $scope.manifestPopupDetails.terminatingShipments;
  				} else if ($scope.currentTabnameTime === CONFIGURATIONS.shipmentManagent.ETD) {
  					$scope.manifestPopupDetails.timeLabel = CONFIGURATIONS.shipmentManagent.ETD;
  					$scope.manifestPopupDetails.inbound = CONFIGURATIONS.shipmentManagent.tabOutbound;
  					$scope.manifestPopupDetails.flightServiceNameDetail = CONFIGURATIONS.shipmentManagent.shipper;
  					$scope.manifestPopupDetails.terminatingLabel = CONFIGURATIONS.shipmentManagent.originating;
  					$scope.manifestPopupDetails.timeValue = $scope.manifestPopupDetails.estimatedDepartureTime;
  					$scope.manifestPopupDetails.orginatingTerminatingShipments = $scope.manifestPopupDetails.originatingShipments;
  					$scope.manifestPopupDetails.totalShipmentsTerminating = $scope.manifestPopupDetails.totalShipmentsOriginating;
  				}
  			}

  			//Common data across Manifest Search Page
  			if (!$scope.manifestPopupDetails.carrierType) {
  				$scope.manifestPopupDetails.carrierType = CONFIGURATIONS.shipmentManagent.carrierWN;
  			}
  			$scope.manifestPopupAsteriskShow($scope.manifestPopupDetails);

  		}

  	});

  };
	 /**This method is for - MANIFEST POPUP ASTERTISK DISPLAY .
	  * @pram object - tabdata to save.

	  **/
	 $scope.manifestPopupAsteriskShow = function () {

	 	if ($scope.manifestPopupDetails.bookedShipments) {
	 		angular.forEach($scope.manifestPopupDetails.bookedShipments, function (row, index) {
	 			if ($scope.manifestPopupDetails.bookedShipments[index].forcedBook == true) {
	 				$scope.manifestPopupDetails.forcedBookBookedShipments = true;
	 				$scope.manifestPopupDetails.mainFlagAsterisk = true;
	 			}
	 		});
	 	} else {
	 		$scope.manifestPopupDetails.forcedBookBookedShipments = false;
	 	}

	 	if ($scope.manifestPopupDetails.orginatingTerminatingShipments) {
	 		angular.forEach($scope.manifestPopupDetails.orginatingTerminatingShipments, function (row, index) {
	 			if ($scope.manifestPopupDetails.orginatingTerminatingShipments[index].forcedBook == true) {
	 				$scope.manifestPopupDetails.forcedBookTerminatingShipments = true;
	 				$scope.manifestPopupDetails.mainFlagAsterisk = true;
	 			}
	 		});
	 	} else {
	 		$scope.manifestPopupDetails.forcedBookTerminatingShipments = false;
	 	}

	 	if ($scope.manifestPopupDetails.wharehouseTransferShipments) {
	 		angular.forEach($scope.manifestPopupDetails.wharehouseTransferShipments, function (row, index) {
	 			if ($scope.manifestPopupDetails.wharehouseTransferShipments[index].forcedBook == true) {
	 				$scope.manifestPopupDetails.forcedBookwharehouseTransferShipments = true;
	 				$scope.manifestPopupDetails.mainFlagAsterisk = true;
	 			}
	 		});
	 	} else {
	 		$scope.manifestPopupDetails.forcedBookwharehouseTransferShipments = false;
	 	}

	 	if ($scope.manifestPopupDetails.tailToTailShipments) {
	 		angular.forEach($scope.manifestPopupDetails.tailToTailShipments, function (row, index) {
	 			if ($scope.manifestPopupDetails.tailToTailShipments[index].forcedBook == true) {
	 				$scope.manifestPopupDetails.forcedBookTailToTailShipments = true;
	 				$scope.manifestPopupDetails.mainFlagAsterisk = true;
	 			}
	 		});
	 	} else {
	 		$scope.manifestPopupDetails.forcedBookTailToTailShipments = false;
	 	}

	 };
	
	/** Print deliver and receive & deliver popup starts.
	 * @param object - data to save.
	 **/
	$scope.printDeliverManifestInbound = function (data) {
		var baseurl = ServiceURL.baseUrl;
		var totalNoPiece = 0;
		var allAWB = [];
		var output = [];
		/***Checking same awb/lot**/
		angular.forEach(data.deliverLots, function (item, index) {
			var awbNo = data.deliverLots[index].awbNumber;
				if (allAWB.indexOf(awbNo) === -1) {
					allAWB.push(awbNo);
					output.push(data.deliverLots[index]);
				}
		});
		data.deliverLots = output;
	  
	 /** Multiple awb print.
	  * @param index
	  **/
	 angular.forEach(data.deliverLots, function (row, index) {
	 	var dataQuery = "awbNumber=" + data.deliverLots[index].awbNumber + "&deliveredDateTimeStr=" + data.deliveredDateTimeStr + "&customerName=" + data.customerName + "&empId=" + data.empId + "&piecesDelivered=" + data.deliverLots[index].piecesDelivered;
	 	window.open(baseurl + '/shpmnt/printAWBDelivery?' + dataQuery);
	 });
	 };
	/**PRINT MANIFEST for ManifestPopupWindow - Start
	 **/
	$scope.printManifest = function () {

		if ($scope.manifestPopupDetails) {

			$scope.manifestPopupDetails.manifestFlightInfo.carrier = $scope.manifestPopupDetails.carrierType;
			$scope.manifestPopupDetails.manifestFlightInfo.origin = $scope.manifestPopupDetails.originStr;
			$scope.manifestPopupDetails.manifestFlightInfo.destination = $scope.manifestPopupDetails.destination;

			if ($scope.manifestPopupDetails.inbound === CONFIGURATIONS.shipmentManagent.tabInbound ) {
				$scope.manifestPopupDetails.manifestFlightInfo.estimatedArrivalTime = $scope.manifestPopupDetails.estimatedArrivalTime;
			} else {

				$scope.manifestPopupDetails.manifestFlightInfo.estimatedDepartureTime = $scope.manifestPopupDetails.estimatedDepartureTime;
			}
			$scope.manifestPopupDetails.manifestFlightInfo.flightNumber = $scope.manifestPopupDetails.flightNumber;
			$scope.manifestPopupDetails.manifestFlightInfo.date = $scope.manifestPopupDetails.date;

		}

		var carrier = '',
		flightNumber = '',
		flightLegDepartureDate = '',
		flightLegSuffix = '',
		date = '',
		inbound = '',
		lockDown = '',
		originStr = '',
		destination = '',
		estimatedArrivalTime = '',
		estimatedDepartureTime = '',
		assignedDateTimeZone = '';

		carrier += "carrier=" + $scope.manifestDataSelected.carrier;
		flightNumber += "flightNumber=" + $scope.manifestDataSelected.flightNumber;
		flightLegDepartureDate += "flightLegDepartureDate=" + $scope.manifestDataSelected.flightLegDepartureDate;
		flightLegSuffix += "flightLegSuffix=" + $scope.manifestDataSelected.flightLegSuffix;
		date += "date=" + $scope.manifestDataSelected.date;
		originStr += "originStr=" + $scope.manifestDataSelected.originStr;
		destination += "destination=" + $scope.manifestDataSelected.destination;
		estimatedArrivalTime += "estimatedArrivalTime=" + $scope.manifestDataSelected.estimatedArrivalTime;
		estimatedDepartureTime += "estimatedDepartureTime=" + $scope.manifestDataSelected.estimatedDepartureTime;
		lockDown += "lockDown=" + false;
		var sessionTime = $filter('colonFilter')($rootScope.currentDateTime);// Passing the loggedin time and timezone for displaying in the topmost section(Printed) in print reports
		assignedDateTimeZone += "assignedDateTimeZone=" + sessionTime;

		var baseurl = ServiceURL.baseUrl;
		//Modified for defect #5307 
		if ($scope.manifestPopupDetails.inbound === CONFIGURATIONS.shipmentManagent.headerInbound ) {
			inbound += "inbound=" + true;
			window.open(baseurl + '/shpmnt/printManifestInbound?' + carrier + '&' + flightNumber + '&' + flightLegDepartureDate + '&' + flightLegSuffix + '&' + date 
			+ '&' + inbound + '&' + lockDown + '&' + originStr + '&' + destination + '&' + estimatedArrivalTime + '&' + estimatedDepartureTime  + '&' + assignedDateTimeZone);
		} else {

			inbound += "inbound=" + false;
			var baseurl = ServiceURL.baseUrl;
			window.open(baseurl + '/shpmnt/printManifestOutbound?' + carrier + '&' + flightNumber + '&' + flightLegDepartureDate + '&' + flightLegSuffix + '&' + date 
			+ '&' + inbound + '&' + lockDown + '&' + originStr + '&' + destination + '&' + estimatedArrivalTime + '&' + estimatedDepartureTime  + '&' + assignedDateTimeZone);

		}
	};
  
	/**Load outbound tab starts , This method takes care updating COMETD channel and initiate loading outbound data
	 **/

	$scope.loadOutbounddata = function () {

		if ($scope.facilityChanged) {

			$scope.selectedStation = $scope.shipmentInboundSearch.facility;
		} else {
			$scope.selectedStation = $localStorage.userObject.swaLocation;
		}
		$scope.currentTab = CONFIGURATIONS.shipmentManagent.tabOutbound;
		if (!$scope.isSubscribed) {
			$scope.subscribe($scope.selectedStation);
		} else {
			$scope.updateSubscription();
		}
		$scope.$broadcast('fetchingOutboundData');
	};
	
	
	/**Load transfer tab starts , This method takes care updating COMETD channel and initiate loading transfer data
	 **/
	$scope.loadTransferdata = function () {

		if ($scope.facilityChanged) {
			$scope.selectedStation = $scope.shipmentInboundSearch.facility;
		} else {
			$scope.selectedStation = $localStorage.userObject.swaLocation;
		}
		$scope.currentTab =  CONFIGURATIONS.shipmentManagent.xfr;
		if (!$scope.isSubscribed) {
			$scope.subscribe($scope.selectedStation);
		} else {
			$scope.updateSubscription();
		}

		$scope.$broadcast('fetchingTransferData');
	};
	/**Load delivered tab starts , initiate loading delivered data
	 **/
	$scope.loadDelivereddata = function () {
		$scope.currentTab =  CONFIGURATIONS.shipmentManagent.tabDelivered;
		$scope.$broadcast('fetchingDeliverData');
	};
	/**Load missing tab starts ,it initiate loading missing data
	 **/
	$scope.loadMissingdata = function () {
		$scope.currentTab = CONFIGURATIONS.shipmentManagent.tabMissing;
		$scope.$broadcast('fetchingMissingData');
	};
   
	/**Change runner in runner dropdown
	 * @param string newRunner
	 **/
	$scope.changeRunner = function (newRunner) {
		$scope.selectedRunner = newRunner;
	};
	/**Assign runner select all check box clicked
	 **/
	$scope.selectAllClicked = function () {
		if ($scope.headerChkBox.selected) {
			angular.forEach($scope.assignRunnerRecords, function (runnerDetail) {
				runnerDetail.itemSelected = true;
				$scope.assignRunnerFormValid = true;
			});
		} else {
			angular.forEach($scope.assignRunnerRecords, function (runnerDetail) {
				runnerDetail.itemSelected = false;
				$scope.assignRunnerFormValid = false;
			});
		}

	};
	/** Assign runner popup item check box clicked 
	**/
	$scope.chkClicked = function (index) {
		var count = 0;

		angular.forEach($scope.assignRunnerRecords, function (runnerDetail) {
			if (!runnerDetail.itemSelected) {
				$scope.headerChkBox.selected = false;
			} else {
				count++;
			}

		});
		if ($scope.assignRunnerRecords.length == count) {
			$scope.headerChkBox.selected = true;
		}

		if (count > 0) {
			$scope.assignRunnerFormValid = true;
		} else {
			$scope.assignRunnerFormValid = false;
		}
	};
	/** Update newly edited runner name in database
	 **/
	$scope.updateRunnerInline = function () {
		if (!$scope.facilityChanged) {
			$scope.editRunnerName = false;
			var data;
			var inboundRunners = [];
			angular.forEach($scope.inboundLots, function (runnerDetail) {
				inboundRunners.push({
					"awbNumber" : runnerDetail.awbNumber,
					"awbPrefix" : runnerDetail.awbPrefix,
					"assignedRunner" : runnerDetail.assignedRunner,
					"expectedArrTime" : runnerDetail.expectedArrTime,
					"inGateNumber" : runnerDetail.inGateNumber,
					"flightNumber" : runnerDetail.flightNumber,
					"lotNumber" : runnerDetail.lotNumber
				});

			});
			data = {
				"inboundRunners" : inboundRunners,
				"station" : $localStorage.userObject.swaLocation,
				"assignedRunners" : ""
			};
			$scope.popUpSaveService(data, CONFIGURATIONS.shipmentManagent.assignRunnerPopup);
		}
	};
	/** Print runner assignment popup view filtered result**/
	$scope.showAssignmentForRunner = function () {
		$scope.filteredInboundLots = [];
		//trimmed ":" from receiveTime for service request.
		var selectedTime = moment($scope.commonData.receiveDate).format('YYYYMMDD') +
			$scope.commonData.receiveTime.toString().replace(/:/g, ""); 
		var data = {};
		var inboundRunners = [];
		data = {
			"runner" : $scope.selectedRunner,
			"station" : $localStorage.userObject.swaLocation,
			"assignedDateStr" : selectedTime,
			"assignedDate" : ""
		};
		shipmentMgmtService.inboundRunnerAssgnmtsPopUp(data).then(function (responseData) {
			if (angular.isObject(responseData.data.inboundRunnerAssignment)) {
				$scope.filteredInboundLots = angular.copy(responseData.data.inboundRunnerAssignment);
			}
		});

	};
	/** Print runner assignment
	 **/
	$scope.printRunnerAssignment = function () {
		var baseurl = ServiceURL.baseUrl;
		var shpmyPrint = "";
		var sessionTime = $filter('colonFilter')($rootScope.currentDateTime);
		var selectedTime = moment($scope.commonData.receiveDate).format('YYYYMMDD') +
			$scope.commonData.receiveTime.toString().replace(/:/g, ""); // Fixed for picking session time for reports .Added field assignedDateTimeZone
		shpmyPrint += "&runner=" + $scope.selectedRunner + "&assignedDateStr=" + selectedTime + "&assignedDate=0"+"&assignedDateTimeZone=" +sessionTime;
		window.open(baseurl + '/shpmnt/printIBRunnerAssgnmt?' + shpmyPrint + "&station=" + $localStorage.userObject.swaLocation);
	};

    /** Load assign Location popup 
     * @param object lotvalue
     * @param index
     **/
    $scope.getDetailForAssignLocation = function (index) {

    	$scope.inboundProcess = [];
    	$scope.commonData = {};
    	$scope.masterDropDown.warehouseLocation =$scope.inboundLots[index].wharehouseLocation;
    	$scope.warehouseLocationList = [];

    	var tmpValues = {};
    	var reqData = {};
    	//BOTH is used to check for inbound or outbound
    		reqData = {
    			"station" : $localStorage.userObject.swaLocation,
    			"locType" : [ CONFIGURATIONS.shipmentManagent.inboundTab, CONFIGURATIONS.shipmentManagent.bothInboundOutbound]
    		};

    		shipmentMgmtService.getWarehouseLocation(reqData).then(function (responseData) {
    			if (angular.isObject(responseData.data.keyValue)) {
    				$scope.warehouseLocationList = angular.copy(responseData.data.keyValue);
    				if ($scope.warehouseLocationList) {
					// Fix for code review comments start.
					//removed repeated condition. 
					tmpValues.awbPrefix = $scope.inboundLots[index].awbPrefix;
    				tmpValues.awbNumber = $scope.inboundLots[index].awbNumber;
    				tmpValues.lotNumber = $scope.inboundLots[index].lotNumber;
    				tmpValues.totalPieces = $scope.inboundLots[index].noOfPieces;
    				$scope.commonData.empId = $localStorage.userObject.userId;
					//removed repeated condition.
					// Fix for code review comments end.
    				}
					
    				tmpValues.warehouseLocation = $scope.masterDropDown.warehouseLocation;
    				tmpValues.awbVersion = $scope.inboundLots[index].awbVersion;
    				$scope.currentTabname = CONFIGURATIONS.shipmentManagent.inboundTab;
    				$timeout(function () {
    					focusAttrib("data-id='warehouseLocationAssign'");
    				},  CONFIGURATIONS.TIMEOUT_FOUR_HUNDRED_MS);
    				$scope.inboundProcess.push(tmpValues);
    				$scope.commonData.receiveDateTime = $rootScope.currentDate; //moment().format('MM/DD/YYYY');
    				$scope.commonData.receiveTime = moment($rootScope.currentTime, 'HHmm').format('HH:mm');
    				$timeout(function () {
    					$(".reinitialize").selectpicker("refresh");
    				},  CONFIGURATIONS.TIMEOUT_EIGHT_HUNDRED_MS);
    			}

    		});
    };
	
	/**Reset initial values on inbound load**/
	$scope.getFreshState = function () {
		$scope.selectedRow = [];
		$scope.discrepancy = false;
		$scope.assignRunner = true;
		$scope.printAssignment = false;
		$scope.receive = false;
		$scope.undoReceive = false;
		$scope.receiveDeliver = false;
		$scope.deliver = false;
		// modified as code review comments.. starts !
		$scope.privilegeProvide = false;
		// modified as code review comments.. ends !
		$scope.errorObject.diffAWBError = false;
		$scope.diffAWBErrorFlag = false;
			$scope.commonAWBArray = [];
			$scope.allowToCheck = "";
	};
	/**Get data based on state**/
	$scope.getDataBasedOnState = function () {

		if ($scope.filterApplied && !angular.equals({}, $scope.appliedFilter)) {

			$scope.appliedFilter.station = $scope.selectedStation;
			$rootScope.$broadcast('displayLoadingBar');
			shipmentMgmtService.applyFliterInboundShipment($scope.appliedFilter).then(function (responseData) {
				/**Reset popup privilege**/
				$scope.allowToCheck = false;
				$scope.accessPermission();
				$scope.filterApplied = true;
				$scope.inboundLots = responseData.data.inboundLots;
				$rootScope.shipmentRefereshTime = $rootScope.currentTime + " " + $rootScope.timezone;
				$scope.selectedRow = [];
				$scope.getFreshState();
				$rootScope.$broadcast('hideLoadingBar');
			});
		} else {
			$scope.init();
		}
	};
	
/**load selected shipment tab when user have privilege to access that particular tab
 * @param index
 **/
 /*Modified code for CCB - 298 -Start changed tab sequence  to "inbound" ,"missing","transfer","outbound" , delivered*/
$scope.showTab = function (index) {
	$(".tabWrapper .nav-tabs li").removeClass('active');
	$(".tabWrapper .nav-tabs li:eq(" + index + ")").addClass('active');
	$(".tabWrapper .tab-content .tab-pane").hide();
	$(".tabWrapper .tab-content .tab-pane:eq(" + index + ")").show();
};
/**Load selected shipment tab based on privilage**/
if (!$scope.has_permission($scope.role.role_VIEW_INBOUND_SHIPMENTS)) {
	if (!$scope.has_permission($scope.role.role_VIEW_MISSING_SHIPMENTS)) {
		if (!$scope.has_permission($scope.role.role_VIEW_TRANSFER_SHIPMENTS)) {
			if (!$scope.has_permission($scope.role.role_VIEW_OUTBOUND_SHIPMENTS)) {
				if ($scope.has_permission($scope.role.role_VIEW_PICKUP_SHIPMENTS)) {
					$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.DELIVERED);
					$timeout(function () {
						$scope.loadDelivereddata();
					},  CONFIGURATIONS.TIMEOUT_FIVE_HUNDRED_MS);
				}
			} else {
				$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.OUTBOUND);
				$timeout(function () {
					$scope.loadOutbounddata();
				},  CONFIGURATIONS.TIMEOUT_THOUSAND_MS);
			}
		} else {
			$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.XFR);
			$timeout(function () {
				$scope.loadTransferdata();
			}, CONFIGURATIONS.TIMEOUT_FIVE_HUNDRED_MS);
		}
	} else {
		$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.MISSING);
		$timeout(function () {
			$scope.loadMissingdata();
		}, CONFIGURATIONS.TIMEOUT_THOUSAND_MS);

	}
} else {
	$scope.showTab(CONFIGURATIONS.shipmentManagent.tabName.INBOUND);
	$timeout(function () {
		$scope.init();
	},CONFIGURATIONS.TIMEOUT_FIVE_HUNDRED_MS);

}
 /*Modified code for CCB - 298 -End*/
	/**Clear all filter flags
	 **/
	$scope.clearFilterFlags = function () {
		$scope.filterFlags = {};
	};
	/**COMETD IMPLIMENTATION  - set selected station for creating cometD channel **/
	$scope.selectedStation = $localStorage.userObject.swaLocation;

	/**Function to get curently selected inbound tab filters
	 **/
	function getInboundFilterState() {
		$scope.selectedIbLotStatusList = [];
		$scope.selectedIbSrvLvlList = [];
		if ($scope.lotStatus.ARR === true){
			$scope.selectedIbLotStatusList.push(CONFIGURATIONS.shipmentManagent.ARR);
		}if ($scope.lotStatus.DEP === true){
			$scope.selectedIbLotStatusList.push(CONFIGURATIONS.shipmentManagent.DEP);
		}if ($scope.lotStatus.RCS === true){
			$scope.selectedIbLotStatusList.push(CONFIGURATIONS.shipmentManagent.RCS);
		}if ($scope.lotStatus.RCF === true){
			$scope.selectedIbLotStatusList.push(CONFIGURATIONS.shipmentManagent.RCF);
		}if ($scope.lotStatus.XFOH === true){
			$scope.selectedIbLotStatusList.push(CONFIGURATIONS.shipmentManagent.XFOH);
		}
		/**SERVICE LEVEL CHECKING**/
		angular.forEach($scope.srvLvl, function (value, key) {
			if (value === true) {
				$scope.selectedIbSrvLvlList.push(key);
			}
		});

	}
  /** This function update cometd feed based on current filter selection, with currently applied shoring order
   * For Inbound tab
   **/
  function updateInboundDataChange() {

  	getInboundFilterState();
  	var lotStatusMatch = false;
  	var svrLvlmatch = false;
  	angular.forEach($scope.selectedIbLotStatusList, function (filteredItem) {
  		if (filteredItem == $scope.msg.lotStatus) {
  			lotStatusMatch = true;
  		}
  	});
  	angular.forEach($scope.selectedIbSrvLvlList, function (filteredItem) {
  		if (filteredItem == $scope.msg.serviceLevel.code) {
  			svrLvlmatch = true;
  		}
  	});

  	if (lotStatusMatch == true && svrLvlmatch == true) {
  		$rootScope.shipmentRefereshTime = $rootScope.currentTime + " " + $rootScope.timezone;
  		if ($scope.msg.operation === CONFIGURATIONS.shipmentManagent.operation.updated || $scope.msg.operation === CONFIGURATIONS.shipmentManagent.operation.created) {
  			var isNewRecord = true;
  			var itemIndex = 0;
  			if ($scope.inboundLots.length > 0) {
  				$scope.order($scope.sortedfield, $scope.orderState);
  				for (var item in $scope.inboundLots) {
  					if ($scope.inboundLots[item].awbNumber == $scope.msg.awbNumber && $scope.inboundLots[item].lotNumber == $scope.msg.lotNumber) {
  						if ($scope.inboundLots[item].flightNumber == $scope.msg.flightNumber) {
  							isNewRecord = false;
  							$scope.$apply($scope.inboundLots[item] = $scope.msg);
  							$scope.order($scope.sortedfield, $scope.orderState);
  							break;
  						} else if ($scope.inboundLots[item].flightNumber != $scope.msg.flightNumber) {
  							$scope.$apply($scope.inboundLots.splice(itemIndex, 1));
  							break;
  						}
  					}
  					itemIndex++;
  				}

  			}
  			if (isNewRecord == true) {
  				$scope.$apply($scope.inboundLots.push($scope.msg));
  				$scope.order($scope.sortedfield, $scope.orderState);
  			}
  		} else if ($scope.msg.operation === CONFIGURATIONS.shipmentManagent.operation.deleted) {
  			var itemIndex = 0;
  			$scope.order($scope.sortedfield, $scope.orderState);
  			for (var item in $scope.inboundLots) {
  				if ($scope.inboundLots[item].awbNumber == $scope.msg.awbNumber && $scope.inboundLots[item].lotNumber == $scope.msg.lotNumber && $scope.inboundLots[item].flightNumber == $scope.msg.flightNumber) {
  					$scope.$apply($scope.inboundLots.splice(itemIndex, 1));
  				}
  				itemIndex++;
  			}
  		}
  	} else {
  		var itemIndex = 0;
  		$scope.order($scope.sortedfield, $scope.orderState);
  		for (var item in $scope.inboundLots) {
  			if ($scope.inboundLots[item].awbNumber == $scope.msg.awbNumber && $scope.inboundLots[item].lotNumber == $scope.msg.lotNumber && $scope.inboundLots[item].flightNumber == $scope.msg.flightNumber) {
  				$scope.$apply($scope.inboundLots.splice(itemIndex, 1));
  			}
  			itemIndex++;
  		}
  	}

  }
	/**Create appropriate channel and update cometD feeds
	 * @param string station
	 **/
	$scope.subscribe = function (station) {
		setTimeout(function () {
			var channel = '/cometd/shipments/notifications/' + $scope.currentTab + '/' + station;
			$scope.isSubscribed = true;
			$scope.subscription = $rootScope.cometd.subscribe(channel, function (msg) {
					$scope.msg = JSON.parse(msg.data);
					//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
					if ($scope.currentTab === CONFIGURATIONS.shipmentManagent.tabInbound ) {
						updateInboundDataChange();
						$scope.getFreshState();
					} else if ($scope.currentTab === CONFIGURATIONS.shipmentManagent.tabOutbound) {
						$scope.$broadcast('updateOutBoundChange');
					} else if ($scope.currentTab === CONFIGURATIONS.shipmentManagent.xfr) {
						$scope.$broadcast('updateXFRChange');
					}
				});
		},CONFIGURATIONS.TIMEOUT_THOUSAND_MS);
	};
	/**Unsubscribe cometd channal.
	 * @param currSubscription
	 **/
	function unsubscribe(currSubscription) {
		$rootScope.cometd.unsubscribe(currSubscription);
	}
	/**Update cometd channel/ subscription**/
	$scope.updateSubscription = function () {
		$rootScope.cometd.batch(function () {
			unsubscribe($scope.subscription);
			$scope.subscribe($scope.selectedStation);
		});
	};
	/** Function to retain default filter states or applied filter states
	 **/
	$scope.setFilterState = function () {
		/**if filters are applied**/
		if ($scope.filterApplied) {
			/** Applied filters are checked against available filters**/
			/**checking for service level**/
			angular.forEach($scope.serviceLevel, function (value, index) {
				if ($scope.appliedFilter.serviceLevel.indexOf(value.key) > -1) {
					$scope.srvLvl[value.key] = true;
				} else {
					/** service level which are not  applied in filter**/
					$scope.srvLvl[value.key] = false;
				}

			});
			/** Applied filters are checked against available filters**/
			/** checking for lot status**/
			//@CodeReviewFix:03-2016 Hard coded data has been moved to constant.
			angular.forEach(CONFIGURATIONS.shipmentManagent.filterLotStsListInbound, function (value, index) {
				if ($scope.appliedFilter.lotStatus.indexOf(value.key) > -1) {
					$scope.lotStatus[value.key] = true;
				} else {
					$scope.lotStatus[value.key] = false;
				}
			});

		} else {
			/**set default filters for inbound tab**/
			$scope.resetFilters();
		}

	};
	/**set default filters for inbound tab**/
	$scope.resetFilters = function () {
		angular.forEach($scope.serviceLevel, function (key, index) {
			$scope.serviceLevelCount++;
			$scope.srvLvl[key.key] = true;
		});
		angular.forEach(CONFIGURATIONS.shipmentManagent.filterLotStsListInbound, function (key, index) {
			$scope.lotStatus[key.key] = true;
		});
	};
}]);

/**link the DOM element to the view model.
**/
app.directive("ngLogDomCreation", function () {
	// I link the DOM element to the view model.
	function link($scope, element, attributes) {}
	return ({
		link : link,
		restrict : "A"
	});
});