$(document).ready(function() {     
	$('#leadsPlan').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "100%",
	"bInfo": false,
	"bPaginate": false,     
	"bSort": true,
	"bScrollCollapse": false,
	"sPaginationType": "full_numbers"
	} );
	
	loadDefaultObjects();
	
} ); 



var rowCount = 1;

var currentSystemDate="";
var feedbackCodeTOArray = new Array();

var planTOArray = new Array();

function loadDefaultObjects(){
	//addleadPlanningRow();
	getParentValues();
	getFeedBackCode();
	getLeadsPlanDtlsByleadNumber();
	
	
	
}

var dateStrFieldName = "dateStr";

function getParentValues(){
	var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var customerName = window.opener.document.getElementById("customerName").value;
	var leadId = window.opener.document.getElementById("leadId").value;
	$("#leadNumber").val(leadNumber);
	$("#custName" ).val(customerName);
	$("#leadId" ).val(leadId);
	
}

function addleadPlanningRow() {
	$('#leadsPlan').dataTable().fnAddData( [
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	'<input type="text" id="dateStr'+ rowCount +'" name="to.dateStr" size="20" class="txtbox" onblur="validateVisitedDate('+ rowCount +');" /> \
	<a href="javascript:show_calendar4Grid(dateStrFieldName,'+ rowCount +', this.value)"> \
	<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" /></a>',
	'<input type="text" id="purposeOfVisitors'+ rowCount +'" name="to.purposeOfVisitors" size="20" maxlength="50" class="txtbox" onkeypress="return onlyAlphabet(event);" />',
	'<input type="text" id="contactPersons'+ rowCount +'" name="to.contactPersons" size="20" maxlength="30" class="txtbox" onkeypress="return onlyAlphabet4Lead(event);"  />',
	'<input type="text" id="timeStr'+ rowCount +'" name="to.timeStr" size="10" maxlength="5" class="txtbox" />',
	'<select name="to.feedBackCode" id="feedBackCode'+ rowCount +'" onfocus="setFocusIfNullById(\'timeStr'+ rowCount +'\');"/>\
	<option value="">--Select--</option></select>',
	'<input type="text" id="remarks'+ rowCount +'" name="to.remarks" size="10" maxlength="50" class="txtbox" />\
	<input type="hidden" id="planFeedbackIds'+ rowCount+ '" name="to.planFeedbackIds" value=""/>',
	] );
	
	populateFeedBackCode(rowCount);
	rowCount++;
	updateSerialNoVal("leadsPlan");
	return rowCount-1;
}

/*onblur="addRow('+ rowCount +');"*/

function show_calendar4Grid(str_target, rowId, str_datetime) {
	show_calendar(str_target+rowId, str_datetime);
}

function getFeedBackCode(){
	//var reasonTypeCode = $("#reasonTypeCode").val();
	var url = "./leadPlanning.do?submitName=getFeedBackCodeDetails";
	//showProcessing();
	$.ajax({
		url: url,
		success: function(data){populateFeedBackCodeTOList(data);}
	});
}

function populateFeedBackCodeTOList(data){
	//hideProcessing();
	feedbackCodeTOArray = eval('(' + data + ')');
	//addleadPlanningRow();
	
}

function populateFeedBackCode(rowId){
	clearDropDownList("feedBackCode" + rowId);
	for(var i=0;i<feedbackCodeTOArray.length;i++) {
		addOptionTODropDown("feedBackCode" + rowId, 
				feedbackCodeTOArray[i].description, 
				feedbackCodeTOArray[i].stdTypeCode);
	}
}
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;	
	addOptionTODropDown(selectId, "--Select--", "");
}

function addOptionTODropDown(selectId, label, value){
	var myselect=document.getElementById(selectId);
	try{
		 myselect.add(new Option(label, value), null); //add new option to end of "myselect"
	}
	catch(e){ //in IE, try the below version instead of add()
		 myselect.add(new Option(label, value)); //add new option to end of "myselect"
	}
}

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

function cancelPage(){
	/*var url = "./leadPlanning.do?submitName=preparePage";
	window.location = url;*/
	window.close();
}

function deleteLeadsDataRow(){
	deleteTableRow("leadsPlan");
	updateSerialNoVal("leadsPlan");
	
}
function deleteTableRow(tableId) {

	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		var isDel = false;
		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId, i - 1);
				//table.deleteRow(i);
				rowCount--;
				i--;
				isDel = true;
			}
		}
		if(isDel){
			alert("Record(s) deleted successfully.");
		}else{
			alert("Please select a Non Empty row to delete.");
		}
	} catch (e) {
		alert(e);
	}
}
function deleteRow(tableId, rowIndex) {

	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}





