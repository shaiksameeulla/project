$(document).ready(function() {     
	$('#cdAwbGrid').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "150%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
	"sPaginationType": "full_numbers"
	} );
	$("#cdAwbGrid").find("tr:not(:first)").remove();
	defaultChanges();
	getDispatchedUsingAndType();
	//getReasonsByReasonType();
} ); 

/** The tableId */
var tableId = "cdAwbGrid";

var dispatchedUsingArray = new Array();
var dispatchedTypeArray = new Array();

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


//Using data grid
/** The rowCount */
var rowCount = 1;

/*function addCdAwbRow(dataTableObj) {
	//$('#cdAwbGrid').dataTable().fnAddData( [
	dataTableObj.fnAddData( [
	'<div id="sno'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="tokenNumbers'+ rowCount +'" name="to.tokenNumbers" size="20" class="txtbox" readonly="true" onkeypress="enterKeyNavigationFocus(event,\'dispatchedUsings'+ rowCount +'\');"/>',
	'<select name="to.dispatchedUsings" id="dispatchedUsings'+ rowCount +'" onkeypress="enterKeyNav4DispatchedUsing(event,'+ rowCount +');" onchange="validateDispatchedType('+ rowCount +');"></select>',
	'<select name="to.dispatchedTypes" id="dispatchedTypes'+ rowCount +'" disabled="true" onkeypress="enterKeyNav4DispatchedType(event,'+ rowCount +');"></select>',
	'<input type="text" id="dispatchDate'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	'<input type="text" id="gatePassNumber'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	'<input type="text" id="vendor'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	'<input type="text" id="destOffice'+ rowCount +'" size="20" class="txtbox" disabled="true");"/> \
	<input type="hidden" id="loadConnectedIds'+ rowCount +'" name="to.loadConnectedIds"/> \
	<input type="hidden" id="loadMovementIds'+ rowCount +'" name="to.loadMovementIds"/> \
	<input type="hidden" id="transportModeCodes'+ rowCount +'" name="to.transportModeCodes"/> '
	]);
	
	//<input type="hidden" id="rateCalculateds'+ rowCount +'" name="to.rateCalculateds"/>
	//validateDispatchedType
	populateStdTypeInDDL("dispatchedUsings"+rowCount, dispatchedUsingArray);
	populateStdTypeInDDL("dispatchedTypes"+rowCount, dispatchedTypeArray);
	rowCount++;
	return rowCount-1;
}*/

/**
 * defaultChanges
 *
 * @author narmdr
 */
function defaultChanges(){
	//$("#consignmentNumber").focus();
}

/**
 * getDispatchedUsingAndType
 *
 * @author narmdr
 */
function getDispatchedUsingAndType(){
	var url = "./cdAwbModification.do?submitName=getDispatchedUsingAndTypeList";
	showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateDispatchedUsingAndTypeList(data);}
	});
}

/**
 * populateDispatchedUsingAndTypeList
 *
 * @param data
 * @author narmdr
 */
var DISPATCHED_USING_OPTION="<option>--SELECT--</option>";
var DISPATCHED_TYPE_OPTION="<option>--SELECT--</option>";
function populateDispatchedUsingAndTypeList(data){
	hideProcessing();
	var cdAwbModificationTO = eval('(' + data + ')');
	
	var errorMsg = getErrorMessage(cdAwbModificationTO);	
	if(!isNull(errorMsg)){
		alert(errorMsg);
		return;
	}
	prepareDispatchedUsingOption(cdAwbModificationTO.standardTypeTOs4DispUsing);
	prepareDispatchedTypeOption(cdAwbModificationTO.standardTypeTOs4DispType);
	
	//FIXME remove bellow if not used.
//	dispatchedUsingArray = cdAwbModificationTO.standardTypeTOs4DispUsing;
//	dispatchedTypeArray = cdAwbModificationTO.standardTypeTOs4DispType;
	//addCdAwbRow();
	
//	alert("DISPATCHED_USING_OPTION: " + DISPATCHED_USING_OPTION);
//	alert("DISPATCHED_TYPE_OPTION: " + DISPATCHED_TYPE_OPTION);
}
 
