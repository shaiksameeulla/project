/**
 * prepare page
 */
$(document).ready(function() {     
	var oTable = $('#loadReceiveOutstationGrid').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "150%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
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
	getTransportModeList();
	getWeightFromWeighingMachine();
} ); 

/** The moduleName */
var moduleName = "receiveOut";
/** The transportModeLabelValueArray */
var transportModeLabelValueArray = new Array();

//Using data grid
var rowCount = 1;
var loadNumberFieldName ="loadNumber";
var manifestDestCityFieldName = "manifestDestCity";
var weightKgFieldName = "weightKg";
var weightGmFieldName = "weightGm";
var lockNumberFieldName = "lockNumber";
var recvTransportNumberFieldName = "recvTransportNumber";
var recvVendorFieldName = "recvVendor";
var recvTransportModeFieldName = "recvTransportMode";


/**
 * addLoadReceiveOutstationRow
 *
 * @returns {Number}
 * @author narmdr
 */
function addLoadReceiveOutstationRow() {
	$('#loadReceiveOutstationGrid').dataTable().fnAddData( [
	'<input type="checkbox"   id="chk'+ rowCount +'" name="chkBoxName" value=""/>',
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="loadNumber'+ rowCount +'" name="to.loadNumber" size="15" maxlength="10" class="txtbox" onchange="validateManifestNumber(this);" onblur="setTotalBags();" onkeypress="enterKeyNavigationFocus4Grid(event, manifestDestCityFieldName,'+ rowCount +');"/>',
	'<input type="text" id="manifestDestCity'+ rowCount +'" name="to.manifestDestCity" size="15" maxlength="3" onchange="getDestinationCity(this);" class="txtbox" onfocus="mBPLNumberFocus('+ rowCount +');" onkeypress="enterKeyNavigationFocus4Grid(event, weightKgFieldName,'+ rowCount +');" />',
	'<input type="text" id="weightKg'+ rowCount +'" maxlength="5" size="4" class="txtbox"  onblur="validateWeight('+ rowCount +');" style="text-align: right" onfocus="validateWeightFromWeighingMachine('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, weightGmFieldName,'+ rowCount +');"/> \
	<span class="lable">.</span> \
	<input type="text" id="weightGm'+ rowCount +'" maxlength="3" size="4" class="txtbox" onblur="validateWeight('+ rowCount +');" onkeypress="return onlyNumberAndEnterKeyNavFocus4Grid(event, lockNumberFieldName,'+ rowCount +');"/>',
	'<input type="text" id="lockNumber'+ rowCount +'" name="to.lockNumber" size="15" maxlength="8" class="txtbox" onkeypress="enterKeyNavigationFocus4Grid(event, recvTransportNumberFieldName,'+ rowCount +');" />',
	'<input type="text" id="recvTransportNumber'+ rowCount +'" name="to.recvTransportNumber" size="15" maxlength="30" class="txtbox" onchange="isValidTransportNumber(this);" onkeypress="enterKeyNavigationFocus4Grid(event, recvVendorFieldName,'+ rowCount +');"/>',
	'<input type="text" id="tokenNumber'+ rowCount +'" name="to.tokenNumber" size="15" class="txtbox" maxlength="10" onchange="isValidAwbCdRrNumber(this);" onkeypress="enterKeyNavigationFocus(event,\'recvVendor'+ rowCount +'\');" />',
	'<input type="text" id="recvVendor'+ rowCount +'" name="to.recvVendor" size="15" maxlength="60" class="txtbox" onblur="validateVendor('+ rowCount +');" onkeypress="enterKeyNavigationFocus4Grid(event, recvTransportModeFieldName,'+ rowCount +');"/>',
	'<select id="recvTransportMode'+ rowCount +'" name="to.recvTransportMode" class="txtbox" onfocus="vendorFocus('+ rowCount +')" onkeypress="enterKeyNavigationFocus(event,\'recvGatePassNumber'+ rowCount +'\');" /><option value="">--Select--</option></select>',
	'<input type="text" id="recvGatePassNumber'+ rowCount +'" name="to.recvGatePassNumber" size="15" maxlength="12" class="txtbox" onchange="isValidGatepassNo(this);" onkeypress="enterKeyNavigationFocus(event,\'remarks'+ rowCount +'\');" />',
	'<input type="text" id="remarks'+ rowCount +'" name="to.remarks" size="15" maxlength="199" class="txtbox"/> \
	<input type="hidden" id="manifestId'+ rowCount +'" name="to.manifestId"/> \
	<input type="hidden" id="loadConnectedId'+ rowCount +'" name="to.loadConnectedId"/> \
	<input type="hidden" id="manifestDestCityDetails'+ rowCount +'" name="to.manifestDestCityDetails"/> \
	<input type="hidden" id="manifestWeight'+ rowCount +'" name="to.manifestWeight"/> \
	<input type="hidden" id="receivedStatus'+ rowCount +'" name="to.receivedStatus"/> \
	<input type="hidden" id="weight'+ rowCount +'" name="to.weight"/> \
	<input type="hidden" id="manifestOriginOffId'+ rowCount +'" name="to.manifestOriginOffId"/> \
	<input type="hidden" id="manifestDestOffId'+ rowCount +'" name="to.manifestDestOffId"/> \
	<input type="hidden" id="consgTypeId'+ rowCount +'" name="to.consgTypeId"/> \
	<input type="hidden" id="docType'+ rowCount +'" name="to.docType"/> \
	<input type="hidden" id="weightTolerance'+ rowCount +'" name="to.weightTolerance" value="N"/>'
	] );

	rowCount++;
	updateSerialNoVal("loadReceiveOutstationGrid");
	populateTransportModes(rowCount-1);
	return rowCount-1;
}

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	//document.getElementById("receiveNumber").focus();
}

