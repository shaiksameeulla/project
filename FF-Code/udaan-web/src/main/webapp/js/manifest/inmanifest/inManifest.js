/** Remark global variable */
var REMARKS_OPTION = "";
var MAX_CN_ALLOWED_FOR_IN_DOX=75;

//msg
/** The pleaseSelectMsg */
var pleaseSelectMsg = "Please Select ";
/** The pleaseEnterMsg */
var pleaseEnterMsg = "Please Enter ";
/** The errorEndMsg */
var errorEndMsg = " !" ;
/** The invalidMsg */
var invalidMsg = "Invalid ";


/**
 * updateSerialNoVal
 *
 * @param tableId
 * @author narmdr
 */
function updateSerialNoVal(tableId){
	try{
		var table = document.getElementById(tableId);

		for (var i=1;i<table.rows.length;i++) {
			var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
			var serialNo = document.getElementById("serialNo" + rowId);
			if(serialNo.innerHTML!=i){
				serialNo.innerHTML = i;
			}
		}
	}catch(e) {
		
	}
}

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
//	if (window.event) {
//		charCode = window.event.keyCode; // IE
//	} else {
//		charCode = evt.which; // firefox
//	}
//	
//	if(charCode==13){
//		$("#" + elementIdToFocus).focus();
//		//document.getElementById(elementIdToFocus).focus();
//		return true;
//	}
//	return false;
//}

/**
 * enterKeyNavigationFocus4Grid
 *
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigationFocus4Grid(evt, elementIdToFocus, rowId){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	
	if(charCode==13){
		$("#" + elementIdToFocus + rowId).focus();
		//document.getElementById(elementIdToFocus).focus();
		return true;
	}
	return false;
}


/**
 * onlyNumberAndEnterKeyNavFocus4Grid
 *
 * @param evt
 * @param elementIdToFocus
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function onlyNumberAndEnterKeyNavFocus4Grid(evt, elementIdToFocus, rowId){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if(charCode == 13) {
		$("#" + elementIdToFocus + rowId).focus();
		return false;
	}else {
		return true;
	}
	return false;
}

function onlyDecimalAndEnterKeyNavFocus4Grid(evt, elementIdToFocus, rowId){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else if(charCode == 13) {
		$("#" + elementIdToFocus + rowId).focus();
		return false;
	}else {
		return true;
	}
	return false;
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
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * disableFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function disableFieldById(fieldId){
	document.getElementById(fieldId).disabled = true;
}
/**
 * enableFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function enableFieldById(fieldId){
	document.getElementById(fieldId).disabled = false;
}

/**
 * fixFormatUptoTwoDecimalPlace
 *
 * @param obj
 * @author narmdr
 */
function fixFormatUptoTwoDecimalPlace(obj){
	if(obj.value != "" && obj.value != null){
		obj.value=parseFloat(obj.value).toFixed(2);
	}else {
		obj.value="";
		obj.focus();
		return;
	}
}
function getMaxRowsForInManifest(){
	return getDomElementById("maxAllowedRows").value;
}
/**
 * setMaxRowsForDrs  for Each screen from config params table
 */
function setMaxRowsForInManifest(){
	var configRows=getMaxRowsForInManifest();
	if(!isNull(configRows)){
		MAX_CN_ALLOWED_FOR_IN_DOX=parseIntNumber(configRows);
	}
}

function isValidConsgNoFormat(consgNoObj) {
	// Branch Code+Product Name+7digits :: BOYAB1234567
	// 1st & 5th Char are alphaNumeric
	// 2nd to 4th Char are alphaNumeric
	// last 7 char are numeric only.

	var numpattern = /^[0-9]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;

	consgNoObj.value = $.trim(consgNoObj.value);
	consgNoObj.value = consgNoObj.value.toUpperCase();

	if (isNull(consgNoObj.value)) {
		return false;
	}
	if (consgNoObj.value.length != 12) {
		return false;
	}
	if (!consgNoObj.value.substring(0, 5).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		return false;
	}
	return true;
}
function validateConsignmentBranchCode(consgNoObj){
	// cnNo = branch Code(4 digits) + product series + 7 digit numeric number
	var flag = false;
	if(isValidConsgNoFormat(consgNoObj)){
		var branchCodeFromCnNo = consgNoObj.value.substring(0, 4);
		var url = "./inOGMDoxManifest.do?submitName=validateBranchCode&branchCode="+branchCodeFromCnNo;
		showProcessing();
		jQuery.ajax({
			url : url,
			async : false,
			success : function(req) {
				flag = validateBranchCodeResponse(consgNoObj,req);
			}
		});
	}
	return flag;
}
function validateBranchCodeResponse(consgNoObj,data){
	hideProcessing();
	if (!isNull(data)) {
		var result = jsonJqueryParser(data);
		var success = result["SUCCESS"];
		if (!isNull(success) && success=="Y") {
			return true;			
		}else{
			clearFocusAlertMsg(consgNoObj, "Invalid Number");
			return false;
		}			
	}else{
		clearFocusAlertMsg(consgNoObj, "Invalid Number");
		return false;
	}
}