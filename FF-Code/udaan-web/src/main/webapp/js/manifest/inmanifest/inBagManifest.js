/**
 * InBagManifest.js is common js for In BPL Dox and Parcel.
 * @author narmdr
 */

//labels
/** The selectOption */
var selectOption = "--Select--";

//msg
/** The pleaseSelectMsg */
var pleaseSelectMsg = "Please Select ";
/** The pleaseEnterMsg */
var pleaseEnterMsg = "Please Enter ";
/** The errorEndMsg */
var errorEndMsg = " !" ;
/** The invalidMsg */
var invalidMsg = "Invalid ";

//css
/** The buttonDisableCss */
var buttonDisableCss = "button_disabled";
/** The buttonEnableCss */
var buttonEnableCss = "btnintform";

//codes
/** The receivedCode */
var receivedCode="R";
/** The notReceivedCode */
var notReceivedCode="N";

var isViewScreen = false;

/**
 * getOriginCitiesByRegion
 *
 * @author narmdr
 */
function getOriginCitiesByRegion() {
	var originRegionId = "";
	originRegionId = document.getElementById("originRegion").value;

	clearDropDownList("originCity");
	clearOriginOffice();
	
	if(isNull(originRegionId)){
		return;
	}
	
	url = './inBagManifest.do?submitName=getCitiesByRegion&regionId='+ originRegionId;
	//ajaxCallWithoutForm(url, populateOriginCities);
	$.ajax({
		url: url,
		async: false,
		success: function(data){populateOriginCities(data);}
	});
}

/**
 * populateOriginCities
 *
 * @param data
 * @author narmdr
 */
function populateOriginCities(data) {
	var originCityTOArray = eval('(' + data + ')');	
	var errorMsg = getErrorMessage(originCityTOArray);

	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	
	clearDropDownList("originCity");
	for(var i=0;i<originCityTOArray.length;i++) {
		addOptionTODropDown("originCity", originCityTOArray[i].cityName, 
				originCityTOArray[i].cityId);
	}
}

/**
 * clearOriginOffice
 *
 * @author narmdr
 */
function clearOriginOffice() {
	clearDropDownList("originOffice");
	$("#originOfficeType").val("");
}

/**
 * getOriginOfficesByCityAndOfficeType
 *
 * @author narmdr
 */
function getOriginOfficesByCityAndOfficeType(){	
	var originCityId = document.getElementById("originCity").value;	
	var originOfficeTypeId =  document.getElementById("originOfficeType").value;	
	url = './inBagManifest.do?submitName=getAllOfficesByCityAndOfficeType&cityId=' + originCityId + "&officeTypeId=" + originOfficeTypeId;		
	ajaxCallWithoutForm(url, populateOriginOffices);	
}

/**
 * populateOriginOffices
 *
 * @param data
 * @author narmdr
 */
function populateOriginOffices(originOfficeTOArray) {
	var errorMsg = getErrorMessage(originOfficeTOArray);

	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	clearDropDownList("originOffice");
	for ( var i = 0; i < originOfficeTOArray.length; i++) {
		addOptionTODropDown("originOffice", originOfficeTOArray[i].officeCode
				+ "-" + originOfficeTOArray[i].officeName,
				originOfficeTOArray[i].officeId);
	}
}

/**
 * fixFormatUptoThreeDecimalPlace
 *
 * @param obj
 * @author narmdr
 */
function fixFormatUptoThreeDecimalPlace(obj){
	if(obj.value != "" && obj.value != null){
		obj.value=parseFloat(obj.value).toFixed(3);
	}else {
		obj.value="";
		obj.focus();
		return;
	}
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
 * fixWeightFormatForGram
 *
 * @param weightGmObj
 * @author narmdr
 */
function fixWeightFormatForGram(weightGmObj){
	if(isNull(weightGmObj.value) || weightGmObj.value=="undefined"){
		weightGmObj.value="000";
	}else if(weightGmObj.value.length==1){
		weightGmObj.value += "00";
	}else if(weightGmObj.value.length==2){
		weightGmObj.value += "0";
	}
}

/**
 * isValidBplNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidBplNo(manifestNoObj) {	
	//Region Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();
	
	if (isNull(manifestNoObj.value)) {
		focusAlertMsg4TxtBox(manifestNoObj, "BPL Number");
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric) 
			|| !manifestNoObj.value.substring(3, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * isValidPacketNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidPacketNo(manifestNoObj) {	
	//City Code+7 digits :: BOY1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();
	
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric)
			|| !numpattern.test(manifestNoObj.value.substring(3))) {
		clearFocusAlertMsg(manifestNoObj,
				"OGM/Open Manifest No. Format is not correct!");
		return false;
	}
	return true;
}


/**
 * isValidConsgNo
 *
 * @param consgNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidConsgNo(consgNoObj) {
	//Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	//last 7 char are numeric only.
	
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;	
	
	consgNoObj.value = $.trim(consgNoObj.value);
	consgNoObj.value = consgNoObj.value.toUpperCase();
	
	if (isNull(consgNoObj.value)) {
		return false;
	}

	if (consgNoObj.value.length!=12) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12 characters only!");
		return false;
	}
	
	if (!consgNoObj.value.substring(0, 5).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}
	
/*	if (!consgNoObj.value.substring(0, 1).match(letters)
			|| !consgNoObj.value.substring(4, 5).match(letters)
			|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}
*/
	return true;
}

