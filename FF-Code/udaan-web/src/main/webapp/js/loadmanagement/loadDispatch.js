/**
 * prepare page
 */
$(document).ready(function() {     
	var oTable = $('#loadDispatchGrid').dataTable( {         
	"sScrollY": "160",         
	"sScrollX": "100%",         
	"sScrollXInner": "120%",
	"bScrollCollapse": false,
	"bSort": false,
	"bInfo": false,
	"bPaginate": false,
	"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});

	loadGlobalValues();
	defaultChanges();
	getConsignmentTypeList();
	//CR : Weight should be taken from weighing machine at hub
	checkLoginOfficeTypeAndAllowWeighingMachine();
} );


var BRANCH_TO_BRANCH = "BTB";
var BRANCH_TO_HUB = "BTH";
var HUB_TO_BRANCH = "HTB";
var HUB_TO_HUB = "HTH";

/** The moduleName */
var moduleName = "dispatch";
/** The tripServicedByTOArray */
var tripServicedByTOArray = new Array();
/** The consignmentTypeTOArray */
var consignmentTypeTOArray = new Array();
/** The destOfficeClickFlag */
var destOfficeClickFlag = false;

//Using data grid
/** The rowCount */
var rowCount = 1;
var manifestDestCityFieldName = "manifestDestCity";
var weightKgFieldName = "weightKg";
var weightGmFieldName = "weightGm";
//Added by Himal
var cdWtKgFieldName = "cdWeightKg";
var cdWtGmFieldName = "cdWeightGm";
var tokenNoFieldName = "tokenNumber";

/**
 * addLoadDispatchRow
 *
 * @returns {Number}
 * @author narmdr
 */
function addLoadDispatchRow() {
	$('#loadDispatchGrid').dataTable().fnAddData( [
	'<input type="checkbox"   id="chk'+ rowCount +'" name="chkBoxName" value=""/>',
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="loadNumber'+ rowCount +'" name="to.loadNumber" size="15" maxlength="10" class="txtbox" onchange="validateManifestNumber(this);" onblur="setTotalBags();" onkeypress="enterKeyNavigationFocus4Grid(event, weightKgFieldName,'+ rowCount +');"/>',
	'<input type="text" id="manifestDestCity'+ rowCount +'" name="to.manifestDestCity" size="15" onchange="getManifestDestCity(this);" class="txtbox" onfocus="mBPLNumberFocus('+ rowCount +');" onkeypress="enterKeyNavigationFocus4Grid(event, weightKgFieldName,'+ rowCount +');" />',
	'<select name="to.docType" id="docType'+ rowCount +'"  onkeypress="enterKeyNavigationFocus(event,\'weightKg'+ rowCount +'\');" ><option value="">--Select--</option></select>',
	'<input type="text" id="weightKg'+ rowCount +'" maxlength="5" size="4" class="txtbox" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');" style="text-align: right" onfocus="validateWeightFromWeighingMachine('+ rowCount +');" /> \
	<span class="lable">.</span> \
	<input type="text" id="weightGm'+ rowCount +'" maxlength="3" size="4" class="txtbox" onblur="validateWeight('+ rowCount +');addRow('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightKgFieldName,'+ rowCount +');"/>',
	'<input type="text" id="cdWeightKg'+ rowCount +'" maxlength="4" name="to.cdWeightKg" size="4" class="txtbox" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, cdWtGmFieldName,'+ rowCount +');" onblur="validateCDWeight('+ rowCount +');" style="text-align: right"/> \
	<span class="lable">.</span> \
	<input type="text" id="cdWeightGm'+ rowCount +'" maxlength="3" size="4" class="txtbox" onblur="validateCDWeight('+ rowCount +');" onkeypress="enterKeyNavForCdWgt(event,'+ rowCount +');" />',
	'<input type="text" id="lockNumber'+ rowCount +'" name="to.lockNumber" size="15" maxlength="8" class="txtbox" onkeypress="enterKeyNavigationFocus(event,\'tokenNumber'+ rowCount +'\');"/>',
	'<input type="text" id="tokenNumber'+ rowCount +'" name="to.tokenNumber" size="15" maxlength="10" class="txtbox" onchange="validateSendMail('+ rowCount +');" onkeypress="enterKeyNavForCdAwb(event,'+ rowCount +');" onblur="validateCdDetails('+ rowCount +')" />',
	'<input type="text" id="remarks'+ rowCount +'" name="to.remarks" size="15" class="txtbox"/> \
	<input type="hidden" id="manifestId'+ rowCount +'" name="to.manifestId"/> \
	<input type="hidden" id="loadConnectedId'+ rowCount +'" name="to.loadConnectedId"/> \
	<input type="hidden" id="manifestDestCityDetails'+ rowCount +'" name="to.manifestDestCityDetails"/> \
	<input type="hidden" id="manifestWeight'+ rowCount +'" name="to.manifestWeight"/> \
	<input type="hidden" id="weight'+ rowCount +'" name="to.weight"/> \
	<input type="hidden" id="cdWeight'+ rowCount +'" name="to.cdWeight" value="0.000"/> \
	<input type="hidden" id="weightTolerance'+ rowCount +'" name="to.weightTolerance" value="N"/> \
	<input type="hidden" id="editFlag'+ rowCount +'" name="editFlag" value="Y"/> \
	<input type="hidden" id="sendMail'+ rowCount +'" name="to.sendMail" value="N"/> \
	<input type="hidden" id="manifestOriginOffId'+ rowCount +'" name="to.manifestOriginOffId"/> \
	<input type="hidden" id="manifestDestOffId'+ rowCount +'" name="to.manifestDestOffId"/>',
	
	] );

	/*onkeypress="enterKeyNavigationFocus4Grid(event, manifestDestCityFieldName,'+ rowCount +');"*/
	rowCount++;
	populateDocType(rowCount-1);
	updateSerialNoVal("loadDispatchGrid");
	return rowCount-1;
}

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	buttonDisableById("btnEdit");
	buttonDisableById("btnOffload");
	buttonDisableById("btnPrint");

	$("#destOfficeType").focus();
}

/**
 * deleteTableData
 *
 * @author narmdr
 */
function deleteTableData(){
	var tableee = document.getElementById('dataTable');
	for(var i=tableee.rows.length;i>1;i--) {
		tableee.deleteRow(i-1);
	}	
}

/**
 * validateWeight
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeight(rowIdd){
	if(!isNull(getValueByElementId("loadMovementId"))){
		return;
	}
	var weightKgObj = document.getElementById("weightKg" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
	var weightObj = document.getElementById("weight" + rowIdd);
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	weightObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	validateWeightTolerance(weightObj);
	//addRow(rowIdd);
	setTotalWeight();
}

/**
 * populateWeightInKgGmField
 *
 * @param weight
 * @param rowId
 * @author narmdr
 */
function populateWeightInKgGmField(weight, rowId){
	weight += "";
	var weightKgObj = document.getElementById("weightKg" + rowId);
	var weightGmObj = document.getElementById("weightGm" + rowId);
	
	if(!isNull(weight)){
		weightKgObj.value = weight.split(".")[0];
		weightGmObj.value = weight.split(".")[1];
	}
	fixWeightFormatForGram(weightGmObj);
}

/**
 * addRow
 *
 * @param selectedRowId
 * @author narmdr
 */
function addRow(selectedRowId){	
	var table = document.getElementById('loadDispatchGrid');
	var lastRow = table.rows.length - 1;
	var weightObj = document.getElementById("weight" + selectedRowId);
	var isNewRow = false;

	if(!validateMandatoryRowFields(selectedRowId)){
		weightObj.value = "";
		document.getElementById("weightKg" + selectedRowId).value = "";
		document.getElementById("weightGm" + selectedRowId).value = "";
		return;
	}
	var oldLastRowId = null;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(rowId==selectedRowId && i==lastRow){
			isNewRow = true;
		}
		oldLastRowId = rowId;
	}
	if(isNewRow){
		var rowId = addLoadDispatchRow();
		validateTableByOriginAndDestCity();
		//document.getElementById("loadNumber" + rowId).focus();
		focusById("loadNumber" + rowId);
	}
	else{
		focusById("loadNumber" + oldLastRowId);
		//document.getElementById("loadNumber" + oldLastRowId).focus();
	}
}

/**
 * validateMandatoryRowFields
 *
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var loadNumber = document.getElementById("loadNumber" + rowId);
	var manifestDestCity = document.getElementById("manifestDestCity" + rowId);
	var weight = document.getElementById("weight" + rowId);
	var manifestId = document.getElementById("manifestId" + rowId);
	
	if(isNull(manifestId.value)){
		if(isNull(loadNumber.value)){
			loadNumber.focus();
			alert(pleaseEnterMessage + bplMbplNoLabel + errorEndMsg);
			return false;
		}
		if(isNull(manifestDestCity.value)){
			manifestDestCity.focus();
			alert(pleaseEnterMessage + destinationCityLabel + errorEndMsg);
			return false;
		}
	}
	if(isNull(weight.value) || parseFloat(weight.value)==0.000){
		weight.focus();
		alert(pleaseEnterMessage + weightLabel + errorEndMsg);
		return false;
	}	
	return true;
}

/**
 * getConsignmentTypeList
 *
 * @author narmdr
 */
function getConsignmentTypeList(){
	var url = "./loadDispatch.do?submitName=getConsignmentTypeList";
	ajaxCall(url, "loadManagementForm", populateConsignmentTypeTOList);
}
/**
 * populateConsignmentTypeTOList
 *
 * @param req
 * @author narmdr
 */