/**
 * validateVendor
 *
 * @param rowIdd
 * @author narmdr
 */
function validateVendor(rowIdd){
	addRow(rowIdd);
}

/**
 * validateWeight
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeight(rowIdd){
	var weightKgObj = document.getElementById("weightKg" + rowIdd);
	var weightGmObj = document.getElementById("weightGm" + rowIdd);
	var weightObj = document.getElementById("weight" + rowIdd);
	
	fixWeightFormatForGram(weightGmObj);
	
	if(weightKgObj.value.length==0){
		weightKgObj.value = "0";
	}
	weightObj.value = weightKgObj.value+ "."+ weightGmObj.value;
	validateWeightTolerance(weightObj);
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
	var table = document.getElementById('loadReceiveOutstationGrid');
	var lastRow = table.rows.length - 1;
	var isNewRow = false;

	if(!validateMandatoryRowFields(selectedRowId)){
		var vendorObj = document.getElementById("recvVendor" + selectedRowId);
		vendorObj.value = "";
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
		var rowId = addLoadReceiveOutstationRow();
		document.getElementById("loadNumber" + rowId).focus(); 
	}else{
		document.getElementById("loadNumber" + oldLastRowId).focus();
	}
}

/**
 * getDestinationCity
 *
 * @param manifestDestCityObj
 * @author narmdr
 */
function getDestinationCity(manifestDestCityObj){
	manifestDestCityObj.value = $.trim(manifestDestCityObj.value);
	
	if(isNull(manifestDestCityObj)){
		return;
	}
	var url = "./loadReceiveOutstation.do?submitName=getCity&cityCode="+manifestDestCityObj.value;

	$.ajax({
		url: url,
		success: function(data){populateDestinationCity(data, manifestDestCityObj);}
	});
}

/**
 * populateDestinationCity
 *
 * @param data
 * @param manifestDestCityObj
 * @author narmdr
 */
function populateDestinationCity(data, manifestDestCityObj){
	var rowId = getRowId(manifestDestCityObj, "manifestDestCity");
	var cityTO = eval('(' + data + ')');
	
	if(!isNull(cityTO)){
		document.getElementById("manifestDestCityDetails" + rowId).value = 
			cityTO.cityId + tild + cityTO.cityCode + tild + cityTO.cityName;
	}else{
		manifestDestCityObj.value = "";
		clearFieldById("manifestDestCityDetails" + rowId);
		manifestDestCityObj.focus();
		alert("Invalid City Code.");
		return;
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
	var weightKgObj = document.getElementById("weightKg" + rowId);
	var manifestId = document.getElementById("manifestId" + rowId);
	var recvTransportNumber = document.getElementById("recvTransportNumber" + rowId);
	var lockNumberObj = document.getElementById("lockNumber" + rowId);
	var vendorObj = document.getElementById("recvVendor" + rowId);
	
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
		weightKgObj.focus();
		alert(pleaseEnterMessage + weightLabel + errorEndMsg);
		return false;
	}
	if(isNull(lockNumberObj.value)){
		lockNumberObj.focus();
		alert(pleaseEnterMessage + lockNoLabel + errorEndMsg);
		return false;
	}
	if(isNull(recvTransportNumber.value)){
		recvTransportNumber.focus();
		alert(pleaseEnterMessage + flightTrainVehicleLabel + errorEndMsg);
		return false;
	}
	if(isNull(vendorObj.value)){
		vendorObj.focus();
		alert(pleaseEnterMessage + coloaderOtcObcLabel + errorEndMsg);
		return false;
	}
	return true;
}

