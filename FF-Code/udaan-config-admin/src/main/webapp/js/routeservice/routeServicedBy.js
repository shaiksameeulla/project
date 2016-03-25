var transportModeCode;
var tableRowId;
var tableId;
var station;
var tsByList;
var tripList;
var vendorList;
var search = false;
var labelVal;
var labelVen;
var serviceByTypeId;
var serviceByTypeCode;
var rowfill = false;
var delStatus = false;
var idsList = "";
c1 = false;
c2 = false;
var action;
var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";

var vendorDropDownId;
/**
 * add new row to the grid
 */
function addRouteServicedByRow() {
	addRow();
	if(serviceByTypeCode != "D"){	
		if(!isNull(getDomElementById("vendorNumber" + (rowCount-1)))){
		setTimeout(function() {
		getDomElementById("vendorNumber" + (rowCount-1)).focus();
			}, 10);
		}
	}else{
		if(!isNull(getDomElementById("tripNumber" + (rowCount-1)))){
			setTimeout(function() {
			getDomElementById("tripNumber" + (rowCount-1)).focus();
				}, 10);
			}
	}
}

/**
 * Check the data has been provided for Header fields or not
 * 
 * @returns {Boolean}
 */
function checkFields() {
	var originRegionDom = getDomElementById("originRegionId");
	var destinationRegionDom = getDomElementById("destinationRegionId");
	var destinationStationDom = getDomElementById("destinationStationId");
	var originStationDom = getDomElementById("originStationId");
	var transportModeDom = getDomElementById("transportMode");
	var serviceByTypeDom = getDomElementById("serviceByType");
	var effectiveFrom = getDomElementById("effectiveFromStr");
	var effectiveTo = getDomElementById("effectiveToStr");
	// effectiveFrom.disabled = false;
	// effectiveTo.disabled = false;

	if (isNull(originRegionDom.value)) {
		alert('Please select Origin Region');
		originRegionDom.focus();
		return false;
	}
	if (isNull(originStationDom.value)) {
		alert('Please select Origin Station');
		originStationDom.focus();
		return false;
	}
	if (isNull(destinationRegionDom.value)) {
		alert('Please select Destination Region');
		destinationRegionDom.focus();
		return false;
	}
	if (isNull(destinationStationDom.value)) {
		alert('Please select Destination Station');
		destinationStationDom.focus();
		return false;
	}
	if (isNull(transportModeDom.value)) {
		alert('Please select Tranpsort Mode');
		transportModeDom.focus();
		return false;
	}
	if (isNull(serviceByTypeDom.value)) {
		alert('Please select Service By Type');
		serviceByTypeDom.focus();
		return false;
	}
	if (isNull(effectiveFrom.value)) {
		alert('Please select Effective From date');
		effectiveFrom.focus();
		return false;
	}
	if (isNull(effectiveTo.value)) {
		alert('Please select Effective Till date');
		effectiveTo.focus();
		return false;
	}

	if ((originStationDom.value) == (destinationStationDom.value)) {
		alert('Origin and Destination Stations should not be same');
		return false;
	}

	if (!compareDates())
		return false;
	return true;
}

/**
 * Check the data has been provided for Grid fields or not
 * 
 * @returns {Boolean}
 */
function checkGridValues() {

	rCount = serialNo;

	for ( var i = 1; i < rCount; i++) {
		if (!checkMandatory(i)) {
			return false;
		}
	}

	return true;
}

/**
 * Check the data has been provided for the fields of Grid row or not
 * 
 * @returns {Boolean}
 */
function checkMandatory(rowId) {
	
	var vendorNumber = getDomElementById("vendorNumber" + rowId);
	var tripId = getDomElementById("tripNumber" + rowId);
	serviceByTypeCode = (getDomElementById("serviceByType").value)
	.split(tild)[1];
	if (!isNull(vendorNumber)) {
		if(rowId < (rowCount-1)  && (countNo-1) != 1 && checkLastRow(rowId)){
		
		if((serviceByTypeCode != "D") && !checkVendorField(rowId)){
			return false;
		}
		else if (!checkTripField(rowId) ){
			return false;
		}
		else if (validateCheckBox(rowId)) {
			alert('Row Number: ' + getDomElementById("count" + rowId).value
					+ '  Please check at least one serviced days check box');
			setTimeout(function() {
				getDomElementById("days" + rowId).focus();
			}, 10);
			
			return false;
		}
		}
		if(((countNo-1) == 1) || !checkLastRow(rowId)){
			
			if (isNull(vendorNumber.value) && isNull(tripId.value) && validateCheckBox(rowId)){
				
				return true;
			}else{
				
				if((serviceByTypeCode != "D") && !checkVendorField(rowId)){
					return false;
				}
				else if (!checkTripField(rowId) ){
					return false;
				}
				else if (validateCheckBox(rowId)) {
					alert('Row Number: ' + getDomElementById("count" + rowId).value
							+ '  Please check at least one serviced days check box');
					setTimeout(function() {
						getDomElementById("days" + rowId).focus();
					}, 10);
					
					return false;
				}
			}
		}
		rowfill = true;
}
	return true;
}

/**
 * It checks whether Vendor name has selected or not
 * @param rowId
 * @returns {Boolean}
 */
function checkVendorField(rowId){
	var vendorNumber = getDomElementById("vendorNumber" + rowId);
	if (isNull(vendorNumber.value)) {
				alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please select ' + labelVen);
				setTimeout(function() {
					vendorNumber.focus();
				}, 10);				
				return false;
			
		}
		return true;
}	

/**
 * It checks whether Train/Vehicle/Flight number has selected or not
 * @param rowId
 * @returns {Boolean}
 */
function checkTripField(rowId){
	var tripId = getDomElementById("tripNumber" + rowId);
	if (isNull(tripId.value)) {
		alert('Row Number: ' + getDomElementById("count" + rowId).value + '  Please select ' + labelVal);
		setTimeout(function() {
			tripId.focus();
		}, 10);
		return false;
	}
		return true;
}	

/**
 * It checks whether All the Check boxes of Days are selected or not
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateCheckBox(rowId) {
	if (getDomElementById("days" + rowId).checked == false
			&& getDomElementById("mon" + rowId).checked == false
			&& getDomElementById("tue" + rowId).checked == false
			&& getDomElementById("wed" + rowId).checked == false
			&& getDomElementById("thu" + rowId).checked == false
			&& getDomElementById("fri" + rowId).checked == false
			&& getDomElementById("sat" + rowId).checked == false
			&& getDomElementById("sun" + rowId).checked == false) {
		return true;
	}
	return false;
}

/**
 * @param rowId
 * @param week
 * @returns {Boolean}
 */
