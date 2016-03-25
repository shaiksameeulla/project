var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";

/**
 * isJsonResponce
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp){
	var responseText=null;
	try{
		responseText = jsonJqueryParser(ObjeResp);
	}catch(e){
		
	}
	if(!isNull(responseText)){
		var error = responseText[ERROR_FLAG];
		if(!isNull(error)){
		alert(error);
		return true;
		}
	}
	return false;
}
	

$(document).ready(function() {     
	var oTable = $('#leadsPlan').dataTable( {         
	"sScrollY": 200,         
	"sScrollX": "100%",         
	"sScrollXInner": "100%",
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
	} );
	
	loadDefaultObjects();
	
} ); 

var rowCount = 1;

var currentSystemDate="";
var dateStrFieldName = "dateStr";
var contactPerson="";

function loadDefaultObjects(){
	getParentValues();
	var rowid= addleadPlanningRow();
	$("#contactPersons"+ rowid ).val(contactPerson);
	
}



function getParentValues(){
	var leadNumber = window.opener.$("#leadNumber").val();
	var customerName = window.opener.$("#customerName").val();
	var leadId = window.opener.$("#leadId").val();
	var status = window.opener.$("#status").val();
	
	/*var leadNumber = window.opener.document.getElementById("leadNumber").value;
	var customerName = window.opener.document.getElementById("customerName").value;
	var leadId = window.opener.document.getElementById("leadId").value;
	var status = window.opener.document.getElementById("status").value;*/
	contactPerson = window.opener.document.getElementById("contactPerson").value;
	$("#leadNumber").val(leadNumber);
	$("#custName" ).val(customerName);
	$("#leadId" ).val(leadId);
	$("#status" ).val(status);
	//$("#contactPerson"+ rowCount ).val(contactPerson);
//	$("#contactPerson" ).val(contactPerson);
	
}


function addleadPlanningRow() {
	$('#leadsPlan').dataTable().fnAddData( [
	'<input type="checkbox"   id="chk'+ rowCount +'" name="chkBoxName" value=""/>',
	'<div id="serialNo'+ rowCount +'">' + rowCount + '</div>',
	/*'<input type="text" id="visitNos'+ rowCount +'" name="to.visitNos" size="20" class="txtbox" onchange="getCurrentDateTime('+ rowCount +');"  />',*/
	'<input type="text" id="dateStr'+ rowCount +'" name="to.dateStr" size="20" class="txtbox" onblur="validateVisitedDate('+ rowCount +');" readonly="true"/> \
	<a href="javascript:show_calendar4Grid(dateStrFieldName,'+ rowCount +', this.value)"> \
	<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" /></a>',
	'<input type="text" id="purposeOfVisitors'+ rowCount +'" name="to.purposeOfVisitors" size="20" maxlength="50" class="txtbox" onkeypress="return charactersForPurVisit(event);" onchange="getContactPerson('+ rowCount +');" />',
	'<input type="text" id="contactPersons'+ rowCount +'" name="to.contactPersons" size="20" maxlength="30" class="txtbox"  onkeypress="return charactersForSecondaryContactPerson(event);"  />',
	'<input type="text" id="timeStr'+ rowCount +'" name="to.timeStr" size="10" maxlength="5" class="txtbox" onblur="addRow('+ rowCount +');" onkeypress="return allowOnlyNumbers(event)"/>'
	] );
	
	rowCount++;
	updateSerialNoVal("leadsPlan");
	
	return rowCount-1;
}



function show_calendar4Grid(str_target, rowId, str_datetime) {
	show_calendar(str_target+rowId, str_datetime);
}
/*onfocus="getDateTime('+ rowCount +');"
onfocus="getDateTime('+ rowCount +');"*/


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
	if(confirm("Do you want to Cancel the details?")){
		window.close();
	}
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
					return;
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



