var selectOption = "--Select--";

//msg
/** The pleaseSelectMsg */
var pleaseSelectMsg = "Please Select ";
/** The pleaseEnterMsg */
var pleaseEnterMsg = "Please Enter ";
/** The errorEndMsg */
var errorEndMsg = "." ;
/** The invalidMsg */
var invalidMsg = "Invalid ";

//css
/** The buttonDisableCss */
var buttonDisableCss = "button_disabled";
/** The buttonEnableCss */
var buttonEnableCss = "btnintform";

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
 * clearFocusAlertMsg
 *
 * @param obj
 * @param msg
 * @author narmdr
 */
function clearFocusAlertMsg(obj, msg){
	obj.val("");
	obj.focus();
	alert(msg);
}

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
}
/**
 * enableFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function enableFieldById(fieldId){
	if(isButton(fieldId)){
		buttonEnableById(fieldId);
	}else{
		document.getElementById(fieldId).disabled = false;
	}
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
	
	consgNoObj.val($.trim(consgNoObj.val()));
	consgNoObj.val(consgNoObj.val().toUpperCase());

	if (isNull(consgNoObj.val())) {
		focusAlertMsg4TxtBox(consgNoObj, "Consignment Number");
		return false;
	}

	if (consgNoObj.val().length!=12) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. should contain 12 characters only!");
		return false;
	}
	
	if (!consgNoObj.val().substring(0, 5).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.val().substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}
	
	/*if (!consgNoObj.val().substring(0, 1).match(letters)
			|| !consgNoObj.val().substring(4, 5).match(letters)
			|| !consgNoObj.val().substring(1, 4).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.val().substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}*/

	return true;
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
 * populateWeightInKgGmField
 *
 * @param weight
 * @author narmdr
 */
function populateWeightInKgGmField(weight){
	weight += "";
	var weightKgObj = document.getElementById("weightKg");
	var weightGmObj = document.getElementById("weightGm");
	
	if(!isNull(weight)){
		weightKgObj.value = weight.split(".")[0];
		weightGmObj.value = weight.split(".")[1];
	}
	fixWeightFormatForGram(weightGmObj);
}


function getCurrentDateTime(rowId){
	var url = "./rthRtoValidation.do?submitName=getCurrentDateTime";
	//showProcessing();
	$.ajax({
		url: url,
		success: function(data){populatetCurrentDateTime(data, rowId);}
	});
}

function populatetCurrentDateTime(currentDateTime, rowId){
	//hideProcessing();
	$("#dateStr" + rowId).val(currentDateTime.split(" ")[0]);
	$("#time" + rowId).val(currentDateTime.split(" ")[1]);

	/*var contactNumberObj = $("#contactNumber");
	if(isNull(contactNumberObj.val())){
		contactNumberObj.focus();
	}else{
		$("#reasonId" + rowId).focus();
	}*/

	$("#reasonId" + rowId).focus();
	buttonEnableById("btnSave");
}

function disableEnableRowField(rowIdd, flag){
	document.getElementById("reasonId" + rowIdd).disabled = flag;
	//document.getElementById("remarks" + rowIdd).disabled = flag;
	document.getElementById("remarks" + rowIdd).readOnly = flag;	
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

function validateDuplicateReasons(reasonObj){
	if(isNull(reasonObj.value)){
		return;
	}
	if(isDuplicateFieldInGrid(reasonObj, "reasonId", tableId)){
		alert("Duplicate Pending Reason!");
		reasonObj.value = "";
		reasonObj.focus();
	}
}

/**
 * validateContactNumber
 *
 * @author narmdr
 */
function validateContactNumber() {
	var contactNumberObj = $("#contactNumber");
	if (contactNumberObj.val().length != 10) {
		clearFocusAlertMsg(contactNumberObj, "Contact Number must be of 10 digit!");
	}
}

function isConsgNoEntered() {
	if(isNull($("#consignmentId").val())){
		focusAlertMsg4TxtBox($("#consignmentNumber"), "Consignment Number and click on search button first.");
		return false;
	}
	return true;
}

/**********************Enter Key Navigation Start*********************************************/

/**
 * isEnterKey
 *
 * @param evt
 * @returns {Boolean}
 * @author narmdr
 */
function isEnterKey(evt){
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
/*function enterKeyNavigation(evt, elementId){	
	if(isEnterKey(evt)){
		$("#" + elementId).click();
		return true;
	}
	return false;
}
*/
/**
 * enterKeyNavigationFocus
 *
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author narmdr
 */
/*function enterKeyNavigationFocus(evt, elementIdToFocus){
	if(isEnterKey(evt)){
		$("#" + elementIdToFocus).focus();
		return true;
	}
	return false;
}
*/
/**
 * enterKeyNavigationFocus4Grid
 *
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author narmdr
 */
/*function enterKeyNavigationFocus4Grid(evt, elementIdToFocus, rowId){
	if(isEnterKey(evt)){
		$("#" + elementIdToFocus + rowId).focus();
		return true;
	}
	return false;
}*/
function onlyNumberAndEnterKeyNavFocus(evt, elementIdToFocus){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		$("#" + elementIdToFocus).focus();
		return false;
	}else {
		return true;
	}
	return false;
}
/**********************Enter Key Navigation End*********************************************/