/**
 * deleteLoadReceiveOutstationRow
 *
 * @author narmdr
 */
function deleteLoadReceiveOutstationRow(){
	deleteTableRow("loadReceiveOutstationGrid");
	updateSerialNoVal("loadReceiveOutstationGrid");
	setTotalBags();
}

/**
 * setTotalBags
 *
 * @author narmdr
 */
function setTotalBags(){	
	var totalBags = 0;
	var table = document.getElementById('loadReceiveOutstationGrid');

	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var loadNumber = document.getElementById("loadNumber" + rowId).value;
		if(!isNull(loadNumber)){
			totalBags++;
		}
	}
	document.getElementById("totalBags").value = totalBags;
}

/**
 * setTotalWeight
 *
 * @author narmdr
 */
function setTotalWeight(){
	var totalWeight = 0;
	var table = document.getElementById('loadReceiveOutstationGrid');

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
	
	if(!isNull(totalWeight)){
		weightKgObj.value = totalWeight.split(".")[0];
		weightGmObj.value = totalWeight.split(".")[1];
	}
}

/**
 * getTransportModeList
 *
 * @author narmdr
 */
function getTransportModeList(){
	var url = "./loadReceiveOutstation.do?submitName=getTransportModeList";
	ajaxCall(url, "loadReceiveOutstationForm", populateTransportModeList);
}
/**
 * populateTransportModeList
 *
 * @param req
 * @author narmdr
 */
function populateTransportModeList(req){
	transportModeLabelValueArray = eval('(' + req + ')');
	var rowId = addLoadReceiveOutstationRow();
	$("#loadNumber"+rowId).focus();
	setDataTableDefaultWidth();
}

/**
 * populateTransportModes
 *
 * @param rowId
 * @author narmdr
 */
function populateTransportModes(rowId){
	clearDropDownList("recvTransportMode" + rowId);
	for(var i=0;i<transportModeLabelValueArray.length;i++) {
		addOptionTODropDown("recvTransportMode" + rowId, transportModeLabelValueArray[i].label, 
				transportModeLabelValueArray[i].value.split(tild)[0]);
	}
}

//Manifest BPL/MBPL validation Starts
/**
 * validateManifestNumber
 *
 * @param manifestNumberObj
 * @author narmdr
 */
function validateManifestNumber(manifestNumberObj){
	manifestNumberObj.value = $.trim(manifestNumberObj.value);
	var destOfficeId = getValueByElementId("destOfficeId");
	var rowId = getRowId(manifestNumberObj, "loadNumber");			

	//validate Manifest Number format 
	if(!isValidManifestNo(manifestNumberObj)){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		enableManifestFields(rowId);
		clearManifestFields(rowId);	
		return;
	}
	
	if(isDuplicateFieldInGrid(manifestNumberObj, "loadNumber", "loadReceiveOutstationGrid")){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		alert("Duplicate " + bplMbplNoLabel);
		return;
	}
	var url = "./loadReceiveOutstation.do?submitName=validateManifestNumber&manifestNumber="+manifestNumberObj.value+"&destOfficeId="+destOfficeId;
	
	showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateManifest(data, manifestNumberObj);}
	});
}

/**
 * populateManifest
 *
 * @param data
 * @param manifestNumberObj
 * @author narmdr
 */