function populateConsignmentTypeTOList(req){
	consignmentTypeTOArray = eval('(' + req + ')');
	addLoadDispatchRow();
	setDataTableDefaultWidth();
}

/**
 * populateDocType
 *
 * @param rowId
 * @author narmdr
 */
function populateDocType(rowId){
	clearDropDownList("docType" + rowId);
	for(var i=0;i<consignmentTypeTOArray.length;i++) {
		addOptionTODropDown("docType" + rowId, consignmentTypeTOArray[i].consignmentName, 
				consignmentTypeTOArray[i].consignmentId + tild + 
				consignmentTypeTOArray[i].consignmentCode + tild + 
				consignmentTypeTOArray[i].consignmentName);
	}		
}

/**
 * validateOthersVehicle
 *
 * @author narmdr
 */
function validateOthersVehicle(){
	var vehicleNumber = getValueByElementId("vehicleNumber");
	if(vehicleNumber==othersVehicleCode){
		showFieldById("otherVehicleNumber");
//		$("#otherVehicleNumber").focus();
		focusById("otherVehicleNumber");
	}else{
		clearFieldById("otherVehicleNumber");
		hideFieldById("otherVehicleNumber");
	}
}

/**
 * validateOthersTransport
 *
 * @author narmdr
 */
function validateOthersTransport(){
	var transportNumber = getValueByElementId("transportNumber");
	if(transportNumber==othersCode){
		showFieldById("otherTransportNumber");
		$("#otherTransportNumber").focus();
	}else{
		clearFieldById("otherTransportNumber");
		hideFieldById("otherTransportNumber");
	}
}

/**
 * getDestinationOffices
 *
 * @author narmdr
 */
function getDestinationOffices(){
	var destOfficeType = getValueByElementId("destOfficeType");
	clearFieldById("routeId");
	clearFieldById("destCity");
	if(isNull(destOfficeType)){
		clearDropDownList("destOffice");
		return;
	}
	var officeTypeId = destOfficeType.split(tild)[0];	
	var destOfficeTypeCode = destOfficeType.split(tild)[1];
	var originOfficeId = getValueByElementId("originOfficeId");
	var originCityId = getValueByElementId("originCityId");
	var url = "";
	
	if(destOfficeTypeCode==hubCode){
		url = "./loadDispatch.do?submitName=getDestinationOffices&officeTypeId="+officeTypeId+"&originOfficeId="+originOfficeId;
	}else {
		url = "./loadDispatch.do?submitName=getDestinationOffices&officeTypeId="+officeTypeId+"&originOfficeId="+originOfficeId+"&originCityId="+originCityId;
	}	

	showProcessing();
	$.ajax({
		url: url,
		success: function(req){populateDestinationOffices(req);}
	});
}

/**
 * populateDestinationOffices
 *
 * @param req
 * @author narmdr
 */
function populateDestinationOffices(req){
	hideProcessing();
	var officeTOList = eval('(' + req + ')');
	//var officeTOList = destOfficeListResponse.officeTOList;

	var errorMsg = getErrorMessage(officeTOList);
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}

	clearDropDownList("destOffice");
	for(var i=0;i<officeTOList.length;i++) {
		addOptionTODropDown("destOffice", 
				officeTOList[i].officeCode + hyphen + officeTOList[i].officeName, 
				officeTOList[i].officeId + tild + officeTOList[i].cityId + tild + 
				officeTOList[i].officeCode + hyphen + officeTOList[i].officeName);
	}	
}

/**
 * getRouteByOriginCityAndDestCity
 *
 * @author narmdr
 */
function getRouteByOriginCityAndDestCity(){
	var originCityId = document.getElementById("originCityId").value;
	var destCityId = getValueByElementId("destOffice").split(tild)[1];
	if(isNull(destCityId)){
		isDestOfficeInProcess = false;
		return;
	}
	var url = "./loadDispatch.do?submitName=getRouteByOriginCityAndDestCity&originCityId="+originCityId+"&destCityId="+destCityId;

	$.ajax({
		url: url,
		success: function(req){populateRouteId(req);}
	});
}
/**
 * populateRouteId
 *
 * @param routeIdRes
 * @author narmdr
 */
function populateRouteId(routeIdRes){
	var routeId = eval('(' + routeIdRes + ')');
	
	var errorMsg = getErrorMessage(routeId);
	if(!isNull(errorMsg)){
		isDestOfficeInProcess = false;
		alert(errorMsg);
		return;
	}

	if(isNull(routeId)){
		//alert("There is no route between " + originOfficeLabel +   " and " + destinationOfficeLabel);
		document.getElementById("routeId").value = "";
	}else{
		document.getElementById("routeId").value = routeId;		
	}
	
	if(destOfficeClickFlag){
		getServiceByTypeListByMode();
	}
}

/**
 * getServiceByTypeListByMode
 *
 * @author narmdr
 */
function getServiceByTypeListByMode(){
	var transportModeId = getValueByElementId("transportMode").split(tild)[0];
	if(isNull(transportModeId)){
		clearDropDownList("serviceByType");
		return;
	}
	var url = "./loadDispatch.do?submitName=getServiceByTypeListByTransportModeId&transportModeId="+transportModeId;

	$.ajax({
		url: url,
		success: function(req){populateServiceByType(req);}
	});
}
/**
 * populateServiceByType
 *
 * @param req
 * @author narmdr
 */
function populateServiceByType(req){
	//alert("There is no "+ typeLabel +" for selected " + modeLabel);
	var serviceByTypeList = eval('(' + req + ')');
	//var serviceByTypeList = destOfficeListResponse.serviceByTypeList;
	clearDropDownList("serviceByType");
	
	var errorMsg = getErrorMessage(serviceByTypeList);
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}

	for(var i=0;i<serviceByTypeList.length;i++) {
		addOptionTODropDown("serviceByType", serviceByTypeList[i].label, serviceByTypeList[i].value);
	}

	//To select default serviceByType
	if(destOfficeClickFlag){
		destOfficeClickFlag = false;
		
		if(isDifferentCities()){
			selectTypeByServiceByTypeCode(coloaderCode);
			document.getElementById("loadMovementVendor").focus();
		}else{
			selectTypeByServiceByTypeCode(directCode);
			document.getElementById("vehicleNumber").focus();
		}
		validateDirectColoader();
		getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId();
		isDestOfficeInProcess = false;
	}
}

/**
 * getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId
 *
 * @author narmdr
 */
function getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(){
	showProcessing();
	var serviceByTypeId = getValueByElementId("serviceByType").split(tild)[0];
	var transportModeId = getValueByElementId("transportMode").split(tild)[0];
	var routeId = getValueByElementId("routeId");
	var transportModeCode = document.getElementById("transportMode").value.split(tild)[1];
	var url;
	
	clearFieldById("expectedDeparture");
	clearFieldById("expectedArrival");
	clearDropDownList("loadMovementVendor");
	clearDropDownList("transportNumber");
	hideFieldById("otherTransportNumber");
	document.getElementById("otherTransportNumber").value = "";
	setTripServicedById("");
	
	if(isNull(transportModeId) || isNull(serviceByTypeId)){
		return;
	}
	
	//for Air & Train check route always
	if(transportModeCode==airCode || transportModeCode==trainCode){
		if(isNull(routeId)){
			alert("There is no route between " + originOfficeLabel +   " and " + destinationOfficeLabel);
			return;
		}
	}
	
	if(isDifferentCities()){
		if(isNull(routeId)){
			alert("There is no route between " + originOfficeLabel +   " and " + destinationOfficeLabel);
			return;
		}
		url = "./loadDispatch.do?submitName=getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId&routeId="+ routeId +"&serviceByTypeId="+ serviceByTypeId +"&transportModeId="+transportModeId;
	}else{
		url = "./loadDispatch.do?submitName=getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId&serviceByTypeId="+ serviceByTypeId +"&transportModeId="+transportModeId;
	}

/*	if(transportModeCode==roadCode){
		url = "./loadDispatch.do?submitName=getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId&serviceByTypeId="+ serviceByTypeId +"&transportModeId="+transportModeId;
	}else{
		if(isNull(routeId)){
			alert("There is no route between " + originOfficeLabel +   " and " + destinationOfficeLabel);
			return;
		}
		url = "./loadDispatch.do?submitName=getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId&routeId="+ routeId +"&serviceByTypeId="+ serviceByTypeId +"&transportModeId="+transportModeId;
	}*/

	$.ajax({
		url: url,
		success: function(req){populateTripServicedByTOList(req);}
	});
}
/**
 * populateTripServicedByTOList
 *
 * @param req
 * @author narmdr
 */
function populateTripServicedByTOList(req){
	//alert("There is no "+ typeLabel +" for selected " + modeLabel);	
	var tripServicedByTOList = eval('(' + req + ')');
	//var tripServicedByTOList = tripServicedByTOListResponse.tripServicedByTOList;

	var errorMsg = getErrorMessage(tripServicedByTOList);
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}

	tripServicedByTOArray = tripServicedByTOList;
	for(var i=0;i<tripServicedByTOList.length;i++){
		flushTripServicedByTO(tripServicedByTOList[i]);
	}

	if(tripServicedByTOList.length>0){
		addOptionTODropDown("transportNumber", othersLabel, othersCode);
	}
	
	hideProcessing();
}

/**
 * flushTripServicedByTO
 *
 * @param tripServicedByTO
 * @author narmdr
 */