/**
 * isValidLockNo
 *
 * @param lockNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidLockNo(lockNoObj) {
	//Region Code+7digits :: R1234567
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	lockNoObj.value = $.trim(lockNoObj.value);
	lockNoObj.value = lockNoObj.value.toUpperCase();
		
	if (isNull(lockNoObj.value)) {
		return false;
	}

	if (lockNoObj.value.length!=8) {
		clearFocusAlertMsg(lockNoObj, "Lock No. should contain 8 characters only!");
		return false;
	}
		
	if (!lockNoObj.value.substring(0, 1).match(letters)
			|| !numpattern.test(lockNoObj.value.substring(1))) {
		clearFocusAlertMsg(lockNoObj, "Lock No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * clearFocusAlertMsg
 *
 * @param obj
 * @param msg
 * @author narmdr
 */
/*function clearFocusAlertMsg(obj, msg){
	obj.value = "";
	obj.focus();
	alert(msg);
	setTimeout(function() {
		obj.focus();
	}, 10);
}*/

/**
 * focusAlertMsg4TxtBox
 *
 * @param obj
 * @param fieldName
 * @author narmdr
 */
function focusAlertMsg4TxtBox(obj, fieldName){
	obj.focus();
	alert(pleaseEnterMsg + fieldName + errorEndMsg);
	setTimeout(function() {
		obj.focus();
	}, 10);
}
/**
 * focusAlertMsg4Select
 *
 * @param obj
 * @param fieldName
 * @author narmdr
 */
function focusAlertMsg4Select(obj, fieldName){
	obj.focus();
	alert(pleaseSelectMsg + fieldName + errorEndMsg);
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * isDuplicateFieldInGrid
 *
 * @param fieldNameToValidateObj
 * @param fieldNameToValidate
 * @param tableId
 * @returns {Boolean}
 * @author narmdr
 */
function isDuplicateFieldInGrid(fieldNameToValidateObj, fieldNameToValidate, tableId){
	var table = document.getElementById(tableId);
	var selectedRowId  = getRowId(fieldNameToValidateObj , fieldNameToValidate);
	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var fieldValue = document.getElementById(fieldNameToValidate + rowId).value;
		if(fieldNameToValidateObj.value==fieldValue && rowId!=selectedRowId){
			return true;
		}
	}
	return false;
}

/**
 * clearDropDownList
 *
 * @param selectId
 * @author narmdr
 */
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;	
	addOptionTODropDown(selectId, selectOption, "");
}

/**
 * addOptionTODropDown
 *
 * @param selectId
 * @param label
 * @param value
 * @author narmdr
 */
function addOptionTODropDown(selectId, label, value){
	var myselect=document.getElementById(selectId);
	try{
		 myselect.add(new Option(label, value), null); //add new option to end of "myselect"
		 //myselect.add(new Option(label, value), myselect.options[0]); //add new option to beginning of "myselect"
	}
	catch(e){ //in IE, try the below version instead of add()
		 myselect.add(new Option(label, value)); //add new option to end of "myselect"
		 //myselect.add(new Option(label, value), 0); //add new option to beginning of "myselect"
	}
}

/**
 * deleteTableRow
 *
 * @param tableId
 * @author narmdr
 */
function deleteTableRow(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for(var i=0; i<rowCount; i++) {    	
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
				if(rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId,i-1);
				//table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
	}catch(e) {
		alert(e);
	}
}
/**
 * deleteRow
 *
 * @param tableId
 * @param rowIndex
 * @author narmdr
 */
function deleteRow(tableId,rowIndex){ 
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex); 
}
/**
 * deleteAllRowOfTableExceptHeaderRow
 *
 * @param tableId
 * @author narmdr
 */
