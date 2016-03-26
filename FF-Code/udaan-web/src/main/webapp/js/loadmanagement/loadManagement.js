//This file contains all the common methods for dispatch & receive

var dispatchModuleName = "dispatch";
var receiveLocalModuleName = "receiveLocal";
var receiveOutModuleName = "receiveOut";

/**
 * isValidGatepassNo
 *
 * @param gatepassNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidGatepassNo(gatepassNoObj) {
	//G+BranchCode(4 alphanumeric)+6digits :: RMUMB1234567
	gatepassNoObj.value = $.trim(gatepassNoObj.value);
	gatepassNoObj.value = gatepassNoObj.value.toUpperCase();

	var gatepassNo = gatepassNoObj.value;
	var numpattern = /^[0-9]+$/;
	//var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	var gatepassNoSubStr;
	
	if (isNull(gatepassNo)){
		return false;
	}

	if (gatepassNo.length!=12) {
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. length must be of 12 characters!");
		return false;
	}
	
	gatepassNoSubStr = gatepassNo.substring(0, 1);
	if(gatepassNoSubStr!="G"){
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. must Starts with G!");
		return false;
	}
	
	gatepassNoSubStr = gatepassNo.substring(1, 5);
	if (!gatepassNoSubStr.match(alphaNumeric)){
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. Format is not correct!");
		return false;
	}

	gatepassNoSubStr = gatepassNo.substring(5);
	if (!numpattern.test(gatepassNoSubStr)){
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. last 7 characters must be digit!");
		return false;
	}
	
	return true;
}

/**
 * isValidManifestNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidManifestNo(manifestNoObj) {	
	//Region Code+M+6digits :: BOYM123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	
	manifestNoObj.value = $.trim(manifestNoObj.value);
	manifestNoObj.value = manifestNoObj.value.toUpperCase();
	
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL/MBPL No. should contain 10 characters only!");
		return false;
	}
	
	if (!manifestNoObj.value.substring(0, 3).match(alphaNumeric) || 
			!manifestNoObj.value.substring(3, 4).match(letters) || 
			!numpattern.test(manifestNoObj.value.substring(4)) ||
			(manifestNoObj.value.substring(3, 4)!="M"
				&& manifestNoObj.value.substring(3, 4)!="B")) {
		clearFocusAlertMsg(manifestNoObj, "BPL/MBPL No. Format is not correct!");
		return false;			
	}

	return true;
}

/**
 * isValidReceiveNo
 *
 * @param receiveNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidReceiveNo(receiveNoObj) {
	//R+BranchCode(4 alphanumeric)+6digits :: RMUMB1234567
	receiveNoObj.value = $.trim(receiveNoObj.value);
	receiveNoObj.value = receiveNoObj.value.toUpperCase();
	
	var receiveNo = receiveNoObj.value;
	var numpattern = /^[0-9]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	var receiveNoSubStr;
	
	if (isNull(receiveNo)){
		return false;
	}

	if (receiveNo.length!=12) {
		clearFocusAlertMsg(receiveNoObj, "Receive No. length must be of 12 characters!");
		return false;
	}
	
	receiveNoSubStr = receiveNo.substring(0, 1);
	if(receiveNoSubStr!="R"){
		clearFocusAlertMsg(receiveNoObj, "Receive No. must Starts with R!");
		return false;
	}
	
	receiveNoSubStr = receiveNo.substring(1, 5);
	if (!receiveNoSubStr.match(alphaNumeric)){
		clearFocusAlertMsg(receiveNoObj, "Receive No. Format is not correct!");
		return false;
	}

	receiveNoSubStr = receiveNo.substring(5);
	if (!numpattern.test(receiveNoSubStr)){
		clearFocusAlertMsg(receiveNoObj, "Receive No. last 7 characters must be digit!");
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
 * isValidHHMMFormat HH:MM
 *
 * @param obj
 * @author narmdr
 */
function isValidHHMMFormat(obj){
	var format= new RegExp("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");	
	if(obj==null){
		return;
	}	
	if(!obj.value.match(format)){
		obj.value="";
		obj.focus();
		alert("Please Enter Time in HH:MM Format.");
	}
}

/**
 * isValidVehicleNumber
 * 
 * @param vehicleNumberObj
 * @returns {Boolean}
 * 
 * @author narmdr
 */
