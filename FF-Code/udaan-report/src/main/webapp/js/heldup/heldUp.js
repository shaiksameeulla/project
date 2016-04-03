/** The selectOption */
var selectOption = "--Select--";

//msg
/** The pleaseSelectMsg */
var pleaseSelectMsg = "Please Select ";
/** The pleaseEnterMsg */
var pleaseEnterMsg = "Please Enter ";
/** The errorEndMsg */
var errorEndMsg = " !" ;

$(document).ready(function() {     
	$('#heldUpGrid').dataTable( {         
	"sScrollY": 70,         
	"sScrollX": "100%",         
	"sScrollXInner": "140%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
	"sPaginationType": "full_numbers"
	} );
	
	defaultChanges();
} ); 


/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	$("#transactionType").focus();
	
	//check method isFind
	if(!isNull($("#isFind").val())){
		$("#reasonName").val($("#reasonToSelect").val());
		$("#employeeId").val($("#employeeToSelect").val());
		disableAll();
		$("#btnClear").attr("disabled", false);
		$("#btnNew").attr("disabled", false);
	}
}

/**
 * validateTransactionNumber
 *
 * @param transactionNumberObj
 * @author narmdr
 */
function validateTransactionNumber(transactionNumberObj){
	var transactionTypeObj = $("#transactionType");
	
	if(isNull(transactionTypeObj.val())){
		focusAlertMsg4Select(transactionTypeObj, "Transaction Type");
		transactionNumberObj.value = "";
	}

	/*if(transactionTypeObj instanceof $) {
	    alert(' transactionTypeObj is a jQuery object! ');    
	}
	if(txTypeObj instanceof $) {
	    alert(' txTypeObj is a jQuery object! ');    
	}
	if ( txTypeObj.jquery ) { // falsy, since it's undefined
	    alert(' txTypeObj is a jQuery object! ');    
	}

	if ( transactionTypeObj.jquery ) { // truthy, since it's a string
	    alert(' transactionTypeObj is a jQuery object! ');   
	}
	return;*/
	
	if(transactionTypeObj.val()==$("#transactionTypeConsignment").val()){
		isValidConsgNo(transactionNumberObj);		
	}
	if(transactionTypeObj.val()==$("#transactionTypeOpenManifest").val() ||
			transactionTypeObj.val()==$("#transactionTypeOGM").val()){
		isValidPacketNo(transactionNumberObj);		
	}
	if(transactionTypeObj.val()==$("#transactionTypeBplDox").val() ||
			transactionTypeObj.val()==$("#transactionTypeBplParcel").val()){
		isValidBplNo(transactionNumberObj);		
	}
	if(transactionTypeObj.val()==$("#transactionTypeMbpl").val()){
		isValidMbplNo(transactionNumberObj);		
	}
	if(transactionTypeObj.val()==$("#transactionTypeMblDispatch").val()){
		isValidGatepassNo(transactionNumberObj);		
	}
	if(transactionTypeObj.val()==$("#transactionTypeAwbCdRr").val()){
		isValidAwbCdRrNumber(transactionNumberObj);		
	}
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryFields(){
	var transactionTypeObj = $("#transactionType");
	var transactionNumberObj = $("#transactionNumber");
	var reasonNameObj = $("#reasonName");
	//var reasonTypeDescObj = $("#reasonTypeDesc");

	if(isNull(transactionTypeObj.val())){
		focusAlertMsg4Select(transactionTypeObj, "Transaction Type");
		return false;
	}
	if(isNull(transactionNumberObj.val())){
		focusAlertMsg4TxtBox(transactionNumberObj, "Transaction Number");
		return false;
	}
	if(isNull(reasonNameObj.val())){
		focusAlertMsg4Select(reasonNameObj, "Reason Code");
		return false;
	}
	/*if(isNull(reasonTypeDescObj.val())){
		focusAlertMsg4TxtBox(reasonTypeDescObj, "Held Up Reason");
		return false;
	}*/
	
	return true;
}

/**
 * saveOrUpdateHeldUp
 *
 * @author narmdr
 */
function saveOrUpdateHeldUp(){
	if(!validateMandatoryFields()){
		return;
	}
	var flag=confirm("Do you want to Save the Held Up Details!");
	if(!flag){
		return;
	}
	//enableAll();
	progressBar();
	var url="./heldUp.do?submitName=saveOrUpdateHeldUp";
	document.heldUpForm.action=url;
	document.heldUpForm.submit();
}

/**
 * cancelHeldUp
 *
 * @author narmdr
 */
function cancelHeldUp(obj){
	var msg = "Do you want to ";;
	if(obj.value=="New"){
		msg += " Refresh the page!";
	}else{
		msg += obj.value + " the Held Up Details!";
	}
	var flag = confirm(msg);
	if(flag){
		cancelPage();
	}
}

/**
 * cancelPage
 *
 * @author narmdr
 */
function cancelPage(){
	url = "./heldUp.do?submitName=viewHeldUp";
	window.location = url;
}

/**
 * findHeldUpNumber
 *
 * @author narmdr
 */
function findHeldUpNumber(){
	var heldUpNoObj = document.getElementById("heldUpNumber");
	
	//validate HeldUpNumber format
	if(!isValidHeldUpNumber(heldUpNoObj)){
		return;
	}

	progressBar();
	url = "./heldUp.do?submitName=findHeldUpNumber";
	document.heldUpForm.action=url;
	document.heldUpForm.submit();
}

/**
 * setFocusIfNullById
 *
 * @param elementId
 * @author narmdr
 */
function setFocusIfNullById(elementId){
	var obj = $("#" + elementId);
	obj.val($.trim(obj.val()));
	
	if(isNull(obj.val())){
		obj.focus();
	}
}

/**
 * setReason
 *
 * @author narmdr
 */
function setReason(){
	var reasonNameArray = $("#reasonName").val().split("~");
	
	$("#reasonId").val(reasonNameArray[0]);
	$("#reasonTypeDesc").val(reasonNameArray[2]);
	
	if(isNull(reasonNameArray)){
		$("#reasonId").val("");
		$("#reasonTypeDesc").val("");
	}
}

/**
 * enterKeyNavigation
 *
 * @param evt
 * @param elementId
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigation(evt, elementId){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	
	if(charCode==13){
		//document.getElementById(elementId).click();
		$("#" + elementId).click();
		return true;
	}
	return false;
}

/**
 * enterKeyNavigationFocus
 *
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigationFocus(evt, elementIdToFocus){
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}
	
	if(charCode==13){
		$("#" + elementIdToFocus).focus();
		//document.getElementById(elementIdToFocus).focus();
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
 * clearFocusAlertMsg
 *
 * @param obj
 * @param msg
 * @author narmdr
 */
function clearFocusAlertMsg(obj, msg){
	obj.value = "";
	obj.focus();
	alert(msg);
}
/**
 * clearFieldById
 *
 * @param fieldId
 * @author narmdr
 */
function clearFieldById(fieldId){
	$("#"+fieldId).val("");
}

//**************************Format Validation Start*************************************************

/**
 * isValidHeldUpNumber
 *
 * @param heldUpNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidHeldUpNumber(heldUpNoObj) {
	//office code + DDMMYY+HHMMSS = 16 digit :: MUMB150813120055
	var numpattern = /^[0-9]+$/;
	//var letters = /^[A-Za-z]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	
	heldUpNoObj.value = $.trim(heldUpNoObj.value);
		
	if(isNull(heldUpNoObj.value)){
		focusAlertMsg4TxtBox(heldUpNoObj, "Held Up Number");
		return false;
	}	

	if (heldUpNoObj.value.length!=16) {
		clearFocusAlertMsg(heldUpNoObj, "Held Up Number should contain 16 characters only!");
		return false;
	}
	
	if (!heldUpNoObj.value.substring(0, 4).match(alphaNumeric)
			|| !numpattern.test(heldUpNoObj.value.substring(4))) {
		clearFocusAlertMsg(heldUpNoObj, "Held Up Number Format is not correct!");
		return false;
	}

	return true;
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
	var gatepassNo = gatepassNoObj.value;
	var numpattern = /^[0-9]+$/;
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
	manifestNoObj.value = $.trim(manifestNoObj.value);
	
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "OGM/Open Manifest No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 3).match(letters)
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
	
	/*if (!consgNoObj.value.substring(0, 1).match(letters)
			|| !consgNoObj.value.substring(4, 5).match(letters)
			|| !consgNoObj.value.substring(1, 4).match(alphaNumeric)
			|| !numpattern.test(consgNoObj.value.substring(5))) {
		clearFocusAlertMsg(consgNoObj, "Consignment No. Format is not correct!");
		return false;
	}*/

	return true;
}

/**
 * isValidBplNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidBplNo(manifestNoObj) {	
	//City Code+B+6digits :: BOYB123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "BPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "B") {
		clearFocusAlertMsg(manifestNoObj, "BPL No. Format is not correct!");
		return false;
	}

	return true;
}

/**
 * isValidMbplNo
 *
 * @param manifestNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidMbplNo(manifestNoObj) {	
	//City Code+M+6digits :: BOYM123456
	var numpattern = /^[0-9]+$/;
	var letters = /^[A-Za-z]+$/;
	manifestNoObj.value = $.trim(manifestNoObj.value);
		
	if (isNull(manifestNoObj.value)) {
		return false;
	}

	if (manifestNoObj.value.length!=10) {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. should contain 10 characters only!");
		return false;
	}
		
	if (!manifestNoObj.value.substring(0, 4).match(letters)
			|| !numpattern.test(manifestNoObj.value.substring(4))
			|| manifestNoObj.value.substring(3, 4) != "M") {
		clearFocusAlertMsg(manifestNoObj, "MBPL No. Format is not correct!");
		return false;
	}

	return true;
}

//**************************Format Validation End*************************************************