function flushTripServicedByTO(tripServicedByTO) {
	var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	if(serviceByTypeCode==directCode){
		setTripServicedById(tripServicedByTO.tripServicedById);
		populateTransport(tripServicedByTO);
	}
	
	//format tripServicedById~vendorId~V or tripServicedById~empId~E
	if(!isNull(tripServicedByTO.servicedByTO)){
		if(tripServicedByTO.servicedByTO.servicedByType=="V" && !isNull(tripServicedByTO.servicedByTO.loadMovementVendorTO) ){
			addOptionTODropDown("loadMovementVendor", 
					tripServicedByTO.servicedByTO.loadMovementVendorTO.businessName, 
					tripServicedByTO.tripServicedById + tild + tripServicedByTO.servicedByTO.loadMovementVendorTO.vendorId + tild + "V");	
		}else{
			if(!isNull(tripServicedByTO.servicedByTO.employeeTO)){
				addOptionTODropDown("loadMovementVendor", 
						tripServicedByTO.servicedByTO.employeeTO.firstName + space + tripServicedByTO.servicedByTO.employeeTO.lastName, 
						tripServicedByTO.tripServicedById + tild + tripServicedByTO.servicedByTO.employeeTO.employeeId + tild + "E");					
			}
		}	
	}
	/*
	var transportModeCode = getValueByElementId("transportMode").split(tild)[1];
	
	if(transportModeCode==airCode){
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.flightTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.flightTO.flightNumber, 
					tripServicedByTO.tripServicedById);
		}		
	}else if(transportModeCode==trainCode){
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.trainTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.trainTO.trainNumber, 
					tripServicedByTO.tripServicedById);
		}	
	}else if(transportModeCode==roadCode){
		//setTripServicedById(tripServicedByTO.tripServicedById);		
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.vehicleTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.vehicleTO.regNumber, 
					tripServicedByTO.tripServicedById);
		}
	}*/	
}

/**
 * populateExpectedDepartureAndArrival
 *
 * @param tripServicedById
 * @author narmdr
 */
function populateExpectedDepartureAndArrival(tripServicedById){
	if(isNull(tripServicedById)){
		return;
	}
	if(tripServicedById==othersCode){
		clearFieldById("expectedDeparture");
		clearFieldById("expectedArrival");
		return;
	}
	
	for(var i=0;i<tripServicedByTOArray.length;i++){
		if(!isNull(tripServicedByTOArray[i].tripTO) && 
				!isNull(tripServicedByTOArray[i].tripTO.originPortTO)){
			if(tripServicedByTOArray[i].tripServicedById==tripServicedById){
				document.getElementById("expectedDeparture").value = 
					tripServicedByTOArray[i].tripTO.departureTime;
				
				document.getElementById("expectedArrival").value = 
					tripServicedByTOArray[i].tripTO.arrivalTime;
				break;
			}
		}
	}
}

//validate Flight, Train Label & Disable/Enable vehicle & FlightTrain Fields. 
/**
 * validateTransportByMode
 *
 * @author narmdr
 */
function validateTransportByMode(){
	var transportMode = getValueByElementId("transportMode");
	if(isNull(transportMode)){
		return;
	}
	
	var transportModeCode = transportMode.split(tild)[1];
	
	if(isDifferentCities()){
		clearAndDisableFieldById("vehicleNumber");
		hideFieldById("otherVehicleNumber");
		hideStar("vehicleNumberStar");
		enableFieldById("transportNumber");
		showStar("transportStar");
		showStar("typeStar");
		showStar("coloaderStar");
		
		if(transportModeCode==roadCode){
			changeLabelValue("transportLabel", vehicleNumberLabel);
			enableFieldById("driverName");
			showStar("driverNameStar");
		}else{
			clearAndDisableFieldById("driverName");
			hideStar("vehicleNumberStar");
			hideStar("driverNameStar");
			if(transportModeCode==airCode){
				changeLabelValue("transportLabel", flightNumberLabel);
			}else if(transportModeCode==trainCode){
				changeLabelValue("transportLabel", trainNumberLabel);			
			}
		}
	}else{//same cities
		if(transportModeCode==roadCode){
			clearAndDisableFieldById("transportNumber");
			clearFieldById("expectedDeparture");
			clearFieldById("expectedArrival");
			hideStar("transportStar");
			hideStar("typeStar");
			hideStar("coloaderStar");
			showStar("vehicleNumberStar");
			showStar("driverNameStar");
			enableFieldById("vehicleNumber");
			enableFieldById("driverName");
			clearAndDisableFieldById("transportNumber");
		}else{
			if(transportModeCode==airCode){
				changeLabelValue("transportLabel", flightNumberLabel);
			}else if(transportModeCode==trainCode){
				changeLabelValue("transportLabel", trainNumberLabel);			
			}
			clearAndDisableFieldById("vehicleNumber");
			clearAndDisableFieldById("driverName");
			enableFieldById("transportNumber");
			showStar("transportStar");
			showStar("typeStar");
			showStar("coloaderStar");
			hideStar("vehicleNumberStar");
			hideStar("driverNameStar");
			hideFieldById("otherVehicleNumber");
		}
	}
	
	$("#emailTransportLabel").val($("#transportLabel").html());	
}

/**
 * setTripServicedById
 *
 * @param tripServicedById
 * @author narmdr
 */
function setTripServicedById(tripServicedById){
	document.getElementById("tripServicedById").value = tripServicedById;
}

/**
 * setTransportNumber
 *
 * @param tripServicedById
 * @author narmdr
 */
function setTransportNumber(tripServicedById){
	setTripServicedById(tripServicedById);
	//var transportModeCode = document.getElementById("transportMode").value.split(tild)[1];	
	
	if(isDifferentCities()){
	//if(transportModeCode!=roadCode){
		document.getElementById("transportNumber").value = tripServicedById;	
		if(isNull(tripServicedById)){
			clearFieldById("expectedDeparture");
			clearFieldById("expectedArrival");			
		}else{
			populateExpectedDepartureAndArrival(tripServicedById);	
		}
	}
}

/**
 * setLoadMovementVendor
 *
 * @param tripServicedById
 * @author narmdr
 */
function setLoadMovementVendor(tripServicedById){
	if(tripServicedById!=othersCode){
		setTripServicedById(tripServicedById);		
	}
	
	if(isNull(tripServicedById)){
		clearFieldById("expectedDeparture");
		clearFieldById("expectedArrival");			
	}
	
	var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	/*if(isNull(serviceByTypeCode)){
		return;
	}*/
	//added because of emp mapping(don't display emp in case of direct)
	if(serviceByTypeCode!=directCode){
		document.getElementById("loadMovementVendor").value = tripServicedById;
	}	
}

/**
 * saveLoadDispatch
 *
 * @author narmdr
 */
function saveLoadDispatch(){
	var loadMovementId = getValueByElementId("loadMovementId");
	if(isNull(loadMovementId)){
		if(!validateMandatoryFields()){
			//setEmailValues();
			return;
		}
		/*if(!validateCdDetails()){
			return;
		}*/
	}
	
	//setting values for mail trigger
	
	setEmailValues();
	
	flag=confirm("Do you want to Save the Dispatch Details");
	if(!flag){
		return;
	}
	enableAll();
	var url = "./loadDispatch.do?submitName=saveOrUpdateLoadDispatch";
	ajaxCall(url, "loadManagementForm", saveCallback);
}

/**
 * saveCallback
 *
 * @param data
 * @author narmdr
 */
function saveCallback(data){
	showProcessing();
	var loadMovementTO = eval('(' + data + ')');
	
	if(!isNull(loadMovementTO.errorMessage)){
		alert(loadMovementTO.errorMessage);
		cancelPage();
		
	} else if(!isNull(loadMovementTO.successMessage)){
		/*var successMsg = loadMovementTO.successMessage;
		if(!isNull(loadMovementTO.smsErrorMessage)){
			successMsg = successMsg + loadMovementTO.smsErrorMessage;
		}*/
		alert(loadMovementTO.successMessage);
		document.getElementById("gatePassNumber").value = loadMovementTO.gatePassNumber;
		//call print function printLoadDispatch(url)
		var confirm1 = confirm("Do you want to print Load Dispatch Details.");
		if(confirm1){
			printLoadDispatch();
		}else{
			cancelPage();
		}
	}
}



function setEmailValues(){
	var destOfficeType  =$('#destOfficeType :selected').text();
	var destOffice  =$('#destOffice :selected').text();
	var transportMode  =$('#transportMode :selected').text();
	var serviceByType  =$('#serviceByType :selected').text();
	var serviceByTypeVal  =$('#serviceByType').val();
	
	var loadMovementVendor  =$('#loadMovementVendor :selected').text();
	var loadMovementVendorVal  =$('#loadMovementVendor').val();
	
	var transportNumber  =$('#transportNumber :selected').text();
	var transportNumberVal  =$('#transportNumber').val();
	
	var vehicleNumber  =$('#vehicleNumber :selected').text();
	var vehicleNumberVal  =$('#vehicleNumber').val();
	
	if(isNull(serviceByTypeVal)){
		serviceByType ="";
	}
	
	if(isNull(loadMovementVendorVal)){
		loadMovementVendor ="";
	}
	
/*	if(isNull(vehicleNumberVal)){
		vehicleNumber =$('#otherVehicleNumber').val();
	}
		
	if(isNull(transportMode)){
		transportMode =$('#otherTransportNumber').val();
	}*/
	
	if(isNull(vehicleNumberVal)){
		vehicleNumber ="";
	}
	if(isNull(transportNumberVal)){
		transportNumber = "";
	}
	if(vehicleNumberVal==othersVehicleCode){
		vehicleNumber = $('#otherVehicleNumber').val();
	}
	if(transportNumberVal==othersCode){
		transportNumber = $('#otherTransportNumber').val();		
	}
	$("#emailDstnOffcType").val(destOfficeType);	
	$("#emailDestOffice").val(destOffice);
	$("#emailTransportMode").val(transportMode);
	$("#emailServiceByType").val(serviceByType);
	$("#emailVendor").val(loadMovementVendor);	
	$("#emailTransportNumber").val(transportNumber);
	$("#emailVehicleNumber").val(vehicleNumber);
	
	
}