function prepareDispatchedUsingOption(stdTypeTOs) {
	$.each(stdTypeTOs, function(index, item) {
		DISPATCHED_USING_OPTION = DISPATCHED_USING_OPTION + '<option value="'+item.stdTypeCode+'">' + item.description + '</option>';
	});
}

function prepareDispatchedTypeOption(stdTypeTOs) {
	$.each(stdTypeTOs, function(index, item) {
		if(index == 0) {
			DISPATCHED_TYPE_OPTION = DISPATCHED_TYPE_OPTION + '<option value="'+item.stdTypeCode+'">' + item.description + '</option>';
		} else{
			DISPATCHED_TYPE_OPTION = DISPATCHED_TYPE_OPTION + '<option value="'+item.stdTypeCode+'">' + item.description + '</option>';
		}
		
	});
}

/**
 * populateStdTypeInDDL
 *
 * @param dropDownId
 * @param stdTypeTOs
 * @author narmdr
 */
function populateStdTypeInDDL(dropDownId, stdTypeTOs){
	clearDropDownList(dropDownId);
	for(var i=0;i<stdTypeTOs.length;i++) {
		addOptionTODropDown(dropDownId, 
				stdTypeTOs[i].description, 
				stdTypeTOs[i].stdTypeCode);
	}
}

/******************************************* COMMON STARTS *************************************************************************/
/**
 * clearDropDownList
 *
 * @param selectId
 * @author narmdr
 */
function clearDropDownList(selectId) {
	var obj = document.getElementById(selectId);
	if(! isNull(obj)) {
		obj.options.length = 0;	
		addOptionTODropDown(selectId, selectOption, "");
	}
//	addOptionTODropDown(selectId, selectOption, "");
}

function isNull(value) {
	var flag = true;
	if (value != undefined && value != null && value != "" && value != "null"
			&& value != " " && value != "0") {
		flag = false;
	}
	return flag;
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
			var serialNo = document.getElementById("sno" + rowId);
			serialNo.innerHTML = i;
		}
	}catch(e) {
		
	}
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
 * buttonEnable
 *
 * @author narmdr
 */