function validateDayCheckBox(rowId,week) {
	
	
	if (week != "mon" && getDomElementById("mon" + rowId).checked)
		return false;
	if (week != "tue" && getDomElementById("tue" + rowId).checked)
		return false;
	if (week != "wed" && getDomElementById("wed" + rowId).checked)
		return false;
	if (week != "thu" && getDomElementById("thu" + rowId).checked)
		return false;
	if (week != "fri" && getDomElementById("fri" + rowId).checked)
		return false;
	if (week != "sat" && getDomElementById("sat" + rowId).checked)
		return false;
	if (week != "sun" && getDomElementById("sun" + rowId).checked)
		return false;
	if (week == "days" && getDomElementById("days" + rowId).checked)
		return true;	
	return true;
}

// Using data grid
var serialNo = 1;
var gridSerialNo = 1;
var rowCount = 1;
var gridRowCount = 1;
var count = 1;
var countNo = 1;

/**
 * This method add the new row to the Grid
 */
function addRow() {
	vendorNumber = "";
	expDepTime = "";
	expArrTime = "";
	tripNumber = "";
	servicedByNumber = "";
	tripServicedByNumber = "";
	operationDays = "";
	checkAll = "unchecked";
	allDays = "";

	var check = [ "unchecked", "unchecked", "unchecked", "unchecked",
			"unchecked", "unchecked", "unchecked" ];

	// if (transportModeCode == airCode) {
	if ((!isNull(tsByList)) && (!isNull(tsByList[count - 1]))) {
		
		if (!isNull(tsByList[count - 1].tripServicedById)) {
			tripServicedByNumber = tsByList[count - 1].tripServicedById;
		}
		if (serviceByTypeCode == directCode) {
			if (!isNull(tsByList[count - 1].servicedByTO.employeeTO) && !isNull(tsByList[count - 1].servicedByTO.employeeTO.employeeId)) {
				vendorNumber = tsByList[count - 1].servicedByTO.employeeTO.employeeId;
			}
		} else {
			if (!isNull(tsByList[count - 1].servicedByTO.loadMovementVendorTO) && !isNull(tsByList[count - 1].servicedByTO.loadMovementVendorTO.vendorId)) {
				vendorNumber = tsByList[count - 1].servicedByTO.loadMovementVendorTO.vendorId;
			}
		}
		
		if (!isNull(tsByList[count - 1].servicedByTO.servicedById)) {
			servicedByNumber = tsByList[count - 1].servicedByTO.servicedById;
		}
		
		if (!isNull(tsByList[count - 1].tripTO.tripId)) {
			tripNumber = tsByList[count - 1].tripTO.tripId;
		}
		if (!isNull(tsByList[count - 1].tripTO.departureTime)) {
			expDepTime = tsByList[count - 1].tripTO.departureTime;
		}
		if (!isNull(tsByList[count - 1].tripTO.arrivalTime)) {
			expArrTime = tsByList[count - 1].tripTO.arrivalTime;
		}
		if (!isNull(tsByList[count - 1].allDays)) {
			allDays = tsByList[count - 1].allDays;
			if (allDays == "Y") {
				checkAll = "checked";
			} else {
				if (!isNull(tsByList[count - 1].operationDays)) {
					operationDays = tsByList[count - 1].operationDays;
					days = operationDays.split("-");
					for ( var i = 0; i < days.length; i++) {
						if (days[i] == "Y") {
							check[i] = "checked";
						}
					}
				}
			}
		}
		
	}

	//if (isNull(tsByList[count - 1].tripTO.active) || !isNull(tsByList[count - 1].tripTO.active)) {
		//if(isNull(tsByList[count - 1].tripTO.active) || tsByList[count - 1].tripTO.active == 'Y'){
	$('#routeServicedByGrid')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"   id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
									'<span id="serial'+rowCount+'">'+countNo+'</span>',
							'<select  id="vendorNumber'
									+ rowCount
									+ '" name="to.vendorNumber" value = ""  class="selectBox width145" onchange="getTripDetails('
									+ rowCount
									+ ');" onkeypress = "return enterKeyNav(\'tripNumber'+ rowCount +'\',event.keyCode);" onblur="checkVendor(this.value,'
									+ rowCount + ')"/></select><input type="hidden" id="servicedByNumber'
									+ rowCount
									+ '" name="to.servicedByNumber" value="'
									+ servicedByNumber
									+ '"/><input type="hidden" id="tripServicedByNumber'
									+ rowCount
									+ '" name="to.tripServicedByNumber" value="'
									+ tripServicedByNumber + '"/>',
							'<select  id="tripNumber'
									+ rowCount
									+ '" name="to.tripNumber" value = ""  class="selectBox width145" onchange="getExpDepArrTime(this.value,'
									+ rowCount + ');" onkeypress = "return enterKeyNav(\'days'+ rowCount +'\',event.keyCode);" onblur="checkTrip(this.value,'
									+ rowCount + ')"/></select>',

							'<input type="text" id="expDepartureTime'
									+ rowCount
									+ '" name="to.expDepartureTime" value = "'
									+ expDepTime
									+ '" size="15" disabled class="txtbox" />',
							'<input type="text" id="expArrivalTime'
									+ rowCount
									+ '" name="to.expArrivalTime" disabled value = "'
									+ expArrTime
									+ '" size="15" class="txtbox" />',
							'<input type="checkbox"   id="days'
									+ rowCount
									+ '" name="days'
									+ rowCount
									+ '" '
									+ checkAll
									+ ' onchange="checkAllBox('
									+ rowCount
									+ ','+"true"+');"/><input type="hidden" id="allDaysArr'
									+ rowCount
									+ '" name="to.allDaysArr" value="'
									+ allDays + '" />',
							'<input type="checkbox"   id="mon' + rowCount
									+ '" name="mon' + rowCount + '"  '
									+ check[0] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'mon'"+');"/>',
							'<input type="checkbox"   id="tue' + rowCount
									+ '" name="tue' + rowCount + '"  '
									+ check[1] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'tue'"+');"/>',
							'<input type="checkbox"   id="wed' + rowCount
									+ '" name="wed' + rowCount + '"  '
									+ check[2] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'wed'"+');"/>',
							'<input type="checkbox"   id="thu' + rowCount
									+ '" name="thu' + rowCount + '"  '
									+ check[3] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'thu'"+');"/>',
							'<input type="checkbox"   id="fri' + rowCount
									+ '" name="fri' + rowCount + '"  '
									+ check[4] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'fri'"+');"/>',
							'<input type="checkbox"   id="sat' + rowCount
									+ '" name="sat' + rowCount + '"  '
									+ check[5] + ' onchange="checkBox('
									+ rowCount
									+ ','+"'sat'"+');"/>',
							'<input type="checkbox"   id="sun'
									+ rowCount
									+ '" name="sun'
									+ rowCount
									+ '"  '
									+ check[6]
									+ ' onchange="checkBox('
									+ rowCount
									+ ','+"'sun'"+');"/><input type="hidden" id="operationDaysArr'
									+ rowCount
									+ '" name="to.operationDaysArr" value="'
									+ operationDays + '"/><input type="hidden" id="count'
									+ rowCount
									+ '" name="count'+rowCount+'" value = "'
									+ countNo + '"/>' ]);

	loadDropDown("vendorNumber" + rowCount, vendorNumber, "", "vendor");
	serviceByTypeCode = (getDomElementById("serviceByType").value)
	.split(tild)[1];
	
	if(serviceByTypeCode != "D"){
		loadDropDown("tripNumber" + rowCount, tripNumber, vendorNumber, "trip");
		
		if(!isNull(tripList)){
			tripLen = tripList.length;
			if (tripLen > 0) {
				//loadTripDetails(tripList,rowCount,obj); 
			} 
			obj = getDomElementById("tripNumber" + rowCount); 
			var tripByLen = tsByList.length; 
			for ( var i = 0; i < tripByLen; i++) { 
				var tripNo =tsByList[i].tripTO.tripId;  
				if(tripNo==tripNumber){	
					opt = document.createElement('OPTION');
					opt.value = tsByList[i].tripTO.tripId;
					if (transportModeCode == airCode) {
					opt.text = tsByList[i].tripTO.transportTO.flightTO.flightNumber;  
					}else if (transportModeCode == trainCode) {
					opt.text = tsByList[i].tripTO.transportTO.trainTO.trainNumber;
					}else if (transportModeCode == roadCode) {
					opt.text = tsByList[i].tripTO.transportTO.vehicleTO.regNumber;
					}
				}   
			}
			obj.options.add(opt);  
			getDomElementById("tripNumber" + rowCount).value = tripNumber;
			//loadDropDown("tripNumber" + rowCount, tripNumber, "", "trip");
		}
	}else{
		
		getDomElementById("vendorNumber" + rowCount).disabled = true;
		if(!isNull(tripList)){
			tripLen = tripList.length;
			if (tripLen > 0) {
				obj = getDomElementById("tripNumber" + rowCount);
				loadTripDetails(tripList,rowCount,obj);
				getDomElementById("tripNumber" + rowCount).value = tripNumber;
			}
		}
	}
	if (allDays == 'Y') {
		checkAllBox(rowCount,false);
	}
	rowCount++;
	serialNo++;
		//}
	//}
	count++;
	countNo++;
	// }
}