/**
 * cancelLoadDispatch
 *
 * @author narmdr
 */
function cancelLoadDispatch(){
	var flag = confirm("Do you want to cancel the Load Dispatch details.");
	if(flag){
		cancelPage();
		/*url = "./loadDispatch.do?submitName=viewLoadDispatch";
		submitForm(url, "loadManagementForm");*/
		/*document.loadManagementForm.action=url;
		document.loadManagementForm.submit();*/
	}
}

/**
 * cancelPage
 *
 * @author narmdr
 */
function cancelPage(){
	var url = "./loadDispatch.do?submitName=viewLoadDispatch";
	window.location = url;
}

/**
 * selectModeByTransportModeCode
 *
 * @param transportModeCode
 * @author narmdr
 */
function selectModeByTransportModeCode(transportModeCode){
	var el = document.getElementById("transportMode");
	for(var i=0; i<el.options.length; i++){
		if(el.options[i].value.split(tild)[1] == transportModeCode){
			el.selectedIndex = i; //var kk = el.options[i].text;
			break;
		}		
	}
}

/**
 * selectTypeByServiceByTypeCode
 *
 * @param serviceByTypeCode
 * @author narmdr
 */
function selectTypeByServiceByTypeCode(serviceByTypeCode){
	var el = document.getElementById("serviceByType");
	for(var i=0; i<el.options.length; i++){
		if(el.options[i].value.split(tild)[1] == serviceByTypeCode){
			el.selectedIndex = i;
			break;
		}		
	}
}

var isDestOfficeInProcess = false;
/**
 * validateDestinationOffice
 *
 * @author narmdr
 */
function validateDestinationOffice(){
	showProcessing();
	if(isDestOfficeInProcess){
		return;
	}
	isDestOfficeInProcess = true;
	destOfficeClickFlag = true;
	document.getElementById("routeId").value = "";
	clearFieldById("destCity");
	getDestCity();
	
	//code moved to populateDestCity due to asynchronous call of getDestCity
//	if(isDifferentCities()){
//		selectModeByTransportModeCode(airCode);
//		getRouteByOriginCityAndDestCity();
//		//getServiceByTypeListByMode(); this is calling in route
//	}else{
//		selectModeByTransportModeCode(roadCode);
//		getServiceByTypeListByMode();
//	}
//	validateTransportByMode();
//	validateTableByOriginAndDestCity();
	
	//validateOriginAndDestOfficeType();
	hideProcessing();
}

/*function validateOriginAndDestOfficeType(){
	var originOfficeType = getValueByElementId("originOfficeType");
	var destOfficeType = getValueByElementId("destOfficeType").split(tild)[1];
	
	//if(originOfficeType==hubCode && destOfficeType==hubCode){
	if(isDifferentCities()){
		selectModeByTransportModeCode(airCode);
	}else{
		selectModeByTransportModeCode(roadCode);
	}
	getServiceByTypeListByMode();
	validateTransportByMode();
	getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId();
}*/

/**
 * validateDirectColoaderLabelField
 *
 * @author narmdr
 */
function validateDirectColoaderLabelField(){
	var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	if(isNull(serviceByTypeCode)){
		return;
	}
	if(serviceByTypeCode==directCode){
		changeLabelValue("coloaderLabel", directLabel);
		document.getElementById("loadMovementVendor").value = "";
		disableFieldById("loadMovementVendor");
		hideStar("coloaderStar");
		
	}else if(serviceByTypeCode==coloaderCode){
		changeLabelValue("coloaderLabel", coloaderLabel);
		enableFieldById("loadMovementVendor");
		showStar("coloaderStar");	
		
	}else if(serviceByTypeCode==otcCode){
		changeLabelValue("coloaderLabel", otcLabel);
		enableFieldById("loadMovementVendor");
		showStar("coloaderStar");	
		
	}else if(serviceByTypeCode==obcCode){
		changeLabelValue("coloaderLabel", obcLabel);
		enableFieldById("loadMovementVendor");
		showStar("coloaderStar");		
	}
}

/**
 * validateDirectColoader
 *
 * @author narmdr
 */
function validateDirectColoader(){
	validateDirectColoaderLabelField();
}

//find GatePassNumber
/**
 * getLoadDispatchByGatePassNumber
 *
 * @returns {Boolean}
 * @author narmdr
 */
function getLoadDispatchByGatePassNumber(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var gatePassNumberObj = document.getElementById("gatePassNumber");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	
	//TODO validate gatePassNumber format.
	if(!isValidGatepassNo(gatePassNumberObj)){
		//document.getElementById("gatePassNumber").readOnly = true;
		return;
	}
	
	if(isNull(gatePassNumberObj.value)){
		gatePassNumberObj.focus();
		alert(pleaseEnterMessage + gatePassNoLabel + errorEndMsg);
		return false;
	}
	var url = "./loadDispatch.do?submitName=getLoadMovementTOByGatePassNumber&gatePassNumber="+gatePassNumberObj.value;
	ajaxCall(url, "loadManagementForm", populateLoadDispatch);
}

/**
 * populateLoadDispatch
 *
 * @param req
 * @author narmdr
 */
function populateLoadDispatch(req){
	var loadMovementTO = eval('(' + req + ')');
	//var loadMovementTO = loadMovementTOListResponse.loadMovementTO;
	
	var errorMsg = getErrorMessage(loadMovementTO);
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}

	if(isNull(loadMovementTO)){
		var gatePassNumberObj = document.getElementById("gatePassNumber");
		gatePassNumberObj.value = "";
		gatePassNumberObj.focus();
		//alert(invalidMsg + gatePassNoLabel);
		alert(gatePassNoLabel + " not found.");
	}else{
		populateLoadMovementTO(loadMovementTO);		
	}
}
/**
 * populateLoadMovementTO
 *
 * @param loadMovementTO
 * @author narmdr
 */
function populateLoadMovementTO(loadMovementTO){
	document.getElementById("loadMovementId").value = loadMovementTO.loadMovementId;
	document.getElementById("dispatchDateTime").value = loadMovementTO.dispatchDateTime;
	document.getElementById("gatePassNumber").value = loadMovementTO.gatePassNumber;
	document.getElementById("regionalOffice").value = loadMovementTO.regionalOffice;
	document.getElementById("originOfficeId").value = loadMovementTO.originOfficeId;
	document.getElementById("originOffice").value = loadMovementTO.originOffice;
	document.getElementById("destOfficeType").value = loadMovementTO.destOfficeType;
	document.getElementById("transportMode").value = loadMovementTO.transportMode;
	document.getElementById("processId").value = loadMovementTO.processId;
	document.getElementById("processNumber").value = loadMovementTO.processNumber;

	if(!isNull(loadMovementTO.destOffice)){
		addOptionTODropDown("destOffice", loadMovementTO.destOffice.split(tild)[2], loadMovementTO.destOffice);
		document.getElementById("destOffice").value = loadMovementTO.destOffice;
	}
	validateTransportByMode();
	
	if(!isNull(loadMovementTO.serviceByType)){
		addOptionTODropDown("serviceByType", loadMovementTO.serviceByType.split(tild)[0], loadMovementTO.serviceByType);
		document.getElementById("serviceByType").value = loadMovementTO.serviceByType;
		validateDirectColoaderLabelField();
	}
	if(!isNull(loadMovementTO.loadMovementVendor)){
		addOptionTODropDown("loadMovementVendor", loadMovementTO.loadMovementVendor, loadMovementTO.loadMovementVendor);
		document.getElementById("loadMovementVendor").value = loadMovementTO.loadMovementVendor;
	}
	if(!isNull(loadMovementTO.transportNumber)){
		if(loadMovementTO.transportNumber==othersCode){
			addOptionTODropDown("transportNumber", othersLabel, loadMovementTO.transportNumber);
			document.getElementById("transportNumber").value = loadMovementTO.transportNumber;			
			document.getElementById("otherTransportNumber").value = loadMovementTO.otherTransportNumber;
			validateOthersTransport();
		}else{
			addOptionTODropDown("transportNumber", loadMovementTO.transportNumber, loadMovementTO.transportNumber);
			document.getElementById("transportNumber").value = loadMovementTO.transportNumber;	
			document.getElementById("expectedDeparture").value = loadMovementTO.expectedDeparture;
			document.getElementById("expectedArrival").value = loadMovementTO.expectedArrival;		
		}
	}
	document.getElementById("vehicleNumber").value = loadMovementTO.vehicleNumber;
	document.getElementById("otherVehicleNumber").value = loadMovementTO.otherVehicleNumber;
	validateOthersVehicle();
	document.getElementById("driverName").value = loadMovementTO.driverName;
	document.getElementById("loadingTime").value = loadMovementTO.loadingTime;
	document.getElementById("tripServicedById").value = loadMovementTO.tripServicedById;

	deleteAllRowOfTableExceptHeaderRow('loadDispatchGrid');
	
	for(var i=0;i<loadMovementTO.loadDispatchDetailsTOs.length;i++) {
		var rowId = addLoadDispatchRow();
		populateLoadDispatchDetailsTO(loadMovementTO.loadDispatchDetailsTOs[i], rowId);		
	} 

	setTotalWeight();
	setTotalBags();
	disableAll();	
	buttonEnableById("btnEdit");
	buttonEnableById("btnOffload");
	buttonEnableById("btnPrint");
	buttonEnableById("btnCancel");
	buttonDisableById("btnSave");
	hideFieldById("deleteBtn");
}

