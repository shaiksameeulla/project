var serialNo = 1;
var rowCount = 1;
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
$(document).ready( function () {
				var oTable = $('#invoicePrinting').dataTable( {
					"sScrollY": "160",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
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
				} );
			} );
function loadDefaultObjects() {
	//addRow();
	dropdownEnable();//in common.js
}

function addRow() {

	$('#invoicePrinting')
			.dataTable()
			.fnAddData(
					[
					 	serialNo,
					 	'<span id="customerSpan'+rowCount+'"></span>',
					 	
					 	'<span id="billNumberSpan'+rowCount+'"></span><input type="hidden" id="customer'+ rowCount +'"  name="to.customerIds" /><input type="hidden" id="billNumber'+ rowCount +'" name="to.invoiceIds" /><input type="hidden" id="shipToCode'+ rowCount +'" name="to.shipToCode" /><input type="hidden" id="invoiceRunSheetId'+ rowCount +'" name="to.invoiceRunSheetId" /><input type="hidden" id="invoiceRunSheetDetailId'+ rowCount +'" name="to.invoiceRunSheetDetailId" /><input type="hidden" id="startDateStr'+ rowCount +'" name="to.startDateStr" /><input type="hidden" id="endDateStr'+ rowCount +'" name="to.endDateStr" /><input type="hidden" id="pickUpBoy'+ rowCount +'" name="to.pickUpBoy.employeeId" />',
					 	
					 	 '<select id="status'+ rowCount + '" name="to.status" class="selectBox width145" disabled="false" onchange="getRecieverNameValidation('+ rowCount +');"/><option value="">--Select--</option>' + runSheetStatus + '</select>',
					 	 
					 	 '<input type="text" name = "to.receiverName" id="receiverName'+ rowCount+ '" class="txtbox width115" maxlength="50"/>'
					 	 
					 	]);
 
	rowCount++;
	serialNo++;
}


function isValidJson(json) {
    try {
        JSON.parse(json);
        return true;
    } catch (e) {
        return false;
    }
}
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


function getInvoiceRunSheet(){
	showProcessing();
	var invoiceRunSheetNumber = $("#invoiceRunSheetNumber" ).val();
	if(isNull(invoiceRunSheetNumber.trim())){
		hideProcessing();
		alert("Please Enter Invoice Run Sheet Number");
		return;
		
	}else{
	if(!isNull(invoiceRunSheetNumber)){
		var url = './invoiceRunSheetUpdate.do?submitName=getInvoiceRunSheet&invoiceRunSheetNumber='+invoiceRunSheetNumber;
		jQuery.ajax({
			url : url,
			success : function(req) {
				getinvoiceRun(req);
				}
			});	
		}
	}
}