function saveOrUpdateLeadsFeedbackDtls(){
	enableAll();
	if(!GridEmpty()){
		return;
	}
	
	flag=confirm("Do you want to Save the lead Planning Details");
	if(!flag){
		return;
	}
	
	//showProcessing();
	var url="./leadPlanning.do?submitName=savePlanFeedBackDetails";
	document.viewUpdateFeedbackForm.action=url;
	document.viewUpdateFeedbackForm.submit();
	//hideProcessing();
}


function addNewRow(rowCount) {
	var nextRow = parseInt(rowCount) + 1;
	if (eval(document.getElementById("serialNo" + nextRow)) == null) {
		addleadPlanningRow();
		document.getElementById("serialNo" + nextRow).focus();
	}

}

function addRow(selectedRowId){	
	var table = document.getElementById('leadsPlan');
	var lastRow = table.rows.length - 1;
	var isNewRow = false; 
	
	if(!isValidHHMMFormat($("#timeStr" + selectedRowId))){
		$("#timeStr" + selectedRowId).focus();
		return;
	}
	
	if(!validateMandatoryRowFields(selectedRowId)){
		$("#timeStr" + selectedRowId).val("");
		return;
	}
	
	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(rowId==selectedRowId && i==lastRow){
			isNewRow = true;
		}
	}
	if(isNewRow){
		var rowId = addNewRow();
		 $("#dateStr" + rowId).focus();
	}
}


function getLeadDetailsByLeadNumber(){
	var leadNumberObj = $("#leadNumber");
	if(!isNull(leadNumberObj.val())){
		 var url = "./leadPlanning.do?submitName=getLeadDetailsByLeadNumber&leadNumber="+leadNumberObj.val();
		    //showProcessing();
		    $.ajax({
		          url: url,
		          success: function(data){populatetLeadDetails(data);}
		    });	
	}
   
}

function populatetLeadDetails(leadDetals){
	var leadDetals = eval('(' + leadDetals + ')');
	if(!isNull(leadDetals)){
		$("#leadId" ).val(leadDetals.leadId);	
		$("#custName" ).val(leadDetals.customerName);
		getLeadsPlanDtlsByleadNumber();
	}else{
		alert("Lead Number is not valied");
		$("#leadNumber").focus();
	}
    //hideProcessing(); 
}

function validateVisitedDate(rowId){
	var visitDate = $("#dateStr"+ rowId).val();
	if(!isNull(visitDate)){
		 var url = "./leadPlanning.do?submitName=validateVisitedDate&visitedDate="+visitDate;
		    //showProcessing();
		    $.ajax({
		          url: url,
		          success: function(data){validatedDateDiff(data, rowId);}
		    });	
	}
   
}
function validatedDateDiff(dateAlertMsg, rowId){
	if(!isNull(dateAlertMsg)){
		alert(dateAlertMsg);
		$("#dateStr"+ rowId).val("");
		$("#dateStr"+ rowId).focus();
	}
	  //hideProcessing(); 
}

function checkUncheckAllRows(tableId){
	var table = document.getElementById(tableId);
	var checkFlag = document.getElementById("chk0").checked;
	
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		document.getElementById("chk" + rowId).checked = checkFlag;
	}
}


function validateMandatoryRowFields(rowId){
	//var errorsData="";
	var dateStr = $("#dateStr"+ rowId);
	var purposeOfVisitors = $("#purposeOfVisitors"+ rowId);
	var contactPersons = $("#contactPersons"+ rowId);
	var timeStr = $("#timeStr"+ rowId);
	
	if(isNull(dateStr.val())){
			alert("Please enter Date of Visted");
			$("#dateStr"+ rowId).focus();
			return false;
		//errorsData = errorsData+"Please enter Date of Visted."+"\n";	
	}
	if(isNull(purposeOfVisitors.val())){
		
		alert("Please enter purpose Of Visitors ");
		$("#purposeOfVisitors"+ rowId).focus();
		return false;
		//errorsData = errorsData+"Please enter purpose Of Visitors ."+"\n";	
	}
	if(isNull(contactPersons.val())){
		
		alert("Please enter contact Persons ");
		$("#contactPersons"+ rowId).focus();
		return false;
		//errorsData = errorsData+"Please enter contact Persons ."+"\n";		
	}
	/*if(isNull(timeStr.val())){
		alert("Please enter time ");
		$("#timeStr"+ rowId).focus();
		return false;
	}*/
	/*if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{
    	return true;
    }*/
	return true;
}