/**
 * populateLoadDispatchDetailsTO
 *
 * @param loadDispatchDetailsTO
 * @param rowId
 * @author narmdr
 */
function populateLoadDispatchDetailsTO(loadDispatchDetailsTO, rowId){
	document.getElementById("loadNumber" + rowId).value = loadDispatchDetailsTO.loadNumber;
	selectOptionByFirstSplitValue(document.getElementById("docType" + rowId), loadDispatchDetailsTO.docType);
	document.getElementById("weight" + rowId).value = loadDispatchDetailsTO.weight;
	document.getElementById("cdWeight" + rowId).value = loadDispatchDetailsTO.cdWeight;
	fixFormatUptoThreeDecimalPlace(document.getElementById("weight" + rowId));
	fixFormatUptoThreeDecimalPlace(document.getElementById("cdWeight" + rowId));
	document.getElementById("lockNumber" + rowId).value = loadDispatchDetailsTO.lockNumber;
	document.getElementById("remarks" + rowId).value = loadDispatchDetailsTO.remarks;
	document.getElementById("manifestId" + rowId).value = loadDispatchDetailsTO.manifestId;
	document.getElementById("loadConnectedId" + rowId).value = loadDispatchDetailsTO.loadConnectedId;
	document.getElementById("manifestDestCity" + rowId).value = loadDispatchDetailsTO.manifestDestCity;
	document.getElementById("manifestDestCityDetails" + rowId).value = loadDispatchDetailsTO.manifestDestCityDetails;
	document.getElementById("manifestWeight" + rowId).value = loadDispatchDetailsTO.manifestWeight;
	if(!isNull(loadDispatchDetailsTO.tokenNumber)){
		document.getElementById("tokenNumber" + rowId).value = loadDispatchDetailsTO.tokenNumber;	
		document.getElementById("editFlag" + rowId).value = "N";
		disableFieldById("chk" + rowId);
	}else{
		document.getElementById("tokenNumber" + rowId).value = loadDispatchDetailsTO.tokenNumber;
		//document.getElementById("editFlag" + rowId).value = "Y";
	}	

	populateWeightInKgGmField(loadDispatchDetailsTO.weight, rowId);
	populateCDWeightInKgGmField(loadDispatchDetailsTO.cdWeight, rowId);
}

/**
 * setTotalBags
 *
 * @author narmdr
 */
function setTotalBags(){	
	try{
		var totalBags = 0;
		var table = document.getElementById('loadDispatchGrid');

		for (var i=1;i<table.rows.length;i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var loadNumber = document.getElementById("loadNumber" + rowId).value;
			if(!isNull(loadNumber)){
				totalBags++;
			}
		}
		document.getElementById("totalBags").value = totalBags;
	}catch(e) {
		document.getElementById("totalBags").value = "0";
	}	
}

/**
 * setTotalWeight
 *
 * @author narmdr
 */
function setTotalWeight(){
	try{
		var totalWeight = 0;
		var table = document.getElementById('loadDispatchGrid');

		for (var i=1;i<table.rows.length;i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var weight = document.getElementById("weight" + rowId).value;
			if(!isNull(weight)){
				totalWeight += parseFloat(weight);
			}
		}
		document.getElementById("totalWeight").value = totalWeight;
		fixFormatUptoThreeDecimalPlace(document.getElementById("totalWeight"));
		

		var weightKgObj = document.getElementById("totalWeightKg");
		var weightGmObj = document.getElementById("totalWeightGm");
		var totalWeight = document.getElementById("totalWeight").value;
		
		$("#totalWeight").val(totalWeight);
		
		if(!isNull(totalWeight)){
			weightKgObj.value = totalWeight.split(".")[0];
			weightGmObj.value = totalWeight.split(".")[1];
		}
	}catch(e) {
		document.getElementById("totalWeightKg").value = "0";
		document.getElementById("totalWeightGm").value = "000";
	}	
}


/**
 * edit
 *
 * @author narmdr
 */
function edit(){
	buttonEnableById("btnSave");
	var table = document.getElementById('loadDispatchGrid');
	var selectRowId = 0;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(getValueByElementId("editFlag" + rowId)=="Y"){
			if(selectRowId==0){
				selectRowId = rowId;				
			}
			document.getElementById("cdWeightKg" + rowId).readOnly = false;
			document.getElementById("cdWeightGm" + rowId).readOnly = false;
			document.getElementById("tokenNumber" + rowId).readOnly = false;
		}
	}
	if(selectRowId!=0){
		document.getElementById("cdWeightKg" + selectRowId).focus();
	}
}

/**
 * setOffloadIds
 *
 * @param offLoadId
 * @author narmdr
 */
function setOffloadIds(offLoadId){
	var offLoadIdsObj = document.getElementById("offLoadIds");
	if(isNull(offLoadIdsObj.value)){
		offLoadIdsObj.value = offLoadId;
	}else{
		offLoadIdsObj.value += tild + offLoadId;
	}
}

/**
 * offload
 *
 * @author narmdr
 */
function offload(){
	try {		
		buttonEnableById("btnSave");
		var tableId = "loadDispatchGrid";
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		var isRowSelected = false;
		for(var i=0; i<rowCount; i++) {
			var chkbox = table.rows[i].cells[0].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
				if(!isRowSelected){
					isRowSelected = true;
					var confirm1 = confirm("Do you want to offload selected BPL/MBPL.");
					if(!confirm1){
						return;
					}
				}
				if(rowCount <= 1) {
					//alert("Cannot delete all the rows.");
					break;
				}
				var rowId = getRowId(chkbox, "chk");
				setOffloadIds(getValueByElementId("loadConnectedId" + rowId));				
				deleteRow(tableId,i-1);
				rowCount--;
				i--;
			}
		}
		if(!isRowSelected){
			alert(pleaseSelectMsg + " at least one BPL/MBPL to offload.");
		}
		updateSerialNoVal("loadDispatchGrid");

		setTotalWeight();
		setTotalBags();
	}catch(e) {
		//alert(e);
	}
}

/**
 * deleteLoadDispatchRow
 *
 * @author narmdr
 */
function deleteLoadDispatchRow(){
	deleteTableRow("loadDispatchGrid");
	updateSerialNoVal("loadDispatchGrid");
	setTotalBags();
	setTotalWeight();
}

/**
 * validateManifestNumber
 *
 * @param manifestNumberObj
 * @returns {Boolean}
 * @author narmdr
 */
function validateManifestNumber(manifestNumberObj){
	manifestNumberObj.value = $.trim(manifestNumberObj.value);
	var loggedInOfficeId = getValueByElementId("originOfficeId");
	var originOfficeType = getValueByElementId("originOfficeType");
	var destOfficeType = getValueByElementId("destOfficeType").split(tild)[1];
	var destOffice = document.getElementById("destOffice");

	//TODO validate Manifest Number format 
	if(!isValidManifestNo(manifestNumberObj)){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		return;
	}
	
	if(isNull(destOffice.value)){
		manifestNumberObj.value = "";
		destOffice.focus();
		alert(pleaseSelectMsg + " " + destinationOfficeLabel + " first.");
		return false;
	}
	//prepare Master Bag List
	//var masterBagList = prepareMasterBagList(manifestNumberObj.value, "loadDispatchGrid");
	//alert(masterBagList);
	
	var url = "./loadDispatch.do?submitName=validateManifestNumber&manifestNumber="+manifestNumberObj.value+"&loggedInOfficeId="+loggedInOfficeId+"&destCityId="+destOffice.value.split(tild)[1]+"&originOfficeType="+originOfficeType+"&destOfficeType="+destOfficeType;

	if(isDuplicateFieldInGrid(manifestNumberObj, "loadNumber", "loadDispatchGrid")){
		alert("Duplicate " + bplMbplNoLabel);
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		return;
	}
	
	showProcessing();
	$.ajax({
		url: url,
		success: function(req){populateManifest(req, manifestNumberObj);}
	});
}

/**
 * populateManifest
 *
 * @param req
 * @param manifestNumberObj
 * @author narmdr
 */
function populateManifest(req, manifestNumberObj){
	var manifestTO = eval('(' + req + ')');
	var rowId = getRowId(manifestNumberObj, "loadNumber");
	hideProcessing();
	
	if(isNull(manifestTO) || manifestTO.isNewManifest==true || manifestTO.isNewManifest=="true"){
		//create new manifest
		document.getElementById("manifestDestCity" + rowId).focus();
		enableManifestFields(rowId);
		clearManifestFields(rowId);
		
	}else{
		if(!isNull(manifestTO.errorMsg)){
			manifestNumberObj.value = "";
			manifestNumberObj.focus();
			alert(manifestTO.errorMsg);

			enableManifestFields(rowId);
			clearManifestFields(rowId);
		}else{
			flushManifest(manifestTO, rowId);
			//CR : Weight should be taken from weighing machine at hub
			//var officetype = document.getElementById("originOfficeType").value;
			if(getOriginToDestOfficeDirection() == HUB_TO_HUB){
				document.getElementById("weightKg" + rowId).focus();
			} else {
				addRow(rowId);
			}
			disableManifestFields(rowId);
		}
	}

	setTotalWeight();
	setTotalBags();
}