/**
 * It checks whether Vendor Name has selected or not
 * @param vendor
 * @param rowId
 * @returns {Boolean}
 */
function checkVendor(vendor, rowId) {
	c1 = false;
	if( !checkLastRow(rowId) && isNull(vendor)){
		return;	
	}
	/*if(!checkVendorField(rowId)){
		c1 = true;
	return false;
	}*/
	return true;
}

/**
 * It checks whether Train/Flight/Vechile Number has selected or not
 * @param trip
 * @param rowId
 * @returns {Boolean}
 */
function checkTrip(trip, rowId) {
	
	if( !checkLastRow(rowId) && isNull(trip)){
		return;	
	}
	/*if(c1 == false){
	if(!checkTripField(rowId)){
	return false;
	}
	}*/
	return true;
}
/**
 * Uncheck the checkBox
 * 
 * @param rowId
 */
function unCheckAllBox(rowId) {
	getDomElementById("days" + rowId).checked = false;
	checkAllBox(rowId,false);
}
/**
 * This is Ajax Call It gets the Trip serviced details by search parameters adn
 * vendor of Grid row
 * 
 * @param rowId
 */
function getTripDetails(rowId) {
	tableRowId = rowId;
	vendor = getDomElementById("vendorNumber" + rowId).value;
	var check = true;

	clearDropDownList("tripNumber" + rowId);
	getDomElementById("expDepartureTime" + rowId).value = "";
	getDomElementById("expArrivalTime" + rowId).value = "";
	getDomElementById("servicedByNumber" + rowId).value = "";
	//unCheckAllBox(rowId);
	if (!isNull(vendor)) {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("vendorNumber" + i))) {
				if ((rowId != i) && (vendor == getDomElementById("vendorNumber" + i).value)) {
					loadDropDown("tripNumber" + rowId, "", "", "tripNumber" + i);
					check = false;
					break;
				}
			}
		}
		if (check) {
			var originStationId = getDomElementById("originStationId").value;
			var destinationStationId = getDomElementById("destinationStationId").value;
			serviceByTypeId = (getDomElementById("serviceByType").value)
					.split(tild)[0];
			serviceByTypeCode = (getDomElementById("serviceByType").value)
					.split(tild)[1];
			var effectiveFrom = getDomElementById("effectiveFromStr").value;
			var effectiveTo = getDomElementById("effectiveToStr").value;
			var transportMode = getValueByElementId("transportMode");
			var transportModeId = transportMode.split(tild)[0];
			var transportModeCode = transportMode.split(tild)[1];
			var originRegionId = getDomElementById("originRegionId").value;
			var url = "./routeServicedBy.do?submitName=getTripDetailsByVendor&originStationId="
					+ originStationId
					+ "&destinationStationId="
					+ destinationStationId
					+ "&transportModeId="
					+ transportModeId
					+ "&serviceByTypeId="
					+ serviceByTypeId
					+ "&serviceByTypeCode="
					+ serviceByTypeCode
					+ "&effectiveFrom="
					+ effectiveFrom
					+ "&effectiveTo="
					+ effectiveTo + "&vendor=" + vendor
					+ "&originRegionId=" + originRegionId
					+ "&transportModeCode=" + transportModeCode;
			
			ajaxCallWithPost(url, "routeServicedByForm", populateTransporterDetails);
		} 
	}
} 

/**
 * This method populates the Trip details after get the response from
 * getTripDetails() AJAX Call
 * 
 * @param data
 */
function populateTransporterDetails(resp) {

	selectId = "tripNumber" + tableRowId;
	obj = getDomElementById("tripNumber" + tableRowId);
	
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			tripList = responseText[SUCCESS_FLAG];
			if(!isNull(tripList)){
			var tripLen = tripList.length;
			if (tripLen > 0) {
				loadTripDetails(tripList,tableRowId,obj);
			}else {
				clearDropDownList("tripNumber" + tableRowId);
			}
			}else {
				clearDropDownList("tripNumber" + tableRowId);
			}
		}
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error.");
	}
	
	
	/*selectId = "tripNumber" + tableRowId;
	obj = getDomElementById("tripNumber" + tableRowId);
	tripList = jsonJqueryParser(resp);
	if (!isNull(tripList)) {
		if (tripList.length > 0) {
			clearDropDownList("tripNumber" + tableRowId);
			for ( var i = 0; i < tripList.length; i++) {
				opt = document.createElement('OPTION');
				if (transportModeCode == airCode) {
					// opt.value = tripList[i].transportTO.flightTO.flightId;
					opt.value = tripList[i].tripId;
					opt.text = tripList[i].transportTO.flightTO.flightNumber;
				} else if (transportModeCode == trainCode) {
					// opt.value = tripList[i].transportTO.trainTO.trainId;
					opt.value = tripList[i].tripId;
					opt.text = tripList[i].transportTO.trainTO.trainNumber;
				} else if (transportModeCode == roadCode) {
					// opt.value = tripList[i].transportTO.vehicleTO.vehicleId;
					opt.value = tripList[i].tripId;
					opt.text = tripList[i].transportTO.vehicleTO.regNumber;
				}
				obj.options.add(opt);
			}
		} else {
			clearDropDownList("tripNumber" + tableRowId);
		}
	} else {
		clearDropDownList("tripNumber" + tableRowId);
	}*/
}