function onlyAlphabet4Lead(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 39
			|| charCode == 32 || charCode == 0) {
		return true;
	} else {
		return false;
	}

}

function GridEmpty(){
	var table = document.getElementById('leadsPlan');
	 var isValid = true;
    for ( var i = 1; i < table.rows.length; i++) {
    	var rowId = table.rows[i].cells[0].childNodes[0].id.substring(8);
		//var manifestNos = document.getElementById("manifestNos" + rowId);
		var dateStr = $("#dateStr"+ rowId);
		if (rowId == 1) {
			if (isNull(dateStr.val())) {
				alert("Please Enter Atleast 1 Row.");
				$("#dateStr"+ rowId).focus();			
				 isValid = false;
		         return isValid;
			}
			
		}else {
			var dateStr = $("#dateStr"+ rowId).val();
			if(!isNull(dateStr)){
				if(!validateMandatoryRowFields(rowId)){
					return;
				}	
			}
			
		}
    }
    return isValid;
}

function isValidHHMMFormat(obj){
	var format= new RegExp("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");	
	
	if(isNull(obj.val())){
		obj.val("");
		obj.focus();
		alert("Please enter time ");
		return false;
	}
	
	if(!obj.val().match(format)){
		obj.val("");
		obj.focus();
		alert("Please Enter Time in HH:MM Format.");
		return false;
	}	
	return true;
}

function clearFocusAlertMsg(obj, msg){
	obj.val("");
	obj.focus();
	alert(msg);
}

function getLeadsPlanDtlsByleadNumber(){
	var leadNumberObj = $("#leadNumber");
	if(!isNull(leadNumberObj.val())){
		 var url = "./leadPlanning.do?submitName=getLeadsPlanDtlsByLeadNumber&leadNumber="+leadNumberObj.val();
		    //showProcessing();
		    $.ajax({
		          url: url,
		          success: function(data){populatetLeadPlansDetails(data);}
		    });	
	}
   
}
function populatetLeadPlansDetails(planDetals){
	var planTOArray = eval('(' + planDetals + ')');
	deleteAllRowOfTableExceptHeaderRow('leadsPlan');
	for(var i=0;i<planTOArray.length;i++) {
		var rowId = addleadPlanningRow();
		populateGridDtls(planTOArray[i], rowId);
	}
	//addleadPlanningRow();
    //hideProcessing(); 
}

function populateGridDtls(planTOArray, rowId){ 
	
	$("#planFeedbackIds"+ rowId ).val(planTOArray.planFeedbackId);	
	$("#dateStr"+ rowId ).val(planTOArray.date);	
	$("#purposeOfVisitors"+ rowId ).val(planTOArray.purposeOfVisit);
	$("#contactPersons"+ rowId ).val(planTOArray.contactPerson);	
	$("#timeStr"+ rowId ).val(planTOArray.dateTime);
	$("#feedBackCode"+ rowId ).val(planTOArray.feedbackCode);	
	$("#remarks"+ rowId ).val(planTOArray.remarks);
	disableGridDtls(planTOArray,rowId);
}

function disableGridDtls(planTOArray,rowId){
	disableFieldById("dateStr"+ rowId);
	disableFieldById("purposeOfVisitors"+ rowId);
	disableFieldById("contactPersons"+ rowId);
	if(!isNull(planTOArray.feedbackCode)){
		disableFieldById("feedBackCode"+ rowId);
	}
	if(!isNull(planTOArray.remarks)){
		disableFieldById("remarks"+ rowId);
	}
	
	
	
}
function disableFieldById(fieldId){
	document.getElementById(fieldId).disabled = true;
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


function setFocusIfNullById(elementId){

	var obj = $("#" + elementId);

	obj.val($.trim(obj.val()));


	if(isNull(obj.val())){

	obj.focus();

	}

	}

function enableAll(){
	buttonEnable();
	inputEnable();
	dropdownEnable();
}

function buttonEnable(){
	jQuery(":button").attr("disabled", false);
	
}
function inputEnable(){
	jQuery(":input").attr("readonly", false);	
	jQuery(":input").attr("disabled", false);	
}
function dropdownEnable(){
	jQuery("select").attr("disabled", false);
	
}