function getOriginToDestOfficeDirection() {	
	var officeDirection = null;
	var originOfficeType = document.getElementById("originOfficeType").value;
	var destOfficeType = getValueByElementId("destOfficeType").split(tild)[1];
	
	if (isNull(originOfficeType) || isNull(destOfficeType)) {
		return officeDirection;
	} else if (originOfficeType == branchCode && destOfficeType == branchCode) {
		officeDirection = BRANCH_TO_BRANCH;
	} else if (originOfficeType == branchCode && destOfficeType == hubCode) {
		officeDirection = BRANCH_TO_HUB;
	} else if (originOfficeType == hubCode && destOfficeType == hubCode) {
		officeDirection = HUB_TO_HUB;
	} else if (originOfficeType == hubCode && destOfficeType == branchCode) {
		officeDirection = HUB_TO_BRANCH;
	}
	return officeDirection;
}

/**
 * flushManifest
 *
 * @param manifestTO
 * @param rowId
 * @author narmdr
 */
function flushManifest(manifestTO, rowId){	
	if(!isNull(manifestTO.destinationCityTO)){
		var cityTO = manifestTO.destinationCityTO;
		document.getElementById("manifestDestCity" + rowId).value = cityTO.cityCode;
		document.getElementById("manifestDestCityDetails" + rowId).value = cityTO.cityId + tild + 
					cityTO.cityCode + tild + cityTO.cityName;		
	}
	document.getElementById("manifestWeight" + rowId).value = manifestTO.manifestWeight;
	document.getElementById("manifestId" + rowId).value = manifestTO.manifestId;
	document.getElementById("lockNumber" + rowId).value = manifestTO.bagLockNo;
	
	if(!isNull(manifestTO.consignmentTypeTO)){
		//document.getElementById("docType" + rowId).value = manifestTO.consignmentTypeTO;
		selectOptionByFirstSplitValue(document.getElementById("docType" + rowId), 
				manifestTO.consignmentTypeTO.consignmentId);
	}

	//added origin office and destination office for manifest
	if(!isNull(manifestTO.originOfficeTO)){
		$("#manifestOriginOffId" + rowId).val(manifestTO.originOfficeTO.officeId);
	}
	if(!isNull(manifestTO.destinationOfficeTO)){
		$("#manifestDestOffId" + rowId).val(manifestTO.destinationOfficeTO.officeId);		
	}
	//CR : Weight should be taken from weighing machine at hub
	//var officetype = document.getElementById("originOfficeType").value;
	if(getOriginToDestOfficeDirection() != HUB_TO_HUB){
		var manifestWeight =  parseFloat(manifestTO.manifestWeight).toFixed(3);
		document.getElementById("weightKg" + rowId).value = manifestWeight.split(".")[0];
		document.getElementById("weightGm" + rowId).value = manifestWeight.split(".")[1];
		document.getElementById("weight" + rowId).value = manifestTO.manifestWeight;
	}
	
}

/**
 * enableManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function enableManifestFields(rowId){
	document.getElementById("manifestDestCity" + rowId).readOnly = false;
	document.getElementById("lockNumber" + rowId).readOnly = false;
	enableFieldById("docType" + rowId);
}

/**
 * disableManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function disableManifestFields(rowId){
	document.getElementById("manifestDestCity" + rowId).readOnly = true;
	document.getElementById("lockNumber" + rowId).readOnly = true;
	disableFieldById("docType" + rowId);	
}

/**
 * clearManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function clearManifestFields(rowId){
	clearFieldById("manifestId" + rowId);
	document.getElementById("manifestDestCity" + rowId).value = "";
	document.getElementById("lockNumber" + rowId).value = "";
	document.getElementById("docType" + rowId).value = "";
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var pleaseSelectMessage = pleaseSelectMsg + " ";
	var errorEndMsg = "." ;

	var destOfficeType = document.getElementById("destOfficeType");
	var destOffice = document.getElementById("destOffice");
	var transportMode = document.getElementById("transportMode");
	var transportModeCode = transportMode.value.split(tild)[1];
	var serviceByType = document.getElementById("serviceByType");
	var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	var loadMovementVendor = document.getElementById("loadMovementVendor");
	var transportNumber = document.getElementById("transportNumber");
	var otherTransportNumber = document.getElementById("otherTransportNumber");
	var vehicleNumber = document.getElementById("vehicleNumber");
	var otherVehicleNumber = document.getElementById("otherVehicleNumber");
	var driverName = document.getElementById("driverName");	
	var table = document.getElementById("loadDispatchGrid");
	
	if(isNull(destOfficeType.value)){
		destOfficeType.focus();
		alert(pleaseSelectMessage + destinationOfficeTypeLabel + errorEndMsg);
		return false;
	}
	if(isNull(destOffice.value)){
		destOffice.focus();;
		alert(pleaseSelectMessage + destinationOfficeLabel + errorEndMsg);
		return false;
	}
	if(isNull(transportMode.value)){
		transportMode.focus();
		alert(pleaseSelectMessage + modeLabel + errorEndMsg);
		return false;
	}
	if(transportModeCode==roadCode && isNull(serviceByType.value) && !isDifferentCities()){
		setTripServicedById("");
		loadMovementVendor.value="";
	}else{
		if(isNull(serviceByType.value)){
			serviceByType.focus();
			alert(pleaseSelectMessage + typeLabel + errorEndMsg);
			return false;
		}
		if(isNull(loadMovementVendor.value) && serviceByTypeCode!=directCode){
			loadMovementVendor.focus();
			var serviceByTypeCodeLabel = "";
			
			/*if(serviceByTypeCode==directCode){
				serviceByTypeCodeLabel = directLabel;			
			}else */
			if(serviceByTypeCode==coloaderCode){
				serviceByTypeCodeLabel = coloaderLabel;			
			}else if(serviceByTypeCode==otcCode){
				serviceByTypeCodeLabel = otcLabel;			
			}else if(serviceByTypeCode==obcCode){
				serviceByTypeCodeLabel = obcLabel;			
			}
			alert(pleaseSelectMessage + serviceByTypeCodeLabel + errorEndMsg);
			return false;
		}		
	}
	
	if(!isDifferentCities() && transportModeCode==roadCode){
		if(isNull(vehicleNumber.value)){
			vehicleNumber.focus();
			alert(pleaseSelectMessage + vehicleNumberLabel + errorEndMsg);
			return false;
		}
		if(vehicleNumber.value==othersVehicleCode && isNull(otherVehicleNumber.value)){
			otherVehicleNumber.focus();
			alert(pleaseEnterMessage + "Other " + vehicleNumberLabel + errorEndMsg);
			return false;
		}
	}else{
		if(isNull(transportNumber.value)){
			transportNumber.focus();
			if(transportModeCode==airCode){
				alert(pleaseSelectMessage + flightNumberLabel + errorEndMsg);
			}else if(transportModeCode==trainCode){
				alert(pleaseSelectMessage + trainNumberLabel + errorEndMsg);	
			}else if(transportModeCode==roadCode){
				alert(pleaseSelectMessage + vehicleNumberLabel + errorEndMsg);	
			}
			return false;
		}
		if(transportNumber.value==othersCode && isNull(otherTransportNumber.value)){
			otherTransportNumber.focus();
			var labelName="";
			if(transportModeCode==roadCode){
				labelName = vehicleNumberLabel;
			}else if(transportModeCode==airCode){
				labelName = flightNumberLabel;
			}else if(transportModeCode==trainCode){
				labelName = trainNumberLabel;
			}
			alert(pleaseEnterMessage + "Other " + labelName + errorEndMsg);
			return false;
		}
	}
	
	if(transportModeCode==roadCode){
		if(isNull(driverName.value)){
			driverName.focus();
			alert(pleaseEnterMessage + driverNameLabel + errorEndMsg);
			return false;
		}
	}
/*	if(transportModeCode==roadCode){
		if(isNull(vehicleNumber.value)){
			vehicleNumber.focus();
			alert(pleaseSelectMessage + vehicleNumberLabel + errorEndMsg);
			return false;
		}
		if(vehicleNumber.value==othersVehicleCode && isNull(otherVehicleNumber.value)){
			otherVehicleNumber.focus();
			alert(pleaseEnterMessage + "Other " + vehicleNumberLabel + errorEndMsg);
			return false;
		}
		if(isNull(driverName.value)){
			driverName.focus();
			alert(pleaseEnterMessage + driverNameLabel + errorEndMsg);
			return false;
		}
	}else{
		if(isNull(transportNumber.value)){
			transportNumber.focus();
			if(transportModeCode==airCode){
				alert(pleaseSelectMessage + flightNumberLabel + errorEndMsg);
			}else if(transportModeCode==trainCode){
				alert(pleaseSelectMessage + trainNumberLabel + errorEndMsg);	
			}
			return false;
		}
	}*/
	
	var isEmptyBag = true;
	var tableRowId = 1;
	//Validating table data	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var loadNumber = document.getElementById("loadNumber" + rowId);
		//var lockNumber = document.getElementById("lockNumber" + rowId);
		var manifestDestCity = document.getElementById("manifestDestCity" + rowId);
		//var docType = document.getElementById("docType" + rowId);
		var weight = document.getElementById("weight" + rowId);
		var manifestId = document.getElementById("manifestId" + rowId);
		tableRowId = rowId;
		