function saveOrUpdateLeadPlaningDetails(){
	
	if(!GridEmpty()){
		return;
	}
	if(!validateMandatoryFields4Save()){
		return;
	}
	if(confirm("Do you want to Save the lead Planning Details")){
	
		/*var url="./leadPlanning.do?submitName=savePlan";
		document.viewUpdateFeedbackForm.action=url;
		document.viewUpdateFeedbackForm.submit();*/
		var url = './leadPlanning.do?submitName=savePlan';
		jQuery.ajax({
			url : url,
			async: false,
			data : jQuery("#viewUpdateFeedbackForm").serialize(),
			success : function(req) {
				callBack(req);
			}
		});
	}
		
}
function callBack(data){
	if(data != null){
		var viewUpdateFeedbackTO = eval('(' + data + ')');
		if(viewUpdateFeedbackTO.transMag!="LM002"){
			alert('Plan saved successfully.\n');
			buttonDisabled("save","btnintform");
			jQuery("#" + "save").addClass("btnintformbigdis");
			//window.location.reload();
		}else{
			alert('Plan could not be saved successfully.');
		}
	}
}

function validateMandatoryFields4Save(){
	var table = document.getElementById("leadsPlan");
	
	var rowValidation = false;
	for (var i=1;i<table.rows.length;i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if(rowId > 1){
			var dateStr = $("#dateStr"+ rowId);
			if(isNull(dateStr.val())){
				continue;
			}
		}
		
		if(!validateMandatoryRowFields(rowId)){
			return false;
		}
		rowValidation = true;
	}
	/*if(!rowValidation){
		alert("Please Enter at least one Row!");
	}*/
	return true;
}


function addNewRow(rowCount) {
	var nextRow = parseInt(rowCount) + 1;
	if (eval(document.getElementById("serialNo" + nextRow)) == null) {
		var nextRowid = addleadPlanningRow();
		//$("#contactPersons"+ nextRowid ).val(contactPerson);
		// $("#dateStr" + nextRowid).focus();
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
		addNewRow();
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
	if(!isNull(ajaxResp)){
		if(isJsonResponce(ajaxResp)){
			return ;
		}
	var leadDetals = eval('(' + leadDetals + ')');
	/*if(!isNull(leadDetals.alertMsg)){
		alert(leadDetals.alertMsg);
		$("#leadNumber").focus();
	}*/
	if(!isNull(leadDetals)){
		$("#leadId" ).val(leadDetals.leadId);	
		$("#custName" ).val(leadDetals.customerName);
	}
	}
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
	var dateStr = $("#dateStr"+ rowId);
	var purposeOfVisitors = $("#purposeOfVisitors"+ rowId);
	var contactPersons = $("#contactPersons"+ rowId);
	var timeStr = $("#timeStr"+ rowId);
	
	if(isNull(dateStr.val())){
			alert("Please enter Date of Visted");
			$("#dateStr"+ rowId).focus();
			return false;
	}
	if(isNull(purposeOfVisitors.val())){
		
		alert("Please enter purpose Of Visitors ");
		$("#purposeOfVisitors"+ rowId).focus();
		return false;
	}
	if(isNull(contactPersons.val())){
		
		alert("Please enter contact Persons ");
		$("#contactPersons"+ rowId).focus();
		return false;
	}
	if(isNull(timeStr.val())){
		alert("Please enter time ");
		$("#timeStr"+ rowId).focus();
		return false;
	}
	
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
    	var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		//var manifestNos = document.getElementById("manifestNos" + rowId);
		var dateStr = $("#dateStr"+ rowId);
		if (rowId == 1) {
			if (isNull(dateStr.val())) {
				alert("Please Enter Atleast 1 Row.");
				$("#dateStr"+ rowId).focus();			
				 isValid = false;
		         return isValid;
			}
			
		}
    }
    return isValid;
}

function isValidHHMMFormat(obj){
	var format= new RegExp("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");	
	if(obj.val()==null){
		return false;
	}	
	if(!obj.val().match(format)){
		obj.val("");
		setTimeout(function(){ obj.focus();},10);
		//obj.focus();
		alert("Please Enter Time in HH:MM Format.");
		return false;
	}	
	return true;
}

function getContactPerson(rowId){
	$("#contactPersons"+ rowId ).val(contactPerson);
}

function OnlyAlphabetsAndNos(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 || (charCode >= 48 && charCode <= 57)||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function allowOnlyAlphabets(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if((charCode>=97 && charCode<=122)||(charCode>=65 && charCode<=90)||charCode==9 || charCode==8 ||charCode==0){
		return true;
	}
	else{
		return false;
	}
	return false;
}

function allowOnlyNumbers(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if ((charCode >= 48 && charCode <= 58) || charCode == 9 || charCode == 8 || charCode==0) {
		return true;
	}
	else{
		return false;
	}
	return false;
}