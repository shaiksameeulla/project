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
					"bSort": false,				"bInfo": false,
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
	disablePrintBtn();
}

function addRow() {

	$('#invoicePrinting')
			.dataTable()
			.fnAddData(
					[
					 serialNo,
					 '<span id="customerSpan'+rowCount+'"></span>',
					 
					 '<span id="billNumberSpan'+rowCount+'"></span>',
					 
					 '<span id="signatureSpan'+rowCount+'"></span> <input type="hidden" name = "to.customerIds" id="customer'+ rowCount+ '" class="txtbox width115"/><input type="hidden" name = "to.invoiceIds" id="billNumber'+ rowCount+ '" class="txtbox width115"/> <input type="hidden" name = "to.signature" id="signature'+ rowCount+ '" class="txtbox width115"/><input type="hidden" name = "to.shipToCode" id="shipToCode'+ rowCount+ '" class="txtbox width115"/>'
					]);
 
	rowCount++;
	serialNo++;
}



function checkMandatory(){
	hideProcessing();
	var errorsData="";
	var stDate=$("#startDate" ).val();
	var enDate=$("#endDate" ).val();
	var pBoy=$("#pickUpBoy" ).val();
	/*if(isNull(stDate)){
		alert("Please provide start date.");
		setTimeout(function(){$("#startDate" ).focus();},10);
		return false;
	}
	if(isNull(enDate)){
		alert("Please provide end date.");
		setTimeout(function(){$("#endDate" ).focus();},10);
		return false;
	}
	if(isNull(pBoy)){
		alert("Please select a pick up boy.");
		setTimeout(function(){$("#pickUpBoy" ).focus();},10);
		return false;
	}
	return true;*/
	if(isNull(stDate)){
		errorsData = errorsData+"Please provide start date."+"\n";	
	}
	if(isNull(enDate)){
		errorsData = errorsData+"Please provide end date."+"\n";	
	}
	if(isNull(pBoy)){
		errorsData = errorsData+"Please select a Pickup Boy."+"\n";	
	}
	if (!isNull(errorsData)){
    	alert(errorsData);
    	return false;
    }else{
    	return true;
    }
	
}

/*function checkDate(){
	var stDate=$("#startDate" ).val();
	var enDate=$("#endDate" ).val();
	var sDate = new Date(stDate);
	var eDate = new Date(enDate);
	if(stDate=="" || sDate>eDate){
		alert("Please ensure that end date is greater than or equal to start date");
		$("#endDate" ).val("");
		$("#endDate" ).focus();
		return false;
	}
}*/
function checkDate(){
	var startDate=$("#startDate" ).val();
	var endDate=$("#endDate" ).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1], arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1], arrEndDate[0]);
	if(date1=="" || date1>date2){
		alert("Please ensure that end date is greater than or equal to start date");
		$("#endDate" ).val("");
		$("#endDate" ).focus();
		return false;
	}
}

function disablePrintBtn(){
	buttonDisabled("printBtn","btnintform");
	jQuery("#" + "printBtn").addClass("btnintformbigdis");
}

function enableSaveBtn(){
	buttonEnabled("saveBtn","btnintformbigdis");
	jQuery("#" + "saveBtn").addClass("btnintform");
}

function cancelDetails(){
	if(confirm("Do you want to Cancel the details?")) {
	window.location.reload();
	}
}

