var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
formId='rateQuotationForm';

/*function searchQuotation(){
	var url = "./rateQuotation.do?submitName=searchQuotationByQuotationNo";
	ajaxCall(url, "rateQuotationForm", populateDetails);
}*/


function searchQuotation(){
		var url="./rateQuotation.do?submitName=searchQuotation";
		globalFormSubmit(url,formId);
}

function globalFormSubmit(url,formId){

	 document.forms[formId].action=url;
	 document.forms[formId].submit();
}


function approveQuotation(opName){
	var selectdQuotationNos="";
	var selectdApprovalRequirds="";
	/*var approvRequiredValue="";
	var status="";*/
	
	jQuery('#example >tbody >tr').each(function(i, tr) {
		
		var isChecked = jQuery(this).find('input:checkbox').is(':checked');
		if(isChecked) {
			
				var approvRequiredValue = $('span#approvRequird'+(i+1)).text();
				selectdApprovalRequirds=selectdApprovalRequirds+approvRequiredValue+",";
			
			var selectdQuotationNo=$('span#quotatnNo'+(i+1)).text();
			selectdQuotationNos=selectdQuotationNos+selectdQuotationNo+",";
			//var selectdQuotationNos=selectdQuotationNos+selectdQuotationNo;
		}
	});
	
	if(!isNull(selectdQuotationNos)){
	var url="./rateQuotation.do?submitName=approveQuotation&selectdQuotationNos="+selectdQuotationNos+
	"&opName="+opName+"&selectdApprovalRequirds="+selectdApprovalRequirds;
	
	ajaxJqueryWithRow(url,"rateQuotationForm",approvQuotationResponse,opName);
	}else{
		alert("Pelase select at least one record");
	}
}

function approvQuotationResponse(ajaxResp, status) {
	if (!isNull(ajaxResp)) {
		var responseText = jsonJqueryParser(ajaxResp);
		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			clear();
		} else {
			if (status == "approve") {
				if (responseText != null && success != null) {
					alert(success);
					getDomElementById("fromDate").value = "  "; /*This line has been added so that the list view screens will work as they are*/
					getQuotations();
				}

			} else if (status == "reject") {
				if (responseText != null && success != null) {
					alert(success);
					clear();
				}
			}
		}
	}
	jQuery.unblockUI();
}
	/*if(data!=null){
		if(data=="SUCCESS"){
			if(status=="approve"){
			alert("Selected Quotations approved successfully");
			getQuotations();
			}else if(status=="reject"){
				alert("Selected Quotations rejected successfully");
				clear();
			}
		}else if(data=="FAILURE"){
			if(status=="approve"){
				alert("Selected Quotations could not be approved successfully");
				clear();
				}else if(status=="reject"){
					alert("Selected Quotations could not be rejected successfully");
					clear();
				}
		}
		
	}
	jQuery.unblockUI();*/


function clear(){
/*jQuery('#example >tbody >tr').each(function(i, tr) {
		
		var isChecked = jQuery(this).find('input:checkbox').is(':checked');
		if(isChecked) {
			
			$('.span#chk'+(i+1)).prop("checked", false);
		}
	});

*/

var oTable = $('#example').dataTable();
var sData = jQuery('input:checked', oTable.fnGetNodes()).serialize();
var aTrs = oTable.fnGetNodes();
for ( var i=0 ; i<aTrs.length ; i++ )
{
    if(jQuery('input:checked', aTrs[i]).val()){
        oTable.fnDeleteRow(aTrs[i]);
    }
}


}

function getQuotations(){
	var empId = getDomElementById("empId").value;
	var quotationNo = getDomElementById("quotationNo").value;
	var isEQApprover = getDomElementById("isEQApprover").value;
	if(validateDates()){
		var url = "./rateQuotation.do?submitName=searchApproveQuotations&type="+getDomElementById("type").value+"&fromDate="+getDomElementById("fromDate").value+"&toDate="+getDomElementById("toDate").value+"&status="+getDomElementById("quotatnStatus").value+"&empId="+empId+"&quotationNo="+quotationNo+"&isEQApprover="+isEQApprover;
		ajaxCallWithoutForm(url, showDetails);
	 	jQuery.blockUI({ message: '<h3><img src="images/loading_animation.gif"/></h3>' });
	}
}

function showDetails(ajaxResp) {
	jQuery.unblockUI();
	deleteTableRow();
	if (!isNull(ajaxResp)) {
		var error = ajaxResp[ERROR_FLAG];
		if (ajaxResp != null && error != null) {		
			alert(error);
		} else {
			for ( var i = 0; i < ajaxResp.length; i++) {
				addRow(ajaxResp[i]);
			
			}
		}
	}
	// To set from date equals to current date
	var frDt = trimString(getDomElementById("fromDate").value);
	if (isNull(frDt)) {
		getDomElementById("fromDate").value = FROM_DATE;
	}
}