function populateManifest(data, manifestNumberObj){
	hideProcessing();
	var loadReceiveManifestValidationTO = eval('(' + data + ')');
	var rowId = getRowId(manifestNumberObj, "loadNumber");
	
	if(loadReceiveManifestValidationTO.isNewManifest==true){
		//create new manifest
		document.getElementById("manifestDestCity" + rowId).focus();
		enableManifestFields(rowId);
		clearManifestFields(rowId);
		//return;
		
	}else if(!isNull(loadReceiveManifestValidationTO.manifestTO)){
		flushManifest(loadReceiveManifestValidationTO.manifestTO, rowId);
		document.getElementById("weightKg" + rowId).focus();
		disableManifestFields(rowId);
		
	}else if(!isNull(loadReceiveManifestValidationTO.loadReceiveOutstationDetailsTO)){
		populateLoadReceiveOutstationDetailsTO4Manifest(loadReceiveManifestValidationTO.loadReceiveOutstationDetailsTO, rowId);
		document.getElementById("weightKg" + rowId).focus();
		disableManifestFields(rowId);
		
	}else if(!isNull(loadReceiveManifestValidationTO.errorMsg)){
		manifestNumberObj.value = "";
		manifestNumberObj.focus();
		alert(loadReceiveManifestValidationTO.errorMsg);

		enableManifestFields(rowId);
		clearManifestFields(rowId);
	}
	
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
		$("#consgTypeId" + rowId).val(manifestTO.consignmentTypeTO.consignmentId);
		$("#docType" + rowId).val(manifestTO.consignmentTypeTO.consignmentCode);
	}

	//added origin office and destination office for manifest
	if(!isNull(manifestTO.originOfficeTO)){
		$("#manifestOriginOffId" + rowId).val(manifestTO.originOfficeTO.officeId);
	}
	if(!isNull(manifestTO.destinationOfficeTO)){
		$("#manifestDestOffId" + rowId).val(manifestTO.destinationOfficeTO.officeId);		
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
}

/**
 * disableManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function disableManifestFields(rowId){
	document.getElementById("manifestDestCity" + rowId).readOnly = true;
}

/**
 * clearManifestFields
 *
 * @param rowId
 * @author narmdr
 */
function clearManifestFields(rowId){
	clearFieldById("manifestId" + rowId);
	clearFieldById("manifestWeight" + rowId);
	clearFieldById("manifestDestCity" + rowId);
	clearFieldById("manifestDestCityDetails" + rowId);
	clearFieldById("lockNumber" + rowId);
	clearFieldById("recvGatePassNumber" + rowId);
	
	enableFieldById("recvGatePassNumber" + rowId);
}

/**
 * populateLoadReceiveOutstationDetailsTO4Manifest
 *
 * @param loadReceiveOutstationDetailsTO
 * @param rowId
 * @author narmdr
 */
function populateLoadReceiveOutstationDetailsTO4Manifest(loadReceiveOutstationDetailsTO, rowId){	
	//document.getElementById("loadNumber" + rowId).value = loadReceiveOutstationDetailsTO.loadNumber;
	document.getElementById("lockNumber" + rowId).value = loadReceiveOutstationDetailsTO.lockNumber;
	document.getElementById("manifestId" + rowId).value = loadReceiveOutstationDetailsTO.manifestId;
	document.getElementById("manifestDestCity" + rowId).value = loadReceiveOutstationDetailsTO.manifestDestCity;
	document.getElementById("manifestDestCityDetails" + rowId).value = loadReceiveOutstationDetailsTO.manifestDestCityDetails;
	document.getElementById("manifestWeight" + rowId).value = loadReceiveOutstationDetailsTO.manifestWeight;
	document.getElementById("recvTransportNumber" + rowId).value = loadReceiveOutstationDetailsTO.transportNumber;
	document.getElementById("recvVendor" + rowId).value = loadReceiveOutstationDetailsTO.vendor;
	document.getElementById("recvGatePassNumber" + rowId).value = loadReceiveOutstationDetailsTO.gatePassNumber;
	document.getElementById("tokenNumber" + rowId).value = loadReceiveOutstationDetailsTO.tokenNumber;
	document.getElementById("recvTransportMode" + rowId).value = loadReceiveOutstationDetailsTO.transportMode;
	
	disableFieldById("recvGatePassNumber" + rowId);

	//added origin office and destination office for manifest
	$("#manifestOriginOffId" + rowId).val(loadReceiveOutstationDetailsTO.manifestOriginOffId);
	$("#manifestDestOffId" + rowId).val(loadReceiveOutstationDetailsTO.manifestDestOffId);
	$("#consgTypeId" + rowId).val(loadReceiveOutstationDetailsTO.consgTypeId);
	$("#docType" + rowId).val(loadReceiveOutstationDetailsTO.docType);
		
	/*document.getElementById("weight" + rowId).value = loadReceiveOutstationDetailsTO.weight;
	populateWeightInKgGmField(loadReceiveOutstationDetailsTO.weight, rowId);
	fixFormatUptoThreeDecimalPlace(document.getElementById("weight" + rowId));*/
}
//Manifest BPL/MBPL validation End

/**
 * cancelLoadReceiveOutstation
 *
 * @author narmdr
 */