function isValidVehicleNumber(vehicleNumberObj) {
	/*flight no: alpha numeric - 10 as per DB
	train no: numeric 5
	vehicle no: alpha numeric  50 as per DB*/
	
	var alphaNumeric = /^[A-Za-z0-9\s]+$/;
	
	vehicleNumberObj.value = $.trim(vehicleNumberObj.value);
		
	if(isNull(vehicleNumberObj.value)){
		focusAlertMsg4TxtBox(vehicleNumberObj, "Vehicle Number");
		return false;
	}

	if (vehicleNumberObj.value.length>30) {
		clearFocusAlertMsg(vehicleNumberObj, "Vehicle Number should contain max 30 characters!");
		return false;
	}
	
	if (!vehicleNumberObj.value.match(alphaNumeric)) {
		clearFocusAlertMsg(vehicleNumberObj, "Vehicle Number Format is not correct!");
		return false;
	}

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

/**
 * validateWeightTolerance if more than 10%
 *
 * @param weightObj
 * @author narmdr
 */
function validateWeightTolerance(weightObj){
	var rowId = getRowId(weightObj, "weight");
	var manifestId = getValueByElementId("manifestId" + rowId);

	if(isNull(manifestId)){
		return;
	}
	
	var weight = getValueByElementId("weight" + rowId);
	if(isNANCheck(weight)){
		weight = 0.0;
	}
	weight = parseFloat(weight);
	
	var manifestWeight = parseFloat(getValueByElementId("manifestWeight" + rowId));
	
	var toleranceWeight = manifestWeight + manifestWeight*(10/100);
	
	if(weight>toleranceWeight){
		document.getElementById("weightTolerance" + rowId).value = "Y";
	}else{
		document.getElementById("weightTolerance" + rowId).value = "N";
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
		var isRowSelected = false;
		
		for(var i=0; i<rowCount; i++) {    	
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if(null != chkbox && true == chkbox.checked) {
				if(!isRowSelected){
					isRowSelected = true;
				}
				
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

		if(!isRowSelected){
			alert("Please select at least one BPL/MBPL to delete.");
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

/*select dropdown by By First Split Value (~) 
for eg. selectValue=1, ddl value format is cityId~code~name (1~BLR~Bangalore)*/
/**
 * selectOptionByFirstSplitValue
 * select dropdown by By First Split Value (~) 
 * for eg. selectValue=1, ddl value format is cityId~code~name (1~BLR~Bangalore)
 *
 * @param selectObj
 * @param selectValue
 * @author narmdr
 */
function selectOptionByFirstSplitValue(selectObj, selectValue){
	for(var i=0; i<selectObj.options.length; i++){
		if(selectObj.options[i].value.split(tild)[0] == selectValue){
			selectObj.selectedIndex = i;
			break;
		}		
	}
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
 * changeLabelValue
 *
 * @param labelId
 * @param labelValue
 * @author narmdr
 */
function changeLabelValue(labelId, labelValue){
	document.getElementById(labelId).innerHTML = labelValue;
}

/**
 * hideStar
 *
 * @param starId
 * @author narmdr
 */
function hideStar(starId){
	hideFieldById(starId);
}
/**
 * showStar
 *
 * @param starId
 * @author narmdr
 */
function showStar(starId){
	showFieldById(starId);
}
/**
 * hideFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function hideFieldById(fieldId){
	document.getElementById(fieldId).style.visibility = "hidden";
}
/**
 * showFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function showFieldById(fieldId){
	document.getElementById(fieldId).style.visibility = "visible";
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
 * submitForm
 *
 * @param url
 * @param formId
 * @author narmdr
 */
function submitForm(url, formId){
	document.forms[formId].action=url;
	document.forms[formId].submit();
}

//common starts
/**
 * inputDisable
 *
 * @author narmdr
 */
function inputDisable(){
	jQuery(":input").attr("readonly", true);	
}
/**
 * inputEnable
 *
 * @author narmdr
 */
function inputEnable(){
	jQuery(":input").attr("readonly", false);	
	jQuery(":input").attr("disabled", false);	
}
/**
 * dropdownDisable
 *
 * @author narmdr
 */
function dropdownDisable(){
	jQuery("select").attr("disabled", 'disabled');	
}
/**
 * dropdownEnable
 *
 * @author narmdr
 */
function dropdownEnable(){
	jQuery("select").attr("disabled", false);
	
}
/**
 * buttonDisable
 *
 * @author narmdr
 */
function buttonDisable(){
	jQuery(":button").attr("disabled", true);
	
}
/**
 * buttonEnable
 *
 * @author narmdr
 */
function buttonEnable(){
	jQuery(":button").attr("disabled", false);
	
}
/**
 * disableAll
 *
 * @author narmdr
 */
function disableAll(){
	buttonDisable();
	inputDisable();
	dropdownDisable();
}
/**
 * enableAll
 *
 * @author narmdr
 */
function enableAll(){
	buttonEnable();
	inputEnable();
	dropdownEnable();
}
/**
 * buttonDisabled
 *
 * @param btnName
 * @param styleClassToAdd
 * @author narmdr
 */
function buttonDisabled(btnName, styleClassToAdd) {	
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).css("background-color", "grey");
	//disableFieldById(btnName);
	//jQuery("#" + btnName).removeClass(buttonEnableCss);
	//jQuery("#" + btnName).addClass(styleClassToAdd);
	//$(element).removeClass(classesToRemove).addClass(classesToAdd);
}

/**
 * buttonDisabled
 *
 * @param btnName
 * @author narmdr
 */
/*function buttonDisabled(fieldId) {	
	disableFieldById(fieldId);
	jQuery("#" + fieldId).attr("disabled", true);
	jQuery("#" + fieldId).css("background-color", "grey");
}*/
/**
 * buttonEnabled
 *
 * @param btnName
 * @param styleClassToRemove
 * @author narmdr
 */
function buttonEnabled(btnName, styleClassToRemove) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleClassToRemove);

	jQuery("#" + btnName).css("background-color", "#f78937");
}
/*function fieldEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).addClass(styleclass);
	jQuery(":input").attr("readonly", false);
}*/
/**
 * getTableLength
 *
 * @param tableId
 * @returns
 * @author narmdr
 */
function getTableLength(tableId){
	var tableee = getDomElementById(tableId);
	return  tableee.rows.length;
}
/**
 * getTableRows
 *
 * @param tableId
 * @returns
 * @author narmdr
 */
function getTableRows(tableId){
	var tableee = getDomElementById(tableId);
	return tableee.getElementsByTagName("tr");
}
//common ends


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

//moved to common.js
/**
 * checkUncheckAllRows
 *
 * @param tableId
 * @author narmdr
 */
//function checkUncheckAllRows(tableId){
//	var table = document.getElementById(tableId);
//	var checkFlag = document.getElementById("chk0").checked;
//	
//	for (var i=1;i<table.rows.length;i++) {
//		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
//		document.getElementById("chk" + rowId).checked = checkFlag;
//	}
//}

function isMasterBag(manifestNumber){
	var manifestChar = manifestNumber.charAt(3);
	//var manifestChar1 = manifestNumber[3];
	if(manifestChar=="M" || manifestChar=="m"){
		return true;
	}
	return false;
}
function prepareMasterBagList(manifestNumber, tableId){
	var table = document.getElementById(tableId);
	//var masterBagList = null;
	var masterBagList = new Array();
	if(isMasterBag(manifestNumber)){
		return masterBagList;
	}
	
	//Iterating table data
	var j=0;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var loadNumber = $("#loadNumber" + rowId).val();		

		if(isNull(loadNumber)){
			continue;
		}
		if(isMasterBag(loadNumber)){
			masterBagList[j++] = loadNumber;
		}
		/*if(isNull(masterBagList)){
			masterBagList = loadNumber;
		}else{
			return masterBagList;
		}*/
	}
	return masterBagList;
}


/**
 * isValidAwbCdRrNumber
 *
 * @param transactionNumberObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidAwbCdRrNumber(transactionNumberObj) {
	//Length <=10 and allows alphanumeric
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	
	transactionNumberObj.value = $.trim(transactionNumberObj.value);
		
	if(isNull(transactionNumberObj.value)){
		focusAlertMsg4TxtBox(transactionNumberObj, "AWB/CD/RR Number");
		return false;
	}

	if (transactionNumberObj.value.length>10) {
		clearFocusAlertMsg(transactionNumberObj, "AWB/CD/RR Number should contain max 10 characters!");
		return false;
	}
	
	if (!transactionNumberObj.value.match(alphaNumeric)) {
		clearFocusAlertMsg(transactionNumberObj, "AWB/CD/RR Number Format is not correct!");
		return false;
	}

	return true;
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
//		//$("#" + elementIdToFocus).focus();
//		//document.getElementById(elementIdToFocus).focus();
//		focusById(elementIdToFocus);
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
/****************Weighing Machine Integration Start************************/
/*var weightInkgs=0;
var weightInGms=0;*/
/** The wmWeight */
var wmWeight = 0.00;
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
		wmWeight = capturedWeight;
		isWeighingMachineConnected = false;		
	}else {
		wmWeight = parseFloat(capturedWeight).toFixed(3);
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
	if(!isNull(getValueByElementId("loadConnectedId" + rowIdd))){
		return;
	}
	if(isWeighingMachineConnected){
		getWeightFromWeighingMachineWithParam(capturedWeightWithParam, rowIdd);
	}
	disableEnableWeightField(rowIdd, isWeighingMachineConnected);
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
	if (isNull(capturedWeight) || capturedWeight == -1 || capturedWeight == -2) {
		wmWeight = capturedWeight;
		flag = false;
	} else {
		wmWeight = parseFloat(capturedWeight).toFixed(3);
		document.getElementById("weightKg" + rowIdd).value = wmWeight.split(".")[0];
		document.getElementById("weightGm" + rowIdd).value = wmWeight.split(".")[1];
		document.getElementById("weight" + rowIdd).value = wmWeight;
		flag = true;
		//setTotalWeight();
		validateWeight(rowIdd);
		
		if(moduleName === dispatchModuleName){
			 addRow(rowIdd);
		}
		if(moduleName === receiveLocalModuleName){
			addRow(rowIdd);
		}
		if(moduleName === receiveOutModuleName){
			$("#lockNumber" + rowIdd).focus();
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
//CR : Weight should be taken from weighing machine at hub
function checkLoginOfficeTypeAndAllowWeighingMachine(){
	var officetype = document.getElementById("originOfficeType").value;
	if(officetype ==hubCode){
		getWeightFromWeighingMachine();
	}
}