//		if(i==(table.rows.length - 1)){
//			continue;
//		}
		if(isNull(loadNumber.value)){
			continue;
		}
		if(isNull(manifestId.value)){
			/*if(isNull(loadNumber.value)){
				loadNumber.focus();
				alert(pleaseEnterMessage + bplMbplNoLabel + errorEndMsg);
				return false;
			}*/
			if(isNull(manifestDestCity.value)){
				manifestDestCity.focus();
				alert(pleaseEnterMessage + destinationCityLabel + errorEndMsg);
				return false;
			}
			/*if(isNull(lockNumber.value)){
				lockNumber.focus();
				alert(pleaseEnterMessage + lockNoLabel + errorEndMsg);
				return false;
			}*/
			/*if(isNull(docType.value)){
				docType.focus();
				alert(pleaseSelectMessage + documentTypeLabel + errorEndMsg);
				return false;
			}*/
		}
		if(isNull(weight.value) || parseFloat(weight.value)==0.000){
			var weightKgObj = $("#weightKg" + rowId);
			clearFocusAlertMsg(weightKgObj, pleaseEnterMessage + weightLabel + errorEndMsg);
			return false;
		}
		if(!isNull(loadNumber.value)){
			isEmptyBag = false;
		}
	}
	
	if(isEmptyBag){
		var loadNumberObj = document.getElementById("loadNumber" + tableRowId);
		clearFocusAlertMsg(loadNumberObj, pleaseEnterMessage + "at least one BPL/MBPL" + errorEndMsg);
		return false;
	}
	return true;
}

/**
 * isDifferentCities
 *
 * @returns {Boolean}
 * @author narmdr
 */
function isDifferentCities(){
	var destCityId = getValueByElementId("destOffice").split(tild)[1];
	var originCityId = getValueByElementId("originCityId");
	
	if(originCityId!=destCityId){
		return true;
	}
	return false;
}

/**
 * getDestCity
 *
 * @author narmdr
 */
function getDestCity(){
	var destCityId = getValueByElementId("destOffice").split(tild)[1];
	if(isNull(destCityId)){
		return;
	}
	var url = "./loadDispatch.do?submitName=getCity&cityId="+destCityId;

	$.ajax({
		url: url,
		success: function(data){populateDestCity(data);}
	});
}
/**
 * populateDestCity
 *
 * @param data
 * @author narmdr
 */
function populateDestCity(data){
	var cityTO = eval('(' + data + ')');
	if(!isNull(cityTO)){
		document.getElementById("destCity").value = cityTO.cityId + tild + 
				cityTO.cityCode + tild + cityTO.cityName;
	}
	
	//code moved here due to asynchronous call from validateDestinationOffice
	if(destOfficeClickFlag){
		if(isDifferentCities()){
			selectModeByTransportModeCode(airCode);
			getRouteByOriginCityAndDestCity();
			//getServiceByTypeListByMode(); this is calling in route
		}else{
			isDestOfficeInProcess = false;
			selectModeByTransportModeCode(roadCode);
			getServiceByTypeListByMode();
		}
		validateTransportByMode();
		validateTableByOriginAndDestCity();		
	}
}

/**
 * getManifestDestCity
 *
 * @param manifestDestCityObj
 * @returns {Boolean}
 * @author narmdr
 */
function getManifestDestCity(manifestDestCityObj){
	var destOffice = document.getElementById("destOffice");
	if(isNull(manifestDestCityObj.value)){
		return;
	}
	if(isNull(destOffice.value)){
		manifestDestCityObj.value = "";
		destOffice.focus();
		alert(pleaseSelectMsg + " " + destinationOfficeLabel + " first.");
		return false;
	}
	
	var rowId = getRowId(manifestDestCityObj, "manifestDestCity");
	var url = "./loadDispatch.do?submitName=getCity&cityCode="+manifestDestCityObj.value;
	showProcessing();
	
	$.ajax({
		url: url,
		success: function(data){populateManifestDestCity(data, manifestDestCityObj, rowId);}
	});
}

/**
 * populateManifestDestCity
 *
 * @param data
 * @param manifestDestCityObj
 * @param rowId
 * @author narmdr
 */
function populateManifestDestCity(data, manifestDestCityObj, rowId){
	var cityTO = eval('(' + data + ')');
	hideProcessing();
	
	if(!isNull(cityTO)){
		document.getElementById("manifestDestCityDetails" + rowId).value = cityTO.cityId + tild + 
				cityTO.cityCode + tild + cityTO.cityName;
		validatePureOrTransshipmentRoute(manifestDestCityObj, rowId);
	}else{
		manifestDestCityObj.value = "";
		clearFieldById("manifestDestCityDetails" + rowId);
		manifestDestCityObj.focus();
		alert("Invalid city code.");
		return;
	}	
}

/**
 * validatePureOrTransshipmentRoute
 *
 * @param manifestDestCityObj
 * @param rowId
 * @author narmdr
 */
function validatePureOrTransshipmentRoute(manifestDestCityObj, rowId){
	var dispatchDestCityId = getValueByElementId("destCity").split(tild)[0]; //transshipmentCityId
	var manifestDestCityId = getValueByElementId("manifestDestCityDetails" + rowId).split(tild)[0]; //servicedCityId
	
	//validate Pure Route
	if(isPureRoute(dispatchDestCityId, manifestDestCityId)){
		document.getElementById("manifestDestCityDetails" + rowId).focus();
		return;
	}
	
	//validate Transshipment Route
	var url = "./loadDispatch.do?submitName=validateTransshipmentRoute&transshipmentCityId=" + dispatchDestCityId +"&servicedCityId="+manifestDestCityId;

	$.ajax({
		url: url,
		success: function(data){isTransshipmentRoute(data, manifestDestCityObj, rowId);}
	});
}

//validate whether manifest Destination City & Dispatch Destination City are same.
/**
 * isPureRoute
 *
 * @param dispatchDestCityId
 * @param manifestDestCityId
 * @returns {Boolean}
 * @author narmdr
 */
function isPureRoute(dispatchDestCityId, manifestDestCityId){
	if(dispatchDestCityId==manifestDestCityId){
		return true;
	}
	return false;
}

/**
 * isTransshipmentRoute
 *
 * @param isTransshipmentFlagRes
 * @param manifestDestCityObj
 * @param rowId
 * @author narmdr
 */
function isTransshipmentRoute(isTransshipmentFlagRes, manifestDestCityObj, rowId){
	var isTransshipmentFlag = eval('(' + isTransshipmentFlagRes + ')');

	var errorMsg = getErrorMessage(isTransshipmentFlag);
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}

	if(isTransshipmentFlag==true || isTransshipmentFlag=="true"){
		document.getElementById("docType" + rowId).focus();
	}else{
		manifestDestCityObj.focus();
		alert("There is no Pure/Transshipment Route defined for : " + manifestDestCityObj.value + ".");
		clearFieldById("manifestDestCityDetails" + rowId);
		manifestDestCityObj.value = "";
		return;
	}	
}

/**
 * validateCDWeight
 *
 * @param rowIdd
 * @author narmdr
 */
function validateCDWeight(rowIdd){
	var cdWeightKgObj = document.getElementById("cdWeightKg" + rowIdd);
	var cdWeightGmObj = document.getElementById("cdWeightGm" + rowIdd);
	var cdWeightObj = document.getElementById("cdWeight" + rowIdd);
	
	fixWeightFormatForGram(cdWeightGmObj);
	
	if(cdWeightKgObj.value.length==0){
		cdWeightKgObj.value = "0";
	}
	cdWeightObj.value = cdWeightKgObj.value+ "."+ cdWeightGmObj.value;
}

/**
 * populateCDWeightInKgGmField
 *
 * @param weight
 * @param rowId
 * @author narmdr
 */
function populateCDWeightInKgGmField(weight, rowId){
	weight += "";
	var cdWeightKgObj = document.getElementById("cdWeightKg" + rowId);
	var cdWeightGmObj = document.getElementById("cdWeightGm" + rowId);
	
	if(!isNull(weight)){
		cdWeightKgObj.value = weight.split(".")[0];
		cdWeightGmObj.value = weight.split(".")[1];
	}else{
		cdWeightKgObj.value = "0";
	}
	fixWeightFormatForGram(cdWeightGmObj);
}

/**
 * validateTableByOriginAndDestCity
 *
 * @author narmdr
 */
function validateTableByOriginAndDestCity(){
	/*If origin and destination office are in the same city then CD weight and 
	CD airway bill or RR number should be disabled.*/
	var table = document.getElementById("loadDispatchGrid");

	try{		
		for (var i=1;i<table.rows.length;i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var tokenNumberObj = document.getElementById("tokenNumber" + rowId);
			var cdWeightKgObj = document.getElementById("cdWeightKg" + rowId);
			var cdWeightGmObj = document.getElementById("cdWeightGm" + rowId);

			if(isDifferentCities()){
				tokenNumberObj.disabled = false;
				cdWeightKgObj.disabled = false;
				cdWeightGmObj.disabled = false;
			}else{
				tokenNumberObj.disabled = true;
				cdWeightKgObj.disabled = true;
				cdWeightGmObj.disabled = true;
			}
		}
	}catch(e) {
		
	}
}

/**
 * mBPLNumberFocus
 *
 * @param rowIdd
 * @author narmdr
 */
function mBPLNumberFocus(rowIdd){
	var loadNumberObj = document.getElementById("loadNumber" + rowIdd);
	loadNumberObj.focus();
	/*if(isNull(loadNumberObj.value)){
		loadNumberObj.focus();
	}*/
}

/**
 * validateSendMail
 *
 * @param rowIdd
 * @author narmdr
 */