function deleteAllRowOfTableExceptHeaderRow(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for(var i=1; i<rowCount; i++) {  
			deleteRow(tableId,i-1);
			rowCount--;
			i--;			
		}
	}catch(e) {
		alert(e);
	}
}

/**
 * deleteInBplRow
 *
 * @param tableId
 * @author narmdr
 */
function deleteInBplRow(tableId){
	deleteTableRow(tableId);
	updateSerialNoVal(tableId);
	setTotalWeight();
}

/**
 * clearFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function clearFieldById(fieldId){
	document.getElementById(fieldId).value = "";
}
/**
 * clearAndDisableFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function clearAndDisableFieldById(fieldId){
	clearFieldById(fieldId);
	disableFieldById(fieldId);
}

//moved to common.js
/**
 * enterKeyNavigation
 *
 * @param evt
 * @param elementId
 * @returns {Boolean}
 * @author narmdr
 */
//function enterKeyNavigation(evt, elementId){	
//	if(evt==13){
//		document.getElementById(elementId).click();
//		return true;
//	}
//	return false;
//}

//moved to common.js
/**
 * enterKeyNavigationFocus
 *
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author narmdr
 */
//function enterKeyNavigationFocus(evt, elementIdToFocus){
//	var charCode;
//	if (window.event)
//		charCode = window.event.keyCode; // IE
//	else
//		charCode = evt.which; // firefox
//	
//	if(charCode==13){
//		document.getElementById(elementIdToFocus).focus();
//		return true;
//	}
//	return false;
//}

/**
 * onlyNumberAndEnterKeyNav
 *
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 * @author narmdr
 */
function onlyNumberAndEnterKeyNav(e,Obj,focusId){
	var charCode;	

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	}else {
		return true;
	}
}

/****************Weighing Machine Integration Start************************/

var inBplDoxModuleName = "inBplDox";
var inBplParcelModuleName = "inBplParcel";

/** The wmWeight */
var wmWeight = 0.00;
var totalWmWeight = 0.00;
/** The isWeighingMachineConnected */
var isWeighingMachineConnected = false;

/**
 * capturedWeight
 *
 * @param data
 * @author narmdr
 */
function capturedWeight(data) {
	var capturedWeight = eval('(' + data + ')');
	if (capturedWeight == -1 || capturedWeight == -2) {
		totalWmWeight = capturedWeight;
		isWeighingMachineConnected = false;		
	}else {
		totalWmWeight = parseFloat(capturedWeight).toFixed(3);
		isWeighingMachineConnected = true;
	}
}

/**
 * validateWeightFromWeighingMachine
 *
 * @param rowIdd
 * @author narmdr
 */
function validateWeightFromWeighingMachine(rowIdd){
	
	//if(isWeighingMachineConnected){
	// getWeightFromWeighingMachineWithParam(capturedWeightWithParam, rowIdd);
	capturedWeightWithParam(-1, rowIdd);
	//}
	if(!isViewScreen){
		disableEnableWeightField(rowIdd, isWeighingMachineConnected);
	}
}
/**
 * capturedWeightWithParam
 *
 * @param data
 * @param rowIdd
 * @author narmdr
 */
function capturedWeightWithParam(data, rowIdd) {
	var capturedWeight = eval('(' + data + ')');
	var flag = true;
	if (capturedWeight == -1 || capturedWeight == -2) {
		wmWeight = capturedWeight;
		flag = false;
	} else {
		wmWeight = parseFloat(capturedWeight).toFixed(3);
		var cnWeight = 0;
		cnWeight = totalWmWeight - wmWeight;
		totalWmWeight = wmWeight;
		cnWeight = parseFloat(cnWeight).toFixed(3);
		
		document.getElementById("weightKg" + rowIdd).value = cnWeight.split(".")[0];
		document.getElementById("weightGm" + rowIdd).value = cnWeight.split(".")[1];
		flag = true;
		
		if(moduleName === inBplParcelModuleName){
			document.getElementById("actualWeights" + rowIdd).value = cnWeight;
			setTotalWeight();
			setFinalWeight(rowIdd);
		}
		if(moduleName === inBplDoxModuleName){
			document.getElementById("manifestWeights" + rowIdd).value = cnWeight;
			setTotalWeight();
		}
	}
	disableEnableWeightField(rowIdd, flag);
}

/**
 * disableEnableWeightField
 *
 * @param rowIdd
 * @param flag
 * @author narmdr
 */
function disableEnableWeightField(rowIdd, flag){
	document.getElementById("weightKg" + rowIdd).readOnly = flag;
	document.getElementById("weightGm" + rowIdd).readOnly = flag;	
}

/****************Weighing Machine Integration End************************/