function getinvoiceRun(ajaxResp){
	hideProcessing();
	if(isNull(ajaxResp) || ajaxResp=="[]"){
		alert("Invoice Run Sheet Number Does not exist");
		$("#invoiceRunSheetNumber" ).val("");
		$("#invoiceRunSheetNumber" ).focus();
	}
	if(!isNull(ajaxResp)){
		/*var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}
		else{*/
		
		if(isJsonResponce(ajaxResp)){
			$("#invoiceRunSheetNumber" ).val("");
			$("#invoiceRunSheetNumber" ).focus();
			return ;
		}
		$("#invoiceRunSheetNumber").attr("disabled", true);
		enableForSave();
		var invoiceRunSheetList = eval('(' + ajaxResp + ')');
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		if (!isNull(invoiceRunSheetList.length)){
				for(var i =0 ;i<invoiceRunSheetList.length;i++){
					for(var j =i ;j<invoiceRunSheetList[i].invoiceRunSheetDetailsList.length;j++){
						addRow();
						$("#customer" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].customerTO.customerId);
						$("#billNumber" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].invoiceBillTO.invoiceId);
						$("#status" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].status);
						$("#receiverName" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].receiverName);
						$("#shipToCode" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].shipToCode);
						$("#invoiceRunSheetDetailId" + (j+1)).val( invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].invoiceRunSheetDetailId);
						$("#invoiceRunSheetId" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetId);
						$("#startDateStr" + (j+1)).val(invoiceRunSheetList[i].startDateStr);
						$("#endDateStr" + (j+1)).val(invoiceRunSheetList[i].endDateStr);
						$("#pickUpBoy" + (j+1)).val(invoiceRunSheetList[i].pickUpBoy.employeeId);
						
						$("#customerSpan" + (j+1)).html(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].customerTO.businessName);
						$("#billNumberSpan" + (j+1)).html(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].invoiceBillTO.invoiceNumber);
						
						if($("#status" + (j+1)).val()=="DEL"){
							$("#status" + (j+1) ).attr("disabled", true);
							$("#receiverName" + (j+1) ).attr("readonly", true);
						}
						else{
							$("#status" + (j+1) ).attr("disabled", false);
							$("#receiverName" + (j+1) ).attr("readonly", true);
						}
					}
			}
		}
	  
	}else{
		deleteAllRow();
	}
		
}
function deleteAllRow() {
	var table = getDomElementById("invoicePrinting");
	var tableRowCount = table.rows.length;
	var oTable = $('#invoicePrinting').dataTable();
	for ( var i = tableRowCount; i >= 0; i--) {
		oTable.fnDeleteRow(i);
	}

}
function saveInvoiceRunSheet(){
	//jQuery("status").attr("disabled", false);
	dropdownEnable();//in common.js
	$("#invoiceRunSheetNumber").attr("disabled", false);
	if(chekMandatory()&&gridEmpty()){
		showProcessing();
	var url = './invoiceRunSheetUpdate.do?submitName=saveInvoiceRunSheet';
	jQuery.ajax({
		url : url,
		data : jQuery("#invoiceRunSheetPrintingForm").serialize(),
		success : function(req) {
			callBack(req);
			}
		});
	}
}
function callBack(data){
	hideProcessing();
	if (data != null && data!="N") {
		var invoiceRunSheetTO = eval('(' + data + ')');
		if(invoiceRunSheetTO.transMag!="BILL0008" && invoiceRunSheetTO.transMag!="BILL0007"){
		disableForSave();
		inputDisable();
		dropdownDisable();//in common.js
		alert('Data saved successfully.');
		var url = "./invoiceRunSheetUpdate.do?submitName=preparePage";
		document.invoiceRunSheetPrintingForm.action=url;
		document.invoiceRunSheetPrintingForm.submit();
		}else {
		alert('Data saved Unsuccessfully.');
		var url = "./invoiceRunSheetUpdate.do?submitName=preparePage";
		document.invoiceRunSheetPrintingForm.action=url;
		document.invoiceRunSheetPrintingForm.submit();
		}
	} 
}

function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
	window.location.reload();
	}
}
function disableForSave(){
	buttonDisabled("saveBtn","btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
}
function enableForSave(){
	buttonEnabled("saveBtn","btnintform");
	jQuery("#" + "saveBtn").removeClass("btnintformbigdis");
	jQuery("#" + "saveBtn").addClass("btnintform");
}

function chekMandatory(){
	if(isNull($("#invoiceRunSheetNumber" ).val())){
		alert("Please Enter Invoice RunSheet Number");
		return false;
	}
	return true;
}

function gridEmpty(){
	var flag= false;
	for(var i=1;i<rowCount;i++){
		if(!isNull($("#customer"+i).val())){
			flag=true;
		}
	}
	if(flag==false){
		alert("Please enter atleast one record.");	
	}
	return flag;
}


function getRecieverNameValidation(rowId){
	var status1=$("#status" + (rowId)).val();
	
	if($("#status" + (rowId)).val()=="DEL"){
		$("#receiverName" + (rowId) ).attr("readonly", false);
	}
	else{
		$("#receiverName" + (rowId) ).attr("readonly", true);
	}
	
	
}