rowCount = 1;
function addRow(record) {
	

	serialNo = 1;
	var ofcName = "";
	var ofcCode = "";
	if(!isNull(record.salesOfcCode) && (record.salesOfcCode == 'RO')){
		ofcCode = record.salesOfcCode;
		ofcName = record.salesOfcName;
	}else{
		ofcCode = record.rhoOfcCode;
		ofcName = record.regionalName;
	}
		
		$('#example')
				.dataTable()
				.fnAddData(
						[
						 		'<input type="checkbox" id="chk'+rowCount+'" class="checkbox" name="type"/>',
						 		/*rowCount,*/
						 		'<span id="createdDate">'+record.quotationDate+'</span>',
						 		'<span id="quotatnNo'+rowCount+'"><a href="#" onclick="return openQuotation('+"'"
						 		+record.rateQuotationNo+"'"+','+"'"
						 		+record.rateQuotationType+"'"+','
						 		+record.userEmpId+','
						 		+record.salesUserEmpId+','
						 		+record.quotationCreatedBy+','+"'"
						 		+ofcName+"'"+','+"'"+record.cityName+"'"+','
						 		+"'"+record.salesOfficeName+"'"+','+"'"
						 		+record.salesPersonName+"'"+');">'+record.rateQuotationNo+'</a></span>',
						 		'<span id="regionName">'+ofcName+'</span>',
						 		'<span id="stationName">'+record.cityName+'</span>',
						 		'<span id="customerName">'+record.customerName+'</span>',
						 		'<span id="salesOffcName">'+record.salesOfficeName+'</span>',
						 		'<span id="salesPerson">'+record.salesPersonName+'</span>',
						 		'<span id="status">'+record.status+'</span><span id="approvRequird'
						 		+rowCount+'" style="VISIBILITY:hidden;display:none">'+record.approvalRequired+'</span>'
								
						 		/*
						 		<input type="hidden" id="approvRequird"'
							 		+rowCount+' name = "approvRequired"'+rowCount+' value="'+record.approvalRequired+'"/>'*/
						 		
						 		]);
		

		rowCount++;
}


function deleteRow(tableId, rowIndex) {
	var oTable = $('#'+tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}


function deleteTableRow() {
	//$("#example4 > tbody > tr").remove();
	//$("#example4 > tbody").empty();
	try {
	var table = getDomElementById("example");

	var tableRowCount = table.rows.length;
	for ( var i = 0; i < tableRowCount; i++) {
			deleteRow("example", i);
			tableRowCount--;
			i--;
	}
	}catch(e){
	alert(e);
	}
		rowCount = 1;

}


function openQuotation(quotationNo,quotationType,userEmpId,salesUserEmpId,userId,regionalName,cityName,salesOfcName,salesPerson){
	var url = "./rateQuotation.do?submitName=listViewRateQuotation&rateQuotationNo="+quotationNo+"&type="+quotationType+"&userEmpId="
	+userEmpId+"&salesUserEmpId="+salesUserEmpId+"&quotUserId="+userId+"&regionalName="+regionalName+"&cityName="
	+cityName+"&salesOfcName="+salesOfcName+"&salesPerson="+salesPerson +"&approver=Y&approverLevel="+getValueByElementId("type");
	window.open(
			url,'popUpWindow','height=600,width=1100,left=50,top=50,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');

	return;
}

function approveButtonsDisable(){
	jQuery("#" +"approveBtn").removeClass("btnintform");
	jQuery("#" +"rejectBtn").removeClass("btnintform");
	jQuery("#" +"search").removeClass("button");
	jQuery(":button").attr("disabled", true);
}

function validateFromDate(obj){
	if(!isNull(obj.value)){
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);
		var fromDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
		if(fromDate > toDayDate){
			alert("From Date should not be greater than today date");
			obj.value="";
			return false;
		}
		return true;
	}
}

function validateToDate(obj){
	var validfromDate = document.getElementById("fromDate").value;
	if(!isNull(obj.value) && !isNull(validfromDate)){
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);
		
		var validfromDateDay = parseInt(validfromDate.substring(0, 2), 10);
		var validfromDateMon = parseInt(validfromDate.substring(3, 5), 10);
		var validfromDateYr = parseInt(validfromDate.substring(6, 10), 10);
		
		var fromDate = new Date(validfromDateYr, (validfromDateMon - 1), validfromDateDay);
		var toDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
		
		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(), toDayDate.getDate());
		
		if(toDate > toDayDate){
			alert("To Date should not be greater than today date");
			obj.value="";
			return false;
		}else if(toDate < fromDate){
			alert("To Date should be greater than From date");
			obj.value="";
			return false;
		}
		
	}else if(isNull(validfromDate)){
		alert("Please select From date");
		obj.value="";
		return false;
	}
	
	
	return true;
}

function validateDates(){
	var validFromDate = document.getElementById("fromDate").value;
	var validToDate = document.getElementById("toDate").value;
	
	if(isNull(validFromDate)){
		alert("Please select From date");
		return false;
	}else if(isNull(validToDate)){
		alert("Please select To date");
		return false;
	}else if(!isNull(validFromDate) && !isNull(validToDate)){
		if(!validateToDate(document.getElementById("toDate"))){
			return false;
		}
	}
	return true;
}

function enableOrDisablebuttons(obj){
	if(obj.value == "S"){
		btnEnabled("approveBtn","","");
		btnEnabled("rejectBtn","","");
	}else{
		btndisabled("approveBtn","","");
		btndisabled("rejectBtn","","");
	}

}

function btnEnabled(btnName,btnForm,style){
	jQuery("#" +btnName).removeClass("btnintformbigdis");
	buttonEnabled(btnName,"btnintform");
}
function btndisabled(btnName,btnForm,style){
	buttonDisabled(btnName,"btnintform");
	jQuery("#" +btnName).addClass("btnintformbigdis");
}


function gridCheckAll() {
	var checkB = false;
	
	if (getDomElementById("chk0").checked == true) {
		checkB = true;
	}
	
	if (checkB) {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = true;
			}
		}
	} else {
		for ( var i = 1; i < rowCount; i++) {
			if (!isNull(getDomElementById("chk" + i))) {
				getDomElementById("chk" + i).checked = false;
			}
		}
	}
}