function cancelLoadReceiveOutstation(){
	var flag = confirm("Do you want to cancel the Load Receive Local details.");
	if(flag){
		cancelPage();
	}
}

/**
 * saveLoadReceiveOutstation
 *
 * @author narmdr
 */
function saveLoadReceiveOutstation(){
	if(!validateMandatoryFields()){
		return;
	}	
	flag=confirm("Do you want to Save the Receive - Outstation Load Details");
	if(!flag){
		return;
	}
	enableAll();
	showProcessing();
	var url = "./loadReceiveOutstation.do?submitName=saveOrUpdateLoadReceiveOutstation";
	submitForm(url, "loadReceiveOutstationForm");
}

/**
 * cancelPage
 *
 * @author narmdr
 */
function cancelPage(){
	url = "./loadReceiveOutstation.do?submitName=viewLoadReceiveOutstation";
	window.location = url;
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields(){
	/*var pleaseEnterMessage = pleaseEnterMsg + " ";
	var errorEndMsg = "." ;

	var receiveNumberObj = document.getElementById("receiveNumber");*/	
	
	/*if(isNull(receiveNumberObj.value)){
		receiveNumberObj.focus();
		alert(pleaseEnterMessage + receiveNumberLabel + errorEndMsg);
		return false;
	}*/	

	var table = document.getElementById("loadReceiveOutstationGrid");
	
	//Validating table data	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		
		if(i==(table.rows.length - 1) && table.rows.length>2){
			continue;
		}
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
	}	
	
	return true;
}

/**
 * validateReceiveNo
 *
 * @author narmdr
 */
function validateReceiveNo(){
	var receiveNumberObj = document.getElementById("receiveNumber");	
	receiveNumberObj.value = $.trim(receiveNumberObj.value);
	
	if(!isValidReceiveNo(receiveNumberObj)){
		receiveNumberObj.focus();
		return;
	}
	var url = "./loadReceiveOutstation.do?submitName=isReceiveNumberExist&receiveNumber="+receiveNumberObj.value;

	$.ajax({
		url: url,
		success: function(data){isReceiveNumberExist(data, receiveNumberObj);}
	});
}

/**
 * isReceiveNumberExist
 *
 * @param data
 * @param receiveNumberObj
 * @author narmdr
 */
function isReceiveNumberExist(data, receiveNumberObj){
	var loadReceiveValidationTO = eval('(' + data + ')');
	if(!isNull(loadReceiveValidationTO.errorMsg)){
		receiveNumberObj.value = "";
		receiveNumberObj.focus();
		alert(loadReceiveValidationTO.errorMsg);
	}	
}

/**
 * receiveNoFocus
 *
 * @author narmdr
 */
function receiveNoFocus(){
	var receiveNumberObj = document.getElementById("receiveNumber");	
	if(isNull(receiveNumberObj.value)){
		receiveNumberObj.focus();
	}else{
		document.getElementById("loadNumber1").focus();
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
	if(isNull(loadNumberObj.value)){
		loadNumberObj.focus();
	}
}

/**
 * vendorFocus
 *
 * @param rowIdd
 * @author narmdr
 */
function vendorFocus(rowIdd){
	var vendorObj = document.getElementById("recvVendor" + rowIdd);	
	if(isNull(vendorObj.value)){
		vendorObj.focus();
	}
}

/*function gatePassNumberFocus(rowIdd){
	var gatePassNumberObj = document.getElementById("recvGatePassNumber" + rowIdd);	
	if(isNull(gatePassNumberObj.value)){
		gatePassNumberObj.focus();
	}
}*/

function isValidTransportNumber(transportNumberObj) {
	/*flight no: alpha numeric - 10 as per DB
	train no: numeric 5
	vehicle no: alpha numeric  50 as per DB*/
	
	var alphaNumeric = /^[A-Za-z0-9\s]+$/;
	
	transportNumberObj.value = $.trim(transportNumberObj.value);
		
	if(isNull(transportNumberObj.value)){
		focusAlertMsg4TxtBox(transportNumberObj, "Transport Number");
		return false;
	}

	if (transportNumberObj.value.length>30) {
		clearFocusAlertMsg(transportNumberObj, "Transport Number should contain max 30 characters.");
		return false;
	}
	
	if (!transportNumberObj.value.match(alphaNumeric)) {
		clearFocusAlertMsg(transportNumberObj, "Transport Number Format is not correct.");
		return false;
	}

	return true;
}