function saveInvoiceRunSheet(){
	if(checkMandatory()&&gridEmpty()){
	var url = './invoiceRunSheetPrinting.do?submitName=saveInvoiceRunSheet';
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
	if (data != null && data!="N") {
		var invoiceRunSheetTO = eval('(' + data + ')');
		var successMessage="Invoice Runsheet No  "+invoiceRunSheetTO.invoiceRunSheetNumber+"   saved successfully";
		if(invoiceRunSheetTO.transMag!="BILL0008" && invoiceRunSheetTO.transMag!="BILL0007"){
			alert(successMessage);
		$("#invoiceRunSheetId").val(invoiceRunSheetTO.invoiceRunSheetId);
		if(confirm("Do you want to print the invoice run sheet.")){
			printInvoiceRunSheet();	
		}else{
			cancelPage();
			}
		}else {
			alert('Data saved Unsuccessfully.');
			} 
		}
	}

function getCustomersByPickUpBoy(){
	showProcessing();
	if(checkMandatory()){
	var pickUpBoy = $("#pickUpBoy" ).val();
	if(!isNull(pickUpBoy)){
		var url = './invoiceRunSheetPrinting.do?submitName=getCustomersByPickUpBoy&pickUpBoy='+pickUpBoy;
		//ajaxCall(url, "invoiceRunSheetPrintingForm", getCustomers);
		jQuery.ajax({
			url : url,
			data : jQuery("#invoiceRunSheetPrintingForm").serialize(),
			success : function(req) {
				getCustomers(req);
				}
			});	
		}
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

function getCustomers(ajaxResp){
	hideProcessing();
	if(!isNull(ajaxResp)){
	/*	var responseText =ajaxResp; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
		}
	 else{*/
		
		if(isJsonResponce(ajaxResp)){
			return ;
		}
		
		var j = 0;
		var custBillList = eval('(' + ajaxResp + ')');
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		if (!isNull(custBillList.length))
		//var j = 0;
		for(var i =0 ;i<custBillList.length;i++){
				for(var k=0;k<custBillList[i].billTOs.length;k++){
			addRow();	
			$("#customer" + (j+1)).val(custBillList[i].customerTO.customerId);
			$("#billNumber" + (j+1)).val(custBillList[i].billTOs[k].invoiceId);
			$("#signature" + (j+1)).val("");
			$("#shipToCode" + (j+1)).val(custBillList[i].billTOs[k].shipToCode);
			
			$("#customerSpan" + (j+1)).html(custBillList[i].customerTO.businessName);
			$("#billNumberSpan" + (j+1)).html(custBillList[i].billTOs[k].invoiceNumber);
			j++;
			enableSave();
				}
			}
	 
	}else{
		deleteAllRow();
		alert('No Data Found');
	}
		
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
	if(isNull(invoiceRunSheetNumber)){
		alert("Please enter invoiceRunSheetNumber.");
	}else{
	if(!isNull(invoiceRunSheetNumber)){
		var url = './invoiceRunSheetPrinting.do?submitName=getInvoiceRunSheet&invoiceRunSheetNumber='+invoiceRunSheetNumber;
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
		if(isJsonResponce(ajaxResp)){
			return ;
		}
		var invoiceRunSheetList = eval('(' + ajaxResp + ')');
		
		deleteAllRow();
		serialNo = 1;
		rowCount = 1;
		if (!isNull(invoiceRunSheetList.length))
		for(var i =0 ;i<invoiceRunSheetList.length;i++){
			for(var j =i ;j<invoiceRunSheetList[i].invoiceRunSheetDetailsList.length;j++){
				addRow();
			$("#customer" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].customerTO.customerId);
			$("#billNumber" + (j+1)).val(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].invoiceBillTO.invoiceId);
			$("#signature" + (j+1)).val("");
			
			$("#customerSpan" + (j+1)).html(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].customerTO.businessName);
			$("#billNumberSpan" + (j+1)).html(invoiceRunSheetList[i].invoiceRunSheetDetailsList[j].invoiceBillTO.invoiceNumber);
			$("#invoiceRunSheetId").val(invoiceRunSheetList[i].invoiceRunSheetId);
			enablePrint();
			}
		}
	}else{
		deleteAllRow();
	}
}

function cancelPage(){
	window.location.reload();
}
function printInvoiceRunSheet(){
	enablePrint();
	/*var invoiceRunSheetNumber = $("#invoiceRunSheetNumber").val();
	var invoiceRunSheetNumber = "IMUMB0000038";*/
	//var j = rowCount -1;
	var invoiceRunSheetId = $("#invoiceRunSheetId").val();
	
	
	/*var url = 'http://10.76.170.168:8080/udaan-report/pages/reportviewer/invoiceRunSheetReport.jsp?'
		+ "&InvoiceRunsheetNumber="+invoiceRunSheetId;*/
	var url = document.getElementById("reportUrl").value + 'pages/reportviewer/invoiceRunSheetReport.jsp?'
		+ "&InvoiceRunsheetNumber="+invoiceRunSheetId;
		window.open(url, "_blank");
}

function enablePrint(){
	buttonDisabled("saveBtn","btnintform");
	jQuery("#" + "printBtn").removeClass("btnintformbigdis");
	jQuery("#" + "printBtn").addClass("btnintform");
	buttonEnabled("printBtn","btnintform");
	jQuery("#" + "saveBtn").addClass("btnintformbigdis");
}

function enableSave(){
	buttonEnabled("saveBtn","btnintform");
	jQuery("#" + "saveBtn").removeClass("btnintformbigdis");
	jQuery("#" + "saveBtn").addClass("btnintform");
	buttonDisabled("printBtn","btnintform");
	jQuery("#" + "printBtn").removeClass("btnintform");
	jQuery("#" + "printBtn").addClass("btnintformbigdis");
	
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