function loadTripDetails(trList, rowNo, obj){
	clearDropDownList("tripNumber" + rowNo);
	tripLen = trList.length;
	for ( var i = 0; i < tripLen; i++) {
		opt = document.createElement('OPTION');
		if (transportModeCode == airCode) {
			// opt.value = tripList[i].transportTO.flightTO.flightId;
			opt.value = trList[i].tripId;
			opt.text = trList[i].transportTO.flightTO.flightNumber;
		} else if (transportModeCode == trainCode) {
			// opt.value = tripList[i].transportTO.trainTO.trainId;
			opt.value = trList[i].tripId;
			opt.text = trList[i].transportTO.trainTO.trainNumber;
		} else if (transportModeCode == roadCode) {
			// opt.value = tripList[i].transportTO.vehicleTO.vehicleId;
			opt.value = trList[i].tripId;
			opt.text = trList[i].transportTO.vehicleTO.regNumber;
		}
		obj.options.add(opt);
	}
}
/**
 * It Loads the Expected and Departure Time values based on selected
 * Train/Flight/Vehicle Number
 * 
 * @param selVal
 * @param rowId
 */
function getExpDepArrTime(selVal, rowId) {

	transporterIdVal = getDomElementById("tripNumber" + rowId).value;
	getDomElementById("expDepartureTime" + rowId).value = "";
	getDomElementById("expArrivalTime" + rowId).value = "";
	//unCheckAllBox(rowId);
	if (!isNull(transporterIdVal)) {
		if (validateFlightNumbers(rowId)) {
			if (tripList.length > 0) {

				for ( var i = 0; i < tripList.length; i++) {
					/*
					 * if( transportModeCode == airCode) transportVal =
					 * tripList[i].transportTO.flightTO.flightId; else if(
					 * transportModeCode == trainCode) transportVal =
					 * tripList[i].transportTO.trainTO.trainId; else if(
					 * transportModeCode == roadCode) transportVal =
					 * tripList[i].transportTO.vehicleTO.vehicleId;
					 */
					transportVal = tripList[i].tripId;
					if (transporterIdVal == transportVal) {
						getDomElementById("expDepartureTime" + rowId).value = tripList[i].departureTime;
						getDomElementById("expArrivalTime" + rowId).value = tripList[i].arrivalTime;
					}
				}
			}
		}
	}
}

/**
 * IT loads the drop down values of Vendor and Train/Flight/Vehicle fields of
 * the Grid rows
 * 
 * @param selectId
 * @param selValue
 * @param venNumber
 * @param dropName
 */
function loadDropDown(selectId, selValue, venNumber, dropName) {

	var obj = getDomElementById(selectId);
	if (dropName == "vendor") {
		if (!isNull(vendorList) && vendorList.length > 0) {
			clearDropDownList(selectId);
			if (serviceByTypeCode == directCode) {
				for ( var i = 0; i < vendorList.length; i++) {
					opt = document.createElement('OPTION');
					opt.value = vendorList[i].employeeId;
					opt.text = vendorList[i].firstName;
					obj.options.add(opt);
				}
			} else {
				for ( var i = 0; i < vendorList.length; i++) {
					opt = document.createElement('OPTION');
					opt.value = vendorList[i].vendorId;
					opt.text = vendorList[i].businessName;
					obj.options.add(opt);
				}
			}
			obj.value = selValue;
		} else {
			clearDropDownList(selectId);
		}
	} else if (dropName == "trip") {
		if (!isNull(tsByList) && !isNull(map) && tsByList.length > 0) {
			clearDropDownList(selectId, selectOption, "");
			if (transportModeCode == airCode) {
				for ( var key in map) {
					if (venNumber == key) {
						for ( var i = 0; i < map[key].length; i++) {
							opt = document.createElement('OPTION');
							// opt.value =
							// (map[key])[i].transportTO.flightTO.flightId;
							opt.value = (map[key])[i].tripId;
							opt.text = (map[key])[i].transportTO.flightTO.flightNumber;
							obj.options.add(opt);
						}
					}
				}
			} else if (transportModeCode == trainCode) {
				for ( var key in map) {
					if (venNumber == key) {
						for ( var i = 0; i < map[key].length; i++) {
							opt = document.createElement('OPTION');
							// opt.value =
							// (map[key])[i].transportTO.trainTO.trainId;
							opt.value = (map[key])[i].tripId;
							opt.text = (map[key])[i].transportTO.trainTO.trainNumber;
							obj.options.add(opt);
						}
					}
				}
			} else if (transportModeCode == roadCode) {
				for ( var key in map) {
					if (venNumber == key) {
						for ( var i = 0; i < map[key].length; i++) {
							opt = document.createElement('OPTION');
							// opt.value =
							// (map[key])[i].transportTO.vehicleTO.vehicleId;
							opt.value = (map[key])[i].tripId;
							opt.text = (map[key])[i].transportTO.vehicleTO.regNumber;
							obj.options.add(opt);
						}
					}
				}
			}

			obj.value = selValue;
		} else {
			clearDropDownList(selectId);
		}
	} else {
		var optionsList = getDomElementById(dropName).options;
		obj.options.length = 0;
		for ( var i = 0; i < optionsList.length; i++) {
			opt = document.createElement('OPTION');
			opt.value = optionsList[i].value;
			opt.text = optionsList[i].text;
			obj.options.add(opt);
		}
		obj.value = selValue;
	}

}

/**
 * This method delete the rows from the grid
 */