function validateSendMail(rowIdd){
	var sendMailObj = document.getElementById("sendMail" + rowIdd);
	var tokenNumberObj = document.getElementById("tokenNumber" + rowIdd);
	if(!isNull(tokenNumberObj.value)){
		sendMailObj.value = "Y";
	}else{
		sendMailObj.value = "N";		
	}
}

//Enter Key Navigation
/**
 * enterKeyNavigation
 *
 * @param evt
 * @param elementId
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigation(evt, elementId){	
	if(evt==13){
		document.getElementById(elementId).click();
		return true;
	}
	return false;
}

/*function getWeightFromWMachine(rowIdd){
	if(isNull(getValueByElementId("loadMovementId"))){
		validateWeightFromWeighingMachine(rowIdd);
	}
}*/

/**
 * printDispatch
 *
 * @returns {Boolean}
 * @author sdalli
 */
function printDispatch(){
	var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var gatePassNumberObj = document.getElementById("gatePassNumber");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	
	if(isNull(gatePassNumberObj.value)){
		gatePassNumberObj.focus();
		alert(pleaseEnterMessage + gatePassNoLabel + errorEndMsg);
		return false;
	}
		
	if(!isValidGatepassNo(gatePassNumberObj)){
		//document.getElementById("gatePassNumber").readOnly = true;
		return;
	}
	
	var confirm1 = confirm("Do you want to print Load Dispatch Details.");
	if(confirm1){			
		printLoadDispatch();
	}
}


/**
 * printLoadDispatch
 *
 * @author sdalli
 */
function printLoadDispatch(){
	var gatePassNumberObj = document.getElementById("gatePassNumber");
	gatePassNumberObj.value = $.trim(gatePassNumberObj.value);
	
	var url = "./loadDispatch.do?submitName=printLoadDispatch&gatePassNumber="+gatePassNumberObj.value;
	//var w =window.open(url,'','height=450,width=600,left=60,top=120,resizable=yes,scrollbars=auto');
	document.loadManagementForm.action=url;
	document.loadManagementForm.submit();		
}

/**
 * enterKeyNavigationFocus4VehicleNo
 *
 * @param evt
 * @returns {Boolean}
 * @author narmdr
 */
/*function enterKeyNavigationFocus4VehicleNo(evt){
	if(isEnterKey(evt)){
		var vehicleNumber = getValueByElementId("vehicleNumber");
		if(vehicleNumber==othersVehicleCode){
			$("#otherVehicleNumber").focus();
		}else{
			$("#driverName").focus();
		}
		return true;
	}
	return false;
}*/


/**
 * isEnterKey
 *
 * @param evt
 * @returns {Boolean}
 * @author narmdr
 */
/*function isEnterKey(evt){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	
	if(charCode==13){
		return true;
	}
	return false;
}*/

function getTripServicedByTOsForTransport(){
	showProcessing();
	var serviceByTypeId = getValueByElementId("serviceByType").split(tild)[0];
	var transportModeId = getValueByElementId("transportMode").split(tild)[0];
	var routeId = getValueByElementId("routeId");
	
	//format tripServicedById~vendorId~V or tripServicedById~empId~E
	var vendorId = getValueByElementId("loadMovementVendor").split(tild)[1];
		
	clearFieldById("expectedDeparture");
	clearFieldById("expectedArrival");
	clearDropDownList("transportNumber");
	hideFieldById("otherTransportNumber");
	document.getElementById("otherTransportNumber").value = "";
	setTripServicedById("");
	
	if(isNull(transportModeId) || isNull(serviceByTypeId) || isNull(routeId) || isNull(vendorId)){
		return;
	}
	
	var url = "./loadDispatch.do?submitName=getTripServicedByTOsForTransport&serviceByTypeId="+ serviceByTypeId +"&transportModeId="+transportModeId+"&routeId="+ routeId +"&vendorId="+ vendorId;

	$.ajax({
		url: url,
		success: function(req){populateTripServicedByTOsForTransport(req);}
	});
}

function populateTripServicedByTOsForTransport(req){
	var tripServicedByTOList = eval('(' + req + ')');
	tripServicedByTOArray = tripServicedByTOList;
	for(var i=0;i<tripServicedByTOList.length;i++){
		populateTransport(tripServicedByTOList[i]);
	}

	if(tripServicedByTOList.length>0){
		addOptionTODropDown("transportNumber", othersLabel, othersCode);
	}
	
	hideProcessing();
}

function populateTransport(tripServicedByTO) {
	
	var transportModeCode = getValueByElementId("transportMode").split(tild)[1];
	
	if(transportModeCode==airCode){
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.flightTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.flightTO.flightNumber, 
					tripServicedByTO.tripServicedById);
		}		
	}else if(transportModeCode==trainCode){
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.trainTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.trainTO.trainNumber, 
					tripServicedByTO.tripServicedById);
		}
	}else if(transportModeCode==roadCode){
		//setTripServicedById(tripServicedByTO.tripServicedById);
		if(!isNull(tripServicedByTO.tripTO) && !isNull(tripServicedByTO.tripTO.transportTO) 
				&& !isNull(tripServicedByTO.tripTO.transportTO.vehicleTO)){
			addOptionTODropDown("transportNumber", 
					tripServicedByTO.tripTO.transportTO.vehicleTO.regNumber, 
					tripServicedByTO.tripServicedById);
		}
	}
}

function isDirectServiceByTypeCode(){	
	var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	if(serviceByTypeCode==directCode){
		return true;
	}
	return false;
}

function setTransportTripServicedById(tripServicedById){
	if(tripServicedById!=othersCode  && !isNull(tripServicedById)){
		setTripServicedById(tripServicedById);
		/*if(!isDirectServiceByTypeCode() && !isNull(tripServicedById)){
		}*/
	}else{
		//format tripServicedById~vendorId~V or tripServicedById~empId~E
		var tripServicedByIdd = getValueByElementId("loadMovementVendor").split(tild)[0];
		
		//var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
		if(!isDirectServiceByTypeCode()){
			setTripServicedById(tripServicedByIdd);	
		}
	}
	
	if(isNull(tripServicedById)){
		clearFieldById("expectedDeparture");
		clearFieldById("expectedArrival");			
	}

	populateExpectedDepartureAndArrival(tripServicedById);
	
	/*var serviceByTypeCode = getValueByElementId("serviceByType").split(tild)[1];
	//added because of emp mapping(don't display emp in case of direct)
	if(serviceByTypeCode!=directCode){
		document.getElementById("loadMovementVendor").value = tripServicedById;
	}*/
}
function enterKeyNavForVehicle(evt){
	if(isEnterKey(evt)){
		validateOthersVehicle();
		if(document.getElementById('otherVehicleNumber').style.visibility =='hidden'){
			focusById("driverName");
		}else {
			focusById("otherVehicleNumber");
		}
	}
}
function enterKeyNavForCdWgt(evt,rowCount){
	if(isEnterKey(evt)){
		var loadId=document.getElementById("loadConnectedId" + rowCount).value;
		if(isNull(loadId)){
			//document.getElementById("lockNumber" + rowCount).focus();
			focusById("lockNumber" + rowCount);
		}else{
			focusById("tokenNumber" + rowCount);
			//document.getElementById("tokenNumber" + rowCount).focus();
		}
	}
}
function enterKeyNavForCdAwb(evt,rCount){
	var table = document.getElementById('loadDispatchGrid');
	var lastRow = table.rows.length;
	var isFocusGained=false;
	var currentRowCount=parseInt(rCount,10);
	if(isEnterKey(evt)){
		var loadId=document.getElementById("loadConnectedId" + rCount).value;
		if(!isNull(loadId) ){
			
			var  tokenNumberDom=document.getElementsByName("to.tokenNumber");
			var weightKgDom= document.getElementsByName("to.cdWeightKg");
			var totalRows=tokenNumberDom.length;
			if(tokenNumberDom!=null && totalRows>1){
				for(var rowCounter=0;rowCounter<totalRows; rowCounter++){
					var tokenRowId=parseInt(getRowId(tokenNumberDom[rowCounter], "tokenNumber"));
					if((tokenRowId >currentRowCount) && isNull(tokenNumberDom[rowCounter].value) ){
						setFocusOnDom(weightKgDom[rowCounter]);
						isFocusGained=true;
						break;
					}
				}
			}
			if(!isFocusGained){
				focusById("btnSave");
			}
		}else{
			focusById("remarks" + rCount);
			//document.getElementById("remarks" + rowCount).focus();
		}
	}
	/**
	 * setFocusOnDom
	 * @param domElement
	 */
	function setFocusOnDom(domElement){
		if(domElement!=null){
			setTimeout( /** make a focus on given element*/
					function(){domElement.focus();}
					,10);
		}
	}

}

/**
 * CD Wieght and CD/AWB/RR No. are Manadatory if either of value is entered
 * 
 * @param rowId
 * @returns {Boolean}
 */
function validateCdDetails(rowId) {
	
	var tokenNumber = document.getElementById("tokenNumber" + rowId);
	var cdWeightKg = document.getElementById("cdWeightKg" + rowId);
	var cdWeight = document.getElementById("cdWeight" + rowId);
	
	if(!isNull(tokenNumber.value)) {
		if (isNull(cdWeight.value) || parseFloat(cdWeight.value)==0.000){
			cdWeightKg.focus();
			alert("Please enter CD Weight against CD/AWB/RR Number entered");
			return false;
		}
	} else if (!isNull(cdWeight.value) && parseFloat(cdWeight.value) > 0.000) {
		if (isNull(tokenNumber.value)) {
			tokenNumber.focus();
			alert("Please enter CD/AWB/RR Number against CD Weight entered");
			return false;
		}
	}
	return true;
}