function buttonEnable(){
	jQuery(":button").attr("disabled", false);
	
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
 * dropdownEnable
 *
 * @author narmdr
 */
function dropdownEnable(){
	jQuery("select").attr("disabled", false);	
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

/******************************************* COMMON ENDS *************************************************************************/


/**
 * findCdAwbDetails
 *
 * @returns {Boolean}
 * @author narmdr
 */
function findCdAwbDetails(){
	var regionIdObj = $("#regionId");
	var fromDateObj = $("#fromDate");
	var toDateObj = $("#toDate");
	var statusObj = $("#status");

	if(!validateHeader()){
		return false;
	}
	
	var url="./cdAwbModification.do?submitName=findCdAwbDetails";
	
	showProcessing();
	$.ajax({
		url: url,
		data: "regionId="+regionIdObj.val()+"&fromDate="+fromDateObj.val()+"&toDate="+toDateObj.val()+"&status="+statusObj.val(),
		success: function(data){populateCdAwbDetails(data);}
	});
}

/**
 * populateCdAwbDetails
 *
 * @param data
 * @author narmdr
 */
function populateCdAwbDetails(data){
	hideProcessing();
	deleteAllRowOfTableExceptHeaderRow(tableId);

	var cdAwbModificationTO = eval('(' + data + ')');

	if(!isNull(cdAwbModificationTO.errorMsg)){
		clearFocusAlertMsg($("#consignmentNumber"), cdAwbModificationTO.errorMsg);
		return;
	}
	if(!isNull(cdAwbModificationTO.cdAwbModificationDetailsTOs)){
	//	$('#cdAwbGrid tbody').remove();
		var dataTableObj = $('#cdAwbGrid').dataTable();
		$.each(cdAwbModificationTO.cdAwbModificationDetailsTOs, function(index, item) {
			addCdAwbRow(index, item);
//			$('#cdAwbGrid > tbody').append('<tr><td>column 1 value</td><td>column 2 value</td></tr>');
			if($("#status").val()=="U"){
				populateCdAwbModificationDetailsTO(cdAwbModificationTO.cdAwbModificationDetailsTOs[index], index);
				}
		});
	}
	
	
	/*var rowIdd = -1;
	if(!isNull(cdAwbModificationTO.cdAwbModificationDetailsTOs)){
		var dataTableObj = $('#cdAwbGrid').dataTable();
		for(var i=0;i<cdAwbModificationTO.cdAwbModificationDetailsTOs.length;i++) {
			var rowId = addCdAwbRow(dataTableObj);
			if(i==0){
				rowIdd = rowId;
			}
			populateCdAwbModificationDetailsTO(cdAwbModificationTO.cdAwbModificationDetailsTOs[i], rowId);		
		}
		dataTableObj.fnDraw();
	}

	if(rowIdd!=-1){
		$("#dispatchedUsings" + rowIdd).focus();
	}

	if($("#status").val()=="U"){
		disableAll();
		buttonDisableById("btnSave");
		buttonEnableById("btnCancel");
		$("#btnCancel").focus();
	}
	updateSerialNoVal(tableId);*/
	if($("#status").val()=="U"){
		disableAll();
		buttonDisableById("btnSave");
		buttonEnableById("btnCancel");
		$("#btnCancel").focus();
	}
}

function addCdAwbRow(oldIindex, item) {
	//alert(index);
	//alert(item.tokenNumber);
//	alert(oldIindex);
	var index = oldIindex;//(oldIindex + 1);
	var showIndex = oldIindex + 1;
	//<div id="sno'+ index +'">' + index + '</div>
	var newRowContent = 
		'<tr>'+
		'<td><div id="sno'+ index +'">' + showIndex + '</div> </td> ' +
		'<td><input type="text" id="tokenNumbers'+ index +'" name="to.tokenNumbers" value="'+ item.tokenNumber + '" size="20" class="txtbox" readonly="true" onkeypress="enterKeyNavigationFocus(event,\'dispatchedUsings'+ index +'\');"/></td>'+
		'<td><select name="to.dispatchedUsings" id="dispatchedUsings'+ index +'" onkeypress="enterKeyNav4DispatchedUsing(event,'+ index +');"  onchange="validateDispatchedType('+ index +');">'+
		DISPATCHED_USING_OPTION + '</select></td>'+
		'<td><select name="to.dispatchedTypes" id="dispatchedTypes'+ index +'" disabled="true" onkeypress="enterKeyNav4DispatchedType(event,'+ rowCount +');">'+DISPATCHED_TYPE_OPTION+'</select></td>'+
		'<td><input type="text" id="dispatchDate'+ index +'" value="'+ item.dispatchDate + '" size="15" class="txtbox" disabled="true");"/></td>'+
		'<td><input type="text" id="gatePassNumber'+ index +'" value="'+ item.gatePassNumber + '" size="15" class="txtbox" disabled="true");"/></td>'+
		'<td><input type="text" id="vendor'+ index +'" value="'+ item.vendor + '" size="15" class="txtbox" disabled="true");"/></td>'+
		'<td><input type="text" id="destOffice'+ index +'" value="'+ item.destOffice + '" size="20" class="txtbox" disabled="true");"/><input type="hidden" id="loadConnectedIds'+ index +'" value="'+ item.loadConnectedId + '" name="to.loadConnectedIds"/><input type="hidden" id="loadMovementIds'+ index +'" value="'+ item.loadMovementId + '" name="to.loadMovementIds"/>	<input type="hidden" id="transportModeCodes'+ index +'" value="'+ item.transportModeCode + '" name="to.transportModeCodes"/></td></tr>';

/*	dataTableObj.fnAddData( [
	                     	'<div id="sno'+ rowCount +'">' + rowCount + '</div>',
	                     	'<input type="text" id="tokenNumbers'+ rowCount +'" name="to.tokenNumbers" size="20" class="txtbox" readonly="true" onkeypress="enterKeyNavigationFocus(event,\'dispatchedUsings'+ rowCount +'\');"/>',
	                     	'<select name="to.dispatchedUsings" id="dispatchedUsings'+ rowCount +'" onkeypress="enterKeyNav4DispatchedUsing(event,'+ rowCount +');" onchange="validateDispatchedType('+ rowCount +');"></select>',
	                     	'<select name="to.dispatchedTypes" id="dispatchedTypes'+ rowCount +'" disabled="true" onkeypress="enterKeyNav4DispatchedType(event,'+ rowCount +');"></select>',
	                     	'<input type="text" id="dispatchDate'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	                     	'<input type="text" id="gatePassNumber'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	                     	'<input type="text" id="vendor'+ rowCount +'" size="15" class="txtbox" disabled="true");"/>',
	                     	'<input type="text" id="destOffice'+ rowCount +'" size="20" class="txtbox" disabled="true");"/> \
	                     	<input type="hidden" id="loadConnectedIds'+ rowCount +'" name="to.loadConnectedIds"/> \
	                     	<input type="hidden" id="loadMovementIds'+ rowCount +'" name="to.loadMovementIds"/> \
	                     	<input type="hidden" id="transportModeCodes'+ rowCount +'" name="to.transportModeCodes"/> '
	                     	]);*/
	
	//var DISPATCHED_USING_OPTION;
	//var DISPATCHED_TYPE_OPTION;
	
	/*$("#dispatchedUsings" + index).innerHTML=DISPATCHED_USING_OPTION;
	$("#dispatchedTypes" + index).innerHTML=DISPATCHED_TYPE_OPTION;*/
	//alert(newRowContent);
	//	$('#cdAwbGrid > tbody').append('<tr><td>column 1 value</td><td>column 2 value</td></tr>'); 

	$("#cdAwbGrid > tbody").append(newRowContent);
}
/**
 * populateCdAwbModificationDetailsTO
 *
 * @param cdAwbModificationDetailsTO
 * @param rowId
 * @author narmdr
 */
function populateCdAwbModificationDetailsTO(cdAwbModificationDetailsTO, rowId){
	$("#tokenNumbers" + rowId).val(cdAwbModificationDetailsTO.tokenNumber);
	$("#dispatchedUsings" + rowId).val(cdAwbModificationDetailsTO.dispatchedUsing);
	$("#dispatchedTypes" + rowId).val(cdAwbModificationDetailsTO.dispatchedType);
	$("#loadConnectedIds" + rowId).val(cdAwbModificationDetailsTO.loadConnectedId);
	$("#loadMovementIds" + rowId).val(cdAwbModificationDetailsTO.loadMovementId);
	$("#transportModeCodes" + rowId).val(cdAwbModificationDetailsTO.transportModeCode);
	$("#dispatchDate" + rowId).val(cdAwbModificationDetailsTO.dispatchDate);
	$("#gatePassNumber" + rowId).val(cdAwbModificationDetailsTO.gatePassNumber);
	$("#vendor" + rowId).val(cdAwbModificationDetailsTO.vendor);
	$("#destOffice" + rowId).val(cdAwbModificationDetailsTO.destOffice);
	//validateDispatchedType(rowId);
}

/**
 * cancelCdAwbDetails
 *
 * @author narmdr
 */
function cancelCdAwbDetails(){
	var flag = confirm("Do you want to Cancel the CD/AWB Details!");
	if(flag){
		var url = "./cdAwbModification.do?submitName=viewCdAwbModification";
		window.location = url;
	}
}

/**
 * updateCdAwbDetails
 *
 * @author narmdr
 */
function updateCdAwbDetails(){
	if(!validateMandatoryFields()){
		return;
	}
	flag=confirm("Do you want to Save the CD/AWB Details");
	if(!flag){
		return;
	}
	enableAll();
	var url="./cdAwbModification.do?submitName=updateCdAwbDetails";
	document.cdAwbModificationForm.action=url;
	document.cdAwbModificationForm.submit();
	showProcessing();
}

/**
 * validateMandatoryFields
 *
 * @returns {Boolean}
 * @author narmdr
 */
var COUNT = 0;
function validateMandatoryFields(){
	var table = document.getElementById(tableId);

	if(!validateHeader()){
		return false;
	}
	
	//Validating table data
	var rowValidation = false;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
		rowValidation = true;
	}
	
	 /* $('#cdAwbGrid >tbody ').find('tr').each(function(i, el) {
		 var index = i;
//		 alert("first Alert: " + el.childNodes[0].innerHTML);
//		 alert("second alert: " + i);
         if(!validateMandatoryRowFields(index)){
 			return false;
 		}
 		rowValidation = true;
       
     });*/
	 
	if(!rowValidation){
		alert("No CD/AWB Details to save!");
		return false;
	}
	return true;
}


/**
 * validateHeader
 *
 * @returns {Boolean}
 * @author narmdr
 */
function validateHeader(){

	var fromDateObj = $("#fromDate");
	var toDateObj = $("#toDate");
	var statusObj = $("#status");

	if(isNull(fromDateObj.val())){
		focusAlertMsg4TxtBox(fromDateObj, "From Date");
		return false;
	}
	if(isNull(toDateObj.val())){
		focusAlertMsg4TxtBox(toDateObj, "To Date");
		return false;
	}	
	if(isNull(statusObj.val())){
		focusAlertMsg4Select(statusObj, "Status");
		return false;
	}
	return true;
}

/**
 * validateMandatoryRowFields
 *
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateMandatoryRowFields(rowId){
	var dispatchedUsingObj = $("#dispatchedUsings" + rowId);
	var dispatchedTypesObj = $("#dispatchedTypes" + rowId);
	if(isNull(dispatchedUsingObj.val()) || dispatchedUsingObj.val()=='--SELECT--'&& $("#transportModeCodes" + rowId).val()!=$("#transportModeRoadCode").val()){
		focusAlertMsg4Select(dispatchedUsingObj, "Dispatched Using");
		return false;
	}
	if(dispatchedUsingObj.val() =="AWB" && dispatchedTypesObj.val() == "--SELECT--"){
		focusAlertMsg4Select(dispatchedUsingObj, "Dispatched Type");
		return false;
	}
	return true;
}


/**
 * validateDispatchedType
 *
 * @param rowId
 * @returns {Boolean}
 * @author narmdr
 */
function validateDispatchedType(rowId){

	var dispatchedUsingObj = $("#dispatchedUsings" + rowId);
	var dispatchedTypesObj = document.getElementById("dispatchedTypes" + rowId);

	if(dispatchedUsingObj.val()=="AWB"){
		dispatchedTypesObj.disabled = false;
		return true;
	} else {
		dispatchedTypesObj.value='--SELECT--';
		dispatchedTypesObj.disabled = true;
		return false;
	}
}


/**
 * enterKeyNav4DispatchedUsing
 *
 * @param evt
 * @param rowId
 * @author narmdr
 */
function enterKeyNav4DispatchedUsing(evt, rowId){
	if(isEnterKey(evt)){
		if(validateDispatchedType(rowId)){
			$("#dispatchedTypes" + rowId).focus();
		}else{
			validateLastRowAndFocus("dispatchedUsings" + (rowId+1));
		}
	}
}

/**
 * enterKeyNav4DispatchedType
 *
 * @param evt
 * @param rowId
 * @author narmdr
 */
function enterKeyNav4DispatchedType(evt, rowId){
	if(isEnterKey(evt)){
		validateLastRowAndFocus("dispatchedUsings" + (rowId+1));
	}
}

/**
 * validateLastRowAndFocus
 *
 * @param nextRowFocusId
 * @author narmdr
 */
function validateLastRowAndFocus(nextRowFocusId){
	var obj = document.getElementById(nextRowFocusId);
	if(!isNull(obj)){
		obj.focus();
	}else{
		$("#btnSave").focus();
	}
}