function deleteTableRow() {
	try {
		cnt = 1;
		if(hasCheckboxChecked()){
		var table = getDomElementById(tableId);
		var tableRowCount = table.rows.length;
		idsList = "";
		for(var j=1;j<rowCount;j++)
		{
			if(!isNull(getDomElementById("chk"+j)) && getDomElementById("chk"+j).checked == true){
				if(!isNull(getDomElementById("tripServicedByNumber"+j).value)){
				if(idsList.length > 0 ){
				  idsList = idsList+",";
				}
				idsList = idsList+ getDomElementById("tripServicedByNumber"+j).value;
				}
			}
			
		}

		for ( var i = 0; i < tableRowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				cnt++;
				deleteRow(tableId, i - 1);
				tableRowCount--;
				i--;
			}
		}
		var  rowAssign = true;
		for ( var i = 1, j=1; i < rowCount; i++) {
			if (!isNull(getDomElementById("count"+i))) {
				rowAssign = false;
				getDomElementById("serial"+i).innerHTML = j;
				getDomElementById("count"+i).value = j;
				j++;
				countNo = j;
			}
		}
		if(rowAssign)
			countNo = 1;
		
		getDomElementById("chk0").checked = false;
	  }else{
			alert("Please check atleast one row");
	 }
	} catch (e) {
		alert(e);
	}
}

/**
 * It checks whether atleast one row has been cheked or not 
 * @returns {Boolean}
 */
function hasCheckboxChecked(){
	for(var i = 1; i<rowCount;i++){
		if(!isNull(getDomElementById("chk"+i)) && getDomElementById("chk"+i).checked == true){
			hasChecked = true;
			return true;
		}
	}
	return false;
}
/**
 * This Method Deleted the selected row from the Grid
 * 
 * @param tableId
 * @param rowIndex
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

/**
 * This is AJAX Call This Method send the RouteServicedBy details to server to
 * save in the database
 */
function saveRouteServicedBy() {
	var check = false;
	serviceByTypeCode = (getDomElementById("serviceByType").value)
	.split(tild)[1];
	
	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	if (checkGridValues()) {
		for ( var i = 1; i < rowCount; i++) {
			if (serviceByTypeCode != "D" && !isNull(getDomElementById("vendorNumber" + i))
					&& !isNull(getDomElementById("vendorNumber" + i).value)){
				check = true;
				break;
			}else if (serviceByTypeCode == "D" && !isNull(getDomElementById("tripNumber" + i))
						&& !isNull(getDomElementById("tripNumber" + i).value)){
					check = true;
					break;
			}else{
				delStatus = false;
				check = false;
			}
		}
		
		/*if(delStatus)
			check = true;*/
		if (check) {
			getDomElementById("pageAction").value = action;
			getDomElementById("tripServicedIdsArrStr").value = idsList;
			jQuery.unblockUI();
			flag = confirm("Do you want to Save Route Serviced Details");
			if (!flag) {
				return;
			}
			enableHeaderFields();
			loadCheckBoxValues();
			var url = "./routeServicedBy.do?submitName=saveTripServicedBy";
			ajaxCallWithPost(url, "routeServicedByForm", saveCallback);
			jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
		} else {
			jQuery.unblockUI();
			alert("Please provide at least one row details of Grid");
			return;
		}
	}else{
		jQuery.unblockUI();
	}
}

/**
 * This Method popup message whether the data has been stored in database or not
 * 
 * @param data
 */
function saveCallback(data) {
	/*if (data == "SUCCESS") {
		if (searchRouteServicedBy())
			if(delStatus == true)
				alert("Route serviced has updated successfully.");
			else
			alert("Route serviced has created successfully.");
	} else if (data == "FAILURE") {
		disableHeaderFields();
		if(delStatus == true)
			alert("Transshipment Route serviced not updated.");
		else
		alert("Exception occurred. Route serviced not created.");
	}*/
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			disableHeaderFields();
			jQuery.unblockUI();
			alert(error);
		}else {
			resp = responseText[SUCCESS_FLAG];
			/*if(searchRouteServicedBy()){
				alert(resp);
			}*/
			searchRouteServicedBy();
			alert(resp);
		}
		}
	}else{
		jQuery.unblockUI();
	}
	delStatus = false;
}


/**
 * It checks whether atleast one check serviced week has checked or not
 * @param rowId
 * @param week
 * @returns {Boolean}
 */
function checkBox(rowId,week){

	if( !checkLastRow(rowId) && validateCheckBox(rowId) && isNull(getDomElementById("vendorNumber"+rowId).value) 
			&& isNull(getDomElementById("tripNumber"+rowId).value)){
		return;	
	}
	if(getDomElementById(week+rowId).checked == true){
	if(checkMandatory(rowId)){
		if(!checkLastRow(rowId)){
			//if(validateDayCheckBox(rowId,week)){
			addRow();
		if(serviceByTypeCode != "D"){	
			if(!isNull(getDomElementById("vendorNumber" + (rowCount-1)))){
			setTimeout(function() {
			getDomElementById("vendorNumber" + (rowCount-1)).focus();
				}, 10);
			}
		}else{
			if(!isNull(getDomElementById("tripNumber" + (rowCount-1)))){
				setTimeout(function() {
				getDomElementById("tripNumber" + (rowCount-1)).focus();
					}, 10);
				}
		}
		}
	}else{
		return false;
	}
	}
	
	return true;
}
/**
 * Checks the all the checkboxes of Grid Rows
 * 
 * @param rowId
 */

function checkAllBox(rowId, isCheck) {

	mon = getDomElementById("mon" + rowId);
	tue = getDomElementById("tue" + rowId);
	wed = getDomElementById("wed" + rowId);
	thu = getDomElementById("thu" + rowId);
	fri = getDomElementById("fri" + rowId);
	sat = getDomElementById("sat" + rowId);
	sun = getDomElementById("sun" + rowId);
	validDays = true;
	//validDays = validateDayCheckBox(rowId,"days");
	
	if (getDomElementById("days" + rowId).checked == true) {
		mon.checked = true;
		mon.disabled = true;

		tue.checked = true;
		tue.disabled = true;

		wed.checked = true;
		wed.disabled = true;

		thu.checked = true;
		thu.disabled = true;

		fri.checked = true;
		fri.disabled = true;

		sat.checked = true;
		sat.disabled = true;

		sun.checked = true;
		sun.disabled = true;

	} else {

		mon.checked = false;
		mon.disabled = false;

		tue.checked = false;
		tue.disabled = false;

		wed.checked = false;
		wed.disabled = false;

		thu.checked = false;
		thu.disabled = false;

		fri.checked = false;
		fri.disabled = false;

		sat.checked = false;
		sat.disabled = false;

		sun.checked = false;
		sun.disabled = false;

	}
	
	if(isCheck && (getDomElementById("days"+rowId).checked == true)){
	if( !checkLastRow(rowId) && validateCheckBox(rowId) && isNull(getDomElementById("vendorNumber"+rowId).value) 
			&& isNull(getDomElementById("tripNumber"+rowId).value)){
		return;	
	}
	if(checkMandatory(rowId)){
		if(!checkLastRow(rowId)){
			if(validDays){
			addRow();
		if(!isNull(getDomElementById("vendorNumber" + (rowCount-1)))){
		setTimeout(function() {
		getDomElementById("vendorNumber" + (rowCount-1)).focus();
			}, 10);
		}
		}
		}
	}else{
		return false;
	}
	}
	return true;
}

/**
 * Set the checkbox values into hidden fields
 */
function loadCheckBoxValues() {
	for ( var i = 1; i < rowCount; i++) {
		mon = getDomElementById("mon" + i);
		tue = getDomElementById("tue" + i);
		wed = getDomElementById("wed" + i);
		thu = getDomElementById("thu" + i);
		fri = getDomElementById("fri" + i);
		sat = getDomElementById("sat" + i);
		sun = getDomElementById("sun" + i);
		if (!isNull(sun)) {
			

			if (mon.checked == true)
				operationDays = "Y";
			else
				operationDays = "N";

			if (tue.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";

			if (wed.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";

			if (thu.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";

			if (fri.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";

			if (sat.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";
			
			if (sun.checked == true)
				operationDays = operationDays + "-Y";
			else
				operationDays = operationDays + "-N";

			getDomElementById("operationDaysArr" + i).value = operationDays;

			if (getDomElementById("days" + i).checked == true)
				getDomElementById("allDaysArr" + i).value = 'Y';
			else
				getDomElementById("allDaysArr" + i).value = 'N';
		}
	}
}

/**
 * Erase the Grid fields Data
 */
function deleteTableData() {
	var tableee = getDomElementById('dataTable');
	for ( var i = tableee.rows.length; i > 1; i--) {
		tableee.deleteRow(i - 1);
	}
}

/**
 * 
 * It loads the grid with appropriate labels based on Transport Mode.
 */
function validateTransportByMode() {
	var transportMode = getValueByElementId("transportMode");
	if (isNull(transportMode)) {
		return;
	}

	transportModeCode = transportMode.split(tild)[1];

	var optionsList = getDomElementById("serviceByType").options;
	var serVal = getDomElementById("serviceByType").value;
	for ( var i = 0; i < optionsList.length; i++) {
		opt = document.createElement('OPTION');
		val = optionsList[i].value;
		if (val == serVal) {
			changeLabelValue("vendorLabel", optionsList[i].text);
			labelVen = optionsList[i].text;
		}
	}

	if (transportModeCode == airCode) {
		changeLabelValue("flightTrainVehicleLabel", flightNumberLabel);
	} else if (transportModeCode == trainCode) {
		changeLabelValue("flightTrainVehicleLabel", trainNumberLabel);
	} else if (transportModeCode == roadCode) {
		changeLabelValue("flightTrainVehicleLabel", vehicleNumberLabel);
	}

}

/**
 * This is Ajax Call This is method get the City list based on Region
 * 
 * @param region
 * @param city
 */
function getStationByRegionId(region, city) {
	station = city;
	if (!isNull(region)) {
		var url = "./routeServicedBy.do?submitName=getStationsByRegion&regionId="
				+ region;
		ajaxCall(url, "routeServicedByForm", populateCity);
	} else {
		clearDropDownList(station);
	}

}

/**
 * This method populates the City Dropdown after get the response from
 * getStationByRegionId() AJAX Call
 * 
 * @param resp
 */
function populateCity(resp) {
/*	stationList = jsonJqueryParser(resp);
	clearDropDownList(station);
	obj = getDomElementById(station);
	if (!isNull(stationList)) {
		for ( var i = 0; i < stationList.length; i++) {
			addOptionTODropDown(station, stationList[i].cityName,
					stationList[i].cityId);
		}
	}
*/
	
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			stationList = responseText[SUCCESS_FLAG];			
			clearDropDownList(station);
			obj = getDomElementById(station);
			if (!isNull(stationList)) {
				for ( var i = 0; i < stationList.length; i++) {
					addOptionTODropDown(station, stationList[i].cityName,
							stationList[i].cityId);
				}
			}
			obj.focus();
		}
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error.");
	}
}

/**
 * This Method change the label value of grid columns
 * 
 * @param labelId
 * @param labelValue
 */
function changeLabelValue(labelId, labelValue) {
	labelVal = labelValue;
	getDomElementById(labelId).innerHTML = labelValue;
}

function displayData(vendorId, renewFlag, coloaderType) {
	var transportModeId="";
	var transportModeCode="";
	var serviceByTypeId="";
	var screenName="";
	
	if(renewFlag != 'R') {
		vendorDropDownId = vendorId;
		
		if(coloaderType != "" && coloaderType != null) {
			if(coloaderType == "RAIL") {
				transportModeId = 2;
				transportModeCode = 'Rail';
				serviceByTypeId = 2;
				screenName = 'Train Co-loader Rate Entry';
			}
			else {
				transportModeId = 1;
				transportModeCode = 'Air';
				serviceByTypeId = 1;
				screenName = 'Air Co-loader Rate Entry - AWB/CD';
			}
		} 
		serviceByTypeCode = 'C';

		var originRegionId = getValueByElementId("origionRegionList");
		var originStationId = getValueByElementId("origionStationList");
		var destinationStationId = getValueByElementId("destinationStationList");
		var effectiveFrom = getValueByElementId("effectiveFrom");
		
		// infinite end date as no calender for effectiveTo Date
		var effectiveTo = '30/12/2099';
//		var screenName = 'Air Co-loader Rate Entry - AWB/CD';
		
		if(effectiveFrom != "" && effectiveFrom != null && (originRegionId != "" && originRegionId != null) && originRegionId != '-1'){
			var url = "./routeServicedBy.do?submitName=getTripServicedByDetails&originStationId="
				+ originStationId
				+ "&destinationStationId="
				+ destinationStationId
				+ "&transportModeId="
				+ transportModeId
				+ "&serviceByTypeId="
				+ serviceByTypeId
				+ "&serviceByTypeCode="
				+ serviceByTypeCode
				+ "&effectiveFrom="
				+ effectiveFrom
				+ "&effectiveTo=" + effectiveTo
				+ "&originRegionId=" + originRegionId
				+ "&transportModeCode=" + transportModeCode
				+ "&screenName=" + screenName;	
			
			ajaxCallWithoutForm(url, getTripServicedByDetails);
			showProcessing();
		}
	}
}

function getTripServicedByDetails(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
			
		if (responseText != null && error != null) {
			if(error == "ERROR") {
				alert("No vendors found for selected region and date");
				
				var vendorListID = document.getElementById("vendorList");
				vendorListID.length = 0;	
				
				$("#trainbody").empty();
				//$("#threeButtons").empty();
				//$("#renewButtonId").empty();

			} else {
				alert(error);
			}
			return;
		}

		var content = document.getElementById(vendorDropDownId);
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.vendorId;
			option.appendChild(document.createTextNode(this.vendorCode + '-'
					+ this.businessName));
			content.appendChild(option);
		});
	}
}


/**
 * This is AJAX Call This is method get the RouteServicedBy details by the
 * search parameters.
 */
function searchRouteServicedBy() {
	tableId = "routeServicedByGrid";
	var transportMode = getValueByElementId("transportMode");
	
	var transportModeId = transportMode.split(tild)[0];
	var transportModeCode = transportMode.split(tild)[1];

	if (checkFields()) {
		validateTransportByMode();

		clearGrid();

		var originStationId = getDomElementById("originStationId").value;
		var destinationStationId = getDomElementById("destinationStationId").value;
		serviceByTypeId = (getDomElementById("serviceByType").value)
				.split(tild)[0];
		
		
		serviceByTypeCode = (getDomElementById("serviceByType").value)
				.split(tild)[1];
		
		var effectiveFrom = getDomElementById("effectiveFromStr").value;
		var effectiveTo = getDomElementById("effectiveToStr").value;
		var originRegionId = getDomElementById("originRegionId").value;
		var url = "./routeServicedBy.do?submitName=getTripServicedByDetails&originStationId="
				+ originStationId
				+ "&destinationStationId="
				+ destinationStationId
				+ "&transportModeId="
				+ transportModeId
				+ "&serviceByTypeId="
				+ serviceByTypeId
				+ "&serviceByTypeCode="
				+ serviceByTypeCode
				+ "&effectiveFrom="
				+ effectiveFrom
				+ "&effectiveTo=" + effectiveTo
				+ "&originRegionId=" + originRegionId
				+ "&transportModeCode=" + transportModeCode;

		ajaxCall(url, "routeServicedByForm", populateRouteServicedByDetails);
		showProcessing();
	}
	return true;
}

/**
 * This method populates the RouteServicedBy details in the screen after get the
 * response from searchRouteServicedBy() AJAX Call
 * 
 * @param data
 */
function populateRouteServicedByDetails(data) {
	
	delStatus = false;
	count = 1;
	action = "save";
	tsByList = "";
	serviceByTypeCode = (getDomElementById("serviceByType").value)
	.split(tild)[1];
	if(countNo != 1)
		countNo = 1;
	
	if (!isNull(data)) {
		var responseText =jsonJqueryParser(data);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			respData = responseText[SUCCESS_FLAG];
			disableHeaderFields();
			if (!isNull(respData)) {				
				vendorList = respData[0];
				tripList = respData[1];
				tsByList = respData[2];
				map = respData[3];
				
/*				if ( serviceByTypeCode != "D" && (isNull(vendorList) || vendorList.length == 0) ) {
					alert("There are no vendors");
					return false;
				}*/
				
				if (!isNull(tripList)) {
					getDomElementById("routeId").value = tripList[0].routeTO.routeId;
					getDomElementById("demo").style.display = '';
					getDomElementById("button_adddelete").style.display = '';
					getDomElementById("button_savecancel").style.display = '';
					if (!isNull(tsByList)) {
						var tsLen = tsByList.length;
						if(tsLen >0){
							delStatus = true;
							action = "update";
							}
						for ( var i = 0; i < tsLen; i++){
							if(!isNull(tsByList[i].tripServicedById)){
								addRow();								
								}
						}
					} else
						addRow();					
				} else {
					alert("Route does not exist between selected Origin station and Destination Station");
					enableHeaderFields();
					return false;
				}
				
				
			}
		}
		}else {
			alert("Exception occured while fetching the records.");
			enableHeaderFields();
			return false;
		}
	}else {
		alert("Exception occured while fetching the records.");
		//alert("No Pure Route data available for given date range.");
		enableHeaderFields();
		return false;
	}
	hideProcessing();
	/*count = 1;
	disableHeaderFields();
	respData = jsonJqueryParser(data);
	delStatus = false;
	if(countNo != 1)
		countNo = 1;
	if (!isNull(respData)) {
		vendorList = respData[0];
		tripList = respData[1];
		tsByList = respData[2];
		map = respData[3];
		if (isNull(vendorList)) {
			alert("There are no vendors");
			return false;
		}
		if (!isNull(tripList)) {
			getDomElementById("routeId").value = tripList[0].routeTO.routeId;
			getDomElementById("demo").style.display = '';
			getDomElementById("button_adddelete").style.display = '';
			getDomElementById("button_savecancel").style.display = '';
			if (!isNull(tsByList)) {
				delStatus = true;
				for ( var i = 0; i < tsByList.length; i++)
					if(!isNull(tsByList[i].tripServicedById)){
						addRow();
						}
			} else
				addRow();
		} else {
			alert("Pure roure does not exist between given Origin station and Destination Station");
			enableHeaderFields();
			return false;
		}

	} else {
		alert("Exception occured while fetching the records.");
		enableHeaderFields();
		return false;
	}*/

	search = true;
	return true;
}

/**
 * This method deletes the rows of the grid
 */
function clearGrid() {

	if (rowCount > 1) {
		var table = getDomElementById(tableId);
		var tableRowCount = table.rows.length;
		clearGridFieldsData(tableId, serialNo);
		for ( var i = 1; i < tableRowCount; i++) {
			deleteRow(tableId, i - 1);
			tableRowCount--;
			i--;
		}
		rowCount = 1;
		serialNo = 1;
	}
}

/**
 * This method delete the Grid Fields data
 * 
 * @param tableId
 * @param rowIndex
 */
function clearGridFieldsData(tableId, rowIndex) {
	for ( var i = 1; i < rowIndex; i++) {
		if (!isNull(getDomElementById("vendorNumber" + rowIndex))) {
			getDomElementById("vendorNumber" + rowIndex).value = "";
			getDomElementById("tripNumber" + rowIndex).value = "";
			getDomElementById("servicedByNumber" + rowIndex).value = "";
			getDomElementById("tripServicedByNumber" + rowIndex).value = "";
			getDomElementById("expDepartureTime" + rowIndex).value = "";
			getDomElementById("expArrivalTime" + rowIndex).value = "";
			getDomElementById("allDays" + rowIndex).checked = false;
			getDomElementById("mon" + rowIndex).checked = false;
			getDomElementById("tue" + rowIndex).checked = false;
			getDomElementById("wed" + rowIndex).checked = false;
			getDomElementById("thu" + rowIndex).checked = false;
			getDomElementById("fri" + rowIndex).checked = false;
			getDomElementById("sat" + rowIndex).checked = false;
			getDomElementById("sun" + rowIndex).checked = false;
			clearDropDownList("tripNumber" + rowIndex);

		}
	}

	getDomElementById("routeId").value = "";
}

/**
 * This method disabled the Header fields to prevent data changes
 */
function disableHeaderFields() {
	getDomElementById("originRegionId").disabled = true;
	getDomElementById("originStationId").disabled = true;
	getDomElementById("destinationRegionId").disabled = true;
	getDomElementById("destinationStationId").disabled = true;
	getDomElementById("transportMode").disabled = true;
	getDomElementById("serviceByType").disabled = true;
	$("#effectiveFromDt").hide();
	$("#effectiveToDt").hide();
}

/**
 * This method enabled the Header fields to do data changes
 */
function enableHeaderFields() {
	getDomElementById("originRegionId").disabled = false;
	getDomElementById("originStationId").disabled = false;
	getDomElementById("destinationRegionId").disabled = false;
	getDomElementById("destinationStationId").disabled = false;
	getDomElementById("transportMode").disabled = false;
	getDomElementById("serviceByType").disabled = false;
	$("#effectiveFromDt").show();
	$("#effectiveToDt").show();
}

/**
 * It deletes the Dropdown values of the fields
 * 
 * @param selectId
 */
function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, selectOption, "");
}

/**
 * It adds the values to dropdown
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * It clears the data of the Header fieds
 */
function clearHeaderFieldsData() {
	getDomElementById("originRegion").value = "";
	getDomElementById("originStationId").value = "";
	getDomElementById("destinationRegion").value = "";
	getDomElementById("destinationStationId").value = "";
	getDomElementById("transportMode").value = "";
	getDomElementById("serviceByType").value = "";
}

/**
 * It clears the data of the screen (Header fields data and Grid fields Data)
 */
function cancelRouteServicedBy() {
	flag = confirm("Do you want to Clear the Details");
	if (!flag) {
		return;
	}
	document.routeServicedByForm.action = "./routeServicedBy.do?submitName=viewRouteServicedBy";
	document.routeServicedByForm.submit();
}

/**
 * It checks whether the Origin station and Destination Station are same or not
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateStations(obj,city) {
	origin = getDomElementById("originStationId").value;
	destination = getDomElementById("destinationStationId").value;

	if (!isNull(origin) && !isNull(destination)) {
		if (origin == destination) {
			alert("Origin Station and Destination Station should not be same");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}
	}
	if(city == 'origin'){
		document.getElementById("destinationRegionId").focus();
	}else{
		document.getElementById("transportMode").focus();
	}
}

/**
 * This is Ajax Call This is method get the Service By Type list based on
 * Transport Mode
 * 
 * @param transportMode
 */
function getServiceByType(transportMode) {

	if (!isNull(transportMode)) {
		var transportModeId = transportMode.split(tild)[0];

		var url = "./routeServicedBy.do?submitName=getServiceByType&transportModeId="
				+ transportModeId;
		ajaxCall(url, "routeServicedByForm", populateServiceByType);
	} else {
		clearDropDownList("serviceByType");
	}

}

/**
 * This method populates the ServiceByType Dropdown after get the response from
 * getServiceByType() AJAX Call
 * 
 * @param resp
 */
function populateServiceByType(resp) {
/*	serviceByTypeList = jsonJqueryParser(resp);
	clearDropDownList("serviceByType");
	if (!isNull(serviceByTypeList)) {
		for ( var i = 0; i < serviceByTypeList.length; i++) {
			addOptionTODropDown("serviceByType", serviceByTypeList[i].label,
					serviceByTypeList[i].value);
		}
	}
*/
	
	if (!isNull(resp)) {
		var responseText =jsonJqueryParser(resp);
		if(responseText!=null){
		var error = responseText[ERROR_FLAG];
		if(error!=null){
			alert(error);
		}else {
			serviceByTypeList = responseText[SUCCESS_FLAG];			
			clearDropDownList("serviceByType");			
			if (!isNull(serviceByTypeList)) {
				for ( var i = 0; i < serviceByTypeList.length; i++) {
					addOptionTODropDown("serviceByType", serviceByTypeList[i].label,
							serviceByTypeList[i].value);
				}
			}
			document.getElementById("serviceByType").focus();
		}
		}
	}else{
		alert("Details are not fecthed due to Business Logic Error.");
	}
}

/**
 * It checks whether two rows of the grid are having same Flight/Train/Vehicle
 * number or not
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateFlightNumbers(rowId) {
	obj2 = getDomElementById("tripNumber" + rowId);
	objVal2 = getDomElementById("tripNumber" + rowId).value;
	ven2 = getDomElementById("vendorNumber" + rowId);
	venVal2 = getDomElementById("vendorNumber" + rowId).value;

	for ( var i = 1; i < rowCount; i++) {
		if (i != rowId) {
			obj1 = getDomElementById("tripNumber" + i);
			ven1 = getDomElementById("vendorNumber" + i);
			if (!isNull(obj1) && !isNull(ven1)) {
				venVal1 = getDomElementById("vendorNumber" + i).value;
				objVal1 = getDomElementById("tripNumber" + i).value;

				if ((venVal1 == venVal2) && (objVal1 == objVal2)) {
					alert(labelVal + "s row number " + getDomElementById("count" + i).value + " and " + getDomElementById("count" + rowId).value
							+ " should not be same");
					obj2.value = "";
					setTimeout(function() {
						obj2.focus();
					}, 10);
					return false;
				}
			}
		}
	}

	return true;
}

/**
 * It dont allow to modify the Date field values
 * 
 * @param field
 * @param value
 * @returns {Boolean}
 */
function checkDate(field, value) {
	
	if (search) {
		alert('You can not select the Date');
		return false;
	} else {
		show_calendar(field, value);
	}
	return true;
}

/**
 * Change the Cursor Style
 * 
 * @param fieldId
 */
function changeCursor(fieldId) {

	getDomElementById(fieldId).style.cursor = "pointer";

}

/**
 * It compare Effective date and Effective To dats values
 * 
 * @returns {Boolean}
 */
function compareDates() {
	var str1 = getDomElementById("effectiveFromStr").value;
	var str2 = getDomElementById("effectiveToStr").value;
	var dt1 = parseInt(str1.substring(0, 2), 10);
	var mon1 = parseInt(str1.substring(3, 5), 10);
	var yr1 = parseInt(str1.substring(6, 10), 10);
	var dt2 = parseInt(str2.substring(0, 2), 10);
	var mon2 = parseInt(str2.substring(3, 5), 10);
	var yr2 = parseInt(str2.substring(6, 10), 10);
	var date1 = new Date(yr1, (mon1 - 1), dt1);
	var date2 = new Date(yr2, (mon2 - 1), dt2);
	var date = new Date();
	date3 = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	if (date1 < date3) {
		alert("Effective From date should be same or greater than present date");
		return false;
	} else if (date2 < date1) {
		alert("Effective Till date should be same or greater than Effective From date");
		return false;

	}
	return true;
}

/**
 * It selects or unselects the check box of all rows of the Grid
 */
function gridCheckAll() {

	if (getDomElementById("chk0").checked == true) {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = true;
			}
		}
	} else {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = false;
			}
		}
	}
}

function checkLastRow(rowId){
	if(getDomElementById("count" + rowId).value == (countNo-1)){
		return false;
	}
	return true;
}